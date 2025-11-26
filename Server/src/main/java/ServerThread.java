import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {

    private final ServerMain app;
    private final int port;
    private boolean running = true;
    private ServerSocket serverSocket;

    public ServerThread(ServerMain app, int port) {
        this.app = app;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            app.appendLog("SERVER: Listening on port " + port);
            while (running) {
                Socket client = null;
                try {
                    client = serverSocket.accept();
                    app.appendLog("SERVER: Client connected: " + client.getRemoteSocketAddress());
                    ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                    PokerInfo req = (PokerInfo) in.readObject();
                    app.appendLog("SERVER RECEIVED: " + req.messageType + " status =" + req.statusMessage);
                    PokerInfo reply;
                    switch (req.messageType) {
                        case "BET": reply = processBet(req); break;
                        case "PLAY": reply = processPlay(req); break;
                        case "FOLD": reply = processFold(req); break;
                        default:
                            reply = new PokerInfo("ERROR");
                            reply.statusMessage = "Unkown messageType: " + req.messageType;
                    }
                    out.writeObject(reply);
                    out.flush();
                } catch (IOException e) {
                    if (!running) {
                        app.appendLog("SERVER: stopped accepting connections.");
                        break;
                    } else {
                        app.appendLog("SERVER ERROR (accept loop): " + e.getMessage());
                        e.printStackTrace();
                        break;
                    }
                } catch (Exception e) {
                    app.appendLog("SERVER ERROR (client handling): " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    if (client != null && !client.isClosed()) {
                        try {
                            client.close();
                        } catch (IOException ignored) {}
                    }
                }
            }

        } catch (Exception e) {
            if (running) {
                app.appendLog("SERVER ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException ignored) {}
            }
        }
    }

    public void stopServer() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            app.appendLog("Server error while stopping: " + e.getMessage());
        }
    }

    private PokerInfo processBet(PokerInfo req) {
        PokerInfo reply = new PokerInfo("DEAL");
        Deck deck = new Deck();

        reply.playerHand.add(deck.draw());
        reply.playerHand.add(deck.draw());
        reply.playerHand.add(deck.draw());

        reply.dealerHand.add(deck.draw());
        reply.dealerHand.add(deck.draw());
        reply.dealerHand.add(deck.draw());

        reply.anteBet = req.anteBet;
        reply.pairPlusBet = req.pairPlusBet;

        reply.pairPlusWinnings = ThreeCardLogic.evalPPWinnings(reply.playerHand, reply.pairPlusBet);
        reply.handWinnings = 0;
        reply.totalWinnings = 0;
        reply.statusMessage = "Cards dealt.";

        return reply;
    }

    private PokerInfo processPlay(PokerInfo req) {
        PokerInfo reply = new PokerInfo("RESULT");
        reply.playerHand.addAll(req.playerHand);
        reply.dealerHand.addAll(req.dealerHand);
        reply.anteBet = req.anteBet;
        reply.pairPlusBet = req.pairPlusBet;

        int pp = ThreeCardLogic.evalPPWinnings(reply.playerHand, reply.pairPlusBet);
        int hand = ThreeCardLogic.compareHands(reply.playerHand, reply.dealerHand, reply.anteBet);

        reply.handWinnings = hand;
        reply.pairPlusWinnings = pp;
        reply.totalWinnings = hand + pp;

        if (hand > 0) reply.statusMessage = "Player wins hand.";
        else if (hand < 0) reply.statusMessage = "Dealer wins hand.";
        else reply.statusMessage = "its a draw";

        return reply;
    }

    private PokerInfo processFold(PokerInfo req) {
        PokerInfo reply = new PokerInfo("RESULT");

        reply.playerHand.addAll(req.playerHand);
        reply.dealerHand.addAll(req.dealerHand);

        reply.anteBet = req.anteBet;
        reply.pairPlusBet = req.pairPlusBet;

        int pp = ThreeCardLogic.evalPPWinnings(reply.playerHand, reply.pairPlusBet);

        reply.pairPlusWinnings = pp;
        reply.handWinnings = -reply.anteBet;
        reply.totalWinnings = pp - reply.anteBet;
        reply.statusMessage = "Player folds.";

        return reply;
    }
}

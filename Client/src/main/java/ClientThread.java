import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private final String host;
    private final int port;
    private final GameController controller;
    private final PokerInfo request;

    public ClientThread(String host, int port, PokerInfo request, GameController controller) {
        this.host = host;
        this.port = port;
        this.controller = controller;
        this.request = request;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(host, port)) {

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(request);
            out.flush();

            PokerInfo reply = (PokerInfo) in.readObject();
            controller.updateFromPokerInfo(reply);

        } catch (Exception e) {
            controller.appendGameMessage("CLIENT ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

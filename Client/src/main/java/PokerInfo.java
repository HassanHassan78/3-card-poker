import java.io.Serializable;
import java.util.ArrayList;

public class PokerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    public String messageType;
    public String statusMessage;
    public int anteBet;
    public int pairPlusBet;
    public int playBet;
    public ArrayList<Card> playerHand;
    public ArrayList<Card> dealerHand;
    public int pairPlusWinnings;
    public int handWinnings;
    public int totalWinnings;
    public PokerInfo(String messageType) {
        this.messageType = messageType;
        this.statusMessage = "";
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
    }
}
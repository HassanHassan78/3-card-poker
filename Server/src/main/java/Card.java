import java.io.Serializable;

public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int value;
    private final char suit;
    public Card(int value, char suit) {
        this.value = value;
        this.suit = suit;
    }
    public int getValue() {
        return value;
    }
    public char getSuit() {
        return suit;
    }
    @Override
    public String toString() {
        String v;
        switch (value) {
            case 11: v = "J"; break;
            case 12: v = "Q"; break;
            case 13: v = "K"; break;
            case 14: v = "A"; break;
            default: v = Integer.toString(value);
        }
        String s;
        switch (suit) {
            case 'H': s = "♥"; break;
            case 'D': s = "♦"; break;
            case 'C': s = "♣"; break;
            case 'S': s = "♠"; break;
            default: s = String.valueOf(suit);
        }
        return v + s;
    }
}

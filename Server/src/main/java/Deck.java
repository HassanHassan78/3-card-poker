import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        char[] suits = {'C','H','S','D'};
        for (char s : suits) {
            for (int v = 2; v <= 14; v++) {
                cards.add(new Card(v,s));
            }
        }
        shuffle();
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }
    public Card draw() {
        return cards.remove(0);
    }
}

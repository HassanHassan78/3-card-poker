import java.util.ArrayList;
import java.util.Arrays;

public class ThreeCardLogic {

    private static boolean isFlush(ArrayList<Card> cards) {
        return cards.get(0).getSuit() == cards.get(1).getSuit()
                && cards.get(0).getSuit() == cards.get(2).getSuit();
    }

    private static boolean isThreeKind(ArrayList<Card> cards) {
        int x = cards.get(0).getValue();
        int y = cards.get(1).getValue();
        int z = cards.get(2).getValue();
        return x == y && x == z;
    }

    private static boolean isPair(ArrayList<Card> cards) {
        int x = cards.get(0).getValue();
        int y = cards.get(1).getValue();
        int z = cards.get(2).getValue();
        return x == y || y == z || x == z;
    }

    private static boolean isStraight(ArrayList<Card> cards) {
        int[] v = {
                cards.get(0).getValue(),
                cards.get(1).getValue(),
                cards.get(2).getValue(),
        };
        Arrays.sort(v);
        return v[0] + 1 == v[1] && v[1] + 1 == v[2];
    }

    private static int highest(ArrayList<Card> cards) {
        int x = cards.get(0).getValue();
        int y = cards.get(1).getValue();
        int z = cards.get(2).getValue();
        return Math.max(x, Math.max(y, z));
    }

    public static int evalHand(ArrayList<Card> cards) {
        boolean flush = isFlush(cards);
        boolean threeKind = isThreeKind(cards);
        boolean pair = isPair(cards);
        boolean straight = isStraight(cards);

        if (straight && flush) return 1;
        if (threeKind) return 2;
        if (straight) return 3;
        if (flush) return 4;
        if (pair) return 5;
        return 0;
    }

    public static int evalPPWinnings(ArrayList<Card> cards, int bet) {
        if (bet <= 0) return 0;
        int rank = evalHand(cards);

        switch (rank) {
            case 1: return bet * 40;
            case 2: return bet * 30;
            case 3: return bet * 6;
            case 4: return bet * 3;
            case 5: return bet;
            default: return 0;
        }
    }

    private static int handStrength(int rank) {
        switch (rank) {
            case 1: return 6;
            case 2: return 5;
            case 3: return 4;
            case 4: return 3;
            case 5: return 2;
            case 0: return 1;
        }
        return 0;
    }

    public static int compareHands(ArrayList<Card> player, ArrayList<Card> dealer, int anteBet) {

        if (anteBet <= 0) return 0;

        int pr = evalHand(player);
        int dr = evalHand(dealer);
        int ph = highest(player);
        int dh = highest(dealer);

        boolean dealerQualifies = (dr > 0) || (dh >= 12);

        if (!dealerQualifies) return anteBet;

        int ps = handStrength(pr);
        int ds = handStrength(dr);

        if (ps > ds) return anteBet * 2;
        if (ps < ds) return -anteBet * 2;

        if (ph > dh) return anteBet * 2;
        if (ph < dh) return -anteBet * 2;

        return 0;
    }
}

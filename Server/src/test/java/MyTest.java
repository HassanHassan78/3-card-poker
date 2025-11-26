import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest {

    private int ante;
    private int ppBet;
    @BeforeEach
    void setup() {
        ante = 5;
        ppBet = 5;
    }

    private ArrayList<Card> hand(Card c1, Card c2, Card c3) {
        ArrayList<Card> h = new ArrayList<>();
        h.add(c1);
        h.add(c2);
        h.add(c3);
        return h;
    }
    @Test
    void evalHandStraightFlushTest() {
        ArrayList<Card> h = hand(
                new Card(10, 'S'),
                new Card(11, 'S'),
                new Card(12, 'S')
        );
        assertEquals(1, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalHandThreeOfAKindTest() {
        ArrayList<Card> h = hand(
                new Card(7, 'C'),
                new Card(7, 'D'),
                new Card(7, 'H')
        );
        assertEquals(2, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalHandStraightTest() {
        ArrayList<Card> h = hand(
                new Card(4, 'C'),
                new Card(5, 'D'),
                new Card(6, 'H')
        );
        assertEquals(3, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalHandFlushTest() {
        ArrayList<Card> h = hand(
                new Card(2, 'H'),
                new Card(8, 'H'),
                new Card(13, 'H')
        );
        assertEquals(4, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalHandPairTest() {
        ArrayList<Card> h = hand(
                new Card(9, 'C'),
                new Card(9, 'D'),
                new Card(12, 'S')
        );
        assertEquals(5, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalHandHighCardTest() {
        ArrayList<Card> h = hand(
                new Card(2, 'C'),
                new Card(7, 'D'),
                new Card(11, 'H')
        );
        assertEquals(0, ThreeCardLogic.evalHand(h));
    }

    @Test
    void EvalPPWinnings_ForStraightFlushTest() {
        ArrayList<Card> h = hand(
                new Card(10, 'S'),
                new Card(11, 'S'),
                new Card(12, 'S')
        );
        int win = ThreeCardLogic.evalPPWinnings(h, ppBet);
        assertEquals(ppBet * 40, win);
    }

    @Test
    void EvalPPWinnings_NoWinForHighCardTest() {
        ArrayList<Card> h = hand(
                new Card(2, 'C'),
                new Card(7, 'D'),
                new Card(11, 'H')
        );
        int win = ThreeCardLogic.evalPPWinnings(h, ppBet);
        assertEquals(0, win);
    }

    @Test
    void CompareHands_PlayerWinsTest() {
        ArrayList<Card> player = hand(
                new Card(7, 'C'),
                new Card(7, 'D'),
                new Card(7, 'H')
        );

        ArrayList<Card> dealer = hand(
                new Card(2, 'C'),
                new Card(9, 'D'),
                new Card(12, 'H')   // FIXED: Q high → dealer qualifies
        );

        int result = ThreeCardLogic.compareHands(player, dealer, ante);

        assertEquals(ante * 2, result);
    }

    @Test
    void CompareHands_DealerWinsTest() {
        ArrayList<Card> player = hand(
                new Card(2, 'C'),
                new Card(9, 'D'),
                new Card(11, 'H')
        );

        ArrayList<Card> dealer = hand(
                new Card(4, 'C'),
                new Card(5, 'D'),
                new Card(6, 'H')
        );

        int result = ThreeCardLogic.compareHands(player, dealer, ante);

        assertEquals(-ante * 2, result);
    }

    @Test
    void CompareHands_DealerDoesNotQualifyTest() {
        ArrayList<Card> dealer = hand(
                new Card(2, 'C'),
                new Card(6, 'D'),
                new Card(10, 'H')
        );

        ArrayList<Card> player = hand(
                new Card(4, 'C'),
                new Card(5, 'D'),
                new Card(6, 'H')
        );

        int result = ThreeCardLogic.compareHands(player, dealer, ante);

        assertEquals(ante, result);
    }

    @Test
    void CompareHands_TieTest() {
        ArrayList<Card> p = hand(
                new Card(10, 'S'),
                new Card(11, 'S'),
                new Card(12, 'S')
        );

        ArrayList<Card> d = hand(
                new Card(10, 'S'),
                new Card(11, 'S'),
                new Card(12, 'S')
        );

        assertEquals(0, ThreeCardLogic.compareHands(p, d, ante));
    }

}




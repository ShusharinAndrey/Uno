package com.shusharin.myapplication;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

import com.shusharin.myapplication.card.Card;
import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.card.CardWithColor;
import com.shusharin.myapplication.card.Color;
import com.shusharin.myapplication.mode.SinglePlayerApp;
import com.shusharin.myapplication.user.Bot;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTests {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void cardCheckTest() {
        CardViewer cardViewer = new CardViewer(new Card(CardWithColor.TWO, Color.YELLOW));
        Card card = cardViewer.getCard();
        assertNotNull(card);
        assertEquals(2, card.getId());
        assertSame(Color.YELLOW, card.getColor());
    }



    @Test
    public void preferredColorTest() {
        Bot bot = new Bot();
        CardViewer card1 = new CardViewer(new Card(CardWithColor.TWO, Color.YELLOW));
        CardViewer card2 = new CardViewer(new Card(CardWithColor.THREE, Color.YELLOW));
        CardViewer card3 = new CardViewer(new Card(CardWithColor.FOUR, Color.BLUE));
        bot.addCardsInHand(card1);
        bot.addCardsInHand(card2);
        bot.addCardsInHand(card3);
        assertEquals(bot.getPreferredColor(), Color.YELLOW);
    }

    @Test
    public void createDeckTest() {
        int countOfBlue = 0;
        int countOfRed = 0;
        int countOfGreen = 0;
        int countOfYellow = 0;

        SinglePlayerApp.createDeck();
        for (CardViewer card : SinglePlayerApp.deck) {
            switch (card.getCard().getColor()) {
                case BLUE:
                    countOfBlue++;
                    break;
                case RED:
                    countOfRed++;
                    break;
                case GREEN:
                    countOfGreen++;
                    break;
                case YELLOW:
                    countOfYellow++;
                    break;
            }
        }
        assertEquals(112, SinglePlayerApp.deck.size());
        assertEquals(26, countOfBlue);
        assertEquals(26, countOfRed);
        assertEquals(26, countOfGreen);
        assertEquals(26, countOfYellow);
    }

    @After
    public void tearDown() {
        System.out.println();
        System.out.println("!!TEST passed!!");
    }
}
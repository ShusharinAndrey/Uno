package com.shusharin.myapplication;

import java.util.ArrayList;
import java.util.Vector;

public class Bot
{
    private CardsDeck hand;
    private int countOfBlue;
    private int countOfGreen;
    private int countOfYellow;
    private int countOfRed;

    public Bot()
    {
        this.hand = null;
    }

    private void colorQuantity()
    {
        for(CardViewer cards: hand.cards)
        {
            switch(cards.getCard().getColor())
            {
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
        int max = Math.max(Math.max(countOfBlue, countOfGreen), Math.max(countOfRed, countOfYellow));
    }



}

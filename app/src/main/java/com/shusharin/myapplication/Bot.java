package com.shusharin.myapplication;

import java.util.ArrayList;
import java.util.Vector;

public class Bot
{
    private Vector<Card> hand;
    private int countOfBlue;
    private int countOfGreen;
    private int countOfYellow;
    private int countOfRed;

    public Bot()
    {
        this.hand = new Vector<>();
    }

    private void colorQuantity()
    {
        for(Card cards: hand)
        {
            switch(cards.getColor())
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
    }


}

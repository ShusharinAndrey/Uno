package com.shusharin.myapplication.user;

import com.shusharin.myapplication.CardViewer;

import java.util.ArrayList;
import java.util.Collections;

public class Bot implements Playable {

    private int countOfBlue;
    private int countOfGreen;
    private int countOfYellow;
    private int countOfRed;

    public Bot(ArrayList<CardViewer> cardsInHand) {
        Collections.copy(this.cardsInHand,cardsInHand);
    }

    private void colorQuantity() {
        for (CardViewer cardInHand : cardsInHand) {
            switch (cardInHand.getCard().getColor()) {
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
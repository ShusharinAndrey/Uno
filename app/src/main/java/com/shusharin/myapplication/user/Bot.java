package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Bot extends Player {

    private int countOfBlue;
    private int countOfGreen;
    private int countOfYellow;
    private int countOfRed;

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
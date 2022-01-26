package com.shusharin.myapplication.user;

import static com.shusharin.myapplication.card.Color.BLUE;
import static com.shusharin.myapplication.card.Color.GREEN;
import static com.shusharin.myapplication.card.Color.RED;
import static com.shusharin.myapplication.card.Color.YELLOW;

import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.card.Color;

import java.util.ArrayList;

import lombok.NoArgsConstructor;

public class Bot extends User<ArrayList<CardViewer>> {

    private int countOfBlue;
    private int countOfGreen;
    private int countOfYellow;
    private int countOfRed;
    private Color preferredColor;

    public Bot() {
        cardsInHand = new ArrayList<>();
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
        if (max == countOfBlue) {
            preferredColor = BLUE;
        } else if (max == countOfGreen) {
            preferredColor = GREEN;
        } else if (max == countOfRed) {
            preferredColor = RED;
        } else {
            preferredColor = YELLOW;
        }
    }

    public Color getPreferredColor() {
        colorQuantity();
        return preferredColor;
    }

    public CardViewer turn(){
        for (int i = 0; i < cardsInHand.size(); i++) {
            if (cardsInHand.get(i).isAvailable()) {
                return cardsInHand.get(i);
            }
        }
        return cardsInHand.get(0);
    }
}
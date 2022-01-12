package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.card.Color;

import java.util.ArrayList;

public abstract class Player {
    ArrayList<CardViewer> cardsInHand = new ArrayList<>();

    public ArrayList<CardViewer> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardsInHand(CardViewer card) {
        this.cardsInHand.add(card);
    }

    public static void setAvailableCards(ArrayList<CardViewer> cards, CardViewer cardOnTheTable) {
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setAvailable(
                    cards.get(i).getCard().getColor() == cardOnTheTable.getCard().getColor()
                            || cards.get(i).getCard().getId() == cardOnTheTable.getCard().getId()
                            || cards.get(i).getCard().getColor() == Color.BLACK
            );
        }
    }
}

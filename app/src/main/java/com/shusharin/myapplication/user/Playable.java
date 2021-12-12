package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;

import java.util.ArrayList;

public interface Playable {
    ArrayList<CardViewer> cardsInHand = new ArrayList<>();

    default ArrayList<CardViewer> getCardsInHand() {
        return cardsInHand;
    }

    default void addCardsInHand(CardViewer card) {
        this.cardsInHand.add(card);
    }

}

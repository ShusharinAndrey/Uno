package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;

import java.util.ArrayList;

public abstract class Playable {
    ArrayList<CardViewer> cardsInHand = new ArrayList<>();

    public ArrayList<CardViewer> getCardsInHand() {
        return cardsInHand;
    }

    public void addCardsInHand(CardViewer card) {
        this.cardsInHand.add(card);
    }

}

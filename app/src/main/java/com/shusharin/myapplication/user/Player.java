package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Playable {

    public Player(ArrayList<CardViewer> cardsInHand) {
        Collections.copy(this.cardsInHand, cardsInHand);
    }


}

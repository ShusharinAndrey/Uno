package com.shusharin.myapplication.mode;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.shusharin.myapplication.card.CardsDeckApp;
import com.shusharin.myapplication.user.Player;

import java.util.ArrayList;

public class MultiPlayerApp extends SinglePlayerApp {
    private ArrayList<Player> players;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < conservation.getQuantityPlayer(); i++) {
            players.add(new Player());
        }
    }


    @Override
    public void onClickHand(View view) {
        if (!isPressed) {
            isPressed = true;
            setAvailableCards(players.get(conservation.getNumberPlayer()).getCardsInHand());
            CardsDeckApp.setCards(players.get(conservation.getNumberPlayer()).getCardsInHand());
            Intent intent = new Intent(this, CardsDeckApp.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }

    @Override
    protected void handOutCard() {
        for (int i = 0; i < quantityStartCard; i++) {
            for (int j = 0; j < conservation.getQuantityPlayer(); j++) {
                players.get(j).addCardsInHand(peekCard());
            }
        }
    }

    public void giveNextTurn() {
    }

}

package com.shusharin.myapplication.mode;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.user.Player;

import java.util.ArrayList;

public class MultiPlayerApp extends SinglePlayerApp {
    private final ArrayList<Player> players = new ArrayList<>();

    public static void afterSelectingCard() {
        startTurn.setVisibility(View.VISIBLE);
        blackView.setVisibility(View.VISIBLE);
        blackCardsInHand.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preferences = getSharedPreferences(getIntent().getStringExtra("GAME"), Context.MODE_PRIVATE);
        conservation = ContinueApp.conservations.get(getIntent().getIntExtra("NUMBER_CONSERVATION", 0));

        for (int i = 0; i < conservation.getQuantityPlayer(); i++) {
            players.add(new Player());
        }
        super.onCreate(savedInstanceState);
    }

    protected ArrayList<CardViewer> getCardsInHandCurrentPlayer() {
        return players.get(conservation.getNumberPlayer()).getCardsInHand();
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
        if(conservation.getNumberPlayer() < conservation.getQuantityPlayer()-1){
            conservation.setNumberPlayer(conservation.getNumberPlayer()+1);
        }else{
            conservation.setNumberPlayer(0);
        }
        //if специальная карта
    }

    @Override
    public void onClickStartTurn(View view) {
        giveNextTurn();
        super.onClickStartTurn(view);
    }
}

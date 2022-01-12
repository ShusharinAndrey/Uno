package com.shusharin.myapplication.mode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.user.Human;

import java.util.ArrayList;

public class MultiPlayerApp extends SinglePlayerApp {
    private final ArrayList<Human> players = new ArrayList<>();

    public static void afterSelectingCard() {
        startTurn.setVisibility(View.VISIBLE);
        blackView.setVisibility(View.VISIBLE);
        blackCardsInHand.setVisibility(View.VISIBLE);
    }

    @Override
    protected void loadData() {
        if (preferences.contains(conservation.getName())) {
            loadCardArray("DECK_SIZE", deck, "DECK_CARD_ID", "DECK_CARD_COLOR");
            loadCardArray("TABLE_SIZE", table, "TABLE_CARD_ID", "TABLE_CARD_COLOR");
            for (int i = 0; i < players.size(); i++) {
                loadCardArray("PLAYER_SIZE" + i, players.get(i).getCardsInHand(), "PLAYER_CARD_ID" + i, "PLAYER_CARD_COLOR" + i);
            }
        }
    }

    @Override
    protected void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(conservation.getName(), conservation.getName());

        saveCardArray(editor, "DECK_SIZE", deck, "DECK_CARD_COLOR", "DECK_CARD_ID");
        saveCardArray(editor, "TABLE_SIZE", table, "TABLE_CARD_COLOR", "TABLE_CARD_ID");
        for (int i = 0; i < players.size(); i++) {
            saveCardArray(editor, "PLAYER_SIZE" + i, players.get(i).getCardsInHand(), "PLAYER_CARD_COLOR" + i, "PLAYER_CARD_ID" + i);
        }
        editor.apply();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        for (int i = 0; i < conservation.getQuantityPlayer(); i++) {
            players.add(new Human());
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

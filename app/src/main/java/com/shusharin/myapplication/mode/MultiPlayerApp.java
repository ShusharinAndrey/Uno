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
    private static final ArrayList<Human> players = new ArrayList<>();
    private static boolean isClockwise = true;

    public static void afterSelectingCard() {
        isTakeCard = false;
        startTurn.setVisibility(View.VISIBLE);
        blackView.setVisibility(View.VISIBLE);
        blackCardsInHand.setVisibility(View.VISIBLE);
        reactForSpecialCard();
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
        conservation = ContinueApp.conservations.get(getIntent().getIntExtra("NUMBER_CONSERVATION", 0));
        preferences = getSharedPreferences(getIntent().getStringExtra(conservation.getName()), Context.MODE_PRIVATE);

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

    public static void reactForSpecialCard() {
        switch (getCardOnTheTable().getCard().getId()) {
            case 10:
                giveNextTurn();
                giveNextTurn();
                break;
            case 13:
                giveNextTurn();
                for (int i = 0; i < 4; i++) {
                    players.get(conservation.getNumberPlayer()).addCardsInHand(peekCard());
                }
                giveNextTurn();
                // Выбор цвета из диалогового окна
                break;
            case 12:
                giveNextTurn();
                for (int i = 0; i < 2; i++) {
                    players.get(conservation.getNumberPlayer()).addCardsInHand(peekCard());
                }
                giveNextTurn();
                break;
            case 14:
                // Выбор цвета из диалогового окна
                giveNextTurn();
                break;
            case 11:
                isClockwise = !isClockwise;
                giveNextTurn();
                break;
        }

    }

    public static void giveNextTurn() {
        if (isClockwise) {
            if (conservation.getNumberPlayer() < conservation.getQuantityPlayer() - 1) {
                conservation.setNumberPlayer(conservation.getNumberPlayer() + 1);
            } else {
                conservation.setNumberPlayer(0);
            }
        }else {
            if (conservation.getNumberPlayer() > 0) {
                conservation.setNumberPlayer(conservation.getNumberPlayer() - 1);
            } else {
                conservation.setNumberPlayer(conservation.getQuantityPlayer() - 1);
            }
        }
    }

    @Override
    protected void toAfterSelectingCard(){
        afterSelectingCard();
    }

    @Override
    public void onClickStartTurn(View view) {
        super.onClickStartTurn(view);
    }
}

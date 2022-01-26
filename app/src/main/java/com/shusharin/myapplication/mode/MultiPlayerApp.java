package com.shusharin.myapplication.mode;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.shusharin.myapplication.R;
import com.shusharin.myapplication.selected_games.ContinueApp;

import java.util.ArrayList;

public class MultiPlayerApp extends SinglePlayerApp {
    private static final ArrayList<Human> players = new ArrayList<>();
    public TextView quantityCardsInHandLeft;
    public TextView playerLeft;
    public TextView quantityCardsInHandRight;
    public TextView playerRight;
    private static boolean isClockwise;

    @Override
    public void afterSelectingCard(boolean isOnlyBot) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getCardsInHand().size() == 0) {
                blackView.setVisibility(View.VISIBLE);
                startTurn.setActivated(false);
                playerTop.setText(R.string.winner);
                String winner = playerTop.getText().toString();
                playerTop.setText(R.string.player);
                String player = playerTop.getText().toString();
                currentPlayerStart.setText(winner + player + conservation.getNumberPlayer());
                currentPlayerStart.setVisibility(View.VISIBLE);
                currentPlayerStart.setTextSize(20);
                conservation.setFinished(true);
                return;
            }
        }

        isTakeCard = false;
        startTurn.setVisibility(View.VISIBLE);
        blackView.setVisibility(View.VISIBLE);
        blackCardsInHand.setVisibility(View.VISIBLE);
        currentPlayerStart.setVisibility(View.VISIBLE);
        reactForSpecialCard();
    }

    public void reactForSpecialCard() {
        switch (getCardOnTheTable().getCard().getId()) {
            case 10:
                giveNextTurn();
                giveNextTurn();
                break;
            case 13:
                giveNextTurn();
                for (int i = 0; i < 4; i++) {
                    players.get(conservation.getNumberPlayer()).getCardsInHand().add(peekCard());
                }
                giveNextTurn();
                // Выбор цвета из диалогового окна
                break;
            case 12:
                giveNextTurn();
                for (int i = 0; i < 2; i++) {
                    players.get(conservation.getNumberPlayer()).getCardsInHand().add(peekCard());
                }
                giveNextTurn();
                break;// Выбор цвета из диалогового окна
            case 11:
                isClockwise = !isClockwise;
                giveNextTurn();
                break;
            default:
                giveNextTurn();
                break;
        }
        setNameAndQuantityCards();

    }

    @SuppressLint("DefaultLocale")
    private void setNameAndQuantityCards() {

        int topId;
        int leftId = -1;
        int rightId = -1;
        if (conservation.getQuantityPlayer() == 2) {
            topId = up(conservation.getNumberPlayer());
        } else if (conservation.getQuantityPlayer() == 3) {
            rightId = up(conservation.getNumberPlayer());
            topId = up(rightId);
        } else {
            rightId = up(conservation.getNumberPlayer());
            topId = up(rightId);
            leftId = up(topId);
        }
        playerTop.setText(R.string.player);
        String player = playerTop.getText().toString();
        playerTop.setText(String.format("%s%d", player, topId));
        quantityCardsInHandTop.setText(String.valueOf(players.get(topId).getCardsInHand().size()));
        if (rightId != -1) {
            playerRight.setText(String.format("%s%d", player, rightId));
            quantityCardsInHandRight.setText(String.valueOf(players.get(rightId).getCardsInHand().size()));
            if (leftId != -1) {
                playerLeft.setText(String.format("%s%d", player, leftId));
                quantityCardsInHandLeft.setText(String.valueOf(players.get(leftId).getCardsInHand().size()));
            }
        }
        setCurrentPlayer(player);
    }

    private static int up(int start) {
        if (isClockwise) {
            start++;
            if (start == conservation.getQuantityPlayer()) {
                start = 0;
            }
        } else {
            start--;
            if (start < 0) {
                start = conservation.getQuantityPlayer() - 1;
            }
        }
        return start;
    }

    public static void giveNextTurn() {
        if (isClockwise) {
            if (conservation.getNumberPlayer() < conservation.getQuantityPlayer() - 1) {
                conservation.setNumberPlayer(conservation.getNumberPlayer() + 1);
            } else {
                conservation.setNumberPlayer(0);
            }
        } else {
            if (conservation.getNumberPlayer() > 0) {
                conservation.setNumberPlayer(conservation.getNumberPlayer() - 1);
            } else {
                conservation.setNumberPlayer(conservation.getQuantityPlayer() - 1);
            }
        }
    }

    @SuppressLint("DefaultLocale")
    private void setCurrentPlayer(String player) {
        currentPlayerStart.setText(String.format("%s%d", player, conservation.getNumberPlayer()));
        playerCurrent.setText(String.format("%s%d", player, conservation.getNumberPlayer()));
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

        players.clear();

        for (int i = 0; i < conservation.getQuantityPlayer(); i++) {
            players.add(new Human());
            players.get(i).setCardsInHand(new Hand());
        }
        isClockwise = true;

        super.onCreate(savedInstanceState);
        quantityCardsInHandLeft = findViewById(R.id.quantityCardsInHandLeft);
        playerLeft = findViewById(R.id.PlayerLeft);
        quantityCardsInHandRight = findViewById(R.id.quantityCardsInHandRight);
        playerRight = findViewById(R.id.playerRight);
        setNameAndQuantityCards();
    }

    protected Hand getCardsInHandCurrentPlayer() {
        return players.get(conservation.getNumberPlayer()).getCardsInHand();
    }

    @Override
    protected void handOutCard() {
        for (int i = 0; i < quantityStartCard; i++) {
            for (int j = 0; j < conservation.getQuantityPlayer(); j++) {
                players.get(j).getCardsInHand().add(peekCard());
            }
        }
    }

    @Override
    public void onClickStartTurn(View view) {
        super.onClickStartTurn(view);
    }
}

package com.shusharin.myapplication.mode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.R;
import com.shusharin.myapplication.card.Card;
import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.card.CardWithColor;
import com.shusharin.myapplication.card.CardsDeckApp;
import com.shusharin.myapplication.card.Color;
import com.shusharin.myapplication.card.SpecialCardWithBlack;
import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.user.Bot;
import com.shusharin.myapplication.user.Human;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerApp extends AppCompatActivity {
    protected static final ArrayList<CardViewer> deck = new ArrayList<>();
    protected static final int quantityStartCard = 7;
    public static ArrayList<CardViewer> table = new ArrayList<>();
    public static View cardOnTheTable;
    public static Conservation conservation;
    public static View cardsInHand;
    public static TextView quantityCardsInHand;
    protected static View blackView;
    protected static View blackCardsInHand;
    protected static Button startTurn;
    private final Human player = new Human();
    private final Bot bot = new Bot();
    protected SharedPreferences preferences;
    protected boolean isPressed = false;

    public static void afterSelectingCard() {
//Бот ходит здесь
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cardOnTheTable = findViewById(R.id.cardOnTheTable);
        cardsInHand = findViewById(R.id.cardsInHand);
        quantityCardsInHand = findViewById(R.id.quantityCardsInHand);
        blackView = findViewById(R.id.blackView);
        blackCardsInHand = findViewById(R.id.blackCardsInHand);
        startTurn = findViewById(R.id.startTurn);

        conservation = ContinueApp.conservations.get(getIntent().getIntExtra("NUMBER_CONSERVATION", 0));
        preferences = getSharedPreferences(getIntent().getStringExtra(conservation.getName()), Context.MODE_PRIVATE);

        if (conservation.isContinue()) {
            loadData();
        }
        else
        {
            createDeck();
            mixDeck();

            handOutCard();

            table.add(peekCard());
        }

        if (conservation.isFinished()) {

        }

        cardOnTheTable.setBackground(getCardOnTheTable().getDrawable(this));
    }

    protected void loadData() {
        if (preferences.contains(conservation.getName())) {
            deck.clear();
            for (int i = 0; i < deck.size(); i++) {
                deck.add(new CardViewer(new Card(
                        preferences.getInt("DECK_CARD_ID" + i, 0),
                        Color.values()[preferences.getInt("DECK_CARD_COLOR", 0)]
                )));
            }
            for (int i = 0; i < table.size(); i++) {
                table.add(new CardViewer(new Card(
                        preferences.getInt("TABLE_CARD_ID" + i, 0),
                        Color.values()[preferences.getInt("TABLE_CARD_COLOR", 0)]
                )));
            }
            for (int i = 0; i < player.getCardsInHand().size(); i++) {
                player.addCardsInHand(new CardViewer(new Card(
                        preferences.getInt("PLAYER_CARD_ID" + i, 0),
                        Color.values()[preferences.getInt("PLAYER_CARD_COLOR", 0)]
                )));
            }
            for (int i = 0; i < bot.getCardsInHand().size(); i++) {
                bot.addCardsInHand(new CardViewer(new Card(
                        preferences.getInt("BOT_CARD_ID" + i, 0),
                        Color.values()[preferences.getInt("BOT_CARD_COLOR", 0)]
                )));
            }
        }
    }

    protected void saveData() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("saveName", conservation.getName());

        for (int i = 0; i < deck.size(); i++) {
            editor.putInt("DECK_CARD_COLOR" + i, deck.get(i).getCard().getColor().getNumber());
            editor.putInt("DECK_CARD_ID" + i, deck.get(i).getCard().getId());
        }
        for (int i = 0; i < table.size(); i++) {
            editor.putInt("TABLE_CARD_COLOR" + i, table.get(i).getCard().getColor().getNumber());
            editor.putInt("TABLE_CARD_ID" + i, table.get(i).getCard().getId());
        }
        for (int i = 0; i < player.getCardsInHand().size(); i++) {
            editor.putInt("PLAYER_CARD_COLOR" + i, player.getCardsInHand().get(i).getCard().getColor().getNumber());
            editor.putInt("PLAYER_CARD_ID" + i, player.getCardsInHand().get(i).getCard().getId());
        }
        for (int i = 0; i < bot.getCardsInHand().size(); i++) {
            editor.putInt("BOT_CARD_COLOR" + i, bot.getCardsInHand().get(i).getCard().getColor().getNumber());
            editor.putInt("BOT_CARD_ID" + i, bot.getCardsInHand().get(i).getCard().getId());
        }
        editor.apply();
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    protected ArrayList<CardViewer> getCardsInHandCurrentPlayer() {
        return player.getCardsInHand();
    }

    protected void setBackgroundHand() {
        cardsInHand.setBackground(getCardsInHandCurrentPlayer().get(0).getDrawable(this));
        quantityCardsInHand.setText(String.valueOf(getCardsInHandCurrentPlayer().size()));
    }

    protected CardViewer peekCard() {
        return peekCard(0);
    }

    protected CardViewer peekCard(int index) {
        CardViewer cardView = deck.get(index);
        deck.remove(index);
        return cardView;
    }

    protected CardViewer getCardOnTheTable() {
        return table.get(table.size() - 1);
    }

    protected void mixDeck() {
        Random random = new Random();
        CardViewer card;
        for (int i = 0; i < deck.size() - 1; i++) {
            int index = random.nextInt(i + 1);
            card = peekCard(index);
            deck.add(index, peekCard(i));
            deck.add(i, card);
        }
    }

    protected void handOutCard() {
        for (int i = 0; i < quantityStartCard; i++) {
            player.addCardsInHand(peekCard());
            bot.addCardsInHand(peekCard());
        }
    }

    protected void createDeck() {
        deck.clear();
        for (int j = 1; j < Color.values().length - 1; j++) {
            for (int i = 0; i < CardWithColor.values().length; i++) {
                for (int k = 0; k < 2; k++) {
                    deck.add(new CardViewer(new Card(CardWithColor.values()[i], Color.values()[j])));
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < SpecialCardWithBlack.values().length; j++) {
                deck.add(new CardViewer(new Card(SpecialCardWithBlack.values()[j])));
            }
        }
    }

    public void onClickHand(View view) {
        if (!isPressed) {
            isPressed = true;
            setAvailableCards(getCardsInHandCurrentPlayer());
            CardsDeckApp.setCards(getCardsInHandCurrentPlayer());
            Intent intent = new Intent(this, CardsDeckApp.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }

    protected void setAvailableCards(ArrayList<CardViewer> cards) {
        CardViewer cardOnTheTable = getCardOnTheTable();
        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).setAvailable(
                    cards.get(i).getCard().getColor() == cardOnTheTable.getCard().getColor()
                            || cards.get(i).getCard().getId() == cardOnTheTable.getCard().getId()
                            || cards.get(i).getCard().getColor() == Color.BLACK
            );
        }
    }

    public void onClickNo(View view) {

    }

    public void onClickStartTurn(View view) {
        startTurn.setVisibility(View.GONE);
        blackView.setVisibility(View.GONE);
        blackCardsInHand.setVisibility(View.GONE);
        setBackgroundHand();
    }
}

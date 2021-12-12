package com.shusharin.myapplication.mode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
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

import java.util.ArrayList;

public class SinglePlayerApp extends AppCompatActivity {
    protected static final ArrayList<CardViewer> deck = new ArrayList<>();
    protected static final int quantityStartCard = 7;
    public static ArrayList<CardViewer> table = new ArrayList<>();
    public static View cardOnTheTable;
    protected static Conservation conservation;
    protected SharedPreferences preferences;
    protected View cardsInHand;
    protected TextView quantityCardsInHand;
    protected boolean isPressed = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cardOnTheTable = findViewById(R.id.cardOnTheTable);
        cardsInHand = findViewById(R.id.cardsInHand);
        quantityCardsInHand = findViewById(R.id.quantityCardsInHand);

        preferences = getSharedPreferences(getIntent().getStringExtra("GAME"), Context.MODE_PRIVATE);

        conservation = ContinueApp.conservations.get(getIntent().getIntExtra("NUMBER_CONSERVATION", 0));

        createDeck();
        mixDeck();

        handOutCard();

        table.add(peekCard());
        cardOnTheTable.setBackground(getCardOnTheTable().getDrawable(this));
    }

    protected CardViewer peekCard() {
        CardViewer cardView = deck.get(0);
        deck.remove(0);
        return cardView;
    }

    protected CardViewer getCardOnTheTable() {
        return table.get(table.size() - 1);
    }

    protected void mixDeck() {

    }

    protected void handOutCard() {
        for (int i = 0; i <= quantityStartCard; i++) {

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
            //Здесь вместо deck должны быть карты игрока с уже установленым правильно флагом isAvailable
            CardsDeckApp.setCards(deck);
            Intent intent = new Intent(this, CardsDeckApp.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }
}

package com.shusharin.myapplication.card;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.R;
import com.shusharin.myapplication.mode.MultiPlayerApp;
import com.shusharin.myapplication.mode.SinglePlayerApp;

import java.util.ArrayList;

public class CardsDeckApp extends AppCompatActivity {
    private static ArrayList<CardViewer> cards = null;
    private static int numberSelectionCard = 0;
    private static AlertDialog dialog;
    private boolean isPressed;

    private static void showDialog() {
        dialog.show();
    }

    public static void setCards(ArrayList<CardViewer> cards) {
        CardsDeckApp.cards = cards;
    }

    public static void toCard(int numberSelectionCard) {
        CardsDeckApp.numberSelectionCard = numberSelectionCard;
        showDialog();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateDialog();
        setContentView(R.layout.cards_deck);
        RecyclerView listConservation = findViewById(R.id.list);
        final CardAdapter adapterConservationsTwoPlayerOneField = new CardAdapter(this, cards);
        listConservation.setAdapter(adapterConservationsTwoPlayerOneField);
        adapterConservationsTwoPlayerOneField.notifyDataSetChanged();
    }

    private void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        //Создать с кнопками
        @SuppressLint("InflateParams") View viewDialog = inflater.inflate(R.layout.card_dialog, null);

        viewDialog.findViewById(R.id.positive).setOnClickListener(arg0 -> {
            if (!isPressed) {
                isPressed = true;
                //передать numberSelectionCard
                SinglePlayerApp.table.add(cards.get(numberSelectionCard));
                SinglePlayerApp.cardOnTheTable.setBackground(cards.get(numberSelectionCard).getDrawable(SinglePlayerApp.cardOnTheTable.getContext()));

                cards.remove(numberSelectionCard);

                SinglePlayerApp.cardsInHand.setBackground(cards.get(0).getDrawable(this));
                SinglePlayerApp.quantityCardsInHand.setText(String.valueOf(cards.size()));

                cards = null;
                finish();
                overridePendingTransition(0, 0);
                if (SinglePlayerApp.conservation.getMode() == Conservation.Modes.SINGLE) {
                    SinglePlayerApp.afterSelectingCard();
                } else {
                    MultiPlayerApp.afterSelectingCard();
                }
                new Handler().postDelayed(() -> isPressed = false, 250);
            }
        });

        viewDialog.findViewById(R.id.negative).setOnClickListener(arg0 -> {
            if (!isPressed) {
                isPressed = true;
                dialog.dismiss();
                new Handler().postDelayed(() -> isPressed = false, 100);
            }
        });
        builder.setView(viewDialog);
        dialog = builder.create();

        dialog.setOnShowListener(arg0 -> {
            CardViewer card = cards.get(numberSelectionCard);
            if (card.isAvailable()) {
                viewDialog.findViewById(R.id.card).setBackground(card.getDrawable(this));
            }
        });

        dialog.setOnCancelListener(arg0 -> closeDialog());
        dialog.setOnDismissListener(arg0 -> closeDialog());
    }

    private void closeDialog() {
    }
}

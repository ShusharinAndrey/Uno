package com.shusharin.myapplication;

import static com.shusharin.myapplication.CardsDeck.cards;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    static CardViewer cardViewer = null;
    private boolean isPressed = false;

    public static int getIdDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cards.clear();
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i <= 12; i++) {
                switch (j) {
                    case 1:
                        cards.add(new CardViewer(new Card(CardWithColor.values()[i], Color.BLUE)));
                        break;
                    case 2:
                        cards.add(new CardViewer(new Card(CardWithColor.values()[i], Color.RED)));
                        break;
                    case 3:
                        cards.add(new CardViewer(new Card(CardWithColor.values()[i], Color.YELLOW)));
                        break;
                    case 4:
                        cards.add(new CardViewer(new Card(CardWithColor.values()[i], Color.GREEN)));
                        break;

                }
            }
        }

        cards.get(0).setAvailable(false);
        cards.get(3).setAvailable(false);
        cards.get(11).setAvailable(false);

        for (int i = 0; i < 2; i++) {
            cards.add(new CardViewer(new Card(SpecialCardWithBlack.TAKE_4_CARDS_AND_CHANGE_COLOR)));
            cards.add(new CardViewer(new Card(SpecialCardWithBlack.CHANGE_COLOR)));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cardViewer != null) {
            findViewById(R.id.card).setBackground(cardViewer.getDrawable(this));
        }
    }

    public void onClickPlay(View view) {
        if (!isPressed) {
            isPressed = true;
            Intent intent = new Intent(MainActivity.this, CardsDeck.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }
}
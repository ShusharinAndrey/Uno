package com.shusharin.myapplication;

import static com.shusharin.myapplication.CardsDeckApp.cards;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.selected_games.NewGameApp;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    static CardViewer cardViewer = null;
    private boolean isPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReferenceApp.hideNavigationBar(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        TextView text = findViewById(R.id.tvTitle);
        text.setText(R.string.title_activity_main);
    }

    private void createDeck() {
        cards.clear();
        for (int j = 0; j <= 4; j++) {
            for (int i = 0; i <= 12; i++) {
                for (int k = 0; k < 2; k++) {
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
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if (cardViewer != null) {
//            findViewById(R.id.card).setBackground(cardViewer.getDrawable(this));
//        }
    }

    public void onClickNewGame(View view) {
        goTo(NewGameApp.class);
    }

    public void onClickContinue(View view) {
        goTo(ContinueApp.class);
    }

    public void onClickReference(View view) {
        goTo(ReferenceApp.class);
    }

    private void goTo(Class<?> cls) {
        if (!isPressed) {
            isPressed = true;
            Intent intent = new Intent(this, cls);
            startActivity(intent);
            overridePendingTransition(0, 0);
            new Handler().postDelayed(() -> isPressed = false, 250);
        }
    }
}
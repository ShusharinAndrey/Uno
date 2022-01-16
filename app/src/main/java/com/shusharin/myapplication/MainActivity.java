package com.shusharin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.shusharin.myapplication.card.CardViewer;
import com.shusharin.myapplication.selected_games.ContinueApp;
import com.shusharin.myapplication.selected_games.NewGameApp;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private boolean isPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReferenceApp.hideNavigationBar(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        getSupportActionBar().setCustomView(R.layout.title_layout);
        TextView text = findViewById(R.id.tvTitle);
        text.setText(R.string.title_activity_main);
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
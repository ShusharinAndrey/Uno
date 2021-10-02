package com.shusharin.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    View cardView;
    View cardView1;
    View cardView2;
    View cardView3;

    public static int getIdDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Card card = new Card(this,  SpecialCardWithBlack.TAKE_4_CARDS_AND_CHANGE_COLOR);
        Card card1 = new Card(this,  SpecialCardWithColor.CHANGE_ORDER_MOVE, Color.BLUE);
        Card card2 = new Card(this,  6, Color.GREEN);
        Card card3 = new Card(this,  2, Color.RED);

        cardView = findViewById(R.id.card);
        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView.setBackground(card.getDrawable());
        cardView1.setBackground(card1.getDrawable());
        cardView2.setBackground(card2.getDrawable());
        cardView3.setBackground(card3.getDrawable());
    }
}
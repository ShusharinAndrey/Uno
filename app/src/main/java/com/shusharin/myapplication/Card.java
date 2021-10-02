package com.shusharin.myapplication;

import static com.shusharin.myapplication.MainActivity.getIdDrawable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import lombok.AllArgsConstructor;
import lombok.Getter;

enum Color {
    BLUE {
        @Override
        int getIdDrawable() {
            return R.drawable.blue;
        }
    },
    GREEN {
        @Override
        int getIdDrawable() {
            return R.drawable.green;
        }
    },
    RED {
        @Override
        int getIdDrawable() {
            return R.drawable.red;
        }
    },
    YELLOW {
        @Override
        int getIdDrawable() {
            return R.drawable.yellow;
        }
    },
    BLACK {
        @Override
        int getIdDrawable() {
            return R.drawable.black;
        }
    };

    abstract int getIdDrawable();
}

@AllArgsConstructor
@Getter
enum SpecialCardWithColor {
    SKIPPING_MOVE(10),
    CHANGE_ORDER_MOVE(11),
    TAKE_2_CARDS(12);
    private final int id;
}

@AllArgsConstructor
@Getter
enum SpecialCardWithBlack {
    TAKE_4_CARDS_AND_CHANGE_COLOR(13),
    CHANGE_COLOR(14);
    private final int id;
}

public class Card {
    private final Context context;
    private final int id;
    private final Color color;

    public Card(Context context, int id, Color color) {
        this.context = context;

        this.id = Math.max(0, Math.min(id, 9));

        //Или лучше ошибку выдавть?
        this.color = color == Color.BLACK ? Color.BLUE : color;
    }

    public Card(Context context, SpecialCardWithColor specialCardWithColor, Color color) {
        this.context = context;

        this.id = specialCardWithColor.getId();

        //Или лучше ошибку выдавть?
        this.color = color == Color.BLACK ? Color.BLUE : color;
    }

    public Card(Context context, SpecialCardWithBlack specialCardWithBlack) {
        this.context = context;

        this.id = specialCardWithBlack.getId();

        //Или лучше ошибку выдавть?
        this.color = Color.BLACK;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    Drawable getDrawable() {
        return new LayerDrawable(new Drawable[]{
                context.getResources().getDrawable(color.getIdDrawable(), context.getTheme()),
                context.getResources().getDrawable(R.drawable.border, context.getTheme()),
                context.getResources().getDrawable(getIdDrawable(context, "card_" + id), context.getTheme())});
    }

}
package com.shusharin.myapplication;

import androidx.annotation.NonNull;

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
enum CardWithColor {
    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
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

@Getter
public class Card {
    private final int id;
    private final Color color;

    public Card(@NonNull CardWithColor cardWithColor, Color color) {

        this.id = cardWithColor.getId();

        //Или лучше ошибку выдавать?
        this.color = color == Color.BLACK ? Color.BLUE : color;
    }

    public Card(@NonNull SpecialCardWithBlack specialCardWithBlack) {

        this.id = specialCardWithBlack.getId();

        //Или лучше ошибку выдавать?
        this.color = Color.BLACK;
    }

}
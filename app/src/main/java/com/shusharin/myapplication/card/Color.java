package com.shusharin.myapplication.card;

import com.shusharin.myapplication.R;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Color {
    BLUE(0) {
        @Override
        int getIdDrawable() {
            return R.drawable.blue;
        }
    },
    GREEN(1) {
        @Override
        int getIdDrawable() {
            return R.drawable.green;
        }
    },
    RED(2) {
        @Override
        int getIdDrawable() {
            return R.drawable.red;
        }
    },
    YELLOW(3) {
        @Override
        int getIdDrawable() {
            return R.drawable.yellow;
        }
    },
    BLACK(4) {
        @Override
        int getIdDrawable() {
            return R.drawable.black;
        }
    };

    abstract int getIdDrawable();

    private int number;
}

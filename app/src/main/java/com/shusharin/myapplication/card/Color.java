package com.shusharin.myapplication.card;

import com.shusharin.myapplication.R;

public enum Color {
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

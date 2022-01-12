package com.shusharin.myapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Conservation {
    private String name;
    private int numberPlayer = -1;
    private int quantityPlayer;
    private int numberName;
    private boolean isFinished;
    private boolean isContinue = false;
    private Modes mode;

    public Conservation(String name, int numberName, boolean isFinished, Modes mode) {
        this.name = name;
        this.numberName = numberName;
        this.isFinished = isFinished;
        this.mode = mode;
    }

    public Conservation(String name, int numberPlayer, int quantityPlayer, int numberName, boolean isFinished, Modes mode) {
        this.name = name;
        this.numberPlayer = numberPlayer;
        this.quantityPlayer = quantityPlayer;
        this.numberName = numberName;
        this.isFinished = isFinished;
        this.mode = mode;
    }

    public String getName() {
        if (numberName == 0) {
            return this.name;
        } else {
            return this.name + numberName;
        }
    }

    public String getNameNoNumber() {
        return this.name;
    }

    @AllArgsConstructor
    @Getter
    public enum Modes {
        SINGLE(0) {
            @Override
            public int getString() {
                return R.string.single;
            }
        },
        MULTIPLAYER(1) {
            @Override
            public int getString() {
                return R.string.multiplayer;
            }
        };

        public abstract int getString();

        private int number;
    }
}
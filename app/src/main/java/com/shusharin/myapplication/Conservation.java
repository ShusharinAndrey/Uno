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
    private Modes mode;

    public Conservation(String name, int numberName, boolean isFinished,Modes mode) {
        this.name = name;
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

    public enum Modes {
        SINGLE{
            @Override
            public int getString() {
                return R.string.single;
            }
        },
        MULTIPLAYER{
            @Override
            public int getString() {
                return R.string.multiplayer;
            }
        };
        public abstract int getString();}
}
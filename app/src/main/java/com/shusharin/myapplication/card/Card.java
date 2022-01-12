package com.shusharin.myapplication.card;

import androidx.annotation.NonNull;

import com.shusharin.myapplication.R;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class Card {
    private final int id;
    @Setter
    private Color color;

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
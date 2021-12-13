package com.shusharin.myapplication.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SpecialCardWithBlack {
    TAKE_4_CARDS_AND_CHANGE_COLOR(13),
    CHANGE_COLOR(14);
    private final int id;
}

package com.shusharin.myapplication.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public
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

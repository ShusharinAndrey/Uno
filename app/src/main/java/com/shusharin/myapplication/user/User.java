package com.shusharin.myapplication.user;

import com.shusharin.myapplication.card.CardViewer;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public abstract class User<T> {
    @Setter
    @Getter
    T cardsInHand;

}

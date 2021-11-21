package com.shusharin.myapplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Player
{
    private Vector<Card> hand_;

    public Player()
    {
        this.hand_ = new Vector<>();
    }

    private void sortColor()
    {
        Collections.sort(hand_, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getColor().toString().compareTo(o2.getColor().toString());
            }
        });
    }


}

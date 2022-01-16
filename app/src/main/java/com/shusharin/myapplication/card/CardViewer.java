package com.shusharin.myapplication.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.shusharin.myapplication.R;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class CardViewer implements Serializable {
    private final Card card;
    @Setter
    private boolean isAvailable;

    public CardViewer(Card card) {
        this.card = card;
        isAvailable = true;
    }

    public static int getIdDrawable(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawable(Context context) {
        return new LayerDrawable(new Drawable[]{
                context.getResources().getDrawable(card.getColor().getIdDrawable(), context.getTheme()),
                context.getResources().getDrawable(R.drawable.border, context.getTheme()),
                context.getResources().getDrawable(getIdDrawable(context, "card_" + card.getId()), context.getTheme())});
    }
}

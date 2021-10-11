package com.shusharin.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private final List<CardViewer> cards;

    CardAdapter(Activity context, List<CardViewer> cards) {
        this.cards = cards;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewInGroup = inflater.inflate(R.layout.card, parent, false);
        ViewHolder holder = new ViewHolder(viewInGroup);
        viewInGroup.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition();
            if (cards.get(position).isAvailable()) {
                CardsDeck.toCard(position);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardViewer card = cards.get(position);
        holder.card.setBackground(card.getDrawable(context));
        if (!card.isAvailable()) {
            holder.available.setVisibility(View.VISIBLE);
        }
        else {
            holder.available.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
        final View card;
        final View available;

        ViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.card);
            available = view.findViewById(R.id.not_available);
        }
    }
}

package com.shusharin.myapplication.selected_games;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.R;

import java.util.List;

public class ConservationAdapter extends RecyclerView.Adapter<ConservationAdapter.ViewHolder> {
    private final LayoutInflater inflater;
    private List<Conservation> conservations;
    private final Activity context;

    ConservationAdapter(Activity context, List<Conservation> conservations) {
        this.conservations = conservations;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewInGroup = inflater.inflate(R.layout.list_item, parent, false);
        ViewHolder holder = new ViewHolder(viewInGroup);
        View Delete = holder.delete;
        viewInGroup.setOnClickListener(view1 -> {
            int position = holder.getAdapterPosition();
            Conservation conservation = conservations.get(position);
            ContinueApp.toConservation(conservation);
        });

        Delete.setOnClickListener(Delete1 -> {
            Conservation conservation =conservations.get(holder.getAdapterPosition());
            SharedPreferences preferences = context.getSharedPreferences(conservation.getName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            ContinueApp.conservations.remove(conservation);
            conservations = ContinueApp.conservations;
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Conservation conservation = conservations.get(position);
        holder.nameView.setText(conservation.getName());
    }

    @Override
    public int getItemCount() {
        return conservations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView;
        final TextView playerView;
        final TextView fieldView;
        final View delete;
        final View layout;

        ViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.NameGame);
            playerView = view.findViewById(R.id.NumberPlayer);
            fieldView = view.findViewById(R.id.NumberField);
            delete = view.findViewById(R.id.remote);
            layout = view.findViewById(R.id.All_item);
        }
    }

}


package com.shusharin.myapplication.selected_games;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.R;
import com.shusharin.myapplication.ReferenceApp;
import com.shusharin.myapplication.mode.MultiPlayerApp;
import com.shusharin.myapplication.mode.SinglePlayerApp;

import java.util.ArrayList;
import java.util.Objects;

public class ContinueApp extends AppCompatActivity {
    public static ArrayList<Conservation> conservations = new ArrayList<>();
    private static Conservation conservation;
    private static AlertDialog dialog;
    private static boolean isDialogShow = false;
    protected boolean isPressed;

    private static void showDialog() {
        isDialogShow = true;
        dialog.show();
    }

    public static void toConservation(Conservation conservation) {
        ContinueApp.conservation = conservation;
        showDialog();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateDialog();
        setContentView(R.layout.activity_continue);
        RecyclerView listConservation = findViewById(R.id.List);
        final ConservationAdapter adapterConservationsTwoPlayerOneField = new ConservationAdapter(this, conservations);
        listConservation.setAdapter(adapterConservationsTwoPlayerOneField);
        ReferenceApp.hideNavigationBar(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        getSupportActionBar().setCustomView(R.layout.title_layout);
        TextView text = findViewById(R.id.tvTitle);
        text.setText(R.string.title_continue);
        adapterConservationsTwoPlayerOneField.notifyDataSetChanged();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        saveConservation();
        if (sharedPreferences.contains("SIZE")) {
            conservations.clear();
            for (int i = 0; i < sharedPreferences.getInt("SIZE", conservations.size()); i++) {
                conservations.add(new Conservation(
                        sharedPreferences.getString(getString(R.string.Game) + i, ""),
                        sharedPreferences.getInt("NUMBER_PLAYER" + i, 0),
                        sharedPreferences.getInt("QUANTITY_PLAYER" + i, 0),
                        sharedPreferences.getInt("NUMBER_NAME" + i, 0),
                        sharedPreferences.getBoolean("IS_FINISHED" + i, true),
                        Conservation.Modes.values()[sharedPreferences.getInt("MODE_NUMBER" + i, 0)]
                       )
                );
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(0, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isDialogShow) {
            showDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveConservation();
    }

    private void saveConservation() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("SIZE", conservations.size());
        for (int i = 0; i < conservations.size(); i++) {
            edit.putString(getString(R.string.Game) + i, conservations.get(i).getNameNoNumber());
            edit.putInt("NUMBER_NAME" + i, conservations.get(i).getNumberName());
            edit.putInt("NUMBER_PLAYER" + i, conservations.get(i).getNumberPlayer());
            edit.putInt("QUANTITY_PLAYER" + i, conservations.get(i).getQuantityPlayer());
            edit.putBoolean("IS_FINISHED" + i, conservations.get(i).isFinished());
            edit.putInt("MODE_NUMBER" + i, conservations.get(i).getMode().getNumber());
        }
        edit.apply();
    }

    @SuppressLint({"InflateParams", "UseCompatLoadingForDrawables", "DefaultLocale"})
    private void onCreateDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_game_name, null);


        viewDialog.setBackground(getResources().getDrawable(R.color.grey, getTheme()));
        builder.setView(viewDialog);
        Button positive = viewDialog.findViewById(R.id.positive);
        positive.setText(R.string.open);
        positive.setOnClickListener(arg0 -> {
            if (!isPressed) {
                isPressed = true;
//                MainActivity.saveName = conservation.getName();
                Intent intent;
                if (conservation.getMode() == Conservation.Modes.SINGLE) {
                    intent = new Intent(this, SinglePlayerApp.class);
                } else {
                    intent = new Intent(this, MultiPlayerApp.class);
                }

                intent.putExtra("GAME", conservation.getName());
                intent.putExtra("NUMBER_CONSERVATION", ContinueApp.conservations.indexOf(conservation));
                conservation.setContinue(true);

                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
                isDialogShow = false;
                new Handler().postDelayed(() -> isPressed = false, 250);
            }
        });
        dialog = builder.create();
        viewDialog.findViewById(R.id.negative).setOnClickListener(arg0 -> {
            if (!isPressed) {
                isPressed = true;
                dialog.dismiss();
                isDialogShow = false;
                new Handler().postDelayed(() -> isPressed = false, 100);
            }
        });

        dialog.setOnShowListener(arg0 -> {
            TextView nameGameText = viewDialog.findViewById(R.id.nameGame);
            TextView modeText = viewDialog.findViewById(R.id.mode);
            TextView numberPlayerText = viewDialog.findViewById(R.id.player);
            nameGameText.setText(String.format(getString(R.string.Party) + getString(R.string.enter_stoke), conservation.getName()));
            numberPlayerText.setText(String.format(getString(R.string.Players), conservation.getQuantityPlayer()));
            modeText.setText(conservation.getMode().getString());
        });
        dialog.setOnCancelListener(arg0 -> closeDialog(viewDialog));
        dialog.setOnDismissListener(arg0 -> closeDialog(viewDialog));
    }

    private void closeDialog(View viewDialog) {
        isDialogShow = false;
    }

}

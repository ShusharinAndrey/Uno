package com.shusharin.myapplication.selected_games;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.shusharin.myapplication.Conservation;
import com.shusharin.myapplication.MainActivity;
import com.shusharin.myapplication.R;
import com.shusharin.myapplication.mode.MultiPlayerApp;
import com.shusharin.myapplication.mode.SinglePlayerApp;


public class NewGameApp extends MainActivity {
    private static Conservation conservation;
    private static boolean isDialogShow = false;
    protected Button singlePlayer;
    protected Button multiPlayer;
    protected EditText editNameGame;
    private AlertDialog dialog;
    private Intent intent;
    private boolean isPressed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateDialog();
        setContentView(R.layout.activity_new_game);
        singlePlayer = findViewById(R.id.singlePlayer);
        multiPlayer = findViewById(R.id.multiPlayer);
        editNameGame = findViewById(R.id.editTextName);
        TextView text = findViewById(R.id.tvTitle);
        text.setText(R.string.title_activity_new_game);
    }

    protected void onStart() {
        super.onStart();
        editNameGame.setVisibility(View.VISIBLE);
        singlePlayer.setVisibility(View.VISIBLE);
        multiPlayer.setVisibility(View.VISIBLE);
        if (isDialogShow) {
            showDialog();
        }
    }


    @SuppressLint({"InflateParams", "UseCompatLoadingForDrawables", "NonConstantResourceId"})
    private void onCreateDialog() {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.dialog_game_name, null);
        viewDialog.setBackground(getResources().getDrawable(R.color.grey, getTheme()));
        builder.setView(viewDialog);

        viewDialog.findViewById(R.id.positive).setOnClickListener(arg0 -> {
            ContinueApp.conservations.add(conservation);
            intent.putExtra("GAME", conservation.getName());
            intent.putExtra("NUMBER_CONSERVATION", ContinueApp.conservations.indexOf(conservation));
            startActivity(intent);
            finish();
            overridePendingTransition(0, 0);
            isDialogShow = false;
            new Handler().postDelayed(() -> isPressed = false, 100);
        });
        dialog = builder.create();
        RadioGroup radioGroup = viewDialog.findViewById(R.id.quantityPlayers);
        TextView numberPlayerText = viewDialog.findViewById(R.id.player);
        radioGroup.setVisibility(View.VISIBLE);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.twoPlayer:
                    conservation.setQuantityPlayer(2);
                    break;
                case R.id.threePlayer:
                    conservation.setQuantityPlayer(3);
                    break;
                case R.id.fourPlayer:
                    conservation.setQuantityPlayer(4);
                    break;

                default:
                    break;
            }
            numberPlayerText.setText(String.format(getString(R.string.Players), conservation.getQuantityPlayer()));
        });

        viewDialog.findViewById(R.id.negative).setOnClickListener(arg0 -> {
            dialog.dismiss();
            isDialogShow = false;
            new Handler().postDelayed(() -> isPressed = false, 100);
        });

        dialog.setOnShowListener(arg0 -> {
            TextView nameGameText = viewDialog.findViewById(R.id.nameGame);
            TextView modeText = viewDialog.findViewById(R.id.mode);
            nameGameText.setText(String.format(getString(R.string.Party) + getString(R.string.enter_stoke), conservation.getName()));
            if (conservation.getMode() == Conservation.Modes.SINGLE) {
                numberPlayerText.setVisibility(View.GONE);
                radioGroup.setVisibility(View.GONE);
            } else {
                numberPlayerText.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                numberPlayerText.setText(String.format(getString(R.string.Players), 2));
            }
            modeText.setText(conservation.getMode().getString());
        });
        dialog.setOnCancelListener(arg0 -> closeDialog());
        dialog.setOnDismissListener(arg0 -> closeDialog());
    }

    private void closeDialog() {
        isDialogShow = false;
        new Handler().postDelayed(() -> isPressed = false, 100);
    }

    public void showDialog() {
        isDialogShow = true;
        dialog.show();
    }

    public void onClickSinglePlayer(View view) {
        setMode(Conservation.Modes.SINGLE, SinglePlayerApp.class);
    }

    public void onClickMultiPlayer(View view) {
        setMode(Conservation.Modes.MULTIPLAYER, MultiPlayerApp.class);
    }

    private void setMode(Conservation.Modes mode, Class<?> cls) {
        if (!isPressed) {
            isPressed = true;
            intent = new Intent(NewGameApp.this, cls);
            String saveName = !editNameGame.getText().toString().equals("") ? editNameGame.getText().toString() : editNameGame.getHint().toString();
            int number = -1;
            for (int i = 0; i < ContinueApp.conservations.size(); i++) {
                if (saveName.equals(ContinueApp.conservations.get(i).getNameNoNumber())) {
                    if (number < ContinueApp.conservations.get(i).getNumberName()) {
                        number = ContinueApp.conservations.get(i).getNumberName();
                    }
                }
            }
            conservation = new Conservation(saveName, number == -1 ? 0 : number + 1, false, mode);
            conservation.setQuantityPlayer(2);
            showDialog();
        }
    }

}

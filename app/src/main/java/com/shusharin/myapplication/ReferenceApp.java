package com.shusharin.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ReferenceApp extends AppCompatActivity {

    private TextView title;
    private TextView text;

    public static void hideNavigationBar(@NonNull final Window window, @NonNull ActionBar actionBar) {
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = window.getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (visibility -> {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        new Handler().postDelayed(() -> {
                            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                        }, 3500);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference);
        openOptionsMenu();
        hideNavigationBar(getWindow(), Objects.requireNonNull(getSupportActionBar()));
        title = findViewById(R.id.title);
        text = findViewById(R.id.text);
        if (savedInstanceState != null) {
            title.setText(savedInstanceState.getCharSequence("TITLE"));
            text.setText(savedInstanceState.getCharSequence("TEXT"));
        }
        getSupportActionBar().setCustomView(R.layout.title_layout);
        TextView text = findViewById(R.id.tvTitle);
        text.setText(R.string.uno_game_rules);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reference, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
//            Здесь надо будет вставить правила в формате
            case R.id.Components:
                title.setText(R.string.components);
                text.setText(R.string.components_text);
                return true;
            case R.id.Object_of_the_game:
                title.setText(R.string.object_of_the_game);
                text.setText(R.string.object_of_the_game_text);
                return true;
            case R.id.Setup:
                title.setText(R.string.setup);
                text.setText(R.string.setup_text);
                return true;
            case R.id.Draw_2_card:
                title.setText(R.string.draw_2_card);
                text.setText(R.string.draw_2_card_text);
                return true;
            case R.id.Reverse_card:
                title.setText(R.string.reverse_card);
                text.setText(R.string.reverse_card_text);
                return true;
            case R.id.Skip_card:
                title.setText(R.string.skip_card);
                text.setText(R.string.skip_card_text);
                return true;
            case R.id.Wild_card:
                title.setText(R.string.wild_card);
                text.setText(R.string.wild_card_text);
                return true;
            case R.id.Wild_draw_4_card:
                title.setText(R.string.wild_draw_4_card);
                text.setText(R.string.wild_draw_4_card_text);
                return true;
            case R.id.Game_play:
                title.setText(R.string.game_play);
                text.setText(R.string.game_play_text);
                return true;
//             Где всместо Move_rules названия заголовков/подзаголовков
//            сами заголовки ещё нало будет создать в файле menu/reference.xml
            case android.R.id.home:
                finish();
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("TITLE", title.getText());
        outState.putCharSequence("TEXT", text.getText());
    }
}

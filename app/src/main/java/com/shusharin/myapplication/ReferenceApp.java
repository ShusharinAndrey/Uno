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
        text.setText(R.string.title_activity_reference);
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
            case R.id.Move_rules:
                title.setText(R.string.Move_rules);
                text.setText(R.string.Move_rules_Text);
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

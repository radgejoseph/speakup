package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlateRatingsActivity extends AppCompatActivity {
    private View decorView;
    private TextView textPlate;
    private Button to_rateme_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plate_ratings_activity);


        to_rateme_button = findViewById(R.id.to_rateme_button);

        to_rateme_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String plate = textPlate.getText().toString();
               Intent intent = new Intent(PlateRatingsActivity.this, RateMeActivity.class);
               intent.putExtra("selected_plate", plate);
               startActivity(intent);

           }
        });

        android.widget.ImageView back_to = findViewById(R.id.back_to);
        back_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility ==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });

        if (getIntent().hasExtra("selected_plate")) {
            ListItem listItem = getIntent().getParcelableExtra("selected_plate");
            String plate = listItem.getPlateL();

            textPlate = findViewById(R.id.plate_number);
            textPlate.setText(plate);
        }

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    public void openRateMeActivity() {
        Intent intent = new Intent(PlateRatingsActivity.this, RateMeActivity.class);
        startActivity(intent);
    }

}
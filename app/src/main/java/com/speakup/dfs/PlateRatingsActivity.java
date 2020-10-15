package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private TextView textPlate;
    private Button to_rateme_button;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plate_ratings_activity);
        setTitle("");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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


        if (getIntent().hasExtra("selected_plate")) {
            ListItem listItem = getIntent().getParcelableExtra("selected_plate");
            String plate = listItem.getPlateL();

            textPlate = findViewById(R.id.plate_number);
            textPlate.setText(plate);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void openRateMeActivity() {
        Intent intent = new Intent(PlateRatingsActivity.this, RateMeActivity.class);
        startActivity(intent);
    }

}
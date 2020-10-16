package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class RateMeActivity extends AppCompatActivity {

    private TextView textPlate;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_me);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        android.widget.ImageView backBut = findViewById(R.id.advance_option);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTabbedActivity();
            }
        });


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String plate = getIntent().getStringExtra("selected_plate");
        textPlate = findViewById(R.id.plate_number);
        textPlate.setText(plate);


    }


    public void openTabbedActivity() {
        String plate = textPlate.getText().toString();
        Intent intent = new Intent(RateMeActivity.this, TabbedActivity.class);
        intent.putExtra("selected_plate", plate);
        startActivity(intent);
//        Intent intent = new Intent(this, TabbedActivity.class);
//        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
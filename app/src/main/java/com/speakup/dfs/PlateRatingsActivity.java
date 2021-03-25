package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//

public class PlateRatingsActivity extends AppCompatActivity {

    private TextView textPlate;
    private TextView textVehicle;
    private Button to_rateme_button;
    Toolbar toolbar;

    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//

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
               String vehicle = textVehicle.getText().toString();
               //String vehicle = textVehicle.getText().toString();
               Intent intent = new Intent(PlateRatingsActivity.this, RateMeActivity.class);
               intent.putExtra("selected_plate", plate);
               intent.putExtra("vehicle", vehicle);
               startActivity(intent);

           }
        });

        if (getIntent().hasExtra("selected_plate")) {
            ListItem listItem = getIntent().getParcelableExtra("selected_plate");
            String plate = listItem.getPlateL();
            String vehicle = listItem.getVehicleL();

            textPlate = findViewById(R.id.txtPlate_number);
            textPlate.setText(plate);
            textVehicle = findViewById(R.id.vehicle_type_holder);
            textVehicle.setText(vehicle);
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

    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
    // =========================================== THIS CLASS IS NOT USED AT THE MOMENT ====================================================//
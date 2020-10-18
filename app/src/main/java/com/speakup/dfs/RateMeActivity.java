package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateMeActivity extends AppCompatActivity {

    private TextView textPlate;
    TextView rateCount;
    EditText review;
    Toolbar toolbar;
    RatingBar ratingBar;
    Button submit_button;
    int rateValue; String temp;

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


        ratingBar = findViewById(R.id.ratingBar);
        review = findViewById(R.id.review_box);
        submit_button = findViewById(R.id.submit_button);
        rateCount = findViewById(R.id.ratecount);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String plate = getIntent().getStringExtra("selected_plate");
        textPlate = findViewById(R.id.plate_number);
        textPlate.setText(plate);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = (int) ratingBar.getRating();

                if (rateValue <= 1 && rateValue > 0)
                    rateCount.setText("Bad " +rateValue+ "/5");
                else if (rateValue <= 2 && rateValue > 1)
                    rateCount.setText("OK " +rateValue+ "/5");
                else if (rateValue <= 3 && rateValue > 2)
                    rateCount.setText("Good " +rateValue+ "/5");
                else if (rateValue <= 4 && rateValue > 3)
                    rateCount.setText("Very Good " +rateValue+ "/5");
                else if (rateValue <= 5 && rateValue > 4)
                    rateCount.setText("Best " +rateValue+ "/5");
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp = review.getText().toString();
                Toast.makeText(RateMeActivity.this, "Your Rating:\n" + rateValue + "\n" + temp, Toast.LENGTH_SHORT).show();
                review.setText("");
                ratingBar.setRating(0);
                rateCount.setText("");
            }
        });


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
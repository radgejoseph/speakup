package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RateMeActivity extends AppCompatActivity {
    private static String URL_REVIEW = "http://192.168.1.117/SpeakUP/review.php";

    private TextView textPlate;
    private TextView textVehicle;
    private TextView narrative;
    TextView rateCount;
    Toolbar toolbar;
    RatingBar ratingBar;
    Button submit_button;
    String getId;
    SessionManager sessionManager;
    float rateValue;

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
        //narrative = findViewById(R.id.review_box);
        rateCount = findViewById(R.id.ratecount);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String plate = getIntent().getStringExtra("selected_plate");
        String vehicle = getIntent().getStringExtra("vehicle");
        textPlate = findViewById(R.id.plate_number);
        textPlate.setText(plate);
        textVehicle = findViewById(R.id.vehicle_type_holder);
        textVehicle.setText(vehicle);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue = ratingBar.getRating();

                if (rateValue == 1){
                    rateCount.setText("1");
                }
                else if (rateValue == 2) {
                    rateCount.setText("2");
                }
                else if (rateValue == 3) {
                    rateCount.setText("3");
                }
                else if (rateValue == 4) {
                    rateCount.setText("4");
                }
                else if (rateValue == 5) {
                    rateCount.setText("5");
                }
            }
        });

        sessionManager = new SessionManager(RateMeActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        narrative = findViewById(R.id.review_box);

        submit_button = findViewById(R.id.submit_button_review);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewSubmit();
            }
        });

    }


    private void ReviewSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(RateMeActivity.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();
        //progress.setVisibility(View.VISIBLE);
        //submit_button.setVisibility(View.GONE);
        //temp_rating = rateCount.getText().toString().trim();

        final String textPlate = this.textPlate.getText().toString().trim();
        final String textVehicle = this.textVehicle.getText().toString().trim();
        final String narrative = this.narrative.getText().toString().trim();
        final String ratings = this.rateCount.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REVIEW,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                //openRegisterComplete();
                                progressDialog.dismiss();
                                Toast.makeText(RateMeActivity.this,"Submit Success!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RateMeActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            //progress.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            //reg_button.setVisibility(View.VISIBLE);
                            Toast.makeText(RateMeActivity.this,"Submit Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progress.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        //reg_button.setVisibility(View.VISIBLE);
                        Toast.makeText(RateMeActivity.this,"Submit Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body_plate", textPlate);
                params.put("vehicle", textVehicle);
                params.put("narrative", narrative);
                params.put("ratings", ratings);
                params.put("user_id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RateMeActivity.this);
        requestQueue.add(stringRequest);
    }


    public void openTabbedActivity() {
        String plate = textPlate.getText().toString();
        String vehicle = textVehicle.getText().toString();
        Intent intent = new Intent(RateMeActivity.this, TabbedActivity.class);
        intent.putExtra("selected_plate", plate);
        intent.putExtra("vehicle", vehicle);
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
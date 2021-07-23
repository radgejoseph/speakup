package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
//    private static String URL_REVIEW = "http://speakupadnu.000webhostapp.com/speakupmobile/review.php";
    private static String URL_REVIEW = "http://speakupadnu.000webhostapp.com/speakupmobile/review.php";

    private TextView textPlate;
    private TextView textVehicle;
    private EditText narrative;
    TextView rateCount, rateCountText;
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
                openComplaintActivity();
            }
        });


        ratingBar = findViewById(R.id.ratingBar);
        rateCountText = findViewById(R.id.ratecounttext);
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
                    rateCountText.setText("Terrible Driver \uD83D\uDE21");
                    rateCount.setText("1");
                }
                else if (rateValue == 2) {
                    rateCountText.setText("Bad Driver \uD83D\uDE1E");
                    rateCount.setText("2");
                }
                else if (rateValue == 3) {
                    rateCountText.setText("Okay Driver \uD83D\uDE10");
                    rateCount.setText("3");
                }
                else if (rateValue == 4) {
                    rateCountText.setText("Good Driver \uD83D\uDE0A");
                    rateCount.setText("4");
                }
                else if (rateValue == 5) {
                    rateCountText.setText("Great Driver \uD83D\uDE04");
                    rateCount.setText("5");
                }
            }
        });

        sessionManager = new SessionManager(RateMeActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        narrative = findViewById(R.id.review_box);

        narrative.setImeOptions(EditorInfo.IME_ACTION_DONE);
        narrative.setRawInputType(InputType.TYPE_CLASS_TEXT);

        submit_button = findViewById(R.id.submit_button_review);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String narrative_r = narrative.getText().toString().trim();

                if (!narrative_r.isEmpty() && ratingBar.getRating() != 0.0){
                    ReviewSubmit();
                }
                else {
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(RateMeActivity.this);
                    builder1.setMessage("Both Review and Rating is Required");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });


                    androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                    //narrative.setError("Your Review and Rating is Required");
                }
            }
        });

    }

    private void ReviewSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(RateMeActivity.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

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
                                progressDialog.dismiss();
                                Toast.makeText(RateMeActivity.this,"Rating submitted successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RateMeActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(RateMeActivity.this,"Submit Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
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

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


//    public void openTabbedActivity() {
//        String plate = textPlate.getText().toString();
//        String vehicle = textVehicle.getText().toString();
//        Intent intent = new Intent(RateMeActivity.this, TabbedActivity.class);
//        intent.putExtra("selected_plate", plate);
//        intent.putExtra("vehicle", vehicle);
//        startActivity(intent);
//    }

    public void openComplaintActivity() {
        String plate = textPlate.getText().toString();
        String vehicle = textVehicle.getText().toString();
        Intent intent = new Intent(RateMeActivity.this, ComplaintActivity.class);
        intent.putExtra("selected_plate", plate);
        intent.putExtra("vehicle", vehicle);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
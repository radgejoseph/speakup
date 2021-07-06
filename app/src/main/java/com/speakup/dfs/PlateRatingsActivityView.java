package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateRatingsActivityView extends AppCompatActivity {

    //    private static final String URL_PLATE_LIST = "http://speakupnaga.herokuapp.com/speakup/plate_reviews.php";
    private static final String URL_PLATE_LIST = "http://192.168.1.139/SpeakUp/plate_reviews.php";

    RecyclerView recyclerView2;
    List<ListItemPlateReviews> itemListPlate;

    private TextView textPlate;
    private TextView textVehicle;
    private Button to_rateme_button;
    Toolbar toolbar;

    String body_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plate_ratings_activity_view);
        setTitle("");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        itemListPlate = new ArrayList<>();

        recyclerView2 = findViewById(R.id.recyclerview_list_reports);
        recyclerView2.bringToFront();
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        to_rateme_button = findViewById(R.id.to_rateme_button);

        to_rateme_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plate = textPlate.getText().toString();
                String vehicle = textVehicle.getText().toString();
                Intent intent = new Intent(PlateRatingsActivityView.this, RateMeActivity.class);
                intent.putExtra("selected_plate", plate);
                intent.putExtra("vehicle", vehicle);
                startActivity(intent);

            }
        });

        if (getIntent().hasExtra("selected_plate")) {
            ListItem listItem = getIntent().getParcelableExtra("selected_plate");
            String plate = listItem.getPlateL();
            String vehicle = listItem.getVehicleL();

            textPlate = findViewById(R.id.plate_number);
            textPlate.setText(plate);
            textVehicle = findViewById(R.id.vehicle_type_holder);
            textVehicle.setText(vehicle);

        }
        loadList();

        body_plate = this.textPlate.getText().toString().trim();
    }

    private void loadList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, URL_PLATE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                itemListPlate.add(new ListItemPlateReviews(
                                        object.getString("username"),
                                        object.getInt("ratings"),
                                        object.getString("narrative")
                                ));
                            }

                            ListItemPlateReviewAdapter  listItemPlateReviewAdapter = new ListItemPlateReviewAdapter(PlateRatingsActivityView.this, itemListPlate);
                            recyclerView2.setAdapter(listItemPlateReviewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PlateRatingsActivityView.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body_plate", body_plate);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class ColorumFormActivity extends AppCompatActivity {
    private static String URL_COLORUM = "http://192.168.1.138/SpeakUP/colorum.php";

    Toolbar toolbar;
    private TextView textPlate;
    //private TextView textOperator;
    private AutoCompleteTextView textVehicle;
    String[] vehicles = { "jeep","taxicle","tricycle","taxi" };

    String getId;
    SessionManager sessionManager;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colorum_form_activity);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        textPlate = findViewById(R.id.plate_number);
        //textOperator = findViewById(R.id.operator_name);

        //String vehicle = getIntent().getStringExtra("vehicle");
        //textVehicle = findViewById(R.id.vehicle_type_holder);
        //textVehicle.setText(vehicle);
        //Create Array Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, vehicles);
        //Find TextView control
        textVehicle = findViewById(R.id.vehicle_type_holder);
        //Set the number of characters the user must type before the drop down list is shown
        textVehicle.setThreshold(1);
        //Set the adapter
        textVehicle.setAdapter(adapter);

        sessionManager = new SessionManager(ColorumFormActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        submit_button = findViewById(R.id.submit_colorum);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorumSubmit();
            }
        });
    }

    private void ColorumSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(ColorumFormActivity.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String textPlate = this.textPlate.getText().toString().trim();
        final String textVehicle = this.textVehicle.getText().toString().trim();
        //final String textOperator = this.textOperator.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COLORUM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(ColorumFormActivity.this,"Colorum Plate submitted successfully!"+"\n"+"You Can now Search the plate", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ColorumFormActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ColorumFormActivity.this,"Submit Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ColorumFormActivity.this,"Submit Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body_plate", textPlate);
                //params.put("name", textOperator);
                params.put("vehicle", textVehicle);
                params.put("user_id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ColorumFormActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
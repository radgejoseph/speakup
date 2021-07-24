package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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

public class ColorumFormActivity extends AppCompatActivity {
    private static String URL_COLORUM = "http://speakupadnu.000webhostapp.com/speakupmobile/colorum.php";

    Toolbar toolbar;
    private TextView textPlate;
    private String selectedItemText;
    private TextView textVehicle;
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

        sessionManager = new SessionManager(ColorumFormActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        String vehicle = getIntent().getStringExtra("vehicle");
        textVehicle = findViewById(R.id.vehicle_type_holder);
        textVehicle.setText(vehicle);

        submit_button = findViewById(R.id.submit_colorum);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textPlate_r = textPlate.getText().toString().trim();
                if (!textPlate_r.isEmpty()){
                    ColorumSubmit();
                }
                else {
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ColorumFormActivity.this);
                    builder1.setMessage("Plate and Type is Required!");
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
                }
            }
        });

                selectedItemText = textVehicle.getText().toString().trim();
                if (selectedItemText.equals("tricycle"))
                {
                    textPlate.setEnabled(true);
                    textPlate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                    textPlate.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                if (selectedItemText.equals("jeep"))
                {
                    textPlate.setEnabled(true);
                    textPlate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    textPlate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                if (selectedItemText.equals("taxicle"))
                {
                    textPlate.setEnabled(true);
                    textPlate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});
                    textPlate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                if (selectedItemText.equals("taxi"))
                {
                    textPlate.setEnabled(true);
                    textPlate.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                    textPlate.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
    }

    private void ColorumSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(ColorumFormActivity.this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String textPlate = this.textPlate.getText().toString().trim();
        final String textVehicle = this.selectedItemText.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COLORUM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(ColorumFormActivity.this,"New PUV record submitted succesfully!"+"\n"+"You Can now Search the plate", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ColorumFormActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ColorumFormActivity.this);
                            builder1.setMessage("Submit Error! Plate already exist!");
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
                params.put("vehicle", textVehicle);
                params.put("user_id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ColorumFormActivity.this);
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
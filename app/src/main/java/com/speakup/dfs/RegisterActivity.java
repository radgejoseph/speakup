package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText name, username, password, phone_number, email, address;
    private static String URL_REGIST = "http://192.168.1.138/SpeakUP/register.php";
//private static String URL_REGIST = "https://speakupadnu.000webhostapp.com/register.php";
//    private static String URL_REGIST = "https://speakup-app-apk.herokuapp.com/register.php";
    private Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        name = findViewById(R.id.fullname_text);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        phone_number = findViewById(R.id.phone_text);
        email = findViewById(R.id.email_text);
        address = findViewById(R.id.address_text);
        reg_button = findViewById(R.id.register_button);
        
        android.widget.ImageView backBut = findViewById(R.id.imagebackButton);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_r = name.getText().toString().trim();
                String username_r = username.getText().toString().trim();
                String password_r = password.getText().toString().trim();
                String mobile_r = phone_number.getText().toString().trim();
                String email_r = email.getText().toString().trim();
                String address_r = address.getText().toString().trim();


                if (!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
                        && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()) {

                    Regist();
                }
                else {
                    name.setError("Full Name is Required");
                    username.setError("Username is Required");
                    password.setError("Password is Required");
                    phone_number.setError("Mobile Number is Required");
                    email.setError("Email Address is Required");
                }
            }
        });
    }

    private void Regist() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String name = this.name.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String phone_number = this.phone_number.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String address = this.address.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                openRegisterComplete();
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();
                            }
                            else if (success.equals("0")) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Register Error! " + response, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            reg_button.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this,"Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        reg_button.setVisibility(View.VISIBLE);
                        Toast.makeText(RegisterActivity.this,"Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
                params.put("password", password);
                params.put("phone_number", phone_number);
                params.put("email", email);
                params.put("address", address);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

    public void openRegisterComplete() {
        Intent intent = new Intent(this, RegisterComplete.class);
        startActivity(intent);
    }

}
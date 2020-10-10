package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
    private View decorView;

    private EditText name, username, password, mobile, email, address;
    private ProgressBar progress;
    private static String URL_REGIST = "http://192.168.1.146/SpeakUP/register.php";
    private Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        name = findViewById(R.id.fullname_text);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        mobile = findViewById(R.id.phone_text);
        email = findViewById(R.id.email_text);
        address = findViewById(R.id.adress_text);
        progress = findViewById(R.id.progress);
        reg_button = findViewById(R.id.register_button);

//        name.addTextChangedListener(RegisterTextWatcher);
//        username.addTextChangedListener(RegisterTextWatcher);
//        password.addTextChangedListener(RegisterTextWatcher);
//        mobile.addTextChangedListener(RegisterTextWatcher);
//        email.addTextChangedListener(RegisterTextWatcher);
        //address.addTextChangedListener(RegisterTextWatcher);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility ==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
        
        android.widget.ImageView backBut = findViewById(R.id.imagebackButton);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Button button = findViewById(R.id.register_button);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_r = name.getText().toString().trim();
                String username_r = username.getText().toString().trim();
                String password_r = password.getText().toString().trim();
                String mobile_r = mobile.getText().toString().trim();
                String email_r = email.getText().toString().trim();
                //String address_r = address.getText().toString().trim();

                if (!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
                        && !mobile_r.isEmpty() && !email_r.isEmpty()/* && !address_r.isEmpty()*/) {

                    Regist();
                    openRegisterComplete();
                }
                else {
                    name.setError("Full Name is Required");
                    username.setError("Username is Required");
                    password.setError("Password is Required");
                    mobile.setError("Mobile Number is Required");
                    email.setError("Email Address is Required");
                }
            }
        });
    }


    private void Regist() {
        progress.setVisibility(View.VISIBLE);
        reg_button.setVisibility(View.GONE);

        final String name = this.name.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String mobile = this.mobile.getText().toString().trim();
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
                                Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                            reg_button.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this,"Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
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
                params.put("mobile", mobile);
                params.put("email", email);
                params.put("address", address);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
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
package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //private View decorView;

    private EditText username, password;
    private Button l_button, tr_button;
    private ProgressBar progress;
    private static String URL_LOGIN = "http://192.168.1.117/SpeakUP/login.php";//"http://half-a-dozen-school.000webhostapp.com/login.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sessionManager = new SessionManager(this);

        progress = findViewById(R.id.progress);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        l_button = findViewById(R.id.login_button);
        tr_button = findViewById(R.id.to_register_button);

//        decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
//            @Override
//            public void onSystemUiVisibilityChange(int visibility) {
//                if (visibility ==0)
//                    decorView.setSystemUiVisibility(hideSystemBars());
//            }
//        });

        //Button button = findViewById(R.id.to_register_button);
        tr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

        //Button l_button = findViewById(R.id.login_button);
        l_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_l = username.getText().toString().trim();
                String password_l = password.getText().toString().trim();

                if (!username_l.isEmpty() && !password_l.isEmpty()) {
                    Login(username_l, password_l);
                }
                else {
                    username.setError("Please insert your username");
                    password.setError("Please insert your password");
                }
            }
        });
    }

    private void Login(final String username, final String password) {

        progress.setVisibility(View.VISIBLE);
        l_button.setVisibility(View.GONE);
        tr_button.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    //String username = object.getString("username").trim();
                                    //String password = object.getString("password").trim();
                                    //String mobile = object.getString("mobile").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    //String address = object.getString("address").trim();
                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    //intent.putExtra("username", username);
                                    //intent.putExtra("password", password);
                                    //intent.putExtra("mobile", mobile);
                                    intent.putExtra("email", email);
                                    //intent.putExtra("id", id);
                                    //intent.putExtra("address", address);
                                    startActivity(intent);

                                    Toast.makeText(MainActivity.this, "Success Login! \nName : "
                                            +name+"\nEmail : "+email, Toast.LENGTH_LONG).show();

//                                    Toast.makeText(MainActivity.this, "Success Login! \nName : "
//                                            +name+"\nUsername : "+username+"\nPassword : "+password+
//                                            "\nMobile : "+mobile+"\nEmail : "+email+"\nAddress : "+address, Toast.LENGTH_LONG).show();
                                    progress.setVisibility(View.GONE);
                                    openHomeActivity();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                            l_button.setVisibility(View.VISIBLE);
                            tr_button.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        l_button.setVisibility(View.VISIBLE);
                        tr_button.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error! " + error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            decorView.setSystemUiVisibility(hideSystemBars());
//        }
//    }
//    private int hideSystemBars(){
//        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
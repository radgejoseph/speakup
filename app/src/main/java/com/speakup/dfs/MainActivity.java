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

    private EditText _txtUsername, _txtPassword;
    private Button _btnLogIn, _btnToReg;
    private ProgressBar _progress;
    private static String URL_LOGIN = "http://localhost/SpeakUP/login.php";

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sessionManager = new SessionManager(this);

        _progress = findViewById(R.id.progress);
        _txtUsername = findViewById(R.id.txtUsername);
        _txtPassword = findViewById(R.id.txtPassword);
        _btnLogIn = findViewById(R.id.btnLogin);
        _btnToReg = findViewById(R.id.btnToReg);
        //Section of Login

        _btnLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user_name = _txtUsername.getText().toString();
                String pass_word = _txtPassword.getText().toString();
                String type = "login";
                BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                backgroundTask.execute(type, user_name, pass_word);
            }
        });

        /*_btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_l = _txtUsername.getText().toString();
                String password_l = _txtPassword.getText().toString();

                if (!username_l.isEmpty() && !password_l.isEmpty()) {
                    Login(username_l, password_l);
                }
                else {
                    _txtUsername.setError("Please insert your username");
                    _txtPassword.setError("Please insert your password");
                }
            }
        });*/

        //Section for Opening Registration
        _btnToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    private void Login(final String username, final String password) {

        _progress.setVisibility(View.VISIBLE);
        _btnLogIn.setVisibility(View.GONE);
        _btnToReg.setVisibility(View.GONE);

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
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);

                                    Toast.makeText(MainActivity.this, "Success Login! \nName : "
                                            +name+"\nEmail : "+email, Toast.LENGTH_LONG).show();

                                    _progress.setVisibility(View.GONE);
                                    openHomeActivity();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            _progress.setVisibility(View.GONE);
                            _btnLogIn.setVisibility(View.VISIBLE);
                            _btnToReg.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        _progress.setVisibility(View.GONE);
                        _btnLogIn.setVisibility(View.VISIBLE);
                        _btnToReg.setVisibility(View.VISIBLE);
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
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

    private EditText username, password;
    private Button l_button, tr_button;
    private ProgressBar progress;
//    private static String URL_LOGIN = "http://speakupnaga.herokuapp.com/speakup/login.php";
    private static String URL_LOGIN = "http://48383786ae99.ngrok.io/SpeakUp/login.php";

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

        tr_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });

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
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    sessionManager.createSession(name, email, id);

                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);

                                    Toast.makeText(MainActivity.this, "Success Login! \nName : "
                                            +name+"\nEmail : "+email, Toast.LENGTH_LONG).show();

                                    progress.setVisibility(View.GONE);
                                    openHomeActivity();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.setVisibility(View.GONE);
                            l_button.setVisibility(View.VISIBLE);
                            tr_button.setVisibility(View.VISIBLE);
                            Toast.makeText(MainActivity.this, "Error! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        l_button.setVisibility(View.VISIBLE);
                        tr_button.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Error! ", Toast.LENGTH_SHORT).show();

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

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}
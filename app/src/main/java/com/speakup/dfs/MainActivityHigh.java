package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityHigh extends AppCompatActivity implements ListItemPlateAdapter.OnItemListener{

    private EditText username, password;
    private Button l_button, tr_button;
    private ProgressBar progress;
    //    private static String URL_LOGIN = "http://speakupnaga.herokuapp.com/speakup/login.php";
    private static String URL_LOGIN = "http://192.168.1.103/speakup/login.php";
    //private static final String URL_ALL_PLATE_LIST_RECENT = "http://192.168.1.103/speakup/vehicle_plate_list_recent.php";
    private static final String URL_ALL_PLATE_LIST_HIGHEST = "http://192.168.1.103/speakup/vehicle_plate_list_highest.php";
    private static final String URL_ALL_PLATE_LIST_LOWEST = "http://192.168.1.103/speakup/vehicle_plate_list_lowest.php";

    RecyclerView recyclerView;
    ListItemPlateAdapter listItemAdapter;
    List<ListItem> itemList;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        EditText editTextSearch = findViewById(R.id.search_bar);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listItemAdapter.getFilter().filter(s);
            }

        });

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

        android.widget.ImageView recent = findViewById(R.id.recent);
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityHigh.this, MainActivity.class);
                startActivity(intent);
            }
        });

        android.widget.ImageView low = findViewById(R.id.low);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityHigh.this, MainActivityLow.class);
                startActivity(intent);
            }
        });

        android.widget.ImageView high = findViewById(R.id.high);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityHigh.this, MainActivityHigh.class);
                startActivity(intent);
            }
        });



        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_list);
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadList();



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

                                    Intent intent = new Intent(MainActivityHigh.this, HomeActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", email);
                                    startActivity(intent);

                                    Toast.makeText(MainActivityHigh.this, "Success Login! \nName : "
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
                            Toast.makeText(MainActivityHigh.this, "Error! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        l_button.setVisibility(View.VISIBLE);
                        tr_button.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivityHigh.this, "Error! ", Toast.LENGTH_SHORT).show();

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



    private void loadList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ALL_PLATE_LIST_HIGHEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                String  strVehicle = object.getString("vehicle");
                                String strPlate = object.getString("body_plate");
                                int strRatings = object.getInt("ratings");

                                ListItem listItem = new ListItem(strVehicle, strPlate, strRatings);
                                itemList.add(listItem);
                            }

                            listItemAdapter = new ListItemPlateAdapter(itemList, MainActivityHigh.this);
                            recyclerView.setAdapter(listItemAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(MainActivityHigh.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivityHigh.this, PlateRatingsActivityView.class);
        intent.putExtra("selected_plate", itemList.get(position));
        startActivity(intent);
    }


}
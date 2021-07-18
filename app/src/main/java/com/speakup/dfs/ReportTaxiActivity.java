package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportTaxiActivity extends AppCompatActivity implements ListItemAdapterTaxi.OnItemListener {

    private static final String URL_TAXI_LIST = "http://192.168.1.136/speakupmobile/list_taxi.php";

    RecyclerView recyclerView;
    ListItemAdapterTaxi listItemAdapter;
    Toolbar toolbar;
    Button button_colorum;

    List<ListItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_taxi_page);
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

        editTextSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
        editTextSearch.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS | InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button_colorum = findViewById(R.id.button_colorum);
        button_colorum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportTaxiActivity.this, ColorumFormActivity.class);
                startActivity(intent);
            }
        });

        android.widget.ImageView high = findViewById(R.id.high);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(itemList, ListItem.listItemComparatorHtoL);
                listItemAdapter.notifyDataSetChanged();
//                Intent intent = new Intent(MainActivity.this, MainActivityHigh.class);
//                startActivity(intent);
            }
        });

        android.widget.ImageView low = findViewById(R.id.low);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(itemList, ListItem.listItemComparatorLtoH);
                listItemAdapter.notifyDataSetChanged();
//                Intent intent = new Intent(MainActivity.this, MainActivityLow.class);
//                startActivity(intent);
            }
        });

        android.widget.ImageView recent = findViewById(R.id.recent);
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(itemList, ListItem.listItemComparatorAZ);
                listItemAdapter.notifyDataSetChanged();
//                Intent intent = new Intent(ReportTaxiActivity.this, ReportTaxiActivity.class);
//                startActivity(intent);
            }
        });


        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_list);
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadList();
    }

    private void filter(String text) {
        ArrayList<ListItem> filteredList = new ArrayList<>();

        for (ListItem item : itemList) {
            if (item.getPlateL().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        listItemAdapter.filterList(filteredList);
    }

    private void loadList() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading list...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAXI_LIST,
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

                            listItemAdapter = new ListItemAdapterTaxi(itemList, ReportTaxiActivity.this);
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
                Toast.makeText(ReportTaxiActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ReportTaxiActivity.this, PlateRatingsActivity.class);
        intent.putExtra("selected_plate", itemList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        Intent intent = new Intent(ReportTaxiActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
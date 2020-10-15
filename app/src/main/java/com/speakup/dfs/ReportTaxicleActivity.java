package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportTaxicleActivity extends AppCompatActivity implements ListItemAdapter.OnItemListener {

    private static final String URL_TAXICLE_LIST = "http://half-a-dozen-school.000webhostapp.com/list_taxicle.php";

    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;
    Toolbar toolbar;

    List<ListItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_taxicle_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitle("TAXICLE");

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
                filter(s.toString());
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerview_list);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAXICLE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                int strID = object.getInt("id");
                                String strPlate = object.getString("body_plate");

                                ListItem listItem = new ListItem(strID, strPlate);
                                itemList.add(listItem);
                            }

                            listItemAdapter = new ListItemAdapter(itemList, ReportTaxicleActivity.this);
                            recyclerView.setAdapter(listItemAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportTaxicleActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onItemClick(int position) {
        //itemList.get(position);
        Intent intent = new Intent(ReportTaxicleActivity.this, PlateRatingsActivity.class);
        intent.putExtra("selected_plate", itemList.get(position));
        startActivity(intent);
        //openRateMeActivity();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
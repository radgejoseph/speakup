package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

public class ReportTricycleActivity extends AppCompatActivity implements ListItemAdapterTricycle.OnItemListener {

    private static final String URL_TRICYCLE_LIST = "http://192.168.1.138/SpeakUP/list_tricycle.php";
//    private static final String URL_TRICYCLE_LIST = "https://speakup-app-apk.herokuapp.com/list_tricycle.php";

    RecyclerView recyclerView;
    ListItemAdapterTricycle listItemAdapter;
    Toolbar toolbar;
    Button button_colorum;

    List<ListItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_tricycle_page);
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


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        button_colorum = findViewById(R.id.button_colorum);
        button_colorum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReportTricycleActivity.this, ColorumFormActivity.class);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TRICYCLE_LIST,
                response -> {
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

                        listItemAdapter = new ListItemAdapterTricycle(itemList, ReportTricycleActivity.this);
                        recyclerView.setAdapter(listItemAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ReportTricycleActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(this).add(stringRequest);
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ReportTricycleActivity.this, PlateRatingsActivity.class);
        intent.putExtra("selected_plate", itemList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
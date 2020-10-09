package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ReportTaxicleActivity extends AppCompatActivity {
    private View decorView;

    RecyclerView recyclerView;
    ListItemAdapter listItemAdapter;

    List<ListItem> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_taxicle_page);

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility ==0)
                    decorView.setSystemUiVisibility(hideSystemBars());
            }
        });
        android.widget.ImageView backBut = findViewById(R.id.back_to_home);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeActivity();
            }
        });

        itemList = new ArrayList<>();

        recyclerView = findViewById(R.id.taxicle_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //adding some items to our list
        itemList.add(
                new ListItem(
                        1,
                        "AVG 123",
                        R.drawable.taxicle_ico));

        itemList.add(
                new ListItem(
                        1,
                        "JCO 123",
                        R.drawable.taxicle_ico));

        itemList.add(
                new ListItem(
                        1,
                        "LVT 123",
                        R.drawable.taxicle_ico));

        itemList.add(
                new ListItem(
                        1,
                        "PPP 123",
                        R.drawable.taxicle_ico));

         itemList.add(
                 new ListItem(
                         1,
                         "LPG 123",
                         R.drawable.taxicle_ico));

        itemList.add(
                new ListItem(
                        1,
                        "ABC 123",
                        R.drawable.taxicle_ico));

        listItemAdapter = new ListItemAdapter(this, itemList);
        recyclerView.setAdapter(listItemAdapter);

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

}
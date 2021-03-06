package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;

public class TabbedActivity extends AppCompatActivity {

    private ViewPager viewPager;
    public PagerAdapter pagerAdapter;
    String plate, vehicle;
    Toolbar toolbar;

    private TextView textPlate;
    private TextView textVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        findViewById(R.id.tab_complaint);
        findViewById(R.id.tab_commendation);
        viewPager = findViewById(R.id.viewpager);

        pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }
                else if (tab.getPosition() == 1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        plate = getIntent().getStringExtra("selected_plate");
        vehicle = getIntent().getStringExtra("vehicle");
        textPlate = findViewById(R.id.plate_hoder);
        textPlate.setText(plate);
        textVehicle = findViewById(R.id.vehicle_type_holder);
        textVehicle.setText(vehicle);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String sendPlate() {
        return plate;
    }

    public String sendVehicle() {
        return vehicle;
    }

}
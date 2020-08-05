package com.speakup.dfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VehicleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.vehicle_fragment, container, false);

        ImageButton jeepImage = (ImageButton) view.findViewById(R.id.jeepney_icon);
        ImageButton taxicleImage = (ImageButton) view.findViewById(R.id.taxicle_icon);
        ImageButton tricycleImage = (ImageButton) view.findViewById(R.id.tricycle_icon);
        ImageButton taxiImage = (ImageButton) view.findViewById(R.id.taxi_icon);

        jeepImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportJeepActivity.class);
                startActivity(intent);
            }
        });
        taxicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
                startActivity(intent);
            }
        });
        tricycleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportTricycleActivity.class);
                startActivity(intent);
            }
        });
        taxiImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReportTaxiActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

package com.speakup.dfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

public class VehicleFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.vehicle_fragment, container, false);

        Group jeepField = view.findViewById(R.id.group_jeepney);
        int[] jeepFieldIds = jeepField.getReferencedIds();
        for (int id : jeepFieldIds) {
            view.findViewById(id).setOnClickListener(view1 -> {
                Intent intent = new Intent(getActivity(), ReportJeepActivity.class);
                startActivity(intent);
            });
        }

        Group taxicleField = view.findViewById(R.id.group_taxicle);
        int[] taxicleFieldIds = taxicleField.getReferencedIds();
        for (int id : taxicleFieldIds) {
            view.findViewById(id).setOnClickListener(view12 -> {
                Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
                startActivity(intent);
            });
        }


        Group tricycleField = view.findViewById(R.id.group_tricycle);
        int[] tricycleFieldIds = tricycleField.getReferencedIds();
        for (int id : tricycleFieldIds) {
            view.findViewById(id).setOnClickListener(view13 -> {
                Intent intent = new Intent(getActivity(), ReportTricycleActivity.class);
                startActivity(intent);
            });
        }

        Group taxiField = view.findViewById(R.id.group_taxi);
        int[] taxiFieldIds = taxiField.getReferencedIds();
        for (int id : taxiFieldIds) {
            view.findViewById(id).setOnClickListener(view14 -> {
                Intent intent = new Intent(getActivity(), ReportTaxiActivity.class);
                startActivity(intent);
            });
        }

        return view;
    }
}

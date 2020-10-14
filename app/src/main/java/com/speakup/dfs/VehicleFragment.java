package com.speakup.dfs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class VehicleFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.vehicle_fragment, container, false);

        //Group jeepImage = (Group) view.findViewById(R.id.group_jeepney);
        //Group taxicleImage = (Group) view.findViewById(R.id.group_taxicle);
        //Group tricycleImage = (Group) view.findViewById(R.id.group_tricycle);
        // taxiImage = (Group) view.findViewById(R.id.group_taxi);

        Group jeepField = view.findViewById(R.id.group_jeepney);
        int[] jeepFieldIds = jeepField.getReferencedIds();
        for (int id : jeepFieldIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ReportJeepActivity.class);
                    startActivity(intent);
                }
            });
        }


        Group taxicleField = view.findViewById(R.id.group_taxicle);
        int[] taxicleFieldIds = taxicleField.getReferencedIds();
        for (int id : taxicleFieldIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
                    startActivity(intent);
                }
            });
        }


        Group tricycleField = view.findViewById(R.id.group_tricycle);
        int[] tricycleFieldIds = tricycleField.getReferencedIds();
        for (int id : tricycleFieldIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
                    startActivity(intent);
                }
            });
        }


        Group taxiField = view.findViewById(R.id.group_taxi);
        int[] taxiFieldIds = taxiField.getReferencedIds();
        for (int id : taxiFieldIds) {
            view.findViewById(id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
                    startActivity(intent);
                }
            });
        }


//        jeepImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ReportJeepActivity.class);
//                startActivity(intent);
//            }
//        });
//        taxicleImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ReportTaxicleActivity.class);
//                startActivity(intent);
//            }
//        });
//        tricycleImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ReportTricycleActivity.class);
//                startActivity(intent);
//            }
//        });
//        taxiImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ReportTaxiActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }
}

package com.speakup.dfs;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommendationFragment extends Fragment {

    TextView date_picker;
    TextView time_picker;
    DatePickerDialog.OnDateSetListener setListenerD;
    TimePickerDialog.OnTimeSetListener setListenerT;
    private TextView textPlate;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommendationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommendationFragment newInstance(String param1, String param2) {
        CommendationFragment fragment = new CommendationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_commendation, container, false);

        TabbedActivity tabbedActivity = (TabbedActivity) getActivity();
        String getData = tabbedActivity.sendData();
        textPlate = view.findViewById(R.id.plate_number);
        textPlate.setText(getData);

/* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ DATE PICKER ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
        date_picker = view.findViewById(R.id.date_picker);
        Calendar calendarD = Calendar.getInstance();
        final int year = calendarD.get(Calendar.YEAR);
        final int month = calendarD.get(Calendar.MONTH);
        final int day = calendarD.get(Calendar.DAY_OF_MONTH);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerD,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerD = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                date_picker.setText(date);
            }
        };
/* ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ DATE PICKER ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */

/* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ TIME PICKER ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
        time_picker = view.findViewById(R.id.time_picker);
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarT = Calendar.getInstance();
                int hour = calendarT.get(Calendar.HOUR_OF_DAY);
                int minutes = calendarT.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerT,hour,minutes,android.text.format.DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
            }
        });

        setListenerT = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                String am_pm;
                if (hour > 12)
                {
                    hour = hour - 12;
                    am_pm = "PM";
                } else {
                    am_pm = "AM";
                }
                String time = hour+":"+minutes+" "+am_pm;
                time_picker.setText(time);
            }
        };
/* ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ TIME PICKER ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */

        android.widget.ImageView add_image_video = view.findViewById(R.id.capture_image);
        add_image_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Gallery is Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        android.widget.ImageView add_audio = view.findViewById(R.id.add_audio);
        add_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Audio is Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        android.widget.ImageView basic_option = view.findViewById(R.id.basic_option);
        basic_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RateMeActivity.class);
                startActivity(intent);
            }
        });
        return  view;
    }

}
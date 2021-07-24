package com.speakup.dfs;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintActivity extends AppCompatActivity {
    private static String URL_COMPLAINT = "http://speakupadnu.000webhostapp.com/speakupmobile/complaint.php";

    public static final int CAMERA_PERM_CODE = 101;
    public static final int GALLERY_REQUEST_CODE = 105;
    String complaint_selection = "";
    TextView date_picker;
    TextView time_picker;
    TextView checked_items;
    DatePickerDialog.OnDateSetListener setListenerD;
    TimePickerDialog.OnTimeSetListener setListenerT;
    private TextView textPlate;
    private TextView textVehicle;
    private TextView narrative;
    ImageView upload_image_view_gallery;
    Button submit_button;
    String getId;
    Bitmap bitmap;
    String encodeImageString;
    SessionManager sessionManager;
    private List<String> mSelectedItems;
    String plate, vehicle;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        plate = getIntent().getStringExtra("selected_plate");
        vehicle = getIntent().getStringExtra("vehicle");
        textPlate = findViewById(R.id.plate_number);
        textPlate.setText(plate);
        textVehicle = findViewById(R.id.vehicle_type_holder);
        textVehicle.setText(vehicle);

        checked_items = findViewById(R.id.checked_items);

        sessionManager = new SessionManager(ComplaintActivity.this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        askCameraPermission();

        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ComplaintActivity.this);
        builder1.setTitle("DISCLAIMER!");
        builder1.setIcon(R.drawable.ic_baseline_notification_important_24);
        builder1.setMessage("All complaint reports are recorded, scrutinized, and reviewed by the Public Safety Office (PSO). Thus, you are liable for every complaint report you will submit.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Proceed!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "Take me back!",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        onBackPressed();
                        dialog.cancel();
                    }
                });

        androidx.appcompat.app.AlertDialog alert11 = builder1.create();
        alert11.show();

        narrative = findViewById(R.id.complaint_text);

        narrative.setImeOptions(EditorInfo.IME_ACTION_DONE);
        narrative.setRawInputType(InputType.TYPE_CLASS_TEXT);

        Button buttonComplaintType = findViewById(R.id.buttonComplaintType);
        buttonComplaintType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complaint_selection = "";
                onCreateDialog().show();
            }
        });

        submit_button = findViewById(R.id.submit_button_complaint);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checked_items_r = checked_items.getText().toString().trim();

                if (upload_image_view_gallery.getDrawable() != null && !checked_items_r.equals("")){
                    ComplaintSubmit();
                }
                else {
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(ComplaintActivity.this);
                    builder1.setMessage("Image and Complaint are Required!");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });


                    androidx.appcompat.app.AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });

        /* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ DATE PICKER ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
        date_picker = findViewById(R.id.date_picker);

        date_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarD = Calendar.getInstance();
                int year = calendarD.get(Calendar.YEAR);
                int month = calendarD.get(Calendar.MONTH);
                int day = calendarD.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ComplaintActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerD,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerD = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = year+"-"+month+"-"+day;
                date_picker.setText(date);
            }
        };
        /* ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ DATE PICKER ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */

        /* ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ TIME PICKER ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ */
        time_picker = findViewById(R.id.time_picker);
        time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendarT = Calendar.getInstance();
                int hour = calendarT.get(Calendar.HOUR_OF_DAY);
                int minutes = calendarT.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        ComplaintActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        ,setListenerT,hour,minutes, DateFormat.is24HourFormat(ComplaintActivity.this));
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
                time_picker.setText(String.format("%02d:%02d %s",hour, minutes, am_pm));
            }
        };
        /* ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ TIME PICKER ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ */

        upload_image_view_gallery = findViewById(R.id.upload_image_view_camera_gallery);

        ImageView add_image_video = findViewById(R.id.add_image_video);
        add_image_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void ComplaintSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(ComplaintActivity.this);
        progressDialog.setMessage("Submitting Please Wait...");
        progressDialog.show();

        final String textPlate = this.textPlate.getText().toString().trim();
        final String textVehicle = this.textVehicle.getText().toString().trim();
        final String other_narrative = this.narrative.getText().toString().trim();
        final String date = this.date_picker.getText().toString().trim();
        final String time = this.time_picker.getText().toString().trim();
        final String complaint_selection = this.complaint_selection.trim();
        final String narrative = complaint_selection + other_narrative;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COMPLAINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(ComplaintActivity.this,"Complaint submitted successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ComplaintActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(ComplaintActivity.this,"Submit Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(ComplaintActivity.this,"Submit Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", getId);
                params.put("body_plate", textPlate);
                params.put("date", date);
                params.put("time", time);
                params.put("narrative", narrative);
                params.put("vehicle", textVehicle);
                params.put("file", encodeImageString);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ComplaintActivity.this);
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(ComplaintActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ComplaintActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri filepath=data.getData();
                try
                {
                    InputStream inputStream = ComplaintActivity.this.getContentResolver().openInputStream(filepath);
                    bitmap= BitmapFactory.decodeStream(inputStream);
                    upload_image_view_gallery.setImageBitmap(bitmap);
                    encodeBitmapImage(bitmap);
                }catch (Exception ex)
                {

                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @NonNull
    public Dialog onCreateDialog() {
        mSelectedItems = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintActivity.this);
        builder.setTitle("Select Complaints");

        builder.setMultiChoiceItems(R.array.complaints, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                String[] items = ComplaintActivity.this.getResources().getStringArray(R.array.complaints);
                if (isChecked)
                {
                    mSelectedItems.add(items[which]);
                }
                else if(mSelectedItems.contains(items[which]))
                {
                    mSelectedItems.remove(items[which]);
                }
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for (String Item : mSelectedItems)
                {
                    complaint_selection = complaint_selection+Item+", ";
                }
                checked_items.setText(complaint_selection);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

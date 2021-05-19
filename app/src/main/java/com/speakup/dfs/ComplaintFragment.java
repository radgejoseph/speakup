package com.speakup.dfs;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplaintFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplaintFragment extends Fragment {
    private static String URL_COMPLAINT = "http://192.168.1.138/SpeakUP/complaint.php";

    public static final int CAMERA_PERM_CODE = 101;
    //public static final int CAMERA_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;
    TextView date_picker;
    TextView time_picker;
    DatePickerDialog.OnDateSetListener setListenerD;
    TimePickerDialog.OnTimeSetListener setListenerT;
    private TextView textPlate;
    private TextView textVehicle;
    private TextView narrative;
    ImageView upload_image_view_camera, upload_image_view_gallery;
    String currentPhotoPath;
    Button submit_button;
    String getId;
    Bitmap bitmap;
    String encodeImageString;
    SessionManager sessionManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComplaintFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComplaintFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplaintFragment newInstance(String param1, String param2) {
        ComplaintFragment fragment = new ComplaintFragment();
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
        View view = inflater.inflate(R.layout.fragment_complaint, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        TabbedActivity tabbedActivity = (TabbedActivity) getActivity();
        String getPlate = tabbedActivity.sendPlate();
        String getVehicle = tabbedActivity.sendVehicle();
        textPlate = view.findViewById(R.id.plate_number);
        textPlate.setText(getPlate);
        textVehicle = view.findViewById(R.id.vehicle_type_holder);
        textVehicle.setText(getVehicle);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        askCameraPermission();

        narrative = view.findViewById(R.id.complaint_text);

        submit_button = view.findViewById(R.id.submit_button_complaint);
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String narrative_r = narrative.getText().toString().trim();
                //String date_r = date_picker.getText().toString().trim();

                if (!narrative_r.isEmpty()/* && !date_r.equals("0000-00-00")*/){
                    ComplaintSubmit();
                }
                else {
                    narrative.setError("Your Complaint is Required");
                    //date_picker.setError("Date is Required");
//                    time_picker.setError("Time is Required");
                }
            }
        });

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
                String date = year+"-"+month+"-"+day;
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

        //upload_image_view_camera = view.findViewById(R.id.upload_image_view_camera);
        upload_image_view_gallery = view.findViewById(R.id.upload_image_view_camera_gallery);

//        android.widget.ImageView capture_image = view.findViewById(R.id.capture_image);
//        capture_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                askCameraPermission();
//            }
//        });

        android.widget.ImageView add_image_video = view.findViewById(R.id.add_image_video);
        add_image_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
            }
        });

        android.widget.ImageView basic_option = view.findViewById(R.id.basic_option);
        basic_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabbedActivity.finish();
            }
        });
        return  view;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
//    {
//        if(requestCode==1 && resultCode==RESULT_OK)
//        {
//            Uri filepath=data.getData();
//            try
//            {
//                InputStream inputStream=getContentResolver().openInputStream(filepath);
//                bitmap= BitmapFactory.decodeStream(inputStream);
//                upload_image_view_gallery.setImageBitmap(bitmap);
//                encodeBitmapImage(bitmap);
//            }catch (Exception ex)
//            {
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void ComplaintSubmit() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String textPlate = this.textPlate.getText().toString().trim();
        final String textVehicle = this.textVehicle.getText().toString().trim();
        final String narrative = this.narrative.getText().toString().trim();
        final String date = this.date_picker.getText().toString().trim();
        final String time = this.time_picker.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_COMPLAINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"Complaint submitted successfully!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), HomeActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(),"Submit Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Submit Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body_plate", textPlate);
                params.put("vehicle", textVehicle);
                params.put("narrative", narrative);
                params.put("date", date);
                params.put("time", time);
                params.put("user_id", getId);
                params.put("file", encodeImageString);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERM_CODE);
        }

//        else {
//            dispatchTakePictureIntent();
//        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == CAMERA_PERM_CODE) {
//            if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                dispatchTakePictureIntent();
//            }
//            else {
//                Toast.makeText(getActivity(),"Camera and Storage Permission are Required to use the Camera.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                File f = new File(currentPhotoPath);
//                upload_image_view_camera.setImageURI(Uri.fromFile(f));
//                Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));
//
//                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri = Uri.fromFile(f);
//                mediaScanIntent.setData(contentUri);
//                getActivity().sendBroadcast(mediaScanIntent);
//
//            }
//        }

        if (requestCode == GALLERY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri filepath=data.getData();
                try
                {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(filepath);
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

//    private String getFileExt(Uri contentUri) {
//        ContentResolver c = getActivity().getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(c.getType(contentUri));
//    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        //File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getActivity(),
//                        "com.speakup.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
//            }
//        }
//    }
}
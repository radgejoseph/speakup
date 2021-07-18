package com.speakup.dfs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment{

    private static final String TAG = HomeActivity.class.getSimpleName();
//    private static String URL_READ = "http://speakupadnu.000webhostapp.com/speakupmobile/read_detail.php";
//    private static String URL_EDIT = "http://speakupadnu.000webhostapp.com/speakupmobile/edit_detail.php";
    private static String URL_READ = "http://speakupadnu.000webhostapp.com/speakupmobile/read_detail.php";
    private static String URL_EDIT = "http://speakupadnu.000webhostapp.com/speakupmobile/edit_detail.php";

//    private TextView name, username, password, phone_number, email, address, status;
    private TextView name, username, phone_number, email, address, status;
    private Menu action;
    String getId;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        setHasOptionsMenu(true);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        name = view.findViewById(R.id.fullname_text);
        username = view.findViewById(R.id.username_text);
//        password = view.findViewById(R.id.password_text);
        phone_number = view.findViewById(R.id.phone_text);
        email = view.findViewById(R.id.email_text);
        address = view.findViewById(R.id.address_text);
        status = view.findViewById(R.id.textVerified);

        address.setImeOptions(EditorInfo.IME_ACTION_DONE);
        address.setRawInputType(InputType.TYPE_CLASS_TEXT);
        name.setImeOptions(EditorInfo.IME_ACTION_DONE);
        name.setRawInputType(InputType.TYPE_CLASS_TEXT);
        username.setImeOptions(EditorInfo.IME_ACTION_DONE);
//        password.setImeOptions(EditorInfo.IME_ACTION_DONE);
        username.setRawInputType(InputType.TYPE_CLASS_TEXT);
        email.setImeOptions(EditorInfo.IME_ACTION_DONE);
        email.setRawInputType(InputType.TYPE_CLASS_TEXT);
        phone_number.setImeOptions(EditorInfo.IME_ACTION_DONE);

        name.setFocusableInTouchMode(false);
        username.setFocusableInTouchMode(false);
//        password.setFocusableInTouchMode(false);
        phone_number.setFocusableInTouchMode(false);
        email.setFocusableInTouchMode(false);
        address.setFocusableInTouchMode(false);
        name.setFocusable(false);
        username.setFocusable(false);
//        password.setFocusable(false);
        phone_number.setFocusable(false);
        email.setFocusable(false);
        address.setFocusable(false);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);


        return  view;
    }

    private void getUserDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("name").trim();
                                    String strUsername = object.getString("username").trim();
                                    String strMobile = object.getString("phone_number").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strAddress = object.getString("address").trim();
                                    String strStatus = object.getString("status").trim();
                                    name.setText(strName);
                                    username.setText(strUsername);
                                    phone_number.setText(strMobile);
                                    email.setText(strEmail);
                                    address.setText(strAddress);
                                    status.setText(strStatus);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error Reading Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error Reading Details", Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserDetails();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_profile_menu, menu);

        action = menu;
        action.findItem(R.id.save_profile).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_profile:
            name.setFocusableInTouchMode(true);
            username.setFocusableInTouchMode(true);
//            password.setFocusableInTouchMode(true);
            phone_number.setFocusableInTouchMode(true);
            email.setFocusableInTouchMode(true);
            address.setFocusableInTouchMode(true);

            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(name, InputMethodManager.SHOW_IMPLICIT);

            action.findItem(R.id.edit_profile).setVisible(false);
            action.findItem(R.id.save_profile).setVisible(true);

                return true;

            case R.id.save_profile:

                String name_r = name.getText().toString().trim();
                String username_r = username.getText().toString().trim();
//                String password_r = password.getText().toString().trim();
                String mobile_r = phone_number.getText().toString().trim();
                String email_r = email.getText().toString().trim();
                String address_r = address.getText().toString().trim();

//                if (!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
//                        && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()) {
                if (!name_r.isEmpty() && !username_r.isEmpty() && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()) {

                            SaveEditDetails();

                            action.findItem(R.id.edit_profile).setVisible(true);
                            action.findItem(R.id.save_profile).setVisible(false);

                            name.setFocusableInTouchMode(false);
                            username.setFocusableInTouchMode(false);
//                            password.setFocusableInTouchMode(false);
                            phone_number.setFocusableInTouchMode(false);
                            email.setFocusableInTouchMode(false);
                            address.setFocusableInTouchMode(false);
                            name.setFocusable(false);
                            username.setFocusable(false);
//                            password.setFocusable(false);
                            phone_number.setFocusable(false);
                            email.setFocusable(false);
                            address.setFocusable(false);

                        }
                        else {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setMessage("Please make sure all fields are filled.");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }

    private void SaveEditDetails() {
        final String name = this.name.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
//        final String password = this.password.getText().toString().trim();
        final String phone_number = this.phone_number.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String address = this.address.getText().toString().trim();
        final String id = getId;

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
//                                sessionManager.createSessionEdit(name, username, password, phone_number, email, address, id);
                                sessionManager.createSessionEdit(name, username, phone_number, email, address, id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
//                params.put("password", password);
                params.put("phone_number", phone_number);
                params.put("email", email);
                params.put("address", address);
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

}

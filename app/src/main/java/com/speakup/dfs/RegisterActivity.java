package com.speakup.dfs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import papaya.in.sendmail.SendMail;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, username, password, phone_number, email, address;
    private static final String URL_REGIST = "http://speakupadnu.000webhostapp.com/speakupmobile/register.php";
    private static final String VERIFICATION = "https://bit.ly/3xBuSvk";
    private Button reg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        name = findViewById(R.id.fullname_text);
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        phone_number = findViewById(R.id.phone_text);
        email = findViewById(R.id.email_text);
        address = findViewById(R.id.address_text);
        reg_button = findViewById(R.id.register_button);

        address.setImeOptions(EditorInfo.IME_ACTION_DONE);
        address.setRawInputType(InputType.TYPE_CLASS_TEXT);
        
        android.widget.ImageView backBut = findViewById(R.id.imagebackButton);
        backBut.setOnClickListener(view -> onBackPressed());

        reg_button.setOnClickListener(view -> {
            String name_r = name.getText().toString().trim();
            String username_r = username.getText().toString().trim();
            String password_r = password.getText().toString().trim();
            String mobile_r = phone_number.getText().toString().trim();
            String email_r = email.getText().toString().trim();
            String address_r = address.getText().toString().trim();


            if (!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
                    && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()) {

                Regist();
            }
            else {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                builder1.setMessage("Please make sure all fields are filled in correctly.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    private void Regist() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String name = this.name.getText().toString().trim();
        final String username = this.username.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String phone_number = this.phone_number.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String address = this.address.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("1")) {
                            openRegisterComplete();
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        reg_button.setVisibility(View.VISIBLE);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                        builder1.setMessage("Register Error! " + response);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "OK",
                                (dialog, id) -> dialog.cancel());


                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    reg_button.setVisibility(View.VISIBLE);
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(RegisterActivity.this);
                    builder1.setMessage("Register Error! " + error.toString());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            (dialog, id) -> dialog.cancel());


                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("username", username);
                params.put("password", password);
                params.put("phone_number", phone_number);
                params.put("email", email);
                params.put("address", address);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        SendMail mail = new SendMail("speakupadnu@gmail.com", "delasalasferrersanjoaquin",
                email,
                "Verify Your Email",
                "Thank you " + name + " for signing up.\n\nUsername: "+ username +"\nPassword: "+ password +"\n\nYou can now share your PUV experiences with us.\n\nPlease Click the link to verify your account "+ VERIFICATION);
        mail.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openRegisterComplete() {
        Intent intent = new Intent(this, RegisterComplete.class);
        startActivity(intent);
    }

}
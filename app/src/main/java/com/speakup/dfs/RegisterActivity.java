package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class RegisterActivity extends AppCompatActivity {

    private Button _btnReg;
    private EditText _txtName, _txtUsername, _txtPassword, _txtPhone, _txtEmail, _txtAddress;
    //private static String _URL_Reg = "http://localhost/SpeakUP/register.php";
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        _txtUsername = findViewById(R.id.txtUsername);
        _txtName = findViewById(R.id.txtFullname);
        _txtEmail = findViewById(R.id.txtEmail);
        _txtPhone = findViewById(R.id.txtPhone);
        _txtAddress = findViewById(R.id.txtAddress);
        _txtPassword = findViewById(R.id.txtPassword);
        _btnReg = findViewById(R.id.btnReg);
        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //add validation Name
        awesomeValidation.addValidation(this, R.id.txtFullname, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        //For Mobile Number
        awesomeValidation.addValidation(this,R.id.txtPhone, "[0]{1}[9]{1}[0-9]{9}$", R.string.invalid_mobile);
        //for email
        awesomeValidation.addValidation(this,R.id.txtEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        //for password
        awesomeValidation.addValidation(this,R.id.txtPassword, ".{8,}", R.string.invalid_password);

        /*android.widget.ImageView backBut = findViewById(R.id.imagebackButton);
        backBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
        _btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_r = _txtUsername.getText().toString();
                String name_r = _txtName.getText().toString();
                String email_r = _txtEmail.getText().toString();
                String mobile_r = _txtPhone.getText().toString();
                String address_r = _txtAddress.getText().toString();
                String password_r = _txtPassword.getText().toString();
                //String type_r = "reg";
                if (awesomeValidation.validate()){
                    Toast.makeText(getApplicationContext(), "Form Validate Successfully...", Toast.LENGTH_LONG).show();
                    regC();
                }else{
                    Toast.makeText(getApplicationContext(),"Validation Error.", Toast.LENGTH_LONG).show();
                }

                /*if (!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
                        && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()) {

                    regC();
                }
                else {
                    _txtName.setError("Full Name is Required");
                    _txtUsername.setError("Username is Required");
                    _txtPassword.setError("Password is Required");
                    _txtPhone.setError("Mobile Number is Required");
                    _txtEmail.setError("Email Address is Required");
                }*/
            }
        });

    }
    private void regC() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String fUsername = this._txtUsername.getText().toString();
        final String fName = this._txtName.getText().toString();
        final String fEmail = this._txtEmail.getText().toString();
        final String fPhone_number = this._txtPhone.getText().toString();
        final String fAddress = this._txtAddress.getText().toString();
        final String fPassword = this._txtPassword.getText().toString();
        final String fType = "reg";

        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(fType, fUsername, fName, fEmail, fPhone_number, fAddress, fPassword);
        openRegisterComplete();
        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, _URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                openRegisterComplete();
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Register Success!", Toast.LENGTH_SHORT).show();
                            }
                            else if (success.equals("0")) {
                                progressDialog.dismiss();
                                Toast.makeText(RegisterActivity.this,"Register Error! " + response, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            _reg_button.setVisibility(View.VISIBLE);
                            //Toast.makeText(RegisterActivity.this,"Register Error!@@@ " + e.toString(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this,"Register Error!@@@ ["+response+"]", Toast.LENGTH_SHORT).show();
                        }
                        //progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        _reg_button.setVisibility(View.VISIBLE);
                        Toast.makeText(RegisterActivity.this,"Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
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
        Toast.makeText(RegisterActivity.this,"Check Input " + requestQueue, Toast.LENGTH_SHORT).show();
        requestQueue.add(stringRequest);*/
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
package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                if (awesomeValidation.validate()&&!name_r.isEmpty() && !username_r.isEmpty() && !password_r.isEmpty()
                        && !mobile_r.isEmpty() && !email_r.isEmpty() && !address_r.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Form Validate Successfully...", Toast.LENGTH_LONG).show();
                    regC();
                }else{
                    Toast.makeText(getApplicationContext(),"Validation Error.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private void regC() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting...");
        progressDialog.show();

        final String rUsername = this._txtUsername.getText().toString();
        final String rName = this._txtName.getText().toString();
        final String rEmail = this._txtEmail.getText().toString();
        final String rPhone_number = this._txtPhone.getText().toString();
        final String rAddress = this._txtAddress.getText().toString();
        final String rPassword = this._txtPassword.getText().toString();
        final String rType = "reg";

        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(rType, rUsername, rName, rEmail, rPhone_number, rAddress, rPassword);
        openRegisterComplete();
        progressDialog.dismiss();
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
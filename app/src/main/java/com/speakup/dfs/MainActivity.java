package com.speakup.dfs;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText _txtUsername, _txtPassword;
    private Button _btnLogIn, _btnToReg;
    private ProgressBar _progress;
    //private static String URL_LOGIN = "http://localhost/SpeakUP/login.php";
    AwesomeValidation awesomeValidation;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        sessionManager = new SessionManager(this);

        _progress = findViewById(R.id.progress);
        _txtUsername = findViewById(R.id.txtUsername);
        _txtPassword = findViewById(R.id.txtPassword);
        _btnLogIn = findViewById(R.id.btnLogin);
        _btnToReg = findViewById(R.id.btnToReg);
        //initialize validation style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //For User Name
        awesomeValidation.addValidation(this,R.id.txtUsername, RegexTemplate.NOT_EMPTY, R.string.invalid_username);
        //for password
        awesomeValidation.addValidation(this,R.id.txtPassword, ".{8,}", R.string.invalid_password);
        //Section of Login
        _btnLogIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user_name = _txtUsername.getText().toString();
                String pass_word = _txtPassword.getText().toString();

                if (awesomeValidation.validate()&&!user_name.isEmpty() && !pass_word.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Successful To Log-In", Toast.LENGTH_LONG).show();
                    Login(user_name, pass_word);
                }else{
                    Toast.makeText(getApplicationContext(),"Failed To Log-In.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Section for Opening Registration
        _btnToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegisterActivity();
            }
        });
    }

    private void Login(final String username, final String password) {
        _progress.setVisibility(View.VISIBLE);
        _btnLogIn.setVisibility(View.GONE);
        _btnToReg.setVisibility(View.GONE);
        final String lUser_name = this._txtUsername.getText().toString();
        final String lPass_word = this._txtPassword.getText().toString();
        final String ltype = "login";

        BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(ltype, lUser_name, lPass_word);
        _progress.setVisibility(View.GONE);

        sessionManager.createSession(lUser_name, lPass_word, ltype);

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        intent.putExtra("name", lUser_name);
        intent.putExtra("password", lPass_word);
        //intent.putExtra("id", lId);
        startActivity(intent);
        openHomeActivity();
    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
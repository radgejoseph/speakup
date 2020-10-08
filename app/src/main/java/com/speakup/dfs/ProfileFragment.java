package com.speakup.dfs;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;

public class ProfileFragment extends Fragment{

    TextView name, username, password,
            mobile, email, address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

//        name = view.findViewById(R.id.fullname_text);
//        username = view.findViewById(R.id.username_text);
//        password = view.findViewById(R.id.password_text);
//        mobile = view.findViewById(R.id.phone_text);
//        email = view.findViewById(R.id.email_text);
//        address = view.findViewById(R.id.address_text);
//
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                FetchData fetchData = new FetchData("http://192.168.1.138/LoginRegister/signup.php");
//                if (fetchData.startFetch()) {
//                    if (fetchData.onComplete()) {
//                        String result = fetchData.getResult();
//                        //End ProgressBar (Set visibility to GONE)
//                        name.setText(result);
//                        username.setText(result);
//                        password.setText(result);
//                        mobile.setText(result);
//                        email.setText(result);
//                        address.setText(result);
//                        Log.i("FetchData", result);
//                    }
//                }
//            }
//        });

    return view;
    }
}

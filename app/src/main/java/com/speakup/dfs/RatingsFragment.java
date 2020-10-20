package com.speakup.dfs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
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

public class RatingsFragment extends Fragment implements  ListItemReviewAdapter.OnItemListener{

    //private static final String URL_MY_LIST = "http://192.168.1.117/SpeakUP/my_ratings.php";
    //private static String URL_READ = "http://192.168.1.117/SpeakUP/read_detail.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ratings_fragment, container, false);
        setHasOptionsMenu(true);


        return view;
    }


    @Override
    public void onItemClick(int position) {

    }
}

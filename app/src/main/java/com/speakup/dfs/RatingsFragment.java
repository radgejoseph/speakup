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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//public class RatingsFragment extends Fragment implements  ListItemReviewAdapter.OnItemListener{
public class RatingsFragment extends Fragment {

    private static final String URL_MY_LIST = "http://192.168.1.137/SpeakUP/my_ratings.php";

    RecyclerView recyclerView;
//    ListItemReviewAdapter listItemAdapter;
    List<ListItemReviews> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ratings_fragment, container, false);
        setHasOptionsMenu(true);

        itemList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerview_list_review);
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        listItemAdapter = new ListItemReviewAdapter(getContext(), itemList);
//        recyclerView.setAdapter(listItemAdapter);
        loadList();

        return view;
    }

    private void loadList() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading list...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_MY_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                itemList.add(new ListItemReviews(
                                        object.getString("vehicle"),
                                        object.getString("body_plate"),
                                        object.getInt("ratings"),
                                        object.getString("narrative")
                                ));
//                                String  strVehicle = object.getString("vehicle");
//                                String strPlate = object.getString("body_plate");
//                                int strRatings = object.getInt("ratings");
//                                String strNarrative = object.getString("narrative");

//                                ListItemReviews listItemReviews = new ListItemReviews(strVehicle, strPlate, strRatings, strNarrative);
//                                itemList.add(listItemReviews);
                            }

                            ListItemReviewAdapter  listItemReviewAdapter= new ListItemReviewAdapter(getActivity(), itemList);
                            recyclerView.setAdapter(listItemReviewAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

}

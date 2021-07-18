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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
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

public class RatingsFragment extends Fragment {

//    private static final String URL_MY_LIST = "http://192.168.1.133/speakupmobile/my_ratings.php";
    private static final String URL_MY_LIST = "http://192.168.1.133/speakupmobile/my_ratings.php";

    RecyclerView recyclerView;
    List<ListItemReviews> itemList;

    String getId;
    SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ratings_fragment, container, false);
        setHasOptionsMenu(true);

        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        itemList = new ArrayList<>();

        recyclerView = view.findViewById(R.id.recyclerview_list_review);
        recyclerView.bringToFront();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadList();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        return view;
    }

    private void loadList() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading list...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.DEPRECATED_GET_OR_POST, URL_MY_LIST,
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
                                            object.getString("narrative"),
                                            object.getString("created_at")
                                    ));
                                }

                            ListItemReviewAdapter  listItemReviewAdapter = new ListItemReviewAdapter(getActivity(), itemList);
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);

        int MY_SOCKET_TIMEOUT_MS = 50000;

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}

package com.speakup.dfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItemPlateReviewAdapter extends RecyclerView.Adapter<ListItemPlateReviewAdapter.ListItemPlateViewHolder> {

    private Context mCtx;
    private List<ListItemPlateReviews> listItemPlateReviews;

    public ListItemPlateReviewAdapter(Context mCtx, List<ListItemPlateReviews> listItemPlateReviews) {
        this.mCtx = mCtx;
        this.listItemPlateReviews = listItemPlateReviews;
    }


    @NonNull
    @Override
    public ListItemPlateReviewAdapter.ListItemPlateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_review, null);
        return new ListItemPlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemPlateReviewAdapter.ListItemPlateViewHolder holder, int position) {
        ListItemPlateReviews listPlateReview = listItemPlateReviews.get(position);

        //holder.vehicle.setText(listPlateReview.getVehicle());
        holder.narrative1.setText(listPlateReview.getNarrative());
        holder.ratings1.setText(String.valueOf(listPlateReview.getRatings()));
        //holder.body_plate.setText(listPlateReview.getBody_plate());
        holder.username1.setText(listPlateReview.getUsername());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ListItemPlateViewHolder extends RecyclerView.ViewHolder {

//        TextView vehicle, narrative, ratings, body_plate, username;
        TextView narrative1, ratings1, username1;

        public ListItemPlateViewHolder(@NonNull View itemView) {
            super(itemView);

            //vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative1 = itemView.findViewById(R.id.review1);
            ratings1 = itemView.findViewById(R.id.ratecount1);
            //body_plate = itemView.findViewById(R.id.body_plate);
            username1 = itemView.findViewById(R.id.username_text1);
        }

    }
}

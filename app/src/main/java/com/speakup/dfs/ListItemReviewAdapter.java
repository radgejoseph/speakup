package com.speakup.dfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListItemReviewAdapter extends RecyclerView.Adapter<ListItemReviewAdapter.ListItemViewHolder> {

    private Context mCtx;
    private List<ListItemReviews> listItemReviews;

    public ListItemReviewAdapter(Context mCtx, List<ListItemReviews> listItemReviews) {
        this.mCtx = mCtx;
        this.listItemReviews = listItemReviews;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_your_review, null);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItemReviews listReviewItem = listItemReviews.get(position);

        holder.vehicle.setText(listReviewItem.getVehicle());
        holder.narrative.setText(listReviewItem.getNarrative());
        holder.ratings.setText(String.valueOf(listReviewItem.getRatings()));
        holder.body_plate.setText(listReviewItem.getBody_plate());
        holder.textdatecreated.setText(listReviewItem.getTimestampL());
    }

    @Override
    public int getItemCount() {
        return listItemReviews.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle, narrative, ratings, body_plate, textdatecreated;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative = itemView.findViewById(R.id.review);
            ratings = itemView.findViewById(R.id.ratecount);
            body_plate = itemView.findViewById(R.id.body_plate);
            textdatecreated = itemView.findViewById(R.id.textdatecreated);
        }
    }
}

package com.speakup.dfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

//public class ListItemReviewAdapter extends RecyclerView.Adapter<ListItemReviewAdapter.ListItemHoder> {
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
        //ListItemViewHolder holder = new ListItemViewHolder(view);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItemReviews listReviewItem = listItemReviews.get(position);

        holder.vehicle.setText(listReviewItem.getVehicle());
        holder.narrative.setText(listReviewItem.getNarrative());
        holder.ratings.setText(String.valueOf(listReviewItem.getRatings()));
        holder.body_plate.setText(listReviewItem.getBody_plate());
    }

    @Override
    public int getItemCount() {
        return listItemReviews.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle, narrative, ratings, body_plate;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative = itemView.findViewById(R.id.review);
            ratings = itemView.findViewById(R.id.ratecount);
            body_plate = itemView.findViewById(R.id.body_plate);
        }
    }
//    private List<ListItemReviews> itemList;
//    private List<ListItemReviews> itemListFull;
//    private OnItemListener mOnItemListener;
//
//    public ListItemReviewAdapter(List<ListItemReviews> itemList, OnItemListener onItemListener) {
//        this.itemList = itemList;
//        itemListFull = new ArrayList<>(itemList);
//        this.mOnItemListener = onItemListener;
//    }
//
//    @NonNull
//    @Override
//    public ListItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_review, null);
//        ListItemHoder listItemHoder = new ListItemHoder(view, mOnItemListener);
//        return new ListItemHoder(view, mOnItemListener);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ListItemHoder holder, int position) {
//        ListItemReviews listItemReviews = itemList.get(position);
//
//        holder.textPlate.setText(listItemReviews.getPlateL());
//        holder.textVehicle.setText(listItemReviews.getVehicleL());
//        holder.textReview.setText(listItemReviews.getReviewL());
//        holder.ratecount.setText(listItemReviews.getRatcountL());
//        holder.ratingBar.setNumStars(listItemReviews.getRatcountL());
//    }
//
//    @Override
//    public int getItemCount() {
//        return itemList.size();
//    }
//
//    class ListItemHoder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//
//        TextView textPlate;
//        TextView textReview;
//        TextView textVehicle;
//        RatingBar ratingBar;
//        TextView ratecount;
//        OnItemListener onItemListener;
//
//        public ListItemHoder(@NonNull View itemView, OnItemListener onItemListener) {
//            super(itemView);
//
//            textPlate = itemView.findViewById(R.id.body_plate);
//            textReview = itemView.findViewById(R.id.review);
//            textVehicle = itemView.findViewById(R.id.vehicle_type);
//            ratecount = itemView.findViewById(R.id.ratecount);
//            this.onItemListener = onItemListener;
//
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            onItemListener.onItemClick(getAdapterPosition());
//        }
//    }
//
//    public interface OnItemListener{
//        void onItemClick(int position);
//    }
//
//    public void filterList(ArrayList<ListItemReviews> filteredList) {
//        itemList = filteredList;
//        notifyDataSetChanged();
//    }

}

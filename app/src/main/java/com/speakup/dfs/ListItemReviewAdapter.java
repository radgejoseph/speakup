package com.speakup.dfs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListItemReviewAdapter extends RecyclerView.Adapter<ListItemReviewAdapter.ListItemHoder> {

    private List<ListItemReviews> itemListReview;
    private OnItemListener mOnItemListener;

    public ListItemReviewAdapter(List<ListItemReviews> itemList, OnItemListener onItemListener) {
        this.itemListReview = itemListReview;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ListItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_review, null);
        ListItemHoder listItemHoder = new ListItemHoder(view, mOnItemListener);
        return new ListItemHoder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHoder holder, int position) {
        ListItemReviews listItemReviews = itemListReview.get(position);

        holder.textPlate.setText(listItemReviews.getPlateL());
        holder.textUsername.setText(listItemReviews.getUsernameL());
        holder.textReview.setText(listItemReviews.getReviewL());
        holder.ratingBar.setNumStars(listItemReviews.getRatcountL());
    }

    @Override
    public int getItemCount() {
        return itemListReview.size();
    }

    class ListItemHoder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView textPlate;
        TextView textReview;
        TextView textUsername;
        RatingBar ratingBar;
        OnItemListener onItemListener;

        public ListItemHoder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            textPlate = itemView.findViewById(R.id.plate_number);
            textReview = itemView.findViewById(R.id.review);
            textUsername = itemView.findViewById(R.id.txtUsername);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public void filterList(ArrayList<ListItemReviews> filteredList) {
        itemListReview = filteredList;
        notifyDataSetChanged();
    }

}

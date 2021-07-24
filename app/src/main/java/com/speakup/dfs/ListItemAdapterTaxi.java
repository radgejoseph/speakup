package com.speakup.dfs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListItemAdapterTaxi extends RecyclerView.Adapter<ListItemAdapterTaxi.ListItemHoder> implements Filterable {

    private List<ListItem> itemList;
    private List<ListItem> itemListFull;
    private OnItemListener mOnItemListener;

    public ListItemAdapterTaxi(List<ListItem> itemList, OnItemListener onItemListener) {
        this.itemList = itemList;
        itemListFull = new ArrayList<>(itemList);
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ListItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_taxi, null);
        ListItemHoder listItemHoder = new ListItemHoder(view, mOnItemListener);
        return new ListItemHoder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHoder holder, int position) {
        ListItem listItem = itemList.get(position);

        holder.textPlate.setText(listItem.getPlateL());
        holder.textRatings.setText(String.valueOf(listItem.getRatingsL()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ListItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(itemListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ListItem item : itemListFull) {
                    if (item.getPlateL().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class ListItemHoder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textPlate, textRatings;
        OnItemListener onItemListener;

        public ListItemHoder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            textPlate = itemView.findViewById(R.id.plate_number);
            textRatings = itemView.findViewById(R.id.ratecount);
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

    public void filterList(ArrayList<ListItem> filteredList) {
        itemList = filteredList;
        notifyDataSetChanged();
    }

}

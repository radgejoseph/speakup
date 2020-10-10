package com.speakup.dfs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHoder> {

    //private Context context;
    private List<ListItem> itemList;
    private OnItemListener mOnItemListener;

    public ListItemAdapter(List<ListItem> itemList, OnItemListener onItemListener) {
        //this.context = context;
        this.itemList = itemList;
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ListItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, null);
        ListItemHoder listItemHoder = new ListItemHoder(view, mOnItemListener);
        return new ListItemHoder(view, mOnItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHoder holder, int position) {
        ListItem listItem = itemList.get(position);

        holder.textPlate.setText(listItem.getPlateL());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ListItemHoder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageIcon;
        TextView textPlate;
        OnItemListener onItemListener;

        public ListItemHoder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.taxicle_icon);
            textPlate = itemView.findViewById(R.id.plate_number);
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

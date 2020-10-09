package com.speakup.dfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ListItemHoder> {

    private Context context;
    private List<ListItem> itemList;

    public ListItemAdapter(Context context, List<ListItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ListItemHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_view, null);
        ListItemHoder listItemHoder = new ListItemHoder(view);
        return new ListItemHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHoder holder, int position) {
        ListItem listItem = itemList.get(position);

        holder.textPlate.setText(listItem.getPlateL());
        holder.imageIcon.setImageDrawable(context.getResources().getDrawable(listItem.getImageL()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ListItemHoder extends RecyclerView.ViewHolder {

        ImageView imageIcon;
        TextView textPlate;

        public ListItemHoder(@NonNull View itemView) {
            super(itemView);

            imageIcon = itemView.findViewById(R.id.taxicle_icon);
            textPlate = itemView.findViewById(R.id.plate_number);
        }
    }
}

package com.speakup.dfs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ListItemComplaintAdapter extends RecyclerView.Adapter<ListItemComplaintAdapter.ListItemViewHolder> {

    private Context mCtx;
    private List<ListItemComplaint> listItemComplaints;

    public ListItemComplaintAdapter(Context mCtx, List<ListItemComplaint> listItemComplaints) {
        this.mCtx = mCtx;
        this.listItemComplaints = listItemComplaints;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_complaint, null);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItemComplaint listComplaintItem = listItemComplaints.get(position);

        holder.vehicle.setText(listComplaintItem.getVehicle());
        holder.narrative.setText(listComplaintItem.getNarrative());
        holder.body_plate.setText(listComplaintItem.getBody_plate());
        holder.date_time.setText(listComplaintItem.getDate() +"  |  "+ listComplaintItem.getTime());
        holder.image_name.setText(listComplaintItem.getImage_name());
        holder.status.setText(listComplaintItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return listItemComplaints.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle, narrative, body_plate, date_time, image_name, status;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative = itemView.findViewById(R.id.review);
            body_plate = itemView.findViewById(R.id.body_plate);
            date_time = itemView.findViewById(R.id.date_time);
            image_name = itemView.findViewById(R.id.image_name);
            status = itemView.findViewById(R.id.statustext);
        }
    }
}

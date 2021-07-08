package com.speakup.dfs;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;

public class ListItemComndComptAdapter extends RecyclerView.Adapter<ListItemComndComptAdapter.ListItemViewHolder> {

    private Context mCtx;
    private List<ListItemCommendComplaint> listItemCommendComplaints;

    public ListItemComndComptAdapter(Context mCtx, List<ListItemCommendComplaint> listItemCommendComplaints) {
        this.mCtx = mCtx;
        this.listItemCommendComplaints = listItemCommendComplaints;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_commend_complaint, null);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItemCommendComplaint listCommendComplaintItem = listItemCommendComplaints.get(position);

        holder.vehicle.setText(listCommendComplaintItem.getVehicle());
        holder.narrative.setText(listCommendComplaintItem.getNarrative());
        holder.body_plate.setText(listCommendComplaintItem.getBody_plate());
        holder.date.setText(listCommendComplaintItem.getDate());
        holder.time.setText(listCommendComplaintItem.getTime());
        holder.image_name.setText(listCommendComplaintItem.getImage_name());
    }

    @Override
    public int getItemCount() {
        return listItemCommendComplaints.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle, narrative, body_plate, date, time, image_name;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative = itemView.findViewById(R.id.review);
            body_plate = itemView.findViewById(R.id.body_plate);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            image_name = itemView.findViewById(R.id.image_name);
        }
    }
}

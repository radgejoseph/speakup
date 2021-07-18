package com.speakup.dfs;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.List;

public class ListItemCommendAdapter extends RecyclerView.Adapter<ListItemCommendAdapter.ListItemViewHolder> {

    private Context mCtx;
    private List<ListItemCommend> listItemCommends;

    public ListItemCommendAdapter(Context mCtx, List<ListItemCommend> listItemCommends) {
        this.mCtx = mCtx;
        this.listItemCommends = listItemCommends;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_view_commend, null);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder holder, int position) {
        ListItemCommend listCommendItem = listItemCommends.get(position);

        holder.vehicle.setText(listCommendItem.getVehicle());
        holder.narrative.setText(listCommendItem.getNarrative());
        holder.body_plate.setText(listCommendItem.getBody_plate());
        holder.date_time.setText(listCommendItem.getDate() +"  |  "+ listCommendItem.getTime());
        holder.image_name.setText(listCommendItem.getImage_name());
    }

    @Override
    public int getItemCount() {
        return listItemCommends.size();
    }

    class ListItemViewHolder extends RecyclerView.ViewHolder{

        TextView vehicle, narrative, body_plate, date_time, image_name;

        public ListItemViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle = itemView.findViewById(R.id.vehicle_type);
            narrative = itemView.findViewById(R.id.review);
            body_plate = itemView.findViewById(R.id.body_plate);
            date_time = itemView.findViewById(R.id.date_time);
            image_name = itemView.findViewById(R.id.image_name);
        }
    }
}

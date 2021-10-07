package id.erris.bajakah.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import id.erris.bajakah.R;
import id.erris.bajakah.activity.FormReminderActivity;
import id.erris.bajakah.model.Reminder;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {
    private Context context;
    public static List<Reminder> data;

    public ReminderAdapter(Context context, List<Reminder> data) {
        this.context = context;
        ReminderAdapter.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.lblWaktu.setText(data.get(position).getJam().substring(0, 5));
        holder.lblKeteranganId.setText(data.get(position).getKeteranganId());
        holder.lblKeteranganEn.setText(data.get(position).getKeteranganEn());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView lblWaktu;
        private final TextView lblKeteranganId;
        private final TextView lblKeteranganEn;

        public ViewHolder(View itemView) {
            super(itemView);

            context = itemView.getContext();
            lblWaktu = itemView.findViewById(R.id.lblContentReminderWaktu);
            lblKeteranganId = itemView.findViewById(R.id.lblContentReminderId);
            lblKeteranganEn = itemView.findViewById(R.id.lblContentReminderEn);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FormReminderActivity.class);
                intent.putExtra("id", data.get(getAdapterPosition()).getId().toString());
                intent.putExtra("keteranganId", data.get(getAdapterPosition()).getKeteranganId());
                intent.putExtra("keteranganEn", data.get(getAdapterPosition()).getKeteranganEn());
                intent.putExtra("waktu", data.get(getAdapterPosition()).getJam().toString());
                intent.putExtra("sunday", data.get(getAdapterPosition()).isSunday());
                intent.putExtra("monday", data.get(getAdapterPosition()).isMonday());
                intent.putExtra("tuesday", data.get(getAdapterPosition()).isTuesday());
                intent.putExtra("wednesday", data.get(getAdapterPosition()).isWednesday());
                intent.putExtra("thursday", data.get(getAdapterPosition()).isThursday());
                intent.putExtra("friday", data.get(getAdapterPosition()).isFriday());
                intent.putExtra("saturday", data.get(getAdapterPosition()).isSaturday());

                context.startActivity(intent);
            });
        }
    }
}

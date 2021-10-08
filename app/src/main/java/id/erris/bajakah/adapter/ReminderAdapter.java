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
import id.erris.bajakah.utils.Constants;

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
                intent.putExtra(Constants.REMINDER_ID, data.get(getAdapterPosition()).getId().toString());
                intent.putExtra(Constants.REMINDER_KETERANGAN_ID, data.get(getAdapterPosition()).getKeteranganId());
                intent.putExtra(Constants.REMINDER_KETERANGAN_EN, data.get(getAdapterPosition()).getKeteranganEn());
                intent.putExtra(Constants.REMINDER_WAKTU, data.get(getAdapterPosition()).getJam().toString());
                intent.putExtra(Constants.REMINDER_SUNDAY, data.get(getAdapterPosition()).isSunday());
                intent.putExtra(Constants.REMINDER_MONDAY, data.get(getAdapterPosition()).isMonday());
                intent.putExtra(Constants.REMINDER_TUESDAY, data.get(getAdapterPosition()).isTuesday());
                intent.putExtra(Constants.REMINDER_WEDNESDAY, data.get(getAdapterPosition()).isWednesday());
                intent.putExtra(Constants.REMINDER_THURSDAY, data.get(getAdapterPosition()).isThursday());
                intent.putExtra(Constants.REMINDER_FRIDAY, data.get(getAdapterPosition()).isFriday());
                intent.putExtra(Constants.REMINDER_SATURDAY, data.get(getAdapterPosition()).isSaturday());
                intent.putExtra(Constants.REMINDER_STATUS, "ubah");

                context.startActivity(intent);
            });
        }
    }
}

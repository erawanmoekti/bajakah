package id.erris.bajakah.service;

import static android.content.Intent.FLAG_INCLUDE_STOPPED_PACKAGES;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Calendar;

import id.erris.bajakah.model.Reminder;

public class ReminderService {
    private Context context;

    public ReminderService(Context context)
    {
        this.context = context;
    }

    public void createReminder(Reminder reminder) {
        String id = reminder.getId().toString().replaceAll("[^\\d.]", "").substring(0, 7);
        String jam = reminder.getJam().substring(0, 2);
        String menit = reminder.getJam().substring(3, 5);

        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.addFlags(FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra(ReminderBroadcast.NOTIFICATION_ID, id);
        intent.putExtra(ReminderBroadcast.JAM_ALARM, jam);
        intent.putExtra(ReminderBroadcast.MENIT_ALARM, menit);
        intent.putExtra(ReminderBroadcast.NOTIFICATION_MESSAGE_ID, reminder.getKeteranganId());
        intent.putExtra(ReminderBroadcast.NOTIFICATION_MESSAGE_EN, reminder.getKeteranganEn());
        intent.putExtra(ReminderBroadcast.REPEAT_SUNDAY, reminder.isSunday());
        intent.putExtra(ReminderBroadcast.REPEAT_MONDAY, reminder.isMonday());
        intent.putExtra(ReminderBroadcast.REPEAT_TUESDAY, reminder.isTuesday());
        intent.putExtra(ReminderBroadcast.REPEAT_WEDNESDAY, reminder.isWednesday());
        intent.putExtra(ReminderBroadcast.REPEAT_THURSDAY, reminder.isThursday());
        intent.putExtra(ReminderBroadcast.REPEAT_FRIDAY, reminder.isFriday());
        intent.putExtra(ReminderBroadcast.REPEAT_SATURDAY, reminder.isSaturday());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Integer.parseInt(id), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jam));
        calendar.set(Calendar.MINUTE, Integer.parseInt(menit));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
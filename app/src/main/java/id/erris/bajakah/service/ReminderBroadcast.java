package id.erris.bajakah.service;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import id.erris.bajakah.R;
import id.erris.bajakah.activity.MainActivity;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReminderBroadcast extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification_id";
    public static String JAM_ALARM = "jam";
    public static String MENIT_ALARM = "menit";
    public static String NOTIFICATION_MESSAGE_ID = "notification_message_id";
    public static String NOTIFICATION_MESSAGE_EN = "notification_message_en";
    public static String REPEAT_SUNDAY = "sunday";
    public static String REPEAT_MONDAY = "monday";
    public static String REPEAT_TUESDAY = "tuesday";
    public static String REPEAT_WEDNESDAY = "wednesday";
    public static String REPEAT_THURSDAY = "thursday";
    public static String REPEAT_FRIDAY = "friday";
    public static String REPEAT_SATURDAY = "saturday";

    private Context myContext;
    private String notificationId, messageId, messageEn;
    private String jam, menit;
    private boolean repeatSunday, repeatMonday, repeatTuesday, repeatWednesday, repeatThursday, repeatFriday,repeatSaturday;

    @Override
    public void onReceive(Context context, Intent intent) {
        myContext = context;
        notificationId = intent.getStringExtra(NOTIFICATION_ID);
        jam = intent.getStringExtra(JAM_ALARM);
        menit = intent.getStringExtra(MENIT_ALARM);
        messageId = intent.getStringExtra(NOTIFICATION_MESSAGE_ID);
        messageEn = intent.getStringExtra(NOTIFICATION_MESSAGE_EN);
        repeatSunday = intent.getBooleanExtra(REPEAT_SUNDAY, false);
        repeatMonday = intent.getBooleanExtra(REPEAT_MONDAY, false);
        repeatTuesday = intent.getBooleanExtra(REPEAT_TUESDAY, false);
        repeatWednesday = intent.getBooleanExtra(REPEAT_WEDNESDAY, false);
        repeatThursday = intent.getBooleanExtra(REPEAT_THURSDAY, false);
        repeatFriday = intent.getBooleanExtra(REPEAT_FRIDAY, false);
        repeatSaturday = intent.getBooleanExtra(REPEAT_SATURDAY, false);

        createAlarm();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        if (day == Calendar.SUNDAY && repeatSunday)
            createNotifikasi();
        else if (day == Calendar.MONDAY && repeatMonday)
            createNotifikasi();
        else if (day == Calendar.TUESDAY && repeatTuesday)
            createNotifikasi();
        else if (day == Calendar.WEDNESDAY && repeatWednesday)
            createNotifikasi();
        else if (day == Calendar.THURSDAY && repeatThursday)
            createNotifikasi();
        else if (day == Calendar.FRIDAY && repeatFriday)
            createNotifikasi();
        else if (day == Calendar.SATURDAY && repeatSaturday)
            createNotifikasi();
    }

    private void createNotifikasi() {
        Intent targetIntent = new Intent(myContext, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(myContext, Integer.parseInt(notificationId), targetIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(myContext, notificationId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(myContext.getResources().getString(R.string.app_name))
                .setContentText(messageId + " (" + messageEn + ")")
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_ALARM_ALERT_URI)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(activity);

        NotificationManager notificationManager = (NotificationManager) myContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(notificationId, "Notifikasi", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId(notificationId);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        assert notificationManager != null;
        notificationManager.notify(Integer.parseInt(notificationId), builder.build());
    }

    private void createAlarm()
    {
        Intent notificationIntent = new Intent(myContext, ReminderBroadcast.class);
        notificationIntent.putExtra(ReminderBroadcast.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(ReminderBroadcast.JAM_ALARM, jam);
        notificationIntent.putExtra(ReminderBroadcast.MENIT_ALARM, menit);
        notificationIntent.putExtra(ReminderBroadcast.NOTIFICATION_MESSAGE_ID, messageId);
        notificationIntent.putExtra(ReminderBroadcast.NOTIFICATION_MESSAGE_EN, messageEn);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_SUNDAY, repeatSunday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_MONDAY, repeatMonday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_TUESDAY, repeatTuesday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_WEDNESDAY, repeatWednesday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_THURSDAY, repeatThursday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_FRIDAY, repeatFriday);
        notificationIntent.putExtra(ReminderBroadcast.REPEAT_SATURDAY, repeatSaturday);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(myContext, Integer.parseInt(notificationId), notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jam));
        calendar.set(Calendar.MINUTE, Integer.parseInt(menit));

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        AlarmManager alarmManager = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}

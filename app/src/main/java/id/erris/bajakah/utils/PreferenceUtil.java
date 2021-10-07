package id.erris.bajakah.utils;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import id.erris.bajakah.model.Reminder;

public class PreferenceUtil {

    private static final String TAG = PreferenceUtil.class.getSimpleName();

    private static final String USER_ID_PREF = "pref_user_id";
    private static final String USER_REMINDER_PREF = "pref_reminder";

    public static @Nullable void setId(Context context, String id) {
        setStringPreference(context, USER_ID_PREF, id);
    }

    public static @Nullable String getId(Context context) {
        return getStringPreference(context, USER_ID_PREF, "");
    }

    public static @Nullable void setReminder(Context context, List<Reminder> reminder) {
        Gson gson = new Gson();
        String reminderJson = gson.toJson(reminder);
        setStringPreference(context, USER_REMINDER_PREF, reminderJson);
    }

    public static @Nullable List<Reminder> getReminder(Context context) {
        Gson gson = new Gson();
        String reminderJson = getStringPreference(context, USER_REMINDER_PREF, "");

        if (reminderJson.equals("")) {
            return null;
        } else {
            Type type = new TypeToken<List<Reminder>>(){}.getType();
            return gson.fromJson(reminderJson, type);
        }
    }
    /* END OF GetSet */

    public static void setBooleanPreference(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void setStringPreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    private static int getIntegerPreference(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    private static void setIntegerPrefrence(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

    private static boolean setIntegerPrefrenceBlocking(Context context, String key, int value) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).commit();
    }

    private static long getLongPreference(Context context, String key, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue);
    }

    private static void setLongPreference(Context context, String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply();
    }
}

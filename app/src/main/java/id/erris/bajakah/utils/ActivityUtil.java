package id.erris.bajakah.utils;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

public class ActivityUtil {
    public static void requestFocus(Activity activity, View view) {
        if ( view.requestFocus() ) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}

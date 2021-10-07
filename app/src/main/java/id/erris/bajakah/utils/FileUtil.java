package id.erris.bajakah.utils;

import android.content.Context;
import android.os.Environment;

import androidx.core.content.ContextCompat;

import java.io.File;

public class FileUtil {
    public static String getRootDirPath(Context context) {
        String path;

        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            path = ContextCompat.getExternalFilesDirs(
                    context.getApplicationContext(),
                    null
            )[0].getAbsolutePath();
        } else {
            path = context.getApplicationContext().getFilesDir().getAbsolutePath();
        }

        return path;
    }
}

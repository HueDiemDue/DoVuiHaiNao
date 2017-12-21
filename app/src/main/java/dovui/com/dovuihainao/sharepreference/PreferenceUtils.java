package dovui.com.dovuihainao.sharepreference;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by hue on 02/06/2017.
 */

public class PreferenceUtils {
    public static void save(String key, boolean value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }


    public static boolean getBooleanFromPreference(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, true);
    }

    public static void save(String key, int value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }

    public static int getIntFromPreference(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
    }


    public static void save(String key, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    public static String getStringFromPreference(String key, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }
}

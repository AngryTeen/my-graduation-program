package edu.learning.jonnypeng.graduationproject.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jonny.peng on 2016/4/27 10:00.
 * Email:Jonny.peng@tinno.com
 */
public class SharedPreferenceUtil {
    private static final String SP_NAME = "sp_name";

    public static void putIntegerParam(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putStringParam(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }
}

package edu.learning.jonnypeng.graduationproject.utils;

import android.util.Log;

/**
 * Created by jonny.peng on 2016/4/27 15:57.
 * Email:Jonny.peng@tinno.com
 */
public class ErrorUtil {
    private static final String TAG = "ErrorUtil";

    public static void insertIntoSqliteFailed() {
        Log.e(TAG, "INSERT DATABASE ERROR");
    }


}

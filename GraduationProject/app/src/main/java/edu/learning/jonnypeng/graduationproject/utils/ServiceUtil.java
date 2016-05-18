package edu.learning.jonnypeng.graduationproject.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by jonny.peng on 2016/4/20 13:15.
 * 邮箱:Jonny.peng@tinno.com
 */
public class ServiceUtil {
    private static final String TAG = "ServiceUtil";
    private Context mContext;

    public ServiceUtil(Context context) {
        this.mContext = context;
    }

    /**
     * check service is running
     *
     * @param className service name
     *
     * @return true is running, false is not running
     */
    public boolean isServiceRunning(String className) {

        Log.d(TAG, "service name : " + className);
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(100);
        for (int i = 0; i < serviceList.size(); i++) {
            Log.d(TAG, "service list [" + i + "] name : " + serviceList.get(i).service.getClassName());
            if (serviceList.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}

package edu.learning.jonnypeng.graduationproject.Thread;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.TimerTask;

import edu.learning.jonnypeng.graduationproject.entities.AppUsedInfo;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.SharedPreferenceUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;
import edu.learning.jonnypeng.graduationproject.utils.ValidateUtil;

/**
 * Created by jonny.peng on 2016/4/29 09:21.
 * 邮箱:Jonny.peng@tinno.com
 */
public class GetAppInfoTimerTask extends TimerTask {
    private static final String TAG = "GetAppInfoTimerTask";
    private static final String PRE_APP_NAME = "preAppName";
    private static final String PRE_APP_START_TIME = "preStartTime";
    private static final int PREVIOUS_ONE_SECONDS = 1000;
    private Context mContext;
    private String preAppName;
    private String preStartTime;
    private AppUsedInfo mAppUsedInfo;

    public GetAppInfoTimerTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void run() {
        String appNme = getRunningApp(mContext);
        String time = new DateUtil().getCurrentTime();
        Log.d(TAG, "app name : " + appNme);
        if (!ValidateUtil.isNull(appNme)) {
            preAppName = SharedPreferenceUtil.getString(mContext, PRE_APP_NAME);
            Log.d(TAG, "previous app name : " + preAppName);
            if (!ValidateUtil.isNull(preAppName)) {
                Log.d(TAG, "previous app name is not null");
                if (!preAppName.equals(appNme)) {

                    preStartTime = SharedPreferenceUtil.getString(mContext, PRE_APP_START_TIME);
                    //start to storage
                    mAppUsedInfo = new AppUsedInfo();
                    mAppUsedInfo.setAppName(preAppName);
                    mAppUsedInfo.setAppStartTime(preStartTime);
                    mAppUsedInfo.setAppStopTime(time);
                    Log.d(TAG, "app start time : " + preStartTime);
                    Log.d(TAG, "app stop time : " + time);
                    AppInfoStorageRunnable runnable = new AppInfoStorageRunnable(mContext, mAppUsedInfo);
                    new Thread(runnable, StringConstant.APP_INFO_STORAGE_THREAD).start();
                } else {
                    return;
                }
            }
            //set previous app
            SharedPreferenceUtil.putStringParam(mContext, PRE_APP_NAME, appNme);
            SharedPreferenceUtil.putStringParam(mContext, PRE_APP_START_TIME, time);
        }

    }

    public String getRunningApp(Context context) {

        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        long ts = System.currentTimeMillis();
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ts - PREVIOUS_ONE_SECONDS, ts);
        if (queryUsageStats == null || queryUsageStats.isEmpty()) {
            return null;

        }

        UsageStats recentStats = null;
        for (UsageStats usageStats : queryUsageStats) {
            if (recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                recentStats = usageStats;
            }
        }//end of for (UsageStats usageStats : queryUsageStats)
        return recentStats.getPackageName();

    }
}

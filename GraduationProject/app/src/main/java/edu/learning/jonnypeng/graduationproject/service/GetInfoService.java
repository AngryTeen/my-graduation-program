package edu.learning.jonnypeng.graduationproject.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Timer;

import edu.learning.jonnypeng.graduationproject.Thread.GetAppInfoTimerTask;
import edu.learning.jonnypeng.graduationproject.Thread.GetSensorInfoTimerTask;
import edu.learning.jonnypeng.graduationproject.listener.CallInfoListener;
import edu.learning.jonnypeng.graduationproject.observer.SmsObserver;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;


public class GetInfoService extends Service {
    protected static final String TAG = "GetInfoService";
    //the delay time of get sensor timer task
    private static final int GET_SENSOR_INFO_DELAY_TIME = 5000;

    //the period of get sensor timer task
    private static final int GET_SENSOR_INFO_PERIOD = 5 * 60 * 1000;

    //the delay of get app information
    private static final int GET_APP_INFO_DELAY_TIME = 1000;

    //the period of get app
    private static final int GET_APP_INFO_PERIOD = 1000;

    //the period of get GPS
    private static final long MIN_TIME = 2 * 1000;
    private static final float MIN_DISTANCE = 10;


    private String mCurrentTime;

    private Timer mTimer;
    private GetSensorInfoTimerTask mSensorTimerTask;
    private GetAppInfoTimerTask mAppInfoTimerTask;
    private LocationManager mLocationManager;

    public GetInfoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "The Service Started");
        mCurrentTime = new DateUtil().getCurrentTime();
        getAppUsedInfo();
        //new GetSensorInfo(this);
        getCallInfo();
        getMessageInfo();
        getSensorInfo();

        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Intent localIntent = new Intent(this, GetInfoService.class);
        this.startService(localIntent);
    }

    private void getAppUsedInfo() {
        mTimer = new Timer();
        mAppInfoTimerTask = new GetAppInfoTimerTask(this);
        mTimer.schedule(mAppInfoTimerTask, GET_APP_INFO_DELAY_TIME, GET_APP_INFO_PERIOD);
    }

    private void getCallInfo() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Listen call
        CallInfoListener mPhoneStateListener = new CallInfoListener(this);
        mTelephonyManager.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void getMessageInfo() {
        getContentResolver().registerContentObserver(Uri.parse("content://sms")
                , true, new SmsObserver(new Handler(), this));
    }

    //Listen Sensor
    private void getSensorInfo() {
        Log.d(TAG, "start listening Sensor");
        mTimer = new Timer();
        mSensorTimerTask = new GetSensorInfoTimerTask(this);
        mTimer.schedule(mSensorTimerTask, GET_SENSOR_INFO_DELAY_TIME, GET_SENSOR_INFO_PERIOD);
    }

}

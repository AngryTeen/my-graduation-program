package edu.learning.jonnypeng.graduationproject.listener;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.learning.jonnypeng.graduationproject.Thread.CallInfoStorageRunnable;
import edu.learning.jonnypeng.graduationproject.handler.CheckInsertHandler;
import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.entities.CallInfo;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/27 15:35.
 * Email:Jonny.peng@tinno.com
 */
public class CallInfoListener extends PhoneStateListener {
    private static final String TAG = "CallInfoListener";
    CallInfo mCallInfo;
    DBAdapter mAdapter;
    SQLiteDatabase mDatabase;
    CheckInsertHandler mHandler;
    List<CallInfo> mCallInfoList;
    Context mContext;

    public CallInfoListener(Context context) {
        mContext = context;
        mAdapter = new DBAdapter(context);
        mCallInfo = new CallInfo();
        mDatabase = mAdapter.open();
        mHandler = new CheckInsertHandler();
        mCallInfoList = new ArrayList<>();
    }


    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                if (mCallInfo.getCallStartTime() == null) {
                    break;
                }
                mCallInfo.setCallStopTime(new DateUtil().getCurrentTime());
                Log.d(TAG, "Call stop time : " + new DateUtil().getCurrentTime());
                Log.d(TAG, "Get date from CallInfo : " + mCallInfo.getCallStartTime() + mCallInfo.getRingTime());
                //start a thread to storage call information
                CallInfoStorageRunnable callInfoStorageRunnable = new CallInfoStorageRunnable(mContext, mCallInfo);

                new Thread(callInfoStorageRunnable, StringConstant.CALL_INFO_STORAGE_THREAD).start();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                mCallInfo.setCallStartTime(new DateUtil().getCurrentTime());
                Log.d(TAG, "Call start time: " + new DateUtil().getCurrentTime());
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                mCallInfo.setRingTime(new DateUtil().getCurrentTime());
                Log.d(TAG, "call ring time" + new DateUtil().getCurrentTime());
                break;
            default:
                break;
        }
    }
}

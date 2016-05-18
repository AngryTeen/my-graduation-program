package edu.learning.jonnypeng.graduationproject.Thread;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.entities.CallInfo;
import edu.learning.jonnypeng.graduationproject.handler.CheckInsertHandler;
import edu.learning.jonnypeng.graduationproject.service.NetService;
import edu.learning.jonnypeng.graduationproject.utils.ErrorUtil;
import edu.learning.jonnypeng.graduationproject.utils.GsonUtil;
import edu.learning.jonnypeng.graduationproject.utils.IntConst;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/27 16:04.
 * Email:Jonny.peng@tinno.com
 */
public class CallInfoStorageRunnable implements Runnable {
    private static final String TAG = "CallInfoStorageRunnable";
    CallInfo mCallInfo;
    DBAdapter mAdapter;
    SQLiteDatabase mDatabase;
    long insertCheck;
    CheckInsertHandler mHandler;
    List<CallInfo> mCallInfoList;
    Context mContext;

    public CallInfoStorageRunnable(Context context, CallInfo callInfo) {
        super();
        mContext = context;
        mCallInfo = callInfo;


    }

    @Override
    public void run() {
        mAdapter = new DBAdapter(mContext);
        mDatabase = mAdapter.open();
        Log.d(TAG, "start to storage call information");
        mCallInfoList = new ArrayList<>();
        synchronized (mContext)
        {
            //Insert call information to database
            insertCheck = mAdapter
                    .insert(IntConst.CALL_INFO_CLASS_NAME,
                            DBAdapter.getDbCallTable(), mCallInfo);
            //send insert result message to check insert handler
            if (-1 == insertCheck) {
                ErrorUtil.insertIntoSqliteFailed();
            }
            //Get call information count from sqlite -> if the count is times of CALL_INFO_MAX_NUM
            //-> if the network flow -> send sensor data to server
            int callInfoCount = mAdapter.getCount(DBAdapter.getDbCallTable());
            Log.i(TAG, "callInfoCount : " + callInfoCount);
            if (callInfoCount >= IntConst.CALL_INFO_MAX_NUM
                    && (0 == (callInfoCount % IntConst.CALL_INFO_MAX_NUM))) {
                //Get data from sqlite -> change data to jsonString -> send to server
                getDataFromSqlite();
                Log.d(TAG, "start send call information to server");
                sendDataToServer();

            }
            mAdapter.close();
            mAdapter = null;
        }

    }

    /**
     * method of send CallInfo to server
     */
    private void sendDataToServer() {
        Intent intent = new Intent(mContext, NetService.class);
        Bundle bundle = new Bundle();
        bundle.putString(StringConstant.URL, StringConstant.CALL_URL);
        bundle.putString(StringConstant.CALL_INFO_JSON, new GsonUtil().getJsonData(mCallInfoList));
        intent.putExtras(bundle);
        mContext.startService(intent);
    }

    /**
     * Method of get data from sqlite database
     */
    private void getDataFromSqlite() {
        CallInfo[] callInfos =
                (CallInfo[]) mAdapter.getAllData(DBAdapter.getDbCallTable());
        for (int i = 0; i < callInfos.length; i++) {
            mCallInfoList.add(callInfos[i]);
        }

    }
}

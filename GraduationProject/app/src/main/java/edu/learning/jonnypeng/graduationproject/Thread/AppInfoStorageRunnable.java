package edu.learning.jonnypeng.graduationproject.Thread;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.entities.AppUsedInfo;
import edu.learning.jonnypeng.graduationproject.handler.CheckInsertHandler;
import edu.learning.jonnypeng.graduationproject.service.NetService;
import edu.learning.jonnypeng.graduationproject.utils.ErrorUtil;
import edu.learning.jonnypeng.graduationproject.utils.GsonUtil;
import edu.learning.jonnypeng.graduationproject.utils.IntConst;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/29 09:58.
 * 邮箱:Jonny.peng@tinno.com
 */
public class AppInfoStorageRunnable implements Runnable {
    private static final String TAG = "AppInfoStorageRunnable";
    AppUsedInfo mAppUsedInfo;
    DBAdapter mAdapter;
    SQLiteDatabase mDatabase;
    long insertCheck;
    CheckInsertHandler mHandler;
    List<AppUsedInfo> mAppUsedInfoList;
    Context mContext;

    public AppInfoStorageRunnable(Context context, AppUsedInfo appUsedInfo) {
        super();

        mContext = context;
        mAppUsedInfo = appUsedInfo;

    }

    @Override
    public void run() {
        mAdapter = new DBAdapter(mContext);
        mDatabase = mAdapter.open();
        mAppUsedInfoList = new ArrayList<>();
        Log.d(TAG, "start store app information");
        synchronized (mContext) {
            //Insert App information to database
            insertCheck = mAdapter
                    .insert(IntConst.APP_USER_INFO_CLASS_NAME,
                            DBAdapter.getDbAppUsedTable(), mAppUsedInfo);
            Log.e(TAG, "insertCheck : " + insertCheck);
            // check insert
            if (-1 == insertCheck) {
                ErrorUtil.insertIntoSqliteFailed();
            }

            //Get app information count from sqlite -> if the count is times of APP_MAX_NUM
            //-> send app data to server

            int appInfoCount = mAdapter.getCount(DBAdapter.getDbAppUsedTable());
            Log.i(TAG, "sensorCount" + appInfoCount);
            if (appInfoCount >= IntConst.APP_USED_INFO_MAX_NUM
                    && (0 == (appInfoCount % IntConst.APP_USED_INFO_MAX_NUM))) {

                getDataFromSqlite();
                Log.d(TAG, "start send app information to server");
                sendDataToServer();
            }

            mAdapter.close();
            mAdapter = null;
        }
    }

    private void getDataFromSqlite() {
        AppUsedInfo[] appUsedInfos =
                (AppUsedInfo[]) mAdapter.getAllData(DBAdapter.getDbAppUsedTable());
        Log.d(TAG, "appUsedInfos.length : " + appUsedInfos.length);
        for (int i = 0; i < appUsedInfos.length; i++) {
            mAppUsedInfoList.add(appUsedInfos[i]);
        }
    }

    private void sendDataToServer() {
        Intent intent = new Intent(mContext, NetService.class);
        Bundle bundle = new Bundle();
        bundle.putString(StringConstant.URL, StringConstant.APP_URL);
        Log.i(TAG, "json String : " + new GsonUtil().getJsonData(mAppUsedInfoList));
        bundle.putString(StringConstant.APP_INFO_JSON, new GsonUtil().getJsonData(mAppUsedInfoList));
        intent.putExtras(bundle);
        mContext.startService(intent);
    }

}

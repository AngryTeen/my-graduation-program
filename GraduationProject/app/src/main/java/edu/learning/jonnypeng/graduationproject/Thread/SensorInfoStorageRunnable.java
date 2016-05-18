package edu.learning.jonnypeng.graduationproject.Thread;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.entities.SensorInfo;
import edu.learning.jonnypeng.graduationproject.handler.CheckInsertHandler;
import edu.learning.jonnypeng.graduationproject.service.NetService;
import edu.learning.jonnypeng.graduationproject.utils.ErrorUtil;
import edu.learning.jonnypeng.graduationproject.utils.GsonUtil;
import edu.learning.jonnypeng.graduationproject.utils.IntConst;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/28 10:11.
 * email:Jonny.peng@tinno.com
 */
public class SensorInfoStorageRunnable implements Runnable {
    private static final String TAG = "SensorInfoStorage";
    private SensorInfo mSensorInfo;
    private DBAdapter mAdapter;
    private SQLiteDatabase mDatabase;
    long insertCheck;
    private CheckInsertHandler mHandler;
    private List<SensorInfo> mSensorInfoList;
    private Context mContext;

    public SensorInfoStorageRunnable(Context context, SensorInfo sensorInfo) {
        super();
        mContext = context;
        mSensorInfo = sensorInfo;


    }

    @Override
    public void run() {
        mAdapter = new DBAdapter(mContext);
        mDatabase = mAdapter.open();

        mSensorInfoList = new ArrayList<>();
        Log.d(TAG, "start store sensor information");
        synchronized (mContext)
        {
            //Insert sensor data to sqlite database
            insertCheck = mAdapter
                    .insert(IntConst.SENSOR_INFO_CLASS_NAME,
                            DBAdapter.getDbSensorTable(), mSensorInfo);
            //send insert result message to check insert handler
            if (-1 == insertCheck) {
                ErrorUtil.insertIntoSqliteFailed();
            }

            //Get sensor count from sqlite -> if the count is times of SENSOR_INFO_MAX_NUM
            //-> if the network flow -> send sensor data to server
            int sensorInfoCount = mAdapter.getCount(DBAdapter.getDbSensorTable());

            Log.i(TAG, "sensorCount" + sensorInfoCount);
            if (sensorInfoCount >= IntConst.SENSOR_INFO_MAX_NUM
                    && (0 == (sensorInfoCount % IntConst.SENSOR_INFO_MAX_NUM))) {

                getDataFromSqlite();
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
        bundle.putString(StringConstant.URL, StringConstant.SENSOR_URL);
        bundle.putString(StringConstant.SENSOR_INFO_JSON, new GsonUtil().getJsonData(mSensorInfoList));
        intent.putExtras(bundle);
        mContext.startService(intent);
    }

    /**
     * Method of get data from sqlite database
     */
    private void getDataFromSqlite() {
        SensorInfo[] sensorInfos =
                (SensorInfo[]) mAdapter.getAllData(DBAdapter.getDbSensorTable());
        for (int i = 0; i < sensorInfos.length; i++) {
            mSensorInfoList.add(sensorInfos[i]);
        }

    }
}

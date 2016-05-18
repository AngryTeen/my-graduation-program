package edu.learning.jonnypeng.graduationproject.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/28 13:19.
 * Email:Jonny.peng@tinno.com
 */
public class ServiceInfoHandler extends Handler {
    private static final String TAG = "ServiceInfoHandler";
    private DBAdapter mAdapter;
    private Context mContext;
    String tableName;

    public ServiceInfoHandler(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void handleMessage(Message msg) {
        Log.d(TAG, "here is running");
        mAdapter = new DBAdapter(mContext);
        mAdapter.open();
        Bundle bundle = msg.getData();
        String returnValue = bundle.getString(StringConstant.SERVER_RETURN_VALUE);
        Log.d(TAG, "return value : " + returnValue);
        switch (returnValue) {
            case StringConstant.CALL_SERVER_RETURN_VALUE:
                tableName = DBAdapter.getDbCallTable();
                break;
            case StringConstant.MESSAGE_SERVER_RETURN_VALUE:
                tableName = DBAdapter.getDbMessageTable();
                break;
            case StringConstant.APP_SERVER_RETURN_VALUE:
                tableName = DBAdapter.getDbAppUsedTable();
                break;
            case StringConstant.SENSOR_SERVER_RETURN_VALUE:
                tableName = DBAdapter.getDbSensorTable();
                break;
            default:
                break;
        }
        mAdapter.deleteData(tableName);
        mAdapter.close();
        mAdapter = null;


    }
}

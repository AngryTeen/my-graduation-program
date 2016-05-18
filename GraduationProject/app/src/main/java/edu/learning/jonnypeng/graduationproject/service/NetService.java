package edu.learning.jonnypeng.graduationproject.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.SharedPreferenceUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/26 10:59.
 * Email:Jonny.peng@tinno.com
 */
public class NetService extends IntentService {
    private static final String TAG = "NetService";
    private RequestQueue mQueue;
    private StringRequest mRequest;
    private Map<String, String> mParams;
    private Map<String, String> mHeaders;
    private DBAdapter mAdapter;
    private String tableName;

    public NetService() {
        super("Send to Service Intent Service");


    }

    public boolean checkNet() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != manager) {
            NetworkInfo[] networkInfos = manager.getAllNetworkInfo();
            if (null != networkInfos && networkInfos.length > 0) {
                for (int i = 0; i < networkInfos.length; i++) {
                    Log.d(TAG, "State : " + networkInfos[i].getState()
                            + "Type : " + networkInfos[i].getTypeName());
                    if (NetworkInfo.State.CONNECTED == networkInfos[i].getState()) {
                        return true;
                    }

                }
            }
        }
        return false;
    }


    /**
     * send service data to server, eg:call data, message data, app used data, sensor data
     *
     * @param url
     */
    public void sendServiceDataToServer(String url) {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        Log.d(TAG, "sendServiceDataToServer");
        mRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                s = s.replaceAll("\r|\n", "");//due to "return value\r\n" ,so replace "\r\n" that by ""
                Log.e(TAG, "s value is : " + s);
                Log.d(TAG, "send to server success" + s);
                if (StringConstant.SERVER_ERROR.equals(s)) {
                    Log.e(TAG, "SERVER EXCEPTION ");
                    return;
                }
                switch (s) {
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
                mAdapter = new DBAdapter(NetService.this);
                mAdapter.open();
                Log.d(TAG, "table name is : " + tableName);
                mAdapter.deleteData(tableName);
                mAdapter.close();
                mAdapter = null;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "SEND TO SERVER ERROR ," + volleyError.getMessage());
                mAdapter = new DBAdapter(NetService.this);
                mAdapter.open();
                mAdapter.deleteData(DBAdapter.getDbSensorTable());
                mAdapter.close();
                mAdapter = null;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (null == mParams) {
                    return super.getParams();
                } else {
                    return mParams;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(TAG, "header string : " + mHeaders.get(StringConstant.UUID_PARAMETER));
                if (null == mHeaders) {
                    return super.getHeaders();
                } else {
                    return mHeaders;
                }
            }
        };
        mQueue.add(mRequest);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart is Running");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, " net work is : " + checkNet());
        if (checkNet()) {

            Log.i(TAG, "send time is : " + new DateUtil().getCurrentTime());
            Bundle data = intent.getExtras();
            String url = data.getString(StringConstant.URL);
            Log.d(TAG, "URL : " + url);
            mParams = new HashMap<>();
            mHeaders = new HashMap<>();
            switch (url) {
                case StringConstant.CALL_URL:
                    mParams.put(StringConstant.CALL_INFO_JSON, data.getString(StringConstant.CALL_INFO_JSON));
                    Log.i(TAG, "CALL INFO JSON STRING : " + data.getString(StringConstant.CALL_INFO_JSON));
                    break;
                case StringConstant.MESSAGE_URL:
                    mParams.put(StringConstant.MESSAGE_INFO_JSON, data.getString(StringConstant.MESSAGE_INFO_JSON));
                    Log.i(TAG, "message Json : " + data.getString(StringConstant.MESSAGE_INFO_JSON));
                    break;
                case StringConstant.APP_URL:
                    mParams.put(StringConstant.APP_INFO_JSON, data.getString(StringConstant.APP_INFO_JSON));

                    break;
                case StringConstant.SENSOR_URL:
                    Log.i(TAG, "SENSOR JSON STRING : " + data.getString(StringConstant.SENSOR_INFO_JSON));
                    mParams.put(StringConstant.SENSOR_INFO_JSON, data.getString(StringConstant.SENSOR_INFO_JSON));
                    break;
                default:
                    break;
            }
            mHeaders.put(StringConstant.UUID_PARAMETER, SharedPreferenceUtil.getString(this, StringConstant.UUID_KEY));

            sendServiceDataToServer(url);
        }
    }
}

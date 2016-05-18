package edu.learning.jonnypeng.graduationproject.Thread;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.learning.jonnypeng.graduationproject.adapter.DBAdapter;
import edu.learning.jonnypeng.graduationproject.entities.MessageInfo;
import edu.learning.jonnypeng.graduationproject.handler.CheckInsertHandler;
import edu.learning.jonnypeng.graduationproject.service.NetService;
import edu.learning.jonnypeng.graduationproject.utils.ErrorUtil;
import edu.learning.jonnypeng.graduationproject.utils.GsonUtil;
import edu.learning.jonnypeng.graduationproject.utils.IntConst;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/27 18:28.
 * 邮箱:Jonny.peng@tinno.com
 */
public class MessageInfoStorageRunnable implements Runnable {
    private static final String TAG = "MessageInfoStorage";
    private MessageInfo mMessageInfo;
    DBAdapter mAdapter;
    SQLiteDatabase mDatabase;
    long insertCheck;
    CheckInsertHandler mHandler;
    List<MessageInfo> mMessageInfoList;
    private Context mContext;

    public MessageInfoStorageRunnable(Context context, MessageInfo messageInfo) {
        super();
        this.mContext = context;
        mMessageInfo = messageInfo;


    }

    @Override
    public void run() {
        mAdapter = new DBAdapter(mContext);
        mDatabase = mAdapter.open();

        Log.d(TAG, "start store message information");
        mMessageInfoList = new ArrayList<>();
        synchronized (mContext) {
            //Insert message information to database
            insertCheck = mAdapter
                    .insert(IntConst.MESSAGE_INFO_CLASS_NAME,
                            DBAdapter.getDbMessageTable(), mMessageInfo);
            //insert check
            if (-1 == insertCheck) {
                Log.e(TAG, "insert error!");
                ErrorUtil.insertIntoSqliteFailed();
            }

            //Get message count from sqlite -> if the count is times of MESSAGE_INFO_MAX_NUM
            //-> if the network flow -> send app data to server
            int messageInfoCount = mAdapter.getCount(DBAdapter.getDbMessageTable());
            Log.i(TAG, "messageInfoCount : " + messageInfoCount);
            if (messageInfoCount >= IntConst.MESSAGE_INFO_MAX_NUM
                    && (0 == (messageInfoCount % IntConst.MESSAGE_INFO_MAX_NUM))) {

                getDataFromSqlite();
                Log.d(TAG, "start send message information to server");
                sendDataToServer();

            }

            mAdapter.close();
            mAdapter = null;
        }

    }

    private void getDataFromSqlite() {
        MessageInfo[] messageInfos =
                (MessageInfo[]) mAdapter.getAllData(DBAdapter.getDbMessageTable());
        for (int i = 0; i < messageInfos.length; i++) {
            mMessageInfoList.add(messageInfos[i]);
        }
    }

    private void sendDataToServer() {
        Intent intent = new Intent(mContext, NetService.class);
        Bundle bundle = new Bundle();
        bundle.putString(StringConstant.URL, StringConstant.MESSAGE_URL);
        bundle.putString(StringConstant.MESSAGE_INFO_JSON, new GsonUtil().getJsonData(mMessageInfoList));
        Log.i(TAG, "message info json : " + new GsonUtil().getJsonData(mMessageInfoList));
        intent.putExtras(bundle);
        mContext.startService(intent);
    }
}

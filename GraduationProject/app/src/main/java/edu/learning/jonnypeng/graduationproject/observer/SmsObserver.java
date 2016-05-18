package edu.learning.jonnypeng.graduationproject.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import edu.learning.jonnypeng.graduationproject.Thread.MessageInfoStorageRunnable;
import edu.learning.jonnypeng.graduationproject.entities.MessageInfo;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;


/**
 * Created by jonny.peng on 2016/4/20 10:33.
 * Email:Jonny.peng@tinno.com
 */
public class SmsObserver extends ContentObserver {
    private static final String TAG = "SmsObserver";
    private static final String SMS_URI = "content://sms";
    private Context mContext;
    private Cursor mCursor;
    private Uri mUri;
    private MessageInfo mMessageInfo;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SmsObserver(Handler handler) {
        super(handler);
    }

    public SmsObserver(Handler handler, Context context) {
        super(handler);
        this.mContext = context;


    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        mMessageInfo = new MessageInfo();
        mMessageInfo.setMessageTime(new DateUtil().getCurrentTime());
        Log.d(TAG, "Time of send or get message" + new DateUtil().getCurrentTime());
        Uri uri = Uri.parse(SMS_URI);
        mCursor = this.mContext.getContentResolver().query(uri, null, null, null, null);
        if (mCursor.moveToFirst()) {
            int type = mCursor.getInt(mCursor.getColumnIndex("type"));
            mMessageInfo.setMessageType(type);
            Log.d(TAG, "Type of short message" + type);
        }
        //Start a thread to storage message information
        MessageInfoStorageRunnable runnable = new MessageInfoStorageRunnable(mContext, mMessageInfo);
        new Thread(runnable, StringConstant.MESSAGE_INFO_STORAGE_THREAD).start();

    }
}

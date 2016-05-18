package edu.learning.jonnypeng.graduationproject.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import edu.learning.jonnypeng.graduationproject.R;
import edu.learning.jonnypeng.graduationproject.adapter.SimpleDialog;

/**
 * Created by jonny.peng on 2016/4/28 10:46.
 * Email:Jonny.peng@tinno.com
 */
public class UserInfoHandler extends Handler {
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public UserInfoHandler(Context context, ProgressDialog progressDialog) {
        super(Looper.getMainLooper());

        mContext = context;
        mProgressDialog = progressDialog;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 0:
                mProgressDialog.dismiss();
                new SimpleDialog(mContext).showTextDialog(mContext.getResources().getString(R.string.dialog_title),
                        mContext.getResources().getString(R.string.dialog_submit_success));
                break;
            case 1:
                mProgressDialog.dismiss();
                new SimpleDialog(mContext).showTextDialog(mContext.getResources().getString(R.string.dialog_title),
                        mContext.getResources().getString(R.string.dialog_can_not_connect_server));
                break;
            case 2:
                mProgressDialog.dismiss();
                new SimpleDialog(mContext).showTextDialog(mContext.getResources().getString(R.string.dialog_title),
                        mContext.getResources().getString(R.string.dialog_user_has_exist));
                break;
            default:
                break;
        }
    }
}

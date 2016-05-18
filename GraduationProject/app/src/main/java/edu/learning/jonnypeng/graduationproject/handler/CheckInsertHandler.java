package edu.learning.jonnypeng.graduationproject.handler;

import android.os.Handler;
import android.os.Message;

import edu.learning.jonnypeng.graduationproject.utils.ErrorUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;

/**
 * Created by jonny.peng on 2016/4/27 16:15.
 * Email:Jonny.peng@tinno.com
 */
public class CheckInsertHandler extends Handler {

    @Override
    public void handleMessage(Message msg) {
        if (-1 == msg.getData().getLong(StringConstant.INSERT_CHECK)) {
            ErrorUtil.insertIntoSqliteFailed();
        }
    }
}

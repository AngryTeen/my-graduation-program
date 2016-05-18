package edu.learning.jonnypeng.graduationproject.Thread;

import android.content.Context;

import java.util.TimerTask;

import edu.learning.jonnypeng.graduationproject.listener.SensorInfoListener;

/**
 * Created by jonny.peng on 2016/4/27 18:52.
 * Email:Jonny.peng@tinno.com
 */
public class GetSensorInfoTimerTask extends TimerTask {
    public Context mContext;

    public GetSensorInfoTimerTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    public void run() {
        new SensorInfoListener(mContext);

    }
}

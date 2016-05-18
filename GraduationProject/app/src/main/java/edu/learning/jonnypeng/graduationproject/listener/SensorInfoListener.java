package edu.learning.jonnypeng.graduationproject.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import edu.learning.jonnypeng.graduationproject.Thread.SensorInfoStorageRunnable;
import edu.learning.jonnypeng.graduationproject.entities.SensorInfo;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.StringConstant;
import edu.learning.jonnypeng.graduationproject.utils.ValidateUtil;

/**
 * Created by jonny.peng on 2016/4/19 11:18.
 * Email:Jonny.peng@tinno.com
 */

/**
 * Listen Sensor Value
 */
public class SensorInfoListener implements SensorEventListener {
    protected static final String TAG = "SensorInfoListener";
    public static final String TYPE_ORIENTATION_X = "ORIENTATION_X";
    public static final String TYPE_ORIENTATION_Y = "ORIENTATION_Y";
    public static final String TYPE_ORIENTATION_Z = "ORIENTATION_Z";
    public static final String TYPE_MAGNETIC_FIELD_X = "MAGNETIC_FIELD_X";
    public static final String TYPE_MAGNETIC_FIELD_Y = "MAGNETIC_FIELD_Y";
    public static final String TYPE_MAGNETIC_FIELD_Z = "MAGNETIC_FIELD_Z";
    public static final String TYPE_AMBIENT_TEMPERATURE = "AMBIENT_TEMPERATURE";
    public static final String TYPE_LIGHT = "LIGHT";
    public static final String TYPE_PRESSURE = "PRESSURE";
    public static final String TYPE_ACCELEROMETER_X = "ACCELEROMETER_X";
    public static final String TYPE_ACCELEROMETER_Y = "ACCELEROMETER_Y";
    public static final String TYPE_ACCELEROMETER_Z = "ACCELEROMETER_Z";
    public static final String TYPE_GYROSCOPE_X = "GYROSCOPE_X";
    public static final String TYPE_GYROSCOPE_Y = "GYROSCOPE_Y";
    public static final String TYPE_GYROSCOPE_Z = "GYROSCOPE_Z";
    public static final String TYPE_PROXIMITY = "PROXIMITY";
    public static final String TYPE_GRAVITY = "GRAVITY";
    public static final String TYPE_LINEAR_ACCELERATION = "LINEAR_ACCELERATION";
    public static final String TYPE_ROTATION_VECTOR_X = "ROTATION_VECTOR_X";
    public static final String TYPE_ROTATION_VECTOR_Y = "ROTATION_VECTOR_Y";
    public static final String TYPE_ROTATION_VECTOR_Z = "ROTATION_VECTOR_Z";


    private SensorManager mSensorManager;
    private Context mContext;
    private List<Sensor> mSensors;//List of sensor
    private TextView mTextView;
    private List<SensorInfo> mSensorInfos;
    private SensorInfo mSensorInfoX;
    private SensorInfo mSensorInfoY;
    private SensorInfo mSensorInfoZ;


    /**
     * constructor
     *
     * @param context
     */
    public SensorInfoListener(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        Log.d(TAG, "SensorManager value:" + mSensorManager.toString());
        getSensorList();
    }

    public void getSensorList() {
        mSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : mSensors) {
            registerSingleSensor(sensor.getType());
            Log.d(TAG, "sensorName:" + sensor.getName());
        }
    }

    public void registerSingleSensor(int sensorType) {
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values;
        //Type of sensor
        int sensorType = event.sensor.getType();
        StringBuilder stringBuilder = new StringBuilder();
        mSensorInfoX = new SensorInfo();
        mSensorInfoY = new SensorInfo();
        mSensorInfoZ = new SensorInfo();
        String currenTime = new DateUtil().getCurrentTime();
        switch (sensorType) {
            case Sensor.TYPE_ORIENTATION:
                mSensorInfoX.setSensorName(TYPE_ORIENTATION_X);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);

                mSensorInfoY.setSensorName(TYPE_ORIENTATION_Y);
                mSensorInfoY.setSensorGetTime(currenTime);
                mSensorInfoY.setSensorValue(values[1]);

                mSensorInfoZ.setSensorName(TYPE_ORIENTATION_Z);
                mSensorInfoZ.setSensorGetTime(currenTime);
                mSensorInfoZ.setSensorValue(values[2]);

                break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                mSensorInfoX.setSensorName(TYPE_MAGNETIC_FIELD_X);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);

                mSensorInfoY.setSensorName(TYPE_MAGNETIC_FIELD_Y);
                mSensorInfoY.setSensorGetTime(currenTime);
                mSensorInfoY.setSensorValue(values[1]);

                mSensorInfoZ.setSensorName(TYPE_MAGNETIC_FIELD_Z);
                mSensorInfoZ.setSensorGetTime(currenTime);
                mSensorInfoZ.setSensorValue(values[2]);
                break;

            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mSensorInfoX.setSensorName(TYPE_AMBIENT_TEMPERATURE);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_LIGHT:
                mSensorInfoX.setSensorName(TYPE_LIGHT);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_PRESSURE:
                mSensorInfoX.setSensorName(TYPE_PRESSURE);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_ACCELEROMETER:
                mSensorInfoX.setSensorName(TYPE_ACCELEROMETER_X);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);

                mSensorInfoY.setSensorName(TYPE_ACCELEROMETER_Y);
                mSensorInfoY.setSensorGetTime(currenTime);
                mSensorInfoY.setSensorValue(values[1]);

                mSensorInfoZ.setSensorName(TYPE_ACCELEROMETER_Z);
                mSensorInfoZ.setSensorGetTime(currenTime);
                mSensorInfoZ.setSensorValue(values[2]);
                break;

            case Sensor.TYPE_GYROSCOPE:
                mSensorInfoX.setSensorName(TYPE_GYROSCOPE_X);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);

                mSensorInfoY.setSensorName(TYPE_GYROSCOPE_Y);
                mSensorInfoY.setSensorGetTime(currenTime);
                mSensorInfoY.setSensorValue(values[1]);

                mSensorInfoZ.setSensorName(TYPE_GYROSCOPE_Z);
                mSensorInfoZ.setSensorGetTime(currenTime);
                mSensorInfoZ.setSensorValue(values[2]);
                break;

            case Sensor.TYPE_PROXIMITY:
                mSensorInfoX.setSensorName(TYPE_PROXIMITY);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_GRAVITY:
                mSensorInfoX.setSensorName(TYPE_GRAVITY);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:
                mSensorInfoX.setSensorName(TYPE_LINEAR_ACCELERATION);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);
                break;

            case Sensor.TYPE_ROTATION_VECTOR:
                mSensorInfoX.setSensorName(TYPE_ROTATION_VECTOR_X);
                mSensorInfoX.setSensorGetTime(currenTime);
                mSensorInfoX.setSensorValue(values[0]);

                mSensorInfoY.setSensorName(TYPE_ROTATION_VECTOR_Y);
                mSensorInfoY.setSensorGetTime(currenTime);
                mSensorInfoY.setSensorValue(values[1]);

                mSensorInfoZ.setSensorName(TYPE_ROTATION_VECTOR_Z);
                mSensorInfoZ.setSensorGetTime(currenTime);
                mSensorInfoZ.setSensorValue(values[2]);
                break;
            default:
                break;
        }
        Log.d(TAG, "mSensorInfoX name and time and value:"
                + mSensorInfoX.getSensorName() + " , "
                + mSensorInfoX.getSensorGetTime() + " , "
                + mSensorInfoX.getSensorValue());
        mSensorManager.unregisterListener(this, event.sensor);
        //start 1~3 threads to storage sensor information
        SensorInfoStorageRunnable runnableX = new SensorInfoStorageRunnable(mContext, mSensorInfoX);
        new Thread(runnableX, StringConstant.SENSOR_INFO_STORAGE_THREAD_X).start();

        if (!ValidateUtil.isNull(mSensorInfoY.getSensorName())) {
            //If sensorInfoY's name is not null
            SensorInfoStorageRunnable runnableY = new SensorInfoStorageRunnable(mContext, mSensorInfoY);
            new Thread(runnableY, StringConstant.SENSOR_INFO_STORAGE_THREAD_Y).start();
        }
        if (!ValidateUtil.isNull(mSensorInfoZ.getSensorName())) {
            //If sensorInfoZ's name is not null
            SensorInfoStorageRunnable runnableZ = new SensorInfoStorageRunnable(mContext, mSensorInfoZ);
            new Thread(runnableZ, StringConstant.SENSOR_INFO_STORAGE_THREAD_Z).start();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}

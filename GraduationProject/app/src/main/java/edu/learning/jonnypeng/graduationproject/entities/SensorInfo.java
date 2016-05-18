package edu.learning.jonnypeng.graduationproject.entities;

/**
 * Created by jonny.peng on 2016/4/21 11:32.
 * Email:Jonny.peng@tinno.com
 */
public class SensorInfo {
    private String mSensorName;
    private String mSensorGetTime;
    private float mSensorValue;

    public SensorInfo() {
    }

    public SensorInfo(String sensorGetTime, String sensorName, float sensorValue) {
        mSensorGetTime = sensorGetTime;
        mSensorName = sensorName;
        mSensorValue = sensorValue;
    }

    public String getSensorGetTime() {
        return mSensorGetTime;
    }

    public void setSensorGetTime(String sensorGetTime) {
        mSensorGetTime = sensorGetTime;
    }

    public String getSensorName() {
        return mSensorName;
    }

    public void setSensorName(String sensorName) {
        mSensorName = sensorName;
    }

    public float getSensorValue() {
        return mSensorValue;
    }

    public void setSensorValue(float sensorValue) {
        mSensorValue = sensorValue;
    }
}

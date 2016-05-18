package edu.learning.jonnypeng.graduationproject.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import edu.learning.jonnypeng.graduationproject.entities.AppUsedInfo;
import edu.learning.jonnypeng.graduationproject.entities.CallInfo;
import edu.learning.jonnypeng.graduationproject.entities.MessageInfo;
import edu.learning.jonnypeng.graduationproject.entities.SensorInfo;
import edu.learning.jonnypeng.graduationproject.utils.DateUtil;
import edu.learning.jonnypeng.graduationproject.utils.IntConst;


/**
 * Created by jonny.peng on 2016/4/21 09:37.
 * Email:Jonny.peng@tinno.com
 */

/**
 * DBAdapter is a SQLite database Adapter, which can create database dynamic
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter";
    //database name
    private static final String DB_NAME = "InfoCollection";
    //database version
    private static final int DB_VERSION = 1;
    //user table name
    //private static final String DB_USER_TABLE = "user";
    //call info table name
    private static final String DB_CALL_TABLE = "call";
    //short message info table name
    private static final String DB_MESSAGE_TABLE = "message";
    //app used info table name
    private static final String DB_APP_USED_TABLE = "app_used";
    //sensor info table name
    private static final String DB_SENSOR_TABLE = "sensor";

    public static String getDbAppUsedTable() {
        return DB_APP_USED_TABLE;
    }

    public static String getDbCallTable() {
        return DB_CALL_TABLE;
    }

    public static String getDbMessageTable() {
        return DB_MESSAGE_TABLE;
    }

    public static String getDbSensorTable() {
        return DB_SENSOR_TABLE;
    }


    //call info key
    public static final String CALL_ID = "call_id";
    public static final String CALL_RING_TIME = "call_ring_time";
    public static final String CALL_START_TIME = "call_start_time";
    public static final String CALL_STOP_TIME = "call_stop_time";

    //short message info key
    public static final String MESSAGE_ID = "message_id";
    public static final String MESSAGE_TIME = "message_time";
    public static final String MESSAGE_TYPE = "message_type";

    //app used info key
    public static final String APP_ID = "app_id";
    public static final String APP_NAME = "app_name";
    public static final String APP_START_TIME = "app_start_time";
    public static final String APP_STOP_TIME = "app_stop_time";

    //sensor info key
    public static final String SENSOR_ID = "sensor_id";
    public static final String SENSOR_NAME = "sensor_name";
    public static final String SENSOR_GET_TIME = "sensor_get_time";
    public static final String SENSOR_VALUE = "sensor_value";


    //define SQLiteDatabase Object
    private SQLiteDatabase mDatabase;
    //Define a context
    private Context mContext;

    private DBOpenHelper mDBOpenHelper;

    //Create a help class extends SQLiteOpenHelper, some method can help open database
    private static class DBOpenHelper extends SQLiteOpenHelper {
        //Call information SQL statement
        private static final String CALL_TABLE_CREATE = "create table " + DB_CALL_TABLE +
                "( " + CALL_ID + " integer primary key autoincrement, " + CALL_RING_TIME + " text, "
                + CALL_START_TIME + " text, " + CALL_STOP_TIME + " text not null);";
        //Message information SQL statement
        private static final String MESSAGE_TABLE_CREATE = "create table " + DB_MESSAGE_TABLE +
                "( " + MESSAGE_ID + " integer primary key autoincrement, "
                + MESSAGE_TIME + " text not null, " + MESSAGE_TYPE + " integer not null);";
        //App used information SQL statement
        private static final String APP_USED_TABLE_CREATE = "create table " + DB_APP_USED_TABLE +
                "( " + APP_ID + " integer primary key autoincrement, " + APP_NAME + " text not null,"
                + APP_START_TIME + " text not null, " + APP_STOP_TIME + " text not null);";
        //Sensor information SQL statement
        private static final String SENSOR_TABLE_CREATE = "create table " + DB_SENSOR_TABLE +
                "( " + SENSOR_ID + " integer primary key autoincrement, "
                + SENSOR_NAME + " text not null, "
                + SENSOR_GET_TIME + " text not null, " + SENSOR_VALUE + " real not null);";

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory
                , int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        /**
         * @param context
         * @param name    Database name
         * @param factory
         * @param version
         */
        public DBOpenHelper(Context context, String name
                , SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "onCreate(),Run time :" + new DateUtil().getCurrentTime());
            //Create table
            db.execSQL(CALL_TABLE_CREATE);
            db.execSQL(MESSAGE_TABLE_CREATE);
            db.execSQL(APP_USED_TABLE_CREATE);
            db.execSQL(SENSOR_TABLE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + DB_CALL_TABLE);
            db.execSQL("DROP TABLE IF EXISTS" + DB_MESSAGE_TABLE);
            db.execSQL("DROP TABLE IF EXISTS" + DB_APP_USED_TABLE);
            db.execSQL("DROP TABLE IF EXISTS" + DB_SENSOR_TABLE);
            onCreate(db);
        }
    }

    /**
     * @param context
     */
    public DBAdapter(Context context) {
        mContext = context;
    }

    /**
     * Open database,If not exist then create
     *
     * @return Database instant
     */
    public SQLiteDatabase open() {
        Log.d(TAG, "open(), Run time :" + new DateUtil().getCurrentTime());
        //Init DBOpenHelper
        mDBOpenHelper = new DBOpenHelper(mContext, DB_NAME, null, DB_VERSION);
        //Get database instant
        mDatabase = mDBOpenHelper.getWritableDatabase();
        return mDatabase;
    }

    /**
     * close database
     */
    public void close() {
        if (null != mDatabase) {
            mDatabase.close();
            mDatabase = null;
        }
    }

    /**
     * Insert into database
     *
     * @param className judge the entity
     * @param tableName the table name
     * @param object    the entity
     *
     * @return if return -1, then insert failed
     */

    public long insert(int className, String tableName, Object object) {
        Log.d(TAG, "insert(), Run time : " + new DateUtil().getCurrentTime());
        Log.d(TAG, "table name : " + tableName);
        Log.d(TAG, "object name : " + object.toString());
        ContentValues values = new ContentValues();
        //judge the class
        switch (className) {
            case IntConst.CALL_INFO_CLASS_NAME:
                values = getCallValues((CallInfo) object);
                break;
            case IntConst.MESSAGE_INFO_CLASS_NAME:
                values = getMessageValues((MessageInfo) object);
                break;
            case IntConst.APP_USER_INFO_CLASS_NAME:
                values = getAppValues((AppUsedInfo) object);
                break;
            case IntConst.SENSOR_INFO_CLASS_NAME:
                values = getSensorValues((SensorInfo) object);
                break;
            default:
                break;
        }

        return mDatabase.insert(tableName, null, values);
    }

    /**
     * Get call information ContentValues
     *
     * @param callInfo call entity
     *
     * @return ContentValues instant
     */
    private ContentValues getCallValues(CallInfo callInfo) {
        ContentValues values = new ContentValues();
        values.put(CALL_RING_TIME, callInfo.getRingTime());
        values.put(CALL_START_TIME, callInfo.getCallStartTime());
        values.put(CALL_STOP_TIME, callInfo.getCallStopTime());
        return values;
    }

    /**
     * get message information ContentValues
     *
     * @param messageInfo message entity
     *
     * @return ContentValues instant
     */
    private ContentValues getMessageValues(MessageInfo messageInfo) {
        ContentValues values = new ContentValues();
        values.put(MESSAGE_TIME, messageInfo.getMessageTime());
        values.put(MESSAGE_TYPE, messageInfo.getMessageType());
        return values;
    }

    /**
     * get app used information contentValues
     *
     * @param appUsedInfo APP used entity
     *
     * @return ContentValues instant
     */
    private ContentValues getAppValues(AppUsedInfo appUsedInfo) {
        ContentValues values = new ContentValues();
        values.put(APP_NAME, appUsedInfo.getAppName());
        values.put(APP_START_TIME, appUsedInfo.getAppStartTime());
        values.put(APP_STOP_TIME, appUsedInfo.getAppStopTime());
        return values;
    }

    /**
     * get sensor information ContentValues
     *
     * @param sensorInfo sensor entity
     *
     * @return ContentValues entity
     */
    private ContentValues getSensorValues(SensorInfo sensorInfo) {
        ContentValues values = new ContentValues();
        values.put(SENSOR_GET_TIME, sensorInfo.getSensorGetTime());
        values.put(SENSOR_NAME, sensorInfo.getSensorName());
        values.put(SENSOR_VALUE, sensorInfo.getSensorValue());
        return values;
    }

    /**
     * Check cursor object and take the query result from table change to object[]
     *
     * @param cursor    cursor object
     * @param tableName table name
     *
     * @return object[]
     */
    private Object[] convertToObjects(Cursor cursor, String tableName) {
        Log.d(TAG, "convertToCallInfo(), Run Time : " + new DateUtil().getCurrentTime());
        int resultCounts = cursor.getCount();
        Log.d(TAG, "Count of data:" + resultCounts);
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        switch (tableName) {
            case DB_CALL_TABLE:
                return getCallInfos(cursor, resultCounts);
            case DB_MESSAGE_TABLE:
                return getMessageInfos(cursor, resultCounts);
            case DB_APP_USED_TABLE:
                return getAppUsedInfos(cursor, resultCounts);
            case DB_SENSOR_TABLE:
                return getSensorInfos(cursor, resultCounts);
        }
        return null;
    }

    /**
     * get data from table
     *
     * @param tableName table name
     *
     * @return object[]
     */
    public Object[] getAllData(String tableName) {
        Log.d(TAG, "getAllData(), Run time : " + new DateUtil().getCurrentTime());
        String[] key;
        Cursor results = null;
        switch (tableName) {
            case DB_CALL_TABLE:
                results = mDatabase.query(DB_CALL_TABLE, new String[]
                        {CALL_RING_TIME, CALL_START_TIME, CALL_STOP_TIME
                        }, null, null, null, null, null);
                break;
            case DB_MESSAGE_TABLE:
                results = mDatabase.query(DB_MESSAGE_TABLE, new String[]
                        {MESSAGE_TIME, MESSAGE_TYPE
                        }, null, null, null, null, null);
                break;
            case DB_APP_USED_TABLE:
                results = mDatabase.query(DB_APP_USED_TABLE, new String[]
                        {APP_NAME, APP_START_TIME, APP_STOP_TIME
                        }, null, null, null, null, null);
                break;
            case DB_SENSOR_TABLE:
                results = mDatabase.query(DB_SENSOR_TABLE, new String[]
                        {SENSOR_NAME, SENSOR_GET_TIME, SENSOR_VALUE
                        }, null, null, null, null, null);
        }

        Log.d(TAG, "results Value : " + results.toString());
        return convertToObjects(results, tableName);
    }

    /**
     * Count data in table
     *
     * @param tableName table name
     *
     * @return the count
     */
    public int getCount(String tableName) {
        Cursor results = null;
        switch (tableName) {
            case DB_CALL_TABLE:
                results = mDatabase.query(DB_CALL_TABLE, new String[]
                        {CALL_RING_TIME, CALL_START_TIME, CALL_STOP_TIME
                        }, null, null, null, null, null);
                break;
            case DB_MESSAGE_TABLE:
                results = mDatabase.query(DB_MESSAGE_TABLE, new String[]
                        {MESSAGE_TIME, MESSAGE_TYPE
                        }, null, null, null, null, null);
                break;
            case DB_APP_USED_TABLE:
                results = mDatabase.query(DB_APP_USED_TABLE, new String[]
                        {APP_NAME, APP_START_TIME, APP_STOP_TIME
                        }, null, null, null, null, null);
                break;
            case DB_SENSOR_TABLE:
                results = mDatabase.query(DB_SENSOR_TABLE, new String[]
                        {SENSOR_NAME, SENSOR_GET_TIME, SENSOR_VALUE
                        }, null, null, null, null, null);
                break;
            default:
                break;
        }
        return results.getCount();
    }


    /**
     * get call information object[]
     *
     * @param cursor
     * @param resultCounts count
     *
     * @return callInfo[]
     */
    private CallInfo[] getCallInfos(Cursor cursor, int resultCounts) {
        CallInfo[] callInfos = new CallInfo[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            callInfos[i] = new CallInfo();
            callInfos[i].setRingTime(cursor.getString(cursor.getColumnIndex(CALL_RING_TIME)));
            callInfos[i].setCallStartTime(cursor.getString(cursor.getColumnIndex(CALL_START_TIME)));
            callInfos[i].setCallStopTime(cursor.getString(cursor.getColumnIndex(CALL_STOP_TIME)));
            Log.d(TAG, "RingTime : " + callInfos[i].getRingTime() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "CallStartTime : " + callInfos[i].getCallStartTime() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "CallStopTime : " + callInfos[i].getCallStopTime() + " , " + new DateUtil().getCurrentTime());
            cursor.moveToNext();
        }

        return callInfos;
    }

    /**
     * get message information object[]
     *
     * @param cursor
     * @param resultCounts
     *
     * @return
     */
    private MessageInfo[] getMessageInfos(Cursor cursor, int resultCounts) {
        MessageInfo[] messageInfos = new MessageInfo[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            messageInfos[i] = new MessageInfo();
            messageInfos[i].setMessageTime(cursor.getString(cursor.getColumnIndex(MESSAGE_TIME)));
            messageInfos[i].setMessageType(cursor.getInt(cursor.getColumnIndex(MESSAGE_TYPE)));
            Log.d(TAG, "MessageTime : " + messageInfos[i].getMessageTime() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "MessageType : " + messageInfos[i].getMessageType() + " , " + new DateUtil().getCurrentTime());
            cursor.moveToNext();
        }
        return messageInfos;
    }

    private AppUsedInfo[] getAppUsedInfos(Cursor cursor, int resultCounts) {
        AppUsedInfo[] appUsedInfos = new AppUsedInfo[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            appUsedInfos[i] = new AppUsedInfo();
            appUsedInfos[i].setAppName(cursor.getString(cursor.getColumnIndex(APP_NAME)));
            appUsedInfos[i].setAppStartTime(cursor.getString(cursor.getColumnIndex(APP_START_TIME)));
            appUsedInfos[i].setAppStopTime(cursor.getString(cursor.getColumnIndex(APP_STOP_TIME)));
            Log.d(TAG, "AppName : " + appUsedInfos[i].getAppName() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "AppStartTime : " + appUsedInfos[i].getAppStartTime() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "AppStopTime : " + appUsedInfos[i].getAppStopTime() + " , " + new DateUtil().getCurrentTime());
            cursor.moveToNext();
        }
        return appUsedInfos;
    }

    private SensorInfo[] getSensorInfos(Cursor cursor, int resultCounts) {
        SensorInfo[] sensorInfos = new SensorInfo[resultCounts];
        for (int i = 0; i < resultCounts; i++) {
            sensorInfos[i] = new SensorInfo();
            sensorInfos[i].setSensorName(cursor.getString(cursor.getColumnIndex(SENSOR_NAME)));
            sensorInfos[i].setSensorGetTime(cursor.getString(cursor.getColumnIndex(SENSOR_GET_TIME)));
            sensorInfos[i].setSensorValue(cursor.getFloat(cursor.getColumnIndex(SENSOR_VALUE)));
            Log.d(TAG, "SensorName : " + sensorInfos[i].getSensorName() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "SensorGetTime : " + sensorInfos[i].getSensorGetTime() + " , " + new DateUtil().getCurrentTime());
            Log.d(TAG, "SensorValue : " + sensorInfos[i].getSensorValue() + " , " + new DateUtil().getCurrentTime());
            cursor.moveToNext();
        }
        return sensorInfos;
    }

    public long deleteData(String tableName) {
        return mDatabase.delete(tableName, null, null);
    }


}

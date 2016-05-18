package edu.learning.jonnypeng.graduationproject.utils;

/**
 * Created by jonny.peng on 2016/4/22 12:52.
 * 邮箱:Jonny.peng@tinno.com
 */
public class StringConstant {

    //SERVER URL
    public static final String URL = "http://10.0.0.2:8089/serverDemo";

    public static final String USER_URL = URL + "/UserInfoServlet";
    public static final String CALL_URL = URL + "/CallInfoServlet";
    public static final String MESSAGE_URL = URL + "/MessageInfoServlet";
    public static final String APP_URL = URL + "/AppUsedInfoServlet";
    public static final String SENSOR_URL = URL + "/SensorInfoServlet";

    //UUID key
    public static final String UUID_KEY = "UUID";

    //JSON
    public static final String UUID_PARAMETER = "uuid_parameter";
    public static final String CALL_INFO_JSON = "CALL_JSON";
    public static final String MESSAGE_INFO_JSON = "MESSAGE_JSON";
    public static final String APP_INFO_JSON = "APP_JSON";
    public static final String SENSOR_INFO_JSON = "SENSOR_JSON";


    //Insert into database check constant
    public static final String INSERT_CHECK = "insertCheck";

    //Thread Name
    public static final String CALL_INFO_STORAGE_THREAD = "StorageCallInfo";
    public static final String MESSAGE_INFO_STORAGE_THREAD = "StorageMessageInfo";
    public static final String APP_INFO_STORAGE_THREAD = "StorageAppInfo";
    public static final String SENSOR_INFO_STORAGE_THREAD_X = "StorageSensorInfoX";
    public static final String SENSOR_INFO_STORAGE_THREAD_Y = "StorageSensorInfoY";
    public static final String SENSOR_INFO_STORAGE_THREAD_Z = "StorageSensorInfoZ";

    //server return value
    public static final String SERVER_RETURN_VALUE = "RETURN_VALUE";
    public static final String CALL_SERVER_RETURN_VALUE = "CALL_VALUE";
    public static final String MESSAGE_SERVER_RETURN_VALUE = "MESSAGE_VALUE";
    public static final String APP_SERVER_RETURN_VALUE = "APP_VALUE";
    public static final String SENSOR_SERVER_RETURN_VALUE = "SENSOR_VALUE";
    public static final String SERVER_ERROR = "SERVER_ERROR";
    public static final String USER_REGISTER_SUCCESS = "REGISTER_SUCCESS";
    public static final String USER_EXIST = "USER_EXIST";

    //request parameter key
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String SCHOOL = "schoolName";
    public static final String STUDENT_ID = "studentId";
    public static final String BIRTH = "birth";
    public static final String SEX = "sex";

    public static final String TABLE_NAME_CONST = "TABLE_NAME";

}

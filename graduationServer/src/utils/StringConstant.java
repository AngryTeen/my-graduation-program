package utils;

import entity.AppUsedInfo;
import entity.CallInfo;
import entity.MessageInfo;
import entity.SensorInfo;
import entity.UserInfo;

public class StringConstant 
{
	//request parameter key
	public static final String ACCOUNT = "account";
	public static final String PASSWORD = "password";
	public static final String EMAIL = "email";
	public static final String SCHOOL = "schoolName";
	public static final String STUDENT_ID = "studentId";
	public static final String BIRTH = "birth";
	public static final String SEX = "sex";
	
	//JSON
	public static final String UUID_PARAMETER = "uuid_parameter";
    public static final String CALL_INFO_JSON = "CALL_JSON";
    public static final String MESSAGE_INFO_JSON = "MESSAGE_JSON";
    public static final String APP_INFO_JSON = "APP_JSON";
    public static final String SENSOR_INFO_JSON = "SENSOR_JSON";
	
	public static final String CALL_SERVER_RETURN_VALUE = "CALL_VALUE";
    public static final String MESSAGE_SERVER_RETURN_VALUE = "MESSAGE_VALUE";
    public static final String APP_SERVER_RETURN_VALUE = "APP_VALUE";
    public static final String SENSOR_SERVER_RETURN_VALUE = "SENSOR_VALUE";
	
	//response value
	public static final String USER_REGISTER_SUCCESS = "REGISTER_SUCCESS";
    public static final String USER_EXIST = "USER_EXIST";
    public static final String SERVER_ERROR = "SERVER_ERROR";
    
  //entity class name
    public static final String USER_INFO_CLASS_NAME = "USER";
    public static final String CALL_INFO_CLASS_NAME = "CALL";
    public static final String MESSAGE_INFO_CLASS_NAME = "MESSAGE";
    public static final String APP_INFO_CLASS_NAME = "APP";
    public static final String SENSOR_INFO_CLASS_NAME = "SENSOR";
}

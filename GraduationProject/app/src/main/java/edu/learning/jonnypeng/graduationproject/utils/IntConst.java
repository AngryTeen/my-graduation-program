package edu.learning.jonnypeng.graduationproject.utils;

/**
 * Created by jonny.peng on 2016/4/20 12:57.
 * Email:Jonny.peng@tinno.com
 */
public class IntConst {
    //Used In DateUtil, judge which theme
    public static final int ONLY_YEAR = 0;
    public static final int ONLY_MONTH = 1;
    public static final int ONLY_DAY = 2;
    public static final int DATE_THEME_1 = 3;
    public static final int DATE_THEME_2 = 4;

    //used in DBAdapter, insert type of entity
    public static final int CALL_INFO_CLASS_NAME = 0;
    public static final int MESSAGE_INFO_CLASS_NAME = 1;
    public static final int SENSOR_INFO_CLASS_NAME = 2;
    public static final int APP_USER_INFO_CLASS_NAME = 3;
    public static final int USER_INFO_CLASS_NAME = 4;


    //max count of database
    public static final int CALL_INFO_MAX_NUM = 2;
    public static final int MESSAGE_INFO_MAX_NUM = 2;
    public static final int APP_USED_INFO_MAX_NUM = 5;
    public static final int SENSOR_INFO_MAX_NUM = 10;
}

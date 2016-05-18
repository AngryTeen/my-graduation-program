package edu.learning.jonnypeng.graduationproject.utils;

import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jonny.peng on 2016/4/22 13:12.
 * 邮箱:Jonny.peng@tinno.com
 */
public class ValidateUtil {
    Context mContext;

    public ValidateUtil() {
        super();
    }

    public ValidateUtil(Context context) {
        this.mContext = context;
    }

    /**
     * check is null
     *
     * @param checkData data
     *
     * @return true is null， false is not null
     */
    public static boolean isNull(String checkData) {
        if (null == checkData || "".equals(checkData)) {
            return true;
        }
        return false;
    }


    /**
     * check email is right
     *
     * @param checkData Email string
     *
     * @return false is error，true is right
     */
    public static boolean validateEmail(String checkData) {
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher matcher = pattern.matcher(checkData);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static boolean hasSpecialCharacter(String checkData) {
        char[] tempChar = checkData.toCharArray();
        for (int i = 0; i < tempChar.length; i++) {
            if ((tempChar[i] >= '0' && tempChar[i] <= '9')
                    || (tempChar[i] >= 'a' && tempChar[i] <= 'z')
                    || (tempChar[i] >= 'A' && tempChar[i] <= 'Z')) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }


}

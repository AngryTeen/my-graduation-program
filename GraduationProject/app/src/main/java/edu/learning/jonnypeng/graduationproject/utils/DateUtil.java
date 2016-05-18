package edu.learning.jonnypeng.graduationproject.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jonny.peng on 2016/4/19 13:42.
 * Email:Jonny.peng@tinno.com
 */
public class DateUtil {
    /**
     * Get current time
     *
     * @return return current time format yyyy-MM-dd HH:mm:ss
     */
    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentTime = simpleDateFormat.format(new Date());
        return currentTime;
    }

    /**
     * @param type Type of date
     *
     * @return current date
     */
    public String getCurrentDate(int type) {
        StringBuilder tempDate = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        switch (type) {
            case IntConst.ONLY_YEAR:
                tempDate.append(calendar.get(Calendar.YEAR));
                break;
            case IntConst.ONLY_MONTH:
                tempDate.append(calendar.get(Calendar.MONTH) + 1);
                break;
            case IntConst.ONLY_DAY:
                tempDate.append(calendar.get(Calendar.DAY_OF_MONTH));
                break;
            case IntConst.DATE_THEME_1:
                tempDate.append(calendar.get(Calendar.YEAR))
                        .append("-")
                        .append(calendar.get(Calendar.MONTH) + 1)
                        .append("-")
                        .append(calendar.get(Calendar.DAY_OF_MONTH));
                break;
            case IntConst.DATE_THEME_2:
                tempDate.append(calendar.get(Calendar.YEAR))
                        .append("年")
                        .append(calendar.get(Calendar.MONTH) + 1)
                        .append("月")
                        .append(calendar.get(Calendar.DAY_OF_MONTH))
                        .append("日");
                break;
        }

        return tempDate.toString();
    }

    public static boolean isLeapYear(int year) {
        if (0 == (year % 4)) {
            if (0 == (year % 100)) {
                if (0 == (year % 400)) {
                    return true;
                } else {
                    return false;
                }//end of if (0 == (year % 400))
            } else {
                return true;
            }//end of if (0 == (year % 100))
        } else {
            return false;
        }//end of if (0 == (year % 4))
    }
}

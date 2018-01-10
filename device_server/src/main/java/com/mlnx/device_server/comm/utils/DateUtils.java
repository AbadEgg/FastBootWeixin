package com.mlnx.device_server.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/22.
 */
public class DateUtils {

    public enum TimeState {
        MORNING, AFTERNOON, NIGHT
    }

    public static long calTimeInterval(String startTime, String stopTime, String dateFormat)
            throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.parse(stopTime).getTime() - format.parse(startTime).getTime();
    }

    public static String format(long time, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date(time));
    }


    public static Date parse(String time, String dateFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.parse(time);
    }

    /**
     * 判断是否为同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar
                .MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DAY_OF_MONTH) ==
                calendar2.get(Calendar.DAY_OF_MONTH))
            return true;
        return false;
    }


    /**
     * 返回时长 00：00：00
     *
     * @param duration
     * @return
     */
    public static String getDuration(long duration) {
        duration /= 1000;
        int second = (int) (duration % 60);
        duration /= 60;
        int minute = (int) (duration % 60);
        duration /= 60;
        int hour = (int) (duration % 60);

        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%02d", hour));
        builder.append(":");
        builder.append(String.format("%02d", minute));
        builder.append(":");
        builder.append(String.format("%02d", second));
        return builder.toString();
    }

    /**
     * 返回时间状态
     * @param time
     * @return
     */
    public static TimeState getTimeState(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);

        if (calendar.get(Calendar.HOUR_OF_DAY) > 5 && calendar.get(Calendar.HOUR_OF_DAY) < 12)
            return TimeState.MORNING;
        else if (calendar.get(Calendar.HOUR_OF_DAY) < 18)
            return TimeState.AFTERNOON;
        else
            return TimeState.NIGHT;
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(long birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(new Date(birthday))) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(new Date(birthday));
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

}

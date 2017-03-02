package com.zw.birthdays.utils;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * 时间相关常用工具类
 * Created by Yuri on 2016/4/20.
 */
public class TimeUtil {

    private static final String TAG = TimeUtil.class.getSimpleName();

    /**
     * 获取当前时间，并格式化
     *
     * @return 当前时间格式化后的字符
     */
    public static String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    /**
     * 获取当前时间，并格式化
     *
     * @return 当前时间格式化后的字符
     */
    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date);
    }

    /**
     * 获取当前时间，并格式化
     *
     * @return 当前时间格式化后的字符
     */
    public static String getDate(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 获取传入时间的农历或公历日期
     *
     * @param time    时间戳
     * @param isLunar 是否公历 true：农历  false：公历
     * @return
     */
    public static String getDate(long time, boolean isLunar) {
        if (isLunar) {
            return LunarCalendar.getLunarData(time);
        } else {
            return getDate(time);
        }
    }


    /**
     * 按指定格式返回时间
     *
     * @return 当前时间格式化后的字符
     */
    public static String getDate(long time, String formats) {
        SimpleDateFormat format = new SimpleDateFormat(formats, Locale.CHINA);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 距离生日剩余天数
     *
     * @param time
     * @param isLunar
     * @return
     */
    public static int getDifferDay(long time, boolean isLunar) {
        Calendar currentTime = new GregorianCalendar();
        currentTime.setTimeInMillis(System.currentTimeMillis());
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        if (isLunar) {
            int[] lunarDate = LunarCalendar.lunarToSolar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar
                    .DAY_OF_MONTH), true);
            calendar.set(lunarDate[0], lunarDate[1] - 1, lunarDate[2]);
        }
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (calendar.getTimeInMillis() < currentTime.getTimeInMillis()) {
            calendar.set(currentTime.get(Calendar.YEAR) + 1, month, day);
        } else {
            calendar.set(currentTime.get(Calendar.YEAR), month, day);
        }
        Log.d(TAG, "getDifferDay: " + getDate(calendar.getTimeInMillis(), false) + "  " + getDate(currentTime.getTimeInMillis(), false));
        return Integer.parseInt(String.valueOf(((calendar.getTimeInMillis() - currentTime.getTimeInMillis()) / (1000 * 3600 * 24))));
    }


    /**
     * 获取宝宝的岁数 格式：2岁8个月
     *
     * @param birthdayTime 宝宝的出生日期
     */

    public static String getBabyAge(long birthdayTime, boolean isLunar) {
        Calendar birthday = Calendar.getInstance();
        birthday.setTimeInMillis(birthdayTime);

        Calendar now = Calendar.getInstance();
        int day;
        int month;
        int year;
        if (isLunar) {
            int[] data = LunarCalendar.solarToLunar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            day = data[2] - birthday.get(Calendar.DAY_OF_MONTH);
            month = data[1] - birthday.get(Calendar.MONTH);
            year = data[0] - birthday.get(Calendar.YEAR);
        } else {
            day = now.get(Calendar.DAY_OF_MONTH) - birthday.get(Calendar.DAY_OF_MONTH);
            month = now.get(Calendar.MONTH) - birthday.get(Calendar.MONTH);
            year = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        }

        //按照减法原理，先day相减，不够向month借；然后month相减，不够向year借；最后year相减。
        if (day < 0) {
            month -= 1;
            now.add(Calendar.MONTH, -1);//得到上一个月，用来得到上个月的天数。
            day += now.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        if (month < 0) {
            month = (month + 12) % 12;
            year--;
        }

        String result;
        if (year < 0) {
            return null;
        }

        if (year == 0 && month == 0 && day == 0) {
            return "0天";
        }

        String monthStr = month > 0 ? month + "个月" : "";
        String dayStr = day > 0 ? day + "天" : "";

        if (year > 0) {
            result = year + "年" + month + "个月";
        } else {
            result = monthStr + dayStr;
        }

        return result;
    }

    /**
     * 判断两个时间是否为同一天
     *
     * @param timeA time in mills
     * @param timeB time in mills
     * @return true 两个时间为同一天；false：非同一天
     */
    public static boolean isSameDay(long timeA, long timeB) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTimeInMillis(timeA);
        Calendar calDateB = Calendar.getInstance();
        calDateB.setTimeInMillis(timeB);
        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断指定时间是否就是今天的日期
     *
     * @param time time in mills
     * @return true toady，else not
     */
    public static boolean isToady(long time) {
        return time > 0 && isSameDay(time, System.currentTimeMillis());
    }
}

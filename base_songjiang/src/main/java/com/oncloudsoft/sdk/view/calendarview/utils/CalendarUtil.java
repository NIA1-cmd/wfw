package com.oncloudsoft.sdk.view.calendarview.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;


import com.oncloudsoft.sdk.utils.DateUtil;
import com.oncloudsoft.sdk.view.calendarview.bean.DateBean;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CalendarUtil {
    /**
     * 获得当月显示的日期（上月 + 当月 + 下月）
     *
     * @param year  当前年份
     * @param month 当前月份
     * @return
     */
    public static List<DateBean> getMonthDate(int year, int month, Map<String, String> map) {
        List<DateBean> datas = new ArrayList<>();
        int week = SolarUtil.getFirstWeekOfMonth(year, month - 1);

        int lastYear;
        int lastMonth;
        if (month == 1) {
            lastMonth = 12;
            lastYear = year - 1;
        } else {
            lastMonth = month - 1;
            lastYear = year;
        }
        int lastMonthDays = SolarUtil.getMonthDays(lastYear, lastMonth);//上个月总天数

        int currentMonthDays = SolarUtil.getMonthDays(year, month);//当前月总天数

        int nextYear;
        int nextMonth;
        if (month == 12) {
            nextMonth = 1;
            nextYear = year + 1;
        } else {
            nextMonth = month + 1;
            nextYear = year;
        }

        for (int i = 0; i < week; i++) {
            datas.add(initDateBean(lastYear, lastMonth, lastMonthDays - week + 1 + i, 0, map));
        }

        for (int i = 0; i < currentMonthDays; i++) {
            datas.add(initDateBean(year, month, i + 1, 1, map));
        }

        for (int i = 0; i < 7 * getMonthRows(year, month) - currentMonthDays - week; i++) {
            datas.add(initDateBean(nextYear, nextMonth, i + 1, 2, map));
        }

        return datas;
    }

    private static DateBean initDateBean(int year, int month, int day, int type, Map<String, String> map) {
        DateBean dateBean = new DateBean();
        dateBean.setSolar(year, month, day);

        if (map == null) {
            String[] temp = LunarUtil.solarToLunar(year, month, day);
            dateBean.setLunar(new String[]{temp[0], temp[1]});
            dateBean.setLunarHoliday(temp[2]);
        } else {
            if (map.containsKey(year + "." + month + "." + day)) {
                dateBean.setLunar(new String[]{"", map.get(year + "." + month + "." + day), ""});
            } else {
                dateBean.setLunar(new String[]{"", "", ""});
            }
        }

        dateBean.setType(type);
        dateBean.setTerm(LunarUtil.getTermString(year, month - 1, day));

        if (type == 0) {
            dateBean.setSolarHoliday(SolarUtil.getSolarHoliday(year, month, day - 1));
        } else {
            dateBean.setSolarHoliday(SolarUtil.getSolarHoliday(year, month, day));
        }
        return dateBean;
    }

    public static DateBean getDateBean(int year, int month, int day) {
        return initDateBean(year, month, day, 1, null);
    }

    /**
     * 计算当前月需要显示几行
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthRows(int year, int month) {
        int items = SolarUtil.getFirstWeekOfMonth(year, month - 1) + SolarUtil.getMonthDays(year, month);
        int rows = items % 7 == 0 ? items / 7 : (items / 7) + 1;
        if (rows == 4) {
            rows = 5;
        }
        return rows;
    }

    /**
     * 根据ViewPager position 得到对应年月
     *
     * @param position
     * @return
     */
    public static int[] positionToDate(int position, int startY, int startM) {
        int year = position / 12 + startY;
        int month = position % 12 + startM;

        if (month > 12) {
            month = month % 12;
            year = year + 1;
        }

        return new int[]{year, month};
    }

    /**
     * 根据年月得到ViewPager position
     *
     * @param year
     * @param month
     * @return
     */
    public static int dateToPosition(int year, int month, int startY, int startM) {
        return (year - startY) * 12 + month - startM;
    }

    /**
     * 计算当前日期
     *
     * @return
     */
    public static int[] getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return new int[]{calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)};
    }

    public static int[] strToArray(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] strArray = str.split("\\.");
            int[] result = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                result[i] = Integer.valueOf(strArray[i]);
            }
            return result;
        }
        return null;
    }

    public static long dateToMillis(int[] date) {
        int day = date.length == 2 ? 1 : date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(date[0], date[1], day);
        return calendar.getTimeInMillis();
    }

    public static int getPxSize(Context context, int size) {
        return size * context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int getTextSize1(Context context, int size) {
        return (int) (size * context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static int getTextSize(Context context, int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, size, context.getResources().getDisplayMetrics());

    }

    /**
     * 获取一个时间范围中不能点击的天数
     *
     * @param startStr        开始时间
     * @param endStr          结束时间
     * @param haveContentList 能点击的日期
     * @return 不能点击的天数
     */
    public static ArrayList<String> disEnableDate(String startStr, String endStr, List<String> haveContentList) {
        // 返回的日期集合
        ArrayList<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat(DateUtil.DateFormat.Yearmonthday_03);
        try {
            Date start = dateFormat.parse(startStr);
            Date end = dateFormat.parse(endStr);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                if (!haveContentList.contains(getTimeFormat(tempStart.getTime().getTime()))) {
                    String format = dateFormat.format(tempStart.getTime());//2019.02.04
                    String simplify = simplify(format);

                    days.add(simplify);
                }
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        String[] startSplit = startStr.split("\\.");
        String startDay = startSplit[2];
        for (int i = 1; i < Integer.parseInt(startDay); i++) {//3
            String s = startSplit[0] + "." + startSplit[1] +"."+ i + "";
            s = simplify(s);
            days.add(s);//将每个月开始的一部分添加到不能点击范围中
        }

        String[] endSplit = endStr.split("\\.");
        String endDay = endSplit[2];

        int daysByYearMonth = getDaysByYearMonth(Integer.parseInt(endSplit[0]), Integer.parseInt(endSplit[1]));

        for (int i = daysByYearMonth; i > Integer.parseInt(endDay); i--) {//20
            String e = endSplit[0] + "." + endSplit[1] + "."+i + "";
            e = simplify(e);
            days.add(e);//将每个月结束的一部分添加到不能点击范围中
        }
        return days;

    }

    private static String getTimeFormat(long time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");//这个是你要转成后的时间的格式
        return simplify(sdf.format(new Date(time)));   // 时间戳转换成时间


    }

    /**
     * 26      * 根据 年、月 获取对应的月份 的 天数
     * 27
     */
    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 日期去掉前面的0
     *
     * @param date
     * @return
     */
    public static String simplify(String date) {
        String[] split = date.split("\\.");
        for (int i=0;i<split.length;i++){
           String a= split[i];
            split[i]=Integer.parseInt(a)+"";
        }


//
//        int index1 = date.indexOf(".");
//        int index2 = date.lastIndexOf(".");
//        int month;
//        int day;
//        int year = Integer.parseInt(date.substring(0, index1));
//        if (date.substring(index1 + 1, index1 + 2) == "0") {
//            month = Integer.parseInt(date.substring(index1 + 2, index1 + 3));
//        } else {
//            month = Integer.parseInt(date.substring(index1 + 1, index1 + 3));
//        }
//        if (date.substring(index2 + 1, index2 + 2) == "0") {
//            day = Integer.parseInt(date.substring(index2 + 2, index2 + 3));
//        } else {
//            day = Integer.parseInt(date.substring(index2 + 1, index2 + 3));
//        }
//        String date1 = (year + "." + month + "." + day) + "";
        return split[0]+"."+split[1]+"."+split[2];
    }
}

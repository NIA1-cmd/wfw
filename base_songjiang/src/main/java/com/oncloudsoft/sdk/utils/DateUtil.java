package com.oncloudsoft.sdk.utils;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat sdf;


    public interface DateFormat {

        String YearmonthdayHourminutesseconds_01 = "yyyy-MM-dd HH:mm:ss";
        String YearmonthdayHourminutesseconds_02 = "yyyy年MM月dd日 HH:mm:ss";

        String YearmonthdayHourminutes_01 = "yyyy年MM月dd日 HH:mm";
        String Yearmonthday_01 = "yyyy-MM-dd";
        String Yearmonthday_02 = "yyyy年MM月dd日";
        String Yearmonthday_03 = "yyyy.MM.dd";
        String Yearmonthday_04 = "yyyy/MM/dd";
        String Yearmonth_01 = "yyyy/MM";
        String hourMinutes_01 = "HH:mm";
    }

    public static String getCurrentTiem() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateFormat.YearmonthdayHourminutesseconds_01);// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //毫秒转日期
    public static String longToTime(Long seconds, int simpleType) {
        Date date = new Date(seconds);
        SimpleDateFormat format = null;
        if (simpleType == 0) {
            format = new SimpleDateFormat(DateFormat.YearmonthdayHourminutesseconds_01);
        } else if (simpleType == 1) {
            format = new SimpleDateFormat(DateFormat.Yearmonthday_01);
        } else if (simpleType == 2) {
            format = new SimpleDateFormat(DateFormat.YearmonthdayHourminutesseconds_02);
        } else if (simpleType == 3) {
            format = new SimpleDateFormat(DateFormat.Yearmonthday_02);
        } else if (simpleType == 4) {
            format = new SimpleDateFormat(DateFormat.Yearmonth_01);
        } else if (simpleType == 5) {
            format = new SimpleDateFormat(DateFormat.Yearmonthday_04);
        }
        return format.format(date);
    }


    //毫秒转日期
    public static String longToTime(Long seconds, String simpleType) {
        if (seconds == 0) {//服务器返回错误的时间 直接不显示
            return "";
        } else {
            return new SimpleDateFormat(simpleType).format(new Date(seconds));

        }
    }

    /**
     * 2016-11-08 14:39:38
     * pattern yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String showDate(String seconds) {
        long seconds1 = Long.parseLong(seconds);
        String dateStr = longToTime(seconds1, 0);
        String year = dateStr.substring(0, 4);
        Long yearNum = Long.parseLong(year);
        int month = Integer.parseInt(dateStr.substring(5, 7));
        int day = Integer.parseInt(dateStr.substring(8, 10));
        String hour = dateStr.substring(11, 13);
        String minute = dateStr.substring(14, 16);
        long addtime = Long.parseLong(seconds);
        long today = System.currentTimeMillis();//当前时间的毫秒数
        Date now = new Date(today);
        String nowStr = format(now);
        int nowDay = Integer.parseInt(nowStr.substring(8, 10));
        String result = "";
        long l = today - addtime;//当前时间与给定时间差的毫秒数
        long _month = l / (4 * 7 * 24 * 60 * 60 * 1000);//相差的周数 大于四相差一个月
        long week = l / (7 * 24 * 60 * 60 * 1000);//相差的周数 大于四相差一个月
        long days = l / (24 * 60 * 60 * 1000);//这个时间相差的天数整数,大于1天为前天的时间了,小于24小时则为昨天和今天的时间
        long hours = (l / (60 * 60 * 1000) - days * 24);//这个时间相差的减去天数的小时数
        long min = ((l / (60 * 1000)) - days * 24 * 60 - hours * 60);//
        long s = (l / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - min * 60);

        if (_month > 0) {
            if (_month > 0 && _month < 12) {
                result = _month + "个月前";
            } else if (_month > 12) {
                result = yearNum % 100 + "/" + month + "/" + day + " "/* + hour + ":" + minute + ""*/;
            }
        } else if (week > 0) {
            if (week > 0 && week < 5) {
                result = week + "周前";
            } else {
                result = yearNum % 100 + "/" + month + "/" + day + " " /*+ hour + ":" + minute + ""*/;
            }
        } else if (days > 0) {
            if (days > 0 && days < 2) {
                result = "前天" + hour + ":" + minute + "";

            } else if (days >= 2 && days < 7) {
                result = days + "天前";
            } else {
                result = yearNum % 100 + "/" + month + "/" + day + " "/* + hour + ":" + minute + ""*/;
            }
        } else if (hours > 0) {
            if (day != nowDay) {
                result = "昨天" + hour + ":" + minute + "";
            } else {
                result = hours + "小时前";
            }
        } else if (min > 0) {
            if (min > 0 && min < 15) {
                result = min + "分前";
            } else {
                result = min + "分前";
            }
        } else {
            result = s + "秒前";
        }
        return result;
    }

    public static String format(Date date) {
        sdf = new SimpleDateFormat(DateFormat.YearmonthdayHourminutesseconds_01);
        return sdf.format(date);
    }

    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /*
     * 毫秒转化 为  34:20
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return strMinute + ":" + strSecond;
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "0秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;

                if (minute == 0) {
                    timeStr = second + "秒";
                } else {
                    timeStr = minute + "分" + second + "秒";
                }
            } else {
                hour = minute / 60;
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;

                if (hour == 0) {
                    timeStr = minute + "分" + second + "秒";
                } else {
                    timeStr = hour + "小时" + minute + "分" + second + "秒";
                }


            }
        }
        return timeStr;
    }


    public static void setTime(Context context, TextView textView, long time) {
        long currentTimeMillis = System.currentTimeMillis();
        //            long timestamp = message.getTimestamp();
        Date messageDate = new Date(time);
        //        if (isdetail){
        //            long chazhi = time - Hll.getLastTime();
        //
        //            if (chazhi < 5 * 60 * 1000) {//5分钟之内的消息
        //                textView.setVisibility(View.GONE);
        //            }
        //            Hll.setLastTime(time);
        //        }

        long d = currentTimeMillis - time;
        if (d < 60 * 1000) {
            //textView.setText(R.string.leancloudchatkit_just);
        } else if (d < 60 * 60 * 1000) {
            long t = d / 60 / 1000;
            //textView.setText(t + context.getString(R.string.leancloudchatkit_minutes_ago));
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(messageDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            long messageTime = calendar.getTimeInMillis();

            long dTime = currentTimeMillis - messageTime;

            int day = 24 * 60 * 60 * 1000;
            if (dTime < day) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                textView.setText(format.format(messageDate));
            } else if (dTime < 2 * day) {
                // textView.setText(R.string.leancloudchatkit_yesterday);
            } else {
                int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                if (w <= 0) {
                    w = 7;
                }
                /* String[] weekDays = {"", context.getString(R.string.leancloudchatkit_monday), context.getString(R.string.leancloudchatkit_tuesday), context.getString(R.string.leancloudchatkit_wednesday), context.getString(R.string.leancloudchatkit_thursday), context.getString(R.string.leancloudchatkit_friday), context.getString(R.string.leancloudchatkit_Saturday), context.getString(R.string.leancloudchatkit_sunday)};*/
                if (dTime < (8 - w) * day) {
                    // textView.setText(weekDays[w]);
                } else {
                    Date date = new Date(time);
                    Date t = new Date(currentTimeMillis);
                    SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");
                    if (formatYear.format(t).equals(formatYear.format(date))) {

                        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
                        textView.setText(format.format(messageDate));

                    } else {

                        SimpleDateFormat format = new SimpleDateFormat(DateFormat.Yearmonthday_01);
                        textView.setText(format.format(messageDate));

                    }
                }
            }
        }
    }


    public static Time test(int intervals) {
        ArrayList<Time> pastDaysList = new ArrayList<>();
//        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(getPastDate(i));
//            fetureDaysList.add(getFetureDate(i));
        }
        Time time = new Time();

        time.setStartTime(pastDaysList.get(pastDaysList.size() - 1).getStartTime());
        time.setEndTime(pastDaysList.get(0).getEndTime());
        return time;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateFormat.Yearmonthday_01);
        String result = format.format(today);
        Log.e(null, result);
        return result;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static Time getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(DateFormat.Yearmonthday_01);
        String result = format.format(today);
        Time time = new Time();
        time.setStartTime(getLongTime(result + " 00:00:00", DateFormat.YearmonthdayHourminutesseconds_01));
        time.setEndTime(getLongTime(result + " 23:59:59", DateFormat.YearmonthdayHourminutesseconds_01));

        return time;
    }

    //具体的时间 转  时间戳
    public static Long getLongTime(String timeStamp, String type) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(type);
        Date date = new Date();
        try {
            date = dateFormat.parse(timeStamp);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static class Time {
        private Long startTime;
        private Long endTime;

        public Long getStartTime() {
            return startTime;
        }

        public void setStartTime(Long startTime) {
            this.startTime = startTime;
        }

        public Long getEndTime() {
            return endTime;
        }

        public void setEndTime(Long endTime) {
            this.endTime = endTime;
        }
    }
}

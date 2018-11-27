package com.lingyun.common.support.util.date;

import com.lingyun.common.support.util.string.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateTimeUtil{


    public static class DateFormatString {
        public static final String yyyy_MM_dd = "yyyy-MM-dd";
        public static final String yyyyMMdd = "yyyyMMdd";
        public static final String yyyy_MM_ddHH$mm$ss = "yyyy-MM-dd HH:mm:ss";
        public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
        public static final String HHMMSS = "HHmmss";
        public static final String HH$mm$ss = "HH:mm:ss";
        public static final String HH$mm = "HH:mm";
        public static final String HH$mm$ss$SSS = "HH:mm:ss:SSS";
        public static final String yyyy = "yyyy";
        public static final String yyyy_MM = "yyyy-MM";
        public static final String MM = "MM";
        public static final String dd = "dd";

    }


    private static final long SECONDS_ONE_DAY = 24*60*60L;
    private static final SimpleDateFormat yyyyMMddHHmmssFormat = new SimpleDateFormat(DateFormatString.yyyyMMddHHmmss);
    private static final SimpleDateFormat yyyyForamt = new SimpleDateFormat(DateFormatString.yyyy);
    private static final SimpleDateFormat mmFormat = new SimpleDateFormat(DateFormatString.MM);
    private static final SimpleDateFormat ddFormat = new SimpleDateFormat(DateFormatString.dd);
    private static final SimpleDateFormat yyyy_MM_ddFormat = new SimpleDateFormat(DateFormatString.yyyy_MM_dd);
    private static final SimpleDateFormat yyyy_MMFormat = new SimpleDateFormat(DateFormatString.yyyy_MM);
    private static final SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat(DateFormatString.yyyyMMdd);
    private static final SimpleDateFormat yyyy_MM_ddHH$mm$ssFormat = new SimpleDateFormat(DateFormatString.yyyy_MM_ddHH$mm$ss);


    /**
     * 日期表示法
     */
    public static class DateRepresentation {

        /**
         *
         * @param dateTime yyyy-MM-dd HH:mm:ss
         * @return unix 时间
         */
        public static String getUnixTimeStamp(String dateTime){

            Calendar c = Calendar.getInstance();
            try {
                c.setTime(yyyy_MM_ddHH$mm$ssFormat.parse(dateTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return (c.getTimeInMillis()/1000)+"";
        }
        /**
         * unix 时间 转换
         * @param timestampString  1252639886
         * @param formats
         * @return
         */
        public static String gerUnixTime2String(String timestampString, String formats){

            if(StringUtils.isBlank(timestampString) || "null".equals(timestampString)){
                return "";
            }
            Long timestamp = Long.parseLong(timestampString)*1000;
            String date = new SimpleDateFormat(formats).format(new Date(timestamp));
            return date;
        }
        /**
         *
         * @return unix 时间
         */
        public static String getCurrentUnixTimeStamp(){

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            return (c.getTimeInMillis()/1000)+"";
        }

        /**
         * 时间转字符串
         * @param date  传入时间
         * @param timeFormat  返回时间格式
         * @return ""
         */

        public static String toString(Date date,String timeFormat){
            return date == null ? null : (StringUtils.isBlank(timeFormat)?null:new SimpleDateFormat(timeFormat)).format(date);
        }

        /**
         * 把long型日期转String ；---OK
         */
        public static String longToString(long date, String format) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            // 前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
            Date dt2 = new Date(date * 1000L);
            return sdf.format(dt2); // 得到精确到秒的表示：08/31/2006 21:08:00
        }
        /**
         * 获得当前时间
         * 格式：2014-12-02 10:38:53
         * @return String
         */
        public static String getCurrentTime() {
            return yyyyMMddHHmmssFormat.format(new Date());
        }

        /**
         * 可以获取昨天的日期
         * 格式：2014-12-01
         * @return String
         */
        public static String getYesterdayYYYYMMDD() {
            Date date = new Date(System.currentTimeMillis() - SECONDS_ONE_DAY * 1000L);
            String str = yyyy_MM_ddFormat.format(date);
            try {
                date = yyyyMMddHHmmssFormat.parse(str + " 00:00:00");
                return yyyy_MM_ddFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 可以获取今天的日期
         * 格式：2014-12-01
         * @return String
         */
        public static String getTodayYYYYMMDD() {
            Date date = new Date(System.currentTimeMillis());
            String str = yyyy_MM_ddFormat.format(date);
            try {
                date = yyyyMMddHHmmssFormat.parse(str + " 00:00:00");
                return yyyy_MM_ddFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    /**
     * 日期转换
     */

    public static class DateConvert {

        /**
         * 获得今天零点
         *
         * @return Date
         */
        public static Date getTodayZeroHour() {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.HOUR, 0);
            return cal.getTime();
        }
        /**
         * 获得今天23:59:59
         *
         * @return Date
         */
        public static Date getTodayEndHour() {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.HOUR, 23);
            return cal.getTime();
        }



        public static Date convertStringToDateTime(String datePattern, String strDateTime) {
            try {
                return new Date((new SimpleDateFormat(datePattern)).parse(strDateTime).getTime());
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
        public static Date getMonthEndTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);

            tmpCalendar.add(Calendar.MONTH, 1);
            tmpCalendar.add(Calendar.MILLISECOND, -1);
            return tmpCalendar.getTime();
        }

        public static Date getMonthEndTime(int year, int month) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            return getMonthEndTime(calendar);
        }

        public static Date getMonthEndTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getMonthEndTime(calendar);
        }

        public static Date getMonthStartTime(int year, int month) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            return getMonthStartTime(calendar);
        }

        public static Date getMonthStartTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.DAY_OF_MONTH, 1);
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);
            return tmpCalendar.getTime();
        }

        public static Date getMonthStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getMonthStartTime(calendar);
        }

        public static Date getDayEndTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);

            tmpCalendar.add(Calendar.DATE, 1);
            tmpCalendar.add(Calendar.MILLISECOND, -1);
            return tmpCalendar.getTime();
        }

        public static Date getDayEndTime(int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            return getDayEndTime(calendar);
        }

        public static Date getDayEndTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getDayEndTime(calendar);
        }

        public static Date getDayEndTime(String date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateConvert.convertStringToDateTime(DateFormatString.yyyy_MM_dd, date));
            return getDayEndTime(calendar);
        }

        public static Date getDayStartTime(int year, int month, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            return getDayStartTime(calendar);
        }

        public static Date getDayStartTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);
            return tmpCalendar.getTime();
        }

        public static Date getDayStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getDayStartTime(calendar);
        }



        public static Date getWeekStartTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);
            tmpCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            tmpCalendar.set(Calendar.DAY_OF_WEEK, tmpCalendar.getFirstDayOfWeek());
            return tmpCalendar.getTime();
        }

        public static Date getWeekStartTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getWeekStartTime(calendar);
        }

        public static Date getWeekEndTime(Calendar calendar) {
            Calendar tmpCalendar = (Calendar) calendar.clone();
            tmpCalendar.set(Calendar.HOUR_OF_DAY, 0);
            tmpCalendar.set(Calendar.MINUTE, 0);
            tmpCalendar.set(Calendar.SECOND, 0);
            tmpCalendar.set(Calendar.MILLISECOND, 0);
            tmpCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            tmpCalendar.set(Calendar.DAY_OF_WEEK, tmpCalendar.getFirstDayOfWeek());
            tmpCalendar.add(Calendar.DATE, 7);
            tmpCalendar.set(Calendar.MILLISECOND, -1);
            return tmpCalendar.getTime();
        }

        public static Date getWeekEndTime(Date date) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return getWeekEndTime(calendar);
        }

    }

    /**
     * 日期计算
     */

    public static class DateArithmetic{

        /**
         * 比较日期是否在 期间
         *
         */
        public static boolean between(Date startDate, Date endDate, Date compareDate) {
            if (startDate == null || endDate == null || compareDate == null) {
                return false;
            }

            return (startDate.before(compareDate) || startDate.equals(compareDate))
                    && (compareDate.before(endDate) || compareDate.equals(endDate));
        }

        /**
         * 功能：返回上旬/中旬/下旬
         * 1 ：上旬  2： 中旬  3： 下旬
         */
        public static int getEarlyMidLate(Date date) {
            int day=getDay(date);
            int earlyMidLate=0;
            if(1<=day && day<= 10){
                earlyMidLate=1;
            }
            if(11<=day && day<=20){
                earlyMidLate=2;
            }
            if(20<day){
                earlyMidLate=3;
            }
            return earlyMidLate;
        }

        /**
         * 返回当前月份的第一天
         * @author gongz
         * @return ""
         */
        public static Date currentMonthFirstDay(){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1); //设置为1号，当前日期既为本月第一天
            return calendar.getTime();
        }

        /**
         * 返回当前月份的最后一天
         * @author gongz
         */
        public static Date currentMonthLastDay(){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        }

        /**
         * 两个日期相差的天数
         */
        public static int statisSubDay(Date endDate,Date startDate){

            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.setTime(startDate);
            fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
            fromCalendar.set(Calendar.MINUTE, 0);
            fromCalendar.set(Calendar.SECOND, 0);
            fromCalendar.set(Calendar.MILLISECOND, 0);

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.setTime(endDate);
            toCalendar.set(Calendar.HOUR_OF_DAY, 0);
            toCalendar.set(Calendar.MINUTE, 0);
            toCalendar.set(Calendar.SECOND, 0);
            toCalendar.set(Calendar.MILLISECOND, 0);
            long tempString = (toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
            return Integer.valueOf(Long.toString(tempString));
        }

        /**
         * 时间减去 几小时 返回时间
         */
        public static String getSubTwoDate(String strDate,int a){
            try{
                SimpleDateFormat dft=new SimpleDateFormat(DateFormatString.yyyy_MM_ddHH$mm$ss);
                Calendar   dar=Calendar.getInstance();
                dar.setTime(dft.parse(strDate));
                dar.add(Calendar.HOUR_OF_DAY, -a);
                return dft.format(dar.getTime());
            }catch(Exception e){
                e.printStackTrace();
            }
            return "";
        }



        public static Date getLastMonthDay(){
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            calendar.set(Calendar.MONTH, month-1);
            calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            return calendar.getTime();
        }
        public static Date getNextDay(Date date,int day) {
            return getDateAfterDays(date, day);
        }

        public static Date getDateAfterDays(Date date, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, +day);//+1今天的时间加一天
            date = calendar.getTime();
            return date;
        }
        /**
         * 两时间相减 返回
         */
        public static String getSubTwoTime(String endTime,String startTime){
            SimpleDateFormat df = new SimpleDateFormat(DateFormatString.yyyy_MM_ddHH$mm$ss);

            try{

                Date d1 = df.parse(startTime);
                Date d2 = df.parse(endTime);
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);

                long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//    logger.info(""+days+"天"+hours+"小时"+minutes+"分");
                if(hours <0){
                    hours = new BigDecimal(hours).abs().intValue();
                }
                if(minutes <0){
                    minutes = new BigDecimal(minutes).abs().intValue();
                }
                return ""+days+"-"+hours+"-"+minutes;
            }catch(Exception e){
                e.printStackTrace();
            }
            return "";
        }
        /**
         * 两时间相减 返回
         */
        public static String getSubTwoTimeYY(String endTime,String startTime){
            SimpleDateFormat df = new SimpleDateFormat(DateFormatString.yyyy_MM_dd);

            try{
                Date d1 = df.parse(startTime);
                Date d2 = df.parse(endTime);
                long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
                long days = diff / (1000 * 60 * 60 * 24);
                return ""+days;
            }catch(Exception e){
                e.printStackTrace();
            }
            return "";
        }

        /**
         *
         * 求某一个时间向前多少秒的时间(currentTimeToBefore)---OK
         *
         * @param givenTime
         *            给定的时间
         * @param interval
         *            间隔时间的毫秒数；计算方式 ：n(天)*24(小时)*60(分钟)*60(秒)(类型)
         * @param format_Date_Sign
         *            输出日期的格式；如yyyy-MM-dd、yyyyMMdd等；
         */
        public static String timeBefore(String givenTime, long interval,
                                        String format_Date_Sign) {
            String tomorrow = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format_Date_Sign);
                Date gDate = sdf.parse(givenTime);
                long current = gDate.getTime(); // 将Calendar表示的时间转换成毫秒
                long beforeOrAfter = current - interval * 1000L; // 将Calendar表示的时间转换成毫秒
                Date date = new Date(beforeOrAfter); // 用timeTwo作参数构造date2
                tomorrow = new SimpleDateFormat(format_Date_Sign).format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return tomorrow;
        }
        /**
         * 得到二个日期间的间隔日期；
         *
         * @param endTime
         *            结束时间
         * @param beginTime
         *            开始时间
         * @param isEndTime
         *            是否包含结束日期；
         * @return ""
         */
        public static Map<String, String> getTwoDay(String endTime,
                                                    String beginTime, boolean isEndTime) {
            Map<String, String> result = new HashMap<>();
            if ((endTime == null || endTime.equals("") || (beginTime == null || beginTime
                    .equals(""))))
                return null;
            try {
                Date date = yyyy_MM_ddFormat.parse(endTime);
                endTime = yyyy_MM_ddFormat.format(date);
                Date mydate = yyyy_MM_ddFormat.parse(beginTime);
                long day = (date.getTime() - mydate.getTime())
                        / (24 * 60 * 60 * 1000);
                result = getDateAfterDays(endTime, Integer.parseInt(day + ""), isEndTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }



        /**
         * 根据结束时间以及间隔差值，求符合要求的日期集合；
         *
         */
        public static Map<String, String> getDateAfterDays(String endTime, Integer interval,
                                                           boolean isEndTime) {
            Map<String, String> result = new HashMap<>();
            if (interval == 0 || isEndTime) {
                if (isEndTime)
                    result.put(endTime, endTime);
            }
            if (interval > 0) {
                int begin = 0;
                for (int i = begin; i < interval; i++) {
                    endTime = timeBefore(endTime, SECONDS_ONE_DAY, DateFormatString.yyyy_MM_dd);
                    result.put(endTime, endTime);
                }
            }
            return result;
        }



        /**
         * 获取今天0点开始的秒数
         * @return long
         */
        public static long getTimeNumberToday() {
            Date date = new Date();
            String str = yyyy_MM_ddFormat.format(date);
            try {
                date = yyyy_MM_ddFormat.parse(str);
                return date.getTime() / 1000L;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return 0L;
        }
        /**
         * 获得昨天零点
         *
         * @return Date
         */
        public static Date getYesterdayZeroHour() {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.HOUR, 0);
            return cal.getTime();
        }


        /**
         * 获得指定日期所在的自然周的第一天，即周日
         *
         * @param date
         *            日期
         * @return 自然周的第一天
         */
        public static Date getStartDayOfWeek(Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_WEEK, 1);
            date = c.getTime();
            return date;
        }

        /**
         * 获得指定日期所在的自然周的最后一天，即周六
         */
        public static Date getLastDayOfWeek(Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_WEEK, 7);
            date = c.getTime();
            return date;
        }

        /**
         * 获得指定日期所在当月第一天
         */
        public static Date getStartDayOfMonth(Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.set(Calendar.DAY_OF_MONTH, 1);
            date = c.getTime();
            return date;
        }


            /**
         * 功能：返回日
         */
        public static int getDay(Date date) {
            if (date == null) {
                date = new Date();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(Calendar.DAY_OF_MONTH);
        }

    }

}

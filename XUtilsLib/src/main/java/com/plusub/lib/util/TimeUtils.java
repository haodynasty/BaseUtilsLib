package com.plusub.lib.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * TimeUtils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-8-24
 */
public class TimeUtils {

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }


    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
	 * 格式为yyyy年MM月dd日  HH:mm:ss
	 * <p>Title: getDateCN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateCN() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}
	
	/**
	 * 格式为yyyy年MM月dd日  HH:mm
	 * <p>Title: getDateCN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateCNNotSecond() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}

	/**
	 * 格式为yyyy年MM月dd日
	 * <p>Title: getDateCN
	 * <p>Description:
	 * @return
	 */
	public static String getDateCNNotHour() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}
	
	/**
	 * 格式为yyyy-MM-dd HH:mm
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateENNotSecond() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;
	}
	
	/**
	 * 格式为yyyy-MM-dd HH:mm
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateENNotSecond(long timeMills) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date1 = format1.format(new Date(timeMills));
		return date1;
	}

	/**
	 * 格式为yyyy-MM-dd
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateNotMinByCurrentTime() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;
	}
	
	/**
	 * 格式为yyyy-MM-dd
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateNotMin(long timeMills) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(new Date(timeMills));
		return date1;
	}
	
	/**
	 * 格式为MM-dd
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateNotYear(long timeMills) {
		SimpleDateFormat format1 = new SimpleDateFormat("MM-dd");
		String date1 = format1.format(new Date(timeMills));
		return date1;
	}
	
	/**
	 * 格式为yyyy-MM-dd HH:mm:ss
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @return
	 */
	public static String getDateEN() {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(System.currentTimeMillis()));
		return date1;
	}
	
	/**
	 * 格式为yyyy-MM-dd HH:mm:ss
	 * <p>Title: getDateEN
	 * <p>Description: 
	 * @param timeMills
	 * @return
	 */
	public static String getDateEN(long timeMills){
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date1 = format1.format(new Date(timeMills));
		return date1;
	}

	/**
	 * 格式为HH:mm
	 * <p>Title: getDate
	 * <p>Description: 
	 * @return
	 */
	public static String getDateByHourAndMin() {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		String date = format.format(new Date(System.currentTimeMillis()));
		return date;
	}
	
	/**
	 * 时间间隔天数
	 * <p>Title: getTimeSpaceByHour
	 * <p>Description: 
	 * @param startTimeMills
	 * @param endTimeMills
	 * @return 
	 */
	public static int getTimeSpaceDays(long startTimeMills, long endTimeMills){
		if (endTimeMills < startTimeMills) {
			return 0;
		}
		 return (int) ((endTimeMills-startTimeMills)/(24*60*60*1000));
	}
	
	/**
	 * 时间间隔小时
	 * <p>Title: getTimeSpaceHour
	 * <p>Description: 
	 * @param startTimeMills
	 * @param endTimeMills
	 * @return
	 */
	public static int getTimeSpaceHour(long startTimeMills, long endTimeMills){
		if (endTimeMills < startTimeMills) {
			return 0;
		}
		 return (int) ((endTimeMills-startTimeMills)/(60*60*1000));
	}
	
	/**
	 * 获取时间间隔年
	 * <p>Title: getTimeSpaceYear
	 * <p>Description: 
	 * @param date
	 * @return
	 */
	public static int getTimeSpaceYear(Date date){
		Calendar c = Calendar.getInstance();
		if(c.get(Calendar.YEAR)-(date.getYear()+1900)>0){
			int i = c.get(Calendar.YEAR)-date.getYear();
			return i;
		}
		return 0;
	}
	
	/**
	 * 获取时间间隔
	 * <p>Title: getCreateAt
	 * <p>Description: 
	 * @param date
	 * @return
	 */
	public static String getTimeSpace(Date date){
		return getTimeSpace(date.getTime());
	}
	
	/**
	 * 获取时间间隔
	 * <p>Title: getTimeSpace
	 * <p>Description: 
	 * @param timeMill
	 * @return
	 */
	public static String getTimeSpace(Long timeMill){
		Calendar c = Calendar.getInstance();
		Long sec = 1000l;
		Long min = 60*sec ;
		Long hour = 60*min ;
		Long day = 24*hour ;
		Long month = 30*day ;
		Long currentTime = System.currentTimeMillis() ;
		Long timeSpace = currentTime-timeMill ;
		Date date = new Date(timeMill);
		if(c.get(Calendar.YEAR)-(date.getYear()+1900)>0){
			int i = c.get(Calendar.YEAR)-date.getYear();
			return i+"年前";
		}else if (timeSpace>month) {
			return timeSpace/month+"月前" ;
		}else if (timeSpace>day) {
			return timeSpace/day + "天前" ;
		}else if (timeSpace>hour) {
			return timeSpace/hour + "小时前" ;
		}else if (timeSpace>min) {
			return timeSpace/min + "分钟前" ;
		}else {
			return "刚刚" ;
		}
	}
	
	/**
	 * 秒转换为"*时*分*秒"
	 * <p>Title: secondsToString
	 * <p>Description: 
	 * @param second
	 * @return
	 */
	public static String secondsToString(int second){
	     int h=0,d=0,s=0;
	     s=second%60;
	     second=second/60;
	     d=second%60;
	     h=second/60;
	     if (h == 0) {
			return d+"分"+s+"秒";
		}
	    return h+"时"+d+"分"+s+"秒";
	}

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    /**
     * get current time in milliseconds, format is yyyy-MM-dd HH:mm:ss
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }
    
    /**
     * long time to string, format is yyyy-MM-dd HH:mm:ss
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }
    
    
    /**
	 * 是否为本周
	 * <p>Title: isWeek
	 * <p>Description: 
	 * @param time
	 * @return
	 */
	public static boolean isSameWeek(long time, long time2){
		Calendar ca= Calendar.getInstance();//获取Calendar实例 
		Date date1 = new Date(time);
		Date date2 = new Date(time2);
		ca.setTime(date1);
		int week1 = ca.WEEK_OF_YEAR;
		ca.setTime(date2);
		int week2 = ca.WEEK_OF_YEAR;
		return week1 == week2 ? true:false;
	}
	
	/**
	 * 本周的开始和结束时间
	 * <p>Title: getSameWeekStartAndEndTime
	 * <p>Description: 
	 * @return
	 */
	public static long[] getSameWeekStartAndEndTime(){
		long[] date = {0, 0};
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		 int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		 cal.add(Calendar.DATE, -day_of_week);
		 date[0] = cal.getTimeInMillis();
		 cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		 cal.add(Calendar.DATE, 6);
		 date[1] = cal.getTimeInMillis();
		 return date;
	}
	
	/**
	 * 今天的开始和结束时间
	 * <p>Title: getSameDayStartAndEndTime
	 * <p>Description: 
	 * @return
	 */
	public static long[] getSameDayStartAndEndTime(){
		long[] date = {0, 0};
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date[0] = cal.getTimeInMillis();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		date[1] = cal.getTimeInMillis();
		return date;
	}

	/**
	 * 是否为今天
	 * @return
	 */
	public static boolean isToday(long timeMills){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.setTimeInMillis(timeMills);
		return (year == calendar.get(Calendar.YEAR) && day == calendar.get(Calendar.DAY_OF_YEAR)) ? true:false;
	}

	/**
	 * 获取当前时间是一年中的多少周
	 * @return
	 */
	public static int getWeekOfYear(){
		Calendar cal = Calendar.getInstance();
		//设置计算周数从周一开始算
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取data是一年中的多少周
	 * @return
	 */
	public static int getWeekOfYear(Date date){
		Calendar cal = Calendar.getInstance();
		//设置计算周数从周一开始算
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(7);
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 获取年的最大周数
	 * @param year
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year){
		Calendar c = Calendar.getInstance();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}


	/**
	 * 获得当天0点时间
	 */
	public static Date getTimesmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得当天23:59:59点时间
	 */
	public static Date getTimesNight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得当天开始和结束时间
	 * <p>Title: getSameDayStartAndEndTime
	 * <p>Description:
	 * @return
	 */
	public static long[] getTimeStartAndEndTime(Date inputTime){
		long[] date = {0, 0};
		Calendar cal = Calendar.getInstance();
		cal.setTime(inputTime);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		date[0] = cal.getTimeInMillis();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		date[1] = cal.getTimeInMillis();
		return date;
	}

	/**
	 * 获得timeMills的23:59:59点时间
	 */
	public static Date getTimesNight(long timeMills) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMills);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得timeMills的0点时间
	 */
	public static Date getTimesMorning(long timeMills) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMills);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得昨天0点时间
	 * @return
	 */
	public static Date getYesterdaymorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimesmorning().getTime() - 3600 * 24 * 1000);
		return cal.getTime();
	}

	/**
	 * 获得昨天23:59:59点时间
	 * @return
	 */
	public static Date getYesterdayNight() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(getTimesNight().getTime() - 3600 * 24 * 1000);
		return cal.getTime();
	}

	/**
	 * 获得本月第一天0点时间
	 * @return
	 */
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得本月最后一天24点时间
	 * @return
	 */
	public static Date getTimesMonthnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		return cal.getTime();
	}

	/**
	 * 获得timeMills所在月第一天0点时间
	 * @return
	 */
	public static Date getTimesMonthmorning(long timeMills) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMills);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获得timeMills所在月最后一天24点时间
	 * @return
	 */
	public static Date getTimesMonthnight(long timeMills) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timeMills);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 *获取本季度开始第一天0点时间
	 * @return
	 */
	public static Date getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 * @return
	 */
	public static Date getCurrentQuarterEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentQuarterStartTime());
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}

	/**
	 * 获取本年开始时间
	 * @return
	 */
	public static Date getCurrentYearStartTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.YEAR));
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取本年结束时间
	 * @return
	 */
	public static Date getCurrentYearEndTime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getCurrentYearStartTime());
		cal.add(Calendar.YEAR, 1);
		return cal.getTime();
	}


	/**
	 * 以周为单位获取当前周所属的年（主要解决跨年问题，如2015-12-28是2016年的第一周，
	 * 此时返回的年就应该是2016而不是2015）
	 * @param timeMills
	 * @return 有问题
	 */
//	public static int getYearByWeekDate(long timeMills){
//		Calendar cal = Calendar.getInstance();
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.setMinimalDaysInFirstWeek(7);
//		cal.setTimeInMillis(timeMills);
//		int week = cal.get(Calendar.WEEK_OF_YEAR);
//		//看是否为跨年
//		cal.add(Calendar.DAY_OF_MONTH, -7);
//		int year = cal.get(Calendar.YEAR);
//		if(week < cal.get(Calendar.WEEK_OF_YEAR)){
//			year+=1;
//		}
//		return year;
//	}
}

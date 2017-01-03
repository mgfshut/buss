package com.rhtop.buss.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/**
	 * 返回格式：2007-08-14
	 * @return
	 */
	public static String getToday(){
		String time = "";
		time = getToday("yyyy-MM-dd");
		return time;
	}
	/**
	 * 
	 * @param format 根据指定的格式时间类型返回当前时间
	 * @return
	 */
	public static String getToday(String format){
		return getDateStr(Calendar.getInstance().getTime(),format);
	}
	
	/**
	 * 获取min分钟后的时间字符串
	 * @param format 时间格式，如yyyy-MM-dd HH:mm:ss
	 * @param min 单位分钟
	 * @return 返回min分钟后的时间
	 */
	public static String getRightMin(String format,int min){
		return getDateStr(org.apache.commons.lang.time.DateUtils.addMinutes(Calendar.getInstance().getTime(), min),format);
	}
	/**
	 * 获取min分钟前的时间字符串
	 * @param format 时间格式，如yyyy-MM-dd HH:mm:ss
	 * @param min 单位分钟
	 * @return 返回min分钟前的时间
	 */
	public static String getLeftMin(String format,int min){
		return getRightMin(format,-min);
	}
	/**
	 * 获取min分钟后的时间字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @param min 单位分钟
	 * @return 返回min分钟前的时间
	 */
	public static String getTimeLeftMin(int min){
		return getLeftMin("yyyy-MM-dd HH:mm:ss", min);
	}
	/**
	 * 获取min分钟后的时间字符串
	 * @param min
	 * @return
	 */
	public static String getTimeRightMin(int min){
		return getRightMin("yyyy-MM-dd HH:mm:ss", min);
	}
	
	/**
	 * 日期转字符
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDateStr(Date date,String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	public static Date getDate(String date,String format) throws ParseException{
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.parse(date);
	}
	
	/**
	 * @param millis
	 * @return
	 */
	public static Date parseMills(long millis){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	
	public static Date getDateToday(String format) throws ParseException{
		String str = getDateStr(Calendar.getInstance().getTime(),format);
		return getDate(str,format);
	}
	
//	/**
//	 * 考试年份编码
//	 * @return
//	 */
//	public static String getYearCode(){
//		Calendar cal = Calendar.getInstance();
//    	int year = cal.get(Calendar.YEAR);
//    	String yearStr = new Integer(year).toString();
//    	return yearStr.substring(2,4);//当前年份后两位
//	}
	
	
	public static String getNowTime() {
		return getToday("yyyy-MM-dd HH:mm:ss");
	}
	
	public static String getNowTime(String pattern) {
		return getToday(pattern);
	}
	
	public static boolean checkTxnTime(String startTime, String endTime) {
		String nowDate = getDateStr(new Date(), "HH:mm:ss");
		if(nowDate.compareTo(startTime) >= 0 && nowDate.compareTo(endTime) <= 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断当前时间是否大于过期时间
	 * @return
	 */
	public static boolean checkNowTime(String time){
		String nowDate = getDateStr(new Date(), "yyyy-MM-dd HH:mm:ss");
		if(nowDate.compareTo(time) <= 0){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证码超时时间计算
	 * @param i
	 * @return
	 */
	public static String getExpireTime(int i) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, i);
		return sdf.format(cal.getTime()); 
	}
	
	public static String getAddOneMonthBy(String thisDay) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(thisDay));
			cal.add(Calendar.MONTH, 1);
			return sdf.format(cal.getTime());
		}catch(Exception e){
			System.out.println("日期转换错误");
		}
		return null;
	}
	
}

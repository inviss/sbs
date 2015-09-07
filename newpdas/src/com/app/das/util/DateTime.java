package com.app.das.util;

/**
 * @(#)
 * Copyright 1999-2000 by  LG-EDS Systems, Inc.,
 * Information Technology Group, Application Architecture Team,
 * Application Intrastructure Part.
 * 236-1, Hyosung-2dong, Kyeyang-gu, Inchun, 407-042, KOREA.
 * All rights reserved.
 *
 * NOTICE !      You can copy or redistribute this code freely,
 * but you should not remove the information about the copyright notice
 * and the author.
 *
 * @author  WonYoung Lee, javaservice@hanmail.net, lwy@kr.ibm.com
 * @author  SooKyung Lim, sukyunglim@lgeds.lg.co.kr.
 * 2000.05.29 isValid(??? 체�?) , whichDay(??? 찾기) �????�??
 *            ageBetween, daysBetween �????�??
 * 2000.07.19 addDays, addMonths, addYears �????�??
 * 2000.07.22 whichDay, ageBetween, daysBetween, addDays, addMonths, addYears �??????? *            ?????�?? ??��??존�???? ??? ?????경�? java.text.ParseException �??
 * 2000.07.24 daysBetween �????? from보�? to�??��? ????�면 -999리�????????minus(-)�?? 리�???
 * 2000.07.29 check �????? 리�?�?java.util.Date) ?��?
 *            addMonths �???????
 *            monthsBetween, lastDayOfMonth �????�??
 */
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
/**
 * 시간 관련 UTIL이 정의되어진 CLASS
 
 */
public final class DateTime {

	/**
	 * Don't let anyone instantiate this class
	 */
	public DateTime() {}

	/**
	 * check date string validation with the default format "yyyyMMdd".
	 * @param s date string you want to check with default format "yyyyMMdd".
     * @return date java.util.Date
	 */
	public static java.util.Date check(String s) throws java.text.ParseException {
		return check(s, "yyyyMMdd");
	}

	/**
	 * check date string validation with an user defined format.
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
     * @return date java.util.Date
	 */
	public static java.util.Date check(String s, String format) throws java.text.ParseException {
		if ( s == null )
			throw new java.text.ParseException("date string to check is null", 0);
		if ( format == null )
			throw new java.text.ParseException("format string to check date is null", 0);

		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = null;
		try {
			date = formatter.parse(s);
		}
		catch(java.text.ParseException e) {
            /*
			throw new java.text.ParseException(
				e.getMessage() + " with format \"" + format + "\"",
				e.getErrorOffset()
			);
            */
            throw new java.text.ParseException(" wrong date:\"" + s +
            "\" with format \"" + format + "\"", 0);
		}

		if ( ! formatter.format(date).equals(s) )
			throw new java.text.ParseException(
				"Out of bound date:\"" + s + "\" with format \"" + format + "\"",
				0
			);
        return date;
	}

	/**
	 * check date string validation with the default format "yyyyMMdd".
	 * @param s date string you want to check with default format "yyyyMMdd"
	 * @return boolean true ??? ?????�??, 존�???? ???????	 *                 false ??? ?????�?? ??��?? 존�???? ??? ???????	 */
	public static boolean isValid(String s) throws Exception {
		return DateTime.isValid(s, "yyyyMMdd");
	}
   
	public static boolean isValidDate(String s) throws Exception {
		return DateTime.isValid(s, "yyyy-MM-dd");
	}
	/**
	 * check date string validation with an user defined format.
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return boolean true ??? ?????�??, 존�???? ???????	 *                 false ??? ?????�?? ??��?? 존�???? ??? ???????	 */
	public static boolean isValid(String s, String format) {
/*
		if ( s == null )
			throw new NullPointerException("date string to check is null");
		if ( format == null )
			throw new NullPointerException("format string to check date is null");
*/
		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = null;
		try {
			date = formatter.parse(s);
		}
		catch(java.text.ParseException e) {
            return false;
		}

		if ( ! formatter.format(date).equals(s) )
            return false;

        return true;
	}

	/**
	 * @return formatted string representation of current day with  "yyyy-MM-dd".
	 */
	public static String getDateString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	public static String getDateString2() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	public static String sgetDateString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	/**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int getDay() {
		return getNumberByPattern("dd");
	}

 	/**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int getYear() {
	   return getNumberByPattern("yyyy");
	}

	public static int sgetYear() {
	   return getNumberByPattern("yy");
	}
	/**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int getMonth() {
		return getNumberByPattern("MM");
	}

	/**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static int getNumberByPattern(String pattern) {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return Integer.parseInt(dateString);
	}
		public static String getSiBun(String sibun) throws java.text.ParseException {
		StringBuffer str=new StringBuffer();
		String si="";
		String bun="";
               
		if(sibun!=null){
	 	 si=sibun.substring(0,2);
	 	 bun=sibun.substring(2,4);
		}
		str.append(si+":"+bun);
	       return str.toString();
	}

	/**
	 *
	 * For example, String time = DateTime.getFormatString("yyyy-MM-dd HH:mm:ss");
	 *
	 * @param java.lang.String pattern  "yyyy, MM, dd, HH, mm, ss and more"
	 * @return formatted string representation of current day and time with  your pattern.
	 */
	public static String getFormatString(String pattern) {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat (pattern, java.util.Locale.KOREA);
		String dateString = formatter.format(new java.util.Date());
		return dateString;
	}
    
    
    public static boolean isEqualDay(String s) throws Exception {
        String today=getFormatString("yyyy-MM-dd");
        if(today.equals(s)){
            return true;
        }else{
            return false;
        }
        
    }
   	/**
	 * @return formatted string representation of current day with  "yyyyMMdd".
	 */
	public static String getDateConvertString() {
		 java.text.SimpleDateFormat formatter =
         new java.text.SimpleDateFormat ("yyyyMMddHHmm", java.util.Locale.KOREA);
		 return formatter.format(new java.util.Date());
	}
	public static String getDateConvertString2() {
		 java.text.SimpleDateFormat formatter =
         new java.text.SimpleDateFormat ("yyyyMMddHHmmss", java.util.Locale.KOREA);
		 return formatter.format(new java.util.Date());
	}
	public static String getShortDateString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyyMMdd", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "HHmmss".
	 */
	public static String getShortTimeString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("HHmmss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "yyyy-MM-dd-HH:mm:ss".
	 */
	public static String getTimeStampString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd-HH:mm:ss:SSS", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}
	public static String getTimeStampString2() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd-HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	/**
	 * @return formatted string representation of current time with  "HH:mm:ss".
	 */
	public static String getTimeString() {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("HH:mm:ss", java.util.Locale.KOREA);
		return formatter.format(new java.util.Date());
	}

	
	/**
	 * return days between two date strings with default defined format.(yyyyMMdd)
	 * @param s date string you want to check.
	 * @return int ??? ?????�??, 존�???? ????????????리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 *          0: ?��???(java.util.Calendar.SUNDAY ??�??)
	 *          1: ?????(java.util.Calendar.MONDAY ??�??)
	 *          2: ?????(java.util.Calendar.TUESDAY ??�??)
	 *          3: ?????(java.util.Calendar.WENDESDAY ??�??)
	 *          4: 목�???(java.util.Calendar.THURSDAY ??�??)
	 *          5: �????(java.util.Calendar.FRIDAY ??�??)
	 *          6: ?????(java.util.Calendar.SATURDAY ??�??)
	 * ?? String s = "20000529";
	 *  int dayOfWeek = whichDay(s, format);
	 *  if (dayOfWeek == java.util.Calendar.MONDAY)
	 *      System.out.println(" ????? " + dayOfWeek);
	 *  if (dayOfWeek == java.util.Calendar.TUESDAY)
	 *      System.out.println(" ????? " + dayOfWeek);
	 */
    public static int whichDay(String s) throws java.text.ParseException {
        return whichDay(s, "yyyyMMdd");
    }
    public static int whichDay2(String s) throws java.text.ParseException {
        return whichDay2(s, "yyyy-MM-dd");
    }
	/**
	 * return days between two date strings with user defined format.
	 * @param s date string you want to check.
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int ??? ?????�??, 존�???? ????????????리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 *          0: ?��???(java.otil.Calendar.SUNDAY ??�??)
	 *          1: ?????(java.util.Calendar.MONDAY ??�??)
	 *          2: ?????(java.util.Calendar.TUESDAY ??�??)
	 *          3: ?????(java.util.Calendar.WENDESDAY ??�??)
	 *          4: 목�???(java.util.Calendar.THURSDAY ??�??)
	 *          5: �????(java.util.Calendar.FRIDAY ??�??)
	 *          6: ?????(java.util.Calendar.SATURDAY ??�??)
	 * ?? String s = "2000-05-29";
	 *  int dayOfWeek = whichDay(s, "yyyy-MM-dd");
	 *  if (dayOfWeek == java.util.Calendar.MONDAY)
	 *      System.out.println(" ????? " + dayOfWeek);
	 *  if (dayOfWeek == java.util.Calendar.TUESDAY)
	 *      System.out.println(" ????? " + dayOfWeek);
	 */

    public static int whichDay(String s, String format) throws java.text.ParseException {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);

        java.util.Calendar calendar = formatter.getCalendar();
		calendar.setTime(date);
           
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }
    public static int whichDay2(String s, String format) throws java.text.ParseException {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);

        java.util.Calendar calendar = formatter.getCalendar();
		calendar.setTime(date);
           
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }
    
    public static String startDay(String strDate) throws java.text.ParseException { 
    	
    	int  week=-1;
    	String sdy="";
    	if (strDate!=null && !strDate.equals("")){
    	week=whichDay2(strDate,"yyyy-mm-dd");
    	
    		sdy=backDay(strDate, week, "yyyy-MM-dd");
    	}
    		return sdy;
    	
    	   	
    }

    public static String endDay(String endDate) throws java.text.ParseException { 
    	
    	String sdy="";
    	if (endDate!=null && !endDate.equals("")){
    	sdy=addDays(startDay(endDate), 6, "yyyy-MM-dd");
    	}
    		return sdy;
    	
    	   	
    }
	/**
	 * return days between two date strings with default defined format.("yyyyMMdd")
	 * @param String from date string
	 * @param String to date string
	 * @return int ??? ?????�??, 존�???? ???????2�??��? ?��?????? 리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static int daysBetween(String from, String to) throws java.text.ParseException {
        return daysBetween(from, to, "yyyyMMdd");
    }

	/**
	 * return days between two date strings with user defined format.
	 * @param String from date string
	 * @param String to date string
	 * @return int ??? ?????�??, 존�???? ???????2�??��? ?��????��? 리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static int daysBetween(String from, String to, String format) throws java.text.ParseException {
        java.text.SimpleDateFormat formatter =
        new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
        java.util.Date d1 = check(from, format);
        java.util.Date d2 = check(to, format);
        long duration = d2.getTime() - d1.getTime();
        return (int)( duration/(1000 * 60 * 60 * 24) );
       // seconds in 1 day
    }
   
	/**
	 * return age between two date strings with default defined format.("yyyyMMdd")
	 * @param String from date string
	 * @param String to date string
	 * @return int ??? ?????�??, 존�???? ???????2�??��? ?��?????? 리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static int ageBetween(String from, String to) throws java.text.ParseException {
        return ageBetween(from, to, "yyyyMMdd");
    }

	/**
	 * return age between two date strings with user defined format.
	 * @param String from date string
	 * @param String to date string
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int ??? ?????�??, 존�???? ???????2�??��? ?��?????? 리�?
	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static int ageBetween(String from, String to, String format) throws java.text.ParseException {
        return (int)(daysBetween(from, to, format) / 365 );
    }

	/**
	 * return add day to date strings
	 * @param String date string
	 * @param int ??? ?��?
	 * @return int ??? ?????�??, 존�???? ????????��? ???�?	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static String addDays(String s, int day) throws java.text.ParseException {
        return addDays(s, day, "yyyyMMdd");
    }
    public static String addDays2(String s, int day) throws java.text.ParseException {
        return addDays(s, day, "yyyy-MM-dd");
    }
	/**
	 * return add day to date strings with user defined format.
	 * @param String date string
	 * @param int ??? ?��?
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int ??? ?????�??, 존�???? ????????��? ???�?	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static String backDay(String s, int day, String format) throws java.text.ParseException{
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);

        date.setTime(date.getTime() - ((long)day * 1000 * 60 * 60 * 24));
        return formatter.format(date);
    }
    public static String addDays(String s, int day, String format) throws java.text.ParseException{
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s,format);

                 date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60 * 24));
                 return formatter.format(date);
    }
    public static String addTimes(String s, int day, String format) throws java.text.ParseException{
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);
        date.setTime(date.getTime() + ((long)day * 1000 * 60 * 60));
        return formatter.format(date);
    }
    public static String minusTimes(String s, int day, String format) throws java.text.ParseException{
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);
        date.setTime(date.getTime() - ((long)day * 1000 * 60 * 60));
        return formatter.format(date);
    }

	/**
	 * return add month to date strings
	 * @param String date string
	 * @param int ??? ???
	 * @return int ??? ?????�??, 존�???? ?????????? ???�?	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static String addMonths(String s, int month) throws Exception {
        return addMonths(s, month, "yyyyMMdd");
    }

	/**
	 * return add month to date strings with user defined format.
	 * @param String date string
	 * @param int ??? ???
	 * @param format string representation of the date format. For example, "yyyy-MM-dd".
	 * @return int ??? ?????�??, 존�???? ?????????? ???�?	 *           ???????�� ???거�? 존�???? ??? ???: java.text.ParseException �??
	 */
    public static String addMonths(String s, int addMonth, String format) throws Exception {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);

 		java.text.SimpleDateFormat yearFormat =
		    new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
 		java.text.SimpleDateFormat monthFormat =
		    new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
 		java.text.SimpleDateFormat dayFormat =
		    new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);
        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        int day = Integer.parseInt(dayFormat.format(date));

        month += addMonth;
        if (addMonth > 0) {
            while (month > 12) {
                month -= 12;
                year += 1;
            }
        } else {
            while (month <= 0) {
                month += 12;
                year -= 1;
            }
        }
 		java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
 		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
        String tempDate = String.valueOf(fourDf.format(year))
                         + String.valueOf(twoDf.format(month))
                         + String.valueOf(twoDf.format(day));
        java.util.Date targetDate = null;

        try {
            targetDate = check(tempDate, "yyyyMMdd");
        } catch(java.text.ParseException pe) {
            day = lastDay(year, month);
            tempDate = String.valueOf(fourDf.format(year))
                         + String.valueOf(twoDf.format(month))
                         + String.valueOf(twoDf.format(day));
            targetDate = check(tempDate, "yyyyMMdd");
        }

        return formatter.format(targetDate);
    }

    public static String addYears(String s, int year) throws java.text.ParseException {
        return addYears(s, year, "yyyyMMdd");
    }

    public static String addYears(String s, int year, String format) throws java.text.ParseException {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(s, format);
        date.setTime(date.getTime() + ((long)year * 1000 * 60 * 60 * 24 * (365 + 1)));
        return formatter.format(date);
    }

    public static int monthsBetween(String from, String to) throws java.text.ParseException {
        return monthsBetween(from, to, "yyyyMMdd");
    }

    public static String syear(String s,String format) throws java.text.ParseException {
                java.util.Date sdate = check(s, format);
 		java.text.SimpleDateFormat yformat =
		    new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);

                return yformat.format(sdate);

    }
    public static String smonth(String s,String format) throws java.text.ParseException {
                java.util.Date sdate = check(s, format);
 		java.text.SimpleDateFormat mformat =
		    new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

              return mformat.format(sdate);
    }
    public static String sdate(String s,String format) throws java.text.ParseException {
                java.util.Date sdate = check(s, format);
 		java.text.SimpleDateFormat dformat =
		    new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

        return dformat.format(sdate);
    }
    public static String ssi(String s,String format) throws java.text.ParseException {
                java.util.Date sdate = check(s, format);
 		java.text.SimpleDateFormat sformat =
		    new java.text.SimpleDateFormat("HH", java.util.Locale.KOREA);

        return sformat.format(sdate);
    }
    public static String sbun(String s,String format) throws java.text.ParseException    {
                java.util.Date sdate = check(s, format);
 		java.text.SimpleDateFormat bformat =
		    new java.text.SimpleDateFormat("mm", java.util.Locale.KOREA);

                return bformat.format(sdate);
    }
    public static int monthsBetween(String from, String to, String format) throws java.text.ParseException {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
        java.util.Date fromDate = check(from, format);
        java.util.Date toDate = check(to, format);

        // if two date are same, return 0.
        if (fromDate.compareTo(toDate) == 0) return 0;

 		java.text.SimpleDateFormat yearFormat =
		    new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
 		java.text.SimpleDateFormat monthFormat =
		    new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);
 		java.text.SimpleDateFormat dayFormat =
		    new java.text.SimpleDateFormat("dd", java.util.Locale.KOREA);

        int fromYear = Integer.parseInt(yearFormat.format(fromDate));
        int toYear = Integer.parseInt(yearFormat.format(toDate));
        int fromMonth = Integer.parseInt(monthFormat.format(fromDate));
        int toMonth = Integer.parseInt(monthFormat.format(toDate));
        int fromDay = Integer.parseInt(dayFormat.format(fromDate));
        int toDay = Integer.parseInt(dayFormat.format(toDate));

        int result = 0;
        result += ((toYear - fromYear) * 12);
        result += (toMonth - fromMonth);

//        if (((toDay - fromDay) < 0) ) result += fromDate.compareTo(toDate);
        // ceil�?floor???�과
        if (((toDay - fromDay) > 0) ) result += toDate.compareTo(fromDate);

        return result;
    }

    public static String lastDayOfMonth(String src) throws java.text.ParseException {
        return lastDayOfMonth(src, "yyyyMMdd");
    }

    public static String lastDayOfMonth(String src, String format) throws java.text.ParseException {
 		java.text.SimpleDateFormat formatter =
		    new java.text.SimpleDateFormat (format, java.util.Locale.KOREA);
		java.util.Date date = check(src, format);

 		java.text.SimpleDateFormat yearFormat =
		    new java.text.SimpleDateFormat("yyyy", java.util.Locale.KOREA);
 		java.text.SimpleDateFormat monthFormat =
		    new java.text.SimpleDateFormat("MM", java.util.Locale.KOREA);

        int year = Integer.parseInt(yearFormat.format(date));
        int month = Integer.parseInt(monthFormat.format(date));
        int day = lastDay(year, month);

        java.text.DecimalFormat fourDf = new java.text.DecimalFormat("0000");
 		java.text.DecimalFormat twoDf = new java.text.DecimalFormat("00");
        String tempDate = String.valueOf(fourDf.format(year))
                         + String.valueOf(twoDf.format(month))
                         + String.valueOf(twoDf.format(day));
        date = check(tempDate, format);

        return formatter.format(date);
    }

    private static int lastDay(int year, int month) throws java.text.ParseException {
        int day = 0;
        switch(month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12: day = 31;
                     break;
            case 2: if ((year % 4) == 0) {
                        if ((year % 100) == 0 && (year % 400) != 0) { day = 28; }
                        else { day = 29; }
                    } else { day = 28; }
                    break;
            default: day = 30;
        }
        return day;
    }

    public static String showDate(java.util.Date time) {

	GregorianCalendar cal=new GregorianCalendar();
	cal.setGregorianChange(time);
	
	int year=cal.get(Calendar.YEAR);
	int month=cal.get(Calendar.MONTH)+1;
	int date1=cal.get(Calendar.DATE);
	
	StringBuffer str=new StringBuffer();
	str.append(year+"."+month+"."+date1);
	String date=str.toString();
	return date;
    }

	public static String sdotDate(String dates) {
	   	String year="";
	   	String month="";
	   	String day="";
	   	if(dates!=null){
	 	year=dates.substring(0,4);
	 	month=dates.substring(4,6);
	 	day=dates.substring(6,8);
	                                                
		StringBuffer str=new StringBuffer();
		str.append(year+"."+month+"."+day+"");
		String  date=str.toString();
		return date;
	   	}else{
	   		return null;
	   	}
     }
	public static String gubunDate() {
        String dates=getTimeStampString2();
   	   String year="";
     	String month="";
     	String day="";
 	  year=dates.substring(0,4);
 	  month=dates.substring(5,7);
 	  day=dates.substring(8,10);
                                                
	  StringBuffer str=new StringBuffer();
	  str.append(year+month+day);
	  String  date=str.toString();
	return date;
    }
	
     
	public static String changeDate(String dates) {
   	String year="";
   	String month="";
   	String day="";
   	String date="";
   	if(dates!=null){
 	year=dates.substring(0,4);
 	month=dates.substring(5,7);
 	day=dates.substring(8,10);
                                                
	StringBuffer str=new StringBuffer();
	str.append(year+"/"+month+"/"+day);
	date=str.toString();
   	}else{
     date="";
   	}
	return date;
        }
        public static String slushDate(String dates) {
   	String year="";
   	String month="";
   	String date="";
   	if(dates!=null){
 	year=dates.substring(0,4);
 	month=dates.substring(5,7);
	StringBuffer str=new StringBuffer();
	str.append(year+"/"+month);
	date=str.toString();
   	}
   	return date;
   }
	public static String codeC(String dates) {
   	String year="";
   	String month="";
   	String day="";
 	year=dates.substring(0,4);
	return year;
        }
	public static String dotDate(String dates) {
   	String year="";
   	String month="";
   	String day="";
   	String  date="";
   	if(dates!=null){
 	  year=dates.substring(0,4);
 	  month=dates.substring(5,7);
 	  day=dates.substring(8,10);
      StringBuffer str=new StringBuffer();
	  str.append(year+"."+month+"."+day);
	  date=str.toString();
   	}else{ 
	   date="";
	}
	  return date;
    }  
	public static String dt(String ye,String mo,String da,String si,String bun) throws java.text.ParseException {
		
	StringBuffer str=new StringBuffer();
	str.append(ye+"-"+mo+"-"+da+" ");
	str.append(si+":"+bun+":00");
	String  date=str.toString();
	
       return date;
    }
	
	public static java.util.Date dt2(String ye,String mo,String da,String si,String bun) throws java.text.ParseException {
		java.text.SimpleDateFormat formatter =
            new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	StringBuffer str=new StringBuffer();
	str.append(ye+"-"+mo+"-"+da+" ");
	str.append(si+":"+bun+":00");
	String  date=str.toString();
        java.util.Date dates=new Date();
		try {
			dates = formatter.parse(date);
		}
		catch(java.text.ParseException e) {
            /*
			throw new java.text.ParseException(
				e.getMessage() + " with format \"" + format + "\"",
				e.getErrorOffset()
			);
            */
            throw new java.text.ParseException(" wrong date:\"" + date +
            "\" with format \"" + date + "\"", 0);
		}
       return dates;
    }
	public static java.util.Date sysdates(String ye,String mo,String da,String si,String bun) throws java.text.ParseException {
	java.text.SimpleDateFormat formatter =
          new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
	StringBuffer str=new StringBuffer();
	str.append(ye+"-"+mo+"-"+da+" ");
	str.append(si+":"+bun+":00");
	String  date=str.toString();
        java.util.Date dates=new Date();
	try {
		dates = formatter.parse(date);
	}
	catch(java.text.ParseException e) {
           /*
			throw new java.text.ParseException(
				e.getMessage() + " with format \"" + format + "\"",
				e.getErrorOffset()
			);
            */
            throw new java.text.ParseException(" wrong date:\"" + date +
            "\" with format \"" + date + "\"", 0);
		}
       return dates;
    }
    public static java.util.Date stringConvert(String st) throws java.text.ParseException {
	java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat ("yyyy-MM-dd");
	String  date=st;
        java.util.Date dates=new Date();
	try {
		dates = formatter.parse(date);
	}
	catch(java.text.ParseException e) {
           /*
			throw new java.text.ParseException(
				e.getMessage() + " with format \"" + format + "\"",
				e.getErrorOffset()
			);
            */
            throw new java.text.ParseException(" wrong date:\"" + date +
            "\" with format \"" + date + "\"", 0);
		}
       return dates;
    } public static int stimeConvert(String fn) throws java.text.ParseException {
	       
		  int hh=0;
		  int mm=0;
		  int ss=0;
		 
		  String splitStr[]=new String[3];
		  System.out.println("fn---------------------------------"+fn);
		  if(!fn.equals("") && fn!=null && !fn.equals("0")){
			  fn=fn+":";
			 StringTokenizer st = new StringTokenizer(fn);
			 for(int i=0;i<splitStr.length; i++){
					 splitStr[i]=startZero(st.nextToken(":"));
					 
		    }
		 		
	      if(!splitStr[0].equals("00") &&!splitStr[0].equals("") ){ hh=Integer.parseInt(splitStr[0])*60*60; }
        if(!splitStr[1].equals("")&& !splitStr[1].equals("00") ){ mm=Integer.parseInt(splitStr[1])*60; }
        if(!splitStr[2].equals("") && !splitStr[2].equals("00") ){ ss=Integer.parseInt(splitStr[2]); }		
		  }
				 
			  return hh+mm+ss;
	    }
	     public static String sconvertTime(int in) throws java.text.ParseException {
	       
		  String hh="";
		  String mm="";
		  String ss="";
		  int hss=0;
		     if(in>0){
		 
	           if(in>0 && in<60){
	        	       hh="00";
	        	       mm="00";
	        	       ss=Integer.toString(in);
	           }else if(in>=60 && in<3600){
	        	   hh="00";
	        	   mm=Integer.toString(in/60);
	        	   ss=Integer.toString(in%60);
	           }else{
	        	   hh=Integer.toString(in/3600);
	        	   System.out.println("hjh"+hh);
	        	   hss=in%3600;
	        	   mm=Integer.toString(hss/60);
	        	   ss=Integer.toString(hss%60);
	              	    
	        	   
	           }
		     if(hh.length()<2){hh="0"+hh;}
		     if(mm.length()<2){mm="0"+mm;}
		     if(ss.length()<2){ss="0"+ss;}
				 
			  return hh+":"+mm+":"+ss;
			  
		     }else{
		    	 return "";
		     }
	    }
	     
	     public static String startZero(String str){ 
	  	   
	         String svalue=""; 
	         if(str.substring(0,1)=="0"){
	            svalue=str.substring(1);
	         }else{
	            svalue=str; 
	         }  
	         return svalue; 
	     }
    public static String dtu(String date,String si,String bun) throws java.text.ParseException {
		StringBuffer str=new StringBuffer();
		str.append(date+" ");
		str.append(si+":"+bun+":00");
		String  dates=str.toString();
	     return dates;
	    }
	
	public static String stampYear(String s) throws java.text.ParseException {
      String st=""; 
        if(s!=null){
  	    
         st=s.substring(0,4);
        }else{
        st="";
       }
       return st;
    }
	public static String stampDate(String s) throws java.text.ParseException {
	      String st=""; 
	      System.out.println("s22"+s);
	    
	        if(s!=null){
	  	       st=s.substring(0,8);
	  	     
	        }else{
	           st="";
	       }
	       return st;
	    }
	public static String stampMonth(String s) throws java.text.ParseException {
	      String st=""; 
	        if(s!=null){
	  	      st=s.substring(4,6);
	        }else{
	           st="";
	       }
	       return st;
	    }
	public static String stampDay(String s) throws java.text.ParseException {
	      String st=""; 
	        if(s!=null){
	  	     st=s.substring(6,8);
	        }else{
	        st="";
	       }
	       return st;
	    }
	public static String stampTime(String s) throws java.text.ParseException {
	      String st=""; 
	        if(s!=null){
	  	     
	  	      st=s.substring(8,10);
	        }else{
	        st="";
	       }
	       return st;
	}
	public static String stampMin(String s) throws java.text.ParseException {
	      String st=""; 
	        if(s!=null){
	  	   
	  	      st=s.substring(10,12);
	        }else{
	        st="";
	       }
	       return st;
	}
	public static String dts(String fdate) throws java.text.ParseException {
        String date=""; 
        if(fdate!=null){
  	    StringBuffer str=new StringBuffer();
        String ye=fdate.substring(0,4);
        String mo=fdate.substring(4,6);
        String da=fdate.substring(6,8);
        String si=fdate.substring(8,10);
        String bun=fdate.substring(10,12);
        String sec=fdate.substring(12,14);

	str.append(ye+"-"+mo+"-"+da+" ");
	str.append(si+":"+bun+":"+sec);
        date=str.toString();
       }else{
         date="";
       }
       return date;
    }
	public static String timeDiv(String s) throws java.text.ParseException {
	      String st=""; 
	      String stu=""; 
	      String st2="";
	      if(s!=null){
	  	      st=s.substring(0,2);
	          st2=s.substring(2,4);
	          stu=st+":"+st2;
	       }else{
	    	  stu=""; 
	       }
	          
	       return stu;
	}
	
	public static String dt3(String dates) throws java.text.ParseException {
		StringBuffer str=new StringBuffer();
		String date="";
		String ye="";
		String mo="";
		String da="";
		String si="";
		String bun="";
		if(dates!=null){
		ye=dates.substring(0,4);
	 	mo=dates.substring(4,6);
	    da=dates.substring(6,8);
	    si=dates.substring(8,10);
	    bun=dates.substring(10,12);
		}
		str.append(ye+"-"+mo+"-"+da+" ");
		str.append(si+":"+bun);
		date=str.toString();
		
	       return date;
	}
	 public static int timeConvert(String value, String format) throws java.text.ParseException {
	        java.text.SimpleDateFormat formatter =
	        new java.text.SimpleDateFormat (format,java.util.Locale.KOREA);
	        java.util.Date d1 = check(value, format);
	       
	        long dur = d1.getTime();
	       
	        return (int)( dur/(60 * 60) );
	        // seconds in 1 day
	    }
	    
}

package com.app.das.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import sun.misc.BASE64Decoder;

import com.app.das.business.transfer.MetaInfoDO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;

//import com.dki.ddf.Helper;
//import com.ktf.uniop.hds.com.common.bo.PageForm;
//import com.ktf.uniop.hds.qty.com.QtyCommonUtil;

/**
 * DAS 공통 Utility : XmlUtil
 *
 * @author :
 * @since : 2010-07-21
 * @param :
 * @return :
 */
public class XmlUtil {

	/**
	 * @Description : zero 추가 : int(param) 앞에 0을 붙여 param_len의 자리 수로 만듬
	 *
	 * @see
	 * @param int
	 *            param 변경전 숫자
	 * @param int
	 *            param_len 자리수
	 * @return String 변경된 String
	 * @throws
	 */
	public static String addZero(int param, int param_len) {
		String rtn_val = Integer.toString(param);
		int len = rtn_val.trim().length();
		for (int i = len; i < param_len; i++) {
			rtn_val = "0" + rtn_val;
		}

		return rtn_val;
	}

	/**
	 * @Description : SMS 결과에 0채울때 사용함
	 *
	 * @see
	 * @param int
	 *            param 변경전 숫자
	 * @param int
	 *            param_len 자리수
	 * @return String 변경된 String
	 * @throws
	 */
	public static String addZeroSMSResult(String serverCode, int param) {
		int param_len = 4;
		if (param == 0) {
			param_len = 6;
		}
		String rtn_val = Integer.toString(param);
		int len = rtn_val.trim().length();
		for (int i = len; i < param_len; i++) {
			rtn_val = "0" + rtn_val;
		}
		if (param != 0) {
			rtn_val = serverCode + rtn_val;
		}
		return rtn_val;
	}

	/**
	 * @Description : WAPPUSH 결과에 0채울때 사용함
	 *
	 * @see
	 * @param int
	 *            param 변경전 숫자
	 * @param int
	 *            param_len 자리수
	 * @return String 변경된 String
	 * @throws
	 */
	public static String addZeroWAPPUSHResult(String serverCode, int param) {
		int param_len = 4;
		if (param == 0) {
			param_len = 6;
		}
		String rtn_val = Integer.toString(param);
		int len = rtn_val.trim().length();
		for (int i = len; i < param_len; i++) {
			rtn_val = "0" + rtn_val;
		}
		if (param != 0) {
			rtn_val = serverCode + rtn_val;
		}
		return rtn_val;
	}

	/**
	 * @Description : zero 추가 : String(param) 앞에 0을 붙여 param_len의 자리 수로 만듬
	 *
	 * @see
	 * @param String
	 *            param 변경전 String
	 * @param int
	 *            param_len 자리수
	 * @return String 변경된 String
	 * @throws
	 */
	public static String addZero(String param, int param_len) {
		String rtn_val = param;
		int len = rtn_val.trim().length();
		for (int i = len; i < param_len; i++) {
			rtn_val = "0" + rtn_val;
		}

		return rtn_val;
	}

	/**
	 * Empty String인지 체크
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : boolean
	 */
	public static boolean isEmptyString(String val) {
		if (val == null || val.trim().equalsIgnoreCase("") || val.length() == 0
				|| val.trim().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Empty Array인지 체크
	 *
	 * @author :
	 * @since : 2004-09-09
	 * @param :
	 * @return :
	 */
	public static boolean isEmptyArray(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * int형을 String형으로 변환(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            int
	 * @return : String
	 */
	public static String int2str(int val) {
		return (new Integer(val).toString());
	}

	/**
	 * int형을 String형으로 변환(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            int, String ("#,##0")
	 * @return : String
	 */
	public static String int2str(int val, String format) {
		DecimalFormat form = new DecimalFormat(format);

		return (form.format(new Integer(val)));
	}

	/**
	 * String형을 int형으로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : int
	 */
	public static int str2int(String val) {
		if (isEmptyString(val)) {
			return 0;
		} else {
			return (Integer.valueOf(val).intValue());
		}
	}

	/**
	 * int형을 double형으로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            int
	 * @return : double
	 */
	public static double int2dbl(int val) {
		return (new Integer(val).doubleValue());
	}

	/**
	 * long형을 String형으로 변환(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            long
	 * @return : String
	 */
	public static String long2str(long val) {
		return (new Long(val).toString());
	}

	/**
	 * long형을 String형으로 변환(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            long, String ("#,##0")
	 * @return : String
	 */
	public static String long2str(long val, String format) {
		DecimalFormat form = new DecimalFormat(format);

		return (form.format(new Long(val)));
	}

	/**
	 * String형을 long형으로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : long
	 */
	public static long str2long(String val) {
		if (isEmptyString(val)) {
			return 0;
		} else {
			return (Long.parseLong(val));
		}
	}

	/**
	 * double형을 String형으로 변환(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double
	 * @return : String
	 */
	public static String dbl2str(double val) {
		return (new Double(val).toString());
	}

	/**
	 * double형을 String형으로 변환(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double, String ("#,##0.0")
	 * @return : String
	 */
	public static String dbl2str(double val, String format) {
		DecimalFormat form = new DecimalFormat(format);

		return (form.format(new Double(val)));
	}

	/**
	 * String형을 double형으로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : double
	 */
	public static double str2dbl(String val) {
		if (isEmptyString(val)) {
			return 0;
		} else {
			return (Double.parseDouble(val));
		}
	}

	/**
	 * 특정값을 기준으로 나누어 배열로 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String
	 * @return : String[]
	 */
	public static String[] split(String val, String key) {
		StringTokenizer token = new StringTokenizer(val, key);
		int tokenCount = token.countTokens();
		String[] arr = new String[tokenCount];

		for (int i = 0; i < tokenCount; i++) {
			arr[i] = token.nextToken();
		}

		return arr;
	}

	/**
	 * 월의 첫 날짜를 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String
	 */
	public static String getFirstDate() {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

		return int2str(year)
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 월의 첫 날짜를 반환한다(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String ("-")
	 * @return : String
	 */
	public static String getFirstDate(String format) {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

		return int2str(year) + format
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ format + ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 월의 마지막 날짜를 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String
	 */
	public static String getEndDate() {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return int2str(year)
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 월의 마지막 날짜를 반환한다(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String ("-")
	 * @return : String
	 */
	public static String getEndDate(String format) {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return int2str(year) + format
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ format + ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 현재 날짜를 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String
	 */
	public static String getCurrentDate() {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		return int2str(year)
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 현재 날짜에 year, month, day 를 더한 날짜를 반환 format 적용 시 ex) YYYY-MM-DD, 미적용 시 ex)
	 * YYYYMMDD
	 *
	 * @param int
	 *            year
	 * @param int
	 *            month
	 * @param int
	 *            day return String 'YYYYMMDD' 또는 구분 포맷이 적용된
	 *            YYYY(format)MM(format)DD ex) 2010-01-01
	 */
	public static String getSomeDate(String format, int year, int month, int day) {

		Calendar calendar = Calendar.getInstance();

		if (year != 0)
			calendar.add(Calendar.DAY_OF_YEAR, year);
		if (month != 0)
			calendar.add(Calendar.DAY_OF_MONTH, month);
		if (day != 0)
			calendar.add(Calendar.DAY_OF_WEEK, day);

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);

		return (format == null) ? int2str(year)
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ ((day < 10) ? "0" + int2str(day) : int2str(day))
				: int2str(year)
						+ format
						+ ((month < 10) ? "0" + int2str(month) : int2str(month))
						+ format
						+ ((day < 10) ? "0" + int2str(day) : int2str(day));
	}

	/**
	 * 현재 시간을 반환 String Format yyyyMMdd HH:mm:ss
	 */
	public static String nowDate() {
		String newDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss")
				.format(new Date());
		return newDate;
	}

	/**
	 * 현재 시간을 반환 String Format yyyyMMdd HH:mm:ss
	 */
	public static String getDate(String format) {
		String newDate = new SimpleDateFormat(format).format(new Date());
		return newDate;
	}

	String newDate = new SimpleDateFormat("yyyyMMdd HHmm").format(new Date());

	/**
	 * 현재 날짜를 반환한다(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String ("-")
	 * @return : String
	 */
	public static String getCurrentDate(String format) {
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		return int2str(year) + format
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ format + ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 현재 날짜를 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String YYYYMMDDHH24MISS
	 */
	public static String getCurrentDateTime() {
		return getCurrentDate() + getCurrentTime();
	}

	/**
	 * 현재 시간을 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String
	 */
	public static String getCurrentTime() {
		String format = "HHmmss";
		String tz = "KST";

		int millisPerHour = 60 * 60 * 1000;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, tz);
		fmt.setTimeZone(timeZone);

		String str = fmt.format(new java.util.Date(System.currentTimeMillis()));

		return str;
	}

	/**
	 * 현재 시간을 반환한다(포맷없음)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return : String
	 */
	public static String getBoxCurrentTime() {
		String format = "HHmmssS";
		String tz = "KST";

		int millisPerHour = 60 * 60 * 1000;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, tz);
		fmt.setTimeZone(timeZone);

		String str = fmt.format(new java.util.Date(System.currentTimeMillis()));

		return str;
	}

	/**
	 * 현재 시간을 반환한다(timestamp)
	 *
	 * @author :
	 * @since : ("2004-01-19T14:29:59Z")
	 * @param :
	 * @return : String
	 */
	public static String getTimeStamp() {
		String timestamp = getCurrentDate("-") + "T" + getCurrentTime(":")
				+ "Z";
		return timestamp;
	}


	/**
	 * 현재 시간을 반환한다(포맷적용)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String (":")
	 * @return : String
	 */
	public static String getCurrentTime(String format) {
		format = "HH" + format + "mm" + format + "ss";
		String tz = "KST";

		int millisPerHour = 60 * 60 * 1000;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, tz);
		fmt.setTimeZone(timeZone);

		String str = fmt.format(new java.util.Date(System.currentTimeMillis()));

		return str;
	}

	/**
	 * 년/월/일 구분하여 계산
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String, int ("D", "20040907", 1) 년:Y, 월:M, 일:D
	 * @return : String
	 */
	public static String getCalcDate(String flag, String val, int cal) {
		int y = str2int(val.substring(0, 4));
		int m = str2int(val.substring(4, 6));
		int d = str2int(val.substring(6, 8));

		Calendar calendar = Calendar.getInstance();
		calendar.set(y, m - 1, d);

		if (flag.equals("Y")) {
			calendar.add(Calendar.YEAR, cal);
		} else if (flag.equals("M")) {
			calendar.add(Calendar.MONTH, cal);
		} else if (flag.equals("D")) {
			calendar.add(Calendar.DATE, cal);
		}

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);

		return int2str(year)
				+ ((month < 10) ? "0" + int2str(month) : int2str(month))
				+ ((date < 10) ? "0" + int2str(date) : int2str(date));
	}

	/**
	 * 두 날짜 사이의 Gap일수 계산
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static long getDateGap(String sStartDate, String sEndDate)
			throws Exception {
		long lStartDate = str2long(getLongDate(sStartDate));
		long lEndDate = str2long(getLongDate(sEndDate));

		return (lEndDate - lStartDate) / (24 * (60 * (60 * 1000)));
	}

	/**
	 * 해당날짜를 long 형으로 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static String getLongDate(String sDate) throws Exception {
		Calendar calendar = Calendar.getInstance();
		if (sDate.length() == 8) {
			int y = str2int(sDate.substring(0, 4));
			int m = str2int(sDate.substring(4, 6));
			int d = str2int(sDate.substring(6, 8));
			calendar.set(y, m - 1, d);
		} else if (sDate.length() > 8) {
			int y = str2int(sDate.substring(0, 4));
			int m = str2int(sDate.substring(4, 6));
			int d = str2int(sDate.substring(6, 8));
			int h = str2int(sDate.substring(8, 10));
			int mi = str2int(sDate.substring(10, 12));
			int s = str2int(sDate.substring(12, 14));
			calendar.set(y, m - 1, d, h, mi, s);
		}

		return long2str(calendar.getTimeInMillis());
	}

	/**
	 * 특정날짜의 요일을 반환한다.
	 *
	 * @author : sang
	 * @since : 2005-05-08
	 * @param :
	 *            String sDate 날짜(TYPE = yyyymmdd)
	 * @return : String 요일
	 */
	public static String getDayOfWeek(String inputDate) throws Exception {
		if (inputDate == null || inputDate.length() < 8) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		DateFormat ddf = new SimpleDateFormat("EEE");

		return ddf.format(df.parse(inputDate));
	}

	/**
	 * 두 개의 String값을 비교하여 같을 경우 원하는 값을 반환해 준다
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String, String ("AAA", "AAA", "SELECTED")
	 * @return : String
	 */
	public static String getCompareString(String str1, String str2, String rtn) {
		if (isEmptyString(str1) || isEmptyString(str2)) {
			return "";
		} else {
			if (str1.equals(str2)) {
				return rtn;
			} else {
				return "";
			}
		}
	}


	/**
	 * 숫자포맷을 적용하여 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String ("123456", "#,##0.00") => 123,456.00
	 * @return : String
	 */
	public static String getNumberMask(String val, String format) {
		double d = 0;
		d = str2dbl(val);
		return (dbl2str(d, format));
	}

	/**
	 * 날짜포맷을 적용하여 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String ("20040907", "-") => 2005-09-27
	 * @return : String
	 */
	public static String getDateMask(String val, String format) {
		String str = "";

		if (isEmptyString(val)) {
			str = "";
		} else {
			if (val != null && val.trim().length() >= 8) {
				str = val.substring(0, 4) + format + val.substring(4, 6)
						+ format + val.substring(6, 8);
			} else {
				str = val;
			}
		}

		return str;
	}

	/**
	 * 시간포맷을 적용하여 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String ("123545", ":") => 12:35:45
	 * @return : String
	 */
	public static String getTimeMask(String val, String format) {
		String str = "";

		if (isEmptyString(val.trim())) {
			str = "";
		} else {
			if (val != null && val.length() >= 6) {
				str = val.substring(0, 2) + format + val.substring(2, 4)
						+ format + val.substring(4, 6);
			} else {
				str = val;
			}
		}

		return str;
	}

	/**
	 * 날짜와 시간 포맷을 적용하여 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String, String ("20040907123545", "-", ":") =>
	 *            2005-09-27 12:35:45
	 * @return : String
	 */
	public static String getDateTimeMask(String val, String sDateFormat,
			String sTimeFormat) {
		String str = "";

		if (isEmptyString(val)) {
			str = "";
		} else {
			if (val != null && val.length() >= 14) {
				str = val.substring(0, 4) + sDateFormat + val.substring(4, 6)
						+ sDateFormat + val.substring(6, 8);
				str += " " + val.substring(8, 10) + sTimeFormat
						+ val.substring(10, 12) + sTimeFormat
						+ val.substring(12, 14);
			} else {
				str = val;
			}
		}

		return str;
	}

	/**
	 * 오른쪽으로 특정 문자를 자리수에 맞게 붙여서 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, int, char ("ABC", 5, "Z") => ABCZZ
	 * @return : String
	 */
	public static String getRPad(String val, int len, char pad) {
		String result = val;
		int templen = len - result.getBytes().length;

		for (int i = 0; i < templen; i++) {
			result = result + pad;
		}

		return result;
	}

	/**
	 * 왼쪽으로 특정 문자를 자리수에 맞게 붙여서 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, int, char ("ABC", 5, "Z") => ZZABC
	 * @return : String
	 */
	public static String getLPad(String val, int len, char pad) {
		String result = val;
		int templen = len - result.getBytes().length;

		for (int i = 0; i < templen; i++) {
			result = pad + result;
		}

		return result;
	}

	/**
	 * null String을 공백으로 만들어 반환("null" 이라는 글씨가 찍히는 것을 방지하기 위함)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : String
	 */
	public static String checkNull(String val) {
		if (isEmptyString(val)) {
			return "";
		} else {
			return val;
		}
	}
	/**
	 * null String을 공백으로 만들어 반환("null" 이라는 글씨가 찍히는 것을 방지하기 위함)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : String
	 */
	public static Object checkNull(Object val) {
		if (val==null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * null String을 Default값으로 만들어 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String
	 * @return : String
	 */
	public static String checkNull(String val, String str) {
		if (isEmptyString(val)) {
			return str;
		} else {
			return val;
		}
	}

	/**
	 * null String을 "&nbsp"로 돌려준다(html 코딩시 라인 깨짐방지)
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static String checkNbsp(String val) {
		if (isEmptyString(val)) {
			return "&nbsp;";
		} else {
			return val;
		}
	}

	/**
	 * 정상적인 날짜인지 체크한다
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String
	 * @return : boolean
	 */
	public static boolean isValidDate(String src) {
		int year = 0;
		int month = 0;
		int day = 0;

		if (isEmptyString(src)) {
			return false;
		} else {

			if (src.length() != 8) {
				return false;
			}

			try {
				year = str2int(src.substring(0, 4));
				month = str2int(src.substring(4, 6));
				day = str2int(src.substring(6));
			} catch (Exception e) {
				return false;
			}

			// 윤년 Flag
			boolean flag = false;

			// 입력받은 년도가 윤년인지 체크
			if (year % 4 == 0) {
				flag = true;

				if (year % 100 == 0) {
					flag = false;

					if (year % 400 == 0) {
						flag = true;
					}
				}
			}

			int dayArray[] = new int[12];
			{
				dayArray[0] = 31;
				dayArray[1] = (flag) ? 29 : 28;
				dayArray[2] = 31;
				dayArray[3] = 30;
				dayArray[4] = 31;
				dayArray[5] = 30;
				dayArray[6] = 31;
				dayArray[7] = 31;
				dayArray[8] = 30;
				dayArray[9] = 31;
				dayArray[10] = 30;
				dayArray[11] = 31;
			}

			if (month < 1 || month > 12) {
				return false;
			}

			if (day < 1 || day > dayArray[month - 1]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 원하는 문자열을 제거하여 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String ("ABCD", "AB") => "CD"
	 * @return : String
	 */
	public static String remString(String strSource, String strRemStr) {
		for (;;) {
			int index = strSource.indexOf(strRemStr);

			if (index >= 0) {
				strSource = strSource.substring(0, index)
						+ strSource.substring(index + 1);
			} else {
				break;
			}
		}

		return strSource;
	}

	/**
	 * oldString에서 from 문자열을 to 문자열로 바꾸어 반환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            String, String, String
	 * @return : String
	 */
	public static String replace(String oldString, String from, String to) {
		String newString = oldString;
		int offset = 0;

		while ((offset = newString.indexOf(from, offset)) > -1) {
			StringBuffer temp = new StringBuffer(newString.substring(0, offset));
			temp.append(to);
			temp.append(newString.substring(offset + from.length()));
			newString = temp.toString();
			offset++;
		}

		return newString;
	}

	/**
	 * 올림
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double, int
	 * @return : double
	 */
	public static double roundUp(double doubleValue, int scale)
			throws ArithmeticException, IllegalArgumentException {
		int ROUND_UP = BigDecimal.ROUND_UP;
		double dblReturn = 0;

		if (isEmptyString(dbl2str(doubleValue)) || doubleValue == 0) {
			return 0;
		} else {
			BigDecimal dbl_scaler = new BigDecimal(doubleValue);

			dblReturn = dbl_scaler.setScale(scale, ROUND_UP).doubleValue();
		}

		return dblReturn;
	}

	/**
	 * 반올림
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double, int
	 * @return :
	 */
	public static double round(double doubleValue, int scale)
			throws ArithmeticException, IllegalArgumentException {
		int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
		double dblReturn = 0;

		if (isEmptyString(dbl2str(doubleValue)) || doubleValue == 0) {
			return 0;
		} else {
			BigDecimal dbl_scaler = new BigDecimal(doubleValue);

			dblReturn = dbl_scaler.setScale(scale, ROUND_HALF_UP).doubleValue();
		}

		return dblReturn;
	}

	/**
	 * 내림
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double, int
	 * @return : double
	 */
	public static double trunc(double doubleValue, int scale)
			throws ArithmeticException, IllegalArgumentException {
		int ROUND_DOWN = BigDecimal.ROUND_DOWN;
		double dblReturn = 0;

		if (isEmptyString(dbl2str(doubleValue)) || doubleValue == 0) {
			return 0;
		} else {
			BigDecimal dbl_scaler = new BigDecimal(doubleValue);

			dblReturn = dbl_scaler.setScale(scale, ROUND_DOWN).doubleValue();
		}

		return dblReturn;
	}

	/**
	 * 절대값
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            double
	 * @return : double
	 */
	public static double abs(double doubleValue) throws ArithmeticException,
			IllegalArgumentException {
		double dblReturn = 0;

		if (isEmptyString(dbl2str(doubleValue)) || doubleValue == 0) {
			return 0;
		} else {
			dblReturn = (doubleValue < 0) ? doubleValue * (-1)
					: doubleValue * 1;
		}

		return dblReturn;
	}

	/**
	 * 한글을 UniCode로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static String Ksc2Uni(String sArg)
			throws UnsupportedEncodingException {
		if (sArg == null || sArg.equals("")) {
			return "";
		} else {
			return new String(sArg.getBytes("EUC-KR"), "8859_1");
		}
	}

	/**
	 * UniCode를 한글로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static String Uni2Ksc(String sArg)
			throws UnsupportedEncodingException {
		if (sArg == null || sArg.equals("")) {
			return "";
		} else {
			return new String(sArg.getBytes("8859_1"), "EUC-KR");
		}
	}

	/**
	 * String에 특정 포맷을 적용
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 *            ("123456", "??-??:??") => 12-34:56
	 * @return :
	 */
	public static String getFormatStr(String sArgSrc, String sArgFormat) {
		String sRtn = "";
		int iStart = 0;
		int iLen = 0;
		int iTCount;
		int iFCount;

		iTCount = sArgSrc.length();
		iFCount = sArgFormat.length();

		if (sArgSrc == null || sArgSrc.equals("")) {
			return sRtn;
		}

		if (sArgFormat == null || sArgFormat.equals("")) {
			return sArgSrc;
		}

		if (sArgSrc.equals("&nbsp;")) {
			return "&nbsp;";
		}

		for (int i = 0; i < iTCount; i++) {
			if (!(sArgSrc.substring(i, i + 1).equals("-"))) {
				sRtn = sRtn + sArgSrc.substring(i, i + 1);
			}
		}

		sArgSrc = sRtn;
		iTCount = sArgSrc.length();

		for (int i = 0; i < iFCount; i++) {
			if (sArgFormat.substring(i, i + 1).equals("?")) {
				iLen++;
			}
		}

		if (iTCount < iLen) {
			for (int i = 0; i < (iLen - iTCount); i++) {
				sArgSrc = "0" + sArgSrc;
			}
			iTCount = iLen;
		}

		sRtn = "";
		for (int i = 0; i < iTCount; i++) {
			for (int j = iStart; j < iFCount; j++) {
				if (sArgFormat.substring(j, j + 1).equals("?")) {
					sRtn = sRtn + sArgSrc.substring(i, i + 1);
					iStart = iStart + 1;
					break;
				} else {
					sRtn = sRtn + sArgFormat.substring(j, j + 1);
					iStart = iStart + 1;
				}
			}
		}
		return sRtn + sArgFormat.substring(iStart);
	}

	/**
	 * 사업자번호 형식 체크
	 *
	 * @author : stowes
	 * @since : 2004-10-21
	 * @param :
	 * @return :
	 */
	public static boolean isValidVendorNo(String sArgs) {

		if (sArgs == null || sArgs.length() != 10) {
			return false;
		}

		int iNo = 0;
		int iC1 = 0;
		int iC2 = 0;
		int iC3 = 0;
		int iC4 = 0;
		int iC5 = 0;
		int iC6 = 0;
		int iC7 = 0;
		int iC8 = 0;
		int iC9 = 0;
		int iC10 = 0;
		int iC9_1 = 0;
		int iC9_2 = 0;

		iC1 = Integer.parseInt(sArgs.substring(0, 1));
		iC2 = Integer.parseInt(sArgs.substring(1, 2));
		iC3 = Integer.parseInt(sArgs.substring(2, 3));
		iC4 = Integer.parseInt(sArgs.substring(3, 4));
		iC5 = Integer.parseInt(sArgs.substring(4, 5));
		iC6 = Integer.parseInt(sArgs.substring(5, 6));
		iC7 = Integer.parseInt(sArgs.substring(6, 7));
		iC8 = Integer.parseInt(sArgs.substring(7, 8));
		iC9 = Integer.parseInt(sArgs.substring(8, 9));
		iC10 = Integer.parseInt(sArgs.substring(9, 10));

		iC1 = iC1 * 1;
		iC1 = iC1 % 10;
		iC2 = iC2 * 3;
		iC2 = iC2 % 10;
		iC3 = iC3 * 7;
		iC3 = iC3 % 10;
		iC4 = iC4 * 1;
		iC4 = iC4 % 10;
		iC5 = iC5 * 3;
		iC5 = iC5 % 10;
		iC6 = iC6 * 7;
		iC6 = iC6 % 10;
		iC7 = iC7 * 1;
		iC7 = iC7 % 10;
		iC8 = iC8 * 3;
		iC8 = iC8 % 10;
		iC9 = iC9 * 5;
		iC9_2 = iC9 % 10;
		iC9_1 = iC9 - iC9_2;
		iC9_1 = iC9_1 / 10;

		iNo = iC1 + iC2 + iC3 + iC4 + iC5 + iC6 + iC7 + iC8 + iC9_1 + iC9_2;
		iNo = (iNo % 10);
		iNo = (10 - iNo) % 10;

		if (iNo != iC10) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 이메일 주소 형식 체크
	 *
	 * @author : sarasu
	 * @since : 2004-10-21
	 * @param :
	 * @return :
	 */
	public static boolean isValidEmail(String sEmail) {
		if (sEmail == null || sEmail.equals("")) {
			return false;
		} else {
			return Pattern.matches(
					"[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", sEmail
							.trim());
		}
	}

	/**
	 * Replace String str에서 rep에 해당하는 String을 tok로 replace
	 *
	 * @param str
	 *            대체될 문자를 포함한 값
	 * @param regex
	 *            대체할 문자
	 * @param replacement
	 *            rep가 대체된 문자
	 * @return rep의 값이 replacement로 바뀐 스트링값을 반환한다.
	 */
	public static String replaceALL(String str, String regex, String replacement) {
		String retStr = "";
		if (str == null || (str != null && str.length() < 1)) {
			return "";
		}
		if (regex == null || (regex != null && regex.length() < 1)) {
			return str;
		}

		if ((str.indexOf(regex) == -1)) {
			return str;
		}

		for (int i = 0, j = 0; (j = str.indexOf(regex, i)) > -1; i = j
				+ regex.length()) {
			retStr += (str.substring(i, j) + replacement);
		}

		return retStr
				+ str.substring(str.lastIndexOf(regex) + regex.length(), str
						.length());
	}

	/**
	 * System.currentTimeMillis()에 의해 생성된 시간을 날짜와 시간으로 변환
	 *
	 * @author :
	 * @since : 2005-09-27
	 * @param :
	 * @return :
	 */
	public static String getConvTime(String str) {
		String sRtn = "";
		String sTemp = "";
		long lTemp = 0;

		if (checkNull(str).equals("")) {
			return sRtn;
		}

		lTemp = str2long(str);
		// Date date = new Date(lTemp);

		Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		calendar.setTimeInMillis(lTemp);

		int year = calendar.get(Calendar.YEAR);
		sRtn += int2str(year);

		int month = calendar.get(Calendar.MONTH) + 1;
		sTemp = (month < 10) ? "0" + int2str(month) : int2str(month);
		sRtn += sTemp;

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		sTemp = (day < 10) ? "0" + int2str(day) : int2str(day);
		sRtn += sTemp;

		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		sTemp = (hour < 10) ? "0" + int2str(hour) : int2str(hour);
		sRtn += sTemp;

		int minute = calendar.get(Calendar.MINUTE);
		sTemp = (minute < 10) ? "0" + int2str(minute) : int2str(minute);
		sRtn += sTemp;

		int second = calendar.get(Calendar.SECOND);
		sTemp = (second < 10) ? "0" + int2str(second) : int2str(second);
		sRtn += sTemp;

		return sRtn;
	}

	/**
	 * DelFile 입력받은 경로의 파일삭제( 실제 물리경로 )
	 *
	 * @author :
	 * @since : 2006-09-27
	 * @param :
	 * @return :
	 */
	public static boolean DelFile(String realPath) {
		boolean delOk = false;

		File del = new File(realPath);

		if (del.exists()) {
			delOk = del.delete();
		}
		return delOk;
	}

	/**
	 * 영문, 한글 혼용 문자열을 일정한 길이에서 잘라준다.
	 *
	 * @param String
	 *            str
	 * @param int
	 *            limit
	 * @return
	 */
	public static String CutString(String str, int limit) {
		if (str == null || limit < 4) {
			return str;
		}

		if (str.trim().length() == 0) {
			return str;
		}

		int len = str.length();
		int cnt = 0, index = 0;

		while (index < len && cnt < limit) {
			if (str.charAt(index++) < 256) { // 1바이트 문자라면...
				cnt++; // 길이 1 증가
			} else { // 2바이트 문자라면...
				if (cnt < limit - 3) {
					cnt += 2; // 길이 2 증가
				} else {
					break;
				} // 여기까지.
			}
		}

		if (index < len) {
			str = str.substring(0, index) + "...";
		}

		return str;
	}

	/**
	 * 시작 날짜와 끝날자를 넣고 날차 차이 구하기
	 *
	 * @param String
	 *            begin
	 * @param enString
	 *            endd
	 * @return long
	 */
	public static long diffOfDate(String begin, String end) throws Exception {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = formatter.parse(begin);
		Date endDate = formatter.parse(end);

		long diff = endDate.getTime() - beginDate.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffDays;
	}

	/**
	 * iDay
	 *
	 * @param iDay
	 * @return String
	 */
	public static String getDate(int iDay) {
		Calendar temp = Calendar.getInstance();
		StringBuffer sbDate = new StringBuffer();

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		int nYear = temp.get(Calendar.YEAR);
		int nMonth = temp.get(Calendar.MONTH) + 1;
		int nDay = temp.get(Calendar.DAY_OF_MONTH);

		sbDate.append(nYear);
		if (nMonth < 10)
			sbDate.append("0");
		sbDate.append(nMonth);
		if (nDay < 10)
			sbDate.append("0");
		sbDate.append(nDay);

		return sbDate.toString();
	}

	/**
	 * @Description : 웹형식의 문자열을 일반 문자로 변환
	 *
	 * @see
	 * @param String
	 *            args 변경할 문자열
	 * @return String 변경된 String
	 * @throws
	 */
	public static String transHtmlText(String args) {
		if (args == null)
			return "";

		args = args.replaceAll("&lt;", "<");
		args = args.replaceAll("&gt;", ">");
		args = args.replaceAll("&quot;", "'");
		args = args.replaceAll("&quot;", "\"");
		args = args.replaceAll("<br>", "\r");

		return args;
	}
	
	/**
	 * @Description : 일반 형식 문자열을 웹형식 문자열로 변환. 090714. 추가. 형승민
	 *
	 * @see
	 * @param 	String args 변경할 문자열
	 *            
	 * @return 	String 변경된 String
	 * @throws
	 */
	public static String transTextHtml(String args) {
		if (args == null)
			return "";

		args = args.replaceAll("<", "&lt;");
		args = args.replaceAll(">", "&gt;");

		return args;
	}
	

	/**
	 * Request를 Map형태로 변환
	 *
	 * @param HttpServletRequest
	 *            request
	 * @return HashMap
	 */
	public static Map convertRequestToMap(HttpServletRequest request) {
		Map returnMap = new HashMap();

		if (null == request) {
			return returnMap;
		}

		Map paramMap = request.getParameterMap();
		String paramName = "";
		String str = "";

		Enumeration enumm = request.getParameterNames();
//		System.out.println("##########Start param##########");
		for (int i = 0; i < paramMap.size(); i++) {
			paramName = (String) enumm.nextElement();

			str = ((String[]) paramMap.get(paramName))[0];
//			System.out.println(paramName+"="+str);
			returnMap.put(paramName, checkNull(str));
		}
//		System.out.println("##########End param##########");
		setLogin_id(returnMap);  // DEKIM:20091223 맥스 'LOGIN_ID' 처리
		return returnMap;
	}

	/**
	 * Request를 Map형태로 변환
	 *
	 * @param HttpServletRequest
	 *            request
	 * @return HashMap
	 */
	public static Map convertRequestToMapForWap(HttpServletRequest request) {
		Map returnMap = new HashMap();

		if (null == request) {
			return returnMap;
		}

		Map paramMap = request.getParameterMap();
		String paramName = "";
		String str = "";

		Enumeration enumm = request.getParameterNames();

		for (int i = 0; i < paramMap.size(); i++) {
			paramName = (String) enumm.nextElement();

			str = ((String[]) paramMap.get(paramName))[0];
			returnMap.put(paramName, str);
		}

		return returnMap;
	}

	/**
	 * Map을 Xml형태로 변환
	 *
	 * @param HashMap
	 *            map
	 * @return String
	 */
	public static String convertMapToXml(HashMap map) {
		return convertMapToXml((Map) map);
	}

	/**
	 * Map을 Xml형태로 변환(CDATA가 없을 경우)
	 *
	 * @param HashMap
	 *            map
	 * @return String
	 */
	public static String convertMapToXml(Map map) {
		List results = new ArrayList();
		return convertMapToXml(map, results);
	}
	/**
	 * Map을 Xml형태로 변환(필수 파람값 추가)
	 * @param map
	 * @return
	 */
	public static String convertMapToXmlParam(Map map) {
		List results = new ArrayList();
		String xml =convertMapToXml(map, results); 
		return xml;
	}

	/**
	 * Map을 Xml형태로 변환(CDATA가 존재할 경우)
	 *
	 * @param HashMap
	 *            map
	 * @return String
	 */
	public static String convertMapToXml(Map map, List arrList) {
		String returnXml = "";
		try {
			Element root = new Element("AMAX");
			Element returnValueroot = new Element("ReturnValue");

			Set set = null;
			try {
				set = map.keySet();
			} catch (Exception e) {
				e.printStackTrace();

				try {
					set = map.keySet();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			Object[] hmKeys = null;
			try {
				hmKeys = set.toArray();
			} catch (Exception e) {
				e.printStackTrace();

				try {
					hmKeys = set.toArray();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			for (int i = 0; i < hmKeys.length; i++) {
				String key = (String) hmKeys[i];
				if (null != map.get(key)) {
					if (map.get(key).getClass() == new ArrayList().getClass()) {
						Element returnValue = new Element(key);
						returnValueroot.addContent(returnValue);
						returnValue.setAttribute("count", ((List) map.get(key))
								.size()
								+ "");
						returnValue = loopList((List) map.get(key),
								returnValue, arrList);
					} else if (map.get(key).getClass() == new HashMap()
							.getClass()) {
						Element returnValue = new Element(key);
						returnValueroot.addContent(returnValue);
						returnValue = tmpMap((HashMap) map.get(key),
								returnValue, arrList);
					} else {
						Element elm = new Element(key);
						boolean lIdx = false;
						for (int l = 0; l < arrList.size(); l++) {
							if (arrList.get(l).equals(key)) {
								lIdx = true;
							}
						}
						if (lIdx) {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.addContent(getCDATA(strTemp));
						} else {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.setText(strTemp);
						}

						returnValueroot.addContent(elm);
					}
				} else {
					Element elm = new Element(key);
					elm.setText("");
					returnValueroot.addContent(elm);
				}
			}

			root.addContent(returnValueroot);

			Document doc = new Document(root);

			XMLOutputter xmlOutputter = new XMLOutputter();
			
			returnXml = xmlOutputter.outputString(doc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnXml;
	}
	
	/**
	 * Map을 Xml형태로 변환(CDATA가 존재할 경우)
	 * 필수 파람값 추가(paramList 추가)
	 * @param map
	 * @param arrList
	 * @return
	 */
	public static String convertMapToXmlParam(Map map, List arrList) {
		String returnXml = "";
		try {
			Element root = new Element("AMAX");
			Element returnValueroot = new Element("RETURNVALUE");
			String[] paramList = {"ERRORMESSAGE","ERRORCODE"};
			Set set = null;
			try {
				set = map.keySet();
			} catch (Exception e) {
				e.printStackTrace();

				try {
					set = map.keySet();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

			Object[] hmKeys = null;
			try {
				hmKeys = set.toArray();
			} catch (Exception e) {
				e.printStackTrace();

				try {
					hmKeys = set.toArray();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			String sTmp = "";
			for (int k = 0; k < paramList.length; k++) {
				Element paramValue = new Element(paramList[k].toUpperCase());
				returnValueroot.addContent(paramValue);
				if(map.get(paramList[k])!=null){
					sTmp = map.get(paramList[k])+"";
					paramValue.setText(sTmp);
				}else{
					if(paramList[k].toUpperCase().equals("ERRORCODE")){paramValue.setText("000000");} // DEKIM 성공시에 에러코드값 '000000'
				}
			}
			
			for (int i = 0; i < hmKeys.length; i++) {
				String key = (String) hmKeys[i];
				if (null != map.get(key)) {
					if (map.get(key).getClass() == new ArrayList().getClass()) {
						Element returnValue = new Element(key.toUpperCase());
						returnValueroot.addContent(returnValue);
						returnValue.setAttribute("COUNT", ((List) map.get(key))
								.size()
								+ "");
						returnValue = loopList((List) map.get(key),
								returnValue, arrList);
					} else if (map.get(key).getClass() == new HashMap()
							.getClass()) {
						Element returnValue = new Element(key.toUpperCase());
						returnValueroot.addContent(returnValue);
						returnValue = tmpMap((HashMap) map.get(key),
								returnValue, arrList);
					} else {
						Element elm = new Element(key.toUpperCase());
						boolean lIdx = false;
						for (int l = 0; l < arrList.size(); l++) {
							if (arrList.get(l).equals(key)) {
								lIdx = true;
							}
						}
						if (lIdx) {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.addContent(getCDATA(strTemp));
						} else {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.setText(strTemp);
						}

						returnValueroot.addContent(elm);
					}
				} else {
					Element elm = new Element(key.toUpperCase());
					elm.setText("");
					returnValueroot.addContent(elm);
				}
			}

			root.addContent(returnValueroot);

			Document doc = new Document(root);

			XMLOutputter xmlOutputter = new XMLOutputter();
			
			returnXml = xmlOutputter.outputString(doc);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnXml;
	}
	
	
	
	public static Element paramMap(Map param){
		String returnXml = "";

		
		return null;
	}

	/**
	 * convertMapToXml에서 사용함
	 *
	 * @param HashMap
	 *            map
	 * @param Element
	 *            returnValue
	 * @return Element
	 */
	public static Element tmpMap(HashMap map, Element returnValue, List arrList) {
		try {
			Set set = map.keySet();
			Object[] hmKeys = set.toArray();
			for (int i = 0; i < hmKeys.length; i++) {
				String key = (String) hmKeys[i];

				if (null != map.get(key)) {
					if (map.get(key).getClass() == new ArrayList().getClass()) {
						Element elm = new Element(key.toUpperCase());
						returnValue.addContent(elm);
						returnValue.setAttribute("COUNT", ((List) map.get(key))
								.size()
								+ "");
						returnValue = loopList((List) map.get(key), elm,
								arrList);
					} else if (map.get(key).getClass() == new HashMap()
							.getClass()) {
						Element elm = new Element(key.toUpperCase());
						returnValue.addContent(elm);
						returnValue = tmpMap((HashMap) map.get(key), elm,
								arrList);
					} else {
						Element elm = new Element(key.toUpperCase());
						boolean lIdx = false;
						for (int l = 0; l < arrList.size(); l++) {
							if (arrList.get(l).equals(key)) {
								lIdx = true;
							}
						}
						if (lIdx) {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.addContent(getCDATA(strTemp));
						} else {
							String strTemp = map.get(key) + "";
							// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
							//strTemp = strTemp.trim();
							elm.setText(strTemp);
						}
						returnValue.addContent(elm);
					}
				} else {
					Element elm = new Element(key.toUpperCase());
					elm.setText("");
					returnValue.addContent(elm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	/**
	 * CATA 생성
	 *
	 * @param String
	 *            str
	 * @return CDATA
	 */
	public static CDATA getCDATA(String str) {
		CDATA cRtn = new CDATA(str);
		return cRtn;
	}

	/**
	 * convertMapToXml에서 사용함
	 *
	 * @param List
	 *            list
	 * @param Element
	 *            returnValue
	 * @return Element
	 */
	public static Element loopList(List list, Element returnValue, List arrList) {
		try {
			for (int j = 0; j < list.size(); j++) {
				Element returnList = new Element("ROW");
				Element clone_returnList = returnList;
				returnValue.addContent(returnList);
//				Object rTmp = list.get(j).getClass().getName();
				Map row = new HashMap();
//				System.out.println("list.get(j).getClass()+"+n);
//				rTmp = list.get(j);
//				MetaInfoDO mt = (MetaInfoDO)rTmp;
//				System.out.println("mt.getBrdLeng():"+mt.getBrdLeng());
				row =(Map) list.get(j);
				
				
				Set set = row.keySet();
				Object[] hmKeys = set.toArray();
				for (int i = 0; i < hmKeys.length; i++) {
					String key = (String) hmKeys[i];

					Element returnValue2 = new Element(key.toUpperCase());
					if (null != row.get(key)) {
						if (row.get(key).getClass() == new ArrayList()
								.getClass()) {
							returnList.addContent(returnValue2);
							returnValue2.setAttribute("count", ((List) row
									.get(key)).size()
									+ "");
							returnValue2 = loopList((List) row.get(key),
									returnValue2, arrList);
						} else {
							if (clone_returnList.getName().equals(
									returnList.getName())) {
								Element elm = new Element(key.toUpperCase());
								boolean lIdx = false;
								for (int l = 0; l < arrList.size(); l++) {
									if (arrList.get(l).equals(key)) {
										lIdx = true;
									}
								}
								if (lIdx) {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.addContent(getCDATA(strTemp));
								} else {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.setText(strTemp);
								}

								returnList.addContent(elm);
							} else if (row.get(key).getClass() == new HashMap()
									.getClass()) {
								Element elm = new Element(key.toUpperCase());
								returnValue.addContent(elm);
								returnValue = tmpMap((HashMap) row.get(key),
										elm, arrList);
							} else {
								Element elm = new Element(key.toUpperCase());
								boolean lIdx = false;
								for (int l = 0; l < arrList.size(); l++) {
									if (arrList.get(l).equals(key)) {
										lIdx = true;
									}
								}
								if (lIdx) {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.addContent(getCDATA(strTemp));
								} else {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.setText(strTemp);
								}
								clone_returnList.addContent(elm);
							}
						}
					} else {
						Element elm = new Element(key.toUpperCase());
						elm.setText("");
						clone_returnList.addContent(elm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static Element loopList2(List list, Element returnValue, List arrList) {
		try {
			for (int j = 0; j < list.size(); j++) {
				Element returnList = new Element("ROW");
				Element clone_returnList = returnList;
				returnValue.addContent(returnList);
				Object rTmp = null;
				Map row = new HashMap();
				rTmp =  list.get(j);
				
				Set set = row.keySet();
				Object[] hmKeys = set.toArray();
				for (int i = 0; i < hmKeys.length; i++) {
					String key = (String) hmKeys[i];

					Element returnValue2 = new Element(key.toUpperCase());
					if (null != row.get(key)) {
						if (row.get(key).getClass() == new ArrayList()
								.getClass()) {
							returnList.addContent(returnValue2);
							returnValue2.setAttribute("count", ((List) row
									.get(key)).size()
									+ "");
							returnValue2 = loopList((List) row.get(key),
									returnValue2, arrList);
						} else {
							if (clone_returnList.getName().equals(
									returnList.getName())) {
								Element elm = new Element(key.toUpperCase());
								boolean lIdx = false;
								for (int l = 0; l < arrList.size(); l++) {
									if (arrList.get(l).equals(key)) {
										lIdx = true;
									}
								}
								if (lIdx) {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.addContent(getCDATA(strTemp));
								} else {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.setText(strTemp);
								}

								returnList.addContent(elm);
							} else if (row.get(key).getClass() == new HashMap()
									.getClass()) {
								Element elm = new Element(key.toUpperCase());
								returnValue.addContent(elm);
								returnValue = tmpMap((HashMap) row.get(key),
										elm, arrList);
							} else {
								Element elm = new Element(key.toUpperCase());
								boolean lIdx = false;
								for (int l = 0; l < arrList.size(); l++) {
									if (arrList.get(l).equals(key)) {
										lIdx = true;
									}
								}
								if (lIdx) {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.addContent(getCDATA(strTemp));
								} else {
									String strTemp = row.get(key) + "";
									// trim 제거 (이모티콘을 xml로 만들경우 이모티콘 형태가 어긋나는 경우가 생김 09년07월09일)-안B-
									//strTemp = strTemp.trim();
									elm.setText(strTemp);
								}
								clone_returnList.addContent(elm);
							}
						}
					} else {
						Element elm = new Element(key.toUpperCase());
						elm.setText("");
						clone_returnList.addContent(elm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}



	/**
	 * 전화번호[3-4-4] 규격에 맞게 수정하여 리턴.
	 *
	 * @return
	 */
	public static String getPhone11(String phone) {

		String reciverPhoneCutSum = phone;

		if (phone.length() == 12) {
			String PhoneCut1 = phone.substring(0, 3);
			String PhoneCut2 = phone.substring(4, 12);
			reciverPhoneCutSum = PhoneCut1 + PhoneCut2;
		} else if (phone.length() == 10) {
			reciverPhoneCutSum = phone.substring(0, 3) + "0"
					+ phone.substring(3);
		}

		return reciverPhoneCutSum;
	}

	/**
	 * 실제 전화번호 규격에 맞게 수정하여 리턴(3-4-4 또는 3-3-4 포맷).
	 *
	 * @return
	 */
	public static String getPhoneToMdn(String phone) {

		String reciverPhoneCutSum = phone;
		// System.out.println("phone" + phone);
		// System.out.println("phone.substring(4,5)" + phone.substring(4, 6));
		// System.out.println("phone.substring(4,4)" + phone.substring(4, 5));
		if (phone.length() == 12) {
			String PhoneCut1 = "";
			String PhoneCut2 = "";
			if ("00".equals(phone.substring(4, 6))) {
				PhoneCut1 = phone.substring(0, 3);
				PhoneCut2 = phone.substring(6, 12);
			} else if ("0".equals(phone.substring(4, 5))) {
				PhoneCut1 = phone.substring(0, 3);
				PhoneCut2 = phone.substring(5, 12);
			} else {
				PhoneCut1 = phone.substring(0, 3);
				PhoneCut2 = phone.substring(4, 12);
			}
			// System.out.println("PhoneCut1" + PhoneCut1);
			// System.out.println("PhoneCut2" + PhoneCut2);

			reciverPhoneCutSum = PhoneCut1 + PhoneCut2;
			// System.out.println("reciverPhoneCutSum" + reciverPhoneCutSum);
		} else if (phone.length() == 11) {
			String PhoneCut1 = "";
			String PhoneCut2 = "";
			if ("0".equals(phone.substring(3, 4))) {
				PhoneCut1 = phone.substring(0, 3);
				PhoneCut2 = phone.substring(4, 11);
			} else {
				PhoneCut1 = phone.substring(0, 3);
				PhoneCut2 = phone.substring(3, 11);
			}
			// System.out.println("PhoneCut1" + PhoneCut1);
			// System.out.println("PhoneCut2" + PhoneCut2);

			reciverPhoneCutSum = PhoneCut1 + PhoneCut2;
			// System.out.println("reciverPhoneCutSum" + reciverPhoneCutSum);

		}

		return reciverPhoneCutSum;
	}

	/**
	 * 소스 문자열이 Null 또는 Empty 문자열일 경우 다른 문자열로 치환하는 메소드
	 *
	 * @param src
	 * @param defaultStc
	 */
	public static String defaultString(String src, String defaultStr) {
		if (src == null || src.trim().length() <= 0 || "null".equals(src)) {
			return defaultStr;
		}

		return src;
	}

	/**
	 * String의 오른쪽에 특수한 char를 size만큼 삽입함
	 *
	 * @param String
	 *            src
	 * @param int
	 *            size
	 * @param char
	 *            paddingChar
	 * @return String
	 */
	public static String rPadding(String src, int size, char paddingChar) {
		int srcLength = 0;
		if (src == null) {
			StringBuffer result = new StringBuffer();
			for (int i = 0; i < size; i++)
				result.append(paddingChar);

			return result.toString();
		}
		byte srcBytes[] = src.getBytes();
		srcLength = srcBytes.length;
		if (size == srcLength)
			return src;
		if (size < srcLength)
			return new String(srcBytes, 0, size);
		int paddingCount = size - srcLength;
		StringBuffer result = new StringBuffer();
		result.append(src);
		for (int i = 0; i < paddingCount; i++)
			result.append(paddingChar);

		return result.toString();
	}

	/**
	 * int를 4byte의 바이널리로 변환
	 *
	 * @param int
	 *            value
	 * @return byte[]
	 */
	public static byte[] intToByteArray(int value) {
		return new byte[] { (byte) (value >>> 24), (byte) (value >>> 16),
				(byte) (value >>> 8), (byte) value };
	}

	/**
	 * logn를 4byte의 바이널리로 변환
	 *
	 * @param long
	 *            value
	 * @return byte[]
	 */
	public static byte[] Long2Byte(long value) {
		byte result[] = new byte[8];
		result[0] = (byte) (value >> 56);
		result[1] = (byte) (value >> 48);
		result[2] = (byte) (value >> 40);
		result[3] = (byte) (value >> 32);
		result[4] = (byte) (value >> 24);
		result[5] = (byte) (value >> 16);
		result[6] = (byte) (value >> 8);
		result[7] = (byte) (value);
		return result;
	}

	/**
	 * int를 8bit 바이널리로 변환
	 *
	 * @param int
	 *            value
	 * @return byte[]
	 */
	public static byte[] int8Bit(int value) {
		byte result[] = new byte[1];
		result[0] = (byte) value;

		return result;
	}

	/**
	 * int를 8bit 바이널리로 변환
	 *
	 * @param int
	 *            value
	 * @param int
	 *            value2
	 * @return byte[]
	 */
	public static byte[] int8Bit(int value1, int value2) {
		byte result[] = new byte[1];
		result[0] = (byte) (value1 << 4);

		result[0] += (byte) value2;

		return result;
	}


	/**
	 * 음수이면 0으로 반환
	 *
	 * @param :
	 *            int
	 * @return : String
	 */
	public static int minus2zero(int val) {
		int rInt = 0;

		if (val >= 0)
			rInt = val;

		return rInt;
	}

	/**
	 * Map null처리하기
	 *
	 * @param HashMap
	 *            map
	 * @return String
	 */
	public static Map nullMap(Map map) {
		try {
			if (map != null && !map.isEmpty()) {
				Set set = null;
				try {
					set = map.keySet();
				} catch (Exception e) {
					e.printStackTrace();

					try {
						set = map.keySet();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				Object[] hmKeys = null;
				try {
					hmKeys = set.toArray();
				} catch (Exception e) {
					e.printStackTrace();

					try {
						hmKeys = set.toArray();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				for (int i = 0; i < hmKeys.length; i++) {
					String key = (String) hmKeys[i];
					if (null != map.get(key)) {
						if (map.get(key).getClass() == new ArrayList()
								.getClass()) {
							nullLoopList((List) map.get(key));
						} else if (map.get(key).getClass() == new HashMap()
								.getClass()) {
							nullTmpMap((HashMap) map.get(key));
						} else {
							String strTemp = map.get(key) + "";
							map.put(key, checkNull(strTemp.trim()));
						}
					} else {
						String strTemp = map.get(key) + "";
						map.put(key, checkNull(strTemp.trim()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * nullMap에서 사용함
	 *
	 * @param HashMap
	 *            map
	 * @return void
	 */
	public static void nullTmpMap(HashMap map) {
		try {
			Set set = map.keySet();
			Object[] hmKeys = set.toArray();
			for (int i = 0; i < hmKeys.length; i++) {
				String key = (String) hmKeys[i];

				if (null != map.get(key)) {
					if (map.get(key).getClass() == new ArrayList().getClass()) {
						nullLoopList((List) map.get(key));
					} else if (map.get(key).getClass() == new HashMap()
							.getClass()) {
						nullTmpMap((HashMap) map.get(key));
					} else {
						String strTemp = map.get(key) + "";
						map.put(key, checkNull(strTemp.trim()));
					}
				} else {
					String strTemp = map.get(key) + "";
					map.put(key, checkNull(strTemp.trim()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * nullMap에서 사용함
	 *
	 * @param List
	 *            list
	 * @return void
	 */
	public static void nullLoopList(List list) {
		try {
			for (int j = 0; j < list.size(); j++) {
				Map row = new HashMap();
				row = (Map) list.get(j);
				Set set = row.keySet();
				Object[] hmKeys = set.toArray();
				for (int i = 0; i < hmKeys.length; i++) {
					String key = (String) hmKeys[i];

					if (null != row.get(key)) {
						if (row.get(key).getClass() == new ArrayList()
								.getClass()) {
							nullLoopList((List) row.get(key));
						} else if (row.get(key).getClass() == new HashMap()
								.getClass()) {
							nullTmpMap((HashMap) row.get(key));
						} else {
							String strTemp = row.get(key) + "";
							row.put(key, checkNull(strTemp.trim()));
						}
					} else {
						String strTemp = row.get(key) + "";
						row.put(key, checkNull(strTemp.trim()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * pid에서 ptid 추출
	 *
	 * @param String
	 *            pid
	 * @return pid의 앞세자리
	 * @throws
	 */
	public static String getPtidFromPid(String pid) throws Exception {
		String ptid = "";

		if (!isEmptyString(pid)) {
			if (pid.length() > 3) {
				ptid = pid.substring(0, 3);
			} else {
				throw new Exception("잘못된 pid 입니다.");
			}
		} else {
			throw new Exception("잘못된 pid 입니다.");
		}

		return ptid;
	}

	/**
	 * delayTime만큼 sleep
	 *
	 * @param int
	 *            delayTime
	 * @throws
	 */
	public static void MatrixTime(int delayTime) {

		long saveTime = System.currentTimeMillis();
		long currTime = 0;

		while (currTime - saveTime < delayTime) {
			currTime = System.currentTimeMillis();
		}
	}

	/**
	 * Base64로 Encoding된 파일 String을 파일로 생성하여 저장
	 *
	 * @param String
	 *            inputFile String filePath
	 * @return long : 파일사이즈(byte)
	 * @throws
	 */
	public static long Base64FileMake(String inputFile, String filePath)
			throws IOException {
		long fileSize = 0;
		try {
			byte[] decode = null;
			decode = new BASE64Decoder().decodeBuffer(inputFile);
			File file = new File(filePath);
			FileOutputStream out;
			out = new FileOutputStream(file);
			out.write(decode);
			out.flush();
			out.close();

			fileSize = file.length();
		} catch (IOException e) {
			throw e;
		}
		return fileSize;
	}
	
	/**
	 * 날짜포맷에서 공백과 특수문자를 제거
	 */
	public static String trimString(String in) throws Exception {
		String resultString = "";
		char ch;
		for(int i = 0; i < in.length(); i++){
			ch = in.charAt(i);
			if(ch >= '0' && ch <= '9')
				resultString = resultString + ch;
		}
		
		return resultString;
	}
	
	/**
	 * Method 호출 시스템에 따른 forward name 검사
	 */
	public static boolean himsRequestYn(HttpServletRequest request) {
		/*
		String reqHostNm = request.getRemoteHost();
		String himsHostNm = Helper.getConfiguration("hds").get("HIMS_HOST_NAME");
		
		if(reqHostNm != null && reqHostNm.equals(himsHostNm)) 
			return true;
		else 
			return false;
		*/
		return false;
		
	}

	public static String obj2str(Object object) {
		String returnVal = "";
		if(object!=null) returnVal = object.toString();
		return returnVal;
	}
	
	/*********************************************************************
 * 메소드명 : toText(String args)
 * 파라미터 : String strText
 * 설    명   : < 을 &lt >은 &gt \n 은 <br> 빈칸은 &nbsp;  은 <br>
 *********************************************************************/	
	public static String toText(String args) {
		String str="";
		try {	
			StringBuffer strTxt = new StringBuffer("");
			char charBuff;
			int len=0;
			int i=0;
			
			len = args.length();
			
			for (i=0;i<len;i++) {
				charBuff = (char)args.charAt(i);
				switch (charBuff) {
					case '<' :
						strTxt.append("&lt");
						break;
					case '>' :
						strTxt.append("&gt");
						break;
					case 10 :
						strTxt.append("<br>");
						break;
					case '' :
						strTxt.append("<br>");
						break;
					case 13 :
						strTxt.append("<br>");
						break;
					case ' ' :
						strTxt.append("&nbsp;");
						break;
					case '' :
						strTxt.append("<br>");
						break;
					default :						
						strTxt.append(charBuff);
				}//switch
			}	//for
			str = strTxt.toString();
		}catch (Exception ex) {		
		}
		return str;
    }
	/**
	 * \r\n 을 html의 <br>로 conver한다
	 */	
   public static String convertHtmlBr(String comment) {
		//**********************************************************************
		if (comment == null)
			return "";
		int length = comment.length();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			String tmp = comment.substring(i, i + 1);
			if ("\r".compareTo(tmp) == 0) {
				tmp = comment.substring(++i, i + 1);
				if ("\n".compareTo(tmp) == 0)
					buffer.append("<br>\r");
				else
					buffer.append("\r");
			}
			buffer.append(tmp);
		}
		return buffer.toString();
	}	
   
   /**
	 * XML을 MAP으로 변형
	 *
	 * @param String
	 *            xml
	 * @return Map
	 */
	public static Map convertXmlToMap(String xmldoc) {
		Map map = new HashMap();

		SAXBuilder builder = new SAXBuilder();
		try {
			Document doc = builder.build(new StringReader(xmldoc));

			Element root = doc.getRootElement();

			List parentChildren = root.getChildren();

			Iterator iter = parentChildren.iterator();
			List list = new ArrayList();

			while (iter.hasNext()) {
				Element detail = (Element) iter.next();

				if (detail.getParent().getChildren(detail.getName())
						.size() > 1) {
					list = (List) map.get(detail.getName());
					if (list == null) {
						list = new ArrayList();
					}

					list = xmlListParser(detail, list);
					map.put(detail.getName(), list);
				} else {
					List children = detail.getChildren();

					if (children.size() == 0) {
						map.put(detail.getName(), detail.getText());
					} else {
						map.put(detail.getName(), xmlParser(detail));
					}
				}
			}

		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * XML을 MAP으로 변형(부분 메소드)
	 *
	 * @param Element
	 *            elm
	 * @return Map
	 */
	public static Map xmlParser(Element elm) {

		Map map = new HashMap();

		List parentChildren = elm.getChildren();

		Iterator iter = parentChildren.iterator();
		List list = new ArrayList();

		while (iter.hasNext()) {
			Element detail = (Element) iter.next();
			if (detail.getParent().getChildren(detail.getName()).size() > 1) {
				list = (List) map.get(detail.getName());
				if (list == null) {
					list = new ArrayList();
				}

				list = xmlListParser(detail, list);
				map.put(detail.getName(), list);
			} else {
				List children = detail.getChildren();

				if (children.size() == 0) {
					map.put(detail.getName(), detail.getText());
				} else {
					map.put(detail.getName(), xmlParser(detail));
				}
			}
		}

		return map;
	}

	/**
	 * XML을 MAP으로 변형(부분 메소드)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static List xmlListParser(Element elm, List list) {
		List parentChildren = elm.getChildren();
		Iterator iter = parentChildren.iterator();

		Map listMap = new HashMap();
		while (iter.hasNext()) {
			Element detail = (Element) iter.next();

			if (detail.getParent().getChildren(detail.getName()).size() > 1) {
				list = xmlListParser(detail, list);
			}

			List children = detail.getChildren();
			if (children.size() == 0) {
				listMap.put(detail.getName(), detail.getText());
			} else {
				listMap.put(detail.getName(), xmlParser(detail));
			}
		}
		list.add(listMap);
		return list;
	}
	
	public static String listToXMLMapping(List resultList){
		Map xmlMap = new HashMap();
		xmlMap.put("row", resultList);
 	 	String xml = XmlUtil.convertMapToXmlParam(xmlMap);
		return xml;
	}
	public static String[] stringSplit(String str, String tokenizer){
        String strSplit[];
        StringTokenizer st=new StringTokenizer(str, tokenizer);
        strSplit=new String[st.countTokens()];
        for(int i=0;i<strSplit.length;i++){
                strSplit[i]=st.nextToken();
        }
        return strSplit;
  }
	
	
	public static void setLogin_id(Map entity){
		
		if(entity.containsKey("LOGIN_ID") == true){
			entity.remove("reg_user_id");
			entity.remove("mod_user_id");
			entity.remove("write_id");
			entity.remove("modify_id");
			entity.put("reg_user_id", entity.get("LOGIN_ID")+"");
			entity.put("mod_user_id", entity.get("LOGIN_ID")+"");
			entity.put("write_id", entity.get("LOGIN_ID")+"");
			entity.put("modify_id", entity.get("LOGIN_ID")+"");
		}
		
	}
	
	public static String getToXmlXstream(Object obj){
		XStream xstream = new XStream(new DomDriver("UTF-8", new XmlFriendlyReplacer("-", "_")));
		String xml = xstream.toXML( obj);
		xml ="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + "<das>\n"
		+ xml+ "</das>";
		return xml;
	}
	
	public static String getXmlWrapper(String in_xml){
		String xml="\n<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + in_xml;
		return xml;
		
	}
	
	public static String getFromXmlXstream(String xml){
		
		XStream xStream = new XStream();
		xStream.fromXML(xml);
		return "";
	}
	
	
	
	/**
	 * 녹음형식을 변환(pds -> das)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changRecordCode(String args) {
		String str="";
		/*try {	
		
	
	
				switch (args.length()) {
					case 'M' :
						str="001";						
						break;
					case 'S' :
						str="002";
						break;
					case 'F' :
						str="003";
						break;
					case 'B' :
						str="004";
						break;
					case 'V' :
						str="005";
						break;
					case 'O' :
						str="006";
						break;
					
					default :						
						str="";
				}//switch
				return str;
		}catch (Exception ex) {		
		}*/
		
		
		if(args.equals("M")){
			str="001";
		}else if(args.equals("S")){
			str="002";
		}else if(args.equals("F")){
			str="003";
		}else if(args.equals("B")){
			str="004";
		}else if(args.equals("Y")){
			str="005";
		}else if(args.equals("O")){
			str="006";
		}else if(args.equals("A")){
			str="007";
		}
		
	
		return str;
    }
	
	

	/**
	 * 시청등급 변환(pds -> das)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changViewGrade(String args) {
		String str="";
		
		
		if(args.equals("0")){
			str="001";
		}else if(args.equals("7")){
			str="002";
		}else if(args.equals("12")){
			str="003";
		}else if(args.equals("15")){
			str="004";
		}else if(args.equals("19")){
			str="005";
		}else if(args.equals("IMP")){
			str="006";
		}else if(args.equals("EXP")){
			str="007";
		}else if(args.equals("NONE")){
			str="008";
		}
		
	
		return str;
    }
	
	
	
	/**
	 * 녹음형식을 변환(das -> pds)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changRecordCode2(String args) {
		String str="";
		/*try {	
		
	
	
				switch (args.length()) {
					case 'M' :
						str="001";						
						break;
					case 'S' :
						str="002";
						break;
					case 'F' :
						str="003";
						break;
					case 'B' :
						str="004";
						break;
					case 'V' :
						str="005";
						break;
					case 'O' :
						str="006";
						break;
					
					default :						
						str="";
				}//switch
				return str;
		}catch (Exception ex) {		
		}*/
		
		
		if(args.equals("001")){
			str="M";
		}else if(args.equals("002")){
			str="S";
		}else if(args.equals("003")){
			str="B";
		}else if(args.equals("004")){
			str="F";
		}else if(args.equals("005")){
			str="Y";
		}else if(args.equals("006")){
			str="O";
		}else if(args.equals("007")){
			str="A";
		}
		
	
		return str;
    }
	
	

	/**
	 * 시청등급 변환(das -> pds)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changViewGrade2(String args) {
		String str="";
		
		
		if(args.equals("001")){
			str="0";
		}else if(args.equals("002")){
			str="7";
		}else if(args.equals("003")){
			str="12";
		}else if(args.equals("004")){
			str="15";
		}else if(args.equals("005")){
			str="19";
		}else if(args.equals("006")){
			str="IMP";
		}else if(args.equals("007")){
			str="EXP";
		}else if(args.equals("008")){
			str="NONE";
		}
	
		return str;
    }
	
	
	/**
	 * 시청등급 변환(ifcms -> das)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changRententPerid(String args) {
		String str="";
		
		
		if(args.equals("y1")){
			str="001";
		}else if(args.equals("y5")){
			str="060";
		}else if(args.equals("y10")){
			str="120";
		}else if(args.equals("y20")){
			str="240";
		}
	
		return str;
    }
	
	
	
	
	

	/**
	 * 보존기간 코드 변환(ifcms -> das)
	 *
	 * @param Element
	 *            elm, List list
	 * @return Map
	 */
	public static String changRsvPrdCd(String args) {
		String str="";
		
		
		if(args.equals("d1")){
			str="001";
		}else if(args.equals("d2")){
			str="001";
		}else if(args.equals("d3")){
			str="001";
		}else if(args.equals("d4")){
			str="001";
		}else if(args.equals("d5")){
			str="001";
		}else if(args.equals("w1")){
			str="001";
		}else if(args.equals("w2")){
			str="001";
		}else if(args.equals("w3")){
			str="001";
		}else if(args.equals("w4")){
			str="001";
		}else if(args.equals("m1")){
			str="001";
		}else if(args.equals("m6")){
			str="001";
		}else if(args.equals("y1")){
			str="001";
		}else if(args.equals("y2")){
			str="060";
		}else if(args.equals("y3")){
			str="060";
		}else if(args.equals("y4")){
			str="060";
		}else if(args.equals("y10")){
			str="120";
		}else if(args.equals("y20")){
			str="240";
		}else if(args.equals("p")){
			str="000";
		}else{
			str="000";
		}
		
	
		return str;
    }
	
	
	 
		public static String changeResolution(String args) {
			String str="";
			
			
			if(args.equals("001")){
				str="1";
			}else if(args.equals("002")){
				str="0";
			}else if(args.equals("003")){
				str="2";
			}
		
			return str;
	    }
		
	
	
	
}

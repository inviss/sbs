package kr.co.d2net.commons.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimeConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

public class Utility {

	final static Logger logger = LoggerFactory.getLogger(Utility.class);

	final static String[] FILE_EXT = new String[] {"MXF", "mxf"};
	final static String[] DELETE_EXT = new String[] {"MXF", "mxf", "xml", "mxf.ewc2"};

	public final static Map<String, Object> QUOTA_CHECK = new HashMap<String, Object>();
	public final static Map<String, Object> RES_CHECK = new HashMap<String, Object>();

	/**
	 * 원하는 형식으로 현재날짜를 반환한다.
	 * @param format
	 * @return
	 */
	public static String getTimestamp(String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(System.currentTimeMillis());
			timestamp = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 원하는 형식으로 현재날짜를 반환한다.
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = new Date(System.currentTimeMillis());
			timestamp = sdf.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * 입력한 숫자를 빼거나 더한 날짜를 반환한다.
	 * @param iDay
	 * @return String
	 */
	public static String getDate ( int iDay ) {
		Calendar temp=Calendar.getInstance ();
		StringBuffer sbDate=new StringBuffer ( );

		temp.add ( Calendar.DAY_OF_MONTH, iDay );

		int nYear = temp.get ( Calendar.YEAR );
		int nMonth = temp.get ( Calendar.MONTH ) + 1;
		int nDay = temp.get ( Calendar.DAY_OF_MONTH );

		sbDate.append ( nYear );
		sbDate.append(StringUtils.leftPad(String.valueOf(nMonth), 2, "0"));
		sbDate.append(StringUtils.leftPad(String.valueOf(nDay), 2, "0"));

		return sbDate.toString ( );
	}


	/**
	 * Timestamp 값을 원하는 포맷으로 반환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static String getTimestamp(Timestamp reqTimestamp, String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(reqTimestamp.getTime());
		}catch(Exception e){
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * Date 값을 원하는 포맷으로 반환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static String getDate(Date reqDate, String format) {
		String timestamp = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			timestamp = sdf.format(reqDate);
		}catch(Exception e){
			timestamp = "";
			e.printStackTrace();
		}
		return timestamp;
	}

	/**
	 * Date 값을 원하는 포맷으로 반환한다.
	 * @param reqTimestamp
	 * @param format
	 * @return
	 */
	public static Date getDate(String reqDate, String format) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(reqDate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * <pre>
	 * 입력된 날짜가 지정된 포맷형식인지 판단
	 * </pre>
	 * @param format
	 * @param date
	 * @return boolean
	 */
	public static boolean isValidDate(String format, String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.parse(date);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 두 날짜 사이의 기간을 반환한다.
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long getDaysBetween( String startDate, String endDate, String format ) throws ParseException{
		if (format == null) 
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date sDate;
		Date eDate;
		long day2day = 0;
		sDate = sdf.parse(startDate);
		eDate = sdf.parse(endDate);
		day2day = (eDate.getTime() - sDate.getTime()) / (1000*60*60*24);

		return day2day;
	}



	/**
	 * 특수문자를 치환한다.
	 * @param value
	 * @return
	 */
	public static String escapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&.*")) {
			value = value.replaceAll("&", "&amp;");
		}
		if (value.matches(".*\'.*")) {
			value = value.replaceAll("\'", "&apos;");
		}
		if (value.matches(".*<.*")) {
			value = value.replaceAll("<", "&lt;");
		}
		if (value.matches(".*>.*")) {
			value = value.replaceAll(">", "&gt;");
		}
		if (value.matches(".*\".*")) {
			value = value.replaceAll("\"", "&quot;");
		}
		return value;
	}

	/**
	 * HTML escape 문자열을 역치환한다.
	 * @param value
	 * @return
	 */
	public static String unescapeXml(String value) {
		// & has to be checked and replaced before others
		if (value.matches(".*&amp;.*")) {
			value = value.replaceAll("&amp;", "&");
		}
		if (value.matches(".*&apos;.*")) {
			value = value.replaceAll("&apos;", "\'");
		}
		if (value.matches(".*&lt;.*")) {
			value = value.replaceAll("&lt;", "<");
		}
		if (value.matches(".*&gt;.*")) {
			value = value.replaceAll("&gt;", ">");
		}
		if (value.matches(".*&quot;.*")) {
			value = value.replaceAll("&quot;", "\"");
		}
		return value;
	}

	/**
	 * 입력된 Integer값이 null이면 -1을 반환한다.
	 * @param value
	 * @return Integer
	 */
	public static Integer defaultInteger(Integer value) {
		if(value == null) return -1;
		else return value;
	}

	/**
	 * 입력된 Integer값이 null이면 입력받은 defaultValue를 반환한다.
	 * @param value
	 * @return Integer
	 */
	public static Integer defaultInteger(Integer value, Integer defaultValue) {
		if(value == null) return defaultValue;
		else return value;
	}

	/**
	 * 입력된 Long값이 null이면 -1을 반환한다.
	 * @param value
	 * @return Long
	 */
	public static Long defaultLong(Long value) {
		if(value == null) return -1L;
		else return value;
	}

	/**
	 * 입력된 Long값이 null이면입력받은 defaultValue를 반환한다.
	 * @param value
	 * @return Long
	 */
	public static Long defaultLong(Long value, Long defaultValue) {
		if(value == null) return defaultValue;
		else return value;
	}

	/**
	 * DTO 객체에 담긴 데이타를 복제한다.
	 * @param target 복사될 객체
	 * @param orgin 원본객체
	 * @throws Exception
	 */
	public static void cloneObj(Object target, Object orgin) 
			throws Exception {
		try {
			BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
			beanUtilsBean.getConvertUtils().register(
					new SqlTimestampConverter(null), Timestamp.class); 
			beanUtilsBean.getConvertUtils().register(
					new SqlDateConverter(null), Date.class); 
			beanUtilsBean.getConvertUtils().register(
					new SqlTimeConverter(null), Time.class);
			beanUtilsBean.getConvertUtils().register(
					new LongConverter(0), Long.class);
			beanUtilsBean.getConvertUtils().register(
					new IntegerConverter(0), Integer.class);
			beanUtilsBean.copyProperties(target, orgin);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 기존 파일을 신규파일명으로 변경한다.
	 * @param orgFilePath
	 * @param newFilePath
	 * @throws Exception
	 */
	public static void fileRename(String orgFilePath, String newFilePath) throws Exception {
		try{
			File f1 = new File(orgFilePath);
			if(!f1.isFile()) {
				throw new Exception("변경할 파일명이 존재하지 않습니다. - "+f1.getAbsolutePath());
			}
			File f2 = new File(newFilePath);

			f1.renameTo(f2);
		}catch(Exception e){
			if(e instanceof FileNotFoundException){
				throw e;
			}else{
				throw new Exception("File Rename Error", e);
			}
		}
	}

	public static boolean sendSocketXml(String xml, String ip, int port) {

		boolean retValue = true;

		if(StringUtils.isNotBlank(xml)) {

			Socket _sock = null;
			DataOutputStream _dos = null;
			try {
				byte[] xmlByte = xml.getBytes("UTF-8");
				byte[] _little = CharFormatConverter.toLittleEndian(xmlByte.length);

				_sock = new Socket(ip, port);
				if(logger.isDebugEnabled()) {
					logger.debug("Socket Connected - ip: "+ip+", port: "+port);
				}
				_dos = new DataOutputStream(_sock.getOutputStream());

				_dos.write(_little);
				_dos.flush();
				_dos.write(xmlByte);
				_dos.flush();
				if(logger.isDebugEnabled()) {
					logger.debug("Socket Transfed - xml: "+xml);
				}
			} catch (Exception e) {
				logger.error("Socket Connect Error - ip: "+ip+", port: "+port, e);
			} finally {
				try {
					if(_dos != null) {
						_dos.close();
						_dos = null;
					}
					if(_sock != null) {
						_sock.close();
						_sock = null;
					}
					if(logger.isDebugEnabled()) {
						logger.debug("Socket closed - "+_dos);
					}
				} catch (Exception e) {
				}
			}
		} else {
			retValue = false;
		}
		return retValue;
	}

	/**
	 * 타임코드 = duration로
	 * @param param frame
	 * @return  String
	 * @throws DataAccessException
	 */
	public static long changeTimeCode(String timecode) {

		int hh, mm, m10, m1, ss, ff;
		long frame;

		hh = Integer.parseInt(timecode.substring(0, 2));     	// 타임코드의 처음 2자리(시간)를 숫자로 변환
		mm = Integer.parseInt(timecode.substring(3, 5));   		// 타임코드의 : 다음 2자리(분)를 숫자로 변환
		ss = Integer.parseInt(timecode.substring(6, 8));      	// 타임코드의 : 다음 2자리(초)를 숫자로 변환
		ff = Integer.parseInt(timecode.substring(9)); ;     	// 타임코드의 : 다음 2자리(프레임)를 숫자로 변환

		m10 = mm / 10; // mm div 10;
		m1 = mm % 10;
		frame = hh * 107892 + m10 * 17982;

		if (0 == m1)
			frame = frame + ss * 30 + ff;
		else {
			frame = frame + (m1 - 1) * 1798 + 1800;

			if ( 0 == ss)
				frame = frame + ff - 2;
			else
				frame = frame + (ss - 1) * 30 + 28 + ff;
		}

		return frame;
	}

	/**
	 * duration = 타임코드로
	 * @param param frame
	 * @return  String
	 * @throws DataAccessException
	 */
	public static String changeDuration(long frame) {

		long hh, mm, m10, m1, ss, ff;  // 시, 분, 중간값, 중간값, 중간값, 중간값

		hh = frame / 107892; // (30000/1001)*3600
		frame = frame % 107892;
		m10 = frame / 17982; // (30000/1001)*600
		frame = frame % 17982;

		if ( frame < 1800 ) {
			m1 = 0;
			ss = frame / 30;
			ff = frame % 30;
		} else {      // except for each m10, plus 2 frame per m1

			frame = frame - 1800;  // in case of m10
			m1 = frame / 1798 + 1;
			frame = frame % 1798;

			if ( frame < 28 ) { // in case of m1
				ss = 0;
				ff = frame + 2;
			} else {    // in case of none of m10 and m1
				frame = frame - 28;
				ss = frame / 30 + 1;
				ff = frame % 30;
			}
		}
		mm = m10 * 10 + m1;

		return padLeft(String.valueOf(hh), "0", 2)+":"+padLeft(String.valueOf(mm), "0", 2)+":"+padLeft(String.valueOf(ss), "0", 2)+":"+padLeft(String.valueOf(ff), "0", 2);
	}

	/**
	 * <pre>
	 * 요청한 파일 객체를 참조하여 해당 컨텐츠를 삭제처리한다.
	 * </pre>
	 * @param file
	 */
	public static void fileForceDelete(File file) {
		try {
			if(SystemUtils.IS_OS_WINDOWS) {
				//logger.debug("windows file delete!! - "+file.getAbsolutePath());
				//FileUtils.forceDeleteOnExit(file);
			} else {
				Process proc = null;
				if(file.isDirectory()) {
					proc = Runtime.getRuntime().exec("rm -rf "+file.getAbsolutePath());
					proc.waitFor();

					if(proc.exitValue() != 0) {
						logger.error("folder delete error! - ["+file.getAbsolutePath()+"] : ");
						BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream ()));
						while (err.ready())
							logger.error(err.readLine());
						err.close();
					}
				} else {
					if(file.getAbsolutePath().toLowerCase().endsWith(".jpg")) {
						String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
						proc = Runtime.getRuntime().exec("rm -rf "+file.getAbsolutePath());
						proc.waitFor();
						if(proc.exitValue() != 0) {
							logger.error("file delete error! - ["+file.getAbsolutePath()+"] : ");
							BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream ()));
							while (err.ready())
								logger.error(err.readLine());
							err.close();
						}
					} else {
						//logger.debug("unix file delete!! - "+file.getAbsolutePath());
						String filePath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
						for(String ext : DELETE_EXT) {
							//logger.debug("file delete req - "+filePath+"."+ext);
							proc = Runtime.getRuntime().exec("rm -rf "+filePath+"."+ext);
							proc.waitFor();
							if(proc.exitValue() != 0) {
								logger.error("file delete error! - ["+file.getAbsolutePath()+"] : ");
								BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream ()));
								while (err.ready())
									logger.error(err.readLine());
								err.close();
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("delete error", e);
		}
	}

	public static void fileForceDelete(String filePath) {
		try {
			File f = new File(filePath);
			if(f.exists())
				fileForceDelete(f);
			else
				logger.error("file or folder not exist!! - "+filePath);
		} catch (Exception e) {
			logger.error("fileForceDelete error", e);
		}
	}

	/**
	 * <pre>
	 * 파일 객체의 배열을 받아서 삭제처리한다.
	 * </pre>
	 * @param files
	 */
	public static void fileForceDelete(File[] files) {
		try {
			for(File file : files) {
				fileForceDelete(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 값 채우기
	 * 
	 * @param strTarget
	 *            대상 문자열
	 * @param strDest
	 *            채워질 문자열
	 * @param nSize
	 *            총 문자열 길이
	 * @param bLeft
	 *            채워질 문자의 방향이 좌측인지 여부
	 * @return 지정한 길이만큼 채워진 문자열
	 */
	public static String padValue(final String strTarget, final String strDest, final int nSize, final boolean bLeft) {
		if (strTarget == null) {
			return strTarget;
		}

		String retValue = null;
		StringBuffer objSB = new StringBuffer();
		int nLen = strTarget.length();
		int nDiffLen = nSize - nLen;

		for (int i = 0; i < nDiffLen; i++) {
			objSB.append(strDest);
		}

		if (bLeft) { // 채워질 문자열의 방향이 좌측일 경우
			retValue = objSB.toString() + strTarget;
		}
		else { // 채워질 문자열의 방향이 우측일 경우
			retValue = strTarget + objSB.toString();
		}

		return retValue;
	}

	/**
	 * 좌측으로 값 채우기
	 * 
	 * @param strTarget
	 *            대상 문자열
	 * @param strDest
	 *            채워질 문자열
	 * @param nSize
	 *            총 문자열 길이
	 * @return 채워진 문자열
	 */
	public static String padLeft(final String strTarget, final String strDest, final int nSize) {
		return padValue(strTarget, strDest, nSize, true);
	}

	/**
	 * 우측으로 값 채우기
	 * 
	 * @param strTarget
	 *            대상문자열
	 * @param strDest
	 *            채워질 문자열
	 * @param nSize
	 *            총 문자열 길이
	 * @return 채워진 문자열 길이
	 */
	public static String padRight(final String strTarget, final String strDest, final int nSize) {
		return padValue(strTarget, strDest, nSize, false);
	}

	/**
	 * byte array to image
	 * 
	 * @param path      이미지 경로
	 * @param buffer    이미지 데이터
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void byte2Image(String path, byte[] buffer) throws FileNotFoundException, IOException {
		FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
		imageOutput.write(buffer, 0, buffer.length);
		imageOutput.close();
	}

}

package kr.co.d2net.commons.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.stream.FileImageOutputStream;

import org.apache.commons.lang.StringUtils;

public class Utility {

	public static Map<String, Object> configMap = new HashMap<String, Object>();
	static {
		configMap = PropertyLoader.getPropertyHash("config");
	}

	public static void download(String address, String filePath) throws Exception {
		BufferedInputStream in = null;
		FileOutputStream fop = null;
		try {
			in = new BufferedInputStream(new URL(address+filePath).openStream());

			String tmpPath = (String)configMap.get("file.download.tmp");

			File f = new File((tmpPath+filePath).substring(0, (tmpPath+filePath).lastIndexOf("/")));
			if(!f.exists()) f.mkdirs();

			fop = new FileOutputStream((tmpPath+filePath));

			byte data[] = new byte[1024]; 
			int count; 
			while ((count = in.read(data, 0, 1024)) != -1) { 
				fop.write(data, 0, count); 
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try { 
				if (fop != null) { 
					fop.close(); 
				} 
				if (in != null) { 
					in.close(); 
				}
			} catch (Exception ignored) {} 
		}
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
	 * 입력한 숫자를 빼거나 더한 날짜를 반환한다.
	 * @param iDay
	 * @return String
	 */
	public static String getDate ( int iDay ) {
		Calendar temp=Calendar.getInstance ( );
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
	
	public static boolean isNumeric(String s) {
		java.util.regex.Pattern pattern = Pattern.compile("[+-]?\\d+"); 
	    return pattern.matcher(s).matches(); 
	}
}

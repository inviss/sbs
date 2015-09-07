package com.konantech.search.util;

import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;

import com.konantech.search.data.ParameterVO;


/** 공통  유틸. 
 * 
 * @author KONAN Technology
 * @since 2010.01.21
 * @version 1.0
 */
public class CommonUtil {

	/** 입력받은 문자열특수문자를 html format으로 변환.
	*	@param str 변환할 문자열
	*	@return 변환된 문자열
	*/
	public static String formatHtml(String str)
	{
		if (str.length() == 0) {
			return new String("&nbsp;");
		}

		String t = "";
		
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) 
			{
				case '<': t += "&lt;"; break;
				case '>': t += "&gt;"; break;
				case '&': t += "&amp;"; break;
				case '\"': t += "&quot;"; break;
				case '\'':  t += "\\\'"; break;
//				case '\r':
//					t += "<br>\n";
//					if( i<str.length()-1 && str.charAt(i+1)=='\n') i++;
//					break;
//				case '\n': t += "<br>\n"; break;
				default: t += str.charAt(i); break;
			}
		}
		return t;
	}

	/** YYYYMMDD 포멧의 문자열을 입력받아 정의한 구분자를 사용하여 YYYY.MM.DD 포멧으로 변환.
	*	@param str 변환할 문자열
	*	@param deli 구분자	
	*	@return 변환된 문자열
	*/
	public static String formatDateStr(String str, String deli)
	{
		StringBuffer t = new StringBuffer("");
		if (str == null || str.length() < 8) {
			return null; 
		} else {
			t.append(str.substring(0,4)).append(deli)
             .append(str.substring(4,6)).append(deli)
		     .append(str.substring(6,8));
			 
			return t.toString();
		}
	}

	/**
	* 문자열이 긴 경우에 입력받은 문자길이로 자른다.
	*	@param str 변환할 문자열
	*	@param cutLen 스트링 길이
	*	@param tail tail 스트링
	*	@return 변환된 문자열
	*/
	public static String getCutString(String str, int cutLen, String tail) { 
		int counter = 0; 
	
		int strLength = str.getBytes().length;
		
		if (strLength <= cutLen) {
			return str; 
		}
		
		byte[] bytes = str.getBytes(); 
		
		for (int i = cutLen - 1; i >= 0; i--) { 
			if (((int) bytes[i] & 0x80) != 0) {
				counter++;	
			}
		} 
		
		return (new String(bytes, 0, cutLen - (counter % 2)) + tail); 
	}
	
	
	/** 널이거나 빈 문자열을 원하는 스트링으로 변환한다<br>
	* 단, 좌우 공백이 있는 문자열은 trim 한다 <br>.
	*
	* @param org 입력 문자열
	* @param converted 변환 문자열
	* @return 치환된 문자열
	*/
	public static String null2Str(String org, String converted) {
		if (org == null || org.trim().length() == 0) {
			return converted;
		} else {
			return org.trim();
		}
	}
	
	/** 널이거나 빈 문자열(숫자형)을 integer로 변환한다.
	*
	* @param org 입력문자열
	* @param converted 변환숫자
	* @return 치환된 Interger
	*/
	public static int null2Int(String org, int converted) {
		int i = 0;
		
		if (org == null || org.trim().length() == 0) {
			return converted;
		} else {
			try {
				i = Integer.parseInt(org); 
			} catch (Exception ex) { 
				i = converted;
			}
			return i;
		}
	}
	
	/** 스트링 숫자열의 포맷을  ###,### 으로 변환하여 리턴함.
	* @param str 숫자문자열
	* @return 변환된 문자열
	*/
	public static String formatMoney(String str){
		String result = "0";
		try {
			double iAmount = (new Double(str)).doubleValue();
			java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,###,###,###,###,###");
			return df.format(iAmount);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/** Int형 숫자의 포맷을  ###,### 으로 변환하여 리턴함.
	* @param num 정수값
	* @return 변환된 문자열
	*/
	public static String formatMoney(int num) {
		String result = "0";
		try {
			double iAmount = (new Double(num)).doubleValue();
			java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,###,###,###,###,###");
			
			return df.format(iAmount);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

    /** 문자열의 인코딩을 변환하여 리턴함.
     * 
     * @param str : 인코딩 변환할 스트링
     * @param myEnc : 현재 인코딩
     * @param targetEnc : 타겟 인코딩
     * @return 변환된 문자열 
     */
    public static String changeEncode(String str, String myEnc, String targetEnc)
    {
        if (str == null || str.trim().equals("")) {
        	return str;
        }
        
        try {
            return new String(str.getBytes(myEnc), targetEnc);
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
    }
    
    /** 현재 날짜로부터 특정 기간 전, 후 날짜 구하여 반환함. 
     * 
     * @param iDay 현재로부터 구하고자 하는 날짜 수 int (과거 : 음수, 미래 : 양수)
     * @return date 값 문자열 
     */
    public static String getTargetDate(int iDay)
	{
		Calendar temp = Calendar.getInstance();
		StringBuffer sbDate = new StringBuffer();

		temp.add(Calendar.DAY_OF_MONTH, iDay);

		int nYear = temp.get(Calendar.YEAR);
		int nMonth = temp.get(Calendar.MONTH) + 1;
		int nDay = temp.get(Calendar.DAY_OF_MONTH);

		sbDate.append(nYear);
		if (nMonth < 10) {
			sbDate.append("0");
			sbDate.append(nMonth);
		}
		
		if (nDay < 10) {
			sbDate.append("0");
			sbDate.append(nDay);
		}

		return sbDate.toString();
	}
    
    /** 이전검색어 히든 태그 생성 후 반환.
     * 
     * @param srchParam ParameterVO 오브젝트
     * @return 이전 검색어 태그 문자열
     */
    public static StringBuffer makeHtmlForPreKwd(ParameterVO srchParam)
    {
    	StringBuffer preKwdStr = new StringBuffer("");
    	int tmpCnt = 0;

    	preKwdStr.append("<input type='hidden' name=\"preKwd\" value=\"" + srchParam.getKwd() + "\">\n");
    	if (srchParam.getReSrchFlag()) {
    		if ( srchParam.getPreKwds() != null ) {
    			int preKwdCnt = srchParam.getPreKwds().length;
    			
    			tmpCnt = 0;
    			if (srchParam.getPreKwds()[0].equals(srchParam.getKwd()) && preKwdCnt > 1 ) {
    				tmpCnt = 1; 
    			}
    			
    			for ( ; tmpCnt < preKwdCnt; tmpCnt++) {
    				preKwdStr.append( "<input type=\"hidden\" name=\"preKwd\" value=\"").append(srchParam.getPreKwds()[tmpCnt]).append("\">\n");
    			}

    			/* 이전검색어 & 키워드가 존재하는 경우 / 첫페이지내 검색시만 생성 / 2개 키워드가 같지 않은경우*/
    			if ( srchParam.getKwd().length() > 0 && srchParam.getPageNum() == 1
    				&& !srchParam.getKwd().equals(srchParam.getPreKwds()[0]) ) {
    				srchParam.setRecKwd(srchParam.getPreKwds()[0] + "||" + srchParam.getKwd());    // 추천검색어 구성용 단어 생성
    			}
    		} // end of if
    	} // end of if  
    	
    	return preKwdStr;
    }
    
    /** target값과 비교값이 같을 경우 특정 값을 리턴.
	*	@param target 대상값
	*	@param str 비교값
	*	@param returnVal 반환할 값 지정
	*	@return returnVal
	*/
    public static String makeReturnValue(String target, String str, String returnVal) 
    {
    	if (target.equals(str)) {
    		return returnVal;
    	} else {
    		return "";
    	}
    }
    
    /** target값과 비교값이 같을 경우 특정 값을 리턴.
	*	@param target 대상값
	*	@param str 비교값
	*	@param trueVal target 값과 비교값이 동일할 경우 리턴값 
	*   @param falseVal target 값과 비교값이 동일하지 않을 경우 리턴값
	*	@return trueVal or falseVal 값
	*/
    public static String makeReturnValue(String target, String str, String trueVal, String falseVal)
    {
    	if (target.equalsIgnoreCase(str)) {
            return trueVal;
        } else {
            return falseVal;
        }
    }

    /** 첨부파일명에 따른  이미지 파일명을 리턴함. 
     * @param fileName 파일명 
     * @return 이미지 파일명
     */
    public static String getAttachFileImage(String fileName) {
    	
    	String fileExt = "";
    	String imgFile = "";
    	
    	//파일 확장자명 추출
    	fileExt = fileName.substring(fileName.indexOf(".")+1);
    	
    	if ("doc".equalsIgnoreCase(fileExt) || "docx".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_doc.gif";
    	} else if ("ppt".equalsIgnoreCase(fileExt) || "pptx".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_ppt.gif";
    	} else if ("xls".equalsIgnoreCase(fileExt) || "xlsx".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_xls.gif";
    	} else if ("hwp".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_hwp.gif";
    	} else if ("zip".equalsIgnoreCase(fileExt) || "gzip".equalsIgnoreCase(fileExt) 
    			|| "tar".equalsIgnoreCase(fileExt) || "azip".equalsIgnoreCase(fileExt) 
    			|| "bzip".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_zip.gif";
    	} else if ("pdf".equalsIgnoreCase(fileExt)) {
    		imgFile = "ico_pdf.gif";
    	} else {
    		imgFile = "ico_etc.gif";
    	}
    	
    	return imgFile;
    }
    
    /*
    public static Cookie getCookie(HttpServletRequest request, String name)
    {
    	Cookie[] cookies = request.getCookies();
    	Cookie returnCookie = null;
    	  
    	if (cookies != null) {
    		for(int i=0;i < cookies.length; i++){
    			if(cookies[i].getName().equals(name)){
    				returnCookie = cookies[i];
    				break;
    			}
    		}
    	}
    	return returnCookie;
    }
	*/
    
    /** JDBC로 각 DB 별 connection.
     * 
     * @param dbName DB 이름(ex. oracle, mssql, mysql.. etc.)
     * @param dbURL 
     * @param id
     * @param passwd
     * @param query
     */
    public static void selectFromDB(String dbName, String dbURL, String id, String passwd, String query) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rSet = null;
       
        String classForName = "";
        
        /* 
         * dbURL sample
         * Oracle 
              ex : jdbc:oracle:thin:@127.0.0.1:1521:ORA9I
         * MySQL 
              ex : jdbc:mysql://localhost:3306/mydb
         * MS SQL Server
              ex : jdbc:microsoft:sqlserver://localhost:1433
         */
        
        if ("oracle".equalsIgnoreCase(dbName)) {
        	classForName = "oracle.jdbc.driver.OracleDriver";
        } else if ("mssql".equalsIgnoreCase(dbName)) {
        	classForName = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
        } else if ("mysql".equalsIgnoreCase(dbName)) {
        	classForName = "com.mysql.jdbc.Driver";
        } 
        
    	try {
    		Class.forName(classForName).newInstance();
    		conn = DriverManager.getConnection(dbURL, id, passwd);

    		if (!conn.isClosed()) {
    			/*
    			 * For Debug
    			 */
    			System.out.println("connection Successful");
    		}
    		
    		stmt = conn.createStatement();
    		rSet = stmt.executeQuery(query);
    		while (rSet.next()) {
    			//fetch code here 
    			//rSet.getString(0);
    			//rSet.getInt(0);
    		}
    	} catch (Exception exception) {
    		System.err.println("Exception: " + exception.getMessage());
    	} finally {
    		try {
    			if (conn != null) {
    				conn.close();	
    			}
    		} catch (SQLException sException) {
    			System.err.println("SQLException : " + sException.getMessage());
    		}
    	}
    }
}


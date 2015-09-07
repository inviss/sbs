package com.konantech.search.util;
import java.util.ResourceBundle;
/** 검색 관련 공통 유틸.
 * @author KONAN Technology
 * @since 2010.01.21
 * @version 1.0
 */

public class DCUtil {
	private static ResourceBundle bundle = ResourceBundle.getBundle("search");
	/**검색어에 대한 escape 처리.
	* @param kwd 검색어 
	* @return escape된 검색어
	**/
	public static String escapeQuery(String kwd) {
		String str = "";
		char ch;
	        for (int i = 0; i < kwd.length(); i++) {
			ch = kwd.charAt(i);
			switch (ch) {
				case '\"':
					str = str + "\\\"";
					break;
				case '\'':
					str = str + "\\'";
					break;
				case '\\':
					str = str + "\\\\";
					break;
				case '?':
					str = str + "\\?";
					break;         
				case '*':
					str = str + "\\*";
					break;    
				default:
					str = str + ch;
					break;
			}
		}

		return str;
	}

	/** 검색엔진 로그정보 로그포맷. 
	* <br>[클래스+사용자ID$|첫검색|페이지번호|정렬방법^키워드]##이전검색어|현재검색어] 
	* @param site 사이트명
	* @param nmSchCat 카테고리명
	* @param userId 사용자ID
	* @param kwd 키워드
	* @param pageNum 페이지번호
	* @param reSchFlag 재검색여부(true/false)
	* @param orderNm 정렬방법
	* @param recKwd 추천검색어('이전검색어|현재검색어')
	* 
	* @return 검색 로그 String
	*/
	public static String getLogInfo(String site, String nmSchCat, String userId, String kwd, 
					int pageNum, boolean reSchFlag, String orderNm, String recKwd) {
		StringBuffer logInfo = new StringBuffer("");
		
		logInfo.append(site);
		logInfo.append("@");
		
		logInfo.append(nmSchCat);
		logInfo.append("+");

		// 페이지 이동은 검색으로 간주하지 않음
		if (pageNum > 1) {
			logInfo.append("$||");
			logInfo.append(pageNum);
			logInfo.append("|");
		
		} else {
			logInfo.append(userId);
			logInfo.append("$|");

			if (reSchFlag) {
				logInfo.append("재검색|");
			} else {
				logInfo.append("첫검색|");
			}
			logInfo.append(pageNum);
			logInfo.append("|");
		}

		logInfo.append(orderNm);
		logInfo.append("^");
		logInfo.append(kwd);
		
		// 추천어로그
		if (recKwd != null && recKwd.length() > 0) {
			logInfo.append("]##").append(recKwd);
		}
		return logInfo.toString();
	}
    
	/** 키워드/코드형식쿼리 생성. 
     *
     * @param nmFd 검색대상 필드명 또는 인덱스명
     * @param kwd 검색어
     * @param schMethod 검색메소드
     * @param query 쿼리 String 
     * @param logicOp 연결 논리연산자 (ex : and, or, and not) 
     *
     * @return 쿼리 StringBuffer
     */
     public static StringBuffer makeQuery(String nmFd, String kwd, String schMethod, 
    		                              StringBuffer query, String logicOp) {

         StringBuffer tempQuery = new StringBuffer("");
         String compare = bundle.getString("textfield");
         String[] compas = compare.split(",");
         if (query != null && query.length() > 0) {
        	 //tempQuery.append("(" + query + ")");
        	 tempQuery.append(query);
         }

         if (kwd != null && kwd.length() > 0) {
             if (tempQuery.length() > 0) {
            	 if ("".equalsIgnoreCase(logicOp)) {
            		 tempQuery.append(" and ");
            	 } else {
            		 tempQuery.append(" " + logicOp + " ");
            	 }	 
             }
             if(kwd.equals("NON")){
            	 tempQuery.append(nmFd);
                 tempQuery.append("!='0");
                 //tempQuery.append(escapeQuery(kwd));
                 tempQuery.append("' ");
             }else if(kwd.endsWith("NOT")){
             tempQuery.append(nmFd);
             tempQuery.append("<>'");
             tempQuery.append("M");
             tempQuery.append("' ");
             }else{
                 tempQuery.append(nmFd);
                 tempQuery.append("='");
                 tempQuery.append(escapeQuery(kwd));
                 tempQuery.append("' ");
                 }
             //	System.out.println("compare  " + compare);
             for(int i =0; i<compas.length;i++){
            	 if(compas[i].equals(nmFd)){
            	 tempQuery.append(" allword synonym");
            	 }
     		}
             compare.compareTo(nmFd);
             tempQuery.append(schMethod);
         }
         return tempQuery;
     }
     
     /** 키워드/코드형식쿼리 생성. 
     *
     * @param nmFd 검색대상 필드명 또는 인덱스명
     * @param kwd 검색어
     * @param schMethod 검색메소드
     * @param query 쿼리 String 
     * @param logicOp 연결 논리연산자 (ex : and, or, and not) 
     * @param Bracket 소괄호 ( ex :')' )
     * @return 쿼리 StringBuffer
     */
     public static StringBuffer makeQueryBracket(String nmFd, String kwd, String schMethod, 
             StringBuffer query, String logicOp,String PreBracket,String PostBracket) {
		
		StringBuffer tempQuery = new StringBuffer("");
		
		if (query != null && query.length() > 0) {
		//tempQuery.append("(" + query + ")");
			tempQuery.append(query);
		}
		
		 if (kwd != null && kwd.length() > 0) {
             if (tempQuery.length() > 0) {
            	 if ("".equalsIgnoreCase(logicOp)) {
            		 tempQuery.append(" and ");
            	 } else {
            		 tempQuery.append(" " + logicOp + " ");
            	 }	 
             }
             tempQuery.append(PreBracket);
             tempQuery.append(nmFd);
             tempQuery.append("='");
             tempQuery.append(escapeQuery(kwd));
             tempQuery.append("' ");
             tempQuery.append(schMethod);
             tempQuery.append(PostBracket);
         }
		return tempQuery;
		}
     
	/** 확장형 쿼리 생성.
	*
	* @param nmFd 검색대상 필드명 또는 인덱스명
	* @param op 연산자 (ex : =, >, <) 
	* @param kwd 키워드
	* @param query 쿼리 StringBuffer
	* @param logicOp 논리연산자 (ex : and, or, and not)
	* @param isText 형태소 검색 여부 (default y)
	* @param srchMethod 검색 메소드 
	*
	* @return 검색쿼리 StringBuffer
	*/

	public static StringBuffer makeExpressionQuery(String nmFd, String op, String kwd, boolean isText, 
												String srchMethod, String logicOp, StringBuffer query) {

		StringBuffer tempQuery = new StringBuffer("");

		if (query != null && query.length() > 0) {
			// 이전쿼리가 값이 있을 경우 괄호로 묶어준다
			tempQuery.append("(" + query + ")"); 		
			// 이전쿼리의 논리연산자 append (ex : and, or, and not)
			tempQuery.append(" " + logicOp + " ");		
		}
		
		// 생성될 쿼리의 시작 괄호 
		if (query != null && query.length() > 0) {
			tempQuery.append("(");
		}
		
		// 검색 대상 필드 append
		tempQuery.append(nmFd);
		
		// 연산자 append (ex : =, >, <)
		tempQuery.append(op);
		
		// 형태소 검색 여부 판단 
		if (isText) {
			tempQuery.append("'" + kwd + "' " + srchMethod);
		} else {
			tempQuery.append("'" + kwd + "' ");	
		}
		
		// 생성될 쿼리의 끝 괄호 
		if (query != null && query.length() > 0) {
			tempQuery.append(")");						
		}
		
		return tempQuery;
	}
	
  	/** Like 쿼리 생성.
  	 * 
  	 * @param nmFd 검색대상 필드명 또는 인덱스명
  	 * @param kwd 키워드
  	 * @param query 이전 생성 쿼리
  	 * @param option 좌절단 : left, 우절단 : right, 좌우절단 : all
  	 * 
  	 * @return 검색쿼리 StringBuffer
  	 */
  	public static StringBuffer makeLikeQuery(String nmFd, String kwd, String option, 
  			 							StringBuffer query) {
  		StringBuffer tempQuery = new StringBuffer("");
  
  		if (query != null && query.length() > 0) {
  			tempQuery.append("(" + query + ")");
  		}
  		
  		if (kwd != null && kwd.length() > 0) {
  			if (tempQuery.length() > 0) {
  				tempQuery.append(" AND ");
  			}
  			tempQuery.append(nmFd + " like '");
  			
  			if ("all".equals(option) || "left".equals(option)) {
  				tempQuery.append("*");
  			} 
  			
  			tempQuery.append(escapeQuery(kwd));
  			
  			if ("all".equals(option) || "right".equals(option)) {
  	  			tempQuery.append("*");
  			}
  			tempQuery.append("'");
  			
  		}
  		return tempQuery;
  	}

    /** IN쿼리 생성.
     *
     * @param nmFd 검색대상 필드명 또는 인덱스명
     * @param code 조회 대상 코드 값
     * @param isNumber code 값이 숫자값인지 여부 (true : 숫자, false : 문자)
     * @param query 이전생성 쿼리
     * @return 검색쿼리 StringBuffer
     */
    public static StringBuffer makeINQuery(String nmFd, String code[], boolean isNumber, 
    										StringBuffer query) { 

        StringBuffer tempQuery = new StringBuffer("");
        StringBuffer inQuery = new StringBuffer("");

        if (query != null && query.length() > 0) {
            tempQuery.append(" (" + query + ") ");
        }

        if ((code != null) && (code.length > 0)) {

            boolean check = true;
            for (int i = 0; i < code.length; i++) {
                if (!"".equals(code[i])) {
                    check = false;
                    break;
                }
            }

            if (check) {
                return tempQuery;
            }

            if (tempQuery.length() > 0) {
                tempQuery.append(" AND ");
            }

            tempQuery.append(nmFd);
            tempQuery.append(" IN {");
            for (int i = 0; i < code.length; i++) {
                if (code[i] != null && code[i].length() > 0) {
                    if (inQuery.length() > 0) {
                        inQuery.append(", ");
                    }
                    if (isNumber) { 
                    	inQuery.append(code[i]);
                    } else {
                    	inQuery.append("'" + code[i] + "'"); 
                    }
                }
            }

            tempQuery.append(inQuery.toString());
            tempQuery.append("}");
        }
        return tempQuery;
    }

	
	/** 구간검색 쿼리 생성.
	* 
	* @param nmFd 검색대상 필드명 또는 인덱스명
	* @param startVal 시작값
	* @param endVal 종료값 
	* @param query 이전 생성 쿼리
	* @return 검색 쿼리 StringBuffer
	*/
    public static StringBuffer makeRangeQuery( String nmFd, String startVal, String endVal, 
			StringBuffer query) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if ( query != null && query.length() > 0 ){
    		tempQuery.append( "(" + query +")");
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append(nmFd);
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append(nmFd);
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}

    	tempQuery.append(")");

    	return tempQuery;
    }
    public static StringBuffer makeRangeQuerys( String nmFd, String startVal, String endVal, 
			StringBuffer query,String logicOp) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if ( query != null && query.length() > 0 ){
    		tempQuery.append( "(" + query +")");
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append(nmFd);
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       
       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append(nmFd);
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	tempQuery.append(")");
    	 
             
            		 tempQuery.append(" " + logicOp + " ");
            
    	
    	return tempQuery;
    }
    public static StringBuffer makeRangeQueryFm_dt( String nmFd, String startVal, String endVal, 
			StringBuffer query) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if ( query != null && query.length() > 0 ){
    		tempQuery.append( "(" + query +")");
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append(nmFd);
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       
       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append(nmFd);
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	
    	
    	tempQuery.append(")");
    	 
//            		 tempQuery.append(" " + logicOp + " ");
    	
    	return tempQuery;
    }
    
    /**
     * 방송일/촬영일은 동시에 조회조건으로 쿼리를 생성한다.
     * @param startVal
     * @param endVal
     * @param query
     * @return
     */
    public static StringBuffer makeRangeQueryBrd_ddFm_dt( String startVal, String endVal,StringBuffer query) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if ( query != null && query.length() > 0 ){
    		tempQuery.append( "(" + query +")");
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append("BRD_DD");
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       
       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append("BRD_DD");
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	if(!startVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" OR ");
    		}
    		tempQuery.append("FM_DT");
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}     
    	
    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append("FM_DT");
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	
   
    	tempQuery.append(")");
    	 
    	
    	return tempQuery;
    }
    
    
    
    

    /**
     * 방송일/촬영일은 동시에 조회조건으로 쿼리를 생성한다.
     * @param startVal
     * @param endVal
     * @param query
     * @return
     */
    public static StringBuffer makeRangeQueryBrd_ddFm_dtForIfCms( String startVal, String endVal,StringBuffer query) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if ( query != null && query.length() > 0 ){
    		tempQuery.append( "(" + query +")");
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append("BRD_DD");
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       
       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append("BRD_DD");
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	if(!startVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" OR ");
    		}
    		tempQuery.append("FM_DT");
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}     
    	
    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append("FM_DT");
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	
    	if(!startVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" OR ");
    		}
    		tempQuery.append("REG_DT");
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}     
    	
    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append("REG_DT");
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	tempQuery.append(")");
    	 
    	
    	return tempQuery;
    }
    
    
    
    
    
    public static StringBuffer makeRangeQueryBrd_dd( String nmFd, String startVal, String endVal, 
			StringBuffer query, int cnt) {

    	StringBuffer tempQuery = new StringBuffer("");

    	if ("".equals(startVal) && "".equals(endVal) ){
    		return query;
    	}

    	if(cnt==0){
    		tempQuery.append(query);
    		tempQuery.append(" AND (  ");		
    	}else if ( query != null && query.length() > 0 ){
    		tempQuery.append(query);
    		tempQuery.append(" AND ");			
    	}

    	tempQuery.append("(");

    	if(!startVal.equals("")){		
    		tempQuery.append(nmFd);
    		tempQuery.append(" >= '");
    		tempQuery.append(startVal);
    		tempQuery.append("'");
    	}       
       

    	if(!endVal.equals("")){	
    		if(!startVal.equals("")){
    			tempQuery.append(" AND ");
    		}
    		tempQuery.append(nmFd);
    		tempQuery.append(" <= '");
    		tempQuery.append(endVal);
    		tempQuery.append("'");
    	}
    	
    	tempQuery.append(")");
    	 
//            		 tempQuery.append(" " + logicOp + " ");
    	
    	return tempQuery;
    }
	/** 재검색 쿼리 생성. 
	* 
	* @param nmFd 검색대상 필드명 또는 인덱스명
	* @param kwd 키워드
	* @param prevKwd 이전 키워드 배열
	* @param prevKwdLength 이전 키워드 배열 길이
	* @param schMethod 검색 메소드
	* 
	* @return 검색 쿼리 StringBuffer
	*/
	public static StringBuffer makePreQuery(String nmFd, String kwd, String[] prevKwd, 
											int prevKwdLength, StringBuffer query,String schMethod) {
		//StringBuffer query = new StringBuffer("");

		if (prevKwd != null && prevKwdLength > 0) {
			for (int i = 0; i < prevKwdLength; i++) {
				if (!escapeQuery(prevKwd[i]).equalsIgnoreCase(kwd)) {
					//for debug 
					//System.out.println("kwd : " + kwd + " | prev : "  + CommonUtil.changeEncode(prevKwd[i], "ISO-8859-1", "UTF-8"));
					if (query.length() > 0) {
						query.append(" AND ");
					}
					query.append(nmFd);
					query.append("= '");
					query.append(escapeQuery(prevKwd[i]));
					//query.append(escapeQuery(CommonUtil.changeEncode(prevKwd[i], "ISO-8859-1", "UTF-8")));
					//query.append(escapeQuery(prevKwd[i]));
					//이전 키워드가 깨질 경우 위의 코드로 대체하도록 한다. 
					query.append(" ' ");
					query.append(schMethod);
				}
			}
			if (query.length() > 0) { 
				query = new StringBuffer("(").append(query).append(")");
			}
		}

		return query;
	}
	
	
	
	
	
	/** 키워드/코드형식쿼리 생성. 
    *
    * @param nmFd 검색대상 필드명 또는 인덱스명
    * @param kwd 검색어
    * @param schMethod 검색메소드
    * @param query 쿼리 String 
    * @param logicOp 연결 논리연산자 (ex : and, or, and not) 
    *
    * @return 쿼리 StringBuffer
    */
    public static StringBuffer makeQueryForAuth(String newquery,StringBuffer query) {

        StringBuffer tempQuery = new StringBuffer("");
      
        if (query != null && query.length() > 0) {
        	 tempQuery.append(query);
				if (tempQuery.length() > 0) {
					tempQuery.append(" AND ");
				}
				tempQuery.append("( ( ");
				tempQuery.append(newquery);
				tempQuery.append(" ) ");
			
        }

        return tempQuery;
    }
}

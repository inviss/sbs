package com.app.das.util;

import java.text.ParseException;

/**
 * 코드의 공통 sub 쿼리를 정의해 놓았다.
 * @author ysk523
 *
 */
public class CodeCommon 
{
	
	/**
	 * 조건에맞는 쿼리를 생성한다(주제영상, 사용등급 제외)
	 * @version 1.0
	 * @see
	 */
	public static String getCodeQueryMake(String groupCode, String columnName)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n	CASE    ");
		buf.append("\n 		WHEN "+columnName+" is null or  "+columnName+" = '' THEN ''  ");
		buf.append("\n 		ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = '"+groupCode+"' and code.SCL_CD = "+columnName+"  and code.GUBUN='L' ) ");
		buf.append("\n 	END	");		
		return buf.toString();
	}
	
	/**
	 * 조건에맞는 쿼리를 생성한다(주제영상, 사용등급)
	 * @version 1.0
	 * @see
	 */
	public static String getCodeQueryMake2(String groupCode, String columnName)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n	CASE    ");
		buf.append("\n 		WHEN "+columnName+" is null or  "+columnName+" = '' THEN ''  ");
		buf.append("\n 		ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = '"+groupCode+"' and code.SCL_CD = "+columnName+"   ) ");
		buf.append("\n 	END	");		
		return buf.toString();
	}

	

	
}

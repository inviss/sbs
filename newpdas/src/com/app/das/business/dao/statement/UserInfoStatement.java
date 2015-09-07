package com.app.das.business.dao.statement;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.util.StringUtils;

/**
 * 사용자 조회 및 로그인에 대한 SQL 쿼리가 정의되어 있다.<1.0 소스가 대부분으로 사용되지 않는 소스이다>
 * @author ysk523
 *
 */
public class UserInfoStatement 
{	/**
	 * 특정 정직원의 정보를 조회한다.
	 */
	public static String selectEmployeeRoleQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select  ");                                                        												
		buf.append("\n 	user.USER_NO,  	");                                         											
		buf.append("\n  	user.DEPT_NM,  	");                                         								
		buf.append("\n  	user.USER_ID,  	");                                         											
		buf.append("\n  	user.USER_NM,  	");                                         											
		buf.append("\n  	(select auth.ROLE from DAS.REGULAR_AUTH_TBL auth where user.USER_ID = auth.USER_ID) AS ROLE ");
		buf.append("\n from DAS.ERP_COM_USER_TBL user  	");
		buf.append("\n where user.USER_ID = ?	");		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 정직원 정보를 조회한다.
	 */
	public static String selectEmployeeInfoByUserNoQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select  ");                                                        												
		buf.append("\n 	user.USER_NO,  	");                                         											
		buf.append("\n  	user.DEPT_NM,  	");                                         								
		buf.append("\n  	user.USER_ID,  	");                                         											
		buf.append("\n  	user.USER_NM  	");                                         											
		buf.append("\n from DAS.ERP_COM_USER_TBL user  	");
		buf.append("\n where user.USER_NO = ?	");		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 1.0 소스 -  비직원 로그인 - 사용하지 않음
	 */
	public static String selectNonEmployeeRoleQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select ");
		buf.append("\n 	PER_REG_NO, "); 
		buf.append("\n 	OUT_USER_ID, "); 
		buf.append("\n 	OUT_USER_NM, ");
		buf.append("\n 	decrypt_char(PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, "); 
		buf.append("\n 	PW_LST_CHG, "); 
		buf.append("\n 	PW_ERN, ");
		buf.append("\n 	VLDDT_END, "); 
		buf.append("\n 	ROLE ");
		buf.append("\n from DAS.OUTSIDER_INFO_TBL ");
		buf.append("\n where OUT_USER_ID = ? ");	
		buf.append("\n WITH UR	 ");
//		buf.append("\n 		and PASSWORD = ? ");	
		
		return buf.toString();
	}
	/**
	 * 사용자 목록을 검색한다.(정직원, 외부직원)
	 * @param searchValue 검색하고자 하는 이름 또는 ID
	 * @param searchFlag 조건
	 */
	public static String getAllUserListQuery(String searchValue, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select "); 
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
		
			buf.append("\n 	user.EMP_FLAG,  ");
			buf.append("\n 	user.USER_ID,  ");
			buf.append("\n 	user.USER_NM,  ");
			buf.append("\n 	user.ETC_NM,  ");
			buf.append("\n 	user.PHONE_NO,  ");
			buf.append("\n 	ROW_NUMBER() OVER(order by user.USER_NM) AS rownum ");
		}
		buf.append("\n from ( ");
		buf.append("\n 	select  ");
		buf.append("\n 		'1' AS EMP_FLAG, ");
		buf.append("\n 		USER_ID AS USER_ID, ");
		buf.append("\n 		USER_NM AS USER_NM, ");
		buf.append("\n 		DEPT_NM AS ETC_NM, ");
		buf.append("\n 		HAND_PHON AS PHONE_NO ");
		buf.append("\n 	from DAS.ERP_COM_USER_TBL ");
		buf.append("\n 	union all ");
		buf.append("\n 	select  ");
		buf.append("\n 		'2' AS EMP_FLAG, ");
		buf.append("\n 		usr.OUT_USER_ID AS USER_ID, ");
		buf.append("\n 		usr.OUT_USER_NM AS USER_NM, ");
		buf.append("\n 		pgm.PGM_NM AS ETC_NM, ");
		buf.append("\n 		agn.CNTC_PLC_OUTS AS PHONE_NO ");
		buf.append("\n 	from DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n 	  	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");
		buf.append("\n 	  	LEFT OUTER JOIN DAS.ERP_AGNTMST_TBL agn ON usr.PER_REG_NO = agn.PER_REG_NO ");
		buf.append("\n 	where value(agn.PD_END_DD, '') = value((select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = usr.PER_REG_NO), '') ");
		buf.append("\n ) user ");
		if(!StringUtils.isEmpty(searchValue))
		{
			buf.append("\n where user.USER_ID = '"+searchValue+"' ");
			buf.append("\n 	or user.USER_NM like '%"+searchValue+"%' ");
		}
		
		return buf.toString();
	}
	/**
	 * 정직원 사용자 목록을 검색한다.
	 * @param searchValue 검색하고자 하는 이름 또는 ID
	 * @param searchFlag 조건
	 */
	public static String getRegularUserListQuery(String searchValue, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	USER_ID, ");
			buf.append("\n 	USER_NM,  ");
			buf.append("\n 	TITLE,  ");
			buf.append("\n 	JOB,  ");
			buf.append("\n 	HAND_PHON, "); 
			buf.append("\n 	E_MAIL, ");
			buf.append("\n 	SEG_NM, ");
			buf.append("\n 	DEPT_NM, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by USER_NM) AS rownum ");
		}
		buf.append("\n from DAS.ERP_COM_USER_TBL ");
		if(!StringUtils.isEmpty(searchValue))
		{
			buf.append("\n where USER_ID = '"+searchValue+"' or USER_NM like '%"+searchValue+"%' ");
		}
		return buf.toString();
	}
	/**
	 * 로그인서비스(정확한 사용자인지 검색)
	 */
	public static String selectEmployeeRoleLoginQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select  ");                                                        												
		buf.append("\n 	user.USER_NO,  	");                                         											
		buf.append("\n  	user.DEPT_NM,  	");                                         								
		buf.append("\n  	user.USER_ID,  	");                                         											
		buf.append("\n  	user.USER_NM,  	");    
		buf.append("\n  	decrypt_char(user.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD,  	");    
		buf.append("\n  	(select auth.ROLE from DAS.REGULAR_AUTH_TBL auth where user.USER_ID = auth.USER_ID) AS ROLE ");
		buf.append("\n from DAS.ERP_COM_USER_TBL user  	");
		buf.append("\n where user.USER_ID = ?	");		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 정직원 로그인
	 */
	public static String selectEmployeeLoginQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select  ");                                                        												
		buf.append("\n  	user.USER_ID  	");                                         											
		buf.append("\n from DAS.ERP_COM_USER_TBL user  	");
		buf.append("\n where user.USER_ID = ?	");		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 화면 사용 권한이 있는지 확인한다
	 */
	public static String selectAuthByRoleQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	count(1) ");
		buf.append("\n from DAS.ROLE_TBL role, DAS.CODE_TBL code ");
		buf.append("\n where  role.ROLE_CD = ?  ");
		buf.append("\n 	and code.CLF_CD = 'A034' ");
		buf.append("\n 	and code.SCL_CD = role.AUTH_CD ");		
		buf.append("\n 	and role.OK_YN = 'Y' ");
		buf.append("\n 	and role.AUTH_CD = ? ");
		buf.append("\n WITH UR	 ");		
				
		return buf.toString();
	}


}

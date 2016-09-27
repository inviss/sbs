package com.app.das.business.dao.statement;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.ErpAppointDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.business.transfer.SubsidiaryinfoDO;
import com.app.das.util.CodeCommon;

/**
 * 사용자 관리(내부, 외부, 역할)에 대한 SQL 쿼리가 정의되어 있다.
 * @author ysk523
 *
 */
public class UserRoleStatement 
{
	
	/*public static String selectEmployeeRoleListQuery(EmployeeRoleConditionDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 		user.USER_NO,                                           											\n");
			buf.append("\n 		user.DEPT_CD,                                           								\n");
			buf.append("\n		user.DEPT_NM AS DEP_NAME, 															\n");
			buf.append(" \n		user.USER_ID,                                           											\n");
			buf.append("\n 		user.USER_NM,                                           											\n");
			buf.append("\n 		auth.ROLE,                                      														\n");
			buf.append("\n 		ROW_NUMBER() OVER(ORDER BY user.USER_NO) AS rownum      		\n");
		}
		buf.append("\n 	from DAS.ERP_COM_USER_TBL user LEFT OUTER JOIN DAS.REGULAR_AUTH_TBL auth ON  user.USER_ID = auth.USER_ID  	\n");
		buf.append("\n 	where user.USER_NO is not null                              						\n");
		if(!StringUtils.isEmpty(condition.getCompanyCode()))
		{
			buf.append("\n 		and user.CO_CD = '" +condition.getCompanyCode()+ "'  						\n");
		}
		if(!StringUtils.isEmpty(condition.getCenterCode()))
		{
			buf.append("\n 		and user.SEG_CD = '" +condition.getCenterCode()+ "'						 	\n");
		}
		if(!StringUtils.isEmpty(condition.getDepartmentCode()))
		{
			buf.append("\n 		and user.DEPT_CD = '" +condition.getDepartmentCode()+ "'  		\n");
		}
		if(!StringUtils.isEmpty(condition.getRoleCode()))
		{
			buf.append("\n 		and auth.ROLE = '" +condition.getRoleCode() + "'  		\n");
		}
		if(!StringUtils.isEmpty(condition.getSearchType()) && !StringUtils.isEmpty(condition.getSearchText()))
		{
			if(DASBusinessConstants.UserRoleSearchFlag.SEARCH_TYPE_NAME.equals(condition.getSearchType()))
			{
				buf.append("\n 		and user.USER_NM like '%" +condition.getSearchText()+ "%' 					\n");
			}
			else if(DASBusinessConstants.UserRoleSearchFlag.SEARCH_TYPE_ID.equals(condition.getSearchType()))
			{
				buf.append("\n 		and user.USER_ID = '" +condition.getSearchText()+ "'						\n");
			}
		}
		
		return buf.toString();
	}
	
	*/
	
	
	
	
	
	/**
	 * 비직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @param searchFlag
	 *            조건
	 */
	public static String selectOutEmployeeRoleListQuery(NonEmployeeDASRoleDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		
			buf.append("\n 	SBS_USER_ID, ");
			buf.append("\n 	USER_NM, ");
			buf.append("\n 	W_DEPT, ");
			buf.append("\n 	PGM_NM, ");
			buf.append("\n 	VLDDT_BGN, ");
			buf.append("\n 	VLDDT_END, ");
			buf.append("\n 	POSITION, ");
			buf.append("\n 	APPROVE_YN , ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY SEQ DESC) AS rownum ");
		
		buf.append("\n from DAS.USER_INFO_TBL  ");		
		//buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n where SBS_USER_ID is not null ");

		/*if(CodeConstants.IDStatus.CONTINUE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END >= (values(hex(current date))) ");     
		}
		else if(CodeConstants.IDStatus.CLOSE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END < (values(hex(current date))) ");                                
		}
		if(!StringUtils.isEmpty(condition.getSearchText()))
		{
			buf.append("\n 	and (usr.OUT_USER_NM like '%"+condition.getSearchText()+"%' ) ");
		}
		*/
		buf.append("\n AND EMPLOYEE_TYPE = '003' ");
		//buf.append("\n AND W_DEPT = ? ");
		
		
		
		return buf.toString();
	}
	/**
	 * 이미 테이블에 존재하는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 */
	public static String selectOutEmployeeRoleQuery(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	usr.PER_REG_NO, ");
		buf.append("\n 	usr.VLDDT_BGN, ");
		buf.append("\n 	usr.VLDDT_END, ");
		buf.append("\n 	usr.OUT_USER_NM, ");
		buf.append("\n 	pgm.PGM_NM, ");
		buf.append("\n 	usr.OUT_USER_ID, ");
		buf.append("\n 	agn.DEPT, ");
		buf.append("\n 	decrypt_char(usr.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, ");
		buf.append("\n 	usr.ROLE, ");
		buf.append("\n 	agn.CNTC_PLC_OUTS, ");
		buf.append("\n 	agn.CNTC_PLC_TEL_NO, ");
		buf.append("\n 	usr.GAURANTOR_ID, ");
		buf.append("\n 	usr.PGM_ID, ");
		buf.append("\n 	usr.PW_LST_CHG, ");
		buf.append("\n 	usr.MOD_DT ");
		buf.append("\n from DAS.OUTSIDER_INFO_TBL usr "); 
		buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n 	LEFT OUTER JOIN (select "); 
		buf.append("\n 		PER_REG_NO, DEPT, CNTC_PLC_OUTS, CNTC_PLC_TEL_NO "); 
		buf.append("\n 	from DAS.ERP_AGNTMST_TBL ");
		buf.append("\n 	where PER_REG_NO = ? "); 
		buf.append("\n 		and PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = ?) ");
		buf.append("\n 	) agn ON usr.PER_REG_NO = LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) ");
		buf.append("\n where usr.OUT_USER_ID is not null ");
//		//MHCHOI
		//주민번호가 없는 사용자 이면 사용자 ID를 가지고 정보 조회
        if(perRegNo.equals(DASBusinessConstants.PER_REG_NO))
        	buf.append("\n 	and usr.OUT_USER_ID = ?	 ");
		else		    
        	buf.append("\n 	and usr.PER_REG_NO = ?	 ");
		
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}
	
	/**
	 * 이미 테이블에 존재하는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 * @param userID
	 *            사용자 정보
	 */
	public static String selectOutEmployeeRoleQuery(String perRegNo, String userID)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PER_REG_NO, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	PASSWORD, ");
		//buf.append("\n 	decrypt_char(usr.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, ");
		buf.append("\n 	ROLE, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN, ");
		buf.append("\n 	MOBILE, ");
		buf.append("\n 	SUBSI_TEL ");
	
		
		buf.append("\n from DAS.USER_INFO_TBL "); 
		/*buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n 	LEFT OUTER JOIN (select "); 
		buf.append("\n 		PER_REG_NO, DEPT, CNTC_PLC_OUTS, CNTC_PLC_TEL_NO "); 
		buf.append("\n 	from DAS.ERP_AGNTMST_TBL ");
		buf.append("\n 	where PER_REG_NO = ? "); 
		buf.append("\n 		and PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = ?) ");
		buf.append("\n 	) agn ON usr.PER_REG_NO = LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) ");*/
		//buf.append("\n where usr.OUT_USER_ID is not null ");
		buf.append("\n where PER_REG_NO = ? "); //MHCHOI
		buf.append("\n 	and  SBS_USER_ID = ?	 ");
		buf.append("\n 	and EMPLOYEE_TYPE = '003'	 ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}

	
	
	/**
	 * 테이블에 존재하지 않는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 */
	public static String selectOutEmployeeRoleForNotExistQuery(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		/*buf.append("\n  	agn.PER_REG_NO,  ");
		buf.append("\n  	agn.AGNT_NM,  ");
		buf.append("\n  	value(pgm.PGM_NM, '') AS PGM_NM,  ");
		buf.append("\n  	agn.DEPT,  ");
		buf.append("\n  	agn.CNTC_PLC_OUTS,  ");
		buf.append("\n  	agn.CNTC_PLC_TEL_NO,  ");
		buf.append("\n  	value((select com.USER_ID from DAS.ERP_COM_USER_TBL com where com.USER_ID = map.EMP_NO), '') AS USER_ID,  ");
		buf.append("\n  	pgm.PGM_ID  ");
		buf.append("\n  from DAS.ERP_AGNTMST_TBL agn LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON agn.PGM_CD = right(pgm.PGM_CD,6)  and (pgm.PGM_CD is not null and pgm.PGM_CD <> ''), ");
		buf.append("\n  		DAS.ERP_AGNTDTL_TBL map  ");
		buf.append("\n  where agn.AGNT = map.AGNT  ");
		buf.append("\n  	and agn.AGNT_SEQ = map.AGNT_SEQ   ");                                               
		buf.append("\n  	and map.EMP_NO = (select max(EMP_NO) from DAS.ERP_AGNTDTL_TBL where map.AGNT = AGNT and map.AGNT_SEQ = AGNT_SEQ) ");
		buf.append("\n  	and agn.PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where LTRIM(REPLACE(PER_REG_NO, '-', '')) = ?)  ");
		buf.append("\n  	and LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) = ? ");*/
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN , ");
		buf.append("\n FROM DAS.USER_INFO_TBL WHERE	 ");
		buf.append("\n 	PER_REG_NO = ? ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 외부직원의 정보를 조회한다.
	 * 
	 * @param userId
	 *            사용자 ID
	 */
	public static String selectOutEmployeeInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PER_REG_NO, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	PASSWORD, ");
		//buf.append("\n 	decrypt_char(usr.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, ");
		buf.append("\n 	ROLE, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN, ");
		buf.append("\n 	MOBILE, ");
		buf.append("\n 	SUBSI_TEL, ");
		buf.append("\n 	EMPLOYEE_YN ");
		buf.append("\n from DAS.USER_INFO_TBL ");
		buf.append("\n where SBS_USER_ID = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 외부 사용자 변경 내역을 조회한다.
	 * 
	 * @param perRegNo
	 *            외부사용자의 주민번호
	 * @param userId
	 *            사용자id
	 * @param searchFlag
	 *            조건
	 */
	public static String selectOutEmployeeRoleHistoryList(String perRegNo, String userId, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	hist.OUT_USER_ID, "); 
			buf.append("\n 	hist.SEQ,  ");
			String roleCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ROLE_CODE, "hist.ROLE");
			buf.append(roleCodeName + " AS ROLE_NM, ");
			buf.append("\n 	hist.ROLE,  ");
			buf.append("\n 	hist.VLDDT_BGN,  ");
			buf.append("\n 	hist.VLDDT_END,  ");
			buf.append("\n 	CASE WHEN hist.PGM_ID is null THEN '' ");
			buf.append("\n 	     ELSE (select pgm.PGM_NM from DAS.PGM_INFO_TBL pgm where pgm.PGM_ID = hist.PGM_ID) ");
			buf.append("\n 	END AS PGM_NM,  ");
			buf.append("\n 	hist.GAURANTOR_ID, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY SEQ) AS rownum ");
		}
		buf.append("\n from DAS.OUTSIDER_HIST_TBL hist, DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n where hist.OUT_USER_ID = usr.OUT_USER_ID ");
		
		//buf.append("\n 	and usr.PER_REG_NO = '"+perRegNo+"' "); 
		//MHCHOI
//		//주민번호가 없는 사용자 이면 사용자 ID를 가지고 정보 조회
        if(perRegNo.equals(DASBusinessConstants.PER_REG_NO))
        	buf.append("\n 	and usr.OUT_USER_ID = '"+userId+"' ");
		else		    
        	buf.append("\n 	and usr.PER_REG_NO = '"+perRegNo+"'	 ");
		
		return buf.toString();
	}
	/**
	 * 보증인 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 */
	public static String selectGuarantorInfoList(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	usr.USER_NO, ");
		buf.append("\n 	usr.USER_NM, ");
		buf.append("\n 	usr.DEPT_NM ");
		buf.append("\n from DAS.ERP_AGNTMST_TBL agn, DAS.ERP_AGNTDTL_TBL mp, DAS.ERP_COM_USER_TBL usr ");
		buf.append("\n where agn.AGNT = mp.AGNT ");
		buf.append("\n 	and agn.AGNT_SEQ = mp.AGNT_SEQ ");
		buf.append("\n 	and mp.EMP_NO = usr.USER_ID ");
		buf.append("\n 	and RTRIM(REPLACE(agn.PER_REG_NO, '-', '')) = ? ");
		buf.append("\n 	and agn.PD_END_DD >= ? ");
		buf.append("\n order by USER_NM	 ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 화면 권한코드를 조회한다.
	 * 
	 * @param regularYn
	 *            권한여부
	 */
	public static String selectAuthScreenQuery(String regularYn)
	{
		StringBuffer buf = new StringBuffer();
		if(DASBusinessConstants.YesNo.YES.equals(regularYn))
		{
			buf.append("\n select ");
			buf.append("\n 	role.OK_YN ");
			buf.append("\n from DAS.REGULAR_AUTH_TBL auth, DAS.ROLE_TBL role ");
			buf.append("\n where auth.ROLE = role.ROLE_CD ");
			buf.append("\n 	and auth.USER_ID = ? ");
			buf.append("\n 	and upper(role.AUTH_CD) = ? ");
		}
		else
		{
			buf.append("\n select ");
			buf.append("\n 	role.OK_YN ");
			buf.append("\n from DAS.OUTSIDER_INFO_TBL auth, DAS.ROLE_TBL role ");
			buf.append("\n where auth.ROLE = role.ROLE_CD ");
		//	buf.append("\n 	and auth.PER_REG_NO = ? ");
			buf.append("\n 	and auth.OUT_USER_ID = ? ");  //MHCHOI
			buf.append("\n 	and upper(role.AUTH_CD) = ? ");					
		}
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 역활별 권한 정보를 목록 조회한다.
	 */
	public static String selectAuthListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	code.SCL_CD, ");
		buf.append("\n 	code.DESC, ");
		buf.append("\n 	(select OK_YN from DAS.ROLE_TBL where ROLE_CD = ? and AUTH_CD = code.SCL_CD) AS OK_YN  ");
		buf.append("\n from DAS.CODE_TBL code ");
		buf.append("\n where CLF_CD = ? ");		
		buf.append("\n order by DESC ");		
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}	
	/**
	 * 계열사 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @param searchFlag
	 *            조건
	 * @return
	 */
	public static String selectSubEmployeeRoleListQuery(SubsidiaryinfoDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	SBS_USER_ID, ");
			buf.append("\n 	USER_NM, ");
			buf.append("\n 	VLDDT_BGN, ");
			buf.append("\n 	VLDDT_END, ");
			buf.append("\n 	MOBILE, ");
			buf.append("\n 	SUBSI_TEL, ");
			buf.append("\n 	APPROVE_YN , ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY SEQ DESC) AS rownum ");
		}
		buf.append("\n from DAS.USER_INFO_TBL  ");		
		
		//buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		//buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n where SBS_USER_ID is not null ");
		//buf.append("\n 	and usr.VLDDT_END >= (values(hex(current date))) ");  
		//buf.append("\n 	and usr.VLDDT_END < (values(hex(current date))) ");                                
		buf.append("\n AND EMPLOYEE_TYPE = '002' ");
		
				
		return buf.toString();
	}

	/*public static String selectsubEmployeeRoleListQuery(EmployeeRoleConditionDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	usr.OUT_USER_ID, ");
			buf.append("\n 	usr.OUT_USER_NM, ");
			buf.append("\n 	com.USER_NM, ");
			buf.append("\n 	pgm.PGM_NM, ");
			buf.append("\n 	usr.VLDDT_BGN, ");
			buf.append("\n 	usr.VLDDT_END, ");
			buf.append("\n 	usr.ROLE, ");
			buf.append("\n 	usr.PER_REG_NO, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY usr.REG_DT DESC) AS rownum ");
		}
		buf.append("\n from DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n where usr.OUT_USER_ID is not null ");

		if(CodeConstants.IDStatus.CONTINUE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END >= (values(hex(current date))) ");     
		}
		else if(CodeConstants.IDStatus.CLOSE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END < (values(hex(current date))) ");                                
		}
		if(!StringUtils.isEmpty(condition.getSearchText()))
		{
			buf.append("\n 	and (usr.OUT_USER_NM like '%"+condition.getSearchText()+"%' ) ");
		}
		
		
		return buf.toString();
	}
	*/
	
	
	/*public static String selectSubEmployeeRoleQuery(String perRegNo, String userID)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PER_REG_NO, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	USER_NM, ");
		
		buf.append("\n 	SBS_USER_ID, ");
		
		buf.append("\n 	PASSWORD, ");
		//buf.append("\n 	decrypt_char(usr.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, ");
		buf.append("\n 	ROLE, ");
	
		buf.append("\n 	MOBILE, ");
		buf.append("\n 	SUBSI_TEL,"); 
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN, ");		
		
		
		buf.append("\n from DAS.USER_INFO_TBL "); 
		buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n 	LEFT OUTER JOIN (select "); 
		buf.append("\n 		PER_REG_NO, DEPT, CNTC_PLC_OUTS, CNTC_PLC_TEL_NO "); 
		buf.append("\n 	from DAS.ERP_AGNTMST_TBL ");
		buf.append("\n 	where PER_REG_NO = ? "); 
		buf.append("\n 		and PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = ?) ");
		buf.append("\n 	) agn ON usr.PER_REG_NO = LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) ");
		//buf.append("\n where usr.OUT_USER_ID is not null ");
		buf.append("\n where PER_REG_NO = ? "); //MHCHOI
		buf.append("\n 	and  SBS_USER_ID = ?	 ");
		buf.append("\n 	and EMPLOYEE_TYPE = '002'	 ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}*/
	/**
	 * 테이블에 존재하지 않는 특정계열사직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            계열사 주민번호
	 * 

	 */
	public static String selectSubEmployeeRoleForNotExistQuery(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		/*buf.append("\n  	agn.PER_REG_NO,  ");
		buf.append("\n  	agn.AGNT_NM,  ");
		buf.append("\n  	value(pgm.PGM_NM, '') AS PGM_NM,  ");
		buf.append("\n  	agn.DEPT,  ");
		buf.append("\n  	agn.CNTC_PLC_OUTS,  ");
		buf.append("\n  	agn.CNTC_PLC_TEL_NO,  ");
		buf.append("\n  	value((select com.USER_ID from DAS.ERP_COM_USER_TBL com where com.USER_ID = map.EMP_NO), '') AS USER_ID,  ");
		buf.append("\n  	pgm.PGM_ID  ");
		buf.append("\n  from DAS.ERP_AGNTMST_TBL agn LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON agn.PGM_CD = right(pgm.PGM_CD,6)  and (pgm.PGM_CD is not null and pgm.PGM_CD <> ''), ");
		buf.append("\n  		DAS.ERP_AGNTDTL_TBL map  ");
		buf.append("\n  where agn.AGNT = map.AGNT  ");
		buf.append("\n  	and agn.AGNT_SEQ = map.AGNT_SEQ   ");                                               
		buf.append("\n  	and map.EMP_NO = (select max(EMP_NO) from DAS.ERP_AGNTDTL_TBL where map.AGNT = AGNT and map.AGNT_SEQ = AGNT_SEQ) ");
		buf.append("\n  	and agn.PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where LTRIM(REPLACE(PER_REG_NO, '-', '')) = ?)  ");
		buf.append("\n  	and LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) = ? ");*/
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN , ");
		buf.append("\n FROM DAS.USER_INFO_TBL WHERE	 ");
		buf.append("\n 	PER_REG_NO = ? ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	
	
	
	
	/*public static String selectEmployeeRoleListQuery(EmployeeDASRoleDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	SBS_USER_ID, ");
			buf.append("\n 	USER_NM, ");
			buf.append("\n 	DEPT_CD, ");
			buf.append("\n 	PGM_NM, ");
			buf.append("\n 	VLDDT_BGN, ");
			buf.append("\n 	VLDDT_END, ");
			buf.append("\n 	POSITION, ");
			buf.append("\n 	APPROVE_YN , ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY VLDDT_BGN ) AS rownum ");
		}
		buf.append("\n from DAS.USER_INFO_TBL  ");		
		//buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n where SBS_USER_ID is not null ");

		if(CodeConstants.IDStatus.CONTINUE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END >= (values(hex(current date))) ");     
		}
		else if(CodeConstants.IDStatus.CLOSE.equals(condition.getIdStatus()))
		{
			buf.append("\n 	and usr.VLDDT_END < (values(hex(current date))) ");                                
		}
		if(!StringUtils.isEmpty(condition.getSearchText()))
		{
			buf.append("\n 	and (usr.OUT_USER_NM like '%"+condition.getSearchText()+"%' ) ");
		}
		
		buf.append("\n AND EMPLOYEE_TYPE = '001' ");
		//buf.append("\n AND W_DEPT = ? ");
		
		
		
		return buf.toString();
	}*/
	
	
	
	
	
	/*public static String selectEmployeeRoleQuery(String perRegNo, String userID)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PER_REG_NO, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	DEPT_CD, ");
		buf.append("\n 	PASSWORD, ");
		//buf.append("\n 	decrypt_char(usr.PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD, ");
		buf.append("\n 	ROLE, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN, ");
		buf.append("\n 	MOBILE, ");
		buf.append("\n 	SUBSI_TEL ");
	
		
		buf.append("\n from DAS.USER_INFO_TBL "); 
		buf.append("\n 	LEFT OUTER JOIN DAS.ERP_COM_USER_TBL com ON usr.GAURANTOR_ID = com.USER_ID  ");
		buf.append("\n 	LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON usr.PGM_ID = pgm.PGM_ID ");		
		buf.append("\n 	LEFT OUTER JOIN (select "); 
		buf.append("\n 		PER_REG_NO, DEPT, CNTC_PLC_OUTS, CNTC_PLC_TEL_NO "); 
		buf.append("\n 	from DAS.ERP_AGNTMST_TBL ");
		buf.append("\n 	where PER_REG_NO = ? "); 
		buf.append("\n 		and PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = ?) ");
		buf.append("\n 	) agn ON usr.PER_REG_NO = LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) ");
		//buf.append("\n where usr.OUT_USER_ID is not null ");
		buf.append("\n where PER_REG_NO = ? "); //MHCHOI
		buf.append("\n 	and  SBS_USER_ID = ?	 ");
		buf.append("\n 	and EMPLOYEE_TYPE = '001'	 ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}
*/	
	/**
	 * 테이블에 존재하지 않는 특정 직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            직원의 주민번호
	 */
	public static String selectEmployeeRoleForNotExistQuery(String perRegNo)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		/*buf.append("\n  	agn.PER_REG_NO,  ");
		buf.append("\n  	agn.AGNT_NM,  ");
		buf.append("\n  	value(pgm.PGM_NM, '') AS PGM_NM,  ");
		buf.append("\n  	agn.DEPT,  ");
		buf.append("\n  	agn.CNTC_PLC_OUTS,  ");
		buf.append("\n  	agn.CNTC_PLC_TEL_NO,  ");
		buf.append("\n  	value((select com.USER_ID from DAS.ERP_COM_USER_TBL com where com.USER_ID = map.EMP_NO), '') AS USER_ID,  ");
		buf.append("\n  	pgm.PGM_ID  ");
		buf.append("\n  from DAS.ERP_AGNTMST_TBL agn LEFT OUTER JOIN DAS.PGM_INFO_TBL pgm ON agn.PGM_CD = right(pgm.PGM_CD,6)  and (pgm.PGM_CD is not null and pgm.PGM_CD <> ''), ");
		buf.append("\n  		DAS.ERP_AGNTDTL_TBL map  ");
		buf.append("\n  where agn.AGNT = map.AGNT  ");
		buf.append("\n  	and agn.AGNT_SEQ = map.AGNT_SEQ   ");                                               
		buf.append("\n  	and map.EMP_NO = (select max(EMP_NO) from DAS.ERP_AGNTDTL_TBL where map.AGNT = AGNT and map.AGNT_SEQ = AGNT_SEQ) ");
		buf.append("\n  	and agn.PD_END_DD = (select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where LTRIM(REPLACE(PER_REG_NO, '-', '')) = ?)  ");
		buf.append("\n  	and LTRIM(REPLACE(agn.PER_REG_NO, '-', '')) = ? ");*/
		buf.append("\n 	SBS_USER_ID, ");
		buf.append("\n 	USER_NM, ");
		buf.append("\n 	W_DEPT, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	POSITION, ");
		buf.append("\n 	APPROVE_YN , ");
		buf.append("\n FROM DAS.USER_INFO_TBL WHERE	 ");
		buf.append("\n 	PER_REG_NO = ? ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	
	
/*	public static String selecSubcodeList(SubCodeDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	CO_NM, ");
			
			buf.append("\n 	SEG_NM, ");
			
			buf.append("\n 	DEPT_NM, ");
			buf.append("\n 	DEPT_ID, ");
			
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY SEQ DESC) AS rownum ");
		}
		buf.append("\n from DAS.SUBSI_CODE_TBL  ");		
		
		buf.append("\n where DEPT_ID is not null ");

		
		
		
		
		return buf.toString();
	}
	
	*/
	
	/**
	 * 부서 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public static String selectDepInfoListQuery(DepInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
		
			buf.append("\n 		CODE.DESC,   DEP.DEPT_CD,  DEP.COCD,                                											\n");
			buf.append(" \n		DEP.POST_UNIT_CLF, DEP.DEPT_NM,  DEP.LVL,   DEP.SEQ,  DEP.SUP_HEAD_CD,                            								\n");
			buf.append("\n		DEP.SUP_HEAD_NM,  DEP.SUP_HEAD_SEQ,  DEP.SUP_HTPO_CD,  DEP.SUP_HTPO_NM,													\n");
			buf.append(" \n			DEP.SUP_HTPO_SEQ,   DEP.DEPT_CHAP_EMP_NO,  DEP.USE_YN,                                      											\n");			
			buf.append(" \n		ROW_NUMBER() OVER(ORDER BY SEQ) AS rownum      		\n");
		
		buf.append("\n 		FROM DEP_INFO_TBL DEP, CODE_TBL CODE 	\n");
		buf.append("\n 	WHERE DEP.COCD=CODE.SCL_CD AND CODE.CLF_CD='P058' 	\n");
		
					
			if(!condition.getCocd().equals("")){
			
			buf.append("\n AND   DEP.COCD = '"+condition.getCocd()+"'	\n");
			
		}
			
			
		if(!condition.getDept_nm().equals("")){			
			
			buf.append("\n AND	 DEP.DEPT_NM LIKE  '%"+condition.getDept_nm()+"%'	\n");
		
		}
if(!condition.getDept_cd().equals("")){			
			
			buf.append("\n AND	 DEP.DEPT_CD LIKE  '%"+condition.getDept_cd()+"%'	\n");
		
		}
			
				
				if(!condition.getSup_head_nm().equals("")){
				
	buf.append("\n AND	DEP.SUP_HEAD_NM LIKE '%"+condition.getSup_head_nm()+"%'	\n");


		}
				
				
				
		


		if(!condition.getPost_unit_clf().equals("")){
			
			buf.append("\n AND	DEP.POST_UNIT_CLF = '"+condition.getPost_unit_clf()+"'	\n");
			
				}
	
		
	if(!condition.getUse_yn().equals("")){
			
			buf.append("\n AND   DEP.USE_YN = '"+condition.getUse_yn()+"'	\n");
			
		}
	
	if(!condition.getSup_head_cd().equals("")){
		
		buf.append("\n AND   DEP.DEPT_CD = '"+condition.getSup_head_cd()+"'	\n");
		
	}
		
		return buf.toString();
	}
	
	
	/**
	 * 부서 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectDepInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select      DISTINCT DEPT_CD, DEPT_NM  FROM DEP_INFO_TBL WHERE POST_UNIT_CLF='003'    	\n");
		
		
		return buf.toString();
	}
	
	/**
	 * 회사소속 부서정보 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectDepInfoQuery(String cocd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select       DEPT_CD, DEPT_NM  FROM DEP_INFO_TBL WHERE COCD='"+cocd+"'   and USE_YN = 'Y' order by dept_nm asc	\n");
		
		
		return buf.toString();
	}
	
	
	
	/**
	 * 본부코드와 국코드를 조회한다.
	 * 
	 * @param DepInfoDO
	 */
	public static String selectDepListQuery(DepInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
		
		
			buf.append("\n 		DEPT_CD ,                          											\n");
			buf.append("\n 		DEPT_NM                           											\n");
			
				
		buf.append("\n 		FROM DEP_INFO_TBL 	\n");
		buf.append("\n 	WHERE POST_UNIT_CLF = '"+condition.getPost_unit_clf()+"' 	\n");
		 
		return buf.toString();
	}
	
	
	/**
	 * 역활 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectRoleInfoListQuery(RoleInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
	
			buf.append("\n 		                                       											\n");
			buf.append(" \n		  CODE.DESC    ,  INFO.ROLE_GROUP_ID                     								\n");
			buf.append("\n		FROM ROLE_INFO_TBL INFO												\n");
		   buf.append(" \n		INNER JOIN  ROLE_CODE_TBL ROLE ON ROLE.ROLE_GROUP_ID=INFO.ROLE_GROUP_ID                                         											\n");			
		
			buf.append(" \n		LEFT OUTER JOIN CODE_TBL CODE ON CODE.SCL_CD=INFO.ROLE_ID AND CODE.CLF_CD='A049'   	   		\n");
			buf.append(" \n		LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.SCL_CD=INFO.ROLE_GROUP_ID AND CODE2.CLF_CD='A050'  		\n");
			   
		
			buf.append("\n  	WHERE INFO.USE_YN = 'Y'		\n");
		    if(!condition.getRole_group_nm().equals("")){
			
			buf.append("\n 	 AND CODE2.DESC LIKE '%"+condition.getRole_group_nm()+"%'	\n");
		
		}
		    buf.append("\n 	ORDER BY INFO.ROLE_ID    \n");
		
	
		
		return buf.toString();
	}
	
	
	/**
	 * 권한 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @param searchFlag
	 *            조건
	 */
	public static String selectAuthorInfoListQuery(AuthorDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 		 DEP.SUP_HEAD_NM, DEP.DEPT_NM, USER.SBS_USER_ID,                                      											\n");
			buf.append("\n 		USER.USER_NM, USER.ROLE_CD,                            								\n");
			buf.append("\n		USER.MOBILE, DEP.COCD,													\n");
			buf.append(" \n		ROW_NUMBER() OVER(ORDER BY  DEP.COCD) AS rownum                                           											\n");			
		}
			buf.append("\n 		FROM USER_INFO_TBL USER, DEP_INFO_TBL DEP     		\n");
		    buf.append("\n 	WHERE DEP.DEPT_CD=USER.DEPT_CD 	\n");
		if(!condition.getCo_cd().equals("")){
			
			buf.append("\n 	AND DEP.COCD  = '"+condition.getCo_cd()+"'	\n");
			
		}
if(!condition.getUser_nm().equals("")){
			
			buf.append("\n 	AND USER.USER_NM LIKE '%"+condition.getUser_nm()+"%'	\n");
			
		}
if(!condition.getSbs_user_id().equals("")){
	
	buf.append("\n 	AND USER.SBS_USER_ID like '%"+condition.getSbs_user_id()+"%'	\n");
	
}
if(!condition.getDept_nm().equals("")){
	
	buf.append("\n 	AND DEP.DEPT_NM like '%"+condition.getDept_nm()+"%'	\n");
	
}
if(!condition.getDept_cd().equals("")){
	
	buf.append("\n 	AND DEP.DEPT_CD like '%"+condition.getDept_cd()+"%'	\n");
	
}
if(!condition.getAcct_code().equals("")){
	
	buf.append("\n 	AND USER.ACCT_CODE = '"+condition.getAcct_code()+"'	\n");
	
}


buf.append("\n 	order by dep.dept_nm asc, user.user_nm asc	\n");

buf.append("\n 	with ur		\n");

	
		
		return buf.toString();
	}
	/**
	 * 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectEmployeeListQuery(EmployeeInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select       DISTINCT                                                   												\n");
		   buf.append("\n 		USER.PER_REG_NO,USER.PASSWORD,USER.MOBILE, value(DEP.SUP_DEPT_CD, '')as SUP_DEPT_CD,  USER.PGM_ID,                                       											\n");
			buf.append("\n 		USER.SBS_USER_ID, USER.USER_NM, value(DEP.DEPT_NM,'') as DEPT_NM,  USER.EMPLOYEE_TYPE ,USER.EMPLOYEE_YN  ,                                             											\n");
			buf.append(" \n		value(PGM.PROGRAM_NAME,'') AS TITLE, USER.VLDDT_BGN, USER.VLDDT_END,  USER.APPROVE_YN ,  USER.ROLE_CD,                                   								\n");
			buf.append("\n		USER.POSITION, USER.APPROVE_STATUS,  USER.EMPLOYEE_YN,  DEP.COCD,DEP.DEPT_CD,	USER.USER_NUM,USER.ACCT_CODE,USER.DELETE_YN												\n");
			buf.append("\n		  ,value(USER2.USER_NM,'') AS REG_NM, value(USER3.USER_NM, '') AS APPROVE_NM   ,user.reg_dt	\n");
			buf.append("\n		  ,value(code.desc,'') AS ACCT_NM , USER.MONITOR_ROLE, CODE2.DESC AS MONITOR_NM	\n");
			buf.append("\n 		FROM USER_INFO_TBL USER                                      											\n");
			buf.append("\n 		LEFT OUTER  JOIN DEP_INFO_TBL DEP ON DEP.DEPT_CD=USER.DEPT_CD    AND DEP.COCD = USER.COCD  		\n");
			buf.append("\n 		LEFT OUTER JOIN USER_INFO_TBL USER2 ON USER2.SBS_USER_ID=USER.REG_ID  		\n");
			buf.append("\n 		LEFT OUTER JOIN USER_INFO_TBL USER3 ON USER3.SBS_USER_ID=USER.REG_ID  		\n");
			buf.append("\n 		 LEFT OUTER JOIN  DAS.PDS_PGMINFO_TBL PGM  ON  USER.PDS_PGM_ID=PGM.PROGRAM_CODE  \n");
			buf.append("\n 		LEFT OUTER JOIN  DAS.code_TBL code  ON  USER.acct_code=code.scl_cd and CODE.clf_cd='P059' 			\n");
			buf.append("\n 		LEFT OUTER JOIN  DAS.code_TBL code2  ON  USER.MONITOR_ROLE=code2.scl_cd and CODE2.clf_cd='P059' 			\n");
			
			
			buf.append(" \n		WHERE 1=1	and USER.ACCT_CODE <>'SA' AND USER.ACCT_CODE <>'SC'		\n");
			if(!condition.getEmployee_type().equals("")){
				String[] type=condition.getEmployee_type().split(",");
				buf.append("\n 	AND USER.EMPLOYEE_TYPE in (");
				for(int i =0;i<type.length;i++){
				buf.append("\n '"+type[i]+"'			\n");
				if(i<type.length-1){
					buf.append("\n ,		\n");
				}
			
				}	
				buf.append("\n  )	\n");
			}
			if(!condition.getUser_nm().equals("")){
			buf.append("\n 		AND USER.USER_NM LIKE '%"+condition.getUser_nm()+"%'                                    														\n");
			}
			if(!condition.getReg_nm().equals("")){
			buf.append(" \n		AND USER2.USER_NM like  '%"+condition.getUser_nm()+"%'     		\n");
			}
			if(!condition.getSbs_user_ID().equals("")){
				buf.append(" \n		AND USER.sbs_user_id like '%"+condition.getSbs_user_ID()+"%'     		\n");
				}
			
			if(!condition.getReg_id().equals("")){
				buf.append(" \n		AND USER.reg_id like '%"+condition.getReg_id()+"%'     		\n");
				}
			
			
			if(!condition.getPgm_nm().equals("")){
				buf.append("\n 		AND PGM.PGM_NM LIKE '%"+condition.getPgm_nm()+"%'                                    														\n");
			}
		/*	if(!condition.getDept_nm().equals("")){
				buf.append(" \n		AND DEP.DEPT_NM LIKE '%"+condition.getDept_nm()+"%'     		\n");
			}*/
			if(!condition.getDept_cd().equals("")){
				buf.append(" \n		AND (DEP.DEPT_CD LIKE '%"+condition.getDept_cd()+"%'  or   DEP.SUP_DEPT_CD LIKE '%"+condition.getDept_cd()+"%'  )  		\n");
			}
			if(!condition.getApprove_status().equals("")){
				buf.append(" \n		AND USER.APPROVE_STATUS = '"+condition.getApprove_status()+"'     		\n");
			}
			if(!condition.getCocd().equals("")){
				buf.append(" \n		AND DEP.COCD = '"+condition.getCocd()+"'     		\n");
			}
	
			if(!condition.getAcct_code().equals("")){
				buf.append(" \n		AND USER.ACCT_CODE = '"+condition.getAcct_code()+"'     		\n");
			}
			buf.append(" \n AND USER.DELETE_YN='N'    		\n");
			buf.append(" \n	ORDER BY USER.reg_dt DESC   , user.user_nm asc		\n");
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	/**
	 * MyPage 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectMyEmployeeListQuery(EmployeeInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select       DISTINCT                                                   												\n");
		   buf.append("\n 		USER.PER_REG_NO,USER.PASSWORD,USER.MOBILE, DEP.SUP_DEPT_CD,  USER.PGM_ID,                                       											\n");
			buf.append("\n 		USER.SBS_USER_ID, USER.USER_NM, DEP.DEPT_NM,  USER.EMPLOYEE_TYPE ,USER.EMPLOYEE_YN  ,                                             											\n");
			buf.append(" \n		VALUE(PGM.PROGRAM_NAME,'') AS TITLE, USER.VLDDT_BGN, USER.VLDDT_END,  USER.APPROVE_YN ,  USER.ROLE_CD,                                   								\n");
			buf.append("\n		USER.POSITION, USER.APPROVE_STATUS,  USER.EMPLOYEE_YN,  DEP.COCD,DEP.DEPT_CD,	USER.USER_NUM,USER.ACCT_CODE,USER.DELETE_YN												\n");
			buf.append("\n		  ,value(USER2.USER_NM,'') AS REG_NM, value(USER3.USER_NM, '') AS APPROVE_NM   ,user.reg_dt	\n");
			buf.append("\n		  ,value(code.desc,'') AS ACCT_NM  	\n");
			buf.append("\n 		FROM USER_INFO_TBL USER                                      											\n");
			buf.append("\n 		INNER JOIN DEP_INFO_TBL DEP ON DEP.DEPT_CD=USER.DEPT_CD    AND DEP.COCD = USER.COCD    		\n");
			buf.append("\n 		 LEFT OUTER JOIN USER_INFO_TBL USER2 ON USER2.SBS_USER_ID=USER.REG_ID  		\n");
			buf.append("\n 		 LEFT OUTER JOIN USER_INFO_TBL USER3 ON USER3.SBS_USER_ID=USER.REG_ID  		\n");
			buf.append("\n 		 LEFT OUTER JOIN  DAS.PDS_PGMINFO_TBL PGM  ON  USER.PDS_PGM_ID=PGM.PROGRAM_CODE    			\n");
			buf.append("\n 		LEFT OUTER JOIN  DAS.code_TBL code  ON  USER.acct_code=code.scl_cd and clf_cd='P059' 			\n");
			
			buf.append(" \n		WHERE 1=1		and USER.ACCT_CODE <>'SA' AND USER.ACCT_CODE <>'SC'	\n");
			buf.append(" \n		and ( USER.dept_cd = '"+condition.getDept_cd()+"' or	user2.reg_id = '"+condition.getReg_id()+"')	\n");
			if(!condition.getUser_nm().equals("")){
			buf.append("\n 		AND USER.USER_NM LIKE '%"+condition.getUser_nm()+"%'                                    														\n");
			}
			if(!condition.getReg_nm().equals("")){
			buf.append(" \n		AND USER2.USER_NM like  '%"+condition.getReg_nm()+"%'     		\n");
			}
			if(!condition.getSbs_user_ID().equals("")){
				buf.append(" \n		AND USER.sbs_user_id like '%"+condition.getSbs_user_ID()+"%'     		\n");
				}
			if(!condition.getPgm_nm().equals("")){
				buf.append("\n 		AND PGM.Program_name LIKE '%"+condition.getPgm_nm()+"%'                                    														\n");
			}
			/*if(!condition.getDept_nm().equals("")){
				buf.append(" \n		AND DEP.DEPT_NM LIKE '%"+condition.getDept_nm()+"%'     		\n");
			}*/
			if(!condition.getApprove_status().equals("")){
				buf.append(" \n		AND USER.APPROVE_STATUS = '"+condition.getApprove_status()+"'     		\n");
			}
			if(!condition.getCocd().equals("")){
				buf.append(" \n		AND DEP.COCD = '"+condition.getCocd()+"'     		\n");
			}
			/*if(!condition.getDept_cd().equals("")){
				buf.append(" \n		AND DEP.DEPT_CD LIKE '%"+condition.getDept_cd()+"%'     		\n");
			}*/
	
			if(!condition.getAcct_code().equals("")){
				buf.append(" \n		AND USER.ACCT_CODE = '"+condition.getAcct_code()+"'     		\n");
			}
			buf.append(" \n AND USER.DELETE_YN='N'    		\n");
			buf.append(" \n	ORDER BY user.reg_dt DESC  , user.user_nm asc  		\n");
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	/**
	 * 특정 직원 정보를 조회한다.
	 * 
	 * @param userid
	 *           사용자id
	 */
	public static String selectEmployeeInfoQuery(String userid)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select      DISTINCT                                                    												\n");
		   buf.append(" \n		USER.PER_REG_NO,USER.PASSWORD,USER.MOBILE, USER.PDS_PGM_ID, \n");
			buf.append(" \n		USER.SBS_USER_ID, USER.USER_NM, value(DEP.DEPT_CD,'') as DEPT_CD,  USER.EMPLOYEE_TYPE ,USER.EMPLOYEE_YN  ,  					\n");
			buf.append(" \n		USER.VLDDT_BGN, USER.VLDDT_END,  USER.APPROVE_YN ,  USER.ROLE_CD,      	\n");
			buf.append("\n		USER.POSITION, USER.APPROVE_STATUS,  USER.EMPLOYEE_YN,  user.COCD,	USER.USER_NUM,USER.ACCT_CODE,USER.DELETE_YN	,USER.employee_yn											\n");
			buf.append("\n		,user.out_sys						\n");
			
			buf.append(" \n		FROM USER_INFO_TBL USER           	\n");
			buf.append(" \n		left outer JOIN  DEP_INFO_TBL DEP ON DEP.DEPT_CD = USER.DEPT_CD          	\n");
		//buf.append(" \n		INNER JOIN  PGM_INFO_TBL PGM ON PGM.PGM_ID = USER.PGM_ID           	\n");
		
			buf.append(" \n		WHERE 1=1                           											\n");
		
			
			buf.append("\n 		AND USER.SBS_USER_ID LIKE '%"+userid+"%'     		\n");
		
	
		return buf.toString();
	}
	
	/**
	 * 특정 직원 정보를 조회한다.(사번
	 * 
	 * @param user_no
	 *            사번
	 */
	public static String selectEmployeeInfoQuery2(String user_no)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select      DISTINCT                                                    												\n");
		   buf.append(" \n		USER.PER_REG_NO,USER.PASSWORD,USER.MOBILE, USER.PDS_PGM_ID, \n");
			buf.append(" \n		USER.SBS_USER_ID, USER.USER_NM, DEP.DEPT_CD,  USER.EMPLOYEE_TYPE ,USER.EMPLOYEE_YN  ,  					\n");
			buf.append(" \n		USER.VLDDT_BGN, USER.VLDDT_END,  USER.APPROVE_YN ,  USER.ROLE_CD,      	\n");
			buf.append("\n		USER.POSITION, USER.APPROVE_STATUS,  USER.EMPLOYEE_YN,  DEP.COCD,	USER.USER_NUM,USER.ACCT_CODE,USER.DELETE_YN	,USER.employee_yn											\n");
			buf.append("\n		,user.out_sys						\n");
			
			buf.append(" \n		FROM USER_INFO_TBL USER           	\n");
			buf.append(" \n		INNER JOIN  DEP_INFO_TBL DEP ON DEP.DEPT_CD = USER.DEPT_CD          	\n");
		//buf.append(" \n		INNER JOIN  PGM_INFO_TBL PGM ON PGM.PGM_ID = USER.PGM_ID           	\n");
		
			buf.append(" \n		WHERE 1=1                           											\n");
		
			
			buf.append("\n 		AND USER.USER_NUM LIKE '"+user_no+"'     		\n");
		
	
		return buf.toString();
	}
	
	
	/**
	 * 비직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectNonEmployeeRoleListQuery(EmployeeInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select distinct");               
		
		buf.append("\n 	 CASE WHEN USER.ACCT_CODE='RA' OR USER.ACCT_CODE='RB' THEN USER.USER_NM ");
		buf.append("\n 	ELSE   OUT.OUT_USER_NM ");
		buf.append("\n 		END  as OUT_USER_NM, VALUE(user2.USER_NM,'')AS USER_NM, USER.SBS_USER_ID, USER.PER_REG_NO,   ");
	
			buf.append("\n 	DEP.DEPT_NM,  VALUE(PGM.PROGRAM_NAME,'') AS PROGRAM_NAME, USER.VLDDT_BGN,USER.VLDDT_END, USER.MOBILE, ");
			buf.append("\n 	 USER.APPROVE_STATUS,  JOB.DESC as role_nm , USER.PASSWORD, DEP.COCD  ,USER.PDS_PGM_ID ");
			buf.append("\n 	FROM USER_INFO_TBL USER  ");
			buf.append("\n 	inner join DEP_INFO_TBL DEP on  DEP.DEPT_CD=USER.DEPT_CD ");
			buf.append("\n 	 inner join code_tbl JOB on  JOB.CLF_CD='A049'  AND USER.ROLE_CD =JOB.SCL_CD ");
			buf.append("\n 	   left outer  join OUTWORKER_INFO_TBL OUT on  OUT.OUT_USER_ID=USER.SBS_USER_ID ");
			buf.append("\n 	 inner join PDS_PGMINFO_TBL PGM on USER.pds_pgm_id=PGM.PROGRAM_CODE ");
			buf.append("\n 	left outer  join USER_INFO_TBL user2 on user.REG_ID =USER2.SBS_USER_ID ");
			buf.append("\n 		left outer join approve_info_tbl app on app.pgm_id = user.pds_pgm_id ");
			
			
			
			
			buf.append("\n 	WHERE 1=1 ");
			buf.append("\n 	AND USER.EMPLOYEE_TYPE = '003'");
			buf.append("\n 	AND USER.APPROVE_STATUS = '1'");
			//buf.append("\n 	AND USER.reg_id like '%"+condition.getSbs_user_ID()+"%'");
			buf.append("\n 	and app.pgm_id in ("+condition.getPds_pgm_id()+" )");
			if(!condition.getOut_user_nm().equals("")){
			buf.append("\n 	AND OUT.OUT_USER_NM LIKE  '%"+condition.getOut_user_nm()+"%'");
			}
			if(!condition.getPgm_nm().equals("")){
				buf.append("\n 	AND PGM.program_name LIKE  '%"+condition.getPgm_nm()+"%'");
				}
			
		
		
		return buf.toString();
	}
	
	/**
	 * ERP 발령 정보를 조회한다.
	 * 
	 * @param erpappointDO
	 */
	public static String selectERPAppointListQuery(ErpAppointDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select         distinct                                                 												\n");
		
			buf.append("\n 		 com.SEQ_NO,com.CO_CD,com.USER_NO,com.ODER_DD,com.ODER_CD,                                     											\n");
			buf.append("\n 		 com.USER_NM,com.SEG_CD,com.SEG_NM,com.DEPT_CD,                         								\n");
			buf.append("\n		 com.DEPT_NM,com.TEAM_YN,com.JOB,com.TITLE													\n");
			buf.append("\n 		,com.OCPN_GR_CD, com.TEAM_YN ,com.ADAPT_YN,com.SEARCH_YN  ,com.order_nm,com.order_flag											\n");			
			buf.append("\n 		FROM DAS.COM_ORDER_TBL com    		\n");
			buf.append("\n 		inner join (select user_num from user_info_tbl group by user_num) user on user.USER_NUM=com.user_no	\n");
			buf.append("\n 		WHERE adapt_yn ='N'     		\n");
		
			buf.append("\n 			and seq_no<>0   		\n");
		
		return buf.toString();
	}
	
	
	/**
	 * ERP 발령정보 최근 순번 GET
	 */
		public static String selectERPAppointMaxSeqQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select                                                          												\n");
		
			buf.append("\n 		value(max(com.seq_no),0)+1                                  											\n");
			buf.append("\n 		FROM DAS.COM_ORDER_TBL com    		\n");
		
		return buf.toString();
	}
	
	/*public static String selectERPAppointInofQuery(String apdat_yn)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select                                                          												\n");
		
			buf.append("\n 		COCD,USER_NO,ODER_DD,ODER_SEQ,ODER_CD,                                     											\n");
			buf.append(" \n		USER_NM,USER_CLF,DEPT_CD,HAND_PHON,                           								\n");
			buf.append("\n		JOB_CD,TEAM_YN,APDAT_YN													\n");
		                            													
			buf.append(" \n		FROM DAS.ERP_APPOINT_TBL     		\n");
			buf.append(" \n		WHERE  APDAT_YN =  '" +apdat_yn+	"'	\n");
	
		
		return buf.toString();
	}*/
	
	
	
		/**
		 * TOKEN 생성을 위한 목록 조회한다.
		 */
	public static String selectTokenQuery()
	{
		StringBuffer buf = new StringBuffer();
		
			buf.append("\n 	select                                                          												\n");
			buf.append("\n 		RTRIM(USER_NUM) as user_num, LTRIM(RTRIM(PER_REG_NO)) as per_reg_no, SBS_USER_ID, COCD,ACCT_CODE,PASSWORD, 	\n");
			buf.append("\n 		USER_NM,ROLE_CD ,VLDDT_END,APPROVE_STATUS	\n");
			buf.append(" \n		 FROM USER_INFO_TBL 		\n");
			
			buf.append("\n WHERE SBS_USER_ID = ? AND APPROVE_STATUS='2'	\n"); 
			// APPROVE_STATUS ='2' 계정승인완료.
		
		return buf.toString();
	}
	
	/**
	 * ERP 발령 정보를 조회한다.
	 */
	public static String selectERPListQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select                                                          												\n");
		
			
			buf.append("\n 		DEPT_CD                           								\n");
			buf.append("\n 		FROM DAS.user_info_tbl     		\n");
			buf.append("\n 		where user_num = ?	\n");
			buf.append("\n 		AND cocd = ?	\n");
		
		return buf.toString();
	}	
	
	/**
	 * 타시스템과 직원 정보를 동기화한다.
	 */
	public static String selectOtherEmployeeList()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select       DISTINCT                                                   												\n");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD,  ");		
		buf.append("\n 	EMPLOYEE_TYPE , ");				
		buf.append("\n 	APPROVE_STATUS , ");	
		buf.append("\n 	EMPLOYEE_YN,  ");	
		buf.append("\n 	USER_NUM,  ");			
		buf.append("\n 	ACCT_CODE , ");	
		buf.append("\n 	COCD , ");
		buf.append("\n 	DELETE_YN  ");
			
			buf.append("\n 		FROM USER_INFO_TBL USER                                      											\n");
			
			
			buf.append(" \n		WHERE sbs_user_id= ?			\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}




	/**
	 * 타시스템과 부서 정보를 동기화한다.
	 * @param cocd 회사코드
	 * @param dept_cd 부서코드
	 * @return
	 * @throws DASException
	 */
	public static String selectOhterDepInfoList()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select                                                          												\n");
		
			buf.append("\n 		  DEP.DEPT_CD,  DEP.COCD,                                											\n");
			buf.append(" \n		DEP.POST_UNIT_CLF, DEP.DEPT_NM,  DEP.LVL,   DEP.SEQ,  DEP.SUP_HEAD_CD,                            								\n");
			buf.append("\n		DEP.SUP_HEAD_NM,  DEP.SUP_HEAD_SEQ,  DEP.SUP_HTPO_CD,  DEP.SUP_HTPO_NM,													\n");
			buf.append(" \n			DEP.SUP_HTPO_SEQ,   DEP.DEPT_CHAP_EMP_NO                                        											\n");			
			
		
		buf.append("\n 		FROM DEP_INFO_TBL DEP 	\n");
		buf.append("\n 	WHERE 1=1	\n");
		
		
			buf.append("\n AND   DEP.COCD = ?	\n");
			
		
			buf.append("\n AND	DEP.DEPT_CD = ?	\n");
			
			
		
		return buf.toString();
	}
	



	
	
	/**
	 * 유저 id를 찾는다
	 */
	public static String selecUserId()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select   	\n");
	
		buf.append("\n 	SBS_USER_ID  ");
		buf.append("\n 	,password ");
			
			buf.append("\n 		FROM USER_INFO_TBL                                    											\n");
			
			
			buf.append(" \n		WHERE LTRIM(RTRIM(per_reg_no))= ?			\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	/**
	 * 유저 id를 찾는다
	 */
	public static String selecUserId2()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select   	\n");
	
		buf.append("\n 	SBS_USER_ID  ");
		
		buf.append("\n 	,password ");
			buf.append("\n 		FROM USER_INFO_TBL                                    											\n");
			
			
			buf.append(" \n		WHERE USER_Num= ?			\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	/**
	 * 해당 유저의 부서정보를 가져온다
	 */
	public static String selecDepInfoForUserId()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select   	\n");
	
		buf.append("\n 	dept_cd  ");
		
			
			buf.append("\n 		FROM USER_INFO_TBL                                    											\n");
			
			
			buf.append(" \n		WHERE sbs_user_id= ?			\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	/**
	 * 본부코드와 국코드를 조회한다.
	 * 
	 * @param cocd 회사코드
	 * @return
	 * @throws DASException
	 */
	public static String selectSupHeadQuery(String cocd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select   DISTINCT      							\n");
		
		
			buf.append("\n 		sup_head_cd, sup_head_nm		\n");
						
		buf.append("\n 		FROM DAS.DEP_INFO_TBL 	\n");
		buf.append("\n 	WHERE use_yn = 'Y' and cocd = '"+cocd+"' 	\n");
		buf.append("\n AND SUP_HEAD_CD  <> '' AND SUP_HEAD_NM  <> '' 	\n");
		return buf.toString();
	}
	
	
	
	/**
	 * 부서코드와 부서명를 조회한다.
	 */
	public static String selectdepQuery(String deptcd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select   DISTINCT      							\n");
		buf.append("\n 		COCD,DEPT_CD,POST_UNIT_CLF,DEPT_NM,LVL,SEQ,SUP_HEAD_CD,SUP_HEAD_NM,SUP_HEAD_SEQ,SUP_HTPO_CD, \n");
		buf.append("\n 		SUP_HTPO_NM,SUP_HTPO_SEQ,DEPT_CHAP_EMP_NO,SUP_DEPT_CD,USE_YN,SUP_DEPT_NM \n");
		buf.append("\n 		FROM DAS.DEP_INFO_TBL 	\n");
		buf.append("\n 	WHERE DEPT_CD = '"+deptcd+"' 	\n");
		return buf.toString();
	}
	/**
	 * 부서코드와 부서명를 조회한다.
	 * @param deptcd     부서코드                                                                                                                                                                                          
	 * @return       list                                                                                                                                                                                       
	 * @throws DASException
	 */
	public static String selectSupHeadQuery2(String deptcd)
	{
		StringBuffer buf = new StringBuffer();
		//logger.debug("11111");
		buf.append("\n 	select   DISTINCT      							\n");
		
		
			buf.append("\n 		DEPT_CD,DEPT_NM		\n");
						
		buf.append("\n 		FROM DAS.DEP_INFO_TBL 	\n");
		buf.append("\n 	WHERE use_yn = 'Y' and  SUP_DEPT_CD = '"+deptcd+"' 	\n");
		//buf.append("\n AND SUP_DEPT_CD  <> '' OR SUP_DEPT_CD IS NOT NULL 	\n");
		return buf.toString();
	}
	
	  
	/**
	 * 역활 목록을 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 */
	public static String selectAuthorList(RoleInfoDO condition)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select DISTINCT CODE2.DESC, ROLE.ROLE_GROUP_ID        						\n");
	
			buf.append("\n 	 from					\n");
			buf.append(" \n		code_tbl CODE				\n");
			buf.append("\n		INNER JOIN ROLE_INFO_TBL ROLE ON  CODE.SCL_CD=ROLE.ROLE_ID									\n");
			buf.append(" \n		INNER JOIN code_tbl CODE2 ON  CODE2.SCL_CD=ROLE.ROLE_GROUP_ID                                         											\n");			
		
			buf.append(" \n		where CODE.CLF_CD='A049' AND CODE2.CLF_CD='A050' 	   		\n");
			
		
			buf.append("\n  	AND ROLE.USE_YN='Y'		\n");
		if(!condition.getRole_group_nm().equals("")){
			buf.append(" \n		AND CODE2.DESC LIKE '%"+condition.getRole_group_nm()+"%'		\n");
		}
	
		
		return buf.toString();
	}
	
	
	
	/**
	 * 국코드와 국명를 조회한다.
	 * 
	 * @param cocd 회사코드
	 */
	public static String selectSupHtpoQuery(String cocd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select   DISTINCT      							\n");
		
		
			buf.append("\n 		dept_cd,dept_nm		\n");
						
		buf.append("\n 		FROM DAS.DEP_INFO_TBL 	\n");
		buf.append("\n 	WHERE use_yn = 'Y' and  cocd = '"+cocd+"' 	\n");
		buf.append("\n AND (value(SUP_DEPT_CD,'')='' OR SUP_DEPT_CD = '"+cocd+"00000'  )	\n");
		return buf.toString();
	}
	
	
	
	
	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * 
	 * @param deptcd 부서코드
	 */
	public static String getDepinfoForuserList(String deptcd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select        							\n");
		
		
			buf.append("\n 		sbs_user_id, user_nm, user_num		\n");
						
		buf.append("\n 		from user_info_tbl where USER_NUM <> '' 	\n");
		buf.append("\n 	and dept_cd = '"+deptcd+"' 	\n");
		
		return buf.toString();
	}
	
	
	
	
	/**
	 * 프로그램별 승인 목록에서 사용자 사번과 직책을 구해온다
	 * @param dep_cd 부서코드
	 * @param user_nm 사용자명
	 */
	public static String selectEmployeeListForApp(String dep_cd, String user_nm)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select                                                         												\n");
		   buf.append("\n 		user_num,position			\n");
			buf.append("\n 		from user_info_Tbl										\n");
			
			
			buf.append(" \n		where user_num <>''			\n");
			
				buf.append("\n and user_nm like  '%"+user_nm+"%'	\n");
				buf.append("\n   and dept_cd = '"+dep_cd+"'	\n");
			
				
			buf.append(" \n AND DELETE_YN='N'    		\n");
			
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	
	
	
	
	
	
	
	
	/**
	 * 역활 정보를 목록 조회한다.
	 * 
	 * @param userid
	 *            사용자id
	 * @return
	 */
	public static String selecttRoleForLoginQuery(String userid)
	{
		StringBuffer buf = new StringBuffer();
		
	
			buf.append(" \n		  SELECT role_Cd FROM USER_INFO_TBL WHERE SBS_USER_ID='"+userid+"'		\n");
			
		
		
		return buf.toString();
	}
	
	
	/**
	 * 역활에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 */
	public static String selectRoleGroupForLogin(String role_cd)
	{
		StringBuffer buf = new StringBuffer();
		
	
			buf.append(" \n		  select  DISTINCT CODE.FUNCTION_ID from ROLE_CODE_TBL code	\n");
			
			buf.append(" \n		 inner join role_info_tbl rol on rol.ROLE_GROUP_ID = code.ROLE_GROUP_ID		\n");
			
			buf.append(" \n		 where rol.role_id = '"+role_cd+"' and rol.use_yn='Y'	 AND code.FUNCTION_ID <>''	\n");
			
			
		
		return buf.toString();
	}
	
	/**
	 * 해당 유저의 프로그램 정보를 가져온다
	 */
	public static String selectPgminfoForUser()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select   	\n");
	
		buf.append("\n 	pgm_id  ");
		
			
			buf.append("\n 		FROM approve_info_tbl                                    											\n");
			
			
			buf.append(" \n		WHERE approve_user_num= ?			\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	

	/**
	 * 해당 유저의 프로그램 정보를 가져온다
	 */
	public static String selectPgminfoForUser2()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append(" \n	select   	\n");
	
		buf.append("\n 	pgm_id  ");
		
			
			buf.append("\n 		FROM approve_info_tbl                                    											\n");
			
			
			buf.append(" \n		WHERE approve_user_num= ?		or dept_cd = ?	\n");
			
		
		
			
			
		
			buf.append("\n WITH UR	 ");
			
	
		return buf.toString();
	}
	
	
	/**
	 * 역활 정보를 목록 조회한다.
	 */
	public static String selectRoleInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select scl_cd, desc from code_tbl where clf_cd='A049' AND USE_YN ='Y'    order by desc asc       												\n");
	
		
		return buf.toString();
	}
	
	
	/**
	 * 주제영상 코드 조회           
	 */
	public static String selectAnnotInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select scl_cd, desc from code_tbl where clf_cd='P018' AND USE_YN ='Y' AND GUBUN ='S'    order by desc asc       												\n");
	
		
		return buf.toString();
	}
	
	
	
	//2012.4.19 다스 확장 추가 구현
	
	
	/**
	 * 역활에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 */
	public static String selectRoleInfoForChennel(RoleInfoDO roleInfoDO)
	{
		StringBuffer buf = new StringBuffer();
		
	
			buf.append(" \n		  SELECT SCL_CD, DESC	\n");
			
			buf.append(" \n		FROM CODE_TBL	\n");
			
			buf.append(" \n		 where CLF_CD='A049' \n");
			
			if(!roleInfoDO.getChennel().equals("")){
				buf.append(" \n	and rmk_2= '"+roleInfoDO.getChennel()+"'	\n");
			}
			
			if(!roleInfoDO.getCocd().equals("")){
				buf.append(" \n	and rmk_1= '"+roleInfoDO.getCocd()+"'	\n");
			}
		
		return buf.toString();
	}
	
	/**
	 * 사용자 로그인 현황을 조회한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static String selectLogInfo(LogInOutDO roleDO)
	{
		StringBuffer buf = new StringBuffer();
		
	
			buf.append(" \n		  select	\n");
			
			buf.append(" \n		 login.USER_ID	\n");
			
			buf.append(" \n		 ,dep.DEPT_NM \n");
	        buf.append(" \n		 ,login.LOGIN_DT	\n");
			
			buf.append(" \n		,login.STATUS	\n");
			
			buf.append(" \n		,login.LOGOUT_DT \n");	
			buf.append(" \n		,login.ip \n");	
			buf.append(" \n		 from	LOGINLOG_TBL login \n");
			
			buf.append(" \n		left outer join user_info_tbl user on user.sbs_user_id = login.USER_ID	\n");
			
			buf.append(" \n		 left outer join dep_info_tbl dep on dep.DEPT_CD = user.DEPT_CD \n");	
			buf.append(" \n		 where 1=1 \n");	
			
			if(!roleDO.getUser_id().equals("")){
				buf.append(" \n	and login.user_id= '"+roleDO.getUser_id()+"'	\n");
			}
			
			if(!roleDO.getUser_nm().equals("")){
				buf.append(" \n	and  USER.USER_NM like'%"+roleDO.getUser_nm()+"%'	\n");
			}
			if(roleDO.getAcctype().equals("R")){
				buf.append(" \n	and  USER.ACCT_CODE like'%"+roleDO.getAcctype()+"%'	\n");
			}else if(roleDO.getAcctype().equals("S")){
				buf.append(" \n	and  USER.ACCT_CODE like'%"+roleDO.getAcctype()+"%'	\n");
			}else {
				
			}
		
		return buf.toString();
	}
	
	
	
	/**
	 * 모니터링 역활 정보를 목록 조회한다.
	 * 
	 * @param userid
	 *            사용자id
	 * @return
	 */
	public static String selectRoleForLoginInMonitoringQuery(String userid)
	{
		StringBuffer buf = new StringBuffer();
			buf.append(" \n		  SELECT tm.MENU_ID, tm.MENU_nm, tm.depth, mp.USE_PERM, mp.perm_id	\n");
			buf.append(" \n		  from (		\n");
			buf.append(" \n		  SELECT node.MENU_ID, node.MENU_nm, (count(parent.MENU_ID) - (sub.depth + 1)) as depth	\n");
			buf.append(" \n		  from menu_tbl node, menu_tbl parent, menu_tbl parent2, (	\n");
			buf.append(" \n		  select 	  menu1.MENU_ID, menu1.MENU_nm, (count(menu2.MENU_ID) -1) as depth \n");
			buf.append(" \n		  from menu_tbl menu1, menu_tbl menu2	\n");
			buf.append(" \n		  where menu1.LFT between menu2.lft and menu2.rgt	\n");
			buf.append(" \n		  and menu1.MENU_ID = 1 and menu1.use_yn = 'Y' and menu2.use_yn = 'Y'	\n");
			buf.append(" \n		  group by menu1.MENU_ID, menu1.MENU_nm, menu1.lft	\n");
			buf.append(" \n		   ) sub 	\n");
			buf.append(" \n		  where node.lft between parent.lft and parent.rgt and node.lft between parent2.lft and parent2.rgt	\n");
			buf.append(" \n		  and parent2.MENU_ID = sub.MENU_ID and node.use_yn = 'Y' and parent.use_yn = 'Y'	\n");
			buf.append(" \n		  group by node.MENU_ID, node.MENU_nm, sub.depth, node.lft		\n");
			buf.append(" \n		  having (count(parent.MENU_ID) - (sub.depth + 1)) <= 2 		\n");
			buf.append(" \n		  order by node.lft 	\n");
			buf.append(" \n		  ) tm 		\n");
			buf.append(" \n		 inner join MENU_PERM_TBL mp on tm.MENU_ID = mp.MENU_ID	\n");
			buf.append(" \n		 	inner join (select tu.sbs_user_id, CASE WHEN RTRIM(VALUE(tu.monitor_role, '9')) = '' THEN '9' ELSE VALUE(tu.monitor_role, '9') end auth_id	\n");
			buf.append(" \n		 				from user_info_tbl tu					\n");
			buf.append(" \n		  				where tu.sbs_user_id='"+userid+"'		\n");
			buf.append(" \n		  ) up on mp.PERM_ID = up.AUTH_ID		\n");
			
			
		
		
		return buf.toString();
	}
	
	

	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * 
	 * @param deptcd 부서코드
	 */
	public static String getDepinfoForuserListFormonitoring(String deptcd)
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select        							\n");
		
		
			buf.append("\n 		sbs_user_id, user_nm, user_num, monitor_role		\n");
						
		buf.append("\n 		from user_info_tbl where USER_NUM <> '' 	\n");
		buf.append("\n 	and dept_cd = '"+deptcd+"' 	and monitor_role <>'0' \n"  );
		
		return buf.toString();
	}
	
	

	/**
	 * 역활 정보를 목록 조회한다.
	 */
	public static String selectRoleInfoForMonitoringQuery()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select varchar(perm_id) as perm_id ,perm_nm from PERM_TBL where use_yn='Y'	\n");
	
		
		return buf.toString();
	}
	
	

	
	/**
	 * 모니터링 역활 정보를 목록 조회한다.
	 * 
	 * @param userid
	 *            사용자id
	 * @return
	 */
	public static String selectAuthorForMonitoringQuery(String role_cd)
	{
		StringBuffer buf = new StringBuffer();
			buf.append(" \n		  SELECT tm.MENU_ID, tm.MENU_nm, tm.depth, mp.USE_PERM, mp.perm_id	\n");
			buf.append(" \n		  from (		\n");
			buf.append(" \n		  SELECT node.MENU_ID, node.MENU_nm, (count(parent.MENU_ID) - (sub.depth + 1)) as depth	\n");
			buf.append(" \n		  from menu_tbl node, menu_tbl parent, menu_tbl parent2, (	\n");
			buf.append(" \n		  select 	  menu1.MENU_ID, menu1.MENU_nm, (count(menu2.MENU_ID) -1) as depth \n");
			buf.append(" \n		  from menu_tbl menu1, menu_tbl menu2	\n");
			buf.append(" \n		  where menu1.LFT between menu2.lft and menu2.rgt	\n");
			buf.append(" \n		  and menu1.MENU_ID = 1 and menu1.use_yn = 'Y' and menu2.use_yn = 'Y'	\n");
			buf.append(" \n		  group by menu1.MENU_ID, menu1.MENU_nm, menu1.lft	\n");
			buf.append(" \n		   ) sub 	\n");
			buf.append(" \n		  where node.lft between parent.lft and parent.rgt and node.lft between parent2.lft and parent2.rgt	\n");
			buf.append(" \n		  and parent2.MENU_ID = sub.MENU_ID and node.use_yn = 'Y' and parent.use_yn = 'Y'	\n");
			buf.append(" \n		  group by node.MENU_ID, node.MENU_nm, sub.depth, node.lft		\n");
			buf.append(" \n		  having (count(parent.MENU_ID) - (sub.depth + 1)) <= 2 		\n");
			buf.append(" \n		  order by node.lft 	\n");
			buf.append(" \n		  ) tm 		\n");
			buf.append(" \n		 inner join MENU_PERM_TBL mp on tm.MENU_ID = mp.MENU_ID	\n");
			
			buf.append(" \n		  where mp.perm_id='"+role_cd+"'		\n");
			
			
		
		
		return buf.toString();
	}
	
}

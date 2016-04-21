package com.app.das.business.dao.statement;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.CopyInfoDO;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.SubsiInfoDO;
import com.app.das.business.transfer.SystemManageConditionDO;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CodeCommon;
import com.app.das.util.StringUtils;

/**
 * 시스템 관리의 모니터링의 장비, 작업, ID별 자료이용현황의 조회 및  미접속 ID현황의 조회 및 중지, 복구에 대한 
 * SQL 쿼리가 정의되어 있다.
 * @author ysk523
 *
 */
public class SystemManageStatement 
{
	private static Logger logger = Logger.getLogger(SystemManageStatement.class);
	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 */
	public static String selectEquipmentMonitoringListForMaxQuery() throws DASException
	{

		StringBuffer buf  = new StringBuffer();

		buf.append("\n select distinct");
		buf.append("\n 	equ.DAS_EQ_NM, ");		
		String eqClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_DEVICE_TYPE, "equ.DAS_EQ_CLF_CD");
		buf.append(eqClfCodeName + " AS DAS_EQ_CLF_NM, ");
		buf.append("\n 	B.CT_NM, ");
		buf.append("\n 	A.SCN_TTL, "); 
		buf.append("\n 	A.MCUID, "); 
		buf.append("\n 	equ.DAS_EQ_CLF_CD, ");
		buf.append("\n 	equ.REQ_USRID,  ");
		String workStateCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_JOB_STATUS, "equ.DAS_WORKSTAT_CD");
		buf.append(workStateCodeName + " AS DAS_WORKSTAT_NM, ");
		buf.append("\n 	equ.DAS_WORKSTAT_CD, ");
		String eqPsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_EQ_PROCESS_CODE, "equ.DAS_EQ_PS_CD");
		buf.append(eqPsCodeName + " AS DAS_EQ_PS_NM, ");
		buf.append("\n 	equ.PRGRS, ");
		buf.append("\n 	equ.MOD_DT, ");
		buf.append("\n 	equ.DAS_EQ_ID, ");
		buf.append("\n 	equ.LOG_RCD_PERIOD ");		
		buf.append("\n from DAS.DAS_EQUIPMENT_TBL equ "); 		
		buf.append("\n LEFT OUTER JOIN  ");
		buf.append("\n (select work.TAPE_ID, work.TAPE_ITEM_ID, work.MCUID, work.DAS_EQ_CLF_CD, work.DAS_EQ_PS_CD, work.DAS_WORKSTAT_CD, work.DAS_EQ_ID, tape.SCN_TTL ");
		buf.append("\n 	from DAS.WORK_LOG_TBL work, DAS.D_TAPEITEM_TBL tape, ");
		buf.append("\n 	(select  max(W.REG_DT) as MAXDATE, W.DAS_EQ_PS_CD, W.DAS_EQ_ID ");
		buf.append("\n  	from DAS.WORK_LOG_TBL W ");
		buf.append("\n 	    group by W.DAS_EQ_PS_CD, W.DAS_EQ_ID order by W.DAS_EQ_ID) as sub ");
		buf.append("\n 	    where work.TAPE_ITEM_ID = tape.TAPE_ITEM_ID ");
		buf.append("\n 	          and work.REG_DT = sub.MAXDATE ");
		buf.append("\n 	          and work.DAS_EQ_PS_CD = sub.DAS_EQ_PS_CD ");
		buf.append("\n 		      and work.DAS_EQ_ID = sub.DAS_EQ_ID ) A  ");
		buf.append("\n  on equ.DAS_EQ_ID = A.DAS_EQ_ID and equ.DAS_EQ_PS_CD = A.DAS_EQ_PS_CD and equ.DAS_WORKSTAT_CD = A.DAS_WORKSTAT_CD ");
		buf.append("\n LEFT OUTER JOIN  ");
		buf.append("\n (select con.CT_NM, e.CTI_ID ");
		buf.append("\n 	from DAS.CONTENTS_INST_TBL inst, DAS.CONTENTS_TBL con,DAS.DAS_EQUIPMENT_TBL e ");
		buf.append("\n 		where e.CTI_ID = inst.CTI_ID ");
		buf.append("\n 		and inst.CT_ID = con.CT_ID and inst.CT_ID = con.CT_ID) B on B.CTI_ID = equ.CTI_ID ");
		buf.append("\n where  equ.DAS_EQ_CLF_CD in ('A00','B00','C00','D00','E00','F00','G00','H00','I00','J00','K00','L00','M00','N00','O00','P00') ");		
		buf.append("\n  order by  equ.MOD_DT DESC, equ.DAS_EQ_NM ");
		buf.append("\n WITH UR	 ");


		return buf.toString();
	}
	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param workStateCode
	 */
	public static String selectEquipmentMonitoringListForMaxQuery(String workStateCode) throws DASException
	{

		StringBuffer buf  = new StringBuffer();

		buf.append("\n select distinct");
		buf.append("\n 	equ.DAS_EQ_NM, ");		
		String eqClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_DEVICE_TYPE, "equ.DAS_EQ_CLF_CD");
		buf.append(eqClfCodeName + " AS DAS_EQ_CLF_NM, ");
		buf.append("\n 	B.CT_NM, ");
		buf.append("\n 	A.SCN_TTL, "); 
		buf.append("\n 	A.MCUID, "); 
		buf.append("\n 	equ.DAS_EQ_CLF_CD, ");
		buf.append("\n 	equ.REQ_USRID,  ");
		String workStateCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_JOB_STATUS, "equ.DAS_WORKSTAT_CD");
		buf.append(workStateCodeName + " AS DAS_WORKSTAT_NM, ");
		buf.append("\n 	equ.DAS_WORKSTAT_CD, ");
		String eqPsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_EQ_PROCESS_CODE, "equ.DAS_EQ_PS_CD");
		buf.append(eqPsCodeName + " AS DAS_EQ_PS_NM, ");
		buf.append("\n 	equ.PRGRS, ");
		buf.append("\n 	equ.MOD_DT, ");
		buf.append("\n 	equ.DAS_EQ_ID, ");
		buf.append("\n 	equ.LOG_RCD_PERIOD ");		
		buf.append("\n from DAS.DAS_EQUIPMENT_TBL equ "); 		
		buf.append("\n LEFT OUTER JOIN  ");
		buf.append("\n (select work.TAPE_ID, work.TAPE_ITEM_ID, work.MCUID, work.DAS_EQ_CLF_CD, work.DAS_EQ_PS_CD, work.DAS_WORKSTAT_CD, work.DAS_EQ_ID, tape.SCN_TTL ");
		buf.append("\n 	from DAS.WORK_LOG_TBL work, DAS.D_TAPEITEM_TBL tape, ");
		buf.append("\n 	(select  max(W.REG_DT) as MAXDATE, W.DAS_EQ_PS_CD, W.DAS_EQ_ID ");
		buf.append("\n  	from DAS.WORK_LOG_TBL W ");
		buf.append("\n 	    group by W.DAS_EQ_PS_CD, W.DAS_EQ_ID order by W.DAS_EQ_ID) as sub ");
		buf.append("\n 	    where work.TAPE_ITEM_ID = tape.TAPE_ITEM_ID ");
		buf.append("\n 	          and work.REG_DT = sub.MAXDATE ");
		buf.append("\n 	          and work.DAS_EQ_PS_CD = sub.DAS_EQ_PS_CD ");
		buf.append("\n 		      and work.DAS_EQ_ID = sub.DAS_EQ_ID ) A  ");
		buf.append("\n  on equ.DAS_EQ_ID = A.DAS_EQ_ID and equ.DAS_EQ_PS_CD = A.DAS_EQ_PS_CD and equ.DAS_WORKSTAT_CD = A.DAS_WORKSTAT_CD ");
		buf.append("\n LEFT OUTER JOIN  ");
		buf.append("\n (select con.CT_NM, e.CTI_ID ");
		buf.append("\n 	from DAS.CONTENTS_INST_TBL inst, DAS.CONTENTS_TBL con,DAS.DAS_EQUIPMENT_TBL e ");
		buf.append("\n 		where e.CTI_ID = inst.CTI_ID ");
		buf.append("\n 		and inst.CT_ID = con.CT_ID and inst.CT_ID = con.CT_ID) B on B.CTI_ID = equ.CTI_ID ");
		buf.append("\n where  equ.DAS_EQ_CLF_CD in ('A00','B00','C00','D00','E00','F00','G00','H00','I00','J00','K00','L00','M00','N00','O00','P00') ");		
		if(!StringUtils.isEmpty(workStateCode))
		{
			buf.append("\n 	and equ.DAS_WORKSTAT_CD = ?  ");
		}		
		buf.append("\n  order by  equ.DAS_EQ_NM, DAS_EQ_PS_NM ");
		buf.append("\n WITH UR	 ");


		return buf.toString();
	}
	/**
	 * 모니터링 장비에서 로그테이블의 장비구분코드별 당일 것을 조회한다.
	 * @param eqClfCd 장비구분코드
	 */
	public static String selectEquipmentMonitoringListForToDateQuery(String eqClfCd)
	{

		StringBuffer buf  = new StringBuffer();

		buf.append("\n select distinct");
		buf.append("\n 	equ.DAS_EQ_NM, ");
		String eqClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_DEVICE_TYPE, "equ.DAS_EQ_CLF_CD");
		buf.append(eqClfCodeName + " AS DAS_EQ_CLF_NM, ");
		buf.append("\n 	equ.DAS_EQ_CLF_CD, ");
		buf.append("\n 	equ.REQ_USRID,  ");
		String workStateCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_JOB_STATUS, "equ.DAS_WORKSTAT_CD");
		buf.append(workStateCodeName + " AS DAS_WORKSTAT_NM, ");
		buf.append("\n 	equ.DAS_WORKSTAT_CD, ");
		String eqPsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_EQ_PROCESS_CODE, "equ.DAS_EQ_PS_CD");
		buf.append(eqPsCodeName + " AS DAS_EQ_PS_NM, ");
		buf.append("\n 	equ.PRGRS, ");
		//	buf.append("\n 	equ.MOD_DT, ");
		buf.append("\n 	sub.MAXDATE, ");
		buf.append("\n 	equ.DAS_EQ_ID, ");
		buf.append("\n 	equ.LOG_RCD_PERIOD ");		
		buf.append("\n from DAS.DAS_EQUIPMENT_TBL equ, ");		
		buf.append("\n (select  max(W.REG_DT) as MAXDATE, W.DAS_EQ_ID, W.DAS_EQ_PS_CD, W.DAS_WORKSTAT_CD  ");
		buf.append("\n 	from DAS.WORK_LOG_TBL W ");
		buf.append("\n 	where W.DAS_WORKSTAT_CD  = '000' ");		
		buf.append("\n 	    group by W.DAS_EQ_ID, W.DAS_EQ_PS_CD, W.DAS_WORKSTAT_CD order by W.DAS_EQ_ID) sub ");
		buf.append("\n where equ.DAS_WORKSTAT_CD  = '000' ");
		buf.append("\n       and sub.DAS_EQ_ID = equ.DAS_EQ_ID ");
		buf.append("\n       and sub.DAS_EQ_PS_CD = equ.DAS_EQ_PS_CD ");
		buf.append("\n       and sub.DAS_WORKSTAT_CD = equ.DAS_WORKSTAT_CD  ");
		buf.append("\n       and substr(equ.MOD_DT, 1, 8) = ?  ");
		if(!StringUtils.isEmpty(eqClfCd))
		{
			buf.append("\n 	and equ.DAS_EQ_CLF_CD = ?  ");
		}		
		buf.append("\n  order by  equ.DAS_EQ_NM, DAS_EQ_PS_NM ");
		buf.append("\n WITH UR	 ");

		return buf.toString();				
	}
	/**
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param conditionDO 조회조건을 담고있는 beans
	 */
	public static String  selectEquipmentMonitoringListForWorkQuery(SystemManageConditionDO conditionDO, String searchFlag)
	{
		String toDate = CalendarUtil.getToday();

		StringBuffer buf  = new StringBuffer();
		buf.append("\n select distinct ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	log.DD, "); 
			String workStateCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_JOB_STATUS, "log.DAS_WORKSTAT_CD");
			buf.append(workStateCodeName + " AS DAS_WORKSTAT_NM, ");
			buf.append("\n 	log.DAS_WORKSTAT_CD,  ");
			String eqClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_DEVICE_TYPE, "log.DAS_EQ_CLF_CD");
			buf.append(eqClfCodeName + " AS DAS_EQ_CLF_NM, ");
			String eqClfCodeName2 = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_WORK_TYPE, "log.DAS_EQ_CLF_CD");
			buf.append(eqClfCodeName2 + " AS DAS_WORK_NM, ");
			buf.append("\n 	log.DAS_EQ_CLF_CD, "); 
			buf.append("\n 	log.DAS_EQ_NM,  ");
			String eqPsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_EQ_PROCESS_CODE, "log.DAS_EQ_PS_CD");
			buf.append(eqPsCodeName + " AS DAS_EQ_PS_NM, ");
			buf.append("\n 	log.DAS_EQ_PS_CD,  ");
			buf.append("\n 	log.REQ_USRID,  ");
			buf.append("\n 	log.REQ_TM, ");
			buf.append("\n 	(meta.TITLE || ' - ' || cont.CT_NM) as ct_nm, ");
			buf.append("\n 	log.USER_NM, ");
			buf.append("\n 	log.MASTER_ID, ");
			buf.append("\n 	log.DOWN_VOL, ");
			buf.append("\n 	log.TOTAL_SIZE, ");
			buf.append("\n 	inst.CT_ID, ");
			buf.append("\n 	log.REG_DT, ");
			buf.append("\n 	log.MOD_DT, "); 
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY log.REG_DT DESC) AS rownum ");
		}
		buf.append("\n from DAS.WORK_LOG_TBL log ");
		buf.append("\n inner join das.CONTENTS_INST_TBL inst on inst.CTI_ID = log.CTI_ID ");
		buf.append("\n inner join das.CONTENTS_TBL cont on cont.CT_ID = inst.CT_ID ");
		buf.append("\n inner join (select max(A.reg_dt) as reg_dt, inst.CT_ID, A.DAS_EQ_CLF_CD, A.DAS_EQ_PS_CD from das.WORK_LOG_TBL A ");
		buf.append("\n 	              inner join das.CONTENTS_INST_TBL inst on inst.CTI_ID = A.CTI_ID ");
		buf.append("\n 				group by inst.CT_ID, A.DAS_EQ_CLF_CD, A.DAS_EQ_PS_CD) as recent on recent.reg_dt = log.REG_DT and recent.CT_ID = cont.CT_ID and recent.DAS_EQ_CLF_CD = log.DAS_EQ_CLF_CD and recent.DAS_EQ_PS_CD = log.DAS_EQ_PS_CD ");
		buf.append("\n , das.METADAT_MST_TBL meta ");
		buf.append("\n where meta.MASTER_ID = log.MASTER_ID ");
		buf.append("\n and log.DAS_EQ_CLF_CD in ('A00','B00','C00','D00','E00','F00','G00','H00','I00','J00','K00','L00','M00','N00','O00','P00') ");		
		if(!StringUtils.isEmpty(conditionDO.getWorkCode()))
		{
			buf.append("\n 	and log.DAS_EQ_CLF_CD = '"+conditionDO.getWorkCode()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getWorkStateCode()))
		{
			buf.append("\n 	and log.DAS_WORKSTAT_CD = '"+conditionDO.getWorkStateCode()+"' ");
		}

		if(!DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n  order by  log.MOD_DT DESC, log.DAS_EQ_NM ");			
		}

		return buf.toString();
	}
	/**
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectEquipmentLogListQuery(SystemManageConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	log.DD, "); 
			String workStateCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_JOB_STATUS, "log.DAS_WORKSTAT_CD");
			buf.append(workStateCodeName + " AS DAS_WORKSTAT_NM, ");
			buf.append("\n 	log.DAS_WORKSTAT_CD,  ");
			String eqClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_DEVICE_TYPE, "log.DAS_EQ_CLF_CD");
			buf.append(eqClfCodeName + " AS DAS_EQ_CLF_NM, ");
			String eqClfCodeName2 = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_WORK_TYPE, "log.DAS_EQ_CLF_CD");
			buf.append(eqClfCodeName2 + " AS DAS_WORK_NM, ");
			buf.append("\n 	log.DAS_EQ_CLF_CD, "); 
			buf.append("\n 	log.DAS_EQ_NM,  ");
			String eqPsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_EQ_PROCESS_CODE, "log.DAS_EQ_PS_CD");
			buf.append(eqPsCodeName + " AS DAS_EQ_PS_NM, ");
			buf.append("\n 	log.DAS_EQ_PS_CD,  ");
			buf.append("\n 	log.REQ_USRID,  ");
			buf.append("\n 	log.REQ_TM, ");
			buf.append("\n 	(meta.TITLE || ' - ' || cont.CT_NM) as ct_nm, ");
			buf.append("\n 	log.USER_NM, ");
			buf.append("\n 	log.MASTER_ID, ");
			buf.append("\n 	log.DOWN_VOL, ");
			buf.append("\n 	log.TOTAL_SIZE, ");
			buf.append("\n 	inst.CT_ID, ");
			buf.append("\n 	log.REG_DT, ");
			buf.append("\n 	log.MOD_DT, "); 
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY log.REG_DT DESC) AS rownum ");
		}
		buf.append("\n from DAS.WORK_LOG_TBL log ");
		buf.append("\n inner join das.CONTENTS_INST_TBL inst on inst.CTI_ID = log.CTI_ID ");
		buf.append("\n left outer join das.METADAT_MST_TBL meta on meta.MASTER_ID = log.MASTER_ID ");
		buf.append("\n , das.CONTENTS_TBL cont "); 
		buf.append("\n where cont.CT_ID = inst.CT_ID ");

		if(!StringUtils.isEmpty(conditionDO.getStartDate()))
		{
			buf.append("\n 	and substr(log.DD, 1, 8) >= '"+conditionDO.getStartDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEndDate()))
		{
			buf.append("\n 	and substr(log.DD, 1, 8) <= '"+conditionDO.getEndDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getWorkCode()))
		{
			buf.append("\n 	and log.DAS_EQ_CLF_CD = '"+conditionDO.getWorkCode()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEqClfCd()))
		{
			buf.append("\n 	and log.DAS_EQ_ID = "+conditionDO.getEqClfCd());
		}
		if(!StringUtils.isEmpty(conditionDO.getWorkStateCode()))
		{
			buf.append("\n 	and log.DAS_WORKSTAT_CD = '"+conditionDO.getWorkStateCode()+"' ");
		}

		return buf.toString();
	}
	/**
	 * ID별 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectContentsUseInfoListQuery(SystemManageConditionDO conditionDO, String searchFlag) 
	{
		String Erp_Com_UserNm = "";
		String Outsider_Info_UserNm = "";


		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	REQ_USRID, ");
			buf.append("\n 	USER_NM, ");
			buf.append("\n 	CT_ID, ");
			buf.append("\n 	CT_NM, ");
			buf.append("\n 	REQ_DT, ");
			buf.append("\n 	SOM, ");
			buf.append("\n 	EOM, ");
			buf.append("\n 	CART_SEQ, ");
			buf.append("\n 	RIST_CLF_CD, ");
			buf.append("\n 	TITLE, ");
			buf.append("\n 	CART_NO, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY REQ_USRID) AS rownum ");
		}
		buf.append("\n  from (select ");
		buf.append("\n 	        log.REQ_USRID, ");
		buf.append("\n 	        CASE WHEN SUBSTR(log.REQ_USRID, 1, 1) != 'D' THEN (SELECT USER_NM FROM DAS.ERP_COM_USER_TBL WHERE user_id = log.REQ_USRID) ");
		buf.append("\n              ELSE (SELECT OUT_USER_NM FROM DAS.OUTSIDER_INFO_TBL WHERE out_user_id = log.REQ_USRID) END AS USER_NM, ");
		buf.append("\n 	        cont.CT_ID, ");
		buf.append("\n 	        cont.CT_NM, ");
		buf.append("\n        	log.REQ_DT, ");
		buf.append("\n       	lcont.SOM, ");
		buf.append("\n 	        lcont.EOM, ");
		buf.append("\n 	        meta.TITLE, ");
		buf.append("\n 	        (select desc from DAS.code_tbl where clf_cd = 'P021' and scl_cd = lcont.RIST_CLF_CD) as RIST_CLF_CD, ");
		buf.append("\n 	        lcont.CART_SEQ, ");
		buf.append("\n          lcont.CART_NO ");
		//			buf.append("\n  (SELECT TITLE FROM DAS.METADAT_MST_TBL A ");
		//			buf.append("\n      INNER JOIN DAS.CONTENTS_MAPP_TBL B on B.ct_id = lcont.ct_id and B.master_id = A.master_id) as TITLE, ");

		buf.append("\n            from DAS.CART_CONT_TBL lcont, DAS.DOWN_CART_TBL log, DAS.METADAT_MST_TBL meta, ");
		buf.append("\n 		           DAS.CONTENTS_INST_TBL inst, DAS.CONTENTS_TBL cont ");
		buf.append("\n            where lcont.CART_NO = log.CART_NO ");
		buf.append("\n 	               and lcont.CTI_ID = inst.CTI_ID ");
		buf.append("\n 	               and inst.CT_ID = cont.CT_ID ");
		buf.append("\n 	               and meta.MASTER_ID = lcont.MASTER_ID ");

		if(!StringUtils.isEmpty(conditionDO.getUserId()))
		{
			buf.append("\n 	and log.REQ_USRID = '"+conditionDO.getUserId()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getCt_id()))
		{
			buf.append("\n 	and cont.CT_ID = "+conditionDO.getCt_id()+" ");
		}
		if(!StringUtils.isEmpty(conditionDO.getStartDate()))
		{
			buf.append("\n 	and substr(log.REQ_DT, 1, 8) >= '"+conditionDO.getStartDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEndDate()))
		{
			buf.append("\n 	and substr(log.REQ_DT, 1, 8) <= '"+conditionDO.getEndDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getCt_nm()))
		{
			buf.append("\n 	and cont.CT_NM like '%"+conditionDO.getCt_nm()+"%'  ");
		}
		buf.append("\n  ) A ");

		// 이름으로 검색시 
		if(!StringUtils.isEmpty(conditionDO.getUserNm()))
		{
			buf.append("\n  WHERE USER_NM like '%" + conditionDO.getUserNm() + "%'  ");
		}
		return buf.toString();
	}
	/**
	 * 외부직원 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectContentsOutUseInfoListQuery(SystemManageConditionDO conditionDO, String searchFlag) 
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	log.REQ_USRID, ");
			buf.append("\n 	out.OUT_USER_NM AS USER_NM, ");
			buf.append("\n 	cont.CT_NM, ");
			buf.append("\n 	log.DOWN_DT, ");
			buf.append("\n 	lcont.SOM, ");
			buf.append("\n 	lcont.EOM, ");
			buf.append("\n 	log.DOWN_SUBJ, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY log.DOWN_DT DESC) AS rownum ");
		}
		buf.append("\n from DAS.CART_CONT_TBL lcont, DAS.DOWN_CART_TBL log, "); 
		buf.append("\n 	DAS.CONTENTS_INST_TBL inst, DAS.CONTENTS_TBL cont, ");
		buf.append("\n 	( ");
		buf.append("\n 	select  ");
		buf.append("\n 		usr.OUT_USER_ID, usr.OUT_USER_NM ");
		buf.append("\n 	from DAS.OUTSIDER_INFO_TBL usr ");
		buf.append("\n 	where usr.GAURANTOR_ID = '"+conditionDO.getUserId()+"' ");

		buf.append("\n 		union all ");
		buf.append("\n 			 select USER_ID as OUT_USER_ID, USER_NM as OUT_USER_NM from DAS.ERP_COM_USER_TBL WHERE USER_ID = '" + conditionDO.getUserId() + "' ");
		buf.append("\n 	) out ");
		buf.append("\n where lcont.CART_NO = log.CART_NO ");
		buf.append("\n 	and lcont.CTI_ID = inst.CTI_ID ");
		buf.append("\n 	and inst.CT_ID = cont.CT_ID ");
		buf.append("\n 	and log.REQ_USRID = out.OUT_USER_ID ");
		if(!StringUtils.isEmpty(conditionDO.getAgentId()))
		{
			buf.append("\n 	and log.REQ_USRID = '"+conditionDO.getAgentId()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getStartDate()))
		{
			buf.append("\n 	and substr(log.DOWN_DT, 1, 8) >= '"+conditionDO.getStartDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEndDate()))
		{
			buf.append("\n 	and substr(log.DOWN_DT, 1, 8) <= '"+conditionDO.getEndDate()+"' ");
		}

		return buf.toString();
	}


	/**
	 * 미접속 ID 현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectNonLoginUserListQuery(SystemManageConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();


		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n select ");
			buf.append("\n 		count(1) ");
			buf.append("\n from DAS.OUTSIDER_INFO_TBL usr ");
			buf.append("\n 		LEFT OUTER JOIN DAS.ERP_AGNTMST_TBL agn ON usr.PER_REG_NO = agn.PER_REG_NO "); 
			buf.append("\n 		LEFT OUTER JOIN DAS.ERP_COM_USER_TBL erp ON usr.GAURANTOR_ID = erp.USER_ID ");
			buf.append("\n 	    LEFT OUTER JOIN (select USER_ID, max(ACCESSTIME) AS ACCESS_TIME from DAS.IDLOG_TBL group by USER_ID) log ON usr.OUT_USER_ID = log.USER_ID ");
			buf.append("\n  where value(agn.PD_END_DD, '') = value((select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = usr.PER_REG_NO), '')  ");
			if(CodeConstants.LoginUserFlag.NON_LOGIN_ID.equals(conditionDO.getLoginUseFlag()))
			{
				if(!StringUtils.isEmpty(conditionDO.getStartDate()) && !StringUtils.isEmpty(conditionDO.getEndDate())) {
					buf.append("\n	and (log.ACCESS_TIME is null or (substr(log.ACCESS_TIME, 1, 8)  < '"+conditionDO.getStartDate()+"' and value(usr.VLDDT_END, '99999999') > '"+conditionDO.getEndDate()+"')) ");
					buf.append("\n 	and usr.PW_LST_CHG < '"+conditionDO.getStartDate()+"' ");
					buf.append("\n 	and usr.VLDDT_END > '"+conditionDO.getStartDate()+"' ");
				}
			}
			else if(CodeConstants.LoginUserFlag.STOP_DATE.equals(conditionDO.getLoginUseFlag()))
			{
				if(!StringUtils.isEmpty(conditionDO.getStartDate()))
				{
					buf.append("\n 	and usr.VLDDT_END >= '"+conditionDO.getStartDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getEndDate()))
				{
					buf.append("\n 	and usr.VLDDT_END <= '"+conditionDO.getEndDate()+"' ");
				}
			}
		}
		else
		{

			buf.append("\n	select ");
			buf.append("\n 	list.OUT_USER_ID, ");
			buf.append("\n 	list.OUT_USER_NM, ");
			buf.append("\n 	list.CO_NM, ");
			buf.append("\n	list.CNTC_PLC_OUTS, ");
			buf.append("\n	list.VLDDT_END, ");
			buf.append("\n	list.GUARANTOR_NM, ");
			buf.append("\n	list.GUARANTOR_HAND_PHON, ");
			buf.append("\n	ROW_NUMBER() OVER(ORDER BY list.OUT_USER_ID) AS rownum ");
			buf.append("\n	FROM ");
			buf.append("\n	(    "); 
			buf.append("\n select distinct ");
			buf.append("\n 	usr.OUT_USER_ID, ");
			buf.append("\n 	usr.OUT_USER_NM, ");
			String coCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_CO_CODE, "agn.CO_CD");
			buf.append(coCodeName + " AS CO_NM, ");
			buf.append("\n 	agn.CNTC_PLC_OUTS, ");
			buf.append("\n 	usr.VLDDT_END, ");
			buf.append("\n 	erp.USER_NM AS GUARANTOR_NM, ");
			buf.append("\n 	erp.HAND_PHON AS GUARANTOR_HAND_PHON "); // MHCHOI , deleted from origial
			buf.append("\n from DAS.OUTSIDER_INFO_TBL usr ");
			buf.append("\n 		LEFT OUTER JOIN DAS.ERP_AGNTMST_TBL agn ON usr.PER_REG_NO = agn.PER_REG_NO "); 
			buf.append("\n 		LEFT OUTER JOIN DAS.ERP_COM_USER_TBL erp ON usr.GAURANTOR_ID = erp.USER_ID ");
			buf.append("\n 	    LEFT OUTER JOIN (select USER_ID, max(ACCESSTIME) AS ACCESS_TIME from DAS.IDLOG_TBL group by USER_ID) log ON usr.OUT_USER_ID = log.USER_ID ");
			buf.append("\n  where value(agn.PD_END_DD, '') = value((select max(PD_END_DD) from DAS.ERP_AGNTMST_TBL where PER_REG_NO = usr.PER_REG_NO), '')  ");
			if(CodeConstants.LoginUserFlag.NON_LOGIN_ID.equals(conditionDO.getLoginUseFlag()))
			{
				if(!StringUtils.isEmpty(conditionDO.getStartDate()) && !StringUtils.isEmpty(conditionDO.getEndDate())) {
					buf.append("\n	and (log.ACCESS_TIME is null or (substr(log.ACCESS_TIME, 1, 8) < '"+conditionDO.getStartDate()+"' and value(usr.VLDDT_END, '99999999') > '"+CalendarUtil.getToday()+"')) ");
					buf.append("\n 	and usr.PW_LST_CHG < '"+conditionDO.getStartDate()+"' ");
					buf.append("\n 	and usr.VLDDT_END > '"+conditionDO.getStartDate()+"' ");					

				}
			}
			else if(CodeConstants.LoginUserFlag.STOP_DATE.equals(conditionDO.getLoginUseFlag()))
			{
				if(!StringUtils.isEmpty(conditionDO.getStartDate()))
				{
					buf.append("\n 	and usr.VLDDT_END >= '"+conditionDO.getStartDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getEndDate()))
				{
					buf.append("\n 	and usr.VLDDT_END <= '"+conditionDO.getEndDate()+"' ");
				}
			}
			buf.append("\n ) AS list ");
		}



		return buf.toString();
	}
	/**
	 * 다운로드 진행상황를 목록 조회한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param String 조건
	 */
	public static String selectDownloadStatusListQuery(SystemManageConditionDO conditionDO, String searchFlag, String role)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	down.CART_NO, cart.cart_seq, down.REQ_USRID, ");
			buf.append("\n 		CASE WHEN UPPER(substr(down.REQ_USRID, 1, 1)) = 'D' THEN ( ");
			buf.append("\n 	      select OUT_USER_NM ");
			buf.append("\n 	      from DAS.OUTSIDER_INFO_TBL ");
			buf.append("\n 	      where OUT_USER_ID = down.REQ_USRID ");
			buf.append("\n 	      ) ");
			buf.append("\n 		ELSE (select USER_NM from DAS.ERP_COM_USER_TBL where USER_ID = down.REQ_USRID) ");
			buf.append("\n 	    END AS USER_NM, ");
			buf.append("\n 		CASE WHEN UPPER(substr(down.REQ_USRID, 1, 1)) = 'D' THEN ( ");
			buf.append("\n 	     '' )");
			buf.append("\n 		ELSE (select DEPT_NM from DAS.ERP_COM_USER_TBL where USER_ID = down.REQ_USRID) ");
			buf.append("\n 	    END AS DEPT_NM, ");
			buf.append("\n 	 down.REQ_DT, down.DOWN_SUBJ, down.VD_QLTY, ");
			String vdQltyCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_IMAGE_QUALITY, "down.VD_QLTY");
			buf.append(vdQltyCodeName + " AS VD_QLTY_NM, ");
			buf.append("\n 	down.ASP_RTO_CD, ");
			String aspRtoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_HOR_VER_RATIO, "down.ASP_RTO_CD");
			buf.append(aspRtoCodeName + " AS ASP_RTO_NM, ");
			//buf.append("\n 	wlog.DOWN_VOL as DOWN_SIZE, wlog.TOTAL_SIZE, down.CART_STAT, ");
			//buf.append("\n 	wlog.DOWN_VOL as DOWN_SIZE, cart.DURATION as TOTAL_SIZE, down.CART_STAT, ");
			buf.append("\n 	cart.DOWN_VOL as DOWN_SIZE, cart.DURATION as TOTAL_SIZE, ");
			buf.append("\n 	CASE WHEN cart.error_state = '' THEN down.CART_STAT ");
			buf.append("\n 	ELSE cart.error_state ");
			buf.append("\n 	END as CART_STAT, ");

			//String cartStatusCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_CART_STATUS, "down.CART_STAT");
			String columnName = "down.CART_STAT";
			buf.append("\n	CASE    ");
			buf.append("\n 		WHEN cart.error_state LIKE '7_' THEN (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.ERROR_STATUS+"' and code.SCL_CD = '0' || cart.error_state )");
			buf.append("\n 		WHEN "+columnName+" is null or  "+columnName+" = '' THEN ''  ");
			buf.append("\n 		ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_CART_STATUS+"' and code.SCL_CD = "+columnName+" ) ");
			buf.append("\n 	END	");
			buf.append("\n AS CART_STAT_NAME, ");
			buf.append("\n cart.master_id, ");
			//			buf.append("\n 	value(round((down.DOWN_SIZE/down.TOTAL_SIZE*100), 2), 0) AS PERCNT, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY down.REQ_DT desc) AS rownum ");
		}
		//		buf.append("\nents.CA from ");
		//		buf.append("\n 	( ");
		//		buf.append("\n select ");
		//		buf.append("\n 	cart.CART_NO,  ");
		//		buf.append("\n 	cart.REQ_USRID,  ");
		//		buf.append("\n 	cart.REQ_DT,  ");
		//		buf.append("\n 	cart.DOWN_SUBJ,  ");
		//		buf.append("\n 	cart.VD_QLTY,   ");
		//		buf.append("\n 	cart.ASP_RTO_CD,  ");
		//		buf.append("\n 	sum(contents.DOWN_VOL) AS DOWN_SIZE,  ");
		//		buf.append("\n 	sum(contents.TOTAL_SIZE) AS TOTAL_SIZE  ");
		//		buf.append("\n from  ");
		//		buf.append("\n ( ");
		//		buf.append("\n 	select  ");
		//		buf.append("\n 		cont.CART_NO, ");
		//		buf.append("\n 		wrk.DOWN_VOL, ");
		//		buf.append("\n 		wrk.TOTAL_SIZE, ");
		//		buf.append("\n 		wrk.REG_DT, ");
		//		buf.append("\n 		wrk.CTI_ID ");
		//		buf.append("\n 	from DAS.CART_CONT_TBL cont  ");
		//		buf.append("\n 		LEFT OUTER JOIN DAS.WORK_LOG_TBL wrk ON cont.CTI_ID = wrk.CTI_ID  ");
		//		buf.append("\n 	where value(wrk.REG_DT, '') = value((select max(REG_DT) from DAS.WORK_LOG_TBL where CTI_ID = wrk.CTI_ID and REQ_USRID = wrk.REQ_USRID), '') ");
		//		buf.append("\n ) contents, DAS.DOWN_CART_TBL cart  ");
		//		buf.append("\n where contRT_NO = cart.CART_NO  ");
		//		buf.append("\n 		and cont.CTI_ID = wrk.CTI_ID ");
		//		buf.append("\n 		and cart.REQ_USRID = wrk.REQ_USRID ");
		//		buf.append("\n 		and substr(cart.REQ_DT, 1, 8) = substr(wrk.REQ_TM, 1, 8) ");
		buf.append("\n from das.DOWN_CART_TBL down ");
		buf.append("\n , das.CART_CONT_TBL cart "); 
		buf.append("\n     where cart.CART_NO = down.CART_NO and down.cart_stat <> '001'  ");
		//		buf.append("\n left outer join das.WORK_LOG_TBL wlog "); 
		//		buf.append("\n     on wlog.REGRID = (rtrim(char(cart.CART_NO)) || '-' || rtrim(char(cart.CART_SEQ))) "); 
		//		buf.append("\n 		    and wlog.REQ_USRID = down.REQ_USRID "); 
		//		buf.append("\n where value(wlog.REG_DT, '') = value((select max(A.reg_dt) as reg_dt ");
		//		buf.append("\n                     from das.WORK_LOG_TBL A ");
		//		buf.append("\n  				   where A.REGRID = wlog.REGRID ");
		//		buf.append("\n 						  and A.REQ_USRID = wlog.REQ_USRID), '') ");

		// 슈퍼 유저나 콘텐츠 관리자라면 전체 유저 목록을 보여주라.!!
		if (!("007".equals(role) || "005".equals(role))) {
			if(!StringUtils.isEmpty(conditionDO.getUserId()))
			{
				buf.append("\n 		 and down.REQ_USRID = '"+conditionDO.getUserId()+"' ");
			}
		}
		if(!StringUtils.isEmpty(conditionDO.getStartDate()))
		{
			buf.append("\n 		and substr(down.REQ_DT, 1, 8) >= '"+conditionDO.getStartDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEndDate()))
		{
			buf.append("\n 		and substr(down.REQ_DT, 1, 8) <= '"+conditionDO.getEndDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getSearchText()))
		{
			buf.append("\n 		and down.DOWN_SUBJ like '%"+conditionDO.getSearchText()+"%' ");
		}

		return buf.toString();
	}
	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 *  @param searchFlag 조건
	 */
	public static String selectProgramListQuery(SystemManageConditionDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 		count(1)                                          													\n");
			buf.append("\n from (select ");
		}

		/*
			buf.append("\n 	PGM_ID, ");
			buf.append("\n 	PGM_CD, ");
			buf.append("\n 	PGM_NM, ");
			buf.append("\n 	CTGR_L_CD, ");
			buf.append("\n 	CTGR_M_CD, ");
			buf.append("\n 	CTGR_S_CD, ");
			buf.append("\n 	BRD_BGN_DD, ");
			buf.append("\n 	BRD_END_DD, ");
			buf.append("\n 	PRD_DEPT_NM, ");
			buf.append("\n 	SCHD_PGM_NM, ");
			buf.append("\n 	AWARD_HSTR, ");
			buf.append("\n 	PILOT_YN, ");
//			buf.append("\n 	MOD_END_YN, ");
			buf.append("\n	ROW_NUMBER() OVER(ORDER BY PGM_ID) AS rownum ");
		 */
		buf.append("\n  A.*, ROW_NUMBER() OVER(ORDER BY " + (condition.getSort().equals("") ? "PGM_NM" : condition.getSort()) + " " + condition.getAsceding() + ") AS rownum from ( ");
		buf.append("\n  select ");
		buf.append("\n  concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) as PGM_CD, ");
		buf.append("\n 	PGM.PGM_ID , ");
		//buf.append("\n 	PGM.PGM_CD, ");
		buf.append("\n  CASE WHEN PGM.PGM_NM IS NULL OR PGM.PGM_NM = '' THEN ERP.PGM_NM ");
		buf.append("\n      ELSE PGM.PGM_NM END AS PGM_NM, ");
		buf.append("\n  PGM.CTGR_L_CD, ");
		buf.append("\n  PGM.CTGR_M_CD, ");
		buf.append("\n  PGM.CTGR_S_CD, ");
		buf.append("\n  CASE WHEN PGM.BRD_BGN_DD IS NULL OR PGM.BRD_BGN_DD = '' THEN ERP.BRD_BGN_DD ");
		buf.append("\n      ELSE PGM.BRD_BGN_DD END AS BRD_BGN_DD, ");
		buf.append("\n  CASE WHEN PGM.BRD_END_DD IS NULL OR PGM.BRD_END_DD = '' THEN ERP.BRD_END_DD ");
		buf.append("\n      ELSE PGM.BRD_END_DD END AS BRD_END_DD, ");
		buf.append("\n  CASE WHEN PGM.PRD_DEPT_NM IS NULL OR PGM.PRD_DEPT_NM = '' THEN ERP.PRDT_DEPT_NM ");
		buf.append("\n      ELSE PGM.PRD_DEPT_NM END AS PRD_DEPT_NM, ");
		buf.append("\n  PGM.SCHD_PGM_NM, ");
		buf.append("\n  PGM.AWARD_HSTR, ");
		buf.append("\n  CASE WHEN PGM.PILOT_YN IS NULL OR PGM.PILOT_YN = '' THEN ERP.PILOT_YN ");
		buf.append("\n      ELSE PGM.PILOT_YN END AS PILOT_YN ");
		//			buf.append("\n 	MOD_END_YN, ");
		//			buf.append("\n	ROW_NUMBER() OVER(ORDER BY PGM_ID) AS rownum ");

		//buf.append("\n from DAS.PGM_INFO_TBL ");
		//buf.append("\n where PGM_NM LIKE '%" +condition.getSearchText()+ "%' ");	

		buf.append("\n FROM DAS.E_PGMMST_TBL ERP LEFT outer Join DAS.PGM_INFO_TBL PGM ON concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) = PGM.PGM_CD where concat(erp.MEDIA, erp.CHAN_CD) in ('TT','ZZ','DT') ");
		buf.append("\n union all ");
		buf.append("\n  select pgm_cd, pgm_id, pgm_nm, ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, BRD_BGN_DD, BRD_END_DD, PRD_DEPT_NM, SCHD_PGM_NM, AWARD_HSTR, PILOT_YN from das.PGM_INFO_TBL where PGM_CD LIKE 'ZZ%' ");
		buf.append("\n ) A ");

		buf.append("\n where PGM_NM LIKE '%" +condition.getSearchText()+ "%' ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n ) B ");
		}
		//		if(!StringUtils.isEmpty(condition.getMod_end_yn()))
		//		{
		//			buf.append("\n 		and MOD_END_YN = '"+condition.getMod_end_yn()+"' ");
		//		}
		return buf.toString();
	}		
	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_nm 프로그램명
	 */
	public static String selectParentsInfoQuery(String pgm_nm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n  A.* from ( ");
		buf.append("\n  select ");
		buf.append("\n  concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) as PGM_CD, ");
		buf.append("\n 	PGM.PGM_ID , ");

		buf.append("\n  CASE WHEN PGM.PGM_NM IS NULL OR PGM.PGM_NM = '' THEN ERP.PGM_NM ");
		buf.append("\n      ELSE PGM.PGM_NM END AS PGM_NM ");


		buf.append("\n FROM DAS.E_PGMMST_TBL ERP LEFT outer Join DAS.PGM_INFO_TBL PGM ON concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) = PGM.PGM_CD where concat(erp.MEDIA, erp.CHAN_CD) in ('TT','ZZ','DT') ");
		buf.append("\n union all ");
		buf.append("\n  select pgm_cd, pgm_id, pgm_nm from das.PGM_INFO_TBL  ");
		buf.append("\n ) A ");

		buf.append("\n where PGM_NM LIKE '%" +pgm_nm+ "%' ");
		buf.append("\n AND PGM_ID IS NOT NULL ");
		return buf.toString();
	}		
	/**
	 * 매체변환 오류내역 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectErrorListQuery(SystemManageConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();


		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n SELECT ");
			buf.append("\n 		count(1) ");
			buf.append("\n from DAS.ERROR_RGST_TBL ERR ");
			buf.append("\n 		LEFT OUTER JOIN DAS.METADAT_MST_TBL META ON META.MASTER_ID = ERR.MASTER_ID "); 
			buf.append("\n 		LEFT OUTER JOIN DAS.PGM_INFO_TBL PGM ON PGM.PGM_ID = META.PGM_ID ");
			if(!StringUtils.isEmpty(conditionDO.getStartDate()))
			{
				buf.append("\n 	WHERE ERR.REG_DT >= '"+conditionDO.getStartDate()+"000000"+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getEndDate()))
			{
				buf.append("\n 	and ERR.REG_DT <= '"+conditionDO.getEndDate()+"999999"+"' ");
			}
		}
		else
		{

			buf.append("\n select ");
			buf.append("\n 		ERR.MASTER_ID, ");
			buf.append("\n 		ERR.WRT, ");
			buf.append("\n 		ERR.ER_CONT, ");
			buf.append("\n 		ERR.REACT_CONT, ");
			buf.append("\n		SUBSTR(ERR.REG_DT, 1, 8) AS REG_DT, ");
			buf.append("\n		ERR.REGRID, ");
			buf.append("\n		SUBSTR(ERR.MOD_DT, 1, 8) AS MOD_DT, ");
			buf.append("\n		ERR.MODRID, ");
			buf.append("\n		PGM.PGM_NM, ");
			buf.append("\n		META.REQ_CD, ");
			buf.append("\n		META.EPIS_NO, ");
			buf.append("\n		ROW_NUMBER() OVER(ORDER BY ERR.REG_DT DESC) AS rownum ");
			buf.append("\n from DAS.ERROR_RGST_TBL ERR ");
			buf.append("\n 		LEFT OUTER JOIN DAS.METADAT_MST_TBL META ON META.MASTER_ID = ERR.MASTER_ID "); 
			buf.append("\n 		LEFT OUTER JOIN DAS.PGM_INFO_TBL PGM ON PGM.PGM_ID = META.PGM_ID ");
			if(!StringUtils.isEmpty(conditionDO.getStartDate()))
			{
				buf.append("\n 	WHERE ERR.REG_DT >= '"+conditionDO.getStartDate()+"000000"+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getEndDate()))
			{
				buf.append("\n 	and ERR.REG_DT <= '"+conditionDO.getEndDate()+"999999"+"' ");
			}
		}

		return buf.toString();
	}
	/**
	 * I사진 다운로드 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectPhotoDownListQuery(SystemManageConditionDO conditionDO, String searchFlag) 
	{

		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
		}
		else
		{
			buf.append("\n 	A.PHOT_ID, ");
			buf.append("\n 	A.REQ_ID, ");
			buf.append("\n 	B.USER_NM, ");
			buf.append("\n 	A.SUBJ, ");
			buf.append("\n 	A.req_dt, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY A.REQ_DT DESC) AS rownum ");
		}
		buf.append("\n from DAS.PHOT_DOWN_TBL A, DAS.ERP_COM_USER_TBL B ");
		buf.append("\n where A.REQ_ID = B.USER_ID ");

		if(!StringUtils.isEmpty(conditionDO.getUserId()))
		{
			buf.append("\n 	and A.REQ_ID = '"+conditionDO.getUserId()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getPhot_id()))
		{
			buf.append("\n 	and A.PHOT_ID = "+conditionDO.getPhot_id()+" ");
		}
		if(!StringUtils.isEmpty(conditionDO.getUserNm()))
		{
			buf.append("\n 	and B.USER_NM like '%"+conditionDO.getUserNm()+"%'  ");
		}
		if(!StringUtils.isEmpty(conditionDO.getStartDate()))
		{
			buf.append("\n 	and substr(A.REQ_DT, 1, 8) >= '"+conditionDO.getStartDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getEndDate()))
		{
			buf.append("\n 	and substr(A.REQ_DT, 1, 8) <= '"+conditionDO.getEndDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getSubj()))
		{
			buf.append("\n 	and A.SUBJ like '%"+conditionDO.getSubj()+"%'  ");
		}

		return buf.toString();
	}


	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition2 조회조건을 포함하고 있는 DataObject
	 */
	public static String selectNewPgmListQuery(ProgramInfoDO condition2)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\nSELECT * FROM (                                                                                             ");
		buf.append("\n	SELECT                                                                                                    ");
		buf.append("\n		PGM_CD               AS ORDER_STD                                                                       ");
		buf.append("\n	   ,PGM_ID               AS PGM_ID                                                                        ");
		buf.append("\n	   ,PGM_CD               AS PGM_CD                                                                        ");
		buf.append("\n	   ,value(PARENTS_CD,'') AS PARENTS_CD                                                                    ");
		buf.append("\n	   ,MEDIA_CD             AS MEDIA_CD                                                                      ");
		buf.append("\n	   ,CHAN_CD              AS CHAN_CD                                                                       ");
		buf.append("\n	   ,PGM_NM               AS PGM_NM                                                                        ");
		buf.append("\n	   ,CTGR_L_CD            AS CTGR_L_CD                                                                     ");
		buf.append("\n	   ,CTGR_M_CD            AS CTGR_M_CD                                                                     ");
		buf.append("\n	   ,CTGR_S_CD            AS CTGR_S_CD                                                                     ");
		buf.append("\n	   ,BRD_BGN_DD           AS BRD_BGN_DD                                                                    ");
		buf.append("\n	   ,BRD_END_DD           AS BRD_END_DD                                                                    ");
		buf.append("\n	   ,PRD_DEPT_NM          AS PRD_DEPT_NM                                                                   ");
		buf.append("\n	   ,SCHD_PGM_NM          AS SCHD_PGM_NM                                                                   ");
		buf.append("\n	   ,AWARD_HSTR           AS AWARD_HSTR                                                                    ");
		buf.append("\n	   ,PILOT_YN             AS PILOT_YN                                                                      ");
		buf.append("\n	   ,USE_YN               AS USE_YN                                                                        ");
		buf.append("\n	FROM PGM_INFO_TBL                                                                                         ");
		buf.append("\n	WHERE 1=1                                                                                                 ");
		buf.append("\n		AND (PGM_CD IS NOT NULL AND RTRIM(PGM_CD) <> '')                                                        ");
		buf.append("\n	UNION ALL                                                                                                 ");
		buf.append("\n	SELECT                                                                                                    ");
		buf.append("\n	   ''               	 	AS ORDER_STD                                                                      ");
		buf.append("\n	   ,0               	 	AS PGM_ID                                                                         ");
		buf.append("\n	   ,MEDIA||CHAN_CD||PGM_CD  AS PGM_CD                                                                     ");
		buf.append("\n	   ,'' 						AS PARENTS_CD                                                                           ");
		buf.append("\n	   ,MEDIA             		AS MEDIA_CD                                                                     ");
		buf.append("\n	   ,CHAN_CD              	AS CHAN_CD                                                                      ");
		buf.append("\n	   ,PGM_NM               	AS PGM_NM                                                                       ");
		buf.append("\n	   ,''             			AS CTGR_L_CD                                                                      ");
		buf.append("\n	   ,''             			AS CTGR_M_CD                                                                      ");
		buf.append("\n	   ,''             			AS CTGR_S_CD                                                                      ");
		buf.append("\n	   ,BRD_BGN_DD           	AS BRD_BGN_DD                                                                   ");
		buf.append("\n	   ,BRD_END_DD           	AS BRD_END_DD                                                                   ");
		buf.append("\n	   ,PRDT_DEPT_NM          	AS PRD_DEPT_NM                                                                ");
		buf.append("\n	   ,''           			AS SCHD_PGM_NM                                                                      ");
		buf.append("\n	   ,''            			AS AWARD_HSTR                                                                     ");
		buf.append("\n	   ,''              		AS PILOT_YN                                                                       ");
		buf.append("\n	   ,''               		AS USE_YN                                                                         ");
		buf.append("\n	FROM DAS.ERP_E_PGMMST_TBL                                                                                     ");
		buf.append("\n	WHERE 1=1                                                                                                 ");
		buf.append("\n		AND PGM_CD NOT IN (SELECT  SUBSTR(PGM_CD, 3, 6) FROM PGM_INFO_TBL)                                      ");
		buf.append("\n	    AND ((MEDIA='T' AND CHAN_CD='T') OR ((MEDIA='Z' AND CHAN_CD='Z')) OR ((MEDIA='D' AND CHAN_CD='T')))   ");
		buf.append("\n	    AND (PGM_CD IS NOT NULL AND RTRIM(PGM_CD) <> '')                                                      ");
		buf.append("\n) a                                                                                                         ");
		buf.append("\nWHERE 1=1                                                                                                   ");
		
		if(!condition2.getUse_yn().equals("")){
			buf.append("\n    AND upper(a.use_yn) = '"+condition2.getUse_yn().toUpperCase()+"'                                                                ");
		}
		if(condition2.getSRCH_TYPE().equals("0")){
			buf.append("\n	  AND (a.pgm_nm LIKE ? OR a.pgm_nm LIKE ?)                                                                ");
		}
		if(condition2.getSRCH_TYPE().equals("1") || condition2.getSRCH_TYPE().equals("2")){
			buf.append("\n    AND (a.pgm_cd LIKE ? OR a.parents_cd LIKE ? OR a.pgm_cd IN (SELECT parents_cd FROM PGM_INFO_TBL WHERE pgm_cd LIKE ?))");
		}
		buf.append("\nORDER BY a.ORDER_STD, a.PGM_CD                                                                             ");
		
		return buf.toString();
	}
	
	@Deprecated
	public static String selectPgmListQuery(ProgramInfoDO condition2)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n   A.PGM_ID   ");
		buf.append("\n   ,A.PGM_CD    ");
		buf.append("\n   ,value(A.PARENTS_CD,'') AS PARENTS_CD ");
		buf.append("\n   ,A.MEDIA_CD ");
		buf.append("\n 	 ,A.CHAN_CD ");
		buf.append("\n   ,A.PGM_NM   ");
		buf.append("\n   ,A.CTGR_L_CD ");
		buf.append("\n   ,A.CTGR_M_CD ");
		buf.append("\n   ,A.CTGR_S_CD ");
		buf.append("\n   ,A.BRD_BGN_DD ");
		buf.append("\n   ,A.BRD_END_DD ");
		buf.append("\n   ,A.PRD_DEPT_NM ");
		buf.append("\n   ,A.SCHD_PGM_NM ");
		buf.append("\n   ,A.AWARD_HSTR ");			
		buf.append("\n   ,A.PILOT_YN ");
		buf.append("\n   ,A.USE_YN ");
		buf.append("\n  FROM (  ");
		buf.append("\n  SELECT ");

		buf.append("\n   PGM_CD               AS ORDER_STD ");
		buf.append("\n   ,PGM_ID               AS PGM_ID ");
		buf.append("\n   ,PGM_CD               AS PGM_CD   ");
		buf.append("\n   ,value(PARENTS_CD,'') AS PARENTS_CD ");
		buf.append("\n 	 ,MEDIA_CD             AS MEDIA_CD ");
		buf.append("\n   ,CHAN_CD              AS CHAN_CD ");
		buf.append("\n   ,PGM_NM               AS PGM_NM ");
		buf.append("\n   ,CTGR_L_CD            AS CTGR_L_CD ");
		buf.append("\n  ,CTGR_M_CD            AS CTGR_M_CD ");
		buf.append("\n  ,CTGR_S_CD            AS CTGR_S_CD ");
		buf.append("\n  ,BRD_BGN_DD           AS BRD_BGN_DD ");
		buf.append("\n  ,BRD_END_DD           AS BRD_END_DD ");
		buf.append("\n  ,PRD_DEPT_NM          AS PRD_DEPT_NM ");
		buf.append("\n  ,SCHD_PGM_NM          AS SCHD_PGM_NM ");			
		buf.append("\n  ,AWARD_HSTR           AS AWARD_HSTR ");
		buf.append("\n  ,PILOT_YN             AS PILOT_YN ");
		buf.append("\n  ,USE_YN               AS USE_YN   ");
		buf.append("\n  FROM PGM_INFO_TBL ");

		buf.append("\n   WHERE (Case when rtrim(PARENTS_CD)='' or PARENTS_CD is null then 'NULL' ELSE PARENTS_CD  END ) = 'NULL' ");
		buf.append("\n   AND ( 1=1 ");

		if(!condition2.getUse_yn().equals("")){
			buf.append("\n AND USE_YN='"+condition2.getUse_yn()+"' ");
		}

		if(condition2.getSRCH_TYPE().equals("0")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			
			//String Big = condition2.getPgmNm().toUpperCase();
			//String small = condition2.getPgmNm().toLowerCase();

			buf.append("\n AND ( PGM_NM LIKE ? ");
			buf.append("\n or PGM_NM LIKE ? ) ");
		}
		if(condition2.getSRCH_TYPE().equals("1")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			String Big = condition2.getPgmCd().toUpperCase();
			String small = condition2.getPgmCd().toLowerCase();



			buf.append("\n AND (PGM_CD LIKE '%"+Big+"%' ");
			buf.append("\n or PARENTS_CD LIKE '%"+Big+"%' ");	
			buf.append("\n or  PGM_CD IN ( SELECT PARENTS_CD FROM PGM_INFO_TBL WHERE PGM_CD LIKE '%"+Big+"%' ) )");	
		}
		if(condition2.getSRCH_TYPE().equals("2")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			String Big = condition2.getParents_cd().toUpperCase();
			String small = condition2.getParents_cd().toLowerCase();

			buf.append("\n AND (PGM_CD LIKE '%"+Big+"%' ");
			buf.append("\n or PARENTS_CD LIKE '%"+Big+"%' ");	
			buf.append("\n or  PGM_CD IN ( SELECT PARENTS_CD FROM PGM_INFO_TBL WHERE PGM_CD LIKE '%"+Big+"%' ) )");	
		}
		buf.append("\n   and PGM_CD <> '' ");	
		buf.append("\n  ) union all");	



		buf.append("\n  SELECT ");

		buf.append("\n   PARENTS_CD               AS ORDER_STD ");
		buf.append("\n   ,PGM_ID               AS PGM_ID ");
		buf.append("\n   ,PGM_CD               AS PGM_CD   ");
		buf.append("\n   ,value(PARENTS_CD,'') AS PARENTS_CD ");
		buf.append("\n 	 ,MEDIA_CD             AS MEDIA_CD ");
		buf.append("\n   ,CHAN_CD              AS CHAN_CD ");
		buf.append("\n   ,PGM_NM               AS PGM_NM ");
		buf.append("\n   ,CTGR_L_CD            AS CTGR_L_CD ");
		buf.append("\n  ,CTGR_M_CD            AS CTGR_M_CD ");
		buf.append("\n  ,CTGR_S_CD            AS CTGR_S_CD ");
		buf.append("\n  ,BRD_BGN_DD           AS BRD_BGN_DD ");
		buf.append("\n  ,BRD_END_DD           AS BRD_END_DD ");
		buf.append("\n  ,PRD_DEPT_NM          AS PRD_DEPT_NM ");
		buf.append("\n  ,SCHD_PGM_NM          AS SCHD_PGM_NM ");			
		buf.append("\n  ,AWARD_HSTR           AS AWARD_HSTR ");
		buf.append("\n  ,PILOT_YN             AS PILOT_YN ");
		buf.append("\n  ,USE_YN               AS USE_YN   ");
		buf.append("\n  FROM PGM_INFO_TBL ");

		buf.append("\n   WHERE (Case when rtrim(PARENTS_CD)='' or PARENTS_CD is null then 'NULL' ELSE PARENTS_CD  END ) <> 'NULL'   ");
		buf.append("\n   AND ( 1=1 ");

		if(!condition2.getUse_yn().equals("")){
			buf.append("\n AND USE_YN='"+condition2.getUse_yn()+"' ");
		}

		if(condition2.getSRCH_TYPE().equals("0")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			String Big = condition2.getPgmNm().toUpperCase();
			String small = condition2.getPgmNm().toLowerCase();

			logger.debug("big     "+Big);
			logger.debug("small   "+small);
			buf.append("\n AND ( PGM_NM LIKE ? ");
			buf.append("\n or PGM_NM LIKE ? ) ");
		}
		if(condition2.getSRCH_TYPE().equals("1")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			String Big = condition2.getPgmCd().toUpperCase();
			String small = condition2.getPgmCd().toLowerCase();


			buf.append("\n AND (PGM_CD LIKE '%"+Big+"%' ");
			buf.append("\n or PARENTS_CD LIKE '%"+Big+"%' ");	
			buf.append("\n or  PGM_CD IN ( SELECT PARENTS_CD FROM PGM_INFO_TBL WHERE PGM_CD LIKE '%"+Big+"%' ) )");	
		}
		if(condition2.getSRCH_TYPE().equals("2")){
			//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
			String Big = condition2.getParents_cd().toUpperCase();
			String small = condition2.getParents_cd().toLowerCase();

			buf.append("\n AND (PGM_CD LIKE '%"+Big+"%' ");
			buf.append("\n or PARENTS_CD LIKE '%"+Big+"%' ");	
			buf.append("\n or  PGM_CD IN ( SELECT PARENTS_CD FROM PGM_INFO_TBL WHERE PGM_CD LIKE '%"+Big+"%' ) )");	
		}
		buf.append("\n   and PGM_CD <> '' ");	


		buf.append("\n  ) ");	
		if(condition2.getUse_yn().equals("")){
			buf.append("\n   union all");	

			buf.append("\n  SELECT ");

			buf.append("\n   ''               AS ORDER_STD  ");
			buf.append("\n   ,0               AS PGM_ID ");
			buf.append("\n   ,media||chan_cd||PGM_CD               AS PGM_CD   ");
			buf.append("\n   ,'' AS PARENTS_CD  ");
			buf.append("\n 	 ,media             AS MEDIA_CD ");
			buf.append("\n   ,CHAN_CD              AS CHAN_CD ");
			buf.append("\n   ,PGM_NM               AS PGM_NM   ");
			buf.append("\n   ,''             AS CTGR_L_CD ");
			buf.append("\n  ,''             AS CTGR_M_CD ");
			buf.append("\n  ,''             AS CTGR_S_CD ");
			buf.append("\n  ,BRD_BGN_DD           AS BRD_BGN_DD ");
			buf.append("\n  ,BRD_END_DD           AS BRD_END_DD ");
			buf.append("\n  ,PRDt_DEPT_NM          AS PRD_DEPT_NM ");
			buf.append("\n  ,''           AS SCHD_PGM_NM ");			
			buf.append("\n  ,''            AS AWARD_HSTR ");
			buf.append("\n  ,''              AS PILOT_YN ");
			buf.append("\n  ,' '               AS USE_YN   ");
			buf.append("\n  FROM DAS.E_PGMMST_TBL ");

			buf.append("\n   WHERE   ");
			buf.append("\n     1=1 ");
			buf.append("\n  and pgm_cd not in (select  substr(pgm_cd,3,6) from PGM_INFO_TBL )  ");		 			 	
			/*if(!condition2.getUse_yn().equals("")){
buf.append("\n AND USE_YN='"+condition2.getUse_yn()+"' ");
}*/

			if(condition2.getSRCH_TYPE().equals("0")){
				//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
				String Big = condition2.getPgmNm().toUpperCase();
				String small = condition2.getPgmNm().toLowerCase();

				logger.debug("big     "+Big);
				logger.debug("small   "+small);
				buf.append("\n AND ( PGM_NM LIKE ? ");
				buf.append("\n or PGM_NM LIKE ? ) ");
			}
			if(condition2.getSRCH_TYPE().equals("1")){
				//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
				String Big = condition2.getPgmCd().toUpperCase();
				String small = condition2.getPgmCd().toLowerCase();


				buf.append("\n AND (PGM_CD LIKE '%"+Big+"%') ");

			}
			if(condition2.getSRCH_TYPE().equals("2")){
				//대소문자 구별없이 조회하기위하여 소문자인경우와 대문자인경우 둘다 조회한다
				String Big = condition2.getParents_cd().toUpperCase();
				String small = condition2.getParents_cd().toLowerCase();

				buf.append("\n AND (PGM_CD LIKE '%"+Big+"%' ) ");
				//buf.append("\n or PARENTS_CD LIKE '%"+Big+"%' ");	
				//	buf.append("\n or  PGM_CD IN ( SELECT PARENTS_CD FROM PGM_INFO_TBL WHERE PGM_CD LIKE '%"+Big+"%' ) )");	
			}
			//buf.append("\n  and ((media='T' AND CHAN_CD='T')OR((media='Z' AND CHAN_CD='Z'))) ");
			buf.append("\n   and ((media='T' AND CHAN_CD='T')OR((media='Z' AND CHAN_CD='Z'))OR((media='D' AND CHAN_CD='T')))  ");	
			buf.append("\n   and PGM_CD <> '' ");	
		}
		buf.append("\n   ) A");	
		buf.append("\n  ORDER BY " );	 
		buf.append("\n  A.ORDER_STD ,A.PGM_CD ");	













		return buf.toString();
	}		






	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_cd 프로그램코드
	 */
	public static String selectPgmInfoQuery(String pgm_cd)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n  A.*, ROW_NUMBER() OVER(ORDER BY PGM_NM ) AS rownum from ( ");
		buf.append("\n  select ");
		buf.append("\n  concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) as PGM_CD, ");
		buf.append("\n 	PGM.PGM_ID , ");
		//buf.append("\n 	PGM.PGM_CD, ");
		buf.append("\n  CASE WHEN PGM.PGM_NM IS NULL OR PGM.PGM_NM = '' THEN ERP.PGM_NM ");
		buf.append("\n      ELSE PGM.PGM_NM END AS PGM_NM, ");
		buf.append("\n  PGM.CTGR_L_CD, ");
		buf.append("\n  PGM.CTGR_M_CD, ");
		buf.append("\n  PGM.CTGR_S_CD, ");
		buf.append("\n  CASE WHEN PGM.BRD_BGN_DD IS NULL OR PGM.BRD_BGN_DD = '' THEN ERP.BRD_BGN_DD ");
		buf.append("\n      ELSE PGM.BRD_BGN_DD END AS BRD_BGN_DD, ");
		buf.append("\n  CASE WHEN PGM.BRD_END_DD IS NULL OR PGM.BRD_END_DD = '' THEN ERP.BRD_END_DD ");
		buf.append("\n      ELSE PGM.BRD_END_DD END AS BRD_END_DD, ");
		buf.append("\n  CASE WHEN PGM.PRD_DEPT_NM IS NULL OR PGM.PRD_DEPT_NM = '' THEN ERP.PRDT_DEPT_NM ");
		buf.append("\n      ELSE PGM.PRD_DEPT_NM END AS PRD_DEPT_NM, ");
		buf.append("\n  PGM.SCHD_PGM_NM, ");
		buf.append("\n  PGM.AWARD_HSTR, ");
		buf.append("\n  CASE WHEN PGM.PILOT_YN IS NULL OR PGM.PILOT_YN = '' THEN ERP.PILOT_YN ");
		buf.append("\n      ELSE PGM.PILOT_YN END AS PILOT_YN ");
		//			buf.append("\n 	MOD_END_YN, ");
		//			buf.append("\n	ROW_NUMBER() OVER(ORDER BY PGM_ID) AS rownum ");

		//buf.append("\n from DAS.PGM_INFO_TBL ");
		//buf.append("\n where PGM_NM LIKE '%" +condition.getSearchText()+ "%' ");	

		buf.append("\n FROM DAS.E_PGMMST_TBL ERP LEFT outer Join DAS.PGM_INFO_TBL PGM ON concat(concat(ERP.MEDIA, ERP.CHAN_CD), ERP.PGM_CD) = PGM.PGM_CD where concat(erp.MEDIA, erp.CHAN_CD) in ('TT','ZZ','DT') ");
		buf.append("\n union all ");
		buf.append("\n  select pgm_cd, pgm_id, pgm_nm, ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, BRD_BGN_DD, BRD_END_DD, PRD_DEPT_NM, SCHD_PGM_NM, AWARD_HSTR, PILOT_YN from das.PGM_INFO_TBL where PGM_CD LIKE 'ZZ%' ");
		buf.append("\n ) A ");

		buf.append("\n where PGM_CD = '" +pgm_cd+ "' ");

		//		if(!StringUtils.isEmpty(condition.getMod_end_yn()))
		//		{
		//			buf.append("\n 		and MOD_END_YN = '"+condition.getMod_end_yn()+"' ");
		//		}
		return buf.toString();
	}



	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 제목
	 */
	public static String selectPgmQuery(String title)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               

		buf.append("\n   PGM_ID, PGM_NM ");
		buf.append("\n  FROM DAS.PGM_INFO_TBL ");
		buf.append("\n  WHERE PGM_NM LIKE '%"+title+"%'");

		return buf.toString();
	}



	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 프로그램명
	 * @return List
	 */
	public static String selectPgmQuery2(String title)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select distinct ");               

		buf.append("\n   program_name ");
		buf.append("\n  ,program_Code ");
		buf.append("\n  from PDS_PGMINFO_TBL ");
		buf.append("\n  WHERE program_name LIKE '%"+title+"%'");
		buf.append("\n   order by program_name asc");
		buf.append("\n   with ur ");
		return buf.toString();
	}




	/*	public static String selectParentsQuery(String title)
	{
	//SystemManageConditionDO condition = new SystemManageConditionDO();
	StringBuffer buf = new StringBuffer();
	buf.append("\n select ");               

		buf.append("\n  DISTINCT PGM_ID, TITLE ");
		buf.append("\n  FROM DAS.PGM_INFO_TBL ");
		buf.append("\n  WHERE TITLE LIKE '%"+title+"%'");

	return buf.toString();
	}
	 */
	/**
	 * 계열사 수신서버 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 * @return
	 */
	public static String selectSubsiServerListQuery(SubsiInfoDO condition, String searchFlag)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append(" 		count(1)                                          													\n");
			buf.append(" from (select ");
		}


		buf.append("\n  SUBSI_ID,  ");
		buf.append("\n  SUBSI_NM,  ");
		buf.append("\n  RECEVE_SERVER_IP,  ");
		buf.append("\n  RECEVE_SERVER_NM,  ");
		buf.append("\n  RECEVE_SERVER_PORT,  ");
		buf.append("\n  PASSWORD,  ");
		buf.append("\n  PATH , ");
		buf.append("\n  ADMIN_ID , ");
		buf.append("\n  ROW_NUMBER() OVER(ORDER BY RECEVE_SERVER_PORT) AS rownum");
		buf.append("\n  from DAS.SUBSI_INFO_TBL ");
		if(!condition.getSubsi_id().equals("")){
			buf.append("\n  WHERE SUBSI_ID  = '" +condition.getSubsi_id()+"' ");
		}
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n ) B ");
		}

		return buf.toString();
	}		

	/**
	 * 프로그램 복본관리를 조회한다.(다중조회)
	 * @param  condition                                                                                                                                                                                              
	 *  @param  searchFlag 조건           
	 */
	public static String selectCopyListQuery(CopyInfoDO condition, String searchFlag)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n  COPY.CMS_PGM_ID, ");
		buf.append("\n    PGM.PROGRAM_NAME,  ");
		buf.append("\n  COPY.REG_DT,  ");
		buf.append("\n  COPY.REG_ID, ");
		buf.append("\n  COPY.COPY_YN  ");
		buf.append("\n   FROM COPY_INFO_TBL COPY  ");
		buf.append("\n  LEFT JOIN   (SELECT PROGRAM_NAME,PROGRAM_CODE FROM PDS_PGMINFO_TBL GROUP BY PROGRAM_NAME,PROGRAM_CODE)  PGM ON PGM.PROGRAM_CODE = COPY.CMS_PGM_ID       ");


		if(!condition.getStart_reg_dt().equals("") || !condition.getEnd__reg_dt().equals("") || !condition.getCopy_yn().equals("")  || !condition.getTitle().equals("")){
			buf.append("\n where   ");	



			if(!condition.getStart_reg_dt().equals("")){


				buf.append("\n  COPY.REG_DT  >='" +condition.getStart_reg_dt()+"000000' ");
			}


			if( !condition.getEnd__reg_dt().equals("") ){
				if(!condition.getStart_reg_dt().equals("")){buf.append("\n and   ");	}	

				buf.append("\n  COPY.REG_DT <= '" +condition.getEnd__reg_dt()+"999999' ");
			}		

			if(!condition.getCopy_yn().equals("")){
				if(!condition.getStart_reg_dt().equals("") || !condition.getEnd__reg_dt().equals("")){buf.append("\n and   ");	}
				buf.append("\n   COPY.COPY_YN = '" +condition.getCopy_yn()+"' ");
			}


			if(!condition.getTitle().equals("")){
				if(!condition.getCopy_yn().equals("") || !condition.getStart_reg_dt().equals("") || !condition.getEnd__reg_dt().equals("")){buf.append("\n and   ");	}
				buf.append("\n   pgm.PROGRAM_NAME LIKE '%" +condition.getTitle()+"%' ");
			}



		}
		//buf.append("\n and    ");	

		return buf.toString();
	}		










	/**
	 * 이용현황 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectUseInfoList(UseInfoDO condition, String searchFlag)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n   distinct  CODE.DESC,   ");
			buf.append("\n   case when  META.ctgr_l_cd='100' then META.TITLE  ");
			buf.append("\n   when  META.ctgr_l_cd ='200' and  META.pgm_id !=0 then pgm.pgm_nm  ");
			buf.append("\n   else META.TITLE   end as title  ");
			buf.append("\n  ,  META.BRD_DD, META.REG_DT,  ");
			if(condition.getScl_cd().equals(200)){
				buf.append("\n   META.BRD_DD,  ");
			}else{
				buf.append("\n   META.FM_DT,  ");
			}
			buf.append("\n   META.CHENNEL_CD,  ");
			buf.append("\n   case when loc.backup_status='C' THEN 'Y' ELSE 'N' END AS BACKUP_YN,  ");
			buf.append("\n   case when LOC.copy_status='C' THEN 'Y' ELSE 'N' END AS copy_object_yn,  ");
			buf.append("\n   case when LOC.cyn='Y' THEN 'Y' ELSE 'N' END AS old_copy_object_yn,  ");
			buf.append("\n   META.BRD_LENG, META.MASTER_ID,value(meta.epis_no, '0') as epis_no    ");
			buf.append("\n  ,value(  CART.CNT, 0 ) AS cnt  ");
			buf.append("\n  ,ROW_NUMBER() OVER(ORDER BY  META.master_id) AS rownum");
		}
		buf.append("\n     FROM  METADAT_MST_TBL META  ");
		buf.append("\n    inner join CODE_TBL CODE on CODE.SCL_CD=META.CTGR_L_CD  AND CLF_CD = 'P002' ");
		buf.append("\n       INNER JOIN (select master_id,ct_id,del_dd from CONTENTS_MAPP_TBL group by master_id,ct_id,del_dd) MAP ON MAP.MASTER_ID = META.MASTER_ID  ");
		buf.append("\n      INNER JOIN (SELECT CT_ID,CTI_FMT ,CTI_ID FROM CONTENTS_INST_TBL group by CT_ID, CTI_FMT,CTI_ID ) INST ON INST.CT_ID = MAP.CT_ID AND INST.CTI_FMT LIKE '%10%' ");
		buf.append("\n     left outer JOIN  (select copy_status,CTI_ID ,backup_status ,cyn FROM CONTENTS_LOC_TBL GROUP BY copy_status,CTI_ID, backup_status,cyn) LOC ON LOC.CTI_ID = INST.CTI_ID  ");
		buf.append("\n     inner join contents_tbl con on map.ct_id = con.CT_ID   and con.CT_ID =meta.RPIMG_CT_ID ");
		buf.append("\n     left outer join  (SELECT COUNT(MASTER_ID) AS CNT,MASTER_id FROM (  ");
		buf.append("\n          select CCT.cart_no,CCT.cart_seq ,CCT.MASTER_ID ");

		buf.append("\n       from CART_CONT_TBL CCT   ");
		buf.append("\n     	 ");
		buf.append("\n      group by CCT.cart_no,CCT.cart_seq,CCT.MASTER_ID  ");
		buf.append("\n     ) SU GROUP BY MASTER_ID  ");
		buf.append("\n     )  cart on cart.MASTER_ID = META.MASTER_ID  ");
		buf.append("\n    LEFT OUTER JOIN (SELECT MASTER_ID FROM CART_CONT_TBL GROUP BY MASTER_ID) CA ON CA.MASTER_ID= META.master_id ");
		buf.append("\n     left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = meta.PGM_ID ");



		/** 검색조건*/

		buf.append("\n   WHERE 1=1 ");
		buf.append("\n   and meta.del_dd=''  and map.del_dd='' ");
		buf.append("\n    and ( meta.master_id not in (select master_id from DISCARD_INFO_TBL group by master_id)  or meta.DEL_DD ='' or meta.DEL_DD is null ) ");


		/** 등록일*/
		if(!condition.getStart_reg_dt().equals("")){

			buf.append("\n  and META.REG_DT  >= '"+condition.getStart_reg_dt()+"000000'");
		}

		if(!condition.getEnd_reg_dt().equals("")){

			buf.append("\n  and  META.REG_DT  <= '"+condition.getEnd_reg_dt()+"999999'");
		}



		/** 방송일/촬영일*/
		if(!condition.getStart_brd_dd().equals("")){

			buf.append("\n  and (( META.brd_dd  >= '"+condition.getStart_brd_dd()+"'");

			buf.append("\n  and  META.brd_dd  <= '"+condition.getEnd_brd_dd()+"') or ");
			buf.append("\n  ( META.fm_dt  >= '"+condition.getStart_brd_dd()+"'");

			buf.append("\n  and  META.fm_dt  <= '"+condition.getEnd_brd_dd()+"')) ");
		}


		/** 제목*/
		if(!condition.getTitle().equals("")){

			buf.append("\n and ( META.TITLE LIKE '%"+condition.getTitle()+"%' or  pgm.PGM_NM like '%"+condition.getTitle()+"%' ) ");
		}



		/** 이용횟수*/
		/*		 	if(!condition.getFlag().equals("")){
if(!condition.getStart_reg_dt().equals("")||!condition.getEnd_reg_dt().equals("")){
	buf.append("\n   AND ");
		 		}
		 	if(condition.getFlag().equals("0")){		 		
		 	buf.append("\n   cnt  < " +condition.getUse_cont());
		 	}else  	if(condition.getFlag().equals("1")){
			buf.append("\n   cnt  = "+condition.getUse_cont());
		 	}else  	if(condition.getFlag().equals("2")){
		 	buf.append("\n   cnt  > "+condition.getUse_cont());
			}		 	
		 	}
		 */



		//추후 구현 예정 이용회수 from to

		if(!condition.getUse_end_cont().equals("")||!condition.getUse_start_cont().equals("")){

			if(condition.getUse_start_cont().equals("0")&&!condition.getUse_end_cont().equals("")){
				buf.append("\n   and (cnt  is null ");
				buf.append("\n   or cnt  <= "+condition.getUse_end_cont()+")");
			}else  if(condition.getUse_start_cont().equals("0")){
				buf.append("\n   and cnt  is null ");
			}else  if(condition.getUse_end_cont().equals(condition.getUse_start_cont())){
				buf.append("\n   and cnt  = "+condition.getUse_start_cont());
			}else  if(!condition.getUse_start_cont().equals("")){
				buf.append("\n   and cnt  >= "+condition.getUse_start_cont());
			}

			if(condition.getUse_end_cont().equals("0")){

			}else  if(!condition.getUse_end_cont().equals("")){
				
				if(condition.getUse_start_cont().equals("0")&&!condition.getUse_end_cont().equals("")){

				}else 	{
					if(!condition.getUse_start_cont().equals("")){
						buf.append("\n   AND ");
					}
					buf.append("\n   cnt  <= "+condition.getUse_end_cont());
				}
				
			}
		}


		/* if( !condition.getUse_start_cont().equals("")||!condition.getUse_end_cont().equals("")){

				if(condition.getUse_start_cont().equals("0")||condition.getUse_end_cont().equals("0")){
					buf.append("\n  and (cnt is null or (cnt >='0' and cnt <= '"+condition.getUse_end_cont()+"'))  "); 
				}else {
				if(!condition.getUse_start_cont().equals("")){
				buf.append("\n  and  cnt >= '"+condition.getUse_start_cont()+"'  ");
				}
				if(!condition.getUse_end_cont().equals("")){
					 if(condition.getUse_end_cont().equals("0")) {

					}
					buf.append("\n  and  cnt <= '"+condition.getUse_end_cont()+"'  ");
						}
				}

			}*/

		/** 장르 대분류 검색*/
		if(!condition.getCtgr_l_cd().equals("")){
			buf.append("\n   AND  meta.CTGR_L_CD= '"+condition.getCtgr_l_cd()+"'");
		}


		/** 코드별 검색*/
		if(!condition.getClf_cd().equals("")){
			/** 복본여부*/
			//buf.append("\n   AND  ");
			if(condition.getClf_cd().equals("COPY")){
				if(!condition.getScl_cd().equals("")){
					//20121221 최효정과장 요청사항
					//복본여부 N 으로 검색시 : copy복본= N AND H.264복본 = N 으로 검색
					//복본여부 Y 로 검색시 : copy복본= Y OR H.264복본 = Y 으로 검색
					if(condition.getScl_cd().equals("Y")){  
						buf.append("\n and (loc.cyn = '"+condition.getScl_cd()+"'" + " OR LOC.copy_status = 'C')");
					}else{
						buf.append("\n and VALUE(loc.cyn,'N') = '"+condition.getScl_cd()+"'" + " AND VALUE(LOC.copy_status,'N') <> 'C'" );
					}
					//buf.append("\n and  loc.cyn = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}
			}

			/** 보존기한*/
			if(condition.getClf_cd().equals("RSV")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.RSV_PRD_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n   ");	
				}


			}

			/** 저작권*/
			if(condition.getClf_cd().equals("CPRT")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.CPRT_TYPE = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n ");	
				}

			}

			/** 시청등급*/
			if(condition.getClf_cd().equals("VIEW")){
				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.VIEW_GR_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}


			}


			/** 화질*/
			if(condition.getClf_cd().equals("VP")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND CON.VD_QLTY = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}



			}



			/** 종횡비*/
			if(condition.getClf_cd().equals("ASP")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n AND  CON.ASP_RTO_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n ");	
				}



			}


			/** 사용제한*/
			if(condition.getClf_cd().equals("USE_LIMIT")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.RIST_CLF_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}

			}


			/** 컨텐츠 구분*/
			if(condition.getClf_cd().equals("CT_CLA")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND CON.CT_CLA = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}

			}
		}

		/** 회사 구분*/
		if(!condition.getCocd().equals("")){
			buf.append("\n  AND meta.cocd = '"+condition.getCocd()+"'");
		}else{
			buf.append("\n  ");	
		}
		/** 채널 구분*/
		if(!condition.getChennel().equals("")){
			buf.append("\n  AND meta.chennel_cd = '"+condition.getChennel()+"'");
		}else{
			buf.append("\n  ");	
		}

		if(!DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag )){
			buf.append("\n   	  ORDER BY cnt desc, title asc,meta.BRD_DD asc, meta.FM_DT asc, META.REG_DT DESC  ");
		}



		return buf.toString();
	}		

	/**
	 * 이용현황 정보 목록을 조회(총조회건수, 총길이) 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 */
	public static String selectUseInfoQuery(UseInfoDO condition, String flag) {
		StringBuffer buf = new StringBuffer();
		if(DASBusinessConstants.PageQueryFlag.NORMAL.equals(flag)) {
			buf.append("\nSELECT * FROM (                                                                                                                                          ");
			buf.append("\n    SELECT                                                                                                                                               ");
			buf.append("\n    	ROW_NUMBER() OVER(ORDER BY  mst.master_id) AS ROWNUM,                                                                                               ");
			buf.append("\n    	mst.cnt, mst.master_id, mst.RPIMG_CT_ID, loc.JOB_STATUS,                                                                                            ");
			buf.append("\n		CODE.DESC,                                                                                                                                            ");
			buf.append("\n		case                                                                                                                                                  ");
			buf.append("\n			when  mst.ctgr_l_cd='100' then mst.TITLE                                                                                                            ");
			buf.append("\n			when  mst.ctgr_l_cd ='200' and  mst.pgm_id !=0 then pgm.pgm_nm                                                                                      ");
			buf.append("\n			else mst.TITLE                                                                                                                                      ");
			buf.append("\n		end as title                                                                                                                                          ");
			buf.append("\n		, mst.BRD_DD, mst.REG_DT,                                                                                                                             ");
			buf.append("\n		mst.FM_DT,                                                                                                                                            ");
			buf.append("\n		mst.CHENNEL_CD,                                                                                                                                       ");
			buf.append("\n		case when loc.backup_status='C' THEN 'Y' ELSE 'N' END AS BACKUP_YN,                                                                                   ");
			buf.append("\n		case when LOC.copy_status='C' THEN 'Y' ELSE 'N' END AS copy_object_yn,                                                                                ");
			buf.append("\n		case when LOC.cyn='Y' THEN 'Y' ELSE 'N' END AS old_copy_object_yn,                                                                                    ");
			buf.append("\n		mst.BRD_LENG, mst.MASTER_ID,value(mst.epis_no, '0') as epis_no                                                                                        ");
			buf.append("\n    FROM (                                                                                                                                               ");
			buf.append("\n    	SELECT                                                                                                                                              ");
			buf.append("\n        	meta.*,                                                                                                                                         ");
			buf.append("\n        	CART_USED_COUNT(meta.MASTER_ID) AS cnt                                                                                                          ");
			buf.append("\n      FROM METADAT_MST_TBL meta                                                                                                                        ");
			buf.append("\n			left outer join PGM_INFO_TBL pgm on meta.PGM_ID = pgm.PGM_ID                                                                                           ");
			buf.append("\n    	WHERE 1=1 AND (meta.del_dd IS NULL OR meta.del_dd = '') AND NOT EXISTS (                                                                            ");
			buf.append("\n        	SELECT 1 FROM DISCARD_INFO_TBL discard WHERE meta.master_id = discard.master_id                                                                 ");
			buf.append("\n      )                                                                 																					");
		} else {
			buf.append("\nSELECT                                                                                                                                                    ");
			buf.append("\n	COUNT(*) AS ccount, sum(t.sum_brd_leng)*29.97  as sum_brd_leng                                                                                          ");
			buf.append("\nFROM (                                                                                                                                                    ");
			buf.append("\n    SELECT                                                                                                                                                ");
			buf.append("\n		CASE                                                                                                                                                  ");
			buf.append("\n			WHEN mst.BRD_LENG IS NOT NULL AND mst.BRD_LENG <> ''                                                                                                ");
			buf.append("\n			THEN bigint(SUBSTR(mst.BRD_LENG,1,2))*60*60+bigint(substr(mst.BRD_LENG,4,2))*60+bigint(substr(mst.BRD_LENG,7,2))                                    ");
			buf.append("\n			ELSE 0                                                                                                                                              ");
			buf.append("\n		END sum_brd_leng                                                                                                                                      ");
			buf.append("\n    FROM (                                                                                                                                                ");
			buf.append("\n    	SELECT                                                                                                                                              ");
			buf.append("\n        	meta.*                                                                                                                                          ");
			buf.append("\n      FROM METADAT_MST_TBL meta                                                                                                                         ");
			buf.append("\n			left outer join PGM_INFO_TBL pgm on meta.PGM_ID = pgm.PGM_ID                                                                                           ");
			buf.append("\n    	WHERE 1=1 AND (meta.del_dd IS NULL OR meta.del_dd = '') AND NOT EXISTS (                                                                            ");
			buf.append("\n        	SELECT 1 FROM DISCARD_INFO_TBL discard WHERE meta.master_id = discard.master_id                                                                 ");
			buf.append("\n      )                                                                 																					");
		}
		
		// 등록일
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_reg_dt()) 
				&& org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_reg_dt())){
			buf.append("\n    AND substr(meta.REG_DT,1,8) BETWEEN '"+condition.getStart_reg_dt()+"' AND '"+condition.getEnd_reg_dt()+"'											");
		}
		// 방송일 or 촬영일
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_brd_dd()) 
				&& org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_brd_dd())){
			buf.append("\n    AND ((meta.brd_dd BETWEEN '"+condition.getStart_brd_dd()+"' AND '"+condition.getEnd_brd_dd()+"')													");
			buf.append("\n    	OR (meta.fm_dt BETWEEN '"+condition.getStart_brd_dd()+"' AND '"+condition.getEnd_brd_dd()+"'))													");
		}
		// 제목
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getTitle())){
			buf.append("\n    AND (meta.title like '%"+condition.getTitle()+"%'  or pgm.pgm_nm like '"+condition.getTitle()+"')                                                ");
		}
		// 장르
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getCtgr_l_cd())){
			buf.append("\n    AND meta.ctgr_l_cd = '"+condition.getCtgr_l_cd()+"'                                                ");
		}
		// ------------------------- 코드검색 시작
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getClf_cd())) {

			// 보존기한
			if("RSV".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND meta.rsv_prd_cd = '"+condition.getScl_cd()+"'                                                ");
			}
			// 저작권
			if("CPRT".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND meta.cprt_type = '"+condition.getScl_cd()+"'                                                ");
			}
			// 시청등급
			if("VIEW".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND meta.view_gr_cd = '"+condition.getScl_cd()+"'                                                ");
			}
			// 사용제한
			if("USE_LIMIT".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND meta.rist_clf_cd = '"+condition.getScl_cd()+"'                                                ");
			}
		}
		// ------------------------ 코드 검색 종료
		// 회사 구분
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getCocd())){
			buf.append("\n    AND meta.cocd = '"+condition.getCocd()+"'                                                ");
		}
		// 채널 구분
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getChennel())){
			buf.append("\n    AND meta.chennel_cd = '"+condition.getChennel()+"'                                                ");
		}
		buf.append("\n    ) mst                                                                                                                                                 ");
		buf.append("\n    	inner JOIN (select master_id, ct_id from CONTENTS_MAPP_TBL WHERE del_yn = 'N' group by master_id, ct_id) mapp ON mapp.MASTER_ID = mst.MASTER_ID     ");
		buf.append("\n    	inner join contents_tbl ct on mapp.CT_ID = ct.CT_ID                                                                                                 ");
		buf.append("\n    	inner JOIN CONTENTS_INST_TBL inst ON ct.CT_ID = inst.CT_ID AND inst.CTI_FMT LIKE '1%'                                                               ");
		buf.append("\n      left outer JOIN CONTENTS_LOC_TBL loc ON inst.CTI_ID = loc.CTI_ID                                                                                  ");
		buf.append("\n		left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = mst.PGM_ID                                                                                           ");
		buf.append("\n      inner join CODE_TBL CODE on CODE.SCL_CD=mst.CTGR_L_CD  AND CLF_CD = 'P002'                                                                        ");
		buf.append("\n    WHERE 1=1                                                                                                                               ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getClf_cd())) {
			/*
			 * 20121221 최효정과장 요청사항
			 * 복본여부 N 으로 검색시 : copy복본= N AND H.264복본 = N 으로 검색
			 * 복본여부 Y 로 검색시   : copy복본= Y OR  H.264복본 = Y 으로 검색
			 */
			// 복본여부
			if("COPY".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				if("Y".equals(condition.getScl_cd())) {
					buf.append("\n    AND (loc.cyn = '"+condition.getScl_cd()+"'" + " OR LOC.copy_status = 'C')                                                ");
				} else {
					buf.append("\n    AND value(loc.cyn,'N') = '"+condition.getScl_cd()+"'" + " AND value(LOC.copy_status,'N') <> 'C'                                                ");
				}
			}
			// 화질
			if("VP".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND ct.vd_qlty = '"+condition.getScl_cd()+"'                                                ");
			}
			// 종횡비
			if("ASP".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND ct.asp_rto_cd = '"+condition.getScl_cd()+"'                                                ");
			}
			// 콘텐츠 구분
			if("CT_CLA".equals(condition.getClf_cd()) && org.apache.commons.lang.StringUtils.isNotBlank(condition.getScl_cd())) {
				buf.append("\n    AND ct.ct_cla = '"+condition.getScl_cd()+"'                                                ");
			}
		}
		if(DASBusinessConstants.PageQueryFlag.NORMAL.equals(flag)) {
			buf.append("\n    ORDER BY mst.cnt desc, title asc, mst.BRD_DD asc, mst.FM_DT asc, mst.REG_DT DESC");
		}
		buf.append("\n) t                                                                                                		");
		
		return buf.toString();
	}
	
	@Deprecated
	public static String selectUseInfoList2(UseInfoDO condition)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select  count(*) as CCOUNT ,sum(sum_brd_leng)*29.97  as sum_brd_leng from ( ");               
		buf.append("\n select ");               
		buf.append("\n  distinct  CODE.DESC, META.TITLE,  META.BRD_DD, META.REG_DT,  ");
		if(condition.getScl_cd().equals(200)){
			buf.append("\n   META.BRD_DD,  ");
		}else{
			buf.append("\n   META.FM_DT,  ");
		}
		buf.append("\n META.BRD_LENG, META.MASTER_ID,META.EPIS_NO,LOC.CYN as copy_object_yn  ");
		buf.append("\n  , CASE WHEN cart.cnt IS NULL THEN  0   ");
		buf.append("\n  ELSE CART.CNT    ");
		buf.append("\n   END AS cnt  , ");
		buf.append("\n   CASE    ");
		buf.append("\n  when META.BRD_LENG is not null or META.BRD_LENG <> ''   ");
		buf.append("\n   THEN bigint(SUBSTR(META.BRD_LENG,1,2))*60*60+bigint(substr(META.BRD_LENG,4,2))*60+bigint(substr(META.BRD_LENG,7,2))   ");
		buf.append("\n  ELSE 0  ");
		buf.append("\n  END SUM_BRD_LENG,  ");
		buf.append("\n  ROW_NUMBER() OVER(ORDER BY META.master_id) AS rownum   ");
		buf.append("\n     FROM  METADAT_MST_TBL META  ");
		buf.append("\n    inner join CODE_TBL CODE on CODE.SCL_CD=META.CTGR_L_CD  AND CLF_CD = 'P002' ");
		buf.append("\n       INNER JOIN (select master_id,ct_id,del_dd from CONTENTS_MAPP_TBL group by master_id,ct_id,del_dd) MAP ON MAP.MASTER_ID = META.MASTER_ID  ");
		buf.append("\n      INNER JOIN (SELECT CT_ID,CTI_FMT ,CTI_ID  FROM CONTENTS_INST_TBL group by CT_ID, CTI_FMT,CTI_ID ) INST ON INST.CT_ID = MAP.CT_ID AND INST.CTI_FMT LIKE '%10%' ");
		//최효정과정 요청사항 copy_status 추가
		buf.append("\n     left outer JOIN  (select CYN,CTI_ID,backup_yn,copy_status FROM CONTENTS_LOC_TBL GROUP BY CYN,CTI_ID,backup_yn,copy_status) LOC ON LOC.CTI_ID = INST.CTI_ID  ");
		buf.append("\n     inner join contents_tbl con on map.ct_id = con.CT_ID  and con.CT_ID =meta.RPIMG_CT_ID ");
		buf.append("\n     left outer join  (SELECT COUNT(MASTER_ID) AS CNT,MASTER_id FROM (  ");
		buf.append("\n          select CCT.cart_no,CCT.cart_seq ,CCT.MASTER_ID ");
		buf.append("\n        from CART_CONT_TBL CCT   ");
		buf.append("\n     	 ");
		buf.append("\n      group by CCT.cart_no,CCT.cart_seq,CCT.MASTER_ID  ");
		buf.append("\n     ) SU GROUP BY MASTER_ID  ");
		buf.append("\n     )  cart on cart.MASTER_ID = META.MASTER_ID  ");
		buf.append("\n    LEFT OUTER JOIN (SELECT MASTER_ID FROM CART_CONT_TBL GROUP BY MASTER_ID) CA ON CA.MASTER_ID= META.master_id ");
		buf.append("\n     left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = meta.PGM_ID ");

		/** 검색조건*/

		buf.append("\n   WHERE 1=1 ");
		buf.append("\n   and meta.del_dd='' 	 and map.del_dd=''  ");
		buf.append("\n    and ( meta.master_id not in (select master_id from DISCARD_INFO_TBL group by master_id)  or meta.DEL_DD ='' or meta.DEL_DD is null ) ");



		/** 등록일*/
		if(!condition.getStart_reg_dt().equals("")){

			buf.append("\n  and META.REG_DT  >= '"+condition.getStart_reg_dt()+"000000'");
		}

		if(!condition.getEnd_reg_dt().equals("")){

			buf.append("\n  and  META.REG_DT  <= '"+condition.getEnd_reg_dt()+"999999'");
		}

		/** 방송일/촬영일*/
		if(!condition.getStart_brd_dd().equals("")){

			buf.append("\n  and(( META.brd_dd  >= '"+condition.getStart_brd_dd()+"'");

			buf.append("\n  and  META.brd_dd  <= '"+condition.getEnd_brd_dd()+"') or ");
			buf.append("\n  ( META.fm_dt  >= '"+condition.getStart_brd_dd()+"'");

			buf.append("\n  and  META.fm_dt  <= '"+condition.getEnd_brd_dd()+"')) ");
		}

		/** 제목*/
		if(!condition.getTitle().equals("")){

			buf.append("\n and ( META.TITLE LIKE '%"+condition.getTitle()+"%' or  pgm.PGM_NM like '%"+condition.getTitle()+"%' ) ");
		}




		/** 이용횟수*/
		/*		 	if(!condition.getFlag().equals("")){
if(!condition.getStart_reg_dt().equals("")||!condition.getEnd_reg_dt().equals("")){
buf.append("\n   AND ");
	 		}
	 	if(condition.getFlag().equals("0")){		 		
	 	buf.append("\n   cnt  < " +condition.getUse_cont());
	 	}else  	if(condition.getFlag().equals("1")){
		buf.append("\n   cnt  = "+condition.getUse_cont());
	 	}else  	if(condition.getFlag().equals("2")){
	 	buf.append("\n   cnt  > "+condition.getUse_cont());
		}		 	
	 	}
		 */



		//추후 구현 예정 이용회수 from to


		if(!condition.getUse_end_cont().equals("")||!condition.getUse_start_cont().equals("")){
			logger.debug("getUse_end_cont =="+condition.getUse_end_cont());
			logger.debug("getUse_start_cont =="+condition.getUse_start_cont());
			buf.append("\n   and ");

			if(condition.getUse_start_cont().equals("0")&&!condition.getUse_end_cont().equals("")){
				buf.append("\n   (cnt  is null ");
				buf.append("\n   or cnt  <= "+condition.getUse_end_cont()+")");
			}else  if(condition.getUse_start_cont().equals("0")){
				buf.append("\n   cnt  is null ");
			}else  if(condition.getUse_end_cont().equals(condition.getUse_start_cont())){
				buf.append("\n   cnt  = "+condition.getUse_start_cont());
			}else  if(!condition.getUse_start_cont().equals("")){
				buf.append("\n   cnt  >= "+condition.getUse_start_cont());
			}

			if(condition.getUse_end_cont().equals("0")){

			}else  if(!condition.getUse_end_cont().equals("")){


				if(condition.getUse_start_cont().equals("0")&&!condition.getUse_end_cont().equals("")){

				}else 	{
					if(!condition.getUse_start_cont().equals("")){
						buf.append("\n   AND ");
					}
					buf.append("\n   cnt  <= "+condition.getUse_end_cont());
				}
			}

		}



		/* if( !condition.getUse_start_cont().equals("")||!condition.getUse_end_cont().equals("")){

			if(condition.getUse_start_cont().equals("0")||condition.getUse_end_cont().equals("0")){
				buf.append("\n  and (cnt is null or (cnt >='0' and cnt <= '"+condition.getUse_end_cont()+"'))  "); 
			}else {
			if(!condition.getUse_start_cont().equals("")){
			buf.append("\n  and  cnt >= '"+condition.getUse_start_cont()+"'  ");
			}
			if(!condition.getUse_end_cont().equals("")){
				 if(condition.getUse_end_cont().equals("0")) {

				}
				buf.append("\n  and  cnt <= '"+condition.getUse_end_cont()+"'  ");
					}
			}

		}*/

		/** 장르 대분류 검색*/
		if(!condition.getCtgr_l_cd().equals("")){
			buf.append("\n   AND  meta.CTGR_L_CD= '"+condition.getCtgr_l_cd()+"'");
		}





		/** 코드별 검색*/
		if(!condition.getClf_cd().equals("")){
			/** 복본여부*/
			//buf.append("\n   AND  ");
			if(condition.getClf_cd().equals("COPY")){
				if(!condition.getScl_cd().equals("")){
					//20121221 최효정과장 요청사항
					//복본여부 N 으로 검색시 : copy복본= N AND H.264복본 = N 으로 검색
					//복본여부 Y 로 검색시 : copy복본= Y OR H.264복본 = Y 으로 검색
					if(condition.getScl_cd().equals("Y")){  
						buf.append("\n and (loc.cyn = '"+condition.getScl_cd()+"'" + " OR LOC.copy_status = 'C')");
					}else{
						buf.append("\n and VALUE(loc.cyn,'N') = '"+condition.getScl_cd()+"'" + " AND VALUE(LOC.copy_status,'N') <> 'C'" );
					}
					//buf.append("\n and  loc.cyn = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}
			}

			/** 보존기한*/
			if(condition.getClf_cd().equals("RSV")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.RSV_PRD_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n   ");	
				}


			}

			/** 저작권*/
			if(condition.getClf_cd().equals("CPRT")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.CPRT_TYPE = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n ");	
				}

			}

			/** 시청등급*/
			if(condition.getClf_cd().equals("VIEW")){
				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.VIEW_GR_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}


			}


			/** 화질*/
			if(condition.getClf_cd().equals("VP")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND CON.VD_QLTY = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}



			}



			/** 종횡비*/
			if(condition.getClf_cd().equals("ASP")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n AND  CON.ASP_RTO_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n ");	
				}



			}


			/** 사용제한*/
			if(condition.getClf_cd().equals("USE_LIMIT")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND META.RIST_CLF_CD = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}

			}


			/** 컨텐츠 구분*/
			if(condition.getClf_cd().equals("CT_CLA")){

				if(!condition.getScl_cd().equals("")){
					buf.append("\n  AND CON.CT_CLA = '"+condition.getScl_cd()+"'");
				}else{
					buf.append("\n  ");	
				}

			}
		}


		/** 회사 구분*/
		if(!condition.getCocd().equals("")){
			buf.append("\n  AND meta.cocd = '"+condition.getCocd()+"'");
		}else{
			buf.append("\n  ");	
		}
		/** 채널 구분*/
		if(!condition.getChennel().equals("")){
			buf.append("\n  AND meta.chennel_cd = '"+condition.getChennel()+"'");
		}else{
			buf.append("\n  ");	
		}


		buf.append("\n   	 ORDER BY META.REG_DT DESC  ");
		buf.append("\n   	  ) as t  ");

		return buf.toString();
	}		







	/**
	 * 아카이브 상태 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 */
	public static String selectArchiveStatusList(ArchiveInfoDO condition)
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n   CASE WHEN MEAT.CTGR_L_CD ='100' THEN MEAT.TITLE  ");
		buf.append("\n  WHEN MEAT.CTGR_L_CD='200' AND MEAT.PGM_ID<>0 THEN PGM.PGM_NM    ");
		buf.append("\n  ELSE MEAT.TITLE    ");
		buf.append("\n   END AS TITLE   ");
		buf.append("\n  , CON.MEDIA_ID,  CON.REG_DT,value(USER.USER_NM,'') as USER_NM      ");
		buf.append("\n   ,case when LOC.job_status='C' THEN 'Y'   ");
		buf.append("\n   WHEN LOC.job_status<>'C' THEN 'N'      ");
		buf.append("\n  ELSE 'N'    ");
		buf.append("\n  END AS DTL_YN    ");
		buf.append("\n ,meat.master_id ");




		buf.append("\n   ,case when MEAT.ARCH_ROUTE = 'P' and MEAT.COCD = 'S' THEN 'PDS'   ");
		buf.append("\n   when MEAT.ARCH_ROUTE = 'P' AND MEAT.COCD <> 'S'THEN 'IFCMS'   ");
		buf.append("\n   when MEAT.ARCH_ROUTE LIKE 'D%' THEN '매체변환'      ");
		buf.append("\n   when MEAT.ARCH_ROUTE LIKE 'O%' THEN 'ON-AIR'    ");
		buf.append("\n   ELSE ''    ");
		buf.append("\n   END AS ARCH_ROUTE    ");
		//buf.append("\n ,value ((SELECT JOB_STATUS FROM CONTENTS_LOC_TBL C WHERE C.CTI_ID=INST_MXF.CTI_ID AND  C.USE_YN='Y'),'') AS ARCHIVE_RE ");
		//buf.append("\n ,value ((SELECT ARCH_STE_YN FROM CONTENTS_INST_TBL A WHERE A.CTI_ID=INST_MXF.CTI_ID AND A.CTI_FMT LIKE '1%'),'') AS HIGH_QUAL");
		buf.append("\n   ,CASE       ");
		buf.append("\n     when MEAT.ARCH_ROUTE = 'P' and  INST_MXF.fl_path <>'' THEN  'Y'            ");
		buf.append("\n   WHEN MEAT.ARCH_ROUTE <> 'P' THEN  'N'    ");
		buf.append("\n   ELSE 'N'    ");
		buf.append("\n   END AS ARCHIVE_RE    ");
		buf.append("\n   ,CASE       ");
		buf.append("\n     WHEN (MEAT.ARCH_ROUTE LIKE 'O%' OR MEAT.ARCH_ROUTE LIKE 'D%') and INST_MXF.fl_path <>'' THEN 'Y'        ");
		buf.append("\n     WHEN MEAT.ARCH_ROUTE = 'P' THEN  'N'    ");
		buf.append("\n     ELSE 'N'    ");
		buf.append("\n   END AS HIGH_QUAL    ");		
		buf.append("\n , CASE WHEN INST_WMV.cti_id IS NOT NULL AND INST_WMV.FL_PATH IS NOT NULL THEN 'Y' ELSE 'N' END AS wmv ");
		buf.append("\n , CASE WHEN INST_WMV.cti_id IS NOT NULL AND INST_WMV.CATALOG_YN IS NOT NULL THEN 'Y' ELSE 'N' END AS catalog_yn  ");
		buf.append("\n , VALUE(loc.CYN, 'N') OLD_COPY  ");
		buf.append("\n , CASE WHEN LOC.COPY_STATUS='C' THEN 'Y' ELSE 'N' END COPY ");	
		buf.append("\n , VALUE (INST_MXF.ETC,'') AS ETC ");

		buf.append("\n , value(meat.epis_no,0) as epis_no ");
		buf.append("\n , case when meat.CTGR_L_CD='100' then meat.FM_DT ");
		buf.append("\n   when meat.CTGR_L_CD='200' then meat.BRD_DD ");
		buf.append("\n   else '' end as brd_dd ");
		buf.append("\n  ,INST_MXF.MOD_DT ");
		buf.append("\n  ,case when loc.backup_status='C' THEN 'Y' ELSE 'N' END AS backup_yn ");
		buf.append("\n  ,meat.CHENNEL_CD ");

		buf.append("\n FROM  METADAT_MST_TBL MEAT ");

		buf.append("\n   INNER JOIN (select MASTER_ID,CT_ID FROM CONTENTS_MAPP_TBL where del_yn = 'N' and value(del_dd, '') = '' GROUP BY MASTER_ID, CT_ID) MAPP ON  MEAT.MASTER_ID=MAPP.MASTER_ID   ");
		buf.append("\n   INNER JOIN CONTENTS_TBL CON ON CON.CT_ID = MAPP.CT_ID  ");
		buf.append("\n   LEFT OUTER JOIN CONTENTS_INST_TBL INST_WMV ON  INST_WMV.CT_ID=MAPP.CT_ID   AND INST_WMV.CTI_FMT LIKE '3%' ");
		buf.append("\n 	 LEFT OUTER JOIN CONTENTS_INST_TBL INST_MXF  ON INST_MXF.CT_ID=MAPP.CT_ID AND INST_MXF.CTI_FMT LIKE '1%'  ");
		buf.append("\n 	 LEFT OUTER JOIN CONTENTS_LOC_TBL LOC ON INST_MXF.CTI_ID=LOC.CTI_ID  ");
		buf.append("\n 	 LEFT OUTER JOIN USER_INFO_TBL USER ON  USER.SBS_USER_ID=INST_MXF.REGRID ");
		buf.append("\n 	 LEFT OUTER JOIN PGM_INFO_TBL PGM ON PGM.PGM_ID = MEAT.PGM_ID ");

		/** 검색조건*/
		if(!condition.getArchive_path().equals("")||!condition.getWhatdd().equals("")||!condition.getTitle().equals("")||!condition.getReq_nm().equals("")){
			buf.append("\n  WHERE 1=1 ");

			/** 아카이브 경로*/

			if(!condition.getArchive_path().equals("")){
				String[] path = condition.getArchive_path().split(",");
				buf.append("\n 	and  ( ");
				for(int i=0;i<path.length;i++){
					if(path[i].equals("D"))
					{
						if(i!=0){
							buf.append("\n 	 or  ");
						}
						buf.append("\n 	(MEAT.arch_route LIKE 'D%') ");
					}
					else if(path[i].equals("O"))
					{   // mcuidYn=Y 온에어(주조)
						if(i!=0){
							buf.append("\n 	 or  ");
						}
						buf.append("\n 	 ( ");
						buf.append("\n  MEAT.arch_route LIKE 'O%' ");
						buf.append("\n 	 ) ");

					}else if(path[i].equals("P")){
						//PDS
						if(i!=0){
							buf.append("\n 	 or  ");
						}
						buf.append("\n 	( MEAT.arch_route ='P' )");

					}
				}
				buf.append("\n 	 ) ");
			}
			
			//2012.4.24 조회조건 추가
			if(!condition.getCocd().equals("")){
				buf.append("\n  AND MEAT.cocd like  '" + condition.getCocd()+"%'");
			}
			if(!condition.getChennel().equals("")){
				buf.append("\n   AND MEAT.chennel_cd like  '" + condition.getChennel()+"%'");
			}

			/** 기간 검색*/
			if(!condition.getWhatdd().equals("")){
				if(!condition.getArchive_path().equals("")){
					buf.append("\n  and  ");
				}
				if(condition.getWhatdd().equals("BRD")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd()) && 
							org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())) {
						buf.append("\n   substr(MEAT.BRD_DD,1,8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
				}
				if(condition.getWhatdd().equals("FM")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd()) && 
							org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())) {
						buf.append("\n   substr(MEAT.FM_DT,1,8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
				}
				if(condition.getWhatdd().equals("WMV")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd()) && 
							org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())) {
						buf.append("\n   substr(INST_WMV.RE_WMV_REG_DT,1,8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
				}
				if(condition.getWhatdd().equals("COPY")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd()) && 
							org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())) {
						buf.append("\n   substr(INST_MXF.MOD_DT,1,8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
					buf.append("\n  and  loc.cyn ='Y' ");
				}


				if(condition.getWhatdd().equals("CT")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd()) &&
							org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())) {
						buf.append("\n   substr(CON.MOD_DT, 1, 8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
				}

				if(condition.getWhatdd().equals("REG")){
					if(org.apache.commons.lang.StringUtils.isNotBlank(condition.getStart_dd())
							&& org.apache.commons.lang.StringUtils.isNotBlank(condition.getEnd_dd())){
						buf.append("\n   substr(MEAT.REG_DT,1,8) between  '" + condition.getStart_dd()+"' and '" + condition.getEnd_dd()+"'");
					}
				}

			}  

			/** 프로그램명*/
			if(!condition.getTitle().equals("")){

				if(!condition.getArchive_path().equals("")||!condition.getWhatdd().equals("")){
					buf.append("\n  and  ");
				} 
				buf.append("\n   MEAT.TITLE LIKE  '%" + condition.getTitle()+"%'");
			}
			
			/** 요청자명*/
			if(!condition.getReq_id().equals("")){

				if(!condition.getArchive_path().equals("")||!condition.getWhatdd().equals("")||condition.getTitle().equals("")){
					buf.append("\n  and  ");
				}
				buf.append("\n   USER.USER_NM  LIKE '%" + condition.getReq_id()+"%'");
			}
		}
		buf.append("\n  and value(meat.del_dd,'')=''  ");
		buf.append("\n 	ORDER BY  INST_MXF.REG_DT DESC, meat.title asc ");

		return buf.toString();
	}		





	/**
	 * 아카이브 상태 목록을 조회한다.
	 */
	public static String selectNewTodayList()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\nSELECT                                                                                                                                         ");
		buf.append("\n	CASE                                                                                                                                         ");
		buf.append("\n    	WHEN mst.PGM_ID != 0 THEN pgm.PGM_NM                                                                                                     ");
		buf.append("\n    	WHEN mst.PGM_ID IS NULL OR mst.PGM_CD = '' THEN mst.TITLE                                                                                ");
		buf.append("\n        ELSE mst.TITLE                                                                                                                         ");
		buf.append("\n    END AS title,                                                                                                                              ");
		buf.append("\n	CASE WHEN (SELECT ANNOT_CLF_CD FROM ANNOT_INFO_TBL annot WHERE annot.MASTER_ID = mst.MASTER_ID FETCH FIRST 1 ROW only) IS NULL THEN ''       ");
		buf.append("\n    ELSE (                                                                                                                                     ");
		buf.append("\n    	SELECT                                                                                                                                   ");
		buf.append("\n        	CODE6.DESC                                                                                                                           ");
		buf.append("\n		FROM ANNOT_INFO_TBL ann                                                                                                                    ");
		buf.append("\n			inner JOIN CODE_TBL code6 ON ann.ANNOT_CLF_CD = code6.SCL_CD AND code6.CLF_CD = 'P018'                                                   ");
		buf.append("\n    	WHERE ann.MASTER_ID = mst.MASTER_ID AND code6.GUBUN = 'L'                                                                                ");
		buf.append("\n    	ORDER BY code6.RMK_1 ASC                                                                                                                 ");
		buf.append("\n    	FETCH FIRST 1 ROWS ONLY                                                                                                                  ");
		buf.append("\n    ) end AS rist_nm,                                                                                                                          ");
		buf.append("\n    mst.MASTER_ID, mst.BRD_DD, mst.BRD_LENG, mst.REG_DT, GET_CODE_NM('P011', mst.DATA_STAT_CD) DESC, inst.ARCH_STE_YN, ct.CT_LENG,             ");
		buf.append("\n    value(mst.EPIS_NO, '0') AS epis_no, mst.CTGR_L_CD                                                                                          ");
		buf.append("\nFROM METADAT_MST_TBL mst                                                                                                                       ");
		buf.append("\n    inner JOIN CONTENTS_TBL ct ON mst.RPIMG_CT_ID = ct.CT_ID                                                                                   ");
		buf.append("\n    inner JOIN CONTENTS_INST_TBL inst ON ct.CT_ID = inst.CT_ID AND inst.CTI_FMT LIKE '1%'                                                      ");
		buf.append("\n    left outer JOIN PGM_INFO_TBL pgm ON mst.PGM_ID = pgm.PGM_ID                                                                                ");
		buf.append("\nWHERE mst.CTGR_L_CD = '200' AND mst.COCD = 'S' AND (mst.ARCH_ROUTE LIKE 'O%' OR mst.ARCH_ROUTE LIKE 'D%')                                      ");
		buf.append("\n	AND (VALUE(mst.DEL_DD, '') = '' OR mst.DEL_DD IS NOT null)                                                                                   ");
		buf.append("\n    AND mst.BRD_DD BETWEEN ? AND ?                                                                                                             ");
		buf.append("\nORDER BY mst.BRD_DD DESC                                                                                                                       ");
		
		return buf.toString();
	}
	
	@Deprecated
	public static String selectTodayList()
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n DISTINCT "); 
		buf.append("\n  case when mmt.pgm_id !=0 then pgm.pgm_nm ");
		buf.append("\n  when (mmt.pgm_id is null or mmt.pgm_cd ='') then mmt.title  ");
		buf.append("\n  else mmt.title end as title");

		buf.append("\n     ,MMT.MASTER_ID, MMT.BRD_DD, MMT.BRD_LENG, mmt.reg_dt,value(CODE2.DESC,'') as rist_nm");
		buf.append("\n   ,CODE.DESC, INST.ARCH_STE_YN, con.CT_LENG,value(mmt.epis_no, '0') as epis_no,mmt.CTGR_L_CD ");
		buf.append("\n FROM DAS.METADAT_MST_TBL MMT    ");
		buf.append("\n INNER JOIN CONTENTS_MAPP_TBL MAP ON MAP.MASTER_ID=MMT.MASTER_ID ");
		buf.append("\n INNER JOIN CONTENTS_INST_TBL INST ON MAP.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'     ");
		buf.append("\n  INNER JOIN CONTENTS_TBL con ON MAP.CT_ID=con.CT_ID  AND CON.CT_TYP='003'    ");
		buf.append("\n 	left outer join pgm_info_Tbl pgm on pgm.pgm_id = mmt.pgm_id ");
		buf.append("\n INNER JOIN CODE_TBL CODE ON CODE.SCL_CD= MMT.DATA_STAT_CD AND CODE.CLF_CD='P011'    ");
		buf.append("\n  	left outer JOIN (SELECT MASTER_ID, ANNOT_CLF_CD,GUBUN FROM annot_info_tbl GROUP BY ANNOT_CLF_cD,MASTER_ID,GUBUN)  annot ON annot.master_id=mmt.master_id AND ANNOT.GUBUN='L' and annot.ANNOT_CLF_CD = mmt.RIST_CLF_CD ");

		buf.append("\n left outer JOIN CODE_TBL CODE2 ON CODE2.SCL_CD= annot.ANNOT_CLF_CD AND CODE2.CLF_CD='P018' and code2.GUBUN='L'      ");

		buf.append("\n WHERE MMT.brd_Dd >= ? ");
		buf.append("\n AND MMT.brd_Dd  <= ?  ");
		buf.append("\n AND MMT.ctgr_l_cd  = '200'  ");
		buf.append("\n AND MMT.COCD  = 'S'  ");
		buf.append("\n and (mmt.ARCH_ROUTE like 'O%' or   mmt.ARCH_ROUTE like 'D%')  ");
		buf.append("\n AND (mmt.del_dd ='' or mmt.del_dd is null)  ");
		buf.append("\n ORDER BY MMT.brd_Dd DESC   ");




		return buf.toString();
	}		


	/**
	 * 명장면 조회한다.

	 */
	public static String selectGoodMediaList()
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n   map.ct_id, AIT.MASTER_ID, AIT.ANNOT_CLF_CONT,AIT.SOM,AIT.EOM, ");
		buf.append("\n  case when mmt.pgm_id !=0 then pgm.pgm_nm  ");
		buf.append("\n  when (mmt.pgm_id is null or mmt.pgm_cd ='') then mmt.title ");
		buf.append("\n  else mmt.title end as title ,");


		buf.append("\n  mmt.BRD_LENG, value(mmt.epis_no, '0') as epis_no,ait.DURATION as ct_leng  ");

		buf.append("\n 		,MMT.CTGR_L_CD ,CODE.DESC ,value(MMT.ARCH_REG_DD,'') as Arch_reg_dd");
		buf.append("\n ,case when mmt.CTGR_L_CD ='100' then mmt.FM_DT "); 
		buf.append("\n when mmt.CTGR_L_CD='200'  then mmt.BRD_dd "); 
		buf.append("\n else ''		end as brd_dd "); 
		buf.append("\n  FROM DAS.ANNOT_INFO_TBL AIT");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = AIT.MASTER_ID  ");
		buf.append("\n  inner join (select master_id, ct_id,cn_id from DAS.CONTENTs_MAPP_TBL group by master_id, ct_id,cn_id)  map on map.master_id=mmt.MASTER_id and map.CN_ID = ait.CN_ID ");
		buf.append("\n  inner join (select ct_leng, ct_id from das.contents_tbl group by ct_leng, ct_id) con on con.CT_ID=AIT.ct_id  ");
		buf.append("\n  inner  join code_tbl code on code.scl_Cd=ait.ANNOT_CLF_CD and code.CLF_CD='P018'  ");
		buf.append("\n  left outer join pgm_info_Tbl pgm on pgm.pgm_id = mmt.pgm_id  ");
		buf.append("\n WHERE AIT.GUBUN = 'S'  ");
		buf.append("\n and mmt.cocd='S'  ");
		buf.append("\n ORDER BY AIT.REG_DT DESC   ");

		buf.append("\n FETCH FIRST 30 ROWS ONLY   ");




		return buf.toString();
	}		




	/**
	 * 스토리지 용량 조회한다.

	 */
	public static String selectStorageList()
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n   use  ");
		buf.append("\n 	,limite ");
		buf.append("\n  FROM DAS.storage_tbl ");
		buf.append("\n where storage = ?  ");


		return buf.toString();
	}		

	/**
	 * 스토리지 용량 조회한다.

	 */
	public static String selectStorageInfo()
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n   use  ");
		buf.append("\n 	,total_capacity ");
		buf.append("\n 	,storage ");
		buf.append("\n  FROM DAS.storage_tbl ");
		buf.append("\n where storage in (?,?,?)  ");


		return buf.toString();
	}		








	/**
	 * ct_id, cti_id(wmv) 값을 얻어온다
	 * @param media_id 미디어id  
	 *  */
	public static String selectTCInfoQuery(String media_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n  ct.ct_id     ");
		buf.append("\n  ,inst.CTI_ID ");
		buf.append("\n  from contents_tbl ct  ");
		buf.append("\n 	inner join contents_inst_tbl inst on inst.ct_id=ct.CT_ID and inst.CTI_FMT like '30%'   ");
		buf.append("\n  where ct.media_id= '"+media_id+"' ");
		buf.append("\n  order by ct.REG_DT desc ");
		buf.append("\n  fetch first 1 rows only   ");

		return buf.toString();
	}





	/**
	 * 마스터ID를 조회한다(미디어id)
	 * @param media_id 미디어id
	 */
	public static String selectMaster_idQuery(String media_id) {

		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT mapp.master_id ");
		buf.append("\n FROM CONTENTS_MAPP_TBL mapp     ");
		buf.append("\n 		inner JOIN CONTENTS_TBL ct ON mapp.ct_id = ct.ct_id  ");
		buf.append("\n WHERE ct.media_id = ? ORDER BY mapp.REG_DT DESC ");
		buf.append("\n FETCH FIRST 1 ROWS only with ur   ");

		return buf.toString();
	}

	/**
	 * 마스터ID를 조회한다(그룹id)
	 * @param group_id 그룹id
	 */
	public static String selectMaster_idForIfCmsQuery(long group_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n  master_id     ");

		buf.append("\n  from metadat_msT_tbl   ");

		buf.append("\n  where group_id =  '"+group_id+"' ");


		return buf.toString();
	}




	/**
	 * 스토리지 용량 조회한다.

	 */
	public static String getStorageCheck()
	{
		//SystemManageConditionDO condition = new SystemManageConditionDO();
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n   folder_nm  ");
		buf.append("\n 	,path ");
		buf.append("\n 	,total_volume ");
		buf.append("\n 	,use_volume ");
		buf.append("\n 	,passible_volume ");
		buf.append("\n 	,limit ");
		buf.append("\n  FROM DAS.STORAGE_INFO_TBL ");
		buf.append("\n where  folder_nm = ?");


		return buf.toString();
	}		




}

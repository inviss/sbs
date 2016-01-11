package com.app.das.business.dao.statement;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.WorkOrdersConditionDO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.util.CodeCommon;
import com.app.das.util.StringUtils;

/**
 * 작업현황의 매체변환, 주조송출, 작업지시, 사용제한 승인에 대한 SQL 쿼리가 정의되어 있다.
 * @author ysk523
 *
 */
public class WorkStatement 
{
	
	
	/**
	 * 작업 현황중 매체변환 목록조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 * @param workOrdersKind 작업유형
	 */
	public static String selectWorkStatusListQuery(WorkStatusConditionDO conditionDO, String searchFlag, String workOrdersKind)
	{
		
		String sort = conditionDO.getSort();
		
		if (sort == null || sort.equals("")) {
			sort = "master_id";
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{				
		//	buf.append("\n 	B.master_id, ");
			buf.append("\n 	A.master_id, ");
			buf.append("\n 	A.data_stat_cd as data_stat_cd, ");
			buf.append("\n 	C.clf_nm as clf_nm, ");
			buf.append("\n 	A.title as title, ");
			buf.append("\n 	A.reg_dt as reg_dt, ");
			buf.append("\n 	A.req_cd, ");
			buf.append("\n 	A.epis_no, ");
			buf.append("\n 	A.brd_leng as brd_leng, ");
			buf.append("\n 	A.brd_dd as brd_dd, ");
			buf.append("\n 	A.ing_reg_dd as ing_reg_dd, ");
			buf.append("\n 	A.arch_reg_dd as arch_reg_dd, ");
			
			buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
			buf.append("\n 	A.error_stat_cd as error_stat_cd, ");
			buf.append("\n 	A.modrid as modrid, ");
			
			buf.append("\n 	B.COUNT, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY A." + sort + " " + conditionDO.getAsceding() + ") AS rownum ");	
		}
		buf.append("\n from das.metadat_mst_tbl A  ");	
		buf.append("\n LEFT OUTER JOIN ( select z.master_id, count(*) as COUNT  ");
		buf.append("\n from (select distinct master_id, ct_id from das.contents_mapp_tbl) z  ");
		buf.append("\n group by z.master_id) B ON A.master_id = B.master_id ");
		buf.append("\n left outer join das.ERP_COM_USER_TBL d on d.user_id = a.ACCEPTOR_ID, ");
		buf.append("\n das.code_tbl C  ");
		buf.append("\n where A.data_stat_cd = C.scl_cd ");
		buf.append("\n 		AND C.clf_cd = 'P011' ");	
		//buf.append("\n 		AND A.DEL_DD not like '2%' ");
		buf.append("\n 		AND (A.DEL_DD is null or A.DEL_DD = '') ");
		if(CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and LEFT(A.brd_dd, 8) >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and LEFT(A.brd_dd, 8) <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and LEFT(ING_REG_DD, 8) >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and LEFT(ING_REG_DD, 8) <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and LEFT(A.arrg_end_dt, 8) >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and LEFT(A.arrg_end_dt, 8) <= '"+conditionDO.getToDate()+"' ");
			}
		}	
			
	   // Use Between
         /*            	  
		              AND substr(A.reg_dt,1,8) between '20070101' and '20080111'
		--            AND A.fm_dt between ? and ?
		--            AND substr(A.arrg_end_dt,1,8) between ? and ?
		
		              AND A.data_stat_cd in ('001',,,)
		              AND (A.mcuid is null or A.mcuid = '')
	           
		        ) z
		group by z.data_stat_cd,z.clf_nm,z.title,z.reg_dt,z.brd_leng,z.brd_dd,z.arch_reg_dd
		 */   
		
		if(DASBusinessConstants.WorkOrdersKind.MESIUM.equals(workOrdersKind))
		{
			buf.append("\n 	 AND (A.mcuid is null or A.mcuid = '') ");
		}
		else
		{
			buf.append("\n 	AND A.mcuid is not null ");
			buf.append("\n 	AND A.mcuid <> '' ");
		}
		
		boolean isDateCondition = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getEditreadyYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getEditYn()))			

		{
			isDateCondition = true;
		}
		
		if(isDateCondition)
		{
			buf.append("\n 	and ( ");
		}
		
		boolean firstKind = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn()))
		{
			buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_BEFORE+"' ");
			
			firstKind = true;
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				firstKind = true;
			}
		}
		// 편집준비중
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getEditreadyYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
				firstKind = true;
			}
		}
		// 편집중
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getEditYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"' ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"'  ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.COMPLET+"'  ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.COMPLET+"'  ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn()))
		{
			if(firstKind)
			{
				buf.append("\n or ( A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
				buf.append("\n and A.ERROR_STAT_CD = '002' ) ");
			}
			else
			{
				buf.append("\n ( A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
				buf.append("\n and A.ERROR_STAT_CD = '002' ) ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARCHIVE+"' ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARCHIVE+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn()))
		{
			if(firstKind)
			{
				buf.append("\n or ( A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
				buf.append("\n and A.ERROR_STAT_CD = '002' ) ");
			}
			else
			{
				buf.append("\n ( A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
				buf.append("\n and A.ERROR_STAT_CD = '002' ) ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()))
		{
			if(firstKind)
			{
				// 오류를 uncheck하고 정리전으로만 검색해도 오류인 정리전 자료도 모두 검색된다(상태가 오류로 표시되더라도) (2008.06.19. 김건학실장)
				//buf.append(" or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n or A.ERROR_STAT_CD <> '000' ");
			}
			else
			{
				//buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n A.ERROR_STAT_CD <> '000' ");
				firstKind = true;
			}
		}
		if(isDateCondition)
		{
			buf.append(")  ");
		}		
		if(!"".equals(conditionDO.getSearchKey()) && !"".equals(conditionDO.getColumnName())) {
			if ( "MASTER_ID".equals(conditionDO.getColumnName()) ) {
				buf.append("\n and A." + conditionDO.getColumnName() + " = " + conditionDO.getSearchKey());
			} else if ( !"ACCEPTOR_NM".equals(conditionDO.getColumnName())) {
				buf.append("\n and A." + conditionDO.getColumnName() + " like '%" + conditionDO.getSearchKey() + "%' ");
			} else {
				buf.append("\n and d.user_nm like '%" + conditionDO.getSearchKey() + "%' ");
			}
		}
		
		return buf.toString();
	}
	
	/*public static String selectWorkStatusListQuery_old(WorkStatusConditionDO conditionDO, String searchFlag, String workOrdersKind)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	cont.CT_ID, ");
			buf.append("\n 	mapp.MASTER_ID, ");  // MHCHOI
			buf.append("\n 	inst.CTI_ID, ");
			buf.append("\n 	pgm.PGM_NM, ");
			buf.append("\n 	meta.EPIS_NO, ");
			buf.append("\n 	cont.REG_DT, ");
			buf.append("\n 	meta.BRD_LENG, ");
			buf.append("\n 	meta.BRD_DD, ");
			buf.append("\n 	cont.DATA_STAT_CD, ");
			buf.append("\n 	cont.CT_CLA, ");
			buf.append("\n 	inst.INGEST_EQ_ID, ");
			buf.append("\n 	eq.DAS_EQ_NM AS EQ_NM, ");
			buf.append("\n 	inst.CTI_FMT, ");
			buf.append("\n 	inst.REG_DT AS INGEST_CLOSE_DD, ");
			buf.append("\n 	inst.ARCH_STE_YN, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY pgm.PGM_NM) AS rownum ");                 
		}
		buf.append("\n from DAS.CONTENTS_TBL cont, DAS.CONTENTS_MAPP_TBL mapp, DAS.PGM_INFO_TBL pgm,  ");
		buf.append("\n 	DAS.METADAT_MST_TBL meta, DAS.CONTENTS_INST_TBL inst, DAS.DAS_EQUIPMENT_TBL eq ");
		buf.append("\n where cont.CT_ID = mapp.CT_ID ");
		buf.append("\n 	and mapp.MASTER_ID = meta.MASTER_ID ");
		buf.append("\n 	and mapp.PGM_ID = pgm.PGM_ID ");
		buf.append("\n 	and cont.CT_ID = inst.CT_ID ");
		buf.append("\n 	and inst.INGEST_EQ_ID > 0  ");
		buf.append("\n 	and inst.INGEST_EQ_ID = eq.DAS_EQ_ID ");
		if(DASBusinessConstants.WorkOrdersKind.MESIUM.equals(workOrdersKind))
		{
			buf.append("\n 	and eq.DAS_EQ_CLF_CD in ('"+CodeConstants.DeviceType.NLE+"', '"+CodeConstants.DeviceType.SDI+"') ");
		}
		else
		{
			buf.append("\n 	and eq.DAS_EQ_CLF_CD = '"+CodeConstants.DeviceType.FILE+"' ");
		}
		
		if(CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and meta.FM_DT >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and meta.FM_DT <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and cont.REG_DT >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and cont.REG_DT <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and inst.REG_DT >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and inst.REG_DT <= '"+conditionDO.getToDate()+"' ");
			}
		}
		
		boolean isDateCondition = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn()))
		{
			isDateCondition = true;
		}
		
		if(isDateCondition)
		{
			buf.append("\n 	and ( ");
		}
		
		boolean firstKind = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn()))
		{
			buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_BEFORE+"' ");
			
			firstKind = true;
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn()))
		{
			if(firstKind)
			{
				buf.append("\n or cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
			}
			else
			{
				buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn()))
		{
			if(firstKind)
			{
				buf.append("\n or cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"' ");
			}
			else
			{
				buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"'  ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn()))
		{
			if(firstKind)
			{
				buf.append("\n or cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.COMPLET+"'  ");
			}
			else
			{
				buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.COMPLET+"'  ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn()))
		{
			if(firstKind)
			{
				buf.append("\n or cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
			}
			else
			{
				buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn()))
		{
			if(firstKind)
			{
				buf.append("\n or cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARCHIVE+"' ");
			}
			else
			{
				buf.append("\n cont.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARCHIVE+"' ");
				firstKind = true;
			}
		}

		if(isDateCondition)
		{
			buf.append(") ");
		}

//		buf.append("\n 	and (cont.DATA_STAT_CD = ? or cont.DATA_STAT_CD = ?) ");
		
		return buf.toString();
	}*/
	/**
	 * 작업지시 목록 조회를 한다.(작업 순위가 DB에 셋팅되어 있지 않는 경우 '3' 보통으로 셋팅한다)
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectWorkOrdersListQuery(WorkOrdersConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	REQ_NO, ");
			//buf.append("\n 	PGM_NM, ");  Not program name
			buf.append("\n 	SCN_TTL, ");
			buf.append("\n 	REG_DD, ");
			buf.append("\n 	LEN, ");
			buf.append("\n 	BRD_DD, ");
			String workStatCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.INGEST_STATUS, "INGEST_STATUS");
			buf.append(workStatCodeName + " AS WORK_STAT, ");
			buf.append("\n 	WORK_SEQ, ");
			buf.append("\n 	TAPE_ITEM_ID, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY " + conditionDO.getSort() + " " + conditionDO.getAsceding() + ") AS rownum ");   
		}
		buf.append("\n from DAS.D_TAPEITEM_TBL ");
		buf.append("\n where TAPE_ITEM_ID is not null  ");
		if(!StringUtils.isEmpty(conditionDO.getTapeReqNo()))
		{
			buf.append("\n 	and REQ_NO like '%"+conditionDO.getTapeReqNo()+"%' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getFromDate()))
		{
			buf.append("\n 	and BRD_DD >= '"+conditionDO.getFromDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getToDate()))
		{
			buf.append("\n 	and BRD_DD <= '"+conditionDO.getToDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getProgramNm()))
		{
			buf.append("\n 	and PGM_NM like '%"+conditionDO.getProgramNm()+"%' ");
		}
		
		return buf.toString();
	}
	/**
	 * 오류 내역을 조회한다.
	 */
	public static String selectErrorRegisterInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MASTER_ID, "); 
		buf.append("\n 	CT_ID, "); 
		buf.append("\n 	WRT, "); 
		buf.append("\n 	WORK_CLF, "); 
		buf.append("\n 	ER_CONT, "); 
		buf.append("\n 	REACT_CONT, "); 
		buf.append("\n 	WORK_SEQ "); 
		buf.append("\n from DAS.ERROR_RGST_TBL ");
		//buf.append("\n where CT_ID = ?	 ");  //MHCHOI  request from Hwang 0114
		buf.append("\n where MASTER_ID = ?	 ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
		
	}
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 */
	public static String selectApproveCartListQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cart.CART_NO, ");
//		buf.append("\n 	cart.REQ_NM, ");
		buf.append("\n 	CASE WHEN SUBSTR(cart.REQ_USRID, 1, 1) != 'D' THEN (SELECT USER_NM FROM DAS.ERP_COM_USER_TBL WHERE user_id = cart.REQ_USRID) ");
		buf.append("\n              ELSE (SELECT OUT_USER_NM FROM DAS.OUTSIDER_INFO_TBL WHERE out_user_id = cart.REQ_USRID) END AS REQ_NM, ");
		buf.append("\n 	cart.REQ_DT, ");
		buf.append("\n 	cart.DOWN_SUBJ, ");
		String vdQltyCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_IMAGE_QUALITY, "cart.VD_QLTY");
		buf.append(vdQltyCodeName + " AS VD_QLTY_NM, ");
		String aspRtoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_HOR_VER_RATIO, "cart.ASP_RTO_CD");
		buf.append(aspRtoCodeName + " AS ASP_RTO_NM, ");
		buf.append("\n 	( ");
		buf.append("\n 		select count(1) from DAS.CART_CONT_TBL cont  ");
		buf.append("\n 		where cont.CART_NO = cart.CART_NO  ");
		buf.append("\n 			and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT, ");
		buf.append("\n 	cart.APP_CONT, ");
		buf.append("\n 	'' AS USE_LIMIT_FlAG ");
		buf.append("\n from DAS.DOWN_CART_TBL cart ");
		buf.append("\n where RIST_YN = 'Y' ");
		buf.append("\n   and not CART_STAT in (?, ?, ?, ?) ");
		buf.append("\n order by reg_dt desc ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.(데정팀일경우)
	 * @param cartItemDO 조회조건이 담긴 beans
	 */
	public static String selectDownloadRequestListQuery(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		
		
	
		buf.append("\n SELECT 	 ");
		buf.append("\n DCT.CART_NO  ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");		
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		buf.append("\n ,CODE.DESC ");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n  ,CODE2.DESC AS CONM ");
		buf.append("\n  ,value(DCT.FILE_PATH, '') as FILE_PATH ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n INNER JOIN (select ct_id,cart_no,down_stat,MEDIA_ID from  DAS.CART_CONT_TBL)  CCT ON CCT.CART_NO = DCT.CART_NO    ");
		buf.append("\n INNER JOIN (select media_id,ct_id from DAS.CONTENTS_TBL ) CON ON CON.CT_ID = CCT.CT_ID ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=DCT.DOWN_GUBUN AND CODE.CLF_CD='A051' ");
		buf.append("\n INNER JOIN (select cocd, sbs_user_id, user_nm from DAS.USER_INFO_TBL where NVL(approve_yn,'N') = 'Y'  group by sbs_user_id, user_nm,cocd) USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n LEFT OUTER join das.cart_cont_tbl cont on cont.CART_NO = dct.CART_NO ");
		buf.append("\n  left outer join das.code_tbl code2 on code2.SCL_CD = user.COCD and code2.CLF_CD ='P058' ");
		
		if(!cartItemDO.getDown_day().equals("")){
			if(cartItemDO.getDown_day().equals("1")){
				buf.append("\n  where DCT.REG_DT >= ? AND DCT.REG_DT <= ? ");
			}else if(cartItemDO.getDown_day().equals("2")){
				buf.append("\n  where DCT.mod_DT >= ? AND DCT.mod_DT <= ? ");
			}
		}else{
			buf.append("\n where 1=1 ");
		}
		buf.append("\n and dct.down_subj <>'' ");
		if(!cartItemDO.getDownSubj().equals("")){
			buf.append("\n AND DCT.DOWN_SUBJ LIKE ? ");
		}
		if(!cartItemDO.getReq_id().equals("")){
			buf.append("\n  AND DCT.REQ_USRID LIKE ? ");
		}
		if(!cartItemDO.getReqNm().equals("")){
			buf.append("\n  AND USER.USER_NM  LIKE ? ");
		}
		if(!cartItemDO.getMedia_id().equals("")){
			buf.append("\n  AND CON.MEDIA_ID like ? ");
		}
		if(!cartItemDO.getDown_gubun().equals("")){
			buf.append("\n  AND DCT.down_gubun = ? ");
		}
		if(!cartItemDO.getCompany_gubun().equals("")){
			buf.append("\n  AND user.cocd = ? ");
		}
		if(!cartItemDO.getDown_status().equals("")){
			buf.append("\n  AND cont.down_stat = ? ");
		}
		if(cartItemDO.getCti_id() !=0){
			buf.append("\n  AND cont.cti_id = ? ");
		}
		
		buf.append("\n group by DCT.cart_no, DCT.req_dt,DCT.down_subj,USER.user_nm,CODE.desc,DCT.down_gubun,DCT.FILE_PATH ,user.cocd,dct.CART_STAT	,CODE2.DESC  ");
		buf.append("\n order by DCT.REQ_DT desc 	 ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 */
	public static String selectApproveCartDetailsListQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n 	( ");
		buf.append("\n 		select ");
		buf.append("\n 			pgm.PGM_NM ");
		buf.append("\n 		from DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL mt  ");
		buf.append("\n 		where mt.MASTER_ID = cont.MASTER_ID ");
		buf.append("\n 			and mt.PGM_ID = pgm.PGM_ID ");
		buf.append("\n 	) AS PGM_NM, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.APP_CONT, ");
		String ristClfCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "cont.RIST_CLF_CD");
		buf.append(ristClfCodeName + " AS RIST_CLF_NM, ");
		buf.append("\n 	cont.RIST_CLF_CD ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n where cont.CART_NO = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.(데정팀인경우)
	 
	 */
	public static String selectDownloadRequestDetailsListQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select distinct ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n 	cont.CTI_ID, ");
		buf.append("\n  case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm ");
		buf.append("\n  when value(mt.PGM_ID,0) =0 and mt.CTGR_L_CD ='100' then mt.TITLE ");
		buf.append("\n  else mt.TITLE ");
		buf.append("\n  end as title, ");
		buf.append("\n  mt.master_id, ");
		buf.append("\n  value(mt.epis_no, '0') as epis_no,  ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n  mt.ctgr_l_cd, ");
		buf.append("\n  mt.fm_dt, ");
		buf.append("\n  value(down.PROGRESS,'0') as down_vol, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.APP_CONT, ");
		buf.append("\n 	cont.MEDIA_ID, ");
		//buf.append("\n 	value(cont.req_cont,'') ");
		buf.append("\n  CASE WHEN CONT.DOWN_TYP ='F' THEN 'FULL' ");
		buf.append("\n       ELSE 'PARTIAL' ");
		buf.append("\n       END AS DOWN_TYP_NM ,");
	//	String ristClfCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "cont.RIST_CLF_CD");
		buf.append("\n CASE   WHEN cont.RIST_CLF_CD is null or  cont.RIST_CLF_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'P018' and code.SCL_CD = cont.RIST_CLF_CD AND GUBUN='L'  ) ");
		buf.append("  END      AS RIST_CLF_NM,  ");
		
		buf.append("\n  cont.OUTSOURCING_YN, ");
		buf.append("\n 	cont.RIST_CLF_CD , ");
		buf.append("\n  cont.req_cont , ");
	
		
		buf.append("\n CASE  ");
		buf.append("\n WHEN cont.VD_QLTY is null or  cont.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = cont.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ,");
		buf.append("\n  value (ait.progeress, '')  as trans_vol, ");
		
		buf.append("\n CASE  ");
		buf.append("\n WHEN cont.ASP_RTO_CD is null or  cont.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = cont.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM, ");
	
		buf.append("\n  CODE.DESC ,");
		buf.append("\n  cont.DOWN_STAT, ");
		buf.append("\n  value(CODE2.DESC,'') AS STATUS, ");
		buf.append("\n  value(cont.app_cont,'') AS app_cont, ");
		buf.append("\n 	inst.FL_PATH, ");
		buf.append("\n  inst.WRK_FILE_NM, ");
		buf.append("\n  value(user2.user_nm,'') as approveid, ");
		buf.append("\n  	CODE3.DESC AS CHENNEL_NM ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n        inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no ");
		buf.append("\n       left outer join das.contents_down_tbl down on down.cart_no = cart.cart_no and down.CART_SEQ = cont.CART_SEQ ");
		buf.append("\n      left outer join das.metadat_mst_tbl mt on mt.master_id = cont.master_id ");
		buf.append("\n     	left outer join das.pgm_info_Tbl pgm on pgm.PGM_ID = mt.PGM_ID ");
		
		buf.append("\n      left outer join das.ariel_info_tbl ait on ait.cart_no = cont.cart_no and ait.cart_seq=cont.cart_seq ");
		buf.append("\n       left outer join das.CODE_TBL CODE on CODE.SCL_CD = cart.DOWN_GUBUN and CODE.CLF_CD='A051' ");
		buf.append("\n  left outer join DAS.CODE_TBL code2 on code2.SCL_CD = ait.STATUS and code2.CLF_CD='P062' ");
		buf.append("\n  left outer join DAS.user_info_tbl user2 on cont.approveid = user2.sbs_user_id ");
		
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = cont.CT_ID and inst.CTI_FMT like '3%' ");
		buf.append("\n   left outer join DAS.code_tbl code3 on code3.SCL_CD=MT.CHENNEL_CD AND CODE3.CLF_CD='P065'  ");
		
		buf.append("\n where CONT.CART_NO = ? ");
		
		buf.append("\n order by cont.cart_seq asc");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
/*	public static String selectIDListQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.CT_ID  as contentID, ");
		buf.append("\n 	mapp.CT_ID as mapContentID, ");
		buf.append("\n 	mapp.MASTER_ID as mappMasterID, ");
		buf.append("\n 	meta.MASTER_ID as metaMasterID, ");
		buf.append("\n 	inst.CT_ID as instantceContentID, ");
		buf.append("\n 	coner.MASTER_ID as conerMAsterID ");
		buf.append("\n 	from DAS.CONTENTS_TBL cont, DAS.CONTENTS_MAPP_TBL mapp, ");
		buf.append("\n 	     DAS.METADAT_MST_TBL meta, DAS.CONTENTS_INST_TBL inst, DAS.CORNER_TBL coner ");
		buf.append("\n 	where cont.CT_ID = mapp.CT_ID ");
		buf.append("\n 	      and mapp.MASTER_ID = meta.MASTER_ID, ");
		buf.append("\n 	      and cont.CT_ID = inst.CT_ID ");
		buf.append("\n 	      and coner.MASTER_ID = meta.MASTER_ID ");
		buf.append("\n WITH UR ");
		
		return buf.toString();
	}*/
	
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param searchFlag 조건
	 */
	public static String selectApproveCartListQuery2(String searchFlag)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");		
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
		buf.append("\n 	cart.CART_NO, ");
//		buf.append("\n 	cart.REQ_NM, ");
		buf.append("\n 	CASE WHEN SUBSTR(cart.REQ_USRID, 1, 1) != 'D' THEN (SELECT USER_NM FROM DAS.ERP_COM_USER_TBL WHERE user_id = cart.REQ_USRID) ");
		buf.append("\n              ELSE (SELECT OUT_USER_NM FROM DAS.OUTSIDER_INFO_TBL WHERE out_user_id = cart.REQ_USRID) END AS REQ_NM, ");
		buf.append("\n 	cart.REQ_DT, ");
		buf.append("\n 	cart.DOWN_SUBJ, ");
		String vdQltyCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_IMAGE_QUALITY, "cart.VD_QLTY");
		buf.append(vdQltyCodeName + " AS VD_QLTY_NM, ");
		String aspRtoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_HOR_VER_RATIO, "cart.ASP_RTO_CD");
		buf.append(aspRtoCodeName + " AS ASP_RTO_NM, ");
		buf.append("\n 	( ");
		buf.append("\n 		select count(1) from DAS.CART_CONT_TBL cont  ");
		buf.append("\n 		where cont.CART_NO = cart.CART_NO  ");
		buf.append("\n 			and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT, ");
		buf.append("\n 	cart.APP_CONT, ");
		buf.append("\n 	cart.CART_STAT as USE_LIMIT_FlAG, ");
		buf.append("\n 	ROW_NUMBER() OVER(ORDER BY cart.CART_NO) AS rownum ");
		}
		buf.append("\n from DAS.DOWN_CART_TBL cart ");
		buf.append("\n where RIST_YN = 'Y' ");
//		buf.append("\n   and not CART_STAT in (?, ?, ?, ?) ");
//		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param searchFlag 조건
	 * @param startDate 시작일
	 * @param endDate 종료일
	 */
	public static String selectApproveCartListQuery2(String searchFlag, String startDate, String endDate)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");		
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
		buf.append("\n 	cart.CART_NO, ");
//		buf.append("\n 	cart.REQ_NM, ");
		buf.append("\n 	CASE WHEN SUBSTR(cart.REQ_USRID, 1, 1) != 'D' THEN (SELECT USER_NM FROM DAS.ERP_COM_USER_TBL WHERE user_id = cart.REQ_USRID) ");
		buf.append("\n              ELSE (SELECT OUT_USER_NM FROM DAS.OUTSIDER_INFO_TBL WHERE out_user_id = cart.REQ_USRID) END AS REQ_NM, ");
		buf.append("\n 	cart.REQ_DT, ");
		buf.append("\n 	cart.DOWN_SUBJ, ");
		String vdQltyCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_IMAGE_QUALITY, "cart.VD_QLTY");
		buf.append(vdQltyCodeName + " AS VD_QLTY_NM, ");
		String aspRtoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_HOR_VER_RATIO, "cart.ASP_RTO_CD");
		buf.append(aspRtoCodeName + " AS ASP_RTO_NM, ");
		buf.append("\n 	( ");
		buf.append("\n 		select count(1) from DAS.CART_CONT_TBL cont  ");
		buf.append("\n 		where cont.CART_NO = cart.CART_NO  ");
		buf.append("\n 			and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT, ");
		buf.append("\n 	cart.APP_CONT, ");
		buf.append("\n 	cart.CART_STAT as USE_LIMIT_FlAG, ");
		buf.append("\n 	ROW_NUMBER() OVER(ORDER BY cart.CART_NO) AS rownum ");
		}
		buf.append("\n from DAS.DOWN_CART_TBL cart ");
		buf.append("\n where RIST_YN = 'Y' ");
		buf.append("\n 		and REQ_DT >= '"+ startDate +"' ");
		buf.append("\n 		and REQ_DT <= '"+ endDate +"' ");
//		buf.append("\n   and not CART_STAT in (?, ?, ?, ?) ");
//		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 */
	public static String getAnnot_App_ContQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT ");
		buf.append("\n 		ANNOT_CLF_CONT ");
		buf.append("\n FROM DAS.ANNOT_INFO_TBL ");
		buf.append("\n WHERE CT_ID = ? ");
		buf.append("\n 		AND S_FRAME <= ? ");
		buf.append("\n 		AND DURATION >= ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	
	
	
	
	/*public static String selectUserInfoQuery(String userid)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n  SELECT COUNT(*) FROM USER_INFO_TBL WHERE SBS_USER_ID='"+userid+"' AND DEPT_CD='D3OB01' ");
	
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}*/
	
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param pgm_id 프로그램id
	 * @param cartItemDO 조회조건이담긴 beans
	 * @param outsorcing_yn 외주직원여부
	 */
	public static String selectDownloadRequestListForUserIdQuery(String pgm_id,CartItemDO cartItemDO, String outsorcing_yn)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select distinct ");
		buf.append("\n CART_NO ");
		buf.append("\n ,reQ_DT ");
		buf.append("\n , down_subj ");
		
		//buf.append("\n , VD_QLTY_NM ");
		//buf.append("\n , ASP_RTO_NM ");
		buf.append("\n , USE_LIMIT_COUNT ");
		//buf.append("\n ,USE_LIMIT_FLAG ");
		buf.append("\n , DESC ");
		//buf.append("\n , DOWN_STAT ");
		buf.append("\n , USER_NM ");
		buf.append("\n ,file_path ");
		buf.append("\n ,conm");
	//	buf.append("\n ,fm_dt ");
		//buf.append("\n ,epis_no ");
	//	buf.append("\n ,ctgr_l_cd ");
		buf.append("\n   from ( ");

		buf.append("\n select ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,value(DCT.file_path ,'') as file_path ");
		/*buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.VD_QLTY is null or  DCT.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = DCT.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.ASP_RTO_CD is null or  DCT.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = DCT.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM ");*/
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
	
		//buf.append("\n ,'' AS USE_LIMIT_FlAG ");
		buf.append("\n ,value(CODE.DESC ,'') desc ");
		buf.append("\n  ,USER.USER_NM ");
		//buf.append("\n  ,mmt.BRD_DD ");
	//	buf.append("\n  ,mmt.FM_DT ");
		//buf.append("\n  ,mmt.EPIS_NO ");
		buf.append("\n  ,mmt.ctgr_l_cd ");
		buf.append("\n  ,CODE2.DESC AS CONM ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner join (select   ct_id,cti_id,cart_no,down_stat,master_id ,OUTSOURCING_YN,MEDIA_ID  from  DAS.CART_CONT_TBL)  CCT ON CCT.CART_NO = DCT.CART_NO      ");
		buf.append("\n inner join (select media_id,ct_id from DAS.CONTENTS_TBL ) CON ON CON.CT_ID = CCT.CT_ID  ");
		buf.append("\n inner join METADAT_MST_TBL mmt on mmt.MASTER_ID = cct.master_id ");
		buf.append("\n INNER JOIN DAS.APPROVE_INFO_TBL APP ON APP.PGM_ID = MMT.PDS_CMS_PGM_ID ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=DCT.DOWN_GUBUN AND CODE.CLF_CD='A051' ");
	//	buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE2 ON CODE2.SCL_CD=CCT.DOWN_STAT AND CODE2.CLF_CD='P061'  ");
		buf.append("\n  inner join (select sbs_user_id, user_nm ,cocd from DAS.USER_INFO_TBL group by sbs_user_id, user_nm,cocd) USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join das.code_tbl code2 on code2.SCL_CD = user.COCD and code2.CLF_CD ='P058' ");
		
		buf.append("\n where 1=1 ");
		buf.append("\n 	and cct.OUTSOURCING_YN = '"+outsorcing_yn+"' ");
		buf.append("\n AND dct.DOWN_GUBUN <>  '005'   ");
		if(!cartItemDO.getDown_day().equals("")){
			if(cartItemDO.getDown_day().equals("1")){   // 1: 등록일
				buf.append("\n  and  LEFT(DCT.REG_DT,8) >= ? AND LEFT(DCT.REG_DT,8) <= ? ");
			}else if(cartItemDO.getDown_day().equals("2")){  //2:완료일
				buf.append("\n  and  LEFT(DCT.mod_DT,8) >= ? AND LEFT(DCT.mod_DT,8) <= ? ");
			}
		}
		if(!cartItemDO.getDown_gubun().equals("")){
			if(cartItemDO.getDown_gubun().equals(CodeConstants.RestoreGubun.TAPE_OUT)){
				buf.append("\n   AND DCT.DOWN_GUBUN IN ( '"+CodeConstants.RestoreGubun.TAPE_OUT+"' ) ");
			}else if(!cartItemDO.getDown_gubun().equals(CodeConstants.RestoreGubun.TAPE_OUT)){
				buf.append("\n   AND NOT DCT.DOWN_GUBUN IN ( '"+CodeConstants.RestoreGubun.TAPE_OUT+"' ) ");
			}
		}
		

		if(!cartItemDO.getDown_status().equals("")){
			
				buf.append("\n   AND CCT.DOWN_STAT = '"+cartItemDO.getDown_status()+"' ");//clf_cd=P061
		
			}  
	if(!cartItemDO.getUesr_type().equals("")){
			
			buf.append("\n   AND user.EMPLOYEE_TYPE = '"+cartItemDO.getUesr_type()+"' ");//clf_cd=P061
	
		}
		if(!cartItemDO.getCompany_gubun().equals("")){
			
			buf.append("\n   AND USER.COCD = '"+cartItemDO.getCompany_gubun()+"' ");
	  
		}
		
		if(!cartItemDO.getOut_user().equals("")){
			buf.append("\n  AND DCT.OUT_USER_YN = ? ");
		}
	/*	if(!cartItemDO.getDownSubj().equals("")){
			buf.append("\n AND DCT.DOWN_SUBJ LIKE %"+cartItemDO.getDownSubj()+"% ");
			}
		if(!cartItemDO.getMedia_id().equals("")){
			buf.append("\n  AND CON.MEDIA_ID LIKE %"+cartItemDO.getMedia_id()+"% ");
		}
		if(!cartItemDO.getReq_id().equals("")){
			buf.append("\n  AND DCT.REQ_USRID LIKE %"+cartItemDO.getReq_id()+"% ");
		}
		
		if(!cartItemDO.getReqNm().equals("")){
			buf.append("\n  AND USER.USER_NM  LIKE %"+cartItemDO.getReqNm()+"% ");
		}
		*/
		if(!cartItemDO.getDownSubj().equals("")){
			buf.append("\n AND DCT.DOWN_SUBJ LIKE ? ");
			}
		if(!cartItemDO.getMedia_id().equals("")){
			buf.append("\n  AND CON.MEDIA_ID LIKE ? ");
		}
		if(!cartItemDO.getReq_id().equals("")){
			buf.append("\n  AND DCT.REQ_USRID LIKE ? ");
		}
		
		if(!cartItemDO.getReqNm().equals("")){
			buf.append("\n  AND USER.USER_NM  LIKE ? ");
		}
		
		
		if(cartItemDO.getCti_id() !=0){
			buf.append("\n  AND cct.cti_id = ? ");
		}
		
		buf.append("\n AND  APP.PGM_ID in ( "+pgm_id+" ) ");
		buf.append("\n order by DCT.reg_dt desc ");
		buf.append("\n ) B ");
		buf.append("\n group by CART_NO,reQ_DT, down_subj,  USE_LIMIT_COUNT ,DESC, USER_NM,file_path,conm 	 ");
		
	
		
	
		return buf.toString();
	}
	
	
	
	/*public static String selectDownloadRequestListForPDQuery(String pgm_id)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.VD_QLTY is null or  DCT.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = DCT.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.ASP_RTO_CD is null or  DCT.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = DCT.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM ");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,'' AS USE_LIMIT_FlAG ");
		buf.append("\n ,CODE.DESC ");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n INNER JOIN DAS.CART_CONT_TBL CCT ON CCT.CART_NO = DCT.CART_NO ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = CCT.MASTER_ID ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=DCT.DOWN_GUBUN AND CODE.CLF_CD='A051' ");
		buf.append("\n  LEFT OUTER JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n WHERE MMT.PGM_ID  in ( "+pgm_id+") ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	*/
	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param pgmId_grp 프로그램 id 그룹
	 */
	public static String selectDownloadRequestDetailsListUserIdQuery(String pgmId_grp)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select distinct ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n 	cont.CTI_ID, ");
		buf.append("\n  	case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm ");
		buf.append("\n  when value(mt.PGM_ID,0) =0 and mt.CTGR_L_CD ='100' then mt.TITLE ");
		buf.append("\n  else '' ");
		buf.append("\n  end as title, ");
		buf.append("\n   value(mt.epis_no, '0') as epis_no, ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n  mt.FM_DT, ");
		buf.append("\n  mt.CTGR_L_CD, ");
		buf.append("\n   value(down.PROGRESS,'0') as down_vol, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.APP_CONT, ");
		buf.append("\n 	cont.MEDIA_ID, ");
		buf.append("\n  CASE WHEN CONT.DOWN_TYP ='F' THEN 'FULL' ");
		buf.append("\n       ELSE 'PARTIAL' ");
		buf.append("\n       END AS DOWN_TYP_NM ,");
		buf.append("\n CASE   WHEN cont.RIST_CLF_CD is null or  cont.RIST_CLF_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'P018' and code.SCL_CD = cont.RIST_CLF_CD AND GUBUN='L'  ) ");
		buf.append("  END      AS RIST_CLF_NM,  ");
		
		buf.append("\n  cont.OUTSOURCING_YN, ");
		buf.append("\n 	cont.RIST_CLF_CD , ");
		buf.append("\n  cont.req_cont , ");
		buf.append("\n CASE  ");
		buf.append("\n WHEN cart.ASP_RTO_CD is null or  cart.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = cart.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM, ");
		buf.append("\n CASE  ");
		buf.append("\n WHEN cart.VD_QLTY is null or  cart.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = cart.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ,");
		buf.append("\n  value (ait.progeress, '')  as trans_vol, ");
	
		buf.append("\n  CODE.DESC ,");
		buf.append("\n  cont.DOWN_STAT, ");
		buf.append("\n  	value(CODE2.DESC,'') AS STATUS, ");
		
		buf.append("\n  	inst.FL_PATH, ");
		buf.append("\n  	inst.WRK_FILE_NM, ");
		buf.append("\n  value(user2.user_nm,'') as approveid, ");
		buf.append("\n  CODE3.DESC AS CHENNEL_NM ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n        inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no ");
		buf.append("\n        left outer join das.contents_down_tbl down on down.cart_no = cart.cart_no and down.CART_SEQ = cont.CART_SEQ ");
		buf.append("\n      left outer join das.metadat_mst_tbl mt on mt.master_id = cont.master_id ");
		buf.append("\n      left outer join das.pgm_info_Tbl pgm on pgm.PGM_ID = mt.PGM_ID ");
		buf.append("\n      left outer join das.ariel_info_tbl ait on ait.cart_no = cont.cart_no and ait.cart_seq=cont.cart_seq ");
		buf.append("\n       left outer join das.CODE_TBL CODE on CODE.SCL_CD = cart.DOWN_GUBUN and CODE.CLF_CD='A051' ");
		buf.append("\n       left outer join das.user_info_tbl user2 on user2.sbs_user_id = cont.approveid ");
		
		buf.append("\n INNER JOIN DAS.APPROVE_INFO_TBL APP ON APP.PGM_ID = MT.PDS_CMS_PGM_ID ");
		buf.append("\n  left outer join DAS.CODE_TBL code2 on code2.SCL_CD = ait.STATUS and code2.CLF_CD='P061' ");
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = cont.CT_ID and inst.CTI_FMT like '3%' ");
		buf.append("\n  left outer join DAS.code_tbl code3 on code3.SCL_CD=MT.CHENNEL_CD AND CODE3.CLF_CD='P065'  ");
		
		
		buf.append("\n where CONT.CART_NO = ? ");
		buf.append("\n      AND APP.PGM_ID IN ("+pgmId_grp+") ");
		buf.append("\n order by cont.cart_seq ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/*
	public static String selectDownloadRequestDetailsForCPQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n  mt.title, ");
		buf.append("\n  mt.epis_no, ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n  cont.down_vol, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.APP_CONT, ");
		buf.append("\n  CASE WHEN CONT.DOWN_TYP ='F' THEN 'FULL' ");
		buf.append("\n       ELSE 'PARTIAL' ");
		buf.append("\n       END AS DOWN_TYP_NM ,");
		String ristClfCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "cont.RIST_CLF_CD");
		buf.append(ristClfCodeName + " AS RIST_CLF_NM, ");
		buf.append("\n  cont.OUTSOURCING_YN, ");
		buf.append("\n 	cont.RIST_CLF_CD , ");
		buf.append("\n  cont.req_cont , ");
		buf.append("\n  ait.progeress  as trans_vol, ");
		buf.append("\n    CODE.DESC ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n        inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no ");
		buf.append("\n      left outer join das.metadat_mst_tbl mt on mt.master_id = cont.master_id ");
		buf.append("\n      left outer join das.ariel_info_tbl ait on ait.cart_no = cont.cart_no and ait.cart_seq=cont.cart_seq ");
		buf.append("\n      left outer join das.CODE_TBL CODE on CODE.SCL_CD = cart.DOWN_GUBUN and CODE.CLF_CD='A051' ");
		buf.append("\n where MT.PGM_ID = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}*/
	
	
	/*
	public static String selectDownloadRequestDetailsForPDQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n  mt.title, ");
		buf.append("\n  mt.epis_no, ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n  cont.down_vol, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.APP_CONT, ");
		buf.append("\n  CASE WHEN CONT.DOWN_TYP ='F' THEN 'FULL' ");
		buf.append("\n       ELSE 'PARTIAL' ");
		buf.append("\n       END AS DOWN_TYP_NM ,");
		String ristClfCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "cont.RIST_CLF_CD");
		buf.append(ristClfCodeName + " AS RIST_CLF_NM, ");
		buf.append("\n  cont.OUTSOURCING_YN, ");
		buf.append("\n 	cont.RIST_CLF_CD , ");
		buf.append("\n  cont.req_cont , ");
		buf.append("\n  ait.progeress  as trans_vol, ");
		buf.append("\n    CODE.DESC ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n        inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no ");
		buf.append("\n      left outer join das.metadat_mst_tbl mt on mt.master_id = cont.master_id ");
		buf.append("\n      left outer join das.ariel_info_tbl ait on ait.cart_no = cont.cart_no and ait.cart_seq=cont.cart_seq ");
		buf.append("\n      left outer join das.CODE_TBL CODE on CODE.SCL_CD = cart.DOWN_GUBUN and CODE.CLF_CD='A051' ");
		buf.append("\n where MT.PGM_ID = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	*/
	
	
	/**
	 * My Page 다운로드 목록 조회
	 * @param cartItemDO 조회조건이 담긴 beans
	 */
	public static String selectMyDownloadRequestList(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path ");
		/*buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.VD_QLTY is null or  DCT.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = DCT.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN DCT.ASP_RTO_CD is null or  DCT.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = DCT.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM ");*/
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
	//	buf.append("\n ,DCT.APP_CONT ");
		//buf.append("\n ,'' AS USE_LIMIT_FlAG ");
		buf.append("\n ,CODE.DESC ");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner JOIN (select cart_no,down_stat from  DAS.CART_CONT_TBL group by cart_no ,down_stat ) CCT ON CCT.CART_NO = DCT.CART_NO ");
		//buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = CCT.MASTER_ID ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=DCT.DOWN_GUBUN AND CODE.CLF_CD='A051' ");
		buf.append("\n LEFT OUTER JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		
			
				buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
			
			buf.append("\n AND dct.REQ_USRID LIKE '%"+cartItemDO.getUserid()+"%' ");
		
			
			
	
		if(!cartItemDO.getSearch_flag().equals("")){
			
				buf.append("\n   AND CCT.DOWN_STAT = '"+cartItemDO.getSearch_flag()+"' ");//clf_cd=P061
		
			}
	
		buf.append("\n order by DCT.reg_dt desc ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}

	/**
	 * My Page 다운로드 상세 조회
	 * @param cartNo 카트번호
	 * @param user_id 유져id
	 * @return List CartItemDO를 포함하고 있는 List
	 */
	public static String selectMyDownloadRequestDetailsQuery()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n  case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm ");
		buf.append("\n  when  mt.CTGR_L_CD ='100' then mt.TITLE ");
		buf.append("\n  else  mt.TITLE ");
		buf.append("\n  end as title, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.app_cont, ");
		buf.append("\n 	cont.media_id, ");
		buf.append("\n 	value(mt.epis_no,'0') as epis_no,  ");
		buf.append("\n CASE  ");
		buf.append("\n WHEN cart.VD_QLTY is null or  cart.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = cart.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN cart.ASP_RTO_CD is null or  cart.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = cart.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM, ");
		buf.append("\n CASE   WHEN cont.RIST_CLF_CD is null or  cont.RIST_CLF_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'P018' and code.SCL_CD = cont.RIST_CLF_CD AND GUBUN='L'  ) ");
		buf.append("  END      AS RIST_CLF_NM,  ");
		
		buf.append("\n '' AS USE_LIMIT_FlAG ");
		buf.append("\n   , CODE.DESC ");
		buf.append("\n   , CODE2.DESC as DOWN_STAT ");
		buf.append("\n   , value (cont.req_cont,'') as req_cont ");
		buf.append("\n   ,	value(CODE3.DESC,'') AS STATUS ");
		buf.append("\n from DAS.CART_CONT_TBL cont ");
		buf.append("\n        inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no ");
		buf.append("\n      left outer join das.metadat_mst_tbl mt on mt.master_id = cont.master_id ");
		buf.append("\n      	left outer join das.pgm_info_Tbl pgm on pgm.PGM_ID = mt.PGM_ID ");
		
		buf.append("\n      left outer join das.ariel_info_tbl ait on ait.cart_no = cont.cart_no and ait.cart_seq=cont.cart_seq ");
		buf.append("\n      left outer join das.CODE_TBL CODE on CODE.SCL_CD = cart.DOWN_GUBUN and CODE.CLF_CD='A051' ");
		buf.append("\n      LEFT OUTER JOIN DAS.CODE_TBL CODE2 ON CODE2.SCL_CD = cont.DOWN_STAT AND CODE2.CLF_CD='P061' ");
		buf.append("\n      LEFT OUTER JOIN DAS.CODE_TBL CODE3 ON CODE3.SCL_CD = ait.STATUS AND CODE3.CLF_CD='P062' ");
		buf.append("\n where cart.req_usrid = ? ");
		buf.append("\n and cart.cart_no = ? ");
	//	buf.append("\n and (cont.down_stat = '001' or cont.down_stat = '002' or cont.down_stat = '003')");
		buf.append("\n and cart.down_subj <> ''	 ");
		buf.append("\n order by cont.cart_seq asc	 ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	public static String selectNewMyDownloadAprroveList(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT distinct                                                                                        ");
		buf.append("\n 	mst.master_id, cart.CART_NO,                                                           				  ");
		buf.append("\n     CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj, ");
		buf.append("\n     dcart.file_path, dcart.STORAGENAME, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT,   ");
		buf.append("\n     GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,      ");
		buf.append("\n     (                                                                                                  ");
		buf.append("\n     	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                       ");
		buf.append("\n         AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007') ");
		buf.append("\n     ) AS use_limit_count,                                                                               ");
		buf.append("\n     app.APPROVE_USER_NUM                                                                               ");
		buf.append("\n FROM DOWN_CART_TBL dcart                                                                               ");
		buf.append("\n 	inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                   ");
		buf.append("\n 	inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                         ");
		buf.append("\n     inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                   ");
		buf.append("\n     inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                                 ");
		buf.append("\n WHERE cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005' AND RTRIM(value(app.DEPT_CD, '')) <> ''     ");
		buf.append("\n 	   AND SUBSTR(dcart.REG_DT, 1, 8) BETWEEN ? AND ?                                                     ");
		buf.append("\n     AND app.APPROVE_USER_NUM = '"+cartItemDO.getUserid().substring(1, 7)+"'       ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n and dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n ORDER BY cart.CART_NO DESC                                                          					 ");
		
		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveList(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		
		buf.append("\n select ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		
		buf.append("\n ,dct.STORAGENAME ");
		//buf.append("\n ,cont.media_id ");
		//buf.append("\n ,cont.req_CONT ");
		
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n  inner JOIN (select master_id,cart_no,down_stat from  DAS.CART_CONT_TBL group by  master_id,cart_no,down_stat ) cont ON cont.CART_NO = DCT.CART_NO  ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer JOIN (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID  ");	
		buf.append("\n AND APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
	
		buf.append("\n  and ( cont.DOWN_STAT='005')	 ");
		buf.append("\n and app.PGM_ID is not null ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n order by DCT.CART_NO desc ");

	
		return buf.toString();
	}

	public static String selectNewMyDownloadAprroveList2(CartItemDO cartItemDO,String dept_cd)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT distinct                                                                                                ");
		buf.append("\n 	mst.master_id, cart.CART_NO,                                                           ");
		buf.append("\n     CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj, ");
		buf.append("\n     dcart.file_path, dcart.STORAGENAME, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT,   ");
		buf.append("\n     GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,      ");
		buf.append("\n     (                                                                                                  ");
		buf.append("\n     	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                       ");
		buf.append("\n         AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007') ");
		buf.append("\n     ) AS use_limit_count                                                                               ");
		buf.append("\n FROM DOWN_CART_TBL dcart                                                                               ");
		buf.append("\n 	inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                   ");
		buf.append("\n 	inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                         ");
		buf.append("\n     inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                   ");
		// 관리자가 아니면 부서 및 사용자 조건 추가
		if(DASBusinessConstants.ADMIN_DEP.contains(dept_cd.trim())) {
			buf.append("\n 	WHERE 1=1                                              											  ");
		} else {
			buf.append("\n     inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                             ");
			buf.append("\n WHERE  RTRIM(value(app.DEPT_CD, '')) <> '' and app.DEPT_CD = '"+dept_cd.trim()+"'     		      ");
			buf.append("\n    AND app.APPROVE_USER_NUM = '"+cartItemDO.getUserid().substring(1, 7)+"'    ");
		}
		buf.append("\n 	AND cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005'                                              ");
		buf.append("\n 	AND SUBSTR(dcart.REG_DT, 1, 8) BETWEEN ? AND ?                                                        ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n and dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n ORDER BY cart.CART_NO DESC                                                          ");
		
		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveList2(CartItemDO cartItemDO,String dept_cd)
	{
		StringBuffer buf  = new StringBuffer();
		
		buf.append("\n select distinct ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");

		buf.append("\n ,dct.STORAGENAME ");
	//	buf.append("\n ,cont.media_id ");
	//	buf.append("\n ,cont.req_CONT ");
		
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n INNER JOIN DAS.CART_CONT_TBL CONT ON CONT.CART_NO = DCT.CART_NO ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
	
		if(DASBusinessConstants.ADMIN_DEP.contains(dept_cd.trim())) {
			buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		}else{
			 buf.append("\n left outer JOIN  (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID   ");

			buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
			buf.append("\n AND ( (APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
			buf.append("\n and  APP.dept_cd = '"+dept_cd.trim()+"') )");
		}  
		buf.append("\n  and (cont.DOWN_STAT='005')	 ");
	if(!cartItemDO.getDownSubj().equals("")){
		
		buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
	}
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		buf.append("\n order by DCT.CART_NO desc ");

	
		return buf.toString();
	}
	
	public static String selectNewMyDownloadAprroveForOutSourcingList(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT a.* FROM (                                                                                          ");
		buf.append("\n 	SELECT                                                                                                    ");
		buf.append("\n 	   mst.master_id, cart.CART_NO, cart.CART_SEQ,                                                            ");
		buf.append("\n 	   CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj, ");
		buf.append("\n 	   dcart.file_path, dcart.STORAGENAME, cart.media_id, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT,        ");
		buf.append("\n 	   GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,          ");
		buf.append("\n 	   (                                                                                                      ");
		buf.append("\n 	   	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                           ");
		buf.append("\n 	       AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007')     ");
		buf.append("\n 	   ) AS use_limit_count,                                                                                   ");
		buf.append("\n 	   app.APPROVE_USER_NUM                                                                                   ");
		buf.append("\n 	FROM DOWN_CART_TBL dcart                                                                                  ");
		buf.append("\n 		inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                     ");
		buf.append("\n 		inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                           ");
		buf.append("\n 	   	inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                      ");
		buf.append("\n 	   	inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                                    ");
		buf.append("\n 	WHERE cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005' AND RTRIM(value(app.DEPT_CD, '')) <> ''        ");
		buf.append("\n 		AND (cart.OUTSOURCING_YN = 'N' OR (cart.OUTSOURCING_APPROVE = 'Y' AND cart.OUTSOURCING_YN = 'Y'))       ");
		buf.append("\n 		AND SUBSTR(cart.REG_DT, 1, 8) BETWEEN ? AND ?                                         ");
		buf.append("\n 	   	AND app.USE_YN = 'Y' AND app.APPROVE_USER_NUM = '"+cartItemDO.getUserid().substring(1, 7)+"'          ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n  AND dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' 											 ");
		}
		buf.append("\n 	UNION ALL                                                                                                 ");
		buf.append("\n 	SELECT                                                                                                    ");
		buf.append("\n 		mst.master_id, cart.CART_NO, cart.CART_SEQ,                                                             ");
		buf.append("\n 	   	dcart.DOWN_SUBJ, dcart.file_path,                                                                     ");
		buf.append("\n 	   	dcart.STORAGENAME, cart.media_id, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT,        ");
		buf.append("\n 	   	GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,         ");
		buf.append("\n 	   	(                                                                                                     ");
		buf.append("\n 	   		SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                         ");
		buf.append("\n 	       	AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007')    ");
		buf.append("\n 	   	) AS use_limit_count,                                                                                  ");
		buf.append("\n 	   	app.APPROVE_USER_NUM                                                                                  ");
		buf.append("\n 	FROM DOWN_CART_TBL dcart                                                                                  ");
		buf.append("\n 		inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                     ");
		buf.append("\n 		inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                           ");
		buf.append("\n 	   	inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                      ");
		buf.append("\n 	   	inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                                    ");
		buf.append("\n 	WHERE cart.DOWN_STAT = '004' AND dcart.DOWN_GUBUN <> '005'                                                ");
		buf.append("\n 	   	AND cart.OUTSOURCING_YN = 'Y'                                                                         ");
		buf.append("\n 		AND SUBSTR(cart.REG_DT, 1, 8) BETWEEN ? AND ?                                                         ");
		buf.append("\n 		AND app.USE_YN = 'Y' AND app.APPROVE_USER_NUM = user.USER_NUM                                         ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n  AND dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' 											 ");
		}
		buf.append("\n ) a                                                                                                        ");
		buf.append("\n ORDER BY a.CART_NO DESC, a.CART_SEQ ASC                                                                    ");
		
		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인조회(외주제작 용)
	 * @param cartItemDO 조회조건이 담긴beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveForOutSourcingList(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select distinct");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path ");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		buf.append("\n ,cont.req_cont ");
		buf.append("\n ,cont.media_id ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n inner JOIN (select cart_no,master_id,down_stat,OUTSOURCING_APPROVE,OUTSOURCING_YN,media_id,req_cont  from  DAS.CART_CONT_TBL group by cart_no,master_id,down_stat,OUTSOURCING_APPROVE,OUTSOURCING_YN,media_id,req_cont ) cont ON cont.CART_NO = DCT.CART_NO  ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer JOIN (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID ");	
		buf.append("\n AND APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		buf.append("\n  AND (CONT.OUTSOURCING_YN='N' OR (CONT.OUTSOURCING_APPROVE='Y' and cont.OUTSOURCING_YN='Y'))	 ");
		buf.append("\n  and (cont.DOWN_STAT='005')	 ");
		buf.append("\n and app.PGM_ID is not null ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n order by DCT.CART_NO desc ");

		buf.append("\n ) union all ( ");

		
		
		
		
		
		buf.append("\n select  distinct ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path ");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n ,cont.req_cont ");
		buf.append("\n ,cont.media_id ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n INNER JOIN DAS.CART_CONT_TBL CONT ON CONT.CART_NO = DCT.CART_NO ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer JOIN DAS.approve_info_Tbl app ON  app.pgm_id = mst.PDS_CMS_PGM_ID and  USER.USER_NUM=app.APPROVE_USER_NUM ");	
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n and cont.OUTSOURCING_YN='Y' ");
		buf.append("\n  and (CONT.DOWN_STAT='004')	 ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		buf.append("\n order by DCT.CART_NO desc ");
		
		buf.append("\n )  ");
		return buf.toString();
	}

	
	/**
	 * My Sign 다운로드 승인 상세조회
	 */
	public static String selectMyDownloadAprroveDetailList()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select  distinct ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n  	case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm ");
		buf.append("\n  	when value(mt.PGM_ID,0) =0 and mt.CTGR_L_CD ='100' then mt.TITLE ");
		buf.append("\n else '' ");
		buf.append("\n end as title, ");
		buf.append("\n  value(mt.epis_no, '0') as epis_no,  ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.media_id, ");
		buf.append("\n 	cont.app_cont, ");
		buf.append("\n 	mt.fm_Dt, ");
		buf.append("\n 	mt.ctgr_l_cd, ");
		buf.append("\n CASE  ");
		buf.append("\n WHEN cart.VD_QLTY is null or  cart.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = cart.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN cart.ASP_RTO_CD is null or  cart.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = cart.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM, ");		
		buf.append("\n CASE   WHEN cont.RIST_CLF_CD is null or  cont.RIST_CLF_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'P018' and code.SCL_CD = cont.RIST_CLF_CD AND GUBUN='L'  ) ");
		buf.append("  END      AS RIST_CLF_NM,  ");
		
		buf.append("\n  CODE2.DESC as status ,");
		buf.append("\n 	cont.RIST_CLF_CD , ");
	
		buf.append("\n  cont.req_cont, ");		
		buf.append("\n  inst.FL_PATH, ");		
		buf.append("\n  inst.WRK_FILE_NM, ");		
		buf.append("\n  MT.master_id, ");	
		buf.append("\n  value(user2.user_nm,'') as approveid ");
		buf.append("\n    from das.approve_info_Tbl pds   ");
		buf.append("\n       left outer join das.METADAT_MST_TBL MT  on MT.pds_cms_pgm_ID=pds.pgm_id  ");
		buf.append("\n     	 left outer join das.pgm_info_tbl  pgm on pgm.pgm_id = MT.PGM_ID ");
		
		buf.append("\n      left outer join das.cart_cont_tbl cont on cont.master_id=MT.master_id ");
		buf.append("\n     left outer join das.user_info_Tbl user2 on user2.sbs_user_id = cont.approveid  ");
		buf.append("\n      inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no  ");
		buf.append("\n  LEFT OUTER JOIN DAS.CODE_TBL CODE2 ON CODE2.SCL_CD=cont.DOWN_STAT AND CODE2.CLF_CD='P061' ");		
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = cont.CT_ID and inst.CTI_FMT like '3%' ");		
		
		
		buf.append("\n where CONT.CART_NO = ? ");
		buf.append("\n and pds.APPROVE_USER_NUM like ? ");
		buf.append("\n and (cont.down_stat = '001'  or cont.down_stat = '005')");
		buf.append("\n AND cont.RIST_CLF_CD <>'003' ");
		
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인 상세조회(아카이브팀)
	 */
	public static String selectMyDownloadAprroveDetailListForArchive()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select  distinct ");
		buf.append("\n 	cont.CART_NO, ");
		buf.append("\n 	cont.CART_SEQ, ");
		buf.append("\n 	cont.CT_ID, ");
		buf.append("\n  	case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm ");
		buf.append("\n  	when value(mt.PGM_ID,0) =0 and mt.CTGR_L_CD ='100' then mt.TITLE ");
		buf.append("\n else '' ");
		buf.append("\n end as title, ");
		buf.append("\n  value(mt.epis_no, '0') as epis_no,  ");
		buf.append("\n  mt.brd_dd, ");
		buf.append("\n 	cont.SOM, ");
		buf.append("\n 	cont.EOM, ");
		buf.append("\n 	cont.S_FRAME, ");
		buf.append("\n 	cont.DURATION, ");
		buf.append("\n 	cont.media_id, ");
		buf.append("\n 	cont.app_cont, ");
		buf.append("\n 	mt.fm_Dt, ");
		buf.append("\n 	mt.ctgr_l_cd, ");
		buf.append("\n CASE  ");
		buf.append("\n WHEN cart.VD_QLTY is null or  cart.VD_QLTY = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A005' and code.SCL_CD = cart.VD_QLTY )  ");
		buf.append("\n END	 AS VD_QLTY_NM ");
		buf.append("\n ,CASE  ");
		buf.append("\n WHEN cart.ASP_RTO_CD is null or  cart.ASP_RTO_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'A006' and code.SCL_CD = cart.ASP_RTO_CD )  ");
		buf.append("\n END	 AS ASP_RTO_NM, ");		
		buf.append("\n CASE   WHEN cont.RIST_CLF_CD is null or  cont.RIST_CLF_CD = '' THEN ''   ");
		buf.append("\n ELSE (select code.DESC from DAS.CODE_TBL code where code.CLF_CD = 'P018' and code.SCL_CD = cont.RIST_CLF_CD AND GUBUN='L'  ) ");
		buf.append("  END      AS RIST_CLF_NM,  ");
		
		buf.append("\n  CODE2.DESC as status ,");
		buf.append("\n 	cont.RIST_CLF_CD , ");
	
		buf.append("\n  cont.req_cont, ");		
		buf.append("\n  inst.FL_PATH, ");		
		buf.append("\n  inst.WRK_FILE_NM, ");		
		buf.append("\n  MT.master_id, ");	
		buf.append("\n  value(user2.user_nm,'') as approveid ");
		buf.append("\n    from  das.METADAT_MST_TBL MT  ");
	buf.append("\n     	 left outer join das.pgm_info_tbl  pgm on pgm.pgm_id = MT.PGM_ID ");
		
		buf.append("\n      left outer join das.cart_cont_tbl cont on cont.master_id=MT.master_id ");
		buf.append("\n     left outer join das.user_info_Tbl user2 on user2.sbs_user_id = cont.approveid  ");
		buf.append("\n      inner join das.down_cart_tbl cart on cart.cart_no = cont.cart_no  ");
		buf.append("\n  LEFT OUTER JOIN DAS.CODE_TBL CODE2 ON CODE2.SCL_CD=cont.DOWN_STAT AND CODE2.CLF_CD='P061' ");		
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = cont.CT_ID and inst.CTI_FMT like '3%' ");		
		
		
		buf.append("\n where CONT.CART_NO = ? ");
		//buf.append("\n and pds.APPROVE_USER_NUM like ? ");
		buf.append("\n and (cont.down_stat = '001'  or cont.down_stat = '005')");
		buf.append("\n AND cont.RIST_CLF_CD <>'003' ");
		
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	
	/**
	 * My Sign 다운로드 승인 상세조회
	 */
	public static String selectMyDownloadAprroveDetailListForOutsosing()
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select  distinct ");
		buf.append("\n 	 cart.CART_NO, ");
		buf.append("\n 	cart.CART_SEQ, ");
		buf.append("\n 	cart.CT_ID, ");   
		buf.append("\n   cart.SOM,  ");    
		buf.append("\n cart.EOM, ");
		buf.append("\n  cart.S_FRAME,  ");
		buf.append("\n cart.DURATION, ");
		buf.append("\n cart.media_id,   ");
		buf.append("\n  cart.app_cont, ");
		buf.append("\n   cart.req_cont,  ");
		buf.append("\n 	cart.RIST_CLF_CD  , ");
		buf.append("\n 	mt.fm_Dt, ");   
		buf.append("\n 	mt.ctgr_l_cd, ");
		buf.append("\n  inst.FL_PATH, ");
		buf.append("\n 	inst.WRK_FILE_NM,  ");
		buf.append("\n 	 MT.master_id , ");
		buf.append("\n 	 case when value(mt.PGM_ID,0) !=0 and mt.CTGR_L_CD='200' then pgm.pgm_nm  ");
		buf.append("\n  when mt.CTGR_L_CD ='100' then mt.TITLE   ");
		buf.append("\n else ''  end as title,     ");
		buf.append("\n value(mt.epis_no, '0') as epis_no,  ");
		buf.append("\n  mt.brd_dd , ");
		buf.append("\n code3.desc as VD_QLTY_nm ,  ");
		buf.append("\n code4.desc as ASP_RTO_NM,  ");
		buf.append("\n code5.desc as  RIST_CLF_NM,  ");
		buf.append("\n code2.desc as  status , ");
		buf.append("\n  value(user2.user_nm,'') as approveid ");
		
		buf.append("\n    from das.cart_cont_Tbl cart   ");
		buf.append("\n   inner join das.down_cart_tbl down on down.cart_no = cart.cart_no   ");
		buf.append("\n   inner join das.METADAT_MST_TBL MT  on cart.MASTER_ID=MT.MASTER_ID ");
		buf.append("\n     left outer join das.user_info_tbl user2  on user2.sbs_user_id = cart.approveid ");
		buf.append("\n   left outer join das.pgm_info_tbl  pgm on pgm.pgm_id = MT.PGM_ID ");
		buf.append("\n   LEFT OUTER JOIN DAS.CODE_TBL CODE2 ON CODE2.SCL_CD=cart.DOWN_STAT AND CODE2.CLF_CD='P061'  ");
		buf.append("\n  LEFT OUTER JOIN DAS.CODE_TBL CODE3 ON CODE3.SCL_CD= down.VD_QLTY AND CODE3.CLF_CD='A005' ");		
		buf.append("\n  LEFT OUTER JOIN DAS.CODE_TBL CODE4 ON CODE4.SCL_CD=down.ASP_RTO_CD AND CODE4.CLF_CD='A006'");		
		buf.append("\n  LEFT OUTER JOIN DAS.CODE_TBL CODE5 ON CODE5.SCL_CD=cart.RIST_CLF_CD AND CODE5.CLF_CD='P018'  ");		
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = cart.CT_ID and inst.CTI_FMT like '3%'  ");		
		buf.append("\n    LEFT OUTER join DAS.APPROVE_INFO_TBL app on app.PGM_ID = mt.PDS_CMS_PGM_ID  ");		
		
		
		buf.append("\n where cart.CART_NO = ? ");

		buf.append("\n  and ((cart.down_stat = '001' or cart.down_stat = '004' )or(app.APPROVE_USER_NUM like ?))");
		
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	/**
	 * 외주승인 여부 조회.
	 */
	
	public static String selectOutSourcingInfo()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select              												\n");
		   buf.append(" \n		OUTSOURCING_APPROVE, OUTSOURCING_YN ,DOWN_STAT   \n");
			buf.append(" \n		from   cart_cont_tbl where 					\n");
			buf.append(" \n		cart_no = ?   	\n");
			buf.append("\n		and cart_seq = ? 	\n");
		
	
		return buf.toString();
	}

	

	/**
	 * 승인 여부 조회.
	 * 
	 * @param cart_no
	 *             카트번호
	 * @param cart_seq
	 *            카트순번
	 * @return
	 */
	public static String isApproveYn()
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n 	select              												\n");
		   buf.append(" \n		DOWN_STAT   \n");
			buf.append(" \n		from   cart_cont_tbl where 					\n");
			buf.append(" \n		cart_no = ?   	\n");
			buf.append("\n		and cart_seq = ? 	\n");
		
	
		return buf.toString();
	}

	
	public static String selectNewMyDownloadAprroveListForIfCms(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT                                                                                                  ");
		buf.append("\n 	mst.master_id, cart.CART_NO, cart.CART_SEQ,                                                           ");
		buf.append("\n     CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj,   ");
		buf.append("\n     dcart.file_path,                                                                                    ");
		buf.append("\n     dcart.STORAGENAME, cart.media_id, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT,      ");
		buf.append("\n     GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,       ");
		buf.append("\n     (                                                                                                   ");
		buf.append("\n     	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                       ");
		buf.append("\n         AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007')  ");
		buf.append("\n     ) AS use_limit_count                                                                                 ");
		buf.append("\n FROM DOWN_CART_TBL dcart                                                                                ");
		buf.append("\n 	inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                   ");
		buf.append("\n 	inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                         ");
		buf.append("\n     inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                    ");
		buf.append("\n     left outer JOIN PGM_INFO_TBL pgm ON mst.PGM_ID = pgm.PGM_ID                                         ");
		buf.append("\n     inner JOIN APPROVE_CHENNEL_TBL app ON mst.CHENNEL_CD = app.COCD                                     ");
		buf.append("\n WHERE cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005'                                              ");
		buf.append("\n 	AND SUBSTR(dcart.REG_DT, 1, 8) BETWEEN ? AND ?                                                          ");
		buf.append("\n 	AND app.USE_YN = 'Y' AND app.USER_ID = '"+cartItemDO.getUserid()+"'                                     ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n and dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n ORDER BY cart.CART_NO DESC, cart.CART_SEQ ASC                                                           ");
		
		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인조회(Ifcms 승인자)
	 * @param cartItemDO 조회정보가 담긴 beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveListForIfCms(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		
		buf.append("\n select distinct ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,cct.cart_seq ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,mst.title as DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");

		buf.append("\n ,dct.STORAGENAME ");
		buf.append("\n ,CCT.media_id ");
		buf.append("\n ,CCT.req_cont ");
		buf.append("\n ,CCT.master_id ");
		buf.append("\n ,CCT.RIST_CLF_CD ");
		buf.append("\n ,ANNOT_DESC.DESC as rist_clf_nm ");
		
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n  inner JOIN (select master_id,cart_no,down_stat,req_cont,media_id ,cart_seq,rist_clf_cd from  DAS.CART_CONT_TBL group by  master_id,cart_no,down_stat,req_cont,media_id ,cart_seq,rist_clf_cd ) CCT ON CCT.CART_NO = DCT.CART_NO  ");
		buf.append("\n INNER  JOIN   DAS.APPROVE_CHENNEL_TBL app on app.USER_ID = '"+cartItemDO.getUserid()+"' ");
		buf.append("\n INNER  JOIN   DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CCT.MASTER_ID and APP.COCD =mst.chennel_cd ");
		buf.append("\n   left outer join das.code_tbl annot_desc on annot_desc.scl_Cd=cct.RIST_CLF_CD and annot_desc.clf_Cd='P018' ");
		
		
			buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
	
		buf.append("\n  and ( cct.DOWN_STAT='005')	 ");
	
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n order by DCT.CART_NO desc ");

		return buf.toString();
	}
	
	/**
	 * My Sign 다운로드 승인조회(외주제작 용)
	 * @param cartItemDO 조회조건이 담긴beans
	 */
	public static String selectMyDownloadAprroveForOutSourcingListForIfCms(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select distinct");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,CONT.CART_SEQ ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,mst.title as DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path ");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n  ,cont.req_cont ");
		buf.append("\n  ,cont.media_id ");
		buf.append("\n  ,cont.master_id ");
		buf.append("\n  ,cont.rist_clf_cd ");
		buf.append("\n  ,annot_desc.desc as rist_clf_nm ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n inner JOIN (select cart_no,master_id,down_stat,OUTSOURCING_APPROVE,OUTSOURCING_YN,cart_seq ,req_cont,media_id,rist_clf_cd from  DAS.CART_CONT_TBL group by cart_no,master_id,down_stat,OUTSOURCING_APPROVE,OUTSOURCING_YN,cart_seq,req_cont,media_id,rist_clf_cd  ) cont ON cont.CART_NO = DCT.CART_NO  ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer join das.code_tbl annot_desc on annot_desc.scl_Cd=cont.RIST_CLF_CD and annot_desc.clf_Cd='P018' ");
		buf.append("\n left outer JOIN (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID ");	
		buf.append("\n AND APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		buf.append("\n  AND (CONT.OUTSOURCING_YN='N' OR (CONT.OUTSOURCING_APPROVE='Y' and cont.OUTSOURCING_YN='Y'))	 ");
		buf.append("\n  and (cont.DOWN_STAT='005')	 ");
		buf.append("\n and app.PGM_ID is not null ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n order by DCT.CART_NO desc ");

		buf.append("\n ) union all ( ");

		
		
		
		
		
		buf.append("\n select  distinct ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,CONT.CART_SEQ ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,DCT.DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path ");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n and (cont.RIST_CLF_CD is not null and cont.RIST_CLF_CD <> '') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n  ,cont.req_cont ");
		buf.append("\n  ,cont.media_id ");
		buf.append("\n ,cont.master_id ");
		buf.append("\n  ,cont.rist_clf_cd ");
		buf.append("\n  ,annot_desc.desc as rist_clf_nm ");
		
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n INNER JOIN DAS.CART_CONT_TBL CONT ON CONT.CART_NO = DCT.CART_NO ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer join das.code_tbl annot_desc on annot_desc.scl_Cd=cont.RIST_CLF_CD and annot_desc.clf_Cd='P018' ");
		buf.append("\n left outer JOIN DAS.approve_info_Tbl app ON  app.pgm_id = mst.PDS_CMS_PGM_ID and  USER.USER_NUM=app.APPROVE_USER_NUM ");	
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n and cont.OUTSOURCING_YN='Y' ");
		buf.append("\n  and (CONT.DOWN_STAT='004')	 ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		buf.append("\n order by DCT.CART_NO desc ");
		
		buf.append("\n )  ");
		return buf.toString();
	}

	
	
	public static String selectNewMyDownloadAprroveListForIfCms2(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT                                                                                        		  ");
		buf.append("\n 	mst.master_id, cart.CART_NO, cart.CART_SEQ,                                            				  ");
		buf.append("\n     CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj, ");
		buf.append("\n     dcart.file_path, dcart.STORAGENAME, cart.media_id, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT, ");
		buf.append("\n     GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,      ");
		buf.append("\n     (                                                                                                  ");
		buf.append("\n     	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                       ");
		buf.append("\n         AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007') ");
		buf.append("\n     ) AS use_limit_count,                                                                               ");
		buf.append("\n     app.APPROVE_USER_NUM                                                                               ");
		buf.append("\n FROM DOWN_CART_TBL dcart                                                                               ");
		buf.append("\n 	inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                   ");
		buf.append("\n 	inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                         ");
		buf.append("\n     inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                   ");
		buf.append("\n     inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                                 ");
		buf.append("\n WHERE cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005' AND RTRIM(value(app.DEPT_CD, '')) <> ''     ");
		buf.append("\n 	   AND SUBSTR(dcart.REG_DT, 1, 8) BETWEEN ? AND ?                                                     ");
		buf.append("\n     AND app.APPROVE_USER_NUM = '"+cartItemDO.getUserid().substring(1, 7)+"'       ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n and dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n ORDER BY cart.CART_NO DESC, cart.CART_SEQ ASC                                                          ");
		
		return buf.toString();
	}
	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveListForIfCms2(CartItemDO cartItemDO)
	{
		StringBuffer buf  = new StringBuffer();
		
		buf.append("\n select ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,cont.cart_seq ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,mst.title as DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");
		buf.append("\n ,dct.STORAGENAME ");
		buf.append("\n ,cont.media_id ");
		buf.append("\n ,cont.req_CONT ");
		buf.append("\n ,cont.master_id ");
		buf.append("\n ,cont.rist_clf_cd ");
		buf.append("\n ,annot_desc.desc as rist_clf_nm ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n  inner JOIN (select master_id,cart_no,down_stat,cart_seq,req_cont,media_id,rist_clf_cd from  DAS.CART_CONT_TBL group by  master_id,cart_no,down_stat ,cart_seq,req_cont,media_id,rist_clf_cd) cont ON cont.CART_NO = DCT.CART_NO  ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer JOIN (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID  ");	
		buf.append("\n left outer join das.code_tbl annot_desc on annot_desc.scl_Cd=cont.RIST_CLF_CD and annot_desc.clf_Cd='P018'  ");	
		buf.append("\n AND APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
		buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
	
		buf.append("\n  and ( cont.DOWN_STAT='005')	 ");
		buf.append("\n and app.PGM_ID is not null ");
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		if(!cartItemDO.getDownSubj().equals("")){
			
			buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n order by DCT.CART_NO desc ");

	
		return buf.toString();
	}
	
	public static String selectNewMyDownloadAprroveListForIfCms2(CartItemDO cartItemDO,String dept_cd)
	{
		StringBuffer buf  = new StringBuffer();
		buf.append("\n SELECT                                                                                                ");
		buf.append("\n 	mst.master_id, cart.CART_NO, cart.CART_SEQ,                                                          ");
		buf.append("\n     CASE WHEN RTRIM(VALUE(dcart.DOWN_SUBJ, '')) <> '' then mst.title||'('||dcart.down_subj||')' ELSE  mst.title END AS down_subj, ");
		buf.append("\n     dcart.file_path, dcart.STORAGENAME, cart.media_id, cart.REQ_CONT, cart.RIST_CLF_CD, user.user_nm, dcart.REQ_DT, ");
		buf.append("\n     GET_CODE_NM('P018', cart.RIST_CLF_CD) as rist_clf_nm, GET_CODE_NM('P058', user.COCD) AS conm,      ");
		buf.append("\n     (                                                                                                  ");
		buf.append("\n     	SELECT COUNT(1) FROM CART_CONT_TBL ccart WHERE cart.CART_NO = ccart.CART_NO                       ");
		buf.append("\n         AND (ccart.RIST_CLF_CD IS NULL OR RTRIM(ccart.RIST_CLF_CD) = '' OR ccart.RIST_CLF_CD <> '007') ");
		buf.append("\n     ) AS use_limit_count                                                                               ");
		buf.append("\n FROM DOWN_CART_TBL dcart                                                                               ");
		buf.append("\n 	inner JOIN USER_INFO_TBL user ON dcart.REQ_USRID = user.SBS_USER_ID                                   ");
		buf.append("\n 	inner JOIN CART_CONT_TBL cart ON dcart.CART_NO = cart.CART_NO                                         ");
		buf.append("\n     inner JOIN METADAT_MST_TBL mst ON cart.MASTER_ID = mst.MASTER_ID                                   ");
		// 관리자가 아니면 부서 및 사용자 조건 추가
		if(DASBusinessConstants.ADMIN_DEP.contains(dept_cd.trim())) {
			buf.append("\n 	WHERE 1=1                                              											  ");
		} else {
			buf.append("\n     inner JOIN APPROVE_INFO_TBL app ON mst.PDS_CMS_PGM_ID = app.PGM_ID                             ");
			buf.append("\n WHERE  RTRIM(value(app.DEPT_CD, '')) <> '' and app.DEPT_CD = '"+dept_cd.trim()+"'     		      ");
			buf.append("\n    AND app.APPROVE_USER_NUM = '"+cartItemDO.getUserid().substring(1, 7)+"'    ");
		}
		buf.append("\n 	AND cart.DOWN_STAT = '005' AND dcart.DOWN_GUBUN <> '005'                                              ");
		buf.append("\n 	AND SUBSTR(dcart.REG_DT, 1, 8) BETWEEN ? AND ?                                                        ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(cartItemDO.getDownSubj())) {
			buf.append("\n and dcart.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
		}
		buf.append("\n ORDER BY cart.CART_NO DESC, cart.CART_SEQ ASC                                                          ");
		
		return buf.toString();
	}
	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 */
	@Deprecated
	public static String selectMyDownloadAprroveListForIfCms2(CartItemDO cartItemDO,String dept_cd)
	{
		StringBuffer buf  = new StringBuffer();
		
		buf.append("\n select distinct ");
		buf.append("\n DCT.CART_NO ");
		buf.append("\n ,cont.cart_seq ");
		buf.append("\n ,DCT.REQ_DT ");
		buf.append("\n ,mst.title as DOWN_SUBJ ");
		buf.append("\n ,DCT.file_path");
		buf.append("\n ,( select count(1) from DAS.CART_CONT_TBL cont   ");
		buf.append("\n 	where cont.CART_NO = DCT.CART_NO   ");
		buf.append("\n  and ((cont.RIST_CLF_CD is  null or cont.RIST_CLF_CD = '') or cont.rist_clf_Cd<>'007') ");
		buf.append("\n 	) AS USE_LIMIT_COUNT ");
		//buf.append("\n ,DCT.APP_CONT ");
		buf.append("\n ,CODE.DESC as conm");
		buf.append("\n  ,USER.USER_NM ");

		buf.append("\n ,dct.STORAGENAME ");
		buf.append("\n ,cont.media_id ");
		buf.append("\n ,cont.req_CONT ");
		buf.append("\n ,cont.master_id ");
		buf.append("\n ,cont.rist_clf_cd ");
		buf.append("\n ,annot_desc.desc as rist_clf_nm ");
		buf.append("\n FROM DAS.DOWN_CART_TBL DCT ");
		buf.append("\n inner  JOIN DAS.USER_INFO_TBL USER ON USER.SBS_USER_ID=DCT.REQ_USRID  ");
		buf.append("\n  left outer join (select cocd from das.dep_info_tbl  group by cocd) dep on dep.cocd = user.cocd ");
		buf.append("\n LEFT OUTER JOIN DAS.CODE_TBL CODE ON CODE.SCL_CD=dep.cocd AND CODE.CLF_CD='P058' ");
		buf.append("\n INNER JOIN DAS.CART_CONT_TBL CONT ON CONT.CART_NO = DCT.CART_NO ");
		buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MST ON MST.MASTER_ID = CONT.MASTER_ID ");
		buf.append("\n left outer join das.code_tbl annot_desc on annot_desc.scl_Cd=cont.RIST_CLF_CD and annot_desc.clf_Cd='P018' ");
		
	
		if(dept_cd.trim().equals("D3OB01")){
			buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
		}else{
			 buf.append("\n left outer JOIN  (select dept_cd,pgm_id,APPROVE_USER_NUM from DAS.approve_info_Tbl group by  dept_cd,pgm_id,APPROVE_USER_NUM ) app ON  app.pgm_id = mst.PDS_CMS_PGM_ID   ");

			buf.append("\n  WHERE LEFT(DCT.reg_DT,8) >= ? AND LEFT(DCT.reg_DT,8) <= ? ");
			buf.append("\n AND ( (APP.APPROVE_USER_NUM LIKE '%"+cartItemDO.getUserid().substring(1, 7)+"%' ");
			buf.append("\n and  APP.dept_cd = '"+dept_cd.trim()+"') )");
		}  
		buf.append("\n  and (cont.DOWN_STAT='005')	 ");
	if(!cartItemDO.getDownSubj().equals("")){
		
		buf.append("\n and dct.down_subj like '%"+cartItemDO.getDownSubj()+"%' ");
	}
		buf.append("\n and dct.DOWN_GUBUN <>'005' ");
		buf.append("\n order by DCT.CART_NO desc ");

	
		return buf.toString();
	}
	
	
}

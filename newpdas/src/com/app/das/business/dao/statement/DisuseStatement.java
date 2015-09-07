package com.app.das.business.dao.statement;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.ArchiveIngestInfoDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DisuseConditionDO;
import com.app.das.util.CodeCommon;
import com.app.das.util.StringUtils;

/**
 * 폐기에 대한 SQL 쿼리에 대한 정의가 되어 있다.
 * @author ysk523
 *
 */
public class DisuseStatement 
{
	
	/**
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param searchFlag 검색조건
	 * @return DisuseDO 를 포함하고 있는 List
	 */
	public static String selectDisuseTargetListQuery(DisuseConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	master.MASTER_ID, ");
			buf.append("\n 	pgm.PGM_NM, ");
			buf.append("\n 	master.EPIS_NO, ");
			buf.append("\n 	master.TITLE, ");
			buf.append("\n 	master.RSV_PRD_CD, ");
			//보존기간
			String rsvPrdCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ARCHIVE_PERIOD, "master.RSV_PRD_CD");
			buf.append(rsvPrdCodeName + " AS RSV_PRD_NM, ");
	
			buf.append("\n 	master.ARRG_END_DT, ");
			buf.append("\n 	master.RSV_PRD_END_DD, ");
			buf.append("\n 	master.CTGR_L_CD, ");         
			buf.append("\n 	master.CTGR_M_CD, ");
			buf.append("\n 	master.CTGR_S_CD, ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			count(1)  ");
			buf.append("\n 		from DAS.CTI_USE_FRQ_TBL frq  ");
			buf.append("\n 		where frq.MASTER_ID = master.MASTER_ID ");
			buf.append("\n 	) AS USE_COUNT, ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			count(1)  ");
			buf.append("\n 		from DAS.CTI_USE_FRQ_TBL frq  ");
			buf.append("\n 		where frq.MASTER_ID = master.MASTER_ID  ");
			buf.append("\n 		and frq.REQ_DD between hex((current date - 1 year)) and hex(current date) ");
			buf.append("\n 	) AS ONE_YEAR_COUNT, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by pgm.PGM_NM) AS rownum ");
		}
		buf.append("\n from DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL master ");
		buf.append("\n where pgm.PGM_ID = master.PGM_ID ");
		buf.append("\n 		and not EXISTS(select 1 from DAS.DISUSE_INFO_TBL where DATA_ID = master.MASTER_ID) ");
		if(!StringUtils.isEmpty(conditionDO.getFromDate()))
		{
			buf.append("\n 	and master.RSV_PRD_END_DD >= '"+conditionDO.getFromDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getToDate()))
		{
			buf.append("\n 	and master.RSV_PRD_END_DD <= '"+conditionDO.getToDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getSearchValue()))
		{
			if(StringUtils.isNumeric(conditionDO.getSearchValue()))
			{
				buf.append("\n 	and (master.EPIS_NO = "+conditionDO.getSearchValue()+" or master.TITLE like '"+conditionDO.getSearchValue()+"' or pgm.PGM_NM like '"+conditionDO.getSearchValue()+"') ");
			}
			else
			{
				buf.append("\n 	and (master.TITLE like '%"+conditionDO.getSearchValue()+"%' or pgm.PGM_NM like '%"+conditionDO.getSearchValue()+"%') ");
			}
		}
		buf.append("\n ) temp2 ");
		if(!StringUtils.isEmpty(conditionDO.getUseCountFrom()) && !DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	where USE_COUNT >= "+conditionDO.getUseCountFrom()+" ");
		}
		if(!StringUtils.isEmpty(conditionDO.getUseCountTo()) && !DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	and USE_COUNT <= "+conditionDO.getUseCountTo()+" ");
		}
		return buf.toString();
	}
	/**
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param userId 사용자id
	 * @param searchFlag 검색조건
	 * @return PageDO 를 포함하고 있는 DataObject
	 */
	public static String selectDisuseListQuery(DisuseConditionDO conditionDO, String userId, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	info.DISUSE_NO, "); 
			buf.append("\n 	info.DATA_NM, "); 
			buf.append("\n 	info.DATA_ID, "); 
			buf.append("\n 	info.DATA_CLF_CD,  ");
			buf.append("\n 	info.DISUSE_DD, "); 
			buf.append("\n 	info.DISUSE_RSL, "); 
			buf.append("\n 	info.DISUSE_CLF, "); 
			buf.append("\n 	info.EXTS_RSL, "); 
			buf.append("\n 	info.EXTS_DT, "); 
			buf.append("\n 	info.EXTS_CD, "); 
			//보존기간
			String extsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ARCHIVE_PERIOD, "info.EXTS_CD");
			buf.append(extsCodeName + " AS EXTS_NM, ");
	
			buf.append("\n 	info.CTGR_L_CD, "); 
			buf.append("\n 	info.CTGR_M_CD, "); 
			buf.append("\n 	info.CTGR_S_CD, "); 
			buf.append("\n 	info.USE_COUNT, "); 
			buf.append("\n 	info.BY_Y_USE_COUNT, "); 
			buf.append("\n 	info.DISUSE_FST_SLT_DD, "); 
			buf.append("\n 	info.DISUSER_RV_DD, "); 
			buf.append("\n 	info.DI_CONFIRM_DD, ");
			buf.append("\n 	master.RSV_PRD_CD, ");
			//보존기간
			String rsvPrdCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ARCHIVE_PERIOD, "master.RSV_PRD_CD");
			buf.append(rsvPrdCodeName + " AS RSV_PRD_NM, ");
			buf.append("\n 	master.ARRG_END_DT, ");
			buf.append("\n 	master.RSV_PRD_END_DD, ");
			buf.append("\n	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' and code.SCL_CD = info.CTGR_L_CD) AS L_USER_ID, ");
			buf.append("\n	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' and code.SCL_CD = info.CTGR_M_CD) AS M_USER_ID, ");
			buf.append("\n	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' and code.SCL_CD = info.CTGR_S_CD) AS S_USER_ID, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by info.DISUSE_NO) AS rownum ");
		}
		buf.append("\n from DAS.DISUSE_INFO_TBL info, DAS.METADAT_MST_TBL master ");
		buf.append("\n where info.DATA_ID = master.MASTER_ID ");
		buf.append("\n 	and info.DISUSE_CLF = '"+conditionDO.getDisuseClf()+"' ");
		if(!StringUtils.isEmpty(conditionDO.getFromDate()))
		{
			buf.append("\n 	and info.DISUSER_RV_DD >= '"+conditionDO.getFromDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getToDate()))
		{
			buf.append("\n 	and info.DISUSER_RV_DD <= '"+conditionDO.getToDate()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getLargeCategory()))
		{
			buf.append("\n 	and info.CTGR_L_CD = '"+conditionDO.getLargeCategory()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getMidiumCategory()))
		{
			buf.append("\n 	and info.CTGR_M_CD = '"+conditionDO.getMidiumCategory()+"' ");
		}
		if(!StringUtils.isEmpty(conditionDO.getSmallCategory()))
		{
			buf.append("\n 	and info.CTGR_S_CD = '"+conditionDO.getSmallCategory()+"' ");
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getDisuseUserFlag()))
		{
			buf.append("\n 	and info.DISUSER_ID = '"+userId+"' ");
		}
		
		return buf.toString();
	}
	
	/**
	 * 폐기 취소 Process를 수행하기위한 폐기구분에 해당하는 목록 조회를 한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 * @return List DisuseDO 를 포함하고 있는 List
	 */
	public static String selectDisuseCancelListQuery(DisuseConditionDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	info.DISUSE_NO, "); 
			buf.append("\n 	info.DATA_NM,  ");
			buf.append("\n 	info.DATA_ID, "); 
			buf.append("\n 	info.DATA_CLF_CD, "); 
			buf.append("\n 	info.DISUSE_DD,  ");
			buf.append("\n 	info.DISUSE_RSL,  ");
			buf.append("\n 	info.DISUSE_CLF,  ");
			buf.append("\n 	info.EXTS_RSL,  ");
			buf.append("\n 	info.EXTS_DT,  ");
			buf.append("\n 	info.EXTS_CD, "); 
			//보존기간
			String extsCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ARCHIVE_PERIOD, "info.EXTS_CD");
			buf.append(extsCodeName + " AS EXTS_NM, ");
			
			buf.append("\n 	info.CTGR_L_CD,  ");
			buf.append("\n 	info.CTGR_M_CD,  ");
			buf.append("\n 	info.CTGR_S_CD,  ");
			buf.append("\n 	info.USE_COUNT,     ");
			buf.append("\n 	info.BY_Y_USE_COUNT,  ");
			buf.append("\n 	info.DISUSE_FST_SLT_DD,  ");
			buf.append("\n 	info.DISUSER_RV_DD,  ");
			buf.append("\n 	info.DI_CONFIRM_DD, ");
			buf.append("\n 	master.RSV_PRD_CD, ");
			String rsvPrdCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_ARCHIVE_PERIOD, "master.RSV_PRD_CD");
			buf.append(rsvPrdCodeName + " AS RSV_PRD_NM, ");
	
			buf.append("\n 	master.ARRG_END_DT, ");
			buf.append("\n 	master.RSV_PRD_END_DD, ");
			buf.append("\n 	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' and code.SCL_CD = info.CTGR_L_CD) AS L_USER_ID, ");
			buf.append("\n 	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' and code.SCL_CD = info.CTGR_M_CD) AS M_USER_ID, ");
			buf.append("\n 	(select code.RMK_2 from DAS.CODE_TBL code where code.CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' and code.SCL_CD = info.CTGR_S_CD) AS S_USER_ID, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by info.DISUSE_NO) AS rownum ");
		}
		buf.append("\n from DAS.DISUSE_INFO_TBL info, DAS.METADAT_MST_TBL master ");
		buf.append("\n where info.DATA_ID = master.MASTER_ID ");
		buf.append("\n 	and info.DISUSE_CLF = '"+conditionDO.getDisuseClf()+"' ");
		if(CodeConstants.DisuseKind.FIRST_CHOICE.equals(conditionDO.getDisuseClf())
				|| CodeConstants.DisuseKind.INVESTIGATION.equals(conditionDO.getDisuseClf()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and info.DISUSE_FST_SLT_DD >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and info.DISUSE_FST_SLT_DD <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.DisuseKind.DATA_INFO_DISCUSSION.equals(conditionDO.getDisuseClf()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and info.DISUSER_RV_DD >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and info.DISUSER_RV_DD <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.DisuseKind.DISUSE_COMPLETION.equals(conditionDO.getDisuseClf()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and info.DI_CONFIRM_DD >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and info.DI_CONFIRM_DD <= '"+conditionDO.getToDate()+"' ");
			}
		}
		else if(CodeConstants.DisuseKind.DISUSE_CANCEL.equals(conditionDO.getDisuseClf()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and info.EXTS_DT >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and info.EXTS_DT <= '"+conditionDO.getToDate()+"' ");
			}
		}
		
		return buf.toString();
	}
	/**
	 * 인제스트 된 목록
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 * @return String DisuseDO 를 포함하고 있는 List
	 */
	public static String selectArchiveIngestQuery(ArchiveIngestInfoDO conditionDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n A.TAPE_ITEM_ID, ");
			buf.append("\n A.TITLE, ");
			buf.append("\n C.CT_NM, ");
			buf.append("\n D.CTI_ID, ");
			buf.append("\n D.ARCH_STE_YN, ");
			buf.append("\n D.DTL_YN, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by A.MASTER_ID) AS rownum ");
		}
		buf.append("\n from DAS.METADAT_MST_TBL A ");
		buf.append("\n INNER JOIN DAS.CONTENTS_MAPP_TBL B ON B.MASTER_ID = A.MASTER_ID  ");
		buf.append("\n INNER JOIN DAS.CONTENTS_TBL C ON C.CT_ID = B.CT_ID  ");
		buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL D ON D.CT_ID = C.CT_ID ");
		if (conditionDO.getToDate() != null && !"".equals(conditionDO.getToDate())) {
			buf.append("\n where SUBSTR(A.ING_REG_DD, 1, 8) >= '" + conditionDO.getFromDate() + "' and SUBSTR(A.ING_REG_DD, 1, 8) <= '" + conditionDO.getToDate() + "' ");
			if (!"".equals(conditionDO.getArchiveState())) {
				buf.append("\n 		AND D.ARCH_STE_YN = '" + conditionDO.getArchiveState() + "'");
			}
		}
		
		return buf.toString();
	}
	
	
	

	/**
	 *  폐기 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @param searchFlag 조회조건
	 * @return
	 */
	public static String selecDiscardList(DiscardDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{           
		buf.append("\n 	 CASE WHEN CON.MEDIA_ID IS NULL THEN  ' '  ");
		buf.append("\n 	  ELSE CON.MEDIA_ID   ");
		buf.append("\n 	   END AS MEDIA_ID, ");
			buf.append("\n 	 MST.CTGR_L_CD, ");

			buf.append("\n 	 case when mst.ctgr_l_cd ='100' then  MST.TITLE  ");
			buf.append("\n 	 when mst.ctgr_l_cd = '200' and mst.pgm_id !=0 then  pgm.pgm_nm   ");
			buf.append("\n 	 ELSE  MST.TITLE  END as TITLE, ");
				
			buf.append("\n 	  case when mst.ctgr_l_cd ='100' then mst.fm_dt  ");
			buf.append("\n 	  when mst.ctgr_l_cd = '200'  then  mst.brd_dd   ");
			buf.append("\n 	   ELSE  ''  ");
			buf.append("\n 	 END as brd_dd, ");
			
			buf.append("\n 	value(MST.RSV_PRD_END_DD,'') as RSV_PRD_END_DD,  ");
			buf.append("\n 	MST.RSV_PRD_CD, ");
			buf.append("\n 	MST.BRD_LENG, ");
			buf.append("\n 	MST.MASTER_ID, ");
			/*buf.append("\n 	CASE WHEN 	DIS.DISUSE_STA IS NULL THEN  ' '  ");
			buf.append("\n ELSE 	DIS.DISUSE_STA   ");
			buf.append("\n 	  END AS DISUSE_STA, ");*/
			buf.append("\n  CASE WHEN cart.cnt IS NULL THEN  0 ");
			buf.append("\n  ELSE CART.CNT  ");
			buf.append("\n  END AS cnt, ");
			buf.append("\n 	 value(MST.SUB_TTL, '') as sub_ttl,  ");
			buf.append("\n 	  value(MST.epis_no, 0) as epis_no,  ");
			//buf.append("\n 	  value(user.user_nm,'') as user_nm,  ");
			buf.append("\n 	ROW_NUMBER() OVER(order by  MST.ING_REG_DD) AS rownum ");
		}
		buf.append("\n from DAS.METADAT_MST_TBL MST   ");		
		buf.append("\n LEFT OUTER JOIN DAS.DISCARD_INFO_TBL DIS   ON DIS.MASTER_ID = MST.MASTER_ID   ");	
		buf.append("\n  LEFT OUTER JOIN  (select master_id, ct_id from DAS.CONTENTS_MAPP_TBL group by master_id, ct_id) MAP   ON MAP.MASTER_ID = MST.MASTER_ID  ");		
		buf.append("\n  LEFT OUTER JOIN DAS.CONTENTS_TBL CON   ON CON.CT_ID = MAP.CT_ID    ");	
		buf.append("\n  LEFT OUTER JOIN DAS.pgm_info_Tbl pgm   ON pgm.pgm_id = mst.pgm_id    ");	
		//buf.append("\n  left outer join das.user_info_tbl user on user.sbs_user_id = dis.reg_id   ");	
		buf.append("\n  left outer join  (SELECT COUNT(MASTER_ID) AS CNT,MASTER_id FROM (   ");		
		buf.append("\n   select cart_no,master_id    ");	
		buf.append("\n  FROM DAS.CART_CONT_TBL   ");		
		buf.append("\n  WHERE  DOWN_DT <> ''   ");	
		buf.append("\n  group by cart_no,master_id ) SU GROUP BY MASTER_ID    ");		
		buf.append("\n  )  cart on cart.MASTER_ID = MST.MASTER_ID    ");	
	
		
		
		   
			buf.append("\n where 1=1");
			buf.append("\n   and con.ct_typ='003'  ");
			
			if(!condition.getCtgr_l_cd().equals("")){
				buf.append("\n  and MST.ctgr_l_cd = '"+condition.getCtgr_l_cd()+"'  ");
				}
			if(!condition.getRsv_prd_start_dd().equals("")){
				buf.append("\n  and MST.RSV_PRD_END_DD >= '"+condition.getRsv_prd_start_dd()+"000000'  ");
				}
			
		if(!condition.getRsv_prd_end_dd().equals("")){			
		
		
			buf.append("\n  and MST.RSV_PRD_END_DD <= '"+condition.getRsv_prd_end_dd()+"999999'  ");
			}
		
		
		
		
		if(!condition.getStart_brd_dd().equals("")){
			buf.append("\n  and (( MST.brd_dd >= '"+condition.getStart_brd_dd()+"'  ");
			}
		
	if(!condition.getEnd_brd_dd().equals("")){			
	
	
		buf.append("\n  and MST.brd_dd <= '"+condition.getEnd_brd_dd()+"' ) ");
		}
		
	if(!condition.getStart_brd_dd().equals("")){
		buf.append("\n  or ( MST.fm_dt >= '"+condition.getStart_brd_dd()+"'  ");
		}
	
	if(!condition.getEnd_brd_dd().equals("")){			


	buf.append("\n  and MST.fm_dt <= '"+condition.getEnd_brd_dd()+"' )) ");
	}
		
		if( !condition.getRsv_prd_cd().equals("")){
		
			
			buf.append("\n  and  MST.RSV_PRD_CD = '"+condition.getRsv_prd_cd()+"'  ");
			}
		
		if( !condition.getMedia_id().equals("")){
			
			
			buf.append("\n   and CON.MEDIA_ID LIKE '%"+condition.getMedia_id()+"%'  ");
			}
		if( condition.getMaster_id()!=0){
			
			
			buf.append("\n   and mst.master_id ="+condition.getMaster_id()+"  ");
			}
		
		
		if(!condition.getTitle().equals("")){
			buf.append("\n   and MST.TITLE like '%"+condition.getTitle()+"%'  ");
			}
//		if(!condition.getReg_nm().equals("")){
//			buf.append("\n   and user.user_nm like '%"+condition.getReg_nm()+"%'  ");
//			}
//		
		

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
				buf.append("\n 	(MST.arch_route LIKE 'D%') ");
			}
			else if(path[i].equals("O"))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  MST.arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");
				
			}else if(path[i].equals("P")){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( MST.arch_route ='P' )");
		
			}
		}
		buf.append("\n 	 ) ");
	 	}
	 	//2012.4.24 조회조건 추가
	 	if(!condition.getCocd().equals("")){
	 		
		 	buf.append("\n  AND MST.cocd like  '" + condition.getCocd()+"%'");

		 	
		 	}
	 	if(!condition.getChennel().equals("")){
	 		
		 	buf.append("\n   AND MST.chennel_cd like  '" + condition.getChennel()+"%'");

		 	
		 	}
		
		
		
	
			buf.append("\n 	 AND (DIS.DISUSE_YN <> 'Y' OR DIS.DISUSE_YN IS NULL)");
			
if( !condition.getFrom_use().equals("")||!condition.getTo_use().equals("")){
				
				if(condition.getFrom_use().equals("0")||condition.getTo_use().equals("0")){
					buf.append("\n  and (cnt is null or (cnt >='0' and cnt <= '"+condition.getTo_use()+"'))  "); 
				}else {
				if(!condition.getFrom_use().equals("")){
				buf.append("\n  and  cnt >= '"+condition.getFrom_use()+"'  ");
				}
				if(!condition.getTo_use().equals("")){
					 if(condition.getTo_use().equals("0")) {
						
					}
					buf.append("\n  and  cnt <= '"+condition.getTo_use()+"'  ");
						}
				}
			
			}
buf.append("\n and mst.ARCH_REG_DD<>'' ");
buf.append("\n and (mst.DEL_DD='' or mst.del_dd is null ) ");
buf.append("\n    AND (mst.master_ID NOT IN (   ");
buf.append("\n   SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM   ");
buf.append("\n   INNER JOIN ( SELECT MASTER_ID FROM METADAT_MST_TBL    ");
buf.append("\n   WHERE DEL_DD IS NULL OR DEL_DD > TS_FMT(CURRENT DATE,'yyyymmdd') or REPLACE(del_dd,' ','')=''   ");
buf.append("\n    ) PM ON PM.MASTER_ID =RM.PARENT_MASTER_ID    ");
buf.append("\n   GROUP BY CHILD_MASTER_ID  ");
buf.append("\n      ) )  ");


if(!DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
{
	buf.append("\n order by   CART.CNT desc, mst.title asc, mst.brd_dd ");
}

	

		return buf.toString();
	}
	
	/**
	 * 폐기 현황을 조회한다
	 * @author asura
	 *
	 */
	public static String selecHyenDiscardList(DiscardDO condition)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		buf.append("\n 	dis.master_id, ");
			buf.append("\n 	dis.SEQ, ");
			buf.append("\n 	case when  MST.CTGR_L_CD ='100' and mst.PGM_ID=0 then mst.TITLE ");
			buf.append("\n 	WHEN MST.CTGR_L_CD ='200' and mst.PGM_ID!= 0 THEN pgm.pgm_nm ");
			buf.append("\n 	else mst.title  end as title,  ");
			
			buf.append("\n 	CASE WHEN MST.CTGR_L_CD ='100' THEN MST.FM_DT ");
			buf.append("\n 	 WHEN MST.CTGR_L_CD ='200' THEN MST.BRD_DD ");
			buf.append("\n 	 ELSE '' ");
			buf.append("\n 	  END AS BRD_DD, ");
			buf.append("\n 	dis.BRD_LEN, ");
			buf.append("\n 	dis.DISUSE_CONT, ");
			buf.append("\n 	dis.REG_DT, ");
			buf.append("\n 	dis.RSV_PRD_END_DD, ");
			buf.append("\n 	CASE WHEN  (map.DEL_DD IS NOT NULL OR map.DEL_DD <> ''  ) and map.del_yn='N' then ''    ");
			buf.append("\n 	  when (map.DEL_DD IS NOT NULL OR map.DEL_DD <> '') and map.DEL_YN ='Y'  then map.DEL_DD  ");
			buf.append("\n 	 else ''    ");
			buf.append("\n 	END AS DEL_DD ,      ");
			buf.append("\n  CASE WHEN  (map.DEL_DD IS NOT NULL OR map.DEL_DD <> ''  ) and map.del_yn='N' then '폐기신청'  ");
			buf.append("\n 	 when (map.DEL_DD IS NOT NULL OR map.DEL_DD <> '') and map.DEL_YN ='Y'  then '폐기완료'  ");
			buf.append("\n 	else ''  ");
			buf.append("\n END AS DISUSE_STA ,    ");
			buf.append("\n value(user.user_nm ,'' ) as user_nm,    ");
			
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY 	dis.SEQ DESC) AS rownum ");
	
		buf.append("\n from DAS.DISCARD_INFO_TBL dis ");		
		buf.append("\n inner join metadat_mst_tbl mst on mst.MASTER_ID=dis.MASTER_ID ");
		buf.append("\n inner join (select del_dd, del_yn,master_id from contents_mapp_tbl group by del_dd, del_yn,master_id) map on map.MASTER_ID = dis.MASTER_ID ");
		buf.append("\n left outer join pgm_info_tbl pgm on pgm.pgm_id = mst.PGM_ID ");
		buf.append("\n left outer join user_info_tbl user on user.sbs_user_id = dis.reg_id ");
		buf.append("\n where  dis.DISUSE_YN = 'Y'");

		
		
		
		if(!condition.getRsv_prd_start_dd().equals("") || !condition.getRsv_prd_end_dd().equals("") || !condition.getRsv_prd_cd().equals("")){
			
			
			if(condition.getRsv_prd_start_dd().equals("")){			
		}else {buf.append("\n AND  dis.reg_dt >= '"+condition.getRsv_prd_start_dd()+"000000'  ");
		}
		
		if(condition.getRsv_prd_end_dd().equals("")){			
		}else {
			
			buf.append("\n AND dis.reg_dt <= '"+condition.getRsv_prd_end_dd()+"999999'  ");
			}
		
		if(condition.getRsv_prd_start_dd()==condition.getRsv_prd_end_dd()){
			buf.append("\n AND dis.reg_dt =  '"+condition.getReg_dt()+"'  ");
			}
	
		}
		if(!condition.getStatus().equals("")){
			if(condition.getStatus().equals("000")){
			buf.append("\n AND MAP.DEL_YN  =  'N'  ");
			}else if(condition.getStatus().equals("004")){
			buf.append("\n AND MAP.DEL_YN =  'Y'  ");
			}
		}

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
				buf.append("\n 	(mst.arch_route LIKE 'D%') ");
			}
			else if(path[i].equals("O"))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  mst.arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");
				
			}else if(path[i].equals("P")){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( mst.arch_route ='P' )");
		
			}
		}
		buf.append("\n 	 ) ");
	 	}
	 	//2012.4.24 조회조건 추가
	 	if(!condition.getCocd().equals("")){
	 		
		 	buf.append("\n  AND mst.cocd like  '" + condition.getCocd()+"%'");

		 	
		 	}
	 	if(!condition.getChennel().equals("")){
	 		
		 	buf.append("\n   AND mst.chennel_cd like  '" + condition.getChennel()+"%'");

		 	
		 	}
		
		
		if(!condition.getReg_nm().equals("")){
			buf.append("\n AND user_nm ='%"+condition.getReg_nm()+"%'  ");
			}
		buf.append("\n  order by dis.REG_DT desc,title asc, BRD_DD asc  ");
		return buf.toString();
	}
	
	
	/**
	 *  폐기 현황를 전체수를 조회한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 */
	public static String selecHyenDiscardCount()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
	
			buf.append("\n 	count(*) ");
			
		buf.append("\n from DAS.DISCARD_INFO_TBL ");		
		buf.append("\n where  DISUSE_YN = 'Y'");
		
	
		return buf.toString();
	}
	
	
	/**
	 * 연장 현황을 조회한다
	 * @author asura
	 *
	 */
	public static String selecHyenUseList(DiscardDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");     
			buf.append("\n 	discard.SEQ, ");
			buf.append("\n 	discard.MASTER_ID, ");
			buf.append("\n 	discard.TITLE, ");
			buf.append("\n 	discard.BRD_LEN, ");
			buf.append("\n 	discard.REG_DT, ");
			buf.append("\n 	discard.RSV_PRD_END_DD, ");
			buf.append("\n 	discard.PRE_RSV_PRD_CD, ");
			buf.append("\n 	discard.RSV_PRD_CD, ");
			buf.append("\n CON.MEDIA_ID, ");
			buf.append("\n CASE WHEN cart.cnt IS NULL THEN  0  ");
			buf.append("\n ELSE CART.CNT  ");
			buf.append("\n END AS cnt, ");
			buf.append("\n 	ROW_NUMBER() OVER(ORDER BY discard.SEQ DESC) AS rownum ");
		
		buf.append("\n from DAS.DISCARD_INFO_TBL discard");		
		buf.append("\n INNER JOIN (select master_id ,ct_id from DAS.CONTENTS_MAPP_TBL group by  master_id ,ct_id ) MAP ON MAP.MASTER_ID = DISCARD.MASTER_ID ");	
		buf.append("\n INNER JOIN DAS.CONTENTS_TBL CON ON CON.CT_ID= MAP.CT_ID");
		buf.append("\n  left outer join  (SELECT COUNT(MASTER_ID) AS CNT,MASTER_id FROM ( ");	
		buf.append("\n select cart_no,master_id    ");	
		buf.append("\n  FROM DAS.CART_CONT_TBL     ");	
		buf.append("\n WHERE DOWN_YN = 'Y' AND DOWN_DT <> ''");	
		buf.append("\n group by cart_no,master_id ) SU GROUP BY MASTER_ID   ");	
		buf.append("\n  )  cart on cart.MASTER_ID = discard.MASTER_ID    ");	
		
		buf.append("\n where  discard.DISUSE_YN = 'N'");
		
		
			
			if(!condition.getRsv_prd_start_dd().equals("")){	
								buf.append("\n  and discard.reg_dt >= '"+condition.getRsv_prd_start_dd()+"000000'  ");
			}
			
		if(!condition.getRsv_prd_end_dd().equals("")){	
			buf.append("\n   and discard.reg_dt <= '"+condition.getRsv_prd_end_dd()+"999999'  ");
			}
		
		
			
		if( !condition.getRsv_prd_cd().equals("")){
			buf.append("\n    and  discard.RSV_PRD_CD = '"+condition.getRsv_prd_cd()+"'  ");
		}
		if( !condition.getMedia_id().equals("")){
			buf.append("\n    and  con.media_id like '%"+condition.getMedia_id()+"%'  ");
		}
		
		if( condition.getMaster_id() !=0){
			buf.append("\n    and  discard.MASTER_ID ="+condition.getMaster_id()+" ");
		}
		return buf.toString();
	}
	
	
	
	
	
	
	/**
	 * 특정 마스터 id에대한 보존기간코드 값을 조회한다
	 * @author asura
	 *
	 */
	public static String selectRsvprdcdQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	RSV_PRD_CD ");		
		buf.append("\n from DAS.METADAT_MST_TBL ");
		buf.append("\n where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	
	
	
	/**
	 * 특정 마스터 id에대해서 이전종료일, 현재 종료일에대한 정보를 조회한다
	 * @author asura
	 *
	 */
	public static String selecDiscardInfo(int master_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select "); 
			buf.append("\n 	 RSV_PRD_END_DD ,");	
			buf.append("\n PRE_RSV_PRD_CD ");
			
		buf.append("\n from DAS.DISCARD_INFO_TBL  ");		
	
		buf.append("\n where MASTER_ID = " +  master_id);	
	
		return buf.toString();
	}
	
	
	
	
	
	
	
	
	/**
	 * 폐기 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public static final String selectDisCardInfoQuery(DiscardDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
		
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		
			buf.append("\n select count(*) as CCOUNT ,bigint(sum(sum_brd_leng)*29.97) as sum_brd_leng from ( ");
		
		buf.append("\n select DISTINCT  ");
		buf.append("\n 	 CASE WHEN CON.MEDIA_ID IS NULL THEN  ' '   ");
		buf.append("\n 	ELSE CON.MEDIA_ID    ");
		buf.append("\n 	END AS MEDIA_ID,  ");
		buf.append("\n 	 MST.CTGR_L_CD,  ");
		buf.append("\n 	CASE WHEN  MST.EPIS_NO <> 0 THEN  MST.TITLE||' ' || MST.EPIS_NO ||'회'  ");
		buf.append("\n 	ELSE  MST.TITLE  ");
		buf.append("\n 	END TITLE, ");
		buf.append("\n 	MST.BRD_DD,  ");
		buf.append("\n 	MST.RSV_PRD_END_DD,  ");
		buf.append("\n 	 MST.RSV_PRD_CD,  ");
		buf.append("\n 	 MST.BRD_LENG,  ");
		buf.append("\n 	 MST.MASTER_ID, ");
		buf.append("\n 	CASE WHEN cart.cnt IS NULL THEN  0  ");
		buf.append("\n 	ELSE CART.CNT   ");
		buf.append("\n 	END AS cnt,  ");
		buf.append("\n 	MST.SUB_TTL , ");
		buf.append("\n  CASE "); 
	 	buf.append("\n   when MST.BRD_LENG is not null or MST.BRD_LENG <> ''  "); 
		buf.append("\n 	  THEN bigint(SUBSTR(MST.BRD_LENG,1,2))*60*60+bigint(substr(MST.BRD_LENG,4,2))*60+bigint(substr(MST.BRD_LENG,7,2))  "); 
		buf.append("\n  ELSE 0 ");
		buf.append("\n END SUM_BRD_LENG ");
		buf.append("\n from das.metadat_mst_tbl MST    ");	
		buf.append("\n LEFT OUTER JOIN DAS.DISCARD_INFO_TBL DIS   ON DIS.MASTER_ID = MST.MASTER_ID    ");
		buf.append("\n LEFT OUTER JOIN DAS.CONTENTS_MAPP_TBL MAP   ON MAP.MASTER_ID = MST.MASTER_ID    ");
		buf.append("\n  LEFT OUTER JOIN DAS.CONTENTS_TBL CON   ON CON.CT_ID = MAP.CT_ID    ");
		//buf.append("\n LEFT OUTER JOIN DAS.user_info_tbl user   ON DIS.reg_id = user.sbs_user_id");
		buf.append("\n  left outer join  (SELECT COUNT(MASTER_ID) AS CNT,MASTER_id FROM (      ");	
		buf.append("\n select cart_no,master_id        ");
		buf.append("\n  FROM DAS.CART_CONT_TBL     ");
		buf.append("\n   WHERE  DOWN_DT <> ''      ");
		buf.append("\n group by cart_no,master_id ) SU GROUP BY MASTER_ID       ");	
		buf.append("\n  )  cart on cart.MASTER_ID = MST.MASTER_ID        ");
		buf.append("\n where 1=1    ");
	
		buf.append("\n   and con.ct_typ='003'  ");
		// 보존기간으로 검색  검색하는경우
		if(!conditionDO.getRsv_prd_start_dd().equals("")){
			buf.append("\n  and MST.RSV_PRD_END_DD >= '"+conditionDO.getRsv_prd_start_dd()+"'  ");
			}
		if(!conditionDO.getCtgr_l_cd().equals("")){
			buf.append("\n  and MST.ctgr_l_cd = '"+conditionDO.getCtgr_l_cd()+"'  ");
			}
	if(!conditionDO.getRsv_prd_end_dd().equals("")){			
	
	
		buf.append("\n  and MST.RSV_PRD_END_DD <= '"+conditionDO.getRsv_prd_end_dd()+"'  ");
		}

	if(!conditionDO.getStart_brd_dd().equals("")){
		buf.append("\n  and (( MST.brd_dd >= '"+conditionDO.getStart_brd_dd()+"'  ");
		}
	
if(!conditionDO.getEnd_brd_dd().equals("")){			


	buf.append("\n  and MST.brd_dd <= '"+conditionDO.getEnd_brd_dd()+"' ) ");
	}
	
if(!conditionDO.getStart_brd_dd().equals("")){
	buf.append("\n  or ( MST.fm_dt >= '"+conditionDO.getStart_brd_dd()+"'  ");
	}

if(!conditionDO.getEnd_brd_dd().equals("")){			


buf.append("\n  and MST.fm_dt <= '"+conditionDO.getEnd_brd_dd()+"' )) ");
}
	
	// 보존기간코드로 검색하는 경우
	if( !conditionDO.getRsv_prd_cd().equals("")){
	
		
		buf.append("\n  and  MST.RSV_PRD_CD = '"+conditionDO.getRsv_prd_cd()+"'  ");
		}
	// 미디어id로 검색하는 경우
	if( !conditionDO.getMedia_id().equals("")){
		
		
		buf.append("\n   and CON.MEDIA_ID like '%"+conditionDO.getMedia_id()+"%'  ");
		}
	
	if( conditionDO.getMaster_id()!=0){
		
		
		buf.append("\n   and mst.master_id ="+conditionDO.getMaster_id()+"  ");
		}
	
	

	

	// 프로그램 명으로 검색하는경우
		if(!conditionDO.getTitle().equals("")){
		buf.append("\n   and MST.TITLE like '%"+conditionDO.getTitle()+"%'  ");
		}
	/*	// 프로그램 명으로 검색하는경우
		if(!conditionDO.getReg_nm().equals("")){
		buf.append("\n   and user.user_nm like '%"+conditionDO.getReg_nm()+"%'  ");
		}*/

		buf.append("\n 	 AND (DIS.DISUSE_YN <> 'Y' OR DIS.DISUSE_YN IS NULL)");
		
		
/** 아카이브 경로*/
		
	 	if(!conditionDO.getArchive_path().equals("")){
	String[] path = conditionDO.getArchive_path().split(",");
	buf.append("\n 	and  ( ");
		for(int i=0;i<path.length;i++){
			if(path[i].equals("D"))
			{
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	(mst.arch_route LIKE 'D%') ");
			}
			else if(path[i].equals("O"))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  mst.arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");
				
			}else if(path[i].equals("P")){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( mst.arch_route ='P' )");
		
			}
		}
		buf.append("\n 	 ) ");
	 	}
	 	//2012.4.24 조회조건 추가
	 	if(!conditionDO.getCocd().equals("")){
	 		
		 	buf.append("\n  AND mst.cocd like  '" + conditionDO.getCocd()+"%'");

		 	
		 	}
	 	if(!conditionDO.getChennel().equals("")){
	 		
		 	buf.append("\n   AND mst.chennel_cd like  '" + conditionDO.getChennel()+"%'");

		 	
		 	}
		
		if( !conditionDO.getFrom_use().equals("")||!conditionDO.getTo_use().equals("")){
			
			if(conditionDO.getFrom_use().equals("0")||conditionDO.getTo_use().equals("0")){
				buf.append("\n  and (cnt is null or (cnt >='0' and cnt <= '"+conditionDO.getTo_use()+"'))  "); 
			}else {
			if(!conditionDO.getFrom_use().equals("")){
			buf.append("\n  and  cnt >= '"+conditionDO.getFrom_use()+"'  ");
			}
			if(!conditionDO.getTo_use().equals("")){
				 if(conditionDO.getTo_use().equals("0")) {
					
				}
				buf.append("\n  and  cnt <= '"+conditionDO.getTo_use()+"'  ");
					}
			}
		
	
		}
		
		buf.append("\n and mst.ARCH_REG_DD<>'' ");
		buf.append("\n and (mst.DEL_DD='' or mst.del_dd is null ) ");
		buf.append("\n    AND (mst.master_ID NOT IN (   ");
		buf.append("\n   SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM   ");
		buf.append("\n   INNER JOIN ( SELECT MASTER_ID FROM METADAT_MST_TBL    ");
		buf.append("\n   WHERE DEL_DD IS NULL OR DEL_DD > TS_FMT(CURRENT DATE,'yyyymmdd') or REPLACE(del_dd,' ','')=''   ");
		buf.append("\n    ) PM ON PM.MASTER_ID =RM.PARENT_MASTER_ID    ");
		buf.append("\n   GROUP BY CHILD_MASTER_ID  ");
		buf.append("\n      ) )  ");

		
		buf.append("\n  ) as t ");
		
		buf.append("\n with ur");
		
		return buf.toString();		

	}
	
	
	
	
	
	
	
	/**
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 */
	public static final String selectSumHyenDiscard(DiscardDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
		
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		
			buf.append("\n select count(*) as CCOUNT ,sum(sum_brd_leng)*29.97 as sum_brd_leng from ( ");
		
			buf.append("\n select ");               
			buf.append("\n 	dis.master_id, ");
				buf.append("\n 	dis.SEQ, ");
				buf.append("\n 	dis.TITLE, ");
				buf.append("\n 	dis.BRD_DD, ");
				buf.append("\n 	dis.BRD_LEN, ");
				buf.append("\n 	dis.DISUSE_CONT, ");
				buf.append("\n 	dis.REG_DT, ");
				buf.append("\n 	dis.RSV_PRD_END_DD, ");

				buf.append("\n 	CASE WHEN 	dis.DEL_DD IS NULL or dis.DEL_DD ='' THEN  ''  ");
				buf.append("\n ELSE 	dis.DEL_DD   ");
				buf.append("\n 	  END AS DEL_DD, ");
				buf.append("\n 	CASE WHEN 	dis.DISUSE_STA IS NULL THEN  ' '  ");
				buf.append("\n ELSE 	dis.DISUSE_STA   ");
				buf.append("\n 	  END AS DISUSE_STA, ");
		buf.append("\n  CASE "); 
	 	buf.append("\n   when MST.BRD_LENG is not null or MST.BRD_LENG <> ''  "); 
		buf.append("\n 	  THEN bigint(SUBSTR(MST.BRD_LENG,1,2))+bigint(substr(MST.BRD_LENG,4,2))*60+bigint(substr(MST.BRD_LENG,7,2))  "); 
		buf.append("\n  ELSE 0 ");
		buf.append("\n END SUM_BRD_LENG ");
		buf.append("\n from DAS.DISCARD_INFO_TBL dis ");		
		buf.append("\n inner join metadat_mst_tbl mst on mst.MASTER_ID=dis.MASTER_ID ");	
		buf.append("\n left outer join user_info_tbl user on user.sbs_user_id = dis.reg_id ");
		buf.append("\n left outer join (select master_id , del_yn from contents_mapp_tbl group by master_id , del_yn) map on map.master_id = dis.master_id ");
		buf.append("\n where  dis.DISUSE_YN = 'Y'");
	
		
		
	
	if(!conditionDO.getRsv_prd_start_dd().equals("") || !conditionDO.getRsv_prd_end_dd().equals("") || !conditionDO.getRsv_prd_cd().equals("")){
			
			
			if(conditionDO.getRsv_prd_start_dd().equals("")){			
		}else {buf.append("\n AND  dis.reg_dt >= '"+conditionDO.getRsv_prd_start_dd()+"000000'  ");
		}
		
		if(conditionDO.getRsv_prd_end_dd().equals("")){			
		}else {
			
			buf.append("\n AND dis.reg_dt <= '"+conditionDO.getRsv_prd_end_dd()+"999999'  ");
			}
		
		if(conditionDO.getRsv_prd_start_dd()==conditionDO.getRsv_prd_end_dd()){
			buf.append("\n AND dis.reg_dt =  '"+conditionDO.getReg_dt()+"'  ");
			}
	
		}

	
	if(!conditionDO.getStatus().equals("")){
		if(conditionDO.getStatus().equals("000")){
		buf.append("\n AND MAP.DEL_YN  =  'N'  ");
		}else if(conditionDO.getStatus().equals("004")){
		buf.append("\n AND MAP.DEL_YN =  'Y'  ");
		}
	}
	if(!conditionDO.getReg_nm().equals("")){
		buf.append("\n AND user_nm ='%"+conditionDO.getReg_nm()+"%'  ");
		}
	
	
	/** 아카이브 경로*/
	
 	if(!conditionDO.getArchive_path().equals("")){
String[] path = conditionDO.getArchive_path().split(",");
buf.append("\n 	and  ( ");
	for(int i=0;i<path.length;i++){
		if(path[i].equals("D"))
		{
			if(i!=0){
				buf.append("\n 	 or  ");
			}
			buf.append("\n 	(mst.arch_route LIKE 'D%') ");
		}
		else if(path[i].equals("O"))
		{   // mcuidYn=Y 온에어(주조)
			if(i!=0){
				buf.append("\n 	 or  ");
			}
			buf.append("\n 	 ( ");
			buf.append("\n  mst.arch_route LIKE 'O%' ");
			buf.append("\n 	 ) ");
			
		}else if(path[i].equals("P")){
			//PDS
			if(i!=0){
				buf.append("\n 	 or  ");
			}
			buf.append("\n 	( mst.arch_route ='P' )");
	
		}
	}
	buf.append("\n 	 ) ");
 	}
 	//2012.4.24 조회조건 추가
 	if(!conditionDO.getCocd().equals("")){
 		
	 	buf.append("\n  AND mst.cocd like  '" + conditionDO.getCocd()+"%'");

	 	
	 	}
 	if(!conditionDO.getChennel().equals("")){
 		
	 	buf.append("\n   AND mst.chennel_cd like  '" + conditionDO.getChennel()+"%'");

	 	
	 	}
	
	buf.append("\n ) as t    ");
		return buf.toString();
	}
	

	/**
	 * 연장 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public static final String selectSumHyenuse(DiscardDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
		
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		
			buf.append("\n select count(*) as CCOUNT ,sum(sum_brd_leng)*29.97 as sum_brd_leng from ( ");
		
			buf.append("\n select ");     
			buf.append("\n 	dis.SEQ, ");
			buf.append("\n 	dis.TITLE, ");
			buf.append("\n 	dis.BRD_LEN, ");
			buf.append("\n 	dis.REG_DT, ");
			buf.append("\n 	dis.RSV_PRD_END_DD, ");
			buf.append("\n 	dis.PRE_RSV_PRD_CD, ");
			buf.append("\n 	dis.RSV_PRD_CD, ");
		
		buf.append("\n  CASE "); 
	 	buf.append("\n  when dis.BRD_LEN is not null or dis.BRD_LEN <> ''    "); 
		buf.append("\n 	  THEN bigint(SUBSTR(dis.BRD_LEN,1,2))+bigint(substr(dis.BRD_LEN,4,2))*60+bigint(substr(dis.BRD_LEN,7,2))    "); 
		buf.append("\n  ELSE 0 ");
		buf.append("\n END SUM_BRD_LENG ");
		buf.append("\n from DAS.DISCARD_INFO_TBL DIS ");
		buf.append("\n  INNER JOIN (select master_id ,ct_id from DAS.CONTENTS_MAPP_TBL group by  master_id ,ct_id ) MAP ON MAP.MASTER_ID = DIS.MASTER_ID ");
		buf.append("\n  inner join das.contents_tbl con on con.ct_id=map.CT_ID  ");
		buf.append("\n where  dis.DISUSE_YN = 'N'");
		
		
			
			if(!conditionDO.getRsv_prd_start_dd().equals("")){	
								buf.append("\n  and dis.reg_dt >= '"+conditionDO.getRsv_prd_start_dd()+"000000'  ");
			}
			
		if(!conditionDO.getRsv_prd_end_dd().equals("")){	
			buf.append("\n   and dis.reg_dt <= '"+conditionDO.getRsv_prd_end_dd()+"999999'  ");
			}
		
		
			
		if( !conditionDO.getRsv_prd_cd().equals("")){
			buf.append("\n    and  dis.RSV_PRD_CD = '"+conditionDO.getRsv_prd_cd()+"'  ");
		}
		if( !conditionDO.getMedia_id().equals("")){
			buf.append("\n    and  con.media_id like '%"+conditionDO.getMedia_id()+"%'  ");
		}
		if( conditionDO.getMaster_id() != 0){
			buf.append("\n    and  discard.MASTER_ID = "+conditionDO.getMaster_id()+"  ");
		}
		
		buf.append("\n ) as t    ");
		return buf.toString();
	}
	


	
	
	
	
	
	/**
	 * 폐기 현황 정보의 마스터id를 가져온다
	 * @param master_id 마스터id
	 * @return
	 */
	
	public static final String selectUseDiscard(int master_id)
	{
		StringBuffer buf = new StringBuffer();
		
	
			buf.append("\n select master_id from das.discard_info_tbl where master_id ="+master_id+"");
		
		
		return buf.toString();
	}
	

}

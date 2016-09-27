package com.app.das.business.dao.statement;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.StringUtils;

/**
 * 이용관리에 대한 SQL 쿼리에 대한 정의가 되어 있다.
 * @author ysk523
 *
 */
public class CommunityStatement 
{/**
	 * 게시판에 대한 정보를조회한다
	 * @author
	 *
	 */
	public static String selectBoardListQuery(BoardConditionDO conditionDO, String searchFlag, String readCountOrderYn) throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	brd.BOARD_ID, "); 
			buf.append("\n 	brd.BOARD_TYPE_CD, "); 
			buf.append("\n 	brd.ANSWER_YN,  ");
			buf.append("\n 	brd.SUBJ, "); 
			buf.append("\n 	brd.CONT, "); 
			buf.append("\n 	substr(brd.REG_DT, 1, 8) AS REG_DT, "); 
			buf.append("\n 	brd.MOD_DT, "); 
			buf.append("\n 	brd.REGRID, "); 
			buf.append("\n  	CASE ");
			buf.append("\n 		WHEN out.OUT_USER_NM is not null or out.OUT_USER_NM <> ''  THEN out.OUT_USER_NM ");
			buf.append("\n  	    ELSE erp.USER_NM ");
			buf.append("\n  	END AS USER_NM, "); 	
			buf.append("\n 	brd.MODRID, "); 
			buf.append("\n 	brd.RD_COUNT, "); 
			buf.append("\n 	brd.MAIN_ID, ");
			buf.append("\n 	brd.POPUP_START_DD, "); 
			buf.append("\n 	brd.POPUP_END_DD, ");
			buf.append("\n 	(select count(1) from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) AS ATTACH_COUNT, ");
			if(DASBusinessConstants.YesNo.YES.equals(readCountOrderYn))
			{
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.RD_COUNT DESC) AS rownum ");
			}
			else
			{
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.MAIN_ID DESC, brd.BOARD_ID) AS rownum ");
			}
		}
		buf.append("\n from DAS.BOARD_TBL brd  ");
		buf.append("\n 		LEFT OUTER JOIN DAS.OUTSIDER_INFO_TBL out ON out.OUT_USER_ID = brd.REGRID ");
		buf.append("\n 		LEFT OUTER JOIN DAS.ERP_COM_USER_TBL erp ON erp.USER_ID = brd.REGRID ");
		buf.append("\n where brd.BOARD_TYPE_CD = '"+conditionDO.getBoardTypeCd()+"' ");
		if(!StringUtils.isEmpty(conditionDO.getSearchKind()) && !StringUtils.isEmpty(conditionDO.getSearchValue()))
		{
			if(CodeConstants.BoardSearchKind.USER.equals(conditionDO.getSearchKind()))
			{
				buf.append("\n 	and (out.OUT_USER_NM like '%"+conditionDO.getSearchValue()+"%' or erp.USER_NM like '%"+conditionDO.getSearchValue()+"%') ");
			}
			else if(CodeConstants.BoardSearchKind.SUBJECT.equals(conditionDO.getSearchKind()))
			{
				buf.append("\n 	and brd.SUBJ like '%"+conditionDO.getSearchValue()+"%' ");
			}
			else if(CodeConstants.BoardSearchKind.REG_DATE.equals(conditionDO.getSearchKind()))
			{
				buf.append("\n 	and substr(brd.REG_DT, 1, 8) = '"+conditionDO.getSearchValue()+"' ");
			}
			
		}		
		buf.append("\n order by brd.REG_DT desc ");
		return buf.toString();
	}
	
	/**
	 * 특정 게시판정보에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selectBoardInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	brd.BOARD_ID, "); 
		buf.append("\n 	brd.BOARD_TYPE_CD, "); 
		buf.append("\n 	brd.POPUP_YN, "); 
		buf.append("\n 	brd.SUBJ, "); 
		buf.append("\n 	brd.CONT, "); 
		buf.append("\n 	brd.REG_DT, "); 
		//buf.append("\n 	brd.MOD_DT, "); 
		buf.append("\n 	brd.REGRID, "); 
		buf.append("\n  (SELECT USER_NM FROM USER_INFO_TBL WHERE SBS_USER_ID=brd.REGRID )AS USER_NM, ");
		//buf.append("\n 	brd.MODRID, "); 
		buf.append("\n 	brd.RD_COUNT, "); 
		buf.append("\n 	brd.MAIN_ID, ");
		buf.append("\n 	 value( brd.POPUP_START_DD, '')as POPUP_START_DD , ");
		buf.append("\n 	 value(  brd.POPUP_END_DD, '') as POPUP_END_DD, ");
		//buf.append("\n 	,(select count(1) from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) AS ATTACH_COUNT ");
		buf.append("\n 	case when (select FL_NM from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) is null then 'N' ");
		buf.append("\n 	   ELSE 'Y' ");
		buf.append("\n 	   END AS ATTACH_YN,  ");
		buf.append("\n from DAS.BOARD_TBL brd ");
		buf.append("\n where brd.MAIN_ID = ? ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	
	/**
	 * 특정 게시판의 첨부파일의 정보에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selectFileInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MOTHR_DATA_ID, "); 
		buf.append("\n 	SEQ, "); 
		buf.append("\n 	ATTC_FILE_TYPE_CD, "); 
		buf.append("\n 	ATTC_CLF_CD, "); 
		buf.append("\n 	FL_NM, "); 
		buf.append("\n 	FL_SZ, "); 
		buf.append("\n 	FL_PATH, "); 
		buf.append("\n 	ORG_FILE_NM, "); 
		buf.append("\n 	REG_DT, "); 
		buf.append("\n 	REGRID, "); 
		buf.append("\n 	MOD_DT, "); 
		buf.append("\n 	MODRID ");
		buf.append("\n from DAS.ATTCH_TBL ");
		buf.append("\n where MOTHR_DATA_ID = ?  ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	
	
	
	
	
	/**
	 * 덧글 대해 조회한다
	 * @author 
	 *
	 */
	
	public static String selectBoardReplyListQuery(String mainId) throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	brd.BOARD_ID, "); 
		buf.append("\n 	brd.BOARD_TYPE_CD, "); 
		buf.append("\n 	brd.ANSWER_YN,  ");
		buf.append("\n 	brd.SUBJ, "); 
		buf.append("\n 	brd.CONT, "); 
		buf.append("\n 	brd.REG_DT, "); 
		buf.append("\n 	brd.MOD_DT, "); 
		buf.append("\n 	brd.REGRID, "); 
		buf.append("\n  	CASE ");
		buf.append("\n 		WHEN out.OUT_USER_NM is not null or out.OUT_USER_NM <> ''  THEN out.OUT_USER_NM ");
		buf.append("\n  	    ELSE erp.USER_NM ");
		buf.append("\n  	END AS USER_NM, "); 	
		buf.append("\n 	brd.MODRID, "); 
		buf.append("\n 	brd.RD_COUNT, "); 
		buf.append("\n 	brd.MAIN_ID, ");
		buf.append("\n 	(select count(1) from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) AS ATTACH_COUNT, ");
		buf.append("\n 	ROW_NUMBER() OVER(order by brd.BOARD_ID) AS rownum ");
		buf.append("\n from DAS.BOARD_TBL brd  ");
		buf.append("\n 		LEFT OUTER JOIN DAS.OUTSIDER_INFO_TBL out ON out.OUT_USER_ID = brd.REGRID ");
		buf.append("\n 		LEFT OUTER JOIN DAS.ERP_COM_USER_TBL erp ON erp.USER_ID = brd.REGRID ");
		buf.append("\n where brd.MAIN_ID = "+mainId+" ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	} 

	
	/**
	 * 프리뷰에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selectPreviewQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PRE.MASTER_ID,  "); 
		buf.append("\n 	PRE.PREVIEW_ID,  "); 
		buf.append("\n 	PRE.PREVIEW_SUBJ, "); 
		buf.append("\n 	PRE.PREVIEW_CONT, "); 
		//buf.append("\n  ATTACH.FL_PATH ||'\\' ||ATTACH.FL_NM AS PATH, "); 
		buf.append("\n 	PRE.REG_DT  "); 
		buf.append("\n 	FROM  PREVIEW_TBL  pre "); 
	//	buf.append("\n 	LEFT OUTER JOIN PREVIEW_ATTACH_TBL ATTACH ON PRE.MASTER_ID=ATTACH.MASTER_ID "); 
		buf.append("\n 	WHERE "); 
		buf.append("\n 	PRE.MASTER_ID = ? "); 
                                     
		return buf.toString();
	}
	
	
	/**
	 * 특정 프리뷰에 연관된 첨부파일에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selectPreviewAttachQuery(int master_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MASTER_ID, "); 
		buf.append("\n 	PREVIEW_ATTATCH_ID, "); 
		buf.append("\n 	FL_NM, "); 
		buf.append("\n 	FL_SZ, "); 
		buf.append("\n 	FL_PATH, "); 
		buf.append("\n 	ORG_FILE_NM, "); 
		buf.append("\n 	REG_DT, "); 
		buf.append("\n 	REGRID "); 
		buf.append("\n 	FROM  PREVIEW_ATTACH_TBL   "); 	
		buf.append("\n 	WHERE "); 
		buf.append("\n 	master_id = "+master_id+" "); 
                                     
		return buf.toString();
	}
	
	
	
	
	/**
	 * 게시판정보에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selectBoardInfoListQuery(BoardDO conditionDO, String searchFlag, String readCountOrderYn) throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n 	brd.BOARD_ID, "); 
			buf.append("\n 	brd.BOARD_TYPE_CD, "); 
			buf.append("\n 	brd.POPUP_YN,  ");
			buf.append("\n 	brd.SUBJ, "); 
			buf.append("\n 	brd.CONT, "); 
			buf.append("\n 	substr(brd.REG_DT, 1, 8) AS REG_DT, "); 
			buf.append("\n 	brd.REGRID, "); 
			buf.append("\n  (SELECT USER_NM FROM USER_INFO_TBL WHERE SBS_USER_ID=brd.REGRID )AS USER_NM, ");
			buf.append("\n 	brd.RD_COUNT, "); 
			buf.append("\n 	brd.MAIN_ID, ");
			buf.append("\n value( brd.POPUP_START_DD, '') as POPUP_START_DD , "); 
			buf.append("\n 	 value(  brd.POPUP_END_DD, '') as POPUP_END_DD, ");
			buf.append("\n 	case when (select FL_NM from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID  fetch first 1 rows only ) is null then 'N'  ");
			buf.append("\n 	ELSE 'Y' END AS ATTACH_YN, ");
			if(DASBusinessConstants.YesNo.YES.equals(readCountOrderYn))
			{
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.RD_COUNT DESC) AS rownum ");
			}
			else
			{
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.MAIN_ID DESC, brd.BOARD_ID) AS rownum ");
			}
		}
		buf.append("\n from DAS.BOARD_TBL brd  ");
		buf.append("\n 		LEFT OUTER JOIN DAS.ERP_COM_USER_TBL USER  ON USER.USER_ID = brd.REGRID  ");
		
		buf.append("\n where brd.BOARD_TYPE_CD = '"+conditionDO.getBoardTypeCd()+"' ");
		
		
		/*if(!conditionDO.getCont().equals("") || conditionDO.getSubj().equals(""))
		{
			if(CodeConstants.BoardSearchKind.USER.equals(conditionDO.getSearchKind()))
			{
				buf.append("\n 	and (out.OUT_USER_NM like '%"+conditionDO.getSearchValue()+"%' or erp.USER_NM like '%"+conditionDO.getSearchValue()+"%') ");
			}
			else if(CodeConstants.BoardSearchKind.SUBJECT.equals(conditionDO.getSearchKind()))
			{
				buf.append("\n 	and brd.SUBJ like '%"+conditionDO.getSearchValue()+"%' ");
			}
			
			
		}		*/
		if(!conditionDO.getCont().equals("")){
			buf.append("\n 	and BRD.SUBJ LIKE '%"+conditionDO.getCont() +"%' ");
		}
		

		return buf.toString();
	}
	/**
	 * 게시판 목록 조회를 한다.(메인화면용)
	 *
	 * @return List 조회된 게시판 정보를 포함하고 있는 DataObject
	 */
	public static String selectMainBoardListQuery() throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
	
			buf.append("\n 	brd.BOARD_ID, "); 
			buf.append("\n 	brd.BOARD_TYPE_CD, "); 
			buf.append("\n 	brd.ANSWER_YN,  ");
			buf.append("\n 	brd.SUBJ, "); 
			buf.append("\n 	brd.CONT, "); 
			buf.append("\n 	substr(brd.REG_DT, 1, 8) AS REG_DT, "); 
			buf.append("\n 	brd.MOD_DT, "); 
			buf.append("\n 	brd.REGRID, "); 
	
			buf.append("\n  erp.USER_NM, "); 	
			buf.append("\n 	brd.MODRID, "); 
			buf.append("\n 	brd.RD_COUNT, "); 
			buf.append("\n 	brd.MAIN_ID, ");
			buf.append("\n 	(select count(1) from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) AS ATTACH_COUNT, ");
		
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.MAIN_ID DESC, brd.BOARD_ID) AS rownum ");
		
		
		buf.append("\n from DAS.BOARD_TBL brd  ");
	//	buf.append("\n 		LEFT OUTER JOIN DAS.OUTSIDER_INFO_TBL out ON out.OUT_USER_ID = brd.REGRID ");
		buf.append("\n 		LEFT OUTER JOIN DAS.user_info_tbl erp ON erp.SBS_USER_ID = brd.REGRID ");
		buf.append("\n where brd.BOARD_TYPE_CD = '001' ");
		//String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
		//buf.append("\n where brd.POPUP_END_DD > "+toDateTime);
		buf.append("\n FETCH FIRST 5 ROWS ONLY   ");
			

		return buf.toString();
	}
	
	
	
	/**
	 * 게시판 목록 조회를 한다.(팝업용)오늘 날짜를 기준으로 큰것을 조회한다
	 * @param today 오늘날짜
	 * @param commonDO 공통정보
	 * @return List 조회된 게시판 정보를 포함하고 있는 DataObject
	 */
	public static String selectMainBoardListQuery2(String today) throws DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
	
			buf.append("\n 	brd.BOARD_ID, "); 
			buf.append("\n 	brd.BOARD_TYPE_CD, "); 
			buf.append("\n 	brd.ANSWER_YN,  ");
			buf.append("\n 	brd.SUBJ, "); 
			buf.append("\n 	brd.CONT, "); 
			buf.append("\n 	substr(brd.REG_DT, 1, 8) AS REG_DT, "); 
			buf.append("\n 	brd.MOD_DT, "); 
			buf.append("\n 	brd.REGRID, "); 
		
			buf.append("\n  user.USER_NM, "); 	
			buf.append("\n 	brd.MODRID, "); 
			buf.append("\n 	brd.RD_COUNT, "); 
			buf.append("\n 	brd.MAIN_ID, ");
			buf.append("\n 	(select count(1) from DAS.ATTCH_TBL where MOTHR_DATA_ID = brd.BOARD_ID) AS ATTACH_COUNT, ");
		
				buf.append("\n 	ROW_NUMBER() OVER(order by brd.MAIN_ID DESC, brd.BOARD_ID) AS rownum ");
		
		
		buf.append("\n from DAS.BOARD_TBL brd  ");
	
		buf.append("\n 		LEFT OUTER JOIN DAS.USER_INFO_TBL user ON user.SBS_USER_ID = brd.REGRID ");
		buf.append("\n where brd.BOARD_TYPE_CD = '001' ");
		//String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
		buf.append("\n and brd.POPUP_END_DD >= '"+today+"' ");
		
			

		return buf.toString();
	}
}

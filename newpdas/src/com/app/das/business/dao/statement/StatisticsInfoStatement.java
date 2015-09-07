package com.app.das.business.dao.statement;

import java.rmi.RemoteException;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.business.transfer.StatisticsInfo;
import com.app.das.util.CodeCommon;
import com.app.das.util.StringUtils;

/**
 * 통계에 대한 SQL 쿼리가 정의되어 있다.
 * @author ysk523
 *
 */
public class StatisticsInfoStatement 
{
	
	/**
	 * 수집의 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectCollectionJarnreListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY, ");
			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_MVSTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 4) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		sum(BY_DY_TM) AS SUM_TM, ");
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_QTY, ");
			buf.append("\n 			 		value(( ");
			buf.append("\n 			 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_TM  FROM DAS.GNR_MVSTAT_TBL "); 
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(DD, 1, 4) >= '"+fromDate.substring(0, 4)+"' "); 
			buf.append("\n 			 		and substr(DD, 1, 4) <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY, ");
			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_MVSTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 6) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		sum(BY_DY_TM) AS SUM_TM, ");
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_QTY, ");
			buf.append("\n 			 		value(( ");
			buf.append("\n 			 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_TM  FROM DAS.GNR_MVSTAT_TBL "); 
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' "); 
			buf.append("\n 			 		and substr(DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY, ");
			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 		) AS TOTAL_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_MVSTAT_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, '00000000' AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		sum(BY_DY_TM) AS SUM_TM, ");
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select sub.BY_PURE_QTY  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_QTY, ");
			buf.append("\n 			 		value(( ");
			buf.append("\n 			 			select sub.BY_PURE_TIME  ");
			buf.append("\n 			 			from DAS.GRN_RSVSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
			buf.append("\n 			 		), 0) AS TOTAL_TM  FROM DAS.GNR_MVSTAT_TBL "); 
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and DD >= '"+fromDate+"' "); 
			buf.append("\n 			 		and DD <= '"+toDate+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DD) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 수집의 수집처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectCollectionGathRegularListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	org.DEP_NM, ");
			buf.append("\n 	org.CENTER_NM, ");
			buf.append("\n 	org.COM_NM, ");
			buf.append("\n 	qty.GATH_DEPT_CD, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY, ");
			buf.append("\n 	qty.SUM_TM ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select ");
			buf.append("\n 		mv.GATH_DEPT_CD, ");
			buf.append("\n 		com.ORG_NM AS DEP_NM, ");
			buf.append("\n 		com.HSEG_NM AS CENTER_NM, ");
			buf.append("\n 		( ");
			buf.append("\n 		select  ");
			buf.append("\n 			sub.ORG_NM  ");
			buf.append("\n 		from DAS.ERP_COM_BAS_TBL sub  ");
			buf.append("\n 		where sub.ORG_TYP = 'C'  ");
			buf.append("\n 			and sub.ORG_CD = (select HSEG_CD from DAS.ERP_COM_BAS_TBL where ORG_TYP = 'S' and ORG_CD = com.HSEG_CD) ");
			buf.append("\n 		) AS COM_NM ");
			buf.append("\n 	from (select GATH_DEPT_CD from DAS.GATH_MVSTAT_TBL group by GATH_DEPT_CD) mv, DAS.ERP_COM_BAS_TBL com ");
			buf.append("\n 	where mv.GATH_DEPT_CD = com.ORG_CD ");
			buf.append("\n 		and ORG_TYP = 'D' ");
			buf.append("\n 	) org, ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		GATH_DEPT_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");
			buf.append("\n 	group by GATH_DEPT_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where org.GATH_DEPT_CD = qty.GATH_DEPT_CD ");
			buf.append("\n 	and qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n order by org.DEP_NM, org.CENTER_NM, org.COM_NM ");
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	org.DEP_NM, ");
			buf.append("\n 	org.CENTER_NM, ");
			buf.append("\n 	org.COM_NM, ");
			buf.append("\n 	qty.GATH_DEPT_CD, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY, ");
			buf.append("\n 	qty.SUM_TM ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select ");
			buf.append("\n 		mv.GATH_DEPT_CD, ");
			buf.append("\n 		com.ORG_NM AS DEP_NM, ");
			buf.append("\n 		com.HSEG_NM AS CENTER_NM, ");
			buf.append("\n 		( ");
			buf.append("\n 		select  ");
			buf.append("\n 			sub.ORG_NM  ");
			buf.append("\n 		from DAS.ERP_COM_BAS_TBL sub  ");
			buf.append("\n 		where sub.ORG_TYP = 'C'  ");
			buf.append("\n 			and sub.ORG_CD = (select HSEG_CD from DAS.ERP_COM_BAS_TBL where ORG_TYP = 'S' and ORG_CD = com.HSEG_CD) ");
			buf.append("\n 		) AS COM_NM ");
			buf.append("\n 	from (select GATH_DEPT_CD from DAS.GATH_MVSTAT_TBL group by GATH_DEPT_CD) mv, DAS.ERP_COM_BAS_TBL com ");
			buf.append("\n 	where mv.GATH_DEPT_CD = com.ORG_CD ");
			buf.append("\n 		and ORG_TYP = 'D' ");
			buf.append("\n 	) org, ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		GATH_DEPT_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");
			buf.append("\n 	group by GATH_DEPT_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where org.GATH_DEPT_CD = qty.GATH_DEPT_CD ");
			buf.append("\n 	and qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n order by org.DEP_NM, org.CENTER_NM, org.COM_NM ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 	org.DEP_NM, ");
			buf.append("\n 	org.CENTER_NM, ");
			buf.append("\n 	org.COM_NM, ");
			buf.append("\n 	qty.GATH_DEPT_CD, ");
			buf.append("\n 	'00000000' AS DAY, ");
			buf.append("\n 	qty.SUM_QTY, ");
			buf.append("\n 	qty.SUM_TM ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select ");
			buf.append("\n 		mv.GATH_DEPT_CD, ");
			buf.append("\n 		com.ORG_NM AS DEP_NM, ");
			buf.append("\n 		com.HSEG_NM AS CENTER_NM, ");
			buf.append("\n 		( ");
			buf.append("\n 		select  ");
			buf.append("\n 			sub.ORG_NM  ");
			buf.append("\n 		from DAS.ERP_COM_BAS_TBL sub  ");
			buf.append("\n 		where sub.ORG_TYP = 'C'  ");
			buf.append("\n 			and sub.ORG_CD = (select HSEG_CD from DAS.ERP_COM_BAS_TBL where ORG_TYP = 'S' and ORG_CD = com.HSEG_CD) ");
			buf.append("\n 		) AS COM_NM ");
			buf.append("\n 	from (select GATH_DEPT_CD from DAS.GATH_MVSTAT_TBL group by GATH_DEPT_CD) mv, DAS.ERP_COM_BAS_TBL com ");
			buf.append("\n 	where mv.GATH_DEPT_CD = com.ORG_CD ");
			buf.append("\n 		and ORG_TYP = 'D' ");
			buf.append("\n 	) org, ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		GATH_DEPT_CD, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 	  and DD <= '"+toDate+"' ");
			buf.append("\n 	group by GATH_DEPT_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n where org.GATH_DEPT_CD = qty.GATH_DEPT_CD ");
			buf.append("\n order by org.DEP_NM, org.CENTER_NM, org.COM_NM ");		
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 수집의 수집처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectCollectionGathOutListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}


		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	qty.GATH_CO_CD, ");
			String gathCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTOR, "qty.GATH_CO_CD");
			buf.append(gathCoCodeName + " AS GATH_CO_NM, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY,    ");
			buf.append("\n 	qty.SUM_TM ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		GATH_CO_CD,  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");
			buf.append("\n 	where GATH_CO_CD is not null and GATH_CO_CD <> ''  ");
			buf.append("\n 	group by GATH_CO_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n order by qty.GATH_CO_CD ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	qty.GATH_CO_CD, ");
			String gathCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTOR, "qty.GATH_CO_CD");
			buf.append(gathCoCodeName + " AS GATH_CO_NM, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY,    ");
			buf.append("\n 	qty.SUM_TM ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		GATH_CO_CD,  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");
			buf.append("\n 	where GATH_CO_CD is not null and GATH_CO_CD <> ''  ");
			buf.append("\n 	group by GATH_CO_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n order by qty.GATH_CO_CD ");

		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");                                   
			buf.append("\n 	qty.GATH_CO_CD, ");                   
			String gathCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTOR, "qty.GATH_CO_CD");
			buf.append(gathCoCodeName + " AS GATH_CO_NM, ");
			buf.append("\n 	'00000000' AS DAY,  ");               
			buf.append("\n 	qty.SUM_QTY,  ");                     
			buf.append("\n 	qty.SUM_TM   ");                      
			buf.append("\n from  ");                                     
			buf.append("\n 	(   ");                               
			buf.append("\n 	select  ");                           
			buf.append("\n 		GATH_CO_CD, ");               
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		SUM(BY_DY_TM) AS SUM_TM  ");  
			buf.append("\n 	from DAS.GATH_MVSTAT_TBL  ");         
			buf.append("\n 	where DD >= '"+fromDate+"' ");                    
			buf.append("\n 		and DD <= '"+toDate+"' ");              
			buf.append("\n 	 	and (GATH_CO_CD is not null and GATH_CO_CD <> '')  ");
			buf.append("\n 	group by GATH_CO_CD  ");              
			buf.append("\n 	) qty    ");                          
			buf.append("\n order by qty.GATH_CO_CD  ");  
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 수집의 수집구분 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectCollectionGathKindListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}


		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		cd.SCL_CD AS GATH_CLF_CD, "); 
			String gathKindCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE, "cd.SCL_CD");
			buf.append(gathKindCodeName + " AS GATH_CLF_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		SCL_CD ");
			buf.append("\n 	from DAS.CODE_TBL ");
			buf.append("\n 	where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE+"' ");
			buf.append("\n ) cd  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.GATH_CLF_CD,  ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 		(select  ");
			buf.append("\n 			GATH_CLF_CD,  ");
			buf.append("\n 			substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 			sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 			sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 		from DAS.GACLF_MVSTAT_TBL ");
			buf.append("\n 		group by GATH_CLF_CD, substr(DD, 1, 4) ");
			buf.append("\n 		) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON qr.GATH_CLF_CD = cd.SCL_CD ");
			buf.append("\n order by cd.SCL_CD, qr.day ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		cd.SCL_CD AS GATH_CLF_CD, "); 
			String gathKindCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE, "cd.SCL_CD");
			buf.append(gathKindCodeName + " AS GATH_CLF_NM, ");
			buf.append("\n 		value(qr.DAY, '000000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		SCL_CD ");
			buf.append("\n 	from DAS.CODE_TBL ");
			buf.append("\n 	where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE+"' ");
			buf.append("\n ) cd  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.GATH_CLF_CD,  ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 		(select  ");
			buf.append("\n 			GATH_CLF_CD,  ");
			buf.append("\n 			substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 			sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 			sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 		from DAS.GACLF_MVSTAT_TBL ");
			buf.append("\n 		group by GATH_CLF_CD, substr(DD, 1, 6) ");
			buf.append("\n 		) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON qr.GATH_CLF_CD = cd.SCL_CD ");
			buf.append("\n order by cd.SCL_CD, qr.day ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		cd.SCL_CD AS GATH_CLF_CD, "); 
			String gathKindCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE, "cd.SCL_CD");
			buf.append(gathKindCodeName + " AS GATH_CLF_NM, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		SCL_CD ");
			buf.append("\n 	from DAS.CODE_TBL ");
			buf.append("\n 	where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_COLLECTION_TYPE+"' ");
			buf.append("\n ) cd  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.GATH_CLF_CD,  ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 		(select  ");
			buf.append("\n 			GATH_CLF_CD,  ");
			buf.append("\n 			sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 			sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 		from DAS.GACLF_MVSTAT_TBL ");
			buf.append("\n 		where DD >= '"+fromDate+"' ");
			buf.append("\n 			and DD <= '"+toDate+"' ");
			buf.append("\n 		group by GATH_CLF_CD ");
			buf.append("\n 		) qty ");
			buf.append("\n ) qr ON qr.GATH_CLF_CD = cd.SCL_CD ");
			buf.append("\n order by cd.SCL_CD ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
		
	}
	/**
	 * 정리의 인코딩 매체 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @throws DASException
	 */
	public static String selectEncMediaListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		
		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select ");
		buf.append("\n 	cd.SCL_CD AS TAPE_MEDIA_CLF_CD, ");
		String tapeMediaCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_TAPE_TYPE, "cd.SCL_CD");
		buf.append(tapeMediaCodeName + " AS TAPE_MEDIA_CLF_NM, ");
		buf.append("\n 	value(qr.CTGR_L_CD, '') AS CTGR_L_CD, ");
		String ctLCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "qr.CTGR_L_CD");
		buf.append(ctLCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 	value(qr.SUM_DURATION, 0) AS SUM_DURATION, ");
		buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
		buf.append("\n 	value(qr.TOT_DURATION, 0) AS TOT_DURATION, ");
		buf.append("\n 	value(qr.TOT_QTY, 0) AS TOT_QTY ");
		buf.append("\n from  ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		SCL_CD ");
		buf.append("\n 	from DAS.CODE_TBL ");
		buf.append("\n 	where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_TAPE_TYPE+"' ");                  
		buf.append("\n ) cd  ");
		buf.append("\n LEFT OUTER JOIN ( ");
		buf.append("\n 	select ");
		buf.append("\n 		qty.TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 		qty.CTGR_L_CD, ");
		buf.append("\n 		qty.SUM_DURATION, ");
		buf.append("\n 		qty.SUM_QTY, ");
		buf.append("\n 		( ");
		buf.append("\n 		select  ");
		buf.append("\n 			sub.TOT_DURATION ");
		buf.append("\n 		from DAS.ENC_MEDIATION_TBL sub ");
		buf.append("\n 		where sub.TAPE_MEDIA_CLF_CD = qty.TAPE_MEDIA_CLF_CD ");
		buf.append("\n 			and sub.CTGR_L_CD = qty.CTGR_L_CD ");
		buf.append("\n 			and sub.DD = (select max(DD) from DAS.ENC_MEDIATION_TBL where TAPE_MEDIA_CLF_CD = qty.TAPE_MEDIA_CLF_CD and CTGR_L_CD = qty.CTGR_L_CD) ");
		buf.append("\n 		) AS TOT_DURATION, ");
		buf.append("\n 		( ");
		buf.append("\n 		select  ");
		buf.append("\n 			sub.TOT_QTY ");
		buf.append("\n 		from DAS.ENC_MEDIATION_TBL sub ");
		buf.append("\n 		where sub.TAPE_MEDIA_CLF_CD = qty.TAPE_MEDIA_CLF_CD ");
		buf.append("\n 			and sub.CTGR_L_CD = qty.CTGR_L_CD ");
		buf.append("\n 			and sub.DD = (select max(DD) from DAS.ENC_MEDIATION_TBL where TAPE_MEDIA_CLF_CD = qty.TAPE_MEDIA_CLF_CD and CTGR_L_CD = qty.CTGR_L_CD) ");
		buf.append("\n 		) AS TOT_QTY ");
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 			CTGR_L_CD, ");
		buf.append("\n 			SUM(DAY_DURATION) AS SUM_DURATION, ");
		buf.append("\n 			SUM(DAY_QTY) AS SUM_QTY ");
		buf.append("\n 		from DAS.ENC_MEDIATION_TBL ");
		buf.append("\n 		where DD >= '"+fromDate+"' ");
		buf.append("\n 			and DD <= '"+toDate+"' ");
		buf.append("\n 		group by TAPE_MEDIA_CLF_CD, CTGR_L_CD ");
		buf.append("\n 	) qty ");
		buf.append("\n ) qr ON qr.TAPE_MEDIA_CLF_CD = cd.SCL_CD ");
		buf.append("\n order by cd.SCL_CD, qr.CTGR_L_CD ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 정리의 인코딩 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectEncJanreListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 		category.CTGR_L_CD,  ");
		String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
		buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 		category.CTGR_M_CD,  ");
		String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
		buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
	//	buf.append("\n 		category.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
		buf.append("\n 		ELSE category.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");	
		String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
		buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
		buf.append("\n 		value(qr.TAPE_MEDIA_CLF_CD, '') AS TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY ");
		buf.append("\n from  ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE small.SCL_CD ");
		buf.append("\n 		END      AS CTGR_S_CD ");
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
		buf.append("\n 	) large ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
		buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
		buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n ) category  ");
		buf.append("\n LEFT OUTER JOIN ( ");
		buf.append("\n 	select ");
		buf.append("\n 		qty.CTGR_L_CD,  ");
		buf.append("\n 		qty.CTGR_M_CD,  ");
	//	buf.append("\n 		qty.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE qty.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");
		buf.append("\n 		qty.TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 		qty.SUM_QTY ");
		buf.append("\n 	from ");
		buf.append("\n 	(select  ");
		buf.append("\n 		CTGR_L_CD,  ");
		buf.append("\n 		CTGR_M_CD,  ");
		buf.append("\n 		CTGR_S_CD, ");
		buf.append("\n 		TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
		buf.append("\n 	from DAS.ENC_MD_GNRSTAT_TBL ");
		buf.append("\n 	where DD >= '"+fromDate+"' ");
		buf.append("\n 		and DD <= '"+toDate+"' ");
		buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, TAPE_MEDIA_CLF_CD ");
		buf.append("\n 	) qty ");
		buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
		buf.append("\n order by category.CTGR_L_CD, category.CTGR_M_CD, category.CTGR_S_CD, qr.TAPE_MEDIA_CLF_CD ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 이용의 다운로드 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectUseJanreListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD, "); 
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 	category.CTGR_S_CD, ");
			buf.append("\n 	CASE ");
			buf.append("\n 	WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 	ELSE category.CTGR_S_CD ");
			buf.append("\n 	END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 			fetch first 1 rows only ");
			buf.append("\n 		) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 4) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		(  ");
			buf.append("\n 			 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = '')  ");
			buf.append("\n 			 			fetch first 1 rows only ");
			buf.append("\n 			 		) AS TOTAL_QTY  FROM DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(DD, 1, 4) >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 			 		and substr(DD, 1, 4) <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD, "); 
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			buf.append("\n 	category.CTGR_S_CD, ");
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	value(qr.DAY, '000000') AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 			fetch first 1 rows only ");
			buf.append("\n 		) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 6) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		(  ");
			buf.append("\n 			 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = '')  ");
			buf.append("\n 			 			fetch first 1 rows only ");
			buf.append("\n 			 		) AS TOTAL_QTY  FROM DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 			 		and substr(DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");

		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			buf.append("\n 	category.CTGR_S_CD, ");
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	'00000000' AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		( ");
			buf.append("\n 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
			buf.append("\n 			fetch first 1 rows only ");
			buf.append("\n 		) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, '00000000' AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 			 		(  ");
			buf.append("\n 			 			select sub.BY_TOTAL_QTY  ");
			buf.append("\n 			 			from DAS.GNR_DOWNSTAT_TBL sub  ");
			buf.append("\n 			 			where sub.CTGR_L_CD = '' ");
			buf.append("\n 			 				and sub.DD = (select max(DD) from DAS.GNR_DOWNSTAT_TBL where CTGR_L_CD = '')  ");
			buf.append("\n 			 			fetch first 1 rows only ");
			buf.append("\n 			 		) AS TOTAL_QTY  FROM DAS.GNR_DOWNSTAT_TBL ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and DD >= '"+fromDate+"' ");
			buf.append("\n 			 		and DD <= '"+toDate+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();

		
	}
	/**
	 * 이용의 다운로드 이용처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectUseCompanyListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	qty.CO_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.CO_CD and ORG_TYP = 'C'), '') AS COM_NM, ");
			buf.append("\n 	qty.SEG_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.SEG_CD and ORG_TYP = 'S'), '') AS CENTER_NM,  ");
			buf.append("\n 	qty.DEPT_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.DEPT_CD and ORG_TYP = 'D'), '') AS DEP_NM, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		CO_CD, ");
			buf.append("\n 		SEG_CD, ");
			buf.append("\n 		DEPT_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.USLOC_DOWNSTAT_TBL ");
			buf.append("\n 	group by CO_CD, SEG_CD, DEPT_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n  and qty.CO_CD <> '' and qty.SEG_CD <> '' and qty.DEPT_CD <> '' ");
			buf.append("\n order by DEP_NM, CENTER_NM, COM_NM  ");			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	qty.CO_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.CO_CD and ORG_TYP = 'C'), '') AS COM_NM, ");
			buf.append("\n 	qty.SEG_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.SEG_CD and ORG_TYP = 'S'), '') AS CENTER_NM,  ");
			buf.append("\n 	qty.DEPT_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.DEPT_CD and ORG_TYP = 'D'), '') AS DEP_NM, ");
			buf.append("\n 	qty.DAY, ");
			buf.append("\n 	qty.SUM_QTY ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		CO_CD, ");
			buf.append("\n 		SEG_CD, ");
			buf.append("\n 		DEPT_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.USLOC_DOWNSTAT_TBL ");
			buf.append("\n 	group by CO_CD, SEG_CD, DEPT_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n  and qty.CO_CD <> '' and qty.SEG_CD <> '' and qty.DEPT_CD <> '' ");
			buf.append("\n order by DEP_NM, CENTER_NM, COM_NM  ");			

		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 	qty.CO_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.CO_CD and ORG_TYP = 'C'), '') AS COM_NM, ");
			buf.append("\n 	qty.SEG_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.SEG_CD and ORG_TYP = 'S'), '') AS CENTER_NM, ");
			buf.append("\n 	qty.DEPT_CD, ");
			buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.DEPT_CD and ORG_TYP = 'D'), '') AS DEP_NM, ");
			buf.append("\n 	'00000000' AS DAY, ");
			buf.append("\n 	qty.SUM_QTY ");
			buf.append("\n from ");
			buf.append("\n 	( ");
			buf.append("\n 	select  ");
			buf.append("\n 		CO_CD, ");
			buf.append("\n 		SEG_CD, ");
			buf.append("\n 		DEPT_CD, ");
			buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.USLOC_DOWNSTAT_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CO_CD, SEG_CD, DEPT_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n  WHERE qty.CO_CD <> '' and qty.SEG_CD <> '' and qty.DEPT_CD <> '' ");			
			buf.append("\n order by DEP_NM, CENTER_NM, COM_NM ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();

		
	}	
	/**
	 * 이용의 다운로드 제한영상 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectUseLimitListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	qty.CO_CD, ");
		buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.CO_CD and ORG_TYP = 'C'), '') AS COM_NM, ");
		buf.append("\n 	qty.SEG_CD, ");
		buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.SEG_CD and ORG_TYP = 'S'), '') AS CENTER_NM, ");
		buf.append("\n 	qty.DEPT_CD, ");
		buf.append("\n 	value((select ORG_NM from DAS.ERP_COM_BAS_TBL where ORG_CD = qty.DEPT_CD and ORG_TYP = 'D'), '') AS DEP_NM, ");
		buf.append("\n 	'00000000' AS DAY, ");
		buf.append("\n 	qty.ANNOT_CLF_CD, ");
		buf.append("\n 	qty.SUM_QTY ");
		buf.append("\n from ");
		buf.append("\n 	( ");
		buf.append("\n 	select  ");
		buf.append("\n 		CO_CD, ");
		buf.append("\n 		SEG_CD, ");
		buf.append("\n 		DEPT_CD, ");
		buf.append("\n 		ANNOT_CLF_CD, ");
		buf.append("\n 		SUM(BY_DY_QTY) AS SUM_QTY ");
		buf.append("\n 	from DAS.STRMV_DOWNSTAT_TBL ");
		buf.append("\n 	where DD >= '"+fromDate+"' ");
		buf.append("\n 		and DD <= '"+toDate+"' ");
		buf.append("\n 	group by CO_CD, SEG_CD, DEPT_CD, ANNOT_CLF_CD ");
		buf.append("\n 	) qty ");
		buf.append("\n  WHERE qty.CO_CD <> '' and qty.SEG_CD <> '' and qty.DEPT_CD <> '' ");
		buf.append("\n order by DEP_NM, CENTER_NM, COM_NM, qty.ANNOT_CLF_CD  ");
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
	}
	/**
	 * 폐기 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectDisuseListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 	category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");		
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN '' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_DISUSESTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n union all ");
			buf.append("\n ( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 4) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM FROM DAS.GNR_DISUSESTAT_TBL "); 
			buf.append("\n   WHERE CTGR_L_CD = '' ");
			buf.append("\n       and substr(DD, 1, 4) >= '"+fromDate.substring(0, 4)+"' "); 
			buf.append("\n 		 and substr(DD, 1, 4) <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n   GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			buf.append("\n 	category.CTGR_S_CD, ");
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	value(qr.DAY, '000000') AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_DISUSESTAT_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n 	where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n union all ");
			buf.append("\n ( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(DD, 1, 6) AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM FROM DAS.GNR_DISUSESTAT_TBL "); 
			buf.append("\n   WHERE CTGR_L_CD = '' ");
			buf.append("\n       and substr(DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' "); 
			buf.append("\n 		 and substr(DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n   GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");

		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 	category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 	category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			buf.append("\n 	category.CTGR_S_CD, ");
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 	'00000000' AS DAY, ");
			buf.append("\n 	value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 	value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM ");
			buf.append("\n 	from DAS.GNR_DISUSESTAT_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n union all ");
			buf.append("\n ( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, '00000000' AS DAY, sum(BY_DY_QTY) AS SUM_QTY, "); 
			buf.append("\n 		sum(BY_DY_TM) AS SUM_TM FROM DAS.GNR_DISUSESTAT_TBL "); 
			buf.append("\n   WHERE CTGR_L_CD = '' ");
			buf.append("\n       and DD >= '"+fromDate+"' "); 
			buf.append("\n 		 and DD <= '"+toDate+"' ");
			buf.append("\n   GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");

		}
		buf.append("\n WITH UR	 ");
		
		return buf.toString();
		
	}
	/**
	 * 보유량 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectHoldingListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		
		buf.append("\n select ");
		buf.append("\n 		category.CTGR_L_CD,  ");
		String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
		buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 		category.CTGR_M_CD,  ");
		String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
		buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");		
		buf.append("\n 		category.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
		buf.append("\n 		ELSE category.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");		
		String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
		buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
		buf.append("\n 		value(qr.METER_TYPE, '') AS METER_TYPE, ");
		buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
		buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY, ");
		buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
		buf.append("\n from ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE small.SCL_CD ");
		buf.append("\n 		END      AS CTGR_S_CD ");
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
		buf.append("\n 	) large ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
		buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
		buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n ) category ");
		buf.append("\n LEFT OUTER JOIN ( ");
		buf.append("\n 	select ");
		buf.append("\n 		qty.CTGR_L_CD,  ");
		buf.append("\n 		qty.CTGR_M_CD,  ");
		//buf.append("\n 		qty.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE qty.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");
		buf.append("\n 		qty.METER_TYPE, ");
		buf.append("\n 		qty.SUM_QTY, ");
		buf.append("\n 		( ");
		buf.append("\n 			select sub.BY_PURE_QTY ");
		buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
		buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
		buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
		buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
		buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
		buf.append("\n 		) AS TOTAL_QTY, ");
		buf.append("\n 		( ");
		buf.append("\n 			select sub.BY_PURE_TIME  ");
		buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
		buf.append("\n 			where sub.CTGR_L_CD = qty.CTGR_L_CD ");
		buf.append("\n 				and sub.CTGR_M_CD = qty.CTGR_M_CD ");
		buf.append("\n 				and sub.CTGR_S_CD = qty.CTGR_S_CD ");
		buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD) ");
		buf.append("\n 		) AS TOTAL_TM ");
		buf.append("\n 	from ");
		buf.append("\n 	(select  ");
		buf.append("\n 		CTGR_L_CD,  ");
		buf.append("\n 		CTGR_M_CD,  ");
		buf.append("\n 		CTGR_S_CD, ");
		buf.append("\n 		METER_TYPE, ");
		buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
		buf.append("\n 	from DAS.GRN_RSVSTAT_TBL ");
		buf.append("\n 	where DD >= '"+fromDate+"' ");
		buf.append("\n 		and DD <= '"+toDate+"' ");
		buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, METER_TYPE ");
		buf.append("\n 	) qty ");
		buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
		buf.append("\n order by category.CTGR_L_CD, category.CTGR_M_CD, category.CTGR_S_CD, qr.METER_TYPE ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}
	/**
	 * 종합 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectTotalStatisticsListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	category.CTGR_L_CD, "); 
		String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
		buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 	category.CTGR_M_CD, "); 
		String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
		buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
		//buf.append("\n 	category.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN category.CTGR_S_CD  = 'N' THEN '' ");
		buf.append("\n 		ELSE category.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");
		String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
		buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
		buf.append("\n 	value(qr.MV_SUM_QTY, 0) AS MV_SUM_QTY, ");
		buf.append("\n 	value(qr.MD_SUM_QTY, 0) AS MD_SUM_QTY, ");
		buf.append("\n 	value(qr.DW_SUM_QTY, 0) AS DW_SUM_QTY, ");
		buf.append("\n 	value(qr.DS_SUM_QTY, 0) AS DS_SUM_QTY, ");
		buf.append("\n 	value(qr.TOTAL_QTY, 0) AS TOTAL_QTY, ");
		buf.append("\n 	value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
		buf.append("\n from ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
	//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN   small.SCL_CD is null or    small.SCL_CD  = '' THEN 'N' ");
		buf.append("\n 		ELSE  small.SCL_CD ");
		buf.append("\n 		END      AS CTGR_S_CD ");	
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
		buf.append("\n 	) large ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
		buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
		buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n ) category  ");
		buf.append("\n LEFT OUTER JOIN ( ");
		buf.append("\n 	select ");
		buf.append("\n 		mv.CTGR_L_CD,  ");
		buf.append("\n 		mv.CTGR_M_CD,  ");
		//buf.append("\n 		mv.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN   mv.CTGR_S_CD is null or    mv.CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE   mv.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");	
		buf.append("\n 		mv.MV_SUM_QTY, ");
		buf.append("\n 		md.MD_SUM_QTY, ");
		buf.append("\n 		dw.DW_SUM_QTY, ");
		buf.append("\n 		ds.DS_SUM_QTY, ");
		buf.append("\n 		( ");
		buf.append("\n 		select sub.BY_PURE_QTY ");
		buf.append("\n 		from DAS.GRN_RSVSTAT_TBL sub  ");
		buf.append("\n 		where sub.CTGR_L_CD = mv.CTGR_L_CD ");
		buf.append("\n 			and sub.CTGR_M_CD = mv.CTGR_M_CD ");
		buf.append("\n 			and  mv.CTGR_S_CD in ('N', sub.CTGR_S_CD) ");
		buf.append("\n 			and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = mv.CTGR_L_CD and CTGR_M_CD = mv.CTGR_M_CD and mv.CTGR_S_CD in ('N', sub.CTGR_S_CD)) ");
		buf.append("\n 		) AS TOTAL_QTY, ");
		buf.append("\n 		( ");
		buf.append("\n 			select sub.BY_PURE_TIME  ");
		buf.append("\n 			from DAS.GRN_RSVSTAT_TBL sub  ");
		buf.append("\n 			where sub.CTGR_L_CD = mv.CTGR_L_CD ");
		buf.append("\n 				and sub.CTGR_M_CD = mv.CTGR_M_CD ");
		buf.append("\n 				and  mv.CTGR_S_CD in ('N', sub.CTGR_S_CD) ");
		buf.append("\n 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = mv.CTGR_L_CD and CTGR_M_CD = mv.CTGR_M_CD and mv.CTGR_S_CD in ('N', sub.CTGR_S_CD)) ");
		buf.append("\n 		) AS TOTAL_TM ");
		buf.append("\n 	from ");
		
		buf.append("\n ( ");
		buf.append("\n		select ");
		buf.append("\n			large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n			middle.SCL_CD AS CTGR_M_CD, "); 
		buf.append("\n			CASE ");
		buf.append("\n			WHEN   small.SCL_CD is null or    small.SCL_CD  = '' THEN 'N' ");
		buf.append("\n			ELSE  small.SCL_CD ");
		buf.append("\n			END      AS CTGR_S_CD ");
		buf.append("\n		from ");
		buf.append("\n		( ");
		buf.append("\n			select  ");
		buf.append("\n				SCL_CD ");
		buf.append("\n			from DAS.CODE_TBL ");
		buf.append("\n			where CLF_CD = 'P002' ");
		buf.append("\n		) large ");
		buf.append("\n		LEFT OUTER JOIN ( ");
		buf.append("\n			select  ");
		buf.append("\n				SCL_CD ");
		buf.append("\n			from DAS.CODE_TBL ");
		buf.append("\n			where CLF_CD = 'P003' ");
		buf.append("\n		) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n		LEFT OUTER JOIN ( ");
		buf.append("\n			select  ");
		buf.append("\n				SCL_CD ");
		buf.append("\n			from DAS.CODE_TBL ");
		buf.append("\n			where CLF_CD = 'P004' ");
		buf.append("\n		)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n		group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n	) category2 ");
		
		buf.append("\n 		LEFT OUTER JOIN (select  ");
		buf.append("\n 			CTGR_L_CD,  ");
		buf.append("\n 			CTGR_M_CD,  ");
//		buf.append("\n 			CTGR_S_CD, ");
		buf.append("\n 			CASE ");
		buf.append("\n 			WHEN  CTGR_S_CD is null or   CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 			ELSE  CTGR_S_CD ");
		buf.append("\n 			END      AS CTGR_S_CD,  ");	     
		buf.append("\n 			sum(BY_DY_QTY) AS MV_SUM_QTY ");
		buf.append("\n 		from DAS.GNR_MVSTAT_TBL ");
		buf.append("\n 		where DD >= '"+fromDate+"' ");
		buf.append("\n 			and DD <= '"+toDate+"' ");
		buf.append("\n 		group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n 		) mv on mv.CTGR_L_CD = category2.CTGR_L_CD and mv.CTGR_M_CD = category2.CTGR_M_CD and mv.CTGR_S_CD = category2.CTGR_S_CD  ");
		buf.append("\n 		LEFT OUTER JOIN (select  ");
		buf.append("\n 			CTGR_L_CD,  ");
		buf.append("\n 			CTGR_M_CD,  ");
//		buf.append("\n 			CTGR_S_CD, ");
		buf.append("\n 			CASE ");
		buf.append("\n 			WHEN  CTGR_S_CD is null or   CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 			ELSE  CTGR_S_CD ");
		buf.append("\n 			END      AS CTGR_S_CD,  ");	   
		buf.append("\n 			sum(BY_DY_QTY) AS MD_SUM_QTY ");
		buf.append("\n 		from DAS.ENC_MD_GNRSTAT_TBL ");
		buf.append("\n 		where DD >= '"+fromDate+"' ");
		buf.append("\n 			and DD <= '"+toDate+"' ");
		buf.append("\n 		group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n 		) md ON category2.CTGR_L_CD = md.CTGR_L_CD and category2.CTGR_M_CD = md.CTGR_M_CD and category2.CTGR_S_CD = md.CTGR_S_CD  ");
		buf.append("\n 		LEFT OUTER JOIN (select  ");
		buf.append("\n 			CTGR_L_CD,  ");
		buf.append("\n 			CTGR_M_CD,  ");
//		buf.append("\n 			CTGR_S_CD, ");
		buf.append("\n 			CASE ");
		buf.append("\n 			WHEN  CTGR_S_CD is null or   CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 			ELSE  CTGR_S_CD ");
		buf.append("\n 			END      AS CTGR_S_CD,  ");	   
		buf.append("\n 			sum(BY_DY_QTY) AS DW_SUM_QTY ");
		buf.append("\n 		from DAS.GNR_DOWNSTAT_TBL ");
		buf.append("\n 		where DD >= '"+fromDate+"' ");
		buf.append("\n 			and DD <= '"+toDate+"' ");
		buf.append("\n 		group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n 		) dw ON category2.CTGR_L_CD = dw.CTGR_L_CD and category2.CTGR_M_CD = dw.CTGR_M_CD and category2.CTGR_S_CD = dw.CTGR_S_CD  ");
		buf.append("\n 		LEFT OUTER JOIN (select  ");
		buf.append("\n 			CTGR_L_CD,  ");
		buf.append("\n 			CTGR_M_CD,  ");
//		buf.append("\n 			CTGR_S_CD, ");
		buf.append("\n 			CASE ");
		buf.append("\n 			WHEN  CTGR_S_CD is null or   CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 			ELSE  CTGR_S_CD ");
		buf.append("\n 			END      AS CTGR_S_CD,  ");	   
		buf.append("\n 			sum(BY_DY_QTY) AS DS_SUM_QTY ");
		buf.append("\n 		from DAS.GNR_DISUSESTAT_TBL ");
		buf.append("\n 		where DD >= '"+fromDate+"' ");
		buf.append("\n 			and DD <= '"+toDate+"' ");
		buf.append("\n 		group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n 		) ds ON category2.CTGR_L_CD = ds.CTGR_L_CD and category2.CTGR_M_CD = ds.CTGR_M_CD and category2.CTGR_S_CD = ds.CTGR_S_CD  ");
		buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
		buf.append("\n union all ");
		buf.append("\n ( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, "); 
		buf.append("\n     value((select  ");
		buf.append("\n  			sum(BY_DY_QTY) AS MV_SUM_QTY "); 
		buf.append("\n  		from DAS.GNR_MVSTAT_TBL "); 
		buf.append("\n  		where CTGR_L_CD = '' ");
		buf.append("\n 		  and DD >= '"+fromDate+"' ");
		buf.append("\n 		  and DD <= '"+toDate+"' "); 
		buf.append("\n        ), 0) AS MV_SUM_QTY, "); 
		buf.append("\n     value((select ");  
		buf.append("\n             sum(BY_DY_QTY) AS MD_SUM_QTY ");
		buf.append("\n            from DAS.ENC_MD_GNRSTAT_TBL "); 
		buf.append("\n            where CTGR_L_CD = '' ");
		buf.append("\n               and DD >= '"+fromDate+"' "); 
		buf.append("\n               and DD <= '"+toDate+"' ");
		buf.append("\n 		 		), 0) AS MD_SUM_QTY, ");
		buf.append("\n 				value((select ");
		buf.append("\n 		 			sum(BY_DY_QTY) AS DW_SUM_QTY "); 
		buf.append("\n 		 		from DAS.GNR_DOWNSTAT_TBL ");
		buf.append("\n 		 		where CTGR_L_CD = '' ");
		buf.append("\n 				  and DD >= '"+fromDate+"' ");
		buf.append("\n 		 			and DD <= '"+toDate+"' ");
		buf.append("\n 		 		), 0) AS DW_SUM_QTY, ");
		buf.append("\n 				value((select ");  
		buf.append("\n 		 			sum(BY_DY_QTY) AS DS_SUM_QTY ");
		buf.append("\n 		 		from DAS.GNR_DISUSESTAT_TBL "); 
		buf.append("\n 		 		where CTGR_L_CD = '' ");
		buf.append("\n 				  and DD >= '"+fromDate+"' "); 
		buf.append("\n 		 			and DD <= '"+toDate+"' "); 
		buf.append("\n 		 		), 0) AS DS_SUM_QTY, ");
		buf.append("\n 		 		value(( "); 
		buf.append("\n 		 		select sub.BY_PURE_QTY "); 
		buf.append("\n 		 		from DAS.GRN_RSVSTAT_TBL sub ");
		buf.append("\n 		 		where sub.CTGR_L_CD = '' ");
		buf.append("\n 		 			and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') "); 
		buf.append("\n 		 		), 0) AS TOTAL_QTY, "); 
		buf.append("\n 		 		value(( ");
		buf.append("\n 		 			select sub.BY_PURE_TIME  ");
		buf.append("\n 		 			from DAS.GRN_RSVSTAT_TBL sub  ");
		buf.append("\n 		 			where sub.CTGR_L_CD = '' ");
		buf.append("\n 		 				and sub.DD = (select max(DD) from DAS.GRN_RSVSTAT_TBL where CTGR_L_CD = '') ");
		buf.append("\n 		 		), 0) AS TOTAL_TM  FROM SYSIBM.SYSDUMMY1 ");
		buf.append("\n 		) ");
		buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}
	
	/**
	 * 분류별 정리 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectAdjustmentListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	category.CTGR_L_CD, "); 
		String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
		buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 	category.CTGR_M_CD, "); 
		String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
		buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
		//buf.append("\n 	category.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
		buf.append("\n 		ELSE category.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");	
		String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
		buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
		buf.append("\n 		value(qr.SUM_MEDIA_GATH_QTY, 0) AS SUM_MEDIA_GATH_QTY, ");
		buf.append("\n 		value(qr.SUM_MCV_DY_ARRG_QTY, 0) AS SUM_MCV_DY_ARRG_QTY, ");
		buf.append("\n 		value(qr.SUM_ONAIR_DY_GATH_QTY, 0) AS SUM_ONAIR_DY_GATH_QTY, ");
		buf.append("\n 		value(qr.ONAIR_DY_ARRG_QTY, 0) AS SUM_ONAIR_DY_ARRG_QTY ");
		buf.append("\n from  ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE small.SCL_CD ");
		buf.append("\n 		END      AS CTGR_S_CD ");
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
		buf.append("\n 	) large ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
		buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
		buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n ) category  ");
		buf.append("\n LEFT OUTER JOIN ( ");
		buf.append("\n 	select ");
		buf.append("\n 		qty.CTGR_L_CD,  ");
		buf.append("\n 		qty.CTGR_M_CD,  ");
		//buf.append("\n 		qty.CTGR_S_CD, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
		buf.append("\n 		ELSE qty.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");
		buf.append("\n 		qty.SUM_MEDIA_GATH_QTY, ");
		buf.append("\n 		qty.SUM_MCV_DY_ARRG_QTY, ");
		buf.append("\n 		qty.SUM_ONAIR_DY_GATH_QTY, ");
		buf.append("\n 		qty.ONAIR_DY_ARRG_QTY ");
		buf.append("\n 	from ");
		buf.append("\n 	(select  ");
		buf.append("\n 		CTGR_L_CD,  ");
		buf.append("\n 		CTGR_M_CD,  ");
		buf.append("\n 		CTGR_S_CD, ");
		buf.append("\n 		sum(MCV_DY_GATH_QTY) AS SUM_MEDIA_GATH_QTY, ");
		buf.append("\n 		sum(MCV_DY_ARRG_QTY) AS SUM_MCV_DY_ARRG_QTY, ");
		buf.append("\n 		sum(ONAIR_DY_GATH_QTY) AS SUM_ONAIR_DY_GATH_QTY, ");
		buf.append("\n 		sum(ONAIR_DY_ARRG_QTY) AS ONAIR_DY_ARRG_QTY ");
		buf.append("\n 	from DAS.SCD_ARCH_STAT_TBL ");
		buf.append("\n 	where DD >= '"+fromDate+"' ");
		buf.append("\n 		and DD <= '"+toDate+"' ");
		buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n 	) qty ");
		buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
		buf.append("\n 		union all ");
		buf.append("\n 		( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, sum(MCV_DY_GATH_QTY) AS SUM_MEDIA_GATH_QTY,  ");
		buf.append("\n 		 		sum(MCV_DY_ARRG_QTY) AS SUM_MCV_DY_ARRG_QTY, sum(ONAIR_DY_GATH_QTY) AS SUM_ONAIR_DY_GATH_QTY, sum(ONAIR_DY_ARRG_QTY) AS SUM_ONAIR_DY_ARRG_QTY FROM DAS.SCD_ARCH_STAT_TBL "); 
		buf.append("\n 		 WHERE CTGR_L_CD = '' ");
		buf.append("\n 		and DD >= '"+fromDate+"' ");
		buf.append("\n 		 		and DD <= '"+toDate+"' ");
		buf.append("\n 		GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
		buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		
		return buf.toString();
	}
	/**
	 * 작업자별 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectWorkUserStatisticsListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	qr.WORKER_CLF, ");
		String workerCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_DAS_WORKER_CODE, "qr.WORKER_CLF");
		buf.append(workerCodeName + " AS WORKER_CLF_NM, ");
		buf.append("\n 	qr.WORK_USER_ID, ");
		buf.append("\n 	qr.ITEM, ");
		buf.append("\n 	qr.PRGM, ");
		buf.append("\n 	qr.DRAMA, ");
		buf.append("\n 	qr.EDUCTNAL, ");
		buf.append("\n 	qr.ENTERTAIN, ");
		buf.append("\n 	qr.EXTRA, ");
		buf.append("\n 	qr.REF, ");
		buf.append("\n 	qr.TOTAL ");
		buf.append("\n from ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		WORKER_CLF, ");
		buf.append("\n 		WORK_USER_ID, ");
		buf.append("\n 		sum(ITEM) AS ITEM, ");
		buf.append("\n 		sum(PRGM) AS PRGM, ");
		buf.append("\n 		sum(DRAMA) AS DRAMA, ");
		buf.append("\n 		sum(EDUCTNAL) AS EDUCTNAL, ");
		buf.append("\n 		sum(ENTERTAIN) AS ENTERTAIN, ");
		buf.append("\n 		sum(EXTRA) AS EXTRA, ");
		buf.append("\n 		sum(REF) AS REF, ");
		buf.append("\n 		sum(TOTAL) AS TOTAL ");
		buf.append("\n 	from DAS.WORKER_STAT_TBL ");
		buf.append("\n 	where DD >= '"+fromDate+"' ");
		buf.append("\n 		and DD <= '"+toDate+"' ");
		buf.append("\n 	group by WORKER_CLF, WORK_USER_ID ");
		buf.append("\n ) qr ");
		buf.append("\n order by qr.WORKER_CLF, qr.WORK_USER_ID ");

		
		return buf.toString();
	}
	/**
	 * 이용의 로그인 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectLoginListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n FROM ");
			buf.append("\n         (SELECT SUBSTR(ACCESSTIME, 1, 4) as DATE, COUNT(SUBSTR(ACCESSTIME, 1, 4)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n         FROM DAS.IDLOG_TBL ");
			buf.append("\n         WHERE SUBSTR(USER_ID, 1, 1) = 'S' ");
			buf.append("\n         GROUP BY SUBSTR(ACCESSTIME, 1, 4) UNION ALL ");

			buf.append("\n         SELECT SUBSTR(ACCESSTIME, 1, 4) as DATE, 0 as S_COUNT, COUNT(SUBSTR(ACCESSTIME, 1, 4)) as D_COUNT "); 
			buf.append("\n         FROM DAS.IDLOG_TBL ");
			buf.append("\n         WHERE SUBSTR(USER_ID, 1, 1) = 'D' ");
			buf.append("\n         GROUP BY SUBSTR(ACCESSTIME, 1, 4)) as A ");
			buf.append("\n WHERE DATE >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n         AND DATE <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n GROUP BY DATE ");
			buf.append("\n ORDER BY A.DATE ");
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n FROM ");
			buf.append("\n         (SELECT SUBSTR(ACCESSTIME, 1, 6) as DATE, COUNT(SUBSTR(ACCESSTIME, 1, 6)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n         FROM DAS.IDLOG_TBL ");
			buf.append("\n         WHERE SUBSTR(USER_ID, 1, 1) = 'S' ");
			buf.append("\n         GROUP BY SUBSTR(ACCESSTIME, 1, 6) UNION ALL ");

			buf.append("\n         SELECT SUBSTR(ACCESSTIME, 1, 6) as DATE, 0 as S_COUNT, COUNT(SUBSTR(ACCESSTIME, 1, 6)) as D_COUNT "); 
			buf.append("\n         FROM DAS.IDLOG_TBL ");
			buf.append("\n         WHERE SUBSTR(USER_ID, 1, 1) = 'D' ");
			buf.append("\n         GROUP BY SUBSTR(ACCESSTIME, 1, 6)) as A ");
			buf.append("\n WHERE DATE >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n         AND DATE <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n GROUP BY DATE ");
			buf.append("\n ORDER BY A.DATE ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n SELECT '' as DATE , SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT  ");
			buf.append("\n FROM ( ");
			buf.append("\n         SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n         FROM ");
			buf.append("\n                 (SELECT SUBSTR(ACCESSTIME, 1, 8) as DATE, COUNT(SUBSTR(ACCESSTIME, 1, 8)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n                 FROM DAS.IDLOG_TBL ");
			buf.append("\n                 WHERE SUBSTR(USER_ID, 1, 1) = 'S' ");
			buf.append("\n                 GROUP BY SUBSTR(ACCESSTIME, 1, 8) UNION ALL ");

			buf.append("\n                 SELECT SUBSTR(ACCESSTIME, 1, 8) as DATE, 0 as S_COUNT, COUNT(SUBSTR(ACCESSTIME, 1, 8)) as D_COUNT "); 
			buf.append("\n                 FROM DAS.IDLOG_TBL ");
			buf.append("\n                 WHERE SUBSTR(USER_ID, 1, 1) = 'D' ");
			buf.append("\n                 GROUP BY SUBSTR(ACCESSTIME, 1, 8)) as A ");
			buf.append("\n         GROUP BY DATE ");
			buf.append("\n )A ");
			buf.append("\n WHERE DATE >= '"+fromDate+"' ");
			buf.append("\n 		   AND DATE <= '"+toDate+"' ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 일반 검색 상세 검색 로그를 남긴다.
	 * @param flag 조건
	 * @param commonDO 공통정보
	 */
	public static String insertSearchDate(String flag, DASCommonDO commonDO)
	{
		String userId = commonDO.getUserId();

		StringBuffer buf = new StringBuffer();
		
		if (flag.equals("S")) {
			buf.append("\n INSERT INTO DAS.SEARCHLOG_TBL(USER_ID, SIMP_SRCH_DT, DETL_SRCH_DT) ");
			buf.append("\n VALUES ('" + userId + "', substr(hex((CURRENT timestamp)), 1, 14), '') ");
		} else {
			buf.append("\n INSERT INTO DAS.SEARCHLOG_TBL(USER_ID, SIMP_SRCH_DT, DETL_SRCH_DT) ");
			buf.append("\n VALUES ('" + userId + "', '', substr(hex((CURRENT timestamp)), 1, 14)) ");
		}
		return buf.toString();
	}
	/**
	 * 이용의 검색 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectSearchListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n FROM ");
			buf.append("\n         (SELECT SUBSTR(SIMP_SRCH_DT, 1, 4) as DATE, COUNT(SUBSTR(SIMP_SRCH_DT, 1, 4)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n         FROM DAS.SEARCHLOG_TBL ");
			buf.append("\n         WHERE SIMP_SRCH_DT <> '' ");
			buf.append("\n         GROUP BY SUBSTR(SIMP_SRCH_DT, 1, 4) UNION ALL ");

			buf.append("\n         SELECT SUBSTR(DETL_SRCH_DT, 1, 4) as DATE, 0 as S_COUNT, COUNT(SUBSTR(DETL_SRCH_DT, 1, 4)) as D_COUNT "); 
			buf.append("\n         FROM DAS.SEARCHLOG_TBL  ");
			buf.append("\n         WHERE DETL_SRCH_DT <> '' ");
			buf.append("\n         GROUP BY SUBSTR(DETL_SRCH_DT, 1, 4)) as A ");
			buf.append("\n WHERE DATE >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n         AND DATE <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n GROUP BY DATE ");
			buf.append("\n ORDER BY A.DATE ");
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n FROM ");
			buf.append("\n         (SELECT SUBSTR(SIMP_SRCH_DT, 1, 6) as DATE, COUNT(SUBSTR(SIMP_SRCH_DT, 1, 6)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n         FROM DAS.SEARCHLOG_TBL ");
			buf.append("\n         WHERE SIMP_SRCH_DT <> '' ");
			buf.append("\n         GROUP BY SUBSTR(SIMP_SRCH_DT, 1, 6) UNION ALL ");

			buf.append("\n         SELECT SUBSTR(DETL_SRCH_DT, 1, 6) as DATE, 0 as S_COUNT, COUNT(SUBSTR(DETL_SRCH_DT, 1, 6)) as D_COUNT "); 
			buf.append("\n         FROM DAS.SEARCHLOG_TBL  ");
			buf.append("\n         WHERE DETL_SRCH_DT <> '' ");
			buf.append("\n         GROUP BY SUBSTR(DETL_SRCH_DT, 1, 6)) as A ");
			buf.append("\n WHERE DATE >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n         AND DATE <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n GROUP BY DATE ");
			buf.append("\n ORDER BY A.DATE ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n SELECT '' as DATE , SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT  ");
			buf.append("\n FROM ( ");
			buf.append("\n         SELECT DATE, SUM(S_COUNT) as S_COUNT, SUM(D_COUNT) as D_COUNT ");
			buf.append("\n         FROM ");
			buf.append("\n                 (SELECT SUBSTR(SIMP_SRCH_DT, 1, 8) as DATE, COUNT(SUBSTR(SIMP_SRCH_DT, 1, 8)) as S_COUNT, 0 as D_COUNT "); 
			buf.append("\n                 FROM DAS.SEARCHLOG_TBL ");
			buf.append("\n                 WHERE SIMP_SRCH_DT <> '' ");
			buf.append("\n                 GROUP BY SUBSTR(SIMP_SRCH_DT, 1, 8) UNION ALL ");

			buf.append("\n                 SELECT SUBSTR(DETL_SRCH_DT, 1, 8) as DATE, 0 as S_COUNT, COUNT(SUBSTR(DETL_SRCH_DT, 1, 8)) as D_COUNT "); 
			buf.append("\n                 FROM DAS.SEARCHLOG_TBL  ");
			buf.append("\n                 WHERE DETL_SRCH_DT <> '' ");
			buf.append("\n                 GROUP BY SUBSTR(DETL_SRCH_DT, 1, 8)) as A ");
			buf.append("\n         GROUP BY DATE ");
			buf.append("\n )A ");
			buf.append("\n WHERE DATE >= '"+fromDate+"' ");
			buf.append("\n 		   AND DATE <= '"+toDate+"' ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	/**
	 * 사진 다운로드/삭제 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 */
	public static String selectPicDownDeleteListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}
		
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	category.CTGR_L_CD, "); 
		String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
		buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
		buf.append("\n 	category.CTGR_M_CD, "); 
		String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
		buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN category.CTGR_S_CD  = 'N' THEN '' ");
		buf.append("\n 		ELSE category.CTGR_S_CD ");
		buf.append("\n 		END      AS CTGR_S_CD, ");
		String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
		buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
		buf.append("\n 	(select count(*) ");
		buf.append("\n 	  from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = category.CTGR_L_CD ");
		buf.append("\n 		  and pdt.CTGR_M_CD = category.CTGR_M_CD ");
		buf.append("\n 			and pdt.CTGR_S_CD = category.CTGR_S_CD ");
		buf.append("\n 			and pdt.REQ_DT >= '"+fromDate+"' ");
		buf.append("\n 			and pdt.REQ_DT <= '"+toDate+"' ");
		buf.append("\n 			and pdt.CHECK = '2') as md_sum_qty,");
		buf.append("\n 	(select count(*) ");
		buf.append("\n 	  from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = category.CTGR_L_CD ");
		buf.append("\n 		  and pdt.CTGR_M_CD = category.CTGR_M_CD ");
		buf.append("\n 			and pdt.CTGR_S_CD = category.CTGR_S_CD ");
		buf.append("\n 			and pdt.REQ_DT >= '"+fromDate+"' ");
		buf.append("\n 			and pdt.REQ_DT <= '"+toDate+"' ");
		buf.append("\n 			and pdt.CHECK = '1') as DW_SUM_QTY,");
		buf.append("\n	(select count(*) "); 
		buf.append("\n    from das.PHOT_TBL pt "); 
		buf.append("\n	  inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
		buf.append("\n	  inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id "); 
		buf.append("\n	  where pit.CTGR_L_CD = category.CTGR_L_CD "); 
		buf.append("\n	    and pit.CTGR_M_CD = category.CTGR_M_CD "); 
		buf.append("\n		and pit.CTGR_S_CD = category.CTGR_S_CD "); 
		buf.append("\n		and pt.GATH_DD >= '"+fromDate+"' "); 
		buf.append("\n		and pt.GATH_DD <= '"+toDate+"' ");
		buf.append("\n	 ) as RG_SUM_QTY, ");
		buf.append("\n   (select sum(c.cnt) from ");
		buf.append("\n   	(select b.pgm_id, b.cnt, pit.CTGR_L_CD, pit.CTGR_M_CD, pit.CTGR_S_CD ");
		buf.append("\n   	  from "); 
		buf.append("\n   	(select pgm_id, count(*) as cnt ");
		buf.append("\n    		from das.PGM_PHOT_INFO_TBL ppit ");
		buf.append("\n   				group by pgm_id ");
		buf.append("\n   		)as b "); 
		buf.append("\n   		inner join das.PGM_INFO_TBL pit on pit.pgm_id = b.pgm_id ");
		buf.append("\n   	) as c where category.ctgr_l_cd = c.ctgr_l_cd and category.ctgr_m_cd = c.ctgr_m_cd and category.ctgr_s_cd = c.ctgr_s_cd ");
		buf.append("\n   ) AS TOTAL_QTY  ");
		buf.append("\n from ");
		buf.append("\n ( ");
		buf.append("\n 	select ");
		buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
		buf.append("\n 		middle.SCL_CD AS CTGR_M_CD, "); 
		buf.append("\n 		CASE ");
		buf.append("\n 		WHEN   small.SCL_CD is null or    small.SCL_CD  = '' THEN '' ");
		buf.append("\n 		ELSE  small.SCL_CD ");
		buf.append("\n 		END      AS CTGR_S_CD 	");
		buf.append("\n 	from ");
		buf.append("\n 	( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = 'P002' ");
		buf.append("\n 	) large ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = 'P003' ");
		buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
		buf.append("\n 	LEFT OUTER JOIN ( ");
		buf.append("\n 		select  ");
		buf.append("\n 			SCL_CD ");
		buf.append("\n 		from DAS.CODE_TBL ");
		buf.append("\n 		where CLF_CD = 'P004' ");
		buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
		buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
		buf.append("\n ) category ");
		buf.append("\n 		union all ");
		buf.append("\n 		( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, ");
		buf.append("\n 		  (select count(*) "); 
		buf.append("\n 		 	  from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
		buf.append("\n 		 			and pdt.REQ_DT >= '"+fromDate+"' ");
		buf.append("\n 		 			and pdt.REQ_DT <= '"+toDate+"' ");
		buf.append("\n 		 			and pdt.CHECK = '2') as md_sum_qty, ");
		buf.append("\n 		 	(select count(*)  ");
		buf.append("\n 		 	  from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
		buf.append("\n 		 			and pdt.REQ_DT >= '"+fromDate+"'  ");
		buf.append("\n 		 			and pdt.REQ_DT <= '"+toDate+"' ");
		buf.append("\n 		 			and pdt.CHECK = '1') as DW_SUM_QTY, ");
		buf.append("\n			(select count(*) "); 
		buf.append("\n  		 from das.PHOT_TBL pt "); 
		buf.append("\n       	 inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
		buf.append("\n			 inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id "); 
		buf.append("\n			    where pit.CTGR_L_CD = '' "); 
		buf.append("\n				  and pt.GATH_DD >= '"+fromDate+"' "); 
		buf.append("\n				  and pt.GATH_DD <= '"+toDate+"' ");
		buf.append("\n			 ) as RG_SUM_QTY, ");
		buf.append("\n 		   (select sum(c.cnt) from ");
		buf.append("\n 		   	(select b.pgm_id, b.cnt, pit.CTGR_L_CD, pit.CTGR_M_CD, pit.CTGR_S_CD ");
		buf.append("\n 		   	  from  ");
		buf.append("\n 		   	(select pgm_id, count(*) as cnt  ");
		buf.append("\n 		    		from das.PGM_PHOT_INFO_TBL ppit ");
		buf.append("\n 		   				group by pgm_id ");
		buf.append("\n 		   		)as b ");
		buf.append("\n 		   		inner join das.PGM_INFO_TBL pit on pit.pgm_id = b.pgm_id ");
		buf.append("\n 		   	) as c where c.ctgr_l_cd = '' ");
		buf.append("\n 		   ) AS TOTAL_QTY ");
		buf.append("\n 			FROM DAS.PHOT_DOWN_TBL ");
		buf.append("\n 		 WHERE CTGR_L_CD = '' ");
		buf.append("\n 		and REQ_DT >= '"+fromDate+"' ");
		buf.append("\n 		 			and REQ_DT <= '"+toDate+"' ");
		buf.append("\n 		GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
		buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
		buf.append("\n WITH UR	 ");
		
		
		return buf.toString();
	}
	
	
	/**
	 *  사진 등록 엑셀  
	 * @param conditionDO
	 * @return
	 */
	public static String selectPicRegisterListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
//			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
//			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		(( ");
			buf.append("\n 			select count(*)  ");
			buf.append("\n 			from das.PHOT_TBL pt  ");
			buf.append("\n 				inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 				inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 				where pit.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and pit.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and pit.CTGR_S_CD = qty.CTGR_S_CD "); 
			buf.append("\n 			and CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2')) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(pt.GATH_DD, 1, 6) AS DAY, ");
			buf.append("\n 		count(*) AS SUM_QTY ");
			buf.append("\n 	from das.PHOT_TBL pt ");
			buf.append("\n 	inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 	inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 	group by pit.CTGR_L_CD, pit.CTGR_M_CD, pit.CTGR_S_CD, substr(pt.GATH_DD, 1, 6)  ");
			buf.append("\n 	) qty ");
			
			
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(gath_DD, 1, 6) AS DAY, count(*) AS SUM_QTY, "); 
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select count(*)  ");
			buf.append("\n 			 			from das.PHOT_TBL pt  ");
			buf.append("\n 			 			inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 			 			inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 			where pit.CTGR_L_CD = ''  ");
			buf.append("\n 			 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2'), 0) AS TOTAL_QTY ");
			buf.append("\n 		  from das.PHOT_TBL pt ");
			buf.append("\n 		  inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 		  inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(GATH_DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' "); 
			buf.append("\n 			 		and substr(GATH_DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(GATH_DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	/**
	 *  사진 이용 엑셀  
	 * @param conditionDO
	 * @return
	 */
	public static String selectPicUsingListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
//			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
//			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		(( ");
			buf.append("\n 			select count(*)  ");
			buf.append("\n 			from das.PHOT_TBL pt  ");
			buf.append("\n 				inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 				inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 				where pit.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and pit.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and pit.CTGR_S_CD = qty.CTGR_S_CD "); 
			buf.append("\n 			and CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2')) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(pt.GATH_DD, 1, 6) AS DAY, ");
			buf.append("\n 		(select count(*) ");
			buf.append("\n 		from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = pit.CTGR_L_CD ");
			buf.append("\n 		and pdt.CTGR_M_CD = pit.CTGR_M_CD ");
			buf.append("\n 		and pdt.CTGR_S_CD = pit.CTGR_S_CD ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '1') ");
			buf.append("\n 		AS SUM_QTY "); 
			buf.append("\n 	from das.PHOT_TBL pt ");
			buf.append("\n 	inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 	inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 	group by pit.CTGR_L_CD, pit.CTGR_M_CD, pit.CTGR_S_CD, substr(pt.GATH_DD, 1, 6)  ");
			buf.append("\n 	) qty ");
			
			
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(gath_DD, 1, 6) AS DAY, ");
			buf.append("\n 		(select count(*) ");
			buf.append("\n 		from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = pit.CTGR_L_CD ");
			buf.append("\n 		and pdt.CTGR_M_CD = pit.CTGR_M_CD ");
			buf.append("\n 		and pdt.CTGR_S_CD = pit.CTGR_S_CD ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '1') ");
			buf.append("\n 		AS SUM_QTY, "); 
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select count(*)  ");
			buf.append("\n 			 			from das.PHOT_TBL pt  ");
			buf.append("\n 			 			inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 			 			inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 			where pit.CTGR_L_CD = ''  ");
			buf.append("\n 			 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2'), 0) AS TOTAL_QTY ");
			buf.append("\n 		  from das.PHOT_TBL pt ");
			buf.append("\n 		  inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 		  inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(GATH_DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' "); 
			buf.append("\n 			 		and substr(GATH_DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(GATH_DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	/**
	 *  사진 폐기 엑셀  
	 * @param conditionDO
	 * @return
	 */
	public static String selectPicDeleteListQuery(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
//			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM, ");
			buf.append("\n 		value(qr.TOTAL_QTY, 0) AS TOTAL_QTY ");
//			buf.append("\n 		value(qr.TOTAL_TM, 0) AS TOTAL_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		(( ");
			buf.append("\n 			select count(*)  ");
			buf.append("\n 			from das.PHOT_TBL pt  ");
			buf.append("\n 				inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 				inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 				where pit.CTGR_L_CD = qty.CTGR_L_CD ");
			buf.append("\n 				and pit.CTGR_M_CD = qty.CTGR_M_CD ");
			buf.append("\n 				and pit.CTGR_S_CD = qty.CTGR_S_CD "); 
			buf.append("\n 			and CTGR_L_CD = qty.CTGR_L_CD and CTGR_M_CD = qty.CTGR_M_CD and CTGR_S_CD = qty.CTGR_S_CD ");
			buf.append("\n 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2')) AS TOTAL_QTY ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(pt.GATH_DD, 1, 6) AS DAY, ");
			buf.append("\n 		(select count(*) ");
			buf.append("\n 		from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = CTGR_L_CD ");
			buf.append("\n 		and pdt.CTGR_M_CD = CTGR_M_CD ");
			buf.append("\n 		and pdt.CTGR_S_CD = CTGR_S_CD ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2') ");
			buf.append("\n 		AS SUM_QTY "); 
			buf.append("\n 	from das.PHOT_TBL pt ");
			buf.append("\n 	inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 	inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 	group by pit.CTGR_L_CD, pit.CTGR_M_CD, pit.CTGR_S_CD, substr(pt.GATH_DD, 1, 6)  ");
			buf.append("\n 	) qty ");
			
			
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n 			union all ");
			buf.append("\n 			( select ' ' AS CTGR_L_CD, ' ' AS CTGR_L_NM, ' ' AS CTGR_M_CD, ' ' AS CTGR_M_NM, ' ' AS CTGR_S_CD, ' ' AS CTGR_S_NM, substr(gath_DD, 1, 6) AS DAY, ");
			buf.append("\n 		(select count(*) ");
			buf.append("\n 		from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = CTGR_L_CD ");
			buf.append("\n 		and pdt.CTGR_M_CD = CTGR_M_CD ");
			buf.append("\n 		and pdt.CTGR_S_CD = CTGR_S_CD ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2') ");
			buf.append("\n 		AS SUM_QTY, "); 
			buf.append("\n 					value(( ");
			buf.append("\n 			 			select count(*)  ");
			buf.append("\n 			 			from das.PHOT_TBL pt  ");
			buf.append("\n 			 			inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 			 			inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 			where pit.CTGR_L_CD = ''  ");
			buf.append("\n 			 		)- (select count(*) ");
			buf.append("\n   from das.PHOT_DOWN_TBL pdt where pdt.CTGR_L_CD = '' ");
			buf.append("\n 		and pdt.REQ_DT >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	    and pdt.REQ_DT <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 		and pdt.CHECK = '2'), 0) AS TOTAL_QTY ");
			buf.append("\n 		  from das.PHOT_TBL pt ");
			buf.append("\n 		  inner join das.PGM_PHOT_INFO_TBL ppit on ppit.PHOT_ID = pt.PHOT_REG_ID ");
			buf.append("\n 		  inner join das.PGM_INFO_TBL pit on pit.pgm_id = ppit.pgm_id ");
			buf.append("\n 			 WHERE CTGR_L_CD = '' ");
			buf.append("\n 			and substr(GATH_DD, 1, 6) >= '"+fromDate.substring(0, 6)+"' "); 
			buf.append("\n 			 		and substr(GATH_DD, 1, 6) <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 			GROUP BY CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(GATH_DD, 1, 6)) ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 영상종합통계 조회
	 * @param conditionDO
	 */
	/*public static String selectSTAT_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select																																													");	
		buf.append("\n  		category.CTGR_L_CD,                                                                       ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n  		WHEN category.CTGR_L_CD is null or  category.CTGR_L_CD = '' THEN ''                       ");
		buf.append("\n  		ELSE (select code.DESC from DAS.CODE_TBL code                                             ");
		buf.append("\n 														where code.CLF_CD = 'P002' and code.SCL_CD = category.CTGR_L_CD )   ");
		buf.append("\n  	END	 AS CTGR_L_NM,                                                                          ");
		buf.append("\n  		category.CTGR_M_CD,                                                                       ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n  		WHEN category.CTGR_M_CD is null or  category.CTGR_M_CD = '' THEN ''                       ");
		buf.append("\n  		ELSE (select code.DESC from DAS.CODE_TBL code                                             ");
		buf.append("\n 														where code.CLF_CD = 'P003' and code.SCL_CD = category.CTGR_M_CD )   ");
		buf.append("\n  	END	 AS CTGR_M_NM,                                                                          ");
		buf.append("\n  		CASE                                                                                      ");
		buf.append("\n  		WHEN category.CTGR_S_CD = 'N' THEN ' '                                                    ");
		buf.append("\n  		ELSE category.CTGR_S_CD                                                                   ");
		buf.append("\n  		END      AS CTGR_S_CD,                                                                    ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n  		WHEN category.CTGR_S_CD is null or  category.CTGR_S_CD = '' THEN ''                       ");
		buf.append("\n  		ELSE (select code.DESC from DAS.CODE_TBL code                                             ");
		buf.append("\n 														where code.CLF_CD = 'P004' and code.SCL_CD = category.CTGR_S_CD )   ");
		buf.append("\n  	END	 AS CTGR_S_NM ,                                                                         ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n 		WHEN meta.count is null then 0                                                              ");
		buf.append("\n 		ELSE meta.count                                                                             ");
		buf.append("\n 	END AS ARRANGE_COUNT,                                                                         ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n 		WHEN meta.duration is null then 0                                                           ");
		buf.append("\n 		else meta.duration                                                                          ");
		buf.append("\n 	end as ARRANGE_DURATION,                                                                      ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n 		WHEN meta2.count is null then 0                                                             ");
		buf.append("\n 		ELSE meta2.count                                                                            ");
		buf.append("\n 	END AS SUM_QTY,                                                                                 ");
		buf.append("\n 	CASE                                                                                          ");
		buf.append("\n 		WHEN meta2.duration is null then 0                                                          ");
		buf.append("\n 		else meta2.duration                                                                         ");
		buf.append("\n 	end as SUM_TM                                                                               ");
		buf.append("\n from                                                                                           ");
		buf.append("\n (		select                                                                                    ");
		buf.append("\n  		large.SCL_CD AS CTGR_L_CD,                                                                ");
		buf.append("\n  		middle.SCL_CD AS CTGR_M_CD,                                                               ");
		buf.append("\n  		CASE                                                                                      ");
		buf.append("\n  		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N'                                  ");
		buf.append("\n  		ELSE small.SCL_CD                                                                         ");
		buf.append("\n  		END      AS CTGR_S_CD                                                                     ");
		buf.append("\n  	from                                                                                        ");
		buf.append("\n  	( select                                                                                    ");
		buf.append("\n  			SCL_CD                                                                                  ");
		buf.append("\n  		from DAS.CODE_TBL                                                                         ");
		buf.append("\n  		where CLF_CD = 'P002'                                                                     ");
		buf.append("\n  	) large                                                                                     ");
		buf.append("\n  	LEFT OUTER JOIN (                                                                           ");
		buf.append("\n  		select                                                                                    ");
		buf.append("\n  			SCL_CD                                                                                  ");
		buf.append("\n  		from DAS.CODE_TBL                                                                         ");
		buf.append("\n  		where CLF_CD = 'P003'                                                                     ");
		buf.append("\n  	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1)                        ");
		buf.append("\n  	LEFT OUTER JOIN (                                                                           ");
		buf.append("\n  		select                                                                                    ");
		buf.append("\n  			SCL_CD                                                                                  ");
		buf.append("\n  		from DAS.CODE_TBL                                                                         ");
		buf.append("\n  		where CLF_CD = 'P004'                                                                     ");
		buf.append("\n  	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2)                         ");
		buf.append("\n  	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD                                          ");
		buf.append("\n ) category                                                                                     ");
		buf.append("\n  left outer join                                                                               ");
		buf.append("\n    (                                                                                           ");
		buf.append("\n 	 select  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, count(1) as COUNT, sum(B.duration) as DURATION      ");
		buf.append("\n          from das.metadat_mst_tbl A,                                                           ");
		buf.append("\n               das.contents_tbl B,                                                              ");
		buf.append("\n               das.contents_mapp_tbl C                                                          ");
		buf.append("\n          where A.master_id = C.master_id                                                       ");
		buf.append("\n                AND B.ct_id = C.ct_id                                                           ");
		buf.append("\n                AND A.arrg_end_dt <> ''                                                         ");
		buf.append("\n          group by  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd                                             ");
		buf.append("\n 				 ) meta on meta.ctgr_l_cd = category.ctgr_l_cd                                          ");
		buf.append("\n 				 					and  meta.ctgr_m_cd =category.ctgr_m_cd                                       ");
		buf.append("\n 									and meta.ctgr_s_cd = category.ctgr_s_cd                                       ");
		buf.append("\n  LEFT OUTER JOIN                                                                               ");
		buf.append("\n  (                                                                                             ");
		buf.append("\n 	 select  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, count(1) as COUNT, sum(B.duration) as DURATION      ");
		buf.append("\n          from das.metadat_mst_tbl A,                                                           ");
		buf.append("\n               das.contents_tbl B,                                                              ");
		buf.append("\n               das.contents_mapp_tbl C                                                          ");
		buf.append("\n          where A.master_id = C.master_id                                                       ");
		buf.append("\n                AND B.ct_id = C.ct_id                                                           ");
		buf.append("\n           group by  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd                                            ");
		buf.append("\n 				 ) meta2 on meta2.ctgr_l_cd = category.ctgr_l_cd                                        ");
		buf.append("\n 				 					and  meta2.ctgr_m_cd =category.ctgr_m_cd                                      ");
		buf.append("\n 									and meta2.ctgr_s_cd = category.ctgr_s_cd                                      ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}*/
	public static String selectSTAT_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select																																													");	
		buf.append("\n   VALUE(CODE.DESC,'') as ctgr_l_nm                                                                      ");
		buf.append("\n 	 ,VALUE(mst.ctgr_l_cd,'') as ctgr_l_cd                                                                        ");
		buf.append("\n   ,VALUE(CODE2.DESC,'') as ctgr_m_nm                    ");
		buf.append("\n   ,VALUE(mst.ctgr_m_cd,'') as ctgr_m_cd                                            ");
		buf.append("\n 	  ,VALUE(CODE3.DESC,'') as ctgr_s_nm  ");
		buf.append("\n   ,VALUE(mst.ctgr_s_cd,'') as ctgr_s_cd                                                                           ");
		buf.append("\n 	,CASE                                                                                          ");
		buf.append("\n 		WHEN  meta.COUNT is null then 0                                                              ");
		buf.append("\n 		ELSE  meta.COUNT                                                                            ");
		buf.append("\n 	END AS ARRANGE_COUNT                                                                        ");
		buf.append("\n 	,CASE                                                                                          ");
		buf.append("\n 		WHEN bigint(meta.DURATION*29.97)  is null then 0                                                           ");
		buf.append("\n 		else bigint(meta.DURATION*29.97)                                                                            ");
		buf.append("\n 	end as ARRANGE_DURATION                                                                      ");
		buf.append("\n 	,CASE                                                                                          ");
		buf.append("\n 		WHEN meta2.COUNT is null then 0                                                             ");
		buf.append("\n 		ELSE meta2.COUNT                                                                            ");
		buf.append("\n 	END AS SUM_QTY                                                                                 ");
		buf.append("\n 	,CASE                                                                                          ");
		buf.append("\n 		WHEN bigint(meta2.DURATION*29.97) is null then 0                                                          ");
		buf.append("\n 		else bigint(meta2.DURATION*29.97)                                                                           ");
		buf.append("\n 	end as SUM_TM                                                                               ");
		buf.append("\n from                                                                                           ");
		buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''   ) mst              ");
		buf.append("\n  left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD                                                              ");
		buf.append("\n  LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD                                        ");
		buf.append("\n  LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD                               ");
		buf.append("\n  left outer join                                                                               ");
		buf.append("\n    (                                                                                           ");
		buf.append("\n 	 select  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, count(1) as COUNT, sum(bigint(SUBSTR( a.brd_leng,1,2))*60*60+bigint(substr( a.brd_leng,4,2))*60+bigint(substr( a.brd_leng,7,2)) +bigint(substr(a.brd_leng,10,2)/29.97)) as DURATION      ");
		buf.append("\n          from das.metadat_mst_tbl A                                                           ");
	                                           
		//buf.append("\n               where  A.arrg_end_dt = ''     and (a.del_dd is null or a.DEL_DD ='')                                                     ");
		//2012.2.6 수정 정리완료기준이아닌 데이터 상태값 기준으로 수정
		buf.append("\n               where  ( A.DATA_STAT_CD = '001' or A.DATA_STAT_CD = '002'      or A.DATA_STAT_CD = '010'  or A.DATA_STAT_CD = '000' )    and (a.del_dd is null or a.DEL_DD ='') and value(a.arch_reg_dd,'')<>''                                         ");
		
		
		//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
		
		/** 아카이브 경로*/
		
	 	if(!conditionDO.getSource_gubun().equals("")){
	String[] path = conditionDO.getSource_gubun().split(",");
	buf.append("\n 	and  ( ");
		for(int i=0;i<path.length;i++){
			if(path[i].equals("D"))
			{
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	(A.arch_route LIKE 'D%') ");
			}
			else if(path[i].equals("O"))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  A.arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");
				
			}else if(path[i].equals("P")){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( A.arch_route ='P' )");
		
			}
		}
		buf.append("\n 	 ) ");
	 	}
	 	//2012.4.24 조회조건 추가
	 	if(!conditionDO.getCocd().equals("")){
	 		
		 	buf.append("\n  AND A.cocd like  '" + conditionDO.getCocd()+"%'");

		 	
		 	}
	 	if(!conditionDO.getChennel().equals("")){
	 		
		 	buf.append("\n   AND A.chennel_cd like  '" + conditionDO.getChennel()+"%'");

		 	
		 	}
		
		
		buf.append("\n          group by  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd                                             ");
		buf.append("\n 				 ) meta on meta.ctgr_l_cd = mst.ctgr_l_cd                                          ");
		buf.append("\n 				 					and  meta.ctgr_m_cd =mst.ctgr_m_cd                                       ");
		buf.append("\n 									and meta.ctgr_s_cd = mst.ctgr_s_cd                                       ");
		buf.append("\n  LEFT OUTER JOIN                                                                               ");
		buf.append("\n  (                                                                                             ");
		buf.append("\n 	 select  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd, count(1) as COUNT, sum(bigint(SUBSTR( a.brd_leng,1,2))*60*60+bigint(substr( a.brd_leng,4,2))*60+bigint(substr( a.brd_leng,7,2)) +bigint(substr(a.brd_leng,10,2)/29.97)) as DURATION      ");
		buf.append("\n          from das.metadat_mst_tbl a                                                           ");
		buf.append("\n          where (a.del_dd is null or a.DEL_DD ='')                                                    ");
	//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
		
		/** 아카이브 경로*/
		
	 	if(!conditionDO.getSource_gubun().equals("")){
	String[] path = conditionDO.getSource_gubun().split(",");
	buf.append("\n 	and  ( ");
		for(int i=0;i<path.length;i++){
			if(path[i].equals("D"))
			{
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	(A.arch_route LIKE 'D%') ");
			}
			else if(path[i].equals("O"))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  A.arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");
				
			}else if(path[i].equals("P")){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( A.arch_route ='P' )");
		
			}
		}
		buf.append("\n 	 ) ");
	 	}
	 	//2012.4.24 조회조건 추가
	 	if(!conditionDO.getCocd().equals("")){
	 		
		 	buf.append("\n  AND A.cocd like  '" + conditionDO.getCocd()+"%'");

		 	
		 	}
	 	if(!conditionDO.getChennel().equals("")){
	 		
		 	buf.append("\n   AND A.chennel_cd like  '" + conditionDO.getChennel()+"%'");

		 	
		 	}
	 	//20130104 통계확인요청
	 	buf.append("\n   AND A.ARCH_REG_DD <> ''"); 
	 	
		buf.append("\n           group by  ctgr_l_cd, ctgr_m_cd, ctgr_s_cd                                            ");
		buf.append("\n 				 ) meta2 on meta2.ctgr_l_cd = mst.ctgr_l_cd                                        ");
		buf.append("\n 				 					and  meta2.ctgr_m_cd =mst.ctgr_m_cd                                      ");
		buf.append("\n 									and meta2.ctgr_s_cd = mst.ctgr_s_cd                                      ");
		buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC,meta.COUNT ,  meta.DURATION , meta2.COUNT ,  meta2.DURATION  	 ");
		buf.append("\n order by  mst.ctgr_l_cd asc,  mst.ctgr_m_cd asc,  mst.ctgr_s_cd asc  	 ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 수집통계 조회
	 * @param conditionDO
	 */
	/*public static String selectSTAT_COL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 and arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}*/
	
	public static String selectSTAT_COL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''   ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			buf.append("\n 	WHERE 1=1 ");
			//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_COL_TBL ");
			buf.append("\n where DD >= '"+fromDate+"' ");
			buf.append("\n 	and DD <= '"+toDate+"' ");
			
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
		
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n 	 qty ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,DAY ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 폐기통계 조회
	 * @param conditionDO
	 */
/*	public static String selectSTAT_DISUSE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 and arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}*/
	public static String selectSTAT_DISUSE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>'' AND ARCH_REG_DD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where(del_dd='' or del_dd is null) AND CTGR_L_CD<>''   ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_DISUSE_TBL ");
			buf.append("\n where DD >= '"+fromDate+"' ");
			buf.append("\n 	and DD <= '"+toDate+"' ");
			
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n 	qty ");
		
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 정리통계 조회
	 * @param conditionDO
	 */
	/*public static String selectSTAT_ARRA_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
		
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	 where  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			if(!conditionDO.getSource_gubun().equals("")){
				buf.append("\n 	and  arch_route like '"+conditionDO.getSource_gubun()+"%' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}*/
	public static String selectSTAT_ARRA_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''   ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 AND  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 AND  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 AND  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_ARRA_TBL ");
			buf.append("\n where DD >= '"+fromDate+"' ");
			buf.append("\n 	and DD <= '"+toDate+"' ");
			
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n 	 qty ");
		
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 장르별 이용통계
	 * @param conditionDO
	 */
	/*public static String selectSTAT_GNR_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}*/
	public static String selectSTAT_GNR_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''   ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n 	WHERE 1=1 ");
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,qr.DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		 mst.CTGR_L_CD,    ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "mst.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		mst.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "mst.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN mst.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE mst.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "mst.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n (select value(ctgr_l_cd,'') as ctgr_l_cd,value(CTGR_M_CD,'') as CTGR_M_CD,value(CTGR_S_CD,'') as CTGR_S_CD from metadat_mst_Tbl where (del_dd='' or del_dd is null) AND CTGR_L_CD<>''  ) mst              ");
			buf.append("\n 	left outer join code_tbl code on code.CLF_CD='P002' AND MST.CTGR_L_CD=CODE.SCL_CD    ");
			buf.append("\n 	LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD='P003' AND MST.CTGR_M_CD=CODE2.SCL_CD    ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE3 ON CODE3.CLF_CD='P004' AND MST.CTGR_S_CD=CODE3.SCL_CD     ");
			
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE_TBL ");
			buf.append("\n where DD >= '"+fromDate+"' ");
			buf.append("\n 	and DD <= '"+toDate+"' ");
			
//2012.4.25 회사,채널, 아카이브경로별 조회부분 추가
			
			/** 아카이브 경로*/
			
		 	if(!conditionDO.getSource_gubun().equals("")){
		String[] path = conditionDO.getSource_gubun().split(",");
		buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");
					
				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");
			
				}
			}
			buf.append("\n 	 ) ");
		 	}
		 	//2012.4.24 조회조건 추가
		 	if(!conditionDO.getCocd().equals("")){
		 		
			 	buf.append("\n  AND cocd like  '" + conditionDO.getCocd()+"%'");

			 	
			 	}
		 	if(!conditionDO.getChennel().equals("")){
		 		
			 	buf.append("\n   AND chennel like  '" + conditionDO.getChennel()+"%'");

			 	
			 	}
			if(!conditionDO.getCtgr_l_cd().equals("")){
				buf.append("\n 	 and  ctgr_l_cd= '"+conditionDO.getCtgr_l_cd()+"' ");
				}
			if(!conditionDO.getCtgr_m_cd().equals("")){
				buf.append("\n 	 and  ctgr_m_cd= '"+conditionDO.getCtgr_m_cd()+"' ");
				}
			if(!conditionDO.getCtgr_s_cd().equals("")){
				buf.append("\n 	 and  ctgr_s_cd= '"+conditionDO.getCtgr_s_cd()+"' ");
				}
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD) ");
			buf.append("\n 	 qty ");
		
			buf.append("\n ) qr ON MST.CTGR_L_CD = qr.CTGR_L_CD and MST.CTGR_M_CD = qr.CTGR_M_CD and MST.CTGR_S_CD = qr.CTGR_S_CD  ");
			buf.append("\n  group by mst.ctgr_l_cd, mst.ctgr_m_cd, mst.ctgr_s_cd,CODE.DESC,CODE2.DESC,CODE3.DESC ,qr.SUM_QTY ,qr.SUM_TM,DAY  ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
		}
		//buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 프로그램별 이용통계
	 * @param conditionDO
	 */
	public static String selectSTAT_GNR_USE2_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(master_id, 0) AS master_id, ");
			buf.append("\n 		value(pgm_id, 0) AS pgm_id, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN '' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n      qty.pgm_id, ");
			buf.append("\n      qty.master_id, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		pgm_Id, ");
			buf.append("\n 		master_id, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE2_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD,pgm_id,master_id , substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(master_id, 0) AS master_id, ");
			buf.append("\n 		value(pgm_id, 0) AS pgm_id, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN '' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n      qty.pgm_id, ");
			buf.append("\n      qty.master_id, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		pgm_Id, ");
			buf.append("\n 		master_id, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE2_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD,pgm_id,master_id ,substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(master_id, 0) AS master_id, ");
			buf.append("\n 		value(pgm_id, 0) AS pgm_id, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN '' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN '' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n      qty.pgm_id, ");
			buf.append("\n      qty.master_id, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		pgm_Id, ");
			buf.append("\n 		master_id, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_USE2_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, pgm_id,master_id ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 부서별 이용통계
	 * @param statisticsConditionDO
	 *//*
	public static String selectSTAT_DEPT_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                               ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{

			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                          ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                 ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		'00000000' AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		return buf.toString();
	}*/
	
	/**
	 * 부서별 이용통계
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_DEPT_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면 
		
		
		System.out.println("ddddddd");
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n select      distinct                                                                                              ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_dept_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD,  DEP.SUP_HEAD_CD,DEP.SUP_dept_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_dept_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
			
			buf.append("\n  ) category                                                                                               ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			if(!conditionDO.getCocd().equals("")){
				if(conditionDO.getCocd().equals("S")){
				buf.append("\n 	and qty.GRP= 'S' ");
				}else{
				buf.append("\n 	and qty.GRP<> 'S' ");	
				}
			}
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_dept_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{

			buf.append("\n select         distinct                                                                                           ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_DEPT_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD,  DEP.SUP_HEAD_CD,DEP.SUP_DEPT_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_DEPT_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
			
			buf.append("\n  ) category                                                                                                          ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			if(!conditionDO.getCocd().equals("")){
				if(conditionDO.getCocd().equals("S")){
				buf.append("\n 	and qty.GRP= 'S' ");
				}else{
				buf.append("\n 	and qty.GRP<> 'S' ");	
				}
			}
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_DEPT_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select        distinct                                                                                            ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_DEPT_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_DEPT_CD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_DEPT_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
				
			buf.append("\n  ) category                                                                                                                 ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.dd AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			
			buf.append("\n  		'00000000' as dd,                                                                         ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			if(!conditionDO.getCocd().equals("")){
				if(conditionDO.getCocd().equals("S")){
				buf.append("\n 	and GRP= 'S' ");
				}else{
				buf.append("\n 	and GRP<> 'S' ");	
				}
			}
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_DEPT_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		return buf.toString();
	}
	
	
	/**
	 * 부서별 아카이브 통계 PART1(미등록)
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_DEPT_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{

			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                  ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                        ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		'00000000' AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		
		return buf.toString();
	}
	/**
	 * 부서별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_DEPT_ARCH_DTL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                   ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_DTL_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{

			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                               ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_DTL_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6)                                            ");
			buf.append("\n  	) qty                                                                                                  ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                             ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		'00000000' AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_DTL_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
	
		return buf.toString();
	}
	/**
	 * 부서별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_DEPT_ARCH_REQ_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                  ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_REQ_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{

			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                                ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_REQ_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select                                                                                                    ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_HEAD_CD ,category.SUP_HEAD_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_HEAD_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  ) category                                                                                                               ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		'00000000' AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(BIGINT(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_DEPT_ARCH_REQ_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4)                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_HEAD_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_HEAD_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
	
		return buf.toString();
	}
	
	
	
	/**
	 * 프로그램별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws RemoteException
	 */
	public static String getSTAT_PGM_ARCH_DTL_TBL_List(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
	
			buf.append("\n select DISTINCT                                                                                             ");
			buf.append("\n   PGM.PROGRAM_NAME,                                   ");
			buf.append("\n 	 CODE.DESC,                                                                                 ");
			buf.append("\n 	 code.SCL_CD ,                                                                              ");
			buf.append("\n   CASE WHEN stat.CT_CLA=code.SCL_CD THEN SUM(stat.BY_DY_QTY) ELSE 0                                                                     ");
			buf.append("\n   END AS BY_DY_QTY,                                                                 ");
			buf.append("\n   CASE WHEN stat.CT_CLA=code.SCL_CD THEN SUM(stat.BY_DY_TM) ELSE 0                                                                ");
			buf.append("\n   END AS BY_DY_TM                                                                                                  ");
			buf.append("\n   from STAT_PGM_DTL_TBL stat                                                                                                   ");
			buf.append("\n   INNER JOIN (SELECT PROGRAM_CODE,PROGRAM_NAME FROM PDS_PGMINFO_TBL2 GROUP BY PROGRAM_CODE ,PROGRAM_NAME) PGM ON PGM.PROGRAM_CODE = STAT.PDS_PGM_ID                                                                                     ");
			buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE ON  CODE.CLF_CD ='A001' AND CODE.USE_YN='Y'                                                      ");
			buf.append("\n 	 WHERE STAT.DD >= '"+fromDate+"'               ");
			buf.append("\n 	 AND STAT.DD <= '"+toDate+"'               ");
			if(!conditionDO.getPgm_nm().equals("")){
			buf.append("\n 	 AND PGM.PROGRAM_NAME LIKE  '%"+conditionDO.getPgm_nm()+"%'               ");
			}
			buf.append("\n 	 group by PGM.PROGRAM_NAME,CODE.DESC,code.SCL_CD,stat.CT_CLA        ");
			buf.append("\n   ORDER BY PGM.PROGRAM_NAME,CODE.SCL_CD, BY_DY_QTY DESC                                                                                                ");
			
		
		return buf.toString();
	}
	/**
	 * 프로그램별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws RemoteException
	 */
	public static String getSTAT_PGM_ARCH_REQ_TBL_List(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();

		buf.append("\n select DISTINCT                                                                                             ");
		buf.append("\n   PGM.PROGRAM_NAME,                                   ");
		buf.append("\n 	 CODE.DESC,                                                                                 ");
		buf.append("\n 	 code.SCL_CD ,                                                                              ");
		buf.append("\n   CASE WHEN stat.CT_CLA=code.SCL_CD THEN SUM(stat.BY_DY_QTY) ELSE 0                                                                     ");
		buf.append("\n   END AS BY_DY_QTY,                                                                 ");
		buf.append("\n   CASE WHEN stat.CT_CLA=code.SCL_CD THEN SUM(stat.BY_DY_TM) ELSE 0                                                                ");
		buf.append("\n   END AS BY_DY_TM                                                                                                  ");
		buf.append("\n   from STAT_PGM_ARCH_TBL stat                                                                                                   ");
		buf.append("\n   INNER JOIN (SELECT PROGRAM_CODE,PROGRAM_NAME FROM PDS_PGMINFO_TBL2 GROUP BY PROGRAM_CODE ,PROGRAM_NAME) PGM ON PGM.PROGRAM_CODE = STAT.PDS_PGM_ID                                                                                     ");
		buf.append("\n 	 LEFT OUTER JOIN CODE_TBL CODE ON  CODE.CLF_CD ='A001' AND CODE.USE_YN='Y'                                                      ");
		buf.append("\n 	 WHERE STAT.DD >= '"+fromDate+"'               ");
		buf.append("\n 	 AND STAT.DD <= '"+toDate+"'               ");
		if(!conditionDO.getPgm_nm().equals("")){
		buf.append("\n 	 AND PGM.PROGRAM_NAME LIKE  '%"+conditionDO.getPgm_nm()+"%'               ");
			}
		if(!conditionDO.getCt_cla().equals("")){
			buf.append("\n 	 AND stat.CT_CLA =  '"+conditionDO.getCt_cla()+"'               ");
				}
		buf.append("\n 	 group by PGM.PROGRAM_NAME,CODE.DESC,code.SCL_CD,stat.CT_CLA        ");
		buf.append("\n   ORDER BY PGM.PROGRAM_NAME,CODE.SCL_CD, BY_DY_QTY DESC                                                                                                ");
		
		return buf.toString();
	}
	
	
	/**
	 * 프로그램별 이용통계 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PGM_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		pgm_id,  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BIGINT(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_USE_TBL ");
			buf.append("\n 	group by PGM_ID, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		PGM_ID,  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BIGINT(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_USE_TBL ");
			buf.append("\n 	group by PGM_ID, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		PGM_ID,  ");
			buf.append("\n 		'' AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BIGINT(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_USE_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by PGM_ID ");
			buf.append("\n 	) qty ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 사진 종합통계
	 * @param conditionDO
	 */
	public static String selectSTAT_PHOT_COL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		pit.PGM_NM, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_COL_TBL pct");
			buf.append("\n          inner join das.pgm_info_tbl pit on pit.pgm_id=pct.pgm_id ");
			buf.append("\n      where pit.pgm_nm like '%"+ conditionDO.getPgm_nm()+"%' ");
			buf.append("\n      and pit.del_yn <> 'Y' ");
			buf.append("\n 	group by pit.PGM_NM ");
			buf.append("\n 	) qty ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 사진 등록 통계 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PHOT_REG_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_REG_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_REG_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_REG_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
//			buf.append("\n 	where DD >= '"+fromDate+"' ");
//			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 사진 폐기 통계 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PHOT_DISUSE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_DISUSE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_DISUSE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_DISUSE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 사진 누적량 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PHOT_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
			buf.append("\n SELECT COUNT(*) AS TOTAL FROM (  ");
			buf.append("\n SELECT PHOT_ID FROM PGM_PHOT_INFO_TBL WHERE del_yn <> 'Y' ) Z ");
		
		return buf.toString();
	}
	
	/**
	 * 프로그램별 사진 보유량 통계
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PHOT_PGM_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
			buf.append("\n SELECT  COUNT(PPIT.PHOT_ID) AS TOTAL ,MMT.TITLE AS PGM_NM ");
			buf.append("\n FROM DAS.PGM_PHOT_INFO_TBL PPIT ");
			buf.append("\n INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = PPIT.MASTER_ID ");
			buf.append("\n WHERE MMT.TITLE LIKE '%"+conditionDO.getPgm_nm()+"%' ");
			buf.append("\n AND PPIT.DEL_YN <>'Y' ");
			buf.append("\n GROUP BY MMT.TITLE ");
			
		return buf.toString();
	}
	/**
	 * 사진 이용 통계 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PHOT_USE_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_USE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_USE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY ");
			buf.append("\n 	from DAS.STAT_PHOT_USE_TBL ");
			buf.append("\n 	group by  substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 장르별 아카이브 통계 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_GNR_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}  

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
		//	buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_ARCH_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD,  ");
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
//			buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
			//buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		qty.DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_ARCH_TBL ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, DAY ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select ");
			buf.append("\n 		category.CTGR_L_CD, "); 
			String ctLCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY, "category.CTGR_L_CD");
			buf.append(ctLCoCodeName + " AS CTGR_L_NM, ");
			buf.append("\n 		category.CTGR_M_CD,  ");
			String ctMCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY, "category.CTGR_M_CD");
			buf.append(ctMCoCodeName + " AS CTGR_M_NM, ");
			//buf.append("\n 		category.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN category.CTGR_S_CD = 'N' THEN ' ' ");
			buf.append("\n 		ELSE category.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");	
			String ctSCoCodeName = CodeCommon.getCodeQueryMake2(CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY, "category.CTGR_S_CD");
			buf.append(ctSCoCodeName + " AS CTGR_S_NM, ");
			buf.append("\n 		value(qr.DAY, '0000') AS DAY, ");
			buf.append("\n 		value(qr.SUM_QTY, 0) AS SUM_QTY, ");
			buf.append("\n 		value(qr.SUM_TM, 0) AS SUM_TM ");
			
			buf.append("\n from  ");
			buf.append("\n ( ");
			buf.append("\n 	select ");
			buf.append("\n 		large.SCL_CD AS CTGR_L_CD,  ");
			buf.append("\n 		middle.SCL_CD AS CTGR_M_CD,  ");
			//buf.append("\n 		small.SCL_CD AS CTGR_S_CD ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE small.SCL_CD ");
			buf.append("\n 		END      AS CTGR_S_CD ");
			buf.append("\n 	from ");
			buf.append("\n 	( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_LARGE_CATEGORY+"' ");
			buf.append("\n 	) large ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_MEDIUM_CATEGORY+"' ");
			buf.append("\n 	) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1) ");
			buf.append("\n 	LEFT OUTER JOIN ( ");
			buf.append("\n 		select  ");
			buf.append("\n 			SCL_CD ");
			buf.append("\n 		from DAS.CODE_TBL ");
			buf.append("\n 		where CLF_CD = '"+CodeConstants.CodeGroup.CLF_CD_SMALL_CATEGORY+"' ");
			buf.append("\n 	)	small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2) ");
			buf.append("\n 	group by large.SCL_CD, middle.SCL_CD, small.SCL_CD ");
			buf.append("\n ) category  ");
			buf.append("\n LEFT OUTER JOIN ( ");
			buf.append("\n 	select ");
			buf.append("\n 		qty.CTGR_L_CD,  ");
			buf.append("\n 		qty.CTGR_M_CD,  ");
		//	buf.append("\n 		qty.CTGR_S_CD, ");
			buf.append("\n 		CASE ");
			buf.append("\n 		WHEN qty.CTGR_S_CD is null or  qty.CTGR_S_CD = '' THEN 'N' ");
			buf.append("\n 		ELSE qty.CTGR_S_CD ");
			buf.append("\n 		END      AS CTGR_S_CD, ");
			buf.append("\n 		'00000000' AS DAY, ");
			buf.append("\n 		qty.SUM_QTY, ");
			buf.append("\n 		qty.SUM_TM ");
			
			buf.append("\n 	from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		CTGR_L_CD,  ");
			buf.append("\n 		CTGR_M_CD,  ");
			buf.append("\n 		CTGR_S_CD, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_GNR_ARCH_TBL ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			buf.append("\n 	group by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			buf.append("\n 	) qty ");
			buf.append("\n ) qr ON category.CTGR_L_CD = qr.CTGR_L_CD and category.CTGR_M_CD = qr.CTGR_M_CD and category.CTGR_S_CD = qr.CTGR_S_CD ");
			
			buf.append("\n order by CTGR_L_CD, CTGR_M_CD, CTGR_S_CD ");
			
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 프로그램별 아카이브 조회
	 * @param statisticsConditionDO
	 */
	public static String selectSTAT_PGM_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO)
	{
		String fromDate = conditionDO.getFromDate();
		String toDate = conditionDO.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(conditionDO.getDateKind()))
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		pgm_id,  ");
			buf.append("\n 		substr(DD, 1, 4) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(BIGINT(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_ARCH_TBL ");
 			buf.append("\n  where DD >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and DD <= '"+toDate.substring(0, 4)+"' ");
			buf.append("\n 	group by PGM_ID, substr(DD, 1, 4) ");
			buf.append("\n 	) qty ");
			
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(conditionDO.getDateKind()))
		{
			
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		PGM_ID,  ");
			buf.append("\n 		substr(DD, 1, 6) AS DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_ARCH_TBL ");
			buf.append("\n 	group by PGM_ID, substr(DD, 1, 6) ");
			buf.append("\n 	) qty ");
			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n  select * from ");
			buf.append("\n 	(select  ");
			buf.append("\n 		PGM_ID,  ");
			buf.append("\n      '' as DAY, ");
			buf.append("\n 		sum(BY_DY_QTY) AS SUM_QTY, ");
			buf.append("\n 		sum(INTEGER(BY_DY_TM)) AS SUM_TM ");
			buf.append("\n 	from DAS.STAT_PGM_ARCH_TBL ");
			buf.append("\n where DD >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and DD <= '"+toDate.substring(0, 6)+"' ");
			buf.append("\n 	group by PGM_ID ");
			buf.append("\n 	) qty ");
		}
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	
	
	/**
	 * 컨텐츠 소유권자별 다운로드 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public static String selectSTAT_DOWN_COCD_USE_TBL_List(StatisticsInfo info)
	{
		String fromDate = info.getFromDate();
		String toDate = info.getToDate();
		if(StringUtils.isEmpty(fromDate))
		{
			fromDate = "00000000";
		}
		if(StringUtils.isEmpty(toDate))
		{
			toDate = "99999999";
		}

		StringBuffer buf = new StringBuffer();
		//조회 날짜 구분이 연별이면 
		
		if(DASBusinessConstants.StatisticsDateKind.YEAR.equals(info.getDateKind()))
		{
			
			buf.append("\n select      distinct                                                                                              ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_dept_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD,  DEP.SUP_HEAD_CD,DEP.SUP_dept_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_dept_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
			
			buf.append("\n  ) category                                                                                               ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM ,                                                                                          ");
			buf.append("\n  		qty.COCD ,                                                                                          ");
			buf.append("\n  		qty.CHENNEL                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		COCD,                                                                                                ");
			buf.append("\n  		CHENNEL,                                                                                                ");
			buf.append("\n  		substr(DD, 1, 4) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_PGM_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 4),SDAT.COCD,SDAT.CHENNEL                                          ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 4)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 4)+"' ");
			if(!StringUtils.isEmpty(info.getCocd())){
				if(info.getCocd().equals("S")){
				buf.append("\n 	and qty.COCD= 'S' ");
				}else{
				buf.append("\n 	and qty.COCD<> 'S' ");	
				}
				if(!StringUtils.isEmpty(info.getChennel())){
					buf.append("\n 	and qty.chennel = '" + info.getChennel()+"'");	
				}
			}
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_dept_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 월별이면
		else if(DASBusinessConstants.StatisticsDateKind.MONTH.equals(info.getDateKind()))
		{

			buf.append("\n select         distinct                                                                                           ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_DEPT_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD,  DEP.SUP_HEAD_CD,DEP.SUP_DEPT_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_DEPT_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
			
			buf.append("\n  ) category                                                                                                          ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.DAY,                                                                                             ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM,                                                                                           ");
			buf.append("\n  		qty.COCD,                                                                                         ");
			buf.append("\n  		qty.CHENNEL                                                                                           ");
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		COCD,                                                                                                 ");
			buf.append("\n  		CHENNEL,                                                                                                ");
			
			buf.append("\n  		substr(DD, 1, 6) AS DAY,                                                                             ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_PGM_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT, substr(SDAT.DD, 1, 6),SDAT.COCD,SDAT.CHENNEL                                          ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  	                                                                                                       ");

			buf.append("\n where qty.DAY >= '"+fromDate.substring(0, 6)+"' ");
			buf.append("\n 	and qty.DAY <= '"+toDate.substring(0, 6)+"' ");
			if(!StringUtils.isEmpty(info.getCocd())){
				if(info.getCocd().equals("S")){
				buf.append("\n 	and qty.COCD= 'S' ");
				}else{
				buf.append("\n 	and qty.COCD<> 'S' ");	
				}
				if(!StringUtils.isEmpty(info.getChennel())){
					buf.append("\n 	and qty.chennel ='" + info.getChennel()+"'");	
				}
			}
			buf.append("\n  	                                                                                                       ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_DEPT_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		//조회 날짜 구분이 기간별이면
		else
		{
			buf.append("\n select        distinct                                                                                            ");
			buf.append("\n     category.COCD        ,    category.desc AS CONM                                                                                          ");
			buf.append("\n 		,category.SUP_DEPT_CD ,category.SUP_DEPT_NM                                                                                 ");
			buf.append("\n 		,category.DEPT_CD,  category.DEPT_NM,                                                                               ");
			buf.append("\n  		value(qr.DAY, '0000') AS DAY,                                                                        ");
			buf.append("\n  		value(qr.SUM_QTY, 0) AS SUM_QTY,                                                                     ");
			buf.append("\n  		value(qr.SUM_TM, 0) AS SUM_TM                                                                        ");
			buf.append("\n  from                                                                                                     ");
			buf.append("\n  (                                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n 	DEP.COCD, DEP.SUP_DEPT_CD, DEP.SUP_HEAD_CD ,DEP.DEPT_CD ,DEP.DEPT_NM,DEP.SUP_DEPT_NM  ,code.DESC                                                                  ");
			buf.append("\n 	from dep_info_tbl DEP      ,code_tbl code      where code.scl_cd=dep.cocd   and code.CLF_CD='P058'         ");
			buf.append("\n  and VALUE(code.DESC,'')<>''                                                                                             ");
				
			buf.append("\n  ) category                                                                                                                 ");
			buf.append("\n  LEFT OUTER JOIN (                                                                                        ");
			buf.append("\n  	select                                                                                                 ");
			buf.append("\n  		qty.GRP,                                                                                             ");
			buf.append("\n  		qty.SEG,                                                                                             ");
			buf.append("\n  		qty.DEPT,                                                                                            ");
			buf.append("\n  		qty.dd AS DAY,                                                                                            ");
			buf.append("\n  		qty.SUM_QTY,                                                                                         ");
			buf.append("\n  		qty.SUM_TM,                                                                                          ");
			buf.append("\n  		qty.COCD,                                                                                         ");
			buf.append("\n  		qty.CHENNEL                                                                                           ");
			
			buf.append("\n  	from                                                                                                   ");
			buf.append("\n  	(select                                                                                                ");
			buf.append("\n  		GRP,                                                                                                 ");
			buf.append("\n  		SEG,                                                                                                 ");
			buf.append("\n  		DEPT,                                                                                                ");
			buf.append("\n  		sum(BY_DY_QTY) AS SUM_QTY,                                                                           ");
			buf.append("\n  		COCD,                                                                                                 ");
			buf.append("\n  		CHENNEL,                                                                                                 ");
			
			buf.append("\n  		'00000000' as dd,                                                                         ");
			buf.append("\n  		sum(INTEGER(BY_DY_TM)) AS SUM_TM                                                                     ");
			buf.append("\n  	from DAS.STAT_PGM_DEPT_USE_TBL SDAT                                                                       ");
			buf.append("\n 	where DD >= '"+fromDate+"' ");
			buf.append("\n 		and DD <= '"+toDate+"' ");
			if(!StringUtils.isEmpty(info.getCocd())){
				if(info.getCocd().equals("S")){
				buf.append("\n 	and COCD= 'S' ");
				}else{
				buf.append("\n 	and COCD<> 'S' ");	
				}
				if(!StringUtils.isEmpty(info.getChennel())){
					buf.append("\n 	and chennel ='" + info.getChennel()+"'");	
				}
			}
			buf.append("\n  	group by SDAT.GRP,SDAT.SEG,SDAT.DEPT,SDAT.COCD,SDAT.CHENNEL                                            ");
			buf.append("\n  	) qty                                                                                                  ");
			buf.append("\n  ) qr ON category.COCD = qr.GRP and category.SUP_head_CD = qr.SEG and category.DEPT_CD = qr.DEPT      ");
			buf.append("\n  order by COCD, SUP_DEPT_CD, DEPT_CD, DAY                                                             ");
			buf.append("\n  WITH UR                                                                                                  ");
			
		}
		return buf.toString();
	}
	
}

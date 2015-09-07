package com.app.das.business.dao.statement;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.util.CodeCommon;

/**
 * 통합 검색의 내목록 및 요청영상 목록에 대한 SQL Query가 정의되어 있다.
 * @author ysk523
 *
 */
public class SearchStatement 
{
	
	
	/**
	 * 내목록 조회를 한다.
	 * @param searchFlag 조건
	 * @param commonDO 공통정보
	 */
	public static String selectMyCatalogListQuery(DASCommonDO commonDO, String searchFlag) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 	count(1)  ");
		}
		else
		{
			buf.append("\n 	my.REQ_USRID,  ");
			buf.append("\n 	my.SEQ,  ");
			buf.append("\n 	my.MASTER_ID, "); 
			buf.append("\n 	my.CN_ID,  ");
			buf.append("\n 	my.PGM_ID,  ");
			buf.append("\n 	my.PGM_NM,  ");
			buf.append("\n 	value(my.epn, '0') as epn,  ");
			buf.append("\n 	my.TITLE,  ");
			buf.append("\n 	my.BRD_DD,  ");
			buf.append("\n 	my.BRD_LENG,  ");
			buf.append("\n 	my.CN_LENG,  ");
			buf.append("\n 	my.ANNOT_CLF_CD,  ");
			buf.append("\n 	my.CONT,  ");
			buf.append("\n 	my.RPIMG_KFRM_SEQ,  ");
			buf.append("\n 	my.GOOD_SC,  ");
			buf.append("\n 	my.NOT_USE,  ");
			buf.append("\n 	my.DILBRT,  ");
			buf.append("\n 	my.CHECK,  ");
			buf.append("\n 	my.KFRM_PATH,  ");
			buf.append("\n 	my.KFRM_SEQ,  ");
			buf.append("\n 	my.RPIMG_CT_ID,  ");
			buf.append("\n 	my.REG_DT,  ");
			buf.append("\n 	my.REGRID,  ");
			buf.append("\n 	my.MOD_DT,  ");
			buf.append("\n 	my.MODRID, ");
			buf.append("\n 	my.VD_QLTY,  ");
			buf.append("\n 	my.ASP_RTO_CD, ");
			buf.append("\n 	my.PILOT_YN, ");
			buf.append("\n 	my.SUB_TTL, ");
			buf.append("\n 	my.WEEKDAY, ");
			buf.append("\n 	my.FINAL_BRD_YN, ");
			buf.append("\n 	my.SCHD_PGM_NM, ");
			buf.append("\n 		code.desc, ");
			buf.append("\n 	mst.ARCH_REG_DD, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by my.REG_DT) AS rownum ");
		}
		buf.append("\n  from DAS.MY_SRCHLIST_TBL my ");
		buf.append("\n inner join metadat_mst_tbl mst on mst.master_id=my.master_id ");
		//buf.append("\n inner join DISCARD_INFO_TBL dis on dis.MASTER_ID  not in my.MASTER_ID ");
		buf.append("\n  left outer join code_tbl code on code.scl_cd=mst.ctgr_l_cd and code.clf_cd='P002' ");
		buf.append("\n where my.REGRID like  '%"+commonDO.getUserId()+"%'  ");
		buf.append("\n  and my.master_Id not in ( select dis.master_id from das.DISCARD_INFO_TBL dis) ");
	
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			
		}else{
		buf.append("\n  order by my.title asc, brd_dd asc ");
		}
		return buf.toString();
	}
	
	/**
	 * 내목록 조회를 한다.
	 * @param commonDO 조회조건을 포함하고 있는 DataObject
	 *  @param searchFlag 조건
	 */
	public static String selectMyCatalogListQuerys(MyCatalogDO commonDO, String searchFlag) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
	
			buf.append("\n 	my.REQ_USRID,  ");
			buf.append("\n 	my.SEQ,  ");
			buf.append("\n 	my.PGM_NM,  ");
			buf.append("\n 	my.EPN,  ");
			buf.append("\n 	my.TITLE,  ");
			buf.append("\n 	my.BRD_DD,  ");			
			buf.append("\n 		code.desc");			
		
		buf.append("\n  from DAS.MY_SRCHLIST_TBL my ");
		buf.append("\n inner join metadat_mst_tbl mst on mst.master_id=my.master_id ");
		buf.append("\n  left outer join code_tbl code on code.scl_cd=mst.ctgr_l_cd and code.clf_cd='P002' ");
		buf.append("\n where REQ_USRID = '"+commonDO.getReqUsrId()+"' ");		
		return buf.toString();
	}
	/**
	 * 요청 영상 목록 조회를 한다.
	 * @param commonDO 조회 조건을 포함하고 있는 DataObject
	 * @param searchFlag 조건
	 */
	public static String selectRequestDownListQuery(DASCommonDO commonDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag))
		{
			buf.append("\n 	count(1)  ");
		}
		else
		{
			buf.append("\n 	cont.CART_NO, ");
			buf.append("\n 	cont.CART_SEQ, ");
			String vdQltyCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_IMAGE_QUALITY, "cart.VD_QLTY");
			buf.append(vdQltyCodeName + " AS VD_QLTY_NM, ");
			String aspRtoCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_HOR_VER_RATIO, "cart.ASP_RTO_CD");
			buf.append(aspRtoCodeName + " AS ASP_RTO_NM, ");
			buf.append("\n 	pgm.PGM_NM, ");
			buf.append("\n 	meta.EPIS_NO, ");
			buf.append("\n 	cont.SOM,  ");
			buf.append("\n 	cont.EOM, ");
			String ristClfCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "cont.RIST_CLF_CD");
			buf.append(ristClfCodeName + " AS RIST_CLF_NM, ");
			buf.append("\n 	ROW_NUMBER() OVER(order by cont.CART_NO, cont.CART_SEQ) AS rownum ");
		}
		buf.append("\n from DAS.CART_CONT_TBL cont, DAS.DOWN_CART_TBL cart,  ");
		buf.append("\n 		DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL meta ");
		buf.append("\n where cont.CART_NO = cart.CART_NO ");
		buf.append("\n 	and cont.MASTER_ID = meta.MASTER_ID ");
		buf.append("\n 	and meta.PGM_ID = pgm.PGM_ID ");
		buf.append("\n 	and cart.REQ_USRID = '"+commonDO.getUserId()+"' ");		
		
		return buf.toString();
		
	}
}

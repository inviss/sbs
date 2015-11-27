/***********************************************************
 * 프로그램ID  	: ExternalDAO.java
 * 프로그램명  	: ExternalDAO
 * 작성일자     	:
 * 작성자       	:
 * 설명          : Client 와 WebService 관련 DataObject
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0
 ***********************************************************/
package com.app.das.business.dao.statement;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.ErrorLogDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.business.transfer.MediaArchiveDO;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.business.transfer.PreProcessingDO;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.ServersDO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.TimeRistInfo;
import com.app.das.business.transfer.WmvH264DO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.StringUtils;

/**
 *  
 * @author dekim
 *
 */
public class ExternalStatement 
{	
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();

	/**
	 * 입력받은 카트번호에 해당하는 다운로드 카트정보를 조회한다.
	 *
	 */
	public static String selectDownCartInfoQuery()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	down.CART_NO, "); 
		buf.append("\n 	down.DATA_CLF_CD,   ");
		buf.append("\n 	down.PRIO_CD,  ");
		buf.append("\n 	down.STRG_LOC,  ");
		buf.append("\n 	down.RIST_YN,  ");
		buf.append("\n 	down.APP_CONT,  ");
		buf.append("\n 	down.REQ_USRID,  ");
		buf.append("\n 	down.REQ_NM,  ");
		buf.append("\n 	down.REQ_DT,  ");
		buf.append("\n 	down.DOWN_DT,  ");
		buf.append("\n 	down.APP_DT,  ");
		buf.append("\n 	down.DOWN_SUBJ,  ");
		buf.append("\n 	down.GAURANTOR_ID,  ");
		buf.append("\n 	down.REG_DT,  ");
		buf.append("\n 	down.REGRID,  ");
		buf.append("\n 	down.MOD_DT,  ");
		buf.append("\n 	down.MODRID,  ");
		buf.append("\n 	down.DEPT_CD,  ");
		buf.append("\n 	cont.VD_QLTY,  ");
		buf.append("\n 	cont.ASP_RTO_CD,  ");
		buf.append("\n 	cont.DOWN_STAT ,  ");
		buf.append("\n CODE.DESC  ");
		buf.append("\n from DAS.DOWN_CART_TBL down ");
		buf.append("\n  inner join das.CART_CONT_TBL cont on  cont.CART_NO = down.CART_NO  ");
		buf.append("\n  left outer join CODE_TBL code on code.CLF_CD='P018' AND code.GUBUN='L' AND CODE.SCL_CD=CONT.RIST_CLF_CD ");
		buf.append("\n where 1=1 ");
		buf.append("\n and down.REQ_USRID = ? ");
		buf.append("\n  and cont.DOWN_STAT = '001'  ");
		buf.append("\n order by down.REG_DT desc  ");
		buf.append("\n fetch first 1 rows only  ");
		buf.append("\n WITH UR ");


		return buf.toString();
	}
	/**
	 * 입력받은 카트번호에 해당하는 카트내용 정보를 조회한다.
	 *
	 */
	public static final String selectCartContListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n CART.CART_NO,  ");
		buf.append("\n 	CART.CART_SEQ,  ");
		buf.append("\n 	CART.RIST_CLF_CD,  ");
		buf.append("\n 	CART.CT_ID,  ");
		buf.append("\n 	CART.CTI_ID,  ");
		buf.append("\n 	CART.SOM,  ");
		buf.append("\n 	CART.EOM,  ");
		buf.append("\n 	CART.DURATION,  ");
		buf.append("\n 	CART.REG_DT,  ");
		buf.append("\n 	CART.REGRID,  ");
		buf.append("\n 	CART.MOD_DT,  ");
		buf.append("\n 	CART.MODRID,  ");
		buf.append("\n 	CART.CTGR_L_CD,  ");
		buf.append("\n 	CART.CTGR_M_CD,  ");
		buf.append("\n 	CART.CTGR_S_CD,  ");
		buf.append("\n 	CART.CT_CONT,  ");
		buf.append("\n 	CART.CT_NM,  ");
		//buf.append("\n 	CART.DOWN_TYP,  ");
		buf.append("\n 	CART.MASTER_ID, ");
		buf.append("\n 	CASE WHEN  CART.DOWN_TYP = 'P' THEN 'partial' ");
		buf.append("\n 	 when cart.down_typ = 'F' then 'full' ");
		buf.append("\n 	else '' ");
		buf.append("\n 	end as down_typ, ");
		buf.append("\n 	CASE WHEN  meta.PGM_ID <> 0  THEN pgm.PGM_NM ");
		buf.append("\n 	 when  meta.PGM_ID =0  then meta.title ");
		buf.append("\n 	else '' ");
		buf.append("\n 	end as title, ");
		buf.append("\n 	meta.EPIS_NO ");
		buf.append("\n 	,value(CODE.DESC,'') AS VD_QLTY_NM");
		//buf.append("\n 	CART.RIST_CLF_CD ");


		buf.append("\n from DAS.CART_CONT_TBL CART ");		
		buf.append("\n inner join DAS.DOWN_CART_TBL DOWN on CART.CART_NO = DOWN.CART_NO  ");
		buf.append("\n left outer join DAS.METADAT_MST_TBL meta on meta.MASTER_ID=cart.master_id ");
		buf.append("\n left outer join das.contents_tbl con on con.ct_id = cart.ct_id ");
		buf.append("\n left outer join das.code_tbl code on code.SCL_CD = con.VD_QLTY and code.CLF_CD='A005' ");

		buf.append("\n  left outer join DAS.PGM_INFO_TBL pgm on pgm.PGM_ID = meta.PGM_ID  ");
		buf.append("\n where CART.CART_NO = ? ");
		buf.append("\n and CART.down_stat not in ( '003', '004' ,'005','006','007' ) ");
		/*	buf.append("\n or CART.down_stat <> '003'");
		buf.append("\n or CART.down_stat <> '004'");
		buf.append("\n or CART.down_stat <> '005'");
		buf.append("\n or CART.down_stat <> '006'");
		buf.append("\n or CART.down_stat <> '007' )");*/
		buf.append("\n order by CART_SEQ ");
		buf.append("\n WITH UR ");

		return buf.toString();
	}
	/**
	 * 마스터 테이블 수정이력 정보를 조회한다.
	 * 
	 */
	public static final String selectModUserInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	 mod.modrid,    ");
		buf.append("\n 	mod.mod_dt ,  ");
		buf.append("\n 	value(user.USER_NM,'') as mod_nm  ");
		buf.append("\n 	from das.mst_mod_info_tbl  mod	 ");
		buf.append("\n 	left outer join USER_INFO_TBL user on user.SBS_USER_ID=mod.modrid  ");
		buf.append("\n 	where master_id = ?  ");

		buf.append("\n 	order by mod_dt desc fetch first 5 rows only  ");		
		buf.append("\n WITH UR ");

		return buf.toString();
	}
	/**
	 * 코너정보를 조회한다.
	 *
	 */
	public static final String selectCornerInfoListQuery()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	cont.CT_ID, "); 
		buf.append("\n 	cont.CT_NM, ");  
		buf.append("\n 	sub.CTI_ID, ");
		buf.append("\n 	cont.KFRM_PATH, "); 
		buf.append("\n 	sub.FL_PATH, "); 
		buf.append("\n 	cont.KFRM_PX_CD, "); 
		buf.append("\n 	cont.VD_QLTY, ");
		buf.append("\n 	cont.ASP_RTO_CD, ");
		buf.append("\n 	mapp.S_DURATION, "); 
		buf.append("\n 	mapp.E_DURATION,  "); 
		buf.append("\n 	cn.rpimg_kfrm_seq, "); 
		buf.append("\n 	meta.TITLE,  ");
		buf.append("\n 	cn.CN_ID,  ");
		buf.append("\n 	cn.CN_NM,  ");
		buf.append("\n 	cont.CT_NM,  ");
		buf.append("\n 	cont.CONT,  ");
		buf.append("\n 	cn.SOM,  ");
		buf.append("\n 	cn.EOM,  ");
		buf.append("\n 	cn.CN_TYPE_CD,  ");		
		buf.append("\n 	cn.CN_INFO,  ");
		buf.append("\n 	cont.DURATION,  ");
		buf.append("\n 	cn.SOM,  ");
		buf.append("\n 	cont.CT_SEQ,  ");
		buf.append("\n 	cn.RPIMG_CT_ID  ");		
		buf.append("\n from das.metadat_mst_tbl meta, das.CONTENTS_TBL cont, das.CONTENTS_MAPP_TBL mapp,das.CORNER_TBL cn,  ");
		buf.append("\n das.CONTENTS_INST_TBL sub  ");		
		buf.append("\n 	where mapp.MASTER_ID= ? ");
		buf.append("\n 	and mapp.master_id = meta.master_id  ");		
		buf.append("\n 	and cn.CN_ID = mapp.CN_ID  ");
		buf.append("\n 	and cont.CT_ID=sub.CT_ID  ");
		buf.append("\n 	and cont.CT_ID = mapp.CT_ID + 0 ");	
		buf.append("\n 	and sub.cti_fmt ='301'  ");		
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n order by cont.CT_SEQ asc, mapp.s_duration asc  ");
		buf.append("\n with ur ");			


		return buf.toString();		


	}
	/**
	 * 키프레임 정보를 조회한다.

	 */
	public static final String selectKeyFrameInfoInfoListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	kfm.kfrm_seq, ");
		buf.append("\n 	kfm.time_cd, ");
		buf.append("\n 	ct.VD_QLTY, ");
		buf.append("\n 	ct.ASP_RTO_CD "); 
		buf.append("\n from DAS.keyframe_tbl kfm, DAS.contents_tbl ct "); 
		buf.append("\n where kfm.ct_id=ct.ct_id  ");
		buf.append("\n 	and ct.ct_id= ? ");
		buf.append("\n 	and kfrm_seq >= ?  ");
		buf.append("\n 	and kfrm_seq <= ?  ");
		buf.append("\n order by kfrm_seq asc with ur ");

		return buf.toString();
	}
	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 *
	 */
	public static final String selectProgramInfoQuery(String pgmNm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.PGM_ID, ");
		buf.append("\n 	meta.MASTER_ID, ");
		buf.append("\n 	pgm.PGM_NM, ");
		//buf.append("\n 	pgm.PGM_EPIS   ");		// 예전엔 PGM에도 EPIS가 있었나보지.
		buf.append("\n 	meta.EPIS_NO ");
		buf.append("\n from DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL meta  ");
		buf.append("\n where PGM_NM like '%"+pgmNm+"%'");
		buf.append("\n and pgm.PGM_ID = meta.PGM_ID ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다 beans로 파싱
	 * @param pgmNm    프로그램 이름 검색어
	 */
	public static final String selectPgmInfoFromNameQuery(String pgmNm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.PGM_ID,  ");
		buf.append("\n 	mst.master_id, ");
		buf.append("\n 	pgm.PGM_CD, ");
		buf.append("\n 	pgm.BRD_BGN_DD, ");
		buf.append("\n 	pgm.PGM_NM,   ");	
		buf.append("\n 	pgm.BRD_END_DD,   ");	
		buf.append("\n 	pgm.CTGR_L_CD,   ");	
		buf.append("\n 	pgm.CTGR_M_CD,   ");	
		buf.append("\n 	pgm.CTGR_S_CD,   ");	
		buf.append("\n 	pgm.MEDIA_CD   ");	
		buf.append("\n 	, pgm.CHAN_CD  ");
		buf.append("\n 	, pgm.PRD_DEPT_NM      ");
		buf.append("\n 	,  pgm.SCHD_PGM_NM     ");
		buf.append("\n 	,  mst.AWARD_HSTR      ");
		buf.append("\n 	,  mst.SUB_TTL      ");
		buf.append("\n from DAS.PGM_INFO_TBL pgm ");
		buf.append("\n   inner join DAS.METADAT_MST_TBL mst on pgm.PGM_ID=mst.PGM_ID  ");
		if(!pgmNm.equals("")){
			buf.append("\n where pgm.PGM_NM like '%"+pgmNm+"%'");
		}
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}



	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다(pds cms pgm_id 기준)
	 * @param pgmNm    프로그램 이름 검색어
	 */
	public static final String selectPgmInfoFromNameQuery2(String pgmNm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT DISTINCT ");
		buf.append("\n 	PROGRAM_NAME  ");
		buf.append("\n 	,PROGRAM_CODE ");

		buf.append("\n FROM PDS_PGMINFO_TBL ");
		//buf.append("\n   inner join DAS.METADAT_MST_TBL mst on pgm.PGM_ID=mst.PGM_ID  ");
		if(!pgmNm.equals("")){
			buf.append("\n where PROGRAM_NAME like ?");
		}
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 대본정보를 조회한다.
	 *
	 */
	public static final String selectScenarioQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MASTER_ID, ");
		buf.append("\n 	TITLE, ");
		buf.append("\n 	note, ");
		buf.append("\n 	REGDT ");
		buf.append("\n from DAS.SCENARIO_TBL ");
		buf.append("\n where master_id = ?");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 대본정보를 조회한다.
	 *
	 */
	public static final String selectScenarioQuery2()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MASTER_ID, ");
		buf.append("\n 	TITLE, ");
		buf.append("\n 	note, ");
		buf.append("\n 	REGDT ");
		buf.append("\n from DAS.SCENARIO_REAL_tBL ");
		buf.append("\n where master_id = ?");
		buf.append("\n and seq= ?");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	public static final String countScenario()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");		
		buf.append("\n from DAS.SCENARIO_REAL_tBL ");
		buf.append("\n where master_id = ?");

		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 관련영상 존재를 조회한다
	 *
	 */
	public static final String selectRelationQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	PARENT_MASTER_ID, ");
		buf.append("\n 	CHILD_MASTER_ID ");
		buf.append("\n from DAS.RELATION_MASTER ");
		buf.append("\n where PARENT_master_id = ?");
		buf.append("\n fetch first 1 rows only	 ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 관련영상 정보 조회
	 * @param programInfoDO 정보조회를 위해 필요한 beans
	 */
	public static final String selectSearchRelationInfoQuery(ProgramInfoDO	programInfoDO)
	{
		StringBuffer buf = new StringBuffer();
		/*buf.append("\n select distinct                                                                                           ");
		buf.append("\n  	meta.master_id,                                                                                        ");
		buf.append("\n 		(select r.desc from das.code_tbl r where r.clf_cd ='A001' and r.scl_cd =ct.ct_cla) as ct_cla_nm,       ");
		buf.append(" \n	pgm.pgm_id,                                                                                              ");
		buf.append("\n  	meta.title,                                                                                            ");
		buf.append("\n  	pgm.pgm_cd,                                                                                            ");
		buf.append(" \n	(select z.desc from das.code_tbl z where z.clf_cd ='P002' and z.scl_cd =pgm.CTGR_L_CD) as ctgr_l_nm,     ");
		buf.append("\n 	(select z.desc from das.code_tbl z where z.clf_cd ='P003' and z.scl_cd =pgm.CTGR_M_CD) as ctgr_m_nm,     ");
		buf.append("\n 	(select z.desc from das.code_tbl z where z.clf_cd ='P004' and z.scl_cd = pgm.CTGR_S_CD) as ctgr_s_nm,    ");
		buf.append("\n  	meta.epis_no,                                                                                          ");
		buf.append("\n  	meta.brd_dd,                                                                                           ");
		buf.append("\n  	(select r.desc from das.code_tbl r where r.clf_cd ='A005' and r.scl_cd =ct.VD_QLTY) as vd_qlty_nm,     ");
		buf.append("\n 	meta.brd_leng                                                                                            ");
		buf.append("\n  from das.pgm_info_tbl pgm                                                                                ");
		buf.append("\n       inner join das.metadat_mst_tbl meta on  pgm.pgm_id=meta.pgm_id                                      ");
		buf.append("\n 			right outer join das.contents_mapp_tbl cmt on cmt.master_id = meta.master_id                         ");
		buf.append(" \n			inner join das.contents_tbl ct on ct.ct_id = cmt.ct_id                                               ");
		buf.append("\n where pgm.pgm_id=meta.pgm_id ");  11.06.14 박보아 대리님의 요청으로 방송본 원본을 제외한 영상조회건으로 수정*/

		buf.append("\n select distinct                                                                                           ");
		buf.append("\n  	meta.master_id,                                                                                        ");
		buf.append("\n 		      value((select r.desc from das.code_tbl r where r.clf_cd ='A001' and r.scl_cd =ct.ct_cla), '') as ct_cla_nm,        ");

		buf.append("\n  	meta.title,                                                                                            ");
		buf.append("\n  	meta.pgm_id,                                                                                            ");
		buf.append("\n  	meta.pgm_cd,                                                                                            ");

		buf.append(" \n	(select z.desc from das.code_tbl z where z.clf_cd ='P002' and z.scl_cd =meta.CTGR_L_CD) as ctgr_l_nm,     ");

		buf.append("\n  	value(meta.epis_no,'0') as epis_no,                                                                                          ");
		buf.append("\n  	case when meta.CTGR_L_CD ='100' then meta.FM_DT                        ");
		buf.append("\n  	when meta.CTGR_L_CD ='200' then meta.brd_dd                        ");
		buf.append("\n  	 else ''    end as brd_dd,                    ");
		buf.append("\n  value((select r.desc from das.code_tbl r where r.clf_cd ='A005' and r.scl_cd =ct.VD_QLTY ), '') as vd_qlty_nm,        ");
		buf.append("\n 	meta.brd_leng                                                                                            ");
		buf.append("\n  from das.metadat_mst_tbl meta                                                                                  ");
		buf.append("\n       inner join   das.contents_mapp_tbl cmt on cmt.master_id = meta.master_id                                   ");
		buf.append("\n 		inner join das.contents_tbl ct on ct.ct_id = cmt.ct_id                          ");
		buf.append(" \n			 where 1=1                                               ");

		if(!programInfoDO.getPgmNm().equals("")){
			buf.append("\n       and meta.title like '%"+programInfoDO.getPgmNm()+"%'");
		}
		if(!programInfoDO.getBrdBgnDd().equals("")){
			buf.append("\n       and ( (meta.brd_dd >='"+programInfoDO.getBrdBgnDd()+"' ");
		}
		if(!programInfoDO.getBrdEndDd().equals("")){
			buf.append("\n       and meta.brd_dd <='"+programInfoDO.getBrdEndDd()+"' ) " );
		}


		if(!programInfoDO.getBrdBgnDd().equals("")){
			buf.append("\n       or (meta.fm_Dt >='"+programInfoDO.getBrdBgnDd()+"' ");
		}
		if(!programInfoDO.getBrdEndDd().equals("")){
			buf.append("\n       and meta.fm_Dt <='"+programInfoDO.getBrdEndDd()+"' )) " );
		}





		buf.append("\n      	and ct.CT_CLA in ('005','010','011') " );
		buf.append("\n      	and meta.del_dd='' " );
		buf.append("\n      	and meta.lock_stat_cd='N' " );
		//20120727 최효정c 확인결과 관련영상은 아카이브 된것만 보여야함.
		buf.append("\n      	and meta.arch_reg_dd <>'' " );
		buf.append("\n      	 AND (meta.master_ID NOT IN (  " );
		buf.append("\n      	SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM  " );
		buf.append("\n      	INNER JOIN ( SELECT MASTER_ID FROM METADAT_MST_TBL " );
		buf.append("\n      	 WHERE DEL_DD IS NULL OR DEL_DD > TS_FMT(CURRENT DATE,'yyyymmdd') or REPLACE(del_dd,' ','')=''  " );
		buf.append("\n      	) PM ON PM.MASTER_ID =RM.PARENT_MASTER_ID   " );
		buf.append("\n      	GROUP BY CHILD_MASTER_ID  " );
		buf.append("\n      	 ) )  " );
		buf.append("\n order by BRD_DD desc  ");
		//		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다
	 * @param programInfoDO    검색조건을 가진beans
	 */
	public static final String selectLastPgmInfolistQuery(ProgramInfoDO	programInfoDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select distinct ");
		buf.append("\n 	pgm.pgm_id,	pgm.pgm_nm, pgm.pgm_cd,meta.epis_no,meta.brd_dd, 	meta.master_id  ");
		buf.append("\n ,meta.DATA_STAT_CD, meta.REG_DT, meta.REGRID,meta.CTGR_L_CD, ");
		buf.append("\n 	'' as MEDIA_ID, code.DESC AS CTGR_L_NM ,code2.DESC AS DATA_STAT_NM");


		buf.append("\n from das.pgm_info_tbl pgm ");
		buf.append("\n left outer join das.metadat_mst_tbl meta on pgm.pgm_id=meta.pgm_id ");
		buf.append("\n left outer join das.contents_mapp_tbl mapp on mapp.master_id=meta.master_id ");
		buf.append("\n left outer join das.contents_tbl con on con.ct_id=mapp.CT_ID ");
		buf.append("\n left outer join das.code_tbl code2 on meta.DATA_STAT_CD=code2.scl_cd and  code2.CLF_CD='P011' ");
		buf.append("\n left outer join das.code_tbl code on code.scl_cd=meta.CTGR_L_CD and code.CLF_CD='P002'  ");


		/**검색조건*/
		if(!programInfoDO.getPgmNm().equals("")||!programInfoDO.getBrdBgnDd().equals("")||!programInfoDO.getBrdEndDd().equals("")){
			buf.append("\n where ");
			if(!programInfoDO.getPgmNm().equals("")){
				buf.append("\n       pgm.PGM_NM like '%"+programInfoDO.getPgmNm()+"%'");
			}


			if(!programInfoDO.getBrdBgnDd().equals("")){
				if(!programInfoDO.getPgmNm().equals("")){
					buf.append("\n and ");		
				}
				buf.append("\n        meta.brd_dd >='"+programInfoDO.getBrdBgnDd()+"' ");
			}
			if(!programInfoDO.getBrdEndDd().equals("")){
				buf.append("\n       and meta.brd_dd <='"+programInfoDO.getBrdEndDd()+"' " );
			}

			buf.append("\n order by pgm.pgm_nm desc ");
			//		buf.append("\n WITH UR	 ");
		}
		return buf.toString();
	}
	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 * @throws DASException
	 */
	public static final String selectLastPgmInfolistQuery(String pgmNm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.pgm_id, ");
		buf.append("\n 	pgm.pgm_nm, ");
		buf.append("\n 	pgm.pgm_cd, ");
		buf.append("\n value(meta.epis_no, 0)  as epis_no, ");
		buf.append("\n 	meta.brd_dd,   ");	
		buf.append("\n 	master_id   ");	
		buf.append("\n from das.pgm_info_tbl pgm,das.metadat_mst_tbl meta ");
		buf.append("\n where PGM_NM like '%"+pgmNm+"%'");
		buf.append("\n and pgm.pgm_id=meta.pgm_id ");
		buf.append("\n  and pgm.use_yn='Y' ");
		buf.append("\n  and meta.del_dd='' ");
		buf.append("\n order by pgm.pgm_nm asc, meta.epis_no desc , meta.brd_dd desc   ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	
	/**
	 * 일괄 수정할 데이타를 조회한다.
	 * @param programInfoDO
	 * @param searchFlag 조회조건
	 * @throws DASException
	 */
	public static final String selectNewTotalChangelistQuery(ProgramInfoDO	programInfoDO, String searchFlag) {
		StringBuffer buf = new StringBuffer();
		buf.append("\nSELECT                                                                                                ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag )) {
			buf.append("\n 	  count(1) "); 
		} else {
			buf.append("\n * FROM (                                                                        						");
			buf.append("\n	 SELECT                                                                        						");
			buf.append("\n mst.pgm_id,                                                                         ");
			buf.append("\n mst.master_id,                                                                      ");
			buf.append("\n (CASE mst.PGM_ID WHEN 0 THEN mst.TITLE ELSE pgm.PGM_NM END) AS title,               ");
			buf.append("\n value(mst.pgm_cd,'') as pgm_Cd ,                                                    ");
			buf.append("\n value(mst.epis_no, '0') as epis_no,                                                 ");
			buf.append("\n (CASE mst.CTGR_L_CD WHEN '100' THEN mst.FM_DT ELSE mst.BRD_DD end) AS brd_dd,    	 ");
			buf.append("\n mst.CTGR_L_CD,                                                                      ");
			buf.append("\n mst.CTGR_M_CD,                                                                      ");
			buf.append("\n mst.CTGR_S_CD,                                                                      ");
			buf.append("\n value(mst.PRDT_DEPT_NM,'') as PRDT_DEPT_NM,                                         ");
			buf.append("\n value(mst.ORG_PRDR_NM,'') as ORG_PRDR_NM,                                           ");
			buf.append("\n mst.PRDT_IN_OUTS_CD,                                                                ");
			buf.append("\n value(mst.CMR_PLACE,'') as CMR_PLACE,                                               ");
			buf.append("\n value(mst.CPRT_TYPE,'') as CPRT_TYPE,                                               ");
			buf.append("\n mst.RSV_PRD_CD,                                                                     ");
			buf.append("\n value(mst.CPRT_TYPE_DSC,'') as CPRT_TYPE_DSC,                                       ");
			buf.append("\n mst.DATA_STAT_CD,                                                                   ");
			buf.append("\n value(mst.CPRTR_NM,'') as  CPRTR_NM, 	                                           ");
			buf.append("\n value(mst.AWARD_HSTR ,'') as   AWARD_HSTR,                                          ");
			buf.append("\n cti.RECORD_TYPE_CD,                                                                 ");
			buf.append("\n ROW_NUMBER() OVER(ORDER BY mst.BRD_DD asc, mst.FM_DT ASC, mst.TITLE asc) AS rownum  ");
		}
		buf.append("\nFROM METADAT_MST_TBL mst                                                                              ");
		buf.append("\n	inner JOIN CONTENTS_TBL ct ON mst.RPIMG_CT_ID = ct.CT_ID                                            ");
		buf.append("\n    inner JOIN CONTENTS_INST_TBL cti ON ct.CT_ID = cti.CT_ID AND cti.CTI_FMT LIKE '1%'                ");
		buf.append("\n    left outer JOIN PGM_INFO_TBL pgm ON mst.PGM_ID = pgm.PGM_ID                                       ");
		buf.append("\n    left outer JOIN CODE_TBL code ON cti.RECORD_TYPE_CD = code.SCL_CD AND code.CLF_CD = 'A010'             ");
		buf.append("\nWHERE 1=1 AND (mst.del_dd IS NULL OR mst.del_dd = '')                                                 ");
		buf.append("\n    AND NOT EXISTS (                                                                                  ");
		buf.append("\n    	SELECT 1 FROM DISCARD_INFO_TBL discard WHERE mst.master_id = discard.master_id                  ");
		buf.append("\n    )                                                                                                 ");
		buf.append("\n    AND NOT EXISTS (                                                                                  ");
		buf.append("\n    	SELECT 1 FROM RELATION_MASTER rm WHERE rm.CHILD_MASTER_ID = mst.MASTER_ID                       ");
		buf.append("\n    )                                                                                                 ");
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getSearch_word())) {
			buf.append("\n AND (mst.TITLE LIKE '%"+programInfoDO.getSearch_word()+"%'  OR mst.PRDT_DEPT_NM LIKE '%"+programInfoDO.getSearch_word()+"%') ");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getDept_nm())) {
			buf.append("\n AND mst.PRDT_DEPT_NM LIKE '%"+programInfoDO.getDept_nm()+"%' ");
		}
		
		// 방송일 or 촬영일
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getBrdBgnDd())
				&& org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getBrdEndDd())) {
			buf.append("\n AND ((substr(mst.brd_dd, 1, 8) between '"+programInfoDO.getBrdBgnDd()+"' and '"+programInfoDO.getBrdEndDd()+"')");
			buf.append("   	OR (substr(mst.fm_dt, 1, 8) between '"+programInfoDO.getBrdBgnDd()+"' and '"+programInfoDO.getBrdEndDd()+"'))");
		}
		
		// 대분류, 중분류, 소분류
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getCtgrLCd())) {
			buf.append("\n AND mst.ctgr_l_cd like '"+programInfoDO.getCtgrLCd()+"%' ");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getCtgrMCd())) {
			buf.append("\n AND mst.ctgr_m_cd like '"+programInfoDO.getCtgrMCd()+"%' ");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getCtgrSCd())) {
			buf.append("\n AND mst.ctgr_s_cd like '"+programInfoDO.getCtgrSCd()+"%' ");
		}
		
		// 아카이브 경로
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getArchive_path())) {
			String[] path = programInfoDO.getArchive_path().split(",");
			
			buf.append("\n AND ( ");
			for(int i =0; i<path.length;i++){
				if(i!=0){
					buf.append("\n or ");
				}
				if(path[i].equals("D")) { // 매체변환
					buf.append("\n mst.arch_route LIKE 'D%' ");
				} else if(path[i].equals("O")) {
					buf.append("\n mst.arch_route LIKE 'O%'");
				} else if(path[i].equals("P")){
					buf.append("\n mst.arch_route ='P'");
				}
			}
			buf.append("\n ) ");
		}
		
		// 회사 및 채널 검색
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getCocd())) {
			buf.append("\n  AND mst.cocd like  '" + programInfoDO.getCocd()+"%'");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getChennel())) {
			buf.append("\n  AND mst.chennel_cd like  '" + programInfoDO.getChennel()+"%'");
		}
		
		// 코드별 검색
		if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getClf_cd())) {
			if(org.apache.commons.lang.StringUtils.isNotBlank(programInfoDO.getScl_cd())) {
				if("RSV".equals(programInfoDO.getClf_cd())) { //보존기한
					buf.append("\n AND  mst.RSV_PRD_CD = '"+programInfoDO.getScl_cd()+"'");
				} else if("CPRT".equals(programInfoDO.getClf_cd())) { // 저작권
					buf.append("\n AND  mst.CPRT_TYPE = '"+programInfoDO.getScl_cd()+"'");
				} else if("VIEW".equals(programInfoDO.getClf_cd())) { // 시청등급
					buf.append("\n AND  mst.VIEW_GR_CD = '"+programInfoDO.getScl_cd()+"'");
				} else if("VP".equals(programInfoDO.getClf_cd())) {  // 화질
					buf.append("\n AND  ct.VD_QLTY = '"+programInfoDO.getScl_cd()+"'");
				} else if("ASP".equals(programInfoDO.getClf_cd())) { // 종횡비
					buf.append("\n AND  ct.ASP_RTO_CD = '"+programInfoDO.getScl_cd()+"'");
				} else if("USE_LIMIT".equals(programInfoDO.getClf_cd())) { // 사용제한
					buf.append("\n AND  mst.RIST_CLF_CD = '"+programInfoDO.getScl_cd()+"'");
				}
			}
		}
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag )) {
			
		} else {
			buf.append("\n ) t WHERE t.rownum between ? and ? ");
		}
		
		return buf.toString();
	}
	
	@Deprecated
	public static final String selectTotalChangelistQuery(ProgramInfoDO	programInfoDO, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{
			buf.append("\n		 	meta.pgm_id,                                      ");
			buf.append("\n			meta.master_id,                                       ");
			buf.append("\n		 	case when meta.pgm_id !=0 then pgm.pgm_nm                                      ");
			buf.append("\n		 	when meta.pgm_id =0  then  meta.title                                   ");
			buf.append("\n		 	 else meta.title                                     ");
			buf.append("\n		 	 end as title,                                      ");
			buf.append("\n		 	 value(meta.pgm_cd,'') as pgm_Cd ,                                     ");
			buf.append("\n		 	value(meta.epis_no, '0') as epis_no,                                   ");
			buf.append("\n		 	case when meta.ctgr_l_cd='100' then meta.fm_Dt   	                                 ");
			buf.append("\n		 	 when meta.ctgr_l_cd='200'  then  meta.brd_dd   	                                 ");
			buf.append("\n		 	else meta.brd_dd    	                                 ");
			buf.append("\n		 	     end as brd_dd,    	                                 ");

			buf.append("\n			meta.CTGR_L_CD,                                  ");
			buf.append("\n			meta.CTGR_M_CD,                                  ");
			buf.append("\n			meta.CTGR_S_CD,                                  ");
			buf.append("\n			value(meta.PRDT_DEPT_NM,'') as PRDT_DEPT_NM,                                 ");
			buf.append("\n			value(meta.ORG_PRDR_NM,'') as ORG_PRDR_NM,                                 ");
			buf.append("\n			meta.PRDT_IN_OUTS_CD,                            ");
			buf.append("\n			 value(meta.CMR_PLACE,'') as CMR_PLACE,                             ");
			buf.append("\n			 value(meta.CPRT_TYPE,'') as CPRT_TYPE,                  ");

			buf.append("\n          meta.RSV_PRD_CD,                                 ");
			buf.append("\n         value(meta.CPRT_TYPE_DSC,'') as CPRT_TYPE_DSC,        ");
			buf.append("\n          meta.DATA_STAT_CD,                              ");
			buf.append("\n			value(meta.CPRTR_NM,'') as  CPRTR_NM 	                                   ");
			buf.append("\n			 ,value(meta.AWARD_HSTR ,'') as   AWARD_HSTR                                   ");
			buf.append("\n			,inst.RECORD_TYPE_CD                                  ");
			buf.append("\n  ,ROW_NUMBER() OVER(ORDER BY META.BRD_DD) AS rownum");
		}
		buf.append("\n		  from   das.metadat_mst_tbl   meta "); 
		//	buf.append("\n		 INNER JOIN (SELECT PGM_NM,PGM_CD,pgm_id FROM das.pgm_info_tbl GROUP BY  PGM_NM,PGM_CD,pgm_id)   pgm ON  pgm.pgm_id=meta.pgm_id    "); 
		buf.append("\n		 INNER JOIN  (select master_id, ct_id from CONTENTS_mapp_tbl group by master_id , ct_id) map ON MAP.MASTER_ID = META.MASTER_ID  "); 
		buf.append("\n		 INNER JOIN  DAS.CONTENTS_TBL CONT ON  cont.CT_ID =  meta.RPIMG_CT_ID and meta.RPIMG_CT_ID =map.ct_id "); 
		buf.append("\n		 INNER JOIN  DAS.CONTENTS_inst_TBL inst ON inst.CT_ID = map.CT_ID and cti_fmt like '1%'                 ");
		buf.append("\n		 left outer join das.pgm_info_Tbl pgm on pgm.pgm_id = meta.PGM_ID                      ");
		buf.append("\n		 where 1=1                       ");

		if(!programInfoDO.getSearch_word().equals("")){
			buf.append("\n       and (META.TITLE LIKE '%"+programInfoDO.getSearch_word()+"%'  OR meta.PRDT_DEPT_NM LIKE '%"+programInfoDO.getSearch_word()+"%') ");
		}
		if(!programInfoDO.getDept_nm().equals("")){
			buf.append("\n       and meta.PRDT_DEPT_NM LIKE '%"+programInfoDO.getDept_nm()+"%' ");
		}


		if(!programInfoDO.getBrdBgnDd().equals("")){
			buf.append("\n       and ( (meta.brd_dd >='"+programInfoDO.getBrdBgnDd()+"' ");
		}
		if(!programInfoDO.getBrdEndDd().equals("")){
			buf.append("\n       and meta.brd_dd <='"+programInfoDO.getBrdEndDd()+"' ) or" );
		}
		if(!programInfoDO.getBrdBgnDd().equals("")){
			buf.append("\n       ( substr(meta.fm_dt,1,8) >='"+programInfoDO.getBrdBgnDd()+"' ");
		}
		if(!programInfoDO.getBrdEndDd().equals("")){
			buf.append("\n       and substr(meta.fm_dt,1,8) <='"+programInfoDO.getBrdEndDd()+"' ) )" );
		}



		if(!programInfoDO.getCtgrLCd().equals("")){
			buf.append("\n       and meta.ctgr_l_cd like '"+programInfoDO.getCtgrLCd()+"%' ");
		}
		if(!programInfoDO.getCtgrMCd().equals("")){
			buf.append("\n       and meta.ctgr_m_cd = '"+programInfoDO.getCtgrMCd()+"' ");
		}
		if(!programInfoDO.getCtgrSCd().equals("")){
			buf.append("\n       and meta.ctgr_s_cd = '"+programInfoDO.getCtgrSCd()+"' ");
		}



		/** 아카이브 경로*/

		if(!programInfoDO.getArchive_path().equals("")){
			String[] path = programInfoDO.getArchive_path().split(",");
			buf.append("\n 	and  ( ");
			for(int i=0;i<path.length;i++){
				if(path[i].equals("D"))
				{
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	(meta.arch_route LIKE 'D%') ");
				}
				else if(path[i].equals("O"))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  meta.arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");

				}else if(path[i].equals("P")){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( meta.arch_route ='P' )");

				}
			}
			buf.append("\n 	 ) ");
		}
		//2012.4.24 조회조건 추가
		if(!programInfoDO.getCocd().equals("")){

			buf.append("\n  AND meta.cocd like  '" + programInfoDO.getCocd()+"%'");


		}
		if(!programInfoDO.getChennel().equals("")){

			buf.append("\n   AND meta.chennel_cd like  '" + programInfoDO.getChennel()+"%'");


		}









		/** 코드별 검색*/
		if(!programInfoDO.getClf_cd().equals("")){
			/*	*//** 복본여부*//*

	 	if(programInfoDO.getClf_cd().equals("COPY")){

		buf.append("\n CON.COPY_OBJECT_YN = '"+programInfoDO.getScl_cd()+"'");

		}
			 */
			/** 보존기한*/
			if(programInfoDO.getClf_cd().equals("RSV")){

				if(programInfoDO.getScl_cd().equals("")){

				}else{
					buf.append("\n AND  META.RSV_PRD_CD = '"+programInfoDO.getScl_cd()+"'");
				}
			}

			/** 저작권*/
			if(programInfoDO.getClf_cd().equals("CPRT")){

				if(programInfoDO.getScl_cd().equals("")){

				}else{
					buf.append("\n AND   META.CPRT_TYPE = '"+programInfoDO.getScl_cd()+"'");
				}
			}

			/** 시청등급*/
			if(programInfoDO.getClf_cd().equals("VIEW")){

				if(programInfoDO.getScl_cd().equals("")){

				}else{
					buf.append("\n  AND  META.VIEW_GR_CD = '"+programInfoDO.getScl_cd()+"'");
				}
			}


			//** 화질*//*
			if(programInfoDO.getClf_cd().equals("VP")){

				if(programInfoDO.getScl_cd().equals("")){

				}else{
					buf.append("\n  AND CON.VP_QLTY = '"+programInfoDO.getScl_cd()+"'");
				}
			}



			//** 종횡비*//*
			if(programInfoDO.getClf_cd().equals("ASP")){

				if(programInfoDO.getScl_cd().equals("")){

				}else{
					buf.append("\n AND  CON.ASP_RTO_CD = '"+programInfoDO.getScl_cd()+"'");
				}
			}


			//** 사용제한*//*
			if(programInfoDO.getClf_cd().equals("USE_LIMIT")){

				if(programInfoDO.getScl_cd().equals("")){
					buf.append("\n   and (meta.RIST_CLF_CD is  null or meta.RIST_CLF_CD is not null) ");	
				}else{
					buf.append("\n   and meta.RIST_CLF_CD = '"+programInfoDO.getScl_cd()+"'");
				}
			}
		}

		buf.append("\n   and meta.del_dd ='' ");
		buf.append("\n   AND (meta.master_ID NOT IN (  ");
		buf.append("\n    SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM ");
		buf.append("\n   INNER JOIN ( SELECT MASTER_ID FROM METADAT_MST_TBL ");
		buf.append("\n   WHERE DEL_DD IS NULL OR DEL_DD > TS_FMT(CURRENT DATE,'yyyymmdd') or REPLACE(del_dd,' ','')=''  ");
		buf.append("\n   ) PM ON PM.MASTER_ID =RM.PARENT_MASTER_ID  ");
		buf.append("\n    GROUP BY CHILD_MASTER_ID  ) )  ");

		if(!DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n order by meta.brd_dd asc, meta.fm_dt asc ,meta.title asc, CONT.ct_cla asc ");
			buf.append("\n WITH UR	 ");
		}

		//buf.append("\n WITH UR	 ");

		return buf.toString();
	}

	/**
	 * 프로그램 ID에 해당하는 프로그램 정보를 모두 가져온다
	 */
	public static final String selectLastPgmInfolistByPgmIdQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.pgm_id, ");
		buf.append("\n 	pgm.pgm_nm, ");
		buf.append("\n 	pgm.pgm_cd, ");
		buf.append("\n 	meta.epis_no, ");
		buf.append("\n 	meta.brd_dd,   ");	
		buf.append("\n 	master_id   ");	
		buf.append("\n from das.pgm_info_tbl pgm,das.metadat_mst_tbl meta ");
		buf.append("\n where 1=1 ");
		buf.append("\n and pgm.pgm_id=meta.pgm_id ");
		buf.append("\n and pgm.PGM_ID = ? ");
		buf.append("\n  and pgm.use_yn='N' ");
		buf.append("\n order by pgm.pgm_nm desc ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	/**
	 * 프로그램 ID에 대항하는 프로그램 정보를 가져온다
	 * @param pgmID 프로그램 ID
	 * @param brd_dd 방송일
	 */
	public static final String selectLastPgmInfolistByPgmIdQuery2(long pgm_id, String brd_dd)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm_id as pgm_id, ");
		buf.append("\n 	pgm_nm as pgm_nm, ");
		buf.append("\n 	pgm_cd as pgm_cd, ");
		buf.append("\n 		value(epis_no, 0) as epis_no, ");
		buf.append("\n 	brd_dd as brd_dd, ");
		buf.append("\n 	master_id as master_id   ");	
		buf.append("\n 	from (  ");	
		buf.append("\n select  ");
		buf.append("\n  pgm.pgm_id,  ");
		buf.append("\n pgm.pgm_nm, ");
		buf.append("\n pgm.pgm_cd, ");
		buf.append("\n 	value(meta.epis_no , 0)  as epis_no, ");
		buf.append("\n meta.brd_dd,	 ");
		buf.append("\n meta.master_id  ");
		buf.append("\n 	from METADAT_MST_TBL meta ");
		buf.append("\n 	inner join PGM_INFO_TBL pgm on pgm.PGM_ID = meta.PGM_ID ");
		buf.append("\n 	where meta.brd_dd > '"+brd_dd+"' and meta.PGM_ID= "+pgm_id);
		buf.append("\n  and pgm.use_yn='Y' ");
		buf.append("\n  and meta.del_dd ='' ");
		buf.append("\n 	order by meta.brd_dd asc ");
		buf.append("\n 	fetch first 7 rows only   ");	
		buf.append("\n 	) a   ");	
		buf.append("\n 	UNION all ");
		buf.append("\n  select   ");
		buf.append("\n pgm_id as pgm_id, ");
		buf.append("\n 	pgm_nm as pgm_nm, ");
		buf.append("\n pgm_cd as pgm_cd, ");
		buf.append("\n epis_no as epis_no, ");
		buf.append("\n brd_dd as brd_dd,	 ");
		buf.append("\n master_id as master_id");
		buf.append("\n from (");
		buf.append("\n select  ");
		buf.append("\n  pgm.pgm_id, ");
		buf.append("\n pgm.pgm_nm,	 ");
		buf.append("\n pgm.pgm_cd, ");
		buf.append("\n 	value(meta.epis_no , 0)  as epis_no, ");
		buf.append("\n meta.brd_dd, ");
		buf.append("\n meta.master_id   ");
		buf.append("\n from METADAT_MST_TBL meta ");
		buf.append("\n inner join PGM_INFO_TBL pgm on pgm.PGM_ID = meta.PGM_ID   ");	
		buf.append("\n where meta.brd_dd <=  '"+brd_dd+"' and meta.PGM_ID=  "+pgm_id);	
		buf.append("\n  and pgm.use_yn='Y' ");
		buf.append("\n  and meta.del_dd ='' ");
		buf.append("\n order by meta.brd_dd desc ");
		buf.append("\n fetch first 8 rows only  ");
		buf.append("\n )  b ");
		buf.append("\n  ORDER BY BRD_DD desc, epis_no desc ");
		return buf.toString();
	}
	/**
	 * 마스터 ID를 가지는 프로그램 정보를 가져온다
	 */
	public static final String selectPgmInfoFromMasteridQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.pgm_id, ");
		buf.append("\n 	pgm.pgm_nm, ");
		buf.append("\n 	pgm.pgm_cd, ");
		buf.append("\n 	pgm.brd_bgn_dd, ");
		buf.append("\n 	pgm.brd_end_dd,   ");	
		buf.append("\n 	pgm.media_cd,   ");		
		buf.append("\n 	meta.TITLE, ");
		buf.append("\n 	meta.BRD_DD, ");
		buf.append("\n 	meta.FINAL_BRD_YN, ");
		buf.append("\n 	meta.CTGR_L_CD,   ");	
		buf.append("\n 	meta.CTGR_M_CD,   ");		
		buf.append("\n 	meta.CTGR_S_CD, ");
		buf.append("\n 		replace(meta.EPIS_NO, '0', '')  as epis_no, ");
		buf.append("\n 	meta.PRODUCER_NM, ");
		buf.append("\n 	meta.WRITER_NM,   ");	
		buf.append("\n 	meta.DRT_NM,   ");		
		buf.append("\n 	meta.CMR_DRT_NM, ");
		buf.append("\n 	meta.PRDT_DEPT_NM, ");
		buf.append("\n 	meta.PRDT_IN_OUTS_CD,   ");	
		buf.append("\n 	meta.ORG_PRDR_NM,   ");		
		buf.append("\n 	meta.MC_NM,   ");	
		buf.append("\n 	meta.CAST_NM,   ");		
		buf.append("\n 	meta.CMR_PLACE, ");
		buf.append("\n 	meta.KEY_WORDS, ");
		buf.append("\n 	meta.MUSIC_INFO, ");
		buf.append("\n 	meta.SNPS, ");		
		buf.append("\n 	meta.SPC_INFO,   ");	
		buf.append("\n 	meta.VIEW_GR_CD,   ");		
		buf.append("\n 	meta.PGM_RATE, ");
		buf.append("\n 	meta.CPRTR_NM, ");
		buf.append("\n 	meta.CPRT_TYPE, ");
		buf.append("\n 	meta.CPRT_TYPE_DSC, ");
		buf.append("\n 	meta.AWARD_HSTR, ");
		buf.append("\n 	meta.DLBR_CD, ");
		buf.append("\n 	meta.TAPE_MEDIA_CLF_CD, ");
		buf.append("\n 	meta.RERUN, ");
		buf.append("\n 	meta.ARRANGE_NM, ");
		buf.append("\n  cit.RECORD_TYPE_CD ");
		buf.append("\n from das.pgm_info_tbl pgm ");
		buf.append("\n      ,das.metadat_mst_tbl meta ");
		buf.append("\n      ,das.contents_mapp_tbl cmt ");
		buf.append("\n      ,das.contents_inst_tbl cit ");
		buf.append("\n where meta.master_id = ? ");
		buf.append("\n and pgm.pgm_id=meta.pgm_id ");
		buf.append("\n  and cmt.master_id = meta.master_id     ");
		buf.append("\n  and cit.ct_id = cmt.CT_ID    ");
		buf.append("\n  fetch first 1 rows only ");
		buf.append("\n WITH UR	 ");		

		return buf.toString();
	}
	/**
	 * 프로그램에 포함된 콘텐트 정보를 읽어온다
	 */
	public static final String selectPgmContentsInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	con.CT_ID, ");
		buf.append("\n 	con.CT_NM,  ");
		buf.append("\n 	mapp.MASTER_ID,  ");
		buf.append("\n 	inst.FL_PATH, ");
		buf.append("\n 	inst.CTI_ID ");
		buf.append("\n from DAS.METADAT_MST_TBL meta, DAS.CONTENTS_TBL con, DAS.CONTENTS_MAPP_TBL mapp, DAS.CONTENTS_INST_TBL inst ");
		buf.append("\n where mapp.Master_ID = meta.Master_ID ");
		buf.append("\n 	and mapp.CT_ID = con.CT_ID ");
		buf.append("\n 	and con.CT_ID = inst.CT_ID ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '')");
		buf.append("\n 	and meta.Master_ID = ? ");
		buf.append("\n WITH UR	 ");	   

		return buf.toString();
	}
	/**
	 * 주석정보를 조회한다
	 */
	public static final String selectAnnotInfoInfoListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	anot.CN_ID, ");
		buf.append("\n 	anot.ANNOT_ID, ");
		buf.append("\n 	anot.CT_ID, ");
		buf.append("\n 	anot.ANNOT_CLF_CD, ");
		buf.append("\n 	anot.ANNOT_CLF_CONT, ");
		buf.append("\n 	anot.SOM, ");
		buf.append("\n 	anot.GUBUN, ");
		buf.append("\n 	anot.EOM , ");
		buf.append("\n 	anot.ENTIRE_YN  ");
		//buf.append("\n 	CON.RIST_CLF_CD  ");
		buf.append("\n from DAS.corner_tbl cn, DAS.ANNOT_INFO_TBL anot, DAS.CONTENTS_MAPP_TBL mapp, DAS.CONTENTS_TBL CON ");
		buf.append("\n where mapp.CN_ID=cn.CN_ID  ");
		buf.append("\n 	and cn.master_id=mapp.master_id  ");
		buf.append("\n 	and mapp.ct_id=anot.ct_id  ");
		buf.append("\n 	and mapp.ct_id=CON.ct_id  ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n 	and anot.cn_id = cn.CN_ID  ");	// 이렇게 되어야 하는 거 아닌가요? 만약 이거 누군가 수정하려 한다면 김문식에게 말해주세요.
		buf.append("\n 	and  anot.ANNOT_CLF_CD <> '007'  ");
		buf.append("\n 	and mapp.MASTER_ID= ?  order by anot.cn_id asc with ur ");

		return buf.toString();
	}
	/**
	 * 영상정보를 조회한다.
	 */
	public static final String selectReflectionInfoListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.PGM_NM, ");
		buf.append("\n 	meta.TITLE, ");
		buf.append("\n 	meta.BRD_DD, ");
		buf.append("\n 	meta.FINAL_BRD_YN, ");
		buf.append("\n 	pgm.BRD_BGN_DD, ");
		buf.append("\n 	pgm.BRD_END_DD, ");
		buf.append("\n 	meta.TAPE_MEDIA_CLF_CD,  ");
		buf.append("\n 	meta.CTGR_L_CD, ");
		buf.append("\n 	meta.CTGR_M_CD, ");
		buf.append("\n 	meta.CTGR_S_CD, ");
		buf.append("\n 	meta.REQ_CD, ");
		buf.append("\n 	cn.CT_ID, ");
		buf.append("\n 	cn.ASP_RTO_CD, ");
		buf.append("\n 	cn.VD_QLTY, ");
		buf.append("\n 	meta.EPIS_NO, ");
		buf.append("\n 	cn.CT_LENG, ");
		buf.append("\n 	cn.KFRM_PATH, ");
		buf.append("\n 	cnti.FL_PATH  ");
		buf.append("\n from DAS.CONTENTS_TBL cn, DAS.CONTENTS_INST_TBL cnti,  ");
		buf.append("\n 	DAS.CONTENTS_MAPP_TBL mapp, DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL meta  ");
		buf.append("\n where cn.CT_ID=mapp.CT_ID  ");
		buf.append("\n 	and meta.MASTER_ID=mapp.MASTER_ID  ");
		buf.append("\n 	and cnti.CT_ID=mapp.CT_ID  ");
		buf.append("\n 	and pgm.PGM_ID=mapp.PGM_ID  ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n 	and mapp.MASTER_ID=? ");
		buf.append("\n order by cn.CT_SEQ asc with ur ");

		return buf.toString();
	}
	/**
	 * 내용 및 제작정보를 조회한다.
	 */
	public static final String selectContentsInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	meta.PRODUCER_NM, ");
		buf.append("\n 	meta.DRT_NM, ");
		buf.append("\n 	meta.WRITER_NM, ");
		buf.append("\n 	meta.CMR_DRT_NM, ");
		buf.append("\n 	meta.PRDT_DEPT_NM, ");
		buf.append("\n 	meta.ORG_PRDR_NM, ");    
		buf.append("\n 	meta.PRDT_IN_OUTS_CD, ");
		buf.append("\n 	meta.SNPS, ");
		buf.append("\n 	meta.KEY_WORDS, ");
		buf.append("\n 	meta.CAST_NM, ");
		buf.append("\n 	meta.SPC_INFO  ");
		buf.append("\n from DAS.METADAT_MST_TBL meta "); 
		buf.append("\n where meta.MASTER_ID = ? with ur ");

		return buf.toString();
	}
	/**
	 * 컨텐트 미리보기 정보를 조회한다.
	 */
	public static final String selectContentsPreInfoQuery() 
	{						
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cn.CN_ID, ");
		buf.append("\n 	ct.CT_ID, ");
		buf.append("\n 	ct.DURATION, ");
		buf.append("\n 	ct.CT_SEQ ");
		buf.append("\n from DAS.CORNER_TBL cn, DAS.CONTENTS_TBL ct, DAS.CONTENTS_MAPP_TBL mapp  ");
		buf.append("\n where ct.CT_ID=mapp.CT_ID  ");
		buf.append("\n 	and cn.CN_ID = mapp.CN_ID  ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n 	and mapp.MASTER_ID=? ");
		buf.append("\n order by ct.CT_SEQ asc with ur ");

		return buf.toString();
	}

	/*public static final String selectKfrmQuery() 
	{						
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cont.KFRM_PATH ");
		buf.append("\n 	from das.metadat_mst_tbl meta, das.CONTENTS_TBL cont, das.CONTENTS_MAPP_TBL mapp ");
		buf.append("\n 	where meta.master_id = mapp.master_id ");
		buf.append("\n 	and mapp.ct_id = cont.ct_id ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n  with UR  ");

		return buf.toString();
	}
	 */

	/**
	 * 첨부파일을 조회한다.
	 */
	public static final String selectAttachedFileQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	attch.FL_NM, ");
		buf.append("\n 	code.DESC, ");
		buf.append("\n 	attch.FL_SZ, ");
		buf.append("\n 	attch.FL_PATH, ");
		buf.append("\n 	attch.SEQ, ");
		buf.append("\n	attch.ATTC_FILE_TYPE_CD, ");
		buf.append("\n	attch.ATTC_CLF_CD, ");
		buf.append("\n	attch.ORG_FILE_NM ");
		buf.append("\n 	from das.ATTCH_TBL attch, das.CODE_TBL code ");
		buf.append("\n where attch.MOTHR_DATA_ID= ? and  ");
		buf.append("\n code.CLF_CD='P015' and  code.SCL_CD=attch.ATTC_FILE_TYPE_CD ");
		buf.append("\n order by attch.SEQ asc with ur ");

		return buf.toString();	
	}
	/**
	 * 편성/심의 및 저작권/tape정보 조회
	 */
	public static final String selectTapeInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	VIEW_GR_CD, ");
		buf.append("\n 	PGM_RATE, ");
		buf.append("\n 	DLBR_CD, ");
		buf.append("\n 	CPRTR_NM, ");
		buf.append("\n 	CPRT_TYPE, ");
		buf.append("\n 	CPRT_TYPE_DSC  ");
		buf.append("\n 	from das.METADAT_MST_TBL  ");
		buf.append("\n 	where MASTER_ID= ? with ur ");		 

		return buf.toString();
	}
	/**
	 * ct_id의 eom를 조회한다
	 */
	public static final String selectEomByCtId()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	CT_LENG ");
		buf.append("\n 	from CONTENTS_TBL  ");
		buf.append("\n 	where CT_ID= ? with ur ");		 

		return buf.toString();
	}
	/**
	 * ct_id의 화면비 코드를 조회한다
	 */
	public static final String selectAspRtoCdByCtId()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	asp_rto_cd ");
		buf.append("\n 	from CONTENTS_TBL  ");
		buf.append("\n 	where CT_ID= ? with ur ");		 

		return buf.toString();
	}
	/**
	 * ct_id의 Duration를 조회한다
	 */
	public static final String selectDurationByCtId()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	DURATION ");
		buf.append("\n 	from CONTENTS_TBL  ");
		buf.append("\n 	where CT_ID= ? with ur ");		 

		return buf.toString();
	}

	/**
	 * 미디어 정보를 조회한다.
	 */
	public static final String selectMediaInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cnti.AUDIO_YN, ");
		buf.append("\n 	cnti.ME_CD, ");
		buf.append("\n 	cnti.COLOR_CD, ");
		buf.append("\n 	cnti.CTI_FMT, ");
		buf.append("\n 	cnti.REG_DT, ");
		buf.append("\n 	cnti.FL_SZ, ");
		buf.append("\n 	cnti.FRM_PER_SEC, ");
		buf.append("\n 	cnti.BIT_RT, ");
		buf.append("\n 	cnti.RECORD_TYPE_CD, ");
		buf.append("\n 	cnti.AUDIO_BDWT, ");
		buf.append("\n 	cnti.AUD_SAMP_FRQ  ");
		buf.append("\n from DAS.METADAT_MST_TBL meta, DAS.CONTENTS_INST_TBL cnti, DAS.CONTENTS_MAPP_TBL mapp "); 
		buf.append("\n where cnti.CT_ID=mapp.CT_ID  ");
		buf.append("\n 	and meta.MASTER_ID = mapp.MASTER_ID  ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n 	and meta.MASTER_ID= ? ");
		buf.append("\n ORDER BY CT_SEQ  WITH UR ");


		return buf.toString();
	}
	/**
	 * 구분 상세 코드를 조회한다.
	 */
	public static final String selectFLIngestCommonCodeQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT  ");
		buf.append("\n DAS.CODE_TBL.CLF_CD,  ");
		buf.append("\n DAS.CODE_TBL.SCL_CD,  ");
		buf.append("\n DAS.CODE_TBL.CLF_NM,  ");
		buf.append("\n DAS.CODE_TBL.DESC   ");
		buf.append("\n FROM DAS.CODE_TBL  ");
		buf.append("\n WHERE DAS.CODE_TBL.CLF_CD='A046'  ");
		buf.append("\n OR DAS.CODE_TBL.CLF_CD='A047'  ");
		buf.append("\n ORDER BY DAS.CODE_TBL.CLF_CD,DAS.CODE_TBL.SCL_CD  ");
		buf.append("\n WITH UR  ");	

		return buf.toString();
	}
	/**
	 * 구분 상세 코드를 조회한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws DASException
	 */
	public static final String selectTapeOutIngestCommonCodeQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT  ");
		buf.append("\n DAS.CODE_TBL.CLF_CD,  ");
		buf.append("\n DAS.CODE_TBL.SCL_CD,  ");
		buf.append("\n DAS.CODE_TBL.CLF_NM,  ");
		buf.append("\n DAS.CODE_TBL.DESC   ");
		buf.append("\n FROM DAS.CODE_TBL  ");
		buf.append("\n WHERE  ");
		buf.append("\n DAS.CODE_TBL.CLF_CD='A005' OR DAS.CODE_TBL.CLF_CD='A006' OR ");
		buf.append("\n DAS.CODE_TBL.CLF_CD='A046' OR DAS.CODE_TBL.CLF_CD='A047' OR ");
		buf.append("\n DAS.CODE_TBL.CLF_CD='A048' OR  ");
		buf.append("\n DAS.CODE_TBL.CLF_CD='A019' OR DAS.CODE_TBL.CLF_CD='A024'  ");
		buf.append("\n ORDER BY DAS.CODE_TBL.CLF_CD,DAS.CODE_TBL.SCL_CD  ");
		buf.append("\n WITH UR  ");	

		return buf.toString();
	}
	/**
	 * 구분 상세 코드를 조회한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws DASException
	 */
	public static final String selectSDIngestCommonCodeQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT  ");
		buf.append("\n DAS.CODE_TBL.CLF_CD,  ");
		buf.append("\n DAS.CODE_TBL.SCL_CD,  ");
		buf.append("\n DAS.CODE_TBL.CLF_NM,  ");
		buf.append("\n DAS.CODE_TBL.DESC   ");
		buf.append("\n FROM DAS.CODE_TBL  ");
		buf.append("\n WHERE  (DAS.CODE_TBL.CLF_CD='A005' OR DAS.CODE_TBL.CLF_CD='A006'  ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A008' OR DAS.CODE_TBL.CLF_CD='A009'   ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A010' OR DAS.CODE_TBL.CLF_CD='A012' ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A013' OR DAS.CODE_TBL.CLF_CD='A015'    ");
		buf.append("\n   OR   DAS.CODE_TBL.CLF_CD='A021' OR DAS.CODE_TBL.CLF_CD='A024'  ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A046' OR DAS.CODE_TBL.CLF_CD='A047'     ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A048' OR DAS.CODE_TBL.CLF_CD='A049'     ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A043' OR DAS.CODE_TBL.CLF_CD='P011'  ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='P080' OR   DAS.CODE_TBL.CLF_CD='P064' ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A001'  ");
		buf.append("\n  OR   DAS.CODE_TBL.CLF_CD='A002' OR   DAS.CODE_TBL.CLF_CD='P017' ");
		buf.append("\n  OR   (DAS.CODE_TBL.CLF_CD='P018' AND DAS.CODE_TBL.GUBUN='L') ) ");
		buf.append("\n  AND DAS.CODE_TBL.USE_YN ='Y' ");
		buf.append("\n ORDER BY DAS.CODE_TBL.CLF_CD,DAS.CODE_TBL.SCL_CD DESC  ");
		buf.append("\n WITH UR  ");

		return buf.toString();
	}
	/**
	 * 장비 정보를 조회한다.
	 */
	public static final String selectIngestServerQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT DISTINCT ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_ID,  ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_NM,  ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_CLF_CD,  ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_SEQ,    ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_USE_IP,   ");
		buf.append("\n DAS.DAS_EQUIPMENT_TBL.DAS_EQ_USE_PORT    ");
		buf.append("\n FROM DAS.DAS_EQUIPMENT_TBL  ");
		buf.append("\n WHERE DAS.DAS_EQUIPMENT_TBL.DAS_EQ_CLF_CD= ?  ");
		buf.append("\n ORDER BY DAS.DAS_EQUIPMENT_TBL.DAS_EQ_SEQ  ");
		buf.append("\n WITH UR  ");		

		return buf.toString();
	}

	/**
	 * 메타데이터 검색결과의 갯수를 가져온다.
	 */
	@Deprecated
	public static final String selectMetadatInfoQuery(WorkStatusConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();

		String[] McuidYn =  conditionDO.getMcuidYn().split(",");
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		if (conditionDO.getQueryResultCount() == false)
		{

			buf.append(" \n select * from (");
		}
		else{
			buf.append("\n select count(*) as CCOUNT ,bigint(sum(sum_brd_leng)*29.97)  as sum_brd_leng from ( ");
		}
		buf.append("\n select ");
		buf.append("\n 	A.master_id, ");
		buf.append("\n 	A.tape_item_id, ");
		buf.append("\n 	A.mcuid, ");
		buf.append("\n 	A.data_stat_cd as data_stat_cd, ");
		buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
		buf.append("\n 	A.error_stat_cd as error_stat_cd, ");
		buf.append("\n 	C.clf_nm as clf_nm, ");
		buf.append("\n 	C.desc as desc, ");
		buf.append("\n 	case when (a.ctgr_l_cd='200') and ( a.pgm_id <> 0 ) then pgm.pgm_nm ");
		buf.append("\n 	when (a.ctgr_l_cd='200') and (a.pgm_id =0 or a.pgm_id is null) then a.title ");
		buf.append("\n 	when a.ctgr_l_cd='100' then a.title ");
		buf.append("\n 	else '' ");
		buf.append("\n  end as title, ");
		buf.append("\n 	A.modrid as modrid, ");
		buf.append("\n 	A.reg_dt as reg_dt, ");
		buf.append("\n 	A.req_cd as req_cd, ");
		buf.append("\n 	A.brd_leng as brd_leng, ");
		buf.append("\n 	A.brd_dd as brd_dd, ");
		buf.append("\n 	A.epis_no as epis_no, ");
		buf.append("\n 	A.ing_reg_dd as ing_reg_dd, ");
		buf.append("\n 	A.arch_reg_dd as arch_reg_dd, ");			
		buf.append("\n 	B.COUNT, ");
		buf.append("\n 	DECODE(RMP.PARENT_MASTER_ID, NULL, 'N','Y') AS LINK_PARENT, ");
		if (!conditionDO.getQueryResultCount() == false)
		{
			buf.append("\n  CASE "); 
			buf.append("\n  when A.BRD_LENG is not null and A.BRD_LENG <> '' "); 
			buf.append("\n 	  THEN bigint(SUBSTR(A.BRD_LENG,1,2))*60*60+bigint(substr(A.BRD_LENG,4,2))*60+bigint(substr(A.BRD_LENG,7,2)) +bigint(substr(A.BRD_LENG,10,2)/29.97)"); 
			buf.append("\n  ELSE 0 ");
			buf.append("\n END SUM_BRD_LENG, ");
		}
		buf.append("\n 	ROW_NUMBER() OVER(ORDER BY A.master_id) AS rownum ");	
		buf.append("\n from (SELECT * FROM das.metadat_mst_tbl  WHERE 1=1   ");	

		if(!conditionDO.getMcuidYn().equals("")){
			buf.append("\n 	 AND ( ");
			for(int i =0; i<McuidYn.length;i++){
				if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
				{
					// mcuidYn=N 매체변환
					buf.append("\n 	 arch_route LIKE 'D%' ");
				}
				else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");

				}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");

				}
			}
			buf.append("\n 	 ) ");
		}
		buf.append("\n  ) A ");



		buf.append("\n LEFT OUTER JOIN (SELECT CT_ID,MASTER_ID FROM CONTENTS_MAPP_TBL GROUP BY CT_ID ,MASTER_ID )   MAP ON MAP.MASTER_ID=A.MASTER_ID and  map.CT_ID =a.RPIMG_CT_ID ");	
		buf.append("\n  inner JOIN  CONTENTS_TBL CON  ON CON.CT_ID=MAP.CT_ID AND CON.CT_TYP = '003'   ");	
		buf.append("\n  LEFT OUTER JOIN USER_INFO_TBL USER2 ON USER2.SBS_USER_ID=A.MODRID   ");	
		buf.append("\n  LEFT OUTER JOIN USER_INFO_TBL USER3 ON USER3.SBS_USER_ID=A.ACCEPTOR_ID   ");	
		buf.append("\n inner JOIN contents_inst_tbl inst ON inst.CT_id = map.CT_ID and cti_fmt like '%10%'  ");	
		buf.append("\n left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = a.PGM_ID  ");	
		buf.append("\n left outer join DAS.RELATION_MASTER as rmp on a.master_id = rmp.PARENT_MASTER_ID  ");	


		//	buf.append("\n     LEFT OUTER JOIN AUTO_ARCHVIE_TBL AUTO ON AUTO.SCL_CD =  CON.ct_cla  ");	

		buf.append("\n inner  JOIN ( select z.master_id, count(*) as COUNT   ");
		buf.append("\n from (select distinct master_id from das.contents_mapp_tbl mapp where ( mapp.del_dd = ''  or mapp.del_dd is null)) z    ");
		buf.append("\n  group by z.master_id) B ON A.master_id = B.master_id  ");

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())
				||DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			buf.append("\n INNER JOIN (      ");
			buf.append("\n  select distinct MAP.MASTER_ID, CT.VD_QLTY, CT.ASP_RTO_CD ");
			buf.append("\n  from DAS.CONTENTS_TBL CT, ");
			buf.append("\n  DAS.CONTENTS_MAPP_TBL MAP   ");
			buf.append("\n  where   	MAP.CT_ID = CT.CT_ID  ");
			buf.append("\n  and ( MAP.del_dd = '')    ");
			buf.append("\n  )  CTT on CTT.master_id = A.master_id ");
		}

		buf.append("\n  LEFT OUTER JOIN CODE_TBL C ON C.SCL_CD =  A.data_stat_cD AND C.clf_cd = 'P051'  ");

		/*		// 검수자 이름으로 검색한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)			
				buf.append(" , DAS.ERP_COM_USER_TBL USER ");
		}*/

		buf.append("\n where ");
		buf.append("\n 		 ");	
		buf.append("\n 		value(a.del_dd,'')='' ");
		buf.append("\n  AND (a.manual_yn='N' OR a.manual_yn IS NULL )");

		//		buf.append("\n 		AND (A.master_ID NOT IN (SELECT CHILD_MASTER_ID FROM RELATION_MASTER GROUP BY CHILD_MASTER_ID) ) ");
		/**
		 * 영상 삭제 또는 폐기시에 금일 기준으로 삭제가 되지 않은 마스터에 관련영상 정보와 조인하여 해당 마스터의 차일드 마스터를
		 * 리스트 상에 제외 시켜준다(2011.05.20 by Dekim) 삭제스케줄러가 클립단위로 삭제되기에 해당 마스터건에 대해서 판단하기가 곤란함. 
		 */
		buf.append("\n 		AND (A.master_ID NOT IN ( ");
		buf.append("\n            SELECT CHILD_MASTER_ID FROM RELATION_MASTER   ");
		 
		buf.append("\n      ) ) ");

		buf.append("\n  AND ((  (  a.ARCH_REG_DD <>'')  ");
		buf.append("\n        OR(A.MANUAL_YN='Y' AND INST.ARCH_STE_YN='Y')  ");
		buf.append("\n        or((A.MANUAL_YN='Y' AND con.MEDIA_ID like 'D%'))");
		buf.append("\n        OR (A.ARCH_ROUTE LIKE 'P%'))  ");
		buf.append("\n        or (  (  a.ARCH_REG_DD <>'')  ");
		buf.append("\n        OR(A.MANUAL_YN='Y' AND INST.ARCH_STE_YN='Y') ");
		buf.append("\n        or((A.MANUAL_YN='Y' AND con.MEDIA_ID like 'D%')) ");
		buf.append("\n         or((A.ARCH_ROUTE LIKE 'D%' AND A.MANUAL_YN='N')))   ");
		buf.append("\n       ) ");


		if(!conditionDO.getCtgr_l_cd().equals("")){
			// 100: 소재  200: 프로그램  
			if(conditionDO.getCtgr_l_cd().equals("200")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
			}else if(!conditionDO.getCtgr_l_cd().equals("")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD <> '200' ");
			}
			//buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
		}


		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  CTT.ASP_RTO_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (CTT.ASP_RTO_CD <> '' OR CTT.ASP_RTO_CD IS NULL  )");	
			}
		}
		if(DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n 	AND CTT.VD_QLTY = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n 	AND (CTT.VD_QLTY <> '' OR CTT.VD_QLTY IS NULL) ");	
			}
		}
		/*if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
		}*/
		if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.rist_clf_Cd = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.VIEW_GR_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.VIEW_GR_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.VIEW_GR_CD <> '' OR A.VIEW_GR_CD IS NULL or A.VIEW_GR_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CPRT_TYPE.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.CPRT_TYPE = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.CPRT_TYPE <> '' OR A.CPRT_TYPE IS NULL or A.CPRT_TYPE = '')");	
			}
		}
		if(DASBusinessConstants.SearchCombo.RSV_PRD_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (A.RSV_PRD_CD <> '' OR A.RSV_PRD_CD IS NULL   or A.RSV_PRD_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CT_CLA.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  CON.CT_CLA= '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (CON.CT_CLA <> '' OR CON.CT_CLA IS NULL  or CON.CT_CLA = '') ");
			}
		}


		if(DASBusinessConstants.SearchCombo.TAPE_KIND.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.TAPE_MEDIA_CLF_CD= '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  ( A.TAPE_MEDIA_CLF_CD <> '' OR  A.TAPE_MEDIA_CLF_CD IS NULL  or  A.TAPE_MEDIA_CLF_CD = '') ");
			}
		}

		String[] dateGb = conditionDO.getDateKind().split(",");
		for(int i=0; i < dateGb.length; i++){
			//		등록일 검색
			buf.append("\n  AND ");

			if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n  ( SUBSTR(A.ING_REG_DD,1,8) >= '"+conditionDO.getFromDate()+"'   ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	AND   SUBSTR(A.ING_REG_DD,1,8) <= '"+conditionDO.getToDate()+"' )");
				}
			}

			if(CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	(( A.fm_dt >= '"+conditionDO.getFromDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.fm_dt <= '"+conditionDO.getToDate()+"' ) ");
				}

				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	or ( A.brd_dd >= '"+conditionDO.getFromDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.brd_dd <= '"+conditionDO.getToDate()+"' ) )");
				}
			}	
			if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n ( A.arrg_end_dt >= '"+conditionDO.getFromDate()+"000000' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.arrg_end_dt <= '"+conditionDO.getToDate()+"235959') ");
				}
			}
			if(CodeConstants.WorkStatusDateFlag.ACCEPT_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	(A.accept_end_dd >= '"+conditionDO.getFromDate()+"000000' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.accept_end_dd <= '"+conditionDO.getToDate()+"235959') ");
				}
			}				


		}
		//		
		//		if(!conditionDO.getMcuidYn().equals("")){
		//			buf.append("\n 	 AND (   ");
		//			for(int i =0; i<McuidYn.length;i++){
		//			if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
		//			{
		//				// mcuidYn=N 매체변환
		//			//	buf.append("\n 	 (A.mcuid is null or A.mcuid = '') ");
		//				buf.append("\n 	 A.arch_route LIKE 'D%' ");
		//			}
		//			else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
		//			{   // mcuidYn=Y 온에어(주조)
		//				if(i!=0){
		//					buf.append("\n 	 or  ");
		//				}
		//				buf.append("\n 	 ( ");
		//				buf.append("\n  A.arch_route LIKE 'O%' ");
		//				buf.append("\n 	 ) ");
		//				
		//			}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
		//				//PDS
		//				if(i!=0){
		//					buf.append("\n 	 or  ");
		//				}
		//				buf.append("\n 	( A.arch_route ='P' )");
		//		
		//			}
		//			}
		//			buf.append("\n 	 ) ");
		//		}



		boolean isDateCondition = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()) 			
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))	
		{
			isDateCondition = true;
		}

		if(isDateCondition)
		{
			buf.append("\n 	AND ( ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");

			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()))
		{
			if(firstKind)
			{
				// 오류를 uncheck하고 정리전으로만 검색해도 오류인 정리전 자료도 모두 검색된다(상태가 오류로 표시되더라도) (2008.06.19. 김건학실장)
				//buf.append(" or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n or A.DATA_STAT_CD = '009' ");
			}
			else
			{
				//buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n A.DATA_STAT_CD  = '009' ");
				firstKind = true;
			}
		}


		if(isDateCondition)
		{
			buf.append(")  ");
		}

		// 검색어를 입력한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_NM") == true)			
				buf.append("\n and a.SEC_ARCH_NM like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_ID") == true)			
				buf.append("\n and a.SEC_ARCH_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("REQ_CD") == true)			
				buf.append("\n and a.REQ_CD like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("TITLE") == true)	{		

				buf.append("\n and ((A.TITLE like '%" + conditionDO.getSearchKey() + "%'  )");
				buf.append("\n or (pgm.pgm_nm like '%" + conditionDO.getSearchKey() + "%'   ))");


			}

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_ID") == true)			
				buf.append("\n and a.ACCEPTOR_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)
			{

				buf.append("\n and USER3.USER_NM like '%" + conditionDO.getSearchKey() + "%'");
			}

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("MASTER_ID") == true)			
				buf.append("\n and A.MASTER_ID = " + conditionDO.getSearchKey() + " ");
		}

		if(!conditionDO.getArchiveYn().equals("")){
			if(conditionDO.getArchiveYn().equals("Y")){
				buf.append(" AND value(A.ARCH_REG_DD,'') <>'' ");	
			}else{
				buf.append(" AND value(A.ARCH_REG_DD,'') ='' ");	
			}
		}
		//2012.4.18 채널, 회사 조건  추가
		if(!conditionDO.getCocd().equals("")){

			buf.append(" AND A.cocd= '" +conditionDO.getCocd()+"'");	

		}

		if(!conditionDO.getChennel().equals("")){
			buf.append(" AND A.chennel_cd= '" +conditionDO.getChennel()+"'");	
		}
		if(conditionDO.getSortColume().length()>0){
			buf.append("\n order by "+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}
		if (conditionDO.getQueryResultCount() == false)
		{
			// rownum 으로 갯수를 제한하는 경우라면 
			if ( (conditionDO.getStartPos() > 0) && (conditionDO.getEndPos() > 0) )
			{
				buf.append(" \n ) as t ");
				buf.append("\n where rownum between " );
				buf.append(conditionDO.getStartPos() );
				buf.append(" and " );
				buf.append(conditionDO.getEndPos() );
			}
		}
		else	// 갯수만 가져오는 경우라면 
			buf.append(" \n ) as t ");

		buf.append("\n with ur");

		return buf.toString();		

	}

	/**
	 * 프로그램 정보를 조회한다.(das 1.0 소스 현재 사용하지 않음)
	 */
	public static final String selectVideoPageInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.pgm_id, ");
		buf.append("\n 	pgm.pgm_cd, ");
		buf.append("\n 	pgm.pgm_nm, ");
		buf.append("\n 	pgm.brd_bgn_dd, ");
		buf.append("\n 	pgm.brd_end_dd, ");
		buf.append("\n 	pgm.media_cd ");
		buf.append("\n 	from das.pgm_info_tbl pgm, das.metadat_mst_tbl meta ");
		buf.append("\n 	where meta.pgm_id=pgm.pgm_id and meta.master_id = ?  with ur");

		return buf.toString();
	}	
	/**
	 * 프로그램 컨텐트 정보를 조회한다.
	 */
	public static final String selectVideoPageContentInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cn.CT_ID, ");
		buf.append("\n 	cn.ASP_RTO_CD, ");
		buf.append("\n 	cn.VD_QLTY, ");
		buf.append("\n 	cn.CT_LENG ");
		buf.append("\n 	from das.CONTENTS_TBL cn,das.contents_mapp_tbl mapp  ");
		//buf.append("\n 	from das.CONTENTS_TBL cn,das.contents_mapp_tbl mapp");
		buf.append("\n 	where mapp.master_id = ?  and cn.CT_ID + 0 = mapp.CT_ID  " );
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n with ur");	

		return buf.toString();
	}
	/**
	 * 비디오 메타 정보를 조회한다.
	 */
	public static final String selectVideoPageMetaInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	TITLE, ");
		buf.append("\n 	BRD_DD, ");
		buf.append("\n 	FINAL_BRD_YN, ");
		buf.append("\n 	CTGR_L_CD, ");
		buf.append("\n 	CTGR_M_CD,");
		buf.append("\n 	CTGR_S_CD,  ");		
		buf.append("\n 	EPIS_NO,  ");
		buf.append("\n 	REQ_CD  ");
		buf.append("\n 	from das.METADAT_MST_TBL   ");
		buf.append("\n 	where MASTER_ID= ? with ur");	

		return buf.toString();
	}
	/**
	 * 관리 정보를 조회한다.
	 */
	public static final String selectManagementInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	meta.ARRG_END_DT, ");
		buf.append("\n 	meta.GATH_CO_CD, ");
		buf.append("\n 	cnt.DATA_STAT_CD, ");
		buf.append("\n 	meta.SEC_ARCH_NM,  ");
		buf.append("\n 	meta.ARCH_REG_DD,  ");
		buf.append("\n 	meta.RSV_PRD_CD  ");		
		buf.append("\n from DAS.METADAT_MST_TBL meta, DAS.CONTENTS_TBL cnt, DAS.CONTENTS_MAPP_TBL mapp  ");
		buf.append("\n where cnt.CT_ID=mapp.CT_ID  ");		
		buf.append("\n 	and meta.MASTER_ID = mapp.MASTER_ID  ");
		buf.append("\n  and (mapp.del_dd is null or mapp.del_dd = '') ");
		buf.append("\n 	and meta.MASTER_ID= ? with ur ");		

		return buf.toString();
	}
	/**
	 * 사진정보를 조회한다.
	 */
	public static String selectPhotoInfoListQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	phot.GATH_CO_CD, ");
		buf.append("\n 	phot.GATH_CLF_CD, ");
		buf.append("\n 	phot.MEDIA_COLOR_INFO, ");
		buf.append("\n 	phot.GATH_DD, ");
		buf.append("\n 	phot.PHOT_REG_ID, ");
		buf.append("\n 	phot.CONT, ");
		buf.append("\n 	phot.SPC_INFO,  ");
		buf.append("\n 	phot.PHOT_CLF_CD,  ");
		buf.append("\n 	pgmphot.SEQ, ");
		buf.append("\n 	pgmphot.BGN_EPN,  ");
		buf.append("\n 	pgmphot.END_EPN,  ");
		buf.append("\n  pgmphot.PGM_ID ");
		buf.append("\n from DAS.PGM_PHOT_INFO_TBL pgmphot, DAS.PHOT_TBL phot "); 
		buf.append("\n where pgmphot.PGM_ID = (select pgm_id from DAS.METADAT_MST_TBL where master_id = ?)   ");
		buf.append("\n 	and phot.phot_reg_id = pgmphot.phot_id with ur ");

		return buf.toString();
	}
	/**
	 * 코너 대표화면 정보를 조회한다.
	 */
	public static String selectCornerHeaderImgInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	kfm.KFRM_SEQ, "); 
		buf.append("\n 	kfm.TIME_CD "); 
		buf.append("\n from das.corner_tbl cn, das.contents_tbl ct, das.keyframe_tbl kfm  ");
		buf.append("\n where ct.CT_ID = kfm.CT_ID  ");
		buf.append("\n 	and cn.RPIMG_KFRM_SEQ = kfm.kfrm_seq  ");
		buf.append("\n 	and ct.ct_id = ? ");
		buf.append("\n 	and cn.cn_id= ?  ");
		buf.append("\n 	and ct.ct_id = cn.rpimg_ct_id  ");       
		buf.append("\n with ur ");

		return buf.toString();

	}
	/**
	 * 클립 대표화면 정보를 조회한다.
	 */
	public static String selectClipHeaderImgInfoQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	rpimg_ct_id, "); 
		buf.append("\n 	rpimg_kfrm_seq   ");
		buf.append("\n from das.metadat_mst_tbl where master_id = ?  ");
		buf.append("\n with ur ");

		return buf.toString();		
	}
	/**
	 * 에러정보를 등록한다
	 * @param con 커넥션
	 * @param errorRegisterDO 정보가담겨있는 beans

	 * @throws DASException
	 */
	public static final String insertErrorRegInfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ERROR_RGST_TBL( ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	WRT,  ");
		buf.append("\n 	WORK_CLF,  ");
		buf.append("\n 	ER_CONT,  ");
		buf.append("\n 	REACT_CONT,  ");
		buf.append("\n 	WORK_SEQ,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	REG_DT  ");	
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?) ");

		return buf.toString();
	}
	/**
	 * 콘텐트 멥 정보를 저장한다.
	 */
	public static final String insertContentsMappinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.CONTENTS_MAPP_TBL( ");
		buf.append("\n 	CT_ID,  ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	CN_ID,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	S_DURATION,  ");
		buf.append("\n 	E_DURATION,  ");
		buf.append("\n 	CN_SEQ,  ");
		buf.append("\n 	CT_SEQ,  ");
		buf.append("\n 	del_dd  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ");

		return buf.toString();
	}
	/**
	 * 메타데이타 마스터의 클립 정보를 갱신한다.
	 */
	public static final String updateClipRepinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.metadat_mst_tbl set ");
		buf.append("\n 	RPIMG_KFRM_SEQ= ?,  ");
		buf.append("\n 	RPIMG_CT_ID= ?  ");
		buf.append("\n 	where master_id= ? ");		

		return buf.toString();
	}
	/**
	 * 코너 테이블의 대표화면 정보를 갱신한다.
	 */
	public static final String updateCornerRepinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.corner_tbl set ");
		buf.append("\n 	RPIMG_KFRM_SEQ = ?,  ");
		buf.append("\n 	RPIMG_CT_ID = ?  ");
		buf.append("\n 	where cn_id = ? ");		

		return buf.toString();
	}
	/**
	 * 콘텐트 멥테이블의 시작/종료 duraiton을 갱신한다.
	 */
	public static final String updateCornerDurationQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.contents_mapp_tbl set ");
		buf.append("\n 	s_duration = ?,  ");
		buf.append("\n 	e_duration = ?  ");
		buf.append("\n 	where master_id = ? ");
		buf.append("\n 	and cn_id = ? ");
		buf.append("\n 	and ct_id = ? ");

		return buf.toString();
	}
	/**
	 * 코너 정보를 저장한다.
	 */
	public static final String insertCornerinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.CORNER_TBL( ");
		buf.append("\n 	CN_ID,  ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	CN_NM,  ");
		buf.append("\n 	CN_TYPE_CD,  ");
		buf.append("\n 	SOM,  ");
		buf.append("\n 	EOM,  ");
		buf.append("\n 	CN_INFO,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	RPIMG_CT_ID,  ");
		buf.append("\n 	RPIMG_KFRM_SEQ,  ");	
		buf.append("\n 	DURATION,  ");
		buf.append("\n 	S_FRAME  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");		

		return buf.toString();
	}
	/**
	 * 주석정보를 저장한다.
	 */
	public static final String insertAnnotinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.ANNOT_INFO_TBL( ");
		buf.append("\n 	ANNOT_ID,  ");
		buf.append("\n 	CN_ID,  ");
		buf.append("\n 	CT_ID,  ");
		buf.append("\n 	ANNOT_CLF_CD,  ");
		buf.append("\n 	ANNOT_CLF_CONT,  ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	SOM,  ");
		buf.append("\n 	EOM,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	DURATION,  ");
		buf.append("\n 	S_FRAME,  ");
		buf.append("\n  GUBUN, ");
		buf.append("\n  entire_yn) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");		

		return buf.toString();
	}

	/*	public static final String insertReqNoHistoryQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAT.D_REQNOHIS_TBL( ");
		buf.append("\n 	REQ_NO,  ");
		buf.append("\n 	BGN_DT,  ");
		buf.append("\n 	END_DT,  ");
		buf.append("\n 	REQ_CLF,  ");
		buf.append("\n 	TAPE_ID,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGR  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?) ");		

		return buf.toString();		
	}*/
	/**
	 * 첨부 파일 정보를 저장한다.
	 */
	public static final String insertAttachFileQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	SEQ,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_SZ,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	ORG_FILE_NM  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		return buf.toString();
	}

	/**
	 * 사진 정보를 저장한다.
	 */
	public static final String insertPhotoInfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.phot_tbl( ");
		buf.append("\n 	phot_reg_id,  ");
		buf.append("\n 	fl_path ,  ");
		buf.append("\n 	org_file_nm ,  ");
		buf.append("\n 	int_file_nm ,  ");
		buf.append("\n 	cont,  ");
		buf.append("\n 	resolution ,  ");
		buf.append("\n 	CPRTR_NM ,  ");
		buf.append("\n 	down_yn,   ");
		buf.append("\n 	fl_sz ,  ");
		buf.append("\n 	REGRID ,  ");
		buf.append("\n 	SUBJ,   ");
		buf.append("\n 	REG_DT   ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?,?,?,?,?,?,CURRENT_DATE) ");

		return buf.toString();
	}
	/**
	 * 사진 정보를 저장한다.
	 */
	public static final String insertPgmPhotoInfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.pgm_phot_info_tbl( ");
		buf.append("\n 	SEQ,  ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	phot_id,  ");		
		buf.append("\n 	reg_dt,  ");	
		buf.append("\n 	REGRID,  ");	
		buf.append("\n 	TITLE,  ");	
		buf.append("\n 	del_yn  ");	
		buf.append("\n ) ");
		buf.append("\n values(?, ?,?,?,?,?,?) ");		

		return buf.toString();
	}
	/**
	 * 메타타이타 정보를 갱신한다.
	 */
	public static final String updateMetadatQuery(String ctgr_l_cd,String tape_id, String tape_item_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.METADAT_MST_TBL set ");
		if(ctgr_l_cd.equals("200")){
			buf.append("\n 	BRD_DD= ?,  ");
			buf.append("\n 	TITLE= ?,  ");
		}else {
			buf.append("\n 	FM_DT= ?,  ");
			buf.append("\n 	TITLE= ?,  ");
		}
		buf.append("\n 	SUB_TTL= ?,  ");
		buf.append("\n 	RERUN= ?,  ");		
		buf.append("\n 	FINAL_BRD_YN= ?,  ");
		buf.append("\n 	CTGR_L_CD= ?,  ");
		buf.append("\n 	CTGR_M_CD= ?,  ");
		buf.append("\n 	CTGR_S_CD= ?,  ");
		buf.append("\n 	EPIS_NO= ?,  ");
		buf.append("\n 	PGM_CD= ?,  ");
		buf.append("\n 	PGM_ID= ?,  ");
		buf.append("\n 	PRODUCER_NM= ?,  ");
		buf.append("\n 	WRITER_NM= ?,  ");
		buf.append("\n 	DRT_NM= ?,  ");
		buf.append("\n 	CMR_DRT_NM= ?,  ");
		buf.append("\n 	PRDT_DEPT_NM= ?,  ");
		buf.append("\n 	PRDT_DEPT_cd= ?,  ");
		buf.append("\n 	PRDT_IN_OUTS_CD= ?,  ");
		buf.append("\n 	ORG_PRDR_NM= ?,  ");
		buf.append("\n 	MC_NM= ?,  ");
		buf.append("\n 	CAST_NM= ?,  ");
		buf.append("\n 	CMR_PLACE= ?,  ");
		buf.append("\n 	MUSIC_INFO= ?,  ");
		buf.append("\n 	SNPS= ?,  ");
		buf.append("\n 	KEY_WORDS= ?,  ");
		buf.append("\n 	SPC_INFO= ?,  ");
		buf.append("\n 	VIEW_GR_CD= ?,  ");
		buf.append("\n 	PGM_RATE= ?,  ");
		buf.append("\n 	DLBR_CD= ?,  ");
		buf.append("\n 	CPRT_TYPE= ?,  ");
		buf.append("\n 	CPRTR_NM= ?,  ");
		buf.append("\n 	CPRT_TYPE_DSC= ?,  ");
		buf.append("\n 	AWARD_HSTR= ?,  ");
		buf.append("\n 	TAPE_MEDIA_CLF_CD= ?,  ");
		buf.append("\n 	GATH_CO_CD= ?,  ");
		buf.append("\n 	RSV_PRD_CD= ?,  ");
		buf.append("\n 	RSV_PRD_END_DD= ?,  ");
		buf.append("\n 	RPIMG_KFRM_SEQ= ?,  ");
		buf.append("\n 	RPIMG_CT_ID= ?,  ");
		buf.append("\n 	ARRANGE_NM = ?,  ");
		buf.append("\n  REQ_CD = ? , ");
		//buf.append("\n  BRD_LENG = ? , ");


		//추가수정분(10.11.18)

		buf.append("\n 	RIST_CLF_CD= ?,  ");		
		//	buf.append("\n 	BRD_END_HMS= ?,  ");

		//	buf.append("\n 	SEC_ARCH_NM= ?,  ");
		//	buf.append("\n 	SEC_ARCH_ID= ?,  ");
		buf.append("\n 	GATH_CLF_CD= ?,  ");
		//	buf.append("\n 	ARCH_REG_DD= ?,  ");
		//	buf.append("\n 	ARRG_END_DT= ?,  ");
		buf.append("\n 	WORK_PRIO_CD= ?,  ");
		buf.append("\n 	DEL_DD= ?,  ");
		//	buf.append("\n 	USE_YN= ?,  ");
		buf.append("\n 	GATH_DEPT_CD= ?,  ");
		//	buf.append("\n 	MCUID= ?,  ");		
		//	buf.append("\n 	DATA_STAT_CD= ?,  ");
		//	buf.append("\n 	ING_REG_DD= ?,  ");
		buf.append("\n 	COPY_KEEP= ?,  ");
		buf.append("\n 	CLEAN_KEEP= ?,  ");
		buf.append("\n 	RST_CONT= ? , ");	
		//	buf.append("\n 	ACCEPTOR_ID= ?  ");
		buf.append("\n 	PDS_CMS_PGM_ID= ?,  ");	
		buf.append("\n  BRD_LENG = ? , ");
		buf.append("\n 	ARTIST= ?,  ");	
		buf.append("\n  COUNTRY_CD = ?  ");

		//buf.append("\n  CHENNEL_CD = ?  ");

		if (tape_id.trim().length() > 0){
			buf.append("\n  , tape_id = ? ");
		}
		if (tape_item_id.trim().length() > 0){
			buf.append("\n  , tape_item_id = ? ");
		}

		buf.append("\n 	where MASTER_ID=  ?  ");

		return buf.toString();

	}
	/**
	 * 콘텐트 멥 정보를 저장한다.
	 */
	public static final String insertMappinfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.CONTENTS_MAPP_TBL( ");
		buf.append("\n 	CT_SEQ,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	S_DURATION,  ");
		buf.append("\n 	E_DURATION,  ");
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	CT_ID,  ");
		buf.append("\n 	PGM_ID,  ");		
		buf.append("\n 	CN_ID,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MODRID,  ");
		buf.append("\n 	CN_SEQ  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		return buf.toString();
	}





	/**
	 * 등록사진  조회한다(팝업).
	 * @param photoInfoDO                                                                                                                        
	 * @param searchFlag        조건          
	 */
	public static String selecAttachPhotoList(PhotoInfoDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{

			buf.append("\n 	 master_id, title, brd_dd, reg_dt, epis_no,"); 

			buf.append("\n 	ROW_NUMBER() OVER(order by brd_dd) AS rownum ");
		}
		buf.append("\n from METADAT_MST_TBL  ");	


		if(!condition.getTitle().equals("") || !condition.getStart_brd_dd().equals("") || !condition.getEnd_brd_dd().equals("") || condition.getEpis()!=0 ){
			buf.append("\n where    ");	

			if(!condition.getTitle().equals("")){
				buf.append("\n title like  '%"+condition.getTitle()+"%'");	
			}

			if( !condition.getStart_brd_dd().equals("")){
				if(!condition.getTitle().equals("")){ buf.append("\n  and   "); }
				buf.append("\n  brd_dd >=  '"+condition.getStart_brd_dd()+"'  ");

			}
			if( !condition.getEnd_brd_dd().equals("")){
				if(!condition.getTitle().equals("")||!condition.getStart_brd_dd().equals("")){ buf.append("\n  and   "); }		
				buf.append("\n  brd_dd  <=  '"+condition.getEnd_brd_dd()+"'  ");		
			}  

			if( condition.getEpis()!=0){
				if(!condition.getTitle().equals("")||!condition.getStart_brd_dd().equals("")||!condition.getEnd_brd_dd().equals("")){ buf.append("\n  and   "); }
				buf.append("\n EPIS_NO  =   "+condition.getEpis());
			}







		}
		return buf.toString();
	}




	/**
	 * 등록사진 텝을 조회한다.
	 * @param master_id 마스터id                                                                                                                        
	 *  @param searchFlag 조건                                                                                                                  
	 * @throws DASException
	 */
	public static String selecPhotoList(int master_id, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{

			buf.append("\n 	PHOT.CONT, PHOT.RESOLUTION, PHOT.CPRTR_NM , PHOT.DOWN_YN, PHOT.FL_SZ,  "); 
			buf.append("\n 	PGM.MASTER_ID, PHOT.PHOT_REG_ID,  PHOT.fl_path,phot.org_file_nm ,"); 

			buf.append("\n 	ROW_NUMBER() OVER(order by PHOT.PHOT_REG_ID) AS rownum ");
		}
		buf.append("\n  FROM PHOT_TBL PHOT   ");
		buf.append("\n   LEFT OUTER JOIN PGM_PHOT_INFO_TBL PGM   ");	
		buf.append("\n   ON PHOT.PHOT_REG_ID=PGM.PHOT_ID  ");	
		buf.append("\n   WHERE PGM.MASTER_ID = " + master_id);	
		buf.append("\n   and (del_yn <> 'Y' or del_yn is null) ");	

		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{

		}else{
			buf.append("\n   order by phot.phot_reg_id desc   ");
		}
		return buf.toString();
	}




	/*public static String selecAddTaskList(String Task_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


			buf.append("\n 	*  "); 

		buf.append("\n  FROM  ARIEL_INFO_TBL  ");

		buf.append("\n   WHERE TASK_ID = " + Task_id);	


		return buf.toString();
}*/

	public static final String selectPreProcessingQuery(PreProcessingDO preProcessingDO)
	{
		StringBuffer buf = new StringBuffer();

		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select  * from (   ");
		}

		if(preProcessingDO.getSt_gubun().equals("1")||preProcessingDO.getSt_gubun().equals("0")){
			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(down.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");


			buf.append("\n  , '"+dasHandler.getProperty("MP2")+"'||CDOWN.PATH||'/'||CDOWN.FILENAME  AS file_path     ");

			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,0  as pgm_id  ");
			buf.append("\n ,'' as PGM_NM ");
			buf.append("\n ,CT.EDTRID ");
			buf.append("\n ,'mp2' AS GUBUN ");

			buf.append("\n  , DOWN.CART_NO as cart_no ");
			buf.append("\n ,'Y' AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID       ");
			buf.append("\n 	INNER JOIN (SELECT CART_NO,CT_ID ,CART_SEQ FROM DAS.CART_CONT_TBL GROUP BY CART_NO,CT_ID,CART_SEQ ) CART ON CART.CT_ID = INST.CT_ID       ");
			buf.append("\n 	 INNER JOIN DAS.DOWN_CART_TBL DOWN ON DOWN.CART_NO = CART.CART_NO          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = DOWN.REQ_USRID          ");
			//buf.append("\n 	INNER JOIN DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");
			buf.append("\n 	  INNER JOIN DAS.CONTENTS_DOWN_TBL  CDOWN ON CDOWN.CART_NO = CART.CART_NO     and CDOWN.cart_seq = cart.cart_seq    ");
			buf.append("\n 	left outer join das.AUTO_ARCHVIE_TBL auto on CT.CT_CLA = AUTO.SCL_CD     ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			//buf.append("\n 	and (inst.ARCH_STE_YN='Y' OR inst.ARCH_STE_YN='N' )                                               ");
			buf.append("\n 	AND (DOWN.DEL_YN='N'  or DOWN.DEL_YN='' )                                              ");
			//buf.append("\n 	and mmt.data_stat_cd not in (  '000','')                                             ");

			buf.append("\n  AND DOWN.DOWN_GUBUN='003'                                               ");

			/*if(preProcessingDO.getSt_gubun().equals("0")){

			}else if(preProcessingDO.getSt_gubun().equals("1")){
				buf.append("\n AND INST.DTL_YN='Y' ");
			}else if(preProcessingDO.getSt_gubun().equals("2")){
				buf.append("\n AND INST.DTL_YN='N'  ");
			}
			 */


			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND down.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND down.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//		if(!preProcessingDO.getSt_gubun().equals("")){ 
			buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//		}
			buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n  and cdown.UPDT_USER<>'workflow'	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");

		}


		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}

		if(preProcessingDO.getSt_gubun().equals("2")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(ct.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");

			buf.append("\n , case when    ");
			buf.append("\n  mmt.ARCH_ROUTE ='P' THEN  '"+dasHandler.getProperty("ARCREQ")+"/' ||right(tc.INPUT_HR,10) || '/'||tc.INPUT_HR_NM ");
			buf.append("\n  WHEN   mmt.ARCH_ROUTE ='N' THEN     ");
			buf.append("\n   '"+dasHandler.getProperty("MP2")+"/'||left(INST.reg_dt,6)||'/'||substr(INST.reg_dt,7,2)||'/'||INST.wrk_file_nm    ");
			buf.append("\n end  AS file_path   ");
			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,pgm.PGM_NM ");
			buf.append("\n ,CT.EDTRID ");
			buf.append("\n ,'arcreq' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID         ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	INNER JOIN DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");
			//	buf.append("\n 	left outer join das.AUTO_ARCHVIE_TBL auto on CT.CT_CLA = AUTO.SCL_CD     ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//	buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			buf.append("\n 	and MMT.ARCH_REG_DD =''                                          ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");
			buf.append("\n 	and mmt.manual_yn ='Y'      ");
			/*if(preProcessingDO.getSt_gubun().equals("0")){

			}else if(preProcessingDO.getSt_gubun().equals("1")){
				buf.append("\n AND INST.DTL_YN='Y' ");
			}else if(preProcessingDO.getSt_gubun().equals("2")){
				buf.append("\n AND INST.DTL_YN='N'  ");
			}
			 */
			buf.append("\n  AND mmt.ARCH_ROUTE like 'P%'                                           ");

			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//		if(!preProcessingDO.getSt_gubun().equals("")){ 
			buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//		}
			buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}


		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}

		if(preProcessingDO.getSt_gubun().equals("3")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(CT.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");

			buf.append("\n , case when    ");
			buf.append("\n  mmt.ARCH_ROUTE ='P' THEN  '"+dasHandler.getProperty("ARCREQ")+"/' ||right(tc.INPUT_HR,10)  ");
			buf.append("\n  WHEN   mmt.ARCH_ROUTE ='DP' THEN     ");
			buf.append("\n   '"+dasHandler.getProperty("MP2")+"/'||left(INST.reg_dt,6)||'/'||substr(INST.reg_dt,7,2)||'/'||INST.wrk_file_nm    ");
			buf.append("\n end  AS file_path   ");
			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,pgm.PGM_NM ");
			buf.append("\n ,CT.EDTRID ");
			buf.append("\n ,'MP2' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' ");
			buf.append("\n  END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID  and mmt.manual_yn='Y'          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	LEFT OUTER JOIN  DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");
			buf.append("\n 	left outer join das.AUTO_ARCHVIE_TBL auto on CT.CT_CLA = AUTO.SCL_CD     ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//		buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			buf.append("\n 	and mmt.manual_yn ='Y'                                                ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");

			/*if(preProcessingDO.getSt_gubun().equals("0")){

			}else if(preProcessingDO.getSt_gubun().equals("1")){
				buf.append("\n AND INST.DTL_YN='Y' ");
			}else if(preProcessingDO.getSt_gubun().equals("2")){
				buf.append("\n AND INST.DTL_YN='N'  ");
			}
			 */
			buf.append("\n  AND MMT.ARCH_ROUTE like 'DP%'                                               ");

			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//		if(!preProcessingDO.getSt_gubun().equals("")){ 
			buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//		}
			buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}


		if(preProcessingDO.getSt_gubun().equals("0")){


			buf.append("\n )   with ur  ");


		}




























		return buf.toString();
	}
	/**
	 * NLE 메타&DTL 등록 대상을 조회한다.
	 * @param conditionDO
	 * @return
	 * @throws DASException
	 */
	public static final String selectReqNleInfoQuery(WorkStatusConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();

		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		if (conditionDO.getQueryResultCount() == false)
		{
			buf.append(" \n select * from (");
		}
		else{
			buf.append("\n select count(*) as CCOUNT ,sum(sum_brd_leng) as sum_brd_leng from ( ");
		}
		buf.append("\n select ");
		buf.append("\n 	A.master_id, ");
		buf.append("\n 	A.tape_item_id, ");
		buf.append("\n 	A.mcuid, ");
		buf.append("\n 	A.data_stat_cd as data_stat_cd, ");
		buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
		buf.append("\n 	A.error_stat_cd as error_stat_cd, ");
		buf.append("\n 	C.clf_nm as clf_nm, ");
		buf.append("\n 	C.desc as desc, ");
		buf.append("\n 	A.title as title, ");
		buf.append("\n 	A.modrid as modrid, ");
		buf.append("\n 	A.reg_dt as reg_dt, ");
		buf.append("\n 	A.req_cd as req_cd, ");
		buf.append("\n 	A.brd_leng as brd_leng, ");
		buf.append("\n 	A.brd_dd as brd_dd, ");
		buf.append("\n 	A.epis_no as epis_no, ");
		buf.append("\n 	A.ing_reg_dd as ing_reg_dd, ");
		buf.append("\n 	A.arch_reg_dd as arch_reg_dd, ");	
		buf.append("\n 	PGM.PGM_ID, ");
		buf.append("\n 	B.COUNT, ");
		buf.append("\n  CASE when INST.DTL_YN ='N' THEN '아카이브 요청 스토리지' ");
		buf.append("\n 	WHEN INST.DTL_YN ='Y' THEN 'DTL' ");
		buf.append("\n 	ELSE '' ");
		buf.append("\n 	END AS STORAGE, ");
		if(!conditionDO.getQueryResultCount() == false){
			buf.append("\n  CASE "); 
			buf.append("\n  when A.BRD_LENG is not null or A.BRD_LENG <> '' "); 
			buf.append("\n 	  THEN bigint(SUBSTR(A.BRD_LENG,1,2))+bigint(substr(A.BRD_LENG,4,2))*60+bigint(substr(A.BRD_LENG,7,2)) "); 
			buf.append("\n  ELSE 0 ");
			buf.append("\n END SUM_BRD_LENG, ");
		}
		buf.append("\n 	ROW_NUMBER() OVER(ORDER BY A.master_id) AS rownum ");	
		buf.append("\n from das.metadat_mst_tbl A  ");	
		buf.append("\n left outer join das.pgm_info_tbl pgm on pgm.pgm_id=A.PGM_ID");
		buf.append("\n inner join das.contents_mapp_tbl map on map.master_id=A.master_id");
		buf.append("\n inner join das.contentS_inst_tbl inst on inst.ct_id = map.CT_ID and inst.CTI_FMT like '%10%'");

		buf.append("\n LEFT OUTER JOIN ( select z.master_id, count(*) as COUNT  ");
		buf.append("\n from (select distinct master_id, ct_id from das.contents_mapp_tbl mapp where (mapp.del_dd is null or mapp.del_dd = '')) z  ");
		buf.append("\n group by z.master_id) B ON A.master_id = B.master_id ");

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())
				||DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			buf.append("\n INNER JOIN (      ");
			buf.append("\n  select distinct MAP.MASTER_ID, CT.VD_QLTY, CT.ASP_RTO_CD ");
			buf.append("\n  from DAS.CONTENTS_TBL CT, ");
			buf.append("\n  DAS.CONTENTS_MAPP_TBL MAP   ");
			buf.append("\n  where   	MAP.CT_ID = CT.CT_ID  ");
			buf.append("\n  and (MAP.del_dd is null or MAP.del_dd = '')    ");
			buf.append("\n  )  CTT on CTT.master_id = A.master_id ");
		}

		buf.append("\n ,das.code_tbl C  ");

		// 검수자 이름으로 검색한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)			
				buf.append(" , DAS.ERP_COM_USER_TBL USER ");
		}




		buf.append("\n where A.data_stat_cd = C.scl_cd ");
		buf.append("\n 		AND C.clf_cd = 'P011' ");	
		//buf.append("\n 		AND A.DEL_DD not like '2%' ");
		buf.append("\n 		AND (A.DEL_DD is null or A.DEL_DD = '') ");

		buf.append("\n 	    and A.ARCH_ROUTE='N' ");


		//요청일 검색
		if(!conditionDO.getFromDate().equals(""))
		{
			buf.append("\n 	and LEFT(	A.ing_reg_dd, 8) >= '"+conditionDO.getFromDate()+"' ");
		}
		if(!conditionDO.getToDate().equals(""))
		{
			buf.append("\n 	and LEFT(	A.ing_reg_dd, 8) <= '"+conditionDO.getToDate()+"' ");
		}



		// 프로그램명
		if (!conditionDO.getSearchKey().equals("")){			
			buf.append("\n and A.TITLE like '%" + conditionDO.getSearchKey() + "%'");


		}
		// 스토리지 경로명
		if (!conditionDO.getStorage().equals("")){			
			buf.append("\n and inst.dtl_yn = '" + conditionDO.getStorage() + "'");


		}



		if(!conditionDO.getCtgr_l_cd().equals("")){
			// 100: 소재  200: 프로그램  
			buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
		}

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  CTT.ASP_RTO_CD = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			buf.append("\n 	AND CTT.VD_QLTY = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())){
			//			buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.VIEW_GR_CD.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.VIEW_GR_CD = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.CPRT_TYPE.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.CPRT_TYPE = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.RSV_PRD_CD.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
		}


		if(conditionDO.getMcuidYn().equals(DASBusinessConstants.SourceGubun.SDI))
		{
			// mcuidYn=N 매체변환
			buf.append("\n 	 AND (A.mcuid is null or A.mcuid = '') ");
		}
		else if(conditionDO.getMcuidYn().equals(DASBusinessConstants.SourceGubun.ONAIR))
		{   // mcuidYn=Y 온에어(주조)
			buf.append("\n 	AND A.mcuid is not null ");
			buf.append("\n 	AND A.mcuid <> '' ");
		}else if(conditionDO.getMcuidYn().equals(DASBusinessConstants.SourceGubun.ANY)){
			// 전체 검색
		}else if(conditionDO.getMcuidYn().equals(DASBusinessConstants.SourceGubun.PDS)){
			//PDS
		}

		//		등록일 검색
		if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	AND LEFT(A.ING_REG_DD, 8) >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	AND LEFT(A.ING_REG_DD, 8) <= '"+conditionDO.getToDate()+"' ");
			}
		}




		boolean isDateCondition = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()) )			

		{
			isDateCondition = true;
		}

		if(isDateCondition)
		{
			buf.append("\n 	AND ( ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");

			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				buf.append(" or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append(" or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
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

		// 검색어를 입력한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_NM") == true)			
				buf.append("\n and SEC_ARCH_NM = '" + conditionDO.getSearchKey() + "'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_ID") == true)			
				buf.append("\n and SEC_ARCH_ID = '" + conditionDO.getSearchKey() + "'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("REQ_CD") == true)			
				buf.append("\n and REQ_CD like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("TITLE") == true)			
				buf.append("\n and TITLE like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_ID") == true)			
				buf.append("\n and ACCEPTOR_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)
			{
				buf.append("\n and A.Acceptor_ID = USER.USER_ID ");
				buf.append("\n and USER.USER_NM like '%" + conditionDO.getSearchKey() + "%'");
			}

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("MASTER_ID") == true)			
				buf.append("\n and A.MASTER_ID = " + conditionDO.getSearchKey() + " ");
		}

		if(conditionDO.getSortColume().length()>0){
			buf.append("\n order by "+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}
		/*	if (conditionDO.getQueryResultCount() == false)
		{
			// rownum 으로 갯수를 제한하는 경우라면 
			if ( (conditionDO.getStartPos() > 0) && (conditionDO.getEndPos() > 0) )
			{
				buf.append(" \n ) as t ");
				buf.append("\n where rownum between " );
				buf.append(conditionDO.getStartPos() );
				buf.append("\n and " );
				buf.append(conditionDO.getEndPos() );
			}
		}
		else	*/// 갯수만 가져오는 경우라면 
		buf.append(" \n ) as t ");

		buf.append("\n with ur");

		return buf.toString();		

	}
	/**
	 * NLE 메타 & DTL 등록 한다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws DASException
	 */
	public static final String insertMetadatQuery(MetadataMstInfoDO metadataMstInfoDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.METADAT_MST_TBL set ");

		if(metadataMstInfoDO.getTitle().trim().length()>0)
			buf.append("\n 	TITLE= ?,  ");
		if(metadataMstInfoDO.getSubTtl().trim().length()>0)
			buf.append("\n 	SUB_TTL= ?,  ");
		if(metadataMstInfoDO.getRerun().trim().length()>0)
			buf.append("\n 	RERUN= ?,  ");
		if(metadataMstInfoDO.getBrdBgnDd().trim().length()>0)
			buf.append("\n 	BRD_DD= ?,  ");
		if(metadataMstInfoDO.getFinalBrdYn().trim().length()>0)
			buf.append("\n 	FINAL_BRD_YN= ?,  ");
		if(metadataMstInfoDO.getCtgrLCd().trim().length()>0)
			buf.append("\n 	CTGR_L_CD= ?,  ");
		if(metadataMstInfoDO.getCtgrMCd().trim().length()>0)
			buf.append("\n 	CTGR_M_CD= ?,  ");
		if(metadataMstInfoDO.getCtgrSCd().trim().length()>0)
			buf.append("\n 	CTGR_S_CD= ?,  ");
		if(metadataMstInfoDO.getEpisNo()>0)
			buf.append("\n 	EPIS_NO= ?,  ");
		if(metadataMstInfoDO.getPgmId()>0)
			buf.append("\n 	PGM_ID= ?,  ");
		if(metadataMstInfoDO.getProducerNm().trim().length()>0)
			buf.append("\n 	PRODUCER_NM= ?,  ");
		if(metadataMstInfoDO.getWriterNm().trim().length()>0)
			buf.append("\n 	WRITER_NM= ?,  ");
		if(metadataMstInfoDO.getDrtNm().trim().length()>0)
			buf.append("\n 	DRT_NM= ?,  ");
		if(metadataMstInfoDO.getCmrDrtNm().trim().length()>0)
			buf.append("\n 	CMR_DRT_NM= ?,  ");
		if(metadataMstInfoDO.getPrdtDeptNm().trim().length()>0)
			buf.append("\n 	PRDT_DEPT_NM= ?,  ");
		if(metadataMstInfoDO.getPrdtInOutsCd().trim().length()>0)
			buf.append("\n 	PRDT_IN_OUTS_CD= ?,  ");
		if(metadataMstInfoDO.getOrgPrdrNm().trim().length()>0)
			buf.append("\n 	ORG_PRDR_NM= ?,  ");
		if(metadataMstInfoDO.getMcNm().trim().length()>0)
			buf.append("\n 	MC_NM= ?,  ");
		if(metadataMstInfoDO.getCastNm().trim().length()>0)
			buf.append("\n 	CAST_NM= ?,  ");
		if(metadataMstInfoDO.getCmrPlace().trim().length()>0)
			buf.append("\n 	CMR_PLACE= ?,  ");
		if(metadataMstInfoDO.getMusicInfo().trim().length()>0)
			buf.append("\n 	MUSIC_INFO= ?,  ");
		if(metadataMstInfoDO.getSnps().trim().length()>0)
			buf.append("\n 	SNPS= ?,  ");
		if(metadataMstInfoDO.getKeyWords().trim().length()>0)
			buf.append("\n 	KEY_WORDS= ?,  ");
		if(metadataMstInfoDO.getSpcInfo().trim().length()>0)
			buf.append("\n 	SPC_INFO= ?,  ");
		if(metadataMstInfoDO.getViewGrCd().trim().length()>0)
			buf.append("\n 	VIEW_GR_CD= ?,  ");
		if(metadataMstInfoDO.getPgmRate().trim().length()>0)
			buf.append("\n 	PGM_RATE= ?,  ");
		if(metadataMstInfoDO.getDlbrCd().trim().length()>0)
			buf.append("\n 	DLBR_CD= ?,  ");
		if(metadataMstInfoDO.getCprtType().trim().length()>0)
			buf.append("\n 	CPRT_TYPE= ?,  ");
		if(metadataMstInfoDO.getCprtrNm().trim().length()>0)
			buf.append("\n 	CPRTR_NM= ?,  ");
		if(metadataMstInfoDO.getCprtTypeDsc().trim().length()>0)
			buf.append("\n 	CPRT_TYPE_DSC= ?,  ");
		if(metadataMstInfoDO.getAwardHstr().trim().length()>0)
			buf.append("\n 	AWARD_HSTR= ?,  ");
		if(metadataMstInfoDO.getTapeMediaClfCd().trim().length()>0)
			buf.append("\n 	TAPE_MEDIA_CLF_CD= ?,  ");
		if(metadataMstInfoDO.getGathCoCd().trim().length()>0)
			buf.append("\n 	GATH_CO_CD= ?,  ");
		if(metadataMstInfoDO.getRsvPrdCd().trim().length()>0)
			buf.append("\n 	RSV_PRD_CD= ?,  ");
		if(metadataMstInfoDO.getRsvPrdEndDd().trim().length()>0)
			buf.append("\n 	RSV_PRD_END_DD= ?,  ");
		if(metadataMstInfoDO.getRpimgKfrmSeq()>0)
			buf.append("\n 	RPIMG_KFRM_SEQ= ?,  ");
		if(metadataMstInfoDO.getRpimgCtId()>0)
			buf.append("\n 	RPIMG_CT_ID= ?,  ");
		if(metadataMstInfoDO.getArrange_nm().trim().length()>0)
			buf.append("\n 	ARRANGE_NM = ?,  ");
		if(metadataMstInfoDO.getReqCd().trim().length()>0)
			buf.append("\n  REQ_CD = ? ,");
		if(metadataMstInfoDO.getBrdLeng().trim().length()>0)
			buf.append("\n  BRD_LENG = ? ");



		//		
		//		if (tape_id.trim().length() > 0)
		//			buf.append("\n  , tape_id = ? ");
		//		
		//		if (tape_item_id.trim().length() > 0)
		//			buf.append("\n  , tape_item_id = ? ");
		//		
		buf.append("\n 	where MASTER_ID= ?  ");
		return buf.toString();

	} 
	/**
	 * NLE 메타 & DTL 등록 메타데이타를 카피해서 등록한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws DASException
	 */
	public static final String insertCopyMetadatQuery() 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n update das.metadat_mst_tbl                                               ");
		buf.append("\n set ( PGM_ID, PGM_CD, EPIS_NO, TITLE, CTGR_L_CD                          ");
		buf.append("\n      , CTGR_M_CD, CTGR_S_CD, BRD_DD, FINAL_BRD_YN, SNPS                  ");
		buf.append("\n 		 , KEY_WORDS, BRD_BGN_HMS, BRD_END_HMS, BRD_LENG, PGM_RATE            ");
		buf.append("\n 		 , DRT_NM, PRODUCER_NM, WRITER_NM, PRDT_IN_OUTS_CD, PRDT_DEPT_CD      ");
		buf.append("\n 		 , PRDT_DEPT_NM, ORG_PRDR_NM, MC_NM, CAST_NM, CMR_DRT_NM              ");
		buf.append("\n 		 , FM_DT, CMR_PLACE, SPC_INFO, REQ_CD, SEC_ARCH_NM                    ");
		buf.append("\n 		 , SEC_ARCH_ID, GATH_CO_CD, GATH_CLF_CD, ARCH_REG_DD, ARRG_END_DT     ");
		buf.append("\n 		 , WORK_PRIO_CD, RSV_PRD_CD, CPRTR_NM, CPRT_TYPE, CPRT_TYPE_DSC       ");
		buf.append("\n 		 , VIEW_GR_CD, DLBR_CD, AWARD_HSTR, RPIMG_KFRM_SEQ, TAPE_ID           ");
		buf.append("\n 		 , TAPE_ITEM_ID, TAPE_MEDIA_CLF_CD, RSV_PRD_END_DD, DEL_DD, USE_YN    ");
		buf.append("\n 		 , REGRID, REG_DT, MODRID, MOD_DT, GATH_DEPT_CD                       ");
		buf.append("\n 		 , MCUID, RPIMG_CT_ID, DATA_STAT_CD, ING_REG_DD, COPY_KEEP            ");
		buf.append("\n 		 , CLEAN_KEEP, MUSIC_INFO, RST_CONT, RERUN, ACCEPTOR_ID               ");
		buf.append("\n 		 , SUB_TTL, ARRANGE_NM, LOCK_STAT_CD, ERROR_STAT_CD)      ");
		buf.append("\n =(select  PGM_ID, PGM_CD, EPIS_NO, TITLE, CTGR_L_CD                      ");
		buf.append("\n      , CTGR_M_CD, CTGR_S_CD, BRD_DD, FINAL_BRD_YN, SNPS                  ");
		buf.append("\n 		 , KEY_WORDS, BRD_BGN_HMS, BRD_END_HMS, BRD_LENG, PGM_RATE            ");
		buf.append("\n 		 , DRT_NM, PRODUCER_NM, WRITER_NM, PRDT_IN_OUTS_CD, PRDT_DEPT_CD      ");
		buf.append("\n 		 , PRDT_DEPT_NM, ORG_PRDR_NM, MC_NM, CAST_NM, CMR_DRT_NM              ");
		buf.append("\n 		 , FM_DT, CMR_PLACE, SPC_INFO, REQ_CD, SEC_ARCH_NM                    ");
		buf.append("\n 		 , SEC_ARCH_ID, GATH_CO_CD, GATH_CLF_CD, ARCH_REG_DD, ARRG_END_DT     ");
		buf.append("\n 		 , WORK_PRIO_CD, RSV_PRD_CD, CPRTR_NM, CPRT_TYPE, CPRT_TYPE_DSC       ");
		buf.append("\n 		 , VIEW_GR_CD, DLBR_CD, AWARD_HSTR, RPIMG_KFRM_SEQ, TAPE_ID           ");
		buf.append("\n 		 , TAPE_ITEM_ID, TAPE_MEDIA_CLF_CD, RSV_PRD_END_DD, DEL_DD, USE_YN    ");
		buf.append("\n 		 , REGRID, REG_DT, MODRID, MOD_DT, GATH_DEPT_CD                       ");
		buf.append("\n 		 , MCUID, RPIMG_CT_ID, DATA_STAT_CD, ING_REG_DD, COPY_KEEP            ");
		buf.append("\n 		 , CLEAN_KEEP, MUSIC_INFO, RST_CONT, RERUN, ACCEPTOR_ID               ");
		buf.append("\n 		 , SUB_TTL, ARRANGE_NM, LOCK_STAT_CD, ERROR_STAT_CD       ");
		buf.append("\n 		 from metadat_mst_tbl where master_id = ?)                        ");
		buf.append("\n where master_id =?                                                   ");

		return buf.toString();

	}

	/**
	 * 관련영상 링크조회한다
	 * @param masterId
	 * @return
	 * @throws RemoteException
	 */

	public static final String selectRelationLink()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select mmt.title,  code1.desc as ctgr_l_nm, value((code2.desc||'/'||code3.desc),'') AS ctgr_ms_nm,      ");
		buf.append("\n 	rel.child_master_id,  ");
		buf.append("\n 	case when mmt.CTGR_L_CD ='100' then mmt.FM_DT");
		buf.append("\n 	when mmt.CTGR_L_CD ='200' then mmt.brd_dd ");
		buf.append("\n  else '' ");
		buf.append("\n 	end as brd_dd,  mmt.brd_leng, code4.desc as vd_qlty from metadat_mst_tbl mmt  ");

		buf.append("\n 	left outer join contents_mapp_tbl map on map.MASTER_ID = mmt.master_id ");
		buf.append("\n left outer join code_tbl code1 on code1.scl_cd=mmt.ctgr_l_cd and code1.clf_cd='P002' ");
		buf.append("\n left outer join code_tbl code2 on code2.scl_cd=mmt.ctgr_m_cd and code2.clf_cd='P003' ");
		buf.append("\n left outer join code_tbl code3 on code3.scl_cd=mmt.ctgr_s_cd and code3.clf_cd='P004'	 ");
		buf.append("\n left outer join contents_tbl ct on ct.ct_id =map.ct_id	 ");
		buf.append("\n   left outer join code_tbl code4 on code4.scl_cd=ct.vd_qlty and code4.clf_cd='A005'	 ");
		buf.append("\n inner join RELATION_MASTER rel on rel.child_master_id=mmt.master_id	 ");
		buf.append("\n where rel.parent_master_id=?	 ");
		buf.append("\n group by mmt.title,  code1.desc , value((code2.desc||'/'||code3.desc),'') ,   	 ");
		buf.append("\n rel.child_master_id, mmt.brd_dd,  mmt.brd_leng, code4.desc,mmt.CTGR_L_CD ,mmt.FM_DT    	 ");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}



	/**
	 * 관련영상 master_id를 찾는다
	 * @param master_id   마스터id
	 * @throws DASException
	 */
	public static final String selectRelCtid(long master_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select map.ct_id from metadat_mst_tbl mst ");
		buf.append("\n 	left outer join contents_mapp_tbl map on map.MASTER_ID=mst.master_id  ");
		buf.append("\n where mst.master_id="+master_id+" ");
		buf.append("\n 	fetch first 1 rows only ");



		return buf.toString();
	}





	/**
	 * NDS DOWN
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public static String selectNDSList()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n 	pds.storageid  "); 
		buf.append("\n 	,con.media_id"); 
		buf.append("\n 	,mst.title "); 
		buf.append("\n 	,mst.sub_ttl "); 
		buf.append("\n 	,mst.SEC_ARCH_ID ");
		buf.append("\n 	,cart.CART_SEQ ");
		buf.append("\n 	,cart.CART_NO ");
		buf.append("\n 	,down.PATH  ");
		buf.append("\n  from cart_cont_tbl cart ");
		buf.append("\n   inner join metadat_mst_tbl mst on cart.master_id=mst.master_id  ");	
		buf.append("\n   inner join contents_tbl con on  con.ct_id=cart.ct_id  ");	
		buf.append("\n   inner join PDS_PGM_USERINFO_TBL pds on pds.USERNAME=mst.SEC_ARCH_ID ");
		buf.append("\n   inner join  CONTENTS_DOWN_TBL down on down.CART_NO=cart.CART_NO ");	
		buf.append("\n   where cart.cart_no = ? and cart.cart_Seq = ? ");	

		return buf.toString();
	}

	/**
	 * PDS DOWN
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public static String selectPDSList()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               

		buf.append("\n 	mst.pgm_id  "); 
		buf.append("\n 	,mst.title"); 
		buf.append("\n 	,mst.epis_no "); 
		buf.append("\n 	,mst.sub_ttl"); 
		buf.append("\n 	,cart.cti_id  "); 
		buf.append("\n ,cart.ctgr_m_cd"); 
		buf.append("\n 	,cart.cart_no  "); 
		buf.append("\n 	,cart.cart_seq "); 
		buf.append("\n 	,con.MEDIA_ID  "); 
		buf.append("\n 	,down.DOWN_SUBJ "); 
		buf.append("\n 	,cdown.path ");
		buf.append("\n 	from cart_cont_tbl cart  "); 
		buf.append("\n 	left outer join metadat_mst_tbl mst on mst.master_id=cart.master_id"); 
		buf.append("\n 	left outer join contents_tbl con on con.ct_id=cart.ct_id"); 
		buf.append("\n 	left outer join DOWN_CART_TBL down on down.CART_NO=cart.CART_no  "); 
		buf.append("\n 	left outer join CONTENTS_DOWN_TBL cdown on cdown.CART_NO=cart.cart_no "); 
		buf.append("\n 	where cart.cart_no = ? ");
		buf.append("\n 	and cart.cart_seq = ? ");

		return buf.toString();
	}




	/*	
	public static String selectContents()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select  ");
		buf.append("\n 	inst.ct_id ");
		buf.append("\n  ,CASE WHEN con.media_id = ''  THEN 0	");
		buf.append("\n   ELSE con.media_id  ");
		buf.append("\n   END AS media_id  ");
		buf.append("\n ,inst.fl_path ");
		buf.append("\n ,con.kfrm_path ");
		buf.append("\n ,inst.cti_id	 ");
		buf.append("\n from contents_inst_tbl inst	 ");
		buf.append("\n left outer join contents_tbl con on inst.ct_id=con.ct_id 	 ");
		buf.append("\n where inst.ct_id=?	 ");
		buf.append("\n and inst.CTI_FMT like '10%' ");

		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	 */


	/**
	 * 컨텐츠 정보를 받아온다
	 * @param pgm_id    프로그램 id
	 */
	public static final String selectMappTbl(long pgm_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		buf.append("\n 	map.master_id  ");
		buf.append("\n 	,map.ct_id  ");
		buf.append("\n 	,map.pgm_id  ");
		buf.append("\n 	,inst.cti_id  ");
		buf.append("\n 	,inst.fl_path  ");
		buf.append("\n 	from contents_mapp_tbl map  ");
		buf.append("\n 	LEFT join CONTENTS_INST_TBL  inst on inst.ct_id= map.ct_id and inst.cti_fmt like '10%' ");
		buf.append("\n where map.pgm_id="+pgm_id+" ");
		buf.append("\n 	fetch first 1 rows only ");

		return buf.toString();
	}

	/**
	 * 컨텐츠 정보를 받아온다
	 * @param pgm_id    프로그램 id
	 */
	public static final String selectMappTbl2(long master_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		buf.append("\n 	map.master_id  ");
		buf.append("\n 	,map.ct_id  ");
		buf.append("\n 	,map.pgm_id  ");
		buf.append("\n 	,inst.cti_id  ");
		buf.append("\n 	,'"+dasHandler.getProperty("MP2")+"/'||left(INST.reg_dt,6)||'/'||substr(INST.reg_dt,7,2) as fl_path  ");
		buf.append("\n 	from contents_mapp_tbl map  ");
		buf.append("\n 	LEFT join CONTENTS_INST_TBL  inst on inst.ct_id= map.ct_id and inst.cti_fmt like '10%' ");
		buf.append("\n where map.master_id="+master_id+" ");
		buf.append("\n 	fetch first 1 rows only ");

		return buf.toString();
	}


	/**
	 * cti_id를 받아온다
	 * @param ct_id    영상id
	 */
	public static final String selectCtidFromCtiId(long ct_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		buf.append("\n cti_id  ");
		buf.append("\n 	from contents_inst_tbl   ");
		buf.append("\n where ct_id="+ct_id+" ");
		buf.append("\n and cti_fmt like '%10%'");
		buf.append("\n 	fetch first 1 rows only ");

		return buf.toString();
	}
	/**
	 * tc q의 데이터를 읽어온다
	 * @param tcbean    검색할 정보가 담긴 beans
	 */
	public static final String selectTcBeanTbl(TcBeanDO tcbean)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		buf.append("\n 	 '"+dasHandler.getProperty("WINNEARLINE")+"/restore/D080009/"+tcbean.getCart_no()+"'as fl_path, ");
		buf.append("\n 	case when  left(con.KFRM_PATH,1)='/'  then right(con.KFRM_PATH,length(con.KFRM_PATH)-1)  ");
		buf.append("\n 	else con.KFRM_PATH  ");
		buf.append("\n 	end as KFRM_PATH,  ");
		buf.append("\n 	inst.CTi_ID,  ");
		buf.append("\n 	case when  left(inst.fl_path,1)='/'  then right(inst.fl_path,length(inst.fl_path)-1)  ");
		buf.append("\n 	else inst.fl_path  ");
		buf.append("\n 	end as flpath  ");
		buf.append("\n 	from contents_inst_tbl inst ");
		buf.append("\n 	inner join contents_tbl con on con.CT_ID=inst.CT_ID  ");
		buf.append("\n 	where 1=1  ");
		buf.append("\n 	and inst.CTI_FMT like '30%' ");
		buf.append("\n and inst.ct_id="+tcbean.getCt_id()+" ");

		return buf.toString();
	}

	/**
	 * tc q의 데이터를 읽어온다
	 * @param ct_id    컨텐츠id
	 */
	public static final String selectTcBeanTbl(long ct_id)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		//	buf.append("\n 	 'mp2/restore/D080009/"+tcbean.getCart_no()+"'as fl_path, ");
		buf.append("\n 	case when  left(con.KFRM_PATH,1)='/'  then right(con.KFRM_PATH,length(con.KFRM_PATH)-1)  ");
		buf.append("\n 	else con.KFRM_PATH  ");
		buf.append("\n 	end as KFRM_PATH,  ");
		buf.append("\n 	inst.CTi_ID,  ");
		buf.append("\n 	case when  left(inst.fl_path,1)='/'  then right(inst.fl_path,length(inst.fl_path)-1)  ");
		buf.append("\n 	else inst.fl_path  ");
		buf.append("\n 	end as fl_path  ");
		buf.append("\n 	from contents_inst_tbl inst ");
		buf.append("\n 	inner join contents_tbl con on con.CT_ID=inst.CT_ID  ");
		buf.append("\n 	where 1=1  ");
		buf.append("\n 	and inst.CTI_FMT like '30%' ");
		buf.append("\n and inst.ct_id="+ct_id+" ");

		return buf.toString();
	}
	/**
	 * addtask job xml 생성 쿼리
	 *  @param num    contents_down_tbl의 key
	 */
	public static final String selectAddTaskForXml(int num)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   ");
		buf.append("\n  down_subj||'_'||value(cart.MEDIA_ID,'')||'_HR' as RENAME ");
		buf.append("\n 	,value(cart.MEDIA_ID,'')as MEDIA_ID ");
		//buf.append("\n 	,down.FILE_PATH ");
		//down.STRG_LOC
		buf.append("\n 	,'mp2/'||(select path from contents_down_tbl where cart_no = down.cart_no and cart_seq = cart.CART_SEQ group by path) STRG_LOC ");
		buf.append("\n 	,down.CATEGORY  ");
		buf.append("\n 	,down.STORAGENAME ");
		buf.append("\n 	,down.STRG_LOC ");
		buf.append("\n 	,meta.TITLE  ");
		buf.append("\n 	,value(pgm.pgm_nm,'') as pgm_nm    ");
		buf.append("\n 	,cart.CART_SEQ  ");
		buf.append("\n 	,cart.CART_NO  ");
		buf.append("\n 	,down.req_usrid ");
		buf.append("\n 	,cdown.FILENAME ");
		buf.append("\n 	,down.down_gubun ");
		buf.append("\n 	,cart.target_cms_id ");
		buf.append("\n 	,cart.som ");
		buf.append("\n 	,cart.eom ");
		buf.append("\n 	,cart.down_typ ");
		buf.append("\n 	,cart.reg_dt ");
		buf.append("\n 	from  down_cart_Tbl down  ");
		buf.append("\n 	inner join cart_cont_tbl cart on cart.CART_NO=down.CART_NO ");
		buf.append("\n 	inner join metadat_mst_tbl meta on meta.MASTER_ID = cart.MASTER_ID ");
		buf.append("\n 	left outer join pgm_info_tbl pgm on meta.pgm_id = pgm.pgm_id ");
		buf.append("\n 		inner join CONTENTS_DOWN_TBL cdown on cdown.cart_no = cart.cart_no   and cdown.cart_seq = cart.cart_seq ");
		buf.append("\n where  ");
		buf.append("\n cdown.num ="+num+" ");

		return buf.toString();
	}

	/*	*//**
	 * addtask job xml 생성 쿼리 생성
	 * @param cartNo 카트넘버
	 * @param cartSeq 카트순번
	 *//*
	public static final String selectAddTaskForXmlByStorageClip(long cartNo,long cartSeq)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select                                                                                                                 ");
		buf.append("\n 		  down_subj||'_'||value(cart.MEDIA_ID,'')||'_HR' as RENAME                                                          ");
		buf.append("\n 		 	,value(cart.MEDIA_ID,'')as MEDIA_ID                                                                               ");
		buf.append("\n 		 	,down.FILE_PATH                                                                                                   ");
		buf.append("\n 		 	,down.CATEGORY                                                                                                    ");
		buf.append("\n 		 	,down.STORAGENAME                                                                                                 ");
		buf.append("\n 			,cit.fl_path as strg_loc                                                                                          ");
		buf.append("\n 		 	,meta.TITLE                                                                                                       ");
		buf.append("\n 		 	,pgm.pgm_nm                                                                                                       ");
		buf.append("\n 		 	,cart.CART_SEQ                                                                                                    ");
		buf.append("\n 		 	,cart.CART_NO                                                                                                     ");
		buf.append("\n 		 	,down.req_usrid                                                                                                   ");
		buf.append("\n             , value(cit.org_file_nm,'') as filename                                                                              ");
		buf.append("\n             , cit.wrk_file_nm as wrk_filename                                                                              ");
		buf.append("\n             , down.down_gubun                                                                              ");
		buf.append("\n 		 	from  down_cart_Tbl down                                                                                          ");
		buf.append("\n 		 	inner join cart_cont_tbl cart on cart.CART_NO=down.CART_NO                                                        ");
		buf.append("\n 		 	inner join metadat_mst_tbl meta on meta.MASTER_ID = cart.MASTER_ID                                                ");
		buf.append("\n 		 	left outer join pgm_info_tbl pgm on meta.pgm_id = pgm.pgm_id                                                      ");
		buf.append("\n 			left outer join contents_inst_tbl cit on cit.ct_id = cart.ct_id and cit.cti_fmt like '1%'                         ");
		buf.append("\n 		 where                                                                                                            ");
		buf.append("\n 		 cart.cart_no ="+cartNo+" and cart.cart_seq="+cartSeq+"                                                               ");

		return buf.toString();
	}
	  */


	/**
	 * addtask job xml 생성 쿼리 생성(파셜 스토리지 포함.)
	 * @param cartNo 카트넘버
	 * @param cartSeq 카트순번
	 */
	public static final String selectAddTaskForXmlByStorageClip(long cartNo,long cartSeq)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select                                                                                                                 ");
		buf.append("\n 		  down_subj||'_'||value(cart.MEDIA_ID,'')||'_HR' as RENAME                                                          ");
		buf.append("\n 		 	,value(cart.MEDIA_ID,'')as MEDIA_ID                                                                               ");
		buf.append("\n 		 	,down.FILE_PATH                                                                                                   ");
		buf.append("\n 		 	,down.CATEGORY                                                                                                    ");
		buf.append("\n 		 	,down.STORAGENAME                                                                                                 ");
		buf.append("\n 			,cit.fl_path as strg_loc                                                                                          ");
		buf.append("\n 		 	,meta.TITLE                                                                                                       ");
		buf.append("\n 		 	,pgm.pgm_nm                                                                                                       ");
		buf.append("\n 		 	,cart.CART_SEQ                                                                                                    ");
		buf.append("\n 		 	,cart.CART_NO                                                                                                     ");
		buf.append("\n 		 	,cart.som                                                                                                     ");
		buf.append("\n 		 	,cart.eom                                                                                                    ");
		buf.append("\n 		 	,cart.target_cms_id                                                                                                    ");
		buf.append("\n 		 	,code.desc as vd_qlty                                                                                                   ");
		buf.append("\n 		 	,value(CDOWN.PATH,'') as path    ");
		buf.append("\n 		 	,cit.cti_id    ");
		buf.append("\n 		 	,down.req_usrid                                                                                                   ");
		buf.append("\n             , value(cit.org_file_nm,'') as filename                                                                              ");
		buf.append("\n             , cit.wrk_file_nm as wrk_filename                                                                              ");
		buf.append("\n             , down.down_gubun                                                                              ");
		buf.append("\n             , cart.down_typ                                                                             ");
		buf.append("\n             , cart.reg_dt                                                                             ");

		buf.append("\n 		 	from  down_cart_Tbl down                                                                                          ");
		buf.append("\n 		 	inner join cart_cont_tbl cart on cart.CART_NO=down.CART_NO                                                        ");
		buf.append("\n 		 	inner join metadat_mst_tbl meta on meta.MASTER_ID = cart.MASTER_ID             ");
		buf.append("\n 		 	left outer join CONTENTS_DOWN_TBL CDOWN ON CDOWN.CART_NO = CART.CART_NO AND CDOWN.CART_SEQ = CART.CART_SEQ             ");
		buf.append("\n 		 	left outer join pgm_info_tbl pgm on meta.pgm_id = pgm.pgm_id                                                      ");
		buf.append("\n 			left outer join contents_inst_tbl cit on cit.ct_id = cart.ct_id and cit.cti_fmt like '1%'                         ");
		buf.append("\n 			left outer join code_tbl code on code.clf_cd='A005' AND code.scl_cd = cart.vd_qlty                         ");
		buf.append("\n 		 where                                                                                                            ");
		buf.append("\n 		 cart.cart_no ="+cartNo+" and cart.cart_seq="+cartSeq+"                                                               ");

		return buf.toString();
	}

	/**
	 * pds, nds  xml 생성 쿼리
	 * @param num    contents_down_tbl의 key
	 * 		 */
	public static final String selectInfoForDownXml(int num)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   distinct "); 
		buf.append("\n 	value(cart.MEDIA_ID ,'') as MEDIA_ID");
		buf.append("\n 	,value(down.FILE_PATH ,'') as FILE_PATH");
		buf.append("\n 	,value(down.CATEGORY ,'') as CATEGORY ");
		buf.append("\n 	,value(down.storagename,'') as storagename ");
		buf.append("\n 	,value(down.DOWN_GUBUN,'') as DOWN_GUBUN ");
		buf.append("\n 	,value(pgm.PROGRAM_NAME,'') as PROGRAM_NAME  ");
		buf.append("\n 	,value(meta.TITLE ,'') as TITLE ");
		buf.append("\n 	,value(meta.sub_ttl ,'') as sub_ttl ");
		buf.append("\n 	,value(meta.PDS_CMS_PGM_ID,'') as PDS_CMS_PGM_ID");
		buf.append("\n 	,value(meta.EPIS_NO  ,0) as EPIS_NO ");
		buf.append("\n 	,value(meta.PRODUCER_NM,'') as PRODUCER_NM ");
		buf.append("\n 	,value(meta.CMR_PLACE,'') AS CMR_PLACE ");
		buf.append("\n 	,case when value(meta.fm_dt,'') != '' and meta.fm_dt<>'00000000' and meta.fm_dt is not null and meta.fm_dt <>'' and replace(meta.fm_dt,' ','')<>''  then char(date(insert(insert(meta.FM_DT,5,0,'-'),8,0,'-'))) ");
		buf.append("\n 	else char(date(insert(insert(meta.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	end as fm_dt ");
		buf.append("\n 	,value(meta.CPRTR_NM ,'') as CPRTR_NM ");
		buf.append("\n 	,value(meta.CPRT_TYPE ,'') as CPRT_TYPE ");
		buf.append("\n 	,value(meta.RIST_CLF_CD,'') AS RIST_CLF_CD ");
		buf.append("\n 	,value(meta.VIEW_GR_CD,'') as VIEW_GR_CD ");
		buf.append("\n 	,value(meta.BRD_LENG  ,'') as BRD_LENG");
		buf.append("\n 	,value(meta.BRD_BGN_HMS ,'') as BRD_BGN_HMS ");
		buf.append("\n 	,value(meta.BRD_END_HMS ,'') as BRD_END_HMS");
		buf.append("\n 	,case when  value(meta.brd_dd,'') != '' and meta.brd_dd<>'00000000' and meta.brd_dd is not null and meta.brd_dd <>'' and replace(meta.brd_dd,' ','')<>''  then char(date(insert(insert(meta.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	else char(date(insert(insert(meta.FM_DT,5,0,'-'),8,0,'-')))   ");
		buf.append("\n 	end as BRD_DD ");
		buf.append("\n 	,value(inst.VD_HRESOL ,'') as VD_HRESOL");
		buf.append("\n 	,value(inst.VD_VRESOL ,'') as VD_VRESOL " );
		buf.append("\n 	,value(inst.FL_SZ ,0) as FL_SZ ");
		buf.append("\n 	,value(inst.BIT_RT ,'') as BIT_RT ");
		buf.append("\n 	,value(inst.AUD_SAMP_FRQ  ,'') as AUD_SAMP_FRQ ");
		buf.append("\n 	,value(inst.AUDIO_BDWT ,'') as AUDIO_BDWT ");
		buf.append("\n 	,value(inst.RECORD_TYPE_CD,'') AS RECORD_TYPE_CD  ");
		buf.append("\n 	,value(inst.FRM_PER_SEC ,'') as FRM_PER_SEC ");
		buf.append("\n 	,value(con.VD_QLTY ,'') as VD_QLTY ");
		buf.append("\n 	,value(con.CT_CLA  ,'') as CT_CLA ");
		buf.append("\n 	,value(cart.duration ,'') as CT_LENG ");
		buf.append("\n 	,value(con.ASP_RTO_CD  ,'') as ASP_RTO_CD ");
		buf.append("\n 	,value(con.CT_TYP  ,'') as CT_TYP ");
		buf.append("\n 	,value(cart.SOM ,'') as SOM ");
		buf.append("\n 	,value(cart.EOM  ,'') as EOM ");

		buf.append("\n 	,value(cart.CART_SEQ ,0) as CART_SEQ");
		buf.append("\n 	,value(cart.CART_NO ,0) as CART_NO ");
		buf.append("\n 	,value(cart.logical_tree ,'') as logical_tree ");
		buf.append("\n 	,value(cart.PHYICAL_TREE,'') as PHYICAL_TREE ");
		buf.append("\n 	,value(down.req_usrid ,'') as req_usrid");
		buf.append("\n 	,value(user.user_nm ,'') as user_nm");
		buf.append("\n 	,value(cdown.FILENAME ,'') as FILENAME");
		buf.append("\n 	,value(cdown.FILESIZE ,0) as  FILESIZE"  );
		buf.append("\n  ,down.down_subj||'_'||value(cart.MEDIA_ID,'')||'_HR' as rename ");
		buf.append("\n 	from  down_cart_Tbl down  ");
		buf.append("\n 	inner join cart_cont_tbl cart on cart.CART_NO=down.CART_NO ");
		buf.append("\n 	inner join metadat_mst_tbl meta on meta.MASTER_ID = cart.MASTER_ID ");
		buf.append("\n 	inner join contents_mapp_tbl map on map.MASTER_ID = meta.MASTER_ID ");
		buf.append("\n 	inner join contents_inst_tbl inst on inst.CT_ID = map.CT_ID and inst.cti_fmt like '10%' ");
		buf.append("\n 	inner join contents_tbl con on con.CT_ID = map.CT_ID   ");
		//buf.append("\n inner join CORNER_TBL corner on corner.CN_ID = map.CN_ID ");
		buf.append("\n 		left outer join pds_pgminfo_tbl pgm on meta.PDS_CMS_PGM_ID = pgm.PROGRAM_CODE ");
		buf.append("\n 	 inner join CONTENTS_DOWN_TBL cdown on cdown.cart_no = cart.cart_no   and cdown.cart_seq = cart.cart_seq ");
		buf.append("\n inner join user_info_Tbl user on user.sbs_user_id = down.req_usrid ");
		buf.append("\n where  ");
		buf.append("\n  cdown.num ="+num+" ");
		buf.append("\n 	fetch first 1 rows only ");
		return buf.toString();
	}
	/**
	 * pds, nds, 계열사  xml 생성 쿼리 생성하는 함수
	 * @param cartNo 카트넘버
	 * @param cartSeq 카트순번
	 */
	public static final String selectInfoForDownXmlByStorageClip(long cartNo,long cartSeq)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select   distinct ");
		buf.append("\n 	value(cart.MEDIA_ID,'') as  MEDIA_ID");
		buf.append("\n 	,value(down.FILE_PATH,'') as FILE_PATH ");
		buf.append("\n 	,value(down.CATEGORY,'') as  CATEGORY ");
		buf.append("\n 	,value(down.storagename,'') as storagename ");
		buf.append("\n 	,value(down.DOWN_GUBUN ,'') as DOWN_GUBUN ");
		buf.append("\n 	,value(pgm.PROGRAM_NAME,'') as PROGRAM_NAME  ");
		buf.append("\n 	,value(meta.TITLE,'') as  TITLE ");
		buf.append("\n 	,value(meta.PDS_CMS_PGM_ID,'') as PDS_CMS_PGM_ID");
		buf.append("\n 	,value(meta.EPIS_NO,0) as  EPIS_NO");
		buf.append("\n 	,value(meta.PRODUCER_NM,'') as PRODUCER_NM ");
		buf.append("\n 	,value(meta.CMR_PLACE,'') as CMR_PLACE ");
		buf.append("\n 	,case when meta.fm_dt !='' and meta.fm_dt<>'00000000'  then char(date(insert(insert(meta.FM_DT,5,0,'-'),8,0,'-'))) ");
		buf.append("\n 	else char(date(insert(insert(meta.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	end as fm_dt ");
		buf.append("\n 	,value(meta.CPRTR_NM ,'') as CPRTR_NM ");
		buf.append("\n 	,value(meta.CPRT_TYPE ,'') as CPRT_TYPE ");
		buf.append("\n 	,value(meta.RIST_CLF_CD,'') as RIST_CLF_CD ");
		buf.append("\n 	,value(meta.VIEW_GR_CD,'') as VIEW_GR_CD ");
		buf.append("\n 	,value(meta.BRD_LENG ,'') as BRD_LENG ");
		buf.append("\n 	,value(meta.BRD_BGN_HMS ,'') as BRD_BGN_HMS ");
		buf.append("\n 	,value(meta.BRD_END_HMS ,'') as BRD_END_HMS ");
		buf.append("\n 	,case when meta.BRD_DD !='' and meta.brd_Dd<>'00000000' then char(date(insert(insert(meta.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	else char(date(insert(insert(meta.FM_DT,5,0,'-'),8,0,'-')))   ");
		buf.append("\n 	end as BRD_DD  ");
		buf.append("\n 	,value(inst.VD_HRESOL,'') as VD_HRESOL  ");
		buf.append("\n 	,value(inst.VD_VRESOL ,'') as VD_VRESOL ");
		buf.append("\n 	,value(inst.FL_SZ ,0) as FL_SZ ");
		buf.append("\n 	,value(inst.BIT_RT ,'') as BIT_RT ");
		buf.append("\n 	,value(inst.AUD_SAMP_FRQ,'') as AUD_SAMP_FRQ ");
		buf.append("\n 	,value(inst.AUDIO_BDWT,'') as AUDIO_BDWT ");
		buf.append("\n 	,value(inst.RECORD_TYPE_CD,'') as RECORD_TYPE_CD ");
		buf.append("\n 	,value(inst.FRM_PER_SEC,'') as FRM_PER_SEC ");
		buf.append("\n 	,value(con.VD_QLTY ,'') as VD_QLTY ");
		buf.append("\n 	,value(con.CT_CLA ,'') as CT_CLA ");
		buf.append("\n 	,value(con.CT_LENG ,'') as CT_LENG ");
		buf.append("\n 	,value(con.ASP_RTO_CD ,'') as ASP_RTO_CD ");
		buf.append("\n 	,value(con.CT_TYP ,'') as CT_TYP ");
		buf.append("\n 	,value(corner.SOM ,'') as SOM ");
		buf.append("\n 	,value(corner.EOM ,'') as EOM");

		buf.append("\n 	,value(cart.CART_SEQ,'') as CART_SEQ ");
		buf.append("\n 	,value(cart.CART_NO,'') as CART_NO ");
		buf.append("\n 	,value(down.req_usrid ,'') as req_usrid");
		buf.append("\n  ,value(inst.org_file_nm,'') as filename ");
		buf.append("\n  ,value(inst.wrk_file_nm,'') as wrk_filename ");
		buf.append("\n 	,value(cart.logical_tree ,'') as logical_tree");
		buf.append("\n 	,value(cart.phyical_tree ,'') as phyical_tree");
		buf.append("\n  ,down.down_subj||'_'||value(cart.MEDIA_ID,'')||'_HR' as rename ");
		buf.append("\n 	from  down_cart_Tbl down  ");
		buf.append("\n 	inner join cart_cont_tbl cart on cart.CART_NO=down.CART_NO ");
		buf.append("\n 	inner join contents_inst_tbl inst on inst.CT_ID = cart.CT_ID and inst.cti_fmt like '10%'  ");
		buf.append("\n 	 inner join contents_tbl con on con.CT_ID = inst.CT_ID ");
		buf.append("\n 	inner join contents_mapp_tbl  map on map.ct_id = con.ct_id ");
		buf.append("\n 	inner join metadat_mst_tbl meta on meta.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n inner join CORNER_TBL corner on corner.CN_ID = map.CN_ID ");
		buf.append("\n 		left outer join pds_pgminfo_tbl2 pgm on meta.PDS_CMS_PGM_ID = pgm.PROGRAM_CODE ");
		//	buf.append("\n 	 inner join CONTENTS_DOWN_TBL cdown on cdown.cart_no = cart.cart_no   and cdown.cart_seq = cart.cart_seq ");
		buf.append("\n where  ");
		buf.append("\n  cart.cart_no ="+cartNo+" and cart.cart_seq= "+cartSeq+" ");

		return buf.toString();
	}

	/**
	 * 메타데이타 마스터 정보를 조회한다.
	 * @param conditionDO 조회 조건
	 */
	/*public static final String selectMetadatInfoQuery2(WorkStatusConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();

		String[] McuidYn =  conditionDO.getMcuidYn().split(",");
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		if (conditionDO.getQueryResultCount() == false)
		{
				buf.append(" \n select * from (");
		}
		else{
			buf.append("\n select count(*) as CCOUNT ,sum(sum_brd_leng) as sum_brd_leng from ( ");
		}
		buf.append("\n select ");
		buf.append("\n 	A.master_id, ");
		buf.append("\n 	A.tape_item_id, ");
		buf.append("\n 	A.mcuid, ");
		buf.append("\n 	A.data_stat_cd as data_stat_cd, ");
		buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
		buf.append("\n 	A.error_stat_cd as error_stat_cd, ");
		buf.append("\n 	C.clf_nm as clf_nm, ");
		buf.append("\n 	C.desc as desc, ");
		buf.append("\n 	case when (a.ctgr_l_cd='200') and ( a.pgm_id <> 0 ) and pgm.pgm_nm is not null  then pgm.pgm_nm ");
		buf.append("\n 	when (a.ctgr_l_cd='200') and (a.pgm_id =0 or a.pgm_id is null) then a.title ");
		buf.append("\n 	when a.ctgr_l_cd='100' then a.title ");
		buf.append("\n 	else  a.title  ");
		buf.append("\n  end as title, ");
		buf.append("\n 	 value (user2.USER_NM, '') as USER_NM, ");
		buf.append("\n 	A.reg_dt as reg_dt, ");
		buf.append("\n 	value(A.req_cd,'') as req_cd, ");
		buf.append("\n 	A.brd_leng as brd_leng, ");
		buf.append("\n 	 case when a.ctgr_l_cd='200' then a.brd_dd ");
		buf.append("\n 	when a.ctgr_l_cd='100' then a.fm_dt ");
		buf.append("\n 	else a.brd_dd ");
		buf.append("\n 	 end  	as brd_dd,  ");
		buf.append("\n 	value(a.epis_no,'0') as epis_no, ");
		buf.append("\n 	A.ing_reg_dd as ing_reg_dd, ");
		buf.append("\n 	A.arch_reg_dd as arch_reg_dd,  ");	
		buf.append("\n 	A.ctgr_l_cd as ctgr_l_cd, ");
		buf.append("\n 	A.FM_DT as fm_dt, ");
		buf.append("\n 	A.modrid as modrid, ");
		buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
		buf.append("\n  value(MAP.CT_ID,0) as CT_ID , ");
		buf.append("\n  value(inst.CTI_ID ,0) as cti_id , ");
		buf.append("\n  CODE.DESC AS CT_CLA_NM, ");
		buf.append("\n  CODE2.DESC AS CTGR_L_NM, ");
		buf.append("\n 	B.COUNT, ");
		buf.append("\n 	DECODE(RMP.PARENT_MASTER_ID, NULL, 'N','Y') AS LINK_PARENT, ");
		if (!conditionDO.getQueryResultCount() == false)
		{
		buf.append("\n  CASE "); 
	 	buf.append("\n  when A.BRD_LENG is not null AND A.BRD_LENG <> '' "); 
		buf.append("\n 	  THEN bigint(SUBSTR(A.BRD_LENG,1,2))+bigint(substr(A.BRD_LENG,4,2))*60+bigint(substr(A.BRD_LENG,7,2)) "); 
		buf.append("\n  ELSE 0 ");
		buf.append("\n END SUM_BRD_LENG, ");    
		}
		buf.append("\n 	ROW_NUMBER() OVER( ");	

		if(conditionDO.getSortColume().length()>0){
			buf.append("\n ORDER BY A."+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}else{
			buf.append("\n ORDER BY A.ing_reg_dd desc  ");
		}
		buf.append("\n   ) AS rownum ");
		buf.append("\n from (SELECT * FROM das.metadat_mst_tbl  WHERE 1=1   ");	

		if(!conditionDO.getMcuidYn().equals("")){
			buf.append("\n 	 AND ( ");
			for(int i =0; i<McuidYn.length;i++){
			if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
			{
				// mcuidYn=N 매체변환
			//	buf.append("\n 	 (A.mcuid is null or A.mcuid = '') ");
				buf.append("\n 	 arch_route LIKE 'D%' ");
			}
			else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
			{   // mcuidYn=Y 온에어(주조)
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	 ( ");
				buf.append("\n  arch_route LIKE 'O%' ");
				buf.append("\n 	 ) ");

			}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
				//PDS
				if(i!=0){
					buf.append("\n 	 or  ");
				}
				buf.append("\n 	( arch_route ='P' )");

			}
			}
			buf.append("\n 	 ) ");
		}
		buf.append("\n  ) A ");
		buf.append("\n LEFT OUTER JOIN (SELECT CT_ID,MASTER_ID FROM CONTENTS_MAPP_TBL GROUP BY CT_ID ,MASTER_ID )   MAP ON MAP.MASTER_ID=A.MASTER_ID and map.CT_ID =a.RPIMG_CT_ID ");	
		buf.append("\n  LEFT OUTER JOIN  CONTENTS_TBL CON  ON CON.CT_ID=MAP.CT_ID AND CON.CT_TYP = '003' ");	
		buf.append("\n    LEFT OUTER JOIN USER_INFO_TBL USER2 ON USER2.SBS_USER_ID=A.MODRID ");	
		buf.append("\n    LEFT OUTER JOIN USER_INFO_TBL USER3 ON USER3.SBS_USER_ID=a.ACCEPTOR_ID ");
		buf.append("\n     LEFT OUTER JOIN contents_inst_tbl inst ON inst.CT_id = map.CT_ID and cti_fmt like '%10%' ");	

		buf.append("\n     LEFT OUTER JOIN CODE_TBL CODE ON CODE.SCL_CD = CON.CT_CLA AND CODE.CLF_CD='A001'");	
		buf.append("\n    LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.SCL_CD = A.CTGR_L_CD AND CODE2.CLF_CD='P002' ");	
		buf.append("\n   LEFT OUTER JOIN AUTO_ARCHVIE_TBL AUTO ON AUTO.SCL_CD =  CON.ct_cla  ");	
		buf.append("\n left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = a.PGM_ID  ");	
		buf.append("\n  left outer join DAS.RELATION_MASTER as rmp on a.master_id = rmp.PARENT_MASTER_ID  ");	

		buf.append("\n inner  JOIN ( select z.master_id, count(*) as COUNT  ");
		buf.append("\n from (select distinct master_id, ct_id from das.contents_mapp_tbl mapp where ( mapp.del_dd = '' or mapp.del_dd is null)) z  ");
		buf.append("\n group by z.master_id) B ON A.master_id = B.master_id ");

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())
				||DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			buf.append("\n INNER JOIN (      ");
			buf.append("\n  select distinct MAP.MASTER_ID, CT.VD_QLTY, CT.ASP_RTO_CD ");
			buf.append("\n  from DAS.CONTENTS_TBL CT, ");
			buf.append("\n  DAS.CONTENTS_MAPP_TBL MAP   ");
			buf.append("\n  where   	MAP.CT_ID = CT.CT_ID  ");
			buf.append("\n  and ( MAP.del_dd = '')    ");
			buf.append("\n  )  CTT on CTT.master_id = A.master_id ");
		}

		buf.append("\n  LEFT OUTER JOIN CODE_TBL C ON C.SCL_CD =  A.data_stat_cD AND C.clf_cd = 'P011' ");

		// 검수자 이름으로 검색한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)			
				buf.append(" , DAS.ERP_COM_USER_TBL USER ");
		}

		buf.append("\n where  ");


		buf.append("\n 		length(trim(A.DEL_DD))<1 ");
		buf.append("\n  AND (a.manual_yn='N' OR a.manual_yn IS NULL )");
		buf.append("\n 	AND (( (  a.ARCH_REG_DD <>'')OR( AUTO.AUTO_YN='Y')OR(AUTO.AUTO_YN='N' AND INST.ARCH_STE_YN='Y') or((AUTO.AUTO_YN='N' AND con.MEDIA_ID like 'D%'))OR (A.ARCH_ROUTE LIKE 'P%'))   or ( (  a.ARCH_REG_DD <>'')OR( AUTO.AUTO_YN='Y')OR(AUTO.AUTO_YN='N' AND INST.ARCH_STE_YN='Y') or((AUTO.AUTO_YN='N' AND con.MEDIA_ID like 'D%')) or((A.ARCH_ROUTE LIKE 'D%' AND A.MANUAL_YN='N'))) )         ");

	 *//**
	 * 영상 삭제 또는 폐기시에 금일 기준으로 삭제가 되지 않은 마스터에 관련영상 정보와 조인하여 해당 마스터의 차일드 마스터를
	 * 리스트 상에 제외 시켜준다(2011.05.20 by Dekim) 삭제스케줄러가 클립단위로 삭제되기에 해당 마스터건에 대해서 판단하기가 곤란함. 
	 *//*
		buf.append("\n 		AND (A.master_ID NOT IN ( ");
		buf.append("\n            SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM ");
		buf.append("\n            INNER JOIN ( SELECT MASTER_ID FROM METADAT_MST_TBL  ");
		buf.append("\n            WHERE DEL_DD IS NULL OR DEL_DD > TS_FMT(CURRENT DATE,'yyyymmdd') or REPLACE(del_dd,' ','')='' ");
		buf.append("\n          ) PM ON PM.MASTER_ID =RM.PARENT_MASTER_ID  ");
		buf.append("\n          GROUP BY CHILD_MASTER_ID ");
		buf.append("\n      ) ) ");


		if(!conditionDO.getCtgr_l_cd().equals("")){
			// 100: 소재  200: 프로그램  
			if(conditionDO.getCtgr_l_cd().equals("200")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
			}else if(!conditionDO.getCtgr_l_cd().equals("")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD <> '200' ");
			}
			//buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
		}

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n  AND  CTT.ASP_RTO_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
			buf.append("\n  AND  (CTT.ASP_RTO_CD <> '' OR CTT.ASP_RTO_CD IS NULL  )");	
			}
		}
		if(DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n 	AND CTT.VD_QLTY = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n 	AND (CTT.VD_QLTY <> '' OR CTT.VD_QLTY IS NULL) ");	
			}
		}
		if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())){
		buf.append("\n  AND  A.rist_clf_Cd = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.VIEW_GR_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n  AND  A.VIEW_GR_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.VIEW_GR_CD <> '' OR A.VIEW_GR_CD IS NULL or A.VIEW_GR_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CPRT_TYPE.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n  AND  A.CPRT_TYPE = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.CPRT_TYPE <> '' OR A.CPRT_TYPE IS NULL or A.CPRT_TYPE = '')");	
			}
		}
		if(DASBusinessConstants.SearchCombo.RSV_PRD_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (A.RSV_PRD_CD <> '' OR A.RSV_PRD_CD IS NULL   or A.RSV_PRD_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CT_CLA.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
			buf.append("\n  AND  CON.CT_CLA= '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (CON.CT_CLA <> '' OR CON.CT_CLA IS NULL  or CON.CT_CLA = '') ");
			}
		}

		if(CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and ( ( A.fm_dt >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and A.fm_dt <= '"+conditionDO.getToDate()+"' ) ");
			}

			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	or ( A.brd_dd >= '"+conditionDO.getFromDate()+"' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and A.brd_dd <= '"+conditionDO.getToDate()+"' ) )");
			}
		}		
		else if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and A.arrg_end_dt >= '"+conditionDO.getFromDate()+"000000' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and A.arrg_end_dt <= '"+conditionDO.getToDate()+"235959' ");
			}
		}		 
		else if(CodeConstants.WorkStatusDateFlag.ACCEPT_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	and A.accept_end_dd >= '"+conditionDO.getFromDate()+"000000' ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	and A.accept_end_dd <= '"+conditionDO.getToDate()+"235959' ");
			}
		}					
		//	String[] McuidYn =  conditionDO.getMcuidYn().split(",");
		//	
		//	if(!conditionDO.getMcuidYn().equals("")){
		//		buf.append("\n 	 AND ( ");
		//		for(int i =0; i<McuidYn.length;i++){
		//		if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
		//		{
		//			// mcuidYn=N 매체변환
		//		//	buf.append("\n 	 (A.mcuid is null or A.mcuid = '') ");
		//			buf.append("\n 	 A.arch_route LIKE 'D%' ");
		//		}
		//		else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
		//		{   // mcuidYn=Y 온에어(주조)
		//			if(i!=0){
		//				buf.append("\n 	 or  ");
		//			}
		//			buf.append("\n 	 ( ");
		//			buf.append("\n  A.arch_route LIKE 'O%' ");
		//			buf.append("\n 	 ) ");
		//			
		//		}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
		//			//PDS
		//			if(i!=0){
		//				buf.append("\n 	 or  ");
		//			}
		//			buf.append("\n 	( A.arch_route ='P' )");
		//	
		//		}
		//		}
		//		buf.append("\n 	 ) ");
		//	}
//		등록일 검색
		if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(conditionDO.getDateKind()))
		{
			if(!StringUtils.isEmpty(conditionDO.getFromDate()))
			{
				buf.append("\n 	AND ( A.ING_REG_DD >= '"+conditionDO.getFromDate()+"' or  A.ING_REG_DD >= '"+conditionDO.getFromDate()+"000000') ");
			}
			if(!StringUtils.isEmpty(conditionDO.getToDate()))
			{
				buf.append("\n 	AND ( A.ING_REG_DD <= '"+conditionDO.getToDate()+"235959' or A.ING_REG_DD <= '"+conditionDO.getToDate()+"235959' )");
			}
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
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))			

		{
			isDateCondition = true;
		}


		if(isDateCondition)
		{
			buf.append("\n 	AND ( ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");

			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
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

		// 검색어를 입력한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_NM") == true)			
				buf.append("\n and SEC_ARCH_NM = '" + conditionDO.getSearchKey() + "'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_ID") == true)			
				buf.append("\n and SEC_ARCH_ID = '" + conditionDO.getSearchKey() + "'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("REQ_CD") == true)			
				buf.append("\n and REQ_CD like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("TITLE") == true){			

							buf.append("\n and ((A.TITLE like '%" + conditionDO.getSearchKey() + "%'  )");
							buf.append("\n or (pgm.pgm_nm like '%" + conditionDO.getSearchKey() + "%'   ))");


					 }


			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_ID") == true)			
				buf.append("\n and a.ACCEPTOR_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)
			{

				buf.append("\n and USER3.USER_NM like '%" + conditionDO.getSearchKey() + "%'");
			}

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("MASTER_ID") == true)			
				buf.append("\n and A.MASTER_ID = " + conditionDO.getSearchKey() + " ");
		}

		if(conditionDO.getSortColume().length()>0){
			buf.append("\n order by "+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}else{
			buf.append("\n order by a.ing_reg_dd desc ");
		}
		if (conditionDO.getQueryResultCount() == false)
		{
			// rownum 으로 갯수를 제한하는 경우라면 
			if ( (conditionDO.getStartPos() > 0) && (conditionDO.getEndPos() > 0) )
			{
				buf.append(" \n ) as t ");
				buf.append("\n where rownum between " );
				buf.append(conditionDO.getStartPos() );
				buf.append(" and " );
				buf.append(conditionDO.getEndPos() );
			}
		}
		else	// 갯수만 가져오는 경우라면 
			buf.append(" \n ) as t ");

		buf.append("\n with ur");

		return buf.toString();		

	}
	  */
	
	public static final String selectNewMetadatInfoQuery(WorkStatusConditionDO conditionDO, String flag) {
		StringBuffer buf = new StringBuffer();
		
		buf.append("\nSELECT                                                                                                                                            ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(flag)) {
			buf.append("\n	count(*) as CCOUNT,  bigint(sum(sum_brd_leng)*29.97)  as sum_brd_leng                                                                                                         ");
		} else {
			buf.append("\n	t.*, CT_ROWS_COUNT(t.master_id) count                                                                                                           ");
		}
		
		buf.append("\nFROM (                                                                                                                                            ");
		buf.append("\n	SELECT                                                                                                                                          ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(flag)) {
			buf.append("\n		CASE                                                                                                                                ");
			buf.append("\n 			WHEN mst.BRD_LENG IS NOT NULL AND VALUE(mst.BRD_LENG, '') <> ''                                                                                                                             ");
			buf.append("\n 				THEN bigint(SUBSTR(mst.BRD_LENG,1,2))*60*60+bigint(substr(mst.BRD_LENG,4,2))*60+bigint(substr(mst.BRD_LENG,7,2)) +bigint(substr(mst.BRD_LENG,10,2)/29.97)                                                                                                                                    ");
			buf.append("\n 			ELSE 0                                                                                                            ");
			buf.append("\n 		END AS sum_brd_leng                                                                                                             ");
		} else {
			buf.append("\n		mst.master_id,                                                                                                                                ");
			buf.append("\n 		mst.tape_item_id,                                                                                                                             ");
			buf.append("\n 		mst.mcuid,                                                                                                                                    ");
			buf.append("\n 		mst.data_stat_cd as data_stat_cd,                                                                                                             ");
			buf.append("\n 		mst.lock_stat_cd as lock_stat_cd,                                                                                                             ");
			buf.append("\n 		mst.error_stat_cd as error_stat_cd,                                                                                                           ");
			buf.append("\n 		code3.clf_nm as clf_nm,                                                                                                                       ");
			buf.append("\n 		code3.desc as desc,                                                                                                                           ");
			buf.append("\n 		case                                                                                                                                          ");
			buf.append("\n 			when (mst.ctgr_l_cd='200') and ( mst.pgm_id <> 0 ) and pgm.pgm_nm is not null  then pgm.pgm_nm                                              ");
			buf.append("\n 			when (mst.ctgr_l_cd='200') and (mst.pgm_id =0 or mst.pgm_id is null) then mst.title                                                         ");
			buf.append("\n 			when mst.ctgr_l_cd='100' then mst.title                                                                                                     ");
			buf.append("\n 			else  mst.title                                                                                                                             ");
			buf.append("\n  		end as title,                                                                                                                               ");
			buf.append("\n 		value (user.USER_NM, '') as USER_NM,                                                                                                          ");
			buf.append("\n 		mst.reg_dt as reg_dt,                                                                                                                         ");
			buf.append("\n 		value(mst.req_cd,'') as req_cd,                                                                                                               ");
			buf.append("\n 		mst.brd_leng as brd_leng,                                                                                                                     ");
			buf.append("\n 		case                                                                                                                                          ");
			buf.append("\n 			when mst.ctgr_l_cd='200' then mst.brd_dd                                                                                                    ");
			buf.append("\n 			when mst.ctgr_l_cd='100' then mst.fm_dt                                                                                                     ");
			buf.append("\n 			else mst.brd_dd                                                                                                                             ");
			buf.append("\n 		end as brd_dd,                                                                                                                                ");
			buf.append("\n 		value(mst.epis_no,0) as epis_no,                                                                                                              ");
			buf.append("\n 		mst.ing_reg_dd as ing_reg_dd,                                                                                                                 ");
			buf.append("\n 		mst.arch_reg_dd as arch_reg_dd,                                                                                                               ");
			buf.append("\n 		mst.ctgr_l_cd as ctgr_l_cd,                                                                                                                   ");
			buf.append("\n 		mst.FM_DT as fm_dt,                                                                                                                           ");
			buf.append("\n 		mst.modrid as modrid,                                                                                                                         ");
			buf.append("\n  		value(mapp.CT_ID,0) as CT_ID ,                                                                                                              ");
			buf.append("\n  		value(inst.CTI_ID ,0) as cti_id ,                                                                                                           ");
			buf.append("\n  		code1.DESC AS CT_CLA_NM,                                                                                                                    ");
			buf.append("\n  		code2.DESC AS CTGR_L_NM,                                                                                                                    ");
			buf.append("\n 		DECODE(rm.PARENT_MASTER_ID, NULL, 'N','Y') AS LINK_PARENT                                                                                    ");
			if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSortColume())){
				buf.append("\n      ,ROW_NUMBER() OVER(ORDER BY mst."+conditionDO.getSortColume()+" "+conditionDO.getSortValue()+" ) AS rownum                                     ");
			} else {
				buf.append("\n    	,ROW_NUMBER() OVER(ORDER BY mst.ing_reg_dd DESC, mst.MASTER_ID asc) AS rownum                                                              ");
			}
		}
		buf.append("\n	FROM METADAT_MST_TBL mst                                                                                                                        ");
		buf.append("\n		INNER JOIN (SELECT master_id, ct_id FROM CONTENTS_MAPP_TBL WHERE del_yn = 'N' AND VALUE(del_dd, '') = '' GROUP BY master_id, ct_id) mapp      ");
		buf.append("\n	    	ON mst.MASTER_ID = mapp.MASTER_ID                                                                                                         ");
		buf.append("\n	    INNER JOIN CONTENTS_TBL ct ON mapp.CT_ID = ct.CT_ID                                                                                         ");
		buf.append("\n	    INNER JOIN CONTENTS_INST_TBL inst ON ct.CT_ID = inst.CT_ID AND inst.CTI_FMT LIKE '1%'                                                       ");
		buf.append("\n	    LEFT OUTER JOIN PGM_INFO_TBL pgm ON mst.PGM_ID = pgm.PGM_ID                                                                                 ");
		buf.append("\n	    LEFT OUTER JOIN RELATION_MASTER rm ON mst.MASTER_ID = rm.PARENT_MASTER_ID                                                                   ");
		buf.append("\n	    LEFT OUTER JOIN CODE_TBL code1 ON code1.SCL_CD = ct.CT_CLA AND code1.CLF_CD='A001'                                                          ");
		buf.append("\n	    LEFT OUTER JOIN CODE_TBL code2 ON code2.SCL_CD = mst.CTGR_L_CD AND code2.CLF_CD='P002'                                                      ");
		buf.append("\n	    LEFT OUTER JOIN CODE_TBL code3 ON code3.SCL_CD = mst.DATA_STAT_CD AND code3.CLF_CD='P051'                                                   ");
		buf.append("\n	    LEFT OUTER JOIN USER_INFO_TBL USER ON USER.SBS_USER_ID = mst.MODRID                                                                         ");
		buf.append("\n	WHERE 1=1 AND ct.CT_TYP = '003'                                                                                                                  ");
		buf.append("\n		AND NOT EXISTS (                                                                                                                              ");
		buf.append("\n			SELECT 1 FROM DISCARD_INFO_TBL dis WHERE mst.MASTER_ID = dis.MASTER_ID                                                                      ");
		buf.append("\n		)                                                                                                                                             ");
		buf.append("\n	    AND NOT EXISTS (                                                                                                                            ");
		buf.append("\n	    	SELECT 1 FROM RELATION_MASTER rel WHERE mst.MASTER_ID = rel.CHILD_MASTER_ID                                                               ");
		buf.append("\n	    )                                                                                                                                           ");
		buf.append("\n	    AND VALUE(mst.DEL_DD, '') = ''                                                                                                              ");
		buf.append("\n	    AND (mst.MANUAL_YN = 'N' OR mst.MANUAL_YN IS null)                                                                                          ");
		buf.append("\n		AND                                                                                                                                           ");
		buf.append("\n	    ((                                                                                                                                          ");
		buf.append("\n	    	VALUE(mst.ARCH_REG_DD, '') <> ''                                                                                                          ");
		buf.append("\n	        OR (mst.MANUAL_YN='Y' AND inst.ARCH_STE_YN='Y')                                                                                         ");
		buf.append("\n	        OR (mst.MANUAL_YN='Y' AND ct.MEDIA_ID like 'D%')                                                                                        ");
		buf.append("\n	        OR (mst.ARCH_ROUTE LIKE 'P%')                                                                                                           ");
		buf.append("\n	    ) OR (                                                                                                                                      ");
		buf.append("\n	    	VALUE(mst.ARCH_REG_DD, '') <> ''                                                                                                          ");
		buf.append("\n	        OR (mst.MANUAL_YN='Y' AND inst.ARCH_STE_YN='Y')                                                                                         ");
		buf.append("\n	        OR (mst.MANUAL_YN='Y' AND ct.MEDIA_ID like 'D%')                                                                                        ");
		buf.append("\n	        OR (mst.ARCH_ROUTE LIKE 'D%' AND mst.MANUAL_YN='N')                                                                                     ");
		buf.append("\n	    ))                                                                                                                                          ");
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getMcuidYn())) {
			String[] McuidYn =  conditionDO.getMcuidYn().split(",");
			buf.append("\n AND ( ");
			for(int i =0; i<McuidYn.length;i++){
				if(i!=0){
					buf.append("\n or ");
				}
				if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI)) { // 매체변환
					buf.append("\n mst.arch_route LIKE 'D%' ");
				} else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR)) { // 주조
					buf.append("\n mst.arch_route LIKE 'O%'");
				} else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)) {
					buf.append("\n mst.arch_route ='P'");
				}
			}
			buf.append("\n ) ");
		}
		
		// 프로그램 카테고리 조건
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getCtgr_l_cd())) {
			if("200".equals(conditionDO.getCtgr_l_cd())){
				buf.append("\n  AND mst.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
			} else {
				buf.append("\n  AND mst.CTGR_L_CD <> '200' ");
			}
			//buf.append("\n  AND mst.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchCombo())) {
			if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())){
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  ct.ASP_RTO_CD = '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND  (ct.ASP_RTO_CD <> '' OR ct.ASP_RTO_CD IS NULL  )");	
				}
			} else if(DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  ct.VD_QLTY = '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND  (ct.VD_QLTY <> '' OR ct.VD_QLTY IS NULL  )");	
				}
			} else if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())) {
				buf.append("\n  AND  mst.rist_clf_Cd = '"+conditionDO.getSearchComboValue()+"' ");
			} else if(DASBusinessConstants.SearchCombo.VIEW_GR_CD.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  mst.VIEW_GR_CD = '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND ( OR mst.VIEW_GR_CD IS NULL or mst.VIEW_GR_CD = '' ) ");
				}
			} else if(DASBusinessConstants.SearchCombo.CPRT_TYPE.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  mst.CPRT_TYPE = '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND ( mst.CPRT_TYPE IS NULL or mst.CPRT_TYPE = '')");	
				}
			} else if(DASBusinessConstants.SearchCombo.RSV_PRD_CD.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  mst.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND  (mst.RSV_PRD_CD IS NULL or mst.RSV_PRD_CD = '' ) ");
				}
			} else if(DASBusinessConstants.SearchCombo.CT_CLA.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  ct.CT_CLA= '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND  (ct.CT_CLA IS NULL or ct.CT_CLA = '') ");
				}
			} else if(DASBusinessConstants.SearchCombo.TAPE_KIND.equals(conditionDO.getSearchCombo())) {
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchComboValue())) {
					buf.append("\n  AND  mst.TAPE_MEDIA_CLF_CD= '"+conditionDO.getSearchComboValue()+"' ");
				} else {
					buf.append("\n  AND  (mst.TAPE_MEDIA_CLF_CD IS NULL or mst.TAPE_MEDIA_CLF_CD = '') ");
				}
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getDateKind())) {
			String[] dateGb = conditionDO.getDateKind().split(",");
			for(int i=0; i < dateGb.length; i++) {
				buf.append("\n  AND ");
				if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getFromDate())) {
					if (CodeConstants.WorkStatusDateFlag.REG_DATE.equals(String.valueOf(dateGb[i]))) {
						buf.append("\n (SUBSTR(mst.REG_DT, 1, 8) between '"+conditionDO.getFromDate()+"' and '"+conditionDO.getToDate()+"')  ");
					} else if (CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(String.valueOf(dateGb[i]))) {
						buf.append("\n ( ");
						buf.append("\n 		(SUBSTR(mst.fm_dt, 1, 8) between '"+conditionDO.getFromDate()+"' and '"+conditionDO.getToDate()+"')  ");
						buf.append("\n 		or ");
						buf.append("\n 		(SUBSTR(mst.brd_dd, 1, 8) between '"+conditionDO.getFromDate()+"' and '"+conditionDO.getToDate()+"')  ");
						buf.append("\n ) ");
					} else if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(String.valueOf(dateGb[i]))) {
						buf.append("\n (SUBSTR(mst.arrg_end_dt, 1, 8) between '"+conditionDO.getFromDate()+"' and '"+conditionDO.getToDate()+"')  ");
					} else if(CodeConstants.WorkStatusDateFlag.ACCEPT_DATE.equals(String.valueOf(dateGb[i]))) {
						buf.append("\n (SUBSTR(mst.accept_end_dd, 1, 8) between '"+conditionDO.getFromDate()+"' and '"+conditionDO.getToDate()+"')  ");
					}
				}
			}
		}
		String dataStatCd = "";
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.ARRANGE_BEFORE+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.ARRANGE_BEFORE+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())) {
			/*dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? 
					"'"+CodeConstants.DataStatusCode.ARRANGE_ING+"'"+","+"'"+CodeConstants.DataStatusCode.EDIT_ING+"'"+","+"'"+CodeConstants.DataStatusCode.PRE_EDIT+"'" 
					: dataStatCd+","+"'"+CodeConstants.DataStatusCode.ARRANGE_ING+"'"+","+"'"+CodeConstants.DataStatusCode.EDIT_ING+"'"+","+"'"+CodeConstants.DataStatusCode.PRE_EDIT+"'"
			;*/
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.ARRANGE_ING+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.ARRANGE_ING+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.ARRANGE_COMPLET+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.COMPLET+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.COMPLET+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.RE_ORDERS+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.RE_ORDERS+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.ARCHIVE+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.ARCHIVE+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.RE_ARCHIVE+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.RE_ARCHIVE+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.STARTINGYN+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.STARTINGYN+"'";
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn())) {
			dataStatCd = (StringUtils.isEmpty(dataStatCd)) ? "'"+CodeConstants.DataStatusCode.ERROR+"'" : dataStatCd+","+"'"+CodeConstants.DataStatusCode.ERROR+"'";
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(dataStatCd)) {
			buf.append("\n AND mst.DATA_STAT_CD in ("+dataStatCd+")");
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getColumnName())
				&& org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getSearchKey())) {
			if("SEC_ARCH_NM".equals(conditionDO.getColumnName())) {
				buf.append("\n and mst.SEC_ARCH_NM like '%" + conditionDO.getSearchKey() + "%'");
			} else if("SEC_ARCH_ID".equals(conditionDO.getColumnName())) {
				buf.append("\n and mst.SEC_ARCH_ID like '%" + conditionDO.getSearchKey() + "%'");
			} else if("REQ_CD".equals(conditionDO.getColumnName())) {
				buf.append("\n and mst.REQ_CD like '%" + conditionDO.getSearchKey() + "%'");
			} else if("TITLE".equals(conditionDO.getColumnName())) {
				buf.append("\n and (mst.TITLE like '%" + conditionDO.getSearchKey() + "%'");
				buf.append(" or pgm.pgm_nm like '%" + conditionDO.getSearchKey() + "%')");
			} else if("ACCEPTOR_ID".equals(conditionDO.getColumnName())) {
				buf.append("\n and mst.ACCEPTOR_ID like '%" + conditionDO.getSearchKey() + "%'");
			} else if("ACCEPTOR_NM".equals(conditionDO.getColumnName())) {
				buf.append("\n and user.USER_NM like '%" + conditionDO.getSearchKey() + "%'");
			} else if("MASTER_ID".equals(conditionDO.getColumnName())) {
				buf.append("\n and mst.MASTER_ID = '" + conditionDO.getSearchKey() + "'");
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getArchiveYn())) {
			if(conditionDO.getArchiveYn().equals("Y")){
				buf.append(" AND value(mst.ARCH_REG_DD,'') <>'' ");	
			} else {
				buf.append(" AND value(mst.ARCH_REG_DD,'') ='' ");
			}
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getCocd())) {
			buf.append(" AND mst.cocd= '" +conditionDO.getCocd()+"'");
		}
		
		if(org.apache.commons.lang.StringUtils.isNotBlank(conditionDO.getChennel())) {
			buf.append(" AND mst.chennel_cd= '" +conditionDO.getChennel()+"'");	
		}
		
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(flag)) {
			buf.append("\n) AS t                                                                                                           ");
		} else {
			buf.append("\n) AS t WHERE T.ROWNUM BETWEEN "+conditionDO.getStartPos()+" AND "+conditionDO.getEndPos()+"                                                                                                           ");
		}
		buf.append("\nWITH ur                                                                                                                                           ");
		
		return buf.toString();
	}
	
	@Deprecated
	public static final String selectMetadatInfoQuery2(WorkStatusConditionDO conditionDO)
	{
		StringBuffer buf = new StringBuffer();

		String[] McuidYn =  conditionDO.getMcuidYn().split(",");
		// row 수를 구분하려고 하면 그에 맞게 쿼리문을 수정한다.
		if (conditionDO.getQueryResultCount() == false)
		{
			buf.append(" \n select t.*, CT_ROWS_COUNT(t.master_id) count from (");
		}
		else{
			buf.append("\n select count(*) as CCOUNT ,sum(sum_brd_leng) as sum_brd_leng from ( ");
		}
		buf.append("\n select ");
		buf.append("\n k.master_id ");
		buf.append("\n ,k.tape_item_id ");
		buf.append("\n ,k.mcuid ");
		buf.append("\n ,k.data_stat_cd ");
		buf.append("\n ,k.lock_stat_cd ");
		buf.append("\n ,k.error_stat_cd ");
		buf.append("\n ,k.clf_nm ");
		buf.append("\n ,k.desc ");
		buf.append("\n ,k.title ");
		buf.append("\n ,k.USER_NM ");
		buf.append("\n ,k.reg_dt ");
		buf.append("\n ,k.req_cd ");
		buf.append("\n ,k.brd_leng ");
		buf.append("\n ,k.brd_dd ");
		buf.append("\n ,k.epis_no ");
		buf.append("\n ,k.ing_reg_dd ");
		buf.append("\n ,k.arch_reg_dd ");
		buf.append("\n ,k.ctgr_l_cd ");
		buf.append("\n ,k.fm_dt ");
		buf.append("\n ,k.modrid ");
		buf.append("\n ,k.CT_ID ");
		buf.append("\n ,k.cti_id ");
		buf.append("\n ,k.CT_CLA_NM ");
		buf.append("\n ,k.CTGR_L_NM ");
		//buf.append("\n ,k.COUNT ");
		buf.append("\n ,k.LINK_PARENT ");
		buf.append("\n ,ROW_NUMBER() OVER( ");	

		if(conditionDO.getSortColume().length()>0){
			buf.append("\n ORDER BY k."+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}else{
			buf.append("\n ORDER BY k.ing_reg_dd desc  ");
		}
		buf.append("\n   ) AS rownum ");
		buf.append("\n from ( ");

		buf.append("\n select ");
		buf.append("\n 	A.master_id, ");
		buf.append("\n 	A.tape_item_id, ");
		buf.append("\n 	A.mcuid, ");
		buf.append("\n 	A.data_stat_cd as data_stat_cd, ");
		buf.append("\n 	A.lock_stat_cd as lock_stat_cd, ");
		buf.append("\n 	A.error_stat_cd as error_stat_cd, ");
		buf.append("\n 	C.clf_nm as clf_nm, ");
		buf.append("\n 	C.desc as desc, ");
		buf.append("\n 	case when (a.ctgr_l_cd='200') and ( a.pgm_id <> 0 ) and pgm.pgm_nm is not null  then pgm.pgm_nm ");
		buf.append("\n 	when (a.ctgr_l_cd='200') and (a.pgm_id =0 or a.pgm_id is null) then a.title ");
		buf.append("\n 	when a.ctgr_l_cd='100' then a.title ");
		buf.append("\n 	else  a.title  ");
		buf.append("\n  end as title, ");
		buf.append("\n 	 value (user2.USER_NM, '') as USER_NM, ");
		buf.append("\n 	A.reg_dt as reg_dt, ");
		buf.append("\n 	value(A.req_cd,'') as req_cd, ");
		buf.append("\n 	A.brd_leng as brd_leng, ");
		buf.append("\n 	 case when a.ctgr_l_cd='200' then a.brd_dd ");
		buf.append("\n 	when a.ctgr_l_cd='100' then a.fm_dt ");
		buf.append("\n 	else a.brd_dd ");
		buf.append("\n 	 end  	as brd_dd,  ");
		buf.append("\n 	value(a.epis_no,0) as epis_no, ");
		buf.append("\n 	A.ing_reg_dd as ing_reg_dd, ");
		buf.append("\n 	A.arch_reg_dd as arch_reg_dd,  ");	
		buf.append("\n 	A.ctgr_l_cd as ctgr_l_cd, ");
		buf.append("\n 	A.FM_DT as fm_dt, ");
		buf.append("\n 	A.modrid as modrid, ");
		buf.append("\n  value(MAP.CT_ID,0) as CT_ID , ");
		buf.append("\n  value(inst.CTI_ID ,0) as cti_id , ");
		buf.append("\n  CODE.DESC AS CT_CLA_NM, ");
		buf.append("\n  CODE2.DESC AS CTGR_L_NM, ");
		//buf.append("\n 	B.COUNT, ");
		buf.append("\n 	DECODE(RMP.PARENT_MASTER_ID, NULL, 'N','Y') AS LINK_PARENT ");
		if (!conditionDO.getQueryResultCount() == false)
		{
			buf.append("\n  CASE "); 
			buf.append("\n  when A.BRD_LENG is not null AND A.BRD_LENG <> '' "); 
			buf.append("\n 	  THEN bigint(SUBSTR(A.BRD_LENG,1,2))+bigint(substr(A.BRD_LENG,4,2))*60+bigint(substr(A.BRD_LENG,7,2)) "); 
			buf.append("\n  ELSE 0 ");
			buf.append("\n END SUM_BRD_LENG ");    
		}

		buf.append("\n from (SELECT * FROM das.metadat_mst_tbl  WHERE 1=1   ");	

		if(!conditionDO.getMcuidYn().equals("")){
			buf.append("\n 	 AND ( ");
			for(int i =0; i<McuidYn.length;i++){
				if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
				{
					// mcuidYn=N 매체변환
					//	buf.append("\n 	 (A.mcuid is null or A.mcuid = '') ");
					buf.append("\n 	 arch_route LIKE 'D%' ");
				}
				else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
				{   // mcuidYn=Y 온에어(주조)
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	 ( ");
					buf.append("\n  arch_route LIKE 'O%' ");
					buf.append("\n 	 ) ");

				}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
					//PDS
					if(i!=0){
						buf.append("\n 	 or  ");
					}
					buf.append("\n 	( arch_route ='P' )");

				}
			}
			buf.append("\n 	 ) ");
		}
		buf.append("\n  ) A ");
		buf.append("\n LEFT OUTER JOIN (SELECT CT_ID,MASTER_ID FROM CONTENTS_MAPP_TBL GROUP BY CT_ID ,MASTER_ID )   MAP ON MAP.MASTER_ID=A.MASTER_ID and map.CT_ID =a.RPIMG_CT_ID ");	
		buf.append("\n  inner JOIN  CONTENTS_TBL CON  ON CON.CT_ID=MAP.CT_ID AND CON.CT_TYP = '003' ");	
		buf.append("\n    LEFT OUTER JOIN USER_INFO_TBL USER2 ON USER2.SBS_USER_ID=A.MODRID ");	
		buf.append("\n    LEFT OUTER JOIN USER_INFO_TBL USER3 ON USER3.SBS_USER_ID=a.ACCEPTOR_ID ");
		buf.append("\n     inner JOIN contents_inst_tbl inst ON inst.CT_id = map.CT_ID and cti_fmt like '%10%' ");	

		buf.append("\n     LEFT OUTER JOIN CODE_TBL CODE ON CODE.SCL_CD = CON.CT_CLA AND CODE.CLF_CD='A001'");	
		buf.append("\n    LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.SCL_CD = A.CTGR_L_CD AND CODE2.CLF_CD='P002' ");	
		//buf.append("\n   LEFT OUTER JOIN AUTO_ARCHVIE_TBL AUTO ON AUTO.SCL_CD =  CON.ct_cla  ");	
		buf.append("\n left outer join PGM_INFO_TBL pgm on pgm.PGM_ID = a.PGM_ID  ");	
		buf.append("\n  left outer join DAS.RELATION_MASTER as rmp on a.master_id = rmp.PARENT_MASTER_ID  ");	
/*
		buf.append("\n inner  JOIN ( select z.master_id, count(*) as COUNT  ");
		buf.append("\n from (select distinct master_id, ct_id from das.contents_mapp_tbl mapp where ( mapp.del_dd = '' or mapp.del_dd is null)) z  ");
		buf.append("\n group by z.master_id) B ON A.master_id = B.master_id ");
*/
		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())
				||DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			buf.append("\n INNER JOIN (      ");
			buf.append("\n  select distinct MAP.MASTER_ID, CT.VD_QLTY, CT.ASP_RTO_CD ");
			buf.append("\n  from DAS.CONTENTS_TBL CT, ");
			buf.append("\n  DAS.CONTENTS_MAPP_TBL MAP   ");
			buf.append("\n  where   	MAP.CT_ID = CT.CT_ID  ");
			buf.append("\n  and ( MAP.del_dd = '')    ");
			buf.append("\n  )  CTT on CTT.master_id = A.master_id ");
		}

		buf.append("\n  LEFT OUTER JOIN CODE_TBL C ON C.SCL_CD =  A.data_stat_cD AND C.clf_cd = 'P051' ");
		/*
		// 검수자 이름으로 검색한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)			
				buf.append(" , DAS.ERP_COM_USER_TBL USER ");
		}
		 */
		buf.append("\n where  ");


		buf.append("\n 		value(a.del_dd,'')='' ");
		buf.append("\n  AND (a.manual_yn='N' OR a.manual_yn IS NULL )");
		//buf.append("\n 	AND (( (  a.ARCH_REG_DD <>'')OR( AUTO.AUTO_YN='Y')OR(AUTO.AUTO_YN='N' AND INST.ARCH_STE_YN='Y') or((AUTO.AUTO_YN='N' AND con.MEDIA_ID like 'D%'))OR (A.ARCH_ROUTE LIKE 'P%'))   or ( (  a.ARCH_REG_DD <>'')OR( AUTO.AUTO_YN='Y')OR(AUTO.AUTO_YN='N' AND INST.ARCH_STE_YN='Y') or((AUTO.AUTO_YN='N' AND con.MEDIA_ID like 'D%')) or((A.ARCH_ROUTE LIKE 'D%' AND A.MANUAL_YN='N'))) )         ");
		buf.append("\n  AND ((  (  a.ARCH_REG_DD <>'')  ");
		buf.append("\n        OR(A.MANUAL_YN='Y' AND INST.ARCH_STE_YN='Y')  ");
		buf.append("\n        or((A.MANUAL_YN='Y' AND con.MEDIA_ID like 'D%'))");
		buf.append("\n        OR (A.ARCH_ROUTE LIKE 'P%'))  ");
		buf.append("\n        or (  (  a.ARCH_REG_DD <>'')  ");
		buf.append("\n        OR(A.MANUAL_YN='Y' AND INST.ARCH_STE_YN='Y') ");
		buf.append("\n        or((A.MANUAL_YN='Y' AND con.MEDIA_ID like 'D%')) ");
		buf.append("\n         or((A.ARCH_ROUTE LIKE 'D%' AND A.MANUAL_YN='N')))   ");
		buf.append("\n       ) ");


		/**
		 * 영상 삭제 또는 폐기시에 금일 기준으로 삭제가 되지 않은 마스터에 관련영상 정보와 조인하여 해당 마스터의 차일드 마스터를
		 * 리스트 상에 제외 시켜준다(2011.05.20 by Dekim) 삭제스케줄러가 클립단위로 삭제되기에 해당 마스터건에 대해서 판단하기가 곤란함. 
		 */
		buf.append("\n 		AND (A.master_ID NOT IN ( ");
		buf.append("\n            SELECT CHILD_MASTER_ID FROM RELATION_MASTER RM ");
	 
		buf.append("\n      ) ) ");


		if(!conditionDO.getCtgr_l_cd().equals("")){
			// 100: 소재  200: 프로그램  
			if(conditionDO.getCtgr_l_cd().equals("200")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
			}else if(!conditionDO.getCtgr_l_cd().equals("")){
				// 100: 소재  200: 프로그램  
				buf.append("\n  AND A.CTGR_L_CD <> '200' ");
			}
			//buf.append("\n  AND A.CTGR_L_CD = '"+conditionDO.getCtgr_l_cd()+"' ");
		}

		if(DASBusinessConstants.SearchCombo.ASP_RTO_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  CTT.ASP_RTO_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (CTT.ASP_RTO_CD <> '' OR CTT.ASP_RTO_CD IS NULL  )");	
			}
		}
		if(DASBusinessConstants.SearchCombo.VD_QLTY.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n 	AND CTT.VD_QLTY = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n 	AND (CTT.VD_QLTY <> '' OR CTT.VD_QLTY IS NULL) ");	
			}
		}
		if(DASBusinessConstants.SearchCombo.USE_LIMIT.equals(conditionDO.getSearchCombo())){
			buf.append("\n  AND  A.rist_clf_Cd = '"+conditionDO.getSearchComboValue()+"' ");
		}
		if(DASBusinessConstants.SearchCombo.VIEW_GR_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.VIEW_GR_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.VIEW_GR_CD <> '' OR A.VIEW_GR_CD IS NULL or A.VIEW_GR_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CPRT_TYPE.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.CPRT_TYPE = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND ( A.CPRT_TYPE <> '' OR A.CPRT_TYPE IS NULL or A.CPRT_TYPE = '')");	
			}
		}
		if(DASBusinessConstants.SearchCombo.RSV_PRD_CD.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.RSV_PRD_CD = '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (A.RSV_PRD_CD <> '' OR A.RSV_PRD_CD IS NULL   or A.RSV_PRD_CD = '' ) ");
			}
		}
		if(DASBusinessConstants.SearchCombo.CT_CLA.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  CON.CT_CLA= '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  (CON.CT_CLA <> '' OR CON.CT_CLA IS NULL  or CON.CT_CLA = '') ");
			}
		}


		if(DASBusinessConstants.SearchCombo.TAPE_KIND.equals(conditionDO.getSearchCombo())){
			if(!conditionDO.getSearchComboValue().equals("")){
				buf.append("\n  AND  A.TAPE_MEDIA_CLF_CD= '"+conditionDO.getSearchComboValue()+"' ");
			}else{
				buf.append("\n  AND  ( A.TAPE_MEDIA_CLF_CD <> '' OR  A.TAPE_MEDIA_CLF_CD IS NULL  or  A.TAPE_MEDIA_CLF_CD = '') ");
			}
		}


		String[] dateGb = conditionDO.getDateKind().split(",");
		for(int i=0; i < dateGb.length; i++){
			//		등록일 검색
			buf.append("\n  AND ");

			if(CodeConstants.WorkStatusDateFlag.REG_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n  ( SUBSTR(A.ING_REG_DD,1,8) >= '"+conditionDO.getFromDate()+"'   ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	AND   SUBSTR(A.ING_REG_DD,1,8) <= '"+conditionDO.getToDate()+"' )");
				}
			}

			if(CodeConstants.WorkStatusDateFlag.SHOT_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	(( A.fm_dt >= '"+conditionDO.getFromDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.fm_dt <= '"+conditionDO.getToDate()+"' ) ");
				}

				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	or ( A.brd_dd >= '"+conditionDO.getFromDate()+"' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.brd_dd <= '"+conditionDO.getToDate()+"' ) )");
				}
			}	
			if(CodeConstants.WorkStatusDateFlag.COMPLET_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n ( A.arrg_end_dt >= '"+conditionDO.getFromDate()+"000000' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.arrg_end_dt <= '"+conditionDO.getToDate()+"235959') ");
				}
			}
			if(CodeConstants.WorkStatusDateFlag.ACCEPT_DATE.equals(String.valueOf(dateGb[i])))
			{
				if(!StringUtils.isEmpty(conditionDO.getFromDate()))
				{
					buf.append("\n 	(A.accept_end_dd >= '"+conditionDO.getFromDate()+"000000' ");
				}
				if(!StringUtils.isEmpty(conditionDO.getToDate()))
				{
					buf.append("\n 	and A.accept_end_dd <= '"+conditionDO.getToDate()+"235959') ");
				}
			}				


		}
		//	String[] McuidYn =  conditionDO.getMcuidYn().split(",");
		//	
		//	if(!conditionDO.getMcuidYn().equals("")){
		//		buf.append("\n 	 AND ( ");
		//		for(int i =0; i<McuidYn.length;i++){
		//		if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.SDI))
		//		{
		//			// mcuidYn=N 매체변환
		//		//	buf.append("\n 	 (A.mcuid is null or A.mcuid = '') ");
		//			buf.append("\n 	 A.arch_route LIKE 'D%' ");
		//		}
		//		else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.ONAIR))
		//		{   // mcuidYn=Y 온에어(주조)
		//			if(i!=0){
		//				buf.append("\n 	 or  ");
		//			}
		//			buf.append("\n 	 ( ");
		//			buf.append("\n  A.arch_route LIKE 'O%' ");
		//			buf.append("\n 	 ) ");
		//			
		//		}else if(McuidYn[i].equals(DASBusinessConstants.SourceGubun.PDS)){
		//			//PDS
		//			if(i!=0){
		//				buf.append("\n 	 or  ");
		//			}
		//			buf.append("\n 	( A.arch_route ='P' )");
		//	
		//		}
		//		}
		//		buf.append("\n 	 ) ");
		//	}


		boolean isDateCondition = false;
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeBfYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeIngYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArrangeCompYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getCompletYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getReOrdersYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getSecondArchiveYn())
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()) 
				|| DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))			

		{
			isDateCondition = true;
		}


		if(isDateCondition)
		{
			buf.append("\n 	AND ( ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");

			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ARRANGE_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.EDIT_ING+"' ");
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.PRE_EDIT+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"'  ");
			}
			else
			{
				buf.append("\n A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ORDERS+"' ");
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
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.RE_ARCHIVE+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getStartingYn()))
		{
			if(firstKind)
			{
				buf.append("\n or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
			}
			else
			{
				buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.STARTINGYN+"' ");
				firstKind = true;
			}
		}
		if(DASBusinessConstants.YesNo.YES.equals(conditionDO.getErrorYn()))
		{
			if(firstKind)
			{
				// 오류를 uncheck하고 정리전으로만 검색해도 오류인 정리전 자료도 모두 검색된다(상태가 오류로 표시되더라도) (2008.06.19. 김건학실장)
				//buf.append(" or A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n or A.DATA_STAT_CD = '009' ");
			}
			else
			{
				//buf.append(" A.DATA_STAT_CD = '"+CodeConstants.DataStatusCode.ERROR+"' ");
				buf.append("\n A.DATA_STAT_CD  = '009' ");
				firstKind = true;
			}
		}

		if(isDateCondition)
		{
			buf.append(")  ");
		}

		// 검색어를 입력한 경우
		if ( (conditionDO.getColumnName().trim().length() > 0)
				&& (conditionDO.getSearchKey().trim().length() > 0) )
		{
			if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_NM") == true)			
				buf.append("\n and a.SEC_ARCH_NM like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("SEC_ARCH_ID") == true)			
				buf.append("\n and a.SEC_ARCH_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("REQ_CD") == true)			
				buf.append("\n and a.REQ_CD like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("TITLE") == true){			

				buf.append("\n and ((A.TITLE like '%" + conditionDO.getSearchKey() + "%'  )");
				buf.append("\n or (pgm.pgm_nm like '%" + conditionDO.getSearchKey() + "%'   ))");


			}


			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_ID") == true)			
				buf.append("\n and a.ACCEPTOR_ID like '%" + conditionDO.getSearchKey() + "%'");

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("ACCEPTOR_NM") == true)
			{

				buf.append("\n and USER3.USER_NM like '%" + conditionDO.getSearchKey() + "%'");
			}

			else if (conditionDO.getColumnName().trim().equalsIgnoreCase("MASTER_ID") == true)			
				buf.append("\n and A.MASTER_ID = " + conditionDO.getSearchKey() + " ");
		}

		if(!conditionDO.getArchiveYn().equals("")){
			if(conditionDO.getArchiveYn().equals("Y")){
				buf.append(" AND value(A.ARCH_REG_DD,'') <>'' ");	
			}else{
				buf.append(" AND value(A.ARCH_REG_DD,'') ='' ");	
			}
		}

		//2012.4.18 채널, 회사 조건  추가
		if(!conditionDO.getCocd().equals("")){

			buf.append(" AND A.cocd= '" +conditionDO.getCocd()+"'");	

		}

		if(!conditionDO.getChennel().equals("")){
			buf.append(" AND A.chennel_cd= '" +conditionDO.getChennel()+"'");	
		}

		if(conditionDO.getSortColume().length()>0){
			buf.append("\n order by "+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
		}else{
			buf.append("\n order by a.ing_reg_dd desc ");
		}
		if (conditionDO.getQueryResultCount() == false)
		{
			// rownum 으로 갯수를 제한하는 경우라면 
			if ( (conditionDO.getStartPos() > 0) && (conditionDO.getEndPos() > 0) )
			{
				buf.append(" \n ) as k ) as t ");
				buf.append("\n where rownum between " );
				buf.append(conditionDO.getStartPos() );
				buf.append(" and " );
				buf.append(conditionDO.getEndPos() );
			}
			if(conditionDO.getSortColume().length()>0){
				buf.append("\n ORDER BY "+conditionDO.getSortColume()+" "+conditionDO.getSortValue());
			}else{
				buf.append("\n ORDER BY ing_reg_dd desc  ");
			}
		}
		else	// 갯수만 가져오는 경우라면 
			buf.append(" \n ) as k  ) as t ");

		buf.append("\n with ur");

		return buf.toString();		

	}


	/**
	 * 마스터id에 따른 메타저보를 조회한다.
	 */
	public static final String selectMetadatQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	CART.CART_NO,  ");
		buf.append("\n 	CART_SEQ,  ");
		buf.append("\n 	RIST_CLF_CD,  ");
		buf.append("\n 	CT_ID,  ");
		buf.append("\n 	CTI_ID,  ");
		buf.append("\n 	SOM,  ");
		buf.append("\n 	EOM,  ");
		buf.append("\n 	DURATION,  ");
		buf.append("\n 	CART.REG_DT,  ");
		buf.append("\n 	CART.REGRID,  ");
		buf.append("\n 	CART.MOD_DT,  ");
		buf.append("\n 	CART.MODRID,  ");
		buf.append("\n 	CART.CTGR_L_CD,  ");
		buf.append("\n 	CART.CTGR_M_CD,  ");
		buf.append("\n 	CART.CTGR_S_CD,  ");
		buf.append("\n 	CART.CT_CONT,  ");
		buf.append("\n 	CART.CT_NM,  ");
		buf.append("\n 	CART.MASTER_ID ");
		//buf.append("\n 	CART.RIST_CLF_CD ");
		buf.append("\n from DAS.CART_CONT_TBL CART, DAS.DOWN_CART_TBL DOWN ");		
		buf.append("\n where CART.CART_NO = ? ");
		buf.append("\n and CART.CART_NO = DOWN.CART_NO ");
		buf.append("\n and DOWN.CART_STAT <> '003'");
		buf.append("\n and DOWN.CART_STAT <> '004'");
		buf.append("\n and DOWN.CART_STAT <> '005'");
		buf.append("\n and DOWN.CART_STAT <> '006'");
		buf.append("\n and DOWN.CART_STAT <> '007'");
		buf.append("\n order by CART_SEQ ");
		buf.append("\n WITH UR ");

		return buf.toString();
	}

	/**
	 * 다운로드 승인 조회한다.(등록시)
	 */
	public static final String selectApproveInfoList(ApproveInfoDO ApproveInfoDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	app.pgm_id, ");
		buf.append("\n 	app.pgm_nm, ");
		buf.append("\n 	app.app_gubun, ");
		buf.append("\n app.dept_cd, ");
		buf.append("\n 	app.approve_user_num, ");
		buf.append("\n	app.approve_user_nm, ");
		buf.append("\n	CODE.DESC AS position, ");
		buf.append("\n	dep.DEPT_NM ");
		buf.append("\n 	from APPROVE_INFO_TBL app ");
		buf.append("\n left outer join dep_info_tbl dep on dep.DEPT_CD=app.DEPT_CD  ");
		buf.append("\n  LEFT OUTER JOIN CODE_TBL CODE ON  CODE.SCL_CD= APP.POSITION AND CODE.CLF_CD='P060'  ");

		buf.append("\n where 1=1 ");


		/*if(!ApproveInfoDO.getPgm_nm().equals("")){
		buf.append("\n and app.pgm_nm like '%"+ApproveInfoDO.getPgm_nm()+"%' ");
	}
		 */
		if(!ApproveInfoDO.getPgm_cd().equals("")){
			buf.append("\n and app.pgm_id = '"+ApproveInfoDO.getPgm_cd()+"' ");
		}

		buf.append("\n  order by  dept_Cd desc , position desc, dep.dept_nm asc   ");
		return buf.toString();	
	}


	/**
	 * 다운로드 승인 조회한다
	 * @param ApprveDO        
	 */
	public static final String selectApproveInfo(ApproveInfoDO ApproveInfoDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select distinct ");
		buf.append("\n PROGRAM_CODE ");
		buf.append("\n ,PROGRAM_NAME ");
		buf.append("\n ,APP_GUBUN ");
		//20130102 최효정과정 오류수정 
		//새로 입력한 사람들은 '광고전문위원'으로 입력
		//buf.append("\n ,CP_YN ");      
		buf.append("\n ,value(CP_YN,'')  AS CP_YN ");
		buf.append("\n ,USER_NM  ");
		buf.append("\n ,USER_NUM ");
		buf.append("\n ,DEPT_NM ");
		buf.append("\n ,DEPT_CD ");
		buf.append("\n	from ( ");
		buf.append("\n select * FROM ( ");
		buf.append("\n select ");
		buf.append("\n PDS.PROGRAM_CODE ");
		buf.append("\n 	,PDS.PROGRAM_NAME ");
		buf.append("\n 	,APP.APP_GUBUN ");		
		buf.append("\n	, CODE.DESC AS CP_YN ");
		buf.append("\n	, USER.USER_NM ");
		buf.append("\n	, USER.USER_NUM ");
		//buf.append("\n	, USER.POSITION ");
		buf.append("\n	, DEP.DEPT_NM ");
		buf.append("\n	, APP.DEPT_CD ");
		buf.append("\n  FROM (select PROGRAM_CODE, PROGRAM_NAME ,PRODUCER_ID from  PDS_PGMINFO_TBL2  group by  PROGRAM_CODE, PROGRAM_NAME,PRODUCER_ID )PDS  ");
		buf.append("\n inner JOIN APPROVE_INFO_TBL APP ON APP.PGM_ID = PDS.PROGRAM_CODE ");
		buf.append("\n  inner JOIN USER_INFO_TBL USER ON  USER.user_num = APP.APPROVE_USER_NUM     ");
		//buf.append("\n   LEFT OUTER JOIN CODE_TBL CODE ON  CODE.SCL_CD= APP.POSITION AND CODE.CLF_CD='P060'");
		buf.append("\n   LEFT OUTER JOIN CODE_TBL CODE ON  CODE.SCL_CD= user.POSITION AND CODE.CLF_CD='P060'");

		buf.append("\n inner JOIN DEP_INFO_TBL DEP ON DEP.DEPT_CD = APP.DEPT_CD ");
		buf.append("\n where 1=1 ");
		buf.append("\n  and  app.APPROVE_USER_NUM <> ''  ");
		if(!ApproveInfoDO.getPgm_nm().equals("")){
			buf.append("\n and PDS.PROGRAM_NAME like '%"+ApproveInfoDO.getPgm_nm()+"%' ");
		}
		if(!ApproveInfoDO.getUser_nm().equals("")){
			buf.append("\n and USER.USER_NM like '%"+ApproveInfoDO.getUser_nm()+"%' ");
		}
		if(!ApproveInfoDO.getPgm_id().equals("")){
			buf.append("\n and PDS.PROGRAM_CODE like '%"+ApproveInfoDO.getPgm_id()+"%' ");
		}
		buf.append("\n  )   ");

		buf.append("\n )union all( ");

		buf.append("\n select ");
		buf.append("\n PDS.PROGRAM_CODE ");
		buf.append("\n 	,PDS.PROGRAM_NAME ");
		buf.append("\n 	,APP.APP_GUBUN ");

		buf.append("\n	 ,'' AS CP_YN ");
		buf.append("\n	, '' as user_nm ");
		buf.append("\n	, '' as USER_NUM  ");
		//buf.append("\n	, USER.POSITION ");
		buf.append("\n	, DEP.DEPT_NM ");
		buf.append("\n	,APP.DEPT_CD  ");
		buf.append("\n  FROM (select PROGRAM_CODE, PROGRAM_NAME from  PDS_PGMINFO_TBL2  group by  PROGRAM_CODE, PROGRAM_NAME )PDS  ");
		buf.append("\n inner JOIN APPROVE_INFO_TBL APP ON APP.PGM_ID = PDS.PROGRAM_CODE ");

		buf.append("\n inner JOIN DEP_INFO_TBL DEP ON DEP.DEPT_CD = app.DEPT_CD  ");
		buf.append("\n where 1=1 ");

		if(!ApproveInfoDO.getUser_nm().equals("")){
			buf.append("\n  and  app.APPROVE_USER_NUM='' and  app.approve_user_nm != ''  ");
		}else{
			buf.append("\n  and  app.APPROVE_USER_NUM='' ");
		}

		if(!ApproveInfoDO.getPgm_nm().equals("")){
			buf.append("\n and PDS.PROGRAM_NAME like '%"+ApproveInfoDO.getPgm_nm()+"%' ");
		}

		if(!ApproveInfoDO.getPgm_id().equals("")){
			buf.append("\n and PDS.PROGRAM_CODE like '%"+ApproveInfoDO.getPgm_id()+"%' ");
		}


		buf.append("\n  order by PROGRAM_CODE  asc ,program_name desc , dept_Cd desc, position desc    ");
		buf.append("\n ) order by PROGRAM_CODE  asc ,program_name desc , dept_Cd desc, CP_YN desc  ");


		return buf.toString();	
	}



	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 */
	public static final String selectProgramInfoQuery2(String pgmNm)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.PGM_ID, ");
		buf.append("\n 	meta.MASTER_ID, ");
		buf.append("\n 	pgm.PGM_NM, ");
		//buf.append("\n 	pgm.PGM_EPIS   ");		// 예전엔 PGM에도 EPIS가 있었나보지.
		buf.append("\n 	meta.EPIS_NO ");
		buf.append("\n from DAS.PGM_INFO_TBL pgm, DAS.METADAT_MST_TBL meta  ");
		buf.append("\n where PGM_NM like '%"+pgmNm+"%'");
		buf.append("\n and pgm.PGM_ID = meta.PGM_ID ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}





	/**
	 * 재생성시 필요한 정보 를 생성한다.(cart_cont_tbl)
	 * @param ct_id 영상id
	 */
	public static final String selectInfoForRecreate(long ct_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	inst.cti_id, inst.ct_id, map.master_id,  '"+dasHandler.getProperty("MP2")+"/'||left(INST.reg_dt,6)||'/'||substr(INST.reg_dt,7,2) as fl_path   ");
		buf.append("\n  ,con.ASP_RTO_CD, con.VD_QLTY ");
		buf.append("\n  from contents_inst_tbl inst ");
		buf.append("\n 	inner join contents_mapp_Tbl map on map.CT_ID=inst.CT_ID ");
		buf.append("\n 		 	inner join contents_Tbl con on con.CT_ID=inst.CT_ID ");

		buf.append("\n where inst.CTI_FMT like '%10%' ");
		buf.append("\n and inst.CT_ID =" +ct_id);
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	/**
	 * 재생성시 필요한 정보 를 생성한다.(down_cart_tbl)
	 * @param ct_id 영상id
	 * @return
	 * @throws DASException
	 */
	public static final String selectInfoForRecreate2(long ct_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	ASP_RTO_CD, VD_QLTY  ");
		buf.append("\n   from contents_tbl ");

		buf.append("\n where  ");
		buf.append("\n CT_ID =" +ct_id);
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * tc req_cd의 값을 구한다
	 * @param ct_id 컨텐츠id
	 */
	public static final String selectTcInfo(long ct_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select cart.TC_REQ_CD, cont.ct_id, DOWN.CART_NO , down.filename from CONTENTS_DOWN_TBL down ");
		buf.append("\n inner join down_cart_tbl cart on cart.CART_NO = down.CART_NO and cart.TC_REQ_CD <>''   ");
		buf.append("\n inner join cart_cont_tbl cont on cont.CART_NO = down.CART_NO    ");
		buf.append("\n where cont.ct_id= " + ct_id+  "");
		buf.append("\n order by down.REG_DTM desc ");
		buf.append("\n fetch first 1 row only ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}



	/**
	 * tc req_cd의 값을 구한다
	 * @param int num
	 */
	public static final String selectTcInfo(int num)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select cart.TC_REQ_CD, cont.ct_id, DOWN.CART_NO , down.filename,CONT.regrid from CONTENTS_DOWN_TBL down ");
		buf.append("\n inner join down_cart_tbl cart on cart.CART_NO = down.CART_NO and cart.TC_REQ_CD <>''   ");
		buf.append("\n inner join cart_cont_tbl cont on cont.CART_NO = down.CART_NO    ");
		buf.append("\n where down.num= " + num+  "");
		buf.append("\n order by down.REG_DTM desc ");
		buf.append("\n fetch first 1 row only ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}




	/**
	 * 화질, 화면비를 구한다
	 * @param int num
	 * @return CartContDO
	 * @throws DASException
	 */
	public static final String selectAspVdInfo(long ct_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select con.asp_rto_cd, con.vd_qlty ,CODE.DESC AS asp_rto_nm, code2.desc as vd_qlty_nm from CONTENTS_TBL con  ");
		buf.append("\n left outer join code_tbl code on code.scl_Cd = con.asp_rto_cd  AND code.CLF_cD ='A006' ");
		buf.append("\n left outer join code_tbl code2 on code2.scl_Cd = con.vd_qlty  and code2.clf_Cd='A005' ");

		buf.append("\n where ct_id= "+ ct_id+  "");

		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	/**
	 * 재요청할 DOWN_CART의 정보를 얻어온다.
	 * @param  CART_NO 카트번호 
	 * @return
	 * @throws DASException
	 */
	public static final String selectDownCartInfo(long CART_NO)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select  ");
		//buf.append("\n CART_NO  ");
		buf.append("\n DATA_CLF_CD  ");
		buf.append("\n ,PRIO_CD  ");
		buf.append("\n ,STRG_LOC  ");
		buf.append("\n ,RIST_YN  ");
		buf.append("\n ,APP_CONT  ");
		buf.append("\n ,REQ_USRID  ");
		buf.append("\n ,REQ_NM  ");
		//	buf.append("\n ,REQ_DT  ");
		//	buf.append("\n ,DOWN_DT  ");
		//	buf.append("\n ,APP_DT  ");
		buf.append("\n ,DOWN_SUBJ  ");
		buf.append("\n ,GAURANTOR_ID  ");
		//	buf.append("\n ,REG_DT  ");
		buf.append("\n ,REGRID  ");
		//	buf.append("\n ,MOD_DT  ");
		buf.append("\n ,MODRID  ");
		buf.append("\n ,DEPT_CD  ");
		//	buf.append("\n ,VD_QLTY  ");
		//	buf.append("\n ,ASP_RTO_CD  ");
		//	buf.append("\n ,CART_STAT  ");
		//	buf.append("\n ,DOWN_YN  ");
		buf.append("\n ,CO_CD  ");
		buf.append("\n ,SEG_CD  ");
		buf.append("\n ,DOWN_GUBUN  ");
		buf.append("\n ,OUT_STRG_LOC  ");
		//	buf.append("\n ,OUT_USER_YN  ");
		//	buf.append("\n ,DEL_YN  ");
		buf.append("\n ,FILE_PATH  ");
		buf.append("\n ,CATEGORY  ");
		buf.append("\n ,STORAGENAME  ");
		//	buf.append("\n ,TC_REQ_CD  ");
		buf.append("\n FROM  DOWN_CART_TBL  ");
		buf.append("\n where CART_NO= "+ CART_NO+  "");

		buf.append("\n WITH UR	 ");

		return buf.toString();
	}
	/**
	 * 영상의 위치를 설정한다(다운로드, 스토리지 다운로드)
	 * @param cart_no 카트넘버
	 * @param cart_seq 카트순번 
	 */
	public static final String selectCartContInfo(long CART_NO,long cart_seq)
	{

		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n CART_NO  ");
		buf.append("\n ,CART_SEQ  ");
		buf.append("\n ,RIST_CLF_CD  ");
		buf.append("\n ,CT_ID  ");
		buf.append("\n ,CTI_ID  ");
		buf.append("\n ,SOM  ");
		buf.append("\n ,EOM  ");
		buf.append("\n ,DURATION  ");
		//	buf.append("\n ,REG_DT  ");
		buf.append("\n ,REGRID  ");
		///	buf.append("\n ,MOD_DT  ");
		buf.append("\n ,MODRID  ");
		buf.append("\n ,CTGR_L_CD  ");
		buf.append("\n ,CTGR_M_CD  ");
		buf.append("\n ,CTGR_S_CD  ");
		buf.append("\n ,CT_CONT  ");
		buf.append("\n ,CT_NM  ");
		buf.append("\n ,MASTER_ID  ");
		//buf.append("\n ,DOWN_YN  ");
		//	buf.append("\n ,DOWN_DT  ");
		buf.append("\n ,S_FRAME  ");
		buf.append("\n ,APP_CONT  ");
		buf.append("\n ,DOWN_VOL  ");
		//	buf.append("\n ,ERROR_STATE  ");
		buf.append("\n ,VD_QLTY  ");
		buf.append("\n ,ASP_RTO_CD  ");
		buf.append("\n ,DOWN_STAT  ");
		buf.append("\n ,OUTSOURCING_YN  ");
		buf.append("\n ,OUTSOURCING_APPROVE  ");
		buf.append("\n ,DOWN_TYP  ");
		buf.append("\n ,REQ_CONT  ");
		//	buf.append("\n ,DEL_YN  ");
		buf.append("\n ,MEDIA_ID  ");
		//		buf.append("\n ,DOWN_GUBUN  ");
		//buf.append("\n ,RIST_YN  ");

		buf.append("\n FROM  CART_CONT_TBL  ");
		buf.append("\n where CART_NO= "+ CART_NO+  "");
		buf.append("\n and cart_seq= "+ cart_seq+  "");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}
	/**
	 * 영상의 위치를 설정한다(다운로드, 스토리지 다운로드)
	 * @param cart_no 카트넘버
	 */
	public static final String selectCartContInfo(long CART_NO)
	{

		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n CART_NO  ");
		buf.append("\n ,CART_SEQ  ");
		buf.append("\n ,RIST_CLF_CD  ");
		buf.append("\n ,CT_ID  ");
		buf.append("\n ,CTI_ID  ");
		buf.append("\n ,SOM  ");
		buf.append("\n ,EOM  ");
		buf.append("\n ,DURATION  ");
		//	buf.append("\n ,REG_DT  ");
		buf.append("\n ,REGRID  ");
		///	buf.append("\n ,MOD_DT  ");
		buf.append("\n ,MODRID  ");
		buf.append("\n ,CTGR_L_CD  ");
		buf.append("\n ,CTGR_M_CD  ");
		buf.append("\n ,CTGR_S_CD  ");
		buf.append("\n ,CT_CONT  ");
		buf.append("\n ,CT_NM  ");
		buf.append("\n ,MASTER_ID  ");
		//buf.append("\n ,DOWN_YN  ");
		//	buf.append("\n ,DOWN_DT  ");
		buf.append("\n ,S_FRAME  ");
		buf.append("\n ,APP_CONT  ");
		buf.append("\n ,DOWN_VOL  ");
		//	buf.append("\n ,ERROR_STATE  ");
		buf.append("\n ,VD_QLTY  ");
		buf.append("\n ,ASP_RTO_CD  ");
		buf.append("\n ,DOWN_STAT  ");
		buf.append("\n ,OUTSOURCING_YN  ");
		buf.append("\n ,OUTSOURCING_APPROVE  ");
		buf.append("\n ,DOWN_TYP  ");
		buf.append("\n ,REQ_CONT  ");
		//	buf.append("\n ,DEL_YN  ");
		buf.append("\n ,MEDIA_ID  ");
		//		buf.append("\n ,DOWN_GUBUN  ");
		//buf.append("\n ,RIST_YN  ");

		buf.append("\n FROM  CART_CONT_TBL  ");
		buf.append("\n where CART_NO= "+ CART_NO+  "");
		buf.append("\n WITH UR	 ");
		return buf.toString();
	}

	//박보아 대리님이 수상내역과 부제 삭제 하라고 지시. 20110217
	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다
	 * @param programInfoDO   검색할 정보를 가지고있는 beans
	 */
	public static final String selectPgmInfoFromNameQuery3(ProgramInfoDO programInfoDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	pgm.PGM_ID,  ");
		//buf.append("\n 	mst.master_id, ");
		buf.append("\n 	pgm.PGM_CD, ");
		buf.append("\n 	pgm.BRD_BGN_DD, ");
		buf.append("\n 	pgm.PGM_NM,   ");	
		buf.append("\n 	pgm.BRD_END_DD,   ");	
		buf.append("\n 	pgm.CTGR_L_CD,   ");	
		buf.append("\n 	pgm.CTGR_M_CD,   ");	
		buf.append("\n 	pgm.CTGR_S_CD,   ");	
		buf.append("\n 	pgm.MEDIA_CD   ");	
		buf.append("\n 	, pgm.CHAN_CD  ");
		buf.append("\n 	, pgm.PRD_DEPT_NM      ");
		buf.append("\n 	,  pgm.SCHD_PGM_NM     ");
		buf.append("\n 	,  pgm.PARENTS_CD     ");
		buf.append("\n from DAS.PGM_INFO_TBL pgm ");
		//	buf.append("\n   inner join DAS.METADAT_MST_TBL mst on pgm.PGM_ID=mst.PGM_ID  ");
		buf.append("\n where  1=1");

		if(!programInfoDO.getPgmNm().equals("")){
			buf.append("\n and pgm.PGM_NM like ?");
		}

		if(!programInfoDO.getPgmCd().equals("")){
			buf.append("\n and pgm.PGM_cd like ?");
		}
		buf.append("\n order by pgm.pgm_nm asc 	 ");
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}










	/**
	 * 사진 메타정보를 수정한다.
	 * @param contentMappInfoDO  content mapp object
	 * @return Update count
	 * @throws DASException
	 */
	public static final String updatePhotInfoQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update das.PHOT_TBL set ");
		buf.append("\n 	cont = ?,  ");
		buf.append("\n 	MOD_DT = CURRENT_DATE,  ");
		buf.append("\n 	resolution = ?, ");
		buf.append("\n cprtr_nm = ?, ");
		buf.append("\n 	DOWN_YN = ? ");
		buf.append("\n 	where phot_reg_id =  ? ");
		return buf.toString();
	}



	/**
	 * 이용자별 다운로드 목록을 조회한다
	 * @param CartItemDO                                                                                                 
	 * @throws DASException
	 */
	public static final String selectCartInfoForUser(CartItemDO cartItemDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n value(cart.REGRID,'') as REGRID ");
		buf.append("\n ,value(cart.CART_NO,'') as CART_NO ");
		buf.append("\n ,value(cart.CART_SEQ,'') as CART_SEQ ");
		buf.append("\n ,value(CODE.DESC,'') AS RIST_CLF_CD ");
		buf.append("\n ,value(MST.TITLE,'') as TITLE  ");
		buf.append("\n ,value(CART.CT_ID,'') as CT_ID ");
		buf.append("\n ,value(USER.USER_NM,'') as USER_NM ");
		buf.append("\n ,value(CONT.MEDIA_ID,'') as MEDIA_ID ");
		buf.append("\n ,value(CODE2.DESC,'')  AS CT_CLA");
		buf.append("\n ,value(down.REG_DT,'') as REG_DT ");
		buf.append("\n ,value(CODE3.DESC ,'') AS DOWN_STAT ");
		buf.append("\n ,value(CART.SOM,'') as SOM ");
		buf.append("\n ,value(CART.EOM,'') as EOM ");
		buf.append("\n ,value(down.DOWN_SUBJ,'') as down_subj ");
		buf.append("\n  , replace(MST.epis_no, '0', '') as epis_no  ");
		buf.append("\n  ,value(MST.brd_dd,'') as brd_dd   ");
		buf.append("\n  ,value(MST.fm_dt,'') as fm_dt   ");
		buf.append("\n  ,value(MST.ctgr_l_cd,'') as ctgr_l_cd   ");
		buf.append("\n  ,CODE4.DESC AS CONM   ");
		buf.append("\n from CART_CONT_TBL cart ");		
		buf.append("\n inner join METADAT_MST_TBL mst on mst.MASTER_ID = cart.MASTER_ID  ");
		buf.append("\n INNER JOIN CONTENTS_TBL CONT ON CONT.CT_ID = cart.CT_ID ");
		buf.append("\n LEFT OUTER join CODE_TBL code on code.SCL_CD = cart.RIST_CLF_CD and code.CLF_CD='P018' AND code.GUBUN='L' ");
		buf.append("\n LEFT OUTER join CODE_TBL code2 on code2.SCL_CD = CONT.CT_CLA and code2.CLF_CD='A002' ");
		buf.append("\n LEFT OUTER join CODE_TBL code3 on code3.SCL_CD = cart.DOWN_STAT and code3.CLF_CD='P061'");
		buf.append("\n INNER JOIN USER_INFO_TBL USER ON USER.SBS_USER_ID = CART.REGRID ");
		buf.append("\n inner join down_cart_tbl down on down.CART_NO = cart.CART_NO   ");
		buf.append("\n left outer join code_tbl code4 on code4.CLF_CD ='P058' AND CODE4.SCL_CD = USER.COCD   ");
		buf.append("\n WHERE 1=1 ");
		buf.append("\n and down.DOWN_GUBUN<>'006' ");
		buf.append("\n AND cart.DOWN_STAT != '001' ");

		if(!cartItemDO.getReq_id().equals("")){
			buf.append("\n and cart.REGRID like '%"+cartItemDO.getReq_id()+"%'");	
		}
		if(!cartItemDO.getReqNm().equals("")){
			buf.append("\n and user.user_nm like '%"+cartItemDO.getReqNm()+"%'");	
		} 
		if(!cartItemDO.getMedia_id().equals("")){
			buf.append("\n and CONT.MEDIA_ID like '%"+cartItemDO.getMedia_id()+"%'");	
		}
		if(!cartItemDO.getCocd().equals("")){
			buf.append("\n and user.cocd = '"+cartItemDO.getCocd()+"'");	
		}
		if(cartItemDO.getCt_id()!=0){
			buf.append("\n and cart.ct_id = "+cartItemDO.getCt_id()+"");	
		}
		if(!cartItemDO.getFromdate().equals("")||!cartItemDO.getEnddate().equals("")){
			buf.append("\n and down.reg_dt >= '"+cartItemDO.getFromdate()+"00000'");	
			buf.append("\n and down.reg_dt <= '"+cartItemDO.getEnddate()+"99999'");	
		}

		buf.append("\n order by down.REG_DT desc");	

		return buf.toString();	
	}


	/**
	 * MasterId_id의 eom를 조회한다
	 */
	public static final String selectEomByMasterId()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	brd_leng ");
		buf.append("\n 	from metadat_mst_tbl  ");
		buf.append("\n 	where master_id = ? with ur ");		 

		return buf.toString();
	}




	/**
	 * 다운로드 승인 조회한다.(등록시)
	 * @param ApprveDO       
	 */
	public static final String getManualArchiveInfo(String media_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select  ");
		buf.append("\n 	mst.pds_cms_pgm_id ");
		buf.append("\n 	,mst.ctgr_l_cd ");
		buf.append("\n ,mst.title");
		buf.append("\n ,mst.epis_no ");
		buf.append("\n ,mst.sub_ttl ");
		buf.append("\n ,mst.RIST_CLF_CD");
		buf.append("\n ,mst.CMR_PLACE ");
		buf.append("\n ,mst.FM_DT ");
		buf.append("\n ,mst.PRODUCER_NM ");
		buf.append("\n ,cont.CT_CLA");
		buf.append("\n ,cont.ASP_RTO_CD ");
		buf.append("\n ,inst.VD_HRESOL ");
		buf.append("\n ,inst.VD_VRESOL");
		buf.append("\n ,mst.arch_route");
		buf.append("\n ,cont.media_id");
		buf.append("\n ,inst.aud_type_cd");
		buf.append("\n ,mst.cocd ");
		buf.append("\n ,mst.chennel_cd ");
		buf.append("\n from METADAT_MST_TBL mst ");
		buf.append("\n inner join contents_mapp_tbl map on map.MASTER_ID = mst.MASTER_ID ");
		buf.append("\n inner join contents_tbl cont on cont.CT_ID = map.CT_ID ");
		buf.append("\n inner join contents_inst_tbl inst on inst.ct_id = cont.CT_ID  ");
		buf.append("\n where cont.MEDIA_ID = '"+media_id+"'  and inst.CTI_FMT like '1%' ");


		buf.append("\n  with ur  ");
		return buf.toString();	
	}



	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * @param manualArchiveD  
	 */
	public static final String selectManualInfo(ManualArchiveDO manualArchiveD) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n value(FL_PATH,'') as FL_PATH ");
		buf.append("\n ,value(ORG_MEDIA_ID,'') as ORG_MEDIA_ID");
		buf.append("\n ,value(NEW_MEDIA_ID,'') as NEW_MEDIA_ID");
		buf.append("\n ,value(PDS_CMS_ID,'') as PDS_CMS_ID");
		buf.append("\n ,value(CTGR_L_CD,'') as CTGR_L_CD");
		buf.append("\n ,value(TITLE,'') as TITLE");
		buf.append("\n ,value(SUB_TTL,'') as SUB_TTL");
		buf.append("\n ,value(EPIS_NO,'') as EPIS_NO");
		buf.append("\n ,value(CT_CLA,'')  as CT_CLA");
		buf.append("\n ,value(RIST_CLF_CD,'') as RIST_CLF_CD");
		buf.append("\n ,value(FM_DT,'') as FM_DT");
		buf.append("\n ,value(CMR_PLACE,'') as CMR_PLACE");
		buf.append("\n ,value(PRODUCER_NM,'')  as PRODUCER_NM");
		buf.append("\n ,value(VD_VERSOL,'') as VD_VERSOL");
		buf.append("\n ,value(VD_HERSOL,'') as VD_HERSOL");
		buf.append("\n ,value(ASP_RTO_CD,'') as ASP_RTO_CD");
		buf.append("\n ,value(AUDIO_TYPE,'')  as AUDIO_TYPE");
		buf.append("\n ,value(ARCH_ROUTE,'')  as ARCH_ROUTE");
		buf.append("\n ,value(RECORD_TYPE_CD,'')  as RECORD_TYPE_CD");
		//2012.4.26
		buf.append("\n ,value(COCD,'')  as COCD");
		buf.append("\n ,value(CHENNEL,'')  as CHENNEL");
		buf.append("\n ,value(DTL_GUBUN,'')  as DTL_GUBUN");
		buf.append("\n  from TEMP_MANUAL_TBL ");
		buf.append("\n where org_media_id like  '"+manualArchiveD.getOrg_media_id()+"%'");
		buf.append("\n and new_media_id like '"+manualArchiveD.getNew_media_id()+"%' ");



		return buf.toString();	
	}

	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * @param ApprveDO     
	 */
	public static final String selectManualInfo(String media_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n FL_PATH ");
		buf.append("\n ,ORG_MEDIA_ID ");
		buf.append("\n ,NEW_MEDIA_ID ");
		buf.append("\n ,value(PDS_CMS_ID,'') as  PDS_CMS_ID");
		buf.append("\n ,CTGR_L_CD  ");
		buf.append("\n ,TITLE ");
		buf.append("\n ,value(SUB_TTL ,'') as sub_ttl ");
		buf.append("\n ,value(EPIS_NO,'0') as EPIS_NO ");
		buf.append("\n ,CT_CLA  ");
		buf.append("\n ,value(RIST_CLF_CD,'000') as RIST_CLF_CD ");
		buf.append("\n ,FM_DT ");
		buf.append("\n ,CMR_PLACE ");
		buf.append("\n ,PRODUCER_NM  ");
		buf.append("\n ,VD_VERSOL ");
		buf.append("\n ,VD_HERSOL ");
		buf.append("\n ,ASP_RTO_CD ");
		buf.append("\n ,AUDIO_TYPE  ");
		buf.append("\n ,ARCH_ROUTE  ");
		//2012.4.26
		buf.append("\n ,COCD  ");
		buf.append("\n ,CHENNEL  ");
		buf.append("\n ,DTL_GUBUN  ");
		buf.append("\n  from TEMP_MANUAL_TBL ");
		buf.append("\n where new_media_id = '"+media_id+"'");



		return buf.toString();	
	}

	/**
	 * 코너의 내용을 입력한다
	 */
	public static final String insertCornerContQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.cn_detail( ");
		buf.append("\n 	seq,  ");
		buf.append("\n 	CN_ID,  ");
		buf.append("\n 	SOM,  ");
		buf.append("\n 	EOM,  ");
		buf.append("\n 	cont)  ");

		buf.append("\n values(?, ?, ?, ?, ?) ");		

		return buf.toString();
	}


	/**
	 * 삭제일을 구한다
	 * @param long ct_id
	 * @return String
	 * @throws DASException
	 */
	public static final String selectMapDeldd(long master_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ct_id,del_dd from contents_mapp_Tbl  ");

		buf.append("\n where master_id= "+ master_id+  "");

		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	/**
	 * 사진아이디를 기준으로 사진 정보를 구한다
	 * @param long phot_id
	 * @return String
	 * @throws DASException
	 */
	public static final String selectPhotIdForPgmId(long phot_id)
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select pgm_Id from pgm_phot_info_tbl  ");

		buf.append("\n where phot_id= "+ phot_id+  "");

		buf.append("\n WITH UR	 ");

		return buf.toString();
	}


	/**
	 * 다운로드 승인 조회한다
	 * @param ApprveDO        
	 */
	public static final String selectApproveInfoForChennel(ApproveInfoDO ApproveInfoDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select  ");
		buf.append("\n CODE.DESC ");
		buf.append("\n ,APP.USER_ID ");
		buf.append("\n ,USER.USER_NM ");
		buf.append("\n ,app.cocd");
		buf.append("\n from APPROVE_CHENNEL_TBL app ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P058' AND CODE.SCL_CD=APP.COCD  ");
		buf.append("\n LEFT OUTER JOIN USER_INFO_TBL USER ON USER.SBS_USER_ID = APP.USER_ID ");


		buf.append("\n WHERE 1=1 ");
		buf.append("\n AND APP.USE_YN='Y' ");

		if(!ApproveInfoDO.getAcct_code().equals("")){
			buf.append("\n AND user.acct_code like '" + ApproveInfoDO.getAcct_code() + "%'");
		}
		if(!ApproveInfoDO.getUser_nm().equals("")){
			buf.append("\n AND user.user_nm like '%" + ApproveInfoDO.getUser_nm() + "%'");
		}
		if(!ApproveInfoDO.getUser_id().equals("")){
			buf.append("\n AND user.sbs_user_id like '%" + ApproveInfoDO.getUser_id() + "%'");
		}

		if(!ApproveInfoDO.getCocd().equals("")){
			buf.append("\n AND app.cocd like '%" + ApproveInfoDO.getCocd() + "%'");
		}

		return buf.toString();	
	}

	/**
	 * 채널별  승인자 대상  조회한다.(등록시)
	 */
	public static final String selectApproveInfoListForChennel(ApproveInfoDO ApproveInfoDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	user.sbs_user_id, ");
		buf.append("\n 	USER.USER_NM, ");
		buf.append("\n 	USER.USER_NUM ");		
		buf.append("\n 	from USER_INFO_TBL USER ");		
		//buf.append("\n left outer join USER_INFO_TBL USER on USER.SBS_USER_ID=app.USER_ID  ");

		buf.append("\n where 1=1 ");
		//buf.append("\n AND APP.USE_YN='Y' ");
		buf.append("\n AND USER.approve_status ='2'");
		if(!ApproveInfoDO.getPgm_cd().equals("")){
			buf.append("\n and USER.COCD = '"+ApproveInfoDO.getCocd()+"' ");
		}
		if(!ApproveInfoDO.getAcct_code().equals("")){
			buf.append("\n and USER.acct_code = '"+ApproveInfoDO.getAcct_code()+"' ");
		}
		if(!ApproveInfoDO.getUser_id().equals("")){
			buf.append("\n and USER.sbs_user_id = '"+ApproveInfoDO.getUser_id()+"' ");
		}
		if(!ApproveInfoDO.getUser_nm().equals("")){
			buf.append("\n and USER.user_nm = '"+ApproveInfoDO.getUser_nm()+"' ");
		}
		return buf.toString();	
	}




	/**
	 * 미디어 메타데이터 조회를 한다.
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws DASException
	 */
	public static final String selectClipInfoList(MediaArchiveDO mediaArchiveDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	value(CHENNEL_CD,'') as  CHENNEL_CD ");
		buf.append("\n 	,value(CTGR_L_CD,'') as CTGR_L_CD ");
		buf.append("\n 	,value(TAPE_MEDIA_CLF_CD,'') as  TAPE_MEDIA_CLF_CD");		
		buf.append("\n 	,value(TAPE_LENG,'') as TAPE_LENG ");
		buf.append("\n 	,value(REQ_CD,'') as REQ_CD ");	
		buf.append("\n 	,value(SCEAN_NO,0) as SCEAN_NO ");
		buf.append("\n 	,value(TITLE,'') as TITLE ");	
		buf.append("\n 	,value(SUB_TTL,'') as SUB_TTL ");
		buf.append("\n 	,value(EPIS_NO,0) as EPIS_NO ");	
		buf.append("\n 	,value(BRD_DD,'') as BRD_DD ");
		buf.append("\n 	,value(CMR_PLACE,'') as CMR_PLACE ");	
		buf.append("\n 	,value(VD_QLTY,'') as  VD_QLTY ");
		buf.append("\n 	,value(VIEW_GR_CD,'') as  VIEW_GR_CD ");	
		buf.append("\n 	,value(CPRT_TYPE,'') as CPRT_TYPE  ");
		buf.append("\n 	,value(CPRT_TYPE_DSC,'') as  CPRT_TYPE_DSC ");	
		buf.append("\n 	,value(CPRT_NM,'') as CPRT_NM ");
		buf.append("\n 	,value(RECORD_TYPE_CD,'') as RECORD_TYPE_CD ");	
		buf.append("\n 	,value(RIST_CLF_RANGE,'') as RIST_CLF_RANGE ");
		buf.append("\n 	,value(RSV_PRD_CD,'') as  RSV_PRD_CD ");	
		buf.append("\n 	,value(PRDT_IN_OUTS_CD,'') as PRDT_IN_OUTS_CD ");
		buf.append("\n 	,value(ORG_PRDR_NM,'') as  ORG_PRDR_NM ");	
		buf.append("\n 	,value(CONT,'') as  CONT ");
		buf.append("\n 	,value(SPC_INFO,'') as SPC_INFO ");	
		buf.append("\n 	,value(ARTIST,'') as  ARTIST");
		buf.append("\n 	,value(CTGR_M_CD,'') as CTGR_M_CD ");	
		buf.append("\n 	,value(CTGR_S_CD,'') as  CTGR_S_CD ");
		buf.append("\n 	,value(COUNTRY_CD,'') as  COUNTRY_CD");	
		buf.append("\n 	,value(KEY_WORDS,'') as KEY_WORDS ");
		buf.append("\n 	,value(DRT_NM,'') as DRT_NM ");	
		buf.append("\n 	,value(CMR_DRT_NM,'') as CMR_DRT_NM  ");
		buf.append("\n 	,value(MC_NM,'') as MC_NM ");	
		buf.append("\n 	,value(WRITER_NM,'') as WRITER_NM ");
		buf.append("\n 	,value(CT_CLA,'') as CT_CLA ");
		buf.append("\n 	,SEQ ");
		buf.append("\n 	,value(gubun,'') as  gubun ");
		buf.append("\n 	,value(RIST_CLF_CD,'') as RIST_CLF_CD ");
		buf.append("\n 	from media_tapeinfo_tbl  ");		

		buf.append("\n where ");

		if(!mediaArchiveDO.getReq_cd().equals("")){
			buf.append("\n  REQ_CD like '"+mediaArchiveDO.getReq_cd()+"%' ");
		}

		buf.append("\n ORDER BY REQ_CD ASC , SCEAN_NO ASC  ");
		//buf.append("\n and (gubun is null or  gubun ='003' or  gubun ='004' )");
		return buf.toString();	
	}





	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * @param manualArchiveD  
	 */
	public static final String selectDtlInfo() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n DTL_NM ");
		buf.append("\n ,ALIAS ");
		buf.append("\n ,DTL_CONT ");

		buf.append("\n  from DTL_INFO_TBL ");



		return buf.toString();	
	}


	/**
	 * 사용자 로그인 현황을 조회한다(모니터링)
	 * @param  xml                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectLogInOutInfo(LogInOutDO logInOutDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * ");
		buf.append("\n from ( ");
		buf.append("\n select ");
		buf.append("\n log.user_id ");
		buf.append("\n ,user.user_nm ");
		buf.append("\n ,dep.dept_nm ");
		buf.append("\n ,log.ip ");
		buf.append("\n ,log.login_dt ");
		buf.append("\n ,log.status ");
		buf.append("\n ,log.logout_dt ");
		buf.append("\n ,value(user.mobile,'') as mobile ");
		buf.append("\n  ,ROW_NUMBER() OVER(ORDER BY log.seq  ) AS rownum ");

		buf.append("\n  from loginlog_Tbl log ");
		buf.append("\n  left outer join user_info_tbl user on user.sbs_user_id = log.user_id  ");
		buf.append("\n  left outer join dep_info_Tbl dep on dep.dept_cd= user.dept_cd  ");


		buf.append("\n where 1=1 ");

		if(!logInOutDO.getStatus().equals("")){
			buf.append("\n and log.status like '"+ logInOutDO.getStatus()+"%'");
		}

		if(!logInOutDO.getUser_id().equals("")){
			buf.append("\n and log.user_id ='"+ logInOutDO.getUser_id()+"'");
		}

		if(!logInOutDO.getUser_nm().equals("")){
			buf.append("\n and user.user_nm like '%"+ logInOutDO.getUser_nm()+"%'");
		}


		if(!logInOutDO.getStart_login_dt().equals("")){
			buf.append("\n and SUBSTR(log.login_dt,1,8) >='"+ logInOutDO.getStart_login_dt()+"'");
			buf.append("\n and SUBSTR(log.login_dt,1,8) <='"+ logInOutDO.getEnd_login_dt()+"'");
		}
		buf.append("\n ) a");


		buf.append("\n where a.rownum between " + logInOutDO.getStart_page());
		buf.append("\n  ");

		int end_page=logInOutDO.getStart_page()+99;
		buf.append("\n and " + end_page);

		buf.append("\n order by a.login_dt desc ");
		return buf.toString();	
	}


	/**
	 * 총갯수를 카운트한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String countLogInOut(LogInOutDO logInOutDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");		
		buf.append("\n from DAS.loginlog_Tbl log ");
		buf.append("\n  left outer join user_info_tbl user on user.sbs_user_id = log.user_id  ");
		buf.append("\n  left outer join dep_info_Tbl dep on dep.dept_cd= user.dept_cd  ");

		buf.append("\n where 1= 1 ");
		if(!logInOutDO.getStatus().equals("")){
			buf.append("\n and log.status like '"+ logInOutDO.getStatus()+"%'");
		}

		if(!logInOutDO.getUser_id().equals("")){
			buf.append("\n and log.user_id ='"+ logInOutDO.getUser_id()+"'");
		}

		if(!logInOutDO.getUser_nm().equals("")){
			buf.append("\n and user.user_nm like '%"+ logInOutDO.getUser_nm()+"%'");
		}


		if(!logInOutDO.getStart_login_dt().equals("")){
			buf.append("\n and SUBSTR(log.login_dt,1,8) >='"+ logInOutDO.getStart_login_dt()+"'");
			buf.append("\n and SUBSTR(log.login_dt,1,8) <='"+ logInOutDO.getEnd_login_dt()+"'");
		}
		buf.append("\n WITH UR	 ");

		return buf.toString();
	}




	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectArchiveInfo(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n  a.title  ");
		buf.append("\n ,a.NUM ");
		buf.append("\n ,a.CTI_ID ");

		buf.append("\n ,a.TC_STATE ");
		buf.append("\n ,a.TC_PROGRESS ");
		buf.append("\n ,a.LOC_STATE ");
		buf.append("\n ,a.PROGRESS ");
		buf.append("\n ,a.LOC_PRIORITY ");
		buf.append("\n ,a.COPY_PROGRESS ");
		buf.append("\n ,a.COPY_STATUS");
		buf.append("\n ,a.LOC_PRIORITY ");
		buf.append("\n ,a.LOC_PRIORITY ");
		buf.append("\n ,a.BACKUP_PROGRESS ");
		buf.append("\n ,a.BACKUP_STATUS ");
		buf.append("\n ,a.CHANGE_STATE ");
		buf.append("\n ,a.DOWN_PROGRESS ");
		buf.append("\n ,a.DOWN_STATE ");
		buf.append("\n ,a.change_progress ");

		buf.append("\n ,'아카이브' as gubun ");
		buf.append("\n ,a.ct_typ ");
		buf.append("\n ,a.req_dt ");
		buf.append("\n ,a.tc_ip ");
		buf.append("\n ,a.change_ip ");
		buf.append("\n ,a.REQ_ID  ");
		buf.append("\n ,a.cocd ");
		buf.append("\n ,a.TC_ID ");
		buf.append("\n ,a.change_nm ");
		buf.append("\n ,a.ct_id ");
		buf.append("\n ,a.chennel_nm ");
		buf.append("\n ,a.route_nm ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY a.num DESC , A.REQ_DT DESC) AS rownum  ");
		buf.append("\n  from ( ");
		buf.append("\n   select  distinct ");

		buf.append("\n  value(mst.title,'') as  title ");
		buf.append("\n ,value(loc.NUM,0) as  NUM");
		buf.append("\n ,value(inst.CTI_ID,0) as CTI_ID ");

		buf.append("\n , value(TC.PROGRESS,'0')   AS TC_PROGRESS");
		buf.append("\n , case when value(tc.job_status,'') = '' then ''  ");
		buf.append("\n  when tc.job_status not in ('C','W','E','I') THEN '에러' ");
		buf.append("\n  ELSE value(CODE.DESC,'') END   AS TC_STATE  ");
		buf.append("\n ,value(LOC.PRIORITY,5)   AS LOC_PRIORITY");
		buf.append("\n ,value(CODE2.DESC,'')   AS LOC_STATE ");
		buf.append("\n ,value(LOC.PROGRESS,'0')  PROGRESS ");

		buf.append("\n ,value(LOC.COPY_PROGRESS,'0') as COPY_PROGRESS ");
		buf.append("\n ,value(CODE3.DESC,'')   AS COPY_STATUS ");
		buf.append("\n ,value(LOC.BACKUP_PROGRESS,'0') as BACKUP_PROGRESS ");
		buf.append("\n ,value(CODE4.DESC,'')   AS BACKUP_STATUS ");
		buf.append("\n ,value(LOC.DOWN_PROGRESS,'0') as DOWN_PROGRESS");

		buf.append("\n ,value(CODE5.DESC,'') as DOWN_STATE  ");
		buf.append("\n ,value(LOC.CHANGE_PROGRESS,'0') as CHANGE_PROGRESS ");
		buf.append("\n ,value(CODE6.DESC,'')   AS CHANGE_STATE ");
		buf.append("\n ,value(user.USER_NM,'') as REQ_ID  ");
		buf.append("\n ,value(CODE7.DESC,'') as  CT_TYP");
		buf.append("\n ,value(loc.reg_dtm,'') as  REQ_DT ");

		buf.append("\n ,value(code8.DESC,'') as cocd ");
		buf.append("\n ,value(TC.TC_ID,'') as TC_ID ");
		buf.append("\n ,value(eq.das_eq_use_ip,'') as tc_ip ");
		buf.append("\n ,value(chage_eq.das_eq_use_ip,'') as change_ip ");
		buf.append("\n ,value(chage_eq.das_eq_nm,'') as change_nm ");
		buf.append("\n ,con.ct_id ");
		buf.append("\n ,value(code9.desc,'') as chennel_nm ");
		buf.append("\n ,value(code10.desc,'') as route_nm  ");
		buf.append("\n  from metadat_mst_Tbl mst	 ");
		buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.master_ID =mst.master_id  ");
		buf.append("\n  inner join contents_tbl con  on con.ct_id = map.ct_id  ");
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = con.ct_id  and inst.cti_fmt like '1%' ");
		buf.append("\n  left outer join contents_loc_tbl loc on inst.CTI_ID = loc.CTI_ID  ");
		buf.append("\n  left outer join tc_job_tbl tc on tc.CT_ID = inst.CT_ID AND TC.REQ_CD='LRCT' AND TC.TC_TYPE<>'001' ");
		buf.append("\n left outer join DAS_EQUIPMENT_TBL eq on eq.DAS_EQ_NM=tc.TC_ID  ");
		buf.append("\n left outer join DAS_EQUIPMENT_TBL chage_eq on chage_eq.das_eq_id = loc.change_eq_id and chage_eq.use_yn='Y' ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P062' AND CODE.SCL_CD=TC.JOB_STATUS ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='P062' AND CODE2.SCL_CD=LOC.JOB_STATUS ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='P062' AND CODE3.SCL_CD=LOC.COPY_STATUS ");
		buf.append("\n left outer join code_tbl code4 on code4.CLF_CD='P062' AND CODE4.SCL_CD=LOC.BACKUP_STATUS ");
		buf.append("\n left outer join code_tbl code5 on code5.CLF_CD='P062' AND CODE5.SCL_CD=LOC.DOWN_STATUS ");
		buf.append("\n left outer join code_tbl code6 on code6.CLF_CD='P062' AND CODE6.SCL_CD=LOC.CHANGE_STATUS ");
		buf.append("\n left outer join code_tbl code8 on code8.CLF_CD='P058' AND CODE8.SCL_CD=MST.COCD ");
		buf.append("\n left outer join code_tbl code7 on code7.CLF_CD='A002' AND CODE7.SCL_CD=CON.CT_TYP ");
		buf.append("\n left outer join code_tbl code9 on code9.CLF_CD='P065' AND CODE9.SCL_CD=MST.Chennel_cd ");
		buf.append("\n left outer join code_tbl code10 on code10.CLF_CD='A052' AND code10.SCL_CD=MST.arch_route  ");
		buf.append("\n LEFT OUTER join user_info_tbl user on user.sbs_user_id = inst.regrid ");


		buf.append("\n  where 1=1  ");

		buf.append("\n   AND  VALUE(MST.DEL_DD,'')=''  ");

		/*

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}

		 */
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and SUBSTR(mst.REG_DT,1,8) >=  '"+monitoringDO.getStart_search_dd()+"' ");
		}


		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and  SUBSTR(mst.REG_DT,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}

		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  MST.TITLE LIKE  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(!monitoringDO.getStatus().equals("")){
			buf.append("\n  and( tc.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR LOC.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"')");
		}
		buf.append("\n  )a )B  ");


		buf.append("\n where B.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);


		return buf.toString();	
	}

	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String countArchiveMonitoring(MonitoringDO monitoringDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");

		buf.append("\n  from metadat_mst_Tbl mst	 ");
		buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.master_ID =mst.master_id  ");
		buf.append("\n  inner join contents_tbl con  on con.ct_id = map.ct_id  ");
		buf.append("\n  inner join contents_inst_tbl inst on inst.ct_id = con.ct_id and inst.cti_fmt like '1%'  ");
		buf.append("\n  left outer join contents_loc_tbl loc on inst.CTI_ID = loc.CTI_ID  ");
		buf.append("\n left outer join tc_job_tbl tc on tc.CT_ID = inst.CT_ID ");
		//buf.append("\n left outer join user_info_Tbl user on user.sbs_user_id = ");
		buf.append("\n where 1=1   ");
		buf.append("\n   AND  VALUE(MST.DEL_DD,'')=''    ");



		/*if(!monitoringDO.getReq_id().equals("")){
				buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
			}


			if(!monitoringDO.getReq_nm().equals("")){
				buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
			}*/
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and SUBSTR(mst.REG_DT,1,8) >=  '"+monitoringDO.getStart_search_dd()+"' ");
		}


		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and  SUBSTR(mst.REG_DT,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}



		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  MST.TITLE LIKE  '%"+monitoringDO.getTitle()+"%' ");
		}

		if(!monitoringDO.getStatus().equals("")){
			buf.append("\n  and( tc.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR LOC.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"')");
		}
		return buf.toString();	
	}


	/**
	 * 사용자 로그인 현황 테이블 총 갯수를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws DASException
	 */
	@Deprecated
	public static final String TCount(MonitoringDO monitoringDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");

		buf.append("\n  FROM TC_JOB_TBL TC  ");
		buf.append("\n  LEFT OUTER JOIN CART_CONT_TBL CART ON CART.CT_ID = TC.CT_ID  or cart.cart_no = tc.cart_no");
		buf.append("\n  INNER JOIN DOWN_CART_TBL DOWN ON CART.CART_NO = DOWN.CART_NO ");
		buf.append("\n  LEFT OUTER JOIN CONTENTS_DOWN_tBL CDOWN ON CDOWN.CART_NO = CART.CART_NO AND CDOWN.CART_SEQ = CART.CART_SEQ ");
		buf.append("\n  INNER JOIN (SELECT CT_ID, MASTER_ID, DEL_DD FROM CONTENTs_MAPP_tBL GROUP BY CT_ID, MASTER_ID, DEL_dD ) MAP ON MAP.CT_ID = TC.CT_ID ");
		buf.append("\n  INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID ");
		buf.append("\n left outer JOIN user_info_Tbl user ON user.sbs_user_id = tc.req_id ");

		buf.append("\n  WHERE (DOWN.DOWN_GUBUN='006' OR TC.REQ_CD LIKE 'CT%') ");


		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		//buf.append("\n ORDER BY ariel.TASK_ID desc, ARIEL.PRIORITY  ");
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(tc.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}
		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(tc.reg_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}
		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}
		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and mst.title like  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(!monitoringDO.getStatus().equals("")){
			buf.append("\n  and (tc.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR CDOWN.JOB_STATUS='"+monitoringDO.getStatus()+"') ");
		}
		return buf.toString();	
	}



	/**
	 * tc 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	
	public static final String selectTCinfoQuery(MonitoringDO monitoringDO, String flag) {
		StringBuffer buf = new StringBuffer();
		buf.append("\nSELECT                                                                                                               ");
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(flag)) {
			buf.append("\n  	count(*) as cnt                                                                                       ");
		} else {
			buf.append("\n  	a.*,                                                                                        ");
		    buf.append("\n      VALUE(code1.DESC, '') job_status,                                                 ");
		    buf.append("\n      VALUE(code2.DESC, '') down_status, value(eq.DAS_EQ_USE_IP,'') as tc_ip                           ");
		}
	    buf.append("\n  FROM (                                                                                                             ");
	    buf.append("\n      SELECT                                                                                                         ");
		buf.append("\n	         tc.SEQ, tc.REQ_CD, tc.PROGRESS, tc.PRIORITY, value(cdown.PROGRESS, '0') AS down_progress,                         ");
	    buf.append("\n           tc.CT_ID, tc.CART_NO, tc.REG_DT, tc.TC_ID, tc.JOB_STATUS, value(user.user_nm,'') as req_nm,                                                 ");
	    buf.append("\n           cart.REGRID, tc.JOB_STATUS down_status, mst.TITLE, TC.REG_DT AS REQ_DT,                                             ");
	    buf.append("\n           ROW_NUMBER() OVER(ORDER BY cart.reg_dt desc) AS rownum                                                    ");
	    buf.append("\n      FROM CART_CONT_TBL cart                                                                                              ");
	    buf.append("\n      	inner JOIN DOWN_CART_TBL down ON cart.CART_NO = down.CART_NO                                                 ");
	    buf.append("\n      	left outer JOIN CONTENTS_DOWN_TBL cdown ON cart.CART_NO = cdown.CART_NO AND cart.CART_SEQ = cdown.CART_SEQ   ");
	    buf.append("\n      	left outer JOIN TC_JOB_TBL tc ON cart.CT_ID = tc.CT_ID                                                       ");
	    buf.append("\n      	inner JOIN METADAT_MST_TBL mst ON cart.CT_ID = mst.RPIMG_CT_ID                                               ");
	    buf.append("\n  		left outer JOIN USER_INFO_TBL user on user.SBS_USER_ID = cart.REGRID                                                  ");
	    buf.append("\n      WHERE 1=1                                                                                                      ");
	    buf.append("\n      	AND (down.DOWN_GUBUN='006' OR tc.REQ_CD LIKE 'CT%')                                                          ");
	    
	    if(org.apache.commons.lang.StringUtils.isNotBlank(monitoringDO.getReq_id())){
			buf.append("\n  	AND user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}

	    if(org.apache.commons.lang.StringUtils.isNotBlank(monitoringDO.getReq_nm())){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(monitoringDO.getStart_search_dd())){
			buf.append("\n  and substr(tc.reg_dt,1,8) between '"+monitoringDO.getStart_search_dd()+"' and '"+monitoringDO.getEnd_serach_dd()+"'");
		}

		if(org.apache.commons.lang.StringUtils.isNotBlank(monitoringDO.getTitle())){
			buf.append("\n  and mst.title like  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(org.apache.commons.lang.StringUtils.isNotBlank(monitoringDO.getStatus())){
			buf.append("\n  and (tc.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR CDOWN.JOB_STATUS='"+monitoringDO.getStatus()+"') ");
		}
		
	    buf.append("\n  ) a                                                                                                                ");
	    buf.append("\n  left outer JOIN CODE_TBL code1 ON a.job_status = code1.SCL_CD AND code1.CLF_CD = 'P062'                            ");
	    buf.append("\n  left outer JOIN CODE_TBL code2 ON a.down_status = code2.SCL_CD AND code2.CLF_CD = 'P062'                           ");
	    
	    buf.append("\n  left outer JOIN DAS_EQUIPMENT_TBL eq on a.TC_ID = eq.DAS_EQ_NM                                                     ");
	    buf.append("\n  WHERE 1=1                                                                                                          ");
	    buf.append("\n  	AND eq.USE_YN = 'Y'                                                                                              ");
	    if(DASBusinessConstants.PageQueryFlag.NORMAL.equals(flag)) {
	    	buf.append("\n  	AND a.ROWNUM BETWEEN "+monitoringDO.getStart_page()+" AND "+(monitoringDO.getStart_page()+99)+"                                                                                    ");
	    }
	    buf.append("\n with ur");
		return buf.toString();
	}
	
	
	@Deprecated
	public static final String selectTCinfo(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n MST.TITLE ");
		buf.append("\n ,tc.SEQ ");
		buf.append("\n ,TC.REQ_CD ");
		buf.append("\n ,value(code.desc ,'' ) as JOB_STATUS ");
		buf.append("\n ,TC.PROGRESS ");
		buf.append("\n ,TC.PRIORITY ");
		buf.append("\n ,value(cdown.PROGRESS,'0') as down_progress ");
		buf.append("\n ,value(code2.desc ,'' ) as down_status ");
		buf.append("\n ,TC.ct_id ");
		buf.append("\n ,TC.REG_DT AS REQ_DT ");
		buf.append("\n ,value(eq.DAS_EQ_USE_IP,'') as tc_IP ");
		buf.append("\n ,value(user.user_nm,'') as req_nm ");
		buf.append("\n ,TC.TC_id ");
		buf.append("\n  ,ROW_NUMBER() OVER(ORDER BY tc.reg_dt desc) AS rownum ");
		buf.append("\n FROM TC_JOB_TBL TC ");
		buf.append("\n LEFT OUTER JOIN CART_CONT_TBL CART ON CART.CT_ID = TC.CT_ID or cart.cart_no = tc.cart_no");
		buf.append("\n LEFT OUTER JOIN das_equipment_tbl eq on eq.DAS_EQ_NM=tc.TC_ID and eq.use_yn='Y'");
		buf.append("\n LEFT OUTER JOIN contents_down_tbl cdown on cdown.CART_NO= cart.cart_no and cdown.CART_SEQ = cart.CART_SEQ ");
		buf.append("\n INNER JOIN DOWN_CART_TBL DOWN ON CART.CART_NO = DOWN.CART_NO ");
		buf.append("\n INNER JOIN (SELECT CT_ID, MASTER_ID, DEL_DD FROM CONTENTs_MAPP_tBL GROUP BY CT_ID, MASTER_ID, DEL_dD ) MAP ON MAP.CT_ID = TC.CT_ID ");
		buf.append("\n INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID ");
		buf.append("\n left outer JOIN user_info_Tbl user ON user.sbs_user_id = tc.req_id ");
		buf.append("\n left outer JOIN code_tbl code ON code.clf_cd='P062' and code.scl_cd=tc.JOB_STATUS ");
		buf.append("\n left outer JOIN code_tbl code2 ON code2.clf_cd='P062' and code2.scl_cd=cdown.JOB_STATUS ");
		buf.append("\n WHERE  (DOWN.DOWN_GUBUN='006' OR TC.REQ_CD LIKE 'CT%')  ");


		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}

		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(tc.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}
		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(tc.reg_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}
		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}

		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and mst.title like  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(!monitoringDO.getStatus().equals("")){
			buf.append("\n  and (tc.JOB_STATUS ='"+monitoringDO.getStatus()+"' OR CDOWN.JOB_STATUS='"+monitoringDO.getStatus()+"') ");
		}
		buf.append("\n ORDER BY TC.REG_DT DESC ");
		buf.append("\n  ) a ");


		buf.append("\n where a.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);
		buf.append("\n with ur");


		return buf.toString();	
	}





	/**
	 * tm 현황 테이블 총 갯수를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws DASException
	 */
	public static final String TmCount(MonitoringDO monitoringDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");

		buf.append("\n from CONTENTS_down_TBL  down ");
		buf.append("\n inner join ariel_info_tbl ariel on ariel.CART_NO = down.CART_NO and ariel.CART_SEQ=down.CART_seq  ");
		buf.append("\n inner join cart_cont_Tbl cart on cart.CART_NO = ariel.CART_NO and cart.CART_SEQ = ariel.CART_SEQ ");
		buf.append("\n inner join down_cart_tbl dct on dct.CART_NO = ariel.CART_NO  ");
		buf.append("\n inner join (select master_id,ct_id,del_dd from contents_mapp_tbl group by master_id,ct_id,del_dd ) map on map.CT_ID = cart.CT_ID ");
		buf.append("\n left outer join user_info_Tbl user on user.SBS_USER_ID = cart.REGRID ");
		buf.append("\n inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD='A001' AND CODE.SCL_CD=MST.CTGR_L_CD ");

		buf.append("\n  where 1=1 ");

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		return buf.toString();	
	}


	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectTminfo(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n ariel.STATUS ");
		buf.append("\n ,CASE WHEN dct.down_gubun ='001' THEN 'PDS' ");
		buf.append("\n WHEN dct.down_gubun ='002' THEN 'NDS' ");
		buf.append("\n WHEN dct.down_gubun ='005' THEN '계열사' ");
		buf.append("\n ELSE '다운로드' ");
		buf.append("\n END AS JOB_NM ");
		buf.append("\n ,code.DESC ");
		buf.append("\n ,ariel.TASK_ID ");
		buf.append("\n ,VALUE(DOWN.REG_DTM,'') AS REG_DTM ");
		buf.append("\n ,CART.REGRID ");
		buf.append("\n ,VALUE(USER.USER_NM,'') AS USER_NM ");
		buf.append("\n ,CART.MEDIA_ID ");
		buf.append("\n ,ARIEL.PRIORITY ");
		buf.append("\n  ,ROW_NUMBER() OVER(ORDER BY ariel.TASK_ID) AS rownum ");

		buf.append("\n from CONTENTS_down_TBL  down ");
		buf.append("\n inner join ariel_info_tbl ariel on ariel.CART_NO = down.CART_NO and ariel.CART_SEQ=down.CART_seq  ");
		buf.append("\n inner join cart_cont_Tbl cart on cart.CART_NO = ariel.CART_NO and cart.CART_SEQ = ariel.CART_SEQ ");
		buf.append("\n inner join down_cart_tbl dct on dct.CART_NO = ariel.CART_NO  ");
		buf.append("\n inner join (select master_id,ct_id,del_dd from contents_mapp_tbl group by master_id,ct_id,del_dd ) map on map.CT_ID = cart.CT_ID ");
		buf.append("\n left outer join user_info_Tbl user on user.SBS_USER_ID = cart.REGRID ");
		buf.append("\n inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD='A001' AND CODE.SCL_CD=MST.CTGR_L_CD ");

		buf.append("\n  where 1=1 ");

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		buf.append("\n ORDER BY ariel.TASK_ID desc, ARIEL.PRIORITY  ");
		buf.append("\n  ) a ");


		buf.append("\n where a.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);


		return buf.toString();	
	}




	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectArchiveDetail(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n mst.title ");
		buf.append("\n ,case when mst.ctgr_l_cd='100' then fm_Dt ");
		buf.append("\n when mst.ctgr_l_cd='200' then brd_dd ");
		buf.append("\n else brd_dd ");
		buf.append("\n end as brd_dd ");
		buf.append("\n ,loc.PROGRESS ");
		buf.append("\n ,CODE.DESC AS CTGR_L_NM ");
		buf.append("\n ,CODE2.DESC AS CT_CLA_NM ");
		buf.append("\n ,CODE3.DESC AS CT_TYP_NM ");
		buf.append("\n ,con.media_id ");
		buf.append("\n from  ");
		buf.append("\n contents_loc_tbl loc ");
		buf.append("\n inner join contents_inst_tbl inst on inst.cti_id = loc.cti_id ");
		buf.append("\n inner join contents_tbl con on con.CT_ID = inst.CT_ID ");		
		buf.append("\n inner join (select master_id , ct_id ,del_dd from contents_mapp_tbl GROUP BY  master_id , ct_id ,del_dd) map on map.CT_ID = con.CT_ID ");
		buf.append("\n inner join metadat_msT_tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P002' AND CODE.SCL_CD = MST.CTGR_L_CD ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='A001' AND CODE2.SCL_CD = CON.CT_CLA  ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='A002' AND CODE3.SCL_CD = CON.CT_TYP ");

		buf.append("\n  where 1=1 ");

		if(!(monitoringDO.getKey()== 0)){
			buf.append("\n  and loc.num ="+monitoringDO.getKey()+" ");
		}


		return buf.toString();	
	}



	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectTCDetail(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n mst.title ");
		buf.append("\n ,case when mst.ctgr_l_cd='100' then fm_Dt ");
		buf.append("\n when mst.ctgr_l_cd='200' then brd_dd ");
		buf.append("\n else brd_dd ");
		buf.append("\n end as brd_dd ");
		buf.append("\n ,tc.PROGRESS ");
		buf.append("\n ,CODE.DESC AS CTGR_L_NM ");
		buf.append("\n ,CODE2.DESC AS CT_CLA_NM ");
		buf.append("\n ,CODE3.DESC AS CT_TYP_NM ");
		buf.append("\n ,con.media_id ");
		buf.append("\n from  ");
		buf.append("\n tc_job_tbl tc ");
		buf.append("\n inner join contents_tbl con on con.CT_ID =  tc.ct_id  ");		
		buf.append("\n inner join (select master_id , ct_id ,del_dd from contents_mapp_tbl GROUP BY  master_id , ct_id ,del_dd) map on map.CT_ID = con.CT_ID ");
		buf.append("\n inner join metadat_msT_tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P002' AND CODE.SCL_CD = MST.CTGR_L_CD ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='A001' AND CODE2.SCL_CD = CON.CT_CLA  ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='A002' AND CODE3.SCL_CD = CON.CT_TYP ");

		buf.append("\n  where 1=1 ");

		if(!(monitoringDO.getKey()== 0)){
			buf.append("\n  and tc.seq ="+monitoringDO.getKey()+" ");
		}


		return buf.toString();	
	}



	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectTmDetail(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n mst.title ");
		buf.append("\n ,case when mst.ctgr_l_cd='100' then fm_Dt ");
		buf.append("\n when mst.ctgr_l_cd='200' then brd_dd ");
		buf.append("\n else brd_dd ");
		buf.append("\n end as brd_dd ");
		buf.append("\n ,ariel.PROGERESS ");
		buf.append("\n ,CODE.DESC AS CTGR_L_NM ");
		buf.append("\n ,CODE2.DESC AS CT_CLA_NM ");
		buf.append("\n ,CODE3.DESC AS CT_TYP_NM ");
		buf.append("\n ,cart.media_id ");
		buf.append("\n from  ");
		buf.append("\n ariel_info_tbl ariel ");
		buf.append("\n inner join contents_down_tbl cdown on cdown.CART_NO=ariel.CART_NO and cdown.CART_SEQ = ariel.CART_SEQ  ");
		buf.append("\n inner join cart_cont_tbl cart on cart.cart_no = ariel.CART_NO and cart.CART_SEQ = ariel.CART_SEQ  ");
		buf.append("\n inner join contents_tbl con on con.CT_ID = cart.CT_ID  ");	
		buf.append("\n inner join (select master_id , ct_id ,del_dd from contents_mapp_tbl GROUP BY  master_id , ct_id ,del_dd) map on map.CT_ID = con.CT_ID ");
		buf.append("\n inner join metadat_msT_tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P002' AND CODE.SCL_CD = MST.CTGR_L_CD ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='A001' AND CODE2.SCL_CD = CON.CT_CLA  ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='A002' AND CODE3.SCL_CD = CON.CT_TYP ");

		buf.append("\n  where 1=1 ");

		if(!(monitoringDO.getKey()== 0)){
			buf.append("\n  and ariel.task_id ="+monitoringDO.getKey()+" ");
		}


		return buf.toString();	
	}




	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectManualDeleteList(ManualDeleteDO manualDeleteDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  * FROM ( ");
		buf.append("\n select ");
		buf.append("\n a.gubun ");
		buf.append("\n ,a.gubun_cd ");
		buf.append("\n ,a.filename ");
		buf.append("\n ,a.path ");
		buf.append("\n ,a.num ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY a.num) AS rownum ");
		buf.append("\n from ( ");

		if(manualDeleteDO.getGubun().equals("001")||manualDeleteDO.getGubun().equals("000")){
			buf.append("\n select  distinct ");
			buf.append("\n '아카이브' as gubun ");
			buf.append("\n ,'001' as gubun_cd  ");
			buf.append("\n , loc.filename ");
			buf.append("\n , loc.path ");
			buf.append("\n , loc.num ");		
			buf.append("\n from contents_loc_tbl loc  ");
			buf.append("\n inner join contents_inst_tbl inst on inst.CTI_ID = loc.CTI_ID  ");
			buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.CT_ID = inst.CT_ID ");
			buf.append("\n inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
			buf.append("\n where 1=1  ");
			//buf.append("\n AND MST.ARCH_REG_DD<>'' ");
			buf.append("\n AND loc.job_status='C' ");
			buf.append("\n AND inst.fl_path <>'' ");
			if(!manualDeleteDO.getStrat_reg_dt().equals("")){
				buf.append("\n and substr(loc.reg_dtm,1,8) >=   '"+manualDeleteDO.getStrat_reg_dt()+"'");
				buf.append("\n and substr(loc.reg_dtm,1,8) <=   '"+manualDeleteDO.getEnd_reg_dt()+"'");
			}
		}

		if(manualDeleteDO.getGubun().equals("000")){
			buf.append("\n union all ");
		}


		if(manualDeleteDO.getGubun().equals("002")||manualDeleteDO.getGubun().equals("000")){

			buf.append("\n select ");
			buf.append("\n '다운로드' as gubun ");
			buf.append("\n ,'002' as gubun_cd ");
			buf.append("\n , DOWN.filename ");
			buf.append("\n , DOWN.path ");
			buf.append("\n , DOWN.num ");
			buf.append("\n from contents_down_tbl DOWN ");
			buf.append("\n INNER JOIN CART_CONT_TBL CART ON CART.CART_NO = DOWN.CART_NO AND CART.CART_SEQ = DOWN.CART_SEQ ");
			buf.append("\n where ");
			buf.append("\n DOWN.JOB_STATUS='C'  ");
			buf.append("\n AND DOWN.use_yn='Y' ");
			if(!manualDeleteDO.getStrat_reg_dt().equals("")){
				buf.append("\n and substr(DOWN.reg_dtm,1,8) >=   '"+manualDeleteDO.getStrat_reg_dt()+"'");
				buf.append("\n and substr(DOWN.reg_dtm,1,8) <=   '"+manualDeleteDO.getEnd_reg_dt()+"'");
			}


		}
		buf.append("\n )a )B ");
		buf.append("\n WHERE B.rownum between " + manualDeleteDO.getStart_page());
		int end = manualDeleteDO.getStart_page()+99;
		buf.append("\n  and "+ end );		



		return buf.toString();	
	}



	/**
	 * 사용자 로그인 현황 테이블 총 갯수를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws DASException
	 */
	public static final String ManualDeleteCount(ManualDeleteDO manualDeleteDO)
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n  count(*) as cnt  ");			
		buf.append("\n from ( ");

		if(manualDeleteDO.getGubun().equals("001")||manualDeleteDO.getGubun().equals("000")){
			buf.append("\n select  ");
			buf.append("\n '아카이브' as gubun ");
			buf.append("\n ,'001' as gubun_cd  ");
			buf.append("\n , loc.filename ");
			buf.append("\n , loc.path ");
			buf.append("\n , loc.num ");		
			buf.append("\n from contents_loc_tbl loc  ");
			buf.append("\n inner join contents_inst_tbl inst on inst.CTI_ID = loc.CTI_ID  ");
			buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.CT_ID = inst.CT_ID ");
			buf.append("\n inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
			buf.append("\n where 1=1  ");
			buf.append("\n AND MST.ARCH_REG_DD<>'' ");

			if(!manualDeleteDO.getStrat_reg_dt().equals("")){
				buf.append("\n and substr(loc.reg_dtm,1,8) >=   '"+manualDeleteDO.getStrat_reg_dt()+"'");
				buf.append("\n and substr(loc.reg_dtm,1,8) <=   '"+manualDeleteDO.getEnd_reg_dt()+"'");
			}
		}

		if(manualDeleteDO.getGubun().equals("000")){
			buf.append("\n union all ");
		}


		if(manualDeleteDO.getGubun().equals("002")||manualDeleteDO.getGubun().equals("000")){

			buf.append("\n select ");
			buf.append("\n '다운로드' as gubun ");
			buf.append("\n ,'002' as gubun_cd ");
			buf.append("\n , DOWN.filename ");
			buf.append("\n , DOWN.path ");
			buf.append("\n , DOWN.num ");
			buf.append("\n from contents_down_tbl DOWN ");
			buf.append("\n INNER JOIN CART_CONT_TBL CART ON CART.CART_NO = DOWN.CART_NO AND CART.CART_SEQ = DOWN.CART_SEQ ");
			buf.append("\n where ");
			buf.append("\n DOWN.JOB_STATUS='C'  ");

			if(!manualDeleteDO.getStrat_reg_dt().equals("")){
				buf.append("\n and substr(DOWN.reg_dtm,1,8) >=   '"+manualDeleteDO.getStrat_reg_dt()+"'");
				buf.append("\n and substr(DOWN.reg_dtm,1,8) <=   '"+manualDeleteDO.getEnd_reg_dt()+"'");
			}


		}
		buf.append("\n )a  ");
		return buf.toString();	
	}


	/**
	 * 에러 목록을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectErroeList(ErrorLogDO errorLogDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  * FROM ( ");
		buf.append("\n select ");
		buf.append("\n eq.das_eq_nm ");
		buf.append("\n ,eq.das_eq_use_ip  ");
		buf.append("\n ,ERROR.REG_DT ");
		buf.append("\n ,ERROR.ERROR_CONT ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY ERROR.REG_DT) AS rownum ");
		buf.append("\n from ERROR_LOG_TBL ERROR ");
		buf.append("\n  LEFT OUTER JOIN das_equipment_tbl eq ON eq.das_eq_id = error.SERVER_NM where 1=1");
		if(!errorLogDO.getError_type().equals("")){
			buf.append("\n and ERROR.ERROR_TYPE = '"+errorLogDO.getError_type()+"'");
		}
		if(!errorLogDO.getStart_reg_dt().equals("")){
			buf.append("\n  AND SUBSTR(ERROR.REG_dT,1,8)>='"+errorLogDO.getStart_reg_dt()+"'");
			buf.append("\n  AND SUBSTR(ERROR.REG_dT,1,8)<='"+errorLogDO.getEnd_reg_dt()+"'");
		}
		buf.append("\n and eq.USE_YN='Y'  ");
		buf.append("\n )a  ");
		buf.append("\n WHERE a.rownum between " + errorLogDO.getStart_page());
		int end = errorLogDO.getStart_page()+99;
		buf.append("\n  and "+ end );		



		return buf.toString();	
	}



	/**
	 * 사용자 로그인 현황 테이블 총 갯수를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws DASException
	 */
	public static final String ErroeCount(ErrorLogDO errorLogDO)
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n  count(*) as cnt  ");			
		buf.append("\n from ERROR_LOG_TBL WHERE 1=1");

		if(!errorLogDO.getError_type().equals("")){
			buf.append("\n and ERROR_TYPE = '"+errorLogDO.getError_type()+"'");
		}
		if(!errorLogDO.getStart_reg_dt().equals("")){
			buf.append("\n  AND SUBSTR(REG_dT,1,8)>='"+errorLogDO.getStart_reg_dt()+"'");
			buf.append("\n  AND SUBSTR(REG_dT,1,8)<='"+errorLogDO.getEnd_reg_dt()+"'");
		}

		return buf.toString();	
	}



	/**
	 * 에러 목록을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectServerList(ServersDO serversDO) 
	{
		StringBuffer buf = new StringBuffer();


		buf.append("\n select ");
		buf.append("\n value(equip.DAS_EQ_NM,'') as DAS_EQ_NM ");
		buf.append("\n ,value(code6.desc, '대기중') as desc ");
		buf.append("\n ,case when equip.job_status ='T'  THEN '장애'  ");
		buf.append("\n  when equip.job_status ='E' THEN '에러' ");
		buf.append("\n    ELSE '정상' END as tel_stat  ");
		buf.append("\n ,value(equip.DAS_EQ_USE_IP,'') as DAS_EQ_USE_IP ");
		buf.append("\n ,value(mst.TITLE,'') as  TITLE ");
		buf.append("\n ,value(equip.REG_DT,'') as REG_DT ");
		buf.append("\n ,value(equip.MOD_DT,'') as MOD_DT ");
		buf.append("\n ,value(equip.das_eq_clf_Cd,'') as das_eq_clf_Cd ");
		buf.append("\n ,value(equip.das_eq_id,'') as das_eq_id ");
		buf.append("\n ,value(equip.das_eq_use_port,'') as port ");
		buf.append("\n from das_equipment_tbl equip ");
		buf.append("\n left outer join contents_insT_Tbl inst on inst.CTI_ID = equip.CTI_ID and inst.CTI_FMT like '1%' ");
		buf.append("\n left outer join (select ct_id, master_id ,del_dd from contents_mapp_tbl group by ct_id, master_id ,del_dd ) map on map.ct_id = inst.CT_ID ");
		buf.append("\n left outer join METADAT_MST_TBL mst on mst.MASTER_ID = map.MASTER_ID ");
		buf.append("\n left outer join code_tbl code6 on code6.CLF_CD='P062' AND CODE6.SCL_CD= equip.job_status");
		buf.append("\n where equip.USE_YN='Y' ");

		buf.append("\n  ORDER BY DAS_EQ_NM ASC ");




		return buf.toString();	
	}


	public static final String selectPreProcessingQuery2(PreProcessingDO preProcessingDO)
	{

		StringBuffer buf = new StringBuffer();

		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select  * from (   ");
		}


		//SBS 방송본
		if(preProcessingDO.getSt_gubun().equals("1")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(ct.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");

			buf.append("\n , case when    ");
			buf.append("\n  mmt.ARCH_ROUTE ='P' THEN  '"+dasHandler.getProperty("ARCREQ")+"/' ||right(tc.INPUT_HR,10) ||'/'||tc.INPUT_HR_NM ");
			buf.append("\n  WHEN   mmt.ARCH_ROUTE ='N' THEN     ");
			buf.append("\n   '"+dasHandler.getProperty("MP2")+"/'||left(INST.reg_dt,6)||'/'||substr(INST.reg_dt,7,2)||'/'||INST.wrk_file_nm    ");
			buf.append("\n  else ''  ");
			buf.append("\n end  AS file_path   ");


			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,value(pgm.PGM_NM,'') as pgm_nm ");
			buf.append("\n ,value(CT.EDTRID,'') as EDTRID");
			buf.append("\n ,'NEARLINE' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID         ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	INNER JOIN DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");

			buf.append("\n WHERE 1=1                                                                                                        ");

			buf.append("\n 	and MMT.ARCH_REG_DD =''                                          ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");
			buf.append("\n 	and mmt.manual_yn ='Y'      ");

			buf.append("\n  AND mmt.ARCH_ROUTE like 'O%'                                           ");
			buf.append("\n  AND mmt.COCD = 'S'                                           ");

			if(!preProcessingDO.getChennel().equals("")){
				buf.append("\n AND MMT.CHENNEL_CD= '"+preProcessingDO.getChennel()+"'      ");
			}
			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//			if(!preProcessingDO.getSt_gubun().equals("")){ 
				buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//			}
				buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}




		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}


		//SBS 비방송본
		if(preProcessingDO.getSt_gubun().equals("2")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(CT.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");


			buf.append("\n  ,case when value(inst.ORG_FILE_NM,'') ='' then inst.FL_PATH||'/'||inst.WRK_FILE_NM  ");
			buf.append("\n  when value(inst.ORG_FILE_NM,'') <>'' then inst.FL_PATH||'/'||inst.ORG_FILE_NM     ");

			buf.append("\n else '전송중비중입니다' end   AS file_path     ");






			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,value(pgm.PGM_NM,'') as pgm_nm ");
			buf.append("\n ,value(CT.EDTRID,'') as EDTRID");
			buf.append("\n ,'NEARLINE' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' ");
			buf.append("\n  END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID  and mmt.manual_yn='Y'          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	LEFT OUTER JOIN  DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//			buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			buf.append("\n 	and mmt.manual_yn ='Y'                                                ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");

			/*if(preProcessingDO.getSt_gubun().equals("0")){

				}else if(preProcessingDO.getSt_gubun().equals("1")){
					buf.append("\n AND INST.DTL_YN='Y' ");
				}else if(preProcessingDO.getSt_gubun().equals("2")){
					buf.append("\n AND INST.DTL_YN='N'  ");
				}
			 */
			buf.append("\n  AND (mmt.ARCH_ROUTE like 'P%' OR   mmt.ARCH_ROUTE like 'D%' )                                        ");
			buf.append("\n  AND mmt.COCD = 'S'                                           ");

			if(!preProcessingDO.getChennel().equals("")){
				buf.append("\n AND MMT.CHENNEL_CD= '"+preProcessingDO.getChennel()+"'      ");
			}
			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//			if(!preProcessingDO.getSt_gubun().equals("")){ 
				buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//			}
				buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}



		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}



		//미디어넷 방송본
		if(preProcessingDO.getSt_gubun().equals("3")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(CT.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");

			//buf.append("\n , inst.fl_path ||'/'|| inst.wrk_file_nm as file_path");

			buf.append("\n  ,case when value(inst.ORG_FILE_NM,'') ='' then inst.FL_PATH||'/'||inst.WRK_FILE_NM  ");
			buf.append("\n  when value(inst.ORG_FILE_NM,'') <>'' then inst.FL_PATH||'/'||inst.ORG_FILE_NM     ");

			buf.append("\n else '전송중비중입니다' end   AS file_path     ");



			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,value(pgm.PGM_NM,'') as pgm_nm ");
			buf.append("\n ,value(CT.EDTRID,'') as EDTRID");
			buf.append("\n ,'MediaNet' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' ");
			buf.append("\n  END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID  and mmt.manual_yn='Y'          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	LEFT OUTER JOIN  DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//				buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			buf.append("\n 	and mmt.manual_yn ='Y'                                                ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");

			/*if(preProcessingDO.getSt_gubun().equals("0")){

					}else if(preProcessingDO.getSt_gubun().equals("1")){
						buf.append("\n AND INST.DTL_YN='Y' ");
					}else if(preProcessingDO.getSt_gubun().equals("2")){
						buf.append("\n AND INST.DTL_YN='N'  ");
					}
			 */
			buf.append("\n  AND mmt.ARCH_ROUTE like 'O%'                       ");
			buf.append("\n  AND mmt.COCD = 'M'                                           ");

			if(!preProcessingDO.getChennel().equals("")){
				buf.append("\n AND MMT.CHENNEL_CD= '"+preProcessingDO.getChennel()+"'      ");
			}
			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//				if(!preProcessingDO.getSt_gubun().equals("")){ 
					buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//				}
					buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}





		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}


		//미디어넷 비방송본
		if(preProcessingDO.getSt_gubun().equals("4")||preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(CT.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");

			//buf.append("\n , inst.fl_path ||'/'|| inst.wrk_file_nm as file_path");

			buf.append("\n  ,case when value(inst.ORG_FILE_NM,'') ='' then inst.FL_PATH||'/'||inst.WRK_FILE_NM  ");
			buf.append("\n  when value(inst.ORG_FILE_NM,'') <>'' then inst.FL_PATH||'/'||inst.ORG_FILE_NM     ");

			buf.append("\n else '전송중비중입니다' end   AS file_path     ");



			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,value(pgm.pgm_id,0)  as pgm_id  ");
			buf.append("\n ,value(pgm.PGM_NM,'') as pgm_nm ");
			buf.append("\n ,value(CT.EDTRID,'') as EDTRID");
			buf.append("\n ,'MediaNet' AS GUBUN ");
			buf.append("\n  ,0 as cart_no ");
			buf.append("\n  ,CASE WHEN MMT.DATA_STAT_CD ='' OR MMT.DATA_STAT_CD='000' THEN 'N' ");
			buf.append("\n  WHEN MMT.DATA_STAT_CD NOT IN  ('','000') THEN 'Y' ");
			buf.append("\n  ELSE 'N' ");
			buf.append("\n  END AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID  and mmt.manual_yn='Y'          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = CT.REGRID         ");
			buf.append("\n 	LEFT OUTER JOIN  DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//					buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			buf.append("\n 	and mmt.manual_yn ='Y'                                                ");
			buf.append("\n 	and (map.del_dd is null or map.del_dd ='')                                               ");

			buf.append("\n  AND (mmt.ARCH_ROUTE like 'P%' OR   mmt.ARCH_ROUTE like 'D%' )                                        ");
			buf.append("\n  AND mmt.COCD = 'M'                                           ");

			if(!preProcessingDO.getChennel().equals("")){
				buf.append("\n AND MMT.CHENNEL_CD= '"+preProcessingDO.getChennel()+"'      ");
			}
			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND CT.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND CT.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//					if(!preProcessingDO.getSt_gubun().equals("")){ 
						buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//					}
						buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");


		}


		if(preProcessingDO.getSt_gubun().equals("0")){

			buf.append("\n ) union all (    ");

		}



		//다운로드
		if(preProcessingDO.getSt_gubun().equals("5")||preProcessingDO.getSt_gubun().equals("0")){
			buf.append("\n  select    ");
			buf.append("\n  CODE5.DESC AS CTGR_L_NM ");
			buf.append("\n  ,case when mmt.ctgr_l_cd ='100' and mmt.pgm_id =0 then MMT.TITLE ");
			buf.append("\n  when mmt.ctgr_l_cd ='200' and mmt.pgm_id!=0 then pgm.pgm_nm ");
			buf.append("\n  else mmt.title end as title ");
			buf.append("\n  ,value (CODE4.DESC,'') AS CT_CLA_NM, LEFT(down.REG_DT,8) AS REQ_DT,value(UIT.USER_NM,'') as REQ_NM ");


			buf.append("\n  , '"+dasHandler.getProperty("MP2")+"'||CDOWN.PATH||'/'||CDOWN.FILENAME  AS file_path     ");

			buf.append("\n ,CASE WHEN CT.MEDIA_ID IS NULL THEN ''   ");
			buf.append("\n ELSE CT.MEDIA_ID   "); 
			buf.append("\n END MEDIA_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,CT.CT_ID   ");
			buf.append("\n ,MMT.MASTER_ID   ");
			buf.append("\n ,MMT.DATA_STAT_CD   ");
			buf.append("\n ,0  as pgm_id  ");
			buf.append("\n ,'' as PGM_NM ");

			buf.append("\n ,value(CT.EDTRID,'') as EDTRID");

			buf.append("\n ,'mp2' AS GUBUN ");

			buf.append("\n  , DOWN.CART_NO as cart_no ");
			buf.append("\n ,'Y' AS TC_YN ");
			buf.append("\n FROM DAS.CONTENTS_TBL CT    ");
			buf.append("\n INNER JOIN DAS.CONTENTS_INST_TBL INST ON CT.CT_ID=INST.CT_ID AND INST.CTI_FMT LIKE '%10%'           ");
			buf.append("\n  INNER JOIN (SELECT MASTER_ID,CT_ID,del_dd FROM DAS.CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID,del_dd) MAP ON MAP.CT_ID=INST.CT_ID    ");
			buf.append("\n 	INNER JOIN DAS.METADAT_MST_TBL MMT ON MMT.MASTER_ID = MAP.MASTER_ID       ");
			buf.append("\n 	INNER JOIN (SELECT CART_NO,CT_ID ,CART_SEQ FROM DAS.CART_CONT_TBL GROUP BY CART_NO,CT_ID,CART_SEQ ) CART ON CART.CT_ID = INST.CT_ID       ");
			buf.append("\n 	 INNER JOIN DAS.DOWN_CART_TBL DOWN ON DOWN.CART_NO = CART.CART_NO          ");
			buf.append("\n 	left outer JOIN DAS.pgm_info_tbl pgm ON MMT.pgm_id = pgm.pgm_id         ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE4 ON CODE4.CLF_CD = 'A001' AND CODE4.SCL_CD = CT.CT_CLA                                           ");
			buf.append("\n 	LEFT OUTER JOIN DAS.CODE_TBL AS CODE5 ON CODE5.CLF_CD = 'P002' AND CODE5.SCL_CD = MMT.CTGR_L_CD                ");
			buf.append("\n 	LEFT OUTER JOIN DAS.USER_INFO_TBL AS UIT ON UIT.SBS_USER_ID = DOWN.REQ_USRID          ");
			//buf.append("\n 	INNER JOIN DAS.TC_JOB_TBL TC ON TC.CT_ID = INST.CT_ID      ");
			buf.append("\n 	  INNER JOIN DAS.CONTENTS_DOWN_TBL  CDOWN ON CDOWN.CART_NO = CART.CART_NO     and CDOWN.cart_seq = cart.cart_seq    ");

			buf.append("\n WHERE 1=1                                                                                                        ");
			//buf.append("\n 	and auto.AUTO_YN='N'                                                  ");
			//buf.append("\n 	and (inst.ARCH_STE_YN='Y' OR inst.ARCH_STE_YN='N' )                                               ");
			buf.append("\n 	AND (DOWN.DEL_YN='N'  or DOWN.DEL_YN='' )                                              ");
			//buf.append("\n 	and mmt.data_stat_cd not in (  '000','')                                             ");

			buf.append("\n  AND DOWN.DOWN_GUBUN='003'                                               ");

			/*if(preProcessingDO.getSt_gubun().equals("0")){

				}else if(preProcessingDO.getSt_gubun().equals("1")){
					buf.append("\n AND INST.DTL_YN='Y' ");
				}else if(preProcessingDO.getSt_gubun().equals("2")){
					buf.append("\n AND INST.DTL_YN='N'  ");
				}
			 */


			if(!preProcessingDO.getFromDate().equals("")){
				buf.append("\n 			AND down.REG_DT >= '"+preProcessingDO.getFromDate()+"000000'                                                                               ");
			}
			if(!preProcessingDO.getToDate().equals("")){
				buf.append("\n          AND down.REG_DT <= '"+preProcessingDO.getToDate()+"595959'                                                                                     ");
			}
			/*		 // 2010년 10월 20 일 아카이브 요청 스토리지에 대해서 확인이 되어 있지 않은 상태 이므로 무조건 고해상도 스토리지만 검색
//			if(!preProcessingDO.getSt_gubun().equals("")){ 
				buf.append("\n 			AND DCT.STRG_LOC LIKE '%mp2%'                                                                               ");
//			}
				buf.append("\n 			AND DCT.down_gubun ='003'                                                                                   ");*/
			if(!preProcessingDO.getCt_cla().equals("")){
				buf.append("\n 			AND CT.CT_CLA = '"+preProcessingDO.getCt_cla()+"'                                                                                           ");
			}
			if(!preProcessingDO.getSearchKey().equals("")){
				buf.append("\n 			AND MMT.TITLE LIKE '%"+preProcessingDO.getSearchKey()+"%'                                                                                     ");
			}
			buf.append("\n  and (CT.del_yn ='' OR CT.DEL_YN='N')	 	 ");
			buf.append("\n  and value(cdown.UPDT_USER,'')<>'workflow' and value(cdown.job_status,'')='C'	 	 ");
			buf.append("\n order by mmt.reg_dt desc	 ");
			buf.append("\n WITH UR	 ");

		}

		if(preProcessingDO.getSt_gubun().equals("0")){


			buf.append("\n )   with ur  ");


		}

		return buf.toString();

	}



	/**
	 * 에러정보를 등록한다
	 * @param con 커넥션
	 * @param errorRegisterDO 정보가담겨있는 beans

	 * @throws DASException
	 */
	public static final String insertErrorRegQuery() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into ERROR_LOG_TBL( ");
		buf.append("\n 	SERVER_NM,  ");
		buf.append("\n 	ERROR_TYPE,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	ERROR_CONT  ");

		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?) ");

		return buf.toString();
	}



	/**
	 * wmvlist조회 (H264 전황용)
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws DASException
	 */
	public static final String selectWmvList(WmvH264DO wmvH264DO) 
	{
		StringBuffer buf = new StringBuffer();
		/*buf.append("\n SELECT a.* FROM ( ");
		buf.append("\n SELECT map.ct_id, ctid.VD_QLTY, ctid.cti_id, ctid.fl_path, ctid.cti_id||'.wmv' wrk_file_nm FROM ( ");
		buf.append("\n   SELECT ");

		buf.append("\n  ct.ct_id, ct.VD_QLTY, wmv.cti_id, wmv.fl_path ");		
		buf.append("\n  FROM CONTENTS_TBL ct ");		
		buf.append("\n 	inner JOIN CONTENTS_INST_TBL wmv ON ct.ct_id = wmv.ct_id ");
		buf.append("\n 	left outer JOIN ( ");
		buf.append("\n 	SELECT * FROM CONTENTS_INST_TBL WHERE cti_fmt = '301'");	
		buf.append("\n 	) h264 ON ct.ct_id = h264.CT_ID ");	
		buf.append("\n 	 WHERE wmv.cti_fmt like '2%' AND LTRIM(RTRIM(VALUE(wmv.fl_path, ''))) <> '' AND wmv.cti_id > 0 ");
		buf.append("\n GROUP BY ct.ct_id, ct.VD_QLTY, wmv.cti_id, wmv.fl_path, h264.cti_id ");	
		buf.append("\n  HAVING COUNT(h264.cti_id) = 0 ");
		buf.append("\n   ) ctid ");
		buf.append("\n   inner JOIN (SELECT ct_id, master_id, del_dd FROM CONTENTS_MAPP_TBL GROUP BY ct_id, master_id, del_dd) MAP ON ctid.ct_id = map.ct_id ");
		buf.append("\n   Inner JOIN METADAT_MST_TBL mst ON map.MASTER_ID = mst.MASTER_ID ");
		buf.append("\n    WHERE value(mst.DEL_DD, '') = '' AND VALUE(map.DEL_DD, '') = '' AND mst.COCD = 'S' ");
		buf.append("\n    UNION ALL ");
		buf.append("\n   SELECT DISTINCT ");
		buf.append("\n   ct.ct_id, ct.vd_qlty, cti.cti_id, cti.fl_path, cti.cti_id||'.wmv' wrk_file_nm ");
		buf.append("\n   FROM TC_JOB_TBL job ");
		buf.append("\n   inner JOIN CONTENTS_TBL ct ON job.ct_id = ct.ct_id ");
		buf.append("\n   inner JOIN (SELECT * FROM CONTENTS_INST_TBL WHERE cti_fmt = '201') cti ON ct.ct_id = cti.ct_id  ");
		buf.append("\n   inner JOIN (SELECT ct_id, master_id, del_dd FROM CONTENTS_MAPP_TBL GROUP BY ct_id, master_id, del_dd) MAP ON ct.ct_id = map.ct_id ");
		buf.append("\n   inner JOIN METADAT_MST_TBL mst ON map.MASTER_ID = mst.MASTER_ID ");
		buf.append("\n   inner JOIN CART_CONT_TBL cart ON job.ct_id = cart.ct_id ");
		buf.append("\n    inner JOIN DOWN_CART_TBL down ON cart.CART_NO = down.CART_NO ");
		buf.append("\n   WHERE job.REQ_CD = 'LRCT' AND job.TC_TYPE = '001' AND job.COCD = 'S' AND SUBSTR(job.REG_DT,1,8) >= '20120701' AND SUBSTR(CART.REG_DT,1,8) >= '20120701' ");
		buf.append("\n   AND value(mst.DEL_DD, '') = '' AND VALUE(map.DEL_DD, '') = '' AND down.DOWN_GUBUN = '006' ");
		buf.append("\n   ) a ");

		String count = (StringUtils.isEmpty(wmvH264DO.getGetcount()) || wmvH264DO.getGetcount().equals("0")) ? "1" :wmvH264DO.getGetcount();
		buf.append("\n 	fetch first "+wmvH264DO.getGetcount()+" rows only  ");
		buf.append("\n with ur ");*/
		//미디어넷 변환쿼리
		/* buf.append("\n    select  INST.CTI_ID  ");
	    buf.append("\n   , INST.FL_PATH  ");
	    buf.append("\n   , INST.ct_id  ");
	    buf.append("\n   , INST.WRK_FILE_NM  ");
	    buf.append("\n   , CON.VD_QLTY ");
	    buf.append("\n   from contents_inst_Tbl INST  ");
	    buf.append("\n   INNER JOIN (SELECT CT_ID, MASTER_ID ,DEL_DD FROM CONTENTS_MAPP_TBL GROUP BY CT_ID, MASTER_ID ,DEL_DD) MAP ON INST.CT_ID = MAP.CT_ID ");
	    buf.append("\n   INNER JOIN CONTENTS_TBL CON ON INST.CT_ID = CON.CT_ID  ");
	    buf.append("\n   INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID  ");
	    buf.append("\n   where mst.COCD='M' AND VALUE(MST.DEL_DD,'')='' AND VALUE(MAP.DEL_DD,'')='' and value(INST.H264_dt,'')='' ");
	    buf.append("\n   AND INST.CTI_FMT LIKE '2%' AND SUBSTR(INST.REG_DT,1,8)>='20120630' AND SUBSTr(INST.REG_DT,1,8)<='20120731'  ");
	    buf.append("\n   ORDER BY INST.CTI_ID asc  ");

		String count = (StringUtils.isEmpty(wmvH264DO.getGetcount()) || wmvH264DO.getGetcount().equals("0")) ? "1" :wmvH264DO.getGetcount();
		buf.append("\n 	fetch first "+wmvH264DO.getGetcount()+" rows only  ");
		buf.append("\n with ur ");
		 */

		buf.append("\n    select  INST.CTI_ID  ");
		buf.append("\n   , INST.FL_PATH  ");
		buf.append("\n   , INST.ct_id  ");
		buf.append("\n   , INST.WRK_FILE_NM  ");
		buf.append("\n   , CON.VD_QLTY ");
		buf.append("\n   from contents_inst_Tbl INST  ");
		buf.append("\n   INNER JOIN (SELECT CT_ID, MASTER_ID ,DEL_DD FROM CONTENTS_MAPP_TBL GROUP BY CT_ID, MASTER_ID ,DEL_DD) MAP ON INST.CT_ID = MAP.CT_ID ");
		buf.append("\n   INNER JOIN CONTENTS_TBL CON ON INST.CT_ID = CON.CT_ID  ");
		buf.append("\n   INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID  ");
		buf.append("\n   	where INST.CTI_FMT like '2%' and value(INST.H264_dt,'')='99991231' ");
		buf.append("\n   AND MST.DEL_DD='' AND MAP.DEL_DD='' AND INST.WRK_FILE_NM<>'' AND MST.COCD='S'  ");
		buf.append("\n   ORDER BY INST.CTI_ID asc  ");

		/* String query = "";
	    try {
	    	query = FileUtils.readFileToString(new File("/jeus/mc_qry.txt"), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		buf.append(query);*/
		String count = (StringUtils.isEmpty(wmvH264DO.getGetcount()) || wmvH264DO.getGetcount().equals("0")) ? "1" :wmvH264DO.getGetcount();
		buf.append("\n 	fetch first "+wmvH264DO.getGetcount()+" rows only  ");
		buf.append("\n with ur ");
		return buf.toString();	
	}



	/**
	 * 마스터명 영상id, 영상명을 조회한다
	 * @param CartItemDO                                                                                                 
	 * @throws DASException
	 */
	public static final String selectGroupForMasterForRelation(long master_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select DISTINCT");
		buf.append("\n MAP.ct_id ");
		buf.append("\n ,VALUE(CODE.DESC,'') AS CT_NM ");
		buf.append("\n from (SELECT CT_ID,MASTER_ID FROM contents_mapp_tbl GROUP BY CT_ID,MASTER_ID) MAP  ");
		buf.append("\n INNER JOIN CONTENTS_TBL CON ON CON.CT_ID = MAP.CT_ID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD='A002' AND CODE.SCL_CD= CON.CT_TYP ");
		buf.append("\n where MAP.master_id ="+master_id);
		//buf.append("\n group by MAP.ct_id ");

		return buf.toString();	
	}



	/**
	 * 관련영상 마스터별 영상id, 영상명을 조회한다
	 * @param CartItemDO                                                                                                 
	 * @throws DASException
	 */
	public static final String selectGroupForMaster(long master_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n MAP.ct_id ");
		buf.append("\n ,VALUE(CODE.DESC,'') AS CT_NM ");
		buf.append("\n ,value(rel.child_master_id,0) as rel_master_id  ");

		buf.append("\n from (SELECT CT_ID,MASTER_ID FROM contents_mapp_tbl GROUP BY CT_ID,MASTER_ID) MAP  ");
		buf.append("\n INNER JOIN CONTENTS_TBL CON ON CON.CT_ID = MAP.CT_ID ");
		buf.append("\n left outer join  relation_master rel ON parent_master_id = "+master_id);
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD='A002' AND CODE.SCL_CD= CON.CT_TYP ");
		buf.append("\n where MAP.master_id ="+master_id);
		buf.append("\n 	order by code.rmk_2 asc ");

		return buf.toString();	
	}




	/**
	 * 다운로드 현황을 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectDowninfo(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n VALUE(CDOWN.NUM,0) AS NUM ");
		buf.append("\n ,DOWN.DOWN_SUBJ ");
		buf.append("\n ,VALUE(DOWN_GU.DESC,'') AS DOWN_GUBUN  ");
		buf.append("\n ,case when    cart.down_stat in ('007','009')  then '100'      ");
		buf.append("\n   WHEN cart.down_stat in ('001','002','004','005') OR value(cdown.job_Status,'W')='E' THEN '0'     ");
		buf.append("\n else VALUE(CDOWN.PROGRESS,'0') end AS DOWN_PROGRESS  ");
		buf.append("\n ,case when cart.down_stat in ('001','002','004','005')      THEN '승인대기중'   ");
		buf.append("\n  when value(cdown.job_Status,'W')='W'  THEN '대기중'   ");
		buf.append("\n  when cart.down_stat='006'  or cdown.job_Status='I'  THEN '진행중'   ");
		buf.append("\n  when  cdown.job_Status='R'  THEN CODE.DESC    ");
		buf.append("\n  when cart.down_stat in ('008','010')  or cdown.job_Status='E'  THEN '에러'     ");
		buf.append("\n  when cart.down_stat in ('007','009')   or cdown.job_Status='C' THEN '완료'     ");
		buf.append("\n  WHEN  value(CDOWN.JOB_STATUS,'') <>'' THEN CODE.DESC     ");
		buf.append("\n  ELSE APP.desc END AS JOB_STATUS  ");
		buf.append("\n ,VALUE(CDOWN.PRIORITY,0) AS DOWN_PRIORITY");
		buf.append("\n ,VALUE(ARI.PROGERESS,'0') AS TM_PROGRESS ");
		buf.append("\n ,VALUE(CODE2.DESC,'') AS TM_STATUS ");
		buf.append("\n ,VALUE(ARI.PRIORITY,0) AS TM_PRIORITY");
		buf.append("\n ,cart.cart_no");
		buf.append("\n ,cart.cart_seq");
		buf.append("\n ,value(user.user_nm,'') as user_nm");
		buf.append("\n ,VALUE(CONM.DESC,'') AS CONM ");
		buf.append("\n ,CART.REG_DT ");
		buf.append("\n ,DOWN.STORAGENAME ");
		buf.append("\n ,MST.TITLE ");
		buf.append("\n ,CASE WHEN MST.CTGR_L_CD='100' THEN MST.FM_DT ");
		buf.append("\n   WHEN MST.CTGR_L_CD='200' THEN MST.BRD_DD ");
		buf.append("\n  ELSE '' END AS BRD_DD ");
		buf.append("\n ,CART.SOM");
		buf.append("\n ,CART.EOM");
		buf.append("\n ,VALUE(ANNOT.DESC,'') AS RIST_CLF_NM ");
		buf.append("\n ,VALUE(user2.USER_NM,'') AS APPROVE_NM ");
		buf.append("\n ,CART.MEDIA_ID ");
		buf.append("\n ,CART.CTI_ID ");
		buf.append("\n ,CART.CT_ID ");
		buf.append("\n ,value(channel.desc,'') as chennel_nm ");
		buf.append("\n ,VALUE(EQ.DAS_EQ_USE_IP,'') AS TM_IP ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY cart.REG_DT desc) AS rownum  ");
		buf.append("\n FROM DOWN_CART_TBL DOWN ");
		buf.append("\n INNER JOIN CART_CONT_tBL CART ON DOWN.CART_NO = CART.CART_NO ");
		buf.append("\n INNER JOIN (SELECT MASTER_ID,CT_ID FROM CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID) MAP ON MAP.CT_ID = CART.CT_ID  ");
		buf.append("\n INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID ");
		//buf.append("\n INNER JOIN contents_inst_tbl inst on inst.cti_id = cart.CTI_ID ");
		buf.append("\n LEFT OUTER JOIN CONTENTS_DOWN_TBL CDOWN ON CDOWN.CART_NO = CART.CART_NO AND CDOWN.CART_SEQ= CART.CART_SEQ ");
		buf.append("\n LEFT OUTER JOIN ARIEL_INFO_TBL ARI ON ARI.CART_NO = CART.CART_NO AND ARI.CART_SEQ =  CART.CART_SEQ ");
		buf.append("\n LEFT OUTER JOIN DAS_EQUIPMENT_TBL EQ ON EQ.DAS_EQ_ID = ARI.EQ_ID and eq.use_yn='Y' ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD ='P062' AND CDOWN.JOB_STATUS= CODE.SCL_CD ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD ='P062' AND ARI.STATUS= CODE2.SCL_CD ");
		buf.append("\n LEFT OUTER JOIN user_info_tbl user ON user.sbs_user_id = cart.regrid ");
		buf.append("\n LEFT OUTER JOIN user_info_tbl user2 ON user2.sbs_user_id = cart.APPROVEID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL DOWN_GU ON DOWN_GU.CLF_CD='A051' AND DOWN_GU.SCL_CD = DOWN.DOWN_GUBUN ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CONM ON CONM.CLF_CD='P058' AND CONM.SCL_CD = CART.COCD ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL ANNOT ON ANNOT.CLF_CD='P018' AND ANNOT.SCL_CD=CART.RIST_CLF_CD ");
		buf.append("\n  LEFT OUTER JOIN CODE_TBL APP ON APP.CLF_CD ='P061' AND CART.DOWN_STAT=APP.SCL_CD");
		buf.append("\n  LEFT OUTER JOIN CODE_TBL channel ON channel.CLF_CD ='P065' AND channel.scl_cd= mst.chennel_cd");
		buf.append("\n  where 1=1 AND DOWN.DOWN_GUBUN<>'006'");
		buf.append("\n  AND cart.down_stat != '001'");

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and CART.REGRID like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		//buf.append("\n ORDER BY ariel.TASK_ID desc, ARIEL.PRIORITY  ");

		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) <=  '"+monitoringDO.getEnd_serach_dd()+"' ");
		}


		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  MST.TITLE LIKE  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(!monitoringDO.getStatus().equals("")){

			if(monitoringDO.getStatus().equals("W")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"' or cart.down_stat in ('001','002','004','005'))  ");
			} else if(monitoringDO.getStatus().equals("I")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"'  )  ");
			}else if(monitoringDO.getStatus().equals("E")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"'  or cart.down_stat in ('008','010'))  ");
			}else if(monitoringDO.getStatus().equals("C")){
				buf.append("\n  and ((cdown.job_status='"+monitoringDO.getStatus()+"' and ARI.STATUS='"+monitoringDO.getStatus()+"')  and  cart.down_stat in ('007','009','003') )  ");
			}
		}
		buf.append("\n order by cart.reg_dt desc ");
		buf.append("\n  ) a ");


		buf.append("\n where a.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);


		return buf.toString();	
	}








	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectManualJobinfo(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n  a.title  ");
		buf.append("\n ,a.LOC_STATE  ");
		buf.append("\n  ,a.PROGRESS ");
		buf.append("\n  ,a.COPY_PROGRESS");
		buf.append("\n ,a.COPY_STATUS ");
		buf.append("\n  ,a.BACKUP_PROGRESS  ");
		buf.append("\n  ,a.BACKUP_STATUS ");
		buf.append("\n  ,a.CHANGE_STATE ");
		buf.append("\n  ,a.DOWN_PROGRESS ");
		buf.append("\n  ,a.DOWN_STATE ");
		buf.append("\n  ,a.change_progress ");
		buf.append("\n  ,a.change_state ");
		buf.append("\n  ,a.NUM   ");
		buf.append("\n  ,a.CTI_ID  ");
		buf.append("\n  ,a.REQ_DT  ");
		buf.append("\n  ,a.REQ_NM  ");
		buf.append("\n  ,a.tm_ip  ");
		buf.append("\n  ,a.cocd");
		buf.append("\n  ,a.tm_nm");
		buf.append("\n  ,a.chennel_nm");
		buf.append("\n  ,a.ct_id");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY a.req_dt desc) AS rownum  ");

		buf.append("\n   from (  ");
		buf.append("\n    select distinct ");
		buf.append("\n 	mst.title ");
		buf.append("\n  ,loc.NUM ");
		buf.append("\n ,loc.CTI_ID  ");

		buf.append("\n ,value(CODE2.DESC,'')   AS LOC_STATE ");
		buf.append("\n ,value(LOC.PROGRESS,'0')  PROGRESS ");
		buf.append("\n ,value(LOC.COPY_PROGRESS,'0') as COPY_PROGRESS ");
		buf.append("\n ,value(CODE3.DESC,'')   AS COPY_STATUS ");
		buf.append("\n ,value(LOC.BACKUP_PROGRESS,'0') as BACKUP_PROGRESS ");

		buf.append("\n ,value(CODE4.DESC,'')   AS BACKUP_STATUS ");
		buf.append("\n ,value(LOC.DOWN_PROGRESS,'0') as DOWN_PROGRESS ");
		buf.append("\n ,value(CODE5.DESC,'') as DOWN_STATE ");
		buf.append("\n ,value(LOC.CHANGE_PROGRESS,'0') as CHANGE_PROGRESS ");
		buf.append("\n ,value(CODE6.DESC,'')   AS CHANGE_STATE ");
		buf.append("\n ,hist.req_dt ");
		buf.append("\n ,value(eq.das_eq_use_ip,'') as tm_ip ");
		buf.append("\n ,value(eq.das_eq_nm,'') as tm_nm ");
		buf.append("\n ,value(user.user_nm,'')   AS req_nm ");
		buf.append("\n ,value(CODE7.desc,'') as cocd ");
		buf.append("\n ,value(CODE8.desc,'') as chennel_nm ");
		buf.append("\n ,inst.ct_id ");
		buf.append("\n  from contents_inst_tbl inst ");
		buf.append("\n left outer join contents_loc_tbl loc on inst.CTI_ID = loc.CTI_ID  ");
		buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.CT_ID = inst.CT_ID  ");
		buf.append("\n  inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n  inner join ARCHIVE_HIST_TBL hist on hist.JOB_ID=loc.NUM  ");
		buf.append("\n  left outer join user_info_Tbl user on user.sbs_user_id = hist.req_id ");
		buf.append("\n left outer join das_equipment_tbl eq on eq.DAS_EQ_ID = loc.CHANGE_EQ_ID and eq.use_yn='Y'  ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='P062' AND CODE2.SCL_CD=LOC.JOB_STATUS ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='P062' AND CODE3.SCL_CD=LOC.COPY_STATUS ");
		buf.append("\n left outer join code_tbl code4 on code4.CLF_CD='P062' AND CODE4.SCL_CD=LOC.BACKUP_STATUS ");
		buf.append("\n left outer join code_tbl code5 on code5.CLF_CD='P062' AND CODE5.SCL_CD=LOC.DOWN_STATUS ");
		buf.append("\n left outer join code_tbl code6 on code6.CLF_CD='P062' AND CODE6.SCL_CD=LOC.CHANGE_STATUS ");
		buf.append("\n left outer join code_tbl code7 on code7.CLF_CD='P058' AND CODE7.SCL_CD=mst.COCD ");
		buf.append("\n left outer join code_tbl code8 on code8.CLF_CD='P065' AND CODE8.SCL_CD=mst.CHENNEL_CD ");
		/*  아카이브 히스토리 테이브 생성으로 로직 삭제 2012.7.26
		if(monitoringDO.getGubun().equals("001")){
			buf.append("\n  left outer join user_info_Tbl user on user.sbs_user_id = loc.backup_id ");
		}if(monitoringDO.getGubun().equals("002")){
			buf.append("\n  left outer join user_info_Tbl user on user.sbs_user_id = loc.copy_id ");
		}if(monitoringDO.getGubun().equals("003")){
			buf.append("\n  left outer join user_info_Tbl user on user.sbs_user_id = loc.recorver_id ");
		}*/

		buf.append("\n  where 1=1  ");
		//buf.append("\n  AND MST.ARCH_REG_DD<>''  ");

		buf.append("\n   AND loc.use_yn='Y'  ");

		if(monitoringDO.getGubun().equals("001")){
			buf.append("\n  and hist.GUBUN= '002' ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}if(monitoringDO.getGubun().equals("002")){
			buf.append("\n   and hist.GUBUN='003' ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}if(monitoringDO.getGubun().equals("003")){
			buf.append("\n   and hist.GUBUN= '004'  ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}
		/*	if(monitoringDO.getGubun().equals("001")){//아카이브 히스토리 테이브 생성으로 로직 삭제 2012.7.26
		buf.append("\n   AND loc.backup_id <> ''  ");
		}if(monitoringDO.getGubun().equals("002")){
		buf.append("\n   AND loc.copy_id <> '' ");
		}if(monitoringDO.getGubun().equals("003")){
		buf.append("\n   AND loc.recorver_id <> ''  ");
		}*/
		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}


		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and SUBSTR(hist.req_dt,1,8) >=  '"+monitoringDO.getStart_search_dd()+"' ");
		}


		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and  SUBSTR(hist.req_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}


		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  mst.title like '%"+monitoringDO.getTitle()+"%' ");
		}

		buf.append("\n  )a )B  ");


		buf.append("\n where B.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);


		return buf.toString();	
	}






	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String countManualMonitoring(MonitoringDO monitoringDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");

		buf.append("\n from contents_inst_tbl inst ");
		buf.append("\n left outer join contents_loc_tbl loc on inst.CTI_ID = loc.CTI_ID   ");
		buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.CT_ID = inst.CT_ID  ");
		buf.append("\n inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID ");
		buf.append("\n  inner join ARCHIVE_HIST_TBL hist on hist.JOB_ID=loc.NUM  ");
		buf.append("\n  left outer join user_info_Tbl user on user.sbs_user_id = hist.req_id ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='P062' AND CODE2.SCL_CD=LOC.JOB_STATUS ");
		buf.append("\n left outer join code_tbl code3 on code3.CLF_CD='P062' AND CODE3.SCL_CD=LOC.COPY_STATUS ");
		buf.append("\n left outer join code_tbl code4 on code4.CLF_CD='P062' AND CODE4.SCL_CD=LOC.BACKUP_STATUS ");
		buf.append("\n left outer join code_tbl code5 on code5.CLF_CD='P062' AND CODE5.SCL_CD=LOC.DOWN_STATUS ");
		buf.append("\n left outer join code_tbl code6 on code6.CLF_CD='P062' AND CODE6.SCL_CD=LOC.CHANGE_STATUS ");
		//buf.append("\n left outer join code_tbl code7 on code7.CLF_CD='A002' AND CODE7.SCL_CD=CON.CT_TYP ");
		buf.append("\n where 1=1   ");
		//buf.append("\n  AND MST.ARCH_REG_DD<>''  ");

		buf.append("\n   AND loc.use_yn='Y'  ");

		if(monitoringDO.getGubun().equals("001")){
			buf.append("\n  and hist.GUBUN= '002' ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}if(monitoringDO.getGubun().equals("002")){
			buf.append("\n   and hist.GUBUN='003' ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}if(monitoringDO.getGubun().equals("003")){
			buf.append("\n   and hist.GUBUN= '004'  ");
			if(!monitoringDO.getStatus().equals("")){
				if(monitoringDO.getStatus().equals("W")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("I")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}else if(monitoringDO.getStatus().equals("C")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' AND LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' AND LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' AND LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				} else if(monitoringDO.getStatus().equals("E")){
					buf.append("\n  AND ( LOC.COPY_STATUS='"+monitoringDO.getStatus()+"' OR LOC.DOWN_STATUS='"+monitoringDO.getStatus()+"' OR LOC.CHANGE_STATUS='"+monitoringDO.getStatus()+"' OR LOC.BACKUP_STATUS='"+monitoringDO.getStatus()+"') ");
				}  
			}
		}

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and SUBSTR(hist.req_dt,1,8) >=  '"+monitoringDO.getStart_search_dd()+"' ");
		}


		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and  SUBSTR(hist.req_dt,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}


		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  mst.title like '%"+monitoringDO.getTitle()+"%' ");
		}

		return buf.toString();	
	}




	/**
	 * 다운 현황 총조회 갯수를 구한다
	 * @param MonitoringDO
	 * @return 
	 * @throws DASException
	 */
	public static final String downcount(MonitoringDO monitoringDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n count(*) as cnt ");

		buf.append("\n FROM DOWN_CART_TBL DOWN ");
		buf.append("\n INNER JOIN CART_CONT_tBL CART ON DOWN.CART_NO = CART.CART_NO ");
		buf.append("\n INNER JOIN (SELECT MASTER_ID,CT_ID FROM CONTENTS_MAPP_TBL GROUP BY MASTER_ID,CT_ID) MAP ON MAP.CT_ID = CART.CT_ID  ");
		buf.append("\n INNER JOIN METADAT_MST_tBL MST ON MST.MASTER_ID = MAP.MASTER_ID ");
		buf.append("\n LEFT OUTER JOIN CONTENTS_DOWN_TBL CDOWN ON CDOWN.CART_NO = CART.CART_NO AND CDOWN.CART_SEQ= CART.CART_SEQ ");
		buf.append("\n LEFT OUTER JOIN ARIEL_INFO_TBL ARI ON ARI.CART_NO = CDOWN.CART_NO AND ARI.CART_SEQ =  CDOWN.CART_SEQ ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD ='P062' AND CDOWN.JOB_STATUS= CODE.SCL_CD ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE2 ON CODE2.CLF_CD ='P062' AND ARI.STATUS= CODE2.SCL_CD ");
		buf.append("\n LEFT OUTER JOIN user_info_tbl user ON user.sbs_user_id = cart.regrid ");
		buf.append("\n LEFT OUTER JOIN user_info_tbl user2 ON user2.sbs_user_id = cart.APPROVEID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL DOWN_GU ON DOWN_GU.CLF_CD='A051' AND DOWN_GU.SCL_CD = DOWN.DOWN_GUBUN ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CONM ON CONM.CLF_CD='P058' AND CONM.SCL_CD = CART.COCD ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL ANNOT ON ANNOT.CLF_CD='P018' AND ANNOT.SCL_CD=CART.RIST_CLF_CD ");

		buf.append("\n  where 1=1 ");
		buf.append("\n   AND cart.down_stat != '001'");
		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and CART.REGRID like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}
		//buf.append("\n ORDER BY ariel.TASK_ID desc, ARIEL.PRIORITY  ");

		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) >= '"+monitoringDO.getStart_search_dd()+"' ");
		}		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and substr(cart.reg_dt,1,8) <=  '"+monitoringDO.getEnd_serach_dd()+"' ");
		}


		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  MST.TITLE LIKE  '%"+monitoringDO.getTitle()+"%' ");
		}
		if(!monitoringDO.getStatus().equals("")){

			if(monitoringDO.getStatus().equals("W")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"' or cart.down_stat in ('001','002','004','005'))  ");
			} else if(monitoringDO.getStatus().equals("I")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"'  )  ");
			}else if(monitoringDO.getStatus().equals("E")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"'  or cart.down_stat in ('008','010'))  ");
			}else if(monitoringDO.getStatus().equals("C")){
				buf.append("\n  and (cdown.job_status='"+monitoringDO.getStatus()+"' OR ARI.STATUS='"+monitoringDO.getStatus()+"'  or cart.down_stat in ('007','009','003') )  ");
			}
		}
		return buf.toString();	
	}






	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * @param manualArchiveD  
	 */
	public static final String selectArchiveRequestInfo(WorkStatusConditionDO dO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from ( ");
		buf.append("\n select ");
		buf.append("\n loc.CTI_ID ");
		buf.append("\n ,loc.JOB_STATUS ");
		buf.append("\n ,loc.PROGRESS ");
		buf.append("\n ,mst.TITLE ");
		buf.append("\n ,mst.REG_DT  ");
		buf.append("\n ,case when mst.CTGR_L_CD='100' then mst.FM_DT ");
		buf.append("\n when mst.CTGR_L_CD='200' then mst.BRD_DD ");
		buf.append("\n else '' end as brd_dd ");
		buf.append("\n ,CODE.DESC ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY loc.cti_id) AS rownum  ");
		buf.append("\n from contents_loc_tbl loc ");
		buf.append("\n inner join contents_inst_Tbl inst on inst.cti_id = loc.cti_id ");
		buf.append("\n inner join contents_tbl con on con.ct_id = inst.ct_id ");
		buf.append("\n inner join (select ct_id,master_id from contents_mapp_Tbl group by ct_id,master_id) map on map.ct_id = con.CT_ID ");
		buf.append("\n inner join metadat_mst_Tbl mst on mst.master_id = map.master_id ");
		buf.append("\n left outer join code_tbl code on code.SCL_CD = mst.CTGR_L_CD and code.CLF_CD='P002' ");
		buf.append("\n where 1=1 ");
		buf.append("\n and   mst.REGRID='"+dO.getReq_id()+"'");


		if(!dO.getStart_req_dt().equals("")){
			buf.append("\n and substr(mst.reg_dt,1,8) >='"+dO.getStart_req_dt()+"'");
			buf.append("\n and substr(mst.reg_dt,1,8) <='"+dO.getEnd_req_dt()+"'");
		}


		buf.append("\n ) a ");
		buf.append("\n where a.rownum between " + dO.getStart_page());
		buf.append("\n  ");

		int end_page=(int) (dO.getStart_page()+99);
		buf.append("\n and " + end_page);

		return buf.toString();	
	}



	/**
	 * 수동 아카이브 총갯수 조회한다
	 * @param manualArchiveD  
	 */
	public static final String countArchiveRequestInfo(WorkStatusConditionDO dO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select count(*) as cnt ");

		buf.append("\n from contents_loc_tbl loc ");
		buf.append("\n inner join contents_inst_Tbl inst on inst.cti_id = loc.cti_id ");
		buf.append("\n inner join contents_tbl con on con.ct_id = inst.ct_id ");
		buf.append("\n inner join (select ct_id,master_id from contents_mapp_Tbl group by ct_id,master_id) map on map.ct_id = con.CT_ID ");
		buf.append("\n inner join metadat_mst_Tbl mst on mst.master_id = map.master_id ");
		buf.append("\n left outer join code_tbl code on code.SCL_CD = mst.CTGR_L_CD and code.CLF_CD='P002' ");
		buf.append("\n where 1=1 ");
		buf.append("\n and   mst.REGRID='"+dO.getReq_id()+"'");


		if(!dO.getStart_req_dt().equals("")){
			buf.append("\n and substr(mst.reg_dt,1,8) >='"+dO.getStart_req_dt()+"'");
			buf.append("\n and substr(mst.reg_dt,1,8) <='"+dO.getEnd_req_dt()+"'");
		}
		return buf.toString();	
	}




	/**
	 * 다운로드 현황을 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectDowninfoForIfCms(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n down.DOWN_SUBJ ");
		buf.append("\n ,cart.REG_DT ");
		buf.append("\n ,value(code.DESC,'') as dtl_status ");
		buf.append("\n ,value(cdown.PROGRESS,'') as dtl_progress  ");
		buf.append("\n ,value(code2.DESC,'') as tm_status ");
		buf.append("\n ,value(ari.PROGERESS,'') as tm_progress ");
		buf.append("\n ,down.CART_NO ");
		buf.append("\n from down_cart_tbl down ");
		buf.append("\n inner join cart_cont_tbl cart on cart.cart_no = down.CART_NO ");
		buf.append("\n left outer join contents_down_tbl cdown on cdown.CART_NO = cart.CART_NO and cdown.CART_NO = cart.CART_SEQ ");
		buf.append("\n left outer join ARIEL_INFO_TBL ari on  ari.CART_NO = cart.CART_NO and ari.CART_SEQ = cart.cart_seq ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P062' and code.SCL_CD = cdown.JOB_STATUS ");
		buf.append("\n left outer join code_tbl code2 on code2.CLF_CD='P062' and code2.SCL_CD = ari.STATUS ");
		buf.append("\n where cart.CART_NO = "+monitoringDO.getCart_no()+" ");



		return buf.toString();	
	}



	/**
	 * ifcms  xml 생성 쿼리
	 * @param num    contents_down_tbl의 key
	 * 		 */
	public static final String selectInfoForDownXmlInIfCms(int num)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select    ");
		buf.append("\n 	value(mst.TITLE,'') as title");
		//xml 특이사항 빠진 부분 추가
		buf.append("\n 	,value(mst.SPC_INFO,'') as special_info ");
		buf.append("\n 	,value(mst.SUB_TTL,'') as title_sub ");
		buf.append("\n 	,value(mst.PDS_CMS_PGM_ID,'') as program_id ");
		buf.append("\n 	,case   ");
		buf.append("\n 	when mst.PGM_CD <>'' then value(pgm.PGM_NM,'') ");
		buf.append("\n 	else '' end as program_name  ");
		buf.append("\n 	,value(cor.CN_NM,'') as corner_title ");
		buf.append("\n 	,value(cor.CN_INFO,'') as corner_contents ");
		buf.append("\n 	,value(mst.EPIS_NO,0) as program_sequence");
		buf.append("\n 	,value(mst.PRODUCER_NM,'') as creator ");
		buf.append("\n 	,value(mst.DRT_NM,'') as creator_sub ");
		buf.append("\n 	,value(mst.COCD,'') as publisher ");
		buf.append("\n 	,value(mst.chennel_cd,'') as contents_channel ");
		buf.append("\n 	,value(mst.ORG_PRDR_NM,'') as publisher_external ");
		buf.append("\n 	,value(mst.CTGR_L_CD,'') as genre_l ");
		buf.append("\n 	,value(mst.CTGR_M_CD,'') as genre_m ");
		buf.append("\n 	,value(mst.CTGR_S_CD,'') as genre_s ");
		buf.append("\n 	,value(mst.CMR_PLACE,'') as location_shooting");
		buf.append("\n 	,value(mst.KEY_WORDS,'') as keyword ");
		buf.append("\n 	,value(mst.CPRT_TYPE,'') as copyright_type");
		buf.append("\n 	,value(mst.CPRTR_NM,'') as copyright_owner " );
		buf.append("\n 	,value(mst.CPRT_TYPE_DSC,'') as copyright_desc ");
		buf.append("\n 	,value(mst.PRDT_IN_OUTS_CD,'') as production_type ");
		buf.append("\n 	,case when value(annot.ANNOT_CLF_CD,'') =''  then '007' ");
		buf.append("\n 	else annot.ANNOT_CLF_CD end as usegrade ");
		buf.append("\n 	,value(annot.ANNOT_CLF_CONT,'') as usegrade_desc  ");
		buf.append("\n 	,value(mst.MC_NM,'') as name_host ");
		buf.append("\n 	,value(mst.CAST_NM,'') as name_guest ");
		buf.append("\n 	,value(mst.ARTIST,'') as artist ");
		buf.append("\n 	,value(mst.COUNTRY_CD,'') as country ");
		buf.append("\n 	,value(mst.MUSIC_INFO,'') as music_info ");
		buf.append("\n 	,value(cont.MEDIA_ID,'') as media_id ");
		buf.append("\n 	,value(con.VD_QLTY,'') as resolution ");
		buf.append("\n 	,value(con.ASP_RTO_CD,'') as aspectratio ");

		buf.append("\n 	,value(cont.duration,'') as duration");
		buf.append("\n 	,value(inst.RECORD_TYPE_CD,'') as audio_type ");
		buf.append("\n 	,value(mst.COCD,'') as contents_owner ");
		buf.append("\n 	,value(mst.REG_DT,'') as datetime_regist");
		buf.append("\n 	,value(mst.CTGR_L_CD,'') as contents_type");
		buf.append("\n 	,value(cdown.FILENAME,'') as file_name");
		buf.append("\n 	,value(cdown.FILESIZE,'') as file_size");
		buf.append("\n 	,value(cont.SOM,'') as som"  );
		buf.append("\n 	,value(cont.EOM,'') as eom"  );
		buf.append("\n 	,value(inst.VD_HRESOL,'') as vd_hresol"  );
		buf.append("\n 	,value(inst.VD_VRESOL,'') as vd_vresol"  );
		buf.append("\n 	,value(inst.BIT_RT,'') as bit_rate"  );
		buf.append("\n 	,value(inst.AUD_SAMP_FRQ,'') as aud_samp_frq"  );
		buf.append("\n 	,value(inst.AUDIO_BDWT,'') as aud_bandwidth"  );
		buf.append("\n 	,value(inst.FRM_PER_SEC,'') as frame_per_second"  );
		//20121228 최효정과장 오류지적 xml contents_type,contents_class 대칭오류
		buf.append("\n 	,value(con.CT_CLA,'') as contents_class"  );
		buf.append("\n 	,value(con.CT_TYP,'') as broadcast_event_type"  );
		buf.append("\n 	,value(mst.BRD_BGN_HMS,'') as bgn_time_onair"  );
		buf.append("\n 	,value(mst.BRD_END_HMS,'') as end_time_onair"  );
		buf.append("\n 	,value(cont.REGRID,'') as worker_id"  );
		buf.append("\n 	,value(cont.REQ_CONT,'') as download_comment"  );
		buf.append("\n 	,cdown.cart_no"  );
		buf.append("\n 	,inst.cti_id"  );
		buf.append("\n 	,cdown.PATH"  );
		buf.append("\n 	,DOWN.DOWN_GUBUN "  );
		buf.append("\n 	,cont.PHYICAL_TREE "  );
		buf.append("\n 	,value(MST.VIEW_GR_CD,'') as  VIEW_GR_CD "  );
		buf.append("\n 	,down.storagename "  );
		buf.append("\n 	,mst.master_id "  );
		buf.append("\n 	,cont.url "  );
		buf.append("\n 	,cdown.updt_dtm "  );
		buf.append("\n 	,value(transaction_id,'0') as  transaction_id "  );
		buf.append("\n  ,down.down_subj||'_'||value(cont.MEDIA_ID,'') as rename  ");
		buf.append("\n 	,case when value(mst.fm_dt,'') != '' and mst.fm_dt<>'00000000'  and mst.fm_dt is not null and mst.fm_dt <>'' and replace(mst.fm_dt,' ','')<>''  then char(date(insert(insert(mst.FM_DT,5,0,'-'),8,0,'-'))) ");
		buf.append("\n 	else char(date(insert(insert(mst.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	end as datetime_shooting ");
		buf.append("\n 	,case when  value(mst.brd_dd,'') != '' and mst.brd_dd<>'00000000'  and mst.brd_dd is not null and mst.brd_dd <>'' and replace(mst.brd_dd,' ','')<>''  then char(date(insert(insert(mst.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	else char(date(insert(insert(mst.FM_DT,5,0,'-'),8,0,'-')))   ");
		buf.append("\n 	end as datetime_onair ");
		buf.append("\n 	from  down_cart_Tbl down  ");
		buf.append("\n 	inner join CART_CONT_TBL cont on cont.cart_no = down.CART_NO ");
		buf.append("\n 	inner join contents_down_tbl cdown on cdown.CART_NO = cont.CART_NO and cdown.CART_SEQ = cont.CART_SEQ");
		buf.append("\n 	inner join contents_mapp_Tbl map on map.CT_ID = cont.ct_id");
		buf.append("\n 	inner join metadat_mst_Tbl mst on mst.master_id = map.master_id ");
		buf.append("\n 	inner join contents_tbl con on con.ct_id = cont.CT_ID ");
		buf.append("\n  inner join contents_inst_Tbl inst on inst.ct_id = con.CT_ID and inst.cti_fmt like '3%' ");
		buf.append("\n 	inner join corner_tbl cor on cor.CN_ID = map.CN_ID ");
		//buf.append("\n 	left outer join annot_info_tbl annot on annot.MASTER_ID = mst.master_id ");
		//20130820 사용등급코드이외에 주제영상의 정보도 표기하는 현상 수정
		buf.append("\n 	left outer join (SELECT ANNOT_CLF_CD, MASTER_ID,GUBUN,ANNOT_CLF_CONT,CT_ID FROM annot_info_tbl ORDER BY ANNOT_CLF_CD ASC) annot on annot.CT_ID =cont.ct_id and annot.gubun='L'  ");

		buf.append("\n 	left outer join pgm_info_tbl pgm on  pgm.PGM_ID = mst.PGM_ID ");
		buf.append("\n  left outer join user_info_Tbl user on user.SBS_USER_ID = cont.REGRID ");
		buf.append("\n where  ");
		buf.append("\n  cdown.num ="+num+" ");
		buf.append("\n FETCH FIRST 1 ROWS ONLY ");
		return buf.toString();
	}





	/**
	 * ifcms  xml 생성 쿼리 (스토리지 전송용)
	 * @param num    cart_cont_tbl의 key
	 * 		 */
	public static final String selectInfoForDownXmlByStorageIfCms(long cart_no, long cart_seq)
	{ 
		StringBuffer buf = new StringBuffer();
		buf.append("\n select    ");
		buf.append("\n 	value(mst.TITLE,'') as title");
		buf.append("\n 	,value(mst.SUB_TTL,'') as title_sub ");
		//xml 특이사항 빠진 부분 추가
		buf.append("\n 	,value(mst.SPC_INFO,'') as special_info ");
		buf.append("\n 	,value(mst.PDS_CMS_PGM_ID,'') as program_id ");
		buf.append("\n 	,case ");
		buf.append("\n 	when mst.PGM_CD <>'' then value(pgm.PGM_NM,'')  ");
		buf.append("\n 	else '' end as program_name  ");
		buf.append("\n 	,value(cor.CN_NM,'') as corner_title ");
		buf.append("\n 	,value(cor.CN_INFO,'') as corner_contents ");
		buf.append("\n 	,value(mst.EPIS_NO,0) as program_sequence");
		buf.append("\n 	,value(mst.PRODUCER_NM,'') as creator ");
		buf.append("\n 	,value(mst.COCD,'') as publisher ");
		buf.append("\n 	,value(mst.chennel_cd,'') as contents_channel ");
		buf.append("\n 	,value(mst.ORG_PRDR_NM,'') as publisher_external ");
		buf.append("\n 	,value(mst.CTGR_L_CD,'') as genre_l ");
		buf.append("\n 	,value(mst.CTGR_M_CD,'') as genre_m ");
		buf.append("\n 	,value(mst.CTGR_S_CD,'') as genre_s ");
		buf.append("\n 	,value(mst.CMR_PLACE,'') as location_shooting");
		buf.append("\n 	,value(mst.KEY_WORDS,'') as keyword ");
		buf.append("\n 	,value(mst.CPRT_TYPE,'') as copyright_type");
		buf.append("\n 	,value(mst.CPRTR_NM,'') as copyright_owner " );
		buf.append("\n 	,value(mst.CPRT_TYPE_DSC,'') as copyright_desc ");
		buf.append("\n 	,value(mst.PRDT_IN_OUTS_CD,'') as production_type ");
		buf.append("\n 	,case when value(annot.ANNOT_CLF_CD,'') =''  then '007' ");
		buf.append("\n 	else annot.ANNOT_CLF_CD end as usegrade ");
		buf.append("\n 	,value(annot.ANNOT_CLF_CONT,'') as usegrade_desc  ");
		buf.append("\n 	,value(mst.MC_NM,'') as name_host ");
		buf.append("\n 	,value(mst.CAST_NM,'') as name_guest ");
		buf.append("\n 	,value(mst.ARTIST,'') as artist ");
		buf.append("\n 	,value(mst.COUNTRY_CD,'') as country ");
		buf.append("\n 	,value(mst.MUSIC_INFO,'') as music_info ");
		buf.append("\n 	,value(cont.MEDIA_ID,'') as media_id ");
		buf.append("\n 	,value(con.VD_QLTY,'') as resolution ");
		buf.append("\n 	,value(con.ASP_RTO_CD,'') as aspectratio ");

		buf.append("\n 	,value(cont.duration,'') as duration");
		buf.append("\n 	,value(inst.RECORD_TYPE_CD,'') as audio_type ");
		buf.append("\n 	,value(mst.COCD,'') as contents_owner ");
		buf.append("\n 	,value(mst.REG_DT,'') as datetime_regist");
		buf.append("\n 	,value(mst.CTGR_L_CD,'') as contents_type");
		buf.append("\n 	,case when value(inst.ORG_FILE_NM,'')<>'' THEN value(inst.ORG_FILE_NM,'') ");
		buf.append("\n 		ELSE INST.WRK_FILE_NM END as file_name ");
		buf.append("\n 	,value(inst.FL_SZ,0) as file_size");
		buf.append("\n 	,value(cont.SOM,'') as som"  );
		buf.append("\n 	,value(cont.EOM,'') as eom"  );
		buf.append("\n 	,value(inst.VD_HRESOL,'') as vd_hresol"  );
		buf.append("\n 	,value(inst.VD_VRESOL,'') as vd_vresol"  );
		buf.append("\n 	,value(inst.BIT_RT,'') as bit_rate"  );
		buf.append("\n 	,value(inst.AUD_SAMP_FRQ,'') as aud_samp_frq"  );
		buf.append("\n 	,value(inst.AUDIO_BDWT,'') as aud_bandwidth"  );
		buf.append("\n 	,value(inst.FRM_PER_SEC,'') as frame_per_second"  );
		//20130102 최효정과장
		//buf.append("\n 	,value(con.CT_CLA,'') as broadcast_event_type"  );
		//buf.append("\n 	,value(con.CT_TYP,'') as contents_class"  );
		buf.append("\n 	,value(con.CT_CLA,'') as contents_class"  );
		buf.append("\n 	,value(con.CT_TYP,'') as broadcast_event_type"  );
		buf.append("\n 	,value(mst.BRD_BGN_HMS,'') as bgn_time_onair"  );
		buf.append("\n 	,value(mst.BRD_END_HMS,'') as end_time_onair"  );
		buf.append("\n 	,value(cont.REGRID,'') as worker_id"  );
		buf.append("\n 	,value(cont.REQ_CONT,'') as download_comment"  );
		buf.append("\n 	,CONT.cart_no"  );
		buf.append("\n 	,inst.FL_PATH as path"  );
		buf.append("\n 	,DOWN.DOWN_GUBUN "  );
		buf.append("\n 	,cont.PHYICAL_TREE "  );
		buf.append("\n 	,value(MST.VIEW_GR_CD,'') as VIEW_GR_CD "  );
		buf.append("\n 	,down.storagename "  );
		buf.append("\n 	,mst.master_id "  );
		buf.append("\n 	,cont.reg_dt "  );
		buf.append("\n 	,cont.url "  );
		buf.append("\n 	,inst.cti_id "  );
		buf.append("\n 	,value(cont.TRANSACTION_ID,'0') as TRANSACTION_ID"  );
		buf.append("\n  ,down.down_subj||'_'||value(cont.MEDIA_ID,'') as rename  ");
		buf.append("\n 	,case when value(mst.fm_dt,'') != '' and mst.fm_dt<>'00000000' and replace(mst.fm_dt,' ','')<>''  then char(date(insert(insert(mst.FM_DT,5,0,'-'),8,0,'-'))) ");
		buf.append("\n 	else char(date(insert(insert(mst.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	end as datetime_shooting ");
		buf.append("\n 	,case when  value(mst.brd_dd,'') != '' and mst.brd_dd <>'00000000' and replace(mst.brd_dd,' ','')<>''  then char(date(insert(insert(mst.BRD_DD,5,0,'-'),8,0,'-')))  ");
		buf.append("\n 	else char(date(insert(insert(mst.FM_DT,5,0,'-'),8,0,'-')))   ");
		buf.append("\n 	end as datetime_onair ");

		buf.append("\n 	,value(mst.DRT_NM,'') as creator_sub "  );
		buf.append("\n 	,value(mst.CPRT_TYPE,'') as copyright_type "  );
		buf.append("\n 	,value(mst.CPRTR_NM,'') as copyright_owner "  );
		buf.append("\n 	,value(mst.CPRT_TYPE_DSC,'') as copyright_desc"  );
		buf.append("\n 	,value(cont.req_CONT,'') as req_cont "  );
		buf.append("\n 	from  down_cart_Tbl down  ");
		buf.append("\n 	inner join CART_CONT_TBL cont on cont.cart_no = down.CART_NO ");
		buf.append("\n 	inner join contents_mapp_Tbl map on map.CT_ID = cont.ct_id");
		buf.append("\n 	inner join metadat_mst_Tbl mst on mst.master_id = map.master_id ");
		buf.append("\n 	inner join contents_tbl con on con.ct_id = cont.CT_ID ");
		buf.append("\n  inner join contents_inst_Tbl inst on inst.ct_id = con.CT_ID and inst.cti_fmt like '1%' ");
		buf.append("\n 	inner join corner_tbl cor on cor.CN_ID = map.CN_ID ");
		//buf.append("\n 	left outer join annot_info_tbl annot on annot.MASTER_ID = mst.master_id ");
		//20130820 주제영상까지 결과보여주는 현상 수정
		buf.append("\n left outer join (SELECT ANNOT_CLF_CD, MASTER_ID,GUBUN,ANNOT_CLF_CONT,CT_ID FROM annot_info_tbl ORDER BY ANNOT_CLF_CD ASC) annot on annot.CT_ID =cont.ct_id and annot.gubun='L'  ");

		buf.append("\n 	left outer join pgm_info_tbl pgm on  pgm.PGM_ID = mst.PGM_ID ");
		buf.append("\n  left outer join user_info_Tbl user on user.SBS_USER_ID = cont.REGRID ");
		buf.append("\n where  ");
		buf.append("\n  CONT.CART_NO ="+cart_no+" AND CONT.CART_SEQ ="+cart_seq+" ");
		buf.append("\n FETCH FIRST 1 ROWS ONLY ");
		return buf.toString();
	}








	/**
	 * 아카이브 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectJobStatusForArchive(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select * from (");
		buf.append("\n select ");
		buf.append("\n  a.title  ");
		buf.append("\n  ,a.PRIORITY ");
		buf.append("\n ,a.TC_STATE ");
		buf.append("\n ,a.TC_PROGRESS ");
		buf.append("\n ,a.DOWN_STATE ");
		buf.append("\n ,a.down_progress ");
		buf.append("\n ,a.CHANGE_STATE ");
		buf.append("\n ,a.change_progress ");
		buf.append("\n ,a.LOC_STATE ");
		buf.append("\n ,a.PROGRESS ");
		buf.append("\n ,a.NUM ");
		buf.append("\n ,a.CTI_ID ");
		buf.append("\n ,ROW_NUMBER() OVER(ORDER BY a.num) AS rownum  ");
		buf.append("\n  from ( ");
		buf.append("\n   select  ");
		buf.append("\n  mst.title  ");
		buf.append("\n ,loc.NUM ");
		buf.append("\n ,loc.CTI_ID ");
		buf.append("\n  ,loc.PRIORITY ");
		buf.append("\n , CASE WHEN TC.PROGRESS = '0' THEN '대기중' ");
		buf.append("\n    WHEN  TC.PROGRESS > '0' AND TC.PROGRESS <'100' THEN '진행중' ");
		buf.append("\n 	 WHEN TC.PROGRESS = '100' THEN '완료' ");
		buf.append("\n   	ELSE '' END AS TC_STATE ");
		buf.append("\n ,TC.PROGRESS AS TC_PROGRESS ");

		buf.append("\n , CASE WHEN loc.PROGRESS = '0' THEN '대기중' ");
		buf.append("\n    WHEN  loc.PROGRESS > '0' AND loc.PROGRESS <'100' THEN '진행중' ");
		buf.append("\n 	 WHEN loc.PROGRESS = '100' THEN '완료' ");
		buf.append("\n   	ELSE '' END AS LOC_STATE ");
		buf.append("\n ,loc.PROGRESS ");

		buf.append("\n , CASE WHEN loc.DOWN_PROGRESS = '0' THEN '대기중' ");
		buf.append("\n    WHEN  loc.DOWN_PROGRESS > '0' AND loc.DOWN_PROGRESS <'100' THEN '진행중' ");
		buf.append("\n 	 WHEN loc.DOWN_PROGRESS = '100' THEN '완료' ");
		buf.append("\n   	ELSE '' END AS DOWN_STATE ");
		buf.append("\n ,loc.DOWN_PROGRESS AS DOWN_PROGRESS ");

		buf.append("\n , CASE WHEN loc.CHANGE_PROGRESS = '0' THEN '대기중' ");
		buf.append("\n    WHEN  loc.CHANGE_PROGRESS > '0' AND loc.CHANGE_PROGRESS <'100' THEN '진행중' ");
		buf.append("\n 	 WHEN loc.CHANGE_PROGRESS = '100' THEN '완료' ");
		buf.append("\n   	ELSE '' END AS CHANGE_STATE ");
		buf.append("\n ,loc.CHANGE_PROGRESS ");

		buf.append("\n  from contents_inst_tbl inst ");
		buf.append("\n left outer join contents_loc_tbl loc on inst.CTI_ID = loc.CTI_ID  ");
		buf.append("\n inner join (select ct_id,master_id ,del_dd from  contents_mapp_tbl group by ct_id,master_id ,del_dd) map on map.CT_ID = inst.CT_ID  ");
		buf.append("\n  inner join metadat_mst_Tbl mst on mst.MASTER_ID = map.MASTER_ID  ");
		buf.append("\n  left outer join tc_job_tbl tc on tc.CT_ID = inst.CT_ID ");
		buf.append("\n  where 1=1  ");
		buf.append("\n  AND MST.ARCH_REG_DD<>''  ");

		buf.append("\n   AND loc.use_yn='Y'  ");

		/*

		if(!monitoringDO.getReq_id().equals("")){
			buf.append("\n  and user.sbs_user_id like '%"+monitoringDO.getReq_id()+"%' ");
		}


		if(!monitoringDO.getReq_nm().equals("")){
			buf.append("\n  and user.user_nm like '%"+monitoringDO.getReq_nm()+"%' ");
		}

		 */
		if(!monitoringDO.getStart_search_dd().equals("")){
			buf.append("\n  and SUBSTR(LOC.REG_DTM,1,8) >=  '"+monitoringDO.getStart_search_dd()+"' ");
		}


		if(!monitoringDO.getEnd_serach_dd().equals("")){
			buf.append("\n  and  SUBSTR(LOC.REG_DTM,1,8) <= '"+monitoringDO.getEnd_serach_dd()+"' ");
		}

		if(!monitoringDO.getTitle().equals("")){
			buf.append("\n  and  MST.TITLE LIKE  '%"+monitoringDO.getTitle()+"%' ");
		}
		buf.append("\n  )a )B  ");


		buf.append("\n where B.rownum between " + monitoringDO.getStart_page());
		buf.append("\n  ");

		int end_page=monitoringDO.getStart_page()+99;
		buf.append("\n and " + end_page);


		return buf.toString();	
	}




	/**
	 * 아카이브 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectTCstatusForArchive(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n  value(mst.title,'') as title ");
		buf.append("\n ,value(tc.JOB_STATUS,'') as desc ");
		buf.append("\n ,case when value(tc.PROGRESS ,'0') = '' then '0'  else  value(tc.PROGRESS ,'0') end as PROGRESS ");
		buf.append("\n ,tc.reg_dt ");
		buf.append("\n ,value(tc.mod_dt,'') as mod_dt ");
		buf.append("\n from tc_job_tbl tc ");
		buf.append("\n inner join contents_mapp_Tbl map on map.ct_id = TC.ct_id ");
		buf.append("\n inner join metadat_mst_tbl mst on mst.master_id = map.master_id ");

		buf.append("\n where tc.ct_id= "+monitoringDO.getCt_id());


		return buf.toString();	
	}



	/**
	 * 아카이브 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectStatusForArchive(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n case when value(loc.PROGRESS ,'0') = '' then '0'  else  value(loc.PROGRESS ,'0') end as PROGRESS ");
		buf.append("\n ,value(loc.job_status,'') as desc ");
		buf.append("\n ,value(mst.title,'') as title ");
		buf.append("\n ,value(loc.reg_dtm,'') as reg_Dt ");
		buf.append("\n ,value(loc.updt_dtm,'') as end_dt ");
		buf.append("\n from contents_inst_tbl inst ");
		buf.append("\n inner join contents_loc_tbl loc on loc.cti_id = inst.cti_id and inst.cti_fmt like '1%'");
		buf.append("\n inner join contents_mapp_Tbl map on map.ct_id = inst.ct_id ");
		buf.append("\n inner join metadat_mst_tbl mst on mst.master_id = map.master_id ");

		buf.append("\n where inst.ct_id= "+monitoringDO.getCt_id());
		buf.append("\n fetch first 1 rows only ");

		return buf.toString();	
	}




	/**
	 * 다운로드 승인 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectApproveStatusForDownload(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select value(mst.title, '' ) as title ");
		buf.append("\n ,value(cart.DOWN_STAT,'' ) as desc  ");
		buf.append("\n ,value(cart.DOWN_STAT, '') down_stat");
		buf.append("\n ,value(cart.REG_DT, '') as reg_dt");
		buf.append("\n ,value(cart.MOD_DT, '')  as mod_dt");
		buf.append("\n ,value(down.storage_yn, '')  as storage_yn");
		buf.append("\n ,value(cart.outsourcing_yn, '')  as outsourcing_yn");
		buf.append("\n ,value(cart.rist_yn, '')  as rist_yn");
		buf.append("\n ,value(cart.APP_CONT, '') as APP_CONT ");

		buf.append("\n from down_cart_tbl down ");
		buf.append("\n inner join cart_cont_tbl cart on cart.CART_NO = down.CART_NO ");
		buf.append("\n inner join contents_mapp_Tbl map on map.ct_id = cart.ct_id ");
		buf.append("\n inner join metadat_mst_tbl mst on mst.master_id = map.master_id ");

		buf.append("\n where cart.CART_NO= "+monitoringDO.getKeyid());
		buf.append("\n fetch first 1 rows only ");


		return buf.toString();	
	}



	/**
	 * 다운로드 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectProgressForDownload(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n value(CDOWN.JOB_STATUS,'W') as desc  ");
		buf.append("\n ,case when value(CDOWN.PROGRESS ,'0') = '' then '0'  else  value(CDOWN.PROGRESS ,'0') end as PROGRESS ");
		buf.append("\n ,value(CDOWN.reg_dtm,'') as reg_dtm  ");
		buf.append("\n ,value(CDOWN.updt_dtm,'') as updt_dtm  ");
		buf.append("\n from down_cart_tbl down ");
		buf.append("\n inner join cart_cont_tbl cart on cart.CART_NO = down.CART_NO ");
		buf.append("\n INNER JOIN CONTENTS_DOWN_TBL CDOWN ON CDOWN.CART_NO = CART.CART_NO AND CDOWN.CART_SEQ = CART.CART_SEQ ");


		buf.append("\n where cart.CART_NO= "+monitoringDO.getKeyid());
		buf.append("\n fetch first 1 rows only ");


		return buf.toString();	
	}


	/**
	 * 다운로드 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectProgressForDownloadInStorage(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n 'C' as desc  ");
		buf.append("\n ,'100' as PROGRESS ");
		buf.append("\n ,value(cart.reg_dt,'') as reg_dtm  ");
		buf.append("\n ,value(cart.mod_dt,'') as updt_dtm  ");
		buf.append("\n from down_cart_tbl down ");
		buf.append("\n inner join cart_cont_tbl cart on cart.CART_NO = down.CART_NO ");		


		buf.append("\n where cart.CART_NO= "+monitoringDO.getKeyid());
		buf.append("\n fetch first 1 rows only ");


		return buf.toString();	
	}




	/**
	 * TM 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectProgressForTm(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n value(ari.STATUS,'') as desc  ");
		buf.append("\n ,case when value(ari.PROGERESS ,'0') = '' then '0'  else  value(ari.PROGERESS ,'0') end as PROGERESS ");
		buf.append("\n ,value(ari.reg_dt,'') as reg_dtm  ");
		buf.append("\n ,value(ari.mod_dt,'') as updt_dtm  ");

		buf.append("\n from  CONTENTS_DOWN_TBL CDOWN ");
		buf.append("\n inner join ARIEL_INFO_TBL ari on ari.CART_NO = cdown.CART_NO and ari.CART_SEQ = cdown.CART_SEQ ");


		buf.append("\n where cdown.CART_NO= "+monitoringDO.getKeyid());
		buf.append("\n order by ari.TASK_ID desc ");

		buf.append("\n fetch first 1 rows only ");
		return buf.toString();	
	}




	/**
	 * TM 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectProgressForTmForStorage(MonitoringDO monitoringDO) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select  ");
		buf.append("\n value(ari.STATUS,'W') as desc  ");
		buf.append("\n ,case when value(ari.PROGERESS ,'0') = '' then '0'  else  value(ari.PROGERESS ,'0') end as PROGERESS ");
		buf.append("\n ,value(ari.reg_dt,'') as reg_dtm  ");
		buf.append("\n ,value(ari.mod_dt,'') as updt_dtm  ");

		buf.append("\n from  cart_cont_tbl  CDOWN ");
		buf.append("\n inner join ARIEL_INFO_TBL ari on ari.CART_NO = cdown.CART_NO and ari.CART_SEQ = cdown.CART_SEQ ");
		buf.append("\n left outer join code_tbl code on code.CLF_CD='P062' and code.SCL_CD =ari.STATUS ");


		buf.append("\n where cdown.CART_NO= "+monitoringDO.getKeyid());
		buf.append("\n order by ari.TASK_ID desc ");

		buf.append("\n fetch first 1 rows only ");
		return buf.toString();	
	}







	/**
	 * 관련영상 마스터별 영상id, 영상명을 조회한다
	 * @param CartItemDO                                                                                                 
	 * @throws DASException
	 */
	public static final String selectGroupForMasterForClient(long master_id) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select DISTINCT");
		buf.append("\n value(LOC.NUM,0) AS KEY ");
		buf.append("\n ,value(LOC.CTI_ID,0) AS  cti_id ");
		buf.append("\n ,VALUE(CODE.DESC,'') AS CT_NM ");
		buf.append("\n ,CASE WHEN LOC.COPY_STATUS ='C' THEN 'Y' ELSE 'N' END AS COPY_STATUS  ");
		buf.append("\n ,CASE WHEN VALUE(LOC.CYN,'N') ='Y' THEN 'Y' ELSE 'N' END AS OLD_COPY_STATUS  ");
		buf.append("\n ,CASE WHEN LOC.BACKUP_STATUS ='C' THEN 'Y' ELSE 'N' END AS BACKUP_STATUS  ");
		buf.append("\n ,CASE WHEN LOC.JOB_STATUS ='C' THEN 'Y' ELSE 'N' END AS ARCHIVE_STATUS  ");
		buf.append("\n ,INST.FL_SZ ");
		buf.append("\n ,CON.CT_LENG ");
		buf.append("\n from (SELECT CT_ID,MASTER_ID FROM contents_mapp_tbl GROUP BY CT_ID,MASTER_ID) MAP  ");
		buf.append("\n INNER JOIN CONTENTS_TBL CON ON CON.CT_ID = MAP.CT_ID ");
		buf.append("\n INNER JOIN CONTENTS_INST_TBL INST ON CON.CT_ID = INST.CT_ID and inst.cti_fmt like '1%'");
		buf.append("\n LEFT OUTER  JOIN CONTENTS_LOC_TBL LOC ON INST.CTI_ID = LOC.CTI_ID ");
		buf.append("\n LEFT OUTER JOIN CODE_TBL CODE ON CODE.CLF_CD='A002' AND CODE.SCL_CD= CON.CT_TYP ");
		buf.append("\n where MAP.master_id ="+master_id);
		//buf.append("\n group by MAP.ct_id ");

		return buf.toString();	
	}


	/**
	 * 장비의 id와 최종 수정일 값을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String getLastModDtInfo() 
	{
		StringBuffer buf = new StringBuffer();


		buf.append("\n select EQUIP.DAS_EQ_ID ");
		buf.append("\n ,SUBSTR(EQUIP.MOD_DT,1,12) AS MOD_DT ");
		buf.append("\n from das_equipment_tbl equip ");
		buf.append("\n where equip.use_YN='Y'  ");
		buf.append("\n AND equip.MOD_DT <>''  AND EQUIP.DAS_EQ_CLF_CD <> 'E03' ");


		return buf.toString();	
	}


	/**
	 * 아카이브 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectTimeRistList(TimeRistInfo info) 
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n  rist.SEQ ");
		buf.append("\n ,rist.S_TIME ");
		buf.append("\n ,rist.E_TIME ");
		buf.append("\n ,code.desc as RIST_CLF_NM");
		buf.append("\n ,rist.REG_DT ");
		buf.append("\n ,rist.WEEK ");
		buf.append("\n ,WEEK.DESC AS WEEK_NM ");
		buf.append("\n ,value(rist.PDS_PGM_ID,'') as PDS_PGM_ID ");
		buf.append("\n ,value(pds.program_name,'') as PDS_PGM_NM");
		buf.append("\n FROM TIME_RIST_SET_TBL rist ");
		buf.append("\n inner join code_tbl code on code.CLF_CD ='P018' and code.SCL_CD = rist.RIST_CLF_CD ");
		buf.append("\n inner join code_tbl WEEK on WEEK.CLF_CD ='P067' and WEEK.SCL_CD = rist.WEEK ");
		buf.append("\n LEFT OUTER join (select program_code,program_name from PDS_PGMINFO_TBL group by program_code,program_name) pds on pds.program_code = rist.pds_pgm_id ");
		buf.append("\n where 1=1 ");
		if(!info.getRistClfCd().equals("ALL")){
			if(!StringUtils.isEmpty(info.getRistClfCd())){
				buf.append("\n AND rist.rist_clf_cd = "+info.getRistClfCd());
			}
		}

		if(info.getWeek() != null){
			buf.append("\n AND rist.week = "+info.getWeek());
		}
		buf.append("\n   order by rist.week,rist.s_time ");

		return buf.toString();	
	}

	/**
	 * 아카이브 진행 상태를 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectSchedulerList() 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n ( select ");
		buf.append("\n scheduler_nm ");
		buf.append("\n ,next_run_dt ");
		buf.append("\n ,run_dt ");
		buf.append("\n ,pattern ");
		buf.append("\n ,timestampdiff(16,char(next_run_dt - run_dt))  as TIME_DIFER ");
		buf.append("\n , '1' as interval ");
		buf.append("\n from SCHEDULER_INFO_TBL ");
		buf.append("\n ) union ("); 

		buf.append("\n select  ");
		buf.append("\n  a.trigger_name as scheduler_nm");
		buf.append("\n ,unix_to_time(b.next_fire_time) as NEXT_RUN_DT ");
		buf.append("\n ,unix_to_time(b.prev_fire_time) as RUN_DT ");
		buf.append("\n ,a.cron_expression as pattern");
		buf.append("\n ,timestampdiff(a.piriod, char(unix_to_time(b.next_fire_time)- unix_to_time(b.prev_fire_time))) AS TIME_DIFER ");
		buf.append("\n ,a.interval ");
		buf.append("\n from ");
		buf.append("\n ( select trigger_name,CRON_EXPRESSION, "); 
		buf.append("\n case trigger_name when 'storageCheckTrigger' then 8 when 'highStorageQuotaCheckTrigger' then 4 ");
		buf.append("\n else 16 end as piriod, ");
		buf.append("\n case trigger_name when 'storageCheckTrigger' then 1 when 'highStorageQuotaCheckTrigger' then 2 ");
		buf.append("\n else 1 end as interval ");
		buf.append("\n from qrtz_cron_triggers ");	 
		buf.append("\n  ) a inner join qrtz_triggers b on a.trigger_name = b.trigger_name ");
		buf.append("\n )");
		return buf.toString();	
	}


	/**
	 * masterid 에 종속되어있는 tape_id,tape_item_id를 조회한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static final String selectMetaDat(Long masterId) 
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");

		buf.append("\n tape_id ,");
		buf.append("\n tape_item_id ");
		buf.append("\n from metadat_mst_tbl ");
		buf.append("\n where master_id =" +masterId);

		return buf.toString();	
	}


}

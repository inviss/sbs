package com.app.das.business.dao.statement;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.CodeDO;
/**
 * das관리 중 코드관리에 대한 SQL 쿼리에 대한 정의가 되어 있다.
 * @author ysk523
 *
 */
public class CodeInfoStatement 
{


	/**
	 * 코드정보를 조회한다
	 *
	 */
	public static String selectCodeListQuery(CodeDO codeDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");

		buf.append("\n 	code.CLF_CD, "); 
		buf.append("\n 	code.SCL_CD, "); 
		buf.append("\n 	code.CLF_NM, "); 
		buf.append("\n 	code.DESC, "); 
		buf.append("\n 	code.RMK_1, "); 
		buf.append("\n 	code.RMK_2, "); 
		buf.append("\n 	code.REG_DT, ");
		buf.append("\n 	value(user.user_nm,'') as regrid, ");
		buf.append("\n 	code.MOD_DT, ");
		buf.append("\n 	code.MODRID, ");
		buf.append("\n 	code.USE_YN, ");
		buf.append("\n 	code.GUBUN, ");
		buf.append("\n 	ROW_NUMBER() OVER(order by code.SCL_CD) AS rownum ");

		buf.append("\n from DAS.CODE_TBL code ");
		buf.append("\n left outer join USER_INFO_TBL user on user.SBS_USER_ID = code.REGRID ");
		buf.append("\n where code.CLF_CD = 'P018'	 ");	
		buf.append("\n AND code.GUBUN = '"+codeDO.getGubun()+"'	 ");
		if(!codeDO.getClfNm().equals("")){
			buf.append("\n AND code.CLF_NM  LIKE '%"+codeDO.getClfNm()+"%'	 ");
		}
		//buf.append("\n    and code.USE_YN = '"+DASBusinessConstants.YesNo.YES+"'	 ");	

		return buf.toString();
	}



	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 */
	public static String selectCodeQuery(String codeDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");


		buf.append("\n 	SCL_CD, "); 		
		buf.append("\n 	DESC "); 


		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = '"+codeDO+"'	 ");	

		//buf.append("\n    and USE_YN = 'Y'	 ");	

		return buf.toString();
	}



	/**
	 * 자동아카이브 목록을 조회한다
	 * @author asura
	 *
	 */
	public static String selecAutoArchvieList(AutoArchiveDO condition, String searchFlag)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               
		if(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT.equals(searchFlag ))
		{
			buf.append("\n 	count(1) "); 
		}
		else
		{

			buf.append("\n 	 CODE.CLF_CD, "); 
			buf.append("\n 	CODE.SCL_CD, "); 
			buf.append("\n 	CODE.DESC, "); 
			buf.append("\n 	AUTO.AUTO_YN, ");
			buf.append("\n 	auto.COCD, ");
			buf.append("\n 	CODE1.DESC as conm, ");
			buf.append("\n 	auto.CHENNEL, ");
			buf.append("\n 	CODE2.DESC as chennel_nm, ");
			buf.append("\n 	auto.ARCH_ROUTE, ");
			buf.append("\n 	CASE WHEN AUTO.ARCH_ROUTE LIKE 'O%' THEN 'ON-AIR' ");
			buf.append("\n 	WHEN  AUTO.ARCH_ROUTE LIKE 'P%' THEN '아카이브 요청' ");
			buf.append("\n 	WHEN  AUTO.ARCH_ROUTE LIKE 'D%' THEN '매체변환'  ");
			buf.append("\n  ELSE '' ");
			buf.append("\n END AS ARCH_ROUTE_NM, ");


			buf.append("\n 	ROW_NUMBER() OVER(order by CODE.SCL_CD) AS rownum ");
		}
		buf.append("\n from DAS.CODE_TBL CODE ");	
		buf.append("\n LEFT JOIN AUTO_ARCHVIE_TBL AUTO ON CODE.CLF_CD = AUTO.CLF_CD  ");	
		buf.append("\n  AND CODE.SCL_CD = AUTO.SCL_CD  ");	
		buf.append("\n  LEFT JOIN code_tbl code1 ON CODE1.CLF_CD = 'P058' AND CODE1.SCL_CD= AUTO.COCD  ");	
		buf.append("\n  LEFT JOIN code_tbl code2 ON CODE2.CLF_CD = 'P065' AND CODE2.SCL_CD= AUTO.CHENNEL ");	
		buf.append("\n where CODE.CLF_CD='A001'  ");	
		if(!condition.getScl_cd().equals("")){
			buf.append("\n AND CODE.SCL_CD= '"+ condition.getScl_cd() +"'  ");	
		}

		if(!condition.getCocd().equals("")){
			buf.append("\n AND auto.COCD= '"+ condition.getCocd() +"'  ");	
		}

		if(!condition.getChennel().equals("")){
			buf.append("\n AND auto.CHENNEL= '"+ condition.getChennel() +"'  ");	
		}
		buf.append("\n 	and code.use_yn='Y' ");
		buf.append("\n 	 	order by auto.cocd ,auto.CHENNEL ,AUTO.ARCH_ROUTE DESC, auto.scl_cd asc    ");

		return buf.toString();
	}


	/**
	 * 특정 자동아카이브에 대해 조회한다
	 * @author asura
	 *
	 */
	public static String selecAutoArchvieInfo(String scl_cd)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               

		buf.append("\n 	 CODE.CLF_CD, "); 
		buf.append("\n 	CODE.SCL_CD, "); 
		buf.append("\n 	CODE.DESC, "); 
		buf.append("\n 	AUTO.AUTO_YN, ");
		buf.append("\n 	ROW_NUMBER() OVER(order by CODE.SCL_CD) AS rownum ");

		buf.append("\n from DAS.CODE_TBL CODE ");	
		buf.append("\n LEFT JOIN AUTO_ARCHVIE_TBL AUTO ON CODE.CLF_CD = AUTO.CLF_CD  ");	
		buf.append("\n  AND CODE.SCL_CD = AUTO.SCL_CD  ");	
		buf.append("\n where CODE.CLF_CD='A001'  ");	

		buf.append("\n where CODE.SCL_CD= '"+ scl_cd +"'  ");	

		return buf.toString();
	}



	/**
	 * 장르코드에대해서 조회한다
	 * @author asura
	 *
	 */
	public static String selecJanrList11(CodeDO condition)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");               


		buf.append("\n 	SCL_CD, "); 
		buf.append("\n 	CLF_NM, "); 
		buf.append("\n 	DESC, "); 
		buf.append("\n 	USE_YN, "); 
		buf.append("\n 	ROW_NUMBER() OVER(order by SCL_CD) AS rownum ");

		buf.append("\n from DAS.CODE_TBL  WHERE  ");		
		if( condition.getSearch_Type().equals("001")){
			buf.append("\n    CLF_CD='P002'	 ");
			buf.append("\n    OR CLF_CD='P003'	 ");	
			buf.append("\n    OR CLF_CD='P004'	 ");	
		}else if(condition.getSearch_Type().equals("002")){
			buf.append("\n    CLF_CD='P002'	 ");
		}else if(condition.getSearch_Type().equals("003")){
			buf.append("\n    CLF_CD='P003'	 ");
		}else if(condition.getSearch_Type().equals("004")){
			buf.append("\n    CLF_CD='P004'	 ");
		}


		return buf.toString();
	}

	/*
	public static String selecJanrList(CodeDO condition)
	{
		StringBuffer buf = new StringBuffer();



		buf.append("\n select ");               

			buf.append("\n 	C.SCL_CD  as BIG_CODE_CD "); 
			buf.append("\n 	,C.DESC  as BIG_DESC "); 
			buf.append("\n 	,C.CLF_NM as BIG_CLF_NM "); 
			buf.append("\n 	,C.USE_YN as BIG_USE_YN ");
			buf.append("\n 	,B.SCL_CD   as MID_CODE_CD"); 
			buf.append("\n 	,B.DESC as MID_DESC");
			buf.append("\n 	,B.CLF_NM as MID_CLF_NM"); 
			buf.append("\n 	,B.USE_YN as MID_USE_YN ");
			buf.append("\n  , A.SCL_CD  SML_CODE_CD"); 
			buf.append("\n 	,A.DESC as SML_DESC"); 
			buf.append("\n 	,A.CLF_NM as SML_CLF_NM ");
			buf.append("\n 	,A.USE_YN as SML_USE_YN ");
			buf.append("\n FROM ( ");
			buf.append("\n SELECT   "); 
			buf.append("\n 	SCL_CD "); 
			buf.append("\n 	,DESC "); 
			buf.append("\n 	,CLF_NM "); 
			buf.append("\n 	,USE_YN "); 
			buf.append("\n 	FROM CODE_TBL ");
			buf.append("\n 	WHERE CLF_CD='P004' "); 
			buf.append("\n 	) A,   "); 
			buf.append("\n (  SELECT   "); 
			buf.append("\n 	 SCL_CD "); 
			buf.append("\n 	,DESC ");
			buf.append("\n 	,CLF_NM "); 
			buf.append("\n 	,USE_YN "); 
			buf.append("\n 	FROM CODE_TBL "); 
			buf.append("\n 	WHERE CLF_CD='P003' "); 
			buf.append("\n 	) B, "); 
			buf.append("\n ( SELECT   ");
			buf.append("\n 	 SCL_CD "); 
			buf.append("\n 	,DESC "); 
			buf.append("\n 	,CLF_NM ");
			buf.append("\n 	,USE_YN "); 
			buf.append("\n 	FROM CODE_TBL "); 
			buf.append("\n 	WHERE CLF_CD='P002' ");
			buf.append("\n 	) C "); 
			buf.append("\n 	WHERE SUBSTR(A.SCL_CD,1,2)||'0' = B.SCL_CD  "); 
			buf.append("\n 	AND SUBSTR(A.SCL_CD,1,1)||'00' = C.SCL_CD "); 

			buf.append("\n ORDER BY A.SCL_CD ASC"); 


			//중분류 검색
			buf.append("\n 	UNION ALL ");
			buf.append("\n 	 ,C.SCL_CD  as BIG_CLF_CD");
			buf.append("\n 	 ,C.DESC  as BIG_DESC");
			buf.append("\n 	,C.CLF_NM as BIG_CLF_NM"); 
			buf.append("\n 	,C.USE_YN as BIG_USE_YN ");
			buf.append("\n 	,B.SCL_CD  as MID_CODE_CD"); 
			buf.append("\n 	,B.DESC as MID_DESC "); 
			buf.append("\n 	,B.CLF_NM as MID_CLF_NM"); 
			buf.append("\n 	,B.USE_YN as BIG_USE_YN ");
			buf.append("\n 	,''   ");
			buf.append("\n 	,''   "); 
			buf.append("\n 	,''   "); 
			buf.append("\n 	,''   "); 
			buf.append("\n 	FROM ( "); 
			buf.append("\n 	SELECT   "); 
			buf.append("\n 	SCL_CD ");
			buf.append("\n 	,DESC "); 
			buf.append("\n 	,CLF_NM ");
			buf.append("\n 	,USE_YN "); 
			buf.append("\n 	FROM CODE_TBL "); 
			buf.append("\n 	WHERE CLF_CD='P003' "); 
			buf.append("\n 	) B, ( ");
			buf.append("\n 	SELECT   "); 
			buf.append("\n 	SCL_CD "); 
			buf.append("\n 	,DESC "); 
			buf.append("\n 	,CLF_NM "); 
			buf.append("\n 	,USE_YN "); 
			buf.append("\n FROM CODE_TBL ");
			buf.append("\n 	WHERE CLF_CD='P002' "); 
			buf.append("\n 	) C "); 
			buf.append("\n 	WHERE SUBSTR(B.SCL_CD,1,1)||'00' = C.SCL_CD "); 

			buf.append("\n ORDER BY B.SCL_CD ASC");



			//대분류
			buf.append("\n 	UNION ALL "); 
			buf.append("\n 	SELECT ");
			buf.append("\n 	SCL_CD as SML_CLF_CD"); 
			buf.append("\n 	 ,DESC as SML_DESC"); 
			buf.append("\n 	,CLF_NM as SML_CLF_NM"); 
			buf.append("\n 	,USE_YN as SML_USE_YN "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' ");
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	,'' "); 
			buf.append("\n 	FROM CODE_TBL ");
			buf.append("\n 		WHERE CLF_CD='P002' "); 
			buf.append("\n ORDER BY SCL_CD ASC");
				return buf.toString();

}
	 */

	/**
	 * 장르코드 관리를 조회한다.(다중조회)
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public static String selecJanrList(CodeDO condition)
	{
		StringBuffer buf = new StringBuffer();



		buf.append("\n select ");               

		buf.append("\n  category.CTGR_L_CD,   "); 
		buf.append("\n  CASE   "); 
		buf.append("\n  WHEN category.CTGR_L_CD is null or  category.CTGR_L_CD = '' THEN ''      "); 
		buf.append("\n 	 ELSE (select code.CLF_NM from DAS.CODE_TBL code where code.CLF_CD = 'P002' and code.SCL_CD = category.CTGR_L_CD  )    ");
		buf.append("\n  END      AS CTGR_L_NM,  "); 
		buf.append("\n 	category.CTGR_L_DESC,     ");
		buf.append("\n  category.CTGR_L_USE_YN,     "); 
		buf.append("\n 	CASE 	WHEN category.CTGR_M_CD   is null or  category.CTGR_M_CD = '' THEN ''    ");
		buf.append("\n  	ELSE  category.CTGR_M_CD "); 
		buf.append("\n 	 END      AS CTGR_M_CD, "); 
		buf.append("\n 	 CASE  WHEN category.CTGR_M_CD is null or  category.CTGR_M_CD = '' THEN ''   ");
		buf.append("\n 	 ELSE (select code.CLF_NM from DAS.CODE_TBL code where code.CLF_CD = 'P003' and code.SCL_CD = category.CTGR_M_CD   )  ");
		buf.append("\n  END      AS CTGR_M_NM,   ");
		buf.append("\n  CASE WHEN 	category.CTGR_M_DESC  is null or  category.CTGR_M_CD = '' THEN ''    "); 
		buf.append("\n  ELSE category.CTGR_M_DESC	 "); 
		buf.append("\n   END      AS CTGR_M_DESC,    ");  
		buf.append("\n 		CASE  WHEN 	category.CTGR_M_USE_YN is null or  category.CTGR_M_USE_YN = '' THEN ''   "); 
		buf.append("\n 	 	 ELSE     category.CTGR_M_USE_YN "); 
		buf.append("\n 	 	 END      AS CTGR_M_USE_YN,  "); 
		buf.append("\n 	 CASE   ");
		buf.append("\n 	WHEN category.CTGR_S_CD = 'N' THEN ' '   "); 
		buf.append("\n 	ELSE category.CTGR_S_CD    "); 
		buf.append("\n  END      AS CTGR_S_CD,       "); 
		buf.append("\n 	 CASE  WHEN category.CTGR_S_CD is null or  category.CTGR_S_CD = '' THEN ''   "); 
		buf.append("\n ELSE (select code.CLF_NM from DAS.CODE_TBL code where code.CLF_CD = 'P004' and code.SCL_CD = category.CTGR_S_CD   )  ");
		buf.append("\n 	  END      AS CTGR_S_NM , "); 
		buf.append("\n 	 CASE WHEN category.CTGR_S_DESC  is null or  category.CTGR_S_DESC = '' THEN ''    "); 
		buf.append("\n 	 ELSE category.CTGR_S_DESC "); 
		buf.append("\n 	END AS CTGR_S_DESC,  "); 
		buf.append("\n 	   CASE WHEN category.CTGR_S_USE_YN  is null or  category.CTGR_S_USE_YN = '' THEN ''    "); 
		buf.append("\n ELSE 	category.CTGR_S_USE_YN    ");
		buf.append("\n 	 END AS CTGR_S_USE_YN "); 
		buf.append("\n    from   (   "); 
		buf.append("\n select  ");
		buf.append("\n 	  large.SCL_CD AS CTGR_L_CD,    "); 
		buf.append("\n 	  large.DESC AS CTGR_L_DESC,   "); 
		buf.append("\n 		large.USE_YN AS CTGR_L_USE_YN,      ");
		buf.append("\n 	middle.SCL_CD AS CTGR_M_CD,   "); 
		buf.append("\n 	middle.DESC AS CTGR_M_DESC,   "); 
		buf.append("\n  middle.USE_YN AS CTGR_M_USE_YN,  "); 
		buf.append("\n   CASE   WHEN small.SCL_CD is null or  small.SCL_CD = '' THEN 'N'     "); 
		buf.append("\n 	 ELSE small.SCL_CD   ");
		buf.append("\n    END      AS CTGR_S_CD, "); 
		buf.append("\n 	 small.DESC AS CTGR_S_DESC,    "); 
		buf.append("\n 	 small.USE_YN AS CTGR_S_USE_YN  from (  ");
		buf.append("\n 	 select      ");
		buf.append("\n  SCL_CD ,DESC   ,USE_YN  ");
		buf.append("\n  from DAS.CODE_TBL   where CLF_CD = 'P002'    ) large   "); 
		buf.append("\n  LEFT OUTER JOIN (     "); 
		buf.append("\n   select    "); 
		buf.append("\n   SCL_CD  ,DESC  ,USE_YN     "); 
		buf.append("\n     from DAS.CODE_TBL     "); 
		buf.append("\n   where CLF_CD = 'P003'       "); 
		buf.append("\n 	 ) middle ON substr(middle.SCL_CD, 1, 1) = substr(large.SCL_CD, 1, 1)     "); 
		buf.append("\n  LEFT OUTER JOIN (     ");
		buf.append("\n 	 select       "); 
		buf.append("\n 	 SCL_CD ,DESC,USE_YN    "); 
		buf.append("\n   from DAS.CODE_TBL where CLF_CD = 'P004'    "); 
		buf.append("\n 	  )       small ON substr(middle.SCL_CD, 1, 2) = substr(small.SCL_CD, 1, 2)      "); 
		buf.append("\n 	  group by large.SCL_CD,large.DESC,large.USE_YN, middle.SCL_CD,middle.DESC,middle.USE_YN, small.SCL_CD,small.DESC,small.USE_YN     ");
		buf.append("\n 	) category       "); 

		return buf.toString();
	}













	/**
	 * 코드정보를 조회한다
	 *
	 */
	public static String selectMainKeyList()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n 	SELECT  "); 
		buf.append("\n 	CLF_CD, "); 
		buf.append("\n 	SCL_CD, "); 
		buf.append("\n 	CLF_NM, "); 
		buf.append("\n 	DESC, "); 
		buf.append("\n 	RMK_1 "); 



		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'P018'	 ");	
		buf.append("\n AND GUBUN = 'S'	 ");

		return buf.toString();
	}




	/**
	 * 해당 콘텐츠 구분값으로 자동아카이브 여부 판단  (ex: 'Y') 인값을 구한다
	 * @author asura
	 *
	 */
	public static String selecAutoArchvieList(String ct_cla)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select scl_Cd from AUTO_ARCHVIE_TBL where auto_yn='Y' and scl_cd = ? and cocd=?  with ur  ");               

		return buf.toString();
	}


	/**
	 * 해당 콘텐츠 구분값으로 자동아카이브 여부 판단  (ex: 'Y') 인값을 구한다(계열사별)
	 * @author asura
	 *
	 */
	public static String selecAutoArchvieList(String ct_cla,String cocd,String chennel,String arch_route)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select scl_Cd from AUTO_ARCHVIE_TBL where auto_yn='Y' and scl_cd = ?  and cocd = ? and chennel = ? and arch_route = ? with ur  ");               

		return buf.toString();
	}

	//2012.4.18 다스 확장 추가 함수


	/**
	 * 코드정보를 조회한다
	 *
	 */
	public static String selectArchrouteQuery()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");


		buf.append("\n 	code.SCL_CD, "); 
		buf.append("\n 	code.DESC "); 



		buf.append("\n from DAS.CODE_TBL code ");

		buf.append("\n where code.CLF_CD = 'A052'	 ");	
		buf.append("\n AND code.GUBUN = 'Y'	 ");

		return buf.toString();
	}


	/**
	 * 채널정보를 조회한다
	 *
	 */
	public static String selectChennelListQuery(CodeDO codeDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");


		buf.append("\n 	code.SCL_CD, "); 

		buf.append("\n 	code.DESC "); 

		buf.append("\n from DAS.CODE_TBL code ");

		buf.append("\n where code.CLF_CD = 'P065'	 ");	
		if(!codeDO.getRmk1().equals("")){
			buf.append("\n AND code.gubun = '"+codeDO.getRmk1()+"'	 ");
		}
		if(!codeDO.getRmk2().equals("")){
			buf.append("\n AND code.RMK_2 = '"+codeDO.getRmk2()+"'	 ");
		}
		//buf.append("\n    and code.USE_YN = '"+DASBusinessConstants.YesNo.YES+"'	 ");	

		return buf.toString();
	}


	/**
	 * 채널 구성을 위한 회사 정보를 조회한다
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param codeDO 회사정보
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 */
	public static String getCocdForChennel()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");


		buf.append("\n 	code.SCL_CD, "); 

		buf.append("\n 	code.DESC "); 

		buf.append("\n from DAS.CODE_TBL code ");

		buf.append("\n where code.CLF_CD = 'P058'	 ");	
		buf.append("\n AND code.gubun = code.scl_cd ");

		return buf.toString();
	}





	/**
	 * 코드정보를 조회한다
	 *
	 */
	public static String selectCodeListQuery2(CodeDO codeDO)
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");

		buf.append("\n 	code.CLF_CD, "); 
		buf.append("\n 	code.SCL_CD, "); 
		buf.append("\n 	code.CLF_NM, "); 
		buf.append("\n 	code.DESC, "); 
		buf.append("\n 	code.RMK_1, "); 
		buf.append("\n 	code.RMK_2, "); 
		buf.append("\n 	code.REG_DT, ");
		buf.append("\n 	value(user.user_nm,'') as regrid, ");
		buf.append("\n 	code.MOD_DT, ");
		buf.append("\n 	code.MODRID, ");
		buf.append("\n 	code.USE_YN, ");
		buf.append("\n 	code.GUBUN, ");
		buf.append("\n 	ROW_NUMBER() OVER(order by code.SCL_CD) AS rownum ");

		buf.append("\n from DAS.CODE_TBL code ");
		buf.append("\n left outer join USER_INFO_TBL user on user.SBS_USER_ID = code.REGRID ");
		buf.append("\n where 1=1	 ");	

		if(!codeDO.getClfCd().equals("")){
			buf.append("\n and code.CLF_CD  = '"+codeDO.getClfCd()+"'	 ");
		}
		//buf.append("\n    and code.USE_YN = '"+DASBusinessConstants.YesNo.YES+"'	 ");	

		return buf.toString();
	}




}

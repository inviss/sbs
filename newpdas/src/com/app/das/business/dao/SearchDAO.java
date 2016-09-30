package com.app.das.business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.SearchStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.ReqDownItemDO;
import com.app.das.business.transfer.SearchConditionDO;
import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.business.transfer.TapeItemInfoDO;
import com.app.das.business.transfer.TapeLendingDO;
import com.app.das.business.transfer.TapeLendingItemDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CodeCommon;
import com.app.das.util.DBService;
import com.konantech.search.data.ParameterVO;
import com.konantech.search.util.DCUtil;
import com.konantech.search.util.ExportXML;
/**
 * 통합 검색의 내목록, 테이프대출, 요청 영상 목록에 대한 Databse 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class SearchDAO extends AbstractDAO 
{
	private Logger logger = Logger.getLogger(SearchDAO.class);

	private static SearchDAO instance;

	private static ResourceBundle bundle = ResourceBundle.getBundle("search");

	/**
	 * A private constructor
	 *
	 */
	private SearchDAO() 
	{
	}

	public static synchronized SearchDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new SearchDAO();
		}
		return instance;
	}

	/**
	 * 내목록에 저장한다.
	 * @param myCatalogDO 내목록에 저장할 내용을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public boolean insertMyCatalog(MyCatalogDO myCatalogDO) throws Exception
	{
		if(isThereMySearchItem(myCatalogDO))
			return true;
		else
		{	
			StringBuffer buf = new StringBuffer();
			buf.append("\n insert into DAS.MY_SRCHLIST_TBL( ");
			buf.append("\n 	REQ_USRID, "); 
			buf.append("\n 	SEQ, "); 
			buf.append("\n 	MASTER_ID, "); 
			buf.append("\n 	CN_ID, "); 
			buf.append("\n 	PGM_ID, "); 
			buf.append("\n 	PGM_NM, "); 
			buf.append("\n 	EPN,  ");
			buf.append("\n 	TITLE, "); 
			buf.append("\n 	BRD_DD, "); 
			buf.append("\n 	BRD_LENG, "); 
			buf.append("\n 	CN_LENG, "); 
			buf.append("\n 	ANNOT_CLF_CD, "); 
			buf.append("\n 	CONT,  ");
			buf.append("\n 	RPIMG_KFRM_SEQ, "); 
			buf.append("\n 	GOOD_SC, "); 
			buf.append("\n 	NOT_USE, "); 
			buf.append("\n 	DILBRT, "); 
			buf.append("\n 	CHECK, "); 
			buf.append("\n 	KFRM_PATH, ");
			buf.append("\n 	KFRM_SEQ, ");
			buf.append("\n 	RPIMG_CT_ID, ");
			buf.append("\n 	REG_DT, "); 
			buf.append("\n 	REGRID, "); 
			buf.append("\n 	MOD_DT, "); 
			buf.append("\n 	MODRID, ");
			buf.append("\n 	ASP_RTO_CD, ");
			buf.append("\n 	VD_QLTY, ");
			buf.append("\n 	PILOT_YN, ");
			buf.append("\n 	SUB_TTL, ");
			buf.append("\n 	WEEKDAY, ");
			buf.append("\n 	FINAL_BRD_YN, ");
			buf.append("\n 	SCHD_PGM_NM ");
			buf.append("\n )values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			Connection con = null;
			PreparedStatement stmt = null;
			try 
			{
				con = DBService.getInstance().getConnection();
				//	logger.debug("######insertMyCatalog######## con : " + con);
				stmt = con.prepareStatement(buf.toString());

				//현재 시간을 받아온다.
				String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

				int index = 0;
				int nextSeq = getMyCatalogMaxSeq(myCatalogDO.getReqUsrId()) + 1;

				stmt.setString(++index, myCatalogDO.getRegrId());    
				stmt.setLong(++index, nextSeq);
				stmt.setLong(++index, myCatalogDO.getMasterId());
				stmt.setLong(++index, myCatalogDO.getCnId());
				stmt.setLong(++index, myCatalogDO.getPgmId());
				stmt.setString(++index, myCatalogDO.getPgmNm());       
				stmt.setLong(++index, Long.parseLong(myCatalogDO.getEpn()));
				stmt.setString(++index, myCatalogDO.getTitle());       
				stmt.setString(++index, myCatalogDO.getBrdDd());       
				stmt.setString(++index, myCatalogDO.getBrdLeng());     
				stmt.setString(++index, myCatalogDO.getCnLeng());      
				stmt.setString(++index, myCatalogDO.getAnnotClfCd());  
				stmt.setString(++index, myCatalogDO.getCont());        
				stmt.setLong(++index, myCatalogDO.getRpImg());
				stmt.setString(++index, myCatalogDO.getGoodSc());      
				stmt.setString(++index, myCatalogDO.getNotUse());      
				stmt.setString(++index, myCatalogDO.getDilbrt());      
				stmt.setString(++index, myCatalogDO.getCheck());       
				stmt.setString(++index, myCatalogDO.getKfrmPath());
				stmt.setLong(++index, myCatalogDO.getKfrmSeq());
				stmt.setLong(++index, myCatalogDO.getRpimgCtId());
				stmt.setString(++index, toDateTime);       
				stmt.setString(++index, myCatalogDO.getRegrId());      
				stmt.setString(++index, toDateTime);       
				stmt.setString(++index, myCatalogDO.getRegrId());	
				stmt.setString(++index, myCatalogDO.getAspRtoCd());
				stmt.setString(++index, myCatalogDO.getVdQlty());
				stmt.setString(++index, myCatalogDO.getPilot_yn());
				stmt.setString(++index, myCatalogDO.getSub_ttl());
				stmt.setString(++index, myCatalogDO.getDay());
				stmt.setString(++index, myCatalogDO.getFinal_brd_yn());
				stmt.setString(++index, myCatalogDO.getSchd_pgm_nm());

				stmt.executeUpdate();

			} 
			catch (Exception e) 
			{
				logger.error(buf.toString());


				throw e;
			}
			finally
			{
				release(null, stmt, con);
			}

			return false;
		}

	}

	/**
	 * 내목록에 저장한다.
	 * @param myCatalogDO 내목록에 저장할 내용을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */

	public int[] insertMyCatalog(List myCatalogDO) throws Exception
	{
		/*	if(isThereMySearchItem(myCatalogDO))
			return 1;
		else
		{	*/
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.MY_SRCHLIST_TBL( ");
		buf.append("\n 	REQ_USRID, "); 
		buf.append("\n 	SEQ, "); 
		buf.append("\n 	MASTER_ID, "); 
		buf.append("\n 	CN_ID, "); 
		buf.append("\n 	PGM_ID, "); 
		buf.append("\n 	PGM_NM, "); 
		buf.append("\n 	EPN,  ");
		buf.append("\n 	TITLE, "); 
		buf.append("\n 	BRD_DD, "); 
		buf.append("\n 	BRD_LENG, "); 
		buf.append("\n 	CN_LENG, "); 
		buf.append("\n 	ANNOT_CLF_CD, "); 
		buf.append("\n 	CONT,  ");
		buf.append("\n 	RPIMG_KFRM_SEQ, "); 
		buf.append("\n 	GOOD_SC, "); 
		buf.append("\n 	NOT_USE, "); 
		buf.append("\n 	DILBRT, "); 
		buf.append("\n 	CHECK, "); 
		buf.append("\n 	KFRM_PATH, ");
		buf.append("\n 	KFRM_SEQ, ");
		buf.append("\n 	RPIMG_CT_ID, ");
		buf.append("\n 	REG_DT, "); 
		buf.append("\n 	REGRID, "); 
		buf.append("\n 	MOD_DT, "); 
		buf.append("\n 	MODRID, ");
		buf.append("\n 	ASP_RTO_CD, ");
		buf.append("\n 	VD_QLTY, ");
		buf.append("\n 	PILOT_YN, ");
		buf.append("\n 	SUB_TTL, ");
		buf.append("\n 	WEEKDAY, ");
		buf.append("\n 	FINAL_BRD_YN, ");
		buf.append("\n 	SCHD_PGM_NM ");
		buf.append("\n )values(?, NEXTVAL FOR SEQ_MYSEQ, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMyCatalog######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			int index = 0;

			for(int i=0;i<myCatalogDO.size();i++){
				index = 0;
				MyCatalogDO myCatalog = (MyCatalogDO)myCatalogDO.get(i);

				stmt.setString(++index, myCatalog.getReqUsrId()); //REQ_USRID   



				if(myCatalog.getMasterId()!=0){
					stmt.setLong(++index, myCatalog.getMasterId());//MASTER_ID

				}else {
					stmt.setLong(++index,0);//MASTER_ID	
				}


				if(myCatalog.getCnId()!=0){
					stmt.setLong(++index, myCatalog.getCnId());//CN_ID
				}else {
					stmt.setLong(++index,0);
				}


				if(myCatalog.getPgmId()!=0){
					stmt.setLong(++index, myCatalog.getPgmId());//PGM_ID
				}else {
					stmt.setLong(++index,0);
				}


				if(!myCatalog.getPgmNm().equals("")){
					stmt.setString(++index, myCatalog.getPgmNm());    //  PGM_NM 

				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getEpn().equals("0")){
					stmt.setInt(++index, Integer.parseInt(myCatalog.getEpn()));//EPN
				}else {
					stmt.setInt(++index, 0);
				}


				if(!myCatalog.getTitle().equals("")){
					stmt.setString(++index, myCatalog.getTitle());    //  TITLE 
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getBrdDd().equals("")){
					stmt.setString(++index, myCatalog.getBrdDd());    // BRD_DD  
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getBrdLeng().equals("")){
					stmt.setString(++index, myCatalog.getBrdLeng());     //BRD_LENG
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getCnLeng().equals("")){
					stmt.setString(++index, myCatalog.getCnLeng());      //CN_LENG
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getAnnotClfCd().equals("")){
					stmt.setString(++index, myCatalog.getAnnotClfCd());  //ANNOT_CLF_CD
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getCont().equals("")){

					stmt.setString(++index, myCatalog.getCont());       // CONT
				}else {
					stmt.setString(++index, "");	
				}


				if(myCatalog.getRpImg()!=0){
					stmt.setInt(++index, myCatalog.getRpImg());//RPIMG_KFRM_SEQ
				}else {
					stmt.setInt(++index, 0);
				}


				if(!myCatalog.getGoodSc().equals("")){
					stmt.setString(++index, myCatalog.getGoodSc());    //  GOOD_SC
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getNotUse().equals("")){
					stmt.setString(++index, myCatalog.getNotUse());    //  NOT_USE
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getDilbrt().equals("")){
					//stmt.setString(++index, myCatalog.getDilbrt());  
					stmt.setString(++index, "");//  DILBRT
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getCheck().equals("")){
					stmt.setString(++index, myCatalog.getCheck());     //  CHECK
				}else {
					stmt.setString(++index, "");	
				}



				if(!myCatalog.getKfrmPath().equals("")){
					stmt.setString(++index, myCatalog.getKfrmPath());//KFRM_PATH
				}else {
					stmt.setString(++index, "");	
				}



				if(myCatalog.getKfrmSeq()!=0){
					stmt.setInt(++index, myCatalog.getKfrmSeq());//KFRM_SEQ
				}else {
					stmt.setInt(++index,0);
				}



				if(myCatalog.getRpimgCtId()!=0){
					stmt.setLong(++index, myCatalog.getRpimgCtId());//RPIMG_CT_ID
				}else {
					stmt.setLong(++index, 0);	
				}




				stmt.setString(++index, toDateTime);       //REG_DT



				if(!myCatalog.getReqUsrId().equals("")){
					stmt.setString(++index,  myCatalog.getReqUsrId());   //  REGRID 
				}else {
					stmt.setString(++index, "");	
				}




				stmt.setString(++index, toDateTime);       //MOD_DT




				if(!myCatalog.getRegrId().equals("")){
					stmt.setString(++index,  myCatalog.getRegrId());	//MODRID
				}else {
					stmt.setString(++index, "");	
				}



				if(!myCatalog.getAspRtoCd().equals("")){
					stmt.setString(++index, myCatalog.getAspRtoCd());//ASP_RTO_CD
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getVdQlty().equals("")){
					stmt.setString(++index, myCatalog.getVdQlty());//VD_QLTY
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getPilot_yn().equals("")){
					stmt.setString(++index, myCatalog.getPilot_yn());//PILOT_YN
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getSub_ttl().equals("")){
					stmt.setString(++index, myCatalog.getSub_ttl());//SUB_TTL
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getDay().equals("")){
					stmt.setString(++index, "");	//WEEKDAY
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getFinal_brd_yn().equals("")){
					stmt.setString(++index, myCatalog.getFinal_brd_yn());//FINAL_BRD_YN
				}else {
					stmt.setString(++index, "");	
				}


				if(!myCatalog.getSchd_pgm_nm().equals("")){
					stmt.setString(++index, myCatalog.getSchd_pgm_nm());//SCHD_PGM_NM
				}else {
					stmt.setString(++index, "");	
				}


				stmt.addBatch();




			}
			int[] rInt = null;	
			if(myCatalogDO.size()>0)rInt =stmt.executeBatch();




			con.commit();
			return rInt;
		} 
		catch (Exception e) 
		{

			logger.error(buf.toString());

			throw e;

		} 
		finally
		{
			release(null, stmt, con);
		}


	}
	/**
	 * 코너id와 사용자id로 내목록에 존재하는 item인지 검색
	 * @param myCatalogDO 검색조건이 담겨있는 beans
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isThereMySearchItem(MyCatalogDO myCatalogDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	count(1) ");
		buf.append("\n from DAS.MY_SRCHLIST_TBL  ");
		buf.append("\n where CN_ID = ? and REQ_USRID = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setLong(++index, myCatalogDO.getCnId());
			stmt.setString(++index, myCatalogDO.getReqUsrId());


			rs = stmt.executeQuery();

			rs.next();

			int totalCount = rs.getInt(1);

			if(totalCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 내목록 조회를 한다.
	 * @param searchConditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 내목록 정보를 page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public PageDO selectMyCatalogList(SearchConditionDO searchConditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append("\n select * FROM                                                      										\n");
		buf.append("\n (                                                                  												\n");
		buf.append(SearchStatement.selectMyCatalogListQuery(commonDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append("\n ) AS temp                                                           											\n");
		buf.append("\n where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append("\n WITH UR	 ");

		//Page에 따른 계산을 한다.
		int page = searchConditionDO.getPage();
		if(page == 0)
		{
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyCatalogList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SearchStatement.selectMyCatalogListQuery(commonDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = searchConditionDO.getRowPerPage();
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.MY_CATALOG_ROW_COUNT;
			}
			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				MyCatalogDO item = new MyCatalogDO();
				item.setReqUsrId(   		rs.getString("REQ_USRID"));
				item.setSeq(        			rs.getInt("SEQ"));
				item.setMasterId(   		rs.getInt("MASTER_ID"));
				item.setCnId(       			rs.getInt("CN_ID"));
				item.setPgmId(      		rs.getInt("PGM_ID"));
				item.setPgmNm(      		rs.getString("PGM_NM"));
				item.setEpn(        			rs.getString("EPN"));
				item.setTitle(      			rs.getString("TITLE"));
				item.setBrdDd(      		rs.getString("BRD_DD"));
				item.setBrdLeng(    		rs.getString("BRD_LENG"));
				item.setCnLeng(     		rs.getString("CN_LENG"));
				item.setAnnotClfCd( 		rs.getString("ANNOT_CLF_CD"));
				item.setCont(       			rs.getString("CONT"));
				item.setRpImg(      		rs.getInt("RPIMG_KFRM_SEQ"));
				item.setGoodSc(     		rs.getString("GOOD_SC"));
				item.setNotUse(     		rs.getString("NOT_USE"));
				item.setDilbrt(     			rs.getString("DILBRT"));
				item.setCheck(      		rs.getString("CHECK"));
				item.setKfrmPath(			rs.getString("KFRM_PATH"));
				item.setKfrmSeq(			rs.getInt("KFRM_SEQ"));
				item.setRpimgCtId(		rs.getInt("RPIMG_CT_ID"));
				item.setRegDt(      		rs.getString("REG_DT"));
				item.setRegrId(     		rs.getString("REGRID"));
				item.setModDt(      		rs.getString("MOD_DT"));
				item.setModrId(	    	rs.getString("MODRID"));
				item.setVdQlty(	    	rs.getString("VD_QLTY"));
				item.setAspRtoCd(   		rs.getString("ASP_RTO_CD")); 
				item.setSerialNo(   		rs.getInt("rownum"));
				item.setPilot_yn(			rs.getString("PILOT_YN"));
				item.setSub_ttl(				rs.getString("SUB_TTL"));
				item.setDay(					rs.getString("WEEKDAY"));
				item.setFinal_brd_yn(	rs.getString("FINAL_BRD_YN"));
				item.setSchd_pgm_nm(rs.getString("SCHD_PGM_NM"));

				resultList.add(item);
			}
			int totalPageCount = totalCount / rowPerPage  + (totalCount % rowPerPage != 0 ? 1 : 0);

			//검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			//계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);
			//총 검색 숫자를 넣어준다,
			pageDO.setTotalCount(totalCount);


			return pageDO;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 내목록 조회를 한다.
	 * @param searchConditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 내목록 정보를 page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectMyCatalog(SearchConditionDO searchConditionDO, DASCommonDO commonDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select * FROM                                                      										\n");
		buf.append("\n (                                                                  												\n");
		buf.append(SearchStatement.selectMyCatalogListQuery(commonDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append("\n ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append("\n WITH UR	 ");

		//		//Page에 따른 계산을 한다.
		int page = commonDO.getStartPage();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SearchStatement.selectMyCatalogListQuery(commonDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = 200;
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.NEW_MY_CATALOG_ROW_COUNT;
			}
			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);
			//			
			int index = 0;

			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);


			rs = stmt.executeQuery();


			List resultList = new ArrayList();

			while(rs.next())
			{
				MyCatalogDO item = new MyCatalogDO();
				item.setReqUsrId(   		rs.getString("REQ_USRID"));
				item.setSeq(        			rs.getInt("SEQ"));
				item.setMasterId(   		rs.getInt("MASTER_ID"));
				item.setCnId(       			rs.getInt("CN_ID"));
				item.setPgmId(      		rs.getInt("PGM_ID"));
				item.setPgmNm(      		rs.getString("PGM_NM"));
				String epn = rs.getString("EPN");
				if(epn.trim().equals("0")){
					item.setEpn("");
				}else{
					item.setEpn(        			rs.getString("EPN"));	
				}
				item.setTitle(      			rs.getString("TITLE"));
				item.setBrdDd(      		rs.getString("BRD_DD"));
				item.setBrdLeng(    		rs.getString("BRD_LENG"));
				item.setCnLeng(     		rs.getString("CN_LENG"));
				item.setAnnotClfCd( 		rs.getString("ANNOT_CLF_CD"));
				item.setCont(       			rs.getString("CONT"));
				item.setRpImg(      		rs.getInt("RPIMG_KFRM_SEQ"));
				item.setGoodSc(     		rs.getString("GOOD_SC"));
				item.setNotUse(     		rs.getString("NOT_USE"));
				item.setDilbrt(     			rs.getString("DILBRT"));
				item.setCheck(      		rs.getString("CHECK"));
				item.setKfrmPath(			rs.getString("KFRM_PATH"));
				item.setKfrmSeq(			rs.getInt("KFRM_SEQ"));
				item.setRpimgCtId(		rs.getInt("RPIMG_CT_ID"));
				item.setRegDt(      		rs.getString("REG_DT"));
				item.setRegrId(     		rs.getString("REGRID"));
				item.setModDt(      		rs.getString("MOD_DT"));
				item.setModrId(	    	rs.getString("MODRID"));
				item.setVdQlty(	    	rs.getString("VD_QLTY"));
				item.setAspRtoCd(   		rs.getString("ASP_RTO_CD")); 

				item.setPilot_yn(			rs.getString("PILOT_YN"));
				item.setSub_ttl(				rs.getString("SUB_TTL"));
				item.setDay(					rs.getString("WEEKDAY"));
				item.setFinal_brd_yn(	rs.getString("FINAL_BRD_YN"));
				item.setSchd_pgm_nm(rs.getString("SCHD_PGM_NM"));
				item.setGubun(rs.getString("desc"));
				item.setArch_reg_dd(rs.getString("arch_reg_dd"));
				item.setTotalCount(totalCount);
				resultList.add(item);
			}
			//			int totalPageCount = totalCount / rowPerPage  + (totalCount % rowPerPage != 0 ? 1 : 0);
			//			
			//			//검색된 List를 셋팅한다.
			//			pageDO.setPageItems(resultList);
			//			//계산된 총 Page 수를 셋팅한다.
			//			pageDO.setTotalPageCount(totalPageCount);
			//			//총 검색 숫자를 넣어준다,
			//			pageDO.setTotalCount(totalCount);


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * 내목록 조회를 한다.
	 * @param commonDO 조회조건을 포함하고 있는 DataObject
	 * @return List 내목록 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectMyCatalogs(MyCatalogDO commonDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(SearchStatement.selectMyCatalogListQuerys(commonDO, DASBusinessConstants.PageQueryFlag.NORMAL));


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyCatalogs######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();


			List resultList = new ArrayList();

			while(rs.next())
			{
				MyCatalogDO item = new MyCatalogDO();
				item.setReqUsrId(   		rs.getString("REQ_USRID"));
				item.setSeq(        			rs.getInt("SEQ"));
				item.setPgmNm(      		rs.getString("PGM_NM"));
				item.setEpn(        			rs.getString("EPN"));
				item.setTitle(      			rs.getString("TITLE"));
				item.setBrdDd(      		rs.getString("BRD_DD"));				
				item.setGubun(rs.getString("desc"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * 내목록의 특정 정보를 삭제한다.
	 * @param reqUserId 요청자 ID
	 * @param seq 순번
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteMyCatalogInfo(String seq, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.MY_SRCHLIST_TBL  ");
		buf.append("\n where REQ_USRID = ? and SEQ in (" + seq + ") ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteMyCatalogInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, commonDO.getUserId());

			stmt.executeUpdate();
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	public int deleteMyCatalogInfo(MyCatalogDO mycatalogDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.MY_SRCHLIST_TBL  ");
		buf.append("\n where REQ_USRID = ? and SEQ in (" + mycatalogDO.getDel_seq() + ") ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, mycatalogDO.getRegrId());


			int iTmp = 0;
			iTmp = stmt.executeUpdate();
			return iTmp;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		} 
		finally
		{
			release(null, stmt, con);
		}

	}
	/**
	 * 테이프 대출 신청을 한다.
	 * @param tapeLendingDO 대출할 Tape 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertTapeLending(TapeLendingDO tapeLendingDO, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.TAPELENDMST_TBL( ");
		buf.append("\n 	LEND_APLN_NO, "); 
		buf.append("\n 	EMP_NO, "); 
		buf.append("\n 	AGNT,  ");
		buf.append("\n 	APLN_DD, "); 
		buf.append("\n 	PURPOSE,  ");
		buf.append("\n 	U_PGM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	MODRID ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertTapeLending######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			String nextLendAplnNo = getNextSquence(con, DASBusinessConstants.SequenceName.LENDING_APP_NAME);

			stmt.setString(++index, nextLendAplnNo);
			stmt.setString(++index, tapeLendingDO.getEmpNo());     
			stmt.setString(++index, tapeLendingDO.getAgnt());      
			stmt.setString(++index, tapeLendingDO.getAplnDd());    
			stmt.setString(++index, tapeLendingDO.getPurpose());   
			stmt.setString(++index, tapeLendingDO.getUPgm());      
			stmt.setString(++index, toDateTime);     
			stmt.setString(++index, commonDO.getUserId());    
			stmt.setString(++index, toDateTime);     
			stmt.setString(++index, commonDO.getUserId());    

			stmt.executeUpdate();

			int seq = 0;
			for(Iterator i = tapeLendingDO.getTapeLendingItemDOList().iterator(); i.hasNext();)
			{
				seq = seq + 1;
				TapeLendingItemDO tapeLendingItemDO = (TapeLendingItemDO)i.next();
				tapeLendingItemDO.setLendAplnNo(nextLendAplnNo);
				tapeLendingItemDO.setNum(seq);

				insertTapeLendingItem(con, tapeLendingItemDO, toDateTime, commonDO);
			}

			con.commit();

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 테이프 대출 정보를 수정하고 테이프 대출신청 상세 정보를 등록한다.
	 * @param tapeLendingItemDOList
	 * @param commonDO
	 * @throws Exception 
	 */
	public void updateTapeLendingItems(TapeLendingDO tapeLendingDO, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.TAPELENDMST_TBL set ");
		buf.append("\n 	PURPOSE = ?,  ");
		buf.append("\n 	U_PGM = ?,  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where LEND_APLN_NO = ? "); 

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			TapeLendingItemDO maxTapeLendingItemDO = getTapeLendingLendAplnNo(commonDO.getUserNo(), commonDO.getUserId());

			stmt = con.prepareStatement(buf.toString());

			//			현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, tapeLendingDO.getPurpose());   
			stmt.setString(++index, tapeLendingDO.getUPgm());      
			stmt.setString(++index, toDateTime);     
			stmt.setString(++index, commonDO.getUserId());    
			stmt.setString(++index, maxTapeLendingItemDO.getLendAplnNo());    

			stmt.executeUpdate();


			int seq = maxTapeLendingItemDO.getNum();
			for(Iterator i = tapeLendingDO.getTapeLendingItemDOList().iterator(); i.hasNext();)
			{
				TapeLendingItemDO tapeLendingItemDO = (TapeLendingItemDO)i.next();
				if(!isThereLendingItem(tapeLendingItemDO.getReqNo()))
				{
					seq = seq + 1;

					tapeLendingItemDO.setLendAplnNo(maxTapeLendingItemDO.getLendAplnNo());
					tapeLendingItemDO.setNum(seq);

					insertTapeLendingItem(con, tapeLendingItemDO, toDateTime, commonDO);
				}
			}

			con.commit();

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 테이프 대출 신청건을 삭제한다.
	 * @param lendAplnNo 대출 신청 번호
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteTapeLendingAll(String lendAplnNo, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.TAPELENDMST_TBL ");
		buf.append("\n where LEND_APLN_NO = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, lendAplnNo);

			stmt.executeUpdate();

			//테이프 대출 상세내역을 삭제 한다.
			deleteTapeLendingItemAll(con, lendAplnNo);

			con.commit();

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 테이프 대출 상세 내역을 삭제 한다.
	 * @param tapeLendingItemDOList TapeLendingItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteTapeLendingItemList(List tapeLendingItemDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		//PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteTapeLendingItemList######## con : " + con);
			con.setAutoCommit(false);

			for(Iterator i = tapeLendingItemDOList.iterator(); i.hasNext();)
			{
				deleteTapeLendingItem(con, (TapeLendingItemDO)i.next());
			}

			con.commit();

		} 

		catch (Exception e) 
		{
			logger.error("tapeLendingItemDOList : " + tapeLendingItemDOList );
			logger.error("commonDO : " + commonDO );

			if(con != null)
			{
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}


			throw e;
		}
		finally
		{
			release(null, null, con);
		}

	}
	/**
	 *  DAS.TAPELENDMST_TBL 존재하는지 파악
	 * @param userNo 주민등록번호
	 *  @param userId 사용자id
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isThereTapeLending(String userNo, String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	count(1) ");
		buf.append("\n from DAS.TAPELENDMST_TBL  ");
		buf.append("\n where EMP_NO = ? ");
		buf.append("\n 	or AGNT = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereTapeLending######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userNo);
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			rs.next();

			int totalCount = rs.getInt(1);

			if(totalCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	private TapeLendingItemDO getTapeLendingLendAplnNo(String userNo, String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	tape.LEND_APLN_NO, ");
		buf.append("\n 	(select max(NUM) from DAS.TAPELENDDTL_TBL where tape.LEND_APLN_NO = LEND_APLN_NO) AS MAX_COUNT ");
		buf.append("\n from DAS.TAPELENDMST_TBL tape ");
		buf.append("\n where tape.EMP_NO = ? ");
		buf.append("\n 	or tape.AGNT = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getTapeLendingLendAplnNo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userNo);
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			if(rs.next())
			{
				TapeLendingItemDO item = new TapeLendingItemDO();
				item.setLendAplnNo(	rs.getString("LEND_APLN_NO"));
				item.setNum(				rs.getInt("MAX_COUNT"));

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_TAPE_LENDING_INFO, "해당 테이프 대출 신청 정보를 찾을 수 없습니다.");
				throw exception;

			}
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 청구번호가 존재하는지 확인
	 * @param reqNo 청구번호
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isThereLendingItem(String reqNo) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	count(1) ");
		buf.append("\n from DAS.TAPELENDDTL_TBL  ");
		buf.append("\n where REQ_NO = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereLendingItem######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, reqNo);

			rs = stmt.executeQuery();

			rs.next();

			int totalCount = rs.getInt(1);

			if(totalCount > 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 테이프 대출 신청 정보를 조회한다.
	 * @param userNo 사번
	 * @param userId 사용자 ID
	 * @return TapeLendingDO
	 * @throws Exception 
	 */
	public TapeLendingDO selectTapeLendingInfo(String userNo, String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	LEND_APLN_NO, "); 
		buf.append("\n 	EMP_NO,  ");
		buf.append("\n 	AGNT,  ");
		buf.append("\n 	APLN_DD, "); 
		buf.append("\n 	PURPOSE,  ");
		buf.append("\n 	U_PGM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	MODRID ");
		buf.append("\n from DAS.TAPELENDMST_TBL ");
		buf.append("\n where EMP_NO = ? ");
		buf.append("\n 	or AGNT = ? ");
		buf.append("\n WITH UR ");		

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTapeLendingInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userNo);
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			if(rs.next())
			{
				TapeLendingDO item = new TapeLendingDO();
				item.setLendAplnNo(     	rs.getString("LEND_APLN_NO"));
				item.setEmpNo(          		rs.getString("EMP_NO"));
				item.setAgnt(           			rs.getString("AGNT"));
				item.setAplnDd(         		rs.getString("APLN_DD"));
				item.setPurpose(        		rs.getString("PURPOSE"));
				item.setUPgm(           		rs.getString("U_PGM"));
				item.setRegDt(          		rs.getString("REG_DT"));
				item.setRegrId(         		rs.getString("REGRID"));
				item.setModDt(          		rs.getString("MOD_DT"));
				item.setModrId(				rs.getString("MODRID"));

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_TAPE_LENDING_INFO, "해당 테이프 대출 신청 정보를 찾을 수 없습니다.");
				throw exception;

			}
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 테이프 대출 상세 정보를 조회한다.
	 * @param lendAplnNo 대출신청번호
	 * @return List
	 * @throws Exception 
	 */
	public List selectTapeLendingItemInfo(String lendAplnNo) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	tap.LEND_APLN_NO, ");
		buf.append("\n 	tap.NUM,  ");
		buf.append("\n 	tap.TAPE_ID,  ");
		buf.append("\n 	tap.TAPE_ITEM_ID, "); 
		buf.append("\n 	tap.REQ_NO,  ");
		buf.append("\n 	tap.COPY_YN,  ");
		buf.append("\n 	tap.USE_IMG_CONT, "); 
		buf.append("\n 	erp.SCN_TTL, ");
		//사용등급명
		String useGradeCodeName = CodeCommon.getCodeQueryMake(CodeConstants.CodeGroup.CLF_CD_USAGE_LIMITATION_TYPE, "erp.USE_GRADE_CD");
		buf.append(useGradeCodeName + " AS USE_GRADE_NM, ");
		buf.append("\n 	erp.USE_GRADE_CD ");
		buf.append("\n FROM DAS.TAPELENDDTL_TBL tap, DAS.D_TAPEITEM_TBL erp ");
		buf.append("\n where tap.TAPE_ITEM_ID = erp.TAPE_ITEM_ID ");
		buf.append("\n 	and tap.LEND_APLN_NO = ? ");		
		buf.append("\n WITH UR ");		

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTapeLendingItemInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, lendAplnNo);

			rs = stmt.executeQuery();

			List itemList = new ArrayList();
			while(rs.next())
			{
				TapeLendingItemDO item = new TapeLendingItemDO();
				item.setLendAplnNo(     	rs.getString("LEND_APLN_NO"));
				item.setNum(            		rs.getInt("NUM"));
				item.setTapeId(         		rs.getString("TAPE_ID"));
				item.setTapeItemId(     		rs.getString("TAPE_ITEM_ID"));
				item.setReqNo(          		rs.getString("REQ_NO"));
				item.setCopyYn(         		rs.getString("COPY_YN"));
				item.setUseImgCont(     	rs.getString("USE_IMG_CONT"));
				item.setScnTtl(					rs.getString("SCN_TTL"));
				item.setUseGradeNm( 		rs.getString("USE_GRADE_NM"));
				item.setUseGradeCd(		rs.getString("USE_GRADE_CD"));

				itemList.add(item);
			}

			return itemList;

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 테이프 대출 청구번호를 조회한다.
	 * @param lendAplnNo 대출신청번호
	 * @return List
	 * @throws Exception 
	 */
	public List selectTapeLendingItemReqNo(String lendAplnNo) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	tap.REQ_NO  ");
		buf.append("\n FROM DAS.TAPELENDDTL_TBL tap, DAS.D_TAPEITEM_TBL erp ");
		buf.append("\n where tap.TAPE_ITEM_ID = erp.TAPE_ITEM_ID ");
		buf.append("\n 	and tap.LEND_APLN_NO = ? ");		
		buf.append("\n WITH UR ");		

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTapeLendingItemReqNo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, lendAplnNo);

			rs = stmt.executeQuery();

			List itemList = new ArrayList();
			while(rs.next())
			{
				itemList.add(rs.getString("REQ_NO"));
			}

			return itemList;

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}


	/**
	 * 요청 영상 목록 조회를 한다.
	 * @param searchConditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회한 요청 영상 목록을 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO selectRequestDownList(SearchConditionDO searchConditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append("\n select * FROM                                                      										\n");
		buf.append("\n (                                                                  												\n");
		buf.append(SearchStatement.selectRequestDownListQuery(commonDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append("\n ) AS temp                                                           											\n");
		buf.append("\n where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append("\n WITH UR	 ");

		//Page에 따른 계산을 한다.
		int page = searchConditionDO.getPage();
		if(page == 0)
		{
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectRequestDownList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SearchStatement.selectRequestDownListQuery(commonDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = searchConditionDO.getRowPerPage();
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.DOWN_REQ_ROW_COUNT;
			}

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{
				ReqDownItemDO item = new ReqDownItemDO();
				item.setSerialNo(       		rs.getInt("rownum"));
				item.setCartNo(         		rs.getBigDecimal("CART_NO"));
				item.setCartSeq(        		rs.getInt("CART_SEQ"));
				item.setVdQltyNm(       		rs.getString("VD_QLTY_NM"));
				item.setAspRtoNm(       	rs.getString("ASP_RTO_NM"));
				item.setPgmNm(	        	rs.getString("PGM_NM"));
				item.setEpisNo(         		rs.getInt("EPIS_NO"));
				item.setSom(            		rs.getString("SOM"));
				item.setEom(            		rs.getString("EOM"));
				item.setRistClfNm(				rs.getString("RIST_CLF_NM")); 

				resultList.add(item);
			}
			int totalPageCount = totalCount / rowPerPage  + (totalCount % rowPerPage != 0 ? 1 : 0);

			//검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			//계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);


			return pageDO;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 다운로드 제목과 요청자명을 수정한다.
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateRequestDownSubject(String downSubject, String reqUserNm, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DOWN_CART_TBL set ");
		buf.append("\n 	DOWN_SUBJ = ?, ");
		buf.append("\n 	REQ_NM = ?, ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where REQ_USRID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRequestDownSubject######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, downSubject);
			stmt.setString(++index, reqUserNm);
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, commonDO.getUserId());
			stmt.setString(++index, commonDO.getUserId());

			stmt.executeUpdate();

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	/**
	 * 스토리 보드 화면에서 감수시 자료상태 코드를 감수완료로 수정한다
	 * @param masterId 마스타ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDataStatus(String masterId, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL set ");
		buf.append("\n 	DATA_STAT_CD = ?, ");
		//buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where MASTER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDataStatus######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, CodeConstants.DataStatusCode.COMPLET);
			//stmt.setString(++index, toDateTime);
			stmt.setString(++index, commonDO.getUserId());
			stmt.setLong(++index,  Long.parseLong(masterId));

			stmt.executeUpdate();

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, stmt, con);
		}

	}

	private void insertTapeLendingItem(Connection con, TapeLendingItemDO tapeLendingItemDO, String toDateTime, DASCommonDO commonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.TAPELENDDTL_TBL( ");
		buf.append("\n 	LEND_APLN_NO, "); 
		buf.append("\n 	NUM,  ");
		buf.append("\n 	TAPE_ID,  ");
		buf.append("\n 	TAPE_ITEM_ID, "); 
		buf.append("\n 	REQ_NO,  ");
		buf.append("\n 	COPY_YN,  ");
		buf.append("\n 	USE_IMG_CONT,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	MODRID ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, (select erp.COPY_YN from DAS.D_TAPE_TBL erp where erp.TAPE_ID = ?), ?, ?, ?, ?, ?) ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setString(++index, tapeLendingItemDO.getLendAplnNo());
			stmt.setInt(++index, tapeLendingItemDO.getNum()); 
			stmt.setString(++index, tapeLendingItemDO.getTapeId()); 		
			stmt.setString(++index, tapeLendingItemDO.getTapeItemId());      
			stmt.setString(++index, tapeLendingItemDO.getReqNo());           
			stmt.setString(++index, tapeLendingItemDO.getTapeId());          
			stmt.setString(++index, tapeLendingItemDO.getUseImgCont());      
			stmt.setString(++index, tapeLendingItemDO.getRegDt());           
			stmt.setString(++index, tapeLendingItemDO.getRegrId());          
			stmt.setString(++index, tapeLendingItemDO.getModDt());           
			stmt.setString(++index, tapeLendingItemDO.getModrId());          

			stmt.executeUpdate();

		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}

	private void deleteTapeLendingItemAll(Connection con, String lendAplnNo) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.TAPELENDDTL_TBL ");
		buf.append("\n where LEND_APLN_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, lendAplnNo);

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}

	private void deleteTapeLendingItem(Connection con, TapeLendingItemDO tapeLendingItemDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.TAPELENDDTL_TBL ");
		buf.append("\n where LEND_APLN_NO = ? and NUM = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, tapeLendingItemDO.getLendAplnNo());
			stmt.setInt(++index, tapeLendingItemDO.getNum());

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}

	private int getMyCatalogMaxSeq(String reqUserId) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select value(max(SEQ), 0) FROM  DAS.MY_SRCHLIST_TBL where REQ_USRID = '"+reqUserId+"'  \n");

		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getMyCatalogMaxSeq######## con : " + con);
			int maxSeq  = getTotalCount(con, buf.toString());

			return maxSeq;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(null, null, con);
		}
	}

	/**
	 * 테이프 아이템 상세 정보를 조회한다.
	 * @param req_no 청구번호
	 * @return TapeItemInfoDO
	 * @throws Exception 
	 */
	public TapeItemInfoDO viewTapeItemInfo(String req_no) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n	TAPE_ITEM_ID, ");
		buf.append("\n	TAPE_ID, ");
		buf.append("\n	SCN_CNT, ");
		buf.append("\n	TAPE_CLF, ");
		buf.append("\n	SCN_NO, ");
		buf.append("\n	REQ_NO, ");
		buf.append("\n	SCN_TTL, ");
		buf.append("\n	SUB_TTL, ");
		buf.append("\n	RPTR, ");
		buf.append("\n	CMR_MAN, ");
		buf.append("\n	DEPT_CD, ");
		buf.append("\n	CMR_PLACE, ");
		buf.append("\n	CMR_DD, ");
		buf.append("\n	RST_CONT, ");
		buf.append("\n	LEN, ");
		buf.append("\n	CASTING, ");
		buf.append("\n	ORG_TTL, ");
		buf.append("\n	PRDT_CO_NM, ");
		buf.append("\n	DIRT, ");
		buf.append("\n	PRDT_YYYY, ");
		buf.append("\n	TIME_CD, ");
		buf.append("\n	SRIS_NO, ");
		buf.append("\n	EPIS_NO, ");
		buf.append("\n	BRD_DD,");
		buf.append("\n	KEY_WORDS, ");
		buf.append("\n	PHOTO_METHOD, ");
		buf.append("\n	SCN_CONT, ");
		buf.append("\n	MUSIC_INFO, ");
		buf.append("\n	SNPS, ");
		buf.append("\n	AUDIO_CD, ");
		buf.append("\n	COLOR_CD, ");
		buf.append("\n	RECORD_TYPE_CD, ");
		buf.append("\n	ME_CD, ");
		buf.append("\n	CPRTR, ");
		buf.append("\n	CPRT_TYPE, ");
		buf.append("\n	WTCH_GR_NM, ");
		buf.append("\n	AWARD_INFO, ");
		buf.append("\n	ACCESS_RIGHTER, ");
		buf.append("\n	RIGHT_OWNER, ");
		buf.append("\n	LICENSE_OPTION, ");
		buf.append("\n	MAX_USAGE_COUNT, ");
		buf.append("\n	WTCH_GR, ");
		buf.append("\n	DLBR_GR, ");
		buf.append("\n	KEEP_PRT_CD, ");
		buf.append("\n	DATA_STAT_CD, ");
		buf.append("\n	REGR, ");
		buf.append("\n	ARCHIVE_DD, ");
		buf.append("\n	MC, ");
		buf.append("\n	AUTHOR, ");
		buf.append("\n	ORG_PRDR, ");
		buf.append("\n	STAFF, ");
		buf.append("\n	PRDT_TYPE_CD, ");
		buf.append("\n	REG_DD, ");
		buf.append("\n	PGM_NM, ");
		buf.append("\n	DATA_KIND, ");
		buf.append("\n	USE_GRADE_CD, ");
		buf.append("\n	WORK_STAT, ");
		buf.append("\n	LOCK_STAT, ");
		buf.append("\n	RLT_TEXT1, ");
		buf.append("\n	RLT_TEXT2, ");
		buf.append("\n	RLT_TEXT3, ");
		buf.append("\n	RLT_FILE1, ");
		buf.append("\n	RLT_FILE2, ");
		buf.append("\n	RLT_FILE3, ");
		buf.append("\n	BGN_TIME, ");
		buf.append("\n	END_TIME, ");
		buf.append("\n	REVIEW, ");
		buf.append("\n	SCHD_WEEK_DD, ");
		buf.append("\n	PGM_CD, ");
		buf.append("\n	SEL_CONT, ");
		buf.append("\n	BUY_PRC, ");
		buf.append("\n	PRDTR, ");
		buf.append("\n	GAME_NM, ");
		buf.append("\n	CMNTR, ");
		buf.append("\n	TOT_CNT, ");
		buf.append("\n	BUY_CONT, ");
		buf.append("\n	WORD_CD, ");
		buf.append("\n	VIEW_GR, ");
		buf.append("\n	RLY_BRD, ");
		buf.append("\n	WORK_SEQ, ");
		buf.append("\n	INGEST_YN, ");
		buf.append("\n	INGEST_STATUS, ");
		buf.append("\n	INGEST_DD, ");
		buf.append("\n	( ");
		buf.append("\n		select ORG_NM ");
		buf.append("\n		from DAS.ERP_COM_BAS_TBL ");
		buf.append("\n		where ORG_CD = DEPT_CD ");
		buf.append("\n	) as DEPT_NM ");
		buf.append("\n  from DAS.D_TAPEITEM_TBL ");
		buf.append("\n  where REQ_NO = ? ");
		buf.append("\n  with UR ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######viewTapeItemInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, req_no);

			rs = stmt.executeQuery();

			TapeItemInfoDO tapeItemInfo = new TapeItemInfoDO();
			while(rs.next())
			{
				tapeItemInfo.setTapeItemId(rs.getString("TAPE_ITEM_ID"));
				tapeItemInfo.setTapeId(rs.getString("TAPE_ID"));
				tapeItemInfo.setScnCnt(rs.getInt("SCN_CNT"));
				tapeItemInfo.setTapeClf(rs.getString("TAPE_CLF"));
				tapeItemInfo.setScnNo(rs.getInt("SCN_NO"));
				tapeItemInfo.setReqNo(rs.getString("REQ_NO"));
				tapeItemInfo.setScnTtl(rs.getString("SCN_TTL"));
				tapeItemInfo.setSubTtl(rs.getString("SUB_TTL"));
				tapeItemInfo.setRptr(rs.getString("RPTR"));
				tapeItemInfo.setCmrMan(rs.getString("CMR_MAN"));
				tapeItemInfo.setDeptCd(rs.getString("DEPT_CD"));
				tapeItemInfo.setCmrPlace(rs.getString("CMR_PLACE"));
				tapeItemInfo.setCmrDd(rs.getString("CMR_DD"));
				tapeItemInfo.setRstCont(rs.getString("RST_CONT"));
				tapeItemInfo.setLen(rs.getInt("LEN"));
				tapeItemInfo.setCasting(rs.getString("CASTING"));
				tapeItemInfo.setOrgTtl(rs.getString("ORG_TTL"));
				tapeItemInfo.setPrdtCoNm(rs.getString("PRDT_CO_NM"));
				tapeItemInfo.setDirt(rs.getString("DIRT"));
				tapeItemInfo.setPrdtYyyy(rs.getString("PRDT_YYYY"));
				tapeItemInfo.setTimeCd(rs.getString("TIME_CD"));
				tapeItemInfo.setSrisNo(rs.getInt("SRIS_NO"));
				tapeItemInfo.setEpisNo(rs.getInt("EPIS_NO"));
				tapeItemInfo.setBrdDd(rs.getString("BRD_DD"));
				tapeItemInfo.setKeyWord(rs.getString("KEY_WORDS"));
				tapeItemInfo.setPhotoMethod(rs.getString("PHOTO_METHOD"));
				tapeItemInfo.setScnCont(rs.getString("SCN_CONT"));
				tapeItemInfo.setMusicInfo(rs.getString("MUSIC_INFO"));
				tapeItemInfo.setSnps(rs.getString("SNPS"));
				tapeItemInfo.setAudioCd(rs.getString("AUDIO_CD"));
				tapeItemInfo.setColorCd(rs.getString("COLOR_CD"));
				tapeItemInfo.setRecordTypeCd(rs.getString("RECORD_TYPE_CD"));
				tapeItemInfo.setMeCd(rs.getString("ME_CD"));
				tapeItemInfo.setCprtr(rs.getString("CPRTR"));
				tapeItemInfo.setCprtType(rs.getString("CPRT_TYPE"));
				tapeItemInfo.setWtchGrNm(rs.getString("WTCH_GR_NM"));
				tapeItemInfo.setAwardInfo(rs.getString("AWARD_INFO"));
				tapeItemInfo.setAccessRighter(rs.getString("ACCESS_RIGHTER"));
				tapeItemInfo.setRightOwner(rs.getString("RIGHT_OWNER"));
				tapeItemInfo.setLicenseOption(rs.getString("LICENSE_OPTION"));
				tapeItemInfo.setMaxUsageCount(rs.getString("MAX_USAGE_COUNT"));
				tapeItemInfo.setWtchGr(rs.getString("WTCH_GR"));
				tapeItemInfo.setDlbrGr(rs.getString("DLBR_GR"));
				tapeItemInfo.setKeepPrtCd(rs.getString("KEEP_PRT_CD"));
				tapeItemInfo.setDataStatCd(rs.getString("DATA_STAT_CD"));
				tapeItemInfo.setRegr(rs.getString("REGR"));
				tapeItemInfo.setArchiveDd(rs.getString("ARCHIVE_DD"));
				tapeItemInfo.setMc(rs.getString("MC"));
				tapeItemInfo.setAuthor(rs.getString("AUTHOR"));
				tapeItemInfo.setOrgPrdr(rs.getString("ORG_PRDR"));
				tapeItemInfo.setStaff(rs.getString("STAFF"));
				tapeItemInfo.setPrdtTypeCd(rs.getString("PRDT_TYPE_CD"));
				tapeItemInfo.setRegDd(rs.getString("REG_DD"));
				tapeItemInfo.setPgmNm(rs.getString("PGM_NM"));
				tapeItemInfo.setDataKind(rs.getString("DATA_KIND"));
				tapeItemInfo.setUseGradeCd(rs.getString("USE_GRADE_CD"));
				tapeItemInfo.setWorkStat(rs.getString("WORK_STAT"));
				tapeItemInfo.setLockStat(rs.getString("LOCK_STAT"));
				tapeItemInfo.setRltText1(rs.getString("RLT_TEXT1"));
				tapeItemInfo.setRltText2(rs.getString("RLT_TEXT2"));
				tapeItemInfo.setRltText3(rs.getString("RLT_TEXT3"));
				tapeItemInfo.setRltFile1(rs.getString("RLT_FILE1"));
				tapeItemInfo.setRltFile2(rs.getString("RLT_FILE2"));
				tapeItemInfo.setRltFile3(rs.getString("RLT_FILE3"));
				tapeItemInfo.setBgnTime(rs.getString("BGN_TIME"));
				tapeItemInfo.setEndTime(rs.getString("END_TIME"));
				tapeItemInfo.setReview(rs.getString("REVIEW"));
				tapeItemInfo.setSchdWeekDd(rs.getString("SCHD_WEEK_DD"));
				tapeItemInfo.setPgmCd(rs.getString("PGM_CD"));
				tapeItemInfo.setSelCont(rs.getString("SEL_CONT"));
				tapeItemInfo.setBuyPrc(rs.getString("BUY_PRC"));
				tapeItemInfo.setPrdtr(rs.getString("PRDTR"));
				tapeItemInfo.setGameNm(rs.getString("GAME_NM"));
				tapeItemInfo.setCmntr(rs.getString("CMNTR"));
				tapeItemInfo.setTotCnt(rs.getInt("TOT_CNT"));
				tapeItemInfo.setBuyCont(rs.getString("BUY_CONT"));
				tapeItemInfo.setWordCd(rs.getString("WORD_CD"));
				tapeItemInfo.setViewGr(rs.getString("VIEW_GR"));
				tapeItemInfo.setRlyBrd(rs.getString("RLY_BRD"));
				tapeItemInfo.setWorkSeq(rs.getString("WORK_SEQ"));
				tapeItemInfo.setIngestYn(rs.getString("INGEST_YN"));
				tapeItemInfo.setIngestStatus(rs.getString("INGEST_STATUS"));
				tapeItemInfo.setIngestDd(rs.getString("INGEST_DD"));
				tapeItemInfo.setDeptNm(rs.getString("DEPT_NM"));
			}

			return tapeItemInfo;

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}
	/**
	 * 테이프 상세 정보를 조회한다.
	 * @param req_no 청구번호
	 * @return TapeInfoDO
	 * @throws Exception 
	 */
	public TapeInfoDO viewTapeInfo(String req_no) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	COPY_REQ_NO, ");
		buf.append("\n 	COPY_KEEP, ");
		buf.append("\n 	KEEP_DEPT, ");
		buf.append("\n 	CLEAN_REQ_NO, ");
		buf.append("\n 	CLEAN_KEEP, ");
		buf.append("\n 	TAPE_NUM, ");
		buf.append("\n 	TAPE_KIND ");
		buf.append("\n   from DAS.D_TAPE_TBL ");
		buf.append("\n   where REQ_NO = ? ");
		buf.append("\n   with UR ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######viewTapeInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, req_no);

			rs = stmt.executeQuery();

			TapeInfoDO tapeInfo = new TapeInfoDO();
			while(rs.next())
			{
				tapeInfo.setCopyReqNo(rs.getString("COPY_REQ_NO"));
				tapeInfo.setCopyKeep(rs.getString("COPY_KEEP"));
				tapeInfo.setKeepDept(rs.getString("KEEP_DEPT"));
				tapeInfo.setCleanReqNo(rs.getString("CLEAN_REQ_NO"));
				tapeInfo.setCleanKeep(rs.getString("CLEAN_KEEP"));
				tapeInfo.setTapeNum(rs.getString("TAPE_NUM"));
				tapeInfo.setTapeKind(rs.getString("TAPE_KIND"));


			}

			return tapeInfo;

		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}



	/**
	 * 코너 정보 가져오기.
	 * @throws Exception 
	 */
	public String getCornerInfo(String master_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	cn_nm, ");
		buf.append("\n 	cn_info ");
		buf.append("\n   from DAS.CORNER_TBL ");
		buf.append("\n   where MASTER_ID = ? ");
		buf.append("\n       and CN_TYPE_CD = '003' ");  
		buf.append("\n   with UR ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String cn_nmList = "";
		String cn_infoList = "";
		int i = 0;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getCornerInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, master_id);

			rs = stmt.executeQuery();

			while(rs.next())
			{
				if ("".equals(cn_nmList)) {
					cn_nmList = rs.getString("cn_nm");
				} else {
					cn_nmList += " / " + rs.getString("cn_nm");
				}
				cn_infoList += rs.getString("cn_info") + "  ";
				i++;
			}

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

		if (i == 1) {
			return cn_infoList;
		} else {
			return cn_nmList;
		}
	}

	/**
	 * 검색 서치 ( 코난검색엔진)
	 * @param parameterVO 검색 파라메타 정보
	 * @return
	 * @throws Exception 
	 */
	public String getSearchText(ParameterVO srchParam) throws Exception{

		/************************************/
		/* 변수 초기화 및 카테고리별 설정                   */
		/************************************/
		String scn = srchParam.getScn();						//시나리오 
		String orderBy = srchParam.getSort();					//정렬문 (ex : order by regdate desc)
		String orderNm = srchParam.getSortColunm();				//정렬명 (ex : 날짜순)
		int pageNum = srchParam.getPageNum();					// 페이지 번호
		int pageSize = srchParam.getPageSize();					// 페이지 사이즈
		String hilightTxt = srchParam.getKwd();					// 하이라이팅 키워드
		String logInfo = "";									// 검색로그
		StringBuffer query = new StringBuffer();				// 검색 쿼리
		String srchFdNm = "text_idx"; 
		String ctgr_l_cd = srchParam.getCtgrlcd();				//소재 구분
		String browser = srchParam.getBrowser();				//브라우져
		String[] channel_cds = srchParam.getChannel_cd();				//채널 채널권한
		String[] program_yns = srchParam.getProgram_yn();				//프로그램 조회권한
		String[] material_yns = srchParam.getMaterial_yn();				//소재 조회 권한
		/***************************************************************************/

		if(scn.equalsIgnoreCase("program")){
			query = DCUtil.makeQuery("DELETE_FLAG ", "N", "", query, "and");
			query = DCUtil.makeQuery("LINK_MASTER ", "N", "", query, "and");
			query = DCUtil.makeQuery("DATA_STAT_CD  ", "NON", "", query, "and");
			//20120728 검색경로별 조회조건 변경
			if(browser.equals("")){			
				query = DCUtil.makeQuery("COCD  ", "S", "", query, "and");	
			}
			if(!ctgr_l_cd.equals("")){
				query = DCUtil.makeQuery("CTGR_L_CD", ctgr_l_cd, "", query, "and");
			}
		}else {

		}
		if("annot".equalsIgnoreCase(srchParam.getScn())){
			query = DCUtil.makeQuery("DELETE_FLAG ", "N", "", query, "and");
			// 주제영상 구분에 대한 전처리 
			query = DCUtil.makeQuery("GUBUN", "S", "", query, "and"); // 'GUBUN' 컬럼의 데이타 타입이 text 이 아니기에 'allword' 빼야 한다.
			query = DCUtil.makeQuery("ANNOT_CLF_CD", srchParam.getSceanGubun(), "", query, "and");
		}

		if (!orderNm.equals("")) {
			//20110518 mailFrom: [SBS]PGM_NM_TITLE 필드 관련 내용 [수정처리완료.]
			if(orderNm.equals("PGMNM_TITLE")&& srchParam.getScn().equals("annot")){
				orderNm ="TITLE";
			}else 	if(orderNm.equals("PGMNM_TITLE")){
				orderNm ="PGMNM_TITLE_STR";
			}

			//20110623 mailFrom: RE: Re:RE: DAS 검색엔진 수정요청 사항 입니다.-요청추가 7번
			if(orderNm.equals("EPIS_NO")){
				orderNm ="EPIS_NO_STR";
			}

			if(orderBy.equals("D")){
				orderBy = "order by " +orderNm + " desc";
			}else if(orderBy.equals("A")){
				orderBy = "order by " +orderNm+ " asc";	
			}
			//	orderNm = "최신날짜순";
		} 

		//로그포맷
		logInfo = DCUtil.getLogInfo("site", srchParam.getCategory(), "", srchParam.getKwd(), 
				srchParam.getPageNum(), srchParam.getReSrchFlag(), orderNm, 
				srchParam.getRecKwd());

		//20120813 ifcms용 조회 쿼리 추가 권한.
		if(!channel_cds.equals("")){
			int length = channel_cds.length;
			String newQuery ="";
			String nextjob="";
			boolean all = false;
			for(int j =0 ; j<length;j++){
				boolean pgm = false;
				boolean material = false;

				String channelCd = channel_cds[j];
				if("Y".equals(program_yns[j])) {
					pgm = true;
				}
				if("Y".equals(material_yns[j])) {
					material = true;
				}

				if(!pgm && !material){

				} else if(!pgm || !material) {
					if(pgm) {
						if(newQuery.equals("")){
							newQuery= "(CHENNEL_CD ='"+channelCd+"'" +" and CTGR_L_CD='200')";
						}else{
							newQuery= newQuery + " OR (CHENNEL_CD ='"+channelCd+"'" +" and CTGR_L_CD='200')";
						}
					} else if(material){
						if(newQuery.equals("")){
							newQuery= "(CHENNEL_CD ='"+channelCd+"'" +" and CTGR_L_CD='100')";
						}else{
							newQuery= newQuery + " OR (CHENNEL_CD ='"+channelCd+"'" +" and CTGR_L_CD='100')";
						}
					}
				} else {
					if(newQuery.equals("")){
						newQuery= "(CHENNEL_CD ='"+channelCd+"'" +" )";
					}else{
						newQuery= newQuery + " OR (CHENNEL_CD ='"+channelCd+"'" +" )";
					}
				}

				if(j<=length){
					nextjob="Y";
				}else{
					nextjob="N";
				}

			}
			if(!newQuery.equals("")){
				query =  DCUtil.makeQueryForAuth(newQuery, query);
			}
		}


		if (srchParam.getReSrchFlag() && srchParam.getPreKwds() != null) {
			for (int cnt = 0; cnt < srchParam.getPreKwds().length; cnt++) {
				query = DCUtil.makePreQuery(srchFdNm, srchParam.getKwd(), srchParam.getPreKwds(), 
						srchParam.getPreKwds().length,query, "allwordthruindex synonym");
			}
		}

		//기본 키워드 검색 
		if(srchParam.getGrpSrchFd() == null){
			if(channel_cds.length!=0){
				query.append(")");
			}
			query = DCUtil.makeQuery(srchFdNm, srchParam.getKwd(), "allwordthruindex synonym", query, "and");
		}else{
			String[] grpKwd = srchParam.getGrpKwd(); // 상세검색 검색어
			String[] grSrchFd = srchParam.getGrpSrchFd(); // 상세검색 검색대상
			String[] grpAndOr   = srchParam.getGrpAndOr(); // 상세검색 연결 첨언

			/**
			 * grpStartDD & grpEndDD 값이 현재 상세검색 조건값으로 들어올수 있는 값들에 대해서 정의해 Object 인데
			 * 방송일/촬영일에 대해서 정의해 놓고 가는 것이당. 
			 */
			String[] grpStartDD   = srchParam.getGrpStartDD();  // 시작일(
			String[] grpEndDD   = srchParam.getGrpEndDD();      // 종료일
			String kwd = "";
			String srch_fd = "";
			String use_start_dd = "";
			String use_end_Dd = "";
			int count=0;
			int DdCount=0;

			if (srchParam.getDetailSearch()) {	//상세검색일 경우 쿼리 생성

				if(srchParam.getGrpStartDD() != null){
					String startVal = "";
					String endVal = "";
					String SrchFd = "";

					for(int cnt = 0; cnt < grSrchFd.length; cnt++){

						if (!srchParam.getGrpStartDD().equals("")&&(grSrchFd[cnt].equals("REG_DT")||grSrchFd[cnt].equals("FM_DT")||grSrchFd[cnt].equals("BRD_DD"))) {
							if(browser.equals("")){
								startVal = grpStartDD[0];
							}else{
								for(int i =0; i<grpStartDD.length;i++){
									if(!use_start_dd.matches(".*"+grpStartDD[i]+".*")){
										startVal = grpStartDD[i];
									}
									use_start_dd=use_start_dd+","+startVal;
								}
							}
						}
						if (!srchParam.getGrpEndDD().equals("")&&(grSrchFd[cnt].equals("REG_DT")||grSrchFd[cnt].equals("FM_DT")||grSrchFd[cnt].equals("BRD_DD"))) {
							if(browser.equals("")){
								endVal = grpEndDD[0];
							}else{
								for(int i =0; i<grpEndDD.length;i++){
									if(!use_end_Dd.matches(".*"+grpEndDD[i]+".*")){
										endVal = grpEndDD[i];
									}
									use_end_Dd=use_end_Dd+","+endVal;
								}
							}
						}

						//if cms의 상세 조건중 방송,촬영, 등록일 조건추가 (20120921 by mentis)
						if(browser.equals("")){
							query = DCUtil.makeRangeQueryBrd_ddFm_dt(startVal, endVal, query);
						}else if (!srchParam.getGrpSrchFd().equals("")&&(grSrchFd[cnt].equals("REG_DT")||grSrchFd[cnt].equals("FM_DT")||grSrchFd[cnt].equals("BRD_DD"))) {
							SrchFd = grSrchFd[cnt];

							query = DCUtil.makeRangeQueryBrd_dd(SrchFd, startVal, endVal, query, count);	
							count++;
						}

						if(count==3 && StringUtils.isNotBlank(browser)){
							query.append(" ) ");
						}
					}

					if(count!=0 && count<3 && StringUtils.isNotBlank(browser)){
						query.append(" ) ");
					}
				}

				if(channel_cds.length!=0){
					query.append(")");
				}

				if(srchParam.getGrpKwd() != null){
					for(int cnt = 0; cnt < grSrchFd.length; cnt++){
						if(grSrchFd[cnt].equalsIgnoreCase("TITLE")){
							grSrchFd[cnt] ="PGMNM_TITLE";
						}
						//무제한은 공백 값이므로 공백으로 조회하도록 수정 20121022
						for(int kcnt =0; kcnt< grpKwd.length; kcnt++){
							if(grSrchFd[cnt].equalsIgnoreCase("ANNOT_CLF_NM")){
								if(grpKwd[kcnt].equals("무제한")){
									//	grpKwd[kcnt]="";
								}
							}
							if(!srch_fd.matches(".*"+grSrchFd[cnt]+".*")&&!kwd.matches(".*"+grpKwd[kcnt]+".*")&&!(grSrchFd[cnt].equals("REG_DT")||grSrchFd[cnt].equals("FM_DT")||grSrchFd[cnt].equals("BRD_DD"))){
								query = DCUtil.makeQuery(grSrchFd[cnt], grpKwd[kcnt], "", query, grpAndOr[kcnt]);
								kwd=kwd+grpKwd[kcnt]+",";
								srch_fd=srch_fd+grSrchFd[cnt]+",";
							}
						}

					}

				}

				if (!"".equals(srchParam.getExclusiveKwd()) || srchParam.getExclusiveKwd().length() > 0) {
					query = DCUtil.makeQuery(srchFdNm, srchParam.getExclusiveKwd(), "", query, "and not");
				}
			}
		}

		if(!srchParam.getStartDate().equals("")||!srchParam.getEndDate().equals("")){
			String startVal=srchParam.getStartDate();
			String endVal=srchParam.getEndDate();
			if(browser.equals("")){	
				query = DCUtil.makeRangeQueryBrd_ddFm_dt(startVal, endVal, query);
			}else{
				query = DCUtil.makeRangeQueryBrd_ddFm_dtForIfCms(startVal, endVal, query);	 
			}
		}

		try {
			if(logger.isDebugEnabled()) {
				logger.debug("Search Query ["+query.toString()+"]");
				logger.debug("Search scn ["+scn+"]");
				logger.debug("Search orderBy ["+orderBy+"]");
				logger.debug("Search pageNum ["+pageNum+"]");
				logger.debug("Search pageSize ["+pageSize+"]");
			}
			
			String xml = ExportXML.search(scn, query.toString(),orderBy, pageNum, pageSize, 1, 1 );
			
			return xml;
		} catch (Exception ex) {
			logger.error(query.toString());
			throw ex;
		}
	}

	public String getSearchTextTest(ParameterVO srchParam) throws Exception{

		/************************************/
		/* 변수 초기화 및 카테고리별 설정                   */
		/************************************/
		String scn = srchParam.getScn();						//시나리오 
		String orderBy = "";									//정렬문 (ex : order by regdate desc)
		String orderNm = "";									//정렬명 (ex : 날짜순)
		int pageNum = srchParam.getPageNum();					// 페이지 번호
		int pageSize = srchParam.getPageSize();					// 페이지 사이즈
		String hilightTxt = srchParam.getKwd();					// 하이라이팅 키워드
		String logInfo = "";									// 검색로그
		StringBuffer query = new StringBuffer();				// 검색 쿼리
		String srchFdNm = "text_idx"; 
		/***************************************************************************/
		try {
			query = DCUtil.makeQuery(srchFdNm, srchParam.getKwd(), "allword", query, "");
			logger.debug("getSearchTextText!!");
			logger.debug("Search Query ["+query.toString()+"]");
			logger.debug("Search scn ["+scn+"]");
			logger.debug("Search orderBy ["+orderBy+"]");
			logger.debug("Search pageNum ["+pageNum+"]");
			logger.debug("Search pageSize ["+pageSize+"]");

			return ExportXML.search(scn, query.toString(),orderBy, pageNum, pageSize, 1, 1 );

		} catch (Exception ex) {
			logger.error(query.toString());
			throw ex;
		}
	}


	/**
	 * 검색 서치 ( 코난검색엔진)(상세 검색용)
	 * @param parameterVO 검색 파라메타 정보
	 * @return
	 * @throws Exception 
	 */
	public String getSearchTextForDetail(ParameterVO srchParam) throws Exception{

		/************************************/
		/* 변수 초기화 및 카테고리별 설정                   */
		/************************************/
		String scn = srchParam.getScn();						//시나리오 
		String orderBy = "";									//정렬문 (ex : order by regdate desc)
		String orderNm = "";									//정렬명 (ex : 날짜순)
		int pageNum = srchParam.getPageNum();					// 페이지 번호
		int pageSize = srchParam.getPageSize();					// 페이지 사이즈
		String hilightTxt = srchParam.getKwd();					// 하이라이팅 키워드
		String logInfo = "";									// 검색로그
		StringBuffer query = new StringBuffer();				// 검색 쿼리
		String srchFdNm = "text_idx"; 
		/***************************************************************************/

		int rc = 0;

		//로그포맷
		query = DCUtil.makeQuery("DELETE_FLAG ", "N", "", query, "and"); 
		logInfo = DCUtil.getLogInfo("site", srchParam.getCategory(), "", srchParam.getKwd(), 
				srchParam.getPageNum(), srchParam.getReSrchFlag(), orderNm, 
				srchParam.getRecKwd());

		if (srchParam.getReSrchFlag() && srchParam.getPreKwds() != null) {
			for (int cnt = 0; cnt < srchParam.getPreKwds().length; cnt++) {
				query = DCUtil.makePreQuery(srchFdNm, srchParam.getKwd(), srchParam.getPreKwds(), 
						srchParam.getPreKwds().length, query, "allwordthruindex");
			}
		}

		String[] grpKwd = srchParam.getGrpKwd();
		String[] grSrchFd = srchParam.getGrpSrchFd();
		String[] grpAndOr   = srchParam.getGrpAndOr();
		String[] grpStartDD   = srchParam.getGrpStartDD();
		String[] grpEndDD   = srchParam.getGrpEndDD();

		if(srchParam.getGrpStartDD()!=null){
			if(!srchParam.getGrpStartDD().equals("")){
				String startVal = "";
				String endVal = "";
				for(int cnt = 0; cnt < grpStartDD.length; cnt++){
					if (srchParam.getStartDate().equals("")) {
						startVal = grpStartDD[cnt]+"000000";
					}
					if (srchParam.getEndDate().equals("")) {
						endVal = grpEndDD[cnt]+"999999";
					}

					query = DCUtil.makeRangeQuery(grSrchFd[cnt], startVal, endVal, query);
				}
			}
		}else{
			if(srchParam.getKwd() != null && srchParam.getKwd().length() > 0){
				query = DCUtil.makeQuery(srchFdNm, srchParam.getKwd(), "allword", query, "and");
			}
			for (int cnt = 0; cnt < grpKwd.length; cnt++) {
				query = DCUtil.makeQuery(grSrchFd[cnt], grpKwd[cnt], "", query, grpAndOr[cnt]);
			}
		}

		if (srchParam.getDetailSearch()) {	//상세검색일 경우 쿼리 생성
			//날짜 쿼리 추가
			if (!"".equals(srchParam.getStartDate()) || !"".equals(srchParam.getEndDate())) {
				String startVal = "";
				String endVal = "";
				if (!"".equals(srchParam.getStartDate())) {
					startVal = srchParam.getStartDate()+"000000";
				}
				if (!"".equals(srchParam.getEndDate())) {
					endVal = srchParam.getEndDate()+"999999";
				}

				query = DCUtil.makeRangeQuery("regdate", startVal, endVal, query);
			}

			if (!"".equals(srchParam.getExclusiveKwd()) || srchParam.getExclusiveKwd().length() > 0) {
				query = DCUtil.makeQuery(srchFdNm, srchParam.getExclusiveKwd(), "", query, "and not");
			}
		}
		try {
			logger.debug("Search Query ["+query.toString()+"]");
			logger.debug("Search scn ["+scn+"]");
			logger.debug("Search orderBy ["+orderBy+"]");
			logger.debug("Search pageNum ["+pageNum+"]");
			logger.debug("Search pageSize ["+pageSize+"]");
			return ExportXML.search(scn, query.toString(),orderBy, pageNum, pageSize, 1, 1 );

		} catch (Exception ex) {
			logger.error(query.toString());
			throw ex;
		}

	}



	/**
	 * 검색 서치 ( 코난검색엔진)(전체검색용)
	 * @param parameterVO 검색 파라메타 정보
	 * @return
	 * @throws Exception 
	 */
	public String getSearchTextForMain(ParameterVO srchParam) throws Exception{

		/************************************/
		/* 변수 초기화 및 카테고리별 설정                   */
		/************************************/
		String scn = srchParam.getScn();						//시나리오 
		String orderBy = "";									//정렬문 (ex : order by regdate desc)
		String orderNm = "";									//정렬명 (ex : 날짜순)
		int pageNum = srchParam.getPageNum();					// 페이지 번호
		int pageSize = srchParam.getPageSize();					// 페이지 사이즈
		String hilightTxt = srchParam.getKwd();					// 하이라이팅 키워드
		String logInfo = "";									// 검색로그
		StringBuffer query = new StringBuffer();				// 검색 쿼리
		String srchFdNm = "text_idx"; 
		/***************************************************************************/
		// kwd값이 있을 경우만 쿼리를 수행함.
		if (srchParam.getKwd() != null && srchParam.getKwd().length() > 0) { 
			int rc = 0;

			if("annot".equalsIgnoreCase(srchParam.getScn())){
				// 주제영상 구분에 대한 전처리 
				query = DCUtil.makeQuery("GUBUN", "S", "", query, "and"); // 'GUBUN' 컬럼의 데이타 타입이 text 이 아니기에 'allword' 빼야 한다.
				query = DCUtil.makeQuery("ANNOT_CLF_CD", srchParam.getSceanGubun(), "", query, "and");
			}
			//정렬
			if ("d".equals(srchParam.getSort())) {
				orderBy = "order by regdate desc";
				orderNm = "최신날짜순";
			} else {
				orderNm = "정확도순";
			}

			//로그포맷
			logInfo = DCUtil.getLogInfo("site", srchParam.getCategory(), "", srchParam.getKwd(), 
					srchParam.getPageNum(), srchParam.getReSrchFlag(), orderNm, 
					srchParam.getRecKwd());

			if (srchParam.getReSrchFlag() && srchParam.getPreKwds() != null) {
				for (int cnt = 0; cnt < srchParam.getPreKwds().length; cnt++) {
					query = DCUtil.makePreQuery(srchFdNm, srchParam.getKwd(), srchParam.getPreKwds(), 
							srchParam.getPreKwds().length,query, "allwordthruindex");
				}
			}

			query = DCUtil.makeQuery(srchFdNm, srchParam.getKwd(), "allword", query, "and");


			//날짜 쿼리 추가
			if (!"".equals(srchParam.getStartDate()) || !"".equals(srchParam.getEndDate())) {
				String startVal = "";
				String endVal = "";
				if (!"".equals(srchParam.getStartDate())) {
					startVal = srchParam.getStartDate()+"000000";
				}
				if (!"".equals(srchParam.getEndDate())) {
					endVal = srchParam.getEndDate()+"999999";
				}

				query = DCUtil.makeRangeQuery("regdate", startVal, endVal, query);
			}

			if (!"".equals(srchParam.getExclusiveKwd()) || srchParam.getExclusiveKwd().length() > 0) {
				query = DCUtil.makeQuery(srchFdNm, srchParam.getExclusiveKwd(), "", query, "and not");
			}
		}
		try {


			return ExportXML.search(scn, query.toString(),orderBy, pageNum, pageSize, 1, 1 );

		} catch (Exception ex) {
			logger.error(query.toString());
			throw ex;
		}

	}

}

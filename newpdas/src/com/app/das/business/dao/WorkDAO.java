package com.app.das.business.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.naming.NamingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.UserRoleStatement;
import com.app.das.business.dao.statement.WorkStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.CartDetailItemDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.ContentsInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.PdaInfoDO;
import com.app.das.business.transfer.SearchDO;
import com.app.das.business.transfer.TransferDO;
import com.app.das.business.transfer.WorkOrdersConditionDO;
import com.app.das.business.transfer.WorkOrdersDO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.business.transfer.WorkStatusDO;
import com.app.das.log.DasPropHandler;
import com.app.das.log.ErrorPropHandler;
import com.app.das.services.TransferDOXML;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CommonUtl;
import com.app.das.util.DBService;
import com.app.das.util.LoggableStatement;
import com.app.das.util.StringUtils;
import com.app.das.util.jutil;
import com.sbs.das.web.NevigatorProxy;
/**
 * 작업현황의 매체변환, 주조송출, 작업지시, 사용제한 승인에 대한 Database 관련 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class WorkDAO extends AbstractDAO 
{
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	private static WorkDAO instance;

	private Logger logger = Logger.getLogger(WorkDAO.class);
	
	/**
	 * A private constructor
	 *
	 */
	private WorkDAO() 
	{
	}

	public static synchronized WorkDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new WorkDAO();
		}
		return instance;
	}

	/**
	 * 작업 현황중 매체변환 목록조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkStatusDO를 포함하고 있는 DataObejct
	 * @throws Exception 
	 */
	public PageDO selectWorkStatusList(WorkStatusConditionDO conditionDO, String workOrdersKind, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(WorkStatement.selectWorkStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, workOrdersKind));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <= ?                                        	\n");
		buf.append(" WITH UR	\n ");

		//Page에 따른 계산을 한다.
		int page = conditionDO.getPage();
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
			//logger.debug("######selectWorkStatusList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, WorkStatement.selectWorkStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, workOrdersKind));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.WORK_LOG_ROW_COUNT;

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
				WorkStatusDO item = new WorkStatusDO();
				item.setSerialNo(			rs.getInt("rownum"));	
				item.setMasterId(      		rs.getString("MASTER_ID")); 
				item.setRegDt(          	rs.getString("ING_REG_DD"));
				item.setReq_cd(          	rs.getString("REQ_CD"));
				item.setEpisNo(          	rs.getString("EPIS_NO"));
				item.setBrdLeng(        	rs.getString("BRD_LENG")); 
				item.setBrdDd(          	rs.getString("BRD_DD")); 
				item.setDataStatCd(     	rs.getString("DATA_STAT_CD"));
				item.setTitle(				rs.getString("TITlE")); 
				item.setClfNm(      		rs.getString("CLF_NM"));
				item.setArchRegDd(     		rs.getString("ARCH_REG_DD"));
				item.setContentCnt(    		rs.getInt("COUNT"));
				item.setLockStatCd(			rs.getString("LOCK_STAT_CD"));
				item.setErrorStatCd(		rs.getString("ERROR_STAT_CD"));
				item.setModrid(				rs.getString("modrid"));

				resultList.add(item);
			}

			//총페이지 수를 계산한다.
			int totalPageCount = totalCount / rowPerPage  + (totalCount % rowPerPage != 0 ? 1 : 0);

			//검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			//계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);
			//총검색된 목록 수
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
	 * 작업 현황중 매체변환 목록조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkStatusDO를 포함하고 있는 DataObejct
	 * @throws Exception 
	 */
	public PageDO selectWorkStatusList_old(WorkStatusConditionDO conditionDO, String workOrdersKind, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(WorkStatement.selectWorkStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, workOrdersKind));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <= ?                                        	\n");
		buf.append(" WITH UR	 \n");

		//Page에 따른 계산을 한다.
		int page = conditionDO.getPage();
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
			//logger.debug("######selectWorkStatusList_old######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, WorkStatement.selectWorkStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, workOrdersKind));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.WORK_LOG_ROW_COUNT;

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
				WorkStatusDO item = new WorkStatusDO();
				item.setSerialNo(			rs.getInt("rownum"));	
				item.setCtId(          		rs.getString("CT_ID")); 
				item.setMasterId(      		rs.getString("MASTER_ID")); //MHCHOI 0109
				item.setCtiId(         		rs.getString("CTI_ID")); 
				item.setPgmNm(          	rs.getString("PGM_NM")); 
				item.setEpisNo(         	rs.getString("EPIS_NO")); 
				item.setRegDt(          	rs.getString("REG_DT")); 
				item.setBrdLeng(        	rs.getString("BRD_LENG")); 
				item.setBrdDd(          	rs.getString("BRD_DD")); 
				item.setDataStatCd(     	rs.getString("DATA_STAT_CD")); 
				item.setCtCla(          		rs.getString("CT_CLA")); 
				item.setIngestEqId(     	rs.getString("INGEST_EQ_ID")); 
				item.setEqNm(           	rs.getString("EQ_NM")); 
				item.setCtiFmt(         		rs.getString("CTI_FMT")); 
				item.setIngestCloseDd(rs.getString("INGEST_CLOSE_DD")); 
				item.setArchSteYn(      rs.getString("ARCH_STE_YN"));				

				resultList.add(item);
			}

			//총페이지 수를 계산한다.
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
	 * 오류 내역을 조회한다.
	 * @param masterId 마스터 ID
	 * @param commonDO 공통정보
	 * @return ErrorRegisterDO 오류정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public ErrorRegisterDO selectErrorRegisterInfo(String masterId, DASCommonDO commonDO) throws Exception
	{
		String query = WorkStatement.selectErrorRegisterInfoQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectErrorRegisterInfo######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(masterId));

			rs = stmt.executeQuery();

			ErrorRegisterDO item = new ErrorRegisterDO();

			if(rs.next())
			{
				item.setCtiId( 				rs.getString("CT_ID"));
				item.setMasterID(			rs.getString("MASTER_ID"));
				item.setWrt(            		rs.getString("WRT")); 
				item.setWorkClf(        	rs.getString("WORK_CLF")); 
				item.setCont(           		rs.getString("ER_CONT"));
				item.setWorkCmCont(  	rs.getString("REACT_CONT")); 
				item.setWorkSeq(        	rs.getString("WORK_SEQ")); 								
				return item;
			}
			else
			{
				item = null;

				return item;
			}

			/*
			else
			{
		        DASException exception = new DASException(ErrorConstants.NOT_EXIST_ERROR_INFO, "해당 오류정보가 존재하지 않습니다.");
		        throw exception;

			}
			 */

		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 오류내역에 대한 작업 재지시 처리를 한다.
	 * @param errorRegisterDO 오류 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateReWorkOrder(ErrorRegisterDO errorRegisterDO, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateReWorkOrder######## con : " + con);
			con.setAutoCommit(false);

			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			// 작업구분이 오류없음일 경우
			if (errorRegisterDO.getWorkClf().equals("000")) {
				// 오류내역을 삭제한다.
				deleteErrorRegisterInfo(con, errorRegisterDO);
				// 메타 데이타 마스터 테이블의 자료상태를 수정한다.
				updateMetaMasterInfo(con, errorRegisterDO, toDateTime, CodeConstants.DataStatusCode.ARRANGE_BEFORE, commonDO);
			} else {
				//오류내역을 저장한다.
				updateErrorRegisterInfo(con, errorRegisterDO, toDateTime, commonDO);

				//작업구분이 인제스트 제지시일 경우
				if(CodeConstants.WorkKind.INGEST_RE_ORDER.equals(errorRegisterDO.getWorkClf()))
				{
					//ERP 테이블 아이템의 인제스트상태를 '오류'로, 인제스트일자를 '' 로 작업순서를 수정한다.
					updateErpTapeItemInfo(con, errorRegisterDO, commonDO);
					//콘텐츠 테이블의 콘텐츠 삭제일자와 사용여부를 'N' 으로 수정한다.
					updateContentsInfo(con, errorRegisterDO, toDateTime, commonDO);
					//메타 데이타 마스터 테이블의 삭제일자, 사용여부, 자료상태를 수정한다.
					updateMetaMasterInfo(con, errorRegisterDO, toDateTime, CodeConstants.DataStatusCode.RE_ORDERS, commonDO);
					//콘텐츠 매핑 테이블에 마스타ID에 해당하는 것들을 삭제한다.
					deleteContentsMappInfo(con, errorRegisterDO, commonDO);
				}
				else
				{
					//메타 데이타 마스터 테이블의 자료상태를 2차아카이브 재지시로 수정한다.
					updateMetaMasterInfo(con, errorRegisterDO, toDateTime, CodeConstants.DataStatusCode.RE_ARCHIVE, commonDO);
				}
			}

			con.commit();
		} 

		catch (Exception e) 
		{

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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}

	}


	/**
	 * 작업지시 목록 조회를 한다.(작업 순위가 DB에 셋팅되어 있지 않는 경우 '3' 보통으로 셋팅한다)
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkOrdersDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectWorkOrdersList(WorkOrdersConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(WorkStatement.selectWorkOrdersListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	\n ");

		//Page에 따른 계산을 한다.
		int page = conditionDO.getPage();
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
			//logger.debug("######selectWorkOrdersList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, WorkStatement.selectWorkOrdersListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.WORK_LOG_ROW_COUNT;

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
				WorkOrdersDO item = new WorkOrdersDO();
				item.setSerialNo(       	rs.getInt("rownum"));
				item.setReqNo(				rs.getString("REQ_NO"));
				item.setPgmNm(          	rs.getString("SCN_TTL"));
				item.setRegDd(          	rs.getString("REG_DD"));
				item.setLen(            		rs.getString("LEN"));
				item.setBrdDd(          	rs.getString("BRD_DD"));
				item.setWorkState(      	rs.getString("WORK_STAT"));
				item.setWorkSeq(        	rs.getString("WORK_SEQ"));
				item.setTapeItemId( 		rs.getString("TAPE_ITEM_ID"));


				resultList.add(item);
			}

			//총페이지 수를 계산한다.
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
	 * 조회된 작업지시 목록에서 작업 순위를 수정한다.
	 * @param workOdersDO 작업 순위를 포함하고 있는 DataObject
	 * @param comonDO 공통정보
	 * @throws Exception 
	 */
	public void updateWorkOrdersList(List workOdersDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateWorkOrdersList######## con : " + con);
			con.setAutoCommit(false);

			for(Iterator i = workOdersDOList.iterator(); i.hasNext();)
			{
				WorkOrdersDO item = (WorkOrdersDO)i.next();

				updateWorkOrders(con, item, commonDO);
			}

			con.commit();
		} 

		catch (Exception e) 
		{
			logger.error(workOdersDOList.toString());

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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 오류 내역 항목의 작업 순위를 수정한다.
	 * @param errorRegisterDO 오류 내역 등록 정보를 포함하는 DataObject
	 * @param comonDO 공통정보
	 * @throws Exception 
	 */
	public void updateErrorItemWorkOrders(ErrorRegisterDO errorRegisterDO, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateErrorItemWorkOrders######## con : " + con);
			con.setAutoCommit(false);

			updateErrorWorkOrders(con, errorRegisterDO);


			con.commit();
		} 

		catch (Exception e) 
		{
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 조회된 콘텐트 목록을 삭제한다.
	 * @param workStatusDOList 화면상의 콘테트 데이타 리스트
	 * @throws Exception 
	 */
	public void deleteContentItemList(String  masterIdGrp) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			String master_id_grp = masterIdGrp;
			String[] arrayMasterId = master_id_grp.split(",");
			for(int i =0;i < arrayMasterId.length; i++)
			{
				if(!arrayMasterId[i].equals("")){
					deleteContentItem(con, arrayMasterId[i]);
				}
			}

			con.commit();
		} 
		catch (Exception e) 
		{
			logger.error(masterIdGrp);

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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectApproveCartList(DASCommonDO commonDO) throws Exception
	{
		String query = WorkStatement.selectApproveCartListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectApproveCartList######## con : " + con);
			//logger.debug("selectApproveCartListQuery=="+query);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, CodeConstants.CartStatus.APPROVE);
			stmt.setString(++index, CodeConstants.CartStatus.APPROVE_REJECT);
			stmt.setString(++index, CodeConstants.CartStatus.DOWNLOAD);
			stmt.setString(++index, CodeConstants.CartStatus.DOWNLOAD_COMPLET);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("REQ_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				item.setScreenQuality(		rs.getString("VD_QLTY_NM"));
				item.setLengthBreadthRate(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setAppCont(        		rs.getString("APP_CONT"));
				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));				

				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.(데정팀일경우)
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectDownloadRequestList(CartItemDO cartItemDO) throws Exception 
	{

		String query = WorkStatement.selectDownloadRequestListQuery(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{


			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDownloadRequestList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			if(!cartItemDO.getDown_day().equals("")){
				stmt.setString(++index,cartItemDO.getFromdate()+"000000");
				stmt.setString(++index,cartItemDO.getEnddate()+"99999");
			}
			/*
			if(!cartItemDO.getOut_user().equals("")){
				stmt.setString(++index,cartItemDO.getOut_user());
			}
			 */
			if(!cartItemDO.getDownSubj().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getDownSubj()+"%");
			}
			if(!cartItemDO.getReq_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReq_id()+"%");
			}
			if(!cartItemDO.getReqNm().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReqNm()+"%");
			}
			if(!cartItemDO.getMedia_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getMedia_id()+"%");
			}
			if(!cartItemDO.getDown_gubun().equals("")){
				stmt.setString(++index,cartItemDO.getDown_gubun());
			}
			if(!cartItemDO.getCompany_gubun().equals("")){
				stmt.setString(++index,cartItemDO.getCompany_gubun());
			}
			if(!cartItemDO.getDown_status().equals("")){
				stmt.setString(++index,cartItemDO.getDown_status());
			}
			if(cartItemDO.getCti_id() !=0){
				stmt.setLong(++index,cartItemDO.getCti_id());
			}



			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));

				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));

				item.setStorage(  	rs.getString("FILE_PATH"));
				item.setDown_gubun_nm( rs.getString("DESC"));
				item.setConm( rs.getString("CONM"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/*public List selectDownloadRequestList(CartItemDO cartItemDO) throws DASException
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{


			con = DBService.getInstance().getConnection();

			String query = WorkStatement.selectDownloadRequestListQuery(cartItemDO);
			stmt = con.prepareStatement(query);

			int index = 0;
			if(!cartItemDO.getDown_day().equals("")){
				stmt.setString(++index,cartItemDO.getFromdate()+"000000");
				stmt.setString(++index,cartItemDO.getEnddate()+"99999");
			}
			if(!cartItemDO.getOut_user().equals("")){
				stmt.setString(++index,cartItemDO.getOut_user());
			}

			if(!cartItemDO.getDownSubj().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getDownSubj()+"%");
				}
			if(!cartItemDO.getReq_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReq_id()+"%");
				}
			if(!cartItemDO.getReqNm().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReqNm()+"%");
				}
			if(!cartItemDO.getMedia_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getMedia_id()+"%");
				}

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));

				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));


				item.setDown_gubun_nm( rs.getString("DESC"));

				resultList.add(item);
			}
			return resultList;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}*/

	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param cartNo 카트번호
	 * @param commonDO 공통정보
	 * @return List CartDetailItemDO를 포함하고 있는 DO
	 * @throws Exception 
	 */
	public List selectApproveCartDetailsList(String cartNo, DASCommonDO commonDO) throws Exception
	{

		String query = WorkStatement.selectApproveCartDetailsListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectApproveCartDetailsList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartDetailItemDO item = new CartDetailItemDO();
				item.setCartNo(         	rs.getBigDecimal("CART_NO"));
				item.setSeq(					rs.getInt("CART_SEQ"));
				item.setCt_id(				rs.getString("CT_ID"));
				item.setPgmNm(          	rs.getString("PGM_NM"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setS_frame(            rs.getString("S_FRAME"));
				item.setDuration(          	rs.getString("DURATION"));
				item.setApp_cont(        	rs.getString("APP_CONT"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRistClfNm(			rs.getString("RIST_CLF_NM"));

				resultList.add(item);
			}
			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.(데정팀인경우)
	 * @param cartNo
	 * @return
	 * @throws Exception 
	 */
	public List selectDownloadRequestDetailsList(String cartNo) throws Exception
	{

		String query = WorkStatement.selectDownloadRequestDetailsListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDownloadRequestDetailsList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setCtiId(				rs.getLong("CTI_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				String epis = rs.getString("EPIS_NO");
				if(epis.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(            rs.getString("EPIS_NO"));
				}
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setDown_vol(           rs.getString("DOWN_VOL"));
				item.setDown_typ_nm(        rs.getString("DOWN_TYP_NM"));
				//				item.setPgm_nm(          	rs.getString("PGM_NM"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setApp_cont(        	rs.getString("APP_CONT"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setOutsourcing_yn(     rs.getString("OUTSOURCING_YN"));
				item.setDown_gubun_nm(rs.getString("DESC"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setTrans_vol(rs.getString("TRANS_VOL"));
				item.setVd_qlty_nm(rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDown_stat(rs.getString("DOWN_STAT"));
				item.setReq_cont(rs.getString("req_cont"));
				item.setJob_status(rs.getString("status"));
				item.setCtgrLCd(rs.getString("ctgr_l_cd"));
				item.setFm_dt(rs.getString("fm_dt"));
				item.setMedia_id(rs.getString("MEDIA_ID"));
				item.setMasterId(rs.getLong("master_id"));

				item.setFl_nm(rs.getString("WRK_FILE_NM"));
				String hr = rs.getString("FL_PATH");
				if(hr.matches(".*net_mp4.*")){
					item.setPath("cs_net_mp4/"+rs.getString("FL_PATH"));

				}else if(hr.matches(".*mp4.*")){
					item.setPath("cs_mp4/"+rs.getString("FL_PATH"));

				}else{
					item.setPath(rs.getString("FL_PATH"));
				}
				//item.setPath(rs.getString("FL_PATH"));
				item.setApprove_nm(rs.getString("approveid"));
				item.setChennel_nm(rs.getString("CHENNEL_NM"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateCartApproveList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateCartApproveList######## con : " + con);
			con.setAutoCommit(false);

			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = cartItemDOList.iterator(); i.hasNext();)
			{
				CartItemDO item = (CartItemDO)i.next();

				updateCartApprove(con, item, toDateTime, commonDO);
			}

			con.commit();
		} 

		catch (Exception e) 
		{
			logger.error(cartItemDOList.toString());

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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리전 확인한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public boolean checkCartApproveList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		boolean sResult = true;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######checkCartApproveList######## con : " + con);
			con.setAutoCommit(false);

			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = cartItemDOList.iterator(); i.hasNext();)
			{
				CartItemDO item = (CartItemDO)i.next();

				sResult = checkCartApprove(con, item, toDateTime, commonDO);
				if(!sResult)break; // cartItemDOList 중에 한건이라도 사용제한에 확인문구가 없는것 걸러내기.
			}
			//			con.commit();
			return sResult;
		} 

		catch (Exception e) 
		{
			logger.error(cartItemDOList.toString());

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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, null, con);
		}

	}

	private void updateWorkOrders(Connection con, WorkOrdersDO workOrdersDO, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.D_TAPEITEM_TBL set ");
		buf.append("\n  WORK_SEQ = ? ");
		buf.append("\n where TAPE_ITEM_ID = ? ");		

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, workOrdersDO.getWorkSeq());
			stmt.setString(++index, workOrdersDO.getTapeItemId());

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	private void updateErrorWorkOrders(Connection con, ErrorRegisterDO errorRegisterDO) throws SQLException
	{
		//인제스트 재지시 및 2차 아카이브 재지시
		updateErrorRegTable(con, errorRegisterDO);	

		// 인제스트 재지시 일경우 만 테입 아이템 테이블 업데이트
		if(errorRegisterDO.getWorkClf().equals(DASBusinessConstants.RE_INGEST)) {
			StringBuffer buf = new StringBuffer();
			buf.append("\n update DAS.D_TAPEITEM_TBL set ");
			buf.append("\n  WORK_SEQ = ? ");
			buf.append("\n where TAPE_ITEM_ID =  ");
			buf.append("\n (select from ");
			buf.append("\n meta.TAPE_ITEM_ID ");
			buf.append("\n from DAS.METADAT_MST_TBL meta ");
			buf.append("\n where  meta.MASTER_ID= ? ) ");
			buf.append("\n WITH UR	 "); 		

			PreparedStatement stmt = null;
			try 
			{

				stmt = con.prepareStatement(buf.toString());

				int index = 0;

				stmt.setString(++index, errorRegisterDO.getWorkSeq());
				stmt.setString(++index, errorRegisterDO.getMasterId());

				stmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				logger.error(buf.toString());

				throw e;
			}
			finally
			{
				try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
			}
		}

	}

	private void updateErrorRegTable(Connection con, ErrorRegisterDO errorRegisterDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ERROR_RGST_TBL set ");
		buf.append("\n  work_seq = ? ");
		buf.append("\n where master_id = ? ");
		buf.append("\n WITH UR	 "); 

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, errorRegisterDO.getWorkSeq());
			stmt.setString(++index, errorRegisterDO.getMasterId());

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	//	 현재 카트의 요구자 아이디를 가져온다.
	private String getReqUsrID(long cartNo) throws Exception
	{
		StringBuffer buf = new StringBuffer();		
		buf.append("\n select REQ_USRID FROM  DAS.DOWN_CART_TBL where CART_NO = "+cartNo + " with ur \n");

		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();			
			//logger.debug("######getReqUsrID######## con : " + con);

			try
			{
				psmt = con.prepareStatement(buf.toString());
				rs = psmt.executeQuery();
				rs.next();

				return rs.getString("REQ_USRID");
			}
			catch (SQLException ex)
			{
				throw ex;
			}
			finally
			{
				release(rs, psmt, null);
			}
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			
			throw e;
		}
		finally
		{
			release(rs, psmt, con);
		}
	}

	private void updateCartApprove(Connection con, CartItemDO cartItemDO, String toDateTime, DASCommonDO comonDO) throws Exception
	{
		// 요청자를 가져온다.
		long cartNo = cartItemDO.getCartNo();

		String strReqUsrID = getReqUsrID(cartNo);
		String _strg_loc = dasHandler.getProperty("WIN_NEARLINE")+"/restore/" + strReqUsrID + "/" + cartNo;
		_strg_loc = _strg_loc.substring(1, _strg_loc.length());

		int updateCount = 0;

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DOWN_CART_TBL set ");
		buf.append("\n 	CART_STAT = ?,  ");
		if(DASBusinessConstants.ApproveFlag.APPROVE.equals(cartItemDO.getUseLimitFlag()))
		{
			buf.append("\n 	APP_DT = ?,  ");	
		}
		buf.append("\n 	APP_CONT = ?, ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ?, ");   
		buf.append("\n  STRG_LOC = ? " );
		buf.append("\n where CART_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			if(DASBusinessConstants.ApproveFlag.APPROVE.equals(cartItemDO.getUseLimitFlag()))
			{
				stmt.setString(++index, CodeConstants.CartStatus.APPROVE);
				stmt.setString(++index, toDateTime);

			}
			else
			{
				stmt.setString(++index, CodeConstants.CartStatus.APPROVE_REJECT);
			}
			stmt.setString(++index, cartItemDO.getAppCont());
			stmt.setString(++index, toDateTime);
			stmt.setString(++index, comonDO.getUserId());
			stmt.setString(++index, _strg_loc);
			stmt.setLong(++index, cartItemDO.getCartNo());


			updateCount = stmt.executeUpdate();

			if(DASBusinessConstants.ApproveFlag.APPROVE.equals(cartItemDO.getUseLimitFlag())) {
				// 다운로드 제한 여부가 No이 웹 서비스를 호출하여 다운로드 카트를 승인 없이  다운로드 한다
				//				ServiceNevigatorIF port = new ServiceNevigatorService_Impl().getServiceNevigatorIFPort();

				//String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
				//xml = xml + "<das>";
				//xml = xml + "<cart_no>" + String.valueOf(cartItemDO.getCartNo()) + "</cart_no>";
				//xml = xml + "</das>";

				//port.downloadService(xml);
				//				port.downloadService(String.valueOf(cartItemDO.getCartNo()));
				logger.debug("다운 승인시 웹 서비스 콜 : " + String.valueOf(cartItemDO.getCartNo()));
			}

			if (updateCount == 0){
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
		} 
		
		catch (Exception e) 
		{
			logger.error(buf.toString());
			
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO 자동 생성된 catch 블록
				e1.printStackTrace();
			}

			
			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	private boolean checkCartApprove(Connection con, CartItemDO cartItemDO, String toDateTime, DASCommonDO comonDO) throws Exception
	{
		// 요청자를 가져온다.
		long cartNo = cartItemDO.getCartNo();

		boolean sResult = true;
		String strReqUsrID = getReqUsrID(cartNo);
		StringBuffer buf = new StringBuffer();
		buf.append(" \n select                                               ");
		buf.append(" \n  	cont.CART_NO,                                    ");
		buf.append(" \n  	cont.CART_SEQ,                                   ");
		buf.append(" \n  	                                                 ");
		buf.append(" \n  	cont.APP_CONT,                                   ");
		buf.append(" \n 	cont.RIST_CLF_CD                                 ");
		buf.append(" \n  from DAS.CART_CONT_TBL cont                         ");
		buf.append(" \n  where cont.CART_NO = ?                              ");
		buf.append(" \n  			 and rist_clf_cd <> ''  and app_cont = ''");
		buf.append(" \n  WITH UR                                             ");


		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setLong(++index, cartItemDO.getCartNo());


			rs = stmt.executeQuery();

			while(rs.next())
			{
				sResult = false;					
			}
			return sResult;

		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			
			throw e;
		}
		finally
		{
			release(rs, stmt, null);
		}

	}

	private void deleteContentItem(Connection con, String master_id) throws Exception
	{
		List resultList = new ArrayList();
		String filePath = null;
		String contentId = null;
		jutil jt=new jutil();
		String masterId = master_id;
		String strTii = "";
		// select content IDs from mapping table using master ID

		StringBuffer buf = new StringBuffer();
		StringBuffer buf1 = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();
		StringBuffer buf3 = new StringBuffer();
		StringBuffer buf4 = new StringBuffer();
		StringBuffer buf5 = new StringBuffer();
		StringBuffer buf6 = new StringBuffer();
		StringBuffer buf7 = new StringBuffer();
		StringBuffer buf8 = new StringBuffer();
		
		buf.append("\n select CT_ID from DAS.CONTENTS_MAPP_TBL where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, masterId);
			rs = stmt.executeQuery();

			while(rs.next())
			{
				String str = new String();
				str = rs.getString("CT_ID");						

				resultList.add(str);
			}

		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(rs, stmt, null);
		}


		//2 for all content ids 
		//  from content_instance table delete item using content ID
		//  and delete actual file from storage using file path		//  
		//  change CONTENT_TBL USE_YN field to N and insert delete date to DEL_DD
		for(Iterator i = resultList.iterator(); i.hasNext();)
		{
			contentId = (String)i.next();
			String wrk_file_nm="";
			String cti_fmt="";
			String arch_ste_yn="";
			
			buf1.append("\n select FL_PATH,CTI_ID,WRK_FILE_NM,CTI_FMT,ARCH_STE_YN from DAS.CONTENTS_INST_TBL where CT_ID = ? ");
			buf1.append("\n WITH UR	 ");

			// 콘텐트 인스탄스 레코드에서 파일경로를 구하여 실제 파일을 스토어리지에서 작제한다
			stmt = null;
			rs = null;
			try 
			{
				//stmt = LoggableStatement.getInstance(con, buf1.toString());
				stmt = con.prepareStatement(buf1.toString());

				int index = 0;
				stmt.setString(++index, contentId);
				rs = stmt.executeQuery();

				while(rs.next())
				{
					filePath = rs.getString("FL_PATH");	
					long cti_id = rs.getLong("CTI_ID"); 
					wrk_file_nm=rs.getString("WRK_FILE_NM");
					arch_ste_yn=rs.getString("ARCH_STE_YN");
					cti_fmt=rs.getString("CTI_FMT");

					//					lw.log_info("deleteContentItem FL_PATH----------------"+filePath);
					//					lw.log_info("deleteContentItem CT_ID----------------"+cti_id);

					if(cti_fmt.startsWith("1")){ //09.07.16 추가(jsh)
						// SGL에 삭제 요청한다. 테스트해볼 수 있는 자료가 없어서 일단 코드만 넣어둔다.
						try
						{
							//						ServiceNevigatorIF port = new ServiceNevigatorService_Impl().getServiceNevigatorIFPort();
							StringBuffer xml = new StringBuffer();
							xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
							xml.append("<das>");
							xml.append("<info>");
							xml.append("<req_id>D080009</req_id>");
							xml.append("<das_eq_id>4</das_eq_id>");
							xml.append("<das_eq_ps_cd>006</das_eq_ps_cd>");
							xml.append("<cti_id>");
							xml.append(cti_id);
							xml.append("</cti_id>");
							xml.append("<priority>3</priority>");
							xml.append("<sgl_group_nm>m2_%25</sgl_group_nm>");
							xml.append("<job_id>007</job_id>");
							xml.append("<som>0</som>");
							xml.append("<eom>0</eom>");
							xml.append("<file_path>%20</file_path>");
							xml.append("<cart_no></cart_no>");
							xml.append("<cart_seq></cart_seq>");
							xml.append("</info>");
							xml.append("<db_table>");
							xml.append("<pgm_info_tbl>");
							xml.append("<pgm_id>0</pgm_id><media_cd>%20</media_cd><chan_cd>%20</chan_cd></pgm_info_tbl>");
							xml.append("<metadat_mst_tbl>");
							xml.append("<master_id>0</master_id>");
							xml.append("<mcuid></mcuid>");
							xml.append("</metadat_mst_tbl>");
							xml.append("<contents_tbl>");
							xml.append("<ct_id>");
							xml.append(contentId);
							xml.append("</ct_id>");
							xml.append("<mcu_seq>0</mcu_seq>");
							xml.append("</contents_tbl>");
							xml.append("<contents_inst_tbl>");
							xml.append("<cti_id>");
							xml.append(cti_id);
							xml.append("</cti_id>");
							xml.append("<ct_id>");
							xml.append(contentId);
							xml.append("</ct_id>");
							xml.append("</contents_inst_tbl>");
							xml.append("</db_table>");
							xml.append("</das>");

							//						port.archiveService(xml.toString());

							//						lw.log_info("deleteContentItem SGL Delete Request----------------"+xml.toString());


						}
						catch (Exception e)
						{
							e.printStackTrace();				
							//DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "[deleteContentItem] SGL에 삭제 요청 중 에러", e);
							//throw exception;
						}

					}
					filePath = "/"+filePath;
					//					lw.log_info("deleteContentItem filepath----------------"+filePath+"/"+wrk_file_nm);

					//mp4 삭제.....
					//추가정보 : 정상호   07.07  

					try{

						if(wrk_file_nm.endsWith(".WMV") || wrk_file_nm.endsWith(".wmv")){


							if(filePath!=null && !filePath.equals("")){
								if(jt.deleteAdapter(new File(filePath))==0){
									//				          	 lw.log_info("deleteContentItem mp4 delete --------------"+filePath);
								}else{
									//				    	     lw.log_info("deleteContentItem mp4 delete fail----------------"+filePath);
								}
							}
						}else{

							if(filePath!=null && !filePath.equals("")){ 
								if(jt.isFileCheck(filePath,wrk_file_nm)){
									File fs=new File(filePath+"/"+wrk_file_nm);
									fs.delete();
									//						          lw.log_info("deleteContentItem mp2 delete----------------"+filePath+"/"+wrk_file_nm);
								}else{
									//						          lw.log_info("deleteContentItem mp2 no file----------------"+filePath+"/"+wrk_file_nm);

								}
							}
						}

					}catch (Exception e) {
						e.printStackTrace();
					}	


				}

			} 
			catch (SQLException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

				throw e;
			}
			finally
			{
				release(null, stmt, null);
			}




			// 콘텐트 인스탄스 레코드 삭제			
		
			buf2.append("\n DELETE FROM DAS.CONTENTS_INST_TBL ");
			buf2.append("\n where CT_ID = ? ");
			buf2.append("\n WITH UR	 ");
			//			lw.log_info("deleteContentItem content instance delete query----------------"+buf2.toString());
			stmt = null;
			rs = null;
			try 
			{
				//stmt = LoggableStatement.getInstance(con, buf2.toString());
				stmt = con.prepareStatement(buf2.toString());

				int index = 0;
				stmt.setString(++index, contentId);
				stmt.executeUpdate();							
			} 
			catch (SQLException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

				throw e;
			}
			finally
			{
				release(null, stmt, null);
			}


			// 콘테트 테이블 USE_YN필드와 삭제날짜 필드를 갱신한다 
			
			buf3.append("\n update DAS.CONTENTS_TBL set USE_YN = 'N', DEL_DD = ? ");
			buf3.append("\n where CT_ID = ? ");
			buf3.append("\n WITH UR	 ");
			//			lw.log_info("deleteContentItem CONTENTS_TBL update query----------------"+buf3.toString());
			stmt = null;
			rs = null;
			try 
			{
				//stmt = LoggableStatement.getInstance(con, buf3.toString());
				stmt = con.prepareStatement(buf3.toString());

				int index = 0;
				stmt.setString(++index, CalendarUtil.getToday());
				stmt.setString(++index, contentId);
				stmt.executeUpdate();
			} 
			catch (SQLException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

				throw e;
			}
			finally
			{
				release(null, stmt, null);
			}


		} // end for


		//	메타데이타 마스터 테이블의 USE_YN필드와 삭제날짜 필드를 갱신한다	
		
		buf4.append("\n update DAS.METADAT_MST_TBL set USE_YN = 'N', DEL_DD = ? ");
		buf4.append("\n where MASTER_ID = ? ");
		buf4.append("\n WITH UR	 ");
		//		lw.log_info("deleteContentItem content instance METADAT_MST_TBL   update query----------------"+buf4.toString());
		stmt = null;
		rs = null;
		try 
		{
			//stmt = LoggableStatement.getInstance(con, buf4.toString());
			stmt = con.prepareStatement(buf4.toString());

			int index = 0;
			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index,  masterId);
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

		// 코너 테이블 레코드를 삭제한다.
		
		buf5.append("\n delete from DAS.CORNER_TBL where MASTER_ID = ? ");
		buf5.append("\n WITH UR	 ");
		//		lw.log_info("deleteContentItem  CORNER_TBL delete query----------------"+buf5.toString());
		stmt = null;
		rs = null;
		try 
		{
			//stmt = LoggableStatement.getInstance(con, buf5.toString());
			stmt = con.prepareStatement(buf5.toString());

			int index = 0;
			stmt.setString(++index, masterId);
			stmt.executeUpdate();							
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

		// 콘텐트 멥핑 테이블 레코드를 삭제한다
		
		buf6.append("\n update DAS.CONTENTS_MAPP_TBL set DEL_DD = ? ");
		buf6.append("\n where MASTER_ID = ? ");
		buf6.append("\n WITH UR	 ");
		//		lw.log_info("deleteContentItem  CONTENTS_MAPP_TBL update query----------------"+buf5.toString());
		stmt = null;
		rs = null;
		try 
		{
			//stmt = LoggableStatement.getInstance(con, buf6.toString());
			stmt = con.prepareStatement(buf6.toString());

			int index = 0;
			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index, masterId);
			stmt.executeUpdate();							
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

		/**
		 *  ERP dat.d_tape_item_tbl 에 tape_item_id 를 주기 위해서
		 *  master_id 를 통해서 tape_item_id를 구한다.
		 *  encode_yn,encode_dd 를 null 값으로 
		 *  20090903 dekim
		 */
	
		buf7.append("\n select TAPE_ITEM_ID from DAS.METADAT_MST_TBL where MASTER_ID = ? ");
		buf7.append("\n fetch first 1 rows only ");
		stmt = null;
		rs = null;
		try 
		{
			//stmt = LoggableStatement.getInstance(con, buf7.toString());
			stmt = con.prepareStatement(buf7.toString());

			int index = 0;
			stmt.setString(++index, masterId);
			rs = stmt.executeQuery();

			while(rs.next())
			{
				strTii = rs.getString("TAPE_ITEM_ID");						
			}

		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}
		/**
		 *  ERP dat.d_tape_item_tbl 에 tape_item_id 를 찾아서 encode_yn,encode_dd 를 null 값으로 
		 *  20090903 dekim
		 */
	
		buf8.append("\n UPDATE DAT.D_TAPEITEM_TBL  SET");
		buf8.append("\n ENCODE_DD = '' ");
		buf8.append("\n , ENCODE_YN = '' ");
		buf8.append("\n WHERE TAPE_ITEM_ID = ? ");	

		Connection con8 = null;
		stmt = null;

		try 
		{
			con8 = DBService.getInstance().getErpConnection();

			//stmt = LoggableStatement.getInstance(con8, buf8.toString());
			stmt = con8.prepareStatement(buf8.toString());

			int index = 0;			
			stmt.setString(++index, strTii);

			int updateCount = stmt.executeUpdate();

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			logger.error(buf1.toString());
			logger.error(buf2.toString());
			logger.error(buf3.toString());
			logger.error(buf4.toString());
			logger.error(buf5.toString());
			logger.error(buf6.toString());
			logger.error(buf7.toString());
			logger.error(buf8.toString());

			throw e;
		}
		finally
		{
			release(null, stmt, con8);
		}
		//		
		//3 change METADATA_MST_TBL USE_YN to N and insert delete date to DEL_DD
		//
		//4 Find CN_ID using Master ID from COMENT_MAPP table
		//  and delete CORNER_TBL entry for the corner ID 

		// 5 Delete CONTENTS_MAPP_TBL entry for the master ID 
	}

	/**
	 * 오류등록 테이블을 정보를 수정한다.(재지시)
	 * @param con
	 * @param errorRegisterDO
	 * @param dateTime
	 * @param comonDO
	 * @throws SQLException
	 */
	private void updateErrorRegisterInfo(Connection con, ErrorRegisterDO errorRegisterDO, String dateTime, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ERROR_RGST_TBL set ");
		buf.append("\n 	WORK_CLF = ?, ");
		buf.append("\n 	REACT_CONT = ?, ");
		buf.append("\n 	WORK_SEQ = ?, ");
		buf.append("\n 	MODRID = ?, ");
		buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, errorRegisterDO.getWorkClf());
			stmt.setString(++index, errorRegisterDO.getWorkCmCont());
			stmt.setString(++index, errorRegisterDO.getWorkSeq());
			stmt.setString(++index, dateTime);
			stmt.setString(++index, comonDO.getUserId());
			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 작업 재지시에서 테이프아이템 테이블을 수정한다.
	 * @param con
	 * @param errorRegisterDO
	 * @param comonDO
	 * @throws SQLException
	 */
	private void updateErpTapeItemInfo(Connection con, ErrorRegisterDO errorRegisterDO, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.D_TAPEITEM_TBL set ");
		buf.append("\n 	WORK_SEQ = ?, ");
		buf.append("\n 	INGEST_STATUS = ?,  ");
		buf.append("\n 	INGEST_DD = ? ");
		buf.append("\n where TAPE_ITEM_ID = (select TAPE_ITEM_ID from DAS.METADAT_MST_TBL where MASTER_ID = ?) ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, errorRegisterDO.getWorkSeq());
			stmt.setString(++index, CodeConstants.IngestStatus.ERROR);
			stmt.setString(++index, Constants.BLANK);
			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 콘텐츠 테이블의 삭제일자. 사용여부를 수정한다.
	 * @param con
	 * @param errorRegisterDO
	 * @param dateTime
	 * @param comonDO
	 * @throws SQLException
	 */
	private void updateContentsInfo(Connection con, ErrorRegisterDO errorRegisterDO, String dateTime, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.CONTENTS_TBL set ");
		buf.append("\n 	DEL_DD = ?, ");
		buf.append("\n 	USE_YN = ?, ");
		buf.append("\n 	MODRID = ?, ");
		buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where CT_ID in (select CT_ID from DAS.CONTENTS_MAPP_TBL where MASTER_ID = ?) ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index, DASBusinessConstants.YesNo.NO);
			stmt.setString(++index, comonDO.getUserId());
			stmt.setString(++index, dateTime);
			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 메타마스타 테이블의 삭제일자, 사용여부, 자료상태를 수정한다.
	 * @param con
	 * @param errorRegisterDO
	 * @param dateTime
	 * @param comonDO
	 * @throws SQLException
	 */
	private void updateMetaMasterInfo(Connection con, ErrorRegisterDO errorRegisterDO, String dateTime, String dataStateCode, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL set ");
		/*
		if(CodeConstants.DataStatusCode.ARRANGE_BEFORE.equals(dataStateCode))
		{
			buf.append("\n 	DEL_DD = ?, ");
			buf.append("\n 	USE_YN = ?, ");
		}
		 */
		buf.append("\n 	DATA_STAT_CD = (select case when data_stat_cd = '002' then '001' else data_stat_cd end as data_stat_cd from das.metadat_mst_tbl where master_id = ?), ");
		if(CodeConstants.DataStatusCode.ARRANGE_BEFORE.equals(dataStateCode)) {
			buf.append("\n 	ERROR_STAT_CD = '000', ");
		} else if(CodeConstants.DataStatusCode.RE_ORDERS.equals(dataStateCode)) {
			buf.append("\n 	ERROR_STAT_CD = '001', ");
		} else {
			buf.append("\n 	ERROR_STAT_CD = '002', ");
		}
		//	buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			/*
			if(CodeConstants.DataStatusCode.ARRANGE_BEFORE.equals(dataStateCode))
			{
				stmt.setString(++index, CalendarUtil.getToday());
				stmt.setString(++index, DASBusinessConstants.YesNo.NO);
			}
			 */
			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));
			//	stmt.setString(++index, dateTime);
			stmt.setString(++index, comonDO.getUserId());
			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 특정 마스타ID에 해당되는 매핑 테이블의 정보를 삭제한다. 
	 * @param con
	 * @param errorRegisterDO
	 * @param comonDO
	 * @throws SQLException
	 */
	private void deleteContentsMappInfo(Connection con, ErrorRegisterDO errorRegisterDO, DASCommonDO comonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.CONTENTS_MAPP_TBL ");
		buf.append("\n where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 카트번호와 선택된 카트내 순번으로 삭제를 한다.
	 * @param cartNo 카트번호
	 * @param checkList 카트내 순번 목록
	 * @param comonDO
	 * @throws Exception 
	 * @throws SQLException
	 */
	public void deleteCartDetailApproveList(String cartNo, String checkList, DASCommonDO comonDO) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		StringBuffer buf = new StringBuffer();
		try{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteCartDetailApproveList######## con : " + con);
			con.setAutoCommit(false);


			buf.append("\n delete from DAS.CART_CONT_TBL ");
			buf.append("\n where CART_NO = " + cartNo + " AND CART_SEQ IN (" + checkList + ")");
			buf.append("\n WITH UR	 ");

			stmt = con.prepareStatement(buf.toString());
			count = stmt.executeUpdate();

			if (count > 0) {
				result = true;
			}

			con.setAutoCommit(true);


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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}


	/**
	 * 카트번호와 선택된 카트내 순번으로 수정을 한다.
	 * @param cartNo 카트번호
	 * @param checkList 카트내 순번 목록
	 * @param comonDO
	 * @throws Exception 
	 * @throws SQLException
	 */
	public void updateCartDetailApproveList(String cartNo, String checkList, String app_cont, DASCommonDO comonDO) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		StringBuffer buf = new StringBuffer();
		try{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateCartDetailApproveList######## con : " + con);
			con.setAutoCommit(false);


			buf.append("\n update DAS.CART_CONT_TBL set app_cont = '" + app_cont + "' ");
			buf.append("\n where CART_NO = " + cartNo + " AND CART_SEQ = " + checkList );
			buf.append("\n WITH UR	 ");

			stmt = con.prepareStatement(buf.toString());
			count = stmt.executeUpdate();

			if (count > 0) {
				result = true;
			}

			con.setAutoCommit(true);


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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}

	/**
	 * 다운로드 일반승인상태 변경 및 승인사유 입력
	 * @param _do
	 * @throws Exception 
	 */
	public int updateDownloadRequestDetailList(CartContDO _do) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		BufferedWriter bw = null;
		FileWriter fw = null;
		StringBuffer buf = new StringBuffer();

		try{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDownloadRequestDetailList######## con : " + con);
			con.setAutoCommit(false);


			buf.append("\n update DAS.CART_CONT_TBL set app_cont = ? , DOWN_STAT = ? ");
			buf.append("\n ,approveid =  ? "  );
			buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
			buf.append("\n WITH UR	 ");

			int cnt = 0;
			stmt = con.prepareStatement(buf.toString());
			stmt.setString(++cnt,_do.getApp_cont());
			ContentsInfoDO getContDO = externalDAO.getFl_path(_do.getCartNo(),_do.getCartSeq());
			if(_do.getDown_stat().equals( CodeConstants.CartStatus2.APPROVE)){
				stmt.setString(++cnt, CodeConstants.CartStatus2.DOWNLOAD);

			}else if(_do.getDown_stat().equals( CodeConstants.CartStatus2.CANCLE)){
				stmt.setString(++cnt, CodeConstants.CartStatus2.CANCLE);
			}else if(!getContDO.getFl_path().equals("")){
				stmt.setString(++cnt, CodeConstants.CartStatus2.DOWNLOAD_COMPLET);	
			}else {
				stmt.setString(++cnt, CodeConstants.CartStatus2.DOWNLOAD);
			}
			stmt.setString(++cnt,_do.getRegrId());
			stmt.setLong(++cnt, _do.getCartNo());
			stmt.setLong(++cnt, _do.getCartSeq());

			count = stmt.executeUpdate();


			con.setAutoCommit(true);
			// ifcms의 거절건인경우는 ifcms 정보를 알려준다
			if(_do.getDown_stat().equals("003")){
				IfCmsArchiveDO item3 = new IfCmsArchiveDO();

				item3 = getIdsForCart_no(_do.getCartNo(),_do.getCartSeq());  
				String date = item3.getComplete_dt();

				SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");

				Date d1;
				try {
					d1 = df1.parse(date);
					date = df2.format(d1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!item3.getCallback_url().equals("")){
					//http 클라이언트 호출로 완료되었음을 호출한다.
					HttpClient client = new HttpClient();
					GetMethod method = null;

					logger.debug("###################################callbackurl  " +item3.getCallback_url()+"&cmsid=das"+"&wfid="+item3.getCart_no()+"&uid="+item3.getCti_idForHigh()+"&state=error&time="+date);
					method = new GetMethod(item3.getCallback_url()+"&cmsid=das"+"&wfid="+item3.getCart_no()+"&uid="+item3.getCti_idForHigh()+"&state=error&time="+date);

					int status = 0;
					String callresult = "";
					try {
						status = client.executeMethod(method);
						callresult = method.getResponseBodyAsString();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}


			if(_do.getDown_stat().equals(CodeConstants.CartStatus2.APPROVE)){
				if (logger.isDebugEnabled()) 
				{
					logger.debug("[Start : WebService Call To WAS for DownloadCart : CartNo = " + _do.getCartNo() + "]");
				}

				//다운로드 제한 여부가 No이 웹 서비스를 호출하여 다운로드 카트를 승인 없이  다운로드 한다
				//cart_cont_tbl의 rist_yn이 N인 값만 던진다
				//String xml = externalDAO.getDownloadXml(_do);//<- 카트번호를 받아 소속 seq list로 보냄
				_do.setDtl_type(getCocdInfo(_do.getCartNo(),_do.getCartSeq()));

				String xml = externalDAO.getNewDownloadXmlFormat(_do);//카트번호, 카트 순번을 받아 처리 한건씩.
				logger.debug(xml);


				/**
				 * DTL 이관 전 클립 & 스토리지에 존재 한다면 DAS-TM에 전송 요청  시작
				 */
				// DTL 이관 전 클립 & 스토리지에 존재 분기시작.
				CartContDO tmpCartContDO  = externalDAO.getStLocClip(externalDAO.selectCartContInfo(_do.getCartNo(), _do.getCartSeq()).getCtId());
				if(!CommonUtl.isEmptyString(tmpCartContDO.getFl_path())){
					/**
					 * PDS,NDS,계열사 해당하는 다운로드 요청만 DAS-TM 전송요청을 하게 됨(20110512:dekim)
					 */
					if(externalDAO.getUsedDasTmYnByCartNoForAll(_do.getCartNo())){
						ContentsInfoDO contDo = externalDAO.getFl_path(_do.getCartNo(),Long.parseLong(String.valueOf(_do.getCartSeq())));
						logger.debug("contDo           "+contDo.getFl_path());
						

						//스토리지  다운로드인경우도ㅓCONTENTS_DOWN_TBL 에 값을 넣는다.

						DownCartDO downCartDO = new DownCartDO();
						//20121107 파셜 다운로드시 list.txt 파일을 생성한다.
						downCartDO.setCartNo(_do.getCartNo());
						File f = new File("/nearline/"+externalDAO.whichStLocForNew(downCartDO));
						if(!f.exists()) f.mkdirs();
						try {
							logger.debug("list path: "+f.getAbsolutePath());
							fw = new FileWriter(f.getAbsolutePath()+"/list.txt", true);
							bw = new BufferedWriter(fw);
						} catch (Exception e) {
							logger.error("list.txt new file create error - "+e.getMessage());
						}
						downCartDO.setFile_nm(contDo.getWrk_file_nm());
						downCartDO.setCartSeq(_do.getCartSeq());
						downCartDO.setFl_path(externalDAO.whichStLocForNew(downCartDO));
						downCartDO.setCti_id(contDo.getCti_id());

						externalDAO.updateCartContTbl(downCartDO);

						if(contDo.getDown_typ().equals("F")){
							downCartDO.setFile_nm(contDo.getCti_id()+".mxf");
						}else if(contDo.getDown_typ().equals("P")){
							downCartDO.setFile_nm(_do.getCartSeq()+"_"+contDo.getCti_id()+".mxf");	
							if(bw != null) {
								try {
									bw.write(contDo.getCti_id() + "," + contDo.getSom() + "," + contDo.getEom());
									bw.newLine();
									logger.debug("#######"+contDo.getCti_id() + "," + contDo.getSom() + "," + contDo.getEom());
								} catch (IOException e) {
									logger.error("list.txt new file write error - "+e.getMessage());
								}
							}
						}


						externalDAO.InsertContentsDownTbl(downCartDO);

						
						String getMessage =  externalDAO.addTaskByStorageClip(_do.getCartNo(),_do.getCartSeq());

						//String getMessage = "<?xml version=\"1.0\"?><Response><Result Success=\"true\"><TaskID>268</TaskID></Result></Response>";
						logger.debug("getMessage ["+getMessage+"]");
						//						TransferDO _do2 = _processor.getCartInfo(num);
						if(!getMessage.equals("")){

							TransferDOXML _doXML = new TransferDOXML();
							TransferDO _do1 = (TransferDO) _doXML.setDO(getMessage);
							_do1.setCart_no(Integer.parseInt(_do.getCartNo()+""));
							_do1.setCart_seq(Integer.parseInt(_do.getCartSeq()+""));
							externalDAO.insertAddTaskRes(_do1);
						}else{
							return 0;
						}
						//						return "sucess";
					}
					//					return "Fail";
				}else{
					if(!"".equals(xml)){
						NevigatorProxy port = new NevigatorProxy();
						try {
							String _result = port.downloadService(xml);
							logger.debug("_result   -  " + _result);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							logger.error("workflow request or", e);
						} 
					}
				}

				if (logger.isDebugEnabled()) 
				{
					logger.debug("[End : WebService Call To WAS for DownloadCart : CartNo = " + _do.getCartNo() + "]");
				}


			}
			if (count > 0 ) {
				result = true;
			}


			return count;

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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			release(null, stmt, con);

			if(bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					logger.error("list.txt new file close error - "+e.getMessage());
				}
			}
		}

	}

	/**
	 * 다운로드 외주승인상태 변경
	 * @param _do
	 * @throws Exception 
	 */
	public int updateDownloadRequestOutsourcingDetailList(CartContDO _do) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		StringBuffer buf = new StringBuffer();
		try{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);


			buf.append("\n update DAS.CART_CONT_TBL set OUTSOURCING_APPROVE = ? ");
			buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
			buf.append("\n WITH UR	 ");

			int cnt = 0;
			stmt = con.prepareStatement(buf.toString());
			stmt.setString(++cnt,_do.getOutsourcing_approve());
			stmt.setLong(++cnt, _do.getCartNo());
			stmt.setLong(++cnt, _do.getCartSeq());

			count = stmt.executeUpdate();

			//	            if (count > 0) {
			//	            	result = true;
			//	            }

			con.setAutoCommit(true);

			return count;
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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}
	//	/**
	//	 * 
	//	 * @param cartNo
	//	 * @param checkList
	//	 * @param app_cont
	//	 * @param comonDO
	//	 * @throws DASException
	//	 */
	/*public void updateDownloadRequestApproveList(String cartNo, String checkList, String app_cont, DASCommonDO comonDO) throws DASException
	{
	        int count = 0;
	        boolean result = false;
	        Connection con = null;
	        PreparedStatement stmt = null;
	        try{
	        	con = DBService.getInstance().getConnection();
	        	con.setAutoCommit(false);

	        	StringBuffer buf = new StringBuffer();
	    		buf.append("\n update DAS.CART_CONT_TBL set app_cont = '" + app_cont + "' ");
	    		buf.append("\n where CART_NO = " + cartNo + " AND CART_SEQ = " + checkList );
	    		buf.append("\n WITH UR	 ");

	    		stmt = con.prepareStatement(buf.toString());
	            count = stmt.executeUpdate();

	            if (count > 0) {
	            	result = true;
	            }

	            con.setAutoCommit(true);

	    	}
	        catch (NamingException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

		        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
		        throw exception;
			}
    		catch (SQLException e) 
    		{
    			// TODO 자동 생성된 catch 블록
    			e.printStackTrace();

    	        if (logger.isDebugEnabled()) 
    	        {
    	                logger.debug("[NamingException]" + e);
    	        }
    	        if(con != null)
    	        {
    	        	try {
    					con.rollback();
    				} catch (SQLException e1) {
    					// TODO 자동 생성된 catch 블록
    					e1.printStackTrace();
    				}
    	        }

    	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
    	        throw exception;
    		}finally{
    			release(null, stmt, con);
    		}

	}
	 */

	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return PageDO CartItemDO와 페이징 정보를 포함하고 있는 PageDO 
	 * @throws Exception 
	 */
	public PageDO selectApproveCartList(String pageCount, DASCommonDO commonDO, String startDate, String endDate) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(WorkStatement.selectApproveCartListQuery2(DASBusinessConstants.PageQueryFlag.NORMAL, startDate, endDate));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <= ?                                        	\n");
		buf.append(" WITH UR	 \n");

		int page = Integer.parseInt(pageCount);
		//		String query = WorkStatement.selectApproveCartListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectApproveCartList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//		총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, WorkStatement.selectApproveCartListQuery2(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, startDate, endDate));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.DOWN_ROW_COUNT;

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
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("REQ_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				item.setScreenQuality(		rs.getString("VD_QLTY_NM"));
				item.setLengthBreadthRate(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setAppCont(        		rs.getString("APP_CONT"));
				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));				

				resultList.add(item);
			}

			// 총페이지 수를 계산한다.
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
	 * 특정 마스타ID에 해당되는 매핑 테이블의 정보를 삭제한다. 
	 * @param con
	 * @param errorRegisterDO
	 * @param comonDO
	 * @throws SQLException
	 */
	private void deleteErrorRegisterInfo(Connection con, ErrorRegisterDO errorRegisterDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.ERROR_RGST_TBL ");
		buf.append("\n where MASTER_ID = ? ");
		buf.append("\n WITH UR	 ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setLong(++index,   Long.valueOf(errorRegisterDO.getMasterId()));

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf.toString());

			throw e;
		}
		finally
		{
			try { 	if (stmt != null)  stmt.close();	} catch (SQLException e) {}
		}

	}

	/**
	 * 검색어를 받아 검색된 목록을 리턴한다. 
	 * @param String xml형식으로 된 String을 받아 분석하여 검색을 실행
	 * @return String xml형식으로 된 목록을  돌려준다.
	 */
	public ArrayList selectSearchList(SearchDO searchDO) throws DASException
	{
		int IN2_SERVER_PORT = 0;																			//포트넘버
		String IN2_SERVER_IP  = "";																	//서버아이피


		StringBuffer buf = new StringBuffer();
		buf.append(" select DAS_EQ_USE_IP, DAS_EQ_USE_PORT FROM                                                      										\n");
		buf.append(" DAS.DAS_EQUIPMENT_TBL		\n");
		buf.append(" where DAS_EQ_CLF_CD = 'S00' \n");
		buf.append(" WITH UR	 \n");

		//		String query = WorkStatement.selectApproveCartListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSearchList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();

			if(rs.next()) {
				IN2_SERVER_PORT = Integer.parseInt(rs.getString("DAS_EQ_USE_PORT")); 
				IN2_SERVER_IP = rs.getString("DAS_EQ_USE_IP");
			}
		} catch (Exception e) {
			logger.error(buf.toString());
		} finally {
			release(rs, stmt, con);
		}


		//		IN2StdSearcher Searcher  = null;
		String ICASE_INDEXNAME = searchDO.getIndexName();													//인덱스명
		String newsfieldName = "IN2_INTEGRATION";																//필드명
		String searchfieldName = "IN2_INTEGRATION";					
		String returnfieldname = "";
		String returnfieldname2 = ""; 
		boolean sort = true;

		returnfieldname = "PGM_NM/CN_INFO/TITLE/CN_NM/SUB_TTL/EPIS_NO/SCHD_PGM_MN/SNPS/IN2_INTEGRATION";			//리턴필드명
		returnfieldname2 = "VD_QLTY/ASP_RTO_CD/CN_ID/ANNOT_ID/RP_IMG/REG_DT/" +
				"PGM_ID/MASTER_ID/KFRM_SEQ/KFRM_PATH/BRD_DD/BRD_LENG/CTGR_L_CD/" +
				"CTGR_S_CD/CTGR_M_CD/MC_NM/CMR_DRT_NM/WRITER_NM/DRT_NM/" +
				"ANNOT_CLF_CD/PILOT_YN/FINAL_BRD_YN/DAY";

		logger.debug(searchDO.getIndexName());
		if ("DAS_ANNOT".equals(searchDO.getIndexName())) {
			returnfieldname += "ANNOT_CLF_CONT";
		}

		String analyzerdefault = "KMA_BIGRAM";																//언어분석타입
		String analyzerkeyword  = "KEYWORD";																	//언어분석타입
		long count =0;									 																			//프로그램기준총검색문서수

		ArrayList list = new ArrayList();
		//		Searcher = new IN2StdSearcher();

		//검색 초기화
		//		if(Searcher.newQuery()){
		//		}
		//		logger.debug(IN2_SERVER_IP + " " + IN2_SERVER_PORT);
		//		//검색 IP PORT
		//		if(Searcher.setServer(IN2_SERVER_IP, IN2_SERVER_PORT)){
		//		}
		//		
		//		//검색 인덱스
		//		if(Searcher.addIndex(ICASE_INDEXNAME)){
		//		}
		//		
		//		// 방송일 날짜 검색
		//		if(!"".equals(searchDO.getStartdate())) {
		//			Searcher.addRangeField("BRD_DD", searchDO.getStartdate(), searchDO.getEnddate());
		//		}


		//########################################################################
		// 결과 내 검색
		String searchKey = searchDO.getSearchKeyList();

		if (searchKey != null && !searchKey.equals("")) {
			StringTokenizer st = new StringTokenizer(searchKey);

			while(st.hasMoreElements()){
				//				if(Searcher.addQueryEx(st.nextToken(","), analyzerdefault, searchfieldName,"AND")){
				//				}
			}
		}
		//########################################################################


		//########################################################################
		// 검색어 검색
		if (searchDO.getSearchKey() != null && !"".equals(searchDO.getSearchKey())) {
			//			Searcher.addQueryEx(searchDO.getSearchKey(), analyzerdefault, searchfieldName,"AND");
		}
		//########################################################################


		//########################################################################
		// 상세검색
		String operator = searchDO.getOperatorList();
		ArrayList<String> operatorList = new ArrayList<String>();

		if(!operator.equals("")){												// 선택된 텍스트값 리스트 형식으로 변환
			StringTokenizer st = new StringTokenizer(operator);
			while(st.hasMoreElements()){
				operatorList.add(st.nextToken("," ));
			}
		}


		if (!operator.equals("")) {
			//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
			// 상세검색 목록
			// 시작 생성일/종료 생성일
			if (!"".equals(searchDO.getStartReg_dt()) && !"".equals(searchDO.getEndReg_dt())) {
				//				Searcher.addRangeField("REG_DT", searchDO.getStartReg_dt(), searchDO.getEndReg_dt());
			}
			// 대분류 장르
			if (!"".equals(searchDO.getLargeJanre())) {
				//				Searcher.addQueryEx(searchDO.getLargeJanre(), analyzerkeyword, "CTGR_L_CD", operatorList.get(0).toString());
			}
			// 중분류 장르
			if (!"".equals(searchDO.getMediumJanre())) {
				//				Searcher.addQueryEx(searchDO.getMediumJanre(), analyzerkeyword, "CTGR_M_CD", operatorList.get(0).toString());
			}
			// 소분류 장르
			if (!"".equals(searchDO.getSmallJanre())) {
				//				Searcher.addQueryEx(searchDO.getSmallJanre(), analyzerkeyword, "CTGR_S_CD", operatorList.get(0).toString());
			}
			// 프로그램 명
			if (!"".equals(searchDO.getPgm_nm())) {
				//				Searcher.addQueryEx(searchDO.getPgm_nm(), analyzerdefault, "PGM_NM", operatorList.get(0).toString());
			}
			// 타이틀
			if (!"".equals(searchDO.getTitle())) {
				//				Searcher.addQueryEx(searchDO.getTitle(), analyzerdefault, "TITLE", operatorList.get(0).toString());
			}
			// 진행자
			if (!"".equals(searchDO.getMc_nm())) {
				//				Searcher.addQueryEx(searchDO.getMc_nm(), analyzerdefault, "MC_NM", operatorList.get(0).toString());
			}
			// 촬영자
			if (!"".equals(searchDO.getCmr_drt_nm())) {
				//				Searcher.addQueryEx(searchDO.getCmr_drt_nm(), analyzerdefault, "CMR_DRT_NM", operatorList.get(0).toString());
			}
			// 작가
			if (!"".equals(searchDO.getWriter_nm())) {
				//				Searcher.addQueryEx(searchDO.getWriter_nm(), analyzerdefault, "WRITER_NM", operatorList.get(0).toString());
			}
			// 연출자
			if (!"".equals(searchDO.getDrt_nm())) {
				//				Searcher.addQueryEx(searchDO.getDrt_nm(), analyzerdefault, "DRT_NM", operatorList.get(0).toString());
			}
			// 특이사항
			if (!"".equals(searchDO.getSpc_info())) {
				//				Searcher.addQueryEx(searchDO.getSpc_info(), analyzerdefault, "SPC_INFO", operatorList.get(0).toString());
			}
			// 프로듀서명
			if (!"".equals(searchDO.getProducer_nm())) {
				//				Searcher.addQueryEx(searchDO.getProducer_nm(), analyzerdefault, "PRODUCER_NM", operatorList.get(0).toString());
			}
			// 저작권 형태 설명
			if (!"".equals(searchDO.getCprt_type_dsc())) {
				//				Searcher.addQueryEx(searchDO.getCprt_type_dsc(), analyzerdefault, "CPRT_TYPE_DSC", operatorList.get(0).toString());
			}
			// 촬영장소
			if (!"".equals(searchDO.getCmr_place())) {
				//				Searcher.addQueryEx(searchDO.getCmr_place(), analyzerdefault, "CMR_PLACE", operatorList.get(0).toString());
			}
			// 제작부서
			if (!"".equals(searchDO.getPrdt_dept_nm())) {
				//				Searcher.addQueryEx(searchDO.getPrdt_dept_nm(), analyzerdefault, "PRDT_DEPT_NM", operatorList.get(0).toString());
			}
			// 편성명
			if (!"".equals(searchDO.getSchd_pgm_nm())) {
				//				Searcher.addQueryEx(searchDO.getSchd_pgm_nm(), analyzerdefault, "SCHD_PGM_NM", operatorList.get(0).toString());
			}
			// 부제
			if (!"".equals(searchDO.getSub_ttl())) {
				//				Searcher.addQueryEx(searchDO.getSub_ttl(), analyzerdefault, "SUB_TTL", operatorList.get(0).toString());
			}
			// 출연자명
			if (!"".equals(searchDO.getCast_nm())) {
				//				Searcher.addQueryEx(searchDO.getCast_nm(), analyzerdefault, "CAST_NM", operatorList.get(0).toString());
			}
			// 아카이버 명
			if (!"".equals(searchDO.getSec_arch_nm())) {
				//				Searcher.addQueryEx(searchDO.getSec_arch_nm(), analyzerdefault, "SEC_ARCH_NM", operatorList.get(0).toString());
			}
			// 음악정보
			if (!"".equals(searchDO.getMusic_info())) {
				//				Searcher.addQueryEx(searchDO.getMusic_info(), analyzerdefault, "MUSIC_INFO", operatorList.get(0).toString());
			}
			// 수상내역
			if (!"".equals(searchDO.getAward_hstr())) {
				//				Searcher.addQueryEx(searchDO.getAward_hstr(), analyzerdefault, "AWARD_HSTR", operatorList.get(0).toString());
			}
			// 시청등급
			if (!"".equals(searchDO.getView_gr_cd())) {
				//				Searcher.addQueryEx(searchDO.getView_gr_cd(), analyzerdefault, "VIEW_GR_CD", operatorList.get(0).toString());
			}
			// 명장면 명대사
			if ("DAS_ANNOT".equals(searchDO.getIndexName())) {
				if (!"".equals(searchDO.getCont())) {
					//					Searcher.addQueryEx(searchDO.getCont(), analyzerdefault, "CONT", operatorList.get(0).toString());
				}
			}
			// 코너제목
			if (!"DAS_PROGRAM".equals(searchDO.getIndexName())) {
				if (!"".equals(searchDO.getCn_nm())) {
					//					Searcher.addQueryEx(searchDO.getCn_nm(), analyzerdefault, "CN_NM", operatorList.get(0).toString());
				}
			}
			// 심의등급
			if (!"".equals(searchDO.getDlbr_cd())) {
				//				Searcher.addQueryEx(searchDO.getDlbr_cd(), analyzerdefault, "DLBR_CD", operatorList.get(0).toString());
			}
			// 저작권형태
			if (!"".equals(searchDO.getCprt_type())) {
				//				Searcher.addQueryEx(searchDO.getCprt_type(), analyzerdefault, "CPRT_TYPE", operatorList.get(0).toString());
			}
			// 저작권자명
			if (!"".equals(searchDO.getCprtr_nm())) {
				//				Searcher.addQueryEx(searchDO.getCprtr_nm(), analyzerdefault, "CPRTR_NM", operatorList.get(0).toString());
			}
			// 영상 ID
			if (!"".equals(searchDO.getCt_id())) {
				//				Searcher.addQueryEx(searchDO.getCt_id(), analyzerdefault, "CT_ID", operatorList.get(0).toString());
			}
			// 시청률
			if (!"".equals(searchDO.getPgm_rate())) {
				//				Searcher.addQueryEx(searchDO.getPgm_rate(), analyzerdefault, "PGM_RATE", operatorList.get(0).toString());
			}
			// 제작구분
			if (!"".equals(searchDO.getPrdt_in_outs_cd())) {
				//				Searcher.addQueryEx(searchDO.getPrdt_in_outs_cd(), analyzerdefault, "PRDT_IN_OUTS_CD", operatorList.get(0).toString());
			}
			//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		}
		//########################################################################


		// 정렬 방식
		if ("on".equals(searchDO.getAsc())) {
			sort = true;
		} else if ("off".equals(searchDO.getAsc())) {
			sort = false;
		}

		// 정렬 컬럼
		if (!"".equals(searchDO.getSortColumn())) {
			//			Searcher.addSortField(searchDO.getSortColumn(), sort);
		} else {
			//			Searcher.addSortField("CTGR_M_CD", sort);
		}

		// 리턴받을 필드 하이라이트 기능 on
		//		if(Searcher.addReturnField(returnfieldname, true)){
		//		}
		//		
		//		//	리턴받을 필드 하이라이트 기능 off
		//		if(Searcher.addReturnField(returnfieldname2, false)){
		//		}
		//		
		//		//리턴받을 시작과 끝수
		//		if(Searcher.setReturnPositionCount(Integer.parseInt(searchDO.getNowPage())*Integer.parseInt(searchDO.getPageCount()), Integer.parseInt(searchDO.getPageCount()))){
		//		}

		//검색실행
		//		if(Searcher.searchDocument()){
		//			//총검색수입력			
		//			count = Searcher.getTotalDocumentCount();
		//			logger.debug(count);
		//	        //결과값을 HASHMAP에 저장 LIST로 리턴
		//			for (int i =0; i < Searcher.getDocumentCount(); i++) {
		//				SearchDO searchDO2 = new SearchDO();
		//				
		//				searchDO2.setVd_qlty(Searcher.getValueInDocument(i, "VD_QLTY"));
		//				searchDO2.setAsp_rto_cd(Searcher.getValueInDocument(i, "ASP_RTO_CD"));
		//				searchDO2.setCn_id(Searcher.getValueInDocument(i, "CN_ID"));
		//				searchDO2.setAnnot_id(Searcher.getValueInDocument(i, "ANNOT_ID"));
		//				searchDO2.setRp_img(Searcher.getValueInDocument(i, "RP_IMG"));
		//				searchDO2.setReg_dt(Searcher.getValueInDocument(i, "REG_DT"));
		//				searchDO2.setPgm_id(Searcher.getValueInDocument(i, "PGM_ID"));
		//				searchDO2.setMaster_id(Searcher.getValueInDocument(i, "MASTER_ID"));
		//				searchDO2.setKfrm_seq(Searcher.getValueInDocument(i, "KFRM_SEQ"));
		//				searchDO2.setKfrm_path(Searcher.getValueInDocument(i, "KFRM_PATH"));
		//				searchDO2.setBrd_dd(Searcher.getValueInDocument(i, "BRD_DD"));
		//				searchDO2.setBrd_leng(Searcher.getValueInDocument(i, "BRD_LENG"));
		//				searchDO2.setCtgr_l_cd(Searcher.getValueInDocument(i, "CTGR_L_CD"));
		//				searchDO2.setCtgr_s_cd(Searcher.getValueInDocument(i, "CTGR_S_CD"));
		//				searchDO2.setCtgr_m_cd(Searcher.getValueInDocument(i, "CTGR_M_CD"));
		//				searchDO2.setMc_nm(Searcher.getValueInDocument(i, "MC_NM"));
		//				searchDO2.setCmr_drt_nm(Searcher.getValueInDocument(i, "CMR_DRT_NM"));
		//				searchDO2.setWriter_nm(Searcher.getValueInDocument(i, "WRITER_NM"));
		//				searchDO2.setDrt_nm(Searcher.getValueInDocument(i, "DRT_NM"));
		//				searchDO2.setAnnot_clf_cd(Searcher.getValueInDocument(i, "ANNOT_CLF_CD"));
		//				searchDO2.setPilot_yn(Searcher.getValueInDocument(i, "PILOT_YN"));
		//				searchDO2.setFinal_brd_yn(Searcher.getValueInDocument(i, "FINAL_BRD_YN"));
		//				searchDO2.setDay(Searcher.getValueInDocument(i, "DAY"));
		//				searchDO2.setPgm_nm(Searcher.getValueInDocument(i, "PGM_NM"));
		//				searchDO2.setCn_info(Searcher.getValueInDocument(i, "CN_INFO"));
		//				searchDO2.setTitle(Searcher.getValueInDocument(i, "TITLE"));
		//				searchDO2.setCn_nm(Searcher.getValueInDocument(i, "CN_NM"));
		//				searchDO2.setSub_ttl(Searcher.getValueInDocument(i, "SUB_TTL"));
		//				searchDO2.setEpis_no(Searcher.getValueInDocument(i, "EPIS_NO"));
		//				searchDO2.setSchd_pgm_nm(Searcher.getValueInDocument(i, "SCHD_PGM_MN"));
		//				searchDO2.setSnps(Searcher.getValueInDocument(i, "SNPS"));
		//				
		//				if ("DAS_ANNOT".equals(searchDO.getIndexName())) {
		//					searchDO2.setAnnot_clf_cont(Searcher.getValueInDocument(i, "ANNOT_CLF_CONT"));
		//				}

		//				list.add(searchDO2);
		//			}
		// 총 갯수
		//			list.add(count);
		//	     }

		return list;
	}

	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param cartNo 카트번호
	 * @param commonDO 공통정보
	 * @return List CartDetailItemDO를 포함하고 있는 DO
	 * @throws Exception 
	 */
	public String getAnnot_App_Cont(String ct_id, String s_frame, String duration, DASCommonDO commonDO) throws Exception
	{

		String query = WorkStatement.getAnnot_App_ContQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String result = "";

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getAnnot_App_Cont######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(ct_id));
			stmt.setBigDecimal(++index, new BigDecimal(s_frame));
			stmt.setBigDecimal(++index, new BigDecimal(duration));

			rs = stmt.executeQuery();

			if(rs.next())
			{
				result = rs.getString("ANNOT_CLF_CONT");
			}


			return result;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return PageDO CartItemDO와 페이징 정보를 포함하고 있는 PageDO 
	 * @throws Exception 
	 */
	public PageDO selectApproveCartList(String pageCount, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(WorkStatement.selectApproveCartListQuery2(DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <= ?                                        	\n");
		buf.append(" WITH UR	 \n");

		int page = Integer.parseInt(pageCount);
		//		String query = WorkStatement.selectApproveCartListQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectApproveCartList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//		총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, WorkStatement.selectApproveCartListQuery2(DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.DOWN_ROW_COUNT;

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
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("REQ_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				item.setScreenQuality(		rs.getString("VD_QLTY_NM"));
				item.setLengthBreadthRate(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setAppCont(        		rs.getString("APP_CONT"));
				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));				

				resultList.add(item);
			}

			// 총페이지 수를 계산한다.
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
	 * 해당 id가 데정팀 소속관리자인지 확인
	 * @param commonDO 공통정보
	 * @return PageDO CartItemDO와 페이징 정보를 포함하고 있는 PageDO 
	 * @throws Exception 
	 */
	public boolean isHeUser(String userid) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n  SELECT COUNT(*) FROM USER_INFO_TBL WHERE SBS_USER_ID='"+userid+"' AND DEPT_CD='D3OB01' ");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isHeUser######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = getTotalCount(con, buf.toString());

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
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}


	/**
	 * 해당 제목 검색
	 * @param commonDO 공통정보
	 * @return PageDO CartItemDO와 페이징 정보를 포함하고 있는 PageDO 
	 * @throws Exception 
	 */
	public boolean isPgmInfo(String userid) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n  SELECT COUNT(*) FROM USER_INFO_TBL WHERE SBS_USER_ID='"+userid+"' AND DEPT_CD='D3OB01' ");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isPgmInfo######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = getTotalCount(con, buf.toString());

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
			try { 	if (con != null)  con.close();	} catch (SQLException e) {}
		}
	}




	/*
	public List selectDownloadRequestForNotUSERList(CartItemDO cartItemDO) throws DASException
	{


		String query = WorkStatement.selectDownloadRequestListQuery(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(query);

			int index = 0;
//		
			stmt.setString(++index,cartItemDO.getFromdate()+"000000");
			stmt.setString(++index,cartItemDO.getEnddate()+"000000");
			if(!cartItemDO.getOut_user().equals("")){
				stmt.setString(++index,cartItemDO.getOut_user());
			}
			stmt.setString(++index,"%"+cartItemDO.getTitle()+"%");

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("REQ_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setAppCont(        		rs.getString("APP_CONT"));
				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setDown_gubun_nm( rs.getString("down_gubun_nm"));

				resultList.add(item);
			}
			return resultList;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}*/


	/**
	 * USER ID의 프로그램 정보 확인 ( DAS PROGRAM,PDS PROGRAM,CP 여부 확인,방영/종료 구분)
	 * @param userid 사용자 아이디
	 * @return 프로그램 정보
	 * @throws Exception 
	 */
	public List isCPyn(String userid) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		String dateString = CalendarUtil.getDateTime("yyyyMMdd");
		buf.append("\n SELECT PPT.PROGRAMID,PPT.CP_YN ");
		buf.append("\n  ,PPMT.DAS_PGM_ID ,PIT.BRD_END_DD ");
		buf.append("\n ,CASE WHEN  PIT.BRD_END_DD <  '"+dateString+"'   THEN 'N' ");
		buf.append("\n 	WHEN PIT.BRD_END_DD >   '"+dateString+"'   THEN 'Y'  ");
		buf.append("\n  END  AS BRD_END_YN  ");
		buf.append("\n FROM DAS.PDS_PGMINFO_TBL PPT  ");
		buf.append("\n   INNER JOIN DAS.PDS_PGM_MAPP_TBL PPMT ON PPMT.PDS_PGM_ID = PPT.PROGRAMID  ");
		buf.append("\n   INNER JOIN DAS.PGM_INFO_TBL PIT ON PIT.PGM_ID = PPMT.DAS_PGM_ID  ");
		buf.append("\n WHERE PPT.PRODUCER_ID =  '" + userid+"'");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List _resultList = new ArrayList();
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isCPyn######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();



			while(rs.next())
			{
				PdaInfoDO item = new PdaInfoDO();
				item.setProgramid(         		rs.getString("PROGRAMID"));
				item.setCp_yn(          		rs.getString("CP_YN"));
				item.setDas_pgm_id(          		rs.getLong("DAS_PGM_ID"));
				item.setBrd_end_dd(		rs.getString("BRD_END_DD"));
				item.setBrd_end_yn(		rs.getString("BRD_END_YN"));

				_resultList.add(item);
			}
			return  _resultList;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());

			
			throw e;
		}
		finally
		{
			try { 	release(rs,stmt,con);	} catch (Exception e) {}
		}

	}
	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param cartItemDO 조회조건이 담긴beans
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectDownloadRequestForUser(String pgm_id,CartItemDO cartItemDO) throws Exception
	{

		String outsorcing_yn = externalDAO.getOutSourcing_ynEmployee(cartItemDO.getUserid());
		String query = WorkStatement.selectDownloadRequestListForUserIdQuery(pgm_id,cartItemDO,outsorcing_yn);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDownloadRequestForUser######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			if(!cartItemDO.getDown_day().equals("")){
				stmt.setString(++index,cartItemDO.getFromdate()+"000000");
				stmt.setString(++index,cartItemDO.getEnddate()+"99999");
			}
			if(!cartItemDO.getOut_user().equals("")){
				stmt.setString(++index,cartItemDO.getOut_user());
			}
			if(!cartItemDO.getOut_user().equals("")){
				stmt.setString(++index,cartItemDO.getOut_user());
			}
			if(!cartItemDO.getDownSubj().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getDownSubj()+"%");
			}
			if(!cartItemDO.getReq_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReq_id()+"%");
			}
			if(!cartItemDO.getReqNm().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getReqNm()+"%");
			}
			if(!cartItemDO.getMedia_id().equals("")){
				stmt.setString(++index,"%"+cartItemDO.getMedia_id()+"%");
			}
			if(cartItemDO.getCti_id() != 0){
				stmt.setLong(++index,cartItemDO.getCti_id());
			}




			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));

				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				//item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setDown_gubun_nm( rs.getString("DESC"));
				item.setStorage( rs.getString("file_path"));
				item.setConm( rs.getString("CONM"));
				//item.setFm_dt( rs.getString("fm_dt"));
				//item.setEpis_no( rs.getInt("epis_no"));
				//	item.setCtgr_l_cd( rs.getString("ctgr_l_cd"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	//	public List selectDownloadRequestForCP(String pgm_id) throws DASException
	//	{
	//		String query = WorkStatement.selectDownloadRequestListForCPQuery(pgm_id);
	//		
	//		Connection con = null;
	//		PreparedStatement stmt = null;
	//		ResultSet rs = null;
	//		try 
	//		{
	//			con = DBService.getInstance().getConnection();
	//			
	//			stmt = con.prepareStatement(query);
	//			
	//			int index = 0;
	////		
	//			
	//			rs = stmt.executeQuery();
	//			
	//			int indexCount = 0;
	//			List resultList = new ArrayList();
	//
	//			while(rs.next())
	//			{
	//				CartItemDO item = new CartItemDO();
	//				item.setCartNo(         		rs.getLong("CART_NO"));
	//				item.setReqNm(          		rs.getString("USER_NM"));
	//				item.setReqDT(          		rs.getString("REQ_DT"));
	//				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
	//				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
	//				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
	//				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
	//				item.setAppCont(        		rs.getString("APP_CONT"));
	//				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
	//				item.setDown_gubun_nm( rs.getString("DESC"));
	//				//item.setReqNm( rs.getString("PRODUCER_NM"));
	//				resultList.add(item);
	//			}
	//			return resultList;
	//		} 
	//		catch (NamingException e) 
	//		{
	//			// TODO 자동 생성된 catch 블록
	//			e.printStackTrace();
	//			
	//	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	//	        throw exception;
	//		} 
	//		catch (SQLException e) 
	//		{
	//			// TODO 자동 생성된 catch 블록
	//			e.printStackTrace();
	//			
	//	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	//	        throw exception;
	//		}
	//		finally
	//		{
	//			release(rs, stmt, con);
	//		}
	//	}
	//	
	//	public List selectDownloadRequestForPD(String pgm_id) throws DASException
	//	{
	//		String query = WorkStatement.selectDownloadRequestListForPDQuery(pgm_id);
	//		
	//		Connection con = null;
	//		PreparedStatement stmt = null;
	//		ResultSet rs = null;
	//		try 
	//		{
	//			con = DBService.getInstance().getConnection();
	//			
	//			stmt = con.prepareStatement(query);
	//			
	//			int index = 0;
	////		
	//			rs = stmt.executeQuery();
	//			
	//			int indexCount = 0;
	//			List resultList = new ArrayList();
	//
	//			while(rs.next())
	//			{
	//				CartItemDO item = new CartItemDO();
	//				item.setCartNo(         		rs.getLong("CART_NO"));
	//				item.setReqNm(          		rs.getString("USER_NM"));
	//				item.setReqDT(          		rs.getString("REQ_DT"));
	//				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
	//				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
	//				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
	//				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
	//				item.setAppCont(        		rs.getString("APP_CONT"));
	//				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
	//				item.setDown_gubun_nm( rs.getString("DESC"));
	//				
	//				resultList.add(item);
	//			}
	//			return resultList;
	//		} 
	//		catch (NamingException e) 
	//		{
	//			// TODO 자동 생성된 catch 블록
	//			e.printStackTrace();
	//			
	//	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	//	        throw exception;
	//		} 
	//		catch (SQLException e) 
	//		{
	//			// TODO 자동 생성된 catch 블록
	//			e.printStackTrace();
	//			
	//	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	//	        throw exception;
	//		}
	//		finally
	//		{
	//			release(rs, stmt, con);
	//		}
	//	}
	//	
	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param cartNo 카트넘버
	 * @param pgmId_grp 프로그램 id 그룹
	 * @return List
	 * @throws Exception 
	 */
	public List selectDownloadRequestDetailsForUserId(String cartNo,String pgmId_grp) throws Exception
	{

		String query = WorkStatement.selectDownloadRequestDetailsListUserIdQuery(pgmId_grp);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDownloadRequestDetailsForUserId######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setCtiId(				rs.getLong("CTI_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				String ep = rs.getString("EPIS_NO");
				if(ep.equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(            rs.getString("EPIS_NO"));
				}
				item.setEpis_no(            rs.getString("EPIS_NO"));
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setDown_vol(           rs.getString("DOWN_VOL"));
				item.setDown_typ_nm(        rs.getString("DOWN_TYP_NM"));
				//				item.setPgm_nm(          	rs.getString("PGM_NM"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setApp_cont(        	rs.getString("APP_CONT"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setOutsourcing_yn(     rs.getString("OUTSOURCING_YN"));
				item.setDown_gubun_nm(rs.getString("DESC"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setTrans_vol(rs.getString("TRANS_VOL"));
				item.setVd_qlty_nm(rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDown_stat(rs.getString("DOWN_STAT"));
				item.setJob_status(rs.getString("STATUS"));

				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setFm_dt(rs.getString("FM_DT"));
				item.setMedia_id(rs.getString("MEDIA_ID"));

				String hr = rs.getString("FL_PATH");
				if(hr.matches(".*net_mp4.*")){
					item.setFl_path("cs_net_mp4/"+rs.getString("FL_PATH"));

				}else if(hr.matches(".*mp4.*")){
					item.setFl_path("cs_mp4/"+rs.getString("FL_PATH"));

				}else{
					item.setFl_path(rs.getString("FL_PATH"));
				}
				item.setFl_nm(rs.getString("WRK_FILE_NM"));
				//item.setFl_path(rs.getString("WRK_FILE_NM"));
				item.setApprove_nm(rs.getString("approveid"));
				item.setChennel_nm(rs.getString("CHENNEL_NM"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/*public List selectDownloadRequestDetailsForCP(long pgm_id) throws DASException
	{

		String query = WorkStatement.selectDownloadRequestDetailsForCPQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(pgm_id));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				item.setEpis_no(            rs.getString("EPIS_NO"));
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setDown_vol(           rs.getString("DOWN_VOL"));
				item.setDown_typ_nm(        rs.getString("DOWN_TYP_NM"));
//				item.setPgm_nm(          	rs.getString("PGM_NM"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setApp_cont(        	rs.getString("APP_CONT"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setOutsourcing_yn(     rs.getString("OUTSOURCING_YN"));
				item.setDown_gubun_nm(rs.getString("DESC"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setTrans_vol(rs.getString("TRANS_VOL"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}*/
	/*public List selectDownloadRequestDetailsForPD(long pgm_id) throws DASException
	{

		String query = WorkStatement.selectDownloadRequestDetailsForPDQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(pgm_id));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				item.setEpis_no(            rs.getString("EPIS_NO"));
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setDown_vol(           rs.getString("DOWN_VOL"));
				item.setDown_typ_nm(        rs.getString("DOWN_TYP_NM"));
//				item.setPgm_nm(          	rs.getString("PGM_NM"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setApp_cont(        	rs.getString("APP_CONT"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setOutsourcing_yn(     rs.getString("OUTSOURCING_YN"));
				item.setDown_gubun_nm(rs.getString("DESC"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setTrans_vol(rs.getString("TRANS_VOL"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (NamingException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		} 
		catch (SQLException e) 
		{
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
	        throw exception;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}*/



	/**
	 * My Page 다운로드 목록 조회
	 * @param cartItemDO 조회조건이 담긴 beans
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectMyDownloadRequest(CartItemDO cartItemDO) throws Exception
	{
		String query = WorkStatement.selectMyDownloadRequestList(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadRequest######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				item.setStorage(		rs.getString("file_path"));
				item.setDown_gubun_nm( rs.getString("DESC"));
				//item.setReqNm( rs.getString("PRODUCER_NM"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	/**
	 * My Page 다운로드 상세 조회
	 * @param cartNo 카트번호
	 * @param user_id 유져id
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectMyDownloadRequestDetailsList(String cartNo,String user_id) throws Exception
	{

		String query = WorkStatement.selectMyDownloadRequestDetailsQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, user_id);
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartseq(			rs.getInt("CART_SEQ"));
				item.setCt_id(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				String epin = rs.getString("epis_no");
				if(epin.trim().equals("0")){
					item.setEpisno("0");
				}else{
					item.setEpisno(rs.getString("epis_no"));	
				}
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setDown_gubun_nm(rs.getString("DESC"));
				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setReq_cont(		rs.getString("REQ_CONT"));
				item.setDown_status(		rs.getString("DOWN_STAT"));
				item.setStatus(		rs.getString("STATUS"));
				item.setMedia_id(		rs.getString("media_id"));
				item.setAppCont(		rs.getString("app_cont"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}








	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveList(CartItemDO cartItemDO) throws Exception
	{
		String query = WorkStatement.selectMyDownloadAprroveList(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setAppCont(        		rs.getString("APP_CONT"));
				item.setStorage(		rs.getString("file_path"));
				item.setCocd( rs.getString("conm"));
				//item.setDown_status( rs.getString("status"));
				resultList.add(item);
			}
			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveList2(CartItemDO cartItemDO,String dept_cd) throws Exception
	{
		String query ="";
		if(org.apache.commons.lang.StringUtils.isBlank(dept_cd)){
			query = WorkStatement.selectNewMyDownloadAprroveList(cartItemDO);
		}else{
			query = WorkStatement.selectNewMyDownloadAprroveList2(cartItemDO, dept_cd);	
		}
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveList######## query : " + query);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				item.setStorage(		rs.getString("file_path"));
				item.setCocd( rs.getString("conm"));

				item.setStoragename( rs.getString("STORAGENAME"));
				//	item.setMedia_id( rs.getString("media_id"));
				//	item.setCt_cont( rs.getString("req_CONT"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * My Sign 다운로드 승인 상세조회
	 * @param cartNo 카트넘버
	 *  @param user_id  유져id
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveDetailList(String cartNo,String user_id) throws Exception
	{

		String query = WorkStatement.selectMyDownloadAprroveDetailList();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveDetailList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));
			stmt.setString(++index, "%"+user_id.substring(1, 7)+"%");


			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				String epin = rs.getString("EPIS_NO");
				if(epin.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(            rs.getString("EPIS_NO"));	
				}
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				//item.setSetUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setDown_stat( rs.getString("status"));
				item.setApp_cont( rs.getString("app_cont"));
				item.setMedia_id( rs.getString("media_id"));
				item.setMasterId( rs.getLong("master_id"));
				item.setFl_nm( rs.getString("WRK_FILE_NM"));
				String hr = rs.getString("FL_PATH");
				if(hr.matches(".*net_mp4.*")){
					item.setPath("cs_net_mp4/"+rs.getString("FL_PATH"));

				}else if(hr.matches(".*mp4.*")){
					item.setPath("cs_mp4/"+rs.getString("FL_PATH"));

				}else{
					item.setPath(rs.getString("FL_PATH"));
				}
				item.setFm_dt( rs.getString("FM_DT"));
				item.setCtgrLCd( rs.getString("CTGR_L_CD"));
				item.setApprove_nm( rs.getString("approveid"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * My Sign 다운로드 승인 상세조회(아카이브팀)
	 * @param cartNo 카트넘버
	 *  @param user_id  유져id
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveDetailListForArchive(String cartNo,String user_id) throws Exception
	{

		String query = WorkStatement.selectMyDownloadAprroveDetailListForArchive();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveDetailListForArchive######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));
			///	stmt.setString(++index, "%"+user_id.substring(1, 7)+"%");


			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				String epin = rs.getString("EPIS_NO");
				if(epin.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(            rs.getString("EPIS_NO"));	
				}
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				//item.setSetUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setDown_stat( rs.getString("status"));
				item.setApp_cont( rs.getString("app_cont"));
				item.setMedia_id( rs.getString("media_id"));
				item.setMasterId( rs.getLong("master_id"));
				item.setFl_nm( rs.getString("WRK_FILE_NM"));
				String hr = rs.getString("FL_PATH");
				if(hr.matches(".*net_mp4.*")){
					item.setPath("cs_net_mp4/"+rs.getString("FL_PATH"));

				}else if(hr.matches(".*mp4.*")){
					item.setPath("cs_mp4/"+rs.getString("FL_PATH"));

				}else{
					item.setPath(rs.getString("FL_PATH"));
				}
				item.setFm_dt( rs.getString("FM_DT"));
				item.setCtgrLCd( rs.getString("CTGR_L_CD"));
				item.setApprove_nm( rs.getString("approveid"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * My Sign 다운로드 승인 상세조회(외주제작)
	 * @param cartNo 카트넘버
	 *  @param user_id  유져id
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveDetailListForOutsosing(String cartNo,String user_id) throws Exception
	{

		String query = WorkStatement.selectMyDownloadAprroveDetailListForOutsosing();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveDetailListForOutsosing######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setBigDecimal(++index, new BigDecimal(cartNo));
			stmt.setString(++index, "%"+user_id.substring(1, 7)+"%");

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartContDO item = new CartContDO();
				item.setCartNo(         	rs.getLong("CART_NO"));
				item.setCartSeq(			rs.getInt("CART_SEQ"));
				item.setCtId(				rs.getLong("CT_ID"));
				item.setTitle(	            rs.getString("TITLE"));
				String epin = rs.getString("EPIS_NO");
				if(epin.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(            rs.getString("EPIS_NO"));	
				}
				item.setBrd_dd(             rs.getString("BRD_DD"));
				item.setSom(            	rs.getString("SOM"));
				item.setEom(            	rs.getString("EOM"));
				item.setSframe(             rs.getString("S_FRAME"));
				item.setDuration(          	rs.getLong("DURATION"));
				item.setRistClfCd(      	rs.getString("RIST_CLF_CD"));
				item.setRist_clf_nm(		rs.getString("RIST_CLF_NM"));
				item.setReq_cont(rs.getString("REQ_CONT"));
				item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				//item.setSetUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setDown_stat( rs.getString("status"));
				item.setApp_cont( rs.getString("app_cont"));
				item.setMedia_id( rs.getString("media_id"));
				item.setMasterId( rs.getLong("master_id"));
				item.setFl_nm( rs.getString("WRK_FILE_NM"));

				String hr = rs.getString("FL_PATH");
				if(hr.matches(".*net_mp4.*")){
					item.setPath("cs_net_mp4/"+rs.getString("FL_PATH"));

				}else if(hr.matches(".*mp4.*")){
					item.setPath("cs_mp4/"+rs.getString("FL_PATH"));

				}else{
					item.setPath(rs.getString("FL_PATH"));
				}
				//item.setPath( rs.getString("FL_PATH"));
				item.setFm_dt( rs.getString("FM_DT"));
				item.setCtgrLCd( rs.getString("CTGR_L_CD"));
				item.setApprove_nm( rs.getString("approveid"));
				resultList.add(item);
			}


			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * USER ID의 프로그램 정보 확인 ( PROGRAMCD, PROGRAM 구분)
	 * @param userid 사용자 아이디
	 * @return 프로그램 정보
	 * @throws Exception 
	 */
	public List isRightUser(String userid) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		String dateString = CalendarUtil.getDateTime("yyyyMMdd");
		buf.append("\n SELECT ");
		buf.append("\n  APP.PGM_ID AS PDS_PGM_ID  ");
		buf.append("\n , app.approve_user_num ");
		buf.append("\n ,MST.PGM_ID AS DAS_PGM_ID  ");
		buf.append("\n  FROM APPROVE_INFO_TBL APP ");
		buf.append("\n LEFT OUTER JOIN (SELECT PGM_ID, PDS_CMS_PGM_ID FROM  METADAT_MST_TBL GROUP BY PGM_ID, PDS_CMS_PGM_ID  ) MST ON MST.PDS_CMS_PGM_ID = APP.PGM_ID ");
		buf.append("\n WHERE APP.approve_user_num =  '" + userid.substring(1, 7)+"'");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List _resultList = new ArrayList();
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isRightUser######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();



			while(rs.next())
			{
				PdaInfoDO item = new PdaInfoDO();
				item.setProgramid(         		rs.getString("PDS_PGM_ID"));
				item.setUSER_NO(          		rs.getString("approve_user_num"));
				item.setDas_pgm_id(          		rs.getLong("DAS_PGM_ID"));


				_resultList.add(item);
			}
			return  _resultList;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			
			throw e;
		}
		finally
		{
			try { 	release(rs,stmt,con);	} catch (Exception e) {}
		}

	}







	/**
	 * 외주승인 여부 조회.
	 * 
	 * @param cart_no
	 *             카트번호
	 * @param cart_seq
	 *            카트순번
	 * @return
	 * @throws Exception 
	 */
	public CartContDO selectOutSourcingInfo(long cart_no , int cart_seq) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append( WorkStatement.selectOutSourcingInfo());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutSourcingInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			int index = 0;
			stmt.setLong(++index,cart_no);
			stmt.setInt(++index,cart_seq);

			rs = stmt.executeQuery();

			int indexCount = 0;

			CartContDO item = new CartContDO();

			if (rs.next()) {
				item.setOutsourcing_approve(rs.getString("OUTSOURCING_APPROVE"));
				item.setOutsourcing_yn(rs.getString("OUTSOURCING_YN"));
				item.setDown_stat(rs.getString("DOWN_STAT"));
			}

			return item;
		} catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}







	/**
	 * 다운로드 외주승인상태 변경
	 * @param _do
	 * @throws Exception 
	 */

	public int updateDownloadRequestOutsourcingDetailList2(CartContDO _do) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		BufferedWriter bw = null;
		FileWriter fw = null;
		StringBuffer buf = new StringBuffer();

		try{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDownloadRequestOutsourcingDetailList2######## con : " + con);
			con.setAutoCommit(false);


			buf.append("\n update DAS.CART_CONT_TBL set OUTSOURCING_APPROVE = ? ");
			buf.append("\n ,down_stat = ? ");
			buf.append("\n ,app_cont = ? ");
			buf.append("\n ,approveid = ? ");
			buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
			buf.append("\n WITH UR	 ");
			int cnt = 0;

			stmt = con.prepareStatement(buf.toString());
			stmt.setString(++cnt,"Y");

			if(_do.getDown_stat().equals("003")){
				stmt.setString(++cnt,"003");
			}else if(isRiskCodeUnlimit(_do.getCartNo(),_do.getCartSeq())){
				stmt.setString(++cnt,"006");
			}else{
				stmt.setString(++cnt,"005");

			}
			stmt.setString(++cnt,_do.getApp_cont());
			stmt.setString(++cnt, _do.getRegrId());
			stmt.setLong(++cnt, _do.getCartNo());
			stmt.setInt(++cnt, _do.getCartSeq());



			count = stmt.executeUpdate();


			//ifcms신청건인경우 거절햇다는 것을 알려준다.
			if(_do.getDown_stat().equals("003")){
				IfCmsArchiveDO item3 = new IfCmsArchiveDO();

				item3 = getIdsForCart_no(_do.getCartNo(),_do.getCartSeq());  
				String date = item3.getComplete_dt();

				SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMddHHmmss");
				SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss");

				Date d1;
				try {
					d1 = df1.parse(date);
					date = df2.format(d1);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(!item3.getCallback_url().equals("")){
					//http 클라이언트 호출로 완료되었음을 호출한다.
					HttpClient client = new HttpClient();
					GetMethod method = null;

					logger.debug("###################################callbackurl  " +item3.getCallback_url()+"&cmsid=das"+"&wfid="+item3.getCart_no()+"&uid="+item3.getCti_idForHigh()+"&state=error&time="+date);
					method = new GetMethod(item3.getCallback_url()+"&cmsid=das"+"&wfid="+item3.getCart_no()+"&uid="+item3.getCti_idForHigh()+"&state=error&time="+date);

					int status = 0;
					String callresult = "";
					try {
						status = client.executeMethod(method);
						callresult = method.getResponseBodyAsString();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			con.setAutoCommit(true);
			//무제한일 경우 다운로드 고고싱           

			if(isRiskCodeUnlimit(_do.getCartNo(),_do.getCartSeq())&&!_do.getDown_stat().equals("003")){
				if (logger.isDebugEnabled()) 
				{
					logger.debug("[Start : WebService Call To WAS for DownloadCart : CartNo = " + _do.getCartNo() + "]");
				}

				//다운로드 제한 여부가 No이 웹 서비스를 호출하여 다운로드 카트를 승인 없이  다운로드 한다
				//cart_cont_tbl의 rist_yn이 N인 값만 던진다
				//String xml = externalDAO.getDownloadXml(_do);//<- 카트번호를 받아 소속 seq list로 보냄
				_do.setDtl_type(getCocdInfo(_do.getCartNo(),_do.getCartSeq()));

				String xml = externalDAO.getNewDownloadXmlFormat(_do);//카트번호, 카트 순번을 받아 처리 한건씩.
				logger.debug(xml);


				/**
				 * DTL 이관 전 클립 & 스토리지에 존재 한다면 DAS-TM에 전송 요청  시작
				 */
				// DTL 이관 전 클립 & 스토리지에 존재 분기시작.
				CartContDO tmpCartContDO  = externalDAO.getStLocClip(externalDAO.selectCartContInfo(_do.getCartNo(), _do.getCartSeq()).getCtId());
				if(!CommonUtl.isEmptyString(tmpCartContDO.getFl_path())){



					/**
					 * PDS,NDS,계열사 해당하는 다운로드 요청만 DAS-TM 전송요청을 하게 됨(20110512:dekim)
					 */
					if(externalDAO.getUsedDasTmYnByCartNo(_do.getCartNo())){
						ContentsInfoDO contDo = externalDAO.getFl_path(_do.getCartNo(),Long.parseLong(String.valueOf(_do.getCartSeq())));

						//스토리지  다운로드인경우도ㅓCONTENTS_DOWN_TBL 에 값을 넣는다.

						DownCartDO downCartDO = new DownCartDO();


						//20121107 파셜 다운로드시 list.txt 파일을 생성한다.
						downCartDO.setCartNo(_do.getCartNo());
						File f = new File("/nearline/"+externalDAO.whichStLocForNew(downCartDO));
						if(!f.exists()) f.mkdirs();
						try {
							fw = new FileWriter(f.getAbsolutePath()+"/list.txt", true);
							bw = new BufferedWriter(fw);
						} catch (Exception e) {
							logger.error("list.txt new file create error - "+e.getMessage());
						}


						downCartDO.setCartSeq(_do.getCartSeq());
						downCartDO.setFl_path(externalDAO.whichStLocForNew(downCartDO));
						downCartDO.setCti_id(contDo.getCti_id());

						externalDAO.updateCartContTbl(downCartDO);

						if(contDo.getDown_typ().equals("F")){
							downCartDO.setFile_nm(contDo.getCti_id()+".mxf");
						}else if(contDo.getDown_typ().equals("P")){
							downCartDO.setFile_nm(_do.getCartSeq()+"_"+contDo.getCti_id()+".mxf");	

							if(bw != null) {
								try {
									logger.error("##########write info     ----------       "+contDo.getCti_id() + "," + contDo.getSom() + "," + contDo.getEom());

									bw.write(contDo.getCti_id() + "," + contDo.getSom() + "," + contDo.getEom());
									bw.newLine();
								} catch (IOException e) {
									logger.error("list.txt new file write error - "+e.getMessage());
								}
							}
						}


						externalDAO.InsertContentsDownTbl(downCartDO);


						String getMessage =  externalDAO.addTaskByStorageClip(_do.getCartNo(),_do.getCartSeq());

						//String getMessage = "<?xml version=\"1.0\"?><Response><Result Success=\"true\"><TaskID>268</TaskID></Result></Response>";
						logger.debug("getMessage ["+getMessage+"]");
						//						TransferDO _do2 = _processor.getCartInfo(num);
						if(!getMessage.equals("")){
							TransferDOXML _doXML = new TransferDOXML();
							TransferDO _do1 = (TransferDO) _doXML.setDO(getMessage);
							_do1.setCart_no(Integer.parseInt(_do.getCartNo()+""));
							_do1.setCart_seq(Integer.parseInt(_do.getCartSeq()+""));
							externalDAO.insertAddTaskRes(_do1);
						}else{
							return 0;
						}
						//						return "sucess";
					}
					//					return "Fail";
				}else{
					if(!"".equals(xml)){
						NevigatorProxy port = new NevigatorProxy();
						try {
							String _result = port.downloadService(xml);
							logger.debug("_result   -  " + _result);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}

				if (logger.isDebugEnabled()) 
				{
					logger.debug("[End : WebService Call To WAS for DownloadCart : CartNo = " + _do.getCartNo() + "]");
				}


			}
			if (count > 0 ) {
				result = true;
			}


			return count;

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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			release(null, stmt, con);


			if(bw != null) {
				try {
					bw.flush();
					bw.close();
				} catch (IOException e) {
					logger.error("list.txt new file close error - "+e.getMessage());
				}
			}
		}

	}

	/*public int updateDownloadRequestOutsourcingDetailList2(CartContDO cart) throws DASException
	{
	        int count = 0;
	        boolean result = false;
	        Connection con = null;
	        PreparedStatement stmt = null;
	        try{
	        	con = DBService.getInstance().getConnection();
	        	con.setAutoCommit(false);

	        	StringBuffer buf = new StringBuffer();
	    		buf.append("\n update DAS.CART_CONT_TBL set OUTSOURCING_APPROVE = ? ");
	    		buf.append("\n ,down_stat = ? ");
	    		buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
	    		buf.append("\n WITH UR	 ");

	    		int cnt = 0;
	    		stmt = con.prepareStatement(buf.toString());
	    		stmt.setString(++cnt,"Y");
	    		stmt.setString(++cnt,"005");
	    		stmt.setLong(++cnt, cart.getCartNo());
	    		stmt.setInt(++cnt, cart.getCartSeq());

	            count = stmt.executeUpdate();

	            //2차 승인대기로 상태를 바꾼다


	            //updateDownStat(cart);
	            //무제한일 경우 1차 승인후 요청 한다
	       if(isRiskCodeUnlimit(cart.getCartNo(),cart.getCartSeq())){
	            if (logger.isDebugEnabled()) 
				{
					logger.debug("[Start : WebService Call To WAS for DownloadCart : CartNo = " + cart.getCartNo() + "]");
				}

				//다운로드 제한 여부가 No이 웹 서비스를 호출하여 다운로드 카트를 승인 없이  다운로드 한다
				//cart_cont_tbl의 rist_yn이 N인 값만 던진다
				//String xml = externalDAO.getDownloadXml(_do);//<- 카트번호를 받아 소속 seq list로 보냄
				String xml = externalDAO.getDownloadXmlFormat(cart);//카트번호, 카트 순번을 받아 처리 한건씩.

	 *//**
	 * DTL 이관 전 클립 & 스토리지에 존재 한다면 DAS-TM에 전송 요청  시작
	 *//*
				// DTL 이관 전 클립 & 스토리지에 존재 분기시작.
				CartContDO tmpCartContDO  = externalDAO.getStLocClip(externalDAO.selectCartContInfo(cart.getCartNo(), cart.getCartSeq()).getMasterId());
				if(!CommonUtl.isEmptyString(tmpCartContDO.getFl_path())&&externalDAO.getStorage_yn(cart.getCartNo())){
	  *//**
	  * PDS,NDS,계열사 해당하는 다운로드 요청만 DAS-TM 전송요청을 하게 됨(20110512:dekim)
	  *//*
					if(externalDAO.getUsedDasTmYnByCartNo(cart.getCartNo())){

						String getMessage =  externalDAO.addTaskByStorageClip(cart.getCartNo(),cart.getCartSeq());

						//String getMessage = "<?xml version=\"1.0\"?><Response><Result Success=\"true\"><TaskID>268</TaskID></Result></Response>";
						logger.debug("getMessage ["+getMessage+"]");
//						TransferDO _do2 = _processor.getCartInfo(num);
						TransferDOXML _doXML = new TransferDOXML();
						TransferDO _do1 = (TransferDO) _doXML.setDO(getMessage);
						_do1.setCart_no(Integer.parseInt(cart.getCartNo()+""));
						_do1.setCart_seq(Integer.parseInt(cart.getCartSeq()+""));
						externalDAO.insertAddTaskRes(_do1);
//						return "sucess";
					}
//					return "Fail";
				}else{
					if(!"".equals(xml)){
						NevigatorProxy port = new NevigatorProxy();
						try {
							String _result = port.downloadService(xml);

						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 

						 //다운로드로 상태를 바꾼다
					}
				}
				cart.setDown_stat("006");
				updateDownStat(cart);

				if (logger.isDebugEnabled()) 
		        {
		                logger.debug("[End : WebService Call To WAS for DownloadCart : CartNo = " + cart.getCartNo() + "]");
		        }

	       }

	            con.setAutoCommit(true);

	            return count;
	    	}
	        catch (NamingException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

		        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
		        throw exception;
			}
    		catch (SQLException e) 
    		{
    			// TODO 자동 생성된 catch 블록
    			e.printStackTrace();

    	        if (logger.isDebugEnabled()) 
    	        {
    	                logger.debug("[NamingException]" + e);
    	        }
    	        if(con != null)
    	        {
    	        	try {
    					con.rollback();
    				} catch (SQLException e1) {
    					// TODO 자동 생성된 catch 블록
    					e1.printStackTrace();
    				}
    	        }

    	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
    	        throw exception;
    		}finally{
    			release(null, stmt, con);
    		}

	}	
	   */

	/**
	 * My Sign 다운로드 승인조회(외주제작 용)
	 * @param cartItemDO 조회조건이 담긴beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */

	public List selectMyDownloadAprroveForOutSourcingList(CartItemDO cartItemDO) throws Exception
	{
		String query = WorkStatement.selectNewMyDownloadAprroveForOutSourcingList(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveForOutSourcingList######## query : " + query);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				//item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setCocd( rs.getString("conm"));
				item.setStorage( rs.getString("file_path"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

		
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	private boolean isRiskCodeUnlimit(long cartNo, int cartSeq) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n  rist_yn ");
		buf.append("\n  from CART_CONT_TBL ");
		buf.append("\n where cart_no = ? and cart_seq = ? ");	



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isRiskCodeUnlimit######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setLong(++index, cartNo);
			stmt.setLong(++index, cartSeq);
			rs = stmt.executeQuery();

			while(rs.next()){;
			if(rs.getString("rist_yn").equals("N"))
			{
				return true;
			}
			else
			{
				return false;
			}
			}
			return false;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());
			
			throw e;
		}
		finally
		{
			if (logger.isDebugEnabled()) 
			{
				logger.debug("[isDownloadRistrict Released : CartNo = " + cartNo + "]");
			}
			release(rs, stmt, con);
		}		

	}





	/**
	 * 다운로드 상태 변경
	 * @param _do
	 * @throws Exception 
	 */
	public int updateDownStat(CartContDO _do) throws Exception
	{
		int count = 0;
		boolean result = false;
		Connection con = null;
		PreparedStatement stmt = null;
		StringBuffer buf = new StringBuffer();
		try{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDownStat######## con : " + con);
			con.setAutoCommit(false);


			buf.append("\n update DAS.CART_CONT_TBL set down_stat = ? ");
			buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
			buf.append("\n WITH UR	 ");

			int cnt = 0;
			stmt = con.prepareStatement(buf.toString());
			stmt.setString(++cnt,_do.getDown_stat());
			stmt.setLong(++cnt, _do.getCartNo());
			stmt.setLong(++cnt, _do.getCartSeq());

			count = stmt.executeUpdate();

			//	            if (count > 0) {
			//	            	result = true;
			//	            }

			con.setAutoCommit(true);

			return count;
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
		}finally{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}




	/**
	 * 해당영상의 원본경로를 찾는다
	 * @param commonDO 공통정보
	 * @return PageDO CartItemDO와 페이징 정보를 포함하고 있는 PageDO 
	 * @throws Exception 
	 */
	public String getCocdInfo(long cart_no, long cart_seq) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n  select mst.cocd from cart_cont_Tbl cart ");
		buf.append("\n  inner join metadat_mst_Tbl mst on mst.master_id = cart.MASTER_ID ");
		buf.append("\n  where cart.CART_NO = "+cart_no+" and cart.CART_SEQ="+cart_seq+"  ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getCocdInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			rs = stmt.executeQuery();


			String dtl_type ="";
			while(rs.next())
			{
				if(rs.getString("cocd").equals("S")){
					dtl_type ="das";
				}else{
					dtl_type ="medianet";	
				}
			}
			return dtl_type;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			
			throw e;
		}
		finally
		{
			try { 	release(rs,stmt,con);	} catch (Exception e) {}
		}
	}





	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveListForIfCms(CartItemDO cartItemDO) throws Exception
	{
		String query ="";

		query = WorkStatement.selectNewMyDownloadAprroveListForIfCms(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			if(logger.isDebugEnabled()) {
				logger.debug("######selectMyDownloadAprroveListForIfCms######## query : " + query);
			}
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next()) {
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setCartseq(         		rs.getInt("CART_SEQ"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				item.setStorage(		rs.getString("file_path"));
				item.setCocd( rs.getString("conm"));

				item.setStoragename( rs.getString("STORAGENAME"));
				item.setMedia_id( rs.getString("media_id"));
				item.setCt_cont( rs.getString("req_cont"));
				item.setMaster_id( rs.getLong("master_id"));
				item.setRist_clf_cd( rs.getString("rist_clf_cd"));
				item.setRist_clf_nm( rs.getString("rist_clf_nm"));
				resultList.add(item);
			}
			return resultList;
		} catch (Exception e) {
			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}


	/**
	 * 승인 여부 조회.
	 * 
	 * @param cart_no
	 *             카트번호
	 * @param cart_seq
	 *            카트순번
	 * @return
	 * @throws Exception 
	 */
	public String isApproveYn(long cart_no , int cart_seq) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append( WorkStatement.isApproveYn());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isApproveYn######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			int index = 0;
			stmt.setLong(++index,cart_no);
			stmt.setInt(++index,cart_seq);

			rs = stmt.executeQuery();

			int indexCount = 0;

			String approve_yn ="";
			if (rs.next()) {

				approve_yn = rs.getString("DOWN_STAT");
			}

			return approve_yn;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;		} finally {
			release(rs, stmt, con);
		}
	}





	/**
	 * My Sign 다운로드 승인조회(외주제작 용)
	 * @param cartItemDO 조회조건이 담긴beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */

	public List selectMyDownloadAprroveForOutSourcingListForIfCms(CartItemDO cartItemDO) throws Exception
	{
		//String query = WorkStatement.selectMyDownloadAprroveForOutSourcingListForIfCms(cartItemDO);
		String query = WorkStatement.selectNewMyDownloadAprroveForOutSourcingList(cartItemDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyDownloadAprroveForOutSourcingListForIfCms######## con : " + query);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setCartseq(         		rs.getInt("CART_SEQ"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				item.setCt_cont(       	rs.getString("req_cont"));
				item.setMedia_id(  	rs.getString("media_id"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				//item.setUseLimitFlag(		rs.getString("USE_LIMIT_FlAG"));
				item.setCocd( rs.getString("conm"));
				item.setStorage( rs.getString("file_path"));

				item.setMaster_id( rs.getLong("master_id"));
				item.setRist_clf_cd( rs.getString("rist_clf_cd"));
				item.setRist_clf_nm( rs.getString("rist_clf_nm"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}




	/**
	 * My Sign 다운로드 승인조회
	 * @param cartItemDO 조회정보가 담긴 beans
	 * @return List
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectMyDownloadAprroveListForIfCms(CartItemDO cartItemDO,String dept_cd) throws Exception
	{
		String query ="";
		if(org.apache.commons.lang.StringUtils.isBlank(dept_cd)){
			query = WorkStatement.selectNewMyDownloadAprroveListForIfCms2(cartItemDO);
		}else{
			query = WorkStatement.selectNewMyDownloadAprroveListForIfCms2(cartItemDO, dept_cd);
		}
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			logger.debug("######selectMyDownloadAprroveListForIfCms######## query : " + query);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index,cartItemDO.getFromdate());
			stmt.setString(++index,cartItemDO.getEnddate());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				CartItemDO item = new CartItemDO();
				item.setCartNo(         		rs.getLong("CART_NO"));
				item.setCartseq(         		rs.getInt("CART_SEQ"));
				item.setReqNm(          		rs.getString("USER_NM"));
				item.setReqDT(          		rs.getString("REQ_DT"));
				//item.setVd_qlty_nm(		rs.getString("VD_QLTY_NM"));
				//item.setAsp_rto_nm(rs.getString("ASP_RTO_NM"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setUseLimitCount(  	rs.getInt("USE_LIMIT_COUNT"));
				//item.setAppCont(        		rs.getString("APP_CONT"));
				item.setStorage(		rs.getString("file_path"));
				item.setCocd( rs.getString("conm"));

				item.setStoragename( rs.getString("STORAGENAME"));
				item.setMedia_id( rs.getString("media_id"));
				item.setCt_cont( rs.getString("req_CONT"));
				item.setMaster_id( rs.getLong("master_id"));
				item.setRist_clf_cd( rs.getString("rist_clf_cd"));
				item.setRist_clf_nm( rs.getString("rist_clf_nm"));
				resultList.add(item);
			}
			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}



	/**
	 * cart_no 기준 값을 가져온다.
	 * @param cnInfoDO                                                                                                                                    	
	 * @throws Exception 
	 */
	private IfCmsArchiveDO getIdsForCart_no(long cart_no,int cart_seq) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n cti_id,cart_no,value(mod_dt,'00000000000000') as mod_dt,value(url,'') as url ");
		buf.append("\n 	from cart_cont_tbl  ");

		buf.append("\n where cart_no = ? and cart_seq = ?" );

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getIdsForCart_no######## con : " + con);
			//stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			int index = 0;
			stmt.setLong(++index, cart_no);
			stmt.setLong(++index, cart_seq);
			rs = stmt.executeQuery();

			String title="";
			IfCmsArchiveDO item = new IfCmsArchiveDO();
			while(rs.next())
			{
				item.setCti_idForHigh(rs.getLong("cti_id"));
				item.setCart_no(rs.getLong("cart_no"));
				item.setComplete_dt(rs.getString("mod_dt"));
				item.setCallback_url(rs.getString("url"));
			}




			return item;
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


}







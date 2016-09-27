package com.app.das.business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.DisuseStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveIngestInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DisuseConditionDO;
import com.app.das.business.transfer.DisuseDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CalendarUtil;
import com.app.das.util.DBService;
import com.app.das.util.StringUtils;

/**
 * 폐기의 Database 관련 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class DisuseDAO extends AbstractDAO 
{
	private Logger logger = Logger.getLogger(DisuseDAO.class);

	private static DisuseDAO instance;
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	
	/**
	 * A private constructor
	 *
	 */
	private DisuseDAO() 
	{
	}

	public static synchronized DisuseDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new DisuseDAO();
		}
		return instance;
	}

	/**
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectDisuseTargetList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(DisuseStatement.selectDisuseTargetListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	 \n ");

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
			//logger.debug("######selectDisuseTargetList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, DisuseStatement.selectDisuseTargetListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());
			int rowPerPage = DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT;

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);


			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			String sCd,mCd, lCd  = null;
			while(rs.next())
			{
				DisuseDO item = new DisuseDO();
				item.setDataId(	rs.getString("MASTER_ID"));
				//제목을 완성한다.
				String subject = rs.getString("PGM_NM") + " - " + rs.getString("EPIS_NO") + "회 - " + rs.getString("TITLE");
				item.setDataNm(			subject);
				item.setRsvPrdCd(		rs.getString("RSV_PRD_CD"));
				item.setRsvPrdNm(		rs.getString("RSV_PRD_NM"));
				item.setArrgEndDt(		rs.getString("ARRG_END_DT"));
				item.setRsvPrdEndDd(	rs.getString("RSV_PRD_END_DD"));
				lCd = rs.getString("CTGR_L_CD");
				item.setCtgrLCd(lCd);
				mCd = rs.getString("CTGR_M_CD");
				item.setCtgrMCd(mCd);
				sCd = rs.getString("CTGR_S_CD");
				item.setCtgrSCd(sCd);
				item.setUseCount(		rs.getInt("USE_COUNT"));
				item.setYearUseCount(	rs.getInt("ONE_YEAR_COUNT"));

				//소분류 코드를 사용  코드 테이블에서 RMK2 값을 가져온다
				//값이 없으면 중분류 코드를 사용 코드 테이블에서 RMK2 값을 가져온다
				//값이 있으면 DISUSER_ID 설정
				//값이 없으면 대분류 코드를 사용 코드 테이블에서 RMK2 값을 가져온다
				//값이 있으면 DISUSER_ID 설정
				/*
				id = selectDisuseID(con, "P004", sCd );
				if(StringUtils.isEmpty(id))
				{	
					id = selectDisuseID(con, "P003", mCd );
					if (StringUtils.isEmpty(id))
					{
						id = selectDisuseID(con, "P002", lCd );
						if(StringUtils.isEmpty(id))
							item.setDisuseUserId("");
						else 
							item.setDisuseUserId(id);
					}
					else
						item.setDisuseUserId(id);
				}
				else
					item.setDisuseUserId(id);				

				if(logger.isInfoEnabled())
				{
					logger.debug("[패기자 ID]  " + id);
				}	
				 */

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
	 * 폐기대상 1차 선정을 한다,(폐기정보 테이블에 저장한다)
	 * @param disuseDOList 저장할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertFirstDisuseList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				if(isThereDisuseDataId(item.getDataId()))
				{
					DASException exception = new DASException(ErrorConstants.ALREADY_DISUSE_DATA, "이미 등록되어 있는 데이터입니다.");
					throw exception;

				}
			}


			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				insertFirstDisuse(con, item, toDate, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public PageDO selectDisuseList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(DisuseStatement.selectDisuseListQuery(conditionDO, commonDO.getUserId(), DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	 ");

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
			//logger.debug("######selectDisuseList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, DisuseStatement.selectDisuseListQuery(conditionDO, commonDO.getUserId(), DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT;

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
				DisuseDO item = new DisuseDO();

				item.setDisuseNo(			rs.getBigDecimal("DISUSE_NO"));
				item.setDataNm (				rs.getString("DATA_NM"));
				item.setDataId(					rs.getString("DATA_ID"));
				item.setDataClfCd(			rs.getString("DATA_CLF_CD"));
				item.setDisuseDd(			rs.getString("DISUSE_DD"));
				item.setDisuseRsl (			rs.getString("DISUSE_RSL"));
				item.setDisuseClf (			rs.getString("DISUSE_CLF"));
				item.setExtsRsl (				rs.getString("EXTS_RSL"));
				item.setExtsDt(					rs.getString("EXTS_DT"));
				item.setExtsCd(				rs.getString("EXTS_CD"));
				item.setExtsNm(				rs.getString("EXTS_NM"));
				item.setCtgrLCd(				rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(				rs.getString("CTGR_M_CD"));
				item.setCtgrSCd (				rs.getString("CTGR_S_CD"));
				item.setUseCount(			rs.getInt("USE_COUNT"));
				item.setYearUseCount(		rs.getInt("BY_Y_USE_COUNT"));
				item.setDisuseFstSltDd(	rs.getString("DISUSE_FST_SLT_DD"));
				item.setDisuserRvDd(		rs.getString("DISUSER_RV_DD"));
				item.setDiConfirmDd(		rs.getString("DI_CONFIRM_DD"));
				item.setRsvPrdCd(			rs.getString("RSV_PRD_CD"));
				item.setArrgEndDt(			rs.getString("ARRG_END_DT"));
				item.setRsvPrdEndDd(		rs.getString("RSV_PRD_END_DD"));				


				//폐기위원을 찾는다.
				String disuseUserId = null;
				if(!StringUtils.isEmpty(rs.getString("L_USER_ID")))
				{
					disuseUserId = rs.getString("L_USER_ID");
				}
				if(!StringUtils.isEmpty(rs.getString("M_USER_ID")))
				{
					disuseUserId = rs.getString("M_USER_ID");
				}
				if(!StringUtils.isEmpty(rs.getString("S_USER_ID")))
				{
					disuseUserId = rs.getString("S_USER_ID");
				}


				item.setDisuseUserId(disuseUserId);

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
	 * 폐기 1차 선정된 목록에서 폐기 의뢰를 하면 폐기구분을 폐기위원 검토중으로 바꾼다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseInvestList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				updateDisuseInvest(con, item, toDate, CodeConstants.DisuseKind.INVESTIGATION, item.getDisuseUserId(), commonDO);
			}

			con.commit();
		} 
		catch (Exception e) 
		{
			
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기위원 검토 목록에서 연장 처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseExtensionList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				updateDisuseExtension(con, item, toDate, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
	
			
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
	 * 폐기위원 검토 목록에서 폐기 검토 완료처리를 하고 폐기구분을 데이터 정보팀 심의 상태로 수정한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseInvestCompletList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				updateDisuseInvestComplet(
						con, item, toDate, CodeConstants.DisuseKind.DATA_INFO_DISCUSSION, CalendarUtil.getToday(), commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기목록에서 연장을 확정한다.(마스터 테이블의 보존기간 만료일을 수정한다.)
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseExtensionCompletList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				//폐기정보 테이블의 폐기구분을 폐기취소로 수정한다.
				updateDisuseExtensionComplet(con, item, toDate, CodeConstants.DisuseKind.DISUSE_CANCEL, commonDO);
				//메타데이터마스터의 보존기간 만료일을 셋팅한다.
				updateDisuseExtensionMaster(con, item, toDate, item.getExtsDt(), DASBusinessConstants.YesNo.YES, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기 완료처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseCompletList######## con : " + con);
			con.setAutoCommit(false);

			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			String toDate = CalendarUtil.getToday();

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				//폐기정보 테이블의 폐기구분을 폐기완료로 수정하고 데이터정보팀 확정일을 셋팅한다..
				updateDisuseComplet(
						con, item, toDateTime, CodeConstants.DisuseKind.DISUSE_COMPLETION, toDate, commonDO);
				//메타데이터마스터의 삭제일을 셋팅한다.
				updateDisuseCompletMaster(con, item, toDateTime, toDate, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			

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
	 * 폐기 취소 Process를 수행하기위한 폐기구분에 해당하는 목록 조회를 한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectDisuseCancelList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(DisuseStatement.selectDisuseCancelListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
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
			//logger.debug("######selectDisuseCancelList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, DisuseStatement.selectDisuseCancelListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT;

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
				DisuseDO item = new DisuseDO();

				item.setDisuseNo(			rs.getBigDecimal("DISUSE_NO"));
				item.setDataNm (				rs.getString("DATA_NM"));
				item.setDataId(					rs.getString("DATA_ID"));
				item.setDataClfCd(			rs.getString("DATA_CLF_CD"));
				item.setDisuseDd(			rs.getString("DISUSE_DD"));
				item.setDisuseRsl (			rs.getString("DISUSE_RSL"));
				item.setDisuseClf (			rs.getString("DISUSE_CLF"));
				item.setExtsRsl (				rs.getString("EXTS_RSL"));
				item.setExtsDt(					rs.getString("EXTS_DT"));
				item.setExtsCd(				rs.getString("EXTS_CD"));
				item.setExtsNm(				rs.getString("EXTS_NM"));
				item.setCtgrLCd(				rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(				rs.getString("CTGR_M_CD"));
				item.setCtgrSCd (				rs.getString("CTGR_S_CD"));
				item.setUseCount(			rs.getInt("USE_COUNT"));
				item.setYearUseCount(		rs.getInt("BY_Y_USE_COUNT"));
				item.setDisuseFstSltDd(	rs.getString("DISUSE_FST_SLT_DD"));
				item.setDisuserRvDd(		rs.getString("DISUSER_RV_DD"));
				item.setDiConfirmDd(		rs.getString("DI_CONFIRM_DD"));
				item.setRsvPrdCd(			rs.getString("RSV_PRD_CD"));
				item.setArrgEndDt(			rs.getString("ARRG_END_DT"));
				item.setRsvPrdEndDd(		rs.getString("RSV_PRD_END_DD"));				


				//폐기위원을 찾는다.
				String disuseUserId = null;
				if(!StringUtils.isEmpty(rs.getString("L_USER_ID")))
				{
					disuseUserId = rs.getString("L_USER_ID");
				}
				if(!StringUtils.isEmpty(rs.getString("M_USER_ID")))
				{
					disuseUserId = rs.getString("M_USER_ID");
				}
				if(!StringUtils.isEmpty(rs.getString("S_USER_ID")))
				{
					disuseUserId = rs.getString("S_USER_ID");
				}


				item.setDisuseUserId(disuseUserId);

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
	 * 폐기대상 1차 선정이 되었던 건들을 삭제한다.
	 * @param disuseDOList 삭제할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteFirstDisuseList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteFirstDisuseList######## con : " + con);
			con.setAutoCommit(false);

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				deleteFirstDisuse(con, item, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
		
			
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
	 * 폐기구분코드가 폐기위원검토이면 페기구분 코드를 1차 선정으로 변경한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestCancelList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseInvestCancelList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				updateDisuseInvest(con, item, toDate, CodeConstants.DisuseKind.FIRST_CHOICE, Constants.BLANK, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기위원 검토 목록에서 폐기구분을 폐기위원 검토중으로 수정하고 폐기위원 검토완료일을 Clear 시킨다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestCompletCancelList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseInvestCompletCancelList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				updateDisuseInvestComplet(
						con, item, toDate, CodeConstants.DisuseKind.INVESTIGATION, Constants.BLANK, commonDO);
			}

			con.commit();
		} 
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기 완료된 건에 대해 취소 처리를 한다.<br>
	 * 폐기구분코드가 폐기완료  이면 폐기구분을 데이터정보팀심의로 수정하고 데이터 정보팀확정일을 Clear 시킨다.<br>
	 * 또한, 메타데이터마스터의 삭제일자를 셋팅한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseCompletCancelList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseCompletCancelList######## con : " + con);
			con.setAutoCommit(false);

			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				//폐기정보 테이블의 폐기구분을 폐기완료로 수정하고 데이터정보팀 확정일을 셋팅한다..
				updateDisuseComplet(
						con, item, toDateTime, CodeConstants.DisuseKind.DATA_INFO_DISCUSSION, Constants.BLANK, commonDO);
				//메타데이터마스터의 삭제일을 셋팅한다.
				updateDisuseCompletMaster(con, item, toDateTime, Constants.BLANK, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 페기구분코드가 폐기 취소인 건들에 대해 폐기구분을 데이터 정보팀 심의로 수정하고 <br>
	 * 메타데이터마스터의 보존기간 만료일을 Clear 한다. 
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionCompletCancelList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDisuseExtensionCompletCancelList######## con : " + con);
			con.setAutoCommit(false);

			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			for(Iterator i = disuseDOList.iterator(); i.hasNext();)
			{
				DisuseDO item = (DisuseDO)i.next();

				//폐기정보 테이블의 폐기구분을 폐기취소로 수정한다.
				updateDisuseExtensionComplet(con, item, toDate, CodeConstants.DisuseKind.DATA_INFO_DISCUSSION, commonDO);
				//메타데이터마스터의 보존기간 만료일을 Clear 시킨다
				updateDisuseExtensionMaster(con, item, toDate, Constants.BLANK, DASBusinessConstants.YesNo.NO, commonDO);
			}

			con.commit();
		} 
		
		catch (Exception e) 
		{
			logger.error(disuseDOList.toString());
			
			
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
	 * 폐기대상 1차 선정을 한다,(폐기정보 테이블에 저장한다)
	 * @param con 커넥션
	 * @param disuseDO 저장할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param toDate 오늘날짜
	 * @param commonDO 공통정보
	 */
	private void insertFirstDisuse(Connection con, DisuseDO disuseDO, String toDate, DASCommonDO commonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DISUSE_INFO_TBL( ");
		buf.append("\n 	DISUSE_NO,  ");
		buf.append("\n 	DATA_NM,  ");
		buf.append("\n 	DATA_ID,  ");
		buf.append("\n 	DATA_CLF_CD, "); 
		buf.append("\n 	DISUSE_DD,  ");
		buf.append("\n 	DISUSE_RSL, ");
		buf.append("\n 	DISUSE_CLF, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	MOD_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	EXTS_RSL, ");
		buf.append("\n 	EXTS_DT, ");
		buf.append("\n 	CTGR_L_CD, ");
		buf.append("\n 	CTGR_M_CD, ");
		buf.append("\n 	CTGR_S_CD, ");
		buf.append("\n 	USE_COUNT, ");
		buf.append("\n 	BY_Y_USE_COUNT, ");
		buf.append("\n 	DISUSE_FST_SLT_DD, ");
		buf.append("\n 	DISUSER_RV_DD, ");
		buf.append("\n 	DI_CONFIRM_DD, ");
		buf.append("\n 	EXTS_CD, ");
		buf.append("\n 	DISUSER_ID ");		
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;



			//시퀀스를 채번한다.
			stmt.setString(++index, getNextSquence(con, DASBusinessConstants.SequenceName.SEQ_DISUSE_ID)); 
			stmt.setString(++index, disuseDO.getDataNm()); 
			stmt.setString(++index, disuseDO.getDataId()); 
			stmt.setString(++index, CodeConstants.DataClfCode.IMAGE); 
			stmt.setString(++index, disuseDO.getDisuseDd()); 
			stmt.setString(++index, disuseDO.getDisuseRsl()); 
			stmt.setString(++index, CodeConstants.DisuseKind.FIRST_CHOICE); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setString(++index, disuseDO.getExtsRsl()); 
			stmt.setString(++index, disuseDO.getExtsDt()); 
			stmt.setString(++index, disuseDO.getCtgrLCd()); 
			stmt.setString(++index, disuseDO.getCtgrMCd()); 
			stmt.setString(++index, disuseDO.getCtgrSCd()); 
			stmt.setInt(++index, disuseDO.getUseCount()); 
			stmt.setInt(++index, disuseDO.getYearUseCount()); 
			stmt.setString(++index, CalendarUtil.getToday()); 
			stmt.setString(++index, disuseDO.getDisuserRvDd()); 
			stmt.setString(++index, disuseDO.getDiConfirmDd());
			stmt.setString(++index, disuseDO.getExtsCd());
			stmt.setString(++index, disuseDO.getDisuseUserId());

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
	/**
	 * 폐기 1차 선정된 목록에서 폐기 의뢰를 하면 폐기구분을 폐기위원 검토중으로 바꾼다.
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param disuseClf 폐기구분코드
	 * @param disUserId 폐기신청id
	 * @param commonDO 공통정보
	 * 
	 */
	private void updateDisuseInvest(Connection con, DisuseDO disuseDO, String toDate, String disuseClf, String disUserId, DASCommonDO commonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISUSE_INFO_TBL set ");
		buf.append("\n 	DISUSE_CLF = ?, ");
		buf.append("\n 	DISUSER_ID = ?, ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where DISUSE_NO = ? ");		

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, disuseClf); 
			stmt.setString(++index, disUserId); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo());

			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			logger.error(buf);

			throw e;
		}
		finally
		{
			release(null, stmt, null);
		}

	}
	/**
	 * 폐기위원 검토 목록에서 연장 처리를 한다.
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param commonDO 공통정보
	 */
	private void updateDisuseExtension(Connection con, DisuseDO disuseDO, String toDate, DASCommonDO commonDO) throws SQLException, DASException
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISUSE_INFO_TBL set ");
		buf.append("\n 	EXTS_RSL = ?,    ");
		buf.append("\n 	EXTS_CD = ?, ");
		buf.append("\n 	EXTS_DT = ?, ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where DISUSE_NO = ? ");	

		PreparedStatement stmt = null;
		try 
		{


			//연장일을 계산한다.
			String extensionDate = null;
			if(CodeConstants.ExtensionTermCode.FOREVER.equals(disuseDO.getExtsCd()))
			{
				extensionDate = "99991231";
			}
			else
			{
				extensionDate = CalendarUtil.afterSpecDay(CalendarUtil.getToday(), 0, Integer.parseInt(disuseDO.getExtsCd()), 0);
			}

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, disuseDO.getExtsRsl()); 
			stmt.setString(++index, disuseDO.getExtsCd()); 
			stmt.setString(++index, extensionDate); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo());

			stmt.executeUpdate();
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			try {
				throw e;
			} catch (Exception e1) {
		
			}
		}
		
		finally
		{
			release(null, stmt, null);
		}

	}
	/**
	 * 폐기위원 검토 목록에서 폐기 검토 완료처리를 하고 폐기구분을 데이터 정보팀 심의 상태로 수정한다.
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param disuseClf 폐기구분코드
	 * @param disUserId 폐기신청id
	 * @param commonDO 공통정보
	 */
	private void updateDisuseInvestComplet(
			Connection con, DisuseDO disuseDO, String toDate, String disuseClf, String disuserRvDd, DASCommonDO commonDO) throws SQLException
			{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISUSE_INFO_TBL set ");
		buf.append("\n 	DISUSE_CLF = ?, ");
		buf.append("\n 	DISUSER_RV_DD = ?, ");
		buf.append("\n 	MOD_DT = ?, "); 
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where DISUSE_NO = ? ");	

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, disuseClf); 
			stmt.setString(++index, disuserRvDd); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo());

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
	/**
	 * 폐기정보 테이블의 폐기구분을 폐기취소로 수정한다.
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param disuseClf 폐기구분코드
	 * @param commonDO 공통정보
	 */
	private void updateDisuseExtensionComplet(
			Connection con, DisuseDO disuseDO, String toDate, String disuseClf, DASCommonDO commonDO) throws SQLException
			{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISUSE_INFO_TBL set ");
		buf.append("\n 	DISUSE_CLF = ?, ");
		buf.append("\n 	MOD_DT = ?, "); 
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where DISUSE_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, disuseClf); 
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo());

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
	/**
	 * 메타데이터마스터의 보존기간 만료일을 셋팅한다.
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param extsDd 연장일
	 * @param useYn 사용여부
	 * @param commonDO 공통정보
	 */
	private void updateDisuseExtensionMaster(Connection con, DisuseDO disuseDO, String toDate, String extsDd, String useYn, DASCommonDO commonDO) throws SQLException
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL set ");
		buf.append("\n 	RSV_PRD_END_DD = ?, ");
		buf.append("\n 	USE_YN = ?, "); 
		buf.append("\n 	MODRID = ?, "); 
		//buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where MASTER_ID = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, extsDd); 
			stmt.setString(++index, useYn); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setString(++index, toDate); 
			stmt.setInt(++index, Integer.parseInt(disuseDO.getDataId()));

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
	/**
	 * 폐기정보 테이블의 폐기구분을 폐기완료로 수정하고 데이터정보팀 확정일을 셋팅한다
	 * @param disuseDO 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param con 커넥션
	 * @param toDate 오늘날짜
	 * @param disuseClf 폐기구분코드
	 * @param diConfirmDd 확정일
	 * @param commonDO 공통정보
	 */
	private void updateDisuseComplet(
			Connection con, DisuseDO disuseDO, String toDate, String disuseClf, String diConfirmDd, DASCommonDO commonDO) throws SQLException
			{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISUSE_INFO_TBL set ");
		buf.append("\n 	DISUSE_CLF = ?, ");
		buf.append("\n 	DI_CONFIRM_DD = ?, ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where DISUSE_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, disuseClf); 
			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index, toDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo());

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

	private void updateDisuseCompletMaster(Connection con, DisuseDO disuseDO, String toDate, String delDate, DASCommonDO commonDO) throws SQLException
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL set ");
		buf.append("\n 	DEL_DD = ?, ");
		buf.append("\n 	MODRID = ?, "); 
		//buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where MASTER_ID = ? ");

		PreparedStatement stmt = null;
		try 
		{
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, delDate); 
			stmt.setString(++index, commonDO.getUserId()); 
			//stmt.setString(++index, toDate); 
			stmt.setInt(++index, Integer.parseInt(disuseDO.getDataId()));

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

	private void deleteFirstDisuse(Connection con, DisuseDO disuseDO, DASCommonDO commonDO) throws SQLException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.DISUSE_INFO_TBL ");
		buf.append("\n where  DISUSE_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			//페기번호
			stmt.setBigDecimal(++index, disuseDO.getDisuseNo()); 

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


	private boolean isThereDisuseDataId(String dataId) throws NamingException, SQLException
	{
		StringBuffer buf = new StringBuffer();

		buf.append(" select count(1) FROM  DAS.DISUSE_INFO_TBL where DATA_ID = "+dataId+"  \n");

		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereDisuseDataId######## con : " + con);
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
		catch (SQLException e) 
		{
			logger.error(buf.toString());
			throw e;
		}
		finally
		{
			release(null, null, con);
		}
	}

	private String selectDisuseID(Connection con, String clfCd, String sclCd) throws NamingException, SQLException
	{
		StringBuffer buf = new StringBuffer();

		buf.append(" select \n");
		buf.append(" RMK_2 \n");
		buf.append(" from DAS.CODE_TBL where CLF_CD  = ? and SCL_CD = ? \n");

		ResultSet rs = null;
		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, clfCd);
			stmt.setString(++index, sclCd); 

			rs = stmt.executeQuery();
			rs.next();
			return rs.getString("RMK_2");

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


	/**
	 * 인제스트 된 목록
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectArchiveIngestList(ArchiveIngestInfoDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM     					\n");
		buf.append(" (                    			\n");
		buf.append(DisuseStatement.selectArchiveIngestQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
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
			//logger.debug("######selectArchiveIngestList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, DisuseStatement.selectArchiveIngestQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.DISUSE_ROW_COUNT;

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
				ArchiveIngestInfoDO item = new ArchiveIngestInfoDO();

				item.setTapeItem(			rs.getString("TAPE_ITEM_ID"));
				item.setTitle(				rs.getString("TITLE"));
				item.setCt_nm(					rs.getString("CT_NM"));
				item.setCti(			rs.getString("CTI_ID"));
				item.setArchiveYN(			rs.getString("ARCH_STE_YN"));
				item.setDtlYN(			rs.getString("DTL_YN"));

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
	 *  폐기 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getDisCardList(DiscardDO condition) throws Exception
	{
		PageDO pageDO = new PageDO();
		StringBuffer buf = new StringBuffer();
		Map value = null;

		// 만약 처음으로 검색하는 거라면(다음 페이지가 아니라) 그 검색의 전체 데이터 갯수를 넘겨줘야 한다.
		//	if (conditionDO.getStartPos() == 1)
		//	value = this.getMetadatInfoListCount(condition); 

		buf.append(" select * FROM                                                      										\n");
		buf.append(" (         	\n"); 
		buf.append(DisuseStatement.selecDiscardList(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	  ");

		//Page에 따른 계산을 한다.
		int page = condition.getPage();
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
			//logger.debug("######getDisCardList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, DisuseStatement.selecDiscardList(condition, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			logger.debug("######getDisCardList######## query : " + buf.toString());
			stmt = con.prepareStatement(buf.toString());
			int rowPerPage = condition.getRowPerPage();
			if(rowPerPage == 0)
			{
				rowPerPage = DASBusinessConstants.PageRowCount.DISCARD_ROW_COUNT;
			}

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);



			updateDel_StaInfo();
			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();


				item.setCtgr_l_cd(				rs.getString("ctgr_l_cd"));
				item.setTitle(			rs.getString("title"));
				item.setBrd_dd(rs.getString("brd_dd"));
				item.setRsv_prd_end_dd(		rs.getString("rsv_prd_end_dd"));
				item.setRsv_prd_start_dd(		rs.getString("rsv_prd_end_dd"));
				item.setRsv_prd_cd(		rs.getString("rsv_prd_cd"));
				item.setBrd_len(		rs.getString("brd_leng"));
				item.setEpis_no(		rs.getString("epis_no"));
				item.setMaster_id(		rs.getInt("master_id"));
				item.setMedia_id(		rs.getString("media_id"));
				item.setUse_count(		rs.getString("cnt"));
				//	item.setTotalcount( Integer.parseInt(value.get("CCOUNT").toString()));
				//	item.setBrd_leng_sum(  Integer.parseInt(value.get("SUM_BRD_LENG").toString()));
				resultList.add(item);
			}

			//총페이지 수를 계산한다.
			int totalPageCount = totalCount / rowPerPage  + (totalCount % rowPerPage != 0 ? 1 : 0);

			//검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			//계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);



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
	 * 폐기 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getSumDiscard(DiscardDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		Map value = null;

		// 만약 처음으로 검색하는 거라면(다음 페이지가 아니라) 그 검색의 전체 데이터 갯수를 넘겨줘야 한다.
		//	if (conditionDO.getStartPos() == 1)
		//	value = this.getMetadatInfoListCount(condition);            											
		buf.append(DisuseStatement.selectDisCardInfoQuery(condition));




		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			logger.debug("######getSumDiscard######## query : " + buf.toString());

			stmt = con.prepareStatement(buf.toString());

			//int index = 0;
			updateDel_StaInfo();
			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();


				int k = (int)rs.getFloat("SUM_BRD_LENG");
				item.setTotalcount(rs.getInt("CCOUNT"));
				item.setBrd_leng_sum(k);
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
	 * 페기 정보를 등록 한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */


	public int[] insertDisuse(List roleDO) throws Exception
	{
		String delAddDate = dasHandler.getProperty(DASBusinessConstants.DeleteAfterDate.DELETEAFTERDATE);
		StringBuffer buf = new StringBuffer();


		buf.append("\n insert into DAS.DISCARD_INFO_TBL( ");	

		buf.append("\n 	TITLE,  ");
		buf.append("\n 	BRD_DD,  ");
		buf.append("\n 	BRD_LEN, "); 
		buf.append("\n 	DISUSE_CONT, ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	RSV_PRD_END_DD,  ");	
		buf.append("\n 	DISUSE_END_DD,  ");
		buf.append("\n 	MASTER_ID , ");
		buf.append("\n  DISUSE_YN , ");
		buf.append("\n  DISUSE_STA,  ");
		buf.append("\n  pre_rsv_prd_cd , ");
		buf.append("\n  reg_id  ");
		buf.append("\n )  ");
		buf.append("\n values  ");


		buf.append("\n ( ? , ?,?,?, ? ,?, ?,?,?,?,?,? )  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertDisuse######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;


			try{
				String getRsv_prd_end_dd = null;
				String getDel_dd="";
				//String dateString = roleDO.getRsv_prd_end_dd();
				String dateString = CalendarUtil.getDateTime("yyyyMMdd");

				SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
				Date date = formatter.parse(dateString);	
				Date date2 = formatter.parse(dateString);	
				Calendar calendar = Calendar.getInstance();	
				Calendar calendar2 = Calendar.getInstance();	
				calendar.setTime(date);
				calendar2.setTime(date2);
				if(delAddDate!=null){
					calendar.add(Calendar.DAY_OF_MONTH,+Integer.parseInt(delAddDate) );
				}else{
					calendar.add(Calendar.DAY_OF_MONTH,+7);
				}
				calendar2.add(Calendar.DAY_OF_MONTH,+7);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());
				getDel_dd=formatter.format(calendar2.getTime());
				for(int i=0;i<roleDO.size();i++){
					index = 0;
					DiscardDO roleDOs = (DiscardDO)roleDO.get(i);

					String rsvprdcd = selectRsvprdcdInfo(roleDOs.getMaster_id());
					stmt.setString(++index, roleDOs.getTitle());	
					stmt.setString(++index, roleDOs.getBrd_dd());
					stmt.setString(++index, roleDOs.getBrd_len());
					stmt.setString(++index, roleDOs.getDisuse_cont());
					stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddhhmmss"));		
					stmt.setString(++index, roleDOs.getRsv_prd_end_dd());
					stmt.setString(++index, getRsv_prd_end_dd);
					stmt.setInt(++index, roleDOs.getMaster_id());
					stmt.setString(++index, "Y");
					stmt.setString(++index, "000");
					stmt.setString(++index, rsvprdcd);
					stmt.setString(++index, roleDOs.getReg_id());
					updateDisuseInfo(getRsv_prd_end_dd,roleDOs.getMaster_id(),CalendarUtil.getDateTime("yyyyMMdd"));
					updateDisuseInfoForMapp(roleDOs.getMaster_id(),getDel_dd);
					stmt.addBatch();

				}

				int[] rInt = null;	
				rInt =stmt.executeBatch();

				con.commit();
				return rInt;
			} catch(Exception e) {
				e.printStackTrace();
			}


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
		return null;

	}


	/**
	 *  폐기 정보를 등록 한다.(영상선정에서 삭제시 사용)
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertDisuseForMeta(DiscardDO roleDO) throws Exception
	{
		String delAddDate = dasHandler.getProperty(DASBusinessConstants.DeleteAfterDate.DELETEAFTERDATE);
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DISCARD_INFO_TBL( ");	

		buf.append("\n 	TITLE,  ");
		buf.append("\n 	BRD_DD,  ");
		buf.append("\n 	BRD_LEN, "); 
		buf.append("\n 	DISUSE_CONT, ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	RSV_PRD_END_DD,  ");	
		buf.append("\n 	DISUSE_END_DD,  ");
		buf.append("\n 	MASTER_ID , ");
		buf.append("\n  DISUSE_YN , ");
		buf.append("\n  DISUSE_STA,  ");
		buf.append("\n  pre_rsv_prd_cd,  ");
		buf.append("\n  reg_id  ");
		buf.append("\n )  ");
		buf.append("\n values  ");


		buf.append("\n ( ? , ?,?,?, ? ,?, ?,?,?,?,? ,?)  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertDisuseForMeta######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;


			try{
				String getRsv_prd_end_dd = null;
				String getDel_dd="";
				//String dateString = roleDO.getRsv_prd_end_dd();
				String dateString = CalendarUtil.getDateTime("yyyyMMdd");
				String dateString2 = CalendarUtil.getDateTime("yyyyMMddHHmmss");
				//String strNow = CalendarUtil.getDateTime("yyyyMMddHHmmss");
				SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
				Date date = formatter.parse(dateString);	
				Date date2 = formatter.parse(dateString);	
				Calendar calendar = Calendar.getInstance();	
				Calendar calendar2 = Calendar.getInstance();	
				calendar.setTime(date);
				calendar2.setTime(date2);
				if(delAddDate!=null){
					calendar.add(Calendar.DAY_OF_MONTH,+Integer.parseInt(delAddDate) );
				}else{
					calendar.add(Calendar.DAY_OF_MONTH,+7);
				}
				calendar2.add(Calendar.DAY_OF_MONTH,+7);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());
				getDel_dd=formatter.format(calendar2.getTime());
				String rsvprdcd = selectRsvprdcdInfo(roleDO.getMasterId());
				stmt.setString(++index, roleDO.getTitle());	
				stmt.setString(++index, roleDO.getBrd_dd());
				stmt.setString(++index, roleDO.getBrd_len());
				stmt.setString(++index, roleDO.getDisuse_cont());
				stmt.setString(++index, dateString2);		
				stmt.setString(++index, roleDO.getRsv_prd_end_dd());
				stmt.setString(++index, getDel_dd);
				stmt.setLong(++index, roleDO.getMasterId());
				stmt.setString(++index, "Y");
				stmt.setString(++index, "000");
				stmt.setString(++index, rsvprdcd);
				stmt.setString(++index, roleDO.getReg_id());
				updateDisuseInfo(getRsv_prd_end_dd,roleDO.getMasterId(),CalendarUtil.getDateTime("yyyyMMdd"));
				updateDisuseInfoForMapp(roleDO.getMasterId(),getDel_dd);

			} catch(Exception e) {
				e.printStackTrace();
			}






			int itmp = stmt.executeUpdate();

			//사용자 정보의 수정 내역을 등록한다.
			//insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
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
	 * 페기,연장 정보 등록을 취소 한다. < -사용하지 않음
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse(int master_id) throws Exception
	{

		DiscardDO discardDO =  getDisCardInfo(master_id);
		updateCancleInfo(discardDO.getRsv_prd_end_dd(), discardDO.getPre_rsv_prd_cd(),master_id);
		StringBuffer buf = new StringBuffer();
		buf.append("\n DELETE FROM DAS.DISCARD_INFO_TBL ");	

		buf.append("\n WHERE MASTER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancelDisuse######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;	
			stmt.setInt(++index, master_id);
			int itmp = stmt.executeUpdate();

			//사용자 정보의 수정 내역을 등록한다.
			//insertNonEmployeeRoleHistory(con, roleDO);
			cancleDisuseInfoForMapp(master_id);
			con.commit();

			return itmp;
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
	 * 페기,연장 정보 등록을 취소 한다. 
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse2(String master_id) throws Exception
	{
		int index = 0;	
		StringBuffer buf = new StringBuffer();
		String[] Ids = master_id.split(","); 
		buf.append("\n DELETE FROM DAS.DISCARD_INFO_TBL ");	
		buf.append("\n WHERE MASTER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancelDisuse2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			for(int i = 0;i<Ids.length;i++){
				DiscardDO discardDO =  getDisCardInfo(Integer.parseInt(Ids[i]));
				updateCancleInfo(discardDO.getRsv_prd_end_dd(), discardDO.getPre_rsv_prd_cd(),Integer.parseInt(Ids[i]));

				index = 0;	

				//
				stmt.setInt(++index, Integer.parseInt(Ids[i]));

				cancleDisuseInfoForMapp(Integer.parseInt(Ids[i]));

				stmt.executeUpdate();

				//사용자 정보의 수정 내역을 등록한다.
				//insertNonEmployeeRoleHistory(con, roleDO);
			}
			con.commit();
			return 1;
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
	 * 연장 정보를 등록 한다.
	 * @param roleDO 연장 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 */


	public int[] insertUse(List roleDO) throws Exception
	{
		int index=0;


		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DISCARD_INFO_TBL( ");	

		buf.append("\n 	TITLE,  ");		
		buf.append("\n 	BRD_LEN, ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	RSV_PRD_END_DD,  ");
		buf.append("\n 	PRE_RSV_PRD_CD,  ");
		buf.append("\n 	RSV_PRD_CD,  ");	
		buf.append("\n 	USE_CONT, ");	
		buf.append("\n 	MASTER_ID,  ");
		buf.append("\n 	DISUSE_YN,  ");
		buf.append("\n 	reg_id  ");
		buf.append("\n )  ");
		buf.append("\n values  ");


		buf.append("\n ( ? , ?,?,?, ? ,?, ?,? ,?,?)  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertUse######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());



			for(int i=0;i<roleDO.size();i++){
				DiscardDO roleDOs = (DiscardDO)roleDO.get(i);
				String beforeInfoDO = selectRsvprdcdInfo(roleDOs.getMaster_id());
				logger.debug("beforeInfoDO =="+beforeInfoDO);
				index = 0;
				//날짜계산
				String getRsv_prd_end_dd = "";

				String dateTime = CalendarUtil.getDateTime("yyyyMMdd");
				try{
					//String dateString = roleDOs.getRsv_prd_end_dd();

					SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

					Date date = formatter.parse(dateTime);	
					
					Calendar calendar = Calendar.getInstance();		     
					if(roleDOs.getRsv_prd_cd().equals("000")){//영구
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +9999);
						
					} else if(roleDOs.getRsv_prd_cd().equals("003")){//3일
						calendar.setTime(date);
						calendar.add(Calendar.DAY_OF_MONTH, +3);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());
						    	  
					}else if(roleDOs.getRsv_prd_cd().equals("005")){//5일
						calendar.setTime(date);
						calendar.add(Calendar.DAY_OF_MONTH, +5);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
					}else if(roleDOs.getRsv_prd_cd().equals("030")){//한달
						calendar.setTime(date);
						calendar.add(Calendar.DAY_OF_MONTH, +30);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());    	  
					}else if(roleDOs.getRsv_prd_cd().equals("060")){//5년
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +5);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());

					}else if(roleDOs.getRsv_prd_cd().equals("120")){//10년
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +10);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());			    	  
					}else if(roleDOs.getRsv_prd_cd().equals("240")){//20년
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +20);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
					}else if(roleDOs.getRsv_prd_cd().equals("360")){//30년
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +30);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
					}else if(roleDOs.getRsv_prd_cd().equals("001")){//1년
						calendar.setTime(date);
						calendar.add(Calendar.YEAR, +1);
						getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
					}
				} catch(Exception e) {
					e.printStackTrace();
				}

				stmt.setString(++index, roleDOs.getTitle());	
				stmt.setString(++index, roleDOs.getBrd_len());
				stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));
				stmt.setString(++index,getRsv_prd_end_dd);
				stmt.setString(++index, beforeInfoDO);
				stmt.setString(++index, roleDOs.getRsv_prd_cd());		
				stmt.setString(++index, roleDOs.getUse_cont());
				stmt.setInt(++index, roleDOs.getMaster_id());
				stmt.setString(++index, "N");
				stmt.setString(++index, roleDOs.getReg_id());
				
				updateUseInfo(getRsv_prd_end_dd,roleDOs.getRsv_prd_cd(),roleDOs.getMaster_id());
				cancleDisuseInfoForMapp(roleDOs.getMaster_id());
				stmt.addBatch();

			}


			int[] rInt = null;	
			if(roleDO.size()>0)rInt =stmt.executeBatch();
			con.commit();
			return rInt;



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



	/*public int insertUse(DiscardDO roleDO) throws DASException
{

	String beforeInfoDO = selectRsvprdcdInfo(roleDO.getMaster_id());
	logger.debug("beforeInfoDO =="+beforeInfoDO);
	StringBuffer buf = new StringBuffer();
	buf.append("\n insert into DAS.DISCARD_INFO_TBL( ");	

	buf.append("\n 	TITLE,  ");		
	buf.append("\n 	BRD_LEN, ");
	buf.append("\n 	REG_DT,  ");
	buf.append("\n 	RSV_PRD_END_DD,  ");
	buf.append("\n 	PRE_RSV_PRD_CD,  ");
	buf.append("\n 	RSV_PRD_CD,  ");	
	buf.append("\n 	USE_CONT, ");	
	buf.append("\n 	MASTER_ID,  ");
	buf.append("\n 	DISUSE_YN  ");

	buf.append("\n )  ");
	buf.append("\n values  ");


		buf.append("\n ( ? , ?,?,?, ? ,?, ?,? ,?)  ");
	Connection con = null;
	PreparedStatement stmt = null;
	try 
	{
		con = DBService.getInstance().getConnection();
		con.setAutoCommit(false);

		stmt = con.prepareStatement(buf.toString());

		int index = 0;

		//날짜계산
		try{
			String getRsv_prd_end_dd = "";
			String dateString = roleDO.getRsv_prd_end_dd();
			logger.debug("dateString    "+dateString);
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
			Date date = formatter.parse(dateString);			
		    Calendar calendar = Calendar.getInstance();		     
		      if(roleDO.getRsv_prd_cd().equals("000")){
		      calendar.setTime(date);
		      calendar.add(Calendar.YEAR, +9999);
		      logger.debug(formatter.format(calendar.getTime()));
		      } else if(roleDO.getRsv_prd_cd().equals("003")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.DAY_OF_MONTH, +3);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());
			      logger.debug(getRsv_prd_end_dd);		    	  
		      }else if(roleDO.getRsv_prd_cd().equals("005")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.DAY_OF_MONTH, +5);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
		      }else if(roleDO.getRsv_prd_cd().equals("030")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.DAY_OF_MONTH, +30);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());    	  
		      }else if(roleDO.getRsv_prd_cd().equals("060")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.DAY_OF_MONTH, +60);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());

		      }else if(roleDO.getRsv_prd_cd().equals("120")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.YEAR, +120);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());			    	  
		      }else if(roleDO.getRsv_prd_cd().equals("240")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.YEAR, +240);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
		      }else if(roleDO.getRsv_prd_cd().equals("360")){
		    	  calendar.setTime(date);
			      calendar.add(Calendar.YEAR, +360);
			      getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
		      }


		      stmt.setString(++index, roleDO.getTitle());	
				stmt.setString(++index, roleDO.getBrd_len());
				stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmm"));
				stmt.setString(++index,getRsv_prd_end_dd);
				stmt.setString(++index, beforeInfoDO);
				stmt.setString(++index, roleDO.getRsv_prd_cd());		
				stmt.setString(++index, roleDO.getUse_cont());
				stmt.setInt(++index, roleDO.getMaster_id());
				stmt.setString(++index, "N");



				updateUseInfo(roleDO.getRsv_prd_end_dd(),roleDO.getRsv_prd_cd(),roleDO.getMaster_id());
		      	} catch(Exception e) {
			      e.printStackTrace();
		    }











		int itmp = stmt.executeUpdate();

		//사용자 정보의 수정 내역을 등록한다.
		//insertNonEmployeeRoleHistory(con, roleDO);

		con.commit();
		return itmp;
	} 
	catch (NamingException e) 
	{
		// TODO 자동 생성된 catch 블록
		e.printStackTrace();

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
	catch (SQLException e) 
	{
		// TODO 자동 생성된 catch 블록
		e.printStackTrace();

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
	 */
	/**
	 *  폐기 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getHyenDisCardList(DiscardDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(DisuseStatement.selecHyenDiscardList(condition));


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getHyenDisCardList######## con : " + con);


			stmt = con.prepareStatement(buf.toString());

			int totalCount  = 
					getTotalCount(con, DisuseStatement.selecHyenDiscardCount());

			//int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();

				item.setSeq(		rs.getInt("rownum"));
				item.setTitle(				rs.getString("title"));
				item.setBrd_dd(			rs.getString("brd_dd"));
				item.setBrd_len(rs.getString("brd_len"));
				item.setDisuse_cont(		rs.getString("disuse_cont"));
				item.setReg_dt(		rs.getString("reg_dt"));			
				item.setRsv_prd_end_dd(		rs.getString("rsv_prd_end_dd"));
				item.setDisuse_end_dd(		rs.getString("DEL_DD"));
				item.setDisuse_sta(		rs.getString("DISUSE_STA"));
				item.setMaster_id(		rs.getInt("master_id"));
				item.setReg_nm(		rs.getString("user_nm"));
				item.setTotalcount(totalCount);
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
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectSumHyenDiscard(DiscardDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		// 만약 처음으로 검색하는 거라면(다음 페이지가 아니라) 그 검색의 전체 데이터 갯수를 넘겨줘야 한다.
		buf.append(DisuseStatement.selectSumHyenDiscard(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());

			//int index = 0;
			updateDel_StaInfo();
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();



				item.setTotalcount(rs.getInt("CCOUNT"));
				item.setBrd_leng_sum(rs.getInt("SUM_BRD_LENG"));
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
	 *  연장 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getHyenUseList(DiscardDO condition) throws Exception
	{


		StringBuffer buf = new StringBuffer();

		buf.append(DisuseStatement.selecHyenUseList(condition, DASBusinessConstants.PageQueryFlag.NORMAL));




		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//총 조회 갯수를 구한다.
			int totalCount  = 0;



			stmt = con.prepareStatement(buf.toString());

			//int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();

				item.setSeq(		rs.getInt("rownum"));
				item.setTitle(				rs.getString("title"));
				item.setBrd_len(rs.getString("brd_len"));
				item.setReg_dt(		rs.getString("reg_dt"));
				item.setRsv_prd_start_dd(		rs.getString("rsv_prd_end_dd"));
				item.setRsv_prd_end_dd(		rs.getString("rsv_prd_end_dd"));
				item.setPre_rsv_prd_cd(			rs.getString("pre_rsv_prd_cd"));
				item.setRsv_prd_cd(			rs.getString("rsv_prd_cd"));
				item.setMedia_id(			rs.getString("media_id"));
				item.setUse_count(			rs.getString("cnt"));
				item.setMaster_id(			rs.getInt("master_id"));


				resultList.add(item);
			}

			//int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);


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
	 * 연장 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectSumHyenuse(DiscardDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		// 만약 처음으로 검색하는 거라면(다음 페이지가 아니라) 그 검색의 전체 데이터 갯수를 넘겨줘야 한다.
		buf.append(DisuseStatement.selectSumHyenuse(condition));




		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());

			updateDel_StaInfo();
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				DiscardDO item = new DiscardDO();



				item.setTotalcount(rs.getInt("CCOUNT"));
				item.setBrd_leng_sum(rs.getInt("SUM_BRD_LENG"));
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
	 * 보존기간코드 정보를 조회한다.
	 * @param MASTER_ID
	 * @param 
	 * @return NonEmployeeDASRoleDO 외부 사용자 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public String selectRsvprdcdInfo(long masterId) throws Exception
	{
		String query = DisuseStatement.selectRsvprdcdQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setLong(++index, masterId);

			rs = stmt.executeQuery();

			if(rs.next())
			{
				DiscardDO item = new DiscardDO();

				item.setRsv_prd_cd(			rs.getString("rsv_prd_cd"));
				String cd=item.getRsv_prd_cd();
				return cd;

			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;

			}
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
	 * 폐기정보를  수정한다.
	 * @param rsvdd 보존기간
	 * @param masterId 마스터id
	 * @param delDD 삭제일
	 * @throws Exception 
	 */
	public int updateDisuseInfo(String rsvdd, long masterId , String delDD) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL SET ");

		buf.append("\n 	 RSV_PRD_END_DD= ?  ");
		buf.append("\n   ,DEL_DD = ? ");
		buf.append("\n 	 where  ");
		buf.append("\n MASTER_ID= ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setString(++index, rsvdd);
			stmt.setString(++index, delDD);
			stmt.setLong(++index, masterId);



			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

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
	 * contents_mapp_tbl에 삭제등록한다
	 * @param master_id 마스터id
	 * @param del_dd 삭제일
	 * @throws Exception 
	 */
	public int updateDisuseInfoForMapp(long master_id, String del_dd) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.contents_mapp_tbl SET ");

		buf.append("\n 	 del_dd= ?  ");
		buf.append("\n 	 where  ");
		buf.append("\n MASTER_ID= ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, del_dd);
			stmt.setLong(++index, master_id);

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

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
	 * 연장정보를 반영한다.
	 * @param rsvdd 보존기간
	 * @param rsvcd 보존기간 코드
	 * @param masterId 마스터id
	 * @throws Exception 
	 */
	public int updateUseInfo(String rsvdd, String rsvcd,int masterId) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL SET ");

		buf.append("\n 	 RSV_PRD_END_DD= ? ,  ");
		buf.append("\n 	 RSV_PRD_CD= ?  ");

		buf.append("\n 	 where  ");
		buf.append("\n MASTER_ID= ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			//String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			stmt.setString(++index, rsvdd);
			stmt.setString(++index, rsvcd);
			stmt.setInt(++index, masterId);

			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

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
	 *  특정 폐기 정보를  조회한다.
	 * @param master_id 마스터id
	 * @throws Exception 
	 */
	public DiscardDO getDisCardInfo(int master_id) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(DisuseStatement.selecDiscardInfo(master_id));
		buf.append(" )                                         	\n");
		buf.append(" WITH UR	 \n");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();
			DiscardDO item = new DiscardDO();
			while(rs.next())
			{

				item.setRsv_prd_end_dd(		rs.getString("rsv_prd_end_dd"));

				item.setPre_rsv_prd_cd(		rs.getString("pre_rsv_prd_cd"));

				item.setMaster_id(		master_id);
				resultList.add(item);

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


	/**
	 * 취소정보를 반영한다.
	 * @param rsvdd 보존기간 
	 * @param rsvcd 보존기간 코드
	 * @param masterId 마스터id
	 * @param
	 * @throws Exception 
	 */
	public int updateCancleInfo(String rsvdd, String rsvcd,int masterId) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.METADAT_MST_TBL SET ");
		if(rsvcd.equals("")){
			buf.append("\n 	 RSV_PRD_END_DD= ?  ");	

		}else {
			buf.append("\n 	 RSV_PRD_END_DD= ?,  ");	
			buf.append("\n 	 RSV_PRD_CD= ?,  ");
			buf.append("\n 	 DEL_DD= ?  ");
		}
		buf.append("\n 	 where  ");
		buf.append("\n MASTER_ID= ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			//String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			if(rsvcd.equals("")){

				stmt.setString(++index, rsvdd);

			}else {

				stmt.setString(++index, rsvdd);

				stmt.setString(++index, rsvcd);
				stmt.setString(++index, "");
			}
			stmt.setInt(++index, masterId);



			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

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
	 * 폐기정보를  수정한다.(del_dd기준)
	 * @throws Exception 
	 * 
	 */
	public int updateDel_StaInfo() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DISCARD_INFO_TBL SET ");

		buf.append("\n 	    disuse_sta='004' where DEL_DD <> '' ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//int index = 0;

			int updateCount = stmt.executeUpdate();


			return updateCount;

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
	 * 폐기 검색결과의 갯수를 가져온다.
	 * @param conditionDO  정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 *
	 */
	public Map getMetadatInfoListCount(DiscardDO conditionDO) throws Exception
	{
		conditionDO.setQueryResultCount(true);
		String query = DisuseStatement.selectDisCardInfoQuery(conditionDO);
		conditionDO.setQueryResultCount(false);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map nMap = new HashMap();
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();	

			//int nCount = 0;
			if (rs.next()){
				nMap.put("CCOUNT", rs.getInt("CCOUNT"));
				nMap.put("SUM_BRD_LENG",rs.getInt("SUM_BRD_LENG"));
			}
			return nMap;
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
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param master_id 마스터id
	 * @return
	 * @throws Exception 
	 */
	public int isThereMasterId(int master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		Map value = null;

		buf.append(DisuseStatement.selectUseDiscard(master_id));




		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereMasterId######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			//int index = 0;
			updateDel_StaInfo();
			rs = stmt.executeQuery();

			//int indexCount = 0;
			List resultList = new ArrayList();
			int master=0;
			DiscardDO item = new DiscardDO();
			while(rs.next())
			{



				item.setMaster_id(rs.getInt("master_id"));



			}

			master=item.getMaster_id();

			return master;
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
	 *  폐기 연장 등록전에 마스터id기준으로 데이터를 삭제한다
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */

	public int deleteUse(int master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n DELETE FROM DAS.DISCARD_INFO_TBL ");	

		buf.append("\n WHERE MASTER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteUse######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;	

			stmt.setInt(++index, master_id);

			int itmp = stmt.executeUpdate();


			con.commit();
			return itmp;
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
	 * contents_mapp_tbl에 삭제 취소한다
	 * @param master_id 마스터id
	 * @throws Exception 
	 */
	public int cancleDisuseInfoForMapp(int master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.contents_mapp_tbl SET ");

		buf.append("\n 	 del_dd= ?  ");
		buf.append("\n 	 where  ");
		buf.append("\n MASTER_ID= ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancleDisuseInfoForMapp######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;


			stmt.setString(++index, "");
			stmt.setInt(++index, master_id);



			int updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
				throw exception;
			}
			return updateCount;

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



}

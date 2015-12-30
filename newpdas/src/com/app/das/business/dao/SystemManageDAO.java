package com.app.das.business.dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.CharBuffer;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;




import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.CodeInfoStatement;
import com.app.das.business.dao.statement.SystemManageStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.ContentsUseInfoDO;
import com.app.das.business.transfer.CopyInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DeleteDO;
import com.app.das.business.transfer.DownStatusInfoDO;
import com.app.das.business.transfer.EquipmentMonitoringDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.business.transfer.GoodMediaDO;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.NleDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.PaUserInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.business.transfer.PdsMappDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PreviewDO;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.StorageDO;
import com.app.das.business.transfer.SubsiInfoDO;
import com.app.das.business.transfer.SystemManageConditionDO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.TodayDO;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.business.transfer.UserInfoDO;
import com.app.das.business.transfer.WmvH264DO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CommonUtl;
import com.app.das.util.DBService;
import com.app.das.util.LoggableStatement;



/**
 * 시스템 관리의 모니터링의 장비, 작업, ID별 자료이용현황의 조회 및  미접속 ID현황의 조회 및 중지, 복구에 대한 
 * Database 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class SystemManageDAO extends AbstractDAO 
{
	private Logger logger = Logger.getLogger(SystemManageDAO.class);

	private static SystemManageDAO systemManageDAO = SystemManageDAO.getInstance();
	private static  UserRoleDAO userRoleDAO = UserRoleDAO.getInstance();
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();

	private static SystemManageDAO instance;

	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	private static  CodeInfoDAO codeInfoDAO = CodeInfoDAO.getInstance();
	/**
	 * A private constructor
	 *
	 */
	private SystemManageDAO() 
	{
	}

	public static synchronized SystemManageDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new SystemManageDAO();
		}
		return instance;
	}

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectEquipmentMonitoringListForMax(DASCommonDO commonDO) throws Exception
	{
		String query = SystemManageStatement.selectEquipmentMonitoringListForMaxQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEquipmentMonitoringListForMax######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			String lastTime = null;
			int period;

			while(rs.next())
			{
				EquipmentMonitoringDO item = new EquipmentMonitoringDO();
				item.setDasEqNm(			rs.getString("DAS_EQ_NM"));
				item.setDasEqClfNm(		rs.getString("DAS_EQ_CLF_NM"));
				if(rs.getString("DAS_EQ_CLF_CD").equals(CodeConstants.DeviceType.SDI))
					item.setCtNm(		rs.getString("SCN_TTL"));
				else if(rs.getString("DAS_EQ_CLF_CD").equals(CodeConstants.DeviceType.FILE))
					item.setCtNm(		rs.getString("MCUID"));
				else
					item.setCtNm(		rs.getString("CT_NM"));
				item.setDasEqClfCd(			rs.getString("DAS_EQ_CLF_CD"));
				item.setReqUsrId(				rs.getString("REQ_USRID"));
				item.setDasWorkstatNm(	rs.getString("DAS_WORKSTAT_NM"));  //MHCHOI
				item.setDasWorkstatCd(	rs.getString("DAS_WORKSTAT_CD"));
				item.setDasEqPsNm(		rs.getString("DAS_EQ_PS_NM"));
				item.setPrgrs(					rs.getString("PRGRS"));
				lastTime = rs.getString("MOD_DT");
				item.setRegDd(				lastTime);


				// 장비 중지여부 확인 
				period = rs.getInt("LOG_RCD_PERIOD");
				try{
					if(StringUtils.isEmpty(lastTime))
						//lastTime == null || lastTime == "")		
						item.setEquStop(DASBusinessConstants.YesNo.YES);
					else
					{
						int gap = CalendarUtil.getBetweenDatesFull(CalendarUtil.getDateTime("yyyyMMddHHmmss"),lastTime );
						if (period <= (gap + 30)) // add 30 second buffer time
							item.setEquStop(DASBusinessConstants.YesNo.YES);
						else
							item.setEquStop(DASBusinessConstants.YesNo.NO);		
					}
				}
				catch(ParseException e) {
					//					 TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}

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
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectEquipmentMonitoringListForMax(String workStateCode, DASCommonDO commonDO) throws Exception
	{
		String query = SystemManageStatement.selectEquipmentMonitoringListForMaxQuery(workStateCode);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEquipmentMonitoringListForMax######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			if(!StringUtils.isEmpty(workStateCode))
			{
				stmt.setString(++index, workStateCode);
			}
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			String lastTime = null;
			int period;

			while(rs.next())
			{
				EquipmentMonitoringDO item = new EquipmentMonitoringDO();
				item.setDasEqNm(			rs.getString("DAS_EQ_NM"));
				item.setDasEqClfNm(		rs.getString("DAS_EQ_CLF_NM"));
				if(rs.getString("DAS_EQ_CLF_CD").equals(CodeConstants.DeviceType.SDI))
					item.setCtNm(		rs.getString("SCN_TTL"));
				else if(rs.getString("DAS_EQ_CLF_CD").equals(CodeConstants.DeviceType.FILE))
					item.setCtNm(		rs.getString("MCUID"));
				else
					item.setCtNm(		rs.getString("CT_NM"));
				item.setDasEqClfCd(			rs.getString("DAS_EQ_CLF_CD"));
				item.setReqUsrId(				rs.getString("REQ_USRID"));
				item.setDasWorkstatNm(	rs.getString("DAS_WORKSTAT_NM"));  //MHCHOI
				item.setDasWorkstatCd(	rs.getString("DAS_WORKSTAT_CD"));
				item.setDasEqPsNm(		rs.getString("DAS_EQ_PS_NM"));
				item.setPrgrs(					rs.getString("PRGRS"));
				lastTime = rs.getString("MOD_DT");
				item.setRegDd(				lastTime);


				// 장비 중지여부 확인 
				period = rs.getInt("LOG_RCD_PERIOD");
				try{
					if(StringUtils.isEmpty(lastTime) || lastTime.indexOf(" ") > 0)
						//lastTime == null || lastTime == "")		
						item.setEquStop(DASBusinessConstants.YesNo.YES);
					else
					{
						int gap = CalendarUtil.getBetweenDatesFull(CalendarUtil.getDateTime("yyyyMMddHHmmss"),lastTime );
						if (period <= (gap + 30)) // add 30 second buffer time
							item.setEquStop(DASBusinessConstants.YesNo.YES);
						else
							item.setEquStop(DASBusinessConstants.YesNo.NO);		
					}
				}
				catch(ParseException e) {
					//					 TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}

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
	 * 모니터링 장비에서 로그테이블의 장비구분코드별 당일 것을 조회한다.
	 * @param eqClfCd 장비구분코드
	 * @param commonDO
	 * @return EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectEquipmentMonitoringListForToDate(String eqClfCd, DASCommonDO commonDO) throws Exception
	{
		String query = SystemManageStatement.selectEquipmentMonitoringListForToDateQuery(eqClfCd);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEquipmentMonitoringListForToDate######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, CalendarUtil.getToday());
			if(!StringUtils.isEmpty(eqClfCd))
			{
				stmt.setString(++index, eqClfCd);   //MHCHOI
				//stmt.setString(++index, "000");  // TO_DO resolve hard code 
			}

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			String lastTime = null;
			int period;

			while(rs.next())
			{

				EquipmentMonitoringDO item = new EquipmentMonitoringDO();


				item.setDasEqNm(			rs.getString("DAS_EQ_NM"));
				item.setDasEqClfNm(		rs.getString("DAS_EQ_CLF_NM"));
				item.setDasEqClfCd(			rs.getString("DAS_EQ_CLF_CD"));
				item.setReqUsrId(				rs.getString("REQ_USRID"));			
				item.setDasWorkstatNm(	rs.getString("DAS_WORKSTAT_NM")); 
				item.setDasWorkstatCd(	rs.getString("DAS_WORKSTAT_CD"));
				item.setDasEqPsNm(		rs.getString("DAS_EQ_PS_NM"));
				item.setPrgrs(					rs.getString("PRGRS"));
				//	lastTime = rs.getString("MOD_DT");
				lastTime = rs.getString("MAXDATE");
				item.setRegDd(				lastTime);
				item.setEquStop(DASBusinessConstants.YesNo.NO);

				// 장비 중비여부 확인 
				//period = rs.getInt("LOG_RCD_PERIOD");				
				/*
				try{
					if(lastTime == null || lastTime == "")
						item.setEquStop(DASBusinessConstants.YesNo.YES);
					else
					{
						int gap = CalendarUtil.getBetweenDatesFull(CalendarUtil.getDateTime("yyyyMMddHHmmss"),lastTime );
						if (period <= ((gap + 30)) )
							item.setEquStop(DASBusinessConstants.YesNo.YES);
						else
							item.setEquStop(DASBusinessConstants.YesNo.NO);	
					}
				}
				catch(ParseException e) {
				// TODO 자동 생성된 catch 블록
					e.printStackTrace();
				}				
				 */
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
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param conditionDO 조회조건을 담고있는 beans
	 * @return PageDO EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectEquipmentMonitoringListForWork(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectEquipmentMonitoringListForWorkQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectEquipmentMonitoringListForWork######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectEquipmentMonitoringListForWorkQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.EQUIPMENT_ROW_COUNT;

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
				EquipmentMonitoringDO item = new EquipmentMonitoringDO();
				item.setCtNm(					rs.getString("CT_NM"));
				item.setDasEqNm(			rs.getString("DAS_EQ_NM"));
				item.setDasEqClfNm(		rs.getString("DAS_EQ_CLF_NM"));
				item.setDasEqClfCd(			rs.getString("DAS_EQ_CLF_CD"));
				item.setReqUsrId(				rs.getString("REQ_USRID"));			
				item.setDasWorkstatNm(	rs.getString("DAS_WORKSTAT_NM"));
				item.setDasWorkstatCd(	rs.getString("DAS_WORKSTAT_CD"));
				item.setDasEqPsNm(		rs.getString("DAS_EQ_PS_NM"));
				if ("002".equals(rs.getString("DAS_EQ_PS_CD")) && Integer.parseInt(rs.getString("total_size"))!=0) {
					item.setPrgrs(String.valueOf(Integer.parseInt(rs.getString("DOWN_VOL"))/Integer.parseInt(rs.getString("total_size"))*100));
				} else {
					item.setPrgrs(					rs.getString("DOWN_VOL"));
				}
				item.setRegDd(					rs.getString("MOD_DT"));
				item.setSerialNo(				rs.getInt("rownum"));

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
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO
	 * @return EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectEquipmentLogList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectEquipmentLogListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectEquipmentLogList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectEquipmentLogListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.EQUIPMENT_ROW_COUNT;

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
				EquipmentMonitoringDO item = new EquipmentMonitoringDO();
				item.setDd(						rs.getString("DD"));
				item.setDasEqClfNm(		rs.getString("DAS_EQ_CLF_NM"));
				item.setDasWorkstatNm(	rs.getString("DAS_WORK_NM"));
				item.setDasEqClfCd(			rs.getString("DAS_EQ_CLF_CD"));
				item.setDasEqNm(			rs.getString("DAS_EQ_NM"));
				item.setDasWorkstatNm(	rs.getString("DAS_WORKSTAT_NM"));
				item.setDasWorkstatCd(	rs.getString("DAS_WORKSTAT_CD"));
				item.setDasEqPsNm(		rs.getString("DAS_EQ_PS_NM"));
				item.setReqUsrId(				rs.getString("REQ_USRID"));
				item.setReqTm(				rs.getString("MOD_DT"));
				item.setCtNm(		rs.getString("CT_NM"));
				item.setUserNm(				rs.getString("USER_NM"));
				item.setMasterId(				rs.getString("MASTER_ID"));
				if ("002".equals(rs.getString("DAS_EQ_PS_CD")) && Integer.parseInt(rs.getString("total_size"))!=0) {
					item.setPrgrs(String.valueOf(Integer.parseInt(rs.getString("DOWN_VOL"))/Integer.parseInt(rs.getString("total_size"))*100));
				} else {
					item.setPrgrs(					rs.getString("DOWN_VOL"));
				}
				item.setCtId(						rs.getString("CT_ID"));
				item.setRegDd(					rs.getString("REG_DT"));
				item.setSerialNo(				rs.getInt("rownum"));


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
	 * ID별 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO selectContentsUseInfoList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectContentsUseInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectContentsUseInfoList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectContentsUseInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{
				ContentsUseInfoDO item = new ContentsUseInfoDO();
				item.setReqUsrId(			rs.getString("REQ_USRID"));
				item.setUserNm(			rs.getString("USER_NM"));

				//item.setCtNm(			rs.getString("TITLE") + " - " +	rs.getString("CT_NM"));
				item.setCtId(			rs.getString("CT_ID"));
				item.setCtNm(			rs.getString("CT_NM"));
				item.setDownDt(			rs.getString("REQ_DT"));
				item.setSom(				rs.getString("SOM"));
				item.setEom(				rs.getString("EOM"));
				item.setCart_No(rs.getString("CART_NO"));
				item.setRist_clf_cd(rs.getString("RIST_CLF_CD"));
				item.setCart_Seq(				rs.getString("CART_SEQ"));
				item.setTitle(				rs.getString("TITLE"));

				resultList.add(item);
			}
			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.CONTENTS_USE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

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
	 * 미접속 ID 현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List UserInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectNonLoginUserList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectNonLoginUserListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectNonLoginUserList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectNonLoginUserListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.EQUIPMENT_ROW_COUNT;

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
				UserInfoDO item = new UserInfoDO();
				item.setUserId(							rs.getString("OUT_USER_ID"));
				item.setUserNm(						rs.getString("OUT_USER_NM"));
				item.setEtcNm(							rs.getString("CO_NM"));
				item.setPhoneNo(						rs.getString("CNTC_PLC_OUTS"));
				item.setGuarantorNm(				rs.getString("GUARANTOR_NM"));
				item.setGuarantorPhoneNo(		rs.getString("GUARANTOR_HAND_PHON"));
				item.setStopDate(						rs.getString("VLDDT_END"));

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
	 * 사용 중지된 외부 사용자의 유효 종료일을 복구시킨다
	 * @param userInfoDOList 사용자 정보인 UserInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateNonLoginUserRestoreList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateNonLoginUserRestoreList######## con : " + con);
			con.setAutoCommit(false);

			for(Iterator i = userInfoDOList.iterator(); i.hasNext();)
			{
				UserInfoDO item = (UserInfoDO)i.next();

				int preHistSeq = this.selectMaxOutUserHistorySeq(item.getUserId());				

				if(preHistSeq > 0)
				{
					item.setTrxKind(DASBusinessConstants.TrxKind.UPDATE);

					String updateEndDate = selectPreOutUserHistory(item.getUserId(), preHistSeq);
					item.setUpdateEndDate(updateEndDate);
				}
			}


			for(Iterator i = userInfoDOList.iterator(); i.hasNext();)
			{
				UserInfoDO item = (UserInfoDO)i.next();

				if(DASBusinessConstants.TrxKind.UPDATE.equals(item.getTrxKind()))
				{
					updateNonLoginUserValidEndDate(con, item, item.getUpdateEndDate(), commonDO);
				}
			}

			con.commit();
		} 

		catch (Exception e) 
		{
			logger.error("userInfoDOList : " + userInfoDOList);
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
			release(null, null, con);
		}

	}

	/**
	 * 미접속 외부 사용자를 중지시킨다.
	 * @param userInfoDOList UserInfoDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateOutUserStopList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOutUserStopList######## con : " + con);
			con.setAutoCommit(false);

			String updateEndDate = CalendarUtil.beforeSpecDay(CalendarUtil.getToday(), 1);


			for(Iterator i = userInfoDOList.iterator(); i.hasNext();)
			{
				UserInfoDO item = (UserInfoDO)i.next();

				//사용자 검색을 한다.
				NonEmployeeDASRoleDO roleDO = 
						UserRoleDAO.getInstance().selectOutEmployeeInfo(item.getUserId());

				//날짜를 어제 날짜로 중지시킨다.
				updateNonLoginUserValidEndDate(con, item, updateEndDate, commonDO);

				//변경 전 데이터를 history에 넣어준다.
				UserRoleDAO.getInstance().insertNonEmployeeRoleHistory(con, roleDO);
			}

			con.commit();
		} 
		catch (Exception e) 
		{
			logger.error("userInfoDOList : " + userInfoDOList);

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
			release(null, null, con);
		}

	}

	/**
	 * 외부직원 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO selectContentsOutUseInfoList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectContentsOutUseInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" WITH UR	\n");

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
			//logger.debug("######selectContentsOutUseInfoList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectContentsOutUseInfoListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{
				ContentsUseInfoDO item = new ContentsUseInfoDO();
				item.setReqUsrId(			rs.getString("REQ_USRID"));
				item.setUserNm(			rs.getString("USER_NM"));
				item.setCtNm(				rs.getString("CT_NM"));
				item.setDownDt(			rs.getString("DOWN_DT"));
				item.setSom(				rs.getString("SOM"));
				item.setEom(				rs.getString("EOM"));
				item.setSubj(				rs.getString("DOWN_SUBJ"));

				resultList.add(item);
			}
			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

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
	 * 다운로드 진행상황를 목록 조회한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회 결과를 Page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public PageDO selectDownloadStatusList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectDownloadStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL, commonDO.getRole()));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectDownloadStatusList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectDownloadStatusListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT, commonDO.getRole()));

			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.COMMON_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.COMMON_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{

				DownStatusInfoDO item = new DownStatusInfoDO();
				item.setCartNo(					rs.getInt("CART_NO"));
				item.setCartSeq(					rs.getInt("CART_SEQ"));
				item.setReqUsrId(       		rs.getString("REQ_USRID"));
				item.setUserNm( 				rs.getString("USER_NM"));      
				item.setReqDt(          		rs.getString("REQ_DT"));
				item.setDownSubj(       	rs.getString("DOWN_SUBJ"));
				item.setVdQlty(         		rs.getString("VD_QLTY"));
				item.setVdQltyNm(       		rs.getString("VD_QLTY_NM"));
				item.setAspRtoCd(       		rs.getString("ASP_RTO_CD"));
				item.setAspRtoNm(  			rs.getString("ASP_RTO_NM"));
				item.setDeptNm( rs.getString("DEPT_NM"));
				if (rs.getInt("DOWN_SIZE") != 0) {
					item.setDownSize(       		FrameToTimecodeStrDF(rs.getInt("TOTAL_SIZE") * rs.getInt("DOWN_SIZE") / 100));
				} else {
					item.setDownSize(       		FrameToTimecodeStrDF(0));
				}
				item.setTotalSize(      		FrameToTimecodeStrDF(rs.getInt("TOTAL_SIZE")));

				//				BigDecimal percent = rs.getBigDecimal("PERCNT");

				if(rs.getInt("DOWN_SIZE") > 0 && rs.getInt("TOTAL_SIZE") > 0)
				{
					BigDecimal percent = new BigDecimal(String.valueOf(rs.getInt("DOWN_SIZE")));
					//					BigDecimal percent = 
					//						new BigDecimal(String.valueOf(rs.getInt("DOWN_SIZE"))).divide(new BigDecimal(String.valueOf(rs.getInt("TOTAL_SIZE"))), 2, BigDecimal.ROUND_DOWN);
					item.setPercnt(         		percent);
				}
				else
				{
					item.setPercnt(         		Constants.ZERO);
				}

				item.setDownStatusNm(	rs.getString("CART_STAT_NAME"));
				item.setDownStatus(			rs.getString("CART_STAT"));
				item.setMaster_id(			rs.getString("master_id"));
				item.setSerialNo(				rs.getInt("rownum"));

				resultList.add(item);
			}
			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.COMMON_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.COMMON_ROW_COUNT != 0 ? 1 : 0);

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
	 * 다운로드 진행사항 조회에서 상태를 사용중으로 복구한다.
	 * @param downStatusInfoDOList DownStatusInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateRecoveryDownloadStatusList(List downStatusInfoDOList, DASCommonDO commonDO) throws Exception
	{
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRecoveryDownloadStatusList######## con : " + con);
			con.setAutoCommit(false);


			String toDate = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			for(Iterator i = downStatusInfoDOList.iterator(); i.hasNext();)
			{
				DownStatusInfoDO item = (DownStatusInfoDO)i.next();
				updateRecoveryDownloadStatus(con, item, toDate, commonDO);
			}

			con.commit();
		} 

		catch (Exception e) 
		{
			logger.error("downStatusInfoDOList : "  + downStatusInfoDOList);

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
			release(null, null, con);
		}

	}


	private void updateNonLoginUserValidEndDate(Connection con, UserInfoDO item, String updateEndDate, DASCommonDO commonDO) throws SQLException, DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTSIDER_INFO_TBL set VLDDT_END = ? ");
		buf.append("\n where OUT_USER_ID = ? ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, updateEndDate);
			stmt.setString(++index, item.getUserId());

			int updateCount = stmt.executeUpdate();

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

	private String selectPreOutUserHistory(String userId, int seq) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	hist.VLDDT_END ");
		buf.append("\n from DAS.OUTSIDER_HIST_TBL hist ");
		buf.append("\n where hist.OUT_USER_ID = ? ");
		buf.append("\n 	and hist.SEQ = ? ");	
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPreOutUserHistory######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userId);
			stmt.setInt(++index, seq);

			rs = stmt.executeQuery();

			if(rs.next())
			{
				return rs.getString("VLDDT_END");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
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


	private int selectMaxOutUserHistorySeq(String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n select value(max(SEQ), 0) from DAS.OUTSIDER_HIST_TBL where OUT_USER_ID = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMaxOutUserHistorySeq######## con : " + con);
			stmt = con.prepareStatement(buf.toString());
			stmt.setString(1, userId);

			rs = stmt.executeQuery();

			rs.next();

			//int preSeq = rs.getInt(1) - 1;  MHCHOI Seems bug
			int preSeq = rs.getInt(1);

			return preSeq;

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

	private void updateRecoveryDownloadStatus(Connection con, DownStatusInfoDO item, String updateEndDate, DASCommonDO commonDO) throws SQLException, DASException
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DOWN_CART_TBL set ");
		buf.append("\n 	CART_STAT = ?, ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where CART_NO = ? ");

		PreparedStatement stmt = null;
		try 
		{

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, CodeConstants.CartStatus.USE);
			stmt.setString(++index, updateEndDate);
			stmt.setString(++index, commonDO.getUserId());
			stmt.setInt(++index, item.getCartNo());

			int updateCount = stmt.executeUpdate();

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
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public PageDO selectProgramList(SystemManageConditionDO condition, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectProgramListQuery(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" WITH UR	\n ");

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
			//logger.debug("######selectProgramList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectProgramListQuery(condition, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));



			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmCd(rs.getString("PGM_CD"));
				item.setPgmNm(rs.getString("PGM_NM"));
				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));

				resultList.add(item);
			}

			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

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
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_nm 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List selectParentsInfo(String pgm_nm) throws Exception
	{


		StringBuffer buf = new StringBuffer();

		buf.append(SystemManageStatement.selectParentsInfoQuery(pgm_nm));


		//Page에 따른 계산을 한다.


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectParentsInfo######## con : " + con);


			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.

			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmCd(rs.getString("PGM_CD"));
				item.setPgmNm(rs.getString("PGM_NM"));


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
	 * 프로그램 정보를 영구 삭제 한다.
	 * @param prgId 프로그램 ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteProgramInfo(String prgId, DASCommonDO commonDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append(" delete from  DAS.PGM_INFO_TBL  \n");
		buf.append(" where PGM_ID = ?		 		\n");
		buf.append(" WITH UR	 \n");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteProgramInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;          
			stmt.setString(++index, prgId);

			int deleteCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[delete Count]" + deleteCount);
			}

			if(deleteCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
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
			release(null, stmt, con);
		}
	}	

	/**
	 * 권한 코드를 삭제한다.
	 * @param prgId 프로그램 ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deleteScreenAuthentication(CodeDO codeDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from  DAS.ROLE_TBL ");
		buf.append("\n where AUTH_CD = ?  ");
		buf.append("\n 	with UR  ");

		StringBuffer buf1 = new StringBuffer();
		buf1.append("\n select count(1) from DAS.ROLE_TBL ");
		buf1.append("\n where AUTH_CD = ?  ");
		buf1.append("\n 	with UR  ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteScreenAuthentication######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, buf1.toString());
			stmt = con.prepareStatement(buf1.toString());

			int index = 0;          
			stmt.setString(++index, codeDO.getSclCd());

			rs = stmt.executeQuery();

			rs.next();
			// 삭제할 코드가 권한 테일블에 존재 하는지 확인하여 존재 하면 레코드를 삭제한다
			if (rs.getInt(1) != 0) {
				stmt = con.prepareStatement(buf.toString());

				index = 0;          
				stmt.setString(++index, codeDO.getSclCd());

				int deleteCount = stmt.executeUpdate();

				if (logger.isDebugEnabled()) 
				{
					logger.debug("[delete Count]" + deleteCount);
					return deleteCount;
				}

				if(deleteCount == 0)
				{
					//여기서 에러를 던진다.
					DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
					throw exception;
				}
			}


		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			logger.error(buf1.toString());


			throw e;

		} 
		finally
		{
			release(rs, stmt, con);
		}
		return 0;
	}	

	/**
	 * 프로그램 정보를 수정한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.PGM_INFO_TBL set ");
		buf.append("\n 	PGM_NM = ?,  ");
		buf.append("\n 	MEDIA_CD = ?,  ");
		buf.append("\n 	CHAN_CD = ?,  ");
		buf.append("\n 	CTGR_L_CD = ?,  ");
		buf.append("\n 	CTGR_M_CD = ?,  ");
		buf.append("\n 	CTGR_S_CD = ?,  ");
		buf.append("\n 	BRD_BGN_DD = ?,  ");
		buf.append("\n 	BRD_END_DD = ?,  ");
		buf.append("\n 	PGM_CD = ?,  ");
		buf.append("\n 	PRD_DEPT_NM = ?,  ");
		buf.append("\n 	SCHD_PGM_NM = ?,  ");
		buf.append("\n 	AWARD_HSTR = ?,  ");
		buf.append("\n 	PILOT_YN = ?,  ");
		buf.append("\n 	MODRID = ?,  ");
		//		buf.append("\n 	MOD_END_YN = ?,  ");
		buf.append("\n 	MOD_DT = SUBSTR(HEX(CURRENT TIMESTAMP), 1, 14) ");
		buf.append("\n where PGM_ID = ?  ");
		buf.append("\n WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			///logger.debug("######updateProgramInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, pgmDO.getPgmNm());
			stmt.setString(++index, pgmDO.getMediaCd());
			stmt.setString(++index, pgmDO.getChanCd());
			stmt.setString(++index, pgmDO.getCtgrLCd());
			stmt.setString(++index, pgmDO.getCtgrMCd());
			stmt.setString(++index, pgmDO.getCtgrSCd());
			stmt.setString(++index, pgmDO.getBrdBgnDd());
			stmt.setString(++index, pgmDO.getBrdEndDd());
			stmt.setString(++index, pgmDO.getPgmCd());
			stmt.setString(++index, pgmDO.getPrd_Dept_Nm());
			stmt.setString(++index, pgmDO.getSchd_Pgm_Nm());
			stmt.setString(++index, pgmDO.getAward_Hstr());
			stmt.setString(++index, pgmDO.getPilot_Yn());
			stmt.setString(++index, commonDO.getUserId());
			//			stmt.setString(++index, pgmDO.getMod_end_yn());
			stmt.setLong(++index, pgmDO.getPgmId());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
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
	 * 프로그램 정보를 조회한다.
	 * @param pgmId 프로그램id
	 * @return ProgramInfoDO 
	 * @throws Exception 
	 */
	public ProgramInfoDO selectProgramInfoByID(String pgmId) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	PGM_ID, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	MEDIA_CD, ");
		buf.append("\n 	CHAN_CD, ");
		buf.append("\n 	CTGR_L_CD, ");
		buf.append("\n 	CTGR_M_CD, ");
		buf.append("\n 	CTGR_S_CD, ");
		buf.append("\n 	BRD_BGN_DD, ");
		buf.append("\n 	BRD_END_DD, ");
		buf.append("\n 	PGM_CD, ");
		buf.append("\n 	PRD_DEPT_NM, ");
		buf.append("\n 	SCHD_PGM_NM, ");
		buf.append("\n 	AWARD_HSTR, ");
		buf.append("\n 	PILOT_YN ");
		//		buf.append("\n 	MOD_END_YN ");
		buf.append("\n from DAS.PGM_INFO_TBL ");
		buf.append("\n where PGM_ID = ? " );	
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectProgramInfoByID######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, pgmId);	

			rs = stmt.executeQuery();

			if(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmNm(rs.getString("PGM_NM"));
				item.setMediaCd(rs.getString("MEDIA_CD"));
				item.setChanCd(rs.getString("CHAN_CD"));
				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPgmCd(rs.getString("PGM_CD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));
				//				item.setMod_end_yn(rs.getString("MOD_END_YN"));

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
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

	public ProgramInfoDO selectERPProgramInfoByCode(String pgmCD) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		buf.append("\n select ");
		buf.append("\n 	'0' AS PGM_ID, ");
		buf.append("\n 	PGM_NM, ");
		buf.append("\n 	MEDIA as MEDIA_CD, ");
		buf.append("\n 	CHAN_CD, ");
		buf.append("\n 	'' AS CTGR_L_CD, ");
		buf.append("\n 	'' AS CTGR_M_CD, ");
		buf.append("\n 	'' AS CTGR_S_CD, ");
		buf.append("\n 	BRD_BGN_DD, ");
		buf.append("\n 	BRD_END_DD, ");
		buf.append("\n 	concat(concat(MEDIA, CHAN_CD), PGM_CD) as PGM_CD, ");
		buf.append("\n 	PRDT_DEPT_NM AS PRD_DEPT_NM, ");
		buf.append("\n 	'' AS SCHD_PGM_NM, ");
		buf.append("\n 	'' AS AWARD_HSTR, ");
		buf.append("\n 	PILOT_YN ");
		//		buf.append("\n 	MOD_END_YN ");
		buf.append("\n from DAS.E_PGMMST_TBL ");
		buf.append("\n where concat(concat(MEDIA, CHAN_CD), PGM_CD) = ? " );	
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectERPProgramInfoByCode######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, pgmCD);	

			rs = stmt.executeQuery();

			if(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmNm(rs.getString("PGM_NM"));
				item.setMediaCd(rs.getString("MEDIA_CD"));
				item.setChanCd(rs.getString("CHAN_CD"));
				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPgmCd(rs.getString("PGM_CD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));
				//				item.setMod_end_yn(rs.getString("MOD_END_YN"));

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
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
	 * 매체변환 오류내역 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List UserInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectErrorList(SystemManageConditionDO conditionDO, DASCommonDO commonDO, String excel) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectErrorListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		if (!"excel".equals(excel)) {
			buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		}
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
			//logger.debug("######selectErrorList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectErrorListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.EQUIPMENT_ROW_COUNT;

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage -1);

			int index = 0;
			if (!"excel".equals(excel)) {
				stmt.setInt(++index, startNum);
				stmt.setInt(++index, endNum);
			}
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{   
				ErrorRegisterDO item = new ErrorRegisterDO();
				item.setMasterId(					rs.getString("MASTER_ID"));
				item.setWrt(							rs.getString("WRT"));
				item.setCont(						rs.getString("ER_CONT"));
				item.setWorkCmCont(			rs.getString("REACT_CONT"));
				item.setRegDt(						rs.getString("REG_DT"));
				item.setRegrId(						rs.getString("REGRID"));
				item.setModDt(						rs.getString("MOD_DT"));
				item.setModrId(					rs.getString("MODRID"));
				item.setPgm_Nm(					rs.getString("PGM_NM"));
				item.setReq_cd(					rs.getString("REQ_CD"));
				item.setEpis_no(					rs.getString("EPIS_NO"));

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
	 * I사진 다운로드 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO selectPhotoDownList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectPhotoDownListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
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
			//logger.debug("######selectPhotoDownList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectPhotoDownListQuery(conditionDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while(rs.next())
			{
				ContentsUseInfoDO item = new ContentsUseInfoDO();
				item.setPhot_id(			rs.getString("PHOT_ID"));
				item.setReqUsrId(			rs.getString("REQ_ID"));
				item.setUserNm(			rs.getString("USER_NM"));
				item.setSubj(				rs.getString("SUBJ"));
				item.setReq_dt(			rs.getString("REQ_DT"));

				resultList.add(item);
			}
			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.CONTENTS_USE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

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
	 * 프로그램 정보를 입력한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{
		logger.debug(pgmDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.PGM_INFO_TBL (PGM_ID, PGM_NM, MEDIA_CD, CHAN_CD");
		buf.append("\n , CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, BRD_BGN_DD, BRD_END_DD, PGM_CD ");
		buf.append("\n , PRD_DEPT_NM, SCHD_PGM_NM, AWARD_HSTR, PILOT_YN, MODRID, MOD_DT)" );
		buf.append("\n values ");
		buf.append("\n ( ");
		buf.append("\n NEXTVAL FOR das.seq_pgm_id, ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, SUBSTR(HEX(CURRENT TIMESTAMP), 1, 14) ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertProgramInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, pgmDO.getPgmNm());
			stmt.setString(++index, pgmDO.getMediaCd());
			stmt.setString(++index, pgmDO.getChanCd());
			stmt.setString(++index, pgmDO.getCtgrLCd());
			stmt.setString(++index, pgmDO.getCtgrMCd());
			stmt.setString(++index, pgmDO.getCtgrSCd());
			stmt.setString(++index, pgmDO.getBrdBgnDd());
			stmt.setString(++index, pgmDO.getBrdEndDd());
			stmt.setString(++index, pgmDO.getPgmCd());
			stmt.setString(++index, pgmDO.getPrd_Dept_Nm());
			stmt.setString(++index, pgmDO.getSchd_Pgm_Nm());
			stmt.setString(++index, pgmDO.getAward_Hstr());
			stmt.setString(++index, pgmDO.getPilot_Yn());
			stmt.setString(++index, commonDO.getUserId());

			//			stmt.setString(++index, pgmDO.getMod_end_yn());			

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
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
	 * 프래임 수로 타임형식 구한다
	 * @param frame 프레임수
	 *
	 */
	public static String FrameToTimecodeStrDF(long frame) {
		long hh = frame / 107892; // (30000/1001)*3600
		frame = frame % 107892;
		long m10 = frame / 17982; // (30000/1001)*600
		frame = frame % 17982;
		long m1 = 0;
		long ss = 0;
		long ff = 0;
		long mm = 0;

		if ( frame < 1800 ) {
			m1 = 0;
			ss = frame / 30;
			ff = frame % 30;
		} else {      // except for each m10, plus 2 frame per m1
			frame = frame - 1800;  // in case of m10
			m1 = frame / 1798 + 1;
			frame = frame % 1798;

			if ( frame < 28 ) {   // in case of m1
				ss = 0;
				ff = frame + 2;
			} else {    // in case of none of m10 and m1
				frame = frame - 28;
				ss = frame / 30 + 1;
				ff = frame % 30;
			}
		}

		mm = m10 * 10 + m1;

		String str = "";

		str = str.format("%02d:%02d:%02d:%02d", hh, mm, ss, ff );
		return str;
	}




	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectNewPgmList(ProgramInfoDO condition) throws Exception
	{

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List resultList = new ArrayList();
		try 
		{
			// DAS PGM List Search
			con = DBService.getInstance().getConnection();

			String query = SystemManageStatement.selectNewPgmListQuery(condition);
			//logger.debug(query);
			stmt = con.prepareStatement(query);

			String Big = "";
			String small = "";
			int index = 0;
			if(condition.getSRCH_TYPE().equals("0")){
				
				Big = condition.getPgmNm().toUpperCase();
				small = condition.getPgmNm().toLowerCase();
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+small+"%");
			} else {
				if(condition.getSRCH_TYPE().equals("1")) {
					Big = condition.getPgmCd().toUpperCase();
				} else {
					Big = condition.getParents_cd().toUpperCase();
				}
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+Big+"%");
			}
			if(logger.isDebugEnabled()) {
				logger.debug("Big: "+Big);
			}
			rs = stmt.executeQuery();

			while(rs.next()) {
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmCd(rs.getString("PGM_CD"));
				String pgm_nm =  rs.getString("PGM_NM"); 
				if(StringUtils.isNotBlank(pgm_nm)) {
					pgm_nm = CommonUtl.transXmlText(pgm_nm);
				}
				/*
				pgm_nm = pgm_nm.replaceAll( "'" ,"&apos;");
				pgm_nm =pgm_nm.replaceAll("&quot;", "\"");
				pgm_nm =pgm_nm.replaceAll("<", "&lt;");
				pgm_nm =pgm_nm.replaceAll(">", "&gt;");
				pgm_nm =pgm_nm.replaceAll("&", "&amp;");
				 */
				item.setPgmNm(pgm_nm);

				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));
				item.setUse_yn(rs.getString("USE_YN"));
				item.setChanCd(rs.getString("CHAN_CD"));
				item.setMediaCd(rs.getString("MEDIA_CD"));
				item.setParents_cd(rs.getString("PARENTS_CD"));

				resultList.add(item);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			release(rs, stmt, con);
		}
		return resultList;
	}

	@Deprecated
	public List selectPgmList(ProgramInfoDO condition) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(SystemManageStatement.selectPgmListQuery(condition));

		logger.debug(buf.toString());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.

			int index = 0;

			if(condition.getSRCH_TYPE().equals("0")) {

				String Big = condition.getPgmNm().toUpperCase();
				String small = condition.getPgmNm().toLowerCase();
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+small+"%");
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+small+"%");
				stmt.setString(++index, "%"+Big+"%");
				stmt.setString(++index, "%"+small+"%");
			}

			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			int i=0;
			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmCd(rs.getString("PGM_CD"));
				String pgm_nm =  rs.getString("PGM_NM"); 
				pgm_nm = pgm_nm.replaceAll( "'" ,"&apos;");
				pgm_nm =pgm_nm.replaceAll("&quot;", "\"");
				pgm_nm =pgm_nm.replaceAll("<", "&lt;");
				pgm_nm =pgm_nm.replaceAll(">", "&gt;");

				pgm_nm =pgm_nm.replaceAll("&", "&amp;");
				item.setPgmNm(pgm_nm);
				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));
				item.setUse_yn(rs.getString("USE_YN"));
				item.setChanCd(rs.getString("CHAN_CD"));
				item.setMediaCd(rs.getString("MEDIA_CD"));
				item.setParents_cd(rs.getString("PARENTS_CD"));
				resultList.add(item);

				i++;

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


	public String selectPgmList2(ProgramInfoDO condition) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		StringBuffer strResult = new StringBuffer();                                  
		buf.append(SystemManageStatement.selectPgmListQuery(condition));



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPgmList2######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.

			int index = 0;


			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			int i=0;
			while(rs.next())
			{
				this.AddToResultXMLBuffer(strResult, "PGM_ID", "      ", rs.getLong("PGM_ID") + "");            	
				this.AddToResultXMLBuffer(strResult, "PGM_CD", "      ", rs.getString("PGM_CD") + "");            	
				String pgm_nm =  rs.getString("PGM_NM"); 
				pgm_nm = pgm_nm.replaceAll( "'" ,"&apos;");
				pgm_nm =pgm_nm.replaceAll("&quot;", "\"");
				pgm_nm =pgm_nm.replaceAll("<", "&lt;");
				pgm_nm =pgm_nm.replaceAll(">", "&gt;");

				pgm_nm =pgm_nm.replaceAll("&", "&amp;");
				this.AddToResultXMLBuffer(strResult, "PGM_NM", "      ", rs.getString("PGM_NM") + "");            	
				this.AddToResultXMLBuffer(strResult, "CTGR_L_CD", "      ", rs.getString("CTGR_L_CD") + "");            	
				this.AddToResultXMLBuffer(strResult, "CTGR_M_CD", "      ", rs.getString("CTGR_M_CD") + "");            	
				this.AddToResultXMLBuffer(strResult, "CTGR_S_CD", "      ", rs.getString("CTGR_S_CD") + "");            	
				this.AddToResultXMLBuffer(strResult, "BRD_BGN_DD", "      ", rs.getString("BRD_BGN_DD") + "");            	
				this.AddToResultXMLBuffer(strResult, "BRD_END_DD", "      ", rs.getString("BRD_END_DD") + "");            	
				this.AddToResultXMLBuffer(strResult, "PRD_DEPT_NM", "      ", rs.getString("PRD_DEPT_NM") + "");            	
				this.AddToResultXMLBuffer(strResult, "SCHD_PGM_NM", "      ", rs.getString("SCHD_PGM_NM") + "");            	
				this.AddToResultXMLBuffer(strResult, "AWARD_HSTR", "      ", rs.getString("AWARD_HSTR") + "");            	
				this.AddToResultXMLBuffer(strResult, "PILOT_YN", "      ", rs.getString("PILOT_YN") + "");            	
				this.AddToResultXMLBuffer(strResult, "USE_YN", "      ", rs.getString("USE_YN") + "");            	
				this.AddToResultXMLBuffer(strResult, "CHAN_CD", "      ", rs.getString("CHAN_CD") + "");            	
				this.AddToResultXMLBuffer(strResult, "MEDIA_CD", "      ", rs.getString("BRD_BGN_DD") + "");            	
				this.AddToResultXMLBuffer(strResult, "PARENTS_CD", "      ", rs.getString("PARENTS_CD") + "");    


			}



			return strResult.toString();
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
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_cd 프로그램코드
	 * @return List
	 * @throws Exception 
	 */
	public List selectPgmInfo(String pgm_cd) throws Exception
	{
		String query = SystemManageStatement.selectPgmInfoQuery(pgm_cd);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPgmInfo######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();
				item.setPgmId(rs.getLong("PGM_ID"));
				item.setPgmCd(rs.getString("PGM_CD"));
				item.setPgmNm(rs.getString("PGM_NM"));
				item.setCtgrLCd(rs.getString("CTGR_L_CD"));
				item.setCtgrMCd(rs.getString("CTGR_M_CD"));
				item.setCtgrSCd(rs.getString("CTGR_S_CD"));
				item.setBrdBgnDd(rs.getString("BRD_BGN_DD"));
				item.setBrdEndDd(rs.getString("BRD_END_DD"));
				item.setPrd_Dept_Nm(rs.getString("PRD_DEPT_NM"));				
				item.setSchd_Pgm_Nm(rs.getString("SCHD_PGM_NM"));
				item.setAward_Hstr(rs.getString("AWARD_HSTR"));
				item.setPilot_Yn(rs.getString("PILOT_YN"));

				resultList.add(item);

				return resultList;
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
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 제목
	 * @return List
	 * @throws Exception 
	 */
	public List selectPgm(String title) throws Exception
	{
		String query = SystemManageStatement.selectPgmQuery(title);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));

				item.setPgmNm(rs.getString("PGM_NM"));


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
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List selectPgm2(String title) throws Exception
	{
		String query = SystemManageStatement.selectPgmQuery2(title);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPgm2######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmCd(rs.getString("program_Code"));

				item.setPgmNm(rs.getString("program_name"));


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
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 제목
	 * @return List
	 * @throws Exception 
	 */

	public List selectParents(String title) throws Exception
	{
		String query = SystemManageStatement.selectPgmQuery(title);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			while(rs.next())
			{
				ProgramInfoDO item = new ProgramInfoDO();

				item.setPgmId(rs.getLong("PGM_ID"));

				item.setPgmNm(rs.getString("pgm_nm"));


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
	 * 프로그램 정보를 입력한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertPgmInfo(ProgramInfoDO pgmDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.PGM_INFO_TBL (PGM_ID, PGM_NM, MEDIA_CD, CHAN_CD");
		buf.append("\n , CTGR_L_CD, CTGR_M_CD, CTGR_S_CD, BRD_BGN_DD, BRD_END_DD, PGM_CD ");
		buf.append("\n , PRD_DEPT_NM, SCHD_PGM_NM, PILOT_YN, PARENTS_CD, USE_YN)" );
		buf.append("\n values ");
		buf.append("\n ( ");
		buf.append("\n NEXTVAL FOR das.seq_pgm_id, ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, pgmDO.getPgmNm());
			stmt.setString(++index, pgmDO.getMediaCd());
			stmt.setString(++index, pgmDO.getChanCd());
			stmt.setString(++index, pgmDO.getCtgrLCd());
			stmt.setString(++index, pgmDO.getCtgrMCd());
			stmt.setString(++index, pgmDO.getCtgrSCd());
			stmt.setString(++index, pgmDO.getBrdBgnDd());
			stmt.setString(++index, pgmDO.getBrdEndDd());
			stmt.setString(++index, pgmDO.getPgmCd());
			stmt.setString(++index, pgmDO.getPrd_Dept_Nm());
			stmt.setString(++index, pgmDO.getSchd_Pgm_Nm());

			stmt.setString(++index, pgmDO.getPilot_Yn());
			stmt.setString(++index, pgmDO.getParents_cd());
			stmt.setString(++index, pgmDO.getUse_yn());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 프로그램 정보를 수정한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updatePgmcd(ProgramInfoDO programInfoDO) throws Exception
	{
		logger.debug(programInfoDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.PGM_INFO_TBL set ");
		buf.append("\n 	PGM_NM = ?,  ");
		buf.append("\n 	MEDIA_CD = ?,  ");
		buf.append("\n 	CHAN_CD = ?,  ");
		buf.append("\n 	CTGR_L_CD = ?,  ");
		buf.append("\n 	CTGR_M_CD = ?,  ");
		buf.append("\n 	CTGR_S_CD = ?,  ");
		buf.append("\n 	BRD_BGN_DD = ?,  ");
		buf.append("\n 	BRD_END_DD = ?,  ");
		buf.append("\n 	PGM_CD = ?,  ");
		buf.append("\n 	PRD_DEPT_NM = ?,  ");
		buf.append("\n 	SCHD_PGM_NM = ?,  ");
		//buf.append("\n 	AWARD_HSTR = ?,  ");
		buf.append("\n 	PILOT_YN = ?,  ");
		buf.append("\n 	USE_YN = ?,  ");
		buf.append("\n 	PARENTS_CD = ?,  ");
		buf.append("\n 	MOD_DT = SUBSTR(HEX(CURRENT TIMESTAMP), 1, 14) ");
		buf.append("\n where PGM_ID = ?  ");
		buf.append("\n WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updatePgmcd######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, programInfoDO.getPgmNm());
			stmt.setString(++index, programInfoDO.getMediaCd());
			stmt.setString(++index, programInfoDO.getChanCd());
			stmt.setString(++index, programInfoDO.getCtgrLCd());
			stmt.setString(++index, programInfoDO.getCtgrMCd());
			stmt.setString(++index, programInfoDO.getCtgrSCd());
			stmt.setString(++index, programInfoDO.getBrdBgnDd());
			stmt.setString(++index, programInfoDO.getBrdEndDd());
			stmt.setString(++index, programInfoDO.getPgmCd());
			stmt.setString(++index, programInfoDO.getPrd_Dept_Nm());
			stmt.setString(++index, programInfoDO.getSchd_Pgm_Nm());
			//stmt.setString(++index, programInfoDO.getAward_Hstr());
			stmt.setString(++index, programInfoDO.getPilot_Yn());
			stmt.setString(++index, programInfoDO.getUse_yn());
			stmt.setString(++index, programInfoDO.getParents_cd());
			stmt.setLong(++index, programInfoDO.getPgmId());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			String pgm =String.valueOf(programInfoDO.getPgmId());
			systemManageDAO.insertPgmProceduer(Integer.parseInt(pgm));

			return updateCount;
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
	 * 계열사 수신서버 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectSubsiServerList(SubsiInfoDO condition) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectSubsiServerListQuery(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" WITH UR	 \n");

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
			//logger.debug("######selectSubsiServerList######## con : " + con);

			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, SystemManageStatement.selectSubsiServerListQuery(condition, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));



			stmt = con.prepareStatement(buf.toString());

			//디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				SubsiInfoDO item = new SubsiInfoDO();

				item.setSeq(rs.getInt("rownum"));
				item.setSubsi_nm(rs.getString("subsi_nm"));
				item.setReceve_server_nm(rs.getString("receve_server_nm"));
				item.setReceve_server_ip(rs.getString("receve_server_ip"));
				item.setReceve_server_port(rs.getInt("receve_server_port"));
				item.setAdmin_id(rs.getString("admin_id"));
				item.setPassword(rs.getString("password"));
				item.setPath(rs.getString("path"));
				item.setSubsi_id(rs.getString("subsi_id"));


				resultList.add(item);
			}

			int totalPageCount = totalCount / DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT  + (totalCount % DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);

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
	 * 계열사 수신서버 정보 목록을 입력한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertSubsiServer(SubsiInfoDO condition) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.SUBSI_INFO_TBL ( SUBSI_ID,   SUBSI_NM, RECEVE_SERVER_IP, RECEVE_SERVER_NM");
		buf.append("\n , RECEVE_SERVER_PORT, PASSWORD, PATH, ADMIN_ID  )");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, condition.getSubsi_id());
			stmt.setString(++index, condition.getSubsi_nm());
			stmt.setString(++index, condition.getReceve_server_ip());
			stmt.setString(++index, condition.getReceve_server_nm());
			stmt.setInt(++index, condition.getReceve_server_port());
			stmt.setString(++index, condition.getPassword());
			stmt.setString(++index, condition.getPath());
			stmt.setString(++index, condition.getAdmin_id());


			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 계열사 수신서버 정보 수정한다
	 * @param subsiInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateSubsiServer(SubsiInfoDO subsiInfoDO) throws Exception
	{
		logger.debug(subsiInfoDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.SUBSI_INFO_TBL set ");
		buf.append("\n 	SUBSI_ID = ?,  ");
		buf.append("\n 	SUBSI_NM = ?,  ");
		buf.append("\n 	RECEVE_SERVER_IP = ?,  ");
		buf.append("\n 	RECEVE_SERVER_NM = ?,  ");
		buf.append("\n 	RECEVE_SERVER_PORT  = ?,  ");
		buf.append("\n 	PASSWORD = ?,  ");
		buf.append("\n 	PATH = ?,  ");
		buf.append("\n 	ADMIN_ID = ? ");			

		buf.append("\n where SUBSI_ID like ?  ");
		buf.append("\n WITH UR	");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, subsiInfoDO.getSubsi_id());
			stmt.setString(++index, subsiInfoDO.getSubsi_nm());
			stmt.setString(++index, subsiInfoDO.getReceve_server_ip());
			stmt.setString(++index, subsiInfoDO.getReceve_server_nm());
			stmt.setInt(++index, subsiInfoDO.getReceve_server_port());
			stmt.setString(++index, subsiInfoDO.getPassword());
			stmt.setString(++index, subsiInfoDO.getPath());
			stmt.setString(++index, subsiInfoDO.getAdmin_id());
			stmt.setString(++index, subsiInfoDO.getSubsi_id());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 프로그램 복본관리를 조회한다.(다중조회)
	 * @param  condition                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List selectCopyList(CopyInfoDO condition) throws Exception
	{


		StringBuffer buf = new StringBuffer();

		buf.append(SystemManageStatement.selectCopyListQuery(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				CopyInfoDO item = new CopyInfoDO();

				item.setCms_pgm_id(rs.getString("CMS_PGM_ID"));
				item.setTitle(rs.getString("PROGRAM_NAME"));
				item.setReg_dt(rs.getString("reg_dt"));
				item.setRegrid(rs.getString("reg_id"));
				item.setCopy_yn(rs.getString("copy_yn"));

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
	 * 프로그램 복본관리를 등록한다.(pgm_id기준)
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCopy(CopyInfoDO condition) throws Exception
	{
		logger.debug(condition);
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.COPY_INFO_TBL ( PGM_ID,  REG_DT, REG_ID,    ");
		buf.append("\n COPY_YN, CMS_PGM_ID )");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertCopy######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setLong(++index, condition.getPgmId());
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));
			stmt.setString(++index, condition.getRegrid());
			stmt.setString(++index, "N");

			stmt.setString(++index, condition.getCms_pgm_id());



			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 프로그램 복본관리를 등록한다2.(pds cms pgm_id기준)
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCopy2(CopyInfoDO condition) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.COPY_INFO_TBL ( PGM_ID,  REG_DT, REG_ID,    ");
		buf.append("\n COPY_YN, CMS_PGM_ID )");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertCopy2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setLong(++index, 0);
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));
			stmt.setString(++index, condition.getRegrid());
			stmt.setString(++index, "Y");

			stmt.setString(++index, condition.getCms_pgm_id());



			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 프로그램 복본관리를 수정한다.(das pgm_id기준)
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateCopy(CopyInfoDO copyDO) throws Exception
	{
		logger.debug(copyDO);
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######updateCopy######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;
			int updateCount2 =0;
			if(!copyDO.getPgm_id_y().equals("")){
				buf.append("\n update DAS.COPY_INFO_TBL set ");
				buf.append("\n 	COPY_YN = ?  ");

				buf.append("\n where PGM_ID in ("+copyDO.getPgm_id_y()+")  ");
				buf.append("\n WITH UR	");
				stmt = con.prepareStatement(buf.toString());

				int index = 0;
				stmt.setString(++index, "Y");
				//				stmt.setString(++index, copyDO.getPgm_id_y());

				updateCount = stmt.executeUpdate();
			}


			if(!copyDO.getPgm_id_n().equals("")){
				buf2.append("\n update DAS.COPY_INFO_TBL set ");
				buf2.append("\n 	COPY_YN = ?  ");

				buf2.append("\n where PGM_ID in ("+copyDO.getPgm_id_n()+")  ");
				buf2.append("\n WITH UR	");
				//stmt2 = LoggableStatement.getInstance(con, buf2.toString());
				stmt2 = con.prepareStatement(buf2.toString());

				int index = 0;
				stmt2.setString(++index, "N");
				//				stmt2.setString(++index, copyDO.getPgm_id_n());
				updateCount2 = stmt2.executeUpdate();
			}


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount+updateCount2);
			}

			if(updateCount+updateCount2 == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount+updateCount2;
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
			release(null, stmt2, null);
		}
	}



	/**
	 * 프로그램 복본관리를 수정한다.(pds cms pgm_id)
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateCopy2(CopyInfoDO copyDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);
			int updateCount =0;
			int updateCount2 =0;
			if(!copyDO.getPgm_id_y().equals("")){
				buf.append("\n update DAS.COPY_INFO_TBL set ");
				buf.append("\n 	COPY_YN = ?  ");

				buf.append("\n where CMS_PGM_ID in (?)  ");
				buf.append("\n WITH UR	");
				stmt = con.prepareStatement(buf.toString());

				int index = 0;
				String[] Ypgm_id = copyDO.getPgm_id_y().split(",");

				for(int k=0; k<Ypgm_id.length;k++){
					index = 0;
					stmt.setString(++index, "Y");
					stmt.setString(++index, Ypgm_id[k]);

					updateCount = stmt.executeUpdate();
				}

			}


			if(!copyDO.getPgm_id_n().equals("")){
				buf2.append("\n update DAS.COPY_INFO_TBL set ");
				buf2.append("\n 	COPY_YN = ?  ");

				buf2.append("\n where CMS_PGM_ID in (?)  ");
				buf2.append("\n WITH UR	");
				//stmt2 = LoggableStatement.getInstance(con, buf2.toString());
				stmt2 = con.prepareStatement(buf2.toString());

				int index2 = 0;

				String[] Npgm_id = copyDO.getPgm_id_n().split(",");

				for(int j=0; j<Npgm_id.length;j++){
					index2 = 0;
					stmt2.setString(++index2, "N");
					stmt2.setString(++index2, Npgm_id[j]);

					updateCount = stmt2.executeUpdate();
				}

			}


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount+updateCount2);
			}

			if(updateCount+updateCount2 == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount+updateCount2;
		} 

		catch (Exception e) 
		{
			logger.error(buf.toString());
			logger.error(buf2.toString());

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
			release(null, stmt2, null);
		}
	}


	/**
	 * 프로그램 코드를 가져온다.
	 * @return  
	 */
	public String getPgm_cd()
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n SELECT MAX(PGM_CD) as PGM_CD ");
		buf.append("\n FROM DAS.PGM_INFO_TBL ");
		buf.append("\n WHERE SUBSTR(PGM_CD, 1, 2) = 'ZZ' ");
		buf.append("\n WITH UR	 ");

		String pgm_cd = "";
		int pgm_cd_value = 0;
		String pgm_cd_result = "ZZ";

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getPgm_cd######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			//stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			//StringBuffer tag = null;

			//List subList = null;

			if(rs.next())
			{
				pgm_cd = rs.getString("PGM_CD");
			}

			if (pgm_cd != null) {
				pgm_cd_value = Integer.parseInt(pgm_cd.substring(2));
			}

			pgm_cd_value = pgm_cd_value + 1;

			int length = String.valueOf(pgm_cd_value).length();

			for( int i=0; i<6-length; i++) {
				pgm_cd_result = pgm_cd_result + "0";
			}

			pgm_cd_result = pgm_cd_result + pgm_cd_value;

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[subMap size]" + pgm_cd);
			}
			return pgm_cd_result;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());

			return pgm_cd_result;
		} 

		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 스토리지 용량 확인
	 * 
	 * @throws DASException
	 */
	public String getAvailableDisk()throws DASException{
		String s; 
		try { 
			String [] runData = {"/bin/sh", "-c", "df -g | grep mp2"};
			Process ps = Runtime.getRuntime().exec(runData); 
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream())); 
			while((s = br.readLine()) != null) { 
				logger.debug(s); 
			}
			return s;
		} 
		catch( Exception ex ) { 
			logger.debug(ex.toString()); 
		} 
		return "";
	}






	/**
	 * 이용현황 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectNewUseInfoList(UseInfoDO condition, String flag) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectUseInfoQuery(condition, flag));
		if(DASBusinessConstants.PageQueryFlag.NORMAL.equals(flag)) {
			buf.append("\nWHERE t.ROWNUM BETWEEN ? AND ?                                          	");
		}
		buf.append("\nwith ur                                          	");

		//Page에 따른 계산을 한다.
		int page = condition.getPage();
		if(page == 0) {
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			logger.debug("query : " +buf.toString());

			stmt = con.prepareStatement(buf.toString());
			if(DASBusinessConstants.PageQueryFlag.NORMAL.equals(flag)) {
				int endNum = page * DASBusinessConstants.PageRowCount.BASIC_ROW_COUTN;
				int startNum = endNum - (DASBusinessConstants.PageRowCount.BASIC_ROW_COUTN -1);

				int index = 0;
				stmt.setInt(++index, startNum);
				stmt.setInt(++index, endNum);
			}
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next()) {
				UseInfoDO item = new UseInfoDO();

				item.setDesc(rs.getString("DESC"));
				item.setTitle(rs.getString("title"));

				item.setBrd_dd(rs.getString("brd_dd"));
				item.setFm_dd(rs.getString("fm_dt"));
				item.setMaster_id(rs.getString("master_id"));
				item.setReg_dt(rs.getString("reg_dt"));
				item.setBrd_leng(rs.getString("BRD_LENG"));
				item.setUse_cont(rs.getString("cnt"));
				item.setCopy_object_yn(rs.getString("copy_object_yn"));
				String epis = rs.getString("epis_no");
				if(epis.trim().equals("0")){
					item.setEpisno("");
				}else{
					item.setEpisno(rs.getString("epis_no"));
				}
				item.setBackup_yn(rs.getString("backup_yn"));	
				item.setChennel(rs.getString("chennel_cd"));	
				item.setCopy_status(rs.getString("old_copy_object_yn"));	

				resultList.add(item);
			}

			return resultList;
		} catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	@Deprecated
	public List selectUseInfoList(UseInfoDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(SystemManageStatement.selectUseInfoList(condition, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" with ur                                          	\n");
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
			//logger.debug("######selectUseInfoList######## con : " + con);
			logger.debug("query : " +buf.toString());
			//총 조회 갯수를 구한다.
			/*	int totalCount  = 
				getTotalCount(con,SystemManageStatement.selectUseInfoList(condition, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));
			 */


			stmt = con.prepareStatement(buf.toString());


			int endNum = page * DASBusinessConstants.PageRowCount.BASIC_ROW_COUTN;
			int startNum = endNum - (DASBusinessConstants.PageRowCount.BASIC_ROW_COUTN -1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				UseInfoDO item = new UseInfoDO();

				item.setDesc(rs.getString("DESC"));
				item.setTitle(rs.getString("title"));

				item.setBrd_dd(rs.getString("brd_dd"));
				item.setFm_dd(rs.getString("fm_dt"));
				item.setMaster_id(rs.getString("master_id"));
				item.setReg_dt(rs.getString("reg_dt"));
				item.setBrd_leng(rs.getString("BRD_LENG"));
				item.setUse_cont(rs.getString("cnt"));
				item.setCopy_object_yn(rs.getString("copy_object_yn"));
				String epis = rs.getString("epis_no");
				if(epis.trim().equals("0")){
					item.setEpisno("");
				}else{
					item.setEpisno(rs.getString("epis_no"));
				}
				item.setBackup_yn(rs.getString("backup_yn"));	
				item.setChennel(rs.getString("chennel_cd"));	
				item.setCopy_status(rs.getString("old_copy_object_yn"));	

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
	 * 이용현황 정보 목록을 조회(총조회건수, 총길이) 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public UseInfoDO selectUseInfoCount(UseInfoDO condition, String flag) throws Exception {
		UseInfoDO useInfoDO = null;
		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectUseInfoQuery(condition, flag));
		buf.append("\nwith ur");

		logger.debug("useInfo count : "+buf.toString());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		UseInfoDO item = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			if(rs.next()) {
				item = new UseInfoDO();
				item.setTotal(rs.getInt("CCOUNT"));
				item.setTotal_leng(rs.getLong("sum_brd_leng"));
			}
		} catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(rs, stmt, con);
		}

		return item;
	}

	@Deprecated
	public List selectUseInfoList2(UseInfoDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(SystemManageStatement.selectUseInfoList2(condition));


		buf.append(" with ur                                          	\n");
		logger.debug(buf.toString());
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
			//logger.debug("######selectUseInfoList2######## con : " + con);

			stmt = con.prepareStatement(buf.toString());



			int index = 0;


			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				UseInfoDO item = new UseInfoDO();

				item.setTotal(rs.getInt("CCOUNT"));
				logger.debug("total count: "+item.getTotal());
				item.setTotal_leng(rs.getLong("sum_brd_leng"));


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
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateCopyRequest(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateCopyRequest######## con : " + con);
			UseInfoDO pADO = externalDAO.selectCtiFromMasterForPDS(Long.parseLong(copyDO.getMaster_id()));
			String ct_ids[] = pADO.getCt_ids().split(",");
			String cti_ids[] = pADO.getCti_ids().split(",");
			for(int i = 0 ; i<ct_ids.length;i++){
				String pgm_cms_id = systemManageDAO.selectPdsPgmId(Long.parseLong(ct_ids[i]));
				pADO.setUser_id(copyDO.getUser_id());
				pADO.setDtl_type(copyDO.getDtl_type());
				pADO.setCt_id(Long.parseLong(ct_ids[i]));
				pADO.setCti_id(Long.parseLong(cti_ids[i]));
				externalDAO.ArchiveCopyReq(pADO,pgm_cms_id);
				copyDO.setCt_id(Long.parseLong(ct_ids[i]));
				copyDO.setCti_id(Long.parseLong(cti_ids[i]));
				copyDO.setGubun("002");
				insertHistoryInfo(copyDO);
				//return "1";
			}
			return "1";
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
	 * 아카이브 상태 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectArchiveStatusList(ArchiveInfoDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(SystemManageStatement.selectArchiveStatusList(condition));

		Connection con = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectArchiveStatusList######## con : " + con);
			logger.debug("query : " +buf.toString());
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{

				ArchiveInfoDO item = new ArchiveInfoDO();

				item.setTitle(rs.getString("title"));
				item.setMedia_id(rs.getString("media_id"));			
				item.setMaster_id(rs.getLong("master_id"));			

				item.setReq_dd(rs.getString("MOD_DT"));
				item.setReq_id(rs.getString("USER_NM"));
				item.setArchive_path(rs.getString("arch_route"));
				item.setArchive_yn(rs.getString("ARCHIVE_RE"));
				item.setHigh_qual(rs.getString("high_qual"));
				item.setWmv(rs.getString("wmv"));
				item.setDtl_yn(rs.getString("dtl_yn"));
				item.setCopy_cd(rs.getString("copy"));
				item.setOld_copy_cd(rs.getString("old_copy"));
				item.setEtc(rs.getString("ETC"));

				item.setBrd_dd(rs.getString("BRD_DD"));
				if(rs.getString("EPIS_NO").equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(rs.getString("EPIS_NO"));	
				}
				item.setReg_dt(rs.getString("REG_DT"));
				item.setBackup_yn(rs.getString("BACKUP_YN"));
				item.setChennel(rs.getString("CHENNEL_CD"));
				item.setCatalog_yn(rs.getString("CATALOG_YN"));
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
	 * 아카이브 상태 목록을 조회한다.여기서 xml생성도 같이 (2012.1.25)
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String selectArchiveStatusList2(ArchiveInfoDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		StringBuffer strResult = new StringBuffer();
		buf.append(SystemManageStatement.selectArchiveStatusList(condition));



		Connection con = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectArchiveStatusList2######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				index++;
				strResult.append("\n  <archiveinfo>");

				this.AddToResultXMLBuffer(strResult, "title", "      ", rs.getString("title") + "");
				this.AddToResultXMLBuffer(strResult, "media_id", "      ", rs.getString("media_id"));
				this.AddToResultXMLBuffer(strResult, "master_id", "      ", rs.getLong("master_id") + "");
				this.AddToResultXMLBuffer(strResult, "reg_dt", "      ", rs.getString("reg_dt") + "");
				this.AddToResultXMLBuffer(strResult, "USER_NM", "      ", rs.getString("USER_NM") + "");
				this.AddToResultXMLBuffer(strResult, "arch_route", "      ", rs.getString("arch_route") + "");
				this.AddToResultXMLBuffer(strResult, "ARCHIVE_RE", "      ", rs.getString("ARCHIVE_RE") + "");
				this.AddToResultXMLBuffer(strResult, "high_qual", "      ", rs.getString("high_qual") + "");
				this.AddToResultXMLBuffer(strResult, "wmv", "      ", rs.getString("wmv") + "");
				this.AddToResultXMLBuffer(strResult, "dtl_yn", "      ", rs.getString("dtl_yn") + "");
				this.AddToResultXMLBuffer(strResult, "copy", "      ", rs.getString("copy") + "");
				this.AddToResultXMLBuffer(strResult, "ETC", "      ", rs.getString("ETC") + "");

				strResult.append("\n </archiveinfo>");      
				logger.debug("xml finish   " +  index+ "  , master_id =  "+rs.getLong("master_id"));
			}
			return strResult.toString();
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
	 * 아카이브 상태 목록을 조회한다.
	 * 
	 * @return List
	 * @throws Exception 
	 */
	public List selectTodayList() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectNewTodayList());
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());
			int index = 0;
			String yesterday = "";

			String today = CalendarUtil.getDateTime("yyyyMMdd");

			// yesterday
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
			Date date = formatter.parse(today);	
			Calendar calendar = Calendar.getInstance();		     

			calendar.setTime(date);
			calendar.add(Calendar.DATE, -1);
			yesterday=formatter.format(calendar.getTime());

			stmt.setString(++index, yesterday);
			stmt.setString(++index, today);

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				TodayDO item = new TodayDO();

				item.setTitle(rs.getString("title"));
				item.setBrd_dd(rs.getString("brd_dd"));			

				item.setMaster_id(rs.getLong("master_id"));
				item.setArch_stat(rs.getString("DESC"));
				item.setArch_yn(rs.getString("ARCH_STE_YN"));
				item.setBrd_leng(rs.getString("brd_leng"));
				item.setReg_dt(rs.getString("reg_dt"));
				item.setCt_leng(rs.getString("CT_LENG"));
				item.setLimit_use(rs.getString("rist_nm"));
				String epis = rs.getString("EPIS_NO");
				if(epis.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(rs.getString("EPIS_NO"));
				}
				//	item.setLimit_use(rs.getString("ANNOT_CLF_CD"));
				item.setCtgr_l_Cd(rs.getString("CTGR_L_CD"));



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
	 * 명장면 조회한다.

	 * @return                                                                                                                                     MetaInfoDO XML string
	 * @throws Exception 
	 */
	public List selectGoodMediaList() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectGoodMediaList());



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######selectGoodMediaList######## query : " + buf.toString());
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				GoodMediaDO item = new GoodMediaDO();

				item.setMaster_id(rs.getLong("master_id"));
				item.setAnnot_clf_cont(rs.getString("annot_clf_cont"));			
				item.setCt_id(rs.getInt("ct_id"));			

				item.setSom(rs.getString("som"));
				item.setEom(rs.getString("eom"));
				item.setTitle(rs.getString("title"));
				item.setBrd_leng(rs.getString("brd_leng"));
				item.setBrd_dd(rs.getString("brd_dd"));
				String epis = rs.getString("epis_no");
				if(epis.trim().equals("0")){
					item.setEpis_no("");
				}else{
					item.setEpis_no(rs.getString("epis_no"));	
				}
				item.setCt_leng(rs.getString("ct_leng"));
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setAnnot_clf_nm(rs.getString("desc"));
				item.setArch_reg_dd(rs.getString("Arch_reg_dd"));
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
	 * 스토리지 용량 조회
	 * @param targetName 드라이브명
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getDiskAva(String targetName) throws Exception{

		String s="";
		try { 
			//	    	String [] runData = {"/bin/sh", "-c", "df -m | grep /app/db2"};
			String [] runData = {"/bin/sh", "-c", "df -m | grep "+targetName};

			Process ps = Runtime.getRuntime().exec(runData);

			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream())); 

			while((s = br.readLine()) != null) { 
				logger.debug("[getDiskAva][targetName]" + s);
				//용량 확인 구현  호출한 시점의 용량에서 95% 이상이라면 false 이하라면 true 넘기는 로직
				/*  	String[] gu = s.split("    ");
	        for(int i=0; i<gu.length;i++){
	        	logger.debug("[gu]" + gu[i]);
	        	if(i==1){
	        		String re = gu[i].replace("%", "").trim();
	        		logger.debug("[re]" + re);
	        		int lager = Integer.parseInt(re);
	        		logger.debug("[lager]" + lager);
	        	  if(lager>=95){
	        			logger.debug("[result = false");  
	        	  }else {
	        			logger.debug("[result = true");  
	        	  }
	        	}
	        }*/
				return s;
			}
		} 
		catch( Exception ex ) { 

			logger.error(targetName);
			throw ex;
		} 
		return "";
	}




	/**
	 * PDS 프로그램 정보를 등록한다.(벌크)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfoAll(List conditions) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		buf.append("\n INSERT INTO PDS_PGMINFO_TBL (    ");
		buf.append("\n  PROGRAMID    ");
		buf.append("\n ,PROGRAM_NAME   ");
		buf.append("\n ,PROGRAM_CODE  ");
		buf.append("\n ,PRODUCER_NAME");
		buf.append("\n ,PRODUCER_ID");
		buf.append("\n ,PRODUCER_PHONE");
		buf.append("\n ,CP_YN  )");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ? ,?, ?");
		buf.append("\n ) ");


		buf2.append("\n INSERT INTO PDS_PGMINFO_TBL (    ");
		buf2.append("\n  PROGRAMID    ");
		buf2.append("\n ,PROGRAM_NAME   ");
		buf2.append("\n ,PROGRAM_CODE  ");
		buf2.append("\n ,PRODUCER_NAME");
		buf2.append("\n ,PRODUCER_ID");
		buf2.append("\n ,PRODUCER_PHONE");
		buf2.append("\n ,CP_YN  )");
		buf2.append("\n values ");
		buf2.append("\n ( ");		
		buf2.append("\n ?, ?, ?, ?, ? ,?, ?");
		buf2.append("\n ) ");




		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsPgmInfoAll######## con : " + con);
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());
			stmt2 = con.prepareStatement(buf2.toString());

			int index = 0;
			if(conditions.size()>0)stmt = con.prepareStatement(buf.toString());


			for(int i=0;i<conditions.size();i++){
				index = 0;
				PgmInfoDO pgminfoDO = (PgmInfoDO)conditions.get(i);

				//	logger.debug("pgminfoDO = "+pgminfoDO);
				/*if(pgminfoDO.getPROGRAM_CODE().equals("")){
					int[] rtrn={0};
					return rtrn;
				}else{*/
				//deletePdsPgmInfo();
				String[] pid =pgminfoDO.getPid().split(",");
				String[] pname =pgminfoDO.getPRODUCER_NAME().split(",");
				String[] cid =pgminfoDO.getCid().split(",");
				String[] cname =pgminfoDO.getPRODUCTION_PORDUCER_NAME().split(",");

				//	System.out.println("pgminfoDO.getPid() = "+pgminfoDO.getPid());
				//	System.out.println("pgminfoDO.getPROGRAM_NAME() = "+pgminfoDO.getPROGRAM_NAME());
				//	System.out.println("pgminfoDO.getCid() = "+pgminfoDO.getCid());
				//	System.out.println("pgminfoDO.getPRODUCTION_PORDUCER_NAME() = "+pgminfoDO.getPRODUCTION_PORDUCER_NAME());
				if(pgminfoDO.getPid().equals("")&&pgminfoDO.getCid().equals("")){
					index = 0;	
					stmt.setInt(++index, 0);

					stmt.setString(++index, pgminfoDO.getPROGRAM_NAME());

					stmt.setString(++index, pgminfoDO.getPROGRAM_CODE());

					stmt.setString(++index,"");

					stmt.setString(++index, "");	

					stmt.setString(++index, "");

					stmt.setString(++index, "");
					//stmt.executeUpdate();
					if(!isThereApproveinfo("",pgminfoDO.getPROGRAM_CODE()))
					{
						PgmInfoDO pg = new PgmInfoDO();

						pg.setUser_id("");
						if(!pg.getDept_cd().equals("")){
							pg.setDept_cd(pg.getDept_cd());
						}else{
							pg.setDept_cd("");	
						}

						if(!pg.getDept_cd().equals("")){
							pg.setUser_no(pg.getUser_no());
						}else{
							pg.setUser_no("");	
						}

						if(!pg.getDept_cd().equals("")){
							pg.setPosition(pg.getPosition());
						}else{
							pg.setPosition("");	
						}
						pg.setPROGRAM_CODE(pgminfoDO.getPROGRAM_CODE());
						pg.setPROGRAM_NAME(pgminfoDO.getPROGRAM_NAME());

						if(!isTherePgminfo("",pgminfoDO.getPROGRAM_CODE())){
							insertApproveInfo(pg);
						}
					}
					stmt.executeUpdate();
				}else{
					if(!pgminfoDO.getPid().equals("")){
						for(int j =0; j<pid.length;j++){
							if(!pid[j].equals("")){

								index = 0;	
								stmt.setInt(++index, 0);	

								stmt.setString(++index, pgminfoDO.getPROGRAM_NAME());

								stmt.setString(++index, pgminfoDO.getPROGRAM_CODE());

								stmt.setString(++index, pname[j]);

								stmt.setString(++index, pid[j]);	

								stmt.setString(++index, "");	

								stmt.setString(++index, "N");
								//	System.out.println("getPROGRAM_NAME = "+pgminfoDO.getPROGRAM_NAME());
								//		System.out.println("getPROGRAM_CODE = "+pgminfoDO.getPROGRAM_CODE());
								//		System.out.println("getPRODUCER_NAME = "+pname[j]);
								//		System.out.println("getPid = "+pid[j]);	
								stmt.executeUpdate();
								//int asdf = stmt.executeUpdate();

							}


							if(!isThereApproveinfo(pid[j].substring(1),pgminfoDO.getPROGRAM_CODE()))
							{
								PgmInfoDO pg = selectUserInfo(pid[j]);

								pg.setUser_id(pid[j]);
								if(!pg.getDept_cd().equals("")){
									pg.setDept_cd(pg.getDept_cd());
								}else{
									pg.setDept_cd("");	
								}

								if(!pg.getDept_cd().equals("")){
									pg.setUser_no(pg.getUser_no());
								}else{
									pg.setUser_no("");	
								}

								if(!pg.getDept_cd().equals("")){
									pg.setPosition(pg.getPosition());
								}else{
									pg.setPosition("");	
								}
								pg.setPROGRAM_CODE(pgminfoDO.getPROGRAM_CODE());
								pg.setPROGRAM_NAME(pgminfoDO.getPROGRAM_NAME());

								if(!isTherePgminfo(pid[j],pgminfoDO.getPROGRAM_CODE())){
									insertApproveInfo(pg);
								}
							}

						}
					}

					if(!pgminfoDO.getCid().equals("")){
						for(int f =0; f<cid.length;f++){
							if(!cid[f].equals("")){
								index = 0;	
								stmt2.setInt(++index, 0);

								stmt2.setString(++index, pgminfoDO.getPROGRAM_NAME());


								stmt2.setString(++index, pgminfoDO.getPROGRAM_CODE());

								stmt2.setString(++index,cname[f]);

								stmt2.setString(++index, cid[f]);	

								stmt2.setString(++index, "");

								stmt2.setString(++index, "Y");

								stmt2.executeUpdate();
								//stmt2.executeUpdate();
							}


							if(!isThereApproveinfo(cid[f].substring(1),pgminfoDO.getPROGRAM_CODE()))
							{ 
								index = 0;	
								PgmInfoDO pg = selectUserInfo(cid[f]);
								pg.setUser_id(cid[f]);
								pg.setDept_cd(pg.getDept_cd());
								pg.setUser_no(pg.getUser_no());
								pg.setPosition(pg.getPosition());
								pg.setPROGRAM_CODE(pgminfoDO.getPROGRAM_CODE());
								pg.setPROGRAM_NAME(pgminfoDO.getPROGRAM_NAME());

								if(!isTherePgminfo(cid[f],pgminfoDO.getPROGRAM_CODE())){
									insertApproveInfo(pg);
								}
							}
						}

					}

				}
			}
			int[] rInt = null;
			int[] rInt2 = null;
			//stmt.executeBatch();
			//stmt2.executeBatch();
			//stmt3.executeBatch();
			//	System.out.println("stmt2 =");
			/*if(rInt[0] == 0)
				{
					//여기서 에러를 던진다.
					DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
					throw exception;
				}*/
			con.commit();
			insertPdsPgmInfoAll2();

			return 1;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			logger.error(buf2.toString());

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
			release(null, stmt2, null);
		}


	}


	/**
	 * PDS 프로그램 정보를 등록한다.
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfo(PgmInfoDO condition) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();


		buf.append("\n INSERT INTO PDS_PGMINFO_TBL (    ");
		buf.append("\n  PROGRAMID    ");
		buf.append("\n ,PROGRAM_NAME   ");
		buf.append("\n ,PROGRAM_CODE  ");
		buf.append("\n ,PRODUCER_NAME");
		buf.append("\n ,PRODUCER_ID");
		buf.append("\n ,PRODUCER_PHONE");
		buf.append("\n ,CP_YN  )");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ? ,?, ?");
		buf.append("\n ) ");


		buf2.append("\n INSERT INTO PDS_PGMINFO_TBL (    ");
		buf2.append("\n  PROGRAMID    ");
		buf2.append("\n ,PROGRAM_NAME   ");
		buf2.append("\n ,PROGRAM_CODE  ");
		buf2.append("\n ,PRODUCER_NAME");
		buf2.append("\n ,PRODUCER_ID");
		buf2.append("\n ,PRODUCER_PHONE");
		buf2.append("\n ,CP_YN  )");
		buf2.append("\n values ");
		buf2.append("\n ( ");		
		buf2.append("\n ?, ?, ?, ?, ? ,?, ?");
		buf2.append("\n ) ");



		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsPgmInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			stmt2 = con.prepareStatement(buf2.toString());
			int updateCount=0;
			int index = 0;			
			String PidList[]=condition.getPid().split(",");
			String CidList[]=condition.getCid().split(",");
			String PnmList[]=condition.getPRODUCER_NAME().split(",");
			String CnmList[]=condition.getPRODUCTION_PORDUCER_NAME().split(",");

			for(int i=0; i<PidList.length-1;i++){
				index = 0;	
				stmt.setInt(++index, condition.getPROGRAMID());
				stmt.setString(++index, condition.getPROGRAM_NAME());
				stmt.setString(++index, condition.getPROGRAM_CODE());
				if(i<PidList.length-1){
					stmt.setString(++index, PidList[i+1]);
					stmt.setString(++index, PnmList[i+1]);
				}else if(i==PidList.length-1){

				}
				stmt.setString(++index, condition.getPRODUCTION_PORDUCER_PHONE());
				stmt.setString(++index, "N");

				updateCount = stmt.executeUpdate();
			}
			for(int j=0; j<CidList.length-1;j++){
				index = 0;	
				stmt2.setInt(++index, condition.getPROGRAMID());
				stmt2.setString(++index, condition.getPROGRAM_NAME());
				stmt2.setString(++index, condition.getPROGRAM_CODE());
				if(j<CidList.length-1){
					stmt2.setString(++index, CidList[j+1]);
					stmt2.setString(++index, CnmList[j+1]);
				}else if(j==CidList.length-1){

				}
				stmt2.setString(++index, condition.getPRODUCTION_PORDUCER_PHONE());
				stmt2.setString(++index, "Y");

				updateCount = stmt2.executeUpdate();
			}

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;

		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());
			logger.error(buf2.toString());

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
			release(null, stmt2, null);
		}
	}

	/**
	 * PDS 프로그램별 사용자 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmUserInfo(PgmUserInfoDO pdsInfoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO PDS_PGM_USERINFO_TBL (    ");
		buf.append("\n RELATIONID    ");
		buf.append("\n ,TREENODEID    ");
		buf.append("\n ,NODENAME    ");
		buf.append("\n ,CAPTION    ");
		buf.append("\n ,USERID    ");
		buf.append("\n ,USERNAME    ");
		buf.append("\n ,REALNAME    ");
		buf.append("\n ,SUBTYPE    ");
		buf.append("\n ,PROGRAMID    ");
		buf.append("\n ,STORAGEID   ");
		buf.append("\n ,STORAGE_NAME    ");
		buf.append("\n ,PROGRAM_CD    ");
		buf.append("\n ,PROGRAM_NAME  )   ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsPgmInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String IdList[]=pdsInfoDO.getUserid().split(",");
			String UnmList[]=pdsInfoDO.getUsername().split(",");
			String AuList[]=pdsInfoDO.getAuthority().split(",");
			int updateCount = 0;
			int index=0;

			for(int i=0; i<IdList.length-1;i++){
				index = 0;	
				stmt.setLong(++index, pdsInfoDO.getRELATIONID());
				stmt.setInt(++index, pdsInfoDO.getTREENODEID());
				stmt.setString(++index, pdsInfoDO.getNODENAME());
				stmt.setString(++index, pdsInfoDO.getCAPTION());
				stmt.setInt(++index, pdsInfoDO.getUSERID());
				stmt.setString(++index, IdList[i+1]);
				stmt.setString(++index, UnmList[i+1]);
				stmt.setString(++index,AuList[i+1]);
				stmt.setInt(++index, pdsInfoDO.getPROGRAMID());
				stmt.setInt(++index, pdsInfoDO.getSTORAGEID());
				stmt.setString(++index, pdsInfoDO.getSTORAGENM());
				stmt.setString(++index, pdsInfoDO.getProgramcd());
				stmt.setString(++index, pdsInfoDO.getProgramnm());			

				updateCount = stmt.executeUpdate();

			}

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * PDS 프로그램별 사용자 정보를 등록한다.(벌크)
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int[] insertPdsPgmUserInfoAll(List pdsInfoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO PDS_PGM_USERINFO_TBL (    ");
		buf.append("\n RELATIONID    ");
		buf.append("\n ,TREENODEID    ");
		buf.append("\n ,NODENAME    ");
		buf.append("\n ,CAPTION    ");
		buf.append("\n ,USERID    ");
		buf.append("\n ,USERNAME    ");
		buf.append("\n ,REALNAME    ");
		buf.append("\n ,SUBTYPE    ");
		buf.append("\n ,PROGRAMID    ");
		buf.append("\n ,STORAGEID   ");
		buf.append("\n ,STORAGE_NAME    ");
		buf.append("\n ,PROGRAM_CD    ");
		buf.append("\n ,PROGRAM_NAME  )   ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsPgmUserInfoAll######## con : " + con);
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			if(pdsInfoDO.size()>0)stmt = con.prepareStatement(buf.toString());

			for(int i=0;i<pdsInfoDO.size();i++){
				index = 0;
				PgmUserInfoDO pgminfoDO = (PgmUserInfoDO)pdsInfoDO.get(i);	
				if(pgminfoDO.getProgramcd().equals("")){
					int[] rtrn={0};
					return rtrn;
				}else{
					deletePdsUserPgmInfo();
					String IdList[]=pgminfoDO.getUserid().split(",");
					String UnmList[]=pgminfoDO.getUsername().split(",");
					String AuList[]=pgminfoDO.getAuthority().split(",");
					for(int k=0; k<IdList.length-1;k++){
						index = 0;	

						stmt.setLong(++index, pgminfoDO.getRELATIONID());
						stmt.setInt(++index, pgminfoDO.getTREENODEID());
						stmt.setString(++index, pgminfoDO.getNODENAME());
						stmt.setString(++index, pgminfoDO.getCAPTION());
						stmt.setInt(++index, pgminfoDO.getUSERID());
						stmt.setString(++index, IdList[k+1]);
						stmt.setString(++index, UnmList[k+1]);
						stmt.setString(++index,AuList[k+1]);
						stmt.setInt(++index, pgminfoDO.getPROGRAMID());
						stmt.setInt(++index, pgminfoDO.getSTORAGEID());
						stmt.setString(++index, pgminfoDO.getSTORAGENM());
						stmt.setString(++index, pgminfoDO.getProgramcd());
						stmt.setString(++index, pgminfoDO.getProgramnm());			

						stmt.addBatch();
					}


				}
				int[] rInt = null;
				int[] rInt2 = null;
				if(pdsInfoDO.size()>0)rInt =stmt.executeBatch();


				if (logger.isDebugEnabled()) 
				{
					logger.debug("[Inserted Count]" + rInt);
				}

				if(rInt[0] == 0)
				{
					//여기서 에러를 던진다.
					DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
					throw exception;
				}


				con.commit();
				return rInt;

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
	 * PDS-DAS간 연동 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsMappInfo(PdsMappDO condition) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO PDS_PGM_MAPP_TBL (    ");
		buf.append("\n  PDS_PGM_ID    ");
		buf.append("\n ,PDS_PGM_NM  ");
		buf.append("\n ,DAS_PGM_ID  ");
		buf.append("\n ,DAS_PGM_NM )  ");


		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsMappInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setLong(++index, condition.getPds_pgm_id());
			stmt.setString(++index, condition.getPds_pgm_nm());
			stmt.setLong(++index, condition.getDas_pgm_id());
			stmt.setString(++index, condition.getDas_pgm_nm());




			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * pds 프로그램 정보를 삭제한다.
	 * @param  수정할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deletePdsPgmInfo() throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.PDS_PGMINFO_TBL ");


		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deletePdsPgmInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;



			int iTmp = stmt.executeUpdate();



			con.commit();
			return iTmp;
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
	 * pds USER 프로그램 정보를 삭제한다.
	 * @param  수정할 게시판 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deletePdsUserPgmInfo() throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n delete from DAS.PDS_PGM_USERINFO_TBL ");


		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deletePdsUserPgmInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;



			int iTmp = stmt.executeUpdate();



			con.commit();
			return iTmp;
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
	 * PDAS 아카이브 요청건에 대해서 메타 입력
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertMetadatTbl(PdsArchiveDO pgmDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into METADAT_MST_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,PGM_CD    ");
		buf.append("\n ,EPIS_NO    ");
		buf.append("\n ,TITLE    ");
		buf.append("\n ,CTGR_L_CD    ");
		buf.append("\n ,CTGR_M_CD    ");
		buf.append("\n ,CTGR_S_CD    ");
		buf.append("\n ,BRD_DD    ");
		buf.append("\n ,FINAL_BRD_YN   ");
		buf.append("\n ,SNPS    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,BRD_BGN_HMS     ");
		buf.append("\n ,BRD_END_HMS    ");
		buf.append("\n ,BRD_LENG    ");
		buf.append("\n ,PGM_RATE    ");
		buf.append("\n ,DRT_NM    ");
		buf.append("\n ,PRODUCER_NM    ");
		buf.append("\n ,WRITER_NM    ");
		buf.append("\n ,PRDT_IN_OUTS_CD    ");
		buf.append("\n ,PRDT_DEPT_CD    ");
		buf.append("\n ,PRDT_DEPT_NM    ");
		buf.append("\n ,ORG_PRDR_NM   ");
		buf.append("\n ,MC_NM    ");
		buf.append("\n ,CAST_NM    ");
		buf.append("\n ,CMR_DRT_NM   ");
		buf.append("\n ,FM_DT    ");
		buf.append("\n ,CMR_PLACE    ");
		buf.append("\n ,SPC_INFO    ");
		buf.append("\n ,REQ_CD    ");
		buf.append("\n ,SEC_ARCH_NM    ");
		buf.append("\n ,SEC_ARCH_ID    ");
		buf.append("\n ,GATH_CO_CD    ");
		buf.append("\n ,GATH_CLF_CD    ");
		buf.append("\n ,ARCH_REG_DD    ");
		buf.append("\n ,ARRG_END_DT   ");
		buf.append("\n ,WORK_PRIO_CD    ");
		buf.append("\n ,RSV_PRD_CD    ");
		buf.append("\n ,CPRTR_NM    ");		
		buf.append("\n ,CPRT_TYPE    ");
		buf.append("\n ,CPRT_TYPE_DSC    ");
		buf.append("\n ,VIEW_GR_CD   ");
		buf.append("\n ,DLBR_CD    ");
		buf.append("\n ,AWARD_HSTR    ");
		buf.append("\n ,RPIMG_KFRM_SEQ     ");
		buf.append("\n ,TAPE_ID    ");  
		buf.append("\n ,TAPE_ITEM_ID    ");
		buf.append("\n ,TAPE_MEDIA_CLF_CD    ");
		buf.append("\n ,RSV_PRD_END_DD    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,MOD_DT   ");
		buf.append("\n ,GATH_DEPT_CD    ");
		buf.append("\n ,MCUID    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,DATA_STAT_CD    ");
		buf.append("\n ,ING_REG_DD    ");
		buf.append("\n ,COPY_KEEP    ");
		buf.append("\n ,CLEAN_KEEP    ");
		buf.append("\n ,MUSIC_INFO    ");
		buf.append("\n ,RST_CONT    ");
		buf.append("\n ,RERUN    ");
		buf.append("\n ,ACCEPTOR_ID    ");
		buf.append("\n ,SUB_TTL    ");
		buf.append("\n ,ARRANGE_NM   ");
		buf.append("\n ,LOCK_STAT_CD    ");
		buf.append("\n ,ERROR_STAT_CD    ");
		buf.append("\n ,ARCH_ROUTE    ");
		buf.append("\n ,RIST_CLF_CD     ");
		buf.append("\n ,MANUAL_YN    ");
		buf.append("\n ,PDS_CMS_PGM_ID    ");

		buf.append("\n ,COCD    ");
		buf.append("\n ,CHENNEL_CD  ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pgmDO.getConn() != null) {
				con = pgmDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			index = 0;	
			long masterid= selectMasterid();
			stmt.setLong(++index,masterid);//MASTER_ID
			stmt.setInt(++index, 0);//PGM_ID
			stmt.setString(++index,"");//PGM_CD
			stmt.setInt(++index, pgmDO.getEpis_no());//EPIS_NO
			if(pgmDO.getEpis_no()!=0){
				stmt.setString(++index,  pgmDO.getPgm_nm()+" "+pgmDO.getTitle()+" "+pgmDO.getEpis_no()+"회");//TITLE
			}else {
				stmt.setString(++index,  pgmDO.getPgm_nm()+" "+pgmDO.getTitle());//TITLE	
			}
			stmt.setString(++index, "100");//CTGR_L_CD
			stmt.setString(++index, "");//CTGR_M_CD
			stmt.setString(++index,"");//CTGR_S_CD
			if(StringUtils.isNotEmpty(pgmDO.getBrd_dd())){
				stmt.setString(++index, pgmDO.getBrd_dd());//BRD_DD
			}else{
				stmt.setString(++index, "19000101");//BRD_DD
			}
			stmt.setString(++index, "");//FINAL_BRD_YN
			stmt.setString(++index, "");//SNPS
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, pgmDO.getBrd_bgn_hms());//BRD_BGN_HMS
			stmt.setString(++index, pgmDO.getBrd_end_hms());//BRD_END_HMS
			if(StringUtils.isNotEmpty(pgmDO.getBrd_dd())){
				stmt.setString(++index,pgmDO.getBrd_leng());//BRD_LENG
			}else{
				stmt.setString(++index,"00:00:00:00");//BRD_LENG	
			}
			//stmt.setString(++index,"00:00:11");//BRD_LENG
			stmt.setString(++index, "");//PGM_RATE
			stmt.setString(++index, pgmDO.getProducer_nm());//DRT_NM
			stmt.setString(++index, pgmDO.getProducer_nm());//PRODUCER_NM
			stmt.setString(++index, "");//WRITER_NM
			stmt.setString(++index,"");//PRDT_IN_OUTS_CD
			stmt.setString(++index, "");//PRDT_DEPT_CD
			stmt.setString(++index, "");//prdt_dept_nm
			stmt.setString(++index, "");//ORG_PRDR_NM
			stmt.setString(++index, "");//MC_NM
			stmt.setString(++index, "");//CAST_NM
			stmt.setString(++index,"");////CMR_DRT_NM
			if(StringUtils.isNotEmpty(pgmDO.getFm_dt())){
				stmt.setString(++index, pgmDO.getFm_dt());//FM_DT
			}else{
				stmt.setString(++index, "19000101");//FM_DT
			}
			stmt.setString(++index, pgmDO.getCmr_place());//CMR_PLACE
			stmt.setString(++index, "");//SPC_INFO
			stmt.setString(++index, "");//REQ_CD
			stmt.setString(++index,"");//SEC_ARCH_NM
			stmt.setString(++index, "");//SEC_ARCH_ID
			stmt.setString(++index, "");//GATH_CO_CD
			stmt.setString(++index,"");//GATH_CLF_CD
			stmt.setString(++index, "");//ARCH_REG_DD
			stmt.setString(++index, "");//ARRG_END_DT
			stmt.setString(++index, "");//WORK_PRIO_CD
			stmt.setString(++index, "");//RSV_PRD_CD
			if(!pgmDO.getCprt_nm().equals("")){
				stmt.setString(++index, pgmDO.getCprt_nm());//CPRTR_NM
			}else{
				stmt.setString(++index, "SBS");//CPRTR_NM	
			}

			if(!pgmDO.getCprt_nm().equals("")){
				stmt.setString(++index, pgmDO.getCprt_cd());//CPRT_TYPE
			}else{
				stmt.setString(++index, "001");//CPRT_TYPE
			}

			stmt.setString(++index, "");//CPRT_TYPE_DSC
			stmt.setString(++index, pgmDO.getView_gr_cd());//VIEW_GR_CD
			stmt.setString(++index, "");//DLBR_CD
			stmt.setString(++index, "");//AWARD_HSTR
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, "");//TAPE_ID
			stmt.setString(++index, "");//TAPE_ITEM_ID
			stmt.setString(++index,"");//TAPE_MEDIA_CLF_CD
			stmt.setString(++index, "");//RSV_PRD_END_DD
			stmt.setString(++index, "");//del_dd
			stmt.setString(++index, "Y");//USE_YN
			stmt.setString(++index, pgmDO.getReq_id());//REGRID
			stmt.setString(++index, dateTime);//날짜
			stmt.setString(++index, pgmDO.getReq_id());//MODRID
			stmt.setString(++index, "");//날짜
			stmt.setString(++index, "");//GATH_DEPT_CD
			stmt.setString(++index, "");//MCUID
			stmt.setInt(++index, 0);//RPIMG_CT_ID
			stmt.setString(++index, "");//DATA_STAT_CD
			stmt.setString(++index, dateTime);//ING_REG_DD
			stmt.setString(++index, "");//COPY_KEEP
			stmt.setString(++index, "");//CLEAN_KEEP
			stmt.setString(++index, "");//MUSIC_INFO
			stmt.setString(++index, "");//RST_CONT
			stmt.setString(++index, "");//RERUN
			stmt.setString(++index, "");//ACCEPTOR_ID
			stmt.setString(++index, pgmDO.getTitle());//SUB_TTL
			stmt.setString(++index, "");//ARRANGE_NM
			stmt.setString(++index, "N");//LOCK_STAT_CD
			stmt.setString(++index, "000");//ERROR_STAT_CD
			stmt.setString(++index, "P");//ARCH_ROUTE
			stmt.setString(++index, pgmDO.getRist_clf_cd());//RIST_CLF_CD

			boolean result;
			if(pgmDO.getCocd().equals("")){
				result = systemManageDAO.getAutoArchvieList(pgmDO.getCt_cla(),"S","A","P");
			}else{
				result = systemManageDAO.getAutoArchvieList(pgmDO.getCt_cla(),pgmDO.getCocd(),pgmDO.getChennel(),"P");	
			}

			if(result){
				stmt.setString(++index, "N");//MANUAL_YN
			}else{
				stmt.setString(++index, "Y");//MANUAL_YN	
			}
			stmt.setString(++index, pgmDO.getPds_cms_id());//pds_cms_id

			if(pgmDO.getCocd().equals("")){
				stmt.setString(++index, "S");//COCD
				stmt.setString(++index, "A");//CHENNEL_CD
			}else {
				stmt.setString(++index, pgmDO.getCocd());//COCD
				stmt.setString(++index, pgmDO.getChennel());//CHENNEL_CD
			}

			pgmDO.setMaster_id(masterid);

			updateCount = stmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted insertMetadatTbl] " + masterid);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pgmDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pgmDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pgmDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}
			if(pgmDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}




	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다(mxf)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForHigh(PdsArchiveDO pdsInfoDO) throws Exception
	{
		logger.debug(pdsInfoDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID   ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "105");//CTI_FMT
			stmt.setString(++index,"N");//ARCH_STE_YN
			stmt.setString(++index, "002");//ME_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getBit_rt()));//BIT_RT
			stmt.setString(++index, pdsInfoDO.getFrm_per_sec());//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			stmt.setInt(++index, pdsInfoDO.getVd_hresol());//VD_HRESOL
			stmt.setInt(++index, pdsInfoDO.getVd_vresol());	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getRecord_type_cd());//RECORD_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getAudio_yn());//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAud_samp_frq()));//AUD_SAMP_FRQ
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAudio_bdwt()));//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, pdsInfoDO.getStorage_path());//FL_PATH
			stmt.setString(++index,cti_id+".MXF");//WRK_FILE_NM
			stmt.setString(++index, pdsInfoDO.getClip_nm());////ORG_FILE_NM
			stmt.setLong(++index, pdsInfoDO.getFl_sz());//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,pdsInfoDO.getReq_id());//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "N");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			pdsInfoDO.setCti_id(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted insertConInstInfoForHigh] " + cti_id);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}



	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForLow(PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getBit_rt()));//BIT_RT
			stmt.setString(++index, pdsInfoDO.getFrm_per_sec());//FRM_PER_SEC
			stmt.setString(++index, pdsInfoDO.getDrop_yn());//DRP_FRM_YN
			stmt.setInt(++index, pdsInfoDO.getVd_hresol());//VD_HRESOL
			stmt.setInt(++index, pdsInfoDO.getVd_vresol());	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getRecord_type_cd());//RECORD_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getAudio_yn());//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAud_samp_frq()));//AUD_SAMP_FRQ
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAudio_bdwt()));//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,cti_id+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,pdsInfoDO.getReq_id());//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN
			pdsInfoDO.setCti_idForlow(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted insertConInstInfoForLow] " + cti_id);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}








	/**
	 * contents_tbl에 자료를 집어 넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsInfo (PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO CONTENTS_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,CT_TYP    ");
		buf.append("\n ,CT_CLA    ");
		buf.append("\n ,CT_NM    ");
		buf.append("\n ,CONT    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,CT_OWN_DEPT_CD    ");
		buf.append("\n ,CT_OWN_DEPT_NM    ");
		buf.append("\n ,DATA_STAT_CD   ");
		buf.append("\n ,CT_LENG    ");
		buf.append("\n ,VD_QLTY    ");
		buf.append("\n ,ASP_RTO_CD    ");
		buf.append("\n ,EDTRID    ");
		buf.append("\n ,KFRM_PATH    ");
		buf.append("\n ,KFRM_PX_CD    ");
		buf.append("\n ,TOT_KFRM_NUMS    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,MCU_SEQ    ");
		buf.append("\n ,CMS_CT_ID    ");
		buf.append("\n ,COPY_OBJECT_YN    ");
		buf.append("\n ,USE_CONT    ");
		buf.append("\n ,ARCHIVE_YN    ");
		buf.append("\n ,MEDIA_ID    ");
		buf.append("\n ,DEL_YN    )    ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long ctid= selectCtid();
			stmt.setLong(++index, ctid);//CT_ID
			stmt.setInt(++index, 1);//CT_SEQ
			//stmt.setString(++index, pdsInfoDO.getCt_typ());//CT_TYP
			stmt.setString(++index,"003");//CT_TYP
			stmt.setString(++index, pdsInfoDO.getCt_cla());//CT_CLA
			stmt.setString(++index, "");			//CT_NM
			stmt.setString(++index, "");//CONT
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, "");//CT_OWN_DEPT_CD
			stmt.setString(++index, "");//CT_OWN_DEPT_NM
			stmt.setString(++index, pdsInfoDO.getData_stat());	//		DATA_STAT_CD
			stmt.setString(++index, pdsInfoDO.getCt_leng());//CT_LENG
			stmt.setString(++index, pdsInfoDO.getVd_qulty());//VD_QLTY
			stmt.setString(++index, pdsInfoDO.getAsp_rto_cd());//ASP_RTO_CD
			stmt.setString(++index, "");//EDTRID
			stmt.setString(++index,"");		//	KFRM_PATH
			stmt.setString(++index, "");//KFRM_PX_CD
			stmt.setInt(++index,0);//TOT_KFRM_NUMS
			stmt.setString(++index,"Y");//USE_YN
			stmt.setString(++index, "");//DEL_DD
			stmt.setString(++index, dateTime);	//REG_DT
			stmt.setString(++index, pdsInfoDO.getReq_id());//REGRID
			stmt.setString(++index, "D080009");//MODRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setInt(++index, 0);	//DURATION
			stmt.setInt(++index, 0);//MCU_SEQ
			stmt.setInt(++index, 0);	//CMS_CT_ID
			stmt.setString(++index, "N");//COPY_OBJECT_YN
			stmt.setInt(++index,0);//USE_CONT
			stmt.setString(++index, "Y");//ARCHIVE_YN
			stmt.setString(++index, CommonUtl.checkNull(pdsInfoDO.getMedia_id()));	//MEDIA_ID
			stmt.setString(++index, "N");	//DEL_YN
			pdsInfoDO.setCt_id(ctid);

			updateCount = stmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted insertContentsInfo] " + ctid);
			}

			if(updateCount == 0){
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}


	/**
	 * conner tbl에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCornerInfo(PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CORNER_TBL (    ");
		buf.append("\n CN_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_NM    ");
		buf.append("\n ,CN_TYPE_CD    ");
		buf.append("\n ,SOM    ");
		buf.append("\n ,EOM    ");
		buf.append("\n ,CN_INFO    ");
		buf.append("\n ,RPIMG_KFRM_SEQ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,S_FRAME   ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long cnid= selectCnid();
			stmt.setLong(++index,cnid);//CN_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());//MASTER_ID
			stmt.setString(++index, "");//CN_NM
			stmt.setString(++index, "003");//CN_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getSom());//SOM
			stmt.setString(++index, pdsInfoDO.getEom());//EOM
			stmt.setString(++index, pdsInfoDO.getArchivecoments());//CN_INFO
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, pdsInfoDO.getReq_id());//REGRID
			stmt.setString(++index,"");//MOD_DT
			stmt.setString(++index,  "");//MODRID
			stmt.setLong(++index, 0);	//DURATION
			stmt.setLong(++index, 0);//RPIMG_CT_ID
			stmt.setLong(++index,0);//S_FRAME

			pdsInfoDO.setCn_id(cnid);
			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted insertCornerInfo] " + cnid);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}





	/**
	 * CONTENTS_MAPP_TBL 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsMappInfo(PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_MAPP_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,S_DURATION    ");
		buf.append("\n ,E_DURATION    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,CN_SEQ    ");
		buf.append("\n ,DEL_DD )   ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	

			stmt.setLong(++index,pdsInfoDO.getCt_id());
			stmt.setLong(++index, 0);
			stmt.setLong(++index, pdsInfoDO.getMaster_id());
			stmt.setLong(++index, pdsInfoDO.getCn_id());
			stmt.setInt(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, "");			
			stmt.setString(++index,dateTime);	
			stmt.setInt(++index, 1);
			stmt.setString(++index,"");	

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}





	/**
	 * 마스터ID를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectMasterid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select NEXTVAL FOR SEQ_MASTER_ID from sysibm.sysdummy1 ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMasterid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newMasterId = rs.getLong(1);


			return newMasterId;
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
	 * 코너ID를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectCnid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_CN_ID from sysibm.sysdummy1 ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCnid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newCN_Id = rs.getLong(1);



			return newCN_Id;
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
	 * 컨텐츠 id를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectCtid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_CT_ID from sysibm.sysdummy1");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCtid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newCN_Id = rs.getLong(1);

			return newCN_Id;
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
	 * 컨텐츠 인스턴스 id를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectCtiid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_CTI_ID from sysibm.sysdummy1 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######selectCtiid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newCN_Id = rs.getLong(1);



			return newCN_Id;
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
	 * 프리뷰id를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectPreviewid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_PREVIEW_ID from sysibm.sysdummy1 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPreviewid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newCN_Id = rs.getLong(1);



			return newCN_Id;
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
	 * annot id를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectAnootid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_ANNOT_ID from sysibm.sysdummy1 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAnootid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newannot_Id = rs.getLong(1);



			return newannot_Id;
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
	 * 프리뷰 ATTACH_id를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectPreview_attach_id() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_PREVIEW_ATTACTH_ID from sysibm.sysdummy1 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPreview_attach_id######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newCN_Id = rs.getLong(1);



			return newCN_Id;
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
	 * 검수완료처리한다
	 * @param     master_id                                                                                                                                                                                    
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateAccept(long master_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateAccept######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update DAS.metadat_mst_tbl set ");
			buf.append("\n 	data_stat_cd = '007'  ");
			buf.append("\n 	,accept_end_dd = ?  ");
			buf.append("\n where master_id = ?  ");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);		
			stmt.setLong(++index, master_id);
			updateCount = stmt.executeUpdate();


			String result ="007";


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return result;
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
	 * 정리완료처리한다
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateArrange(long master_id,String user_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateArrange######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update DAS.metadat_mst_tbl set ");
			buf.append("\n 	data_stat_cd = '003'  ");
			buf.append("\n 	,sec_arch_id = ?  ");
			buf.append("\n 	,sec_arch_nm = ?  ");
			buf.append("\n 	,ARRG_END_DT = ? ");
			buf.append("\n where master_id = ?  ");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String user_nm = userRoleDAO.getUser_nm(user_id);
			int index = 0;
			stmt.setString(++index, user_id);
			stmt.setString(++index, user_nm);
			stmt.setString(++index, dateTime);
			stmt.setLong(++index, master_id);


			updateCount = stmt.executeUpdate();

			String result = "003," +dateTime ;




			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return result;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, null);
			release(null, stmt2, con);
		}
	}




	/**
	 * PDAS 아카이브  상태변환
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int updatePDSArchiveStatus(PdsArchiveDO pgmDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update  METADAT_MST_TBL set DATA_STAT_CD = ?  where master_id = ?");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;

			stmt.setString(1, "001");//DATA_STAT_CD
			stmt.setLong(2, pgmDO.getMaster_id());// media_id

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) { }
			}
			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {}
			release(null, stmt, con);
		}

	}

	/**
	 * preview_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPreveiw_Info(PdsArchiveDO pdsInfoDO) throws Exception
	{
		logger.debug(pdsInfoDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PREVIEW_TBL (   ");
		buf.append("\n   MASTER_ID   ");
		buf.append("\n  ,PREVIEW_ID   ");
		buf.append("\n  ,PREVIEW_SUBJ  ");
		buf.append("\n 	,PREVIEW_CONT    ");
		buf.append("\n  ,REG_DT    ");
		buf.append("\n ) VALUES (   ");
		buf.append("\n ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPreveiw_Info######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			long preview_id = selectPreviewid();
			stmt.setLong(++index, pdsInfoDO.getMaster_id());
			stmt.setLong(++index, preview_id);
			stmt.setString(++index, "");			
			stmt.setString(++index, pdsInfoDO.getPreview_cont());	
			stmt.setString(++index, pdsInfoDO.getReq_dt());	
			pdsInfoDO.setPreview_id(preview_id);


			updateCount = stmt.executeUpdate();

			if(!pdsInfoDO.getPreview_file_nm().equals("")){
				insertPreveiw_attach_Info(pdsInfoDO);
			}

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) { }
			}
			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) { }
			release(null, stmt, con);
		}

	}

	/**
	 * preview_tbl 에 집어넣는다
	 * @param pdsInfoDO  입력할 정보를 가지고있는beans
	 * @param previewdo  입력할 정보를 가지고있는beans                                                                                                                                                                            
	 * @return      updatecount                                                                                                                                                                                        
	 * @throws Exception 
	 */
	public int insertPreveiw_Info(PdsArchiveDO pdsInfoDO,PreviewDO previewdo) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PREVIEW_TBL (   ");
		buf.append("\n   MASTER_ID   ");
		buf.append("\n  ,PREVIEW_ID   ");
		buf.append("\n  ,PREVIEW_SUBJ  ");
		buf.append("\n 	,PREVIEW_CONT    ");
		buf.append("\n  ,REG_DT    ");
		buf.append("\n ) VALUES (   ");
		buf.append("\n ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPreveiw_Info######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String[] cont =  previewdo.getPreivew_cont().split(",");
			String[] subj = previewdo.getPreview_subj().split(",");

			for(int i=1; i<cont.length;i++){
				index = 0;
				long preview_id = selectPreviewid();
				stmt.setLong(++index, pdsInfoDO.getMaster_id());
				stmt.setLong(++index, preview_id);
				stmt.setString(++index, subj[i]);			
				stmt.setString(++index, cont[i]);	
				stmt.setString(++index, pdsInfoDO.getReq_dt());	
				pdsInfoDO.setPreview_id(preview_id);


				updateCount = stmt.executeUpdate();
			}
			insertPreveiw_attach_Info(pdsInfoDO);




			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
			//	return 0;
		}

	}

	/**
	 * preview_attach_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPreveiw_attach_Info(PdsArchiveDO pdsInfoDO) throws Exception
	{
		logger.debug(pdsInfoDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PREVIEW_ATTACH_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PREVIEW_ATTATCH_ID    ");
		buf.append("\n ,FL_NM    ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,ORG_FILE_NM    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");


		buf.append("\n ) VALUES ( ");

		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPreveiw_attach_Info######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String[] file_nm = pdsInfoDO.getPreview_file_nm().split(",");
			for(int i =0; i<file_nm.length-1;i++){

				index = 0;	

				long Preveiw_attach_Info =selectPreview_attach_id();
				stmt.setLong(++index,pdsInfoDO.getMaster_id());		
				stmt.setLong(++index, Preveiw_attach_Info);				
				stmt.setString(++index, file_nm[i+1]);
				stmt.setLong(++index, 0);
				stmt.setString(++index, pdsInfoDO.getPreview_path());			
				stmt.setString(++index, file_nm[i+1]);	
				stmt.setString(++index, pdsInfoDO.getReq_dt());
				stmt.setString(++index,pdsInfoDO.getReq_id());



				updateCount = stmt.executeUpdate();

			}




			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * PdasArchive 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdasArchive(PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PDS_ARCHIVE_TBL (   ");
		buf.append("\n   SOM   ");
		buf.append("\n  ,EOM   ");
		buf.append("\n  ,VD_HRESOL  ");
		buf.append("\n 	,VD_VRESOL    ");
		buf.append("\n  ,FL_SZ    ");
		buf.append("\n  ,BIT_RT   ");
		buf.append("\n  ,AUD_SAMP_FRQ   ");
		buf.append("\n  ,AUD_BDWT  ");
		buf.append("\n 	,EPIS_NO    ");
		buf.append("\n  ,PRODUCER_NM    ");
		buf.append("\n  , CMR_PLACE   ");
		buf.append("\n  ,FM_DT   ");
		buf.append("\n  ,CPRT_NM  ");
		//buf.append("\n 	,CPTR_TYPE    ");
		//buf.append("\n  ,RIST_CLF_CD    ");
		buf.append("\n  ,REQ_NM   ");
		buf.append("\n  ,REQ_ID   ");
		buf.append("\n  ,REQ_DT  ");
		buf.append("\n 	,MEDIA_ID    ");
		buf.append("\n  ,RECORD_TYPE_CD    ");
		buf.append("\n  ,FRM_PER_SEC   ");
		buf.append("\n  ,ORG_FILE_NM   ");
		buf.append("\n  ,STORAGE_PATH  ");
		buf.append("\n 	,DROP_YN    ");
		buf.append("\n  ,CT_TYP    ");
		buf.append("\n  ,CT_LENG   ");
		buf.append("\n  ,ASP_RTO_CD   ");
		buf.append("\n  ,VD_QLTY  ");
		buf.append("\n 	,DATA_STAT    ");
		buf.append("\n  ,CT_CLA    ");
		buf.append("\n  , CLIP_NM   ");
		buf.append("\n  ,BRD_LENG   ");
		buf.append("\n  ,BRD_END_HMS  ");
		buf.append("\n 	,BRD_BGN_HMS    ");
		buf.append("\n  ,BRD_DD    ");
		buf.append("\n  ,TITLE    ");
		buf.append("\n  , PGM_NM   ");
		buf.append("\n  ,MASTER_ID   ");
		buf.append("\n  ,CN_ID  ");
		buf.append("\n 	,CTI_ID    ");
		buf.append("\n  ,CT_ID    ");
		buf.append("\n  , STATUS   ");
		buf.append("\n  ,PREVIEW_INFO   ");
		buf.append("\n  ,PREVIEW_FILE_NM  ");
		buf.append("\n 	,REG_DT    ");


		buf.append("\n ) VALUES (   ");

		buf.append("\n  ?, ?, ?  ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?,?,?,?,?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	

			stmt.setString(++index, pdsInfoDO.getSom());//SOM
			stmt.setString(++index, pdsInfoDO.getEom());			//EOM
			stmt.setInt(++index, pdsInfoDO.getVd_hresol());	//VD_HRESOL
			stmt.setInt(++index, pdsInfoDO.getVd_vresol());	//VD_VRESOL
			stmt.setLong(++index, pdsInfoDO.getFl_sz());	//FL_SZ
			stmt.setString(++index, pdsInfoDO.getBit_rt());//BIT_RT
			stmt.setFloat(++index, pdsInfoDO.getAud_samp_frq());			//AUD_SAMP_FRQ
			stmt.setLong(++index, pdsInfoDO.getAudio_bdwt());	//AUD_BDWT
			stmt.setLong(++index, pdsInfoDO.getEpis_no());	//EPIS_NO
			stmt.setString(++index, pdsInfoDO.getProducer_nm());	//PRODUCER_NM
			stmt.setString(++index, pdsInfoDO.getCmr_place());//CMR_PLACE
			stmt.setString(++index, pdsInfoDO.getFm_dt());			//FM_DT
			stmt.setString(++index, pdsInfoDO.getCprt_nm());	//CPRT_NM
			//stmt.setString(++index, pdsInfoDO.getCprt_cd());	//CPTR_TYPE
			//stmt.setString(++index, pdsInfoDO.getRist_clf_cd());	//RIST_CLF_CD
			stmt.setString(++index, pdsInfoDO.getReq_nm());//REQ_NM
			stmt.setString(++index, pdsInfoDO.getReq_id());			//REQ_ID
			stmt.setString(++index, pdsInfoDO.getReq_dt());	//REQ_DT
			stmt.setString(++index, pdsInfoDO.getMedia_id());	//MEDIA_ID
			stmt.setString(++index, pdsInfoDO.getRecord_type_cd());	//RECORD_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getFrm_per_sec());//FRM_PER_SEC
			stmt.setString(++index, pdsInfoDO.getOrg_file_nm());			//ORG_FILE_NM
			stmt.setString(++index, pdsInfoDO.getStorage_path());	//STORAGE_PATH
			stmt.setString(++index, pdsInfoDO.getDrop_yn());	//DROP_YN
			stmt.setString(++index, pdsInfoDO.getCt_typ());	//CT_TYP
			stmt.setString(++index, pdsInfoDO.getCt_leng());//CT_LENG
			stmt.setString(++index, pdsInfoDO.getAsp_rto_cd());			//ASP_RTO_CD
			stmt.setString(++index, pdsInfoDO.getVd_qulty());	//VD_QLTY
			stmt.setString(++index, pdsInfoDO.getData_stat());	//DATA_STAT
			stmt.setString(++index, pdsInfoDO.getCon_cla());	//CT_CLA
			stmt.setString(++index, pdsInfoDO.getClip_nm());//CLIP_NM
			stmt.setString(++index, pdsInfoDO.getBrd_leng());			//BRD_LENG
			stmt.setString(++index, pdsInfoDO.getBrd_end_hms());	//BRD_END_HMS
			stmt.setString(++index, pdsInfoDO.getBrd_bgn_hms());	//BRD_BGN_HMS
			stmt.setString(++index, pdsInfoDO.getBrd_dd());	//BRD_DD
			stmt.setString(++index, pdsInfoDO.getTitle());//TITLE
			stmt.setString(++index, pdsInfoDO.getPgm_nm());			//PGM_NM
			stmt.setLong(++index, pdsInfoDO.getMaster_id());	//MASTER_ID
			stmt.setLong(++index, pdsInfoDO.getCn_id());	//CN_ID
			stmt.setLong(++index, pdsInfoDO.getCti_id());	//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, pdsInfoDO.getStatus());			//STATUS
			stmt.setString(++index, "");	//PREVIEW_INFO
			stmt.setString(++index, pdsInfoDO.getPreview_file_nm());	//PREVIEW_FILE_NM
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);	//REG_DT

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}






	/**
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertPDS(PdsArchiveDO pdsArchiveDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ,COCD ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPDS######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(pdsArchiveDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			String storage =  pdsArchiveDO.getStorage_path().replace('\\', d);
			stmt.setString(++index, storage);//INPUT_HR
			if(pdsArchiveDO.getCocd().equals("")){
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}else{
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}

			stmt.setString(++index, pdsArchiveDO.getCti_idForlow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,pdsArchiveDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, pdsArchiveDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index, pdsArchiveDO.getClip_nm());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			stmt.setString(++index,"002");//TC_TYPE
			if(pdsArchiveDO.getCocd().equals("")){
				stmt.setString(++index,"S");//COCD
			}else{
				stmt.setString(++index,"M");//COCD	
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
	 * seq 를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectSeq() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  NEXTVAL FOR SEQ_SEQ_ID from sysibm.sysdummy1 ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSeq######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newSeq_Id = rs.getLong(1);



			return newSeq_Id;
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
	 * 기본정보 보존기간을 수정한다
	 * @param     rsv_prd_cd                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRsv_Prd_DD(String rsv_prd_cd,long master_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();


		Connection con = null;
		PreparedStatement stmt = null;

		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRsv_Prd_DD######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update DAS.metadat_mst_tbl set ");
			buf.append("\n 	rsv_prd_cd = ?  ");
			buf.append("\n 	,rsv_prd_end_dd = ? ");
			buf.append("\n where master_id = ?  ");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());
			// String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			int index = 0;

			String getRsv_prd_end_dd = "";

			// String dateTime = CalendarUtil.getDateTime("yyyyMMdd");
			String dateTime = selectRsv_end_dd(master_id);

			try{

				SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

				Date date = formatter.parse(dateTime);	

				Calendar calendar = Calendar.getInstance();		     
				if(rsv_prd_cd.equals("000")){//영구
					getRsv_prd_end_dd="99991231";

				} else if(rsv_prd_cd.equals("003")){//3일
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, +3);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());

				}else if(rsv_prd_cd.equals("005")){//5일
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, +5);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
				}else if(rsv_prd_cd.equals("030")){//한달
					calendar.setTime(date);
					calendar.add(Calendar.DAY_OF_MONTH, +30);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());    	  
				}else if(rsv_prd_cd.equals("060")){//5년
					calendar.setTime(date);
					calendar.add(Calendar.YEAR, +5);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());

				}else if(rsv_prd_cd.equals("120")){//10년
					calendar.setTime(date);
					calendar.add(Calendar.YEAR, +10);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());			    	  
				}else if(rsv_prd_cd.equals("240")){//20년
					calendar.setTime(date);
					calendar.add(Calendar.YEAR, +20);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
				}else if(rsv_prd_cd.equals("360")){//30년
					calendar.setTime(date);
					calendar.add(Calendar.YEAR, +30);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
				}else if(rsv_prd_cd.equals("001")){//1년
					calendar.setTime(date);
					calendar.add(Calendar.YEAR, +1);
					getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
				}
			} catch(Exception e) {
				e.printStackTrace();
			}



			stmt.setString(++index, rsv_prd_cd);
			stmt.setString(++index, getRsv_prd_end_dd);
			stmt.setLong(++index, master_id);


			// updateCount = stmt.executeUpdate();

			String result = getRsv_prd_end_dd ;




			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}




			con.commit();
			return result;
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
	 * PDS 프로그램 정보를 등록(기존에 존재하면 냅두고 신규면 삽입)한다.(벌크)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfoAll2() throws Exception
	{

		StringBuffer buf = new StringBuffer();


		buf.append("\n  merge into das.pds_pgminfo_tbl2 TG   ");
		buf.append("\n   using (   ");
		buf.append("\n select programid, program_name, program_code, producer_name, producer_phone, producer_id, cp_yn   ");
		buf.append("\n from das.pds_pgminfo_tbl   ");
		buf.append("\n group by programid, program_name, program_code, producer_name, producer_phone, producer_id, cp_yn ");
		buf.append("\n ) ABC ");
		buf.append("\n on ( ");
		buf.append("\n TG.programid = ABC.programid ");
		buf.append("\n  AND TG.program_name = ABC.program_name ");
		buf.append("\n  AND TG.program_code = ABC.program_code ");		
		buf.append("\n AND TG.producer_name = ABC.producer_name ");
		buf.append("\n  AND TG.producer_phone = ABC.producer_phone ");
		buf.append("\n AND TG.producer_id = ABC.producer_id  ");		
		buf.append("\n  AND TG.cp_yn = ABC.cp_yn ");
		buf.append("\n ) ");
		buf.append("\n  when not matched then ");		
		buf.append("\n insert (programid, program_name, program_code, producer_name, producer_phone, producer_id,cp_yn)  ");
		buf.append("\n values (ABC.programid, ABC.program_name, ABC.program_code, ABC.producer_name, ABC.producer_phone, ABC.producer_id,ABC.cp_yn) ");


		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPdsPgmInfoAll2######## con : " + con);
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			int result = stmt.executeUpdate();


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + result);
			}



			con.commit();
			return result;

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
	 * 자동아카이브여부를 목록조회 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public boolean getAutoArchvieList(String ct_cla) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		buf.append(CodeInfoStatement.selecAutoArchvieList(ct_cla));



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getAutoArchvieList######## con : " + con);


			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setString(++index, ct_cla);


			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();
			String result="";
			while(rs.next())
			{

				return true;

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
			release(rs, stmt, con);
		}
	}

	/**
	 * 자동아카이브여부를 목록조회 한다(계열사별)
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public boolean getAutoArchvieList(String ct_cla,String cocd,String chennel,String arch_route) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		if(logger.isDebugEnabled()) {
			logger.debug("#########ct_cla :"+ct_cla);
			logger.debug("#########cocd :"+cocd);
			logger.debug("#########chennel :"+chennel);
			logger.debug("#########arch_route :"+arch_route);
		}
		buf.append(CodeInfoStatement.selecAutoArchvieList(ct_cla,cocd,chennel,arch_route));



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getAutoArchvieList######## con : " + con);


			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setString(++index, ct_cla);
			stmt.setString(++index, cocd);
			stmt.setString(++index, chennel);
			stmt.setString(++index, arch_route);
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();
			String result="";
			while(rs.next())
			{

				return true;

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
			release(rs, stmt, con);
		}
	}


	/**
	 * 복본 프로그램 등록 중복여부 조회한다.
	 * @param cms_pgm_id
	 * @return
	 * @throws Exception 
	 */
	public boolean isTherePgmid(String cms_pgm_id) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.copy_info_Tbl where cms_pgm_id = '"+cms_pgm_id+"' \n");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isTherePgmid######## con : " + con);
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
			release(null, null, con);
		}
	}

	/**
	 *  프로그램 등록 중복여부 조회한다.
	 * @param cms_pgm_id
	 * @return
	 * @throws Exception 
	 */
	public boolean isTherePgmCd(String PGM_CD) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.PGM_INFO_TBL where PGM_CD = '"+PGM_CD.trim()+"' \n");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isTherePgmCd######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = getTotalCount(con, buf.toString());

			if(totalCount > 0)
			{
				return false;
			}
			else
			{
				return true;
			}
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
	 * Nle 아카이브
	 * @param NleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertMetadatTbl(NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into METADAT_MST_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,PGM_CD    ");
		buf.append("\n ,EPIS_NO    ");
		buf.append("\n ,TITLE    ");
		buf.append("\n ,CTGR_L_CD    ");
		buf.append("\n ,CTGR_M_CD    ");
		buf.append("\n ,CTGR_S_CD    ");
		buf.append("\n ,BRD_DD    ");
		buf.append("\n ,FINAL_BRD_YN   ");
		buf.append("\n ,SNPS    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,BRD_BGN_HMS     ");
		buf.append("\n ,BRD_END_HMS    ");
		buf.append("\n ,BRD_LENG    ");
		buf.append("\n ,PGM_RATE    ");
		buf.append("\n ,DRT_NM    ");
		buf.append("\n ,PRODUCER_NM    ");
		buf.append("\n ,WRITER_NM    ");
		buf.append("\n ,PRDT_IN_OUTS_CD    ");
		buf.append("\n ,PRDT_DEPT_CD    ");
		buf.append("\n ,PRDT_DEPT_NM    ");
		buf.append("\n ,ORG_PRDR_NM   ");
		buf.append("\n ,MC_NM    ");
		buf.append("\n ,CAST_NM    ");
		buf.append("\n ,CMR_DRT_NM   ");
		buf.append("\n ,FM_DT    ");
		buf.append("\n ,CMR_PLACE    ");
		buf.append("\n ,SPC_INFO    ");
		buf.append("\n ,REQ_CD    ");
		buf.append("\n ,SEC_ARCH_NM    ");
		buf.append("\n ,SEC_ARCH_ID    ");
		buf.append("\n ,GATH_CO_CD    ");
		buf.append("\n ,GATH_CLF_CD    ");
		buf.append("\n ,ARCH_REG_DD    ");
		buf.append("\n ,ARRG_END_DT   ");
		buf.append("\n ,WORK_PRIO_CD    ");
		buf.append("\n ,RSV_PRD_CD    ");
		buf.append("\n ,CPRTR_NM    ");		
		//	buf.append("\n ,CPRT_TYPE    ");
		buf.append("\n ,CPRT_TYPE_DSC    ");
		buf.append("\n ,VIEW_GR_CD   ");
		buf.append("\n ,DLBR_CD    ");
		buf.append("\n ,AWARD_HSTR    ");
		buf.append("\n ,RPIMG_KFRM_SEQ     ");
		buf.append("\n ,TAPE_ID    ");  
		buf.append("\n ,TAPE_ITEM_ID    ");
		buf.append("\n ,TAPE_MEDIA_CLF_CD    ");
		buf.append("\n ,RSV_PRD_END_DD    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,MOD_DT   ");
		buf.append("\n ,GATH_DEPT_CD    ");
		buf.append("\n ,MCUID    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,DATA_STAT_CD    ");
		buf.append("\n ,ING_REG_DD    ");
		buf.append("\n ,COPY_KEEP    ");
		buf.append("\n ,CLEAN_KEEP    ");
		buf.append("\n ,MUSIC_INFO    ");
		buf.append("\n ,RST_CONT    ");
		buf.append("\n ,RERUN    ");
		buf.append("\n ,ACCEPTOR_ID    ");
		buf.append("\n ,SUB_TTL    ");
		buf.append("\n ,ARRANGE_NM   ");
		buf.append("\n ,LOCK_STAT_CD    ");
		buf.append("\n ,ERROR_STAT_CD    ");
		buf.append("\n ,ARCH_ROUTE    ");
		buf.append("\n ,RIST_CLF_CD     ");
		buf.append("\n ,PDS_CMS_PGM_ID   ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMetadatTbl######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			index = 0;	
			long masterid= selectMasterid();
			stmt.setLong(++index,masterid);//MASTER_ID
			stmt.setInt(++index, 0);//PGM_ID
			stmt.setString(++index,"");//PGM_CD
			stmt.setInt(++index, 0);//EPIS_NO
			stmt.setString(++index, nleDO.getTitle());//TITLE
			stmt.setString(++index, "200");//CTGR_L_CD
			stmt.setString(++index, "");//CTGR_M_CD
			stmt.setString(++index,"");//CTGR_S_CD
			stmt.setString(++index, nleDO.getBrd_dd());//BRD_DD
			stmt.setString(++index, "");//FINAL_BRD_YN
			stmt.setString(++index, "");//SNPS
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, "");//BRD_BGN_HMS
			stmt.setString(++index, "");//BRD_END_HMS
			stmt.setString(++index,nleDO.getBrd_leng());//BRD_LENG
			stmt.setString(++index, "");//PGM_RATE
			stmt.setString(++index, "");//DRT_NM
			stmt.setString(++index, "");//PRODUCER_NM
			stmt.setString(++index, "");//WRITER_NM
			stmt.setString(++index,"");//PRDT_IN_OUTS_CD
			stmt.setString(++index, "");//PRDT_DEPT_CD
			stmt.setString(++index, "");//prdt_dept_nm
			stmt.setString(++index, "");//ORG_PRDR_NM
			stmt.setString(++index, "");//MC_NM
			stmt.setString(++index, "");//CAST_NM
			stmt.setString(++index,"");////CMR_DRT_NM
			stmt.setString(++index, "");//FM_DT
			stmt.setString(++index, "");//CMR_PLACE
			stmt.setString(++index, "");//SPC_INFO
			stmt.setString(++index, "");//REQ_CD
			stmt.setString(++index,"");//SEC_ARCH_NM
			stmt.setString(++index, "");//SEC_ARCH_ID
			stmt.setString(++index, "");//GATH_CO_CD
			stmt.setString(++index,"");//GATH_CLF_CD
			stmt.setString(++index, "");//ARCH_REG_DD
			stmt.setString(++index, "");//ARRG_END_DT
			stmt.setString(++index, "");//WORK_PRIO_CD
			stmt.setString(++index, "");//RSV_PRD_CD
			stmt.setString(++index, "");//CPRTR_NM
			//	stmt.setString(++index, pgmDO.getCprt_cd());//CPRT_TYPE
			stmt.setString(++index, "");//CPRT_TYPE_DSC
			stmt.setString(++index,"");//VIEW_GR_CD
			stmt.setString(++index, "");//DLBR_CD
			stmt.setString(++index, "");//AWARD_HSTR
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, "");//TAPE_ID
			stmt.setString(++index, "");//TAPE_ITEM_ID
			stmt.setString(++index,"");//TAPE_MEDIA_CLF_CD
			stmt.setString(++index, "");//RSV_PRD_END_DD
			stmt.setString(++index, "");//del_dd
			stmt.setString(++index, "Y");//USE_YN
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index, dateTime);//날짜
			stmt.setString(++index, "D080009");//MODRID
			stmt.setString(++index, dateTime);//날짜
			stmt.setString(++index, "");//GATH_DEPT_CD
			stmt.setString(++index, "");//MCUID
			stmt.setInt(++index, 0);//RPIMG_CT_ID
			stmt.setString(++index, "001");//DATA_STAT_CD
			stmt.setString(++index, dateTime);//ING_REG_DD
			stmt.setString(++index, "");//COPY_KEEP
			stmt.setString(++index, "");//CLEAN_KEEP
			stmt.setString(++index, "");//MUSIC_INFO
			stmt.setString(++index, "");//RST_CONT
			stmt.setString(++index, "");//RERUN
			stmt.setString(++index, "");//ACCEPTOR_ID
			stmt.setString(++index, nleDO.getSub_ttl());//SUB_TTL
			stmt.setString(++index, "");//ARRANGE_NM
			stmt.setString(++index, "N");//LOCK_STAT_CD
			stmt.setString(++index, "000");//ERROR_STAT_CD
			stmt.setString(++index, "N");//ARCH_ROUTE
			stmt.setString(++index, "");//RIST_CLF_CD
			stmt.setString(++index, "D");//ARCH_ROUTE
			nleDO.setMaster_id(masterid);



			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * contents_tbl에 자료를 집어 넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsInfo (NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO CONTENTS_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,CT_TYP    ");
		buf.append("\n ,CT_CLA    ");
		buf.append("\n ,CT_NM    ");
		buf.append("\n ,CONT    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,CT_OWN_DEPT_CD    ");
		buf.append("\n ,CT_OWN_DEPT_NM    ");
		buf.append("\n ,DATA_STAT_CD   ");
		buf.append("\n ,CT_LENG    ");
		buf.append("\n ,VD_QLTY    ");
		buf.append("\n ,ASP_RTO_CD    ");
		buf.append("\n ,EDTRID    ");
		buf.append("\n ,KFRM_PATH    ");
		buf.append("\n ,KFRM_PX_CD    ");
		buf.append("\n ,TOT_KFRM_NUMS    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,MCU_SEQ    ");
		buf.append("\n ,CMS_CT_ID    ");
		buf.append("\n ,COPY_OBJECT_YN    ");
		buf.append("\n ,USE_CONT    ");
		buf.append("\n ,ARCHIVE_YN    ");
		buf.append("\n ,MEDIA_ID    ");
		buf.append("\n ,DEL_YN    )    ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertContentsInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long ctid= selectCtid();
			stmt.setLong(++index, ctid);//CT_ID
			stmt.setInt(++index, 1);//CT_SEQ
			//stmt.setString(++index, pdsInfoDO.getCt_typ());//CT_TYP
			stmt.setString(++index,"003");//CT_TYP
			stmt.setString(++index, nleDO.getCon_cla());//CT_CLA
			stmt.setString(++index, "");			//CT_NM
			stmt.setString(++index, "");//CONT
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, "");//CT_OWN_DEPT_CD
			stmt.setString(++index, "");//CT_OWN_DEPT_NM
			stmt.setString(++index, "001");	//		DATA_STAT_CD
			stmt.setString(++index, "");//CT_LENG
			stmt.setString(++index, "");//VD_QLTY
			stmt.setString(++index, "");//ASP_RTO_CD
			stmt.setString(++index, "");//EDTRID
			stmt.setString(++index,"");		//	KFRM_PATH
			stmt.setString(++index, "");//KFRM_PX_CD
			stmt.setInt(++index,0);//TOT_KFRM_NUMS
			stmt.setString(++index,"Y");//USE_YN
			stmt.setString(++index, "");//DEL_DD
			stmt.setString(++index, dateTime);	//REG_DT
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index, "D080009");//MODRID
			stmt.setString(++index, dateTime);//MOD_DT
			stmt.setInt(++index, 0);	//DURATION
			stmt.setInt(++index, 0);//MCU_SEQ
			stmt.setInt(++index, 0);	//CMS_CT_ID
			stmt.setString(++index, "N");//COPY_OBJECT_YN
			stmt.setInt(++index,0);//USE_CONT
			stmt.setString(++index, "Y");//ARCHIVE_YN
			stmt.setString(++index, CommonUtl.checkNull(nleDO.getMedia_id()));	//MEDIA_ID
			stmt.setString(++index, "N");	//DEL_YN
			nleDO.setCt_id(ctid);

			updateCount = stmt.executeUpdate();


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * Content_inst_tbl에 데이터를 집어 넣는다(mxf)
	 * @param NleDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForHigh(NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID   ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForHigh######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, nleDO.getCt_id());//CT_ID
			stmt.setString(++index, "105");//CTI_FMT
			stmt.setString(++index,"N");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "");//BIT_RT
			stmt.setString(++index, "");//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			stmt.setInt(++index, 0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,cti_id+".MXF");//WRK_FILE_NM
			stmt.setString(++index, nleDO.getFile_nm());////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, dateTime);//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "N");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			nleDO.setCti_id(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * Content_inst_tbl에 데이터를 집어 넣는다(wmv)
	 * @param NleDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForLow(NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForLow######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, nleDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "");//BIT_RT
			stmt.setString(++index, "");//FRM_PER_SEC
			stmt.setString(++index, "");//DRP_FRM_YN
			stmt.setInt(++index, 0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,cti_id+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, dateTime);//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "");//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN
			nleDO.setCti_idForLow(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertNleTc(NleDO nleDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertNleTc######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(nleDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			//String storage =  nleDO.getStorage_path().replace('\\', d);
			stmt.setString(++index, nleDO.getFile_path());//INPUT_HR

			stmt.setString(++index, dasHandler.getProperty("MP4")+"/"+dateTime2+"/"+nleDO.getCt_id());//INPUT_LR
			stmt.setString(++index,  dasHandler.getProperty("MP4")+"/"+dateTime2+"/"+nleDO.getCt_id());	//OUTPUT_LR_PATH
			stmt.setString(++index, dasHandler.getProperty("MP4")+"/"+dateTime2+"/"+nleDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			stmt.setString(++index, nleDO.getCti_idForLow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,nleDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, nleDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index, nleDO.getFile_nm());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE



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
	 * conner tbl에 집어넣는다
	 * @param NleDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCornerInfo(NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CORNER_TBL (    ");
		buf.append("\n CN_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_NM    ");
		buf.append("\n ,CN_TYPE_CD    ");
		buf.append("\n ,SOM    ");
		buf.append("\n ,EOM    ");
		buf.append("\n ,CN_INFO    ");
		buf.append("\n ,RPIMG_KFRM_SEQ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,S_FRAME   ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertCornerInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long cnid= selectCnid();
			stmt.setLong(++index,cnid);//CN_ID
			stmt.setLong(++index, nleDO.getMaster_id());//MASTER_ID
			stmt.setString(++index, "");//CN_NM
			stmt.setString(++index, "");//CN_TYPE_CD
			stmt.setString(++index, nleDO.getSom());//SOM
			stmt.setString(++index, nleDO.getEom());//EOM
			stmt.setString(++index, "");//CN_INFO
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index,dateTime);//MOD_DT
			stmt.setString(++index,  "D080009");//MODRID
			stmt.setLong(++index, 0);			//DURATION
			stmt.setLong(++index, 0);//RPIMG_CT_ID
			stmt.setLong(++index,0);//S_FRAME


			nleDO.setCn_id(cnid);


			updateCount = stmt.executeUpdate();






			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * CONTENTS_MAPP_TBL 에 집어넣는다
	 * @param NleDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsMappInfo(NleDO nleDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_MAPP_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,S_DURATION    ");
		buf.append("\n ,E_DURATION    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,CN_SEQ    ");
		buf.append("\n ,DEL_DD )   ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertContentsMappInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	

			stmt.setLong(++index,nleDO.getCt_id());
			stmt.setLong(++index, 0);
			stmt.setLong(++index, nleDO.getMaster_id());
			stmt.setLong(++index, nleDO.getCn_id());
			stmt.setInt(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, "");			
			stmt.setString(++index,dateTime);	
			stmt.setInt(++index, 1);
			stmt.setString(++index,"");	

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 영상 구분을 구한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String selectCtcla(long ct_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  ct_cla from contents_tbl where ct_id= ? with ur ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######selectCtcla######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setLong(++index, ct_id);
			rs = stmt.executeQuery();
			String ct_cla ="";
			if(rs.next()){

				ct_cla = rs.getString("ct_cla");
			}


			return ct_cla;
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
	 * 해당 영상이 pds 요청인지 재생성인지 구분
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String isPDSorRecreate(long seq) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  tc_type from tc_job_tbl where seq= ?");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######isPDSorRecreate######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setLong(++index, seq);
			rs = stmt.executeQuery();
			String tc_type ="";
			if(rs.next()){
				tc_type = rs.getString("tc_type");
			}
			//			logger.debug("rs.next()"+rs.next());
			//			logger.debug("tc_type" +tc_type);
			if(!tc_type.equals("")){
				return tc_type;
			}else{

				return "";
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
	 * pds_pgm_id를 구한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String selectPdsPgmId(long ct_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select pds_cms_pgm_id from metadat_mst_tbl where master_id = (select master_id from contents_mapp_tbl where ct_id = ?  order by reg_dt desc fetch first 1 rows only )");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPdsPgmId######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setLong(++index, ct_id);
			rs = stmt.executeQuery();

			//	con.setAutoCommit(true);
			String pds_cms_id = "";

			if(rs.next()){
				pds_cms_id = rs.getString("pds_cms_pgm_id");
			}


			return pds_cms_id;
		} 
		catch (Exception e) 
		{
			logger.error(buf.toString());


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
			release(rs, stmt, con);
		}	
	}




	/**
	 * 해당 영상이 pds 요청인지 재생성인지 구분
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String isReq_cd(long seq) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  req_cd from tc_job_tbl where seq= ?");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isReq_cd######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setLong(++index, seq);
			rs = stmt.executeQuery();
			String req_cd ="";
			if(rs.next()){;
			req_cd = rs.getString("req_cd");
			}



			if(!req_cd.equals("")){
				return req_cd;
			}else{

				return "";
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
	 * 승인테이블에 해당 계정이있는지 확인한다
	 * @param user_no  사번
	 * @param pgm_Cd 프로그램코드                                                                                                                                                                         
	 * @return boolean                                                                                                                                                                                       
	 * @throws Exception 
	 */
	public boolean isThereApproveinfo(String user_no, String pgm_Cd) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.approve_info_Tbl where approve_user_num = '"+user_no+"'  and pgm_id='"+pgm_Cd+"'\n");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereApproveinfo######## con : " + con);
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
			release(null, null, con);
		}
	}


	/**
	 * 기존 연동 테이블에 해당 계정이있는지 확인한다
	 * @param user_no  사번
	 * @param pgm_Cd 프로그램코드                                                                                                                                                                         
	 * @return boolean                                                                                                                                                                                       
	 * @throws Exception 
	 */
	public boolean isTherePgminfo(String user_no, String pgm_Cd) throws Exception
	{
		//PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.PDS_PGMINFO_TBL2 where producer_id = '"+user_no+"'  and program_code='"+pgm_Cd+"'\n");
		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######isTherePgminfo######## con : " + con);
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
			release(null, null, con);
		}
	}







	/**
	 * 연동된 정보를 승인 테이블에 추가한다
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertApproveInfo(PgmInfoDO pgmDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.APPROVE_INFO_TBL  ( ");
		buf.append("\n   PGM_ID ");
		buf.append("\n  ,PGM_NM ");
		buf.append("\n  ,APP_GUBUN ");
		buf.append("\n  ,DEPT_CD ");
		buf.append("\n  ,APPROVE_USER_NM ");
		buf.append("\n  ,POSITION ");
		buf.append("\n  ,APPROVE_USER_NUM ");
		buf.append("\n ) values ");
		buf.append("\n ( ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertApproveInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, pgmDO.getPROGRAM_CODE());
			stmt.setString(++index, pgmDO.getPROGRAM_NAME());
			stmt.setString(++index, "001");
			stmt.setString(++index, pgmDO.getDept_cd());
			stmt.setString(++index, pgmDO.getPRODUCER_NAME());
			stmt.setString(++index, "");
			stmt.setString(++index, pgmDO.getUser_no());


			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}
	}



	public PgmInfoDO selectUserInfo(String user_id) throws Exception{

		StringBuffer buf = new StringBuffer();


		buf.append(" \n  select ");
		buf.append(" \n   dept_cd  \n");
		buf.append(" \n   ,user_num  \n");
		buf.append(" \n   ,position \n");
		buf.append(" from user_info_tbl where sbs_user_id = ?");		


		//Page에 따른 계산을 한다.


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######selectUserInfo######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setString(++index, user_id);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			PgmInfoDO item = new PgmInfoDO();
			while(rs.next())
			{





				item.setDept_cd(				rs.getString("dept_cd"));
				item.setUser_no(				rs.getString("user_num"));
				item.setPosition(				rs.getString("position"));
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
	 * ct_id, cti_id(wmv) 값을 얻어온다
	 * @param media_id 미디어id                                                                                                                                                                                              
	 * @return  PdsArchiveDO    
	 * @throws Exception 
	 *  */
	public PdsArchiveDO selectTcInfo(String media_id) throws Exception
	{
		String query = SystemManageStatement.selectTCInfoQuery(media_id);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTcInfo######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();

			if(rs.next())
			{
				PdsArchiveDO item = new PdsArchiveDO();
				item.setCt_id(rs.getLong("ct_id"));
				item.setCti_idForlow(rs.getLong("cti_id"));


				return item;
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
	 * 마스터ID를 조회한다(미디어id)
	 * @param media_id 미디어id
	 * @return
	 * @throws Exception 
	 */
	public long selectMasterId(String media_id) throws Exception {
		String query = SystemManageStatement.selectMaster_idQuery(media_id);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(1, media_id);

			rs = stmt.executeQuery();
			long masterid=0;
			if(rs.next()) {
				masterid = rs.getLong("master_id");

				return masterid;
			} else {
				return 0;
			}
		} catch (Exception e) {
			logger.error(e);
			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 보존기간 만료일을 구한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String selectRsv_end_dd(long master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select  left(reg_dt,8) as  reg_dt from metadat_mst_tbl where master_id= ?");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectRsv_end_dd######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setLong(++index, master_id);
			rs = stmt.executeQuery();
			rs.next();

			String rsv_end_dd = rs.getString("reg_dt");



			return rsv_end_dd;
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
	 * annot_info_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertAnnotInfo(PdsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.Annot_Info_tbl (   ");
		buf.append("\n   ANNOT_ID   ");
		buf.append("\n  ,CN_ID   ");
		buf.append("\n  ,CT_ID  ");
		buf.append("\n 	,MASTER_ID    ");
		buf.append("\n  ,ANNOT_CLF_CD    ");
		buf.append("\n  ,ANNOT_CLF_CONT   ");
		buf.append("\n  ,SOM   ");
		buf.append("\n  ,EOM  ");
		buf.append("\n 	,CONT    ");
		buf.append("\n  ,REGRID    ");
		buf.append("\n  , REG_DT   ");
		buf.append("\n  ,MODRID   ");
		buf.append("\n  ,MOD_DT  ");
		buf.append("\n  ,DURATION   ");
		buf.append("\n  ,S_FRAME   ");
		buf.append("\n  ,GUBUN  ");
		buf.append("\n  ,ENTIRE_YN  ");


		buf.append("\n ) VALUES (   ");

		buf.append("\n  ?, ?, ?  ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() != null) {
				con = pdsInfoDO.getConn();
			} else {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			}

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			long annot_id = selectPreviewid();
			stmt.setLong(++index, annot_id);//ANNOT_ID
			stmt.setLong(++index, pdsInfoDO.getCn_id());			//CN_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());	//CT_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());	//MASTER_ID
			stmt.setString(++index, pdsInfoDO.getRist_clf_cd());	//ANNOT_CLF_CD
			stmt.setString(++index, pdsInfoDO.getAnnot_clf_cont());//ANNOT_CLF_CONT
			stmt.setString(++index, pdsInfoDO.getSom());			//SOM
			if(StringUtils.isNotEmpty(pdsInfoDO.getEom())){
				stmt.setString(++index, pdsInfoDO.getEom());	//EOM
			}else{
				stmt.setString(++index, "00:00:00:00");	//EOM
			}
			stmt.setString(++index, "");	//CONT
			stmt.setString(++index, "D080009");	//REGRID
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);	//REG_DT			
			stmt.setString(++index, "");			//MODRID
			stmt.setString(++index, "");	//MOD_DT

			if(StringUtils.isNotEmpty(pdsInfoDO.getBrd_leng())){
				long duration = CommonUtl.changeTime(pdsInfoDO.getBrd_leng());
				stmt.setLong(++index, duration);//DURATION
			}else{
				stmt.setLong(++index, 0);//DURATION
			}

			stmt.setLong(++index, 0);			//S_FRAME
			stmt.setString(++index, "L");	//GUBUN
			stmt.setString(++index, "Y");	//GUBUN
			updateCount = stmt.executeUpdate();


			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}




	/**
	 * 프로그램 복본관리를 수정한다.(das pgm_id기준)
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateCopyRequest(long master_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		StringBuffer buf2 = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateCopyRequest######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;
			int updateCount2 =0;

			buf.append("\n update DAS.CONTENTS_INST_TBL set ");
			buf.append("\n 	ETC = '복본생성중입니다'  ");

			buf.append("\n where CT_ID = (select ct_id from contents_mapp_Tbl where master_id = "+master_id+")  ");
			buf.append("\n WITH UR	");
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, "Y");
			//			stmt.setString(++index, copyDO.getPgm_id_y());

			updateCount = stmt.executeUpdate();





			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount+updateCount2);
			}

			if(updateCount+updateCount2 == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount+updateCount2;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt2, null);
			release(null, stmt, con);
		}
	}






	/**
	 * 수동 아카이브 요청건에 대해서 메타 입력
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertMetadatTbl(ManualArchiveDO pgmDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into METADAT_MST_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,PGM_CD    ");
		buf.append("\n ,EPIS_NO    ");
		buf.append("\n ,TITLE    ");
		buf.append("\n ,CTGR_L_CD    ");
		buf.append("\n ,CTGR_M_CD    ");
		buf.append("\n ,CTGR_S_CD    ");
		buf.append("\n ,BRD_DD    ");
		buf.append("\n ,FINAL_BRD_YN   ");
		buf.append("\n ,SNPS    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,BRD_BGN_HMS     ");
		buf.append("\n ,BRD_END_HMS    ");
		buf.append("\n ,BRD_LENG    ");
		buf.append("\n ,PGM_RATE    ");
		buf.append("\n ,DRT_NM    ");
		buf.append("\n ,PRODUCER_NM    ");
		buf.append("\n ,WRITER_NM    ");
		buf.append("\n ,PRDT_IN_OUTS_CD    ");
		buf.append("\n ,PRDT_DEPT_CD    ");
		buf.append("\n ,PRDT_DEPT_NM    ");
		buf.append("\n ,ORG_PRDR_NM   ");
		buf.append("\n ,MC_NM    ");
		buf.append("\n ,CAST_NM    ");
		buf.append("\n ,CMR_DRT_NM   ");
		buf.append("\n ,FM_DT    ");
		buf.append("\n ,CMR_PLACE    ");
		buf.append("\n ,SPC_INFO    ");
		buf.append("\n ,REQ_CD    ");
		buf.append("\n ,SEC_ARCH_NM    ");
		buf.append("\n ,SEC_ARCH_ID    ");
		buf.append("\n ,GATH_CO_CD    ");
		buf.append("\n ,GATH_CLF_CD    ");
		buf.append("\n ,ARCH_REG_DD    ");
		buf.append("\n ,ARRG_END_DT   ");
		buf.append("\n ,WORK_PRIO_CD    ");
		buf.append("\n ,RSV_PRD_CD    ");
		buf.append("\n ,CPRTR_NM    ");		
		buf.append("\n ,CPRT_TYPE    ");
		buf.append("\n ,CPRT_TYPE_DSC    ");
		buf.append("\n ,VIEW_GR_CD   ");
		buf.append("\n ,DLBR_CD    ");
		buf.append("\n ,AWARD_HSTR    ");
		buf.append("\n ,RPIMG_KFRM_SEQ     ");
		buf.append("\n ,TAPE_ID    ");  
		buf.append("\n ,TAPE_ITEM_ID    ");
		buf.append("\n ,TAPE_MEDIA_CLF_CD    ");
		buf.append("\n ,RSV_PRD_END_DD    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,MOD_DT   ");
		buf.append("\n ,GATH_DEPT_CD    ");
		buf.append("\n ,MCUID    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,DATA_STAT_CD    ");
		buf.append("\n ,ING_REG_DD    ");
		buf.append("\n ,COPY_KEEP    ");
		buf.append("\n ,CLEAN_KEEP    ");
		buf.append("\n ,MUSIC_INFO    ");
		buf.append("\n ,RST_CONT    ");
		buf.append("\n ,RERUN    ");
		buf.append("\n ,ACCEPTOR_ID    ");
		buf.append("\n ,SUB_TTL    ");
		buf.append("\n ,ARRANGE_NM   ");
		buf.append("\n ,LOCK_STAT_CD    ");
		buf.append("\n ,ERROR_STAT_CD    ");
		buf.append("\n ,ARCH_ROUTE    ");
		buf.append("\n ,RIST_CLF_CD     ");
		buf.append("\n ,MANUAL_YN     ");
		buf.append("\n ,PDS_CMS_PGM_ID    ");
		buf.append("\n ,COCD    ");
		buf.append("\n ,CHENNEL_CD )   ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMetadatTbl######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			index = 0;	
			long masterid= selectMasterid();
			stmt.setLong(++index,masterid);//MASTER_ID
			stmt.setInt(++index, 0);//PGM_ID
			stmt.setString(++index,"");//PGM_CD
			if(!pgmDO.getEpis_no().equals("")){
				stmt.setInt(++index, Integer.parseInt(pgmDO.getEpis_no()));//EPIS_NO
			}else{
				stmt.setInt(++index, 0);//EPIS_NO	
			}
			stmt.setString(++index, pgmDO.getTitle());//TITLE
			stmt.setString(++index, pgmDO.getCtgr_l_cd());//CTGR_L_CD
			stmt.setString(++index, "");//CTGR_M_CD
			stmt.setString(++index,"");//CTGR_S_CD
			if(pgmDO.getCtgr_l_cd().equals("200")){
				stmt.setString(++index, pgmDO.getFm_dt());//BRD_DD
			}else {
				stmt.setString(++index, "");//BRD_DD
			}
			stmt.setString(++index, "");//FINAL_BRD_YN
			stmt.setString(++index, "");//SNPS
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index,"");//BRD_BGN_HMS
			stmt.setString(++index,"");//BRD_END_HMS
			stmt.setString(++index,"00:00:00:00");//BRD_LENG
			//stmt.setString(++index,"00:00:11");//BRD_LENG
			stmt.setString(++index, "");//PGM_RATE
			stmt.setString(++index, pgmDO.getProducer_nm());//DRT_NM
			stmt.setString(++index, pgmDO.getProducer_nm());//PRODUCER_NM
			stmt.setString(++index, "");//WRITER_NM
			stmt.setString(++index,"");//PRDT_IN_OUTS_CD
			stmt.setString(++index, "");//PRDT_DEPT_CD
			stmt.setString(++index, "");//prdt_dept_nm
			stmt.setString(++index, "");//ORG_PRDR_NM
			stmt.setString(++index, "");//MC_NM
			stmt.setString(++index, "");//CAST_NM
			stmt.setString(++index,"");////CMR_DRT_NM
			if(pgmDO.getCtgr_l_cd().equals("100")){
				stmt.setString(++index, pgmDO.getFm_dt());//FM_DT
			}else{
				stmt.setString(++index, "");//FM_DT
			}
			stmt.setString(++index, pgmDO.getCmr_place());//CMR_PLACE
			stmt.setString(++index, "");//SPC_INFO
			stmt.setString(++index, "");//REQ_CD
			stmt.setString(++index,"");//SEC_ARCH_NM
			stmt.setString(++index, "");//SEC_ARCH_ID
			stmt.setString(++index, "");//GATH_CO_CD
			stmt.setString(++index,"");//GATH_CLF_CD
			stmt.setString(++index, "");//ARCH_REG_DD
			stmt.setString(++index, "");//ARRG_END_DT
			stmt.setString(++index, "");//WORK_PRIO_CD
			stmt.setString(++index, "");//RSV_PRD_CD

			stmt.setString(++index, "SBS");//CPRTR_NM	


			stmt.setString(++index, "001");//CPRT_TYPE


			stmt.setString(++index, "");//CPRT_TYPE_DSC
			stmt.setString(++index,"");//VIEW_GR_CD
			stmt.setString(++index, "");//DLBR_CD
			stmt.setString(++index, "");//AWARD_HSTR
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, "");//TAPE_ID
			stmt.setString(++index, "");//TAPE_ITEM_ID
			stmt.setString(++index,"");//TAPE_MEDIA_CLF_CD
			stmt.setString(++index, "");//RSV_PRD_END_DD
			stmt.setString(++index, "");//del_dd
			stmt.setString(++index, "Y");//USE_YN
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index, dateTime);//날짜
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");//날짜
			stmt.setString(++index, "");//GATH_DEPT_CD
			stmt.setString(++index, "");//MCUID
			stmt.setInt(++index, 0);//RPIMG_CT_ID
			stmt.setString(++index, "000");//DATA_STAT_CD
			stmt.setString(++index, dateTime);//ING_REG_DD
			stmt.setString(++index, "");//COPY_KEEP
			stmt.setString(++index, "");//CLEAN_KEEP
			stmt.setString(++index, "");//MUSIC_INFO
			stmt.setString(++index, "");//RST_CONT
			stmt.setString(++index, "");//RERUN
			stmt.setString(++index, "");//ACCEPTOR_ID
			stmt.setString(++index, pgmDO.getSub_ttl());//SUB_TTL
			stmt.setString(++index, "");//ARRANGE_NM
			stmt.setString(++index, "N");//LOCK_STAT_CD
			stmt.setString(++index, "000");//ERROR_STAT_CD
			stmt.setString(++index, "DP");//ARCH_ROUTE
			stmt.setString(++index, pgmDO.getRist_clf_cd());//RIST_CLF_CD
			stmt.setString(++index, "N");//MANUAL_YN
			stmt.setString(++index, pgmDO.getCms_id());//pds_cms_id
			stmt.setString(++index, pgmDO.getCocd());//COCD
			stmt.setString(++index, pgmDO.getChennel());//CHENNEL
			pgmDO.setMaster_id(masterid);



			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * contents_tbl에 자료를 집어 넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsInfo (ManualArchiveDO pdsInfoDO) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO CONTENTS_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,CT_TYP    ");
		buf.append("\n ,CT_CLA    ");
		buf.append("\n ,CT_NM    ");
		buf.append("\n ,CONT    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,CT_OWN_DEPT_CD    ");
		buf.append("\n ,CT_OWN_DEPT_NM    ");
		buf.append("\n ,DATA_STAT_CD   ");
		buf.append("\n ,CT_LENG    ");
		buf.append("\n ,VD_QLTY    ");
		buf.append("\n ,ASP_RTO_CD    ");
		buf.append("\n ,EDTRID    ");
		buf.append("\n ,KFRM_PATH    ");
		buf.append("\n ,KFRM_PX_CD    ");
		buf.append("\n ,TOT_KFRM_NUMS    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,MCU_SEQ    ");
		buf.append("\n ,CMS_CT_ID    ");
		buf.append("\n ,COPY_OBJECT_YN    ");
		buf.append("\n ,USE_CONT    ");
		buf.append("\n ,ARCHIVE_YN    ");
		buf.append("\n ,MEDIA_ID    ");
		buf.append("\n ,DEL_YN    )    ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");

		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertContentsInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long ctid= selectCtid();
			stmt.setLong(++index, ctid);//CT_ID
			stmt.setInt(++index, 1);//CT_SEQ

			stmt.setString(++index,"003");//CT_TYP
			stmt.setString(++index, pdsInfoDO.getCt_cla());//CT_CLA
			stmt.setString(++index, "");			//CT_NM
			stmt.setString(++index, "");//CONT
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, "");//CT_OWN_DEPT_CD
			stmt.setString(++index, "");//CT_OWN_DEPT_NM
			stmt.setString(++index, "000");	//		DATA_STAT_CD
			stmt.setString(++index,"");//CT_LENG
			stmt.setString(++index, "");//VD_QLTY
			stmt.setString(++index, pdsInfoDO.getAsp_rto_cd());//ASP_RTO_CD
			stmt.setString(++index, "");//EDTRID
			stmt.setString(++index,"");		//	KFRM_PATH
			stmt.setString(++index, "");//KFRM_PX_CD
			stmt.setInt(++index,0);//TOT_KFRM_NUMS
			stmt.setString(++index,"Y");//USE_YN
			stmt.setString(++index, "");//DEL_DD
			stmt.setString(++index, dateTime);	//REG_DT
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setInt(++index, 0);	//DURATION
			stmt.setInt(++index, 0);//MCU_SEQ
			stmt.setInt(++index, 0);	//CMS_CT_ID
			stmt.setString(++index, "N");//COPY_OBJECT_YN
			stmt.setInt(++index,0);//USE_CONT
			stmt.setString(++index, "Y");//ARCHIVE_YN
			String media_id =codeInfoDAO.getMediaId();
			stmt.setString(++index, pdsInfoDO.getNew_media_id());	//MEDIA_ID
			stmt.setString(++index, "N");	//DEL_YN
			pdsInfoDO.setCt_id(ctid);

			updateCount = stmt.executeUpdate();

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();

			return updateCount;
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
	 * Content_inst_tbl에 데이터를 집어 넣는다(mxf)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForHigh(ManualArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,WMV_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID   ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForHigh######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "105");//CTI_FMT
			stmt.setString(++index,"N");//ARCH_STE_YN
			stmt.setString(++index, "002");//ME_CD
			stmt.setString(++index, "0");//BIT_RT
			stmt.setString(++index, "0");//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			if(!pdsInfoDO.getVd_hresol().equals("")){
				stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_hresol()));//VD_HRESOL
			}else{
				stmt.setInt(++index, 0);//VD_HRESOL	
			}
			if(!pdsInfoDO.getVd_vresol().equals("")){
				stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_vresol()));	//	VD_VRESOL	
			}else{
				stmt.setInt(++index, 0);//VD_HRESOL	
			}
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getRecode_yn());//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, pdsInfoDO.getFl_path());//FL_PATH
			stmt.setString(++index,cti_id+".MXF");//WRK_FILE_NM
			stmt.setString(++index, pdsInfoDO.getNew_media_id()+".mxf");////ORG_FILE_NM
			stmt.setLong(++index, pdsInfoDO.getFile_size());//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "N");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index,"N");	//WMV_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			pdsInfoDO.setCti_idForHigh(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * Content_inst_tbl에 데이터를 집어 넣는다(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForLow(ManualArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForLow######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "0");//BIT_RT
			stmt.setString(++index,"0");//FRM_PER_SEC
			stmt.setString(++index, "");//DRP_FRM_YN
			if(!pdsInfoDO.getVd_hresol().equals("")){
				stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_hresol()));//VD_HRESOL
			}else{
				stmt.setInt(++index, 0);//VD_HRESOL	
			}
			if(!pdsInfoDO.getVd_vresol().equals("")){
				stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_vresol()));	//	VD_VRESOL	
			}else{
				stmt.setInt(++index, 0);//VD_HRESOL	
			}
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getRecode_yn());//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,cti_id+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM  
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN
			pdsInfoDO.setCti_idForLow(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * conner tbl에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCornerInfo(ManualArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CORNER_TBL (    ");
		buf.append("\n CN_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_NM    ");
		buf.append("\n ,CN_TYPE_CD    ");
		buf.append("\n ,SOM    ");
		buf.append("\n ,EOM    ");
		buf.append("\n ,CN_INFO    ");
		buf.append("\n ,RPIMG_KFRM_SEQ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,S_FRAME   ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertCornerInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long cnid= selectCnid();
			stmt.setLong(++index,cnid);//CN_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());//MASTER_ID
			stmt.setString(++index, "");//CN_NM
			stmt.setString(++index, "003");//CN_TYPE_CD
			stmt.setString(++index, "00:00:00:00");//SOM
			stmt.setString(++index, "00:00:00:00");//EOM
			stmt.setString(++index, "");//CN_INFO
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "D080009");//REGRID
			stmt.setString(++index,"");//MOD_DT
			stmt.setString(++index,  "D080009");//MODRID
			stmt.setLong(++index, 0);			//DURATION
			stmt.setLong(++index, 0);//RPIMG_CT_ID
			stmt.setLong(++index,0);//S_FRAME


			pdsInfoDO.setCn_id(cnid);


			updateCount = stmt.executeUpdate();






			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * CONTENTS_MAPP_TBL 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsMappInfo(ManualArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_MAPP_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,S_DURATION    ");
		buf.append("\n ,E_DURATION    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,CN_SEQ    ");
		buf.append("\n ,DEL_DD )   ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertContentsMappInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	

			stmt.setLong(++index,pdsInfoDO.getCt_id());
			stmt.setLong(++index, 0);
			stmt.setLong(++index, pdsInfoDO.getMaster_id());
			stmt.setLong(++index, pdsInfoDO.getCn_id());
			stmt.setInt(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, "");			
			stmt.setString(++index,dateTime);	
			stmt.setInt(++index, 1);
			stmt.setString(++index,"");	

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * annot_info_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertAnnotInfo(ManualArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.Annot_Info_tbl (   ");
		buf.append("\n   ANNOT_ID   ");
		buf.append("\n  ,CN_ID   ");
		buf.append("\n  ,CT_ID  ");
		buf.append("\n 	,MASTER_ID    ");
		buf.append("\n  ,ANNOT_CLF_CD    ");
		buf.append("\n  ,ANNOT_CLF_CONT   ");
		buf.append("\n  ,SOM   ");
		buf.append("\n  ,EOM  ");
		buf.append("\n 	,CONT    ");
		buf.append("\n  ,REGRID    ");
		buf.append("\n  , REG_DT   ");
		buf.append("\n  ,MODRID   ");
		buf.append("\n  ,MOD_DT  ");
		buf.append("\n  ,DURATION   ");
		buf.append("\n  ,S_FRAME   ");
		buf.append("\n  ,GUBUN  ");
		buf.append("\n  ,ENTIRE_YN  ");


		buf.append("\n ) VALUES (   ");

		buf.append("\n  ?, ?, ?  ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertAnnotInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			long annot_id = selectPreviewid();
			stmt.setLong(++index, annot_id);//ANNOT_ID
			stmt.setLong(++index, pdsInfoDO.getCn_id());			//CN_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());	//CT_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());	//MASTER_ID
			stmt.setString(++index, pdsInfoDO.getRist_clf_cd());	//ANNOT_CLF_CD
			stmt.setString(++index, "");//ANNOT_CLF_CONT
			stmt.setString(++index, "00:00:00:00");			//SOM
			stmt.setString(++index, "00:00:00:00");	//EOM
			stmt.setString(++index, "");	//CONT
			stmt.setString(++index, "D080009");	//REGRID
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);	//REG_DT			
			stmt.setString(++index, "");			//MODRID
			stmt.setString(++index, "");	//MOD_DT
			stmt.setLong(++index, 0);//DURATION
			stmt.setLong(++index, 0);			//S_FRAME
			stmt.setString(++index, "L");	//GUBUN
			stmt.setString(++index, "Y");	//ENTIRE_YN

			updateCount = stmt.executeUpdate();






			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertManual(ManualArchiveDO manualArchiveDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ,cocd ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertManual######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, manualArchiveDO.getNew_media_id());//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			//String storage =  pdsArchiveDO.getStorage_path().replace('\\', d);
			stmt.setString(++index, manualArchiveDO.getFl_path());//INPUT_HR


			stmt.setString(++index, manualArchiveDO.getFl_path()+"/"+manualArchiveDO.getCt_id());//INPUT_LR
			if(manualArchiveDO.getCocd().equals("S")){
				stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+manualArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+manualArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}else{
				stmt.setString(++index,  dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+manualArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+manualArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH

			}
			stmt.setString(++index, manualArchiveDO.getCti_idForLow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,manualArchiveDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, manualArchiveDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index, manualArchiveDO.getNew_media_id()+".mxf");//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			stmt.setString(++index,"003");//TC_TYPE
			if(manualArchiveDO.getCocd().equals("")){
				stmt.setString(++index,"S");//COCD
			}else{
				stmt.setString(++index,manualArchiveDO.getCocd());//COCD	
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
	 * 미디어id 중복여부 판단
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereMediaId(String Media_id) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  das.contents_tbl where media_id = '"+ Media_id + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereMediaId######## con : " + con);

			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return false;
			} else {
				return true;
			}
		}catch (Exception e) {
			throw e;
		} finally {
			release(null, null, con);
		}
	}






	/**
	 * 미디어id 중복여부 판단(수동)
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereMediaIdForManual(String Media_id) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  das.temp_manual_tbl where new_media_id = '"
				+ Media_id + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereMediaIdForManual######## con : " + con);

			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return false;
			} else {
				return true;
			}
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}
	/**
	 * 수동아카이브 건에 편집시 편집상태 변경('001' 편집중, '002' 편집완료)
	 * @param code 코드
	 * @param ct_ids 컨텐츠id
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateEdtrId(String code,String ct_ids) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateEdtrId######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n UPDATE DAS.CONTENTS_TBL SET ");
			buf.append("\n EDTRID= '"+code+"'  "); // 001 편집중, 002 편집완료
			buf.append("\n where CT_ID in ("+StringUtils.stripEnd(ct_ids, ",")+" )");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			int index = 0;
			//			stmt.setLong(++index, ct_id);

			updateCount = stmt.executeUpdate();


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 수동아카이브 건에 편집시 편집상태 변경('001' 편집중, '002' 편집완료)
	 * @param code 코드
	 * @param media_id 미디어id
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateEdtrIdbyMediaId(String code,String media_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateEdtrIdbyMediaId######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n UPDATE DAS.CONTENTS_TBL SET ");
			buf.append("\n EDTRID= '"+code+"'  "); // 001 편집중, 002 편집완료
			buf.append("\n where media_id = ?");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			int index = 0;
			stmt.setString(++index, media_id);

			updateCount = stmt.executeUpdate();


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 수동아카이브 건에 원본영상 사용여부N 표기 변경
	 * @param media_id 미디어id
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateMetaYn(String media_id) throws Exception
	{
		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateMetaYn######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n UPDATE DAS.METADAT_MST_tBL SET ");
			buf.append("\n USE_YN='N'  "); 
			buf.append("\n ,del_dd='99991231'  "); 
			buf.append("\n where master_id in (select map.master_id from contents_mapp_tbl map");
			buf.append("\n inner join contents_tbl con on con.ct_id = map.ct_id	");
			buf.append("\n where 	");
			buf.append("\n con.media_id = ? )	");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			int index = 0;
			stmt.setString(++index, media_id);

			updateCount = stmt.executeUpdate();


			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List selectStorageList(StorageDO storageDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectStorageList());



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######selectStorageList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setString(++index, storageDO.getStorage_nm());
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StorageDO item = new StorageDO();



				item.setStorgae_size(rs.getString("USE"));
				item.setLimite(rs.getString("LIMITE"));
				int size=Integer.parseInt(item.getStorgae_size().trim());
				int limit=Integer.parseInt(item.getLimite().trim());

				if(size>limit){
					item.setStorage_yn("FALSE");
				}else{
					item.setStorage_yn("TRUE");
				}
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
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List selectStorageInfo() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.selectStorageInfo());



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectStorageInfo######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			stmt.setString(++index, "MP2");
			stmt.setString(++index, "MP4");
			stmt.setString(++index, "ARCREQ");

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StorageDO item = new StorageDO();



				item.setUsing_sz(rs.getString("USE"));
				item.setStorgae_sz(rs.getLong("total_capacity"));
				item.setStorage_nm(rs.getString("storage"));
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
	 * PDAS 아카이브  상태변환
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public String DeletePDSArchiveStatus(DeleteDO pgmDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n update  das.contents_tbl set  media_id= ''  where media_id ='"+pgmDO.getMedia_id()+"'");


		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######DeletePDSArchiveStatus######## con : " + con);

			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	


			updateCount = stmt.executeUpdate();

			String result="";
			if(updateCount == 0){
				result="0";
			}else{
				result="1";
			}

			return result;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}


	public String deletePdsINfo(DeleteDO pgmDO)throws Exception{

		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf=new StringBuffer();
		StringBuffer buf2=new StringBuffer();
		StringBuffer buf3=new StringBuffer();
		StringBuffer buf4=new StringBuffer();

		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			con.setAutoCommit(false);

			logger.debug("meida_id: "+pgmDO.getMedia_id());

			buf.append("\n select distinct ct_id, master_id from contents_mapp_tbl where ct_id  = (select ct_id from contents_tbl where media_id = ?) ");
			buf2.append("\n update metadat_mst_tbl set del_dd ='99991231',use_yn = 'N' ,group_id=-1 where MASTER_ID =? ");
			buf3.append("\n update contents_mapp_tbl set del_dd = ? where  CT_ID =? ");
			buf4.append("\n update contents_tbl set media_id = 'delete' where  CT_ID =? ");

			stmt=con.prepareStatement(buf.toString());

			int index=0;
			int updatecount=0;
			stmt.setString(++index	, pgmDO.getMedia_id());
			rs = stmt.executeQuery();

			// master_id, ct_id finding
			DeleteDO item = new DeleteDO();
			while(rs.next()) {
				item.setMaster_id(rs.getLong("master_id"));
				item.setCt_id(rs.getLong("ct_id"));
			}

			if(item.getMaster_id() != null && item.getCt_id() != null) {
				// metadata_mst_tbl update
				stmt.close();
				stmt = con.prepareStatement(buf2.toString());

				index=0;
				stmt.setLong(++index	, item.getMaster_id());
				stmt.executeUpdate();

				// contents_mapp_tbl update
				stmt.close();
				stmt = con.prepareStatement(buf3.toString());

				String dateString = CalendarUtil.getDateTime("yyyyMMdd");
				index=0;
				stmt.setString(++index	, dateString);
				stmt.setLong(++index	, item.getCt_id());
				stmt.executeUpdate();

				// contents_tbl update
				stmt.close();
				stmt = con.prepareStatement(buf4.toString());

				index=0;
				stmt.setLong(++index	, item.getCt_id());
				stmt.executeUpdate();

				externalDAO.deletePDSJOb(item.getCt_id());
				if(logger.isInfoEnabled()) {
					logger.info("WorkflowService's Force DeleteMethod Call! - :"+item.getCt_id());
				}
				return "1";
			} else {
				if(logger.isInfoEnabled()) {
					logger.info("contents meta not found!! - "+pgmDO.getMedia_id());
				}
				return "0";
			}

		} catch (Exception e) {
			if(con != null) {
				try {
					con.rollback();					
				} catch (SQLException e1) { }
			}
			throw e;
		} finally {
			if(con != null) {
				con.setAutoCommit(true);
				release(null, stmt, con);
			}
		}
	}


	/**
	 * corner 정보를 입력하는 procedure
	 * @throws Exception 
	 * 
	 */
	@Deprecated
	public String insertCornerInfoForProceduer(int master_id) throws Exception{
		Connection con = null;
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL DAS.PRC_SET_CORNERDESC(?)");
		try {
			con = DBService.getInstance().getConnection();

			stmt = con.prepareCall(buf.toString());
			stmt.setInt(1,master_id);

			stmt.execute();
			stmt.close();

			return "1";
		} catch (Exception e) {
			throw e;
		} finally {
			release(null, stmt, con);
		}
	}


	/**
	 * matadat_mst_tbl 정보를 입력하는 procedure
	 * @throws Exception 
	 * 
	 */
	public String insertMetaInfo(int master_id) throws Exception{
		Connection con = null;
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL DAS.PRC_SET_ANNOT_INFO(?)");
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMetaInfo######## con : " + con);
			stmt = con.prepareCall(buf.toString());
			stmt.setInt(1,master_id);
			//stmt.registerOutParameter(1, Types.INTEGER);


			stmt.execute();
			//System.out.println("stmt =========  " +stmt.getString(1));
			//	mediaId = stmt.getString(1);

			stmt.close();

			return "1";
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
	 * pgm_info_tbl 정보를 입력하는 procedure
	 * @throws Exception 
	 * 
	 */
	public String insertPgmProceduer(int pgm_id) throws Exception{
		Connection con = null;
		CallableStatement stmt = null;
		String mediaId ="";
		StringBuffer buf = new StringBuffer();
		buf.append(" CALL DAS.PRC_SET_PGM_INFO(?)");
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertPgmProceduer######## con : " + con);
			stmt = con.prepareCall(buf.toString());
			stmt.setInt(1,pgm_id);
			//stmt.registerOutParameter(1, Types.INTEGER);


			stmt.execute();
			//System.out.println("stmt =========  " +stmt.getString(1));
			//	mediaId = stmt.getString(1);

			stmt.close();

			return "1";
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


	// xml을 위한 StringBuffer에 넣어준다.
	// 자바에서 이렇게 하면 속도가 너무 느리지 않을까?
	private void AddToResultXMLBuffer(StringBuffer buf, String strTagname, String strPrefix, String strValue)
	{
		buf.append("\n");
		buf.append(strPrefix);		// identation을 맞춘다.
		buf.append("<");
		buf.append(strTagname);
		buf.append(">");
		buf.append(replace(strValue));
		buf.append("</");
		buf.append(strTagname);
		buf.append(">");
	}
	private String replace(String str){
		str = StringUtils.replace(str, "&", "&amp;");
		str = StringUtils.replace(str, "<", "&lt;");
		str = StringUtils.replace(str, ">", "&gt;");
		str = StringUtils.replace(str, "'", "&apos;");
		str = convertTag(str);
		return str;
	}


	/**
	 * 특수문자 처리를 char 단위로 처리를 하겠습니다 김동은 20090731
	 * resource 문제가 있습니다.
	 */
	public static String convertTag(String name) {

		if(name!=null){
			CharBuffer cb = CharBuffer.wrap(name);
			String xmlString = "";
			while ( cb.hasRemaining() ) {
				char tempChar = cb.get();
				if (tempChar < 0x20 && !((tempChar == 0x9) ||(tempChar == 0xA) || (tempChar == 0xD))){

					xmlString += " ";


					//  	  	}else if(tempChar ==0x1c){   // 화살표모양의 특수 문자  '->' 유사함.
					//  	    	xmlString += " ";
					//  	    }else if(tempChar ==0x1a){
					//  	    	xmlString += "";
					//  	    }else if((tempChar == 0x9) ||
					//                (tempChar == 0xA) ||
					//                (tempChar == 0xD) ||
					//                ((tempChar >= 0x20) && (tempChar <= 0xD7FF))){
					//                ((tempChar >= 0xE000) && (tempChar <= 0xFFFD)) ||
					//                ((tempChar >= 0x10000) && (tempChar <= 0x1FFFFF))){
					//  	    	xmlString +="";
				}else {
					xmlString +=tempChar;
				}
			}
			return xmlString;
		}
		return name;
	}





	/**
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertTCinfo(TcBeanDO tcBeanDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ,COCD ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertTCinfo######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(tcBeanDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			String storage =  tcBeanDO.getInput_hr();

			storage =storage.replace('\\', d);
			storage =storage.replaceAll(dasHandler.getProperty("MP2"),dasHandler.getProperty("WINMP2"));
			storage =storage.replaceAll(dasHandler.getProperty("ARCREQ"),dasHandler.getProperty("ARCREQ"));
			storage =storage.replaceAll(dasHandler.getProperty("NEARLINE"),dasHandler.getProperty("WINNEARLINE")); 



			stmt.setString(++index, storage);//INPUT_HR

			/*stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
			stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
			stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			 */
			//2012.06.19 수정후 패치 예정
			if(tcBeanDO.getCocd().equals("S")){
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}else {
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH

			}
			stmt.setString(++index, tcBeanDO.getCti_idForLow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,tcBeanDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, tcBeanDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index,tcBeanDO.getInput_hr_nm());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			if(tcBeanDO.getManual_yn().equals("N")){
				stmt.setString(++index,"004");//TC_TYPE 미디어넷 은 004로 추가 
			}else{
				stmt.setString(++index,"001");//TC_TYPE 재생성으로로직 변경 추가 
			}
			stmt.setString(++index,tcBeanDO.getCocd());//TC_TYPE 미디어넷 은 004로 추가 


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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}




	// 2012.4.20

	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다 외부 매체변환(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public long insertConInstInfoForLowForMedia(TcBeanDO tcBeanDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForLowForMedia######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, tcBeanDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "");//BIT_RT
			stmt.setString(++index, "");//FRM_PER_SEC
			stmt.setString(++index, "");//DRP_FRM_YN
			stmt.setInt(++index, 0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,cti_id+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return cti_id;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
			//	return 0;
		}

	}



	//2012.4.23 다스 확장 추가 개발 
	/**
	 * 로그인 seq를 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectLogInSeq() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select NEXTVAL FOR SEQ_LOGIN from sysibm.sysdummy1 ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectLogInSeq######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newMasterId = rs.getLong(1);



			return newMasterId;
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
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateBackupRequest(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateBackupRequest######## con : " + con);
			UseInfoDO pADO = externalDAO.selectCtiFromMasterForPDS(Long.parseLong(copyDO.getMaster_id()));
			String ct_ids[] = pADO.getCt_ids().split(",");
			String cti_ids[] = pADO.getCti_ids().split(",");
			for(int i = 0 ; i<ct_ids.length;i++){
				String pgm_cms_id = systemManageDAO.selectPdsPgmId(Long.parseLong(ct_ids[i]));
				pADO.setUser_id(copyDO.getUser_id());
				pADO.setDtl_type(copyDO.getDtl_type());
				pADO.setCt_id(Long.parseLong(ct_ids[i]));
				pADO.setCti_id(Long.parseLong(cti_ids[i]));
				externalDAO.ArchiveBackUpReq(pADO,pgm_cms_id);
				copyDO.setCt_id(Long.parseLong(ct_ids[i]));
				copyDO.setCti_id(Long.parseLong(cti_ids[i]));
				copyDO.setGubun("003");
				insertHistoryInfo(copyDO);
				//return "1";
			}
			return "1";
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
	 * Content_inst_tbl에 데이터를 집어 넣는다(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public long insertConInstInfoForH264(WmvH264DO item) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN   ");
		buf.append("\n 	,H264_YN  "); 
		buf.append("\n 	,H264_DT "); 
		buf.append("\n 	,H264_EQ ) "); 
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForH264######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			//long cti_id= selectCtiid();
			long cti_id = getCtiid(item.getCt_id());
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, item.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "");//BIT_RT
			stmt.setString(++index,"");//FRM_PER_SEC
			stmt.setString(++index, "");//DRP_FRM_YN
			stmt.setInt(++index,	0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,"");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN
			stmt.setString(++index, "N");//H264_YN
			stmt.setString(++index, "");//H264_DT
			stmt.setString(++index, "");//H264_EQ
			//pdsInfoDO.setCti_idForlow(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return cti_id;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
			//	return 0;
		}

	}





	/**
	 * 컨텐츠 id를 조회
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long getCtiid(long ct_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select cti_id from contents_inst_tbl where ct_id = ? and cti_fmt like '2%' ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();

			//logger.debug("######getCtiid######## con : " + con);
			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setLong(++index, ct_id);//CT_ID
			rs = stmt.executeQuery();
			long cti_id =0;
			while(rs.next())
			{
				cti_id =rs.getLong("cti_id");

			}


			return cti_id;
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
	 * wmv정보를 업데이트 한다
	 * @param     WmvH264DO                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateWmvInfo(WmvH264DO  item) throws Exception
	{

		StringBuffer buf = new StringBuffer();


		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateWmvInfo######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update contents_inst_tbl  set ");
			buf.append("\n 	h264_yn = ?  ");
			buf.append("\n 	,h264_eq= ?  ");
			buf.append("\n 	,h264_dt= ?  ");
			buf.append("\n 	,frm_per_sec= ?  ");
			buf.append("\n 	,bit_rt= ?  ");
			buf.append("\n 	,vd_vresol= ?  ");
			buf.append("\n 	,vd_hresol= ?  ");
			buf.append("\n 	,fl_path= ?  ");
			buf.append("\n 	,wrk_file_nm= ?  ");
			buf.append("\n 	,fl_sz= ?  ");
			buf.append("\n 	,audio_bdwt= ?  ");
			buf.append("\n 	,aud_samp_frq= ?  ");
			buf.append("\n where cti_id = ? and cti_fmt like '3%'  ");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, item.getJob_status());
			stmt.setString(++index, item.getEq_id());		
			stmt.setString(++index, dateTime);

			stmt.setString(++index, item.getFrm_per_sec());
			stmt.setString(++index, item.getBit_rt());
			stmt.setInt(++index, Integer.parseInt(item.getVd_vresol()));
			stmt.setInt(++index, Integer.parseInt(item.getVd_hresol()));

			String path = getFlPath(item.getCti_id());
			//String path =  item.getFl_path().replaceAll("U:", dasHandler.getProperty("WINMP4"));
			//path =  item.getFl_path().replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));
			stmt.setString(++index,path);
			stmt.setString(++index, item.getFl_nm());

			stmt.setLong(++index, item.getFl_sz());
			stmt.setString(++index, item.getAudio_bdwt());

			stmt.setString(++index, item.getAudio_samp_frq());

			stmt.setLong(++index, item.getCti_id());

			updateCount = stmt.executeUpdate();





			con.commit();
			return updateCount;
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
	 * 파일 경로를 업데이트 한다
	 * @param     WmvH264DO                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateFLpathInfo(TcBeanDO tcBeanDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();


		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateFLpathInfo######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update contents_inst_tbl  set ");
			buf.append("\n 	fl_path = ?  ");

			buf.append("\n where ct_id = ? and cti_fmt like '1%' ");
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			String storage =  tcBeanDO.getInput_hr();
			char d ='/';
			storage =storage.replace('\\', d);
			storage =storage.replaceAll(dasHandler.getProperty("MP2"),dasHandler.getProperty("WINMP2"));
			storage =storage.replaceAll(dasHandler.getProperty("ARCREQ"),dasHandler.getProperty("WINMP2"));
			storage =storage.replaceAll(dasHandler.getProperty("NEARLINE"),dasHandler.getProperty("WINNEARLINE"));
			logger.debug("input_hr   =" +storage);

			stmt.setString(++index, storage);
			stmt.setLong(++index,  tcBeanDO.getCt_id());		


			updateCount = stmt.executeUpdate();





			con.commit();
			return updateCount;
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
	 * 프로그램 복원을 요청한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRecorveryRequest(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRecorveryRequest######## con : " + con);
			UseInfoDO pADO = externalDAO.selectCtiFromMasterForPDS(Long.parseLong(copyDO.getMaster_id()));
			String ct_ids[] = pADO.getCt_ids().split(",");
			String cti_ids[] = pADO.getCti_ids().split(",");
			for(int i = 0 ; i<ct_ids.length;i++){
				String pgm_cms_id = systemManageDAO.selectPdsPgmId(Long.parseLong(ct_ids[i]));
				pADO.setUser_id(copyDO.getUser_id());
				pADO.setDtl_type(copyDO.getDtl_type());
				pADO.setCt_id(Long.parseLong(ct_ids[i]));
				pADO.setCti_id(Long.parseLong(cti_ids[i]));
				externalDAO.ArchiveRecorveryReq(pADO,pgm_cms_id);
				copyDO.setCt_id(Long.parseLong(ct_ids[i]));
				copyDO.setCti_id(Long.parseLong(cti_ids[i]));
				copyDO.setGubun("004");
				insertHistoryInfo(copyDO);
				//return "1";
			}
			return "1";
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
	 * 진행중인 job을 취소한다.(아카이브용)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String cancleJobRequest(MonitoringDO monitoringDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancleJobRequest######## con : " + con);
			MonitoringDO request = new MonitoringDO();
			request = externalDAO.getOBJNAME(monitoringDO.getKey());
			String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			externalDAO.updateCancleJobForArchive(monitoringDO.getKey());
			request.setDtl_type(dtl_type);
			request.setKey(monitoringDO.getKey());
			request.setGubun("005");
			externalDAO.cancleJOb(request);

			return "1";
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
	 * 진행중인 job을 취소한다.(다운)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String cancleJobRequestForDown(MonitoringDO monitoringDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancleJobRequestForDown######## con : " + con);
			MonitoringDO request = new MonitoringDO();
			request = externalDAO.getOBJNAMEForDown(monitoringDO.getKey());
			String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			externalDAO.updateCancleJobForDown(monitoringDO.getKey());
			request.setDtl_type(dtl_type);
			request.setKey(monitoringDO.getKey());
			request.setGubun("007");
			externalDAO.cancleJOb(request);

			return "1";
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
	 * 진행중인 job을 취소한다.(TM용)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String cancleJobRequestForTM(MonitoringDO monitoringDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######cancleJobRequestForTM######## con : " + con);
			MonitoringDO request = new MonitoringDO();
			request = externalDAO.getOBJNAME(monitoringDO.getKey());
			String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			request.setDtl_type(dtl_type);
			externalDAO.cancleJOb(request);

			return "1";
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
	 * 우선순위를 변경한다.(아카이브용)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String changePriorityForArchive(MonitoringDO monitoringDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######changePriorityForArchive######## con : " + con);
			MonitoringDO request = new MonitoringDO();
			request = externalDAO.getOBJNAME(monitoringDO.getKey());
			String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			request.setDtl_type(dtl_type);
			request.setPriority(monitoringDO.getPriority());
			request.setGubun("005");
			request.setKey(monitoringDO.getKey());
			externalDAO.changePriorityForArch(request);

			return "1";
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
	 * 삭제요청을 한다.(아카이브용)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String deleteRequest(ManualDeleteDO manualDeleteDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteRequest######## con : " + con);
			ManualDeleteDO request = new ManualDeleteDO();
			request = externalDAO.getOBJNAMEForDelete(manualDeleteDO.getKey());
			//String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			//request.setDtl_type(dtl_type);
			externalDAO.deleteJOb(request);

			return "1";
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
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertMedia_net(PdsArchiveDO pdsArchiveDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_MEDIA_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMedia_net######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(pdsArchiveDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			String storage =  pdsArchiveDO.getStorage_path().replace('\\', d);
			stmt.setString(++index, storage);//INPUT_HR

			stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());//INPUT_LR
			stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id());	//OUTPUT_LR_PATH
			stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pdsArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			stmt.setString(++index, pdsArchiveDO.getCti_idForlow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,pdsArchiveDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, pdsArchiveDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index, pdsArchiveDO.getClip_nm());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			stmt.setString(++index,"002");//TC_TYPE


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
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertMediaTCinfo(TcBeanDO tcBeanDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_MEDIA_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMediaTCinfo######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(tcBeanDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			String storage =  tcBeanDO.getInput_hr();

			storage =storage.replace('\\', d);
			storage =storage.replaceAll(dasHandler.getProperty("MP2"),dasHandler.getProperty("WINMP2"));
			storage =storage.replaceAll(dasHandler.getProperty("ARCREQ"),dasHandler.getProperty("WINMP2"));
			storage =storage.replaceAll(dasHandler.getProperty("NEARLINE"),dasHandler.getProperty("WINNEARLINE"));
			logger.debug("input_hr   =" +storage);


			stmt.setString(++index, storage);//INPUT_HR

			/*	stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
			stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
			stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			 */
			//2012.06.19 수정후 패치 예정
			if(tcBeanDO.getCocd().equals("S")){
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}else {
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+tcBeanDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH

			}
			stmt.setString(++index, tcBeanDO.getCti_idForLow()+".wmv");//OUTPUT_LR_NM
			stmt.setString(++index,tcBeanDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, tcBeanDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index,tcBeanDO.getInput_hr_nm());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			stmt.setString(++index,"004");//TC_TYPE 미디어넷 은 004로 추가 


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
	 * Content_inst_tbl에 데이터를 집어 넣는다 외부 매체변환(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public long insertConInstInfoForLowForH264(TcBeanDO tcBeanDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForLowForH264######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			//long cti_id= selectCtiid();
			stmt.setLong(++index, tcBeanDO.getCti_idForLow());//CTI_ID
			stmt.setLong(++index, tcBeanDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, "");//BIT_RT
			stmt.setString(++index, "");//FRM_PER_SEC
			stmt.setString(++index, "");//DRP_FRM_YN
			stmt.setInt(++index, 0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, "");//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index, "");//FL_PATH
			stmt.setString(++index,tcBeanDO.getCti_idForLow()+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+tcBeanDO.getCti_idForLow());//OUT_SYSTEM_ID
			stmt.setString(++index, "N");//WMV_YN

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
			//	return 0;
		}

	}




	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다  TC
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public long insertConInstInfoForH264InTC(TcBeanDO tcBeanDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertConInstInfoForH264InTC######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			//long cti_id= selectCtiid();
			stmt.setLong(++index, tcBeanDO.getCti_idForLow());//CTI_ID
			stmt.setLong(++index, tcBeanDO.getCt_id());//CT_ID
			stmt.setString(++index, "301");//CTI_FMT
			stmt.setString(++index,"");//ARCH_STE_YN
			stmt.setString(++index, "");//ME_CD
			stmt.setString(++index, tcBeanDO.getH264_BIT_RT());//BIT_RT
			stmt.setString(++index, tcBeanDO.getH264_FRM_PER_SEC());//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			String[] sol = tcBeanDO.getH264_resol().split("X");
			for(int i =0; i<sol.length;i++){
				stmt.setString(++index, sol[i] );
			}
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, "");//RECORD_TYPE_CD
			stmt.setString(++index, "");//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, tcBeanDO.getH264_AUDIO_SAMP_FRQ());//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			String fl_path =tcBeanDO.getOut_put_lr_path();
			fl_path = fl_path.replaceAll(dasHandler.getProperty("NET_MP4"), dasHandler.getProperty("WINNET_MP4"));
			fl_path = fl_path.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));
			stmt.setString(++index, fl_path);//FL_PATH
			stmt.setString(++index,tcBeanDO.getCti_idForLow()+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, Long.parseLong(tcBeanDO.getH264_FL_SZ()));//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,"");//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+tcBeanDO.getCti_idForLow());//OUT_SYSTEM_ID
			stmt.setString(++index, "Y");//WMV_YN

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
			//	return 0;
		}

	}



	/**
	 * IFCMS 아카이브 요청건에 대해서 메타 입력
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertMetadatTblForIfCms(IfCmsArchiveDO pgmDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into METADAT_MST_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,PGM_CD    ");
		buf.append("\n ,EPIS_NO    ");
		buf.append("\n ,TITLE    ");
		buf.append("\n ,CTGR_L_CD    ");
		buf.append("\n ,CTGR_M_CD    ");
		buf.append("\n ,CTGR_S_CD    ");
		buf.append("\n ,BRD_DD    ");
		buf.append("\n ,FINAL_BRD_YN   ");
		buf.append("\n ,SNPS    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,BRD_BGN_HMS     ");
		buf.append("\n ,BRD_END_HMS    ");
		buf.append("\n ,BRD_LENG    ");
		buf.append("\n ,PGM_RATE    ");
		buf.append("\n ,DRT_NM    ");
		buf.append("\n ,PRODUCER_NM    ");
		buf.append("\n ,WRITER_NM    ");
		buf.append("\n ,PRDT_IN_OUTS_CD    ");
		buf.append("\n ,PRDT_DEPT_CD    ");
		buf.append("\n ,PRDT_DEPT_NM    ");
		buf.append("\n ,ORG_PRDR_NM   ");
		buf.append("\n ,MC_NM    ");
		buf.append("\n ,CAST_NM    ");
		buf.append("\n ,CMR_DRT_NM   ");
		buf.append("\n ,FM_DT    ");
		buf.append("\n ,CMR_PLACE    ");
		buf.append("\n ,SPC_INFO    ");
		buf.append("\n ,REQ_CD    ");
		buf.append("\n ,SEC_ARCH_NM    ");
		buf.append("\n ,SEC_ARCH_ID    ");
		buf.append("\n ,GATH_CO_CD    ");
		buf.append("\n ,GATH_CLF_CD    ");
		buf.append("\n ,ARCH_REG_DD    ");
		buf.append("\n ,ARRG_END_DT   ");
		buf.append("\n ,WORK_PRIO_CD    ");
		buf.append("\n ,RSV_PRD_CD    ");
		buf.append("\n ,CPRTR_NM    ");		
		buf.append("\n ,CPRT_TYPE    ");
		buf.append("\n ,CPRT_TYPE_DSC    ");
		buf.append("\n ,VIEW_GR_CD   ");
		buf.append("\n ,DLBR_CD    ");
		buf.append("\n ,AWARD_HSTR    ");
		buf.append("\n ,RPIMG_KFRM_SEQ     ");
		buf.append("\n ,TAPE_ID    ");  
		buf.append("\n ,TAPE_ITEM_ID    ");
		buf.append("\n ,TAPE_MEDIA_CLF_CD    ");
		buf.append("\n ,RSV_PRD_END_DD    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,MOD_DT   ");
		buf.append("\n ,GATH_DEPT_CD    ");
		buf.append("\n ,MCUID    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,DATA_STAT_CD    ");
		buf.append("\n ,ING_REG_DD    ");
		buf.append("\n ,COPY_KEEP    ");
		buf.append("\n ,CLEAN_KEEP    ");
		buf.append("\n ,MUSIC_INFO    ");
		buf.append("\n ,RST_CONT    ");
		buf.append("\n ,RERUN    ");
		buf.append("\n ,ACCEPTOR_ID    ");
		buf.append("\n ,SUB_TTL    ");
		buf.append("\n ,ARRANGE_NM   ");
		buf.append("\n ,LOCK_STAT_CD    ");
		buf.append("\n ,ERROR_STAT_CD    ");
		buf.append("\n ,ARCH_ROUTE    ");
		buf.append("\n ,RIST_CLF_CD     ");
		buf.append("\n ,MANUAL_YN    ");
		buf.append("\n ,PDS_CMS_PGM_ID    ");

		buf.append("\n ,COCD    ");
		buf.append("\n ,CHENNEL_CD   ");
		buf.append("\n ,ARTIST   ");
		buf.append("\n ,GROUP_ID   ");
		buf.append("\n ,COUNTRY_CD  ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,  ");
		buf.append("\n ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pgmDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pgmDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			index = 0;	
			long masterid= selectMasterid();

			//날짜계산
			String getRsv_prd_end_dd = "";

			String distime = CalendarUtil.getDateTime("yyyyMMdd");

			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

			String dateString = distime;


			Date date = formatter.parse(dateString);	

			Calendar calendar = Calendar.getInstance();		     
			if(pgmDO.getRetention_period().equals("000")){//영구
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +9999);
				getRsv_prd_end_dd="99991231";
			} else if(pgmDO.getRetention_period().equals("003")){//3일
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, +3);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());

			}else if(pgmDO.getRetention_period().equals("005")){//5일
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, +5);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
			}else if(pgmDO.getRetention_period().equals("030")){//한달
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, +30);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());    	  
			}else if(pgmDO.getRetention_period().equals("060")){//5년
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +5);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());

			}else if(pgmDO.getRetention_period().equals("120")){//10년
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +10);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());			    	  
			}else if(pgmDO.getRetention_period().equals("240")){//20년
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +20);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
			}else if(pgmDO.getRetention_period().equals("360")){//30년
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +30);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
			}else if(pgmDO.getRetention_period().equals("001")){//1년
				calendar.setTime(date);
				calendar.add(Calendar.YEAR, +1);
				getRsv_prd_end_dd=formatter.format(calendar.getTime());		    	  
			}else{
				getRsv_prd_end_dd="99991231";
			} 

			stmt.setLong(++index,masterid);//MASTER_ID
			stmt.setInt(++index, 0);//PGM_ID
			stmt.setString(++index,"");//PGM_CD
			//stmt.setInt(++index, Integer.parseInt(pgmDO.getEpisode_no()));//EPIS_NO
			//String tmp_Episode_no = pgmDO.getEpisode_no();
			//stmt.setInt(++index, Integer.parseInt(StringUtils.defaultIfEmpty(pgmDO.getEpisode_no(), "0")));//EPIS_NO
			stmt.setInt(++index, Integer.parseInt(StringUtils.defaultIfEmpty(pgmDO.getEpis_no(), "0")));//EPIS_NO
			//Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue2, "0"))
			stmt.setString(++index,pgmDO.getTitle());//TITLE
			if(pgmDO.getCtgr_l_cd().equals("")){
				stmt.setString(++index, "100");//CTGR_L_CD
			}else{
				stmt.setString(++index, pgmDO.getCtgr_l_cd());//CTGR_L_CD	
			}
			stmt.setString(++index, pgmDO.getCtgr_m_cd());//CTGR_M_CD
			stmt.setString(++index,pgmDO.getCtgr_s_cd());//CTGR_S_CD
			if(StringUtils.isNotEmpty(pgmDO.getBrd_dd())){
				stmt.setString(++index, pgmDO.getBrd_dd());//BRD_DD
			}else{
				stmt.setString(++index, "19000101");//BRD_DD
			}
			stmt.setString(++index, "");//FINAL_BRD_YN
			stmt.setString(++index, "");//SNPS
			//20121205 최효정과장님 구두 요청으로 인해 변경 키워드의 값을 특이사항으로 넣는다, 키워드컬럼은 공백으로 한다.
			//20121221 최효정과장 요청으로 keyword -> das의 keyword로 매핑
			stmt.setString(++index,  pgmDO.getKeyword());//KEY_WORDS
			stmt.setString(++index, "");//BRD_BGN_HMS
			stmt.setString(++index, "");//BRD_END_HMS
			if(StringUtils.isNotEmpty(pgmDO.getBrd_leng())){
				stmt.setString(++index,pgmDO.getBrd_leng());//BRD_LENG
			}else{
				stmt.setString(++index,"00:00:00:00");//BRD_LENG
			}
			//stmt.setString(++index,"00:00:11");//BRD_LENG
			stmt.setString(++index, "");//PGM_RATE
			stmt.setString(++index, pgmDO.getCreator_sub());//DRT_NM
			stmt.setString(++index, pgmDO.getCreator());//PRODUCER_NM
			stmt.setString(++index, pgmDO.getWriter_name());//WRITER_NM
			stmt.setString(++index,pgmDO.getProduction_type());//PRDT_IN_OUTS_CD
			stmt.setString(++index, "");//PRDT_DEPT_CD
			stmt.setString(++index, "");//prdt_dept_nm
			stmt.setString(++index, pgmDO.getPublisher_external());//ORG_PRDR_NM
			stmt.setString(++index, "");//MC_NM
			stmt.setString(++index, "");//CAST_NM
			stmt.setString(++index,pgmDO.getDirector_shooting());////CMR_DRT_NM
			if(StringUtils.isNotEmpty(pgmDO.getFm_dt())){
				stmt.setString(++index, pgmDO.getFm_dt());//FM_DT
			}else{
				stmt.setString(++index, "19000101");//FM_DT
			}
			stmt.setString(++index, pgmDO.getLocation_shooting());//CMR_PLACE
			//20121205 최효정과장님 구두 요청으로 인해 변경 키워드의 값을 특이사항으로 넣는다
			//20121221 최효정 메일 요청으로 keyword -> das의 keyword로 매핑
			stmt.setString(++index, pgmDO.getSpecial_info());//SPC_INFO
			stmt.setString(++index, "");//REQ_CD
			stmt.setString(++index,"");//SEC_ARCH_NM
			stmt.setString(++index, "");//SEC_ARCH_ID
			stmt.setString(++index, "");//GATH_CO_CD
			stmt.setString(++index,"");//GATH_CLF_CD
			stmt.setString(++index, "");//ARCH_REG_DD
			stmt.setString(++index, "");//ARRG_END_DT
			stmt.setString(++index, "");//WORK_PRIO_CD
			stmt.setString(++index, pgmDO.getRetention_period());//RSV_PRD_CD
			stmt.setString(++index, pgmDO.getCopyright_owner());//CPRTR_NM	
			stmt.setString(++index, pgmDO.getCprt_type());//CPRT_TYPE


			stmt.setString(++index, pgmDO.getCopyright_desc());//CPRT_TYPE_DSC
			stmt.setString(++index, pgmDO.getView_gr_cd());//VIEW_GR_CD
			stmt.setString(++index, "");//DLBR_CD
			stmt.setString(++index, "");//AWARD_HSTR
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, "");//TAPE_ID
			stmt.setString(++index, "");//TAPE_ITEM_ID
			stmt.setString(++index,"");//TAPE_MEDIA_CLF_CD
			stmt.setString(++index, getRsv_prd_end_dd);//RSV_PRD_END_DD
			stmt.setString(++index, "");//del_dd
			stmt.setString(++index, "Y");//USE_YN
			stmt.setString(++index, pgmDO.getRequest_id());//REGRID
			stmt.setString(++index, dateTime);//날짜
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");//날짜
			stmt.setString(++index, "");//GATH_DEPT_CD
			stmt.setString(++index, "");//MCUID
			stmt.setInt(++index, 0);//RPIMG_CT_ID
			stmt.setString(++index, "");//DATA_STAT_CD
			stmt.setString(++index, dateTime);//ING_REG_DD
			stmt.setString(++index, "");//COPY_KEEP
			stmt.setString(++index, "");//CLEAN_KEEP
			stmt.setString(++index, "");//MUSIC_INFO
			stmt.setString(++index, "");//RST_CONT
			stmt.setString(++index, "");//RERUN
			stmt.setString(++index, "");//ACCEPTOR_ID
			stmt.setString(++index, pgmDO.getSub_ttl());//SUB_TTL
			stmt.setString(++index, "");//ARRANGE_NM
			stmt.setString(++index, "N");//LOCK_STAT_CD
			stmt.setString(++index, "000");//ERROR_STAT_CD
			String arch_route = "";
			if(pgmDO.getCtgr_l_cd().equals("200")){
				stmt.setString(++index, "O");//ARC;H_ROUTE
				arch_route="O";
			}else{
				stmt.setString(++index, "P");//ARCH_ROUTE
				arch_route="P";
			}
			stmt.setString(++index, pgmDO.getLimited_use());//RIST_CLF_CD
			boolean result;

			if(!pgmDO.getContents_class().equals("")||!pgmDO.getPublisher().equals("")){
				result = getAutoArchvieList(pgmDO.getContents_class(),pgmDO.getPublisher(),pgmDO.getChannel_cd(),arch_route);
			}else{
				result = getAutoArchvieList("001","S","A","P");
			}
			if(result){
				stmt.setString(++index, "N");//MANUAL_YN
			}else{
				stmt.setString(++index, "Y");//MANUAL_YN	
			}
			//stmt.setString(++index, "N");//MANUAL_YN
			stmt.setString(++index, pgmDO.getProgram_id());//pds_cms_id
			if(!pgmDO.getPublisher().equals("")){
				stmt.setString(++index, pgmDO.getPublisher());//COCD
			}else{
				stmt.setString(++index, "S");//COCD	
			}
			if(! pgmDO.getChannel_cd().equals("")){
				stmt.setString(++index, pgmDO.getChannel_cd());//CHENNEL_CD
			}else{
				stmt.setString(++index, "A");//CHENNEL_CD	
			}
			stmt.setString(++index, pgmDO.getArtist());//ARTIST
			stmt.setLong(++index, pgmDO.getGroup_id());//GROUP_ID
			stmt.setString(++index, pgmDO.getCountry());//COUNTRY_CD

			pgmDO.setMaster_id(masterid);

			updateCount = stmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted master_id]" + masterid);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pgmDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pgmDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pgmDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pgmDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}


	/**
	 * contents_tbl에 자료를 집어 넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsInfoForIfCms (IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO CONTENTS_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,CT_TYP    ");
		buf.append("\n ,CT_CLA    ");
		buf.append("\n ,CT_NM    ");
		buf.append("\n ,CONT    ");
		buf.append("\n ,KEY_WORDS    ");
		buf.append("\n ,CT_OWN_DEPT_CD    ");
		buf.append("\n ,CT_OWN_DEPT_NM    ");
		buf.append("\n ,DATA_STAT_CD   ");
		buf.append("\n ,CT_LENG    ");
		buf.append("\n ,VD_QLTY    ");
		buf.append("\n ,ASP_RTO_CD    ");
		buf.append("\n ,EDTRID    ");
		buf.append("\n ,KFRM_PATH    ");
		buf.append("\n ,KFRM_PX_CD    ");
		buf.append("\n ,TOT_KFRM_NUMS    ");
		buf.append("\n ,USE_YN    ");
		buf.append("\n ,DEL_DD    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,MCU_SEQ    ");
		buf.append("\n ,CMS_CT_ID    ");
		buf.append("\n ,COPY_OBJECT_YN    ");
		buf.append("\n ,USE_CONT    ");
		buf.append("\n ,ARCHIVE_YN    ");
		buf.append("\n ,MEDIA_ID    ");
		buf.append("\n ,DEL_YN    )    ");



		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ");
		buf.append("\n ?, ?, ?, ?, ? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long ctid= selectCtid();
			stmt.setLong(++index, ctid);//CT_ID
			stmt.setInt(++index, 1);//CT_SEQ
			stmt.setString(++index, pdsInfoDO.getContents_type());//CT_TYP
			if(!pdsInfoDO.getContents_class().equals("")){
				stmt.setString(++index, pdsInfoDO.getContents_class());//CT_CLA
			}else{
				stmt.setString(++index, "001");//CT_CLA	
			}
			stmt.setString(++index, pdsInfoDO.getContents_name());			//CT_NM
			stmt.setString(++index, pdsInfoDO.getContents_desc());//CONT
			stmt.setString(++index, "");//KEY_WORDS
			stmt.setString(++index, "");//CT_OWN_DEPT_CD
			stmt.setString(++index, "");//CT_OWN_DEPT_NM
			stmt.setString(++index, "");	//		DATA_STAT_CD
			stmt.setString(++index, pdsInfoDO.getBrd_leng());//CT_LENG
			stmt.setString(++index, pdsInfoDO.getResolution());//VD_QLTY
			stmt.setString(++index, pdsInfoDO.getAspectratio());//ASP_RTO_CD
			stmt.setString(++index, "");//EDTRID
			stmt.setString(++index,"");		//	KFRM_PATH
			stmt.setString(++index, "");//KFRM_PX_CD
			stmt.setInt(++index,0);//TOT_KFRM_NUMS
			stmt.setString(++index,"Y");//USE_YN
			stmt.setString(++index, "");//DEL_DD
			stmt.setString(++index, dateTime);	//REG_DT
			stmt.setString(++index, pdsInfoDO.getRequest_id());//REGRID
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setInt(++index, 0);	//DURATION
			stmt.setInt(++index, 0);//MCU_SEQ
			stmt.setInt(++index, 0);	//CMS_CT_ID
			stmt.setString(++index, "N");//COPY_OBJECT_YN
			stmt.setInt(++index,0);//USE_CONT
			stmt.setString(++index, "Y");//ARCHIVE_YN
			stmt.setString(++index, CommonUtl.checkNull(pdsInfoDO.getMedia_id()));	//MEDIA_ID
			stmt.setString(++index, "N");	//DEL_YN
			pdsInfoDO.setCt_id(ctid);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted ct_id]" + ctid);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(con != null) {
					if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
				}
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}

	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다(mxf)
	 * @param PdsArchivIfCmsArchiveDOeDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForHigh(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID   ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "105");//CTI_FMT
			stmt.setString(++index,"N");//ARCH_STE_YN
			stmt.setString(++index, "002");//ME_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getBit_rt()));//BIT_RT
			stmt.setString(++index, pdsInfoDO.getFrm_per_sec());//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_hresol().replaceAll("[^0-9]", "")));  //VD_HRESOL
			stmt.setInt(++index, Integer.parseInt(pdsInfoDO.getVd_vresol().replaceAll("[^0-9]", "")));	//VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getAudio_type());//RECORD_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getAudio_yn());//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAud_samp_frq()));//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			String storage =  pdsInfoDO.getStorage_path();

			storage = storage.replaceAll(dasHandler.getProperty("NEARLINE"), dasHandler.getProperty("WINNEARLINE"));

			stmt.setString(++index, storage);//FL_PATH
			stmt.setString(++index,cti_id+".MXF");//WRK_FILE_NM
			stmt.setString(++index, pdsInfoDO.getFile_name());////ORG_FILE_NM
			stmt.setLong(++index, Long.parseLong(pdsInfoDO.getFile_size()));//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,pdsInfoDO.getRequest_id());//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "N");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			pdsInfoDO.setCti_idForHigh(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted high cti_id]" + cti_id);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(con != null) {
					if(pdsInfoDO.getConn() == null) {
						con.setAutoCommit(true);
						release(null, stmt, con);
					} else
						release(null, stmt, null);
				}
			} catch (SQLException e) {}
		}

	}

	/**
	 * Content_inst_tbl에 데이터를 집어 넣는다(wmv)
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertConInstInfoForLow(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_INST_TBL (    ");
		buf.append("\n CTI_ID    ");
		buf.append("\n ,CT_ID    ");
		buf.append("\n ,CTI_FMT    ");
		buf.append("\n ,ARCH_STE_YN    ");
		buf.append("\n ,ME_CD    ");
		buf.append("\n ,BIT_RT    ");
		buf.append("\n ,FRM_PER_SEC    ");
		buf.append("\n ,DRP_FRM_YN    ");
		buf.append("\n ,VD_HRESOL    ");
		buf.append("\n ,VD_VRESOL   ");
		buf.append("\n ,COLOR_CD    ");
		buf.append("\n ,RECORD_TYPE_CD    ");
		buf.append("\n ,AUDIO_YN    ");
		buf.append("\n ,AUD_TYPE_CD    ");
		buf.append("\n ,AUD_LAN_CD    ");
		buf.append("\n ,AUD_SAMP_FRQ    ");
		buf.append("\n ,AUDIO_BDWT   ");
		buf.append("\n ,NOI_RDUC_TYP_CD    ");
		buf.append("\n ,INGEST_EQ_ID    ");
		buf.append("\n ,ENC_QLTY_CD    ");
		buf.append("\n ,ENC_QLTY_DESC    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,WRK_FILE_NM    ");
		buf.append("\n ,ORG_FILE_NM   ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DTL_YN    ");
		buf.append("\n ,FILE_YN    ");
		buf.append("\n ,OUT_SYSTEM_ID    ");
		buf.append("\n ,WMV_YN  ) ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,  ");
		buf.append("\n ?, ?, ?, ?, ?, ?, 'N' ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long cti_id= selectCtiid();
			stmt.setLong(++index, cti_id);//CTI_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());//CT_ID
			stmt.setString(++index, "305");//CTI_FMT
			stmt.setString(++index,"N");//ARCH_STE_YN
			stmt.setString(++index, "002");//ME_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getBit_rt()));//BIT_RT
			stmt.setString(++index, pdsInfoDO.getFrm_per_sec());//FRM_PER_SEC
			stmt.setString(++index, "Y");//DRP_FRM_YN
			stmt.setInt(++index, 0);//VD_HRESOL
			stmt.setInt(++index, 0);	//	VD_VRESOL	
			stmt.setString(++index, "");//COLOR_CD
			stmt.setString(++index, pdsInfoDO.getAudio_type());//RECORD_TYPE_CD
			stmt.setString(++index, pdsInfoDO.getAudio_yn());//AUDIO_YN
			stmt.setString(++index,"");////AUD_TYPE_CD
			stmt.setString(++index, "");//AUD_LAN_CD
			stmt.setString(++index, String.valueOf(pdsInfoDO.getAud_samp_frq()));//AUD_SAMP_FRQ
			stmt.setString(++index, "");//AUDIO_BDWT
			stmt.setString(++index, "");//NOI_RDUC_TYP_CD
			stmt.setInt(++index , 0);//INGEST_EQ_ID
			stmt.setString(++index, "");	//		ENC_QLTY_CD
			stmt.setString(++index, "");//ENC_QLTY_DESC
			stmt.setString(++index,  "");//FL_PATH
			stmt.setString(++index,cti_id+".mp4");//WRK_FILE_NM
			stmt.setString(++index, "");////ORG_FILE_NM
			stmt.setLong(++index, 0);//FL_SZ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index,pdsInfoDO.getRequest_id());//REGRID
			stmt.setString(++index, "");//MOD_DT
			stmt.setString(++index, "");//MODRID
			stmt.setString(++index, "N");	//DTL_YN	
			stmt.setString(++index,"");	//FILE_YN
			stmt.setString(++index, "DAS"+cti_id);//OUT_SYSTEM_ID
			pdsInfoDO.setCti_idForLow(cti_id);

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted low cti_id]" + cti_id);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(con != null) {
					if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);

					if(pdsInfoDO.getConn() == null)
						release(null, stmt, con);
					else
						release(null, stmt, null);
				}
			} catch (SQLException e) {}
		}

	}



	/**
	 * conner tbl에 집어넣는다
	 * @param IfCmsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertCornerInfo(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CORNER_TBL (    ");
		buf.append("\n CN_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_NM    ");
		buf.append("\n ,CN_TYPE_CD    ");
		buf.append("\n ,SOM    ");
		buf.append("\n ,EOM    ");
		buf.append("\n ,CN_INFO    ");
		buf.append("\n ,RPIMG_KFRM_SEQ    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,MODRID    ");
		buf.append("\n ,DURATION    ");
		buf.append("\n ,RPIMG_CT_ID    ");
		buf.append("\n ,S_FRAME   ) ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,? ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	
			long cnid= selectCnid();
			stmt.setLong(++index,cnid);//CN_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());//MASTER_ID
			stmt.setString(++index, "");//CN_NM
			stmt.setString(++index, "003");//CN_TYPE_CD
			stmt.setString(++index, "00:00:00:00");//SOM
			stmt.setString(++index, pdsInfoDO.getBrd_leng());//EOM
			//20121221 최효정 과장 요청 contents_desc -> das 코너의 내용으로 매핑
			stmt.setString(++index, pdsInfoDO.getContents_desc());//CN_INFO
			stmt.setInt(++index, 0);//RPIMG_KFRM_SEQ
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, pdsInfoDO.getRequest_id());//REGRID
			stmt.setString(++index,"");//MOD_DT
			stmt.setString(++index,  "");//MODRID
			stmt.setLong(++index, 0);			//DURATION
			stmt.setLong(++index, 0);//RPIMG_CT_ID
			stmt.setLong(++index,0);//S_FRAME

			pdsInfoDO.setCn_id(cnid);
			updateCount = stmt.executeUpdate();
			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted corner id]" + cnid);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}


	/**
	 * CONTENTS_MAPP_TBL 에 집어넣는다
	 * @param IfCmsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertContentsMappInfo(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO DAS.CONTENTS_MAPP_TBL (    ");
		buf.append("\n CT_ID    ");
		buf.append("\n ,PGM_ID    ");
		buf.append("\n ,MASTER_ID    ");
		buf.append("\n ,CN_ID    ");
		buf.append("\n ,CT_SEQ    ");
		buf.append("\n ,S_DURATION    ");
		buf.append("\n ,E_DURATION    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");
		buf.append("\n ,MODRID   ");
		buf.append("\n ,MOD_DT    ");
		buf.append("\n ,CN_SEQ    ");
		buf.append("\n ,DEL_DD )   ");
		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;	

			stmt.setLong(++index,pdsInfoDO.getCt_id());
			stmt.setLong(++index, 0);
			stmt.setLong(++index, pdsInfoDO.getMaster_id());
			stmt.setLong(++index, pdsInfoDO.getCn_id());
			stmt.setInt(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setLong(++index, 0);
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, "");			
			stmt.setString(++index,dateTime);	
			stmt.setInt(++index, 1);
			stmt.setString(++index,"");	

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted mapp]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(con != null) {
					if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);

					if(pdsInfoDO.getConn() == null)
						release(null, stmt, con);
					else
						release(null, stmt, null);
				}
			} catch (SQLException e) {}
		}

	}

	/**
	 * annot_info_tbl 에 집어넣는다
	 * @param IfCmsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertAnnotInfo(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into das.Annot_Info_tbl (   ");
		buf.append("\n   ANNOT_ID   ");
		buf.append("\n  ,CN_ID   ");
		buf.append("\n  ,CT_ID  ");
		buf.append("\n 	,MASTER_ID    ");
		buf.append("\n  ,ANNOT_CLF_CD    ");
		buf.append("\n  ,ANNOT_CLF_CONT   ");
		buf.append("\n  ,SOM   ");
		buf.append("\n  ,EOM  ");
		buf.append("\n 	,CONT    ");
		buf.append("\n  ,REGRID    ");
		buf.append("\n  , REG_DT   ");
		buf.append("\n  ,MODRID   ");
		buf.append("\n  ,MOD_DT  ");
		buf.append("\n  ,DURATION   ");
		buf.append("\n  ,S_FRAME   ");
		buf.append("\n  ,GUBUN  ");
		buf.append("\n  ,ENTIRE_YN  ");


		buf.append("\n ) VALUES (   ");

		buf.append("\n  ?, ?, ?  ,?, ?, ?, ?, ? ,?, ?, ?, ?, ? ,?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());
			CommonUtl commonUtl = new CommonUtl();
			int updateCount = 0;
			int index=0;


			index = 0;	
			long annot_id = selectAnootid();
			stmt.setLong(++index, annot_id);//ANNOT_ID
			stmt.setLong(++index, pdsInfoDO.getCn_id());			//CN_ID
			stmt.setLong(++index, pdsInfoDO.getCt_id());	//CT_ID
			stmt.setLong(++index, pdsInfoDO.getMaster_id());	//MASTER_ID
			stmt.setString(++index, pdsInfoDO.getLimited_use());	//ANNOT_CLF_CD
			stmt.setString(++index, pdsInfoDO.getLimited_use_cont());//ANNOT_CLF_CONT
			stmt.setString(++index, "00:00:00:00");			//SOM
			if(StringUtils.isNotEmpty(pdsInfoDO.getBrd_leng())){
				stmt.setString(++index, pdsInfoDO.getBrd_leng());	//EOM
			}else{
				stmt.setString(++index, "00:00:00:00");	//EOM
			}
			stmt.setString(++index, "");	//CONT
			stmt.setString(++index, "D080009");	//REGRID
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);	//REG_DT			
			stmt.setString(++index, "");			//MODRID
			stmt.setString(++index, "");	//MOD_DT
			if(StringUtils.isNotEmpty(pdsInfoDO.getBrd_leng())){
				long duration = commonUtl.changeTime(pdsInfoDO.getBrd_leng());
				stmt.setLong(++index, duration);//DURATION
			}else{
				stmt.setLong(++index, 0);//DURATION
			}
			stmt.setLong(++index, 0);			//S_FRAME
			stmt.setString(++index, "L");	//GUBUN
			stmt.setString(++index, "Y");	//ENTIRE_YN

			updateCount = stmt.executeUpdate();
			if (logger.isDebugEnabled())  {
				logger.debug("[Inserted annot]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}






	/**
	 * 그룹id 중복여부 판단
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereGroupId(long group_id) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  das.metadat_mst_Tbl where group_id = '"+ group_id + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereGroupId######## con : " + con);

			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return false;
			} else {
				return true;
			}
		}  catch (Exception e) {
			logger.error("isThereGroupId", e);

			throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 마스터id를 조회
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long getmaster_id(long group_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select master_id from METADAT_MST_TBL where GROUP_ID = ?   ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getmaster_id######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setLong(++index, group_id);
			rs = stmt.executeQuery();
			long master_id =0;
			while(rs.next())
			{
				master_id =rs.getLong("master_id");

			}


			return master_id;
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
	 * preview_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPreveiw_Info(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PREVIEW_TBL (   ");
		buf.append("\n   MASTER_ID   ");
		buf.append("\n  ,PREVIEW_ID   ");
		buf.append("\n  ,PREVIEW_SUBJ  ");
		buf.append("\n 	,PREVIEW_CONT    ");
		buf.append("\n  ,REG_DT    ");
		buf.append("\n ) VALUES (   ");
		buf.append("\n ?, ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long preview_id = selectPreviewid();
			stmt.setLong(++index, pdsInfoDO.getMaster_id());
			stmt.setLong(++index, preview_id);
			stmt.setString(++index, "");			
			stmt.setString(++index, "");	
			stmt.setString(++index, dateTime);	
			pdsInfoDO.setPreview_id(preview_id);


			updateCount = stmt.executeUpdate();

			insertPreveiw_attach_Info(pdsInfoDO);

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			try {
				if(pdsInfoDO.getConn() == null) con.setAutoCommit(true);
			} catch (SQLException e) {}

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, con);
		}

	}



	/**
	 * preview_attach_tbl 에 집어넣는다
	 * @param PdsArchiveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPreveiw_attach_Info(IfCmsArchiveDO pdsInfoDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into PREVIEW_ATTACH_TBL (   ");
		buf.append("\n MASTER_ID    ");
		buf.append("\n ,PREVIEW_ATTATCH_ID    ");
		buf.append("\n ,FL_NM    ");
		buf.append("\n ,FL_SZ    ");
		buf.append("\n ,FL_PATH    ");
		buf.append("\n ,ORG_FILE_NM    ");
		buf.append("\n ,REG_DT    ");
		buf.append("\n ,REGRID    ");


		buf.append("\n ) VALUES ( ");

		buf.append("\n ?, ?, ?, ?, ?, ?, ?, ?");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			if(pdsInfoDO.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = pdsInfoDO.getConn();

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			long Preveiw_attach_Info =selectPreview_attach_id();
			stmt.setLong(++index,pdsInfoDO.getMaster_id());		
			stmt.setLong(++index, Preveiw_attach_Info);				
			stmt.setString(++index, pdsInfoDO.getPreview_kr());
			stmt.setLong(++index, Long.parseLong(pdsInfoDO.getPre_size()));
			String storage =   pdsInfoDO.getPreview_path();

			storage = storage.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));
			storage = storage.replaceAll(dasHandler.getProperty("NET_MP4"), dasHandler.getProperty("WINNET_MP4"));

			stmt.setString(++index, storage);			
			stmt.setString(++index, pdsInfoDO.getPreview_kr());	
			stmt.setString(++index, dateTime);
			stmt.setString(++index,pdsInfoDO.getRequest_id());


			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0) {
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			if(pdsInfoDO.getConn() == null)
				con.commit();

			return updateCount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(pdsInfoDO.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {

			if(pdsInfoDO.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}




	/**
	 * 첨부파일 메타데이터를 저장한다
	 * @param FileInfoDO 저장할 첨부파일 정보
	 * @throws Exception 
	 * 
	 */

	public int insertAttachFile(IfCmsArchiveDO _do) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ATTCH_TBL( ");
		buf.append("\n 	MOTHR_DATA_ID,  ");
		buf.append("\n 	ATTC_FILE_TYPE_CD,  ");
		buf.append("\n 	ATTC_CLF_CD,  ");
		buf.append("\n 	FL_NM,  ");
		buf.append("\n 	FL_SZ,  ");
		buf.append("\n 	FL_PATH,  ");
		buf.append("\n 	ORG_FILE_NM,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	CAPTION_TYPE,  ");

		buf.append("\n 	SEQ ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?,?,  ?) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{

			if(_do.getConn() == null) {
				con = DBService.getInstance().getConnection();
				con.setAutoCommit(false);
			} else
				con = _do.getConn();

			stmt = con.prepareStatement(buf.toString());

			//현재 시간을 받아온다.
			String toDateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;

			String storage =   _do.getCaption_path();

			storage = storage.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));
			storage = storage.replaceAll(dasHandler.getProperty("NET_MP4"), dasHandler.getProperty("WINNET_MP4"));


			if(_do.getType().equals("010")){
				int maxSeq = selectFileInfoMaxSeq(_do.getMaster_id()) + 1;
				stmt.setLong(++index, _do.getMaster_id());
				stmt.setString(++index, _do.getType());
				stmt.setString(++index, CodeConstants.AttachFlag.ATTACH);
				stmt.setString(++index,_do.getCaption_kr());
				stmt.setLong(++index, Long.parseLong(_do.getKr_size()));
				stmt.setString(++index, storage);
				stmt.setString(++index, _do.getCaption_kr());
				stmt.setString(++index, toDateTime);
				stmt.setString(++index, _do.getRequest_id());
				stmt.setString(++index, _do.getType());
				stmt.setInt(++index, maxSeq);
				stmt.executeUpdate();
			}else if(_do.getType().equals("011")){
				int maxSeq = selectFileInfoMaxSeq(_do.getMaster_id()) + 1;
				stmt.setLong(++index, _do.getMaster_id());
				stmt.setString(++index, _do.getType());
				stmt.setString(++index, CodeConstants.AttachFlag.ATTACH);
				stmt.setString(++index,_do.getCaption_jp());
				stmt.setLong(++index, Long.parseLong(_do.getJp_size()));
				stmt.setString(++index, storage);
				stmt.setString(++index, _do.getCaption_jp());
				stmt.setString(++index, toDateTime);
				stmt.setString(++index, _do.getRequest_id());
				stmt.setString(++index, _do.getType());
				stmt.setInt(++index, maxSeq);
				stmt.executeUpdate();
			}else if(_do.getType().equals("012")){
				int maxSeq = selectFileInfoMaxSeq(_do.getMaster_id()) + 1;
				stmt.setLong(++index, _do.getMaster_id());
				stmt.setString(++index, _do.getType());
				stmt.setString(++index, CodeConstants.AttachFlag.ATTACH);
				stmt.setString(++index,_do.getCaption_en());
				stmt.setLong(++index, Long.parseLong(_do.getEn_size()));
				stmt.setString(++index, storage);
				stmt.setString(++index, _do.getCaption_en());
				stmt.setString(++index, toDateTime);
				stmt.setString(++index, _do.getRequest_id());
				stmt.setString(++index,_do.getType());
				stmt.setInt(++index, maxSeq);
				stmt.executeUpdate();
			}else if(_do.getType().equals("013")){
				int maxSeq = selectFileInfoMaxSeq(_do.getMaster_id()) + 1;
				stmt.setLong(++index, _do.getMaster_id());
				stmt.setString(++index, _do.getType());
				stmt.setString(++index, CodeConstants.AttachFlag.ATTACH);
				stmt.setString(++index,_do.getCaption_cn());
				stmt.setLong(++index, Long.parseLong(_do.getCn_size()));
				stmt.setString(++index, storage);
				stmt.setString(++index, _do.getCaption_cn());
				stmt.setString(++index, toDateTime);
				stmt.setString(++index, _do.getRequest_id());
				stmt.setString(++index,_do.getType());
				stmt.setInt(++index, maxSeq);
				stmt.executeUpdate();
			}
			int updatcount =1;

			if(_do.getConn() == null)
				con.commit();

			return updatcount;
		} catch (Exception e) {
			if(con != null) {
				try {
					if(_do.getConn() == null) con.rollback();
				} catch (SQLException e1) {}
			}
			throw e;
		} finally {
			if(_do.getConn() == null)
				release(null, stmt, con);
			else
				release(null, stmt, null);
		}

	}	


	/**
	 * 특정 게시물id의 첨부파일에서  첨부파일 개수를 파악한다.
	 * @param boardId 게시판 id
	 * @throws Exception 
	 */
	private int selectFileInfoMaxSeq(long masterId) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(" select value(max(SEQ), 0) FROM  DAS.ATTCH_TBL where MOTHR_DATA_ID = "+masterId+"  \n");

		Connection con = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectFileInfoMaxSeq######## con : " + con);

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
	 * IFCMS 아카이브  상태변환
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int updateIfCmsArchiveStatus(IfCmsArchiveDO pgmDO) throws Exception
	{
		logger.debug(pgmDO);
		StringBuffer buf = new StringBuffer();
		buf.append("\n update  METADAT_MST_TBL set   ");

		buf.append("\n DATA_STAT_CD = ?    ");
		buf.append("\n where   master_id = ?  ");


		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateIfCmsArchiveStatus######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int updateCount = 0;
			int index=0;


			index = 0;	

			stmt.setString(++index, "001");//DATA_STAT_CD
			stmt.setLong(++index, pgmDO.getMaster_id());//MASTER_ID


			externalDAO.updateContentsInstForIfCms(con,pgmDO);
			updateCount = stmt.executeUpdate();
			//ExternalDAO.updateContents(item);



			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * 마스터ID를 조회한다(그룹id)
	 * @param media_id 미디어id
	 * @return
	 * @throws Exception 
	 */
	public long selectMasterIdForIfCms(long group_id) throws Exception
	{
		String query = SystemManageStatement.selectMaster_idForIfCmsQuery(group_id);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMasterIdForIfCms######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();
			long masterid=0;
			if(rs.next())
			{
				masterid = rs.getLong("master_id");

				return masterid;
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
	 * ct_id, cti_id(wmv) 값을 얻어온다
	 * @param media_id 미디어id                                                                                                                                                                                              
	 * @return  PdsArchiveDO    
	 * @throws Exception 
	 *  */
	public IfCmsArchiveDO selectTcInfoForIfCms(String media_id) throws Exception
	{
		String query = SystemManageStatement.selectTCInfoQuery(media_id);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTcInfoForIfCms######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			//  stmt.setString(++index, pgm_cd);
			//stmt.setString(++index, perRegNo);

			// stmt.setString(++index, perRegNo);


			rs = stmt.executeQuery();

			if(rs.next())
			{
				IfCmsArchiveDO item = new IfCmsArchiveDO();
				item.setCt_id(rs.getLong("ct_id"));
				item.setCti_idForLow(rs.getLong("cti_id"));


				return item;
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
	 * tc에  정보를 삽입
	 * @param m
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertIfCms(IfCmsArchiveDO ifCmsArchiveDO) throws Exception
	{


		Connection con = null;
		PreparedStatement stmt = null;

		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO TC_JOB_TBL ( ");
		buf.append("\n   SEQ ");
		buf.append("\n ,MEDIA_ID ");
		buf.append("\n ,REG_DT ");
		buf.append("\n ,RESULT ");
		buf.append("\n ,TC_ID ");
		buf.append("\n ,REQ_CD ");
		buf.append("\n ,INPUT_HR ");
		buf.append("\n ,INPUT_LR ");
		buf.append("\n ,OUTPUT_LR_PATH ");
		buf.append("\n ,OUTPUT_CT_PATH ");
		buf.append("\n ,OUTPUT_LR_NM ");
		buf.append("\n ,OUTPUT_CT_NM ");
		buf.append("\n ,CT_ID ");
		buf.append("\n ,FILE_READY ");
		buf.append("\n ,INPUT_HR_NM ");
		buf.append("\n ,JOB_ALOCATE ");
		buf.append("\n ,TC_TYPE ");
		buf.append("\n ,COCD ");
		buf.append("\n ) VALUES ( ");
		buf.append("\n ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertIfCms######## con : " + con);
			con.setAutoCommit(false);

			//	stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
			long seq =  selectSeq();
			stmt.setLong(++index, seq);//SEQ
			stmt.setString(++index, String.valueOf(ifCmsArchiveDO.getMedia_id()));//MEDIA_ID
			stmt.setString(++index, dateTime);//REG_DT
			stmt.setString(++index, "");//RESULT
			stmt.setString(++index, "");//TC_ID
			stmt.setString(++index, "LRCT");			//REQ_CD
			char d ='/';

			String storage =  ifCmsArchiveDO.getStorage_path().replace('\\', d);

			storage = storage.replaceAll(dasHandler.getProperty("NEARLINE"), dasHandler.getProperty("WINNEARLINE"));
			stmt.setString(++index, storage);//INPUT_HR
			if(ifCmsArchiveDO.getPublisher().equals("")||ifCmsArchiveDO.getPublisher().equals("S")){
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}else{
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id());//INPUT_LR
				stmt.setString(++index,  dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id());	//OUTPUT_LR_PATH
				stmt.setString(++index, dasHandler.getProperty("WINNET_MP4")+"/"+dateTime2+"/"+ifCmsArchiveDO.getCt_id()+"/KFRM");//OUTPUT_CT_PATH
			}

			stmt.setString(++index, ifCmsArchiveDO.getCti_idForLow()+".mp4");//OUTPUT_LR_NM
			stmt.setString(++index,ifCmsArchiveDO.getCt_id()+".mer");//OUTPUT_CT_NM
			stmt.setLong(++index, ifCmsArchiveDO.getCt_id());//CT_ID

			stmt.setString(++index, "");//FILE_READY
			stmt.setString(++index, ifCmsArchiveDO.getFile_name());//INPUT_HR_NM
			stmt.setString(++index,"N");//JOB_ALOCATE
			stmt.setString(++index,"002");//TC_TYPE
			if(ifCmsArchiveDO.getPublisher().equals("")){
				stmt.setString(++index,"S");//COCD
			}else{
				if(ifCmsArchiveDO.getPublisher().equals("S")){
					stmt.setString(++index,"S");//COCD	
				}else{
					stmt.setString(++index,ifCmsArchiveDO.getPublisher());//COCD	
				}
			}

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
	 * 수동요청건에대한 기록을 남긴다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertHistoryInfo(UseInfoDO condition) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into ARCHIVE_HIST_TBL ( GUBUN,  JOB_ID, REQ_ID, REQ_DT)   ");

		buf.append("\n values ");
		buf.append("\n ( ");		
		buf.append("\n ?, ?, ?, ?  ");
		buf.append("\n ) ");

		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertHistoryInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;			
			stmt.setString(++index, condition.getGubun());
			long num = getLocNum(condition.getCti_id());
			stmt.setLong(++index,num );
			if(!condition.getUser_id().equals("")){

				stmt.setString(++index, condition.getUser_id());
			}else{
				stmt.setString(++index, condition.getReg_id());
			}
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));



			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Inserted Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}


			con.commit();
			return updateCount;
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
	 * num값을 조회
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long getLocNum(long cti_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select num from contents_loc_TBL where cti_id = ?   ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getLocNum######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setLong(++index, cti_id);
			rs = stmt.executeQuery();
			long num =0;
			while(rs.next())
			{
				num =rs.getLong("num");

			}


			return num;
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
	 * 우선순위를 변경한다.(다운로드용)
	 * @param monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String changePriorityForDownload(MonitoringDO monitoringDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######changePriorityForDownload######## con : " + con);
			MonitoringDO request = new MonitoringDO();
			request = externalDAO.getOBJNAME(monitoringDO.getKey());
			String dtl_type = externalDAO.selectCOCD(request.getCti_id());
			request.setDtl_type(dtl_type);
			request.setPriority(monitoringDO.getPriority());
			request.setGubun("007");
			request.setKey(monitoringDO.getKey());
			externalDAO.changePriorityForArch(request);

			return "1";
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
	 * 스토리지 용량 조회한다
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 */
	public List getStorageCheck(StorageDO storageDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append(SystemManageStatement.getStorageCheck());



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getStorageCheck######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;
			if(storageDO.getSystem_id().equals("SBSONAIR")){
				stmt.setString(++index, "SBS 방송본");
			}else if(storageDO.getSystem_id().equals("SBSNON")){
				stmt.setString(++index, "SBS 비방송본");
			}else if(storageDO.getSystem_id().equals("MEDIANETONAIR")){
				stmt.setString(++index, "미디어넷 방송본");
			}else if(storageDO.getSystem_id().equals("MEDIANETNON")){
				stmt.setString(++index, "미디어넷 비방송본");
			}else{
				stmt.setString(++index, "SBS 방송본");
			}
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StorageDO item = new StorageDO();



				item.setStorage_nm(rs.getString("folder_nm"));
				item.setPath(rs.getString("path"));
				item.setTotal_size(rs.getString("total_volume"));
				item.setUsing_sz(rs.getString("use_volume"));
				item.setStorgae_size(rs.getString("passible_volume"));
				item.setLimite(rs.getString("limit"));
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
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateCopyRequestForCtId(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateCopyRequestForCtId######## con : " + con);
			UseInfoDO pADO = externalDAO.getIdForCtIdForCopy(copyDO.getCt_id());

			String pgm_cms_id = systemManageDAO.selectPdsPgmId(copyDO.getCt_id());
			pADO.setUser_id(copyDO.getUser_id());
			pADO.setDtl_type(copyDO.getDtl_type());
			pADO.setCt_id(copyDO.getCt_id());
			pADO.setFileName(copyDO.getFileName());
			externalDAO.ArchiveCopyReq(pADO,pgm_cms_id);
			copyDO.setCt_id(pADO.getCt_id());
			copyDO.setCti_id(pADO.getCti_id());
			copyDO.setGubun("002");
			insertHistoryInfo(copyDO);
			//return "1";

			return "1";
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
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateBackupRequestForCtId(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateBackupRequestForCtId######## con : " + con);
			UseInfoDO pADO = externalDAO.getIdForCtIdForCopy(copyDO.getCt_id());

			String pgm_cms_id = systemManageDAO.selectPdsPgmId(copyDO.getCt_id());



			pADO.setUser_id(copyDO.getUser_id());
			pADO.setDtl_type(copyDO.getDtl_type());
			pADO.setCt_id(copyDO.getCt_id());

			externalDAO.ArchiveBackUpReq(pADO,pgm_cms_id);

			copyDO.setCti_id(pADO.getCti_id());
			copyDO.setGubun("003");
			insertHistoryInfo(copyDO);

			return "1";
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
	 * 프로그램 복원을 요청한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRecorveryRequestForCtId(UseInfoDO copyDO) throws Exception
	{

		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRecorveryRequestForCtId######## con : " + con);
			UseInfoDO pADO = externalDAO.getIdForCtIdForCopy(copyDO.getCt_id());

			String pgm_cms_id = systemManageDAO.selectPdsPgmId(copyDO.getCt_id());

			pADO.setUser_id(copyDO.getUser_id());
			pADO.setDtl_type(copyDO.getDtl_type());
			pADO.setCt_id(copyDO.getCt_id());
			pADO.setFileName(copyDO.getFileName());
			externalDAO.ArchiveRecorveryReq(pADO,pgm_cms_id);

			copyDO.setCti_id(pADO.getCti_id());
			copyDO.setGubun("004");
			insertHistoryInfo(copyDO);

			return "1";
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
	 * contents_down_tbl 의 키값을 생성한다
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public long selectContentsDownid() throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select nextval for SEQ_DOWN_NUM from sysibm.sysdummy1 ");


		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectContentsDownid######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			rs = stmt.executeQuery();
			rs.next();

			long newMasterId = rs.getLong(1);



			return newMasterId;
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
	 * 경로조회
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String getFlPath(long cti_id) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("select fl_path from contents_inst_tbl where cti_id = ? and cti_fmt like '2%' ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getFlPath######## con : " + con);

			stmt = con.prepareStatement(buf.toString());


			int index = 0;

			stmt.setLong(++index, cti_id);//CT_ID
			rs = stmt.executeQuery();
			String fl_path ="";
			while(rs.next())
			{
				fl_path =rs.getString("fl_path");

			}


			return fl_path;
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
	 * 정리완료처리한다
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRunScheduleDt() throws Exception
	{
		StringBuffer buf = new StringBuffer();

		Connection con = null;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try   
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateArrange######## con : " + con);
			con.setAutoCommit(false);
			int updateCount =0;


			buf.append("\n update DAS.scheduler_info_Tbl set ");
			buf.append("\n 	next_run_dt =  current timestamp +1 days ");
			buf.append("\n 	,run_dt = current timestamp  "); 
			buf.append("\n WITH UR	");

			stmt = con.prepareStatement(buf.toString());

			int index = 0;


			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_PROGRAM_INFO, "해당 프로그램 정보가 존재하지 않습니다.");
				throw exception;
			}


			con.commit();
			return "success";
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
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, null);
			release(null, stmt2, con);
		}
	}

}

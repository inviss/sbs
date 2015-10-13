package com.app.das.business.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.app.das.business.JNI_Des;
import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.UserRoleStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.AuthDO;
import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.EmployeeDASRoleDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.ErpAppointDO;
import com.app.das.business.transfer.GuarantorInfoDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.Node;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.business.transfer.OtherDBDeptInfoDO;
import com.app.das.business.transfer.OtherDBuserInfoDO;
import com.app.das.business.transfer.PaUserInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.business.transfer.SubsidiaryinfoDO;
import com.app.das.business.transfer.TokenDO;
import com.app.das.log.DasPropHandler;
import com.app.das.services.PaUserInfoDOXML;
import com.app.das.services.PaUserPasswordInfoDOXML;
import com.app.das.services.XmlConvertorService;
import com.app.das.services.XmlConvertorServiceImpl;
import com.app.das.util.CalendarUtil;
import com.app.das.util.DBService;
import com.app.das.util.LoggableStatement;
import com.app.das.util.StringUtils;

/**
 * 사용자 관리(내부, 외부, 역할)에 대한 Database 로직이 구현되어 있다.
 * 
 * @author ysk523
 * 
 */
public class UserRoleDAO extends AbstractDAO {

	private Logger logger = Logger.getLogger(UserRoleDAO.class);
	
	private static UserRoleDAO instance;
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	private static SystemManageDAO systemManageDAO = SystemManageDAO.getInstance();
	/**
	 * A private constructor
	 * 
	 */
	private UserRoleDAO() {
	}

	public static synchronized UserRoleDAO getInstance() {
		if (instance == null) {
			instance = new UserRoleDAO();
		}
		return instance;
	}

	/**
	 * 정직원 목록조회를 한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	/*
	 * public PageDO selectEmployeeRoleList(EmployeeRoleConditionDO condition,
	 * DASCommonDO commonDO) throws DASException { PageDO pageDO = new PageDO();
	 * 
	 * StringBuffer buf = new StringBuffer(); buf.append(
	 * " select * FROM                                                      										\n"
	 * ); buf.append(
	 * " (                                                                  												\n"
	 * ); buf.append(UserRoleStatement.selectEmployeeRoleListQuery(condition,
	 * DASBusinessConstants.PageQueryFlag.NORMAL)); buf.append(
	 * " ) AS usr                                                           											\n"
	 * ); buf.append(
	 * " where usr.rownum >= ? and usr.rownum <=?                                            	\n"
	 * ); buf.append(" WITH UR	 ");
	 * 
	 * //Page에 따른 계산을 한다. int page = condition.getPage(); if(page == 0) { page =
	 * 1; }
	 * 
	 * Connection con = null; PreparedStatement stmt = null; ResultSet rs =
	 * null; try { con = DBService.getInstance().getConnection();
	 * 
	 * //총 조회 갯수를 구한다. int totalCount = getTotalCount(con,
	 * UserRoleStatement.selectEmployeeRoleListQuery(condition,
	 * DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));
	 * 
	 * 
	 * 
	 * stmt = con.prepareStatement(buf.toString());
	 * 
	 * //디스플레이할 페이지의 시작 row와 끝 row를 계산한다. int endNum = page *
	 * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT; int startNum =
	 * endNum - (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT -1);
	 * 
	 * int index = 0; stmt.setInt(++index, startNum); stmt.setInt(++index,
	 * endNum);
	 * 
	 * rs = stmt.executeQuery();
	 * 
	 * int indexCount = 0; List resultList = new ArrayList();
	 * 
	 * while(rs.next()) { EmployeeDASRoleDO item = new EmployeeDASRoleDO();
	 * item.setUserNo( rs.getString("USER_NO")); item.setDeptCd(
	 * rs.getString("DEPT_CD")); item.setDeptNm( rs.getString("DEP_NAME"));
	 * item.setUserId( rs.getString("USER_ID")); item.setUserNm(
	 * rs.getString("USER_NM")); item.setRole( rs.getString("ROLE"));
	 * item.setSerialNo( rs.getInt("rownum"));
	 * 
	 * resultList.add(item); }
	 * 
	 * int totalPageCount = totalCount /
	 * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT + (totalCount %
	 * DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1 : 0);
	 * 
	 * //검색된 List를 셋팅한다. pageDO.setPageItems(resultList); //계산된 총 Page 수를 셋팅한다.
	 * pageDO.setTotalPageCount(totalPageCount);
	 * 
	 * 
	 * return pageDO; } catch (NamingException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } catch (SQLException e) { // TODO 자동
	 * 생성된 catch 블록 e.printStackTrace();
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { release(rs, stmt, con); }
	 * }
	 */
	/**
	 * 정직원의 Role 정보를 등록 또는 수정한다.
	 * 
	 * @param regularEmployeeDASRoleList
	 *            정직원 Role 정보(RegularEmployeeDASRole)가 포함되어 있는 List
	 * @return
	 */
	/*
	 * public void updateEmployeeRoleList(List regularEmployeeDASRoleList,
	 * DASCommonDO commonDO) throws DASException { Connection con = null; try {
	 * con = DBService.getInstance().getConnection(); con.setAutoCommit(false);
	 * 
	 * for(Iterator i = regularEmployeeDASRoleList.iterator(); i.hasNext();) {
	 * EmployeeDASRoleDO item = (EmployeeDASRoleDO)i.next();
	 * 
	 * //현재 테이블에 존재하면 수정, 존재하지 않으면 등록이다
	 * if(isThereEmployeeRole(item.getUserId())) {
	 * item.setTrxKind(DASBusinessConstants.TrxKind.UPDATE); } else {
	 * item.setTrxKind(DASBusinessConstants.TrxKind.CREATE); } }
	 * 
	 * 
	 * for(Iterator i = regularEmployeeDASRoleList.iterator(); i.hasNext();) {
	 * EmployeeDASRoleDO item = (EmployeeDASRoleDO)i.next();
	 * 
	 * //현재 테이블에 존재하면 수정, 존재하지 않으면 등록이다
	 * if(DASBusinessConstants.TrxKind.UPDATE.equals(item.getTrxKind())) {
	 * updateEmployeeRole(con, item.getUserId(), item.getRole(), commonDO); }
	 * else { insertEmployeeRole(con, item, commonDO); } }
	 * 
	 * con.commit(); } catch (NamingException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } } DASException exception =
	 * new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e); throw
	 * exception;
	 * 
	 * } catch (SQLException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * try { con.rollback(); } catch (SQLException e1) { // TODO 자동 생성된 catch 블록
	 * e1.printStackTrace(); } DASException exception = new
	 * DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e); throw
	 * exception;
	 * 
	 * } finally { try { if (con != null) con.close(); } catch (SQLException e)
	 * {} } }
	 */
	/**
	 * 비직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectOutsiderEmployeeRoleList(NonEmployeeDASRoleDO condition)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectOutEmployeeRoleListQuery(condition,
				DASBusinessConstants.PageQueryFlag.NORMAL));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutsiderEmployeeRoleList######## con : " + con);

			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con,
					UserRoleStatement.selectOutEmployeeRoleListQuery(condition,
							DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				NonEmployeeInfoDO item = new NonEmployeeInfoDO();

				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setOut_user_ID(rs.getString("out_user_id"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setRole_cd(rs.getString("role_cd"));

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

	/**
	 * 이미 테이블에 존재하는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 * 
	 * @return NonEmployeeDASRoleDO 외부직원 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public NonEmployeeDASRoleDO selectOutEmployeeRole(String perRegNo)
			throws Exception {
		String query = UserRoleStatement.selectOutEmployeeRoleQuery(perRegNo);
		NonEmployeeDASRoleDO nDO = new NonEmployeeDASRoleDO();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutEmployeeRole######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, perRegNo);

			// MHCHOI
			// 주민번호가 없는 사용자 이면 사용자 ID를 가지고 정보 조회
			if (perRegNo.equals(DASBusinessConstants.PER_REG_NO))
				stmt.setString(++index, nDO.getSbs_user_ID());
			else
				stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();

			if (rs.next()) {
				NonEmployeeDASRoleDO item = new NonEmployeeDASRoleDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("OUT_USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("OUT_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setMobile(rs.getString("CNTC_PLC_TEL_NO"));
				item.setSubsi_tel(rs.getString("CNTC_PLC_OUTS"));
				item.setPgm_id(rs.getInt("PGM_ID"));
				item.setMod_dt(rs.getString("MOD_DT"));

				return item;
			} else {
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;

			}
		}  catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}
	/**
	 * 이미 테이블에 존재하는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 * @param userID
	 *            사용자 정보
	 * @return List 외부직원 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectOutEmployeeRole(String perRegNo, String userID)
			throws Exception {
		String query = UserRoleStatement.selectOutEmployeeRoleQuery(perRegNo,
				userID);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutEmployeeRole######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			// stmt.setString(++index, perRegNo);
			stmt.setString(++index, userID); // MHCHOI 0106
			// stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if (rs.next()) {
				NonEmployeeDASRoleDO item = new NonEmployeeDASRoleDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setMobile(rs.getString("MOBILE"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));

				resultList.add(item);
				return resultList;
			} else {
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;

			}
		} catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 테이블에 존재하지 않는 특정 외부직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 * @param commonDO
	 *            공통정보
	 * @return NonEmployeeDASRoleDO 외부직원 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectOutEmployeeRoleForNotExist(String perRegNo)
			throws Exception {
		PageDO pageDO = new PageDO();

		String query = UserRoleStatement
				.selectOutEmployeeRoleForNotExistQuery(perRegNo);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutEmployeeRoleForNotExist######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if (rs.next()) {
				NonEmployeeDASRoleDO item = new NonEmployeeDASRoleDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setMobile(rs.getString("MOBILE"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));
				item.setEmployee_yn(rs.getString("EMPLOYEE_YN"));
				resultList.add(item);
				return resultList;
			} else {
				return resultList;

			}
		} catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 외부직원의 정보를 조회한다.
	 * 
	 * @param userId
	 *            사용자 ID
	 * @return NonEmployeeDASRoleDO 외부 사용자 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public NonEmployeeDASRoleDO selectOutEmployeeInfo(String userId)
			throws Exception {
		String query = UserRoleStatement.selectOutEmployeeInfoQuery();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutEmployeeInfo######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			if (rs.next()) {
				NonEmployeeDASRoleDO item = new NonEmployeeDASRoleDO();

				return item;
			} else {
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;

			}
		} catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 외부 사용자 변경 내역을 조회한다.
	 * 
	 * @param perRegNo
	 *            외부사용자의 주민번호
	 * @param userId
	 *            사용자id
	 * @param currentPage
	 *            현재 페이지
	 * @param commonDO
	 *            공통정보
	 * @return List 외부사용자 정보 변경 내역을 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO selectOutEmployeeRoleHistoryList(String perRegNo,
			String userId, int currentPage, DASCommonDO commonDO)
					throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(UserRoleStatement.selectOutEmployeeRoleHistoryList(perRegNo,
				userId, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" WITH UR	 \n");

		// Page에 따른 계산을 한다.
		int page = currentPage;
		if (page == 0) {
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOutEmployeeRoleHistoryList######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con,
					UserRoleStatement.selectOutEmployeeRoleHistoryList(
							perRegNo, userId,
							DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.SBS_USER_ROLE_ROW_COUNT;

			// 디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page * rowPerPage;
			int startNum = endNum - (rowPerPage - 1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				NonEmployeeDASRoleDO item = new NonEmployeeDASRoleDO();

				resultList.add(item);
			}

			// 총페이지 수를 계산한다.
			int totalPageCount = totalCount / rowPerPage
					+ (totalCount % rowPerPage != 0 ? 1 : 0);

			// 검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			// 계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);

			return pageDO;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 보증인 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            외부직원의 주민번호
	 * @return List 보증인 정보를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectGuarantorInfoList(String perRegNo) throws Exception {
		String query = UserRoleStatement.selectGuarantorInfoList(perRegNo);

		if (logger.isDebugEnabled()) {
			logger.debug("[selectGuarantorInfoList][Input perRegNo]" + perRegNo);
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectGuarantorInfoList######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, CalendarUtil.getToday());

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				GuarantorInfoDO item = new GuarantorInfoDO();
				item.setUserNo(rs.getString("USER_NO"));
				item.setUserNm(rs.getString("USER_NM"));
				item.setDeptNm(rs.getString("DEPT_NM"));

				resultList.add(item);
			}

			return resultList;
		} catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 사용정지 외부직원의 Role 정보를 복원한다.
	 * 
	 * @param perRegN
	 *            정직원 주민등록 번호
	 */
	/*
	 * public void updateEnableOutEmployeeRole(String perRegNo) throws
	 * DASException { StringBuffer buf = new StringBuffer();
	 * buf.append("\n update DAS.OUTSIDER_INFO_TBL set ");
	 * buf.append("\n 	DEL_DD = ?,  "); buf.append("\n where PER_REG_NO = ?  ");
	 * 
	 * Connection con = null; PreparedStatement stmt = null; try { con =
	 * DBService.getInstance().getConnection(); con.setAutoCommit(false);
	 * 
	 * stmt = con.prepareStatement(buf.toString());
	 * 
	 * int index = 0; stmt.setString(++index, " "); stmt.setString(++index,
	 * perRegNo);
	 * 
	 * int updateCount = stmt.executeUpdate();
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("[Update Count]" +
	 * updateCount); }
	 * 
	 * if(updateCount == 0) { //여기서 에러를 던진다. DASException exception = new
	 * DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다."); throw
	 * exception; }
	 * 
	 * con.commit(); } catch (NamingException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception;
	 * 
	 * } catch (SQLException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { release(null, stmt, con);
	 * }
	 * 
	 * }
	 */
	/**
	 * 외부직원의 Role 정보를 사용정지 한다.
	 * 
	 * @param perRegN
	 *            정직원 주민등록 번호
	 */
	/*
	 * public void updateDisabledOutEmployeeRole(String perRegNo) throws
	 * DASException { StringBuffer buf = new StringBuffer();
	 * buf.append("\n update DAS.OUTSIDER_INFO_TBL set ");
	 * buf.append("\n 	DEL_DD = ?,  "); buf.append("\n where PER_REG_NO = ?  ");
	 * 
	 * Connection con = null; PreparedStatement stmt = null; try { con =
	 * DBService.getInstance().getConnection(); con.setAutoCommit(false);
	 * 
	 * stmt = con.prepareStatement(buf.toString());
	 * 
	 * int index = 0; stmt.setString(++index, CalendarUtil.getToday());
	 * stmt.setString(++index, perRegNo);
	 * 
	 * int updateCount = stmt.executeUpdate();
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("[Update Count]" +
	 * updateCount); }
	 * 
	 * if(updateCount == 0) { //여기서 에러를 던진다. DASException exception = new
	 * DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다."); throw
	 * exception; }
	 * 
	 * con.commit(); } catch (NamingException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception;
	 * 
	 * } catch (SQLException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { release(null, stmt, con);
	 * } }
	 */
	/**
	 * 외부직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateOutEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_BGN  = ?,  ");
		buf.append("\n 	VLDDT_END  = ?,  ");
		buf.append("\n 	USER_NM  = ?,  ");
		buf.append("\n 	PGM_NM  = ?,  ");
		buf.append("\n 	W_DEPT = ?,  ");
		buf.append("\n 	ROLE = ?,  ");
		buf.append("\n 	POSITION = ? , ");
		buf.append("\n 	MOBILE = ?,  ");
		buf.append("\n 	SUBSI_TEL = ? , ");
		buf.append("\n 	MOD_DT = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		String dateToTime = CalendarUtil.getDateTime("yyyyMMdd");
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOutEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getPgm_nm());
			stmt.setString(++index, roleDO.getW_Dept());
			stmt.setString(++index, roleDO.getRole());
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setString(++index, roleDO.getSubsi_tel());
			stmt.setString(++index, dateToTime);
			stmt.setString(++index, roleDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 외부직원의 Role 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int insertNonEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	W_DEPT,  ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END,  ");
		buf.append("\n 	POSITION,  ");
		buf.append("\n 	APPROVE_YN,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	ROLE,  ");
		buf.append("\n 	MOBILE,  ");
		buf.append("\n 	SUBSI_TEL,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	DEL_DD,  ");
		buf.append("\n 	PW_LST_CHG , ");
		buf.append("\n 	PW_ERN,  ");
		buf.append("\n 	PGM_NM,  ");
		buf.append("\n 	EMPLOYEE_YN , ");
		buf.append("\n 	EMPLOYEE_TYPE , ");
		buf.append("\n 	INTER_PHON , ");
		buf.append("\n 	REG_DT , ");
		buf.append("\n 	MOD_DT  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		// 주민등록번호 길이를 검사하여 주민등록번호가 없는 사용자의 비밀번호는 "1111"로 설정한다
		if (roleDO.getPer_reg_no().length() < 13)
			// buf.append("\n (?, ? , encrypt('"+DASBusinessConstants.INITIAL_PASSWD+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)), ? , ? , ? , ? , ?, ? , ? , ? , ?, ? , ? , ? , ?)  ");
			buf.append("\n (?, ? , ?, ? , ? , ? , ?, ? , ? , ? , ?, ? , ? , ?,?,?,?,?,?,?,?,?)  ");
		else
			// buf.append("\n (?, ? , encrypt('"+StringUtils.getInitialPasswd(roleDO.getPer_reg_no())+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)), ? , ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?)  ");
			buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ? , ? , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?)  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;

			userId = getOutUserID();

			if (roleDO.getPer_reg_no().length() < 13)
				stmt.setString(++index, DASBusinessConstants.PER_REG_NO);
			else
				stmt.setString(++index, roleDO.getPer_reg_no());
			stmt.setString(++index, userId);
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getW_Dept());
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, roleDO.getApprove_yn());
			stmt.setString(++index, roleDO.getPassword());
			stmt.setString(++index, roleDO.getRole());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setString(++index, roleDO.getSubsi_tel());
			stmt.setInt(++index, roleDO.getPgm_id());
			stmt.setString(++index, roleDO.getDel_DD());
			stmt.setString(++index, roleDO.getPw_lst_chg());
			stmt.setInt(++index, roleDO.getPw_ern());
			stmt.setString(++index, roleDO.getPgm_nm());
			stmt.setString(++index, "Y");
			stmt.setString(++index, "003");
			stmt.setString(++index, roleDO.getSubsi_tel());
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMdd"));
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMdd"));

			//

			roleDO.setSbs_user_ID(userId);

			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 외부직원의 Role 신청을 승인한다 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int approveNonEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n APPROVE_YN = 'Y'  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######approveNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			// stmt.setString(++index, roleDO.getApprove_yn());
			stmt.setString(++index, roleDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.

			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 외부직원의 Role 기간을 연장 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int prolongNonEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_END = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######prolongNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.

			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 외부직원의 Role 정지 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int stopNonEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_END = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######stopNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateToTime = CalendarUtil.getDateTime("yyyyMMdd");
			stmt.setString(++index, dateToTime);
			stmt.setString(++index, roleDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.

			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 외부직원의 Role 정보를 영구 삭제 한다.
	 * 
	 * @param regularEmployeeDASRoleList
	 *            정직원 Role 정보(EmployeeDASRoleDO)가 포함되어 있는 List
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int deleteOutEmployeeRole(String perRegNo) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(" delete from  DAS.USER_INFO_TBL  \n");
		buf.append(" where PER_REG_NO = ?		 		\n");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteOutEmployeeRole######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, passwd);
			stmt.setString(++index, perRegNo);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;

		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 외부직원의 Role 정보를 영구 삭제 한다.
	 * 
	 * @param regularEmployeeDASRoleList
	 *            정직원 Role 정보(EmployeeDASRoleDO)가 포함되어 있는 List
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public void deleteOutEmployeeRole(String perRegNo, String userID,
			DASCommonDO commonDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(" delete from  DAS.OUTSIDER_INFO_TBL  \n");
		buf.append(" where PER_REG_NO = ? and OUT_USER_ID = ?	\n");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteOutEmployeeRole######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, passwd);
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, userID); // MHCHOI

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;

		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 외부사용자의 비밀번호를 초기화 시킨다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public void updateOutEmployeePasswd(String regNo, DASCommonDO commonDO)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTSIDER_INFO_TBL set  ");
		buf.append("\n 		PASSWORD = encrypt('"
				+ StringUtils.getInitialPasswd(regNo)
				+ "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ");
		buf.append(" \n		PW_LST_CHG = ?,  ");
		buf.append("\n 		PW_ERN = 0, ");
		buf.append("\n 		MOD_DT = ?, ");
		buf.append("\n 		MODRID = ? ");
		buf.append("\n where PER_REG_NO = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOutEmployeePasswd######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, StringUtils.getInitialPasswd(regNo));
			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));
			stmt.setString(++index, commonDO.getUserId());
			stmt.setString(++index, regNo);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
		}catch (Exception e) {
			logger.error(buf.toString());
			throw e;

		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 외부사용자의 비밀번호를 초기화 시킨다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public void updateOutEmployeePasswd(String regNo, String userID,
			DASCommonDO commonDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTSIDER_INFO_TBL set  ");

		// 주민번호가 없는 사용자는 초기 패스워드를 1111로 초기화 한다 MHCHOI
		if (regNo.equals(DASBusinessConstants.PER_REG_NO))
			buf.append("\n 		PASSWORD = encrypt('"
					+ DASBusinessConstants.INITIAL_PASSWD
					+ "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ");
		else
			buf.append("\n 		PASSWORD = encrypt('"
					+ StringUtils.getInitialPasswd(regNo)
					+ "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ");

		buf.append(" \n		PW_LST_CHG = ?,  ");
		buf.append("\n 		PW_ERN = 0, ");
		buf.append("\n 		MOD_DT = ?, ");
		buf.append("\n 		MODRID = ? ");
		// buf.append("\n where PER_REG_NO = ? "); MHCHOI
		buf.append("\n where OUT_USER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOutEmployeePasswd######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, StringUtils.getInitialPasswd(regNo));
			stmt.setString(++index, CalendarUtil.getToday());
			stmt.setString(++index, CalendarUtil.getDateTime("yyyyMMddHHmmss"));
			stmt.setString(++index, commonDO.getUserId());
			stmt.setString(++index, userID);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;

		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 사용자 정보의 수정 내역을 등록한다.
	 * 
	 * @param roleDO
	 *            사용자 정보를 포함하고 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws SQLException 
	 */
	public void insertNonEmployeeRoleHistory(Connection con,
			NonEmployeeDASRoleDO roleDO) throws DASException, SQLException {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.OUTSIDER_HIST_TBL( ");
		buf.append("\n 	OUT_USER_ID, ");
		buf.append("\n 	SEQ,  ");
		buf.append("\n 	ROLE, ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END, ");
		buf.append("\n 	PGM_ID,");
		buf.append("\n 	GAURANTOR_ID, ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	MOD_DT,  ");
		buf.append("\n 	MODRID  ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)	");

		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			int programId = 0;

			stmt.setInt(++index, programId);

			String dateToTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateToTime);

			stmt.setString(++index, dateToTime);

			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, stmt, null);
		}

	}

	/*
	 * public boolean isThereDisabledNonEmployeeRole(String perRegNo) throws
	 * DASException { StringBuffer buf = new StringBuffer();
	 * 
	 * buf.append(
	 * " select count(1) FROM  DAS.OUTSIDER_INFO_TBL where PER_REG_NO = '"
	 * +perRegNo+"' and DEL_DD != ' '  \n");
	 * 
	 * Connection con = null; try { con =
	 * DBService.getInstance().getConnection();
	 * 
	 * //총 조회 갯수를 구한다. int totalCount = getTotalCount(con, buf.toString());
	 * 
	 * if(totalCount > 0) { return true; } else { return false; } } catch
	 * (NamingException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } catch (SQLException e) { // TODO 자동
	 * 생성된 catch 블록 e.printStackTrace();
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { try { if (con != null)
	 * con.close(); } catch (SQLException e) {} } }
	 */
	/**
	 *  이미 존재하는 사용자인지를 검증한다.
	 * @param perRegNo 외부직원의 주민번호
	 * 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isThereNonEmployeeRole(String perRegNo) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.OUTWOKER_INFO_TBL where PER_REG_NO = '"
				+ perRegNo + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereNonEmployeeRole######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

		throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 주민번호 중복여부 조회한다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereEmployeeRole(String perRegNo) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.USER_INFO_TBL where rtrim(ltrim(PER_REG_NO)) = '"+ perRegNo + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereEmployeeRole######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}
	/**
	 * 이름을 조회한다.
	 * 
	 * @param user_id
	 *            사용자id
	 * 
	 * @return user_nm
	 * @throws Exception 
	 */


	public String getUser_nm(String userId) throws Exception
	{

		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	user_nm ");
		buf.append("\n from user_info_tbl ");
		buf.append("\n where sbs_user_id = ? ");	

		buf.append("\n WITH UR	 ");



		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getUser_nm######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();
			String user_nm ="";
			while(rs.next()){
				user_nm = rs.getString("user_nm");
			}
			return user_nm;
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
	 * 사번 중복여부 조회한다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereEmployeeRole2(String user_no) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.USER_INFO_TBL where user_num = '"
				+ user_no + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereEmployeeRole2######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(buf.toString());
throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 아이디 중복여부 조회한다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereUserInfo(String user_id) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.USER_INFO_TBL where sbs_user_id = '"
				+ user_id + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereUserInfo######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 부서정보 중복여부 조회한다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereDeptInfo(String dept_cd) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.DEP_INFO_TBL where DEPT_CD = '"
				+ dept_cd + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereDeptInfo######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 화면 권한코드를 조회한다.
	 * 
	 * @param screenNo
	 *            화면번호
	 * @param commonDO
	 *            우리시스템의 사용자 공통정보
	 * @return 권한여부코드
	 * @throws Exception 
	 */
	public String selectAuthScreen(String screenNo, DASCommonDO commonDO)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	count(1) ");
		buf.append("\n from DAS.CODE_TBL where SCL_CD = '"
				+ screenNo.toUpperCase() + "' ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAuthScreen######## con : " + con);
			// Too much log print.
			// stmt = LoggableStatement.getInstance(con,
			// UserRoleStatement.selectAuthScreenQuery(commonDO.getRegularYn()));
			stmt = con.prepareStatement(UserRoleStatement
					.selectAuthScreenQuery(commonDO.getRegularYn()));

			int index = 0;
			stmt.setString(++index, commonDO.getUserId());
			stmt.setString(++index, screenNo.toUpperCase());

			rs = stmt.executeQuery();
			String str;

			if (rs.next()) {
				str = rs.getString(1);
				if (str.equals(DASBusinessConstants.YesNo.YES)
						|| str.equals(DASBusinessConstants.YesNo.NO))
					return str;
				else
					return DASBusinessConstants.YesNo.NO;
			} else {
				// stmt = con.prepareStatement(buf.toString());
				stmt = con.prepareStatement(buf.toString());
				rs = stmt.executeQuery();
				rs.next();
				if (rs.getInt(1) == 0)
					return DASBusinessConstants.YesNo.YES; // MHCHOI
				else
					return DASBusinessConstants.YesNo.NO;
			}
			/*
			 * if(str.equals(DASBusinessConstants.YesNo.YES) ||
			 * str.equals(DASBusinessConstants.YesNo.NO)) { if
			 * (logger.isDebugEnabled()) { logger.debug("[YES/NO]!!" + str); }
			 * return str; } else { if (logger.isDebugEnabled()) {
			 * logger.debug("[Result BLANK]!!" + str); } return
			 * DASBusinessConstants.YesNo.NO; }
			 * 
			 * } else { if (logger.isDebugEnabled()) {
			 * logger.debug("[NO Result!!" + "Y"); } return
			 * DASBusinessConstants.YesNo.YES; //MHCHOI
			 * 
			 * }
			 */
		}  catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 역활별 권한 정보를 목록 조회한다.
	 * 
	 * @param role
	 *            역할코드
	 * @param commonDO
	 *            공통정보
	 * @return List AuthDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List selectAuthList(String role, DASCommonDO commonDO)
			throws Exception {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAuthList######## con : " + con);
//			stmt = LoggableStatement.getInstance(con,
//					UserRoleStatement.selectAuthListQuery());
			stmt = con.prepareStatement(UserRoleStatement.selectAuthListQuery());

			int index = 0;
			stmt.setString(++index, role);
			stmt.setString(++index, CodeConstants.CodeGroup.CLF_CD_AUTH_CODE);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				AuthDO item = new AuthDO();
				item.setAuth(rs.getString("SCL_CD"));
				item.setAuthName(rs.getString("DESC"));
				String okYn = rs.getString("OK_YN");
				if (StringUtils.isEmpty(okYn)) {
					okYn = DASBusinessConstants.YesNo.NO;
				}
				item.setAuthYn(okYn);
				item.setRole(role);

				resultList.add(item);
			}

			return resultList;
		}catch (Exception e) {
			logger.error(role);

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * 역활별 권한 정보를 수정한다.
	 * 
	 * @param authDOList
	 *            AuthDO 를 포함하고 있는 List
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public void updateAuthList(List authDOList, DASCommonDO commonDO)
			throws Exception {
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateAuthList######## con : " + con);
			con.setAutoCommit(false);

			for (Iterator i = authDOList.iterator(); i.hasNext();) {
				AuthDO item = (AuthDO) i.next();

				// 현재 테이블에 존재하면 수정, 존재하지 않으면 등록이다
				if (isThereAuthInfo(item)) {
					item.setTrxKind(DASBusinessConstants.TrxKind.UPDATE);
				} else {
					item.setTrxKind(DASBusinessConstants.TrxKind.CREATE);
				}
			}

			for (Iterator i = authDOList.iterator(); i.hasNext();) {
				AuthDO item = (AuthDO) i.next();

				// 현재 테이블에 존재하면 수정, 존재하지 않으면 등록이다
				if (DASBusinessConstants.TrxKind.UPDATE.equals(item
						.getTrxKind())) {
					updateAuthInfo(con, item, commonDO);
				} else {
					insertAuthInfo(con, item, commonDO);
				}
			}

			con.commit();
		} catch (Exception e) {
			logger.error(authDOList.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
				release(null, null, con);
			} catch (SQLException e) {
			}
		}

	}

	/**
	 * 외부사용자의 비밀번호를 변경한다.
	 * 
	 * @param userId
	 *            사용자ID
	 * @param afterPasswd
	 *            변경 할 비밀번호
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateOutEmployeePasswdAmendment(String userId,
			String afterPasswd) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set  ");
		// buf.append("\n 		PASSWORD = encrypt('"+afterPasswd+"' , (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ");
		buf.append("\n 		PASSWORD = '" + afterPasswd + "'  ");

		buf.append("\n where SBS_USER_ID = ? ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOutEmployeePasswdAmendment######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, afterPasswd);

			stmt.setString(++index, userId);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 비밀번호 변경시 이전 비밀번호에 대한 검증을 한다.
	 * 
	 * @param userId
	 *            외부 사용자 ID
	 * @param beforePasswd
	 *            이전 비밀번호
	 * @throws Exception 
	 */
	public void validateNonEmployeeAmendPasswd(String userId,
			String beforePasswd) throws Exception {
		StringBuffer buf = new StringBuffer();

		buf.append(" select count(1) FROM  DAS.USER_INFO_TBL where SBS_USER_ID = '"
				+ userId + "' \n");
		buf.append(" AND PASSWORD = '" + beforePasswd + "' \n");
		// buf.append(" and decrypt_char(PASSWORD, (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) = '"+beforePasswd+"'   \n");

		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######validateNonEmployeeAmendPasswd######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount == 0) {
				DASException exception = new DASException(
						ErrorConstants.NOT_CORRECT_ID_OR_PASSWD,
						"ID 또는 Password 가 정확하지 않습니다.");
				throw exception;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		}  finally {
			release(null, null, con);
		}
	}

	/*
	 * private boolean isThereEmployeeRole(String userId) throws
	 * NamingException, SQLException { StringBuffer buf = new StringBuffer();
	 * 
	 * buf.append(" select count(1) FROM  DAS.REGULAR_AUTH_TBL where USER_ID = '"
	 * +userId+"'   \n");
	 * 
	 * Connection con = null; try { con =
	 * DBService.getInstance().getConnection();
	 * 
	 * //총 조회 갯수를 구한다. int totalCount = getTotalCount(con, buf.toString());
	 * 
	 * if(totalCount > 0) { return true; } else { return false; } } catch
	 * (NamingException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * throw e; } catch (SQLException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace(); throw e; } finally { try { if (con != null)
	 * con.close(); } catch (SQLException e) {} } }
	 */
	private void updateEmployeeRole(Connection con, String id, String role,
			DASCommonDO commonDO) throws SQLException, DASException {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.REGULAR_AUTH_TBL set ");
		buf.append("\n 	ROLE = ? ,   ");
		buf.append("\n 	MODRID = ?, ");
		buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where USER_ID = ?	");

		PreparedStatement stmt = null;
		try {

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, role);
			stmt.setString(++index, commonDO.getUserId());

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);

			stmt.setString(++index, id);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}
		} catch (SQLException e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, stmt, null);
		}

	}

	/*
	 * private void insertEmployeeRole(Connection con, EmployeeDASRoleDO roleDO,
	 * DASCommonDO commonDO) throws SQLException { StringBuffer buf = new
	 * StringBuffer(); buf.append(
	 * " insert into DAS.REGULAR_AUTH_TBL(USER_ID, ROLE, REGRID, REG_DT, MODRID, MOD_DT) 	\n"
	 * ); buf.append(" values(?, ?, ?, ?, ?, ?)																\n");
	 * 
	 * PreparedStatement stmt = null; try {
	 * 
	 * stmt = con.prepareStatement(buf.toString());
	 * 
	 * int index = 0; stmt.setString(++index, roleDO.getUserId());
	 * stmt.setString(++index, roleDO.getRole()); stmt.setString(++index,
	 * commonDO.getUserId());
	 * 
	 * String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
	 * stmt.setString(++index, dateTime); stmt.setString(++index,
	 * commonDO.getUserId()); stmt.setString(++index, dateTime);
	 * 
	 * stmt.executeUpdate(); } catch (SQLException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * throw e; } finally { try { if (stmt != null) stmt.close(); } catch
	 * (SQLException e) {} }
	 * 
	 * }
	 */
	private int selectNonEmployeeRoleHistoryMaxSeq(String userId)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select  ");
		buf.append("\n 	max(SEQ)   ");
		buf.append("\n from DAS.OUTSIDER_HIST_TBL ");
		buf.append("\n where OUT_USER_ID = ?	");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectNonEmployeeRoleHistoryMaxSeq######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return 0;

			}
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	private void insertAuthInfo(Connection con, AuthDO authDO,
			DASCommonDO commonDO) throws SQLException {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.ROLE_TBL( ");
		buf.append("\n 	AUTH_CD, ");
		buf.append("\n 	ROLE_CD, ");
		buf.append("\n 	OK_YN, ");
		buf.append("\n 	REGRID, ");
		buf.append("\n 	REG_DT, ");
		buf.append("\n 	MODRID, ");
		buf.append("\n 	MOD_DT ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?, ?) ");

		PreparedStatement stmt = null;
		try {

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, authDO.getAuth());
			stmt.setString(++index, authDO.getRole());
			stmt.setString(++index, authDO.getNewAuthYn());
			stmt.setString(++index, commonDO.getUserId());

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, commonDO.getUserId());
			stmt.setString(++index, dateTime);

			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, stmt, null);
		}

	}

	private void updateAuthInfo(Connection con, AuthDO authDO,
			DASCommonDO commonDO) throws SQLException, DASException {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.ROLE_TBL set  ");
		buf.append("\n 	OK_YN = ?,  ");
		buf.append("\n 	MODRID = ?, ");
		buf.append("\n 	MOD_DT = ? ");
		buf.append("\n where AUTH_CD = ? ");
		buf.append("\n 	and ROLE_CD = ?	 ");

		PreparedStatement stmt = null;
		try {

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, authDO.getNewAuthYn());
			stmt.setString(++index, commonDO.getUserId());

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, authDO.getAuth());
			stmt.setString(++index, authDO.getRole());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

		} catch (SQLException e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, stmt, null);
		}

	}

	private boolean isThereAuthInfo(AuthDO authDO) throws Exception {
		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) from DAS.ROLE_TBL where AUTH_CD = '"
				+ authDO.getAuth() + "' and ROLE_CD = '" + authDO.getRole()
				+ "'  \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereAuthInfo######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}

	private String getOutUserID() throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	value(max(INTEGER(substr(SBS_USER_ID, 4, 4))), 0) ");
		buf.append("\n from DAS.USER_INFO_TBL ");
		buf.append("\n where substr(SBS_USER_ID, 2, 2) = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getOutUserID######## con : " + con);
			String YY = CalendarUtil.getToday().substring(2, 4);

			stmt = con.prepareStatement(buf.toString());
			stmt.setString(1, YY);

			rs = stmt.executeQuery();

			rs.next();

			int nextSeq = rs.getInt(1) + 1;

			String paddMax = StringUtils.left(String.valueOf(nextSeq), 4, '0');

			String newUserId = DASBusinessConstants.SBS_USER_ID_PREFIX + YY
					+ paddMax;

			return newUserId;

		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * 화면 권한코드를 조회한다.
	 * 
	 * @param screenNo
	 *            화면번호
	 * @param commonDO
	 *            우리시스템의 사용자 공통정보
	 * @return 권한여부코드
	 * @throws Exception 
	 */
	public String selectEncryptPasswd(String passwd) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select encrypt('"
				+ passwd
				+ "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)) AS PASSWORD   ");
		buf.append("\n from sysibm.sysdummy1  ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEncryptPasswd######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, passwd);

			rs = stmt.executeQuery();

			rs.next();

			return rs.getString(1);
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * 계열사 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectSubEmployeeRoleList(SubsidiaryinfoDO condition)
			throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(UserRoleStatement.selectSubEmployeeRoleListQuery(condition,
				DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                            	\n");
		buf.append(" WITH UR	 \n");

		// Page에 따른 계산을 한다.
		int page = condition.getPage();
		if (page == 0) {
			page = 1;
		}

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSubEmployeeRoleList######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con,
					UserRoleStatement.selectSubEmployeeRoleListQuery(condition,
							DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			// 디스플레이할 페이지의 시작 row와 끝 row를 계산한다.
			int endNum = page
					* DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT;
			int startNum = endNum
					- (DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT - 1);

			int index = 0;
			stmt.setInt(++index, startNum);
			stmt.setInt(++index, endNum);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				SubsidiaryinfoDO item = new SubsidiaryinfoDO();
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));
				item.setMobile(rs.getString("MOBILE"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));

				resultList.add(item);
			}

			int totalPageCount = totalCount
					/ DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT
					+ (totalCount
							% DASBusinessConstants.PageRowCount.USER_ROLE_ROW_COUNT != 0 ? 1
									: 0);

			// 검색된 List를 셋팅한다.
			pageDO.setPageItems(resultList);
			// 계산된 총 Page 수를 셋팅한다.
			pageDO.setTotalPageCount(totalPageCount);

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

		throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	public List selectSubEmployeeRole(String perRegNo, String userID)
			throws Exception {
		String query = UserRoleStatement.selectOutEmployeeRoleQuery(perRegNo,
				userID);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSubEmployeeRole######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			// stmt.setString(++index, perRegNo);
			stmt.setString(++index, userID); // MHCHOI 0106
			// stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if (rs.next()) {
				SubsidiaryinfoDO item = new SubsidiaryinfoDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setMobile(rs.getString("MOBILE"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));
				item.setEmployee_yn(rs.getString("APPROVE_YN"));
				resultList.add(item);
				return resultList;
			} else {
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;

			}
		}  catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 테이블에 존재하지 않는 특정계열사직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            계열사 주민번호
	 * 
	 * @return List NonEmployeeDASRoleDO 계열사 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectSubEmployeeRoleForNotExist(String perRegNo)
			throws Exception {
		PageDO pageDO = new PageDO();

		String query = UserRoleStatement
				.selectSubEmployeeRoleForNotExistQuery(perRegNo);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSubEmployeeRoleForNotExist######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if (rs.next()) {
				SubsidiaryinfoDO item = new SubsidiaryinfoDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setMobile(rs.getString("MOBILE"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));
				item.setEmployee_yn(rs.getString("EMPLOYEE_YN"));
				resultList.add(item);
				return resultList;
			} else {
				return resultList;

			}
		}  catch (Exception e) {
			logger.error(query);

		throw e;
		} finally {
			release(rs, stmt, con);
		}
	}
	/**
	 *  이미 존재하는 사용자인지를 검증한다.
	 * @param perRegNo 외부직원의 주민번호
	 * 
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean isThereSubEmployeeRole(String perRegNo) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.USER_INFO_TBL where PER_REG_NO = '"
				+ perRegNo + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereSubEmployeeRole######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(null, null, con);
		}
	}

	/**
	 * 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selecEmployeeRoleList(EmployeeInfoDO condition)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectEmployeeListQuery(condition));

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecEmployeeRoleList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setPassword(rs.getString("password"));
				item.setMobile(rs.getString("mobile"));
				item.setCocd(rs.getString("cocd"));
				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setDept_cd(rs.getString("dept_cd"));
				item.setPgm_nm(rs.getString("TITLE"));
				item.setPgm_id(rs.getInt("pgm_id"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN").trim());
				item.setVlddt_end(rs.getString("VLDDT_END").trim());
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setEmployee_type(rs.getString("employee_type"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setAcct_nm(rs.getString("ACCT_NM"));
				item.setUser_num(rs.getString("user_num"));
				item.setDelete_yn(rs.getString("delete_yn"));
				item.setAcct_code(rs.getString("acct_code"));
				item.setReg_nm(rs.getString("REG_NM"));
				item.setMod_nm(rs.getString("APPROVE_NM"));
				item.setReg_dt(rs.getString("reg_Dt"));
				item.setMonitor_cd(rs.getString("MONITOR_ROLE"));
				item.setMonitor_nm(rs.getString("MONITOR_NM"));
				resultList.add(item);

			}

			return resultList;
		} catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		}  finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * MyPage 직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectMyEmployeeRoleList(EmployeeInfoDO condition)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectMyEmployeeListQuery(condition));

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectMyEmployeeRoleList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setPassword(rs.getString("password"));
				item.setMobile(rs.getString("mobile"));
				item.setCocd(rs.getString("cocd"));
				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setDept_cd(rs.getString("dept_cd"));
				item.setPgm_nm(rs.getString("TITLE"));
				item.setPgm_id(rs.getInt("pgm_id"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN").trim());
				item.setVlddt_end(rs.getString("VLDDT_END").trim());
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setEmployee_type(rs.getString("employee_type"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setAcct_nm(rs.getString("ACCT_NM"));
				item.setUser_num(rs.getString("user_num"));
				item.setDelete_yn(rs.getString("delete_yn"));
				item.setAcct_code(rs.getString("acct_code"));
				item.setReg_nm(rs.getString("REG_NM"));
				item.setMod_nm(rs.getString("APPROVE_NM"));
				item.setReg_dt(rs.getString("reg_Dt"));
				resultList.add(item);

			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 특정 직원 정보를 조회한다.
	 * 
	 * @param userid
	 *           사용자id
	 * @return
	 * @throws Exception 
	 */
	public EmployeeInfoDO selecEmployeeInfo(String userid) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectEmployeeInfoQuery(userid));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecEmployeeInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			EmployeeInfoDO item = new EmployeeInfoDO();

			if (rs.next()) {
				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setPassword(rs.getString("password"));
				item.setMobile(rs.getString("mobile"));
				item.setCocd(rs.getString("cocd"));
				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setDept_cd(rs.getString("dept_cd"));
				item.setPds_pgm_id(rs.getString("pds_pgm_id"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setEmployee_type(rs.getString("employee_type"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setUser_num(rs.getString("user_num"));
				item.setDelete_yn(rs.getString("delete_yn"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setAcct_code(rs.getString("acct_code"));
				item.setOut_sys(rs.getString("out_sys"));
				item.setNewPassword(item.getPassword());
			}

			return item;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		}  finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 특정 직원 정보를 조회한다.(사번
	 * 
	 * @param user_no
	 *            사번
	 * @return
	 * @throws Exception 
	 */
	public EmployeeInfoDO selecEmployeeInfo2(String user_no)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectEmployeeInfoQuery2(user_no));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selecEmployeeInfo2######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			EmployeeInfoDO item = new EmployeeInfoDO();

			if (rs.next()) {
				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setPassword(rs.getString("password"));
				item.setMobile(rs.getString("mobile"));
				item.setCocd(rs.getString("cocd"));
				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setDept_cd(rs.getString("dept_cd"));
				item.setPds_pgm_id(rs.getString("pds_pgm_id"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setEmployee_type(rs.getString("employee_type"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setUser_num(rs.getString("user_num"));
				item.setDelete_yn(rs.getString("delete_yn"));
				item.setEmployee_yn(rs.getString("employee_yn"));
				item.setAcct_code(rs.getString("acct_code"));
				item.setOut_sys(rs.getString("out_sys"));
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
	 * 테이블에 존재하지 않는 특정 직원의 정보를 조회한다.
	 * 
	 * @param perRegNo
	 *            직원의 주민번호
	 * 
	 * @return EmployeeDASRoleDO 직원 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List selectEmployeeRoleForNotExist(String perRegNo)
			throws Exception {
		PageDO pageDO = new PageDO();

		String query = UserRoleStatement
				.selectEmployeeRoleForNotExistQuery(perRegNo);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEmployeeRoleForNotExist######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;
			stmt.setString(++index, perRegNo);
			stmt.setString(++index, perRegNo);

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if (rs.next()) {
				EmployeeDASRoleDO item = new EmployeeDASRoleDO();
				item.setPer_reg_no(rs.getString("PER_REG_NO"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				item.setW_Dept(rs.getString("W_DEPT"));
				item.setPassword(rs.getString("PASSWORD"));
				item.setRole(rs.getString("ROLE"));
				item.setPosition(rs.getString("POSITION"));
				item.setApprove_yn(rs.getString("APPROVE_YN"));
				item.setMobile(rs.getString("MOBILE"));
				item.setSubsi_tel(rs.getString("SUBSI_TEL"));
				item.setEmployee_yn(rs.getString("EMPLOYEE_YN"));
				resultList.add(item);
				return resultList;
			} else {
				return resultList;

			}
		} catch (Exception e) {
			logger.error(query);

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 */
	/*
	 * public int updateEmployeeRole(EmployeeInfoDO roleDO) throws DASException
	 * { //변경전 정보를 조회한다. //NonEmployeeDASRoleDO beforeRoleDO =
	 * selectOutEmployeeInfo(roleDO.getSbs_user_ID());
	 * 
	 * StringBuffer buf = new StringBuffer();
	 * buf.append("\n update DAS.USER_INFO_TBL set ");
	 * buf.append("\n 	VLDDT_BGN  = ?,  "); buf.append("\n 	VLDDT_END  = ?,  ");
	 * buf.append("\n 	USER_NM  = ?,  "); buf.append("\n 	PGM_ID  = ?,  ");
	 * buf.append("\n 	DEPT_CD = ?,  "); if(!roleDO.getPassword().equals("")){
	 * buf.append("\n 	PASSWORD = 	?,  "); } buf.append("\n 	ROLE_CD = ? , ");
	 * buf.append("\n 	MOBILE = ?,  "); buf.append("\n 	POSITION = ?,  ");
	 * buf.append("\n 	EMPLOYEE_YN = ?,  "); buf.append("\n 	ACCT_CODE = ?,  ");
	 * buf.append("\n 	DELETE_YN = ?,  "); buf.append("\n 	COCD = ? , ");
	 * buf.append("\n 	MOD_DT = ? , "); buf.append("\n 	MOD_ID = ?  ");
	 * buf.append("\n where SBS_USER_ID = ?"); //MHCHOI OUT_USER_ID = ?
	 * 
	 * 
	 * boolean isUpdate = false; Connection con = null; PreparedStatement stmt =
	 * null;
	 * 
	 * try { con = DBService.getInstance().getConnection();
	 * con.setAutoCommit(false);
	 * 
	 * stmt = con.prepareStatement(buf.toString());
	 * 
	 * int index = 0; //String dateTime =
	 * CalendarUtil.getDateTime("yyyyMMddHHmmss"); stmt.setString(++index,
	 * roleDO.getVlddt_bgn()); stmt.setString(++index, roleDO.getVlddt_end());
	 * stmt.setString(++index, roleDO.getUser_nm()); stmt.setLong(++index,
	 * roleDO.getPgm_id()); stmt.setString(++index, roleDO.getDept_cd());
	 * if(!roleDO.getPassword().equals("")){
	 * if(roleDO.getEmployee_type().equals("003")){ String password =
	 * getPasswd(roleDO.getPassword()); stmt.setString(++index, password);
	 * }else{ stmt.setString(++index, roleDO.getPassword()); } }
	 * stmt.setString(++index, roleDO.getRole_cd()); stmt.setString(++index,
	 * roleDO.getMobile()); stmt.setString(++index, roleDO.getPosition());
	 * stmt.setString(++index, roleDO.getEmployee_yn());
	 * stmt.setString(++index,roleDO.getAcct_code()); stmt.setString(++index,
	 * roleDO.getDelete_yn()); stmt.setString(++index, roleDO.getCocd());
	 * stmt.setString(++index, roleDO.getReg_dt()); stmt.setString(++index,
	 * roleDO.getMod_id()); stmt.setString(++index, roleDO.getSbs_user_ID());
	 * int updateCount = stmt.executeUpdate();
	 * 
	 * if (logger.isDebugEnabled()) { logger.debug("[Update Count]" +
	 * updateCount); }
	 * 
	 * if(updateCount == 0) { //여기서 에러를 던진다. DASException exception = new
	 * DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다."); throw
	 * exception; }
	 * 
	 * //사용자 정보의 수정 내역을 등록한다. //insertNonEmployeeRoleHistory(con, beforeRoleDO);
	 * con.commit(); return updateCount; } catch (NamingException e) { // TODO
	 * 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception;
	 * 
	 * } catch (SQLException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { release(null, stmt, con);
	 * } }
	 */

	/**
	 * 직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int approveEmployeeRole(EmployeeInfoDO roleDO) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	APPROVE_YN = ?,  ");
		buf.append("\n 	APPROVE_STATUS = ? , ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MOD_ID = ?  ");
		// buf.append("\n 	cont = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######approveEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			// String[] user_id = roleDO.getSbs_user_ID().split(",");
			int updateCount = 0;
			// for(int i=0 ;i<user_id.length;i++ ){
			index = 0;
			// String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, roleDO.getApprove_yn());
			stmt.setString(++index, roleDO.getApprove_status());
			stmt.setString(++index, roleDO.getReg_dt());
			stmt.setString(++index, roleDO.getMod_id());
			// stmt.setString(++index, roleDO.getCont());
			stmt.setString(++index, roleDO.getSbs_user_ID());

			updateCount = stmt.executeUpdate();

			// }
			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}
			throw e;
		} finally {
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
	 * 직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int approveNonEmployeeRole(EmployeeInfoDO roleDO)
			throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	APPROVE_YN = ?,  ");
		buf.append("\n 	ROLE_CD = ?,  ");

		buf.append("\n 	APPROVE_STATUS = ?  ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MOD_ID = ?  ");

		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######approveNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String[] user_id = roleDO.getSbs_user_ID().split(",");
			int updateCount = 0;
			for (int i = 0; i < user_id.length; i++) {
				index = 0;
				String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
				stmt.setString(++index, "Y");
				stmt.setString(++index, "005");
				stmt.setString(++index, "002");
				stmt.setString(++index, dateTime);
				stmt.setString(++index, roleDO.getMod_id());
				stmt.setString(++index, user_id[i]);
				updateCount = stmt.executeUpdate();
				updateCount = 0 + updateCount;
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 직원의 Role 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	/*
	 * public int insertEmployeeRole(EmployeeInfoDO roleDO) throws DASException
	 * {
	 * 
	 * StringBuffer buf = new StringBuffer();
	 * buf.append("\n insert into DAS.TEMP_USER_INFO_TBL( ");
	 * buf.append("\n 	PER_REG_NO,  "); buf.append("\n 	SBS_USER_ID,  ");
	 * buf.append("\n 	PASSWORD,  "); buf.append("\n 	USER_NM,  ");
	 * buf.append("\n 	DEPT_CD,  "); buf.append("\n 	VLDDT_BGN, ");
	 * buf.append("\n 	VLDDT_END,  "); buf.append("\n 	POSITION,  ");
	 * buf.append("\n 	APPROVE_YN,  "); buf.append("\n 	ROLE_CD,  ");
	 * buf.append("\n 	MOBILE,  "); buf.append("\n 	PGM_ID,  ");
	 * buf.append("\n 	EMPLOYEE_TYPE , "); buf.append("\n 	SBSI_USER_YN,  ");
	 * buf.append("\n 	APPROVE_STATUS , "); buf.append("\n 	EMPLOYEE_YN,  ");
	 * buf.append("\n 	USER_NUM,  "); if(!roleDO.getAcct_code().equals("")){
	 * buf.append("\n 	ACCT_CODE , "); } buf.append("\n 	COCD , ");
	 * buf.append("\n 	DELETE_YN , "); buf.append("\n 	REG_DT , ");
	 * buf.append("\n 	REG_ID  "); buf.append("\n )  ");
	 * buf.append("\n values  ");
	 * 
	 * // 주민등록번호 길이를 검사하여 주민등록번호가 없는 사용자의 비밀번호는 "1111"로 설정한다
	 * if(roleDO.getPer_reg_no().length() < 13) {
	 * //buf.append("\n (?, ? , encrypt('"+DASBusinessConstants.INITIAL_PASSWD+
	 * "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ? , ?, ? , ? , ? , ?, ? , ? , ? , ?,?,? )  "
	 * ); if(roleDO.getAcct_code().equals("")){ buf.append(
	 * "\n (?, ? , 1111, ? , ? , ?, ? , ? , ? , ?, ? , ? , ?,?,?,?,?,?,?,?,?)  "
	 * ); }else{ buf.append(
	 * "\n (?, ? , 1111, ? , ? , ?, ? , ? , ? , ?, ? , ? , ?,?,?,?,?,?,?,?,?,?)  "
	 * ); } }else{
	 * //buf.append("\n (?, ? , encrypt('"+StringUtils.getInitialPasswd
	 * (roleDO.getPer_reg_no())+
	 * "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?,?,?)  "
	 * ); //buf.append("\n (?, ? , encrypt('"+roleDO.getPassword()+
	 * "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?,?,?)  "
	 * ); if(roleDO.getAcct_code().equals("")){ buf.append(
	 * "\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?)  "
	 * ); }else{ buf.append(
	 * "\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?)  "
	 * ); } } Connection con = null; PreparedStatement stmt = null; try { con =
	 * DBService.getInstance().getConnection(); con.setAutoCommit(false);
	 * 
	 * stmt = con.prepareStatement(buf.toString()); String
	 * userId=""; int index = 0; String dateTime =
	 * CalendarUtil.getDateTime("yyyyMMddHHmmss");
	 * if(roleDO.getEmployee_type().equals("003")) { userId =
	 * getNonUserID(roleDO.getCocd()); }
	 * if(roleDO.getEmployee_type().equals("001"
	 * )||roleDO.getEmployee_type().equals("002")){ userId =
	 * getUserID(roleDO.getCocd(),roleDO.getUser_num()); }
	 * if(roleDO.getPer_reg_no().length() < 13){ stmt.setString(++index,
	 * DASBusinessConstants.PER_REG_NO );} else{ stmt.setString(++index,
	 * roleDO.getPer_reg_no());} roleDO.setSbs_user_ID(userId);
	 * stmt.setString(++index, userId);
	 * if(roleDO.getEmployee_type().equals("003")){ String password =
	 * getPasswd(roleDO.getPassword()); stmt.setString(++index, password);
	 * }else{ stmt.setString(++index, roleDO.getPassword()); }
	 * stmt.setString(++index, roleDO.getUser_nm()); stmt.setString(++index,
	 * roleDO.getDept_cd()); stmt.setString(++index, roleDO.getVlddt_bgn());
	 * stmt.setString(++index, roleDO.getVlddt_end()); stmt.setString(++index,
	 * roleDO.getPosition()); stmt.setString(++index, "N");
	 * stmt.setString(++index, roleDO.getRole_cd()); stmt.setString(++index,
	 * roleDO.getMobile()); stmt.setInt(++index, roleDO.getPgm_id());
	 * stmt.setString(++index, roleDO.getEmployee_type());
	 * stmt.setString(++index, "N"); stmt.setString(++index, "001");
	 * stmt.setString(++index, roleDO.getEmployee_yn()); stmt.setString(++index,
	 * roleDO.getUser_num()); if(!roleDO.getAcct_code().equals("")){
	 * stmt.setString(++index, roleDO.getAcct_code()); } stmt.setString(++index,
	 * roleDO.getCocd()); stmt.setString(++index, "N"); stmt.setString(++index,
	 * dateTime); stmt.setString(++index, roleDO.getReg_id());
	 * 
	 * 
	 * 
	 * int itmp = stmt.executeUpdate();
	 * 
	 * //사용자 정보의 수정 내역을 등록한다. //insertNonEmployeeRoleHistory(con, roleDO);
	 * 
	 * con.commit(); return itmp;
	 * 
	 * } catch (NamingException e) { // TODO 자동 생성된 catch 블록
	 * e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception;
	 * 
	 * } catch (SQLException e) { // TODO 자동 생성된 catch 블록 e.printStackTrace();
	 * 
	 * if(con != null) { try { con.rollback(); } catch (SQLException e1) { //
	 * TODO 자동 생성된 catch 블록 e1.printStackTrace(); } }
	 * 
	 * DASException exception = new DASException(ErrorConstants.SYSTEM_ERR,
	 * "시스템 장애입니다.", e); throw exception; } finally { release(null, stmt, con);
	 * }
	 * 
	 * 
	 * }
	 */

	public int insertRealEmployeeRole(EmployeeInfoDO roleDO)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD,  ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END,  ");
		buf.append("\n 	POSITION,  ");
		buf.append("\n 	APPROVE_YN,  ");
		buf.append("\n 	ROLE_CD,  ");
		buf.append("\n 	MOBILE,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	EMPLOYEE_TYPE , ");
		buf.append("\n 	SBSI_USER_YN,  ");
		buf.append("\n 	APPROVE_STATUS , ");
		buf.append("\n 	EMPLOYEE_YN,  ");
		buf.append("\n 	USER_NUM,  ");

		buf.append("\n 	ACCT_CODE , ");

		buf.append("\n 	COCD , ");
		buf.append("\n 	DELETE_YN , ");
		buf.append("\n 	REG_DT , ");
		buf.append("\n 	REG_ID  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n ( ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?,?,?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertRealEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			stmt.setString(++index, roleDO.getPer_reg_no());
			stmt.setString(++index, roleDO.getSbs_user_ID());
			if (roleDO.getEmployee_type().equals("003")) {
				String password = getPasswd(roleDO.getPassword());
				stmt.setString(++index, password);
			} else {
				stmt.setString(++index, roleDO.getPassword());
			}
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getDept_cd());
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, "N");
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setInt(++index, roleDO.getPgm_id());
			stmt.setString(++index, roleDO.getEmployee_type());
			stmt.setString(++index, "N");
			stmt.setString(++index, "001");
			stmt.setString(++index, roleDO.getEmployee_yn());
			stmt.setString(++index, roleDO.getUser_num());
			stmt.setString(++index, roleDO.getAcct_code());
			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, "N");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, roleDO.getReg_id());

			if (roleDO.getEmployee_type().equals("003")) {
				insertOutEmployeeInfo(userId, roleDO.getUser_nm(),
						roleDO.getSbs_user_ID());
			}

			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;

		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}
			throw e;
		} finally {
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
	 * 직원의 Role 정보를 등록 한다
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public String insertEmployeeRole(EmployeeInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD,  ");
		buf.append("\n 	VLDDT_BGN, ");
		buf.append("\n 	VLDDT_END,  ");
		buf.append("\n 	POSITION,  ");
		buf.append("\n 	APPROVE_YN,  ");
		buf.append("\n 	ROLE_CD,  ");
		buf.append("\n 	MOBILE,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	EMPLOYEE_TYPE , ");
		buf.append("\n 	SBSI_USER_YN,  ");
		buf.append("\n 	APPROVE_STATUS , ");
		buf.append("\n 	EMPLOYEE_YN,  ");
		buf.append("\n 	USER_NUM,  ");
		if (!roleDO.getAcct_code().equals("")) {
			buf.append("\n 	ACCT_CODE , ");
		}
		buf.append("\n 	COCD , ");
		buf.append("\n 	DELETE_YN , ");
		buf.append("\n 	REG_DT , ");
		buf.append("\n 	REG_ID , ");
		buf.append("\n 	PDS_PGM_ID,  ");
		buf.append("\n 	OUT_SYS  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		// 주민등록번호 길이를 검사하여 주민등록번호가 없는 사용자의 비밀번호는 "1111"로 설정한다
		/*
		 * if(roleDO.getPer_reg_no().length() < 13) {
		 * //buf.append("\n (?, ? , encrypt('"
		 * +DASBusinessConstants.INITIAL_PASSWD+
		 * "', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ? , ?, ? , ? , ? , ?, ? , ? , ? , ?,?,? )  "
		 * ); if(roleDO.getAcct_code().equals("")){ buf.append(
		 * "\n (?, ? , 1111, ? , ? , ?, ? , ? , ? , ?, ? , ? , ?,?,?,?,?,?,?,?,?)  "
		 * ); }else{ buf.append(
		 * "\n (?, ? , 1111, ? , ? , ?, ? , ? , ? , ?, ? , ? , ?,?,?,?,?,?,?,?,?,?)  "
		 * ); } }else{
		 */
		// buf.append("\n (?, ? , encrypt('"+StringUtils.getInitialPasswd(roleDO.getPer_reg_no())+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?,?,?)  ");
		// buf.append("\n (?, ? , encrypt('"+roleDO.getPassword()+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)),  ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?,?,?)  ");
		if (roleDO.getAcct_code().equals("")) {
			buf.append("\n (? , ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?,?,?)  ");
		} else {
			buf.append("\n (? , ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?,?,?,?,?,?,?,?,?,?,?,?)    ");
		}
		// }
		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			if (roleDO.getAcct_code().equals("SA")
					|| roleDO.getAcct_code().equals("SB")
					|| roleDO.getAcct_code().equals("SC")
					|| roleDO.getEmployee_type().equals("003")) {
				userId = getNonUserID(roleDO.getCocd());
			}
			if (roleDO.getAcct_code().equals("RA")
					|| roleDO.getAcct_code().equals("RB")) {
				userId = getUserID(roleDO.getCocd(), roleDO.getUser_num());

			}

			/*
			if (roleDO.getPer_reg_no().length() < 13) {
				// stmt.setString(++index, DASBusinessConstants.PER_REG_NO );
				stmt.setString(++index, "");
			} else {
				stmt.setString(++index, roleDO.getPer_reg_no());
			}
			 */
			stmt.setString(++index, roleDO.getMobile());

			roleDO.setSbs_user_ID(userId);
			stmt.setString(++index, userId);
			if (roleDO.getAcct_code().equals("SA")
					|| roleDO.getAcct_code().equals("SB")
					|| roleDO.getAcct_code().equals("SC")
					|| roleDO.getEmployee_type().equals("003")
					|| roleDO.getAcct_code().equals("RB")) {
				String pw ="";
				if(roleDO.getAcct_code().equals("RB")){
					pw=roleDO.getPassword();
				}else{
					pw = roleDO.getMobile();
				}
				String password = getPasswd(pw);
				stmt.setString(++index, password);
			} else {

				JNI_Des hj = new JNI_Des();
				String password = "";
				password = hj.setEncryption(
						dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND",
						roleDO.getPassword());
				logger.debug("password    " + password);
				stmt.setString(++index, password);

				// stmt.setString(++index, roleDO.getPassword());

			}
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getDept_cd());
			if (roleDO.getVlddt_bgn().equals("")) {
				stmt.setString(++index, "");
				stmt.setString(++index, "");
			} else {
				stmt.setString(++index, roleDO.getVlddt_bgn());
				stmt.setString(++index, roleDO.getVlddt_end());
			}
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, "N");
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile().replace("-", ""));
			stmt.setInt(++index, 0);

			if (roleDO.getAcct_code().equals("SA")
					|| roleDO.getAcct_code().equals("SB")
					|| roleDO.getAcct_code().equals("SC")
					|| roleDO.getEmployee_type().equals("003")) {
				stmt.setString(++index, "003");
			} else {
				stmt.setString(++index, roleDO.getEmployee_type());
			}
			stmt.setString(++index, "N");
			stmt.setString(++index, "1");
			stmt.setString(++index, roleDO.getEmployee_yn());
			stmt.setString(++index, roleDO.getUser_num());
			if (!roleDO.getAcct_code().equals("")) {
				stmt.setString(++index, roleDO.getAcct_code());
			}
			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, "N");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, roleDO.getReg_id());
			stmt.setString(++index, roleDO.getPds_pgm_id());

			if (roleDO.getPds().equals("Y") || roleDO.getNds().equals("N")) {
				stmt.setString(++index, "001");
			} else if (roleDO.getPds().equals("N")
					|| roleDO.getNds().equals("Y")) {
				stmt.setString(++index, "002");
			} else if (roleDO.getPds().equals("Y")
					|| roleDO.getNds().equals("Y")) {
				stmt.setString(++index, "003");
			} else {
				stmt.setString(++index, "");
			}
			// 비직원일경우 보증인/신청자정보를 집어넣는다.
			if (roleDO.getAcct_code().equals("SA")
					|| roleDO.getAcct_code().equals("SB")
					|| roleDO.getAcct_code().equals("SC")
					|| roleDO.getEmployee_type().equals("003")) {
				insertOutEmployeeInfo(userId, roleDO.getUser_nm(),
						roleDO.getReg_id());
			}
			int itmp = stmt.executeUpdate();

			// userId
			con.commit();
			return userId;

		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;

		} finally {
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
	 * 비직원의 Role 정지 한다.
	 * 
	 * @param roleDO
	 *            직원 Role 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 * 
	 */
	public int stopOutEmployeeRole(NonEmployeeDASRoleDO roleDO)
			throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_END = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######stopOutEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateToTime = CalendarUtil.getDateTime("yyyyMMdd");
			stmt.setString(++index, dateToTime);
			stmt.setString(++index, roleDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.

			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}
			throw e;
		} finally {
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
	 * 부서 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            부서 정보가 포함되어 있는 DataObject
	 * @param
	 * @throws Exception 
	 */
	public int insertDepInfo(DepInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DEP_INFO_TBL( ");
		buf.append("\n 	COCD,  ");
		buf.append("\n 	DEPT_CD, ");
		buf.append("\n 	POST_UNIT_CLF, ");
		buf.append("\n 	DEPT_NM,  ");
		buf.append("\n 	LVL,  ");
		buf.append("\n 	SEQ,  ");
		buf.append("\n 	SUP_HEAD_CD,  ");
		buf.append("\n 	SUP_HEAD_NM,  ");
		buf.append("\n 	SUP_HEAD_SEQ,  ");
		buf.append("\n 	SUP_HTPO_CD,  ");
		buf.append("\n 	SUP_HTPO_NM,  ");
		buf.append("\n 	SUP_HTPO_SEQ,  ");
		buf.append("\n 	DEPT_CHAP_EMP_NO  ");
		buf.append("\n 	use_yn  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?,  ?,?,?, ? , ?,?,?,?,?,?,?,?,? )  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertDepInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, roleDO.getDept_cd());
			stmt.setString(++index, roleDO.getPost_unit_clf());
			stmt.setString(++index, roleDO.getDept_nm());
			stmt.setString(++index, roleDO.getLvl());
			stmt.setInt(++index, roleDO.getSeq());
			stmt.setString(++index, roleDO.getSup_head_cd());
			stmt.setString(++index, roleDO.getSup_head_nm());
			stmt.setInt(++index, roleDO.getSup_head_seq());
			stmt.setString(++index, roleDO.getSup_htpo_cd());
			stmt.setString(++index, roleDO.getSup_htpo_nm());
			stmt.setInt(++index, roleDO.getSup_htpo_seq());
			stmt.setString(++index, roleDO.getDept_chap_emp_no());
			stmt.setString(++index, "Y");

			//

			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 부서 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selecDepInfoList(DepInfoDO condition) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectDepInfoListQuery(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecDepInfoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();
				item.setConm(rs.getString("desc"));
				item.setCocd(rs.getString("cocd"));
				item.setDept_cd(rs.getString("DEPT_CD"));
				item.setDept_chap_emp_no(rs.getString("dept_chap_emp_no"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setLvl(rs.getString("lvl"));

				item.setPost_unit_clf(rs.getString("post_unit_clf"));
				item.setSeq(rs.getInt("seq"));
				item.setSup_head_cd(rs.getString("sup_head_cd"));
				item.setSup_head_nm(rs.getString("sup_head_nm"));
				item.setSup_head_seq(rs.getInt("sup_head_seq"));
				item.setSup_htpo_cd(rs.getString("sup_htpo_cd"));
				item.setSup_htpo_nm(rs.getString("sup_htpo_nm"));
				item.setSup_htpo_seq(rs.getInt("sup_htpo_seq"));
				item.setUse_yn(rs.getString("use_yn"));
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

	/**
	 * 본부코드와 국코드를 조회한다.
	 * 
	 * @param DepInfoDO
	 * @return
	 * @throws Exception 
	 */
	public List selecDepList(DepInfoDO condition) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectDepListQuery(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecDepList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setDept_cd(rs.getString("dept_cd"));
				item.setDept_nm(rs.getString("dept_nm"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 부서 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selecDepInfo() throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectDepInfoQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecDepInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setDept_cd(rs.getString("DEPT_CD"));

				item.setDept_nm(rs.getString("dept_nm"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 회사소속 부서정보 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectCocdInfo(String cocd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectDepInfoQuery(cocd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCocdInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setDept_cd(rs.getString("DEPT_CD"));

				item.setDept_nm(rs.getString("dept_nm"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 부서 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateDepInfo(DepInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DEP_INFO_TBL set ");
		buf.append("\n 	COCD = ?,  ");

		buf.append("\n 	POST_UNIT_CLF = ?, ");
		buf.append("\n 	DEPT_NM = ?,  ");
		buf.append("\n 	LVL = ?,  ");
		buf.append("\n 	SEQ = ?,  ");
		buf.append("\n 	SUP_HEAD_CD = ?, ");
		buf.append("\n 	SUP_HEAD_NM = ?, ");
		buf.append("\n 	SUP_HEAD_SEQ = ?,  ");
		buf.append("\n 	SUP_HTPO_CD = ?,  ");
		buf.append("\n 	SUP_HTPO_NM = ?, ");
		buf.append("\n 	SUP_HTPO_SEQ = ?, ");
		buf.append("\n 	DEPT_CHAP_EMP_NO = ? ");

		buf.append("\n where COCD = ?"); //
		buf.append("\n AND DEPT_CD = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDepInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, roleDO.getCocd());

			stmt.setString(++index, roleDO.getPost_unit_clf());
			stmt.setString(++index, roleDO.getDept_nm());
			stmt.setString(++index, roleDO.getLvl());
			stmt.setInt(++index, roleDO.getSeq());
			stmt.setString(++index, roleDO.getSup_head_cd());
			stmt.setString(++index, roleDO.getSup_head_nm());
			stmt.setInt(++index, roleDO.getSup_head_seq());
			stmt.setString(++index, roleDO.getSup_htpo_cd());
			stmt.setString(++index, roleDO.getSup_htpo_nm());
			stmt.setInt(++index, roleDO.getSup_htpo_seq());
			stmt.setString(++index, roleDO.getDept_chap_emp_no());

			stmt.setString(++index, roleDO.getWcocd());
			stmt.setString(++index, roleDO.getDept_cd());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역활 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selecroleInfoList(RoleInfoDO condition) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleInfoListQuery(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecroleInfoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;
			String beans = "";
			String beans2 = "";
			List resultList = new ArrayList();
			List resultList2 = new ArrayList();
			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setRole_group_nm(rs.getString("ROLE_GROUP_ID"));

				item.setRole_nm(rs.getString("DESC"));

				resultList.add(item);
			}

			return resultList;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 역활 목록을 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public List selectAuthorList(RoleInfoDO condition) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectAuthorList(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAuthorList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setRole_group_nm(rs.getString("DESC"));

				item.setRole_group_id(rs.getString("ROLE_GROUP_ID"));

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

	/**
	 * 역할 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateRoleInfo(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.ROLE_INFO_TBL set ");
		buf.append("\n 	 USE_YN = ?  ");

		buf.append("\n where ROLE_GROUP_ID = ?");
		buf.append("\n AND ROLE_ID = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRoleInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;

			String[] role_group_id = roleDO.getRole_group_id().split(",");
			String[] role_id = roleDO.getRole_id().split(",");
			String[] use_yn = roleDO.getUse_yn().split(",");
			for (int i = 0; i < role_group_id.length; i++) {
				index = 0;
				stmt.setString(++index, use_yn[i]);
				stmt.setString(++index, role_group_id[i]);
				stmt.setString(++index, role_id[i]);
				updateCount = stmt.executeUpdate();

			}
			updateCount += updateCount;

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 권한 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectAuthorInfoList(AuthorDO condition) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectAuthorInfoListQuery(condition,
				DASBusinessConstants.PageQueryFlag.NORMAL));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAuthorInfoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				AuthorDO item = new AuthorDO();

				item.setSup_head_nm(rs.getString("sup_head_nm"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setSbs_user_id(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setMobile(rs.getString("mobile"));
				item.setCo_cd(rs.getString("COCD"));

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

	/**
	 * 권한 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateAuthorInfo(AuthorDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	 ROLE_CD = ?  ");

		buf.append("\n where SBS_USER_ID = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateAuthorInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;

			String[] sbs_user_id = roleDO.getSbs_user_id().split(",");
			String[] role_cd = roleDO.getRole_cd().split(",");

			for (int i = 0; i < sbs_user_id.length; i++) {
				index = 0;
				stmt.setString(++index, role_cd[i]);
				stmt.setString(++index, sbs_user_id[i]);

				updateCount = stmt.executeUpdate();

			}

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			con.setAutoCommit(true);
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 비직원 정보를 목록 조회한다.
	 * 
	 * @param condition
	 *            조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List selectNonEmployeeRoleList(EmployeeInfoDO condition)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectNonEmployeeRoleListQuery(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectNonEmployeeRoleList######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setRole_nm(rs.getString("role_nm"));
				item.setPassword(rs.getString("password"));
				item.setMobile(rs.getString("mobile"));
				item.setCocd(rs.getString("cocd"));
				item.setSbs_user_ID(rs.getString("SBS_USER_ID"));
				// item.setSbs_user_ID( rs.getString("SBS_USER_ID"));
				item.setUser_nm(rs.getString("USER_NM"));
				item.setOut_user_nm(rs.getString("out_user_nm"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setPgm_nm(rs.getString("PROGRAM_NAME"));
				item.setVlddt_bgn(rs.getString("VLDDT_BGN"));
				item.setVlddt_end(rs.getString("VLDDT_END"));
				// item.setOut_user_id( rs.getString("out_user_id"));
				item.setApprove_status(rs.getString("APPROVE_STATUS"));
				item.setPds_pgm_id(rs.getString("PDS_PGM_ID"));

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

	/**
	 * 외부직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateNonEmployeeRole(NonEmployeeInfoDO roleDO)
			throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	USER_NM=?,  ");
		buf.append("\n 	DEPT_CD=?, ");
		buf.append("\n 	VLDDT_BGN=?,  ");
		buf.append("\n 	VLDDT_END=?,  ");
		buf.append("\n 	APPROVE_YN=?,  ");
		buf.append("\n 	APPROVE_STATUS=?,  ");
		buf.append("\n 	PASSWORD=?,  ");
		buf.append("\n 	ROLE_CD=?,  ");
		buf.append("\n 	MOBILE=?,  ");
		buf.append("\n 	PGM_ID=?  ");
		buf.append("\n where OUT_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, roleDO.getPer_reg_no());

			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getDept_cd());
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getApprove_yn());
			stmt.setString(++index, roleDO.getApprove_status());
			stmt.setString(++index, roleDO.getPassword());
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setInt(++index, roleDO.getPgm_id());
			stmt.setString(++index, roleDO.getOut_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;

		} finally {
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
	 * 외부직원의 Role 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int insertOutEmployeeRole(EmployeeInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD, ");
		buf.append("\n 	VLDDT_BGN,  ");
		buf.append("\n 	VLDDT_END,  ");
		buf.append("\n 	APPROVE_YN,  ");
		buf.append("\n 	APPROVE_STATUS,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	ROLE_CD,  ");
		buf.append("\n 	MOBILE,  ");
		buf.append("\n 	COCD,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n 	PGM_ID,  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		// 주민등록번호 길이를 검사하여 주민등록번호가 없는 사용자의 비밀번호는 "1111"로 설정한다
		if (roleDO.getPer_reg_no().length() < 13)
			// buf.append("\n (?, ? , encrypt('"+DASBusinessConstants.INITIAL_PASSWD+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)), ? , ? , ? , ? , ?, ? , ? , ? , ?, ? , ? , ? , ?)  ");
			buf.append("\n (?, ? , ?, ? , ? , ? , ?, ? , ? , ? , ?, ? , ? )  ");
		else
			// buf.append("\n (?, ? , encrypt('"+StringUtils.getInitialPasswd(roleDO.getPer_reg_no())+"', (select PASS_KEY from DAS.ERP_COM_KEY_TBL)), ? , ? , ? , ?, ? , ? , ? , ? , ?, ? , ? , ? , ?)  ");
			buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ? , ? , ? , ? , ? , ? )  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertOutEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;

			userId = getNonUserID(roleDO.getCocd());

			if (roleDO.getPer_reg_no().length() < 13)
				stmt.setString(++index, DASBusinessConstants.PER_REG_NO);
			else
				stmt.setString(++index, roleDO.getPer_reg_no());
			stmt.setString(++index, userId);
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getDept_cd());
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, "N");
			stmt.setString(++index, "001");
			stmt.setString(++index, roleDO.getPassword());
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setString(++index, roleDO.getCocd());
			stmt.setInt(++index, roleDO.getPgm_id());
			insertOutEmployeeInfo(userId, roleDO.getUser_nm(),
					roleDO.getSbs_user_ID());
			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;

		} finally {
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
	 * 외부직원의 Role 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int insertOutEmployeeInfo(String out_id, String user_nm,
			String sbs_id) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.OUTWORKER_INFO_TBL( ");
		buf.append("\n 	OUT_USER_ID,  ");
		buf.append("\n 	OUT_USER_NM,  ");
		buf.append("\n 	SBS_USER_ID  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ?,?)  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertOutEmployeeInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;

			//userId = getOutUserID();

			stmt.setString(++index, out_id);
			stmt.setString(++index, user_nm);
			stmt.setString(++index, sbs_id);

			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * ERP 발령 정보를 조회한다.
	 * 
	 * @param erpappointDO
	 * @return
	 * @throws Exception 
	 */
	public List selectERPAppointList(ErpAppointDO condition)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectERPAppointListQuery(condition));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectERPAppointList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			// updateERPAppoint();
			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				ErpAppointDO item = new ErpAppointDO();

				item.setCo_cd(rs.getString("co_cd"));
				item.setUser_no(rs.getString("USER_NO"));
				item.setOder_dd(rs.getString("oder_dd"));
				item.setOder_cd(rs.getString("oder_cd"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setDept_cd(rs.getString("dept_cd"));
				// item.setHand_phon(rs.getString("hand_phon"));
				item.setJob_cd(rs.getString("job"));
				item.setTeam_yn(rs.getString("team_yn"));
				item.setApdat_yn(rs.getString("ADAPT_YN"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setSeg_cd(rs.getString("seg_cd"));
				item.setSeg_nm(rs.getString("seg_nm"));
				item.setSeq_no(rs.getInt("seq_no"));
				item.setTitle(rs.getString("title"));
				item.setOcpn_gr_cd(rs.getString("OCPN_GR_CD"));
				item.setOder_nm(rs.getString("order_nm"));
				item.setOder_flag(rs.getString("order_flag"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * ERP 발령 정보를 갱신한다.
	 * 
	 * @param erpappointDO
	 * @return
	 * @throws Exception 
	 */
	public int updateERPAppoint2(String user_no) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.COM_ORDER_TBL set ");

		buf.append("\n 	adapt_YN= 'Y'");
		buf.append("\n 	where user_no=?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateERPAppoint2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, user_no);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * ERP 발령 정보를 갱신한다.(APPOInt_yn)
	 * 
	 * @param erpappointDO
	 * @return
	 * @throws Exception 
	 */
	public int updateERPAppointYN(String user_num) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.COM_ORDER_TBL set ");

		buf.append("\n  ADAPT_YN=?  ");
		buf.append("\n  WHERE USER_NO=?  ");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateERPAppointYN######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, "Y");
			stmt.setString(++index, user_num);
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * ERP 정보를 테이블에 등록한다.(벌크)
	 * 
	 * @param ErpAppointDO
	 * @return
	 * @throws Exception 
	 */
	public boolean insertERPUserInfo(List roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n INSERT INTO COM_ORDER_TBL ( ");
		buf.append("\n 	SEQ_NO,  ");
		buf.append("\n 	CO_CD,  ");
		buf.append("\n 	USER_NO,  ");
		buf.append("\n 	ODER_DD,  ");
		buf.append("\n 	ODER_CD,  ");
		buf.append("\n 	USER_NM , ");
		buf.append("\n 	SEG_CD , ");
		buf.append("\n 	SEG_NM , ");
		buf.append("\n 	DEPT_CD,  ");
		buf.append("\n 	DEPT_NM , ");
		buf.append("\n 	JOB,  ");
		buf.append("\n 	TITLE,  ");
		buf.append("\n 	OCPN_GR_CD,  ");
		buf.append("\n 	TEAM_YN,   ");
		buf.append("\n 	ADAPT_YN,   ");
		buf.append("\n 	SEARCH_YN ,  ");
		buf.append("\n 	ORDER_FLAG,   ");
		buf.append("\n 	ORDER_NM   ");
		buf.append("\n )  ");
		buf.append("\n values  ");
		buf.append("\n ( ?,?,?, ? , ?,?,?,? ,?,?,?,?,?,?,?,?,?,?)  ");

		boolean bResult = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertERPUserInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int itmp[];
			int index = 0;
			for (int i = 0; i < roleDO.size(); i++) {
				index = 0;
				ErpAppointDO pgminfoDO = (ErpAppointDO) roleDO.get(i);
				if(0!=pgminfoDO.getOder_seq()){
					stmt.setInt(++index, pgminfoDO.getOder_seq());
					//				logger.debug("getOder_seq" + pgminfoDO.getOder_seq());
					stmt.setString(++index, pgminfoDO.getCo_cd());
					stmt.setString(++index, pgminfoDO.getUser_no());
					stmt.setString(++index, pgminfoDO.getOder_dd());
					stmt.setString(++index, pgminfoDO.getOder_cd());
					stmt.setString(++index, pgminfoDO.getUser_nm());
					stmt.setString(++index, pgminfoDO.getSeg_cd());
					stmt.setString(++index, pgminfoDO.getSeg_nm());
					stmt.setString(++index, pgminfoDO.getDept_cd());
					stmt.setString(++index, pgminfoDO.getDept_nm());
					stmt.setString(++index, pgminfoDO.getJob_cd());
					stmt.setString(++index, pgminfoDO.getTitle());
					stmt.setString(++index, pgminfoDO.getOcpn_gr_cd());
					stmt.setString(++index, pgminfoDO.getTeam_yn());
					stmt.setString(++index, "N");
					stmt.setString(++index, pgminfoDO.getSearch_yn());
					stmt.setString(++index, pgminfoDO.getOder_flag());
					stmt.setString(++index, pgminfoDO.getOder_nm());
					stmt.addBatch();
				}
			}
			// int[] rInt = null;
			if (roleDO.size() > 0) {
				stmt.executeBatch();
				bResult = true;
			}

			con.setAutoCommit(true);
			return bResult;

		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}

	}

	private String getERPUserID(String cocd) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	value(max(INTEGER(substr(SBS_USER_ID, 4, 4))), 0) ");
		buf.append("\n from DAS.USER_INFO_TBL ");
		buf.append("\n  where sbs_user_id like '%P%' ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getERPUserID######## con : " + con);
			// String YY = CalendarUtil.getToday().substring(2, 4);

			stmt = con.prepareStatement(buf.toString());
			// stmt.setString(1, YY);

			rs = stmt.executeQuery();

			rs.next();
			String newUserId = null;
			int nextSeq = rs.getInt(1) + 1;
			String paddMax = StringUtils.left(String.valueOf(nextSeq), 4, '0');
			String usercode = null;

			newUserId = cocd + "P" + DASBusinessConstants.SBS_USER_ID_PREFIX
					+ paddMax;

			return newUserId;

		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	private String getUserID(String cocd, String user_num)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	value(max(substr(SBS_USER_ID, 4, 4)), 0) ");
		buf.append("\n from DAS.USER_INFO_TBL ");
		buf.append("\n where substr(SBS_USER_ID, 2, 2) = ? ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getUserID######## con : " + con);
			String YY = CalendarUtil.getToday().substring(2, 4);

			//stmt = con.prepareStatement(buf.toString());
			//stmt.setString(1, YY);

			//rs = stmt.executeQuery();

			//rs.next();

			//int nextSeq = rs.getInt(1) + 1;
			//String paddMax = StringUtils.left(String.valueOf(nextSeq), 4, '0');
			//String usercode = null;

			String newUserId = cocd + user_num;

			return newUserId;

		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * 사번 중복여부 조회한다.
	 * 
	 * @param USER_NUM
	 *            사번
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereERPEmployeeRole(String user_num, String cocd)
			throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.USER_INFO_TBL where USER_NUM = '"
				+ user_num + "' and cocd='" + cocd + "'\n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereERPEmployeeRole######## con : " + con);
			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		}catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(null, null, con);
		}
	}

	private String getNonUserID(String cocd) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	value(max(INTEGER(substr(SBS_USER_ID, 4, 4))), 0) ");
		buf.append("\n from DAS.USER_INFO_TBL ");
		buf.append("\n  where sbs_user_id like '%" + cocd + "P5%' ");
		buf.append("\n WITH UR	 ");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getNonUserID######## con : " + con);
			String YY = CalendarUtil.getToday().substring(2, 4);

			stmt = con.prepareStatement(buf.toString());
			//	 stmt.setString(1, YY);

			rs = stmt.executeQuery();

			rs.next();
			String newUserId = null;
			int nextSeq = rs.getInt(1) + 1;
			String paddMax = StringUtils.left(String.valueOf(nextSeq), 4, '0');
			String usercode = null;

			newUserId = cocd + "P" + "5" + paddMax;

			return newUserId;

		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * ERP 발령정보를 등록한다.
	 * 
	 * @param ErpAppointDO
	 * @return
	 * @throws Exception 
	 */
	public int[] insertERPAppoint(List roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD,  ");
		buf.append("\n 	APPROVE_YN,  ");
		buf.append("\n 	MOBILE,  ");
		buf.append("\n 	EMPLOYEE_TYPE , ");
		buf.append("\n 	APPROVE_STATUS , ");
		buf.append("\n 	POSITION , ");
		buf.append("\n 	EMPLOYEE_YN,  ");
		buf.append("\n 	DELETE_YN,  ");
		buf.append("\n 	COCD,  ");
		buf.append("\n 	USER_NUM , ");
		buf.append("\n 	role_cd,  ");
		buf.append("\n 	reg_id , ");
		buf.append("\n 	pgm_id , ");
		buf.append("\n 	acct_code  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?,  ?,?,?, ? , ?,?,?,? ,?,?,?,?,?,?,?,?)  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertERPAppoint######## con : " + con);
			con.setAutoCommit(false);
			stmt = con.prepareStatement(buf.toString());
			int itmp[];
			int index = 0;
			for (int i = 0; i < roleDO.size(); i++) {
				index = 0;
				ErpAppointDO pgminfoDO = (ErpAppointDO) roleDO.get(i);

				String user_id = null;

				if (!pgminfoDO.getUser_no().equals("")) {
					user_id = getUserID(pgminfoDO.getCo_cd(),
							pgminfoDO.getUser_no());
				} else {
					user_id = getERPUserID(pgminfoDO.getCo_cd());
				}


				stmt.setString(++index, user_id);
				stmt.setString(++index, " ");
				stmt.setString(++index, pgminfoDO.getUser_nm());

				stmt.setString(++index, pgminfoDO.getDept_cd());

				stmt.setString(++index, "Y");
				stmt.setString(++index, "");

				stmt.setString(++index, "001");
				stmt.setString(++index, "2");
				// stmt.setString(++index, pgminfoDO.getJob_cd());
				stmt.setString(++index, "77");
				stmt.setString(++index, "N");
				stmt.setString(++index, "N");
				stmt.setString(++index, pgminfoDO.getCo_cd());

				stmt.setString(++index, pgminfoDO.getUser_no());

				stmt.setString(++index, "001");
				stmt.setString(++index, "D080009");

				stmt.setInt(++index, 0);
				stmt.setString(++index, "RA");
				stmt.addBatch();
				// updateERPAppointYN(pgminfoDO.getUser_no());
			}
			int[] rInt = null;
			if (roleDO.size() > 0)
				rInt = stmt.executeBatch();

			con.commit();
			return rInt;

		} catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * ERP 발령정보를 등록한다.
	 * 
	 * @param ErpAppointDO
	 * @return
	 * @throws Exception 
	 */
	public int[] updateERPAppoint(List roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set  ");

		buf.append("\n 	USER_NM = ?,  ");
		buf.append("\n 	DEPT_CD = ?,  ");
		buf.append("\n 	MOBILE = ?,  ");
		buf.append("\n 	DELETE_YN = ?,  ");
		buf.append("\n 	COCD = ?,  ");

		buf.append("\n where user_num = ?  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateERPAppoint######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int itmp[];
			int index = 0;
			for (int i = 0; i < roleDO.size(); i++) {
				index = 0;
				ErpAppointDO pgminfoDO = (ErpAppointDO) roleDO.get(i);

				String user_id = null;

				stmt.setString(++index, pgminfoDO.getUser_nm());
				stmt.setString(++index, pgminfoDO.getDept_cd());
				stmt.setString(++index, pgminfoDO.getHand_phon());
				stmt.setString(++index, pgminfoDO.getOder_flag());
				stmt.setString(++index, pgminfoDO.getCo_cd());
				stmt.setString(++index, pgminfoDO.getUser_no());
				stmt.addBatch();
				updateERPAppointYN(pgminfoDO.getUser_no());
			}
			int[] rInt = null;
			if (roleDO.size() > 0)
				rInt = stmt.executeBatch();

			con.commit();
			return rInt;

		} catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 패스워드 암호화
	 * 
	 * @param password 패스워드
	 * @return password 암호화된 패스워드
	 * @throws DASException
	 */
	public String getPasswd(String password) {
		MessageDigest md;
		// String message = "password";
		try {
			md = MessageDigest.getInstance("SHA-512");

			md.update(password.getBytes());
			byte[] mb = md.digest();
			String out = "";
			for (int i = 0; i < mb.length; i++) {
				byte temp = mb[i];
				String s = Integer.toHexString(new Byte(temp));
				while (s.length() < 2) {
					s = "0" + s;
				}
				s = s.substring(s.length() - 2);
				out += s;
			}
			return out;
		} catch (NoSuchAlgorithmException e) {
			logger.debug("ERROR: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 패스워드 암호화
	 * 
	 * @param password 패스워드
	 * @return password 암호화된 패스워드
	 * @throws DASException
	 */
	public String getPasswdByAD(String password) {

		JNI_Des hj = new JNI_Des();
		String newPassword = "";
		newPassword = hj.setEncryption(
				dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND",
				password);
		logger.debug("password    " + password);
		return password;
	}

	/**
	 * 패스워드를 초기화한다
	 * 
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public String updateInitPassword(String user_id) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	password = ?  ");

		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateInitPassword######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String password = getPasswd("000000");
			stmt.setString(++index, password);
			stmt.setString(++index, user_id);

			stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return "000000";
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 사용자id를 기준으로 pa등록할 정보를 구한다
	 * @param user_id 사용자id
	 * @throws Exception 
	 */

	public String selectPaEmployeeInfo(String user_id) throws Exception {

		String[] user = user_id.split(",");

		StringBuffer buf = new StringBuffer();

		buf.append(" \n  select ");
		buf.append(" \n   sbs_user_id  \n");
		buf.append(" \n   ,user_nm  \n");
		buf.append(" \n   ,user_num  \n");
		buf.append(" \n   ,per_reg_no  \n");
		buf.append(" \n   ,acct_code \n");
		buf.append(" \n   ,cocd \n");
		buf.append(" from user_info_tbl where sbs_user_id IN (\n");
		for (int i = 0; i < user.length; i++) {
			buf.append(" ? ");
			if (i < user.length - 1) {
				buf.append(" , ");
			}
		}
		buf.append(" ) \n");

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPaEmployeeInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			for (int i = 0; i < user.length; i++) {
				stmt.setString(++index, user[i]);
			}
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				PaUserInfoDO item = new PaUserInfoDO();

				item.setSs_num(rs.getString("per_reg_no"));
				item.setUser_id(rs.getString("sbs_user_id"));
				item.setEmp_num(rs.getString("user_num"));
				item.setUser_name(rs.getString("user_nm"));
				item.setAcct_code(rs.getString("acct_code"));
				item.setCocd(rs.getString("cocd"));

				resultList.add(item);

			}
			String _xml = "";
			if (resultList != null && resultList.size() > 0) {
				Iterator _iter = resultList.iterator();
				while (_iter.hasNext()) {
					PaUserInfoDOXML _do2 = new PaUserInfoDOXML();
					_do2.setDO(_iter.next());
					_xml = _xml + _do2.getSubXML();
				}
				_xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>"
						+ _xml;
				_xml = _xml + "</das>";
				if (logger.isDebugEnabled())
					logger.debug("_xml" + _xml);
			}
			return _xml;

		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}
	/**
	 * 사용자id를 기준으로 pa등록할 패스워드를 구한다
	 * @param user_id 사용자id
	 * @throws Exception 
	 */
	public String selectPaPasswordInfo(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(" \n  select ");
		buf.append(" \n   sbs_user_id  \n");
		buf.append(" \n   ,password \n");
		buf.append(" from user_info_tbl where sbs_user_id = ?");

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectPaPasswordInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				PaUserInfoDO item = new PaUserInfoDO();

				item.setUser_id(rs.getString("sbs_user_id"));
				item.setPassword(rs.getString("password"));

				resultList.add(item);

			}
			String _xml = "";
			if (resultList != null && resultList.size() > 0) {
				Iterator _iter = resultList.iterator();
				while (_iter.hasNext()) {
					PaUserPasswordInfoDOXML _do2 = new PaUserPasswordInfoDOXML();
					_do2.setDO(_iter.next());
					_xml = _xml + _do2.getSubXML();
				}
				_xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>"
						+ _xml;
				_xml = _xml + "</das>";
				if (logger.isDebugEnabled())
					logger.debug("_xml" + _xml);
			}
			return _xml;

		}  catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/*public String selectDelInfo(String user_id) throws DASException {

		StringBuffer buf = new StringBuffer();

		buf.append(" \n  select ");
		buf.append(" \n   sbs_user_id  \n");
		buf.append(" from user_info_tbl where sbs_user_id = ?");

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				PaUserInfoDO item = new PaUserInfoDO();

				item.setUser_id(rs.getString("sbs_user_id"));

				resultList.add(item);

			}
			String _xml = "";
			if (resultList != null && resultList.size() > 0) {
				Iterator _iter = resultList.iterator();
				while (_iter.hasNext()) {
					PaUserPasswordInfoDOXML _do2 = new PaUserPasswordInfoDOXML();
					_do2.setDO(_iter.next());
					_xml = _xml + _do2.getSubXML();
				}
				_xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>"
						+ _xml;
				_xml = _xml + "</das>";
				if (logger.isDebugEnabled())
					logger.debug("_xml" + _xml);
			}
			return _xml;

		} catch (NamingException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			DASException exception = new DASException(
					ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
			throw exception;
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();

			DASException exception = new DASException(
					ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
			throw exception;
		} finally {
			release(rs, stmt, con);
		}

	}
	 */
	/**
	 * 사용자id를 기준으로 사용자id와 직업유형코드를 구한다
	 * @param user_id 사용자id
	 * @throws Exception 
	 */
	public List selectUserInfo(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(" \n  select ");
		buf.append(" \n   sbs_user_id  \n");
		buf.append(" \n   ,acct_code  \n");
		buf.append(" from user_info_tbl where sbs_user_id = ?");

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectUserInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				PaUserInfoDO item = new PaUserInfoDO();

				item.setUser_id(rs.getString("sbs_user_id"));
				item.setAcct_code(rs.getString("acct_code"));

				resultList.add(item);

			}
			return resultList;

		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * TOKEN 생성을 위한 목록 조회한다.
	 * 
	 * @param user_id
	 *           사용자id
	 * @return TokenDO
	 * @throws Exception 
	 */
	public TokenDO selecTokenInfo(String user_id) throws Exception {
		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectTokenQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecTokenInfo######## con : " + con);
			//stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			int index = 0;

			stmt.setString(++index, user_id);
			rs = stmt.executeQuery();

			int indexCount = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			TokenDO item = new TokenDO();
			while (rs.next()) {
				item.setUser_id(rs.getString("sbs_user_id").trim());
				item.setUser_num(rs.getString("user_num"));
				item.setAcct_code(rs.getString("acct_code"));
				String personnum = rs.getString("per_reg_no");
				//String personnum = rs.getString("mobile");
				if(personnum.trim().equals("")){
					item.setPer_reg_no("0");
				}else{
					item.setPer_reg_no(personnum);
				}
				item.setSourceSYS("DAS");
				item.setCocd(rs.getString("COCD"));
				item.setPassword(rs.getString("password"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setRole_cd(rs.getString("role_cd"));
				item.setEnd_token_dd(rs.getString("VLDDT_END"));
				item.setApprove_yn(rs.getString("APPROVE_STATUS"));
				//item.setEnd_token_dd(dateTime);
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
	 * ERP 발령 정보를 로컬DB에 업데이트한다.
	 * 
	 * @param erpappointDO
	 * @return
	 * @throws Exception 
	 */
	public int updateErpUserInfo(ErpAppointDO pgminfoDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n	  COCD =?  ");
		buf.append("\n	, USER_NM =?");
		buf.append("\n	, DEPT_CD =?");
		buf.append("\n	, POSITION =?");
		buf.append("\n 	WHERE USER_NUM= ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateErpUserInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, pgminfoDO.getCo_cd());
			stmt.setString(++index, pgminfoDO.getUser_nm());
			stmt.setString(++index, pgminfoDO.getDept_cd());
			stmt.setString(++index, pgminfoDO.getJob_cd());
			stmt.setString(++index, pgminfoDO.getUser_no());

			int updateCount = stmt.executeUpdate();

			updateERPAppointYN(pgminfoDO.getUser_no());
			con.commit();
			// updateERPAppoint2(pgminfoDO.getUser_no());
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * ERP 발령 정보를 로컬DB에 업데이트한다.
	 * 
	 * @param erpappointDO
	 * @return
	 * @throws Exception 
	 */
	public int updateErpUserDelYN(String user_num) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n DELETE_YN = ? ");
		buf.append("\n where	USER_NUM= ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateErpUserDelYN######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, "Y");

			stmt.setString(++index, user_num);

			int updateCount = stmt.executeUpdate();

			updateERPAppointYN(user_num);
			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 사용자id를 기준으로 사번과, 발령코드를 구한다
	 * @param user_id 사용자id
	 * @throws Exception 
	 */
	public List selectErpUserInfo(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(" \n  select ");
		buf.append(" \n   user_no  \n");
		buf.append(" \n   ,oder_cd  \n");
		buf.append(" from das.COM_ORDER_TBL where sbs_user_id = ?");

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectErpUserInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				PaUserInfoDO item = new PaUserInfoDO();

				item.setUser_id(rs.getString("user_no"));
				item.setAcct_code(rs.getString("oder_cd"));

				resultList.add(item);

			}
			return resultList;

		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}

	}

	/**
	 * ERP 발령 정보를 조회한다.
	 * @param user_no 사번
	 * @param cocd 회사코드
	 * @return List
	 * @throws Exception 
	 */
	public List selectERPList(String user_no, String cocd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectERPListQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectERPList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, user_no);
			stmt.setString(++index, cocd);
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				ErpAppointDO item = new ErpAppointDO();

				item.setDept_cd(rs.getString("dept_cd"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 타 DB에서 사용자 정보를 받아온다.(신규)
	 * 
	 * @param EmployeeDASRoleDO
	 * @return
	 * @throws Exception 
	 * */
	public int insertOtherUserInfo(EmployeeInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.USER_INFO_TBL( ");
		buf.append("\n 	PER_REG_NO,  ");
		buf.append("\n 	SBS_USER_ID,  ");
		buf.append("\n 	PASSWORD,  ");
		buf.append("\n 	USER_NM,  ");
		buf.append("\n 	DEPT_CD,  ");
		buf.append("\n 	EMPLOYEE_TYPE , ");
		buf.append("\n 	APPROVE_STATUS , ");
		buf.append("\n 	EMPLOYEE_YN,  ");
		buf.append("\n 	USER_NUM,  ");
		buf.append("\n 	ACCT_CODE , ");
		buf.append("\n 	COCD , ");
		buf.append("\n 	reg_dt , ");
		buf.append("\n 	DELETE_YN , ");
		buf.append("\n 	role_cd , ");
		buf.append("\n 	VLDDT_BGN , ");
		buf.append("\n 	VLDDT_END  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? , ?, ?,?,?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertOtherUserInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			index = 0;
			stmt.setString(++index, infoDO.getPer_reg_no());
			stmt.setString(++index, infoDO.getSbs_user_ID());
			stmt.setString(++index, infoDO.getPassword());
			stmt.setString(++index, infoDO.getUser_nm());
			stmt.setString(++index, infoDO.getDept_cd());

			// RA,RB이면 정직원 그이외에는 비직원처리
			if (infoDO.getAcct_code().equals("RA")
					|| (infoDO.getAcct_code().equals("RB"))) {
				stmt.setString(++index, "001");
			} else {
				stmt.setString(++index, "003");
			}

			stmt.setString(++index, "1");

			// RA,RB면 Y 아니면 N
			if (infoDO.getAcct_code().equals("RA")
					|| (infoDO.getAcct_code().equals("RB"))) {
				stmt.setString(++index, "Y");
			} else {
				stmt.setString(++index, "N");
			}

			stmt.setString(++index, infoDO.getUser_num());
			stmt.setString(++index, infoDO.getAcct_code());
			stmt.setString(++index, infoDO.getCocd());
			stmt.setString(++index, dateTime);
			stmt.setString(++index, "N");
			if (infoDO.getAcct_code().equals("RA")||infoDO.getAcct_code().equals("RB")){
				stmt.setString(++index, "005");
			}else{
				stmt.setString(++index, "006");	
			}
			if (infoDO.getAcct_code().equals("RA")||infoDO.getAcct_code().equals("RB")){
				stmt.setString(++index, "");
				stmt.setString(++index, "");
			} else {
				stmt.setString(++index, infoDO.getVlddt_bgn());
				stmt.setString(++index, infoDO.getVlddt_end());
			}
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타 DB에서 사용자 정보를 받아온다(수정).
	 * 
	 * @param EmployeeDASRoleDO
	 * @return
	 * @throws Exception 
	 * */
	public int updateOtherUserInfo(EmployeeInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set   ");
		/*buf.append("\n 	PER_REG_NO = ?  ");
		// buf.append("\n 	,SBS_USER_ID =?  ");
		buf.append("\n 	,PASSWORD =?  ");
		buf.append("\n 	,USER_NM =?  ");
		buf.append("\n 	,DEPT_CD =? ");
		buf.append("\n 	,USER_NUM =?  ");
		buf.append("\n 	,ACCT_CODE = ? ");
		buf.append("\n 	,COCD =? ");*/
		buf.append("\n 	mobile =? ");
		buf.append("\n 	,DEPT_CD =? ");

		buf.append("\n where   ");
		buf.append("\n sbs_user_id = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOtherUserInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			//stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			index = 0;
			String mb = infoDO.getMobile().replaceAll("-", "");
			stmt.setString(++index, mb);
			stmt.setString(++index, infoDO.getDept_cd());
			stmt.setString(++index, infoDO.getSbs_user_ID());


			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타 DB에서 사용자 정보를 받아온다(수정).
	 * 
	 * @param EmployeeDASRoleDO
	 * @return
	 * @throws Exception 
	 * */
	public int updateOtherUserPassWd(EmployeeInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set   ");

		buf.append("\n 	PASSWORD =?  ");

		buf.append("\n where   ");

		buf.append("\n sbs_user_id = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOtherUserPassWd######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			//stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			index = 0;

			stmt.setString(++index, infoDO.getPassword());

			stmt.setString(++index, infoDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타 DB에서 부서 정보를 받아온다(수정).
	 * 
	 * @param DepInfoDO
	 * @return
	 * @throws Exception 
	 * */
	public int updateOtherUserInfo(DepInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DEP_INFO_TBL set   ");
		buf.append("\n 	COCD = ?  ");
		buf.append("\n 	,DEPT_CD =?  ");
		buf.append("\n 	,DEPT_CHAP_EMP_NO =?  ");
		buf.append("\n 	,DEPT_NM =? ");
		buf.append("\n 	,LVL =?  ");
		buf.append("\n 	,POST_UNIT_CLF = ? ");
		buf.append("\n 	,SEQ =? ");
		buf.append("\n 	,SUP_DEPT_CD = ?  ");
		buf.append("\n 	,SUP_DEPT_NM = ?  ");
		buf.append("\n 	,SUP_HEAD_CD =?  ");
		buf.append("\n 	,SUP_HEAD_NM =?  ");
		buf.append("\n 	,SUP_HEAD_SEQ =? ");
		buf.append("\n 	,SUP_HTPO_CD =?  ");
		buf.append("\n 	,SUP_HTPO_NM = ? ");
		buf.append("\n 	,SUP_HTPO_SEQ =? ");
		if(infoDO.getStatus().equalsIgnoreCase("c")) {
			buf.append("\n 	,USE_YN = 'Y' ");
		}
		buf.append("\n where   ");
		buf.append("\n DEPT_CD = ?  ");
		buf.append("\n and COCD = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateOtherUserInfo######## con : " + con);
			con.setAutoCommit(false);

			//stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			//index = 0;
			logger.debug("#####infoDO  "+infoDO);
			stmt.setString(++index, infoDO.getCocd());
			stmt.setString(++index, infoDO.getDept_cd());
			stmt.setString(++index, infoDO.getDept_chap_emp_no());
			stmt.setString(++index, infoDO.getDept_nm());
			stmt.setString(++index, infoDO.getLvl());
			stmt.setString(++index, infoDO.getPost_unit_clf());
			stmt.setInt(++index, infoDO.getSeq());
			stmt.setString(++index, infoDO.getSup_dept_cd());
			stmt.setString(++index, infoDO.getSup_dept_nm());
			stmt.setString(++index, infoDO.getSup_head_cd());
			stmt.setString(++index, infoDO.getSup_head_nm());
			stmt.setInt(++index, infoDO.getSup_head_seq());
			stmt.setString(++index, infoDO.getSup_htpo_cd());
			stmt.setString(++index, infoDO.getSup_htpo_nm());
			stmt.setInt(++index, infoDO.getSup_htpo_seq());
			stmt.setString(++index, infoDO.getDept_cd());
			stmt.setString(++index, infoDO.getCocd());
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타 DB에서 사용자 정보를 받아온다.(신규)
	 * 
	 * @param EmployeeDASRoleDO
	 * @return
	 * @throws Exception 
	 * */
	public int insertOtherDepInfo(DepInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DEP_INFO_TBL( ");
		buf.append("\n 	COCD  ");
		buf.append("\n 	,DEPT_CD  ");
		buf.append("\n 	,DEPT_CHAP_EMP_NO   ");
		buf.append("\n 	,DEPT_NM  ");
		buf.append("\n 	,LVL   ");
		buf.append("\n 	,POST_UNIT_CLF  ");
		buf.append("\n 	,SEQ  ");
		buf.append("\n 	,SUP_DEPT_CD   ");
		buf.append("\n 	,SUP_DEPT_NM   ");
		buf.append("\n 	,SUP_HEAD_CD   ");
		buf.append("\n 	,SUP_HEAD_NM   ");
		buf.append("\n 	,SUP_HEAD_SEQ  ");
		buf.append("\n 	,SUP_HTPO_CD   ");
		buf.append("\n 	,SUP_HTPO_NM  ");
		buf.append("\n 	,SUP_HTPO_SEQ  ");
		buf.append("\n 	,USE_YN  ");
		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? ,?,?,?,?)  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertOtherDepInfo######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			//stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			index = 0;
			stmt.setString(++index, infoDO.getCocd());
			stmt.setString(++index, infoDO.getDept_cd());
			stmt.setString(++index, infoDO.getDept_chap_emp_no());
			stmt.setString(++index, infoDO.getDept_nm());
			stmt.setString(++index, infoDO.getLvl());

			stmt.setString(++index, infoDO.getPost_unit_clf());
			stmt.setInt(++index, infoDO.getSeq());
			stmt.setString(++index, infoDO.getSup_dept_cd());
			stmt.setString(++index, infoDO.getSup_dept_nm());
			stmt.setString(++index, infoDO.getSup_head_cd());
			stmt.setString(++index, infoDO.getSup_head_nm());
			stmt.setInt(++index, infoDO.getSup_head_seq());
			stmt.setString(++index, infoDO.getSup_htpo_cd());
			stmt.setString(++index, infoDO.getSup_htpo_nm());
			stmt.setInt(++index, infoDO.getSup_htpo_seq());
			stmt.setString(++index, "Y");
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타시스템과 직원 정보를 동기화한다.
	 * 
	 * @param sbs_user_id 사용자id
	 * @return List
	 * @throws Exception 
	 */
	public List selectOtherEmployeeList(String sbs_user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectOtherEmployeeList());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOtherEmployeeList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, sbs_user_id);
			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				OtherDBuserInfoDO item = new OtherDBuserInfoDO();

				item.setAcct_code(rs.getString("acct_code"));
				item.setApprove_Status(rs.getString("approve_status"));
				item.setCocd(rs.getString("cocd"));
				item.setDelte_yn(rs.getString("delete_yn"));
				item.setDept_cd(rs.getString("dept_cd"));
				item.setPasword(rs.getString("password"));
				item.setPer_reg_no(rs.getString("per_reg_no"));
				item.setSbs_user_id(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setUser_num(rs.getString("user_num"));

				resultList.add(item);

			}

			return resultList;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 유저 id를 찾는다
	 * 
	 * @param user_no 사번
	 * @return
	 * @throws Exception 
	 */
	public EmployeeInfoDO selectUserId(String user_no) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selecUserId2());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectUserId######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, user_no);
			rs = stmt.executeQuery();
			String userid = "";
			int indexCount = 0;
			List resultList = new ArrayList();

			EmployeeInfoDO item = new EmployeeInfoDO();
			while (rs.next()) {

				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setPassword(rs.getString("password"));
				resultList.add(item);
				//userid = item.getSbs_user_ID();
			}

			return item;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		}  finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 유저 id를 찾는다
	 * 
	 * @param employeeRoleConditionDO
	 * @return
	 * @throws Exception 
	 */
	public EmployeeInfoDO selectUserId2(String per_reg_no) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selecUserId());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectUserId2######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, per_reg_no);
			rs = stmt.executeQuery();
			String userid = "";
			int indexCount = 0;
			List resultList = new ArrayList();

			EmployeeInfoDO item = new EmployeeInfoDO();
			while (rs.next()) {

				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setPassword(rs.getString("password"));
				resultList.add(item);

			}

			return item;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 유저 id를 찾는다
	 * 
	 * @param employeeRoleConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String selectDeptcd(String cocd, String dept_cd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selecUserId());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDeptcd######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, cocd);
			stmt.setString(++index, dept_cd);
			rs = stmt.executeQuery();
			String userid = "";
			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setSbs_user_ID(rs.getString("sbs_user_id"));

				resultList.add(item);
				userid = item.getSbs_user_ID();
			}

			return userid;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 타시스템과 부서 정보를 동기화한다.
	 * @param cocd 회사코드
	 * @param dept_cd 부서코드
	 * @return
	 * @throws Exception 
	 */
	public List selectOhterDepInfoList(String cocd, String dept_cd)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectOhterDepInfoList());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectOhterDepInfoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, cocd);
			stmt.setString(++index, dept_cd);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				OtherDBDeptInfoDO item = new OtherDBDeptInfoDO();
				item.setCocd(rs.getString("cocd"));
				item.setDept_cd(rs.getString("DEPT_CD"));
				item.setDept_chap_emp_no(rs.getString("dept_chap_emp_no"));
				item.setDept_nm(rs.getString("dept_nm"));
				item.setLvl(rs.getString("lvl"));
				item.setPost_unit_clf(rs.getString("post_unit_clf"));
				item.setSeq(rs.getInt("seq"));
				item.setSup_head_cd(rs.getString("sup_head_cd"));
				item.setSup_head_nm(rs.getString("sup_head_nm"));
				item.setSup_head_seq(rs.getInt("sup_head_seq"));
				item.setSup_htpo_cd(rs.getString("sup_htpo_cd"));
				item.setSup_htpo_nm(rs.getString("sup_htpo_nm"));
				item.setSup_htpo_seq(rs.getInt("sup_htpo_seq"));

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

	/**
	 * 직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateEmployeeRoleYN(EmployeeInfoDO roleDO) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	APPROVE_YN = ?,  ");
		buf.append("\n 	APPROVE_STATUS = ? , ");
		buf.append("\n 	DELETE_YN = ? , ");
		buf.append("\n 	MOD_DT = ?,  ");
		buf.append("\n 	MOD_ID = ?  ");

		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateEmployeeRoleYN######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			// String[] user_id = roleDO.getSbs_user_ID().split(",");
			// String[] approveyn = roleDO.getApprove_yn().split(",");
			// String[] deleteyn = roleDO.getDelete_yn().split(",");
			// String[] approvesta = roleDO.getApprove_status().split(",");
			int updateCount = 0;
			/*
			 * for(int i=0 ;i<user_id.length;i++ ){ index = 0; String dateTime =
			 * CalendarUtil.getDateTime("yyyyMMddHHmmss");
			 * 
			 * stmt.setString(++index, approveyn[i]);
			 * stmt.setString(++index,approvesta[i]);
			 * if(approvesta[i].equals("2")){
			 * 
			 * stmt.setString(++index,"N"); }else if(approvesta[i].equals("3")){
			 * stmt.setString(++index,"Y"); }
			 * 
			 * stmt.setString(++index, dateTime); stmt.setString(++index,
			 * roleDO.getMod_id());
			 * 
			 * stmt.setString(++index, user_id[i]);
			 * 
			 * 
			 * updateCount = stmt.executeUpdate();
			 * if(roleDO.getDelete_yn().equals("Y")){
			 * deleteApproveUser(roleDO.getDelete_yn()); }
			 * 
			 * }
			 */

			index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			if (roleDO.getApprove_status().equals("2")) {

				stmt.setString(++index, "Y");
			} else if (roleDO.getApprove_status().equals("3")) {
				stmt.setString(++index, "N");
			}
			stmt.setString(++index, roleDO.getApprove_status());
			if (roleDO.getApprove_status().equals("2")) {

				stmt.setString(++index, "N");
			} else if (roleDO.getApprove_status().equals("3")) {
				stmt.setString(++index, "N");
			}

			stmt.setString(++index, dateTime);
			stmt.setString(++index, roleDO.getMod_id());

			stmt.setString(++index, roleDO.getSbs_user_ID());

			updateCount = stmt.executeUpdate();
			if (roleDO.getDelete_yn().equals("Y")) {
				deleteApproveUser(roleDO.getDelete_yn());
			}
			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;

		}  finally {
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
	 * 직원의 Role 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateEmployeeRole(EmployeeInfoDO roleDO) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_BGN  = ?,  ");
		buf.append("\n 	VLDDT_END  = ?,  ");
		buf.append("\n 	USER_NM  = ?,  ");
		buf.append("\n 	PDS_PGM_ID  = ?,  ");
		buf.append("\n 	DEPT_CD = ?,  ");
		if (!roleDO.getPassword().equals("")) {
			buf.append("\n 	PASSWORD = 	?,  ");
		}
		buf.append("\n 	ROLE_CD = ? , ");
		buf.append("\n 	MOBILE = ?,  ");
		buf.append("\n 	POSITION = ?,  ");
		buf.append("\n 	EMPLOYEE_YN = ?,  ");
		buf.append("\n 	ACCT_CODE = ?,  ");
		buf.append("\n 	DELETE_YN = ?,  ");
		buf.append("\n 	COCD = ? , ");
		buf.append("\n 	MOD_DT = ? , ");
		buf.append("\n 	MOD_ID = ?,  ");
		buf.append("\n 	MONITOR_ROLE = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getPds_pgm_id());
			stmt.setString(++index, roleDO.getDept_cd());
			if (!roleDO.getPassword().equals("")) {
				if (roleDO.getAcct_code().equals(CodeConstants.Pacode.SA)
						|| roleDO.getAcct_code()
						.equals(CodeConstants.Pacode.SB)
						|| roleDO.getAcct_code()
						.equals(CodeConstants.Pacode.SC)
						|| roleDO.getEmployee_type().equals(
								CodeConstants.EmployeeGubun.NONEMPLOYEE)) {
					String password = getPasswd(roleDO.getPassword());
					stmt.setString(++index, password);
				} else {

					JNI_Des hj = new JNI_Des();
					String password = "";
					password = hj.setEncryption(
							dasHandler.getProperty("AD_CRYPTO_KEY"),
							"AESPWPND", roleDO.getPassword());
					logger.debug("password    " + password);
					stmt.setString(++index, password);

					// stmt.setString(++index, roleDO.getPassword());

				}
			}
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, roleDO.getEmployee_yn());
			stmt.setString(++index, roleDO.getAcct_code());
			if (roleDO.getDelete_yn().equals("")) {
				stmt.setString(++index, "N");
			} else {
				stmt.setString(++index, roleDO.getDelete_yn());
			}

			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, dateTime);
			stmt.setString(++index, roleDO.getMod_id());
			stmt.setString(++index, roleDO.getMonitor_cd());
			stmt.setString(++index, roleDO.getSbs_user_ID());


			if (roleDO.getAcct_code().equals(CodeConstants.Pacode.SA)
					|| roleDO.getAcct_code()
					.equals(CodeConstants.Pacode.SB)
					|| roleDO.getAcct_code()
					.equals(CodeConstants.Pacode.SC)) {

				updateNonEmployeeRole(roleDO);
			}

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		}  finally {
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
	 * 본부코드와 국코드를 조회한다.
	 * 
	 * @param cocd 회사코드
	 * @return
	 * @throws Exception 
	 */
	public List getSupHeadList(String cocd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectSupHeadQuery(cocd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getSupHeadList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setSup_head_cd(rs.getString("sup_head_cd"));
				item.setSup_head_nm(rs.getString("sup_head_nm"));

				resultList.add(item);
			}

			return resultList;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 부서를 조회한다.
	 * 
	 * @param deptcd 부서코드
	 * @return List
	 * @throws Exception 
	 */
	public List getDepinfo(String deptcd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectdepQuery(deptcd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getDepinfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setCocd(rs.getString("COCD"));
				item.setDept_cd(rs.getString("DEPT_CD"));
				item.setPost_unit_clf(rs.getString("POST_UNIT_CLF"));
				item.setDept_nm(rs.getString("DEPT_NM"));
				item.setLvl(rs.getString("LVL"));
				item.setSeq(rs.getInt("SEQ"));
				item.setSup_head_cd(rs.getString("SUP_HEAD_CD"));
				item.setSup_head_nm(rs.getString("SUP_HEAD_NM"));
				item.setSup_head_seq(rs.getInt("SUP_HEAD_SEQ"));
				item.setSup_htpo_cd(rs.getString("SUP_HTPO_CD"));
				item.setSup_htpo_nm(rs.getString("SUP_HTPO_NM"));
				item.setSup_htpo_seq(rs.getInt("SUP_HTPO_SEQ"));
				item.setDept_chap_emp_no(rs.getString("DEPT_CHAP_EMP_NO"));
				item.setSup_dept_cd(rs.getString("SUP_DEPT_CD"));
				item.setUse_yn(rs.getString("USE_YN"));
				item.setSup_dept_nm(rs.getString("SUP_DEPT_NM"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 부서코드와 부서명를 조회한다.
	 * 
	 * @param deptcd 부서코드
	 * @return List
	 * @throws Exception 
	 */
	public List getDepinfoList(String deptcd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectdepQuery(deptcd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getDepinfoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setDept_cd(rs.getString("dept_cd"));
				item.setDept_nm(rs.getString("dept_nm"));

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


	/**
	 * 부서코드와 부서명를 조회한다.
	 * @param deptcd     부서코드                                                                                                                                                                                          
	 * @return       list                                                                                                                                                                                       
	 * @throws Exception 
	 */
	public List getSupHeadList2(String deptcd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectSupHeadQuery2(deptcd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getSupHeadList2######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setSup_head_cd(rs.getString("DEPT_CD"));
				item.setSup_head_nm(rs.getString("DEPT_NM"));

				resultList.add(item);
			}

			return resultList;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 해당 유저의 부서정보를 가져온다
	 * 
	 * @param user_id
	 *            유저 아이디
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public String selectDepinfoForUser(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selecDepInfoForUserId());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDepinfoForUser######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, user_id);
			rs = stmt.executeQuery();
			String dep_cd = "";
			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setDept_cd(rs.getString("dept_cd"));

				resultList.add(item);
				dep_cd = item.getDept_cd();
			}

			return dep_cd;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 국코드와 국명를 조회한다.
	 * 
	 * @param cocd 회사코드
	 * @return List
	 * @throws Exception 
	 */
	public List getSupHtpoList(String cocd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectSupHtpoQuery(cocd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getSupHtpoList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				DepInfoDO item = new DepInfoDO();

				item.setSup_head_cd(rs.getString("DEPT_CD"));
				item.setSup_head_nm(rs.getString("DEPT_NM"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * 
	 * @param deptcd 부서코드
	 * @return List
	 * @throws Exception 
	 */
	public List getDepinfoForuserList(String deptcd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.getDepinfoForuserList(deptcd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getDepinfoForuserList######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setUser_num(rs.getString("user_num"));
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

	/**
	 * 프로그램별 승인 목록에서 사용자 사번과 직책을 구해온다
	 * @param dep_cd 부서코드
	 * @param user_nm 사용자명
	 * @return List
	 * @throws Exception 
	 */
	public List selecEmployeeListForApp(String dep_cd, String user_nm)
			throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectEmployeeListForApp(dep_cd, user_nm));

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecEmployeeListForApp######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setPosition(rs.getString("POSITION"));
				item.setUser_num(rs.getString("user_num"));

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

	/**
	 * 역활 정보를 목록 조회한다.
	 * 
	 * @param userid
	 *            사용자id
	 * @return
	 * @throws Exception 
	 */
	public String selectRoleForLogin(String userid) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selecttRoleForLoginQuery(userid));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectRoleForLogin######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			List resultList = new ArrayList();


			String role_cd="";
			while (rs.next()) {

				role_cd = rs.getString("role_Cd");
			}

			return role_cd;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}


	/**
	 * 역활에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 * @throws Exception 
	 */
	public List selectRoleGroupForLogin(String role_Cd) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleGroupForLogin(role_Cd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectRoleGroupForLogin######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setFunction_cd(rs.getString("FUNCTION_ID"));
				item.setRole_id(role_Cd);
				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 자신의 암호 정보를 수정한다
	 * 
	 * @param employeeRoleConditionDO
	 * @return
	 * @throws Exception 
	 */
	public int updatePasswd(EmployeeInfoDO roleDO) throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	VLDDT_BGN  = ?,  ");
		buf.append("\n 	VLDDT_END  = ?,  ");
		buf.append("\n 	USER_NM  = ?,  ");
		buf.append("\n 	PDS_PGM_ID  = ?,  ");
		buf.append("\n 	DEPT_CD = ?,  ");
		if (!roleDO.getPassword().equals("")) {
			buf.append("\n 	PASSWORD = 	?,  ");
		}
		buf.append("\n 	ROLE_CD = ? , ");
		buf.append("\n 	MOBILE = ?,  ");
		buf.append("\n 	POSITION = ?,  ");
		buf.append("\n 	EMPLOYEE_YN = ?,  ");
		buf.append("\n 	ACCT_CODE = ?,  ");
		buf.append("\n 	DELETE_YN = ?,  ");
		buf.append("\n 	COCD = ? , ");
		buf.append("\n 	MOD_DT = ? , ");
		buf.append("\n 	MOD_ID = ?  ");
		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updatePasswd######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, roleDO.getVlddt_bgn());
			stmt.setString(++index, roleDO.getVlddt_end());
			stmt.setString(++index, roleDO.getUser_nm());
			stmt.setString(++index, roleDO.getPds_pgm_id());
			stmt.setString(++index, roleDO.getDept_cd());
			if (!roleDO.getPassword().equals("")) {
				if (roleDO.getEmployee_type().equals("003")||roleDO.getAcct_code().equals(CodeConstants.Pacode.SA)||roleDO.getAcct_code().equals(CodeConstants.Pacode.SB)||roleDO.getAcct_code().equals(CodeConstants.Pacode.SC)||roleDO.getAcct_code().equals(CodeConstants.Pacode.RB)) {
					String password = getPasswd(roleDO.getPassword());
					stmt.setString(++index, password);
				} 
			}
			stmt.setString(++index, roleDO.getRole_cd());
			stmt.setString(++index, roleDO.getMobile());
			stmt.setString(++index, roleDO.getPosition());
			stmt.setString(++index, roleDO.getEmployee_yn());
			stmt.setString(++index, roleDO.getAcct_code());
			stmt.setString(++index, "N");
			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, dateTime);
			stmt.setString(++index, roleDO.getMod_id());
			stmt.setString(++index, roleDO.getSbs_user_ID());
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;		} finally {
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
	 * 비밀번호 맞는지여부를 판단한다,
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public String isRightPassWd(String user_id) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select password FROM  DAS.user_INFO_TBL where sbs_user_id = '"
				+ user_id + "' \n");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isRightPassWd######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			String orgpassword = "";
			int indexCount = 0;

			List resultList = new ArrayList();

			//			EmployeeInfoDO item = new EmployeeInfoDO();
			while (rs.next()) {

				orgpassword = rs.getString("password");
			}
			logger.debug("orgpassword [ " + orgpassword+"]");
			return orgpassword;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 계정유형을 판단
	 * 
	 * @param user_id
	 *            사용자 id
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public String typeOfUser(String user_id) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select acct_code FROM  DAS.user_INFO_TBL where sbs_user_id = '"
				+ user_id + "' \n");

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######typeOfUser######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			String acct_code = "";
			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setAcct_code(rs.getString("acct_code"));
				acct_code = item.getAcct_code();
			}
			logger.debug("acct_code   == " + acct_code);
			return acct_code;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 해당 유저의 프로그램 정보를 가져온다
	 * 
	 * @param user_id
	 *            유저 아이디
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public String selectPgminfoForUser(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectPgminfoForUser());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPgminfoForUser######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id.substring(1));
			rs = stmt.executeQuery();
			String pgm_id = "''";
			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setPds_pgm_id(rs.getString("pgm_id"));

				resultList.add(item);
				pgm_id += "," +"'"+ item.getPds_pgm_id()+"'";
			}

			return pgm_id;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}
	/**
	 * 해당 유저의 프로그램 정보를 가져온다
	 * 
	 * @param user_id
	 *            유저 아이디
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public String selectPgminfoForUser2(String user_id,String dep_cd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectPgminfoForUser2());

		// Page에 따른 계산을 한다.

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPgminfoForUser2######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, user_id.substring(1));
			stmt.setString(++index, dep_cd.trim());
			rs = stmt.executeQuery();
			String pgm_id = "''";
			int indexCount = 0;
			List resultList = new ArrayList();

			EmployeeInfoDO item = new EmployeeInfoDO();
			while (rs.next()) {

				item.setPds_pgm_id(rs.getString("pgm_id"));

				resultList.add(item);
				pgm_id += "," +"'"+ item.getPds_pgm_id()+"'";
			}

			return pgm_id;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 삭제시 승인정보역시 삭제한다
	 * 
	 * @param
	 * @param
	 * @throws Exception 
	 */
	public int deleteApproveUser(String user_no) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append(" delete from  DAS.APPROVE_INFO_TBL  \n");
		buf.append(" where APPROVE_USER_NUM = ?		 		\n");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteApproveUser######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			// stmt.setString(++index, passwd);
			stmt.setString(++index, user_no);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;

		} finally {
			release(null, stmt, con);
		}
	}

	/**
	 * 비직원의비밀번호를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateNonEmployeePasswd(EmployeeInfoDO roleDO)
			throws Exception {
		// 변경전 정보를 조회한다.
		// NonEmployeeDASRoleDO beforeRoleDO =
		// selectOutEmployeeInfo(roleDO.getSbs_user_ID());

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	PASSWORD = 	?  ");

		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateNonEmployeePasswd######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String password = getPasswd(roleDO.getNewPassword());
			stmt.setString(++index, password);

			stmt.setString(++index, roleDO.getReg_id());
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 정직원의비밀번호를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateEmployeePasswd(EmployeeInfoDO roleDO)
			throws Exception {
		
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set ");

		buf.append("\n 	PASSWORD = 	?  ");

		buf.append("\n where SBS_USER_ID = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateNonEmployeePasswd######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			//String password = getPasswd(roleDO.getNewPassword());
			stmt.setString(++index, roleDO.getNewPassword());

			stmt.setString(++index, roleDO.getReg_id());
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 비직원의  이름 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO
	 *            공통정보
	 * @throws Exception 
	 */
	public int updateNonEmployeeRole(EmployeeInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTWORKER_INFO_TBL set ");

		buf.append("\n 	out_user_nm = ?  ");


		buf.append("\n where out_user_id = ?"); // MHCHOI OUT_USER_ID = ?

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateNonEmployeeRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;


			int updateCount = 0;

			index = 0;

			stmt.setString(++index, roleDO.getUser_nm());

			stmt.setString(++index, roleDO.getSbs_user_ID());

			updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}



			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역활 권한 그룹신규로 신청.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public RoleInfoDO insertRole(RoleInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.code_tbl( ");
		buf.append("\n 	clf_cd,  ");
		buf.append("\n 	scl_Cd,  ");
		buf.append("\n 	clf_nm,  ");
		buf.append("\n 	desc,  ");
		buf.append("\n 	rmk_1,  ");
		buf.append("\n 	rmk_2, ");
		buf.append("\n 	reg_Dt,  ");
		buf.append("\n 	regrid,  ");
		buf.append("\n 	mod_dt,  ");
		buf.append("\n 	modrid,  ");
		buf.append("\n 	use_yn,  ");
		buf.append("\n 	gubun  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? )    ");

		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String newRoleCode = selectCodeNum();
			stmt.setString(++index, "A049");
			stmt.setString(++index, newRoleCode);
			stmt.setString(++index, "역활코드(das 2.0)");
			stmt.setString(++index, roleDO.getRole_nm());
			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, roleDO.getChennel());
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, "");
			stmt.setString(++index, "Y");
			stmt.setString(++index, "");

			int itmp = stmt.executeUpdate();
			roleDO.setRole_id(newRoleCode);
			// userId
			con.commit();
			return roleDO;

		}catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역활 권한 그룹신규로 신청.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertRoleGroup(RoleInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into ROLE_INFO_TBL( ");
		buf.append("\n 	ROLE_GROUP_ID,  ");
		buf.append("\n 	ROLE_ID,  ");
		buf.append("\n 	USE_YN,  ");
		buf.append("\n 	CHENNEL_CD,  ");
		buf.append("\n 	COCD  ");
		buf.append("\n )  ");
		buf.append("\n values  ");


		buf.append("\n (?, ?, ?, ?, ?)    ");

		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";


		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertRoleGroup######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;
			int itmp =0;
			for (int i =1 ; i<25 ; i++){
				index = 0;
				if(i <10){
					stmt.setString(++index, "00"+ i);
				}else if(i >=10){
					stmt.setString(++index, "0"+ i);
				}
				stmt.setString(++index,roleDO.getRole_id());
				stmt.setString(++index, "N");
				stmt.setString(++index,roleDO.getChennel());
				stmt.setString(++index,roleDO.getCocd());
				itmp = stmt.executeUpdate();
			}
			// userId
			con.commit();
			return itmp;

		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}
			throw e;
		} finally {
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
	 * 권한 코드 생성
	 * @return String 수집처 코드 증가값....
	 * @throws Exception 
	 */
	public String selectCodeNum() throws Exception
	{
		String result = "";

		String scl_cd = "";
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MAX(SCL_CD) as SCL_CD "); 
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'A049' ");
		//buf.append("\n and gubun='"+gubun+"' ");
		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCodeNum######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			if(rs.next())
			{
				scl_cd = rs.getString("SCL_CD");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;

			}
			String cd =scl_cd.substring(1, 3);
			logger.debug("scl_cd  1  ="+cd);
			logger.debug("cd    ="+cd);
			int scl= (Integer.parseInt(cd))+1;

			if(scl < 10){
				result = "00"+scl;
			}else {
				result =  "0"+scl;
			}


			return result;
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
	 * 역활 정보를 목록 조회한다.
	 * @return List
	 * @throws Exception 
	 */
	public List selecroleInfo() throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleInfoQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecroleInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setRole_id(rs.getString("scl_cd"));

				item.setRole_nm(rs.getString("DESC"));

				resultList.add(item);
			}

			return resultList;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}



	/**
	 * 부서 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateDepInfo2(DepInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DEP_INFO_TBL set ");

		buf.append("\n 	DEPT_NM = ?,  ");	
		buf.append("\n 	SUP_DEPT_CD = ?, ");
		buf.append("\n 	SUP_DEPT_NM = ? ");
		buf.append("\n where COCD = ?"); //
		buf.append("\n AND DEPT_CD = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateDepInfo2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			stmt.setString(++index, roleDO.getDept_nm());

			stmt.setString(++index, roleDO.getParent_dept_cd());
			stmt.setString(++index, roleDO.getParent_dept_nm());
			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, roleDO.getDept_cd());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 부서 정보를 등록 한다.
	 * 
	 * @param roleDO
	 *            부서 정보가 포함되어 있는 DataObject
	 * @param
	 * @throws Exception 
	 */
	public int insertDepInfo2(DepInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.DEP_INFO_TBL( ");
		buf.append("\n 	COCD,  ");
		buf.append("\n 	DEPT_CD, ");
		buf.append("\n 	DEPT_NM,  ");		
		buf.append("\n 	SUP_dept_NM,  ");
		buf.append("\n 	SUP_dept_cd,  ");
		buf.append("\n 	use_yn  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?,  ?,?,?, ? , ? )  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertDepInfo2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, roleDO.getDept_cd());
			stmt.setString(++index, roleDO.getDept_nm());
			stmt.setString(++index, roleDO.getParent_dept_nm());
			stmt.setString(++index, roleDO.getParent_dept_cd());
			stmt.setString(++index, "Y");

			//

			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return itmp;
		}  catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 부서 정보를 삭제한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int deleteDepInfo2(DepInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DEP_INFO_TBL set ");

		buf.append("\n 	USE_YN = ? ");	

		buf.append("\n where COCD = ?"); //
		buf.append("\n AND DEPT_CD = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteDepInfo2######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			stmt.setString(++index, "N");

			stmt.setString(++index, roleDO.getCocd());
			stmt.setString(++index, roleDO.getDept_cd());



			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 부서가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 주제영상 코드 조회                                                                                                                                                                                 
	 * @return  List                                                                                                                                                                                             
	 * @throws Exception 
	 */
	public List selecAnnotInfo() throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectAnnotInfoQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecAnnotInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				CodeDO item = new CodeDO();

				item.setSclCd(rs.getString("scl_cd"));

				item.setDesc(rs.getString("DESC"));

				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}




	//2012.4.19 다스 확장 추가 구현



	/**
	 * 역활에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 * @throws Exception 
	 */
	public List getRoleInfoForChennel(RoleInfoDO roleInfoDO) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleInfoForChennel(roleInfoDO));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getRoleInfoForChennel######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setRole_id(rs.getString("scl_cd"));
				item.setRole_nm(rs.getString("DESC"));
				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}

	/**
	 * 로그인 기록
	 * @param xml                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public long insertLogin(LogInOutDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.LOGINLOG_TBL( ");
		buf.append("\n 	USER_ID,  ");
		buf.append("\n 	LOGIN_DT, ");
		buf.append("\n 	IP, ");
		buf.append("\n 	STATUS,  ");
		buf.append("\n 	LOGOUT_DT,  ");
		buf.append("\n 	SEQ,  ");
		buf.append("\n 	ACTIVE_YN  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?,  ?,?,?, ? , ?,? )  ");
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertLogin######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			long seq= systemManageDAO.selectLogInSeq();
			stmt.setString(++index, roleDO.getUser_id());
			stmt.setString(++index, dateTime );
			stmt.setString(++index, roleDO.getIp());
			stmt.setString(++index, roleDO.getStatus());
			stmt.setString(++index, "");
			stmt.setLong(++index, seq);
			stmt.setString(++index, "Y");


			int itmp = stmt.executeUpdate();

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, roleDO);

			con.commit();
			return seq;
		}  catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 로그아웃 정보를 기록한다
	 * 
	 * @param roleDO
	 * @throws Exception 
	 *           
	 */
	public int updateLogout(LogInOutDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.LOGINLOG_TBL set ");

		buf.append("\n 	STATUS = ?,  ");	
		buf.append("\n 	LOGOUT_DT = ?, ");
		buf.append("\n 	ACTIVE_YN = ? ");
		buf.append("\n where SEQ = ?"); //

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateLogout######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			int index = 0;
			stmt.setString(++index, roleDO.getStatus());

			stmt.setString(++index, dateTime);
			stmt.setString(++index, "N");
			stmt.setLong(++index, roleDO.getSeq());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 사용자 로그인 현황을 조회한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getLogInfo(LogInOutDO roleDO) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectLogInfo(roleDO));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getLogInfo######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				LogInOutDO item = new LogInOutDO();

				item.setUser_id(rs.getString("USER_ID"));
				item.setDept_nm(rs.getString("DEPT_NM"));
				item.setLogin_dt(rs.getString("LOGIN_DT"));
				item.setStatus(rs.getString("STATUS"));
				item.setLogout_dt(rs.getString("LOGOUT_DT"));
				item.setIp(rs.getString("IP"));
				resultList.add(item);
			}

			return resultList;
		}  catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}



	/**
	 * 모니터링 역활 정보를 목록 조회한다.
	 * 
	 * @param userid
	 *            사용자id
	 * @return
	 * @throws Exception 
	 */
	public String selectRoleForLoginInMonitoring(String userid) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleForLoginInMonitoringQuery(userid));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectRoleForLoginInMonitoring######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			List resultList = new ArrayList();


			String role_cd="";
			while (rs.next()) {

				role_cd = rs.getString("monitor_Cd");
			}

			return role_cd;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}




	/**
	 * 모니털이 에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 * @throws Exception 
	 */
	public String getRoleForLoginInMonitoring(String user_id) throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleForLoginInMonitoringQuery(user_id));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getRoleForLoginInMonitoring######## con : " + con);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			Das das = new Das();
			Node node = null;
			Node subNode = null;
			String oldDepth = "";
			while (rs.next()) {

				/*
				 * oldDepth(1) - depth(1)
				 * oldDepth(2) - depth(1)
				 * das.addNode
				 */
				String depth = rs.getString("DEPTH");
				if((oldDepth.equals("1") && depth.equals("1")) ||
						(oldDepth.equals("2") && depth.equals("1"))) {
					das.addNode(node);
				}
				oldDepth = depth;

				if(depth.equals("1")) {
					node = new Node();
					node.setName(rs.getString("MENU_NM"));
					node.setAuth(rs.getString("USE_PERM"));
					node.setMenuId(rs.getInt("MENU_ID"));
					node.setPermId(rs.getInt("PERM_ID"));
				} else {
					subNode = new Node();
					subNode.setName(rs.getString("MENU_NM"));
					subNode.setAuth(rs.getString("USE_PERM"));
					node.setMenuId(rs.getInt("MENU_ID"));
					node.setPermId(rs.getInt("PERM_ID"));
					node.addNode(subNode);
				}

				//				RoleInfoDO item = new RoleInfoDO();
				//
				//				item.setMenu_id(rs.getString("MENU_ID"));
				//				item.setMenu_nm(rs.getString("MENU_NM"));
				//				item.setDepth(rs.getString("DEPTH"));
				//				item.setUse_perm(rs.getString("USE_PERM"));
				//				resultList.add(item);
			}
			das.addNode(node);

			String xml = "";

			xml = convertorService.createMarshaller(das);


			return xml;
		} catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		}finally {
			release(rs, stmt, con);
		}
	}





	/**
	 * 역할 정보를 수정한다.
	 * 
	 * @param roleDO
	 * @throws Exception 
	 *          
	 */
	public int updateRoleInfoForMonitoring(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.MENU_PERM_TBL set ");
		buf.append("\n 	 USE_PERM = ?  ");

		buf.append("\n where MENU_ID = ?");
		buf.append("\n AND PERM_ID = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateRoleInfoForMonitoring######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;

			String[] menu_id = roleDO.getMenu_id().split(",");
			String[] perm_id = roleDO.getPerm_id().split(",");
			String[] auth = roleDO.getUse_perm().split(",");
			for (int i = 0; i < menu_id.length; i++) {
				index = 0;
				stmt.setString(++index, auth[i]);
				stmt.setString(++index, menu_id[i]);
				stmt.setString(++index, perm_id[i]);
				updateCount = stmt.executeUpdate();

			}
			updateCount += updateCount;

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			return 1;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 부서정보기준 사용자 정보를 가져온다
	 * 
	 * @param deptcd 부서코드
	 * @return List
	 * @throws Exception 
	 */
	public List getDepinfoForuserListFormonitoring(String deptcd) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.getDepinfoForuserListFormonitoring(deptcd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getDepinfoForuserListFormonitoring######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			while (rs.next()) {
				EmployeeInfoDO item = new EmployeeInfoDO();

				item.setSbs_user_ID(rs.getString("sbs_user_id"));
				item.setUser_nm(rs.getString("user_nm"));
				item.setUser_num(rs.getString("user_num"));
				item.setMonitor_cd(rs.getString("monitor_role"));
				resultList.add(item);

			}

			return resultList;
		}catch (Exception e) {
			logger.error(buf.toString());

			throw e;
		} finally {
			release(rs, stmt, con);
		}
	}





	/**
	 * 타 DB에서 부서 정보를 받아온다(삭제).
	 * 
	 * @param DepInfoDO
	 * @return
	 * @throws Exception 
	 * */
	public int deleteOtherDeptInfo(DepInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.DEP_INFO_TBL set   ");

		buf.append("\n 	use_yn ='N' ");
		buf.append("\n where   ");
		buf.append("\n DEPT_CD = ?  ");
		buf.append("\n and COCD = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteOtherDeptInfo######## con : " + con);
			con.setAutoCommit(false);

			//stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			index = 0;

			stmt.setString(++index, infoDO.getDept_cd());
			stmt.setString(++index, infoDO.getCocd());
			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 타 DB에서 사용자 정보를 받아온다(수정).
	 * 
	 * @param EmployeeDASRoleDO
	 * @return
	 * @throws Exception 
	 * */
	public int DeleteOtherUserInfo(EmployeeInfoDO infoDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.USER_INFO_TBL set   ");
		/*buf.append("\n 	PER_REG_NO = ?  ");
		// buf.append("\n 	,SBS_USER_ID =?  ");
		buf.append("\n 	,PASSWORD =?  ");
		buf.append("\n 	,USER_NM =?  ");
		buf.append("\n 	,DEPT_CD =? ");
		buf.append("\n 	,USER_NUM =?  ");
		buf.append("\n 	,ACCT_CODE = ? ");
		buf.append("\n 	,COCD =? ");*/
		buf.append("\n 	delete_yn ='N' ");

		buf.append("\n where   ");
		buf.append("\n sbs_user_id = ?  ");

		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######DeleteOtherUserInfo######## con : " + con);
			con.setAutoCommit(false);

			//stmt = con.prepareStatement(buf.toString());
			stmt = con.prepareStatement(buf.toString());
			// String userId="";
			int index = 0;

			index = 0;


			stmt.setString(++index, infoDO.getSbs_user_ID());

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) {
				logger.debug("[Inserted Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_PROGRAM_INFO, "저장에 실패했습니다.");
				throw exception;
			}

			con.commit();
			return updateCount;
		}

		catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 사번 중복여부 조회한다.
	 * 
	 * @param perRegNo
	 *            주민번호
	 * 
	 * @return true, false
	 * @throws Exception 
	 */

	public boolean isThereDepInfo(String dept_cd) throws Exception {
		// PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append("\n select count(1) FROM  DAS.dep_info_tbl where dept_cd = '"
				+ dept_cd + "' \n");
		Connection con = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######isThereDepInfo######## con : " + con);

			// 총 조회 갯수를 구한다.
			int totalCount = getTotalCount(con, buf.toString());

			if (totalCount > 0) {
				return true;
			} else {
				return false;
			}
		}  catch (Exception e) {
			logger.error(buf.toString());
			throw e;
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
	}





	/**
	 * 역활 정보를 목록 조회한다.
	 * @return List
	 * @throws Exception 
	 */
	public List selecroleInfoForMonitoring() throws Exception {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectRoleInfoForMonitoringQuery());

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######selecroleInfoForMonitoring######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();

			int indexCount = 0;

			List resultList = new ArrayList();

			while (rs.next()) {
				RoleInfoDO item = new RoleInfoDO();

				item.setRole_id(rs.getString("perm_id"));

				item.setRole_nm(rs.getString("perm_nm"));

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




	/**
	 * 권한 정보를 수정한다.
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateAuthorInfoForMonitoring(AuthorDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.USER_INFO_TBL set ");
		buf.append("\n 	 MONITOR_ROLE = ?  ");

		buf.append("\n where SBS_USER_ID = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateAuthorInfoForMonitoring######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;

			String[] sbs_user_id = roleDO.getSbs_user_id().split(",");
			String[] role_cd = roleDO.getRole_cd().split(",");

			for (int i = 0; i < sbs_user_id.length; i++) {
				index = 0;
				stmt.setString(++index, role_cd[i]);
				stmt.setString(++index, sbs_user_id[i]);

				updateCount = stmt.executeUpdate();

			}

			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			if (updateCount == 0) {
				// 여기서 에러를 던진다.
				DASException exception = new DASException(
						ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다.");
				throw exception;
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			con.setAutoCommit(true);
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 모니털이 에따른 권한 정보 목록 조회한다.
	 * 
	 * @param role_Cd
	 *           역활코드
	 * @return List
	 */
	public String getAuthorForMonitoring(String role_cd) throws DASException {
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();

		buf.append(UserRoleStatement.selectAuthorForMonitoringQuery(role_cd));

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######getAuthorForMonitoring######## con : " + con);
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			rs = stmt.executeQuery();
			String rolenms = "";
			int indexCount = 0;

			Das das = new Das();
			Node node = null;
			Node subNode = null;
			String oldDepth = "";
			while (rs.next()) {

				/*
				 * oldDepth(1) - depth(1)
				 * oldDepth(2) - depth(1)
				 * das.addNode
				 */
				String depth = rs.getString("DEPTH");
				if((oldDepth.equals("1") && depth.equals("1")) ||
						(oldDepth.equals("2") && depth.equals("1"))) {
					das.addNode(node);
				}
				oldDepth = depth;

				if(depth.equals("1")) {
					node = new Node();
					node.setName(rs.getString("MENU_NM"));
					node.setAuth(rs.getString("USE_PERM"));
					node.setMenuId(rs.getInt("MENU_ID"));
					node.setPermId(rs.getInt("PERM_ID"));
				} else {
					subNode = new Node();
					subNode.setName(rs.getString("MENU_NM"));
					subNode.setAuth(rs.getString("USE_PERM"));
					node.setMenuId(rs.getInt("MENU_ID"));
					node.setPermId(rs.getInt("PERM_ID"));
					node.addNode(subNode);
				}

				//				RoleInfoDO item = new RoleInfoDO();
				//
				//				item.setMenu_id(rs.getString("MENU_ID"));
				//				item.setMenu_nm(rs.getString("MENU_NM"));
				//				item.setDepth(rs.getString("DEPTH"));
				//				item.setUse_perm(rs.getString("USE_PERM"));
				//				resultList.add(item);
			}
			das.addNode(node);

			String xml = "";

			xml = convertorService.createMarshaller(das);


			return xml;
		} catch (Exception e) {
			logger.error(buf.toString());
		} finally {
			release(rs, stmt, con);
		}
		return "";
	}




	/**
	 * 사용자 계정별 락을 해제한다
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateUnlock(String user_id) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.metadat_mst_tbl  set ");
		buf.append("\n 	 lock_stat_cd = 'N'   ");

		buf.append("\n where MODRID = ? and  lock_stat_cd='Y'");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateUnlock######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;


			index = 0;

			stmt.setString(++index, user_id);

			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			// 사용자 정보의 수정 내역을 등록한다.
			// insertNonEmployeeRoleHistory(con, beforeRoleDO);
			con.commit();
			con.setAutoCommit(true);
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역활 권한 그룹신규로 신청.(모리터링)
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public RoleInfoDO insertMoniToringRole(RoleInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.code_tbl( ");
		buf.append("\n 	clf_cd,  ");
		buf.append("\n 	scl_Cd,  ");
		buf.append("\n 	clf_nm,  ");
		buf.append("\n 	desc,  ");
		buf.append("\n 	rmk_1,  ");
		buf.append("\n 	rmk_2, ");
		buf.append("\n 	reg_Dt,  ");
		buf.append("\n 	regrid,  ");
		buf.append("\n 	mod_dt,  ");
		buf.append("\n 	modrid,  ");
		buf.append("\n 	use_yn,  ");
		buf.append("\n 	gubun  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ? , ?, ? , ?  , ? , ?, ?  , ? , ? , ? , ? )    ");

		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMoniToringRole######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			String newRoleCode = selectCodeNumForMonitoring();
			stmt.setString(++index, "A055");
			stmt.setString(++index, newRoleCode);
			stmt.setString(++index, "모니터링 권한");
			stmt.setString(++index, roleDO.getRole_nm());
			stmt.setString(++index, "");
			stmt.setString(++index, "");
			stmt.setString(++index, dateTime);
			stmt.setString(++index,"");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, "");
			stmt.setString(++index, "Y");
			stmt.setString(++index, "");

			int itmp = stmt.executeUpdate();
			roleDO.setRole_id(newRoleCode);
			roleDO.setPerm_id(newRoleCode);
			//PERM_TBL에 등록한다(로그인모듈을 위해)
			insertMoniToringRoleForTBL(roleDO);
			// userId
			con.commit();
			return roleDO;

		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 권한 코드 생성(모니터링)
	 * @return String 수집처 코드 증가값....
	 * @throws Exception 
	 */
	public String selectCodeNumForMonitoring() throws Exception
	{
		String result = "";

		String scl_cd = "";
		StringBuffer buf = new StringBuffer();
		buf.append("\n select ");
		buf.append("\n 	MAX(Integer(SCL_CD)) as SCL_CD "); 
		buf.append("\n from DAS.CODE_TBL ");
		buf.append("\n where CLF_CD = 'A055' ");
		//buf.append("\n and gubun='"+gubun+"' ");
		buf.append("\n WITH UR	 ");
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCodeNumForMonitoring######## con : " + con);

			stmt = con.prepareStatement(buf.toString());

			rs = stmt.executeQuery();

			if(rs.next())
			{
				scl_cd = rs.getString("SCL_CD");
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_CODE_INFO, "해당 코드값이 존재하지 않습니다.");
				throw exception;

			}


			int scl= (Integer.parseInt(scl_cd.trim()))+1;



			return String.valueOf(scl);
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
	 * 역활 권한 그룹신규로 신청.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertRoleGroupForMonitoring(RoleInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into MENU_PERM_TBL( ");
		buf.append("\n 	MENU_ID,  ");
		buf.append("\n 	PERM_ID,  ");
		buf.append("\n 	USE_PERM  ");
		buf.append("\n )  ");
		buf.append("\n values  ");


		buf.append("\n (?, ?, ?)    ");

		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";


		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertRoleGroupForMonitoring######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			String userId = "";
			int index = 0;
			int itmp =0;
			int count =countMenuInfo();
			for (int i =2 ; i<count ; i++){
				index = 0;

				stmt.setInt(++index,  i);

				stmt.setString(++index,roleDO.getPerm_id());
				stmt.setString(++index, "R");

				itmp = stmt.executeUpdate();
			}
			// userId
			con.commit();
			return itmp;

		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 매뉴id 갯수를구한다
	 * @return
	 * @throws Exception 
	 */
	public int countMenuInfo() throws Exception{

		StringBuffer buf = new StringBuffer();
		Connection con = null;		
		PreparedStatement psmt = null;		
		ResultSet rs = null;

		buf.append(" select count(*) as cnt                  ");
		buf.append("                             ");
		buf.append(" 	 from das.MENU_TBL     ");

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######countMenuInfo######## con : " + con);
			con.setAutoCommit(false);
			psmt = con.prepareStatement(buf.toString());    //  로그 inform
			// psmt = con.prepareStatement(buf.toString());
			int index = 0;			

			rs = psmt.executeQuery();	
			int count=0;
			if(rs.next())
			{
				count =      	rs.getInt("cnt");

			}
			con.setAutoCommit(true);

			return count;

		}
		catch (Exception ex)
		{
			logger.error(buf.toString());

			
			throw ex;
		}
		finally
		{
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(rs, psmt, con);
		}

	}


	/**
	 * 역활 권한 그룹신규로 신청.(모니터링)
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public RoleInfoDO insertMoniToringRoleForTBL(RoleInfoDO roleDO) throws Exception {
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.PERM_TBL( ");
		buf.append("\n 	PERM_ID,  ");
		buf.append("\n 	PERM_NM,  ");
		buf.append("\n 	USE_yn  ");

		buf.append("\n )  ");
		buf.append("\n values  ");

		buf.append("\n (?, ? , ?)    ");

		Connection con = null;
		PreparedStatement stmt = null;
		// String user_id="";
		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertMoniToringRoleForTBL######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());

			int index = 0;
			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");

			stmt.setString(++index, roleDO.getPerm_id());			
			stmt.setString(++index, roleDO.getRole_nm());			
			stmt.setString(++index, "Y");


			int itmp = stmt.executeUpdate();

			con.commit();
			return roleDO;

		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역할 정보를 삭제한다(실제 삭제는 아님)
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int deleteMonitoringInfoForCode(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.code_tbl set ");
		buf.append("\n 	 USE_YN = 'N'  ");

		buf.append("\n where clf_cd = 'A055'");
		buf.append("\n AND SCL_CD = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteMonitoringInfoForCode######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;



			index = 0;


			stmt.setString(++index, roleDO.getPerm_id());
			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			deleteMonitoringInfoForPerm(roleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역할 정보를 삭제한다(실제 삭제는 아님)
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int deleteMonitoringInfoForPerm(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.perm_tbl set ");
		buf.append("\n 	 USE_YN = 'N'  ");

		buf.append("\n where ");
		buf.append("\n  perm_id = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######deleteMonitoringInfoForPerm######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;



			index = 0;


			stmt.setString(++index, roleDO.getPerm_id());
			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역할 정보를 수정
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateMonitoringInfoForCode(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.code_tbl set ");
		buf.append("\n 	 desc=?  ");

		buf.append("\n where clf_cd = 'A055'");
		buf.append("\n AND SCL_CD = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateMonitoringInfoForCode######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;



			index = 0;

			stmt.setString(++index, roleDO.getRole_nm());
			stmt.setString(++index, roleDO.getPerm_id());
			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			updateMonitoringInfoForPerm(roleDO);
			con.commit();
			return updateCount;
		}  catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
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
	 * 역할 정보를 삭제한다(실제 삭제는 아님)
	 * 
	 * @param roleDO
	 *            외부직원 Role 정보가 포함되어 있는 DataObject @
	 * @throws Exception 
	 */
	public int updateMonitoringInfoForPerm(RoleInfoDO roleDO) throws Exception {

		StringBuffer buf = new StringBuffer();

		buf.append("\n update DAS.perm_tbl set ");
		buf.append("\n 	PERM_NM = ? ");

		buf.append("\n where ");
		buf.append("\n  perm_id = ?");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;

		try {
			con = DBService.getInstance().getConnection();
			//logger.debug("######updateMonitoringInfoForPerm######## con : " + con);
			con.setAutoCommit(false);

			stmt = con.prepareStatement(buf.toString());
			int updateCount = 0;
			int index = 0;



			index = 0;


			stmt.setString(++index, roleDO.getRole_nm());
			stmt.setString(++index, roleDO.getPerm_id());
			updateCount = stmt.executeUpdate();



			if (logger.isDebugEnabled()) {
				logger.debug("[Update Count]" + updateCount);
			}

			con.commit();
			return updateCount;
		} catch (Exception e) {
			logger.error(buf.toString());

			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO 자동 생성된 catch 블록
					e1.printStackTrace();
				}
			}

			throw e;
		} finally {
			try {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			release(null, stmt, con);
		}
	}



}

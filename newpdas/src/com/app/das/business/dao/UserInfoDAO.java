package com.app.das.business.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.statement.UserInfoStatement;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.UserInfoDO;
import com.app.das.util.CalendarUtil;
import com.app.das.util.DBService;
import com.app.das.util.StringUtils;

/**
 * 사용자 조회 및 로그인에 대한 Database 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class UserInfoDAO  extends AbstractDAO 
{
	private Logger logger = Logger.getLogger(UserInfoDAO.class);

	private static UserInfoDAO instance;

	/**
	 * A private constructor
	 *
	 */
	private UserInfoDAO() 
	{
	}

	public static synchronized UserInfoDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new UserInfoDAO();
		}
		return instance;
	}

	/**
	 * 특정 정직원의 정보를 조회한다.
	 * @param userId 사용자ID
	 * @return DASCommonDO 사용자 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public DASCommonDO selectEmployeeInfo(String userId) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEmployeeInfo######## con : " + con);
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeRoleQuery());
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeRoleQuery());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				DASCommonDO item = new DASCommonDO();
				item.setUserNo(	rs.getString("USER_NO"));
				item.setDeptNm(	rs.getString("DEPT_NM"));
				item.setUserId(		rs.getString("USER_ID"));
				item.setUserNm(	rs.getString("USER_NM"));
				item.setRole(		rs.getString("ROLE"));
				item.setRegularYn(DASBusinessConstants.YesNo.YES);

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				throw exception;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userid : " + userId);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}
	/**
	 * erp 직원 조회 .
	 * @param userId 사용자ID
	 * @return String 
	 * @throws Exception 
	 */
	public String selectEmployeeInfoService(String userId) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		if(logger.isDebugEnabled())
			logger.debug("[selectEmployeeInfoService]!!");
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEmployeeInfoService######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeRoleQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeRoleQuery());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			if(rs.next())
			{
				String str = DASBusinessConstants.YesNo.YES+","+" "+","+" "+","+rs.getString("USER_NM");
				return str;
			}			
			else
			{
				//  DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				//  throw exception;
				String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.NOT_EXIST_USER+","+" "+","+" ";
				return str;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userid : " + userId);

		
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 정직원 정보를 조회한다.
	 * @param userNo 사원번호
	 * @return DASCommonDO 사용자 정보를 포함하고 있는 정보
	 * @throws Exception 
	 */
	public DASCommonDO selectEmployeeInfoByUserNo(String userNo) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEmployeeInfoByUserNo######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeInfoByUserNoQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeInfoByUserNoQuery());
			int index = 0;
			stmt.setString(++index, userNo);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				DASCommonDO item = new DASCommonDO();
				item.setUserNo(	rs.getString("USER_NO"));
				item.setDeptNm(	rs.getString("DEPT_NM"));
				item.setUserId(		rs.getString("USER_ID"));
				item.setUserNm(	rs.getString("USER_NM"));

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				throw exception;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userno : " + userNo);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 1.0 소스 -  비직원 로그인 - 사용하지 않음
	 * @param userId 사용자id
	 * @param passwd 암호
	 * @return 유효하지 않은 토큰 유효한 사용자 존재하지 않는 사용자
	 * @throws Exception 
	 */
	public DASCommonDO selectNonEmployeeInfo(String userId, String passwd) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectNonEmployeeInfo######## con : " + con);

			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectNonEmployeeRoleQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectNonEmployeeRoleQuery());
			int index = 0;
			stmt.setString(++index, userId);
			//            stmt.setString(++index, passwd);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				int passwdErrorCount = rs.getInt("PW_ERN");
				//				if(DASBusinessConstants.PASSWD_FAIL_MAX_COUNT == passwdErrorCount)
				//				{
				//			        DASException exception = new DASException(ErrorConstants.EXCESS_MAX_PASSWD_FAIL_COUNT, " 비밀번호 최대 실패 횟수를 초과했습니다.");
				//			        throw exception;
				//				}

				String serverPasswd = rs.getString("PASSWORD");
				if(!passwd.equals(serverPasswd))
				{
					//패스워드 오류 횟수를 증가시킨다.
					updateLoginFailCount(userId);

					DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
					throw exception;
				}

				//오늘 날짜를 구한다.
				String toDate = CalendarUtil.getToday();

				String validEndDate = rs.getString("VLDDT_END");
				if(!StringUtils.isEmpty(validEndDate))
				{
					//만약, 유효만료일이 오늘보다 크면 로그인을 막는다.
					int compareBetweenDay = CalendarUtil.compareBetweenDates(toDate, validEndDate);
					if(compareBetweenDay == 1)
					{
						DASException exception = new DASException(ErrorConstants.EXCESS_VALID_END_DATE, " 사용자 계정이 유효 만료일을 초과했습니다. 관리자에게 문의하세요.");
						throw exception;
					}
				}

				//처음 접속자는 비밀번호를 변경해야 한다.
				String perRegNo = rs.getString("PER_REG_NO");
				String warningMsg = null;
				if(passwd.equals(StringUtils.getInitialPasswd(perRegNo)) || passwd.equals(DASBusinessConstants.INITIAL_PASSWD))
				{
					warningMsg = "현재 비밀번호는 초기화된 비밀번호 입니다. 비밀번호를 변경하십시요";
				}

				String lastChangePasswdDate = rs.getString("PW_LST_CHG");
				String afterYearChangeDate = CalendarUtil.afterSpecDay(lastChangePasswdDate, 1, 0, 0);

				//최종 비밀번호 변경일이 12개월이 초과되면 로그인을 할 수 없고 관리자에게 문의해야 한다.
				if(CalendarUtil.compareBetweenDates(afterYearChangeDate, toDate) != 1)
				{
					DASException exception = new DASException(
							ErrorConstants.EXCESS_12MONTH_CHANGE_PASSWD, " 비밀번호 변경일이 12개월을 초과했습니다. 관리자에게 문의하세요.");
					throw exception;
				}

				//최종 비밀번호 변경일이 12개월이 초과되면 비밀번호를 변경해야 한다.
				String after11MonthChangeDate = CalendarUtil.afterSpecDay(lastChangePasswdDate, 0, 11, 0);

				if(CalendarUtil.compareBetweenDates(after11MonthChangeDate, toDate) != 1)
				{
					int warningDay = CalendarUtil.getBetweenDates(toDate, afterYearChangeDate);
					warningMsg = "계정 만료일이 " + warningDay + " 일 남았습니다.";
				}

				DASCommonDO item = new DASCommonDO();
				item.setUserId(		rs.getString("OUT_USER_ID"));
				item.setUserNm(	rs.getString("OUT_USER_NM"));
				item.setRole(		rs.getString("ROLE"));
				item.setPerRegNo(rs.getString("PER_REG_NO"));
				item.setRegularYn(DASBusinessConstants.YesNo.NO);
				if(!StringUtils.isEmpty(warningMsg))
				{
					item.setWarningMsg(warningMsg);
				}

				return item;
			}
			else
			{
				//		        DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
				throw exception;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userId : "  + userId);
			logger.error("passwd : "  + passwd);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 1.0 소스 -  비직원 로그인 - 사용하지 않음
	 * @param userId 사용자id
	 * @param passwd 암호
	 * @return 유효하지 않은 토큰 유효한 사용자 존재하지 않는 사용자
	 * @throws Exception 
	 */
	public String selectNonEmployeeInfoService(String userId, String passwd) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectNonEmployeeInfoService######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectNonEmployeeRoleQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectNonEmployeeRoleQuery());

			int index = 0;
			stmt.setString(++index, userId);
			//            stmt.setString(++index, passwd);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				int passwdErrorCount = rs.getInt("PW_ERN");
				//				if(DASBusinessConstants.PASSWD_FAIL_MAX_COUNT == passwdErrorCount)
				//				{
				//			        DASException exception = new DASException(ErrorConstants.EXCESS_MAX_PASSWD_FAIL_COUNT, " 비밀번호 최대 실패 횟수를 초과했습니다.");
				//			        throw exception;
				//				}

				String serverPasswd = rs.getString("PASSWORD");
				if(!passwd.equals(serverPasswd))
				{
					//패스워드 오류 횟수를 증가시킨다.
					updateLoginFailCount(userId);

					String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.NOT_CORRECT_ID_OR_PASSWD+","+" "+","+" ";
					return str;
					//DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
					//  throw exception;
				}

				//오늘 날짜를 구한다.
				String toDate = CalendarUtil.getToday();

				String validEndDate = rs.getString("VLDDT_END");
				if(!StringUtils.isEmpty(validEndDate))
				{
					//만약, 유효만료일이 오늘보다 크면 로그인을 막는다.
					int compareBetweenDay = CalendarUtil.compareBetweenDates(toDate, validEndDate);
					if(compareBetweenDay == 1)
					{
						String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.EXCESS_VALID_END_DATE+","+" "+","+" ";
						return str;
						//DASException exception = new DASException(ErrorConstants.EXCESS_VALID_END_DATE, " 사용자 계정이 유효 만료일을 초과했습니다. 관리자에게 문의하세요.");
						//throw exception;
					}
				}

				//처음 접속자는 비밀번호를 변경해야 한다.
				String perRegNo = rs.getString("PER_REG_NO");
				String warningMsg = null;
				if(passwd.equals(StringUtils.getInitialPasswd(perRegNo)) || passwd.equals(DASBusinessConstants.INITIAL_PASSWD))
				{
					warningMsg = "현재 비밀번호는 초기화된 비밀번호 입니다. 비밀번호를 변경하십시요";
				}

				String lastChangePasswdDate = rs.getString("PW_LST_CHG");
				String afterYearChangeDate = CalendarUtil.afterSpecDay(lastChangePasswdDate, 1, 0, 0);

				//최종 비밀번호 변경일이 12개월이 초과되면 로그인을 할 수 없고 관리자에게 문의해야 한다.
				if(CalendarUtil.compareBetweenDates(afterYearChangeDate, toDate) != 1)
				{
					// DASException exception = new DASException(
					// 		ErrorConstants.EXCESS_12MONTH_CHANGE_PASSWD, " 비밀번호 변경일이 12개월을 초과했습니다. 관리자에게 문의하세요.");
					// throw exception;

					String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.EXCESS_12MONTH_CHANGE_PASSWD+","+" "+","+" ";
					return str;
				}

				//최종 비밀번호 변경일이 12개월이 초과되면 비밀번호를 변경해야 한다.
				String after11MonthChangeDate = CalendarUtil.afterSpecDay(lastChangePasswdDate, 0, 11, 0);

				if(CalendarUtil.compareBetweenDates(after11MonthChangeDate, toDate) != 1)
				{
					int warningDay = CalendarUtil.getBetweenDates(toDate, afterYearChangeDate);
					warningMsg = "계정 만료일이 " + warningDay + " 일 남았습니다.";
				}

				DASCommonDO item = new DASCommonDO();
				item.setUserId(		rs.getString("OUT_USER_ID"));
				item.setUserNm(	rs.getString("OUT_USER_NM"));
				item.setRole(		rs.getString("ROLE"));
				item.setPerRegNo(rs.getString("PER_REG_NO"));
				item.setRegularYn(DASBusinessConstants.YesNo.NO);
				if(!StringUtils.isEmpty(warningMsg))
				{
					item.setWarningMsg(warningMsg);
				}

				if(warningMsg == null)
					warningMsg = " ";

				String str = DASBusinessConstants.YesNo.YES+","+" "+","+warningMsg+","+rs.getString("OUT_USER_NM");
				return str;
				//return item;
			}
			else
			{
				//DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
				//throw exception;

				String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.NOT_CORRECT_ID_OR_PASSWD+","+" "+","+" ";
				return str;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userId : " + userId);
			logger.error("passwd : " + passwd);

			
			throw e;
		}

		finally
		{
			release(rs, stmt, con);
		}

	}
	/**
	 * 1.0 소스 - 정직원 로그인 -  사용하지 않음
	 * @param userId 사용자 로그인
	 * @param passwd 암호
	 * @return 유효하지 않은 토큰 유효한 사용자 존재하지 않는 사용자
	 * @throws Exception 
	 */
	public DASCommonDO selectEmployeeRoleLogin(String userId, String passwd) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectEmployeeRoleLogin######## con : " + con);
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeRoleLoginQuery());
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeRoleLoginQuery());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			int indexCount = 0;
			List resultList = new ArrayList();

			if(rs.next())
			{
				DASCommonDO item = new DASCommonDO();

				String serverPasswd = rs.getString("PASSWORD");
				if(!passwd.equals(serverPasswd))
				{
					DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
					throw exception;
				}

				item.setUserNo(	rs.getString("USER_NO"));
				item.setDeptNm(	rs.getString("DEPT_NM"));
				item.setUserId(		rs.getString("USER_ID"));
				item.setUserNm(	rs.getString("USER_NM"));
				item.setRole(		rs.getString("ROLE"));
				item.setRegularYn(DASBusinessConstants.YesNo.YES);

				return item;
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				throw exception;

			}
		} 
		catch (Exception e) 
		{
			logger.error("userId : " + userId);
			logger.error("passwd : " + passwd);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 정직원 로그인
	 * @param userId 사용자id
	 * @return boolean
	 * @throws Exception 
	 */
	public boolean selectEmployeeLogin(String userId) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEmployeeLogin######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeLoginQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeLoginQuery());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();

			int indexCount = 0;

			if(rs.next())
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
			logger.error("userId : " + userId);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 로그인서비스
	 * @param userId 사용자id
	 * @param  passwd 암호
	 * @throws Exception 
	 */
	public String EmployeeRoleLoginService(String userId, String passwd) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######EmployeeRoleLoginService######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectEmployeeRoleLoginQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectEmployeeRoleLoginQuery());

			int index = 0;
			stmt.setString(++index, userId);

			rs = stmt.executeQuery();			

			if(rs.next())
			{
				//	DASCommonDO item = new DASCommonDO();

				String serverPasswd = rs.getString("PASSWORD");
				if(!passwd.equals(serverPasswd))
				{
					String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.NOT_CORRECT_ID_OR_PASSWD+","+" "+","+" "; 

					return str;
					//DASException exception = new DASException(ErrorConstants.NOT_CORRECT_ID_OR_PASSWD, " ID 또는 Password 가 정확하지 않습니다.");
					//throw exception;			        
				}
				String str = DASBusinessConstants.YesNo.YES+","+" "+","+ErrorConstants.NO_SSO_LOGIN+","+rs.getString("USER_NM");
				return str;
			}
			else
			{
				String str = DASBusinessConstants.YesNo.NO+","+ErrorConstants.NOT_EXIST_USER+","+" "+","+" ";
				return str;
				//DASException exception = new DASException(ErrorConstants.NOT_EXIST_USER, "해당 사용자가 존재하지 않습니다..");
				//throw exception;

			}
		} 

		catch (Exception e) 
		{
			logger.error("userId : " + userId);
			logger.error("passwd : " + passwd);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 사용자 목록을 검색한다.(정직원, 외부직원)
	 * @param searchValue 검색하고자 하는 이름 또는 ID
	 * @param commonDO 공통정보
	 * @return
	 * @throws Exception 
	 */
	public PageDO selectAllUserList(String searchValue, int currenntPage, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(UserInfoStatement.getAllUserListQuery(searchValue, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	 \n");

		//Page에 따른 계산을 한다.
		int page = currenntPage;
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
			//logger.debug("######selectAllUserList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, UserInfoStatement.getAllUserListQuery(searchValue, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.USER_INFO_ROW_COUNT;

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
				UserInfoDO item = new UserInfoDO();
				item.setEmpFlag(		rs.getString("EMP_FLAG"));
				item.setUserId(			rs.getString("USER_ID"));
				item.setUserNm(		rs.getString("USER_NM"));
				item.setEtcNm(			rs.getString("ETC_NM"));
				item.setPhoneNo(		rs.getString("PHONE_NO"));

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
	 * 정직원 사용자 목록을 검색한다.
	 * @param searchValue 검색하고자 하는 이름 또는 ID
	 * @param commonDO 공통정보
	 * @return
	 * @throws Exception 
	 */
	public PageDO selectRegularUserList(String searchValue, int currenntPage, DASCommonDO commonDO) throws Exception
	{
		PageDO pageDO = new PageDO();

		StringBuffer buf = new StringBuffer();
		buf.append(" select * FROM                                                      										\n");
		buf.append(" (                                                                  												\n");
		buf.append(UserInfoStatement.getRegularUserListQuery(searchValue, DASBusinessConstants.PageQueryFlag.NORMAL));
		buf.append(" ) AS temp                                                           											\n");
		buf.append(" where temp.rownum >= ? and temp.rownum <=?                                        	\n");
		buf.append(" WITH UR	\n ");

		//Page에 따른 계산을 한다.
		int page = currenntPage;
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
			//logger.debug("######selectRegularUserList######## con : " + con);
			//총 조회 갯수를 구한다.
			int totalCount  = 
					getTotalCount(con, UserInfoStatement.getRegularUserListQuery(searchValue, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT));

			stmt = con.prepareStatement(buf.toString());

			int rowPerPage = DASBusinessConstants.PageRowCount.USER_INFO_ROW_COUNT;

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
				UserInfoDO item = new UserInfoDO();
				item.setUserId(			rs.getString("USER_ID"));
				item.setUserNm(		rs.getString("USER_NM"));
				item.setTitle(				rs.getString("TITLE"));
				item.setJob(				rs.getString("JOB"));
				item.setPhoneNo(		rs.getString("HAND_PHON"));
				item.setEmail(			rs.getString("E_MAIL"));
				item.setSeg_nm(		rs.getString("SEG_NM"));
				item.setDept_nm(		rs.getString("DEPT_NM"));

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
	 * 외부직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateLoginFailCount(String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTSIDER_INFO_TBL set ");
		buf.append("\n 	PW_ERN = (PW_ERN + 1), ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where OUT_USER_ID = ?  ");		

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######updateLoginFailCount######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, userId);
			stmt.setString(++index, userId);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
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
			release(null, stmt, con);
		}
	}

	/**
	 * 외부직원의 Role 정보를 수정한다.(das 1.0 로그인 서비스 현재 사용하지 않는 로직)
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateLoginSucess(String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n update DAS.OUTSIDER_INFO_TBL set ");
		buf.append("\n 	PW_ERN = 0, ");
		buf.append("\n 	MOD_DT = ?, ");
		buf.append("\n 	MODRID = ? ");
		buf.append("\n where OUT_USER_ID = ?  ");		

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######updateLoginSucess######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, dateTime);
			stmt.setString(++index, userId);
			stmt.setString(++index, userId);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
			}

			if(updateCount == 0)
			{
				//여기서 에러를 던진다.
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
			release(null, stmt, con);
		}
	}

	/**
	 * 사용자 로그인시 로그를 남긴다.(das 1.0 로그인 서비스 현재 사용하지 않는 로직)
	 * @param userId 사용자 ID
	 * @throws Exception 
	 */
	public void insertIdLog(String userId) throws Exception
	{
		StringBuffer buf = new StringBuffer();
		buf.append("\n insert into DAS.IDLOG_TBL( ");
		buf.append("\n 	USER_ID,  ");
		buf.append("\n 	ACCESSTIME,  ");
		buf.append("\n 	REGRID,  ");
		buf.append("\n 	REG_DT,  ");
		buf.append("\n 	MODRID,  ");
		buf.append("\n 	MOD_DT ");
		buf.append("\n ) ");
		buf.append("\n values(?, ?, ?, ?, ?, ?) ");

		boolean isUpdate = false;
		Connection con = null;
		PreparedStatement stmt = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######insertIdLog######## con : " + con);
			stmt = con.prepareStatement(buf.toString());

			int index = 0;

			String dateTime = CalendarUtil.getDateTime("yyyyMMddHHmmss");
			stmt.setString(++index, userId);
			stmt.setString(++index, dateTime);
			stmt.setString(++index, userId);
			stmt.setString(++index, dateTime);
			stmt.setString(++index, userId);
			stmt.setString(++index, dateTime);

			int updateCount = stmt.executeUpdate();

			if (logger.isDebugEnabled()) 
			{
				logger.debug("[Update Count]" + updateCount);
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
	 * 화면 사용 권한이 있는지 확인한다
	 * @param role 역할 코드
	 * @param auth 권한 코드
	 * @return
	 * @throws Exception 
	 */
	public int selectAuthByRole(String role, String auth) throws Exception
	{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//	logger.debug("######selectAuthByRole######## con : " + con);
			//stmt = LoggableStatement.getInstance(con, UserInfoStatement.selectAuthByRoleQuery());
			stmt = con.prepareStatement(UserInfoStatement.selectAuthByRoleQuery());

			int index = 0;
			stmt.setString(++index, role);
			stmt.setString(++index, auth);

			rs = stmt.executeQuery();

			rs.next();

			int count = rs.getInt(1);

			return count ;
		} 
		catch (Exception e) 
		{
			logger.error("role : " + role);
			logger.error("auth : " + auth);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}


}

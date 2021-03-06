package com.app.das.business;



import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.UserInfoDAO;
import com.app.das.business.dao.UserRoleDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.ErpAppointDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.business.transfer.TokenDO;
import com.app.das.log.DasPropHandler;
import com.app.das.services.AllOtherDBDeptInfoDOXML;
import com.app.das.services.AllOtherDBUserInfoDOXML;
import com.app.das.services.EmployeeInfoDOXML;
import com.app.das.services.FolderListDOXML;
import com.app.das.util.CommonUtl;
import com.sbs.nds.service.ServicePortTypeProxy;

/**
 * 사용자 관리(내부, 외부, 역할)에 대한 등록, 수정, 삭제, 조회, 비밀번호 변동 및 초기화에 대한 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class EmployeeRoleBusinessProcessor
{
	private Logger logger = Logger.getLogger(EmployeeRoleBusinessProcessor.class);

	private static final UserRoleDAO userRoleDAO = UserRoleDAO.getInstance();
	private static final UserInfoDAO userInfoDAO = UserInfoDAO.getInstance();
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();

	/**
	 * 외부직원 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public List getOutsiderEmployeeRoleList(NonEmployeeDASRoleDO condition) throws Exception
	{

		return userRoleDAO.selectOutsiderEmployeeRoleList(condition);

	}

	/**
	 * 특정 외부직원의 정보를 조회한다.
	 * @param perRegNo 외부직원의 주민번호
	 * @param commonDO 공통정보
	 * @return NonEmployeeDASRoleDO 외부직원 정보를 포함하고 있는 DataObject
	 */
	@Deprecated
	public List getOutsiderEmployeeRole(String perRegNo) throws Exception
	{

		try 
		{   						
			NonEmployeeDASRoleDO nonEmployeeDASRoleDO = null;
			//이미 존재하는 사용자인지를 검증한다.
			//존재하면 지정규직원 테이블을 조회한다.
			//존재하지 않으면 대리인 테이블을 조회한다.
			if(userRoleDAO.isThereNonEmployeeRole(perRegNo))
			{
				nonEmployeeDASRoleDO = userRoleDAO.selectOutEmployeeRole(perRegNo);
			}
			else
			{
				userRoleDAO.selectOutEmployeeRoleForNotExist(perRegNo);
			}

			//보증인 정보를 조회한다.
			List guarantorInfoDOList = userRoleDAO.selectGuarantorInfoList(perRegNo);

		} 
		catch (Exception e)
		{
			throw e;
		}
		return null;
	}

	/**
	 *  외부직원의 정보를 조회한다.
	 * @param perRegNo 외부직원의 주민번호
	 * @param userID 사용자id
	 * @return NonEmployeeDASRoleDO 외부직원 정보를 포함하고 있는 DataObject
	 */
	@Deprecated
	public List getOutsiderEmployeeRole(String perRegNo, String userID) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getOutsiderEmployeeRole][Input perRegNo]" + perRegNo);
			logger.debug("[getOutsiderEmployeeRole][Input userID]" + userID);			
		}

		try 
		{
			NonEmployeeDASRoleDO nonEmployeeDASRoleDO = null;
			//이미 존재하는 사용자인지를 검증한다.
			//존재하면 지정규직원 테이블을 조회한다.
			//존재하지 않으면 대리인 테이블을 조회한다.
			if(userRoleDAO.isThereNonEmployeeRole(perRegNo))
			{
				return userRoleDAO.selectOutEmployeeRole(perRegNo, userID);
			}
			else
			{
				userRoleDAO.selectOutEmployeeRoleForNotExist(perRegNo);
			}




		} 
		catch (Exception e)
		{
			throw e;
		}
		return null;
	}

	/**
	 * 외부직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원  정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public int updateOutEmployeeRole(NonEmployeeDASRoleDO roleDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateOutEmployeeRole][Input NonEmployeeDASRoleDO]" + roleDO);

		}

		try 
		{
			//사용자 정보를 수정한다.
			return  userRoleDAO.updateOutEmployeeRole(roleDO);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}

	/**
	 * 외부직원의 Role 정보를 등록 한다.
	 * @param roleDO 외부직원  정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public int insertOutEmployeeRole(NonEmployeeDASRoleDO roleDO)  throws Exception
	{
		if(logger.isDebugEnabled()) 
		{
			logger.debug("[insertOutEmployeeRole][Input NonEmployeeDASRoleDO]" + roleDO);
		}

		try 
		{
			if (roleDO.getPer_reg_no().length() >= 13)
			{
				//이미 존재하는 사용자인지를 검증한다.
				if(userRoleDAO.isThereNonEmployeeRole(roleDO.getPer_reg_no()))
				{
					DASException exception = new DASException(ErrorConstants.ALREADY_EXIST_USER, "이미 존재하는 사용자 입니다.");
					throw exception;
				}

			}
			//사용자 정보를 등록한다.

			return userRoleDAO.insertNonEmployeeRole(roleDO);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * 외부직원의 Role 정보를 삭제 한다.
	 * @param perRegNo 외부사용자의 주민번호
	 */	
	@Deprecated
	public int deleteOutEmployeeRole(String perRegNo)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[deleteOutEmployeeRole][Input perRegNo]" + perRegNo);
		}

		try 
		{
			//사용자 정보를 삭제한다.
			return userRoleDAO.deleteOutEmployeeRole(perRegNo);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 외부직원의 Role 정보를 삭제 한다.
	 * @param perRegNo 외부사용자의 주민번호
	 * @param commonDO 공통정보
	 * @param userId 사용자id
	 * @return List 외부사용자 정보 변경 내역을 포함하고 있는 List
	 * @param commonDO 공통정보
	 */	
	@Deprecated
	public void deleteOutEmployeeRole(String perRegNo, String userID, DASCommonDO commonDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[deleteOutEmployeeRole][Input perRegNo userID]" + perRegNo + " " + userID);
		}

		try 
		{
			//사용자 정보를 삭제한다.
			userRoleDAO.deleteOutEmployeeRole(perRegNo, userID, commonDO);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 외부사용자의 비밀번호를 초기화 시킨다.
	 * @param perRegNo 주민번호
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public void initialPassword(String perRegNo, DASCommonDO commonDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[initialPassword][Input perRegNo]" + perRegNo);
		}

		try 
		{
			userRoleDAO.updateOutEmployeePasswd(perRegNo, commonDO);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 외부사용자의 비밀번호를 초기화 시킨다.
	 * @param perRegNo 주민번호
	 * @param userID 사용자id
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public void initialPassword(String perRegNo, String userID, DASCommonDO commonDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[initialPassword][Input perRegNo]|" + perRegNo + "|");
		}

		try 
		{
			userRoleDAO.updateOutEmployeePasswd(perRegNo, userID, commonDO);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * 외부사용자의 비밀번호를변경한다.
	 * @param userId 외부 사용자 ID
	 * @param beforePasswd 변경전 패스워드
	 * @param afterPasswd 변경후 패스워드
	 */
	public int amendPassword(String userId, String beforePasswd, String afterPasswd)  throws Exception
	{
		//이전 패스워드 검증을 한다.
		userRoleDAO.validateNonEmployeeAmendPasswd(userId, beforePasswd);

		//비밀번호 변경을 한다.
		return userRoleDAO.updateOutEmployeePasswdAmendment(userId, afterPasswd);
	}

	/**
	 * 외부 사용자 변경 내역을 조회한다.
	 * @param perRegNo 외부사용자의 주민번호
	 * @param commonDO 공통정보
	 * @param userId 사용자id
	 * @return List 외부사용자 정보 변경 내역을 포함하고 있는 List
	 */
	@Deprecated
	public List getOutEmployeeHistoryList(String perRegNo, String userId, DASCommonDO commonDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getOutEmployeeHistoryList][Input perRegNo]" + perRegNo);
		}

		try 
		{
			PageDO pageDO = userRoleDAO.selectOutEmployeeRoleHistoryList(perRegNo, userId, 1, commonDO);

			return pageDO.getPageItems();
		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 화면 권한코드를 조회한다.
	 * @param screenNo 화면번호
	 * @param commonDO 우리시스템의 사용자 공통정보
	 * @return 권한여부코드
	 */
	public String getAuthScreen(String screenNo, DASCommonDO commonDO)  throws Exception
	{
		return userRoleDAO.selectAuthScreen(screenNo, commonDO);
	}

	/**
	 * 역활별 권한 정보를 목록 조회한다.
	 * @param role 역할코드
	 * @param commonDO 공통정보
	 * @return List AuthDO 를 포함하고 있는 List
	 */
	@Deprecated
	public List getAuthList(String role, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getAuthList][Input role]" + role);
		}

		try 
		{
			return userRoleDAO.selectAuthList(role, commonDO);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * 화면의 사용 권한 여부를 확인한다.
	 * @param role 역할코드
	 * @param auth 권한코드
	 * @return boolean
	 * @throws Exception 
	 * 	 
	 * */	
	@Deprecated
	public boolean isThereRight(String role, String auth) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[doHaveRight][Input auth]" + role + "  " + auth);
		}

		try 
		{
			int count = userInfoDAO.selectAuthByRole(role, auth);

			if(count > 0)
				return true;
			else
				return false;
		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * 역활별 권한 정보를 수정한다.
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public void updateAuthList(List authDOList, DASCommonDO commonDO) throws Exception
	{

		try 
		{
			userRoleDAO.updateAuthList(authDOList, commonDO);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}

	/**
	 * 특정 외부직원의 정보를 조회한다.(비밀번호 변경시)
	 * @param commonDO 공통정보
	 * @return NonEmployeeDASRoleDO 외부직원 정보를 포함하고 있는 DataObject
	 */
	@Deprecated
	public NonEmployeeDASRoleDO getEmployeeInfoForChangePasswd(DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getEmployeeInfoForChangePasswd][Input DASCommonDO]" + commonDO);
		}

		try 
		{
			NonEmployeeDASRoleDO nonEmployeeDASRoleDO = 
					userRoleDAO.selectOutEmployeeRole(commonDO.getPerRegNo()); 							

			return nonEmployeeDASRoleDO;
		} 
		catch (Exception e)
		{
			throw e;
		}
	}







	/**
	 * 직원 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public List getEmployeeRoleList(EmployeeInfoDO condition) throws Exception
	{
		if(StringUtils.isBlank(condition.getSearchtype())){
			return userRoleDAO.selecEmployeeRoleList(condition);
		}else if(condition.getSearchtype().equals("2")){

			String dep_cd= userRoleDAO.selectDepinfoForUser(condition.getReg_id());
			condition.setDept_cd(dep_cd);
			return userRoleDAO.selectMyEmployeeRoleList(condition);
		}
		return null;
	}



	/**
	 * 직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	public int updateEmployeeRole(EmployeeInfoDO roleDO)  throws Exception
	{
		try 
		{
			String sResult="";


			int result =0;

			/*
			 * 기본적인 정보를 DB에 반영한다.
			 */
			result = userRoleDAO.updateEmployeeRole(roleDO);

			//20120727 사용자동기화 로직 추가 ifcms동기화 로직
			if(!roleDO.getApprove_status().equals("3")) {

				List resultList = new ArrayList();

				roleDO.setDelete_yn("N");
				roleDO.setApprove_status("2");


				if(!roleDO.getApprove_status().equals("3")){
					roleDO.setGubun("U");
				}else{
					roleDO.setGubun("D");	
				}

				resultList.add(roleDO);

				String _xml = "";
				if (resultList != null && resultList.size() > 0) {
					Iterator _iter = resultList.iterator();
					while (_iter.hasNext()) {
						AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
						_do2.setDO(_iter.next());
						_xml = _xml + _do2.getIfCmsXML();
					}
				}

				//ifcms에 사용자 정보를 등록한다.
				StringHolder sessionid = new StringHolder();
				StringHolder opcode = new StringHolder("runusertransaction");
				com.sbs.ifcms.service.ServicePortTypeProxy port = new com.sbs.ifcms.service.ServicePortTypeProxy();

				try {
					sResult = port.SOAPInterface(
							"" 							//java.lang.String systemversion
							,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
							,opcode 					//javax.xml.rpc.holders.StringHolder opcode
							,_xml// java.lang.String ksccRequest
							,"" 						// java.lang.String exvalue1
							,""  						// java.lang.String exvalue2
							,""  						// java.lang.String exvalue3
							,""  							// java.lang.String exvalue4
							);
				} catch (RemoteException e) {
					logger.error("ifcms user info update error", e);
				}
			}



			/*
			 * 비밀번호 동기화 로직
			 */
			if(org.apache.commons.lang.StringUtils.isNotBlank(roleDO.getPassword())){

				EmployeeInfoDO trans = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(roleDO.getSbs_user_ID());

				// AES 알고리즘으로 암호화 후 보낸다
				JNI_Des hj = new JNI_Des();
				String password = "";
				password = hj.setEncryption(dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND", roleDO.getPassword());
				trans.setNewPassword(password);


				List resultList2 = new ArrayList();

				resultList2.add(trans);
				String _xml2 = "";
				if (resultList2 != null && resultList2.size() > 0) {

					Iterator _iter = resultList2.iterator();
					while (_iter.hasNext()) {
						AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
						_do2.setDO(_iter.next());
						_xml2 = _xml2 + _do2.getIfCmsPwSycn();
					}

				}

				//IFCMS에 비밀번호 변경 사항을 저장한다.
				StringHolder sessionid = new StringHolder();
				StringHolder opcode = new StringHolder("runusertransaction");
				com.sbs.ifcms.service.ServicePortTypeProxy port = new com.sbs.ifcms.service.ServicePortTypeProxy();
				try {
					sResult = port.SOAPInterface(
							"" 							//java.lang.String systemversion
							,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
							,opcode 					//javax.xml.rpc.holders.StringHolder opcode
							,_xml2// java.lang.String ksccRequest
							,"" 						// java.lang.String exvalue1
							,""  						// java.lang.String exvalue2
							,""  						// java.lang.String exvalue3
							,""  							// java.lang.String exvalue4
							);
				} catch (RemoteException e) {
					logger.error("ifcms user password update error", e);
				}

			}

			if(roleDO.getAcct_code().equals(CodeConstants.Pacode.RA)){

				List resultList = new ArrayList();
				roleDO.setUpdate_yn("Y");
				roleDO.setNewPassword(roleDO.getPassword());
				roleDO.setPassword("");
				if(roleDO.getDelete_yn().equals("Y")){
					roleDO.setType(CodeConstants.Pacode.DELETE); //type 001 신청 002 수정 003 삭제
				}else{
					roleDO.setType(CodeConstants.Pacode.UPDATE);
				}
				resultList.add(roleDO);


				//사용자 정보를 등록한다.pa
				String _xml2 = "";
				if (resultList != null && resultList.size() > 0) {

					Iterator _iter = resultList.iterator();
					while (_iter.hasNext()) {
						EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
						_do2.setDO(_iter.next());
						_xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
						_xml2 = _xml2 + _do2.getSubXML();
						_xml2 = _xml2 +"</das>";
					}

				}

				com.sbs.pa.service.Service1SoapProxy port2 = new com.sbs.pa.service.Service1SoapProxy();
				try {
					result =  port2.wsPAUserInfoManager(_xml2);
					logger.debug("Service1SoapProxy Result"+result);
				} catch (RemoteException e) {
					logger.error("Service1SoapProxy error", e);
				}


				return	result;
			} else {
				//비직원이면 바로 업뎃한다
				//20130328 비밀번호 변경을 할때에는 중복채크를 아니 한다.
				result=userRoleDAO.updatePasswd(roleDO);		

				// 비직원 동기화는 ifcms에서 처리한다.
				return result;
			}
		} catch (Exception e){
			throw e;
		} 
	}



	/**
	 * 직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateEmployeeRoleYN(EmployeeInfoDO roleDO)  throws Exception{
		String sXml ="";

		try 
		{
			String sResult="";
			EmployeeInfoDO info = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(roleDO.getSbs_user_ID());
			if(info.getAcct_code().equals(CodeConstants.Pacode.RA)||info.getAcct_code().equals(CodeConstants.Pacode.RB)){
				JNI_Des hj  = new JNI_Des();

				String password="";
				if(!"".equals( info.getPassword())){
					password = hj.getDecryption( dasHandler.getProperty("AD_CRYPTO_KEY"),  dasHandler.getProperty("AD_DEFAULT_HEX"), info.getPassword());
					if(logger.isDebugEnabled()) {
						logger.debug("AD Password after check: "+password);
					}
					info.setPassword(password);        
				}
			}
			if(info.getAcct_code().equals(CodeConstants.Pacode.SB)||info.getAcct_code().equals(CodeConstants.Pacode.SA)||info.getAcct_code().equals(CodeConstants.Pacode.SC)||info.getAcct_code().equals(CodeConstants.Pacode.RB)||roleDO.getApprove_status().equals("3")){
				int result =userRoleDAO.updateEmployeeRoleYN(roleDO);
				return result;
			} else {
				List resultList = new ArrayList();
				if(roleDO.getDelete_yn().equals("Y")){
					info.setType(CodeConstants.Pacode.DELETE);//type 001 신청 002 수정 003 삭제
					info.setUpdate_yn("Y");
					info.setDelete_yn("Y");
				}else{
					info.setType(CodeConstants.Pacode.INSERT);
					info.setUpdate_yn("N");
				}
				resultList.add(info);  

				//사용자 정보를 등록한다.pa
				String _xml2 = "";
				if (resultList != null && resultList.size() > 0) {

					Iterator _iter = resultList.iterator();
					while (_iter.hasNext()) {
						EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
						_do2.setDO(_iter.next());
						_xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
						_xml2 = _xml2 + _do2.getSubXML();
						_xml2 = _xml2 +"</das>";
					}

				}

				//20120829 최효정과장님 요청으로 막음 오픈시 반드시 해제
				int paReseult =0;
				com.sbs.pa.service.Service1SoapProxy port2 = new com.sbs.pa.service.Service1SoapProxy();
				try {
					paReseult =  port2.wsPAUserInfoManager(_xml2);
					logger.debug("paReseult"+paReseult);
				} catch (RemoteException e) {
					logger.error("Service1SoapProxy connect error", e);
				}

				int result =userRoleDAO.updateEmployeeRoleYN(roleDO);
				return 1;
			}

		} catch (Exception e) {
			throw e;
		} 

	}





	/**
	 * 직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	@Deprecated
	public int updateNonEmployeeRoleYN(EmployeeInfoDO roleDO)  throws Exception
	{

		return  userRoleDAO.approveNonEmployeeRole(roleDO);

	}

	/**
	 * 직원의 Role 정보를 등록 한다.
	 * @param roleDO 직원 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public String insertEmployeeRole(EmployeeInfoDO roleDO)  throws Exception
	{
		try 
		{

			String sResult="";

			//이미 존재하는 사용자인지를 검증한다.
			if(roleDO.getAcct_code().equals(CodeConstants.Pacode.RA)||roleDO.getAcct_code().equals(CodeConstants.Pacode.RB)){
				if(userRoleDAO.isThereUserId(roleDO.getCocd(), roleDO.getUser_num()))
				{
					String result = "ERROR : 이미존재하는 사용자입니다";
					return result;
				}
			} else {
				if(org.apache.commons.lang.StringUtils.isBlank(roleDO.getMobile())) {
					String result = "ERROR : 전화번호가 존재하지 않습니다.";
					return result;
				} else {
					if(userRoleDAO.isThereEmployeeRole(roleDO.getMobile().replace("-", "")))
					{
						String result = "ERROR : 이미존재하는 사용자입니다";
						return result;
					}
				}
			}


			String result =userRoleDAO.insertEmployeeRole(roleDO);

			//20120727 사용자동기화 로직 추가 ifcms동기화 로직
			EmployeeInfoDO id = new EmployeeInfoDO();
			if(roleDO.getAcct_code().equals("RA")||roleDO.getAcct_code().equals("RB")){
				id=userRoleDAO.selectUserId(roleDO.getUser_num());
			}else {
				id=userRoleDAO.selectUserId2(roleDO.getMobile());	
			}

			List resultList = new ArrayList();
			roleDO.setSbs_user_ID(id.getSbs_user_ID());
			//비직원인 경우 AES 알고리즘으로 암호화 후 보낸다
			if(roleDO.getAcct_code().equals("SA")||roleDO.getAcct_code().equals("SB")||roleDO.getAcct_code().equals("SC")){
				JNI_Des hj = new JNI_Des();
				String password = "";
				password = hj.setEncryption( //SBSPND
						dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND",
						roleDO.getPassword());
				roleDO.setPassword(password);
			}else{
				roleDO.setPassword(id.getPassword());	
			}

			roleDO.setDelete_yn("N");
			roleDO.setApprove_status("1");
			roleDO.setUpdate_yn("N");
			roleDO.setGubun("C");
			resultList.add(roleDO);
			String _xml = "";
			if (resultList != null && resultList.size() > 0) {

				Iterator _iter = resultList.iterator();
				while (_iter.hasNext()) {
					AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
					_do2.setDO(_iter.next());
					_xml = _xml + _do2.getIfCmsXML();
				}

			}


			//ifcms에 사용자 정보를 등록한다.
			StringHolder sessionid = new StringHolder();
			StringHolder opcode = new StringHolder("runusertransaction");
			com.sbs.ifcms.service.ServicePortTypeProxy port = new com.sbs.ifcms.service.ServicePortTypeProxy();

			try {
				sResult = port.SOAPInterface(
						"" 							//java.lang.String systemversion
						,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
						,opcode 					//javax.xml.rpc.holders.StringHolder opcode
						,_xml// java.lang.String ksccRequest
						,"" 						// java.lang.String exvalue1
						,""  						// java.lang.String exvalue2
						,""  						// java.lang.String exvalue3
						,""  							// java.lang.String exvalue4
						);
				if(logger.isDebugEnabled()) {
					//logger.debug("sResult"+sResult);
				}
			} catch (RemoteException e) {
				logger.error("ifcms call error", e);
			}

			return result;
		}catch (Exception e){
			throw e;
		} 
	}


	/**
	 * 부서 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 */
	public List getDepInfoList(DepInfoDO condition) throws Exception
	{

		return userRoleDAO.selecDepInfoList(condition);

	}

	/**
	 * 본부코드와 국코드를 조회한다.
	 * @param condition                                                                                                                                                                                              
	 * @return  List                                                                                                                                                                   
	 * @throws Exception
	 */
	public List getDepList(DepInfoDO condition) throws Exception
	{

		return userRoleDAO.selecDepList(condition);

	}


	/**
	 * 부서 정보를 목록 조회한다.
	 * @param 
	 * @return List
	 */
	public List getDepInfo() throws Exception
	{

		return userRoleDAO.selecDepInfo();

	}


	/**
	 * 부서 정보를 목록 조회한다.
	 * @param cocd 회사코드
	 * @return List
	 */
	public List getDepCocdInfo(String cocd) throws Exception
	{

		return userRoleDAO.selectCocdInfo(cocd);

	}

	/**
	 *  부서 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @return 성공 1 실패 0 
	 */
	public int insertDepInfo(DepInfoDO roleDO)  throws Exception
	{

		try 
		{
			String sResult="";

			//pds연동부분
			String _xml = "";
			/*
			List resultList = new ArrayList();

			resultList.add(roleDO);

			if (resultList != null && resultList.size() > 0) {

				Iterator _iter = resultList.iterator();
				while (_iter.hasNext()) {
					AllOtherDBDeptInfoDOXML _do2 = new AllOtherDBDeptInfoDOXML();
					_do2.setDO(_iter.next());
					_xml = _xml + _do2.toXML();
				}

				if (logger.isDebugEnabled())
					logger.debug("_xml" + _xml);

			}
			 */
			if(roleDO != null) {
				AllOtherDBDeptInfoDOXML _do2 = new AllOtherDBDeptInfoDOXML();
				_do2.setDO(roleDO);
				_xml = _do2.toXML();
			}

			//PDS에 사용자 정보를 등록한다.
			StringHolder sessionid = new StringHolder();
			StringHolder opcode = new StringHolder("runusertransaction");
			com.sbs.pds.service.ServicePortTypeProxy port = new com.sbs.pds.service.ServicePortTypeProxy();
			try {
				sResult = port.SOAPInterface(
						"" 							//java.lang.String systemversion
						,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
						,opcode 					//javax.xml.rpc.holders.StringHolder opcode
						,_xml// java.lang.String ksccRequest
						,"" 						// java.lang.String exvalue1
						,""  						// java.lang.String exvalue2
						,""  						// java.lang.String exvalue3
						,""  							// java.lang.String exvalue4
						);
				if(logger.isDebugEnabled()) {
					//logger.debug("sResult"+sResult);
				}
			} catch (RemoteException e) {
				logger.error("ServicePortTypeProxy error", e);
				throw e;
			}


			return userRoleDAO.insertDepInfo(roleDO);

		}catch (Exception e){
			throw e;
		} 

	}

	/**
	 * 부서 정보를 수정한다.
	 * @param roleDO 부서 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */

	public int updateDepInfo(DepInfoDO roleDO)  throws Exception
	{
		if(roleDO.getStatus().equals("i")){
			userRoleDAO.insertDepInfo2(roleDO);
		}else if(roleDO.getStatus().equals("u")){
			userRoleDAO.updateDepInfo2(roleDO);
		}else if(roleDO.getStatus().equals("d")){
			userRoleDAO.deleteDepInfo2(roleDO);
		}


		return 1;
	}
	/**
	 * 역활 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 */
	public List getRoleInfoList(RoleInfoDO condition) throws Exception
	{

		return userRoleDAO.selecroleInfoList(condition);

	}



	/**
	 * 역활 정보를 수정한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return 성공 1 실패 0 
	 */
	public int updateRoleInfo(RoleInfoDO roleDO)  throws Exception
	{

		if(roleDO.getGubun().equals("")){
			//DAS 권한 정보를 수정한다.
			return  userRoleDAO.updateRoleInfo(roleDO);
		}else{
			//모니터링 권한 정보를 수정한다.
			if(roleDO.getGubun().equals("D")){
				return  userRoleDAO.deleteMonitoringInfoForCode(roleDO);	
			}else {
				return  userRoleDAO.updateMonitoringInfoForCode(roleDO);		
			}
		}

	}



	/**
	 * 역활 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 */
	public List getAuthorInfoList(AuthorDO condition) throws Exception
	{

		return userRoleDAO.selectAuthorInfoList(condition);

	}

	/**
	 * 권한 정보를 수정한다.
	 * @param roleDO 조회조건을 포함하고 있는 DataObject
	 * @return 성공 1 실패 0
	 */
	public int updatAuthorInfo(AuthorDO roleDO)  throws Exception
	{

		//사용자 정보를 수정한다.
		if(roleDO.getSystem().equals("")){
			return  userRoleDAO.updateAuthorInfo(roleDO);
		}else{
			return  userRoleDAO.updateAuthorInfoForMonitoring(roleDO);	
		}

	}

	/**
	 * 외부직원 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 */
	public List getNonEmployeeRoleList(EmployeeInfoDO condition) throws Exception
	{

		String dep_cd= userRoleDAO.selectDepinfoForUser(condition.getSbs_user_ID());
		String pgm_id = userRoleDAO.selectPgminfoForUser2(condition.getSbs_user_ID(),dep_cd);
		if(pgm_id.equals("")){
			condition.setPds_pgm_id("1111111111111");
		}else{
			condition.setPds_pgm_id(pgm_id);	
		}
		condition.setDept_cd(dep_cd);

		return userRoleDAO.selectNonEmployeeRoleList(condition);

	}


	/**
	 * 외부직원의 Role 정보를 수정한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	public int updateNonEmployeeRole(NonEmployeeInfoDO roleDO)  throws Exception
	{

		//사용자 정보를 수정한다.
		return  userRoleDAO.updateNonEmployeeRole(roleDO);

	}




	/**
	 * 외부직원의 Role 정보를 등록 한다.
	 * @param roleDO 외부직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 */
	public int insertNonEmployeeRole(EmployeeInfoDO roleDO)  throws Exception
	{
		try 
		{
			String sResult="";

			if (roleDO.getPer_reg_no().length() >= 13) {
				//이미 존재하는 사용자인지를 검증한다.
				if(userRoleDAO.isThereNonEmployeeRole(roleDO.getPer_reg_no())) {
					DASException exception = new DASException(ErrorConstants.ALREADY_EXIST_USER, "이미 존재하는 사용자 입니다.");
					throw exception;
				}

			}

			//사용자 정보를 등록한다.
			int count = userRoleDAO.insertOutEmployeeRole(roleDO);

			//pds연동부분
			String result =userRoleDAO.insertEmployeeRole(roleDO);

			//무조건 PDS, NDS동기화 함에 따라 추가 2011.3.29 BY ASURA
			roleDO.setPds("Y");
			roleDO.setNds("Y");

			if(roleDO.getPds().equals("Y")){
				EmployeeInfoDO id = userRoleDAO.selectUserId(roleDO.getUser_num());
				//List resultList = new ArrayList();
				roleDO.setSbs_user_ID(id.getSbs_user_ID());
				roleDO.setPassword(id.getPassword());
				roleDO.setDelete_yn("N");
				roleDO.setApprove_status("1");
				roleDO.setUpdate_yn("N");
				//resultList.add(roleDO);
				String _xml = "";
				/*
				if (resultList != null && resultList.size() > 0) {

					Iterator _iter = resultList.iterator();
					while (_iter.hasNext()) {
						AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
						_do2.setDO(_iter.next());
						_xml = _xml + _do2.getSubXML();
					}

					if (logger.isDebugEnabled())
						logger.debug("_xml" + _xml);

				}
				 */
				AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
				_do2.setDO(roleDO);
				_xml = _do2.getSubXML();

				//PDS에 사용자 정보를 등록한다.
				StringHolder sessionid = new StringHolder();
				StringHolder opcode = new StringHolder("runusertransaction");
				com.sbs.pds.service.ServicePortTypeProxy port = new com.sbs.pds.service.ServicePortTypeProxy();
				try {
					sResult = port.SOAPInterface(
							"" 							//java.lang.String systemversion
							,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
							,opcode 					//javax.xml.rpc.holders.StringHolder opcode
							,_xml// java.lang.String ksccRequest
							,"" 						// java.lang.String exvalue1
							,""  						// java.lang.String exvalue2
							,""  						// java.lang.String exvalue3
							,""  							// java.lang.String exvalue4
							);
					if(logger.isDebugEnabled()) {
						//logger.debug("sResult"+sResult);
					}
				} catch (RemoteException e) {
					logger.error("PDS user add proxy call error", e);
					//throw e;
				}

			}

			if(roleDO.getNds().equals("Y")){
				EmployeeInfoDO id = userRoleDAO.selectUserId(roleDO.getUser_num());
				//List resultList = new ArrayList();
				roleDO.setSbs_user_ID(id.getSbs_user_ID());
				roleDO.setPassword(id.getPassword());
				roleDO.setDelete_yn("N");
				roleDO.setApprove_status("1");
				roleDO.setUpdate_yn("N");

				String _xml = "";
				/*
				resultList.add(roleDO);
				if (resultList != null && resultList.size() > 0) {

					Iterator _iter = resultList.iterator();
					while (_iter.hasNext()) {
						AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
						_do2.setDO(_iter.next());
						_xml = _xml + _do2.getSubXML2();
					}

					if (logger.isDebugEnabled())
						logger.debug("_xml" + _xml);

				}
				 */

				AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
				_do2.setDO(roleDO);
				_xml = _do2.getSubXML2();

				//NDS에 사용자 정보를 등록한다.
				StringHolder sessionid = new StringHolder();
				StringHolder opcode = new StringHolder("runusertransaction");
				ServicePortTypeProxy port = new ServicePortTypeProxy();
				try {
					sResult = port.SOAPInterface(
							"" 							//java.lang.String systemversion
							,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
							,opcode 					//javax.xml.rpc.holders.StringHolder opcode
							,_xml// java.lang.String ksccRequest
							,"" 						// java.lang.String exvalue1
							,""  						// java.lang.String exvalue2
							,""  						// java.lang.String exvalue3
							,""  							// java.lang.String exvalue4
							);
					if(logger.isDebugEnabled()) {
						//logger.debug("sResult"+sResult);
					}
				} catch (RemoteException e) {
					logger.error("NDS Proxy call error", e);
				}

			}

			return count;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * ERP 발령정보 순번을 GET 
	 * @param condition  순번을 GET 하기위한 정보
	 * @return List
	 * @throws Exception
	 */
	public List getERPAppointList(ErpAppointDO condition) throws Exception
	{
		return userRoleDAO.selectERPAppointList(condition);

	}




	/**
	 * ERP 발령정보를 등록한다.
	 * @param roleDO   ERP 발령                                                                                                                                                                                           
	 * @return  성공 1 실패 0                                                                                                                                                                                            
	 * @throws Exception 
	 */
	public int[] insertERPAppoint(List roleDO)  throws Exception
	{

		return userRoleDAO.insertERPAppoint(roleDO);

	}

	/**
	 * ERP 정보를 테이블에 등록한다.(벌크)
	 * @param roleDO    ERP 정보                                                                                                                                                                                           
	 * @return TRUE, FALSE                                                                                                                                                                              
	 * @throws Exception
	 */
	public boolean insertERPUserInfo(List roleDO)  throws Exception
	{

		return userRoleDAO.insertERPUserInfo(roleDO);

	}



	/**  
	 * 패스워드를 초기화한다
	 * @param      user_id       사용자 ID                                                                                                                                                                             
	 * @return      설정값  0000                                                                                                                                                                                 
	 * @throws Exception 
	 */
	public String updateInitPassword(String user_id)  throws Exception
	{

		return  userRoleDAO.updateInitPassword(user_id);

	}


	/**  
	 * 토큰을 얻어온다
	 * @param  user_num      사번                                                                                                                                                                                  
	 * @return   list       토큰정보가 담긴 리스트                                                                                                                                                                                   
	 * @throws Exception 
	 */
	@Deprecated
	public List getToken(String user_num) throws Exception
	{

		TokenDO token = userRoleDAO.selecTokenInfo(user_num); 		
		List list = (List)token;
		return	list;			

	}

	/**
	 * ERP 발령정보를 로컬db에 수정한다
	 * @param roleDO                                                                                                                                                                                              
	 * @return        성공 1 실패 0                                                                                                                                                                                      
	 * @throws Exception
	 */
	public int updateERPAppoint(List roleDO)  throws Exception
	{
		String codeList ="89,91,92,93,94,95,96";
		try 
		{
			int index = 0;
			for(int i=0;i<roleDO.size();i++){
				index = 0;
				ErpAppointDO pgminfoDO = (ErpAppointDO)roleDO.get(i);
				boolean resutl =userRoleDAO.isThereERPEmployeeRole(String.valueOf(pgminfoDO.getUser_no()), pgminfoDO.getCo_cd());

				if(resutl){
					//로컬db에서 부서정보를 가져와서 비교한다. 
					//플레그가 U 이면 업데이트
					if(pgminfoDO.getOder_flag().equals("U")){	
						userRoleDAO.updateErpUserInfo(pgminfoDO);
					} else if(pgminfoDO.getOder_flag().equals("D")){
						userRoleDAO.updateErpUserDelYN(String.valueOf(pgminfoDO.getUser_no()));

						//pa 삭제
						EmployeeInfoDO info = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(pgminfoDO.getUser_no());
						//List resultList = new ArrayList();
						info.setType("003"); //type 001 신청, 002 수정, 003 삭제
						info.setUpdate_yn("Y");
						info.setDelete_yn("Y");

						String _xml2 = "";
						/*
						resultList.add(info);
						if (resultList != null && resultList.size() > 0) {

							Iterator _iter2 = resultList.iterator();
							while (_iter2.hasNext()) {
								EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
								_do2.setDO(_iter2.next());
								_xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
								_xml2 = _xml2 + _do2.getSubXML();
								_xml2 = _xml2 +"</das>";
							}

							if (logger.isDebugEnabled())
								logger.debug("_xml" + _xml2);

						}
						 */

						EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
						_do2.setDO(info);
						_xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
						_xml2 = _xml2 + _do2.getSubXML();
						_xml2 = _xml2 +"</das>";

						if(logger.isDebugEnabled())
							logger.debug(_xml2);

						int paReseult =0;

						com.sbs.pa.service.Service1SoapProxy port2 = new com.sbs.pa.service.Service1SoapProxy();
						try {
							paReseult =  port2.wsPAUserInfoManager(_xml2);
							logger.debug("paReseult"+paReseult);
						} catch (RemoteException e) {
							logger.error("Service1SoapProxy call error", e);
						}

					} 

				}
			}

			return 1;
		} catch (Exception e) {
			throw e;
		}
	}



	/**
	 * 타 DB에서 사용자 정보를 받아온다.
	 * @param roleDO     타 DB에서 사용자 정보                                                                                                                                                                                          
	 * @return   성공 1  실패 0  
	 * @throws Exception
	 *  */
	public int insertOtherUserInfo(List roleDO)  throws Exception
	{
		try 
		{
			int index = 0;
			String passwd="";
			for(int i=0;i<roleDO.size();i++){
				index = 0;
				EmployeeInfoDO infoDO = (EmployeeInfoDO)roleDO.get(i);

				//이미 존재하는 사용자인지를 검증한다.
				if(userRoleDAO.isThereUserInfo(infoDO.getSbs_user_ID()))
				{
					if(infoDO.getFlag().equalsIgnoreCase("U")||infoDO.getFlag().equalsIgnoreCase("S")){
						if(!infoDO.getPassword().equals("")){
							if(!infoDO.getAcct_code().equals("RA")){

								JNI_Des hj  = new JNI_Des();
								String password="";
								if(!"".equals( infoDO.getPassword())){
									password = hj.getDecryption( dasHandler.getProperty("AD_CRYPTO_KEY"),  dasHandler.getProperty("AD_DEFAULT_HEX"), infoDO.getPassword());
									if(logger.isDebugEnabled()) {
										logger.debug("[password] ======  " + password);
									}
									passwd=userRoleDAO.getPasswd(password);
									infoDO.setPassword(passwd);        
								}
								//사용자 정보를 등록한다.
							}

							userRoleDAO.updateOtherUserPassWd(infoDO);
						}else if(infoDO.getPassword().equals("")||infoDO.getFlag().equalsIgnoreCase("U")||infoDO.getFlag().equalsIgnoreCase("S")){
							userRoleDAO.updateOtherUserInfo(infoDO);

						}
					}else if(infoDO.getFlag().equalsIgnoreCase("D")){
						userRoleDAO.DeleteOtherUserInfo(infoDO);	
					}

				}else{
					if(!infoDO.getAcct_code().equals("RA")){

						JNI_Des hj  = new JNI_Des();
						String password="";
						if(!"".equals( infoDO.getPassword())){
							password = hj.getDecryption( dasHandler.getProperty("AD_CRYPTO_KEY"),  dasHandler.getProperty("AD_DEFAULT_HEX"), infoDO.getPassword());
							if(logger.isDebugEnabled()) {
								logger.debug("[sb  password] ======  " + password);
							}
							passwd=userRoleDAO.getPasswd(password);
							infoDO.setPassword(passwd);        
						}
						//사용자 정보를 등록한다.
					}

					userRoleDAO.insertOtherUserInfo(infoDO);
				}


			}
			return 1;
		} catch (Exception e) {
			throw e;
		}


	}


	/**
	 * 타 DB에서 사용자 정보를 받아온다.
	 * @param roleDO                                                                                                                                                                                              
	 * @return       성공 1  실패 0             
	 * @throws Exception
	 *  */
	public int insertOtherDepInfo(List roleDO)  throws Exception
	{
		try 
		{
			int index = 0;
			for(int i=0;i<roleDO.size();i++){
				index = 0;
				DepInfoDO infoDO = (DepInfoDO)roleDO.get(i);


				if(infoDO.getStatus().equalsIgnoreCase("c")) {
					if(userRoleDAO.isThereDepInfo(infoDO.getDept_cd())){
						userRoleDAO.updateOtherUserInfo(infoDO);
					}else{
						userRoleDAO.insertOtherDepInfo(infoDO);	
					}
				}else if(infoDO.getStatus().equalsIgnoreCase("u")){
					userRoleDAO.updateOtherUserInfo(infoDO);
				}else if(infoDO.getStatus().equalsIgnoreCase("d")){
					userRoleDAO.deleteOtherDeptInfo(infoDO);
				}else if(infoDO.getStatus().equalsIgnoreCase("S")){
					if(userRoleDAO.isThereDepInfo(infoDO.getDept_cd())){
						List deptList = userRoleDAO.getDepinfo(infoDO.getDept_cd());
						if(deptList != null && !deptList.isEmpty()) {

							DepInfoDO depInfoDO = (DepInfoDO)deptList.get(0);
							depInfoDO.setCocd(infoDO.getCocd());
							depInfoDO.setDept_cd(infoDO.getDept_cd());
							depInfoDO.setDept_nm(infoDO.getDept_nm());
							depInfoDO.setSup_dept_cd(infoDO.getSup_dept_cd());
							depInfoDO.setSup_dept_nm(infoDO.getSup_dept_nm());

							userRoleDAO.updateOtherUserInfo(depInfoDO);
						}
					}else{
						userRoleDAO.insertOtherDepInfo(infoDO);	
					}
				}
			}
			return 1;
		} catch (Exception e) {
			throw e;
		}



	}

	/**
	 * 타시스템과 직원 정보를 동기화한다.
	 * @param sbs_user_id 사용자 ID                                                                                                                                                                                              
	 * @return   LIST                                                                                                                                                                              
	 * @throws Exception
	 */
	public List getOtherEmployeeList(String sbs_user_id) throws Exception
	{
		return userRoleDAO.selectOtherEmployeeList(sbs_user_id);

	}



	/**
	 * 타시스템과 부서 정보를 동기화한다.
	 * @param cocd,   dept_cd 회사코드, 부서코드                                                                                                                                                                                            
	 * @return LIST                                                                                                                                                                              
	 * @throws Exception
	 */
	public List getOhterDepInfoList(String cocd, String dept_cd) throws Exception
	{

		return userRoleDAO.selectOhterDepInfoList(cocd,dept_cd);

	}



	/**
	 * 직원의 Role 정보를 등록 한다.
	 * @param roleDO 직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertRealEmployeeRole(EmployeeInfoDO roleDO)  throws Exception
	{
		int result=0;
		if(roleDO.getSuccess_yn().equals("200")||roleDO.getSuccess_yn().equals("300"))	{
			result = 1;
		}else{				
			result =  0;
		}
		return result;
	}

	/**
	 * 직원의 Role 정보를 등록 한다.
	 * @param roleDO 직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateRealEmployeeRole(EmployeeInfoDO roleDO)  throws Exception
	{
		try  {
			int result=0;
			String sResult="";
			if(roleDO.getSuccess_yn().equals("200")||roleDO.getSuccess_yn().equals("300")||roleDO.getSuccess_yn().equals("400"))	{

				userRoleDAO.updateEmployeeRole(roleDO);


				// 이곳에서 바로 동기화를 한다.

				EmployeeInfoDO trans = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(roleDO.getSbs_user_ID());

				//				List resultList2 = new ArrayList();
				//
				//				resultList2.add(trans);
				String _xml2 = "";
				if(!roleDO.getNewPassword().equals("")){
					/*
					if (resultList2 != null && resultList2.size() > 0) {

						Iterator _iter = resultList2.iterator();
						while (_iter.hasNext()) {
							AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
							_do2.setDO(_iter.next());
							_xml2 = _xml2 + _do2.getSubXML3();
						}

						if (logger.isDebugEnabled())
							logger.debug("_xml2" + _xml2);

					}
					 */
					AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
					_do2.setDO(trans);
					_xml2 = _do2.getSubXML3();

					result = 1;
				}
			}else{				

				return 0;
			}
			return result;

		}catch (Exception e) {
			logger.error("updateRealEmployeeRole error", e);
		}
		return 0;

	}


	/**
	 * 직원의 Role 정보를 등록 한다.
	 * @param roleDO 직원 Role 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateRealEmployeeRoleYN(EmployeeInfoDO roleDO)  throws Exception
	{

		try {
			int result=0;

			if(roleDO.getSuccess_yn().equals("200")||roleDO.getSuccess_yn().equals("300")||roleDO.getSuccess_yn().equals("400"))	{
				roleDO.setApprove_status("2");
				roleDO.setApprove_yn("Y");
				result = userRoleDAO.approveEmployeeRole(roleDO);
			}else  if(roleDO.getSuccess_yn().equals("500")){				
				roleDO.setApprove_status("5");
				roleDO.setApprove_yn("N");
				result = userRoleDAO.approveEmployeeRole(roleDO);
			}
			return result;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * PDS folderList 정보를 등록한다.(벌크)
	 * PDS Soap Call ( pds_ex_get_programinfo)
	 * @param user_id   사용자ID                                                                                                                                                                                          
	 * @return        xml NDS 폴더 리스트                                                                                                                                                                                           
	 * @throws Exception 
	 */
	public String insertPdsFolderAll(String user_id)  throws Exception
	{


		String sResult="";
		String _xml="";

		FolderListDOXML _do2 = new FolderListDOXML();
		_xml =_do2.getSubXML(user_id);

		if(logger.isDebugEnabled()) {
			logger.debug("request user: "+user_id+", make_xml: "+_xml);
		}

		//folderList 정보를 등록
		StringHolder sessionid = new StringHolder();
		StringHolder opcode = new StringHolder("runusertransaction");
		com.sbs.pds.service.ServicePortTypeProxy port = new com.sbs.pds.service.ServicePortTypeProxy();
		try {
			sResult = port.SOAPInterface(
					"" 							//java.lang.String systemversion
					,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
					,opcode 					//javax.xml.rpc.holders.StringHolder opcode
					,_xml// java.lang.String ksccRequest
					,"" 						// java.lang.String exvalue1
					,""  						// java.lang.String exvalue2
					,""  						// java.lang.String exvalue3
					,""  							// java.lang.String exvalue4
					);
			if(logger.isDebugEnabled()) {
				//logger.debug("sResult  "+sResult);
			}
			String PdsInfo = CommonUtl.transXMLText(sResult);
			return PdsInfo;
		} catch (Exception e) {
			throw e;
		}

	}


	/**
	 * NDS folderList 정보를 등록한다.(벌크)
	 * @param user_id   사용자 아이디                                                                                                                                                                                           
	 * @return     xml NDS 폴더 리스트                                                                                                                                                                                        
	 * @throws Exception 
	 * @throw Soap Call ( nds_ex_service_folderlist )
	 * @pars DASException
	 */
	public String insertNdsFolderAll(String user_id)  throws Exception
	{

		String sResult="";
		String _xml="";

		FolderListDOXML _do2 = new FolderListDOXML();
		_xml =_do2.getSubXML2(user_id);
		if(logger.isDebugEnabled()) {
			logger.debug("_xml"+_xml);
		}
		//folderList 정보를 등록
		StringHolder sessionid = new StringHolder();
		StringHolder opcode = new StringHolder("runusertransaction");
		ServicePortTypeProxy port = new ServicePortTypeProxy();
		try {
			sResult = port.SOAPInterface(
					"" 							//java.lang.String systemversion
					,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
					,opcode 					//javax.xml.rpc.holders.StringHolder opcode
					,_xml// java.lang.String ksccRequest
					,"" 						// java.lang.String exvalue1
					,""  						// java.lang.String exvalue2
					,""  						// java.lang.String exvalue3
					,""  							// java.lang.String exvalue4
					);
			if(logger.isDebugEnabled()) {
				//logger.debug("sResult  "+sResult);
			}

			String PdsInfo = CommonUtl.transXMLText(sResult);
			return PdsInfo;
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 본부코드와 본부명를 조회한다.
	 * @param cocd     회사코드                                                                                                                                                                                         
	 * @return    list                                                                                                                                                                                          
	 * @throws Exception
	 */
	public List getSupHeadList(String cocd) throws Exception
	{
		return userRoleDAO.getSupHeadList(cocd);

	}

	/**
	 * 부서코드와 부서명를 조회한다.
	 * @param deptcd      부서코드                                                                                                                                                                                        
	 * @return   list                                                                                                                                                                                           
	 * @throws Exception
	 */
	public List getDepinfoList(String deptcd) throws Exception
	{

		return userRoleDAO.getDepinfoList(deptcd);

	}

	/**
	 * 부서코드와 부서명를 조회한다.
	 * @param deptcd     부서코드                                                                                                                                                                                          
	 * @return       list                                                                                                                                                                                       
	 * @throws Exception
	 */
	public List getSupHeadList2(String deptcd) throws Exception
	{

		return userRoleDAO.getSupHeadList2(deptcd);

	}

	/**
	 * 역활 정보관련된 정보 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return list 역활 정보관련된 정보 목록 
	 */
	public List getAuthroRoleList(RoleInfoDO condition) throws Exception
	{

		return userRoleDAO.selectAuthorList(condition);

	}


	/**
	 * 국코드와 국명를 조회한다.
	 * @param cocd       회사코드                                                                                                                                                                                        
	 * @return         list      회사코드별 리스트                                                                                                                                                                               
	 * @throws Exception
	 */
	public List getSupHtpoList(String cocd) throws Exception
	{

		return userRoleDAO.getSupHtpoList(cocd);

	}

	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * @param deptcd      부서코드                                                                                                                                                                                        
	 * @return      list         부서별 소속 사용자 리스트                                                                                                                                                                               
	 * @throws Exception
	 */
	public List getDepinfoForuserList(String deptcd) throws Exception
	{

		return userRoleDAO.getDepinfoForuserList(deptcd);

	}

	/**
	 * 프로그램별 승인 목록에서 사용자 사번과 직책을 구해온다
	 * @param dep_cd, user_nm   부서코드 , 유저명                                                                                                                                                                                 
	 * @return     list        정보가 담긴 리스트                                                                                                                                                                                 
	 * @throws Exception
	 */
	public List getEmployeeListForApp(String dep_cd, String user_nm) throws Exception
	{

		return userRoleDAO.selecEmployeeListForApp(dep_cd,user_nm);

	}


	/**
	 * 로그인시 권한 코드를 주는 함수
	 * @param userid 사용자 id
	 * @return list 권한 코드를 가지고있는 리스트
	 */
	public List getRoleForLogin(String userid) throws Exception
	{

		String role_cd = userRoleDAO.selectRoleForLogin(userid);
		userRoleDAO.updateUnlock(userid);
		return userRoleDAO.selectRoleGroupForLogin(role_cd);

	}

	public int updatePasswd(EmployeeInfoDO roleDO)  throws Exception
	{

		String passwd="";
		try {
			String sResult="";

			int result =0;

			String acct = userRoleDAO.typeOfUser(roleDO.getReg_id());

			if(acct.equals(CodeConstants.Pacode.RA)){

				EmployeeInfoDO info = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(roleDO.getReg_id());
				if(logger.isDebugEnabled()) {
					logger.debug("roleDO.getPassword() [" + roleDO.getPassword() + "] ");
					logger.debug("info.getPassword() [" + info.getPassword() + "] ");
				}

				// ad쪽에 인증 후  보낸다

				/**
				 * AD API 활용하여 진행할 것.
				 */
				String approve="";

				JNI_Des hj =new JNI_Des();
				approve = hj.getDecryption(dasHandler.getProperty("AD_CRYPTO_KEY"), dasHandler.getProperty("AD_DEFAULT_HEX"), info.getPassword());

				if(logger.isDebugEnabled()) {
					logger.debug("JNI_Des getAuthentication Result [" + approve + "] ");
					logger.debug("info.getPassword() [" + info.getPassword() + "] ");
				}

				if(approve.equals(roleDO.getPassword())){

					// AES 알고리즘으로 암호화 후 보낸다
					JNI_Des newhj = new JNI_Des();
					String newpassword = newhj.setEncryption( dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND", roleDO.getNewPassword());
					if(logger.isDebugEnabled()) {
						logger.debug("password    " + newpassword);
					}

					//List resultList = new ArrayList();
					info.setUpdate_yn("Y");			
					info.setType(CodeConstants.Pacode.UPDATE);
					info.setNewPassword(roleDO.getNewPassword());
					info.setPassword(roleDO.getPassword());
					//resultList.add(info);

					StringBuffer _xml2 = new StringBuffer();
					/*
					String _xml2 = "";
					if (resultList != null && resultList.size() > 0) {

						Iterator _iter = resultList.iterator();
						while (_iter.hasNext()) {
							EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
							_do2.setDO(_iter.next());
							_xml2 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
							_xml2 = _xml2 + _do2.getSubXML();
							_xml2 = _xml2 +"</das>";
						}

						if (logger.isDebugEnabled())
							logger.debug("_xml" + _xml2);

					}
					 */
					EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
					_do2.setDO(info);
					_xml2.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
					_xml2.append(_do2.getSubXML());
					_xml2.append("</das>");

					com.sbs.pa.service.Service1SoapProxy port2 = new com.sbs.pa.service.Service1SoapProxy();
					try {
						result =  port2.wsPAUserInfoManager(_xml2.toString());
						if(logger.isDebugEnabled()) {
							logger.debug("Result"+result);
						}
					} catch (RemoteException e) {
						logger.error("Service1SoapProxy call error", e);
					}

					info.setNewPassword(newpassword);
					result=	userRoleDAO.updateEmployeePasswd(info);

					/*
					List resultList2 = new ArrayList();
					resultList2.add(info);
					String _xml3 = "";
					if (resultList2 != null && resultList2.size() > 0) {

						Iterator _iter = resultList2.iterator();
						while (_iter.hasNext()) {
							AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
							_do2.setDO(_iter.next());
							_xml3 = _xml3 + _do2.getSubXML3();
						}

						if (logger.isDebugEnabled())
							logger.debug("_xml3" + _xml3);

					}
					 */

					_xml2.setLength(0);
					_do2.setDO(info);
					_xml2.append(_do2.getSubXML3());

					//IFCMS에 비밀번호 변경 사항을 저장한다.
					StringHolder sessionid = new StringHolder();
					StringHolder opcode = new StringHolder("runusertransaction");
					com.sbs.ifcms.service.ServicePortTypeProxy port = new com.sbs.ifcms.service.ServicePortTypeProxy();
					try {
						sResult = port.SOAPInterface(
								"" 							//java.lang.String systemversion
								,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
								,opcode 					//javax.xml.rpc.holders.StringHolder opcode
								,_xml2.toString()			// java.lang.String ksccRequest
								,"" 						// java.lang.String exvalue1
								,""  						// java.lang.String exvalue2
								,""  						// java.lang.String exvalue3
								,""  							// java.lang.String exvalue4
								);
						if(logger.isDebugEnabled()) {
							//logger.debug("sResult"+sResult);
						}
					} catch (RemoteException e) {
						logger.error("ServicePortTypeProxy call error", e);
					}
				}else{
					return 5;
				}


			} else {//비직원이라면 SHA알고리즘으로 암호화
				logger.debug("change SA,SB,SC PASSWORD" + roleDO.getNewPassword());

				//비직원인 경우 이곳에서 바로 동기화를 한다.

				EmployeeInfoDO trans = (EmployeeInfoDO)userRoleDAO.selecEmployeeInfo(roleDO.getReg_id());
				if(logger.isDebugEnabled()) {
					logger.debug("roleDO.getPassword() [" + roleDO.getPassword() + "] ");
					logger.debug("trans.getPassword() [" + trans.getPassword() + "] ");
				}
				if(trans.getPassword().equals(userRoleDAO.getPasswd(roleDO.getPassword().trim()))){
					result=	userRoleDAO.updateNonEmployeePasswd(roleDO);
					// AES 알고리즘으로 암호화 후 보낸다
					if(trans.getAcct_code().equals("SA")||trans.getAcct_code().equals("SB")||trans.getAcct_code().equals("SC")){
						JNI_Des hj = new JNI_Des();
						String password = hj.setEncryption( dasHandler.getProperty("AD_CRYPTO_KEY"), "AESPWPND", roleDO.getNewPassword());
						if(logger.isDebugEnabled()) {
							logger.debug("password    " + password);
						}
						trans.setNewPassword(password);
					}
					/*
					List resultList2 = new ArrayList();
					resultList2.add(trans);
					String _xml2 = "";
					if (resultList2 != null && resultList2.size() > 0) {

						Iterator _iter = resultList2.iterator();
						while (_iter.hasNext()) {
							AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
							_do2.setDO(_iter.next());
							_xml2 = _xml2 + _do2.getSubXML3();
						}

						if (logger.isDebugEnabled())
							logger.debug("_xml2" + _xml2);

					}
					 */

					StringBuffer _xml2 = new StringBuffer();

					AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
					_do2.setDO(trans);

					_xml2.append(_do2.getSubXML3());

					//IFCMS에 비밀번호 변경 사항을 저장한다.
					StringHolder sessionid = new StringHolder();
					StringHolder opcode = new StringHolder("runusertransaction");
					com.sbs.ifcms.service.ServicePortTypeProxy port = new com.sbs.ifcms.service.ServicePortTypeProxy();
					try {
						sResult = port.SOAPInterface(
								"" 							//java.lang.String systemversion
								,sessionid  				//javax.xml.rpc.holders.StringHolder sessionid
								,opcode 					//javax.xml.rpc.holders.StringHolder opcode
								,_xml2.toString()			// java.lang.String ksccRequest
								,"" 						// java.lang.String exvalue1
								,""  						// java.lang.String exvalue2
								,""  						// java.lang.String exvalue3
								,""  							// java.lang.String exvalue4
								);
						if(logger.isDebugEnabled()) {
							//logger.debug("sResult"+sResult);
						}
					} catch (RemoteException e) {
						logger.error("ServicePortTypeProxy call error", e);
					}

				}else{
					return 5;
				}
			}

			return	result;

		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 역활 권한 그룹신규로 신청.
	 * @param roleDO    역활 그룹 정보가 담겨져있는 beans                                                                                                                                                                                          
	 * @return  성공 1 실패 0                 
	 * @throws Exception
	 *  */
	public int insertRoleInfo(RoleInfoDO roleDO)  throws Exception
	{

		if(roleDO.getGubun().equals("")){
			RoleInfoDO NewRole = userRoleDAO.insertRole(roleDO);
			userRoleDAO.insertRoleGroup(NewRole);
		}else{
			RoleInfoDO NewRole = userRoleDAO.insertMoniToringRole(roleDO);
			userRoleDAO.insertRoleGroupForMonitoring(NewRole);	
		}
		return 1;
	}


	/**
	 * 역할 코드 값구한다
	 * @param 
	 * @return list 역활 코드정보를 가지고있는 리스트
	 */
	public List getRoleInfo() throws Exception
	{
		return userRoleDAO.selecroleInfo();
	}

	/**
	 * 주제영상 코드 조회
	 * @param                                                                                                                                                                                               
	 * @return    list 주제영상 정보를 가지고있는 리스트                                                                                                                                                                                         
	 * @throws Exception
	 */
	public List getAnnotCode() throws Exception
	{
		return userRoleDAO.selecAnnotInfo();

	}


	//2012.04.19 다스 확장 추가

	/**
	 * 로그인시 권한 코드를 주는 함수
	 * @param userid 사용자 id
	 * @return list 권한 코드를 가지고있는 리스트
	 */
	public List getRoleInfoForChennel(RoleInfoDO roleInfoDO) throws Exception
	{
		return userRoleDAO.getRoleInfoForChennel(roleInfoDO);

	}

	/**
	 * 로그인 기록
	 * @param xml                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public String LogHistory(LogInOutDO roleDO)  throws Exception
	{

		long sResult=0;

		if(roleDO.getStatus().equals("ON")){
			sResult = userRoleDAO.insertLogin(roleDO);
		}else if(roleDO.getStatus().equals("OFF")){
			sResult = userRoleDAO.updateLogout(roleDO);
		}

		return String.valueOf(sResult);

	}


	/**
	 * 사용자 로그인 현황을 조회한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception
	 */
	public List getLogInfo(LogInOutDO roleDO) throws Exception
	{

		return userRoleDAO.getLogInfo(roleDO);

	}

	/**
	 * 모니터링 로그인시 역활정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception
	 */
	public String getRoleForLoginInMonitoring(String userid) throws Exception
	{

		return userRoleDAO.getRoleForLoginInMonitoring(userid);

	}

	/**
	 * 역활 정보를 수정한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return 성공 1 실패 0 
	 */
	public int updateRoleInfoForMonitoring(RoleInfoDO roleDO)  throws Exception
	{

		return  userRoleDAO.updateRoleInfoForMonitoring(roleDO);

	}

	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * @param deptcd      부서코드                                                                                                                                                                                        
	 * @return      list         부서별 소속 사용자 리스트                                                                                                                                                                               
	 * @throws Exception
	 */
	public List getDepinfoForuserListFormonitoring(String deptcd) throws Exception
	{

		return userRoleDAO.getDepinfoForuserListFormonitoring(deptcd);

	}

	/**
	 * 역할 코드 값구한다
	 * @param 
	 * @return list 역활 코드정보를 가지고있는 리스트
	 */
	public List getRoleCodeForMonitoring() throws Exception
	{

		return userRoleDAO.selecroleInfoForMonitoring();

	}

	/**
	 * 모니터링  역활정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception
	 */
	public String getAuthorForMonitoring(String role_cd) throws Exception
	{

		return userRoleDAO.getAuthorForMonitoring(role_cd);

	}


}

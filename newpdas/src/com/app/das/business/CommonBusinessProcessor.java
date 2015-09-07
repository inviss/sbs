/***********************************************************
 * 프로그램ID  	: CommonBusinessProcessor.java
 * 프로그램명  	: CommonBusinessProcessor
 * 작성일자     	:
 * 작성자       	:
 * 설명         :  공통 관리 비지니스 프로세서 프로그램
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0 전환 수정.
 ***********************************************************/
package com.app.das.business;

import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.log.ErrorPropHandler;
import com.app.das.business.dao.UserInfoDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.util.StringUtils;
import com.app.das.business.CommonBusinessProcessor;

/**
 * 공통으로 사용하는 사용자 조회 팝업에 대한 구현이 되어있는 Class
 * @author ysk523
 *
 */
public class CommonBusinessProcessor 
{
	private static final UserInfoDAO userInfoDAO = UserInfoDAO.getInstance();
	
	private Logger logger = Logger.getLogger(CommonBusinessProcessor.class);
	
	/**
	 * 사용자 조회를 한다.(정직원, 외부직원)
	 * @param searchValue 사용자 ID 또는 사용자명
	 * @param commonDO
	 * @return List UserInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getAllUserList(String searchValue, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getAllUserList][Input searchValue]" + searchValue);
		}
		
		try 
		{
			PageDO pageDO = userInfoDAO.selectAllUserList(searchValue, 1, commonDO);
			
			return pageDO.getPageItems();
		} 
		catch (Exception e)
		{
			
			
			throw e;
		}
	}

	/**
	 * 정직원 사용자 조회를 한다.
	 * @param searchValue 사용자 ID 또는 사용자명
	 * @param commonDO
	 * @return List UserInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public  PageDO getRegularUserList(String searchValue, int page, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getRegularUserList][Input searchValue]" + searchValue);
		}

		try 
		{
			PageDO pageDO = userInfoDAO.selectRegularUserList(searchValue, page, commonDO);
			return pageDO;
		} 
		catch (Exception e)
		{
			
			throw e;
		}
	}
	
	/**
	 * 정직원 정보를 조회한다.
	 * @param userNo 사원번호
	 * @param commonDO 공통정보
	 * @return DASCommonDO 사용자 정보를 포함하고 있는 정보
	 * @throws Exception 
	 */
	public DASCommonDO getEmployeeInfoByUserNo(String userNo, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getEmployeeInfoByUserNo][Input userNo]" + userNo);
		}

		try 
		{
			return userInfoDAO.selectEmployeeInfoByUserNo(userNo);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}
}

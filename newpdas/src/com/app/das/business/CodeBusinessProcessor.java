/***********************************************************
 * 프로그램ID  	: CodeBusinessProcessor.java
 * 프로그램명  	: CodeBusinessProcessor
 * 작성일자     	:
 * 작성자       	:
 * 설명         :  코드 관리 비지니스 프로세서 프로그램
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0 전환 수정.
 ***********************************************************/
package com.app.das.business;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.dao.CodeInfoDAO;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.CodeDO;

/**
 * 공통코드의 조회, 등록, 수정, 삭제 및 장르 List 조회가 구현되어 있는 Class
 * @author ysk523
 *
 */
public class CodeBusinessProcessor 
{
	private static CodeInfoDAO codeInfoDAO = CodeInfoDAO.getInstance();

	private Logger logger = Logger.getLogger(CodeBusinessProcessor.class);



	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.<p>
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getCodeList(CodeDO codeDO) throws Exception
	{

		//clf_cd값이 없다면 주제영상, 사용등급 코드를 조회하고 그렇지 않으면 입력받은 clf_cd에 소속된 코드 정보를 조회한다.
		if(codeDO.getClfCd().equals("")){
			return codeInfoDAO.selectCodeList(codeDO);
		}else{
			return codeInfoDAO.selectCodeInfo2(codeDO);
		}

	}

	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.<p>
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getCode(String codeDO) throws Exception
	{

		return codeInfoDAO.selectCode(codeDO);

	}




	/**
	 * 특정 코드에 대한 정보를 조회한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param code 코드테이블의 구분상세코드
	 * @param commonDO 공통정보
	 * @return CodeDO 특정 코드정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public CodeDO getCodeInfo(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.selectCodeInfo(codeDO);

	}

	/**
	 * 코드 테이블에 코드 정보를 등록한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int insertCodeInfo(CodeDO codeDO) throws Exception
	{

		if(codeDO.getGubun().equals("")){
			return codeInfoDAO.insertCodeInfo(codeDO);  
		}else if(codeDO.getClfCd().equals("P065")){
			return codeInfoDAO.insertCodeInfo(codeDO);  
		} else{
			return codeInfoDAO.insertCodeInfoGubun(codeDO);  // 주제영상 & 사용제한 등급 코드
		}

	}

	/**
	 * 코드 테이블에 코드 정보를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateCodeInfo(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.updateCodeInfo(codeDO);

	}

	/**
	 * 코드 테이블에 코드 정보를 삭제한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int deleteCodeInfo(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.deleteCodeInfo(codeDO);

	}

	/**
	 * 장르 정보를 목록조회 한다.
	 * @param commonDO 공통정보
	 * @return List JanreInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getjanreList(String mainKey) throws Exception
	{

		List janreInfoDOList = codeInfoDAO.selectjanreList(mainKey);

		return janreInfoDOList;

	}



	/**
	 * 장르 정보를 목록조회 한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getJanrCodeList11(CodeDO condition) throws Exception
	{

		return codeInfoDAO.getJanrCodeList(condition);

	}


	/**
	 * 장르코드 관리를 조회한다.(다중조회)
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getJanrCodeList(CodeDO condition) throws Exception
	{

		return codeInfoDAO.getJanrCodeList(condition);

	}



	/**
	 *  장르코드 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertJanrCode(CodeDO roleDO)  throws Exception
	{

		return codeInfoDAO.insertJanrCode(roleDO);

	}




	/**
	 *  대분류코드를 생성 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int creatBcode(CodeDO codeDO)  throws Exception
	{

		return codeInfoDAO.insertBcode(codeDO);

	}




	/**
	 *  중분류코드를 생성 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int creatMcode(CodeDO roleDO)  throws Exception
	{

		return codeInfoDAO.insertMcode(roleDO);

	}




	/**
	 *  소분류코드를 생성 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int creatScode(CodeDO roleDO)  throws Exception
	{

		return codeInfoDAO.insertScode(roleDO);

	}



	/**
	 * 대분류 코드 정보를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateBcode(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.updateBcode(codeDO);

	}

	/**
	 * 중분류 코드 정보를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateMcode(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.updateMcode(codeDO);

	}

	/**
	 * 소분류 코드 정보를 수정한다.
	 * @param codeDO 코드 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public int updateSode(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.updateScode(codeDO);

	}


	/**
	 * 자동 아카이브관리를 조회한다.(다중조회)
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getAutoArchvieList(AutoArchiveDO condition) throws Exception
	{

		//codeInfoDAO.insertAutoArchvie(); 제거 중복되는 값을 보장할 방법이 없음.
		return codeInfoDAO.getAutoArchvieList(condition);

	}



	/**
	 * 자동 아카이브관리를 수정한다
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateAutoArchvie(AutoArchiveDO autoDO) throws Exception
	{

		return codeInfoDAO.updateAutoArchvie(autoDO);

	}




	/**
	 * 메인화면 ㄱㄴㄷㄹㄷ...
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getMainKeyList() throws Exception
	{

		return codeInfoDAO.selectMainKeyList();

	}
	// 2012.4.18 das 확장 추가 개발 함수


	/**
	 * 코드 테이블에서 구분코드에 해당하는 코드 List를 조회한다.<p>
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param mainKey 코드테이블의 구분코드
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getArchiveRoute(String xml) throws Exception
	{

		return codeInfoDAO.getArchiveRoute(xml);

	}


	// 2012.4.20 다스 확장 조회
	/**
	 * 채널정보를 조회한다
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param codeDO 회사정보
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getChennelInfo(CodeDO codeDO) throws Exception
	{

		return codeInfoDAO.getChennelInfo(codeDO);

	}


	/**
	 * 채널 구성을 위한 회사 정보를 조회한다
	 * 코드테이블의 구분코드에 의해 해당하는 코드값을 조회하여 리턴한다.
	 * @param codeDO 회사정보
	 * @param commonDTO 공통정보
	 * @return List CodeDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getCocdForChennel() throws Exception
	{

		return codeInfoDAO.getCocdForChennel();
	}
}

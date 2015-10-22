package com.app.das.business;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.dao.CodeInfoDAO;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.dao.SystemManageDAO;
import com.app.das.business.dao.UserRoleDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.CopyInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DeleteDO;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.NleDO;
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
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.log.DasPropHandler;
import com.app.das.log.ErrorPropHandler;
import com.app.das.services.PreviewNoteDOXML;
import com.app.das.services.SubsiInfoDOXML;
import com.app.das.util.StringUtils;


/**
 * 시스템 관리의 모니터링의 장비, 작업, ID별 자료이용현황의 조회 및  미접속 ID현황의 조회 및 중지, 복구에 대한 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class SystemManageBusinessProcessor 
{
	private static SystemManageDAO systemManageDAO = SystemManageDAO.getInstance();
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	private static CodeInfoDAO	codeInfoDAO = CodeInfoDAO.getInstance();

	private Logger logger = Logger.getLogger(SystemManageBusinessProcessor.class);

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForMaxDate(DASCommonDO commonDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[getEquipmentMonitoringListForMaxDate][Input DASCommonDO]" + commonDO);
		}

		try 
		{
			return systemManageDAO.selectEquipmentMonitoringListForMax(commonDO);
		} 
		catch (Exception e) 
		{

			throw e;
		}
	}

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForMaxDate(String workStateCode, DASCommonDO commonDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[getEquipmentMonitoringListForMaxDate][Input workStateCode]" + workStateCode);
		}

		try 
		{
			return systemManageDAO.selectEquipmentMonitoringListForMax(workStateCode, commonDO);
		} 
		catch (Exception e) 
		{
			
			throw e;
		}
	}

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForToDate(String eqClfCd, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getEquipmentMonitoringListForToDate][Input eqClfCd]" + eqClfCd);
		}

		try 
		{
			return systemManageDAO.selectEquipmentMonitoringListForToDate(eqClfCd, commonDO);
		} 
		catch (Exception e) 
		{
			
			throw e;
		}
	}

	/**
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param eqClfCd 장비구분코드
	 * @param workStateCode 작업상태코드
	 * @param commonDO
	 * @return EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getEquipmentMonitoringListForWork(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getEquipmentMonitoringListForWork][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = systemManageDAO.selectEquipmentMonitoringListForWork(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
		
			throw e;
		}
	}

	/**
	 * 모니터링 작업에서 로그테이블의 당일 것을 조회한다.
	 * @param conditionDO 장비구분코드
	 * @param commonDO
	 * @return EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getEquipmentLogList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getEquipmentLogList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = systemManageDAO.selectEquipmentLogList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
			

			throw e;
		}
	}

	/**
	 * ID별 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO getContentsUseInfoList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getContentsUseInfoList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			return systemManageDAO.selectContentsUseInfoList(conditionDO, commonDO);
		} 
		catch (Exception e) 
		{
		

			throw e;
		}
	}

	/**
	 * 미접속 ID 현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List UserInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getNonLoginUserList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getNonLoginUserList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = systemManageDAO.selectNonLoginUserList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
			
			throw e;
		}
	}

	/**
	 * 사용 중지된 외부 사용자의 유효 종료일을 복구시킨다
	 * @param userInfoDOList 사용자 정보인 UserInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void restoreNonLoginUserList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[restoreNonLoginUserList][Inputt userInfoDOList]" +userInfoDOList);
		}


		try 
		{
			systemManageDAO.updateNonLoginUserRestoreList(userInfoDOList, commonDO);
		} 
		catch (Exception e) 
		{
	

			throw e;
		}

	}

	/**
	 * 외부직원 자료이용현황 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO getContentsOutUseInfoList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getContentsOutUseInfoList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			return systemManageDAO.selectContentsOutUseInfoList(conditionDO, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 미접속 외부 사용자를 중지시킨다.
	 * @param userInfoDOList UserInfoDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void stopOutUserList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			systemManageDAO.updateOutUserStopList(userInfoDOList, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}

	}

	/**
	 * 다운로드 진행상황를 목록 조회한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회 결과를 Page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public PageDO getDownloadStatusList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getDownloadStatusList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			return systemManageDAO.selectDownloadStatusList(conditionDO, commonDO);
		} 
		catch (Exception e) 
		{
			
			throw e;
		}
	}

	/**
	 * 다운로드 진행사항 조회에서 상태를 사용중으로 복구한다.
	 * @param downStatusInfoDOList DownStatusInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void recoveryDownloadStatusList(List downStatusInfoDOList, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			systemManageDAO.updateRecoveryDownloadStatusList(downStatusInfoDOList, commonDO);
		} 
		catch (Exception e) 
		{

			throw e;
		}

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public PageDO getProgramList(SystemManageConditionDO condition, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getProgramList][SystemManageConditionDO]" + condition);
		}

		try 
		{
			PageDO pageDO = systemManageDAO.selectProgramList(condition, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}



	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_nm 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List getParentsInfo(String pgm_nm) throws Exception
	{
		try 
		{
			return  systemManageDAO.selectParentsInfo(pgm_nm);
		} 
		catch (Exception e)
		{
			throw e;
		}
	}



	/**
	 * 프로그램 정보를 삭제 한다.
	 * @param prgId 프로그램 ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */	
	public void deleteProgramInfo(String prgId, DASCommonDO commonDO)  throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[deletedeleteProgramInfo][Input prgId]" + prgId);
		}

		try 
		{
			//프로그램 정보를 삭제한다.
			systemManageDAO.deleteProgramInfo(prgId, commonDO);
		} 
		catch (Exception e) 
		{

			throw e;
		}
	}

	/**
	 * 권한 코드를 삭제한다
	 * @param codeDO 코드 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */	
	public int deleteScreenAuthentication(CodeDO codeDO)  throws Exception
	{
		try 
		{
			//코드테이블에서 권한 코드를 삭제한다.
			int ctmp = codeInfoDAO.deleteCodeInfo(codeDO);

			//권한 테이블에서 권한 정보를 삭제한다
			int stmp = systemManageDAO.deleteScreenAuthentication(codeDO);

			return ctmp;
		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 프로그램 정보를 수정한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updatedProgramInfo][Input pgmDO]" + pgmDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			systemManageDAO.updateProgramInfo(pgmDO, commonDO);
		} 
		catch (Exception e) 
		{
		
			throw e;
		}
	}


	/**
	 * 프로그램 정보를 추가한다. 이 함수가 호출되는 경우는 ERP에만 프로그램 데이터가 있고 DAS에는 없는 경우이다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertProgramInfo][Input pgmDO]" + pgmDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			systemManageDAO.insertProgramInfo(pgmDO, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 프로그램 정보를 조회한다.
	 * @param pgmId 프로그램id
	 * @return ProgramInfoDO 
	 * @throws Exception 
	 */
	public ProgramInfoDO getSelectedProgamInfo(String pgmId) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSelectedProgamInfo][Input pgmId]" + pgmId);
		}

		try 
		{
			//선택된 프로그램 정보를 가져온다.
			ProgramInfoDO pgmInfoDO = null;
			pgmInfoDO = systemManageDAO.selectProgramInfoByID(pgmId);
			return pgmInfoDO;
		} 
		catch (Exception e) 
		{
		
			throw e;
		}
	}


	/**
	 * 코드를 보고 ERP의 프로그램 정보를 가져온다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public ProgramInfoDO getSelectedERPProgamInfoByCode(String pgmCode) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSelectedProgamInfoByCode][Input pgmCode]" + pgmCode);
		}

		try 
		{
			//선택된 프로그램 정보를 가져온다.
			ProgramInfoDO pgmInfoDO = null;
			pgmInfoDO = systemManageDAO.selectERPProgramInfoByCode(pgmCode);
			return pgmInfoDO;
		} 
		catch (Exception e) 
		{
	
			throw e;
		}
	}

	/**
	 * 매체변환의 오류내역 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return PageDO
	 * @throws Exception 
	 */
	public PageDO getErrorList(SystemManageConditionDO condition, DASCommonDO commonDO, String excel) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getProgramList][SystemManageConditionDO]" + condition);
		}

		try 
		{
			PageDO pageDO = systemManageDAO.selectErrorList(condition, commonDO, excel);

			return pageDO;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}


	/**
	 * 사진 다운로드 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return  PageDO ContentsUseInfoDO 를 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO getPhotoDownList(SystemManageConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getContentsUseInfoList][Input SystemManageConditionDO]" + conditionDO);
		}

		try 
		{
			return systemManageDAO.selectPhotoDownList(conditionDO, commonDO);
		} 
		catch (Exception e) 
		{

			throw e;
		}
	}




	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public List getPgmList(ProgramInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getPgmList][programInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectPgmList(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}
	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public String getPgmList2(ProgramInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getPgmList][programInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectPgmList2(condition);


		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_cd 프로그램코드
	 * @return List
	 * @throws Exception 
	 */

	public List getPgmInfo(String pgm_cd) throws Exception
	{
		try 
		{		
			return systemManageDAO.selectPgm(pgm_cd);

		} 
		catch (Exception e)
		{
			throw e;
		}

	}


	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List getPgmInfo2(String pgm_cd) throws Exception
	{
		try 
		{
			return systemManageDAO.selectPgm2(pgm_cd);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */

	public List getParentsCD(String pgm_cd) throws Exception
	{
		try 
		{
			return systemManageDAO.selectParents(pgm_cd);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}


	/**
	 * 프로그램 정보를 추가한다. 이 함수가 호출되는 경우는 ERP에만 프로그램 데이터가 있고 DAS에는 없는 경우이다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 */
	public int insertPgmcd(ProgramInfoDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertPgmcd][Input pgmDO]" + pgmDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertPgmInfo(pgmDO);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}


	/**
	 * 프로그램 코드를 생성한다
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param  Pgm_cd 새로생성된 프로그램 코드
	 */
	public String getPgm_cd()  throws DASException
	{
		return systemManageDAO.getPgm_cd();

	}

	/**
	 * 스토리지 용량 확인
	 * 
	 * @throws DASException
	 */
	public String getAvailableDisk() throws DASException{
		if(logger.isDebugEnabled()) 
		{
			logger.debug("[getAvailableDisk] ");
		}
		return systemManageDAO.getAvailableDisk();
	}
	/**
	 * 프로그램코드 수정한다
	 * @param  pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 */
	public int updatePgmcd(ProgramInfoDO programInfoDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[updatePgmcd][Input programInfoDO]" + programInfoDO);
		}
		try 
		{	
			if(systemManageDAO.isTherePgmCd(programInfoDO.getPgmCd())){
				return systemManageDAO.insertPgmInfo(programInfoDO);
			}else{

				return systemManageDAO.updatePgmcd(programInfoDO);
			}
		} 
		catch (Exception e)
		{

			throw e;
		}

	}



	/**
	 * 계열사 수신서버 관리를 조회한다.(다중조회)
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public List getSubsiServerList(SubsiInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSubsiServerList][SubsiInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectSubsiServerList(condition);


		} 
		catch (Exception e)
		{
			
			throw e;
		}
	}



	/**
	 * 계열사 수신서버 코드를 등록한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return updatecount
	 * @throws Exception 
	 */
	public int insertSubsiServer(SubsiInfoDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertSubsiServer][Input pgmDO]" + pgmDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertSubsiServer(pgmDO);
		} 
		catch (Exception e)
		{
			
			throw e;
		}

	}


	/**
	 * 계열사 수신서버 정보 수정한다
	 * @param subsiInfoDO                                                                                                                                                                                             
	 * @return    updatecount                                                                                                                                                                                          
	 * @throws Exception 
	 */

	public int updateSubsiServer(SubsiInfoDO subsiInfoDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[updatePgmcd][Input subsiInfoDO]" + subsiInfoDO);
		}
		try 
		{
			return systemManageDAO.updateSubsiServer(subsiInfoDO);
		} 
		catch (Exception e)
		{
			

			throw e;
		}

	}


	/**
	 * 프로그램 복본관리를 조회한다.(다중조회)
	 * @param  copyDO     조회를 위한 beans                                                                                                                                                                                         
	 * @return  List                                                                                                                                                                                    
	 * @throws Exception 
	 */
	public List getCopyList(CopyInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getCopyList][CopyInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectCopyList(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}



	/**
	 * 프로그램 복본관리를 등록한다.
	 * @param copyDO    등록을 위한 beans                                                                                                                                                                                          
	 * @return    updatecount                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public int insertCopy(CopyInfoDO copyDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertCopy][Input CopyInfoDO]" + copyDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			if(systemManageDAO.isTherePgmid(copyDO.getCms_pgm_id())){
				return	0;
			}
			return systemManageDAO.insertCopy2(copyDO);
		}

		catch (Exception e)
		{
			

			throw e;
		}

	}

	/**
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateCopy(CopyInfoDO copyDO) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[updateCopy][Input copyDO]" + copyDO);
		}
		try 
		{
			return systemManageDAO.updateCopy2(copyDO);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}


	/**
	 * 이용현황 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getUseInfoList(UseInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getUseInfoList][UseInfoDO]" + condition.toString());
		}

		try 
		{
			return systemManageDAO.selectUseInfoList(condition);


		} 
		catch (Exception e)
		{
			
			throw e;
		}
	}

	/**
	 * 이용현황 정보(총조회건수, 총길이) 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getUseInfoList2(UseInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getUseInfoList2][UseInfoDO]" + condition.toString());
		}

		try 
		{
			return systemManageDAO.selectUseInfoList2(condition);


		} 
		catch (Exception e)
		{
			
			throw e;
		}
	}

	/**
	 * 복본/소산 생성요청을 한다( 기존 아카이브 건에 대해서 복본생성만 요청한다.)
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public String updateCopyRequest(UseInfoDO useInfoDO) throws NumberFormatException, Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateCopyRequest][Input UseInfoDO]" + useInfoDO);
		}

		try 
		{

			if(useInfoDO.getGubun().equals("001")){
				//복본신청표기
				externalDAO.updateCopyYN(Long.parseLong(useInfoDO.getMaster_id()),useInfoDO.getUser_id());
				externalDAO.updateCopyReqId(Long.parseLong(useInfoDO.getMaster_id()),useInfoDO.getUser_id());
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForMaster_id(Long.parseLong(useInfoDO.getMaster_id()));
				useInfoDO.setDtl_type(dtl_type);
				//프로그램 정보를 신청한다한다.
				return	systemManageDAO.updateCopyRequest(useInfoDO);
			}else if(useInfoDO.getGubun().equals("002")){
				//소산신청표기
				externalDAO.updateBackUpYN(Long.parseLong(useInfoDO.getMaster_id()),useInfoDO.getUser_id());
				externalDAO.updateBackUpYN(useInfoDO);
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForMaster_id(Long.parseLong(useInfoDO.getMaster_id()));
				useInfoDO.setDtl_type(dtl_type);
				//소산을 workflow에  신청한다
				return	systemManageDAO.updateBackupRequest(useInfoDO);
			}else if(useInfoDO.getGubun().equals("003")){
				//복원신청표기
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForMaster_id(Long.parseLong(useInfoDO.getMaster_id()));
				externalDAO.updateReCorverReqId(Long.parseLong(useInfoDO.getMaster_id()),useInfoDO.getUser_id());
				useInfoDO.setDtl_type(dtl_type);
				//복원을 workflow에  신청한다
				return	systemManageDAO.updateRecorveryRequest(useInfoDO);
			}
		} 
		catch (Exception e)
		{
			throw e;
		}
		return "";

	}

	/**
	 * 아카이브 상태 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getArchiveStatusList(ArchiveInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getArchiveStatusList][ArchiveInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectArchiveStatusList(condition);


		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * 아카이브 상태 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String getArchiveStatusList2(ArchiveInfoDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getArchiveStatusList][ArchiveInfoDO]" + condition);
		}

		try 
		{
			return systemManageDAO.selectArchiveStatusList2(condition);


		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 오늘의 화면 조회한다.

	 * @param todayDO                                                                                                                                     종료일
	 * @return                                                                                                                                     MetaInfoDO XML string
	 * @throws Exception 
	 */

	public List getTodayList() throws Exception
	{
		try 
		{
			return systemManageDAO.selectTodayList();


		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 명장면 조회한다.

	 * @param goodDO                                                                                                                                     종료일
	 * @return                                                                                                                                     MetaInfoDO XML string
	 * @throws Exception 
	 */
	public List getGoodMediaList() throws Exception
	{
		try 
		{
			
			
			return systemManageDAO.selectGoodMediaList();


		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 스토리지 용량 조회
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getDiskAva() throws Exception{
		String targetName[] ={"MP2","MP2_BK","MP4","MP4_BK","ARC","ARC_BK"};

		String targetValue[] = {"/mp2","","/mp4","","/arcreq",""};
		if(logger.isDebugEnabled())
		{
		}
		// /app/db2
		try 
		{
			String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><das>";
			for(int i =0;i<targetName.length;i++){
				_xml = _xml + "<" + targetName[i] + ">" + systemManageDAO.getDiskAva(targetValue[i]) + "</"  + targetName[i] + ">";
			}
			_xml = _xml+"</das>";
			logger.debug("_xml" + _xml);
			return _xml;
		} 
		catch (Exception e) 
		{
			throw e;
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
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertPdsPgmUserInfo][Input PgmUserInfoDO]" + pdsInfoDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertPdsPgmUserInfo(pdsInfoDO);
		} 
		catch (Exception e)
		{
			throw e;
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
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertPdsPgmUserInfoAll][Input PgmUserInfoDO]" + pdsInfoDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertPdsPgmUserInfoAll(pdsInfoDO);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}





	/**
	 * PDS 프로그램 정보를 등록한다.
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfo(PgmInfoDO pdsInfoDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertCopy][Input pdsInfoDO]" + pdsInfoDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertPdsPgmInfo(pdsInfoDO);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}




	/**
	 * PDS 프로그램 정보를 등록한다.(벌크)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfoAll(List pdsInfoDOs) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertPdsPgmInfoAll][Input pdsInfoDO]" + pdsInfoDOs);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			systemManageDAO.insertPdsPgmInfoAll(pdsInfoDOs);
			return	1;
		} 
		catch (Exception e)
		{
			throw e;
		}
	}




	/**
	 * PDS-DAS간 연동 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsMappInfo(PdsMappDO pdsInfoDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertCopy][Input PdsMappDO]" + pdsInfoDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			return	systemManageDAO.insertPdsMappInfo(pdsInfoDO);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}

	/**
	 * PDAS 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertPdasArchive(PdsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[PdsArchiveDO][Input pgmDO]" + pgmDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.

			if(pgmDO.getCocd().equals("")){

				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
					int one =systemManageDAO.insertMetadatTbl(pgmDO);
					int two =systemManageDAO.insertContentsInfo(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);

					int seven =systemManageDAO.insertPdasArchive(pgmDO);

					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine;
					if(result >= 4){
						return 1;
					}else{
						return 0;	
					}
				}
			}else if(!pgmDO.getCocd().equals("")){

				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
					int one =systemManageDAO.insertMetadatTbl(pgmDO);
					int two =systemManageDAO.insertContentsInfo(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);

					int seven =systemManageDAO.insertPdasArchive(pgmDO);

					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine;
				}
				return 1;
			}
			return -1;
		} 
		catch (Exception e)
		{

			throw e;
		}

	}

	/**
	 * IFCMS 아카이브  상태변환 ver 2.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int updateIfCmsArchiveStatus(IfCmsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[IfCmsArchiveDO][Input pgmDO]" + pgmDO);
		}

		try 
		{

			long masterid  = systemManageDAO.selectMasterIdForIfCms(pgmDO.getGroup_id());
			pgmDO.setMaster_id(masterid);
			//ct_id, cti_id(low) 값을 얻어온다
			IfCmsArchiveDO pgm = systemManageDAO.selectTcInfoForIfCms(pgmDO.getMedia_id());
			pgmDO.setCt_id(pgm.getCt_id());
			pgmDO.setCti_idForLow(pgm.getCti_idForLow());

			systemManageDAO.updateIfCmsArchiveStatus(pgmDO);

			//tc job 등록한다.
			systemManageDAO.insertIfCms(pgmDO);


			return 1;


		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * 수동 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertManualArchive(PdsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[PdsArchiveDO][Input pgmDO]" + pgmDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.
			int one =systemManageDAO.insertMetadatTbl(pgmDO);
			int two =systemManageDAO.insertContentsInfo(pgmDO);
			int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
			int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
			int four =systemManageDAO.insertCornerInfo(pgmDO);
			int five =systemManageDAO.insertContentsMappInfo(pgmDO);
			if(!pgmDO.getPreview_file_nm().equals("")){
				int six =systemManageDAO.insertPreveiw_Info(pgmDO);
			}
			int seven =systemManageDAO.insertPdasArchive(pgmDO);

			int ten =systemManageDAO.insertAnnotInfo(pgmDO);
			//tc job 등록한다.
			systemManageDAO.insertPDS(pgmDO);
			int result = one+two+three+four+five+seven+nine;
			logger.debug("result   ="+result);
			if(result >= 6){
				return 1;
			}else{
				return 0;	
			}
		} 
		catch (Exception e) 
		{
			throw e;
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
		try 
		{
			return systemManageDAO.updateAccept(master_id);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}


	/**  
	 * 정리완료처리한다
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateArrange(long master_id, String user_id) throws Exception
	{
		try 
		{
			return systemManageDAO.updateArrange(master_id,user_id);
		} 
		catch (Exception e)
		{

			throw e;
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
		try 
		{
			return systemManageDAO.updateRsv_Prd_DD(rsv_prd_cd,master_id);
		} 
		catch (Exception e)
		{

			throw e;
		}

	}



	/**
	 * nle 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertNleArchive(NleDO nleDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertNleArchive][Input NleDO]" + nleDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.
			int one =systemManageDAO.insertMetadatTbl(nleDO);
			int two =systemManageDAO.insertContentsInfo(nleDO);
			int three =systemManageDAO.insertConInstInfoForHigh(nleDO);
			int nine =systemManageDAO.insertConInstInfoForLow(nleDO);
			int four =systemManageDAO.insertCornerInfo(nleDO);
			int five =systemManageDAO.insertContentsMappInfo(nleDO);
			int eight =systemManageDAO.insertNleTc(nleDO);

			int result = one+two+three+four+five;
			if(result == 5){
				return 1;
			}else{
				return 0;	
			}
		} 
		catch (Exception e)
		{

			throw e;
		}

	}




	/**
	 * 수동 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertManualArchive(String media_id) throws Exception
	{
		try 
		{
			if(systemManageDAO.isThereMediaId(media_id)){
				//프로그램 정보를 갱신한다.
				ManualArchiveDO manualArchiveDO = externalDAO.getManualInfo(media_id);
			
				int one =systemManageDAO.insertMetadatTbl(manualArchiveDO);
				int two =systemManageDAO.insertContentsInfo(manualArchiveDO);
				int three =systemManageDAO.insertConInstInfoForHigh(manualArchiveDO);
				int nine =systemManageDAO.insertConInstInfoForLow(manualArchiveDO);
				int four =systemManageDAO.insertCornerInfo(manualArchiveDO);
				int five =systemManageDAO.insertContentsMappInfo(manualArchiveDO);

				int ten =systemManageDAO.insertAnnotInfo(manualArchiveDO);
				int six = systemManageDAO.insertManual(manualArchiveDO);
				String tmp = String.valueOf(manualArchiveDO.getMaster_id());
				int master_id = Integer.parseInt(tmp);
				systemManageDAO.insertCornerInfoForProceduer(master_id);
				/**
				 * 수동아카이브 건에 대한 건에 대하여 편집완료 상태로 변경 , 기존 원본영상 사용하지 않도록 컬럼변경 
				 */
				if(!systemManageDAO.isThereMediaId(manualArchiveDO.getOrg_media_id())){
					systemManageDAO.updateEdtrIdbyMediaId("002", manualArchiveDO.getOrg_media_id());
					//검색영상에서 폐기시에도 계속 검색되는 현상이 있어 이 로직에서 폐기 조치하지 않도록 함. 2014.11.7 by asura
					//systemManageDAO.updateMetaYn(manualArchiveDO.getOrg_media_id());
				}
				int result = one+two+three+four+five+nine;
				if(result >= 1){
					return 1;
				}else{
					return 0;	
				}
			}
			return 1;
		} 
		catch (Exception e)
		{
			throw e;
		}

	}




	/**
	 * 수동 아카이브  
	 * @param manualArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertManual(ManualArchiveDO manualArchiveDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[insertManual][Input manualArchiveDO]" + manualArchiveDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.
			int six =0;
			if(systemManageDAO.isThereMediaIdForManual(manualArchiveDO.getNew_media_id())){
				six = externalDAO.insertManualArchiveInfo(manualArchiveDO);
			}else{
				six = externalDAO.updateManualArchive(manualArchiveDO);
			}
			return six;

		} 
		catch (Exception e)
		{
			throw e;
		}
	}
	/**
	 * 수동아카이브 건에 편집시 편집상태 변경('001' 편집중, '002' 편집완료)
	 * @param ct_ids
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateEdtrId(String code,String ct_ids) throws Exception
	{
		try 
		{
			return systemManageDAO.updateEdtrId(code,ct_ids);
		} 
		catch (Exception e)
		{
			throw e;
		}

	}


	/**
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List getStorage(StorageDO storageDO) throws Exception
	{
		try 
		{
			return systemManageDAO.selectStorageList(storageDO);


		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List getStorageInfo() throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getStorageInfo]");
		}

		try 
		{

			return systemManageDAO.selectStorageInfo();


		} 
		catch (Exception e)
		{
			throw e;
		}
	}


	/**
	 * PDAS 아카이브  상태변환
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public String deletePDSArchive(DeleteDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[DeleteDO][Input pgmDO]" + pgmDO);
		}

		try 
		{
			String result ="";
			//프로그램 정보를삭제한다.
			//미디어id의 중복여부를 확인한후 만약 존재한다면 폐기처리  
			//존재하지 않는다면 0으로 리턴값을 보낸다
			if(!systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
				result = systemManageDAO.deletePdsINfo(pgmDO);
			}else {
				result ="0";
			}


			return result;
		} 
		catch (Exception e)
		{
	
			throw e;
		}

	}





	/**
	 * IFCMS 아카이브  ver 1.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public PdsArchiveDO insertIfCmsArchive(PdsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[PdsArchiveDO][Input pgmDO]" + pgmDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.

			PdsArchiveDO item = new PdsArchiveDO();

			if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
				int one =systemManageDAO.insertMetadatTbl(pgmDO);
				int two =systemManageDAO.insertContentsInfo(pgmDO);
				int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
				int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
				int four =systemManageDAO.insertCornerInfo(pgmDO);
				int five =systemManageDAO.insertContentsMappInfo(pgmDO);

				int seven =systemManageDAO.insertPdasArchive(pgmDO);

				int ten =systemManageDAO.insertAnnotInfo(pgmDO);
				long ma = pgmDO.getMaster_id();
				String tmp_ma = String.valueOf(ma);
				int master_id = Integer.parseInt(tmp_ma);
				systemManageDAO.insertCornerInfoForProceduer(master_id);
				externalDAO.updatemetatbl(pgmDO);
				int result = one+two+three+four+five+seven+nine;
				System.out.println("result   ="+result);
				item.setCti_id(pgmDO.getCti_id());
			}
			return item;

		} 
		catch (Exception e) 
		{

			throw e;
		}

	}



	/**
	 * IFCMS 아카이브  ver 2.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return : -1(이미등록), 0 (실패), 1 (성공)
	 * @throws Exception 
	 **/
	public int insertIfCmsArchive(IfCmsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[IfCmsArchiveDO][Input pgmDO]" + pgmDO);
		}
		try 
		{
			// group_id 존재여부 체크
			if(systemManageDAO.isThereGroupId(pgmDO.getGroup_id())||pgmDO.getGroup_id() ==0){
				logger.info("group_id["+pgmDO.getGroup_id()+"] not exist! so go on~ ");
				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
					int one =systemManageDAO.insertMetadatTblForIfCms(pgmDO);
					int two =systemManageDAO.insertContentsInfoForIfCms(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);
					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					if(!pgmDO.getPreview_kr().equals("")){
						int six =systemManageDAO.insertPreveiw_Info(pgmDO);

					}
					if(!pgmDO.getCaption_cn().equals("")){
						pgmDO.setType("013");
						int six =systemManageDAO.insertAttachFile(pgmDO);

					}
					if(!pgmDO.getCaption_en().equals("")){
						pgmDO.setType("012");
						int six =systemManageDAO.insertAttachFile(pgmDO);

					}
					if(!pgmDO.getCaption_jp().equals("")){
						pgmDO.setType("011");
						int six =systemManageDAO.insertAttachFile(pgmDO);

					}
					if(!pgmDO.getCaption_kr().equals("")){
						pgmDO.setType("010");
						int six =systemManageDAO.insertAttachFile(pgmDO);

					}



					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					return 1;
				}else{
					//미디어id가 중복이라면 -1리턴
					return -1;
				}
				
			} else {
				logger.info("group_id["+pgmDO.getGroup_id()+"] exist! so media_id["+pgmDO.getMedia_id()+"] try to check. ");
				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){	
					long keyid  =systemManageDAO.getmaster_id(pgmDO.getGroup_id());
					pgmDO.setMaster_id(keyid);
					int two =systemManageDAO.insertContentsInfoForIfCms(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);
					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					int result = two+three+four+five+nine;
					return 1;
				}else{
					//미디어id가 중목이면 -1리턴
					return -1;
				}
			}
		}  catch (Exception e) {
			logger.error("insertIfCmsArchive", e);
			return 0;
		}

	}




	/**
	 * PDAS 아카이브  상태변환 ver 1.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int updatePDSArchiveStatus(PdsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[PdsArchiveDO][Input pgmDO]" + pgmDO);
		}

		try 
		{
			//프로그램 정보를 갱신한다.
			/**
			 * BackendTC WMV & KFRM 생성 요청 하는곳.
			 */
			systemManageDAO.updatePDSArchiveStatus(pgmDO);
			/**
			 * 프리뷰 노트를 생성하는 곳
			 *  */
			long masterid  = systemManageDAO.selectMasterId(pgmDO.getMedia_id());
			pgmDO.setMaster_id(masterid);

			if(!pgmDO.getPreview_file_nm().equals("")||!pgmDO.getPreview_cont().equals("")){
				int six =systemManageDAO.insertPreveiw_Info(pgmDO);

			}



			//ct_id, cti_id(low) 값을 얻어온다
			PdsArchiveDO pgm = systemManageDAO.selectTcInfo(pgmDO.getMedia_id());
			pgmDO.setCt_id(pgm.getCt_id());
			pgmDO.setCti_idForlow(pgm.getCti_idForlow());

			//tc job 등록한다.


			systemManageDAO.insertPDS(pgmDO);


			return 1;


		} 
		catch (Exception e)
		{

			throw e;
		}

	}



	/**
	 * PDAS 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public String insertPdasArchiveIFcms(PdsArchiveDO pgmDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[PdsArchiveDO][Input pgmDO]" + pgmDO);
		}
		try 
		{
			//프로그램 정보를 갱신한다.

			if(pgmDO.getCocd().equals("")){

				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
					int one =systemManageDAO.insertMetadatTbl(pgmDO);
					int two =systemManageDAO.insertContentsInfo(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);

					int seven =systemManageDAO.insertPdasArchive(pgmDO);

					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine;
					System.out.println("result   ="+result);
					if(result >= 4){
						return String.valueOf(pgmDO.getMaster_id());
					}else{
						return "0";	
					}
				}
			}else if(!pgmDO.getCocd().equals("")){

				if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
					int one =systemManageDAO.insertMetadatTbl(pgmDO);
					int two =systemManageDAO.insertContentsInfo(pgmDO);
					int three =systemManageDAO.insertConInstInfoForHigh(pgmDO);
					int nine =systemManageDAO.insertConInstInfoForLow(pgmDO);
					int four =systemManageDAO.insertCornerInfo(pgmDO);
					int five =systemManageDAO.insertContentsMappInfo(pgmDO);

					int seven =systemManageDAO.insertPdasArchive(pgmDO);

					int ten =systemManageDAO.insertAnnotInfo(pgmDO);
					long ma = pgmDO.getMaster_id();
					String tmp_ma = String.valueOf(ma);
					int master_id = Integer.parseInt(tmp_ma);
					systemManageDAO.insertCornerInfoForProceduer(master_id);
					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine;
					System.out.println("result   ="+result);
				}

				return String.valueOf(pgmDO.getMaster_id());

			}
			return "-1";
		} 
		catch (Exception e)
		{
			throw e;
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
		if(logger.isDebugEnabled())
		{
			logger.debug("[getStorageCheck]  " +storageDO);
		}

		try 
		{
			return systemManageDAO.getStorageCheck(storageDO);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}







	/**
	 * 복본/소산 생성요청을 한다( 기존 아카이브 건에 대해서 복본생성만 요청한다.)
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateCopyRequestForCt_id(UseInfoDO useInfoDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateCopyRequestForCt_id][Input UseInfoDO]" + useInfoDO);
		}

		try 
		{

			if(useInfoDO.getGubun().equals("001")){
				//복본신청표기
				externalDAO.updateCopyYNForCtId(useInfoDO.getCt_id(),useInfoDO.getUser_id());
				externalDAO.updateCopyReqIdForCtId(useInfoDO.getCt_id(),useInfoDO.getUser_id());
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForCt_id(useInfoDO.getCt_id());
				useInfoDO.setDtl_type(dtl_type);
				//프로그램 정보를 신청한다한다.
				return	systemManageDAO.updateCopyRequestForCtId(useInfoDO);
			}else if(useInfoDO.getGubun().equals("002")){
				//소산신청표기
				externalDAO.updateBackUpYNForCtId(useInfoDO.getCt_id(),useInfoDO.getUser_id());
				externalDAO.updateBackUpYNForCtId(useInfoDO);
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForCt_id(useInfoDO.getCt_id());
				useInfoDO.setDtl_type(dtl_type);
				//소산을 workflow에  신청한다
				return	systemManageDAO.updateBackupRequestForCtId(useInfoDO);
			}else if(useInfoDO.getGubun().equals("003")){
				//복원신청표기
				// DTL 정보를 위하여 본영상의 회사코드를 구한다.
				String dtl_type = externalDAO.selectCOCDForCt_id(useInfoDO.getCt_id());
				externalDAO.updateReCorverReqIdForCtId(useInfoDO.getCt_id(),useInfoDO.getUser_id());
				useInfoDO.setDtl_type(dtl_type);
				//복원을 workflow에  신청한다
				return	systemManageDAO.updateRecorveryRequestForCtId(useInfoDO);
			}
		} 
		catch (Exception e) 
		{
			
			throw e;
		}
		return "";

	}


	/**  
	 *  스케쥴 동작 시간을 업데이트한다.
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRunScheduleDt() throws Exception
	{
		try 
		{
			return systemManageDAO.updateRunScheduleDt();
		} 
		catch (Exception e)
		{

			throw e;
		}

	}
}

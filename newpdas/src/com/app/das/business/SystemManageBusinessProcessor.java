package com.app.das.business;

import java.io.File;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.das.business.dao.CodeInfoDAO;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.dao.SystemManageDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.AttachFileInfoDO;
import com.app.das.business.transfer.AttachItem;
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
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.StorageDO;
import com.app.das.business.transfer.SubsiInfoDO;
import com.app.das.business.transfer.SystemManageConditionDO;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CommonUtl;
import com.app.das.util.DBService;


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
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();

	private Logger logger = Logger.getLogger(SystemManageBusinessProcessor.class);

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForMaxDate(DASCommonDO commonDO) throws Exception
	{

		return systemManageDAO.selectEquipmentMonitoringListForMax(commonDO);

	}

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForMaxDate(String workStateCode, DASCommonDO commonDO) throws Exception
	{

		return systemManageDAO.selectEquipmentMonitoringListForMax(workStateCode, commonDO);

	}

	/**
	 * 모니터링 장비에서 로그테이블의 장비별 가장 최신것을 조회한다.
	 * @param commonDO
	 * @return List EquipmentMonitoringDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getEquipmentMonitoringListForToDate(String eqClfCd, DASCommonDO commonDO) throws Exception
	{

		return systemManageDAO.selectEquipmentMonitoringListForToDate(eqClfCd, commonDO);

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

		PageDO pageDO = systemManageDAO.selectEquipmentMonitoringListForWork(conditionDO, commonDO);

		return pageDO;

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

		PageDO pageDO = systemManageDAO.selectEquipmentLogList(conditionDO, commonDO);

		return pageDO;

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

		return systemManageDAO.selectContentsUseInfoList(conditionDO, commonDO);

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

		PageDO pageDO = systemManageDAO.selectNonLoginUserList(conditionDO, commonDO);

		return pageDO;

	}

	/**
	 * 사용 중지된 외부 사용자의 유효 종료일을 복구시킨다
	 * @param userInfoDOList 사용자 정보인 UserInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void restoreNonLoginUserList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{

		systemManageDAO.updateNonLoginUserRestoreList(userInfoDOList, commonDO);

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

		return systemManageDAO.selectContentsOutUseInfoList(conditionDO, commonDO);

	}

	/**
	 * 미접속 외부 사용자를 중지시킨다.
	 * @param userInfoDOList UserInfoDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void stopOutUserList(List userInfoDOList, DASCommonDO commonDO) throws Exception
	{

		systemManageDAO.updateOutUserStopList(userInfoDOList, commonDO);

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

		return systemManageDAO.selectDownloadStatusList(conditionDO, commonDO);

	}

	/**
	 * 다운로드 진행사항 조회에서 상태를 사용중으로 복구한다.
	 * @param downStatusInfoDOList DownStatusInfoDO를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void recoveryDownloadStatusList(List downStatusInfoDOList, DASCommonDO commonDO) throws Exception
	{

		systemManageDAO.updateRecoveryDownloadStatusList(downStatusInfoDOList, commonDO);

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public PageDO getProgramList(SystemManageConditionDO condition, DASCommonDO commonDO) throws Exception
	{

		PageDO pageDO = systemManageDAO.selectProgramList(condition, commonDO);

		return pageDO;

	}



	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_nm 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List getParentsInfo(String pgm_nm) throws Exception
	{

		return  systemManageDAO.selectParentsInfo(pgm_nm);

	}



	/**
	 * 프로그램 정보를 삭제 한다.
	 * @param prgId 프로그램 ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */	
	public void deleteProgramInfo(String prgId, DASCommonDO commonDO)  throws Exception
	{

		//프로그램 정보를 삭제한다.
		systemManageDAO.deleteProgramInfo(prgId, commonDO);

	}

	/**
	 * 권한 코드를 삭제한다
	 * @param codeDO 코드 정보
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */	
	public int deleteScreenAuthentication(CodeDO codeDO)  throws Exception
	{

		//코드테이블에서 권한 코드를 삭제한다.
		int ctmp = codeInfoDAO.deleteCodeInfo(codeDO);

		//권한 테이블에서 권한 정보를 삭제한다
		systemManageDAO.deleteScreenAuthentication(codeDO);

		return ctmp;

	}

	/**
	 * 프로그램 정보를 수정한다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		systemManageDAO.updateProgramInfo(pgmDO, commonDO);

	}


	/**
	 * 프로그램 정보를 추가한다. 이 함수가 호출되는 경우는 ERP에만 프로그램 데이터가 있고 DAS에는 없는 경우이다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertProgramInfo(ProgramInfoDO pgmDO, DASCommonDO commonDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		systemManageDAO.insertProgramInfo(pgmDO, commonDO);

	}

	/**
	 * 프로그램 정보를 조회한다.
	 * @param pgmId 프로그램id
	 * @return ProgramInfoDO 
	 * @throws Exception 
	 */
	public ProgramInfoDO getSelectedProgamInfo(String pgmId) throws Exception
	{

		//선택된 프로그램 정보를 가져온다.
		ProgramInfoDO pgmInfoDO = null;
		pgmInfoDO = systemManageDAO.selectProgramInfoByID(pgmId);
		return pgmInfoDO;

	}


	/**
	 * 코드를 보고 ERP의 프로그램 정보를 가져온다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public ProgramInfoDO getSelectedERPProgamInfoByCode(String pgmCode) throws Exception
	{

		//선택된 프로그램 정보를 가져온다.
		ProgramInfoDO pgmInfoDO = null;
		pgmInfoDO = systemManageDAO.selectERPProgramInfoByCode(pgmCode);
		return pgmInfoDO;

	}

	/**
	 * 매체변환의 오류내역 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return PageDO
	 * @throws Exception 
	 */
	public PageDO getErrorList(SystemManageConditionDO condition, DASCommonDO commonDO, String excel) throws Exception
	{

		PageDO pageDO = systemManageDAO.selectErrorList(condition, commonDO, excel);

		return pageDO;

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

		return systemManageDAO.selectPhotoDownList(conditionDO, commonDO);

	}




	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public List getPgmList(ProgramInfoDO condition) throws Exception
	{

		return systemManageDAO.selectNewPgmList(condition);

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */
	public String getPgmList2(ProgramInfoDO condition) throws Exception
	{

		return systemManageDAO.selectPgmList2(condition);

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param pgm_cd 프로그램코드
	 * @return List
	 * @throws Exception 
	 */

	public List getPgmInfo(String pgm_cd) throws Exception
	{

		return systemManageDAO.selectPgm(pgm_cd);

	}


	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param title 프로그램명
	 * @return List
	 * @throws Exception 
	 */
	public List getPgmInfo2(String pgm_cd) throws Exception
	{

		return systemManageDAO.selectPgm2(pgm_cd);

	}

	/**
	 * 프로그램 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return List
	 * @throws Exception 
	 */

	public List getParentsCD(String pgm_cd) throws Exception
	{

		return systemManageDAO.selectParents(pgm_cd);

	}


	/**
	 * 프로그램 정보를 추가한다. 이 함수가 호출되는 경우는 ERP에만 프로그램 데이터가 있고 DAS에는 없는 경우이다.
	 * @param pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 */
	public int insertPgmcd(ProgramInfoDO pgmDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertPgmInfo(pgmDO);

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
		return systemManageDAO.getAvailableDisk();
	}

	/**
	 * 프로그램코드 수정한다
	 * @param  pgmDO 프로그램 정보가 포함되어 있는 DataObject
	 * @throws Exception 
	 */
	public int updatePgmcd(ProgramInfoDO programInfoDO) throws Exception
	{

		if(systemManageDAO.isTherePgmCd(programInfoDO.getPgmCd())){
			return systemManageDAO.insertPgmInfo(programInfoDO);
		}else{

			return systemManageDAO.updatePgmcd(programInfoDO);
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

		return systemManageDAO.selectSubsiServerList(condition);

	}



	/**
	 * 계열사 수신서버 코드를 등록한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return updatecount
	 * @throws Exception 
	 */
	public int insertSubsiServer(SubsiInfoDO pgmDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertSubsiServer(pgmDO);

	}


	/**
	 * 계열사 수신서버 정보 수정한다
	 * @param subsiInfoDO                                                                                                                                                                                             
	 * @return    updatecount                                                                                                                                                                                          
	 * @throws Exception 
	 */

	public int updateSubsiServer(SubsiInfoDO subsiInfoDO) throws Exception
	{

		return systemManageDAO.updateSubsiServer(subsiInfoDO);

	}


	/**
	 * 프로그램 복본관리를 조회한다.(다중조회)
	 * @param  copyDO     조회를 위한 beans                                                                                                                                                                                         
	 * @return  List                                                                                                                                                                                    
	 * @throws Exception 
	 */
	public List getCopyList(CopyInfoDO condition) throws Exception
	{

		return systemManageDAO.selectCopyList(condition);

	}



	/**
	 * 프로그램 복본관리를 등록한다.
	 * @param copyDO    등록을 위한 beans                                                                                                                                                                                          
	 * @return    updatecount                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public int insertCopy(CopyInfoDO copyDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		if(systemManageDAO.isTherePgmid(copyDO.getCms_pgm_id())){
			return	0;
		}
		return systemManageDAO.insertCopy2(copyDO);

	}

	/**
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateCopy(CopyInfoDO copyDO) throws Exception
	{

		return systemManageDAO.updateCopy2(copyDO);

	}


	/**
	 * 이용현황 정보 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getNewUseInfoList(UseInfoDO condition, String flag) throws Exception
	{

		return systemManageDAO.selectNewUseInfoList(condition, flag);

	}

	@Deprecated
	public List getUseInfoList(UseInfoDO condition) throws Exception
	{

		return systemManageDAO.selectUseInfoList(condition);

	}

	public UseInfoDO getUseInfoCount(UseInfoDO condition, String flag) throws Exception {

		return systemManageDAO.selectUseInfoCount(condition, flag);
	}

	/**
	 * 이용현황 정보(총조회건수, 총길이) 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getUseInfoList2(UseInfoDO condition) throws Exception
	{

		return systemManageDAO.selectUseInfoList2(condition);

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

		return systemManageDAO.selectArchiveStatusList(condition);

	}


	/**
	 * 아카이브 상태 목록을 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public String getArchiveStatusList2(ArchiveInfoDO condition) throws Exception
	{

		return systemManageDAO.selectArchiveStatusList2(condition);

	}

	/**
	 * 오늘의 화면 조회한다.

	 * @param todayDO                                                                                                                                     종료일
	 * @return                                                                                                                                     MetaInfoDO XML string
	 * @throws Exception 
	 */

	public List getTodayList() throws Exception
	{

		return systemManageDAO.selectTodayList();

	}

	/**
	 * 명장면 조회한다.

	 * @param goodDO                                                                                                                                     종료일
	 * @return                                                                                                                                     MetaInfoDO XML string
	 * @throws Exception 
	 */
	public List getGoodMediaList() throws Exception
	{

		return systemManageDAO.selectGoodMediaList();

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

		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
		for(int i =0;i<targetName.length;i++){
			_xml.append("<" + targetName[i] + ">" + systemManageDAO.getDiskAva(targetValue[i]) + "</"  + targetName[i] + ">");
		}
		_xml.append("</das>");

		if(logger.isDebugEnabled())
			logger.debug("_xml" + _xml.toString());
		return _xml.toString();
	}


	/**
	 * PDS 프로그램별 사용자 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmUserInfo(PgmUserInfoDO pdsInfoDO) throws Exception
	{
		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertPdsPgmUserInfo(pdsInfoDO);
	}

	/**
	 * PDS 프로그램별 사용자 정보를 등록한다.(벌크)
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int[] insertPdsPgmUserInfoAll(List pdsInfoDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertPdsPgmUserInfoAll(pdsInfoDO);

	}





	/**
	 * PDS 프로그램 정보를 등록한다.
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfo(PgmInfoDO pdsInfoDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertPdsPgmInfo(pdsInfoDO);

	}




	/**
	 * PDS 프로그램 정보를 등록한다.(벌크)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsPgmInfoAll(List pdsInfoDOs) throws Exception
	{

		//프로그램 정보를 갱신한다.
		systemManageDAO.insertPdsPgmInfoAll(pdsInfoDOs);
		return	1;

	}




	/**
	 * PDS-DAS간 연동 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int insertPdsMappInfo(PdsMappDO pdsInfoDO) throws Exception
	{

		//프로그램 정보를 갱신한다.
		return	systemManageDAO.insertPdsMappInfo(pdsInfoDO);

	}

	/**
	 * PDS에서아카이브 요청시 메타 등록 처리 부분을 재구성 했음.
	 * 등록 과정 중 오류가 발생하면 메타 데이타를 모두 롤백 처리하고
	 * 요청 클라이언트에게 오류 메세지를 전달함.
	 * 성공: 1, Media_id 존재: -1, 오류: 오류 메세지
	 * 2105.10.28 강명성
	 * @param pgmDO
	 * @return
	 * @throws Exception
	 */
	public int insertNewPdasArchive(PdsArchiveDO pgmDO) throws Exception
	{

		if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
			Connection conn = null;
			try {
				conn = DBService.getInstance().getConnection();
				conn.setAutoCommit(false);
				pgmDO.setConn(conn);

				systemManageDAO.insertMetadatTbl(pgmDO);
				systemManageDAO.insertContentsInfo(pgmDO);
				systemManageDAO.insertConInstInfoForHigh(pgmDO);
				systemManageDAO.insertConInstInfoForLow(pgmDO);
				systemManageDAO.insertCornerInfo(pgmDO);
				systemManageDAO.insertContentsMappInfo(pgmDO);
				systemManageDAO.insertPdasArchive(pgmDO);
				systemManageDAO.insertAnnotInfo(pgmDO);

				// 2015.10.27
				// corner_search_tbl 에 코너정보 입력 <== 향후 사용 안해도 됨. function 을 만들었음
				// corner_concat <== v_das_program 에서 함수호출
				try {
					systemManageDAO.insertCornerInfoForProceduer(Long.valueOf(pgmDO.getMaster_id()).intValue());
				} catch (Exception e) {
					logger.error("corner search add error", e);
				}

				externalDAO.updatemetatbl(pgmDO);

			} catch (Exception e) {
				if(conn != null) {
					logger.error("insertPdasArchive2 all rollback!!! - media_id: "+pgmDO.getMedia_id());
					conn.rollback();
				}
				throw e;
			} finally {
				if(conn != null) {
					conn.commit();
					conn.setAutoCommit(true);
					conn.close();

					pgmDO.setConn(null);
				}
			}
		} else return -1; // media_id 중복

		return 1;
	}

	/**
	 * PDAS 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	@Deprecated
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

					// corner_search_tbl 에 코너정보 입력 <== 향후 사용 안해도 됨. function 을 만들었음
					// corner_concat <== v_das_program 에서 함수호출
					systemManageDAO.insertCornerInfoForProceduer(master_id);

					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine+ten;

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

					// corner_search_tbl 에 코너정보 입력 <== 향후 사용 안해도 됨. function 을 만들었음
					// corner_concat <== v_das_program 에서 함수호출
					systemManageDAO.insertCornerInfoForProceduer(master_id);

					externalDAO.updatemetatbl(pgmDO);
					int result = one+two+three+four+five+seven+nine+ten;
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

		try {
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
		} catch (Exception e) {
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


	/**  
	 * 검수완료처리한다
	 * @param     master_id                                                                                                                                                                                    
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateAccept(long master_id) throws Exception
	{

		return systemManageDAO.updateAccept(master_id);

	}


	/**  
	 * 정리완료처리한다
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateArrange(long master_id, String user_id) throws Exception
	{

		return systemManageDAO.updateArrange(master_id,user_id);

	}

	/**  
	 * 기본정보 보존기간을 수정한다
	 * @param     rsv_prd_cd                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateRsv_Prd_DD(String rsv_prd_cd,long master_id) throws Exception
	{
		return systemManageDAO.updateRsv_Prd_DD(rsv_prd_cd,master_id);
	}



	/**
	 * nle 아카이브  
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertNleArchive(NleDO nleDO) throws Exception
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
		} else {
			return 0;	
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




	/**
	 * 수동 아카이브  
	 * @param manualArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public int insertManual(ManualArchiveDO manualArchiveDO) throws Exception
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

	/**
	 * 수동아카이브 건에 편집시 편집상태 변경('001' 편집중, '002' 편집완료)
	 * @param ct_ids
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateEdtrId(String code,String ct_ids) throws Exception
	{

		return systemManageDAO.updateEdtrId(code,ct_ids);

	}


	/**
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List getStorage(StorageDO storageDO) throws Exception
	{

		return systemManageDAO.selectStorageList(storageDO);

	}


	/**
	 * 다운로드 임계치 조회 함수
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 */
	public List getStorageInfo() throws Exception
	{

		return systemManageDAO.selectStorageInfo();

	}


	/**
	 * PDAS 아카이브  상태변환
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public String deletePDSArchive(DeleteDO pgmDO) throws Exception
	{

		String result ="";

		//미디어id의 중복여부를 확인한후 만약 존재한다면 폐기처리  
		//존재하지 않는다면 0으로 리턴값을 보낸다
		if(!systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){
			result = systemManageDAO.deletePdsINfo(pgmDO);
		}else {
			result ="0";
		}
		return result;

	}





	/**
	 * IFCMS 아카이브  ver 1.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 **/
	public PdsArchiveDO insertIfCmsArchive(PdsArchiveDO pgmDO) throws Exception
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

			item.setCti_id(pgmDO.getCti_id());
		}
		return item;

	}


	/**
	 * IFCMS에서아카이브 요청시 메타 등록 처리 부분을 재구성 했음.
	 * 등록 과정 중 오류가 발생하면 메타 데이타를 모두 롤백 처리하고
	 * 요청 클라이언트에게 오류 메세지를 전달함.
	 * 성공: 1, Media_id 존재: -1, 오류: 오류 메세지
	 * 2105.10.28 강명성
	 * @param pgmDO
	 * @return
	 * @throws Exception
	 */
	public int insertNewIfCmsArchive(IfCmsArchiveDO pgmDO) throws Exception
	{
		Connection conn = null;
		try {
			if(systemManageDAO.isThereMediaId(pgmDO.getMedia_id())){

				conn = DBService.getInstance().getConnection();
				conn.setAutoCommit(false);

				pgmDO.setConn(conn);

				boolean isGroupId = false;
				if(systemManageDAO.isThereGroupId(pgmDO.getGroup_id()) || pgmDO.getGroup_id() == 0){
					systemManageDAO.insertMetadatTblForIfCms(pgmDO);
				} else {
					isGroupId = true;
					long keyid  =systemManageDAO.getmaster_id(pgmDO.getGroup_id());
					pgmDO.setMaster_id(keyid);
				}

				systemManageDAO.insertContentsInfoForIfCms(pgmDO);
				systemManageDAO.insertConInstInfoForHigh(pgmDO);
				systemManageDAO.insertConInstInfoForLow(pgmDO);
				systemManageDAO.insertCornerInfo(pgmDO);
				systemManageDAO.insertContentsMappInfo(pgmDO);
				systemManageDAO.insertAnnotInfo(pgmDO);

				if(!isGroupId){
					if(!pgmDO.getPreview_kr().equals("")){
						systemManageDAO.insertPreveiw_Info(pgmDO);
					}
					if(!pgmDO.getCaption_cn().equals("")){
						pgmDO.setType("013");
						systemManageDAO.insertAttachFile(pgmDO);
					}
					if(!pgmDO.getCaption_en().equals("")){
						pgmDO.setType("012");
						systemManageDAO.insertAttachFile(pgmDO);
					}
					if(!pgmDO.getCaption_jp().equals("")){
						pgmDO.setType("011");
						systemManageDAO.insertAttachFile(pgmDO);
					}
					if(!pgmDO.getCaption_kr().equals("")){
						pgmDO.setType("010");
						systemManageDAO.insertAttachFile(pgmDO);
					}
				}

				// 코너 정보를 프로시저를 통해 별도의 테이블에 저장하고 있음. 검색엔진을 위한 작업임.
				systemManageDAO.insertCornerInfoForProceduer(Long.valueOf(pgmDO.getMaster_id()).intValue());

				externalDAO.updatemetatbl(pgmDO);
			} else return -1;
		} catch (Exception e) {
			if(conn != null) conn.rollback();
			logger.error("insertIfCmsArchive all rollback!!! - media_id: "+pgmDO.getMedia_id());
			throw e;
		} finally {
			if(conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();

					pgmDO.setConn(null);
				} catch (Exception e2) {}
			}
		}

		return 1;
	}

	/**
	 * IFCMS 아카이브  ver 2.0
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return : -1(이미등록), 0 (실패), 1 (성공)
	 * @throws Exception 
	 **/
	@Deprecated
	public int insertIfCmsArchive(IfCmsArchiveDO pgmDO) throws Exception
	{
		try 
		{
			// group_id 존재여부 체크
			if(systemManageDAO.isThereGroupId(pgmDO.getGroup_id())||pgmDO.getGroup_id() == 0){
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
			throw e;
		}

	}




	/**
	 * PDAS 아카이브  상태변환 ver 1.0
	 * insertPDSArchive 호출을 통해서 입력된 프로그램에 대한 정보 업데이트.
	 * MXF 영상 및 첨부파일이 정상적으로 존재할 경우 호출되어진다. state 값이 '000' 이면 정상
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int updatePDSArchiveStatus(PdsArchiveDO pgmDO) throws Exception {

		long masterid  = systemManageDAO.selectMasterId(pgmDO.getMedia_id());
		if(masterid > 0) {

			pgmDO.setMaster_id(masterid);

			// 프로그램 상태 업데이트
			int update = systemManageDAO.updatePDSArchiveStatus(pgmDO);

			if(update > 0) {
				// The Preview Note will insert
				try {
					if(StringUtils.isNotBlank(pgmDO.getPreview_file_nm()) 
							|| StringUtils.isNotBlank(pgmDO.getPreview_cont())){
						systemManageDAO.insertPreveiw_Info(pgmDO);
					}
				} catch (Exception e) {
					logger.error("insert preview_note error", e);
				}

				//ct_id, cti_id(low) 값을 얻어온다
				PdsArchiveDO pgm = systemManageDAO.selectTcInfo(pgmDO.getMedia_id());
				pgmDO.setCt_id(pgm.getCt_id());
				pgmDO.setCti_idForlow(pgm.getCti_idForlow());

				/*
				 * PDS 아카이브 요청에서 첨부파일 형식으로 넘어오는 자료가 있을경우 File Type에 따라 후처리 작업을 한다.
				 * 현재는 CG 편집용 압축파일만 전송하지만 추가 형식이 올 수도 있음
				 * 2015.11.12
				 */
				if(pgmDO.getAttatches() != null && pgmDO.getAttatches().size() > 0) {
					int size = pgmDO.getAttatches().size();
					for(int i=0; i<size; i++) {
						AttachItem item = (AttachItem)pgmDO.getAttatches().get(i);
						try {
							/*
							 * CG 편집관련 파일(zip)을 아카이빙 하기위한 로직을 추가한다.
							 * File Type 값이 '015' => CG 편집용 압축파일,  첨부파일유형코드 [P015]
							 * /mp4/<년월>/<일>/<CT_ID>/CG/<org_file_name>.zip <= mp4 위치와 동일하게 설정해야 함.
							 */
							if(logger.isInfoEnabled()) {
								logger.info("attach file_type: "+item.getAttcFileType()+", filename: "+pgmDO.getOrgFileName()+", filesize: "+pgmDO.getFilesize());
							}
							if(item.getAttcFileType().equals("015")) {
								String path = !pgmDO.getStorage_path().startsWith("/") ? "/"+pgmDO.getStorage_path() : pgmDO.getStorage_path();

								if(logger.isDebugEnabled()) {
									logger.debug("org_attach_filepath: "+path);
								}

								File f = new File(path, pgmDO.getOrgFileName());
								if(f.exists()) {
									// /mp4/년월/일/CT_ID 아래에 CG 폴더를 생성하고 하위에 복사하면 됨.
									String dateTime2 = CalendarUtil.getDateTime("yyyyMM/dd");
									String cgPath ="/"+dasHandler.getProperty("WINMP4")+"/"+dateTime2+"/"+pgmDO.getCt_id()+"/CG";

									File cg = new File(cgPath, pgmDO.getOrgFileName());

									// 폴더가 있는지 여부를 체크하여 없다면 폴더 생성
									File parent = cg.getParentFile();
									if(!parent.exists()) {
										parent.mkdirs();
									}

									// CG 파일을 MP4 
									FileUtils.copyFile(f, cg);
									if(logger.isDebugEnabled()) {
										logger.debug("new_attach_filepath: "+cg.getAbsolutePath());
									}

									AttachFileInfoDO attach = new AttachFileInfoDO();
									attach.setMothrDataId(pgmDO.getMaster_id());
									attach.setAttcFileTypeCd(item.getAttcFileType());
									attach.setAttcClfCd("001");
									attach.setRegDt(CalendarUtil.getDateTime("yyyyMMddHHmmss"));
									attach.setRegrid(pgmDO.getReq_id());
									attach.setFileSize(pgmDO.getFilesize());
									attach.setFileName(pgmDO.getOrgFileName());
									attach.setOrgFileNm(pgmDO.getOrgFileName());
									attach.setFilePath(cgPath);

									externalDAO.insertAttachFile(attach);
									if(logger.isDebugEnabled()) {
										logger.debug("attach_tbl inserted: "+pgmDO.getMaster_id()+", file_name: "+pgmDO.getOrgFileName());
									}

									// 한번 더 원 위치에 압축파일이 존재하는지 체크한 후 있다면 재삭제. 시스템 명령어 사용.
									if(f.exists()) {
										logger.debug("org_file delete : "+f.getAbsolutePath());
										CommonUtl.fileForceDelete(f);
									}
								} else {
									logger.error("attach file not exists! - "+f.getAbsolutePath());
								}

								// 추가 첨부파일이 온다면 아래에 정의하면 됨.
							}
						} catch (Exception e) {
							//첨부파일 등록 오류가 나더라도 계속 진행한다.
							logger.error("PDS Attach File Error", e);
						}
					}
				}

				//tc job 등록한다.
				systemManageDAO.insertPDS(pgmDO);

				return 1;
			} else return 0;
		} else {
			return 0;
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
				}

				return String.valueOf(pgmDO.getMaster_id());

			}
			return "-1";
		} catch (Exception e) {
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

		return systemManageDAO.getStorageCheck(storageDO);

	}







	/**
	 * 복본/소산 생성요청을 한다( 기존 아카이브 건에 대해서 복본생성만 요청한다.)
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public String updateCopyRequestForCt_id(UseInfoDO useInfoDO) throws Exception
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
		return systemManageDAO.updateRunScheduleDt();
	}
}

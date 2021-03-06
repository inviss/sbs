/***********************************************************
 * 프로그램ID  	: ExternalBusinessProcessor.java
 * 프로그램명  	: ExternalBusinessProcessor
 * 작성일자     	:
 * 작성자       	:
 * 설명          : Client 와 WebService 관련 로직이 있습니다.
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0
 */
package com.app.das.business;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.dao.CodeInfoDAO;
import com.app.das.business.dao.DisuseDAO;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.dao.SystemManageDAO;
import com.app.das.business.dao.UserInfoDAO;
import com.app.das.business.dao.WorkDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.Annot;
import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.business.transfer.ArchiveReqDO;
import com.app.das.business.transfer.Attach;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.ContentMappInfoDO;
import com.app.das.business.transfer.ContentsInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.DeleteDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.business.transfer.DtlInfoDO;
import com.app.das.business.transfer.EquipMentInfoDO;
import com.app.das.business.transfer.ErrorLogDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.Ingest;
import com.app.das.business.transfer.KeyFrameImgDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.ManagementInfoDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.business.transfer.MediaArchiveDO;
import com.app.das.business.transfer.MediaInfoDO;
import com.app.das.business.transfer.MetaDataInfo;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.NdsDownDO;
import com.app.das.business.transfer.PathInfoDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.business.transfer.PdsDownDO;
import com.app.das.business.transfer.PhotDownDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.business.transfer.PreProcessingDO;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.Relation;
import com.app.das.business.transfer.ScenarioDO;
import com.app.das.business.transfer.ServersDO;
import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.TimeRist;
import com.app.das.business.transfer.TimeRistInfo;
import com.app.das.business.transfer.TotalChangeInfoDO;
import com.app.das.business.transfer.TransferDO;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.business.transfer.VideoPageInfoDO;
import com.app.das.business.transfer.VideoPageMetaInfoDO;
import com.app.das.business.transfer.WmvH264DO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.log.DasPropHandler;
import com.app.das.services.TransferDOXML;
import com.app.das.services.XmlConvertorService;
import com.app.das.services.XmlConvertorServiceImpl;
import com.app.das.util.CommonUtl;
import com.app.das.util.DBService;
import com.app.das.util.StringUtils;
import com.sbs.das.web.NevigatorProxy;
import com.sbs.erp.service.OrderCallServiceProxy;
import com.sbs.tm.service.Tansfer;
import com.sbs.tm.service.TansferLocator;
import com.sbs.tm.service.TansferPortType;
import com.sbs.tm.service.TansferPortTypeProxy;

/**
 * 외부시스템 연동이나 das관리 업무를 처리하는 함수이다
 * @author asura207
 *
 */
public class ExternalBusinessProcessor 
{
	private Logger logger = Logger.getLogger(ExternalBusinessProcessor.class);	

	private static SystemManageDAO systemManageDAO = SystemManageDAO.getInstance();
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();


	private static final UserInfoDAO userInfoDAO = UserInfoDAO.getInstance();


	private static  CodeInfoDAO codeInfoDAO = CodeInfoDAO.getInstance();

	private static DisuseDAO disuseDAO = DisuseDAO.getInstance();

	private static WorkDAO workDAO = WorkDAO.getInstance();


	/**
	 * 카트정보를 조회한다(다운로드 카트정보, 카트내용정보)
	 * @param cartNo 카트번호 
	 * @param reqUserId 요청자 ID
	 * @return DownCartDO
	 * @throws Exception 
	 */
	public DownCartDO getCartInfo(long cartNo, String reqUserId) throws Exception
	{

		return externalDAO.selectDownCartInfo(reqUserId);

	}

	/**
	 * 입력받은 콘텐트 ID로 파일 경로 정보를 가저온다
	 * @param contentId 콘텐트 번호
	 * @return PathInfoDO
	 * @throws Exception 
	 */
	public PathInfoDO getFilePathInfo(long contentId) throws Exception
	{

		// 파일 path 정보를 조회한다.
		PathInfoDO pathInfoDO = externalDAO.selectFilePathInfo(contentId);

		return pathInfoDO;

	}

	/**
	 * CS  에서 받은 카트 정보를 저장한다.<p>
	 * 다운로드 카트 정보를  DB 스키마와 동일하게 받아 저장한다.
	 * @param downCartDO 카트정보
	 * @param commonDO 공통정보
	 * @return DownCartDO 
	 * @throws Exception 
	 */
	public DownCartDO insertDownCartInfo(DownCartDO downCartDO) throws Exception
	{

		DownCartDO resultDownCartDO = null;
		try 
		{
			//이미 카트가 존재하는지를 검증한다.
			long nCartNo = externalDAO.isNewThereDownCart(downCartDO.getReqUsrid(), downCartDO.getMaster_id()); 
			if( nCartNo >= 0) {
				downCartDO.setCartStat(DASBusinessConstants.DOWN_CART_EXIST);
				downCartDO.setCartNo(nCartNo);
				return downCartDO;
			}

			resultDownCartDO = externalDAO.insertCartInfo(downCartDO);

			return resultDownCartDO;
		} catch (Exception e) {
			throw e;
		}		

	}

	/**
	 * 스토리지에 있는 클립들을 DAS-TM에 전송 요청 전달하는 프로그램
	 * @param downCartDO 스토리지 다운에 필요한 정보
	 * @return downCartDO
	 * @throws Exception 
	 */
	public DownCartDO insertStDownCartInfo(DownCartDO downCartDO) throws Exception
	{

		DownCartDO resultDownCartDO = externalDAO.insertCartInfo(downCartDO);

		return resultDownCartDO;

	}

	/**
	 * 사용하지 않는 함수
	 * @param master_id
	 * @return downCartDO 스토리지 다운에 필요한 정보
	 * @throws Exception 
	 */
	public DownCartDO insertDownCartInfoTotal(long master_id) throws Exception
	{
		DownCartDO resultDownCartDO = externalDAO.selectMetadatList(master_id);

		return resultDownCartDO;
	}

	/**
	 * 에러정보를 등록 및 갱신한다.
	 * @param errorRegisterDO 에러 정보
	 * @throws Exception 
	 * @retrun errorRegisterDO 에러 정보		
	 */
	public ErrorRegisterDO insertErrorInfo(ErrorRegisterDO errorRegisterDO) throws Exception
	{

		return externalDAO.insertErrorInfo(errorRegisterDO);

	}

	/**
	 * 관련영상 마스터 ID를 전달한다.
	 * @param masterId 부모 마스터 정보
	 * @return  Child_master_id
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getRelationMaster(long masterId) throws Exception
	{

		return externalDAO.selectRelationMaster(masterId);

	}

	/**
	 * 관련영상 마스터 데이타 조회한다.
	 * 관련영상이 등록된 영상의 master_id
	 * @param masterId 마스터 아이디
	 * @return child_master_id 자식 마스터 아이디
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getRelationTotaly(long masterId) throws Exception
	{

		return externalDAO.selectRelationMaster(masterId);

	}

	/**
	 *  프로그램이름, 방송일, 등등을 포함하는 프로그램 정보을 모두 가져온다
	 * @param programDO 조회조건 정보
	 * @return List 결과 정보
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getLastPgmInfolist(ProgramInfoDO	programInfoDO) throws Exception
	{

		return externalDAO.selectLastPgmInfolist(programInfoDO);

	}

	/**
	 * 관련영상 정보 조회
	 * @param programInfoDO 정보조회를 위해 필요한 beans
	 * @return List 결과정보
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSearchRelationInfoList(ProgramInfoDO programInfoDO) throws Exception
	{

		return externalDAO.selectSearchRelationInfolist(programInfoDO);

	}

	/**
	 * 관련영상 정보를 등록한다.
	 * @param parent_master_id 부모 마스터 정보
	 * @param child_master_id 관련영상 마스터 정보
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertRelationMaster(long parent_master_id,long child_master_id) throws Exception
	{

		return externalDAO.insertRelationMaster(parent_master_id,child_master_id);

	}

	/**
	 * 관련영상 정보를 삭제한다.
	 * @param parent_master_id  부모 마스터 정보
	 * @param child_master_id 관련영상 마스터 정보
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteRelationMaster(long parent_master_id,long child_master_id) throws Exception
	{

		return externalDAO.deleteRelationMaster(parent_master_id,child_master_id);

	}

	/**
	 * 주석정보를 저장한다.
	 * @param masterId 마스터 ID	
	 * @param annotInfoDO 주석 정보 리스트	
	 * @throws Exception 
	 */
	public List insertAnnotinfo(long masterId, List annotInfoList) throws Exception
	{

		return externalDAO.insertAnnotinfo(masterId, annotInfoList);

	}

	/**
	 * 첨부 파일 정보를 저장한다.
	 * @param attachFileInfo 첨부파일  정보 리스트	
	 * @return attachFileInfo 첨부파일 정보 리스트	
	 * @throws Exception 
	 */
	public List insertAttachFile(List attachFileInfo) throws Exception
	{

		return externalDAO.insertAttachFile(attachFileInfo);

	}

	/**
	 * 코너 정보를 저장한다.
	 * @param masterId 마스터 ID	
	 * @param cornerInfoDO 코너 정보 리스트
	 * @return CornerInfoDO object 리스트
	 * @throws Exception 
	 */
	public List insertCornerinfo(long masterId, List cornerInfoDOList) throws Exception
	{

		return externalDAO.insertCornerinfoByBatch(masterId, cornerInfoDOList);

	}


	/**
	 * 테이프 정보를 저장한다.
	 * @param masterId 마스터 ID	
	 * @param IDhead 헤더값
	 * @param userId 사용자id
	 * @param year 년
	 * @return annotInfoDO 주석 정보 리스트	
	 * @throws Exception 
	 */
	public String insertTapeinfo(long masterId, String IDhead, String userId, String year) throws Exception
	{

		return externalDAO.insertTapeinfo(masterId, IDhead, userId, year);

	}


	/**
	 * 콘텐트 멥 정보를 저장한다.
	 * @param masterId 마스터 ID	
	 * @param contentMappInfoDO 주석 정보	list
	 * @return count	입력된 갯수 count
	 * @throws Exception 
	 */
	public int insertContentsMappinfo(long masterId, List contentMappInfoDOList ) throws Exception
	{

		int result = externalDAO.insertContentsMappinfo(masterId, contentMappInfoDOList);

		//프로시져 호출 함수
		String master = String.valueOf(masterId);
		int masteri = Integer.parseInt(master);
		systemManageDAO.insertCornerInfoForProceduer(masteri);
		return result;

	}

	/**
	 * 카트의 내용을 저장한다.
	 * @param cartContDO 카트내용정보
	 * @return CartContDO
	 * @throws Exception 
	 */
	public CartContDO insertCartContInfo(CartContDO cartContDO) throws Exception
	{

		return externalDAO.insertCartContInfo(cartContDO);

	}

	/**
	 * 카트의 내용을 저장한다.
	 * @param cartContDO 카트내용정보
	 * @return CartContDO
	 * @throws Exception 
	 */
	public CartContDO insertStCartContInfo(CartContDO cartContDO) throws Exception
	{

		return externalDAO.insertStCartContInfo(cartContDO);

	}

	/**
	 * 스토리지에 있는 존재하는 클립에 대해서 리스토어 요청시 DAS-TM에 전달 요청하게 된다.
	 * 풀다운로드 한건에 대해서 요청하게 된다.
	 * @param downCartDO_cartContDO
	 * @return CartContDO
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public CartContDO insertStCartContInfo(DownCartDO downCartDO,CartContDO cartContDO) throws Exception
	{

		return externalDAO.insertStCartContInfo(downCartDO,cartContDO);

	}

	/**
	 * 대본 내용을 저장한다.
	 * @param scenarioDO 대본내용정보
	 * @return updatecout
	 * @throws Exception 
	 */
	public int insertScenario(ScenarioDO scenarioDO) throws Exception
	{

		return externalDAO.insertScenario(scenarioDO);

	}

	/**
	 * 대본 내용을 삭제한다.
	 * @param master_id 마스터ID
	 * @return updatecount
	 * @throws Exception 
	 */
	public int deleteScenario(long master_id) throws Exception
	{

		return externalDAO.deleteScenario(master_id);

	}



	/**
	 * 다운카트의 상태를 갱신한다.
	 * @param cartNo 다운 카트번호
	 * @param cartState 다운카트 상태
	 * @param title 제목
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateDownCartState(long cartNo, String cartState, String title) throws Exception
	{

		return externalDAO.updateDownCartState(cartNo, cartState, title);

	}

	/**
	 * 다운로드 요청 DAS2.0 
	 * @param downCartDO 다운로드 요청 정보가 담겨있는 beans
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateDownCart(DownCartDO downCartDO) throws Exception
	{

		return externalDAO.updateDownCart(downCartDO);

	}

	/**
	 *  스토리지에 있는 클립에 대한 전송 요청을 받을 수 있는 풀다운로드 요청 DAS2.0 
	 * @param downCartDO  다운로드  전송요청을 위한 beans
	 * @param cartContDO  다운로드  전송요청을 위한 beans
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateStDownCart(DownCartDO downCartDO,CartContDO cartContDO) throws Exception
	{
		return externalDAO.updateStDownCart(downCartDO,cartContDO);

	}

	/**
	 *  스토리지에 있는 클립에 대한 전송 요청을 받을 수 있는 풀다운로드 요청
	 * @param cartContDOs 풀다운로드 요청을 하기위한 beans
	 * @return Update count
	 * @throws Exception 
	 */
	public int[] updateStCartContInfo(List cartContDOs) throws Exception
	{
		return externalDAO.updateCartContInfo(cartContDOs);
	}


	/**
	 * 카트 아이템에 대한 정보 수정
	 * @param cartContDO 카트에 대한 내용 정리
	 * @return Update count
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int[] updateCartContInfo(List cartContDOs) throws Exception
	{

		return externalDAO.updateCartContInfo(cartContDOs);

	}


	/**
	 * 마스터 테이블 상태코드를 갱신한다.
	 * @param masterID 		마스터 ID
	 * @param secArchId		2차 아카이브 ID
	 * @param secArchNm		2차 아카이브 이름                                       
	 * @return        Update count
	 * @throws Exception 
	 */
	public int updateDatastatCd(long masterID, String secArchId, String secArchNm) throws Exception
	{

		return externalDAO.updateDatastatCd(masterID, secArchId, secArchNm);

	}

	/**
	 * 콘텐츠 테이블 화질코드를 갱신한다. 
	 * @param ctID 컨텐츠 id
	 * @param vd_qlty 화질 코드
	 * @param asp_rto_cd 화면비 코드
	 * @return 성공 1 실패 0 
	 * @throws Exception 
	 */
	public int updateVd_qlty(int ctID, String vd_qlty,String asp_rto_cd) throws Exception
	{

		return externalDAO.updateVd_qlty(ctID, vd_qlty,asp_rto_cd,"");


	}

	/**
	 * 인제스트 상태 정보를 갱신한다
	 * @param itemId 테이프 아이템 ID
	 * @param ingestStatus 인제스트 상태
	 * @return Update count
	 * @throws Exception
	 */
	public int updateSDIngestStatus(String itemId, String ingestStatus) throws Exception
	{

		return externalDAO.updateSDIngestStatus(itemId, ingestStatus);


	}

	/**
	 * 메타데이타 마스터 테이블 사용 정보이력을 갱신한다
	 * @param masterId    마스터 ID
	 * @param userId    사용자 ID
	 * @return update Count
	 * @throws Exception 
	 */
	public int updateModUserid(long masterId, String userId) throws Exception
	{

		return externalDAO.updateModUserid(masterId, userId);

	}

	/**
	 * 콘텐트 멥 테이블의 시작/종료 duraiton을 갱신한다.
	 * @param contentMappInfoDO  content mapp object
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateCornerDuration(ContentMappInfoDO contentMappInfoDO) throws Exception
	{

		return externalDAO.updateCornerDuration(contentMappInfoDO);

	}

	/**
	 * 메타 데이타 마스터 테이블 상태 정보를 갱신한다.	
	 * @param masterID 마스터 ID
	 * @param statCd 상태 코드
	 * @param modrid 수정자id
	 * @param moddt 수정일
	 * @param lock_stat_cd lock 상태 코드
	 * @param error_stat_cd 에러 상태 코드
	 * @return updateCount 		
	 * @throws Exception 
	 */
	public int updateMetadataStatusCd(long masterId, String statCd, String modrid, 
			String moddt, String lock_stat_cd, String error_stat_cd) throws Exception
			{

		return externalDAO.updateMetadataStatusCd(masterId, statCd, modrid, moddt, lock_stat_cd, error_stat_cd);


			}	


	/**
	 * 메타데이타 마스터의 클립 정보를 갱신한다.
	 * @param RpimgKfrmSeq  대표화면 키프레임 일련 번호
	 * @param rpimgCtId  대표화면 콘텐트 ID
	 * @param masterId  마스터 ID
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateClipRepinfo(int rpimgKfrmSeq, long rpimgCtId, long masterId) throws Exception
	{

		return externalDAO.updateClipRepinfo(rpimgKfrmSeq, rpimgCtId,masterId);

	}	

	/**
	 * 코너 테이블의 대표화면 정보를 갱신한다.
	 * @param RpimgKfrmSeq  대표화면 키프레임 일련 번호
	 * @param rpimgCtId  대표화면 콘텐트 ID
	 * @param cnId  코너 ID
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateCornerRepinfo(int rpimgKfrmSeq, long rpimgCtId, long cnId) throws Exception
	{

		return externalDAO.updateClipRepinfo(rpimgKfrmSeq, rpimgCtId,cnId);

	}	


	/**
	 * 메타타이타 정보를 갱신한다.
	 * @param metadataMstInfoDO  메타데이타 정보를 포함하는 object
	 * @return Update count
	 * @throws Exception 
	 */
	public int updateMetadat(MetadataMstInfoDO metadataMstInfoDO) throws Exception
	{

		int result =externalDAO.updateMetadat(metadataMstInfoDO);

		return result;

	}



	/**
	 * 입력받은 카트번호에 해당하는 다운로드 카트 정보와 카트 내용정보를 삭제 한다.
	 * @param cartNo 삭제할 카트번호
	 * @throws Exception 
	 */
	public void deleteAllCartInfo(long cartNo) throws Exception
	{

		externalDAO.deleteAllCartInfo(cartNo);

	}

	/**
	 * Input으로 넘어온 카트 내용 정보를 삭제한다.
	 * @param cartNo 카트번호
	 * @param seq 카트내순번
	 * @throws Exception 
	 */
	public void deleteCartInfoList(long cartNo, int seq) throws Exception
	{

		externalDAO.deleteCartInfo(cartNo, seq);

	}

	/**
	 * Photo 파일 항목을 삭제한다.
	 * @param fileName 파일 이름
	 * @param regId 오늘로 부터 며칠전 파일
	 * @param seq 순번
	 * @return 삭제 여부
	 * @throws Exception 
	 */
	public String deletePhotoFiles(List photoInfoList) throws Exception
	{

		return externalDAO.deletePhotoFiles(photoInfoList);

	}

	/**
	 * Photo 파일 항목을 삭제한다..
	 * @param photoInfo       삭제할 사진 정보                                                                                                            	
	 * @throws Throwable 
	 */
	public String deletePhoto(PhotoInfoDO photoInfoDO) throws Throwable
	{

		String result= externalDAO.deletePhoto(photoInfoDO);

		String[] delList= photoInfoDO.getPhotRegIdS().split(",");
		//해당 사진id에 대해서 마지막 삭제 건이라면. 최종 삭제처리를 한다.
		for(int i=0; i<delList.length;i++){
			if(externalDAO.selectDelPhotCount(Long.parseLong(delList[i]))){
				externalDAO.deletePhotoInfo(Long.parseLong(delList[i]));
			}
		}
		return result;

	}


	/**
	 * 조회된 콘텐트 mp4 폴더, mp2 파일 리스트를 가져온다.
	 * @param days 오늘로 부터 며칠전 파일
	 * @return String 	파일 리스트
	 * @throws Exception 
	 */
	public String deleteFiles(int days) throws Exception
	{

		return externalDAO.deleteContentFiles(days);

	}


	/**
	 * 2차 아카이브 mp4에 저장된 키프레임 파일을 삭제한다
	 * @param krfmFileList 파일 리스트
	 * @return String 	삭제 로그
	 * @throws Exception 
	 */
	public String deleteKfrmFiles(String krfmFileList) throws Exception
	{

		return externalDAO.deleteKfrmFiles(krfmFileList);			

	}


	/**
	 * 스토리지 IP를 조회한다.
	 * 
	 * @return List 조회한 스토리지 IP List
	 * @throws Exception 
	 */
	public List getStorageIP() throws Exception
	{

		return externalDAO.selectStorageIP();

	}

	/**
	 * 수정자 ID, 상태 코드 조회를 한다.
	 * @param masterId 마스타ID
	 * @return MetadataMstInfoDO
	 * @throws Exception 
	 */
	public MetadataMstInfoDO getModDatastatcd(long masterId) throws Exception
	{

		return externalDAO.selectModDatastatcd(masterId);

	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * @param clfCd 구분 코드
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public List getCommonInfoList(String clfCd) throws Exception
	{

		return externalDAO.selectCommonInfoList(clfCd);

	}

	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getProgramInfoList(String pgmNm) throws Exception
	{

		return externalDAO.selectProgramInfo(pgmNm);

	}

	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getPgmInfoFromName(ProgramInfoDO programInfoDO) throws Exception
	{

		return externalDAO.selectPgmInfoFromName(programInfoDO);


	}


	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다 xml로 받음 beans로 파싱
	 * @param pgmNm    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getPgmInfoFromName3(String pgmNm) throws Exception
	{

		return externalDAO.selectPgmInfoFromName(pgmNm);

	}



	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getPgmInfoFromName2(String pgmNm) throws Exception
	{

		return externalDAO.selectPgmInfoFromName2(pgmNm);

	}





	/**
	 * 대본정보를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws Exception 
	 */
	public List getScenario(long masterId) throws Exception
	{

		return externalDAO.selectScenario(masterId);


	}
	/**
	 * 대본정보를 조회한다.
	 * @param masterId
	 * @return list 대본 정보 조회
	 * @throws Exception 
	 */
	public ScenarioDO getScenario2(ScenarioDO scenarioDO) throws Exception
	{

		return externalDAO.selectScenario2(scenarioDO);

	}
	/**
	 * 마스터 ID를 가지는 프로그램 정보를 가져온다
	 * @param episNo    에피소드 #
	 * @param pgmId    프로그램 ID
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getPgmInfoFromMasterid(int episNo, long pgmId) throws Exception
	{

		return externalDAO.selectPgmInfoFromMasterid(episNo, pgmId);

	}

	/**
	 * 프로그램이름을 포함하는 프로그램 정보을 모두 가져온다
	 * @param pgmNm    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getLastPgmInfolist(String pgmNm) throws Exception
	{

		return externalDAO.selectLastPgmInfolist(pgmNm);

	}


	/**
	 * 프로그램ID에 해당하는 프로그램을 모두 가져온다
	 * @param pgmId    프로그램 이름 검색어
	 * @return List		ProgramInfoDO 리스트
	 * @throws Exception 
	 */
	public List getLastPgmInfolistByPgmId(long pgmId) throws Exception
	{

		return externalDAO.selectLastPgmInfolistByPgmId(pgmId);


	}
	/**
	 * 프로그램 ID에 대항하는 프로그램 정보를 가져온다
	 * @param pgmID 프로그램 ID
	 * @param brd_dd 방송일
	 * @return ProgramInfoDO xml string 
	 * @throws Exception 
	 */
	public List getLastPgmInfolistByPgmId2(long pgmId,String brd_dd) throws Exception
	{

		return externalDAO.selectLastPgmInfolistByPgmId2(pgmId,brd_dd);

	}


	/**
	 * 프로그램에 포함된 콘텐트 정보를 읽어온다
	 * @param masterId    마스터 ID
	 * @return List 	PgmContensInfoDO 리스트
	 * @throws Exception 
	 */
	public List getPgmContentsInfoList(long masterId) throws Exception
	{

		return externalDAO.selectPgmContentsInfo(masterId);

	}

	/**
	 * 코너정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return List CornerInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getCornerInfoList(long masterId, String keyWord) throws Exception
	{

		return externalDAO.selectCornerInfoList(masterId, keyWord);

	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public List getFLIngestCommonCodeList() throws Exception
	{

		return externalDAO.selectFLIngestCommonCodeList();

	}

	/**
	 * 파일 인제스트 감시 정보를 가져온다
	 * @return List 조회한 인제스트 정보 List
	 * @throws Exception 
	 */
	public List getFlIngestLastCommandList() throws Exception
	{

		return externalDAO.selectFlIngestLastCommandList();

	}

	/**
	 * 장비 정보를 조회한다.
	 * @param clfCd DAS 장비 작업 구분 코드
	 * @return List 조회한 장비 정보 List
	 * @throws Exception 
	 */
	public List getIngestServerList(String clfCd) throws Exception
	{

		return externalDAO.selectIngestServerList(clfCd);

	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public List getSDIngestCommonCodeList() throws Exception
	{

		return externalDAO.selectSDIngestCommonCodeList();

	}

	/**
	 * 테이프 정보를  조회한다.
	 * @param 	tapeId	테이프 ID 
	 * @return List    테이프 정보 List
	 * @throws Exception 
	 */
	public List getSDIngestRefreshTapeInfo(String tapeId) throws Exception
	{

		return externalDAO.selectSDIngestRefreshTapeInfo(tapeId);

	}

	/**
	 * 인제스트 장비 정보를 조회한다.
	 * @param eqClfCd 장비 구분 코드
	 * @return EquipmentMonitoringDO 장비 정보 object
	 * @throws Exception 
	 */
	public List getSDIngestServerList(String eqClfCd) throws Exception
	{

		return externalDAO.selectSDIngestServerList(eqClfCd);

	}

	/**
	 * 테이프 상태를 코드를 조회한다.
	 * @param 	tapeId	테이프 ID 
	 * @return String    테이프 아이템 상태 코드
	 * @throws Exception 
	 */
	public String getSDIngestStatusInfo(String tapeItemId) throws Exception
	{

		return externalDAO.selectSDIngestStatusInfo(tapeItemId);

	}

	/**
	 * 테이프 아이템 정보를 조회한다.
	 * @param 	reqNum 
	 * @param 	pgmNm   		프로그램 이름
	 * @param	IngestStatus	인제스트 상태
	 * @return List 조회한 테입 정보  List
	 * @throws Exception
	 */
	public List getSDTapeInfoList(String reqNum, String pgmNm, String IngestStatus, boolean OnAirDateSearch,String OnAirDateStart, String OnAirDateEnd) throws Exception
	{

		return externalDAO.selectSDTapeInfoList(reqNum,pgmNm,IngestStatus,OnAirDateSearch,OnAirDateStart,OnAirDateEnd);

	}

	/**
	 * 메타데이타 마스터 정보를 조회한다.
	 * @param case1 조회 조건
	 * @param case2 조회 조건
	 * @param start 시작일
	 * @param end 종료일
	 * @return MetaInfoDO를 포함하고 있는 DataObject
	 * @throws Exception
	 */
	public List getNewMetadatInfoList(WorkStatusConditionDO conditionDO) throws Exception {
		return externalDAO.selectNewMetadatInfoList(conditionDO);
	}


	@Deprecated
	public List getMetadatInfoList(WorkStatusConditionDO conditionDO) throws Exception
	{

		return externalDAO.selectMetadatInfoList(conditionDO);

	}

	public String getMetadatInfoListForString(WorkStatusConditionDO conditionDO) throws Exception
	{

		return externalDAO.selectMetadatInfoListForString(conditionDO);

	}
	/**
	 * 마스터 ID에 대한 모든 메타데이타 마스터 정보를 조회한다.
	 * @param masterId  마스터 ID
	 * @return MetadataMstInfoDO를 포함하고 있는 DataObject
	 * @throws Exception
	 */
	public List getMetadataInfo(long masterId) throws Exception
	{

		return externalDAO.selectMetadataInfo(masterId);

	}


	/**
	 * 오류 내역을 조회한다.
	 * @param masterId 마스터 ID
	 * @return ErrorRegisterDO 오류정보를 포함하고 있는 DataObject 리스트
	 */
	public List getErrorInfoList(long masterId) throws Exception
	{

		return externalDAO.selectErrorInfoList(masterId);

	}

	/**
	 * 카트 콘텐트 정보를 조회한다.
	 * @param 	cartNo	카트 번호 
	 * @return List    카트 콘텐트 정보 List
	 * @throws Exception
	 */
	public List getTapeOutIngestCartItemInfo(long cartNo) throws Exception
	{

		return externalDAO.selectTapeOutIngestCartItemInfo(cartNo);

	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception
	 */
	public List getTapeOutIngestCommonCodeList() throws Exception
	{

		return externalDAO.selectTapeOutIngestCommonCodeList();

	}

	/**
	 * 다운 카트 정보를 조회한다.
	 * @param 	reqUserId	요청자 ID
	 * @param 	resolution	해상도  
	 * @param 	reqDtChk
	 * @param 	String		요청일자
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception
	 */
	public List getTapeOutIngestDownCartInfoList(String reqUserId, int resolution, boolean reqDtChk, String reqDt) throws Exception
	{

		return externalDAO.selectTapeOutIngestDownCartInfoList(reqUserId,resolution,reqDtChk,reqDt);

	}



	/**
	 * 키프레임 정보를 조회한다.
	 * @param ctId 콘텐츠ID
	 * @param fromSeq 키프레임일련번호(from)
	 * @param toSeq 키프레임일련번호(to)
	 * @return List KeyFrameInfoDO 를 포함하고 있는 List
	 * @throws Exception
	 */
	public List getKeyFrameInfoInfoList(long ctId, int fromSeq, int toSeq) throws Exception
	{

		return externalDAO.selectKeyFrameInfoInfoList(ctId, fromSeq, toSeq);

	}

	/**
	 * 주석정보를 조회한다
	 * @param masterId 마스타ID
	 * @return List AnnotInfoDO를 포함하고 있는 List
	 * @throws Exception
	 */
	public List getAnnotInfoInfoList(long masterId) throws Exception
	{

		return externalDAO.selectAnnotInfoInfoList(masterId);

	}

	/**
	 * 영상정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return List ReflectionInfoDO를 포함하고 있는 List
	 * @throws Exception
	 */
	public List getReflectionInfoList(long masterId) throws Exception
	{

		return externalDAO.selectReflectionInfoList(masterId);


	}

	/**
	 * 첨부파일을 조회한다.
	 * @param mothrId 모자료 ID
	 * @return List AttachFileInfoDO를 포함하고 있는 List
	 * @throws Exception
	 */
	public List getAttachFileInfoList(long mothrId) throws Exception
	{

		return externalDAO.selectAttachedFileInfoList(mothrId);

	}

	/**
	 * 컨텐트 미리보기 정보를 조회한다.
	 * @param masterId	마스터 ID 
	 * @return List ContentsPrevInfoDO를 포함하고 있는 List
	 * @throws Exception
	 */
	public List getContentPreInfoList(long masterId) throws Exception
	{

		return externalDAO.selectConentPreInfoList(masterId);

	}

	/**
	 * 입력받은 카트번호에 해당하는 카트내용 정보를 조회한다.
	 * @param cartNo 카트번호
	 * @return List 카트내용정보를 담고있는 리스트
	 * @throws Exception
	 */
	public List getCartContList(long cartNo) throws Exception
	{

		return externalDAO.selectCartContList(cartNo);

	}

	/**
	 * 내용 및 제작정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return ContentsInfoDO 내용 및 제작정보를 포함하고 있는 DataObject
	 * @throws Exception
	 */
	public ContentsInfoDO getContentsInfo(long masterId) throws Exception
	{

		return externalDAO.selectContentsInfo(masterId);

	}

	/**
	 * 편성/심의 및 저작권/tape정보 조회
	 * @param masterId 마스타ID
	 * @return TapeInfoDO
	 * @throws Throwable 
	 */
	public TapeInfoDO getTapeInfo(long masterId) throws Throwable
	{

		return externalDAO.selectTapeInfo(masterId);

	}

	/**
	 * 미디어 정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return MediaInfoDO
	 * @throws Exception 
	 */
	public MediaInfoDO getMediaInfo(long masterId) throws Exception
	{

		return externalDAO.selectMediaInfo(masterId);

	}

	/**
	 * 마스터 테이블 수정이력 정보를 조회한다.
	 * @param masterId 마스터 ID
	 * @return List
	 * @throws Exception 
	 */
	public List getModeUserInfoList(long masterId) throws Exception
	{

		return externalDAO.selectModeUserInfoList(masterId);

	}	

	/**
	 * 프로그램 정보를 조회한다.(das 1.0 소스 현재 사용하지 않음)
	 * @param masterId 마스타ID
	 * @return VideoPageInfoDO
	 * @throws Exception 
	 */
	public VideoPageInfoDO getVideoPageInfo(long masterId) throws Exception
	{

		return externalDAO.selectVideoPageInfo(masterId);

	}

	/**
	 * 비디오 페이지 메타 정보를 조회한다.(das 1.0 소스 현재 사용하지 않음)
	 * @param masterId 마스타ID
	 * @return VideoPageMetaInfoDO
	 * @throws Exception 
	 */
	public VideoPageMetaInfoDO getVideoPageMetaInfo(long masterId) throws Exception
	{

		return externalDAO.selectVideoPageMetaInfo(masterId);

	}

	/**
	 * 프로그램 콘텐트 정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return VideoPageContentInfoDO list
	 * @throws Exception 
	 */
	public List getVideoPageContentsInfoList(long masterId) throws Exception
	{

		return externalDAO.selectVideoPageContentInfoList(masterId);

	}

	/**
	 * 관리 정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return ManagementInfoDO
	 * @throws Exception 
	 */
	public ManagementInfoDO getManagementInfo(long masterId) throws Exception
	{

		return externalDAO.selectManagementInfo(masterId);

	}

	/**
	 * 사진정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return List PhotoInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getPhotoInfoList(long masterId) throws Exception
	{

		return externalDAO.selectPhotoInfoList(masterId);

	}

	/**
	 * 코너 대표화면 정보를 조회한다.
	 * @param ctId 콘텐츠아이디
	 * @param cnId 코너아이디
	 * @return KeyFrameImgDO 대표화면 정보
	 * @throws Exception 
	 */
	public KeyFrameImgDO getCornerHeaderImgInfo(long ctId, long cnId) throws Exception
	{

		return externalDAO.selectCornerHeaderImgInfo(ctId, cnId);

	}

	/**
	 * 클립 대표화면 정보를 조회한다.
	 * @param masterId 마스타아이디
	 * @return KeyFrameImgDO 대표화면 정보
	 * @throws Exception 
	 */
	public KeyFrameImgDO getClipHeaderImgInfo(long masterId) throws Exception
	{

		return externalDAO.selectClipHeaderImgInfo(masterId);

	}

	/**
	 * DAS 장비의 로그 기록 주기를 조회한다.
	 * @param dasEqId DAS장비 ID
	 * @param dasEqPsCd DAS장비프로세스코드
	 * @return int 로그기록주기
	 * @throws Exception 
	 */
	public int getLogRcdPeriod(int dasEqId, String dasEqPsCd) throws Exception
	{

		return externalDAO.selectLogRcdPeriod(dasEqId, dasEqPsCd);

	}
	/**
	 * 로그인 서비스 (das 1.0 로그인 서비스 현재 사용하지 않는 로직)
	 * @param userId                                                                                                                                                                                
	 * @param password                                                                                                                                                                               
	 * @return                                                                                                                                                       
	 * @throws Exception 
	 */
	public String loginService(String userId, String passwd) throws Exception
	{
		String str = null;

		//정직원의 경우 SSO 로그인 처리를 하고 정직원이 아닌 경우 자체 DAS DB의 로그인처리를 한다.
		if(userId.toUpperCase().startsWith("S"))
		{
			Object locator;
		} else {
			str = userInfoDAO.selectNonEmployeeInfoService(userId, passwd);

			//비밀번호 실패횟수를 0으로 클리어 시킨다.
			userInfoDAO.updateLoginSucess(userId);
		}

		//사용자 로그를 남기남긴다.
		userInfoDAO.insertIdLog(userId);

		return str;

	}


	/**
	 * CTI의 정보중 미디어 관련 정보를 가져온다.
	 * @param CTI_ID CTI의 ID
	 * @return 결과 XML
	 * @throws Exception
	 */
	public String getPlayMediaInfo(long CTI_ID) throws Exception 
	{

		return externalDAO.getPlayMediaInfo(CTI_ID);

	}


	/**
	 * MasterID에 연결된 영상관련 정보들을 가져온다.
	 * @param MasterID MasterID
	 * @return 결과 XML
	 * @throws Exception
	 */
	public String getPlayContentInfo(long MasterID) throws Exception 
	{

		return externalDAO.getPlayContentInfo(MasterID);

	}



	/**
	 * 메타 목록을 조회한다.
	 * @param searchColumn, searchKey  검색할 컬럼, 검색어
	 * @return ArrayList 목록리스트
	 * @throws Exception 
	 */
	public ArrayList getMetaList(String searchColumn, String searchKey) throws Exception
	{

		return externalDAO.getMetaList(searchColumn, searchKey);

	}

	/**
	 * 맵핑 테이블 목록을 조회한다.
	 * @param master_id
	 * @return ArrayList 목록리스트
	 * @throws Exception 
	 */
	public ArrayList getMappingList(String master_id) throws Exception
	{

		return externalDAO.getMappingList(master_id);

	}

	/**
	 * 콘텐트 멥 정보를 저장한다.
	 * @param beans 저장시킬 목록	
	 * @param commonDO 사용자 정보
	 * @return count	삭제 count
	 * @throws Exception 
	 */
	public int insertMappinfo(String master_id, String cn_id, String ct_id, ArrayList beans, DASCommonDO commonDO) throws Exception
	{

		return externalDAO.insertMappinfo(master_id, cn_id, ct_id, beans, commonDO);

	}

	/**
	 * 첨부파일을 삭제한다.
	 * @param attachFilename 삭제할 파일 이름	
	 * @param file_type 파일 타입
	 * @return clf_cd	구분
	 * @throws Exception
	 */
	public int deleteAttachFile(String attachFilename, String file_type, String clf_cd) throws Exception
	{	

		return externalDAO.deleteAttachFile(attachFilename, file_type, clf_cd);			

	}

	/**
	 * 첨부파일을 삭제한다.
	 * @param attachFilename 삭제할 파일 이름	
	 * @param file_type 파일 타입
	 * @return clf_cd	구분
	 * @throws Exception
	 */
	public int deleteBoardAttachFile(String attachFilename, String fl_path, int board) throws Exception
	{	

		return externalDAO.deleteBoardAttachFile(attachFilename,fl_path,  board);			

	}
	/**
	 * 사진을 다운로드했다는 기록을 남긴다.(통계를 위해서) 
	 * @param Phot_ID			사진 ID
	 * @param REQ_ID			요청자 ID
	 * @param PGM_ID			프로그램 ID
	 * @param check				1 : 다운로드, 2 : 삭제
	 * @throws Exception 
	 */
	public int InsertPhotoDownloadInfo(long Phot_ID, String REQ_ID, long PGM_ID) throws Exception
	{

		if(PGM_ID == 0){
			PGM_ID = externalDAO.selectPhotIdForPgmId(Phot_ID);
		}
		return externalDAO.InsertPhotoDownloadInfo(Phot_ID, REQ_ID, PGM_ID);

	}

	/**
	 * 사진을 다운로드했다는 기록을 남긴다.(통계를 위해서) 
	 * @param PhotoInfoDO 다운로드 이력을 위한  beans
	 */
	public int InsertPhotoDownInfo(PhotDownDO PhotoInfoDO) throws Exception
	{

		return externalDAO.InsertPhotoDownInfo(PhotoInfoDO);

	}

	/**
	 * 오디오 관련 정보를 업데이트한다.
	 * @param Master_ID 	마스터 아이디
	 * @param aud_type_cd 	 오디오 타입
	 * @param record_type_cd	미디어 녹화 정보
	 * @param me_cd   ME분리 미분리
	 * @param color_cd       컬러 여부
	 */
	public int UpdateContentMediaInfo(long Master_ID, String aud_type_cd, 
			String record_type_cd, String me_cd, String color_cd)  throws Exception
			{

		return externalDAO.UpdateContentMediaInfo(Master_ID, aud_type_cd, record_type_cd, me_cd, color_cd);

			}

	/**
	 * XML로 각 MasterID별 상태를 받아서 업데이트한다.	 *
	 * @param strXML
	 * @return updaetcount
	 * @throws RemoteException
	 */
	public int UpdateDatacdWithMasterid_XML(String strXML) throws Exception
	{
		return externalDAO.UpdateDatacdWithMasterid_XML(strXML);

	}


	/**
	 * XML로 테이프 정보를 받아서 ERP에 tape정보, tape item 정보를 추가한다.	 *
	 * @param strXML
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String insertERPTapeInfo(Das das) throws Exception
	{

		return  externalDAO.insertERPTapeInfo(das);

	}

	/**
	 * ERP 정보를 업데이트한다.
	 * @param xml
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String updateERPTapeInfo(String xml) throws Exception
	{
		return externalDAO.updateERPTapeInfo(xml);

	}

	/**
	 * 아카이브 확인요청
	 * @param date 아카이브 날짜
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	@Deprecated
	public String ArchiveReq(String date) throws Exception {
		Date currentdate = new java.util.Date(); 
		java.text.SimpleDateFormat timestring = new java.text.SimpleDateFormat("yyyyMMdd"); 
		String today = timestring.format(currentdate); 

		// 파람값으로 날짜가 들어가 있다면 
		if (!"".equals(date)) {
			today = date.replace("-", "");
		}

		FileWriter Cti_IdWriter = null;
		File Cti_IdList = new File("Cti_Id" + today);

		String str = null;
		int count = 0;
		StringBuffer query = new StringBuffer(); 

		query.append("\n SELECT distinct C.CTI_ID FROM DAS.METADAT_MST_TBL A ");
		query.append("\n     INNER JOIN DAS.CONTENTS_MAPP_TBL B ON B.MASTER_ID = A.MASTER_ID ");
		query.append("\n     INNER JOIN DAS.CONTENTS_INST_TBL C ON C.CT_ID = B.CT_ID ");
		query.append("\n WHERE C.CTI_FMT LIKE '1%' AND SUBSTR(A.ING_REG_DD, 1, 8) = '" + today + "' ");

		// log

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		List list = new ArrayList();

		try {
			Cti_IdWriter = new FileWriter(Cti_IdList);
			Cti_IdWriter.write(today + "\n" + query.toString() + "\n");

			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query.toString());
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(rs.getString("CTI_ID"));
			}

			// log
			Cti_IdWriter.write(today + "  " + list.size() + "\n");


			for (int i=0; i<list.size(); i++) {
				String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<das>"+
						"<info>"+		
						"<das_eq_id>4</das_eq_id>"+
						"<das_eq_ps_cd>005</das_eq_ps_cd>"+
						"<cti_id>" + list.get(i).toString() + "</cti_id>"+
						"<priority>3</priority>"+
						"<sgl_group_nm>m2_%25</sgl_group_nm>"+
						"<job_id>008</job_id>"+
						"<som>0</som>"+
						"<eom>0</eom>"+
						"<file_path></file_path>"+ 
						"<req_id>requester</req_id>"+
						"<regrid>register</regrid>"+
						"</info>"+
						"<db_table>"+ 
						"<contents_mapp_tbl>"+
						"<ct_id>0</ct_id>"+
						"<master_id>0</master_id>"+
						"<pgm_id></pgm_id>"+
						"</contents_mapp_tbl>"+
						"</db_table>"+
						"</das>";

				// log
				Cti_IdWriter.write(today + " xml = " + xml + "\n");

				logger.debug("xml="+xml);
				boolean _result = false;
				logger.debug("str="+str);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				Cti_IdWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try 
			{
				if (rs != null) 
				{
					rs.close();
				}
			}
			catch (Exception e) {}
			try 
			{
				if (stmt != null) 
				{
					stmt.close();
				}
			} 
			catch (Exception e) {}
			try 
			{
				if (con != null) {
					con.close();
				}
			}
			catch (Exception e) {}
		}

		return String.valueOf(list.size());
	}




	/**
	 *  사용자 권한을 가져온다.
	 * @param UserID 사용자 id
	 * @return
	 * @throws RemoteException
	 */
	public String getUserAuthCD(String UserID) throws Exception
	{

		return externalDAO.getUserAuthCD(UserID);

	}
	/**
	 * 다운로드카트 리스트의 정보조회
	 * @param ReqUsrID 등록id
	 * @param DateStart 시작일
	 * @param DateEnd  종료일
	 * @param down_nm 다운로드제목
	 * @return
	 * @throws RemoteException
	 */
	public String getDownCartList(String ReqUsrID, String DateStart, String DateEnd, String down_nm) throws Exception
	{

		return externalDAO.getDownCartList(ReqUsrID, DateStart, DateEnd, down_nm);

	}

	/**
	 * 검색영상재생시 기초정보 조회
	 * @param masterId
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getBasicPageInfo(long masterId) throws Exception
	{

		return externalDAO.getBasicPageInfo(masterId);

	}

	/**
	 * WMV 재생성 요청 (콘텐츠 인스턴스 아이디)
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String recreateWMV(long cti_id) throws Exception
	{

		return externalDAO.recreateWMV(cti_id);

	}

	/**
	 * 김건삭 실장님 요청 사항 20090903 dekim
	 * WMV 재생성 요청한다.
	 * @param cti_id
	 * @param user_nm
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV(TcBeanDO tcbean,String user_nm) throws Exception {

		return externalDAO.recreateNewWMV(tcbean, user_nm,dasHandler.getProperty("TC_DIR_INTERFACE"));

	}

	/**
	 * WMV 및 KFRM 재신청 요청한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자 명
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV_KFRM(TcBeanDO tcbean,String user_nm) throws Exception {

		return externalDAO.recreateNewWMV_KFRM(tcbean, user_nm, dasHandler.getProperty("TC_DIR_INTERFACE"));

	}


	/**
	 * 키프레인 재신청 요청 한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자명
	 * @return
	 * @throws Exception 
	 */
	public String recreateKFRM(TcBeanDO tcbean,String user_nm) throws Exception {

		return externalDAO.recreateNewKFRM(tcbean,user_nm,dasHandler.getProperty("TC_DIR_INTERFACE"));

	}

	/**
	 * 키프레임 생성(클라이언트 신청시)
	 * @param ct_id 콘텐츠id 
	 * @param user_nm 사용자 이름
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String recreateKFRM(long ct_id,String user_nm) throws Exception
	{

		return externalDAO.recreateKFRM(ct_id,user_nm,dasHandler.getProperty("TC_DIR_INTERFACE"));

	}



	/**
	 * 2010-07-29 UNLOCK by USERID 
	 * @param strUserID
	 * @return
	 * @throws RemoteException
	 */
	public int UnlockByUserID(String strUserID) throws Exception
	{

		return externalDAO.UnlockByUserID(strUserID);

	}


	/**
	 * 마스터의 해당 .mer 파일정보 조회
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getMergedFilenames(long master_id) throws Exception
	{

		return externalDAO.getMergedFilenames(master_id);

	}

	/**
	 * 멀티 락 설정/해제
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public int MultiLockUnlock(String xml) throws Exception
	{

		return externalDAO.MultiLockUnlock(xml);

	}

	/**
	 * 마스터의 해당 정보를 조회
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String getMasterDataTotaly(String xml) throws Exception
	{

		return externalDAO.getMasterDataTotaly(xml);

	}




	/**
	 * 마스터id로 관련데이터를 수집한다
	 * @param xml
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @throws RemoteException
	 */

	public String getMasterDataAll(String xml) throws NumberFormatException, Exception
	{

		return externalDAO.getMasterDataAll(xml);

	}


	/**
	 * 기초정보를 조회
	 * @param master_id
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @throws RemoteException
	 */
	public String getBaseInfo(long master_id) throws NumberFormatException, Exception
	{

		try {
			long nMasterID = master_id;
			long rMasterID = 0;
			// XML에서 masterID를 찾아낸다.
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

			Das das = new Das();

			MetaDataInfo metaDataInfo = externalDAO.getBaseResult(nMasterID);
			if(logger.isDebugEnabled())
				logger.debug("#########metaDataInfo.getTitle("+metaDataInfo.getMasterId()+")########" + metaDataInfo.getTitle() );
			das.setMetaDataInfo(metaDataInfo);

			Ingest ingest = externalDAO.getIngestMetaResult(nMasterID);
			das.getMetaDataInfo().setIngest(ingest);

			rMasterID = Long.parseLong(externalDAO.selectRelationMaster(nMasterID));
			// 관련 영상 Meta 관련 정보를 가져온다.
			if(nMasterID != rMasterID){
				Relation relation = externalDAO.getRelationMetaResult(rMasterID);
				if(relation != null)
					das.getMetaDataInfo().setRelation(relation);
			}

			Attach attach = externalDAO.getAttachResult(nMasterID);
			if(attach != null)
				das.getMetaDataInfo().setAttach(attach);

			Annot annot = externalDAO.getAnnotInfo(nMasterID);
			if(annot != null)
				das.getMetaDataInfo().setAnnot(annot);
			String xml = "";
			try {
				xml = convertorService.createMarshaller(das);
			} catch (JAXBException e) {
				logger.error("getBaseInfo Xml Create Error", e);
			}

			return xml;

		} catch (Exception e) {
			throw e;
		}
	}


	/**
	 * 화면정보를 조회한다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getSceanInfo(long master_id) throws Exception
	{

		return externalDAO.getSceanInfo2(master_id);

	}

	/**
	 * 관련영상을 조회한다.
	 * @param master_id
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @throws RemoteException
	 */

	public String getRelationScean(long master_id) throws NumberFormatException, Exception
	{

		return externalDAO.getRelationScean(master_id);

	}

	/**
	 * 일괄수정할 데이터를 수정한다.
	 * @param totalChangeInfoDO 일괄수정할 정보가 들어있는 beans
	 * @return updaetcount
	 * @throws Exception 
	 */
	public int updateTotalChange(TotalChangeInfoDO totalChangeInfoDO)throws Exception{

		return externalDAO.updateTotalChange(totalChangeInfoDO);

	}

	public int getTotalChangeCount(ProgramInfoDO	programInfoDO) throws Exception
	{

		return externalDAO.selectNewTotalChangeCount(programInfoDO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT);

	}

	/**
	 * 일괄수정할 마스터 정보를 조회한다.
	 * @param programInfoDO 마스터 정보 조회할 정보가 들어있는 beans
	 * @return List
	 * @throws Exception 
	 */
	public List getTotalChangelist(ProgramInfoDO	programInfoDO) throws Exception {
		return externalDAO.selectNewTotalChangelist(programInfoDO, DASBusinessConstants.PageQueryFlag.NORMAL);
		//return externalDAO.selectTotalChangelist(programInfoDO);
	}


	/**
	 * 등록사진 텝을 조회한다 (팝업).
	 * @param condition 조회할 정보가 들어있는 beans                                                                                                       
	 * @return    List                                                                                                                     
	 * @throws Exception 
	 */
	public List getAttachPhotoList(PhotoInfoDO condition) throws Exception
	{

		return externalDAO.getAttachPhotoList(condition);

	}

	/**
	 * 등록사진  조회한다.
	 * @param master_id                                                                                                                        
	 * @return    List                                                                                                                     
	 * @throws Exception 
	 */
	public List getPhotoList(int master_id) throws Exception
	{

		return externalDAO.getPhotoList(master_id);

	}

	/**
	 * 첨부사진 정보를 저장한다.
	 * @param photoInfoDO 사진 정보 리스트	
	 * @return 	PhotoInfoDO object 리스트 
	 * @throws Exception 
	 */
	public String insertPhotoInfo(List attachphotoInfoDOList) throws Exception
	{

		return externalDAO.insertPhotoinfo(attachphotoInfoDOList);

	}

	/**
	 * 사진 정보를 저장한다.
	 * @param photoInfoDO                                                                                                                        
	 * @return    updatecount                                                                                                                     
	 * @throws Exception 
	 */ 
	public int insertPotoInfo(PhotoInfoDO photoInfoDO) throws Exception
	{

		return externalDAO.insertPotoinfo(photoInfoDO);

	}


	/**
	 * 첨부사진 정보를 저장한다.
	 * @param photoInfoDO   정보를 가지고 잇는 beans                                                                                                                     
	 * @return     updatecount                                                                                                                    
	 * @throws Exception 
	 * 
	 */ 
	public int insertAttachPotoInfo(PhotoInfoDO photoInfoDO) throws Exception
	{

		return externalDAO.insertAttachPotoinfo(photoInfoDO);

	}

	/**
	 * DAS-TM 에 전송 작업을 요청한다.-사용하지 않음 completeDown에 통합
	 * @param addTask
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getAddTask(String xml)throws Exception{

		String rtnValue="";

		if(logger.isDebugEnabled())
			logger.debug("[getAddTask][input xml]"+CommonUtl.transXmlText(xml));

		TansferPortTypeProxy port = new TansferPortTypeProxy();
		rtnValue = port.addTask(CommonUtl.transXmlText(xml));
		rtnValue="";
		return rtnValue;

	}

	/**
	 * 클라이언트의 요청으로 DAS-TM 서버로 상태값을 요청한다.
	 * @param getStatus
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getTmStatus(String xml)throws Exception{

		String rtnValue="";

		TansferPortTypeProxy port = new TansferPortTypeProxy();
		rtnValue = port.getTaskStatus(xml);
		return rtnValue;

	}

	/**
	 * DAS-TM 의 데이타를 받는 벌그
	 * @param getTmStatusAll
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertTmStatusAll(List<TransferDO> transferDO)throws Exception{

		int[] retVal = externalDAO.updateTaskidAll(transferDO);
		return retVal.length;
	}



	/**
	 * DAS-TM 에서 받은 데이터를 db에 기록한다
	 * @param transfer 데이터들이 담겨있는 beans
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertAddTask(TransferDO transfer) throws Exception
	{

		//이미 존재하는 사용자인지를 검증한다.
		if(externalDAO.isThereTaskid(transfer.getTaskID())) {
			return externalDAO.updateTaskid(transfer.getTaskID(), transfer.getStatus());
		}
		return externalDAO.insertAddTaskinfo(transfer);

	}

	/**
	 * 사진 이용횟수를 수정한다.
	 * @param photInfoDO                                                                                                                                                                                              
	 * @return     updatecount                                                                                                                                                                                         
	 * @throws Exception 
	 */
	public int updatPhotoCount(PhotoInfoDO photInfoDO) throws Exception
	{

		return externalDAO.updatPhotoCount(photInfoDO);

	}

	/**
	 * NLE Drag&Drop 대상을 조회한다.
	 * @param preProcessingDO
	 * @return List
	 * @throws Exception 
	 */
	public List getPreProcessingList(PreProcessingDO preProcessingDO) throws Exception
	{

		return externalDAO.selectPreProcessingList(preProcessingDO);

	}

	/**
	 * NLE 메타 & DTL 등록 대상 조회한다.
	 * @param conditionDO
	 * @return List
	 * @throws Exception 
	 */
	public List getArchPreProcessing(WorkStatusConditionDO conditionDO) throws Exception
	{

		return externalDAO.selectArchPreProcessingList(conditionDO);

	}

	/**
	 * NLE 메타 & DTL 등록한다.
	 * @param metadataMstInfoDO
	 * @return List
	 * @throws Exception 
	 */
	public int insertMetadat(MetadataMstInfoDO metadataMstInfoDO) throws Exception
	{

		return externalDAO.insertMetadat(metadataMstInfoDO);

	}

	/**
	 * NLE 메타 & DTL 등록 메타데이타를 카피해서 등록한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 */
	public int insertCopyMetadat(MetadataMstInfoDO metadataMstInfoDO) throws Exception
	{

		return externalDAO.insertCopyMetadat(metadataMstInfoDO);

	}



	/**
	 *  DAS-TM 서버에서 받은 값을 db에저장한다.
	 * @param transfer 전송받은 데이터가 담긴 beans
	 * @param TaskID  task id
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertTmStatus(TransferDO transfer, int TaskID) throws Exception
	{

		//이미 존재하는 사용자인지를 검증한다.
		if(externalDAO.isThereTaskid(TaskID)) {
			return externalDAO.updateTaskid(TaskID,transfer.getProgress());
		}
		return externalDAO.insertTMstatusinfo(transfer, TaskID);

	}


	/**
	 * NLE  & DTL 등록한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertNLEandDTL(ManualArchiveDO manualArchiveDO) throws Exception
	{

		PdsArchiveDO pdsarchive = new PdsArchiveDO();
		String sResult ="";
		long ct_id = manualArchiveDO.getCt_id();
		pdsarchive = externalDAO.selectArchiveInfo(manualArchiveDO.getCt_id());
		pdsarchive.setCt_id(manualArchiveDO.getCt_id());
		pdsarchive.setDtl_gubun(manualArchiveDO.getDtl_gubun());

		if(pdsarchive.getArch_route().equals("DP")){
			externalDAO.updateManual_yn(ct_id);
			PdsArchiveDO pADO = externalDAO.selectCtiFromCtIdForNonERP(pdsarchive.getCt_id());
			logger.debug("[pdsarchive][pdsarchive pADO]" + pADO);
			String pgm_cms_id = "";
			sResult =externalDAO.ArchivePDSReq(pADO,pgm_cms_id);
		} else if (ct_id > 0){			
			externalDAO.updateManual_yn(ct_id);
			//  1051141    arcreq/SBS/manual/201604/22/  P20160422V00253_20160422162457_HR.MXF  P20160422V00253  20160422163649  001         001        100   
			PdsArchiveDO pADO = externalDAO.selectCtiFromCtIdForPDS(pdsarchive.getCt_id());
			//  P00692
			String pgm_cms_id = systemManageDAO.selectPdsPgmId(pdsarchive.getCt_id());
			pADO.setPds_cms_id(pgm_cms_id);
			logger.debug("[pADO][Input pADO]" + pADO);
			sResult =externalDAO.ArchivePDSReq(pADO,pgm_cms_id);
		}
		if(!sResult.equals("0")){
			return 1;
		}else{
			return 0;
		}

	}

	/**
	 * NLE  & DTL 등록한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertNLEandDTL(long ct_id) throws Exception
	{

		PdsArchiveDO pdsarchive = new PdsArchiveDO();
		String sResult ="";
		pdsarchive = externalDAO.selectArchiveInfo(ct_id);
		pdsarchive.setCt_id(ct_id);
		if(pdsarchive.getArch_route().equals("DP")){
			externalDAO.updateManual_yn(ct_id);
			PdsArchiveDO pADO = externalDAO.selectCtiFromCtIdForNonERP(pdsarchive.getCt_id());

			String pgm_cms_id = "";
			sResult =externalDAO.ArchivePDSReq(pADO,pgm_cms_id);
		}else if(ct_id>0){			
			externalDAO.updateManual_yn(ct_id);
			PdsArchiveDO pADO = externalDAO.selectCtiFromCtIdForPDS(pdsarchive.getCt_id());
			String pgm_cms_id = systemManageDAO.selectPdsPgmId(pdsarchive.getCt_id());
			pADO.setPds_cms_id(pgm_cms_id);

			sResult =externalDAO.ArchivePDSReq(pADO,pgm_cms_id);
		}
		if(Boolean.valueOf(sResult)){
			return 1;
		}else{
			return 0;
		}

	}


	/**
	 * NLE  & DTL 삭제한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteNLE(long ct_id) throws Exception
	{	

		return externalDAO.deleteNLE(ct_id);			

	}


	/**
	 * NLE  & DTL 삭제한다.(고해상도)
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteNLEForDown( long cart_no) throws Exception
	{	

		return externalDAO.deleteNLEForDown(cart_no);			

	}


	/**
	 * DTL 수동 등록한다.
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertDTL(String master_id) throws Exception
	{

		return externalDAO.insertDTL(master_id);

	}


	/**
	 * 관련영상 링크조회한다
	 * @param masterId
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getRelationLink(long masterId) throws Exception
	{

		return externalDAO.selectRelationLink(masterId);

	}



	/**
	 * PDS DOWN
	 * @param commonDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getPDSList(PdsDownDO pdsDownDO) throws Exception
	{

		return externalDAO.getPDSList(pdsDownDO);

	}



	/**
	 * NDS DOWN
	 * @param commonDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getNDSList(NdsDownDO ndsDownDO) throws Exception
	{

		return externalDAO.getNDSList(ndsDownDO);

	}




	/**
	 * 기본정보 초기화면
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getRepBaseInfo(long master_id) throws Exception
	{

		return externalDAO.getRepBaseInfo(master_id);

	}


	/**
	 * Backend Tc 연동 하는 작업 완료 요청 인터페이스
	 * @param tcBeanDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public TcBeanDO insertReqJobTC(TcBeanDO tcBeanDO) throws Exception{

		String Tc_type = systemManageDAO.isPDSorRecreate(tcBeanDO.getJob_id());
		String req_cd = systemManageDAO.isReq_cd(tcBeanDO.getJob_id());

		//데이터가 없다면 그냥 null 리턴
		if(Tc_type.equals("") || req_cd.equals("")){
			return null;
		}

		tcBeanDO.setReq_cd(req_cd);
		if(logger.isDebugEnabled()) {
			logger.debug("ct_id: "+tcBeanDO.getCt_id()+", tc_type: "+Tc_type);
		}

		if(Tc_type.equals(CodeConstants.TcGubun.PDS)){  //001 재생성 002 pds 요청 003 수동아카이브 004 IFCMS
			TcBeanDO resultTC =	externalDAO.selectTcJob2(tcBeanDO);
			//tcBeanDO.setCt_id(574856);

			PdsArchiveDO pdsarchive = new PdsArchiveDO();
			String ctcla = systemManageDAO.selectCtcla(tcBeanDO.getCt_id());
			PdsArchiveDO info = externalDAO.selectAutoArchiveInfobyCt_id(tcBeanDO.getCt_id());
			pdsarchive.setCt_cla(ctcla);
			pdsarchive.setCt_id(tcBeanDO.getCt_id());
			pdsarchive.setMedia_id(tcBeanDO.getMedia_id());

			boolean result = systemManageDAO.getAutoArchvieList(pdsarchive.getCt_cla(),tcBeanDO.getCocd(),info.getChennel(),info.getArch_route());
			if(result){
				// IfCMS 삭제 콘텐츠가 아니라면 아카이브 요청
				PdsArchiveDO pADO = externalDAO.selectCtiFromMediaidForPDS(pdsarchive);
				if(pADO != null && (pADO.getMedia_id() != null && !pADO.getMedia_id().equals("delete"))) {
					String pgm_cms_id = systemManageDAO.selectPdsPgmId(tcBeanDO.getCt_id());
					logger.debug("[PDS][Input pADO]" + pADO);
					externalDAO.ArchivePDSReq(pADO, pgm_cms_id);
				}

			}

			return resultTC;
		} else if(Tc_type.equals(CodeConstants.TcGubun.MANUAL)) {   //001 재생성 002 pds 요청 003 수동아카이브 004 IFCMS
			logger.debug("before  "+Tc_type);
			logger.debug("before  "+req_cd);
			TcBeanDO resultTC =	externalDAO.selectTcJob3(tcBeanDO);

			//tcBeanDO.setCt_id(574870);

			String ctcla = systemManageDAO.selectCtcla(tcBeanDO.getCt_id());

			PdsArchiveDO pdsarchive = new PdsArchiveDO();
			pdsarchive.setCt_id(tcBeanDO.getCt_id());
			pdsarchive.setCt_cla(ctcla);
			pdsarchive.setMedia_id(tcBeanDO.getMedia_id());

			PdsArchiveDO pADO = externalDAO.selectCtiFromMediaidForPDS(pdsarchive);
			if(pADO != null) {
				logger.debug("[MANUAL][Input pADO]" + pADO);
				String pgm_cms_id = systemManageDAO.selectPdsPgmId(tcBeanDO.getCt_id());
				externalDAO.ArchivePDSReq(pADO, pgm_cms_id);
			}

			return resultTC;
		} else if(Tc_type.equals(CodeConstants.TcGubun.IFCMS)) {   //001 재생성 002 pds 요청 003 수동아카이브 004 IFCMS
			TcBeanDO resultTC =	externalDAO.selectTcJob4(tcBeanDO);

			PdsArchiveDO pdsarchive = new PdsArchiveDO();
			String ctcla = systemManageDAO.selectCtcla(tcBeanDO.getCt_id());

			pdsarchive.setCt_cla(ctcla);
			pdsarchive.setCt_id(tcBeanDO.getCt_id());
			PdsArchiveDO pADO = externalDAO.selectCtiFromMediaidForIFCMS(pdsarchive);
			if(pADO != null && (pADO.getMedia_id() != null && !pADO.getMedia_id().equals("delete"))) {
				String pgm_cms_id = systemManageDAO.selectPdsPgmId(tcBeanDO.getCt_id());
				logger.debug("[IFCMS][Input pADO]" + pADO);
				externalDAO.ArchivePDSReq(pADO,pgm_cms_id);
			}

			return resultTC;
		} else {
			return externalDAO.selectTcJob2(tcBeanDO);
		}

	}


	/**
	 * Backend TC 연동 하여 상태값을 변경하는 인터페이스
	 * @param tcBeanDO
	 * @return boolean
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public boolean updateTCState(TcBeanDO newTcBeanDO) throws Exception{

		long cti_id = externalDAO.selectMxfID(newTcBeanDO);
		newTcBeanDO.setCti_id(cti_id);


		if(newTcBeanDO.getCocd().equals("S")||newTcBeanDO.getCocd().equals("")){
			externalDAO.updateTcState(newTcBeanDO);

			if(newTcBeanDO.getReq_cd().equals("H264")){
				externalDAO.updateTcProgressForH264(newTcBeanDO);
			}else {
				externalDAO.updateTcProgress(newTcBeanDO);	
			}

			//das 장비테이블에 상태값 저장하는 로직
			externalDAO.updateDasEquipMent(newTcBeanDO);

			TcBeanDO oldTcBeanDO =externalDAO.selectTcState(newTcBeanDO);

			// work_stat의 상태가 F일때는 실패로 등록한다
			if(newTcBeanDO.getWork_stat().equals("F")){

				externalDAO.updateErrTcjob(newTcBeanDO);

				long eq_id = externalDAO.selectTcEqId(newTcBeanDO);
				String error_cont = "";
				String error_code="";
				String job_id ="014";
				String process_id ="";
				if(newTcBeanDO.getJob_status().equals("1")){
					error_cont="파일존재하지 않음";
					error_code="001";				
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("2")){
					error_cont="네트워크 오류";
					error_code="002";
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("3")){
					error_cont="파일 생성 실패";
					error_code="003";
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("4")){
					error_cont="파일자체 오류";
					error_code="004";
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("5")){
					error_cont="Catalog 생성 오류";
					error_code="007";
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("6")){
					error_cont="H264 생성 오류";
					error_code="005";
					process_id="MT";
				}else if(newTcBeanDO.getJob_status().equals("7")){
					error_cont="메타데이터 업데이트 실패";
					error_code="006";
					process_id="MT";
				}

				externalDAO.InsertErrjob(eq_id,error_code ,error_cont ,job_id,process_id,newTcBeanDO.getCt_id());

			}

			/**
			 * 기존 저장된 TC의 상태값이 'I'가 아닐때, 새로 들어온 값이 'I' 일때 JOB 이 있다면 작업을 할당한다.
			 */
			if(oldTcBeanDO.getWork_stat().equals("B") && newTcBeanDO.getWork_stat().equals("I")  
					||oldTcBeanDO.getWork_stat().equals("I") && newTcBeanDO.getWork_stat().equals("I")
					||oldTcBeanDO.getWork_stat().equals("F") && newTcBeanDO.getWork_stat().equals("I")
					){

				TcBeanDO jobTcBeanDO = externalDAO.selectTcJob();   // 작업이 있는지 확인하는 곳.

				TcBeanDO stateTcBeanDO = externalDAO.selectTcAllocationState(newTcBeanDO); // IDLE 인 backend_TC 확인하는곳.


				if(jobTcBeanDO != null && stateTcBeanDO != null){

					externalDAO.getTCJob(jobTcBeanDO, stateTcBeanDO,dasHandler.getProperty("TC_DIR_INTERFACE"));

					externalDAO.updateTcJobState(jobTcBeanDO.getSeq(),stateTcBeanDO.getTc_id());

					externalDAO.updateTcState(stateTcBeanDO.getSeq()+"");

					sleep(3000);

					return true;
				}
			}
			return false;
		} else {

			//미디어넷 TC 전용
			externalDAO.updateTcState(newTcBeanDO);
			externalDAO.updateTcProgress(newTcBeanDO);


			//das 장비테이블에 상태값 저장하는 로직
			externalDAO.updateDasEquipMent(newTcBeanDO);

			TcBeanDO oldTcBeanDO =externalDAO.selectTcState(newTcBeanDO);

			// work_stat의 상태가 F일때는 실패로 등록한다
			if(newTcBeanDO.getWork_stat().equals("F")){
				externalDAO.updateErrTcjob(newTcBeanDO);
			}

			/**
			 * 기존 저장된 TC의 상태값이 'I'가 아닐때, 새로 들어온 값이 'I' 일때 JOB 이 있다면 작업을 할당한다.
			 */
			//logger.debug("old workstat: "+oldTcBeanDO.getWork_stat()+", new workstat: "+newTcBeanDO.getWork_stat());
			if(oldTcBeanDO.getWork_stat().equals("B")&&newTcBeanDO.getWork_stat().equals("I")  
					||oldTcBeanDO.getWork_stat().equals("I")&&newTcBeanDO.getWork_stat().equals("I")
					||oldTcBeanDO.getWork_stat().equals("F")&&newTcBeanDO.getWork_stat().equals("I")
					){

				TcBeanDO jobTcBeanDO = externalDAO.selectMediaTcJob();   // 작업이 있는지 확인하는 곳.

				TcBeanDO stateTcBeanDO = externalDAO.selectTcAllocationState(newTcBeanDO); // IDLE 인 backend_TC 확인하는곳.


				if(jobTcBeanDO != null && stateTcBeanDO != null) {

					externalDAO.getTCJob(jobTcBeanDO, stateTcBeanDO,dasHandler.getProperty("MEDIATC_DIR_INTERFACE"));

					externalDAO.updateTcJobState(jobTcBeanDO.getSeq(),stateTcBeanDO.getTc_id());

					externalDAO.updateTcState(stateTcBeanDO.getSeq()+"");
					sleep(3000);
					return true;
				}
			}
			return false;
		}

	}

	/**
	 * 첨부파일 정보를 조회한다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getAttachFileInfo(long master_id) throws Exception
	{
		return externalDAO.getAttachFileInfo(master_id);

	}


	/**
	 * 다운로드 에러난것 재요청 DAS2.0
	 * @param cartNo 카드번호
	 * @param cartseq 카트순번
	 * @param title 제목
	 * @return updatecount
	 * @throws Exception 
	 */
	public int updateErrorDownCart(DownCartDO downCartDO) throws Exception
	{

		return externalDAO.updateErrorDownCart(downCartDO);

	}

	/**
	 * 다운로드 승인 조회한다.(등록시)
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return     List                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public List getApproveInfoList(ApproveInfoDO ApproveInfoDO) throws Exception
	{

		return externalDAO.selectApproveInfoList(ApproveInfoDO);

	}

	/**
	 * 다운로드 승인 조회한다.
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return    List                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public List getApproveInfo(ApproveInfoDO ApproveInfoDO) throws Exception
	{

		return externalDAO.getApproveInfo(ApproveInfoDO);

	}


	/**
	 *  승인정보를 저장한다
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @return    List                                                                                                                                                                                          
	 * @throws Exception 
	 */


	public int insertApproveInfo(List roleDO)  throws Exception
	{

		externalDAO.insertApproveInfo(roleDO);
		return 1;

	}


	/**
	 * 승인정보를 삭제한다
	 * @param user_no
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteApproveInfo(String user_no,String dept_Cd) throws Exception
	{

		return externalDAO.deleteApproveInfo(user_no,dept_Cd);

	}


	/**
	 * 승인정보를 삭제한다
	 * @param user_no
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteApproveInfo2(ApproveInfoDO approveInfoDO) throws Exception
	{

		return externalDAO.deleteApproveInfo2(approveInfoDO.getUser_no(),approveInfoDO.getDept_cd(),approveInfoDO.getPgm_id());

	}


	/**
	 * 김건삭 실장님 요청 사항 20090903 dekim
	 * WMV 재생성 요청한다.
	 * @param cti_id 컨텐츠 인스턴스id
	 * @param user_nm 유져명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateWMVForClient(long ct_id,String user_id) throws Exception
	{

		return externalDAO.recreateWMV_KFRMForClient(ct_id,user_id,dasHandler.getProperty("TC_DIR_INTERFACE"),"LR");

	}

	/**
	 * 김건삭 실장님 요청 사항 20090903 dekim
	 * WMV 재생성 요청한다.
	 * @param cti_id 컨텐츠 인스턴스id
	 * @param user_nm 유져명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateWMVforMainSean(long master_id,String user_id) throws Exception
	{

		String ct_ids = externalDAO.getIdFormasterId(master_id);
		String[] ct = ct_ids.split(",");
		for(int i = 0; i<ct.length;i++){
			externalDAO.recreateWMV_KFRMForClient(Long.parseLong(ct[i]),user_id,dasHandler.getProperty("TC_DIR_INTERFACE"),"LR");
		}
		return "1";

	}

	/**
	 * WMV 및 KFRM 재신청 요청한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자 명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateWMV_KFRMForClient(long ct_id,String user_nm) throws Exception
	{

		return externalDAO.recreateWMV_KFRMForClient(ct_id,user_nm,dasHandler.getProperty("TC_DIR_INTERFACE"),"LRCT");

	}

	/**
	 * WMV 및 KFRM 재신청 요청한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자 명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateWMV_KFRMforMainSean(long master_id,String user_nm) throws Exception
	{

		String ct_ids = externalDAO.getIdFormasterId(master_id);
		String[] ct = ct_ids.split(",");

		for(int i = 0; i<ct.length;i++){
			externalDAO.recreateWMV_KFRMForClient(Long.parseLong(ct[i]), user_nm, dasHandler.getProperty("TC_DIR_INTERFACE"),"LRCT");
		} 

		return "1";

	}

	/**
	 * 키프레인 재신청 요청 한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateKFRMForClient(long ct_id,String user_id) throws Exception
	{

		return externalDAO.recreateWMV_KFRMForClient(ct_id,user_id,dasHandler.getProperty("TC_DIR_INTERFACE"),"CT");

	}


	/**
	 * 키프레인 재신청 요청 한다.
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * @param user_nm 요청자명
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateKFRMforMainSean(long master_id,String user_id) throws Exception
	{

		String ct_ids = externalDAO.getIdFormasterId(master_id);
		String[] ct = ct_ids.split(",");
		for(int i = 0; i<ct.length;i++){
			externalDAO.recreateWMV_KFRMForClient(Long.parseLong(ct[i]),user_id,dasHandler.getProperty("TC_DIR_INTERFACE"),"CT");
		} 
		return "1";

	}


	/**
	 * DAS-TM Job 등록
	 * @param num 순번
	 * @return xml
	 * @throws Exception 
	 */
	public String DoAddTask(int num) throws Exception {

		String rtnValue="";
		String xml = externalDAO.selectNewAddTaskForXml(num);

		String tmURL = dasHandler.getProperty("DAS_TM_URL");
		for(int i=0; i<3; i++) {
			try {
				Tansfer transfer = new TansferLocator();
				TansferPortType  port = transfer.getTansferPort(new URL(tmURL));

				rtnValue = port.addTask(xml);
				break;
			} catch (Exception e) {
				logger.error("TM AddTask Error recount("+i+")", e);
				continue;
			}
		}

		return rtnValue;
	}

	/**
	 * DAS-TM 이용 여부 판단.
	 * @param num 순번
	 * @return boolean
	 * @throws Exception
	 */
	public boolean getUsedDasTmYn(int num) {

		try {//
			return externalDAO.getUsedDasTmYn(num);
		} catch (Exception e) {
			logger.error("getUsedDasTmYn", e);
		}
		return false;
	}


	/**
	 * contents_down_Tbl 의  num컬럼의 값으로 카트정보를 가져오게한다
	 * @param num - contents_down_tbl의 키값
	 * @return TransferDO
	 * @throws RemoteException
	 */
	public TransferDO getCartInfo(int num) throws Exception{

		return externalDAO.getCartInfo(num);

	}


	/**
	 * 아카이브가 되었다면 생성한다.
	 * @param num - contents_down_tbl의 키값
	 * @return
	 * @throws RemoteException
	 */
	public String getArchveList(String job_status, long cti_id) throws Exception
	{
		if(job_status.equals("C")){
			return "1";  
		}else{
			return "0";
		}
	}


	/**
	 * 사진 메타정보를 수정한다.
	 * @param PhotoInfoDO  수정할 정보를 가지고있는 beans
	 * @return Update count
	 * @throws Exception 
	 */
	public int updatePhotInfo(PhotoInfoDO photoInfoDO) throws Exception
	{

		return externalDAO.updatePhotInfo(photoInfoDO);

	}



	/**
	 * archive 요청job  등록
	 * @param xml
	 * @return updaetcount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int insertArchiveReq(String xml) throws Exception
	{

		return externalDAO.insertArchiveReq(xml);

	}

	/**
	 * archive 연동 하여 상태값을 변경하는 인터페이스
	 * @param archiveReqDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public boolean updateArchiveReq(ArchiveReqDO newTcBeanDO) throws Exception{

		//tc를 상태를 변화시킨다.
		externalDAO.updateArchive(newTcBeanDO);
		externalDAO.updateOnAirIngestStatus(String.valueOf(newTcBeanDO.getSEQ()));

		ArchiveReqDO oldBeanDO = externalDAO.selectArchiveState(newTcBeanDO);

		// work_stat의 상태가 F일때는 실패로 등록한다
		if(newTcBeanDO.getWork_stat().equals("F")){
			externalDAO.updateErrArchivejob(newTcBeanDO);
		}

		/**
		 * 기존 저장된 TC의 상태값이 'I'가 아닐때, 새로 들어온 값이 'I' 일때 JOB 이 있다면 작업을 할당한다.
		 */
		if(oldBeanDO.getWork_stat().equals("B") && newTcBeanDO.getWork_stat().equals("I")  
				||oldBeanDO.getWork_stat().equals("I") && newTcBeanDO.getWork_stat().equals("I")  ){

			ArchiveReqDO jobArchiveBeanDO = externalDAO.selectArchiveJob();   // 작업이 있는지 확인하는 곳.

			//20111228 받은   장비에 바로 리턴.
			ArchiveReqDO stateArchiveBeanDO = externalDAO.selectArchiveState(newTcBeanDO); // IDLE 인 backend_TC 확인하는곳.

			//logger.debug("[jobArchiveBeanDO]"+jobArchiveBeanDO);
			if(jobArchiveBeanDO != null && newTcBeanDO != null){
				externalDAO.getArchveJob(jobArchiveBeanDO, stateArchiveBeanDO,dasHandler.getProperty("ON_AIR_DIR_INTERFACE"));

				externalDAO.updateArchiveState(newTcBeanDO.getSEQ()+"");

				jobArchiveBeanDO.setArchive_id(String.valueOf(stateArchiveBeanDO.getSEQ()));
				externalDAO.updateArchiveInfo(jobArchiveBeanDO);

				return true;
			}
		}
		return false;

	}


	/**
	 * archive  연동 하는 작업 완료 요청 인터페이스
	 * @param archiveReqDO
	 * @return ArchiveReqDO
	 * @throws RemoteException
	 */
	public ArchiveReqDO insertReqJobArchive(ArchiveReqDO archiveReqDO) throws Exception{

		return externalDAO.selectArchiveJob(archiveReqDO);

	}

	/**
	 * SBS ERP DB에서 발령정보를 받아온다
	 * 가장 최근에 받아온 발령정보의 SEQ값을 받아와서 그이후의 발령정보를
	 * 받도록 한다.
	 */
	public String getOrderInfo() throws Exception{

		try {
			String maxValue = externalDAO.selectERPAppointMaxSeqQuery();
			if(logger.isDebugEnabled()) {
				logger.debug("order max value : "+maxValue);
			}

			OrderCallServiceProxy port = new OrderCallServiceProxy();
			return port.getOrderInfoList(Integer.parseInt(maxValue));
		} catch (Exception e) {
			logger.error("OrderCallService Error", e);
		}
		return "";
	}


	/**
	 * 이용자별 다운로드 목록을 조회한다
	 * 다운로드 현황에서 사용하는 함수. 다운로드 사용자 기준으로 다운로드 요청 건수를 확인한다.
	 * 
	 * @param CartItemDO                                                                                                                                                                     
	 * @return    List                                                                                                         
	 * @throws Exception 
	 */
	public List getCartInfoForUser(CartItemDO cartItemDO) throws Exception
	{

		return externalDAO.getCartInfoForUser(cartItemDO);

	}


	/**
	 * 우클릭 삭제(영상선정, 클립검색).
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String deleteMasterScean(long master_id) throws Exception
	{	

		externalDAO.deleteMasterSceanForMapp(master_id);	
		externalDAO.deleteMasterSceanForMst(master_id);	
		DiscardDO dis =  externalDAO.getDiscardInfo(master_id);
		dis.setMasterId(master_id);
		disuseDAO.insertDisuseForMeta(dis);

		return "1";		

	}

	/**
	 * 우클릭 삭제(영상선정, 클립검색).
	 * @param metadataMstInfoDO
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String deleteMasterScean2(DeleteDO deleteDO) throws Exception {	

		externalDAO.deleteMasterSceanForMapp(deleteDO.getMaster_id());	
		externalDAO.deleteMasterSceanForMst(deleteDO.getMaster_id());	
		DiscardDO dis =  externalDAO.getDiscardInfo(deleteDO.getMaster_id()); // metadat_mst_tbl에서 메타정보 수집

		dis.setMasterId(deleteDO.getMaster_id());
		dis.setDisuse_cont(deleteDO.getDel_cont()); // 폐기 요청 사유
		dis.setReg_id(deleteDO.getReg_id());

		disuseDAO.insertDisuseForMeta(dis);

		return "1";		

	}


	/**
	 * 수동아카이브 정보조회(미디어id)
	 * @param media_id
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getManualArchiveInfo(ManualArchiveDO manualArchiveDO) throws Exception
	{

		String[] media_ids = manualArchiveDO.getOrg_media_id().split(",");

		String mediaid = "";
		List resultList = new ArrayList();
		for(int i=0; i< media_ids.length;i++){
			ManualArchiveDO item = new ManualArchiveDO();
			ManualArchiveDO manualArchiveDO2 = externalDAO.getManualArchiveInfo(media_ids[i]);
			if(!manualArchiveDO2.getOrg_media_id().equals("")){
				manualArchiveDO2.setOrg_media_id(media_ids[i]);
				manualArchiveDO2.setNew_media_id(codeInfoDAO.getMediaId());
				manualArchiveDO2.setFl_path(manualArchiveDO.getFl_path());
				manualArchiveDO2.setDtl_gubun(externalDAO.getDtl_route(0));
				item.setOrg_media_id(manualArchiveDO2.getOrg_media_id());
				item.setNew_media_id(manualArchiveDO2.getNew_media_id());
				item.setIsmedia_yn("Y");

				externalDAO.insertManualArchiveInfo(manualArchiveDO2);
			}else{
				item.setOrg_media_id(media_ids[i]);
				item.setNew_media_id(codeInfoDAO.getMediaId());
				item.setIsmedia_yn("N");	
			}

			resultList.add(item);
		}
		return resultList;

	}

	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getManualInfo(ManualArchiveDO manualArchiveDO) throws Exception
	{

		return externalDAO.getManualInfo(manualArchiveDO);

	}


	/**
	 * 아카이브 전송재요청한다. DAS2.0
	 * @param seq contents_loc_tbl의 순번을 기준으로 재 아카이브 요청을 한다(유지보수용)
	 * @return boolean
	 * @throws RemoteException
	 */
	public String updateRetryArchive(long seq) throws Exception
	{

		PdsArchiveDO pdsarchive = externalDAO.updateRetryArchive(seq);
		return	externalDAO.ArchivePDSReq(pdsarchive,pdsarchive.getPds_cms_id());

	}



	/**
	 * 아카이브 전송재요청한다. DAS2.0
	 * @param seq contents_loc_tbl의 순번을 기준으로 재 아카이브 요청을 한다(유지보수용)
	 * @return boolean
	 * @throws RemoteException
	 */
	public String updateRetryArchiveByCtId(long ct_id) throws Exception
	{

		PdsArchiveDO pdsarchive = externalDAO.updateRetryArchiveByCtId(ct_id);

		return	externalDAO.ArchivePDSReq(pdsarchive,pdsarchive.getPds_cms_id());

	}

	/**
	 * 코너의 내용을 입력한다
	 * @param cnInfoDO                                                                                                                                    	
	 * @throws Exception 
	 */
	public List insertCornerContinfo(long cn_id, List annotInfoList) throws Exception
	{				
		return externalDAO.insertCornerContinfoByBatch(cn_id,annotInfoList);

	}


	/**
	 * 미디어id를 생성한다
	 * @param                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 */
	public String insertMediaId() throws Exception
	{
		for(int i =0; i<4;i++){
			externalDAO.insertMediaid();
		}
		return "1";

	}


	public void sleep(int time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 다운로드 완료  DAS2.0 
	 * @param downCartDO 다운로드 요청 정보가 담겨있는 beans
	 * @return Update count
	 * @throws Exception 
	 */
	public int compledownprocess(int cart_no, int cart_seq) throws Exception
	{

		return externalDAO.updateCompleteDown(cart_no,cart_seq);

	}


	/**
	 * 방송길이를 합한다
	 * @param master_brd_leng테이블의 값을 가져온후 해당값을 업데이트후 metadat_mst_tbl에 저장한다.
	 * @return boolean
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String updateBrdLeng() throws Exception
	{

		return	externalDAO.updateBrdLeng();

	}


	/**
	 * 메타 데이타 마스터 테이블 상태 정보를 갱신한다.	
	 *
	 * @return updateCount 		
	 * @throws Exception 
	 */
	public String updateMetadataStatusCd(MetadataMstInfoDO metadataMstInfoDO) throws Exception{

		int result = externalDAO.updateMetaDatastatCd(metadataMstInfoDO);

		return String.valueOf(result);

	}	

	/**
	 * 외부 매체변환 건에대해서 tc에 등록하는 함수
	 * @param tcBeanDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public TcBeanDO insertComMedia(TcBeanDO tcBeanDO) throws Exception{

		//미디어 ID를 검색한다.
		TcBeanDO tcDO = new TcBeanDO();
		String cocd = externalDAO.selectCOCDForCt_id(tcBeanDO.getCt_id());
		String manual_yn =externalDAO.selectManualYnForCt_id(tcBeanDO.getCt_id());
		String fl_path = externalDAO.selectFlPathorCt_id(tcBeanDO.getCt_id());
		tcBeanDO.setCocd(cocd);
		tcBeanDO.setInput_hr(fl_path);
		tcBeanDO.setManual_yn(manual_yn);
		// low 정보 생성한다.
		long cti_id =systemManageDAO.insertConInstInfoForLowForMedia(tcBeanDO);
		tcBeanDO.setCti_idForLow(cti_id);

		int result=0;

		result  = systemManageDAO.insertTCinfo(tcBeanDO);

		if(result == 0){
			tcDO.setResult("FALSE");
		}else {
			tcDO.setResult("TRUE");
		}
		return tcDO;

	}

	//2012.4.24
	/**
	 * 다운로드 승인 조회한다.
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return    List                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public List getApproveInfoForChennel(ApproveInfoDO ApproveInfoDO) throws Exception
	{

		return externalDAO.getApproveInfoForChennel(ApproveInfoDO);

	}


	/**
	 * 채널별  승인자 대상  조회한다.(등록시)
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return     List                                                                                                                                                                                          
	 * @throws Exception 
	 */
	public List getApproveInfoListForChennel(ApproveInfoDO ApproveInfoDO) throws Exception
	{

		return externalDAO.selectApproveInfoListForChennel(ApproveInfoDO);

	}


	public int insertApproveInfoForChennel(List roleDO)  throws Exception
	{

		externalDAO.insertApproveInfoForChennel(roleDO);
		return 1;

	}

	/**
	 * 승인정보를 삭제한다
	 * @param user_no
	 * @return updatecount
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteApproveInfoForChennel(ApproveInfoDO approveInfoDO) throws Exception
	{

		return externalDAO.deleteApproveInfoForChennel(approveInfoDO);

	}

	/**
	 * 미디어 메타데이터 조회를 한다.
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 */
	public List getClipInfoList(MediaArchiveDO manualArchiveDO) throws Exception
	{

		return	externalDAO.getClipInfoList(manualArchiveDO);

	}

	/**
	 * DTL 경로 추가.
	 * @param DtlInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 *  */
	public int insertDtlInfo(DtlInfoDO dtlInfoDO) throws Exception
	{

		return externalDAO.insertDtlInfo(dtlInfoDO);

	}


	/**
	 * dtl 목록을 조회한다
	 * @param  xml                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getDtlInfo() throws Exception
	{

		return externalDAO.getDtlInfo();

	}


	/**
	 * 사용자 로그인 현황을 조회한다(모니터링)
	 * @param  xml                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getLogInOutInfo(LogInOutDO logInOutDO) throws Exception
	{

		return externalDAO.getLogInOutInfo(logInOutDO);

	}


	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getArchiveInfo(MonitoringDO monitoringDO) throws Exception
	{

		return externalDAO.getArchiveInfo(monitoringDO);

	}

	/**
	 * tc 현황을 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getTCinfo(MonitoringDO monitoringDO) throws Exception
	{

		return externalDAO.getTCinfo(monitoringDO);

	}


	/**
	 * tm 현황을 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getTminfo(MonitoringDO monitoringDO) throws Exception
	{

		return externalDAO.getTminfo(monitoringDO);

	}


	/**
	 * 해당 건의 상세 정보를 조회한다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getDetailInfo(MonitoringDO monitoringDO) throws Exception
	{

		if(monitoringDO.getGubun().equals("001")){
			//아카이브 상세 구분
			return externalDAO.getDetailInfoForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("002")){
			//tc 상세구분
			return externalDAO.getDetailInfoForTC(monitoringDO);
		}else if(monitoringDO.getGubun().equals("003")){
			//tm 상세구분
			return externalDAO.getDetailInfoForTM(monitoringDO);
		}

		return null;

	}


	/**
	 * 우선순위를 변경한다
	 * @param  monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int changePriority(MonitoringDO monitoringDO) throws Exception
	{

		if(monitoringDO.getGubun().equals("001")){
			//아카이브 관리tab

			//WF 에 우선순위변경 요청을 보낸다
			systemManageDAO.changePriorityForArchive(monitoringDO);

			return externalDAO.changePriorityForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("002")){
			//재생성 관리 텝
			return externalDAO.changePriorityForTC(monitoringDO);
		}else if(monitoringDO.getGubun().equals("003")){
			//다운로드 관리 텝 -> 전송우선순위(구현예정)
			//return externalDAO.changePriorityForTM(monitoringDO);
		}else if(monitoringDO.getGubun().equals("004")){
			//수동복본 관리텝
			//WF 에 우선순위변경 요청을 보낸다
			systemManageDAO.changePriorityForArchive(monitoringDO);

			return 1;
		}else if(monitoringDO.getGubun().equals("005")){
			//수동복원 관리텝
			//WF 에 우선순위변경 요청을 보낸다
			systemManageDAO.changePriorityForArchive(monitoringDO);

			return 1;
		}else if(monitoringDO.getGubun().equals("006")){
			//수동 소산 관리텝
			//WF 에 우선순위변경 요청을 보낸다
			systemManageDAO.changePriorityForArchive(monitoringDO);

			return 1;
		}else if(monitoringDO.getGubun().equals("007")){
			//WF 에 우선순위변경 요청을 보낸다
			systemManageDAO.changePriorityForDownload(monitoringDO);
			return externalDAO.changePriorityForDown(monitoringDO);
		}
		return 0;

	}


	/**
	 * 진행중 작업을 취소한다
	 * @param  monitoringDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int cancelJob(MonitoringDO monitoringDO) throws Exception
	{

		if(monitoringDO.getGubun().equals("001")){
			//아카이브 상세 구분

			//WF 에 취소요청을 보낸다
			monitoringDO.setGubun("005");
			systemManageDAO.cancleJobRequest(monitoringDO);
			return 1;
		}else if(monitoringDO.getGubun().equals("002")){
			//재생성
			return externalDAO.cancelJobForTC(monitoringDO);
		}else if(monitoringDO.getGubun().equals("003")){
			//다운로드tm 전송(구현예정)
			//WF 에 취소요청을 보낸다
			//systemManageDAO.cancleJobRequestForDown(monitoringDO);
			//return externalDAO.cancelJobForTM(monitoringDO);
		}else if(monitoringDO.getGubun().equals("004")){
			//수동복본
			//WF 에 취소요청을 보낸다
			systemManageDAO.cancleJobRequest(monitoringDO);
			return externalDAO.cancelJobForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("005")){
			//수동복원
			//WF 에 취소요청을 보낸다
			systemManageDAO.cancleJobRequest(monitoringDO);
			return externalDAO.cancelJobForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("006")){
			//수동소산
			//WF 에 취소요청을 보낸다
			systemManageDAO.cancleJobRequest(monitoringDO);
			return externalDAO.cancelJobForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("007")){
			//다운로드 (dtl)
			//WF 에 취소요청을 보낸다
			monitoringDO.setGubun("007");
			systemManageDAO.cancleJobRequestForDown(monitoringDO);
			return 1;
		}

		return 0;

	}


	/**
	 * 사용자 수동삭제목록을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getManualDeleteList(ManualDeleteDO manualDeleteDO) throws Exception
	{

		return externalDAO.getManualDeleteList(manualDeleteDO);

	}


	/**
	 * 사용자 수동삭제를 요청한다.
	 * @param xml
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int manualDelete(ManualDeleteDO manualDeleteDO) throws Exception
	{

		if(manualDeleteDO.getGubun().equals("001")){
			systemManageDAO.deleteRequest(manualDeleteDO);
			return externalDAO.manualDeleteForArchive(manualDeleteDO);
		}else if(manualDeleteDO.getGubun().equals("002")){
			return externalDAO.manualDeleteForDown(manualDeleteDO);

		}

		return 0;

	}


	/**
	 * 에러 목록을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getErroeList(ErrorLogDO errorLogDO) throws Exception
	{

		return externalDAO.getErroeList(errorLogDO);


	}


	/**
	 * 서버 목록을 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getServerList(ServersDO serversDO) throws Exception
	{

		externalDAO.updateServerStatus(serversDO);
		return externalDAO.getServerList(serversDO);

	}


	/**
	 * 에러정보를 등록 및 갱신한다.
	 * @param errorRegisterDO 에러 정보
	 * @throws Exception 
	 * @retrun errorRegisterDO 에러 정보		
	 */
	public int insertError(ErrorLogDO errorLogDO) throws Exception
	{

		return externalDAO.insertError(errorLogDO);

	}



	/**
	 * 미디어넷 자료 상태를 업데이트한다
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateMediaCilpStatus(MediaArchiveDO mediaArchiveDO) throws Exception
	{

		return externalDAO.updateMediaCilpStatus(mediaArchiveDO);

	}



	/**
	 * wmvlist조회 (H264 전황용)
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 */
	public List getWmvList(WmvH264DO wmvH264DO) throws Exception
	{

		return	externalDAO.getWmvList(wmvH264DO);

	}


	/**
	 * WMV- H264 완료 상태를 업데이트 한다.
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List updateWmvStatus(WmvH264DO xml) throws Exception
	{

		return externalDAO.updateWmvStatus(xml);

	}




	/**
	 * 마스터id별 ctiid의 묶음을 조회한다
	 * @param cartItemDO                                                                                                                                                                                            
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                            
	 * @throws Exception 
	 */
	public List getGroupForMaster(long master_id) throws Exception
	{

		return externalDAO.getGroupForMaster(master_id);

	}


	/**
	 * 화면정보를 조회한다(미디어넷 스토리보드).
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getSceanInfoForIfCms(long ct_id)  throws Exception
	{

		return externalDAO.getSceanInfoForIfCms(ct_id);

	}




	/**
	 * 실패난 작업을 재요청한다.
	 * @param 
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String tryAgain(MonitoringDO monitoringDO) throws Exception
	{

		if(monitoringDO.getGubun().equals("001")){
			//아카이브 상세 구분

			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			_processor.updateRetryArchive(monitoringDO.getKey());
			return "success";
		}else if(monitoringDO.getGubun().equals("002")){
			//재생성

			return externalDAO.tryAgainForReMake(monitoringDO);
		}else if(monitoringDO.getGubun().equals("003")){
			//다운로드 전송(tm)
			TransferDOXML _doXML = new TransferDOXML();
			String getMessage =  TryAgingAddTask(Integer.parseInt(String.valueOf(monitoringDO.getKey())));
			logger.debug("getMessage ["+getMessage+"]");
			TransferDO _do2 = getCartInfo(Integer.parseInt(String.valueOf(monitoringDO.getKey())));
			compledownprocess(_do2.getCart_no(),_do2.getCart_seq());
			TransferDO _do = (TransferDO) _doXML.setDO(getMessage);
			_do.setCart_no(_do2.getCart_no());
			_do.setCart_seq(_do2.getCart_seq());
			insertAddTask(_do);

			return "success";


		}else if(monitoringDO.getGubun().equals("004")){
			//수동복본
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			long CT_ID = externalDAO.selectCT_IDForArchive(monitoringDO.getKey());
			String fileName = externalDAO.getContentsLocTbl(monitoringDO.getKey());
			UseInfoDO useDO = new UseInfoDO();
			useDO.setGubun("001");
			useDO.setFileName(fileName);
			useDO.setCt_id(CT_ID);
			useDO.setReg_id(monitoringDO.getReq_id());
			_processor.updateCopyRequestForCt_id(useDO);
			return "success";
		}else if(monitoringDO.getGubun().equals("005")){
			//수동소산
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			long CT_ID = externalDAO.selectCT_IDForArchive(monitoringDO.getKey());
			UseInfoDO useDO = new UseInfoDO();
			useDO.setGubun("002");
			useDO.setCt_id(CT_ID);
			useDO.setReg_id(monitoringDO.getReq_id());
			_processor.updateCopyRequestForCt_id(useDO);
			return "success";
		}else if(monitoringDO.getGubun().equals("006")){
			//수동복원
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			long CT_ID = externalDAO.selectCT_IDForArchive(monitoringDO.getKey());
			String tempFileName = externalDAO.getContentsLocTbl(monitoringDO.getKey());
			String[] temp = tempFileName.split("mxf");
			logger.debug("temp   " + temp.length);
			String fileName = temp[0]+"mp4";
			UseInfoDO useDO = new UseInfoDO();
			useDO.setGubun("003");
			useDO.setCt_id(CT_ID);
			useDO.setFileName(fileName);
			useDO.setReg_id(monitoringDO.getReq_id());
			_processor.updateCopyRequestForCt_id(useDO);
			return "success";
		}else if(monitoringDO.getGubun().equals("007")){
			//다운로드 요청
			CartContDO _do = new CartContDO();
			_do =externalDAO.selectCartInfo(monitoringDO.getKey());
			_do.setDtl_type(workDAO.getCocdInfo(_do.getCartNo(),_do.getCartSeq()));

			String xml = externalDAO.getNewDownloadXmlFormat(_do);//카트번호, 카트 순번을 받아 처리 한건씩.


			/**
			 * DTL 이관 전 클립 & 스토리지에 존재 한다면 DAS-TM에 전송 요청  시작
			 */
			// DTL 이관 전 클립 & 스토리지에 존재 분기시작.
			CartContDO tmpCartContDO  = externalDAO.getStLocClip(externalDAO.selectCartContInfo(_do.getCartNo(), _do.getCartSeq()).getMasterId());
			if(!CommonUtl.isEmptyString(tmpCartContDO.getFl_path())){
				/**
				 * PDS,NDS,계열사 해당하는 다운로드 요청만 DAS-TM 전송요청을 하게 됨(20110512:dekim)
				 */
				if(externalDAO.getUsedDasTmYnByCartNo(_do.getCartNo())){

					String getMessage =  externalDAO.addTaskByStorageClip(_do.getCartNo(),_do.getCartSeq());

					logger.debug("getMessage ["+getMessage+"]");
					TransferDOXML _doXML = new TransferDOXML();
					TransferDO _do1 = (TransferDO) _doXML.setDO(getMessage);
					_do1.setCart_no(Integer.parseInt(_do.getCartNo()+""));
					_do1.setCart_seq(Integer.parseInt(_do.getCartSeq()+""));
					externalDAO.insertAddTaskRes(_do1);
					_do.setNum(monitoringDO.getKey());
					externalDAO.updateDownState(_do);
				}
			}else{
				if(!"".equals(xml)){
					NevigatorProxy port = new NevigatorProxy();
					try {
						String _result = port.downloadService(xml);
						logger.debug("############_result   -  " + _result);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
				_do.setNum(monitoringDO.getKey());
				externalDAO.updateDownState(_do);
			}
			return "success";

		}
		return "fail";

	}




	/**
	 * 다운로드 현황을 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getDowninfo(MonitoringDO monitoringDO) throws Exception
	{

		return externalDAO.getDowninfo(monitoringDO);

	}


	/**
	 * 수동 작업 현황을 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getManualJobinfo(MonitoringDO monitoringDO) throws Exception
	{

		return externalDAO.getManualJobinfo(monitoringDO);

	}




	/**
	 * 내 아카이브 요청 목록조회
	 * @param commonDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getMyArchiveRequestList(WorkStatusConditionDO dO) throws Exception
	{

		return externalDAO.getMyArchiveRequestList(dO);

	}


	/**
	 * 다운로드 현황을 조회한다(ifcms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getDowninfoForIfCms(MonitoringDO monitoringDO)  throws Exception{

		return externalDAO.getDowninfoForIfCms(monitoringDO);

	}


	/**
	 * DAS-TM 재전송 요철
	 * @param num 순번
	 * @return xml
	 * @throws Exception 
	 */
	public String TryAgingAddTask(int num) throws Exception {

		String rtnValue="";
		String xml="";
		int errorcount =0;

		TansferPortTypeProxy port = new TansferPortTypeProxy();
		for(int i=0; i<3; i++) {
			try {
				rtnValue = port.addTask(String.valueOf(num));
				break;
			} catch (Exception e) {
				if(i==2) {
					throw e;
				}
				logger.error("Tansfer call error", e);
				try {
					Thread.sleep(3000L);
				} catch (Exception e2) {}
			}
		}

		/*
		try {

			logger.debug("###errorcount"+errorcount);
			TansferPortTypeProxy port = new TansferPortTypeProxy();
			rtnValue = port.addTask(String.valueOf(num));

			return 		rtnValue;

		} catch (RemoteException e) {
			try {
				errorcount=1;

				TansferPortTypeProxy port = new TansferPortTypeProxy();
				rtnValue = port.addTask(CommonUtl.transXmlText(xml));
				return 		CommonUtl.transXMLText(rtnValue);
			} catch (RemoteException e1) {
				try {
					errorcount=2;
					logger.debug("###errorcount"+errorcount);
					TansferPortTypeProxy port = new TansferPortTypeProxy();
					rtnValue = port.addTask(CommonUtl.transXmlText(xml));
					return 		CommonUtl.transXMLText(rtnValue);
				} catch (RemoteException e2) {
					logger.error(e2);
				}
			}

		} catch (Exception e) {
			logger.error("TryAgingAddTask error", e);
		}
		 */
		return rtnValue;
	}


	/**
	 *  진행 상태를 조회한다(if cms)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public List getJobStatus(MonitoringDO monitoringDO) throws Exception
	{

		//001 : 아카이브, 002:다운로드
		if(monitoringDO.getGubun().equals("001")){
			return externalDAO.getJobStatusForArchive(monitoringDO);
		}else if(monitoringDO.getGubun().equals("002")){
			return externalDAO.getJobStatusForDownload(monitoringDO);	
		}

		return null;

	}



	/**
	 * 장비의 상태 업데이트를 한다
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 */
	public int updateEquipMentStatus(EquipMentInfoDO mediaArchiveDO) throws Exception
	{

		return externalDAO.updateEquipMentStatus(mediaArchiveDO);

	}





	/**
	 * 마스터id별 ctiid의 묶음을 조회한다
	 * @param cartItemDO                                                                                                                                                                                            
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                            
	 * @throws Exception 
	 */
	public List getGroupForMasterForClient(long master_id) throws Exception
	{

		return externalDAO.getGroupForMasterForClient(master_id);

	}




	/**
	 * 사용등급 업데이트를 한다
	 * @param                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 */
	public String updateRistClfCd() throws Exception
	{
		//for(int i =0; i<3;i++){
		externalDAO.updateRistClfCd();
		//}
		return "1";
	}



	/**
	 * 기초정보를 조회(if cms)
	 * @param master_id
	 * @return
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @throws RemoteException
	 */
	public String getBaseInfoForIfCms(long master_id) throws NumberFormatException, Exception
	{

		long nMasterID = master_id;
		long rMasterID = 0;
		// XML에서 masterID를 찾아낸다.
		XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();

		Das das = new Das();

		MetaDataInfo metaDataInfo = externalDAO.getBaseResult(nMasterID);
		das.setMetaDataInfo(metaDataInfo);


		Ingest ingest = externalDAO.getIngestMetaResult(nMasterID);
		das.getMetaDataInfo().setIngest(ingest);


		rMasterID = Long.parseLong(externalDAO.selectRelationMaster(nMasterID));
		// 관련 영상 Meta 관련 정보를 가져온다.
		if(nMasterID != rMasterID){
			Relation relation = externalDAO.getRelationMetaResult(rMasterID);
			if(relation != null)
				das.getMetaDataInfo().setRelation(relation);
		}


		Attach attach = externalDAO.getAttachResult(nMasterID);
		if(attach != null)
			das.getMetaDataInfo().setAttach(attach);


		Annot annot = externalDAO.getAnnotInfo(nMasterID);
		if(annot != null)
			das.getMetaDataInfo().setAnnot(annot);

		String xml = "";
		try {
			xml = convertorService.createMarshaller(das);

		} catch (JAXBException e) {
			logger.error("getBaseInfo Xml Create Error", e);
		}

		return xml;

	}



	/**
	 * 스토리지에 있는 존재하는 클립에 대해서 리스토어 요청시 DAS-TM에 전달 요청하게 된다.
	 * 풀다운로드 한건에 대해서 요청하게 된다.
	 * @param downCartDO_cartContDO
	 * @return CartContDO
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List insertStCartContInfoForList(DownCartDO downCartDO,CartContDO cartContDO) throws Exception
	{

		return externalDAO.insertStCartContInfoForList(downCartDO,cartContDO);

	}


	public String isVideoReleateYN(long master_id,long ct_id) throws Exception
	{

		return externalDAO.isVideoReleateYN(master_id,ct_id);
	}



	/**
	 * 시간대별 사용등급 MAPPGING 조회
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getRistClfInfoListForTime(Das das) throws Exception
	{
		String xml = "";

		for(TimeRistInfo info : das.getTimeRist().getItems()){
			xml =  externalDAO.getRistClfInfoListForTime(info);
		}
		return xml;
	}


	/**
	 * 시간대별 사용등급정보를 등록한다.
	 * @param downCartDO_cartContDO
	 * @return
	 * @throws Exception 
	 */
	public String insertRistClfInfoListForTime(Das das) throws Exception
	{

		String xml = "";
		for(TimeRistInfo info : das.getTimeRist().getItems()){

			List<TimeRistInfo> result = externalDAO.checkDuplication(info);

			if(result.size() == 0){
				xml = externalDAO.insertRistClfInfoListForTime(info);
			}else{
				XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
				Das xmlResult = new Das();
				for(TimeRistInfo resultInfo : result){
					TimeRist timeRist = new TimeRist();
					timeRist.setResult("fail");
					List<TimeRistInfo> resultList = new ArrayList<TimeRistInfo>();
					resultList.add(resultInfo);
					logger.debug("result" +resultInfo.getRistClfCd());
					timeRist.setItems(resultList);
					xmlResult.setTimeRist(timeRist);
				}
				xml = convertorService.createMarshaller(xmlResult);
			}

		}

		return xml;

	}

	/**
	 *  시간대별 사용등급정보를 수정한다.
	 * @param                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 */
	public String updateRistClfInfoListForTime(Das das) throws Exception
	{

		String xml = "";
		List<TimeRistInfo> dupleList = new ArrayList<TimeRistInfo>();
		for(TimeRistInfo info : das.getTimeRist().getItems()){

			//시간대를 수정하려한다면 중복체크를 한다.
			if(!StringUtils.isEmpty(info.getsTime()) || !StringUtils.isEmpty(info.geteTime())){

				List<TimeRistInfo> results = externalDAO.checkDuplication(info);


				if(results.size() ==0){
					xml = externalDAO.updateRistClfInfoListForTime(info);
				}else if(results.size() <= 1) {
					for(TimeRistInfo result : results){
						if(result.getSeq() == info.getSeq()){
							xml = externalDAO.updateRistClfInfoListForTime(info);
						}else{

							XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
							Das xmlResult = new Das();

							TimeRist timeRist = new TimeRist();
							timeRist.setResult("fail");

							for(TimeRistInfo duple : results){
								dupleList.add(duple);

								timeRist.setItems(dupleList);
								xmlResult.setTimeRist(timeRist);
							}
							xml = convertorService.createMarshaller(xmlResult);


						}
					}
				}else{
					XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
					Das xmlResult = new Das();

					TimeRist timeRist = new TimeRist();
					timeRist.setResult("fail");
					List<TimeRistInfo> resultList = new ArrayList<TimeRistInfo>();
					for(TimeRistInfo result : results){
						resultList.add(result);

						timeRist.setItems(resultList);
						xmlResult.setTimeRist(timeRist);
					}
					xml = convertorService.createMarshaller(xmlResult);
				}

			}else{
				xml = externalDAO.updateRistClfInfoListForTime(info);
			}

		}

		return xml;

	}

	/**
	 *  시간대별 사용등급정보를 삭제한다.
	 * @param                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 */
	public String deleteRistClfInfoListForTime(Das das) throws Exception
	{  

		String xml = "";
		for(TimeRistInfo info : das.getTimeRist().getItems()){
			xml = externalDAO.deleteRistClfInfoListForTime(info);
		}

		return xml;

	}


	/**
	 * 청구번호를 수기로 입력, 수정한다..
	 * @param xml                                                                                                              
	 * @return                                                                                                           
	 * @throws Exception 
	 * @throws DASException
	 */
	public String updateReqCd(Das das) throws Exception
	{

		String result="";
		int erpDuple = externalDAO.countErpReqCd(das.getMetaDataInfo().getReqCd());
		int dasDuple = externalDAO.countDasReqCd(das.getMetaDataInfo().getReqCd());

		if(logger.isDebugEnabled()) {
			logger.debug("###########################erpDuple "+erpDuple);
			logger.debug("###########################dasDuple "+dasDuple);
		}

		if(erpDuple == 1 && dasDuple ==0){
			result =externalDAO.updateReqCd(das);				
		}else{
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das rexml= new Das(); 
			MetaDataInfo metaDataInfo = new MetaDataInfo();
			metaDataInfo.setResult("fail");
			metaDataInfo.setReason("DAS DB에 중복된 청구번호가 존재합니다");
			rexml.setMetaDataInfo(metaDataInfo);
			result = convertorService.createMarshaller(rexml);
		}

		return result;

	}



	/**
	 * 스케쥴러의 작동시간 리스트를 본다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getSchedulerList() throws Exception
	{
		String xml =  externalDAO.getSchedulerList();

		return xml;

	}



	/**
	 * 청구번호 중복체크
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getDuplicateReqCd(Das das) throws Exception
	{
		String xml =  externalDAO.getDuplicateReqCd(das);

		return xml;

	}

}

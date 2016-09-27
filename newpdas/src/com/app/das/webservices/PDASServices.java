/***********************************************************
 * 프로그램ID  	: PDASServices.java
 * 프로그램명  	: PDASServices
 * 작성일자     	:
 * 작성자       	:
 * 설명         : WebService interface 프로그램
 * 변경이력
 * --------------------------------------------------------------------
 *    변경NO     변경일자        변경자                설   명
 * --------------------------------------------------------------------
 *    1          2008/01/04     ysk523               최초생성
 *    2			 2010/08/09     김동은                DAS2.0 전환 수정.
 ***********************************************************/
package com.app.das.webservices;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.app.das.business.CodeBusinessProcessor;
import com.app.das.business.CommunityBusinessProcessor;
import com.app.das.business.DisuseBusinessProcessor;
import com.app.das.business.EmployeeRoleBusinessProcessor;
import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.JNI_Des;
import com.app.das.business.LoginBusinessProcessor;
import com.app.das.business.SearchBusinessProcessor;
import com.app.das.business.StatisticsBusinessProcessor;
import com.app.das.business.SystemManageBusinessProcessor;
import com.app.das.business.WorkBusinessProcessor;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.dao.SystemManageDAO;
import com.app.das.business.dao.UserRoleDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.ArchiveReqDO;
import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.ContentsInfoDO;
import com.app.das.business.transfer.CopyInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.DeleteDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DownCartDO;
import com.app.das.business.transfer.DtlInfoDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.EquipMentInfoDO;
import com.app.das.business.transfer.ErpAppointDO;
import com.app.das.business.transfer.ErrorLogDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.business.transfer.GoodMediaDO;
import com.app.das.business.transfer.IfCmsArchiveDO;
import com.app.das.business.transfer.KeyFrameImgDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.ManagementInfoDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.ManualDeleteDO;
import com.app.das.business.transfer.MediaArchiveDO;
import com.app.das.business.transfer.MediaInfoDO;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.business.transfer.NdsDownDO;
import com.app.das.business.transfer.NleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.business.transfer.PathInfoDO;
import com.app.das.business.transfer.PdsArchiveDO;
import com.app.das.business.transfer.PdsDownDO;
import com.app.das.business.transfer.PdsMappDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotDownDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.business.transfer.PreProcessingDO;
import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.business.transfer.ScenarioDO;
import com.app.das.business.transfer.SearchConditionDO;
import com.app.das.business.transfer.ServersDO;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.business.transfer.StorageDO;
import com.app.das.business.transfer.SubsiInfoDO;
import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.business.transfer.TcBeanDO;
import com.app.das.business.transfer.TodayDO;
import com.app.das.business.transfer.TokenDO;
import com.app.das.business.transfer.TotalChangeInfoDO;
import com.app.das.business.transfer.TransferDO;
import com.app.das.business.transfer.UseInfoDO;
import com.app.das.business.transfer.VideoPageInfoDO;
import com.app.das.business.transfer.VideoPageMetaInfoDO;
import com.app.das.business.transfer.WmvH264DO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.log.DasPropHandler;
import com.app.das.services.*;
import com.app.das.util.CalendarUtil;
import com.app.das.util.CommonUtl;
import com.app.das.util.XmlUtil;
import com.konantech.search.data.ParameterVO;
import com.sbs.das.web.NevigatorProxy;


public class PDASServices {
	//private static PDASServices instance;
	private Logger logger = Logger.getLogger(PDASServices.class);
	private static ExternalDAO externalDAO = ExternalDAO.getInstance();
	private static SystemManageDAO systemManageDAO = SystemManageDAO.getInstance();
	private static final UserRoleDAO userRoleDAO = UserRoleDAO.getInstance();
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();

	/**
	 * PDASServices WebService 시작 전 전처리
	 * - metadat_mst_tbl.lock_stat_cd 'N' SET
	 * jeus로딩시 db connection 문제로 lock_stat_cd의 값을 수정하는 것은 로그인후 권한 정보를 받아오는 시점에서 풀어준다.
	 */
/*
	static{
		PDASInit.init();
	}
*/
	/**
	 * 테스트 인터페이스 메소드
	 * 
	 * @param name 테스트 String INPUT param
	 * @return param+"한글"
	 */
	public String getString(String name){

		CommonUtl commonUtl = new CommonUtl();
		String timecode=commonUtl.sendTimecode(Long.parseLong(name));

		try {
			Thread.sleep(100000L);
		} catch (Exception e) {}

		return timecode;

	}

	/**
	 * 테스트 인터페이스 메소드 
	 * @param bill 테스트 int INPUT param
	 * @return
	 */
	public int getBill(int bill){
		return bill+1;
	}

	/**
	 * 암호화 테스트 인터페이스
	 * @param strSSOKey 키값
	 * @param strMacHex 헥스값
	 * @param strSSOText token_text 값(예 : 토큰 스티링)
	 * @return
	 * @throws RemoteException
	 */
	public String getDecryption(String strSSOKey,String strMacHex,String  strSSOText)throws RemoteException{
		JNI_Des hj = new JNI_Des();
		String strSSOEnc= "";
		logger.debug("JNI_Des===============>"+hj);
		try{ 
			/**
			 * getDescryption ( Key : Hex : Text)
			 */
			strSSOEnc  =hj.getDecryption(strSSOKey, strMacHex, strSSOText);
		}catch(Exception e ){
			logger.error("getDecryption error", e);
		}
		return strSSOEnc;
	}

	/**
	 * 복호화 인터페이스 메소드
	 * @param strSSOKey
	 * @param strMacHex
	 * @param strSSOEnc
	 * @return
	 * @throws RemoteException
	 */
	public String getEncription(String strSSOKey,String strMacHex,String  strSSOEnc)throws RemoteException{
		//		 JNI_Des hj = JNI_Des.getInstance();
		JNI_Des hj = new JNI_Des();
		String strSSOText= "";
		logger.debug("JNI_Des===============>"+hj);
		try{ 
			/**
			 * setEncryption( Key : Hex : Text )
			 */
			strSSOText  =hj.setEncryption(strSSOKey, strMacHex, strSSOEnc);
		}catch(Exception e ){
			logger.error("getEncription error", e);
		}
		return strSSOText;
	}

	/**
	 * AD 인증 인터페이스 메소드
	 * @param ID
	 * @param password
	 * @param AD_DOMAIN
	 * @return
	 * @throws RemoteException
	 */
	public String getAuthentication(String ID,String password,String  AD_DOMAIN)throws RemoteException{
		JNI_Des hj = new JNI_Des();
		String strSSOText= "";
		try{ 
			/**
			 * getAuthentication( ID : password : domain)
			 */
			strSSOText  =hj.getAuthentication(ID, password, AD_DOMAIN);
		}catch(Exception e ){
			logger.error("getAuthentication error", e);
		}
		return strSSOText;
	}

	/**
	 * 로그인 서비스 (das 1.0 로그인 서비스 현재 사용하지 않는 로직)
	 * @param userId                                                                                                                                                                                
	 * @param password                                                                                                                                                                               
	 * @return                                                                                                                                                       
	 * @throws Exception 
	 */
	@Deprecated
	public String getLogin(String userId, String password)
			throws Exception {
		logger.info("######getLogin########" + "userId : " + userId + "passwd : " + password);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String str = _processor.loginService(userId, password);
			logger.debug("getLogin Call End return =["+str+"]");
			return str;
		} catch (Exception e) {
			logger.error("getLogin error", e);
		}
		logger.info("######getLogin########" + "userId : " + userId + "passwd : " + password);
		return null;
	}

	/**
	 * 카트정보를 조회한다(다운로드 카트정보, 카트내용정보)
	 * 로그인시 db에 다운로드 요청 대기중인 건이 있는지 조회하고 있다면 해당 카드 정보를 불어러온다
	 * @param cartNo                                                                                                                                                                                            
	 * @param reqUserId                                                                                                                                                                                              
	 * @return                                                                                                            
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getCartInfo(long cartNo, String reqUserId)
			throws Exception {
		logger.info("######getCartInfo########" + "cartNo : " + cartNo + ", reqUserId : " + reqUserId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			DownCartDO _infoList = _processor.getCartInfo(cartNo, reqUserId);

			StringBuffer buff = new StringBuffer();
			buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");


			DownCartDOXML _do = new DownCartDOXML();
			_do.setDO(_infoList);

			buff.append(_do.getSubXML());


			buff.append("</das>");
			return buff.toString();

		} catch (Exception e) {
			logger.error("getCartInfo error", e);
		}
		return "";
	}
	/**
	 * 프로그램 정보를 조회한다.(das 1.0 소스 현재 사용하지 않음)
	 * @param masterId                                                                                                                                                                                    
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 * @throws DASException
	 */

	public String getVideoPageInfo(long masterId) throws Exception {
		logger.info("######getVideoPageInfo########"  + " masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			VideoPageInfoDO _returnDO = _processor.getVideoPageInfo(masterId);
			if (_returnDO != null) {
				VideoPageInfoDOXML _returnDoXML = new VideoPageInfoDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("getVideoPageInfo error", e);
		}
		return null;
	}


	/**
	 * 입력받은 콘텐트 ID로 파일 경로 정보를 가저온다(das 1.0 소스 현재 사용하지 않음)
	 * 검색영상과 스토리보드 파일 경로를 가져온다.
	 * @param contentId                                                                                                                                                                       
	 * @return                                                                                                                                                                         
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getFilePathInfo(long contentId) throws Exception {
		logger.info("######getFilePathInfo########" + " contentId : " + contentId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			PathInfoDO _returnDO = _processor.getFilePathInfo(contentId);
			if (_returnDO != null) {
				PathInfoDOXML _returnDoXML = new PathInfoDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("getFilePathInfo error", e);
		}
		return null;
	}


	/**
	 * 프로그램 정보를 조회한다. 
	 * 입력받은 master_id의 제목,최종방송일, 대,중,소분류, 회차, 청구번호 정보를 조회한다.
	 * @param masterId                                                                                                                                                                                     
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getVideoPageMetaInfo(long masterId) throws Exception {
		logger.info("######getVideoPageMetaInfo########"  + " masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			VideoPageMetaInfoDO _returnDO = _processor
					.getVideoPageMetaInfo(masterId);
			if (_returnDO != null) {
				VideoPageMetaInfoDOXML _returnDoXML = new VideoPageMetaInfoDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("getVideoPageMetaInfo error", e);
		}
		return null;
	}

	/**
	 * 프로그램 콘텐트 정보를 조회한다.
	 * 입력받은 master_id의 소속된 ct_id, 화면비, 화질, 길이 정보를 조회한다.
	 * @param masterId                                                                                                                                                                         
	 * @return                                                                                                                                                                         
	 * @throws Exception 
	 * @throws DASException
	 */

	public String getVideoPageContentsInfoList(long masterId)
			throws Exception {
		logger.info("######getVideoPageContentsInfoList########"  + " masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getVideoPageContentsInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					VideoPageContentInfoDOXML _do = new VideoPageContentInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}
				_xml.append("</das>"); 
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getVideoPageContentsInfoList error", e);
		}
		return "";
	}

	/**
	 * 공지사항을 신규로 쓴다.
	 * 팝업여부를 설정하고, 기간을 설정할수 있다.
	 * @param boardInfoDO 공지사항 정보를 가지고 있는 beans                                                                                                                                                                                             
	 * @return  성공 1 실패 0                                                                                                                                                                                            
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertBoardInfo(String boardInfoDO) throws Exception{

		logger.info("######insertBoardInfo######## boardInfoDO : "+boardInfoDO);
		try {
			BoardInfoDOXML _doXML = new BoardInfoDOXML();
			BoardDO _do = (BoardDO) _doXML.setDO(boardInfoDO);		
			CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();

			return _processor.insertBoardInfo(_do);
		} catch (Exception e) {
			logger.error("insertBoardInfo error", e);
		}
		logger.debug("######end insertBoardInfo########");
		return 0;

	}

	/**
	 * 공지사항을 수정한다.
	 * 공지사항 내용을 수정하며, 첨부파일을 등록할수있다.
	 * @param boardDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateBoardInfo(String boardInfoDO) throws Exception{

		logger.info("######updateBoardInfo######## boardInfoDO: "+boardInfoDO);
		try {
			BoardInfoDOXML _doXML = new BoardInfoDOXML();
			BoardDO _do = (BoardDO) _doXML.setDO(boardInfoDO);		
			CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();

			return _processor.updateBoardInfo(_do);
		} catch (Exception e) {
			logger.error("updateBoardInfo error", e);
		}
		logger.debug("######end updateBoardInfo########");
		return 0;

	}

	/**
	 * 공지사항을 삭제한다.
	 * 등록되어있는 정보를 삭제한다. 만약 첨부파일이 존재한다면 같이 삭제한다.
	 * @param boardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public void deleteBoardInfo(int BoardId) throws Exception{

		logger.info("######deleteBoardInfo######## BoardId : " + BoardId);
		//BoardInfoDOXML _doXML = new BoardInfoDOXML();

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();
		try {
			_processor.deleteBoardInfo(BoardId);
		} catch (Exception e) {
			logger.error("deleteBoardInfo error", e);
		}
		logger.debug("######end deleteBoardInfo########");


	}

	/**
	 * 공지사항을 조회한다.
	 * 등록된 공지사항 단일건에 대해서 상세 정보를 조회한다.
	 * @param boardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getBoardInfo(String boardDO) throws Exception{

		logger.info("######getBoardInfo######## boardDO: "+boardDO);
		try { 
			CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();

			BoardInfoDOXML _doXML = new BoardInfoDOXML();

			BoardDO _do = (BoardDO) _doXML.setDO(boardDO);

			List _infoList = _processor.getBoardInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					BoardInfoDOXML _do2 = new BoardInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getBoardInfo error", e);
		}
		logger.debug("######end getBoardInfo########");
		return "";
	}

	/**
	 * 프리뷰노트를 조회한다.
	 * master_id에 소속되어있는 프리뷰의 정보를 조회한다.
	 * @param previewDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                                
	 * @throws Exception 
	 * @throws DASException  
	 */
	@Deprecated
	public String getPreviewInfo(int master_id) throws Exception{

		logger.info("######getPreviewInfo######## master_id : " + master_id);

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();
		try {
			List _infoList = _processor.getPreviewInfo(master_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					PreviewDOXML _do = new PreviewDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPreviewInfo error", e);
		}
		logger.debug("######end getgetPreviewInfo########");
		return "";
	}




	/**
	 * 프리뷰 첨부파일 정보를 조회한다
	 * 프리뷰에 소속되어있는 첨부파일 정보를 조회한다.
	 * @param master_id                                                                                                                                                                                              
	 * @return  xml                                                                                                                                                                                          
	 * @throws Exception 
	 * @throws DASException  
	 */
	@Deprecated
	public String getPreviewAttachInfo(int master_id) throws Exception{

		logger.info("######getPreviewAttachInfo######## master_id : " + master_id);

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();
		try {
			List _infoList = _processor.getPreviewAttachInfo(master_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					PreviewAttachDOXML _do = new PreviewAttachDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				} 
				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}  
		} catch (Exception e) {
			logger.error("getPreviewAttachInfo error", e);
		}
		logger.debug("######end getPreviewAttachInfo########");
		return "";
	}


	/**
	 * 에러정보 등록 및 갱신한다.
	 * @param errorRegisterDO                                                                                                                                    
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String insertErrorInfo(String errorRegisterDO)
			throws Exception {
		logger.info("######insertErrorInfo######## errorRegisterDO : " + errorRegisterDO);
		try {
			ErrorRegisterDOXML _doXML = new ErrorRegisterDOXML();
			ErrorRegisterDO _do = (ErrorRegisterDO) _doXML.setDO(errorRegisterDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			ErrorRegisterDO _returnDO = _processor.insertErrorInfo(_do);
			if (_returnDO != null) {
				ErrorRegisterDOXML _returnDoXML = new ErrorRegisterDOXML();
				_returnDoXML.setDO(_returnDO);
				logger.debug("@@@"+_returnDoXML.toXML());
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("insertErrorInfo error", e);
		}
		logger.debug("######end insertErrorInfo########");
		return "";
	}

	/**
	 * 사진 정보를 저장한다.
	 * @param photoInfoDO                                                                                                                        
	 * @return                                                                                                                         
	 * @throws Exception 
	 * @throws DASException
	 */ 
	public int insertPhotoinfo(String photoInfoDO) throws Exception{

		logger.info("######insertPhotoinfo######## photoInfoDO: " + photoInfoDO );
		try {
			AttatchPhotoInfoDOXML _doXML = new AttatchPhotoInfoDOXML();
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photoInfoDO);		
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertPotoInfo(_do);
		} catch (Exception e) {
			logger.error("insertPhotoinfo error", e);
		} 
		logger.debug("######endinsertPhotoinfo########");
		return 0; 

	}

	/**
	 * 코너 정보를 저장한다.
	 * 바뀐 코너정보를 저장하기위해서 기존의 코너정보를 일괄적으로 삭제를 한뒤 재입력하는 로직을 거친다.
	 * 재입력후 바뀐 코너정보를 다시 클라이언트에게 넘겨준다.
	 * @param masterId                                                                                                                         
	 * @param cornerInfoDO                                                                                                                         
	 * @return                                                                                                                         
	 * @throws Exception 
	 * @throws DASException
	 */
	public String insertCornerinfo(long masterId, String cornerInfo)
			throws Exception {
		long sTime1 = System.currentTimeMillis();
		long sTime2;
		logger.info("######insertCornerinfo######## masterId: " + masterId +" cornerInfo: " + cornerInfo );
		try {
			CornerInfoDOXML _doXML = new CornerInfoDOXML();
			List _list = (List) _doXML.setDO(cornerInfo);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.insertCornerinfo(masterId, _list);

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CornerInfoDOXML _do = new CornerInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				logger.debug("[insertCornerinfo][ouput]"+_xml.toString());

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("insertCornerinfo error", e);
		}
		logger.debug("######end insertErrorInfologger########");
		return "";
	}

	/**
	 * 콘텐트 멥 정보를 저장한다.
	 * 메타데이터와 컨텐츠, 코너간의 key 값을 mapping하는 테이블에 정보를 저장한다.
	 * 코너입력과 마찬가지로 기존정보를 삭제후 재입력하게 되며, 순서는
	 * corner_tbl, contents_mapp_Tbl 순으로 저장되게 된다.
	 * @param masterId                                                                                                                         	
	 * @param contentMappInfoDO                                                                                                                        
	 * @return                                                                                                                        
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertContentsMappinfo(long masterId, String contentMappInfo)
			throws Exception {
		logger.info("######insertContentsMappinfo######  contentMappInfo : " +contentMappInfo + " masterId : " + masterId);


		try {
			ContentMappInfoDOXML _doXML = new ContentMappInfoDOXML();
			List _list = (List) _doXML.setDO(contentMappInfo);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();


			int result = _processor.insertContentsMappinfo(masterId, _list);

			return result;
		} catch (Exception e) {
			logger.error("insertContentsMappinfo error", e);
		}
		return 0;
	}

	/**
	 * 내 목록 담기 내용을 등록한다.
	 * 작업하고자하는 영상을 계정별로 미리 담아두는 로직으로 정보확인은 mypage에서 내목록 확인에서 확인할수있다.
	 * master_id, 간략한 메타 정보를 저장하는 로직이며 실제 메타정보에 영향을 끼치는 일은 없다.
	 * @param myCatalogDO 내 목록 담기 내용 정보
	 * @return
	 * @throws Exception 
	 */
	public int insertMyCatalog(String myCatalogDO)throws Exception {
		logger.info("##### insertMyCatalog start#####  myCatalogDO: "+myCatalogDO);
		try {
			MyCatalogInfoDOXML _doXML = new MyCatalogInfoDOXML();
			//MyCatalogDO _do = (MyCatalogDO) _doXML.setDO(myCatalogDO);
			List _result = (List)_doXML.setDO(myCatalogDO);	
			SearchBusinessProcessor _processor = new SearchBusinessProcessor();

			return _processor.insertMyCatalog(_result);
		} catch (Exception e) {
			logger.error("insertMyCatalog error", e);
		}
		return 0;
	}    

	/**
	 * 내 목록 담기 내용을 삭제한다.
	 * 
	 * @param myCatalogDO 내 목록 내용 정보
	 * @return
	 * @throws Exception 
	 */
	public int deleteMyCatalog(String myCatalogDO)throws Exception {
		logger.info("##### deleteMyCatalog ##### myCatalogDO : " + myCatalogDO);
		try {
			MyCatalogInfoDOXML2 _doXML = new MyCatalogInfoDOXML2();

			MyCatalogDO _do = (MyCatalogDO) _doXML.setDO(myCatalogDO);
			SearchBusinessProcessor _processor = new SearchBusinessProcessor();

			return _processor.deleteMyCatalogInfo(_do);
		} catch (Exception e) {
			logger.error("deleteMyCatalog error", e);
		}
		return 0;
	}

	/**
	 * 주제영상 및 사용제한 등급를 저장한다.
	 * 저장시에는 cn_id정보가 바뀌게 되므로 클라이언트로부터 바뀐 cn_id정보를 넘겨받아 다시 저장한다.
	 * corner_tbl, contents_mapp_Tbl과 동일할 로직으로 기존 정보를 모두 삭제후 다시 입력하는 방식으로
	 * 되어있다. 주제영상 저장시에는gubun 값이 S 사용등급 지정시에는 L로 저장된다.
	 * 하나의 코너에 다수의 사용등급, 주제영상이 엮일수도있으며 한건만이 지정될수도 있다.
	 * 저장이 완료되면 완료된 정보를 xml로 클라이언트에 다시넘겨준다.
	 * @param annotInfoDO                                                                                                                                    	
	 * @throws DASException
	 */
	public String insertAnnotinfo(long masterId, String annotInfoDO)
			throws RemoteException {
		logger.info("######insertAnnotinfo######## annotInfoDO : " + annotInfoDO + " masterId : " + masterId);
		try {
			AnnotInfoDOXML _doXML = new AnnotInfoDOXML();
			List _list = (List) _doXML.setDO(annotInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.insertAnnotinfo(masterId, _list);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AnnotInfoDOXML _do = new AnnotInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("insertAnnotinfo error", e);
		}
		return "";
	}

	/**
	 * 코드 정보를 조회한다.
	 * 입력 CLF_CD값이 없다면 주제영상과 사용등급에 대한 정보를 조회하고,
	 * 있다면 해당 CLF_CD에 소속된 코드리스트를 조회한다.
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws Exception 
	 */
	public String getCodeList(String codeDO)throws Exception{
		logger.info("######getCodeList######## codeDO : " + codeDO);
		try {
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();
			CodeDOXML _doXML = new CodeDOXML();

			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);


			List _infoList = _processor.getCodeList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//		if (logger.isDebugEnabled())
				//			logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getCodeList error", e);
		}
		logger.debug("#####endgetCodeList########");
		return "";
	}

	/**
	 *  특정 코드 정보를 조회한다.
	 *  clf_cd P018에 소속된 코드의 상세 정보를 조회한다.
	 *  
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws Exception 
	 */
	public String getCodeInfo(String codeDO)throws Exception{
		logger.info("#####getCodeInfo###### codeDO : " + codeDO);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		try {
			CodeDOXML _doXML = new CodeDOXML();

			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);


			CodeDO _infoList = _processor.getCodeInfo(_do);

			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			CodeDOXML _do2 = new CodeDOXML();
			_do2.setDO(_infoList);
			_xml.append(_do2.getSubXML());

			_xml.append("</das>");

			return _xml.toString();

		} catch (Exception e) {
			logger.error("getCodeInfo error", e);
		}
		return "";
	}

	/**
	 * 코드 정보를 등록한다.
	 * 입력 컬럼중 gubun이 "" 아니라면 주제영상, 사용등급 코드 저장,
	 * 입력 컬럼중 clf_cd의 값이 P065라면 채널 코드 입력
	 * 위두가지에 모두 해당되지 않는다면 기타 코드 입력이다.
	 * @param codeDO
	 * @return
	 * @throws Exception 
	 */
	public int insertCodeInfo(String codeDO)throws Exception{
		logger.info("#####insertCodeInfo##### codeDO: " + codeDO );
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO)_doXML.setDO(codeDO);
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return _processor.insertCodeInfo(_do);
		} catch (Exception e) {
			logger.error("insertCodeInfo error", e);
		}
		return 0;
	}


	/**
	 * 코드 정보를 수정한다.
	 * 만약 gubun값이 S라면 코드의 우선순위 조정을 위해서 RMK_1 컬럼의 값을 수정하며
	 * L이라면 해당 컬럼을 공백으로 저장한다.
	 * @param codeDO
	 * @return
	 * @throws Exception 
	 */
	public int updateCodeInfo(String codeDO)throws Exception{
		logger.info("#####updateCodeInfo##### codeDO : "+ codeDO );
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO)_doXML.setDO(codeDO);
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return _processor.updateCodeInfo(_do);
		} catch (Exception e) {
			logger.error("updateCodeInfo error", e);
		}
		return 0;
	}



	/**
	 * 코드 정보를 삭제한다.
	 * @param codeDO
	 * @return
	 * @throws Exception 
	 */
	public int deleteCodeInfo(String codeDO)throws Exception{
		logger.info("#####deleteCodeInfo##### codeDO : " + codeDO);
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO)_doXML.setDO(codeDO);
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return _processor.deleteCodeInfo(_do);
		} catch (Exception e) {
			logger.error("deleteCodeInfo error", e);
		}
		return 0;
	}

	/**
	 * 대본정보를 등록한다.
	 * 대본정보가 긴 경우에는 분할을하여서 저장을 하면 한번에 저장되는 분략은 약 5만자 정도이다.
	 * 대본정보를 저장하기전에 SEQ로 해당 row를 삭제후 다시 저장하는 방식을 취하며
	 * 저장이 완료된 이 이후에는 검색엔진에 해당 내용을 업데이트 하기위해서 프로시져를 호출한다.
	 * @param masterId
	 * @param scenarioDO
	 * @return
	 * @throws Exception 
	 */
	public int insertScenario(String scenarioDO)throws Exception{
		logger.info("#####insertScenario#####  scenarioDO: "    + scenarioDO);
		try {
			String scenario = scenarioDO;
			ScenarioDOXML _doXML = new ScenarioDOXML();
			ScenarioDO _do = (ScenarioDO)_doXML.setDO(scenario);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertScenario(_do);
		} catch (Exception e) {
			logger.error("insertScenario error", e);
		}
		return 0;
	}


	/**
	 * 대본정보를 삭제한다.
	 * @param masterId
	 * @param scenarioDO
	 * @return
	 * @throws Exception 
	 */
	public int deleteScenario(long master_id)throws Exception{
		logger.info("#####deleteScenario##### master_id :" + master_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteScenario(master_id);
		} catch (Exception e) {
			logger.error("deleteScenario error", e);
		}
		return 0;
	}

	/**
	 * 관련영상 정보를 등록한다.
	 * 등록된 관련영상은 영상선정, 지난 정보조회, 관련영상 등록 정보에 더이상 조회가 되지 않으며
	 * 검색엔진의 경우 벌크색인이 끝나면 더이상 조회가 되지 않는다.
	 * @param parent_master_id 부모 마스터 정보
	 * @param child_master_id 관련영상 마스터 정보
	 * @return
	 * @throws RemoteException
	 */
	public int insertRelationMaster(long parent_master_id,long child_master_id)throws RemoteException{
		logger.info("#####insertRelationMaster##### parent_master_id : " + parent_master_id + ", child_master_id : " + child_master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.insertRelationMaster(parent_master_id, child_master_id);
		} catch (Exception e) {
			logger.error("insertRelationMaster error", e);
		}
		return 0;
	}

	/**
	 * 관련영상 정보를 삭제한다.
	 * 관련영상으로 등록되어있는 정보를 삭제한다. 이후 기존에 검색되지 않았던 화면에서 조회가 된다.
	 * @param parent_master_id  부모 마스터 정보
	 * @param child_master_id 관련영상 마스터 정보
	 * @return
	 * @throws RemoteException
	 */
	public int deleteRelationMaster(long parent_master_id,long child_master_id)throws RemoteException{
		logger.info("#####deleteRelationMaster##### parent_master_id : " + parent_master_id + ", child_master_id : " + child_master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteRelationMaster(parent_master_id, child_master_id);
		} catch (Exception e) {
			logger.error("deleteRelationMaster error", e);
		}
		return 0;
	}

	/**
	 * 관련영상 마스터 ID를 전달한다.
	 * 입력된  master_id에 소속되어있는 master_id 정보를 리턴한다.기본정보 조회시 같이 조회되며, 만약 리턴값이 존재한다면
	 * 해당 master_id의 스토리보드 정보를 조회하여 화면에 뿌려주게 된다.
	 * @param masterId 부모 마스터 정보
	 * @return  Child_master_id
	 * @throws Exception 
	 */
	public String getRelationMaster(long masterId)throws Exception{
		logger.info("#####getRelationMaster##### : " + masterId);
		ExternalBusinessProcessor _prosessor = new ExternalBusinessProcessor();
		try{
			return _prosessor.getRelationMaster(masterId);
		}catch(Exception e){
			logger.error("getRelationMaster error", e);
		}
		return "";
	}

	/**
	 * 관련영상 마스터 데이타 조회한다.
	 * @param masterId 마스터 아이디로 관련영상 mapping 정보를 조회한다
	 * @return
	 * @throws Exception 
	 */
	public String getRelationTotaly(long masterId)throws Exception{
		logger.info("#####getRelationTotaly##### : " + masterId);
		ExternalBusinessProcessor _prosessor = new ExternalBusinessProcessor();
		try{
			return _prosessor.getRelationTotaly(masterId);
		}catch(Exception e){
			logger.error("getRelationTotaly error", e);
		}
		return "";
	}



	/**
	 * 대본정보를 조회한다.
	 * 입력된 master_id에 등록된 모든 대본정보를 조회한다.(das2.0 초기 방식)
	 * 이후 대본길이가 지나치게 길 경우 초과되는 부분에 대해서 정보가 누락되는 현상이 발생해 현재는 사용하지 않는함수
	 * getScenario2()함수로 기능 대체
	 * @param masterId
	 * @return
	 * @throws Exception 
	 */
	@Deprecated
	public String getScenario(long masterId)throws Exception{
		logger.info("#####getScenario###### : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getScenario(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ScenarioDOXML _do = new ScenarioDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getScenario error", e);
		}
		return "";
	}


	/**
	 * 대본정보를 조회한다.
	 * getScenario() 함수에서 생긴 문자열 누락 형상때분에 수정로직으로 구현된 함수
	 * page 처리가 되어있으며, 그 순서는 seq로 순서를 구분한다.
	 * @param masterId
	 * @return
	 * @throws Exception 
	 */
	public String getScenario2(String senario)throws Exception{
		logger.info("#####getScenario2######" + senario);
		try {
			ScenarioDOXML _doXML = new ScenarioDOXML();
			ScenarioDO _do = (ScenarioDO) _doXML.setDO(senario);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();


			ScenarioDO _infoList = _processor.getScenario2(_do);

			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			ScenarioDOXML _do2 = new ScenarioDOXML();
			_do2.setDO(_infoList);
			_xml.append(_do2.getSubXML());

			_xml.append("</das>");

			return _xml.toString();

		} catch (Exception e) {
			logger.error("getScenario2 error", e);
		}
		return "";
	}

	/**
	 * 마이워크 페이지(정렬옵션)
	 * 내목록 페이지를 조회시 정렬값을 추가한 함수.
	 * 
	 * @param myCatalogDO
	 * @return
	 * @throws Exception 
	 */
	public String getMyCatalogBySort(String myCatalogDO)throws Exception{
		try {
			SearchConditionDOXML _doXML1 = new SearchConditionDOXML();
			DASCommonDOXML _doXML2 = new DASCommonDOXML();
			SearchConditionDO searchConditionDO = (SearchConditionDO) _doXML1.setDO(myCatalogDO);
			DASCommonDO commonDO = (DASCommonDO) _doXML2.setDO(myCatalogDO);

			SearchBusinessProcessor _processor = new SearchBusinessProcessor();


			String _xml = "";
			_xml = XmlUtil.getToXmlXstream(_processor.getMyCatalogList(searchConditionDO, commonDO));
			//	logger.debug("getMyCatalog [output_xml}"+_xml);
			return _xml;
		} catch (Exception e) {
			logger.error("getMyCatalogBySort error", e);
		}
		return "";
	}

	/**
	 * 마이워크 페이지
	 * 내목록 페이지를 조회  함수.
	 * 
	 * @param myCatalogDO
	 * @return
	 * @throws Exception 
	 */
	public String getMyCatalog(String myCatalogDO)throws Exception{
		if(logger.isDebugEnabled()) {
			logger.debug("getMyCatalog xml: "+myCatalogDO);
		}
		SearchConditionDOXML _doXML1 = new SearchConditionDOXML();
		DASCommonDOXML _doXML2 = new DASCommonDOXML();
		try {
			SearchConditionDO searchConditionDO = (SearchConditionDO) _doXML1.setDO(myCatalogDO);
			DASCommonDO commonDO = (DASCommonDO) _doXML2.setDO(myCatalogDO);

			SearchBusinessProcessor _processor = new SearchBusinessProcessor();


			String _xml = "";
			_xml = XmlUtil.getToXmlXstream(_processor.getMyCatalogList(searchConditionDO, commonDO));
			//logger.debug("getMyCatalog [output_xml}"+_xml);
			return _xml;
		} catch (Exception e) {
			logger.error("getMyCatalog error", e);
		}
		return "";
	}

	/**
	 * Photo 파일 항목을 삭제한다.
	 * master_id별로 등록되어있는 사진 정보를 삭제한다. 
	 * 등록된 사진 정보는 다른 master_id에 연관하여 등록할수 있으므로 delete문으로 삭제하는 것이 아닌 del_yn 를 업데이트하여 해당
	 * master_id에서만 사용이 않되도록 수정되게 한다. 만약 삭제 요청 한 master_id가 사진 정보를 사용하는 마지막 master_id 라면
	 * 사진 메타 정보에서 더이상 사용않함으로 값을 업데이트를 해준다.
	 * 
	 * @param photoInfo       삭제할 사진 정보                                                                                                            	
	 * @throws Throwable 
	 * @throws DASException
	 */
	public String deletePhotoFiles(String photoInfo) throws Throwable {
		logger.info("######deletePhotoFiles######## : " + photoInfo);
		PhotoDOXML _doXML = new PhotoDOXML();
		try {
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photoInfo);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.deletePhoto(_do);
		} catch (Exception e) {
			logger.error("deletePhotoFiles error", e);
		}
		return "";
	}

	/**
	 * 첨부파일 등록
	 * 첨부파일은 신규로 등록한다. 첨부파일의 경로, 파일명, 첨부파일 유형 정보를 저장하며,
	 * 첨부파일 유형이 preview인 경우에는 /mp4/previewnote/YYYYMM/DD/ 밑으로 파일이 저장되며,
	 * 그이외인 경우에는 /mp4/attach/첨부파일유형코드/001/ 밑으로 파일이 저장된다.
	 * @param annotInfoDO 저장할 주석 정보                                                                                                                                    	
	 * @throws Exception 
	 * @throws DASException
	 */
	public String insertAttachFile(String attachFileInfo)
			throws Exception {
		logger.info("######insertAttachFile######## : " + attachFileInfo);
		AttachFileInfoDOXML _doXML = new AttachFileInfoDOXML();
		try {
			List _list = (List) _doXML.setDO(attachFileInfo);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.insertAttachFile(_list);
			if (_infoList != null && _infoList.size() > 0) {
				String _xml = "";
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AttachFileInfoDOXML _do = new AttachFileInfoDOXML();
					_do.setDO(_iter.next());
					_xml = _xml + _do.getSubXML();
				}
				_xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>"
						+ _xml;
				_xml = _xml + "</das>";
				return _xml;
			}
		} catch (Exception e) {
			logger.error("insertAttachFile error", e);
		}
		return "";
	}

	/**
	 * 다운카트의 상태를 갱신한다.das 1.0
	 * @param cartNo                                                                                                                                                                         
	 * @param cartState                                                                                                                                                                         
	 * @return                                                                                                                                     
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public int updateDownCartState(long cartNo, String cartState, String title)
			throws Exception {
		logger.info("######updateDownCartState######## :  cartNo : " + cartNo + " cartState : " + cartState + " title : " + title );
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateDownCartState(cartNo, cartState, title);
		} catch (Exception e) {
			logger.error("updateDownCartState error", e);
		}
		return 0;
	}

	/**
	 * 다운카트의 상태를 갱신한다. DAS2.0
	 * 다운로드 요청 시점에 2가지 유형으로 다운로드가 구분된다. 요청시점해 해당 영상이 스토리지에 존재한다면
	 * DIVA에 다운로드 요청을 하지않고 스토리지 다운로드를 진행하며, 그렇지않은 경우는 DIVA에 다운로드 요청을 한다.
	 * 만약 요청건이 승인이 필요한 경우에는 승인 대기 상태로 승인자의 승인이 있을때까지 대기하며 무제한인 경우는
	 * 다운로드를 요청한다.
	 * @param downCartDO
	 * @return
	 * @throws Exception 
	 */
	public int updateDownCart(String downCartDO)throws Exception{
		logger.info("#####updateDownCart start#####" + downCartDO);
		try {
			DownCartDOXML _doXML = new DownCartDOXML();
			DownCartDO _do = (DownCartDO) _doXML.setDO(downCartDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateDownCart(_do);
		} catch (Exception e) {
			logger.error("updateDownCart error", e);
		}
		return 0;

	}

	/**
	 * 카트 아이템에 대한 정보 수정
	 * 각각의 다운로드 요청건에 대해서 요청사유를 업데이트한다.
	 * @param cartContDO 카트에 대한 내용 정리
	 * @return
	 * @throws Exception 
	 */
	public int updateCartContInfo(String cartContDO)throws Exception{
		logger.info("#####updateCartContInfo#####" + cartContDO);
		try {
			CartContsDOXML _doXML = new CartContsDOXML();
			List _result = (List)_doXML.setDO(cartContDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			_processor.updateCartContInfo(_result); 
		} catch (Exception e) {
			logger.error("updateCartContInfo error", e);
		} 
		return 0;
	}


	/**
	 * 스토리지에 있는 클립에 대한 전송 요청을 받을 수 있는 풀다운로드 요청 카트 아이템에 대한 정보 수정
	 * 각각의 다운로드 요청건에 대해서 요청사유를 업데이트한다.
	 * @param cartContDO 카트에 대한 내용 정리
	 * @return
	 * @throws Exception 
	 */
	public int updateStCartContInfo(String cartContDO)throws Exception{
		logger.info("#####updateCartContInfo##### cartContDO : " + cartContDO);
		try {
			CartContsDOXML _doXML = new CartContsDOXML();
			List _result = (List)_doXML.setDO(cartContDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			_processor.updateStCartContInfo(_result); 
		} catch (Exception e) {
			logger.error("updateStCartContInfo error", e);
		} 
		return 0;
	}

	/**
	 * 마스터 테이블 상태코드를 갱신한다.
	 * secArchNm값이존재할영우는 검수완료를.
	 * 그이외에는 정리 완료로 메타의 데이터의 상태값을 변경한다.
	 * @param masterID                                                                                                                              		
	 * @param secArchId                                                                                                                             		
	 * @param secArchNm                                                                                                                             		                                      
	 * @return                                                                                                                                     
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateDatastatCd(long masterID, String secArchId,
			String secArchNm) throws Exception {
		logger.info("######updateDatastatCd######## secArchId : " + secArchId + " secArchNm : " + secArchNm + ", masterID : " + masterID);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateDatastatCd(masterID, secArchId, secArchNm);
		} catch (Exception e) {
			logger.error("updateDatastatCd error", e);
		}
		return 0;
	}

	/**
	 * 콘텐츠 테이블 화질코드를 갱신한다. TB_CT VD_QLTY
	 * @return                           Update count
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateVd_qlty(int ctID, String vd_qlty, String asp_rto_cd)
			throws Exception {
		logger.info("######updateVd_qlty######## ctID : " + ctID + " vd_qlty : " + vd_qlty + " asp_rto_cd : " + asp_rto_cd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateVd_qlty(ctID, vd_qlty, asp_rto_cd);
		} catch (Exception e) {
			logger.error("updateVd_qlty error", e);
		}
		return 0;
	}

	/**
	 * 메타 데이타 마스터 테이블 상태 정보를 갱신한다.	
	 * 데이터상태, LOCK 상태, 에러상태 값을 변경, 저장하는 함수.
	 * @param masterID 마스터 ID
	 * @param statCd 상태 코드
	 * @param modrid 수정자id
	 * @param moddt 수정일
	 * @param lock_stat_cd lock 상태 코드
	 * @param error_stat_cd 에러 상태 코드
	 * @return updateCount 		
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateMetadataStatusCd(long masterID, String statCd,
			String modrid, String moddt, String lock_stat_cd,
			String error_stat_cd) throws Exception {
		logger.info("updateMetadataStatusCd ["+masterID+"]["+statCd+"]["+modrid+"]["+moddt+"]["+lock_stat_cd+"]["+error_stat_cd+"]");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateMetadataStatusCd(masterID, statCd, modrid,
					moddt, lock_stat_cd, error_stat_cd);
		} catch (Exception e) {
			logger.error("updateMetadataStatusCd error", e);
		}
		return 0;        
	}

	/**
	 * 메타데이타 마스터 테이블 사용 정보이력을 갱신한다
	 * @param masterId                                                                                                                                        
	 * @param userId                                                                                                                                        
	 * @return                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateModUserid(long masterId, String userId)
			throws Exception {
		logger.info("######updateModUserid######## : masterId : " + masterId + ", userId : " + userId );
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateModUserid(masterId, userId);
		} catch (Exception e) {
			logger.error("updateModUserid error", e);
		}
		return 0;
	}



	/**
	 * 인제스트 상태 정보를 갱신한다
	 * SBS의 MAIN DB인 ERP DB에 인제스트 상태를 저장한다.
	 * @param itemId                                                                                                                                     
	 * @param ingestStatus                                                                                                                                     
	 * @return                                                                                                                                     
	 * @throws DASException
	 */
	public int updateSDIngestStatus(String itemId, String ingestStatus)
			throws RemoteException {
		logger.info("######updateSDIngestStatus######## : itemId : " + itemId + ", ingestStatus : " + ingestStatus);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateSDIngestStatus(itemId, ingestStatus);
		} catch (Exception e) {
			logger.error("updateSDIngestStatus error", e);
		}
		return 0;
	}

	/**
	 * 테이프 정보를 저장한다.DAS 1.0함수
	 * 청구번호를 채번한다.
	 * @param masterId 마스터 ID	
	 * @param IDhead 헤더값
	 * @param userId 사용자id
	 * @param year 년
	 * @return annotInfoDO 주석 정보 리스트	
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String insertTapeinfo(long masterId, String IDhead, String userId,
			String year) throws Exception {
		logger.info("######insertTapeinfo######## : masterId : " + masterId + ", IDhead : " + IDhead + ", userId : " + userId + ", year : " + year);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.insertTapeinfo(masterId, IDhead, userId, year);
		} catch (Exception e) {
			logger.error("insertTapeinfo error", e);
		}
		return "";
	}

	/**
	 * DAS 장비의 로그 기록 주기를 조회한다.DAS 1.0 함수
	 * @param dasEqId                                                                                                                                                           
	 * @param dasEqPsCd                                                                                                                                                           
	 * @return                                                                                                                                                           
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public int getLogRcdPeriod(int dasEqId, String dasEqPsCd)
			throws Exception {
		logger.info("######getLogRcdPeriod######## dasEqId : " + ", dasEqPsCd : " + dasEqPsCd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getLogRcdPeriod(dasEqId, dasEqPsCd);
		} catch (Exception e) {
			logger.error("getLogRcdPeriod error", e);
		}
		return 0;
	}


	/**
	 * 입력받은 카트번호에 해당하는 다운로드 카트 정보와 카트 내용정보를 삭제 한다.
	 * @param cartNo 삭제할 카트번호
	 * @throws Exception 
	 * @throws DASException
	 */
	public void deleteAllCartInfo(long cartNo) throws Exception {
		logger.info("######deleteAllCartInfo########  cartNo :  " + cartNo);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			_processor.deleteAllCartInfo(cartNo);
		} catch (Exception e) {
			logger.error("deleteAllCartInfo error", e);
		}
	}

	/**
	 * 삭제 하고자 하는 파일 리스트를 가져 온다.	
	 * CT_ID,CTI_ID,파일경로, 실제 파일명 정보를 조회한다.
	 * @param days   오늘로 부터 며칠전 파일     
	 * @return  String  파일 이름 리스트          
	 * @param days                                                                                                                                                
	 * @return                                                                                                                                             
	 * @throws Exception 
	 */
	@Deprecated
	public String deleteContentFiles(int days) throws Exception {
		logger.info("######deleteContentFiles######## : days : " + days);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteFiles(days);
		} catch (Exception e) {
			logger.error("deleteContentFiles error", e);
		}
		return null;
	}

	/**
	 * 2차 아카이브 mp4에 저장된 키프레임 파일을 삭제한다(사용하지 않음)
	 * @param krfmFileList                                                                                                                      
	 * @return                                                                                                                       
	 * @throws DASException
	 */
	@Deprecated
	public String deleteKfrmFiles(String krfmFileList) throws RemoteException {
		logger.info("######deleteKfrmFiles########"   + krfmFileList);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			//return _processor.deleteKfrmFiles(krfmFileList);
			return "";
		} catch (Exception e) {
			logger.error("deleteKfrmFiles", e);
		}
		return "";
	}

	/**
	 * Input으로 넘어온 카트 내용 정보를 삭제한다.
	 * @param cartNo 카트번호
	 * @param seq 카트내순번
	 * @param cartNo                                                                                                                                                                                              
	 * @param seq                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public void deleteCartInfoList(long cartNo, int seq) throws Exception {
		logger.info("######deleteCartInfoList######## cartNo : " + cartNo + ", seq : " + seq );
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			_processor.deleteCartInfoList(cartNo, seq);
		} catch (Exception e) {
			logger.error("deleteCartInfoList", e);
		}
	}


	/**
	 * 조회된 콘텐트 목록을 삭제한다.(DAS 1.0)
	 * @param masterIdGrp
	 * @throws Exception 
	 */
	@Deprecated
	public void deleteContentItemList(String masterIdGRP) throws Exception{
		logger.info("#####deleteContentItemList##### : masterIdGRP : " + masterIdGRP);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		try {
			_processor.deleteContentItemList(masterIdGRP);
		} catch (Exception e) {
			logger.error("deleteContentItemList", e);
			// TODO: handle exception
		}
	}


	/**
	 * 주석정보를 조회한다
	 * @param masterId        마스타ID
	 * @return  List AnnotInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public String getAnnotInfoInfoList(long masterId) throws Exception {
		logger.info("######getAnnotInfoInfoList######## : masterId : " + masterId);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getAnnotInfoInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					AnnotInfoDOXML _do = new AnnotInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getAnnotInfoInfoList", e);
		}
		return "";
	}


	/**
	 * 입력받은 카트번호에 해당하는 카트내용 정보를 조회한다.
	 * @param cartNo 카트번호
	 * @return List CartContDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public String getCartContList(long cartNo) throws Exception {
		logger.info("######getCartContList######## cartNo : " + cartNo);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getCartContList(cartNo);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CartContDOXML _do = new CartContDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getCartContList", e);
		}
		return "";
	}

	/**
	 * 첨부파일을 조회한다.
	 * @param mothrId 모자료 ID
	 * @return List AttachFileInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public String getAttachFileInfoList(long mothrId) throws Exception {
		logger.info("######getAttachFileInfoList######## mothrId : " + mothrId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getAttachFileInfoList(mothrId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml =  new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
				while (_iter.hasNext()) {
					AttachFileInfoDOXML _do = new AttachFileInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getAttachFileInfoList", e);
		}
		return "";
	}

	/**
	 * 컨텐트 미리보기 정보를 조회한다.
	 * @param masterId  	마스터 ID 
	 * @return   List ContentsPrevInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public String getContentsPreInfoList(long masterId) throws Exception {
		logger.info("######getContentsPreInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getContentPreInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ContentsPreInfoDOXML _do = new ContentsPreInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getContentsPreInfoList", e);
		}
		return "";
	}

	/**
	 * 클립 대표화면 정보를 조회한다.
	 * @param masterId     마스타아이디
	 * @return KeyFrameImgDO 대표화면 정보
	 * @throws Exception 
	 */
	public String getClipHeaderImgInfo(long masterId) throws Exception {
		logger.info("######getClipHeaderImgInfo######## masterId: " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			KeyFrameImgDO _do = _processor.getClipHeaderImgInfo(masterId);
			KeyFrameImgDOXML _xmlDO = new KeyFrameImgDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getClipHeaderImgInfo", e);
		}
		return "";
	}

	/**
	 * 내용 및 제작정보를 조회한다.
	 * @param masterId  마스타ID
	 * @return  ontentsInfoDO 내용 및 제작정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	@Deprecated
	public String getContentsInfo(long masterId) throws Exception {
		logger.info("######getContentsInfo######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			ContentsInfoDO _do = _processor.getContentsInfo(masterId);
			ContentsInfoDOXML _xmlDO = new ContentsInfoDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getContentsInfo", e);
		}
		return "";
	} 

	/**
	 * 코너 대표화면 정보를 조회한다.
	 * @param ctId 콘텐츠아이디
	 * @param cnId 코너아이디
	 * @return KeyFrameImgDO 대표화면 정보
	 * @throws Exception 
	 */
	@Deprecated
	public String getCornerHeaderImgInfo(long ctId, long cnId)
			throws Exception {
		logger.info("######getCornerHeaderImgInfo######## ctId : " + ctId + ", cnId : " + cnId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			KeyFrameImgDO _do = _processor.getCornerHeaderImgInfo(ctId, cnId);
			KeyFrameImgDOXML _xmlDO = new KeyFrameImgDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getCornerHeaderImgInfo", e);
		}
		return "";
	}

	/**
	 * 코너정보를 조회한다.
	 * @param masterId                                                                                                                                                                                         마스타ID
	 * @return                                                                                                                                                                                         List CornerInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 */

	public String getCornerInfoList(long masterId, String keyWord)
			throws Exception {
		logger.info("######getCornerInfoList######## masterId : " +masterId + ", keyWord : " + keyWord);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getCornerInfoList(masterId, keyWord);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					CornerInfoDOXML _do = new CornerInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getCornerInfoList", e);
		}
		return "";
	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * 파일인제스트에서 코드를 조회할때 사용하는 함수
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public String getFLIngestCommonCodeList() throws Exception {
		logger.info("######getFLIngestCommonCodeList########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getFLIngestCommonCodeList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					IngestCodeInfoDOXML _do = new IngestCodeInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getFLIngestCommonCodeList", e);
		}
		return "";
	}

	/**
	 * 파일 인제스트 감시 정보를 가져온다
	 * @return                                                                                                                                              List 조회한 인제스트 정보 List
	 * @throws Exception 
	 */
	@Deprecated
	public String getFlIngestLastCommandList() throws Exception {
		logger.info("######getFlIngestLastCommandList########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getFlIngestLastCommandList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					FIWatchInfoDOXML _do = new FIWatchInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return "";
	}

	/**
	 * 장비 정보를 조회한다.
	 * DAS_EQUIPMENT_TBL에 등록되어있는 서버장비 목록을 불러온다.
	 * @param clfCd  DAS 장비 작업 구분 코드
	 * @return List 조회한 장비 정보 List
	 * @throws Exception 
	 */
	public String getIngestServerList(String clfCd) throws Exception {
		logger.info("######getIngestServerList######## clfCd : " + clfCd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getIngestServerList(clfCd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					IngestEquInfoDOXML _do = new IngestEquInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getFlIngestLastCommandList", e);
		}
		return "";
	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * CODE_TBL에 존재하는 모든 코드값을 제공한다.
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public String getSDIngestCommonCodeList() throws Exception {
		logger.info("######getSDIngestCommonCodeList########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getSDIngestCommonCodeList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					IngestCodeInfoDOXML _do = new IngestCodeInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSDIngestCommonCodeList", e);
		}
		return "";
	}

	/**
	 * 테이프 상태를 코드를 조회한다.
	 * erp db를 조회하여 tape_item_id의 상태코드를 조회한다.
	 * @param tapeId 테이프 ID 
	 * @return String    테이프 아이템 상태 코드
	 * @throws Exception 
	 */
	public String getSDIngestStatusInfo(String tapeItemId)
			throws Exception {
		logger.info("######getSDIngestStatusInfo######## tapeItemId : " + tapeItemId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getSDIngestStatusInfo(tapeItemId);
		} catch (Exception e) {
			logger.error("getSDIngestStatusInfo", e);
		}
		return "";
	}

	/**
	 * 테이프 콘텐트 정보를  조회한다.
	 * erp db를 조회하여 tapeid에 관련된 메타 정보를 조회한다
	 * @param tapeId 테이프 ID 
	 * @return List    테이프 정보 List
	 * @throws Exception 
	 */
	public String getSDIngestRefreshTapeInfo(String tapeId)
			throws Exception {
		logger.info("######getSDIngestRefreshTapeInfo######## tapeId : " + tapeId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getSDIngestRefreshTapeInfo(tapeId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
				while (_iter.hasNext()) {
					TapeContentInfoDOXML _do = new TapeContentInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSDIngestRefreshTapeInfo", e);
		}
		return "";
	}

	/**
	 * 카트 콘텐트 정보를 조회한다.
	 * tape-out로 다운된 목록을 조회한다.
	 * @param cartNo  카트 번호 
	 * @return  List    카트 콘텐트 정보 List
	 * @throws Exception 
	 */
	public String getTapeOutIngestCartItemInfo(long cartNo)
			throws Exception {
		logger.info("######getTapeOutIngestCartItemInfo######## cartNo  : " + cartNo);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getTapeOutIngestCartItemInfo(cartNo);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					IngestCartContDOXML _do = new IngestCartContDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getTapeOutIngestCartItemInfo", e);
		}
		return "";
	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * code_tbl에 존재하는 모든 코드 정보를 제공한다.
	 * @return   List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 */
	public String getTapeOutIngestCommonCodeList() throws Exception {
		logger.info("######getTapeOutIngestCommonCodeList########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getTapeOutIngestCommonCodeList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					IngestCodeInfoDOXML _do = new IngestCodeInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getTapeOutIngestCommonCodeList", e);
		}
		return "";
	}

	/**
	 * 다운 카트 정보를 조회한다.
	 * down_cart_Tbl, cart_cont_tbl의 정보를 조합하여 다운로드 정보를 제공한다.
	 * @param reqUserId  요청자 ID
	 * @param resolution 해상도  
	 * @param reqDtChk
	 * @param String     요청일자
	 * @return           List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getTapeOutIngestDownCartInfoList(String reqUserId,
			int resolution, boolean reqDtChk, String reqDt)
					throws Exception {
		logger.info("#getTapeOutIngestDownCartInfoList input param[reqUserId]:[resolution]:[reqDtChk]:[reqDt]=["+reqUserId+"]["+resolution+"]["+reqDtChk+"]["+reqDt+"]");

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {
			List _infoList = _processor.getTapeOutIngestDownCartInfoList(
					reqUserId, resolution, reqDtChk, reqDt);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml =  new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					IngestDownCartDOXML _do = new IngestDownCartDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getTapeOutIngestDownCartInfoList", e);
		}
		return "";
	}

	/**
	 * 테이프 아이템 정보를 조회한다.
	 * erp db에서 테이프에 관련된 정보를 조회한다.
	 * @param reqNum                                                                                                                                              
	 * @param pgmNm                                                                                                                                                		프로그램 이름
	 * @param IngestStatus                                                                                                                                             	인제스트 상태
	 * @return                                                                                                                                              List 조회한 테입 정보  List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getSDTapeInfoList(String reqNum, String pgmNm,
			String IngestStatus, boolean OnAirDateSearch,
			String OnAirDateStart, String OnAirDateEnd) throws Exception {
		logger.info("#getTapeOutIngestDownCartInfoList input param[reqNum]:[pgmNm]:[IngestStatus]:[OnAirDateSearch]:[OnAirDateStart]:[OnAirDateEnd]=["+reqNum+"]["+pgmNm+"]["+IngestStatus+"]["+OnAirDateSearch+"]" + "," + OnAirDateStart + ", "  + OnAirDateEnd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor
					.getSDTapeInfoList(reqNum, pgmNm, IngestStatus,
							OnAirDateSearch, OnAirDateStart, OnAirDateEnd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					TapeItemInfoDOXML _do = new TapeItemInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSDTapeInfoList", e);
		}
		return "";
	}

	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * 프로그램 정보가 pgm_info_tbl에 정의 되어있고
	 * metadat_mst_tbl에 pgm_id가 적용되어있는 건들중 프로그램 id로 조회하여 그 결과를 가져온다. 
	 * @param pgmNm 프로그램 이름 검색어
	 * @return List	ProgramInfoDO 리스트
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getProgramInfoList(String pgmNm) throws Exception {
		logger.info("######getProgramInfoList######## pgmNm : " + pgmNm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getProgramInfoList(pgmNm);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getProgramInfoList", e);
		}
		return "";
	}

	/**
	 * 프로그램에 포함된 콘텐트 정보를 읽어온다
	 * maser_id에 소속된 영상의 파일 경로 , id값을 가져온다.
	 * @param masterId 마스터 ID
	 * @return List 	PgmContensInfoDO 리스트
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmContentsInfoList(long masterId) throws Exception {
		logger.info("######getPgmContentsInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getPgmContentsInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					PgmContentsInfoDOXML _do = new PgmContentsInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPgmContentsInfoList", e);
		}
		return "";
	}

	/**
	 * 키프레임 정보를 조회한다.
	 * @param ctId                                                                                                                                                                                         콘텐츠ID
	 * @param fromSeq                                                                                                                                                                                         키프레임일련번호(from)
	 * @param toSeq                                                                                                                                                                                         키프레임일련번호(to)
	 * @return                                                                                                                                                                                         List KeyFrameInfoDO 를 포함하고 있는 List
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getKeyFrameInfoInfoList(long ctId, int fromSeq, int toSeq)
			throws Exception {
		logger.info("######getKeyFrameInfoInfoList######## ctId : " + ctId + ", fromSeq : " + fromSeq + ", toSeq : " + toSeq);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getKeyFrameInfoInfoList(ctId, fromSeq,
					toSeq);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					KeyFrameInfoDOXML _do = new KeyFrameInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getKeyFrameInfoInfoList", e);
		}
		return "";
	}

	/**
	 * 관리 정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return ManagementInfoDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getManagementInfo(long masterId) throws Exception {
		logger.info("######getManagementInfo######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			ManagementInfoDO _do = _processor.getManagementInfo(masterId);
			ManagementInfoDOXML _xmlDO = new ManagementInfoDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getManagementInfo", e);
		}
		return "";
	}

	/**
	 * 미디어 정보를 조회한다.
	 * master_id에 소속되어 있는 영상별 정보를 조회한다.
	 * @param masterId     마스타ID
	 * @return  MediaInfoDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getMediaInfo(long masterId) throws Exception {
		logger.info("######getVideoPageContentsInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			MediaInfoDO _do = _processor.getMediaInfo(masterId);
			MediaInfoDOXML _xmlDO = new MediaInfoDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getMediaInfo", e);
		}
		return "";
	}

	/**
	 * 수정자 ID, 상태 코드 조회를 한다.
	 * metadat_mst_Tbl에 저장되어있는  최근 수정자 정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return  MetadataMstInfoDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getModDatastatcd(long masterId) throws Exception {
		logger.info("######getModDatastatcd######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			MetadataMstInfoDO _do = _processor.getModDatastatcd(masterId);
			MetadataMstInfoDOXML _xmlDO = new MetadataMstInfoDOXML();
			_xmlDO.setDO(_do);
			//logger.debug("[getModDatastatcd return _xml]"+_xmlDO.toXML());
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getModDatastatcd error", e);
		}
		return "";
	}

	/**
	 * 사진정보를 조회한다.
	 * master_id로 등록된 사진정보를 조회한다.
	 * @param masterId 마스타ID
	 * @return List PhotoInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPhotoInfoList(long masterId) throws Exception {
		logger.info("######getPhotoInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getPhotoInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					PhotoInfoDOXML _do = new PhotoInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPhotoInfoList", e);
		}
		return "";
	}

	/**
	 * 영상정보를 조회한다.
	 * METADAT_MST_TBL의 정보를 조회한다.
	 * @param masterId   마스타ID
	 * @return List ReflectionInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getReflectionInfoList(long masterId) throws Exception {
		logger.info("######getReflectionInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getReflectionInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ReflectionInfoDOXML _do = new ReflectionInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getReflectionInfoList", e);
		}
		return "";
	}

	/**
	 * MP4 스토리지 IP를 조회한다.
	 * @return List 조회한 스토리지 IP List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getStorageIP() throws Exception {
		logger.info("######getStorageIP########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getStorageIP();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StorageIPXML _do = new StorageIPXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getVideoPageContentsInfoList", e);
		}
		return "";
	}

	/**
	 * 구분 상세 코드를 조회한다.
	 * CODE_TBL에 CLF_CD를 검색조건으로 받아 해당하는 코드정보를 준다.
	 * ALL로 조회할경우 코드테이블에 존재하는 모든 데이터중 USE_YN의 값이 Y인 코드값을 넘겨준다.
	 * @param clfCd 구분 코드
	 * @return List 조회한 상세 구분 코드  List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getCommonInfoList(String clfCd) throws Exception {
		logger.info("######getCommonInfoList######## clfCd : "+ clfCd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getCommonInfoList(clfCd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeInfoDOXML _do = new CodeInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getCommonInfoList", e);
		}
		return "";
	}

	/**
	 * 편성/심의 및 저작권/tape정보 조회
	 * @param masterId    마스타ID
	 * @return TapeInfoDO
	 * @throws Throwable 
	 * @throws DASException
	 */
	public String getTapeInfo(long masterId) throws Throwable {
		logger.info("######getTapeInfo######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			TapeInfoDO _do = _processor.getTapeInfo(masterId);
			TapeInfoDOXML _xmlDO = new TapeInfoDOXML();
			_xmlDO.setDO(_do);
			return _xmlDO.toXML();
		} catch (Exception e) {
			logger.error("getTapeInfo", e);
		}
		return "";
	}



	/**
	 * 마스터 ID에 대한 모든 메타데이타 마스터 정보를 조회한다.
	 * @param masterId 마스터 ID
	 * @return MetadataMstInfoDO를 포함하고 있는 DataObject
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getMetadataInfo(long masterId) throws Exception {
		logger.info("######getMetadataInfo######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getMetadataInfo(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					MetadataMstInfoDOXML _do = new MetadataMstInfoDOXML();
					_do.setDO(_iter.next());
					_xml .append(_do.getSubXML());
				}

				_xml.append("</das>");
				//	logger.debug("getMetadataInfo"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getMetadataInfo", e);
		}
		return "";
	}

	/**
	 * 오류 내역을 조회한다.
	 * @param masterId 마스터 ID
	 * @return ErrorRegisterDO xml String 오류정보를 포함
	 * @throws Exception 
	 */
	@Deprecated
	public String getErrorInfoList(long masterId) throws Exception {
		logger.info("######getErrorInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getErrorInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ErrorRegisterDOXML _do = new ErrorRegisterDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getErrorInfoList", e);
		}
		return "";
	}

	/**
	 * 마스터 테이블 수정이력 정보를 조회한다.
	 * das.mst_mod_info_tbl에 기록되어져있는 정보를 기준으로 입력된 MASTER_ID의 수정자 정보를 조회한다.
	 * @param masterId 마스터 ID
	 * @return ModeUserInfoDO xml string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getModeUserInfoList(long masterId) throws Exception {
		logger.info("######getModeUserInfoList######## masterId : " + masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getModeUserInfoList(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ModeUserInfoDOXML _do = new ModeUserInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				//	logger.debug("[getModeUserInfoList]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getModeUserInfoList", e);
		}
		return "";
	}

	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다
	 * PGM_INFO_TBL에 등록되어져있는 프로그램 중에서  검색조건에 맞는 데이터를 조회한다.
	 * @param pgmNm 프로그램 이름 검색어
	 * @return ProgramInfoDO xml string 
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmInfoFromName(String programInfoDO) throws Exception {
		logger.info("######getPgmInfoFromName######## programInfoDO : "+ programInfoDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ProgramInfoDOXML _doXML = new ProgramInfoDOXML();

		try {	
			ProgramInfoDO _do = (ProgramInfoDO)_doXML.setDO(programInfoDO);

			List _infoList = _processor.getPgmInfoFromName(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do2 = new ProgramInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPgmInfoFromName", e);
		}
		return "";
	}



	/**
	 * 검색어를 포함하는 프로그램 이름을 모두 가져온다(cms pgm_id 기준)
	 * PDS 프로그램 명을 기준으로 PDS 프로그램 정보를 조회한다.
	 * PDS_PGMINFO_TBL의 정보를 조회한다.
	 * @param pgmNm 프로그램 이름 검색어
	 * @return ProgramInfoDO xml string 
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmInfoFromName2(String pgmNm) throws Exception {
		logger.info("######getPgmInfoFromName2######## pgmNm : " + pgmNm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {
			if(pgmNm == null){
				pgmNm="";
			}
			List _infoList = _processor.getPgmInfoFromName2(pgmNm);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPgmInfoFromName2", e);
		}
		return "";
	}


	/**
	 *  프로그램이름, 방송일, 등등을 포함하는 프로그램 정보을 모두 가져온다
	 *  방송일, 프로그램명을 이용하여 메타 정보를 조회한다.
	 * @param programDO 조회조건 정보
	 * @return
	 * @throws Exception 
	 */
	public String getLastPgmInfoXmllist(String programDO) throws Exception {
		logger.info("######getLastPgmInfolist######## programDO : " + programDO);
		try {
			ProgramDOXML _doXML = new ProgramDOXML();
			ProgramInfoDO _doing = (ProgramInfoDO)_doXML.setDO(programDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.getLastPgmInfolist(_doing);
			StringBuffer _xml = new StringBuffer();
			logger.debug("####"+_infoList.size()+_xml.toString());
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ProgramDOXML _do = new ProgramDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("####2"+_xml.toString());
				//logger.debug("[getLastPgmInfolist][ouput]"+_xml);
				logger.info("######getLastPgmInfolist end########  ");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getLastPgmInfolist", e);
		}
		return "";
	}

	/**
	 * 프로그램이름, 방송일, 등등을 포함하는 프로그램 정보을 모두 가져온다
	 * 입력 받은 프로그램 명으로  LIKE 조건으로 조회하여 연관 정보를 조회한다.
	 * @param pgmNm
	 * @return
	 * @throws Exception 
	 */
	public String getLastPgmInfolist(String pgmNm) throws Exception {
		logger.info("######getLastPgmInfolist######## pgmNm : " + pgmNm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getLastPgmInfolist(pgmNm);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				logger.info("######getLastPgmInfolist end######## pgmNm : " + pgmNm);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getLastPgmInfolist", e);
		}
		return "";
	}

	/**
	 * 프로그램 ID에 대응하는 프로그램 정보를 가져온다
	 * 프로그램 ID값을 기준으로 메타데이터를 조회한다.
	 * 메타데이터에 프로그램 ID가 정의된 것에  한하여 조회한다.
	 * @param pgmID 프로그램 ID
	 * @return ProgramInfoDO xml string 
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getLastPgmInfolistByPgmId(long pgmId) throws Exception {
		logger.info("######getLastPgmInfolistByPgmId######## pgmId: " + pgmId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getLastPgmInfolistByPgmId(pgmId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				logger.info("######getLastPgmInfolistByPgmId end######## pgmId: " + pgmId);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getLastPgmInfolistByPgmId", e);
		}
		return "";
	}
	/**
	 * 프로그램 ID에 대응하는 프로그램 정보를 가져온다
	 * 입력받은 프로그램 명과, 방송일을 기준으로 최근 8건의 정보를 조회한다.
	 * 이때 조회되는 데이터는 프로그램 ID가 정의된 프로그램에 한 한다.
	 * @param pgmID 프로그램 ID
	 * @param brd_dd 방송일
	 * @return ProgramInfoDO xml string 
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getLastPgmInfolistByPgmId2(long pgmId, String brd_dd) throws Exception {
		logger.info("######getLastPgmInfolistByPgmId2######## pgmId : " + pgmId + ", brd_dd : " + brd_dd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getLastPgmInfolistByPgmId2(pgmId,brd_dd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				logger.info("######getLastPgmInfolistByPgmId2 end######## pgmId : " + pgmId + ", brd_dd : " + brd_dd);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getLastPgmInfolistByPgmId2", e);
		}
		return "";
	}

	/**
	 * 마스터 ID를 가지는 프로그램 정보를 가져온다
	 * @param episNo 만약 -1이 넘어오면 뒤에 오는 값은 pgmId가 아니라 MasterID다.에피소드 #
	 * @param pgmId 프로그램 ID
	 * @return MetadataMstInfoDO xml string 
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmInfoFromMasterid(int episNo, long pgmId)
			throws Exception {
		logger.info("######getPgmInfoFromMasterid######## episNo : " + episNo + ", pgmId : " + pgmId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getPgmInfoFromMasterid(episNo, pgmId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					MetadataMstInfoDOXML _do = new MetadataMstInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				logger.info("######getPgmInfoFromMasterid end######## episNo : " + episNo + ", pgmId : " + pgmId);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getPgmInfoFromMasterid", e);
		}
		return "";
	}

	/**
	 * 현재 재생하고 있는 미디어 정보를 조회한다.
	 * @param CTI_ID Content Instance ID
	 * @return 미디어 정보를 담은 XML
	 * @throws RemoteException
	 */
	public String getPlayMediaInfo(long CTI_ID) throws RemoteException {
		logger.info("######getPlayMediaInfo######## CTI_ID : " + CTI_ID);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			logger.debug("_processor.getPlayMediaInfo(CTI_ID)"+_processor.getPlayMediaInfo(CTI_ID));
			return _processor.getPlayMediaInfo(CTI_ID);
		} catch (Exception e) {
			logger.error("getPlayMediaInfo", e);
		}
		return "ERROR";
	}

	/**
	 * 검색영상에 대한 정보조회
	 * @param MasterID                                                                                                        
	 * @return                                                                                                              	
	 * @throws RemoteException
	 */
	public String getPlayContentInfo(long MasterID) throws RemoteException {
		logger.info("######getPlayContentInfo######## MasterID : "+ MasterID);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getPlayContentInfo(MasterID);
		} catch (Exception e) {
			logger.error("getPlayContentInfo", e);
		}
		return "ERROR";
	}

	/**
	 * 프로그램코드  코드 코드 생성
	 * 신규로 생성하는 프로그램의 코드를 생성하며 
	 * DAS에서 생성된 프로그램 코드의 앞자리는 ZZ로 시작한다.
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public String getPgm_cd() throws RemoteException{

		logger.info("######getPgm_cd########");

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return _processor.getPgm_cd(); 
		} catch (Exception e) {
			logger.error("", e);  
		} 
		logger.debug("######endgetPgm_cd########");
		return ""; 

	}


	/**
	 * 첨부파일을 삭제한다.
	 * @param attachFilename 삭제할 파일 이름
	 * @param file_type 파일 타입
	 * @param clf_cd 코드.
	 * @return 성공하면 1, 실패하면 0
	 * @throws RemoteException
	 */
	public int deleteAttachFile(String attachFilename, String file_type,
			String clf_cd) throws RemoteException {
		logger.info("######deleteAttachFile######## attachFilename : " + attachFilename + ", file_type : " + file_type + ", clf_cd : " + clf_cd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteAttachFile(attachFilename, file_type,
					clf_cd);
		} catch (Exception e) {
			logger.error("deleteAttachFile", e);
		}
		return 1;
	}

	/**
	 * 사진을 다운로드했다는 기록을 남긴다.(통계를 위해서) 
	 * @param Phot_ID 	사진 ID
	 * @param REQ_ID 	요청자 ID
	 * @param PGM_ID 	프로그램 ID
	 * @param check    				1 : 다운로드, 2 : 삭제 
	 * @throws Exception 
	 */
	public int insertPhotoDownloadInfo(long Phot_ID, String REQ_ID,	long PGM_ID) throws Exception {
		logger.info("######insertPhotoDownloadInfo######## Phot_ID : " + Phot_ID + ", REQ_ID : " + REQ_ID + ", PGM_ID : " + PGM_ID);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.InsertPhotoDownloadInfo(Phot_ID, REQ_ID, PGM_ID);
		} catch (Exception e) {
			logger.error("insertPhotoDownloadInfo", e);
		}

		return 1;
	}

	/**
	 * 사진을 다운로드했다는 기록을 남긴다.
	 * @param Phot_ID 	사진 ID
	 * @param REQ_ID 	요청자 ID
	 * @param PGM_ID 	프로그램 ID
	 * @param check    				1 : 다운로드, 2 : 삭제 
	 */
	public int insertPhotoDownInfo(String Photo) throws RemoteException {
		logger.info("######insertPhotoDownloadInfo######## Photo : " + Photo);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		PhotDownDOXML _doXML = new PhotDownDOXML();
		try {
			PhotDownDO _do = (PhotDownDO) _doXML.setDO(Photo);		

			return _processor.InsertPhotoDownInfo(_do);
		} catch (Exception e) {
			logger.error("insertPhotoDownloadInfo", e);
		}

		return 1;
	}

	/**
	 * 오디오 관련 정보를 업데이트한다.
	 * @param Master_ID 	마스터 아이디
	 * @param aud_type_cd 	 오디오 타입
	 * @param record_type_cd	미디어 녹화 정보
	 * @param me_cd   ME분리 미분리
	 * @param color_cd       컬러 여부
	 */
	public int updateContentMediaInfo(
			long master_ID, 
			String aud_type_cd,
			String record_type_cd, 
			String me_cd, 
			String color_cd)
					throws RemoteException {
		logger.info("######updateContentMediaInfo######## master_ID : " + master_ID + ", aud_type_cd : " + aud_type_cd + ", record_type_cd : " + record_type_cd + ", me_cd : " + me_cd + ", color_cd : " + color_cd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.UpdateContentMediaInfo(master_ID, aud_type_cd,
					record_type_cd, me_cd, color_cd);
		} catch (Exception e) {
			logger.error("updateContentMediaInfo", e);
		}
		return 1;
	}

	/**
	 * XML로 각 MasterID별 상태를 받아서 업데이트한다.	 *
	 * @param strXML
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public int updateDatacdWithMasterid_XML(String strXML)
			throws RemoteException {
		logger.info("######updateDatacdWithMasterid_XML######## : " + strXML);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.UpdateDatacdWithMasterid_XML(strXML);
		} catch (Exception e) {
			logger.error("updateDatacdWithMasterid_XML", e);
		}
		return 1;
	}

	/**
	 * XML로 테이프 정보를 받아서 ERP에 tape정보, tape item 정보를 추가한다.
	 * 청구번호, tape_id, tape_item_id를 신규로 발급받고 그값을 ERP DB에 넣어준다.
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public String insertERPTapeInfo(String xml) throws Exception {
		logger.info("######insertERPTapeInfo######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		Das das = new Das();
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			return _processor.insertERPTapeInfo(das);
		} catch (Exception e) {
			logger.error("insertERPTapeInfo", e);
		}
		return "";
	}

	/**
	 * ERP 정보를 업데이트한다.
	 * 청구 번호를 기준으로 ERP DB의 D_TAPE_TBL, D_TAPEITEM_TBL에 저장
	 *  
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public String updateERPTapeInfo(String xml) throws Exception {
		logger.info("######updateERPTapeInfo######## xml  : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateERPTapeInfo(xml);
		} catch (Exception e) {
			logger.error("updateERPTapeInfo", e);
		}
		return "0";
	}

	/**
	 * 유저아이디의 권한코드 조회
	 * @param UserID
	 * @return
	 * @throws RemoteException
	 */
	public String getUserAuthCD(String UserID) throws RemoteException {
		logger.info("######getUserAuthCD######## UserID : " + UserID);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getUserAuthCD(UserID);
		} catch (Exception e) {
			logger.error("getUserAuthCD", e);
		}
		return "";
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
	public String getDownCartList(String ReqUsrID, String DateStart,
			String DateEnd, String down_nm) throws RemoteException {
		logger.info("######getDownCartList######## ReqUsrID: " + ReqUsrID + ", DateStart : " + DateStart + ", DateEnd : " + DateEnd + ", down_nm : " + down_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getDownCartList(ReqUsrID, DateStart, DateEnd,
					down_nm);
		} catch (Exception e) {
			logger.error("getDownCartList", e);
		}
		return "";
	}

	/**
	 * 검색영상재생시 기초정보 조회
	 * @param masterId
	 * @return
	 * @throws Exception 
	 */
	public String getBasicPageInfo(long masterId) throws Exception {
		logger.info("######getBasicPageInfo######## masterId : "+ masterId);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String strDelimeter = "////----#panboy#----////";
			StringBuffer strResult = new StringBuffer();
			if (logger.isDebugEnabled())
				logger.debug("[getBasicPageInfo] getVideoPageInfo 시도");
			strResult.append(this.getVideoPageInfo(masterId));
			if (logger.isDebugEnabled())
				logger.debug("[getBasicPageInfo] getVideoPageInfo 종료");
			strResult.append(strDelimeter);
			if (logger.isDebugEnabled())
				logger
				.debug("[getBasicPageInfo] getVideoPageContentsInfoList 시도");
			strResult.append(this.getVideoPageContentsInfoList(masterId));
			if (logger.isDebugEnabled()) 
				logger
				.debug("[getBasicPageInfo] getVideoPageContentsInfoList 종료");
			strResult.append(strDelimeter);
			if (logger.isDebugEnabled())
				logger.debug("[getBasicPageInfo] getMetadataInfo 시도");
			strResult.append(this.getMetadataInfo(masterId));
			if (logger.isDebugEnabled())
				logger.debug("[getBasicPageInfo] getMetadataInfo 종료");
			strResult.append(strDelimeter);
			return strResult.toString();
		} catch (RemoteException e) {
			logger.error("", e);
		}
		return "";
	}

	/**
	 * WMV 재생성 요청 (콘텐츠 인스턴스 아이디)
	 * @param cti_id 콘텐츠 인스턴스 아이디
	 * cti_id 기준으로 검색영상을 재요청한다.
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV(long cti_id) throws Exception {
		logger.info("######recreateWMV######## cti_id : " + cti_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMV(cti_id);
		} catch (Exception e) {
			logger.error("recreateWMV", e);
		}
		return "";
	}

	/**
	 * WMV 재생성 요청
	 * 2009년 9월 3일 김건학 실장님 요청에 의한 추가 사항(DEKIM)
	 * 검색영상을 ct_id 기준으로 재생성하고 요청자 명을 받는다. 
	 * @param cti_id
	 * @param user_nm
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String recreateWMV_NM(long ct_id, String user_nm)
			throws RemoteException {
		logger.debug("######recreateWMV_NM######## ct_id : " + ct_id + ", user_nm : " + user_nm);

		return "";
	}

	/**
	 * WMV + KFRM 동시생성.
	 * @param ct_id
	 * @param user_nm
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String recreateWMV_KFRM(long ct_id,String user_nm) throws RemoteException{
		logger.debug("######recreateWMV_KFRM#####");
		/*ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMV_KFRM("", user_nm);
		} catch (Exception e) {
			logger.error("", e);
		}*/
		return "";
	}

	/**
	 * 키프레임 생성
	 * @param ct_id
	 * @param user_nm
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String recreateKFRM(long ct_id,String user_nm) throws RemoteException{
		logger.debug("######recreateKFRM#####");
		/*ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateKFRM(ct_id, user_nm);
		} catch (Exception e) {
			logger.error("", e);
		}*/
		return "";
	}

	/**
	 * 2010-07-29 UNLOCK by USERID 
	 * 유저id에 걸려있는 lock을 모두 푼다.
	 * @param strUserID
	 * @return
	 * @throws RemoteException
	 */
	public int unlockByUserID(String strUserID) throws RemoteException {
		logger.debug("######unlockByUserID########");
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.UnlockByUserID(strUserID);
		} catch (Exception e) {
			logger.error("unlockByUserID", e);
		}
		return -1;
	}

	/**
	 * 마스터의 해당 .mer 파일정보 조회
	 * if-cms 함수
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getMergedFilenames(long master_id) throws RemoteException {
		logger.info("######getMergedFilenames######## master_id : " + master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getMergedFilenames(master_id);
		} catch (Exception e) {
			logger.error("getMergedFilenames", e);
		}
		return "";
	}

	/**
	 * 마스터의 해당 정보를 조회
	 * if-cms 함수
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String getMasterDataTotaly(String xml) throws RemoteException {
		logger.info("######getMasterDataTotaly######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getMasterDataTotaly(xml);
		} catch (Exception e) {
			logger.error("getMasterDataTotaly", e);
		}
		return "";
	}


	/**
	 * 마스터id로 관련데이터를 수집한다
	 * if-cms 함수
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */

	public String getMasterDataAll(String xml) throws RemoteException {
		logger.info("######getMasterDataAll######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getMasterDataAll(xml);
		} catch (Exception e) {

			logger.error("getMasterDataAll", e);
		}
		return "";
	}




	/**
	 * 관련영상을 조회한다.
	 * master_id에 소속된 관련영상 정보를 조회한다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */

	public String getRelationScean(long master_id) throws RemoteException {
		logger.debug("######getRelationScean######## master_id : " + master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getRelationScean(master_id);
		} catch (Exception e) {

			logger.error("getRelationScean", e);
		}
		return "";

	}

	/**
	 * 멀티 락 설정/해제
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public int multiLockUnlock(String xml) throws RemoteException{
		logger.info("######multiLockUnlock######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try{
			return _processor.MultiLockUnlock(xml);
		}catch (Exception e) {
			logger.error("multiLockUnlock", e);
			// TODO: handle exception
		}
		return 0;

	}
	/**
	 * 스토리보드 데이타 조회
	 * IF-CMS전용 함수 getSceanInfo와 동일
	 * @param masterId
	 * @param Keyword
	 * @return
	 * @throws Exception 
	 */
	public String getStoryboardData(long masterId, String Keyword)
			throws Exception {
		logger.debug("######getStoryboardData######## masterId : " + masterId + ", Keyword : " + Keyword);
		try {
			StringBuffer strResult = new StringBuffer();
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getContentsPreInfoList 시도");
			strResult.append(this.getContentsPreInfoList(masterId));
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getContentsPreInfoList 종료");
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getClipHeaderImgInfo 시도");
			strResult.append(this.getClipHeaderImgInfo(masterId));
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getClipHeaderImgInfo 종료");
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getCornerInfoList 시도");
			strResult.append(this.getCornerInfoList(masterId, Keyword)); 
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getCornerInfoList 종료");
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getAnnotInfoInfoList 시도");
			strResult.append(this.getAnnotInfoInfoList(masterId));
			if (logger.isDebugEnabled())
				logger.debug("[StoryboardGetData] getAnnotInfoInfoList 종료");
			return strResult.toString();
		} catch (RemoteException e) {
			logger.error("getStoryboardData", e);
		}
		return "";
	}

	/**
	 * 공지사항을 조회한다.(다중조회)
	 * 
	 * @param boardDO                                                                                                                                                                                              카트내용정보
	 * @return         CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getBoardList(String boardInfoDO) throws Exception{

		logger.info("######getBoardList######## boardInfoDO : " + boardInfoDO);

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();
		BoardInfoDOXML _doXML = new BoardInfoDOXML();
		try {
			BoardConditionDO _do = (BoardConditionDO) _doXML.setDO(boardInfoDO);


			List _infoList = _processor.getBoardList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					BoardInfoDOXML _do2 = new BoardInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getBoardList", e);
		}
		logger.debug("######endgetBoardList########");
		return "";
	}


	/**
	 * 비직원 정보를 조회한다.
	 * 비직원의 정보 중에서 승인 상태가 승인 대기인 건들에 대해서 조회를 한다.
	 * @param nonEmployeeInfoDO 카트내용정보
	 * @return  CartContDO
	 * @throws DASException
	 */
	public String getOutsiderEmployeeRoleList(String employeeInfoDO) throws RemoteException{

		logger.info("######getOutsiderEmployeeRoleList######## employeeInfoDO : " + employeeInfoDO);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
		try {
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);


			List _infoList = _processor.getNonEmployeeRoleList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getOutsiderEmployeeRoleList", e);
		}
		logger.debug("######endgetOutsiderEmployeeRoleList########");
		return "";
	}



	/**
	 * 비직원 정보를 수정한다
	 * @param employeeRoleConditionDO  카트내용정보
	 * @return CartContDO
	 * @throws DASException
	 */
	public int updateOutEmployeeRole(String nonEmployeeInfoDO) throws RemoteException{

		logger.info("######updateOutEmployeeRole######## nonEmployeeInfoDO : " + nonEmployeeInfoDO);
		NonEmployeeInfoDOXML _doXML = new NonEmployeeInfoDOXML();
		try {
			NonEmployeeInfoDO _do = (NonEmployeeInfoDO) _doXML.setDO(nonEmployeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateNonEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("updateOutEmployeeRole", e);
		}
		logger.debug("######endupdateOutEmployeeRole########");
		return 0;

	}

	/**
	 * 비직원 비밀번호를 수정한다
	 * @param employeeRoleConditionDO 카트내용정보
	 * @return  CartContDO
	 * @throws DASException
	 */
	public int amendPassword(String userId, String beforePasswd, String afterPasswd) throws RemoteException{

		logger.info("######updateOutEmployeeRole######## userId : " + userId + ", beforePasswd : " + beforePasswd + ", afterPasswd : " + afterPasswd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		try {
			return _processor.amendPassword( userId,beforePasswd,afterPasswd);
		} catch (Exception e) {
			logger.error("amendPassword", e);
		}
		logger.debug("######endupdateOutEmployeeRole########");
		return 0;

	}

	/**
	 * 비직원 신규로 신청(사용하지 않음).
	 * @param nonEmployeeDASRoleDO                                                                                                                                                                                              카트내용정보
	 * @return    CartContDO
	 * @throws DASException
	 */
	@Deprecated
	public int insertNonEmployeeRole(String employeeInfoDO) throws RemoteException{

		logger.info("######insertOutEmployeeRole######## employeeInfoDO : " + employeeInfoDO);
		try {
			NonEmployeeInfoDOXML _doXML = new NonEmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertNonEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("insertNonEmployeeRole", e);
		}
		logger.debug("######endinsertOutEmployeeRole########");
		return 0;

	}


	/**
	 * 권한 코드를 삭제한다
	 * CODE_tBL, ROLE_TBL에 담겨저 있는 정보를 삭제 처리한다.
	 * @param codeDO 코드 정보
	 * @param commonDO 공통정보 
	 * @throws Exception 
	 */	
	public int deleteScreenAuthentication(String codeDO) throws Exception{

		logger.info("######deleteScreenAuthentication######## codeDO : " + codeDO);
		CodeDOXML _doXML = new CodeDOXML();
		try {
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);		
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			_processor.deleteScreenAuthentication(_do);
		} catch (Exception e) {
			logger.error("deleteScreenAuthentication", e);
		}	


		logger.debug("######deleteScreenAuthentication########");

		return 1;
	}


	/**
	 * 일괄 수정할 마스터 정보를 조회한다.
	 * MASTER_ID 단위로 메타를 수정하기 위해서 메타정보를 제공한다.
	 * 6개월 단위 이상은 XML 포맷 오류가 나므로 기간은 6개월 이하로 해야한다.
	 * @param programDO
	 * @return
	 * @throws Exception 
	 */
	public String getTotalChangelist(String programDO) throws Exception {
		logger.info("######getTotalChangelist########"+programDO);

		StringBuffer _xml = new StringBuffer();
		try {
			ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
			ProgramInfoDO _doing = (ProgramInfoDO)_doXML.setDO(programDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			int totalCount  = _processor.getTotalChangeCount(_doing);
			_doing.setTotalpage(totalCount);
			if(logger.isInfoEnabled()) {
				logger.info("totalCount : "+totalCount);
			}

			List _infoList = null;
			if(totalCount > 0) {
				_infoList = _processor.getTotalChangelist(_doing);

				if (_infoList != null && _infoList.size() > 0) {
					_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
					Iterator _iter = _infoList.iterator();

					_doXML.setDO(_doing);
					_xml.append(_doXML.getSubXML3());

					while (_iter.hasNext()) {
						ProgramInfoDOXML _do = new ProgramInfoDOXML();
						_do.setDO(_iter.next());
						_xml.append(_do.getSubXML());
					}
					_xml.append("</das>");
				}
			}
		} catch (Exception e) {
			logger.error("getTotalChangelist", e);
		}
		return _xml.toString();
	}




	/**
	 * 직원 정보를 조회한다.
	 * SEARCHTYPE 컬럼으로 조회 방식을 달리하며
	 * "" : DAS 관리 - 사용자 관리
	 * 2 : MYPAGE - 계정관리
	 * 로 조회한다.
	 * @param employeeRoleConditionDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getEmployeeList(String employeeInfoDO) throws RemoteException{

		logger.info("######getEmployeeList########" + employeeInfoDO);
		try {

			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();

			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);


			List _infoList = _processor.getEmployeeRoleList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getEmployeeList", e);
		}
		logger.debug("######endgetEmployeeList########");
		return "";
	}




	/**
	 * 직원 정보를 승인, 수정한다
	 * @param employeeRoleConditionDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws DASExceptionR
	 */
	public int updateEmployeeRole(String employeeInfoDO) throws RemoteException{

		logger.info("######updateEmployeeRole########  + " + employeeInfoDO);
		try {
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("updateEmployeeRole", e);
		}
		logger.debug("######endupdateEmployeeRole########");
		return 0;

	}



	/**  
	 * 직원 정보를  승인,승인취소한다
	 * @param employeeRoleConditionDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws DASException 
	 */
	public int updateEmployeeRoleYN(String employeeInfoDO) throws RemoteException{

		logger.info("######updateEmployeeRoleYN########"+employeeInfoDO);
		try {
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateEmployeeRoleYN(_do);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("updateEmployeeRoleYN", e);
		}
		logger.debug("######updateEmployeeRoleYN########");
		return 0;

	}


	/**
	 * 직원 신규로 신청.
	 * @param nonEmployeeDASRoleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public String insertEmployeeRole(String employeeInfoDO) throws RemoteException{

		logger.info("######insertEmployeeRole########" + employeeInfoDO);
		try {
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("insertEmployeeRole", e);
			DASException exception = new DASException(
					ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
		}
		logger.debug("######endinsertEmployeeRole########");
		return "";

	}




	/**
	 * 폐기 정보를 조회한다.(다중조회)
	 * 폐기 할 정보를 조회한다  MASTER_ID 단위 삭제를 하며
	 * 폐기가진행되면 MASTER_ID 소속 모든 영상데이터가 삭제 된다
	 * 보존기간 만료일이 지났다하더라도 자동 폐기되는 것은 아니며
	 * 오직 사용자가 수동으로 폐기 요청시에만 폐기가 된다.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDisCardList(String discardDO) throws RemoteException{
		logger.info("######discardDO  = " + discardDO);
		try {
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();
			DiscardInfoDOXML _doXML = new DiscardInfoDOXML();

			DiscardDO _do = (DiscardDO)_doXML.setDO(discardDO);


			List _infoList = _processor.getDisCardList(_do);
			List _infoList2 = _processor.getSumDiscard(_do);


			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			if (_infoList2 != null && _infoList2.size() > 0) {
				Iterator _iter2 = _infoList2.iterator();
				while (_iter2.hasNext()) {
					DiscardInfoDOXML _do2 = new DiscardInfoDOXML();
					_do2.setDO(_iter2.next());
					_xml.append(_do2.getSubXML2());
				}
			}

			_xml.append("<discard>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DiscardInfoDOXML _do2 = new DiscardInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
			}
			_xml.append("</discard>");

			_xml.append("</das>");

			return _xml.toString();

		} catch (Exception e) {

			logger.error("getDisCardList", e);
		}
		logger.debug("######endgetDisCardList########");
		return "";
	}



	/**
	 * 폐기  등록.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertDisuse(String discardDO) throws RemoteException{

		logger.info("######insertDisuse########+" + discardDO);
		try {
			DiscardregiDOXML _doXML = new DiscardregiDOXML();

			List _result = (List)_doXML.setDO(discardDO);	
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();


			_processor.insertDisuse(_result);
			return 1;
		} catch (Exception e) {
			logger.error("insertDisuse", e);  
		} 
		logger.debug("######endinsertDisuse########");
		return 0; 

	}


	/**
	 * 폐기,연장  등록 취소.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int cancelDisuse(int master_id) throws RemoteException{

		logger.info("######cancelDisuse######## master_id : " + master_id );
		try {

			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();

			return _processor.cancelDisuse(master_id);
		} catch (Exception e) {
			logger.error("cancelDisuse", e);  
		} 
		logger.debug("######endcancelDisuse########");
		return 0; 

	}
	/**
	 * 폐기,연장 등록,취소
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public int cancelDisuse2(String master_id) throws RemoteException{

		logger.info("######cancelDisuse2######## master_id : " + master_id);
		try {

			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();

			return _processor.cancelDisuse2(master_id);
		} catch (Exception e) {
			logger.error("cancelDisuse2", e);  
		} 
		logger.debug("######endcancelDisuse2########");
		return 0; 

	}




	/**
	 * 연장  등록.
	 * @param discardDO                                                                                                                                                                                             
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertUse(String discardDO) throws RemoteException{

		logger.info("######insertUse######## + " +discardDO);
		try {
			DiscardUseDOXML _doXML = new DiscardUseDOXML();

			List _result = (List)_doXML.setDO(discardDO);	
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();

			return _processor.insertUse(_result);
		} catch (Exception e) {
			logger.error("insertUse", e);  
		} 
		logger.debug("######endinsertUse########");
		return 0; 

	}


	/**
	 * 폐기 현황를 조회한다. 
	 * DISCARD_INFO_TBL에 저장된 정보들중 폐기 요청, 폐기 완료인 건들에 대해서 조회를 한다.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws DASException
	 */
	public String getHyenDisCardList(String discardDO) throws RemoteException{

		logger.info("######getHyenDisCardList########    " +discardDO);
		try {
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();

			DiscardregiDOXML2 _doXML = new DiscardregiDOXML2();
			DiscardDO _do = (DiscardDO)_doXML.setDO(discardDO);


			List _infoList = _processor.getHyenDisCardList(_do);
			List _infoList2 = _processor.getSumHyenDiscard(_do);
			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			if (_infoList2 != null && _infoList2.size() > 0) {
				Iterator _iter2 = _infoList2.iterator();
				while (_iter2.hasNext()) {
					DiscardregiDOXML2 _do2 = new DiscardregiDOXML2();
					_do2.setDO(_iter2.next());
					_xml.append(_do2.getSubXML2());
				}
			}

			_xml.append("<discard>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DiscardregiDOXML2 _do2 = new DiscardregiDOXML2();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
			}
			_xml.append("</discard>");

			_xml.append("</das>");

			return _xml.toString();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getHyenDisCardList", e);
		}
		logger.debug("######endgetHyenDisCardList########");
		return "";
	}



	/**
	 * 연장 현황를 조회한다.
	 * DISCARD_INFO_TBL에 등록된 연장 건에대해서 조회를 한다.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getHyenUseList(String discardDO) throws RemoteException{

		logger.info("######getHyenUseList########"+discardDO);
		try {
			DisuseBusinessProcessor _processor = new DisuseBusinessProcessor();
			DiscardUseDOXML2 _doXML = new DiscardUseDOXML2();

			DiscardDO _do = (DiscardDO) _doXML.setDO(discardDO);


			List _infoList = _processor.getHyenUseList(_do);
			List _infoList2 = _processor.getSumHyenuse(_do);
			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			if (_infoList2 != null && _infoList2.size() > 0) {
				Iterator _iter2 = _infoList2.iterator();
				while (_iter2.hasNext()) {
					DiscardUseDOXML2 _do2 = new DiscardUseDOXML2();
					_do2.setDO(_iter2.next());
					_xml.append(_do2.getSubXML2());
				}
			}

			_xml.append("<discard>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DiscardUseDOXML2 _do2 = new DiscardUseDOXML2();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
			}
			_xml.append("</discard>");

			_xml.append("</das>");
			//if (logger.isDebugEnabled())
			//	logger.debug("_xml" + _xml);
			return _xml.toString();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getHyenUseList", e);
		}
		logger.debug("######endgetHyenUseList########");
		return "";
	}




	/**
	 * 장르코드 관리를 조회한다.(다중조회)
	 * 장르 대,중,소 분류 코드에대해서 한번에 조회하는 쿼리.
	 * @param discardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getJanrCodeList(String codeDO) throws Exception{

		logger.info("######getJanrCodeList######## codeDO : " + codeDO);
		try {
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();
			//CodeDOXML _doXML = new CodeDOXML();
			JongrCodeDOXML _doXML = new JongrCodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);


			List _infoList = _processor.getJanrCodeList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {

					JongrCodeDOXML _do2 = new JongrCodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getJanrCodeList", e);
		}
		logger.debug("######endgetJanrCodeList########");
		return "";
	}




	/**
	 * 대분류코드   생성.
	 * @param codeDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int creatBcode(String codeDO) throws RemoteException{

		logger.info("######creatBcode######## codeDO : " + codeDO);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);	
			return _processor.creatBcode(_do);
		} catch (Exception e) {
			logger.error("creatBcode", e);  
		} 
		logger.debug("######endcreatBcode########");
		return 0; 

	}

	/**
	 * 중분류코드   생성.
	 * @param codeDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */

	public int creatMcode(String codeDO) throws RemoteException{

		logger.info("######creatMcode########"   + codeDO);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);	
			return _processor.creatMcode(_do);
		} catch (Exception e) {
			logger.error("creatMcode", e);  
		} 
		logger.debug("######endcreatMcode########");
		return 0; 

	}

	/**
	 * 소분류코드   생성.
	 * @param codeDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */


	public int creatScode(String codeDO) throws RemoteException{

		logger.info("######creatScode########"+codeDO);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);	
			return _processor.creatScode(_do);
		} catch (Exception e) {
			logger.error("creatScode", e);  
		} 
		logger.debug("######endcreatScode########");
		return 0; 

	}







	/**
	 * 대분류 코드 수정한다
	 * @param codeDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */

	public int updateBcode(String codeDO) throws Exception{

		logger.info("######updateBcode######## codeDO : " + codeDO);
		CodeDOXML _doXML = new CodeDOXML();
		try {
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);		
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return	_processor.updateBcode(_do);
		} catch (Exception e) {
			logger.error("updateBcode", e);
		}
		logger.debug("######endupdateBcode#####");
		return 0;

	}

	/**
	 * 중분류 코드 수정한다
	 * @param codeDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */

	public int updateMcode(String codeDO) throws Exception{

		logger.info("######updateMcode######## codeDO : " + codeDO);
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);		
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return	_processor.updateMcode(_do);
		} catch (Exception e) {
			logger.error("updateMcode", e);
		}
		logger.debug("######endupdateMcode########");
		return 0;

	}

	/**
	 * 소분류 코드 수정한다
	 * @param codeDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */

	public int updateScode(String codeDO) throws Exception{

		logger.info("######updateScode######## codeDO : " + codeDO);
		try {
			CodeDOXML _doXML = new CodeDOXML();
			CodeDO _do = (CodeDO) _doXML.setDO(codeDO);		
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return	_processor.updateSode(_do);
		} catch (Exception e) {
			logger.error("updateScode", e);
		}
		logger.debug("######endupdateScode########");
		return 0;

	}




	/**
	 * 프로그램코드 관리를 조회한다.(다중조회)
	 * WHERE 조건으로 조회시 해당 프로그램이 소속된 부모프로그램 코드까지 모두 조회되도록 쿼리 수정
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmList(String programInfoDO) throws Exception{

		logger.info("######getPgmList########"+programInfoDO);
		try {
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			ProgramInfoDOXML _doXML = new ProgramInfoDOXML();

			ProgramInfoDO _do = (ProgramInfoDO) _doXML.setDO(programInfoDO);


			List _infoList = _processor.getPgmList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {

					ProgramInfoDOXML _do2 = new ProgramInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPgmList", e);
		}
		logger.debug("######endgetPgmList########");
		return "";
	}



	/**
	 * 프로그램코드 관리를 조회한다. 
	 * 프로그램 명으로 조회를 한다.
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPgmInfo(String pgm_cd) throws Exception{

		logger.info("######getPgmInfo######## pgm_cd : " + pgm_cd);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			List _infoList = _processor.getPgmInfo(pgm_cd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPgmInfo", e);
		}
		logger.debug("######endgetPgmInfo########");
		return "";
	}

	/**
	 * 프로그램코드부모 코드를 조회한다.(다중조회)
	 * 프로그램 명으로 부모 프로그램으로 지정할 프로그램 정보를 조회한다.
	 * 
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getParentsCD(String pgm_cd) throws Exception{

		logger.info("######getParentsCD######## pgm_cd : " + pgm_cd);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			List _infoList = _processor.getParentsCD(pgm_cd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getParentsCD", e);
		}
		logger.debug("######end  getParentsCD########");
		return "";
	}



	/**
	 * 프로그램코드  등록.
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	public int insertPgmcd(String programInfoDO) throws Exception{

		logger.info("######insertPgmcd######## programInfoDO : " + programInfoDO);
		ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
		ProgramInfoDO _do = (ProgramInfoDO) _doXML.setDO(programInfoDO);		
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return _processor.insertPgmcd(_do);
		} catch (Exception e) {
			logger.error("insertPgmcd", e);  
		} 
		logger.debug("######insertPgmcd########");
		return 0; 

	}

	/**
	 * 관련영상 정보 조회
	 * 관련영상으로 등록시키기 위해 영상을 조회한다 이때 조회되는 기준은 MASTER_ID단위이며
	 * 조회되기위해선 아래의 조건을 만족시켜야한다
	 * 1. 영상의 구분이 CLEAN, 개편본, 종편본 중 하나여야 한다.
	 * 2. 다른 사용자가 편집하고 있는 영상은 않된다
	 * 3. 폐기되지 않은 영상이여야한다
	 * 4. 관련영상으로 등록된적이 없는 영상이여야 한다
	 * 5. 아카이브는 완료된 건이여야 한다.
	 * @param programDO
	 * @return
	 * @throws Exception 
	 */
	public String getSearchRelationInfo(String programDO) throws Exception {
		logger.info("######getSearchRelationInfo######## programDO : " + programDO);
		ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
		ProgramInfoDO _doing = (ProgramInfoDO)_doXML.setDO(programDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			List _infoList = _processor.getSearchRelationInfoList(_doing);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do = new ProgramInfoDOXML();
					_do.setDO(_iter.next());
					_xml.append( _do.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSearchRelationInfo][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSearchRelationInfo", e);
		}
		return "";
	}


	/**
	 * 프로그램 코드 정보 수정한다
	 * PGM_INFO_TBL의 프로그램 정보를 수정하게 되며, 만약 
	 * EPR DB에서 조회한 프로그램을 수정할시에는  해당정보가
	 * DAS DB에 입력되는 형태로 구현된다.
	 * @param employeeRoleConditionDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */

	public int updatePgmcd(String programInfoDO) throws Exception{

		logger.info("######updatePgmcd########"+programInfoDO);
		ProgramInfoDOXML _doXML = new ProgramInfoDOXML();
		ProgramInfoDO _do = (ProgramInfoDO) _doXML.setDO(programInfoDO);		
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return	_processor.updatePgmcd(_do);
		} catch (Exception e) {
			logger.error("updatePgmcd", e);
		}
		logger.debug("######endupdatePgmcd########");
		return 0;

	}




	/**
	 * 계열사 수신서버 관리를 조회한다.(다중조회)
	 * @param subsiInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getSubsiServerList(String subsiInfoDO) throws Exception{

		logger.info("######getPgmList######## subsiInfoDO : " + subsiInfoDO);
		try {
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			SubsiInfoDOXML _doXML = new SubsiInfoDOXML();

			SubsiInfoDO _do = (SubsiInfoDO) _doXML.setDO(subsiInfoDO);


			List _infoList = _processor.getSubsiServerList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					SubsiInfoDOXML _do2 = new SubsiInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPgmList", e);
		}
		logger.debug("######endgetPgmList########");
		return "";
	}
	/**
	 * 계열사 수신서버 관리를 등록한다.
	 * @param subsiInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public int insertSubsiServer(String subsiInfoDO) throws Exception{

		logger.info("######insertSubsiServer######## subsiInfoDO : " + subsiInfoDO);
		try {
			SubsiInfoDOXML _doXML = new SubsiInfoDOXML();
			SubsiInfoDO _do = (SubsiInfoDO) _doXML.setDO(subsiInfoDO);		
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			return _processor.insertSubsiServer(_do);
		} catch (Exception e) {
			logger.error("insertSubsiServer", e);  
		} 
		logger.debug("######endinsertSubsiServer########");
		return 0; 

	}

	/**
	 * 계열사 수신서버 정보 수정한다
	 * @param subsiInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public int updateSubsiServer(String subsiInfoDO) throws Exception{

		logger.info("######updateSubsiServer######## subsiInfoDO : " + subsiInfoDO );
		try {
			SubsiInfoDOXML _doXML = new SubsiInfoDOXML();
			SubsiInfoDO _do = (SubsiInfoDO) _doXML.setDO(subsiInfoDO);		
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			return	_processor.updateSubsiServer(_do);
		} catch (Exception e) {
			logger.error("updateSubsiServer", e);
		}
		logger.debug("######ENDupdateSubsiServer########");
		return 0;

	}



	/**
	 * 프로그램 복본관리를 조회한다.(다중조회)
	 * PDS 프로그램ID 별 복본 여부를 설정 한다
	 * 아카이브시 이 정보를 참조하여 단순아카이브, 복본아카이브 여부를 결정하여
	 * 아카이브 요청을 하게 된다.
	 * @param  copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getCopyList(String copyDO) throws Exception{

		logger.info("######getCopyList######## copyDO : " + copyDO);
		try {
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			CopyInfoDOXML _doXML = new CopyInfoDOXML();

			CopyInfoDO _do = (CopyInfoDO) _doXML.setDO(copyDO);


			List _infoList = _processor.getCopyList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					CopyInfoDOXML _do2 = new CopyInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getCopyList", e);
		}
		logger.debug("######endgetCopyList########");
		return "";
	}




	/**
	 * 프로그램 복본관리를 등록한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertCopy(String copyDO) throws Exception{

		logger.info("######insertCopy########"+copyDO);
		try {
			CopyInfoDOXML _doXML = new CopyInfoDOXML();

			CopyInfoDO _do = (CopyInfoDO) _doXML.setDO(copyDO);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			_processor.insertCopy(_do);
			return 1;
		} catch (Exception e) {
			logger.error("insertCopy", e);  
		} 
		logger.debug("######endinsertCopy########");
		return 0; 

	}




	/**
	 * 프로그램 복본관리를 수정한다.
	 * @param copyDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateCopy(String copyDO) throws Exception{

		logger.info("######updateCopy########"  + copyDO);
		try {
			CopyInfoDOXML _doXML = new CopyInfoDOXML();

			CopyInfoDO _do = (CopyInfoDO) _doXML.setDO(copyDO);
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			return	_processor.updateCopy(_do);
		} catch (Exception e) {
			logger.error("updateCopy", e);
		}

		logger.debug("######endupdateCopy########");
		return 0;

	}





	/**
	 * 다운로드 이용내역 조회
	 * DOWN_CART_TBL을 기준으로 다운로드 요청건에 대한 간략 정보를 보여준다.
	 * @param commonDO
	 * @return    
	 * @throws RemoteException
	 */
	public String getDownloadRequestList(String commonDO) throws RemoteException{
		logger.info("#####getDownloadRequestList#####" + commonDO);
		try {
			WorkBusinessProcessor _processor = new WorkBusinessProcessor();
			CartItemDOXML _doXML = new CartItemDOXML();
			CartItemDO _do = (CartItemDO)_doXML.setDO(commonDO);

			List _infolist = _processor.getDownloadRequestList(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartItemDOXML _doing = new CartItemDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getDownloadRequestList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getDownloadRequestList", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 요청건별(카트별) 상세내역 조회
	 * CART_CONT_TBL 기준으로 데이터를 보여준다.
	 * @param cartNo
	 * @return
	 * @throws RemoteException
	 */
	public String getDownloadRequestDetailList(String cartNo, String user_id) throws RemoteException{
		logger.info("#####getDownloadRequestDetailList##### cartNo : " + cartNo + ", user_id : " + user_id);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		try {
			List _infolist = _processor.getDownloadRequestDetailsList(cartNo, user_id);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartContDOXML _doing = new CartContDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				logger.debug("[getDownloadRequestDetailList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getDownloadRequestDetailList", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 다운로드 요청건에 대해서 승인 내용을 수정한다.
	 * 외주 비직원인 경우를 제외하고는 모두 다운로드 요청을 한다.
	 * @param cartContDO
	 * @return
	 * @throws RemoteException
	 */
	public int updateDownloadRequestDetail(String cartContDO ) throws RemoteException{
		logger.info("#####updateDownloadRequest #####" + cartContDO);
		try {
			WorkBusinessProcessor _processor = new WorkBusinessProcessor();
			CartContDOXML _doXML = new CartContDOXML();
			CartContDO _do = (CartContDO) _doXML.setDO(cartContDO);


			return _processor.updateDownloadRequestDetail(_do);

		} catch (Exception e) {
			logger.error("updateDownloadRequest", e);
			// TODO: handle exception
		}
		return 0;
	}

	/**
	 * 다운로드 요청건에 대해서 외주제작 승인을 수정한다.( 외주제작팀 소속 비직원인 경우: 1차.제작PD 승인 ,2차.외주제작팀직원 승인) 
	 *
	 * @param cartContDO
	 * @return
	 * @throws RemoteException
	 */
	public int updateDownloadRequestOutSourcingDetail(String cartContDO ) throws RemoteException{
		logger.info("#####updateDownloadRequest ##### cartContDO : " + cartContDO);
		try {
			WorkBusinessProcessor _processor = new WorkBusinessProcessor();
			CartContDOXML _doXML = new CartContDOXML();
			CartContDO _do = (CartContDO) _doXML.setDO(cartContDO);

			return _processor.updateDownloadRequestOutSourcingDetail(_do);

		} catch (Exception e) {
			logger.error("updateDownloadRequest", e);
			// TODO: handle exception
		}
		return 0;
	}
	/**
	 * 영상 종합 통계 (Only 미정리량 & 보유량에 대한 통계) 
	 * 조회시마다 직접 DAS DB값을 전체를 다시 읽어온다(실시간)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_TOTAL_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_TOTAL_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_TOTAL_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_TOTAL_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_TOTAL_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 수집통계 조회
	 * 특히, 기간별 수집통계는 종합통계의 수집 통계 부분에도 사용된다.
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_COL_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_COL_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_COL_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_COL_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_COL_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 폐기통계 조회
	 * 특히, 기간별 폐기통계 종합통계의 폐기통계 부분에도 사용된다.
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DISUSE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_DISUSE_TBL_List#####   "   + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_DISUSE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_DISUSE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_DISUSE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 정리통계 조회
	 * 특히, 기간별 정리통계 종합통계의 정리통계 부분에도 사용된다.
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_ARRA_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_ARRA_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_ARRA_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_ARRA_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_ARRA_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 장르별 이용통계
	 * 특히, 기간별 이용통계 종합통계의 이용통계 부분에도 사용된다.
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_GNR_USE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_GNR_USE_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_GNR_USE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_GNR_USE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_GNR_USE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 장르별 이용통계2
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_GNR_USE2_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_GNR_USE2_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_GNR_USE2_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_GNR_USE2_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_GNR_USE2_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 프로그램별 이용통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PGM_USE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PGM_USE_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PGM_USE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PGM_USE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PGM_USE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * 사진 보유량 통계 조회( getSTAT_PHOT_PGM_TOTAL_List IF 로 전환함  by DEKIM 2010.10.07)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PHOT_COL_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PHOT_COL_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PHOT_COL_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PHOT_COL_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PHOT_COL_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 사진 등록 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PHOT_REG_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PHOT_REG_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PHOT_REG_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PHOT_REG_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PHOT_REG_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 사진 누적량 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	@Deprecated
	public String getSTAT_PHOT_TOTAL_List(String statisticsConditionDO)throws Exception{
		logger.info("#####getSTAT_PHOT_TOTAL##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			return _processor.getSTAT_PHOT_TOTAL_List(_do);
		} catch (Exception e) {
			logger.error("getSTAT_PHOT_TOTAL", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 프로그램별 사진 보유량 통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PHOT_PGM_TOTAL_List(String statisticsConditionDO)throws Exception{
		logger.info("#####getSTAT_PHOT_PGM_TOTAL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList = _processor.getSTAT_PHOT_PGM_TOTAL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PHOT_PGM_TOTAL_List][ouput]"+_xml);
				return _xml.toString();
			}


		} catch (Exception e) {
			logger.error("getSTAT_PHOT_PGM_TOTAL_List", e);
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 사진 폐기 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PHOT_DISUSE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PHOT_DISUSE_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PHOT_DISUSE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PHOT_DISUSE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PHOT_DISUSE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * 사진 이용 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PHOT_USE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PHOT_USE_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PHOT_USE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PHOT_USE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PHOT_USE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 장르별 아카이브 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_GNR_ARCH_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_GNR_ARCH_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_GNR_ARCH_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//	logger.debug("[getSTAT_GNR_ARCH_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_GNR_ARCH_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 프로그램별 아카이브 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PGM_ARCH_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PGM_ARCH_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_PGM_ARCH_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PGM_ARCH_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PGM_ARCH_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 부서별 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DEPT_USE_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_DEPT_USE_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			List _infoList =_processor.getSTAT_DEPT_USE_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//	logger.debug("[getSTAT_DEPT_USE_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_DEPT_USE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 부서별 아카이브 통계 PART1(미등록)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DEPT_ARCH_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_DEPT_ARCH_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();


			List _infoList =_processor.getSTAT_DEPT_ARCH_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//	logger.debug("[getSTAT_DEPT_ARCH_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_DEPT_ARCH_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 부서별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DEPT_ARCH_DTL_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_DEPT_ARCH_DTL_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();


			List _infoList =_processor.getSTAT_DEPT_ARCH_DTL_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//	logger.debug("[getSTAT_DEPT_ARCH_DTL_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_DEPT_ARCH_DTL_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 부서별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */

	public String getSTAT_DEPT_ARCH_REQ_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_DEPT_ARCH_REQ_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();


			List _infoList =_processor.getSTAT_DEPT_ARCH_REQ_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//	logger.debug("[getSTAT_DEPT_ARCH_REQ_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_DEPT_ARCH_REQ_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 프로그램별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_PGM_ARCH_DTL_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PGM_ARCH_DTL_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();


			List _infoList =_processor.getSTAT_PGM_ARCH_DTL_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML2());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PGM_ARCH_DTL_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PGM_ARCH_DTL_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 프로그램별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */

	public String getSTAT_PGM_ARCH_REQ_TBL_List(String statisticsConditionDO) throws Exception{
		logger.info("#####getSTAT_PGM_ARCH_REQ_TBL_List##### statisticsConditionDO : " + statisticsConditionDO);
		StatisticsInfoDOXML _doXML = new StatisticsInfoDOXML();
		try {
			StatisticsConditionDO _do = (StatisticsConditionDO) _doXML.setDO(statisticsConditionDO);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();


			List _infoList =_processor.getSTAT_PGM_ARCH_REQ_TBL_List(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if (_infoList != null && _infoList.size() > 0) {
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StatisticsInfoDOXML _doing = new StatisticsInfoDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML2());
				}
				_xml.append("</das>");
				//logger.debug("[getSTAT_PGM_ARCH_REQ_TBL_List][ouput]"+_xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getSTAT_PGM_ARCH_REQ_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * DAS-TM 에 전송 작업을 요청한다.
	 * @param addTask
	 * @return
	 * @throws RemoteException
	 */
	public String insertAddTask(String addTask) throws RemoteException{
		logger.info("addTask:["+addTask+"]");
		TransferDOXML _doXML = new TransferDOXML();
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try{
			String getMessage =  _processor.getAddTask(addTask);

			if(!"".equals(getMessage)){
				TransferDO _do = (TransferDO) _doXML.setDO(getMessage);
				_processor.insertAddTask(_do);
			}
			return getMessage;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("insertAddTask", e);
		}

		return "";
	}


	/**
	 * DAS-TM 에 전송 작업을 요청 및 pds 다운 xml을 생성한다.
	 * (WorkflowService 에서 다운로드가 완료에 대한 인터페이스)
	 * workflowService에서 다운로드가 완료됬다는 값이 오면 해당 key 값에 대한 다운로드 요청 정보에 따라서 이후 로직을 수행한다.
	 * 검색영상 재생성 목적의 다운로드 건인지, tape-out용인지, 데정팀 작업용 영상인지, pds,nds,if-cms 전송용 다운로드인지 구분후
	 * 각 사용목적에 맞는 작업을 진행한다.
	 * @param num - contents_down_tbl의 키값
	 * @return
	 * @throws RemoteException
	 */
	public String completeDown(int num) throws RemoteException{

		logger.info("[completeDown][int num]["+num+"]");
		TransferDOXML _doXML = new TransferDOXML();

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try{

			/**
			 * tc 여부를 판단하고 tc 요청을 한다
			 */

			/* 2016.04.21 TC 관련 재요청 소스 수정
			TcBeanDO tccd = externalDAO.selectTcInfo(num);
			if(!tccd.getReq_cd().equals("")){
				if(tccd.getReq_cd().equals("CT")){
					return _processor.recreateKFRM(tccd, "");
				}else if(tccd.getReq_cd().equals("LR")){
					return _processor.recreateWMV(tccd, "");
				}else if(tccd.getReq_cd().equals("LRCT")){
					return _processor.recreateWMV_KFRM(tccd, "");
				}
			}
			*/
			
			TcBeanDO tccd = externalDAO.selectNewTcInfo(num);

			String mp4 = dasHandler.getProperty("WINMP4");
			String net_mp4 = dasHandler.getProperty("WINNET_MP4");
			String nearline = dasHandler.getProperty("WINNEARLINE");

			if (StringUtils.isBlank(tccd.getLR_FL_PATH())) {
				String hrPath = StringUtils.isBlank(tccd.getArch_path()) ? tccd.getHR_FL_PATH() : tccd.getArch_path();
				String low_prefix = "M".equals(tccd.getCocd()) ? net_mp4 : mp4;
				String lrPath = low_prefix;
				if (StringUtils.isBlank(hrPath)) {
					lrPath = lrPath + "/" + CalendarUtil.dateToString(new Date(), "yyyyMM") + "/" + CalendarUtil.dateToString(new Date(), "dd") + "/" + tccd.getCt_id();
				} else {
					String[] paths = hrPath.split("/");
					lrPath = lrPath + "/" + paths[(paths.length - 2)] + "/" + paths[(paths.length - 1)] + "/" + tccd.getCt_id();
				}
				tccd.setLR_FL_PATH(lrPath);
			}
			if (StringUtils.isBlank(tccd.getOut_put_ct_path())) {
				tccd.setOut_put_ct_path(tccd.getLR_FL_PATH() + "/KFRM");
			}
			if ((tccd.getJob_path() != null) && (tccd.getJob_path().startsWith("/"))) {
				tccd.setJob_path(nearline + tccd.getJob_path());
			} else {
				tccd.setJob_path(nearline + "/" + tccd.getJob_path());
			}
			if (this.logger.isDebugEnabled()) {
				this.logger.debug("TcBeanDO    : " + tccd);
			}
			if (StringUtils.isNotBlank(tccd.getReq_cd())) {
				if (tccd.getReq_cd().equals("CT")) {
					_processor.recreateKFRM(tccd, "");
				} else if (tccd.getReq_cd().equals("LR")) {
					_processor.recreateWMV(tccd, "");
				} else if (tccd.getReq_cd().equals("LRCT")) {
					_processor.recreateWMV_KFRM(tccd, "");
				}
			} else {
				/**
				 * PDS,NDS,계열사 해당하는 다운로드 요청만 DAS-TM 전송요청을 하게 됨(20110314:dekim)
				 */
				if(_processor.getUsedDasTmYn(num)){
					//tm에게 등록요청시 소요되는 시간 확인 로그
					long sTime1 = System.currentTimeMillis();
					String getMessage =  _processor.DoAddTask(num);
					long sTime2 = System.currentTimeMillis();
					logger.debug("regist job transfer tm : "+ (sTime2-sTime1)/1000.0 );

					logger.debug("getMessage ["+getMessage+"]");

					TransferDO _do2 = _processor.getCartInfo(num);

					_processor.compledownprocess(_do2.getCart_no(),_do2.getCart_seq());

					TransferDO _do = (TransferDO) _doXML.setDO(getMessage);
					_do.setCart_no(_do2.getCart_no());
					_do.setCart_seq(_do2.getCart_seq());

					_processor.insertAddTask(_do);

					logger.debug("###################completeDown success end#####################");
					return "sucess";

				}   
				TransferDO _do3 = _processor.getCartInfo(num);
				_processor.compledownprocess(_do3.getCart_no(),_do3.getCart_seq());
				logger.debug("###################completeDown there isn`t transfer things end#####################");
				return "sucess";
			} 
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("completeDown", e);
			throw new RemoteException("completeDown", e);
		}

		logger.debug("###################completeDown fail  end#####################");
		return "fail";
	}


	/**
	 * 아카이브가 되었다면 생성한다.
	 * @param job_status ,cti_id
	 * @return
	 * @throws RemoteException
	 */
	public String completeArchive(String job_status, long cti_id) throws RemoteException{
		logger.info("###################completeArchive##################### : job_status : " +  job_status + ", cti_id : " + cti_id);
		TransferDOXML _doXML = new TransferDOXML();

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try{
			String getMessage =  _processor.getArchveList(job_status,cti_id);

			return getMessage;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("completeArchive", e);
		}

		return "";
	}





	/**
	 * 클라이언트의 요청으로 DAS-TM 서버로 상태값을 요청한다.
	 * 건별로 상태값을 불러오는 기능은 존재하나 
	 * 사용하지 않는다
	 * @param getStatus
	 * @return
	 * @throws RemoteException
	 */
	public String getTmStatus(int TaskID) throws RemoteException{
		logger.info("getStatus:["+TaskID+"]");
		TmStatusDOXML _doXML = new TmStatusDOXML();	

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		String rvalue="";
		try{
			String _xml = _doXML.getXML(TaskID);



			rvalue = _processor.getTmStatus(_xml);

			TransferDO _do = (TransferDO) _doXML.setDO(rvalue);
			_processor.insertTmStatus(_do, TaskID);


			return "1";
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			logger.error("getTmStatus", e);
			return "";
		}

	}

	/**
	 * DAS-TM 의 주기적으로 데이타를 받는 인터페이스
	 * @param getTmStatusAll
	 * @return
	 * @throws Exception 
	 */
	public int getTmStatusAll(String getTmStatusAll)throws Exception{
		logger.info("getTmStatusAll input param["+getTmStatusAll+"]");

		AllTmStatusDOXML _doXML = new AllTmStatusDOXML();
		try {
			List _result = (List)_doXML.setDO(getTmStatusAll);
			//PgmUserInfoDO _do = (PgmUserInfoDO) _doXML.setDO(pgmUserInfoDO);
			String xml ="";

			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			if(_result.size()==0){
				return 0;
			}
			_processor.insertTmStatusAll(_result);
			return 1;
		} catch (Exception e) {
			logger.error("getTmStatusAll", e);   
		} 
		logger.debug("######end insertPdsPgmUserInfoAll########");
		return 0; 

	}




	/**
	 * DAS-TM 의 주기적으로 데이타를 받는 벌그
	 * tm에게서 전송상태를 주기정으로 받으며 받은 데이터는 task_id 기준으로
	 * 벌크로 db에 넣는다 task_id가 100% 존재하므로 update문만 존재한다.
	 * @param getTmStatusAll
	 * @return
	 * @throws Exception 
	 */
	public int updateTmStatusAll(String getTmStatusAll)throws Exception{
		//logger.info("updateTmStatusAll input param["+getTmStatusAll+"]");

		AllTmStatusDOXML _doXML = new AllTmStatusDOXML();
		try {
			List _result = (List)_doXML.setDO(getTmStatusAll);



			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			if(_result.size()==0){

				return 0;
			}
			_processor.insertTmStatusAll(_result);

			return 1;
		} catch (Exception e) {
			logger.error("updateTmStatusAll", e);   
		} 

		return 0; 

	}





	/**
	 * 사진정보를 조회한다.
	 * master_id에 연결되어있는 사진 정보를 조회한다
	 * @param photInfoDO 마스타ID
	 * @return List PhotoInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getPhotoList(int master_id) throws Exception{

		logger.info("######getPhotoList######## master_id : " + master_id);


		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {

			List _infoList = _processor.getPhotoList(master_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					PhotoDOXML _do2 = new PhotoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getPhotoList", e);
		}
		logger.debug("######endgetPhotoList########");
		return "";
	}

	/**
	 * 사진정보를 조회한다.(팝업)
	 * 등록된 사진 정보와 MAPPING 시킬 메타 데이터 정보를 조회하며 기준은 MASTER_ID 단위이다
	 * @param photInfoDO 마스타ID
	 * @return List PhotoInfoDO를 포함하고 있는 List
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getAttachPhotoList(String photInfoDO) throws Exception{

		logger.info("######getAttachPhotoList######## : photInfoDO : "+photInfoDO);


		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		PhotoDOXML _doXML = new PhotoDOXML();
		try {
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photInfoDO);


			List _infoList = _processor.getAttachPhotoList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					PhotoDOXML _do2 = new PhotoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getAttachPhotoList", e);
		}
		logger.debug("######endgetAttachPhotoList########");
		return "";
	}

	/**
	 * 사진 이용횟수를 수정한다.
	 * @param photInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updatPhotoCount(String photInfoDO) throws Exception{

		logger.info("######updatAuthorInfo######## photInfoDO : " + photInfoDO);
		PhotoDOXML _doXML = new PhotoDOXML();
		try {
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photInfoDO);		
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return	_processor.updatPhotoCount(_do);
		} catch (Exception e) {
			logger.error("updatAuthorInfo", e);
		}
		logger.debug("######endupdatAuthorInfo########");
		return 0;

	} 


	/**
	 * 첨부사진 정보를 저장한다.
	 * @param photoInfoDO                                                                                                                        
	 * @return                                                                                                                         
	 * @throws Exception 
	 * @throws DASException
	 * 
	 */ 

	public int insertAttachPhotoinfo(String photoInfoDO) throws Exception{

		logger.info("######insertPhotoinfo######## photoInfoDO : " + photoInfoDO);
		PhotoDOXML _doXML = new PhotoDOXML();
		try {
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photoInfoDO);		
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertAttachPotoInfo(_do);
		} catch (Exception e) {
			logger.error("endupdatAuthorInfo", e);  
		} 
		logger.debug("######endinsertPhotoinfo########");
		return 0; 

	}

	/**
	 * 부서 정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepInfoList(String depInfoDO) throws RemoteException{

		logger.info("######getDepInfoList########   depInfoDO :  "+ depInfoDO );

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepInfoDOXML _doXML = new DepInfoDOXML();
		try {
			DepInfoDO _do = (DepInfoDO) _doXML.setDO(depInfoDO);


			List _infoList = _processor.getDepInfoList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					DepInfoDOXML _do2 = new DepInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//			logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepInfoList", e);
		}
		logger.debug("######endgetgetDepInfoList########");
		return "";
	}




	/**
	 * 부서명 과 부서코드를 조회한다.
	 * DEP_INFO_TBL의 POST_UNIT_CLF의 코드가 '003'인 코드를 모두 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepInfo() throws RemoteException{

		logger.info("######getDepInfoList########");

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepInfoDOXML _doXML = new DepInfoDOXML();


		try {

			List _infoList = _processor.getDepInfo();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DepInfoDOXML _do2 = new DepInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepInfoList", e);
		}
		logger.debug("######endgetgetDepInfoList########");
		return "";
	}



	/**
	 * 회사 코드를 조회한다.
	 * 회사정보를 입력하고 회사 하위에 존재하는 부서 정보들을 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepCocdInfo(String cocd) throws RemoteException{

		logger.info("######getDepCocdInfo######## cocd : " + cocd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepInfoDOXML _doXML = new DepInfoDOXML();


		try {

			List _infoList = _processor.getDepCocdInfo(cocd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					DepInfoDOXML _do2 = new DepInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepCocdInfo", e);
		}
		logger.debug("######endgetDepCocdInfo########");
		return "";
	}


	/**
	 * 본부코드와 국코드를 조회한다.
	 * DEP_INFO_TBL에서 상위 부서 코드를 입력하여 하위 부속 부서 정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepList(String depInfoDO) throws RemoteException{

		logger.info("######getDepList######## depInfoDO : " + depInfoDO);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();
		try {
			DepInfoDO _do = (DepInfoDO) _doXML.setDO(depInfoDO);


			List _infoList = _processor.getDepList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					DepDOXML _do2 = new DepDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepList", e);
		}
		logger.debug("######endgetDepList########");
		return "";
	}



	/**
	 * 부서 정보 등록. 
	 * DAS 2.0 초기에는 DAS에서 부서정보 등록, 수정이 가능하였으나
	 * 이후 PDS에서 부서정보를 모두 받아오도록 결정.
	 * 현재 사용하지 않는 함수.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	@Deprecated
	public int insertDepInfo(String depInfoDO) throws RemoteException{

		logger.info("######insertDepInfo######## depInfoDO : " + depInfoDO);
		DepInfoDOXML _doXML = new DepInfoDOXML();
		try {
			DepInfoDO _do = (DepInfoDO) _doXML.setDO(depInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertDepInfo(_do);
		} catch (Exception e) {
			logger.error("insertDepInfo", e);  
		} 
		logger.debug("######endinsertDepInfo#######");
		return 0; 

	}

	/**
	 * 부서 정보 수정한다
	 * PDS에서 동기화 받은 부서정보를 수정한다. 
	 * STATUS 컬럼의 정보로 신규, 수정, 삭제의 역할을 한다
	 * i : 신규등록, u : 수정, d : 부서 삭제
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updateDepInfo(String depInfoDO) throws RemoteException{

		logger.info("######updateDepInfo######## depInfoDO : " + depInfoDO);
		DepInfoDOXML _doXML = new DepInfoDOXML();
		try {
			DepInfoDO _do = (DepInfoDO) _doXML.setDO(depInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateDepInfo(_do);
		} catch (Exception e) {
			logger.error("updateDepInfo", e);
		}
		logger.debug("######endupdateDepInfo########");
		return 0;

	} 





	/**
	 * 역활 정보를 수정한다.
	 * DAS와 모니터링의 역할정보를 수정한다. 
	 * 구분자가 D라면 das의 역할을 없다면 모니터링의 역할 명을 수정한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updateRoleInfo(String roleInfoDO) throws RemoteException{

		logger.info("######updateRoleInfo######## roleInfoDO : " + roleInfoDO);
		RoleInfoDOXML _doXML = new RoleInfoDOXML();
		try {
			RoleInfoDO _do = (RoleInfoDO) _doXML.setDO(roleInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateRoleInfo(_do);
		} catch (Exception e) {
			logger.error("updateRoleInfo", e);
		}
		logger.debug("######endupdateRoleInfo########");
		return 0;

	} 


	/**
	 * 역할 정보를 조회한다.
	 * 부서에 소속되어있는 사용자들의 역할정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getAuthorInfoList(String authorDO) throws RemoteException{

		logger.info("######getAuthorInfoList######## authorDO : " + authorDO);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		AuthorDOXML _doXML = new AuthorDOXML();
		try {
			AuthorDO _do = (AuthorDO) _doXML.setDO(authorDO);


			List _infoList = _processor.getAuthorInfoList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					AuthorDOXML _do2 = new AuthorDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
				_xml.append("</das>"); 
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getAuthorInfoList", e);
		}
		logger.debug("######endgetAuthorInfoList########");
		return "";
	}



	/**
	 * 역활 정보를 수정한다.
	 * SYSTEM 컬럼이 공백이라면 das 클라이언트의 역할 정보수정.
	 * 공백이 아니라면 모니터링 클라이언트의 역할정보 수정
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updatAuthorInfo(String authorDO) throws RemoteException{

		logger.info("######updatAuthorInfo######## authorDO : " + authorDO);
		AuthorDOXML _doXML = new AuthorDOXML();
		AuthorDO _do = (AuthorDO) _doXML.setDO(authorDO);		
		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		try {
			return	_processor.updatAuthorInfo(_do);
		} catch (Exception e) {
			logger.error("updatAuthorInfo", e);
		}
		logger.debug("######end   updatAuthorInfo########");
		return 0;

	} 



	/**
	 * ERP 발령 정보를 조회한다.
	 * ERP DB에서 가져온 발령정보 를 조회한다.
	 * 적용여부가 N인 건에  대해서만 조회를 하도록 한다.
	 * @param erpappointDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getERPAppointList(String erpappointDO) throws RemoteException{

		logger.info("######getERPAppointList######## erpappointDO : "+ erpappointDO);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		ErpAppointDOXML _doXML = new ErpAppointDOXML();
		try {
			ErpAppointDO _do = (ErpAppointDO) _doXML.setDO(erpappointDO);


			List _infoList = _processor.getERPAppointList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ErpAppointDOXML _do2 = new ErpAppointDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}
				_xml.append("</das>"); 
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getERPAppointList", e);
		}
		logger.debug("######endgetERPAppointList########");
		return "";
	}

	/**
	 * ERP 정보 조회시 맥스 시퀀스 조회 
	 * (20110316: LGCNS 박보아대리
	 * 
	 * 기존 스케쥴러로 ERP 발령정보를 가지고 오는 것을 웹서비스로 클라이언트 호출시 연동으로 수정요 
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String getERPAppointMaxSeq() throws RemoteException{
		logger.debug("######getERPAppointMaxSeq######");

		//	try {
		////		return "";
		////		 return _processor.get; 
		//		 
		//	} catch (Exception e) {
		//		logger.error("", e);  
		//	} 
		logger.debug("######getERPAppointMaxSeq#######");
		return "0";
	}

	/**
	 * ERP 발령정보를 등록한다.
	 * COM_ORDER_TBL에 등록된 발령정보중
	 * DAS USER_INFO_TBL에 적용하고자 하는 정보를 적용하는 로직.
	 * 신규 사용자인경우에 등록처리한다.
	 * @param ErpAppointDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertERPAppoint(String erpappointDO) throws Exception{

		logger.info("######insertERPAppoint######## erpappointDO : "  + erpappointDO);
		ErpUserInfoDOXML _doXML = new ErpUserInfoDOXML();
		try {
			List  _do = (List) _doXML.setDO(erpappointDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			_processor.insertERPAppoint(_do); 
			return 1;
		} catch (Exception e) {
			logger.error("insertERPAppoint", e);  
		} 
		logger.debug("######endinsertERPAppoint#######");
		return 0; 

	}

	/**
	 * 일괄수정 인터페이스
	 * CONTENTS_INST_TBL, METADAT_MST_TBL의 메타데이터를 일괄적으로 수정한다.
	 * 한번에 여러컬럼을 업데이트 하는 것이 아닌 하나의 컬럼을 바꾸는 형식으로 사용된다.
	 * @param totalChangeInfoDO
	 * @return
	 * @throws RemoteException
	 */

	public int updateTotalChange(String totalChangeInfoDO)throws RemoteException{
		logger.info("updateTotalChange [input param] ["+totalChangeInfoDO+"] ");
		TotalChangeDOXML _doXML = new TotalChangeDOXML();
		try{
			TotalChangeInfoDO _do = (TotalChangeInfoDO)_doXML.setDO(totalChangeInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateTotalChange(_do);
		}catch(Exception e){
			logger.error("updateTotalChange", e);
		}
		logger.debug("#####updateTotalChange#####");
		return 0;
	}


	/**
	 * 이용현황 정보를 조회한다.
	 * master_id 기준 다운로드 이용 현황에 대해서 정보를 제공한다.
	 * master_id에 소속되어있는 영상을 다운받을 경우 +1이 된다.
	 * @param useInfoDO 종료일
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getUseInfoList(String useInfoDO) throws Exception {
		logger.info("######getUseInfoList Start######## useInfoDO : "+useInfoDO);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		UseInfoDOXML _DOXML = new UseInfoDOXML();
		/** 2015.11.19 생성 */
		try {
			UseInfoDO _DO = (UseInfoDO) _DOXML.setDO(useInfoDO);

			UseInfoDO useInfo = _processor.getUseInfoCount(_DO, DASBusinessConstants.PageQueryFlag.TOTAL_COUNT);

			StringBuffer _xml = new StringBuffer();

			// 조회 건수가 존재하면 리스트 조회
			if(useInfo.getTotal() > 0) {
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

				UseInfoDOXML infoXML = new UseInfoDOXML();

				// total 건수와 전체 duration 길이를 xml에 append 함.
				infoXML.setDO(useInfo);
				_xml.append(infoXML.getSubXML2());

				// 리스트를 조회.
				List _infoList = _processor.getNewUseInfoList(_DO, DASBusinessConstants.PageQueryFlag.NORMAL);
				int size = _infoList.size();

				infoXML = new UseInfoDOXML();
				for(int i=0; i<size; i++) {
					infoXML.setDO(_infoList.get(i));

					_xml.append(infoXML.getSubXML());
				}

				_xml.append("</das>");
			}
			return _xml.toString();
		} catch (Exception e) {
			logger.error("getUseInfoList", e);
		}
		return "";
	}


	/**
	 * 프로그램코드 조회한다.(다중조회)
	 * 프로그램 명으로 LIKE 검색하여 나온 결과를 제공
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getProgramInfo(String title) throws Exception{

		logger.info("######getProgramInfo######## title  : " + title);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			List _infoList = _processor.getPgmInfo(title);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do2 = new ProgramInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);

				//insertAutoArchvie(_xml);
				return _xml.toString();


			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getProgramInfo", e);
		}
		logger.debug("######endgetProgramInfo########");
		return "";
	}

	/**
	 * 스토리지 용량 확인
	 * DAS 2.0 초기 스토리지 확인 방법 실제로 유닉스 명령어로 스토리지 용량을 조회하여 
	 * 그값을 리턴하였다.
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String getAvailable() throws RemoteException{

		logger.debug("##### getAvailable #####");
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

		try {
			return _processor.getAvailableDisk();
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		logger.debug("##### getAvailable #####");
		return "";
	}



	/**
	 * 오늘의 화면 조회한다.
	 * 대분류가 프로그램이면서, 검색일 기준으로 방송일이 2일 이내인 영상에 대해서 
	 * 조회한다.(어제 오늘 등록된 프로그램데이터 조회)
	 * @param todayDO 종료일
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getTodayList(String todayDO) throws Exception {
		logger.info("######getTodayList Start######## todayDO : "+todayDO);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		TodayDOXML _DOXML = new TodayDOXML();
		try {
			TodayDO _DO = (TodayDO)_DOXML.setDO(todayDO);


			List _infoList = _processor.getTodayList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					TodayDOXML _do2 = new TodayDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);


				return _xml.toString();

			}
		} catch (Exception e) {
			logger.error("getTodayList", e);
		}finally{
			logger.debug("######getTodayList End########");
		}
		return "";
	}


	/**
	 * 명장면 조회한다.
	 * 주제영상이 설정된 영상들의 최신 정보를 제공하는  함수
	 * 등록일 기준으로 최근 30건을 조회한다.
	 * @param goodDO 종료일
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getGoodList(String goodDO) throws Exception {
		logger.info("######getTodayList Start######## goodDO : "+goodDO);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		GoodMediaDOXML _DOXML = new GoodMediaDOXML();
		try {
			GoodMediaDO _DO = (GoodMediaDO)_DOXML.setDO(goodDO);


			List _infoList = _processor.getGoodMediaList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					GoodMediaDOXML _do2 = new GoodMediaDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);
				return _xml.toString();

			}
		} catch (Exception e) {
			logger.error("getGoodList", e);
		}finally{
			logger.debug("######getTodayList End########");
		}
		return "";
	}


	/**
	 * 부모프로그램코드 조회한다.
	 * ERP DB의 E_PGMMST_TBL의 프로그램 정보와
	 * DAS DB의 PGM_INFO_TBL의 프로그램 정보를 조합하여
	 * 검색하고자하는 프로그램의 상위 프로그램을 조회한다.
	 * @param pgm_nm 프로그램명
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getParentsInfo(String pgm_nm) throws Exception {
		logger.info("######getParentsInfo Start######## pgm_nm : "+pgm_nm);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

		try {
			List _infoList = _processor.getParentsInfo(pgm_nm);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml =  new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ParentsDOXML _do2 = new ParentsDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//	logger.debug("_xml" + _xml);


				return _xml.toString();

			}
		} catch (Exception e) {
			logger.error("getParentsInfo", e);
		}finally{
			logger.debug("######getParentsInfo End########");
		}
		return "";
	}


	/**
	 * 스토리지 용량 조회
	 * 스토리지의 가용량, 총량 정보를 제공
	 * @return
	 * @throws Exception 
	 */
	public String getScanDisk() throws Exception{
		logger.info("#####getScanDisk start#####");
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

		StringBuffer _xml= new StringBuffer();
		try {
			List _infoList = _processor.getStorageInfo();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StorageDOXML _do = new StorageDOXML();
					_do.setDO(_iter.next());

					_xml.append(_do.getSubXML2());

				}
			}


			_xml.append("</das>");


			return _xml.toString();


		} catch (Exception e) {
			logger.error("getScanDisk", e);
		}finally{
			logger.debug("######getStorageInfo End########");
		}

		return "";
	}


	/**
	 * NLE drag & drop 내용을 조회한다.
	 * 수동 아카이브 대상(PDS 아카이브, 매체변환 건중 수동아카이브대상, 데정팀 다운로드)
	 * 을 조회하고, 해당영상을 바로 아카이브 요청할수도 있고, 편집후 수동 등록을 할수도 있다.
	 * @param preProcessingDO
	 * @return
	 * @throws Exception 
	 */
	public String getPreProcessing(String preProcessingDO) throws Exception{
		logger.info("#####getPreProcessing##### preProcessingDO : " + preProcessingDO);
		PreProcessingDOXML _doXML = new PreProcessingDOXML();
		try{
			PreProcessingDO _do = (PreProcessingDO)_doXML.setDO(preProcessingDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			StringBuffer _xml = new StringBuffer();


			List _infoList = _processor.getPreProcessingList(_do);

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				while (_iter.hasNext()) {

					PreProcessingDOXML _do2 = new PreProcessingDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());


				}

				_xml.append("</das>");

				return _xml.toString();
			}

		}catch(Exception e){
			logger.error("getPreProcessing", e);
		}finally{

		}
		return "";
	}

	/**
	 * NLE 메타 & DTL 등록 대상을 조회한다.
	 * 수동 아카이브 대상(PDS 아카이브, 매체변환 건중 수동아카이브대상, 데정팀 다운로드)
	 * 을 조회하고, 해당영상을 바로 아카이브 요청할수도 있고, 편집후 수동 등록을 할수도 있다.
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getArchPreProcessing(String conditionDO) throws Exception{
		logger.info("######getArchPreProcessing Start######## conditionDO : "+conditionDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ConditionDOXML _DOXML = new ConditionDOXML();
		try {
			WorkStatusConditionDO _DO = (WorkStatusConditionDO) _DOXML
					.setDO(conditionDO);

			List _infoList = _processor.getArchPreProcessing(_DO);
			StringBuffer _xml = new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer buf = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					MetaInfoDOXML _do = new MetaInfoDOXML();
					_do.setDO(_iter.next());
					buf.append(_do.getSubXML());
				}

				_xml.append("</das>");
				//			logger.debug("#########2");
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("getArchPreProcessing", e);
		}finally{
			logger.debug("######getMetadatInfoList End########");
		}
		return "";
	}

	/**
	 * NLE 메타 & DTL 등록 메타데이타를 등록한다.
	 * METADAT_MST_TBL에 신규로 데이터를 집어넣는다
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public int insertMetadat(String metadataMstInfoDO) throws Exception {
		logger.info("######insertMetadat######## metadataMstInfoDO : " + metadataMstInfoDO);
		MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
		try {
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML
					.setDO(metadataMstInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertMetadat(_do);
		} catch (Exception e) {
			logger.error("insertMetadat", e);
		}
		return 0;
	}

	/**
	 * NLE 메타 & DTL 등록 메타데이타를 카피해서 등록한다.
	 * METADAT_MST_TBL에 존재하는 데이터를 복사해서 
	 * 신규로 만들고자하는 메타에 업어쓰는 방식 
	 * SELECT ... UPDATE 문으로 구성되어있다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public int insertCopyMetadat(String metadataMstInfoDO) throws Exception {
		logger.info("######insertCopyMetadat######## metadataMstInfoDO : " + metadataMstInfoDO);
		MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
		try {
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML
					.setDO(metadataMstInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertCopyMetadat(_do);
		} catch (Exception e) {
			logger.error("insertCopyMetadat", e);
		}
		return 0;
	}

	/**
	 * PDS 프로그램 정보를 등록한다.(벌크)_스케줄러
	 * PDS 프로그램ID, 책임CP,담당PD의 정보를 벌크로 받아서 저장한다.
	 * 매일 새별 6시 30분 CRONTAB에 적용된 스케쥴러가 PDS에게 정보를 요청하며
	 * 이때   넘어온 데이터를 넣어준다. 데이터 정리를 위해 PDS_PGMINFO_TBL에 1차적으로 데이터를
	 * 중복에 상관없이 넣어주며, 2차로 PDS_PGMINFO_TBL2에 PDS_PGMINFO_TBL에서 추린 데이터 정보를 
	 * 넣어주고. 만약 신규로 들어온 프로그램ID이거나 사용자가 추가 되었다면
	 * APPROVE_INFO_TBL에 승인 자로 자동으로 등록시켜준다.
	 * PDS Soap Call ( pds_ex_get_programinfo)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertPdsPgmInfoAll(String pdsInfoDO) throws Exception{

		logger.info("######insertPdsPgmInfoAll######## pdsInfoDO : " + pdsInfoDO);
		try {
			String PdsInfo = CommonUtl.transXMLText(pdsInfoDO);
			logger.debug("######PdsInfo       ===    " + PdsInfo);
			AllPgmInfoDOXML _doXML = new AllPgmInfoDOXML();

			List _result = (List)_doXML.setDO(PdsInfo);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			if(_result.size()==0){
				return 0;
			}
			_processor.updateRunScheduleDt();
			_processor.insertPdsPgmInfoAll(_result);
			logger.debug("###### PgmInfoDO end ########");
			return 1;
		} catch (Exception e) {

			logger.error("insertPdsPgmInfoAll", e);  
			return 0; 
		} 


		//logger.debug("######end insertPdsPgmInfoAll########");


	}

	/**
	 * PDS 프로그램 정보를 등록한다.
	 * PDS 프로그램ID, 책임CP,담당PD의 정보를 벌크로 받아서 저장한다.
	 * 매일 새별 6시 30분 CRONTAB에 적용된 스케쥴러가 PDS에게 정보를 요청하며
	 * 이때   넘어온 데이터를 넣어준다.
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertPdsPgmInfo(String pdsInfoDO) throws Exception{

		logger.info("######insertPdsPgmInfo######## pdsInfoDO : " + pdsInfoDO);

		PgmInfoDOXML _doXML = new PgmInfoDOXML();
		try {
			String PdsInfo = CommonUtl.transHtmlText(pdsInfoDO);
			PgmInfoDO _do = (PgmInfoDO) _doXML.setDO(PdsInfo);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();


			_processor.insertPdsPgmInfo(_do);
			return 1;
		} catch (Exception e) {
			logger.error("insertPdsPgmInfo", e);  
		} 

		//logger.debug("###### PgmInfoDO ########"  + _do);
		logger.debug("######end insertPdsPgmInfo ########");
		return 0; 

	}

	/**
	 * PDS 프로그램별 사용자 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertPdsPgmUserInfo(String pgmUserInfoDO) throws Exception{

		logger.info("######insertPdsPgmUserInfo######## pgmUserInfoDO : " + pgmUserInfoDO);
		String PdsInfo = CommonUtl.transHtmlText(pgmUserInfoDO);
		PgmUserInfoDOXML _doXML = new PgmUserInfoDOXML();
		try {
			PgmUserInfoDO _do = (PgmUserInfoDO) _doXML.setDO(PdsInfo);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();


			_processor.insertPdsPgmUserInfo(_do);
			return 1;
		} catch (Exception e) {
			logger.error("insertPdsPgmUserInfo", e);  
		} 
		logger.debug("######insertPdsPgmUserInfo END########");
		return 0; 

	}

	/**
	 * PDS 프로그램별 사용자 정보를 등록한다.(벌크)
	 * PDS Soap Call ( pds_ex_get_userinfobyfolder)
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertPdsPgmUserInfoAll(String pgmUserInfoDO) throws Exception{

		logger.debug("######insertPdsPgmUserInfoAll######## pgmUserInfoDO : " + pgmUserInfoDO);
		String PdsInfo = CommonUtl.transXMLText(pgmUserInfoDO);
		AllPgmUserInfoDOXML _doXML = new AllPgmUserInfoDOXML();
		try {
			List _result = (List)_doXML.setDO(PdsInfo);
			//PgmUserInfoDO _do = (PgmUserInfoDO) _doXML.setDO(pgmUserInfoDO);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			if(_result.size()==0){
				return 0;
			}
			_processor.insertPdsPgmUserInfoAll(_result);
			return 1;
		} catch (Exception e) {
			logger.error("insertPdsPgmUserInfoAll", e);   
		} 
		logger.debug("######end insertPdsPgmUserInfoAll########");
		return 0; 

	}

	/**
	 * PDS-DAS간 연동 정보를 등록한다.
	 * @param pgmUserInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertPdsMappInfo(String pdsMappDO) throws Exception{

		logger.info("######insertPdsMappInfo######## pdsMappDO : " + pdsMappDO);
		//CommonUtl.transHtmlText(pgmUserInfoDO);
		PdsMappDOXML _doXML = new PdsMappDOXML();
		try {
			PdsMappDO _do = (PdsMappDO) _doXML.setDO(pdsMappDO);
			String xml ="";

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();


			_processor.insertPdsMappInfo(_do);
			return 1;
		} catch (Exception e) {
			logger.error("insertPdsMappInfo", e);  
		} 
		logger.debug("######end insertPdsMappInfo########");
		return 0; 

	}

	/**
	 * NLE  & DTL 등록한다.
	 * 등록되어진 메타데이터를 기준으로 수동아카이브건을 아카이브 시킨다.
	 * 수동아카이브 여부를 N으로 바꾼다.
	 * @param ct_id
	 * @return
	 * @throws Exception 
	 */
	public int insertNLEandDTL(long ct_id) throws Exception {

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.insertNLEandDTL(ct_id); 
		} catch (Exception e) {
			logger.error("insertNLE", e);
		}
		return 0;
	}


	/**
	 *  NLE  & DTL 등록
	 *  등록되어진 메타데이터를 기준으로 수동아카이브건을 아카이브 시킨다.
	 * 수동아카이브 여부를 N으로 바꾼다.
	 * @param ct_id
	 * @return
	 * @throws Exception 
	 */
	public long insertDTL(long ct_id) throws Exception {
		logger.info("######insertNLE######## ct_id : " + ct_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.insertNLEandDTL(ct_id);
		} catch (Exception e) {
			logger.error("insertDTL", e);
		}
		return 0;
	}

	/**
	 * My Page다운로드 목록조회
	 * 자신이 다운로드요청한 모든 목록에 대해서 cart_no 기준으로 
	 * 조회할수있다.
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getMyDownloadRequestList(String commonDO) throws RemoteException{
		logger.info("#####getMyDownloadRequestList#####  commonDO : "  + commonDO);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		CartItemDOXML _doXML = new CartItemDOXML();
		try {
			CartItemDO _do = (CartItemDO)_doXML.setDO(commonDO);

			List _infolist = _processor.getMyDownloadRequestList(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartItemDOXML _doing = new CartItemDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//		logger.debug("[getMyDownloadRequestList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getMyDownloadRequestList", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * My Page다운로드 상세조회
	 * 자신 다운로드 요청한 건들중에서 cart_seq정보 단위로 정보를 제공
	 * @param commonDO 
	 * @return
	 * @throws RemoteException
	 */
	public String getMyDownloadRequestDetailList(String cartNo, String user_id) throws RemoteException{
		logger.info("#####getMyDownloadRequestDetailList##### cartNo : " + cartNo +   " user_id: " + user_id );
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		try {
			List _infolist = _processor.getMyDownloadRequestDetailsList(cartNo,user_id);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartItemDOXML _doing = new CartItemDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				///		logger.debug("[getMyDownloadRequestDetailList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getMyDownloadRequestDetailList", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * NLE  & DTL 삭제한다.
	 * NLE DRAG&DROP에서 사용하는 함수
	 * 매체변환, PDS 아카이브 수동등록건을 삭제 처리한다.
	 * 삭제후에는 검색되지 않는다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public int deleteNLE(long ct_id) throws Exception {
		if(logger.isDebugEnabled()) {
			logger.debug("######deleteNLE######## ct_id: "  + ct_id);
		}

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteNLE(ct_id); 
		} catch (Exception e) {
			logger.error("deleteNLE", e);
		}

		return 0;
	}


	/**
	 * NLE  & DTL 삭제한다.(고해상도)
	 * NLE DRAG&DROP에서 사용하는 함수
	 * 데정팀 다운로드 건에 대해서 삭제 처리가 가능하며 삭제 후에는 
	 * 검색되지 않는다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public int deleteNLEForDown(long cart_no) throws Exception {
		logger.info("######deleteNLEForDown########  cart_no : "  + cart_no);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteNLEForDown(cart_no); 
		} catch (Exception e) {
			logger.error("deleteNLEForDown", e);
		}
		return 0;
	}

	/**
	 * 검색엔진을 통한 조회
	 * 코난검색엔진을 이용해 결과를 도출하는 함수.
	 * 코난에서 제공한 LIB을 사용하며, DAS는 해당 LIB이용해  WHERE 조건을 작성하여 질의를 던진다
	 * 결과내 재검색, 상세검색, 키워드 검색등의 기능을 제공하며
	 * IF-CMS에서 날린 질의 인지(질의시 검색하고자하는 회사코드를 반드시 넣어주게 되어있으며,
	 * 전체 검색이 가능하다. 키워드검색과 상세검색을 동시에 할수있다.)
	 * , DAS CMS에서 날린 질의 인지에 따라서 조회조건이 달라진다.(SBS 소재의 데이터만 검색이 가능하며,
	 * 날짜 혹은 검색키워드가 무조건 존재하여야 한다. 키워드검색과 상세검색을 동시에 할수없다)
	 * 
	 * 
	 * @param searchInfoDO
	 * @return
	 * @throws RemoteException
	 */
	public String getSearchText(String searchInfoDO) throws RemoteException{
		if(logger.isDebugEnabled()) {
			logger.debug("#####getSearchText searchInfoDO input ["      +  searchInfoDO+"] ");
		}
		SearchInfoDOXML _doXML = new SearchInfoDOXML();
		try {
			ParameterVO _do = (ParameterVO) _doXML.setDO(searchInfoDO);
			logger.debug("_do.getReSrchFlag() ="+_do.getReSrchFlag());
			SearchBusinessProcessor _processor = new SearchBusinessProcessor();


			String sResult = _processor.getSearchText(_do);

			return sResult; 
		} catch (Exception e) {
			logger.error("getSearchText", e);
		}finally{
			logger.debug("#####getSearchText end#####");
		}
		return "";
	}

	/**
	 * ERP 정보를 테이블에 등록한다.(벌크)
	 * SBS ERP DB에서 발령정보를 받아온다
	 * 가장 최근에 받아온 발령정보의 SEQ값을 받아와서 그이후의 발령정보를
	 * 받도록 한다.
	 * @param ErpAppointDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int insertERPUserInfo(String erpappointDO) throws RemoteException{

		logger.info("######insertERPUserInfo######## String erpappointDO size["+erpappointDO.length()+"] xml["+erpappointDO+"] ");
		ErpUserInfoDOXML _doXML = new ErpUserInfoDOXML();
		try {
			List  _do = (List) _doXML.setDO(erpappointDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			if(_processor.insertERPUserInfo(_do)){
				return 1;
			}
			return 0;
		} catch (Exception e) {
			logger.error("insertERPUserInfo", e);  
		} 
		logger.debug("######insertERPUserInfo end#######");
		return 0; 

	}


	/**
	 * 암호테스트 
	 * SHA-512 암호화 테스트
	 * @param PASSWORD                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String insertPassword(String pawd) throws NoSuchAlgorithmException {

		logger.info("######insertPassword######## pawd : " + pawd);
		MessageDigest md;
		String message = "password";
		try {
			md= MessageDigest.getInstance("SHA-512");

			md.update(pawd.getBytes());
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
			logger.debug(out.length());
			return out;

		} catch (NoSuchAlgorithmException e) {
			logger.debug("ERROR: " + e.getMessage());
		}

		return ""; 

	}



	/**  
	 * 패스워드를 초기화한다
	 * @param                                                                                                                                                                                          
	 * @return                                                                                                                                                                                              
	 * @throws DASException 
	 */
	public String updateInitPassword(String user_id) throws RemoteException{

		logger.info("######updateInitPassword######## user_id : " + user_id);
		//EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
		//EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		try {
			return	_processor.updateInitPassword(user_id);
		} catch (Exception e) {
			logger.error("updateInitPassword", e);
		}
		logger.debug("######updateInitPassword end########");
		return "";

	}



	/**
	 * 관련영상 링크조회한다
	 * @param masterId
	 * @return
	 * @throws Exception 
	 */
	public String getRelationLink(long masterId)throws Exception{
		logger.info("#####getRelationLink##### masterId : " + masterId);
		ExternalBusinessProcessor _prosessor = new ExternalBusinessProcessor();
		try {
			List _infoList = _prosessor.getRelationLink(masterId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					RelationDOXML _do2 = new RelationDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();

			}
		} catch (Exception e) {
			logger.error("getRelationLink", e);
		}finally{
			logger.debug("######getTodayList End########");
		}
		return "";
	}




	/**
	 * ERP 발령정보를 로컬db에 수정한다
	 * @param ErpAppointDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updateERPAppoint(String erpappointDO) throws RemoteException{

		logger.info("######updateERPAppoint########  erpappointDO :  "+erpappointDO);
		try {
			ErpUserInfoDOXML2 _doXML = new ErpUserInfoDOXML2();
			List  _do = (List) _doXML.setDO(erpappointDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			_processor.updateERPAppoint(_do); 
			return 1;
		} catch (Exception e) {
			logger.error("updateERPAppoint", e);  
		} 
		logger.debug("######updateERPAppoint end#######");
		return 0; 

	}





	/**
	 * 타 DB에서 사용자 정보를 받아온다.
	 * @param EmployeeDASRoleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertOtherUserInfo(String employeeInfoDO) throws RemoteException{

		logger.info("######insertOtherUserInfo######## employeeInfoDO : "   + employeeInfoDO);
		AllUserInfoDOXML _doXML = new AllUserInfoDOXML();
		try {
			List _result = (List)_doXML.setDO(employeeInfoDO);	
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			_processor.insertOtherUserInfo(_result);
			return 1;
		} catch (Exception e) {
			logger.error("insertOtherUserInfo", e);
		}
		logger.debug("######insertOtherUserInfo end########");
		return 0;

	}



	/**
	 * 타 DB에서  부서 정보를 받아온다.
	 * operation_type == status [C:생성, U:변경, D:삭제, S:동기화]
	 * @param EmployeeDASRoleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertOtherDepInfo(String employeeInfoDO) throws RemoteException{

		logger.info("######insertOtherDepInfo######## employeeInfoDO : " + employeeInfoDO);
		AllDepInfoDOXML _doXML = new AllDepInfoDOXML();
		try {
			List _result = (List)_doXML.setDO(employeeInfoDO);	
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertOtherDepInfo(_result);

		} catch (Exception e) {
			logger.error("insertOtherDepInfo", e);
		}
		logger.debug("######insertOtherDepInfo end########");
		return 0;

	}


	/**
	 * My Sign 다운로드 승인조회
	 * DAS CLIENT, IF-CMS,외주직원 승인자가 조회할수 있는 함수이며,
	 * DAS CLIENT, IF-CMS CLIENT 에서 사용하는 함수이다.
	 * 외주부서 정직원의 경우 OUTSOCING_YN='Y'
	 * DAS의 경우 XML중 SYSTEM 컬럼의 값이 ''
	 * IF-CMS의 조회 요청자의ID가 채널별 승인자 목록에 등록되어져 있다면 승인할 건에 대해서 조회하도록 
	 * 정보를 전달한다.
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getMyDownloadAprroveList(String commonDO) throws RemoteException{
		logger.info("#####getMyDownloadAprroveList#####    commonDO :  "+commonDO);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		CartItemDOXML _doXML = new CartItemDOXML();
		try {
			CartItemDO _do = (CartItemDO)_doXML.setDO(commonDO);

			List _infolist = _processor.getMyDownloadAprroveList(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartItemDOXML _doing = new CartItemDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//		logger.debug("[getMyDownloadAprroveList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getMyDownloadAprroveList", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * My Sign 다운로드 승인 상세조회
	 * DAS CLIENT, IF-CMS,외주직원 승인자가 조회할수 있는 함수이며,
	 * DAS CLIENT, IF-CMS CLIENT 에서 사용하는 함수이다.
	 * 상세로 조회된것 중에서 승인자가 승인해야할 건만 조회가 가능 하며, 그이외의 건은 조회가 되지 않도록 한다.
	 * 단. 승인자ID가 데정팀 소속일경우 슈퍼유져이므로 다른 승인요청건 역시 보이도록 한다.
	 * @param commonDO 
	 * @return
	 * @throws RemoteException
	 */
	public String getMyDownloadAprroveDetailList(String cartNo, String user_id) throws RemoteException{
		logger.info("#####getMyDownloadAprroveDetailList##### cartNo : " + cartNo + " user_id: " + user_id);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		try {
			List _infolist = _processor.getMyDownloadAprroveDetailList(cartNo,user_id);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					CartContDOXML _doing = new CartContDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}
				_xml.append("</das>");
				//		logger.debug("[getMyDownloadAprroveDetailList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getMyDownloadAprroveDetailList", e);
			// TODO: handle exception
		}
		return "";
	}



	/**
	 * 메인화면
	 * @param 종료일
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getMainList() throws Exception {
		logger.info("######getTodayList Start########");
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		GoodMediaDOXML _DOXML = new GoodMediaDOXML();
		TodayDOXML _DOXML2 = new TodayDOXML();	
		CommunityBusinessProcessor _processor2 = new CommunityBusinessProcessor();	
		BoardInfoDOXML _doXML3 = new BoardInfoDOXML();
		StringBuffer _xml = new StringBuffer();
		try {
			List _infoList = _processor.getGoodMediaList();
			List _infoList2 = _processor.getTodayList();
			List _infoList3 = _processor2.getMainBoardList();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			_xml.append("<goods>");
			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					GoodMediaDOXML _do = new GoodMediaDOXML();
					_do.setDO(_iter.next());

					_xml.append(_do.getSubXML());

				}
			}
			_xml.append("</goods>");
			_xml.append("<todays>");
			if (_infoList2 != null && _infoList2.size() > 0) {

				Iterator _iter2 = _infoList2.iterator();
				while (_iter2.hasNext()) {
					TodayDOXML _do2 = new TodayDOXML();

					_do2.setDO(_iter2.next());

					_xml.append(_do2.getSubXML());

				}

			}
			_xml.append("</todays>");
			_xml.append("<boardinfos>");
			if (_infoList3 != null && _infoList3.size() > 0) {

				Iterator _iter3 = _infoList3.iterator();
				while (_iter3.hasNext()) {
					BoardInfoDOXML _do3 = new BoardInfoDOXML();

					_do3.setDO(_iter3.next());

					_xml.append(_do3.getSubXML());

				}

			}
			_xml.append("</boardinfos>");


			_xml.append("</das>");
			//	if (logger.isDebugEnabled())
			//	logger.debug("_xml" + _xml);


			return _xml.toString();


		} catch (Exception e) {
			logger.error("getMainList", e);
		}finally{
			logger.debug("######getMainList End########");
		}
		return "";
	}



	/**
	 * 타시스템과 직원 정보를 동기화한다.
	 * @param employeeRoleConditionDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getOtherEmployeeList(String sbs_user_id) throws RemoteException{

		logger.info("######getOtherEmployeeList######## sbs_user_id : " + sbs_user_id );

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		AllOtherDBUserInfoDOXML _doXML = new AllOtherDBUserInfoDOXML();

		try {

			List _infoList = _processor.getOtherEmployeeList(sbs_user_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AllOtherDBUserInfoDOXML _do2 = new AllOtherDBUserInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getOtherEmployeeList", e);
		}
		logger.debug("######getOtherEmployeeList########");
		return "";
	}



	/**
	 * 타시스템과 부서 정보를 동기화한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getOhterDepInfoList(String cocd, String dept_cd) throws RemoteException{

		logger.info("######getOhterDepInfoList######## cocd : " + cocd + " dept_cd : " + dept_cd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		AllOtherDBDeptInfoDOXML _doXML = new AllOtherDBDeptInfoDOXML();


		try {

			List _infoList = _processor.getOhterDepInfoList(cocd,dept_cd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AllOtherDBDeptInfoDOXML _do2 = new AllOtherDBDeptInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.toXML());
				}
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getOhterDepInfoList", e);
		}
		logger.debug("######getOhterDepInfoList end########");
		return "";
	}



	/**
	 * 메인화면
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws Exception 
	 */
	@Deprecated
	public String getMainKeyList()throws Exception{
		logger.info("######getMainKeyList########");

		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		CodeDOXML _doXML = new CodeDOXML();

		//CodeDO _do = (CodeDO) _doXML.setDO(codeDO);
		try {

			List _infoList = _processor.getMainKeyList();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		logger.debug("#####getMainKeyList end########");
		return "";

	}

	/**
	 * 직원 신규로 신청.
	 * @param nonEmployeeDASRoleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertRealEmployeeRole(String employeeInfoDO) throws RemoteException{

		logger.info("######insertRealEmployeeRole######## employeeInfoDO : " + employeeInfoDO);
		EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
		try {
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertRealEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("insertRealEmployeeRole", e);
		}
		//	logger.debug("######endinsertEmployeeRole########");
		return 0;

	}

	/**
	 * PDAS 아카이브 요청(PART 1.메타)
	 * 2015.10.28 등록 로직 재구성 -> 오류 발생시 일괄 롤백처리
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int insertPDSArchive(String pdasArchiveDO) throws RemoteException{

		logger.info("######insertPdasArchive######## pdasArchiveDO : "+pdasArchiveDO);


		PdsArchiveDOXML _doXML = new PdsArchiveDOXML();
		try {
			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(pdasArchiveDO);	


			IfCmsArchiveDOXML _doXML2 = new IfCmsArchiveDOXML();
			IfCmsArchiveDO _do2 = (IfCmsArchiveDO) _doXML2.setDO(pdasArchiveDO);	

			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			if(_do2.getVersion().startsWith("1.")){
				int result = _processor.insertNewPdasArchive(_do);
				logger.debug("######end insertPdasArchive  ########" + result);

				return result;

			}else{ 
				int result = _processor.insertNewIfCmsArchive(_do2);
				logger.debug("######end insertPdasArchive ifcms ########"+result);

				return result;
			}
		} catch (Exception e) {
			logger.error("insertPdasArchive", e);
			throw new RemoteException("insertPDSArchive", e.getCause());
		}
	}


	/**
	 * PDAS 아카이브  상태변환(PART 2. 콘텐츠)
	 * @param pdasArchiveDO   state (000[정상], 011[비정상])                                                                                                                                                                                           
	 * @return  1, 0, -1
	 * @throws DASException
	 *  */
	public int updatePDSArchiveStatus(String pdasArchiveDO) throws RemoteException {

		logger.info("######updatePDSArchiveStatus######## pdasArchiveDO : "+pdasArchiveDO);
		try {
			PdsArchiveDOXML _doXML = new PdsArchiveDOXML();
			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(pdasArchiveDO);
			IfCmsArchiveDOXML _doXML2 = new IfCmsArchiveDOXML();
			IfCmsArchiveDO _do2 = (IfCmsArchiveDO) _doXML2.setDO(pdasArchiveDO);


			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			int resutl=0;
			if(_do2.getVersion().startsWith("1.")){
				resutl =_processor.updatePDSArchiveStatus(_do);
			}else{
				resutl 	=_processor.updateIfCmsArchiveStatus(_do2);	
			}
			logger.debug("resutl: "+resutl);
			return resutl;
		} catch (Exception e) {
			logger.error("updatePDSArchiveStatus", e);
			throw new RemoteException("updatePDSArchiveStatus", e.getCause());
		}
	}

	/**  
	 * 검수완료처리한다
	 * @param     master_id                                                                                                                                                                                    
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException 
	 */
	public String updateAccept(long master_id) throws Exception{

		logger.info("######updateAccept######## master_id : " + master_id);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return	_processor.updateAccept(master_id);
		} catch (Exception e) {
			logger.error("updateAccept", e);
		}
		logger.debug("######updateAccept end########");
		return "0";

	}

	/**  
	 * 정리완료처리한다
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws DASException 
	 */
	@Deprecated
	public String updateArrange(long master_id) throws RemoteException{

		logger.debug("######updateArrange########");

		logger.debug("######updateArrange end########");
		return "0";

	}

	/**  
	 * 정리완료처리한다
	 * 정리완료시 정리완료자의 id를 받아와 저장해준다.
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException 
	 */
	public String updateArrange2(long master_id, String user_id) throws Exception{

		logger.info("######updateArrange######## master_id : " + master_id  + " user_id: " + user_id);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return	_processor.updateArrange(master_id, user_id);
		} catch (Exception e) {
			logger.error("", e);
		}
		logger.debug("######updateArrange end########");
		return "0";

	}
	/**
	 * PDS DOWN
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getPDSList(String PdsDownDO) throws RemoteException{
		logger.info("#####getPDSList##### PdsDownDO : "+PdsDownDO);
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			PdsDownDOXML _doXML = new PdsDownDOXML();
			PdsDownDO _do = (PdsDownDO)_doXML.setDO(PdsDownDO);

			List _infolist = _processor.getPDSList(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> \n" );
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					PdsDownDOXML _doing = new PdsDownDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}

				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getPDSList", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * NDS DOWN
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getNDSList(String NdsDownDO) throws RemoteException{
		logger.info("#####getNDSList##### NdsDownDO : " + NdsDownDO);
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			NdsDownDOXML _doXML = new NdsDownDOXML();
			NdsDownDO _do = (NdsDownDO)_doXML.setDO(NdsDownDO);

			List _infolist = _processor.getNDSList(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> \n" );
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				while(_iter.hasNext()){
					NdsDownDOXML _doing = new NdsDownDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getSubXML());
				}

				//	logger.debug("[getNDSList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getNDSList", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 * 기본정보 초기화면
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getRepBaseInfo(long master_id) throws RemoteException {
		logger.info("######getRepBaseInfo######## master_id : " + master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getRepBaseInfo(master_id);
		} catch (Exception e) {
			logger.error("getRepBaseInfo", e);
		}
		return "";

	}

	/**
	 * 토큰을 통한 사용자 인증
	 * @param TokenDO
	 * @return
	 * @throws RemoteException
	 */

	public String isValidUserWithToken(String TokenDO) throws RemoteException{
		logger.info("#####isValidUserWithToken##### TokenDO : " +TokenDO);
		try {
			LoginBusinessProcessor _processor = new LoginBusinessProcessor();

			TokenDOXML _doXML = new TokenDOXML();

			/**
			 * 복호화 로직 추가 부분.. DEKIM 20101129
			 * descript(TokenDO); 
			 */
			TokenDO _do = (TokenDO)_doXML.setDO(TokenDO);

			LoginBusinessProcessor _processor1 = new LoginBusinessProcessor();

			TokenDO _tdo =_processor1.isValidUserWithToken(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> <das>\n" );
			if(_tdo != null){
				TokenDOXML _doing = new TokenDOXML();
				_doing.setDO(_tdo);
				_xml.append( _doing.getSubXML());

				_xml.append("</das> \n" );
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("isValidUserWithToken", e);
			// TODO: handle exception
		}
		return "";

	}

	/**
	 * 토큰 없이 사용자 인증
	 * @param TokenDO
	 * @return
	 * @throws RemoteException
	 */
	public String isValidUser(String TokenDO)throws RemoteException{
		logger.info("#####isValidUser##### TokenDO : "+TokenDO);
		try {
			TokenDOXML _doXML = new TokenDOXML();

			/**
			 * 복호화 로직 추가 부분.. DEKIM 20101129
			 * descript(TokenDO);   
			 */
			logger.debug("TokenDO" + TokenDO);
			TokenDO _do = (TokenDO)_doXML.setDO(TokenDO);

			LoginBusinessProcessor _processor1 = new LoginBusinessProcessor();

			TokenDO _tdo = _processor1.isValidUser(_do);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> <das>\n" );
			if(_tdo != null){
				TokenDOXML _doing = new TokenDOXML();
				_doing.setDO(_tdo);
				_xml.append( _doing.getSubXML());
				//	logger.debug(_xml.toString());
				_xml.append("</das> \n" );
				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("isValidUser", e);
			// TODO: handle exception
		}
		return "";
	}

	/**
	 *  Backend TC 연동 하여 JOB을 할당하는 인터페이스
	 * (주기적 요청으로 Job 할당시 필요한 인터페이스이나 파일 베이스로 JOB push 시에 필요 없게 된 인터페이스) 
	 * @param tcBeanDO
	 * @return  Job 전달
	 * @throws RemoteException
	 */
	@Deprecated
	public String insertReqJobTC(String tcBeanDO)throws RemoteException{
		return "";
	}

	/**
	 * Backend TC 연동 하여 상태값을 변경하는 인터페이스
	 * @param tcBeanDO
	 * @return
	 * @throws RemoteException
	 */
	public String updateTcState(String tcBeanDO)throws RemoteException{
		//logger.debug("#####updateTcState START#####  tcBeanDO : "   +tcBeanDO);
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			TcBeanDOXML _doXML = new TcBeanDOXML();
			TcBeanDO _do = (TcBeanDO)_doXML.setDO(tcBeanDO);


			boolean result = _processor.updateTCState(_do);
			//	logger.debug("#####updateTcState END#####     ");
			return Boolean.toString(result);
		} catch (Exception e) {
			logger.error("updateTcState", e);
		}
		return "";
	}


	/**
	 * Backend Tc 연동 하는 작업 완료 요청 인터페이스(재요청, 작업완료)
	 * @param tcBeanDO
	 * @return
	 * @throws RemoteException
	 */
	public String insertReqComTc(String tcBeanDO)throws RemoteException{
		logger.info("#####insertReqComTc ##### tcBeanDO " + tcBeanDO);
		try {
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			TcBeanDOXML _doXML = new TcBeanDOXML();
			TcBeanDO _do = (TcBeanDO)_doXML.setDO(tcBeanDO);

			StringBuffer _xml = new StringBuffer();
			TcBeanDO do2 = _processor.insertReqJobTC(_do); //updateReqComTc(_do);

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" );
			_xml.append("<das> \n" );
			TcBeanDOXML _doing = new TcBeanDOXML();
			if(do2==null){
				_doing.setDO(_do);
				_do.setResult("F");
			}else{
				_doing.setDO(do2);
				do2.setResult("S");
			}

			_xml.append( _doing.getSubXML2());
			_xml.append("</das> \n" );
			return _xml.toString();
		} catch (Exception e) {
			logger.error("insertReqComTc", e);
		}

		return "";
	}


	/**
	 * 직원 업데이트
	 * @param EmployeeDASRoleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int updateRealEmployeeRole(String employeeInfoDO) throws RemoteException{
		try {
			logger.info("######updateRealEmployeeRole######## employeeInfoDO : " + employeeInfoDO);
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.updateRealEmployeeRole(_do);
		} catch (Exception e) {
			logger.error("updateRealEmployeeRole", e);
		}
		//	logger.debug("######endinsertEmployeeRole########");
		return 0;

	}


	/**
	 * 직원 승인.
	 * @param employeeInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public int updateRealEmployeeRoleYN(String employeeInfoDO) throws RemoteException{
		try {
			logger.info("######updateRealEmployeeRoleYN######## employeeInfoDO : " + employeeInfoDO);
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.updateRealEmployeeRoleYN(_do);
		} catch (Exception e) {
			logger.error("updateRealEmployeeRoleYN", e);
		}
		//	logger.debug("######endinsertEmployeeRole########");
		return 0;

	}
	/**
	 * 직원 승인.
	 * @param employeeInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public String updateRealEmployeeRoleCharYN(String employeeInfoDO) throws RemoteException{
		try {
			logger.info("######updateRealEmployeeRoleYN######## employeeInfoDO : " + employeeInfoDO);
			EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();



			StringBuffer _xml = new StringBuffer();
			int result = _processor.updateRealEmployeeRoleYN(_do);//updateReqComTc(_do);

			//TcBeanDO do3 = new TcBeanDO();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" );
			_xml.append("<das> \n" );
			_xml.append("<response> \n" );
			_xml.append("<result> \n" );
			_xml.append(result );
			_xml.append("</resutl> \n" );
			_xml.append("</response> \n" );


			_xml.append("</das> \n" );
			return _xml.toString();
		} catch (Exception e) {
			logger.error("updateRealEmployeeRoleCharYN", e);
			// TODO: handle exception
		}
		//	logger.debug("######endinsertEmployeeRole########");
		return "0";

	}


	/**
	 * PDS folderList 정보를 등록한다.(벌크)
	 * PDS Soap Call ( pds_ex_get_programinfo)
	 * @param pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String insertPdsFolderAll2(String user_id) throws RemoteException{
		logger.info("######insertPdsFolderAll2######## user_id " + user_id);
		try {
			AllDepInfoDOXML _doXML = new AllDepInfoDOXML();
			//	List _result = (List)_doXML.setDO(PdsFolderDO);	
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();


			return _processor.insertPdsFolderAll(user_id);
			//} catch (Exception e) {
		} catch (Exception e) {
			logger.error("insertPdsFolderAll2", e);
		}
		logger.debug("######insertPdsFolderAll2 end########");
		return "0";
	}



	/**
	 * NDS folderList 정보를 등록한다.(벌크)
	 * NDSam pdsInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throw Soap Call ( nds_ex_service_folderlist )
	 * @pars DASException
	 */
	public String insertNdsFolderAll(String user_id) throws RemoteException{
		logger.info("######insertNdsFolderAll######## user_id : " + user_id);
		AllDepInfoDOXML _doXML = new AllDepInfoDOXML();
		//	List _result = (List)_doXML.setDO(PdsFolderDO);	
		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		try {

			return _processor.insertNdsFolderAll(user_id);
		} catch (Exception e) {
			logger.error("insertNdsFolderAll", e);
		}
		logger.debug("######insertNdsFolderAll end########");
		return "0";
	}


	/**
	 * PDS folderList 정보를 등록한다( 삭제불가)
	 * @return
	 * @throws RemoteException
	 */
	public int insertPdsFolderAll() throws RemoteException{
		logger.debug("######insertPdsFolderAll dummy########");
		return 0;
	}

	/**
	 * 본부코드와 본부명를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getSupHeadList(String COCD) throws RemoteException{

		logger.info("######getSupHeadList######## COCD : " + COCD);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();

		//DepInfoDO _do = (DepInfoDO) _doXML.setDO(depInfoDO);
		try {

			List _infoList = _processor.getSupHeadList(COCD);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DepDOXML _do2 = new DepDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//		if (logger.isDebugEnabled())
				//			logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getSupHeadList", e);
		}
		logger.debug("######getSupHeadList end########");
		return "";
	}

	/**
	 * 첨부파일 정보를 조회한다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getAttachFileInfo(long master_id) throws RemoteException {
		logger.info("######getAttachFileInfo######## master_id : " + master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.getAttachFileInfo(master_id);
		} catch (Exception e) {
			logger.error("getAttachFileInfo", e);
		}
		return "";
	}




	/**
	 * 다운카트의 에러난것을 재요청한다. DAS2.0
	 * @param downCartDO
	 * @return
	 * @throws RemoteException
	 */
	public int updateErrorDownCart(String downCartDO)throws RemoteException{
		logger.info("#####updateErrorDownCart start##### downCartDO : "+downCartDO);
		try {
			DownCartDOXML _doXML = new DownCartDOXML();
			DownCartDO _do = (DownCartDO) _doXML.setDO(downCartDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateErrorDownCart(_do);
		} catch (Exception e) {
			logger.error("updateErrorDownCart", e);
		}
		return 0;

	}

	/**
	 * 검색엔진을 통한 조회(상세검색만)
	 * @param searchInfoDO
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String getSearchTextForDetail(String searchInfoDO) throws RemoteException{

		return "";
	}

	/**
	 * 검색엔진을 통한 조회(검색어만)
	 * @param searchInfoDO
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String getSearchTextForKey(String searchInfoDO) throws RemoteException{

		return "0";
	}



	/**
	 * 대,중,소 각기 코드 정보를 조회한다.
	 * @param clf_cd
	 * @param scl_cd
	 * @return
	 * @throws RemoteException
	 */
	public String getCode(String codeDO)throws RemoteException{
		logger.info("######getCode######## codeDO : " + codeDO);

		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		CodeDOXML _doXML = new CodeDOXML();


		try {

			List _infoList = _processor.getCode(codeDO);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getCode", e);
		}
		logger.debug("#####getCode end #######");
		return "";
	}





	/**
	 * 공지사항을 첨부파일정보를 조회한다
	 * @param boardId                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getBoardAtaachInfo(int boardId) throws RemoteException{

		logger.info("######getBoardAtaachInfo######## boardId : "+boardId);

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();


		try { 
			List _infoList = _processor.getBoardAtaachInfo(boardId);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					FileInfoDOXML _do2 = new FileInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getBoardAtaachInfo", e);
		}
		logger.debug("######getBoardAtaachInfo end########");
		return "";
	}




	/**
	 * 공지사항 첨부파일정보를 저장한다.
	 * @param cartContDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws DASException
	 */
	public int insertBoardAtaachInfo(String FileInfoDO) throws RemoteException{

		logger.info("######insertBoardAtaachInfo######## FileInfoDO : "+FileInfoDO);
		try {
			FileInfoDOXML _doXML = new FileInfoDOXML();
			///	List _result = (List)_doXML.setDO(FileInfoDO);	
			FileInfoDO _do = (FileInfoDO) _doXML.setDO(FileInfoDO);		
			CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();

			return _processor.insertBoardAtaachInfo(_do);
		} catch (Exception e) {
			logger.error("insertBoardAtaachInfo", e);
		}
		logger.debug("######insertBoardAtaachInfo end########");
		return 0;

	}


	/**
	 * 공지사항 첨부파일정보를 수정한다.
	 * @param boardDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updateBoardAtaachInfo(String FileInfoDO) throws RemoteException{

		logger.info("######updateBoardAtaachInfo######## FileInfoDO : "+FileInfoDO);
		try {
			FileInfoDOXML _doXML = new FileInfoDOXML();
			FileInfoDO _do = (FileInfoDO) _doXML.setDO(FileInfoDO);		
			CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();

			return _processor.updateBoardAtaachInfo(_do);
		} catch (Exception e) {
			logger.error("updateBoardAtaachInfo", e);
		}
		logger.debug("######updateBoardAtaachInfo end########");
		return 0;

	}
	/**  
	 * 기본정보 보존기간을 수정한다
	 * @param     rsv_prd_cd                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws DASException 
	 */
	public String updateRsv_Prd_DD(String rsv_prd_cd,long master_id) throws RemoteException{

		logger.info("######updateRsv_Prd_DD######## rsv_prd_cd : " + rsv_prd_cd + " master_id: " + master_id);
		//EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
		//EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return	_processor.updateRsv_Prd_DD(rsv_prd_cd, master_id);
		} catch (Exception e) {
			logger.error("updateRsv_Prd_DD", e);
		}
		logger.debug("######updateRsv_Prd_DD end########");
		return "0";

	}

	/**   
	 * 첨부파일을 삭제한다.
	 * @param attachFilename 삭제할 파일 이름
	 * @param file_type 파일 타입
	 * @param clf_cd 코드.
	 * @return 성공하면 1, 실패하면 0
	 * @throws RemoteException
	 */
	public int deleteBoardAttachFile(String attachFilename,String fl_path,	int boardId) throws RemoteException {
		logger.info("######deleteBoardAttachFile111######## attachFilename : " + attachFilename + " fl_path : " + fl_path + " boardId: " + boardId );
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteBoardAttachFile(attachFilename,fl_path,	boardId);
		} catch (Exception e) {
			logger.error("deleteBoardAttachFile111", e);
		}
		return 1;
	}



	/**
	 * 국코드와 국명를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getSupHtpoList(String COCD) throws RemoteException{

		logger.info("######getSupHtpoList######## COCD : " + COCD);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();

		try {

			List _infoList = _processor.getSupHtpoList(COCD);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DepDOXML _do2 = new DepDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getSupHtpoList", e);
		}
		logger.debug("######getSupHtpoList end########");
		return "";
	}



	/**
	 * 본부코드와 본부명를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getSupHeadList2(String deptcd) throws RemoteException{

		logger.info("######getSupHeadList2######## deptcd : "+deptcd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();

		try {

			List _infoList = _processor.getSupHeadList2(deptcd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DepDOXML _do2 = new DepDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getSupHeadList2", e);
		}
		logger.debug("######getSupHeadList end########");
		return "";
	}




	/**
	 * 부서코드와 부서명를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepinfoList(String deptcd) throws RemoteException{

		logger.info("######getDepinfoList######## deptcd : "+deptcd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();

		try {

			List _infoList = _processor.getDepinfoList(deptcd);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DepDOXML _do2 = new DepDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepinfoList", e);
		}
		logger.debug("######getSupHeadList end########");
		return "";
	}






	/**
	 * 부서정보기준 사용자 정보를 가져온다
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepinfoForuserList(String deptcd) throws RemoteException{

		logger.info("######getDepinfoForuserList######## deptcd : "+deptcd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		DepDOXML _doXML = new DepDOXML();

		try {

			List _infoList = _processor.getDepinfoForuserList(deptcd);

			StringBuffer _xml = new StringBuffer();
			Iterator _iter = _infoList.iterator();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			while (_iter.hasNext()) {
				EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
				_do2.setDO(_iter.next());
				_xml.append(_do2.getSubXML());
			}

			_xml.append("</das>");

			return _xml.toString();
			//	}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepinfoForuserList", e);
		}
		logger.debug("######getDepinfoForuserList end########");
		return "";
	}





	/**
	 * 다운로드 승인 조회한다.(등록시)
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getApproveInfoList(String ApprveDO) throws Exception{

		logger.info("######getApproveInfoList######## ApprveDO : "+ApprveDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);


			List _infoList = _processor.getApproveInfoList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ApproveInfoDOXML _do2 = new ApproveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getApproveInfoList", e);
		}
		logger.debug("######getApproveInfoList end########");
		return "";
	}


	/**
	 * 다운로드 승인 조회한다.
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getApproveInfo(String ApprveDO) throws Exception{

		logger.info("######getApproveInfo######## ApprveDO : "+ApprveDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {

			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);

			List _infoList = _processor.getApproveInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ApproveInfoDOXML _do2 = new ApproveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getApproveInfoList", e);
		}
		logger.debug("######getApproveInfoList end########");
		return "";
	}




	/**
	 * 승인정보를 저장한다
	 * @param codeDO
	 * @return
	 * @throws Exception 
	 */
	public int insertApproveInfo(String ApprveDO)throws Exception{
		logger.info("#####insertApproveInfo##### ApprveDO : " + ApprveDO );
		ApproveInfoDOXML2 _doXML = new ApproveInfoDOXML2();
		//ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);
		try {
			List _result = (List)_doXML.setDO(ApprveDO);	
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertApproveInfo(_result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertApproveInfo", e);
		}
		return 0;
	}



	/**
	 * 승인정보를 삭제한다
	 * @param user_no
	 * @return
	 * @throws Exception 
	 */
	public void deleteApproveInfo(String xml) throws Exception {
		logger.info("######deleteApproveInfo######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(xml);
			//List _result = (List)_doXML.setDO(ApprveDO);	

			_processor.deleteApproveInfo2(_do);
		} catch (Exception e) {
			logger.error("deleteApproveInfo", e);
		}


	}
	/**
	 * 승인정보를 삭제한다
	 * @param user_no
	 * @param dept_cd
	 * @return
	 * @throws Exception 
	 */
	public int deleteApproveInfo2(String user_no,String dept_cd) throws Exception {
		logger.info("######deleteApproveInfo######## user_no : " + user_no + " dept_cd: " + dept_cd);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return	_processor.deleteApproveInfo(user_no,dept_cd);
		} catch (Exception e) {
			logger.error("deleteApproveInfo2", e);
		}
		return 0;
	}




	/**
	 * 프로그램코드 조회한다 (das 2.0 신규버젼).(PDS_CMS_PGM_ID 기준)
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getProgramInfo2(String title) throws Exception{

		logger.info("######getProgramInfo2 ######## title : " + title);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			List _infoList = _processor.getPgmInfo2(title);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					ProgramInfoDOXML _do2 = new ProgramInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();


			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getProgramInfo2", e);
		}
		logger.debug("######end  getProgramInfo2########");
		return "";
	}






	/**
	 * WMV 재생성 요청(클라이언트 신청시)
	 * 2009년 9월 3일 김건학 실장님 요청에 의한 추가 사항(DEKIM)
	 * @param cti_id
	 * @param user_nm
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV_NMforClient(long ct_id, String user_nm)
			throws Exception {
		logger.info("######recreateWMV_NM######## ct_id : " + ct_id + " user_nm : " + user_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMVForClient(ct_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateWMV_NMforClient", e);
		}
		return "";
	}
	/**
	 * WMV 재생성 요청(클라이언트 신청시)
	 * 2009년 9월 3일 김건학 실장님 요청에 의한 추가 사항(DEKIM)
	 * @param cti_id
	 * @param user_nm
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV_NMforMainSean(long master_id , String user_nm)
			throws Exception {
		logger.info("######recreateWMV_NM######## master_id : " + master_id + " user_nm: " + user_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMVforMainSean(master_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateWMV_NMforMainSean", e);
		}
		return "";
	}

	/**
	 * WMV + KFRM 동시생성.(클라이언트 신청시)
	 * @param ct_id
	 * @param user_nm
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV_KFRMforClient(long ct_id,String user_nm) throws Exception{
		logger.info("######recreateWMV_KFRM##### ct_id : " + ct_id + " user_nm: " + user_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMV_KFRMForClient(ct_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateWMV_KFRMforClient", e);
		}
		return "";
	}

	/**
	 * WMV + KFRM 동시생성.(클라이언트 신청시)
	 * @param ct_id
	 * @param user_nm
	 * @return
	 * @throws Exception 
	 */
	public String recreateWMV_KFRMMainSean(long master_id,String user_nm) throws Exception{
		logger.info("######recreateWMV_KFRM##### master_id : " + master_id + " user_nm: " + user_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.recreateWMV_KFRMforMainSean(master_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateWMV_KFRMMainSean", e);
		}
		return "";
	}

	/**
	 * 키프레임 생성(클라이언트 신청시)
	 * @param ct_id 콘텐츠id 
	 * @param user_nm 사용자 이름
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateKFRMforClient(long ct_id,String user_nm) throws Exception{
		logger.info("######recreateKFRM##### ct_id : " + ct_id + " user_nm: "+ user_nm );
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			//TcBeanDO tccd = externalDAO.selectTcInfo(ct_id);

			return _processor.recreateKFRM(ct_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateKFRMforClient", e);
		}
		return "";
	}

	/**
	 * 키프레임 생성(클라이언트 신청시)
	 * @param ct_id 콘텐츠id 
	 * @param user_nm 사용자 이름
	 * @return updatecount
	 * @throws Exception 
	 */
	public String recreateKFRMMainSean(long master_id,String user_nm) throws Exception{
		logger.info("######recreateKFRM##### master_id : "  + master_id + " user_nm: " + user_nm);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			//TcBeanDO tccd = externalDAO.selectTcInfo(ct_id);

			return _processor.recreateKFRMforMainSean(master_id, user_nm);
		} catch (Exception e) {
			logger.error("recreateKFRM", e);
		}
		return "";
	}

	/**
	 * 프로그램별 승인 목록에서 사용자 사번과 직책을 구해온다
	 * @param employeeRoleConditionDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getEmployeeListForApp(String dep_cd, String user_nm) throws RemoteException{

		logger.info("######getEmployeeList######## dep_cd : "+dep_cd+" user_nm : "+user_nm);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

		try {

			List _infoList = _processor.getEmployeeListForApp(dep_cd,user_nm);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");
				//	if (logger.isDebugEnabled())
				//		logger.debug("_xml" + _xml);
				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getEmployeeListForApp", e);
		}
		logger.debug("######endgetEmployeeList########");
		return "";
	}


	/**
	 * 공지사항을 조회한다.팝업용(오늘 날짜를 받는다)
	 * @param boardDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getBoardInfoForPopUp(String today) throws Exception{

		logger.info("######getBoardInfoForPopUp######## today : " +today);

		CommunityBusinessProcessor _processor = new CommunityBusinessProcessor();


		try { 
			List _infoList = _processor.selectMainBoardList2(today);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					BoardInfoDOXML _do2 = new BoardInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getBoardInfoForPopUp", e);
		}
		logger.debug("######end getBoardInfoForPopUp########");
		return "0";
	}




	/**  
	 * 사진탭 메타정보를 수정한다
	 * @param     photoInfoDO                                                                                                                                                                                     
	 * @return   updatecount                                                                                                                                                                                           
	 * @throws Exception 
	 * @throws DASException 
	 */
	public int updatePhotInfo(String photoInfoDO) throws Exception{

		logger.info("######updatePhotInfo######## + photoInfoDO : " + photoInfoDO);
		AttatchPhotoInfoDOXML _doXML = new AttatchPhotoInfoDOXML();
		try {
			PhotoInfoDO _do = (PhotoInfoDO) _doXML.setDO(photoInfoDO);		
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return	_processor.updatePhotInfo(_do);
		} catch (Exception e) {
			logger.error("updatePhotInfo", e);
		}
		logger.debug("######updatePhotInfo end########");
		return 0;

	}

	/**
	 * 로그인시 역활정보를 조회한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleForLogin(String user_id) throws RemoteException{

		logger.info("######getRoleForLogin######## user_id : "  + user_id);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		RoleForLoginDOXML _doXML = new RoleForLoginDOXML();

		try {

			List _infoList = _processor.getRoleForLogin(user_id);


			StringBuffer _xml=new StringBuffer();

			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				RoleForLoginDOXML _do2 = new RoleForLoginDOXML();


				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML2());
					i++;
				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleForLogin", e);
		}
		logger.debug("######end  getRoleForLogin########");
		return "";
	}


	/**
	 * archive 요청job  등록
	 * @param archiveReqDO
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public int insertArchiveReq(String archiveReqDO) throws Exception{
		//logger.debug("######insertArchiveReq##### archiveReqDO : "+archiveReqDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {
			return _processor.insertArchiveReq(archiveReqDO);
		} catch (Exception e) {
			logger.error("insertArchiveReq", e);
		}
		return 0;

	}


	/**
	 * archive 연동 하여 상태값을 변경하는 인터페이스
	 * @param archiveReqDO
	 * @return
	 * @throws RemoteException
	 */
	public String updateArchiveReq(String archiveReqDO)throws RemoteException{
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ArchiveReqDOXML _doXML = new ArchiveReqDOXML();
		try {
			ArchiveReqDO _do = (ArchiveReqDO)_doXML.setDO(archiveReqDO);

			boolean result = _processor.updateArchiveReq(_do);
			return Boolean.toString(result);
		} catch (Exception e) {
			logger.error("updateArchiveReq", e);
		}
		return "false";
	}

	/**
	 * archive  연동 하는 작업 완료 요청 인터페이스
	 * @param archiveReqDO
	 * @return
	 * @throws RemoteException
	 */
	public String insertComArchivereq(String archiveReqDO)throws RemoteException{
		logger.info("#####insertComArchivereq ##### archiveReqDO : "+archiveReqDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ArchiveReqDOXML _doXML = new ArchiveReqDOXML();
		try {
			ArchiveReqDO _do = (ArchiveReqDO)_doXML.setDO(archiveReqDO);

			ArchiveReqDO do2 = _processor.insertReqJobArchive(_do);//updateReqComTc(_do);

			return do2.getResult();
		} catch (Exception e) {
			logger.error("insertComArchivereq", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * 자신의  암호 정보를 수정한다
	 * @param employeeRoleConditionDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updatePasswd(String employeeInfoDO) throws RemoteException{

		logger.info("######updatePasswd######## employeeInfoDO : "+employeeInfoDO);
		EmployeeInfoDOXML _doXML = new EmployeeInfoDOXML();
		try {
			EmployeeInfoDO _do = (EmployeeInfoDO) _doXML.setDO(employeeInfoDO);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updatePasswd(_do);
		} catch (Exception e) {
			logger.error("updatePasswd", e);
		}
		logger.debug("######updatePasswd end########");
		return 0;

	}

	/**
	 * NLE 아카이브 요청(메타)
	 * @param nleDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 */
	public int insertNleArchive(String nleDO) throws Exception{

		logger.info("######insertNleArchive######## nleDO : "+nleDO);

		NleDOXML _doXML = new NleDOXML();
		try {
			NleDO _do = (NleDO)_doXML.setDO(nleDO);	
			logger.debug("_do    "+_do);
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
			return _processor.insertNleArchive(_do);
		} catch (Exception e) {
			logger.error("insertNleArchive", e);
		}
		logger.debug("######insertPdasArchive########");
		return 0;
	}

	/**
	 * ERP 정보를 테이블에 등록한다.(벌크)
	 * @param ErpAppointDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int getOrderInfo() throws RemoteException{
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String retVal = _processor.getOrderInfo();
			if(StringUtils.isBlank(retVal)) return -1;
			else
				return insertERPUserInfo(retVal);
		} catch (Exception e) {
			logger.error("getOrderInfo", e);
			return 0;
		}
	}


	/**
	 *테스트 함수들
	 */
	public String testServiceTest(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.serviceTest(arg0);
			//System.out.println("port.serviceTest(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testDownloadService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.downloadService(arg0);
			//System.out.println("port.downloadService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testReportService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.reportService(arg0);
			//	System.out.println("port.reportService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}

	public String testGetResArchieve(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			//		sResult =port.getResArchieve(arg0);
			//System.out.println("port.getResArchive(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}


	public String testNleIngestService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult =port.nleIngestService(arg0);
			//	System.out.println("port.nleIngestService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testGetResDelete(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			//		sResult =port.getResDelete(arg0);
			//System.out.println("port.getResDelete(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testDeleteContentService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			//sResult = port.deleteContentService(arg0);
			//System.out.println("port.deleteContentService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testIngestReportService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult =port.ingestReportService(arg0);
			//System.out.println("port.ingestReportService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testArchiveReportService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.archiveReportService(arg0);
			//System.out.println("port.archiveReportService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testGetResCopy(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			//		sResult =port.getResCopy(arg0);
			//System.out.println("port.getResCopy(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testFileIngestService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.fileIngestService(arg0);
			//System.out.println("port.fileIngestService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testAddClipInfoService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult = port.addClipInfoService(arg0);
			//System.out.println("port.addClipInfoService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testGetResUpdate(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			//		sResult = port.getResUpdate(arg0);
			//System.out.println("port.getResUpdate(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testGetResRestore(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult =port.fileIngestService(arg0);
			//System.out.println("port.fileIngestService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testArchiveSchedulerService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult =port.archiveSchedulerService(arg0);
			//	System.out.println("port.archiveSchedulerService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testDeleteClipInfoService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult ="";
		try {
			sResult =port.recoveryService(arg0);
			//System.out.println("port.deleteClipInfoService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}
	public String testArchiveService(String arg0) throws RemoteException{
		NevigatorProxy port = new NevigatorProxy();
		String sResult="";
		try {
			sResult = port.archiveService(arg0);
			//System.out.println("port.archiveService(arg0) ["+sResult+"]");
		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return sResult;
	}

	// 테스트 함수 끝






	/**
	 * 이용자별 다운로드 목록을 조회한다
	 * 다운로드 현황메뉴에서 사용하는 함수. cart기준으로 조회하는 다운로드 관리와는 달리
	 * 다운로드 유저id 기준으로 정보를 제공
	 * @param cartItemDO                                                                                                                                                                                            
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                            
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getCartInfoForUser( String  cartItemDO )
			throws Exception {
		logger.info("######getCartInfoForUser######## cartItemDO : "+cartItemDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		CartItemDOXML _doXML = new CartItemDOXML();
		try {
			CartItemDO _do = (CartItemDO)_doXML.setDO(cartItemDO);


			List _infoList = _processor.getCartInfoForUser(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CartItemDOXML _do2 = new CartItemDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getCartInfoForUser", e);
		}
		logger.debug("######getApproveInfoList end########");
		return "";
	}








	/**
	 * 역할 코드 조회
	 * clf_cd가 A049인 건들중에서 USE_YN이 Y인 건에 대해서 조회를 한다.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleCode() throws RemoteException{

		logger.info("######getRoleInfoList########");

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

		try {

			List _infoList = _processor.getRoleInfo();

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					RoleInfoDOXML _do2 = new RoleInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();


			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleInfoList", e);
		}
		logger.debug("######getRoleInfoList edn########");
		return "";
	}


	/**
	 * 우클릭 삭제(영상선정). 단순 폐기
	 * 영상을 폐기 요청을 한다. MASTER_ID 단위 삭제를 요청한다. 
	 * MASETR_ID에 소속된 영상들은 모두 폐기 요청 처리되며, 이후
	 * 검색엔진, 영상선정에서 더이상 조회되지 않고, 페기관리의 폐기현황에서 상태 조회가능하다.
	 * 이 삭제건은 폐기 통계에 수집된다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public String deleteMasterScean(long master_id) throws Exception {
		logger.info("######deleteMasterScean######## master_id: "  + master_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.deleteMasterScean(master_id); 
		} catch (Exception e) {
			logger.error("deleteMasterScean", e);
		}
		return "0";
	}

	/**
	 * 우클릭 삭제(영상선정) 폐기요청자 입력 버전
	 * 영상을 폐기 요청을 한다. MASTER_ID 단위 삭제를 요청한다. 
	 * MASETR_ID에 소속된 영상들은 모두 폐기 요청 처리되며, 이후
	 * 검색엔진, 영상선정에서 더이상 조회되지 않고, 페기관리의 폐기현황에서 상태 조회가능하다.
	 * 이 삭제건은 폐기 통계에서 (getArchiveStatusList) 수집된다.
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */           
	public String deleteMasterScean2(String deleteDO) throws Exception {
		logger.info("######deleteMasterScean2######## deleteDO: "  + deleteDO);

		DeletePdsArchiveDOXML _doXML = new DeletePdsArchiveDOXML();
		try {
			DeleteDO _do = (DeleteDO) _doXML.setDO(deleteDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.deleteMasterScean2(_do); 
		} catch (Exception e) {
			logger.error("deleteMasterScean2", e);
		}
		return "0";
	}


	/**
	 * 수동아카이브 건에 편집시 편집상태 변경('001' 편집중, '002' 편집완료)
	 * 편집중으로 설정되어있을시 DTL 등록을 할수 없음
	 * @param ct_ids
	 * @return
	 * @throws Exception 
	 */
	public int updateEdtrId(String code,String ct_ids) throws Exception{

		logger.info("######updateEdtrId######## code : " + code + " ct_ids: " + ct_ids);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {
			return	_processor.updateEdtrId(code,ct_ids);
		} catch (Exception e) {
			logger.error("updateEdtrId", e);
		}
		logger.debug("######updateArrange end########");
		return 0;

	}







	/**
	 * 주제영상 코드 조회
	 * CLF_CD가 P018인 건들중에 GUBUN이 S인 건들을 조회
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getAnnotCode() throws RemoteException{

		logger.info("######getAnnotCode########");

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

		try {

			List _infoList = _processor.getAnnotCode();

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();


			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getAnnotCode", e);
		}
		logger.debug("######getRoleInfoList edn########");
		return "";
	}





	/**
	 * 아카이브 전송재요청한다. DAS2.0
	 * 이미 아카이브요 요청이 된건 중에서 아카이브 실패가 난건에 대해서 재요청을 하는 함수
	 * @param 
	 * @return
	 * @throws RemoteException
	 */
	public String updateRetryArchive(long seq)throws RemoteException{
		logger.info("#####updateRetryArchive start##### seq : "+seq);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateRetryArchive(seq);
		} catch (Exception e) {
			logger.error("updateRetryArchive", e);
		}
		return "실패";

	}



	/**
	 * 아카이브 전송재요청한다. DAS2.0
	 * 아카이브가 되기전에 임의의 이유로 인하여 아카이브를 정상적으로 요청하지 못한 건에 대해서
	 * 아카이브를 요청하는 함수. ct_id값으로 메타를  입력하여 아카이브를요청한다.
	 * @param 
	 * @return
	 * @throws RemoteException
	 */
	public String updateRetryArchiveByCtId(long ct_id )throws RemoteException{
		logger.info("#####updateRetryArchiveByCtId start##### ct_id : "+ct_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.updateRetryArchiveByCtId(ct_id);
		} catch (Exception e) {
			logger.error("updateRetryArchiveByCtId", e);
		}
		return "실패";

	}



	/**
	 * 코너의 내용을 입력한다
	 * 신규 데이터를 삽입하기전에 기존 데이터를 모두 삭제 처리한뒤 
	 * 코너 정보를 신규로 집어넣는다
	 * annot_info_Tbl에 바뀐 cn_id값을 업데이트 해준다.
	 * @param annotInfoDO                                                                                                                                    	
	 * @throws Exception 
	 * @throws DASException
	 */
	public String insertCornerContinfo(long cn_id, String cnInfoDO)
			throws Exception {

		logger.info("######insertCornerContinfo######## cn_id : " + cn_id + " cnInfoDO : " + cnInfoDO);

		CnDetailDOXML _doXML = new CnDetailDOXML();
		try {
			List _list = (List) _doXML.setDO(cnInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			List _infoList = _processor.insertCornerContinfo(cn_id,_list);

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CnDetailDOXML _do = new CnDetailDOXML();
					_do.setDO(_iter.next());
					_xml.append(_do.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			logger.error("insertCornerContinfo", e);
		}
		return "";
	}




	/**
	 * 다운로드 임계치 조회 함수
	 * 다운로드 진행하기전 사용량을 체크하여 제공한다.
	 * 이후 클라이언트는 남은 양과 다운 받고자하는 영상의 크기를 비교하여 다운로드 여부를 판단한다.
	 * @param XML string
	 * @return  XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getStorageInfo(String storageDO) throws Exception {
		logger.info("######getStorageInfo Start######## storageDO : "  + storageDO);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		StorageDOXML _DOXML = new StorageDOXML();
		try {
			StorageDO ConditionDO = (StorageDO) _DOXML.setDO(storageDO);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			List _infoList = _processor.getStorage(ConditionDO);


			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StorageDOXML _do = new StorageDOXML();
					_do.setDO(_iter.next());

					_xml.append(_do.getSubXML());

				}
			}

			_xml.append("</das>");


			return _xml.toString();


		} catch (Exception e) {
			logger.error("getStorageInfo", e);
		}finally{
			logger.debug("######getStorageInfo End########");
		}
		return "";
	}


	/**
	 * PDAS 아카이브 삭제 요청
	 * 2015.12.22 확인사항
	 * IFCMS 및 PDS에서 등록 요청을 할경우 DAS TM에서 100% 삭제 요청을 하고 있음.
	 * 기등록 프로그램이 있을경우 삭제 처리 시간이 최소 1분이 소요되기때문에 등록 실패가 많은 상황임.
	 * DAS TM과 등록 워크플로우를 바꾸는 상황이기에 삭제 요청이 왔을경우 media_id = 'delete'로 선 변경하고
	 * 삭제 스케줄러에 등록하도록 변경함. 즉, 삭제 요청 후 오류가 없다면 바로 등록이 가능함.
	 * 오류가 날경우 rollback 처리 하고 오류 메세지를 반환 함.
	 * 
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	public String deletePDSArchive(String deleteDO) throws Exception{

		logger.info("######deletePDSArchive########   deleteDO:  "+deleteDO);

		DeletePdsArchiveDOXML _doXML = new DeletePdsArchiveDOXML();
		try {
			//20130215
			DeleteDO _do = (DeleteDO) _doXML.setDO(deleteDO);
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			String result = _processor.deletePDSArchive(_do);

			return result;
		} catch (Exception e) {
			logger.error("deletePDSArchive", e);
			throw e;
		}
	}


	/**
	 * 미디어id를 생성한다
	 * 과거 미디어넷 메타데이터를 위한 미디어ID 생성 로직이 있었으나
	 * 더이상 사용되지 않으며 현재 사용되고 있는 로직은 사용등급 코드 정리 함수이다.
	 * 
	 * @param                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws DASException
	 */
	@Deprecated
	public String insertMediaId() throws RemoteException {

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {

			return _processor.updateRistClfCd();
		} catch (Exception e) {
			logger.error("insertMediaId", e);
		}
		return null;
	}


	/**
	 * 역활 정보를 조회한다.
	 * 입력된 역활명에 따른 권한 정보를 제공한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleInfoList(String roleInfoDO) throws RemoteException{

		logger.info("######getRoleInfoList######## roleInfoDO : "  + roleInfoDO);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		RoleInfoDOXML _doXML = new RoleInfoDOXML();
		AuthorRoleInfoDOXML _do2XML = new AuthorRoleInfoDOXML();
		try {
			RoleInfoDO _do = (RoleInfoDO) _doXML.setDO(roleInfoDO);


			List _infoList = _processor.getRoleInfoList(_do);
			List _infoList2 = _processor.getAuthroRoleList(_do);

			StringBuffer _xml= new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");
			_xml.append("<authorinfo>");
			if (_infoList2 != null && _infoList2.size() > 0) {
				Iterator _iter2 = _infoList2.iterator();

				AuthorRoleInfoDOXML _do3 = new AuthorRoleInfoDOXML();

				while (_iter2.hasNext()) {
					_do3.setDO(_iter2.next());
					_xml.append(_do3.getSubXML());

				}
			}
			_xml.append("</authorinfo>");
			_xml.append("<roleinfo>");
			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				RoleInfoDOXML _do2 = new RoleInfoDOXML();

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());

				}
				_xml.append("</roleinfo>");
				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleInfoList", e);
		}
		logger.debug("######getRoleInfoList edn########");
		return "";
	}




	// 2012.4.16 das2.0 확장 함수 부분 



	/**  
	 * 메타데이터의 정리 상태를 정한다.
	 * METADAT_MST_TBL의 DATA_STAT_CD 컬럼의 값을 변경하고
	 * 정리완료, 검수 완료, 에러 일경우에는 입력자 ID, 입력일도 같이 변경한다.
	 * 001 : 정리전, 003 : 정리완료, 007 : 검수완료
	 * 009 : 에러 
	 * @param     master_id                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException 
	 */
	public String updateDataStatCd(String xml) throws Exception{

		logger.info("######updateDataStatCd######## xml : " + xml);
		MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
		try {
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML.setDO(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateMetadataStatusCd(_do);
		} catch (Exception e) {
			logger.error("updateDataStatCd", e);
		}	       
		logger.debug("######updateArrange end########");
		return "0";

	}



	/**
	 * 미디어 메타데이터 조회를 한다.
	 * 미디어넷 메타 데이터 벌크 작업시 사용한 함수
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getClipInfoList(String xml) throws Exception{

		logger.info("######getClipInfoList######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MediaArchiveDOXML _doXML = new MediaArchiveDOXML();
		try {
			MediaArchiveDO _do = (MediaArchiveDO) _doXML.setDO(xml);


			List _infoList = _processor.getClipInfoList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					MediaArchiveDOXML _do2 = new MediaArchiveDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getClipInfoList", e);
		}
		logger.debug("######getClipInfoList########");
		return "";
	}



	/**
	 * 스토리지 용량 조회한다
	 * 모니터링 클라이언트으로 기능을 넘어갔으나
	 * DAS 클라이언트 화면에서는 여전히 서비스 제공중
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getStorageCheck(String xml) throws Exception{

		logger.info("######getStorageCheck######## xml : "+ xml);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		StorageDOXML _DOXML = new StorageDOXML();
		try {
			StorageDO ConditionDO = (StorageDO) _DOXML.setDO(xml);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

			List _infoList = _processor.getStorageCheck(ConditionDO);


			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					StorageDOXML _do = new StorageDOXML();
					_do.setDO(_iter.next());

					_xml.append(_do.getStorageCheck());

				}
			}

			_xml.append("</das>");


			logger.debug("######getStorageCheck########");
			return _xml.toString();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getStorageCheck", e);
		}

		return "";

	}



	/**  
	 * 인제스트 상태를 업데이트 한다.
	 * @param     xml                                                                                                                                                                                     
	 * @return                                                                                                                                                                                              
	 * @throws DASException 
	 */
	@Deprecated
	public String updateIngestStatus(String xml) throws RemoteException{

		logger.debug("######updateIngestStatus######## xml  : " + xml);

		logger.debug("######updateIngestStatus end########");
		return "0";

	}


	/**
	 * 회사정보조회(채널 기준)
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getCocdForChennel(String xml) throws Exception{

		logger.info("######getCocdForChennel######## xml : " + xml);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		try {

			List _infoList = _processor.getCocdForChennel();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getCocdForChennel", e);
		}
		logger.debug("######getCocdForChennel end########");
		return "1";
	}


	/**
	 * 채널 정보 조회
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getChennelInfo(String xml) throws Exception{

		logger.info("######getChennelInfo######## xml " + xml);

		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		CodeDOXML _doXML = new CodeDOXML();
		try {
			CodeDO _do = (CodeDO) _doXML.setDO(xml);


			List _infoList = _processor.getChennelInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getChennelInfo", e);
		}
		logger.debug("######getChennelInfo end ########");
		return " ";
	}



	/**
	 * 아카이브 경로조회
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getArchiveRoute(String xml) throws Exception{

		logger.info("######getArchiveRoute######## xml " + xml);
		CodeBusinessProcessor _processor = new CodeBusinessProcessor();

		try {

			List _infoList = _processor.getArchiveRoute(xml);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CodeDOXML _do2 = new CodeDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
			logger.debug("######getArchiveRoute end ########");
		}catch (Exception e) {
			logger.error("getArchiveRoute", e);
		}
		return "";
	}

	/**
	 * NLE  & DTL 등록한다. 2.0 확장용
	 * NLE DRAG&DROP에서 사용하는 함수. 
	 * 수동 아카이브 등록된 영상에 대해서 별다른 데이터 처리나 영상 편집없이
	 * 처음 생성된 그대로 DIVA에 등록할때 사용하는 함수
	 * @param metadataMstInfoDO
	 * @return
	 * @throws Exception 
	 */
	public int insertNLEDTL(String xml) throws Exception {
		logger.info("######insertNLEandDTL######## xml : "+xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ManualArchiveDOXML _doXML = new ManualArchiveDOXML();
		try {
			ManualArchiveDO _do = (ManualArchiveDO)_doXML.setDO(xml);
			long ct_id =1;

			return _processor.insertNLEandDTL(_do);
		} catch (Exception e) {
			logger.error("insertNLEDTL", e);
		}
		return 0;
	}


	/**
	 * DTL 경로 추가.
	 * 기능 사라짐
	 * @param programInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	@Deprecated
	public int insertDtlInfo(String xml) throws Exception{

		logger.info("######insertDtlInfo######## xml : "+xml);
		DtlInfoDOXML _doXML = new DtlInfoDOXML();
		DtlInfoDO _do = (DtlInfoDO) _doXML.setDO(xml);		
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			return _processor.insertDtlInfo(_do);
		} catch (Exception e) {
			logger.error("insertDtlInfo", e);  
		} 
		logger.debug("######insertDtlInfo########");
		return 0; 

	}

	/**
	 * 자동 아카이브관리를 조회한다.(다중조회)
	 * 영상구분별,채널별, 회사별,아카이브 경로별(ON-AIR, 매체변환, PDS&IF-CMS)로
	 * 자동아카이브 여부를 지정해주고, 해당 조건에 맞는 아카이브건이 넘어온다면
	 * 자동으로 아카이브하여 영상선정에서 보이도록 하거나,
	 * 수동으로 아카이브하도록 처리하여 NLE DRAG&DROP에서 보이도록한다.
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getAutoArchvieList(String autoDO) throws Exception{

		logger.info("######getAutoArchvieList######## autoDO " + autoDO);

		CodeBusinessProcessor _processor = new CodeBusinessProcessor();
		AutoArchiveDOXML _doXML = new AutoArchiveDOXML();

		AutoArchiveDO _do = (AutoArchiveDO) _doXML.setDO(autoDO);
		try {

			List _infoList = _processor.getAutoArchvieList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					AutoArchiveDOXML _do2 = new AutoArchiveDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getAutoArchvieList", e);
		}
		logger.debug("######getAutoArchvieList end########");
		return "";
	}
	/**
	 * 자동 아카이브관리를 수정한다
	 * 
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateAutoArchvie(String autoDO) throws Exception{

		logger.info("######updateAutoArchvie######## autoDO" +  autoDO);
		AutoArchiveDOXML _doXML = new AutoArchiveDOXML();
		try {
			AutoArchiveDO _do = (AutoArchiveDO) _doXML.setDO(autoDO);
			CodeBusinessProcessor _processor = new CodeBusinessProcessor();

			return	_processor.updateAutoArchvie(_do);
		} catch (Exception e) {
			logger.error("updateAutoArchvie", e);
		}


		logger.debug("######endupdateCopy########");
		return 0;

	}



	/**
	 * 수동 아카이브 요청(TC까지)
	 * 임시저장한 메타데이터를 근거로 검색영상, 스토리보드를 생성을 요청한다.
	 * 
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	public int insertManualArchive(String media_id) throws Exception{

		logger.info("######insertManualArchive######## media_id : " + media_id);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		try {

			return _processor.insertManualArchive(media_id);
		} catch (Exception e) {
			logger.error("insertManualArchive", e);
		}
		logger.debug("######insertManualArchive  end########");
		return 0;
	}


	/**
	 * 수동 아카이브 상세정보를 조회한다
	 * 임시저장한 영상의 정보를 조회한다.
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getManualInfo(String manualArchiveDO) throws Exception{

		logger.info("######getManualInfo######## manualArchiveDO : "+manualArchiveDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ManualArchiveDOXML _doXML = new ManualArchiveDOXML();
		try {
			ManualArchiveDO _do = (ManualArchiveDO)_doXML.setDO(manualArchiveDO);


			List _infoList = _processor.getManualInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ManualArchiveDOXML _do2 = new ManualArchiveDOXML();
					_do2.setDO(_iter.next());
					_xml.append( _do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getManualInfo", e);
		}
		logger.debug("######getManualInfo end########");
		return "";
	}


	/**
	 *  임시 저장
	 *  수동등록에서 사용되는 함수
	 *  DTL에 수동등록하기 전에 반드시 저장되어야하는 함수이며, 이곳에서 
	 *  입력된 메타를 기준으로 새로운 메타를 생성하여 DAS CLIENT에 제공한다.
	 *  새로 생성한 MEDIA_ID 파일의 원 파일 명 정보를 저장한다.
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	public int insertManual(String manualArchiveDO) throws Exception{

		logger.info("######insertManual######## manualArchiveDO : "+manualArchiveDO);

		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

		ManualArchiveDOXML _doXML = new ManualArchiveDOXML();
		try {
			ManualArchiveDO _do = (ManualArchiveDO)_doXML.setDO(manualArchiveDO);


			return _processor.insertManual(_do);
		} catch (Exception e) {
			logger.error("insertManual", e);
		}
		logger.debug("######insertManual end########");
		return 0;
	}
	/**
	 * 메타데이타 마스터 정보를 조회한다.
	 * 영산선정에서 사용하는 함수,
	 * MASTER_ID 단위로 영상을 조회하며, 
	 * 이곳에서 조회되는 조건은 ON-AIR, PDS&IF-CMS, 매체변환
	 * 에 따라 각기 달리 설정된다.
	 * @param case1 조회 조건
	 * @param case2 조회 조건
	 * @param start 시작일
	 * @param end   종료일
	 * @return	MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getMetadatInfoList(String conditionDO) throws Exception {
		logger.info("######getMetadatInfoList Start######## conditionDO : " +conditionDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ConditionDOXML _DOXML = new ConditionDOXML();
		try {
			WorkStatusConditionDO _DO = (WorkStatusConditionDO) _DOXML.setDO(conditionDO);

			List _infoList = _processor.getNewMetadatInfoList(_DO);

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer buf = new StringBuffer();
				buf.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					MetaInfoDOXML _do = new MetaInfoDOXML();
					_do.setDO(_iter.next());
					buf.append(_do.getSubXML());
				}

				buf.append("</das>");

				return buf.toString();
			}
		} catch (Exception e) {
			logger.error("getMetadatInfoList", e);
		}finally{
			logger.debug("######getMetadatInfoList End########" );
		}
		return "";
	}
	/**
	 * 화면정보를 조회한다.
	 * master_id에 소속되어져 있는 corner 정보, 코너에 소속되어져있는
	 * 주석(주제영상, 사용등급) 정보를 일괄적으로 조회하며,
	 * 사용등급이 전체 영상으로 지정되어 있을 경우 entire_yn의 값을 Y로 지정해준다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getSceanInfo(long master_id) throws RemoteException {
		logger.info("######getSceanInfo######## master_id " + master_id);
		long sTime1 = System.currentTimeMillis();
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String xml =_processor.getSceanInfo(master_id);

			return xml;
		} catch (Exception e) {
			logger.error("getSceanInfo", e);
		}
		return "";
	}

	/**
	 * 기초정보를 조회
	 * master_id에 소속되어있는 메타정보, 관련영상 존재유무, 
	 * 첨부파일 정보, 주석(사용등급, 주제영상)정보, 소속 영상 정보등을 
	 * 일괄적으로 조회한다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */
	public String getBaseInfo(long master_id) throws RemoteException {
		long sTime1 = System.currentTimeMillis();
		logger.info("######getBaseInfo######## master_id : " + master_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		String xml = "";
		try {
			xml = _processor.getBaseInfo(master_id);
		} catch (Exception e) {
			logger.error("getBaseInfo", e);
		}
		return xml;
	}
	/**
	 * 메타타이타 정보를 갱신한다.
	 * @param metadataMstInfoDO                                                                                                              
	 * @return                                                                                                           
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateMetadat(String metadataMstInfoDO) throws Exception {
		logger.info("######updateMetadat######## metadataMstInfoDO : " + metadataMstInfoDO  );

		MetadataMstInfoDOXML _doXML = new MetadataMstInfoDOXML();
		try {
			MetadataMstInfoDO _do = (MetadataMstInfoDO) _doXML
					.setDO(metadataMstInfoDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateMetadat(_do);
		} catch (Exception e) {
			logger.error("updateMetadat", e);
		}
		return 0;
	}


	/**
	 * 역활 권한 그룹신규로 신청.
	 * DAS, MORNITORING의 역활을 추가로 만들수 있다.
	 * 각 클라이언트별 역활을 생성하고 난뒤에는 그 역활이 가질수있는 권한을 자동으로 부여하며
	 * 최초의 값은 N이다. 
	 * 현재 모니터링 클라이언트에서 역할을 추가로 생성하는 화면은 사라졌다.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */

	public int insertRoleInfo(String roleInfoDO) throws RemoteException{

		logger.info("######insertRoleInfo######## roleInfoDO : " + roleInfoDO);
		RoleInfoDOXML _doXML = new RoleInfoDOXML();
		try {
			RoleInfoDO _do = (RoleInfoDO) _doXML.setDO(roleInfoDO);	
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.insertRoleInfo(_do);
		} catch (Exception e) {
			logger.error("insertRoleInfo", e);
		}
		logger.debug("######insertRoleInfo end########");
		return 0;

	}




	/**
	 * 외부 매체변환 건에대해서 tc에 등록하는 함수
	 * @param tcBeanDO
	 * @return
	 * @throws RemoteException
	 */
	public String insertComMedia(String tcBeanDO)throws RemoteException{
		logger.info("#####insertComMedia ##### tcBeanDO : "+tcBeanDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		TcBeanDOXML _doXML = new TcBeanDOXML();
		try {
			TcBeanDO _do = (TcBeanDO)_doXML.setDO(tcBeanDO);

			StringBuffer _xml = new StringBuffer();
			TcBeanDO do2 = _processor.insertComMedia(_do);//updateReqComTc(_do);

			//TcBeanDO do3 = new TcBeanDO();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n" );
			_xml.append("<das> \n" );
			TcBeanDOXML _doing = new TcBeanDOXML();
			if(do2==null){
				_doing.setDO(_do);
				_do.setResult("F");
			}else{
				_doing.setDO(do2);
				do2.setResult("S");
			}

			_xml.append( _doing.getSubXML2());
			_xml.append("</das> \n" );
			return _xml.toString();
		} catch (Exception e) {
			logger.error("insertComMedia", e);
			// TODO: handle exception
		}
		return "";
	}





	/**
	 * 채널 별 역활을 조회한다
	 * DAS 2.0 기준  채널별 역활정보를 제공
	 * 현재는 SBS의 역활만이 존재.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleInfoForChennel(String xml) throws RemoteException{

		logger.info("######getRoleInfoForChennel######## xml : " + xml);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		RoleForLoginDOXML _doXML = new RoleForLoginDOXML();	
		try {
			RoleInfoDO _do = (RoleInfoDO) _doXML.setDO(xml);


			List _infoList = _processor.getRoleInfoForChennel(_do);


			StringBuffer _xml= new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				RoleForLoginDOXML _do2 = new RoleForLoginDOXML();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");


				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML3());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleInfoForChennel", e);
		}
		logger.debug("######getRoleInfoForChennel end########");
		return "";
	}



	/**
	 * 로그인 기록
	 * 로그인, 아웃 정보를 기록하는 함수.
	 *  das 클라이언트가 시작시, 종료시 값을 넣어주고 그 기록을 제공
	 * @param xml                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	public String logHistory(String xml) throws RemoteException{

		logger.info("######LogHistory######## xml : " + xml);
		LogInOutDOXML _doXML = new LogInOutDOXML();
		try {
			LogInOutDO _do = (LogInOutDO) _doXML.setDO(xml);		
			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return _processor.LogHistory(_do);
		} catch (Exception e) {
			logger.error("logHistory", e);
		}
		logger.debug("######end LogHistory########");
		return "";

	}


	/**
	 * 사용자 로그인 현황을 조회한다
	 * 모니터링 클라이언트 함수
	 * 
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getLogInfo(String xml) throws RemoteException{

		logger.info("######getLogInfo######## xml : " + xml);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		LogInOutDOXML _doXML = new LogInOutDOXML();
		try {
			LogInOutDO _do = (LogInOutDO) _doXML.setDO(xml);


			List _infoList = _processor.getLogInfo(_do);


			StringBuffer _xml= new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<das>");

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				LogInOutDOXML _do2 = new LogInOutDOXML();

				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getLogInfo", e);
		}
		logger.debug("######getLogInfo end########");
		return "";
	}




	/**
	 * 채널별 승인자  조회
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getApproveInfoForChennel(String ApprveDO) throws Exception{

		logger.info("######getApproveInfoForChennel######## ApprveDO : "+ApprveDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);


			List _infoList = _processor.getApproveInfoForChennel(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ApproveInfoDOXML _do2 = new ApproveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getApproveInfoForChennel", e);
		}
		logger.debug("######getApproveInfoForChennel end########");
		return "";
	}



	/**
	 * 채널별 승인정보를 저장한다
	 * @param codeDO
	 * @return
	 * @throws Exception 
	 */
	public int insertApproveInfoForChennel(String ApprveDO)throws Exception{
		logger.info("#####insertApproveInfo##### ApprveDO : " + ApprveDO );
		ApproveInfoDOXML2 _doXML = new ApproveInfoDOXML2();
		try {
			//ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);
			List _result = (List)_doXML.setDO(ApprveDO);	
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.insertApproveInfoForChennel(_result);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertApproveInfoForChennel", e);
		}
		return 0;
	}



	/**
	 * 채널별  승인자 대상  조회한다.(등록시)
	 * 승인 완료된 유져 중에서 승인자로 등록할 사람의 정보를 조회한다.
	 * @param ApprveDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getApproveInfoListForChennel(String ApprveDO) throws Exception{

		logger.info("######getApproveInfoListForChennel######## ApprveDO : "+ApprveDO);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(ApprveDO);


			List _infoList = _processor.getApproveInfoListForChennel(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ApproveInfoDOXML _do2 = new ApproveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML3());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getApproveInfoListForChennel", e);
		}
		logger.debug("######getApproveInfoList end########");
		return "";
	}



	/**
	 * 채널별 승인정보를 삭제한다
	 * 실제로 삭제하는 것이 아니라 use_yn 정보만을 수정하여 사용않함으로 고친다.
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public void deleteApproveInfoForChennel(String xml) throws Exception {
		logger.info("######deleteApproveInfoForChennel######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ApproveInfoDOXML _doXML = new ApproveInfoDOXML();
		try {
			ApproveInfoDO _do = (ApproveInfoDO)_doXML.setDO(xml);
			//List _result = (List)_doXML.setDO(ApprveDO);	

			_processor.deleteApproveInfoForChennel(_do);
		} catch (Exception e) {
			logger.error("deleteApproveInfoForChennel", e);
		}


	}


	/**
	 * 아카이브상태 정보를 조회한다.
	 * @param useInfoDO 종료일
	 * @return MetaInfoDO XML string
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getArchiveStatusList(String archiveInfoDO) throws Exception {
		logger.info("######getArchiveStatusList Start######## archiveInfoDO : "+archiveInfoDO);
		SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();
		ArchiveInfoDOXML _DOXML = new ArchiveInfoDOXML();
		try {
			ArchiveInfoDO _DO = (ArchiveInfoDO)_DOXML.setDO(archiveInfoDO);

			List _infoList = _processor.getArchiveStatusList(_DO);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ArchiveInfoDOXML _do2 = new ArchiveInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());


				}

				_xml.append("</das>");

				return _xml.toString();

			}
		} catch (Exception e) {
			logger.error("getArchiveStatusList", e);
		}finally{
			logger.debug("######getArchiveStatusList End########");
		}
		return "";
	}



	/**
	 * 수동아카이브 정보조회(미디어id)
	 * das client 수동등록에서 사용하는 함수
	 * 사용자가 입력하고자하는 파일명을 기준으로 contents_tbl에 존재하는 media_id 컬럼을 조회해 
	 * 해당 값이 존재하는지 확인후 만약 존재한다면  필요 정보를 TEMP_MANUAL_TBL에 넣어주고,
	 * 새로운 media_id로 발급해 파일명을 치환시키고 만약 없다면
	 * 미디어id만 신규로 발급한다.
	 * @param media_id
	 * @return
	 * @throws Exception 
	 */
	public String getManualArchiveInfo(String manualArchiveDO) throws Exception {
		logger.info("######getManualArchiveInfo######## manualArchiveDO : " + manualArchiveDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ManualArchiveDOXML _doXML = new ManualArchiveDOXML();
		try {
			ManualArchiveDO _do = (ManualArchiveDO)_doXML.setDO(manualArchiveDO);


			List _infoList = _processor.getManualArchiveInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					ManualArchiveDOXML _do2 = new ManualArchiveDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getManualArchiveInfo", e);
		}

		return "";
	}




	/**
	 * dtl 목록을 조회한다
	 * @param  xml                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getDtlInfo(String xml) throws Exception{

		logger.info("######getDtlInfo######## xml : "  + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {

			List _infoList = _processor.getDtlInfo();
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					DtlInfoDOXML _do2 = new DtlInfoDOXML();

					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDtlInfo", e);
		}
		logger.debug("######getAutoArchvieList end########");
		return "";
	}

	/**
	 * CS  에서 받은 카트 정보를 저장한다.<p> 다운로드 카트 정보를  DB 스키마와 동일하게 받아 저장한다.
	 * @param downCartDO                                                                                                                                                                                             
	 * @param commonDO                                                                                                                                                                                            
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String insertDownCartInfo(String downCartDO) throws Exception {
		logger.info("######insertDownCartInfo######## downCartDO : "+downCartDO);
		DownCartDOXML _doXML = new DownCartDOXML();
		try {
			DownCartDO _do = (DownCartDO) _doXML.setDO(downCartDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			DownCartDO _returnDO = _processor.insertDownCartInfo(_do);
			if (_returnDO != null) {
				DownCartDOXML _returnDoXML = new DownCartDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("insertDownCartInfo", e);
		}
		return null;
	}


	/**
	 * 카트의 내용을 저장한다.
	 * 다운로드를 요청하기 전에 보관함에 담는 방식 
	 * 다운로드 요청전에 각 건에 대해서 사용등급이 걸려있는지, 요청자가 외주 직원인지에 대해서
	 * 정보를 넣어준다.
	 * @param cartContDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                             
	 * @throws Exception 
	 * @throws DASException
	 */
	public String insertCartContInfo(String cartContDO) throws Exception {
		logger.info("######insertCartContInfo######## cartContDO : "+cartContDO);
		CartContDOXML _doXML = new CartContDOXML();
		try {
			CartContDO _do = (CartContDO) _doXML.setDO(cartContDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			CartContDO _returnDO = _processor.insertCartContInfo(_do);
			if (_returnDO != null) {
				CartContDOXML _returnDoXML = new CartContDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("insertCartContInfo", e);
		}
		return null;
	}



	/**
	 * 스토리지에 있는 존재하는 클립에 대해서 리스토어 요청시 DAS-TM에 전달 요청하게 된다.
	 * 풀다운로드 한건에 대해서 요청하게 된다.
	 * @param downCartDO_cartContDO
	 * @return
	 * @throws Exception 
	 */
	public String insertStDownCartInfo(String downCartDO_cartContDO) throws Exception {
		logger.info("######insertStDownCartInfo######## downCartDO_cartContDO : " + downCartDO_cartContDO);
		DownCartDOXML _doXML_cart = new DownCartDOXML();
		try {
			DownCartDO _cart = (DownCartDO) _doXML_cart.setDO(downCartDO_cartContDO);

			CartContDOXML _doXML_cont = new CartContDOXML();
			CartContDO _cont = (CartContDO) _doXML_cont.setDO(downCartDO_cartContDO);

			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			_processor.insertStDownCartInfo(_cart);
			_cont.setCartNo(_cart.getCartNo());

			List _infoList = _processor.insertStCartContInfoForList(_cart,_cont);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					CartContDOXML _do2 = new CartContDOXML();

					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();

			} 
		}catch (Exception e) {
			logger.error("insertStDownCartInfo", e);
		}
		return null;
	}

	/**
	 * 스토리지에 있는 존재하는 클립에 대해서 리스토어 요청시 DAS-TM에 전달 요청하게 된다.
	 * 풀다운로드 한건에 대해서 요청하게 된다.
	 * 한건에 대한 카트의 내용을 저장한다.
	 * @param cartContDO
	 * @return
	 * @throws Exception 
	 */
	public String insertStCartContInfo(String cartContDO) throws Exception {
		logger.info("######insertStCartContInfo########"+cartContDO);
		CartContDOXML _doXML = new CartContDOXML();
		try {
			CartContDO _do = (CartContDO) _doXML.setDO(cartContDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			CartContDO _returnDO = _processor.insertStCartContInfo(_do);
			if (_returnDO != null) {
				CartContDOXML _returnDoXML = new CartContDOXML();
				_returnDoXML.setDO(_returnDO);
				return _returnDoXML.toXML();
			}
		} catch (Exception e) {
			logger.error("insertStCartContInfo", e);
		}
		return null;
	}






	/**
	 * 사용자 로그인 현황을 조회한다(모니터링)
	 * @param  xml                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getLogInOutInfo(String xml) throws Exception{

		logger.info("######getLogInOutInfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		LogInOutDOXML _doXML = new LogInOutDOXML();
		try {
			LogInOutDO _do = (LogInOutDO) _doXML.setDO(xml);		


			List _infoList = _processor.getLogInOutInfo(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					LogInOutDOXML _do2 = new LogInOutDOXML();

					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getLogInOutInfo", e);
		}
		logger.debug("######getLogInOutInfo end########");
		return "";
	}



	/**
	 * 스토리지에 있는 클립에 대한 전송 요청을 받을 수 있는 풀다운로드 요청 다운카트의 상태를 갱신한다. DAS2.0
	 * 다운로드 요청건이 무제한인 경우에는 바로 스토리지 혹은, DIVA에 다운로드 요청을 진행 하며, 
	 * 다운로드 요청자가 외부부서직원이거나, 사용제한 건이라면 승인 대기 상태에서 대기한다.
	 * @param downCartDO
	 * @return
	 * @throws Exception 
	 */
	public int updateStDownCart(String downCartDO)throws Exception{
		logger.info("#####updateStDownCart start##### downCartDO : " + downCartDO);
		DownCartDOXML _doXML_cart = new DownCartDOXML();
		try {
			DownCartDO _cart = (DownCartDO) _doXML_cart.setDO(downCartDO);

			CartContDOXML _doXML_cont = new CartContDOXML();
			CartContDO _cont = (CartContDO) _doXML_cont.setDO(downCartDO);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();


			return _processor.updateStDownCart(_cart,_cont);


		} catch (Exception e) {
			logger.error("updateStDownCart", e);
		}
		return 0;

	}







	//모니터링 함수

	/**
	 * 아카이브 진행 상태를 조회한다(모니터링)
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getArchiveInfo(String xml) throws Exception{

		logger.info("######getArchiveInfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);

			List _infoList = _processor.getArchiveInfo(_do);


			StringBuffer _xml= new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();

				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getArchiveXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getArchiveInfo", e);
		}
		logger.debug("######getArchiveInfo end########");
		return "";
	}

	/**
	 * tc 현황을 조회한다(모니터링)
	 * TC 작업 현황에 대해서 조회한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getTCinfo(String xml) throws Exception{

		logger.info("######getTCinfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getTCinfo(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getTCXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getTCinfo", e);
		}
		logger.debug("######getTCinfo end########");
		return "";
	}


	/**
	 * 다운로드 현황을 조회한다(모니터링)
	 * TM 전송 현황에 대해서 조회한다. 
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getTminfo(String xml) throws Exception{

		logger.info("######getTminfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getTminfo(_do);


			StringBuffer _xml= new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				MonitoringDOXML _do2 = new MonitoringDOXML();

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getTminfo", e);
		}
		logger.debug("######getTminfo end########");
		return "";
	}

	/**
	 * 우선순위를 변경한다
	 * 우선 대기 순위를 변경하는 함수
	 * 하지만 우선순위 변경에는  조건이 있으며, 작업중인 파일에 대해서 우선 순위 변경이 불가능하며
	 * 작업 진행 전의 건에 대해서만 변경이 가능하다.
	 * 각 변경작업 구분은 GUBUN 컬럼으로 구분한다.
	 * 001 : 아카이브, 002 : 재생성, 003 : TM(미구현) 
	 * 004 : 수동복본, 005 : 수동복원, 006 : 수동소산
	 * 007 : 다운로드
	 * 모니터링 클라이언트 함수
	 * @param  autoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int changePriority(String xml) throws Exception{

		logger.info("######changePriority######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);

			return	_processor.changePriority(_do);
		} catch (Exception e) {
			logger.error("changePriority", e);
		}


		logger.debug("######end changePriority########");
		return 0;

	}



	/**
	 * 진행중인 작업을 취소한다.
	 * 진행중인 작업에 대해서 취소 명령어를 내리는 함수.
	 * 하지만 취소 명령에는 조건이 있으며, 작업중인 파일에 대해서 취소는 불가능하며
	 * 작업 진행 전의 건에 대해서만 취소가 가능하다.
	 * 각 취소작업 구분은 GUBUN 컬럼으로 구분한다.
	 * 001 : 아카이브, 002 : 재생성, 003 : TM(미구현) 
	 * 004 : 수동복본, 005 : 수동복원, 006 : 수동소산
	 * 007 : 다운로드
	 * 모니터링 클라이언트 함수
	 * @param master_id
	 * @return
	 * @throws Exception 
	 */
	public int cancelJob(String xml) throws Exception{

		logger.info("######cancelJob######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);

			return	_processor.cancelJob(_do);
		} catch (Exception e) {
			logger.error("cancelJob", e);
		}
		logger.debug("######end cancelJob########");
		return 0; 

	}


	/**
	 * 해당 건의 상세 정보를 조회한다
	 * 아카이브, 검색영상,스토리보드 생성, 전송건에 대한 상세 정보를 제공하는 함수
	 * 각각의 건은 GUBUN 컬럼(001 : 아카이브, 002 : TC, 003 : TM) 로 구분지어 조회된다.
	 * 모니터링용 함수이다
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getDetailInfo(String xml) throws Exception{

		logger.info("######getDetailInfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getDetailInfo(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

				MonitoringDOXML _do2 = new MonitoringDOXML();

				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML2());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDetailInfo", e);
		}
		logger.debug("######getDetailInfo end########");
		return "";
	}



	/**
	 * 사용자 수동삭제목록을 조회한다.
	 * 아카이브와 다운로드 건 중 작업이 완료되었으나 아직 삭제 되지 않은 건들에 대해서 조회하는
	 * 함수. 
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getManualDeleteList(String xml) throws Exception{

		logger.info("######getManualDeleteList######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ManualDeleteDOXML _doXML = new ManualDeleteDOXML();
		try {
			ManualDeleteDO _do = (ManualDeleteDO) _doXML.setDO(xml);


			List _infoList = _processor.getManualDeleteList(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				ManualDeleteDOXML _do2 = new ManualDeleteDOXML();

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getManualDeleteList", e);
		}
		logger.debug("######getManualDeleteList end########");
		return "";
	}




	/**
	 * 사용자 수동삭제를 요청한다.
	 * 아카이브 완료건과, 다운로드 전송 완료 건에 대해서 수동으로 삭제할수있는 함수.
	 * GUBUN 컬럼으로 작업을 구분한다. 현재는 아카이브 완료건만 삭제하도록 되어있으나
	 * 서버는 다운로드건도 삭제 요청할수있도록 구현된 상태
	 * (001 : 아카이브, 002 : 다운로드 )
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public int manualDelete(String xml) throws Exception{

		logger.info("######manualDelete######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ManualDeleteDOXML _doXML = new ManualDeleteDOXML();
		try {
			ManualDeleteDO _do = (ManualDeleteDO) _doXML.setDO(xml);

			return	_processor.manualDelete(_do);
		} catch (Exception e) {
			logger.error("manualDelete", e);
		}
		logger.debug("######end manualDelete########");
		return 0; 

	}




	/**
	 * 에러 목록을 조회한다.
	 * ERROR_LOG_TBL에 등록되어있는 에러로그에 대해서 정보를 제공
	 * 모니터링 클라이언트에서 사용하는 함수
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getErroeList(String xml) throws Exception{

		logger.info("######getErroeList######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ErrorLogDOXML _doXML = new ErrorLogDOXML();
		try {
			ErrorLogDO _do = (ErrorLogDO) _doXML.setDO(xml);


			List _infoList = _processor.getErroeList(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				ErrorLogDOXML _do2 = new ErrorLogDOXML();

				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getErroeList", e);
		}
		logger.debug("######getErroeList end########");
		return "";
	}





	/**
	 * 서버 목록을 조회한다.
	 * 모니터링 클라이언트에서 사용하는 서버리스트 조회함수
	 * DAS_EQUIPMENT_TBL에 등록되어 있는 함수중 USE_YN이 Y인 건에 대해서 조회를 한다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */

	public String getServerList(String xml) throws Exception{

		logger.info("######getServerList######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		ServersDO _do = new ServersDO();
		_do.setIp("11");
		try {

			List _infoList = _processor.getServerList(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				ServersDOXML _do2 = new ServersDOXML();


				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getSubXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getServerList", e);
		}
		logger.debug("######getServerList end########");
		return "";
	}




	/**
	 * 복본/소산 생성요청을 한다( 기존 아카이브 건에 대해서 복본생성만 요청한다.)
	 * DAS 2.0 초기 버전 이후 TRYAGIN으로 함수 변경
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * @throws DASException
	 */
	@Deprecated
	public String updateCopyRequest(String useInfoDO) throws NumberFormatException, Exception{

		logger.info("######updateCopyRequest######## useInfoDO : " + useInfoDO);
		UseInfoDOXML _doXML = new UseInfoDOXML();
		try {
			UseInfoDO _do = (UseInfoDO) _doXML.setDO(useInfoDO);		
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();

			return	_processor.updateCopyRequest(_do);
		} catch (Exception e) {
			logger.error("updateCopyRequest", e);
		}
		logger.debug("######end updateCopyRequest########");
		return "";

	} 



	/**
	 * 미디어넷 자료 상태를 업데이트한다
	 * 미디어넷자료를 벌크로 넣기위해서사용된 함수
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public int updateMediaCilpStatus(String xml) throws Exception{

		logger.info("######updateMediaCilpStatus######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MediaArchiveDOXML _doXML = new MediaArchiveDOXML();
		try {
			MediaArchiveDO _do = (MediaArchiveDO) _doXML.setDO(xml);

			return	_processor.updateMediaCilpStatus(_do);
		} catch (Exception e) {
			logger.error("updateMediaCilpStatus", e);
		}
		logger.debug("######end updateMediaCilpStatus########");
		return 0;

	} 




	/**
	 * wmvlist조회 (H264 전황용)
	 * H264 전환을 위하여 사용한 함수
	 * @param XML                                                                                                                                                                                              카트내용정보
	 * @return                                                                                                                                                                                              CartContDO
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String getJobList(String xml) throws Exception{

		logger.info("######getWmvList######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		WmvH264DOXML _doXML = new WmvH264DOXML();
		try {
			WmvH264DO _do = (WmvH264DO) _doXML.setDO(xml);


			List _infoList = _processor.getWmvList(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					WmvH264DOXML _do2 = new WmvH264DOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getJobList", e);
		}
		//logger.debug("######getWmvList########");
		return " ";
	}






	/**
	 * WMV- H264 완료 상태를 업데이트 한다.
	 * WMV-H264 변환 작업시 사용하였던 함수 더이상 사용하지 않는다.
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	@Deprecated
	public String updateWmvStatus(String xml) throws Exception{

		logger.info("######updateWmvStatus######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		WmvH264DOXML2 _doXML = new WmvH264DOXML2();
		try {
			WmvH264DO _do = (WmvH264DO) _doXML.setDO(xml);


			List _infoList = _processor.updateWmvStatus(_do);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					WmvH264DOXML _do2 = new WmvH264DOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getResultXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("updateWmvStatus", e);
		}

		logger.debug("######end updateWmvStatus########");
		return "";

	} 



	/**
	 * ifcms 아카이브 요청(PART 1.메타)
	 * PDS 아카이브모듈과 공통 모듈로 사용함.
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	@Deprecated
	public String insertIfCmsArchive(String pdasArchiveDO) throws RemoteException{

		logger.info("######insertIfCmsArchive######## pdasArchiveDO : "+pdasArchiveDO);
		try {
			PdsArchiveDOXML _doXML = new PdsArchiveDOXML();

			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(pdasArchiveDO.replaceAll("&quot;", "\""));	



			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();


			return _processor.insertPdasArchiveIFcms(_do);

		} catch (Exception e) {
			logger.error("insertIfCmsArchive", e);

		}
		logger.debug("######insertIfCmsArchive end ########");
		return "";
	}


	/**
	 * PDAS 아카이브  상태변환(PART 2. 콘텐츠)
	 * PDS 아카이브모듈과 공통 모듈로 사용함.
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws DASException
	 *  */
	@Deprecated
	public String updateIfCmsArchiveStatus(String pdasArchiveDO) throws RemoteException{

		logger.info("######updateIfCmsArchiveStatus######## pdasArchiveDO : "+pdasArchiveDO);
		try {
			PdsArchiveDOXML2 _doXML = new PdsArchiveDOXML2();
			PdsArchiveDO _do = (PdsArchiveDO) _doXML.setDO(pdasArchiveDO.replaceAll("&quot;", "\""));		
			SystemManageBusinessProcessor _processor = new SystemManageBusinessProcessor();


			int resutl =  _processor.updatePDSArchiveStatus(_do);

			return "";

		} catch (Exception e) {
			logger.error("updateIfCmsArchiveStatus", e);
		}
		logger.debug("######updateIfCmsArchiveStatus end########");
		return "";

	}


	/**
	 * 스토리지에 있는 존재하는 클립에 대해서 리스토어 요청시 DAS-TM에 전달 요청하게 된다.
	 * 풀다운로드 한건에 대해서 요청하게 된다.
	 * if-cms에서 사용하는 함수.
	 * @param downCartDO_cartContDO
	 * @return
	 * @throws Exception 
	 */
	public String insertIfCmsDownCartInfo(String downCartDO_cartContDO) throws Exception {
		logger.info("######insertIfCmsDownCartInfo########  downCartDO_cartContDO :  " + downCartDO_cartContDO);
		DownCartDOXML _doXML_cart = new DownCartDOXML();
		try {
			DownCartDO _cart = (DownCartDO) _doXML_cart.setDO(downCartDO_cartContDO);

			CartContDOXML _doXML_cont = new CartContDOXML();
			CartContDO _cont = (CartContDO) _doXML_cont.setDO(downCartDO_cartContDO);

			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			_processor.insertStDownCartInfo(_cart);
			_cont.setCartNo(_cart.getCartNo());
			CartContDO _returnDO = _processor.insertStCartContInfo(_cart,_cont);
			if (_returnDO != null) {
				CartContDOXML _returnDoXML = new CartContDOXML();
				_returnDoXML.setDO(_returnDO);

				return _returnDoXML.toXML();
			}

		} catch (Exception e) {
			logger.error("insertIfCmsDownCartInfo", e);
		}
		return null;
	}


	/**
	 * 다운로드 요청건에 대해서 승인 내용을 수정한다.
	 * @param cartContDO
	 * @return
	 * @throws RemoteException
	 */
	public String updateIfCmsDownloadApprove(String cartContDO ) throws RemoteException{
		logger.info("#####updateIfCmsDownloadApprove ##### cartContDO : " + cartContDO);
		WorkBusinessProcessor _processor = new WorkBusinessProcessor();
		CartContDOXML _doXML = new CartContDOXML();
		try {
			CartContDO _do = (CartContDO) _doXML.setDO(cartContDO);


			_processor.updateDownloadRequestDetail(_do);
			return "";

		} catch (Exception e) {
			logger.error("", e);
			// TODO: handle exception
		}
		return "updateIfCmsDownloadApprove";
	}


	/**
	 * 모니터링 로그인시 역활정보를 조회한다.
	 * 로그인id에 부여된 역활 정보를 기준으로 사용에게 보여줄 client 정보를 넘겨준다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleForLoginInMonitoring(String user_id) throws RemoteException{

		logger.info("######getRoleForLoginInMonitoring######## user_id : "  + user_id);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		RoleForLoginDOXML _doXML = new RoleForLoginDOXML();

		try {


			String _xml= _processor.getRoleForLoginInMonitoring(user_id);


			return _xml;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleForLoginInMonitoring", e);
		}
		logger.debug("######end  getRoleForLogin########");
		return "";
	}







	/**
	 * 마스터id별 ctiid의 묶음을 조회한다
	 * if-cms에서 master_id에 소속된 영상id 정보를 얻어오기위해서 사용하는 함수이며,
	 * 리턴값은 ct_id, ct_nm값이다.
	 * @param cartItemDO                                                                                                                                                                                            
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                            
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getGroupForMaster( long master_id )
			throws Exception {
		logger.info("######getGroupForMaster######## master_id : "+master_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {

			List _infoList = _processor.getGroupForMaster(master_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					WmvH264DOXML _do2 = new WmvH264DOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getGroupXML());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getGroupForMaster", e);
		}
		logger.debug("######getGroupForMaster end########");
		return "";
	}




	/**
	 * 화면정보를 조회한다(미디어넷 스토리보드).
	 * ct_id 별로 소속된 코너의 정보를 전달한다
	 * @param ct_id
	 * @return
	 * @throws RemoteException
	 */
	public String getSceanInfoForIfCms(long ct_id) throws RemoteException {
		logger.info("######getSceanInfoForIfCms######## ct_id : " + ct_id);
		long sTime1 = System.currentTimeMillis();
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String xml =_processor.getSceanInfoForIfCms(ct_id);

			return xml;
		} catch (Exception e) {
			logger.error("getSceanInfoForIfCms", e);
		}
		return "";
	}




	/**
	 * 실패난 작업을 재요청한다.
	 *  gubun 컬럼으로 재작업 요청에 대한 업무를 분할한다.
	 *  001 : 아카이브, 002 : 검색영상 재생성 , 003 : tm 재전송
	 *  004 : 수동복본, 005 : 수동소산 , 006 : 수동 복원, 007 : 다운로드 재요청
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	public String tryAgain(String xml)throws Exception{
		logger.info("#####tryAgain start##### xml : "+xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);

			return _processor.tryAgain(_do);
		} catch (Exception e) {
			logger.error("tryAgain", e);
		}
		return "fail";

	}




	/**
	 * 다운로드 현황을 조회한다(모니터링)
	 * 다운로드 요청건에 대한 전체 진행 상황을 한눈에 파악할수있도록
	 * 정보를 제공
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getDowninfo(String xml) throws Exception{

		logger.info("######getDowninfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getDowninfo(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getDownXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDowninfo", e);
		}
		logger.debug("######getDowninfo end########");
		return "";
	}



	/**
	 * 수동 작업 현황을 조회한다(모니터링)
	 * 기존 아카이브 history 테이블인 contents_loc_Tbl은 현황기록 테이블로 두고
	 * 별로로 수동작업에대한 history  테이블인 archive_hist_tbl을 만들어서
	 * 수동 작업에 대한 이력까지 확인 할수있도록 정보를 제공
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getManualJobinfo(String xml) throws Exception{

		logger.info("######getManualJobinfo######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getManualJobinfo(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");

				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getManualXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getManualJobinfo", e);
		}
		logger.debug("######getManualJobinfo end########");
		return "";
	}








	/**
	 * 역활 정보를 수정한다(모니터링)
	 * 모니터링에서 사용되어지는 역할에 대해서 부여된 권한 정보를 수정한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public int updateRoleInfoForMonitoring(String roleInfoDO) throws RemoteException{

		logger.info("######updateRoleInfoForMonitoring######## roleInfoDO : " + roleInfoDO);
		RoleInfoDOXML _doXML = new RoleInfoDOXML();
		try {
			RoleInfoDO _do = (RoleInfoDO) _doXML.setDO(roleInfoDO);		

			EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

			return	_processor.updateRoleInfoForMonitoring(_do);
		} catch (Exception e) {
			logger.error("updateRoleInfoForMonitoring", e);
		}
		logger.debug("######updateRoleInfoForMonitoring########");
		return 0;

	} 



	/**
	 * 부서정보기준 사용자 정보를 가져온다(모니터링용)
	 * user_info_Tbl 기준으로  monitor_role이 0이 아닌 유저에 대해서 정보를 조회.
	 * 
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getDepinfoForuserListFormonitoring(String deptcd) throws RemoteException{

		logger.info("######getDepinfoForuserList######## deptcd : "+deptcd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

		try {

			List _infoList = _processor.getDepinfoForuserListFormonitoring(deptcd);

			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			Iterator _iter = _infoList.iterator();
			while (_iter.hasNext()) {
				EmployeeInfoDOXML _do2 = new EmployeeInfoDOXML();
				_do2.setDO(_iter.next());
				_xml.append(_do2.getSubXML3());
			}

			_xml.append("</das>");

			return _xml.toString();

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDepinfoForuserListFormonitoring", e);
		}
		logger.debug("######getDepinfoForuserList end########");
		return "";
	}





	/**
	 * 내 아카이브 요청 목록조회
	 * DAS에 아카이브를 요청한 ID를 기준으로 조회를 할수있게하는 함수.
	 * @param commonDO
	 * @return
	 * @throws RemoteException
	 */
	public String getMyArchiveRequestList(String commonDO) throws RemoteException{
		logger.info("#####getMyArchiveRequestList#####  commonDO :  "  + commonDO);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		ConditionDOXML _DOXML = new ConditionDOXML();
		try {
			WorkStatusConditionDO _DO = (WorkStatusConditionDO) _DOXML
					.setDO(commonDO);

			List _infolist = _processor.getMyArchiveRequestList(_DO);
			StringBuffer _xml = new StringBuffer();
			_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
			if(_infolist != null&&_infolist.size()!=0){
				Iterator _iter = _infolist.iterator();
				Iterator _iter2 = _infolist.iterator();

				ConditionDOXML _do2 = new ConditionDOXML();
				_do2.setDO(_iter2.next());
				_xml.append(_do2.getSubXML3());

				while(_iter.hasNext()){
					ConditionDOXML _doing = new ConditionDOXML();
					_doing.setDO(_iter.next());
					_xml.append( _doing.getReqListXML());
				}
				_xml.append("</das>");
				//		logger.debug("[getMyDownloadRequestList][ouput]"+_xml);
				return _xml.toString();
			}

		} catch (Exception e) {
			logger.error("getMyArchiveRequestList", e);
			// TODO: handle exception
		}
		return "";
	}






	/**
	 * 다운로드 현황을 조회한다(ifcms)
	 * 다운로드 명, DIVA 다운로드 현황, TM 전송 현황을 확인할수있는 함수
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getDowninfoForIfCms(String xml) throws Exception{

		logger.info("######getDowninfoForIfCms######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getDowninfoForIfCms(_do);


			StringBuffer _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();

				int i = 1;
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getIfCmsXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getDowninfoForIfCms", e);
		}
		logger.debug("######getDowninfoForIfCms end########");
		return "";
	}





	/**
	 *  진행 상태를 조회한다(if cms)
	 *  아카이브, 다운로드 진행상황을 확인 할수있는 함수
	 *  GUBUN(001 : 아카이브, 002 : 다운로드)컬럼으로 구분지어서 조회하며
	 *  화면에서는 각 단계가 한번에 보이지만 실제 내부로직은 진행 상황에 따라서 쿼리를 변경
	 *  하여 보여주는 방식으로 되어있다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getJobStatus(String xml) throws Exception{

		//logger.debug("######getJobStatus######## xml : " + xml);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		MonitoringDOXML _doXML = new MonitoringDOXML();
		try {
			MonitoringDO _do = (MonitoringDO) _doXML.setDO(xml);


			List _infoList = _processor.getJobStatus(_do);


			StringBuffer  _xml = new StringBuffer();

			if (_infoList != null && _infoList.size() > 0) {

				Iterator _iter = _infoList.iterator();

				MonitoringDOXML _do2 = new MonitoringDOXML();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				while (_iter.hasNext()) {
					_do2.setDO(_iter.next());

					_xml.append(_do2.getJobInfoXML());

				}

				_xml.append("</das>"); 

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getJobStatus", e);
		}

		return "";
	}


	/**
	 * 장비의 상태 업데이트를 한다
	 * 주기적으로 장비의 상태를 업데이트한다.
	 * @param DepInfoDO                                                                                                                                                                                             
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public int updateEquipMentStatus(String xml) throws Exception{

		//logger.info("######updateEquipMentStatus######## xml : " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		EquipMentDOXML _doXML = new EquipMentDOXML();
		try {
			EquipMentInfoDO _do = (EquipMentInfoDO) _doXML.setDO(xml);

			return	_processor.updateEquipMentStatus(_do);
		} catch (Exception e) {
			logger.error("updateEquipMentStatus", e);
		}
		//logger.debug("######end updateEquipMentStatus########");
		return 0;

	} 





	/**
	 * 마스터id별 ctiid의 묶음을 조회한다(das client)
	 * 프로그램별 복본, 소산 요청을 하기위해하여 프로그램에 소속되어있는 모든 영상의 
	 * 복본, 소산에 필요한 정보를 조회하는 함수.
	 * @param cartItemDO                                                                                                                                                                                            
	 * @param                                                                                                                                                                                               
	 * @return                                                                                                            
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getGroupForMasterForClient( long master_id )
			throws Exception {
		logger.info("######getGroupForMasterForClient######## master_id : "+master_id);

		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		try {

			List _infoList = _processor.getGroupForMasterForClient(master_id);
			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					WmvH264DOXML _do2 = new WmvH264DOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getGroupXML2());
				}

				_xml.append("</das>");

				return _xml.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getGroupForMasterForClient", e);
		}
		logger.debug("######getGroupForMasterForClient end########");
		return "";
	}





	/**
	 * 역할 코드 조회
	 * PERM_TBL 기록되어있는 역활정보 중에
	 * USE_YN이 Y인 건을 조회한다.
	 * @param roleInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getRoleCodeForMonitoring() throws RemoteException{



		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();

		try {

			List _infoList = _processor.getRoleCodeForMonitoring();

			if (_infoList != null && _infoList.size() > 0) {
				StringBuffer _xml = new StringBuffer();
				_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
				Iterator _iter = _infoList.iterator();
				while (_iter.hasNext()) {
					RoleInfoDOXML _do2 = new RoleInfoDOXML();
					_do2.setDO(_iter.next());
					_xml.append(_do2.getSubXML2());
				}

				_xml.append("</das>");

				return _xml.toString();


			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getRoleCodeForMonitoring", e);
		}

		return "";
	}






	/**
	 * 모니터링  역활정보를 조회한다.
	 * MENU_TBL,MENU_PERM_TBL의 정보를 조합하여 입력
	 * 역활 코드에 부여된 화면정보와, 읽기, 쓰기 권한을 모니터링 클라이언트에 제공한다.
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String getAuthorForMonitoring(String role_cd) throws RemoteException{

		logger.info("######getAuthorForMonitoring######## role_cd : "  + role_cd);

		EmployeeRoleBusinessProcessor _processor = new EmployeeRoleBusinessProcessor();
		RoleForLoginDOXML _doXML = new RoleForLoginDOXML();

		try {


			String _xml= _processor.getAuthorForMonitoring(role_cd);


			return _xml;

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getAuthorForMonitoring", e);
		}

		return "";
	}



	/*


	 *//**
	 * 기초정보를 조회(if cms)
	 * IF-CMS용으로 관련영상 정보를 조회하는 함수이다.
	 * @param master_id
	 * @return
	 * @throws RemoteException
	 */

	public String isVideoReleateYN(long master_id,long ct_id) throws RemoteException {

		logger.info("######isVideoReleateYN######## master_id : " + master_id + " ct_id : " + ct_id);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
		try {
			String xml = _processor.isVideoReleateYN(master_id,ct_id);

			return xml;
		} catch (Exception e) {
			logger.error("isVideoReleateYN", e);
		}
		return "";
	}


	/**
	 * 컨텐츠 소유권자별 다운로드 이용통계
	 * 기간별, 월별, 연별 통계 데이터를 조회할수 있으며, 
	 * 컨텐츠의 소속기준 다운로드 통계정보를 제공한다.
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DOWN_COCD_USE_TBL_List(String xml) throws Exception{
		logger.info("#####getSTAT_DOWN_COCD_USE_TBL_List##### statisticsConditionDO : " + xml);
		Das das = new Das();
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			StatisticsBusinessProcessor _processor = new StatisticsBusinessProcessor();

			String result =_processor.getSTAT_DOWN_COCD_USE_TBL_List(das);

			return result;

		} catch (Exception e) {
			logger.error("getSTAT_DOWN_COCD_USE_TBL_List", e);
			// TODO: handle exception
		}
		return "";
	}


	/**
	 * 시간대별 사용등급 MAPPGING 조회
	 * 요일, 프로그램ID, 시간대를 조회하는 하는 함수.
	 * 일주일 단위로 설정할수 있으며, 설정 시간대별로 프로그램ID를 정의해
	 * 설정시간대에 생성된 주조영상에 해당 프로그램ID와 사용등급을 자동 지정해주기위한 화면이다.
	 * 
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getRistClfInfoListForTime(String xml) throws Exception{
		logger.info("######getRistClfInfoListForTime########   :  " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		Das das = new Das();
		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);

			String resultXml = _processor.getRistClfInfoListForTime(das);


			return resultXml;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getJobStatus", e);
		}
		//logger.debug("######getJobStatus end########");
		return "";
	}

	/**
	 * 시간대별 사용등급정보를 수정한다.
	 * 수정하고자하는 시간의 시작점과 끝점이 등록되어있는 다른 시간대별 등급정보와 겹친다면 
	 * 저장이 되지 않는다. 단. 수정하고자하는 값이 시작점과 끝점은 동일하고 프로그램ID만 변경하는 것은 가능하다.
	 * 
	 * @param DepInfoDO                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws DASException
	 */
	public String updateRistClfInfoListForTime(String xml) throws RemoteException{

		logger.info("######updateRistClfInfoListForTime######## xml : " + xml);
		Das das = new Das();

		try {

			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			String result = _processor.updateRistClfInfoListForTime(das);
			return	result;
		} catch (Exception e) {
			logger.error("updateRistClfInfoListForTime", e);
		}
		logger.debug("######updateRistClfInfoListForTime########");
		return "";

	} 
	/**
	 * 시간대별 사용등급정보를 등록한다.
	 * 시간대 별 사용 등급은 중복될수 없으며,
	 * 입력하고자하는 시작점과 종료점이 이미 등록된 시간대와 겹친다면 
	 * 등록되지 않는다.
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public String insertRistClfInfoListForTime(String xml) throws Exception {
		logger.info("######insertRistClfInfoListForTime########   :  " + xml);
		Das das = new Das();

		try {

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			String result = _processor.insertRistClfInfoListForTime(das);

			return result;

		} catch (Exception e) {
			logger.error("insertRistClfInfoListForTime", e);
		}
		return null;
	}


	/**
	 *  시간대별 사용등급정보를 삭제한다.
	 *  key 값인 seq값을 기준으로 데이터를 삭제처리한다.
	 *
	 * @param pdasArchiveDO                                                                                                                                                                                              
	 * @return                   
	 * @throws Exception 
	 * @throws DASException
	 *  */
	public String deleteRistClfInfoListForTime(String xml) throws Exception{

		logger.info("######deleteRistClfInfoListForTime########   xml:  "+xml);
		Das das = new Das();

		try {

			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			String result = _processor.deleteRistClfInfoListForTime(das);

			return result;


		} catch (Exception e) {
			logger.error("deleteRistClfInfoListForTime", e);
		}finally{
			logger.debug("######deleteRistClfInfoListForTime End########");
		}
		return "";
	}


	/**
	 * 청구번호를 수기로 입력, 수정한다..
	 * 사용자로 부터 ERP DB의 REQ_NO의 값을 입력받고 그것을 DAS DB의  REQ_CD에 입력해주는 로직이다
	 * 입력된 값이 DAS DB에 저장되는 조건은. ERP_DB에는 그 값이 존재하나, DAS_DB에는 해당값을 가진 데이터가
	 * 한건도 없을시 가능하다.
	 * 
	 * @param xml                                                                                                              
	 * @return                                                                                                           
	 * @throws Exception 
	 * @throws DASException
	 */
	public String updateReqCd(String xml) throws Exception {
		logger.info("######updateReqCd######## updateReqCd : " + xml  );
		Das das = new Das();

		try {
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			das = convertorService.unMarshaller(xml);
			ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

			return _processor.updateReqCd(das);
		} catch (Exception e) {
			logger.error("updateReqCd", e);
		}
		return "";
	}


	/**
	 * 스케쥴러의 작동시간 리스트를 본다.
	 * scheduler_info_tbl과 QRTZ에서 사용하는 스케쥴러 테이블의 정보를
	 * UNION ALL로 취합하여 정보를 보여준다. 
	 * QRTZ에서 사용하는 UNIX_TIME을 YYYY-MM-DD 형태로 치환하기 위해서 
	 * timestampdiff function을 생성하여 사용하였다.
	 * @param  XML                                                                                                                                                                                              
	 * @return                                                                                                                                                                                              
	 * @throws Exception 
	 * @throws DASException
	 */
	public String getSchedulerList(String xml) throws Exception{
		//logger.info("######getSchedulerList########   :  " + xml);
		ExternalBusinessProcessor _processor = new ExternalBusinessProcessor();

		Das das = new Das();
		try {

			String resultXml =_processor.getSchedulerList();


			return resultXml;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("getJobStatus", e);
		}
		//logger.debug("######getJobStatus end########");
		return "";
	}




}




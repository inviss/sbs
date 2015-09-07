package kr.co.d2net.commons.system;

import java.rmi.RemoteException;

public interface DasCmsConnector {

	/**
	 * <pre>
	 * 아카이브 요청
	 * </pre>
	 * @param xml
	 * @return String
	 * @throws RemoteException
	 */
	public Integer archiveReq(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 카트요청
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String insertDownCart(String xml) throws RemoteException;
	
	/**
	 * 카트에 영상 담기
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String insertCartCont(String xml) throws RemoteException;
	
	/**
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public Integer updateDownCart(String xml) throws RemoteException;
	
	
	/**
	 * <pre>
	 * 전송요청
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public void transferReq(String xml) throws RemoteException;
	
	/**
	 * 
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findContents(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 사용자 로그인
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String login(String tokenDO) throws RemoteException;
	
	/**
	 * <pre>
	 * 세션ID에 대한 유효 체크
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String loginValid(String token) throws RemoteException;
	
	/**
	 * <pre>
	 * 프로그램의 영상별 스토리보드 조회
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String getSceanInfo(Long masterId) throws RemoteException;
	
	/**
	 * <pre>
	 * 프로그램 기본정보 조회
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String getBaseInfo(Long masterId) throws RemoteException;
	
	/**
	 * <pre>
	 * 요청한 작업에대한 상태정보 조회
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String getJobState(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * Master_id를 기준으로 하위에 생성된 영상의 ID 목록을 반환
	 * </pre>
	 * @param masterId
	 * @return
	 * @throws RemoteException
	 */
	public String getGroupForMaster(Long masterId) throws RemoteException;
	
	/**
	 * <pre>
	 * 영상ID의 코너정보 및 샷정보를 반환
	 * </pre>
	 * @param ct_id
	 * @return
	 * @throws RemoteException
	 */
	public String getSceanInfoForIfCms(Long ctId) throws RemoteException;
	
	public String getApproveList(String xml) throws RemoteException;
	
	public String getApproveDetail(String cartNo, String userId ) throws RemoteException;
	
	public String getDownloadList(String xml) throws RemoteException;
	
	public String getDownloadDetail(String cartNo, String userId) throws RemoteException;	
	
	/**
	 * <pre>
	 * 승인 or 취소
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public Integer requestApprove(String xml) throws RemoteException;
	
	
	/**
	 * <pre>
	 * 작업 취소 요청
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public Integer requestJobCacel(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 사용할 스토리지 용량 체크
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String getDiskSpace(String xml) throws RemoteException;
	
	public String isVideoRelatedYN(Long masterId, Long ctId) throws RemoteException;
	

}

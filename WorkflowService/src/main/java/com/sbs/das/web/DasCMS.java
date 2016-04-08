package com.sbs.das.web;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface DasCMS {
	
	/**
	 * <pre>
	 * WISE(ERP) 프로그램 정보 신규 등록 및 변경
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void savePgmInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 프로그램 회차 조회.
	 * 방송일, 시작시간, 종료시간으로 메타정보 조회
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findEpisodeList(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 회차 메타정보 변경
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void updateEpisodeInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 회차 코너 및 사용제한 등급 변경
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void updateCornerInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 다운로드 및 전송요청
	 * 영상이 스토리지에 있으면 즉시 전송, 없다면 다운로드 후 전송
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public long transferRequest(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * OPS TC에서 다운로드 및 전송 요청 후 진행률도 조회하는 것으로 결정
	 * <states>
	 * 	<state>
	 * 		<job_id>12345</job_id>
	 *      <progress>25</progress>
	 *      <job_cd>MD</job_cd> // MD: 다운로드, MF: 전송
	 *  </state>
	 *  <state>
	 * 		<job_id>67890</job_id>
	 *      <progress>100</progress>
	 *      <job_cd>MF</job_cd> // MD: 다운로드, MF: 전송
	 *  </state>
	 * </states>
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	@Deprecated
	public String findStatus(String xml) throws RemoteException;
	
}

package com.sbs.das.commons.system;

import java.rmi.RemoteException;

/**
 * DAS CMS에서 제공하는 서비스
 */
public interface DasCmsConnector {
	/**
	 * <pre>
	 * OnAir 장비중 SDI Ingest에서 생성된 영상에 대해서 DAS CMS에 해당 xml을 전달하여 File Ingest에 Job 할당을 하게 된다.
	 * OnAir에서 생성된 영상은 (송출본 or 방송본) 영상이며, Mpeg2 영상만 생성되고 File Ingest에 전달하여 Mpeg4 영상을 생성요청한다.
	 * Mpeg4 영상이 성공했을경우 DAS CMS에 완료 메세지를 전달하고 CMS에서는 완료상태를 받았을경우 DAS Workflow의 archiveService를 호출하여
	 * 아카이브 저장 요청을 한다.
	 * </pre>
	 * @param xml
	 * @return String
	 * @throws RemoteException
	 */
	public Integer insertArchiveReq(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * PDS에서 아카이브 저장 요청한 영상에 대해서 DIVA로부터 완료 메세지를 받았을경우 DAS CMS에게 
	 * NUM key를 전달한다.
	 * </pre>
	 * @param num
	 * @throws RemoteException
	 */
	public String completeDown(long num) throws RemoteException;
	
	/**
	 * <pre>
	 * DAS Cms에 TC요청을 전달한다.
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void insertTranscodeReq(String xml) throws RemoteException;
	
	public String getMetadatInfoList(String xml) throws RemoteException;
	
	public Integer insertPDSArchive(String xml) throws RemoteException;
	
}

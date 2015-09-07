package com.sbs.das.commons.system;

import java.rmi.RemoteException;

/**
 * Diva Connector에서 제공하는 아카이브 관련 서비스 
 */
public interface DivaConnectSerivce {
	
	/**
	 * <pre>
	 * Archive Backup 요청 메소드.
	 * info Element의 cti_id값이 존재하고 copy_to_group의 value가 'y'일경우 Backup 후 복본 생성 요청
	 * </pre>
	 * @param xml
	 * @return 
	 * @throws RemoteException
	 */
	public String archive(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * DTL 장비에 아카이브된 영상에 대해서 복본(copy)만 요청할 경우 사용
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String archiveCopy(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * Archive Restore 요청 메소드.
	 * info Element의 cti_id값이 존재하고 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String restore(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * Archive Partial Restore 요청 메소드
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String restorePFR(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String delete(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String cancelJob(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String changePrioity(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String recovery(String xml) throws RemoteException;
}

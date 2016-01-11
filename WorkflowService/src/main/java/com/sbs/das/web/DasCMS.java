package com.sbs.das.web;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService
public interface DasCMS {

	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findPgmList(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void savePgmInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findEpisodeList(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void updateEpisodeInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @throws RemoteException
	 */
	public void updateCornerInfo(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public boolean transferRequest(String xml) throws RemoteException;
	
	/**
	 * <pre>
	 * 
	 * </pre>
	 * @param xml
	 * @return
	 * @throws RemoteException
	 */
	public String findStatus(String xml) throws RemoteException;
}

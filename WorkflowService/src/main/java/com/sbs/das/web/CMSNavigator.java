package com.sbs.das.web;

import java.rmi.RemoteException;

import javax.jws.WebService;

@WebService(endpointInterface = "com.sbs.das.web.DasCMS")
public class CMSNavigator implements DasCMS {

	public String findPgmList(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void savePgmInfo(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String findEpisodeList(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateEpisodeInfo(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public void updateCornerInfo(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public boolean transferRequest(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public String findStatus(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}

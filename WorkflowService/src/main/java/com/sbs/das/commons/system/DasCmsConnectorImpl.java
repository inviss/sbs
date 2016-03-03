package com.sbs.das.commons.system;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Locale;

import javax.xml.rpc.ServiceException;
   
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.app.das.webservices.PDASServices;
import com.app.das.webservices.PDASServicesService;
import com.app.das.webservices.PDASServicesServiceLocator;

public class DasCmsConnectorImpl implements DasCmsConnector {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	private  PDASServices pdasServices;
	
	public void init() throws MalformedURLException {
		try {
			PDASServicesService pdasServicesService = new PDASServicesServiceLocator();
			
			String ip = messageSource.getMessage("cms.major.ip", null, Locale.KOREA);
			String port = messageSource.getMessage("cms.major.port", null,Locale.KOREA);
			if(logger.isDebugEnabled()) {
				logger.debug("cms ip: "+ip);
				logger.debug("cms port: "+port);
			}
			pdasServices = pdasServicesService.getPDASServices(new URL("http://"+ip+":"+port+"/PDAS/services/PDASServices"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	  
	public Integer insertArchiveReq(String xml) throws RemoteException {
		return pdasServices.insertArchiveReq(xml);
	}

	public String completeDown(long num) throws RemoteException {
		return pdasServices.completeDown(new Long(num).intValue());
	}

	public void insertTranscodeReq(String xml) throws RemoteException {
		pdasServices.insertComMedia(xml);
	}

	public String getMetadatInfoList(String xml) throws RemoteException {
		return pdasServices.getMetadatInfoList(xml);
	}

	public Integer insertPDSArchive(String xml) throws RemoteException {
		return pdasServices.insertPDSArchive(xml);
	}

	public String insertDownCart(String xml) throws RemoteException {
		return pdasServices.insertDownCartInfo(xml);
	}
	
	public String insertCartCont(String xml) throws RemoteException {
		return pdasServices.insertCartContInfo(xml);
	}

	public Integer updateDownCart(String xml) throws RemoteException {
		return pdasServices.updateDownCart(xml);
	}

}

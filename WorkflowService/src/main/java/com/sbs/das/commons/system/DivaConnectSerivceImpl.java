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

import DIVAConnector.DivaConnector;
import DIVAConnector.DivaConnectorLocator;
import DIVAConnector.DivaConnectorPortType;

public class DivaConnectSerivceImpl implements DivaConnectSerivce{

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MessageSource messageSource;
	private DivaConnectorPortType portType;
	
	public void init() throws MalformedURLException {
		try {
			DivaConnector divaConnector = new DivaConnectorLocator();
			String ip = messageSource.getMessage("diva.major.ip", null, Locale.KOREA);
			String port = messageSource.getMessage("diva.major.port", null,Locale.KOREA);
			if(logger.isDebugEnabled()) {
				logger.debug("diva ip: "+ip);
				logger.debug("diva port: "+port);
			}
			portType = divaConnector.getDivaConnectorPort(new URL("http://"+ip+":"+port+"/DIVAConnector.php"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public String archive(String xml) throws RemoteException {
		return portType.archive(xml);
	}

	public String archiveCopy(String xml) throws RemoteException {
		return portType.storeCopy(xml);
	}
	
	public String restore(String xml) throws RemoteException {
		return portType.restore(xml);
	}

	public String restorePFR(String xml) throws RemoteException {
		return portType.restorePFR(xml);
	}

	public String delete(String xml) throws RemoteException {
		return portType.delete(xml);
	}

	public String cancelJob(String xml) throws RemoteException {
		return portType.cancel(xml);
	}

	public String changePrioity(String xml) throws RemoteException {
		return portType.changePriority(xml);
	}

	public String recovery(String xml) throws RemoteException {
		return portType.recovery(xml);
	}

}

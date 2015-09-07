package kr.co.d2net.commons.system;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.xml.rpc.ServiceException;

import kr.co.d2net.commons.utils.PropertyLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.das.webservices.PDASServices;
import com.app.das.webservices.PDASServicesService;
import com.app.das.webservices.PDASServicesServiceLocator;
import com.sbs.das.web.Nevigator;
import com.sbs.das.web.ServiceNevigatorService;
import com.sbs.das.web.ServiceNevigatorServiceLocator;

public class DasCmsConnectorImpl implements DasCmsConnector {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Properties properties;
	private PDASServices pdasServices;
	private Nevigator nevigator;
	
	public DasCmsConnectorImpl() throws MalformedURLException {
		try {
			PDASServicesService pdasServicesService = new PDASServicesServiceLocator();
			ServiceNevigatorService nevigatorService = new ServiceNevigatorServiceLocator();
			
			properties = PropertyLoader.getProperty("config");
			
			String ip = properties.getProperty("cms.major.ip");
			String port = properties.getProperty("cms.major.port");
			if(logger.isDebugEnabled()) {
				logger.debug("cms ip: "+ip);
				logger.debug("cms port: "+port);
			}
			pdasServices = pdasServicesService.getPDASServices(new URL("http://"+ip+":"+port+"/PDAS/services/PDASServices"));
			nevigator = nevigatorService.getServiceNevigatorPort(new URL("http://"+ip+":"+port+"/WorkflowService/services/ServiceNevigator"));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public DasCmsConnectorImpl(String url) throws MalformedURLException {
		try {
			PDASServicesService pdasServicesService = new PDASServicesServiceLocator();
			//ServiceNevigatorService nevigatorService = new ServiceNevigatorServiceLocator();

			pdasServices = pdasServicesService.getPDASServices(new URL(url));
			//nevigator = nevigatorService.getServiceNevigatorPort(new URL(workflowURL));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	public Integer archiveReq(String xml) throws RemoteException {
		return pdasServices.insertPDSArchive(xml);
	}

	public String findContents(String xml) throws RemoteException {
		return pdasServices.getSearchText(xml);
	}

	public String login(String tokenDO) throws RemoteException {
		return pdasServices.isValidUser(tokenDO);
	}

	public String loginValid(String token) throws RemoteException {
		return pdasServices.isValidUserWithToken(token);
	}

	public String getSceanInfo(Long masterId) throws RemoteException {
		return  pdasServices.getSceanInfo(masterId);
	}

	public String getBaseInfo(Long masterId) throws RemoteException {
		return  pdasServices.getBaseInfo(masterId);
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

	public void transferReq(String xml) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	public String getJobState(String xml) throws RemoteException {
		return pdasServices.getJobStatus(xml);
	}

	public String getGroupForMaster(Long masterId) throws RemoteException {
		return pdasServices.getGroupForMaster(masterId);
	}

	public String getSceanInfoForIfCms(Long ctId) throws RemoteException {
		return pdasServices.getSceanInfoForIfCms(ctId);
	}

	public String getApproveList(String xml) throws RemoteException {
		return pdasServices.getMyDownloadAprroveList(xml);
	}

	public String getApproveDetail(String cartNo, String userId) throws RemoteException {
		return pdasServices.getMyDownloadAprroveDetailList(cartNo, userId);
	}

	public String getDownloadList(String xml) throws RemoteException {
		return pdasServices.getMyDownloadRequestList(xml);
	}

	public String getDownloadDetail(String cartNo, String userId) throws RemoteException {
		return pdasServices.getDownloadRequestDetailList(cartNo, userId);
	}

	public Integer requestApprove(String xml) throws RemoteException {
		return pdasServices.updateDownloadRequestDetail(xml);
	}

	public Integer requestJobCacel(String xml) throws RemoteException {
		return pdasServices.cancelJob(xml);
	}

	public String getDiskSpace(String xml) throws RemoteException {
		return pdasServices.getStorageCheck(xml);
	}

	public String isVideoRelatedYN(Long masterId, Long ctId)
			throws RemoteException {
		return pdasServices.isVideoReleateYN(masterId, ctId);
	}


}

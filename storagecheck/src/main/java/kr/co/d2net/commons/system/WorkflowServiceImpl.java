package kr.co.d2net.commons.system;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;

import kr.co.d2net.commons.utils.PropertyLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sbs.das.web.Nevigator;
import com.sbs.das.web.ServiceNevigatorService;
import com.sbs.das.web.ServiceNevigatorServiceLocator;

@Service(value="workflowService")
public class WorkflowServiceImpl implements WorkflowService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	private Properties properties;
	private Nevigator nevigator;
	public WorkflowServiceImpl() throws Exception {
		
		properties = PropertyLoader.getProperty("config");
		
		String ip = properties.getProperty("cms.major.ip");
		String port = properties.getProperty("cms.major.port");
		if(logger.isDebugEnabled()) {
			logger.debug("cms ip: "+ip);
			logger.debug("cms port: "+port);
		}
		
		ServiceNevigatorService nevigatorService = new ServiceNevigatorServiceLocator();
		try {
			nevigator = nevigatorService.getServiceNevigatorPort(new URL("http://"+ip+":"+port+"/WorkflowService/services/ServiceNevigator"));
		} catch (Exception e) {
			throw e;
		}
	}

	public void regiesterArchive(String xml) throws RemoteException {
		nevigator.archiveService(xml);
	}

}

package com.sbs.das.web;

import java.rmi.RemoteException;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.dto.xml.Das;

@WebService(endpointInterface = "com.sbs.das.web.DasCMS")
public class CMSNavigator implements DasCMS {
	
	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	
	public String findPgmList(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("findPgmList Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findPgmList xml parsing error!!", e);
			throw new RemoteException("findPgmList xml parsing error", e);
		}
		return "findPgmList";
	}

	public void savePgmInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("savePgmInfo Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("savePgmInfo xml parsing error!!", e);
			throw new RemoteException("savePgmInfo xml parsing error", e);
		}
		
	}

	public String findEpisodeList(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("findEpisodeList Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findEpisodeList xml parsing error!!", e);
			throw new RemoteException("findEpisodeList xml parsing error", e);
		}
		return "findEpisodeList";
	}

	public void updateEpisodeInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("updateEpisodeInfo Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("updateEpisodeInfo xml parsing error!!", e);
			throw new RemoteException("updateEpisodeInfo xml parsing error", e);
		}
	}

	public void updateCornerInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("updateCornerInfo Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("updateCornerInfo xml parsing error!!", e);
			throw new RemoteException("updateCornerInfo xml parsing error", e);
		}
	}

	public long transferRequest(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("transferRequest Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("transferRequest xml parsing error!!", e);
			throw new RemoteException("transferRequest xml parsing error", e);
		}
		return 0L;
	}

	public String findStatus(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("findStatus Call XML: "+xml);
		}

		Das das = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			//das = (Das)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findStatus xml parsing error!!", e);
			throw new RemoteException("findStatus xml parsing error", e);
		}
		return "findStatus";
	}

}

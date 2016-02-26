package com.sbs.das.web;

import java.rmi.RemoteException;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.services.CornerService;
import com.sbs.das.services.MetadataService;
import com.sbs.das.services.PgmInfoService;

@WebService(endpointInterface = "com.sbs.das.web.DasCMS")
public class CMSNavigator implements DasCMS {

	final Logger logger = LoggerFactory.getLogger(getClass());

	final private String XML_PREFIX = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private XmlStream xmlStream;
	@Autowired
	private MetadataService metadataService;
	@Autowired
	private PgmInfoService pgmInfoService;
	@Autowired
	private CornerService cornerService;


	public void savePgmInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("savePgmInfo Call XML: "+xml);
		}

		Data data = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			data = (Data)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("savePgmInfo xml parsing error!!", e);
			throw new RemoteException("savePgmInfo xml parsing error", e);
		}
		if(data.getProgram() == null || StringUtils.isBlank(data.getProgram().getDasPgmCd())) {
			throw new RemoteException("Program Object is null or UniqueID is blank!");
		}
		/*
			PgmInfoTbl pgmInfoTbl = pgmInfoService.getPgmInfo(data.getProgram().getDasPgmCd());
			if(pgmInfoTbl != null) {
				pgmInfoService.savePgmInfo(pgmInfoTbl);
			} else {
				throw new RemoteException("Can't find the return value from the requesting ID - "+data.getProgram().getDasPgmCd());
			}
		 */
		try {
			pgmInfoService.savePgmInfo(data.getProgram());
		} catch (Exception e) {
			throw new RemoteException("Program Info Save Error", e);
		}



	}

	public String findEpisodeList(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("findEpisodeList Call XML: "+xml);
		}

		Data data = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			data = (Data)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("findEpisodeList xml parsing error!!", e);
			throw new RemoteException("findEpisodeList xml parsing error", e);
		}

		try {
			List<MetadatMstTbl> metadatMstTbls = metadataService.findMetaDataList(data);

			data = new Data();
			for(MetadatMstTbl metadatMstTbl : metadatMstTbls) {
				Metadata metadata = new Metadata();
				metadata.setDasMasterId(metadatMstTbl.getMasterId());
				metadata.setDasPgmCd(metadatMstTbl.getPgmCd());
				metadata.setChId(metadatMstTbl.getChennelCd());
				metadata.setPgmTms(metadatMstTbl.getEpisNo());
				metadata.setPgmTmsTitle(metadatMstTbl.getTitle());
				metadata.setBradDay(metadatMstTbl.getBrdDd());
				metadata.setBradStTime(metadatMstTbl.getBrdBgnHms());
				metadata.setBradFnsTime(metadatMstTbl.getBrdEndHms());
				metadata.setBradLen(metadatMstTbl.getDuration());

				data.addMetadatas(metadata);
			}
		} catch (Exception e) {
			throw new RemoteException("Metadata Info Find Error", e);
		}

		return XML_PREFIX+xmlStream.toXML(data);
	}

	public void updateEpisodeInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("updateEpisodeInfo Call XML: "+xml);
		}

		Data data = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			data = (Data)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("updateEpisodeInfo xml parsing error!!", e);
			throw new RemoteException("updateEpisodeInfo xml parsing error", e);
		}
		if(data != null && data.getMetadatas().size() > 0) {
			Metadata mst = (Metadata)data.getMetadatas().get(0);
			if(mst.getDasMasterId() == null || mst.getDasMasterId() <= 0)
				throw new RemoteException("Primary Key is null or wrong value! - master_id: "+mst.getDasMasterId());

			try {
				metadataService.updateMetadataInfo(mst);
			} catch (ServiceException e) {
				throw new RemoteException("Metadata Info Update Error", e);
			}
		}

	}

	public void updateCornerInfo(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("updateCornerInfo Call XML: "+xml);
		}

		Data data = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			data = (Data)xmlStream.fromXML(xml);
		} catch (Exception e) {
			logger.error("updateCornerInfo xml parsing error!!", e);
			throw new RemoteException("updateCornerInfo xml parsing error", e);
		}
		
		if(data == null || (data.getDasMasterId() == null || data.getDasMasterId() <= 0L)) {
			throw new RemoteException("Primary Key is null or wrong value!");
		}

		try {
			cornerService.updateCorners(data);
		} catch (Exception e) {
			throw new RemoteException("Corners Info Update Error", e);
		}
		
	}

	public long transferRequest(String xml) throws RemoteException {
		if(logger.isDebugEnabled()){
			logger.debug("transferRequest Call XML: "+xml);
		}

		Data data = null;
		try {
			if(StringUtils.isBlank(xml)) {
				throw new RemoteException("The Requested XML is Blank!");
			}
			data = (Data)xmlStream.fromXML(xml);
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

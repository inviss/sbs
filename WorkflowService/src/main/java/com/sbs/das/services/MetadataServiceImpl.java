package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.repository.MetadatMstDao;

@Service(value="metadataService")
public class MetadataServiceImpl implements MetadataService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MetadatMstDao metadatMstDao;
	
	public List<MetadatMstTbl> findMetaDataList(Data data) throws ServiceException {
		if(logger.isDebugEnabled()) {
			logger.debug("brd_dd   : "+data.getBradDay());
			logger.debug("brdBgnHms: "+data.getBradStTime());
			logger.debug("brdEndHms: "+data.getBradFnsTime());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brdDd", data.getBradDay());
		params.put("brdBgnHms", data.getBradStTime());
		params.put("brdEndHms", data.getBradFnsTime());
		
		return metadatMstDao.findMetadataList(params);
	}

	public void updateMetadataInfo(Metadata metadata) throws ServiceException {
		if(metadata.getDasMasterId() == null || metadata.getDasMasterId() <= 0)
			throw new ServiceException("das_master_id's value is wrong!");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", metadata.getDasMasterId());
		
		MetadatMstTbl mstTbl = metadatMstDao.getMetadata(params);
		if(mstTbl == null)
			throw new ServiceException("MetadatMstTbl is null - master_id: "+metadata.getDasMasterId());
		
		mstTbl.setMasterId(metadata.getDasMasterId());
		
		
	}

}

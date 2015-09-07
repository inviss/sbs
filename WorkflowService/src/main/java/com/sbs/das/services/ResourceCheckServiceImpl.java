package com.sbs.das.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.StorageInfoTbl;
import com.sbs.das.repository.StorageInfoDao;
import com.sbs.das.repository.UpdateMetaDataDao;

@Service(value="resourceCheckService")
public class ResourceCheckServiceImpl implements ResourceCheckService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private StorageInfoDao storageInfoDao;
	@Autowired
	private UpdateMetaDataDao updateMetaDataDao;

	@SuppressWarnings("unchecked")
	public List<StorageInfoTbl> findDiskQuota(Map<String, Object> params)
			throws ServiceException {
		List<StorageInfoTbl> storageInfoTbls = null;
		try {
			storageInfoTbls = storageInfoDao.findStorageInfo(params);
		} catch (ServiceException e) {
			logger.error("findDiskQuota error", e);
		}
		return (storageInfoTbls == null) ? Collections.EMPTY_LIST : storageInfoTbls;
	}

	public void updateDiskQuota(StorageInfoTbl storageInfoTbl)
			throws ServiceException {
		try {
			storageInfoDao.updateStorageInfo(storageInfoTbl);
		} catch (ServiceException e) {
			logger.error("updateDiskQuota error", e);
		}
	}

	public List<MasterTbl> findMetaData(String regDt) throws ServiceException {
		// TODO Auto-generated method stub
		return updateMetaDataDao.findMetaData(regDt);
	}

	public Long getCtMeta(Long masterId) throws ServiceException {
		// TODO Auto-generated method stub
		return updateMetaDataDao.getCtMeta(masterId);
	}

	public void updateMetaData(Long masterId, Long ctId)
			throws ServiceException {
		updateMetaDataDao.updateMetaData(masterId, ctId);
	}

}

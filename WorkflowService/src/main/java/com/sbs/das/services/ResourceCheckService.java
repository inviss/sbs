package com.sbs.das.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.StorageInfoTbl;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface ResourceCheckService {
	
	public List<StorageInfoTbl> findDiskQuota(Map<String, Object> params) throws ServiceException;
	
	public void updateDiskQuota(StorageInfoTbl storageInfoTbl) throws ServiceException;
	
	public List<MasterTbl> findMetaData(String regDt) throws ServiceException;
	
	public Long getCtMeta(Long masterId) throws ServiceException;
	
	public void updateMetaData(Long masterId, Long ctId) throws ServiceException;
}

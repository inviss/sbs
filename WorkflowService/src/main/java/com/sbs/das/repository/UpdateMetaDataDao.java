package com.sbs.das.repository;

import java.util.List;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MasterTbl;

public interface UpdateMetaDataDao {
	public List<MasterTbl> findMetaData(String regDt) throws ServiceException;
	
	public Long getCtMeta(Long masterId) throws ServiceException;
	
	public void updateMetaData(Long masterId, Long ctId) throws ServiceException;
}

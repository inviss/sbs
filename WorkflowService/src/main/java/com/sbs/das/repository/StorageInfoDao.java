package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.StorageInfoTbl;

public interface StorageInfoDao {
	public void insertStorageInfo(StorageInfoTbl storageInfoTbl) throws DaoRollbackException;
	
	public void updateStorageInfo(StorageInfoTbl storageInfoTbl) throws DaoRollbackException;
	
	public List<StorageInfoTbl> findStorageInfo(Map<String, Object> params) throws DaoNonRollbackException;
	
	public StorageInfoTbl getStorageInfo(Map<String, Object> params) throws DaoNonRollbackException;
}

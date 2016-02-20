package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.MetadatMstTbl;

public interface MetadatMstDao {
	public List<MetadatMstTbl> findMetadataList(Map<String, Object> params) throws DaoNonRollbackException;
	public MetadatMstTbl getMetadata(Map<String, Object> params) throws DaoNonRollbackException;
	public void saveMetadata(MetadatMstTbl metadatMstTbl) throws DaoRollbackException;
}

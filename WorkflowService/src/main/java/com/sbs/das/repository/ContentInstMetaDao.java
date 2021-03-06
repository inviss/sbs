package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentInstTbl;

public interface ContentInstMetaDao {
	
	public void insertContentInst(ContentInstTbl contentInstTbl) throws DaoRollbackException;

	public void updateContentInst(ContentInstTbl contentInstTbl) throws DaoRollbackException;

	public long getContentInstNewId() throws DaoNonRollbackException;

	public List<ContentInstTbl> findContentInst(Map<String, Object> params) throws DaoNonRollbackException; 

	public ContentInstTbl getContentInst(Map<String, Object> params) throws DaoNonRollbackException;
	
	public Integer getContentInstCount(Map<String, Object> params) throws DaoNonRollbackException;
	
	public int callProceduer(Long cti_id) throws DaoNonRollbackException;
	
	public Long getMasterIdOnly(Long ctiId) throws DaoNonRollbackException;
	
	public ContentInstTbl getMasterObj(Long ctiId) throws DaoNonRollbackException;
	
	public ContentInstTbl getContentInst(Long masterId) throws DaoNonRollbackException;
}

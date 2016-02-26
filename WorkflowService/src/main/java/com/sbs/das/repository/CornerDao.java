package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.CornerTbl;

public interface CornerDao {
	public Integer getCornerCount(Map<String, Object> params) throws DaoNonRollbackException;
	
	public long getCornerMax(Map<String, Object> params) throws DaoNonRollbackException;
	
	public CornerTbl getCornerObj(Map<String, Object> params) throws DaoNonRollbackException;
	
	public long getCornerNewId() throws DaoNonRollbackException;
	
	public Long insertCorner(CornerTbl cornerTbl) throws DaoRollbackException;
	
	public void updateCorner(CornerTbl cornerTbl) throws DaoRollbackException;
	
	public void deleteCorner(Long masterId) throws DaoRollbackException;
}

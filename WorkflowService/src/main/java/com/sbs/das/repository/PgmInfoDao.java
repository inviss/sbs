package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.PgmInfoTbl;

public interface PgmInfoDao {
	
	public List<PgmInfoTbl> findPgmInfo(Map<String, Object> params) throws DaoNonRollbackException;
	public PgmInfoTbl getPgmInfo(Map<String, Object> params) throws DaoNonRollbackException;
	public void updatePgmInfo(PgmInfoTbl pgmInfoTbl) throws DaoRollbackException;
	
}

package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;

public interface QuartzManagerDao {
	public List<String> findQuartzTables() throws DaoNonRollbackException;
	
	public void createQuartzTables(Map<String, String> params) throws DaoRollbackException;
	
	public void quartzTableInitialize(String lockName) throws DaoRollbackException;
	
	public void insertLockName(String lockName) throws DaoRollbackException;
}

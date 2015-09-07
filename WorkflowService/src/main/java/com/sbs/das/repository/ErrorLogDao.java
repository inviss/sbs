package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ErrorLogTbl;
import com.sbs.das.dto.StorageInfoTbl;

public interface ErrorLogDao {
	public void insertErrorLog(ErrorLogTbl errorLogTbl) throws DaoRollbackException;

}

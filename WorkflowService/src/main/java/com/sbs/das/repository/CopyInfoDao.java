package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.CopyInfoTbl;

public interface CopyInfoDao {
	public CopyInfoTbl getCopyInfoObj(Map<String, Object> params) throws DaoNonRollbackException;
}

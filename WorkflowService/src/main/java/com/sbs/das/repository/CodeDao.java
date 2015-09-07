package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.CodeTbl;

public interface CodeDao {
	
	public CodeTbl getCodeObj(Map<String, Object> params) throws DaoNonRollbackException;
}

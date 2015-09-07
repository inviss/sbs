package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.TimeRistSetTbl;

public interface TimeRistSetDao {
	public TimeRistSetTbl getTimeRistSet(Map<String, Object> params) throws DaoNonRollbackException;
}

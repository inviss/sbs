package com.sbs.das.repository;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.ErpTapeTbl;

public interface TapeDao {
	public ErpTapeTbl getTapeObj(String tapeId) throws DaoNonRollbackException;
}

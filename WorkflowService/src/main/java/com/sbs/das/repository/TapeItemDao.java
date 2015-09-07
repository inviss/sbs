package com.sbs.das.repository;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.ErpTapeItemTbl;

public interface TapeItemDao {
	public ErpTapeItemTbl getTapeItemObj(String tapeItemId) throws DaoNonRollbackException;
}

package com.sbs.das.repository;

import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.AttachTbl;

public interface AttachDao {
	public void insertAttach(AttachTbl attachTbl) throws DaoRollbackException;
}

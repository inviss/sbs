package com.sbs.das.repository;

import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.NotReportMsgTbl;

public interface NotReportedDao {
	public void insertNotReportedMsg(NotReportMsgTbl notReportMsgTbl) throws DaoRollbackException;
}

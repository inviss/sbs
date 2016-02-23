package com.sbs.das.services;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.PgmInfoTbl;
import com.sbs.das.dto.ops.Program;

public interface PgmInfoService {
	public PgmInfoTbl getPgmInfo(String pgmCd) throws ServiceException;
	public void savePgmInfo(Program program) throws ServiceException;
}

package com.sbs.das.services;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.NotReportMsgTbl;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface NotReportedService {
	
	@Transactional
	public void insertNotReported(NotReportMsgTbl notReportMsgTbl ) throws ServiceException;
}

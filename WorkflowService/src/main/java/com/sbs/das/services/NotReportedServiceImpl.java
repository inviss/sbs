package com.sbs.das.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.NotReportMsgTbl;
import com.sbs.das.repository.NotReportedDao;

@Service(value="notReportedService")
public class NotReportedServiceImpl implements NotReportedService {
	
	@Autowired
	private NotReportedDao notReportedDao;
	
	public void insertNotReported(NotReportMsgTbl notReportMsgTbl)
			throws ServiceException {
		
		notReportedDao.insertNotReportedMsg(notReportMsgTbl);
	}

}

package com.sbs.das.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.CornerDao;

@Service(value="cornerService")
public class CornerServiceImpl implements CornerService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CornerDao cornerDao;
	@Autowired
	private ContentDao contentDao;
	
	public void updateCorners(Data data) throws ServiceException {
		ContentTbl contentTbl = contentDao.getContentWithMap(data.getDasMasterId());
		Long ctId = contentTbl.getCtId();
		
		long total = data.getTotalDuration() - contentTbl.getDuration();
		long main = data.getMainDuration() - contentTbl.getDuration();
	}

}

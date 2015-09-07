package com.sbs.das.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MerHistTbl;
import com.sbs.das.repository.MerUnCompressDao;

@Service(value="merUnCompressService")
public class MerUnCompressServiceImpl implements MerUnCompressService {

	@Autowired
	private MerUnCompressDao merUnCompressDao;
	
	public List<MerHistTbl> findNewJobs() throws ServiceException {
		return merUnCompressDao.findNewJobs();
	}

	public MerHistTbl getMerJob(Long seq) throws ServiceException {
		return merUnCompressDao.getMerJob(seq);
	}

	public void insertMerJob(MerHistTbl merHisTbl) throws ServiceException {
		merUnCompressDao.insertMerJob(merHisTbl);
	}

	public void updateMerJob(MerHistTbl merHistTbl) throws ServiceException {
		merUnCompressDao.updateMerJob(merHistTbl);
	}

}

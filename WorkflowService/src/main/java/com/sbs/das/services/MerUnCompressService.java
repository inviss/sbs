package com.sbs.das.services;

import java.util.List;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MerHistTbl;

public interface MerUnCompressService {
	public List<MerHistTbl> findNewJobs() throws ServiceException;
	public MerHistTbl getMerJob(Long seq) throws ServiceException;
	public void insertMerJob(MerHistTbl merHisTbl) throws ServiceException;
	public void updateMerJob(MerHistTbl merHistTbl) throws ServiceException;
}

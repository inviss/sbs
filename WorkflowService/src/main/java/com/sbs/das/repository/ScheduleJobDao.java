package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ScheduleJobTbl;

public interface ScheduleJobDao {
	public List<ScheduleJobTbl> findScheduleJob(Map<String, Object> params) throws ServiceException;
	
	public ScheduleJobTbl getScheduleJob(Map<String, Object> params) throws ServiceException;
	
	public void updateScheduleJob(ScheduleJobTbl scheduleJobTbl) throws ServiceException;
}

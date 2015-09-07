package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ScheduleJobTbl;

@Repository(value="scheduleJobDao")
public class ScheduleJobDaoImpl extends SqlMapClientDaoSupport implements ScheduleJobDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<ScheduleJobTbl> findScheduleJob(Map<String, Object> params)
			throws ServiceException {
		return getSqlMapClientTemplate().queryForList("ScheduleJob.findScheduleJob", params);
	}

	public ScheduleJobTbl getScheduleJob(Map<String, Object> params) throws ServiceException {
		return (ScheduleJobTbl)getSqlMapClientTemplate().queryForObject("ScheduleJob.getScheduleJob", params);
	}

	public void updateScheduleJob(ScheduleJobTbl scheduleJobTbl)
			throws ServiceException {
		getSqlMapClientTemplate().update("ScheduleJob.updateScheduleJob", scheduleJobTbl);
	}

}

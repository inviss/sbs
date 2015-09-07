package com.sbs.das.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.repository.QuartzManagerDao;

@Service(value="scheduleJobService")
public class QuartzManagerServiceImpl implements QuartzManagerService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QuartzManagerDao quartzManagerDao;
	
	@SuppressWarnings("serial")
	final List<String> QRTZ_TABLE_LIST = new ArrayList<String>() {
		{
			add("QRTZ_JOB_DETAILS");
			add("QRTZ_JOB_LISTENERS");
			add("QRTZ_TRIGGERS");
			add("QRTZ_SIMPLE_TRIGGERS");
			add("QRTZ_CRON_TRIGGERS");
			add("QRTZ_BLOB_TRIGGERS");
			add("QRTZ_TRIGGER_LISTENERS");
			add("QRTZ_CALENDARS");
			add("QRTZ_FIRED_TRIGGERS");
			add("QRTZ_PAUSED_TRIGGER_GRPS");
			add("QRTZ_SCHEDULER_STATE");
			add("QRTZ_LOCKS");
		}
	};
	
	@SuppressWarnings("serial")
	final List<String> LOCK_NAMES = new ArrayList<String>() {
		{
			add("TRIGGER_ACCESS");
			add("JOB_ACCESS");
			add("CALENDAR_ACCESS");
			add("STATE_ACCESS");
			add("MISFIRE_ACCESS");
		}
	};
	

	public List<String> findQuartzTables() throws ServiceException {
		return quartzManagerDao.findQuartzTables();
	}

	public void createQuartzTables(List<String> tableNames)
			throws ServiceException {
		
		Map<String, String> params = new HashMap<String, String>();
		
		for(String tableName : QRTZ_TABLE_LIST) {
			if(!tableNames.contains(tableName)) {
				if(logger.isInfoEnabled()) {
					logger.info("Quartz Table Create: "+tableName);
				}
				params.clear();
				params.put("tableName", tableName);
				
				quartzManagerDao.createQuartzTables(params);
				if(tableName.equals("QRTZ_LOCKS")) {
					for(String lockName : LOCK_NAMES) {
						quartzManagerDao.insertLockName(lockName);
					}
				}
			}
		}
	}

	public void quartzTableInitialize()
			throws ServiceException {
		
		/*
		 * Quartz 관련 테이블중 몇몇 테이블에서 Forein key 가 설정되어 있으므로 
		 * 데이타를 삭제를 할 경우에는 생성된 테이블의 역순으로 삭제처리를 진행해야 한다.
		 */
		List<String> tableNames = QRTZ_TABLE_LIST;
		Collections.reverse(tableNames);

		for(String tableName : tableNames) {
			if(!tableName.equals("QRTZ_LOCKS"))
				quartzManagerDao.quartzTableInitialize(tableName);
		}
	}
	
	
}

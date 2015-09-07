package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.TimeRistSetTbl;

@Repository(value="timeRistSetDao")
public class TimeRistSetDaoImpl extends SqlMapClientDaoSupport implements TimeRistSetDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	public TimeRistSetTbl getTimeRistSet(Map<String, Object> params)
			throws DaoNonRollbackException {
		TimeRistSetTbl timeRistSetTbl = (TimeRistSetTbl)getSqlMapClientTemplate().queryForObject("TimeRistSet.getTimeRistSet", params);
		return timeRistSetTbl;
	}

}

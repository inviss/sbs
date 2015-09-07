package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;

@Repository(value="quartzManagerDao")
public class QuartzManagerDaoImpl extends SqlMapClientDaoSupport implements QuartzManagerDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<String> findQuartzTables() throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Quartz.findTables");
	}

	public void createQuartzTables(Map<String, String> params)
			throws DaoRollbackException {
		getSqlMapClientTemplate().queryForObject("Quartz.createTables", params);
	}

	public void quartzTableInitialize(String lockName) throws DaoRollbackException {
		getSqlMapClientTemplate().delete("Quartz.tableInitializ", lockName);
	}

	public void insertLockName(String lockName) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Quartz.insertLocks", lockName);
	}

}

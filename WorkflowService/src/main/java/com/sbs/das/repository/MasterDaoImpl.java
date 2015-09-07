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
import com.sbs.das.dto.MasterTbl;

@Repository(value="masterDao")
public class MasterDaoImpl extends SqlMapClientDaoSupport implements MasterDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public void updateArchiveComplete(Map<String, Object> params)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("Master.updateMasterArchRegDay", params);
	}

	@SuppressWarnings("unchecked")
	public List<MasterTbl> findMaster(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Master.findMaster", params);
	}

	public Long getMaxMaster(Map<String, Object> params)
			throws DaoNonRollbackException {
		Long masterId = (Long)getSqlMapClientTemplate().queryForObject("Master.getMasterMax", params);
		return (masterId == null) ? 0L : masterId;
	}

	public Long getMasterNewId() throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("Master.getMasterNewId");
	}

	public void updateMaster(MasterTbl masterTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Master.updateMaster", masterTbl);
	}

	public void insertMaster(MasterTbl masterTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Master.insertMaster", masterTbl);
	}

	public MasterTbl getMaster(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (MasterTbl)getSqlMapClientTemplate().queryForObject("Master.getMasterObj", params);
	}

	public String getCocd(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (String)getSqlMapClientTemplate().queryForObject("Master.getCocd", params);
	}

}

package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.CornerTbl;

@Repository(value="cornerDao")
public class CornerDaoImpl extends SqlMapClientDaoSupport implements CornerDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public Integer getCornerCount(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("Corner.getCornerCount", params);
	}

	public CornerTbl getCornerObj(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (CornerTbl)getSqlMapClientTemplate().queryForObject("Corner.getCornerObj", params);
	}

	public Long insertCorner(CornerTbl cornerTbl) throws DaoRollbackException {
		return (Long)getSqlMapClientTemplate().insert("Corner.insertCorner", cornerTbl);
	}

	public void updateCorner(CornerTbl cornerTbl) throws DaoRollbackException {
		getSqlMapClientTemplate().update("Corner.updateCorner", cornerTbl);
	}

	public long getCornerMax(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("Corner.getCornerMax", params);
	}

	public long getCornerNewId() throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("Corner.getCornerNewId");
	}

	public void deleteCorner(Long masterId) throws DaoRollbackException {
		getSqlMapClientTemplate().delete("Corner.deleteCorner", masterId);
	}
	
	public void deleteCtCorner(Long ctId) throws DaoRollbackException {
		getSqlMapClientTemplate().delete("Corner.deleteCtCorner", ctId);
	}

}

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
import com.sbs.das.dto.PgmInfoTbl;

@Repository(value="pgmInfoDao")
public class PgmInfoDaoImpl extends SqlMapClientDaoSupport implements PgmInfoDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<PgmInfoTbl> findPgmInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("PgmInfo.findPgmInfo", params);
	}
	
	public PgmInfoTbl getPgmInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (PgmInfoTbl)getSqlMapClientTemplate().queryForObject("PgmInfo.getPgmInfo", params);
	}

	public void updatePgmInfo(PgmInfoTbl pgmInfoTbl)
			throws DaoRollbackException {
		// TODO Auto-generated method stub

	}

}

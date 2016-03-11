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
import com.sbs.das.dto.AnnotInfoTbl;

@Repository(value="annotInfoDao")
public class AnnotInfoDaoImpl extends SqlMapClientDaoSupport implements
		AnnotInfoDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public void insertAnnotInfo(AnnotInfoTbl annotInfoTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("AnnotInfo.insertAnnotInfo", annotInfoTbl);
	}

	@SuppressWarnings("unchecked")
	public List<AnnotInfoTbl> findRistList() throws DaoNonRollbackException {
		return  getSqlMapClientTemplate().queryForList("AnnotInfo.findRistList");	 
	}

	public AnnotInfoTbl getRistInfo(Map<String, Object> params) throws DaoNonRollbackException {
	 
		return (AnnotInfoTbl)getSqlMapClientTemplate().queryForObject("AnnotInfo.getRistInfo",params);
	}

	public void deleteAnnotInfo(Long ctId) throws DaoRollbackException {
		getSqlMapClientTemplate().insert("AnnotInfo.deleteAnnotInfo", ctId);
	}

}

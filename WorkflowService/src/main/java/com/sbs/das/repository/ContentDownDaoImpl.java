package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentDownTbl;

@Repository(value="contentDownDao")
public class ContentDownDaoImpl extends SqlMapClientDaoSupport implements ContentDownDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public long insertContentDown(ContentDownTbl contentDownTbl)
			throws DaoRollbackException {
		return (Long)getSqlMapClientTemplate().insert("ContentDown.insertContentDown", contentDownTbl);
	}

	public void updateContentDown(ContentDownTbl contentDownTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("ContentDown.updateContentDown", contentDownTbl);
	}

	public ContentDownTbl getContentDown(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return (ContentDownTbl)getSqlMapClientTemplate().queryForObject("ContentDown.getContentDownObj", params);
	}

	public Integer getContentDownCount(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("ContentDown.getContentDownCount", params);
	}

	public ContentDownTbl getBatchDown(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentDownTbl)getSqlMapClientTemplate().queryForObject("BatchDown.getBatchDownObj", params);
	}

	public void updateBatchDown(ContentDownTbl contentDownTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("BatchDown.updateBatchDown", contentDownTbl);
	}

	public void insertBatchDown(ContentDownTbl contentDownTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("BatchDown.insertBatchDown", contentDownTbl);
	}

	public void findContentDown(ContentDownTbl contentDownTbl)
			throws DaoRollbackException {
		// TODO Auto-generated method stub
		
	}

}

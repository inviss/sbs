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
import com.sbs.das.dto.ContentMapTbl;

@Repository(value="contentMapDao")
public class ContentMapDaoImpl extends SqlMapClientDaoSupport implements ContentMapDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public Integer getContentMapCount(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("ContentMap.getContentMapCount", params);
	}

	public void insertContentMap(ContentMapTbl contentMapTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("ContentMap.insertContentMap", contentMapTbl);
	}

	public void updateContentMap(ContentMapTbl contentMapTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("ContentMap.updateContentMap", contentMapTbl);
	}

	@SuppressWarnings("unchecked")
	public List<ContentMapTbl> findContentMap(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ContentMap.findContentMap", params);
	}

	public ContentMapTbl getContentMap(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentMapTbl)getSqlMapClientTemplate().queryForObject("ContentMap.getContentMapObj", params);
	}

}

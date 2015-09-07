package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentTbl;

@Repository(value="contentDao")
public class ContentDaoImpl extends SqlMapClientDaoSupport implements ContentDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	public long getContentNewId() throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("Content.getContentNewId");
	}

	public void insertContent(ContentTbl contentTbl)
	throws DaoRollbackException {
		getSqlMapClientTemplate().insert("Content.insertContent", contentTbl);
	}

	public void updateContent(ContentTbl contentTbl)
	throws DaoRollbackException {
		getSqlMapClientTemplate().update("Content.updateContent", contentTbl);
	}

	public ContentTbl getContent(Map<String, Object> params)
	throws DaoNonRollbackException {
		return (ContentTbl)getSqlMapClientTemplate().queryForObject("Content.getContentObj", params);
	}

	public Map<String, String> getMediaId(Map<String, String> param) throws DaoRollbackException {
		getSqlMapClientTemplate().queryForObject("Content.getMediaID", param);

		return param;
	}

	public ContentTbl getContentForInst(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return (ContentTbl)getSqlMapClientTemplate().queryForObject("Content.getContentForInstObj", params);
	}

	public Integer getContentCount(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("Content.getContentCount", params);
	}

}

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
import com.sbs.das.dto.ContentLocTbl;

@Repository(value="contentLocDao")
public class ContentLocDaoImpl extends SqlMapClientDaoSupport implements ContentLocDao {
	
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public void updateContentLoc(ContentLocTbl contentLocTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("ContentLoc.updateContentLoc", contentLocTbl);
	}

	@SuppressWarnings("unchecked")
	public ContentLocTbl getContentLoc(Map<String, Object> params) throws DaoNonRollbackException {
		List<ContentLocTbl> contentLocTbls = getSqlMapClientTemplate().queryForList("ContentLoc.getContentLoc", params);
		ContentLocTbl contentLocTbl = null;
		if(!contentLocTbls.isEmpty()) {
			contentLocTbl = (ContentLocTbl)contentLocTbls.get(0);
		}
		
		return contentLocTbl;
	}

	public Long insertContentLoc(ContentLocTbl contentLocTbl)
			throws DaoRollbackException {
		return (Long)getSqlMapClientTemplate().insert("ContentLoc.insertContentLoc", contentLocTbl);
	}

}

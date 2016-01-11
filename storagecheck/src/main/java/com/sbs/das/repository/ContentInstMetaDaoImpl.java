package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import kr.co.d2net.commons.exception.DaoNonRollbackException;
import kr.co.d2net.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentInstTbl;

@Repository(value="contentInstMetaDao")
public class ContentInstMetaDaoImpl extends SqlMapClientDaoSupport implements ContentInstMetaDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public void updateContentInst(ContentInstTbl contentInstTbl)
			throws DaoRollbackException {
		
		getSqlMapClientTemplate().update("ContentInst.updateContentInst", contentInstTbl);
	}

	public long getContentInstNewId() throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("ContentInst.getContentInstNewId");
	}

	public void insertContentInst(ContentInstTbl contentInstTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("ContentInst.insertContentInst", contentInstTbl);
	}

	@SuppressWarnings("unchecked")
	public List<ContentInstTbl> findContentInst(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("ContentInst.findContentInst", params);
	}

	public ContentInstTbl getContentInst(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (ContentInstTbl)getSqlMapClientTemplate().queryForObject("ContentInst.getContentInstObj", params);
	}

	public Integer getContentInstCount(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (Integer)getSqlMapClientTemplate().queryForObject("ContentInst.getContentInstCount", params);
	}
	
	public int callProceduer(Long cti_id)
			throws DaoNonRollbackException {
		
		getSqlMapClientTemplate().queryForObject("ContentInst.callProceduer",  new Long(cti_id));
		 return 1;
	}

	public Long getMasterIdOnly(Long ctiId) throws DaoNonRollbackException {
		return (Long)getSqlMapClientTemplate().queryForObject("ContentInst.getMasterIdOnly", ctiId);
	}


}

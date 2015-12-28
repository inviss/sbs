package com.sbs.das.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.xml.DeleteRequest;

@Repository(value="deleteContentDao")
public class DeleteContentDaoImpl extends SqlMapClientDaoSupport implements DeleteContentDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<DeleteRequest> findArchiveDeleteList(String coCd, String limitDay)
			throws DaoNonRollbackException {
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("coCd", coCd);
		params.put("limitDay", limitDay);
		
		return getSqlMapClientTemplate().queryForList("Scheduler.findArchCompDelete", params);
	}

	@SuppressWarnings("unchecked")
	public List<DeleteRequest> findDownloadDeleteList(Map<String, String> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("Scheduler.findDownCompDelete", params);
	}

	@SuppressWarnings("unchecked")
	public List<DeleteRequest> findScrappedList(String coCd, String limitDay)
			throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("coCd", coCd);
		params.put("limitDay", limitDay);
		return getSqlMapClientTemplate().queryForList("Scheduler.findScrappedDelete", params);
	}

	public DeleteRequest getUserDeleteConent(Long ctId) throws ServiceException {
		return (DeleteRequest)getSqlMapClientTemplate().queryForObject("Scheduler.getContentMeta", ctId);
	}
	
	@SuppressWarnings("unchecked")
	public List<DeleteRequest> findUserDeleteConent(Long ctId)
			throws ServiceException {
		return getSqlMapClientTemplate().queryForList("Scheduler.findContentMeta", ctId);
	}

	public List<DeleteRequest> updateDeleteComplete(Map<String, Long> params)
			throws ServiceException {
		return null;
	}

	public void insertKwKlog(Long masterId) throws ServiceException {
		getSqlMapClientTemplate().insert("Scheduler.insertKwKlog", String.valueOf(masterId));
	}
	
	public void deleteKwKlog(Long masterId) throws ServiceException {
		getSqlMapClientTemplate().insert("Scheduler.deleteKwKlog", String.valueOf(masterId));
	}

}

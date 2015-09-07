package com.sbs.das.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.MerHistTbl;

@Repository(value="merUnCompressDao")
public class MerUnCompressDaoImpl extends SqlMapClientDaoSupport implements MerUnCompressDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MerHistTbl> findNewJobs() throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", "W");
		return getSqlMapClientTemplate().queryForList("MerHist.findMerHist", params);
	}

	public MerHistTbl getMerJob(Long seq) throws ServiceException {
		return (MerHistTbl)getSqlMapClientTemplate().queryForObject("MerHist.getMerHist", seq);
	}

	public void insertMerJob(MerHistTbl merHisTbl) throws ServiceException {
		getSqlMapClientTemplate().insert("MerHist.insertMerHist", merHisTbl);
	}

	public void updateMerJob(MerHistTbl merHistTbl) throws ServiceException {
		getSqlMapClientTemplate().update("MerHist.updateMerHist", merHistTbl);
	}

}

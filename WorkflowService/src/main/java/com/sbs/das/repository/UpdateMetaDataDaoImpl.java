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
import com.sbs.das.dto.MasterTbl;

@Repository(value="updateMetaDataDao")
public class UpdateMetaDataDaoImpl extends SqlMapClientDaoSupport implements UpdateMetaDataDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public List<MasterTbl> findMetaData(String regDt) throws ServiceException {
		return getSqlMapClientTemplate().queryForList("UpdateMeta.findMetaData", regDt);
	}

	public Long getCtMeta(Long masterId) throws ServiceException {
		return (Long)getSqlMapClientTemplate().queryForObject("UpdateMeta.getCtMeta", masterId);
	}

	public void updateMetaData(Long masterId, Long ctId)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", masterId);
		params.put("ctId", ctId);
		getSqlMapClientTemplate().update("UpdateMeta.updateMetaData", params);
	}

}

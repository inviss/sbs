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
import com.sbs.das.dto.MetadatMstTbl;

@Repository(value="metadatMstDao")
public class MetadatMstDaoImpl extends SqlMapClientDaoSupport implements MetadatMstDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<MetadatMstTbl> findMetadataList(Map<String, Object> params)
			throws DaoNonRollbackException {
		if(params.containsKey("brdDd") && params.containsKey("brdBgnHms")) {
			return getSqlMapClientTemplate().queryForList("MetadatMst.findMetadatMst", params);
		}
		return null;
	}

	public void saveMetadata(MetadatMstTbl metadatMstTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("MetadatMst.updateMetadatMst", metadatMstTbl);
	}

	public MetadatMstTbl getMetadata(Map<String, Object> params)
			throws DaoNonRollbackException {
		return  (MetadatMstTbl)getSqlMapClientTemplate().queryForObject("MetadatMst.getMetadatMst", params);
	}

}

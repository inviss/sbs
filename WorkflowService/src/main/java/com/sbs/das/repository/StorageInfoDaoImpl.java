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
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.StorageInfoTbl;

@Repository(value="storageInfoDao")
public class StorageInfoDaoImpl extends SqlMapClientDaoSupport implements
StorageInfoDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	public void insertAnnotInfo(AnnotInfoTbl annotInfoTbl)
	throws DaoRollbackException {
		getSqlMapClientTemplate().insert("StorageInfo.insertStorageInfo", annotInfoTbl);
	}

	public void insertStorageInfo(StorageInfoTbl storageInfoTbl)
	throws DaoRollbackException {
		// TODO Auto-generated method stub

	}

	public void updateStorageInfo(StorageInfoTbl storageInfoTbl)
	throws DaoRollbackException {
		getSqlMapClientTemplate().update("StorageInfo.updateStorageInfo", storageInfoTbl);
	}

	@SuppressWarnings("unchecked")
	public List<StorageInfoTbl> findStorageInfo(Map<String, Object> params)
	throws DaoNonRollbackException {
		if(params == null) {
			params = new HashMap<String, Object>();
			params.put("wait", "true");
		}
		return getSqlMapClientTemplate().queryForList("StorageInfo.findStorageInfo", params);
	}

	public StorageInfoTbl getStorageInfo(Map<String, Object> params)
	throws DaoNonRollbackException {
		// TODO Auto-generated method stub
		return null;
	}

}

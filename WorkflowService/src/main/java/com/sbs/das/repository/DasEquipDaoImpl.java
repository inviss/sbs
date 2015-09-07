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
import com.sbs.das.dto.DasEquipTbl;

@Repository(value="equipDao")
public class DasEquipDaoImpl extends SqlMapClientDaoSupport implements
		DasEquipDao {
	
	
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<DasEquipTbl> findDasEquip(Map<String, Object> params) throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("DasEquip.findEquip", params);
	}

	public Integer getEquipCount(Map<String, Object> params) throws DaoNonRollbackException {
		
		return (Integer)getSqlMapClientTemplate().queryForObject("DasEquip.getEquipCount", params );
	}

	public DasEquipTbl getEquipObject(Map<String, Object> params)
	throws DaoNonRollbackException {
		return (DasEquipTbl)getSqlMapClientTemplate().queryForObject("DasEquip.getEquipObj", params );
	}

	public void updateDasEquip(DasEquipTbl dasEquipTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("DasEquip.updateEquip", dasEquipTbl);
	}

}

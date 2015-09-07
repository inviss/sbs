package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.CopyInfoTbl;

@Repository(value="copyInfoDao")
public class CopyInfoDaoImpl extends SqlMapClientDaoSupport implements CopyInfoDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public CopyInfoTbl getCopyInfoObj(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (CopyInfoTbl)getSqlMapClientTemplate().queryForObject("CopyInfo.getCopyInfoObj", params);
	}

}

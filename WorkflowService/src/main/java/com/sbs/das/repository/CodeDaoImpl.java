package com.sbs.das.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.CodeTbl;

@Repository(value="codeDao")
public class CodeDaoImpl extends SqlMapClientDaoSupport implements CodeDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	public CodeTbl getCodeObj(Map<String, Object> params)
			throws DaoNonRollbackException {
		CodeTbl codeTbl = (CodeTbl)getSqlMapClientTemplate().queryForObject("Code.getCodeObj", params);
		return codeTbl;
	}
	
}

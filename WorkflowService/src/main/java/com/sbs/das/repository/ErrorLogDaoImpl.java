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
import com.sbs.das.dto.ErrorLogTbl;
import com.sbs.das.dto.StorageInfoTbl;

@Repository(value="errorLongDao")
public class ErrorLogDaoImpl extends SqlMapClientDaoSupport implements
ErrorLogDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}


	public void insertErrorLog(ErrorLogTbl errorLogTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("ErrorLogInfo.insertErrorLogInfo", errorLogTbl);
	}

}

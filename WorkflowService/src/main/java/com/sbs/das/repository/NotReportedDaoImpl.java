package com.sbs.das.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.NotReportMsgTbl;

@Repository(value="notReportedDao")
public class NotReportedDaoImpl extends SqlMapClientDaoSupport implements NotReportedDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	public void insertNotReportedMsg(NotReportMsgTbl notReportMsgTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("NotReportMsg.insertNotReportMsg", notReportMsgTbl);
	}

}

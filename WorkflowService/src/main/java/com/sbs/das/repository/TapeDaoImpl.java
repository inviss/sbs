package com.sbs.das.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.ErpTapeTbl;

@Repository(value="tapeDao")
public class TapeDaoImpl extends SqlMapClientDaoSupport implements TapeDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient2") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public ErpTapeTbl getTapeObj(String tapeId) throws DaoNonRollbackException {
		ErpTapeTbl erpTapeTbl = null;
		
		List<ErpTapeTbl> itemTbls = getSqlMapClientTemplate().queryForList("ErpTape.findErpTape", tapeId);
		if(!itemTbls.isEmpty()) {
			erpTapeTbl = (ErpTapeTbl)itemTbls.get(0);
		}
		return erpTapeTbl;
	}

}

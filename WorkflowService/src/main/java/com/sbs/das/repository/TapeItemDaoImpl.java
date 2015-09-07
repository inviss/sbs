package com.sbs.das.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.ErpTapeItemTbl;

@Repository(value="tapeItemDao")
public class TapeItemDaoImpl extends SqlMapClientDaoSupport implements TapeItemDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient2") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public ErpTapeItemTbl getTapeItemObj(String tapeItemId)
			throws DaoNonRollbackException {
		ErpTapeItemTbl erpTapeItemTbl = null;
		
		List<ErpTapeItemTbl> itemTbls = getSqlMapClientTemplate().queryForList("ErpTapeItem.findErpTapeItem", tapeItemId);
		if(!itemTbls.isEmpty()) {
			erpTapeItemTbl = (ErpTapeItemTbl)itemTbls.get(0);
		}
		return erpTapeItemTbl;
	}

}

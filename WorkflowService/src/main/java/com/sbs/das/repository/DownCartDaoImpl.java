package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.DownCartTbl;

@Repository(value="downCartDao")
public class DownCartDaoImpl extends SqlMapClientDaoSupport implements DownCartDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<DownCartTbl> findDownCarts(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("DownCart.findDownCart", params);
	}

	public DownCartTbl getDownCart(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (DownCartTbl)getSqlMapClientTemplate().queryForObject("DownCart.getDownCart", params);
	}

}

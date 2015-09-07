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
import com.sbs.das.dto.CartContTbl;

@Repository(value="cartContDao")
public class CartContDaoImpl extends SqlMapClientDaoSupport implements CartContDao {

	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}
	
	@SuppressWarnings("unchecked")
	public List<CartContTbl> findCartConts(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("CartCont.findCartCont", params);
	}

	public CartContTbl getCartCont(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (CartContTbl)getSqlMapClientTemplate().queryForObject("CartCont.getCartContObj", params);
	}

	public void updateCartCont(CartContTbl cartContTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().update("CartCont.updateCartCont", cartContTbl);
	}

}

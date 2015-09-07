package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.CartContTbl;

public interface CartContDao {
	public List<CartContTbl> findCartConts(Map<String, Object> params) throws DaoNonRollbackException;
	
	public CartContTbl getCartCont(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void updateCartCont(CartContTbl cartContTbl) throws DaoRollbackException;
}

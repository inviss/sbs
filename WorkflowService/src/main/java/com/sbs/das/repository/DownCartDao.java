package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.dto.DownCartTbl;

public interface DownCartDao {
	public List<DownCartTbl> findDownCarts(Map<String, Object> params) throws DaoNonRollbackException;
	
	public DownCartTbl getDownCart(Map<String, Object> params) throws DaoNonRollbackException;
}

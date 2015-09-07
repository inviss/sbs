package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentLocTbl;

public interface ContentLocDao {
	
	public ContentLocTbl getContentLoc(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void updateContentLoc(ContentLocTbl contentLocTbl) throws DaoRollbackException;
	
	public Long insertContentLoc(ContentLocTbl contentLocTbl) throws DaoRollbackException;
}

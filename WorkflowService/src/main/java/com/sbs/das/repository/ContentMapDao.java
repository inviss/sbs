package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentMapTbl;

public interface ContentMapDao {

	public Integer getContentMapCount(Map<String, Object> params) throws DaoNonRollbackException;
	
	public Integer getContentGroupCount(Map<String, Object> params) throws DaoNonRollbackException;

	public void insertContentMap(ContentMapTbl contentMapTbl) throws DaoRollbackException;

	public void updateContentMap(ContentMapTbl contentMapTbl) throws DaoRollbackException;

	public List<ContentMapTbl> findContentMap(Map<String, Object> params) throws DaoNonRollbackException;
	
	public ContentMapTbl getContentMap(Map<String, Object> params) throws DaoNonRollbackException;
	
	public ContentMapTbl getContentGroupInfo(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void deleteContentMap(ContentMapTbl contentMapTbl) throws DaoRollbackException;
}

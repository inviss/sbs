package com.sbs.das.repository;

import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.ContentDownTbl;

public interface ContentDownDao {
	/**
	 * 아카이브 복원 요청을 등록.
	 * @param contentDownTbl
	 * @throws DaoRollbackException
	 */
	public long insertContentDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
	
	/**
	 * Diva에서 전달하는 복원 진행과정에 대한 상태정보를 저장한다.
	 * @param Map
	 * @throws DaoRollbackException
	 */
	public void updateContentDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
	
	public ContentDownTbl getContentDown(Map<String, Object> params) throws DaoNonRollbackException;
	
	public Integer getContentDownCount(Map<String, Object> params) throws DaoNonRollbackException;
	
	public ContentDownTbl getBatchDown(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void updateBatchDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
	
	public void insertBatchDown(ContentDownTbl contentDownTbl) throws DaoRollbackException;
}

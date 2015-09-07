package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.AnnotInfoTbl;

public interface AnnotInfoDao {
	public void insertAnnotInfo(AnnotInfoTbl annotInfoTbl) throws DaoRollbackException;
	
	//사용등급 벌크 작업용 작
	public List<AnnotInfoTbl> findRistList() throws DaoRollbackException;
	public AnnotInfoTbl getRistInfo(Map<String, Object> params) throws DaoRollbackException;
}

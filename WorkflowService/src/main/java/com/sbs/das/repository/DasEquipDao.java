package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.DasEquipTbl;

public interface DasEquipDao {
	public List<DasEquipTbl> findDasEquip(Map<String, Object> params) throws DaoNonRollbackException;
	public Integer getEquipCount(Map<String, Object> params) throws DaoNonRollbackException;
	public DasEquipTbl getEquipObject(Map<String, Object> params) throws DaoNonRollbackException;
	public void updateDasEquip(DasEquipTbl dasEquipTbl) throws DaoRollbackException;
}

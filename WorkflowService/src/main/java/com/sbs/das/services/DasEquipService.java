package com.sbs.das.services;

import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.DasEquipTbl;

@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.DEFAULT)
public interface DasEquipService {
	public List<DasEquipTbl> findDasEquip(String eqClfCd, Boolean wating)
	throws DaoNonRollbackException;
	
	public DasEquipTbl getDasEquip(Integer eqId, String eqPsCd, Boolean wating)
	throws DaoNonRollbackException;
	
	@Transactional
	public void updateDasEquip(DasEquipTbl dasEquipTbl)
	throws DaoRollbackException;
}

package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.DasEquipTbl;
import com.sbs.das.repository.DasEquipDao;

@Service(value="dasEquipService")
public class DasEquipServiceImpl implements DasEquipService {
	
	@Autowired
	private DasEquipDao equipDao;
	
	// 검색 parameter
	private Map<String, Object> params = new HashMap<String, Object>();

	public List<DasEquipTbl> findDasEquip(String eqClfCd, Boolean wating)
			throws DaoNonRollbackException {
		
		params.clear();
		params.put("dasEqClfCd", eqClfCd);
		params.put("wait", wating.booleanValue());
		
		return equipDao.findDasEquip(params);
	}

	public DasEquipTbl getDasEquip(Integer eqId, String eqPsCd, Boolean wating)
			throws DaoNonRollbackException {
		
		params.clear();
		params.put("dasEqId", eqId);
		params.put("dasEqPsCd", eqPsCd);
		params.put("wait", wating.booleanValue());
		
		return equipDao.getEquipObject(params);
	}

	public void updateDasEquip(DasEquipTbl dasEquipTbl)
			throws DaoRollbackException {
		equipDao.updateDasEquip(dasEquipTbl);
	}

}

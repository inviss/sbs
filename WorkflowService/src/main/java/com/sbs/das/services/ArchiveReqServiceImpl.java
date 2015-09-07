package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.CartContTbl;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentLocTbl;
import com.sbs.das.dto.CopyInfoTbl;
import com.sbs.das.dto.DownCartTbl;
import com.sbs.das.dto.ErrorLogTbl;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.repository.CartContDao;
import com.sbs.das.repository.CartContDao2;
import com.sbs.das.repository.ContentDownDao;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.repository.ContentLocDao;
import com.sbs.das.repository.CopyInfoDao;
import com.sbs.das.repository.DownCartDao;
import com.sbs.das.repository.ErrorLogDao;
import com.sbs.das.repository.MasterDao;

@Service(value="archiveReqService")
public class ArchiveReqServiceImpl implements ArchiveReqService {

	@Autowired
	private DownCartDao downCartDao;
	@Autowired
	private CartContDao cartContDao;
	@Autowired
	private ContentDownDao contentDownDao;
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private ContentLocDao contentLocDao;
	@Autowired
	private ContentInstMetaDao contentInstDao;
	@Autowired
	private CopyInfoDao copyInfoDao;

	@Autowired
	private ErrorLogDao errorLogDao;

	public List<DownCartTbl> findDownCarts(Long cartNo, Boolean wating)
	throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartNo", cartNo);
		params.put("wait", wating.booleanValue());

		return downCartDao.findDownCarts(params);
	}

	public DownCartTbl getDownCart(Long cartNo, Boolean wating)
	throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartNo", cartNo);
		params.put("wait", wating.booleanValue());

		return downCartDao.getDownCart(params);
	}

	public List<CartContTbl> findCartConts(Long cartNo, Integer cartSeq, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartNo", cartNo);
		params.put("cartSeq", cartSeq);
		params.put("wait", wating.booleanValue());

		return cartContDao.findCartConts(params);
	}

	public long insertContentDown(ContentDownTbl contentDownTbl)
	throws DaoRollbackException {
		return contentDownDao.insertContentDown(contentDownTbl);
	}

	public CartContTbl getCartCont(Long cartNo, Integer cartSeq, Boolean wating)
	throws DaoNonRollbackException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartNo", cartNo);
		params.put("cartSeq", cartSeq);
		params.put("wait", wating.booleanValue());

		return cartContDao.getCartCont(params);
	}

	public ContentDownTbl getContentDown(Long num, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("wait", wating.booleanValue());

		return contentDownDao.getContentDown(params);
	}
	
	public ContentDownTbl getContentDown(Long cartNo, Integer cartSeq,
			Boolean wating) throws DaoNonRollbackException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cartNo", cartNo);
		params.put("cartSeq", cartSeq);
		params.put("wait", wating.booleanValue());

		return contentDownDao.getContentDown(params);
	}
	
	public ContentDownTbl getContentDown(Map<String, Object> params) throws DaoNonRollbackException {
		
		params.put("wait", "true");

		return contentDownDao.getContentDown(params);
	}

	public MasterTbl getMaster(String tapeItemId, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tapeItemId", tapeItemId);
		params.put("wait", wating.booleanValue());

		MasterTbl masterTbl = null;
		List<MasterTbl> masterTbls = masterDao.findMaster(params);
		if(!masterTbls.isEmpty()) {
			masterTbl = masterTbls.get(0);
		}
		return masterTbl;
	}

	public long getMaxMaster(String mcuId, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("mcuid", mcuId);
		params.put("wait", wating.booleanValue());

		return masterDao.getMaxMaster(params);
	}

	public Long insertContentLoc(ContentLocTbl contentLocTbl)
	throws DaoRollbackException {
		return contentLocDao.insertContentLoc(contentLocTbl);
	}

	public void updateContentLoc(ContentLocTbl contentLocTbl)
	throws DaoRollbackException {
		contentLocDao.updateContentLoc(contentLocTbl);
	}

	public ContentLocTbl getContentLoc(String objName, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objName", objName);
		params.put("wait", wating.booleanValue());

		return contentLocDao.getContentLoc(params);
	}

	public ContentLocTbl getContentLoc(Long cti_id, Boolean wating)
	throws DaoNonRollbackException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctiId", cti_id);
		params.put("wait", wating.booleanValue());

		return contentLocDao.getContentLoc(params);
	}

	public void updateContentDown(ContentDownTbl contentDownTbl)
	throws DaoRollbackException {
		contentDownDao.updateContentDown(contentDownTbl);
	}

	public CopyInfoTbl getCopyInfoObj(String cmsPgmId)
	throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cmsPgmId", cmsPgmId);
		return copyInfoDao.getCopyInfoObj(params);
	}


	public long callProceduer(long cti_id)
	throws DaoRollbackException, DaoNonRollbackException {
		return contentInstDao.callProceduer(cti_id);
	}

	public MasterTbl getMaster(Map<String, Object> params)
	throws DaoNonRollbackException {

		MasterTbl masterTbl = null;
		List<MasterTbl> masterTbls = masterDao.findMaster(params);
		if(!masterTbls.isEmpty()) {
			masterTbl = masterTbls.get(0);
		}
		return masterTbl;
	}

	public void insertErrorLog(ErrorLogTbl errorLogTbl)
			throws ServiceException {
		errorLogDao.insertErrorLog(errorLogTbl);
		
	}

	public List<CartContTbl> findCartConts(Map<String, Object> params)
			throws DaoNonRollbackException {
		
		return cartContDao.findCartConts(params);
	}
	
	public void updateCartCont(CartContTbl cartContTbl)
			throws DaoRollbackException {
		cartContDao.updateCartCont(cartContTbl);
	}

	public String getCode(String objName) throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objName", objName);
		return masterDao.getCocd(params);
	}

}

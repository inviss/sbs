package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.CartContTbl;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentLocTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.repository.CartContDao;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentDownDao;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.repository.ContentLocDao;
import com.sbs.das.repository.MasterDao;

@Service(value="archiveStatusService")
public class ArchiveStatusServiceImpl implements ArchiveStatusService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ContentLocDao contentLocDao;
	@Autowired
	private ContentInstMetaDao contentInstDao;
	@Autowired
	private ContentDownDao contentDownDao;
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private CartContDao cartContDao;
	@Autowired
	private ContentDao contentDao;

	@Autowired
	private ContentInstMetaDao contentInstMetaDao;

	public void updateStatus(ContentLocTbl contentLocTbl)
	throws ServiceException {
		contentLocTbl.setUseYn("Y");
		contentLocDao.updateContentLoc(contentLocTbl);
	}

	public void backupStatus(ContentLocTbl contentLocTbl)
	throws ServiceException {

		contentLocTbl.setUseYn("Y");
		contentLocDao.updateContentLoc(contentLocTbl);

		ContentInstTbl contentInstTbl = new ContentInstTbl();

		contentInstTbl.setCtiId(Long.parseLong(contentLocTbl.getObjName().substring(3)));
		contentInstTbl.setEtc("");
		contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmm"));
		contentInstTbl.setModrid("DIVA");
		contentInstMetaDao.updateContentInst(contentInstTbl);
	}

	public void restoreStatus(ContentDownTbl contentDownTbl)
	throws ServiceException {
		contentDownDao.updateContentDown(contentDownTbl);
	}

	public void updateCompleteMaster(ContentLocTbl contentLocTbl)
	throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ctiId", contentLocTbl.getCtiId());
		params.put("wait", true);
		ContentTbl contentTbl = contentDao.getContentForInst(params);

		if(contentLocTbl.getJobStatus().equals("C") && StringUtils.isNotBlank(contentTbl.getCtTyp()) && contentTbl.getCtTyp().equals("003")) {
			params.clear();
			params.put("ctiId", contentLocTbl.getCtiId());
			params.put("archRegDd", contentLocTbl.getUpdtDtm());

			if(logger.isDebugEnabled()) {
				logger.debug("ctiId : "+contentLocTbl.getCtiId());
				logger.debug("archRegDd : "+contentLocTbl.getUpdtDtm());
			}
			masterDao.updateArchiveComplete(params);
		}

		if(logger.isDebugEnabled()) {
			logger.debug("updateArchiveCompleted success!");
		}

		// 기존 상태값은 무조건 'Y'로 주고 있었음. 에러라면 에러상태로 변경되어야함
		// 2012-11-15
		ContentInstTbl contentInstTbl = new ContentInstTbl();
		contentInstTbl.setArchSteYn(contentLocTbl.getJobStatus().equals("C") ? "Y" : "N");
		contentInstTbl.setDtlYn(contentLocTbl.getJobStatus().equals("C") ? "Y" : "N");
		contentInstTbl.setModrid("DIVA");
		contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentInstTbl.setCtiId(contentLocTbl.getCtiId());
		contentInstTbl.setOutSystemId(contentLocTbl.getObjName());

		contentInstMetaDao.updateContentInst(contentInstTbl);
		if(logger.isDebugEnabled()) {
			logger.debug("updateContnetInst success!");
		}
	}

	public void getContentLoc(String objName, Boolean wating)
	throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objName", objName);
		params.put("wait", wating.booleanValue());

		contentLocDao.getContentLoc(params);
	}

	public void updateStatusCartCont(ContentDownTbl contentDownTbl)
	throws ServiceException {

		CartContTbl cartContTbl = new CartContTbl();
		cartContTbl.setCartNo(contentDownTbl.getCartNo());
		cartContTbl.setCartSeq(contentDownTbl.getCartSeq());
		cartContTbl.setModrid("workflow");
		cartContTbl.setModDt(contentDownTbl.getUpdtDtm());
		cartContTbl.setDownDt(Utility.getTimestamp("yyyyMMddHHmmss"));

		if(contentDownTbl.getStatus().equals("250")) {
			cartContTbl.setDownStat("007");
		} else if(contentDownTbl.getStatus().equals("69")) {
			cartContTbl.setDownStat("008");
		}

		cartContDao.updateCartCont(cartContTbl);
	}

	public ContentDownTbl getConentDown(Long num, Boolean wating)
	throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("wait", wating.booleanValue());

		return contentDownDao.getContentDown(params);
	}

	public void updateContentInst(ContentInstTbl ContentInstTbl)
			throws ServiceException {
		ContentInstTbl.setEtc("");
		contentInstDao.updateContentInst(ContentInstTbl);
		
	}

	public ContentDownTbl getBatchDown(String objName, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("objName", objName);
		params.put("wait", wating.booleanValue());
		
		return contentDownDao.getBatchDown(params);
	}
	
	public ContentDownTbl getBatchDown(Long num, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num", num);
		params.put("wait", wating.booleanValue());
		
		return contentDownDao.getBatchDown(params);
	}

	public void updateBatchDown(ContentDownTbl contentDownTbl)
			throws ServiceException {
		contentDownDao.updateBatchDown(contentDownTbl);
	}

	public void insertBatchDown(ContentDownTbl contentDownTbl)
			throws ServiceException {
		contentDownDao.insertBatchDown(contentDownTbl);
	}

	public List<ContentDownTbl> findContentDown(ContentDownTbl contentDownTbl)
			throws ServiceException {
		return null;
	}


}

package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.AttachTbl;
import com.sbs.das.dto.CodeTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.MasterTbl;
import com.sbs.das.dto.MediaTapeInfoTbl;
import com.sbs.das.dto.NotReportMsgTbl;
import com.sbs.das.dto.TimeRistSetTbl;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.repository.AnnotInfoDao;
import com.sbs.das.repository.AttachDao;
import com.sbs.das.repository.CodeDao;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.repository.ContentMapDao;
import com.sbs.das.repository.CornerDao;
import com.sbs.das.repository.MasterDao;
import com.sbs.das.repository.MediaTapeInfoDao;
import com.sbs.das.repository.NotReportedDao;
import com.sbs.das.repository.TimeRistSetDao;

@Service(value="addClipService")
public class AddClipForTapeServiceImpl implements AddClipForTapeService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CodeDao codeDao;
	@Autowired
	private NotReportedDao notReportedDao;
	@Autowired
	private MasterDao masterDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private CornerDao cornerDao;
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private ContentMapDao contentMapDao;
	@Autowired
	private AnnotInfoDao annotInfoDao;
	@Autowired
	private MediaTapeInfoDao mediaTapeInfoDao;
	@Autowired
	private TimeRistSetDao timeRistSetDao;
	@Autowired
	private AttachDao attachDao;
	
	public CodeTbl getCodeObj(String clfCd, String sclCd, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clfCd", clfCd);
		params.put("sclCd", sclCd);
		params.put("wating", wating);
		
		
		return codeDao.getCodeObj(params);
	}

	public void insertNotReportedMsg(NotReportMsgTbl notReportMsgTbl)
			throws ServiceException {
		notReportedDao.insertNotReportedMsg(notReportMsgTbl);
	}

	public Long getMasterNewId() throws ServiceException {
		return masterDao.getMasterNewId();
	}

	public Long getContentNewId() throws ServiceException {
		return contentDao.getContentNewId();
	}

	public Integer getCornerCount(Das das, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", das.getDbTable().getMaster().getMasterId());
		params.put("wating", wating);
		
		return cornerDao.getCornerCount(params);
	}

	public CodeTbl getCodeObj(Map<String, Object> params, Boolean wating)
			throws ServiceException {
		
		params.put("wating", wating);
		
		return codeDao.getCodeObj(params);
	}

	public Long getCornerMax(Das das, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", das.getDbTable().getMaster().getMasterId());
		params.put("cnTypeCd", "003");
		params.put("wating", wating);
		
		return cornerDao.getCornerMax(params);
	}

	public Long getCornerNewId() throws ServiceException {
		return cornerDao.getCornerNewId();
	}

	public CornerTbl getCornerObj(Long cnId, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnId", cnId);
		params.put("wating", wating);
		
		return cornerDao.getCornerObj(params);
	}

	public Long getContentInstNewId() throws ServiceException {
		return contentInstMetaDao.getContentInstNewId();
	}

	public Integer getContentMapCount(ContentMapTbl contentMapTbl, Boolean wating)
			throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", contentMapTbl.getMasterId());
		params.put("cnId", contentMapTbl.getCnId());
		params.put("ctId", contentMapTbl.getCtId());
		params.put("wating", wating);
		
		return contentMapDao.getContentMapCount(params);
	}

	public void saveAddClipInfo(Map<String, Object> params)
			throws ServiceException {
		if(params.isEmpty()) {
			throw new ServiceException("등록할 메타정보가 없습니다.");
		}
		
		// Master Metadata Insert
		if(params.containsKey("master")) {
			if(logger.isInfoEnabled()) {
				logger.info("Master MetaData Insert stating...");
			}
			MasterTbl masterTbl = (MasterTbl)params.get("master");
			if(masterTbl.isAddClip())
				masterDao.insertMaster(masterTbl);
			else
				masterDao.updateMaster(masterTbl);
		}
		
		// Content Metadata Insert
		if(params.containsKey("content")) {
			if(logger.isInfoEnabled()) {
				logger.info("Content MetaData Insert stating...");
			}
			ContentTbl contentTbl = (ContentTbl)params.get("content");
			if(contentTbl.isAddClip()) {
				// DAS용 media_id 발급
				Map<String, String> param = new HashMap<String, String>();
				param = contentDao.getMediaId(param);
				contentTbl.setMediaId(param.get("mediaId"));
				
				contentDao.insertContent(contentTbl);
			} else{
				logger.info("Content MetaData Insert stating..." + contentTbl.getModrid());
				contentDao.updateContent(contentTbl);
			}
		}
		
		// Corner Metadata Insert
		if(params.containsKey("corner")) {
			if(logger.isInfoEnabled()) {
				logger.info("Corner MetaData Insert stating...");
			}
			CornerTbl cornerTbl = (CornerTbl)params.get("corner");
			if(cornerTbl.isAddClip())
				cornerDao.insertCorner(cornerTbl);
			else
				cornerDao.updateCorner(cornerTbl);
		}
		
		// Content Instance Metadata Insert
		if(params.containsKey("contentInst")) {
			if(logger.isInfoEnabled()) {
				logger.info("Content Instance MetaData Insert stating...");
			}
			ContentInstTbl contentInstTbl = (ContentInstTbl)params.get("contentInst");
			if(contentInstTbl.isAddClip())
				contentInstMetaDao.insertContentInst(contentInstTbl);
			else
				contentInstMetaDao.updateContentInst(contentInstTbl);
		}
		
		// Content Map Metadata Insert
		if(params.containsKey("contentMap")) {
			if(logger.isInfoEnabled()) {
				logger.info("Content Map MetaData Insert stating...");
			}
			ContentMapTbl contentMapTbl = (ContentMapTbl)params.get("contentMap");
			if(contentMapTbl.isAddClip())
				contentMapDao.insertContentMap(contentMapTbl);
		}
		
		// AnnotInfo Metadata Insert
		if(params.containsKey("annotInfo")) {
			if(logger.isInfoEnabled()) {
				logger.info("AnnotInfo MetaData Insert stating...");
			}
			AnnotInfoTbl annotInfoTbl = (AnnotInfoTbl)params.get("annotInfo");
			if(annotInfoTbl.isAddClip()) {
				annotInfoDao.insertAnnotInfo(annotInfoTbl);
			}
		}
		
		/* 2014.10.14 자막관련 기능 추가 */
		if(params.containsKey("attach")) {
			if(logger.isInfoEnabled()) {
				logger.info("Attach MetaData Insert stating...");
			}
			AttachTbl attachTbl = (AttachTbl)params.get("attach");
			if(attachTbl != null) {
				attachDao.insertAttach(attachTbl);
			}
		}
	}

	public MasterTbl getMasterObj(Long masterId) throws ServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", masterId);
		params.put("wait", true);
		
		return masterDao.getMaster(params);
	}

	public MediaTapeInfoTbl getMediaTapeInfo(Map<String, Object> params)
			throws ServiceException {
		return mediaTapeInfoDao.getMediaTapeInfo(params);
	}

	public void insertMediaTapeInfo(MediaTapeInfoTbl mediaTapeInfoTbl)
			throws ServiceException {
		mediaTapeInfoDao.insertMediaTapeInfo(mediaTapeInfoTbl);
	}

	public void insertAllMediaTapeInfo(List<MediaTapeInfoTbl> mediaTapeInfoTbls)
			throws ServiceException {
		mediaTapeInfoDao.insertAllMediaTapeInfo(mediaTapeInfoTbls);
	}

	public MasterTbl getMaster(Map<String, Object> params)
			throws ServiceException {
		params.put("wait", true);
		
		return masterDao.getMaster(params);
	}

	public TimeRistSetTbl getTimeRistSet(Integer week, String nowtime)
			throws ServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nowtime", nowtime);
		params.put("week", week);
		params.put("wait", "true");
		return timeRistSetDao.getTimeRistSet(params);
	}
 

}

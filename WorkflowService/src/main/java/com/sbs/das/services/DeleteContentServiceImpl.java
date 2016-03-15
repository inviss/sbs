package com.sbs.das.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.ContentDownTbl;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.xml.DeleteRequest;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentDownDao;
import com.sbs.das.repository.ContentInstMetaDao;
import com.sbs.das.repository.ContentMapDao;
import com.sbs.das.repository.DeleteContentDao;

@Service(value="deleteContentService")
public class DeleteContentServiceImpl implements DeleteContentService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeleteContentDao deleteContentDao;
	@Autowired
	private ContentDao contentDao;   
	@Autowired
	private ContentMapDao contentMapDao;
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private ContentDownDao contentDownDao;
	@Autowired
	private MessageSource messageSource;

	public List<DeleteRequest> findArchiveDeleteList(String coCd, String limitDay)
	throws ServiceException {
		if(logger.isDebugEnabled()) {
			logger.debug("limitDay   " + limitDay);
		}
		if(StringUtils.isNotBlank(limitDay)) {
			if(limitDay.length() == 8) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
					Date date = formatter.parse(limitDay);
					
					Calendar calendar = Calendar.getInstance();	
					calendar.setTime(date);
					
					limitDay = formatter.format(calendar.getTime())+"000000";
				} catch (ParseException e) {
					throw new ServiceException("Request limitDay parsing error", e);
				}	
			}
		} else {
			throw new ServiceException("Request limitDay null");
		}

		return deleteContentDao.findArchiveDeleteList(coCd, limitDay);
	}

	public List<DeleteRequest> findDownloadDeleteList(String coCd, String limitDay)
	throws ServiceException {
		
		Map<String, String> params = new HashMap<String, String>();
		
		String addLimitDay ="";
		if(StringUtils.isNotBlank(limitDay)) {
			if(limitDay.length() == 8) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");
					Date date = formatter.parse(limitDay);
					
					Calendar calendar = Calendar.getInstance();	
					calendar.setTime(date);
					
					limitDay = formatter.format(calendar.getTime())+"000000";
					
					String maxDay = "0";
					if(coCd.equals("S"))
						maxDay = messageSource.getMessage("download.expired.day", null, Locale.KOREA);
					else
						maxDay = messageSource.getMessage("net.download.expired.day", null, Locale.KOREA);
					
					calendar.add(Calendar.DAY_OF_MONTH, -Integer.valueOf(maxDay));

					addLimitDay = formatter.format(calendar.getTime())+"000000";
					
					if(logger.isDebugEnabled()) {
						logger.debug("limitDay: " + limitDay+", addLimitDay: "+addLimitDay);
					}
				} catch (ParseException e) {
					throw new ServiceException("Request limitDay parsing error", e);
				}	
			}
		} else {
			throw new ServiceException("Request limitDay null");
		}

		params.put("coCd", coCd);
		params.put("limitDay", limitDay);
		params.put("addLimitDay", addLimitDay);

		return deleteContentDao.findDownloadDeleteList(params);
	}

	public List<DeleteRequest> findContentScrappedDeleteList(String coCd, String limitDay)
	throws ServiceException {
		if(logger.isDebugEnabled()) {
			logger.debug("limitDay   " + limitDay);
		}
		if(StringUtils.isNotBlank(limitDay)) {
			if(limitDay.length() == 8) {
				try {
					SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMdd");

					Date date = formatter.parse(limitDay);
					
					Calendar calendar = Calendar.getInstance();	
					calendar.setTime(date);
					
					limitDay = formatter.format(calendar.getTime());
				} catch (ParseException e) {
					throw new ServiceException("Request limitDay parsing error", e);
				}	
			}
		} else {
			throw new ServiceException("Request limitDay null");
		}
		
		return deleteContentDao.findScrappedList(coCd, limitDay);
	}

	public DeleteRequest getUserDeleteConent(Long ctId)
	throws ServiceException {
		return deleteContentDao.getUserDeleteConent(ctId);
	}

	public void updateDeleteComplete(Long ctId, String limitDay)
	throws ServiceException {

		ContentTbl contentTbl = new ContentTbl();
		contentTbl.setCtId(ctId);
		contentTbl.setDelDd(Utility.getDate("yyyyMMdd"));
		contentTbl.setDelYn("Y");
		contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentTbl.setModrid("workflow");

		contentDao.updateContent(contentTbl);

		ContentMapTbl contentMapTbl = new ContentMapTbl();
		contentMapTbl.setModrid("workflow");
		contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentMapTbl.setCtId(ctId);
		contentMapTbl.setDelDd(limitDay);
		contentMapTbl.setDelYn("Y");

		contentMapDao.updateContentMap(contentMapTbl);
	}

	public void updateDeleteCtMap(Long ctId) throws ServiceException {
		ContentMapTbl contentMapTbl = new ContentMapTbl();
		contentMapTbl.setCtId(ctId);
		contentMapTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentMapTbl.setModrid("workflow");
		contentMapTbl.setDelYn("Y");
		contentMapTbl.setDelDd(Utility.getDate("yyyyMMdd"));

		contentMapDao.updateContentMap(contentMapTbl);
	}

	public void updateContentPathBlank(Long ctiId) throws ServiceException {
		ContentInstTbl contentInstTbl = new ContentInstTbl();
		contentInstTbl.setFlPath("");
		contentInstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentInstTbl.setModrid("workflow");
		contentInstTbl.setCtiId(ctiId);

		contentInstMetaDao.updateContentInst(contentInstTbl);
		
		// 2013.03.11 영상 삭제후 검색엔진 VIEW 테이블에 정보등록
		// 검색엔진에 masterId 업데이트(DTL)
		ContentInstTbl contentInstTbl2 = contentInstMetaDao.getMasterObj(ctiId);
		if(logger.isDebugEnabled()) {
			logger.debug("masterId: "+contentInstTbl2.getMasterId()+", use_yn: "+contentInstTbl2.getUseYn());
		}
		insertKwKlog(contentInstTbl2.getMasterId());
		/*
		if(contentInstTbl2.getMasterId() != null) {
			if(StringUtils.defaultIfBlank(contentInstTbl2.getUseYn(), "Y").equals("N"))
				deleteKwKlog(contentInstTbl2.getMasterId());
			else
				insertKwKlog(contentInstTbl2.getMasterId());
		}
		*/
	}

	public void updateDeleteCtDown(Long cartNo, Integer cartSeq) throws ServiceException {

		ContentDownTbl contentDownTbl = new ContentDownTbl();

		contentDownTbl.setCartNo(cartNo);
		contentDownTbl.setCartSeq(cartSeq);
		contentDownTbl.setUpdtDtm(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentDownTbl.setUpdtUser("workflow");
		contentDownTbl.setDelDd(Utility.getDate("yyyyMMdd"));

		contentDownDao.updateContentDown(contentDownTbl);
	}

	
	public void updateContentMediaIdBlank(Long ctId) throws ServiceException {
		
		ContentTbl contentTbl = new ContentTbl();
		contentTbl.setMediaId("delete");
		contentTbl.setModrid("workflow");
		contentTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		contentTbl.setCtId(ctId);
		contentDao.updateContent(contentTbl);
		
	}
	
	public void insertKwKlog(Long masterId) throws ServiceException {
		deleteContentDao.insertKwKlog(masterId);
	}

	public void deleteKwKlog(Long masterId) throws ServiceException {
		deleteContentDao.deleteKwKlog(masterId);
	}

}

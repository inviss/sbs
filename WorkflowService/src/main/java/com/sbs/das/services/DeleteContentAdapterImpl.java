package com.sbs.das.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ApplicationException;
import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.MessageDeleteListener;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.xml.DeleteRequest;

@Service(value="deleteContentAdapter")
public class DeleteContentAdapterImpl implements DeleteContentAdapter {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DeleteContentService deleteContentService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private MessageDeleteListener messageDeleteListener;


	public void archiveExpiredDelete(String coCd, String limitDay) throws ServiceException {
		try {

			Map<String, String> coCds = new HashMap<String, String>();

			String sLimitDay = "";
			String mLimitDay = "";
			if(StringUtils.isBlank(coCd)) {
				sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("storage.archived.day", null, Locale.KOREA))+1)) : limitDay);
				mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.storage.archived.day", null, Locale.KOREA))+1)) : limitDay);
				coCds.put("S", sLimitDay);
				coCds.put("M", mLimitDay);
			} else {
				if(coCd.equals("S")) {
					sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("storage.archived.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("S", sLimitDay);
				} else {
					mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.storage.archived.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("M", mLimitDay);
				}
			}

			Iterator<String> it = coCds.keySet().iterator();
			while(it.hasNext()) {
				String co = it.next();
				String limit = coCds.get(co);

				List<DeleteRequest> storageExpirList = deleteContentService.findArchiveDeleteList(co, limit);
				if(logger.isDebugEnabled()) {
					logger.debug("archive size       : "+storageExpirList.size());
				}
				if(!storageExpirList.isEmpty()) {
					for(DeleteRequest deleteRequest : storageExpirList) {
						if(logger.isDebugEnabled()) {
							logger.debug("ct_id       : "+deleteRequest.getCtId());
							logger.debug("high_cti_id : "+deleteRequest.getHighCtiId());
						}

						if(StringUtils.isNotBlank(deleteRequest.getHighPath())) {
							deleteRequest.setReqDd(limitDay);
							deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
							messageDeleteListener.mput(deleteRequest);
						}
					}
				}
			}
		} catch (ApplicationException e) {
			logger.error("archiveExpiredDelete error - ", e);
		}
	}

	public void downloadExpiredDelete(String coCd, String limitDay) throws ServiceException {
		try {
			Map<String, String> coCds = new HashMap<String, String>();

			String sLimitDay = "";
			String mLimitDay = "";
			if(StringUtils.isBlank(coCd)) {
				sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("storage.restored.day", null, Locale.KOREA))+1)) : limitDay);
				mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.storage.restored.day", null, Locale.KOREA))+1)) : limitDay);
				coCds.put("S", sLimitDay);
				coCds.put("M", mLimitDay);
			} else {
				if(coCd.equals("S")) {
					sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("storage.restored.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("S", sLimitDay);
				} else {
					mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.storage.restored.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("M", mLimitDay);
				}
			}

			Iterator<String> it = coCds.keySet().iterator();
			while(it.hasNext()) {
				String co = it.next();
				String limit = coCds.get(co);

				List<DeleteRequest> storageExpirList = deleteContentService.findDownloadDeleteList(co, limit);
				if(logger.isDebugEnabled()) {
					logger.debug("archive size       : "+storageExpirList.size());
				}
				if(!storageExpirList.isEmpty()) {
					for(DeleteRequest deleteRequest : storageExpirList) {
						if(logger.isDebugEnabled()) {
							logger.debug("cart_no     : "+deleteRequest.getCartNo());
							logger.debug("cart_seq    : "+deleteRequest.getCartSeq());
							logger.debug("high_cti_id : "+deleteRequest.getHighCtiId());
						}

						if(StringUtils.isNotBlank(deleteRequest.getHighPath())) {
							deleteRequest.setReqDd(limitDay);
							deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
							messageDeleteListener.mput(deleteRequest);
						}
					}
				}
			}

		} catch (ApplicationException e) {
			logger.error("downloadExpiredDelete error - ", e);
		}
	}

	public void scrappedDelete(String coCd, String limitDay)
	throws ServiceException {
		try {
			Map<String, String> coCds = new HashMap<String, String>();

			String sLimitDay = "";
			String mLimitDay = "";
			if(StringUtils.isBlank(coCd)) {
				sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("content.scrapped.day", null, Locale.KOREA))+1)) : limitDay);
				mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.content.scrapped.day", null, Locale.KOREA))+1)) : limitDay);
				coCds.put("S", sLimitDay);
				coCds.put("M", mLimitDay);
			} else {
				if(coCd.equals("S")) {
					sLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("content.scrapped.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("S", sLimitDay);
				} else {
					mLimitDay = (StringUtils.isBlank(limitDay) ? Utility.getDate(-(Integer.valueOf(messageSource.getMessage("net.content.scrapped.day", null, Locale.KOREA))+1)) : limitDay);
					coCds.put("M", mLimitDay);
				}
			}
			
			Iterator<String> it = coCds.keySet().iterator();
			while(it.hasNext()) {
				String co = it.next();
				String limit = coCds.get(co);

				List<DeleteRequest> storageExpirList = deleteContentService.findContentScrappedDeleteList(co, limit);
				if(logger.isDebugEnabled()) {
					logger.debug("Contents size       : "+storageExpirList.size());
				}
				if(!storageExpirList.isEmpty()) {
					for(DeleteRequest deleteRequest : storageExpirList) {
						if(logger.isDebugEnabled()) {
							logger.debug("ct_id: "+deleteRequest.getCtId());
							logger.debug("m1-m2: "+(deleteRequest.getM1Cnt() - deleteRequest.getM2Cnt()));
						}
						
						/*
						 * 삭제 요청된 CT_ID가 contents_mapp_tbl에서 다른 회차(master_id)에 사용된 기록이 없다면
						 * mxf, wmv, catalog를 모두 삭제하고 다른 master에서 사용중이라면 0보다 크다면
						 * contents_mapp_tbl에서 del_yn을 'Y'로 update한다. 
						 */
						if((deleteRequest.getM1Cnt() - deleteRequest.getM2Cnt()) <= 0) {
							// content instance의 정보를 조회한다.
							deleteRequest = deleteContentService.getUserDeleteConent(deleteRequest.getCtId());
							if(deleteRequest != null && deleteRequest.getCtId() != null) {
								deleteRequest.setReqGb("F");
								deleteRequest.setReqDd(limitDay);
								deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
								messageDeleteListener.mput(deleteRequest);
								if(logger.isDebugEnabled()) {
									logger.debug("all delete: "+deleteRequest.getCtId());
								}
							}
						} else {
							deleteContentService.updateDeleteCtMap(deleteRequest.getCtId());
							if(logger.isDebugEnabled()) {
								logger.debug("map update: "+deleteRequest.getCtId());
							}
						}
					}
				}
			}
			
		} catch (ApplicationException e) {
			logger.error("userContentExpiredDelete error - ", e);
		}
	}

	public void userRequestDelete(List<DeleteRequest> deleteRequests) throws ServiceException {
		try {
			if(!deleteRequests.isEmpty()) {
				for(DeleteRequest deleteRequest : deleteRequests) {
					if(logger.isDebugEnabled()) {
						logger.debug("ct_id: "+deleteRequest.getCtId());
					}

					// content instance의 정보를 조회한다.
					deleteRequest = deleteContentService.getUserDeleteConent(deleteRequest.getCtId());
					if(deleteRequest != null && deleteRequest.getCtId() != null) {
						deleteRequest.setReqGb("U");
						deleteRequest.setReqDd(Utility.getTimestamp("yyyyMMdd"));
						deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
						messageDeleteListener.mput(deleteRequest);
						if(logger.isDebugEnabled()) {
							logger.debug("all delete: "+deleteRequest.getCtId());
						}
					}
				}
			}
		} catch (ApplicationException e) {
			logger.error("userRequestDelete error - ", e);
		}
	}

	public void pdsRequestDelete(List<DeleteRequest> deleteRequests) throws ServiceException {
		try {
			if(!deleteRequests.isEmpty()) {
				for(DeleteRequest deleteRequest : deleteRequests) {
					if(logger.isDebugEnabled()) {
						logger.debug("ct_id: "+deleteRequest.getCtId());
					}

					// content instance의 정보를 조회한다.
					deleteRequest = deleteContentService.getUserDeleteConent(deleteRequest.getCtId());
					if(deleteRequest != null && deleteRequest.getCtId() != null) {
						deleteRequest.setReqGb("F");
						deleteRequest.setReqDd(Utility.getTimestamp("yyyyMMdd"));
						deleteRequest.setReqDt(Utility.getTimestamp("yyyyMMddHHmmssSSSSS"));
						messageDeleteListener.mput(deleteRequest);
						if(logger.isDebugEnabled()) {
							logger.debug("all delete: "+deleteRequest.getCtId());
						}
					}
				}
			}
		} catch (ApplicationException e) {
			logger.error("pdsRequestDelete error - ", e);
		}
	}

}

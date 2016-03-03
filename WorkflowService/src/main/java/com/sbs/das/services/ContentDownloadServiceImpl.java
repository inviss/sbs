package com.sbs.das.services;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.system.DasCmsConnector;
import com.sbs.das.commons.system.XmlStream;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ops.CartContent;
import com.sbs.das.dto.ops.DownCart;
import com.sbs.das.dto.xml.Das;
import com.sbs.das.repository.ContentInstMetaDao;

@Service(value="contentDownloadService")
public class ContentDownloadServiceImpl implements ContentDownloadService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ContentInstMetaDao contentInstMetaDao;
	@Autowired
	private DasCmsConnector cmsConnector;
	@Autowired
	private XmlStream xmlStream;
	
	public ContentInstTbl getContentInstObj(Long masterId) throws ServiceException {
		return contentInstMetaDao.getContentInst(masterId);
	}

	public long requestDownload(DownCart downCart) throws ServiceException {
		Das das = new Das();
		
		try {
			Long ctId = downCart.getCtId();
			Long ctiId = downCart.getCtiId();
			Long masterId = downCart.getMasterId();
			
			das.setDownCart(downCart);		
			String downCartXml = xmlStream.toXML(das);
			if(logger.isDebugEnabled()) {
				logger.debug("insertDownCart request: "+downCartXml);
			}
			String rtnXML = cmsConnector.insertDownCart(downCartXml);
			if(logger.isDebugEnabled()) {
				logger.debug("insertDownCart response: "+rtnXML);
			}
			
			if(StringUtils.isNotBlank(rtnXML)) {
				downCart = (DownCart)xmlStream.fromXML(rtnXML);
				
				CartContent cartContent = new CartContent();
				cartContent.setCartNo(das.getDownCart().getCartNo());
				cartContent.setCartSeq(1);
				cartContent.setCtId(ctId);
				cartContent.setCtiId(ctiId);
				cartContent.setMasterId(masterId);
				cartContent.setDownTyp("F");
				cartContent.setSom("");
				cartContent.setEom("");
				cartContent.setsFrame(0L);
				cartContent.setDownStat("001");
				cartContent.setRegrid(downCart.getRegrid());
				cartContent.setReqCont("");
				
				das.setCartContent(cartContent);
				
				String cartContXml = xmlStream.toXML(das);
				if(logger.isDebugEnabled()) {
					logger.debug("insertCartCont request: "+cartContXml);
				}
				String rtnCartXML = cmsConnector.insertCartCont(cartContXml);
				if(logger.isDebugEnabled()) {
					logger.debug("insertCartCont response: "+rtnCartXML);
				}
				if(StringUtils.isNotBlank(rtnCartXML)) {
					das = (Das)xmlStream.fromXML(rtnCartXML);
					
					downCart = new DownCart();
					downCart.setDownSubj("");
					downCart.setCellname(""); 								// 폴더ID
					downCart.setDownGubun("008"); 							// ifcms 구분자, PDS(001), NDS(002), 데정팀(003), TAPE_OUT(004), OPS(008)
					downCart.setCartNo(das.getCartContent().getCartNo());
					downCart.setUserId(das.getCartContent().getRegrid());
					downCart.setPhysicaltree(""); 							
					downCart.setUrl(""); 									// 입력 url
					downCart.setReqCont("");
					das.setDownCart(downCart);
					
					String downCartXML = xmlStream.toXML(das);
					Integer result = cmsConnector.updateDownCart(downCartXML);
					if(logger.isDebugEnabled()) {
						logger.debug("download result  : "+result);
					}
					if(result == 1) {
						if(logger.isDebugEnabled()) {
							logger.debug("updateDownCart success: "+result);
						}
					} else {
						throw new ServiceException("The download request is failed - master_id: "+masterId);
					}
				}
			}
		} catch (Exception e) {
			throw new ServiceException("download error", e);
		}
		
		return das.getCartContent().getCartNo();
	}

}

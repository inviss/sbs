package com.sbs.das.services;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.dto.ContentInstTbl;
import com.sbs.das.dto.ops.DownCart;


public interface ContentDownloadService {
	
	public ContentInstTbl getContentInstObj(Long masterId) throws ServiceException;
	public long requestDownload(DownCart downCart) throws ServiceException;
	
}

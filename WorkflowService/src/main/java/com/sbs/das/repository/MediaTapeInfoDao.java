package com.sbs.das.repository;

import java.util.List;
import java.util.Map;

import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.MediaTapeInfoTbl;

public interface MediaTapeInfoDao {
	public List<MediaTapeInfoTbl> findMediaTapeInfo(Map<String, Object> params) throws DaoNonRollbackException;
	
	public MediaTapeInfoTbl getMediaTapeInfo(Map<String, Object> params) throws DaoNonRollbackException;
	
	public void insertMediaTapeInfo(MediaTapeInfoTbl mediaTapeInfoTbl) throws DaoRollbackException;
	
	public void insertAllMediaTapeInfo(List<MediaTapeInfoTbl> mediaTapeInfoTbls) throws DaoRollbackException;
}

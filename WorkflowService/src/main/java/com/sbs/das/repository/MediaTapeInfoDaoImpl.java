package com.sbs.das.repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.sbs.das.commons.exception.DaoNonRollbackException;
import com.sbs.das.commons.exception.DaoRollbackException;
import com.sbs.das.dto.MediaTapeInfoTbl;

@Repository(value="mediaTapeInfoDao")
public class MediaTapeInfoDaoImpl extends SqlMapClientDaoSupport implements MediaTapeInfoDao {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	public final void setSqlMapClientWorkaround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) { 
		super.setSqlMapClient(sqlMapClient); 
	}

	@SuppressWarnings("unchecked")
	public List<MediaTapeInfoTbl> findMediaTapeInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return getSqlMapClientTemplate().queryForList("MediaTapeInfo.findMediaTapeInfo", params);
	}

	public void insertMediaTapeInfo(MediaTapeInfoTbl mediaTapeInfoTbl)
			throws DaoRollbackException {
		getSqlMapClientTemplate().insert("MediaTapeInfo.insertMediaTapeInfo", mediaTapeInfoTbl);
	}

	public MediaTapeInfoTbl getMediaTapeInfo(Map<String, Object> params)
			throws DaoNonRollbackException {
		return (MediaTapeInfoTbl)getSqlMapClientTemplate().queryForObject("MediaTapeInfo.getMediaTapeInfo", params);
	}

	public void insertAllMediaTapeInfo(List<MediaTapeInfoTbl> mediaTapeInfoTbls)
			throws DaoRollbackException {
		
		FileWriter fail = null;
		BufferedWriter failBuffer = null;
		try {
			fail = new FileWriter("D:\\tmp\\uncompress.txt", true);
			failBuffer = new BufferedWriter(fail);
			
			getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(false);
			getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
			
			Map<String, Object> params = new HashMap<String, Object>();
			for(MediaTapeInfoTbl mediaTapeInfoTbl : mediaTapeInfoTbls) {
				//getSqlMapClientTemplate().delete("MediaTapeInfo.deleteMediaTapeInfo", mediaTapeInfoTbl);
				
				if(StringUtils.isNotBlank(mediaTapeInfoTbl.getReqCd())) params.put("reqCd", mediaTapeInfoTbl.getReqCd());
				if(mediaTapeInfoTbl.getSceanNo() != null && mediaTapeInfoTbl.getSceanNo() > 0) params.put("sceanNo", mediaTapeInfoTbl.getSceanNo());
				if(mediaTapeInfoTbl.getEpisNo() != null && mediaTapeInfoTbl.getEpisNo() > 0) params.put("episNo", mediaTapeInfoTbl.getEpisNo());
				
				MediaTapeInfoTbl infoTbl = (MediaTapeInfoTbl)getSqlMapClientTemplate().queryForObject("MediaTapeInfo.getMediaTapeInfo", params);
				if(infoTbl == null) {
					getSqlMapClientTemplate().insert("MediaTapeInfo.insertMediaTapeInfo", mediaTapeInfoTbl);
				} else {
					failBuffer.write(mediaTapeInfoTbl.getReqCd()+"|"+mediaTapeInfoTbl.getSceanNo()+"|"+mediaTapeInfoTbl.getEpisNo()+",00");
					failBuffer.newLine();
				}
				params.clear();
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
			getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().commit();
		} catch (SQLException e) {
			throw new DaoRollbackException("insertAllMediaTapeInfo error", e);
		} catch (IOException e) {
			logger.error("FailList Write Error", e);
		} finally {
			try {
				getSqlMapClientTemplate().getSqlMapClient().endTransaction();
				getSqlMapClientTemplate().getSqlMapClient().getCurrentConnection().setAutoCommit(true);
				
				if(failBuffer != null) {
					failBuffer.flush();
					failBuffer.close();
				}
			} catch (Exception e2) {}
		}
	}

}

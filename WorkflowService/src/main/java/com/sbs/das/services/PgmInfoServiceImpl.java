package com.sbs.das.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.PgmInfoTbl;
import com.sbs.das.dto.ops.Program;
import com.sbs.das.repository.PgmInfoDao;

@Service(value="pgmInfoService")
public class PgmInfoServiceImpl implements PgmInfoService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PgmInfoDao pgmInfoDao;

	public PgmInfoTbl getPgmInfo(String pgmCd) throws ServiceException {
		if(logger.isDebugEnabled()) {
			logger.debug("pgm_cd: "+pgmCd);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pgmCd", pgmCd);
		return pgmInfoDao.getPgmInfo(params);
	}
	
	public void savePgmInfo(Program pgm) throws ServiceException {
		
		PgmInfoTbl pgmInfo = getPgmInfo(pgm.getDasPgmCd());
		
		if(logger.isDebugEnabled()) {
			logger.debug("pgmInfo : "+pgmInfo);
		}
		if(pgmInfo == null) {
			pgmInfo = new PgmInfoTbl();
			pgmInfo.setPgmCd(pgm.getDasPgmCd());
			pgmInfo.setMediaCd(StringUtils.isNotBlank(pgm.getMediaCd()) ? pgm.getMediaCd() : "T");
			pgmInfo.setChanCd(StringUtils.isNotBlank(pgm.getChanCd()) ? pgm.getChanCd() : "T");
			pgmInfo.setPgmNm(pgm.getPgmTitle());
			pgmInfo.setCtgrLCd(pgm.getLargeCtgCd());
			pgmInfo.setCtgrMCd(pgm.getMidCtgCd());
			pgmInfo.setCtgrSCd(pgm.getSmallCtgCd());
			pgmInfo.setBrdBgnDd(pgm.getBradStDate());
			pgmInfo.setBrdEndDd(pgm.getBradFnsDate());
			pgmInfo.setPrdDeptNm(StringUtils.isNotBlank(pgm.getDeptNm()) ? pgm.getDeptNm() : "");
			pgmInfo.setSchdPgmNm(StringUtils.isNotBlank(pgm.getFrmtnNm()) ? pgm.getFrmtnNm() : "");
			pgmInfo.setPilotYn(pgm.getPilotYn());
			pgmInfo.setAwardHstr(StringUtils.isNotBlank(pgm.getAwardTxn()) ? pgm.getAwardTxn() : "");
			pgmInfo.setUseYn("Y");
			pgmInfo.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			pgmInfo.setRegrid(pgm.getRegrid());
			pgmInfo.setModDt("");
			pgmInfo.setModrid("");
			pgmInfo.setModEndYn("N");
			pgmInfo.setParentsCd(StringUtils.isNotBlank(pgm.getParentsCd()) ? pgm.getParentsCd() : "");
			
			pgmInfoDao.insertPgmInfo(pgmInfo);
		} else {
			if(StringUtils.isNotBlank(pgm.getPgmTitle()))
				pgmInfo.setPgmNm(pgm.getPgmTitle());
			if(StringUtils.isNotBlank(pgm.getLargeCtgCd()))
				pgmInfo.setCtgrLCd(pgm.getLargeCtgCd());
			if(StringUtils.isNotBlank(pgm.getMidCtgCd()))
				pgmInfo.setCtgrMCd(pgm.getMidCtgCd());
			if(StringUtils.isNotBlank(pgm.getSmallCtgCd()))
				pgmInfo.setCtgrSCd(pgm.getSmallCtgCd());
			if(StringUtils.isNotBlank(pgm.getBradStDate()))
				pgmInfo.setBrdBgnDd(pgm.getBradStDate());
			if(StringUtils.isNotBlank(pgm.getBradFnsDate()))
				pgmInfo.setBrdEndDd(pgm.getBradFnsDate());
			if(StringUtils.isNotBlank(pgm.getDeptNm()))
				pgmInfo.setPrdDeptNm(pgm.getDeptNm());
			if(StringUtils.isNotBlank(pgm.getFrmtnNm()))
				pgmInfo.setSchdPgmNm(pgm.getFrmtnNm());
			if(StringUtils.isNotBlank(pgm.getPilotYn()))
				pgmInfo.setPilotYn(pgm.getPilotYn());
			if(StringUtils.isNotBlank(pgm.getAwardTxn()))
				pgmInfo.setAwardHstr(pgm.getAwardTxn());
			if(StringUtils.isNotBlank(pgm.getUseYn()))
				pgmInfo.setUseYn(pgm.getUseYn().toUpperCase());
			if(StringUtils.isNotBlank(pgm.getRegrid()))
				pgmInfo.setModrid(pgm.getRegrid());
			if(StringUtils.isNotBlank(pgm.getParentsCd()))
				pgmInfo.setParentsCd(pgm.getParentsCd());
			if(StringUtils.isNotBlank(pgm.getMediaCd()))
				pgmInfo.setMediaCd(pgm.getMediaCd());
			if(StringUtils.isNotBlank(pgm.getChanCd()))
				pgmInfo.setChanCd(pgm.getChanCd());
			
			pgmInfo.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
			pgmInfo.setUseYn(StringUtils.isNotBlank(pgm.getUseYn()) ? pgm.getUseYn() : "Y");
			
			pgmInfoDao.updatePgmInfo(pgmInfo);
		}
		
	}

}

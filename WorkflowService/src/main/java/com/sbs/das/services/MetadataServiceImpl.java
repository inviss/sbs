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
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.repository.MetadatMstDao;

@Service(value="metadataService")
public class MetadataServiceImpl implements MetadataService {
	
	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MetadatMstDao metadatMstDao;
	
	public List<MetadatMstTbl> findMetaDataList(Data data) throws ServiceException {
		if(logger.isDebugEnabled()) {
			logger.debug("brd_dd   : "+data.getBradDay());
			logger.debug("brdBgnHms: "+data.getBradStTime());
			logger.debug("brdEndHms: "+data.getBradFnsTime());
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brdDd", data.getBradDay());
		params.put("brdBgnHms", data.getBradStTime());
		params.put("brdEndHms", data.getBradFnsTime());
		
		return metadatMstDao.findMetadataList(params);
	}

	public void updateMetadataInfo(Metadata mst) throws ServiceException {
		if(mst.getDasMasterId() == null || mst.getDasMasterId() <= 0)
			throw new ServiceException("das_master_id's value is wrong!");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("masterId", mst.getDasMasterId());
		
		MetadatMstTbl mstTbl = metadatMstDao.getMetadata(params);
		if(mstTbl == null)
			throw new ServiceException("Finding Object is null - master_id: "+mst.getDasMasterId());
		
		if(StringUtils.isNotBlank(mst.getDasPgmCd()))
			mstTbl.setPgmCd(mst.getDasPgmCd());
		if(mst.getPgmTms() != null && mst.getPgmTms() > 0)
			mstTbl.setEpisNo(mst.getPgmTms());
		if(StringUtils.isNotBlank(mst.getPgmTmsTitle()))
			mstTbl.setTitle(mst.getPgmTmsTitle());
		if(StringUtils.isNotBlank(mst.getUseClas()))
			mstTbl.setRistClfCd(mst.getUseClas());
		if(StringUtils.isNotBlank(mst.getPgmRght()))
			;
		if(StringUtils.isNotBlank(mst.getPgmTmsSynop()))
			;
		if(StringUtils.isNotBlank(mst.getLargeCtgCd()))
			;
		if(StringUtils.isNotBlank(mst.getMidCtgCd()))
			;
		if(StringUtils.isNotBlank(mst.getSmallCtgCd()))
			;
		if(StringUtils.isNotBlank(mst.getDrtPrsnNm()))
			;
		if(StringUtils.isNotBlank(mst.getProducerNm()))
			;
		if(StringUtils.isNotBlank(mst.getWrtrNm()))
			;
		if(StringUtils.isNotBlank(mst.getMnfcDeptCd()))
			;
		if(StringUtils.isNotBlank(mst.getPgmRght()))
			;
		if(StringUtils.isNotBlank(mst.getOrginMnfcNm()))
			;
		if(StringUtils.isNotBlank(mst.getProgrPrsnNm()))
			;
		if(StringUtils.isNotBlank(mst.getCastNm()))
			;
		if(StringUtils.isNotBlank(mst.getCastDirtNm()))
			;
		if(StringUtils.isNotBlank(mst.getPtghDay()))
			;
		if(StringUtils.isNotBlank(mst.getPtghPlc()))
			;
		if(StringUtils.isNotBlank(mst.getCpyrtPrsnNm()))
			;
		if(StringUtils.isNotBlank(mst.getCpyrtShapCd()))
			;
		if(StringUtils.isNotBlank(mst.getCpyrtShapDesc()))
			;
		if(StringUtils.isNotBlank(mst.getViwrClasCd()))
			;
		if(StringUtils.isNotBlank(mst.getDlbResltCd()))
			;
		if(StringUtils.isNotBlank(mst.getAwardTxn()))
			;
		if(StringUtils.isNotBlank(mst.getPclrMtr()))
			;
		if(StringUtils.isNotBlank(mst.getMusicInfo()))
			;
		if(StringUtils.isNotBlank(mst.getCmpnCd()))
			;
		if(StringUtils.isNotBlank(mst.getArtist()))
			;
		if(StringUtils.isNotBlank(mst.getChId()))
			;
		if(StringUtils.isNotBlank(mst.getAgnBrad()))
			;
		if(StringUtils.isNotBlank(mst.getRstrtnMtr()))
			;
		if(StringUtils.isNotBlank(mst.getSubTitle()))
			;
		if(StringUtils.isNotBlank(mst.getFrmtnNm()))
			;
		if(StringUtils.isNotBlank(mst.getActcCd()))
			;
		
		metadatMstDao.saveMetadata(mstTbl);
	}

}

package com.sbs.das.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.PgmInfoTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.repository.MetadatMstDao;
import com.sbs.das.repository.PgmInfoDao;

@Service(value="metadataService")
public class MetadataServiceImpl implements MetadataService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MetadatMstDao metadatMstDao;
	@Autowired
	private PgmInfoDao pgmInfoDao;

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

		if(StringUtils.isNotBlank(mst.getDasPgmCd())) {

			params.put("pgmCd", mst.getDasPgmCd());
			PgmInfoTbl pgmInfoTbl = pgmInfoDao.getPgmInfo(params);

			if(pgmInfoTbl != null) {
				mstTbl.setPgmCd(pgmInfoTbl.getPgmCd());
				mstTbl.setPgmId(pgmInfoTbl.getPgmId().intValue());
			} else {
				logger.error("program's info can't find out, pgm_cd: "+mst.getDasPgmCd());
				throw new ServiceException("Can't find program info by pgm_cd. pgm_cd: "+mst.getDasPgmCd());
			}
		}
		if(mst.getPgmTms() != null && mst.getPgmTms() > 0)
			mstTbl.setEpisNo(mst.getPgmTms());
		if(StringUtils.isNotBlank(mst.getPgmTmsTitle()))
			mstTbl.setTitle(mst.getPgmTmsTitle());
		if(StringUtils.isNotBlank(mst.getUseClas()))
			mstTbl.setRistClfCd(mst.getUseClas());
		//if(StringUtils.isNotBlank(mst.getPgmRght()))
		//	mstTbl.setCprtType(mst.getPgmRght());
		if(StringUtils.isNotBlank(mst.getPgmTmsSynop()))
			mstTbl.setSnps(mst.getPgmTmsSynop());
		if(StringUtils.isNotBlank(mst.getLargeCtgCd()))
			mstTbl.setCtgrLCd(mst.getLargeCtgCd());
		if(StringUtils.isNotBlank(mst.getMidCtgCd()))
			mstTbl.setCtgrMCd(mst.getMidCtgCd());
		if(StringUtils.isNotBlank(mst.getSmallCtgCd()))
			mstTbl.setCtgrSCd(mst.getSmallCtgCd());
		if(StringUtils.isNotBlank(mst.getDrtPrsnNm()))
			mstTbl.setDrtNm(mst.getDrtPrsnNm());
		if(StringUtils.isNotBlank(mst.getProducerNm()))
			mstTbl.setProducerNm(mst.getProducerNm());
		if(StringUtils.isNotBlank(mst.getWrtrNm()))
			mstTbl.setWriterNm(mst.getWrtrNm());
		if(StringUtils.isNotBlank(mst.getMnfcDivCd()))
			mstTbl.setPrdtInOutsCd(mst.getMnfcDivCd());
		if(StringUtils.isNotBlank(mst.getMnfcDeptCd()))
			mstTbl.setPrdtDeptCd(mst.getMnfcDeptCd());
		if(StringUtils.isNotBlank(mst.getOrginMnfcNm()))
			mstTbl.setOrgPrdrNm(mst.getOrginMnfcNm());
		if(StringUtils.isNotBlank(mst.getProgrPrsnNm()))
			mstTbl.setMcNm(mst.getProgrPrsnNm());
		if(StringUtils.isNotBlank(mst.getCastNm()))
			mstTbl.setCastNm(mst.getCastNm());
		if(StringUtils.isNotBlank(mst.getCastDirtNm()))
			mstTbl.setCmrDrtNm(mst.getCastDirtNm());
		if(StringUtils.isNotBlank(mst.getPtghDay()))
			mstTbl.setFmDt(mst.getPtghDay());
		if(StringUtils.isNotBlank(mst.getPtghPlc()))
			mstTbl.setCmrPlace(mst.getPtghPlc());
		if(StringUtils.isNotBlank(mst.getCpyrtPrsnNm()))
			mstTbl.setCprtrNm(mst.getCpyrtPrsnNm());
		if(StringUtils.isNotBlank(mst.getCpyrtShapCd()))
			mstTbl.setCprtType(mst.getCpyrtShapCd());
		if(StringUtils.isNotBlank(mst.getCpyrtShapDesc()))
			mstTbl.setCprtTypeDsc(mst.getCpyrtShapDesc());
		if(StringUtils.isNotBlank(mst.getViwrClasCd()))
			mstTbl.setViewGrCd(mst.getViwrClasCd());
		if(StringUtils.isNotBlank(mst.getDlbResltCd()))
			mstTbl.setDlbrCd(mst.getDlbResltCd());
		if(StringUtils.isNotBlank(mst.getAwardTxn()))
			mstTbl.setAwardHstr(mst.getAwardTxn());
		if(StringUtils.isNotBlank(mst.getPclrMtr()))
			mstTbl.setSpcInfo(mst.getPclrMtr());
		if(StringUtils.isNotBlank(mst.getMusicInfo()))
			mstTbl.setMusicInfo(mst.getMusicInfo());
		if(StringUtils.isNotBlank(mst.getCmpnCd()))
			mstTbl.setCocd(mst.getCmpnCd());
		if(StringUtils.isNotBlank(mst.getArtist()))
			mstTbl.setArtist(mst.getArtist());
		if(StringUtils.isNotBlank(mst.getChId()))
			mstTbl.setChennelCd(mst.getChId());
		if(StringUtils.isNotBlank(mst.getAgnBrad()))
			mstTbl.setRerun(mst.getAgnBrad());
		if(StringUtils.isNotBlank(mst.getRstrtnMtr()))
			mstTbl.setRstCont(mst.getRstrtnMtr());
		if(StringUtils.isNotBlank(mst.getSubTitle()))
			mstTbl.setSubTtl(mst.getSubTitle());
		if(StringUtils.isNotBlank(mst.getFrmtnNm()))
			mstTbl.setArrangeNm(mst.getFrmtnNm());
		if(StringUtils.isNotBlank(mst.getActcCd()))
			mstTbl.setDataStatCd(mst.getActcCd());
		if(StringUtils.isNotBlank(mst.getRegrid()))
			mstTbl.setModrid(mst.getRegrid());
		mstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
		
		/*
		 * 2016.05.19
		 * OPS -> DAS 맵핑 관계를 제거를 할 경우 is_linked 값을 'N'으로 전달해야 한다.
		 * default 'Y'
		 */
		if(mst.getIsLinked().equals("N")) {
			if(StringUtils.isNotBlank(mstTbl.getArchRoute())) {
				if(mstTbl.getArchRoute().indexOf("OS") > -1) {
					mstTbl.setArchRoute(mstTbl.getArchRoute().replaceAll("OS", ""));
				}
			}
		} else {
			if(StringUtils.isNotBlank(mstTbl.getArchRoute())) {
				if(mstTbl.getArchRoute().indexOf("OS") > -1) ; //nothing
				else mstTbl.setArchRoute(mstTbl.getArchRoute()+"OS");
			} else {
				mstTbl.setArchRoute("OS");
			}
		}

		metadatMstDao.saveMetadata(mstTbl);
	}

}

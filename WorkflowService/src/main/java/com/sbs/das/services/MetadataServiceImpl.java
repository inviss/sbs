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
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.MetadatMstTbl;
import com.sbs.das.dto.PgmInfoTbl;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.dto.ops.Metadata;
import com.sbs.das.repository.AnnotInfoDao;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentMapDao;
import com.sbs.das.repository.CornerDao;
import com.sbs.das.repository.MetadatMstDao;
import com.sbs.das.repository.PgmInfoDao;

@Transactional
@Service(value="metadataService")
public class MetadataServiceImpl implements MetadataService {

	final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MetadatMstDao metadatMstDao;
	@Autowired
	private PgmInfoDao pgmInfoDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private CornerDao cornerDao;
	@Autowired
	private ContentMapDao contentMapDao;
	@Autowired
	private AnnotInfoDao annotInfoDao;

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

	@Transactional
	public void updateMetadataInfo(Metadata mst) throws ServiceException {

		Map<String, Object> params = new HashMap<String, Object>();

		/*
		 * 2016.05.19
		 * OPS -> DAS 맵핑 관계를 제거를 할 경우 is_linked 값을 'N'으로 전달해야 한다.
		 * default 'Y'
		 * 2016.05.25 OPS 회의결과
		 * 변경이 발생할 경우 master_id 필드에 ',' 를 이용하여 전달됨. 예) 12345,12346
		 * is_linked 컬럼에도 master_id 순서에 맞춰서 맵핑여부 전달됨, 예) N,Y
		 */
		String[] masterIds = null;
		String[] isLinkYns = null;
		if(mst.getDasMasterId().indexOf(",") > -1 && mst.getIsLinked().indexOf(",") > -1) {
			masterIds = mst.getDasMasterId().split("\\,");
			isLinkYns = mst.getIsLinked().split("\\,");
		} else {
			masterIds = new String[] {mst.getDasMasterId()};
			isLinkYns = new String[] {StringUtils.isNotBlank(mst.getIsLinked()) ? mst.getIsLinked() : "Y"};
		}

		int count = masterIds.length;
		for(int i=0; i < count; i++) {

			params.put("masterId", Long.parseLong(masterIds[i]));

			MetadatMstTbl mstTbl = metadatMstDao.getMetadata(params);
			if(mstTbl == null) {
				logger.error("Finding Object is null - master_id: "+mst.getDasMasterId());
				throw new ServiceException("Finding Object is null - master_id: "+mst.getDasMasterId());
			}

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
			//if(mst.getPgmTms() != null && mst.getPgmTms() > 0)
				mstTbl.setEpisNo(mst.getPgmTms());
			//if(StringUtils.isNotBlank(mst.getPgmTmsTitle()))
				mstTbl.setTitle(mst.getPgmTmsTitle());
			//if(StringUtils.isNotBlank(mst.getUseClas()))
				mstTbl.setRistClfCd(mst.getUseClas());
			//if(StringUtils.isNotBlank(mst.getPgmTmsSynop()))
				mstTbl.setSnps(mst.getPgmTmsSynop());
			//if(StringUtils.isNotBlank(mst.getLargeCtgCd()))
				mstTbl.setCtgrLCd(mst.getLargeCtgCd());
			//if(StringUtils.isNotBlank(mst.getMidCtgCd()))
				mstTbl.setCtgrMCd(mst.getMidCtgCd());
			//if(StringUtils.isNotBlank(mst.getSmallCtgCd()))
				mstTbl.setCtgrSCd(mst.getSmallCtgCd());
			//if(StringUtils.isNotBlank(mst.getDrtPrsnNm()))
				mstTbl.setDrtNm(mst.getDrtPrsnNm());
			//if(StringUtils.isNotBlank(mst.getProducerNm()))
				mstTbl.setProducerNm(mst.getProducerNm());
			//if(StringUtils.isNotBlank(mst.getWrtrNm()))
				mstTbl.setWriterNm(mst.getWrtrNm());
			//if(StringUtils.isNotBlank(mst.getMnfcDivCd()))
				mstTbl.setPrdtInOutsCd(mst.getMnfcDivCd());
			//if(StringUtils.isNotBlank(mst.getMnfcDeptCd()))
				mstTbl.setPrdtDeptCd(mst.getMnfcDeptCd());
			//if(StringUtils.isNotBlank(mst.getOrginMnfcNm()))
				mstTbl.setOrgPrdrNm(mst.getOrginMnfcNm());
			//if(StringUtils.isNotBlank(mst.getProgrPrsnNm()))
				mstTbl.setMcNm(mst.getProgrPrsnNm());
			//if(StringUtils.isNotBlank(mst.getCastNm()))
				mstTbl.setCastNm(mst.getCastNm());
			//if(StringUtils.isNotBlank(mst.getCastDirtNm()))
				mstTbl.setCmrDrtNm(mst.getCastDirtNm());
			//if(StringUtils.isNotBlank(mst.getPtghDay()))
				mstTbl.setFmDt(mst.getPtghDay());
			//if(StringUtils.isNotBlank(mst.getPtghPlc()))
				mstTbl.setCmrPlace(mst.getPtghPlc());
			//if(StringUtils.isNotBlank(mst.getCpyrtPrsnNm()))
				mstTbl.setCprtrNm(mst.getCpyrtPrsnNm());
			//if(StringUtils.isNotBlank(mst.getCpyrtShapCd()))
				mstTbl.setCprtType(mst.getCpyrtShapCd());
			//if(StringUtils.isNotBlank(mst.getCpyrtShapDesc()))
				mstTbl.setCprtTypeDsc(mst.getCpyrtShapDesc());
			//if(StringUtils.isNotBlank(mst.getViwrClasCd()))
				mstTbl.setViewGrCd(mst.getViwrClasCd());
			//if(StringUtils.isNotBlank(mst.getDlbResltCd()))
				mstTbl.setDlbrCd(mst.getDlbResltCd());
			//if(StringUtils.isNotBlank(mst.getAwardTxn()))
				mstTbl.setAwardHstr(mst.getAwardTxn());
			//if(StringUtils.isNotBlank(mst.getPclrMtr()))
				mstTbl.setSpcInfo(mst.getPclrMtr());
			//if(StringUtils.isNotBlank(mst.getMusicInfo()))
				mstTbl.setMusicInfo(mst.getMusicInfo());
			//if(StringUtils.isNotBlank(mst.getCmpnCd()))
				mstTbl.setCocd(mst.getCmpnCd());
			//if(StringUtils.isNotBlank(mst.getArtist()))
				mstTbl.setArtist(mst.getArtist());
			//if(StringUtils.isNotBlank(mst.getChId()))
				mstTbl.setChennelCd(mst.getChId());
			//if(StringUtils.isNotBlank(mst.getAgnBrad()))
				mstTbl.setRerun(mst.getAgnBrad());
			//if(StringUtils.isNotBlank(mst.getRstrtnMtr()))
				mstTbl.setRstCont(mst.getRstrtnMtr());
			//if(StringUtils.isNotBlank(mst.getSubTitle()))
				mstTbl.setSubTtl(mst.getSubTitle());
			//if(StringUtils.isNotBlank(mst.getFrmtnNm()))
				mstTbl.setArrangeNm(mst.getFrmtnNm());
			/* 2016.07.19 박복영 차장님 요청사항: 데이타 상태코드 변경 안함. '정리완료' or '검수완료'는 DAS에서 처리함. */
			//if(StringUtils.isNotBlank(mst.getActcCd()))
			//	mstTbl.setDataStatCd(mst.getActcCd());
			//if(StringUtils.isNotBlank(mst.getRegrid()))
			/* 2016.11.14 박복영c, 이재만b 요청으로 연계에서 제외함.
			//	mstTbl.setModrid(mst.getRegrid());
			/*
			 * 2016.06.02
			 * 회차 저장시 메타항목 추가
			 */
			//if(StringUtils.isNotBlank(mst.getFinalBrdYn()))
				mstTbl.setFinalBrdYn(mst.getFinalBrdYn());
			//if(StringUtils.isNotBlank(mst.getKeyWords()))
				mstTbl.setKeyWords(mst.getKeyWords());
			//if(StringUtils.isNotBlank(mst.getOrgPrdrNm()))
				mstTbl.setOrgPrdrNm(mst.getOrgPrdrNm());
			//if(StringUtils.isNotBlank(mst.getCmrDrtNm()))
				mstTbl.setCmrDrtNm(mst.getCmrDrtNm());
			//if(StringUtils.isNotBlank(mst.getPrdtDeptNm()))
				mstTbl.setPrdtDeptNm(mst.getPrdtDeptNm());
			
			mstTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));

			String isLinked = isLinkYns[i];
			if(logger.isInfoEnabled()) {
				logger.info("isLinked: "+isLinked);
			}
			if(isLinked.equals("N")) {
				if(logger.isInfoEnabled()) {
					logger.info("must be breaked a connection with OPS : master_id: "+masterIds[i]);
				}
				if(StringUtils.isNotBlank(mstTbl.getArchRoute())) {
					if(mstTbl.getArchRoute().indexOf("OS") > -1) {
						mstTbl.setArchRoute(mstTbl.getArchRoute().replaceAll("OS", ""));
					}
					
					// 자료상태를 정리전으로 변경해야함.
					mstTbl.setDataStatCd("001");
					
					// 분리된 코너를 초기화 해야 함.
					ContentTbl contentTbl = contentDao.getContentWithMap(Long.parseLong(masterIds[i]));
					if(logger.isDebugEnabled()) {
						logger.debug("ct_id: "+contentTbl.getCtId());
					}
					if(contentTbl != null) {
						CornerTbl cornerTbl = new CornerTbl();
						cornerTbl.setCnId(cornerDao.getCornerNewId());
						cornerTbl.setMasterId(Long.parseLong(masterIds[i]));
						cornerTbl.setCnNm("");
						cornerTbl.setSom("00:00:00:00");
						cornerTbl.setEom(contentTbl.getCtLeng());
						cornerTbl.setSFrame(0L);
						cornerTbl.setCnInfo("");
						cornerTbl.setDuration(contentTbl.getDuration());
						cornerTbl.setRpimgKfrmSeq(0);
						cornerTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						cornerTbl.setRegrid(mst.getRegrid());
						cornerTbl.setModrid(mst.getRegrid());
						cornerTbl.setModDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						
						// 코너 삭제
						cornerDao.deleteCtCorner(contentTbl.getCtId());
						
						// 기본코너 추가
						Long cnId = cornerDao.insertCorner(cornerTbl);
						if(logger.isDebugEnabled()) {
							logger.debug("corner delete completed and added - corner_id: "+cnId);
						}
						
						// 사용제한등급 삭제
						annotInfoDao.deleteAnnotInfo(contentTbl.getCtId());
						
						// 맵정보를 초기화 해야 함
						ContentMapTbl contentMapTbl = new ContentMapTbl();
						contentMapTbl.setCtId(contentTbl.getCtId());
						contentMapTbl.setPgmId(0L);
						contentMapTbl.setMasterId(Long.parseLong(masterIds[i]));
						contentMapTbl.setCnId(cnId);
						contentMapTbl.setCtSeq(1);
						contentMapTbl.setsDuration(0L);
						contentMapTbl.seteDuration(contentTbl.getDuration());
						contentMapTbl.setCnSeq(0);
						contentMapTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						contentMapTbl.setRegrid(mst.getRegrid());
						contentMapTbl.setDelDd("");
						contentMapTbl.setDelYn("N");
						
						// CT_ID 기준으로 MAP 정보 삭제
						params.clear();
						params.put("ctId", contentTbl.getCtId());
						contentMapDao.deleteContentMap(params);
						
						// 기본 맵정보 추가
						contentMapDao.insertContentMap(contentMapTbl);
						if(logger.isDebugEnabled()) {
							logger.debug("map info delete completed and added");
						}
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

			if(mstTbl.getPgmId() > 0 && StringUtils.isNotBlank(mst.getBrdEndDd())) {
				if(StringUtils.isNotBlank(mst.getFinalBrdYn()) && mst.getFinalBrdYn().equals("Y")) {
					params.clear();
					params.put("pgmId", mstTbl.getPgmId());
					PgmInfoTbl pgmInfoTbl = pgmInfoDao.getPgmInfo(params);
					if(pgmInfoTbl != null) {
						pgmInfoTbl.setBrdEndDd(mst.getBrdEndDd());

						pgmInfoDao.updatePgmInfo(pgmInfoTbl);
					}
				}
			}

		}

	}

}

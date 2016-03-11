package com.sbs.das.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.AnnotInfoTbl;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.ops.Annot;
import com.sbs.das.dto.ops.Corner;
import com.sbs.das.dto.ops.Corners;
import com.sbs.das.dto.ops.Data;
import com.sbs.das.repository.AnnotInfoDao;
import com.sbs.das.repository.ContentDao;
import com.sbs.das.repository.ContentMapDao;
import com.sbs.das.repository.CornerDao;

@Service(value="cornerService")
public class CornerServiceImpl implements CornerService {

	final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CornerDao cornerDao;
	@Autowired
	private ContentDao contentDao;
	@Autowired
	private ContentMapDao contentMapDao;
	@Autowired
	private AnnotInfoDao annotInfoDao;
	
	public void updateCorners(Data data) throws ServiceException {
		
		final Logger logger = LoggerFactory.getLogger(getClass());
		
		ContentTbl contentTbl = contentDao.getContentWithMap(data.getDasMasterId());
		if(contentTbl != null) {
			Long ctId = contentTbl.getCtId();
			
			/*
			 * 키프레임 경로에서 장면전환 frame 정보를 조회하여 각 코너에서 지정한 코너 대표이미지의 근사값을 찾는다.
			 */
			String[] keyframes = null;
			String kfrmPath = contentTbl.getKfrmPath().startsWith("/") ? contentTbl.getKfrmPath() : "/"+contentTbl.getKfrmPath();
			File f = new File(kfrmPath, ctId+".txt");
			if(logger.isDebugEnabled()) {
				logger.debug("kframe file path: "+f.getAbsolutePath());
			}
			if(f.exists()) {
				try {
					keyframes = FileUtils.readFileToString(f).split("\\,");
				} catch (IOException e) {
					logger.error("key frame file road error", e);
				}
			}
			
			List<CornerTbl> cornerTbls = new ArrayList<CornerTbl>();
			Corners corners = data.getCorners();
			
			List<Integer> array = new ArrayList<Integer>();
			
			for(Corner corner : corners.getCorners()) {
				CornerTbl cornerTbl = new CornerTbl();
				cornerTbl.setCnNm(corner.getCornerNm());
				cornerTbl.setSom(Utility.changeDuration(corner.getFrameStSectNo()));
				cornerTbl.setEom(Utility.changeDuration(corner.getFrameFnsSectNo()));
				cornerTbl.setSFrame(Long.valueOf(corner.getFrameStSectNo()));
				cornerTbl.setCnInfo(corner.getCornerInfo());
				
				if(keyframes != null && keyframes.length > 0) {
					array.clear();
					for(String k : keyframes) {
						int t = Integer.valueOf(k);
						if(t > corner.getFrameFnsSectNo()) break;
						if((corner.getFrameStSectNo() <= t) && (t <= corner.getFrameFnsSectNo()))
							array.add(t);
					}
				}
				
				/*
				 * 코너별 대표이미지 설정
				 * OPS에서 설정된 대표이미지의 frame 정보는 DAS에서 관리되는 장면전환 frame 정보와
				 * 맞지 않기때문에 근사값으로 재설정을 해줘야 함.
				 * 코너의 시작 프레임과 끝 프레임 사이에서 조회가 되도록 설정해야 함.
				 */
				if(logger.isDebugEnabled()) {
					logger.debug("ops rpimg_kfrm_seq: "+corner.getRpimgKfrmSeq()+", array empty: "+array.isEmpty());
				}
				if(corner.getRpimgKfrmSeq() != null && corner.getRpimgKfrmSeq() > 0) {
					if(!array.isEmpty()) {
						cornerTbl.setRpimgKfrmSeq(Utility.getNearValue(array.toArray(new Integer[array.size()]), corner.getRpimgKfrmSeq()));
					} else
						cornerTbl.setRpimgKfrmSeq(0);
				} else cornerTbl.setRpimgKfrmSeq(0);
				if(logger.isDebugEnabled()) {
					logger.debug("das rpimg_kfrm_seq: "+cornerTbl.getRpimgKfrmSeq());
				}
				
				/*
				 * 전체 사용제한등급이 설정되어 있을경우 코너별로 적용한다.
				 */
				if(StringUtils.isNotBlank(data.getAnnotClfCd())) {
					AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();
					annotInfoTbl.setMasterId(data.getDasMasterId());
					annotInfoTbl.setCtId(ctId);
					annotInfoTbl.setSom(cornerTbl.getSom());
					annotInfoTbl.setEom(cornerTbl.getEom());
					annotInfoTbl.setAnnotClfCd(data.getAnnotClfCd());
					annotInfoTbl.setAnnotClfCont(data.getAnnotClfCont());
					
					long som = Long.valueOf(cornerTbl.getSom()).longValue();
					long eom = Long.valueOf(cornerTbl.getEom()).longValue();
					annotInfoTbl.setDuration(Long.valueOf(eom - som));
					
					annotInfoTbl.setsFrame(som);
					annotInfoTbl.setGubun("L");
					annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
					annotInfoTbl.setRegrid(data.getRegrid());
					annotInfoTbl.setEntireYn("Y");
					
					cornerTbl.addAnnotInfoTbl(annotInfoTbl);
				}
				
				/*
				 * 부분 사용등급이 있을경우 추가로 등록한다.
				 */
				List<Annot> annots = corner.getAnnots();
				if(annots != null && !annots.isEmpty()) {
					for(Annot annot : annots) {
						AnnotInfoTbl annotInfoTbl = new AnnotInfoTbl();
						annotInfoTbl.setMasterId(data.getDasMasterId());
						annotInfoTbl.setCtId(ctId);
						long som = 0L;
						long eom = 0L;
						if(annot.getSom() != null && annot.getSom().indexOf(":") > -1) {
							som = Utility.changeTimeCode(annot.getSom());
							eom = Utility.changeTimeCode(annot.getEom());
						} else {
							som = Long.valueOf(annot.getSom()).longValue();
							eom = Long.valueOf(annot.getEom()).longValue();
						}
						if(logger.isDebugEnabled()) {
							logger.debug("som: "+som+", eom: "+eom);
						}
						int somInt = Utility.getNearValue(array.toArray(new Integer[array.size()]), Long.valueOf(som).intValue());
						int eomInt = Utility.getNearValue(array.toArray(new Integer[array.size()]), Long.valueOf(eom).intValue());
						if(logger.isDebugEnabled()) {
							logger.debug("somInt: "+somInt+", eomInt: "+eomInt);
						}
						annotInfoTbl.setSom(Utility.changeDuration(Long.valueOf(somInt)));
						annotInfoTbl.setEom(Utility.changeDuration(Long.valueOf(eomInt)));
						annotInfoTbl.setAnnotClfCd(annot.getClfCd());
						annotInfoTbl.setAnnotClfCont(annot.getClfCont());
						annotInfoTbl.setDuration(Long.valueOf(eomInt - somInt));
						annotInfoTbl.setsFrame(Long.valueOf(somInt));
						annotInfoTbl.setGubun("L");
						annotInfoTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						annotInfoTbl.setRegrid(data.getRegrid());
						annotInfoTbl.setEntireYn("N");

						cornerTbl.addAnnotInfoTbl(annotInfoTbl);
					}
				}

				cornerTbls.add(cornerTbl);
			}
			
			long[] cnIds = new long[cornerTbls.size()];
			if(!cornerTbls.isEmpty()) {
				/*
				 * 1. ct_id 기준으로 corner정보 모두 삭제 후
				 */
				cornerDao.deleteCtCorner(ctId);
				
				List<AnnotInfoTbl> annotInfoTbls = new ArrayList<AnnotInfoTbl>();
				
				// 코너별 정보 등록 및 코너ID 생성
				int c=0;
				for(CornerTbl cornerTbl : cornerTbls) {
					Long cnId = cornerDao.insertCorner(cornerTbl);
					cornerTbl.setCnId(cnId);
					
					cnIds[c] = cornerTbl.getCnId();
					c++;
					
					/*
					 * 코너별 사용제한등급에 코너ID 셋팅
					 */
					List<AnnotInfoTbl> cornerAnnots = cornerTbl.getAnnotInfoTbls();
					if(cornerAnnots != null && !cornerAnnots.isEmpty()) {
						for(AnnotInfoTbl annotInfoTbl : cornerAnnots) {
							annotInfoTbl.setCnId(cornerTbl.getCnId());
							annotInfoTbls.add(annotInfoTbl);
						}
					}
				}
				
				/*
				 * 2. ct_id 기준으로 사용제한등급을 삭제하고
				 * 코너정보와 함께 제한등급을 재설정한다.
				 */
				if(!annotInfoTbls.isEmpty()) {
					annotInfoDao.deleteAnnotInfo(ctId);
					for(AnnotInfoTbl annotInfoTbl : annotInfoTbls) {
						annotInfoDao.insertAnnotInfo(annotInfoTbl);
					}
				}
				
				/*
				 * 3. ct_id 기준으로 mapp 테이블에서 모두 삭제 후 코너정보 신규 등록
				 */
				Map<String, Object> params = new HashMap<String, Object>();
				if(logger.isDebugEnabled()) {
					logger.debug("ct_id: "+ctId);
				}
				params.put("ctId", ctId);
				ContentMapTbl contentInfo = contentMapDao.getContentGroupInfo(params);
				if(contentInfo != null) {
					
					// CT_ID 기준으로 MAP 정보 삭제
					contentMapDao.deleteContentMap(contentInfo);
					
					// 코너별 정보 등록
					int i = 1;
					ContentMapTbl contentMapTbl = null; 
					for(Corner corner : corners.getCorners()) {
						contentMapTbl = new ContentMapTbl();
						contentMapTbl.setCtId(ctId);
						contentMapTbl.setCnId(cnIds[i-1]);
						contentMapTbl.setsDuration(Long.valueOf(corner.getFrameStSectNo()));
						contentMapTbl.seteDuration(Long.valueOf(corner.getFrameFnsSectNo()));
						contentMapTbl.setCnSeq(i);
						contentMapTbl.setRegDt(Utility.getTimestamp("yyyyMMddHHmmss"));
						contentMapTbl.setRegrid(data.getRegrid());
						contentMapTbl.setDelDd("");
						contentMapTbl.setDelYn("N");
						
						contentMapDao.insertContentMap(contentMapTbl);
						i++;
					}
				} else {
					throw new ServiceException("contents_mapp info not found - ct_id: "+ctId);
				}
			}
		} else {
			throw new ServiceException("Not found content info : master_id: "+data.getDasMasterId());
		}
		
	}

}

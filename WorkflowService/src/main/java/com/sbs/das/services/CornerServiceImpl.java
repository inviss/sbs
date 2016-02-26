package com.sbs.das.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbs.das.commons.exception.ServiceException;
import com.sbs.das.commons.utils.Utility;
import com.sbs.das.dto.ContentMapTbl;
import com.sbs.das.dto.ContentTbl;
import com.sbs.das.dto.CornerTbl;
import com.sbs.das.dto.ops.Corner;
import com.sbs.das.dto.ops.Corners;
import com.sbs.das.dto.ops.Data;
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
				
				if(keyframes != null) {
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
				if(corner.getRpimgKfrmSeq() != null && corner.getRpimgKfrmSeq() > 0) {
					if(!array.isEmpty()) {
						cornerTbl.setRpimgKfrmSeq(Utility.getNearValue(array.toArray(new Integer[array.size()]), corner.getRpimgKfrmSeq()));
					} else
						cornerTbl.setRpimgKfrmSeq(0);
				} else cornerTbl.setRpimgKfrmSeq(0);
				
				cornerTbls.add(cornerTbl);
			}
			
			long[] cnIds = new long[cornerTbls.size()];
			if(!cornerTbls.isEmpty()) {
				/*
				 * 1. master_id 기준으로 corner정보 모두 삭제 후
				 */
				cornerDao.deleteCorner(data.getDasMasterId());
				
				// 코너별 정보 등록 및 코너ID 생성
				int c=0;
				for(CornerTbl cornerTbl : cornerTbls) {
					Long cnId = cornerDao.insertCorner(cornerTbl);
					cornerTbl.setCnId(cnId);
					
					cnIds[c] = cornerTbl.getCnId();
					c++;
				}
				
				/*
				 * 2. ct_id 기준으로 mapp 테이블에서 모두 삭제 후 코너정보 신규 등록
				 */
				Map<String, Object> params = new HashMap<String, Object>();
				
				// contentMapDao.getContentGroupCount(params);
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

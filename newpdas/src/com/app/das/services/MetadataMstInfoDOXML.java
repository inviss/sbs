package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  메타 정보 관련 XML파서
 * @author asura207
 *
 */
public class MetadataMstInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "metadatMasterInfo";	                                     
	/**
	 *  마스터 아이디
	 */
	private final String XML_NODE_MASTER_ID = "MASTER_ID";    //
	/**
	 *  프로그램 아이디
	 */
	private final String XML_NODE_PGM_ID = "PGM_ID";			//
	/**
	 * 프로그램 코드
	 */
	private final String XML_NODE_PGM_CD = "PGM_CD";			// 
	/**
	 * 회차
	 */
	private final String XML_NODE_EPIS_NO = "EPIS_NO";		// 
	/**
	 * 부제
	 */
	private final String XML_NODE_TITLE = "TITLE";			// 
	/**
	 * 대분류 코드
	 */
	private final String XML_NODE_CTGR_L_CD = "CTGR_L_CD";	// 
	/**
	 * 중분류 코드
	 */
	private final String XML_NODE_CTGR_M_CD = "CTGR_M_CD";	// 
	/**
	 * 소분류 코드
	 */
	private final String XML_NODE_CTGR_S_CD = "CTGR_S_CD";	// 
	/**
	 * 방송일
	 */
	private final String XML_NODE_BRD_DD = "BRD_DD";			// 
	/**
	 * 최종방송여부
	 */
	private final String XML_NODE_FINAL_BRD_YN = "FINAL_BRD_YN";	// 
	/**
	 * 시놉시스
	 */
	private final String XML_NODE_SNPS = "SNPS";				// 
	/**
	 * 키워드		
	 */
	private final String XML_NODE_KEY_WORDS = "KEY_WORDS";	// 
	/**
	 * 방송시작시간
	 */
	private final String XML_NODE_BRD_BGN_HMS = "BRD_BGN_HMS";	// 
	/**
	 * 방송종료시간
	 */
	private final String XML_NODE_BRD_END_HMS = "BRD_END_HMS";	// 
	/**
	 * 방송 길이
	 */
	private final String XML_NODE_BRD_LENG = "BRD_LENG";			// 
	/**
	 *  시청률
	 */
	private final String XML_NODE_PGM_RATE = "PGM_RATE";			//
	/**
	 *  연출자명
	 */
	private final String XML_NODE_DRT_NM = "DRT_NM";				//
	/**
	 * 프로듀서명
	 */
	private final String XML_NODE_PRODUCER_NM = "PRODUCER_NM";    // 
	/**
	 * 작가명
	 */
	private final String XML_NODE_WRITER_NM = "WRITER_NM";		// 
	/**
	 * 제작구분코드
	 */
	private final String XML_NODE_PRDT_IN_OUTS_CD = "PRDT_IN_OUTS_CD";// 
	/**
	 * 제작부서코드
	 */
	private final String XML_NODE_PRDT_DEPT_CD = "PRDT_DEPT_CD";	// 
	/**
	 * 제작부서명
	 */
	private final String XML_NODE_PRDT_DEPT_NM = "PRDT_DEPT_NM";	// 
	/**
	 * 원제작사명
	 */
	private final String XML_NODE_ORG_PRDR_NM = "ORG_PRDR_NM";	// 
	/**
	 * 진행자명
	 */
	private final String XML_NODE_MC_NM = "MC_NM";				// 
	/**
	 * 출연자명
	 */
	private final String XML_NODE_CAST_NM = "CAST_NM";			// 
	/**
	 * 촬영감독명
	 */
	private final String XML_NODE_CMR_DRT_NM = "CMR_DRT_NM";		// 
	/**
	 * 촬영일
	 */
	private final String XML_NODE_FM_DT = "FM_DT";				// 
	/**
	 * 촬영장소
	 */
	private final String XML_NODE_CMR_PLACE = "CMR_PLACE";		// 
	/**
	 *  특이사항
	 */
	private final String XML_NODE_SPC_INFO = "SPC_INFO";			//
	/**
	 *  청구번호
	 */
	private final String XML_NODE_REQ_CD = "REQ_CD";				//
	/**
	 * 아카이버명
	 */
	private final String XML_NODE_SEC_ARCH_NM = "SEC_ARCH_NM";	// 
	/**
	 * 아카이브 아이디
	 */
	private final String XML_NODE_SEC_ARCH_ID = "SEC_ARCH_ID";	// 
	/**
	 * 수집처코드
	 */
	private final String XML_NODE_GATH_CO_CD = "GATH_CO_CD";		// 
	/**
	 * 수집구분코드
	 */
	private final String XML_NODE_GATH_CLF_CD = "GATH_CLF_CD";	// 
	/**
	 * 아카이브 등록일
	 */
	private final String XML_NODE_ARCH_REG_DD = "ARCH_REG_DD";	// 
	/**
	 * 정리완료일
	 */
	private final String XML_NODE_ARRG_END_DT = "ARRG_END_DT";	// 
	/**
	 * 작업우선순위코드
	 */
	private final String XML_NODE_WORK_PRIO_CD = "WORK_PRIO_CD";	// 
	/**
	 * 보존기간코드
	 */
	private final String XML_NODE_RSV_PRD_CD = "RSV_PRD_CD";		// 
	/**
	 * 저작권자명
	 */
	private final String XML_NODE_CPRTR_NM = "CPRTR_NM";			// 
	/**
	 * 저작권형태코드
	 */
	private final String XML_NODE_CPRT_TYPE = "CPRT_TYPE";		// 
	/**
	 * 저작권형태설명
	 */
	private final String XML_NODE_CPRT_TYPE_DSC = "CPRT_TYPE_DSC";	// 
	/**
	 * 시청등급코드
	 */
	private final String XML_NODE_VIEW_GR_CD = "VIEW_GR_CD";		// 
	/**
	 * 심의결과코드
	 */
	private final String XML_NODE_DLBR_CD = "dlbr_cd";			// 
	/**
	 * 수상내역
	 */
	private final String XML_NODE_AWARD_HSTR = "award_hstr";		// 
	/**
	 * 대표화면 키프레임일련번호
	 */
	private final String XML_NODE_RPIMG_KFRM_SEQ = "rpimg_kfrm_seq";	// 
	/**
	 *  테이프아이디
	 */
	private final String XML_NODE_TAPE_ID = "tape_id";			//
	/**
	 * 테이프 아이템 아이디
	 */
	private final String XML_NODE_TAPE_ITEM_ID = "tape_item_id";	// 
	/**
	 * 테이브 구분코드
	 */
	private final String XML_NODE_TAPE_MEDIA_CLF_CD = "tape_media_clf_cd";	// 
	/**
	 * 보존기간 만료일
	 */
	private final String XML_NODE_RSV_PRD_END_DD = "rsv_prd_end_dd";	// 
	/**
	 * 삭제일
	 */
	private final String XML_NODE_DEL_DD = "del_dd";				// 
	/**
	 * 사용여부
	 */
	private final String XML_NODE_USEYN = "useyn";				// 
	/**
	 * 등록일
	 */
	private final String XML_NODE_REG_DT = "reg_dt";				// 
	/**
	 * 등록자 아이디
	 */
	private final String XML_NODE_REGRID = "regrid";				// 
	/**
	 * 수정일
	 */
	private final String XML_NODE_MOD_DT = "mod_dt";				// 
	/**
	 * 수정자 아이디
	 */
	private final String XML_NODE_MODRID = "modrid";				// 
	/**
	 * 수집부서코드
	 */
	private final String XML_NODE_GATH_DEPT_CD = "gath_dept_cd";	// 
	/**
	 * 온에어 아이디
	 */
	private final String XML_NODE_MCUID = "mcuid";				// 
	/**
	 *  키프레임 콘텐츠 아이디		
	 */
	private final String XML_NODE_RPIMG_CT_ID = "rpimg_ct_id";    //
	/**
	 * 자료상태코드
	 */
	private final String XML_NODE_DATA_STAT_CD = "data_stat_cd";	// 
	/**
	 * 인제스트 일
	 */
	private final String XML_NODE_ING_REG_DD = "ing_reg_dd";		// 
	/**
	 * 부본 보관 여부
	 */
	private final String XML_NODE_COPY_KEEP = "copy_keep";		// 
	/**
	 * 클린본 여부
	 */
	private final String XML_NODE_CLEAN_KEEP = "clean_keep";		// 
	/**
	 * 음악정보
	 */
	private final String XML_NODE_MUSIC_INFO = "music_info";		// 
	/**
	 * 제한사항
	 */
	private final String XML_NODE_RST_CONT = "rst_cont";			// 
	/**
	 * 재방송
	 */
	private final String XML_NODE_RERUN = "rerun";				// 
	/**
	 * 검수자 아이디		
	 */
	private final String XML_NODE_ACCEPTOR_ID = "acceptor_id";	// 
	/**
	 * 부제
	 */
	private final String XML_NODE_SUB_TITLE = "sub_ttl";			// 
	/**
	 * 프로그램명
	 */
	private final String XML_NODE_PROGRAMNAME = "pgm_nm";			// 
	/**
	 *  미디어 코드
	 */
	private final String XML_NODE_MEDIACODE = "media_cd";			//
	/**
	 * 방송시작일
	 */
	private final String XML_NODE_BRDBEGINEDATE = "brd_bgn_dd";	// 
	/**
	 * 방송종료일
	 */
	private final String XML_NODE_BRDENDDATE = "brd_end_dd";		// 
	/**
	 * 편성명
	 */
	private final String XML_NODE_ARRANGE_NM = "arrange_nm";		// 
	/**
	 * lock 기능
	 */
	private final String XML_NODE_LOCK_STAT_CD = "lock_stat_cd";	// 
	/**
	 * 에러 기능
	 */
	private final String XML_NODE_ERROR_STAT_CD = "error_stat_cd";	// 
	/**
	 * 검수자명
	 */
	private final String XML_NODE_ACCEPTOR_NM = "acceptor_nm";	// 
	/**
	 * 녹음방식코드
	 */
	private final String XML_NODE_RECORD_TYPE_CD = "record_type_cd";	// 


	/**
	 * 오디오 유형코드
	 */
	private final String XML_NODE_AUD_TYPE_CD = "aud_type_cd";		// 
	/**
	 * ME분리코드
	 */
	private final String XML_NODE_ME_CD = "me_cd";	// 
	/**
	 * 색상코드
	 */
	private final String XML_NODE_COLOR_CD = "color_cd";	// 
	/**
	 * 컨텐츠ID
	 */
	private final String XML_NODE_CT_ID = "ct_id";	// 
	/**
	 * 화질코드
	 */
	private final String XML_NODE_VD_QLTY = "vd_qlty";	// 
	/**
	 * 화면비코드
	 */
	private final String XML_NODE_ASP_RTO_CD = "asp_rto_cd";	// 
	/**
	 * DTL 메타 복사 시 필요한 타켓 마스터 아이디
	 */
	private final String XML_NODE_TARGET_MASTER_ID = "target_master_id";    // 




	/**
	 * 관련영상마스터ID
	 */
	private final String XML_NODE_REL_MASTER_ID = "rel_master_id";	// 
	/**
	 * 관련영상방송일
	 */
	private final String XML_NODE_REL_BRD_DD = "rel_brd_dd";	// 
	/**
	 * 관련영상청구번호
	 */
	private final String XML_NODE_REL_REQ_CD = "rel_req_cd";	// 
	/**
	 * 관련영상 방송길이
	 */
	private final String XML_NODE_REL_BRD_LENG = "rel_brd_leng";	// 
	/**
	 * 관련영상 	부제
	 */
	private final String XML_NODE_REL_SUB_TTL = "rel_sub_ttl";    // 
	/**
	 * 관련영상 화질코드
	 */
	private final String XML_NODE_REL_ASP_RTO_CD = "rel_asp_rto_cd";    
	/**
	 * 관련영상 컨텐츠ID
	 */
	private final String XML_NODE_REL_REL_CT_ID = "rel_ct_id";    //
	/**
	 * 사용여부
	 */
	private final String XML_NODE_USE_YN = "use_yn";	// 사용여부
	/**
	 * 관련영상 방송일
	 */
	private final String XML_NODE_PGM_NM = "rel_brd_dd";	// 




	/**
	 * 체널코드
	 */
	private final String XML_NODE_CHAN_CD = "chan_cd";    //
	/**
	 * 파일럿 여부
	 */
	private final String XML_NODE_PILOT_YN = "pilot_yn";	// 사용여부


	/**
	 * 비트전송률
	 */
	private final String XML_NODE_BIT_RATE = "bit_rate";    
	/**
	 * 오디오 샘플링
	 */
	private final String XML_NODE_AUD_SAMP_FRQ = "aud_samp_frq";    //
	/**
	 * 오디오 비트레잇
	 */
	private final String XML_NODE_AUDIO_BDWT = "audio_bdwt";	// 사용여부
	/**
	 * 초당 전송률
	 */
	private final String XML_NODE_FRM_PER_SEC = "frm_per_sec";	// 
	/**
	 * 총키프레임
	 */
	private final String XML_NODE_TOT_FRAM = "tot_fram";	// 
	/**
	 * 가로 해상도
	 */
	private final String XML_NODE_HRESOL = "hresol";	// 
	/**
	 * 세로 해상도
	 */
	private final String XML_NODE_VRESOL = "vresol";	// 
	/**
	 * 컨텐츠 타입
	 */
	private final String XML_NODE_CT_CLA = "ct_cla";	// 
	/**
	 * 컨텐츠 유형
	 */
	private final String XML_NODE_CT_TYP = "ct_typ";	// 
	/**
	 * PDS PGMID
	 */
	private final String XML_NODE_PDS_CMS_PGM_ID = "META_PDS_CMS_PGM_ID";	// 
	/**
	 * 아티스트
	 */
	private final String XML_NODE_ARTIST = "artist";	// 
	/**
	 * 국가구분코드
	 */
	private final String XML_NODE_COUNTRY_CD = "country_cd";	// 


	/**
	 * 에러등록자id
	 */
	private final String XML_NODE_ERROR_ID = "ERROR_ID";	// 

	/**
	 * 메타데이터 성능개선용
	 */
	private final String META_MASTER_ID = "META_MASTER_ID";	
	private final String META_PGM_ID = "META_PGM_ID";	
	private final String PGM_PGM_CD = "PGM_PGM_CD";	
	private final String PGM_PGM_NM = "PGM_PGM_NM";	
	private final String PGM_BRD_BGN_DD = "PGM_BRD_BGN_DD";	
	private final String PGM_BRD_END_DD = "PGM_BRD_END_DD";	
	private final String PGM_MEDIA_CD = "PGM_MEDIA_CD";	
	private final String META_EPIS_NO = "META_EPIS_NO";	
	private final String PGM_CHAN_CD = "PGM_CHAN_CD";	
	private final String PGM_PILOT_YN = "PGM_PILOT_YN";	
	private final String TITLE = "TITLE";	
	private final String META_CTGR_L_CD = "META_CTGR_L_CD";	
	private final String META_CTGR_M_CD = "META_CTGR_M_CD";	
	private final String META_CTGR_S_CD = "META_CTGR_S_CD";	
	private final String META_BRD_DD = "META_BRD_DD";	
	private final String META_FINAL_BRD_YN = "META_FINAL_BRD_YN";	
	private final String META_SNPS = "META_SNPS";	
	private final String META_KEY_WORDS = "META_KEY_WORDS";	
	private final String META_BRD_BGN_HMS = "META_BRD_BGN_HMS";	
	private final String META_BRD_END_HMS = "META_BRD_END_HMS";	
	private final String META_BRD_LENG = "META_BRD_LENG";	
	private final String META_PGM_RATE = "META_PGM_RATE";	
	private final String META_DRT_NM = "META_DRT_NM";	
	private final String META_PRODUCER_NM = "META_PRODUCER_NM";	
	private final String META_WRITER_NM = "META_WRITER_NM";	
	private final String META_PRDT_IN_OUTS_CD = "META_PRDT_IN_OUTS_CD";	
	private final String META_PRDT_DEPT_CD = "META_PRDT_DEPT_CD";	
	private final String META_PRDT_DEPT_NM = "META_PRDT_DEPT_NM";	
	private final String META_ORG_PRDR_NM = "META_ORG_PRDR_NM";	
	private final String META_MC_NM = "META_MC_NM";	
	private final String META_CAST_NM = "META_CAST_NM";	
	private final String META_CMR_DRT_NM = "META_CMR_DRT_NM";	
	private final String META_FM_DT = "META_FM_DT";	
	private final String META_CMR_PLACE = "META_CMR_PLACE";	
	private final String META_SPC_INFO = "META_SPC_INFO";	
	private final String META_REQ_CD = "META_REQ_CD";	
	private final String META_SEC_ARCH_NM = "META_SEC_ARCH_NM";	
	private final String META_SEC_ARCH_ID = "META_SEC_ARCH_ID";	
	private final String META_GATH_CO_CD = "META_GATH_CO_CD";	
	private final String META_GATH_CLF_CD = "META_GATH_CLF_CD";	
	private final String META_ARCH_REG_DD = "META_ARCH_REG_DD";	
	private final String META_ARRG_END_DT = "META_ARRG_END_DT";	
	private final String META_WORK_PRIO_CD = "META_WORK_PRIO_CD";	
	private final String META_RSV_PRD_CD = "META_RSV_PRD_CD";	
	private final String META_CPRTR_NM = "META_CPRTR_NM";	
	private final String META_CPRT_TYPE = "META_CPRT_TYPE";	
	private final String META_CPRT_TYPE_DSC = "META_CPRT_TYPE_DSC";	
	private final String META_VIEW_GR_CD = "META_VIEW_GR_CD";	
	private final String META_DLBR_CD = "META_DLBR_CD";	
	private final String META_AWARD_HSTR = "META_AWARD_HSTR";	
	private final String META_TAPE_ID = "META_TAPE_ID";	
	private final String META_TAPE_ITEM_ID = "META_TAPE_ITEM_ID";	
	private final String META_TAPE_MEDIA_CLF_CD = "META_TAPE_MEDIA_CLF_CD";	
	private final String META_RSV_PRD_END_DD = "META_RSV_PRD_END_DD";	
	private final String META_DEL_DD = "META_DEL_DD";	
	private final String META_USE_YN = "META_USE_YN";	
	private final String META_REG_DT = "META_REG_DT";	
	private final String META_MODRID = "META_MODRID";	
	private final String META_MOD_DT = "META_MOD_DT";	
	private final String META_GATH_DEPT_CD = "META_GATH_DEPT_CD";	
	private final String META_MCUID = "META_MCUID";	
	private final String META_RPIMG_CT_ID = "META_RPIMG_CT_ID";	
	private final String META_RPIMG_KFRM_SEQ = "META_RPIMG_KFRM_SEQ";	
	private final String META_DATA_STAT_CD = "META_DATA_STAT_CD";	
	private final String META_ING_REG_DD = "META_ING_REG_DD";	
	private final String META_COPY_KEEP = "META_COPY_KEEP";	
	private final String META_CLEAN_KEEP = "META_CLEAN_KEEP";	
	private final String META_MUSIC_INFO = "META_MUSIC_INFO";	
	private final String META_RST_CONT = "META_RST_CONT";	
	private final String META_RERUN = "META_RERUN";	
	private final String META_ACCEPTOR_ID = "META_ACCEPTOR_ID";	
	private final String META_SUB_TTL = "META_SUB_TTL";	
	private final String META_ARRANGE_NM = "META_ARRANGE_NM";	
	private final String META_ACCEPTOR_NM = "META_ACCEPTOR_NM";	
	private final String META_SCENARIO_TITLE = "META_SCENARIO_TITLE";	
	private final String META_AUD_TYPE_CD = "META_AUD_TYPE_CD";	
	private final String META_RECORD_TYPE_CD = "META_RECORD_TYPE_CD";	
	private final String META_COLOR_CD = "META_COLOR_CD";	
	private final String META_ME_CD = "META_ME_CD";	
	private final String META_ASP_RTO_CD = "META_ASP_RTO_CD";	
	private final String META_VD_QLTY = "META_VD_QLTY";	
	private final String META_DURATION = "META_DURATION";	
	private final String META_CT_TYP = "META_CT_TYP";	
	private final String META_CT_CLA = "META_CT_CLA";	
	private final String META_AUDIO_BDWT = "META_AUDIO_BDWT";	
	private final String META_FRM_PER_SEC = "META_FRM_PER_SEC";	
	private final String META_AUD_SAMP_FRQ = "META_AUD_SAMP_FRQ";	
	private final String META_BIT_RT = "META_BIT_RT";	
	private final String META_HRESOL = "META_HRESOL";	
	private final String META_VD_HRESOL = "META_VD_HRESOL";	
	private final String META_VD_VRESOL = "META_VD_VRESOL";	
	private final String META_PREVIEW_SUBJ = "META_PREVIEW_SUBJ";	
	private final String META_ARCHIVE_REQID = "META_ARCHIVE_REQID";	
	private final String META_INGEST_EQ_ID = "META_INGEST_EQ_ID";	
	private final String META_PDS_CMS_PGM_ID = "META_PDS_CMS_PGM_ID";	
	private final String META_CT_ID = "META_CT_ID";	
	private final String META_STORAGE = "META_STORAGE";	
	private final String META_ARTIST = "META_ARTIST";	
	private final String META_COUNTRY_CD = "META_COUNTRY_CD";	
	private final String META_CHENNEL_CD = "META_CHENNEL_CD";	
	private final String META_COCD = "META_COCD";	
	private final String META_ANNOT_CLF_CD = "META_ANNOT_CLF_CD";	
	private final String META_ANNOT_CLF_DESC = "META_ANNOT_CLF_DESC";	


	public Object setDO(String _xml) {
		setDO(new MetadataMstInfoDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
		}

		return getDO();
	}

	public Object setData(Element pElement) {
		MetadataMstInfoDO infoDO = (MetadataMstInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_MASTER_ID)) {
				if (_nodeValue == null || _nodeValue.equals("")){
					_nodeValue = "0";
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}else{
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_ID)) {
				if (_nodeValue == null || _nodeValue.equals("")){
					_nodeValue = "0";
					infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));}
				else{
					infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_CD)) {
				infoDO.setPgmCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EPIS_NO)) {
				if (_nodeValue == null || _nodeValue.equals(""))
					_nodeValue = "0";
				infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));						
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_CD )) {
				infoDO.setCtgrLCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_M_CD )) {
				infoDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_S_CD )) {
				infoDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRD_DD)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FINAL_BRD_YN )) {
				infoDO.setFinalBrdYn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SNPS )) {
				infoDO.setSnps(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_KEY_WORDS)) {
				infoDO.setKeyWords(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRD_BGN_HMS)) {
				infoDO.setBrdBgnHms(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRD_END_HMS)) {
				infoDO.setBrdEndHms(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRD_LENG)) {
				infoDO.setBrdLeng(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_RATE  )) {
				infoDO.setPgmRate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DRT_NM )) {
				infoDO.setDrtNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRODUCER_NM  )) {
				infoDO.setProducerNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_WRITER_NM  )) {
				infoDO.setWriterNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_IN_OUTS_CD)) {
				infoDO.setPrdtInOutsCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_DEPT_CD)) {
				infoDO.setPrdtDeptCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_DEPT_NM)) {
				infoDO.setPrdtDeptNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ORG_PRDR_NM )) {
				infoDO.setOrgPrdrNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MC_NM )) {
				infoDO.setMcNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CAST_NM )) {
				infoDO.setCastNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CMR_DRT_NM)) {
				infoDO.setCmrDrtNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FM_DT)) {
				infoDO.setFmDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CMR_PLACE)) {
				infoDO.setCmrPlace(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SPC_INFO )) {
				infoDO.setSpcInfo(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_CD )) {
				infoDO.setReqCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEC_ARCH_NM )) {
				infoDO.setSecArchNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEC_ARCH_ID )) {
				infoDO.setSecArchId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GATH_CO_CD)) {
				infoDO.setGathCoCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GATH_CLF_CD)) {
				infoDO.setGathClfCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ARCH_REG_DD )) {
				infoDO.setArchRegDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ARRG_END_DT )) {
				infoDO.setArrgEndDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_WORK_PRIO_CD )) {
				infoDO.setWorkPrioCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RSV_PRD_CD)) {
				infoDO.setRsvPrdCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRTR_NM )) {
				infoDO.setCprtrNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRT_TYPE )) {
				infoDO.setCprtType(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRT_TYPE_DSC )) {
				infoDO.setCprtTypeDsc(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VIEW_GR_CD )) {
				infoDO.setViewGrCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DLBR_CD )) {
				infoDO.setDlbrCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_AWARD_HSTR )) {
				infoDO.setAwardHstr(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RPIMG_KFRM_SEQ )) {
				if (_nodeValue == null || _nodeValue.equals(""))
					_nodeValue = "0";
				infoDO.setRpimgKfrmSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TAPE_ID)) {
				infoDO.setTapeId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TAPE_ITEM_ID)) {
				infoDO.setTapeItemId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TAPE_MEDIA_CLF_CD)) {
				infoDO.setTapeMediaClfCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RSV_PRD_END_DD )) {
				infoDO.setRsvPrdEndDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DEL_DD )) {
				infoDO.setDelDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USEYN)) {
				infoDO.setUseYn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REG_DT)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REGRID)) {
				infoDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MOD_DT )) {
				infoDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MODRID )) {
				infoDO.setModrid(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GATH_DEPT_CD )) {
				infoDO.setGathDeptCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MCUID )) {
				infoDO.setMcuid(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RPIMG_CT_ID )) {
				if (_nodeValue == null || _nodeValue.equals(""))
					_nodeValue = "0";
				infoDO.setRpimgCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DATA_STAT_CD )) {
				infoDO.setDataStatCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ING_REG_DD )) {
				infoDO.setIngRegDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COPY_KEEP )) {
				infoDO.setCopyKeep(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CLEAN_KEEP )) {
				infoDO.setCleanKeep(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MUSIC_INFO )) {
				infoDO.setMusicInfo(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RST_CONT )) {
				infoDO.setRstCont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RERUN )) {
				infoDO.setRerun(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ACCEPTOR_ID )) {
				infoDO.setAcceptorId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SUB_TITLE )) {
				infoDO.setSubTtl(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PROGRAMNAME )) {
				infoDO.setPgmNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MEDIACODE )) {
				infoDO.setMediaCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRDBEGINEDATE )) {
				infoDO.setBrdBgnDd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRDENDDATE )) {
				infoDO.setBrdEndDd(_nodeValue);
			}						
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ARRANGE_NM )) {
				infoDO.setArrange_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_LOCK_STAT_CD )) {
				infoDO.setLock_stat_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ERROR_STAT_CD )) {
				infoDO.setError_stat_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ACCEPTOR_NM )) {
				infoDO.setAcceptorNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RECORD_TYPE_CD)) {
				infoDO.setRecord_type_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TARGET_MASTER_ID)) {
				//if (_nodeValue == null || _nodeValue.equals(""))
					//_nodeValue = "0";
				infoDO.setTarget_master_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_AUD_TYPE_CD )) {
				infoDO.setAud_type_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ME_CD )) {
				infoDO.setMe_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COLOR_CD )) {
				infoDO.setColor_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_ID )) {
				infoDO.setCt_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VD_QLTY)) {
				infoDO.setVd_qlty(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ASP_RTO_CD)) {

				infoDO.setAsp_rto_cd(_nodeValue);
			}

			/*		
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_MASTER_ID)) {

							infoDO.setRel_masterId(Long.parseLong(_nodeValue));
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_BRD_DD )) {
						infoDO.setRel_brd_dd(_nodeValue);
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_REQ_CD )) {
						infoDO.setRel_req_dd(_nodeValue);
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_BRD_LENG )) {
						infoDO.setRel_brd_leng(_nodeValue);
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_SUB_TTL )) {
						infoDO.setRel_sub_ttl(_nodeValue);
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_ASP_RTO_CD)) {
						infoDO.setRel_asp_rto_cd(_nodeValue);
					}
					else if(_nodeName.equalsIgnoreCase(XML_NODE_REL_REL_CT_ID)) {

							infoDO.setRel_ct_id(Integer.parseInt(_nodeValue));
					}*/
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USE_YN)) {

				infoDO.setUse_yn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHAN_CD )) {
				infoDO.setChan_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PILOT_YN)) {
				infoDO.setPilot_yn(_nodeValue);
			}				

			else if(_nodeName.equalsIgnoreCase(XML_NODE_BIT_RATE)) {
				infoDO.setBit_rate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_AUD_SAMP_FRQ)) {

				infoDO.setAud_samp_frq(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_AUDIO_BDWT)) {

				infoDO.setAudio_bdwt(_nodeValue);
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_FRM_PER_SEC )) {
				infoDO.setFrm_per_sec(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TOT_FRAM)) {
				infoDO.setTot_fram(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_HRESOL)) {

				infoDO.setHresol(_nodeValue.replace("*", ","));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_CLA)) {

				infoDO.setCt_cla(_nodeValue);
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_TYP )) {
				infoDO.setCt_typ(_nodeValue);
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_PDS_CMS_PGM_ID )) {
				infoDO.setPds_cms_id(_nodeValue);
			}	
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ARTIST )) {
				infoDO.setArtist(_nodeValue);
			}	
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COUNTRY_CD )) {
				infoDO.setCountry_cd(_nodeValue);
			}	else if(_nodeName.equalsIgnoreCase(XML_NODE_ERROR_ID )) {
				infoDO.setError_id(_nodeValue);
			}
		}
		return infoDO;
	}


	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}

	public String getSubXML() {
		MetadataMstInfoDO infoDO = (MetadataMstInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMasterId() + "</" + XML_NODE_MASTER_ID + ">");
		_xml.append("<" + XML_NODE_PGM_ID + ">" + infoDO.getPgmId() + "</" + XML_NODE_PGM_ID + ">");
		_xml.append("<" + XML_NODE_PGM_CD + ">" + infoDO.getPgmCd() + "</" + XML_NODE_PGM_CD + ">");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis_no() + "</" + XML_NODE_EPIS_NO + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())  + "</" + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgrLCd()  + "</" +  XML_NODE_CTGR_L_CD+ ">");
		_xml.append("<" + XML_NODE_CTGR_M_CD + ">" + infoDO.getCtgrMCd() + "</" +  XML_NODE_CTGR_M_CD+ ">");
		_xml.append("<" + XML_NODE_CTGR_S_CD + ">" + infoDO.getCtgrSCd() + "</" +  XML_NODE_CTGR_S_CD+ ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrdDd()  + "</" + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_FINAL_BRD_YN + ">" + infoDO.getFinalBrdYn()  + "</" +  XML_NODE_FINAL_BRD_YN+ ">");
		_xml.append("<" + XML_NODE_SNPS + ">" + CommonUtl.transXmlText(infoDO.getSnps())  + "</" +  XML_NODE_SNPS+ ">");
		_xml.append("<" + XML_NODE_KEY_WORDS + ">" + CommonUtl.transXmlText(infoDO.getKeyWords())  + "</" + XML_NODE_KEY_WORDS + ">");
		_xml.append("<" + XML_NODE_BRD_BGN_HMS + ">" + infoDO.getBrdBgnHms()  + "</" + XML_NODE_BRD_BGN_HMS + ">");
		_xml.append("<" + XML_NODE_BRD_END_HMS + ">" + infoDO.getBrdEndHms()  + "</" + XML_NODE_BRD_END_HMS + ">");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrdLeng()  + "</" + XML_NODE_BRD_LENG + ">");
		_xml.append("<" + XML_NODE_PGM_RATE + ">" + infoDO.getPgmRate() + "</" +  XML_NODE_PGM_RATE+ ">");
		_xml.append("<" + XML_NODE_DRT_NM + ">" + CommonUtl.transXmlText(infoDO.getDrtNm()) + "</" +  XML_NODE_DRT_NM+ ">");
		_xml.append("<" + XML_NODE_PRODUCER_NM + ">" + CommonUtl.transXmlText(infoDO.getProducerNm()) + "</" +  XML_NODE_PRODUCER_NM+ ">");
		_xml.append("<" + XML_NODE_WRITER_NM + ">" + CommonUtl.transXmlText(infoDO.getWriterNm()) + "</" +  XML_NODE_WRITER_NM+ ">");
		_xml.append("<" + XML_NODE_PRDT_IN_OUTS_CD + ">" + infoDO.getPrdtInOutsCd() + "</" + XML_NODE_PRDT_IN_OUTS_CD + ">");
		_xml.append("<" + XML_NODE_PRDT_DEPT_CD + ">" + infoDO.getPrdtDeptCd() + "</" + XML_NODE_PRDT_DEPT_CD + ">");
		_xml.append("<" + XML_NODE_PRDT_DEPT_NM + ">" + CommonUtl.transXmlText(infoDO.getPrdtDeptNm()) + "</" + XML_NODE_PRDT_DEPT_NM + ">");
		_xml.append("<" + XML_NODE_ORG_PRDR_NM + ">" + CommonUtl.transXmlText(infoDO.getOrgPrdrNm()) + "</" +  XML_NODE_ORG_PRDR_NM+ ">");
		_xml.append("<" + XML_NODE_MC_NM + ">" + CommonUtl.transXmlText(infoDO.getMcNm()) + "</" +  XML_NODE_MC_NM+ ">");
		_xml.append("<" + XML_NODE_CAST_NM + ">" + CommonUtl.transXmlText(infoDO.getCastNm()) + "</" +  XML_NODE_CAST_NM+ ">");
		_xml.append("<" + XML_NODE_CMR_DRT_NM + ">" + CommonUtl.transXmlText(infoDO.getCmrDrtNm()) + "</" + XML_NODE_CMR_DRT_NM + ">");
		_xml.append("<" + XML_NODE_FM_DT + ">" + infoDO.getFmDt() + "</" + XML_NODE_FM_DT + ">");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">" + CommonUtl.transXmlText(infoDO.getCmrPlace()) + "</" + XML_NODE_CMR_PLACE + ">");
		_xml.append("<" + XML_NODE_SPC_INFO + ">" + CommonUtl.transXmlText(infoDO.getSpcInfo()) + "</" +  XML_NODE_SPC_INFO+ ">");
		_xml.append("<" + XML_NODE_REQ_CD + ">" + infoDO.getReqCd() + "</" + XML_NODE_REQ_CD + ">");
		_xml.append("<" + XML_NODE_SEC_ARCH_NM + ">" + CommonUtl.transXmlText(infoDO.getSecArchNm()) + "</" +  XML_NODE_SEC_ARCH_NM+ ">");
		_xml.append("<" + XML_NODE_SEC_ARCH_ID + ">" + infoDO.getSecArchId() + "</" +  XML_NODE_SEC_ARCH_ID+ ">");
		_xml.append("<" + XML_NODE_GATH_CO_CD + ">" + infoDO.getGathCoCd() + "</" + XML_NODE_GATH_CO_CD + ">");
		_xml.append("<" + XML_NODE_GATH_CLF_CD + ">" + infoDO.getGathClfCd() + "</" + XML_NODE_GATH_CLF_CD + ">");
		_xml.append("<" + XML_NODE_ARCH_REG_DD + ">" + infoDO.getArchRegDd() + "</" +  XML_NODE_ARCH_REG_DD+ ">");
		_xml.append("<" + XML_NODE_ARRG_END_DT + ">" + infoDO.getArrgEndDt() + "</" +  XML_NODE_ARRG_END_DT+ ">");
		_xml.append("<" + XML_NODE_WORK_PRIO_CD + ">" + infoDO.getWorkPrioCd() + "</" +  XML_NODE_WORK_PRIO_CD+ ">");
		_xml.append("<" + XML_NODE_RSV_PRD_CD + ">" + infoDO.getRsvPrdCd() + "</" + XML_NODE_RSV_PRD_CD + ">");
		_xml.append("<" + XML_NODE_CPRTR_NM + ">" + CommonUtl.transXmlText(infoDO.getCprtrNm()) + "</" + XML_NODE_CPRTR_NM+ ">");
		_xml.append("<" + XML_NODE_CPRT_TYPE + ">" + infoDO.getCprtType() + "</" +  XML_NODE_CPRT_TYPE+ ">");
		_xml.append("<" + XML_NODE_CPRT_TYPE_DSC + ">" +CommonUtl.transXmlText( infoDO.getCprtTypeDsc()) + "</" +  XML_NODE_CPRT_TYPE_DSC+ ">");
		_xml.append("<" + XML_NODE_VIEW_GR_CD + ">" + infoDO.getViewGrCd() + "</" +  XML_NODE_VIEW_GR_CD+ ">");
		_xml.append("<" + XML_NODE_DLBR_CD + ">" + infoDO.getDlbrCd() + "</" +  XML_NODE_DLBR_CD+ ">");
		_xml.append("<" + XML_NODE_AWARD_HSTR + ">" + CommonUtl.transXmlText(infoDO.getAwardHstr()) + "</" +  XML_NODE_AWARD_HSTR+ ">");
		_xml.append("<" + XML_NODE_RPIMG_KFRM_SEQ + ">" + infoDO.getRpimgKfrmSeq() + "</" +  XML_NODE_RPIMG_KFRM_SEQ+ ">");
		_xml.append("<" + XML_NODE_TAPE_ID + ">" + infoDO.getTapeId() + "</" + XML_NODE_TAPE_ID + ">"); 
		_xml.append("<" + XML_NODE_TAPE_ITEM_ID + ">" + infoDO.getTapeItemId()  + "</" + XML_NODE_TAPE_ITEM_ID + ">");
		_xml.append("<" + XML_NODE_TAPE_MEDIA_CLF_CD + ">" + infoDO.getTapeMediaClfCd() + "</" + XML_NODE_TAPE_MEDIA_CLF_CD + ">");
		_xml.append("<" + XML_NODE_RSV_PRD_END_DD + ">" + infoDO.getRsvPrdEndDd() + "</" +  XML_NODE_RSV_PRD_END_DD+ ">");
		_xml.append("<" + XML_NODE_DEL_DD + ">" + infoDO.getDelDd() + "</" +  XML_NODE_DEL_DD+ ">");
		_xml.append("<" + XML_NODE_USEYN + ">" + infoDO.getUseYn() + "</" + XML_NODE_USEYN + ">");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getRegDt() + "</" + XML_NODE_REG_DT + ">");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getRegrid() + "</" + XML_NODE_REGRID + ">");
		_xml.append("<" + XML_NODE_MOD_DT + ">" + infoDO.getModDt() + "</" +  XML_NODE_MOD_DT+ ">");
		_xml.append("<" + XML_NODE_MODRID + ">" + infoDO.getModrid() + "</" +  XML_NODE_MODRID+ ">");
		_xml.append("<" + XML_NODE_GATH_DEPT_CD + ">" + infoDO.getGathDeptCd() + "</" +  XML_NODE_GATH_DEPT_CD+ ">");
		_xml.append("<" + XML_NODE_MCUID + ">" + infoDO.getMcuid() + "</" +  XML_NODE_MCUID+ ">");
		_xml.append("<" + XML_NODE_RPIMG_CT_ID + ">" + infoDO.getRpimgCtId() + "</" +  XML_NODE_RPIMG_CT_ID+ ">");
		_xml.append("<" + XML_NODE_DATA_STAT_CD + ">" + infoDO.getDataStatCd() + "</" +  XML_NODE_DATA_STAT_CD+ ">");
		_xml.append("<" + XML_NODE_ING_REG_DD + ">" + infoDO.getIngRegDd() + "</" +  XML_NODE_ING_REG_DD+ ">");
		_xml.append("<" + XML_NODE_COPY_KEEP + ">" + infoDO.getCopyKeep() + "</" +  XML_NODE_COPY_KEEP+ ">");
		_xml.append("<" + XML_NODE_CLEAN_KEEP + ">" + infoDO.getCleanKeep() + "</" +  XML_NODE_CLEAN_KEEP+ ">");
		_xml.append("<" + XML_NODE_MUSIC_INFO + ">" + CommonUtl.transXmlText(infoDO.getMusicInfo()) + "</" +  XML_NODE_MUSIC_INFO+ ">");
		_xml.append("<" + XML_NODE_RST_CONT + ">" + CommonUtl.transXmlText(infoDO.getRstCont()) + "</" +   XML_NODE_RST_CONT+ ">");
		_xml.append("<" + XML_NODE_RERUN + ">" + CommonUtl.transXmlText(infoDO.getRerun()) + "</" +  XML_NODE_RERUN+ ">");
		_xml.append("<" + XML_NODE_ACCEPTOR_ID + ">" + infoDO.getAcceptorId() + "</" +  XML_NODE_ACCEPTOR_ID+ ">");
		_xml.append("<" + XML_NODE_SUB_TITLE + ">" + CommonUtl.transXmlText(infoDO.getSubTtl()) + "</" +  XML_NODE_SUB_TITLE+ ">");
		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</" +   XML_NODE_PROGRAMNAME + ">");
		_xml.append("<" + XML_NODE_MEDIACODE + ">" + infoDO.getmediaCd() + "</" +  XML_NODE_MEDIACODE + ">");
		_xml.append("<" + XML_NODE_BRDBEGINEDATE + ">" + infoDO.getBrdBgnDd() + "</" +  XML_NODE_BRDBEGINEDATE + ">");
		_xml.append("<" + XML_NODE_BRDENDDATE + ">" + infoDO.getBrdEndDd() + "</" +  XML_NODE_BRDENDDATE+ ">");
		_xml.append("<" + XML_NODE_ARRANGE_NM + ">" + CommonUtl.transXmlText(infoDO.getArrange_nm()) + "</" +  XML_NODE_ARRANGE_NM + ">");
		_xml.append("<" + XML_NODE_LOCK_STAT_CD + ">" + infoDO.getLock_stat_cd() + "</" +  XML_NODE_LOCK_STAT_CD + ">");
		_xml.append("<" + XML_NODE_ERROR_STAT_CD + ">" + infoDO.getError_stat_cd() + "</" +  XML_NODE_ERROR_STAT_CD + ">");
		_xml.append("<" + XML_NODE_ACCEPTOR_NM + ">" + CommonUtl.transXmlText(infoDO.getAcceptorNm()) + "</" +  XML_NODE_ACCEPTOR_NM + ">");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">" + infoDO.getRecord_type_cd() + "</" +  XML_NODE_RECORD_TYPE_CD + ">");
		_xml.append("<" + XML_NODE_AUD_TYPE_CD + ">" + infoDO.getAud_type_cd() + "</" +  XML_NODE_AUD_TYPE_CD+ ">");
		_xml.append("<" + XML_NODE_ME_CD + ">" + infoDO.getMe_cd() + "</" +  XML_NODE_ME_CD + ">");
		_xml.append("<" + XML_NODE_COLOR_CD + ">" + infoDO.getColor_cd() + "</" +  XML_NODE_COLOR_CD + ">");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</" +  XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + infoDO.getVd_qlty() + "</" +  XML_NODE_VD_QLTY + ">");
		_xml.append("<" + XML_NODE_ASP_RTO_CD + ">" + infoDO.getAsp_rto_cd() + "</" +  XML_NODE_ASP_RTO_CD + ">");
		_xml.append("<" + XML_NODE_PDS_CMS_PGM_ID + ">" + infoDO.getPds_cms_id() + "</" +  XML_NODE_PDS_CMS_PGM_ID + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


	/*

	public String getMetaXML() {
		MetadataMstInfoDO infoDO = (MetadataMstInfoDO)getDO();

		String _xml ="<" + META_MASTER_ID + ">" + infoDO.getMasterId() + "</" + META_MASTER_ID + ">" ;
		_xml = _xml + "<" + META_PGM_ID + ">" + infoDO.getPgmId() + "</" + META_PGM_ID + ">" ;

		_xml = _xml + "<" + PGM_PGM_CD + ">" + infoDO.getPgmCd() + "</" + PGM_PGM_CD + ">" ;

		_xml = _xml + "<" + PGM_PGM_NM + ">" + infoDO.getEpis_no() + "</" + PGM_PGM_NM + ">" ;

		_xml = _xml + "<" + PGM_BRD_BGN_DD + ">" + infoDO.getTitle()  + "</" + PGM_BRD_BGN_DD + ">" ;

		_xml = _xml + "<" + PGM_BRD_END_DD + ">" + infoDO.getCtgrLCd()  + "</" +  PGM_BRD_END_DD+ ">" ;

		_xml = _xml + "<" + PGM_MEDIA_CD + ">" + infoDO.getCtgrMCd() + "</" +  PGM_MEDIA_CD+ ">" ;
		_xml = _xml + "<" + META_EPIS_NO + ">" + infoDO.getCtgrSCd() + "</" +  META_EPIS_NO+ ">" ;

		_xml = _xml + "<" + PGM_CHAN_CD + ">" + infoDO.getBrdDd()  + "</" + PGM_CHAN_CD + ">" ;

		_xml = _xml + "<" + PGM_PILOT_YN + ">" + infoDO.getFinalBrdYn()  + "</" +  PGM_PILOT_YN+ ">";

		_xml = _xml + "<" + XML_NODE_SNPS + ">" + infoDO.getSnps()  + "</" +  XML_NODE_SNPS+ ">" ;

		_xml = _xml + "<" + TITLE + ">" + infoDO.getKeyWords()  + "</" + TITLE + ">" ;

		_xml = _xml + "<" + META_CTGR_L_CD + ">" + infoDO.getBrdBgnHms()  + "</" + META_CTGR_L_CD + ">" ;

		_xml = _xml + "<" + META_CTGR_M_CD + ">" + infoDO.getBrdEndHms()  + "</" + META_CTGR_M_CD + ">" ;

		_xml = _xml + "<" + META_CTGR_S_CD + ">" + infoDO.getBrdLeng()  + "</" + META_CTGR_S_CD + ">" ;

		_xml = _xml + "<" + META_BRD_DD + ">" + infoDO.getPgmRate() + "</" +  META_BRD_DD+ ">" ;

		_xml = _xml + "<" + META_FINAL_BRD_YN + ">" + infoDO.getDrtNm() + "</" +  META_FINAL_BRD_YN+ ">" ;

		_xml = _xml + "<" + META_SNPS + ">" + infoDO.getProducerNm() + "</" +  META_SNPS+ ">" ;

		_xml = _xml + "<" + META_KEY_WORDS + ">" + infoDO.getWriterNm() + "</" +  META_KEY_WORDS+ ">" ;

		_xml = _xml + "<" + META_BRD_BGN_HMS + ">" + infoDO.getPrdtInOutsCd() + "</" + META_BRD_BGN_HMS + ">" ;

		_xml = _xml + "<" + META_BRD_END_HMS + ">" + infoDO.getPrdtDeptCd() + "</" + META_BRD_END_HMS + ">" ;

		_xml = _xml + "<" + META_BRD_LENG + ">" + infoDO.getPrdtDeptNm() + "</" + META_BRD_LENG + ">" ;

		_xml = _xml + "<" + META_PGM_RATE + ">" + infoDO.getOrgPrdrNm() + "</" +  META_PGM_RATE+ ">" ;

		_xml = _xml + "<" + META_DRT_NM + ">" + infoDO.getMcNm() + "</" +  META_DRT_NM+ ">" ;

		_xml = _xml + "<" + META_PRODUCER_NM + ">" + infoDO.getCastNm() + "</" +  META_PRODUCER_NM+ ">" ;

		_xml = _xml + "<" + META_WRITER_NM + ">" + infoDO.getCmrDrtNm() + "</" + META_WRITER_NM + ">" ;

		_xml = _xml + "<" + META_PRDT_IN_OUTS_CD + ">" + infoDO.getFmDt() + "</" + META_PRDT_IN_OUTS_CD + ">" ;

		_xml = _xml + "<" + META_PRDT_DEPT_CD + ">" + infoDO.getCmrPlace() + "</" + META_PRDT_DEPT_CD + ">" ;

		_xml = _xml + "<" + META_PRDT_DEPT_NM + ">" + infoDO.getSpcInfo() + "</" +  META_PRDT_DEPT_NM+ ">" ;

		_xml = _xml + "<" + META_ORG_PRDR_NM + ">" + infoDO.getReqCd() + "</" + META_ORG_PRDR_NM + ">" ;

		_xml = _xml + "<" + META_MC_NM + ">" + infoDO.getSecArchNm() + "</" +  META_MC_NM+ ">" ;

		_xml = _xml + "<" + META_CAST_NM + ">" + infoDO.getSecArchId() + "</" +  META_CAST_NM+ ">" ;

		_xml = _xml + "<" + META_CMR_DRT_NM + ">" + infoDO.getGathCoCd() + "</" + META_CMR_DRT_NM + ">" ;

		_xml = _xml + "<" + META_FM_DT + ">" + infoDO.getGathClfCd() + "</" + META_FM_DT + ">" ;

		_xml = _xml + "<" + META_CMR_PLACE + ">" + infoDO.getArchRegDd() + "</" +  META_CMR_PLACE+ ">" ;

		_xml = _xml + "<" + META_SPC_INFO + ">" + infoDO.getArrgEndDt() + "</" +  META_SPC_INFO+ ">" ;

		_xml = _xml + "<" + META_REQ_CD + ">" + infoDO.getWorkPrioCd() + "</" +  META_REQ_CD+ ">" ;

		_xml = _xml + "<" + META_SEC_ARCH_NM + ">" + infoDO.getRsvPrdCd() + "</" + META_SEC_ARCH_NM + ">" ;

		_xml = _xml + "<" + META_SEC_ARCH_ID + ">" + infoDO.getCprtrNm() + "</" + META_SEC_ARCH_ID+ ">" ;

		_xml = _xml + "<" + META_GATH_CO_CD + ">" + infoDO.getCprtType() + "</" +  META_GATH_CO_CD+ ">" ;

		_xml = _xml + "<" + META_GATH_CLF_CD + ">" + infoDO.getCprtTypeDsc() + "</" +  META_GATH_CLF_CD+ ">";

		_xml = _xml + "<" + XML_NODE_VIEW_GR_CD + ">" + infoDO.getViewGrCd() + "</" +  XML_NODE_VIEW_GR_CD+ ">" ;

		_xml = _xml + "<" + XML_NODE_DLBR_CD + ">" + infoDO.getDlbrCd() + "</" +  XML_NODE_DLBR_CD+ ">" ;

		_xml = _xml + "<" + XML_NODE_AWARD_HSTR + ">" + infoDO.getAwardHstr() + "</" +  XML_NODE_AWARD_HSTR+ ">" ;

		_xml = _xml + "<" + XML_NODE_RPIMG_KFRM_SEQ + ">" + infoDO.getRpimgKfrmSeq() + "</" +  XML_NODE_RPIMG_KFRM_SEQ+ ">" ;

		_xml = _xml + "<" + XML_NODE_TAPE_ID + ">" + infoDO.getTapeId() + "</" + XML_NODE_TAPE_ID + ">" ; 

		_xml = _xml + "<" + XML_NODE_TAPE_ITEM_ID + ">" + infoDO.getTapeItemId()  + "</" + XML_NODE_TAPE_ITEM_ID + ">";

		_xml = _xml + "<" + XML_NODE_TAPE_MEDIA_CLF_CD + ">" + infoDO.getTapeMediaClfCd() + "</" + XML_NODE_TAPE_MEDIA_CLF_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_RSV_PRD_END_DD + ">" + infoDO.getRsvPrdEndDd() + "</" +  XML_NODE_RSV_PRD_END_DD+ ">" ;

		_xml = _xml + "<" + XML_NODE_DEL_DD + ">" + infoDO.getDelDd() + "</" +  XML_NODE_DEL_DD+ ">" ;

		_xml = _xml + "<" + XML_NODE_USEYN + ">" + infoDO.getUseYn() + "</" + XML_NODE_USEYN + ">" ;

		_xml = _xml + "<" + XML_NODE_REG_DT + ">" + infoDO.getRegDt() + "</" + XML_NODE_REG_DT + ">" ;

		_xml = _xml + "<" + XML_NODE_REGRID + ">" + infoDO.getRegrid() + "</" + XML_NODE_REGRID + ">";

		_xml = _xml + "<" + XML_NODE_MOD_DT + ">" + infoDO.getModDt() + "</" +  XML_NODE_MOD_DT+ ">" ;

		_xml = _xml + "<" + XML_NODE_MODRID + ">" + infoDO.getModrid() + "</" +  XML_NODE_MODRID+ ">" ;

		_xml = _xml + "<" + XML_NODE_GATH_DEPT_CD + ">" + infoDO.getGathDeptCd() + "</" +  XML_NODE_GATH_DEPT_CD+ ">" ;

		_xml = _xml + "<" + XML_NODE_MCUID + ">" + infoDO.getMcuid() + "</" +  XML_NODE_MCUID+ ">" ;

		_xml = _xml + "<" + XML_NODE_RPIMG_CT_ID + ">" + infoDO.getRpimgCtId() + "</" +  XML_NODE_RPIMG_CT_ID+ ">" ;

		_xml = _xml + "<" + XML_NODE_DATA_STAT_CD + ">" + infoDO.getDataStatCd() + "</" +  XML_NODE_DATA_STAT_CD+ ">" ;

		_xml = _xml + "<" + XML_NODE_ING_REG_DD + ">" + infoDO.getIngRegDd() + "</" +  XML_NODE_ING_REG_DD+ ">" ;

		_xml = _xml + "<" + XML_NODE_COPY_KEEP + ">" + infoDO.getCopyKeep() + "</" +  XML_NODE_COPY_KEEP+ ">" ;

		_xml = _xml + "<" + XML_NODE_CLEAN_KEEP + ">" + infoDO.getCleanKeep() + "</" +  XML_NODE_CLEAN_KEEP+ ">" ;

		_xml = _xml + "<" + XML_NODE_MUSIC_INFO + ">" + infoDO.getMusicInfo() + "</" +  XML_NODE_MUSIC_INFO+ ">" ;

		_xml = _xml + "<" + XML_NODE_RST_CONT + ">" + infoDO.getRstCont() + "</" +   XML_NODE_RST_CONT+ ">" ;

		_xml = _xml + "<" + XML_NODE_RERUN + ">" + infoDO.getRerun() + "</" +  XML_NODE_RERUN+ ">" ;

		_xml = _xml + "<" + XML_NODE_ACCEPTOR_ID + ">" + infoDO.getAcceptorId() + "</" +  XML_NODE_ACCEPTOR_ID+ ">" ;

		_xml = _xml + "<" + XML_NODE_SUB_TITLE + ">" + infoDO.getSubTtl() + "</" +  XML_NODE_SUB_TITLE+ ">" ;

		_xml = _xml + "<" + XML_NODE_PROGRAMNAME + ">" + infoDO.getPgmNm() + "</" +   XML_NODE_PROGRAMNAME + ">" ;

		_xml = _xml + "<" + XML_NODE_MEDIACODE + ">" + infoDO.getmediaCd() + "</" +  XML_NODE_MEDIACODE + ">" ;

		_xml = _xml + "<" + XML_NODE_BRDBEGINEDATE + ">" + infoDO.getBrdBgnDd() + "</" +  XML_NODE_BRDBEGINEDATE + ">" ;

		_xml = _xml + "<" + XML_NODE_BRDENDDATE + ">" + infoDO.getBrdEndDd() + "</" +  XML_NODE_BRDENDDATE+ ">" ;

		_xml = _xml + "<" + XML_NODE_ARRANGE_NM + ">" + infoDO.getArrange_nm() + "</" +  XML_NODE_ARRANGE_NM + ">" ;

		_xml = _xml + "<" + XML_NODE_LOCK_STAT_CD + ">" + infoDO.getLock_stat_cd() + "</" +  XML_NODE_LOCK_STAT_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_ERROR_STAT_CD + ">" + infoDO.getError_stat_cd() + "</" +  XML_NODE_ERROR_STAT_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_ACCEPTOR_NM + ">" + infoDO.getAcceptorNm() + "</" +  XML_NODE_ACCEPTOR_NM + ">" ;

		_xml = _xml + "<" + XML_NODE_RECORD_TYPE_CD + ">" + infoDO.getRecord_type_cd() + "</" +  XML_NODE_RECORD_TYPE_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_AUD_TYPE_CD + ">" + infoDO.getAud_type_cd() + "</" +  XML_NODE_AUD_TYPE_CD+ ">" ;

		_xml = _xml + "<" + XML_NODE_ME_CD + ">" + infoDO.getMe_cd() + "</" +  XML_NODE_ME_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_COLOR_CD + ">" + infoDO.getColor_cd() + "</" +  XML_NODE_COLOR_CD + ">" ;

		_xml = _xml + "<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</" +  XML_NODE_CT_ID + ">" ;

		_xml = _xml + "<" + XML_NODE_VD_QLTY + ">" + infoDO.getVd_qlty() + "</" +  XML_NODE_VD_QLTY + ">" ;

		_xml = _xml + "<" + XML_NODE_ASP_RTO_CD + ">" + infoDO.getAsp_rto_cd() + "</" +  XML_NODE_ASP_RTO_CD + ">" ;

		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return _xml;
	}*/


}

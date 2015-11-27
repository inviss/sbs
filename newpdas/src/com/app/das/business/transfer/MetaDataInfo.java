package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.app.das.util.AdaptorCDATA;

@XmlRootElement(name="meta")
@XmlAccessorType(XmlAccessType.FIELD)
public class MetaDataInfo {

	@XmlElement(name="META_MASTER_ID")
	private Long		masterId;				// BIGINT				// 마스터ID
	@XmlElement(name="META_PGM_ID")
	private Long		pgmId;					// BIGINT				// 프로그램ID
	@XmlElement(name="PGM_PGM_CD")
	private String		pgmCd;					// CHAR(8)				// 프로그램코드
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="PGM_PGM_NM")
	private String      pgmNm;
	@XmlElement(name="META_EPIS_NO")
	private String		episNo;					// INTEGER				// 회차
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="TITLE")
	private String 		title;					// VARCHAR(150)			// 타이틀
	@XmlElement(name="META_CTGR_L_CD")
	private String		ctgrLCd;				// CHAR(3)				// 대분류코드
	@XmlElement(name="META_CTGR_M_CD")
	private String		ctgrMCd;				// CHAR(3)				// 중분류코드
	@XmlElement(name="META_CTGR_S_CD")
	private String		ctgrSCd;				// CHAR(3)				// 소분류코드 
	@XmlElement(name="META_BRD_DD")
	private String		brdDd;					// CHAR(8)				// 방송일자
	@XmlElement(name="META_FINAL_BRD_YN")
	private String		finalBrdYn;			// CHAR(1)				// 최종방송여부
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_SNPS")
	private String		snps;					// LONGVARCHAR			// 시놉시스
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_KEY_WORDS")
	private String		keyWords;				// LONGVARCHAR			// 키워드
	@XmlElement(name="META_BRD_BGN_HMS")
	private String		brdBgnHms;				// CHAR(14)				// 방송시작시간
	@XmlElement(name="META_BRD_END_HMS")
	private String		brdEndHms;				// CHAR(14)				// 방송종료시간
	@XmlElement(name="META_BRD_LENG")
	private String		brdLeng;				// CHAR(6) 				// 방송길이
	@XmlElement(name="META_PGM_RATE")
	private String		pgmRate;				// DECIMAL(4, 2)		// 시청률
	@XmlElement(name="META_DRT_NM")
	private String		drtNm;					// VARCHAR(300)			// 연출자명
	@XmlElement(name="META_PRODUCER_NM")
	private String		producerNm;			// VARCHAR(300)			// 프로듀서명
	@XmlElement(name="META_WRITER_NM")
	private String		writerNm;				// VARCHAR(300)			// 작가명
	@XmlElement(name="META_PRDT_IN_OUTS_CD")
	private String		prdtInOutsCd;			// CHAR(3)  			// 제작구분코드
	@XmlElement(name="META_PRDT_DEPT_CD")
	private String		prdtDeptCd;			// VARCHAR(6)			// 제작부서코
	@XmlElement(name="META_PRDT_DEPT_NM")
	private String 		prdtDeptNm;			// VARCHAR(90)			// 제작부서명칭
	@XmlElement(name="META_ORG_PRDR_NM")
	private String		orgPrdrNm;				// VARCHAR(90)			// 원제작사명
	@XmlElement(name="META_MC_NM")
	private String		mcNm;					// VARCHAR(300)			// 진행자명
	@XmlElement(name="META_CAST_NM")
	private String 		castNm;				// LONG VARCHAR			// 출연자명
	@XmlElement(name="META_CMR_DRT_NM")
	private String		cmrDrtNm;				// VARCHAR(30)			// 촬영감독명
	@XmlElement(name="META_FM_DT")
	private String		fmDt;					// CHAR(8) 				// 촬영일
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_CMR_PLACE")
	private String		cmrPlace;				// VARCHAR(300)			// 촬영장소
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_SPC_INFO")
	private String		spcInfo;				// LONGVARCHAR			// 특이사항
	@XmlElement(name="META_REQ_CD")
	private String		reqCd;					// CHAR(9)				// 청구번호코드
	@XmlElement(name="META_SEC_ARCH_NM")
	private String		secArchNm;				// VARCHAR(30)			// 2차아카이브명
	@XmlElement(name="META_SEC_ARCH_ID")
	private String		secArchId;				// VARCHAR(15)			// 2차아카이버ID
	@XmlElement(name="META_GATH_CO_CD")
	private String		gathCoCd;				// CHAR(3)				// 수집처코드
	@XmlElement(name="META_GATH_CLF_CD")
	private String		gathClfCd;				// CHAR(3)				// 수집구분코드
	@XmlElement(name="META_ARCH_REG_DD")
	private String		archRegDd;				// CHAR(14)				// 아카이브등록일
	@XmlElement(name="META_ARRG_END_DT")
	private String		arrgEndDt;				// CHAR(14)				// 정리완료일
	@XmlElement(name="META_WORK_PRIO_CD")
	private String		workPrioCd;			// CHAR(3)				// 작업우선순위코드
	@XmlElement(name="META_RSV_PRD_CD")
	private String		rsvPrdCd;				// CHAR(3)				// 보존기간코드
	@XmlElement(name="META_CPRTR_NM")
	private String		cprtrNm;				// VARCHAR(75)			// 저작권자명
	@XmlElement(name="META_CPRT_TYPE")
	private String 		cprtType;				// CHAR(3)				// 저작권형태코드
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_CPRT_TYPE_DSC")
	private String		cprtTypeDsc;			// CHAR(3)				// 저작권형태설명
	@XmlElement(name="META_VIEW_GR_CD")
	private String		viewGrCd;				// CHAR(3)				// 시청등급코드
	@XmlElement(name="META_DLBR_CD")
	private String 		dlbrCd;				// CHAR(3)				// 심의결과코드
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_AWARD_HSTR")
	private String		awardHstr;				// LONGVARCHAR			// 수상내역
	@XmlElement(name="META_RPIMG_KFRM_SEQ")
	private Integer		rpimgKfrmSeq;			// INTEGER				// 대표화면
	@XmlElement(name="META_TAPE_ID")
	private String 		tapeId;				// CHAR(12)				// 테이프식별자ID
	@XmlElement(name="META_TAPE_ITEM_ID")
	private String 		tapeItemId;			// CHAR(12)				// 테이프아이템식별자ID
	@XmlElement(name="META_TAPE_MEDIA_CLF_CD")
	private String		tapeMediaClfCd;		// CHAR(3)				// 테이프매체종류코드
	@XmlElement(name="META_RSV_PRD_END_DD")
	private String		rsvPrdEndDd;			// VARCHAR(6)			// 제작부서코드
	@XmlElement(name="META_DEL_DD")
	private String		delDd;					// CHAR(1)				// 삭제일
	@XmlElement(name="META_USE_YN")
	private String		useYn;					// CHAR(1)				// 사용여부
	@XmlElement(name="meta_regrid")
	private String		regrid;				// VARCHAR(15)			// 등록자ID
	@XmlElement(name="META_REG_DT")
	private String		regDt;					// CHAR(14)				// 등록일시
	@XmlElement(name="META_MODRID")
	private String		modrid;				// VARCHAR(15)			// 수정자ID
	@XmlElement(name="META_MOD_DT")
	private String		modDt;					// CHAR(14)				// 수정일시
	@XmlElement(name="META_GATH_DEPT_CD")
	private String		gathDeptCd;			// VARCHAR(8)			// 수집부서코드
	@XmlElement(name="META_MCUID")
	private String		mcuid;					// VARCHAR(40)			// 주조 ID
	@XmlElement(name="META_RPIMG_CT_ID")
	private Long		rpimgCtId;				// BIGINT				// 대표화면 CT_ID
	@XmlElement(name="META_DATA_STAT_CD")
	private String		dataStatCd;			// CHAR(3)				// 자료상태코드 (000: 준비중, 001: 정리전, 002: 정리중, 003: 정리완료, 004: 인제스트 재지시, 005: 아카이브 재지시, 006: 오류, 007: 검수완료, 008: 아카이브)
	@XmlElement(name="META_ING_REG_DD")
	private String		ingRegDd;				// CHAR(14)				// 인제스트 완료일
	@XmlElement(name="META_COPY_KEEP")
	private String 		copyKeep;				// CHAR(3)				// 부본보관
	@XmlElement(name="META_CLEAN_KEEP")
	private String 		cleanKeep;				// CHAR(3)				// 클린보관
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_MUSIC_INFO")
	private String		musicInfo;				// VARCHAR(300)			// 음악정보
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_RST_CONT")
	private String		rstCont;				// VARCHAR(1500)		// 제한사항
	@XmlElement(name="META_RERUN")
	private String		rerun;					// VARCHAR(300)			// 재방송
	@XmlElement(name="META_ACCEPTOR_ID")
	private String		acceptorId;			// VARCHAR(15)			// 검수자 ID
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_SUB_TTL")
	private String		subTtl;				// VARCHAR(300)			// 부제
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_ARRANGE_NM")
	private String		arrangeNm;				// VARCHAR(80)			// 편성명
	@XmlElement(name="META_ACCEPTOR_NM")
	private String metaAcceptorNm;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_SCENARIO_TITLE")
	private String metaScenarioTitle;
	@XmlElement(name="META_PRDT_DEPT_CD")
	private String metaPrdtDeptCd;
	@XmlElement(name="META_AUD_TYPE_CD")
	private String metaAudTypeCd;
	@XmlElement(name="META_RECORD_TYPE_CD")
	private String metaRecordTypeCd;
	@XmlElement(name="META_COLOR_CD")
	private String metaColorCd;
	@XmlElement(name="META_ME_CD")
	private String metaMeCd;
	@XmlElement(name="META_ASP_RTO_CD")
	private String metaAspRtoCd;
	@XmlElement(name="META_VD_QLTY")
	private String metaVdQlty;
	@XmlElement(name="META_DURATION")
	private String metaDuration;
	@XmlElement(name="META_CT_TYP")
	private String metaCtTyp;
	@XmlElement(name="META_CT_CLA")
	private String metaCtCla;
	@XmlElement(name="META_AUDIO_BDWT")
	private String metaAudioBdwt;
	@XmlElement(name="META_FRM_PER_SEC")
	private String metaFrmPerSec;
	@XmlElement(name="META_AUD_SAMP_FRQ")
	private String metaAudSampFrq;
	@XmlElement(name="META_BIT_RT")
	private String metaBitRt;
	@XmlElement(name="META_VD_HRESOL")
	private String metaVdHresol;
	@XmlElement(name="META_VD_VRESOL")
	private String metaVdVresol;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_PREVIEW_SUBJ")
	private String metaPreviewSubj;
	@XmlElement(name="META_HRESOL")
	private String metaHresol;
	@XmlElement(name="META_ARCHIVE_REQID")
	private String metaarchiveReqid;
	@XmlElement(name="META_INGEST_EQ_ID")
	private String metaIngestEqId;
	@XmlElement(name="META_PDS_CMS_PGM_ID")
	private String metaPdsCmsPgmId;
	@XmlElement(name="META_CT_ID")
	private String metaCdId;

	/** 추가 2012.05.02 */
	@XmlElement(name="META_STORAGE")
	private String     storage;
	@XmlElement(name="META_COCD")
	private String		cocd;				// CHAR(1)					// 회사
	@XmlElement(name="META_CHENNEL_CD")
	private String		chennelCd;			// CHAR(1)					// 채널
	@XmlElement(name="META_ARTIST")
	private String 		artist;				// VARCHAR(15)				// 가수
	@XmlElement(name="META_COUNTRY_CD")
	private String 		countryCd;			// CHAR(3)					// 국가구분
	@XmlElement(name="meta_media_id")
	private String 		mediaId;			// CHAR(3)					// 미디어id
	@XmlElement(name="META_ANNOT_CLF_CD")
	private String 		annotClfNm;			// CHAR(3)					// 사용제한 명

	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_ANNOT_CLF_DESC")
	private String 		annotClfCont;			// CHAR(3)					// 사용제한 내용

	@XmlElement(name="META_VDQLTY_NM")
	private String 		vdqltyNm;			// CHAR(3)					// 화질코드

	@XmlElement(name="PGM_BRD_BGN_DD")
	private String 		pgmBrdBgnDd;			// CHAR(3)					// 방송시작일

	@XmlElement(name="PGM_BRD_END_DD")
	private String 		pgmBrdEndDd;			// CHAR(3)					// 방송종료일

	@XmlElement(name="PGM_MEDIA_CD")
	private String 		pgmMediaCd;			// CHAR(3)					// 프로그램미디어코드

	@XmlElement(name="PGM_CHAN_CD")
	private String 		pgmChanCd;			// CHAR(3)					// 채널코드

	@XmlElement(name="PGM_PILOT_YN")
	private String 		pgmPilotYn;			// CHAR(3)					// 파일럿여부

	@XmlElement(name="META_ACCEPTOR_NM")
	private String 		acceptorNm;			// CHAR(3)					// 승인자명

	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_SCENARIO_TITLE")
	private String 		scenarioTitle;			// CHAR(3)					// 대본명

	@XmlElement(name="META_AUD_TYPE_CD")
	private String 		aud_type_cd;			// CHAR(3)					// 오디오 유형

	@XmlElement(name="META_RECORD_TYPE_CD")
	private String 		recordTypeCd;			// CHAR(3)					// 녹음방식 유형
	@XmlElement(name="META_COLOR_CD")
	private String 		colorCd;			// CHAR(3)					// 색상 유형

	@XmlElement(name="META_ME_CD")
	private String 		meCd;			// CHAR(3)					// me 유형

	@XmlElement(name="META_ASP_RTO_CD")
	private String 		aspRtoCd;			// CHAR(3)					// 화면비 유형

	@XmlElement(name="META_VD_QLTY")
	private String 		vdQlty;			// CHAR(3)					// 화질 유형
	@XmlElement(name="META_DURATION")
	private String 		duration;			// CHAR(3)					// duration 유형
	@XmlElement(name="META_CT_TYP")
	private String 		ctTyp;			// CHAR(3)					// 컨텐츠  유형
	@XmlElement(name="META_CT_CLA")
	private String 		ctCla;			// CHAR(3)					// 컨턴츠 타입
	@XmlElement(name="META_AUDIO_BDWT")
	private String 		audioBdwt;			// CHAR(3)					// 오비오 대역폭	

	@XmlElement(name="META_FRM_PER_SEC")
	private String 		frmPerSec;			// CHAR(3)					// 초당 프레임

	@XmlElement(name="META_AUD_SAMP_FRQ")
	private String 		audSampFrq;			// CHAR(3)					//
	@XmlElement(name="META_BIT_RT")
	private String 		bitRt;			// CHAR(3)					//
	@XmlElement(name="META_VD_HRESOL")
	private String 		vdHresol;			// CHAR(3)					//
	@XmlElement(name="META_VD_VRESOL")
	private String 		vdVresol;			// CHAR(3)					//
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_PREVIEW_SUBJ")
	private String 		previewSubj;			// CHAR(3)					//
	@XmlElement(name="META_HRESOL")
	private String 		hresol;			// CHAR(3)					//
	@XmlElement(name="META_ARCHIVE_REQID")
	private String 		archiveReqId;			// CHAR(3)					//
	@XmlElement(name="META_INGEST_EQ_ID")
	private Integer 		ingestEqId;			// CHAR(3)					//
	@XmlElement(name="META_PDS_CMS_PGM_ID")
	private String 		pdsCmsPgmId;			// CHAR(3)					//
	@XmlElement(name="META_CT_ID")
	private Long 		ctId;			// CHAR(3)					//

	@XmlElement(name="META_ANNOT_CLF_CD")
	private String 		annotClfCd;			// CHAR(3)					//
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="META_ANNOT_CLF_DESC")
	private String 		annotClfDesc;			// CHAR(3)					//

	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="RESULT")
	private String 		result;			// CHAR(3)					//

	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="REASON")
	private String 		reason;			// CHAR(3)					//




	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCtTyp() {
		return ctTyp;
	}
	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public String getAudioBdwt() {
		return audioBdwt;
	}
	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}
	public String getFrmPerSec() {
		return frmPerSec;
	}
	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}
	public String getAudSampFrq() {
		return audSampFrq;
	}
	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}
	public String getBitRt() {
		return bitRt;
	}
	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}
	public String getVdHresol() {
		return vdHresol;
	}
	public void setVdHresol(String vdHresol) {
		this.vdHresol = vdHresol;
	}
	public String getVdVresol() {
		return vdVresol;
	}
	public void setVdVresol(String vdVresol) {
		this.vdVresol = vdVresol;
	}
	public String getPreviewSubj() {
		return previewSubj;
	}
	public void setPreviewSubj(String previewSubj) {
		this.previewSubj = previewSubj;
	}
	public String getHresol() {
		return hresol;
	}
	public void setHresol(String hresol) {
		this.hresol = hresol;
	}
	public String getArchiveReqId() {
		return archiveReqId;
	}
	public void setArchiveReqId(String archiveReqId) {
		this.archiveReqId = archiveReqId;
	}
	public Integer getIngestEqId() {
		return ingestEqId;
	}
	public void setIngestEqId(Integer ingestEqId) {
		this.ingestEqId = ingestEqId;
	}
	public String getPdsCmsPgmId() {
		return pdsCmsPgmId;
	}
	public void setPdsCmsPgmId(String pdsCmsPgmId) {
		this.pdsCmsPgmId = pdsCmsPgmId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getAnnotClfDesc() {
		return annotClfDesc;
	}
	public void setAnnotClfDesc(String annotClfDesc) {
		this.annotClfDesc = annotClfDesc;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getMeCd() {
		return meCd;
	}
	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getAud_type_cd() {
		return aud_type_cd;
	}
	public void setAud_type_cd(String audTypeCd) {
		aud_type_cd = audTypeCd;
	}
	public String getScenarioTitle() {
		return scenarioTitle;
	}
	public void setScenarioTitle(String scenarioTitle) {
		this.scenarioTitle = scenarioTitle;
	}
	public String getAcceptorNm() {
		return acceptorNm;
	}
	public void setAcceptorNm(String acceptorNm) {
		this.acceptorNm = acceptorNm;
	}
	public String getPgmPilotYn() {
		return pgmPilotYn;
	}
	public void setPgmPilotYn(String pgmPilotYn) {
		this.pgmPilotYn = pgmPilotYn;
	}
	public String getPgmChanCd() {
		return pgmChanCd;
	}
	public void setPgmChanCd(String pgmChanCd) {
		this.pgmChanCd = pgmChanCd;
	}
	public String getPgmMediaCd() {
		return pgmMediaCd;
	}
	public void setPgmMediaCd(String pgmMediaCd) {
		this.pgmMediaCd = pgmMediaCd;
	}
	public String getPgmBrdBgnDd() {
		return pgmBrdBgnDd;
	}
	public void setPgmBrdBgnDd(String pgmBrdBgnDd) {
		this.pgmBrdBgnDd = pgmBrdBgnDd;
	}
	public String getPgmBrdEndDd() {
		return pgmBrdEndDd;
	}
	public void setPgmBrdEndDd(String pgmBrdEndDd) {
		this.pgmBrdEndDd = pgmBrdEndDd;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getAnnotClfNm() {
		return annotClfNm;
	}
	public void setAnnotClfNm(String annotClfNm) {
		this.annotClfNm = annotClfNm;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public String getVdqltyNm() {
		return vdqltyNm;
	}
	public void setVdqltyNm(String vdqltyNm) {
		this.vdqltyNm = vdqltyNm;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Long getPgmId() {
		return pgmId;
	}
	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
	}
	public String getPgmCd() {
		return pgmCd;
	}
	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getEpisNo() {
		return episNo;
	}
	public void setEpisNo(String episNo) {
		this.episNo = episNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getFinalBrdYn() {
		return finalBrdYn;
	}
	public void setFinalBrdYn(String finalBrdYn) {
		this.finalBrdYn = finalBrdYn;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getBrdBgnHms() {
		return brdBgnHms;
	}
	public void setBrdBgnHms(String brdBgnHms) {
		this.brdBgnHms = brdBgnHms;
	}
	public String getBrdEndHms() {
		return brdEndHms;
	}
	public void setBrdEndHms(String brdEndHms) {
		this.brdEndHms = brdEndHms;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getPgmRate() {
		return pgmRate;
	}
	public void setPgmRate(String pgmRate) {
		this.pgmRate = pgmRate;
	}
	public String getDrtNm() {
		return drtNm;
	}
	public void setDrtNm(String drtNm) {
		this.drtNm = drtNm;
	}
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	public String getWriterNm() {
		return writerNm;
	}
	public void setWriterNm(String writerNm) {
		this.writerNm = writerNm;
	}
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getPrdtDeptCd() {
		return prdtDeptCd;
	}
	public void setPrdtDeptCd(String prdtDeptCd) {
		this.prdtDeptCd = prdtDeptCd;
	}
	public String getPrdtDeptNm() {
		return prdtDeptNm;
	}
	public void setPrdtDeptNm(String prdtDeptNm) {
		this.prdtDeptNm = prdtDeptNm;
	}
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	public String getMcNm() {
		return mcNm;
	}
	public void setMcNm(String mcNm) {
		this.mcNm = mcNm;
	}
	public String getCastNm() {
		return castNm;
	}
	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}
	public String getCmrDrtNm() {
		return cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm) {
		this.cmrDrtNm = cmrDrtNm;
	}
	public String getFmDt() {
		return fmDt;
	}
	public void setFmDt(String fmDt) {
		this.fmDt = fmDt;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public String getSecArchNm() {
		return secArchNm;
	}
	public void setSecArchNm(String secArchNm) {
		this.secArchNm = secArchNm;
	}
	public String getSecArchId() {
		return secArchId;
	}
	public void setSecArchId(String secArchId) {
		this.secArchId = secArchId;
	}
	public String getGathCoCd() {
		return gathCoCd;
	}
	public void setGathCoCd(String gathCoCd) {
		this.gathCoCd = gathCoCd;
	}
	public String getGathClfCd() {
		return gathClfCd;
	}
	public void setGathClfCd(String gathClfCd) {
		this.gathClfCd = gathClfCd;
	}
	public String getArchRegDd() {
		return archRegDd;
	}
	public void setArchRegDd(String archRegDd) {
		this.archRegDd = archRegDd;
	}
	public String getArrgEndDt() {
		return arrgEndDt;
	}
	public void setArrgEndDt(String arrgEndDt) {
		this.arrgEndDt = arrgEndDt;
	}
	public String getWorkPrioCd() {
		return workPrioCd;
	}
	public void setWorkPrioCd(String workPrioCd) {
		this.workPrioCd = workPrioCd;
	}
	public String getRsvPrdCd() {
		return rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd) {
		this.rsvPrdCd = rsvPrdCd;
	}
	public String getCprtrNm() {
		return cprtrNm;
	}
	public void setCprtrNm(String cprtrNm) {
		this.cprtrNm = cprtrNm;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getCprtTypeDsc() {
		return cprtTypeDsc;
	}
	public void setCprtTypeDsc(String cprtTypeDsc) {
		this.cprtTypeDsc = cprtTypeDsc;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getDlbrCd() {
		return dlbrCd;
	}
	public void setDlbrCd(String dlbrCd) {
		this.dlbrCd = dlbrCd;
	}
	public String getAwardHstr() {
		return awardHstr;
	}
	public void setAwardHstr(String awardHstr) {
		this.awardHstr = awardHstr;
	}
	public Integer getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(Integer rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}
	public String getTapeId() {
		return tapeId;
	}
	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}
	public String getTapeItemId() {
		return tapeItemId;
	}
	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}
	public String getTapeMediaClfCd() {
		return tapeMediaClfCd;
	}
	public void setTapeMediaClfCd(String tapeMediaClfCd) {
		this.tapeMediaClfCd = tapeMediaClfCd;
	}
	public String getRsvPrdEndDd() {
		return rsvPrdEndDd;
	}
	public void setRsvPrdEndDd(String rsvPrdEndDd) {
		this.rsvPrdEndDd = rsvPrdEndDd;
	}
	public String getDelDd() {
		return delDd;
	}
	public void setDelDd(String delDd) {
		this.delDd = delDd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getGathDeptCd() {
		return gathDeptCd;
	}
	public void setGathDeptCd(String gathDeptCd) {
		this.gathDeptCd = gathDeptCd;
	}
	public String getMcuid() {
		return mcuid;
	}
	public void setMcuid(String mcuid) {
		this.mcuid = mcuid;
	}
	public Long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(Long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getIngRegDd() {
		return ingRegDd;
	}
	public void setIngRegDd(String ingRegDd) {
		this.ingRegDd = ingRegDd;
	}
	public String getCopyKeep() {
		return copyKeep;
	}
	public void setCopyKeep(String copyKeep) {
		this.copyKeep = copyKeep;
	}
	public String getCleanKeep() {
		return cleanKeep;
	}
	public void setCleanKeep(String cleanKeep) {
		this.cleanKeep = cleanKeep;
	}
	public String getMusicInfo() {
		return musicInfo;
	}
	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}
	public String getRstCont() {
		return rstCont;
	}
	public void setRstCont(String rstCont) {
		this.rstCont = rstCont;
	}
	public String getRerun() {
		return rerun;
	}
	public void setRerun(String rerun) {
		this.rerun = rerun;
	}
	public String getAcceptorId() {
		return acceptorId;
	}
	public void setAcceptorId(String acceptorId) {
		this.acceptorId = acceptorId;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public String getArrangeNm() {
		return arrangeNm;
	}
	public void setArrangeNm(String arrangeNm) {
		this.arrangeNm = arrangeNm;
	}
	public String getMetaAcceptorNm() {
		return metaAcceptorNm;
	}
	public void setMetaAcceptorNm(String metaAcceptorNm) {
		this.metaAcceptorNm = metaAcceptorNm;
	}
	public String getMetaScenarioTitle() {
		return metaScenarioTitle;
	}
	public void setMetaScenarioTitle(String metaScenarioTitle) {
		this.metaScenarioTitle = metaScenarioTitle;
	}
	public String getMetaPrdtDeptCd() {
		return metaPrdtDeptCd;
	}
	public void setMetaPrdtDeptCd(String metaPrdtDeptCd) {
		this.metaPrdtDeptCd = metaPrdtDeptCd;
	}
	public String getMetaAudTypeCd() {
		return metaAudTypeCd;
	}
	public void setMetaAudTypeCd(String metaAudTypeCd) {
		this.metaAudTypeCd = metaAudTypeCd;
	}
	public String getMetaRecordTypeCd() {
		return metaRecordTypeCd;
	}
	public void setMetaRecordTypeCd(String metaRecordTypeCd) {
		this.metaRecordTypeCd = metaRecordTypeCd;
	}
	public String getMetaColorCd() {
		return metaColorCd;
	}
	public void setMetaColorCd(String metaColorCd) {
		this.metaColorCd = metaColorCd;
	}
	public String getMetaMeCd() {
		return metaMeCd;
	}
	public void setMetaMeCd(String metaMeCd) {
		this.metaMeCd = metaMeCd;
	}
	public String getMetaAspRtoCd() {
		return metaAspRtoCd;
	}
	public void setMetaAspRtoCd(String metaAspRtoCd) {
		this.metaAspRtoCd = metaAspRtoCd;
	}
	public String getMetaVdQlty() {
		return metaVdQlty;
	}
	public void setMetaVdQlty(String metaVdQlty) {
		this.metaVdQlty = metaVdQlty;
	}
	public String getMetaDuration() {
		return metaDuration;
	}
	public void setMetaDuration(String metaDuration) {
		this.metaDuration = metaDuration;
	}
	public String getMetaCtTyp() {
		return metaCtTyp;
	}
	public void setMetaCtTyp(String metaCtTyp) {
		this.metaCtTyp = metaCtTyp;
	}
	public String getMetaCtCla() {
		return metaCtCla;
	}
	public void setMetaCtCla(String metaCtCla) {
		this.metaCtCla = metaCtCla;
	}
	public String getMetaAudioBdwt() {
		return metaAudioBdwt;
	}
	public void setMetaAudioBdwt(String metaAudioBdwt) {
		this.metaAudioBdwt = metaAudioBdwt;
	}
	public String getMetaFrmPerSec() {
		return metaFrmPerSec;
	}
	public void setMetaFrmPerSec(String metaFrmPerSec) {
		this.metaFrmPerSec = metaFrmPerSec;
	}
	public String getMetaAudSampFrq() {
		return metaAudSampFrq;
	}
	public void setMetaAudSampFrq(String metaAudSampFrq) {
		this.metaAudSampFrq = metaAudSampFrq;
	}
	public String getMetaBitRt() {
		return metaBitRt;
	}
	public void setMetaBitRt(String metaBitRt) {
		this.metaBitRt = metaBitRt;
	}
	public String getMetaVdHresol() {
		return metaVdHresol;
	}
	public void setMetaVdHresol(String metaVdHresol) {
		this.metaVdHresol = metaVdHresol;
	}
	public String getMetaVdVresol() {
		return metaVdVresol;
	}
	public void setMetaVdVresol(String metaVdVresol) {
		this.metaVdVresol = metaVdVresol;
	}
	public String getMetaPreviewSubj() {
		return metaPreviewSubj;
	}
	public void setMetaPreviewSubj(String metaPreviewSubj) {
		this.metaPreviewSubj = metaPreviewSubj;
	}
	public String getMetaHresol() {
		return metaHresol;
	}
	public void setMetaHresol(String metaHresol) {
		this.metaHresol = metaHresol;
	}
	public String getMetaarchiveReqid() {
		return metaarchiveReqid;
	}
	public void setMetaarchiveReqid(String metaarchiveReqid) {
		this.metaarchiveReqid = metaarchiveReqid;
	}
	public String getMetaIngestEqId() {
		return metaIngestEqId;
	}
	public void setMetaIngestEqId(String metaIngestEqId) {
		this.metaIngestEqId = metaIngestEqId;
	}
	public String getMetaPdsCmsPgmId() {
		return metaPdsCmsPgmId;
	}
	public void setMetaPdsCmsPgmId(String metaPdsCmsPgmId) {
		this.metaPdsCmsPgmId = metaPdsCmsPgmId;
	}
	public String getMetaCdId() {
		return metaCdId;
	}
	public void setMetaCdId(String metaCdId) {
		this.metaCdId = metaCdId;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getChennelCd() {
		return chennelCd;
	}
	public void setChennelCd(String chennelCd) {
		this.chennelCd = chennelCd;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCountryCd() {
		return countryCd;
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	@XmlElement(name="ingest")
	private Ingest ingest;
	public Ingest getIngest() {
		return ingest;
	}
	public void setIngest(Ingest ingest) {
		this.ingest = ingest;
	}

	@XmlElement(name="relation")
	private Relation relation;
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	@XmlElement(name="Attach")
	private Attach attach;
	public Attach getAttach() {
		return attach;
	}
	public void setAttach(Attach attach) {
		this.attach = attach;
	}

	@XmlElement(name="Annot")
	private Annot annot;
	public Annot getAnnot() {
		return annot;
	}
	public void setAnnot(Annot annot) {
		this.annot = annot;
	}

}

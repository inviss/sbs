package com.sbs.das.dto;

public class MasterTbl extends BaseObject {
	
	private static final long serialVersionUID = 8942341250720012547L;
	
	Long		masterId;				// BIGINT				// 마스터ID
	Long		pgmId;					// BIGINT				// 프로그램ID
	String		pgmCd;					// CHAR(8)				// 프로그램코드
	Integer		episNo;					// INTEGER				// 회차
	String 		title;					// VARCHAR(150)			// 타이틀
	String		ctgrLCd;				// CHAR(3)				// 대분류코드
	String		ctgrMCd;				// CHAR(3)				// 중분류코드
	String		ctgrSCd;				// CHAR(3)				// 소분류코드 
	String		brdDd;					// CHAR(8)				// 방송일자
	String		finalBrdYn;			// CHAR(1)				// 최종방송여부
	String		snps;					// LONGVARCHAR			// 시놉시스
	String		keyWords;				// LONGVARCHAR			// 키워드
	String		brdBgnHms;				// CHAR(14)				// 방송시작시간
	String		brdEndHms;				// CHAR(14)				// 방송종료시간
	String		brdLeng;				// CHAR(6) 				// 방송길이
	String		pgmRate;				// DECIMAL(4, 2)		// 시청률
	String		drtNm;					// VARCHAR(300)			// 연출자명
	String		producerNm;			// VARCHAR(300)			// 프로듀서명
	String		writerNm;				// VARCHAR(300)			// 작가명
	String		prdtInOutsCd;			// CHAR(3)  			// 제작구분코드
	String		prdtDeptCd;			// VARCHAR(6)			// 제작부서코
	String 		prdtDeptNm;			// VARCHAR(90)			// 제작부서명칭
	String		orgPrdrNm;				// VARCHAR(90)			// 원제작사명
	String		mcNm;					// VARCHAR(300)			// 진행자명
	String 		castNm;				// LONG VARCHAR			// 출연자명
	String		cmrDrtNm;				// VARCHAR(30)			// 촬영감독명
	String		fmDt;					// CHAR(8) 				// 촬영일
	String		cmrPlace;				// VARCHAR(300)			// 촬영장소
	String		spcInfo;				// LONGVARCHAR			// 특이사항
	String		reqCd;					// CHAR(9)				// 청구번호코드
	String		secArchNm;				// VARCHAR(30)			// 2차아카이브명
	String		secArchId;				// VARCHAR(15)			// 2차아카이버ID
	String		gathCoCd;				// CHAR(3)				// 수집처코드
	String		gathClfCd;				// CHAR(3)				// 수집구분코드
	String		archRegDd;				// CHAR(14)				// 아카이브등록일
	String		arrgEndDt;				// CHAR(14)				// 정리완료일
	String		workPrioCd;			// CHAR(3)				// 작업우선순위코드
	String		rsvPrdCd;				// CHAR(3)				// 보존기간코드
	String		cprtrNm;				// VARCHAR(75)			// 저작권자명
	String 		cprtType;				// CHAR(3)				// 저작권형태코드
	String		cprtTypeDsc;			// CHAR(3)				// 저작권형태설명
	String		viewGrCd;				// CHAR(3)				// 시청등급코드
	String 		dlbrCd;				// CHAR(3)				// 심의결과코드
	String		awardHstr;				// LONGVARCHAR			// 수상내역
	Integer		rpimgKfrmSeq;			// INTEGER				// 대표화면
	String 		tapeId;				// CHAR(12)				// 테이프식별자ID
	String 		tapeItemId;			// CHAR(12)				// 테이프아이템식별자ID
	String		tapeMediaClfCd;		// CHAR(3)				// 테이프매체종류코드
	String		rsvPrdEndDd;			// VARCHAR(6)			// 제작부서코드
	String		delDd;					// CHAR(1)				// 사용여부
	String		useYn;					// CHAR(1)				// 사용여부
	String		regrid;				// VARCHAR(15)			// 등록자ID
	String		regDt;					// CHAR(14)				// 등록일시
	String		modrid;				// VARCHAR(15)			// 수정자ID
	String		modDt;					// CHAR(14)				// 수정일시
	String		gathDeptCd;			// VARCHAR(8)			// 수집부서코드
	String		mcuid;					// VARCHAR(40)			// 주조 ID
	Long		rpimgCtId;				// BIGINT				// 대표화면 CT_ID
	String		dataStatCd;			// CHAR(3)				// 자료상태코드 (000: 준비중, 001: 정리전, 002: 정리중, 003: 정리완료, 004: 인제스트 재지시, 005: 아카이브 재지시, 006: 오류, 007: 검수완료, 008: 아카이브)
	String		ingRegDd;				// CHAR(14)				// 인제스트 완료일
	String 		copyKeep;				// CHAR(3)				// 부본보관
	String 		cleanKeep;				// CHAR(3)				// 클린보관
	String		musicInfo;				// VARCHAR(300)			// 음악정보
	String		rstCont;				// VARCHAR(1500)		// 제한사항
	String		rerun;					// VARCHAR(300)			// 재방송
	String		acceptorId;			// VARCHAR(15)			// 검수자 ID
	String		subTtl;				// VARCHAR(300)			// 부제
	String		arrangeNm;				// VARCHAR(80)			// 편성명
	String		lockStatCd;			// CHAR(3)				// LOCK 기능
	String		errorStatCd;			// CHAR(3)				// 에러상태모드
	
	/** 추가 Field **/
	String		archRoute;				// VARCHAR(2)			// 아카이브 경로(OL: 온에어 생방, OA: 온에어 송출, P: PDS, DE: 매체변환, DP: ERP에 존재하지 않는 매체변환)
	String		pdsCmsPgmId;			// CHAR(7)				// PDS 프로그램 ID
	String		ristClfCd;				// CHAR(3)				// 사용등급
	String 		acceptEndDd;			// CHAR(14)				// 검수종료일
	String 		manualYn;			// CHAR(1)					// 수동아카이브 여부
	
	/** 추가 2012.05.02 */
	String		cocd;				// CHAR(1)					// 회사
	String		chennelCd;			// CHAR(1)					// 채널
	String 		artist;				// VARCHAR(15)				// 가수
	String 		countryCd;			// CHAR(3)					// 국가구분
	
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
	public String getManualYn() {
		return manualYn;
	}
	public void setManualYn(String manulaYn) {
		this.manualYn = manulaYn;
	}
	public String getAcceptEndDd() {
		return acceptEndDd;
	}
	public void setAcceptEndDd(String acceptEndDd) {
		this.acceptEndDd = acceptEndDd;
	}
	public String getArchRoute() {
		return archRoute;
	}
	public void setArchRoute(String archRoute) {
		this.archRoute = archRoute;
	}
	public String getPdsCmsPgmId() {
		return pdsCmsPgmId;
	}
	public void setPdsCmsPgmId(String pdsCmsPgmId) {
		this.pdsCmsPgmId = pdsCmsPgmId;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
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
	public Integer getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Integer episNo) {
		this.episNo = episNo==null?0:episNo;
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
	public String getLockStatCd() {
		return lockStatCd;
	}
	public void setLockStatCd(String lockStatCd) {
		this.lockStatCd = lockStatCd;
	}
	public String getErrorStatCd() {
		return errorStatCd;
	}
	public void setErrorStatCd(String errorStatCd) {
		this.errorStatCd = errorStatCd;
	}
	
}

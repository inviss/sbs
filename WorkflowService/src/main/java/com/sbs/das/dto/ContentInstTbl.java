package com.sbs.das.dto;

public class ContentInstTbl extends BaseObject {

	private static final long serialVersionUID = -2618559342485897251L;
	
	Long	ctiId;				// BIGINT				컨텐츠인스턴스ID
	Long	ctId;				// CHAR(12)			 	컨텐츠ID
	String 	ctiFmt;			// CHAR(3)			 	컨텐츠인스턴스포맷코드
	/*
	 * 101: M2 MXF Long GOP35 OP1A
	 * 102: Sony XDCAM DV
	 * 103: Sony XDCAM D10
	 * 104: Generic MPEG2 105
	 * 105: Sony XDCAM HD
	 * 106: Generic Mpeg2
	 * 201: WMV9 700K
	 * 301: WAV
	 */
	String	archSteYn;			// CHAR(1)			 	아카이브상태코드
	String	meCd;				// CHAR(3)			 	ME분리코드
	String	bitRt;				// VARCHAR(10)	 		비트전송률
	String	frmPerSec;			// INTEGER			 	초당프레임
	String	drpFrmYn;			// CHAR(1)			 	드롭프레임여부
	Integer	vdHresol;			// INTEGER				비디오수평주파수
	Integer	vdVresol;			// INTEGER			 	비디오수직주파수
	String	colorCd;			// CHAR(3)			 	색상코드 (001: 흑백, 002: 컬러, 003: 오류)
	String	recordTypeCd;		// CHAR(3)			 	녹음방식코드 (001: 모노, 002: 스트레오, 003: 5.1채널, 004: 음성다중)
	String	audioYn;			// CHAR(1)			 	오디오여부
	String	audTypeCd;
	String	audLanCd;			// CHAR(3)			 	오디어언어코드
	String	audSampFrq;		    // VARCHAR(10)	 		오디오샘플링
	String	audioBdwt;			// VARCHAR(10)	 		오디오대역폭
	String	noiRducTypCd;		// CHAR(3)			 	잡음저감유형코드
	Integer	ingestEqId;			// VARCHAR(15)		 	인제스크장치ID
	String	encQltyCd;			// CHAR(3)			 	인코딩품질코드
	String	encQltyDesc;		// LONG VARCHAR  		인코딩품질설명
	String	flPath;			// VARCHAR(256)	 	파일경로
	String	wrkFileNm;			// VARCHAR(256)	 		원파일명
	String	orgFileNm;			// VARCHAR(256)	 		원파일명
	Long	flSz;				// BIGINT				파일크기
	String	regDt;				// CHAR(14)				등록일시
	String	regrid;			// VARCHAR(15)	 		등록자ID
	String	modDt;				// CHAR(14)			 	수정일시
	String	modrid;			// VARCHAR(15)	 		수정자명
	
	/** 추가 FIELD **/
	String  dtlYn;				// CHAR(1)				DTL 존재여부
	String  etc;				// VARCHAR(60)			비고
	String  reWmvRegDt;		// CHAR(14)				재 WMV 등록일
	String  wmvYn;				// CHAR(1)				WMV 존재여부
	String  fileYn;			// CAHR(1)				파일 존재여부
	String  outSystemId;		// VARCHAR(16)			타 시스템 ID
	String  catalogYn;        //CART(1)               카탈로깅 여부
	
	/* 추가 2016-02-24 */
	Long 	masterId;
	String 	useYn;
	
	
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getCatalogYn() {
		return catalogYn;
	}
	public void setCatalogYn(String catalogYn) {
		this.catalogYn = catalogYn;
	}
	public String getDtlYn() {
		return dtlYn;
	}
	public void setDtlYn(String dtlYn) {
		this.dtlYn = dtlYn;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getReWmvRegDt() {
		return reWmvRegDt;
	}
	public void setReWmvRegDt(String reWmvRegDt) {
		this.reWmvRegDt = reWmvRegDt;
	}
	public String getWmvYn() {
		return wmvYn;
	}
	public void setWmvYn(String wmvYn) {
		this.wmvYn = wmvYn;
	}
	public String getFileYn() {
		return fileYn;
	}
	public void setFileYn(String fileYn) {
		this.fileYn = fileYn;
	}
	public String getOutSystemId() {
		return outSystemId;
	}
	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public String getArchSteYn() {
		return archSteYn;
	}
	public void setArchSteYn(String archSteYn) {
		this.archSteYn = archSteYn;
	}
	public String getMeCd() {
		return meCd;
	}
	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}
	public String getBitRt() {
		return bitRt;
	}
	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}
	public String getFrmPerSec() {
		return frmPerSec;
	}
	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}
	public String getDrpFrmYn() {
		return drpFrmYn;
	}
	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}
	public Integer getVdHresol() {
		return vdHresol;
	}
	public void setVdHresol(Integer vdHresol) {
		this.vdHresol = vdHresol;
	}
	public Integer getVdVresol() {
		return vdVresol;
	}
	public void setVdVresol(Integer vdVresol) {
		this.vdVresol = vdVresol;
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
	public String getAudioYn() {
		return audioYn;
	}
	public void setAudioYn(String audioYn) {
		this.audioYn = audioYn;
	}
	public String getAudTypeCd() {
		return audTypeCd;
	}
	public void setAudTypeCd(String audTypeCd) {
		this.audTypeCd = audTypeCd;
	}
	public String getAudLanCd() {
		return audLanCd;
	}
	public void setAudLanCd(String audLanCd) {
		this.audLanCd = audLanCd;
	}
	public String getAudSampFrq() {
		return audSampFrq;
	}
	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}
	public String getAudioBdwt() {
		return audioBdwt;
	}
	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}
	public String getNoiRducTypCd() {
		return noiRducTypCd;
	}
	public void setNoiRducTypCd(String noiRducTypCd) {
		this.noiRducTypCd = noiRducTypCd;
	}
	public Integer getIngestEqId() {
		return ingestEqId;
	}
	public void setIngestEqId(Integer ingestEqId) {
		this.ingestEqId = ingestEqId;
	}
	public String getEncQltyCd() {
		return encQltyCd;
	}
	public void setEncQltyCd(String encQltyCd) {
		this.encQltyCd = encQltyCd;
	}
	public String getEncQltyDesc() {
		return encQltyDesc;
	}
	public void setEncQltyDesc(String encQltyDesc) {
		this.encQltyDesc = encQltyDesc;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getWrkFileNm() {
		return wrkFileNm;
	}
	public void setWrkFileNm(String wrkFileNm) {
		this.wrkFileNm = wrkFileNm;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	
}

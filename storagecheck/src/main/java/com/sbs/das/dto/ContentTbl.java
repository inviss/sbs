package com.sbs.das.dto;

public class ContentTbl extends BaseObject {
	
	private static final long serialVersionUID = -8579206972683635967L;
	
	Long 		ctId;					// BIGINT			컨텐츠ID
	Integer		ctSeq;					// INTEGER			동일 컨텐츠내 순서
	String 		ctTyp;					// CHAR(3)			컨텐츠유형코드
	String 		ctCla;					// CHAR(3)			컨텐츠구분코드
	String 		ctNm;					// VARCHAR(150)		컨텐츠명
	String 		cont;					// LONG VARCHAR		내용
	String 		keyWords;				// LONG VARCHAR		키워드
	String 		ctOwnDeptCd;			// CHfAR(6)			주관부서코드
	String		ctOwnDeptNm;			// VARCHAR(90)		주관부서명
	String 		dataStatCd;				// CHAR(3)			자료상태코드
	String 		ctLeng;					// CHAR(11)			컨텐츠길이
	String		vdQlty;					// CHAR(3)			화질코드
	String		aspRtoCd;				// CHAR(3)			종횡비코드
	String 		edtrid;					// VARCHAR(15)		편집자ID
	String 		kfrmPath;				// VARCHAR(256)		키프레임경로
	String 		kfrmPxCd;				// CHAR(3)			키프레임해상도코드
	Integer		totKfrmNums;			// INTEGER			총키프레임수
	String		useYn;					// CHAR(1)			사용여부
	String		delDd;					// CHAR(1)			삭제여부
	String		regDt;					// CHAR(14)			등록일시
	String		regrid;					// VARCHAR(15)		등록자ID
	String		modDt;					// CHAR(14)			수정일시
	String		modrid;					// VARCHAR(15)		수정자명
	Long		duration;				// BIGINT			SOM과 EOM 사이의 길이
	Integer		mcuSeq;					// INTEGER			총키프레임수
	
	/** 추가 Field **/
	Long		cmsCtId;				// BIGINT			미디어ID
	String		copyObjectYn;			// CHAR(1)          복본여부
	Integer		useCont;				// INTEGER			이용회수
	String		archiveYn;				// CHAR(1)			아카이브요청 여부
	String		mediaId;				// VARCHAR(50)		미디어 ID
	String		delYn;					// CHAR(1)			삭제여부
	String		errorCont;				// LONG VARCHAR		에러내용
	
	/* 2015.01.08 추가 메타 */
	Long		masterId;				// BIGINT			메타ID
	String		orgFileNm;				// VARCHAR(256)		원본파일명
	String		jobStatus;				// VARCHAR(1)		아카이브 상태
	Long		ctiId;					// BIGINT			인스턴스ID
	String		flPath;					// VARCHAR			파일경로
	
	
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getErrorCont() {
		return errorCont;
	}
	public void setErrorCont(String errorCont) {
		this.errorCont = errorCont;
	}
	public Long getCmsCtId() {
		return cmsCtId;
	}
	public void setCmsCtId(Long cmsCtId) {
		this.cmsCtId = cmsCtId;
	}
	public String getCopyObjectYn() {
		return copyObjectYn;
	}
	public void setCopyObjectYn(String copyObjectYn) {
		this.copyObjectYn = copyObjectYn;
	}
	public Integer getUseCont() {
		return useCont;
	}
	public void setUseCont(Integer useCont) {
		this.useCont = useCont;
	}
	public String getArchiveYn() {
		return archiveYn;
	}
	public void setArchiveYn(String archiveYn) {
		this.archiveYn = archiveYn;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Integer getCtSeq() {
		return ctSeq;
	}
	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
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
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getCtOwnDeptCd() {
		return ctOwnDeptCd;
	}
	public void setCtOwnDeptCd(String ctOwnDeptCd) {
		this.ctOwnDeptCd = ctOwnDeptCd;
	}
	public String getCtOwnDeptNm() {
		return ctOwnDeptNm;
	}
	public void setCtOwnDeptNm(String ctOwnDeptNm) {
		this.ctOwnDeptNm = ctOwnDeptNm;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getEdtrid() {
		return edtrid;
	}
	public void setEdtrid(String edtrid) {
		this.edtrid = edtrid;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getKfrmPxCd() {
		return kfrmPxCd;
	}
	public void setKfrmPxCd(String kfrmPxCd) {
		this.kfrmPxCd = kfrmPxCd;
	}
	public Integer getTotKfrmNums() {
		return totKfrmNums;
	}
	public void setTotKfrmNums(Integer totKfrmNums) {
		this.totKfrmNums = totKfrmNums;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getDelDd() {
		return delDd;
	}
	public void setDelDd(String delDd) {
		this.delDd = delDd;
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
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Integer getMcuSeq() {
		return mcuSeq;
	}
	public void setMcuSeq(Integer mcuSeq) {
		this.mcuSeq = mcuSeq;
	}
	
}

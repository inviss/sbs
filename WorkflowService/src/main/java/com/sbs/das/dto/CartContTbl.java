package com.sbs.das.dto;

public class CartContTbl extends BaseObject {

	private static final long serialVersionUID = 6991312064373759374L;
	
	Long		cartNo;			// CHAR(3)			// 카트번호 
	Integer		cartSeq;		// INTEGER			// 카트내순번
	String		ristClfCd;		// CHAR(3)			// 사용제한구분코드 
	Long		ctId;			// CHAR(12)			// 컨텐츠ID
	Long		ctiId;			// BIGINT			// 컨텐츠인스턴스ID
	String		som;			// VARCHAR(30)		// 시작점
	String		eom;			// VARCHAR(30)		// 종료점
	Long		duration;		// BIGINT			// SOM과 EOM 사이의 길이
	String		regDt;			// CHAR(14)			// 등록일시
	String		regrid;			// VARCHAR(15)		// 등록자ID
	String		modDt;			// CHAR(14)			// 수정일시
	String		modrid;			// VARCHAR(15)		// 수정자명
	String		ctgrLCd;		// VARCHAR(15)		// 장르 - 대분류
	String		ctgrMCd;		// CHAR(14)			// 장르 - 중분류
	String		ctgrSCd;		// VARCHAR(15)		// 장르 - 소분류
	String		ctCont;			// BIGINT			// CT_CONT
	String		ctNm;			// VARCHAR(150)	
	Long		masterId;		// BIGINT				
	String		downYn;
	String		downDt;
	Long		sFrame;
	
	/** 추가 Field **/
	String      apprYn;         // CHAR(1)			// 승인여부
	String		appCont;		// VARCHAR(200)		// 승인내용(제한영상에 대한 데이터정보팀 승인내용)
	Long		downVol;		// BIGINT			// 진행률
	String 		errorState;		// VARCHAR(3)		// 에러상태
	String		vdQlty;			// CHAR(3)			// 화질코드(001: HD, 002: SD)
	String		aspRtoCd;		// CHAR(3)			// 종횡비코드(001: 16:9, 002: 4:3)
	String		downStat;		// CHAR(3)			// 다운요청상태(001: 사용중, 002:임시저장, 003: 승인요청, 004: 승인, 005: 승인거부, 006: 진행중, 007: 완료, 008: 오류, 009: TapeOut 완료, 010: TapeOut 삭제)
	String		outsourcingYn;	// CHAR(1)			// 외주승인필요여부
	String		outsourcingApprove;	// CHAR(1)		// 외주제작승인여부
	String		downTyp;		// CHAR(1)			// 다운로드 유형(F: FULL, P: PARTIAL)
	String		reqCont;		// VARCHAR(200)		// 다운로드 요청사유
	String 		delYn;			// CHAR(1)			// 삭제여부
	String		mediaId;		//VARCHAR(20)		// 미디어 ID
	
	
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public Long getDownVol() {
		return downVol;
	}
	public void setDownVol(Long downVol) {
		this.downVol = downVol;
	}
	public String getErrorState() {
		return errorState;
	}
	public void setErrorState(String errorState) {
		this.errorState = errorState;
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
	public String getDownStat() {
		return downStat;
	}
	public void setDownStat(String downStat) {
		this.downStat = downStat;
	}
	public String getOutsourcingYn() {
		return outsourcingYn;
	}
	public void setOutsourcingYn(String outsourcingYn) {
		this.outsourcingYn = outsourcingYn;
	}
	public String getOutsourcingApprove() {
		return outsourcingApprove;
	}
	public void setOutsourcingApprove(String outsourcingApprove) {
		this.outsourcingApprove = outsourcingApprove;
	}
	public String getDownTyp() {
		return downTyp;
	}
	public void setDownTyp(String downTyp) {
		this.downTyp = downTyp;
	}
	public String getReqCont() {
		return reqCont;
	}
	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public Long getCartNo() {
		return cartNo;
	}
	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}
	public Integer getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(Integer cartSeq) {
		this.cartSeq = cartSeq;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getApprYn() {
		return apprYn;
	}
	public void setApprYn(String apprYn) {
		this.apprYn = apprYn;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
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
	public String getCtCont() {
		return ctCont;
	}
	public void setCtCont(String ctCont) {
		this.ctCont = ctCont;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getDownYn() {
		return downYn;
	}
	public void setDownYn(String downYn) {
		this.downYn = downYn;
	}
	public String getDownDt() {
		return downDt;
	}
	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}
	public Long getsFrame() {
		return sFrame;
	}
	public void setsFrame(Long sFrame) {
		this.sFrame = sFrame;
	}

}

package com.sbs.das.dto;

public class DownCartTbl extends BaseObject {

	private static final long serialVersionUID = -8329119770552974661L;
	
	Long	cartNo;			// BIGINT			카트번호
	String	dataClfCd;		// CHAR(3)			자료구분코드
	String	prioCd;			// CHAR(3)			자료구분코드
	String	strgLoc;		// CHAR(256)			
	String	ristYn;			// CHAR(1)			사용제한포함여부
	String	appCont;		// LONG VARCHAR		승인내용
	String 	reqUsrid;		// VARCHAR(15)		요청자ID
	String	reqNm;			// VARCHAR(30)		요청자명
	String	reqDt;			// CHAR(14)			요청일시
	String	downDt;			// CHAR(14)			다운로드일시 
	String	appDt;			// CHAR(14)				
	String	downSubj;		// VARCHAR(120)		
	String	gaurantorId;	// VARCHAR(15)		
	String	regDt;			// CHAR(14)			등록일시	
	String	regrid;			// VARCHAR(15)		등록자ID
	String	modDt;			// CHAR(14)			수정일시
	String	modrid;			// VARCHAR(15)		수정자명
	String	deptCd;			// CHAR(3)			요청 부서 
	String	vdQlty;			// CHAR(3)				
	String	aspRtoCd;		// CHAR(3)				
	String	cartStat;		// CHAR(3)				
	String	downYn;			// CHAR(1)
	String	coCd;			// CHAR(3)
	String	segCd;			// CHAR(8)
	
	
	/** 추가 FIELD **/
	String 	downGubun;		// CHAR(3)			다운로드 구분(001: PDS, 002: NDS, 003: 데정팀, 004: tape-out, 005: 계열사)
	String	outStrgLoc;		// VARCHAR(256)		타 시스템 저장위치
	String  outUserYn;		// CHAR(1)			비직원 요청 구분
	String  delYn;			// CHAR(1)			삭제여부
	String  filePath;		// VARCHAR(15)		파일경로
	String  category;		// VARCHAR(15)		카테고리
	String	storagename;	// VARCHAR(15)		스토리지명
	String	toReqCd;		// CHAR(4)			(LR: 저영상 생성, CT: 카탈로그 생성, LRCT: LR+CT)
	
	
	public String getDownGubun() {
		return downGubun;
	}
	public void setDownGubun(String downGubun) {
		this.downGubun = downGubun;
	}
	public String getOutStrgLoc() {
		return outStrgLoc;
	}
	public void setOutStrgLoc(String outStrgLoc) {
		this.outStrgLoc = outStrgLoc;
	}
	public String getOutUserYn() {
		return outUserYn;
	}
	public void setOutUserYn(String outUserYn) {
		this.outUserYn = outUserYn;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public String getToReqCd() {
		return toReqCd;
	}
	public void setToReqCd(String toReqCd) {
		this.toReqCd = toReqCd;
	}
	public Long getCartNo() {
		return cartNo;
	}
	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}
	public String getDataClfCd() {
		return dataClfCd;
	}
	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}
	public String getPrioCd() {
		return prioCd;
	}
	public void setPrioCd(String prioCd) {
		this.prioCd = prioCd;
	}
	public String getStrgLoc() {
		return strgLoc;
	}
	public void setStrgLoc(String strgLoc) {
		this.strgLoc = strgLoc;
	}
	public String getRistYn() {
		return ristYn;
	}
	public void setRistYn(String ristYn) {
		this.ristYn = ristYn;
	}
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public String getReqUsrid() {
		return reqUsrid;
	}
	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getDownDt() {
		return downDt;
	}
	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}
	public String getAppDt() {
		return appDt;
	}
	public void setAppDt(String appDt) {
		this.appDt = appDt;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public String getGaurantorId() {
		return gaurantorId;
	}
	public void setGaurantorId(String gaurantorId) {
		this.gaurantorId = gaurantorId;
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
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
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
	public String getCartStat() {
		return cartStat;
	}
	public void setCartStat(String cartStat) {
		this.cartStat = cartStat;
	}
	public String getDownYn() {
		return downYn;
	}
	public void setDownYn(String downYn) {
		this.downYn = downYn;
	}
	public String getCoCd() {
		return coCd;
	}
	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}
	public String getSegCd() {
		return segCd;
	}
	public void setSegCd(String segCd) {
		this.segCd = segCd;
	}
	
}

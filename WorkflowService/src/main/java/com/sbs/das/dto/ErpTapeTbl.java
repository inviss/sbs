package com.sbs.das.dto;

public class ErpTapeTbl extends BaseObject {

	private static final long serialVersionUID = -7538520643056213292L;
	
	String	tapeId;
	String  copyReqNo;
	String  oldReqNo;
	String	gathNo;
	String	title;
	String	tapeNum;
	String	gathNm;
	String	tapeKind;
	String	lentStat;				// CHAR(3)
	String	secGr;					// CHAR(3)
	String	copyYn;					// CHAR(1)
	String	copyKeep;				// CHAR(3)
	String	keepDept;				// CHAR(3)
	String	keepLvl;				// CHAR(3)
	String	rtgDd;					// CHAR(8)
	String	modDd;					// CHAR(8)
	String	gathDd;					// CHAR(8)
	String 	gathType;				// CHAR(3)
	Integer	scnCnt;					// INTEGER
	String	lostStat;				// CHAR(3)
	String	reqNo;					// CHAR(9)
	String	regStat;				// CHAR(3)
	String	allWork_stat;			// CHAR(3)
	String	crteDd;					// CHAR(8)
	String	cleanReqNo;				// CHAR(9)
	String	cleanKeep;				// CHAR(3)
	String	ingestDd;				// CHAR(8)
	String hdYn;
	
	public String getTapeId() {
		return tapeId;
	}
	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}
	public String getCopyReqNo() {
		return copyReqNo;
	}
	public void setCopyReqNo(String copyReqNo) {
		this.copyReqNo = copyReqNo;
	}
	public String getOldReqNo() {
		return oldReqNo;
	}
	public void setOldReqNo(String oldReqNo) {
		this.oldReqNo = oldReqNo;
	}
	public String getGathNo() {
		return gathNo;
	}
	public void setGathNo(String gathNo) {
		this.gathNo = gathNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTapeNum() {
		return tapeNum;
	}
	public void setTapeNum(String tapeNum) {
		this.tapeNum = tapeNum;
	}
	public String getGathNm() {
		return gathNm;
	}
	public void setGathNm(String gathNm) {
		this.gathNm = gathNm;
	}
	public String getTapeKind() {
		return tapeKind;
	}
	public void setTapeKind(String tapeKind) {
		this.tapeKind = tapeKind;
	}
	public String getLentStat() {
		return lentStat;
	}
	public void setLentStat(String lentStat) {
		this.lentStat = lentStat;
	}
	public String getSecGr() {
		return secGr;
	}
	public void setSecGr(String secGr) {
		this.secGr = secGr;
	}
	public String getCopyYn() {
		return copyYn;
	}
	public void setCopyYn(String copyYn) {
		this.copyYn = copyYn;
	}
	public String getCopyKeep() {
		return copyKeep;
	}
	public void setCopyKeep(String copyKeep) {
		this.copyKeep = copyKeep;
	}
	public String getKeepDept() {
		return keepDept;
	}
	public void setKeepDept(String keepDept) {
		this.keepDept = keepDept;
	}
	public String getKeepLvl() {
		return keepLvl;
	}
	public void setKeepLvl(String keepLvl) {
		this.keepLvl = keepLvl;
	}
	public String getRtgDd() {
		return rtgDd;
	}
	public void setRtgDd(String rtgDd) {
		this.rtgDd = rtgDd;
	}
	public String getModDd() {
		return modDd;
	}
	public void setModDd(String modDd) {
		this.modDd = modDd;
	}
	public String getGathDd() {
		return gathDd;
	}
	public void setGathDd(String gathDd) {
		this.gathDd = gathDd;
	}
	public String getGathType() {
		return gathType;
	}
	public void setGathType(String gathType) {
		this.gathType = gathType;
	}
	public Integer getScnCnt() {
		return scnCnt;
	}
	public void setScnCnt(Integer scnCnt) {
		this.scnCnt = scnCnt;
	}
	public String getLostStat() {
		return lostStat;
	}
	public void setLostStat(String lostStat) {
		this.lostStat = lostStat;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getRegStat() {
		return regStat;
	}
	public void setRegStat(String regStat) {
		this.regStat = regStat;
	}
	public String getAllWork_stat() {
		return allWork_stat;
	}
	public void setAllWork_stat(String allWork_stat) {
		this.allWork_stat = allWork_stat;
	}
	public String getCrteDd() {
		return crteDd;
	}
	public void setCrteDd(String crteDd) {
		this.crteDd = crteDd;
	}
	public String getCleanReqNo() {
		return cleanReqNo;
	}
	public void setCleanReqNo(String cleanReqNo) {
		this.cleanReqNo = cleanReqNo;
	}
	public String getCleanKeep() {
		return cleanKeep;
	}
	public void setCleanKeep(String cleanKeep) {
		this.cleanKeep = cleanKeep;
	}
	public String getIngestDd() {
		return ingestDd;
	}
	public void setIngestDd(String ingestDd) {
		this.ingestDd = ingestDd;
	}
	
}

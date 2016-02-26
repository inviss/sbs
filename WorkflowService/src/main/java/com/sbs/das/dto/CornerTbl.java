package com.sbs.das.dto;

public class CornerTbl extends BaseObject {
	
	private static final long serialVersionUID = 4846434695020224964L;
	
	Long		cnId;					// BIGINT			// 코너ID
	Long		masterId;				// BIGINT			// 마스터ID
	String		cnNm;					// VARCHAR(150)		// 코너명
	String		cnTypeCd;				// CHAR(3)			// 코너유형코드
	String 		som;					// CHAR(8)			// 시작점
	String		eom;					// CHAR(8)			// 종료점
	String		cnInfo;				// LONGVARCHAR()	// 코너정보
	Integer		rpimgKfrmSeq;			// INTEGER			// 대표화면
	String		regDt;					// CHAR(14)			// 등록일시
	String		regrid;				// VARCHAR(15)		// 등록자명
	String		modDt;					// CHAR(14)			// 수정일시
	String		modrid;				// VARCHAR(15)		// 수정자명 
	Long		duration;				// BIGINT			// SOM과 EOM 사이의 길이
	Long		rpimgCtId;
	Long		sFrame;
	
	
	public Long getCnId() {
		return cnId;
	}
	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	public String getCnTypeCd() {
		return cnTypeCd;
	}
	public void setCnTypeCd(String cnTypeCd) {
		this.cnTypeCd = cnTypeCd;
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
	public String getCnInfo() {
		return cnInfo;
	}
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	public Integer getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(Integer rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
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
		this.duration = (duration == null) ? 0L : duration;
	}
	public Long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(Long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	public Long getSFrame() {
		return sFrame;
	}
	public void setSFrame(Long sFrame) {
		this.sFrame = sFrame;
	}
	
}

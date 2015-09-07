package com.sbs.das.dto;

public class ContentMapTbl extends BaseObject {
	
	private static final long serialVersionUID = -4429364705809118272L;
	
	Long 		ctId;				// BIGINT		컨텐츠 ID
	Long		pgmId;				// BIGINT		프로그램 ID
	Long		masterId;			// BIGINT		마스터 ID
	Long		cnId;				// BIGINT		코너 SEQ
	Integer		ctSeq;				// INTEGER		코너 ID
	Long		sDuration;
	Long		eDuration;
	String		regDt;				// CHAR(14)		등록일시
	String		regrid;				// VARCHAR(15)	등록자ID
	String		modDt;				// CHAR(14)		수정일시
	String		modrid;				// VARCHAR(15)	수정자명
	Integer		cnSeq;
	String		delDd;				// CHAR(8)      삭제요청일
	String      delYn;              // CHAR(1)      삭제여부
	
	
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getDelDd() {
		return delDd;
	}
	public void setDelDd(String delDd) {
		this.delDd = delDd;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Long getPgmId() {
		return pgmId;
	}
	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Long getCnId() {
		return cnId;
	}
	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}
	public Integer getCtSeq() {
		return ctSeq;
	}
	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
	}
	public Long getsDuration() {
		return sDuration;
	}
	public void setsDuration(Long sDuration) {
		this.sDuration = sDuration;
	}
	public Long geteDuration() {
		return eDuration;
	}
	public void seteDuration(Long eDuration) {
		this.eDuration = eDuration;
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
	public Integer getCnSeq() {
		return cnSeq;
	}
	public void setCnSeq(Integer cnSeq) {
		this.cnSeq = cnSeq;
	}
	
}

package com.sbs.das.dto;

public class TimeRistSetTbl extends BaseObject {

	private static final long serialVersionUID = -6225757327340645427L;
	
	private Integer seq;			//SEQ			Integer			순번
	private Integer week;			//WEEK			Integer			주
	private String sTime;			//S_TIME		VARCHAR(6)		시작시간 (010000)
	private String eTime;			//E_TIME		VARCHAR(6)  	종료시간 (020000)
	private String ristClfCd;		//RIST_CLF_CD	CHARACTER(3)	주석구분코드 (001 명장면 002 방송심의제재 003 사용금지 004 담당PD확인 005 사내심의사항)
	private String regDt;			//REG_DT		VARCHAR(14)		등록일시
	private String modId;			//MOD_ID		VARCHAR(10)		수정자ID
	private String modDt;			//MOD_DT		VARCHAR(14)		수정일시
	private String regId;			//REG_ID		VARCHAR(10)		등록자ID
	private String pdsPgmId;		//PDS_PGM_ID					PDS 프로그램ID
	
	
	public String getPdsPgmId() {
		return pdsPgmId;
	}
	public void setPdsPgmId(String pdsPgmId) {
		this.pdsPgmId = pdsPgmId;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getSTime() {
		return sTime;
	}
	public void setSTime(String sTime) {
		this.sTime = sTime;
	}
	public String getETime() {
		return eTime;
	}
	public void setETime(String eTime) {
		this.eTime = eTime;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModId() {
		return modId;
	}
	public void setModId(String modId) {
		this.modId = modId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
}

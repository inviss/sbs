package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="timeristinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeRistInfo {
	
	@XmlElement(name="SEQ")
	private Long seq;
	@XmlElement(name="S_TIME")
	private String sTime;
	@XmlElement(name="E_TIME")
	private String eTime;
	@XmlElement(name="RIST_CLF_CD")
	private String ristClfCd;
	@XmlElement(name="RIST_CLF_NM")
	private String ristClfNm;
	@XmlElement(name="REG_DT")
	private String regDt;
	@XmlElement(name="REG_ID")
	private String regId;
	@XmlElement(name="WEEK")
	private Long week;
	@XmlElement(name="WEEK_NM")
	private String weekNm;
	@XmlElement(name="PDS_PGM_ID")
	private String pdsPgmId;
	@XmlElement(name="PDS_PGM_NM")
	private String pdsPgmNm;
	
	
	
	public String getPdsPgmId() {
		return pdsPgmId;
	}
	public void setPdsPgmId(String pdsPgmId) {
		this.pdsPgmId = pdsPgmId;
	}
	public String getPdsPgmNm() {
		return pdsPgmNm;
	}
	public void setPdsPgmNm(String pdsPgmNm) {
		this.pdsPgmNm = pdsPgmNm;
	}
	public String getWeekNm() {
		return weekNm;
	}
	public void setWeekNm(String weekNm) {
		this.weekNm = weekNm;
	}
	public Long getWeek() {
		return week;
	}
	public void setWeek(Long week) {
		this.week = week;
	}
	public String getRistClfNm() {
		return ristClfNm;
	}
	public void setRistClfNm(String ristClfNm) {
		this.ristClfNm = ristClfNm;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getsTime() {
		return sTime;
	}
	public void setsTime(String sTime) {
		this.sTime = sTime;
	}
	public String geteTime() {
		return eTime;
	}
	public void seteTime(String eTime) {
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
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	
}

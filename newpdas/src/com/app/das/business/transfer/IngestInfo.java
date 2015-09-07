package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ingestinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class IngestInfo {
	
	@XmlElement(name="META_CT_ID")
	private Long ctId;
	@XmlElement(name="META_CTI_ID")
	private Long ctiId;
	@XmlElement(name="META_CT_NM")
	private String ctNm;
	@XmlElement(name="META_CT_LENG")
	private String ctLeng;
	@XmlElement(name="META_REG_DT")
	private String regDt;
	@XmlElement(name="META_M2_SZ")
	private Long m2Sz;
	@XmlElement(name="META_M4_SZ")
	private Long m4Sz;
	@XmlElement(name="META_INGEST_EQ_ID")
	private String ingestEqId;
	@XmlElement(name="META_VD_HRESOL")
	private Integer vdHresol;
	@XmlElement(name="META_VD_VRESOL")
	private Integer vdVresol;
	@XmlElement(name="META_BIT_RT")
	private String bitRt;
	@XmlElement(name="META_FRM_PER_SEC")
	private String frmPerSec;
	@XmlElement(name="META_AUD_SAMP_FRQ")
	private String audSampFrq;
	@XmlElement(name="META_AUDIO_BDWT")
	private String audioBdwt;
	@XmlElement(name="META_TOT_KFRM_NUMS")
	private Integer totKfrmNums;
	@XmlElement(name="META_CT_CLA")
	private String ctCla;
	@XmlElement(name="META_DEL_DD")
	private String delDd;
	
	
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
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public Long getM2Sz() {
		return m2Sz;
	}
	public void setM2Sz(Long m2Sz) {
		this.m2Sz = m2Sz;
	}
	public Long getM4Sz() {
		return m4Sz;
	}
	public void setM4Sz(Long m4Sz) {
		this.m4Sz = m4Sz;
	}
	public String getIngestEqId() {
		return ingestEqId;
	}
	public void setIngestEqId(String ingestEqId) {
		this.ingestEqId = ingestEqId;
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
	public Integer getTotKfrmNums() {
		return totKfrmNums;
	}
	public void setTotKfrmNums(Integer totKfrmNums) {
		this.totKfrmNums = totKfrmNums;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public String getDelDd() {
		return delDd;
	}
	public void setDelDd(String delDd) {
		this.delDd = delDd;
	}
	
}

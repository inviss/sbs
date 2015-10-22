package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.app.das.util.AdaptorCDATA;

@XmlRootElement(name="annotinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnnotInfo {
	
	@XmlElement(name="ANNOT_ID")
	private Long annotId;
	@XmlElement(name="CT_ID")
	private Long ctId;
	@XmlElement(name="ANNOT_CLF_CD")
	private String annotClfCd;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="ANNOT_CLF_CONT")
	private String annotClfCont;
	@XmlElement(name="SOM")
	private String som;
	@XmlElement(name="EOM")
	private String eom;
	@XmlElement(name="GUBUN")
	private String gubun;
	
	@XmlElement(name="ANNOT_CLF_NM")
	private String annotClfNm;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="ANNOT_CONT")
	private String annotCont;
	@XmlElement(name="ENTIRE_YN")
	private String entireYn;
	@XmlElement(name="ENTIRE_RIST_CLF_CD")
	private String entireRistClfCd;
	
	
	public String getEntireRistClfCd() {
		return entireRistClfCd;
	}
	public void setEntireRistClfCd(String entireRistClfCd) {
		this.entireRistClfCd = entireRistClfCd;
	}
	public String getEntireYn() {
		return entireYn;
	}
	public void setEntireYn(String entireYn) {
		this.entireYn = entireYn;
	}
	public Long getAnnotId() {
		return annotId;
	}
	public void setAnnotId(Long annotId) {
		this.annotId = annotId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
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
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getAnnotClfNm() {
		return annotClfNm;
	}
	public void setAnnotClfNm(String annotClfNm) {
		this.annotClfNm = annotClfNm;
	}
	public String getAnnotCont() {
		return annotCont;
	}
	public void setAnnotCont(String annotCont) {
		this.annotCont = annotCont;
	}
	
}

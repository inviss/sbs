package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="annotinfo")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnnotInfo {
	
	@XmlElement(name="annot_id")
	private Long annotId;
	@XmlElement(name="ct_id")
	private Long ctId;
	@XmlElement(name="annot_clf_cd")
	private String annotClfCd;
	@XmlElement(name="annot_clf_cont")
	private String annotClfCont;
	@XmlElement(name="som")
	private String som;
	@XmlElement(name="eom")
	private String eom;
	@XmlElement(name="gubun")
	private String gubun;
	
	@XmlElement(name="annot_clf_nm")
	private String annotClfNm;
	@XmlElement(name="annot_cont")
	private String annotCont;
	
	
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

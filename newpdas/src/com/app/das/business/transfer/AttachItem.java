package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.app.das.util.AdaptorCDATA;

@XmlRootElement(name="attach_item")
@XmlAccessorType(XmlAccessType.FIELD)
public class AttachItem {
	
	@XmlElement(name="FL_NM")
	private String flNm;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="DESC")
	private String desc;
	@XmlElement(name="FL_SZ")
	private Long flSz;
	@XmlElement(name="FL_PATH")
	private String flPath;
	@XmlElement(name="SEQ")
	private Long seq;
	@XmlElement(name="ATTC_FILE_TYPE_CD")
	private String attcFileType;
	@XmlElement(name="ATTC_CLF_CD")
	private String attcClfCd;
	@XmlJavaTypeAdapter(value = AdaptorCDATA.class)
	@XmlElement(name="ORG_FILE_NM")
	private String orgFileNm;
	@XmlElement(name="CAPTION_TYPE")
	private String captionType;
	@XmlElement(name="CAPTION_TYPE_NM")
	private String captionTypeNm;
	
	
	
	public String getCaptionTypeNm() {
		return captionTypeNm;
	}
	public void setCaptionTypeNm(String captionTypeNm) {
		this.captionTypeNm = captionTypeNm;
	}
	public String getCaptionType() {
		return captionType;
	}
	public void setCaptionType(String captionType) {
		this.captionType = captionType;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Long getFlSz() {
		return flSz;
	}
	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getAttcFileType() {
		return attcFileType;
	}
	public void setAttcFileType(String attcFileType) {
		this.attcFileType = attcFileType;
	}
	public String getAttcClfCd() {
		return attcClfCd;
	}
	public void setAttcClfCd(String attcClfCd) {
		this.attcClfCd = attcClfCd;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	
}

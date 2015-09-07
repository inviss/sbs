package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 관련영상
 * @author Administrator
 *
 */
@XmlRootElement(name="relation")
@XmlAccessorType(XmlAccessType.FIELD)
public class Relation {
	
	@XmlElement(name="REL_MASTER_ID")
	private Long masterId;
	@XmlElement(name="REL_BRD_DD")
	private String brdDd;
	@XmlElement(name="REL_REQ_CD")
	private String reqCd;
	@XmlElement(name="REL_BRD_LENG")
	private String brdLeng;
	@XmlElement(name="REL_SUB_TTL")
	private String subTtl;
	@XmlElement(name="REL_ASP_RTO_CD")
	private String aspRtoCd;
	@XmlElement(name="rel_preview_subj")
	private String previewSubj;
	@XmlElement(name="ct_id")
	private String ctId;
	@XmlElement(name="ct_nm")
	private String ctNm;
	@XmlElement(name="REL_VD_QLTY")
	private String vdQlty;
	
	
	
	
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getPreviewSubj() {
		return previewSubj;
	}
	public void setPreviewSubj(String previewSubj) {
		this.previewSubj = previewSubj;
	}
	
}

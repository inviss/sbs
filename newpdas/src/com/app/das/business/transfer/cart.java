package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cartItems")
@XmlAccessorType(XmlAccessType.FIELD)
public class cart {
	
	@XmlElement(name="down_subj")
	private String downSubj;
	@XmlElement(name="fromdate")
	private String fromdate;
	@XmlElement(name="enddate")
	private String enddate;
	@XmlElement(name="userid")
	private String userid;
	@XmlElement(name="cart_no")
	private String cartNo;
	@XmlElement(name="cart_seq")
	private String cartSeq;
//20120808 추가
	@XmlElement(name="req_nm")
	private String reqNm;
	@XmlElement(name="storage_name")
	private String storageName;
	@XmlElement(name="media_id")
	private String mediaId;
	@XmlElement(name="ct_cont")
	private String ctCont;
	@XmlElement(name="req_dt")
	private String reqDt;
	@XmlElement(name="system")
	private String system;

	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(String cartSeq) {
		this.cartSeq = cartSeq;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getCtCont() {
		return ctCont;
	}
	public void setCtCont(String ctCont) {
		this.ctCont = ctCont;
	}
	public String getCartNo() {
		return cartNo;
	}
	public void setCartNo(String cartNo) {
		this.cartNo = cartNo;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}

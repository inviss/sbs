package com.sbs.das.dto;

public class NotReportMsgTbl extends BaseObject {
	
	private static final long serialVersionUID = -3843178718441535575L;
	
	String 	uid;
	String 	dasEqClfCd;
	Integer	retryNo;
	String	regDt;
	String	modDt;
	String  host;
	String  port;
	String	msg;
	String	checkstamp;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDasEqClfCd() {
		return dasEqClfCd;
	}
	public void setDasEqClfCd(String dasEqClfCd) {
		this.dasEqClfCd = dasEqClfCd;
	}
	public Integer getRetryNo() {
		return retryNo;
	}
	public void setRetryNo(Integer retryNo) {
		this.retryNo = retryNo;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCheckstamp() {
		return checkstamp;
	}
	public void setCheckstamp(String checkstamp) {
		this.checkstamp = checkstamp;
	}
	
}

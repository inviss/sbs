package com.sbs.das.dto;

public class ErrorLogTbl extends BaseObject {
	
	private static final long serialVersionUID = 6335162632116476402L;
	
	private String 		serverNm;    			// CHARACTER(12)       
	private String 		errorType;         		// CHARACTER(12)       
	private String		regDt;         			// INTEGER             
	private String 		errorCont;       		// CHARACTER(3)      
	private String		jobId;
	private String 		processId;
	private Integer		eqId;
	private Long		key;
	
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public Integer getEqId() {
		return eqId;
	}
	public void setEqId(Integer eqId) {
		this.eqId = eqId;
	}
	public Long getKey() {
		return key;
	}
	public void setKey(Long key) {
		this.key = key;
	}
	public String getServerNm() {
		return serverNm;
	}
	public void setServerNm(String serverNm) {
		this.serverNm = serverNm;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getErrorCont() {
		return errorCont;
	}
	public void setErrorCont(String errorCont) {
		this.errorCont = errorCont;
	}
	
}

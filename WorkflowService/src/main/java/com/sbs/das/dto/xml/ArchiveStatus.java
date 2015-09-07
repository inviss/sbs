package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("status")
public class ArchiveStatus {
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("job_status")
	private String jobStatus;					// 상태값(I: 진행중, C: 완료, Q: 대기, E: 에러)
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("object_name")
	private String objectName;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("updt_dtm")
	private String updtDtm;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("progress")
	private Integer progress;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("job_id")
	private String jobId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("restore_id")
	private Long restoreId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("error_id")
	private String errorId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("error_msg")
	private String errorMsg;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("filesize")
	private Long filesize;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_no")
	private Long cartNo;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("cart_seq")
	private Integer cartSeq;
	
	// 2012.05.18
	private String systemName;
	private String systemHost;
	private String lastDatetime;
	private String status;
	private String message;
	
	// 2012.07.26
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("process_cd")
	private String processCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("eq_id")
	private String eqId;
	

	public String getEqId() {
		return eqId;
	}

	public void setEqId(String eqId) {
		this.eqId = eqId;
	}

	public String getProcessCd() {
		return processCd;
	}

	public void setProcessCd(String processCd) {
		this.processCd = processCd;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemHost() {
		return systemHost;
	}

	public void setSystemHost(String systemHost) {
		this.systemHost = systemHost;
	}

	public String getLastDatetime() {
		return lastDatetime;
	}

	public void setLastDatetime(String lastDatetime) {
		this.lastDatetime = lastDatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCartNo() {
		return cartNo;
	}

	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}

	public Integer getCartSeq() {
		return cartSeq;
	}

	public void setCartSeq(Integer cartSeq) {
		this.cartSeq = cartSeq;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Long getRestoreId() {
		return restoreId;
	}

	public void setRestoreId(Long restoreId) {
		this.restoreId = restoreId;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getUpdtDtm() {
		return updtDtm;
	}

	public void setUpdtDtm(String updtDtm) {
		this.updtDtm = updtDtm;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
}

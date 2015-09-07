package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("report")
public class Report {
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("das_eq_id")
	private Integer dasEqId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_eq_ps_cd")
	private String dasEqPsCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("status")
	private String status;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("file_path")
	private String filePath;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("req_id")
	private String reqId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("job_id")
	private String jobId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("mcuid")
	private String mcuid;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("mcu_seq")
	private Integer mcuSeq;

	
	public String getMcuid() {
		return mcuid;
	}

	public void setMcuid(String mcuid) {
		this.mcuid = mcuid;
	}

	public Integer getMcuSeq() {
		return mcuSeq;
	}

	public void setMcuSeq(Integer mcuSeq) {
		this.mcuSeq = mcuSeq;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Integer getDasEqId() {
		return dasEqId;
	}

	public void setDasEqId(Integer dasEqId) {
		this.dasEqId = dasEqId;
	}

	public String getDasEqPsCd() {
		return dasEqPsCd;
	}

	public void setDasEqPsCd(String dasEqPsCd) {
		this.dasEqPsCd = dasEqPsCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
}

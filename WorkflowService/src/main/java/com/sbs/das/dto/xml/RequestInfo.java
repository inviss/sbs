package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("info")
public class RequestInfo {
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("das_eq_id")
	private Integer dasEqId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_eq_ps_cd")
	private String dasEqPsCd;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cti_id")
	private long ctiId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private long masterId;
	
	@XStreamConverter(TextConverter.class)
	private String som;
	
	@XStreamConverter(TextConverter.class)
	private String eom;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_no")
	private long cartNo;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cart_seq")
	private String cartSeq;
	
	@XStreamConverter(TextConverter.class)
	private String regrid;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("req_id")
	private String reqId;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("das_eq_nm")
	private String dasEqNm;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_eq_clf_cd")
	private String dasEqClfCd;
	
	@XStreamConverter(TextConverter.class)
	private String status;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("out_system_id")
	private String outSystemId;
	
	@XStreamConverter(TextConverter.class)
	private String priority;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("sgl_group_nm")
	private String sglGroupNm;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("job_id")
	private Long jobId;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("file_path")
	private String filePath;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("kfrm_path")
	private String kfrmPath;
	
	@XStreamConverter(TextConverter.class)
	private String eoms;
	
	@XStreamConverter(TextConverter.class)
	private String soms;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("req_method")
	private String reqMethod;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("copy_to_group")
	private String copyToGroup;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("limit_day")
	private String limitDay;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("pds_program_id")
	private String pdsProgramId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("archive_type")
	private String archiveType;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("dtl_type")
	private String dtlType;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("disk_path")
	private String diskPath;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("gubun")
	private String gubun;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("co_cd")
	private String coCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("vd_qlty")
	private String vdQlty;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("filename")
	private String filename;
	
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getKfrmPath() {
		return kfrmPath;
	}

	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getCoCd() {
		return coCd;
	}

	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getDiskPath() {
		return diskPath;
	}

	public void setDiskPath(String diskPath) {
		this.diskPath = diskPath;
	}

	public String getDtlType() {
		return dtlType;
	}

	public void setDtlType(String dtlType) {
		this.dtlType = dtlType;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

	public String getPdsProgramId() {
		return pdsProgramId;
	}

	public void setPdsProgramId(String pdsProgramId) {
		this.pdsProgramId = pdsProgramId;
	}

	public String getLimitDay() {
		return limitDay;
	}

	public void setLimitDay(String limitDay) {
		this.limitDay = limitDay;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public String getCopyToGroup() {
		return copyToGroup;
	}

	public void setCopyToGroup(String copyToGroup) {
		this.copyToGroup = copyToGroup;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getDasEqNm() {
		return dasEqNm;
	}

	public void setDasEqNm(String dasEqNm) {
		this.dasEqNm = dasEqNm;
	}

	public String getDasEqClfCd() {
		return dasEqClfCd;
	}

	public void setDasEqClfCd(String dasEqClfCd) {
		this.dasEqClfCd = dasEqClfCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutSystemId() {
		return outSystemId;
	}

	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getSglGroupNm() {
		return sglGroupNm;
	}

	public void setSglGroupNm(String sglGroupNm) {
		this.sglGroupNm = sglGroupNm;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getEoms() {
		return eoms;
	}

	public void setEoms(String eoms) {
		this.eoms = eoms;
	}

	public String getSoms() {
		return soms;
	}

	public void setSoms(String soms) {
		this.soms = soms;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
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

	public long getCtiId() {
		return ctiId;
	}

	public void setCtiId(long ctiId) {
		this.ctiId = ctiId;
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

	public long getCartNo() {
		return cartNo;
	}

	public void setCartNo(long cartNo) {
		this.cartNo = cartNo;
	}

	public String getCartSeq() {
		return cartSeq;
	}

	public void setCartSeq(String cartSeq) {
		this.cartSeq = cartSeq;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	
}

package com.sbs.das.dto;

public class ContentDownTbl extends BaseObject {
	
	private static final long serialVersionUID = -8429621064988966780L;
	
	Long num;				//	BIGINT	DEFAULT ,
	Long cartNo;			//	BIGINT	DEFAULT ,
	Integer cartSeq;		//	INTEGER,
	Long ctiId;				//	BIGINT,
	String status;			//	VARCHAR(10),
	String objName;			//	VARCHAR(18),
	String jobStatus;		//	VARCHAR(1)	NOT NULL	DEFAULT 'W',
	String regUser;			//	VARCHAR(30),
	String updtUser;		//	VARCHAR(30),
	String regDtm;			//	VARCHAR(16),
	String updtDtm;			//	VARCHAR(16),
	String progress;		//	VARCHAR(4)	NOT NULL	DEFAULT '0',
	String path;			//	VARCHAR(255),
	String filename;		//	VARCHAR(255),
	String begintc;			//	VARCHAR(11),
	Integer scount;			//	INTEGER	NOT NULL	DEFAULT ,
	String endtc;			//	VARCHAR(11),
	Long som;				//	BIGINT	DEFAULT ,
	Long eom;				//	BIGINT	DEFAULT ,
	String useYn;			//	CHARACTER(1)	DEFAULT 'Y',
	String title;			//	VARCHAR(300)
	String delDd;			//  CHAR(8) DEFAULT '삭제일'
	Long filesize;			//  BIGINT
	String tcStatus;        //  CHAR(1) DEFAULT 'N' TC 작업상태
	Long tcCtiId;
	String tcPath;
	String tcGb;			//  CHAR(1) DEFAULT 'S' 작업구분 ['S': mp4, 'H': mxf]
	String dtlYn;

	public String getDtlYn() {
		return dtlYn;
	}
	public void setDtlYn(String dtlYn) {
		this.dtlYn = dtlYn;
	}
	public String getTcGb() {
		return tcGb;
	}
	public void setTcGb(String tcGb) {
		this.tcGb = tcGb;
	}
	public Long getTcCtiId() {
		return tcCtiId;
	}
	public void setTcCtiId(Long tcCtiId) {
		this.tcCtiId = tcCtiId;
	}
	public String getTcPath() {
		return tcPath;
	}
	public void setTcPath(String tcPath) {
		this.tcPath = tcPath;
	}
	public String getTcStatus() {
		return tcStatus;
	}
	public void setTcStatus(String tcStatus) {
		this.tcStatus = tcStatus;
	}
	public Long getFilesize() {
		return filesize;
	}
	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}
	public String getDelDd() {
		return delDd;
	}
	public void setDelDd(String delDd) {
		this.delDd = delDd;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
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
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getRegUser() {
		return regUser;
	}
	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}
	public String getUpdtUser() {
		return updtUser;
	}
	public void setUpdtUser(String updtUser) {
		this.updtUser = updtUser;
	}
	public String getRegDtm() {
		return regDtm;
	}
	public void setRegDtm(String regDtm) {
		this.regDtm = regDtm;
	}
	public String getUpdtDtm() {
		return updtDtm;
	}
	public void setUpdtDtm(String updtDtm) {
		this.updtDtm = updtDtm;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getBegintc() {
		return begintc;
	}
	public void setBegintc(String begintc) {
		this.begintc = begintc;
	}
	public Integer getScount() {
		return scount;
	}
	public void setScount(Integer scount) {
		this.scount = scount;
	}
	public String getEndtc() {
		return endtc;
	}
	public void setEndtc(String endtc) {
		this.endtc = endtc;
	}
	public Long getSom() {
		return som;
	}
	public void setSom(Long som) {
		this.som = som;
	}
	public Long getEom() {
		return eom;
	}
	public void setEom(Long eom) {
		this.eom = eom;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}

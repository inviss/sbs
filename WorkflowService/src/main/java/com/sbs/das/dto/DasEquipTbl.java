package com.sbs.das.dto;


public class DasEquipTbl extends BaseObject {
	
	private static final long serialVersionUID = 7144178972367096549L;
	
	Integer		dasEqId;				// integer                                                 
	String  	dasEqPsCd;                                                                	
	String  	dasEqClfCd;				// char(3)				das_eq_clf_cd;		// das ???????????? 	
	Integer		dasEqSeq;				// integer                                                 
	String  	dasEqNm;				// varchar(30)			das_eq_nm;			// das ??????         	
	String  	dasEqUseIp;				// varchar(15)			das_eq_use_ip;		// das ????????ip  	
	String  	dasEqUsePort;			// varchar(15)			das_eq_use_port;	// das ????????????	
	String  	dasEqUseUrl;			// varchar(256)                                          	
	String  	dasEqUseId;				// varchar(256)                                          	
	String  	dasEqUsePasswd;			// varchar(256)                                          	
	String  	dasWorkstatCd;    		//character(3)    		not null  default '',                  
	Long		ctiId;           		// bigint          		not null  default 0,                   
	String  	outSystemId;                                                               	
	String  	reqUsrid;         		// varchar(15)     		not null  default '',                  
	String  	prgrs;            		// varchar(10)     		not null  default '',                  
	Long		downVol;          		// bigint          		not null  default 0,                   
	Long		totalSize;        		// bigint          		not null  default 0,                   
	Integer		logRcdPeriod;     		// integer         		not null  default 0,                   
	String 		useYn;					// char(1)				use_yn;				// ????????              
	String  	regrid;					// varchar(15)			regrid;					// ??????id          	
	String  	regDt;					// char(14)				reg_dt;					// ????????          	
	String  	modrid;					// varchar(15)			modrid;					// ????????          	
	String  	modDt;					// char(14)				mod_dt;					// ????????          	
	String  	flPath;
	
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
	public String getDasEqClfCd() {
		return dasEqClfCd;
	}
	public void setDasEqClfCd(String dasEqClfCd) {
		this.dasEqClfCd = dasEqClfCd;
	}
	public Integer getDasEqSeq() {
		return dasEqSeq;
	}
	public void setDasEqSeq(Integer dasEqSeq) {
		this.dasEqSeq = dasEqSeq;
	}
	public String getDasEqNm() {
		return dasEqNm;
	}
	public void setDasEqNm(String dasEqNm) {
		this.dasEqNm = dasEqNm;
	}
	public String getDasEqUseIp() {
		return dasEqUseIp;
	}
	public void setDasEqUseIp(String dasEqUseIp) {
		this.dasEqUseIp = dasEqUseIp;
	}
	public String getDasEqUsePort() {
		return dasEqUsePort;
	}
	public void setDasEqUsePort(String dasEqUsePort) {
		this.dasEqUsePort = dasEqUsePort;
	}
	public String getDasEqUseUrl() {
		return dasEqUseUrl;
	}
	public void setDasEqUseUrl(String dasEqUseUrl) {
		this.dasEqUseUrl = dasEqUseUrl;
	}
	public String getDasEqUseId() {
		return dasEqUseId;
	}
	public void setDasEqUseId(String dasEqUseId) {
		this.dasEqUseId = dasEqUseId;
	}
	public String getDasEqUsePasswd() {
		return dasEqUsePasswd;
	}
	public void setDasEqUsePasswd(String dasEqUsePasswd) {
		this.dasEqUsePasswd = dasEqUsePasswd;
	}
	public String getDasWorkstatCd() {
		return dasWorkstatCd;
	}
	public void setDasWorkstatCd(String dasWorkstatCd) {
		this.dasWorkstatCd = dasWorkstatCd;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getOutSystemId() {
		return outSystemId;
	}
	public void setOutSystemId(String outSystemId) {
		this.outSystemId = outSystemId;
	}
	public String getReqUsrid() {
		return reqUsrid;
	}
	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}
	public String getPrgrs() {
		return prgrs;
	}
	public void setPrgrs(String prgrs) {
		this.prgrs = prgrs;
	}
	public Long getDownVol() {
		return downVol;
	}
	public void setDownVol(Long downVol) {
		this.downVol = downVol;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public Integer getLogRcdPeriod() {
		return logRcdPeriod;
	}
	public void setLogRcdPeriod(Integer logRcdPeriod) {
		this.logRcdPeriod = logRcdPeriod;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}    
	
	
}

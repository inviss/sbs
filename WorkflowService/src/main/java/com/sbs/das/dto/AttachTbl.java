package com.sbs.das.dto;

public class AttachTbl extends BaseObject {

	private static final long serialVersionUID = -1539764106727815204L;

	private long mothrDataId;
	private int seq;
	private String attcFileTypeCd;
	private String attcClfCd;
	private String flNm;
	private long flSz;
	private String flPath;
	private String orgFileNm;
	private String regDt;
	private String regrid;
	private String modDt;
	private String modrid;
	private String captionType;
	
	public long getMothrDataId() {
		return mothrDataId;
	}
	public void setMothrDataId(long mothrDataId) {
		this.mothrDataId = mothrDataId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getAttcFileTypeCd() {
		return attcFileTypeCd;
	}
	public void setAttcFileTypeCd(String attcFileTypeCd) {
		this.attcFileTypeCd = attcFileTypeCd;
	}
	public String getAttcClfCd() {
		return attcClfCd;
	}
	public void setAttcClfCd(String attcClfCd) {
		this.attcClfCd = attcClfCd;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public long getFlSz() {
		return flSz;
	}
	public void setFlSz(long flSz) {
		this.flSz = flSz;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getCaptionType() {
		return captionType;
	}
	public void setCaptionType(String captionType) {
		this.captionType = captionType;
	}
	
}

package com.sbs.das.dto;

public class CopyInfoTbl extends BaseObject {

	private static final long serialVersionUID = -622321559853781076L;
	
	private Long pgmId;
	private String copyYn; // 자동복본 생성여부 ('Y', 'N')
	private String regId;
	private String regDt;
	private String cmsPgmId;
	
	public Long getPgmId() {
		return pgmId;
	}
	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
	}
	public String getCopyYn() {
		return copyYn;
	}
	public void setCopyYn(String copyYn) {
		this.copyYn = copyYn;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getCmsPgmId() {
		return cmsPgmId;
	}
	public void setCmsPgmId(String cmsPgmId) {
		this.cmsPgmId = cmsPgmId;
	}
	
}

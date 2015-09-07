package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 편성/심의 및 저작권/tape 정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class TapeInfoDO extends DTO 
{
	/**
	 * 방송시작일
	 */
	//private String brdBgnDd    = Constants.BLANK;
	/**
	 * 방송종료일
	 */
	//private String brdEndDd    = Constants.BLANK;
	/**
	 * 시청등급코드
	 */
	private String viewGrCd    = Constants.BLANK;
	/**
	 * 시청율
	 */
	private String pgmRate     = Constants.BLANK;
	/**
	 * 심의결과코드
	 */
	private String dlbrCd      = Constants.BLANK;
	/**
	 * 저작권자명
	 */
	private String cprtrNm     = Constants.BLANK;
	/**
	 * 저작권형태코드
	 */
	private String cprtType    = Constants.BLANK;
	/**
	 * 저작권형태설
	 */
	private String cprtTypeDsc = Constants.BLANK;
	
	/**
	 * 부본청구번호
	 */
	private String copyReqNo = Constants.BLANK;
	/**
	 * 부본보관
	 */
	private String copyKeep = Constants.BLANK;
	/**
	 * 부본보관
	 */
	private String KeepDept = Constants.BLANK;
	/**
	 * 클린청구번호
	 */
	private String cleanReqNo = Constants.BLANK;
	/**
	 * 클린보관
	 */
	private String cleanKeep = Constants.BLANK;
	/**
	 * 테이프번호
	 */
	private String tapeNum = Constants.BLANK;
	/**
	 * 테이프종류
	 */
	private String tapeKind = Constants.BLANK;
	
	/*
	public String getBrdBgnDd() {
		return brdBgnDd;
	}
	public void setBrdBgnDd(String brdBgnDd) {
		this.brdBgnDd = brdBgnDd;
	}
	public String getBrdEndDd() {
		return brdEndDd;
	}
		public void setBrdEndDd(String brdEndDd) {
		this.brdEndDd = brdEndDd;
	}
	*/
	
	public String getCprtrNm() {
		return cprtrNm;
	}
	public void setCprtrNm(String cprtrNm) {
		this.cprtrNm = cprtrNm;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getCprtTypeDsc() {
		return cprtTypeDsc;
	}
	public void setCprtTypeDsc(String cprtTypeDsc) {
		this.cprtTypeDsc = cprtTypeDsc;
	}
	public String getDlbrCd() {
		return dlbrCd;
	}
	public void setDlbrCd(String dlbrCd) {
		this.dlbrCd = dlbrCd;
	}
	public String getPgmRate() {
		return pgmRate;
	}
	public void setPgmRate(String pgmRate) {
		this.pgmRate = pgmRate;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getCleanKeep() {
		return cleanKeep;
	}
	public void setCleanKeep(String cleanKeep) {
		this.cleanKeep = cleanKeep;
	}
	public String getCleanReqNo() {
		return cleanReqNo;
	}
	public void setCleanReqNo(String cleanReqNo) {
		this.cleanReqNo = cleanReqNo;
	}
	public String getCopyKeep() {
		return copyKeep;
	}
	public void setCopyKeep(String copyKeep) {
		this.copyKeep = copyKeep;
	}
	public String getCopyReqNo() {
		return copyReqNo;
	}
	public void setCopyReqNo(String copyReqNo) {
		this.copyReqNo = copyReqNo;
	}
	public String getTapeKind() {
		return tapeKind;
	}
	public void setTapeKind(String tapeKind) {
		this.tapeKind = tapeKind;
	}
	public String getTapeNum() {
		return tapeNum;
	}
	public void setTapeNum(String tapeNum) {
		this.tapeNum = tapeNum;
	}
	public String getKeepDept() {
		return KeepDept;
	}
	public void setKeepDept(String keepDept) {
		KeepDept = keepDept;
	}
	
	
}

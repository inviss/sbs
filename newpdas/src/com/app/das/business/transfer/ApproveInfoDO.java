package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * 프로그램별 승인정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ApproveInfoDO {
	
	/** 
	 * 프로그램id
	 */
	private String pgm_id = Constants.BLANK;
	/** 
	 * 프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	/** 
	 * 승인자 구분
	 */
	private String app_gubun = Constants.BLANK;
	/** 
	 * 소속부서
	 */
	private String dept_cd = Constants.BLANK;
	/** 
	 * 승인자 사번
	 */
	private String user_no  = Constants.BLANK;
	/** 
	 * 승인자명
	 */
	private String user_nm = Constants.BLANK;
	/** 
	 * 직책
	 */
	private String position = Constants.BLANK;
	
	
	/** 
	 * 부서명
	 */
	private String dept_nm = Constants.BLANK;
	
	/** 
	 * 프로그램코드
	 */
	private String pgm_cd = Constants.BLANK;
	
	
	
	//2012.4.24
	
	/** 
	 * 직원유형
	 */
	private String acct_code = Constants.BLANK;
	/** 
	 * 승인자 id
	 */
	private String user_id = Constants.BLANK;
	
	/** 
	 * 회사명
	 */
	private String conm = Constants.BLANK;
	
	/** 
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	
	
	
	
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getConm() {
		return conm;
	}
	public void setConm(String conm) {
		this.conm = conm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getAcct_code() {
		return acct_code;
	}
	public void setAcct_code(String acctCode) {
		acct_code = acctCode;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String userNo) {
		user_no = userNo;
	}
	public String getPgm_cd() {
		return pgm_cd;
	}
	public void setPgm_cd(String pgmCd) {
		pgm_cd = pgmCd;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}
	public String getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(String pgmId) {
		pgm_id = pgmId;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}
	public String getApp_gubun() {
		return app_gubun;
	}
	public void setApp_gubun(String appGubun) {
		app_gubun = appGubun;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}

	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
	
	
}

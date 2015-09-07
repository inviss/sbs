package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 직원 목록 조회시 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class OtherDBuserInfoDO extends DTO
{
	/**
	 * 유저 id
	 */
	private String sbs_user_id = Constants.BLANK;
	/**
	 * 사번
	 */
	private String user_num = Constants.BLANK;
	/**
	 * 주민번호
	 */
	private String per_reg_no = Constants.BLANK;
	/**
	 * 삭제여부
	 */
	private String delte_yn = Constants.BLANK;
	/**
	 * 등록상태
	 */
	private String approve_Status = Constants.BLANK;
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	/**
	 * 사용자명
	 */
	private String user_nm = Constants.BLANK;
	/**
	 * 암호
	 */
	private String pasword = Constants.BLANK;
	/**
	 * 부서
	 */
	private String dept_cd = Constants.BLANK;
	/**
	 * 직원유형
	 */
	private String acct_code = Constants.BLANK;
	public String getAcct_code() {
		return acct_code;
	}
	public void setAcct_code(String acctCode) {
		acct_code = acctCode;
	}
	public String getSbs_user_id() {
		return sbs_user_id;
	}
	public void setSbs_user_id(String sbsUserId) {
		sbs_user_id = sbsUserId;
	}
	public String getUser_num() {
		return user_num;
	}
	public void setUser_num(String userNum) {
		user_num = userNum;
	}
	public String getPer_reg_no() {
		return per_reg_no;
	}
	public void setPer_reg_no(String perRegNo) {
		per_reg_no = perRegNo;
	}
	public String getDelte_yn() {
		return delte_yn;
	}
	public void setDelte_yn(String delteYn) {
		delte_yn = delteYn;
	}
	public String getApprove_Status() {
		return approve_Status;
	}
	public void setApprove_Status(String approveStatus) {
		approve_Status = approveStatus;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	public String getPasword() {
		return pasword;
	}
	public void setPasword(String pasword) {
		this.pasword = pasword;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}


}

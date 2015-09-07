package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * pa 요청 사용자 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PaUserInfoDO extends DTO{
	
	/**
	 * 사용자ID
	 */
	private String user_id          = Constants.BLANK;
	/**
	 * 사용자 이름
	 */
	private String	user_name; 
	/**
	 * 사번
	 */
	private String emp_num;
	/**
	 * 주민번호
	 */
	private String ss_num         = Constants.BLANK;
	/**
	 * 계정유형코드
	 */
	private String	acct_code = Constants.BLANK;
	/**
	 * 삭제여부
	 */
	private    String       del_yn = Constants.BLANK;
	/**
	 * 등록상태
	 */
	private    String       active_status     = Constants.BLANK;
	/**
	 * 패스워드
	 */
	private    String       password     = Constants.BLANK;
	/**
	 * 부서코드
	 */
	private    String       dept_cd     = Constants.BLANK;
	/**
	 * 회사코드
	 */
	private    String       cocd     = Constants.BLANK;
	
	
	public String getEmp_num() {
		return emp_num;
	}
	public void setEmp_num(String empNum) {
		emp_num = empNum;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}

	public String getSs_num() {
		return ss_num;
	}
	public void setSs_num(String ssNum) {
		ss_num = ssNum;
	}
	public String getAcct_code() {
		return acct_code;
	}
	public void setAcct_code(String acctCode) {
		acct_code = acctCode;
	}
	public String getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(String delYn) {
		del_yn = delYn;
	}
	public String getActive_status() {
		return active_status;
	}
	public void setActive_status(String activeStatus) {
		active_status = activeStatus;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	


}

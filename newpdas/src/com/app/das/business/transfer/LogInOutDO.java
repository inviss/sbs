package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * 로그인, 로그 아웃 정보를 담고있는 beans
 * @author ysk523
 *2012.4.23 생성
 */
public class LogInOutDO extends DTO 
{
	/**
	 * 순번
	 */
	private int seq;
	/**
	 * IP
	 */
	private String ip      = Constants.BLANK;
	/**
	 * 로그인 시각
	 */
	private String login_dt         = Constants.BLANK;
	/**
	 * 로그아웃 시각
	 */
	private String logout_dt             = Constants.BLANK;
	/**
	 * 상태
	 */
	private String status             = Constants.BLANK;
	/**
	 * 활성화 여부
	 */
	private String active_yn            = Constants.BLANK;
	/**
	 * 로그인ID
	 */
	private String user_id            = Constants.BLANK;
	
	
	/**
	 * 유저명
	 */
	private String user_nm            = Constants.BLANK;
	
	/**
	 * 직원유형
	 */
	private String acctype            = Constants.BLANK;
	
	/**
	 * 부서명
	 */
	private String dept_nm            = Constants.BLANK;
	
	/**
	 * 총카운트
	 */
	private int totalcount            = 0;
	
	/**
	 * 시작페이지
	 */
	private int start_page            = 0;
	

	
	/**
	 * 접속시작일
	 */
	private String start_login_dt            = Constants.BLANK;
	
	/**
	 * 접속종료일
	 */
	private String end_login_dt            = Constants.BLANK;
	
	
	/**
	 * 전화번호
	 */
	private String mobile            = Constants.BLANK;
	
	
	
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getStart_login_dt() {
		return start_login_dt;
	}
	public void setStart_login_dt(String startLoginDt) {
		start_login_dt = startLoginDt;
	}
	public String getEnd_login_dt() {
		return end_login_dt;
	}
	public void setEnd_login_dt(String endLoginDt) {
		end_login_dt = endLoginDt;
	}
	public int getStart_page() {
		return start_page;
	}
	public void setStart_page(int startPage) {
		start_page = startPage;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String userNm) {
		user_nm = userNm;
	}
	public String getAcctype() {
		return acctype;
	}
	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLogin_dt() {
		return login_dt;
	}
	public void setLogin_dt(String loginDt) {
		login_dt = loginDt;
	}
	public String getLogout_dt() {
		return logout_dt;
	}
	public void setLogout_dt(String logoutDt) {
		logout_dt = logoutDt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActive_yn() {
		return active_yn;
	}
	public void setActive_yn(String activeYn) {
		active_yn = activeYn;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	
	
	
	
	
	
}

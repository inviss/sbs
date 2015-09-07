package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 사용자 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class UserInfoDO extends DTO 
{
	/**
	 * 정직원여부(정직원 = '1', 비정직원 = '2')
	 */
	private String empFlag          = Constants.BLANK;
	/**
	 * 사용자ID
	 */
	private String userId           = Constants.BLANK;
	/**
	 * 사용자명
	 */
	private String userNm           = Constants.BLANK;
	/**
	 * 정직원의 경우 부서명, 비정직원의 경우 프로그램명
	 * 미접속 ID현황의 경우 소속사명
	 */
	private String etcNm            = Constants.BLANK;
	/**
	 * 전화번호
	 */
	private String phoneNo  	= Constants.BLANK;
	/**
	 * 보증인명
	 */
	private String guarantorNm = Constants.BLANK;
	/**
	 * 보증인연락처
	 */
	private String guarantorPhoneNo = Constants.BLANK;
	/**
	 * 직위
	 */
	private String title = Constants.BLANK;
	/**
	 * 직책
	 */
	private String job = Constants.BLANK;
	/**
	 * E-Mail
	 */
	private String email = Constants.BLANK;
	/**
	 * 중지일자
	 */
	private String stopDate = Constants.BLANK;
	/**
	 * 트랜젝션 속성
	 */
	private String trxKind = Constants.BLANK;
	/**
	 * 수정최종일
	 */
	private String updateEndDate = Constants.BLANK;
	/**
	 * 본부 명 
	 */
	private String seg_nm = Constants.BLANK;
	/**
	 * 부서 명 
	 */
	private String dept_nm = Constants.BLANK; 
	
	
	
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getSeg_nm() {
		return seg_nm;
	}
	public void setSeg_nm(String seg_nm) {
		this.seg_nm = seg_nm;
	}
	public String getEmpFlag() {
		return empFlag;
	}
	public void setEmpFlag(String empFlag) {
		this.empFlag = empFlag;
	}
	public String getEtcNm() {
		return etcNm;
	}
	public void setEtcNm(String etcNm) {
		this.etcNm = etcNm;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getGuarantorNm() {
		return guarantorNm;
	}
	public void setGuarantorNm(String guarantorNm) {
		this.guarantorNm = guarantorNm;
	}
	public String getGuarantorPhoneNo() {
		return guarantorPhoneNo;
	}
	public void setGuarantorPhoneNo(String guarantorPhoneNo) {
		this.guarantorPhoneNo = guarantorPhoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	public String getTrxKind() {
		return trxKind;
	}
	public void setTrxKind(String trxKind) {
		this.trxKind = trxKind;
	}
	public String getUpdateEndDate() {
		return updateEndDate;
	}
	public void setUpdateEndDate(String updateEndDate) {
		this.updateEndDate = updateEndDate;
	}
}

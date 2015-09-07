package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 보증인 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class GuarantorInfoDO extends DTO 
{
	/**
	 * 사번
	 */
	private String userNo = Constants.BLANK;
	/**
	 * 이름
	 */
	private String userNm = Constants.BLANK;
	/**
	 * 부서
	 */
	private String	deptNm = Constants.BLANK;
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
}

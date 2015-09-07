package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.CodeDO;

/**
 * 공통 코드의 장르 정보를 포함하고 있는 DataObject로서 CodeDO를 상속하고 있다.
 * @author ysk523
 *
 */
public class JanreInfoDO extends CodeDO 
{
	/**
	 * 폐기위원명
	 */
	private String userNm = Constants.BLANK;
	/**
	 * 부서명
	 */
	private String deptNm = Constants.BLANK;

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

}

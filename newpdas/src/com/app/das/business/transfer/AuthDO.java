package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 권한 변경 관련 DataObject
 * @author ysk523
 *
 */
public class AuthDO extends DTO 
{
	/** 
	 * 권한     
	 */
	private String auth = Constants.BLANK;    
	/** 
	 * 권한명   
	 */
	private String authName = Constants.BLANK;
	/** 
	 * 권한여부 
	 */
	private String authYn = Constants.BLANK;  
	/** 
	 * 역할     
	 */ 
	private String role = Constants.BLANK;
	/**
	 * 변경된 권한
	 */
	private String newAuthYn = Constants.BLANK;
	/**
	 * 트랜젝션 속성
	 */
	private String trxKind = Constants.BLANK;
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getAuthName() {
		return authName;
	}
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	public String getAuthYn() {
		return authYn;
	}
	public void setAuthYn(String authYn) {
		this.authYn = authYn;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getNewAuthYn() {
		return newAuthYn;
	}
	public void setNewAuthYn(String newAuthYn) {
		this.newAuthYn = newAuthYn;
	}
	public String getTrxKind() {
		return trxKind;
	}
	public void setTrxKind(String trxKind) {
		this.trxKind = trxKind;
	}
}

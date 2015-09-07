package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;
//import com.ibm.ktl.thanq.admin.SSOAdmin;
import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 우리 시스템의 사용자 공통 정보를 포함하고 있는 DataObject
 * @author dekim
 *
 */
public class DASCommonDO extends DTO {
	/**
	 * 정규직여부(정규직인 경우 'Y', 비정규직 'N')
	 */
	private String regularYn 		= "N";
	/**
	 * 사번
	 */
	private String userNo	        = Constants.BLANK;
	/**
	 * 부서명
	 */
	private String deptNm	        = Constants.BLANK;
	/**
	 * 사용자ID
	 */
	private String userId	        = Constants.BLANK;
	/**
	 * 사용자명
	 */
	private String userNm	        = Constants.BLANK;
	/**
	 * 권한
	 */	
	 private String auth				= Constants.BLANK; 
	/**
	 * 역할
	 */
	private String role				= Constants.BLANK;
	/**
	 * 주민번호
	 */
	private String perRegNo		= Constants.BLANK;
/**	
	public String secArch	        = Constants.BLANK;
	public String ingest	        	= Constants.BLANK;
	public String movieEdtr	    = Constants.BLANK;
	public String srchr				= Constants.BLANK;
	public String spvs				= Constants.BLANK;
*/	
	private String posCd	        = Constants.BLANK;
	private String name				= Constants.BLANK;
	private String respId	        = Constants.BLANK;
//	public String outUserId		= Constants.BLANK;
	
	/**
	 * 로그인 Warning 메세지
	 */
	private String warningMsg = Constants.BLANK;
	
	/**
	 * SSOAdmin 객체
	 */
//	private SSOAdmin ssoAdmin = null;
	/**
	 * SSO Token 값
	 * @return
	 */
	private String token = Constants.BLANK;
	
	//MHCHOI
	/*
	public List getAuthData() {
		return authDataList;
	}
	public void setAuthData(List authDataList) {
		this.authDataList = authDataList;
	}
	*/
	
	
	/**
	 * 시작페이지
	 */
	private int startPage;
	
	
	/**
	 * 종료페이지
	 */
	private int endPage;
	
	
	/**
	 * 정렬 순서 A :ASC   D : DESC
	 */
	private String sort;
	
	
	/**
	 * 정렬 기준 T:구분 P:프로그램제목 B:방송일
	 */
	private String sortby;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getSortby() {
		return sortby;
	}
	public void setSortby(String sortby) {
		this.sortby = sortby;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosCd() {
		return posCd;
	}
	public void setPosCd(String posCd) {
		this.posCd = posCd;
	}
	public String getRegularYn() {
		return regularYn;
	}
	public void setRegularYn(String regularYn) {
		this.regularYn = regularYn;
	}
	public String getRespId() {
		return respId;
	}
	public void setRespId(String respId) {
		this.respId = respId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getPerRegNo() {
		return perRegNo;
	}
	public void setPerRegNo(String perRegNo) {
		this.perRegNo = perRegNo;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
//	public SSOAdmin getSsoAdmin() {
//		return ssoAdmin;
//	}
//	public void setSsoAdmin(SSOAdmin ssoAdmin) {
//		this.ssoAdmin = ssoAdmin;
//	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}

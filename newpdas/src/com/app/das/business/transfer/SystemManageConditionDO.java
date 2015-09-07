package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 시스템 관리의 목록 조회시 조건 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class SystemManageConditionDO extends DTO 
{
	/**
	 * 사용자 ID
	 */
	private String userId = Constants.BLANK;
	/**
	 * 사용자명
	 */
	private String userNm = Constants.BLANK;
	/**
	 * 사번
	 */
	private String userNo = Constants.BLANK;
	/**
	 * 시작일
	 */
	private String startDate = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String endDate = Constants.BLANK;
	/**
	 * 작업구분코드
	 */
	private String eqClfCd = Constants.BLANK;
	/**
	 * 작업상태코드
	 */
	private String workStateCode = Constants.BLANK;
	/**
	 * 장비프로세스코
	 */
	private String workCode = Constants.BLANK;
	/**
	 * 접속구분(미접속ID현황에서 사용)
	 */
	private String loginUseFlag = Constants.BLANK;
	/**
	 * 대리인아이디
	 */
	private String agentId = Constants.BLANK;
	/**
	 * 검색어
	 */
	private String searchText = Constants.BLANK;
	/**
	 * 장비명 
	 */
	private String dasEqNm = Constants.BLANK;
	/**
	 * 작업 완료 여부
	 */
	private String mod_end_yn = Constants.BLANK;
	/**
	 * 페이지 
	 */
	private int page;
	/**
	 * 콘텐츠 ID 
	 */
	private String ct_id = Constants.BLANK;
	/**
	 * 콘텐츠명(클립명) 
	 */
	private String ct_nm = Constants.BLANK;
	/**
	 *  phot_id (사진등록 id)
	 */
	private String phot_id = Constants.BLANK;
	/**
	 *  사진 제목
	 */
	private String subj = Constants.BLANK;
	/**
	 *  sort 컬럼명
	 */
	private String sort = Constants.BLANK;
	/**
	 *  오름차순 정렬인지, 내림차순 정렬인지.
	 */
	private String asceding = Constants.BLANK;
	
	
	
	
	public String getPhot_id() {
		return phot_id;
	}
	public void setPhot_id(String phot_id) {
		this.phot_id = phot_id;
	}
	public String getSubj() {
		return subj;
	}
	public void setSubj(String subj) {
		this.subj = subj;
	}
	public String getCt_id() {
		return ct_id;
	}
	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}
	public String getCt_nm() {
		return ct_nm;
	}
	public void setCt_nm(String ct_nm) {
		this.ct_nm = ct_nm;
	}
	public String getMod_end_yn() {
		return mod_end_yn;
	}
	public void setMod_end_yn(String mod_end_yn) {
		this.mod_end_yn = mod_end_yn;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEqClfCd() {
		return eqClfCd;
	}
	public void setEqClfCd(String eqClfCd) {
		this.eqClfCd = eqClfCd;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	public String getWorkStateCode() {
		return workStateCode;
	}
	public void setWorkStateCode(String workStateCode) {
		this.workStateCode = workStateCode;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getLoginUseFlag() {
		return loginUseFlag;
	}
	public void setLoginUseFlag(String loginUseFlag) {
		this.loginUseFlag = loginUseFlag;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getWorkCode() {
		return workCode;
	}
	public void setWorkCode(String workCode) {
		this.workCode = workCode;
	}
	public String getDasEqNm() {
		return dasEqNm;
	}
	public void setDasEqNm(String dasEqNm) {
		this.dasEqNm = dasEqNm;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getAsceding() {
		return asceding;
	}
	public void setAsceding(String asceding) {
		this.asceding = asceding;
	}

	
}

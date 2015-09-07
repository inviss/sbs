package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 직원 목록 조회시 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class EmployeeRoleConditionDO extends DTO
{
	/**
	 * 회사코드
	 */
	private String companyCode = Constants.BLANK;
	/**
	 * 본부코드
	 */
	private String centerCode = Constants.BLANK;
	/**
	 * 부서코드
	 */
	private String departmentCode = Constants.BLANK;
	/**
	 * 
	 */
	private String roleCode = Constants.BLANK;
	/**
	 * 검색조건(이름, ID, 회사명)
	 */
	private String searchType = Constants.BLANK;
	/**
	 * 검색어
	 */
	private String searchText = Constants.BLANK;
	/**
	 * ID상태
	 */
	private String idStatus = Constants.BLANK;
	/**
	 * 조회 페이지
	 */
	int page;

	public String getCenterCode() {
		return centerCode;
	}

	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}

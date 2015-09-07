package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 폐기 목록 조회시의 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class DisuseConditionDO extends DTO 
{
	/**
	 * 시작일
	 */
	private String fromDate = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String toDate = Constants.BLANK;
	/**
	 * 검색어
	 */
	private String searchValue = Constants.BLANK;
	/**
	 * 대분류
	 */
	private String largeCategory = Constants.BLANK;
	/**
	 * 중분류
	 */
	private String midiumCategory = Constants.BLANK;
	/**
	 * 소분류
	 */
	private String smallCategory = Constants.BLANK;
	/**
	 * 폐기구분
	 */
	private String disuseClf = Constants.BLANK;
	/**
	 * 폐기위원별 조회 Flag ('Y' 이면 폐기위원 조회)
	 */
	private String disuseUserFlag = Constants.BLANK;
	/**
	 * 조회할 Page
	 */
	private int page;
	/**
	 * 시작 이용횟수
	 */
	private String useCountFrom;
	/**
	 * 종료 이용횟수
	 */
	private String useCountTo;
	
	public String getUseCountFrom() {
		return useCountFrom;
	}
	public void setUseCountFrom(String useCountFrom) {
		this.useCountFrom = useCountFrom;
	}
	public String getUseCountTo() {
		return useCountTo;
	}
	public void setUseCountTo(String useCountTo) {
		this.useCountTo = useCountTo;
	}
	public String getDisuseClf() {
		return disuseClf;
	}
	public void setDisuseClf(String disuseClf) {
		this.disuseClf = disuseClf;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getLargeCategory() {
		return largeCategory;
	}
	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}
	public String getMidiumCategory() {
		return midiumCategory;
	}
	public void setMidiumCategory(String midiumCategory) {
		this.midiumCategory = midiumCategory;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getDisuseUserFlag() {
		return disuseUserFlag;
	}
	public void setDisuseUserFlag(String disuseUserFlag) {
		this.disuseUserFlag = disuseUserFlag;
	}
}

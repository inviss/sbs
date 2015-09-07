package com.app.das.business.transfer;

import java.util.HashMap;
import java.util.Map;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 다운로드 이용 통계의 분류 정보를 포함하고 있는 DataObject로서 내부적으로
 * 수치정보를 포함하고 있는 StatisticsItemDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class StatisticsUseItemDO extends DTO 
{
	/**
	 * 회사 코드
	 */
	private String coCd = Constants.BLANK;
	/**
	 * 본부 코드
	 */
	private String centerCd = Constants.BLANK;
	/**
	 * 부서코드
	 */
	private String depCd = Constants.BLANK;
	/**
	 * 회사명
	 */
	private String companyNm = Constants.BLANK;
	/**
	 * 본부명
	 */
	private String centerNm = Constants.BLANK;
	/**
	 * 부서명
	 */
	private String departmentNm = Constants.BLANK;
	
	/**
	 * 통계수치를 포함하고 있는 List
	 */
	private Map statisticsItemDOMap = null;

	public StatisticsUseItemDO()
	{
		statisticsItemDOMap = new HashMap();
	}

	public String getCenterNm() {
		return centerNm;
	}

	public void setCenterNm(String centerNm) {
		this.centerNm = centerNm;
	}

	public String getCompanyNm() {
		return companyNm;
	}

	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}

	public String getDepartmentNm() {
		return departmentNm;
	}

	public void setDepartmentNm(String departmentNm) {
		this.departmentNm = departmentNm;
	}


	public Map getStatisticsItemDOMap() {
		return statisticsItemDOMap;
	}

	public void setStatisticsItemDOMap(Map statisticsItemDOMap) {
		this.statisticsItemDOMap = statisticsItemDOMap;
	}

	public void put(String key, DTO dto)
	{
		statisticsItemDOMap.put(key, dto);
	}

	public String getCenterCd() {
		return centerCd;
	}

	public void setCenterCd(String centerCd) {
		this.centerCd = centerCd;
	}

	public String getCoCd() {
		return coCd;
	}

	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}

	public String getDepCd() {
		return depCd;
	}

	public void setDepCd(String depCd) {
		this.depCd = depCd;
	}

}

package com.app.das.business.transfer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 통계의 장르 분류 정보를 포함하고 있는 DataObject로써 내부적으로
 * 수치정보를 포함하고 있는 StatisticsItemDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class StatisticsJanreItemDO extends DTO 
{
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
	 * 대분류명
	 */
	private String largeCategoryNm = Constants.BLANK;
	/**
	 * 중분류명
	 */
	private String midiumCategoryNm = Constants.BLANK;
	/**
	 * 소분류명
	 */
	private String smallCategoryNm = Constants.BLANK;

	/**
	 * 총수량계
	 */
	private int totalQty;
	/**
	 * 총시간계
	 */
	private BigDecimal totalTm = Constants.ZERO;
	
	/**
	 * 통계수치를 포함하고 있는 List
	 */
	private Map statisticsItemDOMap = null;
	
	public StatisticsJanreItemDO()
	{
		statisticsItemDOMap = new HashMap();
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
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}
	
	public void put(String key, DTO dto)
	{
		statisticsItemDOMap.put(key, dto);
	}

	public Map getStatisticsItemDOMap() {
		return statisticsItemDOMap;
	}

	public void setStatisticsItemDOMap(Map statisticsItemDOMap) {
		this.statisticsItemDOMap = statisticsItemDOMap;
	}

	public int getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(int totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalTm() {
		return totalTm;
	}

	public void setTotalTm(BigDecimal totalTm) {
		this.totalTm = totalTm;
	}

	public String getLargeCategoryNm() {
		return largeCategoryNm;
	}

	public void setLargeCategoryNm(String largeCategoryNm) {
		this.largeCategoryNm = largeCategoryNm;
	}

	public String getMidiumCategoryNm() {
		return midiumCategoryNm;
	}

	public void setMidiumCategoryNm(String midiumCategoryNm) {
		this.midiumCategoryNm = midiumCategoryNm;
	}

	public String getSmallCategoryNm() {
		return smallCategoryNm;
	}

	public void setSmallCategoryNm(String smallCategoryNm) {
		this.smallCategoryNm = smallCategoryNm;
	}
}

package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 전체 통계의 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class StatisticsTotalItemDO extends DTO 
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
	 * 수집량계
	 */
	private int mvSumQty;
	/**
	 * 정리량계
	 */
	private int mdSumQty;
	/**
	 * 이용량계
	 */
	private int dwSumQty;
	/**
	 * 폐기량계
	 */
	private int dsSumQty;
	/**
	 * 보유량
	 */
	private int totalQty;
	/**
	 * 보유시간
	 */
	private int rgSumQty;
	/**
	 * 등록계
	 */
	private BigDecimal totalTm = Constants.ZERO;
	public int getDsSumQty() {
		return dsSumQty;
	}
	public void setDsSumQty(int dsSumQty) {
		this.dsSumQty = dsSumQty;
	}
	public int getDwSumQty() {
		return dwSumQty;
	}
	public void setDwSumQty(int dwSumQty) {
		this.dwSumQty = dwSumQty;
	}
	public String getLargeCategory() {
		return largeCategory;
	}
	public void setLargeCategory(String largeCategory) {
		this.largeCategory = largeCategory;
	}
	public String getLargeCategoryNm() {
		return largeCategoryNm;
	}
	public void setLargeCategoryNm(String largeCategoryNm) {
		this.largeCategoryNm = largeCategoryNm;
	}
	public int getMdSumQty() {
		return mdSumQty;
	}
	public void setMdSumQty(int mdSumQty) {
		this.mdSumQty = mdSumQty;
	}
	public String getMidiumCategory() {
		return midiumCategory;
	}
	public void setMidiumCategory(String midiumCategory) {
		this.midiumCategory = midiumCategory;
	}
	public String getMidiumCategoryNm() {
		return midiumCategoryNm;
	}
	public void setMidiumCategoryNm(String midiumCategoryNm) {
		this.midiumCategoryNm = midiumCategoryNm;
	}
	public int getMvSumQty() {
		return mvSumQty;
	}
	public void setMvSumQty(int mvSumQty) {
		this.mvSumQty = mvSumQty;
	}
	public String getSmallCategory() {
		return smallCategory;
	}
	public void setSmallCategory(String smallCategory) {
		this.smallCategory = smallCategory;
	}
	public String getSmallCategoryNm() {
		return smallCategoryNm;
	}
	public void setSmallCategoryNm(String smallCategoryNm) {
		this.smallCategoryNm = smallCategoryNm;
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
	public int getRgSumQty() {
		return rgSumQty;
	}
	public void setRgSumQty(int rgSumQty) {
		this.rgSumQty = rgSumQty;
	}

}

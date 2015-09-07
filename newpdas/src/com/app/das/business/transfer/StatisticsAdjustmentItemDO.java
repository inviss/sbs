package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 통계 상세 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class StatisticsAdjustmentItemDO extends DTO 
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
	 * 매체변환 수집수량
	 */
	private int mediaGathQty;
	/**
	 * 매체변환 정리수량
	 */
	private int mediaAdjQty;
	/**
	 * 매체변환 정리율
	 */
	private BigDecimal mediaAdjRate = Constants.ZERO;
	//private String mediaAdjRate = Constants.ZERO;
	
	/**
	 * On-Air 수집수량
	 */
	private int onairGathQty;
	/**
	 * On-Air 정리수량
	 */
	private int onairAdjQty;
	/**
	 * On-Air 정리율
	 */
	private BigDecimal onairAdjRate = Constants.ZERO;
	//private String onairAdjRate = Constants.ZERO;
	/**
	 * 총 수집 합계
	 */
	private int totalGathQty;
	/**
	 * 총 정리 합계
	 */
	private int totalAdjQty;
	/**
	 * 총 정리
	 */
	private BigDecimal totalAdjRate = Constants.ZERO;
	//private String totalAdjRate = Constants.ZERO;
	
	
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
	public int getMediaAdjQty() {
		return mediaAdjQty;
	}
	public void setMediaAdjQty(int mediaAdjQty) {
		this.mediaAdjQty = mediaAdjQty;
	}
	public BigDecimal getMediaAdjRate() {
		return mediaAdjRate;
	}	
	public void setMediaAdjRate(BigDecimal mediaAdjRate) {
		this.mediaAdjRate = mediaAdjRate;
	}
	public int getMediaGathQty() {
		return mediaGathQty;
	}
	public void setMediaGathQty(int mediaGathQty) {
		this.mediaGathQty = mediaGathQty;
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
	public int getOnairAdjQty() {
		return onairAdjQty;
	}
	public void setOnairAdjQty(int onairAdjQty) {
		this.onairAdjQty = onairAdjQty;
	}
	public BigDecimal getOnairAdjRate() {
		return onairAdjRate;
	}
	public void setOnairAdjRate(BigDecimal onairAdjRate) {
		this.onairAdjRate = onairAdjRate;
	}
	public int getOnairGathQty() {
		return onairGathQty;
	}
	public void setOnairGathQty(int onairGathQty) {
		this.onairGathQty = onairGathQty;
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
	public int getTotalAdjQty() {
		return totalAdjQty;
	}
	public void setTotalAdjQty(int totalAdjQty) {
		this.totalAdjQty = totalAdjQty;
	}
	public BigDecimal getTotalAdjRate() {
		return totalAdjRate;
	}
	public void setTotalAdjRate(BigDecimal totalAdjRate) {
		this.totalAdjRate = totalAdjRate;
	}
	public int getTotalGathQty() {
		return totalGathQty;
	}
	public void setTotalGathQty(int totalGathQty) {
		this.totalGathQty = totalGathQty;
	}
}

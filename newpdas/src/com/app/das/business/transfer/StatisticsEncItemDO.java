package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 정리의 인코딩 매체 통계 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class StatisticsEncItemDO extends DTO 
{
	/**
	 * 대분류
	 */
	private String largeCategory = Constants.BLANK;
	/**
	 * 대분류명
	 */
	private String largeCategoryNm = Constants.BLANK;
	/**
	 * 대상권수
	 */
	private int objTotal;
	/**
	 * 완료건수
	 */
	private int qty;
	/**
	 * 인코딩 전체 길이
	 */
	private long tot_duration;
	/**
	 * 완료 인코딩 길이
	 */
	private long duration;
	/**
	 * 진행률
	 */
	private BigDecimal conRate = Constants.ZERO;
	
	
	
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getTot_duration() {
		return tot_duration;
	}
	public void setTot_duration(long tot_duration) {
		this.tot_duration = tot_duration;
	}
	public BigDecimal getConRate() {
		return conRate;
	}
	public void setConRate(BigDecimal conRate) {
		this.conRate = conRate;
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
	public int getObjTotal() {
		return objTotal;
	}
	public void setObjTotal(int objTotal) {
		this.objTotal = objTotal;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
}

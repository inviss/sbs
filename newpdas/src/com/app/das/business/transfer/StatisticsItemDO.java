package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 통계의 수치정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class StatisticsItemDO extends DTO 
{
	/**
	 * DATE
	 */
	private String applyDate = Constants.BLANK;
	/**
	 * 테이프 매체분류 코드
	 */
	private String code = Constants.BLANK;

	/**
	 * 수량계
	 */
	private int qty;
	/**
	 * 시간계
	 */
	private BigDecimal time;
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getTime() {
		return time;
	}
	public void setTime(BigDecimal time) {
		this.time = time;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

}

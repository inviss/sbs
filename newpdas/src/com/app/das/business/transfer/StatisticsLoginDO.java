package com.app.das.business.transfer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 통계의 로그인 정보를 포함하고 있는 DataObject로써 내부적으로
 * 수치정보를 포함하고 있는 StatisticsLoginDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class StatisticsLoginDO extends DTO 
{
	/**
	 *   날짜
	 */
	private String date =  Constants.BLANK;
	
	/**
	 *   직원 로그인 수
	 */
	private long s_count = 0;
	
	/**
	 *   비직원 로그인 수
	 */
	private long d_count = 0;
	
	/**
	 *   직원 로그인 총합
	 */
	private long s_tot = 0;
	
	/**
	 *   비직원 로그인 총합
	 */
	private long d_tot = 0;

	
	
	
	public long getD_count() {
		return d_count;
	}

	public void setD_count(long d_count) {
		this.d_count = d_count;
	}

	public long getD_tot() {
		return d_tot;
	}

	public void setD_tot(long d_tot) {
		this.d_tot = d_tot;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public long getS_count() {
		return s_count;
	}

	public void setS_count(long s_count) {
		this.s_count = s_count;
	}

	public long getS_tot() {
		return s_tot;
	}

	public void setS_tot(long s_tot) {
		this.s_tot = s_tot;
	}
	
	
}

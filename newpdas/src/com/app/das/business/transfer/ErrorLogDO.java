package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 에러 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class ErrorLogDO extends DTO 
{
	/**
	 * 서버명
	 */
	private String server_nm = Constants.BLANK;
	
	/**
	 * 에러종류
	 */
	private String error_type = Constants.BLANK;
	
	/**
	 * 발생시각
	 */
	private String reg_dt = Constants.BLANK;
	
	/**
	 * 오류
	 */
	private String error_cont = Constants.BLANK;
	
	/**
	 * 발생시간 시작(검색용)
	 */
	private String start_reg_dt = Constants.BLANK;
	
	/**
	 * 발생시간 종료(검색용) 
	 */
	private String end_reg_dt = Constants.BLANK;
	
	/**
	 * 총페이지
	 */
	private int totalcount = 0;
	
	/**
	 * 시작페이지
	 */
	private int start_page = 0;

	
	/**
	 * ip
	 */
	private String ip = Constants.BLANK;
	
	
	
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getServer_nm() {
		return server_nm;
	}

	public void setServer_nm(String serverNm) {
		server_nm = serverNm;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String errorType) {
		error_type = errorType;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}

	public String getError_cont() {
		return error_cont;
	}

	public void setError_cont(String errorCont) {
		error_cont = errorCont;
	}

	public String getStart_reg_dt() {
		return start_reg_dt;
	}

	public void setStart_reg_dt(String startRegDt) {
		start_reg_dt = startRegDt;
	}

	public String getEnd_reg_dt() {
		return end_reg_dt;
	}

	public void setEnd_reg_dt(String endRegDt) {
		end_reg_dt = endRegDt;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getStart_page() {
		return start_page;
	}

	public void setStart_page(int startPage) {
		start_page = startPage;
	}
	
	

	
	
	
}

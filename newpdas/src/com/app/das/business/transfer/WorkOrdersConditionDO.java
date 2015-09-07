package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 작업지시 목록 조회에서 조회 조건을 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class WorkOrdersConditionDO extends DTO 
{
	/**
	 * Tape 청구번호
	 */
	private String tapeReqNo = Constants.BLANK;
	/**
	 * 시작일
	 */
	private String fromDate = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String toDate = Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String programNm = Constants.BLANK;
	
	/**
	 * 한 페이지당 보여줄 Row 갯수
	 */
	private int rowPerPage;
	
	/**
	 * 현재페이지
	 */
	private int page;
	
	/**
	 * sort
	 */
	private String sort;
	
	/**
	 * asceding
	 */
	private String asceding;
	
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getAsceding() {
		return asceding;
	}

	public void setAsceding(String asceding) {
		this.asceding = asceding;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getProgramNm() {
		return programNm;
	}

	public void setProgramNm(String programNm) {
		this.programNm = programNm;
	}

	public String getTapeReqNo() {
		return tapeReqNo;
	}

	public void setTapeReqNo(String tapeReqNo) {
		this.tapeReqNo = tapeReqNo;
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

}

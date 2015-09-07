package com.app.das.business.transfer;

import com.app.das.business.transfer.DTO;
/**
 * 코난 검색엔진  정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class SearchConditionDO extends DTO 
{
	/**
	 * 현재 페이지
	 */
	private int page;
	/**
	 * 한 화면에서 보여줄 Row 수
	 */
	private int rowPerPage;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
}

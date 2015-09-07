package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 게시판 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class BoardConditionDO extends DTO 
{
	/**
	 * 게시판종류코드
	 */
	private String boardTypeCd      = Constants.BLANK;
	/**
	 * 조회구분
	 */
	private String searchKind = Constants.BLANK;
	/**
	 * 조회값
	 */
	private String searchValue = Constants.BLANK;
	/**
	 * 본문ID
	 */
	private int mainId;
	
	/**
	 * 조회할 페이지
	 */
	private int page;
	
	/**
	 * 한 페이지당 갯수
	 */
	private int rowPerPage;
	/**
	 * 팝업시작일
	 */
	private String popup_start_dd = Constants.BLANK;
	/**
	 * 팝업종료일
	 */
	private String popup_end_dd = Constants.BLANK;
	
	
	public String getPopup_start_dd() {
		return popup_start_dd;
	}

	public void setPopup_start_dd(String popupStartDd) {
		popup_start_dd = popupStartDd;
	}

	public String getPopup_end_dd() {
		return popup_end_dd;
	}

	public void setPopup_end_dd(String popupEndDd) {
		popup_end_dd = popupEndDd;
	}

	public String getBoardTypeCd() {
		return boardTypeCd;
	}

	public void setBoardTypeCd(String boardTypeCd) {
		this.boardTypeCd = boardTypeCd;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchKind() {
		return searchKind;
	}

	public void setSearchKind(String searchKind) {
		this.searchKind = searchKind;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public int getMainId() {
		return mainId;
	}

	public void setMainId(int mainId) {
		this.mainId = mainId;
	}

}

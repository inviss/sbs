package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 아카이브인제스트 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ArchiveIngestInfoDO extends DTO 
{
	/** 
	 * Tape Item
	 */
	private String tapeItem = Constants.BLANK;
	
	/**
	 * title
	 */
	private String title = Constants.BLANK;
	
	/**
	 * ct_nm
	 */
	private String ct_nm = Constants.BLANK;
	
	/**
	 * cti
	 */
	private String cti = Constants.BLANK;
	
	/**
	 * archiveYN
	 */
	private String archiveYN = Constants.BLANK;

	/**
	 * fromDate
	 */
	private String fromDate = Constants.BLANK;
	
	/**
	 * toDate
	 */
	private String toDate = Constants.BLANK;
	
	/**
	 * ArchiveState
	 */
	private String ArchiveState = Constants.BLANK;
	
	/**
	 * page 
	 */
	private int page = 0;
	
	/** 
	 * dtlYN
	 */
	private String dtlYN = Constants.BLANK;
	
	
	public String getDtlYN() {
		return dtlYN;
	}

	public void setDtlYN(String dtlYN) {
		this.dtlYN = dtlYN;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTapeItem() {
		return tapeItem;
	}

	public void setTapeItem(String tapeItem) {
		this.tapeItem = tapeItem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCt_nm() {
		return ct_nm;
	}

	public void setCt_nm(String ct_nm) {
		this.ct_nm = ct_nm;
	}

	public String getCti() {
		return cti;
	}

	public void setCti(String cti) {
		this.cti = cti;
	}

	public String getArchiveYN() {
		return archiveYN;
	}

	public void setArchiveYN(String archiveYN) {
		this.archiveYN = archiveYN;
	}

	public String getArchiveState() {
		return ArchiveState;
	}

	public void setArchiveState(String archiveState) {
		ArchiveState = archiveState;
	}
	
}

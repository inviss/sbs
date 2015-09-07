package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 외부 시스템 승인 폴더 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class FolderListDO extends DTO{
	
	/**
	 * cellname
	 */
	private String cellname           = Constants.BLANK;

	/**
	 * nodeid
	 */
	private String nodeid           = Constants.BLANK;
	
	/**
	 * nodecaption
	 */
	private String nodecaption           = Constants.BLANK;
	
	/**
	 * parentnodeid
	 */
	private String parentnodeid           = Constants.BLANK;
	
	/**
	 * parentnodecaption
	 */
	private String parentnodecaption           = Constants.BLANK;
	
	/**
	 * userid
	 */
	private String userid           = Constants.BLANK;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCellname() {
		return cellname;
	}

	public void setCellname(String cellname) {
		this.cellname = cellname;
	}

	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodecaption() {
		return nodecaption;
	}

	public void setNodecaption(String nodecaption) {
		this.nodecaption = nodecaption;
	}

	public String getParentnodeid() {
		return parentnodeid;
	}

	public void setParentnodeid(String parentnodeid) {
		this.parentnodeid = parentnodeid;
	}

	public String getParentnodecaption() {
		return parentnodecaption;
	}

	public void setParentnodecaption(String parentnodecaption) {
		this.parentnodecaption = parentnodecaption;
	}
	
	
	
	
}

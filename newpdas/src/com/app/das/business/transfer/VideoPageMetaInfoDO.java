package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * tape 메타 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class VideoPageMetaInfoDO extends DTO 
{

	private String title	= Constants.BLANK;
	
	private String brdDd	= Constants.BLANK;
	
	private String finalBrdYn	= Constants.BLANK;
	
	private String ctgrLCd	= Constants.BLANK;
	
	private String ctgrMCd	= Constants.BLANK;
	
	private String ctgrSCd	= Constants.BLANK;
	
	private int episNo;
	
	private String reqCd	= Constants.BLANK;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getFinalBrdYn() {
		return finalBrdYn;
	}
	public void setFinalBrdYn(String finalBrdYn) {
		this.finalBrdYn = finalBrdYn;
	}
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}	
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public int getEpisNo() {
		return episNo;
	}
	public void setEpisNo(int episNo) {
		this.episNo = episNo;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	
}

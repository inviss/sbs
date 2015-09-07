package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 비디오 컨텐츠 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class VideoPageContentInfoDO extends DTO 
{
	/**
	 * 콘텐트 ID
	 */
	private long ctId;
	/**
	 * 종횡비 코드
	 */
	private String aspRtoCd	= Constants.BLANK;
	/**
	 * 화질 코드
	 */
	private String vdQlty	= Constants.BLANK;
	/**
	 * 콘테트 길이
	 */
	private String ctLeng	= Constants.BLANK;
	
	
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getctLeng() {
		return ctLeng;
	}
	public void setctLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	
}

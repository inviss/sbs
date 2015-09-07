package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * tape  정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class VideoPageInfoDO extends DTO 
{
	/**
	 * 프로그램 ID
	 */
	private long pgmId;
	/**
	 * 프로그램 이름
	 */
	private String pgmNm	=	Constants.BLANK;
	/**
	 * 방송 시작 일
	 */
	private String brdBgnDd	=	Constants.BLANK;
	/**
	 * 방송 종료  일
	 */
	private String brdEndDd	=	Constants.BLANK;
	/**
	 * 미디어코드
	 */
	private String mediaCd	=	Constants.BLANK;
	/**
	 * 프로그램코드
	 */
	private String pgm_cd	= Constants.BLANK;
	
	public long getPgmId() {
		return pgmId;
	}
	public void setPgmId(long pgmId) {
		this.pgmId = pgmId;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getBrdBgnDd() {
		return brdBgnDd;
	}
	public void setBrdBgnDd(String brdBgnDd) {
		this.brdBgnDd = brdBgnDd;
	}
	public String getBrdEndDd() {
		return brdEndDd;
	}
	public void setBrdEndDd(String brdEndDd) {
		this.brdEndDd = brdEndDd;
	}
	public String getMediaCd() {
		return mediaCd;
	}
	public void setMediaCd(String mediaCd) {
		this.mediaCd = mediaCd;
	}
	
	public void setPgmCD(String pgmcd)
	{
		this.pgm_cd = pgmcd;		
	}
	public String getPgmCD()
	{
		return this.pgm_cd;
	}

	
}

package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 키프레임 정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class KeyFrameInfoDO extends DTO 
{
	/** 
	 * 키프레임 일련번호 
	 */
	private int kfrmSeq; 
	/** 
	 * 타임코드 
	 */
	private String timeCd	= Constants.BLANK;  
	/** 
	 * 화질코드 
	 */
	private String vdQlty   = Constants.BLANK;
	/** 
	 * 종횡비코드 
	 */
	private String aspRtoCd = Constants.BLANK;
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public int getKfrmSeq() {
		return kfrmSeq;
	}
	public void setKfrmSeq(int kfrmSeq) {
		this.kfrmSeq = kfrmSeq;
	}
	public String getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(String timeCd) {
		this.timeCd = timeCd;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
}

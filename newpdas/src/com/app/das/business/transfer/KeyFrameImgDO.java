package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 대표화면 정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class KeyFrameImgDO extends DTO 
{
	/**
	 * 키프레임일련번호
	 */
	private int kfrmSeq;
	/**
	 * 타임코
	 */	
	private String timeCd = Constants.BLANK;
	
	private long 	rpimgCtId;
	private int 	rpimgKfrmSeq;

	public String getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(String timeCd) {
		this.timeCd = timeCd;
	}
	public int getKfrmSeq() {
		return kfrmSeq;
	}
	public void setKfrmSeq(int kfrmSeq) {
		this.kfrmSeq = kfrmSeq;
	}
	public long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	} 
	public int getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(int rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}   
}

package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * tape 콘턴츠 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class TapeContentInfoDO extends DTO 
{
	/**
	 * 등록 일자
	 */
	private String rtgDd = Constants.BLANK;
	/**
	 * 수집 번호
	 */
	private String gthNo = Constants.BLANK;
	/**
	 * 수집 처
	 */
	private String gthNm = Constants.BLANK;
	/**
	 * 테이프 종류 코드
	 */
	private String tapeKind = Constants.BLANK;
	/**
	 * 등록 일자
	 */
	private String tapeNum = Constants.BLANK;
	
	
	public String getRtgDd() {
		return rtgDd;
	}
	public void setRtgDd(String rtgDd) {
		this.rtgDd = rtgDd;
	}
	public String getGthNo() {
		return gthNo;
	}
	public void setGthNo(String gthNo) {
		this.gthNo = gthNo;
	}
	public String getGthNm() {
		return gthNm;
	}
	public void setGthNm(String gthNm) {
		this.gthNm = gthNm;
	}
	public String getTapeKind() {
		return tapeKind;
	}
	public void setTapeKind(String tapeKind) {
		this.tapeKind = tapeKind;
	}
	public String getTapeNum() {
		return tapeNum;
	}
	public void setTapeNum(String tapeNum) {
		this.tapeNum = tapeNum;
	}
}

package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 작업지시 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class WorkOrdersDO extends DTO 
{
	/**
	 * 일련번호
	 */
	private int serialNo;
	/**
	 * 청구번호
	 */
	private String reqNo		= Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String pgmNm            = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDd            = Constants.BLANK;
	/**
	 * 길이
	 */
	private String len              = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brdDd            = Constants.BLANK;
	/**
	 * 작업상태
	 */
	private String workState        = Constants.BLANK;
	/**
	 * 작업순위
	 */
	private String workSeq          = Constants.BLANK;
	/**
	 * 테이프아이템식별자
	 */
	private String tapeItemId       = Constants.BLANK;
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getLen() {
		return len;
	}
	public void setLen(String len) {
		this.len = len;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getRegDd() {
		return regDd;
	}
	public void setRegDd(String regDd) {
		this.regDd = regDd;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getTapeItemId() {
		return tapeItemId;
	}
	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}
	public String getWorkSeq() {
		return workSeq;
	}
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}
	public String getWorkState() {
		return workState;
	}
	public void setWorkState(String workState) {
		this.workState = workState;
	}
}

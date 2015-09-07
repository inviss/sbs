package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 미디어 정보를 포함하고 있는 DataObejct (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class MediaInfoDO extends DTO 
{
	/**
	 * 오디오여부
	 */
	private String audioYn      	= Constants.BLANK;
	/**
	 * ME분리코드
	 */
	private String meCd             = Constants.BLANK;
	/**
	 * 색상코드
	 */
	private String colorCd          = Constants.BLANK;
	/**
	 * 콘텐츠인스턴츠포맷코드
	 */
	private String ctiFmt           = Constants.BLANK;
	/**
	 * 등록일시
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 파일크기
	 */
	private long flSz;
	/**
	 * 초당프레임
	 */
	private String frmPerSec        = Constants.BLANK;
	/**
	 * 비트전송율
	 */
	private String bitRt            = Constants.BLANK;
	/**
	 * 녹음방식코드
	 */
	private String recordTypeCd     = Constants.BLANK;
	/**
	 * 오디오대역폭
	 */
	private String audioBdwt        = Constants.BLANK;
	/**
	 * 오디오샘플
	 */
	private String audSampFrq       = Constants.BLANK;
	public String getAudioBdwt() {
		return audioBdwt;
	}
	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}
	public String getAudioYn() {
		return audioYn;
	}
	public void setAudioYn(String audioYn) {
		this.audioYn = audioYn;
	}
	public String getAudSampFrq() {
		return audSampFrq;
	}
	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}
	public String getBitRt() {
		return bitRt;
	}
	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public long getFlSz() {
		return flSz;
	}
	public void setFlSz(long flSz) {
		this.flSz = flSz;
	}
	public String getFrmPerSec() {
		return frmPerSec;
	}
	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}
	public String getMeCd() {
		return meCd;
	}
	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
}

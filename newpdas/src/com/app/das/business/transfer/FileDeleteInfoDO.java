package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 파일 삭제 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class FileDeleteInfoDO extends DTO 
{
	
	/**
	 * 콘텐츠ID
	 */
	private long ctId;
	/**
	 * 콘텐츠 인스탄스 ID
	 */
	private long ctiId;
	/**
	 * 파일명
	 */
	private String flNm = Constants.BLANK;
	/**
	 * 파일크기
	 */
	private BigDecimal flSz     = Constants.ZERO;
	/**
	 * 파일경로
	 */
	private String flPath           = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId           = Constants.BLANK;
	
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public BigDecimal getFlSz() {
		return flSz;
	}
	public void setFlSz(BigDecimal flSz) {
		this.flSz = flSz;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrId() {
		return regrId;
	}
	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}	

}

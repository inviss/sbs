package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 첨부 파일 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class FileInfoDO extends DTO 
{
	/**
	 * 모자료ID
	 */
	private int mothrDataId ;
	/**
	 * 순번
	 */
	private int seq;
	/**
	 * 첨부파일 유형코드
	 */
	private String attcFileTypeCd   = Constants.BLANK;
	/**
	 * 첨부구분코드
	 */
	private String attcClfCd        = Constants.BLANK;
	/**
	 * 파일명
	 */
	private String flNm = Constants.BLANK;
	/**
	 * 파일크기
	 */
	private BigDecimal flSz     = Constants.ZERO;
	/**
	 * 파일크기
	 */
	private long fl_size     = 0;
	/**
	 * 파일경로
	 */
	private String flPath           = Constants.BLANK;
	/**
	 * 원파일명
	 */
	private String orgFileNm        = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId           = Constants.BLANK;
	/**
	 * 수정일
	 */
	private String modDt            = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId           = Constants.BLANK;
	public String getAttcClfCd() {
		return attcClfCd;
	}
	public void setAttcClfCd(String attcClfCd) {
		this.attcClfCd = attcClfCd;
	}
	public String getAttcFileTypeCd() {
		return attcFileTypeCd;
	}
	public void setAttcFileTypeCd(String attcFileTypeCd) {
		this.attcFileTypeCd = attcFileTypeCd;
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
	public long getFl_size() {
		return fl_size;
	}
	public void setFl_size(long flSize) {
		fl_size = flSize;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrId() {
		return modrId;
	}
	public void setModrId(String modrId) {
		this.modrId = modrId;
	}
	public int getMothrDataId() {
		return mothrDataId;
	}
	public void setMothrDataId(int mothrDataId) {
		this.mothrDataId = mothrDataId;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
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
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}

}

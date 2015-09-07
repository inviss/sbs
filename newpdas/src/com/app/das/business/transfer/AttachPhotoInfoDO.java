package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 사진정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class AttachPhotoInfoDO extends DTO 
{
	/**
	 * 파일경로
	 */
	private String flPath           = Constants.BLANK;
	/**
	 * 사진등록ID
	 */
	private long photRegId;
	/**
	 *  해상도
	 */
	private String resoultion        = Constants.BLANK;

	/**
	 * 다운로드 금지여부
	 */
	private String down_yn           = Constants.BLANK;
	/**
	 * 저작권자
	 */
	private String cprtr_nm = Constants.BLANK;
	/**
	 * 내용
	 */
	private String cont             = Constants.BLANK;
	/**
	 * 프로그램 제목
	 */
	private String title 		= Constants.BLANK;
	/**
	 * 프로그램 ID
	 */
	private    long      pgm_id;

	
	/**
	 * 사진크기
	 */
	private    int       fl_sz;
	/**
	 * 방송일
	 */
	private    String      brd_dd;
	/**
	 * 등록일
	 */
	private    String       regDt     = Constants.BLANK;
	
	/**
	 * 회차
	 */
	private    String       epis_no     = Constants.BLANK;
	
	/**
	 * 순번
	 */
	private    int       seq;
	
	
	/**
	 * 길이
	 */
	private    String       length     = Constants.BLANK;

	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public long getPhotRegId() {
		return photRegId;
	}
	public void setPhotRegId(long photRegId) {
		this.photRegId = photRegId;
	}
	public String getResoultion() {
		return resoultion;
	}
	public void setResoultion(String resoultion) {
		this.resoultion = resoultion;
	}

	public String getDown_yn() {
		return down_yn;
	}
	public void setDown_yn(String downYn) {
		down_yn = downYn;
	}
	public String getCprtr_nm() {
		return cprtr_nm;
	}
	public void setCprtr_nm(String cprtrNm) {
		cprtr_nm = cprtrNm;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(long pgmId) {
		pgm_id = pgmId;
	}
	public int getFl_sz() {
		return fl_sz;
	}
	public void setFl_sz(int flSz) {
		fl_sz = flSz;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	

}

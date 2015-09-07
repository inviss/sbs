package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 정보를 포함하고 있는 DataObject
 * @author ysk523
 */
public class CartDetailItemDO extends DTO 
{
	/**
	 * 카트번호
	 */
	private BigDecimal cartNo          = Constants.ZERO;
	/**
	 * 순번
	 */
	private int seq;
	/**
	 * 요청자이름
	 */
	private String reqNm           = Constants.BLANK;
	/**
	 * 파일명
	 */
	private String flNm            = Constants.BLANK;
	/**
	 * 프로그램제목
	 */
	private String pgmNm           = Constants.BLANK;
	/**
	 * 시작점
	 */
	private String som              = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom              = Constants.BLANK;
	/**
	 * 사용제한구분코드
	 */
	private String ristClfCd      = Constants.BLANK;
	/**
	 * 사용제한구분
	 */
	private String ristClfNm	= Constants.BLANK;
	/**
	 * 영상ID 
	 */
	private String ct_id = Constants.BLANK;
	/**
	 * 확인사항 
	 */
	private String app_cont = Constants.BLANK;
	/**
	 * s_frame
	 */
	private String s_frame = Constants.BLANK;
	/**
	 * duration 
	 */
	private String duration = Constants.BLANK;
	
	
	public String getS_frame() {
		return s_frame;
	}
	public void setS_frame(String s_frame) {
		this.s_frame = s_frame;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getApp_cont() {
		return app_cont;
	}
	public void setApp_cont(String app_cont) {
		this.app_cont = app_cont;
	}
	public String getCt_id() {
		return ct_id;
	}
	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}
	public BigDecimal getCartNo() {
		return cartNo;
	}
	public void setCartNo(BigDecimal cartNo) {
		this.cartNo = cartNo;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getRistClfNm() {
		return ristClfNm;
	}
	public void setRistClfNm(String ristClfNm) {
		this.ristClfNm = ristClfNm;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
}

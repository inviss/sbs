package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 요청 영상 목록 조회 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class ReqDownItemDO extends DTO 
{
	/**
	 * 순번
	 */
	private int serialNo;
	/**
	 * 카트번호
	 */
	private BigDecimal cartNo = Constants.ZERO;
	/**
	 * 카트내순번
	 */
	private int cartSeq;
	/**
	 * 화질
	 */
	private String vdQltyNm = Constants.BLANK;
	/**
	 * 종횡비
	 */
	private String aspRtoNm = Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String pgmNm	= Constants.BLANK;
	/**
	 * 회차
	 */
	private int episNo;
	/**
	 * 시작점
	 */
	private String som       = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom       = Constants.BLANK;
	/**
	 * 사용제한구
	 */
	private String ristClfNm = Constants.BLANK;
	public String getAspRtoNm() {
		return aspRtoNm;
	}
	public void setAspRtoNm(String aspRtoNm) {
		this.aspRtoNm = aspRtoNm;
	}
	public BigDecimal getCartNo() {
		return cartNo;
	}
	public void setCartNo(BigDecimal cartNo) {
		this.cartNo = cartNo;
	}
	public int getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(int cartSeq) {
		this.cartSeq = cartSeq;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public int getEpisNo() {
		return episNo;
	}
	public void setEpisNo(int episNo) {
		this.episNo = episNo;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getRistClfNm() {
		return ristClfNm;
	}
	public void setRistClfNm(String ristClfNm) {
		this.ristClfNm = ristClfNm;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getVdQltyNm() {
		return vdQltyNm;
	}
	public void setVdQltyNm(String vdQltyNm) {
		this.vdQltyNm = vdQltyNm;
	}

}

package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 다운로드 진행상황 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class DownStatusInfoDO extends DTO 
{
	/**
	 * 카트번호
	 */
	private int cartNo;
	/**
	 * 카트일련번호
	 */
	private int cartSeq;
	/**
	 * 요청자ID
	 */
	private String reqUsrId         = Constants.BLANK;
	/**
	 * 요청자명
	 */
	private String userNm 	        = Constants.BLANK;
	/**
	 * 요청일시
	 */
	private String reqDt            = Constants.BLANK;
	/**
	 * 다운로드 제목
	 */
	private String downSubj         = Constants.BLANK;
	/**
	 * 화질코드
	 */
	private String vdQlty           = Constants.BLANK;
	/**
	 * 화질명
	 */
	private String vdQltyNm         = Constants.BLANK;
	/**
	 * 종횡비코드
	 */
	private String aspRtoCd         = Constants.BLANK;
	/**
	 * 종횡비명
	 */
	private String aspRtoNm  	= Constants.BLANK;
	/**
	 * 받은용량
	 */
	private String downSize;
	/**
	 * 부서명
	 */
	private String deptNm 	        = Constants.BLANK;
	/**
	 * 총용량
	 */
	private String totalSize;
	/**
	 * 진행율
	 */
	private BigDecimal percnt = Constants.ZERO;
	/**
	 * 순번
	 */
	private int serialNo;
	/**
	 * 상태
	 */
	private String downStatus = Constants.BLANK;
	/**
	 * 상태명
	 */
	private String downStatusNm = Constants.BLANK;
	/**
	 * master_id
	 */
	private String master_id = Constants.BLANK;
	
	/**
	 *다운로드 구분(001: PDS , 002:NDS , 003:데정팀 , 004:tape-out, 005:계열사 )
	 */
	private String down_gubun		= Constants.BLANK;
	
	private String use_limit_count = Constants.BLANK;
	
	private String app_cont = Constants.BLANK;
	
	private String use_limit_flag =Constants.BLANK;
	
	
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getAspRtoNm() {
		return aspRtoNm;
	}
	public void setAspRtoNm(String aspRtoNm) {
		this.aspRtoNm = aspRtoNm;
	}
	public String getDownSize() {
		return downSize;
	}
	public void setDownSize(String downSize) {
		this.downSize = downSize;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public BigDecimal getPercnt() {
		return percnt;
	}
	public void setPercnt(BigDecimal percnt) {
		this.percnt = percnt;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getReqUsrId() {
		return reqUsrId;
	}
	public void setReqUsrId(String reqUsrId) {
		this.reqUsrId = reqUsrId;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(String totalSize) {
		this.totalSize = totalSize;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getVdQltyNm() {
		return vdQltyNm;
	}
	public void setVdQltyNm(String vdQltyNm) {
		this.vdQltyNm = vdQltyNm;
	}
	public String getDownStatus() {
		return downStatus;
	}
	public void setDownStatus(String downStatus) {
		this.downStatus = downStatus;
	}
	public String getDownStatusNm() {
		return downStatusNm;
	}
	public void setDownStatusNm(String downStatusNm) {
		this.downStatusNm = downStatusNm;
	}
	public int getCartNo() {
		return cartNo;
	}
	public void setCartNo(int cartNo) {
		this.cartNo = cartNo;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public int getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(int cartSeq) {
		this.cartSeq = cartSeq;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	
}

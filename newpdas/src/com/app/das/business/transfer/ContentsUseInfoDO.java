package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 자료이용현황 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class ContentsUseInfoDO extends DTO 
{
	/**
	 * 사용자ID
	 */
	private String reqUsrId  = Constants.BLANK;
	/**
	 * 사용자명
	 */
	private String userNm    = Constants.BLANK;
	/**
	 * Contents 명
	 */
	private String ctNm      = Constants.BLANK;
	/**
	 * 다운로드일
	 */
	private String downDt    = Constants.BLANK;
	/**
	 * 시작점
	 */
	private String som        = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom        = Constants.BLANK;
	/**
	 *  등록일
	 */
	private String req_dt = Constants.BLANK; 
	/**
	 *  사진 제목
	 */
	private String subj = Constants.BLANK;
	/**
	 *  사진 등록 ID
	 */
	private String phot_id = Constants.BLANK;
	/**
	 *  카트 일련 번호 
	 */
	private String cart_No = Constants.BLANK;
	/**
	 *  카트 시퀀스 번호 
	 */
	private String cart_Seq = Constants.BLANK;
	/**
	 *  타이틀 
	 */
	private String title = Constants.BLANK;
	/**
	 *  ct_id
	 */
	private String ctId = Constants.BLANK;
	/**
	 *  rist_clf_cd
	 */
	private String rist_clf_cd = Constants.BLANK;
	
	

	public String getCart_No() {
		return cart_No;
	}
	public void setCart_No(String cart_No) {
		this.cart_No = cart_No;
	}
	public String getPhot_id() {
		return phot_id;
	}
	public void setPhot_id(String phot_id) {
		this.phot_id = phot_id;
	}
	public String getSubj() {
		return subj;
	}
	public void setSubj(String subj) {
		this.subj = subj;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getDownDt() {
		return downDt;
	}
	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getReqUsrId() {
		return reqUsrId;
	}
	public void setReqUsrId(String reqUsrId) {
		this.reqUsrId = reqUsrId;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
	}
	public String getCart_Seq() {
		return cart_Seq;
	}
	public void setCart_Seq(String cart_Seq) {
		this.cart_Seq = cart_Seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getRist_clf_cd() {
		return rist_clf_cd;
	}
	public void setRist_clf_cd(String rist_clf_cd) {
		this.rist_clf_cd = rist_clf_cd;
	}
}

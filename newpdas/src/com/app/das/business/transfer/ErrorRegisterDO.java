package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 오류 내역 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class ErrorRegisterDO extends DTO 
{
	/**
	 * 콘텐츠인스턴스ID
	 */
	private String ctiId 		= Constants.BLANK;
	/**
	 * 마스터 ID
	 */
	private String masterId 		= Constants.BLANK;
	/**
	 * 작성자
	 */
	private String wrt              = Constants.BLANK;
	/**
	 * 작업구분
	 */
	private String workClf          = Constants.BLANK;
	/**
	 * 내용
	 */
	private String cont             = Constants.BLANK;
	/**
	 * 이미지
	 */
	private String img              = Constants.BLANK;
	/**
	 * 작업지시내용
	 */
	private String workCmCont       = Constants.BLANK;
	/**
	 * 작업순서
	 */
	private String workSeq          = Constants.BLANK;
	/**
	 * 이미지파일경
	 */
	private String imtPath          = Constants.BLANK;
	/**
	 * 등록자 ID
	 */
	private String regrId          = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt          = Constants.BLANK;
	/**
	 * 수정자 ID
	 */
	private String modrId          = Constants.BLANK;
	/**
	 * 수정일
	 */
	private String modDt          = Constants.BLANK;
	/**
	 * 프로그램 명
	 */
	private String pgm_Nm 		= Constants.BLANK;
	/**
	 * 청구번호 
	 */
	private String req_cd = Constants.BLANK;
	/**
	 * 회차 
	 */
	private String epis_no = Constants.BLANK;
	
	
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String epis_no) {
		this.epis_no = epis_no;
	}
	public String getReq_cd() {
		return req_cd;
	}
	public void setReq_cd(String req_cd) {
		this.req_cd = req_cd;
	}
	public String getPgm_Nm() {
		return pgm_Nm;
	}
	public void setPgm_Nm(String pgm_Nm) {
		this.pgm_Nm = pgm_Nm;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterID(String masterId) {
		this.masterId = masterId;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getCtiId() {
		return ctiId;
	}
	public void setCtiId(String ctiId) {
		this.ctiId = ctiId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getImtPath() {
		return imtPath;
	}
	public void setImtPath(String imtPath) {
		this.imtPath = imtPath;
	}
	public String getWorkClf() {
		return workClf;
	}
	public void setWorkClf(String workClf) {
		this.workClf = workClf;
	}
	public String getWorkCmCont() {
		return workCmCont;
	}
	public void setWorkCmCont(String workCmCont) {
		this.workCmCont = workCmCont;
	}
	public String getWorkSeq() {
		return workSeq;
	}
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}
	public String getWrt() {
		return wrt;
	}
	public void setWrt(String wrt) {
		this.wrt = wrt;
	}
	public String getRegrId() {
		return regrId;
	}
	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getModrId() {
		return modrId;
	}
	public void setModrId(String modrId) {
		this.modrId = modrId;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	
}

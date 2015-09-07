package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 미디어 정보를 포함하고 있는 DataObejct (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class MappingDO extends DTO 
{
	/**
	 *  코너 명
	 */
	private String cn_nm = Constants.BLANK;
	
	/**
	 *  콘텐츠 SEQ
	 */
	private String ct_seq = Constants.BLANK;
	
	/**
	 * 콘텐츠 명
	 */
	private String ct_nm = Constants.BLANK;
	
	/**
	 *  등록일
	 */
	private String reg_dt = Constants.BLANK;
	
	/**
	 *  수정일
	 */
	private String mod_dt = Constants.BLANK;
	
	/**
	 *  시작 길이
	 */
	private String s_duration = Constants.BLANK;
	
	/**
	 *  종료 길이
	 */
	private String e_duration = Constants.BLANK;
	
	/**
	 *  Master_id
	 */
	private String master_id = Constants.BLANK;
	
	/**
	 *  ct_id
	 */
	private String ct_id = Constants.BLANK;
	
	/**
	 *  pgm_id
	 */
	private String pgm_id = Constants.BLANK;
	
	/**
	 *  cn_id
	 */
	private String cn_id = Constants.BLANK;
	
	/**
	 *  등록자 ID
	 */
	private String regrid = Constants.BLANK;
	
	/**
	 * 수정자 ID
	 */
	private String modrid = Constants.BLANK;

	/**
	 *  코너 시퀀스
	 */
	private String cn_seq = Constants.BLANK;
	
	
	
	public String getCn_seq() {
		return cn_seq;
	}

	public void setCn_seq(String cn_seq) {
		this.cn_seq = cn_seq;
	}

	public String getCn_id() {
		return cn_id;
	}

	public void setCn_id(String cn_id) {
		this.cn_id = cn_id;
	}

	public String getCn_nm() {
		return cn_nm;
	}

	public void setCn_nm(String cn_nm) {
		this.cn_nm = cn_nm;
	}

	public String getCt_id() {
		return ct_id;
	}

	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}

	public String getCt_nm() {
		return ct_nm;
	}

	public void setCt_nm(String ct_nm) {
		this.ct_nm = ct_nm;
	}

	public String getCt_seq() {
		return ct_seq;
	}

	public void setCt_seq(String ct_seq) {
		this.ct_seq = ct_seq;
	}

	public String getE_duration() {
		return e_duration;
	}

	public void setE_duration(String e_duration) {
		this.e_duration = e_duration;
	}

	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}

	public String getMod_dt() {
		return mod_dt;
	}

	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}

	public String getModrid() {
		return modrid;
	}

	public void setModrid(String modrid) {
		this.modrid = modrid;
	}

	public String getPgm_id() {
		return pgm_id;
	}

	public void setPgm_id(String pgm_id) {
		this.pgm_id = pgm_id;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getS_duration() {
		return s_duration;
	}

	public void setS_duration(String s_duration) {
		this.s_duration = s_duration;
	}
	
	
}

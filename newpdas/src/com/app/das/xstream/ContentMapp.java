package com.app.das.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ContentMappInfo")
public class ContentMapp {
	
	private String ct_id;
	private String master_id;
	private String cn_id;
	private String pgm_id;
	private String reg_dt;
	private String regrid;
	private String s_duration;
	private String e_duration;
	private String cn_seq;
	private String ct_seq;
	
	public String getCt_id() {
		return ct_id;
	}
	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public String getCn_id() {
		return cn_id;
	}
	public void setCn_id(String cn_id) {
		this.cn_id = cn_id;
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
	public String getE_duration() {
		return e_duration;
	}
	public void setE_duration(String e_duration) {
		this.e_duration = e_duration;
	}
	public String getCn_seq() {
		return cn_seq;
	}
	public void setCn_seq(String cn_seq) {
		this.cn_seq = cn_seq;
	}
	public String getCt_seq() {
		return ct_seq;
	}
	public void setCt_seq(String ct_seq) {
		this.ct_seq = ct_seq;
	}

}

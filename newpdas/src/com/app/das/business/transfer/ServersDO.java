package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 서버 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class ServersDO extends DTO 
{
	/**
	 * 서버명
	 */
	private String server_nm = Constants.BLANK;
	
	/**
	 * 상태
	 */
	private String state = Constants.BLANK;
	
	/**
	 * 통신상태
	 */
	private String net_state = Constants.BLANK;
	
	/**
	 * ip
	 */
	private String ip = Constants.BLANK;
	
	/**
	 * 체널
	 */
	private String ch_seq = Constants.BLANK;
	
	/**
	 * 서버재구동시간
	 */
	private String re_start_dt = Constants.BLANK;
	
	/**
	 * 확인시간
	 */
	private String confirm_dt = Constants.BLANK;
	
	/**
	 * 제목
	 */
	private String title = Constants.BLANK;

	
	/**
	 * 장비구분코드
	 */
	private String das_eq_clf_cd = Constants.BLANK;

	
	/**
	 * 장비id
	 */
	private String das_eq_id = Constants.BLANK;
	/**
	 *포트
	 */
	private String port = Constants.BLANK;

	
	
	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDas_eq_clf_cd() {
		return das_eq_clf_cd;
	}

	public void setDas_eq_clf_cd(String dasEqClfCd) {
		das_eq_clf_cd = dasEqClfCd;
	}

	public String getDas_eq_id() {
		return das_eq_id;
	}

	public void setDas_eq_id(String dasEqId) {
		das_eq_id = dasEqId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getServer_nm() {
		return server_nm;
	}

	public void setServer_nm(String serverNm) {
		server_nm = serverNm;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNet_state() {
		return net_state;
	}

	public void setNet_state(String netState) {
		net_state = netState;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCh_seq() {
		return ch_seq;
	}

	public void setCh_seq(String chSeq) {
		ch_seq = chSeq;
	}

	public String getRe_start_dt() {
		return re_start_dt;
	}

	public void setRe_start_dt(String reStartDt) {
		re_start_dt = reStartDt;
	}

	public String getConfirm_dt() {
		return confirm_dt;
	}

	public void setConfirm_dt(String confirmDt) {
		confirm_dt = confirmDt;
	}
	
	

	
	
	
}

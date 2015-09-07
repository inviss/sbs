package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 프로그램 연관  정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class RelationDO extends DTO 
{
	/**
	 * 프로그램명
	 */
	private String title           = Constants.BLANK;
	/**
	 * 구분
	 */
	private String ctgr_l_nm           = Constants.BLANK;
	/**
	 * 장르
	 */
	private String ctgr_ms_nm           = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brd_dd           = Constants.BLANK;
	/**
	 * 화질
	 */
	private String vd_qlty           = Constants.BLANK;
	/**
	 * 길이
	 */
	private String brd_leng           = Constants.BLANK;
	/**
	 * 마스터id
	 */
	private Long master_id;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCtgr_l_nm() {
		return ctgr_l_nm;
	}
	public void setCtgr_l_nm(String ctgrLNm) {
		ctgr_l_nm = ctgrLNm;
	}
	public String getCtgr_ms_nm() {
		return ctgr_ms_nm;
	}
	public void setCtgr_ms_nm(String ctgrMsNm) {
		ctgr_ms_nm = ctgrMsNm;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getVd_qlty() {
		return vd_qlty;
	}
	public void setVd_qlty(String vdQlty) {
		vd_qlty = vdQlty;
	}
	public String getBrd_leng() {
		return brd_leng;
	}
	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}
	public Long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(Long masterId) {
		master_id = masterId;
	}
	

}

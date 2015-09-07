package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 오늘의 영상 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class TodayDO extends DTO 
{
	
	/**
	 * 프로그램 제목
	 */
	private String title = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brd_dd = Constants.BLANK;
	/**
	 * 방송길이
	 */
	private String brd_leng = Constants.BLANK;
	/**
	 * 상태
	 */
	private String arch_stat = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String reg_dt = Constants.BLANK;
	/**
	 * 아카이브여부
	 */
	private String arch_yn = Constants.BLANK;
	
	/**
	 * 마스터 ID
	 */
	private Long master_id;
	
	
	
	/**
	 * 회차
	 */
	private String epis_no = Constants.BLANK;
	/**
	 * 사용등급
	 */
	private String limit_use = Constants.BLANK;
	/**
	 * 클립길이
	 */
	private String ct_leng = Constants.BLANK;
	
	/**
	 * 대분류
	 */
	private String ctgr_l_Cd = Constants.BLANK;
	
	
	public String getCtgr_l_Cd() {
		return ctgr_l_Cd;
	}
	public void setCtgr_l_Cd(String ctgrLCd) {
		ctgr_l_Cd = ctgrLCd;
	}
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}
	public String getLimit_use() {
		return limit_use;
	}
	public void setLimit_use(String limitUse) {
		limit_use = limitUse;
	}
	public String getCt_leng() {
		return ct_leng;
	}
	public void setCt_leng(String ctLeng) {
		ct_leng = ctLeng;
	}
	public Long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(Long masterId) {
		master_id = masterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getBrd_leng() {
		return brd_leng;
	}
	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}
	public String getArch_stat() {
		return arch_stat;
	}
	public void setArch_stat(String archStat) {
		arch_stat = archStat;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
	public String getArch_yn() {
		return arch_yn;
	}
	public void setArch_yn(String archYn) {
		arch_yn = archYn;
	}
	
}

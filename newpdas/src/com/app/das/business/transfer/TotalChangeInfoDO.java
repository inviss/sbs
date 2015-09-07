package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 일괄 수정 정보를 담고 있는 Object
 * @author dekim
 *
 */
public class TotalChangeInfoDO extends DTO 
{
	/**
	 * 수정 필드 구분
	 */
	private String gubun = Constants.BLANK;
	
	/**
	 * 단일 마스터 아이디
	 */
	private long masterId = 0;
	
	/**
	 * 마스터 아이디 그룹
	 */
	private String masterIdGrp = Constants.BLANK;
	
	/**
	 * 보존기간 코드
	 */
	private String rsv_prd_cd = Constants.BLANK;
	
	/**
	 * 대분류 코드
	 */
	private String ctgr_l_cd = Constants.BLANK;
	
	/**
	 * 중분류 코드
	 */
	private String ctgr_m_cd = Constants.BLANK;
	
	/**
	 * 소분류 코드
	 */
	private String ctgr_s_cd = Constants.BLANK;
	
	/**
	 * 장르
	 */
	private String ctgr = Constants.BLANK;
	
	/**
	 * 제작구분코드
	 */
	private String prdt_in_outs_cd = Constants.BLANK;
	
	/**
	 * 제작부서코드
	 */
	private String prdt_dept_cd = Constants.BLANK;
	
	/**
	 * 제작부서명
	 */
	private String prdt_dept_nm = Constants.BLANK;
	
	/**
	 * 촬영장소 
	 */
	private String cmr_place = Constants.BLANK;
	
	/**
	 * 저작권형태 코드 저작권형태코드\N(001 일체소유 002 일부소유 003 저작권없음)
	 */
	private String cprt_type = Constants.BLANK;
	
	/**
	 * 저작권형태 설명
	 */
	private String cprt_type_dsc = Constants.BLANK;
	
	/**
	 * 저작권자명
	 */
	private String cprtr_nm = Constants.BLANK;
	
	/**
	 * 녹음방식 코드 녹음방식코드(001 모노 002 스테레오 003 5.1채널 004 음성다중)
	 */
	private String record_type_cd = Constants.BLANK;
	/**
	 * 수상내역
	 */
	private String award_hstr = Constants.BLANK;
	/**
	 * 외주제작사
	 */
	private String org_prdr_nm = Constants.BLANK;
	
	
	
	public String getAward_hstr() {
		return award_hstr;
	}

	public void setAward_hstr(String awardHstr) {
		award_hstr = awardHstr;
	}

	public String getPrdt_dept_nm() {
		return prdt_dept_nm;
	}

	public void setPrdt_dept_nm(String prdt_dept_nm) {
		this.prdt_dept_nm = prdt_dept_nm;
	}

	public String getCtgr() {
		return ctgr;
	}

	public void setCtgr(String ctgr) {
		this.ctgr = ctgr;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public String getMasterIdGrp() {
		return masterIdGrp.trim().replace(",,", ",");
	}

	public void setMasterIdGrp(String masterIdGrp) {
		this.masterIdGrp = masterIdGrp;
	}

	public String getRsv_prd_cd() {
		return rsv_prd_cd;
	}

	public void setRsv_prd_cd(String rsv_prd_cd) {
		this.rsv_prd_cd = rsv_prd_cd;
	}

	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}

	public void setCtgr_l_cd(String ctgr_l_cd) {
		this.ctgr_l_cd = ctgr_l_cd;
	}

	public String getCtgr_m_cd() {
		return ctgr_m_cd;
	}

	public void setCtgr_m_cd(String ctgr_m_cd) {
		this.ctgr_m_cd = ctgr_m_cd;
	}

	public String getCtgr_s_cd() {
		return ctgr_s_cd;
	}

	public void setCtgr_s_cd(String ctgr_s_cd) {
		this.ctgr_s_cd = ctgr_s_cd;
	}

	public String getPrdt_in_outs_cd() {
		return prdt_in_outs_cd;
	}

	public void setPrdt_in_outs_cd(String prdt_in_outs_cd) {
		this.prdt_in_outs_cd = prdt_in_outs_cd;
	}

	public String getPrdt_dept_cd() {
		return prdt_dept_cd;
	}

	public void setPrdt_dept_cd(String prdt_dept_cd) {
		this.prdt_dept_cd = prdt_dept_cd;
	}

	public String getCmr_place() {
		return cmr_place;
	}

	public void setCmr_place(String cmr_place) {
		this.cmr_place = cmr_place;
	}

	public String getCprt_type() {
		return cprt_type;
	}

	public void setCprt_type(String cprt_type) {
		this.cprt_type = cprt_type;
	}

	public String getCprt_type_dsc() {
		return cprt_type_dsc;
	}

	public void setCprt_type_dsc(String cprt_type_dsc) {
		this.cprt_type_dsc = cprt_type_dsc;
	}

	public String getCprtr_nm() {
		return cprtr_nm;
	}

	public void setCprtr_nm(String cprtr_nm) {
		this.cprtr_nm = cprtr_nm;
	}

	public String getRecord_type_cd() {
		return record_type_cd;
	}

	public void setRecord_type_cd(String record_type_cd) {
		this.record_type_cd = record_type_cd;
	}

	public String getOrg_prdr_nm() {
		return org_prdr_nm;
	}

	public void setOrg_prdr_nm(String orgPrdrNm) {
		org_prdr_nm = orgPrdrNm;
	}
	
}

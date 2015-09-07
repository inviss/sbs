package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * NLE Drag&Drop 정보를 담고 있는 Object
 * @author dekim
 *
 */
public class PreProcessingDO extends DTO 
{
	/**
	 * 스토리지 구분코드  
	 */
	private String st_gubun = Constants.BLANK;
	/**
	 * 콘텐츠 구분
	 */
	private String ct_cla = Constants.BLANK;
	/**
	 * 시작일
	 */
	private String fromDate = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String toDate = Constants.BLANK;
	/**
	 * 검색어
	 */
	private String searchKey = Constants.BLANK;
	
	/**
	 * 대분류명
	 */
	private String ctgr_nm = Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	/**
	 * 미디어 ID
	 */
	private String media_id = Constants.BLANK;
	
	/**
	 * 콘텐츠 구분 명
	 */
	private String ct_cla_nm = Constants.BLANK;
	/**
	 * 요청일
	 */
	private String req_dt = Constants.BLANK;
	/**
	 * 요청자명
	 */
	private String req_nm = Constants.BLANK;
	
	/**
	 * 파일 풀패스
	 */
	private String fullpath = Constants.BLANK;
	
	/**
	 * 컨텐츠 ID
	 */
	private long ct_id;
	
	
	/**
	 * 카트번호
	 */
	private String cart_no = Constants.BLANK;
	/**
	 * 카트순번
	 */
	private String cart_seq = Constants.BLANK;
	
	
	/**
	 *  마스터 id
	 */
	private String master_id = Constants.BLANK;
	
	
	/**
	 *  프로그램 id
	 */
	private long pgm_id;
	
	
	/**
	 * 구분
	 */
	private String gubun = Constants.BLANK;
	
	/**
	 * 편집 구분 ( 001 편집중, 002 편집완료)
	 */
	private String edtrid = Constants.BLANK;
	
	/**
	 * 정리상태
	 */
	private String data_stat_cd = Constants.BLANK;
	
	/**
	 * tc_yn
	 */
	private String tc_yn = Constants.BLANK;
	
	//2012.5.14
	
	/**
	 * 채널구분
	 */
	private String chennel = Constants.BLANK;
	
	
	
	
	public String getChennel() {
		return chennel;
	}
	public void setChennel(String chennel) {
		this.chennel = chennel;
	}
	public String getTc_yn() {
		return tc_yn;
	}
	public void setTc_yn(String tcYn) {
		tc_yn = tcYn;
	}
	public String getData_stat_cd() {
		return data_stat_cd;
	}
	public void setData_stat_cd(String dataStatCd) {
		data_stat_cd = dataStatCd;
	}
	public String getEdtrid() {
		return edtrid;
	}
	public void setEdtrid(String edtrid) {
		this.edtrid = edtrid;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public long getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(long pgmId) {
		pgm_id = pgmId;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String masterId) {
		master_id = masterId;
	}
	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
	}
	public String getCart_no() {
		return cart_no;
	}
	public void setCart_no(String cartNo) {
		cart_no = cartNo;
	}
	public String getCart_seq() {
		return cart_seq;
	}
	public void setCart_seq(String cartSeq) {
		cart_seq = cartSeq;
	}
	public String getFullpath() {
		return fullpath;
	}
	public void setFullpath(String fullpath) {
		this.fullpath = fullpath;
	}
	public String getSt_gubun() {
		return st_gubun;
	}
	public void setSt_gubun(String st_gubun) {
		this.st_gubun = st_gubun;
	}
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ct_cla) {
		this.ct_cla = ct_cla;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	public String getCtgr_nm() {
		return ctgr_nm;
	}
	public void setCtgr_nm(String ctgr_nm) {
		this.ctgr_nm = ctgr_nm;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgm_nm) {
		this.pgm_nm = pgm_nm;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getCt_cla_nm() {
		return ct_cla_nm;
	}
	public void setCt_cla_nm(String ct_cla_nm) {
		this.ct_cla_nm = ct_cla_nm;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String req_dt) {
		this.req_dt = req_dt;
	}
	public String getReq_nm() {
		return req_nm;
	}
	public void setReq_nm(String req_nm) {
		this.req_nm = req_nm;
	}
	
}

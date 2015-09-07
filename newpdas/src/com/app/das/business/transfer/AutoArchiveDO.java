package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * 자동아카이브 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class AutoArchiveDO {
	
	/** 
	 * 구분코드
	 */
	private String clf_cd = Constants.BLANK;
	
	

	/** 
	 * 구분상세코드
	 */
	private String scl_cd = Constants.BLANK;
	
	/** 
	 * 소재종류
	 */
	private String desc = Constants.BLANK;
	
	/** 
	 * 설정
	 */
	private String auto_yn = Constants.BLANK;
	
	/** 
	 * 페이지
	 */
	private int page;
	
	/** 
	 * 순번
	 */
	private int seq;

	
	//2012.04.23
	/** 
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	
	/** 
	 * 채널코드
	 */
	private String chennel = Constants.BLANK;
	
	/** 
	 * 회사명
	 */
	private String conm = Constants.BLANK;
	
	/** 
	 * 채널명
	 */
	private String chennelnm = Constants.BLANK;
	
	
	/** 
	 * 아카이브 경로
	 */
	private String arch_route = Constants.BLANK;
	
	/** 
	 * 아카이브 경로명
	 */
	private String arch_route_nm = Constants.BLANK;
	
	
	
	
	
	public String getArch_route_nm() {
		return arch_route_nm;
	}

	public void setArch_route_nm(String archRouteNm) {
		arch_route_nm = archRouteNm;
	}

	public String getArch_route() {
		return arch_route;
	}

	public void setArch_route(String archRoute) {
		arch_route = archRoute;
	}

	public String getConm() {
		return conm;
	}

	public void setConm(String conm) {
		this.conm = conm;
	}

	public String getChennelnm() {
		return chennelnm;
	}

	public void setChennelnm(String chennelnm) {
		this.chennelnm = chennelnm;
	}

	public String getCocd() {
		return cocd;
	}

	public void setCocd(String cocd) {
		this.cocd = cocd;
	}

	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}

	public String getClf_cd() {
		return clf_cd;
	}

	public void setClf_cd(String clfCd) {
		clf_cd = clfCd;
	}

	public String getScl_cd() {
		return scl_cd;
	}

	public void setScl_cd(String sclCd) {
		scl_cd = sclCd;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuto_yn() {
		return auto_yn;
	}

	public void setAuto_yn(String autoYn) {
		auto_yn = autoYn;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
}

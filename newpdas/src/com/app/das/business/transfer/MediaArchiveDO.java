package com.app.das.business.transfer;
import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 수동아카이브 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class MediaArchiveDO extends DTO {
	
	/**
	 * 회사코드(계열사 구분)

	 */
	private String cocd = Constants.BLANK;
	
	/**
	 * 자료구분

	 */
	private String ctgr_lcd = Constants.BLANK;
	
	/**
	 * 테이프종류코드

	 */
	private String tpae_media_clf_cd = Constants.BLANK;
	
	/**
	 * 테이프 길이

	 */
	private String tape_leng = Constants.BLANK;
	
	/**
	 * 청구번호

	 */
	private String req_cd = Constants.BLANK;
	
	/**
	 * 장면번호

	 */
	private int scean_no = 0;
	
	/**
	 * 제목

	 */
	private String title = Constants.BLANK;
	
	/**
	 * 부제

	 */
	private String sub_ttl = Constants.BLANK;
	
	/**
	 * 회차

	 */
	private int epis_no = 0;
	
	/**
	 * 방송일

	 */
	private String brd_dd = Constants.BLANK;
	
	/**
	 * 촬영장소

	 */
	private String cmr_place = Constants.BLANK;
	
	/**
	 * 화질코드

	 */
	private String vd_qlty = Constants.BLANK;
	
	/**
	 * 시청등급

	 */
	private String view_gr_cd = Constants.BLANK;
	
	/**
	 * 저장권형태

	 */
	private String cprt_type = Constants.BLANK;
	
	/**
	 * 저작권형태설명

	 */
	private String cprt_type_dsc = Constants.BLANK;
	
	/**
	 * 저작권자

	 */
	private String cprt_nm = Constants.BLANK;
	
	/**
	 * 녹음방식

	 */
	private String record_type_cd = Constants.BLANK;
	
	/**
	 * 사용등급코드

	 */
	private String rist_clf_Cd = Constants.BLANK;
	
	/**
	 * 사용범위

	 */
	private String rist_clf_range = Constants.BLANK;
	
	/**
	 * 보존기간

	 */
	private String rsv_prd_cd = Constants.BLANK;
	
	/**
	 * 제작구분

	 */
	private String prdt_in_outs_cd = Constants.BLANK;
	
	/**
	 * 외주제작사

	 */
	private String org_prdr_nm = Constants.BLANK;
	
	/**
	 * 자료내용

	 */
	private String cont = Constants.BLANK;
	
	/**
	 * 특이사항

	 */
	private String spc_info = Constants.BLANK;
	
	/**
	 * 아티스트

	 */
	private String aritist = Constants.BLANK;
	
	/**
	 * 중분류

	 */
	private String ctgr_m_cd = Constants.BLANK;
	
	/**
	 * 소분류

	 */
	private String ctgr_s_cd = Constants.BLANK;
	
	/**
	 * 국가구분코드

	 */
	private String country_cd = Constants.BLANK;
	
	/**
	 * 키워드

	 */
	private String key_words = Constants.BLANK;
	
	/**
	 * 연출자

	 */
	private String drt_nm = Constants.BLANK;
	
	/**
	 * 촬영감독

	 */
	private String cmr_drt_nm = Constants.BLANK;
	
	/**
	 * 진행자

	 */
	private String mc_nm = Constants.BLANK;
	
	/**
	 * 작가명

	 */
	private String writer_nm = Constants.BLANK;
	
	/**
	 * 컨텐츠 유형

	 */
	private String ct_cla = Constants.BLANK;
	
	/**
	 * 순번
	 */
	private int 	seq = 0;
	
	/**
	 * 추가사항.
	 */
	private String gubun = Constants.BLANK;

	public String getCocd() {
		return cocd;
	}

	public void setCocd(String cocd) {
		this.cocd = cocd;
	}

	public String getCtgr_lcd() {
		return ctgr_lcd;
	}

	public void setCtgr_lcd(String ctgrLcd) {
		ctgr_lcd = ctgrLcd;
	}

	public String getTpae_media_clf_cd() {
		return tpae_media_clf_cd;
	}

	public void setTpae_media_clf_cd(String tpaeMediaClfCd) {
		tpae_media_clf_cd = tpaeMediaClfCd;
	}

	public String getTape_leng() {
		return tape_leng;
	}

	public void setTape_leng(String tapeLeng) {
		tape_leng = tapeLeng;
	}

	public String getReq_cd() {
		return req_cd;
	}

	public void setReq_cd(String reqCd) {
		req_cd = reqCd;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSub_ttl() {
		return sub_ttl;
	}

	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
	}

	

	public int getScean_no() {
		return scean_no;
	}

	public void setScean_no(int sceanNo) {
		scean_no = sceanNo;
	}

	public int getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(int episNo) {
		epis_no = episNo;
	}

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}

	public String getCmr_place() {
		return cmr_place;
	}

	public void setCmr_place(String cmrPlace) {
		cmr_place = cmrPlace;
	}

	public String getVd_qlty() {
		return vd_qlty;
	}

	public void setVd_qlty(String vdQlty) {
		vd_qlty = vdQlty;
	}

	public String getView_gr_cd() {
		return view_gr_cd;
	}

	public void setView_gr_cd(String viewGrCd) {
		view_gr_cd = viewGrCd;
	}

	public String getCprt_type() {
		return cprt_type;
	}

	public void setCprt_type(String cprtType) {
		cprt_type = cprtType;
	}

	public String getCprt_type_dsc() {
		return cprt_type_dsc;
	}

	public void setCprt_type_dsc(String cprtTypeDsc) {
		cprt_type_dsc = cprtTypeDsc;
	}

	public String getCprt_nm() {
		return cprt_nm;
	}

	public void setCprt_nm(String cprtNm) {
		cprt_nm = cprtNm;
	}

	public String getRecord_type_cd() {
		return record_type_cd;
	}

	public void setRecord_type_cd(String recordTypeCd) {
		record_type_cd = recordTypeCd;
	}

	public String getRist_clf_Cd() {
		return rist_clf_Cd;
	}

	public void setRist_clf_Cd(String ristClfCd) {
		rist_clf_Cd = ristClfCd;
	}

	public String getRist_clf_range() {
		return rist_clf_range;
	}

	public void setRist_clf_range(String ristClfRange) {
		rist_clf_range = ristClfRange;
	}

	public String getRsv_prd_cd() {
		return rsv_prd_cd;
	}

	public void setRsv_prd_cd(String rsvPrdCd) {
		rsv_prd_cd = rsvPrdCd;
	}

	public String getPrdt_in_outs_cd() {
		return prdt_in_outs_cd;
	}

	public void setPrdt_in_outs_cd(String prdtInOutsCd) {
		prdt_in_outs_cd = prdtInOutsCd;
	}

	public String getOrg_prdr_nm() {
		return org_prdr_nm;
	}

	public void setOrg_prdr_nm(String orgPrdrNm) {
		org_prdr_nm = orgPrdrNm;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getSpc_info() {
		return spc_info;
	}

	public void setSpc_info(String spcInfo) {
		spc_info = spcInfo;
	}

	public String getAritist() {
		return aritist;
	}

	public void setAritist(String aritist) {
		this.aritist = aritist;
	}

	public String getCtgr_m_cd() {
		return ctgr_m_cd;
	}

	public void setCtgr_m_cd(String ctgrMCd) {
		ctgr_m_cd = ctgrMCd;
	}

	public String getCtgr_s_cd() {
		return ctgr_s_cd;
	}

	public void setCtgr_s_cd(String ctgrSCd) {
		ctgr_s_cd = ctgrSCd;
	}

	public String getCountry_cd() {
		return country_cd;
	}

	public void setCountry_cd(String countryCd) {
		country_cd = countryCd;
	}

	public String getKey_words() {
		return key_words;
	}

	public void setKey_words(String keyWords) {
		key_words = keyWords;
	}

	public String getDrt_nm() {
		return drt_nm;
	}

	public void setDrt_nm(String drtNm) {
		drt_nm = drtNm;
	}

	public String getCmr_drt_nm() {
		return cmr_drt_nm;
	}

	public void setCmr_drt_nm(String cmrDrtNm) {
		cmr_drt_nm = cmrDrtNm;
	}

	public String getMc_nm() {
		return mc_nm;
	}

	public void setMc_nm(String mcNm) {
		mc_nm = mcNm;
	}

	public String getWriter_nm() {
		return writer_nm;
	}

	public void setWriter_nm(String writerNm) {
		writer_nm = writerNm;
	}

	public String getCt_cla() {
		return ct_cla;
	}

	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
	}



	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	
	
}

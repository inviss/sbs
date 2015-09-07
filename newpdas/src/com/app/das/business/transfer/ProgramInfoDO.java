package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 프로그램 정보 포함
 * @author mhchoi
 *
 */
public class ProgramInfoDO extends DTO{
	
	/**
	 * 프로그램 ID
	 */
	private long pgmId; 
	/**
	 * 마스터 ID
	 */
	private long masterId;      
	/**
	 * 프로그램 이름
	 */
	private String pgmNm            = Constants.BLANK;
	/**
	 * 에피소드 이름
	 */
	private String pgmEpis            = Constants.BLANK;
	/**
	 * 매체
	 */
	private String mediaCd         = Constants.BLANK;
	/**
	 * 채널
	 */
	private String chanCd         = Constants.BLANK;
	/**
	 * 대분류
	 */
	private String ctgrLCd       = Constants.BLANK;
	/**
	 * 중분류
	 */
	private String ctgrMCd        = Constants.BLANK;
	/**
	 * 소분류
	 */
	private String ctgrSCd    = Constants.BLANK;
	/**
	 * 방송 시작일
	 */
	private String brdBgnDd    = Constants.BLANK;
	/**
	 * 방송 종료일
	 */
	private String brdEndDd    = Constants.BLANK;
	/**
	 * 프로그램 코드
	 */
	private String pgmCd    = Constants.BLANK;
	/**
	 * 에피소드 번호
	 */
	private int episNo;
	/**
	 * 에피소드 번호
	 */
	private String epis_No;
	/**
	 * 제작부서 
	 */
	private String prd_Dept_Nm = Constants.BLANK;
	/**
	 * 편성프로명
	 */
	private String schd_Pgm_Nm = Constants.BLANK;
	/**
	 * 수상내역
	 */
	private String award_Hstr = Constants.BLANK;
	/**
	 * 파일럿 여부
	 */
	private String pilot_Yn = Constants.BLANK;
	/**
	 * 작업완료 여부
	 */
	private String mod_end_yn = Constants.BLANK;

	/**
	 * 제작부서 코드
	 */
	private String prdt_dept_cd = Constants.BLANK;
	
	/**
	 * 제작부서명
	 */
	private String prdt_dept_nm = Constants.BLANK;
	/**
	 * 외주제작코드
	 */
	private String prdt_in_outs_cd = Constants.BLANK;
		
	/**
	 * 촬영지
	 */
	private String cmr_place = Constants.BLANK;
		
	/**
	 * 저작권 형태 코드
	 */
	private String cprt_type = Constants.BLANK;
		
	/**
	 * 저작권 형태 설명
	 */
	private String cprtr_nm = Constants.BLANK;
	
	/**
	 * 검색대상 구분
	 */
	private String gubun = Constants.BLANK;   //일괄수정 조회시 검색 대상 콤보
	
	/**
	 * 페이지
	 */
	private int page;
	
	/**
	 * 사용여부
	 */
	private String use_yn = Constants.BLANK;
	
	/**
	 * 콘텐츠구분명
	 */
	private String ct_cla_nm = Constants.BLANK;
	/**
	 * 프로그램 코드
	 */
	private String pgm_cd = Constants.BLANK;
	/**
	 * 대분류장르명
	 */
	private String ctgr_l_nm = Constants.BLANK;
	
	/**
	 * 중분류장르명
	 */
	private String ctgr_m_nm = Constants.BLANK;;
	
	/**
	 * 소분류장르명
	 */
	private String ctgr_s_nm = Constants.BLANK;
	
	/**
	 * 화질명
	 */
	private String vd_qlty_nm = Constants.BLANK;
	
	/**
	 * 방송길이
	 */
	private String brd_leng = Constants.BLANK;
	
	/**
	 * 보존기간코드
	 */
	private String rsv_prd_cd = Constants.BLANK;
	
	/**
	 * 저작권형태설명
	 */
	private String cprt_type_dsc = Constants.BLANK;
	/**
	 * 제작상태코드
	 */
	private String data_stat_cd = Constants.BLANK;
	/**
	 * 제작상태명
	 */
	private String data_stat_nm = Constants.BLANK;
	/**
	 * 등록일시
	 */
	private String reg_dt = Constants.BLANK;
	/**
	 * 등록자
	 */
	private String reg_id = Constants.BLANK;
	/**
	 * 미디어 id
	 */
	private String media_id = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brd_dd = Constants.BLANK;
	/**
	 * CLF_CD
	 */
	private String clf_cd = Constants.BLANK;
	/**
	 * SCL_CD
	 */
	private String scl_cd = Constants.BLANK;
	
	/**
	 * 부제
	 */
	private String sub_ttl = Constants.BLANK;
	/**
	 * 검색조건
	 */
	private String SRCH_TYPE = Constants.BLANK;
	/**
	 * 총조회건수
	 */
	private int totalpage;

	/**
	 * 제작부서
	 */
	private String dept_nm = Constants.BLANK;
	
	/**
	 * 검색어
	 */
	private String search_word = Constants.BLANK;
	

	/**
	 * 녹음방식코드
	 */
	private String record_type_cd = Constants.BLANK;
	

	/**
	 * 검수상내역	 */
	private String award_hstr = Constants.BLANK;
	
	/**
	 * 외주제작사	 */
	private String org_prdr_nm = Constants.BLANK;
	
	
	//2012.5.10
	/**
	 * 회사	 */
	private String cocd = Constants.BLANK;
	
	/**
	 * 계열사	 */
	private String chennel = Constants.BLANK;
	
	/**
	 * 아카이브 경로	 */
	private String archive_path = Constants.BLANK;
	
	
	
	
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

	public String getArchive_path() {
		return archive_path;
	}

	public void setArchive_path(String archivePath) {
		archive_path = archivePath;
	}

	public String getEpis_No() {
		return epis_No;
	}

	public void setEpis_No(String episNo) {
		epis_No = episNo;
	}

	public String getOrg_prdr_nm() {
		return org_prdr_nm;
	}

	public void setOrg_prdr_nm(String orgPrdrNm) {
		org_prdr_nm = orgPrdrNm;
	}

	public String getRecord_type_cd() {
		return record_type_cd;
	}

	public void setRecord_type_cd(String recordTypeCd) {
		record_type_cd = recordTypeCd;
	}

	public String getAward_hstr() {
		return award_hstr;
	}

	public void setAward_hstr(String awardHstr) {
		award_hstr = awardHstr;
	}

	public String getSearch_word() {
		return search_word;
	}

	public void setSearch_word(String searchWord) {
		search_word = searchWord;
	}

	public String getDept_nm() {
		return dept_nm;
	}

	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public String getSRCH_TYPE() {
		return SRCH_TYPE;
	}

	public void setSRCH_TYPE(String sRCHTYPE) {
		SRCH_TYPE = sRCHTYPE;
	}

	public String getPgm_cd() {
		return pgm_cd;
	}

	public void setPgm_cd(String pgmCd) {
		pgm_cd = pgmCd;
	}

	public String getSub_ttl() {
		return sub_ttl;
	}

	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
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

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}

	public String getData_stat_nm() {
		return data_stat_nm;
	}

	public void setData_stat_nm(String dataStatNm) {
		data_stat_nm = dataStatNm;
	}

	public String getData_stat_cd() {
		return data_stat_cd;
	}

	public void setData_stat_cd(String dataStatCd) {
		data_stat_cd = dataStatCd;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String regId) {
		reg_id = regId;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public String getRsv_prd_cd() {
		return rsv_prd_cd;
	}

	public void setRsv_prd_cd(String rsv_prd_cd) {
		this.rsv_prd_cd = rsv_prd_cd;
	}

	public String getCprt_type_dsc() {
		return cprt_type_dsc;
	}

	public void setCprt_type_dsc(String cprt_type_dsc) {
		this.cprt_type_dsc = cprt_type_dsc;
	}

	public String getBrd_leng() {
		return brd_leng;
	}

	public void setBrd_leng(String brd_leng) {
		this.brd_leng = brd_leng;
	}

	public String getCt_cla_nm() {
		return ct_cla_nm;
	}

	public void setCt_cla_nm(String ct_cla_nm) {
		this.ct_cla_nm = ct_cla_nm;
	}

	public String getCtgr_l_nm() {
		return ctgr_l_nm;
	}

	public void setCtgr_l_nm(String ctgr_l_nm) {
		this.ctgr_l_nm = ctgr_l_nm;
	}

	public String getCtgr_m_nm() {
		return ctgr_m_nm;
	}

	public void setCtgr_m_nm(String ctgr_m_nm) {
		this.ctgr_m_nm = ctgr_m_nm;
	}

	public String getCtgr_s_nm() {
		return ctgr_s_nm;
	}

	public void setCtgr_s_nm(String ctgr_s_nm) {
		this.ctgr_s_nm = ctgr_s_nm;
	}

	public String getVd_qlty_nm() {
		return vd_qlty_nm;
	}

	public void setVd_qlty_nm(String vd_qlty_nm) {
		this.vd_qlty_nm = vd_qlty_nm;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String useYn) {
		use_yn = useYn;
	}

	public String getParents_cd() {
		return parents_cd;
	}

	public void setParents_cd(String parentsCd) {
		parents_cd = parentsCd;
	}

	/**
	 * 부모코드
	 */
	private String parents_cd = Constants.BLANK;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getMod_end_yn() {
		return mod_end_yn;
	}

	public void setMod_end_yn(String mod_end_yn) {
		this.mod_end_yn = mod_end_yn;
	}

	public String getAward_Hstr() {
		return award_Hstr;
	}

	public void setAward_Hstr(String award_Hstr) {
		this.award_Hstr = award_Hstr;
	}

	public String getPilot_Yn() {
		return pilot_Yn;
	}

	public void setPilot_Yn(String pilot_Yn) {
		this.pilot_Yn = pilot_Yn;
	}

	public String getPrdt_dept_nm() {
		return prdt_dept_nm;
	}

	public void setPrdt_dept_nm(String prdt_dept_nm) {
		this.prdt_dept_nm = prdt_dept_nm;
	}

	public String getPrd_Dept_Nm() {
		return prd_Dept_Nm;
	}

	public void setPrd_Dept_Nm(String prd_Dept_Nm) {
		this.prd_Dept_Nm = prd_Dept_Nm;
	}

	public String getSchd_Pgm_Nm() {
		return schd_Pgm_Nm;
	}

	public void setSchd_Pgm_Nm(String schd_Pgm_Nm) {
		this.schd_Pgm_Nm = schd_Pgm_Nm;
	}

	public long getPgmId() {
		return pgmId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public long getMasterId() {
		return masterId;
	}

	public void setPgmId(long pgmId) {
		this.pgmId = pgmId;
	}	
	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	
	public String getMediaCd() {
		return mediaCd;
	}

	public void setMediaCd(String mediaCd) {
		this.mediaCd = mediaCd;
	}
	
	public String getChanCd() {
		return chanCd;
	}

	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}
	
	public String getCtgrLCd() {
		return ctgrLCd;
	}

	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	
	public String getCtgrMCd() {
		return ctgrMCd;
	}

	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	
	public String getCtgrSCd() {
		return ctgrSCd;
	}

	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	
	public String getBrdBgnDd() {
		return brdBgnDd;
	}

	public void setBrdBgnDd(String brdBgnDd) {
		this.brdBgnDd = brdBgnDd;		
	}
	
	public String getBrdEndDd() {
		return brdEndDd;
	}

	public void setBrdEndDd(String brdEndDd) {
		this.brdEndDd = brdEndDd;		
	}
	
	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;		
	}
	public String getPgmEpis() {
		return pgmEpis;
	}
	public void setPgmEpis(String pgmEpis) {
		this.pgmEpis = pgmEpis;
	}
	public int getEpisNo() {
		return episNo;
	}
	public void setEpisNo(int episNo) {
		this.episNo = episNo;
	}

	public String getPrdt_dept_cd() {
		return prdt_dept_cd;
	}

	public void setPrdt_dept_cd(String prdt_dept_cd) {
		this.prdt_dept_cd = prdt_dept_cd;
	}

	public String getPrdt_in_outs_cd() {
		return prdt_in_outs_cd;
	}

	public void setPrdt_in_outs_cd(String prdt_in_outs_cd) {
		this.prdt_in_outs_cd = prdt_in_outs_cd;
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

	public String getCprtr_nm() {
		return cprtr_nm;
	}

	public void setCprtr_nm(String cprtr_nm) {
		this.cprtr_nm = cprtr_nm;
	}

}

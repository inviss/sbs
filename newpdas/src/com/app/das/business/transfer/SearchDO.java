package com.app.das.business.transfer;

import java.util.ArrayList;
/**
 * 검색 상세 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class SearchDO {

	/** 프로그램 ID */
	private String pgm_id = "";
	
	/** 프로그램 명 */
	private String pgm_nm = "";
	
	/** 대분류 코드 */
	private String ctgr_l_cd = "";
	
	/** 중분류 코드 */
	private String ctgr_m_cd = "";
	
	/** 소분류 코드 */
	private String ctgr_s_cd = "";
	
	/** 마스터 ID */
	private String master_id = "";
	
	/** 타이틀 */
	private String title = "";
	
	/** 회차 */
	private String epis_no = "";
	
	/** 시놉시스 */
	private String snps = "";
	
	/** 키워드 */
	private String key_words = "";
	
	/** 연출자 명 */
	private String drt_nm = "";
	
	/** 프로듀서 명 */
	private String producer_nm = "";
	
	/** 제작 구분 코드 */
	private String prdt_in_outs_cd = "";
	
	/** 제작 부서 명칭 */
	private String prdt_dept_nm = "";
	
	/** 작가명 */
	private String writer_nm = "";
	
	/** 진행자 명 */
	private String mc_nm = "";
	
	/** 출연자 명 */
	private String cast_nm = "";
	
	/** 촬영 장소 */
	private String cmr_place = "";
	
	/** 방송일자 */
	private String brd_dd = "";
	
	/** 방송 길이 */
	private String brd_leng = "";
	
	/** 등록 일시 */
	private String reg_dt = "";
	
	/** 아카이버 명 */
	private String sec_arch_nm = "";
	
	/** 특이사항 */
	private String spc_info = "";
	
	/** 촬영 감독명 */ 
	private String cmr_drt_nm = "";
	
	/** 저작권 형태 설명 */
	private String cprt_type_dsc = "";
	
	/** 시청 등급 */
	private String view_gr_cd = "";
	
	/** 심의 결과 코드 */
	private String dlbr_cd = "";
	
	/** 수상 내역 */
	private String award_hstr = "";
	
	/** 대표화면 키프레임 번호 */
	private String rpimg_kfrm_seq = "";
	
	/** 대표화면 콘텐츠 */
	private String rpimg_ct_id = "";
	
	/** 부본보관 / 클린보관 */
	private String  keep_cd = "";
	
	/** 음악 정보 */
	private String  music_info = "";
	
	/** 편성명 */
	private String  schd_pgm_nm = "";
	
	/** 최종방송여부 */
	private String final_brd_yn = "";
	
	/** 파일롯 여부 */
	private String pilot_yn = "";
	
	/** 부제 */
	private String sub_ttl = "";
	
	/** 요일 */
	private String day = "";
	
	/** 화질 코드 */
	private String vd_qlty = "";
	
	/** 종횡비 코드 */
	private String asp_rto_cd = "";
	
	/** 코너 ID */
	private String cn_id = "";
	
	/** 주석 정보 ID */
	private String annot_id = "";
	
	/** 대표 화면 */
	private String rp_img = "";
	
	/** 키프레임 일련번호 */
	private String kfrm_seq = "";
	
	/** 키프레임 경로 */
	private String kfrm_path = "";
	
	/** 주석 구분 코드 */
	private String annot_clf_cd = "";
	
	/** 코너 정보 */
	private String cn_info = "";
	
	/** 코너 명 */
	private String cn_nm = "";

	/** 정렬 컬럼 */
	private String sortColumn = "";
	
	/** 정렬 방식 (오름차순(on) / 내림차순(off))*/
	private String asc = "";
	
	/** 주석 구분 내용 */
	private String annot_clf_cont = "";
	
	
	
	
	// 상세검색 
	/** 시작 생성일 */
	private String startReg_dt = "";
	
	/** 종료 생성일 */
	private String endReg_dt = "";
	
	/** 대분류 장르 */
	private String largeJanre = "";
	
	/** 중분류 장르 */
	private String mediumJanre = "";
	
	/** 소분류 장르 */
	private String smallJanre = "";
	
	/** 명장면 명대사 */
	private String cont = "";
	
	/** 저작권형태 */
	private String cprt_type = "";
	
	/** 저작권자명 */
	private String cprtr_nm = "";
	
	/** 영상 ID */
	private String ct_id = "";
	
	/** 시청률 */
	private String pgm_rate = "";
	

	
	
	/** 검색 목록 */
	private ArrayList list = null;
	
	
	
	
	
	/** 현재 페이지 */
	private String nowPage = "";
	
	/** 출력시킬 목록 수 */
	private String pageCount = "";
	
	/** 검색어 */
	private String searchKey = "";
	
	/** 검색어 목록 */
	private String searchKeyList = "";
	
	/** 기사 상세 검색 연산자 */
	private String operatorList = "";

	/** 검색 인덱스 명 */
	private String indexName = "";
	
	/** 검색된 총 갯수 */
	private String count = "";
	
	/** 시작 날짜 */
	private String startdate = "";
	
	/** 종료 날짜 */
	private String enddate = "";
	
	
	
	
	
	
	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getAward_hstr() {
		return award_hstr;
	}

	public void setAward_hstr(String award_hstr) {
		this.award_hstr = award_hstr;
	}

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brd_dd) {
		this.brd_dd = brd_dd;
	}

	public String getBrd_leng() {
		return brd_leng;
	}

	public void setBrd_leng(String brd_leng) {
		this.brd_leng = brd_leng;
	}

	public String getCast_nm() {
		return cast_nm;
	}

	public void setCast_nm(String cast_nm) {
		this.cast_nm = cast_nm;
	}

	public String getCmr_drt_nm() {
		return cmr_drt_nm;
	}

	public void setCmr_drt_nm(String cmr_drt_nm) {
		this.cmr_drt_nm = cmr_drt_nm;
	}

	public String getCmr_place() {
		return cmr_place;
	}

	public void setCmr_place(String cmr_place) {
		this.cmr_place = cmr_place;
	}

	public String getCprt_type_dsc() {
		return cprt_type_dsc;
	}

	public void setCprt_type_dsc(String cprt_type_dsc) {
		this.cprt_type_dsc = cprt_type_dsc;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDlbr_cd() {
		return dlbr_cd;
	}

	public void setDlbr_cd(String dlbr_cd) {
		this.dlbr_cd = dlbr_cd;
	}

	public String getDrt_nm() {
		return drt_nm;
	}

	public void setDrt_nm(String drt_nm) {
		this.drt_nm = drt_nm;
	}

	public String getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(String epis_no) {
		this.epis_no = epis_no;
	}

	public String getFinal_brd_yn() {
		return final_brd_yn;
	}

	public void setFinal_brd_yn(String final_brd_yn) {
		this.final_brd_yn = final_brd_yn;
	}

	public String getKeep_cd() {
		return keep_cd;
	}

	public void setKeep_cd(String keep_cd) {
		this.keep_cd = keep_cd;
	}

	public String getKey_words() {
		return key_words;
	}

	public void setKey_words(String key_words) {
		this.key_words = key_words;
	}

	public String getMaster_id() {
		return master_id;
	}

	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}

	public String getMc_nm() {
		return mc_nm;
	}

	public void setMc_nm(String mc_nm) {
		this.mc_nm = mc_nm;
	}

	public String getMusic_info() {
		return music_info;
	}

	public void setMusic_info(String music_info) {
		this.music_info = music_info;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getOperatorList() {
		return operatorList;
	}

	public void setOperatorList(String operatorList) {
		this.operatorList = operatorList;
	}

	public String getPgm_id() {
		return pgm_id;
	}

	public void setPgm_id(String pgm_id) {
		this.pgm_id = pgm_id;
	}

	public String getPgm_nm() {
		return pgm_nm;
	}

	public void setPgm_nm(String pgm_nm) {
		this.pgm_nm = pgm_nm;
	}

	public String getPilot_yn() {
		return pilot_yn;
	}

	public void setPilot_yn(String pilot_yn) {
		this.pilot_yn = pilot_yn;
	}

	public String getPrdt_dept_nm() {
		return prdt_dept_nm;
	}

	public void setPrdt_dept_nm(String prdt_dept_nm) {
		this.prdt_dept_nm = prdt_dept_nm;
	}

	public String getPrdt_in_outs_cd() {
		return prdt_in_outs_cd;
	}

	public void setPrdt_in_outs_cd(String prdt_in_outs_cd) {
		this.prdt_in_outs_cd = prdt_in_outs_cd;
	}

	public String getProducer_nm() {
		return producer_nm;
	}

	public void setProducer_nm(String producer_nm) {
		this.producer_nm = producer_nm;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String reg_dt) {
		this.reg_dt = reg_dt;
	}

	public String getRpimg_ct_id() {
		return rpimg_ct_id;
	}

	public void setRpimg_ct_id(String rpimg_ct_id) {
		this.rpimg_ct_id = rpimg_ct_id;
	}

	public String getRpimg_kfrm_seq() {
		return rpimg_kfrm_seq;
	}

	public void setRpimg_kfrm_seq(String rpimg_kfrm_seq) {
		this.rpimg_kfrm_seq = rpimg_kfrm_seq;
	}

	public String getSchd_pgm_nm() {
		return schd_pgm_nm;
	}

	public void setSchd_pgm_nm(String schd_pgm_nm) {
		this.schd_pgm_nm = schd_pgm_nm;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchKeyList() {
		return searchKeyList;
	}

	public void setSearchKeyList(String searchKeyList) {
		this.searchKeyList = searchKeyList;
	}

	public String getSec_arch_nm() {
		return sec_arch_nm;
	}

	public void setSec_arch_nm(String sec_arch_nm) {
		this.sec_arch_nm = sec_arch_nm;
	}

	public String getSnps() {
		return snps;
	}

	public void setSnps(String snps) {
		this.snps = snps;
	}

	public String getSpc_info() {
		return spc_info;
	}

	public void setSpc_info(String spc_info) {
		this.spc_info = spc_info;
	}

	public String getSub_ttl() {
		return sub_ttl;
	}

	public void setSub_ttl(String sub_ttl) {
		this.sub_ttl = sub_ttl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getView_gr_cd() {
		return view_gr_cd;
	}

	public void setView_gr_cd(String view_gr_cd) {
		this.view_gr_cd = view_gr_cd;
	}

	public String getWriter_nm() {
		return writer_nm;
	}

	public void setWriter_nm(String writer_nm) {
		this.writer_nm = writer_nm;
	}

	public ArrayList getList() {
		return list;
	}

	public void setList(ArrayList list) {
		this.list = list;
	}

	public String getAsp_rto_cd() {
		return asp_rto_cd;
	}

	public void setAsp_rto_cd(String asp_rto_cd) {
		this.asp_rto_cd = asp_rto_cd;
	}

	public String getVd_qlty() {
		return vd_qlty;
	}

	public void setVd_qlty(String vd_qlty) {
		this.vd_qlty = vd_qlty;
	}

	public String getCn_id() {
		return cn_id;
	}

	public void setCn_id(String cn_id) {
		this.cn_id = cn_id;
	}

	public String getAnnot_id() {
		return annot_id;
	}

	public void setAnnot_id(String annot_id) {
		this.annot_id = annot_id;
	}

	public String getRp_img() {
		return rp_img;
	}

	public void setRp_img(String rp_img) {
		this.rp_img = rp_img;
	}

	public String getKfrm_seq() {
		return kfrm_seq;
	}

	public void setKfrm_seq(String kfrm_seq) {
		this.kfrm_seq = kfrm_seq;
	}

	public String getKfrm_path() {
		return kfrm_path;
	}

	public void setKfrm_path(String kfrm_path) {
		this.kfrm_path = kfrm_path;
	}

	public String getAnnot_clf_cd() {
		return annot_clf_cd;
	}

	public void setAnnot_clf_cd(String annot_clf_cd) {
		this.annot_clf_cd = annot_clf_cd;
	}

	public String getCn_info() {
		return cn_info;
	}

	public void setCn_info(String cn_info) {
		this.cn_info = cn_info;
	}

	public String getCn_nm() {
		return cn_nm;
	}

	public void setCn_nm(String cn_nm) {
		this.cn_nm = cn_nm;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

	public String getStartReg_dt() {
		return startReg_dt;
	}

	public void setStartReg_dt(String startReg_dt) {
		this.startReg_dt = startReg_dt;
	}

	public String getEndReg_dt() {
		return endReg_dt;
	}

	public void setEndReg_dt(String endReg_dt) {
		this.endReg_dt = endReg_dt;
	}

	public String getLargeJanre() {
		return largeJanre;
	}

	public void setLargeJanre(String largeJanre) {
		this.largeJanre = largeJanre;
	}

	public String getMediumJanre() {
		return mediumJanre;
	}

	public void setMediumJanre(String mediumJanre) {
		this.mediumJanre = mediumJanre;
	}

	public String getSmallJanre() {
		return smallJanre;
	}

	public void setSmallJanre(String smallJanre) {
		this.smallJanre = smallJanre;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
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

	public String getCt_id() {
		return ct_id;
	}

	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}

	public String getPgm_rate() {
		return pgm_rate;
	}

	public void setPgm_rate(String pgm_rate) {
		this.pgm_rate = pgm_rate;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}

	public String getAnnot_clf_cont() {
		return annot_clf_cont;
	}

	public void setAnnot_clf_cont(String annot_clf_cont) {
		this.annot_clf_cont = annot_clf_cont;
	}
	
	
	
	
	
}

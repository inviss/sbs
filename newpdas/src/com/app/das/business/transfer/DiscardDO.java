package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 폐기 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class DiscardDO extends DTO 
{
	/**
	 * 순번
	 */
	private int seq;
	
	/**
	 * 대분류
	 */
	private String ctgr_l_cd = Constants.BLANK;
	
	/**
	 * 프로그램명
	 */
	private String title= Constants.BLANK;
	
	/**
	 * 방송일
	 */
	private String brd_dd= Constants.BLANK;
	
	/**
	 * 보존기간 종료일
	 */
	private String rsv_prd_end_dd= Constants.BLANK;
	
	/**
	 * 보존기간 종료일(검색조건)
	 */
	private String rsv_prd_start_dd= Constants.BLANK;
	
	public String getRsv_prd_start_dd() {
		return rsv_prd_start_dd;
	}

	public void setRsv_prd_start_dd(String rsvPrdStartDd) {
		rsv_prd_start_dd = rsvPrdStartDd;
	}

	/**
	 * 보존기간 코드
	 */
	private String rsv_prd_cd= Constants.BLANK;
	
	/**
	 * 방송길이
	 */
	private String brd_len= Constants.BLANK;
	
	/**
	 * 폐기상태
	 */
	private String disuse_sta= Constants.BLANK;
	
	/**
	 * 폐기 사유
	 */
	private String disuse_cont= Constants.BLANK;
	
	/**
	 * 연장사유
	 */
	private String use_cont= Constants.BLANK;
	
	/**
	 * 등록일
	 */
	private String reg_dt= Constants.BLANK;
	
	/**
	 * 폐기종료일
	 */
	private String disuse_end_dd= Constants.BLANK;
	/**
	 * 미디어id
	 */
	private String media_id= Constants.BLANK;
	/**
	 * 이용횟수
	 */
	private String use_count= Constants.BLANK;
	
	/**
	 * 페이지
	 */
	private int page;
	
	/**
	 * 구분코드
	 */
	private String disuse_yn= Constants.BLANK;
	
	/**
	 * 이용횟수 플래그
	 */
	private String use_flag= Constants.BLANK;
	
	/**
	 * 이용횟수 시작
	 */
	private String from_use= Constants.BLANK;
	/**
	 * 이용횟수 종료
	 */
	private String to_use= Constants.BLANK;
	/**
	 * 부제
	 */
	private String sub_ttl= Constants.BLANK;
	
	/**
	 * 폐기상태
	 */
	private String status= Constants.BLANK;
	
	/**
	 * 전체페이지
	 */
	private int totalcount;
	private int brd_leng_sum;
	private boolean bQueryResultCount = false;
	private boolean QueryResultCount = false;
	
	/**
	 * 촬영일
	 */
	private String fm_dt= Constants.BLANK;
	
	/**
	 * 아카이브 등록여부
	 */
	private String arch_reg_dd= Constants.BLANK;
	
	
	
	/**
	 * 회차
	 */
	private String epis_no= Constants.BLANK;
	

	/**
	 * 방송시작일
	 */
	private String start_brd_dd= Constants.BLANK;
	
	/**
	 * 방송종료일
	 */
	private String end_brd_dd= Constants.BLANK;
	
	
	/**
	 * 폐기 신청자
	 */
	private String reg_id= Constants.BLANK;
	
	/**
	 * 폐기 신청자명
	 */
	private String reg_nm= Constants.BLANK;
	
	
	//2012.05.10
	
	/**
	 * 회사
	 */
	private String cocd= Constants.BLANK;
	
	/**
	 * 채널
	 */
	private String chennel= Constants.BLANK;
	
	/**
	 * 아카이브경로
	 */
	private String archive_path= Constants.BLANK;
	
	
	
	
	
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

	public String getReg_nm() {
		return reg_nm;
	}

	public void setReg_nm(String regNm) {
		reg_nm = regNm;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String regId) {
		reg_id = regId;
	}

	public String getStart_brd_dd() {
		return start_brd_dd;
	}

	public void setStart_brd_dd(String startBrdDd) {
		start_brd_dd = startBrdDd;
	}

	public String getEnd_brd_dd() {
		return end_brd_dd;
	}

	public void setEnd_brd_dd(String endBrdDd) {
		end_brd_dd = endBrdDd;
	}

	public String getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}

	public String getFm_dt() {
		return fm_dt;
	}

	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// 시작 위치
	private long nStartPos = 0;
	
	// 끝 위치
	private long nEndPos = 0;
	
	private int  rowPerPage=0;

long masterId=0;
	

	public long getMasterId() {
	return masterId;
}

public void setMasterId(long masterId) {
	this.masterId = masterId;
}

	public int getRowPerPage() {
		return rowPerPage;
	}

	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}

	public long getStartPos() {
		return nStartPos;
	}

	public void setStartPos(long nStartPos) {
		this.nStartPos = nStartPos;
	}

	public long getEndPos() {
		return nEndPos;
	}

	public void setEndPos(long nEndPos) {
		this.nEndPos = nEndPos;
	}





	public String getArch_reg_dd() {
		return arch_reg_dd;
	}

	public void setArch_reg_dd(String archRegDd) {
		arch_reg_dd = archRegDd;
	}

	public int getBrd_leng_sum() {
		return brd_leng_sum;
	}

	public void setBrd_leng_sum(int brdLengSum) {
		brd_leng_sum = brdLengSum;
	}

	public boolean getQueryResultCount() {
		return bQueryResultCount;
	}

	public void setQueryResultCount(boolean bQueryResultCount) {
		bQueryResultCount = bQueryResultCount;
	}

	public String getSub_ttl() {
		return sub_ttl;
	}

	public void setSub_ttl(String subTtl) {
		sub_ttl = subTtl;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String getFrom_use() {
		return from_use;
	}

	public void setFrom_use(String fromUse) {
		from_use = fromUse;
	}

	public String getTo_use() {
		return to_use;
	}

	public void setTo_use(String toUse) {
		to_use = toUse;
	}

	public String getUse_flag() {
		return use_flag;
	}

	public void setUse_flag(String useFlag) {
		use_flag = useFlag;
	}

	public String getDisuse_yn() {
		return disuse_yn;
	}

	public void setDisuse_yn(String disuseYn) {
		disuse_yn = disuseYn;
	}

	public int getPage() {
		return page;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public String getUse_count() {
		return use_count;
	}

	public void setUse_count(String useCount) {
		use_count = useCount;
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

	
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}

	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
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

	public String getRsv_prd_end_dd() {
		return rsv_prd_end_dd;
	}

	public void setRsv_prd_end_dd(String rsvPrdEndDd) {
		rsv_prd_end_dd = rsvPrdEndDd;
	}

	public String getRsv_prd_cd() {
		return rsv_prd_cd;
	}

	public void setRsv_prd_cd(String rsvPrdCd) {
		rsv_prd_cd = rsvPrdCd;
	}

	public String getBrd_len() {
		return brd_len;
	}

	public void setBrd_len(String brdLen) {
		brd_len = brdLen;
	}

	public String getDisuse_sta() {
		return disuse_sta;
	}

	public void setDisuse_sta(String disuseSta) {
		disuse_sta = disuseSta;
	}

	public String getDisuse_cont() {
		return disuse_cont;
	}

	public void setDisuse_cont(String disuseCont) {
		disuse_cont = disuseCont;
	}

	public String getUse_cont() {
		return use_cont;
	}

	public void setUse_cont(String useCont) {
		use_cont = useCont;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}

	public String getDisuse_end_dd() {
		return disuse_end_dd;
	}

	public void setDisuse_end_dd(String disuseEndDd) {
		disuse_end_dd = disuseEndDd;
	}

	public String getPre_rsv_prd_cd() {
		return pre_rsv_prd_cd;
	}

	public void setPre_rsv_prd_cd(String preRsvPrdCd) {
		pre_rsv_prd_cd = preRsvPrdCd;
	}

	public int getMaster_id() {
		return master_id;
	}

	public void setMaster_id(int masterId) {
		master_id = masterId;
	}

	/**
	 * 이전 보존기간일
	 */
	private String pre_rsv_prd_cd= Constants.BLANK;
	
	/**
	 * 마스터 id
	 */
	private int master_id;
	
	
}














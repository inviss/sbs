package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 요청인별 다운로드 승인요청 관련 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class CartItemDO extends DTO 
{
	/**
	 * 카트번호
	 */
	private long cartNo;
	/**
	 * 회사번호
	 */
	private String cocd	= Constants.BLANK;;
	/**
	 * 카트순번
	 */
	private int cartseq;
	/**
	 * 요청자명
	 */
	private String reqNm            = Constants.BLANK;
	/**
	 * 요청자id
	 */
	private String req_id            = Constants.BLANK;
	
	
	/**
	 * 미디어 id
	 */
	private String media_id            = Constants.BLANK;
	/**
	 * 다운로드 제목
	 */
	private String downSubj         = Constants.BLANK;
	/**
	 * 사용제한건수
	 */
	private int useLimitCount =0;
	/**
	 * 승인내용
	 */
	private String appCont          = Constants.BLANK;
	/**
	 * 승인여부(승인='001', 거부='002')
	 */
	private String useLimitFlag	= Constants.BLANK;
	/**
	 * 화질
	 */
	private String screenQuality = Constants.BLANK;
	/**
	 * 종횡비
	 */
	private String lengthBreadthRate = Constants.BLANK;
	/**
	 * 요청일
	 */
	private String reqDT = Constants.BLANK;
	/**
	 * 화질명
	 */
	private String vd_qlty_nm = Constants.BLANK;
	/**
	 * 종횡비명
	 */
	private String asp_rto_nm = Constants.BLANK;
	/**
	 * 다운로드 구분 (1:전체,2:TAPE-OUT,3:TAPE-OUT 외)    
	 */
	private String down_gubun = Constants.BLANK;
	/**
	 * 다운로드 상태(1:대기, 2:승인, 3:다운로드 완료)
	 */
	private String down_status = Constants.BLANK;
	/**
	 * 일자 ( 1: 다운로드 요청 일자, 2: 다운로드 완료일자)
	 */
	private String down_day = Constants.BLANK;
	/**
	 * 시작일자
	 */
	private String fromdate = Constants.BLANK;
	/**
	 * 완료일자
	 */
	private String enddate = Constants.BLANK;
	/**
	 * 프로그램명
	 */
	private String title = Constants.BLANK;
	/**
	 * 비직원 신청여부(Y:예 , N:아니오)
	 */
	private String out_user = Constants.BLANK;
	/**
	 * 계열사 구분
	 */
	private String company_gubun = Constants.BLANK;
	
	/**
	 * 다운로드 구분명
	 */
	private String down_gubun_nm = Constants.BLANK;
	/**
	 * 사용자 아이디
	 */
	private String userid = Constants.BLANK;
	
	
	/**
	 * 컨텐츠 ID
	 */
	private long ct_id;
	/**
	 * 컨텐츠  instantce ID
	 */
	private long cti_id;
	/**
	 * 시작점
	 */
	private String som = Constants.BLANK;
	
	/**
	 * 종료점
	 */
	private String eom = Constants.BLANK;
	/**
	 * 제한명
	 */
	private String rist_clf_nm = Constants.BLANK;
	/**
	 * 상태
	 */
	private String status = Constants.BLANK;
	
	
	/**
	 * 요청사유
	 */
	private String req_cont = Constants.BLANK;
	
	
	/**
	 * 요청사유
	 */
	private String down_stat = Constants.BLANK;
	
	
	
	/**
	 * 검색대상 플래그
	 */
	private String search_flag = Constants.BLANK;
	
	/**
	 * 회차
	 */
	private int epis_no;
	
	/**
	 * 회차
	 */
	private String episno= Constants.BLANK;
	/**
	 * 정직원, 비직원 구분
	 */
	private String uesr_type = Constants.BLANK;
	
	/**
	 * 콘텐츠 유형
	 */
	private String ct_cla = Constants.BLANK;
	
	/**
	 * 방송일
	 */
	private String brd_dd = Constants.BLANK;
	
	/**
	 * 촬영일
	 */
	private String fm_dt = Constants.BLANK;

	/**
	 * 대분류
	 */
	private String ctgr_l_cd = Constants.BLANK;


	/**
	 * 스토리지 경로
	 */
	private String storage = Constants.BLANK;

	
//2012.5.18 
	
	/**
	 * 채널코드
	 */
	private String chennel = Constants.BLANK;

	
	/**
	 * 회사명
	 */
	private String conm = Constants.BLANK;

	
	/**
	 * 스토리지명
	 */
	private String storagename = Constants.BLANK;

	/**
	 * 요청사유
	 */
	private String ct_cont = Constants.BLANK;

	/**
	 * 시스템구분
	 */
	private String system = Constants.BLANK;

	/**
	 * 마스터id
	 */
	private long master_id =0;

	
	
	//20121212 최효정과장님  MAIL에의거 수정
	/**
	 * 사용등급
	 */
	private String rist_clf_cd= Constants.BLANK;
	
	
	public String getRist_clf_cd() {
		return rist_clf_cd;
	}
	public void setRist_clf_cd(String ristClfCd) {
		rist_clf_cd = ristClfCd;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getCt_cont() {
		return ct_cont;
	}
	public void setCt_cont(String ctCont) {
		ct_cont = ctCont;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public String getConm() {
		return conm;
	}
	public void setConm(String conm) {
		this.conm = conm;
	}
	public String getChennel() {
		return chennel;
	}
	public void setChennel(String chennel) {
		this.chennel = chennel;
	}
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public String getEpisno() {
		return episno;
	}
	public void setEpisno(String episno) {
		this.episno = episno;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getFm_dt() {
		return fm_dt;
	}
	public void setFm_dt(String fmDt) {
		fm_dt = fmDt;
	}
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
	}
	public String getUesr_type() {
		return uesr_type;
	}
	public void setUesr_type(String uesrType) {
		uesr_type = uesrType;
	}
	public int getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(int episNo) {
		epis_no = episNo;
	}
	public String getDown_stat() {
		return down_stat;
	}
	public void setDown_stat(String downStat) {
		down_stat = downStat;
	}
	public String getReq_cont() {
		return req_cont;
	}
	public void setReq_cont(String reqCont) {
		req_cont = reqCont;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String reqId) {
		req_id = reqId;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getCartseq() {
		return cartseq;
	}
	public void setCartseq(int cartseq) {
		this.cartseq = cartseq;
	}
	public String getSearch_flag() {
		return search_flag;
	}
	public void setSearch_flag(String searchFlag) {
		search_flag = searchFlag;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getDown_gubun_nm() {
		return down_gubun_nm;
	}
	public void setDown_gubun_nm(String down_gubun_nm) {
		this.down_gubun_nm = down_gubun_nm;
	}
	public String getCompany_gubun() {
		return company_gubun;
	}
	public void setCompany_gubun(String company_gubun) {
		this.company_gubun = company_gubun;
	}
	public String getDown_gubun() {
		return down_gubun;
	}
	public void setDown_gubun(String down_gubun) {
		this.down_gubun = down_gubun;
	}
	public String getDown_status() {
		return down_status;
	}
	public void setDown_status(String down_status) {
		this.down_status = down_status;
	}
	public String getDown_day() {
		return down_day;
	}
	public void setDown_day(String down_day) {
		this.down_day = down_day;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOut_user() {
		return out_user;
	}
	public void setOut_user(String out_user) {
		this.out_user = out_user;
	}
	public String getVd_qlty_nm() {
		return vd_qlty_nm;
	}
	public void setVd_qlty_nm(String vd_qlty_nm) {
		this.vd_qlty_nm = vd_qlty_nm;
	}
	public String getAsp_rto_nm() {
		return asp_rto_nm;
	}
	public void setAsp_rto_nm(String asp_rto_nm) {
		this.asp_rto_nm = asp_rto_nm;
	}
	public String getReqDT() {
		return reqDT;
	}
	public void setReqDT(String reqDT) {
		this.reqDT = reqDT;
	}
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public long getCartNo() {
		return cartNo;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public void setCartNo(long cartNo) {
		this.cartNo = cartNo;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public int getUseLimitCount() {
		return useLimitCount;
	}
	public void setUseLimitCount(int useLimitCount) {
		this.useLimitCount = useLimitCount;
	}
	public String getUseLimitFlag() {
		return useLimitFlag;
	}
	public void setUseLimitFlag(String useLimitFlag) {
		this.useLimitFlag = useLimitFlag;
	}
	public String getLengthBreadthRate() {
		return lengthBreadthRate;
	}
	public void setLengthBreadthRate(String lengthBreadthRate) {
		this.lengthBreadthRate = lengthBreadthRate;
	}
	public String getScreenQuality() {
		return screenQuality;
	}
	public void setScreenQuality(String screenQuality) {
		this.screenQuality = screenQuality;
	}

	public long getCt_id() {
		return ct_id;
	}
	public void setCt_id(long ctId) {
		ct_id = ctId;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getRist_clf_nm() {
		return rist_clf_nm;
	}
	public void setRist_clf_nm(String ristClfNm) {
		rist_clf_nm = ristClfNm;
	}

	
	
}

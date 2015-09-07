package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;


/**
 * 다운로드 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class DownCartDO extends DTO 
{
	/**
	 * 카트번호
	 */
	private long cartNo;
	/**
	 * 영상id
	 */
	private long ct_id;
	/**
	 * 마스터id
	 */
	private long master_id;
	/**
	 * 영상인스턴스id
	 */
	private long cti_id;
	/**
	 * 카트번호
	 */
	private String cartNos= Constants.BLANK;
	/**
	 * 카트순번
	 */
	private long cartSeq;
	/**
	 * 카트순번
	 */
	private String cartSeqs= Constants.BLANK;
	/**
	 * 자료구분코드
	 */
	private String dataClfCd	= Constants.BLANK;
	/**
	 * 우선순위코드
	 */
	private String prioCd           = Constants.BLANK;
	/**
	 * 저장위치
	 */
	private String strgLoc          = Constants.BLANK;
	/**
	 * 사용제한포함여부
	 */
	private String ristYn           = Constants.BLANK;
	/**
	 * 승인내용
	 */
	private String appCont          = Constants.BLANK;
	/**
	 * 요청자ID
	 */
	private String reqUsrid         = Constants.BLANK;
	/**
	 * 요청자명
	 */
	private String reqNm            = Constants.BLANK;
	/**
	 * 요청일시
	 */
	private String reqDt            = Constants.BLANK;
	/**
	 * 다운로드일시
	 */
	private String downDt           = Constants.BLANK;
	/**
	 * 승인일자
	 */
	private String appDt            = Constants.BLANK;
	/**
	 * 다운로드제목
	 */
	private String downSubj         = Constants.BLANK;
	/**
	 * 보증인ID
	 */
	private String gaurantorId      = Constants.BLANK;
	/**
	 * 등록일시
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId           = Constants.BLANK;
	/**
	 * 수정일시
	 */
	private String modDt            = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId           = Constants.BLANK;
	/**
	 * 조직부서코드
	 */
	private String deptCd           = Constants.BLANK;
	/**
	 * 화질코드
	 */
	private String vdQlty           = Constants.BLANK;
	/**
	 * 종횡비코드
	 */
	private String aspRtoCd         = Constants.BLANK;
	/**
	 * 카트상태
	 */
	private String cartStat         = Constants.BLANK;
	
	/**
	 *다운로드 구분(001: PDS , 002:NDS , 003:데정팀 , 004:tape-out, 005:계열사 )
	 */
	private String down_gubun		= Constants.BLANK;
	
	/**
	 * 타시스템 저장(전송)위치
	 */
	private String out_strg_loc		= Constants.BLANK;
	
	/**
	 * 다운로드 카트 제목
	 */
	private String title = Constants.BLANK;
	/**
	 * 회사코드
	 */
	private String co_cd = Constants.BLANK;
	/**
	 * 본부코드
	 */
	private String seg_cd = Constants.BLANK;
	/**
	 * 풀 or 부분 다운로드
	 */
	private String full_yn = Constants.BLANK;
	
	//2011.01.28 추가 
	
	/**
	 * 파일경로 -> nodecation
	 */
	private String fl_path = Constants.BLANK;
	
	
	/**
	 * 카테고리 -> nodeid
	 */
	private String category = Constants.BLANK;
	
	
	/**
	 *  스토리지 명 -> cell name
	 */
	private String storagename = Constants.BLANK;
	
	/**
	 *  파일명
	 */
	private String file_nm = Constants.BLANK;
	/**
	 *  미디어id
	 */
	private String media_id = Constants.BLANK;
	
	
	/**
	 *  유저ID
	 */
	private String user_id = Constants.BLANK;
	
	
	/**
	 *  프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	
	/**
	 *  파일 크기
	 */
	private long fl_sz;
	/**
	 *  TC 코드
	 */
	private String tc_cd = Constants.BLANK;
	
	
	/**
	 *  사용등급 
	 */
	private String rist_clf_cd = Constants.BLANK;
	
	/**
	 * 아이템 총 영상길이
	 */
	private String duration= Constants.BLANK;
	
	/**
	 * 스토리지(고해상도,아카이브 요청)에 클립 존재 여부
	 */
	private String storage_yn = Constants.BLANK;
	
	/**
	 * 논리적 스토리지명
	 */
	private String logicaltree = Constants.BLANK;
	
	
	/**
	 * 물리적 스토리지명
	 */
	private String physicaltree = Constants.BLANK;
	
	/**
	 * 요청사유
	 */
	private String reg_cont = Constants.BLANK;
	

	/**
	 * 회차
	 */
	private String epis_no = Constants.BLANK;
	
	
	/**
	 * 계열사
	 */
	private String chennel = Constants.BLANK;
	
	/**
	 * ifcms용 url
	 */
	private String url = Constants.BLANK;
	
	
	
	
	public long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(long masterId) {
		master_id = masterId;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}

	public String getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}

	public String getReg_cont() {
		return reg_cont;
	}

	public void setReg_cont(String regCont) {
		reg_cont = regCont;
	}

	public String getLogicaltree() {
		return logicaltree;
	}

	public void setLogicaltree(String logicaltree) {
		this.logicaltree = logicaltree;
	}

	public String getPhysicaltree() {
		return physicaltree;
	}

	public void setPhysicaltree(String physicaltree) {
		this.physicaltree = physicaltree;
	}

	public String getStorage_yn() {
		return storage_yn;
	}

	public void setStorage_yn(String storage_yn) {
		this.storage_yn = storage_yn;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRist_clf_cd() {
		return rist_clf_cd;
	}

	public void setRist_clf_cd(String ristClfCd) {
		rist_clf_cd = ristClfCd;
	}

	public String getTc_cd() {
		return tc_cd;
	}

	public void setTc_cd(String tcCd) {
		tc_cd = tcCd;
	}

	public long getFl_sz() {
		return fl_sz;
	}

	public void setFl_sz(long flSz) {
		fl_sz = flSz;
	}

	public String getPgm_nm() {
		return pgm_nm;
	}

	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String userId) {
		user_id = userId;
	}

	public String getFile_nm() {
		if(file_nm==null)
			return "";
		return file_nm;
	}

	public void setFile_nm(String fileNm) {
		file_nm = fileNm;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public String getFl_path() {
		if(fl_path==null){
			return "";
		}
		return fl_path;
	}

	public void setFl_path(String flPath) {
		fl_path = flPath;
	}

	public String getCategory() {
		if(category==null){
			return "";
		}
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStoragename() {
		if(storagename==null){
			return "";
		}
		return storagename;
	}

	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}

	public String getFull_yn() {
		return full_yn;
	}

	public void setFull_yn(String fullYn) {
		full_yn = fullYn;
	}

	public String getCartNos() {
		return cartNos;
	}

	public void setCartNos(String cartNos) {
		this.cartNos = cartNos;
	}

	public String getCartSeqs() {
		return cartSeqs;
	}

	public void setCartSeqs(String cartSeqs) {
		this.cartSeqs = cartSeqs;
	}

	public long getCartSeq() {
		return cartSeq;
	}

	public void setCartSeq(long cartSeq) {
		this.cartSeq = cartSeq;
	}

	public String getTitle() {
		if(title==null)
			return "";
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDown_gubun() {
		return down_gubun;
	}

	public void setDown_gubun(String down_gubun) {
		this.down_gubun = down_gubun;
	}

	public String getOut_strg_loc() {
		if(out_strg_loc==null){
			return "";
		}
		return out_strg_loc;
	}

	public void setOut_strg_loc(String out_strg_loc) {
		this.out_strg_loc = out_strg_loc;
	}

	public String getAppCont() {
		return appCont;
	}

	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}

	public String getAppDt() {
		return appDt;
	}

	public void setAppDt(String appDt) {
		this.appDt = appDt;
	}

	public String getAspRtoCd() {
		return aspRtoCd;
	}

	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}

	public long getCartNo() {
		return cartNo;
	}

	public void setCartNo(long cartNo) {
		this.cartNo = cartNo;
	}

	public String getCartStat() {
		return cartStat;
	}

	public void setCartStat(String cartStat) {
		this.cartStat = cartStat;
	}

	public String getDataClfCd() {
		return dataClfCd;
	}

	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}

	public String getDeptCd() {
		return deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	public String getDownDt() {
		return downDt;
	}

	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}

	public String getDownSubj() {
		return downSubj;
	}

	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}

	public String getGaurantorId() {
		return gaurantorId;
	}

	public void setGaurantorId(String gaurantorId) {
		this.gaurantorId = gaurantorId;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public String getModrId() {
		return modrId;
	}

	public void setModrId(String modrId) {
		this.modrId = modrId;
	}

	public String getPrioCd() {
		return prioCd;
	}

	public void setPrioCd(String prioCd) {
		this.prioCd = prioCd;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getRegrId() {
		return regrId;
	}

	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}

	public String getReqDt() {
		return reqDt;
	}

	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}

	public String getReqNm() {
		return reqNm;
	}

	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}

	public String getReqUsrid() {
		return reqUsrid;
	}

	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}

	public String getRistYn() {
		return ristYn;
	}

	public void setRistYn(String ristYn) {
		this.ristYn = ristYn;
	}

	public String getStrgLoc() {
		return strgLoc;
	}

	public void setStrgLoc(String strgLoc) {
		this.strgLoc = strgLoc;
	}

	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getCo_cd() {
		return co_cd;
	}

	public void setCo_cd(String co_cd) {
		this.co_cd = co_cd;
	}

	public String getSeg_cd() {
		return seg_cd;
	}

	public void setSeg_cd(String seg_cd) {
		this.seg_cd = seg_cd;
	}

}

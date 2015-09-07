package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 작업현황 조회의 매체변환, 주조송출의 정보를 포함하고 있는 DataObejct
 * @author ysk523
 *
 */
public class WorkStatusDO extends DTO 
{	
	/**
	 * 순번
	 */
	private int serialNo;
	/**
	 * 콘테츠 건구
	 */
	private int contentCnt;
	/**
	 * 컨텐츠ID
	 */
	private String ctId             = Constants.BLANK;	
	/**
	 * 메타데이타 마스터 ID
	 */
	private String masterId             = Constants.BLANK;
	/**
	 * 컨텐츠인스탄스ID
	 */
	private String ctiId            = Constants.BLANK;
	/**
	 * 프로그램 제목
	 */
	private String pgmNm            = Constants.BLANK;
	/**
	 * 타이틀
	 */
	private String title            = Constants.BLANK;
	/**
	 * 구분명
	 */
	private String clfNm            = Constants.BLANK;
	/**
	 * 아카이브 등록일
	 */
	private String archRegDd            = Constants.BLANK;
	
	/**
	 * 회차
	 */
	private String episNo           = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 길이
	 */
	private String brdLeng          = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brdDd            = Constants.BLANK;
	/**
	 * 자료상태코드
	 */
	private String dataStatCd       = Constants.BLANK;
	/**
	 * 컨텐츠구분코드
	 */
	private String ctCla            = Constants.BLANK;
	/**
	 * 인제스트장비ID
	 */
	private String ingestEqId       = Constants.BLANK;
	/**
	 * 인제스트장비명
	 */
	private String eqNm             = Constants.BLANK;
	/**
	 * 인스턴스포멧
	 */
	private String ctiFmt           = Constants.BLANK;
	/**
	 * 인제스트완료일
	 */
	private String ingestCloseDd    = Constants.BLANK;
	/**
	 * 아카이브여부
	 */
	private String archSteYn        = Constants.BLANK;
	/**
	 * 청구번호 
	 */
	private String req_cd = Constants.BLANK;
	
	// 락플래그
	private String lock_stat_cd = Constants.BLANK;
	private String error_stat_cd = Constants.BLANK;
	private String modrid = Constants.BLANK;
	
	public String getReq_cd() {
		return req_cd;
	}
	public void setReq_cd(String req_cd) {
		this.req_cd = req_cd;
	}
	public String getArchSteYn() {
		return archSteYn;
	}
	public void setArchSteYn(String archSteYn) {
		this.archSteYn = archSteYn;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public String getCtiId() {
		return ctiId;
	}
	public void setCtiId(String ctiId) {
		this.ctiId = ctiId;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getEpisNo() {
		return episNo;
	}
	public void setEpisNo(String episNo) {
		this.episNo = episNo;
	}
	public String getEqNm() {
		return eqNm;
	}
	public void setEqNm(String eqNm) {
		this.eqNm = eqNm;
	}
	public String getIngestCloseDd() {
		return ingestCloseDd;
	}
	public void setIngestCloseDd(String ingestCloseDd) {
		this.ingestCloseDd = ingestCloseDd;
	}
	public String getIngestEqId() {
		return ingestEqId;
	}
	public void setIngestEqId(String ingestEqId) {
		this.ingestEqId = ingestEqId;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClfNm() {
		return clfNm;
	}
	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}
	public String getArchRegDd() {
		return archRegDd;
	}
	public void setArchRegDd(String archRegDd) {
		this.archRegDd = archRegDd;
	}
	public int getContentCnt() {
		return contentCnt;
	}
	public void setContentCnt(int contentCnt) {
		this.contentCnt = contentCnt;
	}
	
	public String getLockStatCd() {
		return lock_stat_cd;
	}
	public void setLockStatCd(String lock_stat_cd) {
		this.lock_stat_cd = lock_stat_cd;
	}
	public String getErrorStatCd() {
		return error_stat_cd;
	}
	public void setErrorStatCd(String error_stat_cd) {
		this.error_stat_cd = error_stat_cd;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	
}

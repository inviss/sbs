package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 아카이브연동정보 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author asura207
 *
 */
public class ArchiveInfoDO extends DTO 
{
	/** 
	 * 마스터 ID 
	 */
	private long master_id;         
	/** 
	 * 제목
	 */
	private String title  	= Constants.BLANK;
	/** 
	 * 미디어 id 
	 */       
	private String media_id  	= Constants.BLANK;
	/** 
	 * 아카이브 경로
	 */
	private String archive_path     = Constants.BLANK;
	/** 
	 * 요청 id 
	 */
	private String req_id              = Constants.BLANK;
	/** 
	 * 요청 명 
	 */
	private String   req_nm            = Constants.BLANK;
	/** 
	 * 요청일 
	 */
	private String req_dd              = Constants.BLANK;
	/** 
	 * 아카이브요청
	 */
	private String archive_yn= Constants.BLANK;
	/** 
	 *  고해상도
	 */
	private String high_qual= Constants.BLANK;
	/** 
	 * wmv
	 */
	private String wmv	 	= 	Constants.BLANK;
	/** 
	 * dtl
	 */
	private String dtl_yn	 	= 	Constants.BLANK;
	/** 
	 * 복본
	 */
	private String copy_cd	= 	Constants.BLANK;
	/** 
	 * 복본(구)
	 */
	private String old_copy_cd	= 	Constants.BLANK;
	/** 
	 * 대분류
	 */
	private String clf_cd	= 	Constants.BLANK;
	/** 
	 * 비고
	 */
	private String etc	= 	Constants.BLANK;
	/** 
	 * 방송/촬영일
	 */
	private String brd_dd	= 	Constants.BLANK;
	
	/** 
	 * 등록일
	 */
	private String reg_dt	= 	Constants.BLANK;
	
	/** 
	 * 회차
	 */
	private String epis_no	= 	Constants.BLANK;
	
	
	
	/** 
	 * 검색조건
	 */
	private String start_dd	= 	Constants.BLANK;
	private String end_dd	= 	Constants.BLANK;
	private String whatdd	= 	Constants.BLANK;
	private String path	= 	Constants.BLANK;
	
	
	//2012.4.24
	/** 
	 * 회사코드
	 */
	private String cocd	= 	Constants.BLANK;
	
	/** 
	 * 채널
	 */
	private String chennel	= 	Constants.BLANK;
	
	/** 
	 * 소산여부
	 */
	private String backup_yn	= 	Constants.BLANK;
	
	/** 
	 * 소산등록일
	 */
	private String backup_dt	= 	Constants.BLANK;
	
	/** 
	 * 소산신청자
	 */
	private String backup_id	= 	Constants.BLANK;
	
	

	/** 
	 * 카탈로깅YN
	 */
	private String catalog_yn	= 	Constants.BLANK;
	
	
	
	public String getOld_copy_cd() {
		return old_copy_cd;
	}
	public void setOld_copy_cd(String oldCopyCd) {
		old_copy_cd = oldCopyCd;
	}
	public String getCatalog_yn() {
		return catalog_yn;
	}
	public void setCatalog_yn(String catalogYn) {
		catalog_yn = catalogYn;
	}
	public String getBackup_yn() {
		return backup_yn;
	}
	public void setBackup_yn(String backupYn) {
		backup_yn = backupYn;
	}
	public String getBackup_dt() {
		return backup_dt;
	}
	public void setBackup_dt(String backupDt) {
		backup_dt = backupDt;
	}
	public String getBackup_id() {
		return backup_id;
	}
	public void setBackup_id(String backupId) {
		backup_id = backupId;
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
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}
	public String getEtc() {
		return etc;
	}
	public void setEtc(String etc) {
		this.etc = etc;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getStart_dd() {
		return start_dd;
	}
	public void setStart_dd(String startDd) {
		start_dd = startDd;
	}
	public String getEnd_dd() {
		return end_dd;
	}
	public void setEnd_dd(String endDd) {
		end_dd = endDd;
	}
	public String getWhatdd() {
		return whatdd;
	}
	public void setWhatdd(String whatdd) {
		this.whatdd = whatdd;
	}
	public String getClf_cd() {
		return clf_cd;
	}
	public void setClf_cd(String clfCd) {
		clf_cd = clfCd;
	}
	public String getReq_nm() {
		return req_nm;
	}
	public void setReq_nm(String reqNm) {
		req_nm = reqNm;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getArchive_path() {
		return archive_path;
	}
	public void setArchive_path(String archivePath) {
		archive_path = archivePath;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String reqId) {
		req_id = reqId;
	}
	public String getReq_dd() {
		return req_dd;
	}
	public void setReq_dd(String reqDd) {
		req_dd = reqDd;
	}
	public String getArchive_yn() {
		return archive_yn;
	}
	public void setArchive_yn(String archiveYn) {
		archive_yn = archiveYn;
	}
	public String getHigh_qual() {
		return high_qual;
	}
	public void setHigh_qual(String highQual) {
		high_qual = highQual;
	}
	public String getWmv() {
		return wmv;
	}
	public void setWmv(String wmv) {
		this.wmv = wmv;
	}
	public String getDtl_yn() {
		return dtl_yn;
	}
	public void setDtl_yn(String dtlYn) {
		dtl_yn = dtlYn;
	}
	public String getCopy_cd() {
		return copy_cd;
	}
	public void setCopy_cd(String copyCd) {
		copy_cd = copyCd;
	}
	
	
}

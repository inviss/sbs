package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * das-tm에서 필요한 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class TransferDO extends DTO {

	/**
	 * 소스 미디어 파일
	 */
	private String source_media_file = Constants.BLANK;
	/**
	 * 소스 미디어 XML
	 */
	private String source_media_xml = Constants.BLANK;
	/**
	 * 타겟 미디어 파일
	 */
	private String target_media_file = Constants.BLANK;
	/**
	 * 타겟 미디어 XML
	 */
	private String target_media_xml = Constants.BLANK;
	/**
	 * 타겟 셀 아이디
	 */
	private String Cell_id = Constants.BLANK;
	/**
	 * 타입
	 */
	private String type = Constants.BLANK;
	/**
	 * 액션
	 */
	private String action = Constants.BLANK;
	/**
	 * 태스크아이디
	 */
	private int taskID;
	/**
	 * 메세지
	 */
	private String message = Constants.BLANK;
	/**
	 * 상태값
	 */
	private String status = Constants.BLANK;
	/**
	 * 진행률
	 */
	private String progress = Constants.BLANK;
	/**
	 * 파일경로
	 */
	private String file_path = Constants.BLANK;
	
	/**
	 * 카드번호
	 */
	private int cart_no;
	/**
	 * 카트순번
	 */
	private int cart_seq;
	/**
	 * 성공여부
	 */
	private String success_yn= Constants.BLANK;
	
	
	/**
	 * 미디어 id
	 */
	private String media_id= Constants.BLANK;
	
	/**
	 * 카테고리
	 */
	private String category= Constants.BLANK;
	
	/**
	 * 스토리지
	 */
	private String storagename= Constants.BLANK;
	
	/**
	 * 타이틀
	 */
	private String title= Constants.BLANK;
	
	
	/**
	 * 프로그램명
	 */
	private String pgm_nm= Constants.BLANK;
	
	
	/**
	 * 파일명(pds)
	 */
	private String fl_nm= Constants.BLANK;
	/**
	 * 파일명(nds,on-air)
	 */
	private String wrk_fl_nm= Constants.BLANK;
	/**
	 * das파일명
	 */
	private String dasfl_nm= Constants.BLANK;
	
	/**
	 * 등록자
	 */
	private String user_id= Constants.BLANK;
	
	/**
	 * 저장위치
	 */
	private String strg_loc= Constants.BLANK;
	
	/**
	 * DAS-TM 전송시 사용할 파일(mxf,xml)명
	 */
	private String rename = Constants.BLANK;
	
	
	/**
	 * 다운구분
	 */
	private String down_gubun = Constants.BLANK;
	
	
	/**
	 * 시작점
	 */
	private String som = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom = Constants.BLANK;
	
	
	
	/**
	 * 풀다운여부
	 */
	private String down_typ = Constants.BLANK;
	
	
	/**
	 * 목적지 시스템 
	 */
	private String target_cms_id = Constants.BLANK;
	/**
	 * 화질코드
	 */
	private String vd_qlty = Constants.BLANK;
	
	/**
	 * 장비ID
	 */
	private String eq_id = Constants.BLANK;
	
	/**
	 * 에러코드
	 */
	private String error_code = Constants.BLANK;
	
	/**
	 * cti_id
	 * 
	 */
	private long cti_id=0;
	
	/**
	 * 목적지 코드
	 */
	private String destination = Constants.BLANK;
	
	/**
	 * 등록일
	 */
	private String reg_dt = Constants.BLANK;
	/**
	 * 원본파일명
	 */
	private String org_file_nm = Constants.BLANK;
	
	
	
	public String getOrg_file_nm() {
		return org_file_nm;
	}
	public void setOrg_file_nm(String orgFileNm) {
		org_file_nm = orgFileNm;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String errorCode) {
		error_code = errorCode;
	}
	public String getEq_id() {
		return eq_id;
	}
	public void setEq_id(String eqId) {
		eq_id = eqId;
	}
	public String getVd_qlty() {
		return vd_qlty;
	}
	public void setVd_qlty(String vdQlty) {
		vd_qlty = vdQlty;
	}
	public String getTarget_cms_id() {
		return target_cms_id;
	}
	public void setTarget_cms_id(String targetCmsId) {
		target_cms_id = targetCmsId;
	}
	public String getDown_typ() {
		return down_typ;
	}
	public void setDown_typ(String downTyp) {
		down_typ = downTyp;
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
	public String getDown_gubun() {
		return down_gubun;
	}
	public void setDown_gubun(String downGubun) {
		down_gubun = downGubun;
	}
	public String getWrk_fl_nm() {
		return wrk_fl_nm;
	}
	public void setWrk_fl_nm(String wrkFlNm) {
		wrk_fl_nm = wrkFlNm;
	}
	public String getRename() {
		return rename;
	}
	public void setRename(String rename) {
		this.rename = rename;
	}
	public String getStrg_loc() {
		return strg_loc;
	}
	public void setStrg_loc(String strgLoc) {
		strg_loc = strgLoc;
	}
	public String getDasfl_nm() {
		return dasfl_nm;
	}
	public void setDasfl_nm(String dasflNm) {
		dasfl_nm = dasflNm;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getFl_nm() {
		return fl_nm;
	}
	public void setFl_nm(String flNm) {
		fl_nm = flNm;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStoragename() {
		return storagename;
	}
	public void setStoragename(String storagename) {
		this.storagename = storagename;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgmNm) {
		pgm_nm = pgmNm;
	}
	public String getSuccess_yn() {
		return success_yn;
	}
	public void setSuccess_yn(String successYn) {
		success_yn = successYn;
	}
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cartNo) {
		cart_no = cartNo;
	}
	public int getCart_seq() {
		return cart_seq;
	}
	public void setCart_seq(int cartSeq) {
		cart_seq = cartSeq;
	}
	public String getSource_media_file() {
		return source_media_file;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String filePath) {
		file_path = filePath;
	}
	public void setSource_media_file(String source_media_file) {
		this.source_media_file = source_media_file;
	}
	public String getSource_media_xml() {
		return source_media_xml;
	}
	public void setSource_media_xml(String source_media_xml) {
		this.source_media_xml = source_media_xml;
	}
	public String getTarget_media_file() {
		return target_media_file;
	}
	public void setTarget_media_file(String target_media_file) {
		this.target_media_file = target_media_file;
	}
	public String getTarget_media_xml() {
		return target_media_xml;
	}
	public void setTarget_media_xml(String target_media_xml) {
		this.target_media_xml = target_media_xml;
	}
	public String getCell_id() {
		return Cell_id;
	}
	public void setCell_id(String cell_id) {
		Cell_id = cell_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public int getTaskID() {
		return taskID;
	}
	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	
}

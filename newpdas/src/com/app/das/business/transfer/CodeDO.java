package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 공통 코드 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class CodeDO extends DTO 
{
	/**
	 * 구분코드
	 */
	private String clfCd = Constants.BLANK;
	/**
	 * 구분상세코드
	 */
	private String sclCd = Constants.BLANK;
	/**
	 * 구분명
	 */
	private String clfNm = Constants.BLANK;
	/**
	 * 설명
	 */
	private String desc = Constants.BLANK;
	/**
	 * 2차 구분코드 (예: 주석구분코드 ( 주제영상,사용제한등급)
	 */
	private String gubun = Constants.BLANK;
	/**
	 * 비고1
	 */
	private String rmk1 = Constants.BLANK;
		
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	/**
	 * 비고2
	 */
	private String rmk2 = Constants.BLANK;
	/**
	 * 사용여부
	 */
	private String useYn = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId = Constants.BLANK;
	/**
	 * 수정일
	 */
	private String modDt = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId = Constants.BLANK;
	
	/**
	 * 페이지
	 */
	private int page;
	
	/**
	 * 순번
	 */
	private int seq;
	
	/**
	 * 주제영상 구분(ㄱㄴㄷㄹ.....ㅎ까지)
	 */
	private String mainGubun;
	
	/**
	 * 구분자 (001 전체검색, 002 대분류, 003 중분류, 004 소분류)
	 */
	private String Search_Type = Constants.BLANK;
	
	
	/**
	 * 대분류 코드
	 */
	private String big_code = Constants.BLANK;
	
	/**
	 * 대분류 코드명
	 */
	private String big_nm = Constants.BLANK;
	
	/**
	 * 대분류 코드설명
	 */
	private String big_desc = Constants.BLANK;
	
	/**
	 * 대분류 사용여부
	 */
	private String big_use_yn = Constants.BLANK;
	/**
	 * 중분류 코드
	 */
	private String mid_code = Constants.BLANK;
	
	/**
	 * 중분류 코드명
	 */
	private String mid_nm = Constants.BLANK;
	
	/**
	 * 중분류 코드설명
	 */
	private String mid_desc = Constants.BLANK;
	/**
	 * 중분류 사용여부
	 */
	private String mid_use_yn = Constants.BLANK;
	/**
	 * 소분류 코드
	 */
	private String sml_code = Constants.BLANK;
	
	/**
	 * 소분류 코드명
	 */
	private String sml_nm = Constants.BLANK;
	
	/**
	 * 소분류 코드설명
	 */
	private String sml_desc = Constants.BLANK;
	
	/**
	 * 소분류 사용여부
	 */
	private String sml_use_yn = Constants.BLANK;
	
	
	
	public String getBig_use_yn() {
		return big_use_yn;
	}
	public void setBig_use_yn(String bigUseYn) {
		big_use_yn = bigUseYn;
	}
	public String getMid_use_yn() {
		return mid_use_yn;
	}
	public void setMid_use_yn(String midUseYn) {
		mid_use_yn = midUseYn;
	}
	public String getSml_use_yn() {
		return sml_use_yn;
	}
	public void setSml_use_yn(String smlUseYn) {
		sml_use_yn = smlUseYn;
	}
	public String getBig_code() {
		return big_code;
	}
	public void setBig_code(String bigCode) {
		big_code = bigCode;
	}
	public String getBig_nm() {
		return big_nm;
	}
	public void setBig_nm(String bigNm) {
		big_nm = bigNm;
	}
	public String getBig_desc() {
		return big_desc;
	}
	public void setBig_desc(String bigDesc) {
		big_desc = bigDesc;
	}
	public String getMid_code() {
		return mid_code;
	}
	public void setMid_code(String midCode) {
		mid_code = midCode;
	}
	public String getMid_nm() {
		return mid_nm;
	}
	public void setMid_nm(String midNm) {
		mid_nm = midNm;
	}
	public String getMid_desc() {
		return mid_desc;
	}
	public void setMid_desc(String midDesc) {
		mid_desc = midDesc;
	}
	public String getSml_code() {
		return sml_code;
	}
	public void setSml_code(String smlCode) {
		sml_code = smlCode;
	}
	public String getSml_nm() {
		return sml_nm;
	}
	public void setSml_nm(String smlNm) {
		sml_nm = smlNm;
	}
	public String getSml_desc() {
		return sml_desc;
	}
	public void setSml_desc(String smlDesc) {
		sml_desc = smlDesc;
	}
	public String getMainGubun() {
		return mainGubun;
	}
	public void setMainGubun(String mainGubun) {
		this.mainGubun = mainGubun;
	}
	public String getSearch_Type() {
		return Search_Type;
	}
	public void setSearch_Type(String searchType) {
		Search_Type = searchType;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getClfCd() {
		return clfCd;
	}
	public void setClfCd(String clfCd) {
		this.clfCd = clfCd;
	}
	public String getClfNm() {
		return clfNm;
	}
	public void setClfNm(String clfNm) {
		this.clfNm = clfNm;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRmk1() {
		return rmk1;
	}
	public void setRmk1(String rmk1) {
		this.rmk1 = rmk1;
	}
	public String getRmk2() {
		return rmk2;
	}
	public void setRmk2(String rmk2) {
		this.rmk2 = rmk2;
	}
	public String getSclCd() {
		return sclCd;
	}
	public void setSclCd(String sclCd) {
		this.sclCd = sclCd;
	}
	public String getUseYn() {
		return useYn;
	}
	public void setUseYn(String useYn) {
		this.useYn = useYn;
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
}

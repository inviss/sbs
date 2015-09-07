package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 내용 및 제작정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class ContentsInfoDO extends DTO 
{
	/**
	 * 프로듀서명
	 */
	private String producerNm       = Constants.BLANK;
	/**
	 * 연출자명
	 */
	private String drtNm            = Constants.BLANK;
	/**
	 * 작가명
	 */
	private String writerNm         = Constants.BLANK;
	/**
	 * 촬영감독명
	 */
	private String cmrDrtNm         = Constants.BLANK;
	/**
	 * 제작부서명칭
	 */
	private String prdtDeptNm       = Constants.BLANK;
	/**
	 * 원제작사명
	 */
	private String orgPrdrNm        = Constants.BLANK;
	/**
	 * 제작구분코드
	 */
	private String prdtInOutsCd    = Constants.BLANK;
	/**
	 * 스놉시스
	 */
	private String snps             = Constants.BLANK;
	/**
	 * 키워드
	 */
	private String keyWords         = Constants.BLANK;
	/**
	 * 출연자명
	 */
	private String castNm           = Constants.BLANK;
	/**
	 * 특이사항
	 */
	private String spcInfo   	= Constants.BLANK;
	
	/**
	 *   FL_PATH
	 */
	private String fl_path = Constants.BLANK;
	/**
	 * WRK_FILE_NM
	 */
	private String wrk_file_nm = Constants.BLANK;
	/**
	 * ORG_FILE_NM
	 */
	private String org_file_nm = Constants.BLANK;
	/**
	 * FL_SZ
	 */
	private int fl_sz = 0;
	/**
	 * down_typ
	 */
	private String down_typ = "";
	
	/**
	 * cti_id
	 */
	private long cti_id = 0;
	
	/**
	 * DOWN_GUBUN
	 */
	private String down_gubun = Constants.BLANK;
	
	/**
	 * som
	 */
	private String som = Constants.BLANK;
	
	
	/**
	 * eom
	 */
	private String eom = Constants.BLANK;
	
	
	
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
	public long getCti_id() {
		return cti_id;
	}
	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}
	public String getDown_typ() {
		return down_typ;
	}
	public void setDown_typ(String downTyp) {
		down_typ = downTyp;
	}
	public String getFl_path() {
		return fl_path;
	}
	public void setFl_path(String fl_path) {
		this.fl_path = fl_path;
	}
	public String getWrk_file_nm() {
		return wrk_file_nm;
	}
	public void setWrk_file_nm(String wrk_file_nm) {
		this.wrk_file_nm = wrk_file_nm;
	}
	public String getOrg_file_nm() {
		return org_file_nm;
	}
	public void setOrg_file_nm(String org_file_nm) {
		this.org_file_nm = org_file_nm;
	}
	public int getFl_sz() {
		return fl_sz;
	}
	public void setFl_sz(int fl_sz) {
		this.fl_sz = fl_sz;
	}
	public String getCastNm() {
		return castNm;
	}
	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}
	public String getCmrDrtNm() {
		return cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm) {
		this.cmrDrtNm = cmrDrtNm;
	}
	public String getDrtNm() {
		return drtNm;
	}
	public void setDrtNm(String drtNm) {
		this.drtNm = drtNm;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	public String getPrdtDeptNm() {
		return prdtDeptNm;
	}
	public void setPrdtDeptNm(String prdtDeptNm) {
		this.prdtDeptNm = prdtDeptNm;
	}
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOoutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public String getWriterNm() {
		return writerNm;
	}
	public void setWriterNm(String writerNm) {
		this.writerNm = writerNm;
	}     
}

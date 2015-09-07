package com.app.das.business.transfer;



import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * 명장면 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class GoodMediaDO extends DTO 
{
	/**
	 * 마스터 id
	 */
	private long master_id;
	/**
	 * 주석구분코드내용
	 */
	private String annot_clf_cont   = Constants.BLANK;
	/**
	 * 시작점
	 */
	private String som      = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom         = Constants.BLANK;
	
	/**
	 * 제목
	 */
	private String title         = Constants.BLANK;
	
	/**
	 * 등록일
	 */
	private String reg_dt        = Constants.BLANK;

	/**
	 * 회차
	 */
	private String epis_no        = Constants.BLANK;
	
	/**
	 * 방송일/촬영일
	 */
	private String brd_dd        = Constants.BLANK;
	
	/**
	 * 내용
	 */
	private String cont        = Constants.BLANK;
	
	/**
	 * 길이
	 */
	private String brd_leng        = Constants.BLANK;
	/**
	 * 컨텐츠 길이
	 */
	private String ct_leng        = Constants.BLANK;
	
	/**
	 * 대분류
	 */
	private String ctgr_l_cd        = Constants.BLANK;
	
	
	
	/**
	 * 주제영상 구분
	 */
	private String annot_clf_nm        = Constants.BLANK;
	

	/**
	 * 아카이브 여부
	 */
	private String arch_reg_dd        = Constants.BLANK;
	
	
	/**
	 * 영상id
	 */
	private int ct_id        = 0;
	
	
	
	
	
	public int getCt_id() {
		return ct_id;
	}

	public void setCt_id(int ctId) {
		ct_id = ctId;
	}

	public String getArch_reg_dd() {
		return arch_reg_dd;
	}

	public void setArch_reg_dd(String archRegDd) {
		arch_reg_dd = archRegDd;
	}

	public String getAnnot_clf_nm() {
		return annot_clf_nm;
	}

	public void setAnnot_clf_nm(String annotClfNm) {
		annot_clf_nm = annotClfNm;
	}

	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}

	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}

	public String getCt_leng() {
		return ct_leng;
	}

	public void setCt_leng(String ctLeng) {
		ct_leng = ctLeng;
	}

	public String getEpis_no() {
		return epis_no;
	}

	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}

	public String getBrd_dd() {
		return brd_dd;
	}

	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getBrd_leng() {
		return brd_leng;
	}

	public void setBrd_leng(String brdLeng) {
		brd_leng = brdLeng;
	}

	public long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(long masterId) {
		master_id = masterId;
	}



	public String getAnnot_clf_cont() {
		return annot_clf_cont;
	}

	public void setAnnot_clf_cont(String annotClfCont) {
		annot_clf_cont = annotClfCont;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReg_dt() {
		return reg_dt;
	}

	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
}

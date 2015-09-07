package com.app.das.business.transfer;
import java.math.BigDecimal;


import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 부서별 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class DepInfoDO extends DTO {
	
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	/**
	 * 회사명
	 */
	private String conm = Constants.BLANK;
	/**
	 * 소속구분
	 */
	private String post_clf = Constants.BLANK;
	/**
	 * 부서코드
	 */
	private String dept_cd = Constants.BLANK;
	/**
	 * 소속단위
	 */
	private String post_unit_clf = Constants.BLANK;
	/**
	 * 부서명
	 */
	private String dept_nm = Constants.BLANK;
	/**
	 * 부서레벨
	 */
	private String lvl = Constants.BLANK;
	/**
	 * 본부코드
	 */
	private String sup_head_cd = Constants.BLANK;
	/**
	 * 본부명
	 */
	private String sup_head_nm = Constants.BLANK;
	/**
	 * 국코드
	 */
	private String sup_htpo_cd = Constants.BLANK;
	/**
	 * 국명
	 */
	private String sup_htpo_nm = Constants.BLANK;
	/**
	 * 부서장사번
	 */
	private String dept_chap_emp_no = Constants.BLANK;
	/**
	 * 부서순번
	 */
	private int seq;
	/**
	 * 본부순서
	 */
	private int sup_head_seq;
	/**
	 * 국순서
	 */
	private int sup_htpo_seq;
	/**
	 * 상위부서코드
	 */
	private String sup_dept_cd= Constants.BLANK;
	/**
	 * 상위부서명
	 */
	private String sup_dept_nm= Constants.BLANK;
	/**
	 * 사용여부
	 */
	private String use_yn= Constants.BLANK;
	
	
	/**
	 * 상태
	 */
	private String status= Constants.BLANK;
	/**
	 * 상위부서코드
	 */
	private String parent_dept_cd= Constants.BLANK;
	/**
	 * 상위부서명
	 */
	private String parent_dept_nm= Constants.BLANK;
	
	
	
	public String getSup_dept_nm() {
		return sup_dept_nm;
	}
	public void setSup_dept_nm(String supDeptNm) {
		sup_dept_nm = supDeptNm;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParent_dept_cd() {
		return parent_dept_cd;
	}
	public void setParent_dept_cd(String parentDeptCd) {
		parent_dept_cd = parentDeptCd;
	}
	public String getParent_dept_nm() {
		return parent_dept_nm;
	}
	public void setParent_dept_nm(String parentDeptNm) {
		parent_dept_nm = parentDeptNm;
	}
	public String getUse_yn() {
		return use_yn;
	}
	public void setUse_yn(String useYn) {
		use_yn = useYn;
	}
	public String getSup_dept_cd() {
		return sup_dept_cd;
	}
	public void setSup_dept_cd(String supDeptCd) {
		sup_dept_cd = supDeptCd;
	}
	private int seqnum;
	
	
	private String wcocd = Constants.BLANK;
	
	private String wdept_cd = Constants.BLANK;
	public String getWcocd() {
		return wcocd;
	}
	public void setWcocd(String wcocd) {
		this.wcocd = wcocd;
	}
	public String getWdept_cd() {
		return wdept_cd;
	}
	public void setWdept_cd(String wdeptCd) {
		wdept_cd = wdeptCd;
	}
	public int getSeqnum() {
		return seqnum;
	}
	public void setSeqnum(int seqnum) {
		this.seqnum = seqnum;
	}
	private int page;
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getPost_clf() {
		return post_clf;
	}
	public String getConm() {
		return conm;
	}
	public void setConm(String conm) {
		this.conm = conm;
	}
	public void setPost_clf(String postClf) {
		post_clf = postClf;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}
	public String getPost_unit_clf() {
		return post_unit_clf;
	}
	public void setPost_unit_clf(String postUnitClf) {
		post_unit_clf = postUnitClf;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String deptNm) {
		dept_nm = deptNm;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getSup_head_cd() {
		return sup_head_cd;
	}
	public void setSup_head_cd(String supHeadCd) {
		sup_head_cd = supHeadCd;
	}
	public String getSup_head_nm() {
		return sup_head_nm;
	}
	public void setSup_head_nm(String supHeadNm) {
		sup_head_nm = supHeadNm;
	}
	public String getSup_htpo_cd() {
		return sup_htpo_cd;
	}
	public void setSup_htpo_cd(String supHtpoCd) {
		sup_htpo_cd = supHtpoCd;
	}
	public String getSup_htpo_nm() {
		return sup_htpo_nm;
	}
	public void setSup_htpo_nm(String supHtpoNm) {
		sup_htpo_nm = supHtpoNm;
	}
	public String getDept_chap_emp_no() {
		return dept_chap_emp_no;
	}
	public void setDept_chap_emp_no(String deptChapEmpNo) {
		dept_chap_emp_no = deptChapEmpNo;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getSup_head_seq() {
		return sup_head_seq;
	}
	public void setSup_head_seq(int supHeadSeq) {
		sup_head_seq = supHeadSeq;
	}
	public int getSup_htpo_seq() {
		return sup_htpo_seq;
	}
	public void setSup_htpo_seq(int supHtpoSeq) {
		sup_htpo_seq = supHtpoSeq;
	}
	
	
	
}

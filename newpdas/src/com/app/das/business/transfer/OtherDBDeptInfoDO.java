package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 직원 목록 조회시 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class OtherDBDeptInfoDO extends DTO
{
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	/**
	 * 부서코드
	 */
	private String dept_cd = Constants.BLANK;
	/**
	 * 부서장사번
	 */
	private String dept_chap_emp_no = Constants.BLANK;
	/**
	 * 소속부서명
	 */
	private String dept_nm = Constants.BLANK;
	/**
	 * 부서레벨
	 */
	private String lvl = Constants.BLANK;
	/**
	 * 소속단위구분
	 */
	private String post_unit_clf = Constants.BLANK;
	/**
	 * 부서순번
	 */
	private int seq;
	/**
	 * 상위부서코드
	 */
	private String sup_dept_cd = Constants.BLANK;
	/**
	 * 본부코드
	 */
	private String sup_head_cd = Constants.BLANK;
	/**
	 * 본부명
	 */
	private String sup_head_nm = Constants.BLANK;
	/**
	 * 본부순서
	 */
	private int sup_head_seq;
	/**
	 * 국코드
	 */
	private String sup_htpo_cd = Constants.BLANK;
	/**
	 * 국명
	 */
	private String sup_htpo_nm = Constants.BLANK;
	/**
	 * 국순서
	 */
	private int sup_htpo_seq;
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}
	public String getDept_chap_emp_no() {
		return dept_chap_emp_no;
	}
	public void setDept_chap_emp_no(String deptChapEmpNo) {
		dept_chap_emp_no = deptChapEmpNo;
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
	public String getPost_unit_clf() {
		return post_unit_clf;
	}
	public void setPost_unit_clf(String postUnitClf) {
		post_unit_clf = postUnitClf;
	}
	
	public String getSup_dept_cd() {
		return sup_dept_cd;
	}
	public void setSup_dept_cd(String supDeptCd) {
		sup_dept_cd = supDeptCd;
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

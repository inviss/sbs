package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 통계의 조회 조건을 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class StatisticsConditionDO extends DTO 
{
	/**
	 * 날짜구분(연='1', 월='2', 기간='3')
	 */
	private String dateKind = Constants.BLANK;
	/**
	 * 시작일
	 */
	private String fromDate = Constants.BLANK;
	/**
	 * 종료일
	 */
	private String toDate = Constants.BLANK;
	/**
	 * 분류구분(대분류='1', 중분류='2', 소분류='3')
	 */
	private String categoryKind = Constants.BLANK;
	
	/**
	 * 대분류 코드
	 */
	private String ctgr_l_cd = Constants.BLANK;
	
	/**
	 * 중분류 코드
	 */
	private String ctgr_m_cd = Constants.BLANK;
	
	/**
	 * 소분류코드
	 */
	private String ctgr_s_cd = Constants.BLANK;
	
	/**
	 * 대분류명
	 */
	private String ctgr_l_nm = Constants.BLANK;
	
	/**
	 * 중분류명
	 */
	private String ctgr_m_nm = Constants.BLANK;
	
	/**
	 * 소분류명
	 */
	private String ctgr_s_nm = Constants.BLANK;
	
	/**
	 * 건수계
	 */
	private String by_dy_qty = Constants.BLANK;
	
	/**
	 * 시간계
	 */
	private String by_dy_tm = Constants.BLANK;
	
	/**
	 * 건수합계
	 */
	private String sum_qty = Constants.BLANK;
	
	/**
	 * 시간합계
	 */
	private String sum_tm = Constants.BLANK;
	
	/**
	 * 날짜
	 */
	private String dd = Constants.BLANK;
	
	/**
	 * 날짜그룹
	 */
	private String day = Constants.BLANK;
	
	/**
	 * 소속
	 */
	private String grp = Constants.BLANK;
	
	/**
	 * 소속명
	 */
	private String grp_nm = Constants.BLANK;
	
	/**
	 * 본부
	 */
	private String seg = Constants.BLANK;
	
	/**
	 * 본부명
	 */
	private String seg_nm = Constants.BLANK;
	
	/**
	 * 부서
	 */
	private String dept = Constants.BLANK;
	
	/**
	 * 부서명
	 */
	private String dept_nm = Constants.BLANK;
	/**
	 * 프로그램 아이디
	 */
	private String pgm_id = Constants.BLANK;
	
	
	/**
	 * 마스터 아이디
	 */
	private String master_id = Constants.BLANK;
	
	/**
	 * 아카이브 요청 경로 구분
	 */
	private String source_gubun = Constants.BLANK;
	
	/**
	 * 프로그램명
	 */
	private String pgm_nm = Constants.BLANK;
	
	/**
	 * 총합
	 */
	private String total = Constants.BLANK;
	
	/**
	 * 미정리량 건수
	 */
	private String arrange_count = Constants.BLANK;
	
	/**
	 * 미정리 시간
	 */
	private String arrange_duration = Constants.BLANK;
	
	/**
	 * 컨텐츠 구분 코드
	 */
	private String ct_cla = Constants.BLANK;
	
	/**
	 * 컨텐츠 구분 명
	 */
	private String ct_cla_nm = Constants.BLANK;
	
	//2012.4.25
	/**
	 * 회사코드
	 */
	private String cocd = Constants.BLANK;
	
	/**
	 * 채널코드
	 */
	private String chennel = Constants.BLANK;
	
	
	
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
	public String getCt_cla() {
		return ct_cla;
	}
	public void setCt_cla(String ctCla) {
		ct_cla = ctCla;
	}
	public String getCt_cla_nm() {
		return ct_cla_nm;
	}
	public void setCt_cla_nm(String ctClaNm) {
		ct_cla_nm = ctClaNm;
	}
	public String getGrp_nm() {
		return grp_nm;
	}
	public void setGrp_nm(String grp_nm) {
		this.grp_nm = grp_nm;
	}
	public String getSeg_nm() {
		return seg_nm;
	}
	public void setSeg_nm(String seg_nm) {
		this.seg_nm = seg_nm;
	}
	public String getDept_nm() {
		return dept_nm;
	}
	public void setDept_nm(String dept_nm) {
		this.dept_nm = dept_nm;
	}
	public String getArrange_count() {
		return arrange_count;
	}
	public void setArrange_count(String arrange_count) {
		this.arrange_count = arrange_count;
	}
	
	public String getArrange_duration() {
		return arrange_duration;
	}
	public void setArrange_duration(String arrange_duration) {
		this.arrange_duration = arrange_duration;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getSum_qty() {
		return sum_qty;
	}
	public void setSum_qty(String sum_qty) {
		this.sum_qty = sum_qty;
	}
	public String getSum_tm() {
		return sum_tm;
	}
	public void setSum_tm(String sum_tm) {
		this.sum_tm = sum_tm;
	}
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgr_l_cd) {
		this.ctgr_l_cd = ctgr_l_cd;
	}
	public String getCtgr_m_cd() {
		return ctgr_m_cd;
	}
	public void setCtgr_m_cd(String ctgr_m_cd) {
		this.ctgr_m_cd = ctgr_m_cd;
	}
	public String getCtgr_s_cd() {
		return ctgr_s_cd;
	}
	public void setCtgr_s_cd(String ctgr_s_cd) {
		this.ctgr_s_cd = ctgr_s_cd;
	}
	public String getCtgr_l_nm() {
		return ctgr_l_nm;
	}
	public void setCtgr_l_nm(String ctgr_l_nm) {
		this.ctgr_l_nm = ctgr_l_nm;
	}
	public String getCtgr_m_nm() {
		return ctgr_m_nm;
	}
	public void setCtgr_m_nm(String ctgr_m_nm) {
		this.ctgr_m_nm = ctgr_m_nm;
	}
	public String getCtgr_s_nm() {
		return ctgr_s_nm;
	}
	public void setCtgr_s_nm(String ctgr_s_nm) {
		this.ctgr_s_nm = ctgr_s_nm;
	}
	public String getBy_dy_qty() {
		return by_dy_qty;
	}
	public void setBy_dy_qty(String by_dy_qty) {
		this.by_dy_qty = by_dy_qty;
	}
	public String getBy_dy_tm() {
		return by_dy_tm;
	}
	public void setBy_dy_tm(String by_dy_tm) {
		this.by_dy_tm = by_dy_tm;
	}
	public String getDd() {
		return dd;
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getGrp() {
		return grp;
	}
	public void setGrp(String grp) {
		this.grp = grp;
	}
	public String getSeg() {
		return seg;
	}
	public void setSeg(String seg) {
		this.seg = seg;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPgm_id() {
		return pgm_id;
	}
	public void setPgm_id(String pgm_id) {
		this.pgm_id = pgm_id;
	}
	public String getMaster_id() {
		return master_id;
	}
	public void setMaster_id(String master_id) {
		this.master_id = master_id;
	}
	public String getDateKind() {
		return dateKind;
	}
	public void setDateKind(String dateKind) {
		this.dateKind = dateKind;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getCategoryKind() {
		return categoryKind;
	}
	public void setCategoryKind(String categoryKind) {
		this.categoryKind = categoryKind;
	}
	public String getSource_gubun() {
		return source_gubun;
	}
	public void setSource_gubun(String source_gubun) {
		this.source_gubun = source_gubun;
	}
	public String getPgm_nm() {
		return pgm_nm;
	}
	public void setPgm_nm(String pgm_nm) {
		this.pgm_nm = pgm_nm;
	}
}

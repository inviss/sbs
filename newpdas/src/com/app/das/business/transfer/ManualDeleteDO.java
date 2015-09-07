package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 사용자 수동삭제 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class ManualDeleteDO extends DTO 
{
	/**
	 * 구분(001: 아카이브, 002:다운로드)
	 */
	private String gubun = Constants.BLANK;
	/**
	 * 구분명
	 */
	private String gubun_nm = Constants.BLANK;
	/**
	 * 시작페이지
	 */
	private int start_page = 0;
	
	/**
	 * 총조회갯수
	 */
	private int totalcount = 0;
	
	/**
	 * key
	 */
	private int key = 0;
	
	/**
	 * 파일명
	 */
	private String file_nm = Constants.BLANK;
	
	/**
	 * 파일 경로
	 */
	private String fl_path = Constants.BLANK;
	
	/**
	 * 등록일자(시작)
	 */
	private String strat_reg_dt = Constants.BLANK;
	
	/**
	 * 등록일자(종료)
	 */
	private String end_reg_dt = Constants.BLANK;
	
	/**
	 * 아카이브 경로
	 */
	private String  arch_route = Constants.BLANK;
	
	/**
	 * 채널 
	 */
	private String chennel = Constants.BLANK;

	
	/**
	 * 삭제요청자id
	 */
	private String req_id = Constants.BLANK;
	
	/**
	 * cti_id
	 */
	private long cti_id = 0;
	
	
	/**
	 * ct_id
	 */
	private long ct_id = 0;
	
	/**
	 * 오브젝트명
	 */
	private String obj_name = Constants.BLANK;
	
	/**
	 * dtl 구분
	 */
	private String dtl_type = Constants.BLANK;
	
	
	
	
	
	public long getCt_id() {
		return ct_id;
	}

	public void setCt_id(long ctId) {
		ct_id = ctId;
	}

	public String getDtl_type() {
		return dtl_type;
	}

	public void setDtl_type(String dtlType) {
		dtl_type = dtlType;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public String getObj_name() {
		return obj_name;
	}

	public void setObj_name(String objName) {
		obj_name = objName;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getGubun_nm() {
		return gubun_nm;
	}

	public void setGubun_nm(String gubunNm) {
		gubun_nm = gubunNm;
	}

	public String getReq_id() {
		return req_id;
	}

	public void setReq_id(String reqId) {
		req_id = reqId;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public int getStart_page() {
		return start_page;
	}

	public void setStart_page(int startPage) {
		start_page = startPage;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public String getFile_nm() {
		return file_nm;
	}

	public void setFile_nm(String fileNm) {
		file_nm = fileNm;
	}

	public String getFl_path() {
		return fl_path;
	}

	public void setFl_path(String flPath) {
		fl_path = flPath;
	}

	public String getStrat_reg_dt() {
		return strat_reg_dt;
	}

	public void setStrat_reg_dt(String stratRegDt) {
		strat_reg_dt = stratRegDt;
	}

	public String getEnd_reg_dt() {
		return end_reg_dt;
	}

	public void setEnd_reg_dt(String endRegDt) {
		end_reg_dt = endRegDt;
	}

	public String getArch_route() {
		return arch_route;
	}

	public void setArch_route(String archRoute) {
		arch_route = archRoute;
	}

	public String getChennel() {
		return chennel;
	}

	public void setChennel(String chennel) {
		this.chennel = chennel;
	}


	
	
	
}

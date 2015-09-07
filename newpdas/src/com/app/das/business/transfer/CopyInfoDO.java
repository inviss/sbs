package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;

/**
 * 복본 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
import com.app.das.business.transfer.DTO;

/**
 * 복본 관리 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class CopyInfoDO extends DTO  {

	
	/**
	 * 프로그램 ID
	 */
	private long pgmId; 
	
	/**
	 * 프로그램명
	 */
	private String title            = Constants.BLANK;
	
	
	/**
	 * 등록일
	 */
	private String reg_dt            = Constants.BLANK;
	
	/**
	 * 등록자
	 */
	private String regrid            = Constants.BLANK;
	
	/**
	 * 자동생성여부
	 */
	private String copy_yn            = Constants.BLANK;
	
	/**
	 * cms_pgm_id
	 */
	private String cms_pgm_id            = Constants.BLANK;
	
	/**
	 * page
	 */
	private int page;
	
	
	/**
	 * 시작
	 */
	private String start_reg_dt            = Constants.BLANK;
	
	/**
	 * 종료
	 */
	private String end__reg_dt            = Constants.BLANK;
	
	/**
	 * 복본업데이트 그룹( y: 복본설정)
	 */
	private String pgm_id_y             = Constants.BLANK;
	
	/**
	 * 복본업데이트 그룹( n: 복본설정)
	 */
	private String pgm_id_n             = Constants.BLANK;
	
	
	public String getPgm_id_y() {
		return pgm_id_y;
	}

	public void setPgm_id_y(String pgmIdY) {
		pgm_id_y = pgmIdY;
	}

	public String getPgm_id_n() {
		return pgm_id_n;
	}

	public void setPgm_id_n(String pgmIdN) {
		pgm_id_n = pgmIdN;
	}

	public long getPgmId() {
		return pgmId;
	}

	public void setPgmId(long pgmId) {
		this.pgmId = pgmId;
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

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getCopy_yn() {
		return copy_yn;
	}

	public void setCopy_yn(String copyYn) {
		copy_yn = copyYn;
	}

	public String getCms_pgm_id() {
		return cms_pgm_id;
	}

	public void setCms_pgm_id(String cmsPgmId) {
		cms_pgm_id = cmsPgmId;
	}

	

	public String getStart_reg_dt() {
		return start_reg_dt;
	}

	public void setStart_reg_dt(String startRegDt) {
		start_reg_dt = startRegDt;
	}

	public String getEnd__reg_dt() {
		return end__reg_dt;
	}

	public void setEnd__reg_dt(String endRegDt) {
		end__reg_dt = endRegDt;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}

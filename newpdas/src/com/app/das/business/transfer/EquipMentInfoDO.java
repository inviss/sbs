
package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 장비정보를 가지고잇는 BEANS
 * @author ysk523
 *
 */
public class EquipMentInfoDO extends DTO 
{
	
	/**
	 * DAS장비ID
	 */
	private String das_eq_id           = Constants.BLANK;


	/**
	 *DAS작업구분코드\N(A00 SDI 인제스트 B00 TAPEOUT C00 FILE 인제스트 D00 SGL ARCHIVE E00 NLE)
	 */
	private String das_eq_clf_cd           = Constants.BLANK;

	/**
	 *DAS장비명
	 */
	private String das_eq_nm           = Constants.BLANK;

	/**
	 *CTI_ID
	 */
	private long cti_id           = 0;

	/**
	 *진행률
	 */
	private String progress           = Constants.BLANK;

	/**
	 *제목
	 */
	private String title           = Constants.BLANK;


	/**
	 *진행상태
	 */
	private String status           = Constants.BLANK;

	
	
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDas_eq_id() {
		return das_eq_id;
	}

	public void setDas_eq_id(String dasEqId) {
		das_eq_id = dasEqId;
	}

	public String getDas_eq_clf_cd() {
		return das_eq_clf_cd;
	}

	public void setDas_eq_clf_cd(String dasEqClfCd) {
		das_eq_clf_cd = dasEqClfCd;
	}

	public String getDas_eq_nm() {
		return das_eq_nm;
	}

	public void setDas_eq_nm(String dasEqNm) {
		das_eq_nm = dasEqNm;
	}

	public long getCti_id() {
		return cti_id;
	}

	public void setCti_id(long ctiId) {
		cti_id = ctiId;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	
}

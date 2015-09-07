
package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 아카이브삭제 요청
 * @author ysk523
 *
 */
public class DeleteDO extends DTO 
{
	/**
	 * 미디어ID
	 */
	private String media_id		= Constants.BLANK;
	
	/**
	 * 결과
	 */
	private String result		= Constants.BLANK;
	
	/**
	 * 삭제 사유
	 */
	private String del_cont		= Constants.BLANK;


	/**
	 * 마스터id
	 */
	private Long master_id		= 0L;
	
	/**
	 * ct_idid
	 */
	private Long ct_id		= 0L;

	/**
	 * 폐기신청자idid
	 */
	private String reg_id		= Constants.BLANK;
	
	
	
	
	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String regId) {
		reg_id = regId;
	}

	public String getDel_cont() {
		return del_cont;
	}

	public void setDel_cont(String delCont) {
		del_cont = delCont;
	}

	public Long getMaster_id() {
		return master_id;
	}

	public void setMaster_id(Long masterId) {
		master_id = masterId;
	}

	public Long getCt_id() {
		return ct_id;
	}

	public void setCt_id(Long ctId) {
		ct_id = ctId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}
	
}

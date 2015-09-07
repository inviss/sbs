package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;


/**
 * 수정자 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ModeUserInfoDO extends DTO 
{
	/** 
	 * 수정 일시 
	 */
	private String modDt  	= Constants.BLANK;
	/** 
	 * 수정자 ID 
	 */
	private String modrId     = Constants.BLANK;
	/** 
	 * 수정자 명 
	 */
	private String mod_nm     = Constants.BLANK;

	
	
	public String getMod_nm() {
		return mod_nm;
	}
	public void setMod_nm(String modNm) {
		mod_nm = modNm;
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
}

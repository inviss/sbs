package com.app.das.business.transfer;


import com.app.das.business.constants.Constants;

import com.app.das.business.transfer.DTO;

/**
 * 프리뷰 첨부 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class PreviewAttachDO extends DTO 
{
	/**
	 * 마스터 ID
	 */
	private int masterId;
	
	
	/**
	 * PREVIEW_ATTACH ID
	 */
	private int previewAttatchId;
	
	
	/**
	 * 파일이름
	 */
	private String FL_NM      = Constants.BLANK;
	
	
	/**
	 * 파일크기
	 */
	private int FL_SZ;   
	
	
	/**
	 * 파일 경로
	 */
	private String FL_PATH             = Constants.BLANK;
	
	
	/**
	 * 원 파일명
	 */
	private String Org_Flie_NM             = Constants.BLANK;
	
	
	/**
	 * 등록일
	 */
	private String Reg_dt             = Constants.BLANK;
	
	
	/**
	 * 등록자 ID
	 */
	private String RegRId             = Constants.BLANK;


	public int getMasterId() {
		return masterId;
	}


	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}


	public int getPreviewAttatchId() {
		return previewAttatchId;
	}


	public void setPreviewAttatchId(int previewAttatchId) {
		this.previewAttatchId = previewAttatchId;
	}


	public String getFL_NM() {
		return FL_NM;
	}


	public void setFL_NM(String fLNM) {
		FL_NM = fLNM;
	}


	public int getFL_SZ() {
		return FL_SZ;
	}


	public void setFL_SZ(int fLSZ) {
		FL_SZ = fLSZ;
	}


	public String getFL_PATH() {
		return FL_PATH;
	}


	public void setFL_PATH(String fLPATH) {
		FL_PATH = fLPATH;
	}


	public String getOrg_Flie_NM() {
		return Org_Flie_NM;
	}


	public void setOrg_Flie_NM(String orgFlieNM) {
		Org_Flie_NM = orgFlieNM;
	}


	public String getReg_dt() {
		return Reg_dt;
	}


	public void setReg_dt(String regDt) {
		Reg_dt = regDt;
	}


	public String getRegRId() {
		return RegRId;
	}


	public void setRegRId(String regRId) {
		RegRId = regRId;
	}
	
}
	
	
	
	

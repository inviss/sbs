package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 * 프리뷰 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class PreviewDO extends DTO 
{
	/**
	 * 마스터 ID
	 */
	private int masterId;
	/**
	 * PREVIEW ID
	 */
	private int previewId;
	/**
	 * 제목
	 */
	private String preview_subj      = Constants.BLANK;
	/**
	 * 내용
	 */
	private String preivew_cont         = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String reg_dt             = Constants.BLANK;
	
	/**
	 * 경로
	 */
	private String path             = Constants.BLANK;
	
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getMasterId() {
		return masterId;
	}
	public void setMasterId(int masterId) {
		this.masterId = masterId;
	}
	public int getPreviewId() {
		return previewId;
	}
	public void setPreviewId(int previewId) {
		this.previewId = previewId;
	}
	public String getPreview_subj() {
		return preview_subj;
	}
	public void setPreview_subj(String previewSubj) {
		preview_subj = previewSubj;
	}
	public String getPreivew_cont() {
		return preivew_cont;
	}
	public void setPreivew_cont(String preivewCont) {
		preivew_cont = preivewCont;
	}
	public String getReg_dt() {
		return reg_dt;
	}
	public void setReg_dt(String regDt) {
		reg_dt = regDt;
	}
}
	
	
	
	

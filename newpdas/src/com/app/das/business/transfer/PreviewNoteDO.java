package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * 프리뷰 상세 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PreviewNoteDO {
	
	/** 
	 * 내용
	 */
	private String cont = Constants.BLANK;
	/** 
	 * 시작점
	 */
	private String som= Constants.BLANK;
	
	
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	
	
	
}

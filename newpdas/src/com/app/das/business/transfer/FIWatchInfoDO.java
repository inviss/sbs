package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;


/**
 * 파일 인제스트 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class FIWatchInfoDO extends DTO 
{
	
	private int dasEqSeq;  
	
	private String regDt   = Constants.BLANK;
	
	private String xml   = Constants.BLANK;
	
		
	public int getDasEqSeq() {
		return dasEqSeq;
	}
	public void setDasEqSeq(int dasEqSeq) {
		this.dasEqSeq = dasEqSeq;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}

}

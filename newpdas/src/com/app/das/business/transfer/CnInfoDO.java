package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 코너정보를 포함하고 있는 DataObject 
 * @author ysk523
 *
 */
public class CnInfoDO extends DTO 
{
	/** 
	 * 코너ID 
	 */
	private long cnId;         
	/** 
	 * 순번 
	 */
	private long seq;  
	/** 
	 * 시작점 
	 */       
	private String som  	= Constants.BLANK;
	/** 
	 * 종료점 
	 */
	private String eom     = Constants.BLANK;
	/** 
	 * 내용 
	 */
	private String cont              = Constants.BLANK;
	public long getCnId() {
		return cnId;
	}
	public void setCnId(long cnId) {
		this.cnId = cnId;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	
	
}

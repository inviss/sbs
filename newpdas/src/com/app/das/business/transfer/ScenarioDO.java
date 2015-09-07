package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 대본정보를 포함하고 있는 DataObject (Webservice 연동처리용)
 * @author Dekim
 *
 */
public class ScenarioDO extends DTO 
{
	/** 
	 * 마스터 ID 
	 */
	private long masterId;
	/** 
	 * 타이틀
	 */
	private String title	 	= 	Constants.BLANK;
	/** 
	 * 내용
	 */
	private String desc	 	= 	Constants.BLANK;
	/** 
	 * 순번
	 */
	private int seq;
	/** 
	 * 총수
	 */
	private int totalcount;
	/**
	 * 등록일시 
	 */
	private String regdt = Constants.BLANK;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRegdt() {
		return regdt;
	}
	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	
}

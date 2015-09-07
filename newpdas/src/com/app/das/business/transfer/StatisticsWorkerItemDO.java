package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * 통계 작업자 정보를 가지고있는 dataObject
 * @author asura207
 *
 */
public class StatisticsWorkerItemDO extends DTO 
{
	/**
	 * 작업자구분
	 */
	private String workerClf        = Constants.BLANK;
	/**
	 * 작업자구분명
	 */
	private String workerClfNm    = Constants.BLANK;
	/**
	 * 작업자ID
	 */
	private String workUserId	= Constants.BLANK;
	/**
	 * 소재
	 */
	private int item;
	/**
	 * 프로그램
	 */
	private int prgm;
	/**
	 * 드라마
	 */
	private int drama;
	/**
	 * 교양
	 */
	private int eductnal;
	/**
	 * 예능
	 */
	private int entertain;
	/**
	 * 기타
	 */
	private int extra;
	/**
	 * 참고자료
	 */
	private int ref;
	/**
	 * 합계
	 */
	private int total;
	public int getDrama() {
		return drama;
	}
	public void setDrama(int drama) {
		this.drama = drama;
	}
	public int getEductnal() {
		return eductnal;
	}
	public void setEductnal(int eductnal) {
		this.eductnal = eductnal;
	}
	public int getEntertain() {
		return entertain;
	}
	public void setEntertain(int entertain) {
		this.entertain = entertain;
	}
	public int getExtra() {
		return extra;
	}
	public void setExtra(int extra) {
		this.extra = extra;
	}
	public int getItem() {
		return item;
	}
	public void setItem(int item) {
		this.item = item;
	}
	public int getPrgm() {
		return prgm;
	}
	public void setPrgm(int prgm) {
		this.prgm = prgm;
	}
	public int getRef() {
		return ref;
	}
	public void setRef(int ref) {
		this.ref = ref;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getWorkerClf() {
		return workerClf;
	}
	public void setWorkerClf(String workerClf) {
		this.workerClf = workerClf;
	}
	public String getWorkUserId() {
		return workUserId;
	}
	public void setWorkUserId(String workUserId) {
		this.workUserId = workUserId;
	}
	public String getWorkerClfNm() {
		return workerClfNm;
	}
	public void setWorkerClfNm(String workerClfNm) {
		this.workerClfNm = workerClfNm;
	}
}

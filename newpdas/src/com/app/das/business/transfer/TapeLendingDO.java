package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 테이프 대출신청시에 대출 정보를 포함하고 있는 DataObject로서 
 * 테이프 Item 정보인 TapeLendingItemDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class TapeLendingDO extends DTO 
{
	/**
	 * 대출신청번호
	 */
	private String lendAplnNo = Constants.BLANK;
	/**
	 * 사번
	 */
	private String empNo      = Constants.BLANK;
	/**
	 * 대리인
	 */
	private String agnt       = Constants.BLANK;
	/**
	 * 대출신청일
	 */
	private String aplnDd     = Constants.BLANK;
	/**
	 * 대출용도
	 */
	private String purpose    = Constants.BLANK;
	/**
	 * 사용프로그램
	 */
	private String uPgm       = Constants.BLANK;
	/**
	 * 등록일시
	 */
	private String regDt      = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId     = Constants.BLANK;
	/**
	 * 수정일시
	 */
	private String modDt      = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId     = Constants.BLANK;
	
	/**
	 * 테이프대출 상세 List
	 */
	private List tapeLendingItemDOList = null;
	
	public TapeLendingDO()
	{
		tapeLendingItemDOList = new ArrayList();
	}
	
	public String getAgnt() {
		return agnt;
	}
	public void setAgnt(String agnt) {
		this.agnt = agnt;
	}
	public String getAplnDd() {
		return aplnDd;
	}
	public void setAplnDd(String aplnDd) {
		this.aplnDd = aplnDd;
	}
	public String getEmpNo() {
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getLendAplnNo() {
		return lendAplnNo;
	}
	public void setLendAplnNo(String lendAplnNo) {
		this.lendAplnNo = lendAplnNo;
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
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrId() {
		return regrId;
	}
	public void setRegrId(String regrId) {
		this.regrId = regrId;
	}
	public String getUPgm() {
		return uPgm;
	}
	public void setUPgm(String pgm) {
		uPgm = pgm;
	}

	public List getTapeLendingItemDOList() {
		return tapeLendingItemDOList;
	}

	public void setTapeLendingItemDOList(List tapeLendingItemDOList) {
		this.tapeLendingItemDOList = tapeLendingItemDOList;
	}

}

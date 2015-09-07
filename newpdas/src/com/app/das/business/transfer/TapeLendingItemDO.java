package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 테이프 대출 신청시 테이프 Item 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class TapeLendingItemDO extends DTO 
{
	/**
	 * 대출신청번호
	 */
	private String lendAplnNo = Constants.BLANK;
	/**
	 * 번호
	 */
	private int num;
	/**
	 * 테이프식별자ID
	 */
	private String tapeId 		= Constants.BLANK;
	/**
	 * 테이프아이템식별자 ID
	 */
	private String tapeItemId       = Constants.BLANK;
	/**
	 * 청구번호
	 */
	private String reqNo            = Constants.BLANK;
	/**
	 * 복본여부
	 */
	private String copyYn           = Constants.BLANK;
	/**
	 * 사용화면내용
	 */
	private String useImgCont       = Constants.BLANK;
	/**
	 * 등록일
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId           = Constants.BLANK;
	/**
	 * 수정일
	 */
	private String modDt            = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId           = Constants.BLANK;
	/**
	 * 장면제목
	 */
	private String scnTtl = Constants.BLANK;
	/**
	 * 사용등급코드
	 */
	private String useGradeCd = Constants.BLANK;
	/**
	 * 사용등급명
	 */
	private String useGradeNm = Constants.BLANK;
	
	public String getCopyYn() {
		return copyYn;
	}
	public void setCopyYn(String copyYn) {
		this.copyYn = copyYn;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getTapeId() {
		return tapeId;
	}
	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}
	public String getTapeItemId() {
		return tapeItemId;
	}
	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}
	public String getUseImgCont() {
		return useImgCont;
	}
	public void setUseImgCont(String useImgCont) {
		this.useImgCont = useImgCont;
	}
	public String getScnTtl() {
		return scnTtl;
	}
	public void setScnTtl(String scnTtl) {
		this.scnTtl = scnTtl;
	}
	public String getUseGradeCd() {
		return useGradeCd;
	}
	public void setUseGradeCd(String useGradeCd) {
		this.useGradeCd = useGradeCd;
	}
	public String getUseGradeNm() {
		return useGradeNm;
	}
	public void setUseGradeNm(String useGradeNm) {
		this.useGradeNm = useGradeNm;
	}

}

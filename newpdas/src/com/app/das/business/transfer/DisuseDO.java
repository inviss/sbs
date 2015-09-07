
package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 폐기 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class DisuseDO extends DTO 
{
	/**
	 * 폐기번호
	 */
	private BigDecimal disuseNo		= Constants.ZERO;
	/**
	 * 자료명(제목)
	 */
	private String dataNm           = Constants.BLANK;
	/**
	 * 메타데이타 마스타ID
	 */
	private String dataId           = Constants.BLANK;
	/**
	 * 자료구분코드
	 */
	private String dataClfCd        = Constants.BLANK;
	/**
	 * 폐기일
	 */
	private String disuseDd         = Constants.BLANK;
	/**
	 * 폐기사유
	 */
	private String disuseRsl        = Constants.BLANK;
	/**
	 * 폐기구분
	 */
	private String disuseClf        = Constants.BLANK;
	/**
	 * 등록일시
	 */
	private String regDt            = Constants.BLANK;
	/**
	 * 등록자ID
	 */
	private String regrId           = Constants.BLANK;
	/**
	 * 수정일시
	 */
	private String modDt            = Constants.BLANK;
	/**
	 * 수정자ID
	 */
	private String modrId           = Constants.BLANK;
	/**
	 * 연장사유
	 */
	private String extsRsl          = Constants.BLANK;
	/**
	 * 연장일자
	 */
	private String extsDt           = Constants.BLANK;
	/**
	 * 연장기간 코드
	 */
	private String extsCd = Constants.BLANK;
	/**
	 * 연장기간명
	 */
	private String extsNm = Constants.BLANK;
	/**
	 * 대분류코드
	 */
	private String ctgrLCd          = Constants.BLANK;
	/**
	 * 중분류코드
	 */
	private String ctgrMCd          = Constants.BLANK;
	/**
	 * 소분류코드
	 */
	private String ctgrSCd          = Constants.BLANK;
	/**
	 * 이용횟수
	 */
	private int useCount;
	/**
	 * 연간이용횟수
	 */
	private int yearUseCount;
	/**
	 * 폐기 1차 선정일
	 */
	private String disuseFstSltDd   = Constants.BLANK;
	/**
	 * 폐기위원검토완료일
	 */
	private String disuserRvDd      = Constants.BLANK;
	/**
	 * 데이타정보팀확정
	 */
	private String diConfirmDd      = Constants.BLANK;
	
	/**
	 * 보존기간코드
	 */
	private String rsvPrdCd         = Constants.BLANK;
	/**
	 * 보존기간코드명
	 */
	private String rsvPrdNm         = Constants.BLANK;
	/**
	 * 정리만료일
	 */
	private String arrgEndDt        = Constants.BLANK;
	/**
	 * 보존기간만료일
	 */
	private String rsvPrdEndDd	= Constants.BLANK;
	/**
	 * 폐기위원ID
	 */
	private String disuseUserId = Constants.BLANK;
	
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public String getDataClfCd() {
		return dataClfCd;
	}
	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getDataNm() {
		return dataNm;
	}
	public void setDataNm(String dataNm) {
		this.dataNm = dataNm;
	}
	public String getDiConfirmDd() {
		return diConfirmDd;
	}
	public void setDiConfirmDd(String diConfirmDd) {
		this.diConfirmDd = diConfirmDd;
	}
	public String getDisuseClf() {
		return disuseClf;
	}
	public void setDisuseClf(String disuseClf) {
		this.disuseClf = disuseClf;
	}
	public String getDisuseDd() {
		return disuseDd;
	}
	public void setDisuseDd(String disuseDd) {
		this.disuseDd = disuseDd;
	}
	public String getDisuseFstSltDd() {
		return disuseFstSltDd;
	}
	public void setDisuseFstSltDd(String disuseFstSltDd) {
		this.disuseFstSltDd = disuseFstSltDd;
	}
	public BigDecimal getDisuseNo() {
		return disuseNo;
	}
	public void setDisuseNo(BigDecimal disuseNo) {
		this.disuseNo = disuseNo;
	}
	public String getDisuserRvDd() {
		return disuserRvDd;
	}
	public void setDisuserRvDd(String disuserRvDd) {
		this.disuserRvDd = disuserRvDd;
	}
	public String getDisuseRsl() {
		return disuseRsl;
	}
	public void setDisuseRsl(String disuseRsl) {
		this.disuseRsl = disuseRsl;
	}
	public String getExtsDt() {
		return extsDt;
	}
	public void setExtsDt(String extsDt) {
		this.extsDt = extsDt;
	}
	public String getExtsRsl() {
		return extsRsl;
	}
	public void setExtsRsl(String extsRsl) {
		this.extsRsl = extsRsl;
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
	public int getUseCount() {
		return useCount;
	}
	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}
	public int getYearUseCount() {
		return yearUseCount;
	}
	public void setYearUseCount(int yearUseCount) {
		this.yearUseCount = yearUseCount;
	}
	public String getArrgEndDt() {
		return arrgEndDt;
	}
	public void setArrgEndDt(String arrgEndDt) {
		this.arrgEndDt = arrgEndDt;
	}
	public String getRsvPrdCd() {
		return rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd) {
		this.rsvPrdCd = rsvPrdCd;
	}
	public String getRsvPrdEndDd() {
		return rsvPrdEndDd;
	}
	public void setRsvPrdEndDd(String rsvPrdEndDd) {
		this.rsvPrdEndDd = rsvPrdEndDd;
	}
	public String getRsvPrdNm() {
		return rsvPrdNm;
	}
	public void setRsvPrdNm(String rsvPrdNm) {
		this.rsvPrdNm = rsvPrdNm;
	}
	public String getDisuseUserId() {
		return disuseUserId;
	}
	public void setDisuseUserId(String disuseUserId) {
		this.disuseUserId = disuseUserId;
	}
	public String getExtsCd() {
		return extsCd;
	}
	public void setExtsCd(String extsCd) {
		this.extsCd = extsCd;
	}
	public String getExtsNm() {
		return extsNm;
	}
	public void setExtsNm(String extsNm) {
		this.extsNm = extsNm;
	}
}

package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 관리정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class ManagementInfoDO extends DTO 
{
	/**
	 * 정리완료일
	 */
	private String arrgEndDt        = Constants.BLANK;
	/**
	 * 수집처코드
	 */
	private String gathCoCd         = Constants.BLANK;
	/**
	 * 데이타상태코드
	 */
	private String dataStatCd       = Constants.BLANK;
	/**
	 * 2차 아카이버명
	 */
	private String secArchNm        = Constants.BLANK;
	/**
	 * 아카이브등록
	 */
	private String archRegDd 	= Constants.BLANK;
	/**
	 * 보존기간 RSV_PRD_CD
	 */
	private String rsvPrdCd 	= Constants.BLANK;
	
	public String getRsvPrdCd() {
		return rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd) {
		this.rsvPrdCd = rsvPrdCd;
	}
	public String getArchRegDd() {
		return archRegDd;
	}
	public void setArchRegDd(String archRegDd) {
		this.archRegDd = archRegDd;
	}
	public String getArrgEndDt() {
		return arrgEndDt;
	}
	public void setArrgEndDt(String arrgEndDt) {
		this.arrgEndDt = arrgEndDt;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getGathCoCd() {
		return gathCoCd;
	}
	public void setGathCoCd(String gathCoCd) {
		this.gathCoCd = gathCoCd;
	}
	public String getSecArchNm() {
		return secArchNm;
	}
	public void setSecArchNm(String secArchNm) {
		this.secArchNm = secArchNm;
	}

}

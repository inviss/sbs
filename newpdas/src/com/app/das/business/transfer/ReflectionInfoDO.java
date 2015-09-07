package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 영상정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class ReflectionInfoDO extends DTO 
{
	/**
	 * 프로그램명
	 */
	private String pgmNm            = Constants.BLANK;
	/**
	 * 타이틀
	 */
	private String title            = Constants.BLANK;
	/**
	 * 방송일
	 */
	private String brdDd            = Constants.BLANK;
	/**
	 * 최종방송여부
	 */
	private String finalBrdYn       = Constants.BLANK;
	/**
	 * 방송시작시간
	 */
	private String brdBgnDd         = Constants.BLANK;
	/**
	 * 방송종료시간
	 */
	private String brdEndDd         = Constants.BLANK;
	/**
	 * 테이프매체종류코드
	 */
	private String tapeMediaClfCd   = Constants.BLANK;
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
	 * 청구번호코드
	 */
	private String reqCd		= Constants.BLANK;
	/**
	 * 콘텐츠ID
	 */
	private long ctId;
	/**
	 * 종횡비코드
	 */
	private String aspRtoCd         = Constants.BLANK; 
	/**
	 * 화질코드
	 */
	private String vdQlty    	= Constants.BLANK;
	/**
	 * 회차
	 */
	private int episNo;           
	/**
	 * 콘텐츠길이
	 */
	private String ctLeng    			= Constants.BLANK;         
	/**
	 * 키프레임경로
	 */
	private String kfrmPath                        = Constants.BLANK;
	/**
	 * 파일경
	 */
	private String flPath                          = Constants.BLANK;
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public String getBrdBgnDd() {
		return brdBgnDd;
	}
	public void setBrdBgnDd(String brdBgnDd) {
		this.brdBgnDd = brdBgnDd;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getBrdEndDd() {
		return brdEndDd;
	}
	public void setBrdEndDd(String brdEndDd) {
		this.brdEndDd = brdEndDd;
	}
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
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public String getCtLeng() {
		return ctLeng;
	}
	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
	}
	public int getEpisNo() {
		return episNo;
	}
	public void setEpisNo(int episNo) {
		this.episNo = episNo;
	}
	public String getFinalBrdYn() {
		return finalBrdYn;
	}
	public void setFinalBrdYn(String finalBrdYn) {
		this.finalBrdYn = finalBrdYn;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public String getTapeMediaClfCd() {
		return tapeMediaClfCd;
	}
	public void setTapeMediaClfCd(String tapeMediaClfCd) {
		this.tapeMediaClfCd = tapeMediaClfCd;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
}

package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 코너 정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용) 
 */
public class CornerInfoDO extends DTO 
{
	/**
	 * 콘텐츠ID
	 */
	private long ctId;
	/**
	 * 키프레임해상도코드
	 */
	private String kfrmPxCd	= Constants.BLANK;
	/**
	 * 화질코드
	 */
	private String vdQlty   = Constants.BLANK;
	/**
	 * 종횡비코드
	 */
	private String aspRtoCd = Constants.BLANK;
	/**
	 * 프로그램ID
	 */
	private long pgmId;
	/**
	 * 프로그램명
	 */
	private String pgmNm	= Constants.BLANK;
	/**
	 * 코너ID
	 */
	private long cnId;
	/**
	 * 코너명
	 */
	private String cnNm	= Constants.BLANK;
	/**
	 * 시작점
	 */
	private String som      = Constants.BLANK;
	/**
	 * 종료점
	 */
	private String eom      = Constants.BLANK;
	/**
	 * 코너유형코드
	 */
	private String cnTypeCd = Constants.BLANK;
	/**
	 * 코너정보
	 */
	private String cnInfo   = Constants.BLANK;
	/**
	 * 프레임갯수
	 */
	private long duration;
	/**
	 * 콘텐츠일련번호
	 */	
	private int ctSeq;
	
	/**
	 * 콘텐트 이스탄스 ID
	 */	
	private long ctiId;
	/**
	 * 코너 콘텐트 시작 길이
	 */	
	private long sDuration;
	/**
	 * 코너 콘텐트 종료  길이
	 */	
	private long eDuration;
	/**
	 * 대표화면 키프레임 일련번호
	 */	
	private int rpimgKfrmSeq;
	/**
	 * 타이틀
	 */	
	private String title	= Constants.BLANK;
	/**
	 * 키프레임 경로
	 */	
	private String kfrmPath	= Constants.BLANK;
	/**
	 * WMV 경로
	 */	
	private String flPath	= Constants.BLANK;
	/**
	 * 컨텐츠 이름
	 */	
	private String ctNm	= Constants.BLANK;
	/**
	 * 컨텐츠 내용
	 */	
	private String cont	= Constants.BLANK;
	/** 
	 * 마스터 ID 
	 */
	private    long       masterId;
	/** 
	 * 등록일 
	 */
	private    String       regDt     = Constants.BLANK;
	/** 
	 * 등록자  ID 
	 */
	private    String       regrid     = Constants.BLANK;
	/** 
	 * 키프레임 파일 리스트
	 */
	private    String       kfrmFileNm     = Constants.BLANK;
	/** 
	 * 대표화면 콘텐츠 ID
	 */
	private    long       rpimgCtId;
	/** 
	 * 시작 프레임
	 */
	private    long      sFrame;
	
	
	public long getSFrame() {
		return sFrame;
	}
	public void setSFrame(long sFrame) {
		this.sFrame = sFrame;
	}
	public long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	public String getKfrmFileNm() {
		return kfrmFileNm;
	}
	public void setKfrmFileNm(String kfrmFileNm) {
		this.kfrmFileNm = kfrmFileNm;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String geTtitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(int rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}
	public long getEDuration() {
		return eDuration;
	}
	public void setEDuration(long eDuration) {
		this.eDuration = eDuration;
	}
	public long getSDuration() {
		return sDuration;
	}
	public void setSDuration(long sDuration) {
		this.sDuration = sDuration;
	}
	public long getCtiId() {
		return ctiId;
	}
	public void setCtiId(long ctiId) {
		this.ctiId = ctiId;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}
	public long getCnId() {
		return cnId;
	}
	public void setCnId(long cnId) {
		this.cnId = cnId;
	}
	public String getCnInfo() {
		return cnInfo;
	}
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	public String getCnTypeCd() {
		return cnTypeCd;
	}
	public void setCnTypeCd(String cnTypeCd) {
		this.cnTypeCd = cnTypeCd;
	}
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public int getCtSeq() {
		return ctSeq;
	}
	public void setCtSeq(int ctSeq) {
		this.ctSeq = ctSeq;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getKfrmPxCd() {
		return kfrmPxCd;
	}
	public void setKfrmPxCd(String kfrmPxCd) {
		this.kfrmPxCd = kfrmPxCd;
	}
	public long getPgmId() {
		return pgmId;
	}
	public void setPgmId(long pgmId) {
		this.pgmId = pgmId;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	} 
	public long getMasterId()
	{
	   return  masterId;
	}
	public void setMasterId(long masterId)
	{
	   this.masterId = masterId;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}
	public String getRegrid()
	{
	   return  regrid;
	}
	public void setRegrid(String regrid)
	{
	   this.regrid = regrid;
	}

}

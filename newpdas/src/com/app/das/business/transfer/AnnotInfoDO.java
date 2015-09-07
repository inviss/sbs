package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;

/**
 * 주석정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class AnnotInfoDO extends DTO 
{
	/** 
	 * 코너ID 
	 */
	private long cnId;         
	/** 
	 * 콘텐츠 ID 
	 */
	private long ctId;  
	/** 
	 * 주석구분코드 
	 */       
	private String annotClfCd  	= Constants.BLANK;
	/** 
	 * 주석구분내용 
	 */
	private String annotClfCont     = Constants.BLANK;
	/** 
	 * 시작점 
	 */
	private String som              = Constants.BLANK;
	/** 
	 * 종료점 
	 */
	private String eom              = Constants.BLANK;
	/** 
	 * 주석정보 ID 
	 */
	private long annotId;
	/** 
	 * 마스터 ID 
	 */
	private long masterId;
	/** 
	 * 등록자 ID 
	 */
	private String regrId	 	= 	Constants.BLANK;
	/** 
	 * 등록일시
	 */
	private String regDt	 	= 	Constants.BLANK;
	/** 
	 * 기간
	 */
	private long duration;
	/** 
	 * 시작 프레임
	 */
	private    long      sFrame;
	
	/**
	 * 주제영상 :S , 사용제한등급:L ,기타:N
	 */
	private String gubun = Constants.BLANK;
	
	/**
	 * 전체영상여부
	 */
	private String entire_yn = Constants.BLANK;
	
	/**
	 * 전체영상구분코드
	 */
	private String entire_rist_clf_cd = Constants.BLANK;
	
	
	
	
	public long getsFrame() {
		return sFrame;
	}
	public void setsFrame(long sFrame) {
		this.sFrame = sFrame;
	}
	public String getEntire_yn() {
		return entire_yn;
	}
	public void setEntire_yn(String entireYn) {
		entire_yn = entireYn;
	}
	public String getEntire_rist_clf_cd() {
		return entire_rist_clf_cd;
	}
	public void setEntire_rist_clf_cd(String entireRistClfCd) {
		entire_rist_clf_cd = entireRistClfCd;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public long getSFrame() {
		return sFrame;
	}
	public void setSFrame(long sFrame) {
		this.sFrame = sFrame;
	}	
	public long getAnnotId() {
		return annotId;
	}
	public void setAnnotId(long annotId) {
		this.annotId = annotId;
	}
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public long getCnId() {
		return cnId;
	}
	public void setCnId(long cnId) {
		this.cnId = cnId;
	}
	public long getCtId() {
		return ctId;
	}
	public void setCtId(long ctId) {
		this.ctId = ctId;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public long getMasterId()
	{
	   return  masterId;
	}
	public void setMasterId(long masterId)
	{
	   this.masterId = masterId;
	}
	public String getRegrId()
	{
	   return  regrId;
	}
	public void setRegrId(String regrId)
	{
	   this.regrId = regrId;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}
	public long getDuration()
	{
	   return  duration;
	}
	public void setDuration(long duration)
	{
	   this.duration = duration;
	}
}

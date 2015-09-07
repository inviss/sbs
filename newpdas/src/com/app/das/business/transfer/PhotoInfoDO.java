package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
import java.util.ArrayList;
import java.util.List;
/**
 * 사진정보를 포함하고 있는 DataObject (ActiveX 와의 Webservice 연동처리용)
 * @author ysk523
 *
 */
public class PhotoInfoDO extends DTO 
{
	/**
	 * 파일경로
	 */
	private String flPath           = Constants.BLANK;
	
	/**
	 * 원파일명 
	 */
	private String  org_fl_nm           = Constants.BLANK;
	/**
	 * 서버 내부파일명 
	 */
	private String  int_fl_nm           = Constants.BLANK;
	/**
	 * 수집처코드
	 */
	private String gathCoCd         = Constants.BLANK;
	/**
	 * 수집구분코드
	 */
	private String gathClfCd        = Constants.BLANK;
	/**
	 * 미디어색상정보
	 */
	private String mediaColorInfo   = Constants.BLANK;
	/**
	 * 수집일시
	 */
	private String gathDd           = Constants.BLANK;
	/**
	 * 사진등록ID
	 */
	private long photRegId;
	/**
	 * 사진명
	 */
	private String cont             = Constants.BLANK;
	/**
	 * 특이사항
	 */
	private String spcInfo 		= Constants.BLANK;
	/**
	 * 사진 종류 코드
	 */
	private String photClfCd 		= Constants.BLANK;
	/**
	 * 마스터 id
	 */
	private long master_id;
	/**
	 * 등록자 id
	 */
	private String reg_id;
	/**
	 * 요청자 id
	 */
	private String req_id;
	/**
	 * 순번
	 */
	private    int       seq;
	/**
	 * 사진 ID
	 */
	private    long       photId;
	/**
	 * 프로그램 ID
	 */
	private    long       pgmId;
	/**
	 * 끝회차
	 */
	private    int       endEpn;
	/**
	 * 시작회차
	 */
	private    int       bgnEpn;
	/**
	 * 회차
	 */
	private    int       epis;
	/**
	 * 파일크기
	 */
	private    long       flie_size;
	/**
	 * 등록일
	 */
	private    String       regDt     = Constants.BLANK;
	
	
	private String master_ids             = Constants.BLANK;

	/**
	 *  해상도
	 */
	private String resoultion        = Constants.BLANK;

	/**
	 * 다운로드 금지여부
	 */
	private String down_yn           = Constants.BLANK;
	/**
	 * 저작권자
	 */
	private String cprtr_nm = Constants.BLANK;

	/**
	 * 프로그램 제목
	 */
	private String title 		= Constants.BLANK;


	
	/**
	 * 방송일
	 */
	private    String      brd_dd  = Constants.BLANK;
	/**
	 * 방송일
	 */
	private    String      Start_brd_dd  = Constants.BLANK;
	/**
	 * 방송일
	 */
	private    String      end_brd_dd  = Constants.BLANK;
	
	
	/**
	 * 길이
	 */
	private    String       author     = Constants.BLANK;
	
	private    String      photRegIdS     = Constants.BLANK;
	
private    String       ctgr_l_cd     = Constants.BLANK;
	
	private    String      ctgr_m_cd     = Constants.BLANK;
private    String       ctgr_s_cd     = Constants.BLANK;
	
/**
 * 사진크기
 */
private    String       fl_sz     = Constants.BLANK;

	


	public long getFlie_size() {
	return flie_size;
}
public void setFlie_size(long flieSize) {
	flie_size = flieSize;
}
	public String getFl_sz() {
	return fl_sz;
}
public void setFl_sz(String flSz) {
	fl_sz = flSz;
}
	public String getCtgr_l_cd() {
	return ctgr_l_cd;
}
public void setCtgr_l_cd(String ctgrLCd) {
	ctgr_l_cd = ctgrLCd;
}
public String getReg_id() {
	return reg_id;
}
public void setReg_id(String regId) {
	reg_id = regId;
}
public void setReq_id(String reqId) {
	req_id = reqId;
}
public String getCtgr_m_cd() {
	return ctgr_m_cd;
}
public void setCtgr_m_cd(String ctgrMCd) {
	ctgr_m_cd = ctgrMCd;
}
public String getCtgr_s_cd() {
	return ctgr_s_cd;
}
public void setCtgr_s_cd(String ctgrSCd) {
	ctgr_s_cd = ctgrSCd;
}
	public String getPhotRegIdS() {
		return photRegIdS;
	}
	public void setPhotRegIdS(String photRegIdS) {
		this.photRegIdS = photRegIdS;
	}
	public String getStart_brd_dd() {
		return Start_brd_dd;
	}
	public void setStart_brd_dd(String startBrdDd) {
		Start_brd_dd = startBrdDd;
	}
	public String getEnd_brd_dd() {
		return end_brd_dd;
	}

	
	public String getReq_id() {
		return req_id;
	}
	public String getMaster_ids() {
		return master_ids;
	}
	public void setMaster_ids(String masterIds) {
		master_ids = masterIds;
	}
	public void setEnd_brd_dd(String endBrdDd) {
		end_brd_dd = endBrdDd;
	}
	public String getPhotClfCd() {
		return photClfCd;
	}
	public int getEpis() {
		return epis;
	}
	public void setEpis(int epis) {
		this.epis = epis;
	}
	public void setPhotClfCd(String photClfCd) {
		this.photClfCd = photClfCd;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getGathClfCd() {
		return gathClfCd;
	}
	public void setGathClfCd(String gathClfCd) {
		this.gathClfCd = gathClfCd;
	}
	public String getGathCoCd() {
		return gathCoCd;
	}
	public void setGathCoCd(String gathCoCd) {
		this.gathCoCd = gathCoCd;
	}
	public String getGathDd() {
		return gathDd;
	}
	public void setGathDd(String gathDd) {
		this.gathDd = gathDd;
	}
	public String getMediaColorInfo() {
		return mediaColorInfo;
	}
	public void setMediaColorInfo(String mediaColorInfo) {
		this.mediaColorInfo = mediaColorInfo;
	}
	public long getPhotRegId() {
		return photRegId;
	}
	public void setPhotRegId(long photRegId) {
		this.photRegId = photRegId;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public int getSeq()
	{
	   return  seq;
	}
	public void setSeq(int seq)
	{
	   this.seq = seq;
	}
	public long getPhotId()
	{
	   return  photId;
	}
	public void setPhotId(long photId)
	{
	   this.photId = photId;
	}
	public long getPgmId()
	{
	   return  pgmId;
	}
	public void setPgmId(long pgmId)
	{
	   this.pgmId = pgmId;
	}
	public int getEndEpn()
	{
	   return  endEpn;
	}
	public void setEndEpn(int endEpn)
	{
	   this.endEpn = endEpn;
	}
	public int getBgnEpn()
	{
	   return  bgnEpn;
	}
	public void setBgnEpn(int bgnEpn)
	{
	   this.bgnEpn = bgnEpn;
	}
	public String getRegDt()
	{
	   return  regDt;
	}
	public void setRegDt(String regDt)
	{
	   this.regDt = regDt;
	}
	
	
	

	public String getResoultion() {
		return resoultion;
	}
	public void setResoultion(String resoultion) {
		this.resoultion = resoultion;
	}
	public String getDown_yn() {
		return down_yn;
	}
	public void setDown_yn(String downYn) {
		down_yn = downYn;
	}
	public String getCprtr_nm() {
		return cprtr_nm;
	}
	public void setCprtr_nm(String cprtrNm) {
		cprtr_nm = cprtrNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public String getBrd_dd() {
		return brd_dd;
	}
	public void setBrd_dd(String brdDd) {
		brd_dd = brdDd;
	}
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOrg_fl_nm() {
		return org_fl_nm;
	}
	public void setOrg_fl_nm(String orgFlNm) {
		org_fl_nm = orgFlNm;
	}
	public String getInt_fl_nm() {
		return int_fl_nm;
	}
	public void setInt_fl_nm(String intFlNm) {
		int_fl_nm = intFlNm;
	}
}

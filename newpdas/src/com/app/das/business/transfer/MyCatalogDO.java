package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 내목록에 대한 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class MyCatalogDO extends DTO 
{
	/**
	 * 일련번호
	 */
	private long serialNo;
	/**
	 * 요청자ID
	 */
	private String reqUsrId         = Constants.BLANK;
	/**
	 * 순번
	 */
	private long seq;
	/**
	 * 마스타ID
	 */
	private long masterId;
	/**
	 * 코너ID
	 */
	private long cnId;
	/**
	 * 프로그램ID
	 */
	private long pgmId;
	/**
	 * 프로그램제목
	 */
	private String pgmNm            = Constants.BLANK;
	/**
	 * 회차
	 */
	private String epn = Constants.BLANK;;
	/**
	 * 회차
	 */
	private  String epis_no            = Constants.BLANK;
	/**
	 * 타이틀
	 */
	private String title            = Constants.BLANK;
	/**
	 * 방송일자
	 */
	private String brdDd            = Constants.BLANK;
	/**
	 * 방송길이
	 */
	private String brdLeng          = Constants.BLANK;
	/**
	 * 코너길이
	 */
	private String cnLeng           = Constants.BLANK;
	/**
	 * 주석구분코드
	 */
	private String annotClfCd       = Constants.BLANK;
	/**
	 * 내용
	 */
	private String cont             = Constants.BLANK;
	/**
	 * 대표화면
	 */
	private int rpImg;
	/**
	 * 명장면
	 */
	private String goodSc           = Constants.BLANK;
	/**
	 * 사용금지
	 */
	private String notUse           = Constants.BLANK;
	/**
	 * 심의지적
	 */
	private String dilbrt           = Constants.BLANK;
	/**
	 * 확인 후 사용
	 */
	private String check            = Constants.BLANK;
	/**
	 * 키프레임경로(대표사진 파일 경로)
	 */
	private String kfrmPath = Constants.BLANK;
	/**
	 * 키프레임일련번호
	 */
	private int kfrmSeq;
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
	private String modrId		= Constants.BLANK;
	/**
	 * 대표화면 콘텐츠ID
	 */	
	private long rpimgCtId;
	/**
	 * 비디오 상태 유형
	 */	
	private String vdQlty	= Constants.BLANK;
	/**
	 * 비디오 비율
	 */	
	private String aspRtoCd	= Constants.BLANK;
	/**
	 * 편성명
	 */
	private String schd_pgm_nm = Constants.BLANK;
	/**
	 * 파일럿
	 */
	private String pilot_yn = Constants.BLANK;
	/**
	 * 부제
	 */
	private String sub_ttl = Constants.BLANK;
	/**
	 * 방송요일
	 */
	private String day = Constants.BLANK;
	/**
	 * 내용
	 */
	private String snps = Constants.BLANK;
	/**
	 * 최종회 여부 
	 */
	private String final_brd_yn = Constants.BLANK;
	
	/**
	 * 삭제 순번 그룹(예: 16,17)
	 */
	private String del_seq = Constants.BLANK;
	/**
	 * 구분
	 */
	private String gubun = Constants.BLANK;
	/**
	 * 전체건수
	 */
	private int totalCount;
	
	/**
	 * 아카이브 등록여부
	 */
	private String arch_reg_dd = Constants.BLANK;
	
	
	public String getEpis_no() {
		return epis_no;
	}
	public void setEpis_no(String episNo) {
		epis_no = episNo;
	}
	public String getArch_reg_dd() {
		return arch_reg_dd;
	}
	public void setArch_reg_dd(String archRegDd) {
		arch_reg_dd = archRegDd;
	}
	public int getKfrmSeq() {
		return kfrmSeq;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getFinal_brd_yn() {
		return final_brd_yn;
	}
	public void setFinal_brd_yn(String final_brd_yn) {
		this.final_brd_yn = final_brd_yn;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getPilot_yn() {
		return pilot_yn;
	}
	public void setPilot_yn(String pilot_yn) {
		this.pilot_yn = pilot_yn;
	}
	public String getSchd_pgm_nm() {
		return schd_pgm_nm;
	}
	public void setSchd_pgm_nm(String schd_pgm_nm) {
		this.schd_pgm_nm = schd_pgm_nm;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
	}
	public String getSub_ttl() {
		return sub_ttl;
	}
	public void setSub_ttl(String sub_ttl) {
		this.sub_ttl = sub_ttl;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getAspRtoCd() {
		return aspRtoCd;
	}
	public void setAspRtoCd(String aspRtoCd) {
		this.aspRtoCd = aspRtoCd;
	}	
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public long getCnId() {
		return cnId;
	}
	public void setCnId(long cnId) {
		this.cnId = cnId;
	}
	public String getCnLeng() {
		return cnLeng;
	}
	public void setCnLeng(String cnLeng) {
		this.cnLeng = cnLeng;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getDilbrt() {
		return dilbrt;
	}
	public void setDilbrt(String dilbrt) {
		this.dilbrt = dilbrt;
	}

	public String getGoodSc() {
		return goodSc;
	}
	public void setGoodSc(String goodSc) {
		this.goodSc = goodSc;
	}
	public long getMasterId() {
		return masterId;
	}
	public void setMasterId(long masterId) {
		this.masterId = masterId;
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
	public String getNotUse() {
		return notUse;
	}
	public void setNotUse(String notUse) {
		this.notUse = notUse;
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
	public String getReqUsrId() {
		return reqUsrId;
	}
	public void setReqUsrId(String reqUsrId) {
		this.reqUsrId = reqUsrId;
	}

	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(long serialNo) {
		this.serialNo = serialNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}


	
	public String getEpn() {
		return epn;
	}
	public void setEpn(String epn) {
		this.epn = epn;
	}
	public int getRpImg() {
		return rpImg;
	}
	public void setRpImg(int rpImg) {
		this.rpImg = rpImg;
	}
	public void setKfrmSeq(int kfrmSeq) {
		this.kfrmSeq = kfrmSeq;
	}
	public long getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(long rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	public String getDel_seq() {
		return del_seq;
	}
	public void setDel_seq(String del_seq) {
		this.del_seq = del_seq;
	}
}

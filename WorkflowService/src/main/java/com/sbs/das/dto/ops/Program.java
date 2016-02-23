package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("pgm")
public class Program {

	// DAS프로그램 코드
	@XStreamAlias("das_pgm_cd")
	private String dasPgmCd;

	// 프로그램제목
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_title")
	private String pgmTitle;

	// 줄거리
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_synop")
	private String pgmSynop;

	// 출연자
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cast_nm")
	private String castNm;

	// 방송요일
	@XStreamAlias("brad_dow")
	private String bradDow;

	// 대표 프로그램 여부
	@XStreamAlias("rep_pgm_yn")
	private String repPgmYn;

	// 홈페이이지 주소
	@XStreamAlias("hpage_adr")
	private String hpageAdr;

	// 방송 시작 일자
	@XStreamAlias("brad_st_date")
	private String bradStDate;

	// 방송 종료 일자
	@XStreamAlias("brad_fns_date")
	private String bradFnsDate;

	// 방송 완료 여부
	@XStreamAlias("brad_cmplt_yn")
	private String bradCmpltYn;

	// 프로그램 권리
	@XStreamAlias("pgm_rght")
	private String pgmRght;

	// 미디어 코드
	@XStreamAlias("media_cd")
	private String mediaCd;

	// 파일럿 여부
	@XStreamAlias("pilot_yn")
	private String pilotYn;

	// 대분류 코드
	@XStreamAlias("large_ctg_cd")
	private String largeCtgCd;

	// 중분류 코드
	@XStreamAlias("mid_ctg_cd")
	private String midCtgCd;

	// 소분류 코드
	@XStreamAlias("small_ctg_cd")
	private String smallCtgCd;

	// 특이사항
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pclr_mtr")
	private String pclrMtr;

	// 사용여부
	@XStreamAlias("use_yn")
	private String useYn;

	// 편성명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("frmtn_nm")
	private String frmtnNm;

	// 수상내역
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("award_txn")
	private String awardTxn;

	// 검수코드
	@XStreamAlias("actc_cd")
	private String actcCd;

	// 제작부서명
	@XStreamAlias("dept_nm")
	private String deptNm;


	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getDasPgmCd() {
		return dasPgmCd;
	}

	public void setDasPgmCd(String dasPgmCd) {
		this.dasPgmCd = dasPgmCd;
	}

	public String getPgmTitle() {
		return pgmTitle;
	}

	public void setPgmTitle(String pgmTitle) {
		this.pgmTitle = pgmTitle;
	}

	public String getPgmSynop() {
		return pgmSynop;
	}

	public void setPgmSynop(String pgmSynop) {
		this.pgmSynop = pgmSynop;
	}

	public String getCastNm() {
		return castNm;
	}

	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}

	public String getBradDow() {
		return bradDow;
	}

	public void setBradDow(String bradDow) {
		this.bradDow = bradDow;
	}

	public String getRepPgmYn() {
		return repPgmYn;
	}

	public void setRepPgmYn(String repPgmYn) {
		this.repPgmYn = repPgmYn;
	}

	public String getHpageAdr() {
		return hpageAdr;
	}

	public void setHpageAdr(String hpageAdr) {
		this.hpageAdr = hpageAdr;
	}

	public String getBradStDate() {
		return bradStDate;
	}

	public void setBradStDate(String bradStDate) {
		this.bradStDate = bradStDate;
	}

	public String getBradFnsDate() {
		return bradFnsDate;
	}

	public void setBradFnsDate(String bradFnsDate) {
		this.bradFnsDate = bradFnsDate;
	}

	public String getBradCmpltYn() {
		return bradCmpltYn;
	}

	public void setBradCmpltYn(String bradCmpltYn) {
		this.bradCmpltYn = bradCmpltYn;
	}

	public String getPgmRght() {
		return pgmRght;
	}

	public void setPgmRght(String pgmRght) {
		this.pgmRght = pgmRght;
	}

	public String getMediaCd() {
		return mediaCd;
	}

	public void setMediaCd(String mediaCd) {
		this.mediaCd = mediaCd;
	}

	public String getPilotYn() {
		return pilotYn;
	}

	public void setPilotYn(String pilotYn) {
		this.pilotYn = pilotYn;
	}

	public String getLargeCtgCd() {
		return largeCtgCd;
	}

	public void setLargeCtgCd(String largeCtgCd) {
		this.largeCtgCd = largeCtgCd;
	}

	public String getMidCtgCd() {
		return midCtgCd;
	}

	public void setMidCtgCd(String midCtgCd) {
		this.midCtgCd = midCtgCd;
	}

	public String getSmallCtgCd() {
		return smallCtgCd;
	}

	public void setSmallCtgCd(String smallCtgCd) {
		this.smallCtgCd = smallCtgCd;
	}

	public String getPclrMtr() {
		return pclrMtr;
	}

	public void setPclrMtr(String pclrMtr) {
		this.pclrMtr = pclrMtr;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getFrmtnNm() {
		return frmtnNm;
	}

	public void setFrmtnNm(String frmtnNm) {
		this.frmtnNm = frmtnNm;
	}

	public String getAwardTxn() {
		return awardTxn;
	}

	public void setAwardTxn(String awardTxn) {
		this.awardTxn = awardTxn;
	}

	public String getActcCd() {
		return actcCd;
	}

	public void setActcCd(String actcCd) {
		this.actcCd = actcCd;
	}


}

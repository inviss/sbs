package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.sbs.das.commons.utils.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("metadata")
public class Metadata {

	// DAS 마스터 아이디
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("das_master_id")
	private Long dasMasterId;

	// 프로그램 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_pgm_cd")
	private String dasPgmCd;

	// 회차
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("pgm_tms")
	private Integer pgmTms;

	// 프로그램회별제목
	//@XStreamCDATA
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_tms_title")
	private String pgmTmsTitle;

	// 방송일
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_day")
	private String bradDay;

	// 방송길이
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("brad_len")
	private Long bradLen;

	// 방송 시작 시간
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_st_time")
	private String bradStTime;

	// 방송 종료 시간
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_fns_time")
	private String bradFnsTime;

	// 사용등급
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("use_clas")
	private String useClas;

	// 프로그램 권리
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("pgm_rght")
	private String pgmRght;

	// 줄거리
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_tms_synop")
	private String pgmTmsSynop;

	// 대분류 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("large_ctg_cd")
	private String largeCtgCd;

	// 중분류 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("mid_ctg_cd")
	private String midCtgCd;

	// 소분류 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("small_ctg_cd")
	private String smallCtgCd;

	// 연출자명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("drt_prsn_nm")
	private String drtPrsnNm;

	// 프로듀서명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("producer_nm")
	private String producerNm;

	// 작가명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("wrtr_nm")
	private String wrtrNm;

	// 제작구분코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("mnfc_div_cd")
	private String mnfcDivCd;

	// 제작부서코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("mnfc_dept_cd")
	private String mnfcDeptCd;

	// 원제작자명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("orgin_mnfc_nm")
	private String orginMnfcNm;

	// 진행자명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("progr_prsn_nm")
	private String progrPrsnNm;

	// 출연자
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cast_nm")
	private String castNm;

	// 출연감독명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cast_dirt_nm")
	private String castDirtNm;

	// 촬영일
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ptgh_day")
	private String ptghDay;

	// 촬영장소
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("ptgh_plc")
	private String ptghPlc;

	// 저작권자명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cpyrt_prsn_nm")
	private String cpyrtPrsnNm;

	// 저작권형태코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cpyrt_shap_cd")
	private String cpyrtShapCd;

	// 저작권형태 설명
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cpyrt_shap_desc")
	private String cpyrtShapDesc;

	// 시청등급코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("viwr_clas_cd")
	private String viwrClasCd;

	// 심의결과 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("dlb_reslt_cd")
	private String dlbResltCd;

	// 수상내역
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("award_txn")
	private String awardTxn;

	// 특이사항
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pclr_mtr")
	private String pclrMtr;

	// 사용여부
	@XStreamAlias("use_yn")
	private String useYn;

	// 음악정보
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("music_info")
	private String musicInfo;

	// 회사코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cmpn_cd")
	private String cmpnCd;

	// 국가 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cntry_cd")
	private String cntryCd;

	// 아티스트
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("artist")
	private String artist;

	// 채널ID
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ch_id")
	private String chId;

	// 재방송
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("agn_brad")
	private String agnBrad;

	// 제한사항
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("rstrtn_mtr")
	private String rstrtnMtr;

	// 부제
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("sub_title")
	private String subTitle;
	
	// 부제
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("frmtn_nm")
	private String frmtnNm;

	// 검수코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("actc_cd")
	private String actcCd;
	
	// 등록자
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	// 아카이브 코드
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("obj_name")
	private String objName;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("file_nm")
	private String filename;

	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getObjName() {
		return objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getFrmtnNm() {
		return frmtnNm;
	}

	public void setFrmtnNm(String frmtnNm) {
		this.frmtnNm = frmtnNm;
	}

	public Long getDasMasterId() {
		return dasMasterId;
	}

	public void setDasMasterId(Long dasMasterId) {
		this.dasMasterId = dasMasterId;
	}

	public String getDasPgmCd() {
		return dasPgmCd;
	}

	public void setDasPgmCd(String dasPgmCd) {
		this.dasPgmCd = dasPgmCd;
	}

	public Integer getPgmTms() {
		return pgmTms;
	}

	public void setPgmTms(Integer pgmTms) {
		this.pgmTms = pgmTms;
	}

	public String getPgmTmsTitle() {
		return pgmTmsTitle;
	}

	public void setPgmTmsTitle(String pgmTmsTitle) {
		this.pgmTmsTitle = pgmTmsTitle;
	}

	public String getBradDay() {
		return bradDay;
	}

	public void setBradDay(String bradDay) {
		this.bradDay = bradDay;
	}

	public Long getBradLen() {
		return bradLen;
	}

	public void setBradLen(Long bradLen) {
		this.bradLen = bradLen;
	}

	public String getBradStTime() {
		return bradStTime;
	}

	public void setBradStTime(String bradStTime) {
		this.bradStTime = bradStTime;
	}

	public String getBradFnsTime() {
		return bradFnsTime;
	}

	public void setBradFnsTime(String bradFnsTime) {
		this.bradFnsTime = bradFnsTime;
	}

	public String getUseClas() {
		return useClas;
	}

	public void setUseClas(String useClas) {
		this.useClas = useClas;
	}

	public String getPgmRght() {
		return pgmRght;
	}

	public void setPgmRght(String pgmRght) {
		this.pgmRght = pgmRght;
	}

	public String getPgmTmsSynop() {
		return pgmTmsSynop;
	}

	public void setPgmTmsSynop(String pgmTmsSynop) {
		this.pgmTmsSynop = pgmTmsSynop;
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

	public String getDrtPrsnNm() {
		return drtPrsnNm;
	}

	public void setDrtPrsnNm(String drtPrsnNm) {
		this.drtPrsnNm = drtPrsnNm;
	}

	public String getProducerNm() {
		return producerNm;
	}

	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}

	public String getWrtrNm() {
		return wrtrNm;
	}

	public void setWrtrNm(String wrtrNm) {
		this.wrtrNm = wrtrNm;
	}

	public String getMnfcDivCd() {
		return mnfcDivCd;
	}

	public void setMnfcDivCd(String mnfcDivCd) {
		this.mnfcDivCd = mnfcDivCd;
	}

	public String getMnfcDeptCd() {
		return mnfcDeptCd;
	}

	public void setMnfcDeptCd(String mnfcDeptCd) {
		this.mnfcDeptCd = mnfcDeptCd;
	}

	public String getOrginMnfcNm() {
		return orginMnfcNm;
	}

	public void setOrginMnfcNm(String orginMnfcNm) {
		this.orginMnfcNm = orginMnfcNm;
	}

	public String getProgrPrsnNm() {
		return progrPrsnNm;
	}

	public void setProgrPrsnNm(String progrPrsnNm) {
		this.progrPrsnNm = progrPrsnNm;
	}

	public String getCastNm() {
		return castNm;
	}

	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}

	public String getCastDirtNm() {
		return castDirtNm;
	}

	public void setCastDirtNm(String castDirtNm) {
		this.castDirtNm = castDirtNm;
	}

	public String getPtghDay() {
		return ptghDay;
	}

	public void setPtghDay(String ptghDay) {
		this.ptghDay = ptghDay;
	}

	public String getPtghPlc() {
		return ptghPlc;
	}

	public void setPtghPlc(String ptghPlc) {
		this.ptghPlc = ptghPlc;
	}

	public String getCpyrtPrsnNm() {
		return cpyrtPrsnNm;
	}

	public void setCpyrtPrsnNm(String cpyrtPrsnNm) {
		this.cpyrtPrsnNm = cpyrtPrsnNm;
	}

	public String getCpyrtShapCd() {
		return cpyrtShapCd;
	}

	public void setCpyrtShapCd(String cpyrtShapCd) {
		this.cpyrtShapCd = cpyrtShapCd;
	}

	public String getCpyrtShapDesc() {
		return cpyrtShapDesc;
	}

	public void setCpyrtShapDesc(String cpyrtShapDesc) {
		this.cpyrtShapDesc = cpyrtShapDesc;
	}

	public String getViwrClasCd() {
		return viwrClasCd;
	}

	public void setViwrClasCd(String viwrClasCd) {
		this.viwrClasCd = viwrClasCd;
	}

	public String getDlbResltCd() {
		return dlbResltCd;
	}

	public void setDlbResltCd(String dlbResltCd) {
		this.dlbResltCd = dlbResltCd;
	}

	public String getAwardTxn() {
		return awardTxn;
	}

	public void setAwardTxn(String awardTxn) {
		this.awardTxn = awardTxn;
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

	public String getMusicInfo() {
		return musicInfo;
	}

	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}

	public String getCmpnCd() {
		return cmpnCd;
	}

	public void setCmpnCd(String cmpnCd) {
		this.cmpnCd = cmpnCd;
	}

	public String getCntryCd() {
		return cntryCd;
	}

	public void setCntryCd(String cntryCd) {
		this.cntryCd = cntryCd;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getChId() {
		return chId;
	}

	public void setChId(String chId) {
		this.chId = chId;
	}

	public String getAgnBrad() {
		return agnBrad;
	}

	public void setAgnBrad(String agnBrad) {
		this.agnBrad = agnBrad;
	}

	public String getRstrtnMtr() {
		return rstrtnMtr;
	}

	public void setRstrtnMtr(String rstrtnMtr) {
		this.rstrtnMtr = rstrtnMtr;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getActcCd() {
		return actcCd;
	}

	public void setActcCd(String actcCd) {
		this.actcCd = actcCd;
	}
	
}

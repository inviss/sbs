package com.sbs.das.dto;

public class MediaTapeInfoTbl extends BaseObject {

	private static final long serialVersionUID = -8267190577015447864L;
	
	private String chennelCd;					// CHAR(1) 	채널코드
	private String ctgrLCd;						// CHAR(3)	자료구분
	private String tapeMediaClfCd;				// CHAR(1)	테이프 종료코드
	private String	tapeLeng;					// VARCHAR(11) 테이프 길이
	private String reqCd;						// VARCHAR(9)  청구번호
	private Long	sceanNo;					// BIGINT	장면번호
	private String title;						// VARCHAR(300)	제목
	private String subTtl;						// VARCHAR(300) 부제
	private Long	episNo;						// BIGINT	회차
	private String brdDd;						// VARCHAR(8) 방송일
	private String cmrPlace;					// VARCHAR(600) 촬영장소
	private String vdQlty;						// CHAR(3) 화질코드
	private String viewGrCd;					// CHAR(3) 시청등급
	private String cprtType;					// CHAR(3) 저작권형태
	private String cprtTypeDsc;					// VARCHAR 저작권형태 설명
	private String cprtNm;						// 저작권자
	private String recordTypeCd;				// 녹음방식
	private String ristClfCd;					// 사용등급코드
	private String 	ristClfRange;				// 사용범위
	private String rsvPrdCd;					// 보존기간
	private String prdtInOutsCd;				// 제작구분
	private String orgPrdrNm;					// 외주 제작사
	private String cont;						// 자료내용
	private String spcInfo;						// 특이사항
	private String artist;						// 아티스트
	private String ctgrMCd;						// 중분류
	private String ctgrSCd;						// 소분류
	private String countryCd;					// 국가구분코드
	private String keyWords;					// 키워드
	private String drtNm;						// 연출자
	private String cmrDrtNm;					// 촬영감독
	private String mcNm;						// 진행자
	private String writerNm;					// 작가명
	private String ctCla;						// 컨텐츠 유형
	private Long seq;							// 시퀀스 번호
	private String gubun;						// 구분코드
	private String castNm;						// 출연자
	private String producerNm;					// 프로듀서명
	private String prdtDeptNm;					// 제작부서명
	
	
	public String getPrdtDeptNm() {
		return prdtDeptNm;
	}
	public void setPrdtDeptNm(String prdtDeptNm) {
		this.prdtDeptNm = prdtDeptNm;
	}
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	public String getCastNm() {
		return castNm;
	}
	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}
	public String getChennelCd() {
		return chennelCd;
	}
	public void setChennelCd(String chennelCd) {
		this.chennelCd = chennelCd;
	}
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	public String getTapeMediaClfCd() {
		return tapeMediaClfCd;
	}
	public void setTapeMediaClfCd(String tapeMediaClfCd) {
		this.tapeMediaClfCd = tapeMediaClfCd;
	}
	public String getTapeLeng() {
		return tapeLeng;
	}
	public void setTapeLeng(String tapeLeng) {
		this.tapeLeng = tapeLeng;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public Long getSceanNo() {
		return sceanNo;
	}
	public void setSceanNo(Long sceanNo) {
		this.sceanNo = sceanNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public Long getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Long episNo) {
		this.episNo = episNo;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	public String getVdQlty() {
		return vdQlty;
	}
	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getCprtTypeDsc() {
		return cprtTypeDsc;
	}
	public void setCprtTypeDsc(String cprtTypeDsc) {
		this.cprtTypeDsc = cprtTypeDsc;
	}
	public String getCprtNm() {
		return cprtNm;
	}
	public void setCprtNm(String cprtNm) {
		this.cprtNm = cprtNm;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getRistClfRange() {
		return ristClfRange;
	}
	public void setRistClfRange(String ristClfRange) {
		this.ristClfRange = ristClfRange;
	}
	public String getRsvPrdCd() {
		return rsvPrdCd;
	}
	public void setRsvPrdCd(String rsvPrdCd) {
		this.rsvPrdCd = rsvPrdCd;
	}
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	public String getCont() {
		return cont;
	}
	public void setCont(String cont) {
		this.cont = cont;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
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
	public String getCountryCd() {
		return countryCd;
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getDrtNm() {
		return drtNm;
	}
	public void setDrtNm(String drtNm) {
		this.drtNm = drtNm;
	}
	public String getCmrDrtNm() {
		return cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm) {
		this.cmrDrtNm = cmrDrtNm;
	}
	public String getMcNm() {
		return mcNm;
	}
	public void setMcNm(String mcNm) {
		this.mcNm = mcNm;
	}
	public String getWriterNm() {
		return writerNm;
	}
	public void setWriterNm(String writerNm) {
		this.writerNm = writerNm;
	}
	public String getCtCla() {
		return ctCla;
	}
	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}
	public Long getSeq() {
		return seq;
	}
	public void setSeq(Long seq) {
		this.seq = seq;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	
	
}

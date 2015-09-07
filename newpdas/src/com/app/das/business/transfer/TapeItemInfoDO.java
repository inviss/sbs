package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;
/**
 * tape 아이템 정보를 포함하고있는 datdObject
 * @author asura207
 *
 */
public class TapeItemInfoDO extends DTO 
{
	
	/**
	 * 테이프아이템식별자ID
	 */
	private String tapeItemId = Constants.BLANK;
	/**
	 * 테이프식별자ID
	 */
	private String tapeId = Constants.BLANK;
	/**
	 * 장면갯수
	 */
	private int scnCnt;
	/**
	 * 테이프구분
	 */
	private String tapeClf = Constants.BLANK;
	/**
	 * 장면번호
	 */
	private int scnNo;
	/**
	 * 청구번호
	 */
	private String reqNo = Constants.BLANK;
	/**
	 * 장면제목(프로그램명)
	 */
	private String scnTtl = Constants.BLANK;
	/**
	 * 부제
	 */
	private String subTtl = Constants.BLANK;
	/**
	 * 보고자
	 */
	private String rptr = Constants.BLANK;
	/**
	 * 촬영자
	 */
	private String cmrMan = Constants.BLANK;
	/**
	 * 보고자부서
	 */
	private String deptCd = Constants.BLANK;
	/**
	 * 촬영장소
	 */
	private String cmrPlace = Constants.BLANK;
	/**
	 * 촬영일자(연월일)
	 */
	private String cmrDd = Constants.BLANK;
	/**
	 * 제한사항
	 */
	private String rstCont = Constants.BLANK;
	/**
	 * 길이(방송길이)
	 */
	private int len;
	/**
	 * 출연
	 */
	private String casting = Constants.BLANK;
	/**
	 * 원제
	 */
	private String orgTtl = Constants.BLANK;
	/**
	 * 제작회사
	 */
	private String prdtCoNm = Constants.BLANK;
	/**
	 * 감독
	 */
	private String dirt = Constants.BLANK;
	/**
	 * 제작년도
	 */
	private String prdtYyyy = Constants.BLANK;
	/**
	 * 타임코드
	 */
	private String timeCd = Constants.BLANK;
	/**
	 * 시리즈번호
	 */
	private int srisNo;
	/**
	 * 에피소드번호
	 */
	private int episNo;
	/**
	 * 방송일자(연월일)
	 */
	private String brdDd = Constants.BLANK;
	/**
	 * 키워드
	 */
	private String keyWord = Constants.BLANK;
	/**
	 * 촬영기법
	 */
	private String photoMethod = Constants.BLANK;
	/**
	 * 장면내용(아카이버 작업 스ㅗ리보드 코너내용)
	 */
	private String scnCont = Constants.BLANK;
	/**
	 * 음악정보
	 */
	private String musicInfo = Constants.BLANK;
	/**
	 * 시놉시스
	 */
	private String snps = Constants.BLANK;
	/**
	 * 오디오코드
	 */
	private String audioCd = Constants.BLANK;
	/**
	 * 색상코드
	 */
	private String colorCd = Constants.BLANK;
	/**
	 * 녹음방식코드
	 */
	private String recordTypeCd = Constants.BLANK;
	/**
	 * ME분리코드
	 */
	private String meCd = Constants.BLANK;
	/**
	 * 저작권자
	 */
	private String cprtr = Constants.BLANK;
	/**
	 * 저작권형태코드
	 */
	private String cprtType = Constants.BLANK;
	/**
	 * 관람등급명
	 */
	private String wtchGrNm = Constants.BLANK;
	/**
	 * 수상정보
	 */
	private String awardInfo = Constants.BLANK;
	/**
	 * 접근권자
	 */
	private String accessRighter = Constants.BLANK;
	/**
	 * 소유자
	 */
	private String rightOwner = Constants.BLANK;
	/**
	 * 라이센스옵션
	 */
	private String licenseOption = Constants.BLANK;
	/**
	 * 최대사용수
	 */
	private String maxUsageCount = Constants.BLANK;
	/**
	 * 관람등급코드
	 */
	private String wtchGr = Constants.BLANK;
	/**
	 * 심의등급코
	 */
	private String dlbrGr = Constants.BLANK;
	/**
	 * 보관기간코드
	 */
	private String keepPrtCd = Constants.BLANK;
	/**
	 * 데이타상태코드
	 */
	private String dataStatCd = Constants.BLANK;
	/**
	 * 등록자
	 */
	private String regr = Constants.BLANK;
	/**
	 * 아카이브일자(연월일)
	 */
	private String archiveDd = Constants.BLANK;
	/**
	 * MC
	 */
	private String mc = Constants.BLANK;
	/**
	 * 저자
	 */
	private String author = Constants.BLANK;
	/**
	 * 원제작자
	 */
	private String orgPrdr = Constants.BLANK;
	/**
	 * 제작진
	 */
	private String staff = Constants.BLANK;
	/**
	 * 제작형태코드
	 */
	private String prdtTypeCd = Constants.BLANK;
	/**
	 * 등록일자(연월일)
	 */
	private String regDd = Constants.BLANK;
	/**
	 * 프로그램이름
	 */
	private String pgmNm = Constants.BLANK;
	/**
	 * 자료유형
	 */
	private String dataKind = Constants.BLANK;
	/**
	 * 사용등급코드
	 */
	private String useGradeCd = Constants.BLANK;
	/**
	 * 작업상태
	 */
	private String workStat = Constants.BLANK;
	/**
	 * 락상태
	 */
	private String lockStat = Constants.BLANK;
	/**
	 * 관련텍스트1
	 */
	private String rltText1 = Constants.BLANK;
	/**
	 * 관련텍스트2
	 */
	private String rltText2 = Constants.BLANK;
	/**
	 * 관련텍스트3
	 */
	private String rltText3 = Constants.BLANK;
	/**
	 * 관련화일1
	 */
	private String rltFile1 = Constants.BLANK;
	/**
	 * 관련화일2
	 */
	private String rltFile2 = Constants.BLANK;
	/**
	 * 관련화일3
	 */
	private String rltFile3 = Constants.BLANK;
	/**
	 * 프로그램 시작시간
	 */
	private String bgnTime = Constants.BLANK;
	/**
	 * 프로그램 종료시간
	 */
	private String endTime = Constants.BLANK;
	/**
	 * 비평
	 */
	private String review = Constants.BLANK;
	/**
	 * 편성요일
	 */
	private String schdWeekDd = Constants.BLANK;
	/**
	 * 프로그램코드문자열
	 */
	private String pgmCd = Constants.BLANK;
	/**
	 * 판매내역
	 */
	private String selCont = Constants.BLANK;
	/**
	 * 구입가격
	 */
	private String buyPrc = Constants.BLANK;
	/**
	 * 제작자
	 */
	private String prdtr = Constants.BLANK;
	/**
	 * 게임명
	 */
	private String gameNm = Constants.BLANK;
	/**
	 * 해설자
	 */
	private String cmntr = Constants.BLANK;
	/**
	 * 총갯수
	 */
	private int totCnt;
	/**
	 * 구입내역
	 */
	private String buyCont = Constants.BLANK;
	/**
	 * 언어코드
	 */
	private String wordCd = Constants.BLANK;
	/**
	 * 시청등급코드
	 */
	private String viewGr = Constants.BLANK;
	/**
	 * RLY_BRD
	 */
	private String rlyBrd = Constants.BLANK;
	/**
	 * 작업순서
	 */
	private String workSeq = Constants.BLANK;
	/**
	 * 인제스트여부
	 */
	private String ingestYn = Constants.BLANK;
	/**
	 * 인제스트상태
	 */
	private String ingestStatus = Constants.BLANK;
	/**
	 * 인제스트일자(연월일)
	 */
	private String ingestDd = Constants.BLANK;
	/**
	 * 제작부서명
	 */
	private String deptNm = Constants.BLANK;
	
	
	public String getTapeId() {
		return tapeId;
	}
	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getScnTtl() {
		return scnTtl;
	}
	public void setScnTtl(String scnTtl) {
		this.scnTtl = scnTtl;
	}
	public String getTapeItemId() {
		return tapeItemId;
	}
	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public int getEpisNo() {
		return episNo;
	}
	public void setEpisNo(int episNo) {
		this.episNo = episNo;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getCmrMan() {
		return cmrMan;
	}
	public void setCmrMan(String cmrMan) {
		this.cmrMan = cmrMan;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	public String getCmrDd() {
		return cmrDd;
	}
	public void setCmrDd(String cmrDd) {
		this.cmrDd = cmrDd;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getWorkSeq() {
		return workSeq;
	}
	public void setWorkSeq(String workSeq) {
		this.workSeq = workSeq;
	}
	public String getColorCd() {
		return colorCd;
	}
	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}
	public String getRecordTypeCd() {
		return recordTypeCd;
	}
	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}
	public String getMeCd() {
		return meCd;
	}
	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}
	public String getIngestStatus() {
		return ingestStatus;
	}
	public void setIngestStatus(String ingestStatus) {
		this.ingestStatus = ingestStatus;
	}
	public String getAccessRighter() {
		return accessRighter;
	}
	public void setAccessRighter(String accessRighter) {
		this.accessRighter = accessRighter;
	}
	public String getArchiveDd() {
		return archiveDd;
	}
	public void setArchiveDd(String archiveDd) {
		this.archiveDd = archiveDd;
	}
	public String getAudioCd() {
		return audioCd;
	}
	public void setAudioCd(String audioCd) {
		this.audioCd = audioCd;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAwardInfo() {
		return awardInfo;
	}
	public void setAwardInfo(String awardInfo) {
		this.awardInfo = awardInfo;
	}
	public String getBgnTime() {
		return bgnTime;
	}
	public void setBgnTime(String bgnTime) {
		this.bgnTime = bgnTime;
	}
	public String getBuyCont() {
		return buyCont;
	}
	public void setBuyCont(String buyCont) {
		this.buyCont = buyCont;
	}
	public String getBuyPrc() {
		return buyPrc;
	}
	public void setBuyPrc(String buyPrc) {
		this.buyPrc = buyPrc;
	}
	public String getCasting() {
		return casting;
	}
	public void setCasting(String casting) {
		this.casting = casting;
	}
	public String getCmntr() {
		return cmntr;
	}
	public void setCmntr(String cmntr) {
		this.cmntr = cmntr;
	}
	public String getCprtr() {
		return cprtr;
	}
	public void setCprtr(String cprtr) {
		this.cprtr = cprtr;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getDataKind() {
		return dataKind;
	}
	public void setDataKind(String dataKind) {
		this.dataKind = dataKind;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getDlbrGr() {
		return dlbrGr;
	}
	public void setDlbrGr(String dlbrGr) {
		this.dlbrGr = dlbrGr;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getPrdtYyyy() {
		return prdtYyyy;
	}
	public void setPrdtYyyy(String prdtYyyy) {
		this.prdtYyyy = prdtYyyy;
	}
	public String getGameNm() {
		return gameNm;
	}
	public void setGameNm(String gameNm) {
		this.gameNm = gameNm;
	}
	public String getIngestDd() {
		return ingestDd;
	}
	public void setIngestDd(String ingestDd) {
		this.ingestDd = ingestDd;
	}
	public String getIngestYn() {
		return ingestYn;
	}
	public void setIngestYn(String ingestYn) {
		this.ingestYn = ingestYn;
	}
	public String getKeepPrtCd() {
		return keepPrtCd;
	}
	public void setKeepPrtCd(String keepPrtCd) {
		this.keepPrtCd = keepPrtCd;
	}
	public String getLicenseOption() {
		return licenseOption;
	}
	public void setLicenseOption(String licenseOption) {
		this.licenseOption = licenseOption;
	}
	public String getLockStat() {
		return lockStat;
	}
	public void setLockStat(String lockStat) {
		this.lockStat = lockStat;
	}
	public String getMaxUsageCount() {
		return maxUsageCount;
	}
	public void setMaxUsageCount(String maxUsageCount) {
		this.maxUsageCount = maxUsageCount;
	}
	public String getMc() {
		return mc;
	}
	public void setMc(String mc) {
		this.mc = mc;
	}
	public String getMusicInfo() {
		return musicInfo;
	}
	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}
	public String getOrgPrdr() {
		return orgPrdr;
	}
	public void setOrgPrdr(String orgPrdr) {
		this.orgPrdr = orgPrdr;
	}
	public String getOrgTtl() {
		return orgTtl;
	}
	public void setOrgTtl(String orgTtl) {
		this.orgTtl = orgTtl;
	}
	public String getPgmCd() {
		return pgmCd;
	}
	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getPhotoMethod() {
		return photoMethod;
	}
	public void setPhotoMethod(String photoMethod) {
		this.photoMethod = photoMethod;
	}
	public String getPrdtr() {
		return prdtr;
	}
	public void setPrdtr(String prdtr) {
		this.prdtr = prdtr;
	}
	public String getPrdtTypeCd() {
		return prdtTypeCd;
	}
	public void setPrdtTypeCd(String prdtTypeCd) {
		this.prdtTypeCd = prdtTypeCd;
	}
	public String getRegDd() {
		return regDd;
	}
	public void setRegDd(String regDd) {
		this.regDd = regDd;
	}
	public String getRegr() {
		return regr;
	}
	public void setRegr(String regr) {
		this.regr = regr;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getRightOwner() {
		return rightOwner;
	}
	public void setRightOwner(String rightOwner) {
		this.rightOwner = rightOwner;
	}
	public String getRltFile1() {
		return rltFile1;
	}
	public void setRltFile1(String rltFile1) {
		this.rltFile1 = rltFile1;
	}
	public String getRltFile2() {
		return rltFile2;
	}
	public void setRltFile2(String rltFile2) {
		this.rltFile2 = rltFile2;
	}
	public String getRltFile3() {
		return rltFile3;
	}
	public void setRltFile3(String rltFile3) {
		this.rltFile3 = rltFile3;
	}
	public String getRltText1() {
		return rltText1;
	}
	public void setRltText1(String rltText1) {
		this.rltText1 = rltText1;
	}
	public String getRltText2() {
		return rltText2;
	}
	public void setRltText2(String rltText2) {
		this.rltText2 = rltText2;
	}
	public String getRltText3() {
		return rltText3;
	}
	public void setRltText3(String rltText3) {
		this.rltText3 = rltText3;
	}
	public String getRlyBrd() {
		return rlyBrd;
	}
	public void setRlyBrd(String rlyBrd) {
		this.rlyBrd = rlyBrd;
	}
	public String getRptr() {
		return rptr;
	}
	public void setRptr(String rptr) {
		this.rptr = rptr;
	}
	public String getRstCont() {
		return rstCont;
	}
	public void setRstCont(String rstCont) {
		this.rstCont = rstCont;
	}
	public String getSchdWeekDd() {
		return schdWeekDd;
	}
	public void setSchdWeekDd(String schdWeekDd) {
		this.schdWeekDd = schdWeekDd;
	}
	public int getScnCnt() {
		return scnCnt;
	}
	public void setScnCnt(int scnCnt) {
		this.scnCnt = scnCnt;
	}
	public String getScnCont() {
		return scnCont;
	}
	public void setScnCont(String scnCont) {
		this.scnCont = scnCont;
	}
	public int getScnNo() {
		return scnNo;
	}
	public void setScnNo(int scnNo) {
		this.scnNo = scnNo;
	}
	public String getSelCont() {
		return selCont;
	}
	public void setSelCont(String selCont) {
		this.selCont = selCont;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
	}
	public int getSrisNo() {
		return srisNo;
	}
	public void setSrisNo(int srisNo) {
		this.srisNo = srisNo;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public String getTapeClf() {
		return tapeClf;
	}
	public void setTapeClf(String tapeClf) {
		this.tapeClf = tapeClf;
	}
	public String getTimeCd() {
		return timeCd;
	}
	public void setTimeCd(String timeCd) {
		this.timeCd = timeCd;
	}
	public int getTotCnt() {
		return totCnt;
	}
	public void setTotCnt(int totCnt) {
		this.totCnt = totCnt;
	}
	public String getUseGradeCd() {
		return useGradeCd;
	}
	public void setUseGradeCd(String useGradeCd) {
		this.useGradeCd = useGradeCd;
	}
	public String getViewGr() {
		return viewGr;
	}
	public void setViewGr(String viewGr) {
		this.viewGr = viewGr;
	}
	public String getWordCd() {
		return wordCd;
	}
	public void setWordCd(String wordCd) {
		this.wordCd = wordCd;
	}
	public String getWorkStat() {
		return workStat;
	}
	public void setWorkStat(String workStat) {
		this.workStat = workStat;
	}
	public String getWtchGr() {
		return wtchGr;
	}
	public void setWtchGr(String wtchGr) {
		this.wtchGr = wtchGr;
	}
	public String getWtchGrNm() {
		return wtchGrNm;
	}
	public void setWtchGrNm(String wtchGrNm) {
		this.wtchGrNm = wtchGrNm;
	}
	public String getDirt() {
		return dirt;
	}
	public void setDirt(String dirt) {
		this.dirt = dirt;
	}
	public String getPrdtCoNm() {
		return prdtCoNm;
	}
	public void setPrdtCoNm(String prdtCoNm) {
		this.prdtCoNm = prdtCoNm;
	}
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}



}

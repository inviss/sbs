package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="das_search")
@XmlAccessorType(XmlAccessType.FIELD)
public class DasSearch {
	
	@XmlElement(name="PGM_ID")
	private String pgmId;
	@XmlElement(name="PGM_NM")
	private String pgmNm;
	@XmlElement(name="CTGR_L_CD")
	private String ctgrLCd;
	@XmlElement(name="CTGR_M_CD")
	private String ctgrMCd;
	@XmlElement(name="CTGR_S_CD")
	private String ctgrSCd;
	@XmlElement(name="MASTER_ID")
	private String masterId;
	@XmlElement(name="TITLE")
	private String title;
	@XmlElement(name="EPIS_NO")
	private String episNo;
	@XmlElement(name="SNPS")
	private String snps;
	@XmlElement(name="KEY_WORDS")
	private String keyWords;
	@XmlElement(name="DRT_NM")
	private String drtNm;
	@XmlElement(name="PRODUCER_NM")
	private String producerNm;
	@XmlElement(name="PRDT_IN_OUTS_CD")
	private String prdtInOutsCd;
	@XmlElement(name="PRDT_DEPT_NM")
	private String prdtDeptNm;
	@XmlElement(name="WRITER_NM")
	private String writerNm;
	@XmlElement(name="MC_NM")
	private String mcNm;
	@XmlElement(name="CAST_NM")
	private String castNm;
	@XmlElement(name="CMS_PLACE")
	private String cmrPlace;
	@XmlElement(name="BRD_DD")
	private String brdDd;
	@XmlElement(name="BRD_LENG")
	private String brdLeng;
	@XmlElement(name="REG_DT")
	private String regDt;
	@XmlElement(name="SPC_INFO")
	private String spcInfo;
	@XmlElement(name="CMR_DRT_NM")
	private String cmrDrtNm;
	@XmlElement(name="VIEW_GR_CD")
	private String viewGrCd;
	@XmlElement(name="AWARD_HSTR")
	private String awardHstr;
	@XmlElement(name="RPIMG_KFRM_SEQ")
	private String rpimgKfrmSeq;
	@XmlElement(name="RPIMG_CT_ID")
	private String rpimgCtId;
	@XmlElement(name="MUSIC_INFO")
	private String musicInfo;
	@XmlElement(name="SUB_TTL")
	private String subTtl;
	@XmlElement(name="DAY")
	private String day;
	@XmlElement(name="CPRT_TYPE")
	private String cprtType;
	@XmlElement(name="DLBR_CD")
	private String dlbrCd;
	@XmlElement(name="CPRTR_NM")
	private String cprtrNm;
	@XmlElement(name="ORG_PRDR_NM")
	private String orgPrdrNm;
	@XmlElement(name="ARRANGE_NM")
	private String arrangeNm;
	@XmlElement(name="FM_DT")
	private String fmDt;
	@XmlElement(name="VD_QLTY_NM")
	private String vdQltyNm;
	@XmlElement(name="KFRM_PATH")
	private String kfrmPath;
	@XmlElement(name="PREVIEW_SUBJ")
	private String previewSubj;
	@XmlElement(name="PREVIEW_CONT")
	private String previewCont;
	@XmlElement(name="SCENARIO_TITLE")
	private String scenarioTitle;
	@XmlElement(name="SCENARIO_DESC")
	private String scenarioDesc;
	@XmlElement(name="DELETE_FLAG")
	private String deleteFlag;
	@XmlElement(name="PGMNM_TITLE")
	private String pgmnmTitle;
	@XmlElement(name="STORAGE")
	private String storage;
	@XmlElement(name="DATA_STAT_CD")
	private String dataStatCd;
	@XmlElement(name="CN_INFO")
	private String cnInfo;
	@XmlElement(name="ANNOT_CLF_NM")
	private String annotClfNm;
	@XmlElement(name="ANNOT_CLF_CONT")
	private String annotClfCont;
	@XmlElement(name="CN_NM")
	private String cnNm;
	@XmlElement(name="FIRST_CN_YN")
	private String firstCnYn;
	@XmlElement(name="MEDIA_ID")
	private String mediaId;
	@XmlElement(name="COUNTRY_CD")
	private String countryCd;
	@XmlElement(name="ARTIST")
	private String artist;
	@XmlElement(name="COCD")
	private String cocd;
	@XmlElement(name="CHENNEL_CD")
	private String chennel_cd;
	
	
	// 스트리밍을 위해 추가한 메소드
	@XmlElement(name="FL_PATH")
	private String flPath;
	@XmlElement(name="FL_NM")
	private String flNm;
	@XmlElement(name="CTI_FMT")
	private String ctiFmt;

	
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getChennel_cd() {
		return chennel_cd;
	}
	public void setChennel_cd(String chennelCd) {
		chennel_cd = chennelCd;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getCountryCd() {
		return countryCd;
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public String getPgmId() {
		return pgmId;
	}
	public void setPgmId(String pgmId) {
		this.pgmId = pgmId;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
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
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEpisNo() {
		return episNo;
	}
	public void setEpisNo(String episNo) {
		this.episNo = episNo;
	}
	public String getSnps() {
		return snps;
	}
	public void setSnps(String snps) {
		this.snps = snps;
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
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	public String getPrdtDeptNm() {
		return prdtDeptNm;
	}
	public void setPrdtDeptNm(String prdtDeptNm) {
		this.prdtDeptNm = prdtDeptNm;
	}
	public String getWriterNm() {
		return writerNm;
	}
	public void setWriterNm(String writerNm) {
		this.writerNm = writerNm;
	}
	public String getMcNm() {
		return mcNm;
	}
	public void setMcNm(String mcNm) {
		this.mcNm = mcNm;
	}
	public String getCastNm() {
		return castNm;
	}
	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
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
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	public String getCmrDrtNm() {
		return cmrDrtNm;
	}
	public void setCmrDrtNm(String cmrDrtNm) {
		this.cmrDrtNm = cmrDrtNm;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getAwardHstr() {
		return awardHstr;
	}
	public void setAwardHstr(String awardHstr) {
		this.awardHstr = awardHstr;
	}
	public String getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(String rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}
	public String getRpimgCtId() {
		return rpimgCtId;
	}
	public void setRpimgCtId(String rpimgCtId) {
		this.rpimgCtId = rpimgCtId;
	}
	public String getMusicInfo() {
		return musicInfo;
	}
	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	public String getDlbrCd() {
		return dlbrCd;
	}
	public void setDlbrCd(String dlbrCd) {
		this.dlbrCd = dlbrCd;
	}
	public String getCprtrNm() {
		return cprtrNm;
	}
	public void setCprtrNm(String cprtrNm) {
		this.cprtrNm = cprtrNm;
	}
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	public String getArrangeNm() {
		return arrangeNm;
	}
	public void setArrangeNm(String arrangeNm) {
		this.arrangeNm = arrangeNm;
	}
	public String getFmDt() {
		return fmDt;
	}
	public void setFmDt(String fmDt) {
		this.fmDt = fmDt;
	}
	public String getVdQltyNm() {
		return vdQltyNm;
	}
	public void setVdQltyNm(String vdQltyNm) {
		this.vdQltyNm = vdQltyNm;
	}
	public String getKfrmPath() {
		return kfrmPath;
	}
	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}
	public String getPreviewSubj() {
		return previewSubj;
	}
	public void setPreviewSubj(String previewSubj) {
		this.previewSubj = previewSubj;
	}
	public String getPreviewCont() {
		return previewCont;
	}
	public void setPreviewCont(String previewCont) {
		this.previewCont = previewCont;
	}
	public String getScenarioTitle() {
		return scenarioTitle;
	}
	public void setScenarioTitle(String scenarioTitle) {
		this.scenarioTitle = scenarioTitle;
	}
	public String getScenarioDesc() {
		return scenarioDesc;
	}
	public void setScenarioDesc(String scenarioDesc) {
		this.scenarioDesc = scenarioDesc;
	}
	public String getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getPgmnmTitle() {
		return pgmnmTitle;
	}
	public void setPgmnmTitle(String pgmnmTitle) {
		this.pgmnmTitle = pgmnmTitle;
	}
	public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	public String getDataStatCd() {
		return dataStatCd;
	}
	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}
	public String getCnInfo() {
		return cnInfo;
	}
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	public String getAnnotClfNm() {
		return annotClfNm;
	}
	public void setAnnotClfNm(String annotClfNm) {
		this.annotClfNm = annotClfNm;
	}
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	public String getFirstCnYn() {
		return firstCnYn;
	}
	public void setFirstCnYn(String firstCnYn) {
		this.firstCnYn = firstCnYn;
	}
	
}

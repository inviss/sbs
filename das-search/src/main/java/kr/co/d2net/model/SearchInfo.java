package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlCDATA;

@XmlRootElement(name="searchInfo")
public class SearchInfo {
	
	private String kwd = "";
	private String scn = "program";
	private String preKwds = "";
	private String srchFd;
	private String userId = "";
	private String siteName;
	private String recKwd;
	private String reSrchFlag = "false";
	private Integer pageSize = 20;
	private Integer pageNum = 1;
	private String sort = "D";
	private String sortcolumn = "DAY";
	private String detailSearch = "false"; //true 상세검색
	private String exclusiveKwd;  // or, and
	private String date = "false";
	private String startDate = "";
	private String endDate = "";
	private String grpKwd = "";
	private String grpSrchFd = "";
	private String grpAndOr = "";
	private String sceanGubun = "";
	private String grpstartdd;
	private String grpenddd;
	private String ctgrLCd = "";
	private String ctgrMCd = "";
	private String ctgrSCd = "";
	private String fmDt = "";
	
	private String title = "";
	private String subTtl = "";
	private String pgmNm = "";
	private String cnNm = "";
	private String cnInfo = "";
	private Integer episNo = 0;
	private String producerNm = "";
	private String drpNm = "";
	private String orgPrdrNm = "";
	private String brdDd = "";
	private String cmrPlace = "";
	private String keyWords = "";
	private String spcInfo = "";
	private String cprtType = "";
	private String cprtrNm = "";
	private String prdtInOutsCd = "";
	private String annotClfNm = "";
	private String annotClfCont = "";
	private String viewGrCd = "";
	private String mcNm = "";
	private String castNm = "";
	private String artist = "";
	private String untryCd = "";
	private String musicInfo = "";
	private String mediaId = "";
	private String ctiFmt = "";
	private String vdAltyNm = "";
	private String brdLeng = "";
	private String browser = "";
	private String channelCd = "";
	private String programYN = "";
	private String materialYn = "";
	
	@XmlElement(name="channels_cd")
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	@XmlElement(name="programs_yn")
	public String getProgramYN() {
		return programYN;
	}
	public void setProgramYN(String programYN) {
		this.programYN = programYN;
	}
	@XmlElement(name="materials_yn")
	public String getMaterialYn() {
		return materialYn;
	}
	public void setMaterialYn(String materialYn) {
		this.materialYn = materialYn;
	}
	@XmlElement(name="browser")
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	@XmlElement(name="fm_dt")
	public String getFmDt() {
		return fmDt;
	}
	public void setFmDt(String fmDt) {
		this.fmDt = fmDt;
	}
	@XmlElement(name="ctgr_m_cd")
	public String getCtgrMCd() {
		return ctgrMCd;
	}
	public void setCtgrMCd(String ctgrMCd) {
		this.ctgrMCd = ctgrMCd;
	}
	@XmlElement(name="ctgr_s_cd")
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	@XmlElement(name="title")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@XmlElement(name="sub_ttl")
	public String getSubTtl() {
		return subTtl;
	}
	public void setSubTtl(String subTtl) {
		this.subTtl = subTtl;
	}
	@XmlElement(name="pgm_nm")
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	@XmlElement(name="cn_nm")
	public String getCnNm() {
		return cnNm;
	}
	public void setCnNm(String cnNm) {
		this.cnNm = cnNm;
	}
	@XmlElement(name="cn_info")
	public String getCnInfo() {
		return cnInfo;
	}
	public void setCnInfo(String cnInfo) {
		this.cnInfo = cnInfo;
	}
	@XmlElement(name="epis_no")
	public Integer getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Integer episNo) {
		this.episNo = episNo;
	}
	@XmlElement(name="producer_nm")
	public String getProducerNm() {
		return producerNm;
	}
	public void setProducerNm(String producerNm) {
		this.producerNm = producerNm;
	}
	@XmlElement(name="drp_nm")
	public String getDrpNm() {
		return drpNm;
	}
	public void setDrpNm(String drpNm) {
		this.drpNm = drpNm;
	}
	@XmlElement(name="org_prdr_nm")
	public String getOrgPrdrNm() {
		return orgPrdrNm;
	}
	public void setOrgPrdrNm(String orgPrdrNm) {
		this.orgPrdrNm = orgPrdrNm;
	}
	@XmlElement(name="brd_dd")
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	@XmlElement(name="cmr_place")
	public String getCmrPlace() {
		return cmrPlace;
	}
	public void setCmrPlace(String cmrPlace) {
		this.cmrPlace = cmrPlace;
	}
	@XmlElement(name="key_words")
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	@XmlElement(name="spc_info")
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}
	@XmlElement(name="cprt_type")
	public String getCprtType() {
		return cprtType;
	}
	public void setCprtType(String cprtType) {
		this.cprtType = cprtType;
	}
	@XmlElement(name="cprt_nm")
	public String getCprtrNm() {
		return cprtrNm;
	}
	public void setCprtrNm(String cprtrNm) {
		this.cprtrNm = cprtrNm;
	}
	@XmlElement(name="prdt_in_outs_cd")
	public String getPrdtInOutsCd() {
		return prdtInOutsCd;
	}
	public void setPrdtInOutsCd(String prdtInOutsCd) {
		this.prdtInOutsCd = prdtInOutsCd;
	}
	@XmlElement(name="annot_clf_nm")
	public String getAnnotClfNm() {
		return annotClfNm;
	}
	public void setAnnotClfNm(String annotClfNm) {
		this.annotClfNm = annotClfNm;
	}
	@XmlElement(name="annot_clf_cont")
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	@XmlElement(name="view_gr_cd")
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	@XmlElement(name="mc_nm")
	public String getMcNm() {
		return mcNm;
	}
	public void setMcNm(String mcNm) {
		this.mcNm = mcNm;
	}
	@XmlElement(name="cast_nm")
	public String getCastNm() {
		return castNm;
	}
	public void setCastNm(String castNm) {
		this.castNm = castNm;
	}
	@XmlElement(name="artist")
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	@XmlElement(name="untry_cd")
	public String getUntryCd() {
		return untryCd;
	}
	public void setUntryCd(String untryCd) {
		this.untryCd = untryCd;
	}
	@XmlElement(name="music_info")
	public String getMusicInfo() {
		return musicInfo;
	}
	public void setMusicInfo(String musicInfo) {
		this.musicInfo = musicInfo;
	}
	@XmlElement(name="media_id")
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	@XmlElement(name="cti_fmt")
	public String getCtiFmt() {
		return ctiFmt;
	}
	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}
	@XmlElement(name="vd_alty_nm")
	public String getVdAltyNm() {
		return vdAltyNm;
	}
	public void setVdAltyNm(String vdAltyNm) {
		this.vdAltyNm = vdAltyNm;
	}
	@XmlElement(name="brd_leng")
	public String getBrdLeng() {
		return brdLeng;
	}
	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}
	
	@XmlElement(name="ctgr_l_cd")
	public String getCtgrLCd() {
		return ctgrLCd;
	}
	public void setCtgrLCd(String ctgrLCd) {
		this.ctgrLCd = ctgrLCd;
	}
	@XmlElement(name="kwd")
	public String getKwd() {
		return kwd;
	}
	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	
	@XmlElement(name="scn")
	public String getScn() {
		return scn;
	}
	public void setScn(String scn) {
		this.scn = scn;
	}
	
	@XmlElement(name="preKwds")
	public String getPreKwds() {
		return preKwds;
	}
	public void setPreKwds(String preKwds) {
		this.preKwds = preKwds;
	}
	
	@XmlElement(name="srchFd")
	public String getSrchFd() {
		return srchFd;
	}
	public void setSrchFd(String srchFd) {
		this.srchFd = srchFd;
	}
	
	@XmlElement(name="userId")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@XmlElement(name="siteName")
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	@XmlElement(name="recKwd")
	public String getRecKwd() {
		return recKwd;
	}
	public void setRecKwd(String recKwd) {
		this.recKwd = recKwd;
	}
	
	@XmlElement(name="reSrchFlag")
	public String getReSrchFlag() {
		return reSrchFlag;
	}
	public void setReSrchFlag(String reSrchFlag) {
		this.reSrchFlag = reSrchFlag;
	}
	
	@XmlElement(name="pageSize")
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	@XmlElement(name="pageNum")
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	@XmlElement(name="sort")
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@XmlElement(name="sortcolumn")
	public String getSortcolumn() {
		return sortcolumn;
	}
	public void setSortcolumn(String sortcolumn) {
		this.sortcolumn = sortcolumn;
	}
	
	@XmlElement(name="detailSearch")
	public String getDetailSearch() {
		return detailSearch;
	}
	public void setDetailSearch(String detailSearch) {
		this.detailSearch = detailSearch;
	}
	
	@XmlElement(name="exclusiveKwd")
	public String getExclusiveKwd() {
		return exclusiveKwd;
	}
	public void setExclusiveKwd(String exclusiveKwd) {
		this.exclusiveKwd = exclusiveKwd;
	}
	
	@XmlElement(name="date")
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@XmlElement(name="startDate")
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@XmlElement(name="endDate")
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@XmlElement(name="grpKwd")
	public String getGrpKwd() {
		return grpKwd;
	}
	public void setGrpKwd(String grpKwd) {
		this.grpKwd = grpKwd;
	}
	
	@XmlElement(name="grpSrchFd")
	public String getGrpSrchFd() {
		return grpSrchFd;
	}
	public void setGrpSrchFd(String grpSrchFd) {
		this.grpSrchFd = grpSrchFd;
	}
	
	@XmlElement(name="grpAndOr")
	public String getGrpAndOr() {
		return grpAndOr;
	}
	public void setGrpAndOr(String grpAndOr) {
		this.grpAndOr = grpAndOr;
	}
	
	@XmlElement(name="sceanGubun")
	public String getSceanGubun() {
		return sceanGubun;
	}
	public void setSceanGubun(String sceanGubun) {
		this.sceanGubun = sceanGubun;
	}
	
	@XmlElement(name="grpstartdd")
	public String getGrpstartdd() {
		return grpstartdd;
	}
	public void setGrpstartdd(String grpstartdd) {
		this.grpstartdd = grpstartdd;
	}
	
	@XmlElement(name="grpenddd")
	public String getGrpenddd() {
		return grpenddd;
	}
	public void setGrpenddd(String grpenddd) {
		this.grpenddd = grpenddd;
	}

}

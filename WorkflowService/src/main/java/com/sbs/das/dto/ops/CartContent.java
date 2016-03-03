package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("cartcontents")
public class CartContent {
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_no")
	private Long cartNo;
	@XStreamAlias("cart_seq")
	@XStreamConverter(IntegerConverter.class)
	private Integer cartSeq;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("ct_id")
	private Long ctId;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cti_id")
	private Long ctiId;
	@XStreamAlias("som")
	private String som;
	@XStreamAlias("eom")
	private String eom;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("duration")
	private Long duration;
	@XStreamAlias("reg_dt")
	private String regDt;
	@XStreamAlias("regrid")
	private String regrid;
	@XStreamAlias("ctgr_l_cd")
	private String ctgrLCd;
	@XStreamAlias("ctgr_m_cd")
	private String ctgrMCd;
	@XStreamAlias("ctgr_s_cd")
	private String ctgrSCd;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("ct_cont")
	private String ctCont;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("ct_nm")
	private String ctNm;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private Long masterId;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("s_frame")
	private Long sFrame;
	@XStreamAlias("vd_qlty")
	private String vdQlty;
	@XStreamAlias("vd_qlty_nm")
	private String vdQltyNm;
	@XStreamAlias("asp_rto_cd")
	private String aspRtoCd;
	@XStreamAlias("asp_rto_nm")
	private String aspRtoNm;
	@XStreamAlias("down_stat")
	private String downStat;
	@XStreamAlias("down_typ")
	private String downTyp;
	@XStreamAlias("outsourcing_yn")
	private String outsourceingYn;
	@XStreamAlias("logical_tree")
	private String logicalTree;
	@XStreamAlias("physical_tree")
	private String physicalTree;
	@XStreamAlias("cocd")
	private String coCd;
	@XStreamAlias("chennel")
	private String channelCd;
	@XStreamAlias("rist_clf_cd")
	private String ristClfCd;
	@XStreamAlias("mod_dt")
	private String modDt;
	@XStreamAlias("modrid")
	private String modrid;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("app_cont")
	private String appCont;
	@XStreamAlias("target_cms_id")
	private String targetCmsId;
	@XStreamAlias("transaction_id")
	private String transcationId;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("req_cont")
	private String reqCont;
	@XStreamAlias("req_cd")
	private String reqCd;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_nm")
	private String pgmNm;
	@XStreamAlias("rist_clf_nm")
	private String ristClfNm;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("down_subj")
	private String downSubj;
	@XStreamAlias("use_limit_count")
	private String useLimitCount;
	@XStreamAlias("use_limit_flag")
	private String useLimitFlag;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("title")
	private String title;
	@XStreamAlias("epis_no")
	private String episNo;
	@XStreamAlias("down_vol")
	private String downVol;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("down_typ_nm")
	private String downTypNm;
	@XStreamAlias("trans_vol")
	private String transVol;
	@XStreamAlias("brd_dd")
	private String brdDd;
	@XStreamAlias("status")
	private String status;
	@XStreamAlias("fm_dt")
	private String fmDt;
	@XStreamAlias("media_id")
	private String mediaId;
	@XStreamAlias("path")
	private String path;
	@XStreamAlias("fl_nm")
	private String flNm;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("approve_nm")
	private String approveNm;
	@XStreamAlias("cocd")
	private String cocd;
	@XStreamAlias("chennel")
	private String chennel;
	@XStreamAlias("chennel_nm")
	private String chennelNm;
	@XStreamAlias("down_gubun_nm")
	private String downGubunNm;
	
	public String getDownGubunNm() {
		return downGubunNm;
	}
	public void setDownGubunNm(String downGubunNm) {
		this.downGubunNm = downGubunNm;
	}
	public String getAspRtoNm() {
		return aspRtoNm;
	}
	public void setAspRtoNm(String aspRtoNm) {
		this.aspRtoNm = aspRtoNm;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public String getPgmNm() {
		return pgmNm;
	}
	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}
	public String getRistClfNm() {
		return ristClfNm;
	}
	public void setRistClfNm(String ristClfNm) {
		this.ristClfNm = ristClfNm;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public String getVdQltyNm() {
		return vdQltyNm;
	}
	public void setVdQltyNm(String vdQltyNm) {
		this.vdQltyNm = vdQltyNm;
	}
	public String getUseLimitCount() {
		return useLimitCount;
	}
	public void setUseLimitCount(String useLimitCount) {
		this.useLimitCount = useLimitCount;
	}
	public String getUseLimitFlag() {
		return useLimitFlag;
	}
	public void setUseLimitFlag(String useLimitFlag) {
		this.useLimitFlag = useLimitFlag;
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
	public String getDownVol() {
		return downVol;
	}
	public void setDownVol(String downVol) {
		this.downVol = downVol;
	}
	public String getDownTypNm() {
		return downTypNm;
	}
	public void setDownTypNm(String downTypNm) {
		this.downTypNm = downTypNm;
	}
	public String getTransVol() {
		return transVol;
	}
	public void setTransVol(String transVol) {
		this.transVol = transVol;
	}
	public String getBrdDd() {
		return brdDd;
	}
	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFmDt() {
		return fmDt;
	}
	public void setFmDt(String fmDt) {
		this.fmDt = fmDt;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public String getApproveNm() {
		return approveNm;
	}
	public void setApproveNm(String approveNm) {
		this.approveNm = approveNm;
	}
	public String getCocd() {
		return cocd;
	}
	public void setCocd(String cocd) {
		this.cocd = cocd;
	}
	public String getChennel() {
		return chennel;
	}
	public void setChennel(String chennel) {
		this.chennel = chennel;
	}
	public String getChennelNm() {
		return chennelNm;
	}
	public void setChennelNm(String chennelNm) {
		this.chennelNm = chennelNm;
	}
	public Long getCartNo() {
		return cartNo;
	}
	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}
	public Integer getCartSeq() {
		return cartSeq;
	}
	public void setCartSeq(Integer cartSeq) {
		this.cartSeq = cartSeq;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
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
	public String getCtgrSCd() {
		return ctgrSCd;
	}
	public void setCtgrSCd(String ctgrSCd) {
		this.ctgrSCd = ctgrSCd;
	}
	public String getCtCont() {
		return ctCont;
	}
	public void setCtCont(String ctCont) {
		this.ctCont = ctCont;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Long getsFrame() {
		return sFrame;
	}
	public void setsFrame(Long sFrame) {
		this.sFrame = sFrame;
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
	public String getDownStat() {
		return downStat;
	}
	public void setDownStat(String downStat) {
		this.downStat = downStat;
	}
	public String getDownTyp() {
		return downTyp;
	}
	public void setDownTyp(String downTyp) {
		this.downTyp = downTyp;
	}
	public String getOutsourceingYn() {
		return outsourceingYn;
	}
	public void setOutsourceingYn(String outsourceingYn) {
		this.outsourceingYn = outsourceingYn;
	}
	public String getLogicalTree() {
		return logicalTree;
	}
	public void setLogicalTree(String logicalTree) {
		this.logicalTree = logicalTree;
	}
	public String getPhysicalTree() {
		return physicalTree;
	}
	public void setPhysicalTree(String physicalTree) {
		this.physicalTree = physicalTree;
	}
	public String getCoCd() {
		return coCd;
	}
	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}
	public String getModDt() {
		return modDt;
	}
	public void setModDt(String modDt) {
		this.modDt = modDt;
	}
	public String getModrid() {
		return modrid;
	}
	public void setModrid(String modrid) {
		this.modrid = modrid;
	}
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public String getTargetCmsId() {
		return targetCmsId;
	}
	public void setTargetCmsId(String targetCmsId) {
		this.targetCmsId = targetCmsId;
	}
	public String getTranscationId() {
		return transcationId;
	}
	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}
	public String getReqCont() {
		return reqCont;
	}
	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}
	
}

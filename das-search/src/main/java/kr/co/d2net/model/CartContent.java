package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="cartcontents")
@XmlAccessorType(XmlAccessType.FIELD)
public class CartContent {

	@XmlElement(name="cart_no")
	private Long cartNo;
	@XmlElement(name="cart_seq")
	private Integer cartSeq;
	@XmlElement(name="ct_id")
	private Long ctId;
	@XmlElement(name="cti_id")
	private Long ctiId;
	@XmlElement(name="som")
	private String som;
	@XmlElement(name="eom")
	private String eom;
	@XmlElement(name="duration")
	private Long duration;
	@XmlElement(name="reg_dt")
	private String regDt;
	@XmlElement(name="regrid")
	private String regrid;
	@XmlElement(name="ctgr_l_cd")
	private String ctgrLCd;
	@XmlElement(name="ctgr_m_cd")
	private String ctgrMCd;
	@XmlElement(name="ctgr_s_cd")
	private String ctgrSCd;
	@XmlElement(name="ct_cont")
	private String ctCont;
	@XmlElement(name="ct_nm")
	private String ctNm;
	@XmlElement(name="master_id")
	private Long masterId;
	@XmlElement(name="s_frame")
	private Long sFrame;
	@XmlElement(name="vd_qlty")
	private String vdQlty;
	@XmlElement(name="asp_rto_cd")
	private String aspRtoCd;
	@XmlElement(name="down_stat")
	private String downStat;
	@XmlElement(name="down_typ")
	private String downTyp;
	@XmlElement(name="outsourcing_yn")
	private String outsourceingYn;
	@XmlElement(name="logical_tree")
	private String logicalTree;
	@XmlElement(name="physical_tree")
	private String physicalTree;
	@XmlElement(name="cocd")
	private String coCd;
	@XmlElement(name="chennel")
	private String channelCd;
	@XmlElement(name="rist_clf_cd")
	private String ristClfCd;
	@XmlElement(name="mod_dt")
	private String modDt;
	@XmlElement(name="modrid")
	private String modrid;
	@XmlElement(name="app_cont")
	private String appCont;
	@XmlElement(name="target_cms_id")
	private String targetCmsId;
	@XmlElement(name="transaction_id")
	private String transcationId;
	@XmlElement(name="req_cont")
	private String reqCont;
	
	
	public String getReqCont() {
		return reqCont;
	}
	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}
	public String getTranscationId() {
		return transcationId;
	}
	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}
	public String getTargetCmsId() {
		return targetCmsId;
	}
	public void setTargetCmsId(String targetCmsId) {
		this.targetCmsId = targetCmsId;
	}
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public String getPhysicalTree() {
		return physicalTree;
	}
	public void setPhysicalTree(String physicalTree) {
		this.physicalTree = physicalTree;
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
	
}

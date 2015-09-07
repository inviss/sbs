package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="downCart")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownCart {
	
	@XmlElement(name="ct_id")
	private Long ctId;
	@XmlElement(name="prio_cd")
	private Integer prioCd;
	@XmlElement(name="rist_yn")
	private String ristYn;
	@XmlElement(name="app_dt")
	private String appDt;				// 승인일
	@XmlElement(name="app_cont")
	private String appCont;
	@XmlElement(name="gaurantor_id")
	private String gaurantorId;			// 보증인ID
	@XmlElement(name="mod_dt")
	private String modDt;				// 수정일
	@XmlElement(name="modrid")
	private String modrid;				// 수정자
	@XmlElement(name="down_subj")
	private String downSubj;
	@XmlElement(name="nodecaption")
	private String nodecaption;
	@XmlElement(name="nodeid")
	private Integer nodeid;
	@XmlElement(name="cellname")
	private String cellname;
	@XmlElement(name="down_gubun")
	private String downGubun;
	@XmlElement(name="cart_no")			
	private Long cartNo;				// 카트번호
	@XmlElement(name="user_id")
	private String userId;
	@XmlElement(name="physicaltree")
	private String physicaltree;
	@XmlElement(name="logicaltree")
	private String logicaltree;
	@XmlElement(name="co_cd")
	private String coCd;
	@XmlElement(name="chennel")
	private String channelCd;
	@XmlElement(name="url")
	private String url;
	@XmlElement(name="data_clf_cd")
	private String dataClfCd;			// 자료구분 (001: 영상, 002: 사진)
	@XmlElement(name="strg_loc")
	private String strgLoc;				// 저장위치
	@XmlElement(name="vd_qlty")
	private String vdQlty;				// 화질코드	(001: HD, 002: SD)
	@XmlElement(name="asp_rto_cd")
	private String aspRtoCd;			// 종횡비 (001: 16:9, 002: 4:3)
	@XmlElement(name="req_usrid")
	private String reqUsrid;			// 요청자
	@XmlElement(name="regrid")
	private String regrid;				// 요청자
	@XmlElement(name="req_nm")
	private String reqNm;				// 요청자명
	@XmlElement(name="req_dt")
	private String reqDt;				// 등록일
	@XmlElement(name="down_dt")
	private String downDt;				// 다운로드일
	@XmlElement(name="reg_dt")
	private String regDt;				// 등록일
	@XmlElement(name="cart_stat")
	private String cartStat;			// 카트상태(001:사용중, 002:임시저장, 003:승인요청, 004:승인, 005:승인거부, 006:다운로드 진행중, 007:다운로드 완료)
	@XmlElement(name="seg_cd")
	private String segCd;				// 회사코드
	@XmlElement(name="dept_cd")			
	private String deptCd;				// 부서코드
	@XmlElement(name="out_strg_loc") 	
	private String outStrgLoc;			// 타 시스템 다운로드 경로
	@XmlElement(name="master_id") 	
	private Long masterId;			// 프로그램 ID
	@XmlElement(name="cti_id") 	
	private Long ctiId;			// 영상id
	@XmlElement(name="req_cont") 	
	private String reqCont;			// 요청사유
	
	
	public String getReqCont() {
		return reqCont;
	}
	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public Long getCtiId() {
		return ctiId;
	}
	public void setCtiId(Long ctiId) {
		this.ctiId = ctiId;
	}
	public Long getCtId() {
		return ctId;
	}
	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}
	public Integer getPrioCd() {
		return prioCd;
	}
	public void setPrioCd(Integer prioCd) {
		this.prioCd = prioCd;
	}
	public String getRistYn() {
		return ristYn;
	}
	public void setRistYn(String ristYn) {
		this.ristYn = ristYn;
	}
	public String getAppDt() {
		return appDt;
	}
	public void setAppDt(String appDt) {
		this.appDt = appDt;
	}
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public String getGaurantorId() {
		return gaurantorId;
	}
	public void setGaurantorId(String gaurantorId) {
		this.gaurantorId = gaurantorId;
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
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getReqNm() {
		return reqNm;
	}
	public void setReqNm(String reqNm) {
		this.reqNm = reqNm;
	}
	public String getReqDt() {
		return reqDt;
	}
	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}
	public String getDownDt() {
		return downDt;
	}
	public void setDownDt(String downDt) {
		this.downDt = downDt;
	}
	public String getDataClfCd() {
		return dataClfCd;
	}
	public void setDataClfCd(String dataClfCd) {
		this.dataClfCd = dataClfCd;
	}
	public String getStrgLoc() {
		return strgLoc;
	}
	public void setStrgLoc(String strgLoc) {
		this.strgLoc = strgLoc;
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
	public String getReqUsrid() {
		return reqUsrid;
	}
	public void setReqUsrid(String reqUsrid) {
		this.reqUsrid = reqUsrid;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getCartStat() {
		return cartStat;
	}
	public void setCartStat(String cartStat) {
		this.cartStat = cartStat;
	}
	public String getSegCd() {
		return segCd;
	}
	public void setSegCd(String segCd) {
		this.segCd = segCd;
	}
	public String getDeptCd() {
		return deptCd;
	}
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}
	public String getOutStrgLoc() {
		return outStrgLoc;
	}
	public void setOutStrgLoc(String outStrgLoc) {
		this.outStrgLoc = outStrgLoc;
	}
	public String getDownSubj() {
		return downSubj;
	}
	public void setDownSubj(String downSubj) {
		this.downSubj = downSubj;
	}
	public String getNodecaption() {
		return nodecaption;
	}
	public void setNodecaption(String nodecaption) {
		this.nodecaption = nodecaption;
	}
	public Integer getNodeid() {
		return nodeid;
	}
	public void setNodeid(Integer nodeid) {
		this.nodeid = nodeid;
	}
	public String getCellname() {
		return cellname;
	}
	public void setCellname(String cellname) {
		this.cellname = cellname;
	}
	public String getDownGubun() {
		return downGubun;
	}
	public void setDownGubun(String downGubun) {
		this.downGubun = downGubun;
	}
	public Long getCartNo() {
		return cartNo;
	}
	public void setCartNo(Long cartNo) {
		this.cartNo = cartNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhysicaltree() {
		return physicaltree;
	}
	public void setPhysicaltree(String physicaltree) {
		this.physicaltree = physicaltree;
	}
	public String getLogicaltree() {
		return logicaltree;
	}
	public void setLogicaltree(String logicaltree) {
		this.logicaltree = logicaltree;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("downCart")
public class DownCart {
	
	@XStreamAlias("ct_id")
	private Long ctId;
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("prio_cd")
	private Integer prioCd;
	@XStreamAlias("rist_yn")
	private String ristYn;
	@XStreamAlias("app_dt")
	private String appDt;				// 승인일
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("app_cont")
	private String appCont;
	@XStreamAlias("gaurantor_id")
	private String gaurantorId;			// 보증인ID
	@XStreamAlias("mod_dt")
	private String modDt;				// 수정일
	@XStreamAlias("modrid")
	private String modrid;				// 수정자
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("down_subj")
	private String downSubj;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("nodecaption")
	private String nodecaption;
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("nodeid")
	private Integer nodeid;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("cellname")
	private String cellname;
	@XStreamAlias("down_gubun")
	private String downGubun;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cart_no")			
	private Long cartNo;				// 카트번호
	@XStreamAlias("user_id")
	private String userId;
	@XStreamAlias("physicaltree")
	private String physicaltree;
	@XStreamAlias("logicaltree")
	private String logicaltree;
	@XStreamAlias("co_cd")
	private String coCd;
	@XStreamAlias("chennel")
	private String channelCd;
	@XStreamAlias("url")
	private String url;
	@XStreamAlias("data_clf_cd")
	private String dataClfCd;			// 자료구분 (001: 영상, 002: 사진)
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("strg_loc")
	private String strgLoc;				// 저장위치
	@XStreamAlias("vd_qlty")
	private String vdQlty;				// 화질코드	(001: HD, 002: SD)
	@XStreamAlias("asp_rto_cd")
	private String aspRtoCd;			// 종횡비 (001: 16:9, 002: 4:3)
	@XStreamAlias("req_usrid")
	private String reqUsrid;			// 요청자
	@XStreamAlias("regrid")
	private String regrid;				// 요청자
	@XStreamAlias("req_nm")
	private String reqNm;				// 요청자명
	@XStreamAlias("req_dt")
	private String reqDt;				// 등록일
	@XStreamAlias("down_dt")
	private String downDt;				// 다운로드일
	@XStreamAlias("reg_dt")
	private String regDt;				// 등록일
	@XStreamAlias("cart_stat")
	private String cartStat;			// 카트상태(001:사용중, 002:임시저장, 003:승인요청, 004:승인, 005:승인거부, 006:다운로드 진행중, 007:다운로드 완료)
	@XStreamAlias("seg_cd")
	private String segCd;				// 회사코드
	@XStreamAlias("dept_cd")			
	private String deptCd;				// 부서코드
	@XStreamAlias("out_strg_loc") 	
	private String outStrgLoc;			// 타 시스템 다운로드 경로
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id") 	
	private Long masterId;				// 프로그램 ID
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cti_id") 	
	private Long ctiId;					// 영상id
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("req_cont") 	
	private String reqCont;				// 요청사유
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("title") 	
	private String title;				// 요청사유
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("rist_clf_nm") 	
	private String ristClfNm;				// 요청사유
	@XStreamAlias("full_yn") 	
	private String fullYn;				// 요청사유
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("epis_no") 	
	private Integer episNo;				// 요청사유
	
	
	
	public String getRistClfNm() {
		return ristClfNm;
	}
	public void setRistClfNm(String ristClfNm) {
		this.ristClfNm = ristClfNm;
	}
	public String getFullYn() {
		return fullYn;
	}
	public void setFullYn(String fullYn) {
		this.fullYn = fullYn;
	}
	public Integer getEpisNo() {
		return episNo;
	}
	public void setEpisNo(Integer episNo) {
		this.episNo = episNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getReqCont() {
		return reqCont;
	}
	public void setReqCont(String reqCont) {
		this.reqCont = reqCont;
	}
	
}

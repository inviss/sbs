package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("metadat_mst_tbl")
public class Master {
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private Long masterId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("pgm_id")
	private Long pgmId;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("epis_no")
	private Integer episNo;
	
	@XStreamConverter(TextUTF8Converter.class)
	private String title;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brd_dd")
	private String brdDd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("fm_dt")
	private String fmdt;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("final_brd_yn")
	private String finalBrdYn;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brd_bgn_hms")
	private String brdBgnHms;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brd_end_hms")
	private String brdEndHms;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("brd_leng")
	private String brdLeng;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("use_yn")
	private String useYn;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	
	@XStreamConverter(TextConverter.class)
	private String regrid;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("mcuid")
	private String mcuId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("tape_id")
	private String tapeId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("tape_item_id")
	private String tapeItemId;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("onairmedia_round")
	private Integer onAirMediaRound;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("onairmedia_rating")
	private String onAirMediaRating;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("onairmedia_pd")
	private String onAirMediaPd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("onairmedia_approve")
	private String onAirMediaApprove;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("onairmedia_note")
	private String onAirMediaNote;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("data_stat_cd")
	private String dataStatCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("error_stat_cd")
	private String errorStatCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("tape_media_clf_cd")
	private String tapeMediaClfCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ctgr_l_cd")
	private String ctgrLCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("arch_route")
	private String	archRoute;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("rist_clf_cd")
	private String ristClfCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("pds_cms_pgm_id")
	private String pdsCmsPgmId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("view_gr_cd")
	private String viewGrCd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("spc_info")
	private String spcInfo;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("drt_nm")
	private String drtNm;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cocd")
	private String coCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("chennel_cd")
	private String chennelCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("req_cd")
	private String reqCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("rpimg_kfrm_seq")
	private String rpimgKfrmSeq;
	
	
	
	public String getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}
	public void setRpimgKfrmSeq(String rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}
	public String getChennelCd() {
		return chennelCd;
	}
	public String getCoCd() {
		return coCd;
	}
	public void setCoCd(String coCd) {
		this.coCd = coCd;
	}
	public String getReqCd() {
		return reqCd;
	}
	public void setReqCd(String reqCd) {
		this.reqCd = reqCd;
	}
	public void setChennelCd(String chennelCd) {
		this.chennelCd = chennelCd;
	}
	public String getFmdt() {
		return fmdt;
	}
	public void setFmdt(String fmdt) {
		this.fmdt = fmdt;
	}
	public String getSpcInfo() {
		return spcInfo;
	}
	public void setSpcInfo(String spcInfo) {
		this.spcInfo = spcInfo;
	}

	
	public String getDrtNm() {
		return drtNm;
	}
	public void setDrtNm(String drtNm) {
		this.drtNm = drtNm;
	}
	public String getViewGrCd() {
		return viewGrCd;
	}
	public void setViewGrCd(String viewGrCd) {
		this.viewGrCd = viewGrCd;
	}
	public String getPdsCmsPgmId() {
		return pdsCmsPgmId;
	}
	public void setPdsCmsPgmId(String pdsCmsPgmId) {
		this.pdsCmsPgmId = pdsCmsPgmId;
	}
	public String getArchRoute() {
		return archRoute;
	}
	public void setArchRoute(String archRoute) {
		this.archRoute = archRoute;
	}
	public String getRistClfCd() {
		return ristClfCd;
	}
	public void setRistClfCd(String ristClfCd) {
		this.ristClfCd = ristClfCd;
	}

	public String getTapeId() {
		return tapeId;
	}

	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
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

	public String getDataStatCd() {
		return dataStatCd;
	}

	public void setDataStatCd(String dataStatCd) {
		this.dataStatCd = dataStatCd;
	}

	public String getErrorStatCd() {
		return errorStatCd;
	}

	public void setErrorStatCd(String errorStatCd) {
		this.errorStatCd = errorStatCd;
	}

	public String getTapeItemId() {
		return tapeItemId;
	}

	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public Long getPgmId() {
		return pgmId;
	}

	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
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

	public String getBrdDd() {
		return brdDd;
	}

	public void setBrdDd(String brdDd) {
		this.brdDd = brdDd;
	}

	public String getFinalBrdYn() {
		return finalBrdYn;
	}

	public void setFinalBrdYn(String finalBrdYn) {
		this.finalBrdYn = finalBrdYn;
	}

	public String getBrdBgnHms() {
		return brdBgnHms;
	}

	public void setBrdBgnHms(String brdBgnHms) {
		this.brdBgnHms = brdBgnHms;
	}

	public String getBrdEndHms() {
		return brdEndHms;
	}

	public void setBrdEndHms(String brdEndHms) {
		this.brdEndHms = brdEndHms;
	}

	public String getBrdLeng() {
		return brdLeng;
	}

	public void setBrdLeng(String brdLeng) {
		this.brdLeng = brdLeng;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	public String getMcuId() {
		return mcuId;
	}

	public void setMcuId(String mcuId) {
		this.mcuId = mcuId;
	}

	public Integer getOnAirMediaRound() {
		return onAirMediaRound;
	}

	public void setOnAirMediaRound(Integer onAirMediaRound) {
		this.onAirMediaRound = onAirMediaRound;
	}

	public String getOnAirMediaRating() {
		return onAirMediaRating;
	}

	public void setOnAirMediaRating(String onAirMediaRating) {
		this.onAirMediaRating = onAirMediaRating;
	}

	public String getOnAirMediaPd() {
		return onAirMediaPd;
	}

	public void setOnAirMediaPd(String onAirMediaPd) {
		this.onAirMediaPd = onAirMediaPd;
	}

	public String getOnAirMediaApprove() {
		return onAirMediaApprove;
	}

	public void setOnAirMediaApprove(String onAirMediaApprove) {
		this.onAirMediaApprove = onAirMediaApprove;
	}

	public String getOnAirMediaNote() {
		return onAirMediaNote;
	}

	public void setOnAirMediaNote(String onAirMediaNote) {
		this.onAirMediaNote = onAirMediaNote;
	}
	
	
}

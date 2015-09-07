package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("contents_tbl")
public class Content {
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("ct_id")
	private Long ctId;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("ct_seq")
	private Integer ctSeq;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("ct_nm")
	private String ctNm;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ct_cla")
	private String ctCla;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ct_typ")
	private String ctTyp;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("use_yn")
	private String useYn;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("mcu_seq")
	private Integer mcuSeq;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("duration")
	private Long duration;

	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("ct_leng")
	private String ctLeng;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("vd_qlty")
	private String vdQlty;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("asp_rto_cd")
	private String aspRtoCd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("kfrm_path")
	private String kfrmPath;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("kfrm_px_cd")
	private String kfrmPxCd;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("tot_kfrm_nums")
	private Integer totKfrmNums;
	
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("rpimg_kfrm_seq")
	private Integer rpimgKfrmSeq;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("error_cont")
	private String errorCont;
	
	
	public String getErrorCont() {
		return errorCont;
	}

	public void setErrorCont(String errorCont) {
		this.errorCont = errorCont;
	}

	public String getCtTyp() {
		return ctTyp;
	}

	public void setCtTyp(String ctTyp) {
		this.ctTyp = ctTyp;
	}

	public String getKfrmPath() {
		return kfrmPath;
	}

	public void setKfrmPath(String kfrmPath) {
		this.kfrmPath = kfrmPath;
	}

	public String getKfrmPxCd() {
		return kfrmPxCd;
	}

	public void setKfrmPxCd(String kfrmPxCd) {
		this.kfrmPxCd = kfrmPxCd;
	}

	public Integer getTotKfrmNums() {
		return totKfrmNums;
	}

	public void setTotKfrmNums(Integer totKfrmNums) {
		this.totKfrmNums = totKfrmNums;
	}

	public Integer getRpimgKfrmSeq() {
		return rpimgKfrmSeq;
	}

	public void setRpimgKfrmSeq(Integer rpimgKfrmSeq) {
		this.rpimgKfrmSeq = rpimgKfrmSeq;
	}

	public String getCtLeng() {
		return ctLeng;
	}

	public void setCtLeng(String ctLeng) {
		this.ctLeng = ctLeng;
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

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public String getCtCla() {
		return ctCla;
	}

	public void setCtCla(String ctCla) {
		this.ctCla = ctCla;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public Long getCtId() {
		return ctId;
	}

	public void setCtId(Long ctId) {
		this.ctId = ctId;
	}

	public Integer getCtSeq() {
		return ctSeq;
	}

	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
	}

	public String getCtNm() {
		return ctNm;
	}

	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
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

	public Integer getMcuSeq() {
		return mcuSeq;
	}

	public void setMcuSeq(Integer mcuSeq) {
		this.mcuSeq = mcuSeq;
	}
	
	
	
}

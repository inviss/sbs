package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("work_log_tbl")
public class WorkLog {
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_eq_id")
	private String dasEqId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("das_eq_ps_cd")
	private String dasEqPsCd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("das_eq_clf_cd")
	private String dasEqClfCd;
	
	@XStreamConverter(TextUTF8Converter.class)
	private String dd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("das_eq_nm")
	private String dasEqNm;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private long masterId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cti_id")
	private long ctiId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("tape_id")
	private String tapeId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("tape_item_id")
	private String tapeItemId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("req_userid")
	private String reqUserId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	
	@XStreamConverter(TextConverter.class)
	private String regrid;

	public String getDasEqId() {
		return dasEqId;
	}

	public void setDasEqId(String dasEqId) {
		this.dasEqId = dasEqId;
	}

	public String getDasEqPsCd() {
		return dasEqPsCd;
	}

	public void setDasEqPsCd(String dasEqPsCd) {
		this.dasEqPsCd = dasEqPsCd;
	}

	public String getDasEqClfCd() {
		return dasEqClfCd;
	}

	public void setDasEqClfCd(String dasEqClfCd) {
		this.dasEqClfCd = dasEqClfCd;
	}

	public String getDd() {
		return dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public String getDasEqNm() {
		return dasEqNm;
	}

	public void setDasEqNm(String dasEqNm) {
		this.dasEqNm = dasEqNm;
	}

	public long getMasterId() {
		return masterId;
	}

	public void setMasterId(long masterId) {
		this.masterId = masterId;
	}

	public long getCtiId() {
		return ctiId;
	}

	public void setCtiId(long ctiId) {
		this.ctiId = ctiId;
	}

	public String getTapeId() {
		return tapeId;
	}

	public void setTapeId(String tapeId) {
		this.tapeId = tapeId;
	}

	public String getTapeItemId() {
		return tapeItemId;
	}

	public void setTapeItemId(String tapeItemId) {
		this.tapeItemId = tapeItemId;
	}

	public String getReqUserId() {
		return reqUserId;
	}

	public void setReqUserId(String reqUserId) {
		this.reqUserId = reqUserId;
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
	
}

package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("contents_mapp_tbl")
public class ContentMap {
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("ct_id")
	private Long ctId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("master_id")
	private Long masterId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("pgm_id")
	private Long pgmId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cn_id")
	private Long cnId;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("ct_seq")
	private Integer ctSeq;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("cn_seq")
	private Integer cnSeq = 1;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("s_duration")
	private String SDuration;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("e_duration")
	private Long EDuration;

	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	
	public Integer getCnSeq() {
		return cnSeq;
	}

	public void setCnSeq(Integer cnSeq) {
		this.cnSeq = cnSeq;
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

	public Long getCnId() {
		return cnId;
	}

	public void setCnId(Long cnId) {
		this.cnId = cnId;
	}

	public Integer getCtSeq() {
		return ctSeq;
	}

	public void setCtSeq(Integer ctSeq) {
		this.ctSeq = ctSeq;
	}

	public String getSDuration() {
		return SDuration;
	}

	public void setSDuration(String sDuration) {
		SDuration = sDuration;
	}

	public Long getEDuration() {
		return EDuration;
	}

	public void setEDuration(Long eDuration) {
		EDuration = eDuration;
	}
	
	
}

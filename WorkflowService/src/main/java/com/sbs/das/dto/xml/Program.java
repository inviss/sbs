package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("pgm_info_tbl")
public class Program {
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("pds_program_id")
	private String pdsProgramId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("pgm_id")
	private Long pgmId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("media_cd")
	private String mediaCd;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("pgm_nm")
	private String pgmNm;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("chan_cd")
	private String chanCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("pgm_cd")
	private String pgmCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}

	public String getPgmCd() {
		return pgmCd;
	}

	public void setPgmCd(String pgmCd) {
		this.pgmCd = pgmCd;
	}

	public String getPdsProgramId() {
		return pdsProgramId;
	}

	public void setPdsProgramId(String pdsProgramId) {
		this.pdsProgramId = pdsProgramId;
	}

	public Long getPgmId() {
		return pgmId;
	}

	public void setPgmId(Long pgmId) {
		this.pgmId = pgmId;
	}

	public String getMediaCd() {
		return mediaCd;
	}

	public void setMediaCd(String mediaCd) {
		this.mediaCd = mediaCd;
	}

	public String getPgmNm() {
		return pgmNm;
	}

	public void setPgmNm(String pgmNm) {
		this.pgmNm = pgmNm;
	}

	public String getChanCd() {
		return chanCd;
	}

	public void setChanCd(String chanCd) {
		this.chanCd = chanCd;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
}

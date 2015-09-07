package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("contents_inst_tbl")
public class ContentInst {
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("cti_id")
	private Long ctiId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("ct_id")
	private Long ctId;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("cti_fmt")
	private String ctiFmt;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("arch_ste_yn")
	private String archSteYn;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("drp_frm_yn")
	private String drpFrmYn;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("vd_hresol")
	private Integer vdHresol;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("vd_vresol")
	private Integer vdVreSol;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("audio_yn")
	private String audioYn;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("audio_type")
	private String audioType;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("file_path")
	private String filePath;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("org_file_nm")
	private String orgFileNm;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("fl_sz")
	private Long flSz;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("bit_rt")
	private String	bitRt;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("color_cd")
	private String colorCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("record_type_cd")
	private String recordTypeCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("audio_lan_cd")
	private String audLanCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("audio_samp_frq")
	private String audSampFrq;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("audio_bdwt")
	private String audioBdwt;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("noi_rduc_typ_cd")
	private String noiRducTypCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("onairmedia_audiochannel")
	private String onAirMediaAudioChannel;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("frm_per_sec")
	private String frmPerSec;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("me_cd")
	private String meCd;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("ingest_eq_id")
	private String ingestEqId;

	@XStreamConverter(TextConverter.class)
	@XStreamAlias("catalog_yn")
	private String catalogYn;

	
	
	
	public String getCatalogYn() {
		return catalogYn;
	}

	public void setCatalogYn(String catalogYn) {
		this.catalogYn = catalogYn;
	}

	public String getAudioType() {
		return audioType;
	}

	public void setAudioType(String audioType) {
		this.audioType = audioType;
	}

	public String getFrmPerSec() {
		return frmPerSec;
	}

	public void setFrmPerSec(String frmPerSec) {
		this.frmPerSec = frmPerSec;
	}

	public String getMeCd() {
		return meCd;
	}

	public void setMeCd(String meCd) {
		this.meCd = meCd;
	}

	public String getIngestEqId() {
		return ingestEqId;
	}

	public void setIngestEqId(String ingestEqId) {
		this.ingestEqId = ingestEqId;
	}

	public String getBitRt() {
		return bitRt;
	}

	public void setBitRt(String bitRt) {
		this.bitRt = bitRt;
	}

	public String getColorCd() {
		return colorCd;
	}

	public void setColorCd(String colorCd) {
		this.colorCd = colorCd;
	}

	public String getRecordTypeCd() {
		return recordTypeCd;
	}

	public void setRecordTypeCd(String recordTypeCd) {
		this.recordTypeCd = recordTypeCd;
	}

	public String getAudLanCd() {
		return audLanCd;
	}

	public void setAudLanCd(String audLanCd) {
		this.audLanCd = audLanCd;
	}

	public String getAudSampFrq() {
		return audSampFrq;
	}

	public void setAudSampFrq(String audSampFrq) {
		this.audSampFrq = audSampFrq;
	}

	public String getAudioBdwt() {
		return audioBdwt;
	}

	public void setAudioBdwt(String audioBdwt) {
		this.audioBdwt = audioBdwt;
	}

	public String getNoiRducTypCd() {
		return noiRducTypCd;
	}

	public void setNoiRducTypCd(String noiRducTypCd) {
		this.noiRducTypCd = noiRducTypCd;
	}

	public String getRegrid() {
		return regrid;
	}

	public void setRegrid(String regrid) {
		this.regrid = regrid;
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

	public String getCtiFmt() {
		return ctiFmt;
	}

	public void setCtiFmt(String ctiFmt) {
		this.ctiFmt = ctiFmt;
	}

	public String getArchSteYn() {
		return archSteYn;
	}

	public void setArchSteYn(String archSteYn) {
		this.archSteYn = archSteYn;
	}

	public String getDrpFrmYn() {
		return drpFrmYn;
	}

	public void setDrpFrmYn(String drpFrmYn) {
		this.drpFrmYn = drpFrmYn;
	}

	public Integer getVdHresol() {
		return vdHresol;
	}

	public void setVdHresol(Integer vdHresol) {
		this.vdHresol = vdHresol;
	}

	public Integer getVdVreSol() {
		return vdVreSol;
	}

	public void setVdVreSol(Integer vdVreSol) {
		this.vdVreSol = vdVreSol;
	}

	public String getAudioYn() {
		return audioYn;
	}

	public void setAudioYn(String audioYn) {
		this.audioYn = audioYn;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	public Long getFlSz() {
		return flSz;
	}

	public void setFlSz(Long flSz) {
		this.flSz = flSz;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getOnAirMediaAudioChannel() {
		return onAirMediaAudioChannel;
	}

	public void setOnAirMediaAudioChannel(String onAirMediaAudioChannel) {
		this.onAirMediaAudioChannel = onAirMediaAudioChannel;
	}
	
	
}

package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("attach_tbl")
public class Attach {
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("mothr_data_id")
	private long mothrDataId;
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("seq")
	private int seq;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("attc_file_type_cd")
	private String attcFileTypeCd;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("attc_clf_cd")
	private String attcClfCd;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("fl_nm")
	private String flNm;
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("file_size")
	private long flSz;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("file_path")
	private String flPath;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("org_file_nm")
	private String orgFileNm;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("reg_dt")
	private String regDt;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("mod_dt")
	private String modDt;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("modrid")
	private String modrid;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("caption_type")
	private String captionType;
	
	public long getMothrDataId() {
		return mothrDataId;
	}
	public void setMothrDataId(long mothrDataId) {
		this.mothrDataId = mothrDataId;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getAttcFileTypeCd() {
		return attcFileTypeCd;
	}
	public void setAttcFileTypeCd(String attcFileTypeCd) {
		this.attcFileTypeCd = attcFileTypeCd;
	}
	public String getAttcClfCd() {
		return attcClfCd;
	}
	public void setAttcClfCd(String attcClfCd) {
		this.attcClfCd = attcClfCd;
	}
	public String getFlNm() {
		return flNm;
	}
	public void setFlNm(String flNm) {
		this.flNm = flNm;
	}
	public long getFlSz() {
		return flSz;
	}
	public void setFlSz(long flSz) {
		this.flSz = flSz;
	}
	public String getFlPath() {
		return flPath;
	}
	public void setFlPath(String flPath) {
		this.flPath = flPath;
	}
	public String getOrgFileNm() {
		return orgFileNm;
	}
	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
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
	public String getCaptionType() {
		return captionType;
	}
	public void setCaptionType(String captionType) {
		this.captionType = captionType;
	}
	
}

package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("annot")
public class Annot {
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("som")
	private String som;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("eom")
	private String eom;
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("clf_cd")
	private String clfCd;
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("clf_cont")
	private String clfCont;
	
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
	public String getClfCd() {
		return clfCd;
	}
	public void setClfCd(String clfCd) {
		this.clfCd = clfCd;
	}
	public String getClfCont() {
		return clfCont;
	}
	public void setClfCont(String clfCont) {
		this.clfCont = clfCont;
	}
	
}

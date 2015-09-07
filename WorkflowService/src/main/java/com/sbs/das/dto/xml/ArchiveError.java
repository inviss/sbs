package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("error")
public class ArchiveError {
	
	@XStreamConverter(TextUTF8Converter.class)
	private String code;
	
	@XStreamConverter(TextUTF8Converter.class)
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

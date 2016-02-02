package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("data")
public class Data {
	
	/*
	 * 프로그램 정보
	 */
	@XStreamAlias("pgm")
	private Program program = null;
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	
	/*
	 * 회차 정보
	 */
	@XStreamAlias("metadata")
	private Metadata metadata = null;
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	/*
	 * 본방 코너 정보
	 */
	private Corners corners = null;
	public Corners getCorners() {
		return corners;
	}
	public void setCorners(Corners corners) {
		this.corners = corners;
	}
}

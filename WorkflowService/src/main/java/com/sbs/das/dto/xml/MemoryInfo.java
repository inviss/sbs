package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.DoubleConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("memory")
public class MemoryInfo {
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("use")
	private Double total;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("use")
	private Double use;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("free")
	private Double free;

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getUse() {
		return use;
	}

	public void setUse(Double use) {
		this.use = use;
	}

	public Double getFree() {
		return free;
	}

	public void setFree(Double free) {
		this.free = free;
	}
}

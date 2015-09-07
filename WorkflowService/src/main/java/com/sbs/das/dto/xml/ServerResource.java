package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.DoubleConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("resource")
public class ServerResource {
	
	@XStreamAlias("cpu")
	private CpuInfo cpuInfo;
	
	@XStreamAlias("memory")
	private MemoryInfo memoryInfo;
	
	public CpuInfo getCpuInfo() {
		return cpuInfo;
	}
	public void setCpuInfo(CpuInfo cpuInfo) {
		this.cpuInfo = cpuInfo;
	}
	public MemoryInfo getMemoryInfo() {
		return memoryInfo;
	}
	public void setMemoryInfo(MemoryInfo memoryInfo) {
		this.memoryInfo = memoryInfo;
	}
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("user_time")
	private Double userTime;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("sys_time")
	private Double sysTime;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("idle_time")
	private Double idleTime;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("wait_time")
	private Double waitTime;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("nice_time")
	private Double niceTime;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("combined")
	private Double combined;
	
	@XStreamConverter(DoubleConverter.class)
	@XStreamAlias("irq_time")
	private Double irqTime;
	
	public Double getUserTime() {
		return userTime;
	}
	public void setUserTime(Double userTime) {
		this.userTime = userTime;
	}
	public Double getSysTime() {
		return sysTime;
	}
	public void setSysTime(Double sysTime) {
		this.sysTime = sysTime;
	}
	public Double getIdleTime() {
		return idleTime;
	}
	public void setIdleTime(Double idleTime) {
		this.idleTime = idleTime;
	}
	public Double getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(Double waitTime) {
		this.waitTime = waitTime;
	}
	public Double getNiceTime() {
		return niceTime;
	}
	public void setNiceTime(Double niceTime) {
		this.niceTime = niceTime;
	}
	public Double getCombined() {
		return combined;
	}
	public void setCombined(Double combined) {
		this.combined = combined;
	}
	public Double getIrqTime() {
		return irqTime;
	}
	public void setIrqTime(Double irqTime) {
		this.irqTime = irqTime;
	}
	
	
}

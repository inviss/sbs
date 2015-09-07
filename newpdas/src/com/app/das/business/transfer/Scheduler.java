package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 첨부파일
 * @author Administrator
 *
 */
@XmlRootElement(name="scheduler")
@XmlAccessorType(XmlAccessType.FIELD)
public class Scheduler {

	@XmlElement(name="SCHEDULER_NM")
	private String schedulerNm;
	@XmlElement(name="RUN_DT")
	private String runDt;
	@XmlElement(name="NEXT_RUN_DT")
	private String nextRunDt;
	@XmlElement(name="PATTERN")
	private String pattern;
	@XmlElement(name="RUN_YN")
	private String runYn;
	@XmlElement(name="DESC")
	private String desc;
	
	//내부사용변수
	private String interval;
	private String timeDiffer;
	
	
	
	public String getInterval() {
		return interval;
	}
	public void setInterval(String interval) {
		this.interval = interval;
	}
	public String getTimeDiffer() {
		return timeDiffer;
	}
	public void setTimeDiffer(String timeDiffer) {
		this.timeDiffer = timeDiffer;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSchedulerNm() {
		return schedulerNm;
	}
	public void setSchedulerNm(String schedulerNm) {
		this.schedulerNm = schedulerNm;
	}
	public String getRunDt() {
		return runDt;
	}
	public void setRunDt(String runDt) {
		this.runDt = runDt;
	}
	public String getNextRunDt() {
		return nextRunDt;
	}
	public void setNextRunDt(String nextRunDt) {
		this.nextRunDt = nextRunDt;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getRunYn() {
		return runYn;
	}
	public void setRunYn(String runYn) {
		this.runYn = runYn;
	}
	 
	
}

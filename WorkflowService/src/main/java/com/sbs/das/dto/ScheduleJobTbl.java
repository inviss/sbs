package com.sbs.das.dto;

import java.sql.Timestamp;

public class ScheduleJobTbl extends BaseObject {
	
	private static final long serialVersionUID = -3843178718441535575L;
	
	String 		jobId;
	Timestamp 	startDt;
	Timestamp	endDt;
	String		execYn;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public Timestamp getStartDt() {
		return startDt;
	}
	public void setStartDt(Timestamp startDt) {
		this.startDt = startDt;
	}
	public Timestamp getEndDt() {
		return endDt;
	}
	public void setEndDt(Timestamp endDt) {
		this.endDt = endDt;
	}
	public String getExecYn() {
		return execYn;
	}
	public void setExecYn(String execYn) {
		this.execYn = execYn;
	}
	
}

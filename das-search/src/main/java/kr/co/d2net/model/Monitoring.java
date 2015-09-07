package kr.co.d2net.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import kr.co.d2net.commons.utils.JaxbDateSerializer;

@XmlRootElement(name="monitoring")
@XmlAccessorType(XmlAccessType.FIELD)
public class Monitoring {
	
	@XmlElement(name="gubun")
	private String gubun;
	@XmlElement(name="key_id")
	private String keyId;
	@XmlElement(name="key")
	private String key;
	@XmlElement(name="job_nm")
	private String jobNm;
	@XmlElement(name="title")
	private String title;
	@XmlElement(name="progress")
	private String progress;
	@XmlElement(name="status")
	private String status;
	@XmlElement(name="start_time")
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	private Date startTime;

	@XmlElement(name="end_time")
	@XmlJavaTypeAdapter(JaxbDateSerializer.class)
	private Date endTime;
	@XmlElement(name="app_cont")
	private String appCont;
	
	
	
	public String getAppCont() {
		return appCont;
	}
	public void setAppCont(String appCont) {
		this.appCont = appCont;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getJobNm() {
		return jobNm;
	}
	public void setJobNm(String jobNm) {
		this.jobNm = jobNm;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

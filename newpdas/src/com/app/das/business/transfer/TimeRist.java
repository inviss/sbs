package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.ArrayList;

/**
 * 시간대별 사용등급 설정 DO
 * @author Administrator
 *
 */
@XmlRootElement(name="timerist")
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeRist {
	
	@XmlElement(name="timeristinfo")
	List<TimeRistInfo> items = new ArrayList<TimeRistInfo>();
	
	@XmlElement(name="result")
	String result;
	
	public List<TimeRistInfo> getItems() {
		return items;
	}
	public void setItems(List<TimeRistInfo> items) {
		this.items = items;
	}
	public void addItem(TimeRistInfo timeRistInfo) {
		this.items.add(timeRistInfo);
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}

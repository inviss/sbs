package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 사용제한 등급 or 주제영상
 * @author Administrator
 *
 */
@XmlRootElement(name="Annot")
@XmlAccessorType(XmlAccessType.FIELD)
public class Annot {

	@XmlElement(name="annotinfo")
	List<AnnotInfo> annotInfos = new ArrayList<AnnotInfo>();
	public List<AnnotInfo> getAnnotInfos() {
		return annotInfos;
	}
	public void setAnnotInfos(List<AnnotInfo> annotInfos) {
		this.annotInfos = annotInfos;
	}
	public void addAnnotInfo(AnnotInfo annotInfo) {
		this.annotInfos.add(annotInfo);
	}
	
	@XmlElement(name="Annot_item")
	List<AnnotInfo> items = new ArrayList<AnnotInfo>();
	public List<AnnotInfo> getItems() {
		return items;
	}
	public void setItems(List<AnnotInfo> items) {
		this.items = items;
	}
	public void addItem(AnnotInfo annotInfo) {
		this.items.add(annotInfo);
	}
	
}

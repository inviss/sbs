package com.app.das.business.transfer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;
import java.util.ArrayList;

/**
 * 프로그램 관련 영상정보
 * @author Administrator
 *
 */
@XmlRootElement(name="ingest")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ingest {
	
	@XmlElement(name="ingestinfo")
	List<IngestInfo> items = new ArrayList<IngestInfo>();
	
	public List<IngestInfo> getItems() {
		return items;
	}
	public void setItems(List<IngestInfo> items) {
		this.items = items;
	}
	public void addItem(IngestInfo ingestInfo) {
		this.items.add(ingestInfo);
	}
	
}

package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 

@XmlRootElement(name="das")
@XmlAccessorType(XmlAccessType.FIELD)
public class Das {
	
	@XmlElement(name="node")
	private List<Node> nodes = new ArrayList<Node>();
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	@XmlElement(name="meta")
	private MetaDataInfo metaDataInfo;
	public MetaDataInfo getMetaDataInfo() {
		return metaDataInfo;
	}
	public void setMetaDataInfo(MetaDataInfo metaDataInfo) {
		this.metaDataInfo = metaDataInfo;
	}
	

	@XmlElement(name="ingest")
	private Ingest ingest;
	public Ingest getIngest() {
		return ingest;
	}
	public void setIngest(Ingest ingest) {
		this.ingest = ingest;
	}
	
	@XmlElement(name="relation")
	private Relation relation;
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	
	@XmlElement(name="attach")
	private Attach attach;
	public Attach getAttach() {
		return attach;
	}
	public void setAttach(Attach attach) {
		this.attach = attach;
	}
	
	@XmlElement(name="annot")
	private Annot annot;
	public Annot getAnnot() {
		return annot;
	}
	public void setAnnot(Annot annot) {
		this.annot = annot;
	}
	@XmlElement(name="corner")
	private Corner corner;
	public Corner getCorner() {
		return corner;
	}
	public void setCorner(Corner corner) {
		this.corner = corner;
	}

	@XmlElement(name="timerist")
	private TimeRist timeRist;
	public TimeRist getTimeRist() {
		return timeRist;
	}
	public void setTimeRist(TimeRist timeRist) {
		this.timeRist = timeRist;
	}
	
	@XmlElement(name="statisticsInfo")
	List<StatisticsInfo> items = new ArrayList<StatisticsInfo>();
	public List<StatisticsInfo> getItems() {
		return items;
	}
	public void setItems(List<StatisticsInfo> items) {
		this.items = items;
	}
	
	@XmlElement(name="scheduler")	 
	private List<Scheduler> scheduler ;
	public List<Scheduler> getScheduler() {
		return scheduler;
	}
	public void setScheduler(List<Scheduler> scheduler) {
		this.scheduler = scheduler;
	}
	
	@XmlElement(name="year")	 
	private String year = null;
	
	@XmlElement(name="TAPE_MEDIA_CLF_CD")	 
	private String tapeMediaClfCd = null;
	
	@XmlElement(name="Username")	 
	private String userName = null;
	
	@XmlElement(name="TAPE_CLF")	 
	private String tapeClf = null;
	
	@XmlElement(name="UserID")	 
	private String userId = null;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@XmlElement(name="master_id")	 
	private Long masterId;
 
	public Long getMasterId() {
		return masterId;
	}
	
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	
	public String getTapeMediaClfCd() {
		return tapeMediaClfCd;
	}
	public void setTapeMediaClfCd(String tapeMediaClfCd) {
		this.tapeMediaClfCd = tapeMediaClfCd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTapeClf() {
		return tapeClf;
	}
	public void setTapeClf(String tapeClf) {
		this.tapeClf = tapeClf;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}

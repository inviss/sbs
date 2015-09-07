package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="info")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContentInfo {
	
	@XmlElement(name="cms_id")
	private String cmsId;
	@XmlElement(name="folder_id")
	private String folderId;
	@XmlElement(name="cti_id")
	private String ctiId;
	@XmlElement(name="som")
	private String som;
	@XmlElement(name="eom")
	private String eom;
	@XmlElement(name="req_id")
	private String reqId;
	
	public String getCmsId() {
		return cmsId;
	}
	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getCtiId() {
		return ctiId;
	}
	public void setCtiId(String ctiId) {
		this.ctiId = ctiId;
	}
	public String getSom() {
		return som;
	}
	public void setSom(String som) {
		this.som = som;
	}
	public String getEom() {
		return eom;
	}
	public void setEom(String eom) {
		this.eom = eom;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
}

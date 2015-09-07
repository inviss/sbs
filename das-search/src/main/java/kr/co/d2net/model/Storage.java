package kr.co.d2net.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="storage")
@XmlAccessorType(XmlAccessType.FIELD)
public class Storage {

	@XmlElement(name="system_id")
	private String systemId;
	@XmlElement(name="storage_nm")
	private String storageNm;
	@XmlElement(name="total_size")
	private Long totalSize;
	@XmlElement(name="use_size")
	private Long useSize;
	@XmlElement(name="path")
	private String path;
	@XmlElement(name="passible_size")
	private Long passibleSize;
	@XmlElement(name="limit")
	private String limit;

	
	public String getStorageNm() {
		return storageNm;
	}
	public void setStorageNm(String storageNm) {
		this.storageNm = storageNm;
	}
	public Long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Long totalSize) {
		this.totalSize = totalSize;
	}
	public Long getUseSize() {
		return useSize;
	}
	public void setUseSize(Long useSize) {
		this.useSize = useSize;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getPassibleSize() {
		return passibleSize;
	}
	public void setPassibleSize(Long passibleSize) {
		this.passibleSize = passibleSize;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	
}

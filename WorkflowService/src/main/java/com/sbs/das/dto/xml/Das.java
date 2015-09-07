package com.sbs.das.dto.xml;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("das")
public class Das {
	
	@XStreamAlias("info")
	private RequestInfo info;
	
	@XStreamAlias("db_table")
	private DBTable dbTable;
	
	@XStreamAlias("report")
	private Report report;
	
	@XStreamAlias("storageInfo")
	private StorageInfo storageInfo;

	@XStreamImplicit(itemFieldName="status")
	private List<ArchiveStatus> status = new ArrayList<ArchiveStatus>();
	
	public List<ArchiveStatus> getStatus() {
		return status;
	}
	public void setStatus(List<ArchiveStatus> status) {
		this.status = status;
	}
	public void addStatus(ArchiveStatus archiveStatus) {
		status.add(archiveStatus);
	}
	public RequestInfo getInfo() {
		return info;
	}
	public void setInfo(RequestInfo info) {
		this.info = info;
	}
	public DBTable getDbTable() {
		return dbTable;
	}
	public void setDbTable(DBTable dbTable) {
		this.dbTable = dbTable;
	}
	public Report getReport() {
		return report;
	}
	public void setReport(Report report) {
		this.report = report;
	}
	public StorageInfo getStorageInfo() {
		return storageInfo;
	}
	public void setStorageInfo(StorageInfo storageInfo) {
		this.storageInfo = storageInfo;
	}
	
	@XStreamImplicit(itemFieldName="resource")
	private List<ServerResource> resources = new ArrayList<ServerResource>();
	public List<ServerResource> getResources() {
		return resources;
	}
	public void setResources(List<ServerResource> resources) {
		this.resources = resources;
	}
	public void addResources(ServerResource serverResource) {
		this.resources.add(serverResource);
	}
	
	@XStreamImplicit(itemFieldName="storage")
	private List<StorageInfo> storages = new ArrayList<StorageInfo>();
	public List<StorageInfo> getStorages() {
		return storages;
	}
	public void setStorages(List<StorageInfo> storages) {
		this.storages = storages;
	}
	public void addStorages(StorageInfo storageInfo) {
		this.storages.add(storageInfo);
	}
	
	@XStreamImplicit(itemFieldName="delete")
	private List<DeleteRequest> deleteList = new ArrayList<DeleteRequest>();
	public List<DeleteRequest> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<DeleteRequest> deleteList) {
		this.deleteList = deleteList;
	}
	public void addDeleteList(DeleteRequest deleteRequest) {
		this.deleteList.add(deleteRequest);
	}
	
	
}

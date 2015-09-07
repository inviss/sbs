package com.sbs.das.dto.xml;

import com.sbs.das.commons.convertor.IntegerConverter;
import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

@XStreamAlias("request")
public class ArchiveRequest {
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("object_name")
	private String objectName;
	
	// 2012.05.18 추가
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("archive_type")
	private String archiveType;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("copy_to_group")
	private String copyToGroup;
	
	@XStreamConverter(TextConverter.class)
	private String group;
	
	@XStreamConverter(TextUTF8Converter.class)
	private String source;
	
	@XStreamConverter(IntegerConverter.class)
	private Integer priority;
	
	// 2012.05.18 추가
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("archive_priority")
	private Integer archivePriority;
	
	// 2012.05.18 추가
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("restore_priority")
	private Integer restorePriority;
	
	@XStreamConverter(TextUTF8Converter.class)
	private String category;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("target_filepath")
	private String targetFilePath;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("target_filename")
	private String targetFilename;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("start_tc")
	private String startTc;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("end_tc")
	private String endTc;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("restore_id")
	private Long restoreId;
	
	// 2012.05.18 추가
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("restore_qos")
	private Integer restoreQos;
	
	// 2012.05.18 추가
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("archive_qos")
	private Integer archiveQos;
	
	@XStreamConverter(IntegerConverter.class)
	@XStreamAlias("qos")
	private Integer qos;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("destinations")
	private String destination;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("filename")
	private String filename;
	
	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("filepath")
	private String filepath;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("dtl_type")
	private String dtlType;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("vd_qlty")
	private String vdQlty;
	
	private String reqDt;
	
	
	public String getVdQlty() {
		return vdQlty;
	}

	public void setVdQlty(String vdQlty) {
		this.vdQlty = vdQlty;
	}

	public String getDtlType() {
		return dtlType;
	}

	public void setDtlType(String dtlType) {
		this.dtlType = dtlType;
	}

	public Integer getArchivePriority() {
		return archivePriority;
	}

	public void setArchivePriority(Integer archivePriority) {
		this.archivePriority = archivePriority;
	}

	public Integer getRestorePriority() {
		return restorePriority;
	}

	public void setRestorePriority(Integer restorePriority) {
		this.restorePriority = restorePriority;
	}

	public Integer getRestoreQos() {
		return restoreQos;
	}

	public void setRestoreQos(Integer restoreQos) {
		this.restoreQos = restoreQos;
	}

	public Integer getArchiveQos() {
		return archiveQos;
	}

	public void setArchiveQos(Integer archiveQos) {
		this.archiveQos = archiveQos;
	}

	public String getArchiveType() {
		return archiveType;
	}

	public void setArchiveType(String archiveType) {
		this.archiveType = archiveType;
	}

	public String getReqDt() {
		return reqDt;
	}

	public void setReqDt(String reqDt) {
		this.reqDt = reqDt;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getQos() {
		return qos;
	}

	public void setQos(Integer qos) {
		this.qos = qos;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Long getRestoreId() {
		return restoreId;
	}

	public void setRestoreId(Long restoreId) {
		this.restoreId = restoreId;
	}

	public String getTargetFilename() {
		return targetFilename;
	}

	public void setTargetFilename(String targetFilename) {
		this.targetFilename = targetFilename;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTargetFilePath() {
		return targetFilePath;
	}

	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
	}

	public String getStartTc() {
		return startTc;
	}

	public void setStartTc(String startTc) {
		this.startTc = startTc;
	}

	public String getEndTc() {
		return endTc;
	}

	public void setEndTc(String endTc) {
		this.endTc = endTc;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getCopyToGroup() {
		return copyToGroup;
	}

	public void setCopyToGroup(String copyToGroup) {
		this.copyToGroup = copyToGroup;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
}

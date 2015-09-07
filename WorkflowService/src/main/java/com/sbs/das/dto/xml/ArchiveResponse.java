package com.sbs.das.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("response")
public class ArchiveResponse {
	
	@XStreamAlias("task_id")
	private Long taskId;
	
	@XStreamAlias("object_name")
	private String objectName;
	
	@XStreamAlias("restore_id")
	private Long restoreId;
	
	@XStreamAlias("error")
	private ArchiveError archiveError;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Long getRestoreId() {
		return restoreId;
	}

	public void setRestoreId(Long restoreId) {
		this.restoreId = restoreId;
	}

	public ArchiveError getArchiveError() {
		return archiveError;
	}

	public void setArchiveError(ArchiveError archiveError) {
		this.archiveError = archiveError;
	}
	
}

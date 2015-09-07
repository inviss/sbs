package com.sbs.das.dto.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("db_table")
public class DBTable {
	
	@XStreamAlias("pgm_info_tbl")
	private Program program;
	
	@XStreamAlias("metadat_mst_tbl")
	private Master master;
	
	@XStreamAlias("contents_tbl")
	private Content content;
	
	@XStreamAlias("contents_inst_tbl")
	private ContentInst contentInst;
	
	@XStreamAlias("contents_mapp_tbl")
	private ContentMap contentMap;
	
	@XStreamAlias("work_log_tbl")
	private WorkLog workLog;
	
	@XStreamAlias("attach_tbl")
	private Attach attach;		/* 2014-10-13 추가 */
	
	
	public Attach getAttach() {
		return attach;
	}
	public void setAttach(Attach attach) {
		this.attach = attach;
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public Master getMaster() {
		return master;
	}
	public void setMaster(Master master) {
		this.master = master;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public ContentInst getContentInst() {
		return contentInst;
	}
	public void setContentInst(ContentInst contentInst) {
		this.contentInst = contentInst;
	}
	public ContentMap getContentMap() {
		return contentMap;
	}
	public void setContentMap(ContentMap contentMap) {
		this.contentMap = contentMap;
	}
	public WorkLog getWorkLog() {
		return workLog;
	}
	public void setWorkLog(WorkLog workLog) {
		this.workLog = workLog;
	}
	
}

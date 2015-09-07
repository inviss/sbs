package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;


/**
 * 아카이브 요청 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ArchiveReqDO {
	
	/** 
	 * 순번
	 */
	private long SEQ;
	/** 
	 * xml
	 */
	private String xml_cont = Constants.BLANK;
	/** 
	 * 할당 상태
	 */
	private String job_alocate = Constants.BLANK;
	
	/** 
	 * 할당 상태
	 */
	private String archive_id = Constants.BLANK;
	
	/** 
	 * 할당 상태
	 */
	private String archive_nm = Constants.BLANK;
	
	/** 
	 * 할당 상태
	 */
	private String archive_seq = Constants.BLANK;
	
	/** 
	 * 작업상태
	 */
	private String work_stat = Constants.BLANK;
	
	

	/** 
	 * 결과
	 */
	private String result = Constants.BLANK;
	
	
	
	
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public long getSEQ() {
		return SEQ;
	}
	public void setSEQ(long sEQ) {
		SEQ = sEQ;
	}
	public String getArchive_id() {
		return archive_id;
	}
	public void setArchive_id(String archiveId) {
		archive_id = archiveId;
	}
	public String getArchive_nm() {
		return archive_nm;
	}
	public void setArchive_nm(String archiveNm) {
		archive_nm = archiveNm;
	}
	public String getArchive_seq() {
		return archive_seq;
	}
	public void setArchive_seq(String archiveSeq) {
		archive_seq = archiveSeq;
	}
	public String getWork_stat() {
		return work_stat;
	}
	public void setWork_stat(String workStat) {
		work_stat = workStat;
	}
	public String getXml_cont() {
		return xml_cont;
	}
	public void setXml_cont(String xmlCont) {
		xml_cont = xmlCont;
	}
	public String getJob_alocate() {
		return job_alocate;
	}
	public void setJob_alocate(String jobAlocate) {
		job_alocate = jobAlocate;
	}

	
	
	
}

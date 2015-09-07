

package com.app.das.business.transfer;
/**
 * 사진 다운 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PhotDownDO extends DTO {
    
	  
	
	
	private long  seq;    
	private long master_id;
	private long phot_id;  
	private String req_id;
	private String req_dt;
	private String ctgr_l_cd;  
	private String ctgr_m_cd;
	private String ctgr_s_cd;
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public long getMaster_id() {
		return master_id;
	}
	public void setMaster_id(long masterId) {
		master_id = masterId;
	}
	public long getPhot_id() {
		return phot_id;
	}
	public void setPhot_id(long photId) {
		phot_id = photId;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String reqId) {
		req_id = reqId;
	}
	public String getReq_dt() {
		return req_dt;
	}
	public void setReq_dt(String reqDt) {
		req_dt = reqDt;
	}
	public String getCtgr_l_cd() {
		return ctgr_l_cd;
	}
	public void setCtgr_l_cd(String ctgrLCd) {
		ctgr_l_cd = ctgrLCd;
	}
	public String getCtgr_m_cd() {
		return ctgr_m_cd;
	}
	public void setCtgr_m_cd(String ctgrMCd) {
		ctgr_m_cd = ctgrMCd;
	}
	public String getCtgr_s_cd() {
		return ctgr_s_cd;
	}
	public void setCtgr_s_cd(String ctgrSCd) {
		ctgr_s_cd = ctgrSCd;
	}
	
	
	
	
	  
}
	

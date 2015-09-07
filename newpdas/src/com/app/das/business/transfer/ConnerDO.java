

package com.app.das.business.transfer;
/**
 * 코너정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class ConnerDO extends DTO {
    
	  
	
	public ConnerDO(){
		NUM = 0;
		CTI_ID = 0;
		OBJ_NAME = "";
		ASTATUS = "";
		DSTATUS = "";
		REG_USER = "";
		REG_USER = "";
		UPDT_USER = "";
		REG_DTM = "";
		UPDT_DTM = "";
		PROGRESS = "";
		USE_YN = "N";
		TITLE = "";
		JOB_STATUS = "";
		CYN = "N";
		CPROGRESS = "";
		SCOUNT = "";
		PATH="";
		wrk_file_nm="";
	}
	private long NUM;    
	private long CTI_ID;
	private String OBJ_NAME;  
	private String ASTATUS;
	private String DSTATUS;
	private String REG_USER;  
	private String UPDT_USER;
	private String REG_DTM;
	private String UPDT_DTM;
	private String PROGRESS;
	private String TITLE;
	private String JOB_STATUS;
	private String CYN;
	private String CPROGRESS;
	private String SCOUNT;	
	private String USE_YN;
	private String PATH;
	private String wrk_file_nm;
	private int page;
	
	public String getASTATUS() {
		return ASTATUS;
	}
	public void setASTATUS(String astatus) {
		ASTATUS = astatus;
	}
	public String getCPROGRESS() {
		return CPROGRESS;
	}
	public void setCPROGRESS(String cprogress) {
		CPROGRESS = cprogress;
	}
	public long getCTI_ID() {
		return CTI_ID;
	}
	public void setCTI_ID(long cti_id) {
		CTI_ID = cti_id;
	}
	public String getCYN() {
		return CYN;
	}
	public void setCYN(String cyn) {
		CYN = cyn;
	}
	public String getDSTATUS() {
		return DSTATUS;
	}
	public void setDSTATUS(String dstatus) {
		DSTATUS = dstatus;
	}
	public String getJOB_STATUS() {
		return JOB_STATUS;
	}
	public void setJOB_STATUS(String job_status) {
		JOB_STATUS = job_status;
	}
	public long getNUM() {
		return NUM;
	}
	public void setNUM(long num) {
		NUM = num;
	}
	public String getOBJ_NAME() {
		return OBJ_NAME;
	}
	public void setOBJ_NAME(String obj_name) {
		OBJ_NAME = obj_name;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getPROGRESS() {
		return PROGRESS;
	}
	public void setPROGRESS(String progress) {
		PROGRESS = progress;
	}
	public String getREG_DTM() {
		return REG_DTM;
	}
	public void setREG_DTM(String reg_dtm) {
		REG_DTM = reg_dtm;
	}
	public String getREG_USER() {
		return REG_USER;
	}
	public void setREG_USER(String reg_user) {
		REG_USER = reg_user;
	}
	public String getSCOUNT() {
		return SCOUNT;
	}
	public void setSCOUNT(String scount) {
		SCOUNT = scount;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String title) {
		TITLE = title;
	}
	public String getUPDT_DTM() {
		return UPDT_DTM;
	}
	public void setUPDT_DTM(String updt_dtm) {
		UPDT_DTM = updt_dtm;
	}
	public String getUPDT_USER() {
		return UPDT_USER;
	}
	public void setUPDT_USER(String updt_user) {
		UPDT_USER = updt_user;
	}
	public String getUSE_YN() {
		return USE_YN;
	}
	public void setUSE_YN(String use_yn) {
		USE_YN = use_yn;
	}
	public String getPATH() {
		return PATH;
	}
	public void setPATH(String path) {
		PATH = path;
	}
	public String getWrk_file_nm() {
		return wrk_file_nm;
	}
	public void setWrk_file_nm(String wrk_file_nm) {
		this.wrk_file_nm = wrk_file_nm;
	}
	  
}
	

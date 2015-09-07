package com.app.das.business.transfer;

import com.app.das.business.constants.Constants;
/**
 * 프로그램별 각종 외부 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PgmUserInfoDO {
	
	/** 
	 * 릴레이션id
	 */
	private long  RELATIONID;
	/** 
	 * 트리노드id
	 */
	private int TREENODEID;
	/** 
	 * 트리노드명
	 */
	private String NODENAME = Constants.BLANK;
	/** 
	 * 트리노드한글명
	 */
	private String CAPTION = Constants.BLANK;
	/** 
	 * 사용자id
	 */
	private int USERID;
	/** 
	 * 로그인id
	 */
	private String USERNAME = Constants.BLANK;
	/** 
	 * 사용자명
	 */
	private String REALNAME = Constants.BLANK;
	/** 
	 * 권한정보 1 읽기 2 읽기/쓰기 3 읽기/쓰기/삭제
	 */
	private int SUBTYPE;
	/** 
	 * 프로그램id
	 */
	private int PROGRAMID;
	/** 
	 * 스토리지nm
	 */
	private String STORAGENM;
	/** 
	 * 스토리지id
	 */
	private int STORAGEID;
	/** 
	 * 트리노드명
	 */
	private String treenodename;
	/** 
	 * 프로그램 코드
	 */
	private String programcd;
	
	/** 
	 * 프로그램명
	 */
	private String programnm;
	/** 
	 * 권한
	 */
	private String authority;
	/** 
	 * 유저id
	 */
	private String userid;
	
	/** 
	 * 유저명
	 */
	private String username;
	
	
	
	
	
	public String getSTORAGENM() {
		return STORAGENM;
	}
	public void setSTORAGENM(String sTORAGENM) {
		STORAGENM = sTORAGENM;
	}
	public String getTreenodename() {
		return treenodename;
	}
	public void setTreenodename(String treenodename) {
		this.treenodename = treenodename;
	}
	public String getProgramcd() {
		return programcd;
	}
	public void setProgramcd(String programcd) {
		this.programcd = programcd;
	}
	public String getProgramnm() {
		return programnm;
	}
	public void setProgramnm(String programnm) {
		this.programnm = programnm;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getRELATIONID() {
		return RELATIONID;
	}
	public void setRELATIONID(long rELATIONID) {
		RELATIONID = rELATIONID;
	}
	public int getTREENODEID() {
		return TREENODEID;
	}
	public void setTREENODEID(int tREENODEID) {
		TREENODEID = tREENODEID;
	}
	public String getNODENAME() {
		return NODENAME;
	}
	public void setNODENAME(String nODENAME) {
		NODENAME = nODENAME;
	}
	public String getCAPTION() {
		return CAPTION;
	}
	public void setCAPTION(String cAPTION) {
		CAPTION = cAPTION;
	}
	public int getUSERID() {
		return USERID;
	}
	public void setUSERID(int uSERID) {
		USERID = uSERID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getREALNAME() {
		return REALNAME;
	}
	public void setREALNAME(String rEALNAME) {
		REALNAME = rEALNAME;
	}
	public int getSUBTYPE() {
		return SUBTYPE;
	}
	public void setSUBTYPE(int sUBTYPE) {
		SUBTYPE = sUBTYPE;
	}
	public int getPROGRAMID() {
		return PROGRAMID;
	}
	public void setPROGRAMID(int pROGRAMID) {
		PROGRAMID = pROGRAMID;
	}
	public int getSTORAGEID() {
		return STORAGEID;
	}
	public void setSTORAGEID(int sTORAGEID) {
		STORAGEID = sTORAGEID;
	}
	
	
}

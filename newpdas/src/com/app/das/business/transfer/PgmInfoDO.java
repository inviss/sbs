package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
/**
 * 프로그램별 정보를 포함하고 있는 dataObject
 * @author asura207
 *
 */
public class PgmInfoDO {
	
	/** 
	 * 프로그램id
	 */
	private int  PROGRAMID;
	/** 
	 * 프로그램명
	 */
	private String PROGRAM_NAME = Constants.BLANK;
	/** 
	 * 프로그램그룹코드
	 */
	private String PROGRAM_CODE = Constants.BLANK;
	/** 
	 * 책임PD
	 */
	private String PRODUCER_NAME = Constants.BLANK;
	/** 
	 * 제작PD(연출담당자)
	 */
	private String PRODUCTION_PORDUCER_NAME = Constants.BLANK;
	/** 
	 * 제작PD 연락처
	 */
	private String PRODUCTION_PORDUCER_PHONE = Constants.BLANK;
	
	/** 
	 * 제작 cpdID
	 */
	private String cid = Constants.BLANK;
	
	/** 
	 * 책임 pd ID
	 */
	private String pid = Constants.BLANK;
	
	
	/** 
	 * 제작 cpdID 리스트
	 */
	private List cidList = new ArrayList();
	
	/** 
	 * 책임 pd ID 리스트
	 */
	private List pidList = new ArrayList();
	
	
	/** 
	 * 책임PD 명 리스트
	 */
	private List cpnmList = new ArrayList();
	/** 
	 * 제작PD명 리스트
	 */
	private List pdnmList =new ArrayList();
	
	////////////////////////////// 프로그램 승인용 정보
	
	/** 
	 * id
	 */
	private String user_id = Constants.BLANK;
	
	/** 
	 * 사번
	 */
	private String user_no = Constants.BLANK;
	
	/** 
	 * 부서코드
	 */
	private String dept_cd =Constants.BLANK;
	

	/** 
	 * 직책
	 */
	private String position =Constants.BLANK;
	
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String userId) {
		user_id = userId;
	}
	public String getUser_no() {
		return user_no;
	}
	public void setUser_no(String userNo) {
		user_no = userNo;
	}
	public String getDept_cd() {
		return dept_cd;
	}
	public void setDept_cd(String deptCd) {
		dept_cd = deptCd;
	}
	public List getCidList() {
		return cidList;
	}
	public void setCidList(List cidList) {
		this.cidList = cidList;
	}
	public List getPidList() {
		return pidList;
	}
	public void setPidList(List pidList) {
		this.pidList = pidList;
	}
	public List getCpnmList() {
		return cpnmList;
	}
	public void setCpnmList(List cpnmList) {
		this.cpnmList = cpnmList;
	}
	public List getPdnmList() {
		return pdnmList;
	}
	public void setPdnmList(List pdnmList) {
		this.pdnmList = pdnmList;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public int getPROGRAMID() {
		return PROGRAMID;
	}
	public void setPROGRAMID(int pROGRAMID) {
		PROGRAMID = pROGRAMID;
	}
	public String getPROGRAM_NAME() {
		return PROGRAM_NAME;
	}
	public void setPROGRAM_NAME(String pROGRAMNAME) {
		PROGRAM_NAME = pROGRAMNAME;
	}
	public String getPROGRAM_CODE() {
		return PROGRAM_CODE;
	}
	public void setPROGRAM_CODE(String pROGRAMCODE) {
		PROGRAM_CODE = pROGRAMCODE;
	}
	public String getPRODUCER_NAME() {
		return PRODUCER_NAME;
	}
	public void setPRODUCER_NAME(String pRODUCERNAME) {
		PRODUCER_NAME = pRODUCERNAME;
	}
	public String getPRODUCTION_PORDUCER_NAME() {
		return PRODUCTION_PORDUCER_NAME;
	}
	public void setPRODUCTION_PORDUCER_NAME(String pRODUCTIONPORDUCERNAME) {
		PRODUCTION_PORDUCER_NAME = pRODUCTIONPORDUCERNAME;
	}
	public String getPRODUCTION_PORDUCER_PHONE() {
		return PRODUCTION_PORDUCER_PHONE;
	}
	public void setPRODUCTION_PORDUCER_PHONE(String pRODUCTIONPORDUCERPHONE) {
		PRODUCTION_PORDUCER_PHONE = pRODUCTIONPORDUCERPHONE;
	}
	
	
}

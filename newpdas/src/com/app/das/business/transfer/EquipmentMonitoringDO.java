package com.app.das.business.transfer;

import java.math.BigDecimal;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 모니터링 장비의 로그 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class EquipmentMonitoringDO extends DTO 
{
	/**
	 * 순번
	 */
	private int serialNo;
	/**
	 * 일자
	 */
	private String dd               = Constants.BLANK;
	/**
	 * 장비명
	 */
	private String dasEqNm          = Constants.BLANK;
	/**
	 * 장비구분코드
	 */
	private String dasEqClfCd       = Constants.BLANK;
	/**
	 * 장비구분명
	 */
	private String dasEqClfNm       = Constants.BLANK;
	/**
	 * 작업상태코드
	 */
	private String dasWorkstatCd    = Constants.BLANK;
	/**
	 * 작업상태명
	 */
	private String dasWorkstatNm    = Constants.BLANK;
	/**
	 * 장비 프로세스명
	 */
	private String dasEqPsNm = Constants.BLANK;
	/**
	 * 작업률
	 */
	private String prgrs = Constants.BLANK;
	/**
	 * 콘텐츠 인스턴스 ID
	 */
	private String ctiId            = Constants.BLANK;
	/**
	 * 요청자ID
	 */
	private String reqUsrId         = Constants.BLANK;
	/**
	 * 요청자명
	 */
	private String userNm = Constants.BLANK;
	/**
	 * 요청시간
	 */
	private String reqTm		= Constants.BLANK;
	/**
	 * 등록 일시
	 */
	private String regDd		= Constants.BLANK;
	/**
	 * 콘텐츠명
	 */
	private String ctNm 		= Constants.BLANK;
	/**
	 * 콘텐츠ID 
	 */
	private String ctId = Constants.BLANK;
	/**
	 * 장비 중지여부
	 */
	private String equStop = Constants.BLANK;
	/**
	 * 마스터ID
	 */
	private String masterId = Constants.BLANK;
	/**
	 * 장비 ID
	 */
	private int dasEqId;
	/**
	 * DAS 장비 사용 IP
	 */
	private String eqUseIp = Constants.BLANK;
	/**
	 * DAS 장비 사용 Port 
	 */
	private String eqUsePort = Constants.BLANK;
	/**
	 * DAS 장비 순번 
	 */
	private int dasEqSeq;
	
	
	public int getDasEqSeq() {
		return dasEqSeq;
	}
	public void setDasEqSeq(int dasEqSeq) {
		this.dasEqSeq = dasEqSeq;
	}
	public String getCtiId() {
		return ctiId;
	}
	public void setCtiId(String ctiId) {
		this.ctiId = ctiId;
	}
	public String getDasEqClfCd() {
		return dasEqClfCd;
	}
	public void setDasEqClfCd(String dasEqClfCd) {
		this.dasEqClfCd = dasEqClfCd;
	}
	public String getDasEqNm() {
		return dasEqNm;
	}
	public void setDasEqNm(String dasEqNm) {
		this.dasEqNm = dasEqNm;
	}
	public String getDasWorkstatCd() {
		return dasWorkstatCd;
	}
	public void setDasWorkstatCd(String dasWorkstatCd) {
		this.dasWorkstatCd = dasWorkstatCd;
	}
	public String getDd() {
		return dd;
	}
	public void setDd(String dd) {
		this.dd = dd;
	}
	public String getReqTm() {
		return reqTm;
	}
	public void setReqTm(String reqTm) {
		this.reqTm = reqTm;
	}
	public String getRegDd() {
		return regDd;
	}
	public void setRegDd(String regDd) {
		this.regDd = regDd;
	}
	public String getReqUsrId() {
		return reqUsrId;
	}
	public void setReqUsrId(String reqUsrId) {
		this.reqUsrId = reqUsrId;
	}
	public String getDasEqClfNm() {
		return dasEqClfNm;
	}
	public void setDasEqClfNm(String dasEqClfNm) {
		this.dasEqClfNm = dasEqClfNm;
	}
	public String getDasWorkstatNm() {
		return dasWorkstatNm;
	}
	public void setDasWorkstatNm(String dasWorkstatNm) {
		this.dasWorkstatNm = dasWorkstatNm;
	}
	public String getPrgrs() {
		return prgrs;
	}
	public void setPrgrs(String prgrs) {
		this.prgrs = prgrs;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getCtNm() {
		return ctNm;
	}
	public void setCtNm(String ctNm) {
		this.ctNm = ctNm;
	}
	public String getCtId() {
		return ctId;
	}
	public void setCtId(String ctId) {
		this.ctId = ctId;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public String getEquStop() {
		return equStop;
	}
	public void setEquStop(String equStop) {
		this.equStop = equStop;
	}
	public String getDasEqPsNm() {
		return dasEqPsNm;
	}
	public void setDasEqPsNm(String dasEqPsNm) {
		this.dasEqPsNm = dasEqPsNm;
	}
	public int getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}
	public int getDasEqId() {
		return dasEqId;
	}
	public void setDasEqId(int dasEqId) {
		this.dasEqId = dasEqId;
	}
	public String getEqUseIp() {
		return eqUseIp;
	}
	public void setEqUseIp(String eqUseIp) {
		this.eqUseIp = eqUseIp;
	}
	public String getEqUsePort() {
		return eqUsePort;
	}
	public void setEqUsePort(String eqUsePort) {
		this.eqUsePort = eqUsePort;
	}
	
	
	
	}

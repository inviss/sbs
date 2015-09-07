package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.List;

import com.app.das.business.constants.Constants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.DTO;

/**
 *  NDS 정보를 포함하고 있는 DataObject
 * @author ysk523
 *
 */
public class NdsDownDO extends DTO 
{

	/**
	 * 메타데이터 genrator 이름
	 */
	private String generator_name      = Constants.BLANK;
	/**
	 *  메타데이터 genrator 버전
	 */
	private String generator_ver         = Constants.BLANK;
	/**
	 * 총 스트림 사이즈
	 */
	private String total_stream_size           = Constants.BLANK;
	/**
	 * 스트림 경로
	 */
	private String streams_path             = Constants.BLANK;
	/**
	 * 미디어 타입
	 */
	private String mediatypecode           = Constants.BLANK;
	/**
	 * 미디어 id
	 */
	private String mediaid            = Constants.BLANK;
	/**
	 * 제목
	 */
	private String title            = Constants.BLANK;
	/**
	 * 부제
	 */
	private String sutitle           = Constants.BLANK;
	/**
	 * 물리적 트리구조
	 */
	private String physicaltree = Constants.BLANK;
	/**
	 * 논리적 트리구조
	 */
	private String logicaltree           = Constants.BLANK;
	/**
	 * 작업자명
	 */
	private String workername 		= Constants.BLANK;
	
	
	/**
	 * 카트번호
	 */
	private int cart_no;
	/**
	 * 카트시퀀스
	 * 	 */
	private int cart_seq;
	/**
	 * 다운로드명
	 * 	 */
	private String down_nm 		= Constants.BLANK;
	
	/**
	 * 다운 경로
	 * 	 */
	private String down_path 		= Constants.BLANK;
	
	/**
	 * 컨텐츠 id
	 * 	 */
	private String cti_id 		= Constants.BLANK;
	
	
	
	public String getCti_id() {
		return cti_id;
	}
	public void setCti_id(String ctiId) {
		cti_id = ctiId;
	}
	public int getCart_no() {
		return cart_no;
	}
	public void setCart_no(int cartNo) {
		cart_no = cartNo;
	}
	public int getCart_seq() {
		return cart_seq;
	}
	public void setCart_seq(int cartSeq) {
		cart_seq = cartSeq;
	}
	public String getDown_nm() {
		return down_nm;
	}
	public void setDown_nm(String downNm) {
		down_nm = downNm;
	}
	public String getDown_path() {
		return down_path;
	}
	public void setDown_path(String downPath) {
		down_path = downPath;
	}
	public String getGenerator_name() {
		return generator_name;
	}
	public void setGenerator_name(String generatorName) {
		generator_name = generatorName;
	}
	public String getGenerator_ver() {
		return generator_ver;
	}
	public void setGenerator_ver(String generatorVer) {
		generator_ver = generatorVer;
	}
	public String getTotal_stream_size() {
		return total_stream_size;
	}
	public void setTotal_stream_size(String totalStreamSize) {
		total_stream_size = totalStreamSize;
	}
	public String getStreams_path() {
		return streams_path;
	}
	public void setStreams_path(String streamsPath) {
		streams_path = streamsPath;
	}
	public String getMediatypecode() {
		return mediatypecode;
	}
	public void setMediatypecode(String mediatypecode) {
		this.mediatypecode = mediatypecode;
	}
	public String getMediaid() {
		return mediaid;
	}
	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSutitle() {
		return sutitle;
	}
	public void setSutitle(String sutitle) {
		this.sutitle = sutitle;
	}
	public String getPhysicaltree() {
		return physicaltree;
	}
	public void setPhysicaltree(String physicaltree) {
		this.physicaltree = physicaltree;
	}
	public String getLogicaltree() {
		return logicaltree;
	}
	public void setLogicaltree(String logicaltree) {
		this.logicaltree = logicaltree;
	}
	public String getWorkername() {
		return workername;
	}
	public void setWorkername(String workername) {
		this.workername = workername;
	}
	

}

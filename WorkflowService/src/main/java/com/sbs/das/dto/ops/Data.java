package com.sbs.das.dto.ops;

import java.util.ArrayList;
import java.util.List;

import com.sbs.das.commons.convertor.LongConverter;
import com.sbs.das.commons.convertor.TextConverter;
import com.sbs.das.commons.convertor.TextUTF8Converter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("data")
public class Data {

	// DAS 마스터 아이디
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("das_master_id")
	private Long dasMasterId;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("total_duration")
	private Long totalDuration;
	
	@XStreamConverter(LongConverter.class)
	@XStreamAlias("main_duration")
	private Long mainDuration;
	
	// 방송일
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_day")
	private String bradDay;
	
	// 방송 시작시간
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_st_time")
	private String bradStTime;
	
	// 방송 종료시간
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("brad_fns_time")
	private String bradFnsTime;
	
	// 등록자
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("regrid")
	private String regrid;
	
	@XStreamConverter(TextConverter.class)
	@XStreamAlias("entire_annot_clf_cd")
	private String annotClfCd;

	@XStreamConverter(TextUTF8Converter.class)
	@XStreamAlias("entire_annot_clf_cont")
	private String annotClfCont;
	
	public String getAnnotClfCont() {
		return annotClfCont;
	}
	public void setAnnotClfCont(String annotClfCont) {
		this.annotClfCont = annotClfCont;
	}
	public String getAnnotClfCd() {
		return annotClfCd;
	}
	public void setAnnotClfCd(String annotClfCd) {
		this.annotClfCd = annotClfCd;
	}
	public String getRegrid() {
		return regrid;
	}
	public void setRegrid(String regrid) {
		this.regrid = regrid;
	}
	public String getBradDay() {
		return bradDay;
	}
	public void setBradDay(String bradDay) {
		this.bradDay = bradDay;
	}
	public String getBradStTime() {
		return bradStTime;
	}
	public void setBradStTime(String bradStTime) {
		this.bradStTime = bradStTime;
	}
	public String getBradFnsTime() {
		return bradFnsTime;
	}
	public void setBradFnsTime(String bradFnsTime) {
		this.bradFnsTime = bradFnsTime;
	}
	public Long getDasMasterId() {
		return dasMasterId;
	}
	public void setDasMasterId(Long dasMasterId) {
		this.dasMasterId = dasMasterId;
	}
	public Long getTotalDuration() {
		return totalDuration;
	}
	public void setTotalDuration(Long totalDuration) {
		this.totalDuration = totalDuration;
	}
	public Long getMainDuration() {
		return mainDuration;
	}
	public void setMainDuration(Long mainDuration) {
		this.mainDuration = mainDuration;
	}

	/*
	 * 프로그램 정보
	 */
	@XStreamAlias("pgm")
	private Program program = null;
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}

	/*
	 * 회차 정보
	 */
/*
	@XStreamAlias("metadata")
	private Metadata metadata = null;
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
*/
	/*
	 * 본방 코너 정보
	 */
	private Corners corners = null;
	public Corners getCorners() {
		return corners;
	}
	public void setCorners(Corners corners) {
		this.corners = corners;
	}
	
	/*
	 * 프로그램 조회 리스트
	 */
	/*
	private Metadatas metadatas = null;

	public Metadatas getMetadatas() {
		return metadatas;
	}
	public void setMetadatas(Metadatas metadatas) {
		this.metadatas = metadatas;
	}
	*/
	
	@XStreamImplicit(itemFieldName="metadata")
	List<Metadata> metadatas = new ArrayList<Metadata>();

	public List<Metadata> getMetadatas() {
		return metadatas;
	}
	public void setMetadatas(List<Metadata> metadatas) {
		this.metadatas = metadatas;
	}
	public void addMetadatas(Metadata metadata) {
		this.metadatas.add(metadata);
	}
	
}

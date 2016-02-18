package com.sbs.das.dto.ops;

import com.sbs.das.commons.convertor.LongConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

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
	@XStreamAlias("brad_day")
	private String bradDay;
	
	// 방송 시작시간
	@XStreamAlias("brad_st_time")
	private String bradStTime;
	
	// 방송 종료시간
	@XStreamAlias("brad_fns_time")
	private String bardFnsTime;

	
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
	public String getBardFnsTime() {
		return bardFnsTime;
	}
	public void setBardFnsTime(String bardFnsTime) {
		this.bardFnsTime = bardFnsTime;
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
	@XStreamAlias("metadata")
	private Metadata metadata = null;
	public Metadata getMetadata() {
		return metadata;
	}
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

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
}

package com.app.das.business.transfer;

import java.util.HashMap;
import java.util.Map;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 정리의 인코딩 매체 통계의 분류 정보를 포함하고 있는 DataObejct로써 내부적으로 
 * 통계 수치 정보를 포함하고 있는 StatisticsEncItemDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class StatisticsEncMediaItemDO extends DTO
{
	/**
	 * 테이프 매체분류 코드
	 */
	private String tapeMediaCode = Constants.BLANK;
	/**
	 * 테이프 매체분류 명
	 */
	private String tapeMediaNm = Constants.BLANK;
	/**
	 * 통계수치를 포함하고 있는 List
	 */
	private Map statisticsEncItemDOMap = null;
	
	
	public StatisticsEncMediaItemDO()
	{
		statisticsEncItemDOMap = new HashMap();
	}

	
	public Map getStatisticsEncItemDOMap() {
		return statisticsEncItemDOMap;
	}
	public void setStatisticsEncItemDOMap(Map statisticsEncItemDOMap) {
		this.statisticsEncItemDOMap = statisticsEncItemDOMap;
	}
	public String getTapeMediaCode() {
		return tapeMediaCode;
	}
	public void setTapeMediaCode(String tapeMediaCode) {
		this.tapeMediaCode = tapeMediaCode;
	}
	public String getTapeMediaNm() {
		return tapeMediaNm;
	}
	public void setTapeMediaNm(String tapeMediaNm) {
		this.tapeMediaNm = tapeMediaNm;
	}

	public void put(String key, DTO dto)
	{
		statisticsEncItemDOMap.put(key, dto);
	}

}

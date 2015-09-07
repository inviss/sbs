package com.app.das.business.transfer;

import java.util.HashMap;
import java.util.Map;

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.DTO;

/**
 * 통계의 수집 구분 분류정보를 포함하고 있는 DataObject로서 내부적으로 
 * 수치정보를 포함하고 있는 StatisticsItemDO를 List 형태로 포함하고 있다.
 * @author ysk523
 *
 */
public class StatisticsGathKindItemDO extends DTO 
{
	/**
	 * 수집구분코드
	 */
	private String gathClfCd = Constants.BLANK;
	/**
	 * 수집구분명
	 */
	private String gathClfNm = Constants.BLANK;
	
	/**
	 * 통계수치를 포함하고 있는 List
	 */
	private Map statisticsItemDOMap = null;

	public StatisticsGathKindItemDO()
	{
		statisticsItemDOMap = new HashMap();
	}

	public String getGathClfCd() {
		return gathClfCd;
	}

	public void setGathClfCd(String gathClfCd) {
		this.gathClfCd = gathClfCd;
	}

	public String getGathClfNm() {
		return gathClfNm;
	}

	public void setGathClfNm(String gathClfNm) {
		this.gathClfNm = gathClfNm;
	}

	public Map getStatisticsItemDOMap() {
		return statisticsItemDOMap;
	}

	public void setStatisticsItemDOMap(Map statisticsItemDOMap) {
		this.statisticsItemDOMap = statisticsItemDOMap;
	}

	public void put(String key, DTO dto)
	{
		statisticsItemDOMap.put(key, dto);
	}

}

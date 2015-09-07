package com.app.das.business.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.das.business.transfer.DTO;

/**
 * 우리 시스템의 통계 조회 정보를 포함하고 있는 DataObejct 로써 모든 통계 Method의 Return Type 이다.
 * @author ysk523
 *
 */
public class StatisticsDO extends DTO 
{
	/**
	 * 테이블 헤더의 정보를 String으로 포함하고 있는 List
	 */
	private List headerList = null;
	
	/**
	 * 통계시 사용하는 통계정보를 포함하고 있는 List
	 */
	private List dataList = null;
	
	public StatisticsDO()
	{
		headerList = new ArrayList();
		dataList = new ArrayList();
	}
	
	public List getDataList() 
	{
		return dataList;
	}

	public void setDataList(List dataList) 
	{
		this.dataList = dataList;
	}

	public List getHeaderList() 
	{
		return headerList;
	}

	public void setHeaderList(List headerList) 
	{
		this.headerList = headerList;
	}
	
	public void add(DTO dto)
	{
		dataList.add(dto);
	}
}

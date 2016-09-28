package com.app.das.business;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.dao.StatisticsInfoDAO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.business.transfer.StatisticsDO;
import com.app.das.business.transfer.StatisticsInfo;
import com.app.das.util.CalendarUtil;

/**
 * 통계에 대한 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class StatisticsBusinessProcessor
{
	private Logger logger = Logger.getLogger(StatisticsBusinessProcessor.class);

	private static final StatisticsInfoDAO statisticsInfoDAO = StatisticsInfoDAO.getInstance();



	/**
	 * 수집의 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getCollectionJarnreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectCollectionJarnreList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));		

		return statisticsDO;
	}

	/**
	 * 수집의 수집처 통계 조회를 한다. ( 내부)
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getCollectionGathByRegularList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectCollectionGathRegularList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 수집의 수집처 통계 조회를 한다. ( 외부)
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getCollectionGathByOutList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectCollectionGathOutList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 수집의 수집구분 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getCollectionGathKindList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectCollectionGathKindList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));				

		return statisticsDO;

	}

	/**
	 * 정리의 인코딩 매체 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getEncMediaList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectEncMediaList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 정리의 인코딩 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getEncJanreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectEncJanreList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 이용의 다운로드 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getUseJanreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectUseJanreList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 이용의 다운로드 이용처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getUseCompanyList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectUseCompanyList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 이용의 다운로드 제한영상 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getUseLimitList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectUseLimitList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 폐기 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getDisuseList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectDisuseList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 보유량 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getHoldingList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectHoldingList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 종합 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getTotalStatisticsList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectTotalStatisticsList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 분류별 정리 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getAdjustmentList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectAdjustmentList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 작업자별 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getWorkUserStatisticsList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectWorkUserStatisticsList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 조회 기간의 날짜를 조회날짜 구분에 맞게 Serial 하게 만들어서 Return 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @return List 조회 기간의 날짜를 Serial 하게 포함하고 있는 List
	 * @throws Exception 
	 */
	private List getDateByTerm(StatisticsConditionDO conditionDO) throws Exception
	{
		List dateList = new ArrayList();
		String dateKind = conditionDO.getDateKind();
		if(DASBusinessConstants.StatisticsDateKind.TERM.equals(dateKind))
		{
			dateList.add("00000000");
			return dateList;
		}

		try 
		{
			String startDate = conditionDO.getFromDate().substring(0, 6) + "01";

			if (DASBusinessConstants.StatisticsDateKind.YEAR.equals(dateKind)) 
			{
				dateList.add(startDate.substring(0, 4));
			} 
			else if (DASBusinessConstants.StatisticsDateKind.MONTH.equals(dateKind)) 
			{
				dateList.add(startDate.substring(0, 6));
			}


			while (true) 
			{
				String afterDate = null;

				if (DASBusinessConstants.StatisticsDateKind.YEAR.equals(dateKind)) 
				{
					afterDate = CalendarUtil.afterSpecDay(startDate, 1, 0, 0);

					//list the year when between year is less than one year. Set to Jan, 01.
					afterDate = afterDate.substring(0, 4)+ "0101";  
				}
				else if (DASBusinessConstants.StatisticsDateKind.MONTH.equals(dateKind)) 
				{
					afterDate = CalendarUtil.afterSpecDay(startDate, 0, 1, 0);
				}

				if(CalendarUtil.compareBetweenDates(afterDate, conditionDO.getToDate()) == 1)
				{
					break;
				}
				else
				{
					if (DASBusinessConstants.StatisticsDateKind.YEAR.equals(dateKind)) 
					{
						dateList.add(afterDate.substring(0, 4));						
					} 
					else if (DASBusinessConstants.StatisticsDateKind.MONTH.equals(dateKind)) 
					{
						dateList.add(afterDate.substring(0, 6));
					}

					startDate = afterDate;
				}

			}
		} catch (Exception e) {
			throw e;
		}		

		return dateList;
	}

	/**
	 * 로그인 월간 연간 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getLoginList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectLoginList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 일반검색 상세검색 로그를 남긴다.
	 * @param commonDO 공통정보
	 * @return result 정상적으로 검색로그가 작성이 제대로 들어갔는지 true/false
	 * @throws Exception 
	 */
	public boolean setSearchDate(String flag, DASCommonDO commonDO) throws Exception
	{

		boolean result = statisticsInfoDAO.insertSearchDate(flag, commonDO);

		return result;

	}

	/**
	 * 검색 월간 연간 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getSearchList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectSearchList(conditionDO, commonDO);

		statisticsDO.setHeaderList(getDateByTerm(conditionDO));

		return statisticsDO;

	}

	/**
	 * 사진 다운로드/삭제 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getPicDownDeleteList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.selectPicDownDeleteList(conditionDO, commonDO);

		return statisticsDO;

	}

	/**
	 * 사진 등록현황 조회.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getPicRegisterList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.getPicRegisterList(conditionDO, commonDO);
		statisticsDO.setHeaderList(getDateByTerm(conditionDO));	

		return statisticsDO;

	}

	/**
	 * 사진 이용현황 조회.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getPicUsingList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.getPicUsingList(conditionDO, commonDO);
		statisticsDO.setHeaderList(getDateByTerm(conditionDO));	

		return statisticsDO;

	}

	/**
	 * 사진 폐기현황 조회.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO getPicDeleteList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		StatisticsDO statisticsDO = statisticsInfoDAO.getPicDeleteList(conditionDO, commonDO);
		statisticsDO.setHeaderList(getDateByTerm(conditionDO));	

		return statisticsDO;

	}

	/**
	 * 영상종합통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_TOTAL_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_TOTAL_TBL_QUERY(conditionDO);

	}

	/**
	 * 수집통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_COL_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_COL_TBL_QUERY(conditionDO);

	}

	/**
	 * 폐기통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_DISUSE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_DISUSE_TBL_QUERY(conditionDO);

	}

	/**
	 * 정리통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_ARRA_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_ARRA_TBL_QUERY(conditionDO);

	}

	/**
	 * 장르별 이용통계
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_GNR_USE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_GNR_USE_TBL_QUERY(conditionDO);

	}

	/**
	 * 장르이용통계2 ( 프로그램별 이용통계)
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List getSTAT_GNR_USE2_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_GNR_USE2_TBL_QUERY(conditionDO);

	}

	/**
	 * 부서별 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_DEPT_USE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_DEPT_USE_TBL_QUERY(conditionDO);

	}

	/**
	 * 부서별 아카이브 통계 PART1(미등록)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_DEPT_ARCH_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_DEPT_ARCH_TBL_QUERY(conditionDO);

	}

	/**
	 * 부서별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_DEPT_ARCH_DTL_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_DEPT_ARCH_DTL_TBL_QUERY(conditionDO);

	}

	/**
	 * 부서별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_DEPT_ARCH_REQ_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_DEPT_ARCH_REQ_TBL_QUERY(conditionDO);

	}


	/**
	 * 프로그램별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PGM_ARCH_DTL_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.getSTAT_PGM_ARCH_DTL_TBL_List(conditionDO);

	}

	/**
	 * 프로그램별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PGM_ARCH_REQ_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.getSTAT_PGM_ARCH_REQ_TBL_List(conditionDO);

	}

	/**
	 * 프로그램별 이용통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PGM_USE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PGM_USE_TBL_QUERY(conditionDO);

	}

	/**
	 * 사진 보유량 통계 조회( getSTAT_PHOT_PGM_TOTAL_List IF 로 전환함  by DEKIM 2010.10.07)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PHOT_COL_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PHOT_COL_TBL_QUERY(conditionDO);

	}

	/**
	 * 사진 등록 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PHOT_REG_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PHOT_REG_TBL_QUERY(conditionDO);

	}

	/**
	 * 사진 폐기 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PHOT_DISUSE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PHOT_DISUSE_TBL_QUERY(conditionDO);

	}

	/**
	 * 사진 누적량 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String getSTAT_PHOT_TOTAL_List(StatisticsConditionDO conditionDO) throws Exception{

		return statisticsInfoDAO.selectSTAT_PHOT_TOTAL_TBL_QUERY(conditionDO);

	}

	/**
	 * 프로그램별 사진 보유량 통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PHOT_PGM_TOTAL_List(StatisticsConditionDO conditionDO) throws Exception{

		return statisticsInfoDAO.selectSTAT_PHOT_PGM_TOTAL_TBL_QUERY(conditionDO);

	}

	/**
	 * 사진 이용 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PHOT_USE_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PHOT_USE_TBL_QUERY(conditionDO);

	}

	/**
	 * 장르별 아카이브 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_GNR_ARCH_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_GNR_ARCH_TBL_QUERY(conditionDO);

	}

	/**
	 * 프로그램별 아카이브 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getSTAT_PGM_ARCH_TBL_List(StatisticsConditionDO conditionDO) throws Exception
	{

		return statisticsInfoDAO.selectSTAT_PGM_ARCH_TBL_QUERY(conditionDO);

	}

	/**
	 * 컨텐츠 소유권자별 다운로드 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DOWN_COCD_USE_TBL_List(Das das) throws Exception
	{

		String xml="";
		for(StatisticsInfo info : das.getItems()){
			xml = statisticsInfoDAO.getSTAT_DOWN_COCD_USE_TBL_List(info);
		}

		return xml;
	}

}

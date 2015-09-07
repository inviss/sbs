package com.app.das.business.dao;

//import java.awt.List;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.Constants;
import com.app.das.business.dao.statement.StatisticsInfoStatement;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.Das;
import com.app.das.business.transfer.StatisticsAdjustmentItemDO;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.business.transfer.StatisticsDO;
import com.app.das.business.transfer.StatisticsEncItemDO;
import com.app.das.business.transfer.StatisticsEncMediaItemDO;
import com.app.das.business.transfer.StatisticsGathItemDO;
import com.app.das.business.transfer.StatisticsGathKindItemDO;
import com.app.das.business.transfer.StatisticsInfo;
import com.app.das.business.transfer.StatisticsItemDO;
import com.app.das.business.transfer.StatisticsJanreItemDO;
import com.app.das.business.transfer.StatisticsLoginDO;
import com.app.das.business.transfer.StatisticsTotalItemDO;
import com.app.das.business.transfer.StatisticsUseItemDO;
import com.app.das.business.transfer.StatisticsWorkerItemDO;
import com.app.das.services.XmlConvertorService;
import com.app.das.services.XmlConvertorServiceImpl;
import com.app.das.util.DBService;

/**
 * 통계에 대한 Database 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class StatisticsInfoDAO extends AbstractDAO 
{
	private static StatisticsInfoDAO instance;

	private Logger logger = Logger.getLogger(StatisticsInfoDAO.class);
	
	/**
	 * A private constructor
	 *
	 */
	private StatisticsInfoDAO() 
	{
	}

	public static synchronized StatisticsInfoDAO getInstance() 
	{
		if (instance == null) 
		{
			instance = new StatisticsInfoDAO();
		}
		return instance;
	}

	/**
	 * 수집의 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectCollectionJarnreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectCollectionJarnreListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCollectionJarnreList######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
					jarnreItem.setTotalTm(					rs.getBigDecimal("TOTAL_TM"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));
				item.setTime(				rs.getBigDecimal("SUM_TM"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 수집의 수집처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectCollectionGathRegularList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectCollectionGathRegularListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCollectionGathRegularList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//수집부서코드(이전값)
			String beforeGathDepCd = Constants.BLANK;

			//수집부서코드(현재값)
			String gathDepCd = Constants.BLANK;

			StatisticsGathItemDO gathItem = null;
			while(rs.next())
			{
				gathDepCd = rs.getString("GATH_DEPT_CD");

				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeGathDepCd.equals(gathDepCd))
				{
					if(gathItem != null)
					{
						statisticsDO.add(gathItem);
					}

					gathItem = new StatisticsGathItemDO();
					gathItem.setGathDepCd(			gathDepCd);
					gathItem.setCompanyNm(			rs.getString("COM_NM"));
					gathItem.setCenterNm(				rs.getString("CENTER_NM"));
					gathItem.setDepartmentNm(		rs.getString("DEP_NM"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));
				item.setTime(				rs.getBigDecimal("SUM_TM"));

				gathItem.put(day, item);

				beforeGathDepCd = gathDepCd;
			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(gathItem != null)
			{
				statisticsDO.add(gathItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 수집의 수집처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectCollectionGathOutList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectCollectionGathOutListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCollectionGathOutList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//수집처코드(이전값)
			String beforeGathCoCd = Constants.BLANK;

			//수집처코드(현재값)
			String gathCoCd = Constants.BLANK;

			StatisticsGathItemDO gathItem = null;
			while(rs.next())
			{
				gathCoCd = rs.getString("GATH_CO_CD");

				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsGathItemDO를 생성해야 한다.
				if(!beforeGathCoCd.equals(gathCoCd))
				{
					if(gathItem != null)
					{
						statisticsDO.add(gathItem);
					}

					gathItem = new StatisticsGathItemDO();
					gathItem.setGathCoCd(			gathCoCd);
					gathItem.setCompanyNm(		rs.getString("GATH_CO_NM"));

				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));
				item.setTime(				rs.getBigDecimal("SUM_TM"));

				gathItem.put(day, item);

				beforeGathCoCd = gathCoCd;
			}

			//마지막 StatisticsGathItemDO를 추가해야한다.
			if(gathItem != null)
			{
				statisticsDO.add(gathItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 수집의 수집구분 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectCollectionGathKindList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectCollectionGathKindListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectCollectionGathKindList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//수집처코드(이전값)
			String beforeGathKindCoCd = Constants.BLANK;

			//수집처코드(현재값)
			String gathCoKindCd = Constants.BLANK;

			StatisticsGathKindItemDO gathKindItem = null;
			while(rs.next())
			{
				gathCoKindCd = rs.getString("GATH_CLF_CD");

				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeGathKindCoCd.equals(gathCoKindCd))
				{
					if(gathKindItem != null)
					{
						statisticsDO.add(gathKindItem);
					}

					gathKindItem = new StatisticsGathKindItemDO();
					gathKindItem.setGathClfCd(			gathCoKindCd);
					gathKindItem.setGathClfNm(			rs.getString("GATH_CLF_NM"));

				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));
				item.setTime(				rs.getBigDecimal("SUM_TM"));

				gathKindItem.put(day, item);

				beforeGathKindCoCd = gathCoKindCd;
			}

			//마지막 StatisticsGathKindItemDO를 추가해야한다.
			if(gathKindItem != null)
			{
				statisticsDO.add(gathKindItem);
			}

			return statisticsDO;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 정리의 인코딩 매체 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectEncMediaList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectEncMediaListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEncMediaList######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			//테이프 매체분류 코드(이전값)
			String beforeTapeMediaCode = Constants.BLANK;

			//테이프 매체분류 코드(현재값)
			String tapeMediaCode = Constants.BLANK;

			StatisticsEncMediaItemDO encMediaItem = null;
			while(rs.next())
			{
				tapeMediaCode = rs.getString("TAPE_MEDIA_CLF_CD");
				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeTapeMediaCode.equals(tapeMediaCode))
				{
					if(encMediaItem != null)
					{
						statisticsDO.add(encMediaItem);
					}

					encMediaItem = new StatisticsEncMediaItemDO();
					encMediaItem.setTapeMediaCode(		tapeMediaCode);
					encMediaItem.setTapeMediaNm(			rs.getString("TAPE_MEDIA_CLF_NM"));

				}

				StatisticsEncItemDO item = new StatisticsEncItemDO();
				item.setLargeCategory(rs.getString("CTGR_L_CD"));
				item.setLargeCategoryNm(rs.getString("CTGR_L_NM"));

				long sum = rs.getInt("SUM_DURATION");
				long total = rs.getInt("TOT_DURATION");
				int sum_qty = rs.getInt("SUM_QTY");
				int total_qty = rs.getInt("TOT_QTY");

				if(total_qty == 1)
				{
					double num = 0;
					if (total != 0) {
						num = (double)sum / ((double)total * 3600);
					}
					BigDecimal conRate = new BigDecimal(String.valueOf(num)).multiply(new BigDecimal(100));
					item.setConRate(conRate.setScale(2, BigDecimal.ROUND_HALF_UP));
				}

				item.setQty(sum_qty);
				item.setObjTotal(total_qty);
				item.setDuration(sum);
				item.setTot_duration(total);


				encMediaItem.put(rs.getString("CTGR_L_CD"), item);

				beforeTapeMediaCode = tapeMediaCode;
			}

			//마지막 StatisticsGathKindItemDO를 추가해야한다.
			if(encMediaItem != null)
			{
				statisticsDO.add(encMediaItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);
			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 정리의 인코딩 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectEncJanreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectEncJanreListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectEncJanreList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();


			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO janreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");

				String tapeMediaCode = rs.getString("TAPE_MEDIA_CLF_CD");
				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(janreItem != null)
					{
						statisticsDO.add(janreItem);
					}

					janreItem = new StatisticsJanreItemDO();
					janreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					janreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					janreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					janreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					janreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					janreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

				}

				StatisticsItemDO item = new StatisticsItemDO();
				item.setCode(tapeMediaCode);
				item.setQty(rs.getInt("SUM_QTY"));


				janreItem.put(tapeMediaCode, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;
			}

			//마지막 StatisticsJanreItemDO 추가해야한다.
			if(janreItem != null)
			{
				statisticsDO.add(janreItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 이용의 다운로드 장르 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectUseJanreList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectUseJanreListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectUseJanreList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 이용의 다운로드 이용처 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectUseCompanyList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectUseCompanyListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectUseCompanyList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//회사(이전값)
			String beforeComCode = Constants.BLANK;
			//본부(이전값)
			String beforeCenterCode = Constants.BLANK;
			//부서(이전값)
			String beforeDepCode = Constants.BLANK;

			//회사(현재값)
			String comCode = Constants.BLANK;
			//본부(현재값)
			String centerCode = Constants.BLANK;
			//부서(현재값)
			String depCode = Constants.BLANK;

			StatisticsUseItemDO useItem = null;
			while(rs.next())
			{
				comCode = rs.getString("CO_CD");
				centerCode = rs.getString("SEG_CD");
				depCode = rs.getString("DEPT_CD");

				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeComCode.equals(comCode)
						|| !beforeCenterCode.equals(centerCode)
						|| !beforeDepCode.equals(depCode))
				{
					if(useItem != null)
					{
						statisticsDO.add(useItem);
					}

					useItem = new StatisticsUseItemDO();
					useItem.setCoCd(						comCode);
					useItem.setCenterCd(				centerCode);
					useItem.setDepCd(					comCode);
					useItem.setCompanyNm(			rs.getString("COM_NM"));
					useItem.setCenterNm(				rs.getString("CENTER_NM"));
					useItem.setDepartmentNm(		rs.getString("DEP_NM"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));

				useItem.put(day, item);

				beforeComCode = comCode;
				beforeCenterCode = centerCode;
				beforeDepCode = depCode;
			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(useItem != null)
			{
				statisticsDO.add(useItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 이용의 다운로드 제한영상 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectUseLimitList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectUseLimitListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectUseLimitList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//회사(이전값)
			String beforeComCode = Constants.BLANK;
			//본부(이전값)
			String beforeCenterCode = Constants.BLANK;
			//부서(이전값)
			String beforeDepCode = Constants.BLANK;

			//회사(현재값)
			String comCode = Constants.BLANK;
			//본부(현재값)
			String centerCode = Constants.BLANK;
			//부서(현재값)
			String depCode = Constants.BLANK;

			StatisticsUseItemDO useItem = null;
			while(rs.next())
			{
				comCode = rs.getString("CO_CD");
				centerCode = rs.getString("SEG_CD");
				depCode = rs.getString("DEPT_CD");

				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeComCode.equals(comCode)
						|| !beforeCenterCode.equals(centerCode)
						|| !beforeDepCode.equals(depCode))
				{
					if(useItem != null)
					{
						statisticsDO.add(useItem);
					}

					useItem = new StatisticsUseItemDO();
					useItem.setCoCd(						comCode);
					useItem.setCenterCd(				centerCode);
					useItem.setDepCd(					comCode);
					useItem.setCompanyNm(			rs.getString("COM_NM"));
					useItem.setCenterNm(				rs.getString("CENTER_NM"));
					useItem.setDepartmentNm(		rs.getString("DEP_NM"));
				}

				//주석구분코드
				String annotClfCd = rs.getString("ANNOT_CLF_CD");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setCode(				annotClfCd);
				item.setQty(					rs.getInt("SUM_QTY"));

				useItem.put(annotClfCd, item);

				beforeComCode = comCode;
				beforeCenterCode = centerCode;
				beforeDepCode = depCode;
			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(useItem != null)
			{
				statisticsDO.add(useItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 폐기 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectDisuseList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectDisuseListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectDisuseList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));
				item.setTime(				rs.getBigDecimal("SUM_TM"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 보유량 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectHoldingList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectHoldingListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectHoldingList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
					jarnreItem.setTotalTm(					rs.getBigDecimal("TOTAL_TM"));

				}

				String meterType = rs.getString("METER_TYPE");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setCode(				meterType);
				item.setQty(					rs.getInt("SUM_QTY"));

				jarnreItem.put(meterType, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 종합 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectTotalStatisticsList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectTotalStatisticsListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectTotalStatisticsList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			StatisticsTotalItemDO totalItem = null;
			while(rs.next())
			{
				totalItem = new StatisticsTotalItemDO();
				totalItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
				totalItem.setMidiumCategory(		rs.getString("CTGR_M_CD"));
				totalItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
				totalItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
				totalItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
				totalItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

				totalItem.setMvSumQty(					rs.getInt("MV_SUM_QTY"));
				totalItem.setMdSumQty(				rs.getInt("MD_SUM_QTY"));
				totalItem.setDwSumQty(				rs.getInt("DW_SUM_QTY"));
				totalItem.setDsSumQty(					rs.getInt("DS_SUM_QTY"));

				totalItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
				totalItem.setTotalTm(						rs.getBigDecimal("TOTAL_TM"));

				statisticsDO.add(totalItem);

			}

			return statisticsDO;

		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 분류별 정리 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectAdjustmentList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectAdjustmentListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectAdjustmentList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			StatisticsAdjustmentItemDO item = null;
			while(rs.next())
			{
				item = new StatisticsAdjustmentItemDO();
				item.setLargeCategory(			rs.getString("CTGR_L_CD"));
				item.setMidiumCategory(			rs.getString("CTGR_M_CD"));
				item.setSmallCategory(				rs.getString("CTGR_S_CD"));
				item.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
				item.setMidiumCategoryNm(		rs.getString("CTGR_M_NM"));
				item.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

				int mediaGathQty = rs.getInt("SUM_MEDIA_GATH_QTY");
				int mediaAdjQty = rs.getInt("SUM_MCV_DY_ARRG_QTY");
				item.setMediaGathQty(				mediaGathQty);
				item.setMediaAdjQty(				mediaAdjQty);

				if(mediaGathQty > 0 && mediaAdjQty > 0)
				{
					//BigDecimal mediaRate = new BigDecimal(String.valueOf(mediaGathQty)).divide(new BigDecimal(String.valueOf(mediaAdjQty)), 2, BigDecimal.ROUND_DOWN);

					float num = (float)mediaAdjQty / (float)mediaGathQty;					


					BigDecimal mediaRate = new BigDecimal(String.valueOf(num)).multiply(new BigDecimal(100));
					if(logger.isDebugEnabled())
					{
						logger.debug("[BigDecimal]" + mediaRate.setScale(2, BigDecimal.ROUND_HALF_UP));
					}

					item.setMediaAdjRate(mediaRate.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				else
				{
					item.setMediaAdjRate(Constants.ZERO);				
				}

				int onairGathQty = rs.getInt("SUM_ONAIR_DY_GATH_QTY");
				int onairAdjQty = rs.getInt("SUM_ONAIR_DY_ARRG_QTY");

				item.setOnairGathQty(				onairGathQty);
				item.setOnairAdjQty(					onairAdjQty);
				if(onairGathQty > 0 && onairAdjQty > 0)
				{
					//BigDecimal onairRate = new BigDecimal(String.valueOf(onairGathQty)).divide(new BigDecimal(String.valueOf(onairAdjQty)), 2, BigDecimal.ROUND_DOWN);
					float num = (float)onairAdjQty / (float)onairGathQty;					
					BigDecimal onairRate = new BigDecimal(String.valueOf(num)).multiply(new BigDecimal(100));

					item.setOnairAdjRate(onairRate.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				else
				{
					item.setOnairAdjRate(Constants.ZERO);
				}

				int totalGathQty = mediaGathQty + onairGathQty;
				int totalAdjQty = mediaAdjQty + onairAdjQty;
				item.setTotalGathQty(totalGathQty);
				item.setTotalAdjQty(totalAdjQty);
				if(totalGathQty > 0 && totalAdjQty > 0)
				{
					//BigDecimal totalRate = new BigDecimal(String.valueOf(totalGathQty)).divide(new BigDecimal(String.valueOf(totalAdjQty)), 2, BigDecimal.ROUND_DOWN);
					float num = (float)totalAdjQty / (float)totalGathQty;					
					BigDecimal totalRate = new BigDecimal(String.valueOf(num)).multiply(new BigDecimal(100));
					item.setTotalAdjRate(totalRate.setScale(2, BigDecimal.ROUND_HALF_UP));
				}
				else
				{
					item.setTotalAdjRate(Constants.ZERO);
				}



				statisticsDO.add(item);

			}

			return statisticsDO;

		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}

	}

	/**
	 * 작업자별 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectWorkUserStatisticsList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectWorkUserStatisticsListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectWorkUserStatisticsList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			StatisticsWorkerItemDO item = null;
			while(rs.next())
			{
				item = new StatisticsWorkerItemDO();
				item.setWorkerClf(  		rs.getString("WORKER_CLF"));    
				item.setWorkerClfNm(  	rs.getString("WORKER_CLF_NM"));   
				item.setWorkUserId(		rs.getString("WORK_USER_ID"));
				item.setItem(					rs.getInt("ITEM"));
				item.setPrgm(           	rs.getInt("PRGM"));
				item.setDrama(          	rs.getInt("DRAMA"));
				item.setEductnal(       	rs.getInt("EDUCTNAL"));
				item.setEntertain(      	rs.getInt("ENTERTAIN"));
				item.setExtra(          		rs.getInt("EXTRA"));
				item.setRef(            		rs.getInt("REF"));
				item.setTotal(          		rs.getInt("TOTAL"));

				statisticsDO.add(item);

			}

			return statisticsDO;

		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}	

	/**
	 * 이용의 로그인 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectLoginList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectLoginListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectLoginList######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			String date = "";
			long s_count = 0;
			long d_count = 0;
			long s_tot = 0;
			long d_tot = 0;
			ArrayList list = new ArrayList();

			StatisticsLoginDO statisticsLoginDO = null;
			while(rs.next())
			{
				date = rs.getString("DATE");
				s_count = rs.getLong("S_COUNT");
				d_count = rs.getLong("D_COUNT");

				s_tot = s_tot + s_count;
				d_tot = d_tot + d_count;

				statisticsLoginDO = new StatisticsLoginDO();

				statisticsLoginDO.setDate(date);
				statisticsLoginDO.setS_count(s_count);
				statisticsLoginDO.setD_count(d_count);
				statisticsLoginDO.setS_tot(s_tot);
				statisticsLoginDO.setD_tot(d_tot);

				list.add(statisticsLoginDO);
			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(statisticsLoginDO != null)
			{
				statisticsDO.setDataList(list);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 일반 검색 상세 검색 로그를 남긴다.
	 * @param flag 조건
	 * @param commonDO 공통정보
	 * @return StatisticsDO true/false
	 * @throws Exception 
	 */
	public boolean insertSearchDate(String flag, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.insertSearchDate(flag, commonDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count = 0;
		boolean result = false;

		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######insertSearchDate######## con : " + con);
			stmt = con.prepareStatement(query);

			count = stmt.executeUpdate();

			if(count > 0)
			{
				result = true;
			}

			return result;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 이용의 검색 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectSearchList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSearchListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSearchList######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			String date = "";
			long s_count = 0;
			long d_count = 0;
			long s_tot = 0;
			long d_tot = 0;
			ArrayList list = new ArrayList();

			StatisticsLoginDO statisticsLoginDO = null;
			while(rs.next())
			{
				date = rs.getString("DATE");
				s_count = rs.getLong("S_COUNT");
				d_count = rs.getLong("D_COUNT");

				s_tot = s_tot + s_count;
				d_tot = d_tot + d_count;

				statisticsLoginDO = new StatisticsLoginDO();

				statisticsLoginDO.setDate(date);
				statisticsLoginDO.setS_count(s_count);
				statisticsLoginDO.setD_count(d_count);
				statisticsLoginDO.setS_tot(s_tot);
				statisticsLoginDO.setD_tot(d_tot);

				list.add(statisticsLoginDO);
			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(statisticsLoginDO != null)
			{
				statisticsDO.setDataList(list);
			}

			return statisticsDO;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 사진 다운로드/삭제 통계 조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return StatisticsDO 통계 정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public StatisticsDO selectPicDownDeleteList(StatisticsConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectPicDownDeleteListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectPicDownDeleteList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			StatisticsTotalItemDO totalItem = null;
			while(rs.next())
			{
				totalItem = new StatisticsTotalItemDO();
				totalItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
				totalItem.setMidiumCategory(		rs.getString("CTGR_M_CD"));
				totalItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
				totalItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
				totalItem.setMidiumCategoryNm(		rs.getString("CTGR_M_NM"));
				totalItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));

				totalItem.setMdSumQty(				rs.getInt("MD_SUM_QTY"));
				totalItem.setDwSumQty(				rs.getInt("DW_SUM_QTY"));
				totalItem.setRgSumQty(		rs.getInt("RG_SUM_QTY"));

				totalItem.setTotalQty(					rs.getInt("TOTAL_QTY"));

				statisticsDO.add(totalItem);

			}

			return statisticsDO;

		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
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
		String query = StatisticsInfoStatement.selectPicRegisterListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getPicRegisterList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));
					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;

		} 

		catch (Exception e) 
		{
			logger.error(query);
			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
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
		String query = StatisticsInfoStatement.selectPicUsingListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getPicUsingList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));
					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;

		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
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
		String query = StatisticsInfoStatement.selectPicDeleteListQuery(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getPicDeleteList######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			//대분류(이전값)
			String beforeLargeCategory = Constants.BLANK;
			//중분류(이전값)
			String beforeMidiumCategory = Constants.BLANK;
			//소분류(이전값)
			String beforeSmallCategory = Constants.BLANK;

			//대분류(현재값)
			String largeCategory = Constants.BLANK;
			//중분류(현재값)
			String midiumCategory = Constants.BLANK;
			//소분류(현재값)
			String smallCategory = Constants.BLANK;

			StatisticsJanreItemDO jarnreItem = null;
			while(rs.next())
			{
				largeCategory = rs.getString("CTGR_L_CD");
				midiumCategory = rs.getString("CTGR_M_CD");
				smallCategory = rs.getString("CTGR_S_CD");


				//이전 분류와 현재 분류가 틀리면 새로이 StatisticsJanreItemDO를 생성해야 한다.
				if(!beforeLargeCategory.equals(largeCategory) 
						|| !beforeMidiumCategory.equals(midiumCategory) 
						|| !beforeSmallCategory.equals(smallCategory) )
				{
					if(jarnreItem != null)
					{
						statisticsDO.add(jarnreItem);
					}

					jarnreItem = new StatisticsJanreItemDO();
					jarnreItem.setLargeCategory(			rs.getString("CTGR_L_CD"));
					jarnreItem.setMidiumCategory(			rs.getString("CTGR_M_CD"));
					jarnreItem.setSmallCategory(			rs.getString("CTGR_S_CD"));
					jarnreItem.setLargeCategoryNm(		rs.getString("CTGR_L_NM"));
					jarnreItem.setMidiumCategoryNm(	rs.getString("CTGR_M_NM"));
					jarnreItem.setSmallCategoryNm(		rs.getString("CTGR_S_NM"));
					jarnreItem.setTotalQty(					rs.getInt("TOTAL_QTY"));
				}

				String day = rs.getString("DAY");

				StatisticsItemDO item = new StatisticsItemDO();
				item.setApplyDate(		day);
				item.setQty(					rs.getInt("SUM_QTY"));

				jarnreItem.put(day, item);

				beforeLargeCategory = largeCategory;
				beforeMidiumCategory = midiumCategory;
				beforeSmallCategory = smallCategory;

			}

			//마지막 StatisticsJanreItemDO를 추가해야한다.
			if(jarnreItem != null)
			{
				statisticsDO.add(jarnreItem);
			}

			return statisticsDO;

		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 영상종합통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_TOTAL_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setArrange_count(rs.getString("ARRANGE_COUNT"));
				item.setArrange_duration(rs.getString("ARRANGE_DURATION"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 수집통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_COL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_COL_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_COL_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 폐기통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_DISUSE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DISUSE_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DISUSE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();

				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} catch (Exception e) {
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 정리통계 조회
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_ARRA_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_ARRA_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_ARRA_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 장르별 이용통계
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_GNR_USE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_GNR_USE_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_GNR_USE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 프로그램별 이용통계
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_GNR_USE2_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_GNR_USE2_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_GNR_USE2_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setPgm_id(rs.getString("PGM_ID"));
				item.setMaster_id(rs.getString("MASTER_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 부서별 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_DEPT_USE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DEPT_USE_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DEPT_USE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();

				item.setGrp(rs.getString("COCD"));
				item.setGrp_nm(rs.getString("CONM")); //cocd 에 대한 명이 없음.
				item.setSeg(rs.getString("SUP_DEPT_CD"));
				item.setSeg_nm(rs.getString("SUP_DEPT_NM"));
				item.setDept(rs.getString("DEPT_CD"));
				item.setDept_nm(rs.getString("DEPT_NM"));

				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				//logger.debug("SUM_QTY"+item.getSum_qty());
				//logger.debug("SUM_TM"+item.getSum_tm());
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 부서별 아카이브 통계 PART1(미등록)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_DEPT_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DEPT_ARCH_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DEPT_ARCH_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setGrp(rs.getString("COCD"));
				item.setGrp_nm(rs.getString("CONM")); //cocd 에 대한 명이 없음.
				item.setSeg(rs.getString("SUP_HEAD_CD"));
				item.setSeg_nm(rs.getString("SUP_HEAD_NM"));
				item.setDept(rs.getString("DEPT_CD"));
				item.setDept_nm(rs.getString("DEPT_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_qty(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 부서별 아카이브 통계 PART2(DTL 이관)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_DEPT_ARCH_DTL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DEPT_ARCH_DTL_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DEPT_ARCH_DTL_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setGrp(rs.getString("COCD"));
				item.setGrp_nm(rs.getString("CONM")); //cocd 에 대한 명이 없음.
				item.setSeg(rs.getString("SUP_HEAD_CD"));
				item.setSeg_nm(rs.getString("SUP_HEAD_NM"));
				item.setDept(rs.getString("DEPT_CD"));
				item.setDept_nm(rs.getString("DEPT_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);
			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
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
		String query = StatisticsInfoStatement.getSTAT_PGM_ARCH_DTL_TBL_List(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getSTAT_PGM_ARCH_DTL_TBL_List######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			String oldpgm_nm="";
			String newPgm_nm="";
			String oldCt_cla ="";
			String newCt_cla="";
			while(rs.next())
			{
				newPgm_nm=rs.getString("PROGRAM_NAME").trim();
				newCt_cla=rs.getString("SCL_CD").trim();
				if(!newPgm_nm.equals(oldpgm_nm)||!newCt_cla.equals(oldCt_cla)){
					StatisticsConditionDO item = new StatisticsConditionDO();
					item.setPgm_nm(rs.getString("PROGRAM_NAME"));
					item.setCt_cla_nm(rs.getString("DESC")); //cocd 에 대한 명이 없음.
					item.setCt_cla(rs.getString("SCL_CD"));
					item.setBy_dy_qty(rs.getString("BY_DY_QTY"));
					item.setBy_dy_tm(rs.getString("BY_DY_TM"));


					resultList.add(item);
				}
				oldpgm_nm=rs.getString("PROGRAM_NAME").trim();
				oldCt_cla=rs.getString("SCL_CD").trim();
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
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
		String query = StatisticsInfoStatement.getSTAT_PGM_ARCH_REQ_TBL_List(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######getSTAT_PGM_ARCH_REQ_TBL_List######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			String oldpgm_nm="";
			String newPgm_nm="";
			String oldCt_cla ="";
			String newCt_cla="";
			while(rs.next())
			{
				newPgm_nm=rs.getString("PROGRAM_NAME").trim();
				newCt_cla=rs.getString("SCL_CD").trim();
				if(!newPgm_nm.equals(oldpgm_nm)||!newCt_cla.equals(oldCt_cla)){
					StatisticsConditionDO item = new StatisticsConditionDO();
					item.setPgm_nm(rs.getString("PROGRAM_NAME"));
					item.setCt_cla_nm(rs.getString("DESC")); //cocd 에 대한 명이 없음.
					item.setCt_cla(rs.getString("SCL_CD"));
					item.setBy_dy_qty(rs.getString("BY_DY_QTY"));
					item.setBy_dy_tm(rs.getString("BY_DY_TM"));


					resultList.add(item);
				}
				oldpgm_nm=rs.getString("PROGRAM_NAME").trim();
				oldCt_cla=rs.getString("SCL_CD").trim();
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);
			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 부서별 아카이브 통계 PART3(아카이브 요청)
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_DEPT_ARCH_REQ_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DEPT_ARCH_REQ_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DEPT_ARCH_REQ_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setGrp(rs.getString("COCD"));
				item.setGrp_nm(rs.getString("CONM")); //cocd 에 대한 명이 없음.
				item.setSeg(rs.getString("SUP_HEAD_CD"));
				item.setSeg_nm(rs.getString("SUP_HEAD_NM"));
				item.setDept(rs.getString("DEPT_CD"));
				item.setDept_nm(rs.getString("DEPT_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 프로그램별 이용통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PGM_USE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PGM_USE_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_PGM_USE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setPgm_id(rs.getString("PGM_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}

	/**
	 * 사진 종합통계
	 * @param conditionDO
	 * @return
	 * @throws Exception 
	 */
	public List selectSTAT_PHOT_COL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_COL_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_PHOT_COL_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setPgm_id(rs.getString("PGM_ID"));
				item.setBy_dy_qty(rs.getString("BY_DY_QTY"));
				//					item.setBy_dy_tm(rs.getString("BY_DY_TM"));
				item.setDay(rs.getString("DD"));
				resultList.add(item);
			}

			return resultList;
		}
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 사진 등록 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PHOT_REG_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_REG_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_PHOT_REG_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();
			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				//					item.setPgm_id(rs.getString("PGM_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				//					item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 사진 폐기 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PHOT_DISUSE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_DISUSE_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_PHOT_DISUSE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				//					item.setPgm_id(rs.getString("PGM_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				//					item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 사진 누적량 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public String selectSTAT_PHOT_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_TOTAL_TBL_QUERY(conditionDO);
		List resultList = new ArrayList();
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_PHOT_TOTAL_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			while(rs.next())
			{

				return	rs.getString("TOTAL");

			}

			return ""; 
		} 

		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
		//	return query;
	}
	/**
	 * 프로그램별 사진 보유량 통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PHOT_PGM_TOTAL_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_PGM_TOTAL_TBL_QUERY(conditionDO);

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List resultList = new ArrayList();
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			rs = stmt.executeQuery();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setPgm_nm(rs.getString("PGM_NM"));
				item.setSum_tm(rs.getString("TOTAL"));

				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 사진 이용 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PHOT_USE_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PHOT_USE_TBL_QUERY(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				//					item.setPgm_id(rs.getString("PGM_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				//					item.setBy_dy_tm(rs.getString("BY_DY_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 장르별 아카이브 통계 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_GNR_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_GNR_ARCH_TBL_QUERY(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_GNR_ARCH_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);

			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setCtgr_l_cd(rs.getString("CTGR_L_CD"));
				item.setCtgr_l_nm(rs.getString("CTGR_L_NM"));
				item.setCtgr_m_cd(rs.getString("CTGR_M_CD"));
				item.setCtgr_m_nm(rs.getString("CTGR_M_NM"));
				item.setCtgr_s_cd(rs.getString("CTGR_S_CD"));
				item.setCtgr_s_nm(rs.getString("CTGR_S_NM"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	/**
	 * 프로그램별 아카이브 조회
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List selectSTAT_PGM_ARCH_TBL_QUERY(StatisticsConditionDO conditionDO) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_PGM_ARCH_TBL_QUERY(conditionDO);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			stmt = con.prepareStatement(query);

			int index = 0;

			rs = stmt.executeQuery();

			List resultList = new ArrayList();

			while(rs.next())
			{
				StatisticsConditionDO item = new StatisticsConditionDO();
				item.setPgm_id(rs.getString("PGM_ID"));
				item.setSum_qty(rs.getString("SUM_QTY"));
				item.setSum_tm(rs.getString("SUM_TM"));
				item.setDay(rs.getString("DAY"));
				resultList.add(item);
			}

			return resultList;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	
	/**
	 * 컨텐츠 소유권자별 다운로드 이용통계
	 * @param statisticsConditionDO
	 * @return
	 * @throws Exception 
	 */
	public String getSTAT_DOWN_COCD_USE_TBL_List(StatisticsInfo info) throws Exception
	{
		String query = StatisticsInfoStatement.selectSTAT_DOWN_COCD_USE_TBL_List(info);


		StatisticsDO statisticsDO = new StatisticsDO();

		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try 
		{
			con = DBService.getInstance().getConnection();
			//logger.debug("######selectSTAT_DEPT_USE_TBL_QUERY######## con : " + con);
			stmt = con.prepareStatement(query);
			int index = 0;

			rs = stmt.executeQuery();

			List<StatisticsInfo> resultList = new ArrayList<StatisticsInfo>();

			while(rs.next())
			{
				StatisticsInfo item = new StatisticsInfo();

				item.setGrp(rs.getString("COCD"));
				item.setGrpNm(rs.getString("CONM")); //cocd 에 대한 명이 없음.
				item.setSeg(rs.getString("SUP_DEPT_CD"));
				item.setSegNm(rs.getString("SUP_DEPT_NM"));
				item.setDept(rs.getString("DEPT_CD"));
				item.setDept_nm(rs.getString("DEPT_NM"));

				item.setSumQty(rs.getString("SUM_QTY"));
				item.setSumTm(rs.getString("SUM_TM"));
				
				item.setDay(rs.getString("DAY"));
				
				
				//공백을 채워서보냄
				//item.setPgmId(0l);
				//item.setMasterId(0l);
				//item.
				resultList.add(item);
			}
			XmlConvertorService<Das> convertorService = new XmlConvertorServiceImpl<Das>();
			Das das = new Das();
			das.setItems(resultList);
			String xml =  convertorService.createMarshaller(das);
			return xml;
		} 
		catch (Exception e) 
		{
			logger.error(query);

			
			throw e;
		}
		finally
		{
			release(rs, stmt, con);
		}
	}
	
}

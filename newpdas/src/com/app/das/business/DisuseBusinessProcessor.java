package com.app.das.business;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.DisuseDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.ArchiveIngestInfoDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DisuseConditionDO;
import com.app.das.business.transfer.PageDO;

/**
 * 폐기 프로세스에 대한 로직들이 구현되어 있는 Class
 * @author ysk523
 *
 */
public class DisuseBusinessProcessor 
{
	private static DisuseDAO disuseDAO = DisuseDAO.getInstance();

	private Logger logger = Logger.getLogger(DisuseBusinessProcessor.class);

	/**
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getDisuseTargetList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getDisuseTargetList][Input DisuseConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = disuseDAO.selectDisuseTargetList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * 폐기대상 1차 선정을 한다,(폐기정보 테이블에 저장한다)
	 * @param disuseDOList 저장할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertFirstDisuseList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			disuseDAO.insertFirstDisuseList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{


			throw e;
		}
	}

	/**
	 * 폐기대상 목록조회를 한다.
	 * @param conditionDO 검색조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getDisuseList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getDisuseList][Input DisuseConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = disuseDAO.selectDisuseList(conditionDO, commonDO);
			return pageDO;
		} 
		catch (Exception e)
		{


			throw e;
		}
	}

	/**
	 * 폐기 1차 선정된 목록에서 폐기 의뢰를 하면 폐기구분을 폐기위원 검토중으로 바꾼다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseInvestList][Input disuseDOList]" + disuseDOList);
		}


		try 
		{
			disuseDAO.updateDisuseInvestList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{


			throw e;
		}
	}

	/**
	 * 폐기위원 검토 목록에서 연장 처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseExtensionList][Input disuseDOList]" + disuseDOList);
		}

		try 
		{
			disuseDAO.updateDisuseExtensionList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * 폐기위원 검토 목록에서 폐기 검토 완료처리를 하고 폐기구분을 데이터 정보팀 심의 상태로 수정한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseInvestCompletList][Input disuseDOList]" + disuseDOList);
		}

		try 
		{
			disuseDAO.updateDisuseInvestCompletList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{


			throw e;
		}
	}

	/**
	 * 폐기목록에서 연장을 확정한다.(마스터 테이블의 보존기간 만료일을 수정한다.)
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseExtensionCompletList][Input disuseDOList]" + disuseDOList);
		}


		try 
		{
			disuseDAO.updateDisuseExtensionCompletList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * 폐기 완료처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseCompletList][Input disuseDOList]" + disuseDOList);
		}

		try 
		{
			disuseDAO.updateDisuseCompletList(disuseDOList, commonDO);
		} 
		catch (Exception e)
		{


			throw e;
		}

	}

	/**
	 * 폐기 취소 Process를 수행하기위한 폐기구분에 해당하는 목록 조회를 한다.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getDisuseCancelList(DisuseConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			PageDO pageDO = disuseDAO.selectDisuseCancelList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e)
		{


			throw e;
		}

	}

	/**
	 * 선택되어진 폐기구분에 해당하는 취소 Process를 수행한다.
	 * @param disuseDOList  DisuseDO를 포함하고 있는 List
	 * @param disuseClf  폐기구분 코드
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseCalcelProcessorList(List disuseDOList, String disuseClf, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDisuseCalcelProcessorList][폐기구분코드]" + disuseClf);
		}

		try 
		{
			//폐기구분코드가 1차성정이면 폐기정보 테이블에 있던 것을 삭제한다.
			if(CodeConstants.DisuseKind.FIRST_CHOICE.equals(disuseClf))
			{
				disuseDAO.deleteFirstDisuseList(disuseDOList, commonDO);
			}
			//폐기구분코드가 폐기위원검토이면 페기구분 코드를 1차 선정으로 변경한다.
			else if(CodeConstants.DisuseKind.INVESTIGATION.equals(disuseClf))
			{
				disuseDAO.updateDisuseInvestCancelList(disuseDOList, commonDO);
			}
			//폐기구분코드가 데이터정보팀심의  이면 폐기구분을 폐기위원 검토중으로 수정하고
			//폐기위원 검토완료일을 Clear 시킨다.
			else if(CodeConstants.DisuseKind.DATA_INFO_DISCUSSION.equals(disuseClf))
			{
				disuseDAO.updateDisuseInvestCompletCancelList(disuseDOList, commonDO);
			}
			//	폐기구분코드가 폐기완료  이면 폐기구분을 데이터정보팀심의로 수정하고
			// 데이터 정보팀확정일을 Clear 시킨다.
			// 또한, 메타데이터마스터의 삭제일자를 셋팅한다.
			else if(CodeConstants.DisuseKind.DISUSE_COMPLETION.equals(disuseClf))
			{
				disuseDAO.updateDisuseCompletCancelList(disuseDOList, commonDO);
			}
			// 페기구분코드가 폐기 취소이면 폐기구분을 데이타정보팀 심의로 수정하고 
			// 메타데이터마스터의 보존기간 만료일을 Clear 한다.
			else if(CodeConstants.DisuseKind.DISUSE_CANCEL.equals(disuseClf))
			{
				disuseDAO.updateDisuseExtensionCompletCancelList(disuseDOList, commonDO);
			}
			else
			{
				DASException exception = new DASException(ErrorConstants.NOT_INPUT_DISUSE_CODE, "폐기구분코드가 입력되지 않았습니다.");
				throw exception;

			}

		} 
		catch (Exception e)
		{

			throw e;
		}

	}

	/**
	 * 인제스트 된 목록.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getArchiveIngestList(ArchiveIngestInfoDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			PageDO pageDO = disuseDAO.selectArchiveIngestList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e)
		{


			throw e;
		}

	}






	/**
	 * 폐기 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getDisCardList(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getDisCardList][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getDisCardList(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}



	/**
	 * 폐기 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getSumDiscard(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSumDiscard][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getSumDiscard(condition);


		} 
		catch (Exception e)
		{


			throw e;
		}
	}
	/*	public List getDisCardList(DiscardDO condition) throws DASException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getDisCardList][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getDisCardList(condition);


		} 
		catch (Exception e)
		{
			e.printStackTrace();

			String errorMsg = errorHandler.getProperty(e.getExceptionCode());
			if(!StringUtils.isEmpty(errorMsg))
			{
				e.setExceptionMsg(errorMsg);
			}
			logger.error(e.getExceptionMsg(), e);

			throw e;
		}
	}
	 */



	/**
	 * 폐기 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getHyenDisCardList(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getHyenDisCardList][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getHyenDisCardList(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}



	/**
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getSumHyenDiscard(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSumHyenDiscard][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.selectSumHyenDiscard(condition);


		} 
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 연장 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getHyenUseList(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getHyenUseList][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getHyenUseList(condition);


		} 
		catch (Exception e)
		{


			throw e;
		}
	}


	/**
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getSumHyenuse(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getSumHyenuse][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.selectSumHyenuse(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}


	/**
	 *  폐기 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */


	public int[] insertDisuse(List roleDO)  throws Exception
	{
		try 
		{

			if (roleDO != null && roleDO.size() > 0) {

				Iterator _iter = roleDO.iterator();
				for(int i=0;i<roleDO.size();i++){
					DiscardDO discard = (DiscardDO)roleDO.get(i);
					if(discard.getMaster_id()!=0){
						disuseDAO.deleteUse(discard.getMaster_id());
					}
				}

			}
			return disuseDAO.insertDisuse(roleDO);
		} 
		catch (Exception e)
		{

			throw e;
		}
	}


	/**
	 *  폐기 정보를 등록 한다.(영상선정에서 삭제시 사용)
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertDisuseForMeta(DiscardDO roleDO)  throws Exception
	{
		if(logger.isDebugEnabled()) 
		{
			logger.debug("[insertDisuse][Input DiscardDO]" + roleDO);
		}

		try 
		{
			return disuseDAO.insertDisuseForMeta(roleDO);

		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 *  폐기 정보를 등록을 취소 한다. <-사용않함
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse(int master_id)  throws Exception
	{
		try 
		{
			return disuseDAO.cancelDisuse(master_id);

		} 
		catch (Exception e)
		{

			throw e;
		}
	}
	/**
	 *  폐기 정보를 등록을 취소 한다.
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse2(String master_id)  throws Exception
	{
		try 
		{
			return disuseDAO.cancelDisuse2(master_id);

		} 
		catch (Exception e)
		{


			throw e;
		}
	}



	/**
	 *  연장 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */


	public int insertUse(List roleDO)  throws Exception
	{
		if(logger.isDebugEnabled()) 
		{
			logger.debug("[insertUse][Input DiscardDO]" + roleDO);
		}

		try 
		{

			if (roleDO != null && roleDO.size() > 0) {

				Iterator _iter = roleDO.iterator();
				for(int i=0;i<roleDO.size();i++){
					DiscardDO discard = (DiscardDO)roleDO.get(i);
					if(discard.getMaster_id()!=0){
						disuseDAO.deleteUse(discard.getMaster_id());
					}
				}

			}
			disuseDAO.insertUse(roleDO);
			return 1;

		} 
		catch (Exception e)
		{

			throw e;
		}
	}
	/*public int insertUse(DiscardDO roleDO)  throws DASException
	{
		if(logger.isDebugEnabled()) 
		{
			logger.debug("[insertUse][Input DiscardDO]" + roleDO);
		}

		try 
		{
			return disuseDAO.insertUse(roleDO);

		} 
		catch (Exception e)
		{
			e.printStackTrace();

			String errorMsg = errorHandler.getProperty(e.getExceptionCode());
			if(!StringUtils.isEmpty(errorMsg))
			{
				e.setExceptionMsg(errorMsg + e.getMessage());
			}
			logger.error(e.getExceptionMsg(), e);

			throw e;
		}
	}*/


	/**
	 * 연장 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getJonrCodeList(DiscardDO condition) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getHyenUseList][Input DiscardDO]" + condition);
		}

		try 
		{
			return disuseDAO.getHyenUseList(condition);


		} 
		catch (Exception e)
		{

			throw e;
		}
	}




	//	/**
	//	 * 폐기신청시 한번 연장되었던 존재를 폐기한다
	//	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	//	 * @param commonDO 공통정보
	//	 */
	/*public void updateDisuseForExist(List disuseDOList) throws DASException
	{
	        int count = 0;
	        boolean result = false;
	        Connection con = null;
	        PreparedStatement stmt = null;
	        try{
	        	con = DBService.getInstance().getConnection();
	        	con.setAutoCommit(false);

	        	StringBuffer buf = new StringBuffer();
	    		buf.append("\n update DAS.discard_info_tbl set app_cont = ? , DOWN_STAT = ? ");
	    		buf.append("\n where CART_NO = ? AND CART_SEQ =  ? "  );
	    		buf.append("\n WITH UR	 ");

	    		int cnt = 0;
	    		stmt = LoggableStatement.getInstance(con, buf.toString());
	    		stmt.setString(++cnt,_do.getApp_cont());
	    		stmt.setString(++cnt,_do.getDown_stat());
	    		stmt.setLong(++cnt, _do.getCartNo());
	    		stmt.setLong(++cnt, _do.getCartSeq());

	            count = stmt.executeUpdate();

	            if (count > 0) {
	            	result = true;
	            }

	            con.setAutoCommit(true);

	            return count;

	    	}
	        catch (NamingException e) 
			{
				// TODO 자동 생성된 catch 블록
				e.printStackTrace();

		        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
		        throw exception;
			}
    		catch (SQLException e) 
    		{
    			// TODO 자동 생성된 catch 블록
    			e.printStackTrace();

    	        if (logger.isDebugEnabled()) 
    	        {
    	                logger.debug("[NamingException]" + e);
    	        }
    	        if(con != null)
    	        {
    	        	try {
    					con.rollback();
    				} catch (SQLException e1) {
    					// TODO 자동 생성된 catch 블록
    					e1.printStackTrace();
    				}
    	        }

    	        DASException exception = new DASException(ErrorConstants.SYSTEM_ERR, "시스템 장애입니다.", e);
    	        throw exception;
    		}finally{
    			release(null, stmt, con);
    		}

	}*/

}

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

		PageDO pageDO = disuseDAO.selectDisuseTargetList(conditionDO, commonDO);

		return pageDO;

	}

	/**
	 * 폐기대상 1차 선정을 한다,(폐기정보 테이블에 저장한다)
	 * @param disuseDOList 저장할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertFirstDisuseList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.insertFirstDisuseList(disuseDOList, commonDO);

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

		PageDO pageDO = disuseDAO.selectDisuseList(conditionDO, commonDO);
		return pageDO;

	}

	/**
	 * 폐기 1차 선정된 목록에서 폐기 의뢰를 하면 폐기구분을 폐기위원 검토중으로 바꾼다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.updateDisuseInvestList(disuseDOList, commonDO);

	}

	/**
	 * 폐기위원 검토 목록에서 연장 처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.updateDisuseExtensionList(disuseDOList, commonDO);

	}

	/**
	 * 폐기위원 검토 목록에서 폐기 검토 완료처리를 하고 폐기구분을 데이터 정보팀 심의 상태로 수정한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseInvestCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.updateDisuseInvestCompletList(disuseDOList, commonDO);

	}

	/**
	 * 폐기목록에서 연장을 확정한다.(마스터 테이블의 보존기간 만료일을 수정한다.)
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseExtensionCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.updateDisuseExtensionCompletList(disuseDOList, commonDO);

	}

	/**
	 * 폐기 완료처리를 한다.
	 * @param disuseDOList 수정할 DisuseDO 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDisuseCompletList(List disuseDOList, DASCommonDO commonDO) throws Exception
	{

		disuseDAO.updateDisuseCompletList(disuseDOList, commonDO);

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

		PageDO pageDO = disuseDAO.selectDisuseCancelList(conditionDO, commonDO);

		return pageDO;

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

	/**
	 * 인제스트 된 목록.
	 * @param conditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List DisuseDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getArchiveIngestList(ArchiveIngestInfoDO conditionDO, DASCommonDO commonDO) throws Exception
	{

		PageDO pageDO = disuseDAO.selectArchiveIngestList(conditionDO, commonDO);

		return pageDO;

	}






	/**
	 * 폐기 정보를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getDisCardList(DiscardDO condition) throws Exception
	{

		return disuseDAO.getDisCardList(condition);

	}



	/**
	 * 폐기 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getSumDiscard(DiscardDO condition) throws Exception
	{

		return disuseDAO.getSumDiscard(condition);

	}


	/**
	 * 폐기 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getHyenDisCardList(DiscardDO condition) throws Exception
	{

		return disuseDAO.getHyenDisCardList(condition);

	}


	/**
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getSumHyenDiscard(DiscardDO condition) throws Exception
	{

		return disuseDAO.selectSumHyenDiscard(condition);

	}

	/**
	 * 연장 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getHyenUseList(DiscardDO condition) throws Exception
	{

		return disuseDAO.getHyenUseList(condition);

	}


	/**
	 * 폐기 현황 정보의 길이합, 조회건수를 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */


	public List getSumHyenuse(DiscardDO condition) throws Exception
	{

		return disuseDAO.selectSumHyenuse(condition);

	}


	/**
	 *  폐기 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */


	public int[] insertDisuse(List roleDO)  throws Exception
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


	/**
	 *  폐기 정보를 등록 한다.(영상선정에서 삭제시 사용)
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */
	public int insertDisuseForMeta(DiscardDO roleDO)  throws Exception
	{

		return disuseDAO.insertDisuseForMeta(roleDO);

	}

	/**
	 *  폐기 정보를 등록을 취소 한다. <-사용않함
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse(int master_id)  throws Exception
	{

		return disuseDAO.cancelDisuse(master_id);

	}

	/**
	 *  폐기 정보를 등록을 취소 한다.
	 * @param master_id 마스터id
	 * @param 
	 * @throws Exception 
	 */
	public int cancelDisuse2(String master_id)  throws Exception
	{

		return disuseDAO.cancelDisuse2(master_id);

	}



	/**
	 *  연장 정보를 등록 한다.
	 * @param roleDO 권한 정보가 포함되어 있는 DataObject
	 * @param 
	 * @throws Exception 
	 */


	public int insertUse(List roleDO)  throws Exception
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

	/**
	 * 연장 현황를 목록 조회한다.
	 * @param condition 조회조건을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public List getJonrCodeList(DiscardDO condition) throws Exception
	{

		return disuseDAO.getHyenUseList(condition);

	}

}

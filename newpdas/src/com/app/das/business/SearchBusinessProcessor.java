package com.app.das.business;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;

import com.app.das.business.dao.SearchDAO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.SearchConditionDO;
import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.business.transfer.TapeItemInfoDO;
import com.app.das.business.transfer.TapeLendingDO;
import com.konantech.search.data.ParameterVO;

/**
 * 통합 검색에서 내목록 등록, 삭제, 조회 및 테이프대출의 등록, 삭제,  요청 영상 목록 조회의 조회, 제목수정의 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class SearchBusinessProcessor 
{
	private Logger logger = Logger.getLogger(SearchBusinessProcessor.class);

	private static SearchDAO searchDAO = SearchDAO.getInstance();

	/**
	 * 내목록에 저장한다. (DAS2.0 수정)
	 * @param myCatalogDO myCatalogDO 내목록에 저장할 내용을 포함하고 있는 DataObject
	 * @return
	 * @throws Exception 
	 */
	public int insertMyCatalog(List myCatalogDO) throws Exception
	{			
		searchDAO.insertMyCatalog(myCatalogDO);
		return 1;

	}

	/**
	 * 내목록 조회를 한다.
	 * @param searchConditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 내목록 정보를 page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getMyCatalogList(SearchConditionDO searchConditionDO, DASCommonDO commonDO) throws Exception
	{

		return searchDAO.selectMyCatalog(searchConditionDO, commonDO);

	}


	/**
	 * 내목록 조회를 한다.
	 * @param searchConditionDO 조회조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 내목록 정보를 page 형태로 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public List getMyCatalogLists(MyCatalogDO commonDO) throws Exception
	{

		return searchDAO.selectMyCatalogs(commonDO);

	}

	/**
	 * 내목록의 특정 정보를 삭제한다.
	 * @param reqUserId 요청자 ID
	 * @param seq 순번
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteMyCatalogInfo(String seq, DASCommonDO commonDO) throws Exception
	{

		searchDAO.deleteMyCatalogInfo(seq, commonDO);

	}


	/**
	 * 내 목록 담기 내용을 삭제한다.
	 * @param myCatalogDO 내 목록 내용 정보
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int deleteMyCatalogInfo(MyCatalogDO mycatalogDO) throws Exception
	{

		if(org.apache.commons.lang.StringUtils.isNotBlank(mycatalogDO.getDel_seq())) {
			searchDAO.deleteMyCatalogInfo( mycatalogDO);
		}
		return 1;

	}

	/**
	 * 테이프 대출 신청을 한다.
	 * @param tapeLendingDO 대출할 Tape 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void insertTapeLending(TapeLendingDO tapeLendingDO, DASCommonDO commonDO) throws Exception
	{

		//이미 해당 사용자로 테이프 대출 정보가 존재하면 테이프 대출정보는 수정하고 테이프 상세 정보만 등록한다.
		if(searchDAO.isThereTapeLending(commonDO.getUserNo(), commonDO.getUserId()))
		{
			searchDAO.updateTapeLendingItems(tapeLendingDO, commonDO);
		}
		else
		{
			searchDAO.insertTapeLending(tapeLendingDO, commonDO);
		}

	}

	/**
	 * 테이프 대출 신청건을 삭제한다.
	 * @param lendAplnNo 대출 신청 번호
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteTapeLendingAll(String lendAplnNo, DASCommonDO commonDO) throws Exception
	{

		searchDAO.deleteTapeLendingAll(lendAplnNo, commonDO);

	}

	/**
	 * 테이프 대출 상세 내역을 삭제 한다.
	 * @param tapeLendingItemDOList TapeLendingItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void deleteTapeLendingItemList(List tapeLendingItemDOList, DASCommonDO commonDO) throws Exception
	{

		searchDAO.deleteTapeLendingItemList(tapeLendingItemDOList, commonDO);

	}


	/**
	 * 테이프 대출 신청 정보를 조회한다.
	 * @param userNo 사번
	 * @param userId 사용자 ID
	 * @return TapeLendingDO
	 * @throws Exception 
	 */
	public TapeLendingDO getTapeLendingInfo(DASCommonDO commonDO) throws Exception
	{

		//테이프 대출 정보를 조회한다.
		TapeLendingDO tapeLendingDO = searchDAO.selectTapeLendingInfo(commonDO.getUserNo(), commonDO.getUserId());

		//테이프 대출 상세 정보를 조회한다.
		List tapeLendingItemDOList = searchDAO.selectTapeLendingItemInfo(tapeLendingDO.getLendAplnNo());
		tapeLendingDO.setTapeLendingItemDOList(tapeLendingItemDOList);

		return tapeLendingDO;

	}

	/**
	 * 테이프 대출 청구번호를 조회한다.
	 * @param lendAplnNo 대출신청번호
	 * @return List
	 * @throws Exception 
	 */
	public List getTapeLendingItemReqNo(DASCommonDO commonDO) throws Exception
	{

		//테이프 대출 정보를 조회한다.
		TapeLendingDO tapeLendingDO = searchDAO.selectTapeLendingInfo(commonDO.getUserNo(), commonDO.getUserId());

		//테이프 대출 상세 정보를 조회한다.
		return searchDAO.selectTapeLendingItemReqNo(tapeLendingDO.getLendAplnNo());

	}

	/**
	 * 요청 영상 목록 조회를 한다.
	 * @param searchConditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return PageDO 조회한 요청 영상 목록을 포함하고 있는 PageDO
	 * @throws Exception 
	 */
	public PageDO getRequestDownList(SearchConditionDO searchConditionDO, DASCommonDO commonDO) throws Exception
	{

		PageDO pageDO = searchDAO.selectRequestDownList(searchConditionDO, commonDO);

		return pageDO;

	}

	/**
	 * 다운로드 제목과 요청자명을 수정한다.
	 * @param downSubject 다운로드 제목
	 * @param reqUserNm 요청자명
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateRequestDownSubject(String downSubject, String reqUserNm, DASCommonDO commonDO) throws Exception
	{

		searchDAO.updateRequestDownSubject(downSubject, reqUserNm, commonDO);

	}

	/**
	 * 스토리 보드 화면에서 감수시 자료상태 코드를 감수완료로 수정한다
	 * @param masterId 마스타ID
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void updateDataStatus(String masterId, DASCommonDO commonDO) throws Exception
	{

		searchDAO.updateDataStatus(masterId, commonDO);

	}

	/**
	 * 테이프 아이템 상세 조회를 한다.
	 * @param req_no 청구번호
	 * @param commonDO 공통정보
	 * @return TapeItemInfoDO
	 * @throws Exception 
	 */
	public TapeItemInfoDO getTapeItemInfo(String req_no, DASCommonDO commonDO) throws Exception
	{

		TapeItemInfoDO tapeItemInfo = searchDAO.viewTapeItemInfo(req_no);

		return tapeItemInfo;

	}

	/**
	 * 테이프 상세 조회를 한다.
	 * @param req_no 청구번호
	 * @param commonDO 공통정보
	 * @return TapeInfoDO
	 * @throws Exception 
	 */
	public TapeInfoDO getTapeInfo(String req_no, DASCommonDO commonDO) throws Exception
	{

		TapeInfoDO tapeInfo = searchDAO.viewTapeInfo(req_no);

		return tapeInfo;

	}

	/**
	 * 검색엔진을 통한 조회
	 * @param searchInfoDO
	 * @return xml
	 * @throws RemoteException
	 */
	public String getSearchText(ParameterVO parameterVO) throws Exception{
		return searchDAO.getSearchText(parameterVO);
	}

}

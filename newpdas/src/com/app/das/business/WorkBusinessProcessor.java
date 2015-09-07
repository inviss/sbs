package com.app.das.business;

import java.io.PrintWriter;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.app.das.business.constants.CodeConstants;
import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.constants.ErrorConstants;
import com.app.das.business.dao.ExternalDAO;
import com.app.das.business.dao.UserRoleDAO;
import com.app.das.business.dao.WorkDAO;
import com.app.das.business.exception.DASException;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.business.transfer.DASCommonDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.business.transfer.PageDO;
import com.app.das.business.transfer.PdaInfoDO;
import com.app.das.business.transfer.SearchDO;
import com.app.das.business.transfer.WorkOrdersConditionDO;
import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.util.CalendarUtil;

/**
 * 작업현황의 매체변환, 주조송출, 작업지시, 사용제한 승인에 대한 로직이 구현되어 있다.
 * @author ysk523
 *
 */
public class WorkBusinessProcessor 
{
	private Logger logger = Logger.getLogger(WorkBusinessProcessor.class);

	private static final WorkDAO workDAO = WorkDAO.getInstance();
	private static final ExternalDAO externalDAO = ExternalDAO.getInstance();
	private static final UserRoleDAO userRoleDAO = UserRoleDAO.getInstance();

	/**
	 * 작업 현황중 매체변환 목록조회를 한다.
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkStatusDO를 포함하고 있는 DataObejct
	 * @throws Exception 
	 */
	public PageDO getWorkStatusMesiumChangeList(WorkStatusConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getWorkStatusMesiumChangeList][Input WorkStatusConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = workDAO.selectWorkStatusList(conditionDO, DASBusinessConstants.WorkOrdersKind.MESIUM, commonDO);

			return pageDO;

		} 
		catch (Exception e) 
		{

			throw e;
		}
	}

	/**
	 * 작업 현황 중 주조 송출 목록조회
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkStatusDO를 포함하고 있는 DataObejct
	 * @throws Exception 
	 */
	public PageDO getWorkStatusMainOperationTransferList(WorkStatusConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getWorkStatusMainOperationTransferList][Input WorkStatusConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = workDAO.selectWorkStatusList(conditionDO, DASBusinessConstants.WorkOrdersKind.MAIN_TRANSFER, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 오류 내역을 조회한다.
	 * @param masterId 마스타 ID
	 * @param commonDO 공통정보
	 * @return ErrorRegisterDO 오류정보를 포함하고 있는 DataObject
	 * @throws Exception 
	 */
	public ErrorRegisterDO getErrorRegisterInfo(String masterId, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getErrorRegisterInfo][Input masterId]" + masterId);
		}

		try 
		{
			return workDAO.selectErrorRegisterInfo(masterId, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 오류내역에 대한 작업 재지시 처리를 한다.
	 * @param errorRegisterDO 오류 정보를 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void reWorkOrder(ErrorRegisterDO errorRegisterDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[reWorkOrder][Input ErrorRegisterDO]" + errorRegisterDO);
		}

		try 
		{
			workDAO.updateReWorkOrder(errorRegisterDO, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}

	}

	/**
	 * 작업지시 목록 조회를 한다.(작업 순위가 DB에 셋팅되어 있지 않는 경우 '3' 보통으로 셋팅한다)
	 * @param conditionDO 조회 조건을 포함하고 있는 DataObject
	 * @param commonDO 공통정보
	 * @return List WorkOrdersDO 를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getWorkOrdersList(WorkOrdersConditionDO conditionDO, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getWorkOrdersList][Input WorkOrdersConditionDO]" + conditionDO);
		}

		try 
		{
			PageDO pageDO = workDAO.selectWorkOrdersList(conditionDO, commonDO);

			return pageDO;
		} 
		catch (Exception e) 
		{
		
			throw e;
		}
	}

	/**
	 * 조회된 작업지시 목록에서 작업 순위를 수정한다.
	 * @param workOdersDO 작업 순위를 포함하고 있는 DataObject
	 * @param comonDO 공통정보
	 * @throws Exception 
	 */
	public void updateWorkOrdersList(List workOdersDOList, DASCommonDO commonDO) throws Exception
	{
		try 
		{
			workDAO.updateWorkOrdersList(workOdersDOList, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	//	/**
	//	 * 오류 내역 항목의 작업 순위를 수정한다.
	//	 * @param errorRegisterDO 오류 내역 등록 정보를 포함하는 DataObject
	//	 * @param comonDO 공통정보
	//	 */	
	/*
	public void updateErrorItemWorkOrders(ErrorRegisterDO errorRegisterDO, DASCommonDO commonDO) throws DASException
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateErrorItemWorkOrders][Input ErrorRegisterDO]" + errorRegisterDO);
		}

		try 
		{
			workDAO.updateErrorItemWorkOrders(errorRegisterDO, commonDO);
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
	}
	 */	

	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getApproveCartList(DASCommonDO commonDO) throws Exception
	{
		try 
		{
			return workDAO.selectApproveCartList(commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param cartNo 카트번호
	 * @param commonDO 공통정보
	 * @return List CartDetailItemDO를 포함하고 있는 DO
	 * @throws Exception 
	 */
	public List getApproveCartDetailsList(String cartNo, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getApproveCartDetailsList][Input cartNo]" + cartNo);
		}

		try 
		{
			return workDAO.selectApproveCartDetailsList(cartNo, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void approveCartList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[approveCartList][Input cartItemDOList]" + cartItemDOList);
		}

		try 
		{
			workDAO.updateCartApproveList(cartItemDOList, commonDO);
		} 
		catch (Exception e) 
		{

			throw e;
		}
	}

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리전 사용제한이 걸려 있는지 확인 한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List 
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public boolean checkCartList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		boolean sResult = true;
		if(logger.isDebugEnabled())
		{
			logger.debug("[checkCartList][Input cartItemDOList]" + cartItemDOList);
		}

		try 
		{
			sResult = workDAO.checkCartApproveList(cartItemDOList, commonDO);
			return sResult;
		} 
		catch (Exception e) 
		{
		
			throw e;
		}
	}


	/**
	 * 조회된 콘텐트 목록을 삭제한다.
	 * @param msterIdGrp
	 * @throws Exception 
	 */
	public void deleteContentItemList(String msterIdGrp) throws Exception
	{
		try 
		{
			workDAO.deleteContentItemList(msterIdGrp);
		} 
		catch (Exception e)
		{

			throw e;
		}
	}
	/**
	 * 요청건별 상세내역에서 선택된 목록을 삭제한다.
	 * @param cartNo 카트번호
	 * @param checkList 카트번호안의 상세목록중 선택된 목록
	 * @throws Exception 
	 */
	public void approveCartDetailList(String cartNo, String checkList,  DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[approveCartList][Input cartItemDOList]" + cartNo + checkList);
		}

		try 
		{
			workDAO.deleteCartDetailApproveList(cartNo, checkList, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}


	/**
	 * 요청건별 상세내역에서 선택된 목록을 수정한다.
	 * @param cartNo 카트번호
	 * @param checkList 카트번호안의 상세목록중 선택된 목록
	 * @throws Exception 
	 */
	public void CartDetailUpdate(String cartNo, String[] checkList, String[] app_cont, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[approveCartList][Input cartItemDOList]" + cartNo + checkList);
		}

		try 
		{
			for (int i = 0; i<checkList.length; i++) {
				workDAO.updateCartDetailApproveList(cartNo, checkList[i], app_cont[i], commonDO);
			}
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}


	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public PageDO getApproveCartList(String pageCount, DASCommonDO commonDO, String startDate, String endDate) throws Exception
	{
		try 
		{
			return workDAO.selectApproveCartList(pageCount, commonDO, startDate, endDate);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 * 카트 다운 로드를 요청한다.
	 * @param workStatusDOList 콘텐트 목록
	 * @throws Exception 
	 */	
	public void requestDownloadCart(String cartNum, String state) throws Exception
	{

		if(logger.isDebugEnabled())
		{
			logger.debug("[requestDownloadCart][Input cartNum]" + cartNum);
		}

		try 
		{   int updateCount;

		if(state.equals(CodeConstants.CartStatus.APPROVE))
		{
			// 상태 코드를 다운 로드 진행중 "006" 으로 변경한다
			updateCount = externalDAO.updateDownCartState(Long.parseLong(cartNum), CodeConstants.CartStatus.DOWNLOAD, "");

			// 다운로드 제한 여부가 No이 웹 서비스를 호출하여 다운로드 카트를 승인 없이  다운로드 한다
			//				ServiceNevigatorIF port = new ServiceNevigatorService_Impl().getServiceNevigatorIFPort();
			//				port.downloadService(cartNum);
		}
		else
			// 상태 코드를  승인거부  "005" 으로 변경한다
			updateCount = externalDAO.updateDownCartState(Long.parseLong(cartNum), CodeConstants.CartStatus.APPROVE_REJECT, "");

		if (updateCount == 0){
			DASException exception = new DASException(ErrorConstants.NO_MACHING_FILED, "UPDATE 또는 DELETE에 대한 행이 없습니다.");
			throw exception;
		}

		} 
	
		catch (Exception e) 
		{
			throw e;

			
		}
	}

	/**
	 * 검색어를 받아 검색된 목록을 리턴한다. 
	 * @param String xml형식으로 된 String을 받아 분석하여 검색을 실행
	 * @return String xml형식으로 된 목록을  돌려준다.
	 * @throws Exception 
	 */
	public String getSearchList(String xmlParam) throws Exception
	{
		PrintWriter printWriter = null;
		Document doc;
		SearchDO searchDO = new SearchDO();
		ArrayList list = new ArrayList(); 
		StringBuffer result = new StringBuffer();
		int count = 0;

		try 
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setIgnoringElementContentWhitespace(true);
			// 팩토리로부터 Document파서를 얻어내도록 한다.
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new InputSource(new StringReader(xmlParam)));
			Element root = doc.getDocumentElement();

			/**
			 * 인덱스명 / 검색어나 상세검색, 날짜 (검색하는것 하나이상 꼭 필요) / 현재 페이지 기본('0') / 한페이지에 보여줄 목록수 
			 * <?xml version='1.0' encoding='UTF-8'?>
			 * <data>
			 *    인덱스 명
			 * 		<indexName></indexName>
			 *    검색어
			 * 		<searchKey></searchKey>
			 *    검색어 목록(결과내 검색)
			 * 		<searchKeyList></searchKeyList>
			 *    상세검색 연산자(상세검색 비어 있는것 까지 기본적으로 넣어주어야함 순서도 XML 상세검색 순으로) ex) and, and, and, ....
			 * 		<operatorList></operatorList>
			 *    시작 방송일
			 * 		<startdate></startdate>
			 *    종료 방송일
			 * 		<enddate></enddate>
			 *    현재 페이지
			 * 		<nowPage></nowPage>
			 *    한 페이지에 보여줄 목록 수 
			 * 		<pageCount></pageCount>
			 *    정렬 컬럼(컬럼명)
			 *      <sortColumn></sortColumn>
			 *    정렬 방식(오름차순(on) / 내림차순(off))
			 *      <asc></asc>
			 *  상세검색
			 *    시작 생성일
			 * 		<startReg_dt></startReg_dt>
			 *    종료 생성일
			 *    	<endReg_dt></endReg_dt>
			 *    대분류 장르
			 *    	<largeJanre></largeJanre>
			 *    중분류 장르
			 *    	<mediumJanre></mediumJanre>
			 *    소분류 장르
			 *    	<smallJanre></smallJanre>
			 *    프로그램 명
			 *    	<pgm_nm></pgm_nm>
			 *    타이틀
			 *    	<title></title>
			 *    진행자
			 *    	<mc_nm></mc_nm>
			 *    촬영자
			 *    	<cmr_drt_nm></cmr_drt_nm>
			 *    작가
			 *    	<writer_nm></writer_nm>
			 *    연출자
			 *    	<drt_nm></drt_nm>
			 *    특이사항
			 *    	<spc_info></spc_info>
			 *    프로듀서명
			 *    	<producer_nm></producer_nm>
			 *    저작권 형태 설명
			 *    	<cprt_type_dsc></cprt_type_dsc>
			 *    촬영장소
			 *    	<cmr_place></cmr_place>
			 *    제작부서
			 *    	<prdt_dept_nm></prdt_dept_nm>
			 *    편성명
			 *    	<schd_pgm_nm></schd_pgm_nm>
			 *    부제
			 *    	<sub_ttl></sub_ttl>
			 *    출연자명
			 *    	<cast_nm></cast_nm>
			 *    아카이버 명
			 *    	<sec_arch_nm></sec_arch_nm>
			 *    음악정보
			 *    	<music_info></music_info>
			 *    수상내역
			 *    	<award_hstr></award_hstr>
			 *    시청등급
			 *    	<view_gr_cd></view_gr_cd>
			 *    명장면 명대사
			 *    	<cont></cont>
			 *    코너제목
			 *    	<cn_nm></cn_nm>
			 *    심의등급
			 *    	<dlbr_cd></dlbr_cd>
			 *    저작권형태
			 *    	<cprt_type></cprt_type>
			 *    저작권자명
			 *    	<cprtr_nm></cprtr_nm>
			 *    영상 ID
			 *    	<ct_id></ct_id>
			 *    시청률
			 *    	<pgm_rate></pgm_rate>
			 *    제작구분
			 *    	<prdt_in_outs_cd></prdt_in_outs_cd>
			 * 	</data>
			 */

			printNodeName(root.getChildNodes(), searchDO);


			//			// 받은 데이터에서 값을 추출한다.
			//			// 검색 인덱스 명
			//			NodeList node1 = root.getElementsByTagName("indexName");
			//			searchDO.setSearchKey(node1.item(0).getTextContent());
			//			// 검색어 
			//			NodeList node2 = root.getElementsByTagName("searchKey");
			//			searchDO.setSearchKey(node2.item(0).getTextContent());
			//			if (!"".equals(node2.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 검색어 목록 <결과내 검색>
			//			NodeList node3 = root.getElementsByTagName("searchKeyList");
			//			searchDO.setSearchKeyList(node3.item(0).getTextContent());
			//			if (!"".equals(node3.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 검색 연산자 <AND, OR, NOT>
			//			NodeList node4 = root.getElementsByTagName("operatorList");
			//			searchDO.setOperatorList(node4.item(0).getTextContent());
			//			// 방송일 검색 시작일
			//			NodeList node5 = root.getElementsByTagName("startdate");
			//			searchDO.setStartdate(node5.item(0).getTextContent());
			//			if (!"".equals(node5.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 방송일 검색 종료일
			//			NodeList node6 = root.getElementsByTagName("enddate");
			//			searchDO.setEnddate(node6.item(0).getTextContent());
			//			if (!"".equals(node6.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 현재 페이지 
			//			NodeList node7 = root.getElementsByTagName("nowPage");
			//			searchDO.setNowPage(Integer.parseInt(node7.item(0).getTextContent()));
			//			// 출력시킬 목록 수
			//			NodeList node8 = root.getElementsByTagName("pageCount");
			//			searchDO.setPageCount(Integer.parseInt(node8.item(0).getTextContent()));
			//			// 정렬 컬럼
			//			NodeList node8_1 = root.getElementsByTagName("sortColumn");
			//			searchDO.setSortColumn(node8_1.item(0).getTextContent());
			//			// 정렬 방식 (오름차순(on) / 내림차순(off))
			//			NodeList node8_2 = root.getElementsByTagName("asc");
			//			searchDO.setAsc(node8_2.item(0).getTextContent());
			//			
			//			
			//			
			//			
			//			
			//			// 상세검색 
			//			// 시작 생성일
			//			NodeList node9 = root.getElementsByTagName("startReg_dt");
			//			searchDO.setStartReg_dt(node9.item(0).getTextContent());
			//			if (!"".equals(node9.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 종료 생성일
			//			NodeList node10 = root.getElementsByTagName("endReg_dt");
			//			searchDO.setEndReg_dt(node10.item(0).getTextContent());
			//			if (!"".equals(node10.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 대분류 장르
			//			NodeList node11 = root.getElementsByTagName("largeJanre");
			//			searchDO.setLargeJanre(node11.item(0).getTextContent());
			//			if (!"".equals(node11.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 중분류 장르
			//			NodeList node12 = root.getElementsByTagName("mediumJanre");
			//			searchDO.setMediumJanre(node12.item(0).getTextContent());
			//			if (!"".equals(node12.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 소분류 장르
			//			NodeList node13 = root.getElementsByTagName("smallJanre");
			//			searchDO.setSmallJanre(node13.item(0).getTextContent());
			//			if (!"".equals(node13.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 프로그램 명
			//			NodeList node14 = root.getElementsByTagName("pgm_nm");
			//			searchDO.setPgm_nm(node14.item(0).getTextContent());
			//			if (!"".equals(node14.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 타이틀
			//			NodeList node15 = root.getElementsByTagName("title");
			//			searchDO.setTitle(node15.item(0).getTextContent());
			//			if (!"".equals(node15.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 진행자
			//			NodeList node16 = root.getElementsByTagName("mc_nm");
			//			searchDO.setMc_nm(node16.item(0).getTextContent());
			//			if (!"".equals(node16.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 촬영자
			//			NodeList node17 = root.getElementsByTagName("cmr_drt_nm");
			//			searchDO.setCmr_drt_nm(node17.item(0).getTextContent());
			//			if (!"".equals(node17.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 작가
			//			NodeList node18 = root.getElementsByTagName("writer_nm");
			//			searchDO.setWriter_nm(node18.item(0).getTextContent());
			//			if (!"".equals(node18.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 연출자
			//			NodeList node19 = root.getElementsByTagName("drt_nm");
			//			searchDO.setDrt_nm(node19.item(0).getTextContent());
			//			if (!"".equals(node19.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 특이사항
			//			NodeList node20 = root.getElementsByTagName("spc_info");
			//			searchDO.setSpc_info(node20.item(0).getTextContent());
			//			if (!"".equals(node20.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 프로듀서명
			//			NodeList node21 = root.getElementsByTagName("producer_nm");
			//			searchDO.setProducer_nm(node21.item(0).getTextContent());
			//			if (!"".equals(node21.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 저작권 형태 설명
			//			NodeList node22 = root.getElementsByTagName("cprt_type_dsc");
			//			searchDO.setCprt_type_dsc(node22.item(0).getTextContent());
			//			if (!"".equals(node22.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 촬영장소
			//			NodeList node23 = root.getElementsByTagName("cmr_place");
			//			searchDO.setCmr_place(node23.item(0).getTextContent());
			//			if (!"".equals(node23.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 제작부서
			//			NodeList node24 = root.getElementsByTagName("prdt_dept_nm");
			//			searchDO.setPrdt_dept_nm(node24.item(0).getTextContent());
			//			if (!"".equals(node24.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 편성명
			//			NodeList node25 = root.getElementsByTagName("schd_pgm_nm");
			//			searchDO.setSchd_pgm_nm(node25.item(0).getTextContent());
			//			if (!"".equals(node25.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 부제
			//			NodeList node26 = root.getElementsByTagName("sub_ttl");
			//			searchDO.setSub_ttl(node26.item(0).getTextContent());
			//			if (!"".equals(node26.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 출연자명
			//			NodeList node27 = root.getElementsByTagName("cast_nm");
			//			searchDO.setCast_nm(node27.item(0).getTextContent());
			//			if (!"".equals(node27.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 아카이버 명
			//			NodeList node28 = root.getElementsByTagName("sec_arch_nm");
			//			searchDO.setSec_arch_nm(node28.item(0).getTextContent());
			//			if (!"".equals(node28.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 음악정보
			//			NodeList node29 = root.getElementsByTagName("music_info");
			//			searchDO.setMusic_info(node29.item(0).getTextContent());
			//			if (!"".equals(node29.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 수상내역
			//			NodeList node30 = root.getElementsByTagName("award_hstr");
			//			searchDO.setAward_hstr(node30.item(0).getTextContent());
			//			if (!"".equals(node30.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 시청등급
			//			NodeList node31 = root.getElementsByTagName("view_gr_cd");
			//			searchDO.setView_gr_cd(node31.item(0).getTextContent());
			//			if (!"".equals(node31.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 명장면 명대사
			//			NodeList node32 = root.getElementsByTagName("cont");
			//			searchDO.setCont(node32.item(0).getTextContent());
			//			if (!"".equals(node32.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 코너제목
			//			NodeList node33 = root.getElementsByTagName("cn_nm");
			//			searchDO.setCn_nm(node33.item(0).getTextContent());
			//			if (!"".equals(node33.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 심의등급
			//			NodeList node34 = root.getElementsByTagName("dlbr_cd");
			//			searchDO.setDlbr_cd(node34.item(0).getTextContent());
			//			if (!"".equals(node34.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 저작권형태
			//			NodeList node35 = root.getElementsByTagName("cprt_type");
			//			searchDO.setCprt_type(node35.item(0).getTextContent());
			//			if (!"".equals(node35.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 저작권자명
			//			NodeList node36 = root.getElementsByTagName("cprtr_nm");
			//			searchDO.setCprtr_nm(node36.item(0).getTextContent());
			//			if (!"".equals(node36.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 영상 ID
			//			NodeList node37 = root.getElementsByTagName("ct_id");
			//			searchDO.setCt_id(node37.item(0).getTextContent());
			//			if (!"".equals(node37.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 시청률
			//			NodeList node38 = root.getElementsByTagName("pgm_rate");
			//			searchDO.setPgm_rate(node38.item(0).getTextContent());
			//			if (!"".equals(node38.item(0).getTextContent())) {
			//				count++;
			//			}
			//			// 제작구분
			//			NodeList node39 = root.getElementsByTagName("prdt_in_outs_cd");
			//			searchDO.setPrdt_in_outs_cd(node39.item(0).getTextContent());
			//			if (!"".equals(node39.item(0).getTextContent())) {
			//				count++;
			//			}
			list = workDAO.selectSearchList(searchDO);

			if (list.size() != 0) {
				result.append("<?xml version='1.0' encoding='UTF-8'?>\n");
				result.append("<data>\n");

				for(int i=0; i<list.size()-1; i++) {
					SearchDO searchDO2 = new SearchDO();
					searchDO2 = (SearchDO)list.get(i);
					result.append("<list>\n");
					result.append("<vd_qlty>" + searchDO2.getVd_qlty() + "</vd_qlty>\n");
					result.append("<asp_rto_cd>" + searchDO2.getAsp_rto_cd() + "</asp_rto_cd>\n");
					result.append("<cn_id>" + searchDO2.getCn_id() + "</cn_id>\n");
					result.append("<annot_id>" + searchDO2.getAnnot_id() + "</annot_id>\n");
					result.append("<rp_img>" + searchDO2.getRp_img() + "</rp_img>\n");
					result.append("<reg_dt>" + searchDO2.getReg_dt() + "</reg_dt>\n");
					result.append("<pgm_id>" + searchDO2.getPgm_id() + "</pgm_id>\n");
					result.append("<master_id>" + searchDO2.getMaster_id() + "</master_id>\n");
					result.append("<kfrm_seq>" + searchDO2.getKfrm_seq() + "</kfrm_seq>\n");
					result.append("<kfrm_path>" + searchDO2.getKfrm_path() + "</kfrm_path>\n");
					result.append("<brd_dd>" + searchDO2.getBrd_dd() + "</brd_dd>\n");
					result.append("<brd_leng>" + searchDO2.getBrd_leng() + "</brd_leng>\n");
					result.append("<ctgr_l_cd>" + searchDO2.getCtgr_l_cd() + "</ctgr_l_cd>\n");
					result.append("<ctgr_s_cd>" + searchDO2.getCtgr_s_cd() + "</ctgr_s_cd>\n");
					result.append("<ctgr_m_cd>" + searchDO2.getCtgr_m_cd() + "</ctgr_m_cd>\n");
					result.append("<mc_nm>" + searchDO2.getMc_nm() + "</mc_nm>\n");
					result.append("<cmr_drt_nm>" + searchDO2.getCmr_drt_nm() + "</cmr_drt_nm>\n");
					result.append("<writer_nm>" + searchDO2.getWriter_nm() + "</writer_nm>\n");
					result.append("<drt_nm>" + searchDO2.getDrt_nm() + "</drt_nm>\n");
					result.append("<annot_clf_cd>" + searchDO2.getAnnot_clf_cd() + "</annot_clf_cd>\n");
					result.append("<pilot_yn>" + searchDO2.getPilot_yn() + "</pilot_yn>\n");
					result.append("<final_brd_yn>" + searchDO2.getFinal_brd_yn() + "</final_brd_yn>\n");
					result.append("<day>" + searchDO2.getDay() + "</day>\n");
					result.append("<pgm_nm>" + searchDO2.getPgm_nm() + "</pgm_nm>\n");
					result.append("<cn_info>" + searchDO2.getCn_info() + "</cn_info>\n");
					result.append("<title>" + searchDO2.getTitle() + "</title>\n");
					result.append("<cn_nm>" + searchDO2.getCn_nm() + "</cn_nm>\n");
					result.append("<sub_ttl>" + searchDO2.getSub_ttl() + "</sub_ttl>\n");
					result.append("<epis_no>" + searchDO2.getEpis_no() + "</epis_no>\n");
					result.append("<schd_pgm_nm>" + searchDO2.getSchd_pgm_nm() + "</schd_pgm_nm>\n");
					result.append("<snps>" + searchDO2.getSnps() + "</snps>\n");
					result.append("</list>\n\n");
				}
				result.append("<count>" + list.get(list.size()-1)  + "</count>\n");
				result.append("</data>\n");
			}
			return result.toString();
		}
		
		
		catch (Exception e) 
		{
			throw e;
		}
	}
	/**
	 * 검색어를 받아 검색된 목록을 리턴한다. 
	 * @param String xml형식으로 된 String을 받아 분석하여 검색을 실행
	 * @return String xml형식으로 된 목록을  돌려준다.
	 */
	private void printNodeName(NodeList nodeList, SearchDO searchDO) {

		Node node     = null;
		String name   = "";
		for(int i = 0; i < nodeList.getLength(); i++) {
			node  = nodeList.item(i);

			if(! node.getNodeName().startsWith("#")) { 
				logger.debug(node.getNodeName() + ": " + node.getFirstChild());

				try {
					name   = node.getNodeName();
					searchDO.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), 
							new Class[] { String.class } ).invoke(searchDO, new Object[] { node.getFirstChild().toString().substring(8, node.getFirstChild().toString().length()-1) });

				} catch (Exception e) {
					//					e.printStackTrace();
				}
			}
			if(node.hasChildNodes()) {
				printNodeName(node.getChildNodes(), searchDO);
			}
		}
	}


	/**
	 *  상태정보를 가져온다.
	 * @param masterId
	 * @return MetadataMstInfoDO  목록을  돌려준다.
	 * @throws Exception 
	 */
	public MetadataMstInfoDO selectModDatastatcd(long masterId) throws Exception
	{
		return externalDAO.selectModDatastatcd(masterId);
	}


	/**
	 *  상태정보를 업데이트한다.
	 * @param masterId 마스터id
	 * @param statCd 상태코드
	 * @param modrid 수정자id
	 * @param moddt 수정일
	 * @param lock_stat_cd lock여부
	 * @return MetadataMstInfoDO  목록을  돌려준다.
	 * @throws Exception 
	 */
	public void updateMetadataStatusCd(long masterId, String statCd, String modrid, String moddt, String lock_stat_cd) throws Exception
	{
		String moddt2 = moddt;
		if (moddt.length() <= 0)
			moddt2 = CalendarUtil.getDateTime("yyyyMMddHHmmss");
		externalDAO.updateMetadataStatusCd(masterId, statCd, modrid, moddt2, lock_stat_cd, "");
	}


	/**
	 * 선택된 카트 상세 목록중 제한이 걸려있는 목록 주석.
	 * @param ct_id 콘텐츠 아이디
	 * @param s_frame 시작 프레임
	 * @param duration 길이
	 * @param commonDO 공통정보
	 * @return List CartDetailItemDO를 포함하고 있는 DO
	 * @throws Exception 
	 */
	public String getAnnot_App_Cont(String ct_id, String s_frame, String duration, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[getAnnot_App_Cont][Input ct_id, s_frame, duration]" + ct_id + " , " + s_frame + " , " + duration);
		}

		try 
		{
			return workDAO.getAnnot_App_Cont(ct_id, s_frame, duration, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}


	/**
	 *  상태정보를 가져온다.
	 * @param UserID 사용자 id
	 * 
	 * @return MetadataMstInfoDO  목록을  돌려준다.
	 */
	public String getUserAuthCD(String UserID) throws DASException
	{
		return externalDAO.getUserAuthCD(UserID);
	}


	/**
	 * 요청인별 다운로드 승인요청 목록조회를 한다.
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getDownloadRequestList(CartItemDO cartItemDO) throws Exception
	{
		if(logger.isDebugEnabled())
			logger.debug("[getDownloadRequestList][Input CartItemDO]" + cartItemDO);

		try  
		{
			// 해당 사용자ID가 데정팀인지 확인한다.
			if(workDAO.isHeUser(cartItemDO.getUserid())){
				return workDAO.selectDownloadRequestList(cartItemDO);
			}else{
				/** 데정팀이 아니라면 cp인지 pd인지 판단한다.*/
				/*
				List _result = workDAO.isCPyn(cartItemDO.getUserid());
				String pgmId_grp = "0"; // 프로그램 그룹코드(ex: 1123213,1231231,123123,.....) 
				if(_result!=null&&_result.size()!=0){
					Iterator _iter = _result.iterator();
					while(_iter.hasNext()){
						logger.debug("##"+_result.size());
						PdaInfoDO item = new PdaInfoDO();
						item = (PdaInfoDO)_iter.next();
						logger.debug("item.getCp_yn()"+item.getCp_yn()+"|"+"item.getBrd_end_yn()"+item.getBrd_end_yn());
						if(item.getCp_yn().equals("Y")&&item.getBrd_end_yn().equals("Y")){
							pgmId_grp += ","+item.getDas_pgm_id();
							logger.debug("------------------------------[cp][pgmId_grp]  "+pgmId_grp);
						}else if(item.getCp_yn().equals("N")&&item.getBrd_end_yn().equals("N")){

							pgmId_grp += ","+item.getDas_pgm_id();
							logger.debug("------------------------------[pd][pgmId_grp]  "+pgmId_grp);
						}
					}
				}
				 */
				//데정팀이 아니라면 사번기준으로 승인프로그램을 조회한다.
				List _result = workDAO.isRightUser(cartItemDO.getUserid());
				String pgmId_grp = "'0'";
				if(_result!=null&&_result.size()!=0){
					Iterator _iter = _result.iterator();
					while(_iter.hasNext()){
						PdaInfoDO item = new PdaInfoDO();
						item = (PdaInfoDO)_iter.next();
						pgmId_grp += ","+"'"+item.getProgramid()+"'";

					}
				}
				return workDAO.selectDownloadRequestForUser(pgmId_grp,cartItemDO);
			}
		} 
		catch (Exception e)
		{

			throw e;
		}

	}


	/**
	 * 요청인별 다운로드 승인요청 목록조회에서 특정 카트 선택시 해당하는 파일정보를 목록 조회한다.
	 * @param cartNo
	 * @return
	 * @throws Exception 
	 */
	public List getDownloadRequestDetailsList(String cartNo, String user_id) throws Exception
	{
		try 
		{

			// 해당 사용자ID가 데정팀인지 확인한다.
			if(workDAO.isHeUser(user_id)){
				return workDAO.selectDownloadRequestDetailsList(cartNo);
			}else {
				/** 데정팀이 아니라면 cp인지 pd인지 판단한다.*/
				/*	List _result = workDAO.isCPyn(user_id);
				String pgmId_grp = "0"; // 프로그램 그룹코드(ex: 1123213,1231231,123123,.....) 
				if(_result!=null&&_result.size()!=0){
					Iterator _iter = _result.iterator();
					while(_iter.hasNext()){
						logger.debug("##"+_result.size());
						PdaInfoDO item = new PdaInfoDO();
						item = (PdaInfoDO)_iter.next();
						logger.debug("item.getCp_yn()"+item.getCp_yn()+"|"+"item.getBrd_end_yn()"+item.getBrd_end_yn());
						if(item.getCp_yn().equals("Y")&&item.getBrd_end_yn().equals("Y")){

							pgmId_grp += ","+item.getDas_pgm_id();
							logger.debug("------------------------------[cp][pgmId_grp]  "+pgmId_grp);
						}else if(item.getCp_yn().equals("N")&&item.getBrd_end_yn().equals("N")){

							pgmId_grp += ","+item.getDas_pgm_id();
							logger.debug("------------------------------[pd][pgmId_grp]  "+pgmId_grp);
						}
					}*/
				List _result = workDAO.isRightUser(user_id);
				String pgmId_grp = "'0'";
				if(_result!=null&&_result.size()!=0){
					Iterator _iter = _result.iterator();
					while(_iter.hasNext()){
						PdaInfoDO item = new PdaInfoDO();
						item = (PdaInfoDO)_iter.next();
						pgmId_grp += ","+"'"+item.getProgramid()+"'";

					}
				}
				return workDAO.selectDownloadRequestDetailsForUserId(cartNo,pgmId_grp);
			} 
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public void downloadRequestList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[approveCartList][Input cartItemDOList]" + cartItemDOList);
		}

		try 
		{
			workDAO.updateCartApproveList(cartItemDOList, commonDO);
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}  

	/**
	 * 다운로드 승인요청 목록의 내용을 승인 처리전 사용제한이 걸려 있는지 확인 한다.
	 * @param cartItemDOList CartItemDO 를 포함하고 있는 List 
	 * @param commonDO 공통정보
	 * @throws Exception 
	 */
	public boolean downloadRequestCheckList(List cartItemDOList, DASCommonDO commonDO) throws Exception
	{
		boolean sResult = true;
		if(logger.isDebugEnabled())
		{
			logger.debug("[checkCartList][Input cartItemDOList]" + cartItemDOList);
		}

		try 
		{
			sResult = workDAO.checkCartApproveList(cartItemDOList, commonDO);
			return sResult;
		} 
		catch (Exception e) 
		{
			throw e;
		}
	}

	/**
	 *  요청건별 상세내역에서 선택된 목록을 수정(승인 및 승인내용)한다.
	 * @param _do
	 * @throws Exception 
	 */
	public int updateDownloadRequestDetail(CartContDO 	_do) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDownloadRequestDetail][Input CartContDO ]" + _do.getCartNo() +"|"+ _do.getCartSeq());
		}

		try 
		{
			CartContDO cart2 = workDAO.selectOutSourcingInfo(_do.getCartNo(),_do.getCartSeq());
			String approve_yn =workDAO.isApproveYn(_do.getCartNo(),_do.getCartSeq());
			if(approve_yn.equals("002")||approve_yn.equals("006")||approve_yn.equals("007")){
				return -1;
			}
			if(externalDAO.isDownloadOutsourcing2(_do.getRegrId())&&(cart2.getOutsourcing_yn().equals("Y")&&cart2.getOutsourcing_approve().trim().equals(""))||(cart2.getOutsourcing_yn().equals("Y")&&cart2.getOutsourcing_approve().trim().equals("")))
			{		
				return workDAO.updateDownloadRequestOutsourcingDetailList2(_do);


			}else{
				CartContDO cart = workDAO.selectOutSourcingInfo(_do.getCartNo(),_do.getCartSeq());
				if(workDAO.isHeUser(_do.getRegrId())){
					return  workDAO.updateDownloadRequestDetailList(_do);

				}else 	if(cart.getOutsourcing_yn().equals("N")||cart.getDown_stat().equals("005")){
					return workDAO.updateDownloadRequestDetailList(_do);

				}
				return 0;
			}

		} 
		catch (Exception e)
		{
			
			throw e;
		}
	}

	/**
	 * 다운로드 요청건에 대해서 외주제작 승인을 수정한다.( 외주제작팀 소속 비직원인 경우: 1차.제작PD 승인 ,2차.외주제작팀직원 승인) 
	 *
	 * @param cartContDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public int updateDownloadRequestOutSourcingDetail(CartContDO 	_do) throws Exception
	{
		if(logger.isDebugEnabled())
		{
			logger.debug("[updateDownloadRequestOutSourcingDetail][Input CartContDO ]" + _do.getCartNo() + _do.getCartSeq());
		}

		try 
		{
			return workDAO.updateDownloadRequestOutsourcingDetailList(_do);
		} 
		catch (Exception e)
		{

			throw e;
		}
	}

	/**
	 * My Page 다운로드 목록 조회
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getMyDownloadRequestList(CartItemDO cartItemDO) throws Exception
	{
		if(logger.isDebugEnabled())
			logger.debug("[getMyDownloadRequestList][Input CartItemDO]" + cartItemDO);


		try 
		{

			return workDAO.selectMyDownloadRequest(cartItemDO);

		} 
		catch (Exception e)
		{
			throw e;
		}


	}




	/**
	 * My Page 다운로드 상세 조회
	 * @param commonDO 공통정보
	 * @return List CartItemDO를 포함하고 있는 List
	 * @throws Exception 
	 */
	public List getMyDownloadRequestDetailsList(String cartNo, String user_id) throws Exception
	{
		try 
		{


			return workDAO.selectMyDownloadRequestDetailsList(cartNo,  user_id);

		}
		catch (Exception e)
		{
			throw e;
		}
	}





	/**
	 * My Sign 다운로드 승인조회
	 * @param commonDO
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getMyDownloadAprroveList(CartItemDO cartItemDO) throws Exception
	{
		if(logger.isDebugEnabled())
			logger.debug("[getMyDownloadAprroveList][Input CartItemDO]" + cartItemDO);


		try 
		{
			String outsorcing_yn = externalDAO.getOutSourcing_ynEmployee(cartItemDO.getUserid());
			String ifcms_yn = externalDAO.getIfCmsApproveYn(cartItemDO.getUserid());
			if(outsorcing_yn.equals("Y")){
				if(cartItemDO.getSystem().equals("")){
					return workDAO.selectMyDownloadAprroveForOutSourcingList(cartItemDO);	
				}else{
					return workDAO.selectMyDownloadAprroveForOutSourcingListForIfCms(cartItemDO);		
				}
			}else{

				if(ifcms_yn.equals("Y")||cartItemDO.getUserid().equals("admin")){

					return workDAO.selectMyDownloadAprroveListForIfCms(cartItemDO);
				}else {
					String dep_cd= userRoleDAO.selectDepinfoForUser(cartItemDO.getUserid());
					if(cartItemDO.getSystem().equals("")){
						return workDAO.selectMyDownloadAprroveList2(cartItemDO,dep_cd);
					}else{
						return workDAO.selectMyDownloadAprroveListForIfCms(cartItemDO,dep_cd);	
					}
				}
			}
		} 
		catch (Exception e)
		{
			throw e;
		}


	}




	/**
	 * My Sign 다운로드 승인 상세조회
	 * @param commonDO 
	 * @return
	 * @throws Exception 
	 * @throws RemoteException
	 */
	public List getMyDownloadAprroveDetailList(String cartNo, String user_id) throws Exception
	{
		try 
		{

			String dep_cd= userRoleDAO.selectDepinfoForUser(user_id);
			String ifcms_yn = externalDAO.getIfCmsApproveYn(user_id);
			if(dep_cd.equals("D3JA01")){
				return workDAO.selectMyDownloadAprroveDetailListForOutsosing(cartNo,  user_id);
			}else if(dep_cd.equals("D3OB01")){
				return workDAO.selectMyDownloadAprroveDetailListForArchive(cartNo,  user_id);
			}
			else if(ifcms_yn.equals("Y")){
				return workDAO.selectMyDownloadAprroveDetailListForArchive(cartNo,  user_id);
			}
			else {
				return workDAO.selectMyDownloadAprroveDetailList(cartNo,  user_id);
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}

}

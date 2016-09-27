package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PreProcessingDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;

/**
 *  NLE DROG&DROP 정보 관련 XML파서
 * @author asura207
 *
 */
public class PreProcessingDOXML extends DOXml {
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "preProcessinginfo";
	/**
	 * 스토리지 구분코드 (0 : 전체, 1: 고해상도  2: 아카이브요청)##2012.5.14 일 이전버전
	 * 스토리지 구분코드 (0 : 전체, 1: SBS 방송  2: SBS 방송 3:미디어넷 방송 4:미디어넷 비방송 5:다운로드)##2012.5.14 일  이후 버전
	 */
	private String XML_NODE_ST_GUBUN = "st_gubun";    // 
	/**
	 * 콘시작일텐츠 구분
	 */
	private String XML_NODE_CT_CLA = "ct_cla";        // 
	/**
	 * 시작일
	 */
	private String XML_NODE_FROMDATE = "fromDate";    // 
	/**
	 * 종료일
	 */
	private String XML_NODE_TODATE = "toDate";        // 
	/**
	 * 검색어
	 */
	private String XML_NODE_SEARCHKEY = "searchKey";  // 
	/**
	 *  대분류명
	 */
	private String XML_NODE_CTGR_NM = "ctgr_nm";      //
	/**
	 * 프로그램명	
	 */
	private String XML_NODE_PGM_NM = "pgm_nm";		// 		
	/**
	 * 미디어 ID
	 */
	private String XML_NODE_MEDIA_ID = "media_id";    // 
	/**
	 * 콘텐츠 구분 명
	 */
	private String XML_NODE_CT_CLA_NM = "ct_cla_nm";	// 
	/**
	 * 요청일 
	 */
	private String XML_NODE_REQ_DT = "req_dt";		// 
	/**
	 * 요청자명
	 */
	private String XML_NODE_REQ_NM = "req_nm";			// 
	/**
	 * 파일 풀패스
	 */
	private String XML_NODE_FULLPATH = "fullpath";	// 
	/**
	 * 카트번호
	 */
	private String XML_NODE_CART_NO = "cart_no";			// 
	/**
	 * 카트순번
	 */
	private String XML_NODE_CART_SEQ = "cart_seq";	// 
	/**
	 * 카트순번
	 */
	private String XML_NODE_CT_ID = "ct_id";	// 
	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";	// 
	/**
	 * 프로그램 id
	 */
	private String XML_NODE_PGM_ID = "pgm_id";	// 마스터id
	/**
	 * 구분
	 */
	private String XML_NODE_GUBUN = "gubun";	// 
	/**
	 * 편집 구분 ( 001 편집중, 002 편집완료)
	 */
	private String XML_NODE_EDTRID = "edtrid";  // 
	/**
	 * 정리 상태
	 */
	private String XML_NODE_DATA_STAT_CD = "data_stat_cd";  // 

	/**
	 * 정리 상태
	 */
	private String XML_NODE_TC_YN = "tc_yn";  // 


	/**
	 * 채널
	 */
	private String XML_NODE_CHENNEL = "chennel";  // 

	public Object setDO(String _xml) {
		setDO(new PreProcessingDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
		}

		return getDO();
	}

	public Object setData(Element pElement) {
		PreProcessingDO	preProcessingDO= (PreProcessingDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_ST_GUBUN)) {
				preProcessingDO.setSt_gubun(_nodeValue);

			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_CLA)) {
				preProcessingDO.setCt_cla(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TODATE)) {
				preProcessingDO.setToDate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FROMDATE)) {
				preProcessingDO.setFromDate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEARCHKEY)) {
				preProcessingDO.setSearchKey(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_NM)) {
				preProcessingDO.setCtgr_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_NM)) {
				preProcessingDO.setPgm_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MEDIA_ID)) {
				preProcessingDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_CLA_NM)) {
				preProcessingDO.setCt_cla_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_DT)) {
				preProcessingDO.setReq_dt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_NM)) {
				preProcessingDO.setReq_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FULLPATH)) {
				preProcessingDO.setFullpath(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CART_NO)) {
				preProcessingDO.setCart_no(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CART_SEQ)) {
				preProcessingDO.setCart_seq(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_ID)) {
				preProcessingDO.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GUBUN)) {
				preProcessingDO.setGubun(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DATA_STAT_CD)) {
				preProcessingDO.setData_stat_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHENNEL)) {
				preProcessingDO.setChennel(_nodeValue);
			}
		}

		return preProcessingDO;
	}

	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}


	public String getSubXML() {
		PreProcessingDO infoDO = (PreProcessingDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_ST_GUBUN + ">" + infoDO.getSt_gubun() + "</"  + XML_NODE_ST_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA + ">" + infoDO.getCt_cla() + "</"  + XML_NODE_CT_CLA + "> \n");
		_xml.append("<" + XML_NODE_FROMDATE + ">" + infoDO.getFromDate() + "</"  + XML_NODE_FROMDATE + "> \n");
		_xml.append("<" + XML_NODE_TODATE + ">" + infoDO.getToDate() + "</"  + XML_NODE_TODATE + "> \n");
		_xml.append("<" + XML_NODE_SEARCHKEY + ">" + infoDO.getSearchKey() + "</"  + XML_NODE_SEARCHKEY + "> \n");
		_xml.append("<" + XML_NODE_CTGR_NM + ">" + CommonUtl.transXmlText(infoDO.getCtgr_nm()) + "</"  + XML_NODE_CTGR_NM + "> \n");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(infoDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA_NM + ">" + CommonUtl.transXmlText(infoDO.getCt_cla_nm()) + "</"  + XML_NODE_CT_CLA_NM + "> \n");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DT + "> \n");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + "> \n");
		String hr = infoDO.getFullpath();
		if(hr.matches(".*arcreq.*")){
			hr = hr.replaceAll("/"+dasHandler.getProperty("WINARCREQ"), dasHandler.getProperty("ARCREQ"));
			hr = hr.replaceAll(dasHandler.getProperty("WINARCREQ"), dasHandler.getProperty("ARCREQ"));

			infoDO.setFullpath(hr);
		}else if(hr.matches(".*mp2.*")){
			hr =hr.replaceAll("/"+dasHandler.getProperty("WINMP2"),dasHandler.getProperty("MP2"));
			hr =hr.replaceAll(dasHandler.getProperty("WINMP2"),dasHandler.getProperty("MP2"));

			infoDO.setFullpath(hr);
		}else if(hr.matches(".*nearline.*")){
			hr =hr.replaceAll("/nearline","X:");
			hr =hr.replaceAll("nearline","X:");

			infoDO.setFullpath(hr);
		}else{

		}
		_xml.append("<" + XML_NODE_FULLPATH + ">" + infoDO.getFullpath().replaceAll("//", "/") + "</"  + XML_NODE_FULLPATH + "> \n");
		_xml.append("<" + XML_NODE_CART_NO + ">" + infoDO.getCart_no() + "</"  + XML_NODE_CART_NO + "> \n");
		_xml.append("<" + XML_NODE_CART_SEQ + ">" + infoDO.getCart_seq() + "</"  + XML_NODE_CART_SEQ + "> \n");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_PGM_ID + ">" + infoDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_EDTRID + ">" + infoDO.getEdtrid() + "</"  + XML_NODE_EDTRID + "> \n");
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_DATA_STAT_CD + ">" + infoDO.getData_stat_cd() + "</"  + XML_NODE_DATA_STAT_CD + "> \n");
		_xml.append("<" + XML_NODE_TC_YN + ">" + infoDO.getTc_yn() + "</"  + XML_NODE_TC_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + "> \n");

		return _xml.toString();
	}

}

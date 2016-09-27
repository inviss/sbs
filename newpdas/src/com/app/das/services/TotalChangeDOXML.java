package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TotalChangeInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  일괄수정  정보 관련 XML파서
 * @author asura207
 *
 */
public class TotalChangeDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "totalchangeinfo";
	/**
	 * 수정대상 구분
	 */
	private String XML_NODE_GUBUN = "gubun";          // 수정대상 구분
	/**
	 * 보존기간 코드
	 */
	private String XML_NODE_RSV_PRD_CD= "rsv_prd_cd"; // 보존기간 코드
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd"; // 대분류
	/**
	 * 중분류
	 */
	private String XML_NODE_CTGR_M_CD = "ctgr_m_cd"; // 중분류
	/**
	 *  소분류
	 */
	private String XML_NODE_CTGR_S_CD = "ctgr_s_cd"; // 소분류
	/**
	 *  제작구분 코드
	 */
	private String XML_NODE_PRDT_IN_OUTS_CD = "prdt_in_outs_cd"; // 제작구분 코드
	/**
	 * 제작부서코드
	 */
	private String XML_NODE_PRDT_DEPT_CD = "prdt_dept_cd";  // 제작부서코드
	/**
	 * 제작부서명
	 */
	private String XML_NODE_PRDT_DEPT_NM = "prdt_dept_nm";  // 제작부서명
	/**
	 * 원제작사명
	 */
	private String XML_NODE_ORG_PRDR_NM = "org_prdr_nm";  // 원제작사명
	/**
	 *  촬영장소
	 */
	private String XML_NODE_CMR_PLACE = "cmr_place";     // 촬영장소
	/**
	 * 저작권 형태 코드
	 */
	private String XML_NODE_CPRT_TYPE = "cprt_type";     // 저작권 형태 코드
	/**
	 * 저작권 형태 설명
	 */
	private String XML_NODE_CPRT_TYPE_DSC = "cprt_type_dsc"; // 저작권 형태 설명
	/**
	 * 저작권자
	 */
	private String XML_NODE_CPRTR_NM = "cprtr_nm";      // 저작권자
	/**
	 * 녹음방식 코드
	 */
	private String XML_NODE_RECORD_TYPE_CD ="record_type_cd";  // 녹음방식 코드
	/**
	 * 단일 마스터 아이디
	 */
	private String XML_NODE_MASTERID = "masterId";             // 단일 마스터 아이디
	/**
	 * 마스터 아이디 그룹
	 */
	private String XML_NODE_MASTERIDGRP = "masterIdGrp";       // 마스터 아이디 그룹
	/**
	 * 수상내역
	 */
	private String XML_NODE_AWARD_HSTR = "award_hstr";       // 수상내역
	/**
	 * 녹음방식
	 */
	private String XML_NODE_RECORD_TYPE_CODE = "record_type_code";       // 녹음방식
	/**
	 * 장르
	 */
	private String XML_NODE_CTGR = "ctgr";

	public Object setDO(String _xml) {
		setDO(new TotalChangeInfoDO());

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
		TotalChangeInfoDO  totalchangeDO = (TotalChangeInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_GUBUN)) {
				totalchangeDO.setGubun(_nodeValue);

			}else if(_nodeName.equalsIgnoreCase(XML_NODE_RSV_PRD_CD)) {
				totalchangeDO.setRsv_prd_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_CD)) {
				totalchangeDO.setCtgr_l_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_M_CD)) {
				totalchangeDO.setCtgr_m_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_S_CD)) {
				totalchangeDO.setCtgr_s_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_IN_OUTS_CD)) {
				totalchangeDO.setPrdt_in_outs_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_DEPT_CD)) {
				totalchangeDO.setPrdt_dept_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ORG_PRDR_NM)) {
				totalchangeDO.setOrg_prdr_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CMR_PLACE)) {
				totalchangeDO.setCmr_place(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRT_TYPE)) {
				totalchangeDO.setCprt_type(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRT_TYPE_DSC)) {

				totalchangeDO.setCprt_type_dsc(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CPRTR_NM)) {

				totalchangeDO.setCprtr_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_RECORD_TYPE_CD)) {

				totalchangeDO.setRecord_type_cd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_MASTERID)){

				totalchangeDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_MASTERIDGRP)){

				totalchangeDO.setMasterIdGrp(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR)){

				totalchangeDO.setCtgr(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_PRDT_DEPT_NM)){

				totalchangeDO.setPrdt_dept_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_AWARD_HSTR)){

				totalchangeDO.setAward_hstr(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_RECORD_TYPE_CODE)){

				totalchangeDO.setRecord_type_cd(_nodeValue);
			}

		}

		return totalchangeDO;
	}

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}


	public String getSubXML() {
		TotalChangeInfoDO totalchangeDO = (TotalChangeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");	

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ContentsInfoDO;
import com.app.das.util.CommonUtl;

/**
 * CONTENTS_INFO_tBL  정보 관련 XML파서
 * @author asura207
 *
 */
public class ContentsInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "contentsInfo";
	/**
	 * 프로듀서명
	 */
	private String XML_NODE_PRODUCERNAME = "PRODUCER_NM";
	/**
	 * 연출자명
	 */
	private String XML_NODE_DEPARTMENTNAME = "DRT_NM";
	/**
	 * 작가명
	 */
	private String XML_NODE_WRITERNAME = "WRITER_NM";
	/**
	 * 촬영감독명
	 */
	private String XML_NODE_CAMERADIRECTORNAME = "CMR_DRT_NM"; 
	/**
	 * 제작부서명칭
	 */
	private String XML_NODE_PRODUCEDEPTNAME = "PRDT_DEPT_NM";
	/**
	 * 원제작사명
	 */
	private String XML_NODE_ORIGINALPRODUCERNAME = "ORG_PRDR_NM";
	/**
	 * 제작구분코드
	 */
	private String XML_NODE_PRODUCTINOUTCODE = "PRDT_IN_OUTS_CD";
	/**
	 * 스놉시스
	 */
	private String XML_NODE_SYNAPSIS = "SNPS";
	/**
	 * 키워드
	 */
	private String XML_NODE_KEYWORDS = "KEY_WORDS";
	/**
	 * 출연자명
	 */
	private String XML_NODE_CASTNAME = "CAST_NM";
	/**
	 * 특이사항
	 */
	private String XML_NODE_SPCINFO = "SPC_INFO";

	public Object setDO(String _xml) {
		setDO(new ContentsInfoDO());
		
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
		ContentsInfoDO infoDO = (ContentsInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PRODUCERNAME)) {
				infoDO.setProducerNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPARTMENTNAME)) {
				infoDO.setDrtNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WRITERNAME)) {
				infoDO.setWriterNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CAMERADIRECTORNAME)) {
				infoDO.setCmrDrtNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRODUCEDEPTNAME)) {
				infoDO.setPrdtDeptNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ORIGINALPRODUCERNAME)) {
				infoDO.setOrgPrdrNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRODUCTINOUTCODE)) {
				infoDO.setPrdtInOutsCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SYNAPSIS)) {
				infoDO.setSnps(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_KEYWORDS)) {
				infoDO.setKeyWords(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CASTNAME)) {
				infoDO.setCastNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SPCINFO)) {
				infoDO.setSpcInfo(_nodeValue);
			}
        }
	    
	    return infoDO;
	}
	
	
	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");
		
		return _xml.toString();
	}

	public String getSubXML() {
		ContentsInfoDO infoDO = (ContentsInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PRODUCERNAME + ">" + CommonUtl.transXmlText(infoDO.getProducerNm()) + "</"  + XML_NODE_PRODUCERNAME + ">");
		_xml.append("<" + XML_NODE_DEPARTMENTNAME + ">" + CommonUtl.transXmlText(infoDO.getDrtNm()) + "</"  + XML_NODE_DEPARTMENTNAME + ">");
		_xml.append("<" + XML_NODE_WRITERNAME + ">" + CommonUtl.transXmlText(infoDO.getWriterNm()) + "</"  + XML_NODE_WRITERNAME + ">");
		_xml.append("<" + XML_NODE_CAMERADIRECTORNAME + ">" + CommonUtl.transXmlText(infoDO.getCmrDrtNm()) + "</"  + XML_NODE_CAMERADIRECTORNAME + ">");
		_xml.append("<" + XML_NODE_PRODUCEDEPTNAME + ">" + CommonUtl.transXmlText(infoDO.getPrdtDeptNm()) + "</"  + XML_NODE_PRODUCEDEPTNAME + ">");
		_xml.append("<" + XML_NODE_ORIGINALPRODUCERNAME + ">" + CommonUtl.transXmlText(infoDO.getOrgPrdrNm()) + "</"  + XML_NODE_ORIGINALPRODUCERNAME + ">");
		_xml.append("<" + XML_NODE_PRODUCTINOUTCODE + ">" + CommonUtl.transXmlText(infoDO.getPrdtInOutsCd()) + "</"  + XML_NODE_PRODUCTINOUTCODE + ">");
		_xml.append("<" + XML_NODE_SYNAPSIS + ">" + CommonUtl.transXmlText(infoDO.getSnps()) + "</"  + XML_NODE_SYNAPSIS + ">");
		_xml.append("<" + XML_NODE_KEYWORDS + ">" + CommonUtl.transXmlText(infoDO.getKeyWords()) + "</"  + XML_NODE_KEYWORDS + ">");
		_xml.append("<" + XML_NODE_CASTNAME + ">" + CommonUtl.transXmlText(infoDO.getCastNm()) + "</"  + XML_NODE_CASTNAME + ">");
		_xml.append("<" + XML_NODE_SPCINFO + ">" + CommonUtl.transXmlText(infoDO.getSpcInfo()) + "</"  + XML_NODE_SPCINFO + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
}

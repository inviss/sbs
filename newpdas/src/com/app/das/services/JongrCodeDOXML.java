package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.util.CommonUtl;
/**
 *  장르 코드 정보 관련 XML파서
 * @author asura207
 *
 */
public class JongrCodeDOXML extends DOXml{
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "commonCodeInfo";
	/**
	 * 대분류 코드
	 */
	private String XML_NODE_BIG_CODE = "big_code";
	/**
	 * 대분류 코드설명
	 */
	private String XML_NODE_BIG_DESC = "big_desc";
	/**
	 * 대분류 코드명
	 */
	private String XML_NODE_BIG_CLF_NM = "big_clf_nm";
	/**
	 * 대분류 사용여부
	 */
	private String XML_NODE_BIG_USE_YN= "big_use_yn";
	/**
	 * 중분류 코드
	 */
	private String XML_NODE_MID_CODE = "mid_code";
	/**
	 * 중분류 코드설명
	 */
	private String XML_NODE_MID_DESC = "mid_desc";
	/**
	 * 중분류 코드명
	 */
	private String XML_NODE_MID_CLF_NM = "mid_clf_nm";
	/**
	 * 중분류 사용여부
	 */
	private String XML_NODE_MID_USE_YN= "mid_use_yn";
	/**
	 * 소분류 코드
	 */
	private String XML_NODE_SML_CODE = "sml_code";
	/**
	 * 소분류 코드설명
	 */
	private String XML_NODE_SML_DESC = "sml_desc";
	/**
	 * 소분류 코드명
	 */
	private String XML_NODE_SML_CLF_NM = "sml_clf_nm";
	/**
	 * 소분류 사용여부
	 */
	private String XML_NODE_SML_USE_YN= "sml_use_yn";
	/**
	 * 2차 구분코드 (예: 주석구분코드 ( 주제영상,사용제한등급)
	 */
	private String XML_NODE_GUNBUN = "gubun";
	/**
	 * 구분자 (001 전체검색, 002 대분류, 003 중분류, 004 소분류)
	 */
	private String XML_NODE_SEARCH_TYPE = "SEARCH_TYPE";

	public Object setDO(String _xml) {
		setDO(new CodeDO());

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
		CodeDO infoDO = (CodeDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_GUNBUN)) {
				infoDO.setGubun(_nodeValue);
			}else 
				if(_nodeName.equals( XML_NODE_SEARCH_TYPE)) {
					infoDO.setSearch_Type(_nodeValue);
				}

		}



		return infoDO;
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
		CodeDO infoDO = (CodeDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");

		_xml.append("<" + XML_NODE_BIG_CODE + ">" + infoDO.getBig_code() + "</"  + XML_NODE_BIG_CODE + "> \n");
		_xml.append("<" + XML_NODE_BIG_DESC + ">" + CommonUtl.transXmlText(infoDO.getBig_desc()) + "</"  + XML_NODE_BIG_DESC + "> \n");
		_xml.append("<" + XML_NODE_BIG_CLF_NM + ">" + CommonUtl.transXmlText(infoDO.getBig_nm()) + "</"  + XML_NODE_BIG_CLF_NM + "> \n");
		_xml.append("<" + XML_NODE_BIG_USE_YN + ">" + infoDO.getBig_use_yn() + "</"  + XML_NODE_BIG_USE_YN + "> \n");

		_xml.append("<" + XML_NODE_MID_CODE + ">" + infoDO.getMid_code() + "</"  + XML_NODE_MID_CODE + "> \n");
		_xml.append("<" + XML_NODE_MID_DESC + ">" + CommonUtl.transXmlText(infoDO.getMid_desc()) + "</"  + XML_NODE_MID_DESC + "> \n");
		_xml.append("<" + XML_NODE_MID_CLF_NM + ">" + CommonUtl.transXmlText(infoDO.getMid_nm()) + "</"  + XML_NODE_MID_CLF_NM + "> \n");
		_xml.append("<" + XML_NODE_MID_USE_YN + ">" + infoDO.getMid_use_yn() + "</"  + XML_NODE_MID_USE_YN + "> \n");

		_xml.append("<" + XML_NODE_SML_CODE + ">" + infoDO.getSml_code() + "</"  + XML_NODE_SML_CODE + "> \n");
		_xml.append("<" + XML_NODE_SML_DESC + ">" + CommonUtl.transXmlText(infoDO.getSml_desc()) + "</"  + XML_NODE_SML_DESC + "> \n");
		_xml.append("<" + XML_NODE_SML_CLF_NM + ">" + CommonUtl.transXmlText(infoDO.getSml_nm()) + "</"  + XML_NODE_SML_CLF_NM + "> \n");
		_xml.append("<" + XML_NODE_SML_USE_YN + ">" + infoDO.getSml_use_yn() + "</"  + XML_NODE_SML_USE_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.util.CommonUtl;

/**
 *  코드 정보 관련 XML파서
 * @author asura207
 *
 */
public class CodeInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "commonCodeInfo";
	/**
	 * 구분코드
	 */
	private String XML_NODE_CLFCD = "CLF_CD";
	/**
	 * 구분상세코드
	 */
	private String XML_NODE_SCLCD = "SCL_CD";
	/**
	 * 설명
	 */
	private String XML_NODE_DESCRIPTION = "DESC";
	/**
	 * 2차 구분코드 (예: 주석구분코드 ( 주제영상,사용제한등급)
	 */
	private String XML_NODE_GUBUN = "GUBUN";


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

			if(_nodeName.equals( XML_NODE_CLFCD)) {
				infoDO.setClfCd(_nodeValue);
			}

			else if(_nodeName.equals( XML_NODE_SCLCD)) {
				infoDO.setSclCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				infoDO.setDesc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
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
		_xml.append("<" + XML_NODE_CLFCD + ">" + infoDO.getClfCd() + "</"  + XML_NODE_CLFCD + "> \n");
		_xml.append("<" + XML_NODE_SCLCD + ">" + infoDO.getSclCd() + "</"  + XML_NODE_SCLCD + "> \n");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + "> \n");
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

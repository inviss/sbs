package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.util.CommonUtl;
/**
 * 인제스트 코드  정보 관련 XML파서
 * @author asura207
 *
 */
public class IngestCodeInfoDOXML extends DOXml{
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "commonCodeInfo";
	/**
	 * 구분상세코드
	 */
	private String XML_NODE_SCLCD = "SCL_CD";
	/**
	 * 설명
	 */

	private String XML_NODE_DESCRIPTION = "DESC";
	/**
	 * 구분명
	 */
	private String XML_NODE_CLASSIFICATIONNAME = "CLF_NM";
	/**
	 * 구분코드
	 */
	private String XML_NODE_CLASSIFICATIONCODE = "CLF_CD";

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

			if(_nodeName.equals( XML_NODE_SCLCD)) {
				infoDO.setSclCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				infoDO.setDesc(_nodeValue);
			}
			if(_nodeName.equals( XML_NODE_CLASSIFICATIONNAME)) {
				infoDO.setClfNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CLASSIFICATIONCODE)) {
				infoDO.setClfCd(_nodeValue);
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
		CodeDO infoDO = (CodeDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_SCLCD + ">" + infoDO.getSclCd() + "</"  + XML_NODE_SCLCD + ">");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + ">");
		_xml.append("<" + XML_NODE_CLASSIFICATIONNAME + ">" + CommonUtl.transXmlText(infoDO.getClfNm()) + "</"  + XML_NODE_CLASSIFICATIONNAME + ">");
		_xml.append("<" + XML_NODE_CLASSIFICATIONCODE + ">" + infoDO.getClfCd() + "</"  + XML_NODE_CLASSIFICATIONCODE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

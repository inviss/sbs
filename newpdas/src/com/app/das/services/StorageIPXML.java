package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.util.CommonUtl;
/**
 *   스토리지IP 정보 관련 XML파서
 * @author asura207
 *
 */
public class StorageIPXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "storageIP";
	/**
	 * 설명
	 */
	private String XML_NODE_DESCRIPTION = "desc";

	public Object setDO(String _xml) {
		setDO(new String(""));

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
		String desc = (String)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				desc = _nodeValue;
			}
		}

		return desc;
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
		String desc = (String)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(desc) + "</"  + XML_NODE_DESCRIPTION + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

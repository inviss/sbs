package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PathInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  파일 경로 정보 관련 XML파서
 * @author asura207
 *
 */
public class PathInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "filePathInfo";	
	/**
	 * 키프레임패스
	 */
	private String XML_NODE_KEYFRAMEPATH = "KFRM_PATH";
	/**
	 * 파일 패스
	 */
	private String XML_NODE_FILEPATH = "FL_PATH"; 


	public Object setDO(String _xml) {
		setDO(new PathInfoDO());

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
		PathInfoDO infoDO = (PathInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_KEYFRAMEPATH)) {
				infoDO.setKfrmPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILEPATH)) {
				infoDO.setFlPath(_nodeValue);
			}

		}

		return infoDO;
	}	    

	public String toXML() {

		PathInfoDO infoDO = (PathInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_KEYFRAMEPATH + ">" + infoDO.getKfrmPath() + "</"  + XML_NODE_KEYFRAMEPATH + ">");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + infoDO.getFlPath() + "</"  + XML_NODE_FILEPATH + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		_xml.append("</das>");
		
		return _xml.toString();
	}

}

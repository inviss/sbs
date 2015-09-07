package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 부모코드  정보 관련 XML파서
 * @author asura207
 *
 */
public class ParentsDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "programInfo";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID";	
	/**
	 * 프로그램 이름
	 */
	private String XML_NODE_PROGRAMNAME = "PGM_NM";
	/**
	 * 프로그램 코드
	 */
	private String XML_NODE_PGMCD = "PGM_CD";	
	
	public Object setDO(String _xml) {
		setDO(new ProgramInfoDO());
		
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
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
				infoDO.setPgmNm(_nodeValue);
			}
		
			else if(_nodeName.equals(XML_NODE_PGMCD)) {
				infoDO.setPgmCd(_nodeValue);
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
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		//_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + ">");
	
		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PROGRAMNAME + "> \n");
		
		_xml.append("<" + XML_NODE_PGMCD + ">" + infoDO.getPgmCd() + "</"  + XML_NODE_PGMCD + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

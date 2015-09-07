package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ContentsPrevInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  코너 정보 관련 XML파서
 * @author asura207
 *
 */
public class ContentsPreInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "ContentsPreInfo";
	/**
	 * 코너 ID
	 */
	private String XML_NODE_CORNERID = "CN_ID";
	/**
	 * 콘텐트 ID
	 */
	private String XML_NODE_CONTENTID = "CT_ID";
	/**
	 * 콘텐트 길이
	 */
	private String XML_NODE_DURATION = "DURATION"; 
	/**
	 * 콘텐트 Sequence
	 */
	private String XML_NODE_SEQUENCE = "CT_SEQ"; 
	//private String XML_NODE_KEYFRAMEPATH = "KFRM_PATH"; 


	public Object setDO(String _xml) {
		setDO(new ContentsPrevInfoDO());
		
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
		ContentsPrevInfoDO infoDO = (ContentsPrevInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CORNERID)) {
				infoDO.setCornerID(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CONTENTID)) {
				infoDO.setContentID(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				infoDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SEQUENCE)) {
				infoDO.setContentSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
		//	else if(_nodeName.equals(XML_NODE_KEYFRAMEPATH)) {
		//		infoDO.setkfrmPath(_nodeValue);
		//	}



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
		ContentsPrevInfoDO infoDO = (ContentsPrevInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CORNERID + ">" + infoDO.getCornerID() + "</"  + XML_NODE_CORNERID + ">");
		_xml.append("<" + XML_NODE_CONTENTID + ">" + infoDO.getContentID() + "</"  + XML_NODE_CONTENTID + ">");
		_xml.append("<" + XML_NODE_DURATION + ">" + infoDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("<" + XML_NODE_SEQUENCE + ">" + infoDO.getContentSeq() + "</"  + XML_NODE_SEQUENCE + ">");
	//	_xml = _xml + "<" + XML_NODE_KEYFRAMEPATH + ">" + infoDO.getkfrmPath() + "</"  + XML_NODE_KEYFRAMEPATH + ">";
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}


}

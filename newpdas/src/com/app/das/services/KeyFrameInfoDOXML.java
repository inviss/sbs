package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.KeyFrameInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  키프레임 정보 관련 XML파서
 * @author asura207
 *
 */
public class KeyFrameInfoDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "keyFrameInfo";
	/**
	 * 키프레임 순번
	 */
	private String XML_NODE_KEYFRAMESEQUENCE = "kfrm_seq";
	/** 
	 * 타임코드 
	 */
	private String XML_NODE_TIMECODE = "time_cd";
	/** 
	 * 화질코드 
	 */
	private String XML_NODE_VIDEOQUALITY = "VD_QLTY";
	/** 
	 * 종횡비코드 
	 */
	private String XML_NODE_ASPRTOCODE = "ASP_RTO_CD"; 

	public Object setDO(String _xml) {
		setDO(new KeyFrameInfoDO());
		
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
		KeyFrameInfoDO infoDO = (KeyFrameInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_KEYFRAMESEQUENCE)) {
				infoDO.setKfrmSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_TIMECODE)) {
				infoDO.setTimeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VIDEOQUALITY)) {
				infoDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASPRTOCODE)) {
				infoDO.setAspRtoCd(_nodeValue);
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
		KeyFrameInfoDO infoDO = (KeyFrameInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_KEYFRAMESEQUENCE + ">" + infoDO.getKfrmSeq() + "</"  + XML_NODE_KEYFRAMESEQUENCE + ">");
		_xml.append("<" + XML_NODE_TIMECODE + ">" + infoDO.getTimeCd() + "</"  + XML_NODE_TIMECODE + ">");
		_xml.append("<" + XML_NODE_VIDEOQUALITY + ">" + infoDO.getVdQlty() + "</"  + XML_NODE_VIDEOQUALITY + ">");
		_xml.append("<" + XML_NODE_ASPRTOCODE + ">" + infoDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
}

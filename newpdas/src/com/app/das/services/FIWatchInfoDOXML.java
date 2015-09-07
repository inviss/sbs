package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.FIWatchInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  WORKFLOW 파일 정보 관련 XML파서
 * @author asura207
 *
 */
public class FIWatchInfoDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "fileWatchInfo";
	/**
	 * DAS_EQ_SEQ
	 */
	private String XML_NODE_EQUICPMENTSEQ = "DAS_EQ_SEQ";
	/**
	 * 등록일
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/**
	 * xml 
	 */
	private String XML_NODE_XML = "XML"; 
	
	
	public Object setDO(String _xml) {
		setDO(new FIWatchInfoDO());
		
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
		FIWatchInfoDO infoDO = (FIWatchInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_EQUICPMENTSEQ)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setDasEqSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_XML)) {
				infoDO.setXml(_nodeValue);
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
		FIWatchInfoDO infoDO = (FIWatchInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_EQUICPMENTSEQ + ">" + infoDO.getDasEqSeq() + "</"  + XML_NODE_EQUICPMENTSEQ + ">");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("<" + XML_NODE_XML + ">" + infoDO.getXml() + "</"  + XML_NODE_XML + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

}

package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CnInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  코너 정보 관련 XML파서
 * @author asura207
 *
 */
public class CnDetailDOXML extends DOXml {
	
	//private static Logger logger = Logger.getLogger(CnDetailDOXML.class);
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "CnDetail";
	/** 
	 * 코너ID 
	 */
	private String XML_NODE_CORNERID = "CN_ID";
	/** 
	 * 시작점 
	 */    
	private String XML_NODE_SOM = "SOM";
	/** 
	 * 종료점 
	 */
	private String XML_NODE_EOM = "EOM";
	/** 
	 * 순번 
	 */
	private String XML_NODE_SEQ = "SEQ";
	/** 
	 * 내용 
	 */
	private String XML_NODE_CONT = "CONT"; 
	
		
	public Object setDO(String _xml) {
		setDO(new CnInfoDO());
		
		List resultList  = new ArrayList();
	
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
       
        int _length = _nodeList.getLength();
         for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				resultList.add(setData((Element)_node));
			}
        }     
		
         return resultList;
	}
	
	public Object setData(Element pElement) {
		CnInfoDO infoDO = new CnInfoDO();
	    NodeList _nodeList = pElement.getChildNodes();
	    
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CORNERID)) {
				infoDO.setCnId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SOM)) {
				infoDO.setSom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EOM)) {
				infoDO.setEom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSeq(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONT)) {
				infoDO.setCont(_nodeValue);
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
		CnInfoDO infoDO = (CnInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");		
		_xml.append("<" + XML_NODE_CORNERID + ">" + infoDO.getCnId() + "</"  + XML_NODE_CORNERID + "> \n");
		_xml.append("<" + XML_NODE_SOM + ">" + infoDO.getSom() + "</"  + XML_NODE_SOM + "> \n");
		_xml.append("<" + XML_NODE_EOM + ">" + infoDO.getEom() + "</"  + XML_NODE_EOM + "> \n");
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_CONT + ">" + CommonUtl.transXmlText(infoDO.getCont()) + "</"  + XML_NODE_CONT + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

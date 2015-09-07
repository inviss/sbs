package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AnnotInfoDO;
import com.app.das.business.transfer.ContentMappInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  CONTENTS_MAPP_TBL 정보 관련 XML파서
 * @author asura207
 *
 */
public class ContentMappInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "contentMappInfo";
	/** 
	 * 콘텐트 ID 
	 */
	private String XML_NODE_CONTENTID = "CT_ID";
	/** 
	 * 마스터 ID 
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/** 
	 * 코너 ID 
	 */
	private String XML_NODE_CONERID = "CN_ID";
	/** 
	 * 프로그램 ID 
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID"; 
	/** 
	 * 등록일 
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/** 
	 * 등록자  ID 
	 */
	private String XML_NODE_REGISTRATORID = "REGRID";	
	/** 
	 * 시작 Duration 
	 */
	private String XML_NODE_STARTDURATION = "S_DURATION";
	/** 
	 * 종료 Duration 
	 */
	private String XML_NODE_ENDDURATION = "E_DURATION";
	/** 
	 * 코너 순번 
	 */
	private String XML_NODE_CONERSEQUENCE = "CN_SEQ";
	/** 
	 * 콘텐츠 순번
	 */
	private String XML_NODE_CONTENTSEQUENCE = "CT_SEQ";
	
	
	public Object setDO(String _xml) {
		setDO(new ContentMappInfoDO());
		
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
		ContentMappInfoDO infoDO = new ContentMappInfoDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CONTENTID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCnId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATORID)) {
				infoDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STARTDURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_ENDDURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONERSEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCnSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			
				
        }
	    
	    return infoDO;
	}
	
	
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}

	public String getSubXML() {
		ContentMappInfoDO infoDO = (ContentMappInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CONTENTID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTID + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_CONERID + ">" + infoDO.getCnId() + "</"  + XML_NODE_CONERID + ">");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + ">");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("<" + XML_NODE_REGISTRATORID + ">" + infoDO.getRegrid() + "</"  + XML_NODE_REGISTRATORID + ">");
		_xml.append("<" + XML_NODE_STARTDURATION + ">" + infoDO.getSDuration() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("<" + XML_NODE_ENDDURATION + ">" + infoDO.getEDuration() + "</"  + XML_NODE_REGISTRATORID + ">");
		_xml.append("<" + XML_NODE_CONERSEQUENCE + ">" + infoDO.getCnSeq() + "</"  + XML_NODE_CONERSEQUENCE + ">");
		_xml.append("<" + XML_NODE_CONTENTSEQUENCE + ">" + infoDO.getCtSeq() + "</"  + XML_NODE_CONTENTSEQUENCE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

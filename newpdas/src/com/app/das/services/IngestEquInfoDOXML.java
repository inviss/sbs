package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.EquipmentMonitoringDO;
import com.app.das.util.CommonUtl;
/**
 * 인제스트 장비 정보 관련 XML파서
 * @author asura207
 *
 */
public class IngestEquInfoDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "equipmentInfo";
	/**
	 * 장비명
	 */
	private String XML_NODE_DASEQNAME = "DAS_EQ_NM";
	/**
	 * 장비 ID
	 */
	private String XML_NODE_DASEQID = "DAS_EQ_ID";
	/**
	 * DAS 장비 순번 
	 */
	private String XML_NODE_DASEQSEQUENCE = "DAS_EQ_SEQ"; 
	/**
	 * 장비구분명
	 */
	private String XML_NODE_DASEQCLASSCODE = "DAS_EQ_CLF_CD";
	/**
	 * DAS 장비 사용 IP
	 */
	private String XML_NODE_DASEQUSEIP = "DAS_EQ_USE_IP";
	/**
	 * DAS 장비 사용 Port 
	 */
	private String XML_NODE_DASEQUSEPORT = "DAS_EQ_USE_PORT"; 
	
	
	public Object setDO(String _xml) {
		setDO(new EquipmentMonitoringDO());
		
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
		EquipmentMonitoringDO infoDO = (EquipmentMonitoringDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_DASEQNAME)) {
				infoDO.setDasEqNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DASEQID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setDasEqId(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_DASEQSEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSerialNo(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_DASEQCLASSCODE)) {
				infoDO.setDasEqClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DASEQUSEIP)) {
				infoDO.setEqUseIp(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DASEQUSEPORT)) {
				infoDO.setEqUsePort(_nodeValue);
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
		EquipmentMonitoringDO infoDO = (EquipmentMonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_DASEQNAME + ">" + CommonUtl.transXmlText(infoDO.getDasEqNm()) + "</"  + XML_NODE_DASEQNAME + ">");
		_xml.append("<" + XML_NODE_DASEQID + ">" + infoDO.getDasEqId() + "</"  + XML_NODE_DASEQID + ">");
		_xml.append("<" + XML_NODE_DASEQSEQUENCE + ">" + infoDO.getSerialNo() + "</"  + XML_NODE_DASEQSEQUENCE + ">");
		_xml.append("<" + XML_NODE_DASEQCLASSCODE + ">" + infoDO.getDasEqClfCd() + "</"  + XML_NODE_DASEQCLASSCODE + ">");
		_xml.append("<" + XML_NODE_DASEQUSEIP + ">" + infoDO.getEqUseIp() + "</"  + XML_NODE_DASEQUSEIP + ">");
		_xml.append("<" + XML_NODE_DASEQUSEPORT + ">" + infoDO.getEqUsePort() + "</"  + XML_NODE_DASEQUSEPORT + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

}

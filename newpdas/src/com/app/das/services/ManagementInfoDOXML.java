package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ManagementInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class ManagementInfoDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "managementInfo";
	/**
	 * 정리완료일
	 */
	private String XML_NODE_AGGRENDDATE = "ARRG_END_DT";
	/**
	 * 수집처코드
	 */
	private String XML_NODE_GATHCOMPANYCODE = "GATH_CO_CD";
	/**
	 * 데이타상태코드
	 */
	private String XML_NODE_DATASTATISTICCODE = "DATA_STAT_CD";
	/**
	 * 2차 아카이버명
	 */
	private String XML_NODE_SECARCHNAME = "SEC_ARCH_NM"; 
	/**
	 * 아카이브등록
	 */
	private String XML_NODE_ARCHREGDD = "ARCH_REG_DD"; 
	/**
	 * 보존기간 RSV_PRD_CD
	 */
	private String XML_NODE_RESERVATIONPERIODCODE = "RSV_PRD_CD"; 
	
	public Object setDO(String _xml) {
		setDO(new ManagementInfoDO());
		
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
		ManagementInfoDO infoDO = (ManagementInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_AGGRENDDATE)) {
				infoDO.setArrgEndDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GATHCOMPANYCODE)) {
				infoDO.setGathCoCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DATASTATISTICCODE)) {
				infoDO.setDataStatCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SECARCHNAME)) {
				infoDO.setSecArchNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCHREGDD)) {
				infoDO.setArchRegDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RESERVATIONPERIODCODE)) {
				infoDO.setArchRegDd(_nodeValue);
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
		ManagementInfoDO infoDO = (ManagementInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_AGGRENDDATE + ">" + infoDO.getArrgEndDt() + "</"  + XML_NODE_AGGRENDDATE + ">");
		_xml.append("<" + XML_NODE_GATHCOMPANYCODE + ">" + infoDO.getGathCoCd() + "</"  + XML_NODE_GATHCOMPANYCODE + ">");
		_xml.append("<" + XML_NODE_DATASTATISTICCODE + ">" + infoDO.getDataStatCd() + "</"  + XML_NODE_DATASTATISTICCODE + ">");
		_xml.append("<" + XML_NODE_SECARCHNAME + ">" + infoDO.getSecArchNm() + "</"  + XML_NODE_SECARCHNAME + ">");
		_xml.append("<" + XML_NODE_ARCHREGDD + ">" + infoDO.getArchRegDd() + "</"  + XML_NODE_ARCHREGDD + ">");
		_xml.append("<" + XML_NODE_RESERVATIONPERIODCODE + ">" + infoDO.getRsvPrdCd() + "</"  + XML_NODE_RESERVATIONPERIODCODE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
}

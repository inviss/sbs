package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.util.CommonUtl;
/**
 * ERP 정보 관련 XML파서
 * @author asura207
 *
 */
public class ErrorRegisterDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "errorInfo";
	/**
	 * 작성자
	 */
	private String XML_NODE_WRITER = "WRT";
	/**
	 * 작업구분
	 */
	private String XML_NODE_WORKCASSFICATION = "WORK_CLF";
	/**
	 * 내용
	 */
	private String XML_NODE_ERRORCONTENT = "ER_CONT";
	/**
	 * 작업지시내용
	 */
	private String XML_NODE_REACTIONCONTENT = "REACT_CONT"; 
	/**
	 * 작업순서
	 */
	private String XML_NODE_WORKSEQUENCE = "WORK_SEQ";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTER_ID = "MASTER_ID";
	/**
	 * 등록자 ID
	 */
	private String XML_NODE_REGISTORID = "REGRID"; 
	/**
	 * 등록일
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";


	public Object setDO(String _xml) {
		setDO(new ErrorRegisterDO());

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
		ErrorRegisterDO infoDO = (ErrorRegisterDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_WRITER)) {
				infoDO.setWrt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WORKCASSFICATION)) {
				infoDO.setWorkClf(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ERRORCONTENT)) {
				infoDO.setCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REACTIONCONTENT)) {
				infoDO.setWorkCmCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WORKSEQUENCE)) {
				infoDO.setWorkSeq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMasterID(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTORID)) {
				infoDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
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
		ErrorRegisterDO infoDO = (ErrorRegisterDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_WRITER + ">" + CommonUtl.transXmlText(infoDO.getWrt()) + "</"  + XML_NODE_WRITER + ">");
		_xml.append("<" + XML_NODE_WORKCASSFICATION + ">" + infoDO.getWorkClf() + "</"  + XML_NODE_WORKCASSFICATION + ">");
		_xml.append("<" + XML_NODE_ERRORCONTENT + ">" + CommonUtl.transXmlText(infoDO.getCont()) + "</"  + XML_NODE_ERRORCONTENT + ">");
		_xml.append("<" + XML_NODE_REACTIONCONTENT + ">" + CommonUtl.transXmlText(infoDO.getWorkCmCont()) + "</"  + XML_NODE_REACTIONCONTENT + ">");
		_xml.append("<" + XML_NODE_WORKSEQUENCE + ">" + infoDO.getWorkSeq() + "</"  + XML_NODE_WORKSEQUENCE + ">");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTER_ID + ">");
		_xml.append("<" + XML_NODE_REGISTORID + ">" + infoDO.getRegrId() + "</"  + XML_NODE_REGISTORID + ">");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

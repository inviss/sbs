package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PgmContensInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 프로그램 메타  정보 관련 XML파서
 * @author asura207
 *
 */
public class PgmContentsInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "programContentsInfo";
	/**
	 * 콘텐트 ID
	 */
	private String XML_NODE_CONTENTSID = "CT_ID";
	/**
	 * 콘텐트 인스탄스 ID
	 */
	private String XML_NODE_CONTENTSINSTANCEID = "CTI_ID";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/**
	 * 콘테트 이름
	 */
	private String XML_NODE_CONTENTSNAME = "CT_NM";
	//private String XML_NODE_PROGRAMNAME = "PGM_NM";
	//private String XML_NODE_PROGRAMEPISODE = "PGM_EPIS";
	/**
	 * 콘텐트 파일 경로
	 */
	private String XML_NODE_CONTENTSFILEPATH = "FL_PATH";

	public Object setDO(String _xml) {
		setDO(new PgmContensInfoDO());

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
		PgmContensInfoDO infoDO = (PgmContensInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSNAME)) {
				infoDO.setCtNm(_nodeValue);
			}
			/*
			else if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
				infoDO.setPgmNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMEPISODE)) {
				infoDO.setPgmEpis(_nodeValue);
			}
			 */
			else if(_nodeName.equals(XML_NODE_CONTENTSFILEPATH)) {
				infoDO.setFlPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSINSTANCEID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setCtiId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(_nodeValue));
				}
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
		PgmContensInfoDO infoDO = (PgmContensInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTSID + "> \n");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + "> \n");
		_xml.append("<" + XML_NODE_CONTENTSNAME + ">" + CommonUtl.transXmlText(infoDO.getCtNm()) + "</"  + XML_NODE_CONTENTSNAME + "> \n");
		//	_xml = _xml + "<" + XML_NODE_PROGRAMNAME + ">" + infoDO.getPgmNm() + "</"  + XML_NODE_PROGRAMNAME + ">";
		//	_xml = _xml + "<" + XML_NODE_PROGRAMEPISODE + ">" + infoDO.getPgmEpis() + "</"  + XML_NODE_PROGRAMEPISODE + ">";
		_xml.append("<" + XML_NODE_CONTENTSFILEPATH + ">" + infoDO.getFlPath() + "</"  + XML_NODE_CONTENTSFILEPATH + "> \n");
		_xml.append("<" + XML_NODE_CONTENTSINSTANCEID + ">" + infoDO.getCtiId() + "</"  + XML_NODE_CONTENTSINSTANCEID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

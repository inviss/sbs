package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.SubsiInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  계열사 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class SubsiInfoDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "subsiinfo";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 계열사 ID
	 */
	private String XML_NODE_SUBSI_ID = "subsi_id";
	/**
	 * 계열사 명
	 */
	private String XML_NODE_SUBSI_NM = "subsi_nm";

	/**
	 * 수신서버 ip
	 */
	private String XML_NODE_RECEVE_SERVER_IP = "receve_server_ip";
	/**
	 * 수신서버 명
	 */
	private String XML_NODE_RECEVE_SERVER_NM = "receve_server_nm";
	/**
	 * 수신서버 port
	 */
	private String XML_NODE_RECEVE_SERVER_PORT = "receve_server_port";
	/**
	 * 비밀번호
	 */
	private String XML_NODE_PASSWORD = "password";
	/**
	 * 목적지 폴더경로
	 */
	private String XML_NODE_PATH = "path";
	/**
	 * 관리자 ID
	 */
	private String XML_NODE_ADMIN_ID = "admin_id";


	public Object setDO(String _xml) {
		setDO(new SubsiInfoDO());

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
		SubsiInfoDO infoDO = (SubsiInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_SEQ)) {
				infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals( XML_NODE_SUBSI_ID)) {
				infoDO.setSubsi_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUBSI_NM)) {
				infoDO.setSubsi_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECEVE_SERVER_IP)) {
				infoDO.setReceve_server_ip(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECEVE_SERVER_NM)) {
				infoDO.setReceve_server_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECEVE_SERVER_PORT)) {
				infoDO.setReceve_server_port(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PASSWORD)) {
				infoDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PATH)) {
				infoDO.setPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ADMIN_ID)) {
				infoDO.setAdmin_id(_nodeValue);
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
		SubsiInfoDO infoDO = (SubsiInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_SEQ+ ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_SUBSI_ID + ">" + infoDO.getSubsi_id() + "</"  + XML_NODE_SUBSI_ID + "> \n");
		_xml.append("<" + XML_NODE_SUBSI_NM + ">" + infoDO.getSubsi_nm() + "</"  + XML_NODE_SUBSI_NM + "> \n");
		_xml.append("<" + XML_NODE_RECEVE_SERVER_IP + ">" + infoDO.getReceve_server_ip() + "</"  + XML_NODE_RECEVE_SERVER_IP + "> \n");
		_xml.append("<" + XML_NODE_RECEVE_SERVER_NM + ">" + infoDO.getReceve_server_nm() + "</"  + XML_NODE_RECEVE_SERVER_NM + "> \n");
		_xml.append("<" + XML_NODE_RECEVE_SERVER_PORT + ">" + infoDO.getReceve_server_port() + "</"  + XML_NODE_RECEVE_SERVER_PORT + "> \n");
		_xml.append("<" + XML_NODE_PASSWORD + ">" + infoDO.getPassword() + "</"  + XML_NODE_PASSWORD + "> \n");
		_xml.append("<" + XML_NODE_PATH + ">" + infoDO.getPath() + "</"  + XML_NODE_PATH + "> \n");
		_xml.append("<" + XML_NODE_ADMIN_ID + ">" + infoDO.getAdmin_id() + "</"  + XML_NODE_ADMIN_ID + "> \n");	
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.PaUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  pa 비밀번호 변경 정보 관련 XML파서
 * @author asura207
 *
 */
public class PaUserPasswordInfoDOXML extends DOXml{
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "pauserinfo";	

	/**
	 * 사용자ID
	 */
	private String XML_NODE_USER_ID = "user_id"; 
	/**
	 * 패스워드
	 */
	private String XML_NODE_PASSWORD = "password";




	public Object setDO(String _xml) {
		setDO(new DepInfoDO());

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
		PaUserInfoDO infoDO = (PaUserInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_USER_ID)) {
				infoDO.setUser_id(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PASSWORD)) {
				infoDO.setPassword(_nodeValue);
			}


		}



		return infoDO;
	}	    

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		PaUserInfoDO infoDO = (PaUserInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_USER_ID + ">" + infoDO.getUser_id() + "</"  + XML_NODE_USER_ID + "> \n");	
		_xml.append("<" + XML_NODE_PASSWORD + ">" + infoDO.getPassword() + "</"  + XML_NODE_PASSWORD + "> \n");	
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.util.CommonUtl;





/**
 * 권한 그룹 정보 관련 XML파서
 * @author asura207
 *
 */
public class AuthorRoleInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "authorinfo";

	/**
	 * 권한그룹코드
	 */
	private String XML_NODE_ROLE_GROUP_CD="role_group_cd";
	/**
	 * 권한그룹명
	 */
	private String XML_NODE_ROLE_GROUP_NM="role_group_nm";






	public Object setDO(String _xml) {
		setDO(new RoleInfoDO());

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
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_ROLE_GROUP_CD)) {
				roleInfoDO.setRole_group_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE_GROUP_NM)) {
				roleInfoDO.setRole_group_nm(_nodeValue);
			}



		}

		return roleInfoDO;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		//_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}





	public String getSubXML() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD+ ">");
		_xml.append("<" + XML_NODE_ROLE_GROUP_NM + ">" + roleInfoDO.getRole_group_nm() + "</"  + XML_NODE_ROLE_GROUP_NM + "> \n");		
		_xml.append("<" + XML_NODE_ROLE_GROUP_CD + ">" +roleInfoDO.getRole_group_id()+ "</"  + XML_NODE_ROLE_GROUP_CD + "> \n");
		_xml.append("</" + XML_NODE_HEAD+ ">");



		return _xml.toString();
	}


	public String getBeans() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();


		String a = roleInfoDO.getRole_nm();
		String b = roleInfoDO.getRole_group_nm();


		return a;
	}

	public String getBeans2() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();



		String b = roleInfoDO.getRole_group_nm();


		return b;
	}

}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  로그인 역활  정보 관련 XML파서
 * @author asura207
 *
 */
public class RoleForLoginDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "roleinfo";
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD2 = "funcion";	
	/**
	 * 역할코드
	 */
	private String XML_NODE_ROLE_CD="role_cd";

	/**
	 * 사용자id
	 */
	private String XML_NODE_USERID="user_id";
	/**
	 * 기능코드
	 */
	private String XML_NODE_FUNCTION_CD="funtion_cd";


	//2012.4.19 다스 확장 추가

	/**
	 * 역활명
	 */
	private String XML_NODE_ROLE_NM="role_nm";

	/**
	 * 채널 코드
	 */
	private String XML_NODE_CHENNEL_CD="chennel";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD="cocd";

	//모니터링 용
	/**
	 * 메뉴id
	 */
	private String XML_NODE_MENU_ID="menu_id";
	/**
	 * 메뉴 명
	 */
	private String XML_NODE_MENU_NM="menu_nm";
	/**
	 * 깊이
	 */
	private String XML_NODE_DEPTH="depth";
	/**
	 * 사용권한
	 */
	private String XML_NODE_USE_PERM="auth";
	/**
	 * 역활
	 */
	private String XML_NODE_PERM_ID="perm_id";

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

			if(_nodeName.equals(XML_NODE_ROLE_CD)) {
				roleInfoDO.setRole_id(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_CHENNEL_CD)) {
				roleInfoDO.setChennel(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_COCD)) {
				roleInfoDO.setCocd(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_MENU_ID)) {
				roleInfoDO.setMenu_id(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_PERM_ID)) {
				roleInfoDO.setPerm_id(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_USE_PERM)) {
				roleInfoDO.setUse_perm(_nodeValue);
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
		_xml.append("<" + XML_NODE_ROLE_CD + ">" +roleInfoDO.getRole_id()+ "</"  + XML_NODE_ROLE_CD + "> \n");



		return _xml.toString();
	}




	public String getSubXML2() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_FUNCTION_CD + ">" +roleInfoDO.getFunction_cd()+ "</"  + XML_NODE_FUNCTION_CD + "> \n");


		return _xml.toString();
	}


	public String getSubXML3() {

		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<"+XML_NODE_HEAD+">");
		_xml.append("<" + XML_NODE_ROLE_CD + ">" +roleInfoDO.getRole_id()+ "</"  + XML_NODE_ROLE_CD + "> \n");
		_xml.append("<" + XML_NODE_ROLE_NM + ">" +CommonUtl.transXmlText(roleInfoDO.getRole_nm())+ "</"  + XML_NODE_ROLE_NM + "> \n");

		_xml.append("</"+XML_NODE_HEAD+">");
		return _xml.toString();
	}

	public String getSubXML4() {

		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<"+XML_NODE_HEAD+">");
		_xml.append("<" + XML_NODE_MENU_ID + ">" +roleInfoDO.getMenu_id()+ "</"  + XML_NODE_MENU_ID + "> \n");
		_xml.append("<" + XML_NODE_MENU_NM + ">" +CommonUtl.transXmlText(roleInfoDO.getMenu_nm())+ "</"  + XML_NODE_MENU_NM + "> \n");
		//	_xml = _xml + "<" + XML_NODE_DEPTH + ">" +roleInfoDO.getDepth()+ "</"  + XML_NODE_DEPTH + "> \n");
		_xml.append("<" + XML_NODE_USE_PERM + ">" +CommonUtl.transXmlText(roleInfoDO.getUse_perm())+ "</"  + XML_NODE_USE_PERM + "> \n");
		if(roleInfoDO.getDepth().equals("2")){
			_xml.append("<"+XML_NODE_HEAD+">");
			_xml.append("<" + XML_NODE_MENU_ID + ">" +roleInfoDO.getMenu_id()+ "</"  + XML_NODE_MENU_ID + "> \n");
			_xml.append("<" + XML_NODE_MENU_NM + ">" +CommonUtl.transXmlText(roleInfoDO.getMenu_nm())+ "</"  + XML_NODE_MENU_NM + "> \n");
			_xml.append("</"+XML_NODE_HEAD+">");
		}
		_xml.append("</"+XML_NODE_HEAD+">");
		return _xml.toString();
	}

}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.RoleInfoDO;
import com.app.das.util.CommonUtl;





/**
 *    권한 정보 관련 XML파서
 * @author asura207
 *
 */ 
public class RoleInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "roleinfo";
	/**
	 * 권한그룹명
	 */
	private String XML_NODE_ROLE_GROUP_CD = "author_group_cd";
	/**
	 * 권한그룹id
	 */
	private String XML_NODE_ROLE_GROUP_ID="role_group_id";
	/**
	 * 권한 id
	 */
	private String XML_NODE_ROLE_GROUP_NM="role_group_nm";
	/**
	 * 권한 명
	 */
	private String XML_NODE_ROLE_NM="role_nm";
	/**
	 * 허용여부
	 */
	private String XML_NODE_USE_YN="use_yn";
	/**
	 * 권한코드
	 */
	private String XML_NODE_ATHRY_CD="athry_cd";
	/**
	 * 역할코드
	 */
	private String XML_NODE_ROLE_CD="role_cd";

	/**
	 * 채널 코드
	 */
	private String XML_NODE_CHENNEL_CD="chennel";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD="cocd";



	/**
	 * 역활ID
	 */
	private String XML_NODE_PERM_ID="permId";
	/**
	 * 사용권한
	 */
	private String XML_NODE_AUTH="auth";
	/**
	 * 메뉴id
	 */
	private String XML_NODE_MENU_ID="menuId";

	/**
	 * 구분
	 */
	private String XML_NODE_GUBUN="gubun";

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
				roleInfoDO.setRole_group_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE_GROUP_ID)) {
				roleInfoDO.setRole_group_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE_GROUP_NM)) {
				roleInfoDO.setRole_group_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE_NM)) {
				roleInfoDO.setRole_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_USE_YN)) {
				roleInfoDO.setUse_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ATHRY_CD)) {
				roleInfoDO.setRole_group_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_ROLE_CD)) {
				roleInfoDO.setRole_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CHENNEL_CD)) {
				roleInfoDO.setChennel(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COCD)) {
				roleInfoDO.setCocd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PERM_ID)) {
				roleInfoDO.setPerm_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_AUTH)) {
				roleInfoDO.setUse_perm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MENU_ID)) {
				roleInfoDO.setMenu_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_GUBUN)) {
				roleInfoDO.setGubun(_nodeValue);
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


	public String getSubXML(String group_nm, String role_nm) {
		//RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_ROLE_GROUP_CD + ">" + group_nm + "</"  + XML_NODE_ROLE_GROUP_CD + "> \n");
		//_xml = _xml + "<" + XML_NODE_ROLE_GROUP_ID + ">" + roleInfoDO.getRole_group_id() + "</"  + XML_NODE_ROLE_GROUP_ID + ">";
		_xml.append("<" + XML_NODE_ROLE_NM + ">" +CommonUtl.transXmlText(role_nm)+ "</"  + XML_NODE_ROLE_NM + "> \n");
		//_xml = _xml + "<" + XML_NODE_USE_YN + ">" + roleInfoDO.getUse_yn() + "</"  + XML_NODE_USE_YN + ">";
		_xml.append("</" + XML_NODE_HEAD + ">");
		return _xml.toString();
	}


	public String getSubXML() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();

		String _xml =   "<" + XML_NODE_HEAD + "> \n";	
		_xml = _xml + 	"<" + XML_NODE_ROLE_GROUP_CD + ">" + roleInfoDO.getRole_group_nm() + "</"  + XML_NODE_ROLE_GROUP_CD + "> \n";
		//_xml = _xml + "<" + XML_NODE_ROLE_GROUP_ID + ">" + roleInfoDO.getRole_group_id() + "</"  + XML_NODE_ROLE_GROUP_ID + ">";
		_xml = _xml + "<" + XML_NODE_ROLE_NM + ">" +CommonUtl.transXmlText(roleInfoDO.getRole_nm())+ "</"  + XML_NODE_ROLE_NM + "> \n";
		//_xml = _xml + "<" + XML_NODE_USE_YN + ">" + roleInfoDO.getUse_yn() + "</"  + XML_NODE_USE_YN + ">";
		_xml = _xml + "</" + XML_NODE_HEAD + ">";	
		return _xml;
	}


	public String getBeans() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();

		String a = roleInfoDO.getRole_nm();
		//String b = roleInfoDO.getRole_group_nm();

		return a;
	}

	public String getBeans2() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();

		String b = roleInfoDO.getRole_group_nm();


		return b;
	}





	public String getSubXML2() {
		RoleInfoDO roleInfoDO = (RoleInfoDO)getDO();

		String _xml =   "<" + XML_NODE_HEAD + "> \n";	
		_xml = _xml + 	"<" + XML_NODE_ROLE_CD + ">" + roleInfoDO.getRole_id() + "</"  + XML_NODE_ROLE_CD + "> \n";
		_xml = _xml + "<" + XML_NODE_ROLE_NM + ">" +CommonUtl.transXmlText(roleInfoDO.getRole_nm())+ "</"  + XML_NODE_ROLE_NM + "> \n";
		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return _xml;
	}
}

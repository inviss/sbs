package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.util.CommonUtl;

/**
 * 권한 정보 관련 XML파서
 * @author asura207
 *
 */

public class AuthorDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */

	private String XML_NODE_HEAD = "Authorinfo";
	/**
	 *  회사코드
	 */
	private String XML_NODE_COCD = "cocd";
	/**
	 * 본부명
	 */
	private String XML_NODE_SUP_HEAD_NM = "sup_head_nm";
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM = "dept_nm";
	/**
	 * 사용자 id
	 */
	private String XML_NODE_SBS_USER_ID = "sbs_user_id";
	/**
	 * 이름
	 */
	private String XML_NODE_USER_NM = "user_nm";
	/**
	 * 권한
	 */
	private String XML_NODE_ROLE_CD = "role_cd";
	/**
	 * 핸드폰
	 */
	private String XML_NODE_MOBILE = "mobile";
	/**
	 * 계정유형
	 */
	private String XML_NODE_ACCT_COCD = "acct_code";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "dept_cd";
	
	/**
	 * 시스템구분코드
	 */
	private String XML_NODE_SYSTEM = "system";
	
	
	
	
	public Object setDO(String _xml) {
		setDO(new AuthorDO());
		
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
		AuthorDO authorDO = (AuthorDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_COCD)) {
				authorDO.setCo_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_HEAD_NM)) {
				authorDO.setSup_head_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				authorDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					authorDO.setSbs_user_id(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					authorDO.setUser_nm(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_ROLE_CD)) {
				
				authorDO.setRole_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_MOBILE)) {
				authorDO.setMobile(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACCT_COCD)) {
				authorDO.setAcct_code(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				authorDO.setDept_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SYSTEM)) {
				authorDO.setSystem(_nodeValue);
			}
	
			
        }
	    
	    return authorDO;
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
		AuthorDO authorDO = (AuthorDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_COCD + ">" + authorDO.getCo_cd() + "</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_NM + ">" + CommonUtl.transXmlText(authorDO.getSup_head_nm()) + "</"  + XML_NODE_SUP_HEAD_NM + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" +CommonUtl.transXmlText(authorDO.getDept_nm()) + "</"  + XML_NODE_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + authorDO.getSbs_user_id() + "</"  + XML_NODE_SBS_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(authorDO.getUser_nm())+ "</"  + XML_NODE_USER_NM + "> \n");
		_xml.append("<" + XML_NODE_ROLE_CD + ">" + authorDO.getRole_cd() + "</"  + XML_NODE_ROLE_CD + "> \n");
		_xml.append("<" + XML_NODE_MOBILE + ">" + authorDO.getMobile() + "</"  + XML_NODE_MOBILE + "> \n");

		
		_xml.append("</" + XML_NODE_HEAD + ">");
	
		
		
		return _xml.toString();
	}

	
}

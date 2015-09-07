package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.util.CommonUtl;


/**
 *   로그인, 로그 아웃   XML파서
 * @author asura207
 *
 */
public class LogInOutDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "loginout";
	/**
	 * IP
	 */
	private String XML_NODE_IP = "ip";
	/**
	 * 로그인 시각
	 */
	private String XML_NODE_LOGIN_DT="login_dt";
	/**
	 * 로그아웃 시각
	 */
	private String XML_NODE_LOGOUT_DT="logout_dt";
	/**
	 * 상태
	 */
	private String XML_NODE_STATUS="status";
	/**
	 * 활성화 여부
	 */
	private String XML_NODE_ACTIVE_YN="active_yn";
	/**
	 * 로그인ID
	 */
	private String XML_NODE_USER_ID="user_id";	
	
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM="dept_nm";	
	
	/**
	 * 유져명
	 */
	private String XML_NODE_USER_NM="user_nm";	
	
	/**
	 * 직원 유형
	 */
	private String XML_NODE_ACCT_COCD="acct_code";	
	
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ="seq";	
	
	
	/**
	 * 총카운트
	 */
	private String XML_NODE_TOTAL_COUNT="total_count";	
	/**
	 * 시작페이지
	 */
	private String XML_NODE_START_PAGE="start_page";	
	
	
	/**
	 * 접속시작일
	 */
	private String XML_NODE_START_LOGIN_DT="start_login_dt";	
	
	/**
	 * 접속종료일
	 */
	private String XML_NODE_END_LOGIN_DT="end_login_dt";	
	
	/**
	 * 전화번호
	 */
	private String XML_NODE_MOBILE="mobile";	
	
	public Object setDO(String _xml) {
		setDO(new LogInOutDO());
		
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
		LogInOutDO logInOutDO = (LogInOutDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_IP)) {
				logInOutDO.setIp(_nodeValue);
				
			}
			else if(_nodeName.equals(XML_NODE_LOGIN_DT)) {
				logInOutDO.setLogin_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LOGOUT_DT)) {
				logInOutDO.setLogout_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STATUS)) {
			
				logInOutDO.setStatus(_nodeValue);
			
			}
			else if(_nodeName.equals(XML_NODE_ACTIVE_YN)) {
				
				logInOutDO.setActive_yn(_nodeValue);
			
			}
			else if(_nodeName.equals(XML_NODE_USER_ID)) {
				logInOutDO.setUser_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				logInOutDO.setUser_nm(_nodeValue);
				
			}
			else if(_nodeName.equals(XML_NODE_ACCT_COCD)) {
				logInOutDO.setAcctype(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				logInOutDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_START_PAGE)) {
				logInOutDO.setStart_page(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_START_LOGIN_DT)) {
				logInOutDO.setStart_login_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_LOGIN_DT)) {
				logInOutDO.setEnd_login_dt(_nodeValue);
			}
        }
	    
	    return logInOutDO;
	}
	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");
		
		return _xml.toString();
	}
	
	 
	public String getSubXML() {
		LogInOutDO logInOutDO = (LogInOutDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_IP + ">" + logInOutDO.getIp() + "</"  + XML_NODE_IP + ">");
		_xml.append("<" + XML_NODE_LOGIN_DT + ">" + logInOutDO.getLogin_dt() + "</"  + XML_NODE_LOGIN_DT + ">");
		_xml.append("<" + XML_NODE_LOGOUT_DT + ">" + logInOutDO.getLogout_dt() + "</"  + XML_NODE_LOGOUT_DT + ">");
		_xml.append("<" + XML_NODE_STATUS + ">" + logInOutDO.getStatus() + "</"  + XML_NODE_STATUS + ">");
		_xml.append("<" + XML_NODE_USER_ID + ">" + logInOutDO.getUser_id() + "</"  + XML_NODE_USER_ID + ">");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + CommonUtl.transXmlText(logInOutDO.getDept_nm()) + "</"  + XML_NODE_DEPT_NM + ">");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(logInOutDO.getUser_nm())+ "</"  + XML_NODE_USER_NM + ">");
		_xml.append("<" + XML_NODE_MOBILE + ">" + logInOutDO.getMobile()+ "</"  + XML_NODE_MOBILE + ">");
		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + logInOutDO.getTotalcount() + "</"  + XML_NODE_TOTAL_COUNT + ">");

		_xml.append("</" + XML_NODE_HEAD + ">");

		 
		return _xml.toString();
	}

	
}

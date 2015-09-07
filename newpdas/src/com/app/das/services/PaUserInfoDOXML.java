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
 *  pa 승인 유져 정보 관련 XML파서
 * @author asura207
 *
 */
public class PaUserInfoDOXML extends DOXml{
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "pauserinfo";	
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "dept_cd";
	/**
	 * 사용자ID
	 */
	private String XML_NODE_USER_ID = "user_id"; 
	/**
	 * 사용자 이름
	 */
	private String XML_NODE_USER_NAME = "user_name"; 
	/**
	 * 사번
	 */
	private String XML_NODE_EMP_NUM = "emp_num"; 
	/**
	 * 주민번호
	 */
	private String XML_NODE_SS_NUM = "ss_num"; 
	/**
	 * 계정유형코드
	 */
	private String XML_NODE_ACCT_CODE = "acct_code";
	/**
	 * 삭제여부
	 */
	private String XML_NODE_DELETE_YN = "delete_yn";
	/**
	 * 등록상태
	 */
	private String XML_NODE_ACTIVE_STATUS = "active_status";
	/**
	 * 패스워드
	 */
	private String XML_NODE_PASSWORD = "password";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CODE = "dept_code";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COMP_CODE = "comp_code";




	public Object setDO(String _xml) {
		setDO(new PaUserInfoDO());

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

			if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				infoDO.setDept_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_ID)) {
				infoDO.setUser_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NAME)) {
				infoDO.setUser_name(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EMP_NUM)) {
				infoDO.setEmp_num(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SS_NUM)) {
				infoDO.setSs_num(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACCT_CODE)) {
				infoDO.setAcct_code(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_DELETE_YN)) {
				infoDO.setDel_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACTIVE_STATUS)) {
				infoDO.setActive_status(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PASSWORD)) {
				infoDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CODE)) {
				infoDO.setDept_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_COMP_CODE)) {
				infoDO.setCocd(_nodeValue);
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

		_xml.append("<" + XML_NODE_DEPT_CD + ">" + infoDO.getDept_cd() + "</"  + XML_NODE_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_USER_ID + ">" + infoDO.getUser_id() + "</"  + XML_NODE_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_USER_NAME + ">" + CommonUtl.transXmlText(infoDO.getUser_name()) + "</"  + XML_NODE_USER_NAME + "> \n");		
		_xml.append("<" + XML_NODE_EMP_NUM + ">" + infoDO.getEmp_num() + "</"  + XML_NODE_EMP_NUM + "> \n");		
		_xml.append("<" + XML_NODE_SS_NUM + ">" + infoDO.getSs_num() + "</"  + XML_NODE_SS_NUM + "> \n");
		_xml.append("<" + XML_NODE_ACCT_CODE + ">" + infoDO.getAcct_code() + "</"  + XML_NODE_ACCT_CODE + "> \n");
		_xml.append("<" + XML_NODE_DELETE_YN + ">" + infoDO.getDel_yn() + "</"  + XML_NODE_DELETE_YN + "> \n");
		_xml.append("<" + XML_NODE_ACTIVE_STATUS + ">" + infoDO.getActive_status() + "</"  + XML_NODE_ACTIVE_STATUS + "> \n");		
		_xml.append("<" + XML_NODE_PASSWORD + ">" + infoDO.getPassword() + "</"  + XML_NODE_PASSWORD + "> \n");	
		_xml.append("<" + XML_NODE_DEPT_CODE + ">" + infoDO.getDept_cd() + "</"  + XML_NODE_DEPT_CODE + "> \n");
		_xml.append("<" + XML_NODE_COMP_CODE + ">" + infoDO.getCocd() + "</"  + XML_NODE_COMP_CODE + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

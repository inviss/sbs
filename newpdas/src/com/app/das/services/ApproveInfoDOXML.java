package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.util.CommonUtl;

/**
 * 프로그램별 승인 정보 관련 XML파서
 * @author asura207
 *
 */
public class ApproveInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "approveinfo";
	/** 
	 * 프로그램id
	 */
	private String XML_NODE_PGM_ID = "pgm_id";
	/** 
	 * 프로그램명
	 */
	private String XML_NODE_PGM_NM="pgm_nm";
	/** 
	 * 승인자 구분
	 */
	private String XML_NODE_APP_GUBUN="app_gubun";
	/** 
	 * 소속부서
	 */
	private String XML_NODE_DEPT_CD="dept_cd";
	/** 
	 * 승인자 사번
	 */
	private String XML_NODE_APPROVE_NUM="approve_num";
	/** 
	 * 승인자명
	 */
	private String XML_NODE_APPROVE_NM="approve_nm";	
	/** 
	 * 직책
	 */
	private String XML_NODE_POSITION="position";

	/** 
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM="dept_nm";
	/** 
	 * 프로그램코드
	 */
	private String XML_NODE_PGM_CD="pgm_cd";
	
	/** 
	 * 직원유형
	 */
	private String XML_NODE_ACCT_CODE="acct_code";
	/** 
	 * 승인자 id
	 */
	private String XML_NODE_USER_ID="user_id";

	/** 
	 * 회사명
	 */
	private String XML_NODE_CONM="conm";

	/** 
	 * 회사코드
	 */
	private String XML_NODE_COCD="cocd";
	
	/** 
	 * 이름
	 */
	private String XML_NODE_USER_NM="user_nm";
	public Object setDO(String _xml) {
		setDO(new ApproveInfoDO());
		
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
		ApproveInfoDO boardDO = (ApproveInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PGM_ID)) {
				boardDO.setPgm_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGM_NM)) {
				boardDO.setPgm_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_APP_GUBUN)) {
				boardDO.setApp_gubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
			
					boardDO.setDept_cd(_nodeValue);
			
			}
		/*	else if(_nodeName.equals(XML_NODE_REG_DT)) {
				
					boardDO.s(_nodeValue);
			
			}*/
			else if(_nodeName.equals(XML_NODE_APPROVE_NUM)) {
				boardDO.setUser_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_APPROVE_NM)) {
				boardDO.setUser_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_POSITION)) {
				boardDO.setPosition(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				boardDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGM_CD)) {
				boardDO.setPgm_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACCT_CODE)) {
				boardDO.setPgm_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_ID)) {
				boardDO.setUser_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COCD)) {
				boardDO.setCocd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_USER_NM)) {
				boardDO.setUser_nm(_nodeValue);
			}
			
        }
	    
	    return boardDO;
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
		ApproveInfoDO boardDO = (ApproveInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PGM_ID + ">" + boardDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(boardDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_APP_GUBUN + ">" + boardDO.getApp_gubun() + "</"  + XML_NODE_APP_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" + boardDO.getDept_cd() + "</"  + XML_NODE_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_APPROVE_NUM + ">" + boardDO.getUser_no() + "</"  + XML_NODE_APPROVE_NUM + "> \n");
		//_xml = _xml + "<" + XML_NODE_MOD_DT + ">" + boardDO.getModDt() + "</"  + XML_NODE_MOD_DT + ">";
		_xml.append("<" + XML_NODE_APPROVE_NM + ">" + CommonUtl.transXmlText(boardDO.getUser_nm()) + "</"  + XML_NODE_APPROVE_NM + "> \n");
		//_xml = _xml + "<" + XML_NODE_MODID + ">" + boardDO.getModrid() + "</"  + XML_NODE_MODID + ">";
		_xml.append("<" + XML_NODE_POSITION + ">" + boardDO.getPosition() + "</"  + XML_NODE_POSITION + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + CommonUtl.transXmlText(boardDO.getDept_nm()) + "</"  + XML_NODE_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_PGM_CD + ">" + boardDO.getPgm_cd()+ "</"  + XML_NODE_PGM_CD + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		
		return _xml.toString();
	}
	
	
	public String getSubXML2() {
		ApproveInfoDO boardDO = (ApproveInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_CONM + ">" + CommonUtl.transXmlText(boardDO.getConm()) + "</"  + XML_NODE_CONM + "> \n");
		_xml.append("<" + XML_NODE_COCD + ">" + boardDO.getCocd()+ "</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_USER_ID + ">" + boardDO.getUser_id() + "</"  + XML_NODE_USER_ID + "> \n");
	    _xml.append("<" + XML_NODE_APPROVE_NM + ">" + CommonUtl.transXmlText(boardDO.getUser_nm()) + "</"  + XML_NODE_APPROVE_NM + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
	
	
	public String getSubXML3() {
		ApproveInfoDO boardDO = (ApproveInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_APPROVE_NUM + ">" + boardDO.getUser_no() + "</"  + XML_NODE_APPROVE_NUM + "> \n");
		_xml.append("<" + XML_NODE_USER_ID + ">" + boardDO.getUser_id() + "</"  + XML_NODE_USER_ID + "> \n");
	    _xml.append("<" + XML_NODE_APPROVE_NM + ">" + CommonUtl.transXmlText(boardDO.getUser_nm()) + "</"  + XML_NODE_APPROVE_NM + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		
		return _xml.toString();
	}
}

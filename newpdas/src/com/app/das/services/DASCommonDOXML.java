package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DASCommonDO;
import com.app.das.util.CommonUtl;

/**
 *  DASCommon 정보 관련 XML파서
 * @author asura207
 *
 */
public class DASCommonDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "commonInfo";
	/**
	 * 정규직여부(정규직인 경우 'Y', 비정규직 'N')
	 */
	private String XML_NODE_REGULARYN = "regularyn";
	/**
	 * 사번
	 */
	private String XML_NODE_USERNO = "userno";
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPTNM = "deptnm";
	/**
	 * 사용자ID
	 */
	private String XML_NODE_USERID = "userid";
	/**
	 * 사용자명
	 */
	private String XML_NODE_USERNM = "usernm";
	/**
	 * 권한
	 */	
	private String XML_NODE_AUTH = "auth";
	/**
	 * 역할
	 */
	private String XML_NODE_ROLE = "role";
	/**
	 * 주민번호
	 */
	private String XML_NODE_PERREGNO = "perregno";
	/**
	 * 시작지점코드
	 */
	private String XML_NODE_POSCD = "poscd";
	/**
	 * 이름
	 */
	private String XML_NODE_NAME = "name";
	/**
	 * respid
	 */
	private String XML_NODE_RESPID = "respid";
	/**
	 * 로그인 Warning 메세지
	 */
	private String XML_NODE_WARNINGMSG = "warningmsg";
	/**
	 * token
	 */
	private String XML_NODE_TOKEN = "token";
	/**
	 * 시작페이지
	 */
	private String XML_NODE_SPAGE = "startpage";
	
	
	
	public Object setDO(String _xml) {
		setDO(new DASCommonDO());
		
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
		DASCommonDO cartDO = (DASCommonDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_REGULARYN )) {
				cartDO.setRegularYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USERNO )) {
				cartDO.setUserNo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPTNM  )) {
				cartDO.setDeptNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USERID  )) {
				cartDO.setUserId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USERNM  )) {
				cartDO.setUserNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AUTH  )) {
				cartDO.setAuth(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE  )) {
				cartDO.setRole(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PERREGNO  )) {
				cartDO.setPerRegNo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_POSCD   )) {
				cartDO.setPosCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_NAME   )) {
				cartDO.setName(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RESPID   )) {
				cartDO.setRespId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WARNINGMSG   )) {
				cartDO.setWarningMsg(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TOKEN   )) {
				cartDO.setToken(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SPAGE   )) {
				cartDO.setStartPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
		
        }
	    
	    return cartDO;
	}
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}
		
	public String getSubXML() {
		DASCommonDO commonDO = (DASCommonDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_REGULARYN  + ">" + commonDO.getRegularYn() + "</"  + XML_NODE_REGULARYN + ">");
		_xml.append("<" + XML_NODE_USERNO  + ">" + commonDO.getUserNo() + "</"  + XML_NODE_USERNO + ">");
		_xml.append("<" + XML_NODE_DEPTNM  + ">" + CommonUtl.transXmlText(commonDO.getDeptNm()) + "</"  + XML_NODE_DEPTNM + ">");
		_xml.append("<" + XML_NODE_USERID  + ">" + commonDO.getUserId() + "</"  + XML_NODE_USERID + ">");
		_xml.append("<" + XML_NODE_USERNM  + ">" + CommonUtl.transXmlText(commonDO.getUserNm()) + "</"  + XML_NODE_USERNM + ">");
		_xml.append("<" + XML_NODE_AUTH  + ">" + commonDO.getAuth() + "</"  + XML_NODE_AUTH + ">");
		_xml.append("<" + XML_NODE_ROLE  + ">" + commonDO.getRole() + "</"  + XML_NODE_ROLE + ">");
		_xml.append("<" + XML_NODE_PERREGNO  + ">" + commonDO.getPerRegNo() + "</"  + XML_NODE_PERREGNO + ">");
		_xml.append("<" + XML_NODE_POSCD  + ">" + commonDO.getPosCd() + "</"  + XML_NODE_POSCD + ">");
		_xml.append("<" + XML_NODE_NAME  + ">" + CommonUtl.transXmlText(commonDO.getName()) + "</"  + XML_NODE_NAME + ">");
		_xml.append("<" + XML_NODE_RESPID  + ">" + commonDO.getRespId() + "</"  + XML_NODE_RESPID + ">");
		_xml.append("<" + XML_NODE_WARNINGMSG  + ">" + commonDO.getWarningMsg() + "</"  + XML_NODE_WARNINGMSG + ">");
		_xml.append("<" + XML_NODE_TOKEN  + ">" + commonDO.getToken() + "</"  + XML_NODE_TOKEN + ">");
	

		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	
}

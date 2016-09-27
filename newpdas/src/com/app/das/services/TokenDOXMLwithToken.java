package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TokenDO;
import com.app.das.util.CommonUtl;
/**
 *  Token WITH TOKOEN 정보 관련 XML파서
 * @author asura207
 *
 */
public class TokenDOXMLwithToken extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "tokeninfo";	
	/**
	 * 사번
	 */
	//private String XML_NODE_USER_NO = "user_no"; 
	/**
	 * 주민등록번호
	 */
	//private String XML_NODE_PER_REG_NO = "per_reg_no"; 
	/**
	 * 회사코드
	 */
	//private String XML_NODE_COCD = "cocd";
	/**
	 * 유저id
	 */
	private String XML_NODE_USER_ID = "user_id"; 
	/**
	 * 소스시스템
	 */
	//private String XML_NODE_SOURCESYS = "sourcesys"; 
	/**
	 * 토큰만료일
	 */
	//private String XML_NODE_ENDTOKEN = "endtoken";
	/**
	 * 직원유형
	 */
	//private String XML_NODE_ACCT_CODE = "acct_code";
	/**
	 * 결과값.
	 */
	private String XML_NODE_RESULT = "result";
	/**
	 * 설명
	 */
	private String XML_NODE_DESC = "desc";
	/**
	 * 토큰
	 */
	private String XML_NODE_TOKEN = "token";
	/**
	 * HEX (맥어드레스 : 클라이언트)
	 */
	private String XML_NODE_HEX = "hex";
	/**
	 * 직원유형(정직원 개별ID: S,비직원 개별ID: N,비직원 공용ID: C )
	 */
	//private String XML_NODE_EMPTYPE = "emptype";
	/**
	 * 실행시스템 Count 
	 */
	//private String XML_NODE_EXECNT = "execnt";
	/**
	 * 패스워드
	 */
	private String XML_NODE_PASSWORD = "password";
	/**
	 * 인증 결과 값.
	 */
	private String xML_NODE_AUTH_RESULT = "authresult";


	public Object setDO(String _xml) {
		setDO(new TokenDO());

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
		TokenDO infoDO = (TokenDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_HEX)){
				infoDO.setHex(_nodeValue);
			}
			//			else if(_nodeName.equals(XML_NODE_EXECNT)){
			//				infoDO.setExeCnt(Integer.parseInt(_nodeValue));
			//			}
			//			else if(_nodeName.equals(XML_NODE_EMPTYPE)){
			//				infoDO.setEmpType(_nodeValue);
			//			}
			else if(_nodeName.equals(XML_NODE_PASSWORD)){
				infoDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_ID)){
				infoDO.setUser_id(_nodeValue);
			}
			/**
			 * token 占쏙옙 占쏙옙占쏙옙 enc&dec 占쏙옙 DO 占쏙옙占쏙옙 처占쏙옙 占싼댐옙.
			 */
			else if(_nodeName.equals(XML_NODE_TOKEN)){
				infoDO.setToken(_nodeValue);
			}
			/**
			 * 占쏙옙占?占쏙옙 占쏙옙占쏙옙
			 */
			else if(_nodeName.equals(XML_NODE_RESULT)) {
				infoDO.setResult(_nodeValue);
			}
			else if(_nodeName.equals(xML_NODE_AUTH_RESULT)){
				infoDO.setAuth_result(_nodeValue);
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
		TokenDO infoDO = (TokenDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		//		_xml = _xml + "<" + XML_NODE_USER_NO + ">" + infoDO.getUser_num() + "</"  + XML_NODE_USER_NO + "> \n";
		//		_xml = _xml + "<" + XML_NODE_PER_REG_NO + ">" + infoDO.getPer_reg_no() + "</"  + XML_NODE_PER_REG_NO + "> \n";
		//		_xml = _xml + "<" + XML_NODE_COCD + ">" + infoDO.getCocd() + "</"  + XML_NODE_COCD + "> \n";
		_xml.append("<" + XML_NODE_USER_ID + ">" + infoDO.getUser_id() + "</"  + XML_NODE_USER_ID + "> \n");
		//		_xml = _xml + "<" + XML_NODE_SOURCESYS + ">" + infoDO.getSourceSYS() + "</"  + XML_NODE_SOURCESYS + "> \n";
		//		_xml = _xml + "<" + XML_NODE_ENDTOKEN + ">" + infoDO.getEnd_token_dd() + "</"  + XML_NODE_ENDTOKEN + "> \n";
		//		_xml = _xml + "<" + XML_NODE_ACCT_CODE + ">" + infoDO.getAcct_code()+ "</"  + XML_NODE_ACCT_CODE + "> \n";
		_xml.append("<" + XML_NODE_RESULT + ">" + CommonUtl.transXmlText(infoDO.getResult())+ "</"  + XML_NODE_RESULT + "> \n");
		_xml.append("<" + xML_NODE_AUTH_RESULT + ">" + CommonUtl.transXmlText(infoDO.getResult())+ "</"  + xML_NODE_AUTH_RESULT + "> \n");
		_xml.append("<" + XML_NODE_DESC + ">" + CommonUtl.transXmlText(infoDO.getDesc())+ "</"  + XML_NODE_DESC + "> \n");
		_xml.append("<" + XML_NODE_TOKEN + ">" + infoDO.getToken()+ "</"  + XML_NODE_TOKEN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

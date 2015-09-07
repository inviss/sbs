package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.util.CommonUtl;

/**
 *  코드 정보 관련 XML파서
 * @author asura207
 *
 */
public class CodeDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
private String XML_NODE_HEAD = "commonCodeInfo";
/**
 * 구분코드
 */
	private String XML_NODE_CLFCD = "CLF_CD";
	/**
	 * 구분상세코드
	 */
	private String XML_NODE_SCLCD = "SCL_CD";
	/**
	 * 구분명
	 */
	private String XML_NODE_CLFNM = "CLF_NM";
	/**
	 * 비고1
	 */
	private String XML_NODE_RMK_1 = "RMK_1";
	/**
	 * 비고2
	 */
	private String XML_NODE_RMK_2 = "RMK_2";
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REGRID = "REGRID";
	/**
	 * 설명
	 */
	private String XML_NODE_DESCRIPTION = "DESC";
	/**
	 * 2차 구분코드 (예: 주석구분코드 ( 주제영상,사용제한등급)
	 */
	private String XML_NODE_GUBUN = "GUBUN";
	/**
	 * 사용여부
	 */
	private String XML_NODE_USE_YN = "USE_YN";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "REG_DT";
	/**
	 * 수정일
	 */
	private String XML_NODE_MOD_DT = "MOD_DT";
	/**
	 * 구분자 (001 전체검색, 002 대분류, 003 중분류, 004 소분류)
	 */
	private String XML_NODE_SEARCH_TYPE = "SEARCH_TYPE";
	/**
	 * 주제영상 구분(ㄱㄴㄷㄹ.....ㅎ까지)
	 */
	private String XML_NODE_MAIN_GUNUN = "MAIN_GUBUN";
	
	//2012.4.20
	/**
	 * 채널코드
	 */
	private String XML_NODE_CHENNEL = "chennel";
	/**
	 * 회사 코드
	 */
	private String XML_NODE_COCD = "cocd";

	public Object setDO(String _xml) {
		setDO(new CodeDO());
		
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
		CodeDO infoDO = (CodeDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_CLFCD)) {
				infoDO.setClfCd(_nodeValue);
			}
			else if(_nodeName.equals( XML_NODE_SCLCD)) {
				infoDO.setSclCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				infoDO.setDesc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CLFNM)) {
				infoDO.setClfNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RMK_1)) {
				infoDO.setRmk1(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RMK_2)) {
				infoDO.setRmk2(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGRID)) {
				infoDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_YN)) {
				infoDO.setUseYn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SEARCH_TYPE)) {
				infoDO.setSearch_Type(_nodeValue);
			}
			
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MOD_DT)) {
				infoDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MAIN_GUNUN)) {
				infoDO.setMainGubun(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				infoDO.setRmk2(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COCD)) {
				infoDO.setRmk1(_nodeValue);
			}
        }
	    
		
		
	    return infoDO;
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
		CodeDO infoDO = (CodeDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_CLFCD + ">" + infoDO.getClfCd() + "</"  + XML_NODE_CLFCD + "> \n");
		_xml.append("<" + XML_NODE_SCLCD + ">" + infoDO.getSclCd() + "</"  + XML_NODE_SCLCD + "> \n");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + "> \n");
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + "> \n");
		_xml.append("<" + XML_NODE_CLFNM + ">" + CommonUtl.transXmlText(infoDO.getClfNm()) + "</"  + XML_NODE_CLFNM + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getRegrId() + "</"  + XML_NODE_REGRID + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_MOD_DT + ">" + infoDO.getModDt() + "</"  + XML_NODE_MOD_DT + "> \n");
		_xml.append("<" + XML_NODE_USE_YN + ">" + infoDO.getUseYn() + "</"  + XML_NODE_USE_YN + "> \n");
		_xml.append("<" + XML_NODE_RMK_1 + ">" + infoDO.getMainGubun() + "</"  + XML_NODE_RMK_1 + "> \n");
		_xml.append("<" + XML_NODE_MAIN_GUNUN + ">" + infoDO.getMainGubun() + "</"  + XML_NODE_MAIN_GUNUN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	
	
	
	
	
	public String getSubXML2() {
		CodeDO infoDO = (CodeDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_SCLCD + ">" + infoDO.getSclCd() + "</"  + XML_NODE_SCLCD + "> \n");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + "> \n");
		//_xml = _xml + "<" + XML_NODE_USE_YN + ">" + infoDO.getUseYn() + "</"  + XML_NODE_USE_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	
	

}

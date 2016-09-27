package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.util.CommonUtl;


/**
 * 자동아카이브  정보 관련 XML파서
 * @author asura207
 *
 */
public class AutoArchiveDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "autoarchive";
	/** 
	 * 구분코드
	 */
	private String XML_NODE_CLF_CD = "clf_cd";
	/** 
	 * 구분상세코드
	 */
	private String XML_NODE_SCL_CD="scl_cd";
	/** 
	 * 소재종류
	 */
	private String XML_NODE_DESC="desc";
	/** 
	 * 설정
	 */
	private String XML_NODE_AUTO_YN="auto_yn";
	/** 
	 * 순번
	 */
	private String XML_NODE_SEQ="seq";

	//2012.4.23
	/** 
	 * 회사코드
	 */
	private String XML_NODE_COCD="cocd";
	/** 
	 * 회사명
	 */
	private String XML_NODE_CONM="conm";
	/** 
	 * 채널코드 
	 */
	private String XML_NODE_CHENNEL="chennel";
	/** 
	 * 채널명
	 */
	private String XML_NODE_CHENNEL_NM="chennel_nm";

	/** 
	 * 아카이브 경로
	 */
	private String XML_NODE_ARCH_ROUTE="arch_route";

	/** 
	 * 아카이브 경로명
	 */
	private String XML_NODE_ARCH_ROUTE_NM="arch_route_nm";
	
	public Object setDO(String _xml) {
		setDO(new AutoArchiveDO());
		
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
		AutoArchiveDO autoDO = (AutoArchiveDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SEQ)) {
				autoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CLF_CD)) {
				autoDO.setClf_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SCL_CD)) {
				autoDO.setScl_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESC)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					autoDO.setDesc(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_AUTO_YN)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					autoDO.setAuto_yn(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_COCD)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					autoDO.setCocd(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					autoDO.setChennel(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_ARCH_ROUTE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					autoDO.setArch_route(_nodeValue);
				}
			}
			
        }
	    
	    return autoDO;
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
		AutoArchiveDO autoDO = (AutoArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_SEQ + ">" + autoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_CLF_CD + ">" + autoDO.getClf_cd() + "</"  + XML_NODE_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_SCL_CD + ">" +autoDO.getScl_cd() + "</"  + XML_NODE_SCL_CD + "> \n");
		_xml.append("<" + XML_NODE_DESC + ">" + CommonUtl.transXmlText(autoDO.getDesc()) + "</"  + XML_NODE_DESC + "> \n");
		_xml.append("<" + XML_NODE_AUTO_YN + ">" + autoDO.getAuto_yn() + "</"  + XML_NODE_AUTO_YN + "> \n");
		_xml.append("<" + XML_NODE_COCD + ">" + autoDO.getCocd() + "</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_CONM + ">" +autoDO.getConm() + "</"  + XML_NODE_CONM + "> \n");
		_xml.append("<" + XML_NODE_CHENNEL + ">" + autoDO.getChennel() + "</"  + XML_NODE_CHENNEL + "> \n");
		_xml.append("<" + XML_NODE_CHENNEL_NM + ">" + autoDO.getChennelnm()+ "</"  + XML_NODE_CHENNEL_NM + "> \n");
		_xml.append("<" + XML_NODE_ARCH_ROUTE + ">" + autoDO.getArch_route()+ "</"  + XML_NODE_ARCH_ROUTE + "> \n");
		_xml.append("<" + XML_NODE_ARCH_ROUTE_NM + ">" + CommonUtl.transXmlText(autoDO.getArch_route_nm())+ "</"  + XML_NODE_ARCH_ROUTE_NM + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
	
		return _xml.toString();
	}

	
}

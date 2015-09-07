package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DownCartDO;
import com.app.das.util.CommonUtl;
/**
 *  인제스트 다운카드 정보 관련 XML파서
 * @author asura207
 *
 */
public class IngestDownCartDOXML extends DOXml {


	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "downCart";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CARTNO = "CART_NO";
	/**
	 * 자료구분코드
	 */
	private String XML_NODE_DATACLASSIFICATIONCODE = "DATA_CLF_CD";
	/**
	 * 우선순위코드
	 */
	private String XML_NODE_PRIORCODE = "PRIO_CD";
	/**
	 * 저장위치
	 */
	private String XML_NODE_STORAGELOC = "STRG_LOC";
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQUESTERID = "REQ_USRID";
	/**
	 * 요청일시
	 */
	private String XML_NODE_REQUESTDATE = "REQ_DT";
	/**
	 * 다운로드일시
	 */
	private String XML_NODE_DOWNDATE = "DOWN_DT";
	/**
	 * 다운로드제목
	 */
	private String XML_NODE_DOWNTITLE = "DOWN_SUBJ";
	/**
	 * 화질코드
	 */
	private String XML_NODE_VEDIOQUALITY = "VD_QLTY";
	/**
	 * 종횡비코드
	 */
	private String XML_NODE_ASPRTOCODE = "ASP_RTO_CD";
	/**
	 * 아이템 총 영상길이
	 */
	private String XML_NODE_DURATION ="DURATION";
	
	public Object setDO(String _xml) {
		setDO(new DownCartDO());
		
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
		DownCartDO cartDO = (DownCartDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CARTNO)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}	
			}
			else if(_nodeName.equals(XML_NODE_DATACLASSIFICATIONCODE)) {
				cartDO.setDataClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRIORCODE)) {
				cartDO.setPrioCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STORAGELOC)) {
				cartDO.setStrgLoc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQUESTERID)) {
				cartDO.setReqUsrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQUESTDATE)) {
				cartDO.setReqDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DOWNDATE)) {
				cartDO.setDownDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DOWNTITLE)) {
				cartDO.setDownSubj(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VEDIOQUALITY)) {
				cartDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASPRTOCODE)) {
				cartDO.setAspRtoCd(_nodeValue);
			}		
        }
	    
	    return cartDO;
	}
	
	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");
		
		return _xml.toString();
	}

	public String getSubXML() {
		DownCartDO cartDO = (DownCartDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CARTNO + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO + ">");
		_xml.append("<" + XML_NODE_DATACLASSIFICATIONCODE + ">" + cartDO.getDataClfCd() + "</"  + XML_NODE_DATACLASSIFICATIONCODE + ">");
		_xml.append("<" + XML_NODE_PRIORCODE + ">" + cartDO.getPrioCd() + "</"  + XML_NODE_PRIORCODE + ">");
		_xml.append("<" + XML_NODE_STORAGELOC + ">" + cartDO.getStrgLoc() + "</"  + XML_NODE_STORAGELOC + ">");
		_xml.append("<" + XML_NODE_REQUESTERID + ">" + cartDO.getReqUsrid() + "</"  + XML_NODE_REQUESTERID + ">");
		_xml.append("<" + XML_NODE_REQUESTDATE + ">" + cartDO.getReqDt() + "</"  + XML_NODE_REQUESTDATE + ">");
		_xml.append("<" + XML_NODE_DOWNDATE + ">" + cartDO.getDownDt() + "</"  + XML_NODE_DOWNDATE + ">");
		_xml.append("<" + XML_NODE_DOWNTITLE + ">" + CommonUtl.transXmlText(cartDO.getDownSubj()) + "</"  + XML_NODE_DOWNTITLE + ">");
		_xml.append("<" + XML_NODE_VEDIOQUALITY + ">" + cartDO.getVdQlty() + "</"  + XML_NODE_VEDIOQUALITY + ">");
		_xml.append("<" + XML_NODE_ASPRTOCODE + ">" + cartDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE + ">");
		_xml.append("<" + XML_NODE_DURATION + ">" + cartDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

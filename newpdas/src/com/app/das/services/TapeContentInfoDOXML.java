package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TapeContentInfoDO;
import com.app.das.util.CommonUtl;
/**
 *    TapeContent 정보 관련 XML파서
 * @author asura207
 *
 */
public class TapeContentInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "tapeConentInfo";
	/**
	 * 등록 일자
	 */
	private String XML_NODE_RTGDD = "RTG_DD";
	/**
	 * 수집 번호
	 */
	private String XML_NODE_GATHNO = "GATH_NO";
	/**
	 * 수집 처
	 */
	private String XML_NODE_GATHNM = "GATH_NM"; 
	/**
	 * 테이프 종류 코드
	 */
	private String XML_NODE_TAPEKIND = "TAPE_KIND";
	/**
	 * 등록 일자
	 */
	private String XML_NODE_TAPENUM = "TAPE_NUM"; 

	public Object setDO(String _xml) {
		setDO(new TapeContentInfoDO());

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
		TapeContentInfoDO infoDO = (TapeContentInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_RTGDD)) {
				infoDO.setRtgDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GATHNO)) {
				infoDO.setGthNo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GATHNM)) {
				infoDO.setGthNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPEKIND)) {
				infoDO.setTapeKind(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPENUM)) {
				infoDO.setTapeNum(_nodeValue);
			}


		}

		return infoDO;
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
		TapeContentInfoDO infoDO = (TapeContentInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_RTGDD + ">" + infoDO.getRtgDd() + "</"  + XML_NODE_RTGDD + ">");
		_xml.append("<" + XML_NODE_GATHNO + ">" + infoDO.getGthNo() + "</"  + XML_NODE_GATHNO + ">");
		_xml.append("<" + XML_NODE_GATHNM + ">" + CommonUtl.transXmlText(infoDO.getGthNm()) + "</"  + XML_NODE_GATHNM + ">");
		_xml.append("<" + XML_NODE_TAPEKIND + ">" + infoDO.getTapeKind() + "</"  + XML_NODE_TAPEKIND + ">");
		_xml.append("<" + XML_NODE_TAPENUM + ">" + infoDO.getTapeNum() + "</"  + XML_NODE_TAPENUM + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

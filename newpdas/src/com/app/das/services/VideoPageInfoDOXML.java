package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.VideoPageInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  비디오 페이지  정보 관련 XML파서
 * @author asura207
 *
 */
public class VideoPageInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "vedeoPageInfo";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID";
	/**
	 * 프로그램 이름
	 */
	private String XML_NODE_PROGRAMNAME = "PGM_NM";
	/**
	 * 방송 시작 일
	 */
	private String XML_NODE_BRDBEGINEDATE = "BRD_BGN_DD";
	/**
	 * 방송 종료  일
	 */
	private String XML_NODE_BRDENDDATE = "BRD_END_DD";
	/**
	 * 미디어코드
	 */
	private String XML_NODE_MEDIACODE = "MEDIA_CD";
	/**
	 * 프로그램코드
	 */
	private String XML_NODE_PROGRAMCD = "PGM_CD";


	public Object setDO(String _xml) {
		setDO(new VideoPageInfoDO());

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
		VideoPageInfoDO infoDO = (VideoPageInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				infoDO.setPgmId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
				infoDO.setPgmNm(_nodeValue);
			}	
			else if(_nodeName.equals(XML_NODE_BRDBEGINEDATE)) {
				infoDO.setBrdBgnDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRDENDDATE)) {
				infoDO.setBrdEndDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIACODE)) {
				infoDO.setMediaCd(_nodeValue);
			}		
			if(_nodeName.equals(XML_NODE_PROGRAMCD)) {
				infoDO.setPgmCD(_nodeValue);
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
		VideoPageInfoDO infoDO = (VideoPageInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + ">");
		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PROGRAMNAME + ">");
		_xml.append("<" + XML_NODE_BRDBEGINEDATE + ">" + infoDO.getBrdBgnDd() + "</"  + XML_NODE_BRDBEGINEDATE + ">");
		_xml.append("<" + XML_NODE_BRDENDDATE + ">" + infoDO.getBrdEndDd() + "</"  + XML_NODE_BRDENDDATE  + ">");
		_xml.append("<" + XML_NODE_MEDIACODE + ">" + infoDO.getMediaCd() + "</"  + XML_NODE_MEDIACODE + ">");
		_xml.append("<" + XML_NODE_PROGRAMCD + ">" +infoDO.getPgmCD() + "</"  + XML_NODE_PROGRAMCD + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

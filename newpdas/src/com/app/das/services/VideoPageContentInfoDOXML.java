package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.VideoPageContentInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 비디오 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class VideoPageContentInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "videoPageContentsInfo";
	/**
	 * 영상id
	 */
	private String XML_NODE_CONTENTSID = "CT_ID";
	/**
	 * 화면비
	 */
	private String XML_NODE_ASPRATIOCODE = "ASP_RTO_CD";
	/**
	 * 화질
	 */

	private String XML_NODE_VIDEOQUALITY = "VD_QLTY"; 
	/**
	 * 컨텐츠길이
	 */

	private String XML_NODE_CONTENTSLENGTH = "CT_LENG"; 


	public Object setDO(String _xml) {
		setDO(new VideoPageContentInfoDO());

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
		VideoPageContentInfoDO infoDO = (VideoPageContentInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				infoDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ASPRATIOCODE)) {
				infoDO.setAspRtoCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VIDEOQUALITY)) {
				infoDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSLENGTH)) {
				infoDO.setctLeng(_nodeValue);
			}	

		}

		return infoDO;
	}	    

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		VideoPageContentInfoDO infoDO = (VideoPageContentInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_ASPRATIOCODE + ">" + infoDO.getAspRtoCd() + "</"  + XML_NODE_ASPRATIOCODE + ">");
		_xml.append("<" + XML_NODE_VIDEOQUALITY + ">" + infoDO.getVdQlty() + "</"  + XML_NODE_VIDEOQUALITY + ">");
		_xml.append("<" + XML_NODE_CONTENTSLENGTH + ">" + infoDO.getctLeng() + "</"  + XML_NODE_CONTENTSLENGTH + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.KeyFrameImgDO;
import com.app.das.util.CommonUtl;
/**
 *  키프레임 이미지 정보 관련 XML파서
 * @author asura207
 *
 */
public class KeyFrameImgDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "keyFrameImg";
	/**
	 * 대표화면 ctid
	 */
	private String XML_NODE_RPIMGCTID = "RPIMG_CT_ID";
	/**
	 * 대표화면 키프레임 순번
	 */
	private String XML_NODE_RPIMGKFRMSEQ = "RPIMG_KFRM_SEQ";

	public Object setDO(String _xml) {
		setDO(new KeyFrameImgDO());

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
		KeyFrameImgDO infoDO = (KeyFrameImgDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_RPIMGCTID)) {
				infoDO.setRpimgCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_RPIMGKFRMSEQ)) {
				infoDO.setRpimgKfrmSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
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
		KeyFrameImgDO infoDO = (KeyFrameImgDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_RPIMGCTID + ">" + infoDO.getRpimgCtId() + "</"  + XML_NODE_RPIMGCTID + ">");
		_xml.append("<" + XML_NODE_RPIMGKFRMSEQ + ">" + infoDO.getRpimgKfrmSeq() + "</"  + XML_NODE_RPIMGKFRMSEQ + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

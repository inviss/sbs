package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.VideoPageMetaInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  비디오 메타  정보 관련 XML파서
 * @author asura207
 *
 */
public class VideoPageMetaInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "videoPageMetaInfo";
	/**
	 * 제목
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRDDD = "BRD_DD";
	/**
	 * 최종화여부
	 */
	private String XML_NODE_FINALBRDYN = "FINAL_BRD_YN"; 
	/**
	 * 대분류코드
	 */
	private String XML_NODE_CATEGORYLARGE = "CTGR_L_CD"; 
	/**
	 * 중분류코드
	 */
	private String XML_NODE_CATEGORYMEDIUM = "CTGR_M_CD";
	/**
	 * 소분류코드
	 */
	private String XML_NODE_CATEGORYSMALL = "CTGR_S_CD"; 
	/**
	 * 회차번호
	 */
	private String XML_NODE_EPISODENUMBER = "EPIS_NO"; 
	/**
	 * 청구번호
	 */
	private String XML_NODE_REQUESTDATE = "REQ_CD"; 



	public Object setDO(String _xml) {
		setDO(new VideoPageMetaInfoDO());

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
		VideoPageMetaInfoDO infoDO = (VideoPageMetaInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRDDD)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FINALBRDYN)) {
				infoDO.setFinalBrdYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYLARGE)) {
				infoDO.setCtgrCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYMEDIUM)) {
				infoDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYSMALL)) {
				infoDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPISODENUMBER)) {
				infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_REQUESTDATE)) {
				infoDO.setReqCd(_nodeValue);
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
		VideoPageMetaInfoDO infoDO = (VideoPageMetaInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_BRDDD + ">" + infoDO.getBrdDd() + "</"  + XML_NODE_BRDDD + ">");
		_xml.append("<" + XML_NODE_FINALBRDYN + ">" + infoDO.getFinalBrdYn() + "</"  + XML_NODE_FINALBRDYN + ">");
		_xml.append("<" + XML_NODE_CATEGORYLARGE + ">" + infoDO.getCtgrLCd() + "</"  + XML_NODE_CATEGORYLARGE + ">");
		_xml.append("<" + XML_NODE_CATEGORYMEDIUM + ">" + infoDO.getCtgrMCd() + "</"  + XML_NODE_CATEGORYMEDIUM + ">");
		_xml.append("<" + XML_NODE_CATEGORYSMALL + ">" + infoDO.getCtgrSCd() + "</"  + XML_NODE_CATEGORYSMALL + ">");
		_xml.append("<" + XML_NODE_EPISODENUMBER + ">" + infoDO.getEpisNo() + "</"  + XML_NODE_EPISODENUMBER + ">");
		_xml.append("<" + XML_NODE_REQUESTDATE + ">" + infoDO.getReqCd() + "</"  +XML_NODE_REQUESTDATE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

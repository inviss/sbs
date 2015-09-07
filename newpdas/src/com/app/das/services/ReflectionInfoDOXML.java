package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ReflectionInfoDO;
import com.app.das.util.CommonUtl;
/**
 *   반영 정보 관련 XML파서
 * @author asura207
 *
 */ 
public class ReflectionInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD = "reflectionInfo";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_PROGRAMNAME = "PGM_NM";
	/**
	 * 타이틀
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 방송일
	 */
	private String XML_NODE_BROADCASTDD = "BRD_DD";
	/**
	 * 최종방송여부
	 */
	private String XML_NODE_FINALBRDYN = "FINAL_BRD_YN"; 
	/**
	 * 방송시작시간
	 */
	private String XML_NODE_BROADBEGINDD = "BRD_BGN_DD"; 
	/**
	 * 방송종료시간
	 */
	private String XML_NODE_BROADENDDD = "BRD_END_DD"; 
	/**
	 * 테이프매체종류코드
	 */
	private String XML_NODE_TAPEMEDIACLFCODE = "TAPE_MEDIA_CLF_CD"; 
	/**
	 * 대분류코드
	 */
	private String XML_NODE_CATEGORYLARGECODE = "CTGR_L_CD"; 
	/**
	 * 중분류코드
	 */
	private String XML_NODE_CATEGORYMIDDLECODE = "CTGR_M_CD"; 
	/**
	 * 소분류코드
	 */
	private String XML_NODE_CATEGORYSMALLCODE = "CTGR_S_CD"; 
	/**
	 * 청구번호코드
	 */
	private String XML_NODE_REQUESTCODE = "REQ_CD"; 
	/**
	 * 콘텐츠ID
	 */
	private String XML_NODE_CONTENTSID = "CT_ID"; 
	/**
	 * 종횡비코드
	 */
	private String XML_NODE_ASPRTOCODE = "ASP_RTO_CD"; 
	/**
	 * 화질코드
	 */
	private String XML_NODE_VIDEOQUALITY = "VD_QLTY"; 
	/**
	 * 회차
	 */
	private String XML_NODE_EPISODENUMBER = "EPIS_NO"; 
	/**
	 * 콘텐츠길이
	 */
	private String XML_NODE_CONTENTSLENGTH = "CT_LENG"; 
	/**
	 * 키프레임경로
	 */
	private String XML_NODE_KEYFRAMEPATH = "KFRM_PATH"; 
	/**
	 * 파일경
	 */
	private String XML_NODE_FILEPATH = "FL_PATH"; 

	public Object setDO(String _xml) {
		setDO(new ReflectionInfoDO());

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
		ReflectionInfoDO infoDO = (ReflectionInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
				infoDO.setPgmNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BROADCASTDD)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FINALBRDYN)) {
				infoDO.setFinalBrdYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BROADBEGINDD)) {
				infoDO.setBrdBgnDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BROADENDDD)) {
				infoDO.setBrdEndDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPEMEDIACLFCODE)) {
				infoDO.setTapeMediaClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYLARGECODE)) {
				infoDO.setCtgrLCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYMIDDLECODE)) {
				infoDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYSMALLCODE)) {
				infoDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQUESTCODE)) {
				infoDO.setReqCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				infoDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ASPRTOCODE)) {
				infoDO.setAspRtoCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VIDEOQUALITY)) {
				infoDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPISODENUMBER)) {
				infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSLENGTH)) {
				infoDO.setCtLeng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_KEYFRAMEPATH)) {
				infoDO.setKfrmPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILEPATH)) {
				infoDO.setFlPath(_nodeValue);
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
		ReflectionInfoDO infoDO = (ReflectionInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PROGRAMNAME + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_BROADCASTDD + ">" + infoDO.getBrdDd() + "</"  + XML_NODE_BROADCASTDD + ">");
		_xml.append("<" + XML_NODE_FINALBRDYN + ">" + infoDO.getFinalBrdYn() + "</"  + XML_NODE_FINALBRDYN + ">");
		_xml.append("<" + XML_NODE_BROADBEGINDD + ">" + infoDO.getBrdBgnDd() + "</"  + XML_NODE_BROADBEGINDD + ">");
		_xml.append("<" + XML_NODE_BROADENDDD + ">" + infoDO.getBrdEndDd() + "</"  + XML_NODE_BROADENDDD + ">");
		_xml.append("<" + XML_NODE_TAPEMEDIACLFCODE + ">" + infoDO.getTapeMediaClfCd() + "</"  + XML_NODE_TAPEMEDIACLFCODE + ">");
		_xml.append("<" + XML_NODE_CATEGORYLARGECODE + ">" + infoDO.getCtgrLCd() + "</"  + XML_NODE_CATEGORYLARGECODE + ">");
		_xml.append("<" + XML_NODE_CATEGORYMIDDLECODE + ">" + infoDO.getCtgrMCd() + "</"  + XML_NODE_CATEGORYMIDDLECODE + ">");
		_xml.append("<" + XML_NODE_CATEGORYSMALLCODE + ">" + infoDO.getCtgrSCd() + "</"  + XML_NODE_CATEGORYSMALLCODE + ">");
		_xml.append("<" + XML_NODE_REQUESTCODE + ">" + infoDO.getReqCd() + "</"  + XML_NODE_REQUESTCODE + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + infoDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_ASPRTOCODE + ">" + infoDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE + ">");
		_xml.append("<" + XML_NODE_VIDEOQUALITY + ">" + infoDO.getVdQlty() + "</"  + XML_NODE_VIDEOQUALITY + ">");
		_xml.append("<" + XML_NODE_EPISODENUMBER + ">" + infoDO.getEpisNo() + "</"  + XML_NODE_EPISODENUMBER + ">");
		_xml.append("<" + XML_NODE_CONTENTSLENGTH + ">" + infoDO.getCtLeng() + "</"  + XML_NODE_CONTENTSLENGTH + ">");
		_xml.append("<" + XML_NODE_KEYFRAMEPATH + ">" + infoDO.getKfrmPath() + "</"  + XML_NODE_KEYFRAMEPATH + ">");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + infoDO.getFlPath() + "</"  + XML_NODE_FILEPATH + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TapeItemInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  TapeItem 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class TapeItemInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "attachFileInfo";
	/**
	 * 테이프식별자ID
	 */
	private String XML_NODE_TAPEID = "TAPE_ID";
	/**
	 * 청구번호
	 */
	private String XML_NODE_REQUESTNO = "REQ_NO";
	/**
	 * 장면제목(프로그램명)
	 */
	private String XML_NODE_SCEENTITLE = "SCN_TTL";
	/**
	 * 테이프아이템식별자ID
	 */
	private String XML_NODE_TAPLEITEMID = "TAPE_ITEM_ID";
	/**
	 * 길이(방송길이)
	 */
	private String XML_NODE_LENGTH = "LEN";
	/**
	 * 에피소드번호
	 */
	private String XML_NODE_EPISODNO = "EPIS_NO";
	/**
	 * 방송일자(연월일)
	 */
	private String XML_NODE_BROADCASRDATE = "BRD_DD";
	/**
	 * 촬영자
	 */
	private String XML_NODE_CMRMAN = "CMR_MAN";
	/**
	 * 촬영장소
	 */
	private String XML_NODE_CMRPLACE = "CMR_PLACE";
	/**
	 * 촬영일자(연월일)
	 */
	private String XML_NODE_CMRDATE = "CMR_DD"; 
	/**
	 * 키워드
	 */
	private String XML_NODE_KEYWORDS = "KEY_WORDS";
	/**
	 * 작업순서
	 */
	private String XML_NODE_WORKSEQUENCE = "WORK_SEQ";
	/**
	 * 색상코드
	 */
	private String XML_NODE_COLORCODE = "COLOR_CD"; 
	/**
	 * 녹음방식코드
	 */
	private String XML_NODE_RECORDTYPECODE = "RECORD_TYPE_CD";
	/**
	 * ME분리코드
	 */
	private String XML_NODE_MECD = "ME_CD";
	/**
	 * 인제스트상태
	 */
	private String XML_NODE_INGESTSTATUS = "INGEST_STATUS";
	/**
	 * 부제
	 */
	private String XML_SUBTITLE = "SUB_TTL";
	/**
	 * 제작자
	 */
	private String XML_PRODUSER = "PRDTR";


	//2012.5.16
	/**
	 * 회사코드
	 */
	private String XML_COCD = "COCD";
	/**
	 * 채널 코드
	 */
	private String XML_CHENNEL = "CHENNEL_CD";

	public Object setDO(String _xml) {
		setDO(new TapeItemInfoDO());

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
		TapeItemInfoDO infoDO = (TapeItemInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_TAPEID)) {
				infoDO.setTapeId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQUESTNO)) {
				infoDO.setReqNo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SCEENTITLE)) {
				infoDO.setScnTtl(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LENGTH)) {
				infoDO.setLen(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_EPISODNO)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_BROADCASRDATE)) {
				infoDO.setBrdDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMRMAN)) {
				infoDO.setCmrMan(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMRPLACE)) {
				infoDO.setCmrPlace(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMRDATE)) {
				infoDO.setCmrDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_KEYWORDS)) {
				infoDO.setKeyWord(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WORKSEQUENCE)) {
				infoDO.setWorkSeq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COLORCODE)) {
				infoDO.setColorCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECORDTYPECODE)) {
				infoDO.setRecordTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MECD)) {
				infoDO.setMeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_INGESTSTATUS)) {
				infoDO.setIngestStatus(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPLEITEMID)) {
				infoDO.setTapeItemId(_nodeValue);
			}
			else if(_nodeName.equals(XML_SUBTITLE)) {
				infoDO.setSubTtl(_nodeValue);
			}
			else if(_nodeName.equals(XML_PRODUSER)) {
				infoDO.setPrdtr(_nodeValue);
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
		TapeItemInfoDO infoDO = (TapeItemInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_TAPEID + ">" + infoDO.getTapeId() + "</"  +  XML_NODE_TAPEID + ">");
		_xml.append("<" + XML_NODE_REQUESTNO + ">" + infoDO.getReqNo() + "</"  + XML_NODE_REQUESTNO + ">");
		_xml.append("<" + XML_NODE_SCEENTITLE + ">" + CommonUtl.transXmlText(infoDO.getScnTtl()) + "</"  + XML_NODE_SCEENTITLE + ">");
		_xml.append("<" + XML_NODE_LENGTH + ">" + infoDO.getLen() + "</"  + XML_NODE_LENGTH + ">");
		_xml.append("<" + XML_NODE_TAPLEITEMID + ">" + infoDO.getTapeItemId() + "</"  + XML_NODE_TAPLEITEMID + ">");
		_xml.append("<" + XML_NODE_EPISODNO + ">" + infoDO.getEpisNo() + "</"  + XML_NODE_EPISODNO + ">");
		_xml.append("<" + XML_NODE_BROADCASRDATE + ">" + infoDO.getBrdDd() + "</"  + XML_NODE_BROADCASRDATE + ">");
		_xml.append("<" + XML_NODE_CMRMAN + ">" + CommonUtl.transXmlText(infoDO.getCmrMan()) + "</"  + XML_NODE_CMRMAN + ">");
		_xml.append("<" + XML_NODE_CMRPLACE + ">" + CommonUtl.transXmlText(infoDO.getCmrPlace()) + "</"  + XML_NODE_CMRPLACE + ">");
		_xml.append("<" + XML_NODE_CMRDATE + ">" + infoDO.getCmrDd() + "</"  + XML_NODE_CMRDATE + ">");
		_xml.append("<" + XML_NODE_KEYWORDS + ">" + CommonUtl.transXmlText(infoDO.getKeyWord()) + "</"  + XML_NODE_KEYWORDS + ">");
		_xml.append("<" + XML_NODE_WORKSEQUENCE + ">" + infoDO.getWorkSeq() + "</"  + XML_NODE_WORKSEQUENCE + ">");
		_xml.append("<" + XML_NODE_COLORCODE + ">" + infoDO.getColorCd() + "</"  + XML_NODE_COLORCODE + ">");
		_xml.append("<" + XML_NODE_RECORDTYPECODE + ">" + infoDO.getRecordTypeCd() + "</"  + XML_NODE_RECORDTYPECODE + ">");
		_xml.append("<" + XML_NODE_MECD + ">" + infoDO.getMeCd() + "</"  + XML_NODE_MECD + ">");
		_xml.append("<" + XML_NODE_INGESTSTATUS + ">" + infoDO.getIngestStatus() + "</"  + XML_NODE_INGESTSTATUS + ">");
		_xml.append("<" + XML_SUBTITLE + ">" + CommonUtl.transXmlText(infoDO.getSubTtl()) + "</"  + XML_SUBTITLE + ">");
		_xml.append("<" + XML_PRODUSER + ">" + CommonUtl.transXmlText(infoDO.getPrdtr()) + "</"  + XML_PRODUSER + ">");
		_xml.append("<" + XML_COCD + ">S</"  + XML_COCD + ">");
		_xml.append("<" + XML_CHENNEL + ">A</"  + XML_CHENNEL + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

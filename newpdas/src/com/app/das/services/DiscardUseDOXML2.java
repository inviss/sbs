package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DiscardDO;
import com.app.das.util.CommonUtl;

/**
 *  연장 정보 관련 XML파서(멀티저장)
 * @author asura207
 *
 */
public class DiscardUseDOXML2 extends DOXml {

	DiscardDO infoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "discarduse";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE = "title";	
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 연장사유
	 */
	private String XML_NODE_USE_CONT = "disuse_cont";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt";	
	/**
	 * 보존기간 만료일
	 */
	private String XML_NODE_RSV_PRD_END_DD = "rsv_prd_end_dd";
	/**
	 * 보존기간 시작일
	 */
	private String XML_NODE_RSV_PRD_START_DD = "rsv_prd_start_dd";
	/**
	 * 보존기간코드
	 */
	private String XML_NODE_RSV_PRD_CD = "rsv_prd_cd";
	/**
	 * 이전 보존기간 만료일
	 */
	private String XML_NODE_PRE_RSV_PRD_CD = "pre_rsv_prd_cd";
	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 총합
	 */
	private String XML_NODE_TOTAL_COUNT = "total_count";
	/**
	 * 총방송길이
	 */
	private String XML_NODE_SUM_BRD_LENG = "sum_brd_leng";
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID = "media_id";
	/**
	 * 연장 사유
	 */
	private String XML_NODE_USE_COUNT = "use_count";


	public Object setDO(String _xml) {
		setDO(new DiscardDO());

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
		DiscardDO discard = (DiscardDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_TITLE)) {
				discard.setTitle(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				discard.setBrd_len(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_CONT)) {
				discard.setUse_cont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				discard.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_END_DD)) {
				discard.setRsv_prd_end_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_CD)) {
				discard.setRsv_prd_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRE_RSV_PRD_CD)) {
				discard.setPre_rsv_prd_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				discard.setMaster_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}

			else if(_nodeName.equals(XML_NODE_SEQ)) {
				discard.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_RSV_PRD_START_DD)) {
				discard.setRsv_prd_start_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				discard.setMedia_id(_nodeValue);
			}

		}

		return discard;
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
		DiscardDO discard = (DiscardDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_SEQ + ">" + discard.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(discard.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + discard.getBrd_len() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_USE_CONT + ">" + CommonUtl.transXmlText(discard.getUse_cont()) + "</"  + XML_NODE_USE_CONT + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + discard.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_END_DD + ">" + discard.getRsv_prd_end_dd() + "</"  + XML_NODE_RSV_PRD_END_DD + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_CD + ">" + discard.getRsv_prd_cd() + "</"  + XML_NODE_RSV_PRD_CD + "> \n");
		_xml.append("<" + XML_NODE_PRE_RSV_PRD_CD + ">" + discard.getPre_rsv_prd_cd() + "</"  + XML_NODE_PRE_RSV_PRD_CD + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + discard.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + discard.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_USE_COUNT + ">" + discard.getUse_count() + "</"  + XML_NODE_USE_COUNT + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

	public String getSubXML2() {
		DiscardDO discard = (DiscardDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<totalinfo>\n");
		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + discard.getTotalcount() + "</"  + XML_NODE_TOTAL_COUNT + "> \n");
		_xml.append("<" + XML_NODE_SUM_BRD_LENG + ">" + discard.getBrd_leng_sum() + "</"  + XML_NODE_SUM_BRD_LENG + "> \n");
		_xml.append("</totalinfo>");

		return _xml.toString();
	}

}

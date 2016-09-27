package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DiscardDO;
import com.app.das.util.CommonUtl;

/**
 *  폐기 등록 정보 관련 XML파서
 * @author asura207
 *
 */
public class DiscardregiDOXML extends DOXml {


	DiscardDO infoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "discardregi";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 폐기 사유
	 */
	private String XML_NODE_DISUSE_CONT = "disuse_cont";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt";
	/**
	 * 폐기신청자
	 */
	private String XML_NODE_REG_ID = "reg_id";
	/**
	 * 보존기간 코드
	 */
	private String XML_NODE_RSV_PRD_END_DD = "rsv_prd_end_dd";
	/**
	 * 보존기간 종료일(검색조건)
	 */
	private String XML_NODE_RSV_PRD_START_DD = "rsv_prd_start_dd";

	/**
	 * 폐기종료일
	 */
	private String XML_NODE_DISUSE_END = "disuse_end";
	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 폐기상태
	 */
	private String XML_NODE_DISUSE_STA = "disuse_sta";
	/**
	 * 총합
	 */
	private String XML_NODE_TOTAL_COUNT = "total_count";

	
	
	/*public Object setDO(String _xml) {
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
	}*/
	
	
	
	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
        	infoDO = new DiscardDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
		
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
				result.add(infoDO);
			}
        }
		return getDO();
	}
	
	
	
	public Object setData(Element pElement) {
		//DiscardDO discard = (DiscardDO)getDO();
		List result = (List)getDO();
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			 if(_nodeName.equals(XML_NODE_TITLE)) {
				 infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				infoDO.setBrd_len(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DISUSE_CONT)) {
				infoDO.setDisuse_cont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_ID)) {
				infoDO.setReg_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_END_DD)) {
				infoDO.setRsv_prd_end_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_START_DD)) {
				infoDO.setRsv_prd_start_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DISUSE_END)) {
				infoDO.setDisuse_end_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			
        }
	    
	    return result;
	}
	
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}

	public String getSubXML() {
		DiscardDO discard = (DiscardDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(discard.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + discard.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + discard.getBrd_len() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_DISUSE_CONT + ">" + CommonUtl.transXmlText(discard.getDisuse_cont()) + "</"  + XML_NODE_DISUSE_CONT + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + discard.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");	
		_xml.append("<" + XML_NODE_RSV_PRD_END_DD + ">" + discard.getRsv_prd_end_dd() + "</"  + XML_NODE_RSV_PRD_END_DD + "> \n");
		_xml.append("<" + XML_NODE_DISUSE_END + ">" + discard.getDisuse_end_dd() + "</"  + XML_NODE_DISUSE_END + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + discard.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_SEQ + ">" + discard.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_DISUSE_STA + ">" + discard.getDisuse_sta() + "</"  + XML_NODE_DISUSE_STA + "> \n");
		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + discard.getTotalcount() + "</"  + XML_NODE_TOTAL_COUNT + "> \n");
		_xml.append("<" + XML_NODE_REG_ID + ">" + discard.getReg_id() + "</"  + XML_NODE_REG_ID + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	
	
}

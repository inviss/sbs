package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TodayDO;
import com.app.das.util.CommonUtl;


/**
 *  오늘의 영상 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class TodayDOXML extends DOXml {


	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "today";
	/**
	 * 프로그램 제목
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
	 * 상태
	 */
	private String XML_NODE_ARCH_STAT = "arch_stat";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt"; 
	/**
	 * 아카이브여부
	 */
	private String XML_NODE_ARCH_YN = "arch_yn";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 클립길이
	 */
	private String XML_NODE_CT_LENG = "ct_leng"; 
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "epis_no";
	/**
	 * 사용등급
	 */
	private String XML_NODE_LIMIT_USE = "limit_use";
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";



	public Object setDO(String _xml) {
		setDO(new TodayDO());

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
		TodayDO infoDO = new TodayDO();
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
				infoDO.setBrd_leng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCH_STAT)) {

				infoDO.setArch_stat(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCH_YN)) {
				infoDO.setArch_yn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}



		}

		return infoDO;
	}


	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>  \n");
		_xml.append("<das> \n");
		_xml.append(getSubXML());
		_xml.append(" \n </das>");

		return _xml.toString();
	}

	public String getSubXML() {
		TodayDO infoDO = (TodayDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_ARCH_STAT + ">" + infoDO.getArch_stat() + "</"  + XML_NODE_ARCH_STAT + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt()+ "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_ARCH_YN + ">" + infoDO.getArch_yn() + "</"  + XML_NODE_ARCH_YN + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_CT_LENG + ">" + infoDO.getCt_leng()+ "</"  + XML_NODE_CT_LENG + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_LIMIT_USE + ">" + infoDO.getLimit_use() + "</"  + XML_NODE_LIMIT_USE + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_Cd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");

		_xml.append(" \n</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

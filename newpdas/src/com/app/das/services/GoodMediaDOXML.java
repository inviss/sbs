package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.GoodMediaDO;
import com.app.das.util.CommonUtl;
/**
 *  명장면 정보 관련 XML파서
 * @author asura207
 *
 */
public class GoodMediaDOXML extends DOXml{
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "good";
	/**
	 * 마스터 id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 영상 id
	 */
	private String XML_NODE_CT_ID = "ct_id";
	/**
	 * 주석구분코드내용
	 */
	private String XML_NODE_ANNOT_CLF_CONT = "annot_clf_cont";
	/**
	 * 제목
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 방송일/촬영일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 컨텐츠 길이
	 */
	private String XML_NODE_CT_LENG = "ct_leng";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "epis_no";
	/**
	 * 시작점
	 */
	private String XML_NODE_SOM = "som";
	/**
	 * 종료점
	 */
	private String XML_NODE_EOM = "eom";
	/**
	 * 내용
	 */
	private String XML_NODE_CONT = "cont";
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
	/**
	 * 주제영상 구분
	 */
	private String XML_NODE_ANNOT_CLF_NM = "annot_clf_nm";
	/**
	 * 아카이브 여부
	 */
	private String XML_NODE_ARCH_REG_DD = "arch_reg_dd";



	public Object setDO(String _xml) {
		setDO(new GoodMediaDO());

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
		GoodMediaDO infoDO = (GoodMediaDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals( XML_NODE_ANNOT_CLF_CONT)) {
				infoDO.setAnnot_clf_cont(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {
				infoDO.setCtgr_l_cd(_nodeValue);
			}
		}



		return infoDO;
	}	    

	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}

	/*	public String getSubXML() {
		GoodMediaDO infoDO = (GoodMediaDO)getDO();

		String _xml = "<" + XML_NODE_HEAD + "> \n";
		_xml = _xml + "<" + XML_NODE_MASTER_ID+ ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n";
		_xml = _xml + "<" + XML_NODE_ANNOT_CLF_CONT + ">" + infoDO.getAnnot_clf_cont() + "</"  + XML_NODE_ANNOT_CLF_CONT + "> \n";
			_xml = _xml + "<" + XML_NODE_TITLE + ">" + infoDO.getTitle() + "</"  + XML_NODE_TITLE + "> \n";
		_xml = _xml + "<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n";
		_xml = _xml + "<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n";
		_xml = _xml + "<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n";

		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return _xml;
	}
	 */


	public String getSubXML() {
		GoodMediaDO infoDO = (GoodMediaDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID+ ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_ANNOT_CLF_CONT + ">" + CommonUtl.transXmlText(infoDO.getAnnot_clf_cont()) + "</"  + XML_NODE_ANNOT_CLF_CONT + "> \n");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + "> \n");
		_xml.append("<" + XML_NODE_SOM + ">" + infoDO.getSom() + "</"  + XML_NODE_SOM + "> \n");
		_xml.append("<" + XML_NODE_EOM + ">" + infoDO.getEom() + "</"  + XML_NODE_EOM + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_CT_LENG + ">" + infoDO.getCt_leng() + "</"  + XML_NODE_CT_LENG + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_CONT + ">" + infoDO.getCont() + "</"  + XML_NODE_CONT + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");
		_xml.append("<" + XML_NODE_ANNOT_CLF_NM + ">" + CommonUtl.transXmlText(infoDO.getAnnot_clf_nm()) + "</"  + XML_NODE_ANNOT_CLF_NM + "> \n");
		_xml.append("<" + XML_NODE_ARCH_REG_DD + ">" + infoDO.getArch_reg_dd() + "</"  + XML_NODE_ARCH_REG_DD + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();

	}
}

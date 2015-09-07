package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.business.transfer.RelationDO;
import com.app.das.util.CommonUtl;
/**
 *  관련영상  정보 관련 XML파서
 * @author asura207
 *
 */
public class RelationDOXML extends DOXml{
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD = "relinfo";
	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 구분
	 */
	private String XML_NODE_CTGR_L_NM = "ctgr_l_cd";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 장르
	 */
	private String XML_NODE_CTGR_MS_NM = "ctgr_ms_nm";
	/**
	 * 화질
	 */
	private String XML_NODE_VD_QLTY = "vf_qlty";
	/**
	 * 길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";	




	public Object setDO(String _xml) {
		setDO(new RelationDO());

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
		RelationDO info = (RelationDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				info.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				info.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				info.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_NM)) {
				info.setCtgr_l_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_MS_NM)) {
				info.setCtgr_ms_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_QLTY)) {
				info.setVd_qlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				info.setBrd_leng(_nodeValue);
			}


		}

		return info;
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
		RelationDO info = (RelationDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");

		_xml.append("<" + XML_NODE_MASTER_ID + ">" + info.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(info.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + info.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_NM + ">" + info.getCtgr_l_nm() + "</"  + XML_NODE_CTGR_L_NM + "> \n");
		_xml.append("<" + XML_NODE_CTGR_MS_NM + ">" + info.getCtgr_ms_nm() + "</"  + XML_NODE_CTGR_MS_NM + "> \n");		
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + info.getVd_qlty() + "</"  + XML_NODE_VD_QLTY + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + info.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.*;

import com.app.das.business.transfer.AchiveManagerSystemDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.PhotDownDO;
import com.app.das.util.CommonUtl;


/**
 *  사진 다운로드 정보 관련 XML파서
 * @author asura207
 *
 */
public class PhotDownDOXML extends DOXml {

	private static Logger logger = Logger.getLogger(PhotDownDOXML.class);
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "photdown";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 사진ID
	 */
	private String XML_NODE_PHOT_ID = "phto_id";
	/**
	 * 마스터ID
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 등록ID
	 */
	private String XML_NODE_REQ_ID = "req_id";
	/**
	 * 등록일
	 */
	private String XML_NODE_REQ_DT = "req_dt"; 
	/**
	 * 대분류코드
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
	/**
	 * 중분류코드
	 */
	private String XML_NODE_CTGR_M_CD = "ctgr_m_cd";
	/**
	 * 소분류코드
	 */
	private String XML_NODE_CTGR_S_CD = "ctgr_s_cd";


	public Object setDO(String _xml) {
		setDO(new PhotDownDO());

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
		PhotDownDO infoDO = new PhotDownDO();
		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Long.parseLong(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_PHOT_ID)) {
				infoDO.setPhot_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_REQ_ID)) {

				infoDO.setReq_id(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REQ_DT)) {
				infoDO.setReq_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {
				infoDO.setCtgr_l_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_M_CD)) {

				infoDO.setCtgr_m_cd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CTGR_S_CD)) {

				infoDO.setCtgr_s_cd(_nodeValue);

			}



		}

		return infoDO;
	}


	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		PhotDownDO infoDO = (PhotDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_PHOT_ID + ">" + infoDO.getPhot_id() + "</"  + XML_NODE_PHOT_ID + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_REQ_ID + ">" + infoDO.getReq_id() + "</"  + XML_NODE_REQ_ID + "> \n");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt()+ "</"  + XML_NODE_REQ_DT + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_M_CD + ">" + infoDO.getCtgr_m_cd() + "</"  + XML_NODE_CTGR_M_CD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_S_CD + ">" + infoDO.getCtgr_s_cd() + "</"  + XML_NODE_CTGR_S_CD + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

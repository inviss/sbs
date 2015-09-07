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
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.util.CommonUtl;


/**
 *  내목록 정보 관련 XML파서
 * @author asura207
 *
 */
public class MyCatalogDOXML extends DOXml {

	private static Logger logger = Logger.getLogger(MyCatalogDOXML.class);
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "mycatal";
	/**
	 * 구분
	 */
	private String XML_NODE_GUBUN = "gubun";
	/**
	 * 프로그램제목
	 */
	private String XML_NODE_PGM_NM = "pgm_nm";
	/**
	 * 회차
	 */
	private String XML_NODE_ESIP = "esn";
	/**
	 * 방송일자
	 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ= "seq";



	public Object setDO(String _xml) {
		setDO(new MyCatalogDO());

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
		MyCatalogDO infoDO = new MyCatalogDO();
		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGM_NM)) {
				infoDO.setPgmNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ESIP)) {
				if(!_nodeValue.equals("")){
					infoDO.setEpn(_nodeValue);
				}else{
					infoDO.setEpn("0");	
				}
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {

				infoDO.setBrdDd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}



		}

		return infoDO;
	}


	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		MyCatalogDO infoDO = (MyCatalogDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun()+ "</"  + XML_NODE_GUBUN + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PGM_NM + ">");
		_xml.append("<" + XML_NODE_ESIP + ">" + infoDO.getEpn() + "</"  + XML_NODE_ESIP + ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrdDd() + "</"  + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq()+ "</"  + XML_NODE_SEQ + ">");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

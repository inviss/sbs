package com.app.das.services;

import java.util.ArrayList;
import java.util.List;
import com.app.das.util.CommonUtl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AnnotInfoDO;
import com.app.das.business.transfer.ErrorRegisterDO;
import com.app.das.business.transfer.ScenarioDO;
/**
 *   대본 정보 관련 XML파서
 * @author asura207
 *
 */
public class ScenarioDOXML extends DOXml {

	private static Logger logger = Logger.getLogger(ScenarioDOXML.class);

	private String XML_NODE_HEAD = "scenario";

	private String XML_NODE_MASTER_ID = "MASTER_ID";
	private String XML_NODE_TITLE ="TITLE";
	private String XML_NODE_DESC ="DESC";
	private String XML_NODE_REGDT = "REGDT";
	private String XML_NODE_SEQ = "SEQ";
	private String XML_NODE_TOTALCOUNT = "TOTALCOUNT";


	public Object setDO(String _xml) {
		setDO(new ScenarioDO());

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
		ScenarioDO infoDO = (ScenarioDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_TITLE)){
				//String kk="";	

				//kk=  _nodeValue.replaceAll("<", "&lt;");

				//kk= kk.replaceAll(">", "&gt;");

				//kk= kk.replaceAll("&", "&amp;");

				//infoDO.setDesc(_nodeValue);

				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESC)){
				//String kk="";	
				//kk=  _nodeValue.replaceAll("<", "&lt;");

				//kk= kk.replaceAll(">", "&gt;");

				//kk= kk.replaceAll("&", "&amp;");

				infoDO.setDesc(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_SEQ)){
				if(_nodeValue.equals("0")||_nodeValue.equals("")){
					infoDO.setSeq(1);
				}else {
					infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));	
				}

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
		ScenarioDO infoDO = (ScenarioDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTER_ID + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(CommonUtl.reviveScerino(infoDO.getTitle())) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_DESC + ">" + CommonUtl.transXmlText(CommonUtl.reviveScerino(infoDO.getDesc())) + "</"  + XML_NODE_DESC + ">");
		_xml.append("<" + XML_NODE_REGDT + ">" + infoDO.getRegdt() + "</"  + XML_NODE_REGDT + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

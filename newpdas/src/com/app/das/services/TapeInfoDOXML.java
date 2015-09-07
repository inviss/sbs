package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  TapeInfo 정보  정보 관련 XML파서
 * @author asura207
 *
 */
public class TapeInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "tapeInfo";

	//private String XML_NODE_BROADBEGINDATE = "BRD_BGN_DD";
	//private String XML_NODE_BROADENDDATE = "BRD_END_DD";
	/**
	 * 시청등급코드
	 */
	private String XML_NODE_VIEWGRCODE = "VIEW_GR_CD";
	/**
	 * 시청율
	 */
	private String XML_NODE_PROGRAMRATE = "PGM_RATE"; 
	/**
	 * 심의결과코드
	 */
	private String XML_NODE_DLBRCODE = "DLBR_CD"; 
	/**
	 * 저작권자명
	 */
	private String XML_NODE_CPRTRNAME = "CPRTR_NM"; 
	/**
	 * 저작권형태코드
	 */
	private String XML_NODE_CPRTTYPE = "CPRT_TYPE"; 
	/**
	 * 저작권형태설
	 */
	private String XML_NODE_CPRTTYPEDESC = "CPRT_TYPE_DSC"; 



	public Object setDO(String _xml) {
		setDO(new TapeInfoDO());

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
		TapeInfoDO infoDO = (TapeInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			//			if(_nodeName.equals(XML_NODE_BROADBEGINDATE)) {
			//				infoDO.setBrdBgnDd(_nodeValue);
			//			}
			//			else if(_nodeName.equals(XML_NODE_BROADENDDATE)) {
			//				infoDO.setBrdEndDd(_nodeValue);
			//			}
			if(_nodeName.equals(XML_NODE_VIEWGRCODE)) {
				infoDO.setViewGrCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMRATE)) {
				infoDO.setPgmRate(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DLBRCODE)) {
				infoDO.setDlbrCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRTRNAME)) {
				infoDO.setCprtrNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRTTYPE)) {
				infoDO.setCprtType(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRTTYPEDESC)) {
				infoDO.setCprtTypeDsc(_nodeValue);
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
		TapeInfoDO infoDO = (TapeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		//	_xml = _xml + "<" + XML_NODE_BROADBEGINDATE + ">" + infoDO.getBrdBgnDd() + "</"  + XML_NODE_BROADBEGINDATE + ">";
		//	_xml = _xml + "<" + XML_NODE_BROADENDDATE + ">" + infoDO.getBrdEndDd() + "</"  + XML_NODE_BROADENDDATE + ">";
		_xml.append("<" + XML_NODE_VIEWGRCODE + ">" + infoDO.getViewGrCd() + "</"  + XML_NODE_VIEWGRCODE + ">");
		_xml.append("<" + XML_NODE_PROGRAMRATE + ">" + infoDO.getPgmRate() + "</"  + XML_NODE_PROGRAMRATE + ">");
		_xml.append("<" + XML_NODE_DLBRCODE + ">" + infoDO.getDlbrCd() + "</"  + XML_NODE_DLBRCODE + ">");
		_xml.append("<" + XML_NODE_CPRTRNAME + ">" + CommonUtl.transXmlText(infoDO.getCprtrNm()) + "</"  + XML_NODE_CPRTRNAME + ">");
		_xml.append("<" + XML_NODE_CPRTTYPE + ">" + CommonUtl.transXmlText(infoDO.getCprtType()) + "</"  + XML_NODE_CPRTTYPE + ">");
		_xml.append("<" + XML_NODE_CPRTTYPEDESC + ">" + CommonUtl.transXmlText(infoDO.getCprtTypeDsc()) + "</"  + XML_NODE_CPRTTYPEDESC + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

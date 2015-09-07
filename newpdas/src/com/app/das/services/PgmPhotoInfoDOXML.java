package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PgmPhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  프로그램별 사진 정보 관련 XML파서
 * @author asura207
 *
 */
public class PgmPhotoInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "pgmPhotoInfo";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID";
	/**
	 * 사진 ID
	 */
	private String XML_NODE_PHOTOID = "PHOT_ID";
	/**
	 * 시작회차
	 */
	private String XML_NODE_BEGINEEPN = "BGN_EPN";
	/**
	 * 끝회차
	 */
	private String XML_NODE_ENDEPN = "END_EPN"; 
	/**
	 * 등록일
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";

	public Object setDO(String _xml) {
		setDO(new PgmPhotoInfoDO());

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
		PgmPhotoInfoDO infoDO = (PgmPhotoInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_PHOTOID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPhotId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_BEGINEEPN)) {
				infoDO.setBgnEpn(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_ENDEPN)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEndEpn(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
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
		PgmPhotoInfoDO infoDO = (PgmPhotoInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + "> \n");
		_xml.append("<" + XML_NODE_PHOTOID + ">" + infoDO.getPhotId() + "</"  + XML_NODE_PHOTOID + "> \n");
		_xml.append("<" + XML_NODE_BEGINEEPN + ">" + infoDO.getBgnEpn() + "</"  + XML_NODE_BEGINEEPN + "> \n");
		_xml.append("<" + XML_NODE_ENDEPN + ">" + infoDO.getEndEpn() + "</"  + XML_NODE_ENDEPN + "> \n");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

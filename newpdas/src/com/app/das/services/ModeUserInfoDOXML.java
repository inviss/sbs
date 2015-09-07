package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ModeUserInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  수정자 기록 정보 관련 XML파서
 * @author asura207
 *
 */
public class ModeUserInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "Modeuserinfo";
	/** 
	 * 수정 일시 
	 */
	private String XML_NODE_MODIFICATIONDATE = "MOD_DT";
	/** 
	 * 수정자 ID 
	 */
	private String XML_NODE_MODIFIERID = "MODRID";
	/** 
	 * 수정자 명 
	 */
	private String XML_NODE_MODIFIERNM = "MOD_NM";
	public Object setDO(String _xml) {
		setDO(new ModeUserInfoDO());
		
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
		ModeUserInfoDO infoDO = (ModeUserInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MODIFICATIONDATE)) {
				infoDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODIFIERID)) {
				infoDO.setModrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODIFIERNM)) {
				infoDO.setMod_nm(_nodeValue);
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
		ModeUserInfoDO infoDO = (ModeUserInfoDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_MODIFICATIONDATE + ">" + infoDO.getModDt() + "</"  + XML_NODE_MODIFICATIONDATE + ">");
		_xml.append("<" + XML_NODE_MODIFIERID + ">" + infoDO.getModrId() + "</"  + XML_NODE_MODIFIERID + ">");
		_xml.append("<" + XML_NODE_MODIFIERNM + ">" + CommonUtl.transXmlText(infoDO.getMod_nm()) + "</"  + XML_NODE_MODIFIERNM + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

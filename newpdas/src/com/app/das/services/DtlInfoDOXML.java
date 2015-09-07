
package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.DtlInfoDO;
import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;


/**
 *  DTL 정보 관련 XML파서
 * @author asura207
 *
 */
public class DtlInfoDOXML extends DOXml {
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "dtlinfo";
	/**
	 * DTL명
	 */
	private String XML_NODE_DTL_NM = "dtl_nm";
	/**
	 * alias
	 */
	private String XML_NODE_ALIAS = "alias";
	/**
	 * DTL 위치
	 */
	private String XML_NODE_DTL_CONT = "dtl_cont";
	
	
	
	public Object setDO(String _xml) {
		setDO(new DtlInfoDO());
		
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
		DtlInfoDO dtlInfoDO = (DtlInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			 if(_nodeName.equals(XML_NODE_DTL_NM)) {
				 dtlInfoDO.setDtl_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ALIAS)) {
				dtlInfoDO.setAlias(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DTL_CONT)) {
				dtlInfoDO.setDtl_cont(_nodeValue);
			}
			
        }
	    
	    return dtlInfoDO;
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
		DtlInfoDO dtlInfoDO = (DtlInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_DTL_NM + ">" + CommonUtl.transXmlText(dtlInfoDO.getDtl_nm()) + "</"  + XML_NODE_DTL_NM + "> \n");
		_xml.append("<" + XML_NODE_ALIAS + ">" + dtlInfoDO.getAlias() + "</"  + XML_NODE_ALIAS + "> \n");
		_xml.append("<" + XML_NODE_DTL_CONT + ">" + CommonUtl.transXmlText(dtlInfoDO.getDtl_cont()) + "</"  + XML_NODE_DTL_CONT + "> \n");
		
		
		_xml.append("</" + XML_NODE_HEAD + ">");
	
		
		
		return _xml.toString();
	}

	
	
}

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.DeleteDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;

/**
 * 실패한 전송관련 삭제 관련 XML파서
 * @author asura207
 *
 */
public class DeletePdsArchiveDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "delete";	
	/** 
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";	
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID = "media_id"; 
	/**
	 * 결과값
	 */
	private String XML_NODE_RESULT = "result"; 
	/**
	 * 삭제사유
	 */
	private String XML_NODE_DEL_CONT = "del_cont"; 
	/**
	 * 삭제사유
	 */
	private String XML_NODE_REG_ID = "reg_id"; 
	
	public Object setDO(String _xml) {
		setDO(new DeleteDO());
		
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
		DeleteDO infoDO = (DeleteDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			
			 if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				infoDO.setMedia_id(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_DEL_CONT)) {
				infoDO.setDel_cont(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else  if(_nodeName.equals(XML_NODE_REG_ID)) {
				infoDO.setReg_id(_nodeValue);
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
		DeleteDO infoDO = (DeleteDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
	
		_xml.append("<" + XML_NODE_RESULT + ">" + infoDO.getResult() + "</"  + XML_NODE_RESULT + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	


}

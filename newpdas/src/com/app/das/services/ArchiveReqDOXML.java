package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.ArchiveReqDO;
import com.app.das.business.transfer.AuthorDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.NonEmployeeInfoDO;
import com.app.das.util.CommonUtl;


/**
 * 아카이브 요청 정보 관련 XML파서
 * @author asura207
 *
 */
public class ArchiveReqDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "archiveReq";
	/** 
	 * xml
	 */
	private String XML_NODE_XML_CONT = "XML";
	/** 
	 * 순번
	 */
	private String XML_NODE_SEQ = "SEQ";
	/** 
	 * 할당 상태
	 */
	private String XML_NODE_JOB_ALOCATE = "JOB_ALOCATE";
	/** 
	 * 할당 상태
	 */
	private String XML_NODE_ARCHIVE_SEQ = "ARCHIVE_SEQ";
	/** 
	 * 할당 상태
	 */
	private String XML_NODE_ARCHIVE_ID = "ARCHIVE_ID";
	/** 
	 * 작업상태
	 */
	private String XML_NODE_WORK_STAT = "WORK_STAT";
	/** 
	 * 결과
	 */
	private String XML_NODE_RESULT = "RESULT";

	
	
	
	public Object setDO(String _xml) {
		setDO(new ArchiveReqDO());
		
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
		ArchiveReqDO authorDO = (ArchiveReqDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_XML_CONT)) {
				authorDO.setXml_cont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				authorDO.setSEQ(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_JOB_ALOCATE)) {
				authorDO.setJob_alocate(_nodeValue);
			}	
			else if(_nodeName.equals(XML_NODE_ARCHIVE_SEQ)) {
				authorDO.setArchive_seq(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCHIVE_ID)) {
				authorDO.setArchive_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_WORK_STAT)) {
				authorDO.setWork_stat(_nodeValue);
			}
			
        }
	    
	    return authorDO;
	}
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}
	
	
	public String getSubXML(){
		ArchiveReqDO authorDO = (ArchiveReqDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_RESULT + ">" +  authorDO.getResult()+ "</"  + XML_NODE_RESULT + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
	
		return _xml.toString();
	}
	
	
	
}

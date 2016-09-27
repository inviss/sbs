package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AttachFileInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 첨부파일 정보 관련 XML파서
 * @author asura207
 *
 */
public class AttachFileInfoDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "attachFileInfo";
	/**
	 * 첨부 파일 이름
	 */
	private String XML_NODE_FILENAME = "FL_NM";
	/**
	 * 첨부 파일 사이즈
	 */
	private String XML_NODE_FILESIZE = "FL_SZ";
	/**
	 * 첨부 파일 경로
	 */
	private String XML_NODE_FILEPATH = "FL_PATH";
	/**
	 * 모자료 ID
	 */
	private String XML_NODE_DATAID = "MOTHR_DATA_ID";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQUENCE = "SEQ";
	/**
	 * 등록 일시
	 */
	private String XML_NODE_REGDATE = "REG_DT";
	/**
	 * 등록자 ID
	 */
	private String XML_NODE_REGISTRATOR = "REGRID";
	/**
	 * 상세설명
	 */
	private String XML_NODE_DESCRIPTION = "DESC";
	/**
	 * 첨부파일 유형 코드
	 */
	private String XML_NODE_ATTACHFILETYPECODE = "ATTC_FILE_TYPE_CD";
	/**
	 * 첨부 구분 코드
	 */
	private String XML_NODE_ATTACHCLASSIFICATIONCODE = "ATTC_CLF_CD";
	/**
	 * 원파일 명
	 */
	private String XML_NODE_ORG_FILE_NM = "ORG_FILE_NM";
	
	
	
	
	public Object setDO(String _xml) {
		setDO(new AttachFileInfoDO());
		
		List resultList  = new ArrayList();
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				resultList.add(setData((Element)_node));
			}
        }
		
		return resultList;
	}
	
	public Object setData(Element pElement) {
		AttachFileInfoDO infoDO = new AttachFileInfoDO();		
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_FILENAME)) {
				infoDO.setFileName(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILESIZE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setFileSize(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_FILEPATH)) {
				infoDO.setFilePath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DATAID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMothrDataId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_SEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATOR)) {
				infoDO.setRegrid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DESCRIPTION)) {
				infoDO.setDesc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ATTACHFILETYPECODE)) {
				infoDO.setAttcFileTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ATTACHCLASSIFICATIONCODE)) {
				infoDO.setAttcClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ORG_FILE_NM)) {
				infoDO.setOrgFileNm(_nodeValue);
			}
			
        }
	    
	    return infoDO;
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
		AttachFileInfoDO infoDO = (AttachFileInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_FILENAME + ">" + infoDO.getFileName() + "</"  + XML_NODE_FILENAME + "> \n");
		_xml.append("<" + XML_NODE_FILESIZE + ">" + infoDO.getFileSize() + "</"  + XML_NODE_FILESIZE + "> \n");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + infoDO.getFilePath() + "</"  + XML_NODE_FILEPATH + "> \n");
		_xml.append("<" + XML_NODE_DATAID + ">" + infoDO.getMothrDataId() + "</"  + XML_NODE_DATAID + "> \n");
		_xml.append("<" + XML_NODE_SEQUENCE + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQUENCE + "> \n");
		_xml.append("<" + XML_NODE_REGDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGDATE + "> \n");
		_xml.append("<" + XML_NODE_REGISTRATOR + ">" + infoDO.getRegrid() + "</"  + XML_NODE_REGISTRATOR + "> \n");
		_xml.append("<" + XML_NODE_DESCRIPTION + ">" + CommonUtl.transXmlText(infoDO.getDesc()) + "</"  + XML_NODE_DESCRIPTION + "> \n");
		_xml.append("<" + XML_NODE_ATTACHFILETYPECODE + ">" + infoDO.getAttcFileTypeCd() + "</"  + XML_NODE_ATTACHFILETYPECODE  + "> \n");
		_xml.append("<" + XML_NODE_ATTACHCLASSIFICATIONCODE + ">" + infoDO.getAttcClfCd() + "</"  + XML_NODE_ATTACHCLASSIFICATIONCODE  + "> \n");
		_xml.append("<" + XML_NODE_ORG_FILE_NM + ">" + infoDO.getOrgFileNm() + "</"  + XML_NODE_ORG_FILE_NM  + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

}

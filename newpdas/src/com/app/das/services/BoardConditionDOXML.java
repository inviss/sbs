package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.util.CommonUtl;


/**
 *  공지사항 정보 관련 XML파서
 * @author asura207
 *
 */
public class BoardConditionDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "boardcondition";
	/**
	 * 조회구분
	 */
	private String XML_NODE_SEARCHKIND = "searchKind";
	/**
	 * 게시판종류코드
	 */
	private String XML_NODE_BOARD_TYPE_CD="boardTypeCd";
	/**
	 * 조회값
	 */
	private String XML_NODE_SEARCHVALUE="searchValue";
	/**
	 * 본문ID
	 */
	private String XML_NODE_MAINID="mainId";
	/**
	 * 조회할 페이지
	 */
	private String XML_NODE_PAGE="page";
	/**
	 * 한 페이지당 갯수
	 */
	private String XML_NODE_ROWPERPAGE="rowPerPage";	
	
	
	
	public Object setDO(String _xml) {
		setDO(new BoardConditionDO());
		
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
		BoardConditionDO boardconditionDO = (BoardConditionDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SEARCHKIND)) {
				boardconditionDO.setSearchKind(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BOARD_TYPE_CD)) {
				boardconditionDO.setBoardTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEARCHVALUE)) {
				boardconditionDO.setSearchValue(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MAINID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					boardconditionDO.setMainId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_PAGE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					boardconditionDO.setPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_ROWPERPAGE)) {
				boardconditionDO.setRowPerPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
        }
	    
	    return boardconditionDO;
	}
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}
	
	
	public String getSubXML() {
		BoardConditionDO boardconditionDO = (BoardConditionDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_BOARD_TYPE_CD + ">" + boardconditionDO.getBoardTypeCd() + "</"  + XML_NODE_BOARD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_SEARCHKIND + ">" + boardconditionDO.getSearchKind() + "</"  + XML_NODE_SEARCHKIND + "> \n");
		_xml.append("<" + XML_NODE_SEARCHVALUE + ">" +boardconditionDO.getSearchValue() + "</"  + XML_NODE_SEARCHVALUE + "> \n");
		_xml.append("<" + XML_NODE_MAINID + ">" + boardconditionDO.getMainId() + "</"  + XML_NODE_MAINID + "> \n");
		_xml.append("<" + XML_NODE_PAGE + ">" + boardconditionDO.getPage() + "</"  + XML_NODE_PAGE + "> \n");
		_xml.append("<" + XML_NODE_ROWPERPAGE + ">" + boardconditionDO.getRowPerPage() + "</"  + XML_NODE_ROWPERPAGE + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
	
		
		
		return _xml.toString();
	}

	
}

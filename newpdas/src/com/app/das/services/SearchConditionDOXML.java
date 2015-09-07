package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.SearchConditionDO;
import com.app.das.business.transfer.TapeInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  검색조건  정보 관련 XML파서
 * @author asura207
 *
 */
public class SearchConditionDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "searchConditionInfo";
	/**
	 * page
	 */
	private String XML_NODE_PAGE = "page";
	/**
	 * rowPerPage 페이지당 열수
	 */
	private String XML_NODE_ROWPERPAGE = "rowPerPage"; 


	public Object setDO(String _xml) {
		setDO(new SearchConditionDO());

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
		SearchConditionDO infoDO = (SearchConditionDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PAGE)) {
				infoDO.setPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ROWPERPAGE)) {
				infoDO.setRowPerPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
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
		SearchConditionDO infoDO = (SearchConditionDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PAGE + ">" + infoDO.getPage() + "</"  + XML_NODE_PAGE + ">");
		_xml.append("<" + XML_NODE_ROWPERPAGE + ">" + infoDO.getRowPerPage()+ "</"  + XML_NODE_ROWPERPAGE + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.PreviewDO;
import com.app.das.util.CommonUtl;


/**
 *   프리뷰 메타 정보 관련 XML파서
 * @author asura207
 *
 */
public class PreviewConditionDOXML extends DOXml {
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD = "previewcondition";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * PREVIEW_ID
	 */
	private String XML_NODE_PREVIEW_ID="preview_id";


	public Object setDO(String _xml) {
		setDO(new PreviewDO());

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
		PreviewDO previewDO = (PreviewDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				previewDO.setMasterId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PREVIEW_ID)) {
				previewDO.setPreviewId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}

		}

		return previewDO;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}
	public String getSubXML() {
		PreviewDO previewDO = (PreviewDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + previewDO.getMasterId() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_PREVIEW_ID + ">" + previewDO.getPreviewId() + "</"  + XML_NODE_PREVIEW_ID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


}

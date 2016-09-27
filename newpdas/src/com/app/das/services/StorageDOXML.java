package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.StorageDO;
import com.app.das.util.CommonUtl;

/**
 *  스토리지 임계치 관리 XML파서
 * @author asura207
 *
 */
public class StorageDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "storage";
	/**
	 * 구분코드
	 */
	private String XML_NODE_STORAGE_NM = "storage_nm";
	/**
	 * 가용량
	 */
	private String XML_NODE_STORAGE_SIZE = "storgae_size";
	/**
	 * 설명
	 */
	private String XML_NODE_STORAGE_YN = "storage_yn";

	/**
	 * 사용량
	 */
	private String XML_NODE_USE_SIZE = "use_size";

	/**
	 * 총용량
	 */
	private String XML_NODE_TOTAL_SIZE = "total_size";


	/**
	 * 시스템id
	 */
	private String XML_NODE_SYSTEM_ID = "system_id";

	/**
	 * path
	 */
	private String XML_NODE_PATH = "path";
	/**
	 * 가용량
	 */
	private String XML_NODE_PASSIBLE_SIZE = "passible_size";
	/**
	 * 임계점
	 */
	private String XML_NODE_LIMIT = "limit";

	public Object setDO(String _xml) {
		setDO(new StorageDO());

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
		StorageDO infoDO = (StorageDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_STORAGE_NM)) {
				infoDO.setStorage_nm(_nodeValue);
			}

			else if(_nodeName.equals( XML_NODE_STORAGE_SIZE)) {
				infoDO.setStorgae_size(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STORAGE_YN)) {
				infoDO.setStorage_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SYSTEM_ID)) {
				infoDO.setSystem_id(_nodeValue);
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
		StorageDO infoDO = (StorageDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_STORAGE_YN + ">" + infoDO.getStorage_yn()+ "</"  + XML_NODE_STORAGE_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


	public String getSubXML2() {
		StorageDO infoDO = (StorageDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_STORAGE_NM + ">" + infoDO.getStorage_nm()+ "</"  + XML_NODE_STORAGE_NM + "> \n");
		_xml.append("<" + XML_NODE_TOTAL_SIZE + ">" + infoDO.getStorgae_sz()+ "</"  + XML_NODE_TOTAL_SIZE + "> \n");
		_xml.append("<" + XML_NODE_USE_SIZE + ">" + infoDO.getUsing_sz().trim()+ "%</"  + XML_NODE_USE_SIZE + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



	public String getStorageCheck() {
		StorageDO infoDO = (StorageDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_STORAGE_NM + ">" + infoDO.getStorage_nm().trim()+ "</"  + XML_NODE_STORAGE_NM + "> \n");
		_xml.append("<" + XML_NODE_TOTAL_SIZE + ">" + infoDO.getTotal_size().trim()+ "</"  + XML_NODE_TOTAL_SIZE + "> \n");
		_xml.append("<" + XML_NODE_USE_SIZE + ">" + infoDO.getUsing_sz().trim()+ "</"  + XML_NODE_USE_SIZE + "> \n");
		_xml.append("<" + XML_NODE_PATH + ">" + infoDO.getPath()+ "</"  + XML_NODE_PATH + "> \n");
		_xml.append("<" + XML_NODE_PASSIBLE_SIZE + ">" + infoDO.getStorgae_size().trim()+ "</"  + XML_NODE_PASSIBLE_SIZE + "> \n");
		_xml.append("<" + XML_NODE_LIMIT + ">" + infoDO.getLimite()+ "</"  + XML_NODE_LIMIT + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

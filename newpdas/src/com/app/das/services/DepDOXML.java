package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DepInfoDO;
import com.app.das.util.CommonUtl;

/**
 * 부서 정보 관련 XML파서
 * @author asura207
 *
 */
public class DepDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "depinfo";	
	/**
	 * 소속단위
	 */
	private String XML_NODE_POST_UNIT_CLF = "post_unit_clf"; 
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "dept_cd";
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM = "dept_nm"; 
	/**
	 * 본부코드
	 */
	private String XML_NODE_SUP_HEAD_CD = "sup_head_cd";
	/**
	 * 본부명
	 */
	private String XML_NODE_SUP_HEAD_NM = "sup_head_nm"; 
	/**
	 * 국코드
	 */
	private String XML_NODE_SUP_HTPO_CD = "sup_htpo_cd";
	/**
	 * 국명
	 */
	private String XML_NODE_SUP_HTPO_NM = "sup_htpo_nm"; 



	public Object setDO(String _xml) {
		setDO(new DepInfoDO());

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
		DepInfoDO infoDO = (DepInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				infoDO.setDept_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				infoDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_POST_UNIT_CLF)) {
				infoDO.setPost_unit_clf(_nodeValue);
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
		DepInfoDO infoDO = (DepInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");

		_xml.append("<" + XML_NODE_DEPT_CD + ">" + infoDO.getDept_cd() + "</"  + XML_NODE_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + infoDO.getDept_nm() + "</"  + XML_NODE_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_CD + ">" + infoDO.getSup_head_cd() + "</"  + XML_NODE_SUP_HEAD_CD + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_NM + ">" + infoDO.getSup_head_nm() + "</"  + XML_NODE_SUP_HEAD_NM + "> \n");
		_xml.append("<" + XML_NODE_SUP_HTPO_CD + ">" + infoDO.getSup_htpo_cd() + "</"  + XML_NODE_SUP_HTPO_CD + "> \n");
		_xml.append("<" + XML_NODE_SUP_HTPO_NM + ">" + infoDO.getSup_htpo_nm() + "</"  + XML_NODE_SUP_HTPO_NM + "> \n");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ApproveInfoDO;
import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.NleDO;
import com.app.das.util.CommonUtl;

/**
 *  NLE 정보 관련 XML파서
 * @author asura207
 *
 */
public class NleDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "nleinfo";
	/**
	 * 타이틀
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 영상구분
	 */
	private String XML_NODE_CON_CLA = "con_cla";
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID = "media_id";
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_PGM_NM = "pgm_nm"; 
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL = "sub_ttl";
	/**
	 * 파일명
	 */
	private String XML_NODE_FILE_NM = "file_nm";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FILE_PATH= "file_path";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD= "brd_dd";
	/**
	 * 시작점
	 */
	private String XML_NODE_SOM= "som";
	/**
	 * 종료점
	 */
	private String XML_NODE_EOM= "eom";



	public Object setDO(String _xml) {
		setDO(new NleDO());

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
		NleDO infoDO = (NleDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_CON_CLA)) {

				infoDO.setCon_cla(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				infoDO.setBrd_leng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGM_NM)) {

				infoDO.setPgm_nm(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_SUB_TTL)) {

				infoDO.setSub_ttl(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_FILE_NM)) {
				infoDO.setFile_nm(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_FILE_PATH)) {
				infoDO.setFile_path(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SOM)) {
				infoDO.setSom(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_EOM)) {
				infoDO.setEom(_nodeValue);
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
		ApproveInfoDO boardDO = (ApproveInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		return _xml.toString();
	}


}

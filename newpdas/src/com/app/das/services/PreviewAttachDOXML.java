package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.PreviewAttachDO;
import com.app.das.business.transfer.PreviewDO;
import com.app.das.util.CommonUtl;

/**
 *  프리뷰 첨부파일  정보 관련 XML파서
 * @author asura207
 *
 */
public class PreviewAttachDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "previewattatch";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * PREVIEW_ATTACH ID
	 */
	private String XML_NODE_PREVIEW_ATTATCH_ID="preview_attatch_id";
	/**
	 * 파일이름
	 */
	private String XML_NODE_FL_NM="fl_nm";
	/**
	 * 파일크기
	 */
	private String XML_NODE_FL_SZ="fl_sz";
	/**
	 * 파일 경로
	 */
	private String XML_NODE_FL_PATH="fl_path";
	/**
	 * 원 파일명
	 */
	private String XML_NODE_ORG_FILE_NM="org_file_nm";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT="reg_dt";
	/**
	 * 등록자 ID
	 */
	private String XML_NODE_REGRID="regrid";

	public Object setDO(String _xml) {
		setDO(new PreviewAttachDO());

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
		PreviewAttachDO previewAttatchDO = (PreviewAttachDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				previewAttatchDO.setMasterId(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_PREVIEW_ATTATCH_ID)) {
				previewAttatchDO.setPreviewAttatchId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_FL_NM)) {
				previewAttatchDO.setFL_NM(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FL_SZ)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					previewAttatchDO.setFL_SZ(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {
				previewAttatchDO.setFL_PATH(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ORG_FILE_NM)) {
				previewAttatchDO.setOrg_Flie_NM(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				previewAttatchDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGRID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					previewAttatchDO.setRegRId(_nodeValue);
				}
			}
		}

		return previewAttatchDO;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		return _xml;
	}


	public String getSubXML() {
		PreviewAttachDO previewAttachDO = (PreviewAttachDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + previewAttachDO.getMasterId() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_PREVIEW_ATTATCH_ID + ">" + previewAttachDO.getPreviewAttatchId() + "</"  + XML_NODE_PREVIEW_ATTATCH_ID + "> \n");
		_xml.append("<" + XML_NODE_FL_NM + ">" + previewAttachDO.getFL_NM() + "</"  + XML_NODE_FL_NM + "> \n");
		_xml.append("<" + XML_NODE_FL_SZ + ">" + previewAttachDO.getFL_SZ() + "</"  + XML_NODE_FL_SZ + "> \n");
		_xml.append("<" + XML_NODE_FL_PATH + ">" + previewAttachDO.getFL_PATH() + "</"  + XML_NODE_FL_PATH + "> \n");
		_xml.append("<" + XML_NODE_ORG_FILE_NM + ">" + previewAttachDO.getOrg_Flie_NM() + "</"  + XML_NODE_ORG_FILE_NM + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + previewAttachDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">" + previewAttachDO.getRegRId()+ "</"  + XML_NODE_REGRID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


}

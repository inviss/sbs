package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.DisuseDO;
import com.app.das.business.transfer.FileInfoDO;
import com.app.das.util.CommonUtl;

/**
 *  파일 정보 관련 XML파서
 * @author asura207
 *
 */
public class FileInfoDOXML extends DOXml {

	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "fileinfo";
	/**
	 * 모자료ID
	 */
	private String XML_NODE_MOTHRDATAID = "mothrdataid";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 첨부파일 유형코드
	 */
	private String XML_NODE_ATTCHFILETYPECD = "attchfiletypecd";
	/**
	 * 첨부구분코드
	 */
	private String XML_NODE_ATTCHCLFCD = "attch_clf_cd";
	/**
	 * 파일명
	 */
	private String XML_NODE_FILENM = "file_nm";
	/**
	 * 파일크기
	 */
	private String XML_NODE_FILESZ = "file_sz";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FLPATH = "fl_path";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt";
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REG_ID = "reg_id";
	/**
	 * 수정자ID
	 */
	private String XML_NODE_MOD_ID = "mod_id";


	public Object setDO(String _xml) {
		setDO(new FileInfoDO());

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
		FileInfoDO discard = (FileInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_MOTHRDATAID)) {
				discard.setMothrDataId(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				discard.setSeq(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_ATTCHFILETYPECD)) {
				discard.setAttcFileTypeCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ATTCHCLFCD)) {
				discard.setAttcClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				discard.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILENM)) {
				discard.setFlNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILESZ)) {
				discard.setFl_size(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_FLPATH)) {
				discard.setFlPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_ID)) {
				discard.setRegrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MOD_ID)) {
				discard.setModrId(_nodeValue);
			}

		}

		return discard;
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
		FileInfoDO discard = (FileInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_MOTHRDATAID + ">" + discard.getMothrDataId() + "</"  + XML_NODE_MOTHRDATAID + "> \n");
		_xml.append("<" + XML_NODE_SEQ + ">" + discard.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_ATTCHFILETYPECD + ">" + discard.getAttcFileTypeCd() + "</"  + XML_NODE_ATTCHFILETYPECD + "> \n");
		_xml.append("<" + XML_NODE_ATTCHCLFCD + ">" + discard.getAttcClfCd() + "</"  + XML_NODE_ATTCHCLFCD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + discard.getRegDt() + "</"  + XML_NODE_REG_DT + "> \n");	
		_xml.append("<" + XML_NODE_FILENM + ">" + discard.getFlNm()+ "</"  + XML_NODE_FILENM + "> \n)");
		_xml.append("<" + XML_NODE_FILESZ + ">" + discard.getFl_size() + "</"  + XML_NODE_FILESZ + "> \n");
		_xml.append("<" + XML_NODE_FLPATH + ">" + discard.getFlPath() + "</"  + XML_NODE_FLPATH + "> \n");


		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

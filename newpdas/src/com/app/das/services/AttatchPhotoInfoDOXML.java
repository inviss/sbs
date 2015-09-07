package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 첨부 사진 관련 XML파서
 * @author asura207
 *
 */
public class AttatchPhotoInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "attachphotoInfo";	
	/**
	 * 사진등록ID
	 */
	private String XML_NODE_PHOT_REG_ID = "PHOT_REG_ID";
	/**
	 * 사진명
	 */
	private String XML_NODE_SUBJ = "SUBJ";
	/**
	 * 저작권자
	 */
	private String XML_NODE_CPRTR_NM = "CPRTR_NM"; 

	/**
	 * 다운로드 금지여부
	 */
	private String XML_NODE_DOWN_YN = "DOWN_YN"; 

	/**
	 *  해상도
	 */
	private String XML_NODE_RESOLUTION = "RESOLUTION"; 
	/**
	 * 마스터 id
	 */
	private String XML_NODE_MASTER_ID = "MASTER_ID"; 
	/**
	 * 파일경로
	 */
	private String XML_NODE_FL_PATH = "FL_PATH";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD = "BRD_DD";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "REG_DT";
	/**
	 * 프로그램 제목
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "EPIS_NO";
	/**
	 * 시작 회차
	 */
	private String XML_NODE_START_EPIS_NO = "START_EPIS_NO";
	/**
	 * 종료 회차
	 */
	private String XML_NODE_END_EPIS_NO = "END_EPIS_NO";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "SEQ";
	/**
	 * 저자
	 */
	private String XML_NODE_AUTHOR ="AUTHOR";
	/**
	 * 원파일명 
	 */
	private String XML_NODE_ORG_FILE_NM = "ORG_FILE_NM";
	/**
	 * 서버내부 파일명
	 */
	private String XML_NODE_INT_FILE_NM = "INT_FILE_NM";
	/**
	 * 시작 방송일
	 */
	private String XML_NODE_START_BRD_DD = "START_BRD_DD";
	/**
	 * 종료 방송일
	 */
	private String XML_NODE_END_BRD_DD = "END_BRD_DD"; 
	/**
	 * 마스터id 그룹
	 */
	private String XML_NODE_MASTER_IDS = "MASTER_IDS"; 
	/**
	 * 파일크기
	 */
	private String XML_NODE_FL_SZ = "FL_SZ"; 
	/**
	 * 설명
	 */
	private String XML_NODE_CONT = "CONT"; 
	/**
	 * 제목
	 */
	private String XML_NODE_TTL = "TTL";
	/**
	 * 등록자id
	 */
	private String XML_NODE_REGID = "REGID";
	
	
	
	public String getNodeValue(Node pNode) {
		Node _node = pNode.getChildNodes().item(0);
		if(_node == null) {
			return new String();
		}
		return _node.getNodeValue();
	}
	
	public Object setDO(String _xml) {
		setDO(new PhotoInfoDO());
		
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

	/*
	public Object setDOs(String photoInfoDO, int inum) {
		setDOs(new PhotoInfoDO());
		
		
		 List resultList = new ArrayList();
		Document _document = getDocument(photoInfoDO);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
      
    	  
			Node _node = _nodeList.item(inum);
			 NodeList nodeList = _node.getChildNodes();
			
			String _nodeName = _node.getNodeName() ;
			 Element P = (Element)_node;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				
				 logger.debug("start  " + inum);
				
				 resultList.add(setData(P));
				
				 logger.debug("setData(P)    "+ setData(P) );
				 logger.debug("end  " + inum);
		
		}
    
	return resultList;
		
		
	}
		*/

	public Object setData(Element pElement) {
		PhotoInfoDO infoDO = (PhotoInfoDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			
			if(_nodeName.equals( XML_NODE_PHOT_REG_ID)) {
				infoDO.setPhotRegId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			
			else if(_nodeName.equals(XML_NODE_SUBJ)) {
				infoDO.setCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRTR_NM)) {
				infoDO.setCprtr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DOWN_YN)) {
				infoDO.setDown_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RESOLUTION)) {
				infoDO.setResoultion(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_FL_PATH)) {
				infoDO.setFlPath(_nodeValue);
			}
			
			else if(_nodeName.equals(XML_NODE_REG_DT)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPIS_NO)) {
				infoDO.setEpis(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_START_EPIS_NO)) {
				infoDO.setEpis(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			
			else if(_nodeName.equals(XML_NODE_END_EPIS_NO)) {
				infoDO.setEpis(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_AUTHOR)) {
				infoDO.setCprtr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_START_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ORG_FILE_NM)) {
				infoDO.setOrg_fl_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_INT_FILE_NM)) {
				infoDO.setInt_fl_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MASTER_IDS)) {
				infoDO.setMaster_ids(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FL_SZ)) {
				infoDO.setFl_sz(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TTL)) {
				infoDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REGID)) {
				infoDO.setReg_id(_nodeValue);
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
		PhotoInfoDO infoDO = (PhotoInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PHOT_REG_ID+ ">" + infoDO.getPhotRegId() + "</"  + XML_NODE_PHOT_REG_ID + "> \n");
		_xml.append("<" + XML_NODE_SUBJ + ">" + CommonUtl.transXmlText(infoDO.getCont()) + "</"  + XML_NODE_SUBJ + "> \n");
		_xml.append("<" + XML_NODE_CPRTR_NM + ">" + CommonUtl.transXmlText(infoDO.getCprtr_nm()) + "</"  + XML_NODE_CPRTR_NM + "> \n");
		_xml.append("<" + XML_NODE_DOWN_YN + ">" + infoDO.getDown_yn() + "</"  + XML_NODE_DOWN_YN + "> \n");		
		_xml.append("<" + XML_NODE_RESOLUTION + ">" + infoDO.getResoultion() + "</"  + XML_NODE_RESOLUTION + "> \n");		
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");		
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis() + "</"  + XML_NODE_EPIS_NO + "> \n");	
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_AUTHOR + ">" + CommonUtl.transXmlText(infoDO.getCprtr_nm()) + "</"  + XML_NODE_AUTHOR + "> \n");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	


}

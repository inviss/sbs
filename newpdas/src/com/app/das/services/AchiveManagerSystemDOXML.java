package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.AchiveManagerSystemDO;
import com.app.das.util.CommonUtl;


/**
 * 아카이브 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AchiveManagerSystemDOXML extends DOXml {

	private Logger logger = Logger.getLogger(AchiveManagerSystemDOXML.class);

	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "annotInfo";
	/**
	 * 순번(key값)
	 */
	private String XML_NODE_NUM = "num";
	/**
	 * 컨텐츠 인스턴스id
	 */
	private String XML_NODE_CTI_ID = "cti_id";
	/**
	 * 오브젝트 name
	 */
	private String XML_NODE_OBJ_NAME = "obj_name";
	/**
	 * 신청상태
	 */
	private String XML_NODE_ASTATUS = "astatus";
	/**
	 * 변경상태
	 */
	private String XML_NODE_DSTATUS = "dstatus"; 
	/**
	 * 등록유저
	 */
	private String XML_NODE_REG_USER = "reg_user";
	/**
	 * 수정유저
	 */
	private String XML_NODE_UPDT_USER = "updt_user";
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DTM = "reg_dtm";
	/**
	 * 수정일
	 */
	private String XML_NODE_UPDT_DTM = "updt_dtm";
	/**
	 * 진행율
	 */
	private String XML_NODE_PROGRESS = "progress";
	/**
	 * 사용여부
	 */
	private String XML_NODE_USE_YN = "use_yn";
	/**
	 * 제목
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 상태
	 */
	private  String XML_NODE_JOB_STATUS = "job_status";
	/**
	 * 복본여부
	 */
	private String XML_NODE_CYN = "cyn";
	/**
	 * 복본진행율
	 */
	private String XML_NODE_CPROGRESS = "cprogress";
	/**
	 * 컨텐츠 수
	 */
	private String XML_NODE_SCOUNT= "scount";
	/**
	 * 경로
	 */
	private String XML_NODE_PATH = "path";
	/**
	 * 실제 파일명
	 */
	private String XML_NODE_wrk_file_nm = "wrk_file_nm";


	public Object setDO(String _xml) {
		setDO(new AchiveManagerSystemDO());

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
		AchiveManagerSystemDO infoDO = new AchiveManagerSystemDO();
		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_NUM)) {
				infoDO.setNUM(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CTI_ID)) {
				infoDO.setCTI_ID(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_OBJ_NAME)) {
				infoDO.setOBJ_NAME(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASTATUS)) {

				infoDO.setASTATUS(_nodeValue);		
			}
			else if(_nodeName.equals(XML_NODE_DSTATUS)) {
				infoDO.setDSTATUS(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_USER)) {
				infoDO.setREG_USER(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_UPDT_USER)) {

				infoDO.setUPDT_USER(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REG_DTM)) {

				infoDO.setREG_DTM(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_UPDT_DTM)) {
				infoDO.setUPDT_DTM(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRESS)) {
				infoDO.setPROGRESS(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_YN)) {
				infoDO.setUSE_YN(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {	
				infoDO.setTITLE(StringUtils.defaultIfEmpty(_nodeValue, ""));			
			}
			else if(_nodeName.equals(XML_NODE_JOB_STATUS)) {
				infoDO.setJOB_STATUS(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CYN)) {
				infoDO.setCYN(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPROGRESS)) {
				infoDO.setCPROGRESS(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SCOUNT)) {

				infoDO.setSCOUNT(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_PATH)) {
				infoDO.setPATH(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_wrk_file_nm)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setWrk_file_nm(_nodeValue);
				}
			}


		}

		return infoDO;
	}


	public String toXML() {

		AchiveManagerSystemDO infoDO = (AchiveManagerSystemDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><das>");
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_NUM + ">" + infoDO.getNUM() + "</"  + XML_NODE_NUM + ">");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + infoDO.getCTI_ID() + "</"  + XML_NODE_CTI_ID + ">");
		_xml.append("<" + XML_NODE_OBJ_NAME + ">" + CommonUtl.transXmlText(infoDO.getOBJ_NAME()) + "</"  + XML_NODE_OBJ_NAME + ">");
		_xml.append("<" + XML_NODE_ASTATUS + ">" +infoDO.getASTATUS() + "</"  + XML_NODE_ASTATUS + ">");
		_xml.append("<" + XML_NODE_DSTATUS + ">" + infoDO.getDSTATUS()+ "</"  + XML_NODE_DSTATUS + ">");
		_xml.append("<" + XML_NODE_REG_USER + ">" + CommonUtl.transXmlText(infoDO.getREG_USER()) + "</"  + XML_NODE_REG_USER + ">");
		_xml.append("<" + XML_NODE_UPDT_USER + ">" + CommonUtl.transXmlText(infoDO.getUPDT_USER()) + "</"  + XML_NODE_UPDT_USER + ">");
		_xml.append("<" + XML_NODE_REG_DTM + ">" + infoDO.getREG_DTM() + "</"  + XML_NODE_REG_DTM + ">");
		_xml.append("<" + XML_NODE_UPDT_DTM + ">" + infoDO.getUPDT_DTM() + "</"  + XML_NODE_UPDT_DTM + ">");
		_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getPROGRESS() + "</"  + XML_NODE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_USE_YN + ">" +infoDO.getUSE_YN() + "</"  + XML_NODE_USE_YN + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTITLE()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_JOB_STATUS + ">" + infoDO.getJOB_STATUS() + "</"  + XML_NODE_JOB_STATUS + ">");
		_xml.append("<" + XML_NODE_CYN + ">" + infoDO.getCYN() + "</"  + XML_NODE_CYN + ">");
		_xml.append("<" + XML_NODE_CPROGRESS + ">" +infoDO.getCPROGRESS() + "</"  + XML_NODE_CPROGRESS + ">");
		_xml.append("<" + XML_NODE_SCOUNT + ">" + infoDO.getSCOUNT() + "</"  + XML_NODE_SCOUNT + ">");
		_xml.append("<" + XML_NODE_PATH + ">" + infoDO.getPATH() + "</"  + XML_NODE_PATH + ">");
		_xml.append("<" + XML_NODE_wrk_file_nm + ">" + CommonUtl.transXmlText(infoDO.getWrk_file_nm()) + "</"  + XML_NODE_wrk_file_nm + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		_xml.append("</das>");

		return _xml.toString();
	}
	
}

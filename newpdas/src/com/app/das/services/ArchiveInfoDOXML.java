package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 아카이브 정보 관련 XML파서
 * @author asura207
 *
 */
public class ArchiveInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "archiveinfo";
	/** 
	 * 제목
	 */
	private String XML_NODE_TITLE = "title";
	/** 
	 * 미디어 id 
	 */  
	private String XML_NODE_MEDIA_ID = "media_id";
	/** 
	 * 마스터 id 
	 */  
	private String XML_NODE_MASTER_ID = "master_id";
	/** 
	 * 아카이브 경로
	 */
	private String XML_NODE_ARCHIVE_PATH = "archive_path";
	/** 
	 * 요청 id 
	 */
	private String XML_NODE_REQ_ID = "req_nm"; 
	/** 
	 * 요청일 
	 */
	private String XML_NODE_REQ_DD = "req_dd";
	/** 
	 * 요청 명 
	 */
	private String XML_NODE_REQ_NM = "req_nm";
	/** 
	 * 아카이브요청
	 */
	private String XML_NODE_ARCHIVE_YN = "archive";
	/** 
	 *  고해상도
	 */
	private String XML_NODE_HIGH_QUAL = "high_qual";
	/** 
	 * wmv
	 */
	private String XML_NODE_WMV_YN = "wmv_yn";
	/** 
	 * dtl
	 */
	private String XML_NODE_DTL_YN = "dtl_yn";
	/** 
	 * 복본
	 */
	private String XML_NODE_COPY_CD = "copy";
	/** 
	 * 복본(구)
	 */
	private String XML_NODE_OLD_COPY_CD = "old_copy";
	/** 
	 * 검색조건-구분값
	 */
	private String XML_NODE_WHATDD = "whatdd";
	/** 
	 * 검색조건-시작
	 */
	private String XML_NODE_START_DD = "start_dd";
	/** 
	 * 검색조건-종료일
	 */
	private String XML_NODE_END_DD = "end_dd";
	/** 
	 * 비고
	 */
	private String XML_NODE_ETC = "others";

	/** 
	 * 방송/촬영일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";

	/** 
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "reg_dt";

	/** 
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "epis_no";

	/** 
	 * 회사코드
	 */
	private String XML_NODE_COCD = "cocd";

	/** 
	 * 채널코드
	 */
	private String XML_NODE_CHENNEL = "chennel";



	/** 
	 * 소산여부
	 */
	private String XML_NODE_BACKUP_YN = "backup_yn";


	/** 
	 * 소산등록일
	 */
	private String XML_NODE_BACKUP_DT = "backup_dt";


	/** 
	 * 소산신청자
	 */
	private String XML_NODE_BACKUP_ID = "backup_id";


	/** 
	 * 카탈로그yn
	 */
	private String XML_NODE_CATALOG_YN = "catalog_yn";


	public Object setDO(String _xml) {
		setDO(new ArchiveInfoDO());

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
		ArchiveInfoDO infoDO = (ArchiveInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));


			if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCHIVE_PATH)) {

				infoDO.setArchive_path(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REQ_ID)) {
				infoDO.setReq_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_DD)) {
				infoDO.setReq_dd(_nodeValue); 
			}
			else if(_nodeName.equals(XML_NODE_ARCHIVE_YN)) {

				infoDO.setArchive_yn(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_HIGH_QUAL)) {

				infoDO.setHigh_qual(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_WMV_YN)) {
				infoDO.setWmv(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DTL_YN)) {
				infoDO.setDtl_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COPY_CD)) {
				infoDO.setCopy_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WHATDD)) {
				infoDO.setWhatdd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_START_DD)) {
				infoDO.setStart_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_DD)) {

				infoDO.setEnd_dd(_nodeValue);

			}	else if(_nodeName.equals(XML_NODE_REQ_NM)) {

				infoDO.setReq_nm(_nodeValue);

			}	else if(_nodeName.equals(XML_NODE_MASTER_ID)) {

				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));

			}

			else if(_nodeName.equals(XML_NODE_COCD)) {

				infoDO.setCocd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)) {

				infoDO.setChennel(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_BACKUP_YN)) {

				infoDO.setBackup_yn(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_BACKUP_DT)) {

				infoDO.setBackup_dt(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_BACKUP_ID)) {

				infoDO.setBackup_id(_nodeValue);

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
		ArchiveInfoDO infoDO = (ArchiveInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_ARCHIVE_PATH + ">" + infoDO.getArchive_path() + "</"  + XML_NODE_ARCHIVE_PATH + "> \n");
		_xml.append("<" + XML_NODE_REQ_ID + ">" + infoDO.getReq_id()+ "</"  + XML_NODE_REQ_ID + "> \n");
		_xml.append("<" + XML_NODE_REQ_DD + ">" + infoDO.getReq_dd() + "</"  + XML_NODE_REQ_DD + "> \n");
		_xml.append("<" + XML_NODE_ARCHIVE_YN + ">" + infoDO.getArchive_yn() + "</"  + XML_NODE_ARCHIVE_YN + "> \n");
		_xml.append("<" + XML_NODE_HIGH_QUAL + ">" + infoDO.getHigh_qual() + "</"  + XML_NODE_HIGH_QUAL + "> \n");
		_xml.append("<" + XML_NODE_WMV_YN + ">" + infoDO.getWmv() + "</"  + XML_NODE_WMV_YN + "> \n");
		_xml.append("<" + XML_NODE_DTL_YN + ">" + infoDO.getDtl_yn() + "</"  + XML_NODE_DTL_YN + "> \n");
		_xml.append("<" + XML_NODE_COPY_CD + ">" + infoDO.getCopy_cd() + "</"  + XML_NODE_COPY_CD + "> \n");
		_xml.append("<" + XML_NODE_OLD_COPY_CD + ">" + infoDO.getOld_copy_cd() + "</"  + XML_NODE_OLD_COPY_CD + "> \n");
		_xml.append("<" + XML_NODE_ETC + ">" + infoDO.getEtc() + "</"  + XML_NODE_ETC + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + infoDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_BACKUP_YN + ">" + infoDO.getBackup_yn() + "</"  + XML_NODE_BACKUP_YN + "> \n");
		_xml.append("<" + XML_NODE_CHENNEL + ">" + infoDO.getChennel() + "</"  + XML_NODE_CHENNEL + "> \n");
		_xml.append("<" + XML_NODE_CATALOG_YN + ">" + infoDO.getCatalog_yn() + "</"  + XML_NODE_CATALOG_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.*;

import com.app.das.business.transfer.AchiveManagerSystemDO;
import com.app.das.business.transfer.CodeDO;
import com.app.das.business.transfer.LogInOutDO;
import com.app.das.business.transfer.MonitoringDO;
import com.app.das.business.transfer.MyCatalogDO;
import com.app.das.util.CommonUtl;


/**
 *  내목록 정보 관련 XML파서
 * @author asura207
 *
 */
public class MonitoringDOXML extends DOXml {

	private static Logger logger = Logger.getLogger(MonitoringDOXML.class);
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "monitoring";
	/**
	 * 상태
	 */
	private String XML_NODE_STATUS = "status";
	/**
	 * 작업명
	 */
	private String XML_NODE_JOB_NM = "job_nm";
	/**
	 * 작업 id
	 */
	private String XML_NODE_JOB_ID = "job_id";
	/**
	 * 소재명
	 */
	private String XML_NODE_CTGR_L_NM = "ctgr_l_nm";
	/**
	 * 요청시간
	 */
	private String XML_NODE_REQ_DT= "req_dt";

	/**
	 * 요청자id
	 */
	private String XML_NODE_REQ_ID= "req_id";
	/**
	 * 요청자명
	 */
	private String XML_NODE_REQ_NM= "req_nm";
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID= "media_id";

	/**
	 * 우선순위(기본값 5 아래로 내려갈수록 우선순위 상위)
	 */
	private String XML_NODE_PRIORITY= "priority";


	/**
	 * 총카운트
	 */
	private String XML_NODE_TOTALCOUNT= "totalcount";


	/**
	 * 시작페이지
	 */
	private String XML_NODE_START_PAGE= "start_page";

	/**
	 * key 값
	 */
	private String XML_NODE_KEY= "key";
	/**
	 * 구분(001 아카이브, 002 tc, 003 tm)
	 */
	private String XML_NODE_GUBUN= "gubun";

	/**
	 * 복본 진행률
	 */
	private String XML_NODE_COPY_PROGRESS= "copy_progress";
	/**
	 * 복본 상태
	 */
	private String XML_NODE_COPY_STATE= "copy_state";

	/**
	 * 소산 진행률
	 */
	private String XML_NODE_BACKUP_PROGRESS= "backup_progress";
	/**
	 * 소산 상태
	 */
	private String XML_NODE_BACKUP_STATE= "backup_state";


	//상세검색용

	/**
	 * 제목
	 */
	private String XML_NODE_TITLE= "title";
	/**
	 * 방송/촬영일
	 */
	private String XML_NODE_BRD_DD= "brd_dd";
	/**
	 * 진행률
	 */
	private String XML_NODE_PROGRESS= "progress";
	/**
	 * 컨텐츠 구분명
	 */
	private String XML_NODE_CT_CLA_NM= "ct_cla_nm";
	/**
	 * 컨텐츠 유형명
	 */
	private String XML_NODE_CT_TYP_NM= "ct_typ_nm";

	/**
	 * 영상선정
	 */
	private String XML_NODE_CTI_ID= "cti_id";
	/**
	 * 영상ID
	 */
	private String XML_NODE_CT_ID= "ct_id";

	/**
	 * 다운 진행률
	 */
	private String XML_NODE_DOWN_PROGRESS= "down_progress";
	/**
	 * 다운 상태
	 */
	private String XML_NODE_DOWN_STATE= "down_state";
	/**
	 * 다운 우선순위
	 */
	private String XML_NODE_DOWN_PRIORITY= "down_priority";
	/**
	 * 변환진행률
	 */
	private String XML_NODE_CHANGE_PROGRESS= "change_progress";
	/**
	 * 변환상태
	 */
	private String XML_NODE_CHANGE_STATE= "change_state";
	/**
	 * tm 진행률
	 */
	private String XML_NODE_TM_PROGRESS= "tm_progress";
	/**
	 * tm 상태
	 */
	private String XML_NODE_TM_STATE= "tm_state";
	/**
	 * tm 우선순위
	 */
	private String XML_NODE_TM_PRIORITY= "tm_priority";
	/**
	 * tc 상태
	 */
	private String XML_NODE_TC_STATE= "tc_state";
	/**
	 * tc 진행률
	 */
	private String XML_NODE_TC_PROGRESS= "tc_progress";
	/**
	 * 아카이브 진행률
	 */
	private String XML_NODE_ARCHIVE_PROGRESS= "archive_progress";
	/**
	 * 아카이브 상태
	 */
	private String XML_NODE_ARCHIVE_STATE= "archive_state";

	/**
	 * 검색시작일
	 */
	private String XML_NODE_START_SEARCH_DD= "start_search_dd";
	/**
	 * 검색종료일
	 */
	private String XML_NODE_END_SEARCH_DD= "end_search_dd";

	/**
	 * CART_NO
	 */
	private String XML_NODE_CART_NO= "cart_no";
	/**
	 * CART_SEQ
	 */
	private String XML_NODE_CART_SEQ= "cart_seq";
	/**
	 * REQ_CD
	 */
	private String XML_NODE_REQ_CD= "req_cd";
	/**
	 * key_id
	 */
	private String XML_NODE_KEYID= "key_id";

	/**
	 * 승인여부
	 */
	private String XML_NODE_APPROVE_STATUS= "approve_status";
	/**
	 * 영상구분
	 */
	private String XML_NODE_CT_TYP= "ct_typ";



	/**
	 * 계열사명
	 */
	private String XML_NODE_CO_NM= "co_nm";
	/**
	 * 타겟스토리지명
	 */
	private String XML_NODE_STORAGENM= "storage_nm";
	/**
	 * 시작점
	 */
	private String XML_NODE_SOM= "som";
	/**
	 * 종료점
	 */
	private String XML_NODE_EOM= "eom";
	/**
	 * 사용제한명
	 */
	private String XML_NODE_RIST_CLF_NM= "rist_clf_nm";
	/**
	 * 승인자명
	 */
	private String XML_NODE_APPROVE_NM= "approve_nm";


	/**
	 * 다운제목
	 */
	private String XML_NODE_DOWN_TITLE= "down_title";


	/**
	 * 시작시간
	 */
	private String XML_NODE_START_TIME= "start_time";

	/**
	 * 종료시간
	 */
	private String XML_NODE_END_TIME= "end_time";

	/**
	 * tc_ip
	 */
	private String XML_NODE_TC_IP= "tc_ip";


	/**
	 * 변환 tc ip
	 */
	private String XML_NODE_CHANGE_IP= "change_ip";



	/**
	 * tm ip
	 */
	private String XML_NODE_TM_IP= "tm_ip";

	/**
	 * tc_id
	 */
	private String XML_NODE_TC_ID= "tc_id";




	/**
	 * 거절사유
	 */
	private String XML_NODE_APP_CONT= "app_cont";

	/**
	 * tm 장비명
	 */
	private String XML_NODE_TM_NM= "tm_nm";

	/**
	 * tc 장비명
	 */
	private String XML_NODE_TC_NM= "tc_nm";

	/**
	 * 변환  장비명
	 */
	private String XML_NODE_CHANGE_NM= "change_nm";

	/**
	 * 채널명
	 */
	private String XML_NODE_CHANNEL_NM= "channel_nm";


	/**
	 * 경로명
	 */
	private String XML_NODE_ROUTE_NM= "route_nm";


	public Object setDO(String _xml) {
		setDO(new MonitoringDO());

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
		MonitoringDO infoDO = (MonitoringDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_STATUS)) {
				infoDO.setStatus(_nodeValue.trim());
			}
			else if(_nodeName.equals(XML_NODE_JOB_NM)) {
				infoDO.setJob_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_JOB_ID)) {

				infoDO.setJob_id(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_NM)) {

				infoDO.setCtgr_l_nm(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_REQ_DT)) {
				infoDO.setReq_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_ID)) {
				infoDO.setReq_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_NM)) {
				infoDO.setReq_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PRIORITY)) {
				infoDO.setPriority(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_START_PAGE)) {
				infoDO.setStart_page(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_KEY)) {
				infoDO.setKey(Long.parseLong(_nodeValue));
				infoDO.setKeyid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_START_SEARCH_DD)) {
				infoDO.setStart_search_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_END_SEARCH_DD)) {
				infoDO.setEnd_serach_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				infoDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CART_NO)) {
				infoDO.setCart_no(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_KEYID)) {
				infoDO.setKeyid(_nodeValue);
				infoDO.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
		}

		return infoDO;
	}


	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		_xml.append("<das>");
		_xml.append(getSubXML());
		_xml.append("</das>");

		return _xml.toString();
	}

	public String getSubXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_STATUS + ">" + infoDO.getStatus()+ "</"  + XML_NODE_STATUS + ">");
		_xml.append("<" + XML_NODE_JOB_NM + ">" + CommonUtl.transXmlText(infoDO.getJob_nm()) + "</"  + XML_NODE_JOB_NM + ">");
		_xml.append("<" + XML_NODE_JOB_ID + ">" + infoDO.getJob_id() + "</"  + XML_NODE_JOB_ID + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt()+ "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + ">");




		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



	//detail용
	public String getSubXML2() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getProgress() + "</"  + XML_NODE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_CTGR_L_NM + ">" + CommonUtl.transXmlText(infoDO.getCtgr_l_nm()) + "</"  + XML_NODE_CTGR_L_NM + ">");
		_xml.append("<" + XML_NODE_CT_CLA_NM + ">" + CommonUtl.transXmlText(infoDO.getCt_cla_nm())+ "</"  + XML_NODE_CT_CLA_NM + ">");
		_xml.append("<" + XML_NODE_CT_TYP_NM + ">" + CommonUtl.transXmlText(infoDO.getCt_typ_nm())+ "</"  + XML_NODE_CT_TYP_NM + ">");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + ">");



		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




	public String getArchiveXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_GUBUN + ">" + infoDO.getGubun() + "</"  + XML_NODE_GUBUN + ">");
		_xml.append("<" + XML_NODE_KEY + ">" + infoDO.getKey() + "</"  + XML_NODE_KEY + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_PRIORITY + ">" + infoDO.getPriority() + "</"  + XML_NODE_PRIORITY + ">");
		_xml.append("<" + XML_NODE_CT_TYP + ">" + infoDO.getCt_typ() + "</"  + XML_NODE_CT_TYP + ">");
		_xml.append("<" + XML_NODE_TC_PROGRESS + ">" + infoDO.getTc_progress() + "</"  + XML_NODE_TC_PROGRESS + ">");
		_xml.append("<" + XML_NODE_TC_STATE + ">" + infoDO.getTc_state() + "</"  + XML_NODE_TC_STATE + ">");
		_xml.append("<" + XML_NODE_APPROVE_STATUS + ">" + infoDO.getApprove_status() + "</"  + XML_NODE_APPROVE_STATUS + ">");
		_xml.append("<" + XML_NODE_ARCHIVE_PROGRESS + ">" + infoDO.getArchive_progress()+ "</"  + XML_NODE_ARCHIVE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_ARCHIVE_STATE + ">" + infoDO.getArchive_state() + "</"  + XML_NODE_ARCHIVE_STATE + ">");
		_xml.append("<" + XML_NODE_COPY_PROGRESS + ">" + infoDO.getCopy_progress() + "</"  + XML_NODE_COPY_PROGRESS + ">");
		_xml.append("<" + XML_NODE_COPY_STATE + ">" + infoDO.getCopy_state()+ "</"  + XML_NODE_COPY_STATE + ">");
		_xml.append("<" + XML_NODE_DOWN_PROGRESS + ">" + infoDO.getDown_progress() + "</"  + XML_NODE_DOWN_PROGRESS + ">");
		_xml.append("<" + XML_NODE_DOWN_STATE + ">" + infoDO.getDown_state()+ "</"  + XML_NODE_DOWN_STATE + ">");
		_xml.append("<" + XML_NODE_CHANGE_PROGRESS + ">" + infoDO.getChange_progress() + "</"  + XML_NODE_CHANGE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_CHANGE_STATE + ">" + infoDO.getChange_state()+ "</"  + XML_NODE_CHANGE_STATE + ">");
		_xml.append("<" + XML_NODE_BACKUP_PROGRESS + ">" + infoDO.getBackup_progress() + "</"  + XML_NODE_BACKUP_PROGRESS + ">");
		_xml.append("<" + XML_NODE_BACKUP_STATE + ">" + infoDO.getBackup_state()+ "</"  + XML_NODE_BACKUP_STATE + ">");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_TC_IP + ">" + infoDO.getTc_ip() + "</"  + XML_NODE_TC_IP + ">");
		_xml.append("<" + XML_NODE_CHANGE_IP + ">" + infoDO.getChange_ip() + "</"  + XML_NODE_CHANGE_IP + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + ">");
		_xml.append("<" + XML_NODE_CO_NM + ">" + CommonUtl.transXmlText(infoDO.getConm()) + "</"  + XML_NODE_CO_NM + ">");
		_xml.append("<" + XML_NODE_TC_ID + ">" + infoDO.getTc_id() + "</"  + XML_NODE_TC_ID + ">");
		_xml.append("<" + XML_NODE_CHANGE_NM + ">" + CommonUtl.transXmlText(infoDO.getChange_nm()) + "</"  + XML_NODE_CHANGE_NM + ">");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_CHANNEL_NM + ">" + CommonUtl.transXmlText(infoDO.getChennel_nm()) + "</"  + XML_NODE_CHANNEL_NM + ">");
		_xml.append("<" + XML_NODE_ROUTE_NM + ">" + CommonUtl.transXmlText(infoDO.getRoute_nm()) + "</"  + XML_NODE_ROUTE_NM + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");




		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}






	public String getDownXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_DOWN_TITLE + ">" + CommonUtl.transXmlText(infoDO.getDown_title())+ "</"  + XML_NODE_DOWN_TITLE + ">");
		_xml.append("<" + XML_NODE_KEY + ">" + infoDO.getKey() + "</"  + XML_NODE_KEY + ">");
		_xml.append("<" + XML_NODE_CART_NO + ">" + infoDO.getCart_no() + "</"  + XML_NODE_CART_NO + ">");
		_xml.append("<" + XML_NODE_CART_SEQ + ">" + infoDO.getCart_seq() + "</"  + XML_NODE_CART_SEQ + ">");
		_xml.append("<" + XML_NODE_JOB_NM + ">" + CommonUtl.transXmlText(infoDO.getJob_nm())+ "</"  + XML_NODE_JOB_NM + ">");
		_xml.append("<" + XML_NODE_DOWN_PROGRESS + ">" + infoDO.getDown_progress()+ "</"  + XML_NODE_DOWN_PROGRESS + ">");
		_xml.append("<" + XML_NODE_DOWN_STATE + ">" + infoDO.getDown_state() + "</"  + XML_NODE_DOWN_STATE + ">");
		_xml.append("<" + XML_NODE_DOWN_PRIORITY + ">" + infoDO.getDown_priority()+ "</"  + XML_NODE_DOWN_PRIORITY + ">");
		_xml.append("<" + XML_NODE_TM_PROGRESS + ">" + infoDO.getTm_progress() + "</"  + XML_NODE_TM_PROGRESS + ">");
		_xml.append("<" + XML_NODE_TM_STATE + ">" + infoDO.getTm_state()+ "</"  + XML_NODE_TM_STATE + ">");
		_xml.append("<" + XML_NODE_TM_PRIORITY + ">" + infoDO.getTm_priority() + "</"  + XML_NODE_TM_PRIORITY + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + ">");
		_xml.append("<" + XML_NODE_CO_NM + ">" + CommonUtl.transXmlText(infoDO.getConm()) + "</"  + XML_NODE_CO_NM + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_STORAGENM + ">" + infoDO.getStoragenm() + "</"  + XML_NODE_STORAGENM + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_SOM + ">" + infoDO.getSom() + "</"  + XML_NODE_SOM + ">");
		_xml.append("<" + XML_NODE_EOM + ">" + infoDO.getEom() + "</"  + XML_NODE_EOM + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_NM + ">" + CommonUtl.transXmlText(infoDO.getRist_clf_nm()) + "</"  + XML_NODE_RIST_CLF_NM + ">");
		_xml.append("<" + XML_NODE_APPROVE_NM + ">" + CommonUtl.transXmlText(infoDO.getApprovenm()) + "</"  + XML_NODE_APPROVE_NM + ">");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + ">");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		_xml.append("<" + XML_NODE_TM_IP + ">" + infoDO.getTm_ip() + "</"  + XML_NODE_TM_IP + ">");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_CHANNEL_NM + ">" + CommonUtl.transXmlText(infoDO.getChennel_nm()) + "</"  + XML_NODE_CHANNEL_NM + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");



		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}





	public String getManualXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_PRIORITY + ">" + infoDO.getPriority() + "</"  + XML_NODE_PRIORITY + ">");
		_xml.append("<" + XML_NODE_KEY + ">" + infoDO.getKey() + "</"  + XML_NODE_KEY + ">");
		_xml.append("<" + XML_NODE_COPY_PROGRESS + ">" + infoDO.getCopy_progress() + "</"  + XML_NODE_COPY_PROGRESS + ">");
		_xml.append("<" + XML_NODE_COPY_STATE + ">" + infoDO.getCopy_state()+ "</"  + XML_NODE_COPY_STATE + ">");
		_xml.append("<" + XML_NODE_DOWN_PROGRESS + ">" + infoDO.getDown_progress() + "</"  + XML_NODE_DOWN_PROGRESS + ">");
		_xml.append("<" + XML_NODE_DOWN_STATE + ">" + infoDO.getDown_state()+ "</"  + XML_NODE_DOWN_STATE + ">");
		_xml.append("<" + XML_NODE_CHANGE_PROGRESS + ">" + infoDO.getChange_progress() + "</"  + XML_NODE_CHANGE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_CHANGE_STATE + ">" + infoDO.getChange_state()+ "</"  + XML_NODE_CHANGE_STATE + ">");
		_xml.append("<" + XML_NODE_BACKUP_PROGRESS + ">" + infoDO.getBackup_progress() + "</"  + XML_NODE_BACKUP_PROGRESS + ">");
		_xml.append("<" + XML_NODE_BACKUP_STATE + ">" + infoDO.getBackup_state()+ "</"  + XML_NODE_BACKUP_STATE + ">");			
		_xml.append("<" + XML_NODE_ARCHIVE_PROGRESS + ">" + infoDO.getArchive_progress()+ "</"  + XML_NODE_ARCHIVE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_ARCHIVE_STATE + ">" + infoDO.getArchive_state() + "</"  + XML_NODE_ARCHIVE_STATE + ">");
		_xml.append("<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + ">");
		_xml.append("<" + XML_NODE_TM_IP + ">" + infoDO.getTm_ip() + "</"  + XML_NODE_TM_IP + ">");
		_xml.append("<" + XML_NODE_CO_NM + ">" + CommonUtl.transXmlText(infoDO.getConm()) + "</"  + XML_NODE_CO_NM + ">");
		_xml.append("<" + XML_NODE_TM_NM + ">" + CommonUtl.transXmlText(infoDO.getTm_nm()) + "</"  + XML_NODE_TM_NM + ">");
		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");




		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}






	public String getTCXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_PRIORITY + ">" + infoDO.getPriority() + "</"  + XML_NODE_PRIORITY + ">");
		_xml.append("<" + XML_NODE_KEY + ">" + infoDO.getKey() + "</"  + XML_NODE_KEY + ">");
		_xml.append("<" + XML_NODE_TC_PROGRESS + ">" + infoDO.getTc_progress() + "</"  + XML_NODE_TC_PROGRESS + ">");
		_xml.append("<" + XML_NODE_TC_STATE + ">" + infoDO.getTc_state() + "</"  + XML_NODE_TC_STATE + ">");
		_xml.append("<" + XML_NODE_DOWN_PROGRESS + ">" + infoDO.getDown_progress()+ "</"  + XML_NODE_DOWN_PROGRESS + ">");
		_xml.append("<" + XML_NODE_DOWN_STATE + ">" + infoDO.getDown_state() + "</"  + XML_NODE_DOWN_STATE + ">");

		_xml.append("<" + XML_NODE_CT_ID + ">" + infoDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(infoDO.getReq_nm()) + "</"  + XML_NODE_REQ_NM + ">");
		_xml.append("<" + XML_NODE_TC_IP + ">" + infoDO.getTc_ip() + "</"  + XML_NODE_TC_IP + ">");
		_xml.append("<" + XML_NODE_TC_NM + ">" + CommonUtl.transXmlText(infoDO.getTc_nm()) + "</"  + XML_NODE_TC_NM + ">");
		_xml.append("<" + XML_NODE_TOTALCOUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTALCOUNT + ">");




		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}





	public String getIfCmsXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_CART_NO + ">" + infoDO.getCart_no() + "</"  + XML_NODE_CART_NO + ">");
		_xml.append("<" + XML_NODE_DOWN_PROGRESS + ">" + infoDO.getDown_progress()+ "</"  + XML_NODE_DOWN_PROGRESS + ">");
		_xml.append("<" + XML_NODE_DOWN_STATE + ">" + infoDO.getDown_state() + "</"  + XML_NODE_DOWN_STATE + ">");
		_xml.append("<" + XML_NODE_TM_PROGRESS + ">" + infoDO.getTm_progress() + "</"  + XML_NODE_TM_PROGRESS + ">");
		_xml.append("<" + XML_NODE_TM_STATE + ">" + infoDO.getTm_state()+ "</"  + XML_NODE_TM_STATE + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + infoDO.getReq_dt()+ "</"  + XML_NODE_REQ_DT + ">");



		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}






	public String getJobInfoXML() {
		MonitoringDO infoDO = (MonitoringDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");		


		if(!infoDO.getTc_state().equals("")){
			_xml.append("<" + XML_NODE_JOB_NM + ">transaction</"  + XML_NODE_JOB_NM + ">");
			_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");
			_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getTc_progress() + "</"  + XML_NODE_PROGRESS + ">");
			_xml.append("<" + XML_NODE_STATUS + ">" + infoDO.getTc_state() + "</"  + XML_NODE_STATUS + ">");
			_xml.append("<" + XML_NODE_START_TIME + ">" + infoDO.getStart_time() + "</"  + XML_NODE_START_TIME + ">");
			_xml.append("<" + XML_NODE_END_TIME + ">" + infoDO.getEnd_time() + "</"  + XML_NODE_END_TIME + ">");
			_xml.append("<" + XML_NODE_APP_CONT + ">" +CommonUtl.transXmlText( infoDO.getRefuse_cont()) + "</"  + XML_NODE_APP_CONT + ">");

		}
		if(!infoDO.getStatus().equals("")){
			_xml.append("<" + XML_NODE_JOB_NM + ">approve</"  + XML_NODE_JOB_NM + ">");
			_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");

			if(infoDO.getStatus().equals("002")||infoDO.getStatus().equals("007")||infoDO.getStatus().equals("006")){
				_xml.append("<" + XML_NODE_STATUS + ">C</"  + XML_NODE_STATUS + ">");
			}else if(infoDO.getStatus().equals("003")){
				_xml.append("<" + XML_NODE_STATUS + ">J</"  + XML_NODE_STATUS + ">");
			}else{	
				_xml.append("<" + XML_NODE_STATUS + ">W</"  + XML_NODE_STATUS + ">");
			}


			if(infoDO.getStatus().equals("002")||infoDO.getStatus().equals("007")||infoDO.getStatus().equals("006")){
				_xml.append("<" + XML_NODE_PROGRESS + ">100</"  + XML_NODE_PROGRESS + ">");
			}else{
				_xml.append("<" + XML_NODE_PROGRESS + ">0</"  + XML_NODE_PROGRESS + ">");	
			}
			//	_xml = _xml + "<" + XML_NODE_PROGRESS + ">0</"  + XML_NODE_PROGRESS + ">");	
			_xml.append("<" + XML_NODE_APP_CONT + ">" + CommonUtl.transXmlText(infoDO.getRefuse_cont()) + "</"  + XML_NODE_APP_CONT + ">");
			_xml.append("<" + XML_NODE_START_TIME + ">" + infoDO.getStart_time() + "</"  + XML_NODE_START_TIME + ">");
			if(infoDO.getStatus().equals("002")||infoDO.getStatus().equals("007")||infoDO.getStatus().equals("006")){
				_xml.append("<" + XML_NODE_END_TIME + ">" + infoDO.getEnd_time() + "</"  + XML_NODE_END_TIME + ">");
			}else{
				_xml.append("<" + XML_NODE_END_TIME + ">" + ""+ "</"  + XML_NODE_END_TIME + ">");
			}
		}
		if(!infoDO.getArchive_state().equals("")){
			_xml.append("<" + XML_NODE_JOB_NM + ">archive</"  + XML_NODE_JOB_NM + ">");
			_xml.append("<" + XML_NODE_TITLE + ">" + infoDO.getTitle()+ "</"  + XML_NODE_TITLE + ">");

			_xml.append("<" + XML_NODE_STATUS + ">" + infoDO.getArchive_state() + "</"  + XML_NODE_STATUS + ">");
			_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getArchive_progress()+ "</"  + XML_NODE_PROGRESS + ">");
			_xml.append("<" + XML_NODE_START_TIME + ">" + infoDO.getStart_time() + "</"  + XML_NODE_START_TIME + ">");
			_xml.append("<" + XML_NODE_APP_CONT + ">" + CommonUtl.transXmlText(infoDO.getRefuse_cont()) + "</"  + XML_NODE_APP_CONT + ">");
			_xml.append("<" + XML_NODE_END_TIME + ">" + infoDO.getEnd_time() + "</"  + XML_NODE_END_TIME + ">");

		}

		if(!infoDO.getDown_state().equals("")){
			_xml.append("<" + XML_NODE_JOB_NM + ">download</"  + XML_NODE_JOB_NM + ">");
			_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");

			_xml.append("<" + XML_NODE_STATUS + ">" + infoDO.getDown_state()+ "</"  + XML_NODE_STATUS + ">");
			_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getDown_progress() + "</"  + XML_NODE_PROGRESS + ">");
			_xml.append("<" + XML_NODE_START_TIME + ">" + infoDO.getStart_time() + "</"  + XML_NODE_START_TIME + ">");
			_xml.append("<" + XML_NODE_APP_CONT + ">" + CommonUtl.transXmlText(infoDO.getRefuse_cont()) + "</"  + XML_NODE_APP_CONT + ">");
			_xml.append("<" + XML_NODE_END_TIME + ">" + infoDO.getEnd_time() + "</"  + XML_NODE_END_TIME + ">");

		}

		if(!infoDO.getTm_progress().equals("")){
			_xml.append("<" + XML_NODE_JOB_NM + ">transfer</"  + XML_NODE_JOB_NM + ">");
			_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle())+ "</"  + XML_NODE_TITLE + ">");

			_xml.append("<" + XML_NODE_STATUS + ">" + infoDO.getTm_state()+ "</"  + XML_NODE_STATUS + ">");
			_xml.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getTm_progress() + "</"  + XML_NODE_PROGRESS + ">");
			_xml.append("<" + XML_NODE_START_TIME + ">" + infoDO.getStart_time() + "</"  + XML_NODE_START_TIME + ">");
			_xml.append("<" + XML_NODE_APP_CONT + ">" + CommonUtl.transXmlText(infoDO.getRefuse_cont()) + "</"  + XML_NODE_APP_CONT + ">");
			_xml.append("<" + XML_NODE_END_TIME + ">" + infoDO.getEnd_time() + "</"  + XML_NODE_END_TIME + ">");

		}






		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

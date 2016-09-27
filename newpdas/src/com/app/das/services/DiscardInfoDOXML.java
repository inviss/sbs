package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DiscardDO;
import com.app.das.util.CommonUtl;


/**
 *  폐기 정보 관련 XML파서
 * @author asura207
 *
 */
public class DiscardInfoDOXML extends DOXml {


	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "discardinfo";
	/**
	 * 순번
	 */
	//private String XML_NODE_SEQ = "seq";
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";
	/**
	 * 보존기간 종료일
	 */
	private String XML_NODE_RSV_PRD_END_DD = "rsv_prd_end_dd";
	/**
	 * 보존기간 종료일(검색조건)
	 */
	private String XML_NODE_RSV_PRD_START_DD = "rsv_prd_start_dd";
	/**
	 * 보존기간 코드
	 */
	private String XML_NODE_RSV_PRD_CD = "rsv_prd_cd";	
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRD_LENG = "brd_leng";
	/**
	 * 폐기상태
	 */
	private String XML_NODE_DISUSE_STA = "disuse_sta";
	/**
	 * 마스터 id
	 */	
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 이용횟수
	 */
	private String XML_NODE_USE_COUNT = "use_count";
	/**
	 * 이용횟수 플래그
	 */
	private String XML_NODE_COUNT_FLAG = "count_flag";
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID = "media_id";
	/**
	 * 이용횟수 시작
	 */
	private String XML_NODE_FROM_USE = "from_use";
	/**
	 * 이용횟수 종료
	 */
	private String XML_NODE_TO_USE = "to_use";
	/**
	 * 전체조회수
	 */
	private String XML_NODE_TOTAL_COUNT = "total_count";
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL = "sub_ttl";
	/**
	 * 방송길이 합
	 */
	private String XML_NODE_SUM_BRD_LENG = "sum_brd_leng";
	/**
	 * 페이지당 컬럼수
	 */
	private String XML_NODE_ROWPERPAGE = "rowperpage";
	/**
	 * 페이지
	 */
	private String XML_NODE_PAGE = "page";
	/**
	 * 아카이브 등록일
	 */
	private String XML_NODE_ARCH_REG_DD = "arch_reg_dd";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "epis_no";
	/**
	 * 방송시작일
	 */
	private String XML_NODE_START_BRD_DD = "start_brd_dd";
	/**
	 * 방송종료일
	 */
	private String XML_NODE_END_BRD_DD = "end_brd_dd";


	/**
	 * 신청자명
	 */
	private String XML_NODE_REG_NM = "reg_nm";



	//2012.05.10

	/**
	 * 회사
	 */
	private String XML_NODE_COCD = "cocd";


	/**
	 * 채널
	 */
	private String XML_NODE_CHENNEL = "chennel";


	/**
	 * 아카이브경로
	 */
	private String XML_NODE_ARCHIVE_ROUTE = "archive_path";



	public Object setDO(String _xml) {
		setDO(new DiscardDO());

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
		DiscardDO discard = (DiscardDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {
				discard.setCtgr_l_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				discard.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				discard.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_END_DD)) {
				discard.setRsv_prd_end_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_START_DD)) {
				discard.setRsv_prd_start_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_CD)) {
				discard.setRsv_prd_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_LENG)) {
				discard.setRsv_prd_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DISUSE_STA)) {
				discard.setDisuse_sta(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)) {
				if(_nodeValue.equals("")){
					discard.setMaster_id(0);
				}else{
					discard.setMaster_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}

			}else if(_nodeName.equals(XML_NODE_USE_COUNT)) {
				discard.setUse_count(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)) {
				discard.setMedia_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COUNT_FLAG)) {
				discard.setUse_flag(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FROM_USE)) {
				discard.setFrom_use(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TO_USE)) {
				discard.setTo_use(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_ROWPERPAGE)) {
				discard.setRowPerPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_PAGE)) {
				discard.setPage(Integer.parseInt(_nodeValue));
			}else if(_nodeName.equals(XML_NODE_ARCH_REG_DD)) {
				discard.setArch_reg_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_START_BRD_DD)) {
				discard.setStart_brd_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_END_BRD_DD)) {
				discard.setEnd_brd_dd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REG_NM)) {
				discard.setReg_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COCD)) {
				discard.setCocd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				discard.setChennel(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_ARCHIVE_ROUTE)) {
				discard.setArchive_path(_nodeValue);
			}

		}

		return discard;
	}

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		DiscardDO discard = (DiscardDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");

		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + discard.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(discard.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + discard.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_START_DD + ">" + discard.getRsv_prd_start_dd() + "</"  + XML_NODE_RSV_PRD_START_DD + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_END_DD + ">" + discard.getRsv_prd_end_dd() + "</"  + XML_NODE_RSV_PRD_END_DD + "> \n");		
		_xml.append("<" + XML_NODE_RSV_PRD_CD + ">" + discard.getRsv_prd_cd() + "</"  + XML_NODE_RSV_PRD_CD + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + discard.getBrd_len() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_DISUSE_STA + ">" + discard.getDisuse_sta() + "</"  + XML_NODE_DISUSE_STA + "> \n");
		_xml.append("<" + XML_NODE_MASTER_ID + ">" + discard.getMaster_id() + "</"  + XML_NODE_MASTER_ID + "> \n");
		_xml.append("<" + XML_NODE_USE_COUNT + ">" + discard.getUse_count() + "</"  + XML_NODE_USE_COUNT + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + discard.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_REG_NM + ">" + discard.getReg_nm() + "</"  + XML_NODE_REG_NM + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(discard.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_ARCH_REG_DD + ">" + discard.getArch_reg_dd() + "</"  + XML_NODE_ARCH_REG_DD + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + discard.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



	public String getSubXML2() {
		DiscardDO discard = (DiscardDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<totalinfo>\n");


		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + discard.getTotalcount() + "</"  + XML_NODE_TOTAL_COUNT + "> \n");
		_xml.append("<" + XML_NODE_SUM_BRD_LENG + ">" + discard.getBrd_leng_sum() + "</"  + XML_NODE_SUM_BRD_LENG + "> \n");
		_xml.append("</totalinfo>");

		return _xml.toString();
	}



}

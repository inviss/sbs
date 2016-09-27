
package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.MediaArchiveDO;
import com.app.das.util.CommonUtl;


/**
 *  수동아카이브 정보 관련 XML파서
 * @author asura207
 *
 */
public class MediaArchiveDOXML extends DOXml {
	//private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "manualarchive";
	/**
	 * 회사코드(계열사 구분)

	 */
	private String XML_NODE_COCD = "cocd";
	/**
	 * 채널코드

	 */
	private String XML_NODE_CHENNE_CD = "chennel_cd";
	/**
	 * 자료구분

	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_lcd";
	/**
	 * 테이프종류코드

	 */
	private String XML_NODE_TAPE_MEDIA_CLF_CD = "tpae_media_clf_cd";
	/**
	 * 테이프 길이

	 */
	private String XML_NODE_NEW_TAPE_LENG = "tape_leng";
	/**
	 * 청구번호

	 */
	private String XML_NODE_REQ_CD = "req_cd";
	/**
	 * 장면번호

	 */
	private String XML_NODE_SCEAN_NO = "scean_no";
	/**
	 * 제목

	 */
	private String XML_NODE_TITLE  = "title";
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL = "sub_ttl";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "epis_no";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD = "brd_dd";

	/**
	 * 촬영지
	 */
	private String XML_NODE_CMR_PLACE = "cmr_place";
	/**
	 * 화질코드

	 */
	private String XML_NODE_VD_QLTY = "vd_qlty";

	/**
	 * 시청등급

	 */
	private String XML_NODE_VIEW_GR_CD = "view_gr_cd";
	/**
	 * 저장권형태

	 */
	private String XML_NODE_CPRT_TYPE = "cprt_type";
	/**
	 * 저작권형태설명

	 */
	private String XML_NODE_CPRT_TYPE_DSC = "cprt_type_dsc";
	/**
	 * 저작권자

	 */
	private String XML_NODE_CPRT_NM = "cprt_nm";
	/**
	 * 녹음방식

	 */
	private String XML_NODE_RECORD_TYPE_CD = "record_type_cd";
	/**
	 * 사용등급코드

	 */
	private String XML_NODE_RIST_CLF_CD = "rist_clf_cd";
	/**
	 * 사용범위

	 */
	private String XML_NODE_RIST_CLF_RANGE = "rist_clf_range";

	/**
	 * 보존기간

	 */
	private String XML_NODE_RSV_PRD_CD = "rsv_prd_cd";
	/**
	 * 제작구분

	 */
	private String XML_NODE_PRDT_IN_OUTS_CD = "prdt_in_outs_cd";
	/**
	 * 외주제작사

	 */
	private String XML_NODE_ORG_PRDR_NM = "org_prdr_nm";
	/**
	 * 자료내용

	 */
	private String XML_NODE_CONT = "cont";
	/**
	 * 특이사항

	 */
	private String XML_NODE_SPC_INFO = "spc_info";
	/**
	 * 아티스트

	 */
	private String XML_NODE_ARTIST = "aritist";
	/**
	 * 중분류

	 */
	private String XML_NODE_CTGR_M_CD = "ctgr_m_cd";
	/**
	 * 소분류

	 */
	private String XML_NODE_CTGR_S_CD = "ctgr_s_cd";
	/**
	 * 국가구분코드

	 */
	private String XML_NODE_COUNTRY_CD  = "country_cd";
	/**
	 * 키워드

	 */
	private String XML_NODE_KEY_WORDS = "key_words";
	/**
	 * 연출자

	 */
	private String XML_NODE_DRT_NM = "drt_nm";
	/**
	 * 촬영감독

	 */
	private String XML_NODE_CMR_DRT_NM = "cmr_drt_nm";
	/**
	 * 진행자

	 */
	private String XML_NODE_MC_NM = "mc_nm";
	/**
	 * 작가명

	 */
	private String XML_NODE_WRITER_NM = "writer_nm";
	/**
	 * 컨텐츠 유형

	 */
	private String XML_NODE_CT_CLA = "ct_cla";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQ = "seq";

	/**
	 * 상태 000 : 준비 , 001 : 작업중, 002 : 작업완료 , 003, 성공, 004 : 작업에러, 005 : 전송에러,006 : 연결에러
	 */
	private String XML_NODE_GUBUN = "gubun";



	public Object setDO(String _xml) {
		setDO(new MediaArchiveDO());

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
		MediaArchiveDO manualArchiveDO = (MediaArchiveDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CHENNE_CD)) {

				manualArchiveDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {
				manualArchiveDO.setCtgr_lcd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TAPE_MEDIA_CLF_CD)) {
				manualArchiveDO.setTpae_media_clf_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_NEW_TAPE_LENG)) {
				manualArchiveDO.setTape_leng(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_CD)) {

				manualArchiveDO.setReq_cd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_SCEAN_NO)) {

				manualArchiveDO.setScean_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));

			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {

				manualArchiveDO.setTitle(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUB_TTL)) {
				manualArchiveDO.setSub_ttl(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPIS_NO)) {
				manualArchiveDO.setEpis_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				manualArchiveDO.setBrd_dd(_nodeValue);
			}if(_nodeName.equals(XML_NODE_CMR_PLACE)) {
				manualArchiveDO.setCmr_place(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_QLTY)) {
				manualArchiveDO.setVd_qlty(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMR_PLACE)) {
				manualArchiveDO.setCmr_place(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VIEW_GR_CD)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					manualArchiveDO.setView_gr_cd(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_CPRT_TYPE)) {

				manualArchiveDO.setCprt_type(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CPRT_TYPE_DSC)) {

				manualArchiveDO.setCprt_type_dsc(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_CPRT_NM)) {
				manualArchiveDO.setCprt_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECORD_TYPE_CD)) {
				manualArchiveDO.setRecord_type_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_RIST_CLF_CD)) {
				manualArchiveDO.setRist_clf_Cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RIST_CLF_RANGE)) {
				manualArchiveDO.setRist_clf_range(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_CD)) {
				manualArchiveDO.setRsv_prd_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PRDT_IN_OUTS_CD)) {

				manualArchiveDO.setPrdt_in_outs_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ORG_PRDR_NM)) {
				manualArchiveDO.setOrg_prdr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONT)) {
				manualArchiveDO.setCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SPC_INFO)) {
				manualArchiveDO.setSpc_info(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARTIST)) {

				manualArchiveDO.setAritist(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CTGR_M_CD)) {

				manualArchiveDO.setCtgr_m_cd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CTGR_S_CD)) {

				manualArchiveDO.setCtgr_s_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_COUNTRY_CD)) {
				manualArchiveDO.setCountry_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_KEY_WORDS)) {
				manualArchiveDO.setKey_words(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DRT_NM)) {
				manualArchiveDO.setDrt_nm(_nodeValue);
			}if(_nodeName.equals(XML_NODE_CMR_DRT_NM)) {
				manualArchiveDO.setCmr_drt_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MC_NM)) {
				manualArchiveDO.setMc_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_WRITER_NM)) {
				manualArchiveDO.setWriter_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_CLA)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					manualArchiveDO.setCt_cla(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {

				manualArchiveDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));

			}
			else if(_nodeName.equals(XML_NODE_GUBUN)) {

				manualArchiveDO.setGubun(_nodeValue);
			}


		}

		return manualArchiveDO;
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
		MediaArchiveDO manualArchiveDO = (MediaArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_COCD + ">M</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_CHENNE_CD + ">" + manualArchiveDO.getCocd() + "</"  + XML_NODE_CHENNE_CD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + manualArchiveDO.getCtgr_lcd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");
		_xml.append("<" + XML_NODE_TAPE_MEDIA_CLF_CD + ">" + manualArchiveDO.getTpae_media_clf_cd() + "</"  + XML_NODE_TAPE_MEDIA_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_NEW_TAPE_LENG + ">" + manualArchiveDO.getTape_leng() + "</"  + XML_NODE_NEW_TAPE_LENG + "> \n");
		_xml.append("<" + XML_NODE_REQ_CD + ">" + manualArchiveDO.getReq_cd() + "</"  + XML_NODE_REQ_CD + "> \n");
		_xml.append("<" + XML_NODE_SCEAN_NO + ">" + manualArchiveDO.getScean_no() + "</"  + XML_NODE_SCEAN_NO + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(manualArchiveDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(manualArchiveDO.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + manualArchiveDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + manualArchiveDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">" + CommonUtl.transXmlText(manualArchiveDO.getCmr_place()) + "</"  + XML_NODE_CMR_PLACE + "> \n");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + manualArchiveDO.getVd_qlty() + "</"  + XML_NODE_VD_QLTY + "> \n");
		_xml.append("<" + XML_NODE_VIEW_GR_CD + ">" + manualArchiveDO.getView_gr_cd() + "</"  + XML_NODE_VIEW_GR_CD + "> \n");
		_xml.append("<" + XML_NODE_CPRT_TYPE + ">" + manualArchiveDO.getCprt_type() + "</"  + XML_NODE_CPRT_TYPE + "> \n");
		_xml.append("<" + XML_NODE_CPRT_TYPE_DSC + ">" + CommonUtl.transXmlText(manualArchiveDO.getCprt_type_dsc()) + "</"  + XML_NODE_CPRT_TYPE_DSC + "> \n");
		_xml.append("<" + XML_NODE_CPRT_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getCprt_nm()) + "</"  + XML_NODE_CPRT_NM + "> \n");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">" + manualArchiveDO.getRecord_type_cd() + "</"  + XML_NODE_RECORD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_RIST_CLF_CD + ">" + manualArchiveDO.getRist_clf_Cd() + "</"  + XML_NODE_RIST_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_RIST_CLF_RANGE + ">" + manualArchiveDO.getRist_clf_range() + "</"  + XML_NODE_RIST_CLF_RANGE + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_CD + ">" + manualArchiveDO.getRsv_prd_cd() + "</"  + XML_NODE_RSV_PRD_CD + "> \n");
		_xml.append("<" + XML_NODE_PRDT_IN_OUTS_CD + ">" + manualArchiveDO.getPrdt_in_outs_cd() + "</"  + XML_NODE_PRDT_IN_OUTS_CD + "> \n");
		_xml.append("<" + XML_NODE_ORG_PRDR_NM + ">" + manualArchiveDO.getOrg_prdr_nm() + "</"  + XML_NODE_ORG_PRDR_NM + "> \n");
		_xml.append("<" + XML_NODE_CONT + ">" + CommonUtl.transXmlText(manualArchiveDO.getCont()) + "</"  + XML_NODE_CONT + "> \n");
		_xml.append("<" + XML_NODE_SPC_INFO + ">" + CommonUtl.transXmlText(manualArchiveDO.getSpc_info()) + "</"  + XML_NODE_SPC_INFO + "> \n");
		_xml.append("<" + XML_NODE_ARTIST + ">" + manualArchiveDO.getAritist() + "</"  + XML_NODE_ARTIST + "> \n");
		_xml.append("<" + XML_NODE_CTGR_M_CD + ">" + manualArchiveDO.getCtgr_m_cd() + "</"  + XML_NODE_CTGR_M_CD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_S_CD + ">" + manualArchiveDO.getCtgr_s_cd() + "</"  + XML_NODE_CTGR_S_CD + "> \n");
		_xml.append("<" + XML_NODE_COUNTRY_CD + ">" + manualArchiveDO.getCountry_cd() + "</"  + XML_NODE_COUNTRY_CD + "> \n");
		_xml.append("<" + XML_NODE_KEY_WORDS + ">" + CommonUtl.transXmlText(manualArchiveDO.getKey_words()) + "</"  + XML_NODE_KEY_WORDS + "> \n");
		_xml.append("<" + XML_NODE_DRT_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getDrt_nm()) + "</"  + XML_NODE_DRT_NM + "> \n");
		_xml.append("<" + XML_NODE_CMR_DRT_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getCmr_drt_nm()) + "</"  + XML_NODE_CMR_DRT_NM + "> \n");
		_xml.append("<" + XML_NODE_MC_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getMc_nm()) + "</"  + XML_NODE_MC_NM + "> \n");
		_xml.append("<" + XML_NODE_WRITER_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getWriter_nm()) + "</"  + XML_NODE_WRITER_NM + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA + ">" + manualArchiveDO.getCt_cla() + "</"  + XML_NODE_CT_CLA + "> \n");
		_xml.append("<" + XML_NODE_SEQ + ">" + manualArchiveDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_GUBUN + ">" + manualArchiveDO.getGubun() + "</"  + XML_NODE_GUBUN + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}





}

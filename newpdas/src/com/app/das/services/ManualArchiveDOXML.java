
package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ManualArchiveDO;
import com.app.das.log.DasPropHandler;
import com.app.das.util.CommonUtl;


/**
 *  수동아카이브 정보 관련 XML파서
 * @author asura207
 *
 */
public class ManualArchiveDOXML extends DOXml {
	private static DasPropHandler dasHandler = DasPropHandler.getInstance();
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "manualarchive";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FL_PATH = "fl_path";
	/**
	 * 원본 미디어 ID
	 */
	private String XML_NODE_ORG_MEDIA_ID = "org_media_id";
	/**
	 * 새 미디어 ID
	 */
	private String XML_NODE_NEW_MEDIA_ID = "new_meida_id";
	/**
	 * 새 미디어 ID
	 */
	private String XML_NODE_NEW_MEDIA_ID2 = "new_media_id";
	/**
	 * CMS ID
	 */
	private String XML_NODE_PDS_CMS_ID = "pds_cms_id";
	/**
	 * 대분류코드(100 소재 200 프로그램)
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
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
	 * 콘텐츠 구분
	 */
	private String XML_NODE_CT_CLA = "ct_cla";
	/**
	 * 사용등급코드
	 */
	private String XML_NODE_RIST_CLF_CD = "rist_clf_cd";
	/**
	 * 촬영일
	 */
	private String XML_NODE_FM_DT = "fm_dt";
	/**
	 * 촬영지
	 */
	private String XML_NODE_CMR_PLACE = "cmr_place";
	/**
	 * 제작PD
	 */
	private String XML_NODE_PRODUCER_NM = "producer_nm";

	/**
	 * 가로 해상도
	 */
	private String XML_NODE_VD_VERSOL = "vd_versol";
	/**
	 * 세로 해상도
	 */
	private String XML_NODE_VD_HERSOL = "vd_hersol";
	/**
	 * 화면비
	 */
	private String XML_NODE_ASP_RTO_CD = "asp_rto_cd";
	/**
	 * 오디오 송출형태
	 */
	private String XML_NODE_AUDIO_TYPE = "audio_type";
	/**
	 * 파일 크기
	 */
	private String XML_NODE_FL_SIZE = "fl_size";
	/**
	 * 컨텐츠 유형
	 */
	private String XML_NODE_CT_TYP = "ct_typ";
	/**
	 * 미디어id 존재 여부
	 */
	private String XML_NODE_MEDIA_YN = "media_yn";

	/**
	 * 녹음방식코드
	 */
	private String XML_NODE_RECORD_TYPE_CD = "record_type_cd";

	//2012.4.26

	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD = "cocd";


	/**
	 * 채널코드
	 */
	private String XML_NODE_CHENNEL = "chennel";

	/**
	 * DTL 구분 코드
	 */
	private String XML_NODE_DTL_GUBUN = "dtl";

	/**
	 * 영상id
	 */
	private String XML_NODE_CT_ID = "ct_id";


	public Object setDO(String _xml) {
		setDO(new ManualArchiveDO());

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
		ManualArchiveDO manualArchiveDO = (ManualArchiveDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_FL_PATH)) {
				String value="";
				//String value2="";
				//String value3="";
				value=_nodeValue.replaceAll(dasHandler.getProperty("ARCREQ"), dasHandler.getProperty("WINARCREQ"));

				value= value.replaceAll(dasHandler.getProperty("MP4"), dasHandler.getProperty("WINMP4"));	

				value = value.replaceAll(dasHandler.getProperty("MP2"), dasHandler.getProperty("WINMP2"));	

				manualArchiveDO.setFl_path(value.replace('\\', '/'));
			}
			else if(_nodeName.equals(XML_NODE_ORG_MEDIA_ID)) {
				manualArchiveDO.setOrg_media_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_NEW_MEDIA_ID2)) {
				manualArchiveDO.setNew_media_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_NEW_MEDIA_ID)) {
				manualArchiveDO.setNew_media_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PDS_CMS_ID)) {

				manualArchiveDO.setCms_id(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {

				manualArchiveDO.setCtgr_l_cd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {

				manualArchiveDO.setTitle(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUB_TTL)) {
				manualArchiveDO.setSub_ttl(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPIS_NO)) {
				manualArchiveDO.setEpis_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_CLA)) {
				manualArchiveDO.setCt_cla(_nodeValue);
			}if(_nodeName.equals(XML_NODE_RIST_CLF_CD)) {
				manualArchiveDO.setRist_clf_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FM_DT)) {
				manualArchiveDO.setFm_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMR_PLACE)) {
				manualArchiveDO.setCmr_place(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRODUCER_NM)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					manualArchiveDO.setProducer_nm(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_VD_VERSOL)) {

				manualArchiveDO.setVd_vresol(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_VD_HERSOL)) {

				manualArchiveDO.setVd_hresol(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_ASP_RTO_CD)) {
				manualArchiveDO.setAsp_rto_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AUDIO_TYPE)) {
				manualArchiveDO.setAudio_type(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_FL_SIZE)) {
				manualArchiveDO.setFile_size(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CT_TYP)) {
				manualArchiveDO.setCt_typ(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECORD_TYPE_CD)) {
				manualArchiveDO.setRecode_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COCD)) {
				manualArchiveDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)) {
				manualArchiveDO.setChennel(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DTL_GUBUN)) {
				manualArchiveDO.setDtl_gubun(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_CT_ID)) {
				manualArchiveDO.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
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
		ManualArchiveDO manualArchiveDO = (ManualArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_ORG_MEDIA_ID + ">" + manualArchiveDO.getOrg_media_id() + "</"  + XML_NODE_ORG_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_FL_PATH + ">" + manualArchiveDO.getFl_path().trim() + "</"  + XML_NODE_FL_PATH + "> \n");
		_xml.append("<" + XML_NODE_NEW_MEDIA_ID + ">" + manualArchiveDO.getNew_media_id() + "</"  + XML_NODE_NEW_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_PDS_CMS_ID + ">" + manualArchiveDO.getCms_id() + "</"  + XML_NODE_PDS_CMS_ID + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + manualArchiveDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(manualArchiveDO.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(manualArchiveDO.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + manualArchiveDO.getEpis_no().trim() + "</"  + XML_NODE_EPIS_NO + "> \n");
		_xml.append("<" + XML_NODE_CT_CLA + ">" + manualArchiveDO.getCt_cla() + "</"  + XML_NODE_CT_CLA + "> \n");
		_xml.append("<" + XML_NODE_RIST_CLF_CD + ">" + manualArchiveDO.getRist_clf_cd() + "</"  + XML_NODE_RIST_CLF_CD + "> \n");
		_xml.append("<" + XML_NODE_FM_DT + ">" + manualArchiveDO.getFm_dt() + "</"  + XML_NODE_FM_DT + "> \n");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">" + CommonUtl.transXmlText(manualArchiveDO.getCmr_place()) + "</"  + XML_NODE_CMR_PLACE + "> \n");
		_xml.append("<" + XML_NODE_PRODUCER_NM + ">" + CommonUtl.transXmlText(manualArchiveDO.getProducer_nm()) + "</"  + XML_NODE_PRODUCER_NM + "> \n");
		_xml.append("<" + XML_NODE_VD_VERSOL + ">" + manualArchiveDO.getVd_vresol() + "</"  + XML_NODE_VD_VERSOL + "> \n");
		_xml.append("<" + XML_NODE_VD_HERSOL + ">" + manualArchiveDO.getVd_hresol() + "</"  + XML_NODE_VD_HERSOL + "> \n");
		_xml.append("<" + XML_NODE_ASP_RTO_CD + ">" + manualArchiveDO.getAsp_rto_cd() + "</"  + XML_NODE_ASP_RTO_CD + "> \n");
		_xml.append("<" + XML_NODE_AUDIO_TYPE + ">" + manualArchiveDO.getAudio_type() + "</"  + XML_NODE_AUDIO_TYPE + "> \n");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">" + manualArchiveDO.getRecode_yn() + "</"  + XML_NODE_RECORD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_COCD + ">" + manualArchiveDO.getCocd() + "</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_CHENNEL + ">" + manualArchiveDO.getChennel() + "</"  + XML_NODE_CHENNEL + "> \n");
		_xml.append("<" + XML_NODE_DTL_GUBUN + ">" + manualArchiveDO.getDtl_gubun() + "</"  + XML_NODE_DTL_GUBUN + "> \n");


		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}




	public String getSubXML2() {
		ManualArchiveDO manualArchiveDO = (ManualArchiveDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_ORG_MEDIA_ID + ">" + manualArchiveDO.getOrg_media_id() + "</"  + XML_NODE_ORG_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_NEW_MEDIA_ID + ">" + manualArchiveDO.getNew_media_id() + "</"  + XML_NODE_NEW_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_YN + ">" + manualArchiveDO.getIsmedia_yn() + "</"  + XML_NODE_MEDIA_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}

}

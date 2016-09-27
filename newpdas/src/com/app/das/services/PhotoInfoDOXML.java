package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  사진 메타 정보 관련 XML파서
 * @author asura207
 *
 */
public class PhotoInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */

	private String XML_NODE_HEAD = "photoInfo";	
	/**
	 * 수집처코드
	 */
	private String XML_NODE_GATHERCOMPANYCODE = "GATH_CO_CD";
	/**
	 * 수집구분코드
	 */
	private String XML_NODE_GATHCLASSIFYCODE = "GATH_CLF_CD";
	/**
	 * 미디어색상정보
	 */
	private String XML_NODE_MEDIACOLORINFO = "MEDIA_COLOR_INFO";
	/**
	 * 수집일시
	 */
	private String XML_NODE_GATHERDATE = "GATH_DD"; 
	/**
	 * 사진등록ID
	 */
	private String XML_NODE_PHOTOREGISTERID = "PHOT_REG_ID"; 
	/**
	 * 사진명
	 */
	private String XML_NODE_CONTENTS = "CONT"; 
	/**
	 * 특이사항
	 */
	private String XML_NODE_SPCINFO = "SPC_INFO"; 
	/**
	 * 사진 종류 코드
	 */
	private String XML_NODE_PHOTOCLASSIFICATIONCODE = "PHOT_CLF_CD";
	/**
	 * 파일경로
	 */
	private String XML_NODE_FILEPATH = "FL_PATH";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID";
	/**
	 * 사진 ID
	 */
	private String XML_NODE_PHOTOID = "PHOT_ID";
	/**
	 * 시작회차
	 */
	private String XML_NODE_BEGINEEPN = "BGN_EPN";
	/**
	 * 끝회차
	 */
	private String XML_NODE_ENDEPN = "END_EPN"; 
	/**
	 * 등록일
	 */
	private String XML_NODE_REGISTRATIONDATE = "REG_DT";
	/**
	 * 순번
	 */
	private String XML_NODE_SEQUENCE = "SEQ";	
	/**
	 * 프로그램ID
	 */
	private String XML_NODE_PGMID = "PGM_ID";
	/**
	 * 사진ID 그룹
	 */
	private String XML_NODE_PHOTO_IDS = "PHOT_REG_IDS";
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQ_ID = "REQ_ID";
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REG_ID = "REG_ID";
	/**
	 * 마스터ID
	 */
	private String XML_NODE_MASTER_ID= "MASTER_ID";
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "CTGR_L_CD";
	/**
	 * 중분류
	 */
	private String XML_NODE_CTGR_M_CD = "CTGR_M_CD";
	/**
	 * 소분류
	 */
	private String XML_NODE_CTGR_S_CD = "CTGR_S_CD";

	/*
	public Object setDO(String _xml) {
		setDO(new PhotoInfoDO());

		List resultList  = new ArrayList();

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				resultList.add(setData((Element)_node));
			}
        }

		return resultList;
	}
	 */
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

	public Object setData(Element pElement) {
		PhotoInfoDO infoDO = new PhotoInfoDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_GATHERCOMPANYCODE)) {
				infoDO.setGathCoCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GATHCLASSIFYCODE)) {
				infoDO.setGathClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIACOLORINFO)) {
				infoDO.setMediaColorInfo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_GATHERDATE)) {
				infoDO.setGathDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PHOTOREGISTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPhotRegId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTS)) {
				infoDO.setCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SPCINFO)) {
				infoDO.setSpcInfo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PHOTOCLASSIFICATIONCODE)) {
				infoDO.setPhotClfCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILEPATH)) {
				infoDO.setFlPath(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_PHOTOID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPhotId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_BEGINEEPN)) {
				infoDO.setBgnEpn(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_ENDEPN)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEndEpn(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGISTRATIONDATE)) {
				infoDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setSeq(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_PGMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(_nodeValue));
				}

			}else if(_nodeName.equals(XML_NODE_PHOTO_IDS)) {					
				infoDO.setPhotRegIdS(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REQ_ID)) {					
				infoDO.setReq_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REG_ID)) {					
				infoDO.setReg_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MASTER_ID)) {					
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_CTGR_L_CD)) {					
				infoDO.setCtgr_l_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_M_CD)) {					
				infoDO.setCtgr_m_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_S_CD)) {					
				infoDO.setCtgr_s_cd(_nodeValue);
			}

		}

		return infoDO;
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
		PhotoInfoDO infoDO = (PhotoInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_GATHERCOMPANYCODE + ">" + infoDO.getGathCoCd() + "</"  + XML_NODE_GATHERCOMPANYCODE + "> \n");
		_xml.append("<" + XML_NODE_GATHCLASSIFYCODE + ">" + infoDO.getGathClfCd() + "</"  + XML_NODE_GATHCLASSIFYCODE + "> \n");
		_xml.append("<" + XML_NODE_MEDIACOLORINFO + ">" + infoDO.getMediaColorInfo() + "</"  + XML_NODE_MEDIACOLORINFO + "> \n");
		_xml.append("<" + XML_NODE_GATHERDATE + ">" + infoDO.getGathDd() + "</"  + XML_NODE_GATHERDATE + "> \n");
		_xml.append("<" + XML_NODE_PHOTOREGISTERID + ">" + infoDO.getPhotRegId() + "</"  + XML_NODE_PHOTOREGISTERID + "> \n");
		_xml.append("<" + XML_NODE_CONTENTS + ">" + CommonUtl.transXmlText(infoDO.getCont()) + "</"  + XML_NODE_CONTENTS + "> \n");
		_xml.append("<" + XML_NODE_SPCINFO + ">" + CommonUtl.transXmlText(infoDO.getSpcInfo()) + "</"  + XML_NODE_SPCINFO + "> \n");
		_xml.append("<" + XML_NODE_PHOTOCLASSIFICATIONCODE + ">" + infoDO.getPhotClfCd() + "</"  + XML_NODE_PHOTOCLASSIFICATIONCODE + "> \n");
		_xml.append("<" + XML_NODE_FILEPATH + ">" + infoDO.getFlPath() + "</"  + XML_NODE_FILEPATH + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + "> \n");
		_xml.append("<" + XML_NODE_PHOTOID + ">" + infoDO.getPhotId() + "</"  + XML_NODE_PHOTOID + "> \n");
		_xml.append("<" + XML_NODE_BEGINEEPN + ">" + infoDO.getBgnEpn() + "</"  + XML_NODE_BEGINEEPN + "> \n");
		_xml.append("<" + XML_NODE_ENDEPN + ">" + infoDO.getEndEpn() + "</"  + XML_NODE_ENDEPN + "> \n");
		_xml.append("<" + XML_NODE_REGISTRATIONDATE + ">" + infoDO.getRegDt() + "</"  + XML_NODE_REGISTRATIONDATE + "> \n");
		_xml.append("<" + XML_NODE_SEQUENCE + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQUENCE + "> \n");
		_xml.append("<" + XML_NODE_PGMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PGMID + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

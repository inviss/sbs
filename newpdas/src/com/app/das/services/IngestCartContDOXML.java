package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CartContDO;
import com.app.das.util.CommonUtl;
/**
 *  인제스트 요청 정보 관련 XML파서
 * @author asura207
 *
 */
public class IngestCartContDOXML extends DOXml {
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "cartContents";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CARTNO = "CART_NO";
	/**
	 * 콘텐츠ID
	 */
	private String XML_NODE_CONTENTSID = "CT_ID"; 
	/**
	 * 콘텐츠인스탄스ID
	 */
	private String XML_NODE_CTIID = "CTI_ID"; 
	/**
	 * duration
	 */
	private String XML_NODE_DURATION = "DURATION";
	/**
	 * 콘텐츠
	 */
	private String XML_NODE_CONTENTSNAME = "CT_NM";
	/**
	 * 카트내순번
	 */
	private String XML_NODE_CARTSEQUENCE = "CART_SEQ";
	/**
	 * 종횡비코드(001 16:9 002 4:3)
	 */
	private String XML_NODE_ASP_RTO_CD = "ASP_RTO_CD";
	/**
	 * 화질코드(001 HD 002 SD)
	 */
	private String XML_NODE_VD_QLTY = "VD_QLTY";
	/**
	 *  다운로드 일시 
	 */
	private String XML_NODE_DOWN_DT = "DOWN_DT";
	/**
	 * 방송일/촬영일
	 */
	private String XML_NODE_BRD_DD = "BRD_DD";
	/**
	 * 타이틀명
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "EPIS_NO";
	/**
	 *  파일 명
	 */
	private String XML_NODE_FILE_NM = "FILE_NM";
	/**
	 * 다운요청상태(001 사용 중 002 임시저장 003 승인요청 004 승인 005 승인거부 \N 006 다운로드 진행중 007 다운로드 완료)
	 */
	private String XML_NODE_DOWN_STAT ="DOWN_STAT";
	/**
	 * job_Status ( Q:대기,  I:진행중,  C:완료)
	 */
	private String XML_NODE_JOB_STATUS = "JOB_STATUS";
	/**
	 * 진행률
	 */
	private String XML_NODE_PROGRESS = "PROGRESS";
	/**
	 *  미디어 id
	 */
	private String XML_NODE_MEDIAID = "MEDIA_ID";



	public Object setDO(String _xml) {
		setDO(new CartContDO());

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
		CartContDO cartDO = (CartContDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CARTNO)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CARTSEQUENCE)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCartSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CTIID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCtiId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSNAME)) {
				cartDO.setCtNm(_nodeValue);
			}

		}

		return cartDO;
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
		CartContDO cartDO = (CartContDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CARTNO + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO + ">");
		_xml.append("<" + XML_NODE_CARTSEQUENCE + ">" + cartDO.getCartSeq() + "</"  + XML_NODE_CARTSEQUENCE + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + cartDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_CTIID + ">" + cartDO.getCtiId() + "</"  + XML_NODE_CTIID + ">");
		_xml.append("<" + XML_NODE_DURATION + ">" + cartDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("<" + XML_NODE_CONTENTSNAME + ">" + cartDO.getCtNm() + "</"  + XML_NODE_CONTENTSNAME + ">");
		_xml.append("<" + XML_NODE_VD_QLTY + ">" + cartDO.getVd_qlty() + "</"  + XML_NODE_VD_QLTY + ">");
		_xml.append("<" + XML_NODE_ASP_RTO_CD + ">" + cartDO.getAsp_rto_cd() + "</"  + XML_NODE_ASP_RTO_CD + ">");
		_xml.append("<" + XML_NODE_DOWN_DT + ">" + cartDO.getDown_dt() + "</"  + XML_NODE_DOWN_DT + ">");

		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + CommonUtl.transXmlText(cartDO.getBrd_dd()) + "</"  + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + cartDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + ">");
		_xml.append("<" + XML_NODE_DOWN_STAT + ">" + cartDO.getDown_stat() + "</"  + XML_NODE_DOWN_STAT + ">");
		_xml.append("<" + XML_NODE_PROGRESS + ">" + cartDO.getProgress() + "</"  + XML_NODE_PROGRESS + ">");
		_xml.append("<" + XML_NODE_JOB_STATUS + ">" + cartDO.getJob_status() + "</"  + XML_NODE_JOB_STATUS + ">");
		String fi_nm = "";
		if(cartDO.getFile_nm().matches(".*MXF.*")){
			fi_nm=cartDO.getFile_nm().replaceAll(".MXF", "");
		}else if(cartDO.getFile_nm().matches(".*.mxf.*")){
			fi_nm=cartDO.getFile_nm().replaceAll(".mxf", "");
		}
		//fi_nm = cartDO.getFile_nm().replaceAll(".MXF", "").trim();
		_xml.append("<" + XML_NODE_FILE_NM + ">" + fi_nm + "</"  + XML_NODE_FILE_NM + ">");
		_xml.append("<" + XML_NODE_MEDIAID + ">" + cartDO.getMedia_id() + "</"  + XML_NODE_MEDIAID + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}


}

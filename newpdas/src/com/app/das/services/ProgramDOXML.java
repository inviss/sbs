package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.util.CommonUtl;
/**
 *   프로그램 정보 관련 XML파서
 * @author asura207
 *
 */
public class ProgramDOXML extends DOXml {
	/**
	 * xml해더
	 */ 
	private String XML_NODE_HEAD = "programInfo";
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PROGRAMID = "PGM_ID";
	/**
	 * 마스터 ID
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/**
	 * 프로그램 이름
	 */
	private String XML_NODE_PROGRAMNAME = "PGM_NM";
	/**
	 * 에피소드 이름
	 */
	private String XML_NODE_PROGRAMEPISODE = "PGM_EPIS";
	/**
	 * 방송 시작일
	 */
	private String XML_NODE_BRDBEGINEDATE = "BRD_BGN_DD";
	/**
	 * 방송 종료일
	 */
	private String XML_NODE_BRDENDDATE = "BRD_END_DD";
	/**
	 * 대분류장르명
	 */
	private String XML_NODE_CATEGORYLARGECODE = "CTGR_L_CD";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD= "BRD_DD";
	/**
	 * 에피소드 번호
	 */
	private String XML_NODE_EPISODENUMBER = "EPIS_NO";
	/**
	 * 프로그램 코드
	 */
	private String XML_NODE_PGMCD = "PGM_CD";	
	/**
	 * 대분류장르명
	 */
	private String XML_NODE_CTGR_L_NM = "CTGR_L_NM";  // 대분류 장르명
	/**
	 * 제작상태코드
	 */
	private String XML_NODE_DATA_STAT_CD = "DATA_STAT_CD";  // 외주제작사코드
	/**
	 * 등록일시
	 */
	private String XML_NODE_REG_DT = "REG_DT";              // 촬영장소
	/**
	 * 저작권형태설명
	 */
	private String XML_NODE_REGRID = "REGRID";      // 저작권형태설명
	/**
	 * 미디어 id
	 */
	private String XML_NODE_MEDIA_ID = "MEDIA_ID";                // 저작권자명
	/**
	 * 제작상태명
	 */
	private String XML_NODE_DATA_STAT_NM ="DATA_STAT_NM";  // 보존기간코드




	public Object setDO(String _xml) {
		setDO(new ProgramInfoDO());

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
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PROGRAMID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setPgmId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMNAME)) {
				infoDO.setPgmNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAMEPISODE)) {
				infoDO.setPgmEpis(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setMasterId(Long.parseLong(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_BRDBEGINEDATE)) {
				infoDO.setBrdBgnDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRDENDDATE)) {
				infoDO.setBrdEndDd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYLARGECODE)) {
				infoDO.setCtgrLCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_BRD_DD)) {
				infoDO.setBrd_dd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_EPISODENUMBER)) {

				infoDO.setEpisNo(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PGMCD)) {
				infoDO.setPgmCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_NM)){
				infoDO.setCtgr_l_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DATA_STAT_CD)){
				infoDO.setData_stat_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REG_DT)){
				infoDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGRID)){
				infoDO.setReg_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MEDIA_ID)){
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DATA_STAT_NM)){
				infoDO.setData_stat_nm(_nodeValue);
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
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();

		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPgmId() + "</"  + XML_NODE_PROGRAMID + "> \n");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMasterId() + "</"  + XML_NODE_MASTERID + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PROGRAMNAME + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMEPISODE + ">" + infoDO.getPgmEpis() + "</"  + XML_NODE_PROGRAMEPISODE + "> \n");
		_xml.append("<" + XML_NODE_BRDBEGINEDATE + ">" + infoDO.getBrdBgnDd() + "</"  + XML_NODE_BRDBEGINEDATE + "> \n");
		_xml.append("<" + XML_NODE_BRDENDDATE + ">" + infoDO.getBrdEndDd() + "</"  + XML_NODE_BRDENDDATE + "> \n");
		_xml.append("<" + XML_NODE_CATEGORYLARGECODE + ">" + infoDO.getCtgrLCd() + "</"  + XML_NODE_CATEGORYLARGECODE + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_EPISODENUMBER + ">" + infoDO.getEpisNo() + "</"  + XML_NODE_EPISODENUMBER + "> \n");
		_xml.append("<" + XML_NODE_PGMCD + ">" + infoDO.getPgmCd() + "</"  + XML_NODE_PGMCD + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_NM + ">" + CommonUtl.transXmlText(infoDO.getCtgr_l_nm()) + "</"  + XML_NODE_CTGR_L_NM + "> \n");
		_xml.append("<" + XML_NODE_DATA_STAT_CD + ">" + CommonUtl.transXmlText(infoDO.getData_stat_cd()) + "</"  + XML_NODE_DATA_STAT_CD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getReg_id() + "</"  + XML_NODE_REGRID + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_DATA_STAT_NM + ">" + CommonUtl.transXmlText(infoDO.getData_stat_nm()) + "</"  + XML_NODE_DATA_STAT_NM + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

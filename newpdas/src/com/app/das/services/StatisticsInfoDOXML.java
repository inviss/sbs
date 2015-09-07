package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.business.transfer.StatisticsConditionDO;
import com.app.das.util.CommonUtl;
/**
 *  통계  정보 관련 XML파서
 * @author asura207
 *
 */
public class StatisticsInfoDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "statisticsInfo";
	/**
	 * 프로그램 아이디
	 */
	private String XML_NODE_PGM_ID = "PGM_ID";
	
	/**
	 * 마스터 아이디
	 */
	private String XML_NODE_MASTERID = "MASTER_ID";
	/**
	 * 날짜구분(연='1', 월='2', 기간='3')
	 */
	private String XML_NODE_DATEKIND = "DATEKIND"; // 날짜구분(연='1', 월='2', 기간='3')
	/**
	 * 시작일
	 */
	private String XML_NODE_FROMDATE = "FROMDATE";
	/**
	 * 종료일
	 */
	private String XML_NODE_TODATE = "TODATE";
	/**
	 * 분류구분(대분류='1', 중분류='2', 소분류='3')
	 */
	private String XML_NODE_CATEGORYKIND = "CATEGORYKIND";  //분류구분(대분류='1', 중분류='2', 소분류='3')
	/**
	 * 건수계
	 */
	private String XML_NODE_BY_DY_QTY = "BY_DY_QTY";
	/**
	 * 시간계
	 */
	private String XML_NODE_BY_DY_TM = "BY_DY_TM";
	/**
	 * 날짜
	 */
	private String XML_NODE_DD = "DD";
	/**
	 * 날짜그룹
	 */
	private String XML_NODE_DAY = "DAY";
	/**
	 * 건수합계
	 */
	private String XML_NODE_SUM_QTY = "SUM_QTY";
	/**
	 * 시간합계
	 */
	private String XML_NODE_SUM_TM = "SUM_TM";
	/**
	 * 소속
	 */
	private String XML_NODE_GRP = "GRP";  //소속
	/**
	 * 소속명
	 */
	private String XML_NODE_GRP_NM = "GRP_NM";  //소속명
	/**
	 * 본부
	 */
	private String XML_NODE_SEG = "SEG";  //본부
	/**
	 * 본부명
	 */
	private String XML_NODE_SEG_NM = "SEG_NM"; //본부명
	/**
	 * 부서
	 */
	private String XML_NODE_DEPT ="DEPT"; //부서
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM = "DEPT_NM"; //부서명
	/**
	 * 대분류 코드
	 */
	private String XML_NODE_CTGR_L_CD = "CTGR_L_CD"; // 대분류 코드
	/**
	 * 중분류 코드
	 */
	private String XML_NODE_CTGR_M_CD = "CTGR_M_CD"; // 중분류 코드
	/**
	 * 소분류코드
	 */
	private String XML_NODE_CTGR_S_CD = "CTGR_S_CD"; // 소분류 코드
	/**
	 * 대분류명
	 */
	private String XML_NODE_CTGR_L_NM = "CTGR_L_NM";  // 대분류 장르명
	/**
	 * 중분류명
	 */
	private String XML_NODE_CTGR_M_NM = "CTGR_M_NM";  // 중분류 장르명
	/**
	 * 소분류명
	 */
	private String XML_NODE_CTGR_S_NM = "CTGR_S_NM";  // 소분류 장르명
	/**
	 * 아카이브 요청 경로
	 */
	private String XML_NODE_SOURCE_GUBUN = "SOURCE_GUBUN"; 		// 아카이브 요청 경로
	/**
	 * 프로그램명
	 */
	private String XML_NODE_PGM_NM = "PGM_NM";        // 프로그램명
	/**
	 *  미정리량
	 */
	private String XML_NODE_ARRANGE_COUNT ="ARRANGE_COUNT"; // 미정리량
	/**
	 * 미정리 시간
	 */
	private String XML_NODE_ARRANGE_DURATION = "ARRANGE_DURATION"; // 미정리 시간
	/**
	 *  컨텐츠 구분코드
	 */
	private String XML_NODE_CT_CLA ="CT_CLA"; 
	/**
	 *  컨텐츠 구분명
	 */
	private String XML_NODE_CT_CLA_NM = "CT_CLA_NM"; 
	
	//2012.4.25
	/**
	 *  회사코드
	 */
	private String XML_NODE_COCD = "COCD"; 
	
	/**
	 *  채널코드
	 */
	private String XML_NODE_CHENNEL = "CHENNEL"; 
	
	public Object setDO(String _xml) {
		setDO(new StatisticsConditionDO());
		
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
		StatisticsConditionDO infoDO = (StatisticsConditionDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_ID)) {
					infoDO.setPgm_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MASTERID)) {
					infoDO.setMaster_id(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_NM)){
				infoDO.setCtgr_l_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_M_NM)){
				infoDO.setCtgr_m_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_S_NM)){
				infoDO.setCtgr_s_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DATEKIND)){
				infoDO.setDateKind(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_FROMDATE)){
				infoDO.setFromDate(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_TODATE)){
				infoDO.setToDate(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CATEGORYKIND)){
				infoDO.setCategoryKind(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_BY_DY_QTY)){
				infoDO.setBy_dy_qty(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_BY_DY_TM)){
				infoDO.setBy_dy_tm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DD)){
				infoDO.setDd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_GRP)){
				infoDO.setGrp(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SEG)){
				infoDO.setSeg(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DEPT)){
				infoDO.setDept(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_CD)){
				infoDO.setCtgr_l_cd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_M_CD)){
				infoDO.setCtgr_m_cd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_S_CD)){
				infoDO.setCtgr_s_cd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_NM)){
				infoDO.setCtgr_l_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_M_NM)){
				infoDO.setCtgr_m_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_S_NM)){
				infoDO.setCtgr_s_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DAY)){
				infoDO.setDay(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SUM_QTY)){
				infoDO.setSum_qty(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SUM_TM)){
				infoDO.setSum_tm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SOURCE_GUBUN)){
				infoDO.setSource_gubun(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_NM)){
				infoDO.setPgm_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_ARRANGE_COUNT)){
				infoDO.setArrange_count(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_ARRANGE_DURATION)){
				infoDO.setArrange_duration(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SOURCE_GUBUN)){
				infoDO.setSource_gubun(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_CLA)){
				infoDO.setCt_cla(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_CLA_NM)){
				infoDO.setCt_cla_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_COCD)){
				infoDO.setCocd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CHENNEL)){
				infoDO.setChennel(_nodeValue);
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
		StatisticsConditionDO infoDO = (StatisticsConditionDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PGM_ID + ">" + infoDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + infoDO.getMaster_id() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_DATEKIND + ">" + infoDO.getDateKind() + "</"  + XML_NODE_DATEKIND + ">");
		_xml.append("<" + XML_NODE_FROMDATE + ">" + infoDO.getFromDate() + "</"  + XML_NODE_FROMDATE + ">");
		_xml.append("<" + XML_NODE_TODATE + ">" + infoDO.getToDate() + "</"  + XML_NODE_TODATE + ">");
		_xml.append("<" + XML_NODE_CATEGORYKIND + ">" + infoDO.getCategoryKind() + "</"  + XML_NODE_CATEGORYKIND + ">");
		_xml.append("<" + XML_NODE_BY_DY_QTY + ">" + infoDO.getBy_dy_qty() + "</"  + XML_NODE_BY_DY_QTY + ">");
		_xml.append("<" + XML_NODE_BY_DY_TM + ">" + infoDO.getBy_dy_tm() + "</"  + XML_NODE_BY_DY_TM + ">");
		_xml.append("<" + XML_NODE_DD + ">" + infoDO.getDd() + "</"  + XML_NODE_DD + ">");
		_xml.append("<" + XML_NODE_GRP + ">" + infoDO.getGrp() + "</"  + XML_NODE_GRP + ">");
		_xml.append("<" + XML_NODE_SEG + ">" + infoDO.getSeg() + "</"  + XML_NODE_SEG + ">");
		_xml.append("<" + XML_NODE_DEPT + ">" + infoDO.getDept() + "</"  + XML_NODE_DEPT + ">");

		_xml.append("<" + XML_NODE_GRP_NM + ">" + infoDO.getGrp_nm() + "</"  + XML_NODE_GRP_NM + ">");
		_xml.append("<" + XML_NODE_SEG_NM + ">" + infoDO.getSeg_nm() + "</"  + XML_NODE_SEG_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + infoDO.getDept_nm() + "</"  + XML_NODE_DEPT_NM + ">");
		
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + ">");
		_xml.append("<" + XML_NODE_CTGR_M_CD + ">" + infoDO.getCtgr_m_cd() + "</"  + XML_NODE_CTGR_M_CD + ">");
		_xml.append("<" + XML_NODE_CTGR_S_CD + ">" + infoDO.getCtgr_s_cd() + "</"  + XML_NODE_CTGR_S_CD + ">");
		_xml.append("<" + XML_NODE_CTGR_L_NM + ">" + infoDO.getCtgr_l_nm() + "</"  + XML_NODE_CTGR_L_NM + ">");
		_xml.append("<" + XML_NODE_CTGR_M_NM + ">" + infoDO.getCtgr_m_nm() + "</"  + XML_NODE_CTGR_M_NM + ">");
		_xml.append("<" + XML_NODE_CTGR_S_NM + ">" + infoDO.getCtgr_s_nm() + "</"  + XML_NODE_CTGR_S_NM + ">");
		
		_xml.append("<" + XML_NODE_SUM_QTY + ">" + infoDO.getSum_qty() + "</"  + XML_NODE_SUM_QTY + ">");
		_xml.append("<" + XML_NODE_SUM_TM + ">" + infoDO.getSum_tm()+ "</"  + XML_NODE_SUM_TM + ">");
		_xml.append("<" + XML_NODE_DAY + ">" + infoDO.getDay()+ "</"  + XML_NODE_DAY + ">");
		_xml.append("<" + XML_NODE_SOURCE_GUBUN + ">" + infoDO.getSource_gubun()+ "</"  + XML_NODE_SOURCE_GUBUN + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + infoDO.getPgm_nm()+ "</"  + XML_NODE_PGM_NM + ">");
		_xml.append("<" + XML_NODE_ARRANGE_COUNT + ">" + infoDO.getArrange_count()+ "</"  + XML_NODE_ARRANGE_COUNT + ">");
		_xml.append("<" + XML_NODE_ARRANGE_DURATION + ">" + infoDO.getArrange_duration()+ "</"  + XML_NODE_ARRANGE_DURATION + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	
	public String getSubXML2() {
		StatisticsConditionDO infoDO = (StatisticsConditionDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + infoDO.getPgm_nm() + "</"  + XML_NODE_PGM_NM + ">");
		_xml.append("<" + XML_NODE_CT_CLA + ">" + infoDO.getCt_cla() + "</"  + XML_NODE_CT_CLA + ">");
		_xml.append("<" + XML_NODE_CT_CLA_NM + ">" + infoDO.getCt_cla_nm() + "</"  + XML_NODE_CT_CLA_NM + ">");
		_xml.append("<" + XML_NODE_BY_DY_QTY + ">" + infoDO.getBy_dy_qty() + "</"  + XML_NODE_BY_DY_QTY + ">");
		_xml.append("<" + XML_NODE_BY_DY_TM + ">" + infoDO.getBy_dy_tm() + "</"  + XML_NODE_BY_DY_TM + ">");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
}

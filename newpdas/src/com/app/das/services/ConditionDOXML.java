package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.WorkStatusConditionDO;
import com.app.das.util.CommonUtl;

/**
 *  검색 조건 정보 관련 XML파서
 * @author asura207
 *
 */
public class ConditionDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "conditionInfo";
	
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD2 = "info";
	
	/**
	 * 시작일
	 */
	private String XML_NODE_FROMDATE = "fromDate";
	/**
	 * 종료일
	 */
	private String XML_NODE_TODATE = "toDate";
	/**
	 * 날짜 구분 ( 촬영일 = '001', 등록일 = '002', 정리완료일 = '003')
	 */
	private String XML_NODE_DATEKIND = "dateKind";
	/**
	 * 정리전여부
	 */
	private String XML_NODE_ARRANGEBEFORE = "arrangeBfYn";
	/**
	 * 정리중여부
	 */
	private String XML_NODE_ARRANGEING = "arrangeIngYn"; 
	/**
	 * 정리완료여부
	 */
	private String XML_NODE_ARRANGECOMPLETE = "arrangeCompYn";
	/**
	 * 검수완료여부
	 */
	private String XML_NODE_COMPLETE = "completYn";
	/**
	 * 준비중 여부
	 */
	private String XML_NODE_PREPARE = "startingYn";
	/**
	 * 인제스트 재지시여부
	 */
	private String XML_NODE_REORDER = "reOrdersYn";
	/**
	 * 아카이브상태여부
	 */
	private String XML_NODE_ARCHIVE = "archiveYn";
	/**
	 * 2차 아카이브 재지시 여부
	 */
	private String XML_NODE_SECONFARCHIVE = "secondArchiveYn";
	/**
	 * 오류 여부
	 */
	private String XML_NODE_ERROR = "errorYn";
	/**
	 * 주조 인제스트  
	 */
	private String XML_NODE_MCUID = "mcuidYn";
	/**
	 * 검색어 
	 */
	private String XML_NODE_SEARCHKEY = "searchKey";
	/**
	 * 검색 컬럼 
	 */
	private String XML_NODE_COLUMNNAME = "columnName";
	/**
	 * 시작위치 
	 */
	private String XML_NODE_STARTPOS = "startPos";
	/**
	 * 종료위치
	 */
	private String XML_NODE_ENDPOS = "endPos";
	/**
	 * 검색콤보
	 */
	private String XML_NODE_SEARCHCOMBO = "searchCombo";
	/**
	 * 검색 콤보 값
	 */
	private String XML_NODE_SEARCHCOMBOVALUE ="searchComboValue";
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD = "ctgr_l_cd";
	/**
	 * 정렬 컬럼
	 */
	private String XML_NODE_SORTCOLUME = "sortColume";
	/**
	 * 정렬값
	 */
	private String XML_NODE_SORTVALUE = "sortValue";
	/**
	 * 프로그램id
	 */
	private String XML_NODE_PGM_ID = "pgm_id";	
	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID = "master_id";
	/**
	 * 스토리지 명
	 */
	private String XML_NODE_STOREG_NM = "storage";
	
	//2012.4.18
	/**
	 * 채널
	 */
	private String XML_NODE_CHENNEL = "chennel";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD = "cocd";
	
	
	/**
	 * 전체 조회갯수
	 */
	private String XML_NODE_TOTAL_COUNT = "total_count";
	/**
	 * 시작페이지
	 */
	private String XML_NODE_START_PAGE = "start_page";
	/**
	 * 종료페이지
	 */
	//private String XML_NODE_END_PAGE = "end_page";
	/**
	 * 요청 등록일 
	 */
	private String XML_NODE_START_REG_DD = "start_reg_dd";
	/**
	 * 요청종료일
	 */
	private String XML_NODE_END_REG_DD = "end_reg_dd";
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQ_ID = "req_id";
	
	/**
	 * 제목
	 */
	private String XML_NODE_TITLE = "title";
	/**
	 * 영상인스턴스id
	 */
	private String XML_NODE_CTI_ID = "cti_id";
	/**
	 * 장르대분류명
	 */
	private String XML_NODE_CTGR_L_NM = "ctgr_l_nm";
	/**
	 * 요청일
	 */
	private String XML_NODE_REQ_DD = "req_dt";
	/**
	 * 방송일
	 */
	private String XML_NODE_BRDDD = "brd_dd";
	/**
	 * 아카이브상태
	 */
	private String XML_NODE_ARCH_STAT = "arch_stat";
	/**
	 * 아카이브 진행률
	 */
	private String XML_NODE_PROGRESS = "progress";
	
	
	public Object setDO(String _xml) {
	
		setDO(new WorkStatusConditionDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}else if(_nodeName.equals(XML_NODE_HEAD2)){
				setData((Element)_node);
			}
        }
		
		return getDO();
	}
	
	public Object setData(Element pElement) {
		WorkStatusConditionDO infoDO = (WorkStatusConditionDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_FROMDATE)) {
				infoDO.setFromDate(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TODATE)) {
				infoDO.setToDate(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DATEKIND)) {
				infoDO.setDateKind(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARRANGEBEFORE)) {
				infoDO.setArrangeBfYn(_nodeValue);			
			}
			else if(_nodeName.equals(XML_NODE_ARRANGEING)) {
				infoDO.setArrangeIngYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARRANGECOMPLETE)) {
				infoDO.setArrangeCompYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COMPLETE)) {
				infoDO.setCompletYn(_nodeValue);		
			}else if(_nodeName.equals(XML_NODE_PREPARE)) {
				infoDO.setStartingYn(_nodeValue);		
			}
			else if(_nodeName.equals(XML_NODE_REORDER)) {
				infoDO.setReOrdersYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ARCHIVE)) {
				infoDO.setArchiveYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SECONFARCHIVE)) {
				infoDO.setSecondArchiveYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ERROR)) {
				infoDO.setErrorYn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MCUID)) {
				infoDO.setMcuid(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEARCHKEY)) {
				infoDO.setSearchKey(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_COLUMNNAME)) {
				infoDO.setColumnName(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STARTPOS)) {
				infoDO.setStartPos(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_ENDPOS)) {
				infoDO.setEndPos(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equals(XML_NODE_SEARCHCOMBO)){
				infoDO.setSearchCombo(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEARCHCOMBOVALUE)){
				infoDO.setSearchComboValue(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CTGR_L_CD)){
				infoDO.setCtgr_l_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SORTCOLUME)){
				infoDO.setSortColume(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SORTVALUE)){
				infoDO.setSortValue(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTER_ID)){
				infoDO.setMaster_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_STOREG_NM)){
				infoDO.setStorage(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COCD)){
				infoDO.setCocd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CHENNEL)){
				infoDO.setChennel(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_START_PAGE)){
				infoDO.setStart_page(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_START_REG_DD)){
				infoDO.setStart_req_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_END_REG_DD)){
				infoDO.setEnd_req_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REQ_ID)){
				infoDO.setReq_id(_nodeValue);
			}
			
        }
	    
	    return infoDO;
	}
	
	
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}

	public String getSubXML() {
		WorkStatusConditionDO infoDO = (WorkStatusConditionDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_FROMDATE + ">" + infoDO.getFromDate() + "</"  + XML_NODE_FROMDATE + ">");
		_xml.append("<" + XML_NODE_TODATE + ">" + infoDO.getToDate() + "</"  + XML_NODE_TODATE + ">");
		_xml.append("<" + XML_NODE_DATEKIND + ">" + infoDO.getDateKind() + "</"  + XML_NODE_DATEKIND + ">");
		_xml.append("<" + XML_NODE_ARRANGEBEFORE + ">" + infoDO.getArrangeBfYn() + "</"  + XML_NODE_ARRANGEBEFORE + ">");
		_xml.append("<" + XML_NODE_ARRANGEING + ">" + infoDO.getArrangeIngYn() + "</"  + XML_NODE_ARRANGEING + ">");
		_xml.append("<" + XML_NODE_ARRANGECOMPLETE + ">" + infoDO.getArrangeCompYn() + "</"  + XML_NODE_ARRANGECOMPLETE + ">");
		_xml.append("<" + XML_NODE_COMPLETE + ">" + infoDO.getCompletYn() + "</"  + XML_NODE_COMPLETE + ">");
		_xml.append("<" + XML_NODE_REORDER + ">" + infoDO.getReOrdersYn() + "</"  + XML_NODE_REORDER + ">");
		_xml.append("<" + XML_NODE_ARCHIVE + ">" + infoDO.getArchiveYn() + "</"  + XML_NODE_ARCHIVE + ">");
		_xml.append("<" + XML_NODE_SECONFARCHIVE + ">" + infoDO.getSecondArchiveYn() + "</"  + XML_NODE_SECONFARCHIVE + ">");
		_xml.append("<" + XML_NODE_ERROR + ">" + infoDO.getErrorYn() + "</"  + XML_NODE_ERROR + ">");
		_xml.append("<" + XML_NODE_MCUID + ">" + infoDO.getMcuidYn() + "</"  + XML_NODE_MCUID + ">");
		_xml.append("<" + XML_NODE_SEARCHKEY + ">" + infoDO.getSearchKey() + "</"  + XML_NODE_SEARCHKEY + ">");
		_xml.append("<" + XML_NODE_COLUMNNAME + ">" + infoDO.getColumnName() + "</"  + XML_NODE_COLUMNNAME + ">");
		_xml.append("<" + XML_NODE_STARTPOS + ">" + infoDO.getStartPos() + "</"  + XML_NODE_STARTPOS + ">");
		_xml.append("<" + XML_NODE_ENDPOS + ">" + infoDO.getEndPos() + "</"  + XML_NODE_ENDPOS + ">");
		_xml.append("<" + XML_NODE_SEARCHCOMBO + ">" + infoDO.getSearchCombo() + "</"  + XML_NODE_SEARCHCOMBO + ">");
		_xml.append("<" + XML_NODE_SEARCHCOMBOVALUE + ">" + infoDO.getSearchComboValue() + "</"  + XML_NODE_SEARCHCOMBOVALUE + ">");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + infoDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + ">");
		_xml.append("<" + XML_NODE_PGM_ID + ">" + infoDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + ">");
		
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	
	
	
	


	
	public String getSubXML3() {
		WorkStatusConditionDO infoDO = (WorkStatusConditionDO)getDO();
		
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<totalinfo> \n");
		_xml.append("<" + XML_NODE_TOTAL_COUNT + ">" + infoDO.getTotalcount() + "</"  + XML_NODE_TOTAL_COUNT + "> \n");
		_xml.append("</totalinfo>");
		
		return _xml.toString();
	}
	
	
	
	public String getReqListXML() {
		WorkStatusConditionDO infoDO = (WorkStatusConditionDO)getDO();
		
		StringBuffer buf = new StringBuffer();
		
	    buf.append("<" + XML_NODE_HEAD2 + ">");
	
		buf.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(infoDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		buf.append("<" + XML_NODE_CTI_ID + ">" + infoDO.getCti_id() + "</"  + XML_NODE_CTI_ID + ">");
		buf.append("<" + XML_NODE_CTGR_L_NM + ">" + infoDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_NM + ">");
		buf.append("<" + XML_NODE_REQ_DD + ">" + infoDO.getReq_dt() + "</"  + XML_NODE_REQ_DD + ">");
		buf.append("<" + XML_NODE_BRDDD + ">" + infoDO.getBrd_dd()  + "</" + XML_NODE_BRDDD + ">" );
		buf.append("<" + XML_NODE_ARCH_STAT + ">" + infoDO.getArch_stat() + "</"  + XML_NODE_ARCH_STAT + ">");
		buf.append("<" + XML_NODE_PROGRESS + ">" + infoDO.getProgress()  + "</" + XML_NODE_PROGRESS + ">" );
		
		buf.append("</" + XML_NODE_HEAD2 + ">");

		
		return buf.toString();
	}
	
}

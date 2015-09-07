package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.constants.DASBusinessConstants;
import com.app.das.business.transfer.MetadataMstInfoDO;
import com.app.das.log.ErrorPropHandler;
import com.app.das.log.SearchPropHandler;
import com.app.das.util.CommonUtl;
import com.konantech.search.data.ParameterVO;
/**
 *   코난 검색 정보 관련 XML파서
 * @author asura207
 *
 */
public class SearchInfoDOXML extends DOXml {

	private static SearchPropHandler SearchHandler = SearchPropHandler.getInstance();
	/**
	 * xml해더
	 */
	private final String XML_NODE_HEAD = "searchInfo";	                                     
	/** 검색키워드 */
	private final String XML_NODE_KWD = "kwd";   				 	
	/** 이전검색어 배열 */
	private final String XML_NODE_PREKWDS = "preKwds";			
	/** 검색대상 필드 */
	private final String XML_NODE_SRCHFD = "srchFd";				
	/** 유저 ID */
	private final String XML_NODE_USERID = "userId";				
	/** 사이트명 */
	private final String XML_NODE_SITENAME = "siteName";			
	/** 추천검색어 정보 */
	private final String XML_NODE_RECKWD = "recKwd";				
	/** 재검색 여부 (boolean) */
	private final String XML_NODE_RESRCHFLAG = "reSrchFlag";		
	/** 페이지사이즈 */
	private final String XML_NODE_PAGESIZE = "pageSize";			
	/** 검색결과페이지번호 */
	private final String XML_NODE_PAGENUM = "pageNum";			
	/** 정렬 */
	private final String XML_NODE_SORT = "sort";					
	/** 상세검색 여부 플래그 */
	private final String XML_NODE_DETAILSEARCH = "detailSearch";
	/** 제외어 */
	private final String XML_NODE_EXCLUSIVEKWD = "exclusiveKwd";	
	/** 날짜선택사항 */
	private final String XML_NODE_DATE = "date";					
	/** 시작일 */
	private final String XML_NODE_STARTDATE = "startDate";		
	/** 종료일 */
	private final String XML_NODE_ENDDATE = "endDate";			
	/** 시나리오 */
	private final String XML_NODE_SCN = "scn";					
	/** 상세검색 키워드 */
	private final String XML_NODE_GRPKWD = "grpKwd";				
	/** 상세검색 대상필드 */
	private final String XML_NODE_GRPSRCHFDNM = "grpSrchFd";		
	/** 상세검색 AndOr 구분 */
	private final String XML_NODE_GRPANDOR = "grpAndOr";			
	/** 주제영상 구분 */
	private final String XML_NODE_SCEAN_GUBUN = "sceanGubun";		
	/** 상세검색 날짜 구분(시작일) */
	private final String XML_NODE_GRPSTARTDD = "grpstartdd";		
	/** 상세검색 날짜 구분(종료일) */
	private final String XML_NODE_GRPENDDD = "grpenddd";			
	/** 정렬 컬럼명 */
	private final String XML_NODE_SORTCOLUMN = "sortcolumn";		
	/** 프로그램소재 구분 */
	private final String XML_NODE_CTGR_L_CD = "ctgr_l_cd";		
	/** 검색 경로 구분 */
	private final String XML_NODE_BROWSER = "browser";		
	/** 채널구분 */
	private final String XML_NODE_CHANNEL_CD = "channels_cd";		
	/** 소재 구분 */
	private final String XML_NODE_MATERIAL = "materials_yn";		
	/** 프로그램 구분 */
	private final String XML_NODE_PGM = "programs_yn";		

	public Object setDO(String _xml) {
		setDO(new ParameterVO());

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
		ParameterVO infoDO = (ParameterVO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_KWD)) {
				infoDO.setKwd(_nodeValue);
			}
			if(_nodeName.equalsIgnoreCase(XML_NODE_PREKWDS)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setPreKwds(_nodeValue.split(","));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SRCHFD)) {
				infoDO.setSrchFd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USERID)) {
				infoDO.setUserId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SITENAME)) {
				infoDO.setSiteName(_nodeValue);						
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RECKWD)) {
				infoDO.setRecKwd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RESRCHFLAG )) {
				if(_nodeValue.equals("")||_nodeValue==null)
					_nodeValue = "false";
				infoDO.setReSrchFlag(Boolean.valueOf(_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PAGESIZE )) {
				if(_nodeValue.equals("")||_nodeValue==null)
					_nodeValue = "0";
				infoDO.setPageSize(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PAGENUM )) {
				if(_nodeValue.equals("")||_nodeValue==null)
					_nodeValue = "0";
				infoDO.setPageNum(Integer.parseInt(_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SORT)) {
				infoDO.setSort(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DETAILSEARCH )) {
				if(_nodeValue.equals("")||_nodeValue==null)
					_nodeValue = "false";
				infoDO.setDetailSearch(Boolean.valueOf(_nodeValue));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EXCLUSIVEKWD )) {
				infoDO.setExclusiveKwd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DATE)) {
				infoDO.setDate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_STARTDATE)) {
				infoDO.setStartDate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ENDDATE)) {
				infoDO.setEndDate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SCEAN_GUBUN)) {
				infoDO.setSceanGubun(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BROWSER)) {
				infoDO.setBrowser(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SCN)) {
				if(_nodeValue.equals("")||_nodeValue==null||SearchHandler.getProperty(_nodeValue).equals("")){
					infoDO.setScn(SearchHandler.getProperty(DASBusinessConstants.Search_scenario.SCN_DAS_PROGRAM));
				}else {
					infoDO.setScn(SearchHandler.getProperty(_nodeValue));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GRPKWD)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setGrpKwd(_nodeValue.split(","));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GRPSRCHFDNM)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setGrpSrchFd(_nodeValue.toUpperCase().split(","));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GRPANDOR)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setGrpAndOr(_nodeValue.split(","));
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_GRPSTARTDD)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setGrpStartDD(_nodeValue.split(","));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GRPENDDD)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setGrpEndDD(_nodeValue.split(","));
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_SORTCOLUMN)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setSortColunm(_nodeValue);
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_CD)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setCtgrlcd(_nodeValue);
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHANNEL_CD)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setChannel_cd(_nodeValue.split(","));
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setProgram_yn(_nodeValue.split(","));
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_MATERIAL)) {
				if(_nodeValue.equals("")||_nodeValue==null){
					_nodeValue="";
				}else{
					infoDO.setMaterial_yn(_nodeValue.split(","));
				}
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
		//		MetadataMstInfoDO infoDO = (MetadataMstInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}
}

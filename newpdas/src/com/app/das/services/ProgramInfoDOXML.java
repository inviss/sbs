package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.ProgramInfoDO;
import com.app.das.util.CommonUtl;
/**
 *   프로그램 메타 정보 관련 XML파서
 * @author asura207
 *
 */
public class ProgramInfoDOXML extends DOXml {
	
	private Logger logger = Logger.getLogger(ProgramInfoDOXML.class);
	
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
	 * 대분류
	 */
	private String XML_NODE_CATEGORYLARGECODE = "CTGR_L_CD";
	/**
	 * 중분류
	 */
	private String XML_NODE_CATEGORYMEDIUMCODE = "CTGR_M_CD";
	/**
	 * 소분류
	 */
	private String XML_NODE_CATEGORYSMALLCODE = "CTGR_S_CD";
	/**
	 * 에피소드 번호
	 */
	private String XML_NODE_EPISODENUMBER = "EPIS_NO";
	/**
	 * 미디어 id
	 */
	private String XML_NODE_MEDIACODE = "MEDIA_CD";
	/**
	 * 채널
	 */
	private String XML_NODE_CHANCODE = "CHAN_CD";
	/**
	 * 프로그램 코드
	 */
	private String XML_NODE_PGMCD = "PGM_CD";	
	/**
	 * 제작부서 
	 */
	private String XML_NODE_PRD_DEPT_NM = "PRD_DEPT_NM";
	/**
	 * 편성프로명
	 */
	private String XML_NODE_SCHD_PGM_NM = "SCHD_PGM_NM";
	/**
	 * 수상내역
	 */
	private String XML_NODE_AWARD_HSTR = "AWARD_HSTR";
	/**
	 * 파일럿 여부
	 */
	private String XML_NODE_PILOT_YN = "PILOT_YN";
	/**
	 * 부모코드
	 */
	private String XML_NODE_PARENTS_CD = "PARENTS_CD";
	/**
	 * 사용여부
	 */
	private String XML_NODE_USE_YN = "USE_YN";
	/**
	 * 일괄수정 조회시 검색 대상 콤보
	 */
	private String XML_NODE_GUBUN = "GUBUN";  //
	/**
	 * 컨텐츠 구분명
	 */
	private String XML_NODE_CT_CLA_NM = "CT_CLA_NM";  // 
	/**
	 * 대분류 장르명
	 */
	private String XML_NODE_CTGR_L_NM = "CTGR_L_NM";  // 
	/**
	 * 중분류 장르명
	 */
	private String XML_NODE_CTGR_M_NM = "CTGR_M_NM";  // 
	/**
	 *  소분류 장르명
	 */
	private String XML_NODE_CTGR_S_NM = "CTGR_S_NM";  //
	/**
	 * 화질명
	 */
	private String XML_NODE_VD_QLTY_NM = "VD_QLTY_NM"; // 
	/**
	 * 방송길이
	 */
	private String XML_NODE_BRD_LENG = "BRD_LENG";	// 
	/**
	 *  방송길이
	 */
	//private String XML_NODE_PGM_CD = "PGM_CD";	//
	/**
	 * 제작부서명
	 */
	private String XML_NODE_PRDT_DEPT_NM = "PRDT_DEPT_NM";   // 
	/**
	 *  외주제작사코드
	 */
	private String XML_NODE_PRDT_IN_OUTS_CD = "PRDT_IN_OUTS_CD";  //
	/**
	 * 촬영장소
	 */
	private String XML_NODE_CMR_PLACE = "CMR_PLACE";              // 
	/**
	 * 저작권형태설명
	 */
	private String XML_NODE_CPRT_TYPE_DSC = "CPRT_TYPE_DSC";      // 
	/**
	 * 저작권자명
	 */
	private String XML_NODE_CPRTR_NM = "CPRTR_NM";                // 
	/**
	 * 저작권형태코드\N(001 일체소유 002 일부소유 003 저작권없음)
	 */
	private String XML_NODE_CPRT_TYPE = "CPRT_TYPE";                       // 
	/**
	 * 보존기한코드
	 */
	private String XML_NODE_RSV_PRD_CD ="RSV_PRD_CD";    
	/**
	 * 상태코드
	 */
	private String XML_NODE_DATA_STAT_CD = "DATA_STAT_CD";  // 
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT = "REG_DT";              // 촬영장소
	/**
	 * 등록자id
	 */
	private String XML_NODE_REGRID = "REGRID";      // 저작권형태설명
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIA_ID = "MEDIA_ID";                // 저작권자명
	/**
	 * 데이터상태명
	 */
	private String XML_NODE_DATA_STAT_NM ="DATA_STAT_NM";  // 보존기간코드
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD ="BRD_DD";
	/**
	 * 화질코드
	 */
	private String XML_NODE_ASP_RTO_CD ="ASP_RTO_CD";
	/**
	 * 청구번호
	 */
	private String XML_NODE_REQ_CD ="REQ_CD";
	/**
	 * 재아카이브여부
	 */
	private String XML_NODE_RERUN ="RERUN";
	/**
	 * 마지막방송
	 */
	private String XML_NODE_FILNAL_BRD_YN ="FILNAL_BRD_YN";
	/**
	 * 컬럼병
	 */
	private String XML_NODE_COULMNNAME ="coulmnname";
	/**
	 * 구분상세코드
	 */
	private String XML_NODE_SCL_CD ="scl_cd";
	/**
	 * 부제
	 */
	private String XML_NODE_SUB_TTL ="SUB_TTL";
	/**
	 * 검색유형
	 */
	private String XML_NODE_SRCH_TYPE ="SRCH_TYPE";
	/**
	 * 페이지
	 */
	private String XML_NODE_PAGE ="PAGE";
	/**
	 * 총페이지
	 */
	private String XML_NODE_TOTALPAGE ="TOTALPAGE";
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM ="DEPT_NM";
	/**
	 * 검색어
	 */
	private String XML_NODE_SEARCH_WORD ="SEARCH_WORD";
	/**
	 * 녹음방식코드
	 */
	private String XML_NODE_RECORD_TYPE_CD ="RECORD_TYPE_CD";
	/**
	 * 원제작사명
	 */
	private String XML_NODE_ORG_PRDR_NM ="ORG_PRDR_NM";	 


	//2012.5.10

	/**
	 * 회사	 */
	private String XML_NODE_COCD ="COCD";	 


	/**
	 * 계열사	 */
	private String XML_NODE_CHENNEL ="CHENNEL";	 


	/**
	 * 아카이브 경로	 */
	private String XML_NODE_ARCH_ROUTE ="ARCH_ROUTE";	 



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
			else if(_nodeName.equals(XML_NODE_CATEGORYMEDIUMCODE)) {
				infoDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYSMALLCODE)) {
				infoDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPISODENUMBER)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					infoDO.setEpisNo(Integer.parseInt(_nodeValue));
				}
			}
			else if(_nodeName.equals(XML_NODE_MEDIACODE)) {
				infoDO.setMediaCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CHANCODE)) {
				infoDO.setChanCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PGMCD)) {
				infoDO.setPgmCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRD_DEPT_NM)) {
				infoDO.setPrd_Dept_Nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SCHD_PGM_NM)) {
				infoDO.setSchd_Pgm_Nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_AWARD_HSTR)) {
				infoDO.setAward_Hstr(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PILOT_YN)) {
				infoDO.setPilot_Yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PARENTS_CD)) {
				infoDO.setParents_cd(_nodeValue);
			}	
			else if(_nodeName.equals(XML_NODE_USE_YN)) {
				infoDO.setUse_yn(_nodeValue);
			}	
			else if(_nodeName.equals(XML_NODE_GUBUN)){
				infoDO.setGubun(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CT_CLA_NM)){
				infoDO.setCt_cla_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_L_NM)){
				infoDO.setCtgr_l_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_M_NM)){
				infoDO.setCtgr_m_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_CTGR_S_NM)){
				infoDO.setCtgr_s_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_VD_QLTY_NM)){
				infoDO.setVd_qlty_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_BRD_LENG)){
				infoDO.setBrd_leng(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PRDT_DEPT_NM)){
				infoDO.setPrdt_dept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PRDT_IN_OUTS_CD)){
				infoDO.setPrdt_in_outs_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CMR_PLACE)){
				infoDO.setCmr_place(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRT_TYPE_DSC)){
				infoDO.setCprt_type_dsc(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRTR_NM)){
				infoDO.setCprtr_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CPRT_TYPE)){
				infoDO.setCprt_type(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RSV_PRD_CD)){
				infoDO.setRsv_prd_cd(_nodeValue);
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
			else if(_nodeName.equals(XML_NODE_BRD_DD)){
				infoDO.setData_stat_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASP_RTO_CD)){
				infoDO.setReg_dt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_CD)){
				infoDO.setReg_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RERUN)){
				infoDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FILNAL_BRD_YN)){
				infoDO.setData_stat_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COULMNNAME)){
				infoDO.setClf_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SCL_CD)){
				infoDO.setScl_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SUB_TTL)){
				infoDO.setSub_ttl(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUB_TTL)){
				infoDO.setSub_ttl(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SRCH_TYPE)){
				infoDO.setSRCH_TYPE(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PAGE)){
				infoDO.setPage(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_DEPT_NM)){
				if(!_nodeValue.equals("")){
					infoDO.setDept_nm(_nodeValue);
				}else{
					infoDO.setDept_nm("");	
				}

			}
			else if(_nodeName.equals(XML_NODE_SEARCH_WORD)){
				infoDO.setSearch_word(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RECORD_TYPE_CD)){
				infoDO.setRecord_type_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_ORG_PRDR_NM)){
				infoDO.setOrg_prdr_nm(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_COCD)){
				infoDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CHENNEL)){
				infoDO.setChennel(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_ARCH_ROUTE)){
				infoDO.setArchive_path(_nodeValue);
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
		_xml.append("<" + XML_NODE_CATEGORYMEDIUMCODE + ">" + infoDO.getCtgrMCd() + "</"  + XML_NODE_CATEGORYMEDIUMCODE + "> \n");
		_xml.append("<" + XML_NODE_CATEGORYSMALLCODE + ">" + infoDO.getCtgrSCd() + "</"  + XML_NODE_CATEGORYSMALLCODE + "> \n");
		_xml.append("<" + XML_NODE_EPISODENUMBER + ">" + infoDO.getEpis_No() + "</"  + XML_NODE_EPISODENUMBER + "> \n");
		_xml.append("<" + XML_NODE_MEDIACODE + ">" + infoDO.getMediaCd() + "</"  + XML_NODE_MEDIACODE + "> \n");
		_xml.append("<" + XML_NODE_CHANCODE + ">" + infoDO.getChanCd() + "</"  + XML_NODE_CHANCODE + "> \n");
		_xml.append("<" + XML_NODE_PGMCD + ">" + infoDO.getPgmCd() + "</"  + XML_NODE_PGMCD + "> \n");
		_xml.append("<" + XML_NODE_PRD_DEPT_NM + ">" + CommonUtl.transXmlText(infoDO.getPrd_Dept_Nm()) + "</"  + XML_NODE_PRD_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_SCHD_PGM_NM + ">" + CommonUtl.transXmlText(infoDO.getSchd_Pgm_Nm()) + "</"  + XML_NODE_SCHD_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_AWARD_HSTR + ">" + infoDO.getAward_Hstr() + "</"  + XML_NODE_AWARD_HSTR + "> \n");
		_xml.append("<" + XML_NODE_PILOT_YN + ">" + infoDO.getPilot_Yn() + "</"  + XML_NODE_PILOT_YN + "> \n");
		_xml.append("<" + XML_NODE_PARENTS_CD + ">" + infoDO.getParents_cd() + "</"  + XML_NODE_PARENTS_CD + "> \n");
		_xml.append("<" + XML_NODE_USE_YN + ">" + infoDO.getUse_yn() + "</"  + XML_NODE_USE_YN + "> \n");	
		_xml.append("<" + XML_NODE_CT_CLA_NM + ">" + infoDO.getCt_cla_nm() + "</"  + XML_NODE_CT_CLA_NM + "> \n");
		_xml.append("<" + XML_NODE_CTGR_L_NM + ">" + infoDO.getCtgr_l_nm() + "</"  + XML_NODE_CTGR_L_NM + "> \n");
		_xml.append("<" + XML_NODE_CTGR_M_NM + ">" + infoDO.getCtgr_m_nm() + "</"  + XML_NODE_CTGR_M_NM + "> \n");
		_xml.append("<" + XML_NODE_CTGR_S_NM + ">" + infoDO.getCtgr_s_nm() + "</"  + XML_NODE_CTGR_S_NM + "> \n");
		_xml.append("<" + XML_NODE_VD_QLTY_NM + ">" + infoDO.getVd_qlty_nm() + "</"  + XML_NODE_VD_QLTY_NM + "> \n");
		_xml.append("<" + XML_NODE_BRD_LENG + ">" + infoDO.getBrd_leng() + "</"  + XML_NODE_BRD_LENG + "> \n");
		_xml.append("<" + XML_NODE_PRDT_DEPT_NM + ">" + CommonUtl.transXmlText(infoDO.getPrdt_dept_nm()) + "</"  + XML_NODE_PRDT_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_PRDT_IN_OUTS_CD + ">" + infoDO.getPrdt_in_outs_cd() + "</"  + XML_NODE_PRDT_IN_OUTS_CD + "> \n");
		_xml.append("<" + XML_NODE_CMR_PLACE + ">" + CommonUtl.transXmlText(infoDO.getCmr_place()) + "</"  + XML_NODE_CMR_PLACE + "> \n");
		_xml.append("<" + XML_NODE_CPRT_TYPE_DSC + ">" + infoDO.getCprt_type_dsc() + "</"  + XML_NODE_CPRT_TYPE_DSC + "> \n");
		_xml.append("<" + XML_NODE_CPRTR_NM + ">" + CommonUtl.transXmlText(infoDO.getCprtr_nm()) + "</"  + XML_NODE_CPRTR_NM + "> \n");
		_xml.append("<" + XML_NODE_CPRT_TYPE + ">" + infoDO.getCprt_type() + "</"  + XML_NODE_CPRT_TYPE + "> \n");
		_xml.append("<" + XML_NODE_RSV_PRD_CD + ">" + infoDO.getRsv_prd_cd() + "</"  + XML_NODE_RSV_PRD_CD + "> \n");
		_xml.append("<" + XML_NODE_DATA_STAT_CD + ">" + infoDO.getData_stat_cd() + "</"  + XML_NODE_DATA_STAT_CD + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_REGRID + ">" + infoDO.getReg_id() + "</"  + XML_NODE_REGRID + "> \n");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + "> \n");
		_xml.append("<" + XML_NODE_DATA_STAT_NM + ">" + CommonUtl.transXmlText(infoDO.getData_stat_nm()) + "</"  + XML_NODE_DATA_STAT_NM + "> \n");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + infoDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + "> \n");
		_xml.append("<" + XML_NODE_ASP_RTO_CD + ">" + infoDO.getReg_dt() + "</"  + XML_NODE_ASP_RTO_CD + "> \n");
		_xml.append("<" + XML_NODE_REQ_CD + ">" + infoDO.getReg_id() + "</"  + XML_NODE_REQ_CD + "> \n");
		_xml.append("<" + XML_NODE_RERUN + ">" + infoDO.getMedia_id() + "</"  + XML_NODE_RERUN + "> \n");
		_xml.append("<" + XML_NODE_FILNAL_BRD_YN + ">" + infoDO.getData_stat_nm() + "</"  + XML_NODE_FILNAL_BRD_YN + "> \n");
		_xml.append("<" + XML_NODE_SUB_TTL + ">" + CommonUtl.transXmlText(infoDO.getSub_ttl()) + "</"  + XML_NODE_SUB_TTL + "> \n");
		_xml.append("<" + XML_NODE_RECORD_TYPE_CD + ">" + infoDO.getRecord_type_cd() + "</"  + XML_NODE_RECORD_TYPE_CD + "> \n");
		_xml.append("<" + XML_NODE_ORG_PRDR_NM + ">" + CommonUtl.transXmlText(infoDO.getOrg_prdr_nm()) + "</"  + XML_NODE_ORG_PRDR_NM + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}





	public String getSubXML2() {
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");

		_xml.append("<" + XML_NODE_PROGRAMNAME + ">" + CommonUtl.transXmlText(infoDO.getPgmNm()) + "</"  + XML_NODE_PROGRAMNAME + "> \n");
		_xml.append("<" + XML_NODE_PGMCD + ">" + infoDO.getPgmCd() + "</"  + XML_NODE_PGMCD + "> \n");

		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



	public String getSubXML3() {
		ProgramInfoDO infoDO = (ProgramInfoDO)getDO();
		if(logger.isInfoEnabled()) {
			logger.info("total count: "+infoDO.getTotalpage());
		}
		StringBuffer _xml = new StringBuffer();	
		_xml.append("<totalinfo> \n");
		_xml.append("<" + XML_NODE_TOTALPAGE + ">" + infoDO.getTotalpage() + "</"  + XML_NODE_TOTALPAGE + "> \n");
		_xml.append("</totalinfo>");

		return _xml.toString();
	}




}

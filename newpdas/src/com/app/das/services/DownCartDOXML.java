package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DownCartDO;
import com.app.das.util.CommonUtl;
/**
 *   다운로드 카드 정보 관련 XML파서
 * @author asura207
 *
 */
public class DownCartDOXML extends DOXml {

//	DownCartDO cartDO;	
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "downCart";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CARTNO = "CART_NO";
	/**
	 * 카트순번
	 */
	private String XML_NODE_CARTSEQ = "CART_SEQ";
	/**
	 * 자료구분코드
	 */
	private String XML_NODE_DATACLASSIFICATIONCODE = "DATA_CLF_CD";
	/**
	 * 우선순위코드
	 */
	private String XML_NODE_PRIORCODE = "PRIO_CD";
	/**
	 * 저장위치
	 */
	private String XML_NODE_STORAGELOC = "STRG_LOC";
	/**
	 * 사용제한포함여부
	 */
	private String XML_NODE_CONTROLCODE = "RIST_YN";
	/**
	 * 승인내용
	 */
	private String XML_NODE_APPROVALCOMMENT = "APP_CONT";
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQUESTERID = "REQ_USRID";
	/**
	 * 요청자명
	 */
	private String XML_NODE_REQUESTERNAME = "REQ_NM";
	/**
	 * 요청일시
	 */
	private String XML_NODE_REQUESTDATE = "REQ_DT";
	/**
	 * 요청사유
	 */
	private String XML_NODE_REQ_CONT = "REQ_CONT";
	/**
	 * 다운로드일시
	 */
	private String XML_NODE_DOWNDATE = "DOWN_DT";
	/**
	 * 승인일자
	 */
	private String XML_NODE_APPROVALDATE = "APP_DT";
	/**
	 * 다운로드제목
	 */
	private String XML_NODE_DOWNTITLE = "DOWN_SUBJ";
	/**
	 * 보증인ID
	 */
	private String XML_NODE_GENERATORID = "GAURANTOR_ID";
	/**
	 * 등록일시
	 */
	private String XML_NODE_REGDATE = "REG_DT";
	/**
	 * 등록자ID
	 */
	private String XML_NODE_REGISTERID = "REGRID";
	/**
	 * 수정일시
	 */
	private String XML_NODE_MODDATE = "MOD_DT";
	/**
	 * 수정자ID
	 */
	private String XML_NODE_MODIFIER = "MODRID";
	/**
	 * 화질코드
	 */
	private String XML_NODE_VEDIOQUALITY = "VD_QLTY";
	/**
	 * 종횡비코드
	 */
	private String XML_NODE_ASPRTOCODE = "ASP_RTO_CD";
	/**
	 * 카트상태
	 */
	private String XML_NODE_CARTSTATUS = "CART_STAT";
	/**
	 *다운로드 구분(001: PDS , 002:NDS , 003:데정팀 , 004:tape-out, 005:계열사 )
	 */
	private String XML_NODE_DOWN_GUBUN = "DOWN_GUBUN";
	/**
	 * 타시스템 저장(전송)위치
	 */
	private String XML_NODE_OUT_STRG_LOC = "OUT_STRG_LOC";
	/**
	 * 다운로드 카트 제목
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 회사코드
	 */
	private String XML_NODE_CO_CD = "CO_CD";      //  회사코드
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD = "co_cd";      //  회사코드
	/**
	 * 본부코드
	 */
	private String XML_NODE_SEG_CD = "SEG_CD";     //  본부코드
	/**
	 * 조직부서코드
	 */
	private String XML_NODE_DEPATEMENTCODE = "DEPT_CD";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CART_NOS = "CART_NOS";
	/**
	 * 카트순번
	 */
	private String XML_NODE_CART_SEQS = "CART_SEQS";
	/**
	 * 풀 or 부분 다운로드
	 */
	private String XML_NODE_FULL_YN = "FULL_YN";
	/**
	 *  사용등급 
	 */
	private String XML_NODE_RIST_CLF_NM = "RIST_CLF_NM";
	/**
	 * 파일경로 -> nodecation
	 */
	private String XML_NODE_FL_PATH = "nodecaption";
	/**
	 * 카테고리 -> nodeid
	 */
	private String XML_NODE_NODE_ID = "nodeid";
	/**
	 *  스토리지 명 -> cell name 
	 */
	private String XML_NODE_CELL_NAME = "cellname";
	/**
	 *  유저ID
	 */
	private String XML_NODE_USER_ID = "USER_ID";
	/**
	 * 논리적 스토리지명
	 */
	private String XML_NODE_PHYSICALTREE="physicaltree";
	/**
	 * 물리적 스토리지명
	 */
	private String XML_NODE_LOGICALTREE="logicaltree";
	
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO="epis_no";
	
	
	
	/**
	 * 계열사
	 */
	private String XML_NODE_CHENNEL="chennel";
	/**
	 * URL
	 */
	private String XML_NODE_URL="url";
	
	
	/**
	 * 영상id
	 */
	private String XML_NODE_CT_ID="ct_id";
	
	/**
	 * 영상인스턴스id
	 */
	private String XML_NODE_CTI_ID="cti_id";

	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID="master_id";

	public Object setDO(String _xml) {
		setDO(new DownCartDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equalsIgnoreCase(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
        }
		
		return getDO();
	}
	
	public Object setData(Element pElement) {
		DownCartDO cartDO = (DownCartDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_CARTNO)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}	
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DATACLASSIFICATIONCODE)) {
				cartDO.setDataClfCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PRIORCODE)) {
				cartDO.setPrioCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_STORAGELOC)) {
				cartDO.setStrgLoc(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONTROLCODE)) {
				cartDO.setRistYn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_APPROVALCOMMENT)) {
				cartDO.setAppCont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQUESTERID)) {
				cartDO.setReqUsrid(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQUESTERNAME)) {
				cartDO.setReqNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQUESTDATE)) {
				cartDO.setReqDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWNDATE)) {
				cartDO.setDownDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_APPROVALDATE)) {
				cartDO.setAppDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWNTITLE)) {
				cartDO.setDownSubj(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_GENERATORID)) {
				cartDO.setGaurantorId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REGDATE)) {
				cartDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REGISTERID)) {
				cartDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MODDATE)) {
				cartDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MODIFIER)) {
				cartDO.setModrId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DEPATEMENTCODE)) {
				cartDO.setDeptCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VEDIOQUALITY)) {
				cartDO.setVdQlty(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ASPRTOCODE)) {
				cartDO.setAspRtoCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CARTSTATUS)) {
				cartDO.setCartStat(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_GUBUN)) {
				cartDO.setDown_gubun(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_OUT_STRG_LOC)) {
				cartDO.setOut_strg_loc(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TITLE)){
				cartDO.setTitle(_nodeValue);
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_CO_CD)){
				cartDO.setCo_cd(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_COCD)){
				cartDO.setCo_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEG_CD)){
				cartDO.setSeg_cd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DEPATEMENTCODE)){
				cartDO.setDeptCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CART_NOS)){
				cartDO.setCartNos(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CART_SEQS)){
				cartDO.setCartSeqs(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CARTSEQ)){
				cartDO.setCartSeq(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FULL_YN)){
				cartDO.setFull_yn(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FL_PATH)){
				cartDO.setFl_path(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_NODE_ID)){
				cartDO.setCategory(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CELL_NAME)){
				cartDO.setStoragename(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USER_ID)){
				cartDO.setUser_id(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_RIST_CLF_NM)){
				cartDO.setRist_clf_cd(_nodeValue);
			}
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PHYSICALTREE)){
				cartDO.setPhysicaltree(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_LOGICALTREE)){
				cartDO.setLogicaltree(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_CONT)){
				cartDO.setReg_cont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHENNEL)){
				cartDO.setChennel(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_URL)){
				cartDO.setUrl(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_ID)){
				cartDO.setCt_id(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
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
		DownCartDO cartDO = (DownCartDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CARTNO.toLowerCase() + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DATACLASSIFICATIONCODE.toLowerCase() + ">" + cartDO.getDataClfCd() + "</"  + XML_NODE_DATACLASSIFICATIONCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_PRIORCODE.toLowerCase() + ">" + cartDO.getPrioCd() + "</"  + XML_NODE_PRIORCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_STORAGELOC.toLowerCase() + ">" + cartDO.getStrgLoc() + "</"  + XML_NODE_STORAGELOC.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONTROLCODE.toLowerCase() + ">" + cartDO.getRistYn() + "</"  + XML_NODE_CONTROLCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_APPROVALCOMMENT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getAppCont()) + "</"  + XML_NODE_APPROVALCOMMENT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQUESTERID.toLowerCase() + ">" + cartDO.getReqUsrid() + "</"  + XML_NODE_REQUESTERID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQUESTERNAME.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getReqNm()) + "</"  + XML_NODE_REQUESTERNAME.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQUESTDATE.toLowerCase() + ">" + cartDO.getReqDt() + "</"  + XML_NODE_REQUESTDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWNDATE.toLowerCase() + ">" + cartDO.getDownDt() + "</"  + XML_NODE_DOWNDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_APPROVALDATE.toLowerCase() + ">" + cartDO.getAppDt() + "</"  + XML_NODE_APPROVALDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWNTITLE.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getDownSubj()) + "</"  + XML_NODE_DOWNTITLE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_GENERATORID.toLowerCase() + ">" + cartDO.getGaurantorId() + "</"  + XML_NODE_GENERATORID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REGDATE.toLowerCase() + ">" + cartDO.getRegDt() + "</"  + XML_NODE_REGDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REGISTERID.toLowerCase() + ">" + cartDO.getRegrId() + "</"  + XML_NODE_REGISTERID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MODDATE.toLowerCase() + ">" + cartDO.getModDt() + "</"  + XML_NODE_MODDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MODIFIER.toLowerCase() + ">" + cartDO.getModrId() + "</"  + XML_NODE_MODIFIER.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DEPATEMENTCODE.toLowerCase() + ">" + cartDO.getDeptCd() + "</"  + XML_NODE_DEPATEMENTCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_VEDIOQUALITY.toLowerCase() + ">" + cartDO.getVdQlty() + "</"  + XML_NODE_VEDIOQUALITY.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_ASPRTOCODE.toLowerCase() + ">" + cartDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CARTSTATUS.toLowerCase() + ">" + cartDO.getCartStat() + "</"  + XML_NODE_CARTSTATUS.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_GUBUN.toLowerCase() + ">" + cartDO.getDown_gubun() + "</"  + XML_NODE_DOWN_GUBUN.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_OUT_STRG_LOC.toLowerCase() + ">" + cartDO.getOut_strg_loc() + "</"  + XML_NODE_OUT_STRG_LOC.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_TITLE.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CO_CD.toLowerCase() + ">" + cartDO.getCo_cd() + "</"  + XML_NODE_CO_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_SEG_CD.toLowerCase() + ">" + cartDO.getSeg_cd() + "</"  + XML_NODE_SEG_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_NM.toLowerCase() + ">" + cartDO.getRist_clf_cd() + "</"  + XML_NODE_RIST_CLF_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_FULL_YN.toLowerCase() + ">" + cartDO.getFull_yn() + "</"  + XML_NODE_FULL_YN.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_EPIS_NO.toLowerCase() + ">" + cartDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MASTER_ID.toLowerCase() + ">" + cartDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CTI_ID.toLowerCase() + ">" + cartDO.getCti_id() + "</"  + XML_NODE_CTI_ID.toLowerCase() + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}
	
	/*
	public String toXML() {
		DownCartDO cartDO = (DownCartDO)getDO();

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + "<" + XML_NODE_HEAD + ">";
		_xml = _xml + "<" + XML_NODE_CARTNO + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO + ">";
		_xml = _xml + "<" + XML_NODE_DATACLASSIFICATIONCODE + ">" + cartDO.getDataClfCd() + "</"  + XML_NODE_DATACLASSIFICATIONCODE + ">";
		_xml = _xml + "<" + XML_NODE_PRIORCODE + ">" + cartDO.getPrioCd() + "</"  + XML_NODE_PRIORCODE + ">";
		_xml = _xml + "<" + XML_NODE_STORAGELOC + ">" + cartDO.getStrgLoc() + "</"  + XML_NODE_STORAGELOC + ">";
		_xml = _xml + "<" + XML_NODE_CONTROLCODE + ">" + cartDO.getRistYn() + "</"  + XML_NODE_CONTROLCODE + ">";
		_xml = _xml + "<" + XML_NODE_APPROVALCOMMENT + ">" + cartDO.getAppCont() + "</"  + XML_NODE_APPROVALCOMMENT + ">";
		_xml = _xml + "<" + XML_NODE_REQUESTERID + ">" + cartDO.getReqUsrid() + "</"  + XML_NODE_REQUESTERID + ">";
		_xml = _xml + "<" + XML_NODE_REQUESTERNAME + ">" + cartDO.getReqNm() + "</"  + XML_NODE_REQUESTERNAME + ">";
		_xml = _xml + "<" + XML_NODE_REQUESTDATE + ">" + cartDO.getReqDt() + "</"  + XML_NODE_REQUESTDATE + ">";
		_xml = _xml + "<" + XML_NODE_DOWNDATE + ">" + cartDO.getDownDt() + "</"  + XML_NODE_DOWNDATE + ">";
		_xml = _xml + "<" + XML_NODE_APPROVALDATE + ">" + cartDO.getAppDt() + "</"  + XML_NODE_APPROVALDATE + ">";
		_xml = _xml + "<" + XML_NODE_DOWNTITLE + ">" + cartDO.getDownSubj() + "</"  + XML_NODE_DOWNTITLE + ">";
		_xml = _xml + "<" + XML_NODE_GENERATORID + ">" + cartDO.getGaurantorId() + "</"  + XML_NODE_GENERATORID + ">";
		_xml = _xml + "<" + XML_NODE_REGDATE + ">" + cartDO.getRegDt() + "</"  + XML_NODE_REGDATE + ">";
		_xml = _xml + "<" + XML_NODE_REGISTERID + ">" + cartDO.getRegrId() + "</"  + XML_NODE_REGISTERID + ">";
		_xml = _xml + "<" + XML_NODE_MODDATE + ">" + cartDO.getModDt() + "</"  + XML_NODE_MODDATE + ">";
		_xml = _xml + "<" + XML_NODE_MODIFIER + ">" + cartDO.getModrId() + "</"  + XML_NODE_MODIFIER + ">";
		_xml = _xml + "<" + XML_NODE_DEPATEMENTCODE + ">" + cartDO.getDeptCd() + "</"  + XML_NODE_DEPATEMENTCODE + ">";
		_xml = _xml + "<" + XML_NODE_VEDIOQUALITY + ">" + cartDO.getVdQlty() + "</"  + XML_NODE_VEDIOQUALITY + ">";
		_xml = _xml + "<" + XML_NODE_ASPRTOCODE + ">" + cartDO.getAspRtoCd() + "</"  + XML_NODE_ASPRTOCODE + ">";
		_xml = _xml + "<" + XML_NODE_CARTSTATUS + ">" + cartDO.getCartStat() + "</"  + XML_NODE_CARTSTATUS + ">";
		_xml = _xml + "</" + XML_NODE_HEAD + ">";
		_xml = _xml + "</das>";
		
		return _xml;
	}
	*/
	
}

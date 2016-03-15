package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.CartItemDO;
import com.app.das.util.CommonUtl;

/**
 *  다운로드 정보 관련 XML파서
 * @author asura207
 *
 */
public class CartContDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "cartContents";
	/**
	 * 마스터ID
	 */	
	private String XML_NODE_MASTERID = "MASTER_ID";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CARTNO = "CART_NO";
	/**
	 * 카트내순번
	 */
	private String XML_NODE_CARTSEQUENCE = "CART_SEQ";
	/**
	 * 사용제한구분코드
	 */
	private String XML_NODE_CONTROLCODE = "RIST_CLF_CD";
	/**
	 * 콘텐츠ID
	 */
	private String XML_NODE_CONTENTSID = "CT_ID"; 
	/**
	 * 콘텐츠인스탄스ID
	 */
	private String XML_NODE_CTIID = "CTI_ID"; 
	/**
	 * 시작점
	 */
	private String XML_NODE_SOM = "SOM";
	/**
	 * 종료점
	 */
	private String XML_NODE_EOM = "EOM";
	/**
	 * duration
	 */
	private String XML_NODE_DURATION = "DURATION";
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
	 * 대분류코드
	 */
	private String XML_NODE_CATEGORYLARGE = "CTGR_L_CD";
	/**
	 * 중분류코드
	 */
	private String XML_NODE_CATEGORYMIDDLE = "CTGR_M_CD";
	/**
	 * 소분류코드
	 */
	private String XML_NODE_CATEGORYSMALL = "CTGR_S_CD";
	/**
	 * 콘텐츠내용
	 */
	private String XML_NODE_CONTENTSSUBJECT = "CT_CONT";
	/**
	 * 콘텐츠
	 */
	private String XML_NODE_CONTENTSNAME = "CT_NM";
	/**
	 * 시작프레임
	 */
	private String XML_NODE_SFRAME = "S_FRAME";
	/**
	 * 청구번호
	 */
	private String XML_NODE_REQ_CD = "REQ_CD";
	/**
	 * 카트제목명
	 */
	private String XML_NODE_DOWN_SUBJ = "DOWN_SUBJ";
	/**
	 * 화질명
	 */
	private String XML_NODE_VD_QLTY_NM = "VD_QLTY_NM";
	/**
	 * 종횡비명
	 */
	private String XML_NODE_ASP_RTO_NM = "ASP_RTO_NM";
	/**
	 * 사용제한건수
	 */
	private String XML_NODE_USE_LIMIT_COUNT = "USE_LIMIT_COUNT";
	/**
	 * 사용제한
	 */
	private String XML_NODE_USE_LIMIT_FLAG = "USE_LIMIT_FLAG";
	/**
	 * 외주제작승인여부(Y:승인 , N:승인취소)
	 */
	private String XML_NODE_OUTSOURCING_APPROVE = "OUTSOURCING_APPROVE";
	/**
	 * 타이틀명
	 */
	private String XML_NODE_TITLE = "TITLE";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "EPIS_NO";
	/**
	 * 다운진행률
	 */
	private String XML_NODE_DOWN_VOL = "DOWN_VOL";
	/**
	 * 다운로드 유형명
	 */
	private String XML_NODE_DOWN_TYP_NM = "DOWN_TYP_NM";
	/**
	 * 다운로드 유형 (FULL : F , PARTIAL : P)
	 */
	private String XML_NODE_DOWN_TYP = "DOWN_TYP";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_PGM_NM ="PGM_NM";
	/**
	 * 승인 내용
	 */
	private String XML_NODE_APP_CONT = "APP_CONT";
	/**
	 * 사용제한 명
	 */
	private String XML_NODE_RIST_CLF_NM = "RIST_CLF_NM";
	/**
	 * 다운요청상태(001 사용 중 002 임시저장 003 승인요청 004 승인 005 승인거부 \N 006 다운로드 진행중 007 다운로드 완료)
	 */
	private String XML_NODE_DOWN_STAT = "DOWN_STAT";
	/**
	 * 다운로드 구분명
	 */
	private String XML_NODE_DOWN_GUBUN_NM = "DOWN_GUBUN_NM";
	/**
	 * 요청 사유명
	 */
	private String XML_NODE_REQ_CONT = "REQ_CONT";
	/**
	 * 전송진행률
	 */
	private String XML_NODE_TRANS_VOL = "TRANS_VOL";
	/**
	 * 방송일/촬영일
	 */
	private String XML_NODE_BRD_DD = "BRD_DD";
	/**
	 *  full or partial
	 */
	private String XML_NODE_FULL_YN = "FULL_YN"; 	
	/**
	 *  촬영일
	 */
	private String XML_NODE_FM_DT = "FM_DT"; 	
	/**
	 * 대분류코드
	 */
	private String XML_NODE_CTGR_L_CD = "CTGR_L_CD"; 	
	/**
	 * job_Status ( Q:대기,  I:진행중,  C:완료)
	 */
	private String XML_NODE_STATUS = "STATUS"; 	
	/**
	 *  미디어 id
	 */
	private String XML_NODE_MEDIA_ID = "MEDIA_ID"; 	
	/**
	 *  논리적 저장위치
	 */
	private String XML_NODE_LOGICAL_TREE = "LOGICAL_TREE"; 	
	/**
	 *  물리적 저장위치
	 */
	private String XML_NODE_PHYSICAL_TREE = "PHYSICAL_TREE"; 	
	/**
	 *  경로
	 */
	private String XML_NODE_STRG_LOC = "STRG_LOC"; 	
	/**
	 *  wmv 경로
	 */
	private String XML_NODE_PATH = "PATH"; 	
	/**
	 *  wmv 명
	 */
	private String XML_NODE_FL_NM = "FL_NM"; 	
	
	
	/**
	 *  승인자id
	 */
	private String XML_NODE_SBS_USER_ID = "SBS_USER_ID"; 	
	
	/**
	 *  1,2차 승인 승인자명
	 */
	private String XML_NODE_APPROVE_NM = "APPROVE_NM"; 	
	
	
	
	//2012.4.27
	
	/**
	 *  회사코드
	 */
	private String XML_NODE_COCD = "COCD"; 	
	
	
	/**
	 *  채널코드
	 */
	private String XML_NODE_CHENNEL = "CHENNEL"; 	
	
	/**
	 *  채널명
	 */
	private String XML_NODE_CHENNEL_NM = "CHENNEL_NM"; 
	
	
	/**
	 *  채널명
	 */
	private String XML_NODE_TARGET_CMS_ID = "target_cms_id"; 
	/**
	 *  tx id
	 */
	private String XML_NODE_TRANSCATION_ID = "transaction_id"; 
	
	public Object setDO(String _xml) {
		setDO(new CartContDO());
		
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
		CartContDO cartDO = (CartContDO)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_CARTNO)) {
				cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CARTSEQUENCE)) {
				cartDO.setCartSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONTROLCODE)) {
				cartDO.setRistClfCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONTENTSID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCtId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTIID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setCtiId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SOM)) {
				cartDO.setSom(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EOM)) {
				cartDO.setEom(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
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
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CATEGORYLARGE)) {
				cartDO.setCtgrLCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CATEGORYMIDDLE)) {
				cartDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CATEGORYSMALL)) {
				cartDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONTENTSSUBJECT)) {
				cartDO.setCtCont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CONTENTSNAME)) {
				cartDO.setCtNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SFRAME)) {
				cartDO.setSFrame(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_CD)) {
				cartDO.setReq_CD(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PGM_NM)) {
				cartDO.setPgm_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_APP_CONT)) {
				cartDO.setApp_cont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RIST_CLF_NM)) {
				cartDO.setRist_clf_nm(_nodeValue);
			}
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_SUBJ)) {
				cartDO.setDown_subj(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VD_QLTY_NM)) {
				cartDO.setVd_qlty_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ASP_RTO_NM)) {
				cartDO.setAsp_rto_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USE_LIMIT_FLAG)) {
				cartDO.setUse_limit_flag(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USE_LIMIT_COUNT)) {
				cartDO.setUse_limit_count(_nodeValue);
			}
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TITLE)) {
				cartDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EPIS_NO)) {
				cartDO.setEpis_no(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_VOL)) {
				cartDO.setDown_vol(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_TYP_NM)) {
				cartDO.setDown_typ_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_OUTSOURCING_APPROVE)) {
				cartDO.setOutsourcing_approve(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TRANS_VOL)) {
				cartDO.setTrans_vol(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_CONT)) {
				cartDO.setReq_cont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_GUBUN_NM)) {
				cartDO.setDown_gubun_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_BRD_DD)) {
				cartDO.setBrd_dd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_STAT)) {
				cartDO.setDown_stat(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FULL_YN)) {
				cartDO.setFull_yn(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_TYP)) {
				cartDO.setDown_typ(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_TYP)) {
				cartDO.setDown_typ(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_STATUS)) {
				cartDO.setJob_status(_nodeValue);
			}
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FM_DT)) {
				cartDO.setFm_dt(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTGR_L_CD)) {
				cartDO.setCtgrLCd(_nodeValue);
			}
			
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_LOGICAL_TREE)) {
				cartDO.setLogical_tree(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_PHYSICAL_TREE)) {
				cartDO.setPhysical_tree(_nodeValue);
			}
			
			else if(_nodeName.equalsIgnoreCase(XML_NODE_STRG_LOC)) {
				cartDO.setStrg_loc(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SBS_USER_ID)) {
				cartDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COCD)) {
				cartDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CHENNEL)) {
				cartDO.setChennel(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TARGET_CMS_ID)) {
				cartDO.setTarget_cms_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TRANSCATION_ID)) {
				cartDO.setTranscation_id(_nodeValue);
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
		_xml.append("<" + XML_NODE_MASTERID.toLowerCase() + ">" + cartDO.getMasterId() + "</"  + XML_NODE_MASTERID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CARTNO.toLowerCase() + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CARTSEQUENCE.toLowerCase() + ">" + cartDO.getCartSeq() + "</"  + XML_NODE_CARTSEQUENCE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONTROLCODE.toLowerCase() + ">" + cartDO.getRistClfCd() + "</"  + XML_NODE_CONTROLCODE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONTENTSID.toLowerCase() + ">" + cartDO.getCtId() + "</"  + XML_NODE_CONTENTSID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CTIID.toLowerCase() + ">" + cartDO.getCtiId() + "</"  + XML_NODE_CTIID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_SOM.toLowerCase() + ">" + cartDO.getSom() + "</"  + XML_NODE_SOM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_EOM.toLowerCase() + ">" + cartDO.getEom() + "</"  + XML_NODE_EOM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DURATION.toLowerCase() + ">" + cartDO.getDuration() + "</"  + XML_NODE_DURATION.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REGDATE.toLowerCase() + ">" + cartDO.getRegDt() + "</"  + XML_NODE_REGDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REGISTERID.toLowerCase() + ">" + cartDO.getRegrId() + "</"  + XML_NODE_REGISTERID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MODDATE.toLowerCase() + ">" + cartDO.getModDt() + "</"  + XML_NODE_MODDATE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MODIFIER.toLowerCase() + ">" + cartDO.getModrId() + "</"  + XML_NODE_MODIFIER.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CATEGORYLARGE.toLowerCase() + ">" + cartDO.getCtgrLCd() + "</"  + XML_NODE_CATEGORYLARGE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CATEGORYMIDDLE.toLowerCase() + ">" + cartDO.getCtgrMCd() + "</"  + XML_NODE_CATEGORYMIDDLE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CATEGORYSMALL.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getCtgrSCd()) + "</"  + XML_NODE_CATEGORYSMALL.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONTENTSSUBJECT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getCtCont()) + "</"  + XML_NODE_CONTENTSSUBJECT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONTENTSNAME.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getCtNm()) + "</"  + XML_NODE_CONTENTSNAME.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_SFRAME.toLowerCase() + ">" + cartDO.getSFrame() + "</"  + XML_NODE_SFRAME.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQ_CD.toLowerCase() + ">" + cartDO.getReq_CD() + "</"  + XML_NODE_REQ_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_PGM_NM.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_APP_CONT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getApp_cont()) + "</"  + XML_NODE_APP_CONT.toLowerCase() + ">");
		//_xml = _xml + "<" + XML_NODE_APP_CONT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getApp_cont())+ "</"  + XML_NODE_APP_CONT.toLowerCase() + ">";
		_xml.append("<" + XML_NODE_RIST_CLF_NM.toLowerCase() + ">" + cartDO.getRist_clf_nm() + "</"  + XML_NODE_RIST_CLF_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_SUBJ.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getDown_subj()) + "</"  + XML_NODE_DOWN_SUBJ.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_VD_QLTY_NM.toLowerCase() + ">" + cartDO.getVd_qlty_nm() + "</"  + XML_NODE_VD_QLTY_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_ASP_RTO_NM.toLowerCase() + ">" + cartDO.getAsp_rto_nm() + "</"  + XML_NODE_ASP_RTO_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_COUNT.toLowerCase() + ">" + cartDO.getUse_limit_count() + "</"  + XML_NODE_USE_LIMIT_COUNT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_FLAG.toLowerCase() + ">" + cartDO.getUse_limit_flag() + "</"  + XML_NODE_USE_LIMIT_FLAG.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_TITLE.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_EPIS_NO.toLowerCase() + ">" + cartDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_VOL.toLowerCase() + ">" + cartDO.getDown_vol() + "</"  + XML_NODE_DOWN_VOL.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_TYP_NM.toLowerCase() + ">" + cartDO.getDown_typ_nm() + "</"  + XML_NODE_DOWN_TYP_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_GUBUN_NM.toLowerCase() + ">" + cartDO.getDown_gubun_nm() + "</"  + XML_NODE_DOWN_GUBUN_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQ_CONT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getReq_cont()) + "</"  + XML_NODE_REQ_CONT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_TRANS_VOL.toLowerCase() + ">" + cartDO.getTrans_vol() + "</"  + XML_NODE_TRANS_VOL.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_BRD_DD.toLowerCase() + ">" + cartDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_STAT.toLowerCase() + ">" + cartDO.getDown_stat() + "</"  + XML_NODE_DOWN_STAT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_STATUS.toLowerCase() + ">" + cartDO.getJob_status() + "</"  + XML_NODE_STATUS.toLowerCase() + ">");
		//_xml.append("<" + XML_NODE_CTGR_L_CD.toLowerCase() + ">" + cartDO.getCtgrLCd() + "</"  + XML_NODE_CTGR_L_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_FM_DT.toLowerCase() + ">" + cartDO.getFm_dt() + "</"  + XML_NODE_FM_DT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MEDIA_ID.toLowerCase() + ">" + cartDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_PATH.toLowerCase() + ">" + cartDO.getPath() + "</"  + XML_NODE_PATH.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_FL_NM.toLowerCase() + ">" + cartDO.getFl_nm() + "</"  + XML_NODE_FL_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_APPROVE_NM.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getApprove_nm()) + "</"  + XML_NODE_APPROVE_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_COCD.toLowerCase() + ">" + cartDO.getCocd() + "</"  + XML_NODE_COCD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CHENNEL.toLowerCase() + ">" + cartDO.getChennel() + "</"  + XML_NODE_CHENNEL.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CHENNEL_NM .toLowerCase()+ ">" + cartDO.getChennel_nm() + "</"  + XML_NODE_CHENNEL_NM.toLowerCase() + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	
}

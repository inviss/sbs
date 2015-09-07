package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.CartContDO;
import com.app.das.util.CommonUtl;

/**
 *  다운로드 정보 관련 XML파서(멀티저장)
 * @author asura207
 *
 */
public class CartContsDOXML extends DOXml {

	CartContDO cartDO;
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
	private String XML_NODE_PGM_NM ="PGM_NM";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_APP_CONT = "APP_CONT";
	/**
	 * 승인 내용
	 */
	private String XML_NODE_RIST_CLF_NM = "RIST_CLF_NM";
	/**
	 * 사용제한 명
	 */
	private String XML_NODE_REQ_CONT = "REQ_CONT";
	
	
	
	public Object setDO(String _xml) {
	
		List result = new ArrayList();
		
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
        	cartDO = new CartContDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}
			result.add(cartDO);
        }
		return getDO();
	}
	
	public Object setData(Element pElement) {
		List result = (List)getDO();
		
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
	    	Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			if(_nodeName.equals(XML_NODE_CARTNO)) {
				cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CARTSEQUENCE)) {
				cartDO.setCartSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CONTROLCODE)) {
				cartDO.setRistClfCd(_nodeValue);
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
			else if(_nodeName.equals(XML_NODE_SOM)) {
				cartDO.setSom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EOM)) {
				cartDO.setEom(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DURATION)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setDuration(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_REGDATE)) {
				cartDO.setRegDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REGISTERID)) {
				cartDO.setRegrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODDATE)) {
				cartDO.setModDt(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MODIFIER)) {
				cartDO.setModrId(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYLARGE)) {
				cartDO.setCtgrLCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYMIDDLE)) {
				cartDO.setCtgrMCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CATEGORYSMALL)) {
				cartDO.setCtgrSCd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSSUBJECT)) {
				cartDO.setCtCont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CONTENTSNAME)) {
				cartDO.setCtNm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SFRAME)) {
				cartDO.setSFrame(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_CD)) {
				cartDO.setReq_CD(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_MASTERID)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					cartDO.setMasterId(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_PGM_NM)) {
				cartDO.setPgm_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_APP_CONT)) {
				cartDO.setApp_cont(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_RIST_CLF_NM)) {
				cartDO.setRist_clf_nm(_nodeValue);
			}
			
			else if(_nodeName.equals(XML_NODE_DOWN_SUBJ)) {
				cartDO.setDown_subj(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VD_QLTY_NM)) {
				cartDO.setVd_qlty_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ASP_RTO_NM)) {
				cartDO.setAsp_rto_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_LIMIT_FLAG)) {
				cartDO.setUse_limit_flag(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_LIMIT_COUNT)) {
				cartDO.setUse_limit_count(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TITLE)) {
				cartDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_EPIS_NO)) {
				cartDO.setEpis_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DOWN_VOL)) {
				cartDO.setDown_vol(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DOWN_TYP_NM)) {
				cartDO.setDown_typ_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_OUTSOURCING_APPROVE)) {
				cartDO.setOutsourcing_approve(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_REQ_CONT)) {
				cartDO.setReq_cont(_nodeValue);
			}
			
        }
	    
	    return result;
	}
	public String toXML() {
		
		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";
		
		return _xml;
	}

	public String getSubXML() {
		CartContDO cartDO = (CartContDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_MASTERID + ">" + cartDO.getMasterId() + "</"  + XML_NODE_MASTERID + ">");
		_xml.append("<" + XML_NODE_CARTNO + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO + ">");
		_xml.append("<" + XML_NODE_CARTSEQUENCE + ">" + cartDO.getCartSeq() + "</"  + XML_NODE_CARTSEQUENCE + ">");
		_xml.append("<" + XML_NODE_CONTROLCODE + ">" + cartDO.getRistClfCd() + "</"  + XML_NODE_CONTROLCODE + ">");
		_xml.append("<" + XML_NODE_CONTENTSID + ">" + cartDO.getCtId() + "</"  + XML_NODE_CONTENTSID + ">");
		_xml.append("<" + XML_NODE_CTIID + ">" + cartDO.getCtiId() + "</"  + XML_NODE_CTIID + ">");
		_xml.append("<" + XML_NODE_SOM + ">" + cartDO.getSom() + "</"  + XML_NODE_SOM + ">");
		_xml.append("<" + XML_NODE_EOM + ">" + cartDO.getEom() + "</"  + XML_NODE_EOM + ">");
		_xml.append("<" + XML_NODE_DURATION + ">" + cartDO.getDuration() + "</"  + XML_NODE_DURATION + ">");
		_xml.append("<" + XML_NODE_REGDATE + ">" + cartDO.getRegDt() + "</"  + XML_NODE_REGDATE + ">");
		_xml.append("<" + XML_NODE_REGISTERID + ">" + cartDO.getRegrId() + "</"  + XML_NODE_REGISTERID + ">");
		_xml.append("<" + XML_NODE_MODDATE + ">" + cartDO.getModDt() + "</"  + XML_NODE_MODDATE + ">");
		_xml.append("<" + XML_NODE_MODIFIER + ">" + cartDO.getModrId() + "</"  + XML_NODE_MODIFIER + ">");
		_xml.append("<" + XML_NODE_CATEGORYLARGE + ">" + cartDO.getCtgrLCd() + "</"  + XML_NODE_CATEGORYLARGE + ">");
		_xml.append("<" + XML_NODE_CATEGORYMIDDLE + ">" + cartDO.getCtgrMCd() + "</"  + XML_NODE_CATEGORYMIDDLE + ">");
		_xml.append("<" + XML_NODE_CATEGORYSMALL + ">" + cartDO.getCtgrSCd() + "</"  + XML_NODE_CATEGORYSMALL + ">");
		_xml.append("<" + XML_NODE_CONTENTSSUBJECT + ">" + CommonUtl.transXmlText(cartDO.getCtCont()) + "</"  + XML_NODE_CONTENTSSUBJECT + ">");
		_xml.append("<" + XML_NODE_CONTENTSNAME + ">" + cartDO.getCtNm() + "</"  + XML_NODE_CONTENTSNAME + ">");
		_xml.append("<" + XML_NODE_SFRAME + ">" + cartDO.getSFrame() + "</"  + XML_NODE_SFRAME + ">");
		_xml.append("<" + XML_NODE_REQ_CD + ">" + cartDO.getReq_CD() + "</"  + XML_NODE_REQ_CD + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(cartDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM + ">");
		_xml.append("<" + XML_NODE_APP_CONT + ">" + CommonUtl.transXmlText(cartDO.getApp_cont()) + "</"  + XML_NODE_APP_CONT + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_NM + ">" + cartDO.getRist_clf_nm() + "</"  + XML_NODE_RIST_CLF_NM + ">");
		_xml.append("<" + XML_NODE_DOWN_SUBJ + ">" + CommonUtl.transXmlText(cartDO.getDown_subj()) + "</"  + XML_NODE_DOWN_SUBJ + ">");
		_xml.append("<" + XML_NODE_VD_QLTY_NM + ">" + cartDO.getVd_qlty_nm() + "</"  + XML_NODE_VD_QLTY_NM + ">");
		_xml.append("<" + XML_NODE_ASP_RTO_NM + ">" + cartDO.getAsp_rto_nm() + "</"  + XML_NODE_ASP_RTO_NM + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_COUNT + ">" + cartDO.getUse_limit_count() + "</"  + XML_NODE_USE_LIMIT_COUNT + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_FLAG + ">" + cartDO.getUse_limit_flag() + "</"  + XML_NODE_USE_LIMIT_FLAG + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + cartDO.getEpis_no() + "</"  + XML_NODE_EPIS_NO + ">");
		_xml.append("<" + XML_NODE_DOWN_VOL + ">" + cartDO.getDown_vol() + "</"  + XML_NODE_DOWN_VOL + ">");
		_xml.append("<" + XML_NODE_DOWN_TYP_NM + ">" + cartDO.getDown_typ_nm() + "</"  + XML_NODE_DOWN_TYP_NM + ">");
		_xml.append("<" + XML_NODE_REQ_CONT + ">" + CommonUtl.transXmlText(cartDO.getReq_cont()) + "</"  + XML_NODE_REQ_CONT + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");
		
		return _xml.toString();
	}

	
}

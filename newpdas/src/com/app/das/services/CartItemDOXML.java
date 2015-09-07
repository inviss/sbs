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
 *  다운로드 ITEM별 정보 관련 XML파서
 * @author asura207
 *
 */
public class CartItemDOXML extends DOXml {
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "cartItems";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CARTNO = "CART_NO";             //  
	/**
	 * 카트제목
	 */
	private String XML_NODE_DOWN_SUBJ = "DOWN_SUBJ";        //   
	/**
	 * 화질명
	 */
	private String XML_NODE_VD_QLTY_NM = "VD_QLTY_NM";      // 
	/**
	 * 종횡비명
	 */
	private String XML_NODE_ASP_RTO_NM = "ASP_RTO_NM";      // 
	/**
	 * 사용제한건수
	 */
	private String XML_NODE_USE_LIMIT_COUNT = "USE_LIMIT_COUNT";  // 
	/**
	 *  사용제한
	 */
	private String XML_NODE_USE_LIMIT_FLAG = "USE_LIMIT_FLAG";    //
	/**
	 *  요청자명
	 */
	private String XML_NODE_REQ_NM = "REQ_NM";                    //
	/**
	 *  요청사유
	 */
	private String XML_NODE_APP_CONT = "APP_CONT";                //
	/**
	 * 등록일
	 */
	private String XML_NODE_REQ_DT = "REQ_DT";                    // 
	/**
	 * 다운로드 구분명 
	 */
	private String XML_NODE_DOWN_GUBUN_NM = "DOWN_GUBUN_NM";      // 
	/**
	 * 카트내 순번
	 */
	private String XML_NODE_CART_SEQ = "CART_SEQ";            // 
	/**
	 * 시작점
	 */
	private String XML_NODE_SOM = "SOM";      // 
	/**
	 * 종료점
	 */
	private String XML_NODE_EOM = "EOM";      // 
	/**
	 * 컨텐츠ID
	 */
	private String XML_NODE_CT_ID = "CT_ID";            // 
	/**
	 * 컨텐츠 INSTANCE ID
	 */
	private String XML_NODE_CTI_ID = "CTI_ID";            // 
	/**
	 * 제한명
	 */
	private String XML_NODE_RIST_CLF_NM = "RIST_CLF_NM";      // 
	/**
	 * 제한코드
	 */
	private String XML_NODE_RIST_CLF_CD = "RIST_CLF_CD";      // 

	/**
	 * 회사번호
	 */
	private String XML_NODE_COCD = "COCD";
	/**
	 * 다운로드 상태
	 */
	private String XML_NODE_DOWN_STAT = "DOWN_STAT";
	/**
	 * 회차
	 */
	private String XML_NODE_EPIS_NO = "EPIS_NO";
	// 검색 조건 값들 시작
	/**
	 * 다운로드 구분 (1:전체,2:TAPE-OUT,3:TAPE-OUT 외)      
	 */
	private String XML_NODE_DOWN_GUBUN = "DOWN_GUBUN";			// 
	/**
	 * 다운로드 상태(1:대기, 2:승인, 3:다운로드 완료)
	 */
	private String XML_NODE_DOWN_STATUS = "DOWN_STATUS"; 			// 
	/**
	 *  일자 ( 1: 다운로드 요청 일자, 2: 다운로드 완료일자)
	 */
	private String XML_NODE_DOWN_DAY = "DOWN_DAY";				//
	/**
	 * 		시작일자
	 */
	private String XML_NODE_FROMDATE = "FROMDATE";			// 
	/**
	 * 완료일자
	 */
	private String XML_NODE_ENDDATE = "ENDDATE";					// 
	/**
	 * 프로그램명
	 */
	private String XML_NODE_TITLE ="TITLE";						// 
	/**
	 * 비직원 신청여부 (Y:예 ,N:아니오)
	 */
	private String XML_NODE_OUT_USER = "OUT_USER";				// 
	/**
	 * 계열사 구분
	 */
	private String XML_NODE_COMPANY_GUBUN = "COMPANY_GUBUN";      // 
	/**
	 * 조회 사용자 아이디
	 */
	private String XML_NODE_USERID = "USERID";					// 
	/**
	 * 상태
	 */
	private String XML_NODE_STATUS = "STATUS";	
	/**
	 * 검색조건
	 */
	private String XML_NODE_SEARCH_FLAGE = "SEARCH_FLAG";// 
	/**
	 * 요청자ID
	 */
	private String XML_NODE_REQ_ID = "REQ_ID";	//
	/**
	 * 미디어 ID
	 */
	private String XML_NODE_MEDIA_ID= "MEDIA_ID";// 


	/**
	 * 요청사유
	 */
	private String XML_NODE_REQ_CONT= "REQ_CONT";// 
	/**
	 * 요청사유
	 */
	private String XML_NODE_USER_TYPE= "USER_TYPE";// 
	/**
	 * 컨텐츠 유형
	 */
	private String XML_NODE_CT_CLA= "CT_CLA";// 
	/**
	 * 방송일
	 */
	private String XML_NODE_BRD_DD= "BRD_DD";// 
	/**
	 * 촬영일
	 */
	private String XML_NODE_FM_DT= "FM_DT";// 
	/**
	 * 대분류
	 */
	private String XML_NODE_CTGR_L_CD= "CTGR_L_CD";// 
	/**
	 * 스토리지 경로
	 */
	private String XML_NODE_STORAGE= "STORAGE";// 



	//private String XML_NODE_STATUS= "STATUS";// 진행상태

	//검색 조건 값들 끝

	//2012.5.18

	/**
	 * 채널코드
	 */
	private String XML_NODE_CHENNEL= "CHENNEL";// 

	/**
	 * 회사명
	 */
	private String XML_NODE_CONM= "CONM";// 
	/**
	 * 스토리지 명
	 */
	private String XML_NODE_STORAGENAME= "STORAGE_NAME";// 


	/**
	 * 요청사유
	 */
	private String XML_NODE_CT_CONT= "CT_CONT";// 

	/**
	 * 시스템구분
	 */
	private String XML_NODE_SYSTEM= "SYSTEM";// 

	/**
	 * 마스터id
	 */
	private String XML_NODE_MASTER_ID= "MASTER_ID";// 


	public Object setDO(String _xml) {	
		setDO(new CartItemDO());

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
		CartItemDO cartDO = (CartItemDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equalsIgnoreCase(XML_NODE_CARTNO)) {
				cartDO.setCartNo(Long.parseLong(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_SUBJ)) {
				cartDO.setDownSubj(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_VD_QLTY_NM)) {
				cartDO.setVd_qlty_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ASP_RTO_NM)) {
				cartDO.setAsp_rto_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USE_LIMIT_FLAG)) {
				cartDO.setUseLimitFlag(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USE_LIMIT_COUNT)) {
				cartDO.setUseLimitCount(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_NM)) {
				cartDO.setReqNm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_DT)) {
				cartDO.setReqDT(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_APP_CONT)) {
				cartDO.setAppCont(_nodeValue);
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_GUBUN)) {
				cartDO.setDown_gubun(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_STATUS)) {
				cartDO.setDown_status(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_DAY)) {
				cartDO.setDown_day(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_FROMDATE)) {
				cartDO.setFromdate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_ENDDATE)) {
				cartDO.setEnddate(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_TITLE)) {
				cartDO.setTitle(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_OUT_USER)) {
				cartDO.setOut_user(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COMPANY_GUBUN)) {
				cartDO.setCompany_gubun(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_GUBUN_NM)) {
				cartDO.setDown_gubun_nm(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_USERID)) {
				cartDO.setUserid(_nodeValue);
			}	else if(_nodeName.equalsIgnoreCase(XML_NODE_SOM)) {
				cartDO.setSom(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EOM)) {
				cartDO.setEom(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_CT_ID)) {
				if(_nodeValue.equals("")){
					cartDO.setCt_id(0);
				}else{
					cartDO.setCt_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));	
				}
			}

			else if(_nodeName.equalsIgnoreCase(XML_NODE_CTI_ID)) {
				if(_nodeValue.equals("")){
					cartDO.setCti_id(0);
				}else{
					cartDO.setCti_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));	
				}
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_RIST_CLF_NM)) {
				cartDO.setRist_clf_nm(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CART_SEQ)) {
				cartDO.setCartseq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_COCD)) {
				cartDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_SEARCH_FLAGE)) {
				cartDO.setSearch_flag(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_ID)) {
				cartDO.setReq_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_MEDIA_ID)) {
				cartDO.setMedia_id(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_REQ_CONT)) {
				cartDO.setReq_cont(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_DOWN_STAT)) {
				cartDO.setDown_stat(_nodeValue);
			}
			else if(_nodeName.equalsIgnoreCase(XML_NODE_EPIS_NO)) {
				cartDO.setEpis_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_USER_TYPE)) {
				cartDO.setUesr_type(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_CHENNEL)) {
				cartDO.setChennel(_nodeValue);
			}else if(_nodeName.equalsIgnoreCase(XML_NODE_SYSTEM)) {
				cartDO.setSystem(_nodeValue);
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
		CartItemDO cartDO = (CartItemDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_CARTNO.toLowerCase() + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CART_SEQ.toLowerCase() + ">" + cartDO.getCartseq() + "</"  + XML_NODE_CART_SEQ.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_SUBJ.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getDownSubj()) + "</"  + XML_NODE_DOWN_SUBJ.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_VD_QLTY_NM.toLowerCase() + ">" + cartDO.getVd_qlty_nm() + "</"  + XML_NODE_VD_QLTY_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_ASP_RTO_NM.toLowerCase() + ">" + cartDO.getAsp_rto_nm() + "</"  + XML_NODE_ASP_RTO_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_COUNT.toLowerCase() + ">" + cartDO.getUseLimitCount() + "</"  + XML_NODE_USE_LIMIT_COUNT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_USE_LIMIT_FLAG.toLowerCase() + ">" + cartDO.getUseLimitFlag() + "</"  + XML_NODE_USE_LIMIT_FLAG.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_EPIS_NO.toLowerCase() + ">" + cartDO.getEpisno()+ "</"  + XML_NODE_EPIS_NO.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQ_NM.toLowerCase() + ">" + cartDO.getReqNm() + "</"  + XML_NODE_REQ_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_APP_CONT.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getAppCont()) + "</"  + XML_NODE_APP_CONT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQ_DT.toLowerCase() + ">" + cartDO.getReqDT() + "</"  + XML_NODE_REQ_DT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_GUBUN_NM.toLowerCase() + ">" + cartDO.getDown_gubun_nm() + "</"  + XML_NODE_DOWN_GUBUN_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_DOWN_STATUS.toLowerCase() + ">" + cartDO.getDown_status() + "</"  + XML_NODE_DOWN_STATUS.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_TITLE.toLowerCase() + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_SOM.toLowerCase() + ">" + cartDO.getSom() + "</"  + XML_NODE_SOM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_EOM.toLowerCase() + ">" + cartDO.getEom() + "</"  + XML_NODE_EOM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CT_ID.toLowerCase() + ">" + cartDO.getCt_id() + "</"  + XML_NODE_CT_ID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_NM.toLowerCase() + ">" + cartDO.getRist_clf_nm() + "</"  + XML_NODE_RIST_CLF_NM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_CD.toLowerCase() + ">" + cartDO.getRist_clf_cd() + "</"  + XML_NODE_RIST_CLF_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CART_SEQ.toLowerCase() + ">" + cartDO.getCartseq() + "</"  + XML_NODE_CART_SEQ.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_COCD.toLowerCase() + ">" + cartDO.getCocd() + "</"  + XML_NODE_COCD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_REQ_CONT.toLowerCase() + ">" + cartDO.getReq_cont() + "</"  + XML_NODE_REQ_CONT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CT_CLA.toLowerCase() + ">" + cartDO.getCt_cla() + "</"  + XML_NODE_CT_CLA.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_BRD_DD.toLowerCase() + ">" + cartDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_FM_DT.toLowerCase() + ">" + cartDO.getFm_dt() + "</"  + XML_NODE_FM_DT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CTGR_L_CD.toLowerCase() + ">" + cartDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_STATUS.toLowerCase() + ">" + cartDO.getStatus() + "</"  + XML_NODE_STATUS.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_STORAGE.toLowerCase() + ">" + cartDO.getStorage() + "</"  + XML_NODE_STORAGE.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MEDIA_ID.toLowerCase() + ">" + cartDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CONM.toLowerCase() + ">" + cartDO.getConm() + "</"  + XML_NODE_CONM.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_STORAGENAME.toLowerCase() + ">" + cartDO.getStoragename() + "</"  + XML_NODE_STORAGENAME.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_CT_CONT.toLowerCase() + ">" + cartDO.getCt_cont() + "</"  + XML_NODE_CT_CONT.toLowerCase() + ">");
		_xml.append("<" + XML_NODE_MASTER_ID.toLowerCase() + ">" + cartDO.getMaster_id() + "</"  + XML_NODE_MASTER_ID.toLowerCase() + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

	public String getSubXML2() {
		CartItemDO cartDO = (CartItemDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");
		_xml.append("<" + XML_NODE_REQ_ID + ">" + cartDO.getReq_id() + "</"  + XML_NODE_REQ_ID + ">");
		_xml.append("<" + XML_NODE_CARTNO + ">" + cartDO.getCartNo() + "</"  + XML_NODE_CARTNO + ">");
		_xml.append("<" + XML_NODE_CART_SEQ + ">" + cartDO.getCartseq() + "</"  + XML_NODE_CART_SEQ + ">");
		_xml.append("<" + XML_NODE_RIST_CLF_NM + ">" + CommonUtl.transXmlText(cartDO.getRist_clf_nm()) + "</"  + XML_NODE_RIST_CLF_NM + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(cartDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");
		_xml.append("<" + XML_NODE_CT_ID + ">" + cartDO.getCt_id() + "</"  + XML_NODE_CT_ID + ">");
		_xml.append("<" + XML_NODE_MEDIA_ID + ">" + cartDO.getMedia_id() + "</"  + XML_NODE_MEDIA_ID + ">");
		_xml.append("<" + XML_NODE_REQ_NM + ">" + CommonUtl.transXmlText(cartDO.getReqNm()) + "</"  + XML_NODE_REQ_NM + ">");
		_xml.append("<" + XML_NODE_REQ_DT + ">" + cartDO.getReqDT() + "</"  + XML_NODE_REQ_DT + ">");
		_xml.append("<" + XML_NODE_DOWN_STATUS + ">" + cartDO.getDown_status() + "</"  + XML_NODE_DOWN_STATUS + ">");
		_xml.append("<" + XML_NODE_SOM + ">" + cartDO.getSom() + "</"  + XML_NODE_SOM + ">");
		_xml.append("<" + XML_NODE_EOM + ">" + cartDO.getEom() + "</"  + XML_NODE_EOM + ">");
		_xml.append("<" + XML_NODE_DOWN_SUBJ + ">" + CommonUtl.transXmlText(cartDO.getDownSubj()) + "</"  + XML_NODE_DOWN_SUBJ + ">");
		_xml.append("<" + XML_NODE_FM_DT + ">" + cartDO.getFm_dt() + "</"  + XML_NODE_FM_DT + ">");
		_xml.append("<" + XML_NODE_BRD_DD + ">" + cartDO.getBrd_dd() + "</"  + XML_NODE_BRD_DD + ">");
		_xml.append("<" + XML_NODE_EPIS_NO + ">" + cartDO.getEpisno() + "</"  + XML_NODE_EPIS_NO + ">");
		_xml.append("<" + XML_NODE_CTGR_L_CD + ">" + cartDO.getCtgr_l_cd() + "</"  + XML_NODE_CTGR_L_CD + ">");
		_xml.append("<" + XML_NODE_CONM + ">" + CommonUtl.transXmlText(cartDO.getConm()) + "</"  + XML_NODE_CONM + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

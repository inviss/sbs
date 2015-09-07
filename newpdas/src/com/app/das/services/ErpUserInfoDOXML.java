package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.EmployeeDASRoleDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.ErpAppointDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.SubsidiaryinfoDO;
import com.app.das.util.CommonUtl;


/**
 *  ERP 사용자 정보 관련 XML파서
 * @author asura207
 *
 */
public class ErpUserInfoDOXML extends DOXml {
	ErpAppointDO erpAppointDO;
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "com.sbs.co.kr.OrderInfoDO";
	/** 
	 * 회사코드 
	 */  
	private String XML_NODE_CO_CD = "co_cd";	
	/** 
	 * 사원번호 
	 */ 
	private String XML_NODE_USER_NO="user_no";
	/** 
	 * 발령일자 
	 */  
	private String XML_NODE_ODER_DD="order_dd";
	/** 
	 * 발령순번 
	 */ 
	private String XML_NODE_ODER_SEQ="order_seq";
	/** 
	 * 발령코드 
	 */   
	private String XML_NODE_ODER_CD = "order_cd";
	/** 
	 * 사용자명 
	 */
	private String XML_NODE_USER_NM = "user_nm";
	/** 
	 * 사용자구분 
	 */  
	private String XML_NODE_USER_CLF="user_clf";
	/** 
	 * 부서코드 
	 */
	private String XML_NODE_DEPT_CD="dept_cd";
	/** 
	 * 핸드폰 
	 */
	private String XML_NODE_HAND_PHON="hand_phon";	
	/** 
	 * 직책코드 
	 */
	private String XML_NODE_JOB_CD="job";
	/** 
	 * 팀장여부 
	 */  
	private String XML_NODE_TEAM_YN="team_yn";	
	/** 
	 * 적용여부 
	 */ 
	private String XML_NODE_ADAPT_YN="apdat_yn";				
	/** 
	 * 조회여부 
	 */  
	private String XML_NODE_SEARCH_YN="seach_yn";		
	/** 
	 * 본부코드
	 */    
	private String XML_NODE_SEG_CD="seg_cd";	
	/** 
	 * 본부명
	 */    
	private String XML_NODE_SEG_NM="seg_nm";

	/** 
	 * 부서명 
	 */   
	private String XML_NODE_DEPT_NM="dept_nm";	
	/** 
	 * 제목 
	 */     
	private String XML_NODE_TITLE="title";				
	/** 
	 * 우선순위
	 */     
	private String XML_NODE_OCPN_GR_CD="grade_cd";	
	/** 
	 * 발령순번
	 */     
	private String XML_NODE_SEQ_NO="seq_no";	
	/** 
	 * 발령명
	 */     
	private String XML_NODE_ORDER_NM="order_nm";	
	/** 
	 * 발령코드 D = 삭제 , U = 갱신 
	 */      
	private String XML_NODE_ORDER_FLAG="order_flag";	

	/*public Object setDO(String _xml) {
		setDO(new ErpAppointDO());

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
	}*/


	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			erpAppointDO = new ErpAppointDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;

			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
				result.add(erpAppointDO);
			}
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

			if(_nodeName.equals(XML_NODE_CO_CD)) {
				erpAppointDO.setCo_cd(_nodeValue.trim());
			}else if(_nodeName.equals(XML_NODE_USER_NO)) {
				erpAppointDO.setUser_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ODER_DD)) {
				erpAppointDO.setOder_dd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ODER_SEQ)) {
				erpAppointDO.setOder_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_ODER_CD)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					erpAppointDO.setOder_cd(_nodeValue.trim());
				}
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					erpAppointDO.setUser_nm(_nodeValue.trim());
				}
			}
			else if(_nodeName.equals(XML_NODE_USER_CLF)) {

				erpAppointDO.setUser_clf(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				erpAppointDO.setDept_cd(_nodeValue.trim());
			}
			else if(_nodeName.equals(XML_NODE_HAND_PHON)) {
				erpAppointDO.setHand_phon(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_JOB_CD)) {
				erpAppointDO.setJob_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TEAM_YN)) {
				erpAppointDO.setTeam_yn(_nodeValue.trim());
			}else if(_nodeName.equals(XML_NODE_ADAPT_YN)) {
				erpAppointDO.setApdat_yn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SEARCH_YN)) {
				erpAppointDO.setSearch_yn(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_SEG_CD)) {
				erpAppointDO.setSeg_cd(_nodeValue.trim());
			}
			else if(_nodeName.equals(XML_NODE_SEG_NM)) {
				erpAppointDO.setSeg_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				erpAppointDO.setDept_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_TITLE)) {
				erpAppointDO.setTitle(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_OCPN_GR_CD)) {
				erpAppointDO.setOcpn_gr_cd(_nodeValue);	
			}else if(_nodeName.equals(XML_NODE_SEQ_NO)) {
				erpAppointDO.setSeq_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));

			}else if(_nodeName.equals(XML_NODE_ORDER_NM)) {
				erpAppointDO.setOder_nm(_nodeValue);	
			}else if(_nodeName.equals(XML_NODE_ORDER_FLAG)) {
				erpAppointDO.setOder_flag(_nodeValue);	
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
		ErpAppointDO erpAppointDO = (ErpAppointDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");	
		_xml.append("<" + XML_NODE_CO_CD + ">" + erpAppointDO.getCo_cd() + "</"  + XML_NODE_CO_CD + ">");
		_xml.append("<" + XML_NODE_USER_NO + ">" + erpAppointDO.getUser_no() + "</"  + XML_NODE_USER_NO + ">");
		_xml.append("<" + XML_NODE_ODER_DD + ">" + erpAppointDO.getOder_dd() + "</"  + XML_NODE_ODER_DD + ">");
		_xml.append("<" + XML_NODE_ODER_SEQ + ">" +erpAppointDO.getOder_seq()+ "</"  + XML_NODE_ODER_SEQ + ">");
		_xml.append("<" + XML_NODE_ODER_CD + ">" + erpAppointDO.getOder_cd() + "</"  + XML_NODE_ODER_CD + ">");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(erpAppointDO.getUser_nm()) + "</"  + XML_NODE_USER_NM + ">");
		_xml.append("<" + XML_NODE_USER_CLF + ">" + erpAppointDO.getUser_clf() + "</"  + XML_NODE_USER_CLF + ">");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" + erpAppointDO.getDept_cd() + "</"  + XML_NODE_DEPT_CD + ">");
		_xml.append("<" + XML_NODE_HAND_PHON + ">" +erpAppointDO.getHand_phon() + "</"  + XML_NODE_HAND_PHON + ">");
		_xml.append("<" + XML_NODE_JOB_CD + ">" + erpAppointDO.getJob_cd() + "</"  + XML_NODE_JOB_CD + ">");
		_xml.append("<" + XML_NODE_TEAM_YN + ">" + erpAppointDO.getTeam_yn() + "</"  + XML_NODE_TEAM_YN + ">");
		_xml.append("<" + XML_NODE_ADAPT_YN + ">" + erpAppointDO.getApdat_yn() + "</"  + XML_NODE_ADAPT_YN + ">");	
		_xml.append("<" + XML_NODE_SEARCH_YN + ">" + erpAppointDO.getSearch_yn() + "</"  + XML_NODE_SEARCH_YN + ">");
		_xml.append("<" + XML_NODE_SEG_CD + ">" +erpAppointDO.getSeg_cd() + "</"  + XML_NODE_SEG_CD + ">");
		_xml.append("<" + XML_NODE_SEG_NM + ">" + CommonUtl.transXmlText(erpAppointDO.getSeg_nm()) + "</"  + XML_NODE_SEG_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + CommonUtl.transXmlText(erpAppointDO.getDept_nm()) + "</"  + XML_NODE_DEPT_NM + ">");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(erpAppointDO.getTitle()) + "</"  + XML_NODE_TITLE + ">");	
		_xml.append("<" + XML_NODE_OCPN_GR_CD + ">" + erpAppointDO.getOcpn_gr_cd() + "</"  + XML_NODE_OCPN_GR_CD + ">");
		_xml.append("<" + XML_NODE_SEQ_NO + ">" + erpAppointDO.getSeq_no() + "</"  + XML_NODE_SEQ_NO + ">");
		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}
}

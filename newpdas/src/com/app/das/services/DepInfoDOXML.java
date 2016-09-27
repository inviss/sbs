package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DepInfoDO;
import com.app.das.util.CommonUtl;

/**
 * 부서 정보 관련 XML파서
 * @author asura207
 *
 */
public class DepInfoDOXML extends DOXml{
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "depinfo";	
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD = "cocd";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "dept_cd";
	/**
	 * 소속단위
	 */
	private String XML_NODE_POST_UNIT_CLF = "post_unit_clf"; 
	/**
	 * 부서명
	 */
	private String XML_NODE_DEPT_NM = "dept_nm"; 
	/**
	 * 부서레벨
	 */
	private String XML_NODE_LVL = "lvl"; 
	/**
	 * 부서순번
	 */
	private String XML_NODE_SEQ = "seq"; 
	/**
	 * 본부코드
	 */
	private String XML_NODE_SUP_HEAD_CD = "sup_head_cd";
	/**
	 * 본부명
	 */
	private String XML_NODE_SUP_HEAD_NM = "sup_head_nm";
	/**
	 * 본부순서
	 */
	private String XML_NODE_SUP_HEAD_SEQ = "sup_head_seq";
	/**
	 * 국코드
	 */
	private String XML_NODE_SUP_HTPO_CD = "sup_htpo_cd";
	/**
	 * 국명
	 */
	private String XML_NODE_SUP_HTPO_NM = "sup_htpo_nm";
	/**
	 * 국순서
	 */
	private String XML_NODE_SUP_HTPO_SEQ = "sup_htpo_seq";
	/**
	 * 부서장사번
	 */
	private String XML_NODE_DEPT_CHAP_EMP_NO = "dept_chap_emp_no";
	/**
	 * 상위부서코드
	 */
	private String XML_NODE_SUP_DEPT_CD = "sup_dept_cd";
	/**
	 * 회사명
	 */
	private String XML_NODE_CONM = "conm";
	/**
	 * 회사코드
	 */
	private String XML_NODE_WCOCD = "wcocd";
	/**
	 * 상태
	 */
	private String XML_NODE_STATUS = "status";
	/**
	 * 상위부서코드
	 */
	private String XML_NODE_PARENTS_DEPT_CD = "parent_dept_cd";
	/**
	 * 상위부서명
	 */
	private String XML_NODE_PARENTS_DEPT_NM = "parent_dept_nm";
	/**
	 * 사용여부
	 */
	private String XML_NODE_USE_YN = "use_yn";

	public Object setDO(String _xml) {
		setDO(new DepInfoDO());

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
		DepInfoDO infoDO = (DepInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_COCD)) {
				infoDO.setCocd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				infoDO.setDept_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_POST_UNIT_CLF)) {
				infoDO.setPost_unit_clf(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				infoDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_LVL)) {
				infoDO.setLvl(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SEQ)) {
				infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SUP_HEAD_CD)) {
				infoDO.setSup_head_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUP_HEAD_NM)) {
				infoDO.setSup_head_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_HEAD_SEQ)) {
				infoDO.setSup_head_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_SUP_HTPO_CD)) {
				infoDO.setSup_htpo_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_HTPO_NM)) {
				infoDO.setSup_htpo_nm(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUP_HTPO_SEQ)) {
				infoDO.setSup_htpo_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CHAP_EMP_NO)) {
				infoDO.setDept_chap_emp_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_DEPT_CD)) {
				infoDO.setSup_dept_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_WCOCD)) {
				infoDO.setWcocd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_CONM)) {
				infoDO.setConm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USE_YN)) {
				infoDO.setUse_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_STATUS)) {
				infoDO.setStatus(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PARENTS_DEPT_CD)) {
				infoDO.setParent_dept_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PARENTS_DEPT_NM)) {
				infoDO.setParent_dept_nm(_nodeValue);
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
		DepInfoDO infoDO = (DepInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_COCD+ ">" + infoDO.getCocd() + "</"  + XML_NODE_COCD + "> \n");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" + infoDO.getDept_cd() + "</"  + XML_NODE_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_POST_UNIT_CLF + ">" + infoDO.getPost_unit_clf() + "</"  + XML_NODE_POST_UNIT_CLF + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + infoDO.getDept_nm() + "</"  + XML_NODE_DEPT_NM + "> \n");		
		_xml.append("<" + XML_NODE_LVL + ">" + infoDO.getLvl() + "</"  + XML_NODE_LVL + "> \n");		
		_xml.append("<" + XML_NODE_SEQ + ">" + infoDO.getSeq() + "</"  + XML_NODE_SEQ + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_CD + ">" + infoDO.getSup_head_cd() + "</"  + XML_NODE_SUP_HEAD_CD + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_NM + ">" + CommonUtl.transXmlText(infoDO.getSup_head_nm()) + "</"  + XML_NODE_SUP_HEAD_NM + "> \n");
		_xml.append("<" + XML_NODE_SUP_HEAD_SEQ + ">" + infoDO.getSup_head_seq() + "</"  + XML_NODE_SUP_HEAD_SEQ + "> \n");		
		_xml.append("<" + XML_NODE_SUP_HTPO_CD + ">" + infoDO.getSup_htpo_cd() + "</"  + XML_NODE_SUP_HTPO_CD + "> \n");	
		_xml.append("<" + XML_NODE_SUP_HTPO_NM + ">" + CommonUtl.transXmlText(infoDO.getSup_htpo_nm()) + "</"  + XML_NODE_SUP_HTPO_NM + "> \n");
		_xml.append("<" + XML_NODE_SUP_HTPO_SEQ + ">" + infoDO.getSup_htpo_seq() + "</"  + XML_NODE_SUP_HTPO_SEQ + "> \n");
		_xml.append("<" + XML_NODE_DEPT_CHAP_EMP_NO + ">" + infoDO.getDept_chap_emp_no() + "</"  + XML_NODE_DEPT_CHAP_EMP_NO + "> \n");
		_xml.append("<" + XML_NODE_SUP_DEPT_CD + ">" + infoDO.getSup_dept_cd() + "</"  + XML_NODE_SUP_DEPT_CD + "> \n");
		_xml.append("<" + XML_NODE_CONM + ">" + infoDO.getConm() + "</"  + XML_NODE_CONM + "> \n");
		_xml.append("<" + XML_NODE_USE_YN + ">" + infoDO.getUse_yn() + "</"  + XML_NODE_USE_YN + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}




}

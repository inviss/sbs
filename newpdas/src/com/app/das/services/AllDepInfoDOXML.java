package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DepInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 부서관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllDepInfoDOXML extends DOXml{
	
	private Logger logger = Logger.getLogger(AllDepInfoDOXML.class);
	
	private DepInfoDO infoDO;
	
	/**
	 * xml헤더
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
	 * 국순서
	 */
	private String XML_NODE_SUP_HTPO_CD = "sup_htpo_cd";
	/**
	 * 국명
	 */
	private String XML_NODE_SUP_HTPO_NM = "sup_htpo_nm";
	/**
	 * 국코드
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
	 * 구분
	 */
	private String XML_NODE_STATUS = "status";

	//20120812 신규 부서동기화xml 

	/**
	 * 헤더
	 */
	private String gua_sync_departmentinfo = "gua_sync_departmentinfo";

	/**
	 * 작업종류  C:생성, U:변경, D:삭제, S:동기화
	 */
	private String operation_type = "operation_type";
	/**
	 * 부서태그
	 */
	private String department = "department";
	/**
	 * 회사코드
	 */
	private String company_code = "company_code";
	/**
	 * 부서코드
	 */
	private String department_code = "department_code";
	/**
	 * 부서명
	 */
	private String department_name = "department_name";
	/**
	 * 상위부서코드
	 */
	private String super_department_code = "super_department_code";
	/**
	 * 상위부서명
	 */
	private String super_department_name = "super_department_name";


	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData2((Element)_node);
			}else if(_nodeName.equals(gua_sync_departmentinfo)) {
				setData2((Element)_node);
			}
		}
		return getDO();
	}





	public Object setData(Element pElement) {
		List result = (List)getDO();
		NodeList _nodeList = pElement.getChildNodes();

		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			
			infoDO = new DepInfoDO();
			
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals( XML_NODE_COCD)) {
				infoDO.setCocd(_nodeValue);
			}else  if(_nodeName.equals(XML_NODE_DEPT_CD)) {
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
				if(_nodeValue.equals("")){
					infoDO.setSeq(0);}
				else{				
					infoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_SUP_HEAD_CD)) {
				infoDO.setSup_head_cd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUP_HEAD_NM)) {
				infoDO.setSup_head_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_HEAD_SEQ)) {
				if(_nodeValue.equals("")){
					infoDO.setSeq(0);}
				else{	
					infoDO.setSup_head_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_SUP_HTPO_CD)) {
				infoDO.setSup_htpo_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_HTPO_NM)) {
				infoDO.setSup_htpo_nm(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_SUP_HTPO_SEQ)) {
				if(_nodeValue.equals("")){
					infoDO.setSeq(0);}
				else{	
					infoDO.setSup_htpo_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			}
			else if(_nodeName.equals(XML_NODE_DEPT_CHAP_EMP_NO)) {
				infoDO.setDept_chap_emp_no(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_SUP_DEPT_CD)) {
				infoDO.setSup_dept_cd(_nodeValue);
			}	else if(_nodeName.equals(XML_NODE_STATUS)) {
				infoDO.setStatus(_nodeValue);
			}

		}

		return result;
	}	    





	public Object setData2(Element pElement) {
		List result = (List)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		
		String status = "";
		for(int i = 0; i < _length; i++) {
			
			infoDO = new DepInfoDO();
			
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = getNodeValue(_node);

			if(_nodeName.equals(operation_type)) {
				status = _nodeValue;
				//logger.debug("###############+"+_nodeValue);
			}else if(_nodeName.equals(department)) {

				NodeList _nodeList2 = _node.getChildNodes();
				int _length2 = _nodeList2.getLength();
				for(int k = 0; k < _length2; k++) {
					Node _node2 = _nodeList2.item(k);
					String _nodeName2 = _node2.getNodeName() ;
					String _nodeValue2 = getNodeValue(_node2);
					
					if(_nodeName2.equals(company_code)){
						infoDO.setCocd(_nodeValue2);
					
					}else if(_nodeName2.equals(department_code)) {
						infoDO.setDept_cd(_nodeValue2);
						
					}
					else if(_nodeName2.equals(department_name)) {
						infoDO.setDept_nm(_nodeValue2);
					
					}
					else if(_nodeName2.equals(super_department_code)) {
						infoDO.setSup_dept_cd(_nodeValue2);
					
					}
					else if(_nodeName2.equals(super_department_name)) {
						infoDO.setSup_dept_nm(_nodeValue2);
					
					}
					
					infoDO.setStatus(status);
				}
				result.add(infoDO);

				if(StringUtils.isBlank(infoDO.getDept_cd())){
					break;
				}
			}
			
			
		}
		return result;
	}	    


	public String toXML() {
		
		StringBuffer _xml = new StringBuffer();

		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		_xml.append("</das>");

		return _xml.toString();
	}

}

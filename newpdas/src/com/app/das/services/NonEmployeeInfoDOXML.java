package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;


import com.app.das.business.transfer.EmployeeDASRoleDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.business.transfer.SubsidiaryinfoDO;
import com.app.das.util.CommonUtl;


/**
 *  비직원 정보 관련 XML파서
 * @author asura207
 *
 */
public class NonEmployeeInfoDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "employeeinfo";
	/**
	 * 주민등록번호
	 */
	private String XML_NODE_PER_REG_NO = "per_reg_no";	
	/**
	 * 사용시작일
	 */
	private String XML_NODE_VLDDT_BGN="vlddt_bgn";
	/**
	 * 사용종료일
	 */
	private String XML_NODE_VLDDT_END="vlddt_end";
	/**
	 *   사용자 이름
	 */
	private String XML_NODE_USER_NM="user_nm";
	/**
	 * 프로그램 명
	 */
	private String XML_NODE_PGM_NM = "pgm_nm";
	/**
	 *   sbs 사용자 id
	 */
	private String XML_NODE_SBS_USER_ID = "sbs_user_id";
	/**
	 * 소속부서 명
	 */
	private String XML_NODE_DEPT_NM="dept_nm";
	/**
	 * 암호
	 */
	private String XML_NODE_PASSWORD="password";
	/**
	 * 역할
	 */
	private String XML_NODE_ROLE_CD="role_cd";	
	/**
	 * 직책
	 */
	private String XML_NODE_POSITION="position";
	/**
	 * 직원 휴대폰
	 */
	private String XML_NODE_MOBILE="mobile";	
	/**
	 * 승인여부
	 */
	private String XML_NODE_APPROVE_YN="approve_yn";				
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PGM_ID="pgm_id";		
	/**
	 * 정직원 유무
	 */
	private String XML_NODE_EMPLOYEE_YN="employee_yn";
	/**
	 * 직원유형 001 정직원 002 계열사 003 비직원
	 */
	private String XML_NODE_EMPLOYEE_TYPE="employee_type";
	/**
	 * 부서장 사번
	 */
	private String XML_NODE_DEPT_CHAP_EMP_NO="dept_chap_emp_no";

	/**
	 *   sbs 사용자 id
	 */
	private String XML_NODE_SBSI_USER_ID="sbsi_user_id";
	/**
	 * 승인상태
	 */
	private String XML_NODE_APPROVE_STATUS="approve_status";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD="cocd";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD="dept_cd";
	/**
	 * 사번
	 */
	private String XML_NODE_USER_NUM="user_num";
	/**
	 * 계정유형
	 */
	private String XML_NODE_ACCT_CODE="acct_code";
	/**
	 * 삭제여부
	 */
	private String XML_NODE_DELETE_YN="delete_yn";
	/**
	 * 비직원 이름
	 */
	private String XML_NODE_OUT_USER_NM="out_user_nm";
	/**
	 * 비직원 ID
	 */
	private String XML_NODE_OUT_USER_ID="out_user_id";
	/**
	 * 역할
	 */
	private String XML_NODE_ROLE_NM="role_nm";
	//private String XML_NODE_SUP_DEPT_CD="sup_dept_cd";
	public Object setDO(String _xml) {
		setDO(new EmployeeInfoDO());

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
		EmployeeInfoDO employeeroleconditionDO = (EmployeeInfoDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PER_REG_NO)) {
				employeeroleconditionDO.setPer_reg_no(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				employeeroleconditionDO.setSbs_user_ID(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				employeeroleconditionDO.setUser_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_ROLE_NM)) {
				employeeroleconditionDO.setRole_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				employeeroleconditionDO.setDept_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_VLDDT_BGN)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					employeeroleconditionDO.setVlddt_bgn(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_VLDDT_END)) {
				if(_nodeValue != null && _nodeValue.length() > 0) {
					employeeroleconditionDO.setVlddt_end(_nodeValue);
				}
			}
			else if(_nodeName.equals(XML_NODE_POSITION)) {

				employeeroleconditionDO.setPosition(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_APPROVE_YN)) {
				employeeroleconditionDO.setApprove_yn(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PASSWORD)) {
				employeeroleconditionDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ROLE_CD)) {
				employeeroleconditionDO.setRole_cd(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MOBILE)) {
				employeeroleconditionDO.setMobile(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PGM_NM)) {
				employeeroleconditionDO.setPgm_nm(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_EMPLOYEE_YN)) {
				employeeroleconditionDO.setEmployee_yn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_EMPLOYEE_TYPE)) {
				employeeroleconditionDO.setEmployee_type(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_DEPT_CHAP_EMP_NO)) {

				employeeroleconditionDO.setEmployee_yn(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_SBSI_USER_ID)) {

				employeeroleconditionDO.setEmployee_type(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_OUT_USER_ID)) {

				employeeroleconditionDO.setOut_user_id(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_APPROVE_STATUS)) {

				employeeroleconditionDO.setApprove_status(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_COCD)) {

				employeeroleconditionDO.setCocd(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_DEPT_CD)) {

				employeeroleconditionDO.setDept_cd(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_USER_NUM)) {

				employeeroleconditionDO.setUser_num(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_ACCT_CODE)) {

				employeeroleconditionDO.setAcct_code(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_DELETE_YN)) {

				employeeroleconditionDO.setDelete_yn(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_OUT_USER_NM)) {

				employeeroleconditionDO.setOut_user_nm(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_PGM_ID)) {
				employeeroleconditionDO.setPgm_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				//}else if(_nodeName.equals(XML_NODE_SUP_DEPT_CD)) {
				//	employeeroleconditionDO.setSup_dept_cd(_nodeValue);
			}
		}

		return employeeroleconditionDO;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}


	public String getSubXML() {
		EmployeeInfoDO employeeroleconditionDO = (EmployeeInfoDO)getDO();

		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_PER_REG_NO + ">" + employeeroleconditionDO.getPer_reg_no() + "</"  + XML_NODE_PER_REG_NO + "> \n");
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + employeeroleconditionDO.getSbs_user_ID() + "</"  + XML_NODE_SBS_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_OUT_USER_ID + ">" + employeeroleconditionDO.getOut_user_id() + "</"  + XML_NODE_OUT_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getUser_nm()) + "</"  + XML_NODE_USER_NM + "> \n");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getDept_nm())+ "</"  + XML_NODE_DEPT_NM + "> \n");
		_xml.append("<" + XML_NODE_VLDDT_BGN + ">" + employeeroleconditionDO.getVlddt_bgn() + "</"  + XML_NODE_VLDDT_BGN + "> \n");
		_xml.append("<" + XML_NODE_VLDDT_END + ">" + employeeroleconditionDO.getVlddt_end() + "</"  + XML_NODE_VLDDT_END + "> \n");
		_xml.append("<" + XML_NODE_POSITION + ">" + employeeroleconditionDO.getPosition() + "</"  + XML_NODE_POSITION + "> \n");
		_xml.append("<" + XML_NODE_APPROVE_YN + ">" + employeeroleconditionDO.getApprove_yn() + "</"  + XML_NODE_APPROVE_YN + "> \n");
		_xml.append("<" + XML_NODE_PASSWORD + ">" + employeeroleconditionDO.getPassword() + "</"  + XML_NODE_PASSWORD + "> \n");
		_xml.append("<" + XML_NODE_ROLE_CD + ">" + employeeroleconditionDO.getRole_cd() + "</"  + XML_NODE_ROLE_CD + "> \n");
		_xml.append("<" + XML_NODE_MOBILE + ">" + employeeroleconditionDO.getMobile() + "</"  + XML_NODE_MOBILE + "> \n");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM + "> \n");	
		_xml.append("<" + XML_NODE_EMPLOYEE_YN + ">" + employeeroleconditionDO.getEmployee_yn() + "</"  + XML_NODE_EMPLOYEE_YN + "> \n");
		_xml.append("<" + XML_NODE_EMPLOYEE_TYPE + ">" + employeeroleconditionDO.getEmployee_type() + "</"  + XML_NODE_EMPLOYEE_TYPE + "> \n");	
		_xml.append("<" + XML_NODE_APPROVE_STATUS + ">" + employeeroleconditionDO.getApprove_status() + "</"  + XML_NODE_APPROVE_STATUS + "> \n");
		_xml.append("<" + XML_NODE_COCD + ">" + employeeroleconditionDO.getCocd() + "</"  + XML_NODE_COCD + "> \n");	
		_xml.append("<" + XML_NODE_ACCT_CODE + ">" + employeeroleconditionDO.getAcct_code() + "</"  + XML_NODE_ACCT_CODE + "> \n");
		_xml.append("<" + XML_NODE_DELETE_YN + ">" + employeeroleconditionDO.getDelete_yn() + "</"  + XML_NODE_DELETE_YN + "> \n");	
		_xml.append("<" + XML_NODE_OUT_USER_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getOut_user_nm()) + "</"  + XML_NODE_OUT_USER_NM + "> \n");	
		_xml.append("<" + XML_NODE_ROLE_NM + ">" + employeeroleconditionDO.getRole_nm() + "</"  + XML_NODE_ROLE_NM + "> \n");	
		//	_xml = _xml + "<" + XML_NODE_SUP_DEPT_CD + ">" + employeeroleconditionDO.getSup_dept_cd() + "</"  + XML_NODE_SUP_DEPT_CD + ">";	
		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}
}

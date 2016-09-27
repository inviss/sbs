package com.app.das.services;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.util.CommonUtl;


/**
 *   사용자 정보 관련 XML파서
 * @author asura207
 *
 */
public class EmployeeInfoDOXML extends DOXml {
	/**
	 * xml 헤더
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
	 *  보증 유저id
	 */
	private String XML_NODE_REF_USER_ID="ref_user_id";
	/**
	 * 승인상태
	 */
	private String XML_NODE_APPROVE_STATUS="approve_status";
	/**
	 * 회사 코드
	 */
	private String XML_NODE_COCD="cocd";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD="dept_cd";
	/**
	 * 비직원 이름
	 */
	private String XML_NODE_OUT_USER_NM="out_user_nm";
	/**
	 * 비직원 ID
	 */
	private String XML_NODE_OUT_USER_ID="out_user_id";
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
	 * 등록일시
	 */
	private String XML_NODE_REG_DT="reg_dt";
	/**
	 * 등록자
	 */
	private String XML_NODE_REG_ID="reg_id";
	/**
	 * 등록자명
	 */
	private String XML_NODE_REG_NM="reg_nm";
	/**
	 * 수정일시
	 */
	private String XML_NODE_MOD_DT="mod_dt";
	/**
	 * 수정자
	 */
	private String XML_NODE_MOD_ID="mod_id";
	/**
	 * 승인자명
	 */
	private String XML_NODE_MOD_NM="mod_nm";
	/**
	 * 역할
	 */
	private String XML_NODE_ROLE_NM="role_nm";
	/**
	 * 성공여부
	 */
	private String XML_NODE_SUCCESS_YN="success_yn";
	/**
	 * 수정여부
	 */
	private String XML_NODE_UPDATE_YN="update_yn";
	/**
	 * 검색대상
	 */
	private String XML_NODE_SEARCHTYPE="searchtype";
	/**
	 * 계정유형명
	 */
	private String XML_NODE_ACCT_NM="acct_nm";
	/**
	 * nds 신청여부
	 */
	private String XML_NODE_NDS="nds";
	/**
	 * pds 신청여부
	 */
	private String XML_NODE_PDS="pds";
	/**
	 * pds 프로그램 id
	 * 
	 * 		 */
	private String XML_NODE_PDS_PGM_ID="pds_pgm_id";
	/**
	 * 유형 001-신청 002-수정 003-삭제
	 * 
	 * 		 */
	private String XML_NODE_TYPE="type";
	/**
	 * 승인/취소사유
	 * 
	 * 		 */
	private String XML_NODE_CONT="cont";

	/**
	 * new 암호
	 */
	private String XML_NODE_NEWPASSWORD="newpassword";



	/**
	 * 모니터링 권한 코드
	 */
	private String XML_NODE_MONITOR_CD="monitor_cd";


	/**
	 * 모니터링 권한 명
	 */
	private String XML_NODE_MONITOR_NM="monitor_nm";


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

			//_nodeValue = _nodeValue.replaceAll("&", "&amp;");
			//_nodeValue =_nodeValue.replace( "'" ,"&apos;");
			//_nodeValue =_nodeValue.replaceAll("<", "&lt;");
			//_nodeValue = _nodeValue.replaceAll(">", "&gt;");
			
			if(_nodeName.equals(XML_NODE_PER_REG_NO)) {
				employeeroleconditionDO.setPer_reg_no(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				employeeroleconditionDO.setSbs_user_ID(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				employeeroleconditionDO.setUser_nm(_nodeValue);
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

			}else if(_nodeName.equals(XML_NODE_REF_USER_ID)) {

				employeeroleconditionDO.setRef_user_ID(_nodeValue);

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

			}else if(_nodeName.equals(XML_NODE_PGM_ID)) {
				employeeroleconditionDO.setPds_pgm_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REG_DT)) {
				employeeroleconditionDO.setReg_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_REG_ID)) {
				employeeroleconditionDO.setReg_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MOD_DT)) {
				employeeroleconditionDO.setMod_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MOD_ID)) {
				employeeroleconditionDO.setMod_id(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_OUT_USER_ID)) {

				employeeroleconditionDO.setOut_user_id(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_OUT_USER_NM)) {

				employeeroleconditionDO.setOut_user_nm(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_ROLE_NM)) {

				employeeroleconditionDO.setRole_nm(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_SUCCESS_YN)) {

				employeeroleconditionDO.setSuccess_yn(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_REG_NM)) {

				employeeroleconditionDO.setReg_nm(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_MOD_NM)) {

				employeeroleconditionDO.setMod_nm(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_SEARCHTYPE)) {

				employeeroleconditionDO.setSearchtype(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_NDS)) {

				employeeroleconditionDO.setNds(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_PDS)) {

				employeeroleconditionDO.setPds(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_PDS_PGM_ID)) {

				employeeroleconditionDO.setPds_pgm_id(_nodeValue);

			}
			else if(_nodeName.equals(XML_NODE_TYPE)) {

				employeeroleconditionDO.setType(_nodeValue);

			}		else if(_nodeName.equals(XML_NODE_CONT)) {

				employeeroleconditionDO.setCont(_nodeValue);

			}		
			else if(_nodeName.equals(XML_NODE_NEWPASSWORD)) {

				employeeroleconditionDO.setNewPassword(_nodeValue);

			}	
			else if(_nodeName.equals(XML_NODE_MONITOR_CD)) {

				employeeroleconditionDO.setMonitor_cd(_nodeValue);

			}		
		}

		return employeeroleconditionDO;
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
		EmployeeInfoDO employeeroleconditionDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();


		_xml.append("<" + XML_NODE_HEAD + ">");	
		_xml.append("<" + XML_NODE_TYPE + ">" + employeeroleconditionDO.getType() + "</"  + XML_NODE_TYPE + "> \n");		
		_xml.append("<" + XML_NODE_PER_REG_NO + ">" + employeeroleconditionDO.getPer_reg_no() + "</"  + XML_NODE_PER_REG_NO + ">");
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + employeeroleconditionDO.getSbs_user_ID() + "</"  + XML_NODE_SBS_USER_ID + ">");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getUser_nm()) + "</"  + XML_NODE_USER_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" +CommonUtl.transXmlText(employeeroleconditionDO.getDept_nm())+ "</"  + XML_NODE_DEPT_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" +employeeroleconditionDO.getDept_cd()+ "</"  + XML_NODE_DEPT_CD + ">");
		_xml.append("<" + XML_NODE_VLDDT_BGN + ">" + employeeroleconditionDO.getVlddt_bgn() + "</"  + XML_NODE_VLDDT_BGN + ">");
		_xml.append("<" + XML_NODE_VLDDT_END + ">" + employeeroleconditionDO.getVlddt_end() + "</"  + XML_NODE_VLDDT_END + ">");
		_xml.append("<" + XML_NODE_POSITION + ">" + employeeroleconditionDO.getPosition() + "</"  + XML_NODE_POSITION + ">");
		_xml.append("<" + XML_NODE_APPROVE_YN + ">" + employeeroleconditionDO.getApprove_yn() + "</"  + XML_NODE_APPROVE_YN + ">");
		_xml.append("<" + XML_NODE_PASSWORD + ">" + employeeroleconditionDO.getPassword() + "</"  + XML_NODE_PASSWORD + ">");
		_xml.append("<" + XML_NODE_NEWPASSWORD + ">" + employeeroleconditionDO.getNewPassword() + "</"  + XML_NODE_NEWPASSWORD + ">");	
		_xml.append("<" + XML_NODE_ROLE_CD + ">" + employeeroleconditionDO.getRole_cd() + "</"  + XML_NODE_ROLE_CD + ">");
		_xml.append("<" + XML_NODE_MOBILE + ">" + employeeroleconditionDO.getMobile().trim() + "</"  + XML_NODE_MOBILE + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getPgm_nm()) + "</"  + XML_NODE_PGM_NM + ">");	
		_xml.append("<" + XML_NODE_EMPLOYEE_YN + ">" + employeeroleconditionDO.getEmployee_yn() + "</"  + XML_NODE_EMPLOYEE_YN + ">");
		_xml.append("<" + XML_NODE_EMPLOYEE_TYPE + ">" + employeeroleconditionDO.getEmployee_type() + "</"  + XML_NODE_EMPLOYEE_TYPE + ">");	
		_xml.append("<" + XML_NODE_APPROVE_STATUS + ">" + employeeroleconditionDO.getApprove_status() + "</"  + XML_NODE_APPROVE_STATUS + ">");
		_xml.append("<" + XML_NODE_COCD + ">" + employeeroleconditionDO.getCocd() + "</"  + XML_NODE_COCD + ">");	
		_xml.append("<" + XML_NODE_USER_NUM + ">" + employeeroleconditionDO.getUser_num() + "</"  + XML_NODE_USER_NUM + ">");	
		_xml.append("<" + XML_NODE_ACCT_CODE + ">" + employeeroleconditionDO.getAcct_code() + "</"  + XML_NODE_ACCT_CODE + ">");
		_xml.append("<" + XML_NODE_DELETE_YN + ">" + employeeroleconditionDO.getDelete_yn() + "</"  + XML_NODE_DELETE_YN + ">");	
		_xml.append("<" + XML_NODE_PGM_ID + ">" + employeeroleconditionDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + ">");
		_xml.append("<" + XML_NODE_REG_DT + ">" + employeeroleconditionDO.getReg_dt() + "</"  + XML_NODE_REG_DT + ">");	
		_xml.append("<" + XML_NODE_REG_ID + ">" + employeeroleconditionDO.getReg_id() + "</"  + XML_NODE_REG_ID + ">");
		_xml.append("<" + XML_NODE_OUT_USER_NM + ">" + employeeroleconditionDO.getOut_user_nm() + "</"  + XML_NODE_OUT_USER_NM + "> \n");	
		_xml.append("<" + XML_NODE_ROLE_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getRole_nm()) + "</"  + XML_NODE_ROLE_NM + "> \n");	
		_xml.append("<" + XML_NODE_UPDATE_YN + ">" + employeeroleconditionDO.getUpdate_yn() + "</"  + XML_NODE_UPDATE_YN + "> \n");	
		_xml.append("<" + XML_NODE_REG_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getReg_nm()) + "</"  + XML_NODE_REG_NM + ">");
		_xml.append("<" + XML_NODE_MOD_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getMod_nm()) + "</"  + XML_NODE_MOD_NM + "> \n");	
		_xml.append("<" + XML_NODE_PDS_PGM_ID + ">" + employeeroleconditionDO.getPds_pgm_id() + "</"  + XML_NODE_PDS_PGM_ID + "> \n");	
		_xml.append("<" + XML_NODE_MONITOR_CD + ">" + employeeroleconditionDO.getMonitor_cd() + "</"  + XML_NODE_MONITOR_CD + "> \n");	
		_xml.append("<" + XML_NODE_MONITOR_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getMonitor_nm()) + "</"  + XML_NODE_MONITOR_NM + "> \n");	

		_xml.append("<" + XML_NODE_SUCCESS_YN + ">200</"  + XML_NODE_SUCCESS_YN + "> \n");	
		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}





	public String getSubXML2() {
		EmployeeInfoDO employeeroleconditionDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + ">");	
		_xml.append("<" + XML_NODE_PER_REG_NO + ">" + employeeroleconditionDO.getPer_reg_no() + "</"  + XML_NODE_PER_REG_NO + ">");
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + employeeroleconditionDO.getSbs_user_ID() + "</"  + XML_NODE_SBS_USER_ID + ">");
		_xml.append("<" + XML_NODE_USER_NM + ">" + employeeroleconditionDO.getUser_nm() + "</"  + XML_NODE_USER_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_NM + ">" +employeeroleconditionDO.getDept_nm()+ "</"  + XML_NODE_DEPT_NM + ">");
		_xml.append("<" + XML_NODE_DEPT_CD + ">" +employeeroleconditionDO.getDept_cd()+ "</"  + XML_NODE_DEPT_CD + ">");
		_xml.append("<" + XML_NODE_VLDDT_BGN + ">" + employeeroleconditionDO.getVlddt_bgn() + "</"  + XML_NODE_VLDDT_BGN + ">");
		_xml.append("<" + XML_NODE_VLDDT_END + ">" + employeeroleconditionDO.getVlddt_end() + "</"  + XML_NODE_VLDDT_END + ">");
		_xml.append("<" + XML_NODE_POSITION + ">" + employeeroleconditionDO.getPosition() + "</"  + XML_NODE_POSITION + ">");
		_xml.append("<" + XML_NODE_APPROVE_YN + ">" + employeeroleconditionDO.getApprove_yn() + "</"  + XML_NODE_APPROVE_YN + ">");
		_xml.append("<" + XML_NODE_PASSWORD + ">" + employeeroleconditionDO.getPassword() + "</"  + XML_NODE_PASSWORD + ">");
		_xml.append("<" + XML_NODE_ROLE_CD + ">" + employeeroleconditionDO.getRole_cd() + "</"  + XML_NODE_ROLE_CD + ">");
		_xml.append("<" + XML_NODE_MOBILE + ">" + employeeroleconditionDO.getMobile() + "</"  + XML_NODE_MOBILE + ">");
		_xml.append("<" + XML_NODE_PGM_NM + ">" + employeeroleconditionDO.getPgm_nm() + "</"  + XML_NODE_PGM_NM + ">");	
		_xml.append("<" + XML_NODE_EMPLOYEE_YN + ">" + employeeroleconditionDO.getEmployee_yn() + "</"  + XML_NODE_EMPLOYEE_YN + ">");
		_xml.append("<" + XML_NODE_EMPLOYEE_TYPE + ">" + employeeroleconditionDO.getEmployee_type() + "</"  + XML_NODE_EMPLOYEE_TYPE + ">");	
		_xml.append("<" + XML_NODE_APPROVE_STATUS + ">" + employeeroleconditionDO.getApprove_status() + "</"  + XML_NODE_APPROVE_STATUS + ">");
		_xml.append("<" + XML_NODE_COCD + ">" + employeeroleconditionDO.getCocd() + "</"  + XML_NODE_COCD + ">");	
		_xml.append("<" + XML_NODE_USER_NUM + ">" + employeeroleconditionDO.getUser_num() + "</"  + XML_NODE_USER_NUM + ">");	
		_xml.append("<" + XML_NODE_ACCT_CODE + ">" + employeeroleconditionDO.getAcct_code() + "</"  + XML_NODE_ACCT_CODE + ">");
		_xml.append("<" + XML_NODE_DELETE_YN + ">" + employeeroleconditionDO.getDelete_yn() + "</"  + XML_NODE_DELETE_YN + ">");	
		_xml.append("<" + XML_NODE_PGM_ID + ">" + employeeroleconditionDO.getPds_pgm_id() + "</"  + XML_NODE_PGM_ID + ">");
		_xml.append("<" + XML_NODE_REG_DT + ">" + employeeroleconditionDO.getReg_dt() + "</"  + XML_NODE_REG_DT + ">");	
		_xml.append("<" + XML_NODE_REG_ID + ">" + employeeroleconditionDO.getReg_id() + "</"  + XML_NODE_REG_ID + ">");
		_xml.append("<" + XML_NODE_OUT_USER_NM + ">" + employeeroleconditionDO.getOut_user_nm() + "</"  + XML_NODE_OUT_USER_NM + "> \n");	
		_xml.append("<" + XML_NODE_ROLE_NM + ">" + employeeroleconditionDO.getRole_nm() + "</"  + XML_NODE_ROLE_NM + "> \n");	
		_xml.append("<" + XML_NODE_UPDATE_YN + ">" + employeeroleconditionDO.getUpdate_yn() + "</"  + XML_NODE_UPDATE_YN + "> \n");	
		_xml.append("<" + XML_NODE_REG_NM + ">" + employeeroleconditionDO.getReg_nm() + "</"  + XML_NODE_REG_NM + ">");
		_xml.append("<" + XML_NODE_MOD_NM + ">" + employeeroleconditionDO.getMod_nm() + "</"  + XML_NODE_MOD_NM + "> \n");	
		_xml.append("<" + XML_NODE_ACCT_NM + ">" + employeeroleconditionDO.getAcct_nm() + "</"  + XML_NODE_ACCT_NM + "> \n");	

		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}





	public String getSubXML3() {
		EmployeeInfoDO employeeroleconditionDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();



		_xml.append("<" + XML_NODE_HEAD + ">");	
		_xml.append("<" + XML_NODE_USER_NM + ">" + employeeroleconditionDO.getUser_nm() + "</"  + XML_NODE_USER_NM + ">");
		_xml.append("<" + XML_NODE_MONITOR_CD + ">" + employeeroleconditionDO.getMonitor_cd() + "</"  + XML_NODE_MONITOR_CD + "> \n");	
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + employeeroleconditionDO.getSbs_user_ID() + "</"  + XML_NODE_SBS_USER_ID + "> \n");	

		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}


}

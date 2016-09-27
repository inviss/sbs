package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.NonEmployeeDASRoleDO;
import com.app.das.util.CommonUtl;


/**
 * 비직원 정보 관련 XML파서
 * @author asura207
 *
 */
public class NonEmployeeDASRoleDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "nonemployeerole";
	/**
	 *  비직원 sbs 사용자 id
	 */
	private String XML_NODE_SBS_USER_ID = "sbs_user_id";
	/**
	 *   비직원 사용자 이름
	 */
	private String XML_NODE_USER_NM="user_nm";
	/**
	 * 소속부서
	 */
	private String XML_NODE_W_DEPT="w_dept";
	/**
	 * 사용시작일
	 */
	private String XML_NODE_VLDDT_BGN="vlddt_bgn";
	/**
	 * 사용종료일
	 */
	private String XML_NODE_VLDDT_END="vlddt_end";
	/**
	 * 직책
	 */
	private String XML_NODE_POSITION="position";
	/**
	 * 승인여부
	 */
	private String XML_NODE_APPROVE_YN="approve_yn";
	/**
	 * 암호
	 */
	private String XML_NODE_PASSWORD="password";	
	/**
	 * 역활
	 */
	private String XML_NODE_ROLE="role";	
	/**
	 * 직원 휴대폰
	 */
	private String XML_NODE_MOBILE="mobile";	
	/**
	 * 계열사 전화번호
	 */
	private String XML_NODE_SUBSI_TEL="subsi_tel";			
	/**
	 * 프로그램 ID
	 */
	private String XML_NODE_PGM_ID="pgm_id";	
	/**
	 * 등록일
	 */
	private String XML_NODE_REG_DT="reg_dt";
	/**
	 * 수정일
	 */
	private String XML_NODE_MOD_DT="mod_dt";
	/**
	 * 삭제일
	 */
	private String XML_NODE_DEL_DD="del_dd";
	/**
	 * 마지막 비밀번호 바꾼기록
	 */
	private String XML_NODE_PW_LST_CHG="pw_lst_chg";
	/**
	 * 비밀번호 틀린횟수
	 */
	private String XML_NODE_PW_ERN="pw_ern";
	/**
	 * 정직원 유무
	 */
	private String XML_EMPLOYEE_YN="employee_yn";
	/**
	 * 직원유형 001 정직원 002 계열사 003 비직원
	 */
	private String XML_EMPLOYEE_TYPE="employee_type";




	public Object setDO(String _xml) {
		setDO(new NonEmployeeDASRoleDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData(null);
				setData((Element)_node);
			}
		}

		return getDO();
	}

	public Object setData(Element pElement) {
		NonEmployeeDASRoleDO employeeroleconditionDO = (NonEmployeeDASRoleDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				employeeroleconditionDO.setSbs_user_ID(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				employeeroleconditionDO.setUser_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_W_DEPT)) {
				employeeroleconditionDO.setW_Dept(_nodeValue);
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
			else if(_nodeName.equals(XML_NODE_ROLE)) {
				employeeroleconditionDO.setRole(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MOBILE)) {
				employeeroleconditionDO.setMobile(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_SUBSI_TEL)) {
				employeeroleconditionDO.setSubsi_tel(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PGM_ID)) {
				employeeroleconditionDO.setPgm_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}else if(_nodeName.equals(XML_NODE_REG_DT)) {
				employeeroleconditionDO.setReg_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_MOD_DT)) {
				employeeroleconditionDO.setMod_dt(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_DEL_DD)) {
				employeeroleconditionDO.setDel_DD(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PW_LST_CHG)) {

				employeeroleconditionDO.setPw_lst_chg(_nodeValue);

			}else if(_nodeName.equals(XML_NODE_PW_ERN)) {

				employeeroleconditionDO.setPw_ern(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));

			}else if(_nodeName.equals(XML_EMPLOYEE_YN)) {

				employeeroleconditionDO.setEmployee_yn(_nodeValue);

			}else if(_nodeName.equals(XML_EMPLOYEE_TYPE)) {

				employeeroleconditionDO.setEmployee_type(_nodeValue);

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
		NonEmployeeDASRoleDO employeeroleconditionDO = (NonEmployeeDASRoleDO)getDO();
		StringBuffer _xml = new StringBuffer();

		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_SBS_USER_ID + ">" + employeeroleconditionDO.getSbs_user_ID() + "</"  + XML_NODE_SBS_USER_ID + "> \n");
		_xml.append("<" + XML_NODE_USER_NM + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getUser_nm()) + "</"  + XML_NODE_USER_NM + "> \n");
		_xml.append("<" + XML_NODE_W_DEPT + ">" +employeeroleconditionDO.getW_Dept()+ "</"  + XML_NODE_W_DEPT + "> \n");
		_xml.append("<" + XML_NODE_VLDDT_BGN + ">" + employeeroleconditionDO.getVlddt_bgn() + "</"  + XML_NODE_VLDDT_BGN + "> \n");
		_xml.append("<" + XML_NODE_VLDDT_END + ">" + employeeroleconditionDO.getVlddt_end() + "</"  + XML_NODE_VLDDT_END + "> \n");
		_xml.append("<" + XML_NODE_POSITION + ">" + CommonUtl.transXmlText(employeeroleconditionDO.getPosition()) + "</"  + XML_NODE_POSITION + "> \n");
		_xml.append("<" + XML_NODE_APPROVE_YN + ">" + employeeroleconditionDO.getApprove_yn() + "</"  + XML_NODE_APPROVE_YN + "> \n");
		_xml.append("<" + XML_NODE_PASSWORD + ">" + employeeroleconditionDO.getPassword() + "</"  + XML_NODE_PASSWORD + "> \n");
		_xml.append("<" + XML_NODE_ROLE + ">" + employeeroleconditionDO.getRole() + "</"  + XML_NODE_ROLE + "> \n");
		_xml.append("<" + XML_NODE_MOBILE + ">" + employeeroleconditionDO.getMobile() + "</"  + XML_NODE_MOBILE + "> \n");
		_xml.append("<" + XML_NODE_SUBSI_TEL + ">" + employeeroleconditionDO.getSubsi_tel() + "</"  + XML_NODE_SUBSI_TEL + "> \n");		
		_xml.append("<" + XML_NODE_PGM_ID + ">" + employeeroleconditionDO.getPgm_id() + "</"  + XML_NODE_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_REG_DT + ">" + employeeroleconditionDO.getReg_dt() + "</"  + XML_NODE_REG_DT + "> \n");
		_xml.append("<" + XML_NODE_MOD_DT + ">" + employeeroleconditionDO.getMod_dt() + "</"  + XML_NODE_MOD_DT + "> \n");;
		_xml.append("<" + XML_NODE_DEL_DD + ">" + employeeroleconditionDO.getDel_DD() + "</"  + XML_NODE_DEL_DD + "> \n");
		_xml.append("<" + XML_NODE_PW_LST_CHG + ">" + employeeroleconditionDO.getPw_lst_chg() + "</"  + XML_NODE_PW_LST_CHG + "> \n");
		_xml.append("<" + XML_NODE_PW_ERN + ">" + employeeroleconditionDO.getPw_ern() + "</"  + XML_NODE_PW_ERN + "> \n");
		_xml.append("<" + XML_EMPLOYEE_YN + ">" + employeeroleconditionDO.getEmployee_yn() + "</"  + XML_EMPLOYEE_YN + "> \n");
		_xml.append("<" + XML_EMPLOYEE_TYPE + ">" + employeeroleconditionDO.getEmployee_type() + "</"  + XML_EMPLOYEE_TYPE + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");



		return _xml.toString();
	}
}

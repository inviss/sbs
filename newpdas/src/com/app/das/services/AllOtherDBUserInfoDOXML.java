package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.DiscardDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.OtherDBDeptInfoDO;
import com.app.das.business.transfer.OtherDBuserInfoDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 타시스템의 유져 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllOtherDBUserInfoDOXML extends DOXml{
	//OtherDBDeptInfoDO InfoDO;
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/**
	 * 내부 xml 헤더1
	 */
	private String XML_NODE_HEAD1 = "pds_ex_sync_userinfo";
	/**
	 * 내부 xml 헤더2
	 */
	private String XML_NODE_HEAD3 = "mamex_request";
	/**
	 * 내부 xml 헤더3
	 */
	private String XML_NODE_HEAD2 = "nds_ex_sync_userinfo";
	/**
	 * 내부 xml 헤더4
	 */
	private String XML_NODE_EX_CHANGE_PW = "ex_sync_change_passwd";
	/**
	 *   사용자 이름
	 */
	private String XML_NODE_USER_ID = "username";
	/**
	 *   sbs 사용자 id
	 */
	private String XML_NODE_SBS_USER_ID = "userid";
	/**
	 * 사번
	 */
	private String XML_NODE_USER_NUM = "workerid";
	/**
	 * 주민등록번호
	 */
	private String XML_NODE_PER_REG_NO = "citizennum";
	/**
	 * 계정유형
	 */
	private String XML_NODE_ACCT_CODE = "workertype";
	/**
	 * 계정유형
	 */
	private String XML_NODE_ACCT_CODE2 = "acct_code";
	/**
	 * 삭제여부
	 */
	private String XML_NODE_DELTE_YN = "delete_yn";
	/**
	 * 승인상태
	 */
	private String XML_NODE_APPROVE_STATUS = "active_status";
	/**
	 * 회사 코드
	 */
	private String XML_NODE_COCD = "comp_code";
	/**
	 *   사용자 이름
	 */
	private String XML_NODE_USER_NM = "realname";
	/**
	 * 암호
	 */
	private String XML_NODE_PASSWORD = "password"; 
	/**
	 * 바꿀암호
	 */
	private String XML_NODE_NEWPASSWORD = "newpasswd"; 
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "deptcode";
	/**
	 * 전화번호
	 */
	private String XML_NODE_PHONE = "phone_num";

	
	
	//20120727 신규 ifcms 로직 함수
	
	/**
	 * TR명 - ex_regist_userinfo
	 */
	private String gua_sync_userinfo = "gua_sync_userinfo";
	/**
	 * 시스템id - 미구현
	 */
	private String system_id = "system_id";
	/**
	 * 작업종류 - C:생성, U:변경, D:삭제
	 */
	private String operation_type = "operation_type";
	/**
	 * 사용자ID -userid
	 */
	private String user_id = "user_id";
	/**
	 * 사번 - user_num
	 */
	private String user_id_num = "user_id_num";
	/**
	 * 주민번호 -per_reg_no
	 */
	private String resident_reg_num = "resident_reg_num";
	/**
	 * 직원유형 -acct_code
	 */
	private String user_type = "user_type";
	/**
	 * 회사코드 - cocd
	 */
	private String company_code = "company_code";
	/**
	 * 사용자명 - user_nm
	 */
	private String user_name = "user_name";
	/**
	 * 패스워드 - password
	 */
	private String password = "password";
	/**
	 * 부서코드 - dept_cd
	 */
	private String department_code = "department_code";
	/**
	 * 계정유효 시작일 - acctvaliddate
	 */
	private String account_active_date = "account_active_date";
	/**
	 * 계정유효 종료일 - acctexpiredate
	 */
	private String account_expire_date = "account_expire_date";

	
	public Object setDO(String _xml) {
		setDO(new EmployeeInfoDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			
			
			NodeList node2= _node.getChildNodes();
			Node _node2 = node2.item(i);
			String _nodeName2 = _node2.getNodeName() ;
			
			NodeList node3= _node2.getChildNodes();
			Node _node3 = node3.item(i);
			String _nodeName3 = _node3.getNodeName() ;
			
			NodeList node4= _node3.getChildNodes();
			Node _node4 = node4.item(i);		
			String _nodeName4 = _node4.getNodeName() ;
			
			NodeList node5= _node4.getChildNodes();
			Node _node5 = node5.item(i);		
			String _nodeName5 = _node5.getNodeName() ;
			
			
			if(_nodeName5.equals(XML_NODE_HEAD2)) {
				setData((Element)_node5);
			}
        }
		
		return getDO();

	}
	
/*	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
        	InfoDO = new PgmUserInfoDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			
			
			NodeList node2= _node.getChildNodes();
			Node _node2 = node2.item(i);
			String _nodeName2 = _node2.getNodeName() ;
			
			NodeList node3= _node2.getChildNodes();
			Node _node3 = node3.item(i);
			String _nodeName3 = _node3.getNodeName() ;
			
			NodeList node4= _node3.getChildNodes();
			Node _node4 = node4.item(i);		
			String _nodeName4 = _node4.getNodeName() ;
			
			NodeList node5= _node4.getChildNodes();
			Node _node5 = node5.item(i);		
			String _nodeName5 = _node5.getNodeName() ;
			
			
			if(_nodeName5.equals(XML_NODE_HEAD2)) {
				setData((Element)_node5);
			}
			result.add(InfoDO);
        }
		return getDO();
	}
	
	*/
	


	public Object setData(Element pElement) {
		EmployeeInfoDO InfoDO = (EmployeeInfoDO)getDO();
		
	
	
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			//NamedNodeMap startAttr = _node.getAttributes();
			//String nodeValue = getNodeValue(_node);
		

			if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				InfoDO.setSbs_user_ID(_nodeValue);
				}
				
	    }
	    return InfoDO;
	}	    
	
	public String toXML() {
		StringBuffer _xml = new StringBuffer();
		_xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
		_xml.append("<das> \n");
		//_xml = _xml + getSubXML();
		_xml.append("</das>"); 
		
		return _xml.toString();
	}

	public String getSubXML() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>pds_ex_sync_userinfo</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_HEAD1 + "&gt;");
		_xml.append("&lt;" + XML_NODE_USER_ID + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + XML_NODE_USER_ID + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_USER_NUM + "&gt;" + infoDO.getUser_num() + "&lt;/"  + XML_NODE_USER_NUM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_PER_REG_NO + "&gt;" + infoDO.getPer_reg_no() + "&lt;/"  + XML_NODE_PER_REG_NO + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_ACCT_CODE + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + XML_NODE_ACCT_CODE + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DELTE_YN + "&gt;" + infoDO.getDelete_yn() + "&lt;/"  + XML_NODE_DELTE_YN + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_APPROVE_STATUS + "&gt;" + infoDO.getApprove_status() + "&lt;/"  + XML_NODE_APPROVE_STATUS + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_COCD + "&gt;" + infoDO.getCocd() + "&lt;/"  + XML_NODE_COCD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_USER_NM + "&gt;" + infoDO.getUser_nm() + "&lt;/"  + XML_NODE_USER_NM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_PASSWORD + "&gt;" + infoDO.getPassword() + "&lt;/"  + XML_NODE_PASSWORD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DEPT_CD + "&gt;" + infoDO.getDept_cd() + "&lt;/"  + XML_NODE_DEPT_CD + "&gt; \n");	
		_xml.append("&lt;/" + XML_NODE_HEAD1 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		return _xml.toString();
	}

	
	public String getSubXML2() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>nds_ex_sync_userinfo</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append( "&lt;" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;" + XML_NODE_USER_ID + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + XML_NODE_USER_ID + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_USER_NUM + "&gt;" + infoDO.getUser_num() + "&lt;/"  + XML_NODE_USER_NUM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_PER_REG_NO + "&gt;" + infoDO.getPer_reg_no() + "&lt;/"  + XML_NODE_PER_REG_NO + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_ACCT_CODE + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + XML_NODE_ACCT_CODE + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DELTE_YN + "&gt;" + infoDO.getDelete_yn() + "&lt;/"  + XML_NODE_DELTE_YN + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_APPROVE_STATUS + "&gt;" + infoDO.getApprove_status() + "&lt;/"  + XML_NODE_APPROVE_STATUS + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_COCD + "&gt;" + infoDO.getCocd() + "&lt;/"  + XML_NODE_COCD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_USER_NM + "&gt;" + infoDO.getUser_nm() + "&lt;/"  + XML_NODE_USER_NM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_PASSWORD + "&gt;" + infoDO.getPassword() + "&lt;/"  + XML_NODE_PASSWORD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DEPT_CD + "&gt;" + infoDO.getDept_cd() + "&lt;/"  + XML_NODE_DEPT_CD + "&gt; \n");
		_xml.append("&lt;/" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		return _xml.toString();
	}

	
	
	
	
	

	//password 변환 xml
	public String getSubXML3() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();
/*		String _xml = "<kscc_request><runusertransaction><transactionname>gua_sync_password</transactionname><timeout>2</timeout><requestxml>\n";
		_xml =_xml + "&lt;" + XML_NODE_HEAD3 + "&gt; \n";
		_xml = _xml + "&lt;" + XML_NODE_EX_CHANGE_PW + "&gt;";
		_xml = _xml + "&lt;" + XML_NODE_SBS_USER_ID + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + XML_NODE_SBS_USER_ID + "&gt; \n";
		_xml = _xml + "&lt;" + XML_NODE_ACCT_CODE2 + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + XML_NODE_ACCT_CODE2 + "&gt; \n";
		_xml = _xml + "&lt;" + XML_NODE_NEWPASSWORD + "&gt;" + infoDO.getNewPassword() + "&lt;/"  + XML_NODE_NEWPASSWORD + "&gt; \n";
		_xml = _xml + "&lt;/" + XML_NODE_EX_CHANGE_PW + "&gt;";
		_xml = _xml + "&lt;/" + XML_NODE_HEAD3 + "&gt;";
		_xml = _xml + "</requestxml></runusertransaction></kscc_request>";*/
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>gua_sync_password</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + "gua_sync_password" + "&gt;");
		_xml.append("&lt;" + "src" + "&gt;" + "das"+ "&lt;/"  + "src" + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SBS_USER_ID + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + XML_NODE_SBS_USER_ID + "&gt; \n");
		_xml.append("&lt;" + "jobapplicant" + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + "jobapplicant" + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_ACCT_CODE2 + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + XML_NODE_ACCT_CODE2 + "&gt; \n");
		_xml.append("&lt;" + "oldpasswd" + "&gt;" + "" + "&lt;/"  + "oldpasswd" + "&gt; \n");
		_xml.append("&lt;" + "newpasswd" + "&gt;" + infoDO.getNewPassword()+ "&lt;/"  + "newpasswd" + "&gt; \n");
		_xml.append("&lt;/" + "gua_sync_password" + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		return _xml.toString();
	}
	
	

	//IFCMSpassword 변환 xml
	public String getIfCmsPwSycn() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>gua_sync_password</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + "gua_sync_password" + "&gt;");
		_xml.append("&lt;" + "src" + "&gt;" + "das"+ "&lt;/"  + "src" + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SBS_USER_ID + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + XML_NODE_SBS_USER_ID + "&gt; \n");
		_xml.append("&lt;" + "jobapplicant" + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + "jobapplicant" + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_ACCT_CODE2 + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + XML_NODE_ACCT_CODE2 + "&gt; \n");
		_xml.append("&lt;" + "oldpasswd" + "&gt;" + "" + "&lt;/"  + "oldpasswd" + "&gt; \n");
		_xml.append("&lt;" + "newpasswd" + "&gt;" + infoDO.getNewPassword()+ "&lt;/"  + "newpasswd" + "&gt; \n");
		_xml.append("&lt;/" + "gua_sync_password" + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		return _xml.toString();
	}
	
	
	
	
//ifcms동기화
	public String getIfCmsXML() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>gua_sync_userinfo</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + gua_sync_userinfo + "&gt;");
		_xml.append("&lt;" + system_id + "&gt;das&lt;/"  + system_id + "&gt; \n");
		_xml.append("&lt;" + operation_type + "&gt;" + infoDO.getGubun() + "&lt;/"  + operation_type + "&gt; \n");
		_xml.append("&lt;" + user_id + "&gt;" + infoDO.getSbs_user_ID() + "&lt;/"  + user_id + "&gt; \n");
		_xml.append("&lt;" + user_id_num + "&gt;" + infoDO.getUser_num() + "&lt;/"  + user_id_num + "&gt; \n");
		_xml.append("&lt;" + resident_reg_num + "&gt;" + infoDO.getPer_reg_no() + "&lt;/"  + resident_reg_num + "&gt; \n");
		_xml.append("&lt;" + user_type + "&gt;" + infoDO.getAcct_code()+ "&lt;/"  + user_type + "&gt; \n");
		_xml.append("&lt;" + company_code + "&gt;" + infoDO.getCocd() + "&lt;/"  + company_code + "&gt; \n");
		_xml.append("&lt;" + user_name + "&gt;" + infoDO.getUser_nm() + "&lt;/"  + user_name + "&gt; \n");
		_xml.append("&lt;" + password + "&gt;" + infoDO.getPassword() + "&lt;/"  + password + "&gt; \n");
		_xml.append("&lt;" + account_active_date + "&gt;" + infoDO.getVlddt_bgn() + "&lt;/"  + account_active_date + "&gt; \n");
		_xml.append("&lt;" + account_expire_date + "&gt;" + infoDO.getVlddt_end() + "&lt;/"  + account_expire_date + "&gt; \n");
		_xml.append("&lt;" + "phone_num" + "&gt;" + infoDO.getMobile() + "&lt;/"  + "phone_num" + "&gt; \n");
		
		//20130130 ifcms 회사코드 동기화
		_xml.append("&lt;" + XML_NODE_PHONE + "&gt;" + infoDO.getMobile() + "&lt;/"  + XML_NODE_PHONE + "&gt; \n");	
		_xml.append("&lt;" + department_code + "&gt;" + infoDO.getDept_cd() + "&lt;/"  + department_code + "&gt; \n");
		_xml.append("&lt;/" + gua_sync_userinfo + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		return _xml.toString();
	}
	
	
	


}

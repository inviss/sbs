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


import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.EmployeeInfoDO;
import com.app.das.business.transfer.PaUserInfoDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  유져 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllUserInfoDOXML extends DOXml{
	EmployeeInfoDO infoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "employeeinfo";
	/**
	 *   sbs 사용자 id
	 */
	private String XML_NODE_SBS_USER_ID = "sbs_user_id";	
	/**
	 *   sbs 사용자 id
	 */
	private String XML_NODE_USER_ID = "user_id";	
	/**
	 * 사번
	 */
	private String XML_NODE_USER_NUM="user_num";

	/**
	 * 주민등록번호
	 */
	private String XML_NODE_PER_REG_NO="per_reg_no";
	/**
	 * 계정유형
	 */
	private String XML_NODE_ACCT_CODE="acct_code";
	/**
	 * 삭제여부
	 */
	private String XML_NODE_DELETE_YN = "delete_yn";
	/**
	 * 승인상태
	 */
	private String XML_NODE_APPROVE_STATUS = "approve_status";
	/**
	 * 회사 코드
	 */
	private String XML_NODE_COCD="cocd";
	/**
	 *   사용자 이름
	 */
	private String XML_NODE_USER_NM="user_name";
	/**
	 * 암호
	 */
	private String XML_NODE_PASSWORD="password";	
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD="dept_cd";

	/**
	 * 플레그
	 */
	private String XML_NODE_FLAG="flag";
	/**
	 * 플레그
	 */
	private String XML_NODE_OPERATION_TYPE="operation_type";

	/**
	 * 사용시작일
	 */
	private String XML_NODE_ACCTEXPIREDATE="acctexpiredate";

	/**
	 * 사용종료일
	 */
	private String XML_NODE_ACCTVALIDDATE="acctvaliddate";


	/**
	 * 휴대폰
	 */
	private String XML_NODE_PHONE_NUM="phone_num";



	//20120813 신규 xml로 변환
	/**
	 * 해더1
	 */
	private String mamex_request="mamex_request";
	/**
	 * 해더2
	 */
	private String gua_sync_userinfo="gua_sync_userinfo";
	/**
	 * 구분
	 */
	private String operation_type="operation_type";
	/**
	 * 시스템id
	 */
	private String system_id="system_id";
	/**
	 * 아이디
	 */
	private String user_id="user_id";
	/**
	 * 사원번호
	 */
	private String user_id_num="user_id_num";
	/**
	 * 주민번호
	 */
	private String resident_reg_num="resident_reg_num";
	/**
	 * 계정타입
	 */
	private String user_type="user_type";
	/**
	 * 부서코드
	 */
	private String company_code="company_code";
	/**
	 * 사용자명
	 */
	private String user_name="user_name";
	/**
	 * 패스워드
	 */
	private String password="password";
	/**
	 * 회사코드
	 */
	private String department_code="department_code";
	/**
	 * 전화번호
	 */
	private String phone_num="phone_num";
	/**
	 * 계정유효일
	 */
	private String account_active_date="account_active_date";
	/**
	 * 계정만료일
	 */
	private String account_expire_date="account_expire_date";
	/**
	 * 부서코드
	 */
	private String company="company";




	/*public Object setDO(String _xml) {
		setDO(new PgmInfoDO());

		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			if(_nodeName.equals(XML_NODE_SUB_HEAD)) {
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
			infoDO = new EmployeeInfoDO();
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;

			if(_nodeName.equals(XML_NODE_HEAD)) {
				setData((Element)_node);
			}else if(_nodeName.equals(gua_sync_userinfo)){
				setData2((Element)_node);
			}
			System.out.println("22222222");
			//result.add(infoDO);
			System.out.println("result.size()"+result.size());
		}
		System.out.println("result.size()"+result.size());
		return getDO();
	}





	public Object setData(Element pElement) {
		List result = (List)getDO();
		//PgmInfoDO infoDO = (PgmInfoDO)getDO();
		//List result = new ArrayList();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_SBS_USER_ID)) {
				infoDO.setSbs_user_ID(_nodeValue);
			}
			else  if(_nodeName.equals(XML_NODE_USER_ID)) {
				infoDO.setSbs_user_ID(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_USER_NUM)) {
				infoDO.setUser_num(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PER_REG_NO)) {
				infoDO.setPer_reg_no(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_ACCT_CODE)) {
				infoDO.setAcct_code(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DELETE_YN)) {
				infoDO.setDelete_yn(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_APPROVE_STATUS)) {
				infoDO.setApprove_status(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_COCD)) {
				infoDO.setCocd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PASSWORD)) {
				infoDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_NM)) {
				infoDO.setUser_nm(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				infoDO.setDept_cd(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_FLAG)) {
				infoDO.setFlag(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_OPERATION_TYPE)) {
				infoDO.setFlag(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACCTEXPIREDATE)) {
				infoDO.setVlddt_end(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_ACCTVALIDDATE)) {
				infoDO.setVlddt_bgn(_nodeValue);
			}else if(_nodeName.equals(XML_NODE_PHONE_NUM)) {
				infoDO.setMobile(_nodeValue);
			}else if(_nodeName.equals(company)) {

				//String _undername = _node.getNodeName() ;
				//String _underValue2 = getNodeValue(_node);
				NamedNodeMap _underattbute = _node.getAttributes();

				for(int w = 0; w<_underattbute.getLength();w++){
					Node attr = _underattbute.item(w);
					String nodeName = attr.getNodeName() ;
					String att= attr.getNodeValue();
					//System.out.println("nodeName ======"+nodeName);
					//System.out.println("att ======"+att);
					if(nodeName.equals("code")){
						infoDO.setDept_cd(att);

					}else if(nodeName.equals("name")){
						infoDO.setDept_nm("");
					}else if(nodeName.equals("head")){

					}

				}

			}

		}
		result.add(infoDO);
		return result;
	}	    




	public Object setData2(Element pElement) {
		List result = (List)getDO();
		//PgmInfoDO infoDO = (PgmInfoDO)getDO();
		//List result = new ArrayList();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = getNodeValue(_node);

			if(_nodeName.equals(user_id)) {
				infoDO.setSbs_user_ID(_nodeValue);
			}			

			else if(_nodeName.equals(user_id_num)) {
				infoDO.setUser_num(_nodeValue);
			}

			else if(_nodeName.equals(resident_reg_num)) {
				infoDO.setPer_reg_no(_nodeValue);
			}

			else if(_nodeName.equals(user_type)) {
				infoDO.setAcct_code(_nodeValue);
			}
			else if(_nodeName.equals(department_code)) {
				infoDO.setDept_cd(_nodeValue);
			}

			else if(_nodeName.equals(password)) {
				infoDO.setPassword(_nodeValue);
			}
			else if(_nodeName.equals(user_name)) {
				infoDO.setUser_nm(_nodeValue);
			}

			else if(_nodeName.equals(company_code)) {
				infoDO.setCocd(_nodeValue);
			}
			else if(_nodeName.equals(operation_type)) {
				infoDO.setFlag(_nodeValue);
			}
			else if(_nodeName.equals(account_expire_date)) {
				infoDO.setVlddt_end(_nodeValue);
			}
			else if(_nodeName.equals(account_active_date)) {
				infoDO.setVlddt_bgn(_nodeValue);
			}else if(_nodeName.equals(phone_num)) {
				infoDO.setMobile(_nodeValue);
			}


		}
		result.add(infoDO);
		return result;
	}	    



	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}

	public String getSubXML() {
		EmployeeInfoDO infoDO = (EmployeeInfoDO)getDO();

		String _xml = "<" + XML_NODE_HEAD + "> \n";



		_xml = _xml + "</" + XML_NODE_HEAD + ">";

		return _xml;
	}




}

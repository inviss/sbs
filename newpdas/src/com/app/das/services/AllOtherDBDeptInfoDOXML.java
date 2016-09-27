package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.OtherDBDeptInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 타시스템의 부서 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllOtherDBDeptInfoDOXML extends DOXml{
	//PgmUserInfoDO InfoDO;
	/**
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/**
	 * 내부xml 헤더
	 */
	private String XML_NODE_HEAD2 = "pds_ex_sync_groupinfo";
	/**
	 * 회사코드
	 */
	private String XML_NODE_COCD = "companycode";
	/**
	 * 부서코드
	 */
	private String XML_NODE_DEPT_CD = "deptcode";
	/**
	 * 부서장사번
	 */
	private String XML_NODE_DEPT_CHAMP_NUM = "deptbossempno";
	/**
	 * 소속부서명
	 */
	private String XML_NODE_DEPT_NM = "dept_nm";
	/**
	 * 부서레벨
	 */
	private String XML_NODE_LVL = "lvl";
	/**
	 * 소속단위구분
	 */
	private String XML_NODE_POST_UNIT_CLF = "post_unit_clf";
	/**
	 * 부서순번
	 */
	private String XML_NODE_SEQ = "seq";
	/**
	 * 상위부서코드
	 */
	private String XML_NODE_SUPERDEPTCODE = "superdeptcode";
	/**
	 * 본부코드
	 */
	private String XML_NODE_SUPERHEADCODE = "superheadcode";
	/**
	 * 본부명
	 */
	private String XML_NODE_SUPERHEADNAME = "superheadname"; 
	/**
	 * 본부순서
	 */
	private String XML_NODE_SUPDERHEADSEQ = "superheadseq";
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
	
	
	
	

	
/*	public Object setDO(String _xml) {
		setDO(new PgmUserInfoDO());
		
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
		setDO(new OtherDBDeptInfoDO());
		
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
        NodeList _nodeList = _rootElement.getChildNodes();
        int _length = _nodeList.getLength();
        for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			//String _nodeName = _node.getNodeName() ;
			
			
			NodeList node2= _node.getChildNodes();
			Node _node2 = node2.item(i);
			//String _nodeName2 = _node2.getNodeName() ;
			
			NodeList node3= _node2.getChildNodes();
			Node _node3 = node3.item(i);
			//String _nodeName3 = _node3.getNodeName() ;
			
			NodeList node4= _node3.getChildNodes();
			Node _node4 = node4.item(i);		
			//String _nodeName4 = _node4.getNodeName() ;
			
			NodeList node5= _node4.getChildNodes();
			Node _node5 = node5.item(i);		
			String _nodeName5 = _node5.getNodeName() ;
			
			
			if(_nodeName5.equals(XML_NODE_HEAD2)) {
				setData((Element)_node5);
			}
        }
		
		return getDO();

	}
	
	


	public Object setData(Element pElement) {
		//OtherDBDeptInfoDO InfoDO = (OtherDBDeptInfoDO)getDO();
		DepInfoDO InfoDO = (DepInfoDO)getDO();
	
	
	    NodeList _nodeList = pElement.getChildNodes();
	    int _length = _nodeList.getLength();
	    for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			//NamedNodeMap startAttr = _node.getAttributes();
			//String nodeValue = getNodeValue(_node);
		

			if(_nodeName.equals(XML_NODE_COCD)) {
				InfoDO.setCocd(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_DEPT_CD)) {
				InfoDO.setDept_cd(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_DEPT_CHAMP_NUM)) {
				InfoDO.setDept_chap_emp_no(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_DEPT_NM)) {
				InfoDO.setDept_nm(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_LVL)) {
				InfoDO.setDept_nm(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_POST_UNIT_CLF)) {
				InfoDO.setPost_unit_clf(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SEQ)) {
				InfoDO.setSeq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			if(_nodeName.equals(XML_NODE_SUPERDEPTCODE)) {
				InfoDO.setSup_dept_cd(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SUPERHEADCODE)) {
				InfoDO.setSup_head_cd(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SUPERHEADNAME)) {
				InfoDO.setSup_head_nm(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SUPDERHEADSEQ)) {
				InfoDO.setSup_head_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			if(_nodeName.equals(XML_NODE_SUP_HTPO_CD)) {
				InfoDO.setSup_htpo_cd(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SUP_HTPO_NM)) {
				InfoDO.setSup_htpo_nm(_nodeValue);
				}
			if(_nodeName.equals(XML_NODE_SUP_HTPO_SEQ)) {
				InfoDO.setSup_htpo_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
				}
			
				
	    }
	    return InfoDO;
	}	    

	public String toXML() {
		DepInfoDO infoDO = (DepInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		
		_xml.append("<kscc_request><runusertransaction><transactionname>pds_ex_sync_groupinfo</transactionname><timeout>2</timeout><requestxml>\n") ;
		_xml.append("&lt;" + XML_NODE_HEAD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;" + XML_NODE_COCD + "&gt;" + infoDO.getCocd() + "&lt;/"  + XML_NODE_COCD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DEPT_CD + "&gt;" + infoDO.getDept_cd() + "&lt;/"  + XML_NODE_DEPT_CD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DEPT_CHAMP_NUM + "&gt;" + infoDO.getDept_chap_emp_no() + "&lt;/"  + XML_NODE_DEPT_CHAMP_NUM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_DEPT_NM + "&gt;" + infoDO.getDept_nm() + "&lt;/"  + XML_NODE_DEPT_NM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_LVL + "&gt;" + infoDO.getLvl()+ "&lt;/"  + XML_NODE_LVL + "&gt; \n");		
		_xml.append("&lt;" + XML_NODE_POST_UNIT_CLF + "&gt;" + infoDO.getPost_unit_clf() + "&lt;/"  + XML_NODE_POST_UNIT_CLF + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SEQ + "&gt;" + infoDO.getSeq() + "&lt;/"  + XML_NODE_SEQ + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUPERDEPTCODE + "&gt;" + infoDO.getSup_dept_cd() + "&lt;/"  + XML_NODE_SUPERDEPTCODE + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUPERHEADCODE + "&gt;" + infoDO.getSup_head_cd() + "&lt;/"  + XML_NODE_SUPERHEADCODE + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUPERHEADNAME + "&gt;" + infoDO.getSup_head_nm() + "&lt;/"  + XML_NODE_SUPERHEADNAME + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUPDERHEADSEQ + "&gt;" + infoDO.getSup_head_seq() + "&lt;/"  + XML_NODE_SUPDERHEADSEQ + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUP_HTPO_CD + "&gt;" + infoDO.getSup_htpo_cd() + "&lt;/"  + XML_NODE_SUP_HTPO_CD + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUP_HTPO_NM + "&gt;" + infoDO.getSup_htpo_nm() + "&lt;/"  + XML_NODE_SUP_HTPO_NM + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_SUP_HTPO_SEQ + "&gt;" + infoDO.getSup_htpo_seq() + "&lt;/"  + XML_NODE_SUP_HTPO_SEQ + "&gt; \n");

		
		_xml.append("&lt;/" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");
		
		return _xml.toString();
	}



}

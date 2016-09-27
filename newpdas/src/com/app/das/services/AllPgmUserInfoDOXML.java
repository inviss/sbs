package com.app.das.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 타시스템의 프로그램별 유져 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllPgmUserInfoDOXML extends DOXml{
	PgmUserInfoDO InfoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/** 
	 * 에러 xml 헤더
	 */
	private String XML_NODE_ERROR_HEAD = "error";
	/** 
	 * 내부 xml 헤더
	 */
	private String XML_NODE_SUB_HEAD = "pds_ex_get_userinfobyfolder";
	/** 
	 * 유져리스트 xml 헤더
	 */
	private String XML_NODE_USER_HEAD = "userlist";
	/** 
	 * 스토리지id
	 */
	private String XML_NODE_STORAGEID = "storageid";
	/** 
	 * 스토리지nm
	 */
	private String XML_NODE_STORAGE_NAME = "storagename";
	/** 
	 * 트리노드id
	 */
	private String XML_NODE_TREENODEID = "treenodeid";
	/** 
	 * 트리노드명
	 */
	private String XML_NODE_TREENODE_NAME = "treenodename";
	/** 
	 * 프로그램 코드
	 */
	private String XML_NODE_PROGRAM_CODE = "programcode";

	/** 
	 * 프로그램명
	 */
	private String XML_NODE_PROGRAM_NAME = "programname";
	/** 
	 * 유저id
	 */
	//private String XML_NODE_USERID = "id"; 
	/** 
	 * 권한
	 */
	//private String XML_NODE_AUTHORITY = "authority";
	/** 
	 * 사용자 정보리스트
	 */
	private String XML_NODE_USER = "user";




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
			if(_nodeName.equals(XML_NODE_ERROR_HEAD)){
				return getDO();
			}

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


			if(_nodeName5.equals(XML_NODE_SUB_HEAD)) {
				setData((Element)_node5);
			}
			result.add(InfoDO);
		}
		return getDO();
	}





	public Object setData(Element pElement) {
		List result = (List)getDO();

		String usernmList="0";
		String useridList="0";
		String authorityList="0";

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			//NamedNodeMap startAttr = _node.getAttributes();
			//String nodeValue = getNodeValue(_node);


			if(_nodeName.equals(XML_NODE_STORAGEID)) {
				InfoDO.setSTORAGEID(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_STORAGE_NAME)) {
				InfoDO.setSTORAGENM(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TREENODEID)) {
				InfoDO.setTREENODEID(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_TREENODE_NAME)) {
				InfoDO.setTreenodename(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PROGRAM_CODE)) {
				InfoDO.setProgramcd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PROGRAM_NAME)) {
				InfoDO.setProgramnm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_HEAD)) {

				NodeList nList = _node.getChildNodes();

				int leng = nList.getLength();					 
				for(int q=0; q<leng ; q++){
					Node nde = nList.item(q);
					String nName = nde.getNodeName() ;
					String nValue = getNodeValue(nde);
					NamedNodeMap CpdAttr = nde.getAttributes();
					//String _nValue = getNodeValue(nde);

					if(nName.equals(XML_NODE_USER)){
						for(int k = 0; k<CpdAttr.getLength();k++){
							Node attr = CpdAttr.item(k);
							//System.out.println("CpdAttr.item(i).getAttributes()=["+attr.getNodeName()+"]["+attr.getNodeValue()+"]");
							String nodeName = attr.getNodeName() ;
							//String att= attr.getNodeValue();

							//	 infoDO.setSuccess_yn(att);
							if(nodeName.equals("id")){
								//infoDO.setCid(attr.getNodeValue());
								useridList +=","+attr.getNodeValue();
								InfoDO.setUserid(useridList);

							}else if(nodeName.equals("authority")){
								//infoDO.setCid(attr.getNodeValue());
								authorityList +=","+attr.getNodeValue();
								InfoDO.setAuthority(authorityList);

							}					

						}
						usernmList += ","+nValue;
						InfoDO.setUsername(usernmList);


					}

				}	
			}
		}
		return InfoDO;
	}	    

	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>"; 

		return _xml;
	}

	public String getSubXML() {
		PgmUserInfoDO infoDO = (PgmUserInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_SUB_HEAD + ">");
		_xml.append("<" + XML_NODE_STORAGEID + ">" + infoDO.getSTORAGEID() + "</"  + XML_NODE_STORAGEID + "> \n");

		_xml.append("</" + XML_NODE_SUB_HEAD + ">");




		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}



}

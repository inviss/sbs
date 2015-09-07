package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 *  프로그램별 사용자 정보 관련 XML파서
 * @author asura207
 *
 */
public class PgmUserInfoDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/**
	 * xml해더
	 */
	private String XML_NODE_SUB_HEAD = "pds_ex_get_userinfobyfolder";
	/**
	 * 사용자 리스트
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
	private String XML_NODE_USERID = "id"; 
	/** 
	 * 권한
	 */
	private String XML_NODE_AUTHORITY = "authority";
	/** 
	 * 유저명
	 */
	private String XML_NODE_USER = "user";




	public Object setDO(String _xml) {
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
	}






	public Object setData(Element pElement) {
		PgmUserInfoDO infoDO = (PgmUserInfoDO)getDO();
		List result = new ArrayList();
		String usernmList="0";
		String useridList="0";
		String authorityList="0";

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			NamedNodeMap startAttr = _node.getAttributes();
			String nodeValue = getNodeValue(_node);


			if(_nodeName.equals(XML_NODE_STORAGEID)) {
				infoDO.setSTORAGEID(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_STORAGE_NAME)) {
				infoDO.setSTORAGENM(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_TREENODEID)) {
				infoDO.setTREENODEID(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_TREENODE_NAME)) {
				infoDO.setTreenodename(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PROGRAM_CODE)) {
				infoDO.setProgramcd(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PROGRAM_NAME)) {
				infoDO.setProgramnm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_USER_HEAD)) {

				NodeList nList = _node.getChildNodes();

				int leng = nList.getLength();					 
				for(int q=0; q<leng ; q++){
					Node nde = nList.item(q);
					String nName = nde.getNodeName() ;
					String nValue = getNodeValue(nde);
					NamedNodeMap CpdAttr = nde.getAttributes();
					String _nValue = getNodeValue(nde);

					if(nName.equals(XML_NODE_USER)){
						for(int k = 0; k<CpdAttr.getLength();k++){
							Node attr = CpdAttr.item(k);
							//System.out.println("CpdAttr.item(i).getAttributes()=["+attr.getNodeName()+"]["+attr.getNodeValue()+"]");
							String nodeName = attr.getNodeName() ;
							String att= attr.getNodeValue();

							//	 infoDO.setSuccess_yn(att);



							if(nodeName.equals("id")){
								//infoDO.setCid(attr.getNodeValue());
								useridList +=","+attr.getNodeValue();
								infoDO.setUserid(useridList);

							}else if(nodeName.equals("authority")){
								//infoDO.setCid(attr.getNodeValue());
								authorityList +=","+attr.getNodeValue();
								infoDO.setAuthority(authorityList);

							}					

						}
						usernmList += ","+nValue;
						infoDO.setUsername(usernmList);


					}

				}	
			}
		}
		return infoDO;
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

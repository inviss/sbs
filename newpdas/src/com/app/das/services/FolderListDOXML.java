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
import com.app.das.business.transfer.FolderListDO;
import com.app.das.business.transfer.OtherDBDeptInfoDO;
import com.app.das.business.transfer.OtherDBuserInfoDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
/**
 *  타시스템의 계정별 권한 폴더 정보 관련 XML파서
 * @author asura207
 *
 */
public class FolderListDOXML extends DOXml{
	//PgmUserInfoDO InfoDO;
	/**
	 * xml 헤더1
	 */
	//private String XML_NODE_HEAD = "mamex_response";
	/**
	 * xml 헤더2
	 */
	private String XML_NODE_HEAD3 = "mamex_request";
	/**
	 * xml 헤더3
	 */
	private String XML_NODE_HEAD2 = "pds_ex_service_folderlist";
	/**
	 * xml 헤더4
	 */
	private String XML_NODE_HEAD4 = "nds_ex_service_folderlist";
	/**
	 * folder
	 */
	private String XML_NODE_FOLDER = "folder";
	/**
	 * cellname
	 */
	//private String XML_NODE_CELLNAME = "cellname";

	/**
	 * nodeid
	 */
	//private String XML_NODE_NODEID = "nodeid";
	/**
	 * nodecaption
	 */
	//private String XML_NODE_NODECAPTION = "nodecaption";
	/**
	 * parentnodeid
	 */
	//private String XML_NODE_PARENTNODEID = "parentnodeid";
	/**
	 * parentnodecaption
	 */
	//private String XML_NODE_PARENTNODECAPTION = "parentnodecaption";
	/**
	 * userid
	 */
	private String XML_NODE_USERID = "userid";



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
		setDO(new FolderListDO());

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
		FolderListDO InfoDO = (FolderListDO)getDO();


		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			//Node _node = _nodeList.item(i);
			//String _nodeName = _node.getNodeName() ;
			//String _nodeValue = getNodeValue(_node);
			//NamedNodeMap startAttr = _node.getAttributes();
			//String nodeValue = getNodeValue(_node);
		}
		return InfoDO;
	}	    

	public String toXML() {

		String _xml = "<kscc_request> \n";
		_xml = _xml + "<runusertransaction> \n";
		//_xml = _xml + getSubXML();
		_xml = _xml + "</runusertransaction>"; 

		return _xml;
	}

	public String getSubXML(String userid) {
		//FolderListDO infoDO = (FolderListDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>pds_ex_service_folderlist</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;" + XML_NODE_USERID + "&gt;" + userid + "&lt;/"  + XML_NODE_USERID + "&gt; \n");
		_xml.append("&lt;/" + XML_NODE_HEAD2 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");

		return _xml.toString();
	}
	public String getSubXML2(String userid) {
		//FolderListDO infoDO = (FolderListDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<kscc_request><runusertransaction><transactionname>nds_ex_service_folderlist</transactionname><timeout>2</timeout><requestxml>\n");
		_xml.append("&lt;" + XML_NODE_HEAD3 + "&gt; \n");
		_xml.append("&lt;" + XML_NODE_HEAD4 + "&gt;");
		_xml.append("&lt;" + "target" + "&gt;" + "nds" + "&lt;/"  +  "target"  + "&gt; \n");
		_xml.append("&lt;/" + XML_NODE_HEAD4 + "&gt;");
		_xml.append("&lt;/" + XML_NODE_HEAD3 + "&gt;");
		_xml.append("</requestxml></runusertransaction></kscc_request>");

		return _xml.toString();
	}



}

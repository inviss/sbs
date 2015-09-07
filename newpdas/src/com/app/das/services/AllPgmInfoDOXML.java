package com.app.das.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import com.app.das.business.ExternalBusinessProcessor;
import com.app.das.business.transfer.ArchiveInfoDO;
import com.app.das.business.transfer.CartContDO;
import com.app.das.business.transfer.DepInfoDO;
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 타시스템의 프로그램 정보 관리 정보 관련 XML파서
 * @author asura207
 *
 */
public class AllPgmInfoDOXML extends DOXml{
	private static Logger logger = Logger.getLogger(AllPgmInfoDOXML.class);	
	PgmInfoDO InfoDO;
	/** 
	 * xml 헤더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/** 
	 * 내부 xml 헤더
	 */
	private String XML_NODE_SUB_HEAD = "pds_ex_service_programinfo";
	/** 
	 * 내부 xml 헤더2
	 */
	private String XML_NODE_SUB_HEAD2 = "program";
	/** 
	 * 에러 xml 헤더
	 */
	private String XML_NODE_ERROR_HEAD = "error";
	/** 
	 * 책임pd xml 헤더
	 */
	private String XML_NODE_CP_HEAD = "cpdlist";
	/** 
	 * pd xml 헤더
	 */
	private String XML_NODE_PD_HEAD = "pdlist";
	/** 
	 * 프로그램id
	 */
	private String XML_NODE_PROGRAMID = "pgm_id";
	/** 
	 * 프로그램명
	 */
	private String XML_NODE_PROGRAM_NAME = "programname";
	/** 
	 * 프로그램그룹코드
	 */
	private String XML_NODE_PROGRAM_CODE = "programcode";
	/** 
	 * 책임PD
	 */
	private String XML_NODE_PRODUCER_NAME = "producer_nm"; 
	/** 
	 * 책임 pd ID
	 */
	private String XML_NODE_PRODUCER_ID = "pd";
	/** 
	 * 제작 cpdID
	 */
	private String XML_NODE_PPRODUCTION_PRODUCER_ID = "cpd";
	/** 
	 * 제작PD(연출담당자)
	 */
	private String XML_NODE_PPRODUCTION_PRODUCTION_PRODUCER_NAME = "cpd_name";
	/** 
	 * 제작PD 연락처
	 */
	private String XML_NODE_PRODUCTION_PRODUCER_PHONE = "pro_producer_phon";
	/** 
	 * id
	 */
	private String XML_NODE_USER_ID = "userid";
	/** 
	 * 이름
	 */
	private String XML_NODE_USER_NAME = "username";

	/*public Object setDO(String _xml) {
		setDO(new ErpAppointDO());

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
	}*/


	public Object setDO(String _xml) {
		List result = new ArrayList();
		setDO(result);
		Document _document = getDocument(_xml);
		Element _rootElement = _document.getDocumentElement();
		NodeList _nodeList = _rootElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			NodeList _nodeList2 = _node.getChildNodes();
			String _nodeName = _node.getNodeName() ;
			System.out.println("_nodeName "+_nodeName);

			Node _node2 = _nodeList2.item(i);
			NodeList _nodeList3 = _node2.getChildNodes();
			String _nodeName2 = _node2.getNodeName() ;
			System.out.println("_nodeName2 "+_nodeName2);

			Node _node3 = _nodeList3.item(i);
			NodeList _nodeList4 = _node3.getChildNodes();
			String _nodeName3 = _node3.getNodeName() ;
			System.out.println("_nodeName3 "+_nodeName3);

			Node _node4 = _nodeList4.item(i);
			NodeList _nodeList5 = _node4.getChildNodes();
			String _nodeName4 = _node4.getNodeName() ;


			Node _node5 = _nodeList5.item(i);
			NodeList _nodeList6 = _node5.getChildNodes();
			String _nodeName5 = _node5.getNodeName() ;




			for(int k = 0; k < _nodeList6.getLength(); k++) {
				InfoDO = new PgmInfoDO();
				Node _node6 = _nodeList6.item(k);
				NodeList _nodeList7 = _node6.getChildNodes();
				String _nodeName6 = _node6.getNodeName() ;
				if(_nodeName6.equals(XML_NODE_SUB_HEAD2)) {
					setData((Element)_node6);
					result.add(InfoDO);

				}
			}
		}

		return getDO();
	}


	public Object setData(Element pElement) {
		List result = (List)getDO();
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		String pid ="";
		String pname ="";
		String cid ="";
		String cname ="";

		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			//NodeList _nodeList2 = _node.getChildNodes();
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			NamedNodeMap startAttr = _node.getAttributes();
			//String nodeValue = getNodeValue(_node);


			if(_nodeName.equals(XML_NODE_PROGRAM_CODE)) {
				InfoDO.setPROGRAM_CODE(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAM_NAME)) {
				InfoDO.setPROGRAM_NAME(_nodeValue);
			}

			else if(_nodeName.equals(XML_NODE_PPRODUCTION_PRODUCER_ID)) {

				for(int k = 0; k<startAttr.getLength();k++){

					Node attr = startAttr.item(k);
					String nodeName = attr.getNodeName() ;
					String att= attr.getNodeValue();

					if(nodeName.equals(XML_NODE_USER_ID)){
						if(cid.equals("")){
							cid = attr.getNodeValue();
						}else{
							cid = cid+","+attr.getNodeValue();

						}
						InfoDO.setCid(cid);

					}
					if(nodeName.equals(XML_NODE_USER_NAME)){
						if(cname.equals("")){
							cname = attr.getNodeValue();
						}else{
							cname = cname+","+attr.getNodeValue();
						}
						InfoDO.setPRODUCTION_PORDUCER_NAME(cname);	

					}

				}


			}
			else if(_nodeName.equals(XML_NODE_PRODUCER_ID)) {

				for(int h = 0; h<startAttr.getLength();h++){
					Node attr = startAttr.item(h);
					String nodeName = attr.getNodeName() ;
					String att= attr.getNodeValue();

					if(nodeName.equals(XML_NODE_USER_ID)){

						if(pid.equals("")){
							pid = attr.getNodeValue();

						}else{
							pid = pid+","+attr.getNodeValue();
						}

						InfoDO.setPid(pid);
					}

					if(nodeName.equals(XML_NODE_USER_NAME)){

						if(pid.equals("")){
							pname = attr.getNodeValue();
						}else{
							pname = pname+","+attr.getNodeValue();
						}
						//System.out.println(pname);
						InfoDO.setPRODUCER_NAME(pname);	
					}

				}


			}

		}

		return result;

	}	    
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		_xml = _xml + "<das>";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}


	public String getSubXML() {
		PgmInfoDO infoDO = (PgmInfoDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");
		_xml.append("<" + XML_NODE_PROGRAMID + ">" + infoDO.getPROGRAMID() + "</"  + XML_NODE_PROGRAMID + "> \n");
		_xml.append("<" + XML_NODE_PROGRAM_NAME + ">" + CommonUtl.transXmlText(infoDO.getPROGRAM_NAME()) + "</"  + XML_NODE_PROGRAM_NAME + "> \n");
		_xml.append("<" + XML_NODE_PROGRAM_CODE + ">" + infoDO.getPROGRAM_CODE() + "</"  + XML_NODE_PROGRAM_CODE + "> \n");
		_xml.append("<" + XML_NODE_PRODUCER_NAME + ">" + CommonUtl.transXmlText(infoDO.getPRODUCER_NAME())+ "</"  + XML_NODE_PRODUCER_NAME + "> \n");
		_xml.append("<" + XML_NODE_PPRODUCTION_PRODUCTION_PRODUCER_NAME + ">" + CommonUtl.transXmlText(infoDO.getPRODUCTION_PORDUCER_NAME()) + "</"  + XML_NODE_PPRODUCTION_PRODUCTION_PRODUCER_NAME + "> \n");
		_xml.append("<" + XML_NODE_PRODUCTION_PRODUCER_PHONE + ">" + infoDO.getPRODUCTION_PORDUCER_PHONE() + "</"  + XML_NODE_PRODUCTION_PRODUCER_PHONE + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");

		return _xml.toString();
	}

}

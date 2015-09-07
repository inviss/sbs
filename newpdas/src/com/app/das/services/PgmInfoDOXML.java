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
import com.app.das.business.transfer.PgmInfoDO;
import com.app.das.business.transfer.PgmUserInfoDO;
import com.app.das.business.transfer.PhotoInfoDO;
import com.app.das.util.CommonUtl;
/**
 * 프로그램  정보 관련 XML파서
 * @author asura207
 *
 */
public class PgmInfoDOXML extends DOXml{
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "mamex_response";
	/**
	 * xml해더
	 */
	private String XML_NODE_SUB_HEAD = "pds_ex_get_programinfo";
	/**
	 * 책임 피디 리스트
	 */
	private String XML_NODE_CP_HEAD = "cpdlist";
	/**
	 * 피디 리스트
	 */
	private String XML_NODE_PD_HEAD = "pdlist";
	/**
	 * 프로그램ID
	 */
	private String XML_NODE_PROGRAMID = "pgm_id";
	/**
	 * 프로그램명
	 */
	private String XML_NODE_PROGRAM_NAME = "programname";
	/**
	 * 프로그램코드
	 */
	private String XML_NODE_PROGRAM_CODE = "programcode";
	/**
	 * 피디 이름
	 */
	private String XML_NODE_PRODUCER_NAME = "producer_nm"; 
	/**
	 * xml해더
	 */
	private String XML_NODE_PRODUCER_ID = "pd";
	/**
	 * xml해더
	 */
	private String XML_NODE_PPRODUCTION_PRODUCER_ID = "cpd";
	/**
	 * 책임피디 이름
	 */
	private String XML_NODE_PPRODUCTION_PRODUCTION_PRODUCER_NAME = "cpd_name";
	/**
	 * 피디이름
	 */
	private String XML_NODE_PRODUCTION_PRODUCER_PHONE = "pro_producer_phon";




	public Object setDO(String _xml) {
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
	}






	public Object setData(Element pElement) {
		PgmInfoDO infoDO = (PgmInfoDO)getDO();
		List result = new ArrayList();
		String cidList="0";
		String pidList="0";
		String cnmList="0";
		String pnmList="0";
		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));
			NamedNodeMap startAttr = _node.getAttributes();
			String nodeValue = getNodeValue(_node);


			if(_nodeName.equals(XML_NODE_PROGRAM_CODE)) {
				infoDO.setPROGRAM_CODE(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_PROGRAM_NAME)) {
				infoDO.setPROGRAM_NAME(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_CP_HEAD)) {

				NodeList nList = _node.getChildNodes();

				int leng = nList.getLength();					 
				for(int q=0; q<leng ; q++){
					Node nde = nList.item(q);
					String nName = nde.getNodeName() ;
					String nValue = getNodeValue(nde);
					NamedNodeMap CpdAttr = nde.getAttributes();
					String _nValue = getNodeValue(nde);

					if(nName.equals(XML_NODE_PPRODUCTION_PRODUCER_ID)){
						for(int k = 0; k<CpdAttr.getLength();k++){
							Node attr = CpdAttr.item(k);
							String nodeName = attr.getNodeName() ;
							String att= attr.getNodeValue();

							//	 infoDO.setSuccess_yn(att);



							if(nodeName.equals("id")){
								//infoDO.setCid(attr.getNodeValue());
								cidList +=","+attr.getNodeValue();
								infoDO.setCid(cidList);

							}					

						}
						cnmList += ","+nValue;
						infoDO.setPRODUCTION_PORDUCER_NAME(cnmList);

					}

				}	


			}
			else if(_nodeName.equals(XML_NODE_PD_HEAD)) {

				NodeList nList = _node.getChildNodes();
				int leng = nList.getLength();					 
				for(int q=0; q<leng ; q++){
					Node nde = nList.item(q);
					String nName = nde.getNodeName() ;
					String nValue = getNodeValue(nde);
					NamedNodeMap PdAttr = nde.getAttributes();
					String _nValue = getNodeValue(nde);
					if(nName.equals(XML_NODE_PRODUCER_ID)){
						for(int k = 0; k<PdAttr.getLength();k++){
							Node attr = PdAttr.item(k);
							String nodeName = attr.getNodeName() ;
							String att= attr.getNodeValue();




							if(nodeName.equals("id")){
								pidList += ","+attr.getNodeValue();
								infoDO.setPid(pidList);
							}
							pnmList += ","+nValue;
							infoDO.setPRODUCER_NAME(pnmList);
						}
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

package com.app.das.services;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList; 

import com.app.das.business.constants.Constants;
import com.app.das.business.transfer.AutoArchiveDO;
import com.app.das.business.transfer.BoardConditionDO;
import com.app.das.business.transfer.PdsMappDO;
import com.app.das.util.CommonUtl;


/**
 *  pds 프로그램별 mapping 정보 관련 XML파서
 * @author asura207
 *
 */
public class PdsMappDOXML extends DOXml {
	/**
	 * xml해더
	 */
	private String XML_NODE_HEAD = "pdsmapp";
	/** 
	 * PDS 프로그램 ID
	 */
	private String XML_NODE_PDS_PGM_ID = "pds_pgm_id";
	/** 
	 * PDS 프로그램 NM
	 */
	private String XML_NODE_PDS_PGM_NM="pds_pgm_nm";
	/** 
	 * DAS 프로그램 ID
	 */
	private String XML_NODE_DAS_PGM_ID="das_pgm_id";
	/** 
	 * DAS 프로그램 NM
	 */
	private String XML_NODE_DAS_PGM_NM="das_pgm_nm";





	public Object setDO(String _xml) {
		setDO(new PdsMappDO());

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
		PdsMappDO autoDO = (PdsMappDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_PDS_PGM_ID)) {
				autoDO.setDas_pgm_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_PDS_PGM_NM)) {
				autoDO.setDas_pgm_nm(_nodeValue);
			}
			else if(_nodeName.equals(XML_NODE_DAS_PGM_ID)) {
				autoDO.setPds_pgm_id(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_DAS_PGM_NM)) {

				autoDO.setPds_pgm_nm(_nodeValue);

			}


		}

		return autoDO;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?> \n";
		_xml = _xml + "<das> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</das>";

		return _xml;
	}


	public String getSubXML() {
		PdsMappDO autoDO = (PdsMappDO)getDO();
		StringBuffer _xml = new StringBuffer();
		_xml.append("<" + XML_NODE_HEAD + "> \n");	
		_xml.append("<" + XML_NODE_PDS_PGM_ID + ">" + autoDO.getDas_pgm_id() + "</"  + XML_NODE_PDS_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_PDS_PGM_NM + ">" + CommonUtl.transXmlText(autoDO.getDas_pgm_nm()) + "</"  + XML_NODE_PDS_PGM_NM + "> \n");
		_xml.append("<" + XML_NODE_DAS_PGM_ID + ">" +autoDO.getPds_pgm_id() + "</"  + XML_NODE_DAS_PGM_ID + "> \n");
		_xml.append("<" + XML_NODE_DAS_PGM_NM + ">" + CommonUtl.transXmlText(autoDO.getPds_pgm_nm()) + "</"  + XML_NODE_DAS_PGM_NM + "> \n");
		_xml.append("</" + XML_NODE_HEAD + ">");
		return _xml.toString();
	}


}

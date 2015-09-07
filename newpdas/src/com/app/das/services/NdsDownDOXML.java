package com.app.das.services;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.app.das.business.transfer.BoardDO;
import com.app.das.business.transfer.NdsDownDO;
import com.app.das.business.transfer.PdsDownDO;
import com.app.das.util.CommonUtl;

/**
 *  NDS 다운 정보 관련 XML파서
 * @author asura207
 *
 */
public class NdsDownDOXML extends DOXml {
	/**
	 * xml 해더
	 */
	private String XML_NODE_HEAD = "ndsdown";
	/**
	 * 메타데이터 genrator 이름
	 */
	private String XML_NODE_GENERATOR_NAME = "board_id";
	/**
	 *  메타데이터 genrator 버전
	 */
	private String XML_NODE_GENERATOR_VER="board_type_cd";
	/**
	 * 제목
	 */
	private String XML_NODE_TOTAL_STREAM_SIZE="TOTAL_STREAM_SIZE";


	/**
	 * 미디어타입
	 */
	private String XML_NODE_MEDIATYPECODE="MEDIATYPECODE";
	/**
	 * 미디어id
	 */
	private String XML_NODE_MEDIAID="MEDIAID";	
	/**
	 * 제목
	 */
	private String XML_NODE_TITLE="TITLE";
	/**
	 * 부제
	 */
	private String XML_NODE_SUBTITLE="SUBTITLE";
	/**
	 * 물리적트리
	 */
	private String XML_NODE_PHYSICALTREE="PHYSICALTREE";
	/**
	 * 논리적트리
	 */
	private String XML_NODE_LOGICALTREE="LOGICALTREE";
	/**
	 * 작업명
	 */
	private String XML_NODE_WORKERNAME="WORKERNAME";
	/**
	 * 카트번호
	 */
	private String XML_NODE_CART_NO="CART_NO";
	/**
	 * 카트순서
	 */
	private String XML_NODE_CART_SEQ="CART_SEQ";

	public Object setDO(String _xml) {
		setDO(new NdsDownDO());

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
		NdsDownDO ndsdown = (NdsDownDO)getDO();

		NodeList _nodeList = pElement.getChildNodes();
		int _length = _nodeList.getLength();
		for(int i = 0; i < _length; i++) {
			Node _node = _nodeList.item(i);
			String _nodeName = _node.getNodeName() ;
			String _nodeValue = CommonUtl.transXMLText(getNodeValue(_node));

			if(_nodeName.equals(XML_NODE_CART_NO)) {
				ndsdown.setCart_no(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
			else if(_nodeName.equals(XML_NODE_CART_SEQ)) {
				ndsdown.setCart_seq(Integer.parseInt(StringUtils.defaultIfEmpty(_nodeValue, "0")));
			}
		}

		return ndsdown;
	}
	public String toXML() {

		String _xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> \n";
		_xml = _xml + "<sbsnds xmlns=\"urn:sbsnds2010\"> \n";
		_xml = _xml + getSubXML();
		_xml = _xml + "</sbsnds>";

		return _xml;
	}


	public String getSubXML() {
		NdsDownDO ndsdown = (NdsDownDO)getDO();
		StringBuffer _xml = new StringBuffer();
		long mxf_sz=fl_sz(ndsdown.getCti_id()+".mxf");
		long xml_sz=fl_sz(ndsdown.getCti_id()+".xml");
		long total_sz = mxf_sz+xml_sz;
		_xml.append("<sbsnds xmlns=\"urn:sbsnds2010\"> \n");
		_xml.append("<generator> \n");
		_xml.append("<generator>sbsdas</generator>\n");
		_xml.append("<generator>1.0</generator>\n");
		_xml.append("</generator>\n");		
		_xml.append("<total_stream_size>"+total_sz+"</total_stream_size>\n");	//size byte cart_cont
		_xml.append("<stream_path isfolder = \"false\">\n");
		_xml.append("<stream>"+mxf_sz+"</stream>\n");//mxf

		_xml.append("<stream>"+xml_sz+"</stream>\n");//xml

		_xml.append("</stream_path>\n");
		_xml.append("<clipinformation>\n");	
		_xml.append("<media>\n");
		_xml.append("<" + XML_NODE_MEDIATYPECODE + ">" + ndsdown.getMediatypecode() + "</"  + XML_NODE_MEDIATYPECODE + "> \n");
		_xml.append("<" + XML_NODE_MEDIAID + ">" + ndsdown.getMediaid() + "</"  + XML_NODE_MEDIAID + "> \n");
		_xml.append("<" + XML_NODE_TITLE + ">" + CommonUtl.transXmlText(ndsdown.getTitle()) + "</"  + XML_NODE_TITLE + "> \n");
		_xml.append("<" + XML_NODE_SUBTITLE + ">" + CommonUtl.transXmlText(ndsdown.getSutitle()) + "</"  + XML_NODE_SUBTITLE + "> \n");
		_xml.append("<" + XML_NODE_PHYSICALTREE + "> </"  + XML_NODE_PHYSICALTREE + "> \n");
		_xml.append("<" + XML_NODE_LOGICALTREE + "> </"  + XML_NODE_LOGICALTREE + "> \n");
		_xml.append("<" + XML_NODE_WORKERNAME + ">" + CommonUtl.transXmlText(ndsdown.getWorkername()) + "</"  + XML_NODE_WORKERNAME + "> \n");
		_xml.append("</media>\n");
		_xml.append("</clipinformation>\n");		


		return _xml.toString();
	}

	public int fl_sz(String file_nm){
		PdsDownDO pdsdown = (PdsDownDO)getDO();

		File oFile = new File(pdsdown.getDown_path()+file_nm);

		if (oFile.exists()) {
			long L = oFile.length();
			//System.out.println(L + " bytes : " + oFile.getAbsoluteFile());
		}
		else {
			//System.err.println("파일이 없음...");
		}
		return 0;
	}
}
